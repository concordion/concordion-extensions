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

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.concordion.ext.footer.TimestampFormatter;
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
