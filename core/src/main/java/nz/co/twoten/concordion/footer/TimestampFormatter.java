package nz.co.twoten.concordion.footer;

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
