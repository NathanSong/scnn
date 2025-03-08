package com.songchengnan.service.impl;

import com.songchengnan.mapper.UserMapper;
import com.songchengnan.pojo.House;
import com.songchengnan.pojo.User;
import com.songchengnan.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void update(User user) {
        log.info("更新用户信息");
        userMapper.update(user);
    }

    @Override
    public User userInfo(String username) {
        log.info("查询用户信息");
        return userMapper.userInfo(username);
    }

    @Override
    public List<House> userHouse(String username) {
        log.info("查询用户房子");
        return userMapper.userHouse(username);
    }

    @Override
    public House houseDetail(Integer houseid) {
        log.info("查询房子信息");
        return userMapper.houseDetail(houseid);
    }

    @Override
    public void addHouse(House house) {
        log.info("新增房源");
        userMapper.addHouse(house);
    }

    @Override
    public void deleteHouse(Integer houseid) {
        log.info("删除房源");
        userMapper.deleteHouse(houseid);
    }

    @Override
    public void updateHouse(House house) {
        log.info("更新房源信息");
        userMapper.updateHouse(house);
    }

    @Override
    public List<House> houseAll() {
        log.info("查询所有房源");
        return userMapper.houseAll();
    }

    @Override
    public List<House> houseCommunity(String community) {
        log.info("查询小区房源");
        return userMapper.houseCommunity(community);
    }

    @Override
    public void rentalHouse(Integer houseid, String tenantname) {
        log.info("更新房源信息");
        userMapper.rentalHouse(houseid, tenantname);
    }

    @Override
    public void terminateHouse(Integer houseid) {
        log.info("终止房源信息");
        userMapper.terminateHouse(houseid);
    }
}
