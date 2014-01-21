package scripts.tools;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Seigneur Necron
 */
public class CustomLogger {
	
	private static final String LOGGER_NAME = CustomLogger.class.getName();
	private static final String LOG_FILE = "scriptLog.txt";
	private static final Logger INSTANCE = init();
	
	public static Logger getInstance() {
		return INSTANCE;
	}
	
	private static Logger init() {
		Logger logger = Logger.getLogger(LOGGER_NAME);
		logger.setLevel(Level.ALL);
		logger.setUseParentHandlers(false);
		
		Formatter formatter = new CustomFormatter();
		
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(formatter);
		consoleHandler.setLevel(Level.ALL);
		logger.addHandler(consoleHandler);
		
		try {
			FileHandler fileHandler = new FileHandler(LOG_FILE, false);
			fileHandler.setFormatter(formatter);
			fileHandler.setLevel(Level.ALL);
			fileHandler.setEncoding("UTF-8");
			logger.addHandler(fileHandler);
		}
		catch(IOException argh) {
			argh.printStackTrace();
		}
		
		return logger;
	}
	
	private CustomLogger() {
		// Don't have to instanciate this class.
	}
	
}
