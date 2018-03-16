package jp.vmi.authproxy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersAdapter;
import org.littleshoot.proxy.HttpFiltersSourceAdapter;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

/**
 * Add "Proxy-Authorization" header to request.
 */
public class ProxyAuthorizationHandler extends HttpFiltersSourceAdapter {

    private static final Logger logger = LogManager.getLogger();

    private final Proxy proxy;

    private class RequestHandler extends HttpFiltersAdapter {

        private RequestHandler(HttpRequest originalRequest) {
            super(originalRequest);
        }

        @Override
        public HttpResponse proxyToServerRequest(HttpObject httpObject) {
            if (httpObject instanceof HttpRequest) {
                HttpRequest request = (HttpRequest) httpObject;
                HttpHeaders.addHeader(request, "Proxy-Authorization", proxy.basic);
                logger.info("Request to parent: {} {}", request.getMethod(), request.getUri());
            }
            return null;
        }

        @Override
        public void proxyToServerRequestSending() {
            logger.info("Sending request.");
        }

        @Override
        public void proxyToServerRequestSent() {
            logger.info("Sending request is complete.");
        }

        @Override
        public void serverToProxyResponseReceiving() {
            logger.info("Receiving response.");
        }

        @Override
        public void serverToProxyResponseReceived() {
            logger.info("Receiving response is complete.");
        }
    }

    /**
     * Constructor.
     *
     * @param proxy proxy configuration.
     */
    public ProxyAuthorizationHandler(Proxy proxy) {
        this.proxy = proxy;
    }

    @Override
    public HttpFilters filterRequest(HttpRequest originalRequest) {
        return new RequestHandler(originalRequest);
    }
}
