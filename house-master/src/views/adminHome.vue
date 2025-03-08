<template>
  <div>
    <el-container>
      <el-header style="background-color: #009688; height: 56.22px;">
        <div style="position: relative">
          <span style="font-weight: bold; font-size: larger">房屋租赁管理系统</span>
          <el-button
            type="danger"
            style="float: right; margin-top: 10px"
            @click="returnLogin">
            退出
          </el-button>
        </div>
        <!-- 修改 el-menu 的 margin-top -->
        <el-menu
          :default-active="activeIndex"
          class="el-menu-demo"
          mode="horizontal"
          @select="handleSelect"
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b"
          :router="true"
          style="margin-top: -3.78px;" 
        >
          <el-menu-item index="/adminHome/firstpage">
            <i class="el-icon-document"></i>
            <span>主页</span>
          </el-menu-item>
          <el-menu-item index="/adminHome/adminMessageBoard">
            <i class="el-icon-setting"></i>
            <span>留言管理</span>
          </el-menu-item>
          <el-menu-item index="/adminHome/userAdmin">
            <i class="el-icon-setting"></i>
            <span>用户管理</span>
          </el-menu-item>
          <el-menu-item index="/adminHome/reportManagement">
            <i class="el-icon-warning"></i>
            <span>举报管理</span>
          </el-menu-item>
        </el-menu>
      </el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
      <el-footer>
        <!-- 移除了版权和链接内容 -->
      </el-footer>
    </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      activeIndex: "1"
    };
  },
  methods: {
    returnLogin() {
      localStorage.removeItem("token");
      window.location.href = "/";
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    }
  }
};
</script>

<style lang="less" scoped>
/* 使用 flex 布局，让内容占据剩余空间，将底部固定在最下方 */
.el-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

.el-header {
  background-color: #009688;
  color: #333;
  text-align: center;
  line-height: 56.22px; /* 与高度保持一致 */
  padding: 0;
  height: 56.22px; /* 原高度 60px 减去 3.78px（1毫米） */
}

.el-main {
  background-color: #fff;
  color: #333;
  text-align: center;
  padding: 0%;
  flex: 1; /* 让 main 部分占据剩余空间 */
}

.el-footer {
  background-color: #d3dce6;
  color: #333;
  text-align: center;
}

.el-menu {
  border-bottom: none;
}
</style>