package com.songchengnan.controller;

import com.songchengnan.pojo.House;
import com.songchengnan.pojo.Result;
import com.songchengnan.pojo.User;
import com.songchengnan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 普通用户控制器
@CrossOrigin(origins = "http://localhost:8080")// 允许跨域
@RestController
//@RequestMapping("/userHome/userInfo")
public class UserController {


    @Autowired
    private UserService userService;


    // 查询用户信息
    @GetMapping("/userHome/userInfo")
    public Result userInfo(@RequestParam String username){
        //调用业务层查询功能
        User user = userService.userInfo(username);
        //响应
        return Result.success(user);
    }

    // 更新用户信息
    @PutMapping("/userHome/userInfo")
    public Result update(@RequestBody User user){
        //调用业务层更新功能
        userService.update(user);
        //响应
        return Result.success();
    }

    // 查询用户房子
    @GetMapping("/userHome/userHouse")
    public Result userHouse(@RequestParam String username){
        //调用业务层查询功能
        List<House> house = userService.userHouse(username);
        //响应
        return Result.success(house);
    }

    // 查询房子信息
    @GetMapping("/userHome/userhouse/detail")
    public Result houseDetail(@RequestParam Integer houseid){
        //调用业务层查询功能
        House house = userService.houseDetail(houseid);
        //响应
        return Result.success(house);
    }

    // 新增房源
    @PostMapping("/userHome/userhouse/add")
    public Result addHouse(@RequestBody House house){
        //调用业务层新增功能
        userService.addHouse(house);
        //响应
        return Result.success();
    }

    // 删除房源
    @DeleteMapping("/userHome/userhouse/delete")
    public Result deleteHouse(@RequestParam Integer houseid){
        //调用业务层删除功能
        userService.deleteHouse(houseid);
        //响应
        return Result.success();
    }

    // 修改房源
    @PutMapping("/userHome/userhouse/update")
    public Result updateHouse(@RequestBody House house){
        //调用业务层更新功能
        userService.updateHouse(house);
        //响应
        return Result.success();
    }

    // 查询所有房源
    @GetMapping("/userHome/houseShow")
    public Result houseAll(){
        //调用业务层查询功能
        List<House> house = userService.houseAll();
        //响应
        return Result.success(house);
    }

    // 根据小区名模糊查询查询房源
    @GetMapping("/userHome/houseShow/community")
    public Result houseCommunity(@RequestParam String community){
        //调用业务层查询功能
        List<House> house = userService.houseCommunity(community);
        //响应
        return Result.success(house);
    }

    // 租房
    @PutMapping("/userHome/userhouse/rental")
    public Result rentalHouse(@RequestParam Integer houseid,
                              @RequestParam String tenantname){
        //调用业务层更新功能
        userService.rentalHouse(houseid,tenantname);
        //响应
        return Result.success();
    }

    // 退租
    @PutMapping("/userHome/userhouse/terminate")
    public Result terminateHouse(@RequestParam Integer houseid){
        //调用业务层更新功能
        userService.terminateHouse(houseid);
        //响应
        return Result.success();
    }

    // 房东查询我的房屋
    @GetMapping("/landlord/userHouse")
    public Result landlordUserHouse(@RequestParam String username){
        return userHouse(username);
    }

    // 房东查询房子信息
    @GetMapping("/landlord/userhouse/detail")
    public Result landlordHouseDetail(@RequestParam Integer houseid){
        return houseDetail(houseid);
    }

    // 房东新增房源
    @PostMapping("/landlord/userhouse/add")
    public Result landlordAddHouse(@RequestBody House house){
        return addHouse(house);
    }

    // 房东删除房源
    @DeleteMapping("/landlord/userhouse/delete")
    public Result landlordDeleteHouse(@RequestParam Integer houseid){
        return deleteHouse(houseid);
    }

    // 房东修改房源
    @PutMapping("/landlord/userhouse/update")
    public Result landlordUpdateHouse(@RequestBody House house){
        return updateHouse(house);
    }

    // 房东查询自己的所有房源
    @GetMapping("/landlord/houseShow")
    public Result landlordHouseShow(@RequestParam String landlordName) {
        // 复用查询用户房子的逻辑
        List<House> house = userService.userHouse(landlordName);
        return Result.success(house);
    }

    // 房东根据小区名模糊查询自己的房源
    @GetMapping("/landlord/houseShow/community")
    public Result landlordHouseCommunity(@RequestParam String landlordName, @RequestParam String community) {
        // 这里可以根据业务需求实现具体的查询逻辑，暂时复用普通用户的小区名模糊查询逻辑
        List<House> house = userService.houseCommunity(community);
        // 后续可以根据 landlordName 进一步过滤房源
        return Result.success(house);
    }
}