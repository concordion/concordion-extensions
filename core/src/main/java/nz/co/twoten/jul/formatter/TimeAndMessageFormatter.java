package nz.co.twoten.jul.formatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
 
public class TimeAndMessageFormatter extends Formatter {
	
	private static final DateFormat format = new SimpleDateFormat("h:mm:ss.SSS");
	private static final String lineSep = System.getProperty("line.separator");

	@Override
	public String format(LogRecord record) {
		StringBuilder output = new StringBuilder()
			.append("[")
			.append(format.format(new Date(record.getMillis())))
			.append("]: ")
			.append(record.getMessage()).append(' ')
			.append(lineSep);
		return output.toString();		
	}
}
