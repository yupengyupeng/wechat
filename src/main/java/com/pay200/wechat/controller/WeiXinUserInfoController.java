package com.pay200.wechat.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.pay200.wechat.entity.WeiXinUser;
import com.pay200.wechat.service.WeiXinUserInfoService;
import com.pay200.wechat.util.ProjectConst;
import com.pay200.wechat.util.WeiXinUtils;

/** 
* @author yupeng
* @version 创建时间：2018年12月11日 上午11:54:31 
* 类说明 
*/

@RestController
public class WeiXinUserInfoController {
	@Autowired
    private WeiXinUserInfoService userService;
	private static Logger logger = Logger.getLogger(WeiXinUserInfoController.class);
	
	@RequestMapping("/tologin/test")
	public String test(HttpServletRequest request, HttpServletResponse response)  {
		logger.info("request==" + JSONObject.toJSONString(request.getParameterMap()));
		String[] params = new String[] { ProjectConst.TOKEN, request.getParameter("timestamp"),
				request.getParameter("nonce") };
		Arrays.sort(params);
		// 将三个参数字符串拼接成一个字符串进行sha1加密
		String clearText = params[0] + params[1] + params[2];
		String algorithm = "SHA-1";
		String sign = null;
		try {
			sign = new String(org.apache.commons.codec.binary.Hex
					.encodeHex(MessageDigest.getInstance(algorithm).digest((clearText).getBytes()), true));
			logger.info("sign==" + sign);
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
		if (request.getParameter("signature").equals(sign)) {
			logger.info("验证成功");
			try {
				return request.getParameter("echostr");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			logger.info("验证失败");
		}
		return null;
	}
	 @RequestMapping("/tologin/menucreat")
	 public String menucreat(HttpServletRequest request ,HttpServletResponse response) {
		 logger.info("request=="+JSONObject.toJSONString(request.getParameterMap()));
		 String url="https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+ProjectConst.ACCESS_TOKEN;
		 return "ok";
	 }
	 public static void main(String[] args) {
		 String url="https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+ProjectConst.ACCESS_TOKEN;

	}
	 
	 
    /**
     * 进行网页授权，便于获取到用户的绑定的内容
     * @param request
     * @param session
     * @param map
     * @return
     */
    @RequestMapping("/tologin/userinfo")
    public String check(HttpServletRequest request , HttpSession session, Map<String, Object> map) {
        //首先判断一下session中，是否有保存着的当前用户的信息，有的话，就不需要进行重复请求信息
		logger.info("request==" + JSONObject.toJSONString(request.getParameterMap()));
    	WeiXinUser  weiXinUser = null ;
        if(session.getAttribute("currentUser") != null){
            weiXinUser = (WeiXinUser) session.getAttribute("currentUser");
        }else {
            /**
             * 进行获取openId，必须的一个参数，这个是当进行了授权页面的时候，再重定向了我们自己的一个页面的时候，
             * 会在request页面中，新增这个字段信息，要结合这个ProjectConst.Get_WEIXINPAGE_Code这个常量思考
             */
            String code = request.getParameter("code");
            try {
                //得到当前用户的信息(具体信息就看weixinUser这个javabean)
                weiXinUser = getTheCode(session, code);
                //将获取到的用户信息，放入到session中
                session.setAttribute("currentUser", weiXinUser);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        logger.info(JSONObject.toJSONString(weiXinUser));
        map.put("weiXinUser", weiXinUser);
        return "hello";
    }
 
    @RequestMapping("/tologin/touserinfo")
    public void touserinfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
                //这里要将你的授权回调地址处理一下，否则微信识别不了
         String redirect_uri=URLEncoder.encode("http://83cf756a.ngrok.io/tologin/userinfo", "UTF-8");
                //简单获取openid的话参数response_type与scope与state参数固定写死即可
		StringBuffer url=new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize?redirect_uri="+redirect_uri+
				"&appid="+ProjectConst.PROJECT_APPID+"&response_type=code&scope=snsapi_base&state=1#wechat_redirect");
		logger.info("url"+url);
		response.sendRedirect(url.toString());//这里请不要使用get请求单纯的将页面跳转到该url即可
    }
    /**
     * 获取用户的openId
     * @param session
     * @param code
     * @return 返回封装的微信用户的对象
     */
    private WeiXinUser getTheCode(HttpSession session, String code) {
        Map<String , String>  authInfo = new HashMap<>();
        String openId = "";
        if (code != null)
        {
            // 调用根据用户的code得到需要的授权信息
            authInfo= userService.getAuthInfo(code);
           //获取到openId
            openId = authInfo.get("Openid");
        }
        // 获取基础刷新的接口访问凭证（目前还没明白为什么用authInfo.get("AccessToken");这里面的access_token就不行）
        String accessToken = WeiXinUtils.getAccessToken().getToken();
        //获取到微信用户的信息
        WeiXinUser userinfo = userService.getUserInfo(accessToken ,openId);
 
        return userinfo;
    }

	
}
