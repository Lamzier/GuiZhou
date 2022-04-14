package main.java.business;

import main.java.mysql.Query;

import java.util.*;

/**
 * 用户协同过滤类
 */
public class UserCF {

    private main.java.business.User Master;
    private List<User> Masses;//群众用户
    private Set<Integer> musicItemSet = new HashSet<>();//喜欢的所有物品集合id
    private final List<MusicItem> musicItems = new ArrayList<>();//可能喜欢的所有物品
    private  Map<Integer ,Map<String , Object>> allMusicInfo = null;//存储所有音乐信息


    /**
     * 初始化
     * @param Master 主用户
     * @param Masses 参照用户群
     */
    public UserCF(User Master , User[] Masses){
        this.Master = Master;
        this.Masses = Arrays.asList(Masses);
        flashSimilarityMatrix();//刷新相似度
        flashItemAll();//刷新物品列表
    }

    /**
     * 刷新物品列表
     */
    private void flashItemAll(){
        musicItems.clear();//清理所有数据
        allMusicInfo = Query.getMusicInfo(musicItemSet);//获取所有音乐内容
        for (int id : musicItemSet){//遍历数组
            Map<String , Object > musicInfo = allMusicInfo.get(id);
            MusicItem musicItem = new MusicItem(id , (Integer) musicInfo.get("heat"));
            int like = getItemLike(musicItem);//计算物品喜欢程度
            musicItem.setInterestedProgram(like);//设置喜欢程度
            musicItems.add(musicItem);//添加到列表
        }
        musicItems.sort(Collections.reverseOrder());//从大到小排序
    }

    public Set<Integer> getMusicItemSet() {
        return musicItemSet;
    }

    public List<MusicItem> getMusicItems() {
        return musicItems;
    }

    /**
     * 计算用户对物品的喜欢程度
     * @param musicItem 物品
     * @return 喜欢程度
     */
    private int getItemLike(MusicItem musicItem){
        int reLike = 0;
        for (User tempUser : Masses){//遍历用户
            if(tempUser.hasLikeItem(musicItem.getId() + "")){//如果有则喜欢加一
                reLike ++;
            }
        }
        return reLike;
    }

    /**
     * 刷新相似度矩阵
     */
    private void flashSimilarityMatrix(){
        Map<Integer , Integer> itemLikeUserCount = new HashMap<>();//物品喜欢的用户数量
        for (User item : Masses){//第一次遍历，获取物品喜欢的用户数量
            Set<Integer> itemLikes = item.getLikeItmes();//用户喜欢物品集合
            for (int likeItem : itemLikes){//遍历集合
                Integer itemMap = itemLikeUserCount.get(likeItem);
                itemLikeUserCount.put(likeItem , itemMap == null || itemMap <= 0 ? 1 : ++itemMap);//写信息
            }
        }//完成遍历获取物品被喜欢用户数量
        for (User item : Masses){//遍历计算群众用户
            Master.setSimilarity(
                    getUserLike(Master.getLikeItmes(),item.getLikeItmes(),itemLikeUserCount)
            );//设置相似度
            //添加喜欢的可能物品
            musicItemSet.addAll(item.getLikeItmes());//利用set特性过滤重复属性，执行效率比较高
        }
    }

    /**
     * 计算用户相似度
     * @param user1 用户1的喜欢列表
     * @param user2 用户2的喜欢列表
     * @param itemLikeUserCount 物品被喜欢数
     * @return 相似度，数值越高越相似
     */
    private static double getUserLike(Set<Integer> user1 , Set<Integer> user2 , Map<Integer , Integer> itemLikeUserCount){
        if (user1.size() == 0 || user2.size() == 0){
            return 0;
        }//如果没有喜欢集合
        double molecule = 0;
        Set<Integer> UserLikeItemUnion = getUnion(user1,user2);//获取用户喜欢的交集
        for(int item : UserLikeItemUnion){//遍历交集
            molecule += 1 / Math.log(1 + itemLikeUserCount.get(item));
        }
        double denominator = Math.sqrt(user1.size() * user2.size());
        return molecule / denominator;
        //return (double)getUnion(user1,user2).size() / getIntersection(user1,user2).size();//简单的相除算法
    }

    /**
     * 获取集合交集
     */
    private static Set<Integer> getIntersection(Set<Integer> set1 , Set<Integer> set2){
        Set<Integer> re = new HashSet<>(set1);
        re.addAll(set2);
        return re;
    }

    /**
     * 获取集合并集
     */
    private static Set<Integer> getUnion(Set<Integer> set1 , Set<Integer> set2){
        Set<Integer> re = new HashSet<>();
        for (int temp : set1){//遍历数组
            if (set2.contains(temp)){//如果包含
                re.add(temp);
            }
        }
        return re;
    }




}
