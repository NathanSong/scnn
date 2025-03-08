<template>
  <div class="build-container" style="
            width: 1200px; margin: auto;padding: 30px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
        ">
    <!-- 搜索栏 -->
    <div class="search" align="center">
      <el-form :inline="true" :model="searchInline" class="demo-form-inline" label="搜索">
        <el-form-item label="搜索内容">
          <el-input v-model="searchInline.estateInline" placeholder="请输入关键字"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchOnSubmit">查询</el-button>
          <el-button id="addMessage" type="success" @click="addMessage">新增</el-button>
        </el-form-item>
      </el-form>

      <el-dialog title="新增留言" :visible.sync="AdddialogVisible" width="30%" :before-close="handleClose">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="标题">
            <el-input v-model="form.messagetitle" placeholder="请输入标题" maxlength="30" show-word-limit>
            </el-input>
          </el-form-item>
          <el-form-item label="内容">
            <el-input type="textarea" :rows="7" placeholder="请输入内容" maxlength="190" show-word-limit
              v-model="form.content">
            </el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="AdddialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="addSubmit">确 定</el-button>
        </span>
      </el-dialog>
    </div>
    <!-- 表格 -->
    <div>
      <el-table :data="tableData" stripe style="width: 100%" :default-sort="{ prop: 'date', order: 'descending' }">
        <el-table-column type="index" width="50px" align="center"></el-table-column>
        <el-table-column prop="date" label="时间" width="170px" align="center" sortable></el-table-column>
        <el-table-column prop="messagename" label="留言人" width="120px" align="center" sortable>
        </el-table-column>
        <el-table-column prop="messagetitle" label="标题" width="250px" align="center"></el-table-column>
        <el-table-column prop="content" label="内容" width="400px"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button v-if="scope.row.messagename == currentUser" size="mini" type="danger"
              @click="handleDelete(scope.row)"> 删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页栏 -->
    <div class="block">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
        :page-sizes="[5, 10, 15, 20]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
export default {
  data() {
    const userObject = JSON.parse(localStorage.getItem("token"));
    return {
      // 当前登录用户的用户名
      currentUser: userObject.username,
      dialogVisible: false,
      form: {
        date: new Date(),
        messagename: userObject.username,
        messagetitle: "",
        content: "",
      },
      AdddialogVisible: false,//新增对话框
      currentPage: 1,
      pageSize: 10,
      total: 100,
      searchInline: {
        estateInline: "",
      },
      tableData: [],
    };
  },
  created() {
    this.messageAll(this.searchInline.estateInline, this.currentPage, this.pageSize);
  },
  methods: {
    //查询提交
    searchOnSubmit() {
      this.currentPage = 1; // 重置当前页为第一页
      this.messageAll(this.searchInline.estateInline, this.currentPage, this.pageSize);
      this.$message({
        message: "查询成功",
        type: "success",
      });
      //this.searchInline.estateInline = "";
    },
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => { });
    },
    //新增按钮
    addMessage() {
      this.AdddialogVisible = true;
    },
    //新增留言提交按钮
    addSubmit() {
      if (this.form.messagetitle == "" || this.form.content == "") {
        this.$message({
          message: "请输入标题和内容",
          type: "error",
        });
        return;
      }
      request({
        method: "post",
        url: "/adminHome/adminMessageBoard",
        data: this.form,
      }).then((resp) => {
        this.$message({
          message: "新增成功",
          type: "success",
        });
        this.form = {
          messagename: this.currentUser,
          messagetitle: "",
          content: "",
        };
        this.AdddialogVisible = false;
        this.messageAll(this.searchInline.estateInline, this.currentPage, this.pageSize);
      }).catch((error) => {
        this.$message({
          message: "请输入标题和内容",
          type: "error",
        });
      });
    },
    //删除留言
    handleDelete(row) {
      this.$confirm("此操作将永久删除该信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          request({
            method: "delete",
            url: "/adminHome/adminMessageBoard",
            params: {
              messagetitle: row.messagetitle,
              messagename: row.messagename,
            },
          }).then((resp) => {
            this.$message({ type: "success", message: "删除成功!", });
            this.messageAll(this.searchInline.estateInline, this.currentPage, this.pageSize);
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    //分页查询所有
    messageAll(estateInline, currentPage, pageSize) {
      request({
        url: "/adminHome/adminMessageBoard",
        method: "get",
        params: {
          estateName: estateInline,
          page: currentPage,
          pageSize: pageSize,
        },
      }).then((resp) => {
        this.total = resp.data.data.total;
        this.tableData = resp.data.data.rows;
        if (this.total == 0) {
          this.$message({
            message: "未查询到相关信息",
            type: "warning",
          });
        }
      });
    },
    //改变pagesize
    handleSizeChange(val) {
      this.pageSize = val;
      this.messageAll(this.searchInline.estateInline, this.currentPage, this.pageSize);
      // console.log(`每页 ${val} 条`);
    },
    //改变currentPage
    handleCurrentChange(val) {
      // console.log(`当前页: ${val}`);
      this.currentPage = val;
      this.messageAll(this.searchInline.estateInline, this.currentPage, this.pageSize);
    },
  }
};
</script>

<style lang="less" scoped>
.search {
  margin-top: 10px;
}
</style>