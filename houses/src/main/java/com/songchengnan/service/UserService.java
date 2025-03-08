package com.songchengnan.service;

import com.songchengnan.pojo.House;
import com.songchengnan.pojo.User;

import java.util.List;

public interface UserService {

    void update(User user);

    User userInfo(String username);

    List<House> userHouse(String username);

    House houseDetail(Integer houseid);

    void addHouse(House house);

    void deleteHouse(Integer houseid);

    void updateHouse(House house);

    List<House> houseAll();

    List<House> houseCommunity(String community);

    void rentalHouse(Integer houseid, String tenantname);

    void terminateHouse(Integer houseid);
}
