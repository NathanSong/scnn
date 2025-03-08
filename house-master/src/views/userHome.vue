<template>
  <div >
    <el-container>
      <el-header>
        <el-menu
          :default-active="activeIndex"
          class="el-menu-demo"
          mode="horizontal"
          @select="handleSelect"
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b"
          :router="true"
        >
          <el-menu-item index="/userHome/firstHome">首页</el-menu-item>
          <el-menu-item index="/userHome/houseShow">房屋展示</el-menu-item>
          <el-menu-item index="/userHome/userHouse">我的房屋</el-menu-item>
          <el-menu-item index="/userHome/userMessageBoard">留言板</el-menu-item>
          <el-menu-item index="/userHome/userInfo">个人中心</el-menu-item>
          <el-button
            type="danger"
            style="float: right; text-align: center; height: 50px; width: 80px;
              line-height: 25px; margin-top: 5px; margin-right: 10px;"
            @click="returnLogin">
            退出
          </el-button>
        </el-menu>
      </el-header>
      <el-main> 
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      activeIndex: "1",
    };
  },
  methods: {
    returnLogin() {
      localStorage.removeItem("token");
      window.location.href = "/";
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
  },
};
</script>

<style lang="less" scoped>
</style>
