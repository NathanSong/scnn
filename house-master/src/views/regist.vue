<<template>
  <div>
    <el-card class="box-card">
      <h2>注册</h2>
      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-position="left" label-width="80px"
        class="demo-ruleForm">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="ruleForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="身份证" prop="number">
          <el-input v-model="ruleForm.number"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="userphone">
          <el-input v-model="ruleForm.userphone"></el-input>
        </el-form-item>
        <!-- 判断是用户、管理员还是房东 -->
        <el-form-item label="角色">
          <el-radio v-model="ruleForm.admin" label="1">管理员</el-radio>
          <el-radio v-model="ruleForm.admin" label="2">普通用户</el-radio>
          <el-radio v-model="ruleForm.admin" label="3">房东</el-radio>
        </el-form-item>
      </el-form>
      <div class="btnGroup">
        <el-button type="primary" @click="submitForm('ruleForm')" style="margin-left:79px">提交</el-button>
        <el-button @click="resetForm('ruleForm')" style="margin-left:20px">重置</el-button>
        <el-button @click="goBack" style="margin-left:20px">返回</el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
import request from "../utils/request";
export default {
  data() {
    const validatePassword = (rule, value, callback) => {
      if (value.length < 1) {
        callback(new Error('请输入密码'));
      } else {
        callback()
      }
    };
    const validateUsername = (rule, value, callback) => {
      if (value.length < 1) {
        callback(new Error('请输入用户名'));
      } else {
        callback()
      }
    };

    return {
      ruleForm: {
        username: "",
        password: "",
        number: "",
        userphone: "",
        admin: "2",
      },
      rules: {
        username: [
          { required: true, message: "请输入用户名！", trigger: "blur", validator: validateUsername },
        ],
        password: [
          { required: true, message: "请输入密码！", trigger: "blur", validator: validatePassword },
        ],
      },
    };
  },
  methods: {
    // 提交注册表单
    submitForm(ruleForm) {
      console.log(this.ruleForm);
      if (this.ruleForm.username == "" || this.ruleForm.password == "") {
        this.$message({ message: "请填写完整信息", type: "error", });
        return;
      }
      request({
        method: "post",
        url: "/adminHome/userAdmin",
        data: {
          username: this.ruleForm.username,
          password: this.ruleForm.password,
          admin: this.ruleForm.admin,
          number: this.ruleForm.number,
          userphone: this.ruleForm.userphone,
        },
      }).then(
        (resp) => {
          if (resp.status == 200) {
            this.$router.push("/login");
            this.$message({ message: "注册成功", type: "success" });
          }
        }
      ).catch((error) => {
        console.error(error);
        this.$message({
          message: "用户名已存在",
          type: "error",
        });
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    goBack() {
      this.$router.go(-1);
    },
  },
};
</script>

<style scoped>
.box-card {
  margin: 70px auto;
  width: 400px;
}

.login-from {
  margin: auto auto;
}
</style>