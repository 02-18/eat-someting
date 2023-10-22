<template>
	<view class="u-page">
		<view class="u-demo-block">
			<view class="u-demo-block__content">
				<view style="width: 100%;height: 3rem;">
					<u-row style="float: left;width: 80%;">
						<u-col>
							<u--input placeholder="Please enter keyword" prefixIcon="search"
								prefixIconStyle="font-size: 22px;color: #909399" v-model="searchValue"></u--input>
						</u-col>
					</u-row>
					<u-row style="float: left;width: 20%;">
						<u-col>
							<u-button text="search" @click="doSearch"></u-button>
						</u-col>
					</u-row>
				</view>
				<view style="width: 20rem;margin: 0 auto;padding-bottom: 5rem;">
					<view
						style="width: 9rem;height:11rem;float: left;margin-left: 0.5rem;margin-right: 0.5rem;margin-bottom: 1rem;background-color: #f0f8ff;border-radius: 10px;"
						v-for="item in list">
						<view
							style="width: 6rem;height: 6rem;background-color: aqua;margin: 0.5rem auto;border-radius: 5px;">
							<image :src="BASEURL+item.image" style="width: 100%;height: 100%;border-radius: 5px;"
								@click="menuInfo(item)">
							</image>
						</view>
						<view style="width: 9rem;text-align: center;font-size: 13px; display: -webkit-box;
    overflow: hidden;
    text-overflow: ellipsis;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;">{{item.name}}</view>
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
	</view>
</template>

<script>
	import api from '../api'
	export default {
		data() {
			return {
				list: [],
				searchValue: '',
				BASEURL: ''
			}
		},
		mounted() {
			this.BASEURL = uni.getStorageSync('ip')
			uni.request({
				url: this.BASEURL + api.menuListApi,
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
			menuInfo(item) {
				uni.navigateTo({
					url: '/pages/info/index?id=' + item.id
				})
			},
			doSearch() {
				uni.request({
					url: this.BASEURL + api.searchMenuAPi + this.searchValue,
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