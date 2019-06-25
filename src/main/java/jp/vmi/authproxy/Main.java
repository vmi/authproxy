package jp.vmi.authproxy;

import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.vmi.authproxy.logging.LoggerConfigurator;

/**
 * Auth proxy repeater.
 */
public final class Main {

    static {
        System.setProperty("io.netty.noJavassist", "true");
        LoggerConfigurator.initialize();
    }

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * Main.
     *
     * <div>
     * Proxy configuration from:
     *
     * <ol>
     * <li>command line arguments: HOST PORT USER PASSWORD <del>[NO_PROXY ...]</del></li>
     * <li>environment variables: "http_proxy" <del>and "no_proxy"</del>.</li>
     * <li>configuration file: ./authproxy.properties</li>
     * </ol>
     *
     * Note: currently, no proxy is not supported.
     * </div>
     *
     * @param args command line arguments.
     * @throws Exception exception.
     */
    public static void main(String[] args) throws Exception {
        Proxy proxy = Proxy.configure(args);
        if (proxy == null) {
            System.err.println("[ERROR] No proxy configuration. Abort.");
            System.exit(1);
        }
        logger.info("{}", proxy);
        HttpProxyServer server = DefaultHttpProxyServer.bootstrap()
            .withPort(proxy.localPort)
            .withFiltersSource(new ProxyAuthorizationHandler(proxy))
            .withChainProxyManager(new ParentProxyManager(proxy))
            .start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> server.stop()));
        while (true) {
            Thread.sleep(Long.MAX_VALUE);
        }
    }
}
