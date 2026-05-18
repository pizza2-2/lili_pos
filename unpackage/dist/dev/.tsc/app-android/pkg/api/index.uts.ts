import { authState, redirectToLogin } from '@/store/auth'

export const baseUrl: string = 'http://43.157.91.24:1996'		//服务器请求接口地址http://43.157.91.24:1996/
// export const baseUrl: string = 'http://192.168.43.173:8000'		//服务器请求接口地址http://43.157.91.24:1996/
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

export function buildDownloadHeader(): UTSJSONObject {
	const header = {} as UTSJSONObject
	if (authState.token != '') {
		header['Authorization'] = authState.token
	}
	return header
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

function handleUnauthorizedResponse(url: string, showLoading: boolean) {
	clearLatestResponseMeta()
	if (showLoading) {
		uni.hideLoading()
	}
	if (shouldHandleUnauthorized(url)) {
		redirectToLogin('登录状态已失效，请重新登录')
	}
}

function stringValue(value: any | null): string {
	if (value == null) return ''
	return '' + value
}

function intValue(value: any | null): number {
	const parsed = parseInt(stringValue(value))
	if (isNaN(parsed)) return 0
	return parsed
}

function boolValue(value: any | null): boolean {
	const text = stringValue(value).toLowerCase()
	return text == 'true' || text == '1'
}

function parseObject(value: any | null): UTSJSONObject | null {
	if (value == null) return null
	const text = JSON.stringify(value)
	if (text == null || text == '') return null
	try {
		return JSON.parseObject<UTSJSONObject>(text)
	} catch (error) {
		return null
	}
}

function saveLatestResponseMetaObject(response: UTSJSONObject) {
	latestResponseMeta = {
		success: boolValue(response['success']),
		status: stringValue(response['status']),
		status_code: intValue(response['status_code']),
		message: stringValue(response['message']),
		timestamp: stringValue(response['timestamp']),
	} as ResponseMeta
}

function extractQuotedMessage(text: string): string {
	const marker = '"message":"'
	const markerIndex = text.indexOf(marker)
	if (markerIndex < 0) return ''
	const startIndex = markerIndex + marker.length
	const endIndex = text.indexOf('"', startIndex)
	if (endIndex < 0) return ''
	return text.substring(startIndex, endIndex)
}

function parseRequestFailMessage(err: any | null): string {
	const errorText = stringValue(err)
	const backendMessage = extractQuotedMessage(errorText)
	if (backendMessage != '') return backendMessage
	if (errorText != '' && errorText != '[object Object]') return errorText
	return '网络请求失败'
}

//发送请求，url：请求地址，method：请求方式，reqData：请求数据，showLoading：是否显示loading，默认不显示
export async function request(url:string, method:RequestMethod, reqData:UTSJSONObject = {},showLoading:boolean = false): Promise<any> {
	return new Promise((resolve,reject) => {
		clearLatestResponseMeta()
		if(showLoading){
			uni.showLoading({ title: 'loading' })
		}
		const interceptMap = requestIntercept(reqData)	//请求拦截，返回的是header和data
		__f__('log','at pkg/api/index.uts:162','请求地址:', baseUrl + url)
		uni.request<any>({
			url: baseUrl + url,
			method: method,
			header: interceptMap.get('header'),
			data: interceptMap.get('data'),
			timeout: timeOut,
			success:(res) => {
				//这里首先是判断网络请求的状态码
				if (res.statusCode == 401) {
					handleUnauthorizedResponse(url, showLoading)
					reject(new Error('登录状态已失效'))
					return
				}
				const responseData = res.data
				const responseObject = parseObject(responseData)
				if(res.statusCode >= 200 && res.statusCode < 300){
					// 优先兼容统一响应格式: { success, message, data }
					if (responseObject != null && boolValue(responseObject!['success'])) {
						saveLatestResponseMetaObject(responseObject!)
						
						//这里可以判断返回的header中是否有token，做到无感刷新token的功能
						// const headers = res.header as UTSJSONObject
						// if('token' in headers){
						// 	//设置本地token
						// 	setAuthToken(headers.get('token') as string)
						// }
						
						resolve(responseObject!['data']);	//直接resolve服务器返回的data内容
						return
					}
					if (responseObject != null && stringValue(responseObject!['success']) == 'false') {
						if (intValue(responseObject!['status_code']) == 401) {
							handleUnauthorizedResponse(url, showLoading)
							reject(new Error('登录状态已失效'))
							return
						}
						clearLatestResponseMeta()
						const serverMessage = stringValue(responseObject!['message'])
						reject(new Error(serverMessage == '' ? '请求失败' : serverMessage))
						return
					}
					// 兼容后端直接返回对象/数组（无 success 包裹）
					clearLatestResponseMeta()
					resolve(responseData)
					return
				}
				//可以做其它判断....
				clearLatestResponseMeta()
				if (responseObject != null && stringValue(responseObject!['success']) == 'false') {
					const serverMessage = stringValue(responseObject!['message'])
					if (serverMessage != '') {
						reject(new Error(serverMessage))
						return
					}
				}
				reject(new Error("HTTP状态码错误: " + res.statusCode))
			},
			fail:(err) => {
				clearLatestResponseMeta()
				reject(new Error(parseRequestFailMessage(err)))
			},
			complete:() =>{
				if(showLoading){
					uni.hideLoading()
				}
			}
		})
	})
}
