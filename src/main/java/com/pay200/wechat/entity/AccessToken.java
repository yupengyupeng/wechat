package com.pay200.wechat.entity;
/** 
* @author yupeng
* @version 创建时间：2018年12月11日 上午11:49:01 
* 类说明 
*/
public class AccessToken {
	 private String token;
	    private int expireIn;
	 
	    public String getToken() {
	        return token;
	    }
	    public void setToken(String token) {
	        this.token = token;
	    }
	    public int getExpireIn() {
	        return expireIn;
	    }
	    public void setExpireIn(int expireIn) {
	        this.expireIn = expireIn;
	    }
}
