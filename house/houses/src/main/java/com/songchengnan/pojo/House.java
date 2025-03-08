package com.songchengnan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// 房子信息实体类
public class House {
    Integer houseid; // 房子ID
    Integer statue; // 房子是否被使用 1是 0不是
    Integer area; // 房子面积
    Integer floor; // 房子楼层
    Integer price; // 房子价格
    String community; // 房子所在小区
    String address; // 房子地址
    String type; // 房子类型
    String toward; // 房子朝向
    String surrounding; // 房子周边
    String decoration; // 房子装修
    String housename; // 房子拥有者姓名
    String housephone; // 房子拥有者电话
    String tenantname; // 房子租客姓名
    String image; // 房子图片


}
