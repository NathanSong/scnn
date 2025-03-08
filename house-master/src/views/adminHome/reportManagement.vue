<template>
  <div>
    <!-- 搜索栏 -->
    <div class="search">
      <el-form :inline="true" :model="searchInline" class="demo-form-inline" label="搜索">
        <el-form-item label="搜索内容">
          <el-input v-model="searchInline.keyword" placeholder="搜索举报人"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchOnSubmit">查询</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 表格部分 -->
    <el-table stripe :data="reports" style="width: 100%">
      <el-table-column type="index" width="50px" align="center"></el-table-column>
      <el-table-column prop="reportid" label="举报ID" width="100"></el-table-column>
      <el-table-column prop="reportdate" label="举报时间" width="150">
        <template slot-scope="scope">
          {{ formatDateTime(scope.row.reportdate) }}
        </template>
      </el-table-column>
      <el-table-column prop="reporter" label="举报人" width="120"></el-table-column>
      <el-table-column prop="reportee" label="被举报人" width="120"></el-table-column>
      <el-table-column prop="reporttitle" label="举报标题" width="150"></el-table-column>
      <el-table-column prop="reportcontent" label="举报内容"></el-table-column>
      <el-table-column prop="status" label="状态" width="120">
        <template slot-scope="scope">
          <el-tag :type="statusTagType(scope.row.status)">
            {{ translateStatus(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="mini"
            @click="handleApprove(scope.row.reportid)"
            :disabled="scope.row.status !== 'pending'"
          >通过</el-button>
          <el-button
            type="danger"
            size="mini"
            @click="handleReject(scope.row.reportid)"
            :disabled="scope.row.status !== 'pending'"
          >不通过</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <div class="block">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[2, 5, 10, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import dayjs from 'dayjs';
import utc from 'dayjs/plugin/utc';
import timezone from 'dayjs/plugin/timezone';

// 扩展 dayjs 插件
dayjs.extend(utc);
dayjs.extend(timezone);

export default {
  data() {
    return {
      reports: [], // 举报列表数据
      currentPage: 1, // 当前页码
      pageSize: 5, // 每页显示条数
      total: 0, // 总记录数
      searchInline: {
        keyword: "", // 搜索关键词
      },
    };
  },
  created() {
    this.fetchReports(); // 初始化时加载数据
  },
  methods: {
    // 获取举报列表数据
    fetchReports() {
      request({
        method: "get",
        url: "/report/all",
        params: {
          page: this.currentPage,
          pageSize: this.pageSize,
          keyword: this.searchInline.keyword, // 搜索关键词
        },
      }).then((response) => {
        this.reports = response.data.data.rows; // 分页数据
        this.total = response.data.data.total; // 总记录数
      });
    },
    // 处理分页大小变化
    handleSizeChange(val) {
      this.pageSize = val;
      this.currentPage = 1; // 重置为第一页
      this.fetchReports(); // 重新加载数据
    },
    // 处理当前页码变化
    handleCurrentChange(val) {
      this.currentPage = val;
      this.fetchReports(); // 重新加载数据
    },
    // 搜索提交
    searchOnSubmit() {
      this.currentPage = 1; // 重置当前页为第一页
      this.fetchReports(); // 重新加载数据
    },
    // 处理通过举报
    handleApprove(reportid) {
      this.updateReportStatus(reportid, "approved");
    },
    // 处理不通过举报
    handleReject(reportid) {
      this.updateReportStatus(reportid, "rejected");
    },
    // 更新举报状态
    updateReportStatus(reportid, status) {
      request({
        method: "put",
        url: `/report/updateStatus/${reportid}`,
        params: {
          status: status,
        },
      }).then(() => {
        this.fetchReports(); // 更新状态后重新加载数据
      });
    },
    // 根据状态返回标签类型
    statusTagType(status) {
      switch (status) {
        case "pending":
          return "warning";
        case "approved":
          return "success";
        case "rejected":
          return "danger";
        default:
          return "info";
      }
    },
    // 翻译状态
    translateStatus(status) {
      switch (status) {
        case "pending":
          return "审核中";
        case "approved":
          return "通过";
        case "rejected":
          return "不通过";
        default:
          return "未知状态";
      }
    },
    // 格式化时间
    formatDateTime(dateTime) {
      if (!dateTime) return '';
      // 将 UTC 时间转换为本地时间（北京时间）
      return dayjs.utc(dateTime).tz('Asia/Shanghai').format('YYYY-MM-DD HH:mm:ss');
    },
  },
};
</script>

<style lang="less" scoped>
.search {
  margin-top: 10px;
}

.block {
  margin-top: 20px;
  text-align: center; /* 分页组件水平居中 */
}
</style>