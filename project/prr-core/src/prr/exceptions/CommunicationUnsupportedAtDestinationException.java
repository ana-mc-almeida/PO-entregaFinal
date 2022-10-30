package prr.exceptions;

public class CommunicationUnsupportedAtDestinationException extends Exception {
    /** Class serial number. */
    private static final long serialVersionUID = 202208091753L;

    /** The given key. */
    private String _key;
    /** The given type. */
    private String _type;

    /**
     * @param key
     * @param tupe
     */
    public CommunicationUnsupportedAtDestinationException(String key, String type) {
        _key = key;
        _type = type;
    }

    /**
     * @param key
     * @param type
     * @param cause
     */
    public CommunicationUnsupportedAtDestinationException(String key, String type, Exception cause) {
        super(cause);
        _key = key;
        _type = type;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return _key;
    }

    /**
     * @return the type
     */
    public String getType() {
        return _type;
    }
}
