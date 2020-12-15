package com.yuansong.tools.okhttp;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import okhttp3.ConnectionPool;
import okhttp3.Cookie;
import okhttp3.OkHttpClient;

@Configuration 
public class OkHttpConfiguration {

	@Bean
	public Map<String, List<Cookie>> getCookieStore() {
		return new HashMap<String, List<Cookie>>();
	}
	
	@Bean
	public OkHttpClient okHttpClient() {
		return new OkHttpClient.Builder()
				.sslSocketFactory(sslSocketFactory(), x509TrustManager())
				.retryOnConnectionFailure(false)
				.connectionPool(pool())
				.connectTimeout(10, TimeUnit.SECONDS)
				.readTimeout(30, TimeUnit.SECONDS)
				.writeTimeout(30, TimeUnit.SECONDS)
				.hostnameVerifier((hostname, seesion) -> true)
//				.cookieJar(new CookieJar() {
//					@Override
//					public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
//						getCookieStore().put(url.host(), cookies);
//					}
//					@Override
//					public List<Cookie> loadForRequest(HttpUrl url) {
//						List<Cookie> cookies = getCookieStore().get(url.host());
//			            return cookies != null ? cookies : new ArrayList<Cookie>();
//					}
//				})
//				设置代理
//				.proxy(proxy)
//				拦截器
//				.addInterceptor(interceptor)
				.build();
	}
	
	@Bean
	public X509TrustManager x509TrustManager() {
		return new X509TrustManager() {

			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) 
					throws CertificateException {
				
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) 
					throws CertificateException {
				
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}
			
		};
	}
	
	@Bean
	public SSLSocketFactory sslSocketFactory() {
		try {
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, new TrustManager[] {x509TrustManager()}, new SecureRandom());
			return sslContext.getSocketFactory();
		} catch(NoSuchAlgorithmException | KeyManagementException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Bean
	public ConnectionPool pool() {
//		return new ConnectionPool(5, 5, TimeUnit.MINUTES);
		return new ConnectionPool(5, 3, TimeUnit.SECONDS);
	}
	
}
