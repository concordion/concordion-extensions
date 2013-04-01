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
package test.concordion;

import java.util.ArrayList;
import java.util.List;

import org.concordion.api.listener.AssertEqualsListener;
import org.concordion.api.listener.AssertFailureEvent;
import org.concordion.api.listener.AssertSuccessEvent;
import org.concordion.api.listener.ThrowableCaughtEvent;
import org.concordion.api.listener.ThrowableCaughtListener;

public class EventRecorder implements AssertEqualsListener, ThrowableCaughtListener {

    private List<Object> events = new ArrayList<Object>();

    public void failureReported(AssertFailureEvent event) {
        events.add(event);
    }

    public void successReported(AssertSuccessEvent event) {
        events.add(event);
    }

    public void throwableCaught(ThrowableCaughtEvent event) {
        events.add(event);
    }

    public Object getLast(Class<?> eventClass) {
        Object lastMatch = null;
        for (Object object : events) {
            if (eventClass.isAssignableFrom(object.getClass())) {
                lastMatch = object;
            }
        }
        return lastMatch;
    }


}
