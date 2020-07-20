package com.hom.vcs.service;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.hom.vcs.model.User;

public interface UserService {

	public void uploadUserFiles(String userId, MultipartFile[] files) throws IOException;
	
	public User getUser(String userId);	
	
	public void versionControl(String folderName, File previousFile) throws IOException;
}
