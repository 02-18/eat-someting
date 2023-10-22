<template>
	<view class="u-page">
		<view class="u-demo-block">
			<view class="u-demo-block__content">
				<view style="width: 5rem;font-size: 20px;margin: 2rem auto;text-align: center;font-weight: 700;">
					Sign in
				</view>
				<u-divider :dot="true"></u-divider>
				<u-row>
					<u-col class="background: #e5e9f2;">
						<u--input style="width: 80%;margin: 0.5rem auto;" placeholder="Please enter a nickname"
							border="surround" v-model="U.nickname"></u--input>
					</u-col>
				</u-row>
				<u-row>
					<u-col class="background: #e5e9f2;">
						<u--input style="width: 80%;margin: 0.5rem auto;" placeholder="Please enter your account number"
							border="surround" v-model="U.username"></u--input>
					</u-col>
				</u-row>
				<u-row>
					<u-col class="background: #e5e9f2;">
						<u--input style="width: 80%;margin: 0.5rem auto;" placeholder="Please enter cooking style"
							border="surround" v-model="U.cookStyle"></u--input>
					</u-col>
				</u-row>
				<u-row>
					<u-col class="background: #e5e9f2;">
						<u--input style="width: 80%;margin: 0.5rem auto;"
							placeholder="Please enter dietary requirements" border="surround"
							v-model="U.requirements"></u--input>
					</u-col>
				</u-row>
				<u-row>
					<u-col class="background: #e5e9f2;">
						<u--input style="width: 80%;margin: 0.5rem auto;" placeholder="Please enter allergy information"
							border="surround" v-model="U.allergy"></u--input>
					</u-col>
				</u-row>
				<u-row>
					<u-col class="background: #e5e9f2;">
						<u--input type="password" style="width: 80%;margin: 0.5rem auto;"
							placeholder="Please enter password" border="surround" v-model="U.password"></u--input>
					</u-col>
				</u-row>
				<u-row>
					<u-col class="background: #e5e9f2;">
						<u--input type="password" style="width: 80%;margin: 0.5rem auto;"
							placeholder="Please confirm the password" border="surround"
							v-model="U.Spassword"></u--input>
					</u-col>
				</u-row>
				<u-row>
					<u-col class="background: #e5e9f2;">
						<view style="width: 5rem;margin-left: 1.8rem;color: #3c9cff;font-size: 14px;"
							@click="toPageLogin">Sign in now</view>
					</u-col>
				</u-row>
				<u-row>
					<u-col class="background: #e5e9f2;">
						<u-button style="width: 80%;margin-top: 5rem;" type="error" :plain="true" text="Sign in"
							@click="register"></u-button>
					</u-col>
				</u-row>
				<u-notify ref="uNotify" message="Hi uView"></u-notify>
			</view>
		</view>
	</view>
</template>

<script>
	import api from '../api.js'
	export default {
		data() {
			return {
				show: true,
				U: {
					nickname: '',
					username: '',
					password: '',
					Spassword: '',
					cookStyle: '',
					requirements: '',
					allergy: ''
				},
				BASEURL: ''
			}
		},
		mounted() {
			this.BASEURL = uni.getStorageSync('ip')
		},
		methods: {
			toPageLogin() {
				uni.redirectTo({
					url: '/pages/login/index'
				})
			},
			register() {
				if (this.U.username === '') {
					this.$refs.uNotify.error('The account cannot be empty!')
					return
				}
				if (this.U.nickname === '') {
					this.$refs.uNotify.error('Nicknames cannot be empty!')
					return
				}
				if (this.U.cookStyle === '') {
					this.$refs.uNotify.error('The cooking style cannot be left empty!')
					return
				}
				if (this.U.requirements === '') {
					this.$refs.uNotify.error('Dietary requirements cannot be empty!')
					return
				}
				if (this.U.allergy === '') {
					this.$refs.uNotify.error('Allergy information cannot be empty!')
					return
				}
				if (this.U.Spassword === '') {
					this.$refs.uNotify.error('Confirm that the password cannot be empty!')
					return
				}
				if (this.U.password !== this.U.Spassword) {
					this.$refs.uNotify.error('Two passwords do not match!')
					return
				}
				uni.request({
					url: this.BASEURL + api.registerApi,
					method: 'POST',
					data: {
						username: this.U.username,
						password: this.U.password,
						nickname: this.U.nickname,
						cookStyle: this.U.cookStyle,
						requirements: this.U.requirements,
						allergy: this.U.allergy
					},
					success: (res) => {
						this.$refs.uNotify.success(res.data.msg)
						this.U.username = ''
						this.U.password = ''
						this.U.Spassword = ''
						this.U.nickname = ''
						this.U.cookStyle = ''
						this.U.requirements = ''
						this.U.allergy = ''
					},
					fail: (res) => {
						this.$refs.uNotify.error(res)
						this.U.username = ''
						this.U.password = ''
						this.U.Spassword = ''
						this.U.nickname = ''
						this.U.cookStyle = ''
						this.U.requirements = ''
						this.U.allergy = ''
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