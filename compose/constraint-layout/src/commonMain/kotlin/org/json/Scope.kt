package org.json


/**
 * Lexical scoping elements within this stringer, necessary to insert the
 * appropriate separator characters (ie. commas and colons) and to detect
 * nesting errors.
 */
enum class Scope {
    /**
     * An array with no elements requires no separators or newlines before
     * it is closed.
     */
    EMPTY_ARRAY,

    /**
     * A array with at least one value requires a comma and newline before
     * the next element.
     */
    NONEMPTY_ARRAY,

    /**
     * An object with no keys or values requires no separators or newlines
     * before it is closed.
     */
    EMPTY_OBJECT,

    /**
     * An object whose most recent element is a key. The next element must
     * be a value.
     */
    DANGLING_KEY,

    /**
     * An object with at least one name/value pair requires a comma and
     * newline before the next element.
     */
    NONEMPTY_OBJECT,

    /**
     * A special bracketless array needed by JSONStringer.join() and
     * JSONObject.quote() only. Not used for JSON encoding.
     */
    NULL
}