import Vue from 'vue'
import VueRouter from 'vue-router'
import login from '@/views/login.vue'
import regist from '@/views/regist.vue'

import adminHome from '@/views/adminHome.vue'
import userHome from '@/views/userHome.vue'
import userAdmin from '@/views/adminHome/userAdmin.vue'
import firstpage from '@/views/adminHome/firstpage.vue'
import adminMessageBoard from '@/views/adminHome/adminMessageBoard.vue'
import reportManagement from '@/views/adminHome/reportManagement.vue' // 引入举报管理组件

import firstHome from '@/views/userHome/firstHome.vue'
import userHouse from '@/views/userHome/userHouse.vue'
import houseDetail from '@/views/userHome/houseDetail.vue'
import userInfo from '@/views/userHome/userInfo.vue'
import userMessageBoard from '@/views/userHome/userMessageBoard.vue'
import houseShow from '@/views/userHome/houseShow'

// 假设这里引入房东首页组件
import landlordHome from '@/views/landlordHome.vue'
// 引入房东房屋管理组件（示例）
import landlordHouseManage from '@/views/landlordHome/landlordHouseManage.vue'

Vue.use(VueRouter)

const routes = [
    { path: "/", redirect: "/login" },
    { path: "/login", component: login },
    { path: "/regist", component: regist },
    {
        path: "/adminHome",
        component: adminHome,
        children: [
            { path: "/", redirect: "firstpage" },
            { path: "firstpage", component: firstpage }, // 首页
            { path: "adminMessageBoard", component: adminMessageBoard }, // 留言管理
            { path: "userAdmin", component: userAdmin }, // 用户管理
            { path: "reportManagement", component: reportManagement }, // 举报管理
        ]
    },
    {
        path: "/userHome",
        component: userHome,
        children: [
            { path: "/", redirect: "firstHome" },
            { path: "firstHome", component: firstHome }, // 首页
            { path: "userInfo", component: userInfo }, // 个人信息
            { path: "userMessageBoard", component: userMessageBoard }, // 留言板
            { path: "userHouse", component: userHouse }, // 用户的房屋
            { path: 'userhouse/detail/:houseid', component: houseDetail }, // 房屋详情
            { path: "houseShow", component: houseShow }, // 房屋展示
        ]
    },
    // 添加房东首页路由
    {
        path: "/landlordHome",
        component: landlordHome,
        children: [
            { path: "/", redirect: "firstHome" },
            { path: "firstHome", component: firstHome }, // 房东首页
            { path: "houseManage", component: landlordHouseManage }, // 房屋管理
            { path: "userHouse", component: userHouse }, // 我的房屋
            { path: 'userhouse/detail/:houseid', component: houseDetail }, // 房屋详情
            { path: "houseShow", component: houseShow }, // 房屋展示
        ]
    }
]

const router = new VueRouter({
    routes
})

// 前置守卫
router.beforeEach(function(to, from, next) {
    const arr = ['/adminHome', '/userHome', '/landlordHome'] // 将 /landlordHome 添加到需要拦截的路径数组中
    if (arr.indexOf(to.path) != -1) {
        // 如果访问的是home主页,则拦截,否则放行
        // 判断token是否为空 若为空，则强制跳转到登录页面 不为空则放行
        const token = localStorage.getItem('token')
        if (token) {
            const user = JSON.parse(token)
            if (to.path.startsWith('/landlordHome') && user.admin === 3) {
                next()
            } else if (to.path.startsWith('/userHome') && user.admin !== 3) {
                next()
            } else {
                next('/login')
            }
        } else {
            next('/login')
        }
    } else {
        // 放行
        next()
    }
})

export default router