package org.concordion.ext.logging;

/**
 * Delivers logging messages from the underlying logging mechanism.
 */
public interface LogMessenger {
    /**
     * Get new messages that have been written to the log since the last time this method was called. 
     * @return new log messages
     */
    String getNewLogMessages();
}