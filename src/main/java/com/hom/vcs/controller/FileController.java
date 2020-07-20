package com.hom.vcs.controller;

import java.io.IOException;

import javax.websocket.server.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.hom.vcs.service.FileService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/folder/{folderName}/file/{fileName}")
@RequiredArgsConstructor
public class FileController {		
	
	private final FileService fileService;
	
	@GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> getFileContent(@PathVariable("folderName") String foldername,
			@PathVariable("fileName") String fileName) throws IOException 
	{
		String fileContent = fileService.getFileContent(foldername, fileName);
		
		return ResponseEntity
				.ok()						
				.body(fileContent);
	}
	
	@PutMapping
	public ResponseEntity<Void> updateFile(@PathVariable("folderName") String foldername,
			@PathVariable("fileName") String fileName,
			@PathParam("fileContent") String fileContent) throws IOException 
	{
		fileService.updateFileContent(foldername, fileName, fileContent);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.build();
	}
}