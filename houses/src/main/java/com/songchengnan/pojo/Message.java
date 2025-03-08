package com.songchengnan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String messagename; // 留言人
    private String messagetitle; // 留言标题
    private String content; // 留言内容
    private LocalDateTime date; // 留言时间

}
