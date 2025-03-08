package com.songchengnan.controller;

import com.songchengnan.pojo.Report;
import com.songchengnan.pojo.Result;
import com.songchengnan.pojo.PageBean;
import com.songchengnan.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/add")
    public Result addReport(@RequestBody Report report) {
        reportService.addReport(report);
        return Result.success();
    }

    @GetMapping("/{reportid}")
    public Result getReportById(@PathVariable Integer reportid) {
        Report report = reportService.getReportById(reportid);
        return Result.success(report);
    }

    // 分页查询所有举报信息
    @GetMapping("/all")
    public Result getAllReports(@RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        PageBean pageBean = reportService.getAllReports(page, pageSize);
        return Result.success(pageBean);
    }

    @DeleteMapping("/delete/{reportid}")
    public Result deleteReport(@PathVariable Integer reportid) {
        reportService.deleteReport(reportid);
        return Result.success();
    }

    @PutMapping("/updateStatus/{reportid}")
    public Result updateReportStatus(@PathVariable Integer reportid, @RequestParam String status) {
        reportService.updateReportStatus(reportid, status);
        return Result.success();
    }
}