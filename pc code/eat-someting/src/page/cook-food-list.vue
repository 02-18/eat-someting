<template>
  <div>
    <div class="container">
      <el-row :gutter="20" justify="center" align="middle">
        <el-col :span="6" v-for="(item, index) in list" :key="index">
          <div class="grid-content bg-purple">
            <div
              @click="showTips(item)"
              style="
                width: 100px;
                height: 120px;
                float: left;
                background-color: aliceblue;
              "
            >
              <img
                :src="NETWORK + item.image"
                style="width: 100%; height: 100%; border: 1px solid #ebeef5"
              />
            </div>
            <div
              style="
                width: 170px;
                height: 120px;
                float: right;
                border: 1px solid #ebeef5;
                text-align: center;
              "
            >
              <div
                style="
                  width: 140px;
                  height: 90px;
                  line-height: 30px;
                  margin: 0 auto;
                  font-size: 14px;
                  font-weight: 600;
                  display: -webkit-box;
                  overflow: hidden;
                  text-overflow: ellipsis;
                  -webkit-line-clamp: 3;
                  -webkit-box-orient: vertical;
                "
              >
                {{ item.name }}
              </div>
              <el-button type="danger" size="small" @click="deleteCookBook(item)"
                >delete</el-button
              >
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <div class="container-footer">
      <el-pagination
        background
        layout="prev, pager, next"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-size="pageSize"
        :total="total"
      >
      </el-pagination>
    </div>
    <div>
      <el-dialog :title="tipTitle" :visible.sync="centerDialogVisible" width="50%" center>
        <div>
          <el-form :model="dynamicValidateForm" label-width="100px" class="demo-dynamic">
            <el-form-item
              v-for="(domain, index) in dynamicValidateForm.domains"
              :label="'procedure' + (index + 1)"
              :key="domain.key"
            >
              <el-input v-model="domain.value"></el-input
              ><el-button @click.prevent="removeDomain(domain)">delete</el-button>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm()">submit</el-button>
              <el-button @click="addDomain">New step</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import api from "@/utils/api";

export default {
  data() {
    return {
      tipTitle: "",
      menuId: "",
      list: [],
      tipList: [],
      total: 0,
      pageSize: 12,
      currentPage: 1,
      NETWORK: api.BASE_URL,
      centerDialogVisible: false,
      dynamicValidateForm: {
        domains: [
          {
            value: "",
          },
        ],
      },
    };
  },
  created() {},
  mounted() {
    this.getMenuList(this.currentPage, this.pageSize);
  },
  methods: {
    showTips(item) {
      this.tipTitle = item.name;
      this.menuId = item.id;
      this.centerDialogVisible = true;
    },
    submitForm() {
      console.log(this.dynamicValidateForm.domains);
      this.tipList = this.dynamicValidateForm.domains;
      let str = "";
      this.tipList.forEach((element) => {
        str += element.value + ",";
      });
      str = str.slice(0, str.length - 1);
      console.log(str);
      api.addMenuStep(this.menuId, str).then((result) => {
        if (result.code === 200) {
          this.$message({
            message: result.msg,
            type: "success",
          });
        } else {
          this.$message({
            message: result.msg,
            type: "error",
          });
        }
        this.dynamicValidateForm.domains.forEach((element) => {
          element.value = "";
        });
        this.centerDialogVisible = false;
      });
    },
    removeDomain(item) {
      var index = this.dynamicValidateForm.domains.indexOf(item);
      if (index !== -1) {
        this.dynamicValidateForm.domains.splice(index, 1);
      }
    },
    addDomain() {
      this.dynamicValidateForm.domains.push({
        value: "",
        key: Date.now(),
      });
    },
    deleteCookBook(e) {
      api.deleteMenu(e.id).then((result) => {
        if (result.code === 200) {
          this.$message({
            message: result.msg,
            type: "success",
          });
          this.getMenuList(this.currentPage, this.pageSize);
        }
      });
    },
    getMenuList(currentPage, pageSize) {
      api.menuList(currentPage, pageSize).then((result) => {
        this.total = result.data.total;
        this.list = result.data.list;
      });
    },

    handleCurrentChange(val) {
      this.currentPage = val;
      this.getMenuList(this.currentPage, this.pageSize);
    },
  },
};
</script>
<style scoped>
.container-footer {
  text-align: center;
  width: 1000px;
  margin: 0 auto;
  min-height: 30px;
  display: block;
  clear: both;
}

.el-col {
  border-radius: 4px;
  height: 140px;
  padding: 10px;
  border-radius: 4px;
  border: 1px solid #ebeef5;
  background-color: #fff;
  overflow: hidden;
  color: #303133;
  transition: 0.3s;
  margin-bottom: 60px;
}

.grid-content {
  border-radius: 4px;
  min-height: 120px;
}

.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}

.container {
  width: 1200px;
  height: 600px;
  display: block;
  clear: both;
  margin: 5px auto;
  padding: 0 20px;
}
</style>
