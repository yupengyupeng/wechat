package com.pay200.wechat.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.pay200.wechat.entity.AccessToken;

/** 
* @author yupeng
* @version 创建时间：2018年12月11日 上午11:33:03 
* 类说明 
*/
public class WeiXinUserInfoUtils {
	private static final String GET_USERINFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	 
    /**
     * 获取微信用户账号的相关信息
     * @param opendID  用户的openId，这个通过当用户进行了消息交互的时候，才有
     * @return
     */
    public static String getUserInfo(String opendID){
        AccessToken accessToken = WeiXinUtils.getAccessToken();
        //获取access_token
        String token = accessToken.getToken();
        String url = GET_USERINFO_URL.replace("ACCESS_TOKEN" , token);
        url = url.replace("OPENID" ,opendID);
        JSONObject jsonObject = WeiXinUtils.doGetStr(url);
        return jsonObject.toString();
    }
}
