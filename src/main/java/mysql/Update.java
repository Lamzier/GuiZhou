package main.java.mysql;

import main.java.business.FinalAll;
import java.io.File;
import java.sql.*;
import java.util.List;
import static main.java.mysql.Start.MYSQL_CHARSET;

public class Update {

    /**
     * 修改用户信息
     */
    public static boolean changeUserInfo(String qq, String nickname){
        java.util.Date startDate = new java.util.Date();
        String sql = "UPDATE `UserInfo` SET `nickname` = ? WHERE `qq` = ? ;";
        Connection connection = Start.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,nickname);
            preparedStatement.setString(2,qq);
            if(preparedStatement.executeUpdate() < 1) return false;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("用户昵称修改(" + qq + ")失败！");
            Start.reConnection();//重连数据库
            return false;
        }finally {
            try {
                if(preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Statement关闭失败！");
            }
        }
        java.util.Date endDate = new java.util.Date();
        long span = endDate.getTime() - startDate.getTime();
        System.out.println("用户昵称修改(" + qq + ")成功！耗时：" + span + "毫秒");
        return true;
    }

    /**
     * 修改用户密码
     */
    public static boolean changeUserPassword(String qq , String passowrd){
        java.util.Date startDate = new java.util.Date();
        String sql = "UPDATE `UserInfo` SET `password` = ? WHERE `qq` = ? ;";
        Connection connection = Start.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,passowrd);
            preparedStatement.setString(2,qq);
            if(preparedStatement.executeUpdate() < 1) return false;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("用户密码修改(" + qq + ")失败！");
            Start.reConnection();//重连数据库
            return false;
        }finally {
            try {
                if(preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Statement关闭失败！");
            }
        }
        java.util.Date endDate = new java.util.Date();
        long span = endDate.getTime() - startDate.getTime();
        System.out.println("用户密码修改(" + qq + ")成功！耗时：" + span + "毫秒");
        return true;
    }

    /**
     * 音乐热度+1
     */
    public static boolean updMusicHeat(int id){
        java.util.Date startDate = new java.util.Date();
        String sql = "UPDATE Music SET heat = heat + 1 WHERE id = ?;";
        Connection connection = Start.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            if(preparedStatement.executeUpdate() < 1) return false;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("音乐热度+1(" + id + ")失败！");
            Start.reConnection();//重连数据库
            return false;
        }finally {
            try {
                if(preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Statement关闭失败！");
            }
        }
        java.util.Date endDate = new java.util.Date();
        long span = endDate.getTime() - startDate.getTime();
        System.out.println("音乐热度+1(" + id + ")成功！耗时：" + span + "毫秒");
        return true;
    }

    /**
     * 更新用户喜欢 列表
     */
    public static boolean updUserLikeItem(String qq , String likeItem){
        java.util.Date startDate = new java.util.Date();
        String sql = "UPDATE UserInfo SET likeItems = ? WHERE qq = ?;";
        Connection connection = Start.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,likeItem);
            preparedStatement.setString(2,qq);
            if(preparedStatement.executeUpdate() < 1) return false;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("设置用户喜欢列表(" + qq + ")失败！");
            Start.reConnection();//重连数据库
            return false;
        }finally {
            try {
                if(preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Statement关闭失败！");
            }
        }
        java.util.Date endDate = new java.util.Date();
        long span = endDate.getTime() - startDate.getTime();
        System.out.println("设置用户喜欢列表(" + qq + ")成功！耗时：" + span + "毫秒");
        return true;
    }

    /**
     * 删除用户
     */
    public static boolean deleteUser(int id){
        java.util.Date startDate = new java.util.Date();
        String sql = "DELETE FROM UserInfo WHERE id = " + id;
        Connection connection = Start.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);//删除type
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("用户(" + id + ")删除失败！");
            Start.reConnection();//重连数据库
            return false;
        }finally {
            try {
                if(statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Statement关闭失败！");
            }
        }
        java.util.Date endDate = new java.util.Date();
        long span = endDate.getTime() - startDate.getTime();
        System.out.println("用户(" + id + ")删除成功！耗时：" + span + "毫秒");
        return true;
    }

    /**
     * 删除音乐
     */
    public static boolean deleteMusic(int id , String pathString){
        java.util.Date startDate = new java.util.Date();
        String sql = "DELETE FROM Music_type WHERE id = " + id;
        Connection connection = Start.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);//删除type
            sql = "DELETE FROM Music_singer WHERE id = " + id;
            statement.executeUpdate(sql);//删除singer
            sql = "DELETE FROM Music WHERE id = " + id;
            statement.executeUpdate(sql);//删除主表
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("音乐(" + id + ")删除失败！");
            Start.reConnection();//重连数据库
            return false;
        }finally {
            try {
                if(statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Statement关闭失败！");
            }
        }
        //删除本地文件
        String loadPath = FinalAll.LOCAL_SAVE_PATH + "upload/music";
        File file = new File(loadPath , pathString);
        if(file.exists()){//如果存在
            if(!file.delete()){
                //删除失败
                System.out.println("文件(" + pathString + ")删除失败！但是对整体影响不大...");
            }
        }
        java.util.Date endDate = new java.util.Date();
        long span = endDate.getTime() - startDate.getTime();
        System.out.println("音乐(" + id + ")删除成功！耗时：" + span + "毫秒");
        return true;
    }

    /**
     * 添加音乐
     * @param musicName 音乐名称
     * @param pathString 音乐存储名称包含后缀
     * @param types 音乐类型
     * @param singers 音乐歌手
     */
    public static int addMusic(String musicName , String pathString ,
                                   List<String> types , List<String> singers){
        java.util.Date startDate = new java.util.Date();
        int id = -1;//返回音乐插入id
        String sql = "INSERT INTO Music(`musicName`,`pathString`,`regDate`)" +
                "VALUES(?,?,NOW())";
        Connection connection = Start.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,musicName);
            preparedStatement.setString(2,pathString);
            if(preparedStatement.executeUpdate() < 1){
                return -1;
            }
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                id = resultSet.getInt(1);//获取最新id
            }else {
                return id;
            }
            //写入附加表 ，type hashcode
            for (int i = 0 ; i < types.size() ; i ++){
                String sqlType = "INSERT INTO Music_type(`id`,`musicType`,`type_hash`)" +
                        "VALUES(?,?,?)";
                preparedStatement = connection.prepareStatement(sqlType);
                preparedStatement.setInt(1 , id);
                preparedStatement.setString(2 , types.get(i));
                preparedStatement.setInt(3 , types.get(i).hashCode());
                preparedStatement.executeUpdate();//执行
            }
            //写到附加表 singer hashcode
            for (int i = 0 ; i < singers.size() ; i ++){
                String sqlSinger = "INSERT INTO Music_singer(`id`,`musicSinger`,`singer_hash`)" +
                        "VALUES(?,?,?)";
                preparedStatement = connection.prepareStatement(sqlSinger);
                preparedStatement.setInt(1 , id);
                preparedStatement.setString(2 , singers.get(i));
                preparedStatement.setInt(3 , singers.get(i).hashCode());
                preparedStatement.executeUpdate();//执行
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("添加音乐失败！");
            Start.reConnection();//重连数据库
            return -1;
        }finally {
            try {
                if(preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Statement关闭失败！");
            }
        }
        java.util.Date endDate = new java.util.Date();
        long span = endDate.getTime() - startDate.getTime();
        System.out.println("添加音乐成功！耗时：" + span + "毫秒");
        return id;
    }

    /**
     * 更新用户cookie
     */
    public static boolean updCodeCookie(String qq, String Cookie){
        java.util.Date startDate = new java.util.Date();
        String sql = "UPDATE Code SET cookie = ? , regDate = NOW() WHERE qq = ?;";
        Connection connection = Start.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,Cookie);
            preparedStatement.setString(2,qq);
            if(preparedStatement.executeUpdate() < 1) return false;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("更新用户Cookie(" + qq + ")失败！");
            Start.reConnection();//重连数据库
            return false;
        }finally {
            try {
                if(preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Statement关闭失败！");
            }
        }
        java.util.Date endDate = new java.util.Date();
        long span = endDate.getTime() - startDate.getTime();
        System.out.println("更新用户Cookie(" + qq + ")成功！耗时：" + span + "毫秒");
        return true;
    }

    /**
     * 设置权限
     */
    public static boolean updUserPower(String qq, int power){
        java.util.Date startDate = new java.util.Date();
        String sql = "UPDATE UserInfo SET power = ? WHERE qq = ?;";
        Connection connection = Start.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,power);
            preparedStatement.setString(2,qq);
            if(preparedStatement.executeUpdate() < 1) return false;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("设置用户权限(" + qq + ")失败！");
            Start.reConnection();//重连数据库
            return false;
        }finally {
            try {
                if(preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Statement关闭失败！");
            }
        }
        java.util.Date endDate = new java.util.Date();
        long span = endDate.getTime() - startDate.getTime();
        System.out.println("设置用户权限(" + qq + ")成功！耗时：" + span + "毫秒");
        return true;
    }

    /**
     * 更新验证码
     * @return
     */
    public static boolean updCode(String qq , String verCode , String cookie){
        java.util.Date startDate = new java.util.Date();
        String sql = "UPDATE Code SET verCode = ? , cookie = ? , regDate = NOW() WHERE qq = ?;";
        Connection connection = Start.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,verCode);
            preparedStatement.setString(2,cookie);
            preparedStatement.setString(3,qq);
            if(preparedStatement.executeUpdate() < 1) return false;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("更新验证码(" + qq + ")失败！");
            Start.reConnection();//重连数据库
            return false;
        }finally {
            try {
                if(preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Statement关闭失败！");
            }
        }
        java.util.Date endDate = new java.util.Date();
        long span = endDate.getTime() - startDate.getTime();
        System.out.println("更新验证码(" + qq + ")成功！耗时：" + span + "毫秒");
        return true;
    }

    /**
     * 添加验证码
     */
    public static boolean addCode(String qq , String verCode , String cookie){
        java.util.Date startDate = new java.util.Date();
        String sql = "INSERT INTO Code(" +
                "`qq`,`verCode`,`cookie`,`regDate`) " +
                "VALUES (?,?,?,NOW());";
        Connection connection = Start.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,qq);
            preparedStatement.setString(2,verCode);
            preparedStatement.setString(3,cookie);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("添加验证码(" + qq + ")失败！");
            Start.reConnection();//重连数据库
            return false;
        }finally {
            try {
                if(preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Statement关闭失败！");
            }
        }
        java.util.Date endDate = new java.util.Date();
        long span = endDate.getTime() - startDate.getTime();
        System.out.println("添加验证码(" + qq + ")成功！耗时：" + span + "毫秒");
        return true;
    }

    /**
     * 注册用户
     */
    public static boolean registerUser(String nickname , String qq, String password){
        java.util.Date startDate = new java.util.Date();
        String sql = "INSERT INTO UserInfo(" +
                "`nickname`,`qq`,`password`,`regDate`) " +
                "VALUES (?,?,?,NOW());";
        Connection connection = Start.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1 , nickname);
            preparedStatement.setString(2 , qq);
            preparedStatement.setString(3 , password);
            System.out.println(preparedStatement);
            if(preparedStatement.executeUpdate() <= 0) return false;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("首次添加用户(" + nickname + "|" + qq + ")失败！");
            Start.reConnection();//重连数据库
            return false;
        }finally {
            try {
                if(preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Statement关闭失败！");
            }
        }
        java.util.Date endDate = new java.util.Date();
        long span = endDate.getTime() - startDate.getTime();
        System.out.println("首次添加用户(" + nickname + "|" + qq + ")成功！耗时：" + span + "毫秒");
        return true;
    }

    /**
     * 初始化数据库
     */
    public static boolean initCreateTable(){
        String sql = "CREATE TABLE IF NOT EXISTS UserInfo(" +
                "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                "nickname VARCHAR(16) DEFAULT \"\"," +
                "qq VARCHAR(32) DEFAULT \"\"," +
                "password VARCHAR(128) DEFAULT \"\"," +
                "likeItems VARCHAR(10240) DEFAULT \"\"," +
                "power INT(8) DEFAULT 0," +
                "regDate DATETIME" +
                ")engine=InnoDB AUTO_INCREMENT=0 default charset=" + MYSQL_CHARSET + ";";
        Connection connection = Start.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS Code(" +
                    "qq VARCHAR(32) DEFAULT \"\"," +
                    "verCode VARCHAR(8) DEFAULT \"\"," +
                    "cookie VARCHAR(32) DEFAULT \"\"," +
                    "regDate DATETIME" +
                    ")engine=InnoDB default charset=" + MYSQL_CHARSET + ";";
            statement.executeUpdate(sql);//执行sql
            sql = "CREATE TABLE IF NOT EXISTS Music(" +
                    "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                    "musicName VARCHAR(32) DEFAULT \"\"," +
                    "pathString VARCHAR(32) DEFAULT \"\"," +
                    "regDate DATETIME," +
                    "heat INT DEFAULT 0" +
                    ")engine=InnoDB AUTO_INCREMENT=0 default charset=" + MYSQL_CHARSET + ";";
            statement.executeUpdate(sql);//执行sql
            sql = "CREATE TABLE IF NOT EXISTS Music_type(" +
                    "id INT NOT NULL," +
                    "type_hash INT NOT NULL," +
                    "musicType VARCHAR(32) NOT NULL" +
                    ")engine=InnoDB default charset=" + MYSQL_CHARSET + ";";
            statement.executeUpdate(sql);//执行sql
            sql = "CREATE TABLE IF NOT EXISTS Music_singer(" +
                    "id INT NOT NULL," +
                    "singer_hash INT NOT NULL," +
                    "musicSinger VARCHAR(32) NOT NULL" +
                    ")engine=InnoDB default charset=" + MYSQL_CHARSET + ";";
            statement.executeUpdate(sql);//执行sql
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("初始化数据库失败！");
            Start.reConnection();//重连数据库
            return false;
        }finally {
            try {
                if(statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Statement关闭失败！");
            }
        }
        return true;
    }
}
