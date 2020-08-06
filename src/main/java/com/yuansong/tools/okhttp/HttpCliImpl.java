package com.yuansong.tools.okhttp;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class HttpCliImpl implements IHttpCli {

	private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	private static final MediaType XML = MediaType.parse("application/xml; charset=utf-8");
	private static final MediaType FROM_DATA = MediaType.parse("multipart/form-data");
	
	@Autowired
	private OkHttpClient okHttpClient;

	@Override
	public String doGet(String url) throws Exception {
		return this.doGet(url, null, null);
	}

	@Override
	public String doGet(String url, Map<String, String> params) throws Exception {
		return this.doGet(url, params, null);
	}

	@Override
	public String doGet(String url, String[] headers) throws Exception {
		return this.doGet(url, null, headers);
	}

	@Override
	public String doGet(String url, Map<String, String> params, String[] headers) throws Exception {
		url = url + this.getHeadersStr(params);

		Request.Builder builder = new Request.Builder();
		if(headers != null && headers.length > 0) {
			if(headers.length % 2 == 0) {
				for(int i = 0; i < headers.length; i = i + 2) {
					builder.addHeader(headers[i], headers[i + 1]);
				}
			} else {
				throw new Exception("headers's length[" + String.valueOf(headers.length) + "] is error")  ;
			}
		}
		
		Request request = builder.url(url).build();
		return this.execute(request);
	}

	@Override
	public String doPost(String url, Map<String, String> params) throws Exception {
		FormBody.Builder builder = new FormBody.Builder();
		if(params != null && params.keySet().size() > 0) {
			for(String key : params.keySet()) {
				builder.add(key, params.get(key));
			}
		}
		Request request = new Request.Builder()
				.url(url)
				.post(builder.build())
				.build();
		return this.execute(request);
	}
	
//	@Override
//	public String doPost(String url, Map<String, String> params, String[] headers) throws Exception {
//		FormBody.Builder builder = new FormBody.Builder();
//		if(params != null && params.keySet().size() > 0) {
//			for(String key : params.keySet()) {
//				builder.add(key, params.get(key));
//			}
//		}
//		Request.Builder rBuilder = new Request.Builder();
//		rBuilder.url(url);
//		if(headers != null && headers.length > 0) {
//			if(headers.length % 2 == 0) {
//				for(int i = 0; i < headers.length; i = i + 2) {
//					rBuilder.addHeader(headers[i], headers[i + 1]);
//				}
//			} else {
//				throw new Exception("headers's length[" + String.valueOf(headers.length) + "] is error")  ;
//			}
//		}
//		rBuilder.post(builder.build());
//		return this.execute(rBuilder.build());
////		Request request = new Request.Builder()
////				.url(url)
////				.post(builder.build())
////				.build();
////		return this.execute(request);
//	}
	
	@Override
	public String doPostJson(String url, String json) throws Exception {
		return this.doPostJson(url, null, json);
	}
	
	@Override
	public String doPostJson(String url, String[] headers, String json) throws Exception {
		return this.exectPost(url, headers, json, JSON);
	}

	@Override
	public String doPostXml(String url, String xml) throws Exception {
		return this.doPostXml(url, null, xml);
	}
	
	@Override
	public String doPostXml(String url, String[] headers, String xml) throws Exception {
		return this.exectPost(url, headers, xml, XML);
	}

	@Override
	public String doPostMulti(String url, Map<String, String> params, String[] headers, Map<String, File> data)
			throws Exception {	
		url = url + this.getHeadersStr(params);
		
		Request.Builder builder = new Request.Builder();
		if(headers != null && headers.length > 0) {
			if(headers.length % 2 == 0) {
				for(int i = 0; i < headers.length; i = i + 2) {
					builder.addHeader(headers[i], headers[i + 1]);
				}
			} else {
				throw new Exception("headers's length[" + String.valueOf(headers.length) + "] is error")  ;
			}
		}
		
		
		MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
		bodyBuilder.setType(FROM_DATA);
		
		if(data != null && data.keySet().size() > 0) {
			for(String key : data.keySet()) {
				bodyBuilder.addFormDataPart(key, data.get(key).getName(), 
						RequestBody.create(MediaType.parse("application/octet-stream"), data.get(key)));
			}
		}
		
		Request request = builder
				.post(bodyBuilder.build())
				.url(url)
				.build();
		return this.execute(request);
		
		
	}
	
	@Override
	public String doPostMulti(String url, Map<String, File> data) throws Exception {
		return this.doPostMulti(url, null, null, data);
	}

	@Override
	public String doPostMulti(String url, String[] headers, Map<String, File> data) throws Exception {
		return this.doPostMulti(url, null, headers, data);
	}

	@Override
	public String doPostMulti(String url, Map<String, String> params, Map<String, File> data) throws Exception {
		return this.doPostMulti(url, params, null, data);
	}
	
	private String exectPost(String url, String[] headers,String data, MediaType contentType) throws Exception {
		RequestBody requestBody = RequestBody.create(contentType, data);
		
		Builder builder = new Request.Builder();
		if(headers != null && headers.length > 0) {
			if(headers.length % 2 == 0) {
				for(int i = 0; i < headers.length; i = i + 2) {
					builder.addHeader(headers[i], headers[i + 1]);
				}
			} else {
				throw new Exception("headers's length[" + String.valueOf(headers.length) + "] is error")  ;
			}
		}
		
		Request request = builder		
				.url(url)
				.post(requestBody)
				.build();
		
		return this.execute(request);
	}
	
	private String execute(Request request) throws Exception {
		Response response = null;
		try {
			response = this.okHttpClient.newCall(request).execute();
			String body = response.body().string();
			if(body != null && !"".equals(body.trim())) {
				return body;
			} else {
				throw new Exception(MessageFormat.format("[{0}]{1}", response.code(), response.message()));
			}
		} finally {
			if(response != null) {
				response.close();
			}
		}
	}
	
	private String getHeadersStr(Map<String, String> params) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		if(params != null && params.keySet().size() > 0) {
			boolean firstFlag = true;
			for(String key : params.keySet()) {
				sb.append(firstFlag?"?":"&").append(URLEncoder.encode(key, "utf-8")).append("=").append(URLEncoder.encode(params.get(key), "utf-8"));
				firstFlag = false;				
			}
		}
		return sb.toString();
	}

}
