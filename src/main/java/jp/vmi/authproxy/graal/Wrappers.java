package jp.vmi.authproxy.graal;

import org.graalvm.nativeimage.hosted.Feature;
import org.graalvm.nativeimage.hosted.RuntimeReflection;

import com.oracle.svm.core.annotate.Alias;
import com.oracle.svm.core.annotate.AutomaticFeature;
import com.oracle.svm.core.annotate.RecomputeFieldValue;
import com.oracle.svm.core.annotate.RecomputeFieldValue.Kind;
import com.oracle.svm.core.annotate.Substitute;
import com.oracle.svm.core.annotate.TargetClass;

import ch.qos.logback.classic.pattern.DateConverter;
import ch.qos.logback.classic.pattern.LevelConverter;
import ch.qos.logback.classic.pattern.LineSeparatorConverter;
import ch.qos.logback.classic.pattern.MessageConverter;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Slf4JLoggerFactory;

@TargetClass(className = "io.netty.util.internal.PlatformDependent")
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

@TargetClass(className = "io.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess")
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

@TargetClass(className = "io.netty.util.internal.logging.InternalLoggerFactory")
final class Target_io_netty_util_internal_logging_InternalLoggerFactory {

    @Substitute
    public static InternalLoggerFactory getDefaultFactory() {
        return Slf4JLoggerFactory.INSTANCE;
    }
}

// for logback

@TargetClass(org.slf4j.helpers.Util.class)
final class Target_org_slf4j_helpers_Util {

    @Substitute
    public static Class<?> getCallingClass() {
        return null;
    }
}

@AutomaticFeature
final class RuntimeReflectionRegistrationFeature implements Feature {

    @Override
    public void beforeAnalysis(BeforeAnalysisAccess access) {
        Class<?>[] converters = new Class[] {
            DateConverter.class,
            LevelConverter.class,
            LineSeparatorConverter.class,
            MessageConverter.class
        };
        try {
            for (Class<?> converter : converters) {
                RuntimeReflection.register(converter);
                RuntimeReflection.register(converter.getDeclaredConstructor());
            }
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(e);
        }
    }
}
