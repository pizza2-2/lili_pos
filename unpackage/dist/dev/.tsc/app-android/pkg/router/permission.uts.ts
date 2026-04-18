import { authState } from '@/store/auth'

// 白名单,无需登录跳转页面
const routerWhiteList = [
	'/pages/login/login',		//登录页
	'/pages/webview/webview',	//网页
	'/pages/privacy/privacy'
]

const loginUrl = '/pages/login/login'	//登录页面地址

// 路由跳转拦截
const navigateToInterceptor = {
	invoke: function (options : NavigateToOptions){
		// 获取要跳转的页面路径（url去掉"?"和"?"后的参数）
		const url:string = options.url.split('?')[0]

		// 判断当前窗口是白名单，如果是则不重定向路由
		let pass = routerWhiteList.findIndex((eUrl:string):boolean => eUrl == url)
		// 不是白名单并且没有token,重定向到登录界面
		if (pass == -1 && authState.token == '') {
			options.url = loginUrl
		}
	},
	success: function (res : NavigateBackSuccess)  {
		__f__('log','at pkg/router/permission.uts:26','拦截 navigateTo 接口 success 返回参数为：', res)
	}, 
	fail: function (err : NavigateToFail) {
		__f__('log','at pkg/router/permission.uts:29','拦截 navigateTo 接口 fail 返回参数为：',err)
	}
} as AddInterceptorOptions

// tabbar切换拦截
const switchTabInterceptor = {
	invoke: function (options : SwitchTabOptions) {
		__f__('log','at pkg/router/permission.uts:36','拦截 switchTab 接口传入参数为：', options)
		// 获取要跳转的页面路径（url去掉"?"和"?"后的参数）
		const url:string = options.url.split('?')[0]
		__f__('log','at pkg/router/permission.uts:39',url)
	},
	success: function (res : SwitchTabSuccess) {
		__f__('log','at pkg/router/permission.uts:42','拦截 switchTab 接口 success 返回参数为：', res)
	},
	fail: function (err : SwitchTabFail) {
		__f__('log','at pkg/router/permission.uts:45','拦截 switchTab 接口 fail 返回参数为：', err)
	},
	complete: function (res : SwitchTabComplete) {
		__f__('log','at pkg/router/permission.uts:48','拦截 switchTab 接口 complete 返回参数为：', res)
	}
} as AddInterceptorOptions

// 添加路由拦截权限
export function routerPermission(){
	uni.addInterceptor("navigateTo", navigateToInterceptor)
	uni.addInterceptor("switchTab", switchTabInterceptor)
}

// 移除路由拦截权限
export function removeRouterPermission{
	uni.removeInterceptor('navigateTo')
	uni.removeInterceptor('switchTab')
}
