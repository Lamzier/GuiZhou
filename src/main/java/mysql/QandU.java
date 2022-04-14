package mysql;

import main.java.mysql.Query;
import main.java.mysql.Update;

import java.util.Map;

/**
 * 查询更新类
 */
public class QandU{

    /**
     * 更新验证码
     */
    public static boolean updCode(String qq , String verCode , String cookie){
        Map<String , Object> Code = Query.getCode(qq);
        if (Code == null || Code.size() <= 0){//没有
            return Update.addCode(qq,verCode,cookie);//添加验证码
        }else {//更新验证码
            return Update.updCode(qq,verCode,cookie);//更新验证码
        }
    }


}
