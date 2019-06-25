package jp.vmi.authproxy;

import java.net.InetSocketAddress;
import java.util.Queue;

import org.littleshoot.proxy.ChainedProxy;
import org.littleshoot.proxy.ChainedProxyAdapter;
import org.littleshoot.proxy.ChainedProxyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.handler.codec.http.HttpRequest;

/**
 * Parent proxy manager.
 */
public class ParentProxyManager implements ChainedProxyManager {

    private static final Logger logger = LoggerFactory.getLogger(ParentProxyManager.class);

    private final Proxy proxy;

    private class ParentProxy extends ChainedProxyAdapter {

        @Override
        public InetSocketAddress getChainedProxyAddress() {
            logger.info("Lookup proxy server: {}:{}", proxy.host, proxy.port);
            return new InetSocketAddress(proxy.host, proxy.port);
        }

        @Override
        public void connectionSucceeded() {
            logger.info("Connected proxy server.");
        }

        @Override
        public void connectionFailed(Throwable cause) {
            logger.warn("Connection failed to proxy server.", cause);
        }

        @Override
        public void disconnected() {
            logger.info("Disconnected proxy server.");
        }
    }

    /**
     * Constructor.
     *
     * @param proxy proxy configuration.
     */
    public ParentProxyManager(Proxy proxy) {
        this.proxy = proxy;
    }

    @Override
    public void lookupChainedProxies(HttpRequest httpRequest, Queue<ChainedProxy> chainedProxies) {
        chainedProxies.add(new ParentProxy());
    }
}
