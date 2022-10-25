package prr.exceptions;

/**
 * Launched when a terminal's key isn't in the proper format.
 */
public class InvalidTerminalKeyException extends Exception {

    /** Class serial number. */
    private static final long serialVersionUID = 202208091753L;

    /** The given key. */
    private String _key;

    /**
     * @param key
     */
    public InvalidTerminalKeyException(String key) {
        _key = key;
    }

    /**
     * @param key
     * @param cause
     */
    public InvalidTerminalKeyException(String key, Exception cause) {
        super(cause);
        _key = key;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return _key;
    }

}