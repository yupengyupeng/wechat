package com.pay200.wechat.service;

import java.util.Map;

import com.pay200.wechat.entity.WeiXinUser;

/** 
* @author yupeng
* @version 创建时间：2018年12月11日 上午11:56:08 
* 类说明 
*/
public interface WeiXinUserInfoService {
	 /**
     * 获取到微信个人用户的信息
     * @param accessToken
     * @param openId
     * @return
     */
    WeiXinUser getUserInfo(String accessToken, String openId);

    /**
     *用于获取网页授权后的信息字段，其中主要是获取openId
     * @param code  授权码
     * @return
     */
    Map<String , String > getAuthInfo(String code);

    /**
     * 进行网页授权的认证
     * @param code 授权码
     * @return
     */
    Map<String,String> oauth2GetOpenid(String code);

}
