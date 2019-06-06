package jp.vmi.authproxy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;

/**
 * Auth proxy repeater.
 */
public final class Main {

    private static final Logger logger = LogManager.getLogger();

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
    @SuppressWarnings("unused")
    public static void main(String[] args) throws Exception {
        Proxy proxy = Proxy.configure(args);
        if (proxy == null) {
            System.err.println("[ERROR] No proxy configuration. Abort.");
            System.exit(1);
        }
        logger.info(proxy);
        HttpProxyServer server = DefaultHttpProxyServer.bootstrap()
            .withPort(proxy.localPort)
            .withFiltersSource(new ProxyAuthorizationHandler(proxy))
            .withChainProxyManager(new ParentProxyManager(proxy))
            .start();
    }
}
