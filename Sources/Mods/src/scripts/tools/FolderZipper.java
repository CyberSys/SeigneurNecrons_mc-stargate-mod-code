package scripts.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Seigneur Necron
 */
public class FolderZipper {
	
	/**
	 * Zip the specified folder into the specified zip file.
	 * @param sourceFolder - the folder to zip.
	 * @param zipFile - the output zip file location.
	 */
	public static void zipIt(String sourceFolder, String zipFile) {
		Logger logger = CustomLogger.getInstance();
		
		File absoluteSourceFolder = (new File(sourceFolder)).getAbsoluteFile();
		sourceFolder = absoluteSourceFolder.toString();
		
		List<String> fileList = generateFileList(sourceFolder, absoluteSourceFolder);
		byte[] buffer = new byte[1024];
		
		try(FileOutputStream fileOuput = new FileOutputStream(zipFile); ZipOutputStream zipOutput = new ZipOutputStream(fileOuput)) {
			logger.info("Output to Zip : " + zipFile);
			
			for(String file : fileList) {
				logger.info("File Added : " + file);
				ZipEntry ze = new ZipEntry(file);
				zipOutput.putNextEntry(ze);
				
				try(FileInputStream in = new FileInputStream(sourceFolder + File.separator + file)) {
					int len;
					while((len = in.read(buffer)) > 0) {
						zipOutput.write(buffer, 0, len);
					}
				}
			}
			
			logger.info("Zip done");
		}
		catch(IOException ex) {
			ex.printStackTrace();
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
			fileList.add(generateZipEntry(sourceFolder, node.getAbsoluteFile().toString()));
		}
		else if(node.isDirectory()) {
			String[] subNode = node.list();
			
			for(String filename : subNode) {
				fileList.addAll(generateFileList(sourceFolder, new File(node, filename)));
			}
		}
		
		return fileList;
	}
	
	/**
	 * Format the file path for zip.
	 * @param sourceFolder - the folder to zip.
	 * @param file - the file path.
	 * @return The formatted file path.
	 */
	private static String generateZipEntry(String sourceFolder, String file) {
		return file.substring(sourceFolder.length() + 1, file.length());
	}
}
