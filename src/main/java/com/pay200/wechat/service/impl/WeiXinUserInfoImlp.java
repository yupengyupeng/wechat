package com.pay200.wechat.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.pay200.wechat.entity.WeiXinUser;
import com.pay200.wechat.service.WeiXinUserInfoService;
import com.pay200.wechat.util.ProjectConst;
import com.pay200.wechat.util.WeiXinUtils;

/** 
* @author yupeng
* @version 创建时间：2018年12月11日 上午11:56:54 
* 类说明 
*/
@Service
public class WeiXinUserInfoImlp implements WeiXinUserInfoService{
	/**
     * 获取微信用户的信息
     * @param accessToken
     * @param openId
     * @return
     */
    @Override
    public WeiXinUser getUserInfo(String accessToken, String openId) {
        WeiXinUser weixinUserInfo = null;
        // 拼接获取用户信息接口的请求地址
        String requestUrl = ProjectConst.GET_WEIXIN_USER_URL.replace("ACCESS_TOKEN", accessToken).replace(
                "OPENID", openId);
        // 获取用户信息(返回的是Json格式内容)
        JSONObject jsonObject = WeiXinUtils.doGetStr(requestUrl);
       System.out.println("==="+jsonObject.toJSONString());
        if (null != jsonObject) {
            try {
                //封装获取到的用户信息
                weixinUserInfo = new WeiXinUser();
                // 用户的标识
                weixinUserInfo.setOpenId(jsonObject.getString("openid"));
                // 昵称
                weixinUserInfo.setNickname(jsonObject.getString("nickname"));
                // 用户的性别（1是男性，2是女性，0是未知）
                weixinUserInfo.setSex(jsonObject.getInteger("sex"));
                // 用户所在国家
                weixinUserInfo.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                weixinUserInfo.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                weixinUserInfo.setCity(jsonObject.getString("city"));
                // 用户头像
                weixinUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
            } catch (Exception e) {
                if (0 == weixinUserInfo.getSubscribe()) {
                    System.out.println("用户并没有关注本公众号");
                } else {
                    int errorCode = jsonObject.getInteger("errcode");
                    String errorMsg = jsonObject.getString("errmsg");
                    System.out.println("由于"+errorCode +"错误码；错误信息为："+errorMsg+"；导致获取用户信息失败");
                }
            }
        }
        return weixinUserInfo;
    }
 
    /**
     * 进行用户授权，获取到需要的授权字段，比如openId
     * @param code 识别得到用户id必须的一个值
     * 得到网页授权凭证和用户id
     * @return
     */
    @Override
    public Map<String, String> oauth2GetOpenid(String code) {
    	System.out.println("code"+code);
        //自己的配置appid（公众号进行查阅）
        String appid = ProjectConst.PROJECT_APPID;
        //自己的配置APPSECRET;（公众号进行查阅）
        String appsecret = ProjectConst.PROJECT_APPSECRET;
        //拼接用户授权接口信息
        String requestUrl = ProjectConst.GET_WEBAUTH_URL.replace("APPID", appid).replace("SECRET", appsecret).replace("CODE", code);
        //存储获取到的授权字段信息
        Map<String, String> result = new HashMap<String, String>();
        try {
            JSONObject OpenidJSONO = WeiXinUtils.doGetStr(requestUrl);
            System.out.println("OpenidJSONO"+OpenidJSONO.toJSONString());
            //OpenidJSONO可以得到的内容：access_token expires_in  refresh_token openid scope
            String Openid = String.valueOf(OpenidJSONO.get("openid"));
            String AccessToken = String.valueOf(OpenidJSONO.get("access_token"));
            //用户保存的作用域
            String Scope = String.valueOf(OpenidJSONO.get("scope"));
            String refresh_token = String.valueOf(OpenidJSONO.get("refresh_token"));
            result.put("Openid", Openid);
            result.put("AccessToken", AccessToken);
            result.put("scope", Scope);
            result.put("refresh_token", refresh_token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
 
    /**
     * 获取到微信用户的唯一的OpendID
     * @param code  这是要获取OpendId的必须的一个参数
     * @return
     */
    @Override
    public Map<String , String> getAuthInfo(String code) {
        //进行授权验证，获取到OpenID字段等信息
        Map<String, String> result = oauth2GetOpenid(code);
        // 从这里可以得到用户openid
        String openId = result.get("Openid");
 
        return result;
    }

}
