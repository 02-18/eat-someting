<template>
	<view class="u-page">
		<view class="u-demo-block">
			<view class="u-demo-block__content">
				<u-row>
					<u-col>
						<u-button :plain="true" text="Selection of material" @click="show = true"></u-button>
					</u-col>
				</u-row>
				<u-row>
					<u-col>
						<u-button :plain="true" text="Recommended menu" @click="getRecommendInfo"></u-button>
					</u-col>
				</u-row>
				<view>
					<view v-if="show">
						<u-checkbox-group placement="column" @change="checkboxChange">
							<view style="width: 20rem;margin: 0 auto;padding-bottom: 2rem;">
								<view
									style="width: 9rem;height:12rem;float: left;margin-left: 0.5rem;margin-right: 0.5rem;margin-bottom: 1rem;background-color: #f0f8ff;border-radius: 10px;"
									v-for="item in list">
									<view
										style="width: 6rem;height: 6rem;background-color: aqua;margin: 0.5rem auto;border-radius: 5px;">
										<image :src="BASEURL+item.image"
											style="width: 100%;height: 100%;border-radius: 5px;">
										</image>
									</view>
									<view style="width: 9rem;font-size: 10px;">
										<u-checkbox style="margin-left: 4rem;" :name="item.id">
										</u-checkbox>
									</view>
									<view style="width:10rem;float: left;margin:0 1rem;padding-left: 10px;font-size: 14px; display: -webkit-box;
									overflow: hidden;
									text-overflow: ellipsis;
									-webkit-line-clamp: 3;
									-webkit-box-orient: vertical;">
										{{item.name}}
									</view>
								</view>
							</view>
						</u-checkbox-group>
					</view>
					<view v-else>
						<view style="width: 20rem;margin: 0 auto;padding-bottom: 2rem;">
							<view
								style="width: 9rem;height:9rem;float: left;margin-left: 0.5rem;margin-right: 0.5rem;margin-bottom: 1rem;background-color: #f0f8ff;border-radius: 10px;"
								v-for="item in recommendList">
								<view
									style="width: 6rem;height: 6rem;background-color: aqua;margin: 0.5rem auto;border-radius: 5px;">
									<image :src="BASEURL+item.image"
										style="width: 100%;height: 100%;border-radius: 5px;" @click="menuInfo(item)">
									</image>
								</view>
								<view style="width: 9rem;text-align: center;font-size: 13px;">{{item.name}}</view>
							</view>
						</view>
					</view>
				</view>
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
		<u-notify ref="uNotify" message="Hi uView"></u-notify>
	</view>
</template>

<script>
	import api from '../api'
	export default {
		data() {
			return {
				list: [],
				ingredientList: [],
				recommendList: [],
				BASEURL: '',
				show: true
			}
		},
		mounted() {
			this.BASEURL = uni.getStorageSync('ip')
			uni.request({
				url: this.BASEURL + api.listIngredient,
				method: 'GET',
				success: (res) => {
					console.log(res)
					this.list = res.data.data.list
				},
				fail: (res) => {
					console.log(res)
				}
			})
		},
		methods: {
			getRecommendInfo() {
				if (this.ingredientList.length === 0) {
					this.$refs.uNotify.error('Unselected material!')
					return
				}
				let str = ''
				this.ingredientList.forEach(element => {
					str += element + ','
				});
				str = str.slice(0, str.length - 1)
				uni.request({
					url: this.BASEURL + api.recommendAPi + str,
					method: 'GET',
					success: (res) => {
						console.log(res)
						this.recommendList = res.data.data
						this.ingredientList = []
						this.show = false
					},
					fail: (res) => {
						console.log(res)
					}
				})
			},
			checkboxChange(e) {
				this.ingredientList = e
				console.log(e)
			},
			menuInfo(item) {
				uni.navigateTo({
					url: '/pages/info/index?id=' + item.id
				})
			},
			toPage1() {
				this.$Router.replace('/pages/recommend/index')
			},
			toPage2() {
				this.$Router.replace('/pages/index/index')
			},
			toPage3() {
				this.$Router.replace('/pages/user/index')
			}
		}
	}
</script>

<style scoped>
	.list-cell {
		display: flex;
		box-sizing: border-box;
		width: 100%;
		padding: 10px 24rpx;
		overflow: hidden;
		color: #323233;
		font-size: 14px;
		line-height: 24px;
		background-color: #fff;
	}

	.uni-btn-icon {
		display: none;
	}

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