<template>
    <div>
        <div class="container">
            <el-row :gutter="20" justify="center" align="middle">
                <el-col :span="6" v-for="(item, index) in list" :key="index">
                    <div class="grid-content bg-purple">
                        <div style="width: 100px;height: 120px;float: left;background-color: aliceblue;">
                            <img :src="NETWORK + item.image" style="width: 100%;height: 100%;border: 1px solid #ebeef5;">
                        </div>
                        <div style="width: 170px;height: 120px;float: right;border: 1px solid #ebeef5;text-align: center;">
                            <div
                                style="width: 140px;height: 30px;line-height:30px;margin: 10px auto;font-size: 14px;font-weight: 600;">
                                {{ item.name }}</div>
                            <div style="width: 140px;height: 20px;line-height:20px;margin: 10px auto;font-size: 13px;">
                                {{ item.createDate }}
                            </div>
                            <el-button type="danger" size="small" @click="deleteCookBook(item)">delete</el-button>
                        </div>
                    </div>
                </el-col>
            </el-row>
        </div>
        <div class="container-footer">
            <el-pagination background layout="prev, pager, next" @current-change="handleCurrentChange"
                :current-page="currentPage" :page-size="pageSize" :total="total">
            </el-pagination>
        </div>
    </div>
</template>
<script>
import api from '@/utils/api'

export default {
    data() {
        return {
            list: [],
            total: 0,
            pageSize: 12,
            currentPage: 1,
            NETWORK: api.BASE_URL

        }
    },
    created() {
    },
    mounted() {
        this.getCookBookList(this.currentPage, this.pageSize)
    },
    methods: {
        deleteCookBook(e) {
            api.deleteCookBook(e.id).then(result => {
                if (result.code === 200) {
                    this.$message({
                        message: result.msg,
                        type: 'success'
                    });
                    this.getCookBookList(this.currentPage, this.pageSize)
                }
            })
        },
        getCookBookList(currentPage, pageSize) {
            api.ingredientList(currentPage, pageSize).then(result => {
                this.total = result.data.total
                this.list = result.data.list
            })
        },

        handleCurrentChange(val) {
            this.currentPage = val;
            this.getCookBookList(this.currentPage, this.pageSize)
        },
    }
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
    transition: .3s;
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