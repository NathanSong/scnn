<template>
  <div class="container">
    <!-- 轮播图部分 -->
    <div class="img">
      <el-carousel indicator-position="outside">
        <el-carousel-item
          class="imgshow"
          v-for="item in imgList"
          :key="item.id"
        >
          <img :src="item.url" alt="" />
        </el-carousel-item>
      </el-carousel>
    </div>

    <!-- 卡片展示部分 -->
    <div class="card-container">
      <el-row>
        <el-col :span="6" :offset="0">
          <el-card :body-style="{ padding: '0px' }">
            <img src="./../../assets/5.jpg" class="image" />
            <div style="padding: 14px">
              <span>房屋类型：高档</span>
              <div class="bottom clearfix">
                <time class="time">{{ currentDate }}</time>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6" :offset="2">
          <el-card :body-style="{ padding: '0px' }">
            <img src="./../../assets/6.jpeg" class="image" />
            <div style="padding: 14px">
              <span>房屋类型：标准</span>
              <div class="bottom clearfix">
                <time class="time">{{ currentDate }}</time>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6" :offset="2">
          <el-card :body-style="{ padding: '0px' }">
            <img src="./../../assets/7.jpeg" class="image" />
            <div style="padding: 14px">
              <span>房屋类型：经济</span>
              <div class="bottom clearfix">
                <time class="time">{{ currentDate }}</time>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import dayjs from "dayjs";
import img1 from "@/assets/1.png";
import img2 from "@/assets/2.png";
import img3 from "@/assets/3.png";

export default {
  data() {
    return {
      currentDate: dayjs(new Date()).format("YYYY-MM-DD HH:mm:ss"), // 当前日期
      imgList: [
        { id: 1, url: img2 },
        { id: 2, url: img3 },
        { id: 0, url: img1 },
      ],
    };
  },
  methods: {},
};
</script>

<style>
.container {
  display: flex;
  flex-direction: column;
  align-items: center; /* 水平居中 */
  justify-content: flex-end; /* 垂直方向靠下 */
  height: 100vh; /* 使容器占满整个视口高度 */
  padding-bottom: 12.2px; /* 原 50px 减去 37.8px（1厘米） */
}

.img {
  width: 80%; /* 轮播图宽度 */
  margin: 20px auto 20px; /* 原 57.8px 减去 37.8px（1厘米） */
}

.card-container {
  max-width: 1200px; /* 限制卡片容器的最大宽度 */
  width: 100%; /* 使卡片容器宽度占满父容器 */
  text-align: center; /* 卡片内容居中 */
}

.el-row {
  display: flex;
  justify-content: center; /* 卡片水平居中 */
}

.el-col {
  margin: 0 10px; /* 卡片之间的间距 */
}

.image {
  width: 100%;
  height: 246.31px;
  display: block;
}

.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
</style>