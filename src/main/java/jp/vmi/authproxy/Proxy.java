package jp.vmi.authproxy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Proxy Configuration information.
 */
@SuppressWarnings("javadoc")
public final class Proxy {

    private static final String PROP_FILE = "authproxy.properties";
    private static final String LOCAL_PORT = "8080";

    public final int localPort;
    public final String host;
    public final int port;
    public final String user;
    public final String password;
    public final String basic;

    private static int toInt(String s) {
        return Integer.parseInt(s);
    }

    public Proxy(String localPort, String host, String port, String user, String password) {
        this.localPort = toInt(localPort);
        this.host = host;
        this.port = toInt(port);
        this.user = user;
        this.password = password;
        String raw = user + ":" + password;
        basic = "Basic " + Base64.getEncoder().encodeToString(raw.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String toString() {
        return String.format("localPort=%d, proxyHost=%s, proxyPort=%d, proxyUser=%s, proxyPassord=********",
            localPort, host, port, user);
    }

    private static String localPort(String... args) {
        if (args.length == 0) {
            return LOCAL_PORT;
        } else {
            return args[0];
        }
    }

    private static Proxy configByArgs(String... args) {
        if (args.length != 5) {
            return null;
        }
        return new Proxy(args[0], args[1], args[2], args[3], args[4]);
    }

    private static Proxy configByEnvVar(String... args) {
        String httpProxy = System.getenv("http_proxy");
        if (httpProxy == null) {
            return null;
        }
        // http://user%40domain:password@host:port/
        Matcher matcher = Pattern.compile(
            "\\w+://(?<user>[^:]+):(?<password>[^@]+)@(?<host>[^:/]+):(?<port>\\d+)/?")
            .matcher(httpProxy);
        if (!matcher.matches()) {
            return null;
        }
        String localPort = localPort(args);
        String host = matcher.group("host");
        String port = matcher.group("port");
        String user = matcher.group("user");
        String password = matcher.group("password");
        return new Proxy(localPort, host, port, user, password);
    }

    private static Proxy configByPropFile(String... args) {
        File file = new File(PROP_FILE);
        if (!file.isFile()) {
            return null;
        }
        Properties props = new Properties();
        try (InputStream is = new FileInputStream(PROP_FILE)) {
            props.load(is);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        String localPort = props.getProperty("localPort", localPort(args));
        String host = props.getProperty("proxyHost");
        String port = props.getProperty("proxyPort");
        String user = props.getProperty("proxyUser");
        String password = props.getProperty("proxyPassword");
        return new Proxy(localPort, host, port, user, password);
    }

    public static Proxy configure(String... args) {
        Proxy proxy;
        if ((proxy = configByArgs(args)) != null) {
            return proxy;
        }
        if ((proxy = configByEnvVar(args)) != null) {
            return proxy;
        }
        return configByPropFile(args);
    }
}
