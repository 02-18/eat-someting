<template>
	<view class="u-page">
		<view class="u-demo-block">
			<view class="u-demo-block__content">
				<view style="width: 5rem;font-size: 20px;margin: 2rem auto;text-align: center;font-weight: 700;">
					Login
				</view>
				<u-divider :dot="true"></u-divider>
				<u-row>
					<u-col class="background: #e5e9f2;">
						<u--input style="width: 80%;margin: 1rem auto;" placeholder="Please enter your account number"
							border="surround" v-model="U.username"></u--input>
					</u-col>
				</u-row>
				<u-row>
					<u-col class="background: #e5e9f2;">
						<u--input type="password" style="width: 80%;margin: 1rem auto;"
							placeholder="Please enter password" border="surround" v-model="U.password"></u--input>
					</u-col>
				</u-row>
				<u-row>
					<u-col class="background: #e5e9f2;">
						<view style="width: 8rem;margin-left: 1.8rem;color: #3c9cff;font-size: 14px;"
							@click="toPageLogin">Register now</view>
						<view style="width: 12rem;margin-left: 1.8rem;margin-top:2rem;color: #3c9cff;font-size: 14px;"
							@click="show = true">Network configuration</view>
					</u-col>
				</u-row>
				<u-row>
					<u-col class="background: #e5e9f2;">
						<u-button style="width: 80%;margin-top: 5rem;" type="success" :plain="true" text="Log in"
							@click="doLogin"></u-button>
					</u-col>
				</u-row>
				<u-notify ref="uNotify" message="Hi uView"></u-notify>
			</view>
		</view>
		<u-popup :show="show" @open="open" @close="close" mode="center">
			<view style="padding: 1rem;">
				<u--input placeholder="Please enter IP" border="surround" v-model="ip"></u--input>
				<u-button style="margin-top: 1rem;" type="success" :plain="true" text="disposition"
					@click="netWorkConfig"></u-button>
			</view>
		</u-popup>
	</view>
</template>

<script>
	import api from '../api'
	export default {
		data() {
			return {
				U: {
					username: '',
					password: ''
				},
				ip: '',
				show: false,
				BASEURL: ''
			}
		},
		mounted() {
			this.BASEURL = uni.getStorageSync('ip')
		},
		methods: {
			open() {
				console.log(uni.getStorageSync('ip'))
			},
			close() {
				this.show = false
			},
			netWorkConfig() {
				uni.setStorageSync('ip', 'http://' + this.ip + ':40010')
				this.show = false
			},
			toPageLogin() {
				uni.redirectTo({
					url: '/pages/register/index'
				})
			},
			doLogin() {
				console.log(uni.getStorageSync('ip'))
				if (this.U.username === '') {
					this.$refs.uNotify.error('The account cannot be empty!')
					return
				}
				if (this.U.password === '') {
					this.$refs.uNotify.error('The password cannot be empty!')
					return
				}

				uni.request({
					url: uni.getStorageSync('ip') + api.loginApi,
					method: 'POST',
					data: {
						username: this.U.username,
						password: this.U.password
					},
					success: (res) => {
						this.$refs.uNotify.success(res.data.msg)
						console.log(res.data)
						if (res.data.code === 200) {
							uni.setStorageSync('user', JSON.stringify(res.data.data))
							uni.setStorageSync('username', this.U.username)
							this.$Router.replace('/pages/index/index')
						}
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
</style>