// #ifdef UNI-APP-X
// 检查更新改走自建 Django 升级中心（NewDjango apps/upgrade_center），不依赖 uniCloud
import { baseUrl } from '@/pkg/api/index.uts'
// #endif

export type StoreListItem = {
	enable : boolean
	id : string
	name : string
	scheme : string
	priority : number // 优先级
}

export type UniUpgradeCenterResult = {
	_id : string
	appid : string
	name : string
	title : string
	contents : string
	url : string // 安装包下载地址
	platform : Array<string> // Array<'Android' | 'iOS' | 'Harmony'>
	version : string // 版本号 1.0.0
	uni_platform : string // "android" | "ios" | 'harmony'
	stable_publish : boolean // 是否是稳定版
	is_mandatory : boolean // 是否强制更新
	is_silently : boolean | null	// 是否静默更新
	create_env : string // "upgrade-center"
	create_date : number
	message : string
	code : number

	type : string // "native_app" | "wgt"
	store_list : StoreListItem[] | null
	min_uni_version : string | null  // 升级 wgt 的最低 uni-app 版本
}

export default function () : Promise<UniUpgradeCenterResult> {
	// #ifdef APP
	return new Promise<UniUpgradeCenterResult>((resolve, reject) => {
		const systemInfo = uni.getSystemInfoSync()
		const appId = systemInfo.appId
		const appVersion = systemInfo.appVersion //systemInfo.appVersion
		// #ifndef UNI-APP-X
		if (typeof appId === 'string' && typeof appVersion === 'string' && appId.length > 0 && appVersion.length > 0) {
			plus.runtime.getProperty(appId, function (widgetInfo) {
				if (widgetInfo.version) {
					let data = {
						action: 'checkVersion',
						appid: appId,
						appVersion: appVersion,
						wgtVersion: widgetInfo.version
					}
					uniCloud.callFunction({
						name: 'uni-upgrade-center',
						data,
						success: (e) => {
							resolve(e.result as UniUpgradeCenterResult)
						},
						fail: (error) => {
							reject(error)
						}
					})
				} else {
					reject('widgetInfo.version is EMPTY')
				}
			})
		} else {
			reject('plus.runtime.appid is EMPTY')
		}
		// #endif
		// #ifdef UNI-APP-X
		if (typeof appId === 'string' && typeof appVersion === 'string' && appId.length > 0 && appVersion.length > 0) {
			// 响应契约与 uni-upgrade-center 云函数同构：code>0 有新版本，code==0 已最新，code<0 错误
			let data = {
				appid: appId,
				appVersion: appVersion,
				uni_platform: systemInfo.platform
			}
			try {
				uni.request<UTSJSONObject>({
					url: `${baseUrl}/api/app/check-version/`,
					method: 'POST',
					timeout: 10000,
					data: data,
					success: (res) => {
						const resData = res.data
						if (resData == null) {
							reject('升级检查失败：响应为空')
							return
						}
						const codeValue = resData.getNumber('code')
						if (codeValue == null) {
							reject('升级检查失败：响应缺少 code 字段')
							return
						}
						const code = codeValue as number
						const message = resData.getString('message') ?? ''
						if (code <= 0) {
							reject({
								code: code,
								message: message
							})
							return
						}
						const result = JSON.parse<UniUpgradeCenterResult>(JSON.stringify(resData)) as UniUpgradeCenterResult
						resolve(result)
					},
					fail: (err) => {
						reject(`升级检查请求失败: ${err.errMsg}`)
					}
				})
			} catch (e) {
				reject(e.message)
			}
		} else {
			reject('invalid appid or appVersion')
		}
		// #endif
	})
	// #endif
	// #ifndef APP
	return new Promise((resolve, reject) => {
		reject({
			message: '请在App中使用'
		})
	})
	// #endif
}
