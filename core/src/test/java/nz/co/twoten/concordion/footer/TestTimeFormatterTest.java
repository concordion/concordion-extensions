package nz.co.twoten.concordion.footer;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;


public class TestTimeFormatterTest {
    @Test
    public void translatesSeconds() {
        assertThat(new TimestampFormatter().reformat("in 5771 ms on 24-Sep-2010 at 10:57:30 NZST"), equalTo("in 5s on 24-Sep-2010 at 10:57:30 NZST"));
    }

    @Test
    public void translatesMinutesAndSeconds() {
        assertThat(new TimestampFormatter().reformat("in 105771 ms on 24-Sep-2010 at 10:57:30 NZST"), equalTo("in 1m 45s on 24-Sep-2010 at 10:57:30 NZST"));
    }

    @Test
    public void translatesHoursMinutesAndSeconds() {
        assertThat(new TimestampFormatter().reformat("in 7654321 ms on 24-Sep-2010 at 10:57:30 NZST"), equalTo("in 2h 7m 34s on 24-Sep-2010 at 10:57:30 NZST"));
    }
}
