package prr.exceptions;

/**
 * Client key does not exist
 */
public class UnknownTerminalKeyException extends Exception {

    /** Class serial number. */
    private static final long serialVersionUID = 202208091753L;

    /** The unknown key. */
    private String _key;

    /**
     * @param key
     */
    public UnknownTerminalKeyException(String key) {
        _key = key;
    }

    /**
     * @param key
     * @param cause
     */
    public UnknownTerminalKeyException(String key, Exception cause) {
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