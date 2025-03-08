package com.songchengnan.mapper;

import com.songchengnan.pojo.Report;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReportMapper {
    @Insert("INSERT INTO report (reporter, reportee, reporttitle, reportcontent, reportdate, status) " +
            "VALUES (#{reporter}, #{reportee}, #{reporttitle}, #{reportcontent}, #{reportdate}, #{status})")
    void insertReport(Report report);

    @Select("SELECT * FROM report WHERE reportid = #{reportid}")
    Report getReportById(Integer reportid);

    @Select("SELECT COUNT(*) FROM report")
    Long countReports(); // 查询总记录数

    @Select("SELECT * FROM report LIMIT #{start}, #{pageSize}")
    List<Report> pageReports(@Param("start") Integer start, @Param("pageSize") Integer pageSize); // 分页查询

    @Delete("DELETE FROM report WHERE reportid = #{reportid}")
    void deleteReport(Integer reportid);

    @Update("UPDATE report SET status = #{status} WHERE reportid = #{reportid}")
    void updateReportStatus(@Param("reportid") Integer reportid, @Param("status") String status);
}