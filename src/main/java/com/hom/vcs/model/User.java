package com.hom.vcs.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//3rd day- 1st commit//
@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@Id
	private String id;
	
	private String folderName;
	
	private List<String> fileNames;
	
	public List<String> getFileNames(){
		if(null == fileNames) {
			fileNames = new ArrayList<>();
		}
		return fileNames;
	}

}
