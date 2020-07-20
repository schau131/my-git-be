package com.hom.vcs.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileUtil {

	public static File createFile(String rootPath, String folder, String fileName) 
	{
		File uploadPath = Paths.get(rootPath, folder).toFile();
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		return new File(uploadPath.getAbsoluteFile(), fileName);
	}

	public static File createVersionedFile(String rootPath, String folder, int versionFolder, String fileName) 
	{
		File uploadPath = Paths.get(rootPath, folder, String.valueOf(versionFolder)).toFile();
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		return new File(uploadPath.getAbsoluteFile(), fileName);
	}
	
	public static String readFileContent(String absolutePath) throws IOException 
	{
		StringBuilder sb = new StringBuilder();
		
		try(Stream<String> stream = Files.lines(Paths.get(absolutePath))){
			stream.forEach(line -> sb.append(line).append(Constants.NEW_LINE));
		}
		return sb.toString();
	}
	
	public static void writeNewFile(String content, File file) throws IOException 
	{
		Files.write(Paths.get(file.getAbsolutePath()), content.getBytes());
	}
	
	public static void updateFile(File file, String fileContent) throws IOException 
	{
		try(RandomAccessFile stream = new RandomAccessFile(file, "rw"); FileChannel channel = stream.getChannel(); )
		{
			FileLock lock = null;
			try {
				lock = channel.tryLock();
			} catch (OverlappingFileLockException e) {
				stream.close();
				channel.close();
			}
			stream.writeBytes(fileContent);
			lock.release();
		}
	}
}
