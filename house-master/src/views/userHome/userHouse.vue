<template>
  <div >
    <el-dialog title="添加房源" :visible.sync="AdddialogVisible" width="40%" :before-close="handleClose">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="小区">
            <el-input v-model="form.community" placeholder="请输入小区" 
              maxlength="50" show-word-limit>
            </el-input>
        </el-form-item>
        <el-form-item label="房屋面积">
            <el-input v-model="form.area" placeholder="请输入房屋面积" ></el-input>
        </el-form-item>
        <el-form-item label="楼层">
            <el-input v-model="form.floor" placeholder="请输入楼层" ></el-input>
        </el-form-item>
        <el-form-item label="价格">
            <el-input v-model="form.price" placeholder="请输入价格"></el-input>
        </el-form-item>
        <el-form-item label="地址">
            <el-input v-model="form.address" placeholder="请输入地址" 
              maxlength="50" show-word-limit>
            </el-input>
        </el-form-item>
        <el-form-item label="户型">
            <el-input v-model="form.type" placeholder="请输入户型" 
              maxlength="50" show-word-limit>
            </el-input>
        </el-form-item>
        <el-form-item label="朝向">
            <el-input v-model="form.toward" placeholder="请输入朝向" 
              maxlength="50" show-word-limit>
            </el-input>
        </el-form-item>
        <el-form-item label="周边">
            <el-input v-model="form.surrounding" placeholder="请输入周边" 
              maxlength="50" show-word-limit>
            </el-input>
        </el-form-item>
        <el-form-item label="装修">
            <el-input v-model="form.decoration" placeholder="请输入装修" 
              maxlength="50" show-word-limit>
            </el-input>
        </el-form-item>
        <el-form-item label="房东电话">
            <el-input v-model="form.housephone" placeholder="请输入房东电话" 
              maxlength="20" show-word-limit>
            </el-input>
        </el-form-item>
        <el-form-item label="图片">
              <input type="file" @change="onFileChange" required />
              <img v-if="imagePreview" :src="imagePreview" alt="Preview" width="150" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
          <el-button @click="AdddialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="addSubmit">确 定</el-button>
      </span>
  </el-dialog>


    <!-- 房源列表 -->
    <div style="text-align: right;margin-right: 20%; ">
      <button @click="addHouseButton" class="addHouse" center>
        <i class = "el-icon-circle-plus">添加房源</i>
      </button>
    </div>
    <div class="content">
      <div class="card" v-for="house in houses" :key="house.houseid" @click="goToDetail(house.houseid)">
        <!-- <img src="/images/a0430385-7d21-4cae-afea-b6832b915557.jpg" alt="" /> -->
        <img :src="'/images/' + house.image" alt="房屋图片">
        <div class="card-body">
          <span class="status"
            :class="{
              notRented: house.statue == 0,  rented: house.statue == 1, 
              noStatus: house.statue == 2 }">
            <span v-if="house.statue == 0">未出租</span>
            <span v-else-if="house.statue == 1">已出租</span>
            <span v-else>本人租住</span>
          </span>
            <p>{{ house.community }} <span></span> ￥{{ house.price }} / 月</p>
            <p>面积：{{ house.area }}/m²</p>
            <p>房型：{{ house.type }}</p>
            <p>地址：{{ house.address }}</p>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import request from "@/utils/request";
export default {
  data() {
    return {
      AdddialogVisible: false,
      user: {
        username:"",
      },
      houses: [
        {
          houseid: 1,
          statue: 0,
          area: 20,
          floor: 1,
          price: 2370,
          community: '华策中心城',
          address: '三号线 吉祥站 花园小区',
          type: '一室一厅',
          toward: '正南朝向',
          surrounding: '近地铁',
          decoration: '精装修',
          housename: '张三',
          housephone: '13812345678',
          tenantname: '李四',
          image: 'https://via.placeholder.com/300x150',
        },
      ],
      // 添加房源表单
      form: {
        statue: 0,// 0未出租 1已出租 2本人租住
        community: "",
        area: "",
        floor: "",
        price: "",
        address: "",
        type: "",
        toward: "",
        surrounding: "",
        decoration: "",
        housename: "",
        housephone: "",
        tenantname: "",
      },
      imagePreview: null, // 用于图片预览
    };
  },
  created() {
    this.getToken();
    this.getUserHouse();
  },
  methods: {
    // 添加房源按钮显示添加房源对话框
    addHouseButton() {
      this.AdddialogVisible = true;
    },
    // 新增房源对话框关闭确认
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => { });
    },
    // 上传图片并返回图片路径
    onFileChange(event) {
      const file = event.target.files[0];
      if (file) {
        const formData = new FormData();
        formData.append("image", file);
        request({
          method: "post",
          url: "/upload",
          data: formData,
        }).then((resp) => {
          this.form.image = resp.data.data;
          this.$message({
            message: "图片上传成功",
            type: "success",
          });
        }).catch((error) => {
          this.$message({
            message: "图片上传失败",
            type: "error",
          });
        });
        this.imagePreview = URL.createObjectURL(file);
      }
    },
    // 新增房源提交到后端
    addSubmit() {
      this.form.housename = this.user.username;
      request({
        method: "post",
        url: "/userHome/userhouse/add",
        data: this.form,
      }).then((resp) => {
        this.$message({
          message: "新增成功",
          type: "success",
        });
        this.AdddialogVisible = false;
        this.getUserHouse();
        // 清空表单
        this.imagePreview = "";
        this.form = {
          community: "",
          area: "",
          floor: "",
          price: "",
          address: "",
          type: "",
          toward: "",
          surrounding: "",
          decoration: "",
          housename: "",
          housephone: "",
          tenantname: "",
        };
      }).catch((error) => {
        this.$message({
          message: "新增失败",
          type: "error",
        });
      });
    },
    getToken() {
      const token = localStorage.getItem("token");
      this.user = JSON.parse(token);
      if (this.user == null) {
        router.push("/login");
      }
    },
    // 查询用户的房子-每次进入页面都会调用
    getUserHouse(){
      request({
        method: "get",
        url: "/userHome/userHouse",
        params: {
          username: this.user.username,
        }
      }).then((response) => {
        this.houses = response.data.data;
      });
    },
    goToDetail(houseid) {
      this.$router.push({ path: `userhouse/detail/${houseid}` });
    },
    
  },

}
</script>

<style>

.content {
  margin-left: 10px;
  flex: 1;
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.card {
  border: 1px solid #ddd;
  border-radius: 8px;
  overflow: hidden;
  position: relative;
  width: 350px;
  cursor: pointer;
  transition: transform 0.2s ease;
}

.card:hover {
    transform: scale(1.02); /* 鼠标悬停时微微放大 */
}

.card img {
  width: 100%;
  height: 150px;
  object-fit: cover;
  display: block;
}

.card-body {
  margin: 5px;
  padding: 15px; 
  box-sizing: border-box;
}

.status {
  background-color: green;
  color: white;
  padding: 3px 3px;
  border-radius: 5px;
  font-size: 13px;
  position: absolute;
  line-height: 20px;
  top: 10px;
  right: 10px;
  z-index: 1; /* 确保显示在卡片顶部 */
}
/* 未出租 */
.status.rented {
  background-color: red;
}
/* 我租的房子 */
.status.noStatus {
  background-color: orange;
}

p {
  font-size: 16px;
  margin: 5px 0; /* 添加适当的间距 */
  line-height: 20px;
  text-align: left;
}

.addHouse {
  margin-bottom: 10px;
  padding: 8px 10px;
  background-color: #67C23A;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
.addHouse:hover {
  background-color: #0056b3;
}



</style>