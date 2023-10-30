<template>
  <div>
    <el-container>
      <el-header>
        <el-menu class="el-menu-demo" mode="horizontal" @select="handleSelect">
          <router-link to="/base/cook-food-list"
            ><el-menu-item index="1">cookbook</el-menu-item></router-link
          >
          <router-link to="/base/cook-book-list"
            ><el-menu-item index="2">Complete book of materials</el-menu-item></router-link
          >
          <router-link to="/base/cook-book-make"
            ><el-menu-item index="3">Additive material</el-menu-item></router-link
          >
          <router-link to="/base/cook-food-make"
            ><el-menu-item index="4">Add recipe</el-menu-item></router-link
          >
        </el-menu></el-header
      >
      <el-main>
        <div class="content">
          <router-view></router-view>
        </div>
      </el-main>
    </el-container>
  </div>
</template>
<script>
export default {
  data() {
    return {};
  },
  mounted() {},
  methods: {
    alertSuccess(msg) {
      this.$message({
        message: msg,
        type: "success",
      });
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    logout() {
      window.localStorage.removeItem("token");
      this.$router.push({
        path: "/base/event",
      });
    },
  },
};
</script>
<style scoped>
.el-header {
  line-height: 66px;
  height: 66px !important;
  width: 100%;
}

.el-main {
  padding: 0;
}

.el-menu.el-menu--horizontal {
  width: 100%;
}

.router-link-active {
  text-decoration: none;
}

a {
  text-decoration: none;
  float: left;
}
</style>
