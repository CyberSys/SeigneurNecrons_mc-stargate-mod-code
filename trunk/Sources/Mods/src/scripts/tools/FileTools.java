package scripts.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * @author Seigneur Necron
 */
public class FileTools {
	
	/**
	 * Finds the strings matching a regexp in a file.
	 * @param file - the file in which the string must be find.
	 * @param regexp - the regexp to match.
	 * @return a Matcher wich can be used to get the results.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Matcher findStringInFile(File file, String regexp) throws FileNotFoundException, IOException {
		String content = "";
		
		try(FileInputStream input = new FileInputStream(file)) {
			content = IOUtils.toString(input);
		}
		
		return Pattern.compile(regexp).matcher(content);
	}
	
	/**
	 * Replaces the first string matching a regexp in a file by the specified string.
	 * @param file - the file in which the string must be replaced.
	 * @param regexp - the regexp to match.
	 * @param newString - the string that will replace the first occurence.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void replaceFirstInFile(File file, String regexp, String newString) throws FileNotFoundException, IOException {
		String content = "";
		
		try(FileInputStream input = new FileInputStream(file)) {
			content = IOUtils.toString(input);
		}
		
		content = content.replaceFirst(regexp, newString);
		
		try(FileOutputStream output = new FileOutputStream(file)) {
			IOUtils.write(content, output);
		}
	}
	
	/**
	 * Deletes all the content of the specified folder.
	 * @param directory - the folder to delete.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void deleteFolderContent(File directory) throws FileNotFoundException, IOException {
		if(directory.isDirectory()) {
			for(File file : directory.listFiles()) {
				FileUtils.forceDelete(file);
			}
		}
		else {
			throw new IOException("The file " + directory.getAbsolutePath() + " is not a directory.");
		}
	}
	
	/**
	 * Zips the specified folder into the specified zip file.
	 * @param sourceFolderPath - the folder to zip.
	 * @param zipFile - the output zip file.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void createZip(File sourceFolder, File zipFile) throws FileNotFoundException, IOException {
		sourceFolder = sourceFolder.getAbsoluteFile();
		String sourceFolderPath = sourceFolder.getAbsolutePath();
		
		List<String> fileList = generateFileList(sourceFolderPath, sourceFolder);
		byte[] buffer = new byte[1024];
		
		try(FileOutputStream fileOuput = new FileOutputStream(zipFile); ZipOutputStream zipOutput = new ZipOutputStream(fileOuput)) {
			for(String file : fileList) {
				ZipEntry zipEntry = new ZipEntry(file);
				zipOutput.putNextEntry(zipEntry);
				
				try(FileInputStream in = new FileInputStream(sourceFolderPath + File.separator + file)) {
					int len;
					while((len = in.read(buffer)) > 0) {
						zipOutput.write(buffer, 0, len);
					}
				}
			}
		}
	}
	
	/**
	 * Returns the list of all files contained in a directory (recursive).
	 * @param sourceFolder - the folder to zip.
	 * @param node - current file or directory.
	 * @return the list of all files contained in a directory.
	 */
	private static List<String> generateFileList(String sourceFolder, File node) {
		List<String> fileList = new LinkedList<String>();
		
		if(node.isFile()) {
			fileList.add(generateZipEntry(sourceFolder, node.getAbsolutePath()));
		}
		else if(node.isDirectory()) {
			for(File subNod : node.listFiles()) {
				fileList.addAll(generateFileList(sourceFolder, subNod));
			}
		}
		
		return fileList;
	}
	
	/**
	 * Formats the file path for zip.
	 * @param sourceFolder - the folder to zip.
	 * @param file - the file path.
	 * @return The formatted file path.
	 */
	private static String generateZipEntry(String sourceFolder, String file) {
		return file.substring(sourceFolder.length() + 1, file.length());
	}
}
