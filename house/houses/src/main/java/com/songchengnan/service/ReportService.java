package com.songchengnan.service;

import com.songchengnan.pojo.Report;
import com.songchengnan.pojo.PageBean;

import java.util.List;

public interface ReportService {
    void addReport(Report report);

    Report getReportById(Integer reportid);

    PageBean getAllReports(Integer page, Integer pageSize); // 修改为分页查询

    void deleteReport(Integer reportid);

    void updateReportStatus(Integer reportid, String status);
}