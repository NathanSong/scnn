package com.songchengnan.service.impl;

import com.songchengnan.mapper.AdminMapper;
import com.songchengnan.pojo.Message;
import com.songchengnan.pojo.PageBean;
import com.songchengnan.pojo.User;
import com.songchengnan.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

//管理员业务实现类
@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void save(User user) {
        //调用添加方法
        adminMapper.insert(user);
    }

    @Override
    public void login(String username, String password, Integer admin) {
        //调用登录方法
        User user = adminMapper.login(username,password,admin);
        if (user == null) {
            // 登录失败处理
            throw new RuntimeException("登录失败：用户名或密码错误，或者权限不足");
        }
    }

    @Override
    public void deleteByUsername(String username) {
        //调用删除方法
        adminMapper.deleteByUsername(username);
    }

    @Override
    public PageBean page(Integer page, Integer pageSize) {
        // 1.查询总记录数
        Long count = adminMapper.count();
        // 2.分页查询,获取列表数据
        Integer start = (page-1)*pageSize;
        List<User> userList = adminMapper.page(start,pageSize);
        // 3.封装PageBean对象
        PageBean pageBean = new PageBean(count,userList);
        return pageBean;
    }

    @Override
    public void update(User user) {
        //调用更新方法
        adminMapper.update(user);
    }

    @Override
    public void addMessage(Message message) {
        if (message.getMessagename() == null || message.getMessagename().equals("")) {
            throw new RuntimeException("添加失败：请输入留言人");
        }
        if(message.getMessagetitle() == null || message.getMessagetitle().equals("")){
            throw new RuntimeException("添加失败：请输入标题");
        }
        //调用添加方法
        message.setDate(LocalDateTime.now());
        adminMapper.addMessage(message);
    }

    @Override
    public PageBean pageMessage(String estateName,Integer page, Integer pageSize) {
        // 1.查询总记录数
        Long count = 0L;
        if(estateName.equals("")){
            count = adminMapper.countMessage();
        }else {
            count = adminMapper.countNeedMessage(estateName);
        }

        // 2.分页查询,获取列表数据
        Integer start = (page-1)*pageSize;
        List<Message> messageList = adminMapper.pageMessage(estateName,start,pageSize);
        // 3.封装PageBean对象
        PageBean pageBean = new PageBean(count,messageList);
        return pageBean;
    }


    @Override
    public void deleteMessage(String messagetitle, String messagename) {
        //调用删除方法
        adminMapper.deleteMessage(messagetitle,messagename);
    }


        @Override
        public void registerLandlord(User user) {
            adminMapper.registerLandlord(user);
        }

        @Override
        public User landlordLogin(String username, String password) {
            return adminMapper.landlordLogin(username, password);
        }


}
