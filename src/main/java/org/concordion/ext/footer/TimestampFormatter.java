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
package org.concordion.ext.footer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimestampFormatter {

    Pattern timePattern = Pattern.compile("in (\\d*) ms (.*)");
    
    public String reformat(String string) {
        Matcher matcher = timePattern.matcher(string);
        if (matcher.matches()) {
            Integer ms = Integer.valueOf(matcher.group(1));
            int seconds = ms/1000;

            StringBuilder sb = new StringBuilder(100);
            sb.append("in ");
            int minutes = 0;
            if (seconds > 60) {
                minutes = seconds/60;
                seconds = seconds - (minutes * 60);
                
                if (minutes > 60) {
                    int hours = minutes / 60;
                    minutes = minutes - (hours * 60);
                    sb.append(hours).append("h ");
                }
                
                sb.append(minutes).append("m ");
            }
            sb.append(seconds).append("s ");
            sb.append(matcher.group(2));
            return sb.toString();
        }
        
        return string;
    }

}
