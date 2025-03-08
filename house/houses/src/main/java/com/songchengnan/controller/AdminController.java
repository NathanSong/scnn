package com.songchengnan.controller;

import com.songchengnan.pojo.Result;
import com.songchengnan.pojo.Message;
import com.songchengnan.pojo.PageBean;
import com.songchengnan.pojo.User;
import com.songchengnan.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
// 管理员控制器
@CrossOrigin(origins = "http://localhost:8080")// 允许跨域
@RestController
//@RequestMapping("/adminHome")
public class AdminController {

    @Autowired
    private AdminService adminService;


    //登录
    @GetMapping("/login")
    public Result login(@RequestParam String username, @RequestParam String password,@RequestParam Integer admin){
        //调用业务层新增功能
        adminService.login(username,password,admin);
        return Result.success();
    }


    //新增用户
    @PostMapping("/adminHome/userAdmin")
    public Result save(@RequestBody User user){
        //调用业务层新增功能
        adminService.save(user);
        //响应
        return Result.success();
    }


    // 删除用户-根据用户名
    @DeleteMapping("/adminHome/userAdmin/{username}")
    public Result deleteByUsername(@PathVariable String username){
        //调用业务层删除功能
        adminService.deleteByUsername(username);
        //响应
        return Result.success();
    }

    // 查询所有用户
    @GetMapping("/adminHome/userAdmin")
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize){
        // 调用service查询
        PageBean pageBean = adminService.page(page,pageSize);
        return Result.success(pageBean);
    }

    // 更新用户信息
    @PutMapping("/adminHome/userAdmin")
    public Result update(@RequestBody User user){
        //调用业务层更新功能
        adminService.update(user);
        //响应
        return Result.success();
    }

    // 添加留言
    @PostMapping("/adminHome/adminMessageBoard")
    public Result addMessage(@RequestBody Message message){
        //调用业务层新增功能
        adminService.addMessage(message);
        //响应
        return Result.success();
    }


    // 查询留言 - 搜索/所有
    @GetMapping("/adminHome/adminMessageBoard")
    public Result pageMessage(@RequestParam(defaultValue = "") String estateName,
                              @RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "10") Integer pageSize){
        // 调用service查询
        PageBean pageBean = adminService.pageMessage(estateName,page,pageSize);
        return Result.success(pageBean);
    }

    // 删除留言- 根据messagetitle & messagename
    @DeleteMapping("/adminHome/adminMessageBoard")
    public Result deleteMessage(@RequestParam String messagetitle, @RequestParam String messagename){
        //调用业务层删除功能
        adminService.deleteMessage(messagetitle,messagename);
        //响应
        return Result.success();
    }

    @PostMapping("/registerLandlord")
    public String registerLandlord(@RequestBody User user) {
        try {
            adminService.registerLandlord(user);
            return "房东注册成功";
        } catch (Exception e) {
            return "房东注册失败：" + e.getMessage();
        }
    }

    @GetMapping("/landlordLogin")
    public String landlordLogin(@RequestParam String username, @RequestParam String password) {
        User landlord = adminService.landlordLogin(username, password);
        if (landlord != null) {
            return "房东登录成功";
        } else {
            return "房东登录失败：用户名或密码错误";
        }
    }


}
