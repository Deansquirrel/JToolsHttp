package com.yuansong.tools.okhttp;

import java.io.File;
import java.util.Map;

public interface IHttpUtil {

	/**
	 * get 请求
	 * @param url 请求地址
	 * @param headers 请求头字段（k1, v1, k2, v2）
	 * @param params 请求参数
	 * @return
	 */
	public String doGet(String url, String[] headers, Map<String, String> params) throws Exception;
	
	default public String doGet(String url, String[] headers) throws Exception {
		return this.doGet(url, headers, null);
	}
	
	default public String doGet(String url, Map<String, String> params) throws Exception {
		return this.doGet(url, null, params);
	}
	
	default public String doGet(String url) throws Exception {
		return this.doGet(url, null, null);
	}

	/**
	 * post 表单提交
	 * @param url
	 * @param headers
	 * @param params
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public String doPostForm(String url, String[] headers, Map<String, String> params, Map<String, String> form) throws Exception;
	
	default public String doPostForm(String url, Map<String, String> params, Map<String, String> form) throws Exception {
		return this.doPostForm(url, null, params, form);
	}
	
	default public String doPostForm(String url, String[] headers, Map<String, String> form) throws Exception {
		return this.doPostForm(url, headers, null, form);
	}
	
	default public String doPostForm(String url, Map<String, String> form) throws Exception {
		return this.doPostForm(url, null, null, form);
	}

	/**
	 * post JSON提交
	 * @param url
	 * @param headers
	 * @param params
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public String doPostJson(String url, String[] headers, Map<String, String> params, String json) throws Exception;
	
	default public String doPostJson(String url, Map<String, String> params, String json) throws Exception {
		return this.doPostJson(url, null, params, json);
	}
	
	default public String doPostJson(String url, String[] headers, String json) throws Exception {
		return this.doPostJson(url, headers, null, json);
	}
	default public String doPostJson(String url, String json) throws Exception {
		return this.doPostJson(url, null, null, json);
	}
	
	/**
	 * post XML提交
	 * @param url
	 * @param headers
	 * @param params
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public String doPostXml(String url, String[] headers, Map<String, String> params, String xml) throws Exception;
	
	default public String doPostXml(String url, Map<String, String> params, String xml) throws Exception {
		return this.doPostXml(url, null, params, xml);
	}
	default public String doPostXml(String url, String[] headers, String xml) throws Exception {
		return this.doPostXml(url, headers, null, xml);
	}
	default public String doPostXml(String url, String xml) throws Exception {
		return this.doPostXml(url, null, null, xml);
	}
	
	/**
	 * post File提交
	 * @param url
	 * @param params
	 * @param headers
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String doPostMulti(String url, String[] headers, Map<String, String> params, Map<String, File> data) 	throws Exception;
	default public String doPostMulti(String url, Map<String, String> params, Map<String, File> data) 	throws Exception {
		return this.doPostMulti(url, null, params, data);
	}
	default public String doPostMulti(String url, String[] headers, Map<String, File> data) 	throws Exception {
		return this.doPostMulti(url, headers, null, data);
	}
	default public String doPostMulti(String url, Map<String, File> data) 	throws Exception {
		return this.doPostMulti(url, null, null, data);
	}
}
