package com.pay200.wechat.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import bankchannel.api.ChannelHelper;

/** 
* @author yupeng
* @version 创建时间：2018年12月11日 上午11:26:01 
* 类说明 
*/
public class WeChatUtils {
	/**
     * XML格式转为map格式
     * @param request
     * @return
     */
    public static Map<String , String> xmlToMap(HttpServletRequest request){
        Map<String ,String> map = new HashMap<String , String>();
        try {
            InputStream inputStream =null;
            inputStream = request.getInputStream();
            SAXReader reader = new SAXReader();
            Document doc = reader.read(inputStream);
            Element rootElement = doc.getRootElement();
            List<Element> elements = rootElement.elements();
            for (Element el:elements) {
                map.put(el.getName() , el.getText());
            }
            inputStream.close();
            return map ;
        } catch (Exception e) {
            e.printStackTrace();
            return null ;
        }
    }
    public static void main(String[] args) {
		System.out.println(ChannelHelper.encode("4AraTg8702J8sY64V30i0WA8", "haozailai"));
	}

}
