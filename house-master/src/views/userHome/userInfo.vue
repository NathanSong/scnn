<template>
  <div
    style="
      width: 700px;
      margin: auto;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
      padding: 30px;
    "
  >
    <p  style="font-size: 30px;text-align: center;margin-bottom: 20px;" >个人信息</p>
    <el-form ref="user" :model="user" label-width="80px">
      <el-form-item label="用户名">
        <el-input v-model="user.username" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="user.password" type="password"></el-input>
      </el-form-item>
      <el-form-item label="联系电话">
        <el-input v-model="user.userphone"></el-input>
      </el-form-item>
      <el-form-item label="身份找号">
        <el-input v-model="user.number" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit" 
          style="background-color: orange;border-color: orange;">
          <i class="el-icon-edit"></i>
          修改</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import request from "@/utils/request";
import router from "@/router";
export default {
  data() {
    return {
      user: {
        admin: "",
        username: "",
        password: "",
        number: "",
        userphone: "",
      },
    };
  },
  created() {
    this.getToken();
    this.getUserInfo();
  },
  methods: {
    getToken() {
      const token = localStorage.getItem("token");
      this.user = JSON.parse(token);
      if(this.user == null){
        router.push("/login");
      }
    },
    // 获取个人信息
    getUserInfo(){
      request({ 
        url: "/userHome/userInfo",
        method: "get",
        params: {
          username: this.user.username,
        },
      })
      .then((resp) => {
        this.user = resp.data.data;
      });
    },
    // 修改个人信息按钮
    onSubmit() {
      request({ 
        url: "/userHome/userInfo", 
        method: "put", 
        data: this.user 
      }).then(
        (resp) => {
          this.$message({
            showClose: true,
            message: "修改成功",
            type: "success",
          });
          // router.push("/");
        }
      );
    },
  },
};
</script>

<style>
</style>