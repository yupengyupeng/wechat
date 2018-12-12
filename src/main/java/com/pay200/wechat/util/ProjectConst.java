package com.pay200.wechat.util;
/** 
* @author yupeng
* @version 创建时间：2018年12月11日 上午11:49:56 
* 类说明 
*/
public class ProjectConst {
	/**
     * 用于获取当前与微信公众号交互的用户信息的接口（一般是用第一个接口地址）
     */
    public static final String GET_WEIXIN_USER_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
    public final static String GetPageUsersUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
 
    /**
     * 用于进行网页授权验证的接口URL，通过这个才可以得到opendID等字段信息
     */
    public final static String GET_WEBAUTH_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
 
    /**
     * 用于进行当点击按钮的时候，能够在网页授权之后获取到code，再跳转到自己设定的一个URL路径上的接口，这个主要是为了获取之后于
     * 获取openId的接口相结合
     * 注意：参数：toselfURL  表示的是当授权成功后，跳转到的自己设定的页面，所以这个要根据自己的需要进行修改
     */
    public final static String Get_WEIXINPAGE_Code = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=toselfURL&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";
  
    /**
     * 获取access_token的URL
     */
    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
    public static final String TOKEN="acd9dd9dd9795fd8893d10cca03a7532";
    
    public static final String ACCESS_TOKEN="16_ODq1qk2UFQsYJeklTnDNIonISGQrQCYXIiYcPdkvnw1fEj0Z-q9lIEpXallLEQAEjpy1t4sSxb_7zWDLF8RuM9y2HVyzU6Xhwt0j3y8IrWCWFYWnED4P7sWSOMIQNVjAIAWTB";
    /**
     * appid
     */
    public final static String PROJECT_APPID="wxe217411339a72de8";
    
    public final static String PROJECT_APPSECRET="f23a188b2ea1470fcc4bd7c814ceb281";

}
