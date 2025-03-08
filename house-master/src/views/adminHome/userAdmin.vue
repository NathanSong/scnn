<template>
  <div>
    <!-- 搜索栏 -->
    <div class="search">
      <el-form :inline="true" :model="searchInline" class="demo-form-inline" label="搜索">
        <el-form-item label="搜索内容">
          <el-input v-model="searchInline.estateInline" placeholder="搜索"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchOnSubmit">查询</el-button>
          <el-button id="addUser" type="success" @click="addUser">新增</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-button style="margin-top: 10px;margin-right: -960px; display: none;" type="success" @click="addUser">
      新增用户
    </el-button>
    <el-table stripe :data="tableData" style="width: 100%">
      <el-table-column type="index" width="50px" align="center"> </el-table-column>
      <el-table-column prop="username" label="用户名" width="180">
      </el-table-column>
      <el-table-column prop="password" label="密码" width="180" >
      </el-table-column>
      <el-table-column prop="number" label="身份证号"> </el-table-column>
      <el-table-column prop="userphone" label="联系电话"> </el-table-column>
      <el-table-column prop="admin" label="身份">
        <template slot-scope="scopeAdmin">
          <span v-if="scopeAdmin.row.admin === 1">管理员</span>
          <span v-else-if="scopeAdmin.row.admin === 2">普通用户</span>
          <span v-else-if="scopeAdmin.row.admin === 3">房东</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" type="warning" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      title="修改用户"
      :visible.sync="updatedialogVisible"
      width="30%"
      :before-close="handleClose">
        <el-form :model="user" label-width="100px">
        <el-form-item label="密码" prop="password">
          <el-input v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item label="联系方式" prop="userphone">
          <el-input v-model.number="user.userphone"></el-input>
        </el-form-item>
        <el-form-item label="身份证号" prop="number">
            <el-input v-model.number="user.number"></el-input>
          </el-form-item>
        <el-form-item label="身份" prop="role.roleName">
          <el-select v-model="user.role.roleId" placeholder="请选择身份">
            <el-option
              v-for="item in role"
              :key="item.roleId"
              :label="item.roleName"
              :value="item.roleId"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="updatedialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="updatesubmit">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="新增用户"
      :visible.sync="AdddialogVisible"
      width="30%"
      :before-close="handleClose"
      ><el-form :model="user" label-width="100px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model.lazy="user.username"></el-input>
          {{ message }}
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="user.password" type="password"></el-input>
        </el-form-item>
        <el-form-item label="联系方式" prop="userphone">
          <el-input v-model.number="user.userphone"></el-input>
        </el-form-item>
        <el-form-item label="身份证号" prop="number">
            <el-input v-model.number="user.number"></el-input>
        </el-form-item>
        <el-form-item label="身份" prop="role.roleName">
          <el-select v-model="user.role.roleId" placeholder="请选择身份">
            <el-option
              v-for="item in role"
              :key="item.roleId"
              :label="item.roleName"
              :value="item.roleId"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="AdddialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addsubmit">确 定</el-button>
      </span>
    </el-dialog>
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
import deepClone from "@/utils/deepClone";
export default {
  data() {
    return {
      updatedialogVisible: false, // 修改用户的对话框是否可见
      AdddialogVisible: false, // 新增用户的对话框是否可见
      currentPage: 1,
      pageSize: 5,
      total: 50,
      message: "",
      user: {
        username: "",
        password: "",
        userphone: "",
        number: "",
        admin: 1,
        role: {
          roleId: 1,
          roleName: "管理员",
        },
      },
      role: [
        { roleId: 1, roleName: "管理员" },
        { roleId: 2, roleName: "普通用户" },
        { roleId: 3, roleName: "房东" },
      ],
      tableData: [
        {
          userId: 1,
          username: "zhangsan",
          password: "123456",
          userphone: "111111",
          number: "111111",
          admin: 1,
        },
      ],
      searchInline: {
        estateInline: ""
      }
    };
  },
  created() {
    this.userPage(this.currentPage, this.pageSize);
  },
  methods: {
    //获取用户列表
    userPage(currentPage, pageSize) {
      request({
        url: "/adminHome/userAdmin",
        method: "get",
        params: { 
          page: currentPage, 
          pageSize: pageSize,
          keyword: this.searchInline.estateInline
        },
      }).then((resp) => {
        this.total = resp.data.data.total;
        this.tableData = resp.data.data.rows;
        console.log(this.tableData);
      });
    },
    //新增用户默认值
    addUser() {
      this.user = {
        username: "",
        password: "",
        userphone: "",
        number: "",
        role: {
          roleId: 1,
          roleName: "管理员",
        },
      };
      this.AdddialogVisible = true;
    },
    //新增提交按钮
    addsubmit() {
      request({
        method: "post",
        url: "/adminHome/userAdmin",
        data: {
          username: this.user.username,
          password: this.user.password,
          admin: this.user.role.roleId,
          number: this.user.number,
          userphone: this.user.userphone,
        },
      }).then(
        (resp) => {
          if (resp.status === 200) {
            this.userPage(this.currentPage, this.pageSize);
            this.$message({ message: "用户添加成功", type: "success" });
            this.AdddialogVisible = false;
            this.userPage(this.currentPage, this.pageSize);
          }
        }
      ).catch((error) => {
        console.error(error);
        this.$message({
          message: "用户名已存在",
          type: "error",
        });
      });
      console.log(this.user);
    },
    // 修改用户信息回显
    handleEdit(row) {
      this.updatedialogVisible = true;
      this.user = {
        ...row, // 拷贝 row 的第一层
        role: {
              roleId: row.admin,
              roleName: row.admin === 1 ? "管理员" : row.admin === 2 ? "普通用户" : "房东",
            },
      };
    },
    //修改用户
    updatesubmit() {
      const updatedData = {
        username: this.user.username,
        password: this.user.password,
        userphone: this.user.userphone,
        number: this.user.number,
        admin: this.user.role.roleId, // 直接使用 role.roleId 映射到 admin
      };
      request({ 
        url: "/adminHome/userAdmin", 
        method: "put", 
        data: updatedData,
      }).then(
        (resp) => {
          this.$message({
            type: "success",
            message: "修改成功!",
          });
          this.userPage(this.currentPage, this.pageSize);
        }
      );
      this.updatedialogVisible = false;
    },
    //删除
    handleDelete(row) {
      this.$confirm("此操作将永久删除该信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
      .then(() => {
        request({
          method: "delete",
          url: "/adminHome/userAdmin/" + row.username,
        }).then((resp) => {
          this.$message({
            type: "success",
            message: "删除成功!",
          });
          this.userPage(this.currentPage, this.pageSize);
        });
      })
      .catch(() => {
        this.$message({
          type: "info",
          message: "已取消删除",
        });
      });
      console.log(row);
    },
    //分页尺寸改变
    handleSizeChange(val) {
      this.pageSize = val;
      this.userPage(this.currentPage, this.pageSize);
      // console.log(`每页 ${val} 条`);
    },
    //当前页
    handleCurrentChange(val) {
      this.currentPage = val;
      this.userPage(this.currentPage, this.pageSize);
      // console.log(`当前页: ${val}`);
    },
    //关闭对话框
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => { });
    },
    // 搜索提交
    searchOnSubmit() {
      this.currentPage = 1; // 重置当前页为第一页
      this.userPage(this.currentPage, this.pageSize);
      this.$message({
        message: "查询成功",
        type: "success",
      });
    }
  },
};
</script>

<style lang="less" scoped>
.search {
  margin-top: 10px;
}
</style>