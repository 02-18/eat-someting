<template>
	<view class="u-page">
		<view class="u-demo-block">
			<view class="u-demo-block__content">
				<view style="width: 100%;height: 6rem;margin-bottom: 1rem;padding: 1rem;background-color: #eef0f4;"
					v-for="item in list">
					<view style="background-color: aqua;width:6rem;height: 6rem;float: left;border-radius: 10px;">
						<image :src="BASEURL+item.enshrineImage" style="width: 100%;height: 100%;border-radius: 10px;">
						</image>
					</view>
					<view style="width:10rem;height: 6rem;float: left;margin:0 1rem;line-height: 6rem;padding-left: 10px;font-size: 16px; display: -webkit-box;
    overflow: hidden;
    text-overflow: ellipsis;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;">
						{{item.enshrineName}}
					</view>
					<view style="width:2rem;height: 6rem;float: left;line-height: 6rem;">
						<u-icon color="#ffe14d" name="star-fill" size="28" style="margin-top: 2rem;"
							@click="cancelCollect(item)"></u-icon>
					</view>
				</view>
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
				BASEURL: '',
				username: ''
			}
		},
		mounted() {
			this.BASEURL = uni.getStorageSync('ip')
			this.username = uni.getStorageSync('username')
			this.listEnshrine()
		},
		methods: {
			listEnshrine() {
				uni.request({
					url: this.BASEURL + api.listEnshrine + this.username,
					method: 'GET',
					success: (res) => {
						this.list = res.data.data
						console.log(this.list)
					},
					fail: (res) => {
						console.log(res)
					}
				})
			},
			cancelCollect(item) {
				uni.request({
					url: this.BASEURL + api.deleteEnshrine + 'username=' + this.username + '&menuId=' + item
						.menuId,
					method: 'GET',
					success: (res) => {
						this.$refs.uNotify.success(res.data.msg)
						this.listEnshrine()
					},
					fail: (res) => {
						this.$refs.uNotify.error(res)
					}
				})
			}
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