
	import { isAgreePrivacyState } from '@/store/global'
	import { getAuthStateByStorageSync } from '@/store/auth'
	import { routerPermission,removeRouterPermission } from '@/pkg/router/permission'	//路由拦截
	

	import { registerOSPermission,unregisterOSPermission } from '@/pkg/util/osPermission'	//系统权限申请相关

	

	let firstBackTime = 0


	const __sfc__ = defineApp({
		onLaunch: function () {
			console.log('App Launch', " at App.uvue:16")
			

			registerOSPermission()  	//全局注册监听系统权限申请

			getAuthStateByStorageSync() //同步从本地存储中获取authState的值
			routerPermission()			//添加路由拦截权限
			

			// 监听同意隐私政策状态变化
			uni.onPrivacyAuthorizationChange((res)=>{
			    //更新同意隐私政策状态
				isAgreePrivacyState.value = !res.needAuthorization
			})
			// 获取是否同意隐私政策
			uni.getPrivacySetting({
				success(res){
					// 注意iOS首次启动将不会弹隐私政策窗口（苹果上架对首次进入app弹窗没有要求）
					if(res.needAuthorization){
						isAgreePrivacyState.value = false	// 用户未同意隐私政策
						// 用户未同意隐私政策则弹出“个人信息保护指引”提示框
						uni.openDialogPage({
							url: '/pages/privacy/privacy',  // 修改为应用中定义的页面地址
						})
					}else{
						isAgreePrivacyState.value = true	// 用户已同意隐私政策
					}
				}
			})

		},
		onShow: function (options: OnShowOptions) {
			console.log('App Show', " at App.uvue:48")
			
			console.log(options, " at App.uvue:50")	//这里可以实现跳转打开此app，进入指定页面。
		},
		onHide: function () {
			console.log('App Hide', " at App.uvue:53")
		},

		onLastPageBackPress: function () {
			console.log('App LastPageBackPress', " at App.uvue:57")
			if (firstBackTime == 0) {
				uni.showToast({
					title: '再按一次退出应用',
					position: 'bottom',
				})
				firstBackTime = Date.now()
				setTimeout(() => {
					firstBackTime = 0
				}, 2000)
			} else if (Date.now() - firstBackTime < 2000) {
				firstBackTime = Date.now()
				uni.exit()
			}
		},

		onError: function(err: any){
			console.log(err, " at App.uvue:74")	//监听全局错误，可以在这里实现错误上报
		},
		onExit: function () {
			console.log('App Exit', " at App.uvue:77")
			

			unregisterOSPermission()  // 取消全局监听os权限

			removeRouterPermission()	//移除路由拦截权限
		},
	})

export default __sfc__
const GenAppStyles = [_uM([["iconfont", _pS(_uM([["fontFamily", "iconfont"], ["fontStyle", "normal"]]))], ["alimama-daoliti", _pS(_uM([["fontFamily", "AlimamaDaoLiTiTTF"]]))], ["w100", _pS(_uM([["width", "100%"]]))], ["h100", _pS(_uM([["height", "100%"]]))], ["wh100", _pS(_uM([["width", "100%"], ["height", "100%"]]))], ["flexc", _pS(_uM([["display", "flex"], ["flexDirection", "column"]]))], ["flexcr", _pS(_uM([["display", "flex"], ["flexDirection", "column-reverse"]]))], ["flexr", _pS(_uM([["display", "flex"], ["flexDirection", "row"]]))], ["flexrr", _pS(_uM([["display", "flex"], ["flexDirection", "row-reverse"]]))], ["flex-fw-w", _pS(_uM([["flexWrap", "wrap"]]))], ["flex-fw-wr", _pS(_uM([["flexWrap", "wrap-reverse"]]))], ["flex-jc-e", _pS(_uM([["justifyContent", "flex-end"]]))], ["flex-jc-a", _pS(_uM([["justifyContent", "space-around"]]))], ["flex-jc-c", _pS(_uM([["justifyContent", "center"]]))], ["flex-jc-b", _pS(_uM([["justifyContent", "space-between"]]))], ["flex-ai-s", _pS(_uM([["alignItems", "flex-start"]]))], ["flex-ai-e", _pS(_uM([["alignItems", "flex-end"]]))], ["flex-ai-c", _pS(_uM([["alignItems", "center"]]))], ["flex1", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"]]))], ["color-base", _pS(_uM([["color", "#409eff"]]))], ["color-second", _pS(_uM([["color", "#00daff"]]))], ["color-grey", _pS(_uM([["color", "#a2a2a2"]]))], ["color-black", _pS(_uM([["color", "#171717"]]))], ["color-white", _pS(_uM([["color", "#ffffff"]]))], ["color-red", _pS(_uM([["color", "#FF0000"]]))], ["bg-base", _pS(_uM([["backgroundImage", "none"], ["backgroundColor", "#007aff"]]))], ["bg-second", _pS(_uM([["backgroundImage", "none"], ["backgroundColor", "#00daff"]]))], ["bg-grey", _pS(_uM([["backgroundImage", "none"], ["backgroundColor", "#F8F8F8"]]))], ["bg-white", _pS(_uM([["backgroundImage", "none"], ["backgroundColor", "#ffffff"]]))], ["bg-black", _pS(_uM([["backgroundImage", "none"], ["backgroundColor", "#171717"]]))], ["line", _pS(_uM([["height", "1rpx"], ["backgroundImage", "none"], ["backgroundColor", "#eeeeee"]]))], ["v-line", _pS(_uM([["width", "2rpx"], ["backgroundColor", "#a2a2a2"]]))], ["fs-10", _pS(_uM([["fontSize", "10rpx"]]))], ["fs-12", _pS(_uM([["fontSize", "12rpx"]]))], ["fs-14", _pS(_uM([["fontSize", "14rpx"]]))], ["fs-16", _pS(_uM([["fontSize", "16rpx"]]))], ["fs-18", _pS(_uM([["fontSize", "18rpx"]]))], ["fs-20", _pS(_uM([["fontSize", "20rpx"]]))], ["fs-22", _pS(_uM([["fontSize", "22rpx"]]))], ["fs-24", _pS(_uM([["fontSize", "24rpx"]]))], ["fs-26", _pS(_uM([["fontSize", "26rpx"]]))], ["fs-28", _pS(_uM([["fontSize", "28rpx"]]))], ["fs-30", _pS(_uM([["fontSize", "30rpx"]]))], ["fs-32", _pS(_uM([["fontSize", "32rpx"]]))], ["fs-34", _pS(_uM([["fontSize", "34rpx"]]))], ["fs-36", _pS(_uM([["fontSize", "36rpx"]]))], ["fs-38", _pS(_uM([["fontSize", "38rpx"]]))], ["fs-40", _pS(_uM([["fontSize", "40rpx"]]))], ["fs-60", _pS(_uM([["fontSize", "60rpx"]]))], ["ta-c", _pS(_uM([["textAlign", "center"]]))], ["to-1", _pS(_uM([["textOverflow", "ellipsis"], ["lines", 1]]))], ["to-2", _pS(_uM([["textOverflow", "ellipsis"], ["lines", 2]]))], ["fw-400", _pS(_uM([["fontWeight", "400"]]))], ["fw-700", _pS(_uM([["fontWeight", "700"]]))], ["m-10", _pS(_uM([["marginTop", "10rpx"], ["marginRight", "10rpx"], ["marginBottom", "10rpx"], ["marginLeft", "10rpx"]]))], ["m-16", _pS(_uM([["marginTop", "16rpx"], ["marginRight", "16rpx"], ["marginBottom", "16rpx"], ["marginLeft", "16rpx"]]))], ["m-20", _pS(_uM([["marginTop", "20rpx"], ["marginRight", "20rpx"], ["marginBottom", "20rpx"], ["marginLeft", "20rpx"]]))], ["m-30", _pS(_uM([["marginTop", "30rpx"], ["marginRight", "30rpx"], ["marginBottom", "30rpx"], ["marginLeft", "30rpx"]]))], ["m-40", _pS(_uM([["marginTop", "40rpx"], ["marginRight", "40rpx"], ["marginBottom", "40rpx"], ["marginLeft", "40rpx"]]))], ["mt-10", _pS(_uM([["marginTop", "10rpx"]]))], ["mt-16", _pS(_uM([["marginTop", "16rpx"]]))], ["mt-20", _pS(_uM([["marginTop", "20rpx"]]))], ["mt-30", _pS(_uM([["marginTop", "30rpx"]]))], ["mt-40", _pS(_uM([["marginTop", "40rpx"]]))], ["mr-10", _pS(_uM([["marginRight", "10rpx"]]))], ["mr-16", _pS(_uM([["marginRight", "16rpx"]]))], ["mr-20", _pS(_uM([["marginRight", "20rpx"]]))], ["mr-30", _pS(_uM([["marginRight", "30rpx"]]))], ["mr-40", _pS(_uM([["marginRight", "40rpx"]]))], ["mb-10", _pS(_uM([["marginBottom", "10rpx"]]))], ["mb-16", _pS(_uM([["marginBottom", "16rpx"]]))], ["mb-20", _pS(_uM([["marginBottom", "20rpx"]]))], ["mb-30", _pS(_uM([["marginBottom", "30rpx"]]))], ["mb-40", _pS(_uM([["marginBottom", "40rpx"]]))], ["ml-10", _pS(_uM([["marginLeft", "10rpx"]]))], ["ml-16", _pS(_uM([["marginLeft", "16rpx"]]))], ["ml-20", _pS(_uM([["marginLeft", "20rpx"]]))], ["ml-30", _pS(_uM([["marginLeft", "30rpx"]]))], ["ml-40", _pS(_uM([["marginLeft", "40rpx"]]))], ["m-x-10", _pS(_uM([["marginLeft", "10rpx"], ["marginRight", "10rpx"]]))], ["m-x-16", _pS(_uM([["marginLeft", "16rpx"], ["marginRight", "16rpx"]]))], ["m-x-20", _pS(_uM([["marginLeft", "20rpx"], ["marginRight", "20rpx"]]))], ["m-x-30", _pS(_uM([["marginLeft", "30rpx"], ["marginRight", "30rpx"]]))], ["m-x-40", _pS(_uM([["marginLeft", "40rpx"], ["marginRight", "40rpx"]]))], ["m-y-10", _pS(_uM([["marginTop", "10rpx"], ["marginBottom", "10rpx"]]))], ["m-y-16", _pS(_uM([["marginTop", "16rpx"], ["marginBottom", "16rpx"]]))], ["m-y-20", _pS(_uM([["marginTop", "20rpx"], ["marginBottom", "20rpx"]]))], ["m-y-30", _pS(_uM([["marginTop", "30rpx"], ["marginBottom", "30rpx"]]))], ["m-y-40", _pS(_uM([["marginTop", "40rpx"], ["marginBottom", "40rpx"]]))], ["p-10", _pS(_uM([["paddingTop", "10rpx"], ["paddingRight", "10rpx"], ["paddingBottom", "10rpx"], ["paddingLeft", "10rpx"]]))], ["p-16", _pS(_uM([["paddingTop", "16rpx"], ["paddingRight", "16rpx"], ["paddingBottom", "16rpx"], ["paddingLeft", "16rpx"]]))], ["p-20", _pS(_uM([["paddingTop", "20rpx"], ["paddingRight", "20rpx"], ["paddingBottom", "20rpx"], ["paddingLeft", "20rpx"]]))], ["p-30", _pS(_uM([["paddingTop", "30rpx"], ["paddingRight", "30rpx"], ["paddingBottom", "30rpx"], ["paddingLeft", "30rpx"]]))], ["p-40", _pS(_uM([["paddingTop", "40rpx"], ["paddingRight", "40rpx"], ["paddingBottom", "40rpx"], ["paddingLeft", "40rpx"]]))], ["pt-10", _pS(_uM([["paddingTop", "10rpx"]]))], ["pt-16", _pS(_uM([["paddingTop", "16rpx"]]))], ["pt-20", _pS(_uM([["paddingTop", "20rpx"]]))], ["pt-30", _pS(_uM([["paddingTop", "30rpx"]]))], ["pt-40", _pS(_uM([["paddingTop", "40rpx"]]))], ["pr-10", _pS(_uM([["paddingRight", "10rpx"]]))]]),_uM([["pr-16", _pS(_uM([["paddingRight", "16rpx"]]))], ["pr-20", _pS(_uM([["paddingRight", "20rpx"]]))], ["pr-30", _pS(_uM([["paddingRight", "30rpx"]]))], ["pr-40", _pS(_uM([["paddingRight", "40rpx"]]))], ["pb-10", _pS(_uM([["paddingBottom", "10rpx"]]))], ["pb-16", _pS(_uM([["paddingBottom", "16rpx"]]))], ["pb-20", _pS(_uM([["paddingBottom", "20rpx"]]))], ["pb-30", _pS(_uM([["paddingBottom", "30rpx"]]))], ["pb-40", _pS(_uM([["paddingBottom", "40rpx"]]))], ["pl-10", _pS(_uM([["paddingLeft", "10rpx"]]))], ["pl-16", _pS(_uM([["paddingLeft", "16rpx"]]))], ["pl-20", _pS(_uM([["paddingLeft", "20rpx"]]))], ["pl-30", _pS(_uM([["paddingLeft", "30rpx"]]))], ["pl-40", _pS(_uM([["paddingLeft", "40rpx"]]))], ["p-x-10", _pS(_uM([["paddingLeft", "10rpx"], ["paddingRight", "10rpx"]]))], ["p-x-16", _pS(_uM([["paddingLeft", "16rpx"], ["paddingRight", "16rpx"]]))], ["p-x-20", _pS(_uM([["paddingLeft", "20rpx"], ["paddingRight", "20rpx"]]))], ["p-x-30", _pS(_uM([["paddingLeft", "30rpx"], ["paddingRight", "30rpx"]]))], ["p-x-40", _pS(_uM([["paddingLeft", "40rpx"], ["paddingRight", "40rpx"]]))], ["p-y-10", _pS(_uM([["paddingTop", "10rpx"], ["paddingBottom", "10rpx"]]))], ["p-y-16", _pS(_uM([["paddingTop", "16rpx"], ["paddingBottom", "16rpx"]]))], ["p-y-20", _pS(_uM([["paddingTop", "20rpx"], ["paddingBottom", "20rpx"]]))], ["p-y-30", _pS(_uM([["paddingTop", "30rpx"], ["paddingBottom", "30rpx"]]))], ["p-y-40", _pS(_uM([["paddingTop", "40rpx"], ["paddingBottom", "40rpx"]]))], ["br-4", _pS(_uM([["borderTopLeftRadius", "4rpx"], ["borderTopRightRadius", "4rpx"], ["borderBottomRightRadius", "4rpx"], ["borderBottomLeftRadius", "4rpx"]]))], ["br-6", _pS(_uM([["borderTopLeftRadius", "6rpx"], ["borderTopRightRadius", "6rpx"], ["borderBottomRightRadius", "6rpx"], ["borderBottomLeftRadius", "6rpx"]]))], ["br-8", _pS(_uM([["borderTopLeftRadius", "8rpx"], ["borderTopRightRadius", "8rpx"], ["borderBottomRightRadius", "8rpx"], ["borderBottomLeftRadius", "8rpx"]]))], ["br-10", _pS(_uM([["borderTopLeftRadius", "10rpx"], ["borderTopRightRadius", "10rpx"], ["borderBottomRightRadius", "10rpx"], ["borderBottomLeftRadius", "10rpx"]]))], ["br-16", _pS(_uM([["borderTopLeftRadius", "16rpx"], ["borderTopRightRadius", "16rpx"], ["borderBottomRightRadius", "16rpx"], ["borderBottomLeftRadius", "16rpx"]]))], ["br-20", _pS(_uM([["borderTopLeftRadius", "20rpx"], ["borderTopRightRadius", "20rpx"], ["borderBottomRightRadius", "20rpx"], ["borderBottomLeftRadius", "20rpx"]]))], ["br-30", _pS(_uM([["borderTopLeftRadius", "30rpx"], ["borderTopRightRadius", "30rpx"], ["borderBottomRightRadius", "30rpx"], ["borderBottomLeftRadius", "30rpx"]]))], ["btlr-4", _pS(_uM([["borderTopLeftRadius", "4rpx"]]))], ["btlr-6", _pS(_uM([["borderTopLeftRadius", "6rpx"]]))], ["btlr-8", _pS(_uM([["borderTopLeftRadius", "8rpx"]]))], ["btlr-10", _pS(_uM([["borderTopLeftRadius", "10rpx"]]))], ["btlr-16", _pS(_uM([["borderTopLeftRadius", "16rpx"]]))], ["btlr-20", _pS(_uM([["borderTopLeftRadius", "20rpx"]]))], ["btlr-30", _pS(_uM([["borderTopLeftRadius", "30rpx"]]))], ["btrr-4", _pS(_uM([["borderTopRightRadius", "4rpx"]]))], ["btrr-6", _pS(_uM([["borderTopRightRadius", "6rpx"]]))], ["btrr-8", _pS(_uM([["borderTopRightRadius", "8rpx"]]))], ["btrr-10", _pS(_uM([["borderTopRightRadius", "10rpx"]]))], ["btrr-16", _pS(_uM([["borderTopRightRadius", "16rpx"]]))], ["btrr-20", _pS(_uM([["borderTopRightRadius", "20rpx"]]))], ["btrr-30", _pS(_uM([["borderTopRightRadius", "30rpx"]]))], ["bblr-4", _pS(_uM([["borderBottomLeftRadius", "4rpx"]]))], ["bblr-6", _pS(_uM([["borderBottomLeftRadius", "6rpx"]]))], ["bblr-8", _pS(_uM([["borderBottomLeftRadius", "8rpx"]]))], ["bblr-10", _pS(_uM([["borderBottomLeftRadius", "10rpx"]]))], ["bblr-16", _pS(_uM([["borderBottomLeftRadius", "16rpx"]]))], ["bblr-20", _pS(_uM([["borderBottomLeftRadius", "20rpx"]]))], ["bblr-30", _pS(_uM([["borderBottomLeftRadius", "30rpx"]]))], ["bbrr-4", _pS(_uM([["borderBottomRightRadius", "4rpx"]]))], ["bbrr-6", _pS(_uM([["borderBottomRightRadius", "6rpx"]]))], ["bbrr-8", _pS(_uM([["borderBottomRightRadius", "8rpx"]]))], ["bbrr-10", _pS(_uM([["borderBottomRightRadius", "10rpx"]]))], ["bbrr-16", _pS(_uM([["borderBottomRightRadius", "16rpx"]]))], ["bbrr-20", _pS(_uM([["borderBottomRightRadius", "20rpx"]]))], ["bbrr-30", _pS(_uM([["borderBottomRightRadius", "30rpx"]]))], ["@FONT-FACE", _uM([["0", _uM([["fontFamily", "iconfont"], ["src", "url('/static/iconfont.ttf')"]])], ["1", _uM([["fontFamily", "AlimamaDaoLiTiTTF"], ["src", "url('/static/AlimamaDaoLiTi.ttf')"]])]])]])]
