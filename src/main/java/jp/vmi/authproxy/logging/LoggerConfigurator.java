package jp.vmi.authproxy.logging;

import java.net.URI;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

public class LoggerConfigurator extends ConfigurationFactory {

    private static final String[] SUPPORTED_TYPES = { "*" };

    public static void initialize() {
        ConfigurationFactory factory = new LoggerConfigurator();
        ConfigurationFactory.setConfigurationFactory(factory);
    }

    @Override
    protected String[] getSupportedTypes() {
        return SUPPORTED_TYPES;
    }

    private Configuration getConfiguration() {
        ConfigurationBuilder<BuiltConfiguration> builder = super.newConfigurationBuilder();
        AppenderComponentBuilder console = builder.newAppender("stdout", "Console");
        LayoutComponentBuilder patternLayout = builder.newLayout("PatternLayout");
        patternLayout.addAttribute("pattern", "[%d{yyyy-MM-dd HH:mm:ss.SSS}] %-5p %m%n");
        console.add(patternLayout);
        builder.add(console);
        RootLoggerComponentBuilder rootLogger = builder.newRootLogger(Level.INFO);
        rootLogger.add(builder.newAppenderRef("stdout"));
        builder.add(rootLogger);
        return builder.build();
    }

    @Override
    public Configuration getConfiguration(LoggerContext loggerContext, String name, URI configLocation,
        ClassLoader loader) {
        return getConfiguration();
    }

    @Override
    public Configuration getConfiguration(LoggerContext loggerContext, ConfigurationSource source) {
        return getConfiguration();
    }
}
