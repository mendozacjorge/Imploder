package org.imploder;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.imploder.beans.CustomFile;
import org.imploder.beans.CustomFolder;

/**
 *
 * @author Jorge
 */
public class Operations {

	public static void explodeFolder(Path originFile, Path destination, boolean deleteSource) throws Exception {
		try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(originFile.toString()))) {
			CustomFolder folder = (CustomFolder) is.readObject();			
			Path newLocation = destination == null
				? Paths.get(originFile.getParent().toString(), folder.getName())
				: destination;			
			
			if (!Files.exists(newLocation))
				Files.createDirectory(newLocation);
			
			folder.getFiles().stream().forEach(cf -> {
				try {
					Files.write(Paths.get(newLocation.toString(), cf.getName()), cf.getBytes());
				} catch (Exception ex) {
					log(ex.getMessage());
				}
			});
			
		} catch (Exception ex) {
			throw ex;
		}
		
		if (deleteSource) deleteFileOrFolder(originFile);
		
	}
	
	public static void implodeFolder(Path folder, Path resultFile, boolean deleteSource) throws Exception {
		try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(resultFile.toString()))) {
			CustomFolder cFolder = new CustomFolder();
			cFolder.setName(folder.getFileName().toString());
			
			final List<CustomFile> files = new ArrayList<>();
			
			Files.walkFileTree(folder, new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					if (Files.isDirectory(file)) {
						//TODO: nested folders
					} else {
						CustomFile cFile = new CustomFile();
						cFile.setName(file.getFileName().toString());
						cFile.setBytes(Files.readAllBytes(file));						
						files.add(cFile);
					}
					return FileVisitResult.CONTINUE;
				}
				
			});
			
			cFolder.setFiles(files);
			os.writeObject(cFolder);
			
			if (deleteSource) deleteFileOrFolder(folder);
			
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	private static void log(String msg) {
		log(msg, null);
	}
	
	private static void log(String msg, Exception ex) {
		Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, msg, ex);
	}
	
	
	/**
	 * Deletes the resource recursively in a DFS manner.
	 *
	 * @param path
	 * @throws IOException in case the delete operation fails
	 */
	public static void deleteFileOrFolder(Path path) throws IOException {
		if (Files.exists(path)) {
			if (Files.isDirectory(path)) {
				try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
					for (Path entry : stream) {
						deleteFileOrFolder(entry);
					}
				}
			}
			Files.deleteIfExists(path);
		}
	}
	
}
