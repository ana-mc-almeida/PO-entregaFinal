package prr.exceptions;

/**
 * Launched when already exists a client with that key.
 */
public class DuplicateClientKeyException extends Exception {

    /** Class serial number. */
    private static final long serialVersionUID = 202208091753L;

    /** The given key. */
    private String _key;

    /**
     * @param key
     */
    public DuplicateClientKeyException(String key) {
        _key = key;
    }

    /**
     * @param key
     * @param cause
     */
    public DuplicateClientKeyException(String key, Exception cause) {
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