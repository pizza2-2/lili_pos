import { authState, redirectToLogin } from '@/store/auth'

export const baseUrl: string = 'http://192.168.43.173:8000'		//服务器请求接口地址
export const timeOut: number = 10000							//网络请求超时时间
const loginApiUrl = '/api/accounts/auth/login/'

// 服务器返回通用格式
type RootType= {
	success: boolean;
	status: string;
	status_code: number;
	message: string;
	data: any;
	timestamp: string;
}

type ResponseMeta = {
	success: boolean;
	status: string;
	status_code: number;
	message: string;
	timestamp: string;
}

let latestResponseMeta: ResponseMeta | null = null

function clearLatestResponseMeta() {
	latestResponseMeta = null
}

function saveLatestResponseMeta(response: RootType) {
	latestResponseMeta = {
		success: response.success,
		status: response.status,
		status_code: response.status_code,
		message: response.message,
		timestamp: response.timestamp,
	} as ResponseMeta
}

export function takeLatestResponseMessage(fallback: string = ''): string {
	if (latestResponseMeta == null) {
		return fallback
	}
	const message = latestResponseMeta!.message != '' ? latestResponseMeta!.message : fallback
	clearLatestResponseMeta()
	return message
}

// 自定义方法请求拦截,可以在此方法中对header和data做处理。比如这里就把本地的token添加到header中
function requestIntercept(reqData:UTSJSONObject):Map<string,UTSJSONObject>{
	const map = new Map<string, UTSJSONObject>()
	
	// 请求头
	const header = {
		'content-type': 'application/json',
	} as UTSJSONObject
	// 如果本地存储的有token，则添加到header中
	if (authState.token != '') {
		header['Authorization'] = authState.token
	}
	
	// 当然也可以对请求体做操作，比如这里把时间戳添加进了data中
	let timestamp = new Date().getTime().toString()
	reqData['timestamp'] = timestamp
	
	//返回header和data
	map.set('header',header as UTSJSONObject)
	map.set('data',reqData as UTSJSONObject)
	return map
}

function shouldHandleUnauthorized(url: string) : boolean {
	if (url == loginApiUrl) {
		return false
	}
	return true
}

//发送请求，url：请求地址，method：请求方式，reqData：请求数据，showLoading：是否显示loading，默认不显示
export async function request(url:string, method:RequestMethod, reqData:UTSJSONObject = {},showLoading:boolean = false): Promise<any> {
	return new Promise((resolve,reject) => {
		clearLatestResponseMeta()
		if(showLoading){
			uni.showLoading({ title: 'loading' })
		}
		const interceptMap = requestIntercept(reqData)	//请求拦截，返回的是header和data
		__f__('log','at pkg/api/index.uts:88','请求地址:', baseUrl + url)
		uni.request<RootType>({
			url: baseUrl + url,
			method: method,
			header: interceptMap.get('header'),
			data: interceptMap.get('data'),
			timeout: timeOut,
			success:(res) => {
				//这里首先是判断网络请求的状态码
				if(res.statusCode >= 200 && res.statusCode < 300){
					// 优先兼容统一响应格式: { success, message, data }
					if (res.data != null && res.data.success == true) {
						saveLatestResponseMeta(res.data as RootType)
						
						//这里可以判断返回的header中是否有token，做到无感刷新token的功能
						// const headers = res.header as UTSJSONObject
						// if('token' in headers){
						// 	//设置本地token
						// 	setAuthToken(headers.get('token') as string)
						// }
						
						resolve(res.data.data);	//直接resolve服务器返回的data内容
						return
					}
					if (res.data != null && res.data.success == false) {
						clearLatestResponseMeta()
						reject(new Error(res.data?.message ?? "请求失败"))
						return
					}
					// 兼容后端直接返回对象/数组（无 success 包裹）
					clearLatestResponseMeta()
					resolve(res.data)
					return
				}
				if (res.statusCode == 401) {
					clearLatestResponseMeta()
					if (shouldHandleUnauthorized(url)) {
						redirectToLogin('登录状态已失效，请重新登录')
					}
					reject(new Error('登录状态已失效'))
					return
				}
				//可以做其它判断....
				clearLatestResponseMeta()
				reject(new Error("HTTP状态码错误: " + res.statusCode))
			},
			fail:(err) => {
				clearLatestResponseMeta()
				let message = '网络请求失败'
				if (err != null) {
					const errorText = JSON.stringify(err)
					if (errorText != null && errorText != '') {
						const parsedError = JSON.parseObject<UTSJSONObject>(errorText)
						if (parsedError != null) {
							const errMsg = parsedError!['errMsg']
							if (errMsg != null) {
								const parsedErrMsg = errMsg as string
								if (parsedErrMsg != '') {
									message = parsedErrMsg
								}
							}
						}
					}
					if (message == '网络请求失败') {
						const fallbackMessage = errorText
						if (fallbackMessage != null && fallbackMessage != '') {
							message = fallbackMessage
						}
					}
				}
				reject(new Error(message))
			},
			complete:() =>{
				if(showLoading){
					uni.hideLoading()
				}
			}
		})
	})
}
