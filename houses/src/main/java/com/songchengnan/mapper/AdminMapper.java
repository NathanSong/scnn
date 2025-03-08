package com.songchengnan.mapper;

import com.songchengnan.pojo.Message;
import com.songchengnan.pojo.User;
import org.apache.ibatis.annotations.*;
import java.util.List;

// 用户数据访问接口
@Mapper
public interface AdminMapper {
    @Insert("insert into user(admin,username,password,number,userphone) " +
            "values (#{admin}, #{username}, #{password}, #{number}, #{userphone});")
    void insert(User user);

    @Select("select * from user where username = #{username} and password = #{password} and admin = #{admin}")
    User login(String username, String password, Integer admin);

    @Delete("delete from user where username = #{username}")
    void deleteByUsername(String username);

    @Select("select count(*) from user")
    Long count();
    @Select("select * from user limit #{start},#{pageSize}")
    List<User> page(Integer start, Integer pageSize);

    @Update("update user set password = #{password}, " + "number = #{number}, " +
            "userphone = #{userphone},admin = #{admin} where username = #{username}")
    void update(User user);

    @Insert("insert into house_rental.message( messagename,messagetitle, content, date) " +
            "values (#{messagename},#{messagetitle}, #{content}, #{date})")
    void addMessage(Message message);


    @Select("select count(*) from message WHERE messagetitle LIKE " +
            "concat('%', #{estateName}, '%') OR content LIKE concat('%', #{estateName}, '%')")
    Long countNeedMessage(String estateName);
    @Select("select count(*) from house_rental.message")
    Long countMessage();
    @Select("select * from message WHERE messagetitle LIKE concat('%', #{estateName}, '%') " +
            "OR content LIKE concat('%', #{estateName}, '%')  limit #{start},#{pageSize}")
    List<Message> pageMessage(String estateName,Integer start, Integer pageSize);

    @Delete("delete from house_rental.message where messagetitle = #{messagetitle} and messagename = #{messagename}")
    void deleteMessage(String messagetitle, String messagename);

    // 房东注册
    @Insert("insert into user(admin,username,password,number,userphone) " +
            "values (3, #{username}, #{password}, #{number}, #{userphone});")
    void registerLandlord(User user);

    // 房东登录
    @Select("select * from user where username = #{username} and password = #{password} and admin = 3")
    User landlordLogin(String username, String password);


}