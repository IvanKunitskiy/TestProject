package com.nymbus.core.allure;

import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AllureLogger {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private LogType logType;

    @Step("{message}")
    private void log(String message) {
        switch (logType) {
            case WARN:
                logger.warn(message);
                break;
            case ERROR:
                logger.error(message);
                break;
            default:
                logger.info(message);
        }
    }

    protected void logInfo(String message, Object... args) {
        logType = LogType.INFO;
//        log(String.format(message, args));
        log(message);
    }

    protected void logWarn(String message, Object... args) {
        logType = LogType.WARN;
        log(String.format(message, args));
    }

    protected void logError(String message, Object... args) {
        logType = LogType.ERROR;
        log(String.format(message, args));
    }

    private enum LogType {
        INFO, WARN, ERROR
    }
}
