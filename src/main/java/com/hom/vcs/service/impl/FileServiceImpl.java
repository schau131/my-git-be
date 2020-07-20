package com.hom.vcs.service.impl;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hom.vcs.service.FileService;
import com.hom.vcs.service.UserService;
import com.hom.vcs.util.FileUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{

	@Value("${file.root.path}")
	private String fileRootPath;

	private final UserService userService;
	
	@Override
	public String getFileContent(String folderName, String fileName) throws IOException 
	{
		File file = FileUtil.createFile(fileRootPath, folderName, fileName);		
		return FileUtil.readFileContent(file.getAbsolutePath());
	}

	@Override
	public void updateFileContent(String folderName, String fileName, String fileContent) throws IOException 
	{					
		File file = FileUtil.createFile(fileRootPath, folderName, fileName);
		userService.versionControl(folderName, file);
		FileUtil.updateFile(file, fileContent);				
	}

}
