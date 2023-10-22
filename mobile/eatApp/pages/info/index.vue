<template>
	<view class="u-page">
		<view class="u-demo-block">
			<view class="u-demo-block__content">
				<view style="width: 1rem;height: 1rem;position: fixed;top: 0.1rem;right: 2rem;">
					<u-icon v-if="status === 0" name="star" size="28" @click="doCollect"></u-icon>
					<u-icon color="#ffe14d" v-if="status === 1" name="star-fill" size="28"
						@click="cancelCollect"></u-icon>
				</view>
				<view style="padding: 5px;width: 20rem;height: 14rem;margin: 2rem auto;border-radius: 10px;#f5f5f6;">
					<image :src="BASEURL+Menu.image" style="width: 100%;height: 100%;border-radius: 10px;"></image>
				</view>
				<view
					style="width: 20rem;height: 6rem;margin: 1rem auto;border-radius: 10px;#f5f5f6;box-shadow: -2px 0px 27px -1px rgba(11,10,10,0.39);-webkit-box-shadow: -2px 0px 27px -1px rgba(11,10,10,0.39);-moz-box-shadow: -2px 0px 27px -1px rgba(11,10,10,0.39);">
					<span style="margin-left: 0.5rem;font-weight: 650;font-size: 18px;line-height:2rem;">Menu
						name：</span>
					<view style="margin-left: 0.5rem;display: -webkit-box;
									overflow: hidden;
									text-overflow: ellipsis;
									-webkit-line-clamp: 2;
									-webkit-box-orient: vertical;">
						{{Menu.name}}
					</view>
				</view>
				<view
					style="width: 20rem;margin: 1rem auto;border-radius: 10px;#f5f5f6;box-shadow: -2px 0px 27px -1px rgba(11,10,10,0.39);-webkit-box-shadow: -2px 0px 27px -1px rgba(11,10,10,0.39);-moz-box-shadow: -2px 0px 27px -1px rgba(11,10,10,0.39);">
					<u-steps direction="column" style="padding: 1rem;" current="0">
						<view v-for="item in stepList">
							<u-steps-item :title="item.stepName">
							</u-steps-item>
						</view>
					</u-steps>
				</view>
			</view>
		</view>
		<u-notify ref="uNotify" message="Hi uView"></u-notify>
	</view>
</template>

<script>
	import api from '../api.js'
	export default {
		data() {
			return {
				Menu: {
					id: '',
					name: '',
					image: '',
					type: ''
				},
				stepList: [],
				menuId: '',
				username: '',
				status: '',
				BASEURL: ''
			}
		},
		onLoad(option) {
			this.BASEURL = uni.getStorageSync('ip')
			this.username = uni.getStorageSync('username')
			this.menuId = option.id
			uni.request({
				url: this.BASEURL + api.menuInfoApi + this.menuId,
				method: 'GET',
				success: (res) => {
					this.Menu = res.data.data
				},
				fail: (res) => {
					this.$refs.uNotify.error(res)
				}
			})

			this.getStatusEnshrine()
			this.getMenuStepInfo()
		},
		methods: {
			getMenuStepInfo() {
				uni.request({
					url: this.BASEURL + api.getMenuStepAPi + this.menuId,
					method: 'GET',
					success: (res) => {
						console.log(res.data.code)
						if (res.data.code === 200) {
							this.stepList = res.data.data
						} else {
							this.$refs.uNotify.error(res.data.msg)
						}
					},
					fail: (res) => {
						this.$refs.uNotify.error(res)
					}
				})
			},
			getStatusEnshrine() {
				uni.request({
					url: this.BASEURL + api.getStatusEnshrine + 'username=' + this.username + '&menuId=' + this
						.menuId,
					method: 'GET',
					success: (res) => {
						this.status = res.data.data
					},
					fail: (res) => {
						console.log(res)
					}
				})
			},
			cancelCollect() {
				uni.request({
					url: this.BASEURL + api.deleteEnshrine + 'username=' + this.username + '&menuId=' + this
						.menuId,
					method: 'GET',
					success: (res) => {
						this.$refs.uNotify.error(res.data.msg)
						this.getStatusEnshrine()
					},
					fail: (res) => {
						this.$refs.uNotify.error(res)
					}
				})
			},
			doCollect() {
				uni.request({
					url: this.BASEURL + api.addEnshrine + 'username=' + this.username + '&menuId=' + this.menuId,
					method: 'POST',
					data: {
						menuId: this.menuId,
						username: this.username,
						enshrineName: this.Menu.name,
						enshrineImage: this.Menu.image,
						enshrineType: this.Menu.type
					},
					success: (res) => {
						this.$refs.uNotify.success(res.data.msg)
						this.getStatusEnshrine()
					},
					fail: (res) => {
						this.$refs.uNotify.error(res)
					}
				})
			}
		},
		mounted() {

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
</style>