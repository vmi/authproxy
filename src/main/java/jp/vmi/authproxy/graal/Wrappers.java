package jp.vmi.authproxy.graal;

import java.security.Permission;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.impl.Log4jContextFactory;
import org.apache.logging.log4j.core.util.WatchManager;
import org.apache.logging.log4j.message.DefaultFlowMessageFactory;
import org.apache.logging.log4j.message.ParameterizedMessageFactory;
import org.graalvm.nativeimage.hosted.Feature;
import org.graalvm.nativeimage.hosted.RuntimeReflection;

import com.oracle.svm.core.annotate.Alias;
import com.oracle.svm.core.annotate.AutomaticFeature;
import com.oracle.svm.core.annotate.Delete;
import com.oracle.svm.core.annotate.RecomputeFieldValue;
import com.oracle.svm.core.annotate.RecomputeFieldValue.Kind;
import com.oracle.svm.core.annotate.Substitute;
import com.oracle.svm.core.annotate.TargetClass;

import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Slf4JLoggerFactory;

@TargetClass(io.netty.util.internal.PlatformDependent.class)
final class Target_io_netty_util_internal_PlatformDependent {

    @Alias
    @RecomputeFieldValue(kind = Kind.ArrayBaseOffset, declClass = byte[].class)
    private static long ARRAY_BASE_OFFSET;
}

@TargetClass(className = "io.netty.util.internal.PlatformDependent0")
final class Target_io_netty_util_internal_PlatformDependent0 {

    @Alias
    @RecomputeFieldValue(kind = Kind.FieldOffset, declClassName = "java.nio.Buffer", name = "address")
    private static long ADDRESS_FIELD_OFFSET;
}

@TargetClass(io.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess.class)
final class Target_io_netty_util_internal_shaded_org_jctools_util_UnsafeRefArrayAccess {

    @Alias
    @RecomputeFieldValue(kind = Kind.ArrayIndexShift, declClass = Object[].class)
    public static int REF_ELEMENT_SHIFT;
}

@TargetClass(className = "io.netty.util.internal.Cleaner0")
final class Target_io_netty_util_internal_Cleaner0 {

    @Alias
    @RecomputeFieldValue(kind = Kind.FieldOffset, declClassName = "java.nio.DirectByteBuffer", name = "cleaner")
    private static long CLEANER_FIELD_OFFSET;
}

@TargetClass(io.netty.util.internal.logging.InternalLoggerFactory.class)
final class Target_io_netty_util_internal_logging_InternalLoggerFactory {

    @Substitute
    public static InternalLoggerFactory getDefaultFactory() {
        return Slf4JLoggerFactory.INSTANCE;
    }
}

@TargetClass(com.lmax.disruptor.util.ThreadHints.class)
@Substitute
final class Target_com_lmax_disruptor_util_ThreadHints {

    private Target_com_lmax_disruptor_util_ThreadHints() {
        // no operation.
    }

    public static void onSpinWait() {
        // no operation.
    }
}

@TargetClass(org.apache.logging.log4j.core.config.plugins.convert.DateTypeConverter.class)
@Substitute
final class Target_org_apache_logging_log4j_core_config_plugins_convert_DateTypeConverter {

    @SuppressWarnings("unchecked")
    public static <D extends Date> D fromMillis(final long millis, final Class<D> type) {
        if (type == Date.class) {
            return (D) new Date(millis);
        } else if (type == java.sql.Date.class) {
            return (D) new java.sql.Date(millis);
        } else if (type == Time.class) {
            return (D) new Time(millis);
        } else if (type == Timestamp.class) {
            return (D) new Timestamp(millis);
        } else {
            return null;
        }
    }
}

// for slf4j

@SuppressWarnings("restriction")
@TargetClass(sun.reflect.Reflection.class)
final class Target_sun_reflect_Reflection {

    @Substitute
    public static Class<?> getCallerClass(int depth) {
        throw new UnsupportedOperationException();
    }
}

@TargetClass(org.slf4j.helpers.Util.class)
final class Target_org_slf4j_helpers_Util {

    @Substitute
    public static Class<?> getCallingClass() {
        return null;
    }
}

@TargetClass(org.apache.logging.log4j.core.script.ScriptManager.class)
@Substitute
final class Target_org_apache_logging_log4j_core_script_ScriptManager {

    public Target_org_apache_logging_log4j_core_script_ScriptManager(Configuration configuration,
        WatchManager watchManager) {
        throw new UnsupportedOperationException();
    }
}

@TargetClass(org.apache.logging.log4j.util.Activator.class)
final class Target_org_apache_logging_log4j_util_Activator {

    @Delete
    private static SecurityManager SECURITY_MANAGER;

    @Substitute
    private static void checkPermission(final Permission permission) {
        // no operation.
    }

}

@AutomaticFeature
final class RuntimeReflectionRegistrationFeature implements Feature {

    @Override
    public void beforeAnalysis(BeforeAnalysisAccess access) {
        Class<?>[] classes = new Class<?>[] {
            DefaultFlowMessageFactory.class,
            ParameterizedMessageFactory.class,
            Log4jContextFactory.class,
        };
        try {
            for (Class<?> c : classes) {
                RuntimeReflection.register(c);
                RuntimeReflection.register(c.getDeclaredConstructor());
            }
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(e);
        }
    }
}
