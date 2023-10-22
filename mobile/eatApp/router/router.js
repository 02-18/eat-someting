import {
	RouterMount,
	createRouter
} from 'uni-simple-router';

const router = createRouter({
	platform: process.env.VUE_APP_PLATFORM,
	routes: [...ROUTES]
});
//全局路由前置守卫
router.beforeEach((to, from, next) => {
	if (to.path === '/pages/login/index' || to.path === '/pages/register/index') {
		uni.removeStorageSync('user')
		next()
	} else {
		let user = uni.getStorageSync('user')
		if (!user) {
			next({
				path: '/pages/login/index'
			});
		} else if (user) {
			next()
		}
	}
})
// 全局路由后置守卫
router.afterEach((to, from) => {
	console.log('跳转结束')
})






export {
	router,
	RouterMount
}