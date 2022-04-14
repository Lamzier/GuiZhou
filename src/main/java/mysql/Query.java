package main.java.mysql;

import java.sql.*;
import java.util.*;

public class Query {

    /**
     * 随机取用户信息
     * @param limit 条数
     */
    public static List<Map<String , Object>> getUserRand(int limit){
        java.util.Date startDate = new java.util.Date();
        Connection connection = Start.getConnection();
        PreparedStatement preparedStatement = null;
        String sql ="select * from UserInfo ORDER BY RAND() LIMIT ?";
        List<Map<String , Object>> reList = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,limit);//设置限制
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Map<String , Object> userinfo = new HashMap<>();
                userinfo.put("id" , resultSet.getInt("id"));
                userinfo.put("nickname" , resultSet.getString("nickname"));
                userinfo.put("qq" , resultSet.getString("qq"));
                userinfo.put("likeItems" , resultSet.getString("likeItems"));
                userinfo.put("power" , resultSet.getInt("power"));
                userinfo.put("regDate" , resultSet.getTimestamp("regDate"));
                reList.add(userinfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("随机用户查询失败！");
            Start.reConnection();//重连数据库
            return null;
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
        System.out.println("随机用户查询成功！耗时：" + span + "毫秒");
        return reList;
    }

    /**
     * 模糊搜索歌名
     * @return id集合
     */
    public static Set<Integer> searchMusic(String key){
        java.util.Date startDate = new java.util.Date();
        Connection connection = Start.getConnection();
        PreparedStatement preparedStatement = null;
        String sql;
        if (key == null || key.length() <= 0){//没有内容
            sql = "select id from Music ORDER BY rand() LIMIT 20;";
        }else {
            sql = "select id from Music WHERE CONCAT(musicName,id) LIKE '%" + key + "%' LIMIT 20;";
        }
        Set<Integer> idSet = new HashSet<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                idSet.add(resultSet.getInt("id"));
            }
            if (idSet.size() < 20) {
                sql = "select id from Music_type WHERE CONCAT(musicType) LIKE '%" + key + "%' LIMIT 20;";
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    idSet.add(resultSet.getInt("id"));
                }
            }
            if (idSet.size() < 20) {
                sql = "select id from Music_singer WHERE CONCAT(musicSinger) LIKE '%" + key + "%' LIMIT 20;";
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    idSet.add(resultSet.getInt("id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("模糊搜索音乐(" + key + ")查询失败！");
            Start.reConnection();//重连数据库
            return null;
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
        System.out.println("模糊搜索音乐(" + key + ")查询成功！耗时：" + span + "毫秒");
        return idSet;
    }

    /**
     * 偶去音乐歌手多个
     */
    public static Map<Integer , String> getMusicSinger(Set<Integer> id){
        java.util.Date startDate = new java.util.Date();
        Connection connection = Start.getConnection();
        PreparedStatement preparedStatement = null;
        StringBuilder sql = new StringBuilder("select * from Music_singer WHERE");
        for (int idi : id){
            sql.append(" id = ").append(idi);
            sql.append(" or");
        }
        sql.delete(sql.length() - 2,sql.length());
        sql.append(";");
        Map<Integer , String> singers = new HashMap<>();
        try {
            preparedStatement = connection.prepareStatement(sql.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int idSon = resultSet.getInt("id");//歌曲id
                String item = singers.get(idSon);
                if (item == null || item.length() <= 0){
                    //没有数据
                    singers.put(idSon , resultSet.getString("musicSinger"));
                }else {
                    singers.put(idSon,item + ";" + resultSet.getString("musicSinger"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("多个音乐歌手(" + id + ")查询失败！");
            Start.reConnection();//重连数据库
            return null;
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
        System.out.println("多个音乐歌手(" + id + ")查询成功！耗时：" + span + "毫秒");
        return singers;
    }

    /**
     * 获取音乐歌手
     */
    public static List<String> getMusicSinger(int id){
        java.util.Date startDate = new java.util.Date();
        Connection connection = Start.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "select * from Music_singer WHERE id = ?;";
        List<String> types = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                types.add(resultSet.getString("musicSinger"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("音乐歌手(" + id + ")查询失败！");
            Start.reConnection();//重连数据库
            return null;
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
        System.out.println("音乐歌手(" + id + ")查询成功！耗时：" + span + "毫秒");
        return types;
    }

    /**
     * 获取音乐类型 多个
     */
    public static Map<Integer , String> getMusicType(Set<Integer> id){
        java.util.Date startDate = new java.util.Date();
        Connection connection = Start.getConnection();
        PreparedStatement preparedStatement = null;
        StringBuilder sql = new StringBuilder("select * from Music_type WHERE");
        for (int idi : id){
            sql.append(" id = ").append(idi);
            sql.append(" or");
        }
        sql.delete(sql.length() - 2,sql.length());
        sql.append(";");
        Map<Integer , String> types = new HashMap<>();
        try {
            preparedStatement = connection.prepareStatement(sql.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int idSon = resultSet.getInt("id");//歌曲id
                String item = types.get(idSon);
                if (item == null || item.length() <= 0){
                    //没有数据
                    types.put(idSon , resultSet.getString("musicType"));
                }else {
                    types.put(idSon,item + ";" + resultSet.getString("musicType"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("多个音乐类型(" + id + ")查询失败！");
            Start.reConnection();//重连数据库
            return null;
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
        System.out.println("多个音乐类型(" + id + ")查询成功！耗时：" + span + "毫秒");
        return types;
    }

    /**
     * 获取音乐类型
     */
    public static List<String> getMusicType(int id){
        java.util.Date startDate = new java.util.Date();
        Connection connection = Start.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "select * from Music_type WHERE id = ?;";
        List<String> types = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                types.add(resultSet.getString("musicType"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("音乐类型(" + id + ")查询失败！");
            Start.reConnection();//重连数据库
            return null;
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
        System.out.println("音乐类型(" + id + ")查询成功！耗时：" + span + "毫秒");
        return types;
    }

    /**
     * 获取多个歌单音乐信息
     */
    public static Map<Integer , Map<String , Object>> getMusicInfo(Set<Integer> idSet){
        java.util.Date startDate = new java.util.Date();
        StringBuilder sql = new StringBuilder("select * from Music WHERE");
        for (int id : idSet){//遍历数组
            sql.append(" id = ").append(id);
            sql.append(" or");
        }
        sql.delete(sql.length() - 2 , sql.length());
        sql.append(";");
        Connection connection = Start.getConnection();
        Statement statement = null;
        Map<Integer , Map<String , Object>> reMap = new HashMap<>();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql.toString());
            while (resultSet.next()){//有数据
                Map<String , Object> map = new HashMap<>();
                map.put("id" , resultSet.getInt("id"));
                map.put("musicName" , resultSet.getString("musicName"));
                map.put("pathString" , resultSet.getString("pathString"));
                map.put("regDate" , resultSet.getTimestamp("regDate"));
                map.put("heat" , resultSet.getInt("heat"));
                reMap.put(resultSet.getInt("id") , map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("多个音乐(" + idSet + ")查询失败！");
            Start.reConnection();//重连数据库
            return null;
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
        System.out.println("多个音乐(" + idSet + ")查询成功！耗时：" + span + "毫秒");
        return reMap;
    }

    /**
     * 随机获取音乐
     * @return
     */
    public static List<Map<String , Object>> getRandMusicInfo(int limit){
        java.util.Date startDate = new java.util.Date();
        Connection connection = Start.getConnection();
        PreparedStatement preparedStatement = null;
        String sql ="select * from Music ORDER BY RAND() LIMIT ?";
        List<Map<String , Object>> reList = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,limit);//设置限制
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Map<String , Object> userinfo = new HashMap<>();
                userinfo.put("id" , resultSet.getInt("id"));
                userinfo.put("musicName" , resultSet.getString("musicName"));
                userinfo.put("pathString" , resultSet.getString("pathString"));
                userinfo.put("regDate" , resultSet.getTimestamp("regDate"));
                userinfo.put("heat" , resultSet.getInt("heat"));
                reList.add(userinfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("随机用户查询失败！");
            Start.reConnection();//重连数据库
            return null;
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
        System.out.println("随机用户查询成功！耗时：" + span + "毫秒");
        return reList;

    }

    /**
     * 获取单歌音乐信息
     */
    public static Map<String , Object> getMusicInfo(int id){
        java.util.Date startDate = new java.util.Date();
        String sql = "select * from Music WHERE id = " + id + ";";
        Connection connection = Start.getConnection();
        Statement statement = null;
        Map<String , Object> reMap = new HashMap<>();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()){//有数据
                reMap.put("id" , resultSet.getInt("id"));
                reMap.put("musicName" , resultSet.getString("musicName"));
                reMap.put("pathString" , resultSet.getString("pathString"));
                reMap.put("regDate" , resultSet.getTimestamp("regDate"));
                reMap.put("heat" , resultSet.getInt("heat"));
            }else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("音乐(" + id + ")查询失败！");
            Start.reConnection();//重连数据库
            return null;
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
        System.out.println("音乐(" + id + ")查询成功！耗时：" + span + "毫秒");
        return reMap;
    }

    /**
     * 获取新歌排行版
     * @param limit 条数
     */
    public static Map<String , Map<String , Object>> getMusicNew(int limit){
        java.util.Date startDate = new java.util.Date();
        String sql = "select * from Music ORDER BY regDate DESC limit " + limit + ";";
        Connection connection = Start.getConnection();
        Statement statement = null;
        Map<String , Map<String , Object>> reMap = new HashMap<>();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            int x = 1;
            while (resultSet.next()){//有数据
                Map<String , Object> tt = new HashMap<>();
                tt.put("id" , resultSet.getInt("id"));
                tt.put("musicName" , resultSet.getString("musicName"));
                tt.put("pathString" , resultSet.getString("pathString"));
                tt.put("regDate" , resultSet.getTimestamp("regDate"));
                tt.put("heat" , resultSet.getInt("heat"));
                reMap.put("music_" + x , tt);
                x++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("音乐新歌排行榜查询失败！");
            Start.reConnection();//重连数据库
            return null;
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
        System.out.println("音乐新歌排行榜查询成功！耗时：" + span + "毫秒");
        return reMap;
    }

    /**
     * 获取热度排行榜
     * @param limit 条数
     */
    public static Map<String , Map<String , Object>> getMusicHeat(int limit){
        java.util.Date startDate = new java.util.Date();
        String sql = "select * from Music ORDER BY heat DESC limit " + limit + ";";
        Connection connection = Start.getConnection();
        Statement statement = null;
        Map<String , Map<String , Object>> reMap = new HashMap<>();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            int x = 1;
            while (resultSet.next()){//有数据
                Map<String , Object> tt = new HashMap<>();
                tt.put("id" , resultSet.getInt("id"));
                tt.put("musicName" , resultSet.getString("musicName"));
                tt.put("pathString" , resultSet.getString("pathString"));
                tt.put("regDate" , resultSet.getTimestamp("regDate"));
                tt.put("heat" , resultSet.getInt("heat"));
                reMap.put("music_" + x , tt);
                x++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("音乐热度排行榜查询失败！");
            Start.reConnection();//重连数据库
            return null;
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
        System.out.println("音乐热度排行榜查询成功！耗时：" + span + "毫秒");
        return reMap;
    }

    /**
     * 获取音乐列表
     * @param page 页码
     * @param limit 每次查询条数
     */
    public static Map<String , Object> getMusicList(int page , int limit){
        java.util.Date startDate = new java.util.Date();
        String sql = "select * from Music limit " + (page - 1) * limit + " , " + limit + ";";
        Connection connection = Start.getConnection();
        Statement statement = null;
        Map<String , Object> reMap = new HashMap<>();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            int x = 1;
            while (resultSet.next()){//有数据
                Map<String , Object> tt = new HashMap<>();
                tt.put("id" , resultSet.getInt("id"));
                tt.put("musicName" , resultSet.getString("musicName"));
                tt.put("pathString" , resultSet.getString("pathString"));
                tt.put("regDate" , resultSet.getTimestamp("regDate"));
                tt.put("heat" , resultSet.getInt("heat"));
                reMap.put("music_" + x , tt);
                x++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("音乐列表查询失败！");
            Start.reConnection();//重连数据库
            return null;
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
        int list = getCount("Music");
        reMap.put("list" , list);
        reMap.put("page" , page);
        reMap.put("pages" , (int)Math.ceil((double) list/limit));
        java.util.Date endDate = new java.util.Date();
        long span = endDate.getTime() - startDate.getTime();
        System.out.println("音乐列表查询成功！耗时：" + span + "毫秒");
        return reMap;
    }

    /**
     * 获取最早用户
     * @param page 页码
     * @param limit 每次查询条数
     */
    public static Map<String , Object> getUserList(int page , int limit){
        java.util.Date startDate = new java.util.Date();
        String sql = "select * from UserInfo order by `regDate` asc limit " + (page - 1) * limit + " , " + limit + ";";
        Connection connection = Start.getConnection();
        Statement statement = null;
        Map<String , Object> reMap = new HashMap<>();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            int x = 1;
            while (resultSet.next()){//有数据
                Map<String , Object> tt = new HashMap<>();
                tt.put("id" , resultSet.getInt("id"));
                tt.put("nickname" , resultSet.getString("nickname"));
                tt.put("qq" , resultSet.getString("qq"));
                tt.put("likeItems" , resultSet.getString("likeItems"));
                tt.put("power" , resultSet.getInt("power"));
                tt.put("regDate" , resultSet.getTimestamp("regDate"));
                reMap.put("userinfo_" + x , tt);
                x++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("用户列表查询失败！");
            Start.reConnection();//重连数据库
            return null;
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
        int list = getCount("UserInfo");
        reMap.put("list" , list);
        reMap.put("page" , page);
        reMap.put("pages" , (int)Math.ceil((double) list/limit));
        java.util.Date endDate = new java.util.Date();
        long span = endDate.getTime() - startDate.getTime();
        System.out.println("用户列表查询成功！耗时：" + span + "毫秒");
        return reMap;
    }

    /**
     * 获取表数量
     * @param table 表名
     */
    public static int getCount(String table){
        java.util.Date startDate = new java.util.Date();
        String sql = "select count(*) from " + table + ";";
        Connection connection = Start.getConnection();
        Statement statement = null;
        int rows = -1;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()){
                rows =  resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("表(" + table + ")数量查询失败！");
            return -1;
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
        System.out.println("表(" + table + ")数量查询成功！耗时：" + span + "毫秒");
        return rows;
    }

    /**
     * 获取验证码
     */
    public static Map<String , Object> getCode(String qq){
        java.util.Date startDate = new java.util.Date();
        Connection connection = Start.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "select * from Code WHERE qq = ? limit 1;";
        Map<String , Object> reMap = new HashMap<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1 , qq);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                reMap.put("qq" , qq);
                reMap.put("verCode" , resultSet.getString("verCode"));
                reMap.put("cookie" , resultSet.getString("cookie"));
                reMap.put("regDate" , resultSet.getTimestamp("regDate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("查看验证码(" + qq + ")失败！");
            Start.reConnection();//重连数据库
            return null;
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
        System.out.println("查看验证码(" + qq + ")成功！耗时：" + span + "毫秒");
        return reMap;
    }

    /**
     * 获取用户信息
     */
    public static Map<String , Object> getUserInfo(String qq){
        java.util.Date startDate = new java.util.Date();
        String sql = "select * from UserInfo where qq = ?;";
        Connection connection = Start.getConnection();
        PreparedStatement preparedStatement = null;
        Map<String , Object> reMap = new HashMap<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1 , qq);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){//有数据
                reMap.put("id" , resultSet.getInt("id"));
                reMap.put("nickname" , resultSet.getString("nickname"));
                reMap.put("qq" , resultSet.getString("qq"));
                reMap.put("password" , resultSet.getString("password"));
                reMap.put("likeItems" , resultSet.getString("likeItems"));
                reMap.put("power" , resultSet.getInt("power"));
                reMap.put("regDate" , resultSet.getTimestamp("regDate"));
            }else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("用户(" + qq + ")详查询失败！");
            Start.reConnection();//重连数据库
            return null;
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
        System.out.println("用户(" + qq + ")详查询成功！耗时：" + span + "毫秒");
        return reMap;
    }


}
