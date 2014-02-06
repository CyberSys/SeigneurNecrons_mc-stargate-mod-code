package scripts.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public class FileTools {
	
	// Logger :
	
	protected static final Logger logger = CustomLogger.getInstance();
	
	// Constructors :
	
	private FileTools() {
		// This is an utility class. It don't have to be instanciated.
	}
	
	// Methods :
	
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
	 * Copies a file to a new location preserving the file date. <br />
	 * Calls FileUtils.copyFile(srcFile, destFile) and log the action.
	 * @param srcFile  an existing file to copy, must not be {@code null}
	 * @param destFile  the new file, must not be {@code null}
	 * @throws IOException 
	 */
	public static void copyFile(File srcFile, File destFile) throws IOException {
		FileUtils.copyFile(srcFile, destFile);
		logger.info("-> file copied to : " + destFile.getAbsolutePath());
	}
	
	/**
	 * Copies a whole directory to a new location preserving the file dates. <br />
	 * Calls FileUtils.copyDirectory(srcDir, destDir) and log the action.
	 * @param srcDir  an existing directory to copy, must not be {@code null}
	 * @param destDir  the new directory, must not be {@code null}
	 * @throws IOException
	 */
	public static void copyDirectory(File srcDir, File destDir) throws IOException {
		FileUtils.copyDirectory(srcDir, destDir);
		logger.info("-> directory copied to : " + destDir.getAbsolutePath());
	}
	
	/**
	 * Deletes a directory recursively. <br />
	 * Calls FileUtils.deleteDirectory(directory) and log the action.
	 * @param directory  directory to delete
	 * @throws IOException in case deletion is unsuccessful
	 */
	public static void deleteDirectory(File directory) throws IOException {
		FileUtils.deleteDirectory(directory);
		logger.info("-> directory deleted : " + directory.getAbsolutePath());
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
			
			logger.info("-> deleted content of directory : " + directory.getAbsolutePath());
		}
		else {
			throw new IOException("The file " + directory.getAbsolutePath() + " is not a directory.");
		}
	}
	
	/**
	 * Zips the content of the specified folder into the specified zip file.
	 * @param sourceFolderPath - the folder to zip.
	 * @param zipFile - the output zip file.
	 * @throws ZipException 
	 */
	public static void zipFolderContent(File sourceFolder, File zipFile) throws ZipException {
		ZipFile zip = new ZipFile(zipFile);
		ZipParameters params = new ZipParameters();
		
		if(sourceFolder.isDirectory()) {
			for(File file : sourceFolder.listFiles()) {
				if(file.isDirectory()) {
					zip.addFolder(file, params);
				}
				else {
					zip.addFile(file, params);
				}
			}
		}
		else {
			throw new ZipException("The file " + sourceFolder.getAbsolutePath() + " is not a directory.");
		}
		
		logger.info("-> output zip : " + zipFile.getAbsolutePath());
	}
	
	/**
	 * Zips the specified folder into the specified zip file.
	 * @param sourceFolderPath - the folder to zip.
	 * @param zipFile - the output zip file.
	 * @throws ZipException 
	 */
	public static void zipFolder(File sourceFolder, File zipFile) throws ZipException {
		ZipFile zip = new ZipFile(zipFile);
		ZipParameters params = new ZipParameters();
		
		if(sourceFolder.isDirectory()) {
			zip.addFolder(sourceFolder, params);
		}
		else {
			throw new ZipException("The file " + sourceFolder.getAbsolutePath() + " is not a directory.");
		}
		
		logger.info("-> output zip : " + zipFile.getAbsolutePath());
	}
	
}
