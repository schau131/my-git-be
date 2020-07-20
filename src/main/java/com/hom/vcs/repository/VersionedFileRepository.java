package com.hom.vcs.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.hom.vcs.model.VersionedFile;
import com.hom.vcs.model.VersionedFileKey;

@Repository
public interface VersionedFileRepository extends MongoRepository<VersionedFile, VersionedFileKey>{

}
