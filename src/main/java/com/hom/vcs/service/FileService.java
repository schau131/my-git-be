package com.hom.vcs.service;

import java.io.IOException;

public interface FileService {

	public String getFileContent(String foldername, String fileName) throws IOException ;
	
	public void updateFileContent(String foldername, String fileName, String fileContent) throws IOException;	
	
}
