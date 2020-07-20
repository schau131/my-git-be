package com.hom.vcs.model;

import java.util.LinkedList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VersionedFile {
	
	@Id
	private VersionedFileKey versionedFileKey;		
	
	private LinkedList<Integer> versions;
	
	public LinkedList<Integer> getVersions(){
		if(null == versions) {
			versions = new LinkedList<>();			
		}
		return versions;
	}

}