package com.songchengnan.service.impl;

import com.songchengnan.mapper.ReportMapper;
import com.songchengnan.pojo.Report;
import com.songchengnan.pojo.PageBean;
import com.songchengnan.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportMapper reportMapper;

    @Override
    public void addReport(Report report) {
        log.info("新增举报信息");
        report.setReportdate(new Date());
        report.setStatus("pending"); // 默认状态为审核中
        reportMapper.insertReport(report);
    }

    @Override
    public Report getReportById(Integer reportid) {
        log.info("查询举报信息");
        return reportMapper.getReportById(reportid);
    }

    @Override
    public PageBean getAllReports(Integer page, Integer pageSize) {
        log.info("分页查询所有举报信息");
        // 1. 查询总记录数
        Long count = reportMapper.countReports();
        // 2. 分页查询，获取列表数据
        Integer start = (page - 1) * pageSize;
        List<Report> reportList = reportMapper.pageReports(start, pageSize);
        // 3. 封装PageBean对象
        PageBean pageBean = new PageBean(count, reportList);
        return pageBean;
    }

    @Override
    public void deleteReport(Integer reportid) {
        log.info("删除举报信息");
        reportMapper.deleteReport(reportid);
    }

    @Override
    public void updateReportStatus(Integer reportid, String status) {
        log.info("更新举报状态");
        reportMapper.updateReportStatus(reportid, status);
    }
}