package com.pay200.wechat.entity;
/** 
* @author yupeng
* @version 创建时间：2018年12月11日 下午2:48:22 
* 类说明 
*/
public class WeiXinToken {
	private WeiXinToken() {}  
    private  String token;  
    private static WeiXinToken instance  = new WeiXinToken();
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public static WeiXinToken getInstance() {
		return instance;
	}
	public static void setInstance(WeiXinToken instance) {
		WeiXinToken.instance = instance;
	}  
   
}
