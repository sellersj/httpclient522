package com.example;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.DefaultHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.http.io.SocketConfig;

/**
 */
public class IssueWith522 {

    /** Determines the timeout in milliseconds until a connection is established. */
    private static final long CONNECT_TIMEOUT = 30000L;

    /** Returns the timeout in milliseconds used when requesting a connection from the connection manager. */
    private static final long REQUEST_TIMEOUT = 30000L;

    /**
     * Defines the socket timeout (SO_TIMEOUT) in milliseconds, which is the timeout for waiting for data or, put
     * differently, a maximum period inactivity between two consecutive data packets).
     */
    private static final int SOCKET_TIMEOUT = 60000;

    public static void main(String[] args) throws Exception {
        // work around for websphere to contact Let's Encrypt endpoints because of how WAS manages certs
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory( //
            HttpsURLConnection.getDefaultSSLSocketFactory(), //
            new DefaultHostnameVerifier());

        // websphere work around
        Registry<ConnectionSocketFactory> socketFactoryRegistry = //
            RegistryBuilder.<ConnectionSocketFactory> create() //
                .register("https", sslsf) //
                .register("http", new PlainConnectionSocketFactory()) //
                .build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
            socketFactoryRegistry);

        // Set soTimeout here to affect socketRead in the phase of ssl handshake. Note that
        // the RequestConfig.setSocketTimeout will take effect only after the ssl handshake completed.
        connectionManager
            .setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(SOCKET_TIMEOUT, TimeUnit.MILLISECONDS).build());

        HttpClientBuilder builder = HttpClients.custom();
        builder.setConnectionManager(connectionManager);
        builder.useSystemProperties();

        // set overall timeouts for all requests
        RequestConfig.Builder requestBuilder = RequestConfig.custom();
        requestBuilder.setConnectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        requestBuilder.setConnectionRequestTimeout(REQUEST_TIMEOUT, TimeUnit.MILLISECONDS);
        builder.setDefaultRequestConfig(requestBuilder.build());

        CloseableHttpClient httpClient = builder.build();

        HttpGet getMethod = new HttpGet("https://www.google.com/");
        try (CloseableHttpResponse response = httpClient.execute(getMethod);) {
            System.out.println(String.format("The status is %s for %s", response.getCode(), getMethod));
        }
    }

}
