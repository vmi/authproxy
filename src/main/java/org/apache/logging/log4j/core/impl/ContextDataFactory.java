/*
 *
 */
package org.apache.logging.log4j.core.impl;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.util.ReadOnlyStringMap;
import org.apache.logging.log4j.util.SortedArrayStringMap;
import org.apache.logging.log4j.util.StringMap;

public final class ContextDataFactory {

    private static final StringMap EMPTY_STRING_MAP;

    static {
        EMPTY_STRING_MAP = createContextData(0);
        EMPTY_STRING_MAP.freeze();
    }

    private ContextDataFactory() {
        // no operation.
    }

    public static StringMap createContextData() {
        return new SortedArrayStringMap();
    }

    public static StringMap createContextData(final int initialCapacity) {
        return new SortedArrayStringMap(initialCapacity);
    }

    public static StringMap createContextData(final Map<String, String> context) {
        final StringMap contextData = createContextData(context.size());
        for (final Entry<String, String> entry : context.entrySet()) {
            contextData.putValue(entry.getKey(), entry.getValue());
        }
        return contextData;
    }

    public static StringMap createContextData(final ReadOnlyStringMap readOnlyStringMap) {
        final StringMap contextData = createContextData(readOnlyStringMap.size());
        contextData.putAll(readOnlyStringMap);
        return contextData;
    }

    public static StringMap emptyFrozenContextData() {
        return EMPTY_STRING_MAP;
    }
}
