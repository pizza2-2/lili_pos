import { request } from '../index.uts'

// 当前项目中的 request 封装使用方式：
// request(url, method, reqData, showLoading)
// 其中 request 内部已经直接 resolve 服务器返回的 data 字段。

// 登录请求参数
export type LoginData = {
	username: string
	password: string
}

// 登录接口返回的 data 结构
export type LoginResponse = {
	access_token: string
	refresh_token: string
	token_type: string
	expires_in: number
}

// 当前用户信息接口返回的 data 结构
export type ProfileResponse = {
	id: number
	username: string
	email: string
	first_name: string
	last_name: string
	is_active: boolean
	date_joined: string
	is_platform_admin: boolean
	company_id: number
	company_name: string
	name: string
	cashier_account: string
	cashier_id: number | null
	shop_ids: number[]
	permissions: string[]
}

// 登录接口外层返回格式示例：
// {
//   "success": true,
//   "status": "success",
//   "status_code": 200,
//   "message": "登录成功",
//   "data": {
//     "access_token": "...",
//     "refresh_token": "...",
//     "token_type": "Bearer",
//     "expires_in": 3600
//   },
//   "timestamp": "2026-04-14T18:40:53.721118+00:00"
// }
// 因为 request 已经返回 data，所以这里的 Promise 类型只写 data 内部结构。

// 账号登录
export async function accountLogin(data: LoginData): Promise<LoginResponse> {
	const body = {
		username: data.username,
		password: data.password
	} as UTSJSONObject
	const raw = await request(
		'/api/accounts/auth/login/',
		'POST',
		body,
		true
	)
	const parsed = JSON.parseObject<LoginResponse>(JSON.stringify(raw))
	if (parsed == null) {
		throw new Error('登录响应解析失败')
	}
	return parsed!
}
// {
//     "success": true,
//     "status": "success",
//     "status_code": 200,
//     "message": "操作成功",
//     "data": {
//         "id": 2,
//         "username": "733063605",
//         "email": "",
//         "first_name": "",
//         "last_name": "",
//         "is_active": true,
//         "date_joined": "2026-04-10T22:22:59.620643+02:00",
//         "is_platform_admin": false,
//         "company_id": 1,
//         "company_name": "HAODUODUO",
//         "name": "",
//         "cashier_account": "",
//         "cashier_id": null,
//         "shop_ids": [],
//         "permissions": [
//             "approve",
//             "export",
//             "manage_inventory",
//             "manage_purchase",
//             "manage_users",
//             "view_cost",
//             "void"
//         ]
//     },
//     "timestamp": "2026-04-15T12:28:54.167391+00:00"
// }

// 获取当前登录用户信息
export async function getProfile(): Promise<ProfileResponse> {
	const raw = await request(
		'/api/accounts/auth/me/',
		'GET',
		{} as UTSJSONObject,
		false
	)
	const parsed = JSON.parseObject<ProfileResponse>(JSON.stringify(raw))
	if (parsed == null) {
		throw new Error('用户信息响应解析失败')
	}
	return parsed!
}

// 账号登出
export function logout(): Promise<any> {
	return request(
		'/api/accounts/auth/logout/',
		'POST',
		{} as UTSJSONObject,
		true
	)
}
