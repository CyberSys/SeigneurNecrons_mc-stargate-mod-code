package scripts.tools;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * @author Seigneur Necron
 */
public class CustomFormatter extends Formatter {
	
	@Override
	public String format(LogRecord record) {
		String throwable = "";
		
		if(record.getThrown() != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			pw.println();
			record.getThrown().printStackTrace(pw);
			pw.close();
			throwable = sw.toString();
		}
		
		if(!throwable.isEmpty()) {
			throwable += "\n";
		}
		
		return record.getLevel() + " : " + record.getMessage() + "\n" + throwable;
	}
	
}
