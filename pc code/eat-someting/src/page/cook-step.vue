<template>
    <div class="container">
        <div class="div-first">
            <div class="left-div">
                <el-upload class="avatar-uploader" action="http://localhost:40010/api/eat/upload/picture"
                    :show-file-list="false" :on-success="onSuccess">
                    <img v-if="menu.image" :src="menu.image" class="avatar">
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
            </div>
            <div class="right-div">
                <div style="width: 600px;margin: 50px auto;">
                    <el-input v-model="menu.name" placeholder="Please enter the recipe name"></el-input>
                </div>
                <div style="width: 600px;margin: 50px auto;">
                    <el-input v-model="menu.type" placeholder="Please enter the menu type"></el-input>
                </div>
                <div style="width: 600px;margin: 10px auto;">
                    <el-button type="primary" style="width: 100%;" @click="addFoodMenu">Generate recipe</el-button>
                </div>
            </div>
        </div>
        <div class="div-second">
            <el-checkbox-group @change="getCheckList" v-model="checkList">
                <div v-for="(item, index) in list" :key="index"
                    style="width: 270px;height: 130px;line-height:s 130px;float: left;margin-left:20px;border: 1px solid #ececec;padding-left: 5px;margin-top: 5px;">
                    <el-checkbox style="width: 200px;margin-left: 10px;margin-top:50px"
                        :label="item.id">{{item.name}}</el-checkbox>
                    <div style="width: 60px;height: 80px;float: left;background-color: aliceblue;margin-top: 30px;">
                        <img :src="NETWORK + item.image" style="width: 100%;height: 100%;border: 1px solid #ebeef5;">
                    </div>
                </div>
            </el-checkbox-group>
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
            total: 0,
            pageSize: 8,
            currentPage: 1,
            checkList: [],
            input: '',
            menu: {
                name: '',
                image: '',
                type:''
            },
            list: [],
            NETWORK: api.BASE_URL

        }
    },
    created() {
    },
    mounted() {
        this.getIngredientList(this.currentPage, this.pageSize)
    },
    methods: {
        getCheckList(e){
            console.log(e)
            this.checkList = e
           
        },
        addFoodMenu() {
            if (this.menu.name === '') {
                this.open('The recipe name is not filled in!')
                return
            }
            if (this.menu.image === '') {
                this.open('Recipe picture not uploaded!')
                return
            }
            if (this.menu.type === '') {
                this.open('The menu type is not filled in!')
                return
            }
            let str = ''
            this.checkList.forEach(element => {
                str += element+','
            });
            str = str.slice(0,str.length-1)
            console.log(str)
            api.addFoodMenu(this.menu,str).then(result => {
                this.$message({
                    message: result.msg,
                    type: 'success'
                });
                this.menu.name = ''
                this.menu.image = ''
                this.menu.type = ''
                this.checkList = []
            })

        },
        open(title) {
            this.$alert(title, {
                confirmButtonText: 'OK'
            });
        },
       
        onSuccess(response) {
            this.menu.image = response.data
        },
        getIngredientList(currentPage, pageSize) {
            api.ingredientList(currentPage, pageSize).then(result => {
                this.total = result.data.total
                this.list = result.data.list
            })
        },
        handleCurrentChange(val) {
            this.currentPage = val;
            this.getIngredientList(this.currentPage, this.pageSize)
        },
    }
};
</script>
<style scoped>
.container-footer {
    text-align: center;
    width: 1000px;
    margin: 10px auto;
    min-height: 30px;
    display: block;
    clear: both;
}

.right-div {
    width: 800px;
    height: 300px;
    float: left;
}

.left-div {
    width: 400px;
    height: 300px;
    background-color: aliceblue;
    float: left;
}

.div-second {
    width: 1190px;
    height: 270px;
    border: 1px solid #ececec;
    padding: 5px;
    margin-top: 10px;
}

.div-first {
    width: 1200px;
    height: 300px;
    border: 1px solid #ececec;
}

.container {
    width: 1200px;
    height: 630px;
    display: block;
    clear: both;
    margin: 5px auto;
    padding: 10px 20px;
    border: 1px solid #ececec;
}

.avatar-uploader-icon {
    font-size: 58px;
    color: #8c939d;
    width: 400px;
    height: 300px;
    line-height: 300px;
    text-align: center;
}

.avatar {
    width: 400px;
    height: 300px;
    display: block;
    border-radius: 6px;


}
</style>