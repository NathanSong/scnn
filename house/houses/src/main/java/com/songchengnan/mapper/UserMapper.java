package com.songchengnan.mapper;

import com.songchengnan.pojo.House;
import com.songchengnan.pojo.User;
import org.apache.ibatis.annotations.*;
import java.util.List;


@Mapper
public interface UserMapper {

    // 更新用户信息
    @Update("update user set  password = #{password},number = #{number}," +
            "userphone = #{userphone} where username = #{username}")
    void update(User user);

    // 查询用户信息
    @Select("select * from user where username = #{username}")
    User userInfo(String username);

    @Select("select * from house where housename = #{username} or tenantname = #{username}")
    List<House> userHouse(String username);

    @Select("select * from house where houseid = #{houseid}")
    House houseDetail(Integer houseid);

    @Insert("insert into house(statue,area,floor,price,community,address,type,toward,surrounding,decoration,housename,housephone,tenantname,image) values(#{statue},#{area},#{floor},#{price},#{community},#{address},#{type},#{toward},#{surrounding},#{decoration},#{housename},#{housephone},#{tenantname},#{image})")
    void addHouse(House house);

    @Delete("delete from house where houseid = #{houseid}")
    void deleteHouse(Integer houseid);

    @Update("update house set area = #{area},floor = #{floor},price = #{price},community = #{community},address = #{address},type = #{type},toward = #{toward},surrounding = #{surrounding},decoration = #{decoration},housephone=#{housephone},image = #{image} where houseid = #{houseid}")
    void updateHouse(House house);

    @Select("select * from house ")
    List<House> houseAll();

    // 模糊查询
    @Select("select * from house where community like concat('%',#{community},'%')")
    List<House> houseCommunity(String community);

    @Update("update house set statue = 1, tenantname = #{tenantname} where houseid = #{houseid}")
    void rentalHouse(Integer houseid, String tenantname);

    @Update("update house set statue = 0,tenantname = '' where houseid = #{houseid}")
    void terminateHouse(Integer houseid);
}
