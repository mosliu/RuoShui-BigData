import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: 路由配置项
 *
 * hidden: true                     // 当设置 true 的时候该路由不会再侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
 * alwaysShow: true                 // 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
 *                                  // 只有一个时，会将那个子路由当做根路由显示在侧边栏--如引导页面
 *                                  // 若你想不管路由下面的 children 声明的个数都显示你的根路由
 *                                  // 你可以设置 alwaysShow: true，这样它就会忽略之前定义的规则，一直显示根路由
 * redirect: noRedirect             // 当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
 * name:'router-name'               // 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
 * query: '{"id": 1, "name": "ry"}' // 访问路由的默认传递参数
 * roles: ['admin', 'common']       // 访问路由的角色权限
 * permissions: ['a:a:a', 'b:b:b']  // 访问路由的菜单权限
 * meta : {
    noCache: true                   // 如果设置为true，则不会被 <keep-alive> 缓存(默认 false)
    title: 'title'                  // 设置该路由在侧边栏和面包屑中展示的名字
    icon: 'svg-name'                // 设置该路由的图标，对应路径src/assets/icons/svg
    breadcrumb: false               // 如果设置为false，则不会在breadcrumb面包屑中显示
    activeMenu: '/system/user'      // 当路由设置了该属性，则会高亮相对应的侧边栏。
  }
 */

// 公共路由
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login'),
    hidden: true
  },
  {
    path: '/register',
    component: () => import('@/views/register'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/error/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error/401'),
    hidden: true
  },
  {
    path: '',
    component: Layout,
    redirect: 'index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/index/admin/index'),
        name: 'Index',
        meta: { title: '首页', icon: 'dashboard', affix: true }
      }
    ]
  },
  {
    path: '/user',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [
      {
        path: 'profile',
        component: () => import('@/views/system/user/profile/index'),
        name: 'Profile',
        meta: { title: '个人中心', icon: 'user' }
      }
    ]
  },
  {
    path: '/flink/task-manage',
    redirect: 'noRedirect',
    name: 'TaskManage',
    meta: { title: '任务管理', icon: 'list' },
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'index',
        name: 'FlinkTaskManage',
        component: () => import('@/views/flink/task-manage/index'),
        meta: { title: '任务列表', icon: 'el-icon-star-off' },
        children: [
          {
            path: '/flink/task-manage/create_sql_streaming_task',
            name: 'CreateSqlStreamingTask',
            component: (resolve) => require([`@/views/flink/task-manage/sqltask.vue`], resolve),
            hidden: true,
            meta: { title: '创建SQL流任务' },
            children: []
          },
          {
            path: '/flink/task-manage/edit_sql_streaming_task',
            name: 'UpdateSqlStreamingTask',
            component: (resolve) => require([`@/views/flink/task-manage/sqltask.vue`], resolve),
            hidden: true,
            meta: { title: '编辑SQL流任务' },
            children: []
          },
          {
            path: '/flink/task-manage/view_sql_streaming_task',
            name: 'ViewSqlStreamingTask',
            component: (resolve) => require([`@/views/flink/task-manage/sqltask.vue`], resolve),
            hidden: true,
            meta: { title: '查看SQL流任务' },
            children: []
          },
          {
            path: '/flink/task-manage/create_sql_batch_task',
            name: 'CreateSqlBatchTask',
            component: (resolve) => require([`@/views/flink/task-manage/sqltask.vue`], resolve),
            hidden: true,
            meta: { title: '创建SQL批任务' },
            children: []
          },
          {
            path: '/flink/task-manage/edit_sql_batch_task',
            name: 'UpdateSqlBatchTask',
            component: (resolve) => require([`@/views/flink/task-manage/sqltask.vue`], resolve),
            hidden: true,
            meta: { title: '编辑SQL批任务' },
            children: []
          },
          {
            path: '/flink/task-manage/view_sql_batch_task',
            name: 'ViewSqlBatchTask',
            component: (resolve) => require([`@/views/flink/task-manage/sqltask.vue`], resolve),
            hidden: true,
            meta: { title: '查看SQL批任务' },
            children: []
          },
          {
            path: '/flink/task-manage/create_jar_task',
            name: 'CreateJarTask',
            component: (resolve) => require([`@/views/flink/task-manage/jartask.vue`], resolve),
            hidden: true,
            meta: { title: '创建JAR任务' },
            children: []
          },
          {
            path: '/flink/task-manage/create_jar_task',
            name: 'CreateJarBatchTask',
            component: (resolve) => require([`@/views/flink/task-manage/jartask.vue`], resolve),
            hidden: true,
            meta: { title: '创建JAR批任务' },
            children: []
          },
          {
            path: '/flink/task-manage/edit_jar_task',
            name: 'UpdateJarTask',
            component: (resolve) => require([`@/views/flink/task-manage/jartask.vue`], resolve),
            hidden: true,
            meta: { title: '编辑JAR任务' },
            children: []
          },
          {
            path: '/flink/task-manage/edit_jar_task',
            name: 'UpdateJarBatchTask',
            component: (resolve) => require([`@/views/flink/task-manage/jartask.vue`], resolve),
            hidden: true,
            meta: { title: '编辑JAR批任务' },
            children: []
          },
          {
            path: '/flink/task-manage/view_jar_task',
            name: 'ViewJarTask',
            component: (resolve) => require([`@/views/flink/task-manage/jartask.vue`], resolve),
            hidden: true,
            meta: { title: '查看JAR任务' },
            children: []
          },
          {
            path: '/flink/task-manage/view_jar_task',
            name: 'ViewJarBatchTask',
            component: (resolve) => require([`@/views/flink/task-manage/jartask.vue`], resolve),
            hidden: true,
            meta: { title: '查看JAR批任务' },
            children: []
          },
          {
            path: '/flink/log-manage/view_logdetail',
            name: 'ViewTaskLogDetail',
            component: (resolve) => require([`@/views/flink/log-manage/logdetail.vue`], resolve),
            hidden: true,
            meta: { title: '查看日志详情' },
            children: []
          },
          {
            path: 'index',
            name: 'FlinkLogManage',
            component: () => import('@/views/flink/log-manage/index'),
            meta: { title: '运行日志', icon: 'documentation' },
            children: [
              {
                path: '/flink/log-manage/view_logdetail',
                name: 'ViewLogDetail',
                component: (resolve) => require([`@/views/flink/log-manage/logdetail.vue`], resolve),
                hidden: true,
                meta: { title: '查看日志详情' },
                children: []
              }
            ]
          }
        ]
      },
      {
        path: '/flink/task-manage/history',
        name: 'HistoryTask',
        component: (resolve) => require([`@/views/flink/task-manage/history.vue`], resolve),
        meta: { title: '历史版本', icon: 'el-icon-tickets' },
        children: [
          {
            path: '/flink/task-manage/view_sql_streaming_task',
            name: 'ViewHistorySqlStreamingTask',
            component: (resolve) => require([`@/views/flink/task-manage/sqltask.vue`], resolve),
            hidden: true,
            meta: { title: '查看SQL流任务' },
            children: []
          },
          {
            path: '/flink/task-manage/view_sql_batch_task',
            name: 'ViewHistorySqlBatchTask',
            component: (resolve) => require([`@/views/flink/task-manage/sqltask.vue`], resolve),
            hidden: true,
            meta: { title: '查看SQL批任务' },
            children: []
          },
          {
            path: '/flink/task-manage/view_jar_task',
            name: 'ViewHistoryJarTask',
            component: (resolve) => require([`@/views/flink/task-manage/jartask.vue`], resolve),
            hidden: true,
            meta: { title: '查看JAR批任务' },
            children: []
          }
        ]
      }
    ]
  }
]

// 动态路由，基于用户权限动态去加载
export const dynamicRoutes = [
  {
    path: '/system/user-auth',
    component: Layout,
    hidden: true,
    permissions: ['system:user:edit'],
    children: [
      {
        path: 'role/:userId(\\d+)',
        component: () => import('@/views/system/user/authRole'),
        name: 'AuthRole',
        meta: { title: '分配角色', activeMenu: '/system/user' }
      }
    ]
  },
  {
    path: '/system/role-auth',
    component: Layout,
    hidden: true,
    permissions: ['system:role:edit'],
    children: [
      {
        path: 'user/:roleId(\\d+)',
        component: () => import('@/views/system/role/authUser'),
        name: 'AuthUser',
        meta: { title: '分配用户', activeMenu: '/system/role' }
      }
    ]
  },
  {
    path: '/system/dict-data',
    component: Layout,
    hidden: true,
    permissions: ['system:dict:list'],
    children: [
      {
        path: 'index/:dictId(\\d+)',
        component: () => import('@/views/system/dict/data'),
        name: 'Data',
        meta: { title: '字典数据', activeMenu: '/system/dict' }
      }
    ]
  },
  {
    path: '/monitor/job-log',
    component: Layout,
    hidden: true,
    permissions: ['monitor:job:list'],
    children: [
      {
        path: 'index',
        component: () => import('@/views/monitor/job/log'),
        name: 'JobLog',
        meta: { title: '调度日志', activeMenu: '/monitor/job' }
      }
    ]
  },
  {
    path: '/tool/gen-edit',
    component: Layout,
    hidden: true,
    permissions: ['tool:gen:edit'],
    children: [
      {
        path: 'index/:tableId(\\d+)',
        component: () => import('@/views/tool/gen/editTable'),
        name: 'GenEdit',
        meta: { title: '修改生成配置', activeMenu: '/tool/gen' }
      }
    ]
  }
]



// 防止连续点击多次路由报错
let routerPush = Router.prototype.push;
Router.prototype.push = function push(location) {
  return routerPush.call(this, location).catch(err => err)
}

export default new Router({
  mode: 'history', // 去掉url中的#
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})
