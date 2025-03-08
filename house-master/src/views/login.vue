<template>
  <div class="body-all">
    <el-link type="primary" :underline="false">
      <h1>{{ nowtime }}</h1>
    </el-link>
    <br />
    <img src="./../assets/8.jpeg" alt="" style="height: 90%;"/>
    <!-- <img
      src="./../assets/房子.png"
      alt=""
      style="
        position: absolute;
        width: 10%;
        margin-left: 200px;
        margin-top: -20px;
      "
    /> -->
    <div class="login_container">
      <h1>登陆页面</h1>
      <el-form :label-position="labelPosition" label-width="80px" :model="user">
        <el-form-item label="用户名">
          <el-input type="text" v-model.trim="user.username"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input type="password" v-model.trim="user.password"></el-input>
        </el-form-item>
        <el-form-item label="角色">
          <el-radio v-model.trim="user.admin" label="1">管理员</el-radio>
          <el-radio v-model.trim="user.admin" label="2">普通用户</el-radio>
          <el-radio v-model.trim="user.admin" label="3">房东</el-radio>
        </el-form-item>
        <el-form-item class="button-two">
          <el-button type="primary" @click="submitForm">登录</el-button>
          <el-button @click="resetForm">重置</el-button>
          <el-button  @click="registForm">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import dayjs from "dayjs";
import request from "../utils/request";

export default {
  data() {
    return {
      labelPosition: "right",
      user: {
        username: "",
        password: "",
        admin: "1",
      },
      nowtime: "2024-12-1",
    };
  },
  methods: {
    // 注册按钮的方法
    registForm() {
      this.$router.push("/regist");
    },
    // 重置按钮的方法
    resetForm() {
      this.user.username = "";
      this.user.password = "";
      this.$message({
        message: "重置成功",
        type: "success",
      });
    },
    // 登录提交方法
    submitForm() {
      console.log(this.user);
      request({ 
        method: "get",
        url: "/login",
        params:{
          username: this.user.username,
          password: this.user.password,
          admin: this.user.admin
        },
      })
      .then(
        (resp) => {
          if (resp.status == 200 && this.user.admin == 1) {
            this.$router.push("/adminHome");
            this.$message({ message: "登陆成功", type: "success" });
            // 将用户信息存储到localStorage中
            const userJson = JSON.stringify(this.user);
            localStorage.setItem("token", userJson);
          } else if (resp.status == 200 && this.user.admin == 2) {
            this.$message({ message: "登陆成功", type: "success" });
            const userJson = JSON.stringify(this.user);
            this.$router.push("/userHome");
            localStorage.setItem("token", userJson);
          } else if (resp.status == 200 && this.user.admin == 3) {
            this.$message({ message: "登陆成功", type: "success" });
            const userJson = JSON.stringify(this.user);
            // 假设房东登录后跳转的页面路径为 /landlordHome
            this.$router.push("/landlordHome"); 
            localStorage.setItem("token", userJson);
          } else {
            this.$message({
              message: "用户名或密码错误",
              type: "error",
            });
          }
        }
      )
      .catch((error) => {
        console.error(error);
        this.$message({
          message: "用户名或密码错误",
          type: "error",
        });
      });
    },
  },
  created() {
    this.timeId = setInterval(() => {
      this.nowtime = dayjs(new Date()).format("YYYY-MM-DD HH:mm:ss");
    }, 1000);
  },
};
</script>

<style lang="less" scoped>
.body-all {
  position: relative;
  width:100%;
  height: 700px;
  background-color: rgb(246, 246, 246);
}
.login_container {
  border: 1px solid black;
  padding: 10px 20px 10px 20px;
  margin: -550px 100px 100px 675px;
  width: 500px;
  > h1 {
    padding-left: 200px;
  }
  .password {
    padding-left: 400px;
  }
  .button-two {
    margin-top: -10px;
    .el-button {
      margin-right: 40px;
    }
  }
}
</style>