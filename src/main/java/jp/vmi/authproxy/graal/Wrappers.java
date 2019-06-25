package jp.vmi.authproxy.graal;

import com.oracle.svm.core.annotate.Substitute;
import com.oracle.svm.core.annotate.TargetClass;

@SuppressWarnings("restriction")
@TargetClass(sun.reflect.Reflection.class)
final class sun_reflect_Reflection {

    @Substitute
    public static Class<?> getCallerClass(int depth) {
        throw new UnsupportedOperationException();
    }
}

@TargetClass(org.slf4j.helpers.Util.class)
final class org_slf4j_helpers_Util {

    @Substitute
    public static Class<?> getCallingClass() {
        return null;
    }
}
