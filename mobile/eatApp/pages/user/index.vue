<template>
	<view class="u-page">
		<view class="u-demo-block">
			<view class="u-demo-block__content">
				<u-row>
					<u-col class="background: #e5e9f2;">
						<view style="width: 5rem;height: 5rem;margin: 2rem auto;border-radius: 50%;" @click="showAlbum">
							<image :src="BASEURL+U.avatar" style="width: 100%;height: 100%;border-radius: 50%;">
							</image>
						</view>
					</u-col>
				</u-row>
				<u-row>
					<u--form labelPosition="left" style="width: 80%;margin: 3rem auto;" labelWidth="150">
						<u-form-item label="name" borderBottom>
							<u--input border="none" v-model="U.nickname"></u--input>
						</u-form-item>
						<u-form-item label="Account number" borderBottom>
							<u--input border="none" v-model="U.username"></u--input>
						</u-form-item>
						<u-form-item label="Cooking style" borderBottom>
							<u--input border="none" v-model="U.cookStyle"></u--input>
						</u-form-item>
						<u-form-item label="Dietary requirement" borderBottom>
							<u--input border="none" v-model="U.requirements"></u--input>
						</u-form-item>
						<u-form-item label="Allergy information" borderBottom>
							<u--input border="none" v-model="U.allergy"></u--input>
						</u-form-item>
					</u--form>
				</u-row>
				<u-row>
					<u-button style="width: 40%;margin: 2rem auto;" type="success" @click="linkToEnshrine">My
						collection</u-button>
					<u-button style="width: 40%;margin: 2rem auto;" type="error" @click="close">Log out</u-button>
				</u-row>
				<u-notify ref="uNotify" message="Hi uView"></u-notify>
			</view>
			<view class="footer">
				<view style="width: 30%;float: left;" @click="modeShow = true"><u-button :hairline="true" :plain="true"
						text="Recommended" @click="toPage1"></u-button>
				</view>
				<view style="width:39%;float: left;" @click="modeShow = false"><u-button :hairline="true" :plain="true"
						text="Main page" @click="toPage2"></u-button></view>
				<view style="width:30%;float: left;" @click="modeShow = false"><u-button :hairline="true" :plain="true"
						text="Personage" @click="toPage3"></u-button></view>
			</view>
		</view>
	</view>
</template>

<script>
	import api from '../api.js'
	export default {
		data() {
			return {
				show: false,
				U: {
					nickname: '',
					username: '',
					cookStyle: '',
					requirements: '',
					allergy: '',
					avatar: ''
				},
				BASEURL: '',
			}
		},
		onLoad() {},
		methods: {
			toPage1() {
				this.$Router.replace('/pages/recommend/index')
			},
			toPage2() {
				this.$Router.replace('/pages/index/index')
			},
			toPage3() {
				this.$Router.replace('/pages/user/index')
			},
			logout() {
				this.$Router.replace('/pages/login/index')
			},
			linkToEnshrine() {
				uni.navigateTo({
					url: '/pages/enshrine/index'
				})
			},
			close() {
				this.$refs.uNotify.error('The account is exiting......')
				setTimeout(() => {
					this.$Router.replace('/pages/login/index')
				}, 1500)
			},
			showAlbum() {
				uni.chooseImage({
					success: (chooseImageRes) => {
						const tempFilePaths = chooseImageRes.tempFilePaths;
						uni.uploadFile({
							url: this.BASEURL + '/api/eat/upload/picture',
							filePath: tempFilePaths[0],
							name: 'file',
							success: (uploadFileRes) => {
								console.log(JSON.parse(uploadFileRes.data).data);
								let image = (JSON.parse(uploadFileRes.data).data).split('40010')[1]
								uni.request({
									url: this.BASEURL + api.updateUserAvatar +
										image +
										'&username=' + this
										.U.username,
									method: 'GET',
									success: (res) => {
										if (res.data.code === 200) {
											this.U.avatar = image
											this.$refs.uNotify.success(res.data.msg)
										} else {
											this.$refs.uNotify.error(res.data.msg)
										}
									},
									fail: (res) => {
										this.$refs.uNotify.error(res.data.msg)
									}
								})
							}
						});
					}
				});

			}
		},
		mounted() {
			this.BASEURL = uni.getStorageSync('ip')
			uni.request({
				url: this.BASEURL + api.userInfoApi + uni.getStorageSync('username'),
				method: 'GET',
				success: (res) => {
					this.U.nickname = res.data.data.nickname
					this.U.username = res.data.data.username
					this.U.cookStyle = res.data.data.cookStyle
					this.U.requirements = res.data.data.requirements
					this.U.allergy = res.data.data.allergy
					this.U.avatar = res.data.data.avatar
				},
				fail: (res) => {
					this.$refs.uNotify.error(res)
				}
			})
		}
	}
</script>

<style scoped>
	.wrap {
		padding: 12px;
	}

	.demo-layout {
		height: 25px;
		border-radius: 4px;
	}

	.footer {
		width: 100%;
		height: 35px;
		text-align: center;
		line-height: 35px;
		background-color: #ffffff;
		position: fixed;
		bottom: 0;
	}
</style>