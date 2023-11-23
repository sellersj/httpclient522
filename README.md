# httpclient522

Issue when updating to 5.2.2 and getting an invalid proxy

```
Exception in thread "main" java.lang.IllegalArgumentException: Invalid Proxy
  at java.net.Socket.<init>(Socket.java:120)
  at org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory.createSocket(SSLConnectionSocketFactory.java:208)
  at org.apache.hc.client5.http.impl.io.DefaultHttpClientConnectionOperator.connect(DefaultHttpClientConnectionOperator.java:158)
  at org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager.connect(PoolingHttpClientConnectionManager.java:447)
  at org.apache.hc.client5.http.impl.classic.InternalExecRuntime.connectEndpoint(InternalExecRuntime.java:162)
  at org.apache.hc.client5.http.impl.classic.InternalExecRuntime.connectEndpoint(InternalExecRuntime.java:172)
  at org.apache.hc.client5.http.impl.classic.ConnectExec.execute(ConnectExec.java:142)
  at org.apache.hc.client5.http.impl.classic.ExecChainElement.execute(ExecChainElement.java:51)
  at org.apache.hc.client5.http.impl.classic.ProtocolExec.execute(ProtocolExec.java:192)
  at org.apache.hc.client5.http.impl.classic.ExecChainElement.execute(ExecChainElement.java:51)
  at org.apache.hc.client5.http.impl.classic.HttpRequestRetryExec.execute(HttpRequestRetryExec.java:96)
  at org.apache.hc.client5.http.impl.classic.ExecChainElement.execute(ExecChainElement.java:51)
  at org.apache.hc.client5.http.impl.classic.ContentCompressionExec.execute(ContentCompressionExec.java:152)
  at org.apache.hc.client5.http.impl.classic.ExecChainElement.execute(ExecChainElement.java:51)
  at org.apache.hc.client5.http.impl.classic.RedirectExec.execute(RedirectExec.java:115)
  at org.apache.hc.client5.http.impl.classic.ExecChainElement.execute(ExecChainElement.java:51)
  at org.apache.hc.client5.http.impl.classic.InternalHttpClient.doExecute(InternalHttpClient.java:170)
  at org.apache.hc.client5.http.impl.classic.CloseableHttpClient.execute(CloseableHttpClient.java:123)
```