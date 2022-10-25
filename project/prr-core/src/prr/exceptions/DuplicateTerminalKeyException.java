package prr.exceptions;

/**
 * Launched when already exists a terminal with that key.
 */
public class DuplicateTerminalKeyException extends Exception {

    /** Class serial number. */
    private static final long serialVersionUID = 202208091753L;

    /** The given key. */
    private String _key;

    /**
     * @param key
     */
    public DuplicateTerminalKeyException(String key) {
        _key = key;
    }

    /**
     * @param key
     * @param cause
     */
    public DuplicateTerminalKeyException(String key, Exception cause) {
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