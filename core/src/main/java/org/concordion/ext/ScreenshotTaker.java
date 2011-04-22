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
package org.concordion.ext;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Takes shots of the system under test. 
 */
public interface ScreenshotTaker {

    /**
     * Take a shot and write to the given output stream.  
     * 
     *  @return the width of the screenshot
     *  @throws IOException if an I/O error occurs writing the screenshot to the stream
     *  @throws ScreenshotUnavailableException if unable to take a screenshot 
     */
    int writeScreenshotTo(OutputStream outputStream) throws IOException;

    /**
     * Returns the filename extension that should be used for images taken by this object.
     */
    String getFileExtension();
}