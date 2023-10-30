<template>
    <div class="container">
        <el-card shadow="hover" class="card">
            <div>
                <el-upload class="avatar-uploader" action="http://localhost:40010/api/eat/upload/picture"
                    :show-file-list="false" :on-success="onSuccess">
                    <img v-if="cookBook.image" :src="cookBook.image" class="avatar">
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
            </div>
        </el-card>
        <el-card shadow="hover" class="card-footer">
            <el-input v-model="cookBook.name" placeholder="请输入材料名" style="width: 700px;height:40px;"></el-input>
            <el-button type="primary" style="width: 700px;height:40px;margin-top: 10px;"
                @click="addCookFood">Upload material</el-button>
        </el-card>
    </div>
</template>
<script>
import api from '@/utils/api'
export default {
    data() {
        return {
            cookBook: {
                name: '',
                image: ''
            },
            NETWORK: api.BASE_URL
        }
    },
    created() {
    },
    mounted() {

    },
    methods: {

        addCookFood() {
            if (this.cookBook.name === '') {
                this.open('Material name not filled in!')
                return
            }
            if (this.cookBook.image === '') {
                this.open('Material picture not uploaded!')
                return
            }
            api.addIngredient(this.cookBook).then(result => {
                this.$message({
                    message: result.msg,
                    type: 'success'
                });
                this.cookBook.name = ''
                this.cookBook.image = ''
            })

        },
        open(title) {
            this.$alert(title, {
                confirmButtonText: 'OK'
            });
        },
        onSuccess(response) {
            this.cookBook.image = response.data
            console.log(response)
        }

    }
};
</script>
<style scoped>
.avatar-uploader-icon {
    font-size: 58px;
    color: #8c939d;
    width: 278px;
    height: 330px;
    line-height: 300px;
    text-align: center;
}

.avatar {
    width: 278px;
    height: 330px;
    display: block;
    border-radius: 6px;

}

.card-top {
    width: 800px;
    height: 60px;
    display: block;
    clear: both;
    margin: 5px auto;
}

.card {
    width: 800px;
    height: 360px;
    display: block;
    clear: both;
    margin: 5px auto;
    text-align: center;
}

.card-footer {
    width: 800px;
    height: 120px;
    display: block;
    clear: both;
    margin: 5px auto;
    text-align: center;
}

.container {
    width: 1200px;
    height: 660px;
    display: block;
    clear: both;
    margin: 5px auto;
    padding: 0 20px;
}
</style>