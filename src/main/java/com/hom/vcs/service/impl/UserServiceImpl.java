package com.hom.vcs.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hom.vcs.model.User;
import com.hom.vcs.model.VersionedFile;
import com.hom.vcs.model.VersionedFileKey;
import com.hom.vcs.repository.UserRepository;
import com.hom.vcs.repository.VersionedFileRepository;
import com.hom.vcs.service.UserService;
import com.hom.vcs.util.FileUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
	
	@Value("${file.root.path}")
	private String fileRootPath;
	
	@Value("${versioned.file.root.path}")
	private String versionedFileRootPath; 
	
	private final UserRepository userRepository;
	
	private final VersionedFileRepository versionedFileRepository;
	
	@Override
	@Transactional
	public void uploadUserFiles(String userId, MultipartFile[] files) throws IOException 
	{		
		User existingUser = this.getUser(userId);
		User user = existingUser != null ? existingUser : User.builder()
															.id(userId)
															.folderName(userId).build();
	
		for(MultipartFile file : files) {
			File userFile = FileUtil.createFile(fileRootPath, user.getFolderName(), file.getOriginalFilename());	
			
			if(user.getFileNames().contains(file.getOriginalFilename())) {
				this.versionControl(user.getFolderName(), userFile);
			} else {
				user.getFileNames().add(file.getOriginalFilename());
			}
			
			String fileContent = new String(file.getBytes());
			
			try {
				FileUtil.updateFile(userFile, fileContent);
			} catch(IOException io) {
				log.error("Exception occured while updating a file in uploadUserFiles : " + io.getMessage());
				throw io;
			}
		}	
		userRepository.save(user);
	}

	
	@Override
	public User getUser(String userId)
	{
		Optional<User> optionalUser = userRepository.findById(userId);
		return optionalUser.orElse(null);
	}
	
		
	public void versionControl(String folderName, File previousFile) throws IOException 
	{
		VersionedFileKey versionedFileKey = VersionedFileKey.builder()
				.folderName(folderName)
				.fileName(previousFile.getName())
				.build();
		
		VersionedFile versionedFile = this.getVersionedFile(versionedFileKey);		
		int version=0 ;
	
		if(null == versionedFile || versionedFile.getVersions().isEmpty()) {
			versionedFile = VersionedFile.builder()
					.versionedFileKey(versionedFileKey).build();
			
			versionedFile.getVersions().add(++version);			
		} else {
			version = versionedFile.getVersions().getLast();
			versionedFile.getVersions().add(++version);
		}
						
		File newFile = FileUtil.createVersionedFile(versionedFileRootPath, folderName, version, previousFile.getName());
		
		try {
			FileUtil.writeNewFile(FileUtil.readFileContent(previousFile.getAbsolutePath()), newFile);
		} catch(IOException io) {
			log.error("Exception occured while writing to a version controlled file : " + io.getMessage());
			throw io;
		}
		
		versionedFileRepository.save(versionedFile);
	}
	
	public VersionedFile getVersionedFile(VersionedFileKey versionedFileKey)
	{
		Optional<VersionedFile> optionalUser = versionedFileRepository.findById(versionedFileKey);
		return optionalUser.orElse(null);
	}
	
}
