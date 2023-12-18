package by.melnikov.logisticbase.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomException extends Exception {
    private static final Logger logger = LogManager.getLogger();
    public CustomException() {
    }

    public CustomException(String message) {
        super(message);
        logger.error(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
        logger.error(message + cause.getMessage());
    }

    public CustomException(Throwable cause) {
        super(cause);
        logger.error(cause.getMessage());
    }
}
