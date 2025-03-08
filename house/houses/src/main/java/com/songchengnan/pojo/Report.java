package com.songchengnan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    private Integer reportid; // 举报ID
    private String reporter; // 举报人
    private String reportee; // 被举报人
    private String reporttitle; // 举报标题
    private String reportcontent; // 举报内容
    private Date reportdate; // 举报时间
    private String status; // 审核状态: pending, approved, rejected
}