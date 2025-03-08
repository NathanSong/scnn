<template>
  <div class="house-details">
    <el-dialog title="修改信息" :visible.sync="updatedialogVisible" width="40%" :before-close="handleClose">
      <el-form ref="form" :model="house" label-width="80px">
        <el-form-item label="小区">
          <el-input v-model="house.community" placeholder="请输入小区" maxlength="50" show-word-limit>
          </el-input>
        </el-form-item>
        <el-form-item label="房屋面积">
          <el-input v-model="house.area" placeholder="请输入房屋面积" show-word-limit>
          </el-input>
        </el-form-item>
        <el-form-item label="楼层">
          <el-input v-model="house.floor" placeholder="请输入楼层"  show-word-limit>
          </el-input>
        </el-form-item>
        <el-form-item label="价格">
          <el-input v-model="house.price" placeholder="请输入价格"  show-word-limit>
          </el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="house.address" placeholder="请输入地址" maxlength="50" show-word-limit>
          </el-input>
        </el-form-item>
        <el-form-item label="户型">
          <el-input v-model="house.type" placeholder="请输入户型" maxlength="50" show-word-limit>
          </el-input>
        </el-form-item>
        <el-form-item label="朝向">
          <el-input v-model="house.toward" placeholder="请输入朝向" maxlength="50" show-word-limit>
          </el-input>
        </el-form-item>
        <el-form-item label="周边">
          <el-input v-model="house.surrounding" placeholder="请输入周边" maxlength="50" show-word-limit>
          </el-input>
        </el-form-item>
        <el-form-item label="装修">
          <el-input v-model="house.decoration" placeholder="请输入装修" maxlength="50" show-word-limit>
          </el-input>
        </el-form-item>
        <el-form-item label="房东电话">
          <el-input v-model="house.housephone" placeholder="请输入房东电话" maxlength="20" show-word-limit>
          </el-input>
        </el-form-item>
        <el-form-item label="图片">
          <input type="file" @change="onFileChange" required />
          <img v-if="imagePreview" :src="imagePreview" alt="Preview" width="150" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="updatedialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateHouse">确 定</el-button>
      </span>
    </el-dialog>

    <div class="house-header">
      <img :src="'/images/' + house.image" alt="房屋图片" class="house-image" />
    </div>
    <div class="house-info">
      <h2>房屋详情</h2>
      <ul>
        <!-- <li><strong>状态：</strong>{{ house.statue }}</li> -->
        <li><strong>面积：</strong>{{ house.area }} 平方米</li>
        <li><strong>楼层：</strong>{{ house.floor }} 层</li>
        <li><strong>租金：</strong>{{ house.price }} 元/月</li>
        <li><strong>小区：</strong>{{ house.community }}</li>
        <li><strong>地址：</strong>{{ house.address }}</li>
        <li><strong>户型：</strong>{{ house.type }}</li>
        <li><strong>朝向：</strong>{{ house.toward }}</li>
        <li><strong>周边：</strong>{{ house.surrounding }}</li>
        <li><strong>装修：</strong>{{ house.decoration }}</li>
      </ul>
    </div>

    <div class="contact-info">
      <h2>联系方式</h2>
      <p><strong>房东姓名：</strong>{{ house.housename }}</p>
      <p><strong>联系电话：</strong>{{ house.housephone }}</p>
      <p v-if="house.tenantname !== ''"><strong>租客姓名：</strong>{{ house.tenantname }}</p>
    </div>

    <div class="action-buttons">
      <button v-if="this.user.username == house.housename" @click="editHouseButton" class="edit-button">
        <i class="el-icon-edit"></i>
        修改信息
      </button>
      <button v-if="this.user.username == house.housename" @click="deleteHouse" class="delete-button">
        <i class="el-icon-delete"></i>
        删除房源
      </button>
      <div>
        <button v-if="this.user.username != house.housename && house.statue == 0"
          @click="rentalHouseButton" class="rental-button">
          <i class="el-icon-check"></i>租房
        </button>
        <button v-else-if="this.user.username == house.tenantname"
          @click="terminateRentalButton" class="rental-button">
          <i class="el-icon-close"></i>退租
        </button>
        <el-button size="mini" type="danger" @click="openReportDialog(house.housename)" class="report-button">
          举报
        </el-button>
      </div>
    </div>

    <!-- 举报对话框 -->
    <el-dialog
      title="举报房屋"
      :visible.sync="reportDialogVisible"
      width="30%"
      :before-close="handleReportDialogClose"
    >
      <el-form ref="reportForm" :model="reportFormData" label-width="80px">
        <el-form-item label="举报标题">
          <el-input
            v-model="reportFormData.reporttitle"
            placeholder="请输入举报标题"
            maxlength="50"
            show-word-limit
          ></el-input>
        </el-form-item>
        <el-form-item label="举报内容">
          <el-input
            type="textarea"
            :rows="7"
            placeholder="请输入举报内容"
            maxlength="200"
            show-word-limit
            v-model="reportFormData.reportcontent"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="reportDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitReport">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";
export default {
  name: 'HouseDetails',
  data() {
    return {
      updatedialogVisible: false,
      imagePreview: null, // 用于图片预览
      house: {
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
        image: '',
      },
      reportDialogVisible: false,
      reportFormData: {
        reporter: '',
        reportee: '',
        reporttitle: '',
        reportcontent: ''
      }
    };
  },
  created() {
    this.getToken();
    this.house.houseid = this.$route.params.houseid;
    this.getHouseDetail();
  },
  methods: {
    getToken() {
      const token = localStorage.getItem("token");
      this.user = JSON.parse(token);
      if (this.user == null) {
        this.$router.push("/login");
      }
    },
    // 根据房源ID 获取房源详情
    getHouseDetail() {
      request({
        method: 'get',
        url: '/userHome/userhouse/detail',
        params: {
          houseid: this.house.houseid
        }
      }).then(resp => {
        this.house = resp.data.data;
      });
    },
    // 租房按钮
    rentalHouseButton() {
      this.$confirm('确认租房？')
        .then(_ => {
          request({
            method: 'put',
            url: '/userHome/userhouse/rental',
            params: {
              houseid: this.house.houseid,
              tenantname: this.user.username
            }
          }).then(resp => {
            this.$message({
              message: '租房成功',
              type: 'success'
            });
            this.getHouseDetail();
          });
        })
        .catch(_ => { });
    },
    // 退租按钮
    terminateRentalButton() {
      this.$confirm('确认退租？')
        .then(_ => {
          request({
            method: 'put',
            url: '/userHome/userhouse/terminate',
            params: {
              houseid: this.house.houseid
            }
          }).then(resp => {
            this.$message({
              message: '退租成功',
              type: 'success'
            });
            this.getHouseDetail();
          });
        })
        .catch(_ => { });
    },
    // 根据房源ID 删除房源信息
    deleteHouse() {
      this.$confirm("此操作将永久删除该信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        request({
          method: "delete",
          url: "/userHome/userhouse/delete",
          params: {
            houseid: this.house.houseid,
          },
        }).then((resp) => {
          this.$message({ type: "success", message: "删除成功!", });
          this.$router.push({ path: "/userHome/userHouse" });
        });
      }).catch(() => {
        this.$message({
          type: "info",
          message: "已取消删除",
        });
      });
    },
    // 修改房源按钮
    editHouseButton() {
      this.updatedialogVisible = true;
    },
    // 修改房源对话框关闭确认
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => { });
    },
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
          this.house.image = resp.data.data;
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
    // 跳转到编辑页面
    updateHouse() {
      request({
        method: "put",
        url: "/userHome/userhouse/update",
        data: this.house,
      }).then((resp) => {
        this.$message({
          message: "修改成功",
          type: "success",
        });
        this.updatedialogVisible = false;
        this.getHouseDetail();
      }).catch((error) => {
        this.$message({
          message: "修改失败",
          type: "error",
        });
      });
      this.updatedialogVisible = false;
    },
    // 打开举报对话框
    openReportDialog(reportee) {
      this.reportDialogVisible = true;
      this.reportFormData.reporter = this.user.username; // 设置举报人为当前用户
      this.reportFormData.reportee = reportee; // 设置被举报人
    },
    // 关闭举报对话框
    handleReportDialogClose() {
      this.reportDialogVisible = false;
    },
    // 提交举报
    submitReport() {
      request({
        method: "post",
        url: "/report/add", // 后端接口地址
        data: this.reportFormData // 提交举报数据
      }).then((resp) => {
        this.$message({
          message: "举报成功",
          type: "success"
        });
        this.reportDialogVisible = false; // 关闭对话框
        // 清空表单
        this.reportFormData = {
          reporter: '',
          reportee: '',
          reporttitle: '',
          reportcontent: ''
        };
      }).catch((error) => {
        this.$message({
          message: "举报失败",
          type: "error"
        });
      });
    }
  },
};
</script>

<style scoped>
.house-details {
  font-family: Arial, sans-serif;
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 10px;
  background: #f9f9f9;
}

.house-header {
  text-align: center;
  margin-bottom: 20px;
}

.house-image {
  max-width: 100%;
  height: auto;
  border-radius: 10px;
}

h1 {
  margin: 10px 0;
  font-size: 24px;
}

.house-info ul {
  list-style: none;
  padding: 0;
}

.house-info li {
  margin: 10px 0;
  line-height: 1.6;
}

.contact-info {
  margin-top: 20px;
  padding-top: 20px;
  border-top: 1px solid #ddd;
}

.action-buttons {
  margin-top: 20px;
  display: flex;
  justify-content: space-around;
}

.edit-button {
  padding: 10px 20px;
  background-color: #f3a406;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.edit-button:hover {
  background-color: #e1a01d;
}

.rental-button {
  padding: 10px 20px;
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.rental-button:hover {
  background-color: #218838;
}

.delete-button {
  padding: 10px 20px;
  background-color: #dc3545;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.delete-button:hover {
  background-color: #a71d2a;
}

.report-button {
  padding: 10px 20px;
  background-color: #ff4d4f;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-left: 10px;
}

.report-button:hover {
  background-color: #d9363e;
}
</style>