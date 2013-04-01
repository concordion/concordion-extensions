/*
 * Copyright (c) 2010 Two Ten Consulting Limited, New Zealand 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.concordion.ext.jul.formatter;

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
