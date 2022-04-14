package main.java.business;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Random;

public class Cookie {

    /**
     * 获取cookie 默认长度32
     */
    public static String getCookie(){
        return getCookie(32);
    }

    /**
     * 获取code 默认长度5
     * @return
     */
    public static String getCode(){
        return getCode(5);
    }

    /**
     * 获取cookie 通常32
     * @param num cookie长度
     */
    public static String getCookie(int num){
        char[] c = {'1','2','3','4','5','6','7','8','9','0','a','b','c','d','e','f','g','h','i','j',
                'k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','E','F','G','H','I',
                'J','K','L','M','N','O','P','Q','R','S','T','U','V','X','Y','Z'};
        Random random = new Random();
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = num; i > 0 ; i --){
            stringBuffer.append(c[random.nextInt(c.length)]);//添加字符
        }
        return stringBuffer.toString();
    }

    /**
     * code 通常5
     * @param num code长度
     */
    public static String getCode(int num){
        char[] c = {'1','2','3','4','5','6','7','8','9','0'};
        Random random = new Random();
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = num; i > 0 ; i --){
            stringBuffer.append(c[random.nextInt(c.length)]);//添加字符
        }
        return stringBuffer.toString();
    }

    /**
     * 删除cookie 所有
     */
    public static void removeCookieAll(javax.servlet.http.Cookie[] cookies ,HttpServletResponse response){
        if (cookies == null) return;
        for (javax.servlet.http.Cookie cookie : cookies){
            cookie.setValue(null);
            cookie.setMaxAge(0);//设置无有效时间
            cookie.setPath("/");
            response.addCookie(cookie);//添加到回响
        }
    }

    /**
     * 删除cookie 指定
     * @param key 要删除的key
     */
    public static void removeCookie(javax.servlet.http.Cookie[] cookies ,HttpServletResponse response , List<String> key){
        if (cookies == null) return;
        for (javax.servlet.http.Cookie cookie : cookies){
            if (key.contains(cookie.getName())){//如果包含
                cookie.setValue(null);
                cookie.setMaxAge(0);//设置无有效时间
                cookie.setPath("/");
                response.addCookie(cookie);//添加到回响
            }
        }
    }


}
