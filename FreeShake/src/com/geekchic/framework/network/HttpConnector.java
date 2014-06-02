/**
 * @Title: HttpConnector.java
 * @Package com.geekchic.framework.network
 * @Description: 网络操作管理类
 * @author: evil
 * @date: May 2, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.framework.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpStatus;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;

import com.geekchic.common.log.Logger;
import com.geekchic.framework.bean.HttpRequestBean;
import com.geekchic.framework.bean.HttpRequestBean.Method;
import com.geekchic.framework.network.exception.ConnectionException;

/**
 * @ClassName: HttpConnector
 * @Descritpion: 网络操作管理类
 * @author evil
 * @date May 2, 2014
 */
public class HttpConnector {
	private static final String TAG = "HttpConnector";
	/**
	 * HTTP超时
	 */
	private static final int HTTP_TIME_OUT = (int) TimeUnit.SECONDS
			.toMillis(60L);
	private static final String ACCEPT_CHARSET_HEADER = "Accept-Charset";
	private static final String ACCEPT_ENCODING_HEADER = "Accept-Encoding";
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String LOCATION_HEADER = "Location";

	private static final String UTF8_CHARSET = "UTF-8";

	public static final class ConnectionResult {

		public final Map<String, List<String>> headerMap;
		public final String body;

		public ConnectionResult(Map<String, List<String>> headerMap, String body) {
			this.headerMap = headerMap;
			this.body = body;
		}
	}

	/**
	 * HttpManager构造函数
	 */
	private HttpConnector() {
	}

	public static ConnectionResult execute(HttpRequestBean httpBean) throws ConnectionException {
		return execute(httpBean.getContext(), httpBean.getUrl(),
				httpBean.getMethod(), httpBean.getParameterList(),
				httpBean.getHeaderMap(), httpBean.isGzipEnabled(),
				httpBean.getUserAgent(), httpBean.getPostText(),
				httpBean.getCredentials(), httpBean.isSslValidationEnabled());
	}

	public static ConnectionResult execute(Context context, String urlValue,
			Method method, ArrayList<BasicNameValuePair> parameterList,
			HashMap<String, String> headerMap, boolean isGzipEnabled,
			String userAgent, String postText,
			UsernamePasswordCredentials credentials,
			boolean isSslValidationEnabled) throws ConnectionException {
		HttpURLConnection connection = null;
		try {
			// Prepare the request information
			if (userAgent == null) {
				userAgent = UserAgentUtils.get(context);
			}
			if (headerMap == null) {
				headerMap = new HashMap<String, String>();
			}
			headerMap.put(HTTP.USER_AGENT, userAgent);
			if (isGzipEnabled) {
				headerMap.put(ACCEPT_ENCODING_HEADER, "gzip");
			}
			headerMap.put(ACCEPT_CHARSET_HEADER, UTF8_CHARSET);
			if (credentials != null) {
				headerMap.put(AUTHORIZATION_HEADER,
						createAuthenticationHeader(credentials));
			}

			StringBuilder paramBuilder = new StringBuilder();
			if (parameterList != null && !parameterList.isEmpty()) {
				for (int i = 0, size = parameterList.size(); i < size; i++) {
					BasicNameValuePair parameter = parameterList.get(i);
					String name = parameter.getName();
					String value = parameter.getValue();
					if (TextUtils.isEmpty(name)) {
						// Empty parameter name. Check the next one.
						continue;
					}
					if (value == null) {
						value = "";
					}
					paramBuilder.append(URLEncoder.encode(name, UTF8_CHARSET));
					paramBuilder.append("=");
					paramBuilder.append(URLEncoder.encode(value, UTF8_CHARSET));
					paramBuilder.append("&");
				}
			}

			// Log the request
			Logger.d(TAG, "Request url: " + urlValue);
			Logger.d(TAG, "Method: " + method.toString());

			if (parameterList != null && !parameterList.isEmpty()) {
				Logger.d(TAG, "Parameters:");
				for (int i = 0, size = parameterList.size(); i < size; i++) {
					BasicNameValuePair parameter = parameterList.get(i);
					String message = "- \"" + parameter.getName() + "\" = \""
							+ parameter.getValue() + "\"";
					Logger.d(TAG, message);
				}

				Logger.d(TAG, "Parameters String: \"" + paramBuilder.toString()
						+ "\"");
			}

			if (postText != null) {
				Logger.d(TAG, "Post data: " + postText);
			}

			if (headerMap != null && !headerMap.isEmpty()) {
				Logger.d(TAG, "Headers:");
				for (Entry<String, String> header : headerMap.entrySet()) {
					Logger.d(TAG,
							"- " + header.getKey() + " = " + header.getValue());
				}
			}
			// Create the connection object
			URL url = null;
			String outputText = null;
			switch (method) {
			case GET:
			case DELETE:
				String fullUrlValue = urlValue;
				if (paramBuilder.length() > 0) {
					fullUrlValue += "?" + paramBuilder.toString();
				}
				url = new URL(fullUrlValue);
				connection = HttpUrlConnectionHelper.openUrlConnection(url);
				break;
			case PUT:
			case POST:
				url = new URL(urlValue);
				connection = HttpUrlConnectionHelper.openUrlConnection(url);
				connection.setDoOutput(true);

				if (paramBuilder.length() > 0) {
					outputText = paramBuilder.toString();
					headerMap.put(HTTP.CONTENT_TYPE,
							"application/x-www-form-urlencoded");
					headerMap.put(HTTP.CONTENT_LEN,
							String.valueOf(outputText.getBytes().length));
				} else if (postText != null) {
					outputText = postText;
				}
				break;
			}

			// Set the request method
			connection.setRequestMethod(method.toString());

			// If it's an HTTPS request and the SSL Validation is disabled
			if (url.getProtocol().equals("https") && !isSslValidationEnabled) {
				HttpsURLConnection httpsConnection = (HttpsURLConnection) connection;
				httpsConnection
						.setSSLSocketFactory(getAllHostsValidSocketFactory());
				httpsConnection.setHostnameVerifier(getAllHostsValidVerifier());
			}

			// Add the headers
			if (!headerMap.isEmpty()) {
				for (Entry<String, String> header : headerMap.entrySet()) {
					connection.addRequestProperty(header.getKey(),
							header.getValue());
				}
			}

			// Set the connection and read timeout
			connection.setConnectTimeout(HTTP_TIME_OUT);
			connection.setReadTimeout(HTTP_TIME_OUT);

			// Set the outputStream content for POST and PUT requests
			if ((method == Method.POST || method == Method.PUT)
					&& outputText != null) {
				OutputStream output = null;
				try {
					output = connection.getOutputStream();
					output.write(outputText.getBytes());
				} finally {
					if (output != null) {
						try {
							output.close();
						} catch (IOException e) {
							// Already catching the first IOException so nothing
							// to do here.
						}
					}
				}
			}

			String contentEncoding = connection
					.getHeaderField(HTTP.CONTENT_ENCODING);

			int responseCode = connection.getResponseCode();
			boolean isGzip = contentEncoding != null
					&& contentEncoding.equalsIgnoreCase("gzip");
			Logger.d(TAG, "Response code: " + responseCode);

			if (responseCode == HttpStatus.SC_MOVED_PERMANENTLY) {
				String redirectionUrl = connection
						.getHeaderField(LOCATION_HEADER);
				throw new ConnectionException("New location : "
						+ redirectionUrl, redirectionUrl);
			}

			InputStream errorStream = connection.getErrorStream();
			if (errorStream != null) {
				String error = convertStreamToString(errorStream, isGzip);
				throw new ConnectionException(error, responseCode);
			}

			String body = convertStreamToString(connection.getInputStream(),
					isGzip);

			Logger.v(TAG, "Response body: ");

			int pos = 0;
			int bodyLength = body.length();
			while (pos < bodyLength) {
				Logger.v(TAG, body.substring(pos,
						Math.min(bodyLength - 1, pos + 200)));
				pos = pos + 200;
			}
             ConnectionResult result=new ConnectionResult(connection.getHeaderFields(), body);
			 return result;
		} catch (IOException e) {
			Logger.e(TAG, "IOException", e);
			throw new ConnectionException(e);
		} catch (KeyManagementException e) {
			Logger.e(TAG, "KeyManagementException", e);
			throw new ConnectionException(e);
		} catch (NoSuchAlgorithmException e) {
			Logger.e(TAG, "NoSuchAlgorithmException", e);
			throw new ConnectionException(e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	private static String createAuthenticationHeader(
			UsernamePasswordCredentials credentials) {
		StringBuilder sb = new StringBuilder();
		sb.append(credentials.getUserName()).append(":")
				.append(credentials.getPassword());
		return "Basic "
				+ Base64.encodeToString(sb.toString().getBytes(),
						Base64.NO_WRAP);
	}
	private static SSLSocketFactory sAllHostsValidSocketFactory;

	private static SSLSocketFactory getAllHostsValidSocketFactory()
			throws NoSuchAlgorithmException, KeyManagementException {
		if (sAllHostsValidSocketFactory == null) {
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs,
						String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs,
						String authType) {
				}
			} };

			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			sAllHostsValidSocketFactory = sc.getSocketFactory();
		}

		return sAllHostsValidSocketFactory;
	}

	private static HostnameVerifier sAllHostsValidVerifier;

	private static HostnameVerifier getAllHostsValidVerifier() {
		if (sAllHostsValidVerifier == null) {
			sAllHostsValidVerifier = new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};
		}

		return sAllHostsValidVerifier;
	}

	private static String convertStreamToString(InputStream is,
			boolean isGzipEnabled) throws IOException {
		InputStream cleanedIs = is;
		if (isGzipEnabled) {
			cleanedIs = new GZIPInputStream(is);
		}

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(cleanedIs,
					UTF8_CHARSET));
			StringBuilder sb = new StringBuilder();
			for (String line; (line = reader.readLine()) != null;) {
				sb.append(line);
				sb.append("\n");
			}

			return sb.toString();
		} finally {
			if (reader != null) {
				reader.close();
			}

			cleanedIs.close();

			if (isGzipEnabled) {
				is.close();
			}
		}
	}
}
