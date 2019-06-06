# authproxy

The proxy server to relay to authentication proxy.

## Usage

There are three ways to specify the parent proxy server.

### By command line

`authproxy` _HOST_ _PORT_ _USER_ _PASSWORD_ ~[_NO_PROXY_ ...]~

Note: Currently, NO_PROXY is not supported yet.

### By `http_proxy` environment variable

The format is as follows:

`http_proxy=http://`_USER_`:`_PASSWORD_`@`_HOST_`:`_PORT_`/`

### By `authproxy.properties` file in current directory

The format is as follows:

proxy.properties
```
proxyHost=HOST
proxyPort=PORT
proxyUser=USER
proxyPassword=PASSWORD
```
