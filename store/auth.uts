// 定义模型
export type AuthState = {
	token:string;
	userInfo:UserInfoState | null;
}

export type UserInfoState = {
	id:number;
	name:string;
	avatar:string;
	//其它字段
}

// 实例化为authState
export const authState = reactive({
	token:'',
	userInfo: {
		id:0,
		name:"",
		avatar:"",
		//其它字段
	},
} as AuthState)

// 本地存储(转为字符串存储)
function authLocalStorage(){
	uni.setStorage({
		key: 'authStateKey',
		data: JSON.stringify(authState)
	});
}

// 清除token和userInfo
export const clearAuthState = () => {
	authState.token = ''
	authState.userInfo = null
	//移除本地存储
	uni.removeStorage({ key: 'authStateKey' });
}

// 设置token,本地存储token
export const setAuthToken = (value: string) => {
	authState.token = value
	authLocalStorage()	//存储到本地
}

// 设置userInfo，本地存储userInfo
export const setAuthUserInfo = (value: UserInfoState) => {
	authState.userInfo = value
	authLocalStorage()	//存储到本地
}

// 设置所有数据本地存储
export function setAuthState (value: AuthState){
	authState.token = value.token
	authState.userInfo = value.userInfo
	authLocalStorage()	//存储到本地
}

// 同步从本地存储中获取authState的值
export function getAuthStateByStorageSync(){
	// 程序启动从本地存储中值(同步)
	let authStateString = uni.getStorageSync('authStateKey') as string
	if(authStateString != ''){
	let storageResult = JSON.parse<AuthState>(authStateString)		//字符串为AuthState类型
		setAuthState(storageResult as AuthState)					//赋值给store
	}
}