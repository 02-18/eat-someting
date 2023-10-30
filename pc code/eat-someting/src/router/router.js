import VueRouter from "vue-router";
import Vue from "vue";

Vue.use(VueRouter)

// vue-router路由配置
// const login = () => import('@/page/login.vue')
// const register = () => import('@/page/register.vue')
const index = () => import('@/page/index.vue')
const content = () => import('@/page/content.vue')

const cookStep = () => import('@/page/cook-step.vue')
const cookFoodList = () => import('@/page/cook-food-list.vue')
const cookBookList = () => import('@/page/cook-book-list.vue')
const cookFoodMake = () => import('@/page/cook-food-make.vue')
const cookBookMake = () => import('@/page/cook-book-make.vue')
const routes = [
    {
        path: '',
        component: index,
        redirect: '/base/cook-food-list',
        children: [
            {
                path: '/base',
                component: content,
                children: [
                    {
                        path: 'cook-step',
                        component: cookStep
                    },
                    {
                        path: 'cook-food-list',
                        component: cookFoodList
                    },
                    {
                        path: 'cook-book-list',
                        component: cookBookList
                    },
                    {
                        path: 'cook-food-make',
                        component: cookFoodMake
                    },
                    {
                        path: 'cook-book-make',
                        component: cookBookMake
                    }
                ]
            },

        ]
    }
]


const router = new VueRouter({
    routes
})

export default router;