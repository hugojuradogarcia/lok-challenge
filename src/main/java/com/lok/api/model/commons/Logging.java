package com.lok.api.model.commons;

import org.slf4j.Logger;

public class Logging {
    private static final String FLAG_DEBUG = " ---------- ";
    private static final String FLAG_WARN = " ^^^^^^^^^^ ";
    private static final String FLAG_INFO = " ********** ";
    private static final String FLAG_ERROR = " ########## ";

    // Debug Level
    public static void debug(Logger logger, String message) {
        logger.debug(FLAG_DEBUG + message + FLAG_DEBUG);
    }

    // Warning Level
    public static void warn(Logger logger, String message) {
        logger.warn(FLAG_WARN + message + FLAG_WARN);
    }

    // Info Level
    public static void info(Logger logger, String message) {
        logger.info(FLAG_INFO + message + FLAG_INFO);
    }

    // Error Level
    public static void error(Logger Logger, String message) {
        Logger.error(FLAG_ERROR + message + FLAG_ERROR);
    }
}
