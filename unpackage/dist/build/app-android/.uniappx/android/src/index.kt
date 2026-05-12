@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME", "UNUSED_ANONYMOUS_PARAMETER", "SENSELESS_COMPARISON", "NAME_SHADOWING", "UNNECESSARY_NOT_NULL_ASSERTION")
package uni.UNI1CE1B14
import io.dcloud.uniapp.*
import io.dcloud.uniapp.extapi.*
import io.dcloud.uniapp.framework.*
import io.dcloud.uniapp.runtime.*
import io.dcloud.uniapp.vue.*
import io.dcloud.uniapp.vue.shared.*
import io.dcloud.unicloud.*
import io.dcloud.uts.*
import io.dcloud.uts.Map
import io.dcloud.uts.Set
import io.dcloud.uts.UTSAndroid
import kotlin.properties.Delegates
import uts.sdk.modules.uniRegisterRequestPermissionTips.RequestPermissionTipsListener
import io.dcloud.uniapp.extapi.addInterceptor as uni_addInterceptor
import io.dcloud.uniapp.extapi.createPushMessage as uni_createPushMessage
import io.dcloud.uniapp.extapi.exit as uni_exit
import io.dcloud.uniapp.extapi.getAppAuthorizeSetting as uni_getAppAuthorizeSetting
import io.dcloud.uniapp.extapi.getPrivacySetting as uni_getPrivacySetting
import io.dcloud.uniapp.extapi.getPushChannelManager as uni_getPushChannelManager
import io.dcloud.uniapp.extapi.getPushClientId as uni_getPushClientId
import io.dcloud.uniapp.extapi.getStorageSync as uni_getStorageSync
import io.dcloud.uniapp.extapi.hideLoading as uni_hideLoading
import io.dcloud.uniapp.extapi.hideToast as uni_hideToast
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.onPrivacyAuthorizationChange as uni_onPrivacyAuthorizationChange
import io.dcloud.uniapp.extapi.onPushMessage as uni_onPushMessage
import io.dcloud.uniapp.extapi.openDialogPage as uni_openDialogPage
import io.dcloud.uniapp.extapi.reLaunch as uni_reLaunch
import uts.sdk.modules.uniRegisterRequestPermissionTips.registerRequestPermissionTipsListener
import uts.sdk.modules.uniRegisterRequestPermissionTips.unregisterRequestPermissionTipsListener
import uts.sdk.modules.uniRegisterRequestPermissionTips.setRequestPermissionTips
import io.dcloud.uniapp.extapi.removeInterceptor as uni_removeInterceptor
import io.dcloud.uniapp.extapi.removeStorageSync as uni_removeStorageSync
import io.dcloud.uniapp.extapi.request as uni_request
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
import io.dcloud.uniapp.extapi.uploadFile as uni_uploadFile
val runBlock1 = run {
    __uniConfig.getAppStyles = fun(): Map<String, Map<String, Map<String, Any>>> {
        return GenApp.styles
    }
}
val isAgreePrivacyState = ref(false)
val runBlock2 = run {
    watch(fun(): Boolean {
        return isAgreePrivacyState.value
    }
    , fun(newVal: Boolean){
        if (newVal) {
            console.log("初始化推送")
            uni_getPushClientId(GetPushClientIdOptions(success = fun(res){
                var push_clientid = res.cid
                console.log("客户端推送标识:", push_clientid)
            }
            , fail = fun(err) {
                console.log(err)
            }
            ))
            val manager = uni_getPushChannelManager()
            manager.setPushChannel(SetPushChannelOptions(channelId = "channel-id", channelDesc = "通知渠道描述", enableLights = true, enableVibration = true, importance = 4, lockscreenVisibility = 1))
            uni_onPushMessage(fun(res){
                console.log("收到推送消息：", res)
                if (res.type == "receive") {
                    if (uni_getAppAuthorizeSetting().notificationAuthorized == "authorized") {
                        console.log("推送权限已开")
                        uni_createPushMessage(CreatePushMessageOptions(title = res.data["title"] as String?, content = res.data["content"] as String, cover = true, channelId = "channel-id", `when` = Date.now() + 10000, icon = "/static/logo.png", sound = "system", delay = 1, payload = _uO("pkey" to "pvalue1"), category = "IM", success = fun(res) {
                            console.log("res: " + res)
                            uni_hideToast()
                            uni_showToast(ShowToastOptions(title = "创建本地通知消息成功"))
                        }, fail = fun(e) {
                            console.log("fail :" + e)
                            uni_hideToast()
                            uni_showToast(ShowToastOptions(title = "创建本地通知消息失败", icon = "error"))
                        }))
                    } else {
                        uni_showToast(ShowToastOptions(title = "请在设置中开启通知权限", icon = "error"))
                    }
                } else if (res.type == "click") {}
            }
            )
        }
    }
    , WatchOptions(immediate = true))
}
open class AuthState (
    @JsonNotNull
    open var token: String,
    open var userInfo: UserInfoState? = null,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return AuthStateReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class AuthStateReactiveObject : AuthState, IUTSReactive<AuthState> {
    override var __v_raw: AuthState
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: AuthState, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(token = __v_raw.token, userInfo = __v_raw.userInfo) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): AuthStateReactiveObject {
        return AuthStateReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var token: String
        get() {
            return _tRG(__v_raw, "token", __v_raw.token, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("token")) {
                return
            }
            val oldValue = __v_raw.token
            __v_raw.token = value
            _tRS(__v_raw, "token", oldValue, value)
        }
    override var userInfo: UserInfoState?
        get() {
            return _tRG(__v_raw, "userInfo", __v_raw.userInfo, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("userInfo")) {
                return
            }
            val oldValue = __v_raw.userInfo
            __v_raw.userInfo = value
            _tRS(__v_raw, "userInfo", oldValue, value)
        }
}
open class UserInfoState (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var avatar: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return UserInfoStateReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class UserInfoStateReactiveObject : UserInfoState, IUTSReactive<UserInfoState> {
    override var __v_raw: UserInfoState
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: UserInfoState, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, name = __v_raw.name, avatar = __v_raw.avatar) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UserInfoStateReactiveObject {
        return UserInfoStateReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return _tRG(__v_raw, "id", __v_raw.id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            _tRS(__v_raw, "id", oldValue, value)
        }
    override var name: String
        get() {
            return _tRG(__v_raw, "name", __v_raw.name, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("name")) {
                return
            }
            val oldValue = __v_raw.name
            __v_raw.name = value
            _tRS(__v_raw, "name", oldValue, value)
        }
    override var avatar: String
        get() {
            return _tRG(__v_raw, "avatar", __v_raw.avatar, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("avatar")) {
                return
            }
            val oldValue = __v_raw.avatar
            __v_raw.avatar = value
            _tRS(__v_raw, "avatar", oldValue, value)
        }
}
val loginPageUrl = "/pages/login/login"
var redirectingToLogin = false
@JvmField
val authState = reactive(AuthState(token = "", userInfo = UserInfoState(id = 0, name = "", avatar = "")))
fun authLocalStorage() {
    uni_setStorageSync("authStateKey", JSON.stringify(authState))
}
val clearAuthState = fun(){
    authState.token = ""
    authState.userInfo = null
    uni_removeStorageSync("authStateKey")
}
fun isLoginPageActive(): Boolean {
    val pages = getCurrentPages()
    if (pages.length == 0) {
        return false
    }
    val currentPage = pages[pages.length - 1]
    val currentRoute = currentPage.route
    if (currentRoute == null) {
        return false
    }
    return "/" + currentRoute == loginPageUrl
}
fun resetRedirectFlagWithDelay() {
    setTimeout(fun(){
        redirectingToLogin = false
    }
    , 1500)
}
fun redirectToLogin(message: String = "登录已过期，请重新登录") {
    clearAuthState()
    if (redirectingToLogin) {
        return
    }
    if (isLoginPageActive()) {
        return
    }
    redirectingToLogin = true
    if (message != "") {
        uni_showToast(ShowToastOptions(title = message, icon = "none"))
    }
    uni_reLaunch(ReLaunchOptions(url = loginPageUrl, fail = fun(_){
        uni_navigateTo(NavigateToOptions(url = loginPageUrl))
    }
    , complete = fun(_){
        resetRedirectFlagWithDelay()
    }
    ))
}
val setAuthToken = fun(value: String){
    authState.token = value
    authLocalStorage()
}
val setAuthUserInfo = fun(value: UserInfoState){
    authState.userInfo = value
    authLocalStorage()
}
fun setAuthState(value: AuthState) {
    authState.token = value.token
    authState.userInfo = value.userInfo
    authLocalStorage()
}
fun getAuthStateByStorageSync() {
    var authStateString = uni_getStorageSync("authStateKey") as String
    if (authStateString != "") {
        var storageResult = JSON.parse<AuthState>(authStateString)
        setAuthState(storageResult as AuthState)
    }
}
val routerWhiteList = _uA(
    "/pages/login/login",
    "/pages/webview/webview",
    "/pages/privacy/privacy"
)
val loginUrl = "/pages/login/login"
val navigateToInterceptor = AddInterceptorOptions(invoke = fun(options: NavigateToOptions) {
    val url: String = options.url.split("?")[0]
    var pass = routerWhiteList.findIndex(fun(eUrl: String): Boolean {
        return eUrl == url
    }
    )
    if (pass == -1 && authState.token == "") {
        options.url = loginUrl
    }
}
)
fun routerPermission() {
    uni_addInterceptor("navigateTo", navigateToInterceptor)
}
fun removeRouterPermission() {
    uni_removeInterceptor("navigateTo", null)
}
val enableRequestPermissionTipsListener = false
val timeNumber = ref<Number>(15)
val disAllowLocation = ref<Number>(0)
val disAllowCamera = ref<Number>(0)
val disAllowAlbum = ref<Number>(0)
var PermissionTips: UTSJSONObject = _uO()
val runBlock3 = run {
    PermissionTips["android.permission.ACCESS_COARSE_LOCATION"] = "<h1>定位权限说明</h1><p style=\"color:#cccccc\">为了提供您所在区域的信息服务，我们需要获取您设备所在区域信息。以便于向您展示商品信息。</p>"
    PermissionTips["android.permission.ACCESS_FINE_LOCATION"] = "<h1>定位权限说明</h1><p style=\"color:#cccccc\">为了提供您所在区域的信息服务，我们需要获取您设备所在区域信息。以便于向您展示商品信息。</p>"
    PermissionTips["android.permission.CAMERA"] = "<h1>相机权限说明</h1><p style=\"color:#cccccc\">便于您使用该功能上传您的照片用于更换头像、扫码、拍照，发布商品等场景</p>"
    PermissionTips["android.permission.READ_EXTERNAL_STORAGE"] = "<h1>相册权限说明</h1><p style=\"color:#cccccc\">便于您使用该功能上传您的照片/图片/视频及用于更换头像、拍照，发布商品等场景中读取相册和文件内容</p>"
    PermissionTips["android.permission.WRITE_EXTERNAL_STORAGE"] = "<h1>相册权限说明</h1><p style=\"color:#cccccc\">便于您使用该功能上传您的照片/图片/视频及用于更换头像、拍照，发布商品等场景中写入相册和文件内容</p>"
    PermissionTips["android.permission.CALL_PHONE"] = "<h1>电话权限说明</h1><p style=\"color:#cccccc\">为了您能和商家客户进行电话通话，我们需要获取您拨打电话的权限。</p>"
}
fun registerOSPermission() {
    if (!enableRequestPermissionTipsListener) {
        return
    }
    setRequestPermissionTips(PermissionTips)
    registerRequestPermissionTipsListener(RequestPermissionTipsListener(onComplete = fun(e){
        for(k in resolveUTSKeyIterator(PermissionTips)){
            if (e[k] == "denied") {
                if (k == "android.permission.ACCESS_COARSE_LOCATION" || k == "android.permission.ACCESS_FINE_LOCATION") {
                    disAllowLocation.value = Date().getTime()
                } else if (k == "android.permission.CAMERA") {
                    disAllowCamera.value = Date().getTime()
                } else if (k == "android.permission.READ_EXTERNAL_STORAGE" || k == "android.permission.android.permission.WRITE_EXTERNAL_STORAGE") {
                    disAllowAlbum.value = Date().getTime()
                }
            }
        }
    }
    ))
}
fun unregisterOSPermission() {
    if (!enableRequestPermissionTipsListener) {
        return
    }
    unregisterRequestPermissionTipsListener(null)
}
var firstBackTime: Number = 0
open class GenApp : BaseApp {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {
        onLaunch(fun(_: OnLaunchOptions) {
            console.log("App Launch")
            registerOSPermission()
            getAuthStateByStorageSync()
            routerPermission()
            uni_onPrivacyAuthorizationChange(fun(res){
                isAgreePrivacyState.value = !res.needAuthorization
            }
            )
            uni_getPrivacySetting(GetPrivacySettingOptions(success = fun(res) {
                if (res.needAuthorization) {
                    isAgreePrivacyState.value = false
                    uni_openDialogPage(OpenDialogPageOptions(url = "/pages/privacy/privacy"))
                } else {
                    isAgreePrivacyState.value = true
                }
            }
            ))
        }
        , __ins)
        onAppShow(fun(options: OnShowOptions) {
            console.log("App Show")
            console.log(options)
        }
        , __ins)
        onAppHide(fun() {
            console.log("App Hide")
        }
        , __ins)
        onLastPageBackPress(fun() {
            console.log("App LastPageBackPress")
            if (firstBackTime == 0) {
                uni_showToast(ShowToastOptions(title = "再按一次退出应用", position = "bottom"))
                firstBackTime = Date.now()
                setTimeout(fun(){
                    firstBackTime = 0
                }, 2000)
            } else if (Date.now() - firstBackTime < 2000) {
                firstBackTime = Date.now()
                uni_exit(null)
            }
        }
        , __ins)
        onError(fun(err: Any) {
            console.log(err)
        }
        , __ins)
        onExit(fun() {
            console.log("App Exit")
            unregisterOSPermission()
            removeRouterPermission()
        }
        , __ins)
    }
    companion object {
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0,
                styles1
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("iconfont" to _pS(_uM("fontFamily" to "iconfont", "fontStyle" to "normal")), "alimama-daoliti" to _pS(_uM("fontFamily" to "AlimamaDaoLiTiTTF")), "w100" to _pS(_uM("width" to "100%")), "h100" to _pS(_uM("height" to "100%")), "wh100" to _pS(_uM("width" to "100%", "height" to "100%")), "flexc" to _pS(_uM("display" to "flex", "flexDirection" to "column")), "flexcr" to _pS(_uM("display" to "flex", "flexDirection" to "column-reverse")), "flexr" to _pS(_uM("display" to "flex", "flexDirection" to "row")), "flexrr" to _pS(_uM("display" to "flex", "flexDirection" to "row-reverse")), "flex-fw-w" to _pS(_uM("flexWrap" to "wrap")), "flex-fw-wr" to _pS(_uM("flexWrap" to "wrap-reverse")), "flex-jc-e" to _pS(_uM("justifyContent" to "flex-end")), "flex-jc-a" to _pS(_uM("justifyContent" to "space-around")), "flex-jc-c" to _pS(_uM("justifyContent" to "center")), "flex-jc-b" to _pS(_uM("justifyContent" to "space-between")), "flex-ai-s" to _pS(_uM("alignItems" to "flex-start")), "flex-ai-e" to _pS(_uM("alignItems" to "flex-end")), "flex-ai-c" to _pS(_uM("alignItems" to "center")), "flex1" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "color-base" to _pS(_uM("color" to "#409eff")), "color-second" to _pS(_uM("color" to "#00daff")), "color-grey" to _pS(_uM("color" to "#a2a2a2")), "color-black" to _pS(_uM("color" to "#171717")), "color-white" to _pS(_uM("color" to "#ffffff")), "color-red" to _pS(_uM("color" to "#FF0000")), "bg-base" to _pS(_uM("backgroundImage" to "none", "backgroundColor" to "#007aff")), "bg-second" to _pS(_uM("backgroundImage" to "none", "backgroundColor" to "#00daff")), "bg-grey" to _pS(_uM("backgroundImage" to "none", "backgroundColor" to "#F8F8F8")), "bg-white" to _pS(_uM("backgroundImage" to "none", "backgroundColor" to "#ffffff")), "bg-black" to _pS(_uM("backgroundImage" to "none", "backgroundColor" to "#171717")), "line" to _pS(_uM("height" to "1rpx", "backgroundImage" to "none", "backgroundColor" to "#eeeeee")), "v-line" to _pS(_uM("width" to "2rpx", "backgroundColor" to "#a2a2a2")), "fs-10" to _pS(_uM("fontSize" to "10rpx")), "fs-12" to _pS(_uM("fontSize" to "12rpx")), "fs-14" to _pS(_uM("fontSize" to "14rpx")), "fs-16" to _pS(_uM("fontSize" to "16rpx")), "fs-18" to _pS(_uM("fontSize" to "18rpx")), "fs-20" to _pS(_uM("fontSize" to "20rpx")), "fs-22" to _pS(_uM("fontSize" to "22rpx")), "fs-24" to _pS(_uM("fontSize" to "24rpx")), "fs-26" to _pS(_uM("fontSize" to "26rpx")), "fs-28" to _pS(_uM("fontSize" to "28rpx")), "fs-30" to _pS(_uM("fontSize" to "30rpx")), "fs-32" to _pS(_uM("fontSize" to "32rpx")), "fs-34" to _pS(_uM("fontSize" to "34rpx")), "fs-36" to _pS(_uM("fontSize" to "36rpx")), "fs-38" to _pS(_uM("fontSize" to "38rpx")), "fs-40" to _pS(_uM("fontSize" to "40rpx")), "fs-60" to _pS(_uM("fontSize" to "60rpx")), "ta-c" to _pS(_uM("textAlign" to "center")), "to-1" to _pS(_uM("textOverflow" to "ellipsis", "lines" to 1)), "to-2" to _pS(_uM("textOverflow" to "ellipsis", "lines" to 2)), "fw-400" to _pS(_uM("fontWeight" to "400")), "fw-700" to _pS(_uM("fontWeight" to "700")), "m-10" to _pS(_uM("marginTop" to "10rpx", "marginRight" to "10rpx", "marginBottom" to "10rpx", "marginLeft" to "10rpx")), "m-16" to _pS(_uM("marginTop" to "16rpx", "marginRight" to "16rpx", "marginBottom" to "16rpx", "marginLeft" to "16rpx")), "m-20" to _pS(_uM("marginTop" to "20rpx", "marginRight" to "20rpx", "marginBottom" to "20rpx", "marginLeft" to "20rpx")), "m-30" to _pS(_uM("marginTop" to "30rpx", "marginRight" to "30rpx", "marginBottom" to "30rpx", "marginLeft" to "30rpx")), "m-40" to _pS(_uM("marginTop" to "40rpx", "marginRight" to "40rpx", "marginBottom" to "40rpx", "marginLeft" to "40rpx")), "mt-10" to _pS(_uM("marginTop" to "10rpx")), "mt-16" to _pS(_uM("marginTop" to "16rpx")), "mt-20" to _pS(_uM("marginTop" to "20rpx")), "mt-30" to _pS(_uM("marginTop" to "30rpx")), "mt-40" to _pS(_uM("marginTop" to "40rpx")), "mr-10" to _pS(_uM("marginRight" to "10rpx")), "mr-16" to _pS(_uM("marginRight" to "16rpx")), "mr-20" to _pS(_uM("marginRight" to "20rpx")), "mr-30" to _pS(_uM("marginRight" to "30rpx")), "mr-40" to _pS(_uM("marginRight" to "40rpx")), "mb-10" to _pS(_uM("marginBottom" to "10rpx")), "mb-16" to _pS(_uM("marginBottom" to "16rpx")), "mb-20" to _pS(_uM("marginBottom" to "20rpx")), "mb-30" to _pS(_uM("marginBottom" to "30rpx")), "mb-40" to _pS(_uM("marginBottom" to "40rpx")), "ml-10" to _pS(_uM("marginLeft" to "10rpx")), "ml-16" to _pS(_uM("marginLeft" to "16rpx")), "ml-20" to _pS(_uM("marginLeft" to "20rpx")), "ml-30" to _pS(_uM("marginLeft" to "30rpx")), "ml-40" to _pS(_uM("marginLeft" to "40rpx")), "m-x-10" to _pS(_uM("marginLeft" to "10rpx", "marginRight" to "10rpx")), "m-x-16" to _pS(_uM("marginLeft" to "16rpx", "marginRight" to "16rpx")), "m-x-20" to _pS(_uM("marginLeft" to "20rpx", "marginRight" to "20rpx")), "m-x-30" to _pS(_uM("marginLeft" to "30rpx", "marginRight" to "30rpx")), "m-x-40" to _pS(_uM("marginLeft" to "40rpx", "marginRight" to "40rpx")), "m-y-10" to _pS(_uM("marginTop" to "10rpx", "marginBottom" to "10rpx")), "m-y-16" to _pS(_uM("marginTop" to "16rpx", "marginBottom" to "16rpx")), "m-y-20" to _pS(_uM("marginTop" to "20rpx", "marginBottom" to "20rpx")), "m-y-30" to _pS(_uM("marginTop" to "30rpx", "marginBottom" to "30rpx")), "m-y-40" to _pS(_uM("marginTop" to "40rpx", "marginBottom" to "40rpx")), "p-10" to _pS(_uM("paddingTop" to "10rpx", "paddingRight" to "10rpx", "paddingBottom" to "10rpx", "paddingLeft" to "10rpx")), "p-16" to _pS(_uM("paddingTop" to "16rpx", "paddingRight" to "16rpx", "paddingBottom" to "16rpx", "paddingLeft" to "16rpx")), "p-20" to _pS(_uM("paddingTop" to "20rpx", "paddingRight" to "20rpx", "paddingBottom" to "20rpx", "paddingLeft" to "20rpx")), "p-30" to _pS(_uM("paddingTop" to "30rpx", "paddingRight" to "30rpx", "paddingBottom" to "30rpx", "paddingLeft" to "30rpx")), "p-40" to _pS(_uM("paddingTop" to "40rpx", "paddingRight" to "40rpx", "paddingBottom" to "40rpx", "paddingLeft" to "40rpx")), "pt-10" to _pS(_uM("paddingTop" to "10rpx")), "pt-16" to _pS(_uM("paddingTop" to "16rpx")), "pt-20" to _pS(_uM("paddingTop" to "20rpx")), "pt-30" to _pS(_uM("paddingTop" to "30rpx")), "pt-40" to _pS(_uM("paddingTop" to "40rpx")), "pr-10" to _pS(_uM("paddingRight" to "10rpx")))
            }
        val styles1: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("pr-16" to _pS(_uM("paddingRight" to "16rpx")), "pr-20" to _pS(_uM("paddingRight" to "20rpx")), "pr-30" to _pS(_uM("paddingRight" to "30rpx")), "pr-40" to _pS(_uM("paddingRight" to "40rpx")), "pb-10" to _pS(_uM("paddingBottom" to "10rpx")), "pb-16" to _pS(_uM("paddingBottom" to "16rpx")), "pb-20" to _pS(_uM("paddingBottom" to "20rpx")), "pb-30" to _pS(_uM("paddingBottom" to "30rpx")), "pb-40" to _pS(_uM("paddingBottom" to "40rpx")), "pl-10" to _pS(_uM("paddingLeft" to "10rpx")), "pl-16" to _pS(_uM("paddingLeft" to "16rpx")), "pl-20" to _pS(_uM("paddingLeft" to "20rpx")), "pl-30" to _pS(_uM("paddingLeft" to "30rpx")), "pl-40" to _pS(_uM("paddingLeft" to "40rpx")), "p-x-10" to _pS(_uM("paddingLeft" to "10rpx", "paddingRight" to "10rpx")), "p-x-16" to _pS(_uM("paddingLeft" to "16rpx", "paddingRight" to "16rpx")), "p-x-20" to _pS(_uM("paddingLeft" to "20rpx", "paddingRight" to "20rpx")), "p-x-30" to _pS(_uM("paddingLeft" to "30rpx", "paddingRight" to "30rpx")), "p-x-40" to _pS(_uM("paddingLeft" to "40rpx", "paddingRight" to "40rpx")), "p-y-10" to _pS(_uM("paddingTop" to "10rpx", "paddingBottom" to "10rpx")), "p-y-16" to _pS(_uM("paddingTop" to "16rpx", "paddingBottom" to "16rpx")), "p-y-20" to _pS(_uM("paddingTop" to "20rpx", "paddingBottom" to "20rpx")), "p-y-30" to _pS(_uM("paddingTop" to "30rpx", "paddingBottom" to "30rpx")), "p-y-40" to _pS(_uM("paddingTop" to "40rpx", "paddingBottom" to "40rpx")), "br-4" to _pS(_uM("borderTopLeftRadius" to "4rpx", "borderTopRightRadius" to "4rpx", "borderBottomRightRadius" to "4rpx", "borderBottomLeftRadius" to "4rpx")), "br-6" to _pS(_uM("borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx")), "br-8" to _pS(_uM("borderTopLeftRadius" to "8rpx", "borderTopRightRadius" to "8rpx", "borderBottomRightRadius" to "8rpx", "borderBottomLeftRadius" to "8rpx")), "br-10" to _pS(_uM("borderTopLeftRadius" to "10rpx", "borderTopRightRadius" to "10rpx", "borderBottomRightRadius" to "10rpx", "borderBottomLeftRadius" to "10rpx")), "br-16" to _pS(_uM("borderTopLeftRadius" to "16rpx", "borderTopRightRadius" to "16rpx", "borderBottomRightRadius" to "16rpx", "borderBottomLeftRadius" to "16rpx")), "br-20" to _pS(_uM("borderTopLeftRadius" to "20rpx", "borderTopRightRadius" to "20rpx", "borderBottomRightRadius" to "20rpx", "borderBottomLeftRadius" to "20rpx")), "br-30" to _pS(_uM("borderTopLeftRadius" to "30rpx", "borderTopRightRadius" to "30rpx", "borderBottomRightRadius" to "30rpx", "borderBottomLeftRadius" to "30rpx")), "btlr-4" to _pS(_uM("borderTopLeftRadius" to "4rpx")), "btlr-6" to _pS(_uM("borderTopLeftRadius" to "6rpx")), "btlr-8" to _pS(_uM("borderTopLeftRadius" to "8rpx")), "btlr-10" to _pS(_uM("borderTopLeftRadius" to "10rpx")), "btlr-16" to _pS(_uM("borderTopLeftRadius" to "16rpx")), "btlr-20" to _pS(_uM("borderTopLeftRadius" to "20rpx")), "btlr-30" to _pS(_uM("borderTopLeftRadius" to "30rpx")), "btrr-4" to _pS(_uM("borderTopRightRadius" to "4rpx")), "btrr-6" to _pS(_uM("borderTopRightRadius" to "6rpx")), "btrr-8" to _pS(_uM("borderTopRightRadius" to "8rpx")), "btrr-10" to _pS(_uM("borderTopRightRadius" to "10rpx")), "btrr-16" to _pS(_uM("borderTopRightRadius" to "16rpx")), "btrr-20" to _pS(_uM("borderTopRightRadius" to "20rpx")), "btrr-30" to _pS(_uM("borderTopRightRadius" to "30rpx")), "bblr-4" to _pS(_uM("borderBottomLeftRadius" to "4rpx")), "bblr-6" to _pS(_uM("borderBottomLeftRadius" to "6rpx")), "bblr-8" to _pS(_uM("borderBottomLeftRadius" to "8rpx")), "bblr-10" to _pS(_uM("borderBottomLeftRadius" to "10rpx")), "bblr-16" to _pS(_uM("borderBottomLeftRadius" to "16rpx")), "bblr-20" to _pS(_uM("borderBottomLeftRadius" to "20rpx")), "bblr-30" to _pS(_uM("borderBottomLeftRadius" to "30rpx")), "bbrr-4" to _pS(_uM("borderBottomRightRadius" to "4rpx")), "bbrr-6" to _pS(_uM("borderBottomRightRadius" to "6rpx")), "bbrr-8" to _pS(_uM("borderBottomRightRadius" to "8rpx")), "bbrr-10" to _pS(_uM("borderBottomRightRadius" to "10rpx")), "bbrr-16" to _pS(_uM("borderBottomRightRadius" to "16rpx")), "bbrr-20" to _pS(_uM("borderBottomRightRadius" to "20rpx")), "bbrr-30" to _pS(_uM("borderBottomRightRadius" to "30rpx")), "@FONT-FACE" to _uM("0" to _uM("fontFamily" to "iconfont", "src" to "url('/static/iconfont.ttf')"), "1" to _uM("fontFamily" to "AlimamaDaoLiTiTTF", "src" to "url('/static/AlimamaDaoLiTi.ttf')")))
            }
    }
}
val GenAppClass = CreateVueAppComponent(GenApp::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "app", name = "", inheritAttrs = true, inject = Map(), props = Map(), propsNeedCastKeys = _uA(), emits = Map(), components = Map(), styles = GenApp.styles)
}
, fun(instance): GenApp {
    return GenApp(instance)
}
)
val baseUrl: String = "http://192.168.0.163:8000"
val timeOut: Number = 10000
val loginApiUrl = "/api/accounts/auth/login/"
open class RootType (
    @JsonNotNull
    open var success: Boolean = false,
    @JsonNotNull
    open var status: String,
    @JsonNotNull
    open var status_code: Number,
    @JsonNotNull
    open var message: String,
    @JsonNotNull
    open var data: Any,
    @JsonNotNull
    open var timestamp: String,
) : UTSObject()
open class ResponseMeta (
    @JsonNotNull
    open var success: Boolean = false,
    @JsonNotNull
    open var status: String,
    @JsonNotNull
    open var status_code: Number,
    @JsonNotNull
    open var message: String,
    @JsonNotNull
    open var timestamp: String,
) : UTSObject()
var latestResponseMeta: ResponseMeta? = null
fun clearLatestResponseMeta() {
    latestResponseMeta = null
}
fun saveLatestResponseMeta(response: RootType) {
    latestResponseMeta = ResponseMeta(success = response.success, status = response.status, status_code = response.status_code, message = response.message, timestamp = response.timestamp)
}
fun takeLatestResponseMessage(fallback: String = ""): String {
    if (latestResponseMeta == null) {
        return fallback
    }
    val message = if (latestResponseMeta!!.message != "") {
        latestResponseMeta!!.message
    } else {
        fallback
    }
    clearLatestResponseMeta()
    return message
}
fun requestIntercept(reqData: UTSJSONObject): Map<String, UTSJSONObject> {
    val map = Map<String, UTSJSONObject>()
    val header: UTSJSONObject = _uO("content-type" to "application/json")
    if (authState.token != "") {
        header["Authorization"] = authState.token
    }
    var timestamp = Date().getTime().toString(10)
    reqData["timestamp"] = timestamp
    map.set("header", header as UTSJSONObject)
    map.set("data", reqData as UTSJSONObject)
    return map
}
fun shouldHandleUnauthorized(url: String): Boolean {
    if (url == loginApiUrl) {
        return false
    }
    return true
}
fun request(url: String, method: RequestMethod, reqData: UTSJSONObject = _uO(), showLoading: Boolean = false): UTSPromise<Any> {
    return wrapUTSPromise(suspend w@{
            return@w UTSPromise(fun(resolve, reject){
                clearLatestResponseMeta()
                if (showLoading) {
                    uni_showLoading(ShowLoadingOptions(title = "loading"))
                }
                val interceptMap = requestIntercept(reqData)
                console.log("请求地址:", baseUrl + url)
                uni_request<RootType>(RequestOptions(url = baseUrl + url, method = method, header = interceptMap.get("header"), data = interceptMap.get("data"), timeout = timeOut, success = fun(res){
                    if (res.statusCode >= 200 && res.statusCode < 300) {
                        if (res.data != null && res.data!!.success == true) {
                            saveLatestResponseMeta(res.data as RootType)
                            resolve(res.data!!.data)
                            return
                        }
                        if (res.data != null && res.data!!.success == false) {
                            clearLatestResponseMeta()
                            reject(UTSError(res.data?.message ?: "请求失败"))
                            return
                        }
                        clearLatestResponseMeta()
                        resolve(res.data)
                        return
                    }
                    if (res.statusCode == 401) {
                        clearLatestResponseMeta()
                        if (shouldHandleUnauthorized(url)) {
                            redirectToLogin("登录状态已失效，请重新登录")
                        }
                        reject(UTSError("登录状态已失效"))
                        return
                    }
                    clearLatestResponseMeta()
                    reject(UTSError("HTTP状态码错误: " + res.statusCode))
                }
                , fail = fun(err){
                    clearLatestResponseMeta()
                    var message = "网络请求失败"
                    if (err != null) {
                        val errorText = JSON.stringify(err)
                        if (errorText != null && errorText != "") {
                            val parsedError = JSON.parseObject<UTSJSONObject>(errorText)
                            if (parsedError != null) {
                                val errMsg = parsedError!!["errMsg"]
                                if (errMsg != null) {
                                    val parsedErrMsg = errMsg as String
                                    if (parsedErrMsg != "") {
                                        message = parsedErrMsg
                                    }
                                }
                            }
                        }
                        if (message == "网络请求失败") {
                            val fallbackMessage = errorText
                            if (fallbackMessage != null && fallbackMessage != "") {
                                message = fallbackMessage
                            }
                        }
                    }
                    reject(UTSError(message))
                }
                , complete = fun(_){
                    if (showLoading) {
                        uni_hideLoading(null)
                    }
                }
                ))
            }
            )
    })
}
open class LoginData (
    @JsonNotNull
    open var username: String,
    @JsonNotNull
    open var password: String,
) : UTSObject()
open class LoginResponse (
    @JsonNotNull
    open var access_token: String,
    @JsonNotNull
    open var refresh_token: String,
    @JsonNotNull
    open var token_type: String,
    @JsonNotNull
    open var expires_in: Number,
) : UTSObject()
open class ProfileResponse (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var username: String,
    @JsonNotNull
    open var email: String,
    @JsonNotNull
    open var first_name: String,
    @JsonNotNull
    open var last_name: String,
    @JsonNotNull
    open var is_active: Boolean = false,
    @JsonNotNull
    open var date_joined: String,
    @JsonNotNull
    open var is_platform_admin: Boolean = false,
    @JsonNotNull
    open var company_id: Number,
    @JsonNotNull
    open var company_name: String,
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var cashier_account: String,
    open var cashier_id: Number? = null,
    @JsonNotNull
    open var shop_ids: UTSArray<Number>,
    @JsonNotNull
    open var permissions: UTSArray<String>,
) : UTSObject()
fun accountLogin(data: LoginData): UTSPromise<LoginResponse> {
    return wrapUTSPromise(suspend w@{
            val body: UTSJSONObject = _uO("username" to data.username, "password" to data.password)
            val raw = await(request("/api/accounts/auth/login/", "POST", body, true))
            val parsed = JSON.parseObject<LoginResponse>(JSON.stringify(raw))
            if (parsed == null) {
                throw UTSError("登录响应解析失败")
            }
            return@w parsed!!
    })
}
fun getProfile(): UTSPromise<ProfileResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/accounts/auth/me/", "GET", _uO(), false))
            val parsed = JSON.parseObject<ProfileResponse>(JSON.stringify(raw))
            if (parsed == null) {
                throw UTSError("用户信息响应解析失败")
            }
            return@w parsed!!
    })
}
val GenPagesLoginLoginClass = CreateVueComponent(GenPagesLoginLogin::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesLoginLogin.inheritAttrs, inject = GenPagesLoginLogin.inject, props = GenPagesLoginLogin.props, propsNeedCastKeys = GenPagesLoginLogin.propsNeedCastKeys, emits = GenPagesLoginLogin.emits, components = GenPagesLoginLogin.components, styles = GenPagesLoginLogin.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesLoginLogin.setup(props as GenPagesLoginLogin)
    }
    )
}
, fun(instance, renderer): GenPagesLoginLogin {
    return GenPagesLoginLogin(instance, renderer)
}
)
typealias ReportPeriod = String
open class ReportOverview (
    @JsonNotNull
    open var sales_amount: String,
    @JsonNotNull
    open var order_count: Number,
    @JsonNotNull
    open var average_order_value: String,
    @JsonNotNull
    open var purchase_amount: String,
    @JsonNotNull
    open var expense_amount: String,
    @JsonNotNull
    open var arrears_amount: String,
    @JsonNotNull
    open var net_cashflow: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ReportOverviewReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ReportOverviewReactiveObject : ReportOverview, IUTSReactive<ReportOverview> {
    override var __v_raw: ReportOverview
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ReportOverview, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(sales_amount = __v_raw.sales_amount, order_count = __v_raw.order_count, average_order_value = __v_raw.average_order_value, purchase_amount = __v_raw.purchase_amount, expense_amount = __v_raw.expense_amount, arrears_amount = __v_raw.arrears_amount, net_cashflow = __v_raw.net_cashflow) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ReportOverviewReactiveObject {
        return ReportOverviewReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var sales_amount: String
        get() {
            return _tRG(__v_raw, "sales_amount", __v_raw.sales_amount, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("sales_amount")) {
                return
            }
            val oldValue = __v_raw.sales_amount
            __v_raw.sales_amount = value
            _tRS(__v_raw, "sales_amount", oldValue, value)
        }
    override var order_count: Number
        get() {
            return _tRG(__v_raw, "order_count", __v_raw.order_count, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("order_count")) {
                return
            }
            val oldValue = __v_raw.order_count
            __v_raw.order_count = value
            _tRS(__v_raw, "order_count", oldValue, value)
        }
    override var average_order_value: String
        get() {
            return _tRG(__v_raw, "average_order_value", __v_raw.average_order_value, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("average_order_value")) {
                return
            }
            val oldValue = __v_raw.average_order_value
            __v_raw.average_order_value = value
            _tRS(__v_raw, "average_order_value", oldValue, value)
        }
    override var purchase_amount: String
        get() {
            return _tRG(__v_raw, "purchase_amount", __v_raw.purchase_amount, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("purchase_amount")) {
                return
            }
            val oldValue = __v_raw.purchase_amount
            __v_raw.purchase_amount = value
            _tRS(__v_raw, "purchase_amount", oldValue, value)
        }
    override var expense_amount: String
        get() {
            return _tRG(__v_raw, "expense_amount", __v_raw.expense_amount, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("expense_amount")) {
                return
            }
            val oldValue = __v_raw.expense_amount
            __v_raw.expense_amount = value
            _tRS(__v_raw, "expense_amount", oldValue, value)
        }
    override var arrears_amount: String
        get() {
            return _tRG(__v_raw, "arrears_amount", __v_raw.arrears_amount, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("arrears_amount")) {
                return
            }
            val oldValue = __v_raw.arrears_amount
            __v_raw.arrears_amount = value
            _tRS(__v_raw, "arrears_amount", oldValue, value)
        }
    override var net_cashflow: String
        get() {
            return _tRG(__v_raw, "net_cashflow", __v_raw.net_cashflow, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("net_cashflow")) {
                return
            }
            val oldValue = __v_raw.net_cashflow
            __v_raw.net_cashflow = value
            _tRS(__v_raw, "net_cashflow", oldValue, value)
        }
}
open class ReportTrendItem (
    @JsonNotNull
    open var date: String,
    @JsonNotNull
    open var amount: String,
    @JsonNotNull
    open var order_count: Number,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ReportTrendItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ReportTrendItemReactiveObject : ReportTrendItem, IUTSReactive<ReportTrendItem> {
    override var __v_raw: ReportTrendItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ReportTrendItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(date = __v_raw.date, amount = __v_raw.amount, order_count = __v_raw.order_count) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ReportTrendItemReactiveObject {
        return ReportTrendItemReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var date: String
        get() {
            return _tRG(__v_raw, "date", __v_raw.date, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("date")) {
                return
            }
            val oldValue = __v_raw.date
            __v_raw.date = value
            _tRS(__v_raw, "date", oldValue, value)
        }
    override var amount: String
        get() {
            return _tRG(__v_raw, "amount", __v_raw.amount, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("amount")) {
                return
            }
            val oldValue = __v_raw.amount
            __v_raw.amount = value
            _tRS(__v_raw, "amount", oldValue, value)
        }
    override var order_count: Number
        get() {
            return _tRG(__v_raw, "order_count", __v_raw.order_count, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("order_count")) {
                return
            }
            val oldValue = __v_raw.order_count
            __v_raw.order_count = value
            _tRS(__v_raw, "order_count", oldValue, value)
        }
}
open class ReportPaymentMethod (
    @JsonNotNull
    open var key: String,
    @JsonNotNull
    open var label: String,
    @JsonNotNull
    open var amount: String,
    @JsonNotNull
    open var count: Number,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ReportPaymentMethodReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ReportPaymentMethodReactiveObject : ReportPaymentMethod, IUTSReactive<ReportPaymentMethod> {
    override var __v_raw: ReportPaymentMethod
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ReportPaymentMethod, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(key = __v_raw.key, label = __v_raw.label, amount = __v_raw.amount, count = __v_raw.count) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ReportPaymentMethodReactiveObject {
        return ReportPaymentMethodReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var key: String
        get() {
            return _tRG(__v_raw, "key", __v_raw.key, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("key")) {
                return
            }
            val oldValue = __v_raw.key
            __v_raw.key = value
            _tRS(__v_raw, "key", oldValue, value)
        }
    override var label: String
        get() {
            return _tRG(__v_raw, "label", __v_raw.label, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("label")) {
                return
            }
            val oldValue = __v_raw.label
            __v_raw.label = value
            _tRS(__v_raw, "label", oldValue, value)
        }
    override var amount: String
        get() {
            return _tRG(__v_raw, "amount", __v_raw.amount, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("amount")) {
                return
            }
            val oldValue = __v_raw.amount
            __v_raw.amount = value
            _tRS(__v_raw, "amount", oldValue, value)
        }
    override var count: Number
        get() {
            return _tRG(__v_raw, "count", __v_raw.count, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("count")) {
                return
            }
            val oldValue = __v_raw.count
            __v_raw.count = value
            _tRS(__v_raw, "count", oldValue, value)
        }
}
open class ReportInventory (
    @JsonNotNull
    open var stock_item_count: Number,
    @JsonNotNull
    open var total_quantity: Number,
    @JsonNotNull
    open var available_quantity: Number,
    @JsonNotNull
    open var low_stock_count: Number,
    @JsonNotNull
    open var out_of_stock_count: Number,
    @JsonNotNull
    open var no_movement_count: Number,
    @JsonNotNull
    open var inventory_value: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ReportInventoryReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ReportInventoryReactiveObject : ReportInventory, IUTSReactive<ReportInventory> {
    override var __v_raw: ReportInventory
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ReportInventory, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(stock_item_count = __v_raw.stock_item_count, total_quantity = __v_raw.total_quantity, available_quantity = __v_raw.available_quantity, low_stock_count = __v_raw.low_stock_count, out_of_stock_count = __v_raw.out_of_stock_count, no_movement_count = __v_raw.no_movement_count, inventory_value = __v_raw.inventory_value) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ReportInventoryReactiveObject {
        return ReportInventoryReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var stock_item_count: Number
        get() {
            return _tRG(__v_raw, "stock_item_count", __v_raw.stock_item_count, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("stock_item_count")) {
                return
            }
            val oldValue = __v_raw.stock_item_count
            __v_raw.stock_item_count = value
            _tRS(__v_raw, "stock_item_count", oldValue, value)
        }
    override var total_quantity: Number
        get() {
            return _tRG(__v_raw, "total_quantity", __v_raw.total_quantity, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("total_quantity")) {
                return
            }
            val oldValue = __v_raw.total_quantity
            __v_raw.total_quantity = value
            _tRS(__v_raw, "total_quantity", oldValue, value)
        }
    override var available_quantity: Number
        get() {
            return _tRG(__v_raw, "available_quantity", __v_raw.available_quantity, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("available_quantity")) {
                return
            }
            val oldValue = __v_raw.available_quantity
            __v_raw.available_quantity = value
            _tRS(__v_raw, "available_quantity", oldValue, value)
        }
    override var low_stock_count: Number
        get() {
            return _tRG(__v_raw, "low_stock_count", __v_raw.low_stock_count, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("low_stock_count")) {
                return
            }
            val oldValue = __v_raw.low_stock_count
            __v_raw.low_stock_count = value
            _tRS(__v_raw, "low_stock_count", oldValue, value)
        }
    override var out_of_stock_count: Number
        get() {
            return _tRG(__v_raw, "out_of_stock_count", __v_raw.out_of_stock_count, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("out_of_stock_count")) {
                return
            }
            val oldValue = __v_raw.out_of_stock_count
            __v_raw.out_of_stock_count = value
            _tRS(__v_raw, "out_of_stock_count", oldValue, value)
        }
    override var no_movement_count: Number
        get() {
            return _tRG(__v_raw, "no_movement_count", __v_raw.no_movement_count, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("no_movement_count")) {
                return
            }
            val oldValue = __v_raw.no_movement_count
            __v_raw.no_movement_count = value
            _tRS(__v_raw, "no_movement_count", oldValue, value)
        }
    override var inventory_value: String
        get() {
            return _tRG(__v_raw, "inventory_value", __v_raw.inventory_value, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("inventory_value")) {
                return
            }
            val oldValue = __v_raw.inventory_value
            __v_raw.inventory_value = value
            _tRS(__v_raw, "inventory_value", oldValue, value)
        }
}
open class ReportAlert (
    @JsonNotNull
    open var level: String,
    @JsonNotNull
    open var label: String,
    @JsonNotNull
    open var value: Number,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ReportAlertReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ReportAlertReactiveObject : ReportAlert, IUTSReactive<ReportAlert> {
    override var __v_raw: ReportAlert
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ReportAlert, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(level = __v_raw.level, label = __v_raw.label, value = __v_raw.value) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ReportAlertReactiveObject {
        return ReportAlertReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var level: String
        get() {
            return _tRG(__v_raw, "level", __v_raw.level, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("level")) {
                return
            }
            val oldValue = __v_raw.level
            __v_raw.level = value
            _tRS(__v_raw, "level", oldValue, value)
        }
    override var label: String
        get() {
            return _tRG(__v_raw, "label", __v_raw.label, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("label")) {
                return
            }
            val oldValue = __v_raw.label
            __v_raw.label = value
            _tRS(__v_raw, "label", oldValue, value)
        }
    override var value: Number
        get() {
            return _tRG(__v_raw, "value", __v_raw.value, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("value")) {
                return
            }
            val oldValue = __v_raw.value
            __v_raw.value = value
            _tRS(__v_raw, "value", oldValue, value)
        }
}
open class DashboardReport (
    @JsonNotNull
    open var overview: ReportOverview,
    @JsonNotNull
    open var sales_trend: UTSArray<ReportTrendItem>,
    @JsonNotNull
    open var payment_methods: UTSArray<ReportPaymentMethod>,
    @JsonNotNull
    open var order_status: UTSJSONObject,
    @JsonNotNull
    open var inventory: ReportInventory,
    @JsonNotNull
    open var alerts: UTSArray<ReportAlert>,
) : UTSObject()
fun intValue(value: Any?): Number {
    if (value == null) {
        return 0
    }
    val parsed = parseInt("" + value)
    if (isNaN(parsed)) {
        return 0
    }
    return parsed
}
fun stringValue(value: Any?): String {
    if (value == null) {
        return ""
    }
    return "" + value
}
fun objectValue(value: Any?): UTSJSONObject {
    if (value == null) {
        return _uO()
    }
    val text = JSON.stringify(value)
    val parsed = if (text == null || text == "") {
        null
    } else {
        JSON.parseObject<UTSJSONObject>(text)
    }
    return if (parsed == null) {
        (_uO())
    } else {
        parsed!!
    }
}
fun arrayValue(value: Any?): UTSArray<UTSJSONObject> {
    if (value == null) {
        return _uA<UTSJSONObject>()
    }
    val text = JSON.stringify(value)
    val parsed = if (text == null || text == "") {
        null
    } else {
        JSON.parseArray<UTSJSONObject>(text)
    }
    return if (parsed == null) {
        (_uA<UTSJSONObject>())
    } else {
        parsed!!
    }
}
fun rawDataObject(raw: Any): UTSJSONObject {
    val rawText = JSON.stringify(raw)
    val rawObject = if (rawText == null || rawText == "") {
        null
    } else {
        JSON.parseObject<UTSJSONObject>(rawText)
    }
    if (rawObject == null) {
        throw UTSError("报表接口响应解析失败")
    }
    val dataValue = rawObject!!["data"]
    if (dataValue != null) {
        val dataObject = objectValue(dataValue)
        if (dataObject["overview"] != null) {
            return dataObject
        }
    }
    return rawObject!!
}
fun buildOverview(raw: UTSJSONObject): ReportOverview {
    return ReportOverview(sales_amount = stringValue(raw["sales_amount"]), order_count = intValue(raw["order_count"]), average_order_value = stringValue(raw["average_order_value"]), purchase_amount = stringValue(raw["purchase_amount"]), expense_amount = stringValue(raw["expense_amount"]), arrears_amount = stringValue(raw["arrears_amount"]), net_cashflow = stringValue(raw["net_cashflow"]))
}
fun buildTrendItems(value: Any?): UTSArray<ReportTrendItem> {
    val rows = arrayValue(value)
    val result: UTSArray<ReportTrendItem> = _uA()
    run {
        var index: Number = 0
        while(index < rows.length){
            val row = rows[index]
            result.push(ReportTrendItem(date = stringValue(row["date"]), amount = stringValue(row["amount"]), order_count = intValue(row["order_count"])))
            index += 1
        }
    }
    return result
}
fun buildPaymentMethods(value: Any?): UTSArray<ReportPaymentMethod> {
    val rows = arrayValue(value)
    val result: UTSArray<ReportPaymentMethod> = _uA()
    run {
        var index: Number = 0
        while(index < rows.length){
            val row = rows[index]
            result.push(ReportPaymentMethod(key = stringValue(row["key"]), label = stringValue(row["label"]), amount = stringValue(row["amount"]), count = intValue(row["count"])))
            index += 1
        }
    }
    return result
}
fun buildInventory(raw: UTSJSONObject): ReportInventory {
    return ReportInventory(stock_item_count = intValue(raw["stock_item_count"]), total_quantity = intValue(raw["total_quantity"]), available_quantity = intValue(raw["available_quantity"]), low_stock_count = intValue(raw["low_stock_count"]), out_of_stock_count = intValue(raw["out_of_stock_count"]), no_movement_count = intValue(raw["no_movement_count"]), inventory_value = stringValue(raw["inventory_value"]))
}
fun buildAlerts(value: Any?): UTSArray<ReportAlert> {
    val rows = arrayValue(value)
    val result: UTSArray<ReportAlert> = _uA()
    run {
        var index: Number = 0
        while(index < rows.length){
            val row = rows[index]
            result.push(ReportAlert(level = stringValue(row["level"]), label = stringValue(row["label"]), value = intValue(row["value"])))
            index += 1
        }
    }
    return result
}
fun buildDashboardReport(raw: Any): DashboardReport {
    val rawObject = rawDataObject(raw)
    return DashboardReport(overview = buildOverview(objectValue(rawObject["overview"])), sales_trend = buildTrendItems(rawObject["sales_trend"]), payment_methods = buildPaymentMethods(rawObject["payment_methods"]), order_status = objectValue(rawObject["order_status"]), inventory = buildInventory(objectValue(rawObject["inventory"])), alerts = buildAlerts(rawObject["alerts"]))
}
fun getDashboardReport(period: ReportPeriod): UTSPromise<DashboardReport> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/reports/dashboard/", "GET", _uO("period" to period), true))
            return@w buildDashboardReport(raw)
    })
}
open class PeriodOption (
    @JsonNotNull
    open var key: ReportPeriod,
    @JsonNotNull
    open var label: String,
) : UTSObject()
val GenPagesTabbarReportsClass = CreateVueComponent(GenPagesTabbarReports::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesTabbarReports.inheritAttrs, inject = GenPagesTabbarReports.inject, props = GenPagesTabbarReports.props, propsNeedCastKeys = GenPagesTabbarReports.propsNeedCastKeys, emits = GenPagesTabbarReports.emits, components = GenPagesTabbarReports.components, styles = GenPagesTabbarReports.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesTabbarReports.setup(props as GenPagesTabbarReports)
    }
    )
}
, fun(instance, renderer): GenPagesTabbarReports {
    return GenPagesTabbarReports(instance, renderer)
}
)
open class ProductListQuery (
    open var search: String? = null,
    @JsonNotNull
    open var page: Number,
    @JsonNotNull
    open var page_size: Number,
    @JsonNotNull
    open var filters: UTSArray<ProductSelectedFilter>,
) : UTSObject()
open class ProductSelectedFilter (
    @JsonNotNull
    open var param: String,
    @JsonNotNull
    open var value: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ProductSelectedFilterReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ProductSelectedFilterReactiveObject : ProductSelectedFilter, IUTSReactive<ProductSelectedFilter> {
    override var __v_raw: ProductSelectedFilter
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ProductSelectedFilter, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(param = __v_raw.param, value = __v_raw.value) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ProductSelectedFilterReactiveObject {
        return ProductSelectedFilterReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var param: String
        get() {
            return _tRG(__v_raw, "param", __v_raw.param, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("param")) {
                return
            }
            val oldValue = __v_raw.param
            __v_raw.param = value
            _tRS(__v_raw, "param", oldValue, value)
        }
    override var value: String
        get() {
            return _tRG(__v_raw, "value", __v_raw.value, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("value")) {
                return
            }
            val oldValue = __v_raw.value
            __v_raw.value = value
            _tRS(__v_raw, "value", oldValue, value)
        }
}
open class ProductMediaFile (
    @JsonNotNull
    open var id: String,
    @JsonNotNull
    open var company: Number,
    @JsonNotNull
    open var original_filename: String,
    @JsonNotNull
    open var file_type: String,
    @JsonNotNull
    open var file_type_display: String,
    @JsonNotNull
    open var mime_type: String,
    @JsonNotNull
    open var file_size: Number,
    @JsonNotNull
    open var file_size_display: String,
    @JsonNotNull
    open var file_url: String,
    @JsonNotNull
    open var thumbnail_url: String,
    @JsonNotNull
    open var signed_url: String,
    @JsonNotNull
    open var signed_thumbnail_url: String,
    @JsonNotNull
    open var object_id: String,
    @JsonNotNull
    open var is_deleted: Boolean = false,
    @JsonNotNull
    open var created_at: String,
    @JsonNotNull
    open var updated_at: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ProductMediaFileReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ProductMediaFileReactiveObject : ProductMediaFile, IUTSReactive<ProductMediaFile> {
    override var __v_raw: ProductMediaFile
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ProductMediaFile, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, company = __v_raw.company, original_filename = __v_raw.original_filename, file_type = __v_raw.file_type, file_type_display = __v_raw.file_type_display, mime_type = __v_raw.mime_type, file_size = __v_raw.file_size, file_size_display = __v_raw.file_size_display, file_url = __v_raw.file_url, thumbnail_url = __v_raw.thumbnail_url, signed_url = __v_raw.signed_url, signed_thumbnail_url = __v_raw.signed_thumbnail_url, object_id = __v_raw.object_id, is_deleted = __v_raw.is_deleted, created_at = __v_raw.created_at, updated_at = __v_raw.updated_at) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ProductMediaFileReactiveObject {
        return ProductMediaFileReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: String
        get() {
            return _tRG(__v_raw, "id", __v_raw.id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            _tRS(__v_raw, "id", oldValue, value)
        }
    override var company: Number
        get() {
            return _tRG(__v_raw, "company", __v_raw.company, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("company")) {
                return
            }
            val oldValue = __v_raw.company
            __v_raw.company = value
            _tRS(__v_raw, "company", oldValue, value)
        }
    override var original_filename: String
        get() {
            return _tRG(__v_raw, "original_filename", __v_raw.original_filename, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("original_filename")) {
                return
            }
            val oldValue = __v_raw.original_filename
            __v_raw.original_filename = value
            _tRS(__v_raw, "original_filename", oldValue, value)
        }
    override var file_type: String
        get() {
            return _tRG(__v_raw, "file_type", __v_raw.file_type, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("file_type")) {
                return
            }
            val oldValue = __v_raw.file_type
            __v_raw.file_type = value
            _tRS(__v_raw, "file_type", oldValue, value)
        }
    override var file_type_display: String
        get() {
            return _tRG(__v_raw, "file_type_display", __v_raw.file_type_display, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("file_type_display")) {
                return
            }
            val oldValue = __v_raw.file_type_display
            __v_raw.file_type_display = value
            _tRS(__v_raw, "file_type_display", oldValue, value)
        }
    override var mime_type: String
        get() {
            return _tRG(__v_raw, "mime_type", __v_raw.mime_type, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("mime_type")) {
                return
            }
            val oldValue = __v_raw.mime_type
            __v_raw.mime_type = value
            _tRS(__v_raw, "mime_type", oldValue, value)
        }
    override var file_size: Number
        get() {
            return _tRG(__v_raw, "file_size", __v_raw.file_size, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("file_size")) {
                return
            }
            val oldValue = __v_raw.file_size
            __v_raw.file_size = value
            _tRS(__v_raw, "file_size", oldValue, value)
        }
    override var file_size_display: String
        get() {
            return _tRG(__v_raw, "file_size_display", __v_raw.file_size_display, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("file_size_display")) {
                return
            }
            val oldValue = __v_raw.file_size_display
            __v_raw.file_size_display = value
            _tRS(__v_raw, "file_size_display", oldValue, value)
        }
    override var file_url: String
        get() {
            return _tRG(__v_raw, "file_url", __v_raw.file_url, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("file_url")) {
                return
            }
            val oldValue = __v_raw.file_url
            __v_raw.file_url = value
            _tRS(__v_raw, "file_url", oldValue, value)
        }
    override var thumbnail_url: String
        get() {
            return _tRG(__v_raw, "thumbnail_url", __v_raw.thumbnail_url, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("thumbnail_url")) {
                return
            }
            val oldValue = __v_raw.thumbnail_url
            __v_raw.thumbnail_url = value
            _tRS(__v_raw, "thumbnail_url", oldValue, value)
        }
    override var signed_url: String
        get() {
            return _tRG(__v_raw, "signed_url", __v_raw.signed_url, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("signed_url")) {
                return
            }
            val oldValue = __v_raw.signed_url
            __v_raw.signed_url = value
            _tRS(__v_raw, "signed_url", oldValue, value)
        }
    override var signed_thumbnail_url: String
        get() {
            return _tRG(__v_raw, "signed_thumbnail_url", __v_raw.signed_thumbnail_url, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("signed_thumbnail_url")) {
                return
            }
            val oldValue = __v_raw.signed_thumbnail_url
            __v_raw.signed_thumbnail_url = value
            _tRS(__v_raw, "signed_thumbnail_url", oldValue, value)
        }
    override var object_id: String
        get() {
            return _tRG(__v_raw, "object_id", __v_raw.object_id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("object_id")) {
                return
            }
            val oldValue = __v_raw.object_id
            __v_raw.object_id = value
            _tRS(__v_raw, "object_id", oldValue, value)
        }
    override var is_deleted: Boolean
        get() {
            return _tRG(__v_raw, "is_deleted", __v_raw.is_deleted, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("is_deleted")) {
                return
            }
            val oldValue = __v_raw.is_deleted
            __v_raw.is_deleted = value
            _tRS(__v_raw, "is_deleted", oldValue, value)
        }
    override var created_at: String
        get() {
            return _tRG(__v_raw, "created_at", __v_raw.created_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("created_at")) {
                return
            }
            val oldValue = __v_raw.created_at
            __v_raw.created_at = value
            _tRS(__v_raw, "created_at", oldValue, value)
        }
    override var updated_at: String
        get() {
            return _tRG(__v_raw, "updated_at", __v_raw.updated_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("updated_at")) {
                return
            }
            val oldValue = __v_raw.updated_at
            __v_raw.updated_at = value
            _tRS(__v_raw, "updated_at", oldValue, value)
        }
}
open class ProductItem (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var sku: String,
    @JsonNotNull
    open var barcode: String,
    @JsonNotNull
    open var name_cn: String,
    @JsonNotNull
    open var name_en: String,
    @JsonNotNull
    open var name_other: String,
    @JsonNotNull
    open var description: String,
    @JsonNotNull
    open var media_files: UTSArray<ProductMediaFile>,
    open var category: Any? = null,
    open var supplier: Number? = null,
    @JsonNotNull
    open var supplier_name: String,
    @JsonNotNull
    open var purchase_price: String,
    @JsonNotNull
    open var net_purchase_price: String,
    @JsonNotNull
    open var cost_price: String,
    @JsonNotNull
    open var base_sales_price: String,
    @JsonNotNull
    open var status: String,
    @JsonNotNull
    open var is_featured: Boolean = false,
    @JsonNotNull
    open var is_new: Boolean = false,
    @JsonNotNull
    open var is_bestseller: Boolean = false,
    @JsonNotNull
    open var sort_order: Number,
    @JsonNotNull
    open var rating: String,
    @JsonNotNull
    open var variant_count: Number,
    @JsonNotNull
    open var total_sales_quantity: Number,
    @JsonNotNull
    open var total_sales_amount: String,
    open var last_sale_date: String? = null,
    @JsonNotNull
    open var created_at: String,
    @JsonNotNull
    open var updated_at: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ProductItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ProductItemReactiveObject : ProductItem, IUTSReactive<ProductItem> {
    override var __v_raw: ProductItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ProductItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, sku = __v_raw.sku, barcode = __v_raw.barcode, name_cn = __v_raw.name_cn, name_en = __v_raw.name_en, name_other = __v_raw.name_other, description = __v_raw.description, media_files = __v_raw.media_files, category = __v_raw.category, supplier = __v_raw.supplier, supplier_name = __v_raw.supplier_name, purchase_price = __v_raw.purchase_price, net_purchase_price = __v_raw.net_purchase_price, cost_price = __v_raw.cost_price, base_sales_price = __v_raw.base_sales_price, status = __v_raw.status, is_featured = __v_raw.is_featured, is_new = __v_raw.is_new, is_bestseller = __v_raw.is_bestseller, sort_order = __v_raw.sort_order, rating = __v_raw.rating, variant_count = __v_raw.variant_count, total_sales_quantity = __v_raw.total_sales_quantity, total_sales_amount = __v_raw.total_sales_amount, last_sale_date = __v_raw.last_sale_date, created_at = __v_raw.created_at, updated_at = __v_raw.updated_at) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ProductItemReactiveObject {
        return ProductItemReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return _tRG(__v_raw, "id", __v_raw.id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            _tRS(__v_raw, "id", oldValue, value)
        }
    override var sku: String
        get() {
            return _tRG(__v_raw, "sku", __v_raw.sku, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("sku")) {
                return
            }
            val oldValue = __v_raw.sku
            __v_raw.sku = value
            _tRS(__v_raw, "sku", oldValue, value)
        }
    override var barcode: String
        get() {
            return _tRG(__v_raw, "barcode", __v_raw.barcode, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("barcode")) {
                return
            }
            val oldValue = __v_raw.barcode
            __v_raw.barcode = value
            _tRS(__v_raw, "barcode", oldValue, value)
        }
    override var name_cn: String
        get() {
            return _tRG(__v_raw, "name_cn", __v_raw.name_cn, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("name_cn")) {
                return
            }
            val oldValue = __v_raw.name_cn
            __v_raw.name_cn = value
            _tRS(__v_raw, "name_cn", oldValue, value)
        }
    override var name_en: String
        get() {
            return _tRG(__v_raw, "name_en", __v_raw.name_en, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("name_en")) {
                return
            }
            val oldValue = __v_raw.name_en
            __v_raw.name_en = value
            _tRS(__v_raw, "name_en", oldValue, value)
        }
    override var name_other: String
        get() {
            return _tRG(__v_raw, "name_other", __v_raw.name_other, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("name_other")) {
                return
            }
            val oldValue = __v_raw.name_other
            __v_raw.name_other = value
            _tRS(__v_raw, "name_other", oldValue, value)
        }
    override var description: String
        get() {
            return _tRG(__v_raw, "description", __v_raw.description, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("description")) {
                return
            }
            val oldValue = __v_raw.description
            __v_raw.description = value
            _tRS(__v_raw, "description", oldValue, value)
        }
    override var media_files: UTSArray<ProductMediaFile>
        get() {
            return _tRG(__v_raw, "media_files", __v_raw.media_files, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("media_files")) {
                return
            }
            val oldValue = __v_raw.media_files
            __v_raw.media_files = value
            _tRS(__v_raw, "media_files", oldValue, value)
        }
    override var category: Any?
        get() {
            return _tRG(__v_raw, "category", __v_raw.category, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("category")) {
                return
            }
            val oldValue = __v_raw.category
            __v_raw.category = value
            _tRS(__v_raw, "category", oldValue, value)
        }
    override var supplier: Number?
        get() {
            return _tRG(__v_raw, "supplier", __v_raw.supplier, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("supplier")) {
                return
            }
            val oldValue = __v_raw.supplier
            __v_raw.supplier = value
            _tRS(__v_raw, "supplier", oldValue, value)
        }
    override var supplier_name: String
        get() {
            return _tRG(__v_raw, "supplier_name", __v_raw.supplier_name, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("supplier_name")) {
                return
            }
            val oldValue = __v_raw.supplier_name
            __v_raw.supplier_name = value
            _tRS(__v_raw, "supplier_name", oldValue, value)
        }
    override var purchase_price: String
        get() {
            return _tRG(__v_raw, "purchase_price", __v_raw.purchase_price, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("purchase_price")) {
                return
            }
            val oldValue = __v_raw.purchase_price
            __v_raw.purchase_price = value
            _tRS(__v_raw, "purchase_price", oldValue, value)
        }
    override var net_purchase_price: String
        get() {
            return _tRG(__v_raw, "net_purchase_price", __v_raw.net_purchase_price, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("net_purchase_price")) {
                return
            }
            val oldValue = __v_raw.net_purchase_price
            __v_raw.net_purchase_price = value
            _tRS(__v_raw, "net_purchase_price", oldValue, value)
        }
    override var cost_price: String
        get() {
            return _tRG(__v_raw, "cost_price", __v_raw.cost_price, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("cost_price")) {
                return
            }
            val oldValue = __v_raw.cost_price
            __v_raw.cost_price = value
            _tRS(__v_raw, "cost_price", oldValue, value)
        }
    override var base_sales_price: String
        get() {
            return _tRG(__v_raw, "base_sales_price", __v_raw.base_sales_price, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("base_sales_price")) {
                return
            }
            val oldValue = __v_raw.base_sales_price
            __v_raw.base_sales_price = value
            _tRS(__v_raw, "base_sales_price", oldValue, value)
        }
    override var status: String
        get() {
            return _tRG(__v_raw, "status", __v_raw.status, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("status")) {
                return
            }
            val oldValue = __v_raw.status
            __v_raw.status = value
            _tRS(__v_raw, "status", oldValue, value)
        }
    override var is_featured: Boolean
        get() {
            return _tRG(__v_raw, "is_featured", __v_raw.is_featured, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("is_featured")) {
                return
            }
            val oldValue = __v_raw.is_featured
            __v_raw.is_featured = value
            _tRS(__v_raw, "is_featured", oldValue, value)
        }
    override var is_new: Boolean
        get() {
            return _tRG(__v_raw, "is_new", __v_raw.is_new, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("is_new")) {
                return
            }
            val oldValue = __v_raw.is_new
            __v_raw.is_new = value
            _tRS(__v_raw, "is_new", oldValue, value)
        }
    override var is_bestseller: Boolean
        get() {
            return _tRG(__v_raw, "is_bestseller", __v_raw.is_bestseller, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("is_bestseller")) {
                return
            }
            val oldValue = __v_raw.is_bestseller
            __v_raw.is_bestseller = value
            _tRS(__v_raw, "is_bestseller", oldValue, value)
        }
    override var sort_order: Number
        get() {
            return _tRG(__v_raw, "sort_order", __v_raw.sort_order, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("sort_order")) {
                return
            }
            val oldValue = __v_raw.sort_order
            __v_raw.sort_order = value
            _tRS(__v_raw, "sort_order", oldValue, value)
        }
    override var rating: String
        get() {
            return _tRG(__v_raw, "rating", __v_raw.rating, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("rating")) {
                return
            }
            val oldValue = __v_raw.rating
            __v_raw.rating = value
            _tRS(__v_raw, "rating", oldValue, value)
        }
    override var variant_count: Number
        get() {
            return _tRG(__v_raw, "variant_count", __v_raw.variant_count, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("variant_count")) {
                return
            }
            val oldValue = __v_raw.variant_count
            __v_raw.variant_count = value
            _tRS(__v_raw, "variant_count", oldValue, value)
        }
    override var total_sales_quantity: Number
        get() {
            return _tRG(__v_raw, "total_sales_quantity", __v_raw.total_sales_quantity, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("total_sales_quantity")) {
                return
            }
            val oldValue = __v_raw.total_sales_quantity
            __v_raw.total_sales_quantity = value
            _tRS(__v_raw, "total_sales_quantity", oldValue, value)
        }
    override var total_sales_amount: String
        get() {
            return _tRG(__v_raw, "total_sales_amount", __v_raw.total_sales_amount, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("total_sales_amount")) {
                return
            }
            val oldValue = __v_raw.total_sales_amount
            __v_raw.total_sales_amount = value
            _tRS(__v_raw, "total_sales_amount", oldValue, value)
        }
    override var last_sale_date: String?
        get() {
            return _tRG(__v_raw, "last_sale_date", __v_raw.last_sale_date, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("last_sale_date")) {
                return
            }
            val oldValue = __v_raw.last_sale_date
            __v_raw.last_sale_date = value
            _tRS(__v_raw, "last_sale_date", oldValue, value)
        }
    override var created_at: String
        get() {
            return _tRG(__v_raw, "created_at", __v_raw.created_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("created_at")) {
                return
            }
            val oldValue = __v_raw.created_at
            __v_raw.created_at = value
            _tRS(__v_raw, "created_at", oldValue, value)
        }
    override var updated_at: String
        get() {
            return _tRG(__v_raw, "updated_at", __v_raw.updated_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("updated_at")) {
                return
            }
            val oldValue = __v_raw.updated_at
            __v_raw.updated_at = value
            _tRS(__v_raw, "updated_at", oldValue, value)
        }
}
open class ProductMutationData (
    open var sku: String? = null,
    open var barcode: String? = null,
    @JsonNotNull
    open var name_cn: String,
    open var name_en: String? = null,
    open var name_other: String? = null,
    open var description: String? = null,
    open var category: String? = null,
    open var supplier: String? = null,
    @JsonNotNull
    open var purchase_price: String,
    @JsonNotNull
    open var net_purchase_price: String,
    @JsonNotNull
    open var cost_price: String,
    @JsonNotNull
    open var base_sales_price: String,
    @JsonNotNull
    open var status: String,
    @JsonNotNull
    open var is_featured: Boolean = false,
    @JsonNotNull
    open var is_new: Boolean = false,
    @JsonNotNull
    open var is_bestseller: Boolean = false,
    @JsonNotNull
    open var sort_order: Number,
) : UTSObject()
open class ProductListResponse (
    @JsonNotNull
    open var results: UTSArray<ProductItem>,
    @JsonNotNull
    open var count: Number,
    @JsonNotNull
    open var total_count: Number,
    @JsonNotNull
    open var total_pages: Number,
    @JsonNotNull
    open var current_page: Number,
    @JsonNotNull
    open var page_size: Number,
) : UTSObject()
open class ProductFilterOption (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var label: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ProductFilterOptionReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ProductFilterOptionReactiveObject : ProductFilterOption, IUTSReactive<ProductFilterOption> {
    override var __v_raw: ProductFilterOption
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ProductFilterOption, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(value = __v_raw.value, label = __v_raw.label) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ProductFilterOptionReactiveObject {
        return ProductFilterOptionReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var value: String
        get() {
            return _tRG(__v_raw, "value", __v_raw.value, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("value")) {
                return
            }
            val oldValue = __v_raw.value
            __v_raw.value = value
            _tRS(__v_raw, "value", oldValue, value)
        }
    override var label: String
        get() {
            return _tRG(__v_raw, "label", __v_raw.label, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("label")) {
                return
            }
            val oldValue = __v_raw.label
            __v_raw.label = value
            _tRS(__v_raw, "label", oldValue, value)
        }
}
open class ProductFilterDefinition (
    @JsonNotNull
    open var key: String,
    @JsonNotNull
    open var param: String,
    @JsonNotNull
    open var label: String,
    @JsonNotNull
    open var control: String,
    @JsonNotNull
    open var aliases: UTSArray<String>,
    @JsonNotNull
    open var multiple: Boolean = false,
    @JsonNotNull
    open var options: UTSArray<ProductFilterOption>,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ProductFilterDefinitionReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ProductFilterDefinitionReactiveObject : ProductFilterDefinition, IUTSReactive<ProductFilterDefinition> {
    override var __v_raw: ProductFilterDefinition
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ProductFilterDefinition, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(key = __v_raw.key, param = __v_raw.param, label = __v_raw.label, control = __v_raw.control, aliases = __v_raw.aliases, multiple = __v_raw.multiple, options = __v_raw.options) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ProductFilterDefinitionReactiveObject {
        return ProductFilterDefinitionReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var key: String
        get() {
            return _tRG(__v_raw, "key", __v_raw.key, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("key")) {
                return
            }
            val oldValue = __v_raw.key
            __v_raw.key = value
            _tRS(__v_raw, "key", oldValue, value)
        }
    override var param: String
        get() {
            return _tRG(__v_raw, "param", __v_raw.param, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("param")) {
                return
            }
            val oldValue = __v_raw.param
            __v_raw.param = value
            _tRS(__v_raw, "param", oldValue, value)
        }
    override var label: String
        get() {
            return _tRG(__v_raw, "label", __v_raw.label, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("label")) {
                return
            }
            val oldValue = __v_raw.label
            __v_raw.label = value
            _tRS(__v_raw, "label", oldValue, value)
        }
    override var control: String
        get() {
            return _tRG(__v_raw, "control", __v_raw.control, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("control")) {
                return
            }
            val oldValue = __v_raw.control
            __v_raw.control = value
            _tRS(__v_raw, "control", oldValue, value)
        }
    override var aliases: UTSArray<String>
        get() {
            return _tRG(__v_raw, "aliases", __v_raw.aliases, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("aliases")) {
                return
            }
            val oldValue = __v_raw.aliases
            __v_raw.aliases = value
            _tRS(__v_raw, "aliases", oldValue, value)
        }
    override var multiple: Boolean
        get() {
            return _tRG(__v_raw, "multiple", __v_raw.multiple, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("multiple")) {
                return
            }
            val oldValue = __v_raw.multiple
            __v_raw.multiple = value
            _tRS(__v_raw, "multiple", oldValue, value)
        }
    override var options: UTSArray<ProductFilterOption>
        get() {
            return _tRG(__v_raw, "options", __v_raw.options, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("options")) {
                return
            }
            val oldValue = __v_raw.options
            __v_raw.options = value
            _tRS(__v_raw, "options", oldValue, value)
        }
}
open class ProductFilterOptionsResponse (
    @JsonNotNull
    open var resource: String,
    @JsonNotNull
    open var count: Number,
    @JsonNotNull
    open var filters: UTSArray<ProductFilterDefinition>,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ProductFilterOptionsResponseReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ProductFilterOptionsResponseReactiveObject : ProductFilterOptionsResponse, IUTSReactive<ProductFilterOptionsResponse> {
    override var __v_raw: ProductFilterOptionsResponse
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ProductFilterOptionsResponse, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(resource = __v_raw.resource, count = __v_raw.count, filters = __v_raw.filters) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ProductFilterOptionsResponseReactiveObject {
        return ProductFilterOptionsResponseReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var resource: String
        get() {
            return _tRG(__v_raw, "resource", __v_raw.resource, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("resource")) {
                return
            }
            val oldValue = __v_raw.resource
            __v_raw.resource = value
            _tRS(__v_raw, "resource", oldValue, value)
        }
    override var count: Number
        get() {
            return _tRG(__v_raw, "count", __v_raw.count, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("count")) {
                return
            }
            val oldValue = __v_raw.count
            __v_raw.count = value
            _tRS(__v_raw, "count", oldValue, value)
        }
    override var filters: UTSArray<ProductFilterDefinition>
        get() {
            return _tRG(__v_raw, "filters", __v_raw.filters, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("filters")) {
                return
            }
            val oldValue = __v_raw.filters
            __v_raw.filters = value
            _tRS(__v_raw, "filters", oldValue, value)
        }
}
open class ProductPricingFormulaItem (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var company: Number,
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var code: String,
    @JsonNotNull
    open var expression: String,
    @JsonNotNull
    open var description: String,
    @JsonNotNull
    open var is_active: Boolean = false,
    @JsonNotNull
    open var supported_functions: UTSArray<String>,
    @JsonNotNull
    open var created_at: String,
    @JsonNotNull
    open var updated_at: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ProductPricingFormulaItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ProductPricingFormulaItemReactiveObject : ProductPricingFormulaItem, IUTSReactive<ProductPricingFormulaItem> {
    override var __v_raw: ProductPricingFormulaItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ProductPricingFormulaItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, company = __v_raw.company, name = __v_raw.name, code = __v_raw.code, expression = __v_raw.expression, description = __v_raw.description, is_active = __v_raw.is_active, supported_functions = __v_raw.supported_functions, created_at = __v_raw.created_at, updated_at = __v_raw.updated_at) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ProductPricingFormulaItemReactiveObject {
        return ProductPricingFormulaItemReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return _tRG(__v_raw, "id", __v_raw.id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            _tRS(__v_raw, "id", oldValue, value)
        }
    override var company: Number
        get() {
            return _tRG(__v_raw, "company", __v_raw.company, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("company")) {
                return
            }
            val oldValue = __v_raw.company
            __v_raw.company = value
            _tRS(__v_raw, "company", oldValue, value)
        }
    override var name: String
        get() {
            return _tRG(__v_raw, "name", __v_raw.name, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("name")) {
                return
            }
            val oldValue = __v_raw.name
            __v_raw.name = value
            _tRS(__v_raw, "name", oldValue, value)
        }
    override var code: String
        get() {
            return _tRG(__v_raw, "code", __v_raw.code, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("code")) {
                return
            }
            val oldValue = __v_raw.code
            __v_raw.code = value
            _tRS(__v_raw, "code", oldValue, value)
        }
    override var expression: String
        get() {
            return _tRG(__v_raw, "expression", __v_raw.expression, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("expression")) {
                return
            }
            val oldValue = __v_raw.expression
            __v_raw.expression = value
            _tRS(__v_raw, "expression", oldValue, value)
        }
    override var description: String
        get() {
            return _tRG(__v_raw, "description", __v_raw.description, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("description")) {
                return
            }
            val oldValue = __v_raw.description
            __v_raw.description = value
            _tRS(__v_raw, "description", oldValue, value)
        }
    override var is_active: Boolean
        get() {
            return _tRG(__v_raw, "is_active", __v_raw.is_active, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("is_active")) {
                return
            }
            val oldValue = __v_raw.is_active
            __v_raw.is_active = value
            _tRS(__v_raw, "is_active", oldValue, value)
        }
    override var supported_functions: UTSArray<String>
        get() {
            return _tRG(__v_raw, "supported_functions", __v_raw.supported_functions, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("supported_functions")) {
                return
            }
            val oldValue = __v_raw.supported_functions
            __v_raw.supported_functions = value
            _tRS(__v_raw, "supported_functions", oldValue, value)
        }
    override var created_at: String
        get() {
            return _tRG(__v_raw, "created_at", __v_raw.created_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("created_at")) {
                return
            }
            val oldValue = __v_raw.created_at
            __v_raw.created_at = value
            _tRS(__v_raw, "created_at", oldValue, value)
        }
    override var updated_at: String
        get() {
            return _tRG(__v_raw, "updated_at", __v_raw.updated_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("updated_at")) {
                return
            }
            val oldValue = __v_raw.updated_at
            __v_raw.updated_at = value
            _tRS(__v_raw, "updated_at", oldValue, value)
        }
}
open class ProductPricingFormulaListQuery (
    open var search: String? = null,
    @JsonNotNull
    open var page: Number,
    @JsonNotNull
    open var page_size: Number,
) : UTSObject()
open class ProductPricingFormulaListResponse (
    @JsonNotNull
    open var results: UTSArray<ProductPricingFormulaItem>,
    @JsonNotNull
    open var count: Number,
    @JsonNotNull
    open var total_count: Number,
    @JsonNotNull
    open var total_pages: Number,
    @JsonNotNull
    open var current_page: Number,
    @JsonNotNull
    open var page_size: Number,
) : UTSObject()
open class ProductPricingFormulaMutationData (
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var code: String,
    @JsonNotNull
    open var expression: String,
    @JsonNotNull
    open var description: String,
    @JsonNotNull
    open var is_active: Boolean = false,
) : UTSObject()
val productsBasePath = "/api/products/products/"
val productPricingFormulasBasePath = "/api/products/pricing-formulas/"
val productDiscountsBasePath = "/api/products/discounts/"
val attributeTypesBasePath = "/api/products/attribute-types/"
val attributeValuesBasePath = "/api/products/attribute-values/"
val barcodeSequencesBasePath = "/api/products/barcode-sequences/"
fun buildListQuery(data: ProductListQuery): UTSJSONObject {
    val query: UTSJSONObject = _uO("page" to data.page, "page_size" to data.page_size)
    if (data.search != null && data.search != "") {
        query["search"] = data.search
    }
    run {
        var filterIndex: Number = 0
        while(filterIndex < data.filters.length){
            val filter = data.filters[filterIndex]
            if (filter.param != "" && filter.value != "") {
                query[filter.param] = filter.value
            }
            filterIndex += 1
        }
    }
    return query
}
fun buildPricingFormulaListQuery(data: ProductPricingFormulaListQuery): UTSJSONObject {
    val query: UTSJSONObject = _uO("page" to data.page, "page_size" to data.page_size)
    if (data.search != null && data.search != "") {
        query["search"] = data.search
    }
    return query
}
fun normalizeServerUrl(url: String): String {
    if (url == "") {
        return ""
    }
    if (url.startsWith("http://localhost:8000")) {
        return baseUrl + url.substring(21)
    }
    if (url.startsWith("https://localhost:8000")) {
        return baseUrl + url.substring(22)
    }
    if (url.startsWith("http://127.0.0.1:8000")) {
        return baseUrl + url.substring(21)
    }
    if (url.startsWith("https://127.0.0.1:8000")) {
        return baseUrl + url.substring(22)
    }
    return url
}
fun normalizeProductMediaFiles(files: UTSArray<ProductMediaFile>) {
    run {
        var mediaIndex: Number = 0
        while(mediaIndex < files.length){
            val mediaFile = files[mediaIndex]
            mediaFile.file_url = normalizeServerUrl(mediaFile.file_url)
            mediaFile.thumbnail_url = normalizeServerUrl(mediaFile.thumbnail_url)
            mediaFile.signed_url = normalizeServerUrl(mediaFile.signed_url)
            mediaFile.signed_thumbnail_url = normalizeServerUrl(mediaFile.signed_thumbnail_url)
            mediaIndex += 1
        }
    }
}
fun normalizeProductItem(item: ProductItem): ProductItem {
    normalizeProductMediaFiles(item.media_files)
    return item
}
fun normalizeProductList(data: ProductListResponse): ProductListResponse {
    run {
        var productIndex: Number = 0
        while(productIndex < data.results.length){
            data.results[productIndex] = normalizeProductItem(data.results[productIndex])
            productIndex += 1
        }
    }
    return data
}
fun parseObject(value: Any?): UTSJSONObject? {
    if (value == null) {
        return null
    }
    val text = JSON.stringify(value)
    if (text == null || text == "") {
        return null
    }
    return JSON.parseObject<UTSJSONObject>(text)
}
fun parseObjectArray(value: Any?): UTSArray<UTSJSONObject> {
    if (value == null) {
        return _uA<UTSJSONObject>()
    }
    val text = JSON.stringify(value)
    if (text == null || text == "") {
        return _uA<UTSJSONObject>()
    }
    val parsed = JSON.parseArray<UTSJSONObject>(text)
    if (parsed == null) {
        return _uA<UTSJSONObject>()
    }
    return parsed!!
}
fun intValue__1(value: Any?): Number {
    if (value == null) {
        return 0
    }
    val text = "" + value
    if (text == "") {
        return 0
    }
    val parsed = parseInt(text)
    if (isNaN(parsed)) {
        return 0
    }
    return parsed
}
fun stringValue__1(value: Any?): String {
    if (value == null) {
        return ""
    }
    return "" + value
}
fun booleanValue(value: Any?): Boolean {
    val text = stringValue__1(value).toLowerCase()
    return text == "true" || text == "1" || text == "yes"
}
fun stringArrayValue(value: Any?): UTSArray<String> {
    if (value == null) {
        return _uA()
    }
    val text = JSON.stringify(value)
    val parsed = if (text == null || text == "") {
        null
    } else {
        JSON.parseArray<Any>(text)
    }
    if (parsed == null) {
        return _uA()
    }
    val result: UTSArray<String> = _uA()
    run {
        var index: Number = 0
        while(index < parsed!!.length){
            result.push(stringValue__1(parsed!![index]))
            index += 1
        }
    }
    return result
}
fun buildProductMediaFileFromObject(rawObject: UTSJSONObject): ProductMediaFile {
    return ProductMediaFile(id = stringValue__1(rawObject["id"]), company = intValue__1(rawObject["company"]), original_filename = stringValue__1(rawObject["original_filename"]), file_type = stringValue__1(rawObject["file_type"]), file_type_display = stringValue__1(rawObject["file_type_display"]), mime_type = stringValue__1(rawObject["mime_type"]), file_size = intValue__1(rawObject["file_size"]), file_size_display = stringValue__1(rawObject["file_size_display"]), file_url = normalizeServerUrl(stringValue__1(rawObject["file_url"])), thumbnail_url = normalizeServerUrl(stringValue__1(rawObject["thumbnail_url"])), signed_url = normalizeServerUrl(stringValue__1(rawObject["signed_url"])), signed_thumbnail_url = normalizeServerUrl(stringValue__1(rawObject["signed_thumbnail_url"])), object_id = stringValue__1(rawObject["object_id"]), is_deleted = booleanValue(rawObject["is_deleted"]), created_at = stringValue__1(rawObject["created_at"]), updated_at = stringValue__1(rawObject["updated_at"]))
}
fun buildProductMediaFilesFromValue(value: Any?): UTSArray<ProductMediaFile> {
    val rawArray = parseObjectArray(value)
    val result: UTSArray<ProductMediaFile> = _uA()
    run {
        var index: Number = 0
        while(index < rawArray.length){
            result.push(buildProductMediaFileFromObject(rawArray[index]))
            index += 1
        }
    }
    return result
}
fun buildProductItemFromObject(rawObject: UTSJSONObject): ProductItem {
    return ProductItem(id = intValue__1(rawObject["id"]), sku = stringValue__1(rawObject["sku"]), barcode = stringValue__1(rawObject["barcode"]), name_cn = stringValue__1(rawObject["name_cn"]), name_en = stringValue__1(rawObject["name_en"]), name_other = stringValue__1(rawObject["name_other"]), description = stringValue__1(rawObject["description"]), media_files = buildProductMediaFilesFromValue(rawObject["media_files"]), category = rawObject["category"], supplier = if (rawObject["supplier"] == null) {
        null
    } else {
        intValue__1(rawObject["supplier"])
    }
    , supplier_name = stringValue__1(rawObject["supplier_name"]), purchase_price = stringValue__1(rawObject["purchase_price"]), net_purchase_price = stringValue__1(rawObject["net_purchase_price"]), cost_price = stringValue__1(rawObject["cost_price"]), base_sales_price = stringValue__1(rawObject["base_sales_price"]), status = stringValue__1(rawObject["status"]), is_featured = booleanValue(rawObject["is_featured"]), is_new = booleanValue(rawObject["is_new"]), is_bestseller = booleanValue(rawObject["is_bestseller"]), sort_order = intValue__1(rawObject["sort_order"]), rating = stringValue__1(rawObject["rating"]), variant_count = intValue__1(rawObject["variant_count"]), total_sales_quantity = intValue__1(rawObject["total_sales_quantity"]), total_sales_amount = stringValue__1(rawObject["total_sales_amount"]), last_sale_date = if (rawObject["last_sale_date"] == null) {
        null
    } else {
        stringValue__1(rawObject["last_sale_date"])
    }
    , created_at = stringValue__1(rawObject["created_at"]), updated_at = stringValue__1(rawObject["updated_at"]))
}
fun buildProductItemResponse(raw: Any): ProductItem {
    val rawObject = parseObject(raw)
    if (rawObject == null) {
        throw UTSError("商品详情响应解析失败")
    }
    return buildProductItemFromObject(rawObject!!)
}
fun buildPricingFormulaItemFromObject(rawObject: UTSJSONObject): ProductPricingFormulaItem {
    return ProductPricingFormulaItem(id = intValue__1(rawObject["id"]), company = intValue__1(rawObject["company"]), name = stringValue__1(rawObject["name"]), code = stringValue__1(rawObject["code"]), expression = stringValue__1(rawObject["expression"]), description = stringValue__1(rawObject["description"]), is_active = booleanValue(rawObject["is_active"]), supported_functions = stringArrayValue(rawObject["supported_functions"]), created_at = stringValue__1(rawObject["created_at"]), updated_at = stringValue__1(rawObject["updated_at"]))
}
fun buildPricingFormulaItemResponse(raw: Any): ProductPricingFormulaItem {
    val rawObject = parseObject(raw)
    if (rawObject == null) {
        throw UTSError("价格公式详情响应解析失败")
    }
    return buildPricingFormulaItemFromObject(rawObject!!)
}
fun buildPricingFormulaListResponse(raw: Any, query: ProductPricingFormulaListQuery): ProductPricingFormulaListResponse {
    val rawText = JSON.stringify(raw)
    val rawObject = if (rawText == null || rawText == "") {
        null
    } else {
        JSON.parseObject<UTSJSONObject>(rawText)
    }
    if (rawObject == null) {
        throw UTSError("价格公式列表响应解析失败")
    }
    var paginationObject: UTSJSONObject? = null
    val rawPagination = rawObject["pagination"]
    if (rawPagination != null) {
        val paginationText = JSON.stringify(rawPagination)
        if (paginationText != null && paginationText != "") {
            paginationObject = JSON.parseObject<UTSJSONObject>(paginationText)
        }
    }
    var results: UTSArray<ProductPricingFormulaItem> = _uA()
    val rawResults = rawObject["results"]
    if (rawResults != null) {
        val resultObjects = parseObjectArray(rawResults)
        run {
            var resultIndex: Number = 0
            while(resultIndex < resultObjects.length){
                results.push(buildPricingFormulaItemFromObject(resultObjects[resultIndex]))
                resultIndex += 1
            }
        }
    }
    var totalCount = intValue__1(rawObject["count"])
    if (totalCount <= 0) {
        totalCount = intValue__1(rawObject["total"])
    }
    if (totalCount <= 0) {
        totalCount = intValue__1(rawObject["total_count"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__1(paginationObject["total"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__1(paginationObject["count"])
    }
    if (totalCount <= 0) {
        totalCount = results.length
    }
    var currentPage = intValue__1(rawObject["page"])
    if (currentPage <= 0) {
        currentPage = intValue__1(rawObject["current_page"])
    }
    if (currentPage <= 0 && paginationObject != null) {
        currentPage = intValue__1(paginationObject["page"])
    }
    if (currentPage <= 0) {
        currentPage = query.page
    }
    var pageSize = intValue__1(rawObject["page_size"])
    if (pageSize <= 0) {
        pageSize = intValue__1(rawObject["per_page"])
    }
    if (pageSize <= 0 && paginationObject != null) {
        pageSize = intValue__1(paginationObject["page_size"])
    }
    if (pageSize <= 0) {
        pageSize = query.page_size
    }
    var totalPages = intValue__1(rawObject["total_pages"])
    if (totalPages <= 0) {
        totalPages = intValue__1(rawObject["pages"])
    }
    if (totalPages <= 0) {
        totalPages = intValue__1(rawObject["num_pages"])
    }
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__1(paginationObject["total_pages"])
    }
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__1(paginationObject["pages"])
    }
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__1(paginationObject["num_pages"])
    }
    if (totalPages <= 0 && pageSize > 0) {
        totalPages = Math.ceil(totalCount / pageSize)
    }
    if (totalPages <= 0) {
        totalPages = 1
    }
    return ProductPricingFormulaListResponse(results = results, count = totalCount, total_count = totalCount, total_pages = totalPages, current_page = currentPage, page_size = pageSize)
}
fun buildPricingFormulaMutationBody(data: ProductPricingFormulaMutationData): UTSJSONObject {
    return _uO("name" to data.name, "code" to data.code, "expression" to data.expression, "description" to data.description, "is_active" to data.is_active)
}
fun buildProductListResponse(raw: Any, query: ProductListQuery): ProductListResponse {
    val rawText = JSON.stringify(raw)
    val rawObject = if (rawText == null || rawText == "") {
        null
    } else {
        JSON.parseObject<UTSJSONObject>(rawText)
    }
    if (rawObject == null) {
        throw UTSError("商品列表响应解析失败")
    }
    var paginationObject: UTSJSONObject? = null
    val rawPagination = rawObject["pagination"]
    if (rawPagination != null) {
        val paginationText = JSON.stringify(rawPagination)
        if (paginationText != null && paginationText != "") {
            paginationObject = JSON.parseObject<UTSJSONObject>(paginationText)
        }
    }
    var results: UTSArray<ProductItem> = _uA()
    val rawResults = rawObject["results"]
    if (rawResults != null) {
        val resultObjects = parseObjectArray(rawResults)
        run {
            var resultIndex: Number = 0
            while(resultIndex < resultObjects.length){
                results.push(buildProductItemFromObject(resultObjects[resultIndex]))
                resultIndex += 1
            }
        }
    }
    var totalCount = intValue__1(rawObject["count"])
    if (totalCount <= 0) {
        totalCount = intValue__1(rawObject["total"])
    }
    if (totalCount <= 0) {
        totalCount = intValue__1(rawObject["total_count"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__1(paginationObject["total"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__1(paginationObject["count"])
    }
    if (totalCount <= 0) {
        totalCount = results.length
    }
    var currentPage = intValue__1(rawObject["page"])
    if (currentPage <= 0) {
        currentPage = intValue__1(rawObject["current_page"])
    }
    if (currentPage <= 0 && paginationObject != null) {
        currentPage = intValue__1(paginationObject["page"])
    }
    if (currentPage <= 0) {
        currentPage = query.page
    }
    var pageSize = intValue__1(rawObject["page_size"])
    if (pageSize <= 0) {
        pageSize = intValue__1(rawObject["per_page"])
    }
    if (pageSize <= 0 && paginationObject != null) {
        pageSize = intValue__1(paginationObject["page_size"])
    }
    if (pageSize <= 0) {
        pageSize = query.page_size
    }
    var totalPages = intValue__1(rawObject["total_pages"])
    if (totalPages <= 0) {
        totalPages = intValue__1(rawObject["pages"])
    }
    if (totalPages <= 0) {
        totalPages = intValue__1(rawObject["num_pages"])
    }
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__1(paginationObject["total_pages"])
    }
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__1(paginationObject["pages"])
    }
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__1(paginationObject["num_pages"])
    }
    if (totalPages <= 0 && pageSize > 0) {
        totalPages = Math.ceil(totalCount / pageSize)
    }
    if (totalPages <= 0) {
        totalPages = 1
    }
    return ProductListResponse(results = results, count = totalCount, total_count = totalCount, total_pages = totalPages, current_page = currentPage, page_size = pageSize)
}
fun buildProductFilterOptionsResponse(raw: Any): ProductFilterOptionsResponse {
    val rawText = JSON.stringify(raw)
    val rawObject = if (rawText == null || rawText == "") {
        null
    } else {
        JSON.parseObject<UTSJSONObject>(rawText)
    }
    if (rawObject == null) {
        throw UTSError("商品过滤选项解析失败")
    }
    var filters: UTSArray<ProductFilterDefinition> = _uA()
    val rawFilters = rawObject["filters"]
    if (rawFilters != null) {
        val filtersText = JSON.stringify(rawFilters)
        val filterObjects = if (filtersText == null || filtersText == "") {
            null
        } else {
            JSON.parseArray<UTSJSONObject>(filtersText)
        }
        if (filterObjects != null) {
            val nextFilters: UTSArray<ProductFilterDefinition> = _uA()
            run {
                var filterIndex: Number = 0
                while(filterIndex < filterObjects!!.length){
                    val filterObject = filterObjects!![filterIndex]
                    var options: UTSArray<ProductFilterOption> = _uA()
                    val rawOptions = filterObject["options"]
                    if (rawOptions != null) {
                        val optionsText = JSON.stringify(rawOptions)
                        val optionObjects = if (optionsText == null || optionsText == "") {
                            null
                        } else {
                            JSON.parseArray<UTSJSONObject>(optionsText)
                        }
                        if (optionObjects != null) {
                            val nextOptions: UTSArray<ProductFilterOption> = _uA()
                            run {
                                var optionIndex: Number = 0
                                while(optionIndex < optionObjects!!.length){
                                    val optionObject = optionObjects!![optionIndex]
                                    nextOptions.push(ProductFilterOption(value = stringValue__1(optionObject["value"]), label = stringValue__1(optionObject["label"])))
                                    optionIndex += 1
                                }
                            }
                            options = nextOptions
                        }
                    }
                    nextFilters.push(ProductFilterDefinition(key = stringValue__1(filterObject["key"]), param = stringValue__1(filterObject["param"]), label = stringValue__1(filterObject["label"]), control = stringValue__1(filterObject["control"]), aliases = stringArrayValue(filterObject["aliases"]), multiple = booleanValue(filterObject["multiple"]), options = options))
                    filterIndex += 1
                }
            }
            filters = nextFilters
        }
    }
    return ProductFilterOptionsResponse(resource = stringValue__1(rawObject["resource"]), count = intValue__1(rawObject["count"]), filters = filters)
}
fun productDetailPath(id: Any): String {
    return productsBasePath + stringValue__1(id) + "/"
}
fun pricingFormulaDetailPath(id: Any): String {
    return productPricingFormulasBasePath + stringValue__1(id) + "/"
}
fun resourceDetailPath(basePath: String, id: Any): String {
    return basePath + stringValue__1(id) + "/"
}
fun buildConfigListQuery(search: String?, page: Number, pageSize: Number, extra: UTSJSONObject = _uO()): UTSJSONObject {
    val query: UTSJSONObject = _uO("page" to page, "page_size" to pageSize)
    if (search != null && search != "") {
        query["search"] = search
    }
    val attributeType = extra["attribute_type"]
    if (attributeType != null && ("" + attributeType) != "") {
        query["attribute_type"] = attributeType
    }
    val status = extra["status"]
    if (status != null && ("" + status) != "") {
        query["status"] = status
    }
    val discountType = extra["discount_type"]
    if (discountType != null && ("" + discountType) != "") {
        query["discount_type"] = discountType
    }
    val shop = extra["shop"]
    if (shop != null && ("" + shop) != "") {
        query["shop"] = shop
    }
    return query
}
fun buildConfigListResponse(raw: Any, page: Number, pageSize: Number): UTSJSONObject {
    val rawText = JSON.stringify(raw)
    val rawObject = if (rawText == null || rawText == "") {
        null
    } else {
        JSON.parseObject<UTSJSONObject>(rawText)
    }
    if (rawObject == null) {
        throw UTSError("配置列表响应解析失败")
    }
    var paginationObject: UTSJSONObject? = null
    val rawPagination = rawObject["pagination"]
    if (rawPagination != null) {
        val paginationText = JSON.stringify(rawPagination)
        if (paginationText != null && paginationText != "") {
            paginationObject = JSON.parseObject<UTSJSONObject>(paginationText)
        }
    }
    var results: UTSArray<UTSJSONObject> = _uA()
    val rawResults = rawObject["results"]
    if (rawResults != null) {
        results = parseObjectArray(rawResults)
    }
    var totalCount = intValue__1(rawObject["count"])
    if (totalCount <= 0) {
        totalCount = intValue__1(rawObject["total"])
    }
    if (totalCount <= 0) {
        totalCount = intValue__1(rawObject["total_count"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__1(paginationObject["total"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__1(paginationObject["count"])
    }
    if (totalCount <= 0) {
        totalCount = results.length
    }
    var currentPage = intValue__1(rawObject["page"])
    if (currentPage <= 0) {
        currentPage = intValue__1(rawObject["current_page"])
    }
    if (currentPage <= 0 && paginationObject != null) {
        currentPage = intValue__1(paginationObject["page"])
    }
    if (currentPage <= 0) {
        currentPage = page
    }
    var resolvedPageSize = intValue__1(rawObject["page_size"])
    if (resolvedPageSize <= 0) {
        resolvedPageSize = intValue__1(rawObject["per_page"])
    }
    if (resolvedPageSize <= 0 && paginationObject != null) {
        resolvedPageSize = intValue__1(paginationObject["page_size"])
    }
    if (resolvedPageSize <= 0) {
        resolvedPageSize = pageSize
    }
    var totalPages = intValue__1(rawObject["total_pages"])
    if (totalPages <= 0) {
        totalPages = intValue__1(rawObject["pages"])
    }
    if (totalPages <= 0) {
        totalPages = intValue__1(rawObject["num_pages"])
    }
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__1(paginationObject["total_pages"])
    }
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__1(paginationObject["pages"])
    }
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__1(paginationObject["num_pages"])
    }
    if (totalPages <= 0 && resolvedPageSize > 0) {
        totalPages = Math.ceil(totalCount / resolvedPageSize)
    }
    if (totalPages <= 0) {
        totalPages = 1
    }
    return _uO("results" to results, "count" to totalCount, "total_count" to totalCount, "total_pages" to totalPages, "current_page" to currentPage, "page_size" to resolvedPageSize)
}
fun buildProductMutationBody(data: ProductMutationData): UTSJSONObject {
    val body: UTSJSONObject = _uO("name_cn" to data.name_cn, "purchase_price" to data.purchase_price, "net_purchase_price" to data.net_purchase_price, "cost_price" to data.cost_price, "base_sales_price" to data.base_sales_price, "status" to data.status, "is_featured" to data.is_featured, "is_new" to data.is_new, "is_bestseller" to data.is_bestseller, "sort_order" to data.sort_order)
    if (data.sku != null) {
        body["sku"] = data.sku
    }
    if (data.barcode != null) {
        body["barcode"] = data.barcode
    }
    if (data.name_en != null) {
        body["name_en"] = data.name_en
    }
    if (data.name_other != null) {
        body["name_other"] = data.name_other
    }
    if (data.description != null) {
        body["description"] = data.description
    }
    if (data.category != null) {
        body["category"] = data.category
    }
    if (data.supplier != null) {
        body["supplier"] = data.supplier
    }
    return body
}
fun getProductList(data: ProductListQuery): UTSPromise<ProductListResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(productsBasePath, "GET", buildListQuery(data), true))
            return@w normalizeProductList(buildProductListResponse(raw, data))
    })
}
fun getProductFilterOptions(): UTSPromise<ProductFilterOptionsResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(productsBasePath + "filter-options/", "GET", _uO(), true))
            return@w buildProductFilterOptionsResponse(raw)
    })
}
fun getProductDetail(id: Any): UTSPromise<ProductItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(productDetailPath(id), "GET", _uO(), true))
            return@w normalizeProductItem(buildProductItemResponse(raw))
    })
}
fun createProduct(data: ProductMutationData): UTSPromise<ProductItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(productsBasePath, "POST", buildProductMutationBody(data), true))
            return@w normalizeProductItem(buildProductItemResponse(raw))
    })
}
fun updateProduct(id: Any, data: ProductMutationData): UTSPromise<ProductItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(productDetailPath(id), "PUT", buildProductMutationBody(data), true))
            return@w normalizeProductItem(buildProductItemResponse(raw))
    })
}
fun getProductPricingFormulaDetail(id: Any): UTSPromise<ProductPricingFormulaItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(pricingFormulaDetailPath(id), "GET", _uO(), true))
            return@w buildPricingFormulaItemResponse(raw)
    })
}
fun getProductPricingFormulaList(data: ProductPricingFormulaListQuery): UTSPromise<ProductPricingFormulaListResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(productPricingFormulasBasePath, "GET", buildPricingFormulaListQuery(data), true))
            return@w buildPricingFormulaListResponse(raw, data)
    })
}
fun createProductPricingFormula(data: ProductPricingFormulaMutationData): UTSPromise<ProductPricingFormulaItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(productPricingFormulasBasePath, "POST", buildPricingFormulaMutationBody(data), true))
            return@w buildPricingFormulaItemResponse(raw)
    })
}
fun updateProductPricingFormula(id: Any, data: ProductPricingFormulaMutationData): UTSPromise<ProductPricingFormulaItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(pricingFormulaDetailPath(id), "PUT", buildPricingFormulaMutationBody(data), true))
            return@w buildPricingFormulaItemResponse(raw)
    })
}
fun getProductConfigList(basePath: String, search: String?, page: Number, pageSize: Number, extra: UTSJSONObject = _uO()): UTSPromise<UTSJSONObject> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(basePath, "GET", buildConfigListQuery(search, page, pageSize, extra), true))
            return@w buildConfigListResponse(raw, page, pageSize)
    })
}
fun getProductConfigDetail(basePath: String, id: Any): UTSPromise<UTSJSONObject> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(resourceDetailPath(basePath, id), "GET", _uO(), true))
            val parsed = parseObject(raw)
            if (parsed == null) {
                throw UTSError("配置详情响应解析失败")
            }
            return@w parsed!!
    })
}
fun createProductConfig(basePath: String, data: UTSJSONObject): UTSPromise<UTSJSONObject> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(basePath, "POST", data, true))
            val parsed = parseObject(raw)
            if (parsed == null) {
                throw UTSError("配置创建响应解析失败")
            }
            return@w parsed!!
    })
}
fun updateProductConfig(basePath: String, id: Any, data: UTSJSONObject): UTSPromise<UTSJSONObject> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(resourceDetailPath(basePath, id), "PUT", data, true))
            val parsed = parseObject(raw)
            if (parsed == null) {
                throw UTSError("配置保存响应解析失败")
            }
            return@w parsed!!
    })
}
fun deleteProductConfig(basePath: String, id: Any): UTSPromise<Any> {
    return request(resourceDetailPath(basePath, id), "DELETE", _uO(), true)
}
fun productDiscountsPath(): String {
    return productDiscountsBasePath
}
fun attributeTypesPath(): String {
    return attributeTypesBasePath
}
fun attributeValuesPath(): String {
    return attributeValuesBasePath
}
fun barcodeSequencesPath(): String {
    return barcodeSequencesBasePath
}
val GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass = CreateVueComponent(GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilter::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "component", name = "", inheritAttrs = GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilter.inheritAttrs, inject = GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilter.inject, props = GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilter.props, propsNeedCastKeys = GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilter.propsNeedCastKeys, emits = GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilter.emits, components = GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilter.components, styles = GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilter.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilter.setup(props as GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilter)
    }
    )
}
, fun(instance, renderer): GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilter {
    return GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilter(instance)
}
)
val GenUniModulesLiliPreviewComponentsLiliPreviewLiliPreviewClass = CreateVueComponent(GenUniModulesLiliPreviewComponentsLiliPreviewLiliPreview::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "component", name = "", inheritAttrs = GenUniModulesLiliPreviewComponentsLiliPreviewLiliPreview.inheritAttrs, inject = GenUniModulesLiliPreviewComponentsLiliPreviewLiliPreview.inject, props = GenUniModulesLiliPreviewComponentsLiliPreviewLiliPreview.props, propsNeedCastKeys = GenUniModulesLiliPreviewComponentsLiliPreviewLiliPreview.propsNeedCastKeys, emits = GenUniModulesLiliPreviewComponentsLiliPreviewLiliPreview.emits, components = GenUniModulesLiliPreviewComponentsLiliPreviewLiliPreview.components, styles = GenUniModulesLiliPreviewComponentsLiliPreviewLiliPreview.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenUniModulesLiliPreviewComponentsLiliPreviewLiliPreview.setup(props as GenUniModulesLiliPreviewComponentsLiliPreviewLiliPreview)
    }
    )
}
, fun(instance, renderer): GenUniModulesLiliPreviewComponentsLiliPreviewLiliPreview {
    return GenUniModulesLiliPreviewComponentsLiliPreviewLiliPreview(instance)
}
)
val GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalListClass = CreateVueComponent(GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalList::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "component", name = "", inheritAttrs = GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalList.inheritAttrs, inject = GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalList.inject, props = GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalList.props, propsNeedCastKeys = GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalList.propsNeedCastKeys, emits = GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalList.emits, components = GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalList.components, styles = GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalList.styles, setup = fun(props: ComponentPublicInstance, ctx: SetupContext): Any? {
        return GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalList.setup(props as GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalList, ctx)
    }
    )
}
, fun(instance, renderer): GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalList {
    return GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalList(instance)
}
)
open class SelectChangePayload (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var text: String,
    @JsonNotNull
    open var item: UTSJSONObject,
) : UTSObject()
open class MultiSelectChangePayload (
    @JsonNotNull
    open var values: UTSArray<String>,
    @JsonNotNull
    open var texts: UTSArray<String>,
    @JsonNotNull
    open var items: UTSArray<UTSJSONObject>,
) : UTSObject()
open class TreeDisplayRow (
    @JsonNotNull
    open var key: String,
    @JsonNotNull
    open var item: UTSJSONObject,
    @JsonNotNull
    open var level: Number,
    @JsonNotNull
    open var hasChildren: Boolean = false,
    @JsonNotNull
    open var expanded: Boolean = false,
) : UTSObject()
val GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelectClass = CreateVueComponent(GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelect::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "component", name = "", inheritAttrs = GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelect.inheritAttrs, inject = GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelect.inject, props = GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelect.props, propsNeedCastKeys = GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelect.propsNeedCastKeys, emits = GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelect.emits, components = GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelect.components, styles = GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelect.styles, setup = fun(props: ComponentPublicInstance, ctx: SetupContext): Any? {
        return GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelect.setup(props as GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelect, ctx)
    }
    )
}
, fun(instance, renderer): GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelect {
    return GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelect(instance)
}
)
open class ProductSelectOption (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var text: String,
) : UTSObject()
val GenPagesTabbarProductsClass = CreateVueComponent(GenPagesTabbarProducts::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesTabbarProducts.inheritAttrs, inject = GenPagesTabbarProducts.inject, props = GenPagesTabbarProducts.props, propsNeedCastKeys = GenPagesTabbarProducts.propsNeedCastKeys, emits = GenPagesTabbarProducts.emits, components = GenPagesTabbarProducts.components, styles = GenPagesTabbarProducts.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesTabbarProducts.setup(props as GenPagesTabbarProducts)
    }
    )
}
, fun(instance, renderer): GenPagesTabbarProducts {
    return GenPagesTabbarProducts(instance, renderer)
}
)
open class MenuItem (
    @JsonNotNull
    open var label: String,
    @JsonNotNull
    open var icon: String,
    open var iconPath: String? = null,
    open var path: String? = null,
    @JsonNotNull
    open var action: String,
    @JsonNotNull
    open var disabled: Boolean = false,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return MenuItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class MenuItemReactiveObject : MenuItem, IUTSReactive<MenuItem> {
    override var __v_raw: MenuItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: MenuItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(label = __v_raw.label, icon = __v_raw.icon, iconPath = __v_raw.iconPath, path = __v_raw.path, action = __v_raw.action, disabled = __v_raw.disabled) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): MenuItemReactiveObject {
        return MenuItemReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var label: String
        get() {
            return _tRG(__v_raw, "label", __v_raw.label, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("label")) {
                return
            }
            val oldValue = __v_raw.label
            __v_raw.label = value
            _tRS(__v_raw, "label", oldValue, value)
        }
    override var icon: String
        get() {
            return _tRG(__v_raw, "icon", __v_raw.icon, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("icon")) {
                return
            }
            val oldValue = __v_raw.icon
            __v_raw.icon = value
            _tRS(__v_raw, "icon", oldValue, value)
        }
    override var iconPath: String?
        get() {
            return _tRG(__v_raw, "iconPath", __v_raw.iconPath, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("iconPath")) {
                return
            }
            val oldValue = __v_raw.iconPath
            __v_raw.iconPath = value
            _tRS(__v_raw, "iconPath", oldValue, value)
        }
    override var path: String?
        get() {
            return _tRG(__v_raw, "path", __v_raw.path, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("path")) {
                return
            }
            val oldValue = __v_raw.path
            __v_raw.path = value
            _tRS(__v_raw, "path", oldValue, value)
        }
    override var action: String
        get() {
            return _tRG(__v_raw, "action", __v_raw.action, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("action")) {
                return
            }
            val oldValue = __v_raw.action
            __v_raw.action = value
            _tRS(__v_raw, "action", oldValue, value)
        }
    override var disabled: Boolean
        get() {
            return _tRG(__v_raw, "disabled", __v_raw.disabled, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("disabled")) {
                return
            }
            val oldValue = __v_raw.disabled
            __v_raw.disabled = value
            _tRS(__v_raw, "disabled", oldValue, value)
        }
}
open class MenuGroup (
    @JsonNotNull
    open var label: String,
    @JsonNotNull
    open var rows: UTSArray<UTSArray<MenuItem>>,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return MenuGroupReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class MenuGroupReactiveObject : MenuGroup, IUTSReactive<MenuGroup> {
    override var __v_raw: MenuGroup
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: MenuGroup, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(label = __v_raw.label, rows = __v_raw.rows) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): MenuGroupReactiveObject {
        return MenuGroupReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var label: String
        get() {
            return _tRG(__v_raw, "label", __v_raw.label, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("label")) {
                return
            }
            val oldValue = __v_raw.label
            __v_raw.label = value
            _tRS(__v_raw, "label", oldValue, value)
        }
    override var rows: UTSArray<UTSArray<MenuItem>>
        get() {
            return _tRG(__v_raw, "rows", __v_raw.rows, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("rows")) {
                return
            }
            val oldValue = __v_raw.rows
            __v_raw.rows = value
            _tRS(__v_raw, "rows", oldValue, value)
        }
}
val GenPagesTabbarSettingsClass = CreateVueComponent(GenPagesTabbarSettings::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesTabbarSettings.inheritAttrs, inject = GenPagesTabbarSettings.inject, props = GenPagesTabbarSettings.props, propsNeedCastKeys = GenPagesTabbarSettings.propsNeedCastKeys, emits = GenPagesTabbarSettings.emits, components = GenPagesTabbarSettings.components, styles = GenPagesTabbarSettings.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesTabbarSettings.setup(props as GenPagesTabbarSettings)
    }
    )
}
, fun(instance, renderer): GenPagesTabbarSettings {
    return GenPagesTabbarSettings(instance, renderer)
}
)
open class MenuItem__1 (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var icon: String,
    @JsonNotNull
    open var key: String,
    @JsonNotNull
    open var arrow: Boolean = false,
    open var tone: String? = null,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return MenuItem__1ReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class MenuItem__1ReactiveObject : MenuItem__1, IUTSReactive<MenuItem__1> {
    override var __v_raw: MenuItem__1
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: MenuItem__1, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, name = __v_raw.name, icon = __v_raw.icon, key = __v_raw.key, arrow = __v_raw.arrow, tone = __v_raw.tone) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): MenuItem__1ReactiveObject {
        return MenuItem__1ReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return _tRG(__v_raw, "id", __v_raw.id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            _tRS(__v_raw, "id", oldValue, value)
        }
    override var name: String
        get() {
            return _tRG(__v_raw, "name", __v_raw.name, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("name")) {
                return
            }
            val oldValue = __v_raw.name
            __v_raw.name = value
            _tRS(__v_raw, "name", oldValue, value)
        }
    override var icon: String
        get() {
            return _tRG(__v_raw, "icon", __v_raw.icon, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("icon")) {
                return
            }
            val oldValue = __v_raw.icon
            __v_raw.icon = value
            _tRS(__v_raw, "icon", oldValue, value)
        }
    override var key: String
        get() {
            return _tRG(__v_raw, "key", __v_raw.key, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("key")) {
                return
            }
            val oldValue = __v_raw.key
            __v_raw.key = value
            _tRS(__v_raw, "key", oldValue, value)
        }
    override var arrow: Boolean
        get() {
            return _tRG(__v_raw, "arrow", __v_raw.arrow, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("arrow")) {
                return
            }
            val oldValue = __v_raw.arrow
            __v_raw.arrow = value
            _tRS(__v_raw, "arrow", oldValue, value)
        }
    override var tone: String?
        get() {
            return _tRG(__v_raw, "tone", __v_raw.tone, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("tone")) {
                return
            }
            val oldValue = __v_raw.tone
            __v_raw.tone = value
            _tRS(__v_raw, "tone", oldValue, value)
        }
}
val GenPagesTabbarMineClass = CreateVueComponent(GenPagesTabbarMine::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesTabbarMine.inheritAttrs, inject = GenPagesTabbarMine.inject, props = GenPagesTabbarMine.props, propsNeedCastKeys = GenPagesTabbarMine.propsNeedCastKeys, emits = GenPagesTabbarMine.emits, components = GenPagesTabbarMine.components, styles = GenPagesTabbarMine.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesTabbarMine.setup(props as GenPagesTabbarMine)
    }
    )
}
, fun(instance, renderer): GenPagesTabbarMine {
    return GenPagesTabbarMine(instance, renderer)
}
)
val GenPagesTestScanClass = CreateVueComponent(GenPagesTestScan::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesTestScan.inheritAttrs, inject = GenPagesTestScan.inject, props = GenPagesTestScan.props, propsNeedCastKeys = GenPagesTestScan.propsNeedCastKeys, emits = GenPagesTestScan.emits, components = GenPagesTestScan.components, styles = GenPagesTestScan.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesTestScan.setup(props as GenPagesTestScan)
    }
    )
}
, fun(instance, renderer): GenPagesTestScan {
    return GenPagesTestScan(instance, renderer)
}
)
open class StoreListItem (
    @JsonNotNull
    open var enable: Boolean = false,
    @JsonNotNull
    open var id: String,
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var scheme: String,
    @JsonNotNull
    open var priority: Number,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return StoreListItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class StoreListItemReactiveObject : StoreListItem, IUTSReactive<StoreListItem> {
    override var __v_raw: StoreListItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: StoreListItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(enable = __v_raw.enable, id = __v_raw.id, name = __v_raw.name, scheme = __v_raw.scheme, priority = __v_raw.priority) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): StoreListItemReactiveObject {
        return StoreListItemReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var enable: Boolean
        get() {
            return _tRG(__v_raw, "enable", __v_raw.enable, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("enable")) {
                return
            }
            val oldValue = __v_raw.enable
            __v_raw.enable = value
            _tRS(__v_raw, "enable", oldValue, value)
        }
    override var id: String
        get() {
            return _tRG(__v_raw, "id", __v_raw.id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            _tRS(__v_raw, "id", oldValue, value)
        }
    override var name: String
        get() {
            return _tRG(__v_raw, "name", __v_raw.name, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("name")) {
                return
            }
            val oldValue = __v_raw.name
            __v_raw.name = value
            _tRS(__v_raw, "name", oldValue, value)
        }
    override var scheme: String
        get() {
            return _tRG(__v_raw, "scheme", __v_raw.scheme, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("scheme")) {
                return
            }
            val oldValue = __v_raw.scheme
            __v_raw.scheme = value
            _tRS(__v_raw, "scheme", oldValue, value)
        }
    override var priority: Number
        get() {
            return _tRG(__v_raw, "priority", __v_raw.priority, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("priority")) {
                return
            }
            val oldValue = __v_raw.priority
            __v_raw.priority = value
            _tRS(__v_raw, "priority", oldValue, value)
        }
}
open class UniUpgradeCenterResult (
    @JsonNotNull
    open var _id: String,
    @JsonNotNull
    open var appid: String,
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var title: String,
    @JsonNotNull
    open var contents: String,
    @JsonNotNull
    open var url: String,
    @JsonNotNull
    open var platform: UTSArray<String>,
    @JsonNotNull
    open var version: String,
    @JsonNotNull
    open var uni_platform: String,
    @JsonNotNull
    open var stable_publish: Boolean = false,
    @JsonNotNull
    open var is_mandatory: Boolean = false,
    open var is_silently: Boolean? = null,
    @JsonNotNull
    open var create_env: String,
    @JsonNotNull
    open var create_date: Number,
    @JsonNotNull
    open var message: String,
    @JsonNotNull
    open var code: Number,
    @JsonNotNull
    open var type: String,
    open var store_list: UTSArray<StoreListItem>? = null,
    open var min_uni_version: String? = null,
) : UTSObject()
val platform_iOS: String = "iOS"
val platform_Android: String = "Android"
val platform_Harmony: String = "Harmony"
val GenUniModulesUniUpgradeCenterAppPagesUniAppXUpgradePopupClass = CreateVueComponent(GenUniModulesUniUpgradeCenterAppPagesUniAppXUpgradePopup::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenUniModulesUniUpgradeCenterAppPagesUniAppXUpgradePopup.inheritAttrs, inject = GenUniModulesUniUpgradeCenterAppPagesUniAppXUpgradePopup.inject, props = GenUniModulesUniUpgradeCenterAppPagesUniAppXUpgradePopup.props, propsNeedCastKeys = GenUniModulesUniUpgradeCenterAppPagesUniAppXUpgradePopup.propsNeedCastKeys, emits = GenUniModulesUniUpgradeCenterAppPagesUniAppXUpgradePopup.emits, components = GenUniModulesUniUpgradeCenterAppPagesUniAppXUpgradePopup.components, styles = GenUniModulesUniUpgradeCenterAppPagesUniAppXUpgradePopup.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenUniModulesUniUpgradeCenterAppPagesUniAppXUpgradePopup.setup(props as GenUniModulesUniUpgradeCenterAppPagesUniAppXUpgradePopup)
    }
    )
}
, fun(instance, renderer): GenUniModulesUniUpgradeCenterAppPagesUniAppXUpgradePopup {
    return GenUniModulesUniUpgradeCenterAppPagesUniAppXUpgradePopup(instance, renderer)
}
)
val GenPagesWebviewWebviewClass = CreateVueComponent(GenPagesWebviewWebview::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesWebviewWebview.inheritAttrs, inject = GenPagesWebviewWebview.inject, props = GenPagesWebviewWebview.props, propsNeedCastKeys = GenPagesWebviewWebview.propsNeedCastKeys, emits = GenPagesWebviewWebview.emits, components = GenPagesWebviewWebview.components, styles = GenPagesWebviewWebview.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesWebviewWebview.setup(props as GenPagesWebviewWebview)
    }
    )
}
, fun(instance, renderer): GenPagesWebviewWebview {
    return GenPagesWebviewWebview(instance, renderer)
}
)
val `default` = "/static/logo.png"
val GenPagesPrivacyPrivacyClass = CreateVueComponent(GenPagesPrivacyPrivacy::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesPrivacyPrivacy.inheritAttrs, inject = GenPagesPrivacyPrivacy.inject, props = GenPagesPrivacyPrivacy.props, propsNeedCastKeys = GenPagesPrivacyPrivacy.propsNeedCastKeys, emits = GenPagesPrivacyPrivacy.emits, components = GenPagesPrivacyPrivacy.components, styles = GenPagesPrivacyPrivacy.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesPrivacyPrivacy.setup(props as GenPagesPrivacyPrivacy)
    }
    )
}
, fun(instance, renderer): GenPagesPrivacyPrivacy {
    return GenPagesPrivacyPrivacy(instance, renderer)
}
)
open class SupplierListQuery (
    open var search: String? = null,
    @JsonNotNull
    open var page: Number,
    @JsonNotNull
    open var page_size: Number,
    open var is_active: String? = null,
    open var has_arrears: String? = null,
) : UTSObject()
open class SupplierMediaFile (
    @JsonNotNull
    open var id: String,
    @JsonNotNull
    open var company: Number,
    @JsonNotNull
    open var original_filename: String,
    @JsonNotNull
    open var file_type: String,
    @JsonNotNull
    open var file_type_display: String,
    @JsonNotNull
    open var mime_type: String,
    @JsonNotNull
    open var file_size: Number,
    @JsonNotNull
    open var file_size_display: String,
    @JsonNotNull
    open var file_url: String,
    @JsonNotNull
    open var thumbnail_url: String,
    @JsonNotNull
    open var signed_url: String,
    @JsonNotNull
    open var signed_thumbnail_url: String,
    @JsonNotNull
    open var object_id: String,
    @JsonNotNull
    open var is_deleted: Boolean = false,
    @JsonNotNull
    open var created_at: String,
    @JsonNotNull
    open var updated_at: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return SupplierMediaFileReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class SupplierMediaFileReactiveObject : SupplierMediaFile, IUTSReactive<SupplierMediaFile> {
    override var __v_raw: SupplierMediaFile
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: SupplierMediaFile, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, company = __v_raw.company, original_filename = __v_raw.original_filename, file_type = __v_raw.file_type, file_type_display = __v_raw.file_type_display, mime_type = __v_raw.mime_type, file_size = __v_raw.file_size, file_size_display = __v_raw.file_size_display, file_url = __v_raw.file_url, thumbnail_url = __v_raw.thumbnail_url, signed_url = __v_raw.signed_url, signed_thumbnail_url = __v_raw.signed_thumbnail_url, object_id = __v_raw.object_id, is_deleted = __v_raw.is_deleted, created_at = __v_raw.created_at, updated_at = __v_raw.updated_at) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): SupplierMediaFileReactiveObject {
        return SupplierMediaFileReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: String
        get() {
            return _tRG(__v_raw, "id", __v_raw.id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            _tRS(__v_raw, "id", oldValue, value)
        }
    override var company: Number
        get() {
            return _tRG(__v_raw, "company", __v_raw.company, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("company")) {
                return
            }
            val oldValue = __v_raw.company
            __v_raw.company = value
            _tRS(__v_raw, "company", oldValue, value)
        }
    override var original_filename: String
        get() {
            return _tRG(__v_raw, "original_filename", __v_raw.original_filename, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("original_filename")) {
                return
            }
            val oldValue = __v_raw.original_filename
            __v_raw.original_filename = value
            _tRS(__v_raw, "original_filename", oldValue, value)
        }
    override var file_type: String
        get() {
            return _tRG(__v_raw, "file_type", __v_raw.file_type, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("file_type")) {
                return
            }
            val oldValue = __v_raw.file_type
            __v_raw.file_type = value
            _tRS(__v_raw, "file_type", oldValue, value)
        }
    override var file_type_display: String
        get() {
            return _tRG(__v_raw, "file_type_display", __v_raw.file_type_display, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("file_type_display")) {
                return
            }
            val oldValue = __v_raw.file_type_display
            __v_raw.file_type_display = value
            _tRS(__v_raw, "file_type_display", oldValue, value)
        }
    override var mime_type: String
        get() {
            return _tRG(__v_raw, "mime_type", __v_raw.mime_type, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("mime_type")) {
                return
            }
            val oldValue = __v_raw.mime_type
            __v_raw.mime_type = value
            _tRS(__v_raw, "mime_type", oldValue, value)
        }
    override var file_size: Number
        get() {
            return _tRG(__v_raw, "file_size", __v_raw.file_size, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("file_size")) {
                return
            }
            val oldValue = __v_raw.file_size
            __v_raw.file_size = value
            _tRS(__v_raw, "file_size", oldValue, value)
        }
    override var file_size_display: String
        get() {
            return _tRG(__v_raw, "file_size_display", __v_raw.file_size_display, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("file_size_display")) {
                return
            }
            val oldValue = __v_raw.file_size_display
            __v_raw.file_size_display = value
            _tRS(__v_raw, "file_size_display", oldValue, value)
        }
    override var file_url: String
        get() {
            return _tRG(__v_raw, "file_url", __v_raw.file_url, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("file_url")) {
                return
            }
            val oldValue = __v_raw.file_url
            __v_raw.file_url = value
            _tRS(__v_raw, "file_url", oldValue, value)
        }
    override var thumbnail_url: String
        get() {
            return _tRG(__v_raw, "thumbnail_url", __v_raw.thumbnail_url, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("thumbnail_url")) {
                return
            }
            val oldValue = __v_raw.thumbnail_url
            __v_raw.thumbnail_url = value
            _tRS(__v_raw, "thumbnail_url", oldValue, value)
        }
    override var signed_url: String
        get() {
            return _tRG(__v_raw, "signed_url", __v_raw.signed_url, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("signed_url")) {
                return
            }
            val oldValue = __v_raw.signed_url
            __v_raw.signed_url = value
            _tRS(__v_raw, "signed_url", oldValue, value)
        }
    override var signed_thumbnail_url: String
        get() {
            return _tRG(__v_raw, "signed_thumbnail_url", __v_raw.signed_thumbnail_url, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("signed_thumbnail_url")) {
                return
            }
            val oldValue = __v_raw.signed_thumbnail_url
            __v_raw.signed_thumbnail_url = value
            _tRS(__v_raw, "signed_thumbnail_url", oldValue, value)
        }
    override var object_id: String
        get() {
            return _tRG(__v_raw, "object_id", __v_raw.object_id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("object_id")) {
                return
            }
            val oldValue = __v_raw.object_id
            __v_raw.object_id = value
            _tRS(__v_raw, "object_id", oldValue, value)
        }
    override var is_deleted: Boolean
        get() {
            return _tRG(__v_raw, "is_deleted", __v_raw.is_deleted, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("is_deleted")) {
                return
            }
            val oldValue = __v_raw.is_deleted
            __v_raw.is_deleted = value
            _tRS(__v_raw, "is_deleted", oldValue, value)
        }
    override var created_at: String
        get() {
            return _tRG(__v_raw, "created_at", __v_raw.created_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("created_at")) {
                return
            }
            val oldValue = __v_raw.created_at
            __v_raw.created_at = value
            _tRS(__v_raw, "created_at", oldValue, value)
        }
    override var updated_at: String
        get() {
            return _tRG(__v_raw, "updated_at", __v_raw.updated_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("updated_at")) {
                return
            }
            val oldValue = __v_raw.updated_at
            __v_raw.updated_at = value
            _tRS(__v_raw, "updated_at", oldValue, value)
        }
}
open class SupplierItem (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var code: String,
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var address: String,
    @JsonNotNull
    open var phone: String,
    @JsonNotNull
    open var contact: String,
    open var description: String? = null,
    @JsonNotNull
    open var total_amount: String,
    @JsonNotNull
    open var arrears_amount: String,
    @JsonNotNull
    open var paid_amount: Number,
    @JsonNotNull
    open var is_active: Boolean = false,
    @JsonNotNull
    open var files_count: Number,
    @JsonNotNull
    open var company_infos: UTSArray<UTSJSONObject>,
    @JsonNotNull
    open var is_deleted: Boolean = false,
    @JsonNotNull
    open var created_at: String,
    @JsonNotNull
    open var updated_at: String,
    @JsonNotNull
    open var media_files: UTSArray<SupplierMediaFile>,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return SupplierItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class SupplierItemReactiveObject : SupplierItem, IUTSReactive<SupplierItem> {
    override var __v_raw: SupplierItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: SupplierItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, code = __v_raw.code, name = __v_raw.name, address = __v_raw.address, phone = __v_raw.phone, contact = __v_raw.contact, description = __v_raw.description, total_amount = __v_raw.total_amount, arrears_amount = __v_raw.arrears_amount, paid_amount = __v_raw.paid_amount, is_active = __v_raw.is_active, files_count = __v_raw.files_count, company_infos = __v_raw.company_infos, is_deleted = __v_raw.is_deleted, created_at = __v_raw.created_at, updated_at = __v_raw.updated_at, media_files = __v_raw.media_files) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): SupplierItemReactiveObject {
        return SupplierItemReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return _tRG(__v_raw, "id", __v_raw.id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            _tRS(__v_raw, "id", oldValue, value)
        }
    override var code: String
        get() {
            return _tRG(__v_raw, "code", __v_raw.code, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("code")) {
                return
            }
            val oldValue = __v_raw.code
            __v_raw.code = value
            _tRS(__v_raw, "code", oldValue, value)
        }
    override var name: String
        get() {
            return _tRG(__v_raw, "name", __v_raw.name, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("name")) {
                return
            }
            val oldValue = __v_raw.name
            __v_raw.name = value
            _tRS(__v_raw, "name", oldValue, value)
        }
    override var address: String
        get() {
            return _tRG(__v_raw, "address", __v_raw.address, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("address")) {
                return
            }
            val oldValue = __v_raw.address
            __v_raw.address = value
            _tRS(__v_raw, "address", oldValue, value)
        }
    override var phone: String
        get() {
            return _tRG(__v_raw, "phone", __v_raw.phone, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("phone")) {
                return
            }
            val oldValue = __v_raw.phone
            __v_raw.phone = value
            _tRS(__v_raw, "phone", oldValue, value)
        }
    override var contact: String
        get() {
            return _tRG(__v_raw, "contact", __v_raw.contact, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("contact")) {
                return
            }
            val oldValue = __v_raw.contact
            __v_raw.contact = value
            _tRS(__v_raw, "contact", oldValue, value)
        }
    override var description: String?
        get() {
            return _tRG(__v_raw, "description", __v_raw.description, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("description")) {
                return
            }
            val oldValue = __v_raw.description
            __v_raw.description = value
            _tRS(__v_raw, "description", oldValue, value)
        }
    override var total_amount: String
        get() {
            return _tRG(__v_raw, "total_amount", __v_raw.total_amount, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("total_amount")) {
                return
            }
            val oldValue = __v_raw.total_amount
            __v_raw.total_amount = value
            _tRS(__v_raw, "total_amount", oldValue, value)
        }
    override var arrears_amount: String
        get() {
            return _tRG(__v_raw, "arrears_amount", __v_raw.arrears_amount, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("arrears_amount")) {
                return
            }
            val oldValue = __v_raw.arrears_amount
            __v_raw.arrears_amount = value
            _tRS(__v_raw, "arrears_amount", oldValue, value)
        }
    override var paid_amount: Number
        get() {
            return _tRG(__v_raw, "paid_amount", __v_raw.paid_amount, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("paid_amount")) {
                return
            }
            val oldValue = __v_raw.paid_amount
            __v_raw.paid_amount = value
            _tRS(__v_raw, "paid_amount", oldValue, value)
        }
    override var is_active: Boolean
        get() {
            return _tRG(__v_raw, "is_active", __v_raw.is_active, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("is_active")) {
                return
            }
            val oldValue = __v_raw.is_active
            __v_raw.is_active = value
            _tRS(__v_raw, "is_active", oldValue, value)
        }
    override var files_count: Number
        get() {
            return _tRG(__v_raw, "files_count", __v_raw.files_count, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("files_count")) {
                return
            }
            val oldValue = __v_raw.files_count
            __v_raw.files_count = value
            _tRS(__v_raw, "files_count", oldValue, value)
        }
    override var company_infos: UTSArray<UTSJSONObject>
        get() {
            return _tRG(__v_raw, "company_infos", __v_raw.company_infos, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("company_infos")) {
                return
            }
            val oldValue = __v_raw.company_infos
            __v_raw.company_infos = value
            _tRS(__v_raw, "company_infos", oldValue, value)
        }
    override var is_deleted: Boolean
        get() {
            return _tRG(__v_raw, "is_deleted", __v_raw.is_deleted, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("is_deleted")) {
                return
            }
            val oldValue = __v_raw.is_deleted
            __v_raw.is_deleted = value
            _tRS(__v_raw, "is_deleted", oldValue, value)
        }
    override var created_at: String
        get() {
            return _tRG(__v_raw, "created_at", __v_raw.created_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("created_at")) {
                return
            }
            val oldValue = __v_raw.created_at
            __v_raw.created_at = value
            _tRS(__v_raw, "created_at", oldValue, value)
        }
    override var updated_at: String
        get() {
            return _tRG(__v_raw, "updated_at", __v_raw.updated_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("updated_at")) {
                return
            }
            val oldValue = __v_raw.updated_at
            __v_raw.updated_at = value
            _tRS(__v_raw, "updated_at", oldValue, value)
        }
    override var media_files: UTSArray<SupplierMediaFile>
        get() {
            return _tRG(__v_raw, "media_files", __v_raw.media_files, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("media_files")) {
                return
            }
            val oldValue = __v_raw.media_files
            __v_raw.media_files = value
            _tRS(__v_raw, "media_files", oldValue, value)
        }
}
open class SupplierListResponse (
    @JsonNotNull
    open var results: UTSArray<SupplierItem>,
    @JsonNotNull
    open var count: Number,
    @JsonNotNull
    open var total_count: Number,
    @JsonNotNull
    open var total_pages: Number,
    @JsonNotNull
    open var current_page: Number,
    @JsonNotNull
    open var page_size: Number,
) : UTSObject()
open class SupplierFilterOption (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var label: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return SupplierFilterOptionReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class SupplierFilterOptionReactiveObject : SupplierFilterOption, IUTSReactive<SupplierFilterOption> {
    override var __v_raw: SupplierFilterOption
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: SupplierFilterOption, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(value = __v_raw.value, label = __v_raw.label) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): SupplierFilterOptionReactiveObject {
        return SupplierFilterOptionReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var value: String
        get() {
            return _tRG(__v_raw, "value", __v_raw.value, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("value")) {
                return
            }
            val oldValue = __v_raw.value
            __v_raw.value = value
            _tRS(__v_raw, "value", oldValue, value)
        }
    override var label: String
        get() {
            return _tRG(__v_raw, "label", __v_raw.label, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("label")) {
                return
            }
            val oldValue = __v_raw.label
            __v_raw.label = value
            _tRS(__v_raw, "label", oldValue, value)
        }
}
open class SupplierFilterDefinition (
    @JsonNotNull
    open var key: String,
    @JsonNotNull
    open var param: String,
    @JsonNotNull
    open var label: String,
    @JsonNotNull
    open var control: String,
    @JsonNotNull
    open var aliases: UTSArray<String>,
    @JsonNotNull
    open var multiple: Boolean = false,
    @JsonNotNull
    open var options: UTSArray<SupplierFilterOption>,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return SupplierFilterDefinitionReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class SupplierFilterDefinitionReactiveObject : SupplierFilterDefinition, IUTSReactive<SupplierFilterDefinition> {
    override var __v_raw: SupplierFilterDefinition
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: SupplierFilterDefinition, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(key = __v_raw.key, param = __v_raw.param, label = __v_raw.label, control = __v_raw.control, aliases = __v_raw.aliases, multiple = __v_raw.multiple, options = __v_raw.options) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): SupplierFilterDefinitionReactiveObject {
        return SupplierFilterDefinitionReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var key: String
        get() {
            return _tRG(__v_raw, "key", __v_raw.key, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("key")) {
                return
            }
            val oldValue = __v_raw.key
            __v_raw.key = value
            _tRS(__v_raw, "key", oldValue, value)
        }
    override var param: String
        get() {
            return _tRG(__v_raw, "param", __v_raw.param, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("param")) {
                return
            }
            val oldValue = __v_raw.param
            __v_raw.param = value
            _tRS(__v_raw, "param", oldValue, value)
        }
    override var label: String
        get() {
            return _tRG(__v_raw, "label", __v_raw.label, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("label")) {
                return
            }
            val oldValue = __v_raw.label
            __v_raw.label = value
            _tRS(__v_raw, "label", oldValue, value)
        }
    override var control: String
        get() {
            return _tRG(__v_raw, "control", __v_raw.control, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("control")) {
                return
            }
            val oldValue = __v_raw.control
            __v_raw.control = value
            _tRS(__v_raw, "control", oldValue, value)
        }
    override var aliases: UTSArray<String>
        get() {
            return _tRG(__v_raw, "aliases", __v_raw.aliases, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("aliases")) {
                return
            }
            val oldValue = __v_raw.aliases
            __v_raw.aliases = value
            _tRS(__v_raw, "aliases", oldValue, value)
        }
    override var multiple: Boolean
        get() {
            return _tRG(__v_raw, "multiple", __v_raw.multiple, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("multiple")) {
                return
            }
            val oldValue = __v_raw.multiple
            __v_raw.multiple = value
            _tRS(__v_raw, "multiple", oldValue, value)
        }
    override var options: UTSArray<SupplierFilterOption>
        get() {
            return _tRG(__v_raw, "options", __v_raw.options, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("options")) {
                return
            }
            val oldValue = __v_raw.options
            __v_raw.options = value
            _tRS(__v_raw, "options", oldValue, value)
        }
}
open class SupplierFilterOptionsResponse (
    @JsonNotNull
    open var resource: String,
    @JsonNotNull
    open var count: Number,
    @JsonNotNull
    open var filters: UTSArray<SupplierFilterDefinition>,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return SupplierFilterOptionsResponseReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class SupplierFilterOptionsResponseReactiveObject : SupplierFilterOptionsResponse, IUTSReactive<SupplierFilterOptionsResponse> {
    override var __v_raw: SupplierFilterOptionsResponse
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: SupplierFilterOptionsResponse, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(resource = __v_raw.resource, count = __v_raw.count, filters = __v_raw.filters) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): SupplierFilterOptionsResponseReactiveObject {
        return SupplierFilterOptionsResponseReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var resource: String
        get() {
            return _tRG(__v_raw, "resource", __v_raw.resource, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("resource")) {
                return
            }
            val oldValue = __v_raw.resource
            __v_raw.resource = value
            _tRS(__v_raw, "resource", oldValue, value)
        }
    override var count: Number
        get() {
            return _tRG(__v_raw, "count", __v_raw.count, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("count")) {
                return
            }
            val oldValue = __v_raw.count
            __v_raw.count = value
            _tRS(__v_raw, "count", oldValue, value)
        }
    override var filters: UTSArray<SupplierFilterDefinition>
        get() {
            return _tRG(__v_raw, "filters", __v_raw.filters, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("filters")) {
                return
            }
            val oldValue = __v_raw.filters
            __v_raw.filters = value
            _tRS(__v_raw, "filters", oldValue, value)
        }
}
open class SupplierGlobalStatisticsResponse (
    @JsonNotNull
    open var data: UTSJSONObject,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return SupplierGlobalStatisticsResponseReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class SupplierGlobalStatisticsResponseReactiveObject : SupplierGlobalStatisticsResponse, IUTSReactive<SupplierGlobalStatisticsResponse> {
    override var __v_raw: SupplierGlobalStatisticsResponse
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: SupplierGlobalStatisticsResponse, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(data = __v_raw.data) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): SupplierGlobalStatisticsResponseReactiveObject {
        return SupplierGlobalStatisticsResponseReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var data: UTSJSONObject
        get() {
            return _tRG(__v_raw, "data", __v_raw.data, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("data")) {
                return
            }
            val oldValue = __v_raw.data
            __v_raw.data = value
            _tRS(__v_raw, "data", oldValue, value)
        }
}
open class SupplierMutationData (
    open var code: String? = null,
    @JsonNotNull
    open var name: String,
    open var address: String? = null,
    open var phone: String? = null,
    open var contact: String? = null,
    open var description: String? = null,
    open var is_active: Boolean? = null,
    open var company_infos: UTSArray<UTSJSONObject>? = null,
) : UTSObject()
open class SupplierBatchActionResponse (
    @JsonNotNull
    open var success: Boolean = false,
    @JsonNotNull
    open var message: String,
    @JsonNotNull
    open var data: UTSJSONObject,
) : UTSObject()
fun buildListQuery__1(data: SupplierListQuery): UTSJSONObject {
    val query: UTSJSONObject = _uO("page" to data.page, "page_size" to data.page_size)
    if (data.search != null && data.search != "") {
        query["search"] = data.search
    }
    if (data.is_active != null && data.is_active != "") {
        query["is_active"] = data.is_active
    }
    if (data.has_arrears != null && data.has_arrears != "") {
        query["has_arrears"] = data.has_arrears
    }
    return query
}
fun normalizeServerUrl__1(url: String): String {
    if (url == "") {
        return ""
    }
    if (url.startsWith("http://localhost:8000")) {
        return baseUrl + url.substring(21)
    }
    if (url.startsWith("https://localhost:8000")) {
        return baseUrl + url.substring(22)
    }
    if (url.startsWith("http://127.0.0.1:8000")) {
        return baseUrl + url.substring(21)
    }
    if (url.startsWith("https://127.0.0.1:8000")) {
        return baseUrl + url.substring(22)
    }
    return url
}
fun normalizeSupplierList(data: SupplierListResponse): SupplierListResponse {
    run {
        var supplierIndex: Number = 0
        while(supplierIndex < data.results.length){
            val supplier = data.results[supplierIndex]
            normalizeSupplierMediaFiles(supplier.media_files)
            supplierIndex += 1
        }
    }
    return data
}
fun normalizeSupplierMediaFiles(files: UTSArray<SupplierMediaFile>) {
    run {
        var mediaIndex: Number = 0
        while(mediaIndex < files.length){
            val mediaFile = files[mediaIndex]
            mediaFile.file_url = normalizeServerUrl__1(mediaFile.file_url)
            mediaFile.thumbnail_url = normalizeServerUrl__1(mediaFile.thumbnail_url)
            mediaFile.signed_url = normalizeServerUrl__1(mediaFile.signed_url)
            mediaFile.signed_thumbnail_url = normalizeServerUrl__1(mediaFile.signed_thumbnail_url)
            mediaIndex += 1
        }
    }
}
fun intValue__2(value: Any?): Number {
    if (value == null) {
        return 0
    }
    val text = "" + value
    if (text == "") {
        return 0
    }
    val parsed = parseInt(text)
    if (isNaN(parsed)) {
        return 0
    }
    return parsed
}
fun stringValue__2(value: Any?): String {
    if (value == null) {
        return ""
    }
    return "" + value
}
fun buildSupplierListResponse(raw: Any, query: SupplierListQuery): SupplierListResponse {
    val rawText = JSON.stringify(raw)
    val rawObject = if (rawText == null || rawText == "") {
        null
    } else {
        JSON.parseObject<UTSJSONObject>(rawText)
    }
    if (rawObject == null) {
        throw UTSError("供应商列表响应解析失败")
    }
    var paginationObject: UTSJSONObject? = null
    val rawPagination = rawObject!!["pagination"]
    if (rawPagination != null) {
        val paginationText = JSON.stringify(rawPagination)
        if (paginationText != null && paginationText != "") {
            paginationObject = JSON.parseObject<UTSJSONObject>(paginationText)
        }
    }
    var results: UTSArray<SupplierItem> = _uA()
    val rawResults = rawObject!!["results"]
    if (rawResults != null) {
        val resultText = JSON.stringify(rawResults)
        val parsedResults = if (resultText == null || resultText == "") {
            null
        } else {
            JSON.parseArray<SupplierItem>(resultText)
        }
        if (parsedResults != null) {
            results = parsedResults!!
        }
    }
    var totalCount = intValue__2(rawObject!!["count"])
    if (totalCount <= 0) {
        totalCount = intValue__2(rawObject!!["total"])
    }
    if (totalCount <= 0) {
        totalCount = intValue__2(rawObject!!["total_count"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__2(paginationObject["total"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__2(paginationObject["count"])
    }
    if (totalCount <= 0) {
        totalCount = results.length
    }
    var currentPage = intValue__2(rawObject!!["page"])
    if (currentPage <= 0) {
        currentPage = intValue__2(rawObject!!["current_page"])
    }
    if (currentPage <= 0 && paginationObject != null) {
        currentPage = intValue__2(paginationObject["page"])
    }
    if (currentPage <= 0) {
        currentPage = query.page
    }
    var pageSize = intValue__2(rawObject!!["page_size"])
    if (pageSize <= 0) {
        pageSize = intValue__2(rawObject!!["per_page"])
    }
    if (pageSize <= 0 && paginationObject != null) {
        pageSize = intValue__2(paginationObject["page_size"])
    }
    if (pageSize <= 0 && paginationObject != null) {
        pageSize = intValue__2(paginationObject["per_page"])
    }
    if (pageSize <= 0) {
        pageSize = query.page_size
    }
    var totalPages = intValue__2(rawObject!!["total_pages"])
    if (totalPages <= 0) {
        totalPages = intValue__2(rawObject!!["num_pages"])
    }
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__2(paginationObject["total_pages"])
    }
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__2(paginationObject["num_pages"])
    }
    if (totalPages <= 0 && pageSize > 0) {
        totalPages = Math.ceil(totalCount / pageSize)
    }
    if (totalPages <= 0) {
        totalPages = 1
    }
    return SupplierListResponse(results = results, count = totalCount, total_count = totalCount, total_pages = totalPages, current_page = currentPage, page_size = pageSize)
}
fun stringArrayValue__1(value: Any?): UTSArray<String> {
    if (value == null) {
        return _uA()
    }
    val text = JSON.stringify(value)
    val parsed = if (text == null || text == "") {
        null
    } else {
        JSON.parseArray<Any>(text)
    }
    if (parsed == null) {
        return _uA()
    }
    val result: UTSArray<String> = _uA()
    run {
        var index: Number = 0
        while(index < parsed!!.length){
            result.push(stringValue__2(parsed!![index]))
            index += 1
        }
    }
    return result
}
fun buildSupplierFilterOptionsResponse(raw: Any): SupplierFilterOptionsResponse {
    val rawText = JSON.stringify(raw)
    val rawObject = if (rawText == null || rawText == "") {
        null
    } else {
        JSON.parseObject<UTSJSONObject>(rawText)
    }
    if (rawObject == null) {
        throw UTSError("供应商过滤选项解析失败")
    }
    var filters: UTSArray<SupplierFilterDefinition> = _uA()
    val rawFilters = rawObject!!["filters"]
    if (rawFilters != null) {
        val filtersText = JSON.stringify(rawFilters)
        val filterObjects = if (filtersText == null || filtersText == "") {
            null
        } else {
            JSON.parseArray<UTSJSONObject>(filtersText)
        }
        if (filterObjects != null) {
            val nextFilters: UTSArray<SupplierFilterDefinition> = _uA()
            run {
                var filterIndex: Number = 0
                while(filterIndex < filterObjects!!.length){
                    val filterObject = filterObjects!![filterIndex]
                    var options: UTSArray<SupplierFilterOption> = _uA()
                    val rawOptions = filterObject["options"]
                    if (rawOptions != null) {
                        val optionsText = JSON.stringify(rawOptions)
                        val optionObjects = if (optionsText == null || optionsText == "") {
                            null
                        } else {
                            JSON.parseArray<UTSJSONObject>(optionsText)
                        }
                        if (optionObjects != null) {
                            val nextOptions: UTSArray<SupplierFilterOption> = _uA()
                            run {
                                var optionIndex: Number = 0
                                while(optionIndex < optionObjects!!.length){
                                    val optionObject = optionObjects!![optionIndex]
                                    nextOptions.push(SupplierFilterOption(value = stringValue__2(optionObject["value"]), label = stringValue__2(optionObject["label"])))
                                    optionIndex += 1
                                }
                            }
                            options = nextOptions
                        }
                    }
                    nextFilters.push(SupplierFilterDefinition(key = stringValue__2(filterObject["key"]), param = stringValue__2(filterObject["param"]), label = stringValue__2(filterObject["label"]), control = stringValue__2(filterObject["control"]), aliases = stringArrayValue__1(filterObject["aliases"]), multiple = stringValue__2(filterObject["multiple"]) == "true", options = options))
                    filterIndex += 1
                }
            }
            filters = nextFilters
        }
    }
    return SupplierFilterOptionsResponse(resource = stringValue__2(rawObject!!["resource"]), count = intValue__2(rawObject!!["count"]), filters = filters)
}
fun buildSupplierMediaFileFromObject(rawObject: UTSJSONObject): SupplierMediaFile {
    return SupplierMediaFile(id = stringValue__2(rawObject["id"]), company = intValue__2(rawObject["company"]), original_filename = stringValue__2(rawObject["original_filename"]), file_type = stringValue__2(rawObject["file_type"]), file_type_display = stringValue__2(rawObject["file_type_display"]), mime_type = stringValue__2(rawObject["mime_type"]), file_size = intValue__2(rawObject["file_size"]), file_size_display = stringValue__2(rawObject["file_size_display"]), file_url = normalizeServerUrl__1(stringValue__2(rawObject["file_url"])), thumbnail_url = normalizeServerUrl__1(stringValue__2(rawObject["thumbnail_url"])), signed_url = normalizeServerUrl__1(stringValue__2(rawObject["signed_url"])), signed_thumbnail_url = normalizeServerUrl__1(stringValue__2(rawObject["signed_thumbnail_url"])), object_id = stringValue__2(rawObject["object_id"]), is_deleted = stringValue__2(rawObject["is_deleted"]) == "true", created_at = stringValue__2(rawObject["created_at"]), updated_at = stringValue__2(rawObject["updated_at"]))
}
fun buildSupplierMediaFilesFromValue(value: Any?): UTSArray<SupplierMediaFile> {
    if (value == null) {
        return _uA()
    }
    val text = JSON.stringify(value)
    val rawArray = if (text == null || text == "") {
        null
    } else {
        JSON.parseArray<UTSJSONObject>(text)
    }
    if (rawArray == null) {
        return _uA()
    }
    val result: UTSArray<SupplierMediaFile> = _uA()
    run {
        var index: Number = 0
        while(index < rawArray!!.length){
            result.push(buildSupplierMediaFileFromObject(rawArray!![index]))
            index += 1
        }
    }
    return result
}
fun buildSupplierItemResponse(raw: Any): SupplierItem {
    val rawText = JSON.stringify(raw)
    val rawObject = if (rawText == null || rawText == "") {
        null
    } else {
        JSON.parseObject<UTSJSONObject>(rawText)
    }
    if (rawObject == null) {
        throw UTSError("供应商详情响应解析失败")
    }
    return SupplierItem(id = intValue__2(rawObject!!["id"]), code = stringValue__2(rawObject!!["code"]), name = stringValue__2(rawObject!!["name"]), address = stringValue__2(rawObject!!["address"]), phone = stringValue__2(rawObject!!["phone"]), contact = stringValue__2(rawObject!!["contact"]), description = if (rawObject!!["description"] == null) {
        null
    } else {
        stringValue__2(rawObject!!["description"])
    }
    , total_amount = stringValue__2(rawObject!!["total_amount"]), arrears_amount = stringValue__2(rawObject!!["arrears_amount"]), paid_amount = intValue__2(rawObject!!["paid_amount"]), is_active = stringValue__2(rawObject!!["is_active"]) == "true", files_count = intValue__2(rawObject!!["files_count"]), company_infos = (fun(): UTSArray<UTSJSONObject> {
        val companyInfosValue = rawObject!!["company_infos"]
        if (companyInfosValue == null) {
            return _uA<UTSJSONObject>()
        }
        val companyInfosText = JSON.stringify(companyInfosValue)
        val companyInfosArray = if (companyInfosText == null || companyInfosText == "") {
            null
        } else {
            JSON.parseArray<UTSJSONObject>(companyInfosText)
        }
        if (companyInfosArray == null) {
            return _uA<UTSJSONObject>()
        }
        return companyInfosArray!!
    }
    )(), is_deleted = stringValue__2(rawObject!!["is_deleted"]) == "true", created_at = stringValue__2(rawObject!!["created_at"]), updated_at = stringValue__2(rawObject!!["updated_at"]), media_files = buildSupplierMediaFilesFromValue(rawObject!!["media_files"]))
}
fun buildSupplierGlobalStatisticsResponse(raw: Any): SupplierGlobalStatisticsResponse {
    val rawText = JSON.stringify(raw)
    val rawObject = if (rawText == null || rawText == "") {
        null
    } else {
        JSON.parseObject<UTSJSONObject>(rawText)
    }
    if (rawObject == null) {
        throw UTSError("供应商全局统计解析失败")
    }
    return SupplierGlobalStatisticsResponse(data = rawObject!!)
}
fun buildSupplierMutationBody(data: SupplierMutationData): UTSJSONObject {
    val body: UTSJSONObject = _uO("name" to data.name)
    if (data.code != null) {
        body["code"] = data.code
    }
    if (data.address != null) {
        body["address"] = data.address
    }
    if (data.phone != null) {
        body["phone"] = data.phone
    }
    if (data.contact != null) {
        body["contact"] = data.contact
    }
    if (data.description != null) {
        body["description"] = data.description
    }
    if (data.is_active != null) {
        body["is_active"] = data.is_active
    }
    if (data.company_infos != null) {
        body["company_infos"] = data.company_infos
    }
    return body
}
fun supplierDetailPath(id: Any): String {
    return "/api/procurement/suppliers/" + stringValue__2(id) + "/"
}
fun buildBatchActionBody(ids: UTSArray<String>, remark: String? = null): UTSJSONObject {
    val nextIds: UTSArray<Any> = _uA()
    run {
        var index: Number = 0
        while(index < ids.length){
            val text = stringValue__2(ids[index])
            val parsed = parseInt(text)
            if (!isNaN(parsed) && "" + parsed == text) {
                nextIds.push(parsed)
            } else {
                nextIds.push(text)
            }
            index += 1
        }
    }
    val body: UTSJSONObject = _uO("ids" to nextIds)
    if (remark != null && remark != "") {
        body["remark"] = remark
    }
    return body
}
fun supplierBatchActionPath(action: String): String {
    return "/api/procurement/suppliers/batch-actions/" + action + "/"
}
fun buildSupplierBatchActionResponse(raw: Any): SupplierBatchActionResponse {
    val rawText = JSON.stringify(raw)
    val rawObject = if (rawText == null || rawText == "") {
        null
    } else {
        JSON.parseObject<UTSJSONObject>(rawText)
    }
    if (rawObject == null) {
        return SupplierBatchActionResponse(success = true, message = "操作成功", data = _uO())
    }
    return SupplierBatchActionResponse(success = true, message = stringValue__2(rawObject["message"]), data = rawObject)
}
fun getSupplierList(data: SupplierListQuery): UTSPromise<SupplierListResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/procurement/suppliers/", "GET", buildListQuery__1(data), true))
            return@w normalizeSupplierList(buildSupplierListResponse(raw, data))
    })
}
fun getSupplierFilterOptions(): UTSPromise<SupplierFilterOptionsResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/procurement/suppliers/filter-options/", "GET", _uO(), true))
            return@w buildSupplierFilterOptionsResponse(raw)
    })
}
fun getSupplierDetail(id: Any): UTSPromise<SupplierItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(supplierDetailPath(id), "GET", _uO(), true))
            return@w buildSupplierItemResponse(raw)
    })
}
fun createSupplier(data: SupplierMutationData): UTSPromise<SupplierItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/procurement/suppliers/", "POST", buildSupplierMutationBody(data), true))
            return@w buildSupplierItemResponse(raw)
    })
}
fun updateSupplier(id: Any, data: SupplierMutationData): UTSPromise<SupplierItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(supplierDetailPath(id), "PUT", buildSupplierMutationBody(data), true))
            return@w buildSupplierItemResponse(raw)
    })
}
fun deleteSupplier(id: Any): UTSPromise<Any> {
    return request(supplierDetailPath(id), "DELETE", _uO(), true)
}
fun getSupplierGlobalStatistics(): UTSPromise<SupplierGlobalStatisticsResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/procurement/suppliers/global_statistics/", "GET", _uO(), true))
            return@w buildSupplierGlobalStatisticsResponse(raw)
    })
}
fun batchActivateSuppliers(ids: UTSArray<String>, remark: String? = null): UTSPromise<SupplierBatchActionResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(supplierBatchActionPath("activate"), "POST", buildBatchActionBody(ids, remark), true))
            return@w buildSupplierBatchActionResponse(raw)
    })
}
fun batchDeactivateSuppliers(ids: UTSArray<String>, remark: String? = null): UTSPromise<SupplierBatchActionResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(supplierBatchActionPath("deactivate"), "POST", buildBatchActionBody(ids, remark), true))
            return@w buildSupplierBatchActionResponse(raw)
    })
}
fun batchDeleteSuppliers(ids: UTSArray<String>, remark: String? = null): UTSPromise<SupplierBatchActionResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(supplierBatchActionPath("delete"), "POST", buildBatchActionBody(ids, remark), true))
            return@w buildSupplierBatchActionResponse(raw)
    })
}
val GenPagesSuppliersIndexClass = CreateVueComponent(GenPagesSuppliersIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesSuppliersIndex.inheritAttrs, inject = GenPagesSuppliersIndex.inject, props = GenPagesSuppliersIndex.props, propsNeedCastKeys = GenPagesSuppliersIndex.propsNeedCastKeys, emits = GenPagesSuppliersIndex.emits, components = GenPagesSuppliersIndex.components, styles = GenPagesSuppliersIndex.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesSuppliersIndex.setup(props as GenPagesSuppliersIndex)
    }
    )
}
, fun(instance, renderer): GenPagesSuppliersIndex {
    return GenPagesSuppliersIndex(instance, renderer)
}
)
val GenUniModulesLiliDataComponentsLiliDataLiliDataClass = CreateVueComponent(GenUniModulesLiliDataComponentsLiliDataLiliData::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "component", name = "", inheritAttrs = GenUniModulesLiliDataComponentsLiliDataLiliData.inheritAttrs, inject = GenUniModulesLiliDataComponentsLiliDataLiliData.inject, props = GenUniModulesLiliDataComponentsLiliDataLiliData.props, propsNeedCastKeys = GenUniModulesLiliDataComponentsLiliDataLiliData.propsNeedCastKeys, emits = GenUniModulesLiliDataComponentsLiliDataLiliData.emits, components = GenUniModulesLiliDataComponentsLiliDataLiliData.components, styles = GenUniModulesLiliDataComponentsLiliDataLiliData.styles, setup = fun(props: ComponentPublicInstance, ctx: SetupContext): Any? {
        return GenUniModulesLiliDataComponentsLiliDataLiliData.setup(props as GenUniModulesLiliDataComponentsLiliDataLiliData, ctx)
    }
    )
}
, fun(instance, renderer): GenUniModulesLiliDataComponentsLiliDataLiliData {
    return GenUniModulesLiliDataComponentsLiliDataLiliData(instance)
}
)
val GenUniModulesLiliPopupComponentsLiliPopupLiliPopupClass = CreateVueComponent(GenUniModulesLiliPopupComponentsLiliPopupLiliPopup::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "component", name = "", inheritAttrs = GenUniModulesLiliPopupComponentsLiliPopupLiliPopup.inheritAttrs, inject = GenUniModulesLiliPopupComponentsLiliPopupLiliPopup.inject, props = GenUniModulesLiliPopupComponentsLiliPopupLiliPopup.props, propsNeedCastKeys = GenUniModulesLiliPopupComponentsLiliPopupLiliPopup.propsNeedCastKeys, emits = GenUniModulesLiliPopupComponentsLiliPopupLiliPopup.emits, components = GenUniModulesLiliPopupComponentsLiliPopupLiliPopup.components, styles = GenUniModulesLiliPopupComponentsLiliPopupLiliPopup.styles, setup = fun(props: ComponentPublicInstance, ctx: SetupContext): Any? {
        return GenUniModulesLiliPopupComponentsLiliPopupLiliPopup.setup(props as GenUniModulesLiliPopupComponentsLiliPopupLiliPopup, ctx)
    }
    )
}
, fun(instance, renderer): GenUniModulesLiliPopupComponentsLiliPopupLiliPopup {
    return GenUniModulesLiliPopupComponentsLiliPopupLiliPopup(instance)
}
)
open class MediaBatchUploadItem (
    @JsonNotNull
    open var filePath: String,
    open var name: String? = null,
    open var formData: UTSJSONObject? = null,
) : UTSObject()
open class MediaBatchUploadResult (
    @JsonNotNull
    open var successItems: UTSArray<UTSJSONObject>,
    @JsonNotNull
    open var failItems: UTSArray<UTSJSONObject>,
) : UTSObject()
fun stringValue__3(value: Any?): String {
    if (value == null) {
        return ""
    }
    return "" + value
}
fun mediaFilePath(id: Any): String {
    return "/api/media/files/" + stringValue__3(id) + "/"
}
fun buildUploadHeaders(): UTSJSONObject {
    val headers: UTSJSONObject = _uO()
    if (authState.token != "") {
        headers["Authorization"] = authState.token
    }
    return headers
}
fun parseResponseErrorMessage(text: String): String {
    if (text == "") {
        return ""
    }
    val rootObject = JSON.parseObject<UTSJSONObject>(text)
    if (rootObject == null) {
        return ""
    }
    val detailMessage = stringValue__3(rootObject["detail"])
    if (detailMessage != "") {
        return detailMessage
    }
    val message = stringValue__3(rootObject["message"])
    if (message != "") {
        return message
    }
    return ""
}
fun cloneObject(source: UTSJSONObject): UTSJSONObject {
    val target: UTSJSONObject = _uO()
    for(key in resolveUTSKeyIterator(source)){
        target[key] = source[key]
    }
    return target
}
fun buildBatchUploadFormData(items: UTSArray<MediaBatchUploadItem>): UTSJSONObject {
    val result: UTSJSONObject = _uO()
    var initialized = false
    run {
        var index: Number = 0
        while(index < items.length){
            val itemFormData = items[index].formData
            if (itemFormData == null) {
                index += 1
                continue
            }
            if (!initialized) {
                val cloned = cloneObject(itemFormData!!)
                for(key in resolveUTSKeyIterator(cloned)){
                    result[key] = cloned[key]
                }
                initialized = true
                index += 1
                continue
            }
            val currentContentTypeModel = stringValue__3(itemFormData!!["content_type_model"]).trim()
            val currentObjectId = stringValue__3(itemFormData!!["object_id"]).trim()
            val currentCompanyId = stringValue__3(itemFormData!!["company_id"]).trim()
            val baseContentTypeModel = stringValue__3(result["content_type_model"]).trim()
            val baseObjectId = stringValue__3(result["object_id"]).trim()
            val baseCompanyId = stringValue__3(result["company_id"]).trim()
            if (currentContentTypeModel != "" && baseContentTypeModel != "" && currentContentTypeModel != baseContentTypeModel) {
                throw UTSError("批量上传参数冲突: content_type_model 不一致")
            }
            if (currentObjectId != "" && baseObjectId != "" && currentObjectId != baseObjectId) {
                throw UTSError("批量上传参数冲突: object_id 不一致")
            }
            if (currentCompanyId != "" && baseCompanyId != "" && currentCompanyId != baseCompanyId) {
                throw UTSError("批量上传参数冲突: company_id 不一致")
            }
            for(key in resolveUTSKeyIterator(itemFormData!!)){
                if (result[key] == null || stringValue__3(result[key]).trim() == "") {
                    result[key] = itemFormData!![key]
                }
            }
            index += 1
        }
    }
    val contentTypeModel = stringValue__3(result["content_type_model"]).trim()
    val objectId = stringValue__3(result["object_id"]).trim()
    if (contentTypeModel == "" || objectId == "") {
        throw UTSError("批量上传缺少必填参数: content_type_model 和 object_id")
    }
    return result
}
fun parseBatchUploadResponseText(text: String): UTSArray<UTSJSONObject> {
    if (text == "") {
        return _uA()
    }
    val rootObject = JSON.parseObject<UTSJSONObject>(text)
    if (rootObject == null) {
        throw UTSError("批量上传响应解析失败")
    }
    val successValue = rootObject["success"]
    if (successValue != null) {
        val successText = stringValue__3(successValue)
        if (successText != "true") {
            var message = stringValue__3(rootObject["message"])
            if (message == "") {
                message = stringValue__3(rootObject["detail"])
            }
            throw UTSError(if (message == "") {
                "批量上传失败"
            } else {
                message
            }
            )
        }
        return extractUploadedItems(rootObject["data"])
    }
    return extractUploadedItems(rootObject)
}
fun tryParseObject(text: String): UTSJSONObject? {
    if (text == "") {
        return null
    }
    try {
        return JSON.parseObject<UTSJSONObject>(text)
    }
     catch (error: Throwable) {
        return null
    }
}
fun tryParseArray(text: String): UTSArray<UTSJSONObject>? {
    if (text == "") {
        return null
    }
    try {
        return JSON.parseArray<UTSJSONObject>(text)
    }
     catch (error: Throwable) {
        return null
    }
}
fun firstJsonToken(text: String): String {
    run {
        var index: Number = 0
        while(index < text.length){
            val char = text.substring(index, index + 1)
            if (char == " " || char == "\n" || char == "\r" || char == "\t") {
                index += 1
                continue
            }
            return char
            index += 1
        }
    }
    return ""
}
fun extractUploadedItems(value: Any?): UTSArray<UTSJSONObject> {
    if (value == null) {
        return _uA()
    }
    val valueText = JSON.stringify(value)
    if (valueText == null || valueText == "") {
        return _uA()
    }
    val token = firstJsonToken(valueText)
    if (token == "[") {
        val uploadedArray = tryParseArray(valueText)
        if (uploadedArray != null) {
            return uploadedArray!!
        }
    }
    if (token != "{") {
        return _uA()
    }
    val valueObject = tryParseObject(valueText)
    if (valueObject == null) {
        return _uA()
    }
    val uploadedValue = valueObject["uploaded"]
    if (uploadedValue != null) {
        val uploadedText = JSON.stringify(uploadedValue)
        if (uploadedText != null && uploadedText != "") {
            val uploadedToken = firstJsonToken(uploadedText)
            if (uploadedToken == "[") {
                val parsedUploadedArray = tryParseArray(uploadedText)
                if (parsedUploadedArray != null) {
                    return parsedUploadedArray!!
                }
            }
        }
    }
    if (valueObject["id"] != null || valueObject["original_filename"] != null || valueObject["file_url"] != null || valueObject["signed_url"] != null) {
        return _uA(
            valueObject
        )
    }
    val detailMessage = stringValue__3(valueObject["detail"])
    if (detailMessage != "") {
        throw UTSError(detailMessage)
    }
    val message = stringValue__3(valueObject["message"])
    if (message != "") {
        throw UTSError(message)
    }
    return _uA()
}
fun normalizeUploadFilePath(filePath: String): String {
    return filePath.trim()
}
fun buildUploadFailMessage(err: UploadFileFail): String {
    var message = stringValue__3(err.errMsg)
    val rawText = JSON.stringify(err)
    if (rawText != null && rawText != "") {
        val rawObject = JSON.parseObject<UTSJSONObject>(rawText)
        if (rawObject != null) {
            val causeValue = rawObject!!["cause"]
            if (causeValue != null) {
                val causeText = JSON.stringify(causeValue)
                if (causeText != null && causeText != "") {
                    val causeObject = JSON.parseObject<UTSJSONObject>(causeText)
                    if (causeObject != null) {
                        val causeMessage = stringValue__3(causeObject!!["message"])
                        if (causeMessage != "") {
                            message = if (message == "") {
                                causeMessage
                            } else {
                                (message + " | " + causeMessage)
                            }
                        }
                    }
                }
            }
        }
    }
    if (message == "") {
        return "上传失败"
    }
    return message
}
fun uploadBatchMediaFilesRequest(items: UTSArray<MediaBatchUploadItem>, formData: UTSJSONObject): UTSPromise<UTSArray<UTSJSONObject>> {
    return UTSPromise(fun(resolve, reject){
        val headers = buildUploadHeaders()
        val uploadTimeout = if (timeOut < 120000) {
            120000
        } else {
            timeOut
        }
        val files: UTSArray<UploadFileOptionFiles> = _uA()
        run {
            var index: Number = 0
            while(index < items.length){
                val resolvedFilePath = normalizeUploadFilePath(items[index].filePath)
                files.push(UploadFileOptionFiles(name = "files", uri = resolvedFilePath))
                index += 1
            }
        }
        console.log("media batch upload start:", baseUrl + "/api/media/files/batch-upload/", files.length)
        try {
            uni_uploadFile(UploadFileOptions(url = baseUrl + "/api/media/files/batch-upload/", files = files, header = headers, formData = formData, timeout = uploadTimeout, success = fun(res: UploadFileSuccess){
                console.log("media batch upload success:", res.statusCode, items.length)
                if (res.statusCode < 200 || res.statusCode >= 300) {
                    val responseMessage = parseResponseErrorMessage(res.data)
                    reject(UTSError(if (responseMessage == "") {
                        ("HTTP状态码错误: " + res.statusCode)
                    } else {
                        responseMessage
                    }
                    ))
                    return
                }
                try {
                    resolve(parseBatchUploadResponseText(res.data))
                }
                 catch (error: Throwable) {
                    reject(error)
                }
            }
            , fail = fun(err: UploadFileFail){
                val failMessage = buildUploadFailMessage(err)
                console.log("media batch upload fail:", failMessage, err.errCode)
                reject(UTSError(failMessage))
            }
            ))
        }
         catch (error: Throwable) {
            reject(error)
        }
    }
    )
}
fun deleteMediaFileRequest(id: Any): UTSPromise<Boolean> {
    return UTSPromise(fun(resolve, reject){
        val headers = buildUploadHeaders()
        headers["content-type"] = "application/json"
        val requestUrl = baseUrl + mediaFilePath(id)
        console.log("请求地址:", requestUrl)
        uni_request<Any>(RequestOptions(url = requestUrl, method = "DELETE", header = headers, timeout = timeOut, success = fun(res){
            if (res.statusCode == 204 || res.statusCode == 200) {
                resolve(true)
                return
            }
            reject(UTSError("HTTP状态码错误: " + res.statusCode))
        }
        , fail = fun(err){
            reject(UTSError(stringValue__3(err.errMsg)))
        }
        ))
    }
    )
}
fun deleteMediaFile(id: Any): UTSPromise<Boolean> {
    return deleteMediaFileRequest(id)
}
fun batchUploadMediaFiles(items: UTSArray<MediaBatchUploadItem>): UTSPromise<MediaBatchUploadResult> {
    return wrapUTSPromise(suspend w@{
            val successItems: UTSArray<UTSJSONObject> = _uA()
            val failItems: UTSArray<UTSJSONObject> = _uA()
            console.log("media batch upload count:", items.length)
            if (items.length == 0) {
                return@w MediaBatchUploadResult(successItems = successItems, failItems = failItems)
            }
            try {
                val formData = buildBatchUploadFormData(items)
                val uploadedItems = await(uploadBatchMediaFilesRequest(items, formData))
                if (uploadedItems.length == items.length) {
                    run {
                        var index: Number = 0
                        while(index < items.length){
                            successItems.push(_uO("filePath" to items[index].filePath, "result" to uploadedItems[index]))
                            index += 1
                        }
                    }
                } else {
                    val message = "批量上传返回数量异常: 请求 " + items.length + " 个，返回 " + uploadedItems.length + " 个"
                    run {
                        var index: Number = 0
                        while(index < items.length){
                            if (index < uploadedItems.length) {
                                successItems.push(_uO("filePath" to items[index].filePath, "result" to uploadedItems[index]))
                            } else {
                                failItems.push(_uO("filePath" to items[index].filePath, "message" to message))
                            }
                            index += 1
                        }
                    }
                }
            }
             catch (error: Throwable) {
                val message = stringValue__3((error as UTSError).message)
                run {
                    var index: Number = 0
                    while(index < items.length){
                        failItems.push(_uO("filePath" to items[index].filePath, "message" to message))
                        index += 1
                    }
                }
            }
            return@w MediaBatchUploadResult(successItems = successItems, failItems = failItems)
    })
}
val GenUniModulesLiliUploadComponentsLiliUploadLiliUploadClass = CreateVueComponent(GenUniModulesLiliUploadComponentsLiliUploadLiliUpload::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "component", name = "", inheritAttrs = GenUniModulesLiliUploadComponentsLiliUploadLiliUpload.inheritAttrs, inject = GenUniModulesLiliUploadComponentsLiliUploadLiliUpload.inject, props = GenUniModulesLiliUploadComponentsLiliUploadLiliUpload.props, propsNeedCastKeys = GenUniModulesLiliUploadComponentsLiliUploadLiliUpload.propsNeedCastKeys, emits = GenUniModulesLiliUploadComponentsLiliUploadLiliUpload.emits, components = GenUniModulesLiliUploadComponentsLiliUploadLiliUpload.components, styles = GenUniModulesLiliUploadComponentsLiliUploadLiliUpload.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenUniModulesLiliUploadComponentsLiliUploadLiliUpload.setup(props as GenUniModulesLiliUploadComponentsLiliUploadLiliUpload)
    }
    )
}
, fun(instance, renderer): GenUniModulesLiliUploadComponentsLiliUploadLiliUpload {
    return GenUniModulesLiliUploadComponentsLiliUploadLiliUpload(instance)
}
)
typealias FetchDataFn = (params: UTSJSONObject) -> UTSPromise<UTSJSONObject>
typealias ValidatorFn = (value: Any, formData: UTSJSONObject, mode: String) -> String
val GenUniModulesLiliUniversaFormComponentsLiliUniversaFormLiliUniversaFormClass = CreateVueComponent(GenUniModulesLiliUniversaFormComponentsLiliUniversaFormLiliUniversaForm::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "component", name = "", inheritAttrs = GenUniModulesLiliUniversaFormComponentsLiliUniversaFormLiliUniversaForm.inheritAttrs, inject = GenUniModulesLiliUniversaFormComponentsLiliUniversaFormLiliUniversaForm.inject, props = GenUniModulesLiliUniversaFormComponentsLiliUniversaFormLiliUniversaForm.props, propsNeedCastKeys = GenUniModulesLiliUniversaFormComponentsLiliUniversaFormLiliUniversaForm.propsNeedCastKeys, emits = GenUniModulesLiliUniversaFormComponentsLiliUniversaFormLiliUniversaForm.emits, components = GenUniModulesLiliUniversaFormComponentsLiliUniversaFormLiliUniversaForm.components, styles = GenUniModulesLiliUniversaFormComponentsLiliUniversaFormLiliUniversaForm.styles, setup = fun(props: ComponentPublicInstance, ctx: SetupContext): Any? {
        return GenUniModulesLiliUniversaFormComponentsLiliUniversaFormLiliUniversaForm.setup(props as GenUniModulesLiliUniversaFormComponentsLiliUniversaFormLiliUniversaForm, ctx)
    }
    )
}
, fun(instance, renderer): GenUniModulesLiliUniversaFormComponentsLiliUniversaFormLiliUniversaForm {
    return GenUniModulesLiliUniversaFormComponentsLiliUniversaFormLiliUniversaForm(instance)
}
)
open class SelectOption (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var text: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return SelectOptionReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class SelectOptionReactiveObject : SelectOption, IUTSReactive<SelectOption> {
    override var __v_raw: SelectOption
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: SelectOption, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(value = __v_raw.value, text = __v_raw.text) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): SelectOptionReactiveObject {
        return SelectOptionReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var value: String
        get() {
            return _tRG(__v_raw, "value", __v_raw.value, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("value")) {
                return
            }
            val oldValue = __v_raw.value
            __v_raw.value = value
            _tRS(__v_raw, "value", oldValue, value)
        }
    override var text: String
        get() {
            return _tRG(__v_raw, "text", __v_raw.text, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("text")) {
                return
            }
            val oldValue = __v_raw.text
            __v_raw.text = value
            _tRS(__v_raw, "text", oldValue, value)
        }
}
val GenPagesSuppliersFromClass = CreateVueComponent(GenPagesSuppliersFrom::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesSuppliersFrom.inheritAttrs, inject = GenPagesSuppliersFrom.inject, props = GenPagesSuppliersFrom.props, propsNeedCastKeys = GenPagesSuppliersFrom.propsNeedCastKeys, emits = GenPagesSuppliersFrom.emits, components = GenPagesSuppliersFrom.components, styles = GenPagesSuppliersFrom.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesSuppliersFrom.setup(props as GenPagesSuppliersFrom)
    }
    )
}
, fun(instance, renderer): GenPagesSuppliersFrom {
    return GenPagesSuppliersFrom(instance, renderer)
}
)
open class TransactionListQuery (
    open var search: String? = null,
    @JsonNotNull
    open var page: Number,
    @JsonNotNull
    open var page_size: Number,
    open var transaction_type: String? = null,
    open var supplier: String? = null,
    open var supplier_id: String? = null,
    open var date_from: String? = null,
    open var start_date: String? = null,
    open var date_to: String? = null,
    open var end_date: String? = null,
    open var amount_min: String? = null,
    open var amount_max: String? = null,
    open var ordering: String? = null,
    open var sort_by: String? = null,
) : UTSObject()
open class TransactionMediaFile (
    @JsonNotNull
    open var id: String,
    @JsonNotNull
    open var company: Number,
    @JsonNotNull
    open var original_filename: String,
    @JsonNotNull
    open var file_type: String,
    @JsonNotNull
    open var file_type_display: String,
    @JsonNotNull
    open var mime_type: String,
    @JsonNotNull
    open var file_size: Number,
    @JsonNotNull
    open var file_size_display: String,
    @JsonNotNull
    open var file_url: String,
    @JsonNotNull
    open var thumbnail_url: String,
    @JsonNotNull
    open var signed_url: String,
    @JsonNotNull
    open var signed_thumbnail_url: String,
    @JsonNotNull
    open var object_id: String,
    @JsonNotNull
    open var is_deleted: Boolean = false,
    @JsonNotNull
    open var created_at: String,
    @JsonNotNull
    open var updated_at: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return TransactionMediaFileReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class TransactionMediaFileReactiveObject : TransactionMediaFile, IUTSReactive<TransactionMediaFile> {
    override var __v_raw: TransactionMediaFile
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: TransactionMediaFile, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, company = __v_raw.company, original_filename = __v_raw.original_filename, file_type = __v_raw.file_type, file_type_display = __v_raw.file_type_display, mime_type = __v_raw.mime_type, file_size = __v_raw.file_size, file_size_display = __v_raw.file_size_display, file_url = __v_raw.file_url, thumbnail_url = __v_raw.thumbnail_url, signed_url = __v_raw.signed_url, signed_thumbnail_url = __v_raw.signed_thumbnail_url, object_id = __v_raw.object_id, is_deleted = __v_raw.is_deleted, created_at = __v_raw.created_at, updated_at = __v_raw.updated_at) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): TransactionMediaFileReactiveObject {
        return TransactionMediaFileReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: String
        get() {
            return _tRG(__v_raw, "id", __v_raw.id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            _tRS(__v_raw, "id", oldValue, value)
        }
    override var company: Number
        get() {
            return _tRG(__v_raw, "company", __v_raw.company, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("company")) {
                return
            }
            val oldValue = __v_raw.company
            __v_raw.company = value
            _tRS(__v_raw, "company", oldValue, value)
        }
    override var original_filename: String
        get() {
            return _tRG(__v_raw, "original_filename", __v_raw.original_filename, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("original_filename")) {
                return
            }
            val oldValue = __v_raw.original_filename
            __v_raw.original_filename = value
            _tRS(__v_raw, "original_filename", oldValue, value)
        }
    override var file_type: String
        get() {
            return _tRG(__v_raw, "file_type", __v_raw.file_type, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("file_type")) {
                return
            }
            val oldValue = __v_raw.file_type
            __v_raw.file_type = value
            _tRS(__v_raw, "file_type", oldValue, value)
        }
    override var file_type_display: String
        get() {
            return _tRG(__v_raw, "file_type_display", __v_raw.file_type_display, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("file_type_display")) {
                return
            }
            val oldValue = __v_raw.file_type_display
            __v_raw.file_type_display = value
            _tRS(__v_raw, "file_type_display", oldValue, value)
        }
    override var mime_type: String
        get() {
            return _tRG(__v_raw, "mime_type", __v_raw.mime_type, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("mime_type")) {
                return
            }
            val oldValue = __v_raw.mime_type
            __v_raw.mime_type = value
            _tRS(__v_raw, "mime_type", oldValue, value)
        }
    override var file_size: Number
        get() {
            return _tRG(__v_raw, "file_size", __v_raw.file_size, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("file_size")) {
                return
            }
            val oldValue = __v_raw.file_size
            __v_raw.file_size = value
            _tRS(__v_raw, "file_size", oldValue, value)
        }
    override var file_size_display: String
        get() {
            return _tRG(__v_raw, "file_size_display", __v_raw.file_size_display, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("file_size_display")) {
                return
            }
            val oldValue = __v_raw.file_size_display
            __v_raw.file_size_display = value
            _tRS(__v_raw, "file_size_display", oldValue, value)
        }
    override var file_url: String
        get() {
            return _tRG(__v_raw, "file_url", __v_raw.file_url, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("file_url")) {
                return
            }
            val oldValue = __v_raw.file_url
            __v_raw.file_url = value
            _tRS(__v_raw, "file_url", oldValue, value)
        }
    override var thumbnail_url: String
        get() {
            return _tRG(__v_raw, "thumbnail_url", __v_raw.thumbnail_url, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("thumbnail_url")) {
                return
            }
            val oldValue = __v_raw.thumbnail_url
            __v_raw.thumbnail_url = value
            _tRS(__v_raw, "thumbnail_url", oldValue, value)
        }
    override var signed_url: String
        get() {
            return _tRG(__v_raw, "signed_url", __v_raw.signed_url, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("signed_url")) {
                return
            }
            val oldValue = __v_raw.signed_url
            __v_raw.signed_url = value
            _tRS(__v_raw, "signed_url", oldValue, value)
        }
    override var signed_thumbnail_url: String
        get() {
            return _tRG(__v_raw, "signed_thumbnail_url", __v_raw.signed_thumbnail_url, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("signed_thumbnail_url")) {
                return
            }
            val oldValue = __v_raw.signed_thumbnail_url
            __v_raw.signed_thumbnail_url = value
            _tRS(__v_raw, "signed_thumbnail_url", oldValue, value)
        }
    override var object_id: String
        get() {
            return _tRG(__v_raw, "object_id", __v_raw.object_id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("object_id")) {
                return
            }
            val oldValue = __v_raw.object_id
            __v_raw.object_id = value
            _tRS(__v_raw, "object_id", oldValue, value)
        }
    override var is_deleted: Boolean
        get() {
            return _tRG(__v_raw, "is_deleted", __v_raw.is_deleted, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("is_deleted")) {
                return
            }
            val oldValue = __v_raw.is_deleted
            __v_raw.is_deleted = value
            _tRS(__v_raw, "is_deleted", oldValue, value)
        }
    override var created_at: String
        get() {
            return _tRG(__v_raw, "created_at", __v_raw.created_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("created_at")) {
                return
            }
            val oldValue = __v_raw.created_at
            __v_raw.created_at = value
            _tRS(__v_raw, "created_at", oldValue, value)
        }
    override var updated_at: String
        get() {
            return _tRG(__v_raw, "updated_at", __v_raw.updated_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("updated_at")) {
                return
            }
            val oldValue = __v_raw.updated_at
            __v_raw.updated_at = value
            _tRS(__v_raw, "updated_at", oldValue, value)
        }
}
open class TransactionItem (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var supplier: Number,
    @JsonNotNull
    open var supplier_name: String,
    @JsonNotNull
    open var transaction_type: Number,
    @JsonNotNull
    open var transaction_type_display: String,
    @JsonNotNull
    open var amount: String,
    @JsonNotNull
    open var transaction_date: String,
    @JsonNotNull
    open var transaction_number: String,
    open var note: String? = null,
    @JsonNotNull
    open var media_files: UTSArray<TransactionMediaFile>,
    @JsonNotNull
    open var files_count: Number,
    @JsonNotNull
    open var created_at: String,
    @JsonNotNull
    open var updated_at: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return TransactionItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class TransactionItemReactiveObject : TransactionItem, IUTSReactive<TransactionItem> {
    override var __v_raw: TransactionItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: TransactionItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, supplier = __v_raw.supplier, supplier_name = __v_raw.supplier_name, transaction_type = __v_raw.transaction_type, transaction_type_display = __v_raw.transaction_type_display, amount = __v_raw.amount, transaction_date = __v_raw.transaction_date, transaction_number = __v_raw.transaction_number, note = __v_raw.note, media_files = __v_raw.media_files, files_count = __v_raw.files_count, created_at = __v_raw.created_at, updated_at = __v_raw.updated_at) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): TransactionItemReactiveObject {
        return TransactionItemReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return _tRG(__v_raw, "id", __v_raw.id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            _tRS(__v_raw, "id", oldValue, value)
        }
    override var supplier: Number
        get() {
            return _tRG(__v_raw, "supplier", __v_raw.supplier, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("supplier")) {
                return
            }
            val oldValue = __v_raw.supplier
            __v_raw.supplier = value
            _tRS(__v_raw, "supplier", oldValue, value)
        }
    override var supplier_name: String
        get() {
            return _tRG(__v_raw, "supplier_name", __v_raw.supplier_name, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("supplier_name")) {
                return
            }
            val oldValue = __v_raw.supplier_name
            __v_raw.supplier_name = value
            _tRS(__v_raw, "supplier_name", oldValue, value)
        }
    override var transaction_type: Number
        get() {
            return _tRG(__v_raw, "transaction_type", __v_raw.transaction_type, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("transaction_type")) {
                return
            }
            val oldValue = __v_raw.transaction_type
            __v_raw.transaction_type = value
            _tRS(__v_raw, "transaction_type", oldValue, value)
        }
    override var transaction_type_display: String
        get() {
            return _tRG(__v_raw, "transaction_type_display", __v_raw.transaction_type_display, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("transaction_type_display")) {
                return
            }
            val oldValue = __v_raw.transaction_type_display
            __v_raw.transaction_type_display = value
            _tRS(__v_raw, "transaction_type_display", oldValue, value)
        }
    override var amount: String
        get() {
            return _tRG(__v_raw, "amount", __v_raw.amount, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("amount")) {
                return
            }
            val oldValue = __v_raw.amount
            __v_raw.amount = value
            _tRS(__v_raw, "amount", oldValue, value)
        }
    override var transaction_date: String
        get() {
            return _tRG(__v_raw, "transaction_date", __v_raw.transaction_date, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("transaction_date")) {
                return
            }
            val oldValue = __v_raw.transaction_date
            __v_raw.transaction_date = value
            _tRS(__v_raw, "transaction_date", oldValue, value)
        }
    override var transaction_number: String
        get() {
            return _tRG(__v_raw, "transaction_number", __v_raw.transaction_number, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("transaction_number")) {
                return
            }
            val oldValue = __v_raw.transaction_number
            __v_raw.transaction_number = value
            _tRS(__v_raw, "transaction_number", oldValue, value)
        }
    override var note: String?
        get() {
            return _tRG(__v_raw, "note", __v_raw.note, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("note")) {
                return
            }
            val oldValue = __v_raw.note
            __v_raw.note = value
            _tRS(__v_raw, "note", oldValue, value)
        }
    override var media_files: UTSArray<TransactionMediaFile>
        get() {
            return _tRG(__v_raw, "media_files", __v_raw.media_files, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("media_files")) {
                return
            }
            val oldValue = __v_raw.media_files
            __v_raw.media_files = value
            _tRS(__v_raw, "media_files", oldValue, value)
        }
    override var files_count: Number
        get() {
            return _tRG(__v_raw, "files_count", __v_raw.files_count, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("files_count")) {
                return
            }
            val oldValue = __v_raw.files_count
            __v_raw.files_count = value
            _tRS(__v_raw, "files_count", oldValue, value)
        }
    override var created_at: String
        get() {
            return _tRG(__v_raw, "created_at", __v_raw.created_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("created_at")) {
                return
            }
            val oldValue = __v_raw.created_at
            __v_raw.created_at = value
            _tRS(__v_raw, "created_at", oldValue, value)
        }
    override var updated_at: String
        get() {
            return _tRG(__v_raw, "updated_at", __v_raw.updated_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("updated_at")) {
                return
            }
            val oldValue = __v_raw.updated_at
            __v_raw.updated_at = value
            _tRS(__v_raw, "updated_at", oldValue, value)
        }
}
open class TransactionSummary (
    @JsonNotNull
    open var purchase_amount: String,
    @JsonNotNull
    open var arrears_amount: String,
    @JsonNotNull
    open var payment_amount: String,
    @JsonNotNull
    open var net_amount: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return TransactionSummaryReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class TransactionSummaryReactiveObject : TransactionSummary, IUTSReactive<TransactionSummary> {
    override var __v_raw: TransactionSummary
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: TransactionSummary, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(purchase_amount = __v_raw.purchase_amount, arrears_amount = __v_raw.arrears_amount, payment_amount = __v_raw.payment_amount, net_amount = __v_raw.net_amount) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): TransactionSummaryReactiveObject {
        return TransactionSummaryReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var purchase_amount: String
        get() {
            return _tRG(__v_raw, "purchase_amount", __v_raw.purchase_amount, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("purchase_amount")) {
                return
            }
            val oldValue = __v_raw.purchase_amount
            __v_raw.purchase_amount = value
            _tRS(__v_raw, "purchase_amount", oldValue, value)
        }
    override var arrears_amount: String
        get() {
            return _tRG(__v_raw, "arrears_amount", __v_raw.arrears_amount, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("arrears_amount")) {
                return
            }
            val oldValue = __v_raw.arrears_amount
            __v_raw.arrears_amount = value
            _tRS(__v_raw, "arrears_amount", oldValue, value)
        }
    override var payment_amount: String
        get() {
            return _tRG(__v_raw, "payment_amount", __v_raw.payment_amount, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("payment_amount")) {
                return
            }
            val oldValue = __v_raw.payment_amount
            __v_raw.payment_amount = value
            _tRS(__v_raw, "payment_amount", oldValue, value)
        }
    override var net_amount: String
        get() {
            return _tRG(__v_raw, "net_amount", __v_raw.net_amount, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("net_amount")) {
                return
            }
            val oldValue = __v_raw.net_amount
            __v_raw.net_amount = value
            _tRS(__v_raw, "net_amount", oldValue, value)
        }
}
open class TransactionListResponse (
    @JsonNotNull
    open var results: UTSArray<TransactionItem>,
    @JsonNotNull
    open var count: Number,
    @JsonNotNull
    open var total_count: Number,
    @JsonNotNull
    open var total_pages: Number,
    @JsonNotNull
    open var current_page: Number,
    @JsonNotNull
    open var page_size: Number,
    open var summary: TransactionSummary? = null,
) : UTSObject()
open class TransactionOptionItem (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var label: String,
    @JsonNotNull
    open var extra: UTSJSONObject,
) : UTSObject()
open class TransactionOptionGroup (
    @JsonNotNull
    open var key: String,
    @JsonNotNull
    open var label: String,
    @JsonNotNull
    open var control: String,
    @JsonNotNull
    open var count: Number,
    @JsonNotNull
    open var items: UTSArray<TransactionOptionItem>,
) : UTSObject()
open class TransactionOptionsResponse (
    @JsonNotNull
    open var resource: String,
    @JsonNotNull
    open var total_groups: Number,
    @JsonNotNull
    open var groups: UTSArray<TransactionOptionGroup>,
) : UTSObject()
open class TransactionStatisticsResponse (
    @JsonNotNull
    open var data: UTSJSONObject,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return TransactionStatisticsResponseReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class TransactionStatisticsResponseReactiveObject : TransactionStatisticsResponse, IUTSReactive<TransactionStatisticsResponse> {
    override var __v_raw: TransactionStatisticsResponse
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: TransactionStatisticsResponse, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(data = __v_raw.data) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): TransactionStatisticsResponseReactiveObject {
        return TransactionStatisticsResponseReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var data: UTSJSONObject
        get() {
            return _tRG(__v_raw, "data", __v_raw.data, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("data")) {
                return
            }
            val oldValue = __v_raw.data
            __v_raw.data = value
            _tRS(__v_raw, "data", oldValue, value)
        }
}
open class TransactionMutationData (
    @JsonNotNull
    open var supplier: Any,
    @JsonNotNull
    open var transaction_type: Any,
    @JsonNotNull
    open var amount: Any,
    @JsonNotNull
    open var transaction_date: String,
    open var transaction_number: String? = null,
    open var note: String? = null,
) : UTSObject()
fun normalizeServerUrl__2(url: String): String {
    if (url == "") {
        return ""
    }
    if (url.startsWith("http://localhost:8000")) {
        return baseUrl + url.substring(21)
    }
    if (url.startsWith("https://localhost:8000")) {
        return baseUrl + url.substring(22)
    }
    if (url.startsWith("http://127.0.0.1:8000")) {
        return baseUrl + url.substring(21)
    }
    if (url.startsWith("https://127.0.0.1:8000")) {
        return baseUrl + url.substring(22)
    }
    return url
}
fun intValue__3(value: Any?): Number {
    if (value == null) {
        return 0
    }
    val text = "" + value
    if (text == "") {
        return 0
    }
    val parsed = parseInt(text)
    if (isNaN(parsed)) {
        return 0
    }
    return parsed
}
fun stringValue__4(value: Any?): String {
    if (value == null) {
        return ""
    }
    return "" + value
}
fun booleanValue__1(value: Any?): Boolean {
    return stringValue__4(value) == "true"
}
fun buildTransactionListQuery(data: TransactionListQuery): UTSJSONObject {
    val query: UTSJSONObject = _uO("page" to data.page, "page_size" to data.page_size)
    if (data.search != null && data.search != "") {
        query["search"] = data.search
    }
    if (data.transaction_type != null && data.transaction_type != "") {
        query["transaction_type"] = data.transaction_type
    }
    if (data.supplier != null && data.supplier != "") {
        query["supplier"] = data.supplier
    }
    if (data.supplier_id != null && data.supplier_id != "") {
        query["supplier_id"] = data.supplier_id
    }
    if (data.date_from != null && data.date_from != "") {
        query["date_from"] = data.date_from
    }
    if (data.start_date != null && data.start_date != "") {
        query["start_date"] = data.start_date
    }
    if (data.date_to != null && data.date_to != "") {
        query["date_to"] = data.date_to
    }
    if (data.end_date != null && data.end_date != "") {
        query["end_date"] = data.end_date
    }
    if (data.amount_min != null && data.amount_min != "") {
        query["amount_min"] = data.amount_min
    }
    if (data.amount_max != null && data.amount_max != "") {
        query["amount_max"] = data.amount_max
    }
    if (data.ordering != null && data.ordering != "") {
        query["ordering"] = data.ordering
    }
    if (data.sort_by != null && data.sort_by != "") {
        query["sort_by"] = data.sort_by
    }
    return query
}
fun buildTransactionMediaFileFromObject(rawObject: UTSJSONObject): TransactionMediaFile {
    return TransactionMediaFile(id = stringValue__4(rawObject["id"]), company = intValue__3(rawObject["company"]), original_filename = stringValue__4(rawObject["original_filename"]), file_type = stringValue__4(rawObject["file_type"]), file_type_display = stringValue__4(rawObject["file_type_display"]), mime_type = stringValue__4(rawObject["mime_type"]), file_size = intValue__3(rawObject["file_size"]), file_size_display = stringValue__4(rawObject["file_size_display"]), file_url = normalizeServerUrl__2(stringValue__4(rawObject["file_url"])), thumbnail_url = normalizeServerUrl__2(stringValue__4(rawObject["thumbnail_url"])), signed_url = normalizeServerUrl__2(stringValue__4(rawObject["signed_url"])), signed_thumbnail_url = normalizeServerUrl__2(stringValue__4(rawObject["signed_thumbnail_url"])), object_id = stringValue__4(rawObject["object_id"]), is_deleted = booleanValue__1(rawObject["is_deleted"]), created_at = stringValue__4(rawObject["created_at"]), updated_at = stringValue__4(rawObject["updated_at"]))
}
fun buildTransactionMediaFilesFromValue(value: Any?): UTSArray<TransactionMediaFile> {
    if (value == null) {
        return _uA()
    }
    val text = JSON.stringify(value)
    val rawArray = if (text == null || text == "") {
        null
    } else {
        JSON.parseArray<UTSJSONObject>(text)
    }
    if (rawArray == null) {
        return _uA()
    }
    val result: UTSArray<TransactionMediaFile> = _uA()
    run {
        var index: Number = 0
        while(index < rawArray!!.length){
            result.push(buildTransactionMediaFileFromObject(rawArray!![index]))
            index += 1
        }
    }
    return result
}
fun buildTransactionItemFromObject(rawObject: UTSJSONObject): TransactionItem {
    return TransactionItem(id = intValue__3(rawObject["id"]), supplier = intValue__3(rawObject["supplier"]), supplier_name = stringValue__4(rawObject["supplier_name"]), transaction_type = intValue__3(rawObject["transaction_type"]), transaction_type_display = stringValue__4(rawObject["transaction_type_display"]), amount = stringValue__4(rawObject["amount"]), transaction_date = stringValue__4(rawObject["transaction_date"]), transaction_number = stringValue__4(rawObject["transaction_number"]), note = if (rawObject["note"] == null) {
        null
    } else {
        stringValue__4(rawObject["note"])
    }
    , media_files = buildTransactionMediaFilesFromValue(rawObject["media_files"]), files_count = intValue__3(rawObject["files_count"]), created_at = stringValue__4(rawObject["created_at"]), updated_at = stringValue__4(rawObject["updated_at"]))
}
fun buildTransactionSummary(value: Any?): TransactionSummary? {
    if (value == null) {
        return null
    }
    val text = JSON.stringify(value)
    val rawObject = if (text == null || text == "") {
        null
    } else {
        JSON.parseObject<UTSJSONObject>(text)
    }
    if (rawObject == null) {
        return null
    }
    return TransactionSummary(purchase_amount = stringValue__4(rawObject["purchase_amount"]), arrears_amount = stringValue__4(rawObject["arrears_amount"]), payment_amount = stringValue__4(rawObject["payment_amount"]), net_amount = stringValue__4(rawObject["net_amount"]))
}
fun buildTransactionListResponse(raw: Any, query: TransactionListQuery): TransactionListResponse {
    val rawText = JSON.stringify(raw)
    val rawObject = if (rawText == null || rawText == "") {
        null
    } else {
        JSON.parseObject<UTSJSONObject>(rawText)
    }
    if (rawObject == null) {
        throw UTSError("往来记录列表响应解析失败")
    }
    var paginationObject: UTSJSONObject? = null
    val rawPagination = rawObject["pagination"]
    if (rawPagination != null) {
        val paginationText = JSON.stringify(rawPagination)
        if (paginationText != null && paginationText != "") {
            paginationObject = JSON.parseObject<UTSJSONObject>(paginationText)
        }
    }
    var results: UTSArray<TransactionItem> = _uA()
    val rawResults = rawObject["results"]
    if (rawResults != null) {
        val resultText = JSON.stringify(rawResults)
        val parsedResults = if (resultText == null || resultText == "") {
            null
        } else {
            JSON.parseArray<UTSJSONObject>(resultText)
        }
        if (parsedResults != null) {
            val nextResults: UTSArray<TransactionItem> = _uA()
            run {
                var index: Number = 0
                while(index < parsedResults!!.length){
                    nextResults.push(buildTransactionItemFromObject(parsedResults!![index]))
                    index += 1
                }
            }
            results = nextResults
        }
    }
    var totalCount = intValue__3(rawObject["count"])
    if (totalCount <= 0) {
        totalCount = intValue__3(rawObject["total"])
    }
    if (totalCount <= 0) {
        totalCount = intValue__3(rawObject["total_count"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__3(paginationObject["total"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__3(paginationObject["count"])
    }
    if (totalCount <= 0) {
        totalCount = results.length
    }
    var currentPage = intValue__3(rawObject["page"])
    if (currentPage <= 0) {
        currentPage = intValue__3(rawObject["current_page"])
    }
    if (currentPage <= 0 && paginationObject != null) {
        currentPage = intValue__3(paginationObject["page"])
    }
    if (currentPage <= 0) {
        currentPage = query.page
    }
    var pageSize = intValue__3(rawObject["page_size"])
    if (pageSize <= 0 && paginationObject != null) {
        pageSize = intValue__3(paginationObject["page_size"])
    }
    if (pageSize <= 0) {
        pageSize = query.page_size
    }
    var totalPages = intValue__3(rawObject["total_pages"])
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__3(paginationObject["total_pages"])
    }
    if (totalPages <= 0 && pageSize > 0) {
        totalPages = Math.ceil(totalCount / pageSize)
    }
    if (totalPages <= 0) {
        totalPages = 1
    }
    return TransactionListResponse(results = results, count = totalCount, total_count = totalCount, total_pages = totalPages, current_page = currentPage, page_size = pageSize, summary = buildTransactionSummary(rawObject["summary"]))
}
fun buildTransactionOptionsResponse(raw: Any): TransactionOptionsResponse {
    val rawText = JSON.stringify(raw)
    val rawObject = if (rawText == null || rawText == "") {
        null
    } else {
        JSON.parseObject<UTSJSONObject>(rawText)
    }
    if (rawObject == null) {
        throw UTSError("往来记录表单选项解析失败")
    }
    var groups: UTSArray<TransactionOptionGroup> = _uA()
    val rawGroups = rawObject["groups"]
    if (rawGroups != null) {
        val groupsText = JSON.stringify(rawGroups)
        val groupObjects = if (groupsText == null || groupsText == "") {
            null
        } else {
            JSON.parseArray<UTSJSONObject>(groupsText)
        }
        if (groupObjects != null) {
            val nextGroups: UTSArray<TransactionOptionGroup> = _uA()
            run {
                var groupIndex: Number = 0
                while(groupIndex < groupObjects!!.length){
                    val groupObject = groupObjects!![groupIndex]
                    var items: UTSArray<TransactionOptionItem> = _uA()
                    val rawItems = groupObject["items"]
                    if (rawItems != null) {
                        val itemsText = JSON.stringify(rawItems)
                        val itemObjects = if (itemsText == null || itemsText == "") {
                            null
                        } else {
                            JSON.parseArray<UTSJSONObject>(itemsText)
                        }
                        if (itemObjects != null) {
                            val nextItems: UTSArray<TransactionOptionItem> = _uA()
                            run {
                                var itemIndex: Number = 0
                                while(itemIndex < itemObjects!!.length){
                                    val itemObject = itemObjects!![itemIndex]
                                    nextItems.push(TransactionOptionItem(value = stringValue__4(itemObject["value"]), label = stringValue__4(itemObject["label"]), extra = itemObject))
                                    itemIndex += 1
                                }
                            }
                            items = nextItems
                        }
                    }
                    nextGroups.push(TransactionOptionGroup(key = stringValue__4(groupObject["key"]), label = stringValue__4(groupObject["label"]), control = stringValue__4(groupObject["control"]), count = intValue__3(groupObject["count"]), items = items))
                    groupIndex += 1
                }
            }
            groups = nextGroups
        }
    }
    return TransactionOptionsResponse(resource = stringValue__4(rawObject["resource"]), total_groups = intValue__3(rawObject["total_groups"]), groups = groups)
}
fun buildTransactionStatisticsResponse(raw: Any): TransactionStatisticsResponse {
    val rawText = JSON.stringify(raw)
    val rawObject = if (rawText == null || rawText == "") {
        null
    } else {
        JSON.parseObject<UTSJSONObject>(rawText)
    }
    if (rawObject == null) {
        throw UTSError("往来记录统计解析失败")
    }
    return TransactionStatisticsResponse(data = rawObject)
}
fun buildTransactionMutationBody(data: TransactionMutationData): UTSJSONObject {
    val body: UTSJSONObject = _uO("supplier" to data.supplier, "transaction_type" to data.transaction_type, "amount" to data.amount, "transaction_date" to data.transaction_date)
    if (data.transaction_number != null && data.transaction_number != "") {
        body["transaction_number"] = data.transaction_number
    }
    if (data.note != null) {
        body["note"] = data.note
    }
    return body
}
fun transactionDetailPath(id: Any): String {
    return "/api/procurement/transactions/" + stringValue__4(id) + "/"
}
fun getTransactionList(data: TransactionListQuery): UTSPromise<TransactionListResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/procurement/transactions/", "GET", buildTransactionListQuery(data), true))
            return@w buildTransactionListResponse(raw, data)
    })
}
fun getTransactionOptions(key: String? = null, search: String? = null, limit: Number = 20): UTSPromise<TransactionOptionsResponse> {
    return wrapUTSPromise(suspend w@{
            val query: UTSJSONObject = _uO()
            if (key != null && key != "") {
                query["key"] = key
            }
            if (search != null && search != "") {
                query["search"] = search
            }
            if (limit > 0) {
                query["limit"] = limit
            }
            val raw = await(request("/api/procurement/transactions/options/", "GET", query, true))
            return@w buildTransactionOptionsResponse(raw)
    })
}
fun getTransactionDetail(id: Any): UTSPromise<TransactionItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(transactionDetailPath(id), "GET", _uO(), true))
            val rawText = JSON.stringify(raw)
            val rawObject = if (rawText == null || rawText == "") {
                null
            } else {
                JSON.parseObject<UTSJSONObject>(rawText)
            }
            if (rawObject == null) {
                throw UTSError("往来记录详情响应解析失败")
            }
            return@w buildTransactionItemFromObject(rawObject)
    })
}
fun createTransaction(data: TransactionMutationData): UTSPromise<TransactionItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/procurement/transactions/", "POST", buildTransactionMutationBody(data), true))
            val rawText = JSON.stringify(raw)
            val rawObject = if (rawText == null || rawText == "") {
                null
            } else {
                JSON.parseObject<UTSJSONObject>(rawText)
            }
            if (rawObject == null) {
                throw UTSError("创建往来记录响应解析失败")
            }
            return@w buildTransactionItemFromObject(rawObject)
    })
}
fun updateTransaction(id: Any, data: TransactionMutationData): UTSPromise<TransactionItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(transactionDetailPath(id), "PUT", buildTransactionMutationBody(data), true))
            val rawText = JSON.stringify(raw)
            val rawObject = if (rawText == null || rawText == "") {
                null
            } else {
                JSON.parseObject<UTSJSONObject>(rawText)
            }
            if (rawObject == null) {
                throw UTSError("更新往来记录响应解析失败")
            }
            return@w buildTransactionItemFromObject(rawObject)
    })
}
fun deleteTransaction(id: Any): UTSPromise<Any> {
    return request(transactionDetailPath(id), "DELETE", _uO(), true)
}
fun getTransactionStatistics(query: TransactionListQuery? = null): UTSPromise<TransactionStatisticsResponse> {
    return wrapUTSPromise(suspend w@{
            val requestQuery = if (query == null) {
                _uO()
            } else {
                buildTransactionListQuery(query)
            }
            val raw = await(request("/api/procurement/transactions/statistics/", "GET", requestQuery, true))
            return@w buildTransactionStatisticsResponse(raw)
    })
}
val GenPagesTransactionsIndexClass = CreateVueComponent(GenPagesTransactionsIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesTransactionsIndex.inheritAttrs, inject = GenPagesTransactionsIndex.inject, props = GenPagesTransactionsIndex.props, propsNeedCastKeys = GenPagesTransactionsIndex.propsNeedCastKeys, emits = GenPagesTransactionsIndex.emits, components = GenPagesTransactionsIndex.components, styles = GenPagesTransactionsIndex.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesTransactionsIndex.setup(props as GenPagesTransactionsIndex)
    }
    )
}
, fun(instance, renderer): GenPagesTransactionsIndex {
    return GenPagesTransactionsIndex(instance, renderer)
}
)
open class SelectOption__1 (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var text: String,
) : UTSObject()
val GenPagesTransactionsFromClass = CreateVueComponent(GenPagesTransactionsFrom::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesTransactionsFrom.inheritAttrs, inject = GenPagesTransactionsFrom.inject, props = GenPagesTransactionsFrom.props, propsNeedCastKeys = GenPagesTransactionsFrom.propsNeedCastKeys, emits = GenPagesTransactionsFrom.emits, components = GenPagesTransactionsFrom.components, styles = GenPagesTransactionsFrom.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesTransactionsFrom.setup(props as GenPagesTransactionsFrom)
    }
    )
}
, fun(instance, renderer): GenPagesTransactionsFrom {
    return GenPagesTransactionsFrom(instance, renderer)
}
)
val kasaCategoryBasePath = "/api/categories/kasa-categories/"
open class KasaCategoryListQuery (
    open var search: String? = null,
    @JsonNotNull
    open var page: Number,
    @JsonNotNull
    open var page_size: Number,
    open var id: Any? = null,
    open var is_active: Any? = null,
    open var tax_rate: Any? = null,
    open var unique_kod: String? = null,
    open var simple: Boolean? = null,
) : UTSObject()
open class KasaCategoryItem (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var name_cn: String,
    @JsonNotNull
    open var name_en: String,
    @JsonNotNull
    open var code: String,
    @JsonNotNull
    open var unique_kod: String,
    @JsonNotNull
    open var tax_rate: String,
    @JsonNotNull
    open var tax_rate_display: String,
    @JsonNotNull
    open var products_count: Number,
    @JsonNotNull
    open var is_active: Boolean = false,
    @JsonNotNull
    open var created_at: String,
    @JsonNotNull
    open var updated_at: String,
    @JsonNotNull
    open var raw: UTSJSONObject,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return KasaCategoryItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class KasaCategoryItemReactiveObject : KasaCategoryItem, IUTSReactive<KasaCategoryItem> {
    override var __v_raw: KasaCategoryItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: KasaCategoryItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, name = __v_raw.name, name_cn = __v_raw.name_cn, name_en = __v_raw.name_en, code = __v_raw.code, unique_kod = __v_raw.unique_kod, tax_rate = __v_raw.tax_rate, tax_rate_display = __v_raw.tax_rate_display, products_count = __v_raw.products_count, is_active = __v_raw.is_active, created_at = __v_raw.created_at, updated_at = __v_raw.updated_at, raw = __v_raw.raw) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): KasaCategoryItemReactiveObject {
        return KasaCategoryItemReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return _tRG(__v_raw, "id", __v_raw.id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            _tRS(__v_raw, "id", oldValue, value)
        }
    override var name: String
        get() {
            return _tRG(__v_raw, "name", __v_raw.name, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("name")) {
                return
            }
            val oldValue = __v_raw.name
            __v_raw.name = value
            _tRS(__v_raw, "name", oldValue, value)
        }
    override var name_cn: String
        get() {
            return _tRG(__v_raw, "name_cn", __v_raw.name_cn, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("name_cn")) {
                return
            }
            val oldValue = __v_raw.name_cn
            __v_raw.name_cn = value
            _tRS(__v_raw, "name_cn", oldValue, value)
        }
    override var name_en: String
        get() {
            return _tRG(__v_raw, "name_en", __v_raw.name_en, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("name_en")) {
                return
            }
            val oldValue = __v_raw.name_en
            __v_raw.name_en = value
            _tRS(__v_raw, "name_en", oldValue, value)
        }
    override var code: String
        get() {
            return _tRG(__v_raw, "code", __v_raw.code, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("code")) {
                return
            }
            val oldValue = __v_raw.code
            __v_raw.code = value
            _tRS(__v_raw, "code", oldValue, value)
        }
    override var unique_kod: String
        get() {
            return _tRG(__v_raw, "unique_kod", __v_raw.unique_kod, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("unique_kod")) {
                return
            }
            val oldValue = __v_raw.unique_kod
            __v_raw.unique_kod = value
            _tRS(__v_raw, "unique_kod", oldValue, value)
        }
    override var tax_rate: String
        get() {
            return _tRG(__v_raw, "tax_rate", __v_raw.tax_rate, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("tax_rate")) {
                return
            }
            val oldValue = __v_raw.tax_rate
            __v_raw.tax_rate = value
            _tRS(__v_raw, "tax_rate", oldValue, value)
        }
    override var tax_rate_display: String
        get() {
            return _tRG(__v_raw, "tax_rate_display", __v_raw.tax_rate_display, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("tax_rate_display")) {
                return
            }
            val oldValue = __v_raw.tax_rate_display
            __v_raw.tax_rate_display = value
            _tRS(__v_raw, "tax_rate_display", oldValue, value)
        }
    override var products_count: Number
        get() {
            return _tRG(__v_raw, "products_count", __v_raw.products_count, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("products_count")) {
                return
            }
            val oldValue = __v_raw.products_count
            __v_raw.products_count = value
            _tRS(__v_raw, "products_count", oldValue, value)
        }
    override var is_active: Boolean
        get() {
            return _tRG(__v_raw, "is_active", __v_raw.is_active, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("is_active")) {
                return
            }
            val oldValue = __v_raw.is_active
            __v_raw.is_active = value
            _tRS(__v_raw, "is_active", oldValue, value)
        }
    override var created_at: String
        get() {
            return _tRG(__v_raw, "created_at", __v_raw.created_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("created_at")) {
                return
            }
            val oldValue = __v_raw.created_at
            __v_raw.created_at = value
            _tRS(__v_raw, "created_at", oldValue, value)
        }
    override var updated_at: String
        get() {
            return _tRG(__v_raw, "updated_at", __v_raw.updated_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("updated_at")) {
                return
            }
            val oldValue = __v_raw.updated_at
            __v_raw.updated_at = value
            _tRS(__v_raw, "updated_at", oldValue, value)
        }
    override var raw: UTSJSONObject
        get() {
            return _tRG(__v_raw, "raw", __v_raw.raw, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("raw")) {
                return
            }
            val oldValue = __v_raw.raw
            __v_raw.raw = value
            _tRS(__v_raw, "raw", oldValue, value)
        }
}
open class KasaCategoryListResponse (
    @JsonNotNull
    open var results: UTSArray<KasaCategoryItem>,
    @JsonNotNull
    open var count: Number,
    @JsonNotNull
    open var total_count: Number,
    @JsonNotNull
    open var total_pages: Number,
    @JsonNotNull
    open var current_page: Number,
    @JsonNotNull
    open var page_size: Number,
) : UTSObject()
typealias KasaCategoryMutationData = UTSJSONObject
open class KasaCategoryStatisticsResponse (
    @JsonNotNull
    open var data: UTSJSONObject,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return KasaCategoryStatisticsResponseReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class KasaCategoryStatisticsResponseReactiveObject : KasaCategoryStatisticsResponse, IUTSReactive<KasaCategoryStatisticsResponse> {
    override var __v_raw: KasaCategoryStatisticsResponse
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: KasaCategoryStatisticsResponse, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(data = __v_raw.data) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): KasaCategoryStatisticsResponseReactiveObject {
        return KasaCategoryStatisticsResponseReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var data: UTSJSONObject
        get() {
            return _tRG(__v_raw, "data", __v_raw.data, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("data")) {
                return
            }
            val oldValue = __v_raw.data
            __v_raw.data = value
            _tRS(__v_raw, "data", oldValue, value)
        }
}
open class KasaCategoryTaxRatesResponse (
    open var data: UTSJSONObject? = null,
    @JsonNotNull
    open var items: UTSArray<UTSJSONObject>,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return KasaCategoryTaxRatesResponseReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class KasaCategoryTaxRatesResponseReactiveObject : KasaCategoryTaxRatesResponse, IUTSReactive<KasaCategoryTaxRatesResponse> {
    override var __v_raw: KasaCategoryTaxRatesResponse
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: KasaCategoryTaxRatesResponse, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(data = __v_raw.data, items = __v_raw.items) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): KasaCategoryTaxRatesResponseReactiveObject {
        return KasaCategoryTaxRatesResponseReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var data: UTSJSONObject?
        get() {
            return _tRG(__v_raw, "data", __v_raw.data, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("data")) {
                return
            }
            val oldValue = __v_raw.data
            __v_raw.data = value
            _tRS(__v_raw, "data", oldValue, value)
        }
    override var items: UTSArray<UTSJSONObject>
        get() {
            return _tRG(__v_raw, "items", __v_raw.items, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("items")) {
                return
            }
            val oldValue = __v_raw.items
            __v_raw.items = value
            _tRS(__v_raw, "items", oldValue, value)
        }
}
open class KasaCategoryOptionsQuery (
    open var key: String? = null,
    open var search: String? = null,
    open var q: String? = null,
    open var keyword: String? = null,
    open var limit: Number? = null,
) : UTSObject()
open class KasaCategoryOptionsResponse (
    open var data: UTSJSONObject? = null,
    @JsonNotNull
    open var groups: UTSArray<UTSJSONObject>,
    @JsonNotNull
    open var items: UTSArray<UTSJSONObject>,
) : UTSObject()
fun stringValue__5(value: Any?): String {
    if (value == null) {
        return ""
    }
    return "" + value
}
fun intValue__4(value: Any?): Number {
    if (value == null) {
        return 0
    }
    val text = stringValue__5(value)
    if (text == "") {
        return 0
    }
    val parsed = parseInt(text)
    if (isNaN(parsed)) {
        return 0
    }
    return parsed
}
fun booleanValue__2(value: Any?): Boolean {
    val text = stringValue__5(value).toLowerCase()
    return text == "true" || text == "1" || text == "yes"
}
fun parseObject__1(value: Any?): UTSJSONObject? {
    if (value == null) {
        return null
    }
    val text = JSON.stringify(value)
    if (text == null || text == "") {
        return null
    }
    return JSON.parseObject<UTSJSONObject>(text)
}
fun parseObjectArray__1(value: Any?): UTSArray<UTSJSONObject> {
    if (value == null) {
        return _uA()
    }
    val text = JSON.stringify(value)
    if (text == null || text == "") {
        return _uA()
    }
    val parsed = JSON.parseArray<UTSJSONObject>(text)
    if (parsed == null) {
        return _uA()
    }
    return parsed!!
}
fun buildKasaCategoryItemFromObject(rawObject: UTSJSONObject): KasaCategoryItem {
    val nestedRawObject = parseObject__1(rawObject["raw"])
    val nameCn = if (stringValue__5(rawObject["name_cn"]) != "") {
        stringValue__5(rawObject["name_cn"])
    } else {
        stringValue__5(if (nestedRawObject != null) {
            nestedRawObject["name_cn"]
        } else {
            null
        }
        )
    }
    val nameEn = if (stringValue__5(rawObject["name_en"]) != "") {
        stringValue__5(rawObject["name_en"])
    } else {
        stringValue__5(if (nestedRawObject != null) {
            nestedRawObject["name_en"]
        } else {
            null
        }
        )
    }
    var displayName = stringValue__5(rawObject["name"])
    if (displayName == "") {
        if (nameCn != "" && nameEn != "") {
            displayName = nameCn + " / " + nameEn
        } else if (nameCn != "") {
            displayName = nameCn
        } else {
            displayName = nameEn
        }
    }
    return KasaCategoryItem(id = intValue__4(rawObject["id"]), name = displayName, name_cn = nameCn, name_en = nameEn, code = if (stringValue__5(rawObject["code"]) != "") {
        stringValue__5(rawObject["code"])
    } else {
        stringValue__5(if (nestedRawObject != null) {
            nestedRawObject["code"]
        } else {
            null
        }
        )
    }
    , unique_kod = if (stringValue__5(rawObject["unique_kod"]) != "") {
        stringValue__5(rawObject["unique_kod"])
    } else {
        stringValue__5(if (nestedRawObject != null) {
            nestedRawObject["unique_kod"]
        } else {
            null
        }
        )
    }
    , tax_rate = if (stringValue__5(rawObject["tax_rate"]) != "") {
        stringValue__5(rawObject["tax_rate"])
    } else {
        stringValue__5(if (nestedRawObject != null) {
            nestedRawObject["tax_rate"]
        } else {
            null
        }
        )
    }
    , tax_rate_display = if (stringValue__5(rawObject["tax_rate_display"]) != "") {
        stringValue__5(rawObject["tax_rate_display"])
    } else {
        stringValue__5(if (nestedRawObject != null) {
            nestedRawObject["tax_rate_display"]
        } else {
            null
        }
        )
    }
    , products_count = if (rawObject["products_count"] != null) {
        intValue__4(rawObject["products_count"])
    } else {
        intValue__4(if (nestedRawObject != null) {
            nestedRawObject["products_count"]
        } else {
            null
        }
        )
    }
    , is_active = if (rawObject["is_active"] != null) {
        booleanValue__2(rawObject["is_active"])
    } else {
        booleanValue__2(if (nestedRawObject != null) {
            nestedRawObject["is_active"]
        } else {
            null
        }
        )
    }
    , created_at = if (stringValue__5(rawObject["created_at"]) != "") {
        stringValue__5(rawObject["created_at"])
    } else {
        stringValue__5(if (nestedRawObject != null) {
            nestedRawObject["created_at"]
        } else {
            null
        }
        )
    }
    , updated_at = if (stringValue__5(rawObject["updated_at"]) != "") {
        stringValue__5(rawObject["updated_at"])
    } else {
        stringValue__5(if (nestedRawObject != null) {
            nestedRawObject["updated_at"]
        } else {
            null
        }
        )
    }
    , raw = rawObject)
}
fun buildKasaCategoryArrayFromValue(value: Any?): UTSArray<KasaCategoryItem> {
    val rawArray = parseObjectArray__1(value)
    val result: UTSArray<KasaCategoryItem> = _uA()
    run {
        var index: Number = 0
        while(index < rawArray.length){
            result.push(buildKasaCategoryItemFromObject(rawArray[index]))
            index += 1
        }
    }
    return result
}
fun buildKasaCategoryListQuery(data: KasaCategoryListQuery): UTSJSONObject {
    val query: UTSJSONObject = _uO("page" to data.page, "page_size" to data.page_size)
    if (data.search != null && data.search != "") {
        query["search"] = data.search
    }
    if (data.id != null && stringValue__5(data.id) != "") {
        query["id"] = data.id
    }
    if (data.is_active != null && stringValue__5(data.is_active) != "") {
        query["is_active"] = data.is_active
    }
    if (data.tax_rate != null && stringValue__5(data.tax_rate) != "") {
        query["tax_rate"] = data.tax_rate
    }
    if (data.unique_kod != null && data.unique_kod != "") {
        query["unique_kod"] = data.unique_kod
    }
    if (data.simple != null) {
        query["simple"] = data.simple
    }
    return query
}
fun buildKasaCategoryListResponse(raw: Any, query: KasaCategoryListQuery): KasaCategoryListResponse {
    val rawObject = parseObject__1(raw)
    if (rawObject == null) {
        val results = buildKasaCategoryArrayFromValue(raw)
        return KasaCategoryListResponse(results = results, count = results.length, total_count = results.length, total_pages = 1, current_page = if (query.page > 0) {
            query.page
        } else {
            1
        }
        , page_size = if (query.page_size > 0) {
            query.page_size
        } else {
            results.length
        }
        )
    }
    var paginationObject: UTSJSONObject? = null
    if (rawObject["pagination"] != null) {
        paginationObject = parseObject__1(rawObject["pagination"])
    }
    var results: UTSArray<KasaCategoryItem> = _uA()
    if (rawObject["results"] != null) {
        results = buildKasaCategoryArrayFromValue(rawObject["results"])
    } else if (rawObject["items"] != null) {
        results = buildKasaCategoryArrayFromValue(rawObject["items"])
    } else {
        results = buildKasaCategoryArrayFromValue(raw)
    }
    var totalCount = intValue__4(rawObject["count"])
    if (totalCount <= 0) {
        totalCount = intValue__4(rawObject["total"])
    }
    if (totalCount <= 0) {
        totalCount = intValue__4(rawObject["total_count"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__4(paginationObject["total"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__4(paginationObject["count"])
    }
    if (totalCount <= 0) {
        totalCount = results.length
    }
    var currentPage = intValue__4(rawObject["page"])
    if (currentPage <= 0) {
        currentPage = intValue__4(rawObject["current_page"])
    }
    if (currentPage <= 0 && paginationObject != null) {
        currentPage = intValue__4(paginationObject["page"])
    }
    if (currentPage <= 0) {
        currentPage = if (query.page > 0) {
            query.page
        } else {
            1
        }
    }
    var pageSize = intValue__4(rawObject["page_size"])
    if (pageSize <= 0) {
        pageSize = intValue__4(rawObject["per_page"])
    }
    if (pageSize <= 0 && paginationObject != null) {
        pageSize = intValue__4(paginationObject["page_size"])
    }
    if (pageSize <= 0 && paginationObject != null) {
        pageSize = intValue__4(paginationObject["per_page"])
    }
    if (pageSize <= 0) {
        pageSize = if (query.page_size > 0) {
            query.page_size
        } else {
            results.length
        }
    }
    var totalPages = intValue__4(rawObject["total_pages"])
    if (totalPages <= 0) {
        totalPages = intValue__4(rawObject["num_pages"])
    }
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__4(paginationObject["total_pages"])
    }
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__4(paginationObject["num_pages"])
    }
    if (totalPages <= 0 && pageSize > 0) {
        totalPages = Math.ceil(totalCount / pageSize)
    }
    if (totalPages <= 0) {
        totalPages = 1
    }
    return KasaCategoryListResponse(results = results, count = totalCount, total_count = totalCount, total_pages = totalPages, current_page = currentPage, page_size = pageSize)
}
fun buildKasaCategoryItemResponse(raw: Any, errorMessage: String): KasaCategoryItem {
    val rawObject = parseObject__1(raw)
    if (rawObject == null) {
        throw UTSError(errorMessage)
    }
    return buildKasaCategoryItemFromObject(rawObject)
}
fun buildObjectResponse(raw: Any, errorMessage: String): UTSJSONObject {
    val rawObject = parseObject__1(raw)
    if (rawObject == null) {
        throw UTSError(errorMessage)
    }
    return rawObject
}
fun buildTaxRatesResponse(raw: Any): KasaCategoryTaxRatesResponse {
    val optionsResponse = buildKasaCategoryOptionsResponse(raw)
    return KasaCategoryTaxRatesResponse(data = optionsResponse.data, items = optionsResponse.items)
}
fun buildKasaCategoryOptionsQuery(data: KasaCategoryOptionsQuery): UTSJSONObject {
    val query: UTSJSONObject = _uO()
    if (data.key != null && data.key != "") {
        query["key"] = data.key
    }
    if (data.search != null && data.search != "") {
        query["search"] = data.search
    }
    if (data.q != null && data.q != "") {
        query["q"] = data.q
    }
    if (data.keyword != null && data.keyword != "") {
        query["keyword"] = data.keyword
    }
    if (data.limit != null && data.limit!! > 0) {
        query["limit"] = data.limit
    }
    return query
}
fun buildKasaCategoryOptionsResponse(raw: Any): KasaCategoryOptionsResponse {
    val rawObject = parseObject__1(raw)
    if (rawObject != null) {
        var groups = parseObjectArray__1(rawObject["groups"])
        var items = parseObjectArray__1(rawObject["items"])
        if (items.length == 0 && rawObject["results"] != null) {
            items = parseObjectArray__1(rawObject["results"])
        }
        if (items.length == 0 && rawObject["data"] != null) {
            items = parseObjectArray__1(rawObject["data"])
        }
        if (items.length == 0 && groups.length > 0) {
            val firstGroup = groups[0]
            items = parseObjectArray__1(firstGroup["items"])
        }
        return KasaCategoryOptionsResponse(data = rawObject, groups = groups, items = items)
    }
    return KasaCategoryOptionsResponse(data = null, groups = _uA<UTSJSONObject>(), items = parseObjectArray__1(raw))
}
fun kasaCategoryDetailPath(id: Any): String {
    return kasaCategoryBasePath + stringValue__5(id) + "/"
}
fun getKasaCategoryList(data: KasaCategoryListQuery): UTSPromise<KasaCategoryListResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(kasaCategoryBasePath, "GET", buildKasaCategoryListQuery(data), true))
            return@w buildKasaCategoryListResponse(raw, data)
    })
}
fun getKasaCategoryDetail(id: Any): UTSPromise<KasaCategoryItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(kasaCategoryDetailPath(id), "GET", _uO(), true))
            return@w buildKasaCategoryItemResponse(raw, "收银分类详情响应解析失败")
    })
}
fun createKasaCategory(data: KasaCategoryMutationData): UTSPromise<KasaCategoryItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(kasaCategoryBasePath, "POST", data, true))
            return@w buildKasaCategoryItemResponse(raw, "创建收银分类响应解析失败")
    })
}
fun updateKasaCategory(id: Any, data: KasaCategoryMutationData): UTSPromise<KasaCategoryItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(kasaCategoryDetailPath(id), "PUT", data, true))
            return@w buildKasaCategoryItemResponse(raw, "更新收银分类响应解析失败")
    })
}
fun patchKasaCategory(id: Any, data: KasaCategoryMutationData): UTSPromise<KasaCategoryItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(kasaCategoryDetailPath(id), "PATCH", data, true))
            return@w buildKasaCategoryItemResponse(raw, "部分更新收银分类响应解析失败")
    })
}
fun deleteKasaCategory(id: Any): UTSPromise<Any> {
    return request(kasaCategoryDetailPath(id), "DELETE", _uO(), true)
}
fun getKasaCategoryOptions(query: KasaCategoryOptionsQuery? = null): UTSPromise<KasaCategoryOptionsResponse> {
    return wrapUTSPromise(suspend w@{
            val requestQuery = if (query == null) {
                buildKasaCategoryOptionsQuery(KasaCategoryOptionsQuery(key = null, search = null, q = null, keyword = null, limit = null))
            } else {
                buildKasaCategoryOptionsQuery(query)
            }
            val raw = await(request(kasaCategoryBasePath + "options/", "GET", requestQuery, true))
            return@w buildKasaCategoryOptionsResponse(raw)
    })
}
fun getKasaCategoryTaxRates(): UTSPromise<KasaCategoryTaxRatesResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(kasaCategoryBasePath + "options/", "GET", buildKasaCategoryOptionsQuery(KasaCategoryOptionsQuery(key = "tax_rate", search = null, q = null, keyword = null, limit = null)), true))
            return@w buildTaxRatesResponse(raw)
    })
}
fun getKasaCategoryStatistics(query: KasaCategoryListQuery? = null): UTSPromise<KasaCategoryStatisticsResponse> {
    return wrapUTSPromise(suspend w@{
            val requestQuery = if (query == null) {
                _uO()
            } else {
                buildKasaCategoryListQuery(query)
            }
            val raw = await(request(kasaCategoryBasePath + "statistics/", "GET", requestQuery, true))
            return@w KasaCategoryStatisticsResponse(data = buildObjectResponse(raw, "收银分类统计响应解析失败"))
    })
}
open class SelectOption__2 (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var text: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return SelectOption__2ReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class SelectOption__2ReactiveObject : SelectOption__2, IUTSReactive<SelectOption__2> {
    override var __v_raw: SelectOption__2
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: SelectOption__2, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(value = __v_raw.value, text = __v_raw.text) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): SelectOption__2ReactiveObject {
        return SelectOption__2ReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var value: String
        get() {
            return _tRG(__v_raw, "value", __v_raw.value, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("value")) {
                return
            }
            val oldValue = __v_raw.value
            __v_raw.value = value
            _tRS(__v_raw, "value", oldValue, value)
        }
    override var text: String
        get() {
            return _tRG(__v_raw, "text", __v_raw.text, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("text")) {
                return
            }
            val oldValue = __v_raw.text
            __v_raw.text = value
            _tRS(__v_raw, "text", oldValue, value)
        }
}
val GenPagesKasaCategoryIndexClass = CreateVueComponent(GenPagesKasaCategoryIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesKasaCategoryIndex.inheritAttrs, inject = GenPagesKasaCategoryIndex.inject, props = GenPagesKasaCategoryIndex.props, propsNeedCastKeys = GenPagesKasaCategoryIndex.propsNeedCastKeys, emits = GenPagesKasaCategoryIndex.emits, components = GenPagesKasaCategoryIndex.components, styles = GenPagesKasaCategoryIndex.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesKasaCategoryIndex.setup(props as GenPagesKasaCategoryIndex)
    }
    )
}
, fun(instance, renderer): GenPagesKasaCategoryIndex {
    return GenPagesKasaCategoryIndex(instance, renderer)
}
)
open class SelectOption__3 (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var text: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return SelectOption__3ReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class SelectOption__3ReactiveObject : SelectOption__3, IUTSReactive<SelectOption__3> {
    override var __v_raw: SelectOption__3
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: SelectOption__3, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(value = __v_raw.value, text = __v_raw.text) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): SelectOption__3ReactiveObject {
        return SelectOption__3ReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var value: String
        get() {
            return _tRG(__v_raw, "value", __v_raw.value, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("value")) {
                return
            }
            val oldValue = __v_raw.value
            __v_raw.value = value
            _tRS(__v_raw, "value", oldValue, value)
        }
    override var text: String
        get() {
            return _tRG(__v_raw, "text", __v_raw.text, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("text")) {
                return
            }
            val oldValue = __v_raw.text
            __v_raw.text = value
            _tRS(__v_raw, "text", oldValue, value)
        }
}
val GenPagesKasaCategoryFormClass = CreateVueComponent(GenPagesKasaCategoryForm::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesKasaCategoryForm.inheritAttrs, inject = GenPagesKasaCategoryForm.inject, props = GenPagesKasaCategoryForm.props, propsNeedCastKeys = GenPagesKasaCategoryForm.propsNeedCastKeys, emits = GenPagesKasaCategoryForm.emits, components = GenPagesKasaCategoryForm.components, styles = GenPagesKasaCategoryForm.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesKasaCategoryForm.setup(props as GenPagesKasaCategoryForm)
    }
    )
}
, fun(instance, renderer): GenPagesKasaCategoryForm {
    return GenPagesKasaCategoryForm(instance, renderer)
}
)
val categoryBasePath = "/api/categories/categories/"
open class CategoryListQuery (
    open var search: String? = null,
    @JsonNotNull
    open var page: Number,
    @JsonNotNull
    open var page_size: Number,
    open var id: Any? = null,
    open var is_active: Any? = null,
    open var level: Any? = null,
    open var parent: Any? = null,
    open var parent_id: Any? = null,
    open var code: String? = null,
    open var tax_rate: Any? = null,
    open var kasa_category: Any? = null,
    open var kasa_category_id: Any? = null,
    open var status: String? = null,
    open var ordering: String? = null,
    open var simple: Boolean? = null,
) : UTSObject()
open class CategoryItem (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var code: String,
    @JsonNotNull
    open var level: Number,
    @JsonNotNull
    open var parent_id: Number,
    @JsonNotNull
    open var sort_order: Number,
    @JsonNotNull
    open var tax_rate: String,
    @JsonNotNull
    open var kasa_category_id: Number,
    @JsonNotNull
    open var products_count: Number,
    @JsonNotNull
    open var children_count: Number,
    @JsonNotNull
    open var is_active: Boolean = false,
    @JsonNotNull
    open var is_leaf: Boolean = false,
    @JsonNotNull
    open var full_name: String,
    @JsonNotNull
    open var path: String,
    @JsonNotNull
    open var created_at: String,
    @JsonNotNull
    open var updated_at: String,
    @JsonNotNull
    open var raw: UTSJSONObject,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return CategoryItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class CategoryItemReactiveObject : CategoryItem, IUTSReactive<CategoryItem> {
    override var __v_raw: CategoryItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: CategoryItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, name = __v_raw.name, code = __v_raw.code, level = __v_raw.level, parent_id = __v_raw.parent_id, sort_order = __v_raw.sort_order, tax_rate = __v_raw.tax_rate, kasa_category_id = __v_raw.kasa_category_id, products_count = __v_raw.products_count, children_count = __v_raw.children_count, is_active = __v_raw.is_active, is_leaf = __v_raw.is_leaf, full_name = __v_raw.full_name, path = __v_raw.path, created_at = __v_raw.created_at, updated_at = __v_raw.updated_at, raw = __v_raw.raw) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): CategoryItemReactiveObject {
        return CategoryItemReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return _tRG(__v_raw, "id", __v_raw.id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            _tRS(__v_raw, "id", oldValue, value)
        }
    override var name: String
        get() {
            return _tRG(__v_raw, "name", __v_raw.name, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("name")) {
                return
            }
            val oldValue = __v_raw.name
            __v_raw.name = value
            _tRS(__v_raw, "name", oldValue, value)
        }
    override var code: String
        get() {
            return _tRG(__v_raw, "code", __v_raw.code, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("code")) {
                return
            }
            val oldValue = __v_raw.code
            __v_raw.code = value
            _tRS(__v_raw, "code", oldValue, value)
        }
    override var level: Number
        get() {
            return _tRG(__v_raw, "level", __v_raw.level, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("level")) {
                return
            }
            val oldValue = __v_raw.level
            __v_raw.level = value
            _tRS(__v_raw, "level", oldValue, value)
        }
    override var parent_id: Number
        get() {
            return _tRG(__v_raw, "parent_id", __v_raw.parent_id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("parent_id")) {
                return
            }
            val oldValue = __v_raw.parent_id
            __v_raw.parent_id = value
            _tRS(__v_raw, "parent_id", oldValue, value)
        }
    override var sort_order: Number
        get() {
            return _tRG(__v_raw, "sort_order", __v_raw.sort_order, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("sort_order")) {
                return
            }
            val oldValue = __v_raw.sort_order
            __v_raw.sort_order = value
            _tRS(__v_raw, "sort_order", oldValue, value)
        }
    override var tax_rate: String
        get() {
            return _tRG(__v_raw, "tax_rate", __v_raw.tax_rate, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("tax_rate")) {
                return
            }
            val oldValue = __v_raw.tax_rate
            __v_raw.tax_rate = value
            _tRS(__v_raw, "tax_rate", oldValue, value)
        }
    override var kasa_category_id: Number
        get() {
            return _tRG(__v_raw, "kasa_category_id", __v_raw.kasa_category_id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("kasa_category_id")) {
                return
            }
            val oldValue = __v_raw.kasa_category_id
            __v_raw.kasa_category_id = value
            _tRS(__v_raw, "kasa_category_id", oldValue, value)
        }
    override var products_count: Number
        get() {
            return _tRG(__v_raw, "products_count", __v_raw.products_count, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("products_count")) {
                return
            }
            val oldValue = __v_raw.products_count
            __v_raw.products_count = value
            _tRS(__v_raw, "products_count", oldValue, value)
        }
    override var children_count: Number
        get() {
            return _tRG(__v_raw, "children_count", __v_raw.children_count, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("children_count")) {
                return
            }
            val oldValue = __v_raw.children_count
            __v_raw.children_count = value
            _tRS(__v_raw, "children_count", oldValue, value)
        }
    override var is_active: Boolean
        get() {
            return _tRG(__v_raw, "is_active", __v_raw.is_active, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("is_active")) {
                return
            }
            val oldValue = __v_raw.is_active
            __v_raw.is_active = value
            _tRS(__v_raw, "is_active", oldValue, value)
        }
    override var is_leaf: Boolean
        get() {
            return _tRG(__v_raw, "is_leaf", __v_raw.is_leaf, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("is_leaf")) {
                return
            }
            val oldValue = __v_raw.is_leaf
            __v_raw.is_leaf = value
            _tRS(__v_raw, "is_leaf", oldValue, value)
        }
    override var full_name: String
        get() {
            return _tRG(__v_raw, "full_name", __v_raw.full_name, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("full_name")) {
                return
            }
            val oldValue = __v_raw.full_name
            __v_raw.full_name = value
            _tRS(__v_raw, "full_name", oldValue, value)
        }
    override var path: String
        get() {
            return _tRG(__v_raw, "path", __v_raw.path, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("path")) {
                return
            }
            val oldValue = __v_raw.path
            __v_raw.path = value
            _tRS(__v_raw, "path", oldValue, value)
        }
    override var created_at: String
        get() {
            return _tRG(__v_raw, "created_at", __v_raw.created_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("created_at")) {
                return
            }
            val oldValue = __v_raw.created_at
            __v_raw.created_at = value
            _tRS(__v_raw, "created_at", oldValue, value)
        }
    override var updated_at: String
        get() {
            return _tRG(__v_raw, "updated_at", __v_raw.updated_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("updated_at")) {
                return
            }
            val oldValue = __v_raw.updated_at
            __v_raw.updated_at = value
            _tRS(__v_raw, "updated_at", oldValue, value)
        }
    override var raw: UTSJSONObject
        get() {
            return _tRG(__v_raw, "raw", __v_raw.raw, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("raw")) {
                return
            }
            val oldValue = __v_raw.raw
            __v_raw.raw = value
            _tRS(__v_raw, "raw", oldValue, value)
        }
}
open class CategoryListResponse (
    @JsonNotNull
    open var results: UTSArray<CategoryItem>,
    @JsonNotNull
    open var count: Number,
    @JsonNotNull
    open var total_count: Number,
    @JsonNotNull
    open var total_pages: Number,
    @JsonNotNull
    open var current_page: Number,
    @JsonNotNull
    open var page_size: Number,
) : UTSObject()
typealias CategoryMutationData = UTSJSONObject
open class CategoryRootsQuery (
    open var search: String? = null,
    open var level: Any? = null,
    open var status: String? = null,
    open var ordering: String? = null,
) : UTSObject()
fun stringValue__6(value: Any?): String {
    if (value == null) {
        return ""
    }
    return "" + value
}
fun intValue__5(value: Any?): Number {
    if (value == null) {
        return 0
    }
    val text = stringValue__6(value)
    if (text == "") {
        return 0
    }
    val parsed = parseInt(text)
    if (isNaN(parsed)) {
        return 0
    }
    return parsed
}
fun booleanValue__3(value: Any?): Boolean {
    val text = stringValue__6(value).toLowerCase()
    return text == "true" || text == "1" || text == "yes"
}
fun parseObject__2(value: Any?): UTSJSONObject? {
    if (value == null) {
        return null
    }
    val text = JSON.stringify(value)
    if (text == null || text == "") {
        return null
    }
    return JSON.parseObject<UTSJSONObject>(text)
}
fun parseObjectArray__2(value: Any?): UTSArray<UTSJSONObject> {
    if (value == null) {
        return _uA()
    }
    val text = JSON.stringify(value)
    if (text == null || text == "") {
        return _uA()
    }
    val parsed = JSON.parseArray<UTSJSONObject>(text)
    if (parsed == null) {
        return _uA()
    }
    return parsed!!
}
fun buildCategoryItemFromObject(rawObject: UTSJSONObject): CategoryItem {
    return CategoryItem(id = intValue__5(rawObject["id"]), name = stringValue__6(rawObject["name"]), code = stringValue__6(rawObject["code"]), level = intValue__5(rawObject["level"]), parent_id = intValue__5(if (rawObject["parent_id"] != null) {
        rawObject["parent_id"]
    } else {
        rawObject["parent"]
    }
    ), sort_order = intValue__5(rawObject["sort_order"]), tax_rate = stringValue__6(rawObject["tax_rate"]), kasa_category_id = intValue__5(if (rawObject["kasa_category_id"] != null) {
        rawObject["kasa_category_id"]
    } else {
        rawObject["kasa_category"]
    }
    ), products_count = intValue__5(rawObject["products_count"]), children_count = intValue__5(rawObject["children_count"]), is_active = booleanValue__3(rawObject["is_active"]), is_leaf = booleanValue__3(rawObject["is_leaf"]) || stringValue__6(rawObject["status"]) == "leaf", full_name = stringValue__6(rawObject["full_name"]), path = stringValue__6(rawObject["path"]), created_at = stringValue__6(rawObject["created_at"]), updated_at = stringValue__6(rawObject["updated_at"]), raw = rawObject)
}
fun buildCategoryArrayFromValue(value: Any?): UTSArray<CategoryItem> {
    val rawObject = parseObject__2(value)
    if (rawObject != null) {
        if (rawObject["results"] != null) {
            return buildCategoryArrayFromValue(rawObject["results"])
        }
        if (rawObject["items"] != null) {
            return buildCategoryArrayFromValue(rawObject["items"])
        }
        if (rawObject["children"] != null) {
            return buildCategoryArrayFromValue(rawObject["children"])
        }
        if (rawObject["data"] != null) {
            return buildCategoryArrayFromValue(rawObject["data"])
        }
    }
    val rawArray = parseObjectArray__2(value)
    val result: UTSArray<CategoryItem> = _uA()
    run {
        var index: Number = 0
        while(index < rawArray.length){
            result.push(buildCategoryItemFromObject(rawArray[index]))
            index += 1
        }
    }
    return result
}
fun buildCategoryListQuery(data: CategoryListQuery): UTSJSONObject {
    val query: UTSJSONObject = _uO("page" to data.page, "page_size" to data.page_size)
    if (data.search != null && data.search != "") {
        query["search"] = data.search
    }
    if (data.id != null && stringValue__6(data.id) != "") {
        query["id"] = data.id
    }
    if (data.is_active != null && stringValue__6(data.is_active) != "") {
        query["is_active"] = data.is_active
    }
    if (data.level != null && stringValue__6(data.level) != "") {
        query["level"] = data.level
    }
    if (data.parent != null) {
        query["parent"] = data.parent
    }
    if (data.parent_id != null && stringValue__6(data.parent_id) != "") {
        query["parent_id"] = data.parent_id
    }
    if (data.code != null && data.code != "") {
        query["code"] = data.code
    }
    if (data.tax_rate != null && stringValue__6(data.tax_rate) != "") {
        query["tax_rate"] = data.tax_rate
    }
    if (data.kasa_category != null && stringValue__6(data.kasa_category) != "") {
        query["kasa_category"] = data.kasa_category
    }
    if (data.kasa_category_id != null && stringValue__6(data.kasa_category_id) != "") {
        query["kasa_category_id"] = data.kasa_category_id
    }
    if (data.status != null && data.status != "") {
        query["status"] = data.status
    }
    if (data.ordering != null && data.ordering != "") {
        query["ordering"] = data.ordering
    }
    if (data.simple != null) {
        query["simple"] = data.simple
    }
    return query
}
fun buildCategoryListResponse(raw: Any, query: CategoryListQuery): CategoryListResponse {
    val rawObject = parseObject__2(raw)
    if (rawObject == null) {
        val results = buildCategoryArrayFromValue(raw)
        return CategoryListResponse(results = results, count = results.length, total_count = results.length, total_pages = 1, current_page = if (query.page > 0) {
            query.page
        } else {
            1
        }
        , page_size = if (query.page_size > 0) {
            query.page_size
        } else {
            results.length
        }
        )
    }
    var paginationObject: UTSJSONObject? = null
    if (rawObject["pagination"] != null) {
        paginationObject = parseObject__2(rawObject["pagination"])
    }
    var results: UTSArray<CategoryItem> = _uA()
    if (rawObject["results"] != null) {
        results = buildCategoryArrayFromValue(rawObject["results"])
    } else if (rawObject["items"] != null) {
        results = buildCategoryArrayFromValue(rawObject["items"])
    } else {
        results = buildCategoryArrayFromValue(raw)
    }
    var totalCount = intValue__5(rawObject["count"])
    if (totalCount <= 0) {
        totalCount = intValue__5(rawObject["total"])
    }
    if (totalCount <= 0) {
        totalCount = intValue__5(rawObject["total_count"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__5(paginationObject["total"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__5(paginationObject["count"])
    }
    if (totalCount <= 0) {
        totalCount = results.length
    }
    var currentPage = intValue__5(rawObject["page"])
    if (currentPage <= 0) {
        currentPage = intValue__5(rawObject["current_page"])
    }
    if (currentPage <= 0 && paginationObject != null) {
        currentPage = intValue__5(paginationObject["page"])
    }
    if (currentPage <= 0) {
        currentPage = if (query.page > 0) {
            query.page
        } else {
            1
        }
    }
    var pageSize = intValue__5(rawObject["page_size"])
    if (pageSize <= 0) {
        pageSize = intValue__5(rawObject["per_page"])
    }
    if (pageSize <= 0 && paginationObject != null) {
        pageSize = intValue__5(paginationObject["page_size"])
    }
    if (pageSize <= 0 && paginationObject != null) {
        pageSize = intValue__5(paginationObject["per_page"])
    }
    if (pageSize <= 0) {
        pageSize = if (query.page_size > 0) {
            query.page_size
        } else {
            results.length
        }
    }
    var totalPages = intValue__5(rawObject["total_pages"])
    if (totalPages <= 0) {
        totalPages = intValue__5(rawObject["num_pages"])
    }
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__5(paginationObject["total_pages"])
    }
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__5(paginationObject["num_pages"])
    }
    if (totalPages <= 0 && pageSize > 0) {
        totalPages = Math.ceil(totalCount / pageSize)
    }
    if (totalPages <= 0) {
        totalPages = 1
    }
    return CategoryListResponse(results = results, count = totalCount, total_count = totalCount, total_pages = totalPages, current_page = currentPage, page_size = pageSize)
}
fun buildCategoryItemResponse(raw: Any, errorMessage: String): CategoryItem {
    val rawObject = parseObject__2(raw)
    if (rawObject == null) {
        throw UTSError(errorMessage)
    }
    return buildCategoryItemFromObject(rawObject)
}
fun buildSearchQuery(search: String?): UTSJSONObject {
    val query: UTSJSONObject = _uO()
    if (search != null && search != "") {
        query["search"] = search
    }
    return query
}
fun buildCategoryRootsQuery(data: CategoryRootsQuery): UTSJSONObject {
    val query: UTSJSONObject = _uO()
    if (data.search != null && data.search != "") {
        query["search"] = data.search
    }
    if (data.level != null && stringValue__6(data.level) != "") {
        query["level"] = data.level
    }
    if (data.status != null && data.status != "") {
        query["status"] = data.status
    }
    if (data.ordering != null && data.ordering != "") {
        query["ordering"] = data.ordering
    }
    return query
}
fun categoryDetailPath(id: Any): String {
    return categoryBasePath + stringValue__6(id) + "/"
}
fun getCategoryList(data: CategoryListQuery): UTSPromise<CategoryListResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(categoryBasePath, "GET", buildCategoryListQuery(data), true))
            return@w buildCategoryListResponse(raw, data)
    })
}
fun getCategoryDetail(id: Any): UTSPromise<CategoryItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(categoryDetailPath(id), "GET", _uO(), true))
            return@w buildCategoryItemResponse(raw, "分类详情响应解析失败")
    })
}
fun createCategory(data: CategoryMutationData): UTSPromise<CategoryItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(categoryBasePath, "POST", data, true))
            return@w buildCategoryItemResponse(raw, "创建分类响应解析失败")
    })
}
fun updateCategory(id: Any, data: CategoryMutationData): UTSPromise<CategoryItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(categoryDetailPath(id), "PUT", data, true))
            return@w buildCategoryItemResponse(raw, "更新分类响应解析失败")
    })
}
fun deleteCategory(id: Any): UTSPromise<Any> {
    return request(categoryDetailPath(id), "DELETE", _uO(), true)
}
fun getCategoryRoots(query: CategoryRootsQuery? = null): UTSPromise<UTSArray<CategoryItem>> {
    return wrapUTSPromise(suspend w@{
            val requestQuery = if (query == null) {
                buildCategoryRootsQuery(CategoryRootsQuery(search = null, level = null, status = null, ordering = null))
            } else {
                buildCategoryRootsQuery(query)
            }
            val raw = await(request(categoryBasePath + "roots/", "GET", requestQuery, true))
            return@w buildCategoryArrayFromValue(raw)
    })
}
fun getCategoryChildren(id: Any, search: String? = null): UTSPromise<UTSArray<CategoryItem>> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(categoryDetailPath(id) + "children/", "GET", buildSearchQuery(search), true))
            return@w buildCategoryArrayFromValue(raw)
    })
}
open class FilterOption (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var text: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return FilterOptionReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class FilterOptionReactiveObject : FilterOption, IUTSReactive<FilterOption> {
    override var __v_raw: FilterOption
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: FilterOption, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(value = __v_raw.value, text = __v_raw.text) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): FilterOptionReactiveObject {
        return FilterOptionReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var value: String
        get() {
            return _tRG(__v_raw, "value", __v_raw.value, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("value")) {
                return
            }
            val oldValue = __v_raw.value
            __v_raw.value = value
            _tRS(__v_raw, "value", oldValue, value)
        }
    override var text: String
        get() {
            return _tRG(__v_raw, "text", __v_raw.text, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("text")) {
                return
            }
            val oldValue = __v_raw.text
            __v_raw.text = value
            _tRS(__v_raw, "text", oldValue, value)
        }
}
open class ChildGroup (
    @JsonNotNull
    open var parentId: Number,
    @JsonNotNull
    open var items: UTSArray<CategoryItem>,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ChildGroupReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ChildGroupReactiveObject : ChildGroup, IUTSReactive<ChildGroup> {
    override var __v_raw: ChildGroup
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ChildGroup, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(parentId = __v_raw.parentId, items = __v_raw.items) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ChildGroupReactiveObject {
        return ChildGroupReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var parentId: Number
        get() {
            return _tRG(__v_raw, "parentId", __v_raw.parentId, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("parentId")) {
                return
            }
            val oldValue = __v_raw.parentId
            __v_raw.parentId = value
            _tRS(__v_raw, "parentId", oldValue, value)
        }
    override var items: UTSArray<CategoryItem>
        get() {
            return _tRG(__v_raw, "items", __v_raw.items, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("items")) {
                return
            }
            val oldValue = __v_raw.items
            __v_raw.items = value
            _tRS(__v_raw, "items", oldValue, value)
        }
}
val GenPagesCategoryIndexClass = CreateVueComponent(GenPagesCategoryIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesCategoryIndex.inheritAttrs, inject = GenPagesCategoryIndex.inject, props = GenPagesCategoryIndex.props, propsNeedCastKeys = GenPagesCategoryIndex.propsNeedCastKeys, emits = GenPagesCategoryIndex.emits, components = GenPagesCategoryIndex.components, styles = GenPagesCategoryIndex.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesCategoryIndex.setup(props as GenPagesCategoryIndex)
    }
    )
}
, fun(instance, renderer): GenPagesCategoryIndex {
    return GenPagesCategoryIndex(instance, renderer)
}
)
open class SelectOption__4 (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var text: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return SelectOption__4ReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class SelectOption__4ReactiveObject : SelectOption__4, IUTSReactive<SelectOption__4> {
    override var __v_raw: SelectOption__4
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: SelectOption__4, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(value = __v_raw.value, text = __v_raw.text) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): SelectOption__4ReactiveObject {
        return SelectOption__4ReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var value: String
        get() {
            return _tRG(__v_raw, "value", __v_raw.value, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("value")) {
                return
            }
            val oldValue = __v_raw.value
            __v_raw.value = value
            _tRS(__v_raw, "value", oldValue, value)
        }
    override var text: String
        get() {
            return _tRG(__v_raw, "text", __v_raw.text, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("text")) {
                return
            }
            val oldValue = __v_raw.text
            __v_raw.text = value
            _tRS(__v_raw, "text", oldValue, value)
        }
}
val GenPagesCategoryFromClass = CreateVueComponent(GenPagesCategoryFrom::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesCategoryFrom.inheritAttrs, inject = GenPagesCategoryFrom.inject, props = GenPagesCategoryFrom.props, propsNeedCastKeys = GenPagesCategoryFrom.propsNeedCastKeys, emits = GenPagesCategoryFrom.emits, components = GenPagesCategoryFrom.components, styles = GenPagesCategoryFrom.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesCategoryFrom.setup(props as GenPagesCategoryFrom)
    }
    )
}
, fun(instance, renderer): GenPagesCategoryFrom {
    return GenPagesCategoryFrom(instance, renderer)
}
)
open class ShopListQuery (
    open var search: String? = null,
    @JsonNotNull
    open var page: Number,
    @JsonNotNull
    open var page_size: Number,
) : UTSObject()
open class ShopItem (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var address: String,
    @JsonNotNull
    open var company: Number,
    @JsonNotNull
    open var company_name: String,
    @JsonNotNull
    open var media_records_count: Number,
    @JsonNotNull
    open var media_files: UTSArray<ShopMediaFile>,
    @JsonNotNull
    open var created_at: String,
    @JsonNotNull
    open var updated_at: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ShopItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ShopItemReactiveObject : ShopItem, IUTSReactive<ShopItem> {
    override var __v_raw: ShopItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ShopItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, name = __v_raw.name, address = __v_raw.address, company = __v_raw.company, company_name = __v_raw.company_name, media_records_count = __v_raw.media_records_count, media_files = __v_raw.media_files, created_at = __v_raw.created_at, updated_at = __v_raw.updated_at) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ShopItemReactiveObject {
        return ShopItemReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return _tRG(__v_raw, "id", __v_raw.id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            _tRS(__v_raw, "id", oldValue, value)
        }
    override var name: String
        get() {
            return _tRG(__v_raw, "name", __v_raw.name, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("name")) {
                return
            }
            val oldValue = __v_raw.name
            __v_raw.name = value
            _tRS(__v_raw, "name", oldValue, value)
        }
    override var address: String
        get() {
            return _tRG(__v_raw, "address", __v_raw.address, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("address")) {
                return
            }
            val oldValue = __v_raw.address
            __v_raw.address = value
            _tRS(__v_raw, "address", oldValue, value)
        }
    override var company: Number
        get() {
            return _tRG(__v_raw, "company", __v_raw.company, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("company")) {
                return
            }
            val oldValue = __v_raw.company
            __v_raw.company = value
            _tRS(__v_raw, "company", oldValue, value)
        }
    override var company_name: String
        get() {
            return _tRG(__v_raw, "company_name", __v_raw.company_name, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("company_name")) {
                return
            }
            val oldValue = __v_raw.company_name
            __v_raw.company_name = value
            _tRS(__v_raw, "company_name", oldValue, value)
        }
    override var media_records_count: Number
        get() {
            return _tRG(__v_raw, "media_records_count", __v_raw.media_records_count, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("media_records_count")) {
                return
            }
            val oldValue = __v_raw.media_records_count
            __v_raw.media_records_count = value
            _tRS(__v_raw, "media_records_count", oldValue, value)
        }
    override var media_files: UTSArray<ShopMediaFile>
        get() {
            return _tRG(__v_raw, "media_files", __v_raw.media_files, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("media_files")) {
                return
            }
            val oldValue = __v_raw.media_files
            __v_raw.media_files = value
            _tRS(__v_raw, "media_files", oldValue, value)
        }
    override var created_at: String
        get() {
            return _tRG(__v_raw, "created_at", __v_raw.created_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("created_at")) {
                return
            }
            val oldValue = __v_raw.created_at
            __v_raw.created_at = value
            _tRS(__v_raw, "created_at", oldValue, value)
        }
    override var updated_at: String
        get() {
            return _tRG(__v_raw, "updated_at", __v_raw.updated_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("updated_at")) {
                return
            }
            val oldValue = __v_raw.updated_at
            __v_raw.updated_at = value
            _tRS(__v_raw, "updated_at", oldValue, value)
        }
}
open class ShopListResponse (
    @JsonNotNull
    open var results: UTSArray<ShopItem>,
    @JsonNotNull
    open var count: Number,
    @JsonNotNull
    open var total_count: Number,
    @JsonNotNull
    open var total_pages: Number,
    @JsonNotNull
    open var current_page: Number,
    @JsonNotNull
    open var page_size: Number,
) : UTSObject()
open class ShopMediaListQuery (
    open var search: String? = null,
    open var shop: Any? = null,
    @JsonNotNull
    open var page: Number,
    @JsonNotNull
    open var page_size: Number,
) : UTSObject()
open class ShopMediaFile (
    @JsonNotNull
    open var id: String,
    @JsonNotNull
    open var company: Number,
    @JsonNotNull
    open var original_filename: String,
    @JsonNotNull
    open var file_type: String,
    @JsonNotNull
    open var file_type_display: String,
    @JsonNotNull
    open var mime_type: String,
    @JsonNotNull
    open var file_size: Number,
    @JsonNotNull
    open var file_size_display: String,
    @JsonNotNull
    open var file_url: String,
    @JsonNotNull
    open var thumbnail_url: String,
    @JsonNotNull
    open var signed_url: String,
    @JsonNotNull
    open var signed_thumbnail_url: String,
    @JsonNotNull
    open var object_id: String,
    @JsonNotNull
    open var is_deleted: Boolean = false,
    @JsonNotNull
    open var created_at: String,
    @JsonNotNull
    open var updated_at: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ShopMediaFileReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ShopMediaFileReactiveObject : ShopMediaFile, IUTSReactive<ShopMediaFile> {
    override var __v_raw: ShopMediaFile
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ShopMediaFile, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, company = __v_raw.company, original_filename = __v_raw.original_filename, file_type = __v_raw.file_type, file_type_display = __v_raw.file_type_display, mime_type = __v_raw.mime_type, file_size = __v_raw.file_size, file_size_display = __v_raw.file_size_display, file_url = __v_raw.file_url, thumbnail_url = __v_raw.thumbnail_url, signed_url = __v_raw.signed_url, signed_thumbnail_url = __v_raw.signed_thumbnail_url, object_id = __v_raw.object_id, is_deleted = __v_raw.is_deleted, created_at = __v_raw.created_at, updated_at = __v_raw.updated_at) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ShopMediaFileReactiveObject {
        return ShopMediaFileReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: String
        get() {
            return _tRG(__v_raw, "id", __v_raw.id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            _tRS(__v_raw, "id", oldValue, value)
        }
    override var company: Number
        get() {
            return _tRG(__v_raw, "company", __v_raw.company, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("company")) {
                return
            }
            val oldValue = __v_raw.company
            __v_raw.company = value
            _tRS(__v_raw, "company", oldValue, value)
        }
    override var original_filename: String
        get() {
            return _tRG(__v_raw, "original_filename", __v_raw.original_filename, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("original_filename")) {
                return
            }
            val oldValue = __v_raw.original_filename
            __v_raw.original_filename = value
            _tRS(__v_raw, "original_filename", oldValue, value)
        }
    override var file_type: String
        get() {
            return _tRG(__v_raw, "file_type", __v_raw.file_type, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("file_type")) {
                return
            }
            val oldValue = __v_raw.file_type
            __v_raw.file_type = value
            _tRS(__v_raw, "file_type", oldValue, value)
        }
    override var file_type_display: String
        get() {
            return _tRG(__v_raw, "file_type_display", __v_raw.file_type_display, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("file_type_display")) {
                return
            }
            val oldValue = __v_raw.file_type_display
            __v_raw.file_type_display = value
            _tRS(__v_raw, "file_type_display", oldValue, value)
        }
    override var mime_type: String
        get() {
            return _tRG(__v_raw, "mime_type", __v_raw.mime_type, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("mime_type")) {
                return
            }
            val oldValue = __v_raw.mime_type
            __v_raw.mime_type = value
            _tRS(__v_raw, "mime_type", oldValue, value)
        }
    override var file_size: Number
        get() {
            return _tRG(__v_raw, "file_size", __v_raw.file_size, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("file_size")) {
                return
            }
            val oldValue = __v_raw.file_size
            __v_raw.file_size = value
            _tRS(__v_raw, "file_size", oldValue, value)
        }
    override var file_size_display: String
        get() {
            return _tRG(__v_raw, "file_size_display", __v_raw.file_size_display, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("file_size_display")) {
                return
            }
            val oldValue = __v_raw.file_size_display
            __v_raw.file_size_display = value
            _tRS(__v_raw, "file_size_display", oldValue, value)
        }
    override var file_url: String
        get() {
            return _tRG(__v_raw, "file_url", __v_raw.file_url, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("file_url")) {
                return
            }
            val oldValue = __v_raw.file_url
            __v_raw.file_url = value
            _tRS(__v_raw, "file_url", oldValue, value)
        }
    override var thumbnail_url: String
        get() {
            return _tRG(__v_raw, "thumbnail_url", __v_raw.thumbnail_url, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("thumbnail_url")) {
                return
            }
            val oldValue = __v_raw.thumbnail_url
            __v_raw.thumbnail_url = value
            _tRS(__v_raw, "thumbnail_url", oldValue, value)
        }
    override var signed_url: String
        get() {
            return _tRG(__v_raw, "signed_url", __v_raw.signed_url, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("signed_url")) {
                return
            }
            val oldValue = __v_raw.signed_url
            __v_raw.signed_url = value
            _tRS(__v_raw, "signed_url", oldValue, value)
        }
    override var signed_thumbnail_url: String
        get() {
            return _tRG(__v_raw, "signed_thumbnail_url", __v_raw.signed_thumbnail_url, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("signed_thumbnail_url")) {
                return
            }
            val oldValue = __v_raw.signed_thumbnail_url
            __v_raw.signed_thumbnail_url = value
            _tRS(__v_raw, "signed_thumbnail_url", oldValue, value)
        }
    override var object_id: String
        get() {
            return _tRG(__v_raw, "object_id", __v_raw.object_id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("object_id")) {
                return
            }
            val oldValue = __v_raw.object_id
            __v_raw.object_id = value
            _tRS(__v_raw, "object_id", oldValue, value)
        }
    override var is_deleted: Boolean
        get() {
            return _tRG(__v_raw, "is_deleted", __v_raw.is_deleted, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("is_deleted")) {
                return
            }
            val oldValue = __v_raw.is_deleted
            __v_raw.is_deleted = value
            _tRS(__v_raw, "is_deleted", oldValue, value)
        }
    override var created_at: String
        get() {
            return _tRG(__v_raw, "created_at", __v_raw.created_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("created_at")) {
                return
            }
            val oldValue = __v_raw.created_at
            __v_raw.created_at = value
            _tRS(__v_raw, "created_at", oldValue, value)
        }
    override var updated_at: String
        get() {
            return _tRG(__v_raw, "updated_at", __v_raw.updated_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("updated_at")) {
                return
            }
            val oldValue = __v_raw.updated_at
            __v_raw.updated_at = value
            _tRS(__v_raw, "updated_at", oldValue, value)
        }
}
open class ShopMediaItem (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var shop: Number,
    @JsonNotNull
    open var shop_name: String,
    @JsonNotNull
    open var title: String,
    @JsonNotNull
    open var record_type: String,
    @JsonNotNull
    open var record_type_display: String,
    @JsonNotNull
    open var expiration_date: String,
    @JsonNotNull
    open var notes: String,
    @JsonNotNull
    open var media_files: UTSArray<ShopMediaFile>,
    @JsonNotNull
    open var files_count: Number,
    @JsonNotNull
    open var created_at: String,
    @JsonNotNull
    open var updated_at: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ShopMediaItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ShopMediaItemReactiveObject : ShopMediaItem, IUTSReactive<ShopMediaItem> {
    override var __v_raw: ShopMediaItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ShopMediaItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, shop = __v_raw.shop, shop_name = __v_raw.shop_name, title = __v_raw.title, record_type = __v_raw.record_type, record_type_display = __v_raw.record_type_display, expiration_date = __v_raw.expiration_date, notes = __v_raw.notes, media_files = __v_raw.media_files, files_count = __v_raw.files_count, created_at = __v_raw.created_at, updated_at = __v_raw.updated_at) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ShopMediaItemReactiveObject {
        return ShopMediaItemReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return _tRG(__v_raw, "id", __v_raw.id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            _tRS(__v_raw, "id", oldValue, value)
        }
    override var shop: Number
        get() {
            return _tRG(__v_raw, "shop", __v_raw.shop, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("shop")) {
                return
            }
            val oldValue = __v_raw.shop
            __v_raw.shop = value
            _tRS(__v_raw, "shop", oldValue, value)
        }
    override var shop_name: String
        get() {
            return _tRG(__v_raw, "shop_name", __v_raw.shop_name, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("shop_name")) {
                return
            }
            val oldValue = __v_raw.shop_name
            __v_raw.shop_name = value
            _tRS(__v_raw, "shop_name", oldValue, value)
        }
    override var title: String
        get() {
            return _tRG(__v_raw, "title", __v_raw.title, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("title")) {
                return
            }
            val oldValue = __v_raw.title
            __v_raw.title = value
            _tRS(__v_raw, "title", oldValue, value)
        }
    override var record_type: String
        get() {
            return _tRG(__v_raw, "record_type", __v_raw.record_type, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("record_type")) {
                return
            }
            val oldValue = __v_raw.record_type
            __v_raw.record_type = value
            _tRS(__v_raw, "record_type", oldValue, value)
        }
    override var record_type_display: String
        get() {
            return _tRG(__v_raw, "record_type_display", __v_raw.record_type_display, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("record_type_display")) {
                return
            }
            val oldValue = __v_raw.record_type_display
            __v_raw.record_type_display = value
            _tRS(__v_raw, "record_type_display", oldValue, value)
        }
    override var expiration_date: String
        get() {
            return _tRG(__v_raw, "expiration_date", __v_raw.expiration_date, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("expiration_date")) {
                return
            }
            val oldValue = __v_raw.expiration_date
            __v_raw.expiration_date = value
            _tRS(__v_raw, "expiration_date", oldValue, value)
        }
    override var notes: String
        get() {
            return _tRG(__v_raw, "notes", __v_raw.notes, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("notes")) {
                return
            }
            val oldValue = __v_raw.notes
            __v_raw.notes = value
            _tRS(__v_raw, "notes", oldValue, value)
        }
    override var media_files: UTSArray<ShopMediaFile>
        get() {
            return _tRG(__v_raw, "media_files", __v_raw.media_files, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("media_files")) {
                return
            }
            val oldValue = __v_raw.media_files
            __v_raw.media_files = value
            _tRS(__v_raw, "media_files", oldValue, value)
        }
    override var files_count: Number
        get() {
            return _tRG(__v_raw, "files_count", __v_raw.files_count, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("files_count")) {
                return
            }
            val oldValue = __v_raw.files_count
            __v_raw.files_count = value
            _tRS(__v_raw, "files_count", oldValue, value)
        }
    override var created_at: String
        get() {
            return _tRG(__v_raw, "created_at", __v_raw.created_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("created_at")) {
                return
            }
            val oldValue = __v_raw.created_at
            __v_raw.created_at = value
            _tRS(__v_raw, "created_at", oldValue, value)
        }
    override var updated_at: String
        get() {
            return _tRG(__v_raw, "updated_at", __v_raw.updated_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("updated_at")) {
                return
            }
            val oldValue = __v_raw.updated_at
            __v_raw.updated_at = value
            _tRS(__v_raw, "updated_at", oldValue, value)
        }
}
open class ShopMediaListResponse (
    @JsonNotNull
    open var results: UTSArray<ShopMediaItem>,
    @JsonNotNull
    open var count: Number,
    @JsonNotNull
    open var total_count: Number,
    @JsonNotNull
    open var total_pages: Number,
    @JsonNotNull
    open var current_page: Number,
    @JsonNotNull
    open var page_size: Number,
) : UTSObject()
open class ShopMediaMutationData (
    open var shop: Any? = null,
    @JsonNotNull
    open var title: String,
    open var record_type: String? = null,
    open var expiration_date: String? = null,
    open var notes: String? = null,
) : UTSObject()
val shopBasePath = "/api/shops/shops/"
val shopMediaBasePath = "/api/shops/media/"
fun buildListQuery__2(data: ShopListQuery): UTSJSONObject {
    val query: UTSJSONObject = _uO("page" to data.page, "page_size" to data.page_size)
    if (data.search != null && data.search != "") {
        query["search"] = data.search
    }
    return query
}
fun buildMediaListQuery(data: ShopMediaListQuery): UTSJSONObject {
    val query: UTSJSONObject = _uO("page" to data.page, "page_size" to data.page_size)
    if (data.search != null && data.search != "") {
        query["search"] = data.search
    }
    if (data.shop != null && stringValue__7(data.shop) != "") {
        query["shop"] = data.shop
    }
    return query
}
fun stringValue__7(value: Any?): String {
    if (value == null) {
        return ""
    }
    return "" + value
}
fun intValue__6(value: Any?): Number {
    if (value == null) {
        return 0
    }
    val text = stringValue__7(value)
    if (text == "") {
        return 0
    }
    val parsed = parseInt(text)
    if (isNaN(parsed)) {
        return 0
    }
    return parsed
}
fun boolValue(value: Any?): Boolean {
    if (value == null) {
        return false
    }
    val text = stringValue__7(value).toLowerCase()
    return text == "true" || text == "1" || text == "yes"
}
fun parseObject__3(value: Any?): UTSJSONObject? {
    if (value == null) {
        return null
    }
    val text = JSON.stringify(value)
    if (text == null || text == "") {
        return null
    }
    return JSON.parseObject<UTSJSONObject>(text)
}
fun parseArray(value: Any?): UTSArray<UTSJSONObject> {
    if (value == null) {
        return _uA<UTSJSONObject>()
    }
    val text = JSON.stringify(value)
    if (text == null || text == "") {
        return _uA<UTSJSONObject>()
    }
    val parsed = JSON.parseArray<UTSJSONObject>(text)
    if (parsed == null) {
        return _uA<UTSJSONObject>()
    }
    return parsed
}
fun normalizeServerUrl__3(url: String): String {
    if (url == "") {
        return ""
    }
    if (url.startsWith("http://localhost:8000")) {
        return baseUrl + url.substring(21)
    }
    if (url.startsWith("https://localhost:8000")) {
        return baseUrl + url.substring(22)
    }
    if (url.startsWith("http://127.0.0.1:8000")) {
        return baseUrl + url.substring(21)
    }
    if (url.startsWith("https://127.0.0.1:8000")) {
        return baseUrl + url.substring(22)
    }
    return url
}
fun buildShopItem(rawObject: UTSJSONObject): ShopItem {
    return ShopItem(id = intValue__6(rawObject["id"]), name = stringValue__7(rawObject["name"]), address = stringValue__7(rawObject["address"]), company = intValue__6(rawObject["company"]), company_name = stringValue__7(rawObject["company_name"]), media_records_count = intValue__6(rawObject["media_records_count"]), media_files = buildShopMediaFiles(rawObject["media_files"]), created_at = stringValue__7(rawObject["created_at"]), updated_at = stringValue__7(rawObject["updated_at"]))
}
fun buildShopMediaMutationBody(data: ShopMediaMutationData): UTSJSONObject {
    return _uO("shop" to data.shop, "title" to data.title, "record_type" to data.record_type, "expiration_date" to data.expiration_date, "notes" to if (data.notes == null) {
        ""
    } else {
        data.notes
    }
    )
}
fun buildShopMediaFile(rawObject: UTSJSONObject): ShopMediaFile {
    return ShopMediaFile(id = stringValue__7(rawObject["id"]), company = intValue__6(rawObject["company"]), original_filename = stringValue__7(rawObject["original_filename"]), file_type = stringValue__7(rawObject["file_type"]), file_type_display = stringValue__7(rawObject["file_type_display"]), mime_type = stringValue__7(rawObject["mime_type"]), file_size = intValue__6(rawObject["file_size"]), file_size_display = stringValue__7(rawObject["file_size_display"]), file_url = normalizeServerUrl__3(stringValue__7(rawObject["file_url"])), thumbnail_url = normalizeServerUrl__3(stringValue__7(rawObject["thumbnail_url"])), signed_url = normalizeServerUrl__3(stringValue__7(rawObject["signed_url"])), signed_thumbnail_url = normalizeServerUrl__3(stringValue__7(rawObject["signed_thumbnail_url"])), object_id = stringValue__7(rawObject["object_id"]), is_deleted = boolValue(rawObject["is_deleted"]), created_at = stringValue__7(rawObject["created_at"]), updated_at = stringValue__7(rawObject["updated_at"]))
}
fun buildShopMediaFiles(value: Any?): UTSArray<ShopMediaFile> {
    val rawArray = parseArray(value)
    val result: UTSArray<ShopMediaFile> = _uA()
    run {
        var index: Number = 0
        while(index < rawArray.length){
            result.push(buildShopMediaFile(rawArray[index]))
            index += 1
        }
    }
    return result
}
fun buildShopMediaItem(rawObject: UTSJSONObject): ShopMediaItem {
    return ShopMediaItem(id = intValue__6(rawObject["id"]), shop = intValue__6(rawObject["shop"]), shop_name = stringValue__7(rawObject["shop_name"]), title = stringValue__7(rawObject["title"]), record_type = stringValue__7(rawObject["record_type"]), record_type_display = stringValue__7(rawObject["record_type_display"]), expiration_date = stringValue__7(rawObject["expiration_date"]), notes = stringValue__7(rawObject["notes"]), media_files = buildShopMediaFiles(rawObject["media_files"]), files_count = intValue__6(rawObject["files_count"]), created_at = stringValue__7(rawObject["created_at"]), updated_at = stringValue__7(rawObject["updated_at"]))
}
fun buildShopMediaItemResponse(raw: Any): ShopMediaItem {
    val rawObject = parseObject__3(raw)
    if (rawObject == null) {
        throw UTSError("商店资料详情响应解析失败")
    }
    return buildShopMediaItem(rawObject)
}
fun buildShopItemResponse(raw: Any): ShopItem {
    val rawObject = parseObject__3(raw)
    if (rawObject == null) {
        throw UTSError("商店详情响应解析失败")
    }
    return buildShopItem(rawObject)
}
fun shopDetailPath(id: Any): String {
    return shopBasePath + stringValue__7(id) + "/"
}
fun shopMediaDetailPath(id: Any): String {
    return shopMediaBasePath + stringValue__7(id) + "/"
}
fun buildShopListResponse(raw: Any, query: ShopListQuery): ShopListResponse {
    val rawObject = parseObject__3(raw)
    if (rawObject == null) {
        throw UTSError("商店列表响应解析失败")
    }
    val resultsArray = parseArray(rawObject["results"])
    val results: UTSArray<ShopItem> = _uA()
    run {
        var index: Number = 0
        while(index < resultsArray.length){
            results.push(buildShopItem(resultsArray[index]))
            index += 1
        }
    }
    val paginationObject = parseObject__3(rawObject["pagination"])
    var totalCount = intValue__6(rawObject["count"])
    if (totalCount <= 0) {
        totalCount = intValue__6(rawObject["total"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__6(paginationObject["total"])
    }
    if (totalCount <= 0) {
        totalCount = results.length
    }
    var currentPage = intValue__6(rawObject["page"])
    if (currentPage <= 0 && paginationObject != null) {
        currentPage = intValue__6(paginationObject["page"])
    }
    if (currentPage <= 0) {
        currentPage = if (query.page > 0) {
            query.page
        } else {
            1
        }
    }
    var pageSize = intValue__6(rawObject["page_size"])
    if (pageSize <= 0 && paginationObject != null) {
        pageSize = intValue__6(paginationObject["page_size"])
    }
    if (pageSize <= 0) {
        pageSize = if (query.page_size > 0) {
            query.page_size
        } else {
            results.length
        }
    }
    var totalPages = intValue__6(rawObject["total_pages"])
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__6(paginationObject["total_pages"])
    }
    if (totalPages <= 0 && pageSize > 0) {
        totalPages = Math.ceil(totalCount / pageSize)
    }
    if (totalPages <= 0) {
        totalPages = 1
    }
    return ShopListResponse(results = results, count = totalCount, total_count = totalCount, total_pages = totalPages, current_page = currentPage, page_size = pageSize)
}
fun buildShopMediaListResponse(raw: Any, query: ShopMediaListQuery): ShopMediaListResponse {
    val rawObject = parseObject__3(raw)
    if (rawObject == null) {
        throw UTSError("商店媒体列表响应解析失败")
    }
    val resultsArray = parseArray(rawObject["results"])
    val results: UTSArray<ShopMediaItem> = _uA()
    run {
        var index: Number = 0
        while(index < resultsArray.length){
            results.push(buildShopMediaItem(resultsArray[index]))
            index += 1
        }
    }
    val paginationObject = parseObject__3(rawObject["pagination"])
    var totalCount = intValue__6(rawObject["count"])
    if (totalCount <= 0) {
        totalCount = intValue__6(rawObject["total"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__6(paginationObject["total"])
    }
    if (totalCount <= 0) {
        totalCount = results.length
    }
    var currentPage = intValue__6(rawObject["page"])
    if (currentPage <= 0 && paginationObject != null) {
        currentPage = intValue__6(paginationObject["page"])
    }
    if (currentPage <= 0) {
        currentPage = if (query.page > 0) {
            query.page
        } else {
            1
        }
    }
    var pageSize = intValue__6(rawObject["page_size"])
    if (pageSize <= 0 && paginationObject != null) {
        pageSize = intValue__6(paginationObject["page_size"])
    }
    if (pageSize <= 0) {
        pageSize = if (query.page_size > 0) {
            query.page_size
        } else {
            results.length
        }
    }
    var totalPages = intValue__6(rawObject["total_pages"])
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__6(paginationObject["total_pages"])
    }
    if (totalPages <= 0 && pageSize > 0) {
        totalPages = Math.ceil(totalCount / pageSize)
    }
    if (totalPages <= 0) {
        totalPages = 1
    }
    return ShopMediaListResponse(results = results, count = totalCount, total_count = totalCount, total_pages = totalPages, current_page = currentPage, page_size = pageSize)
}
fun getShopList(query: ShopListQuery): UTSPromise<ShopListResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(shopBasePath, "GET", buildListQuery__2(query), false))
            return@w buildShopListResponse(raw, query)
    })
}
fun getShopDetail(id: Any): UTSPromise<ShopItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(shopDetailPath(id), "GET", _uO(), false))
            return@w buildShopItemResponse(raw)
    })
}
fun getShopMediaList(query: ShopMediaListQuery): UTSPromise<ShopMediaListResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(shopMediaBasePath, "GET", buildMediaListQuery(query), false))
            return@w buildShopMediaListResponse(raw, query)
    })
}
fun getShopMediaDetail(id: Any): UTSPromise<ShopMediaItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(shopMediaDetailPath(id), "GET", _uO(), false))
            return@w buildShopMediaItemResponse(raw)
    })
}
fun createShopMedia(data: ShopMediaMutationData): UTSPromise<ShopMediaItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(shopMediaBasePath, "POST", buildShopMediaMutationBody(data), false))
            return@w buildShopMediaItemResponse(raw)
    })
}
fun updateShopMedia(id: Any, data: ShopMediaMutationData): UTSPromise<ShopMediaItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(shopMediaDetailPath(id), "PUT", buildShopMediaMutationBody(data), false))
            return@w buildShopMediaItemResponse(raw)
    })
}
val GenPagesShopIndexClass = CreateVueComponent(GenPagesShopIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesShopIndex.inheritAttrs, inject = GenPagesShopIndex.inject, props = GenPagesShopIndex.props, propsNeedCastKeys = GenPagesShopIndex.propsNeedCastKeys, emits = GenPagesShopIndex.emits, components = GenPagesShopIndex.components, styles = GenPagesShopIndex.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesShopIndex.setup(props as GenPagesShopIndex)
    }
    )
}
, fun(instance, renderer): GenPagesShopIndex {
    return GenPagesShopIndex(instance, renderer)
}
)
val GenPagesShopMediaClass = CreateVueComponent(GenPagesShopMedia::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesShopMedia.inheritAttrs, inject = GenPagesShopMedia.inject, props = GenPagesShopMedia.props, propsNeedCastKeys = GenPagesShopMedia.propsNeedCastKeys, emits = GenPagesShopMedia.emits, components = GenPagesShopMedia.components, styles = GenPagesShopMedia.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesShopMedia.setup(props as GenPagesShopMedia)
    }
    )
}
, fun(instance, renderer): GenPagesShopMedia {
    return GenPagesShopMedia(instance, renderer)
}
)
open class SelectOption__5 (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var text: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return SelectOption__5ReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class SelectOption__5ReactiveObject : SelectOption__5, IUTSReactive<SelectOption__5> {
    override var __v_raw: SelectOption__5
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: SelectOption__5, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(value = __v_raw.value, text = __v_raw.text) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): SelectOption__5ReactiveObject {
        return SelectOption__5ReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var value: String
        get() {
            return _tRG(__v_raw, "value", __v_raw.value, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("value")) {
                return
            }
            val oldValue = __v_raw.value
            __v_raw.value = value
            _tRS(__v_raw, "value", oldValue, value)
        }
    override var text: String
        get() {
            return _tRG(__v_raw, "text", __v_raw.text, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("text")) {
                return
            }
            val oldValue = __v_raw.text
            __v_raw.text = value
            _tRS(__v_raw, "text", oldValue, value)
        }
}
val GenPagesShopFromClass = CreateVueComponent(GenPagesShopFrom::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesShopFrom.inheritAttrs, inject = GenPagesShopFrom.inject, props = GenPagesShopFrom.props, propsNeedCastKeys = GenPagesShopFrom.propsNeedCastKeys, emits = GenPagesShopFrom.emits, components = GenPagesShopFrom.components, styles = GenPagesShopFrom.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesShopFrom.setup(props as GenPagesShopFrom)
    }
    )
}
, fun(instance, renderer): GenPagesShopFrom {
    return GenPagesShopFrom(instance, renderer)
}
)
open class SelectOption__6 (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var text: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return SelectOption__6ReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class SelectOption__6ReactiveObject : SelectOption__6, IUTSReactive<SelectOption__6> {
    override var __v_raw: SelectOption__6
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: SelectOption__6, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(value = __v_raw.value, text = __v_raw.text) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): SelectOption__6ReactiveObject {
        return SelectOption__6ReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var value: String
        get() {
            return _tRG(__v_raw, "value", __v_raw.value, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("value")) {
                return
            }
            val oldValue = __v_raw.value
            __v_raw.value = value
            _tRS(__v_raw, "value", oldValue, value)
        }
    override var text: String
        get() {
            return _tRG(__v_raw, "text", __v_raw.text, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("text")) {
                return
            }
            val oldValue = __v_raw.text
            __v_raw.text = value
            _tRS(__v_raw, "text", oldValue, value)
        }
}
val GenPagesProductsFromClass = CreateVueComponent(GenPagesProductsFrom::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesProductsFrom.inheritAttrs, inject = GenPagesProductsFrom.inject, props = GenPagesProductsFrom.props, propsNeedCastKeys = GenPagesProductsFrom.propsNeedCastKeys, emits = GenPagesProductsFrom.emits, components = GenPagesProductsFrom.components, styles = GenPagesProductsFrom.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesProductsFrom.setup(props as GenPagesProductsFrom)
    }
    )
}
, fun(instance, renderer): GenPagesProductsFrom {
    return GenPagesProductsFrom(instance, renderer)
}
)
open class FormulaChoice (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var label: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return FormulaChoiceReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class FormulaChoiceReactiveObject : FormulaChoice, IUTSReactive<FormulaChoice> {
    override var __v_raw: FormulaChoice
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: FormulaChoice, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(value = __v_raw.value, label = __v_raw.label) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): FormulaChoiceReactiveObject {
        return FormulaChoiceReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var value: String
        get() {
            return _tRG(__v_raw, "value", __v_raw.value, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("value")) {
                return
            }
            val oldValue = __v_raw.value
            __v_raw.value = value
            _tRS(__v_raw, "value", oldValue, value)
        }
    override var label: String
        get() {
            return _tRG(__v_raw, "label", __v_raw.label, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("label")) {
                return
            }
            val oldValue = __v_raw.label
            __v_raw.label = value
            _tRS(__v_raw, "label", oldValue, value)
        }
}
open class FormulaStep (
    @JsonNotNull
    open var kind: String,
    @JsonNotNull
    open var operator: String,
    @JsonNotNull
    open var operand: String,
    @JsonNotNull
    open var mode: String,
    @JsonNotNull
    open var parameter: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return FormulaStepReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class FormulaStepReactiveObject : FormulaStep, IUTSReactive<FormulaStep> {
    override var __v_raw: FormulaStep
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: FormulaStep, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(kind = __v_raw.kind, operator = __v_raw.operator, operand = __v_raw.operand, mode = __v_raw.mode, parameter = __v_raw.parameter) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): FormulaStepReactiveObject {
        return FormulaStepReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var kind: String
        get() {
            return _tRG(__v_raw, "kind", __v_raw.kind, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("kind")) {
                return
            }
            val oldValue = __v_raw.kind
            __v_raw.kind = value
            _tRS(__v_raw, "kind", oldValue, value)
        }
    override var operator: String
        get() {
            return _tRG(__v_raw, "operator", __v_raw.operator, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("operator")) {
                return
            }
            val oldValue = __v_raw.operator
            __v_raw.operator = value
            _tRS(__v_raw, "operator", oldValue, value)
        }
    override var operand: String
        get() {
            return _tRG(__v_raw, "operand", __v_raw.operand, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("operand")) {
                return
            }
            val oldValue = __v_raw.operand
            __v_raw.operand = value
            _tRS(__v_raw, "operand", oldValue, value)
        }
    override var mode: String
        get() {
            return _tRG(__v_raw, "mode", __v_raw.mode, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("mode")) {
                return
            }
            val oldValue = __v_raw.mode
            __v_raw.mode = value
            _tRS(__v_raw, "mode", oldValue, value)
        }
    override var parameter: String
        get() {
            return _tRG(__v_raw, "parameter", __v_raw.parameter, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("parameter")) {
                return
            }
            val oldValue = __v_raw.parameter
            __v_raw.parameter = value
            _tRS(__v_raw, "parameter", oldValue, value)
        }
}
open class ParsedFormula (
    @JsonNotNull
    open var success: Boolean = false,
    @JsonNotNull
    open var base_variable: String,
    @JsonNotNull
    open var steps: UTSArray<FormulaStep>,
) : UTSObject()
val GenPagesProductsPricingFormulaClass = CreateVueComponent(GenPagesProductsPricingFormula::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesProductsPricingFormula.inheritAttrs, inject = GenPagesProductsPricingFormula.inject, props = GenPagesProductsPricingFormula.props, propsNeedCastKeys = GenPagesProductsPricingFormula.propsNeedCastKeys, emits = GenPagesProductsPricingFormula.emits, components = GenPagesProductsPricingFormula.components, styles = GenPagesProductsPricingFormula.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesProductsPricingFormula.setup(props as GenPagesProductsPricingFormula)
    }
    )
}
, fun(instance, renderer): GenPagesProductsPricingFormula {
    return GenPagesProductsPricingFormula(instance, renderer)
}
)
val GenPagesProductsPricingFormulaIndexClass = CreateVueComponent(GenPagesProductsPricingFormulaIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesProductsPricingFormulaIndex.inheritAttrs, inject = GenPagesProductsPricingFormulaIndex.inject, props = GenPagesProductsPricingFormulaIndex.props, propsNeedCastKeys = GenPagesProductsPricingFormulaIndex.propsNeedCastKeys, emits = GenPagesProductsPricingFormulaIndex.emits, components = GenPagesProductsPricingFormulaIndex.components, styles = GenPagesProductsPricingFormulaIndex.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesProductsPricingFormulaIndex.setup(props as GenPagesProductsPricingFormulaIndex)
    }
    )
}
, fun(instance, renderer): GenPagesProductsPricingFormulaIndex {
    return GenPagesProductsPricingFormulaIndex(instance, renderer)
}
)
open class FormulaChoice__1 (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var label: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return FormulaChoice__1ReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class FormulaChoice__1ReactiveObject : FormulaChoice__1, IUTSReactive<FormulaChoice__1> {
    override var __v_raw: FormulaChoice__1
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: FormulaChoice__1, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(value = __v_raw.value, label = __v_raw.label) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): FormulaChoice__1ReactiveObject {
        return FormulaChoice__1ReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var value: String
        get() {
            return _tRG(__v_raw, "value", __v_raw.value, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("value")) {
                return
            }
            val oldValue = __v_raw.value
            __v_raw.value = value
            _tRS(__v_raw, "value", oldValue, value)
        }
    override var label: String
        get() {
            return _tRG(__v_raw, "label", __v_raw.label, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("label")) {
                return
            }
            val oldValue = __v_raw.label
            __v_raw.label = value
            _tRS(__v_raw, "label", oldValue, value)
        }
}
open class FormulaStep__1 (
    @JsonNotNull
    open var kind: String,
    @JsonNotNull
    open var operator: String,
    @JsonNotNull
    open var operand: String,
    @JsonNotNull
    open var mode: String,
    @JsonNotNull
    open var parameter: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return FormulaStep__1ReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class FormulaStep__1ReactiveObject : FormulaStep__1, IUTSReactive<FormulaStep__1> {
    override var __v_raw: FormulaStep__1
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: FormulaStep__1, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(kind = __v_raw.kind, operator = __v_raw.operator, operand = __v_raw.operand, mode = __v_raw.mode, parameter = __v_raw.parameter) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): FormulaStep__1ReactiveObject {
        return FormulaStep__1ReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var kind: String
        get() {
            return _tRG(__v_raw, "kind", __v_raw.kind, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("kind")) {
                return
            }
            val oldValue = __v_raw.kind
            __v_raw.kind = value
            _tRS(__v_raw, "kind", oldValue, value)
        }
    override var operator: String
        get() {
            return _tRG(__v_raw, "operator", __v_raw.operator, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("operator")) {
                return
            }
            val oldValue = __v_raw.operator
            __v_raw.operator = value
            _tRS(__v_raw, "operator", oldValue, value)
        }
    override var operand: String
        get() {
            return _tRG(__v_raw, "operand", __v_raw.operand, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("operand")) {
                return
            }
            val oldValue = __v_raw.operand
            __v_raw.operand = value
            _tRS(__v_raw, "operand", oldValue, value)
        }
    override var mode: String
        get() {
            return _tRG(__v_raw, "mode", __v_raw.mode, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("mode")) {
                return
            }
            val oldValue = __v_raw.mode
            __v_raw.mode = value
            _tRS(__v_raw, "mode", oldValue, value)
        }
    override var parameter: String
        get() {
            return _tRG(__v_raw, "parameter", __v_raw.parameter, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("parameter")) {
                return
            }
            val oldValue = __v_raw.parameter
            __v_raw.parameter = value
            _tRS(__v_raw, "parameter", oldValue, value)
        }
}
open class ParsedFormula__1 (
    @JsonNotNull
    open var success: Boolean = false,
    @JsonNotNull
    open var base_variable: String,
    @JsonNotNull
    open var steps: UTSArray<FormulaStep__1>,
) : UTSObject()
val GenPagesProductsPricingFormulaFromClass = CreateVueComponent(GenPagesProductsPricingFormulaFrom::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesProductsPricingFormulaFrom.inheritAttrs, inject = GenPagesProductsPricingFormulaFrom.inject, props = GenPagesProductsPricingFormulaFrom.props, propsNeedCastKeys = GenPagesProductsPricingFormulaFrom.propsNeedCastKeys, emits = GenPagesProductsPricingFormulaFrom.emits, components = GenPagesProductsPricingFormulaFrom.components, styles = GenPagesProductsPricingFormulaFrom.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesProductsPricingFormulaFrom.setup(props as GenPagesProductsPricingFormulaFrom)
    }
    )
}
, fun(instance, renderer): GenPagesProductsPricingFormulaFrom {
    return GenPagesProductsPricingFormulaFrom(instance, renderer)
}
)
val GenPagesProductsConfigModelIndexClass = CreateVueComponent(GenPagesProductsConfigModelIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesProductsConfigModelIndex.inheritAttrs, inject = GenPagesProductsConfigModelIndex.inject, props = GenPagesProductsConfigModelIndex.props, propsNeedCastKeys = GenPagesProductsConfigModelIndex.propsNeedCastKeys, emits = GenPagesProductsConfigModelIndex.emits, components = GenPagesProductsConfigModelIndex.components, styles = GenPagesProductsConfigModelIndex.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesProductsConfigModelIndex.setup(props as GenPagesProductsConfigModelIndex)
    }
    )
}
, fun(instance, renderer): GenPagesProductsConfigModelIndex {
    return GenPagesProductsConfigModelIndex(instance, renderer)
}
)
val GenPagesProductsConfigModelFromClass = CreateVueComponent(GenPagesProductsConfigModelFrom::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesProductsConfigModelFrom.inheritAttrs, inject = GenPagesProductsConfigModelFrom.inject, props = GenPagesProductsConfigModelFrom.props, propsNeedCastKeys = GenPagesProductsConfigModelFrom.propsNeedCastKeys, emits = GenPagesProductsConfigModelFrom.emits, components = GenPagesProductsConfigModelFrom.components, styles = GenPagesProductsConfigModelFrom.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesProductsConfigModelFrom.setup(props as GenPagesProductsConfigModelFrom)
    }
    )
}
, fun(instance, renderer): GenPagesProductsConfigModelFrom {
    return GenPagesProductsConfigModelFrom(instance, renderer)
}
)
open class KsefInvoiceListQuery (
    open var search: String? = null,
    @JsonNotNull
    open var page: Number,
    @JsonNotNull
    open var page_size: Number,
    open var sync_status: String? = null,
    open var is_paid: String? = null,
) : UTSObject()
open class KsefInvoiceItem (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var ksef_number: String,
    @JsonNotNull
    open var invoice_number: String,
    @JsonNotNull
    open var seller_name: String,
    @JsonNotNull
    open var seller_nip: String,
    @JsonNotNull
    open var buyer_name: String,
    @JsonNotNull
    open var buyer_nip: String,
    @JsonNotNull
    open var issue_date: String,
    @JsonNotNull
    open var currency: String,
    @JsonNotNull
    open var gross_amount: String,
    @JsonNotNull
    open var amount_due: String,
    @JsonNotNull
    open var is_paid: Boolean = false,
    @JsonNotNull
    open var sync_status: String,
    @JsonNotNull
    open var raw_xml_downloaded_at: String,
    @JsonNotNull
    open var created_at: String,
    @JsonNotNull
    open var updated_at: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return KsefInvoiceItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class KsefInvoiceItemReactiveObject : KsefInvoiceItem, IUTSReactive<KsefInvoiceItem> {
    override var __v_raw: KsefInvoiceItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: KsefInvoiceItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, ksef_number = __v_raw.ksef_number, invoice_number = __v_raw.invoice_number, seller_name = __v_raw.seller_name, seller_nip = __v_raw.seller_nip, buyer_name = __v_raw.buyer_name, buyer_nip = __v_raw.buyer_nip, issue_date = __v_raw.issue_date, currency = __v_raw.currency, gross_amount = __v_raw.gross_amount, amount_due = __v_raw.amount_due, is_paid = __v_raw.is_paid, sync_status = __v_raw.sync_status, raw_xml_downloaded_at = __v_raw.raw_xml_downloaded_at, created_at = __v_raw.created_at, updated_at = __v_raw.updated_at) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): KsefInvoiceItemReactiveObject {
        return KsefInvoiceItemReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return _tRG(__v_raw, "id", __v_raw.id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            _tRS(__v_raw, "id", oldValue, value)
        }
    override var ksef_number: String
        get() {
            return _tRG(__v_raw, "ksef_number", __v_raw.ksef_number, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("ksef_number")) {
                return
            }
            val oldValue = __v_raw.ksef_number
            __v_raw.ksef_number = value
            _tRS(__v_raw, "ksef_number", oldValue, value)
        }
    override var invoice_number: String
        get() {
            return _tRG(__v_raw, "invoice_number", __v_raw.invoice_number, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("invoice_number")) {
                return
            }
            val oldValue = __v_raw.invoice_number
            __v_raw.invoice_number = value
            _tRS(__v_raw, "invoice_number", oldValue, value)
        }
    override var seller_name: String
        get() {
            return _tRG(__v_raw, "seller_name", __v_raw.seller_name, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("seller_name")) {
                return
            }
            val oldValue = __v_raw.seller_name
            __v_raw.seller_name = value
            _tRS(__v_raw, "seller_name", oldValue, value)
        }
    override var seller_nip: String
        get() {
            return _tRG(__v_raw, "seller_nip", __v_raw.seller_nip, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("seller_nip")) {
                return
            }
            val oldValue = __v_raw.seller_nip
            __v_raw.seller_nip = value
            _tRS(__v_raw, "seller_nip", oldValue, value)
        }
    override var buyer_name: String
        get() {
            return _tRG(__v_raw, "buyer_name", __v_raw.buyer_name, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("buyer_name")) {
                return
            }
            val oldValue = __v_raw.buyer_name
            __v_raw.buyer_name = value
            _tRS(__v_raw, "buyer_name", oldValue, value)
        }
    override var buyer_nip: String
        get() {
            return _tRG(__v_raw, "buyer_nip", __v_raw.buyer_nip, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("buyer_nip")) {
                return
            }
            val oldValue = __v_raw.buyer_nip
            __v_raw.buyer_nip = value
            _tRS(__v_raw, "buyer_nip", oldValue, value)
        }
    override var issue_date: String
        get() {
            return _tRG(__v_raw, "issue_date", __v_raw.issue_date, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("issue_date")) {
                return
            }
            val oldValue = __v_raw.issue_date
            __v_raw.issue_date = value
            _tRS(__v_raw, "issue_date", oldValue, value)
        }
    override var currency: String
        get() {
            return _tRG(__v_raw, "currency", __v_raw.currency, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("currency")) {
                return
            }
            val oldValue = __v_raw.currency
            __v_raw.currency = value
            _tRS(__v_raw, "currency", oldValue, value)
        }
    override var gross_amount: String
        get() {
            return _tRG(__v_raw, "gross_amount", __v_raw.gross_amount, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("gross_amount")) {
                return
            }
            val oldValue = __v_raw.gross_amount
            __v_raw.gross_amount = value
            _tRS(__v_raw, "gross_amount", oldValue, value)
        }
    override var amount_due: String
        get() {
            return _tRG(__v_raw, "amount_due", __v_raw.amount_due, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("amount_due")) {
                return
            }
            val oldValue = __v_raw.amount_due
            __v_raw.amount_due = value
            _tRS(__v_raw, "amount_due", oldValue, value)
        }
    override var is_paid: Boolean
        get() {
            return _tRG(__v_raw, "is_paid", __v_raw.is_paid, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("is_paid")) {
                return
            }
            val oldValue = __v_raw.is_paid
            __v_raw.is_paid = value
            _tRS(__v_raw, "is_paid", oldValue, value)
        }
    override var sync_status: String
        get() {
            return _tRG(__v_raw, "sync_status", __v_raw.sync_status, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("sync_status")) {
                return
            }
            val oldValue = __v_raw.sync_status
            __v_raw.sync_status = value
            _tRS(__v_raw, "sync_status", oldValue, value)
        }
    override var raw_xml_downloaded_at: String
        get() {
            return _tRG(__v_raw, "raw_xml_downloaded_at", __v_raw.raw_xml_downloaded_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("raw_xml_downloaded_at")) {
                return
            }
            val oldValue = __v_raw.raw_xml_downloaded_at
            __v_raw.raw_xml_downloaded_at = value
            _tRS(__v_raw, "raw_xml_downloaded_at", oldValue, value)
        }
    override var created_at: String
        get() {
            return _tRG(__v_raw, "created_at", __v_raw.created_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("created_at")) {
                return
            }
            val oldValue = __v_raw.created_at
            __v_raw.created_at = value
            _tRS(__v_raw, "created_at", oldValue, value)
        }
    override var updated_at: String
        get() {
            return _tRG(__v_raw, "updated_at", __v_raw.updated_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("updated_at")) {
                return
            }
            val oldValue = __v_raw.updated_at
            __v_raw.updated_at = value
            _tRS(__v_raw, "updated_at", oldValue, value)
        }
}
open class KsefInvoiceListResponse (
    @JsonNotNull
    open var results: UTSArray<KsefInvoiceItem>,
    @JsonNotNull
    open var count: Number,
    @JsonNotNull
    open var total_count: Number,
    @JsonNotNull
    open var total_pages: Number,
    @JsonNotNull
    open var current_page: Number,
    @JsonNotNull
    open var page_size: Number,
    @JsonNotNull
    open var summary: UTSJSONObject,
) : UTSObject()
open class KsefAutoSyncStatus (
    @JsonNotNull
    open var enabled: Boolean = false,
    @JsonNotNull
    open var metadata_interval_seconds: Number,
    @JsonNotNull
    open var xml_interval_seconds: Number,
    @JsonNotNull
    open var xml_batch_size: Number,
    @JsonNotNull
    open var xml_delay_seconds: Number,
    @JsonNotNull
    open var pending_xml_count: Number,
    @JsonNotNull
    open var last_success_at: String,
    @JsonNotNull
    open var last_success_requested_to: String,
    @JsonNotNull
    open var last_failed_at: String,
    @JsonNotNull
    open var last_failed_message: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return KsefAutoSyncStatusReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class KsefAutoSyncStatusReactiveObject : KsefAutoSyncStatus, IUTSReactive<KsefAutoSyncStatus> {
    override var __v_raw: KsefAutoSyncStatus
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: KsefAutoSyncStatus, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(enabled = __v_raw.enabled, metadata_interval_seconds = __v_raw.metadata_interval_seconds, xml_interval_seconds = __v_raw.xml_interval_seconds, xml_batch_size = __v_raw.xml_batch_size, xml_delay_seconds = __v_raw.xml_delay_seconds, pending_xml_count = __v_raw.pending_xml_count, last_success_at = __v_raw.last_success_at, last_success_requested_to = __v_raw.last_success_requested_to, last_failed_at = __v_raw.last_failed_at, last_failed_message = __v_raw.last_failed_message) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): KsefAutoSyncStatusReactiveObject {
        return KsefAutoSyncStatusReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var enabled: Boolean
        get() {
            return _tRG(__v_raw, "enabled", __v_raw.enabled, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("enabled")) {
                return
            }
            val oldValue = __v_raw.enabled
            __v_raw.enabled = value
            _tRS(__v_raw, "enabled", oldValue, value)
        }
    override var metadata_interval_seconds: Number
        get() {
            return _tRG(__v_raw, "metadata_interval_seconds", __v_raw.metadata_interval_seconds, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("metadata_interval_seconds")) {
                return
            }
            val oldValue = __v_raw.metadata_interval_seconds
            __v_raw.metadata_interval_seconds = value
            _tRS(__v_raw, "metadata_interval_seconds", oldValue, value)
        }
    override var xml_interval_seconds: Number
        get() {
            return _tRG(__v_raw, "xml_interval_seconds", __v_raw.xml_interval_seconds, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("xml_interval_seconds")) {
                return
            }
            val oldValue = __v_raw.xml_interval_seconds
            __v_raw.xml_interval_seconds = value
            _tRS(__v_raw, "xml_interval_seconds", oldValue, value)
        }
    override var xml_batch_size: Number
        get() {
            return _tRG(__v_raw, "xml_batch_size", __v_raw.xml_batch_size, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("xml_batch_size")) {
                return
            }
            val oldValue = __v_raw.xml_batch_size
            __v_raw.xml_batch_size = value
            _tRS(__v_raw, "xml_batch_size", oldValue, value)
        }
    override var xml_delay_seconds: Number
        get() {
            return _tRG(__v_raw, "xml_delay_seconds", __v_raw.xml_delay_seconds, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("xml_delay_seconds")) {
                return
            }
            val oldValue = __v_raw.xml_delay_seconds
            __v_raw.xml_delay_seconds = value
            _tRS(__v_raw, "xml_delay_seconds", oldValue, value)
        }
    override var pending_xml_count: Number
        get() {
            return _tRG(__v_raw, "pending_xml_count", __v_raw.pending_xml_count, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("pending_xml_count")) {
                return
            }
            val oldValue = __v_raw.pending_xml_count
            __v_raw.pending_xml_count = value
            _tRS(__v_raw, "pending_xml_count", oldValue, value)
        }
    override var last_success_at: String
        get() {
            return _tRG(__v_raw, "last_success_at", __v_raw.last_success_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("last_success_at")) {
                return
            }
            val oldValue = __v_raw.last_success_at
            __v_raw.last_success_at = value
            _tRS(__v_raw, "last_success_at", oldValue, value)
        }
    override var last_success_requested_to: String
        get() {
            return _tRG(__v_raw, "last_success_requested_to", __v_raw.last_success_requested_to, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("last_success_requested_to")) {
                return
            }
            val oldValue = __v_raw.last_success_requested_to
            __v_raw.last_success_requested_to = value
            _tRS(__v_raw, "last_success_requested_to", oldValue, value)
        }
    override var last_failed_at: String
        get() {
            return _tRG(__v_raw, "last_failed_at", __v_raw.last_failed_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("last_failed_at")) {
                return
            }
            val oldValue = __v_raw.last_failed_at
            __v_raw.last_failed_at = value
            _tRS(__v_raw, "last_failed_at", oldValue, value)
        }
    override var last_failed_message: String
        get() {
            return _tRG(__v_raw, "last_failed_message", __v_raw.last_failed_message, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("last_failed_message")) {
                return
            }
            val oldValue = __v_raw.last_failed_message
            __v_raw.last_failed_message = value
            _tRS(__v_raw, "last_failed_message", oldValue, value)
        }
}
fun stringValue__8(value: Any?): String {
    if (value == null) {
        return ""
    }
    return "" + value
}
fun intValue__7(value: Any?): Number {
    if (value == null) {
        return 0
    }
    val parsed = parseInt("" + value)
    if (isNaN(parsed)) {
        return 0
    }
    return parsed
}
fun floatValue(value: Any?): Number {
    if (value == null) {
        return 0
    }
    val parsed = parseFloat("" + value)
    if (isNaN(parsed)) {
        return 0
    }
    return parsed
}
fun boolValue__1(value: Any?): Boolean {
    if (value == null) {
        return false
    }
    val text = ("" + value).toLowerCase()
    return text == "true" || text == "1"
}
fun buildListQuery__3(data: KsefInvoiceListQuery): UTSJSONObject {
    val query: UTSJSONObject = _uO("page" to data.page, "page_size" to data.page_size)
    if (data.search != null && data.search != "") {
        query["search"] = data.search
    }
    if (data.sync_status != null && data.sync_status != "") {
        query["sync_status"] = data.sync_status
    }
    if (data.is_paid != null && data.is_paid != "") {
        query["is_paid"] = data.is_paid
    }
    return query
}
fun buildInvoiceItem(rawObject: UTSJSONObject): KsefInvoiceItem {
    return KsefInvoiceItem(id = intValue__7(rawObject["id"]), ksef_number = stringValue__8(rawObject["ksef_number"]), invoice_number = stringValue__8(rawObject["invoice_number"]), seller_name = stringValue__8(rawObject["seller_name"]), seller_nip = stringValue__8(rawObject["seller_nip"]), buyer_name = stringValue__8(rawObject["buyer_name"]), buyer_nip = stringValue__8(rawObject["buyer_nip"]), issue_date = stringValue__8(rawObject["issue_date"]), currency = stringValue__8(rawObject["currency"]), gross_amount = stringValue__8(rawObject["gross_amount"]), amount_due = stringValue__8(rawObject["amount_due"]), is_paid = boolValue__1(rawObject["is_paid"]), sync_status = stringValue__8(rawObject["sync_status"]), raw_xml_downloaded_at = stringValue__8(rawObject["raw_xml_downloaded_at"]), created_at = stringValue__8(rawObject["created_at"]), updated_at = stringValue__8(rawObject["updated_at"]))
}
fun buildInvoiceItems(value: Any?): UTSArray<KsefInvoiceItem> {
    if (value == null) {
        return _uA()
    }
    val text = JSON.stringify(value)
    val rawArray = if (text == null || text == "") {
        null
    } else {
        JSON.parseArray<UTSJSONObject>(text)
    }
    if (rawArray == null) {
        return _uA()
    }
    val result: UTSArray<KsefInvoiceItem> = _uA()
    run {
        var index: Number = 0
        while(index < rawArray!!.length){
            result.push(buildInvoiceItem(rawArray!![index]))
            index += 1
        }
    }
    return result
}
fun buildListResponse(raw: Any, query: KsefInvoiceListQuery): KsefInvoiceListResponse {
    val rawText = JSON.stringify(raw)
    val rawObject = if (rawText == null || rawText == "") {
        null
    } else {
        JSON.parseObject<UTSJSONObject>(rawText)
    }
    if (rawObject == null) {
        throw UTSError("KSeF 发票列表解析失败")
    }
    var paginationObject: UTSJSONObject? = null
    val rawPagination = rawObject!!["pagination"]
    if (rawPagination != null) {
        val paginationText = JSON.stringify(rawPagination)
        paginationObject = if (paginationText == null || paginationText == "") {
            null
        } else {
            JSON.parseObject<UTSJSONObject>(paginationText)
        }
    }
    val results = buildInvoiceItems(rawObject!!["results"])
    var totalCount = intValue__7(rawObject!!["count"])
    if (totalCount <= 0) {
        totalCount = intValue__7(rawObject!!["total_count"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__7(paginationObject!!["total"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__7(paginationObject!!["count"])
    }
    if (totalCount <= 0) {
        totalCount = results.length
    }
    var currentPage = intValue__7(rawObject!!["current_page"])
    if (currentPage <= 0) {
        currentPage = intValue__7(rawObject!!["page"])
    }
    if (currentPage <= 0 && paginationObject != null) {
        currentPage = intValue__7(paginationObject!!["page"])
    }
    if (currentPage <= 0) {
        currentPage = query.page
    }
    var pageSize = intValue__7(rawObject!!["page_size"])
    if (pageSize <= 0 && paginationObject != null) {
        pageSize = intValue__7(paginationObject!!["page_size"])
    }
    if (pageSize <= 0) {
        pageSize = query.page_size
    }
    var totalPages = intValue__7(rawObject!!["total_pages"])
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__7(paginationObject!!["total_pages"])
    }
    if (totalPages <= 0 && pageSize > 0) {
        totalPages = Math.ceil(totalCount / pageSize)
    }
    if (totalPages <= 0) {
        totalPages = 1
    }
    var summary: UTSJSONObject = _uO()
    val rawSummary = rawObject!!["summary"]
    if (rawSummary != null) {
        val summaryText = JSON.stringify(rawSummary)
        val parsedSummary = if (summaryText == null || summaryText == "") {
            null
        } else {
            JSON.parseObject<UTSJSONObject>(summaryText)
        }
        if (parsedSummary != null) {
            summary = parsedSummary!!
        }
    }
    return KsefInvoiceListResponse(results = results, count = totalCount, total_count = totalCount, total_pages = totalPages, current_page = currentPage, page_size = pageSize, summary = summary)
}
fun buildAutoSyncStatus(raw: Any): KsefAutoSyncStatus {
    val rawText = JSON.stringify(raw)
    val rawObject = if (rawText == null || rawText == "") {
        null
    } else {
        JSON.parseObject<UTSJSONObject>(rawText)
    }
    if (rawObject == null) {
        throw UTSError("KSeF 自动同步状态解析失败")
    }
    return KsefAutoSyncStatus(enabled = boolValue__1(rawObject!!["enabled"]), metadata_interval_seconds = floatValue(rawObject!!["metadata_interval_seconds"]), xml_interval_seconds = floatValue(rawObject!!["xml_interval_seconds"]), xml_batch_size = intValue__7(rawObject!!["xml_batch_size"]), xml_delay_seconds = floatValue(rawObject!!["xml_delay_seconds"]), pending_xml_count = intValue__7(rawObject!!["pending_xml_count"]), last_success_at = stringValue__8(rawObject!!["last_success_at"]), last_success_requested_to = stringValue__8(rawObject!!["last_success_requested_to"]), last_failed_at = stringValue__8(rawObject!!["last_failed_at"]), last_failed_message = stringValue__8(rawObject!!["last_failed_message"]))
}
fun getKsefInvoiceList(data: KsefInvoiceListQuery): UTSPromise<KsefInvoiceListResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/procurement/ksef-invoices/", "GET", buildListQuery__3(data), true))
            return@w buildListResponse(raw, data)
    })
}
fun getKsefAutoSyncStatus(): UTSPromise<KsefAutoSyncStatus> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/procurement/ksef-invoices/auto-sync-status/", "GET", _uO(), true))
            return@w buildAutoSyncStatus(raw)
    })
}
fun enqueueKsefAutoSync(): UTSPromise<Any> {
    return request("/api/procurement/ksef-invoices/enqueue-auto-sync/", "POST", _uO(), true)
}
fun downloadKsefInvoiceXml(id: Any): UTSPromise<Any> {
    return request("/api/procurement/ksef-invoices/" + stringValue__8(id) + "/download_xml/", "POST", _uO(), true)
}
val GenPagesKsefIndexClass = CreateVueComponent(GenPagesKsefIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesKsefIndex.inheritAttrs, inject = GenPagesKsefIndex.inject, props = GenPagesKsefIndex.props, propsNeedCastKeys = GenPagesKsefIndex.propsNeedCastKeys, emits = GenPagesKsefIndex.emits, components = GenPagesKsefIndex.components, styles = GenPagesKsefIndex.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesKsefIndex.setup(props as GenPagesKsefIndex)
    }
    )
}
, fun(instance, renderer): GenPagesKsefIndex {
    return GenPagesKsefIndex(instance, renderer)
}
)
open class ExpenseListQuery (
    open var search: String? = null,
    @JsonNotNull
    open var page: Number,
    @JsonNotNull
    open var page_size: Number,
    open var expenditure_type: String? = null,
    open var expenditure_type_id: String? = null,
    open var supplier: String? = null,
    open var supplier_id: String? = null,
    open var date_from: String? = null,
    open var date_to: String? = null,
    open var amount_min: String? = null,
    open var amount_max: String? = null,
) : UTSObject()
open class ExpenseMediaFile (
    @JsonNotNull
    open var id: String,
    @JsonNotNull
    open var company: Number,
    @JsonNotNull
    open var original_filename: String,
    @JsonNotNull
    open var file_type: String,
    @JsonNotNull
    open var file_type_display: String,
    @JsonNotNull
    open var mime_type: String,
    @JsonNotNull
    open var file_size: Number,
    @JsonNotNull
    open var file_size_display: String,
    @JsonNotNull
    open var file_url: String,
    @JsonNotNull
    open var thumbnail_url: String,
    @JsonNotNull
    open var signed_url: String,
    @JsonNotNull
    open var signed_thumbnail_url: String,
    @JsonNotNull
    open var object_id: String,
    @JsonNotNull
    open var is_deleted: Boolean = false,
    @JsonNotNull
    open var created_at: String,
    @JsonNotNull
    open var updated_at: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ExpenseMediaFileReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ExpenseMediaFileReactiveObject : ExpenseMediaFile, IUTSReactive<ExpenseMediaFile> {
    override var __v_raw: ExpenseMediaFile
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ExpenseMediaFile, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, company = __v_raw.company, original_filename = __v_raw.original_filename, file_type = __v_raw.file_type, file_type_display = __v_raw.file_type_display, mime_type = __v_raw.mime_type, file_size = __v_raw.file_size, file_size_display = __v_raw.file_size_display, file_url = __v_raw.file_url, thumbnail_url = __v_raw.thumbnail_url, signed_url = __v_raw.signed_url, signed_thumbnail_url = __v_raw.signed_thumbnail_url, object_id = __v_raw.object_id, is_deleted = __v_raw.is_deleted, created_at = __v_raw.created_at, updated_at = __v_raw.updated_at) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ExpenseMediaFileReactiveObject {
        return ExpenseMediaFileReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: String
        get() {
            return _tRG(__v_raw, "id", __v_raw.id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            _tRS(__v_raw, "id", oldValue, value)
        }
    override var company: Number
        get() {
            return _tRG(__v_raw, "company", __v_raw.company, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("company")) {
                return
            }
            val oldValue = __v_raw.company
            __v_raw.company = value
            _tRS(__v_raw, "company", oldValue, value)
        }
    override var original_filename: String
        get() {
            return _tRG(__v_raw, "original_filename", __v_raw.original_filename, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("original_filename")) {
                return
            }
            val oldValue = __v_raw.original_filename
            __v_raw.original_filename = value
            _tRS(__v_raw, "original_filename", oldValue, value)
        }
    override var file_type: String
        get() {
            return _tRG(__v_raw, "file_type", __v_raw.file_type, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("file_type")) {
                return
            }
            val oldValue = __v_raw.file_type
            __v_raw.file_type = value
            _tRS(__v_raw, "file_type", oldValue, value)
        }
    override var file_type_display: String
        get() {
            return _tRG(__v_raw, "file_type_display", __v_raw.file_type_display, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("file_type_display")) {
                return
            }
            val oldValue = __v_raw.file_type_display
            __v_raw.file_type_display = value
            _tRS(__v_raw, "file_type_display", oldValue, value)
        }
    override var mime_type: String
        get() {
            return _tRG(__v_raw, "mime_type", __v_raw.mime_type, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("mime_type")) {
                return
            }
            val oldValue = __v_raw.mime_type
            __v_raw.mime_type = value
            _tRS(__v_raw, "mime_type", oldValue, value)
        }
    override var file_size: Number
        get() {
            return _tRG(__v_raw, "file_size", __v_raw.file_size, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("file_size")) {
                return
            }
            val oldValue = __v_raw.file_size
            __v_raw.file_size = value
            _tRS(__v_raw, "file_size", oldValue, value)
        }
    override var file_size_display: String
        get() {
            return _tRG(__v_raw, "file_size_display", __v_raw.file_size_display, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("file_size_display")) {
                return
            }
            val oldValue = __v_raw.file_size_display
            __v_raw.file_size_display = value
            _tRS(__v_raw, "file_size_display", oldValue, value)
        }
    override var file_url: String
        get() {
            return _tRG(__v_raw, "file_url", __v_raw.file_url, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("file_url")) {
                return
            }
            val oldValue = __v_raw.file_url
            __v_raw.file_url = value
            _tRS(__v_raw, "file_url", oldValue, value)
        }
    override var thumbnail_url: String
        get() {
            return _tRG(__v_raw, "thumbnail_url", __v_raw.thumbnail_url, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("thumbnail_url")) {
                return
            }
            val oldValue = __v_raw.thumbnail_url
            __v_raw.thumbnail_url = value
            _tRS(__v_raw, "thumbnail_url", oldValue, value)
        }
    override var signed_url: String
        get() {
            return _tRG(__v_raw, "signed_url", __v_raw.signed_url, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("signed_url")) {
                return
            }
            val oldValue = __v_raw.signed_url
            __v_raw.signed_url = value
            _tRS(__v_raw, "signed_url", oldValue, value)
        }
    override var signed_thumbnail_url: String
        get() {
            return _tRG(__v_raw, "signed_thumbnail_url", __v_raw.signed_thumbnail_url, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("signed_thumbnail_url")) {
                return
            }
            val oldValue = __v_raw.signed_thumbnail_url
            __v_raw.signed_thumbnail_url = value
            _tRS(__v_raw, "signed_thumbnail_url", oldValue, value)
        }
    override var object_id: String
        get() {
            return _tRG(__v_raw, "object_id", __v_raw.object_id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("object_id")) {
                return
            }
            val oldValue = __v_raw.object_id
            __v_raw.object_id = value
            _tRS(__v_raw, "object_id", oldValue, value)
        }
    override var is_deleted: Boolean
        get() {
            return _tRG(__v_raw, "is_deleted", __v_raw.is_deleted, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("is_deleted")) {
                return
            }
            val oldValue = __v_raw.is_deleted
            __v_raw.is_deleted = value
            _tRS(__v_raw, "is_deleted", oldValue, value)
        }
    override var created_at: String
        get() {
            return _tRG(__v_raw, "created_at", __v_raw.created_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("created_at")) {
                return
            }
            val oldValue = __v_raw.created_at
            __v_raw.created_at = value
            _tRS(__v_raw, "created_at", oldValue, value)
        }
    override var updated_at: String
        get() {
            return _tRG(__v_raw, "updated_at", __v_raw.updated_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("updated_at")) {
                return
            }
            val oldValue = __v_raw.updated_at
            __v_raw.updated_at = value
            _tRS(__v_raw, "updated_at", oldValue, value)
        }
}
open class ExpenseItem (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var expenditure_type: Number,
    @JsonNotNull
    open var expenditure_type_name: String,
    @JsonNotNull
    open var amount: String,
    @JsonNotNull
    open var expenditure_date: String,
    open var invoice_number: String? = null,
    @JsonNotNull
    open var supplier: Number,
    @JsonNotNull
    open var supplier_name: String,
    open var description: String? = null,
    open var note: String? = null,
    @JsonNotNull
    open var media_files: UTSArray<ExpenseMediaFile>,
    @JsonNotNull
    open var files_count: Number,
    @JsonNotNull
    open var is_deleted: Boolean = false,
    @JsonNotNull
    open var created_at: String,
    @JsonNotNull
    open var updated_at: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ExpenseItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ExpenseItemReactiveObject : ExpenseItem, IUTSReactive<ExpenseItem> {
    override var __v_raw: ExpenseItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ExpenseItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, expenditure_type = __v_raw.expenditure_type, expenditure_type_name = __v_raw.expenditure_type_name, amount = __v_raw.amount, expenditure_date = __v_raw.expenditure_date, invoice_number = __v_raw.invoice_number, supplier = __v_raw.supplier, supplier_name = __v_raw.supplier_name, description = __v_raw.description, note = __v_raw.note, media_files = __v_raw.media_files, files_count = __v_raw.files_count, is_deleted = __v_raw.is_deleted, created_at = __v_raw.created_at, updated_at = __v_raw.updated_at) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ExpenseItemReactiveObject {
        return ExpenseItemReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return _tRG(__v_raw, "id", __v_raw.id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            _tRS(__v_raw, "id", oldValue, value)
        }
    override var expenditure_type: Number
        get() {
            return _tRG(__v_raw, "expenditure_type", __v_raw.expenditure_type, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("expenditure_type")) {
                return
            }
            val oldValue = __v_raw.expenditure_type
            __v_raw.expenditure_type = value
            _tRS(__v_raw, "expenditure_type", oldValue, value)
        }
    override var expenditure_type_name: String
        get() {
            return _tRG(__v_raw, "expenditure_type_name", __v_raw.expenditure_type_name, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("expenditure_type_name")) {
                return
            }
            val oldValue = __v_raw.expenditure_type_name
            __v_raw.expenditure_type_name = value
            _tRS(__v_raw, "expenditure_type_name", oldValue, value)
        }
    override var amount: String
        get() {
            return _tRG(__v_raw, "amount", __v_raw.amount, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("amount")) {
                return
            }
            val oldValue = __v_raw.amount
            __v_raw.amount = value
            _tRS(__v_raw, "amount", oldValue, value)
        }
    override var expenditure_date: String
        get() {
            return _tRG(__v_raw, "expenditure_date", __v_raw.expenditure_date, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("expenditure_date")) {
                return
            }
            val oldValue = __v_raw.expenditure_date
            __v_raw.expenditure_date = value
            _tRS(__v_raw, "expenditure_date", oldValue, value)
        }
    override var invoice_number: String?
        get() {
            return _tRG(__v_raw, "invoice_number", __v_raw.invoice_number, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("invoice_number")) {
                return
            }
            val oldValue = __v_raw.invoice_number
            __v_raw.invoice_number = value
            _tRS(__v_raw, "invoice_number", oldValue, value)
        }
    override var supplier: Number
        get() {
            return _tRG(__v_raw, "supplier", __v_raw.supplier, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("supplier")) {
                return
            }
            val oldValue = __v_raw.supplier
            __v_raw.supplier = value
            _tRS(__v_raw, "supplier", oldValue, value)
        }
    override var supplier_name: String
        get() {
            return _tRG(__v_raw, "supplier_name", __v_raw.supplier_name, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("supplier_name")) {
                return
            }
            val oldValue = __v_raw.supplier_name
            __v_raw.supplier_name = value
            _tRS(__v_raw, "supplier_name", oldValue, value)
        }
    override var description: String?
        get() {
            return _tRG(__v_raw, "description", __v_raw.description, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("description")) {
                return
            }
            val oldValue = __v_raw.description
            __v_raw.description = value
            _tRS(__v_raw, "description", oldValue, value)
        }
    override var note: String?
        get() {
            return _tRG(__v_raw, "note", __v_raw.note, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("note")) {
                return
            }
            val oldValue = __v_raw.note
            __v_raw.note = value
            _tRS(__v_raw, "note", oldValue, value)
        }
    override var media_files: UTSArray<ExpenseMediaFile>
        get() {
            return _tRG(__v_raw, "media_files", __v_raw.media_files, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("media_files")) {
                return
            }
            val oldValue = __v_raw.media_files
            __v_raw.media_files = value
            _tRS(__v_raw, "media_files", oldValue, value)
        }
    override var files_count: Number
        get() {
            return _tRG(__v_raw, "files_count", __v_raw.files_count, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("files_count")) {
                return
            }
            val oldValue = __v_raw.files_count
            __v_raw.files_count = value
            _tRS(__v_raw, "files_count", oldValue, value)
        }
    override var is_deleted: Boolean
        get() {
            return _tRG(__v_raw, "is_deleted", __v_raw.is_deleted, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("is_deleted")) {
                return
            }
            val oldValue = __v_raw.is_deleted
            __v_raw.is_deleted = value
            _tRS(__v_raw, "is_deleted", oldValue, value)
        }
    override var created_at: String
        get() {
            return _tRG(__v_raw, "created_at", __v_raw.created_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("created_at")) {
                return
            }
            val oldValue = __v_raw.created_at
            __v_raw.created_at = value
            _tRS(__v_raw, "created_at", oldValue, value)
        }
    override var updated_at: String
        get() {
            return _tRG(__v_raw, "updated_at", __v_raw.updated_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("updated_at")) {
                return
            }
            val oldValue = __v_raw.updated_at
            __v_raw.updated_at = value
            _tRS(__v_raw, "updated_at", oldValue, value)
        }
}
open class ExpenseListResponse (
    @JsonNotNull
    open var results: UTSArray<ExpenseItem>,
    @JsonNotNull
    open var count: Number,
    @JsonNotNull
    open var total_count: Number,
    @JsonNotNull
    open var total_pages: Number,
    @JsonNotNull
    open var current_page: Number,
    @JsonNotNull
    open var page_size: Number,
    open var summary: UTSJSONObject? = null,
) : UTSObject()
open class ExpenseOptionItem (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var label: String,
    @JsonNotNull
    open var extra: UTSJSONObject,
) : UTSObject()
open class ExpenseOptionGroup (
    @JsonNotNull
    open var key: String,
    @JsonNotNull
    open var label: String,
    @JsonNotNull
    open var control: String,
    @JsonNotNull
    open var count: Number,
    @JsonNotNull
    open var items: UTSArray<ExpenseOptionItem>,
) : UTSObject()
open class ExpenseOptionsResponse (
    @JsonNotNull
    open var resource: String,
    @JsonNotNull
    open var total_groups: Number,
    @JsonNotNull
    open var groups: UTSArray<ExpenseOptionGroup>,
) : UTSObject()
open class ExpenseMutationData (
    open var expenditure_type_id: String? = null,
    open var supplier_id: String? = null,
    @JsonNotNull
    open var amount: String,
    @JsonNotNull
    open var expenditure_date: String,
    open var invoice_number: String? = null,
    open var description: String? = null,
    open var note: String? = null,
) : UTSObject()
fun normalizeServerUrl__4(url: String): String {
    if (url == "") {
        return ""
    }
    if (url.startsWith("http://localhost:8000")) {
        return baseUrl + url.substring(21)
    }
    if (url.startsWith("https://localhost:8000")) {
        return baseUrl + url.substring(22)
    }
    if (url.startsWith("http://127.0.0.1:8000")) {
        return baseUrl + url.substring(21)
    }
    if (url.startsWith("https://127.0.0.1:8000")) {
        return baseUrl + url.substring(22)
    }
    return url
}
fun intValue__8(value: Any?): Number {
    if (value == null) {
        return 0
    }
    val parsed = parseInt("" + value)
    if (isNaN(parsed)) {
        return 0
    }
    return parsed
}
fun stringValue__9(value: Any?): String {
    if (value == null) {
        return ""
    }
    return "" + value
}
fun boolValue__2(value: Any?): Boolean {
    return stringValue__9(value) == "true"
}
fun buildExpenseListQuery(data: ExpenseListQuery): UTSJSONObject {
    val query: UTSJSONObject = _uO("page" to data.page, "page_size" to data.page_size)
    if (data.search != null && data.search != "") {
        query["search"] = data.search
    }
    if (data.expenditure_type != null && data.expenditure_type != "") {
        query["expenditure_type"] = data.expenditure_type
    }
    if (data.expenditure_type_id != null && data.expenditure_type_id != "") {
        query["expenditure_type_id"] = data.expenditure_type_id
    }
    if (data.supplier != null && data.supplier != "") {
        query["supplier"] = data.supplier
    }
    if (data.supplier_id != null && data.supplier_id != "") {
        query["supplier_id"] = data.supplier_id
    }
    if (data.date_from != null && data.date_from != "") {
        query["date_from"] = data.date_from
    }
    if (data.date_to != null && data.date_to != "") {
        query["date_to"] = data.date_to
    }
    if (data.amount_min != null && data.amount_min != "") {
        query["amount_min"] = data.amount_min
    }
    if (data.amount_max != null && data.amount_max != "") {
        query["amount_max"] = data.amount_max
    }
    return query
}
fun buildMediaFileFromObject(rawObject: UTSJSONObject): ExpenseMediaFile {
    return ExpenseMediaFile(id = stringValue__9(rawObject["id"]), company = intValue__8(rawObject["company"]), original_filename = stringValue__9(rawObject["original_filename"]), file_type = stringValue__9(rawObject["file_type"]), file_type_display = stringValue__9(rawObject["file_type_display"]), mime_type = stringValue__9(rawObject["mime_type"]), file_size = intValue__8(rawObject["file_size"]), file_size_display = stringValue__9(rawObject["file_size_display"]), file_url = normalizeServerUrl__4(stringValue__9(rawObject["file_url"])), thumbnail_url = normalizeServerUrl__4(stringValue__9(rawObject["thumbnail_url"])), signed_url = normalizeServerUrl__4(stringValue__9(rawObject["signed_url"])), signed_thumbnail_url = normalizeServerUrl__4(stringValue__9(rawObject["signed_thumbnail_url"])), object_id = stringValue__9(rawObject["object_id"]), is_deleted = boolValue__2(rawObject["is_deleted"]), created_at = stringValue__9(rawObject["created_at"]), updated_at = stringValue__9(rawObject["updated_at"]))
}
fun buildMediaFilesFromValue(value: Any?): UTSArray<ExpenseMediaFile> {
    if (value == null) {
        return _uA()
    }
    val text = JSON.stringify(value)
    val rawArray = if (text == null || text == "") {
        null
    } else {
        JSON.parseArray<UTSJSONObject>(text)
    }
    if (rawArray == null) {
        return _uA()
    }
    val result: UTSArray<ExpenseMediaFile> = _uA()
    run {
        var index: Number = 0
        while(index < rawArray!!.length){
            result.push(buildMediaFileFromObject(rawArray!![index]))
            index += 1
        }
    }
    return result
}
fun buildExpenseItemFromObject(rawObject: UTSJSONObject): ExpenseItem {
    return ExpenseItem(id = intValue__8(rawObject["id"]), expenditure_type = intValue__8(rawObject["expenditure_type"]), expenditure_type_name = stringValue__9(rawObject["expenditure_type_name"]), amount = stringValue__9(rawObject["amount"]), expenditure_date = stringValue__9(rawObject["expenditure_date"]), invoice_number = stringValue__9(rawObject["invoice_number"]), supplier = intValue__8(rawObject["supplier"]), supplier_name = stringValue__9(rawObject["supplier_name"]), description = stringValue__9(rawObject["description"]), note = stringValue__9(rawObject["note"]), media_files = buildMediaFilesFromValue(rawObject["media_files"]), files_count = intValue__8(rawObject["files_count"]), is_deleted = boolValue__2(rawObject["is_deleted"]), created_at = stringValue__9(rawObject["created_at"]), updated_at = stringValue__9(rawObject["updated_at"]))
}
fun buildExpenseItemsFromValue(value: Any?): UTSArray<ExpenseItem> {
    if (value == null) {
        return _uA()
    }
    val text = JSON.stringify(value)
    val rawArray = if (text == null || text == "") {
        null
    } else {
        JSON.parseArray<UTSJSONObject>(text)
    }
    if (rawArray == null) {
        return _uA()
    }
    val result: UTSArray<ExpenseItem> = _uA()
    run {
        var index: Number = 0
        while(index < rawArray!!.length){
            result.push(buildExpenseItemFromObject(rawArray!![index]))
            index += 1
        }
    }
    return result
}
fun rawDataObject__1(raw: Any): UTSJSONObject {
    val rawText = JSON.stringify(raw)
    val rawObject = if (rawText == null || rawText == "") {
        null
    } else {
        JSON.parseObject<UTSJSONObject>(rawText)
    }
    if (rawObject == null) {
        throw UTSError("支出接口响应解析失败")
    }
    val dataValue = rawObject!!["data"]
    if (dataValue != null) {
        val dataText = JSON.stringify(dataValue)
        val dataObject = if (dataText == null || dataText == "") {
            null
        } else {
            JSON.parseObject<UTSJSONObject>(dataText)
        }
        if (dataObject != null) {
            return dataObject!!
        }
    }
    return rawObject!!
}
fun buildExpenseListResponse(raw: Any, query: ExpenseListQuery): ExpenseListResponse {
    val rawObject = rawDataObject__1(raw)
    var paginationObject: UTSJSONObject? = null
    val rawPagination = rawObject["pagination"]
    if (rawPagination != null) {
        val paginationText = JSON.stringify(rawPagination)
        if (paginationText != null && paginationText != "") {
            paginationObject = JSON.parseObject<UTSJSONObject>(paginationText)
        }
    }
    val results = buildExpenseItemsFromValue(rawObject["results"])
    var totalCount = intValue__8(rawObject["count"])
    if (totalCount <= 0) {
        totalCount = intValue__8(rawObject["total_count"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__8(paginationObject!!["total"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__8(paginationObject!!["count"])
    }
    if (totalCount <= 0) {
        totalCount = results.length
    }
    var currentPage = intValue__8(rawObject["current_page"])
    if (currentPage <= 0) {
        currentPage = intValue__8(rawObject["page"])
    }
    if (currentPage <= 0 && paginationObject != null) {
        currentPage = intValue__8(paginationObject!!["page"])
    }
    if (currentPage <= 0) {
        currentPage = query.page
    }
    var pageSize = intValue__8(rawObject["page_size"])
    if (pageSize <= 0 && paginationObject != null) {
        pageSize = intValue__8(paginationObject!!["page_size"])
    }
    if (pageSize <= 0) {
        pageSize = query.page_size
    }
    var totalPages = intValue__8(rawObject["total_pages"])
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__8(paginationObject!!["total_pages"])
    }
    if (totalPages <= 0 && pageSize > 0) {
        totalPages = Math.ceil(totalCount / pageSize)
    }
    if (totalPages <= 0) {
        totalPages = 1
    }
    return ExpenseListResponse(results = results, count = totalCount, total_count = totalCount, total_pages = totalPages, current_page = currentPage, page_size = pageSize, summary = if (rawObject["summary"] == null) {
        null
    } else {
        (rawObject["summary"] as UTSJSONObject)
    }
    )
}
fun buildOptionsResponse(raw: Any): ExpenseOptionsResponse {
    val rawObject = rawDataObject__1(raw)
    val groups: UTSArray<ExpenseOptionGroup> = _uA()
    val rawGroups = rawObject["groups"]
    if (rawGroups != null) {
        val groupsText = JSON.stringify(rawGroups)
        val groupObjects = if (groupsText == null || groupsText == "") {
            null
        } else {
            JSON.parseArray<UTSJSONObject>(groupsText)
        }
        if (groupObjects != null) {
            run {
                var groupIndex: Number = 0
                while(groupIndex < groupObjects!!.length){
                    val groupObject = groupObjects!![groupIndex]
                    val items: UTSArray<ExpenseOptionItem> = _uA()
                    val rawItems = groupObject["items"]
                    if (rawItems != null) {
                        val itemsText = JSON.stringify(rawItems)
                        val itemObjects = if (itemsText == null || itemsText == "") {
                            null
                        } else {
                            JSON.parseArray<UTSJSONObject>(itemsText)
                        }
                        if (itemObjects != null) {
                            run {
                                var itemIndex: Number = 0
                                while(itemIndex < itemObjects!!.length){
                                    val itemObject = itemObjects!![itemIndex]
                                    items.push(ExpenseOptionItem(value = stringValue__9(itemObject["value"]), label = stringValue__9(itemObject["label"]), extra = if (itemObject["extra"] == null) {
                                        (_uO())
                                    } else {
                                        (itemObject["extra"] as UTSJSONObject)
                                    }
                                    ))
                                    itemIndex += 1
                                }
                            }
                        }
                    }
                    groups.push(ExpenseOptionGroup(key = stringValue__9(groupObject["key"]), label = stringValue__9(groupObject["label"]), control = stringValue__9(groupObject["control"]), count = intValue__8(groupObject["count"]), items = items))
                    groupIndex += 1
                }
            }
        }
    }
    return ExpenseOptionsResponse(resource = stringValue__9(rawObject["resource"]), total_groups = intValue__8(rawObject["total_groups"]), groups = groups)
}
fun buildMutationBody(data: ExpenseMutationData): UTSJSONObject {
    val body: UTSJSONObject = _uO("amount" to data.amount, "expenditure_date" to data.expenditure_date, "invoice_number" to if (data.invoice_number == null) {
        ""
    } else {
        data.invoice_number
    }
    , "description" to if (data.description == null) {
        ""
    } else {
        data.description
    }
    , "note" to if (data.note == null) {
        ""
    } else {
        data.note
    }
    )
    if (data.expenditure_type_id != null && data.expenditure_type_id != "") {
        body["expenditure_type_id"] = parseInt(data.expenditure_type_id!!)
    } else {
        body["expenditure_type_id"] = null
    }
    if (data.supplier_id != null && data.supplier_id != "") {
        body["supplier_id"] = parseInt(data.supplier_id!!)
    } else {
        body["supplier_id"] = null
    }
    return body
}
fun detailPath(id: Any): String {
    return "/api/expenses/expenditures/" + stringValue__9(id) + "/"
}
fun getExpenseList(data: ExpenseListQuery): UTSPromise<ExpenseListResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/expenses/expenditures/", "GET", buildExpenseListQuery(data), true))
            return@w buildExpenseListResponse(raw, data)
    })
}
fun getExpenseOptions(key: String? = null, search: String? = null, limit: Number = 20): UTSPromise<ExpenseOptionsResponse> {
    return wrapUTSPromise(suspend w@{
            val query: UTSJSONObject = _uO()
            if (key != null && key != "") {
                query["key"] = key
            }
            if (search != null && search != "") {
                query["search"] = search
            }
            if (limit > 0) {
                query["limit"] = limit
            }
            val raw = await(request("/api/expenses/expenditures/options/", "GET", query, true))
            return@w buildOptionsResponse(raw)
    })
}
fun getExpenseDetail(id: Any): UTSPromise<ExpenseItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(detailPath(id), "GET", _uO(), true))
            return@w buildExpenseItemFromObject(rawDataObject__1(raw))
    })
}
fun createExpense(data: ExpenseMutationData): UTSPromise<ExpenseItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/expenses/expenditures/", "POST", buildMutationBody(data), true))
            return@w buildExpenseItemFromObject(rawDataObject__1(raw))
    })
}
fun updateExpense(id: Any, data: ExpenseMutationData): UTSPromise<ExpenseItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(detailPath(id), "PUT", buildMutationBody(data), true))
            return@w buildExpenseItemFromObject(rawDataObject__1(raw))
    })
}
fun deleteExpense(id: Any): UTSPromise<Any> {
    return request(detailPath(id), "DELETE", _uO(), true)
}
val GenPagesExpensesIndexClass = CreateVueComponent(GenPagesExpensesIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesExpensesIndex.inheritAttrs, inject = GenPagesExpensesIndex.inject, props = GenPagesExpensesIndex.props, propsNeedCastKeys = GenPagesExpensesIndex.propsNeedCastKeys, emits = GenPagesExpensesIndex.emits, components = GenPagesExpensesIndex.components, styles = GenPagesExpensesIndex.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesExpensesIndex.setup(props as GenPagesExpensesIndex)
    }
    )
}
, fun(instance, renderer): GenPagesExpensesIndex {
    return GenPagesExpensesIndex(instance, renderer)
}
)
open class SelectOption__7 (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var text: String,
) : UTSObject()
val GenPagesExpensesFromClass = CreateVueComponent(GenPagesExpensesFrom::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesExpensesFrom.inheritAttrs, inject = GenPagesExpensesFrom.inject, props = GenPagesExpensesFrom.props, propsNeedCastKeys = GenPagesExpensesFrom.propsNeedCastKeys, emits = GenPagesExpensesFrom.emits, components = GenPagesExpensesFrom.components, styles = GenPagesExpensesFrom.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesExpensesFrom.setup(props as GenPagesExpensesFrom)
    }
    )
}
, fun(instance, renderer): GenPagesExpensesFrom {
    return GenPagesExpensesFrom(instance, renderer)
}
)
open class PurchaseListQuery (
    open var search: String? = null,
    @JsonNotNull
    open var page: Number,
    @JsonNotNull
    open var page_size: Number,
    open var status: String? = null,
    open var receive_status: String? = null,
    open var supplier: String? = null,
    open var date_from: String? = null,
    open var date_to: String? = null,
    open var min_amount: String? = null,
    open var max_amount: String? = null,
) : UTSObject()
open class PurchaseItem (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var purchase_number: String,
    @JsonNotNull
    open var purchase_date: String,
    @JsonNotNull
    open var status: String,
    @JsonNotNull
    open var status_display: String,
    @JsonNotNull
    open var shop: Number,
    @JsonNotNull
    open var shop_name: String,
    @JsonNotNull
    open var supplier: Number,
    @JsonNotNull
    open var supplier_name: String,
    @JsonNotNull
    open var total_quantity: Number,
    @JsonNotNull
    open var received_quantity: Number,
    @JsonNotNull
    open var total_amount: String,
    @JsonNotNull
    open var receive_progress: String,
    @JsonNotNull
    open var is_fully_received: Boolean = false,
    @JsonNotNull
    open var remark: String,
    @JsonNotNull
    open var created_at: String,
    @JsonNotNull
    open var updated_at: String,
    @JsonNotNull
    open var items: UTSArray<UTSJSONObject>,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return PurchaseItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class PurchaseItemReactiveObject : PurchaseItem, IUTSReactive<PurchaseItem> {
    override var __v_raw: PurchaseItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: PurchaseItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, purchase_number = __v_raw.purchase_number, purchase_date = __v_raw.purchase_date, status = __v_raw.status, status_display = __v_raw.status_display, shop = __v_raw.shop, shop_name = __v_raw.shop_name, supplier = __v_raw.supplier, supplier_name = __v_raw.supplier_name, total_quantity = __v_raw.total_quantity, received_quantity = __v_raw.received_quantity, total_amount = __v_raw.total_amount, receive_progress = __v_raw.receive_progress, is_fully_received = __v_raw.is_fully_received, remark = __v_raw.remark, created_at = __v_raw.created_at, updated_at = __v_raw.updated_at, items = __v_raw.items) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): PurchaseItemReactiveObject {
        return PurchaseItemReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return _tRG(__v_raw, "id", __v_raw.id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            _tRS(__v_raw, "id", oldValue, value)
        }
    override var purchase_number: String
        get() {
            return _tRG(__v_raw, "purchase_number", __v_raw.purchase_number, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("purchase_number")) {
                return
            }
            val oldValue = __v_raw.purchase_number
            __v_raw.purchase_number = value
            _tRS(__v_raw, "purchase_number", oldValue, value)
        }
    override var purchase_date: String
        get() {
            return _tRG(__v_raw, "purchase_date", __v_raw.purchase_date, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("purchase_date")) {
                return
            }
            val oldValue = __v_raw.purchase_date
            __v_raw.purchase_date = value
            _tRS(__v_raw, "purchase_date", oldValue, value)
        }
    override var status: String
        get() {
            return _tRG(__v_raw, "status", __v_raw.status, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("status")) {
                return
            }
            val oldValue = __v_raw.status
            __v_raw.status = value
            _tRS(__v_raw, "status", oldValue, value)
        }
    override var status_display: String
        get() {
            return _tRG(__v_raw, "status_display", __v_raw.status_display, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("status_display")) {
                return
            }
            val oldValue = __v_raw.status_display
            __v_raw.status_display = value
            _tRS(__v_raw, "status_display", oldValue, value)
        }
    override var shop: Number
        get() {
            return _tRG(__v_raw, "shop", __v_raw.shop, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("shop")) {
                return
            }
            val oldValue = __v_raw.shop
            __v_raw.shop = value
            _tRS(__v_raw, "shop", oldValue, value)
        }
    override var shop_name: String
        get() {
            return _tRG(__v_raw, "shop_name", __v_raw.shop_name, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("shop_name")) {
                return
            }
            val oldValue = __v_raw.shop_name
            __v_raw.shop_name = value
            _tRS(__v_raw, "shop_name", oldValue, value)
        }
    override var supplier: Number
        get() {
            return _tRG(__v_raw, "supplier", __v_raw.supplier, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("supplier")) {
                return
            }
            val oldValue = __v_raw.supplier
            __v_raw.supplier = value
            _tRS(__v_raw, "supplier", oldValue, value)
        }
    override var supplier_name: String
        get() {
            return _tRG(__v_raw, "supplier_name", __v_raw.supplier_name, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("supplier_name")) {
                return
            }
            val oldValue = __v_raw.supplier_name
            __v_raw.supplier_name = value
            _tRS(__v_raw, "supplier_name", oldValue, value)
        }
    override var total_quantity: Number
        get() {
            return _tRG(__v_raw, "total_quantity", __v_raw.total_quantity, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("total_quantity")) {
                return
            }
            val oldValue = __v_raw.total_quantity
            __v_raw.total_quantity = value
            _tRS(__v_raw, "total_quantity", oldValue, value)
        }
    override var received_quantity: Number
        get() {
            return _tRG(__v_raw, "received_quantity", __v_raw.received_quantity, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("received_quantity")) {
                return
            }
            val oldValue = __v_raw.received_quantity
            __v_raw.received_quantity = value
            _tRS(__v_raw, "received_quantity", oldValue, value)
        }
    override var total_amount: String
        get() {
            return _tRG(__v_raw, "total_amount", __v_raw.total_amount, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("total_amount")) {
                return
            }
            val oldValue = __v_raw.total_amount
            __v_raw.total_amount = value
            _tRS(__v_raw, "total_amount", oldValue, value)
        }
    override var receive_progress: String
        get() {
            return _tRG(__v_raw, "receive_progress", __v_raw.receive_progress, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("receive_progress")) {
                return
            }
            val oldValue = __v_raw.receive_progress
            __v_raw.receive_progress = value
            _tRS(__v_raw, "receive_progress", oldValue, value)
        }
    override var is_fully_received: Boolean
        get() {
            return _tRG(__v_raw, "is_fully_received", __v_raw.is_fully_received, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("is_fully_received")) {
                return
            }
            val oldValue = __v_raw.is_fully_received
            __v_raw.is_fully_received = value
            _tRS(__v_raw, "is_fully_received", oldValue, value)
        }
    override var remark: String
        get() {
            return _tRG(__v_raw, "remark", __v_raw.remark, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("remark")) {
                return
            }
            val oldValue = __v_raw.remark
            __v_raw.remark = value
            _tRS(__v_raw, "remark", oldValue, value)
        }
    override var created_at: String
        get() {
            return _tRG(__v_raw, "created_at", __v_raw.created_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("created_at")) {
                return
            }
            val oldValue = __v_raw.created_at
            __v_raw.created_at = value
            _tRS(__v_raw, "created_at", oldValue, value)
        }
    override var updated_at: String
        get() {
            return _tRG(__v_raw, "updated_at", __v_raw.updated_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("updated_at")) {
                return
            }
            val oldValue = __v_raw.updated_at
            __v_raw.updated_at = value
            _tRS(__v_raw, "updated_at", oldValue, value)
        }
    override var items: UTSArray<UTSJSONObject>
        get() {
            return _tRG(__v_raw, "items", __v_raw.items, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("items")) {
                return
            }
            val oldValue = __v_raw.items
            __v_raw.items = value
            _tRS(__v_raw, "items", oldValue, value)
        }
}
open class PurchaseListResponse (
    @JsonNotNull
    open var results: UTSArray<PurchaseItem>,
    @JsonNotNull
    open var total_count: Number,
    @JsonNotNull
    open var total_pages: Number,
    @JsonNotNull
    open var current_page: Number,
    @JsonNotNull
    open var page_size: Number,
) : UTSObject()
open class PurchaseMutationData (
    @JsonNotNull
    open var purchase_date: String,
    @JsonNotNull
    open var shop: String,
    @JsonNotNull
    open var supplier: String,
    open var remark: String? = null,
    @JsonNotNull
    open var items: UTSArray<UTSJSONObject>,
) : UTSObject()
open class PurchaseOptionItem (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var text: String,
) : UTSObject()
open class PurchaseDetailItem (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var purchase: Number,
    @JsonNotNull
    open var purchase_number: String,
    @JsonNotNull
    open var product: Number,
    @JsonNotNull
    open var product_name: String,
    @JsonNotNull
    open var product_sku: String,
    @JsonNotNull
    open var product_barcode: String,
    @JsonNotNull
    open var product_image: String,
    @JsonNotNull
    open var product_images: UTSArray<String>,
    @JsonNotNull
    open var quantity: Number,
    @JsonNotNull
    open var unit_price: String,
    @JsonNotNull
    open var amount: String,
    @JsonNotNull
    open var received_quantity: Number,
    @JsonNotNull
    open var remaining_quantity: Number,
    @JsonNotNull
    open var receive_progress: String,
    @JsonNotNull
    open var is_fully_received: Boolean = false,
    @JsonNotNull
    open var notes: String,
    @JsonNotNull
    open var created_at: String,
    @JsonNotNull
    open var updated_at: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return PurchaseDetailItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class PurchaseDetailItemReactiveObject : PurchaseDetailItem, IUTSReactive<PurchaseDetailItem> {
    override var __v_raw: PurchaseDetailItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: PurchaseDetailItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, purchase = __v_raw.purchase, purchase_number = __v_raw.purchase_number, product = __v_raw.product, product_name = __v_raw.product_name, product_sku = __v_raw.product_sku, product_barcode = __v_raw.product_barcode, product_image = __v_raw.product_image, product_images = __v_raw.product_images, quantity = __v_raw.quantity, unit_price = __v_raw.unit_price, amount = __v_raw.amount, received_quantity = __v_raw.received_quantity, remaining_quantity = __v_raw.remaining_quantity, receive_progress = __v_raw.receive_progress, is_fully_received = __v_raw.is_fully_received, notes = __v_raw.notes, created_at = __v_raw.created_at, updated_at = __v_raw.updated_at) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): PurchaseDetailItemReactiveObject {
        return PurchaseDetailItemReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return _tRG(__v_raw, "id", __v_raw.id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            _tRS(__v_raw, "id", oldValue, value)
        }
    override var purchase: Number
        get() {
            return _tRG(__v_raw, "purchase", __v_raw.purchase, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("purchase")) {
                return
            }
            val oldValue = __v_raw.purchase
            __v_raw.purchase = value
            _tRS(__v_raw, "purchase", oldValue, value)
        }
    override var purchase_number: String
        get() {
            return _tRG(__v_raw, "purchase_number", __v_raw.purchase_number, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("purchase_number")) {
                return
            }
            val oldValue = __v_raw.purchase_number
            __v_raw.purchase_number = value
            _tRS(__v_raw, "purchase_number", oldValue, value)
        }
    override var product: Number
        get() {
            return _tRG(__v_raw, "product", __v_raw.product, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("product")) {
                return
            }
            val oldValue = __v_raw.product
            __v_raw.product = value
            _tRS(__v_raw, "product", oldValue, value)
        }
    override var product_name: String
        get() {
            return _tRG(__v_raw, "product_name", __v_raw.product_name, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("product_name")) {
                return
            }
            val oldValue = __v_raw.product_name
            __v_raw.product_name = value
            _tRS(__v_raw, "product_name", oldValue, value)
        }
    override var product_sku: String
        get() {
            return _tRG(__v_raw, "product_sku", __v_raw.product_sku, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("product_sku")) {
                return
            }
            val oldValue = __v_raw.product_sku
            __v_raw.product_sku = value
            _tRS(__v_raw, "product_sku", oldValue, value)
        }
    override var product_barcode: String
        get() {
            return _tRG(__v_raw, "product_barcode", __v_raw.product_barcode, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("product_barcode")) {
                return
            }
            val oldValue = __v_raw.product_barcode
            __v_raw.product_barcode = value
            _tRS(__v_raw, "product_barcode", oldValue, value)
        }
    override var product_image: String
        get() {
            return _tRG(__v_raw, "product_image", __v_raw.product_image, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("product_image")) {
                return
            }
            val oldValue = __v_raw.product_image
            __v_raw.product_image = value
            _tRS(__v_raw, "product_image", oldValue, value)
        }
    override var product_images: UTSArray<String>
        get() {
            return _tRG(__v_raw, "product_images", __v_raw.product_images, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("product_images")) {
                return
            }
            val oldValue = __v_raw.product_images
            __v_raw.product_images = value
            _tRS(__v_raw, "product_images", oldValue, value)
        }
    override var quantity: Number
        get() {
            return _tRG(__v_raw, "quantity", __v_raw.quantity, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("quantity")) {
                return
            }
            val oldValue = __v_raw.quantity
            __v_raw.quantity = value
            _tRS(__v_raw, "quantity", oldValue, value)
        }
    override var unit_price: String
        get() {
            return _tRG(__v_raw, "unit_price", __v_raw.unit_price, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("unit_price")) {
                return
            }
            val oldValue = __v_raw.unit_price
            __v_raw.unit_price = value
            _tRS(__v_raw, "unit_price", oldValue, value)
        }
    override var amount: String
        get() {
            return _tRG(__v_raw, "amount", __v_raw.amount, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("amount")) {
                return
            }
            val oldValue = __v_raw.amount
            __v_raw.amount = value
            _tRS(__v_raw, "amount", oldValue, value)
        }
    override var received_quantity: Number
        get() {
            return _tRG(__v_raw, "received_quantity", __v_raw.received_quantity, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("received_quantity")) {
                return
            }
            val oldValue = __v_raw.received_quantity
            __v_raw.received_quantity = value
            _tRS(__v_raw, "received_quantity", oldValue, value)
        }
    override var remaining_quantity: Number
        get() {
            return _tRG(__v_raw, "remaining_quantity", __v_raw.remaining_quantity, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("remaining_quantity")) {
                return
            }
            val oldValue = __v_raw.remaining_quantity
            __v_raw.remaining_quantity = value
            _tRS(__v_raw, "remaining_quantity", oldValue, value)
        }
    override var receive_progress: String
        get() {
            return _tRG(__v_raw, "receive_progress", __v_raw.receive_progress, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("receive_progress")) {
                return
            }
            val oldValue = __v_raw.receive_progress
            __v_raw.receive_progress = value
            _tRS(__v_raw, "receive_progress", oldValue, value)
        }
    override var is_fully_received: Boolean
        get() {
            return _tRG(__v_raw, "is_fully_received", __v_raw.is_fully_received, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("is_fully_received")) {
                return
            }
            val oldValue = __v_raw.is_fully_received
            __v_raw.is_fully_received = value
            _tRS(__v_raw, "is_fully_received", oldValue, value)
        }
    override var notes: String
        get() {
            return _tRG(__v_raw, "notes", __v_raw.notes, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("notes")) {
                return
            }
            val oldValue = __v_raw.notes
            __v_raw.notes = value
            _tRS(__v_raw, "notes", oldValue, value)
        }
    override var created_at: String
        get() {
            return _tRG(__v_raw, "created_at", __v_raw.created_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("created_at")) {
                return
            }
            val oldValue = __v_raw.created_at
            __v_raw.created_at = value
            _tRS(__v_raw, "created_at", oldValue, value)
        }
    override var updated_at: String
        get() {
            return _tRG(__v_raw, "updated_at", __v_raw.updated_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("updated_at")) {
                return
            }
            val oldValue = __v_raw.updated_at
            __v_raw.updated_at = value
            _tRS(__v_raw, "updated_at", oldValue, value)
        }
}
open class PurchaseDetailListQuery (
    open var search: String? = null,
    @JsonNotNull
    open var page: Number,
    @JsonNotNull
    open var page_size: Number,
    open var purchase: String? = null,
    open var product: String? = null,
    open var is_fully_received: String? = null,
) : UTSObject()
open class PurchaseDetailListResponse (
    @JsonNotNull
    open var results: UTSArray<PurchaseDetailItem>,
    @JsonNotNull
    open var total_count: Number,
    @JsonNotNull
    open var total_pages: Number,
    @JsonNotNull
    open var current_page: Number,
    @JsonNotNull
    open var page_size: Number,
) : UTSObject()
open class PurchaseDetailMutationData (
    @JsonNotNull
    open var purchase: String,
    @JsonNotNull
    open var product: String,
    @JsonNotNull
    open var quantity: String,
    open var received_quantity: String? = null,
    open var notes: String? = null,
) : UTSObject()
fun stringValue__10(value: Any?): String {
    if (value == null) {
        return ""
    }
    return "" + value
}
fun normalizeServerUrl__5(url: String): String {
    if (url == "") {
        return ""
    }
    if (url.startsWith("http://localhost:8000")) {
        return baseUrl + url.substring(21)
    }
    if (url.startsWith("https://localhost:8000")) {
        return baseUrl + url.substring(22)
    }
    if (url.startsWith("http://127.0.0.1:8000")) {
        return baseUrl + url.substring(21)
    }
    if (url.startsWith("https://127.0.0.1:8000")) {
        return baseUrl + url.substring(22)
    }
    return url
}
fun intValue__9(value: Any?): Number {
    val parsed = parseInt(stringValue__10(value))
    if (isNaN(parsed)) {
        return 0
    }
    return parsed
}
fun boolValue__3(value: Any?): Boolean {
    val text = stringValue__10(value).toLowerCase()
    return text == "true" || text == "1"
}
fun parseObject__4(value: Any?): UTSJSONObject? {
    if (value == null) {
        return null
    }
    val text = JSON.stringify(value)
    if (text == null || text == "") {
        return null
    }
    try {
        return JSON.parseObject<UTSJSONObject>(text)
    }
     catch (error: Throwable) {
        return null
    }
}
fun rawDataObject__2(raw: Any): UTSJSONObject {
    val rawObject = parseObject__4(raw)
    if (rawObject == null) {
        throw UTSError("采购接口响应解析失败")
    }
    val dataValue = rawObject["data"]
    val dataObject = parseObject__4(dataValue)
    if (dataObject != null) {
        return dataObject!!
    }
    return rawObject!!
}
fun rawDetailObject(raw: Any): UTSJSONObject {
    val rawObject = rawDataObject__2(raw)
    val detailObject = parseObject__4(rawObject["detail"])
    if (detailObject != null) {
        return detailObject!!
    }
    val purchaseDetailObject = parseObject__4(rawObject["purchase_detail"])
    if (purchaseDetailObject != null) {
        return purchaseDetailObject!!
    }
    return rawObject
}
fun parseObjectArray__3(value: Any?): UTSArray<UTSJSONObject> {
    if (value == null) {
        return _uA<UTSJSONObject>()
    }
    val text = JSON.stringify(value)
    if (text == null || text == "") {
        return _uA<UTSJSONObject>()
    }
    var parsed: UTSArray<UTSJSONObject>? = null
    try {
        parsed = JSON.parseArray<UTSJSONObject>(text)
    }
     catch (error: Throwable) {
        return _uA<UTSJSONObject>()
    }
    if (parsed == null) {
        return _uA<UTSJSONObject>()
    }
    return parsed!!
}
fun stringArrayValue__2(value: Any?): UTSArray<String> {
    if (value == null) {
        return _uA<String>()
    }
    val text = JSON.stringify(value)
    if (text == null || text == "") {
        return _uA<String>()
    }
    var parsed: UTSArray<Any>? = null
    try {
        parsed = JSON.parseArray<Any>(text)
    }
     catch (error: Throwable) {
        val singleValue = stringValue__10(value)
        if (singleValue == "") {
            return _uA<String>()
        }
        val result: UTSArray<String> = _uA()
        result.push(singleValue)
        return result
    }
    if (parsed == null) {
        return _uA<String>()
    }
    val result: UTSArray<String> = _uA()
    run {
        var index: Number = 0
        while(index < parsed.length){
            result.push(stringValue__10(parsed[index]))
            index += 1
        }
    }
    return result
}
fun pushImageUrl(images: UTSArray<String>, url: String) {
    val normalizedUrl = normalizeServerUrl__5(url)
    if (normalizedUrl != "" && images.indexOf(normalizedUrl) < 0) {
        images.push(normalizedUrl)
    }
}
fun mediaImageUrl(rawObject: UTSJSONObject): String {
    var imageUrl = stringValue__10(rawObject["signed_thumbnail_url"])
    if (imageUrl == "") {
        imageUrl = stringValue__10(rawObject["thumbnail_url"])
    }
    if (imageUrl == "") {
        imageUrl = stringValue__10(rawObject["signed_url"])
    }
    if (imageUrl == "") {
        imageUrl = stringValue__10(rawObject["file_url"])
    }
    return imageUrl
}
fun appendMediaImages(images: UTSArray<String>, value: Any?) {
    val mediaFiles = parseObjectArray__3(value)
    run {
        var index: Number = 0
        while(index < mediaFiles.length){
            pushImageUrl(images, mediaImageUrl(mediaFiles[index]))
            index += 1
        }
    }
}
fun appendStringImages(images: UTSArray<String>, value: Any?) {
    val rawImages = stringArrayValue__2(value)
    run {
        var index: Number = 0
        while(index < rawImages.length){
            pushImageUrl(images, rawImages[index])
            index += 1
        }
    }
}
fun buildProductImages(rawObject: UTSJSONObject, productObject: UTSJSONObject?): UTSArray<String> {
    val images: UTSArray<String> = _uA()
    pushImageUrl(images, stringValue__10(rawObject["product_image"]))
    appendStringImages(images, rawObject["product_images"])
    appendMediaImages(images, rawObject["product_media_files"])
    if (productObject != null) {
        pushImageUrl(images, stringValue__10(productObject!!["image"]))
        appendStringImages(images, productObject!!["images"])
        appendMediaImages(images, productObject!!["media_files"])
    }
    return images
}
fun buildQuery(data: PurchaseListQuery): UTSJSONObject {
    val query: UTSJSONObject = _uO("page" to data.page, "page_size" to data.page_size)
    if (data.search != null && data.search != "") {
        query["search"] = data.search
    }
    if (data.status != null && data.status != "") {
        query["status"] = data.status
    }
    if (data.receive_status != null && data.receive_status != "") {
        query["receive_status"] = data.receive_status
    }
    if (data.supplier != null && data.supplier != "") {
        query["supplier"] = data.supplier
    }
    if (data.date_from != null && data.date_from != "") {
        query["date_from"] = data.date_from
    }
    if (data.date_to != null && data.date_to != "") {
        query["date_to"] = data.date_to
    }
    if (data.min_amount != null && data.min_amount != "") {
        query["min_amount"] = data.min_amount
    }
    if (data.max_amount != null && data.max_amount != "") {
        query["max_amount"] = data.max_amount
    }
    return query
}
fun buildDetailQuery(data: PurchaseDetailListQuery): UTSJSONObject {
    val query: UTSJSONObject = _uO("page" to data.page, "page_size" to data.page_size)
    if (data.search != null && data.search != "") {
        query["search"] = data.search
    }
    if (data.purchase != null && data.purchase != "") {
        query["purchase"] = data.purchase
    }
    if (data.product != null && data.product != "") {
        query["product"] = data.product
    }
    if (data.is_fully_received != null && data.is_fully_received != "") {
        query["is_fully_received"] = data.is_fully_received
    }
    return query
}
fun buildPurchaseItem(rawObject: UTSJSONObject): PurchaseItem {
    var shopName = stringValue__10(rawObject["shop_name"])
    val shopInfo = parseObject__4(rawObject["shop_info"])
    if (shopName == "" && shopInfo != null) {
        shopName = stringValue__10(shopInfo!!["name"])
    }
    var supplierName = stringValue__10(rawObject["supplier_name"])
    val supplierInfo = parseObject__4(rawObject["supplier_info"])
    if (supplierName == "" && supplierInfo != null) {
        supplierName = stringValue__10(supplierInfo!!["name"])
    }
    return PurchaseItem(id = intValue__9(rawObject["id"]), purchase_number = stringValue__10(rawObject["purchase_number"]), purchase_date = stringValue__10(rawObject["purchase_date"]), status = stringValue__10(rawObject["status"]), status_display = stringValue__10(rawObject["status_display"]), shop = intValue__9(rawObject["shop"]), shop_name = shopName, supplier = intValue__9(rawObject["supplier"]), supplier_name = supplierName, total_quantity = intValue__9(rawObject["total_quantity"]), received_quantity = intValue__9(rawObject["received_quantity"]), total_amount = stringValue__10(rawObject["total_amount"]), receive_progress = stringValue__10(rawObject["receive_progress"]), is_fully_received = boolValue__3(rawObject["is_fully_received"]), remark = stringValue__10(rawObject["remark"]), created_at = stringValue__10(rawObject["created_at"]), updated_at = stringValue__10(rawObject["updated_at"]), items = parseObjectArray__3(rawObject["items"]))
}
fun buildPurchaseDetailItem(rawObject: UTSJSONObject): PurchaseDetailItem {
    var purchaseObject = parseObject__4(rawObject["purchase"])
    val purchaseInfo = parseObject__4(rawObject["purchase_info"])
    if (purchaseObject == null && purchaseInfo != null) {
        purchaseObject = purchaseInfo
    }
    var purchaseId = intValue__9(rawObject["purchase"])
    if (purchaseId <= 0 && purchaseObject != null) {
        purchaseId = intValue__9(purchaseObject!!["id"])
    }
    var purchaseNumber = stringValue__10(rawObject["purchase_number"])
    if (purchaseNumber == "" && purchaseObject != null) {
        purchaseNumber = stringValue__10(purchaseObject!!["purchase_number"])
    }
    var productObject = parseObject__4(rawObject["product"])
    val productInfo = parseObject__4(rawObject["product_info"])
    val productDetail = parseObject__4(rawObject["product_detail"])
    if (productObject == null && productInfo != null) {
        productObject = productInfo
    }
    if (productObject == null && productDetail != null) {
        productObject = productDetail
    }
    var productId = intValue__9(rawObject["product"])
    if (productId <= 0 && productObject != null) {
        productId = intValue__9(productObject!!["id"])
    }
    var productName = stringValue__10(rawObject["product_name"])
    if (productName == "" && productObject != null) {
        productName = stringValue__10(productObject!!["name_cn"])
    }
    if (productName == "" && productObject != null) {
        productName = stringValue__10(productObject!!["name"])
    }
    if (productName == "" && productObject != null) {
        productName = stringValue__10(productObject!!["title"])
    }
    var productSku = stringValue__10(rawObject["product_sku"])
    if (productSku == "" && productObject != null) {
        productSku = stringValue__10(productObject!!["sku"])
    }
    var productBarcode = stringValue__10(rawObject["product_barcode"])
    if (productBarcode == "" && productObject != null) {
        productBarcode = stringValue__10(productObject!!["barcode"])
    }
    val productImages = buildProductImages(rawObject, productObject)
    return PurchaseDetailItem(id = intValue__9(rawObject["id"]), purchase = purchaseId, purchase_number = purchaseNumber, product = productId, product_name = productName, product_sku = productSku, product_barcode = productBarcode, product_image = if (productImages.length > 0) {
        productImages[0]
    } else {
        ""
    }
    , product_images = productImages, quantity = intValue__9(rawObject["quantity"]), unit_price = stringValue__10(rawObject["unit_price"]), amount = stringValue__10(rawObject["amount"]), received_quantity = intValue__9(rawObject["received_quantity"]), remaining_quantity = intValue__9(rawObject["remaining_quantity"]), receive_progress = stringValue__10(rawObject["receive_progress"]), is_fully_received = boolValue__3(rawObject["is_fully_received"]), notes = stringValue__10(rawObject["notes"]), created_at = stringValue__10(rawObject["created_at"]), updated_at = stringValue__10(rawObject["updated_at"]))
}
fun buildItems(value: Any?): UTSArray<PurchaseItem> {
    val rawArray = parseObjectArray__3(value)
    val result: UTSArray<PurchaseItem> = _uA()
    run {
        var index: Number = 0
        while(index < rawArray.length){
            result.push(buildPurchaseItem(rawArray[index]))
            index += 1
        }
    }
    return result
}
fun buildDetailItems(value: Any?): UTSArray<PurchaseDetailItem> {
    val rawArray = parseObjectArray__3(value)
    val result: UTSArray<PurchaseDetailItem> = _uA()
    run {
        var index: Number = 0
        while(index < rawArray.length){
            result.push(buildPurchaseDetailItem(rawArray[index]))
            index += 1
        }
    }
    return result
}
fun buildListResponse__1(raw: Any, query: PurchaseListQuery): PurchaseListResponse {
    val rawObject = rawDataObject__2(raw)
    var paginationObject: UTSJSONObject? = null
    val rawPagination = rawObject["pagination"]
    if (rawPagination != null) {
        paginationObject = parseObject__4(rawPagination)
    }
    val results = buildItems(rawObject["results"])
    var totalCount = intValue__9(rawObject["count"])
    if (totalCount <= 0) {
        totalCount = intValue__9(rawObject["total_count"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__9(paginationObject!!["total"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__9(paginationObject!!["count"])
    }
    if (totalCount <= 0) {
        totalCount = results.length
    }
    var currentPage = intValue__9(rawObject["current_page"])
    if (currentPage <= 0) {
        currentPage = intValue__9(rawObject["page"])
    }
    if (currentPage <= 0 && paginationObject != null) {
        currentPage = intValue__9(paginationObject!!["page"])
    }
    if (currentPage <= 0) {
        currentPage = query.page
    }
    var pageSize = intValue__9(rawObject["page_size"])
    if (pageSize <= 0 && paginationObject != null) {
        pageSize = intValue__9(paginationObject!!["page_size"])
    }
    if (pageSize <= 0) {
        pageSize = query.page_size
    }
    var totalPages = intValue__9(rawObject["total_pages"])
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__9(paginationObject!!["total_pages"])
    }
    if (totalPages <= 0 && pageSize > 0) {
        totalPages = Math.ceil(totalCount / pageSize)
    }
    if (totalPages <= 0) {
        totalPages = 1
    }
    return PurchaseListResponse(results = results, total_count = totalCount, total_pages = totalPages, current_page = currentPage, page_size = pageSize)
}
fun buildDetailListResponse(raw: Any, query: PurchaseDetailListQuery): PurchaseDetailListResponse {
    val rawObject = rawDataObject__2(raw)
    var paginationObject: UTSJSONObject? = null
    val rawPagination = rawObject["pagination"]
    if (rawPagination != null) {
        paginationObject = parseObject__4(rawPagination)
    }
    val results = buildDetailItems(rawObject["results"])
    var totalCount = intValue__9(rawObject["count"])
    if (totalCount <= 0) {
        totalCount = intValue__9(rawObject["total_count"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__9(paginationObject!!["total"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__9(paginationObject!!["count"])
    }
    if (totalCount <= 0) {
        totalCount = results.length
    }
    var currentPage = intValue__9(rawObject["current_page"])
    if (currentPage <= 0) {
        currentPage = intValue__9(rawObject["page"])
    }
    if (currentPage <= 0 && paginationObject != null) {
        currentPage = intValue__9(paginationObject!!["page"])
    }
    if (currentPage <= 0) {
        currentPage = query.page
    }
    var pageSize = intValue__9(rawObject["page_size"])
    if (pageSize <= 0 && paginationObject != null) {
        pageSize = intValue__9(paginationObject!!["page_size"])
    }
    if (pageSize <= 0) {
        pageSize = query.page_size
    }
    var totalPages = intValue__9(rawObject["total_pages"])
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__9(paginationObject!!["total_pages"])
    }
    if (totalPages <= 0 && pageSize > 0) {
        totalPages = Math.ceil(totalCount / pageSize)
    }
    if (totalPages <= 0) {
        totalPages = 1
    }
    return PurchaseDetailListResponse(results = results, total_count = totalCount, total_pages = totalPages, current_page = currentPage, page_size = pageSize)
}
fun mutationBody(data: PurchaseMutationData): UTSJSONObject {
    val body: UTSJSONObject = _uO("purchase_date" to data.purchase_date, "shop" to parseInt(data.shop), "supplier" to parseInt(data.supplier), "remark" to if (data.remark == null) {
        ""
    } else {
        data.remark
    }
    )
    if (data.items.length > 0) {
        body["items"] = data.items
    }
    return body
}
fun updateMutationBody(data: PurchaseMutationData): UTSJSONObject {
    return _uO("purchase_date" to data.purchase_date, "supplier" to parseInt(data.supplier), "remark" to if (data.remark == null) {
        ""
    } else {
        data.remark
    }
    )
}
fun detailMutationBody(data: PurchaseDetailMutationData): UTSJSONObject {
    val body: UTSJSONObject = _uO("purchase" to parseInt(data.purchase), "product" to parseInt(data.product), "quantity" to parseInt(data.quantity), "notes" to if (data.notes == null) {
        ""
    } else {
        data.notes
    }
    )
    if (data.received_quantity != null && data.received_quantity != "") {
        body["received_quantity"] = parseInt(data.received_quantity!!)
    }
    return body
}
fun detailPath__1(id: Any): String {
    return "/api/purchases/purchases/" + stringValue__10(id) + "/"
}
fun purchaseDetailPath(id: Any): String {
    return "/api/purchases/purchase-details/" + stringValue__10(id) + "/"
}
fun getPurchaseList(data: PurchaseListQuery): UTSPromise<PurchaseListResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/purchases/purchases/", "GET", buildQuery(data), true))
            return@w buildListResponse__1(raw, data)
    })
}
fun getPurchaseDetail(id: Any): UTSPromise<PurchaseItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(detailPath__1(id), "GET", _uO(), true))
            return@w buildPurchaseItem(rawDataObject__2(raw))
    })
}
fun getPurchaseDetailList(data: PurchaseDetailListQuery): UTSPromise<PurchaseDetailListResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/purchases/purchase-details/", "GET", buildDetailQuery(data), true))
            return@w buildDetailListResponse(raw, data)
    })
}
fun getPurchaseDetailItem(id: Any): UTSPromise<PurchaseDetailItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(purchaseDetailPath(id), "GET", _uO(), true))
            return@w buildPurchaseDetailItem(rawDetailObject(raw))
    })
}
fun createPurchase(data: PurchaseMutationData): UTSPromise<PurchaseItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/purchases/purchases/", "POST", mutationBody(data), true))
            return@w buildPurchaseItem(rawDataObject__2(raw))
    })
}
fun updatePurchase(id: Any, data: PurchaseMutationData): UTSPromise<PurchaseItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(detailPath__1(id), "PUT", updateMutationBody(data), true))
            return@w buildPurchaseItem(rawDataObject__2(raw))
    })
}
fun createPurchaseDetail(data: PurchaseDetailMutationData): UTSPromise<PurchaseDetailItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/purchases/purchase-details/", "POST", detailMutationBody(data), true))
            return@w buildPurchaseDetailItem(rawDetailObject(raw))
    })
}
fun updatePurchaseDetail(id: Any, data: PurchaseDetailMutationData): UTSPromise<PurchaseDetailItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(purchaseDetailPath(id), "PUT", detailMutationBody(data), true))
            return@w buildPurchaseDetailItem(rawDetailObject(raw))
    })
}
fun deletePurchase(id: Any): UTSPromise<Any> {
    return request(detailPath__1(id), "DELETE", _uO(), true)
}
fun deletePurchaseDetail(id: Any): UTSPromise<Any> {
    return request(purchaseDetailPath(id), "DELETE", _uO(), true)
}
fun runPurchaseAction(id: Any, actionName: String): UTSPromise<Any> {
    val body: UTSJSONObject = _uO("action" to actionName)
    if (actionName == "cancel") {
        body["reason"] = "前端取消采购单"
    }
    return request(detailPath__1(id) + "action_purchase/", "POST", body, true)
}
fun receivePurchaseDetail(id: Any, quantity: Number, notes: String = ""): UTSPromise<Any> {
    return request(purchaseDetailPath(id) + "receive/", "POST", _uO("quantity" to quantity, "notes" to notes), true)
}
fun getPurchaseOptionList(path: String, search: String?, labelField: String, extraLabelField: String = ""): UTSPromise<UTSArray<PurchaseOptionItem>> {
    return wrapUTSPromise(suspend w@{
            val query: UTSJSONObject = _uO("page" to 1, "page_size" to 30)
            if (search != null && search != "") {
                query["search"] = search
            }
            val raw = await(request(path, "GET", query, true))
            val rawObject = rawDataObject__2(raw)
            val rows = parseObjectArray__3(rawObject["results"])
            val result: UTSArray<PurchaseOptionItem> = _uA()
            run {
                var index: Number = 0
                while(index < rows.length){
                    val row = rows[index]
                    var text = stringValue__10(row[labelField])
                    val nameEn = stringValue__10(row["name_en"])
                    val nameOther = stringValue__10(row["name_other"])
                    if (nameEn != "" && text.indexOf(nameEn) < 0) {
                        text = if (text == "") {
                            nameEn
                        } else {
                            text + " / " + nameEn
                        }
                    }
                    if (nameOther != "" && text.indexOf(nameOther) < 0) {
                        text = if (text == "") {
                            nameOther
                        } else {
                            text + " / " + nameOther
                        }
                    }
                    val extra = stringValue__10(row[extraLabelField])
                    if (extra != "") {
                        text = text + " / " + extra
                    }
                    result.push(PurchaseOptionItem(value = stringValue__10(row["id"]), text = text))
                    index += 1
                }
            }
            return@w result
    })
}
val GenPagesPurchasesIndexClass = CreateVueComponent(GenPagesPurchasesIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesPurchasesIndex.inheritAttrs, inject = GenPagesPurchasesIndex.inject, props = GenPagesPurchasesIndex.props, propsNeedCastKeys = GenPagesPurchasesIndex.propsNeedCastKeys, emits = GenPagesPurchasesIndex.emits, components = GenPagesPurchasesIndex.components, styles = GenPagesPurchasesIndex.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesPurchasesIndex.setup(props as GenPagesPurchasesIndex)
    }
    )
}
, fun(instance, renderer): GenPagesPurchasesIndex {
    return GenPagesPurchasesIndex(instance, renderer)
}
)
val GenPagesPurchasesFromClass = CreateVueComponent(GenPagesPurchasesFrom::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesPurchasesFrom.inheritAttrs, inject = GenPagesPurchasesFrom.inject, props = GenPagesPurchasesFrom.props, propsNeedCastKeys = GenPagesPurchasesFrom.propsNeedCastKeys, emits = GenPagesPurchasesFrom.emits, components = GenPagesPurchasesFrom.components, styles = GenPagesPurchasesFrom.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesPurchasesFrom.setup(props as GenPagesPurchasesFrom)
    }
    )
}
, fun(instance, renderer): GenPagesPurchasesFrom {
    return GenPagesPurchasesFrom(instance, renderer)
}
)
val GenPagesPurchasesDetailsIndexClass = CreateVueComponent(GenPagesPurchasesDetailsIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesPurchasesDetailsIndex.inheritAttrs, inject = GenPagesPurchasesDetailsIndex.inject, props = GenPagesPurchasesDetailsIndex.props, propsNeedCastKeys = GenPagesPurchasesDetailsIndex.propsNeedCastKeys, emits = GenPagesPurchasesDetailsIndex.emits, components = GenPagesPurchasesDetailsIndex.components, styles = GenPagesPurchasesDetailsIndex.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesPurchasesDetailsIndex.setup(props as GenPagesPurchasesDetailsIndex)
    }
    )
}
, fun(instance, renderer): GenPagesPurchasesDetailsIndex {
    return GenPagesPurchasesDetailsIndex(instance, renderer)
}
)
val GenPagesPurchasesDetailsFromClass = CreateVueComponent(GenPagesPurchasesDetailsFrom::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesPurchasesDetailsFrom.inheritAttrs, inject = GenPagesPurchasesDetailsFrom.inject, props = GenPagesPurchasesDetailsFrom.props, propsNeedCastKeys = GenPagesPurchasesDetailsFrom.propsNeedCastKeys, emits = GenPagesPurchasesDetailsFrom.emits, components = GenPagesPurchasesDetailsFrom.components, styles = GenPagesPurchasesDetailsFrom.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesPurchasesDetailsFrom.setup(props as GenPagesPurchasesDetailsFrom)
    }
    )
}
, fun(instance, renderer): GenPagesPurchasesDetailsFrom {
    return GenPagesPurchasesDetailsFrom(instance, renderer)
}
)
open class OrderListQuery (
    open var search: String? = null,
    @JsonNotNull
    open var page: Number,
    @JsonNotNull
    open var page_size: Number,
    open var status: String? = null,
    open var payment_method: String? = null,
    open var inventory_deducted: String? = null,
    open var date_from: String? = null,
    open var date_to: String? = null,
) : UTSObject()
open class OrderItem (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var shop: Number,
    @JsonNotNull
    open var shop_name: String,
    @JsonNotNull
    open var order_number: String,
    @JsonNotNull
    open var payload: UTSJSONObject,
    @JsonNotNull
    open var cashier_id: String,
    @JsonNotNull
    open var kasa_number: String,
    @JsonNotNull
    open var payment_method: String,
    @JsonNotNull
    open var payment_method_display: String,
    @JsonNotNull
    open var subtotal: String,
    @JsonNotNull
    open var discount_amount: String,
    @JsonNotNull
    open var tax_amount: String,
    @JsonNotNull
    open var total_amount: String,
    @JsonNotNull
    open var order_time: String,
    @JsonNotNull
    open var status: String,
    @JsonNotNull
    open var status_display: String,
    @JsonNotNull
    open var error_message: String,
    @JsonNotNull
    open var inventory_deducted: Boolean = false,
    @JsonNotNull
    open var inventory_deduct_time: String,
    @JsonNotNull
    open var inventory_deduct_error: String,
    @JsonNotNull
    open var created_at: String,
    @JsonNotNull
    open var updated_at: String,
    @JsonNotNull
    open var item_count: Number,
    @JsonNotNull
    open var quantity_count: Number,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return OrderItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class OrderItemReactiveObject : OrderItem, IUTSReactive<OrderItem> {
    override var __v_raw: OrderItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: OrderItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, shop = __v_raw.shop, shop_name = __v_raw.shop_name, order_number = __v_raw.order_number, payload = __v_raw.payload, cashier_id = __v_raw.cashier_id, kasa_number = __v_raw.kasa_number, payment_method = __v_raw.payment_method, payment_method_display = __v_raw.payment_method_display, subtotal = __v_raw.subtotal, discount_amount = __v_raw.discount_amount, tax_amount = __v_raw.tax_amount, total_amount = __v_raw.total_amount, order_time = __v_raw.order_time, status = __v_raw.status, status_display = __v_raw.status_display, error_message = __v_raw.error_message, inventory_deducted = __v_raw.inventory_deducted, inventory_deduct_time = __v_raw.inventory_deduct_time, inventory_deduct_error = __v_raw.inventory_deduct_error, created_at = __v_raw.created_at, updated_at = __v_raw.updated_at, item_count = __v_raw.item_count, quantity_count = __v_raw.quantity_count) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): OrderItemReactiveObject {
        return OrderItemReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return _tRG(__v_raw, "id", __v_raw.id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            _tRS(__v_raw, "id", oldValue, value)
        }
    override var shop: Number
        get() {
            return _tRG(__v_raw, "shop", __v_raw.shop, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("shop")) {
                return
            }
            val oldValue = __v_raw.shop
            __v_raw.shop = value
            _tRS(__v_raw, "shop", oldValue, value)
        }
    override var shop_name: String
        get() {
            return _tRG(__v_raw, "shop_name", __v_raw.shop_name, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("shop_name")) {
                return
            }
            val oldValue = __v_raw.shop_name
            __v_raw.shop_name = value
            _tRS(__v_raw, "shop_name", oldValue, value)
        }
    override var order_number: String
        get() {
            return _tRG(__v_raw, "order_number", __v_raw.order_number, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("order_number")) {
                return
            }
            val oldValue = __v_raw.order_number
            __v_raw.order_number = value
            _tRS(__v_raw, "order_number", oldValue, value)
        }
    override var payload: UTSJSONObject
        get() {
            return _tRG(__v_raw, "payload", __v_raw.payload, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("payload")) {
                return
            }
            val oldValue = __v_raw.payload
            __v_raw.payload = value
            _tRS(__v_raw, "payload", oldValue, value)
        }
    override var cashier_id: String
        get() {
            return _tRG(__v_raw, "cashier_id", __v_raw.cashier_id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("cashier_id")) {
                return
            }
            val oldValue = __v_raw.cashier_id
            __v_raw.cashier_id = value
            _tRS(__v_raw, "cashier_id", oldValue, value)
        }
    override var kasa_number: String
        get() {
            return _tRG(__v_raw, "kasa_number", __v_raw.kasa_number, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("kasa_number")) {
                return
            }
            val oldValue = __v_raw.kasa_number
            __v_raw.kasa_number = value
            _tRS(__v_raw, "kasa_number", oldValue, value)
        }
    override var payment_method: String
        get() {
            return _tRG(__v_raw, "payment_method", __v_raw.payment_method, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("payment_method")) {
                return
            }
            val oldValue = __v_raw.payment_method
            __v_raw.payment_method = value
            _tRS(__v_raw, "payment_method", oldValue, value)
        }
    override var payment_method_display: String
        get() {
            return _tRG(__v_raw, "payment_method_display", __v_raw.payment_method_display, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("payment_method_display")) {
                return
            }
            val oldValue = __v_raw.payment_method_display
            __v_raw.payment_method_display = value
            _tRS(__v_raw, "payment_method_display", oldValue, value)
        }
    override var subtotal: String
        get() {
            return _tRG(__v_raw, "subtotal", __v_raw.subtotal, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("subtotal")) {
                return
            }
            val oldValue = __v_raw.subtotal
            __v_raw.subtotal = value
            _tRS(__v_raw, "subtotal", oldValue, value)
        }
    override var discount_amount: String
        get() {
            return _tRG(__v_raw, "discount_amount", __v_raw.discount_amount, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("discount_amount")) {
                return
            }
            val oldValue = __v_raw.discount_amount
            __v_raw.discount_amount = value
            _tRS(__v_raw, "discount_amount", oldValue, value)
        }
    override var tax_amount: String
        get() {
            return _tRG(__v_raw, "tax_amount", __v_raw.tax_amount, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("tax_amount")) {
                return
            }
            val oldValue = __v_raw.tax_amount
            __v_raw.tax_amount = value
            _tRS(__v_raw, "tax_amount", oldValue, value)
        }
    override var total_amount: String
        get() {
            return _tRG(__v_raw, "total_amount", __v_raw.total_amount, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("total_amount")) {
                return
            }
            val oldValue = __v_raw.total_amount
            __v_raw.total_amount = value
            _tRS(__v_raw, "total_amount", oldValue, value)
        }
    override var order_time: String
        get() {
            return _tRG(__v_raw, "order_time", __v_raw.order_time, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("order_time")) {
                return
            }
            val oldValue = __v_raw.order_time
            __v_raw.order_time = value
            _tRS(__v_raw, "order_time", oldValue, value)
        }
    override var status: String
        get() {
            return _tRG(__v_raw, "status", __v_raw.status, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("status")) {
                return
            }
            val oldValue = __v_raw.status
            __v_raw.status = value
            _tRS(__v_raw, "status", oldValue, value)
        }
    override var status_display: String
        get() {
            return _tRG(__v_raw, "status_display", __v_raw.status_display, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("status_display")) {
                return
            }
            val oldValue = __v_raw.status_display
            __v_raw.status_display = value
            _tRS(__v_raw, "status_display", oldValue, value)
        }
    override var error_message: String
        get() {
            return _tRG(__v_raw, "error_message", __v_raw.error_message, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("error_message")) {
                return
            }
            val oldValue = __v_raw.error_message
            __v_raw.error_message = value
            _tRS(__v_raw, "error_message", oldValue, value)
        }
    override var inventory_deducted: Boolean
        get() {
            return _tRG(__v_raw, "inventory_deducted", __v_raw.inventory_deducted, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("inventory_deducted")) {
                return
            }
            val oldValue = __v_raw.inventory_deducted
            __v_raw.inventory_deducted = value
            _tRS(__v_raw, "inventory_deducted", oldValue, value)
        }
    override var inventory_deduct_time: String
        get() {
            return _tRG(__v_raw, "inventory_deduct_time", __v_raw.inventory_deduct_time, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("inventory_deduct_time")) {
                return
            }
            val oldValue = __v_raw.inventory_deduct_time
            __v_raw.inventory_deduct_time = value
            _tRS(__v_raw, "inventory_deduct_time", oldValue, value)
        }
    override var inventory_deduct_error: String
        get() {
            return _tRG(__v_raw, "inventory_deduct_error", __v_raw.inventory_deduct_error, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("inventory_deduct_error")) {
                return
            }
            val oldValue = __v_raw.inventory_deduct_error
            __v_raw.inventory_deduct_error = value
            _tRS(__v_raw, "inventory_deduct_error", oldValue, value)
        }
    override var created_at: String
        get() {
            return _tRG(__v_raw, "created_at", __v_raw.created_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("created_at")) {
                return
            }
            val oldValue = __v_raw.created_at
            __v_raw.created_at = value
            _tRS(__v_raw, "created_at", oldValue, value)
        }
    override var updated_at: String
        get() {
            return _tRG(__v_raw, "updated_at", __v_raw.updated_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("updated_at")) {
                return
            }
            val oldValue = __v_raw.updated_at
            __v_raw.updated_at = value
            _tRS(__v_raw, "updated_at", oldValue, value)
        }
    override var item_count: Number
        get() {
            return _tRG(__v_raw, "item_count", __v_raw.item_count, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("item_count")) {
                return
            }
            val oldValue = __v_raw.item_count
            __v_raw.item_count = value
            _tRS(__v_raw, "item_count", oldValue, value)
        }
    override var quantity_count: Number
        get() {
            return _tRG(__v_raw, "quantity_count", __v_raw.quantity_count, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("quantity_count")) {
                return
            }
            val oldValue = __v_raw.quantity_count
            __v_raw.quantity_count = value
            _tRS(__v_raw, "quantity_count", oldValue, value)
        }
}
open class OrderListResponse (
    @JsonNotNull
    open var results: UTSArray<OrderItem>,
    @JsonNotNull
    open var total_count: Number,
    @JsonNotNull
    open var total_pages: Number,
    @JsonNotNull
    open var current_page: Number,
    @JsonNotNull
    open var page_size: Number,
) : UTSObject()
open class OrderStatistics (
    @JsonNotNull
    open var total_count: Number,
    @JsonNotNull
    open var inventory_deducted_count: Number,
    @JsonNotNull
    open var inventory_pending_count: Number,
    @JsonNotNull
    open var received_count: Number,
    @JsonNotNull
    open var processed_count: Number,
    @JsonNotNull
    open var failed_count: Number,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return OrderStatisticsReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class OrderStatisticsReactiveObject : OrderStatistics, IUTSReactive<OrderStatistics> {
    override var __v_raw: OrderStatistics
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: OrderStatistics, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(total_count = __v_raw.total_count, inventory_deducted_count = __v_raw.inventory_deducted_count, inventory_pending_count = __v_raw.inventory_pending_count, received_count = __v_raw.received_count, processed_count = __v_raw.processed_count, failed_count = __v_raw.failed_count) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): OrderStatisticsReactiveObject {
        return OrderStatisticsReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var total_count: Number
        get() {
            return _tRG(__v_raw, "total_count", __v_raw.total_count, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("total_count")) {
                return
            }
            val oldValue = __v_raw.total_count
            __v_raw.total_count = value
            _tRS(__v_raw, "total_count", oldValue, value)
        }
    override var inventory_deducted_count: Number
        get() {
            return _tRG(__v_raw, "inventory_deducted_count", __v_raw.inventory_deducted_count, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("inventory_deducted_count")) {
                return
            }
            val oldValue = __v_raw.inventory_deducted_count
            __v_raw.inventory_deducted_count = value
            _tRS(__v_raw, "inventory_deducted_count", oldValue, value)
        }
    override var inventory_pending_count: Number
        get() {
            return _tRG(__v_raw, "inventory_pending_count", __v_raw.inventory_pending_count, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("inventory_pending_count")) {
                return
            }
            val oldValue = __v_raw.inventory_pending_count
            __v_raw.inventory_pending_count = value
            _tRS(__v_raw, "inventory_pending_count", oldValue, value)
        }
    override var received_count: Number
        get() {
            return _tRG(__v_raw, "received_count", __v_raw.received_count, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("received_count")) {
                return
            }
            val oldValue = __v_raw.received_count
            __v_raw.received_count = value
            _tRS(__v_raw, "received_count", oldValue, value)
        }
    override var processed_count: Number
        get() {
            return _tRG(__v_raw, "processed_count", __v_raw.processed_count, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("processed_count")) {
                return
            }
            val oldValue = __v_raw.processed_count
            __v_raw.processed_count = value
            _tRS(__v_raw, "processed_count", oldValue, value)
        }
    override var failed_count: Number
        get() {
            return _tRG(__v_raw, "failed_count", __v_raw.failed_count, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("failed_count")) {
                return
            }
            val oldValue = __v_raw.failed_count
            __v_raw.failed_count = value
            _tRS(__v_raw, "failed_count", oldValue, value)
        }
}
fun stringValue__11(value: Any?): String {
    if (value == null) {
        return ""
    }
    return "" + value
}
fun intValue__10(value: Any?): Number {
    val parsed = parseInt(stringValue__11(value))
    if (isNaN(parsed)) {
        return 0
    }
    return parsed
}
fun boolValue__4(value: Any?): Boolean {
    val text = stringValue__11(value).toLowerCase()
    return text == "true" || text == "1" || text == "yes"
}
fun parseObject__5(value: Any?): UTSJSONObject? {
    if (value == null) {
        return null
    }
    val text = JSON.stringify(value)
    if (text == null || text == "") {
        return null
    }
    return JSON.parseObject<UTSJSONObject>(text)
}
fun parseObjectArray__4(value: Any?): UTSArray<UTSJSONObject> {
    if (value == null) {
        return _uA<UTSJSONObject>()
    }
    val text = JSON.stringify(value)
    if (text == null || text == "") {
        return _uA<UTSJSONObject>()
    }
    val parsed = JSON.parseArray<UTSJSONObject>(text)
    if (parsed == null) {
        return _uA<UTSJSONObject>()
    }
    return parsed!!
}
fun rawDataObject__3(raw: Any): UTSJSONObject {
    val rawObject = parseObject__5(raw)
    if (rawObject == null) {
        throw UTSError("订单接口响应解析失败")
    }
    val dataObject = parseObject__5(rawObject["data"])
    if (dataObject != null) {
        return dataObject!!
    }
    return rawObject!!
}
fun buildQuery__1(data: OrderListQuery): UTSJSONObject {
    val query: UTSJSONObject = _uO("page" to data.page, "page_size" to data.page_size)
    if (data.search != null && data.search != "") {
        query["search"] = data.search
    }
    if (data.status != null && data.status != "") {
        query["status"] = data.status
    }
    if (data.payment_method != null && data.payment_method != "") {
        query["payment_method"] = data.payment_method
    }
    if (data.inventory_deducted != null && data.inventory_deducted != "") {
        query["inventory_deducted"] = data.inventory_deducted
    }
    if (data.date_from != null && data.date_from != "") {
        query["date_from"] = data.date_from
    }
    if (data.date_to != null && data.date_to != "") {
        query["date_to"] = data.date_to
    }
    return query
}
fun getPayloadItems(payload: UTSJSONObject): UTSArray<UTSJSONObject> {
    var rows = parseObjectArray__4(payload["items"])
    if (rows.length > 0) {
        return rows
    }
    rows = parseObjectArray__4(payload["products"])
    if (rows.length > 0) {
        return rows
    }
    rows = parseObjectArray__4(payload["cart"])
    return rows
}
fun getPayloadQuantity(items: UTSArray<UTSJSONObject>): Number {
    var total: Number = 0
    run {
        var index: Number = 0
        while(index < items.length){
            val quantity = intValue__10(items[index]["quantity"])
            if (quantity > 0) {
                total = total + quantity
            }
            index += 1
        }
    }
    return total
}
fun buildOrderItem(rawObject: UTSJSONObject): OrderItem {
    val payload = parseObject__5(rawObject["payload"])
    val payloadObject = if (payload == null) {
        (_uO())
    } else {
        payload!!
    }
    val payloadItems = getPayloadItems(payloadObject)
    return OrderItem(id = intValue__10(rawObject["id"]), shop = intValue__10(rawObject["shop"]), shop_name = stringValue__11(rawObject["shop_name"]), order_number = stringValue__11(rawObject["order_number"]), payload = payloadObject, cashier_id = stringValue__11(rawObject["cashier_id"]), kasa_number = stringValue__11(rawObject["kasa_number"]), payment_method = stringValue__11(rawObject["payment_method"]), payment_method_display = stringValue__11(rawObject["payment_method_display"]), subtotal = stringValue__11(rawObject["subtotal"]), discount_amount = stringValue__11(rawObject["discount_amount"]), tax_amount = stringValue__11(rawObject["tax_amount"]), total_amount = stringValue__11(rawObject["total_amount"]), order_time = stringValue__11(rawObject["order_time"]), status = stringValue__11(rawObject["status"]), status_display = stringValue__11(rawObject["status_display"]), error_message = stringValue__11(rawObject["error_message"]), inventory_deducted = boolValue__4(rawObject["inventory_deducted"]), inventory_deduct_time = stringValue__11(rawObject["inventory_deduct_time"]), inventory_deduct_error = stringValue__11(rawObject["inventory_deduct_error"]), created_at = stringValue__11(rawObject["created_at"]), updated_at = stringValue__11(rawObject["updated_at"]), item_count = payloadItems.length, quantity_count = getPayloadQuantity(payloadItems))
}
fun buildItems__1(value: Any?): UTSArray<OrderItem> {
    val rawArray = parseObjectArray__4(value)
    val result: UTSArray<OrderItem> = _uA()
    run {
        var index: Number = 0
        while(index < rawArray.length){
            result.push(buildOrderItem(rawArray[index]))
            index += 1
        }
    }
    return result
}
fun buildListResponse__2(raw: Any, query: OrderListQuery): OrderListResponse {
    val rawObject = rawDataObject__3(raw)
    var paginationObject: UTSJSONObject? = null
    val rawPagination = rawObject["pagination"]
    if (rawPagination != null) {
        paginationObject = parseObject__5(rawPagination)
    }
    val results = buildItems__1(rawObject["results"])
    var totalCount = intValue__10(rawObject["count"])
    if (totalCount <= 0) {
        totalCount = intValue__10(rawObject["total_count"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__10(paginationObject!!["total"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__10(paginationObject!!["count"])
    }
    if (totalCount <= 0) {
        totalCount = results.length
    }
    var currentPage = intValue__10(rawObject["current_page"])
    if (currentPage <= 0) {
        currentPage = intValue__10(rawObject["page"])
    }
    if (currentPage <= 0 && paginationObject != null) {
        currentPage = intValue__10(paginationObject!!["page"])
    }
    if (currentPage <= 0) {
        currentPage = query.page
    }
    var pageSize = intValue__10(rawObject["page_size"])
    if (pageSize <= 0 && paginationObject != null) {
        pageSize = intValue__10(paginationObject!!["page_size"])
    }
    if (pageSize <= 0) {
        pageSize = query.page_size
    }
    var totalPages = intValue__10(rawObject["total_pages"])
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__10(paginationObject!!["total_pages"])
    }
    if (totalPages <= 0 && pageSize > 0) {
        totalPages = Math.ceil(totalCount / pageSize)
    }
    if (totalPages <= 0) {
        totalPages = 1
    }
    return OrderListResponse(results = results, total_count = totalCount, total_pages = totalPages, current_page = currentPage, page_size = pageSize)
}
fun buildStatistics(raw: Any): OrderStatistics {
    val rawObject = rawDataObject__3(raw)
    val statusDistribution = parseObject__5(rawObject["status_distribution"])
    return OrderStatistics(total_count = intValue__10(rawObject["total_count"]), inventory_deducted_count = intValue__10(rawObject["inventory_deducted_count"]), inventory_pending_count = intValue__10(rawObject["inventory_pending_count"]), received_count = if (statusDistribution == null) {
        0
    } else {
        intValue__10(statusDistribution!!["已接收"])
    }
    , processed_count = if (statusDistribution == null) {
        0
    } else {
        intValue__10(statusDistribution!!["已处理"])
    }
    , failed_count = if (statusDistribution == null) {
        0
    } else {
        intValue__10(statusDistribution!!["处理失败"])
    }
    )
}
fun detailPath__2(id: Any): String {
    return "/api/orders/orders/" + stringValue__11(id) + "/"
}
fun getOrderList(data: OrderListQuery): UTSPromise<OrderListResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/orders/orders/", "GET", buildQuery__1(data), true))
            return@w buildListResponse__2(raw, data)
    })
}
fun getOrderDetail(id: Any): UTSPromise<OrderItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(detailPath__2(id), "GET", _uO(), true))
            return@w buildOrderItem(rawDataObject__3(raw))
    })
}
fun getOrderStatistics(data: OrderListQuery): UTSPromise<OrderStatistics> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/orders/orders/statistics/", "GET", buildQuery__1(data), true))
            return@w buildStatistics(raw)
    })
}
open class FilterItem (
    @JsonNotNull
    open var key: String,
    @JsonNotNull
    open var text: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return FilterItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class FilterItemReactiveObject : FilterItem, IUTSReactive<FilterItem> {
    override var __v_raw: FilterItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: FilterItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(key = __v_raw.key, text = __v_raw.text) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): FilterItemReactiveObject {
        return FilterItemReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var key: String
        get() {
            return _tRG(__v_raw, "key", __v_raw.key, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("key")) {
                return
            }
            val oldValue = __v_raw.key
            __v_raw.key = value
            _tRS(__v_raw, "key", oldValue, value)
        }
    override var text: String
        get() {
            return _tRG(__v_raw, "text", __v_raw.text, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("text")) {
                return
            }
            val oldValue = __v_raw.text
            __v_raw.text = value
            _tRS(__v_raw, "text", oldValue, value)
        }
}
val GenPagesOrdersIndexClass = CreateVueComponent(GenPagesOrdersIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesOrdersIndex.inheritAttrs, inject = GenPagesOrdersIndex.inject, props = GenPagesOrdersIndex.props, propsNeedCastKeys = GenPagesOrdersIndex.propsNeedCastKeys, emits = GenPagesOrdersIndex.emits, components = GenPagesOrdersIndex.components, styles = GenPagesOrdersIndex.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesOrdersIndex.setup(props as GenPagesOrdersIndex)
    }
    )
}
, fun(instance, renderer): GenPagesOrdersIndex {
    return GenPagesOrdersIndex(instance, renderer)
}
)
open class PayloadRow (
    @JsonNotNull
    open var key: String,
    @JsonNotNull
    open var title: String,
    @JsonNotNull
    open var desc: String,
    @JsonNotNull
    open var quantity: String,
) : UTSObject()
val GenPagesOrdersFromClass = CreateVueComponent(GenPagesOrdersFrom::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesOrdersFrom.inheritAttrs, inject = GenPagesOrdersFrom.inject, props = GenPagesOrdersFrom.props, propsNeedCastKeys = GenPagesOrdersFrom.propsNeedCastKeys, emits = GenPagesOrdersFrom.emits, components = GenPagesOrdersFrom.components, styles = GenPagesOrdersFrom.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesOrdersFrom.setup(props as GenPagesOrdersFrom)
    }
    )
}
, fun(instance, renderer): GenPagesOrdersFrom {
    return GenPagesOrdersFrom(instance, renderer)
}
)
open class InventoryListQuery (
    open var search: String? = null,
    @JsonNotNull
    open var page: Number,
    @JsonNotNull
    open var page_size: Number,
    open var status: String? = null,
    open var alert_status: String? = null,
    open var transaction_type: String? = null,
    open var location_type: String? = null,
    open var is_active: String? = null,
) : UTSObject()
open class InventoryListResponse (
    @JsonNotNull
    open var results: UTSArray<UTSJSONObject>,
    @JsonNotNull
    open var total_count: Number,
    @JsonNotNull
    open var total_pages: Number,
    @JsonNotNull
    open var current_page: Number,
    @JsonNotNull
    open var page_size: Number,
) : UTSObject()
open class StockAdjustmentData (
    @JsonNotNull
    open var stock_id: Number,
    @JsonNotNull
    open var quantity_change: Number,
    @JsonNotNull
    open var transaction_type: String,
    @JsonNotNull
    open var unit_cost: String,
    @JsonNotNull
    open var notes: String,
) : UTSObject()
open class InventoryMutationData (
    @JsonNotNull
    open var payload: UTSJSONObject,
) : UTSObject()
fun stringValue__12(value: Any?): String {
    if (value == null) {
        return ""
    }
    return "" + value
}
fun intValue__11(value: Any?): Number {
    if (value == null) {
        return 0
    }
    val parsed = parseInt("" + value)
    if (isNaN(parsed)) {
        return 0
    }
    return parsed
}
fun buildQuery__2(data: InventoryListQuery): UTSJSONObject {
    val query: UTSJSONObject = _uO("page" to data.page, "page_size" to data.page_size)
    if (data.search != null && data.search != "") {
        query["search"] = data.search
    }
    if (data.status != null && data.status != "") {
        query["status"] = data.status
    }
    if (data.alert_status != null && data.alert_status != "") {
        query["alert_status"] = data.alert_status
    }
    if (data.transaction_type != null && data.transaction_type != "") {
        query["transaction_type"] = data.transaction_type
    }
    if (data.location_type != null && data.location_type != "") {
        query["location_type"] = data.location_type
    }
    if (data.is_active != null && data.is_active != "") {
        query["is_active"] = data.is_active
    }
    return query
}
fun parseObjectArray__5(value: Any?): UTSArray<UTSJSONObject> {
    if (value == null) {
        return _uA<UTSJSONObject>()
    }
    val text = JSON.stringify(value)
    val parsed = if (text == null || text == "") {
        null
    } else {
        JSON.parseArray<UTSJSONObject>(text)
    }
    if (parsed == null) {
        return _uA<UTSJSONObject>()
    }
    return parsed!!
}
fun parseObject__6(value: Any?): UTSJSONObject? {
    if (value == null) {
        return null
    }
    val text = JSON.stringify(value)
    if (text == null || text == "") {
        return null
    }
    return JSON.parseObject<UTSJSONObject>(text)
}
fun buildListResponse__3(raw: Any, query: InventoryListQuery): InventoryListResponse {
    val rawObject = parseObject__6(raw)
    if (rawObject == null) {
        throw UTSError("库存列表响应解析失败")
    }
    var paginationObject: UTSJSONObject? = null
    val rawPagination = rawObject!!["pagination"]
    if (rawPagination != null) {
        paginationObject = parseObject__6(rawPagination)
    }
    val results = parseObjectArray__5(rawObject!!["results"])
    var totalCount = intValue__11(rawObject!!["count"])
    if (totalCount <= 0) {
        totalCount = intValue__11(rawObject!!["total_count"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__11(paginationObject!!["total"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__11(paginationObject!!["count"])
    }
    if (totalCount <= 0) {
        totalCount = results.length
    }
    var currentPage = intValue__11(rawObject!!["current_page"])
    if (currentPage <= 0) {
        currentPage = intValue__11(rawObject!!["page"])
    }
    if (currentPage <= 0 && paginationObject != null) {
        currentPage = intValue__11(paginationObject!!["page"])
    }
    if (currentPage <= 0) {
        currentPage = query.page
    }
    var pageSize = intValue__11(rawObject!!["page_size"])
    if (pageSize <= 0 && paginationObject != null) {
        pageSize = intValue__11(paginationObject!!["page_size"])
    }
    if (pageSize <= 0) {
        pageSize = query.page_size
    }
    var totalPages = intValue__11(rawObject!!["total_pages"])
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__11(paginationObject!!["total_pages"])
    }
    if (totalPages <= 0 && pageSize > 0) {
        totalPages = Math.ceil(totalCount / pageSize)
    }
    if (totalPages <= 0) {
        totalPages = 1
    }
    return InventoryListResponse(results = results, total_count = totalCount, total_pages = totalPages, current_page = currentPage, page_size = pageSize)
}
fun stockAdjustmentBody(data: StockAdjustmentData): UTSJSONObject {
    return _uO("stock_id" to data.stock_id, "quantity_change" to data.quantity_change, "transaction_type" to data.transaction_type, "unit_cost" to data.unit_cost, "notes" to data.notes, "reference_type" to "", "reference_id" to "")
}
fun getInventoryStocks(data: InventoryListQuery): UTSPromise<InventoryListResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/inventory/stocks/", "GET", buildQuery__2(data), true))
            return@w buildListResponse__3(raw, data)
    })
}
fun getInventoryLocations(data: InventoryListQuery): UTSPromise<InventoryListResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/inventory/locations/", "GET", buildQuery__2(data), true))
            return@w buildListResponse__3(raw, data)
    })
}
fun getInventoryTransfers(data: InventoryListQuery): UTSPromise<InventoryListResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/inventory/transfer-orders/", "GET", buildQuery__2(data), true))
            return@w buildListResponse__3(raw, data)
    })
}
fun getInventoryChecks(data: InventoryListQuery): UTSPromise<InventoryListResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/inventory/checks/", "GET", buildQuery__2(data), true))
            return@w buildListResponse__3(raw, data)
    })
}
fun getInventoryStockDetail(id: String): UTSPromise<UTSJSONObject> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/inventory/stocks/" + stringValue__12(id) + "/", "GET", _uO(), true))
            val parsed = parseObject__6(raw)
            if (parsed == null) {
                throw UTSError("库存详情响应解析失败")
            }
            return@w parsed!!
    })
}
fun getInventoryLocationDetail(id: String): UTSPromise<UTSJSONObject> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/inventory/locations/" + stringValue__12(id) + "/", "GET", _uO(), true))
            val parsed = parseObject__6(raw)
            if (parsed == null) {
                throw UTSError("库存位置详情响应解析失败")
            }
            return@w parsed!!
    })
}
fun getInventoryTransferDetail(id: String): UTSPromise<UTSJSONObject> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/inventory/transfer-orders/" + stringValue__12(id) + "/", "GET", _uO(), true))
            val parsed = parseObject__6(raw)
            if (parsed == null) {
                throw UTSError("调拨单详情响应解析失败")
            }
            return@w parsed!!
    })
}
fun getInventoryCheckDetail(id: String): UTSPromise<UTSJSONObject> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/inventory/checks/" + stringValue__12(id) + "/", "GET", _uO(), true))
            val parsed = parseObject__6(raw)
            if (parsed == null) {
                throw UTSError("盘点单详情响应解析失败")
            }
            return@w parsed!!
    })
}
fun createInventoryLocation(data: InventoryMutationData): UTSPromise<Any> {
    return request("/api/inventory/locations/", "POST", data.payload, true)
}
fun updateInventoryLocation(id: String, data: InventoryMutationData): UTSPromise<Any> {
    return request("/api/inventory/locations/" + stringValue__12(id) + "/", "PUT", data.payload, true)
}
fun createInventoryTransfer(data: InventoryMutationData): UTSPromise<Any> {
    return request("/api/inventory/transfer-orders/", "POST", data.payload, true)
}
fun updateInventoryTransfer(id: String, data: InventoryMutationData): UTSPromise<Any> {
    return request("/api/inventory/transfer-orders/" + stringValue__12(id) + "/", "PUT", data.payload, true)
}
fun createInventoryCheck(data: InventoryMutationData): UTSPromise<Any> {
    return request("/api/inventory/checks/", "POST", data.payload, true)
}
fun updateInventoryCheck(id: String, data: InventoryMutationData): UTSPromise<Any> {
    return request("/api/inventory/checks/" + stringValue__12(id) + "/", "PUT", data.payload, true)
}
fun adjustInventoryStock(data: StockAdjustmentData): UTSPromise<Any> {
    return request("/api/inventory/stocks/adjust/", "POST", stockAdjustmentBody(data), true)
}
fun approveInventoryTransfer(id: String): UTSPromise<Any> {
    return request("/api/inventory/transfer-orders/" + stringValue__12(id) + "/approve/", "POST", _uO(), true)
}
fun completeInventoryTransfer(id: String): UTSPromise<Any> {
    return request("/api/inventory/transfer-orders/" + stringValue__12(id) + "/complete/", "POST", _uO(), true)
}
fun cancelInventoryTransfer(id: String): UTSPromise<Any> {
    return request("/api/inventory/transfer-orders/" + stringValue__12(id) + "/cancel/", "POST", _uO(), true)
}
fun startInventoryCheck(id: String): UTSPromise<Any> {
    return request("/api/inventory/checks/" + stringValue__12(id) + "/start/", "POST", _uO(), true)
}
fun submitInventoryCheck(id: String): UTSPromise<Any> {
    return request("/api/inventory/checks/" + stringValue__12(id) + "/submit/", "POST", _uO(), true)
}
fun approveInventoryCheck(id: String): UTSPromise<Any> {
    return request("/api/inventory/checks/" + stringValue__12(id) + "/approve/", "POST", _uO(), true)
}
fun adjustInventoryCheck(id: String): UTSPromise<Any> {
    return request("/api/inventory/checks/" + stringValue__12(id) + "/adjust/", "POST", _uO(), true)
}
val GenPagesInventoryManagementIndexClass = CreateVueComponent(GenPagesInventoryManagementIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesInventoryManagementIndex.inheritAttrs, inject = GenPagesInventoryManagementIndex.inject, props = GenPagesInventoryManagementIndex.props, propsNeedCastKeys = GenPagesInventoryManagementIndex.propsNeedCastKeys, emits = GenPagesInventoryManagementIndex.emits, components = GenPagesInventoryManagementIndex.components, styles = GenPagesInventoryManagementIndex.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesInventoryManagementIndex.setup(props as GenPagesInventoryManagementIndex)
    }
    )
}
, fun(instance, renderer): GenPagesInventoryManagementIndex {
    return GenPagesInventoryManagementIndex(instance, renderer)
}
)
open class SelectOption__8 (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var text: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return SelectOption__8ReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class SelectOption__8ReactiveObject : SelectOption__8, IUTSReactive<SelectOption__8> {
    override var __v_raw: SelectOption__8
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: SelectOption__8, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(value = __v_raw.value, text = __v_raw.text) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): SelectOption__8ReactiveObject {
        return SelectOption__8ReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var value: String
        get() {
            return _tRG(__v_raw, "value", __v_raw.value, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("value")) {
                return
            }
            val oldValue = __v_raw.value
            __v_raw.value = value
            _tRS(__v_raw, "value", oldValue, value)
        }
    override var text: String
        get() {
            return _tRG(__v_raw, "text", __v_raw.text, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("text")) {
                return
            }
            val oldValue = __v_raw.text
            __v_raw.text = value
            _tRS(__v_raw, "text", oldValue, value)
        }
}
val GenPagesInventoryManagementFromClass = CreateVueComponent(GenPagesInventoryManagementFrom::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesInventoryManagementFrom.inheritAttrs, inject = GenPagesInventoryManagementFrom.inject, props = GenPagesInventoryManagementFrom.props, propsNeedCastKeys = GenPagesInventoryManagementFrom.propsNeedCastKeys, emits = GenPagesInventoryManagementFrom.emits, components = GenPagesInventoryManagementFrom.components, styles = GenPagesInventoryManagementFrom.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesInventoryManagementFrom.setup(props as GenPagesInventoryManagementFrom)
    }
    )
}
, fun(instance, renderer): GenPagesInventoryManagementFrom {
    return GenPagesInventoryManagementFrom(instance, renderer)
}
)
val GenPagesInventoryLocationsIndexClass = CreateVueComponent(GenPagesInventoryLocationsIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesInventoryLocationsIndex.inheritAttrs, inject = GenPagesInventoryLocationsIndex.inject, props = GenPagesInventoryLocationsIndex.props, propsNeedCastKeys = GenPagesInventoryLocationsIndex.propsNeedCastKeys, emits = GenPagesInventoryLocationsIndex.emits, components = GenPagesInventoryLocationsIndex.components, styles = GenPagesInventoryLocationsIndex.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesInventoryLocationsIndex.setup(props as GenPagesInventoryLocationsIndex)
    }
    )
}
, fun(instance, renderer): GenPagesInventoryLocationsIndex {
    return GenPagesInventoryLocationsIndex(instance, renderer)
}
)
open class SelectOption__9 (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var text: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return SelectOption__9ReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class SelectOption__9ReactiveObject : SelectOption__9, IUTSReactive<SelectOption__9> {
    override var __v_raw: SelectOption__9
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: SelectOption__9, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(value = __v_raw.value, text = __v_raw.text) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): SelectOption__9ReactiveObject {
        return SelectOption__9ReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var value: String
        get() {
            return _tRG(__v_raw, "value", __v_raw.value, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("value")) {
                return
            }
            val oldValue = __v_raw.value
            __v_raw.value = value
            _tRS(__v_raw, "value", oldValue, value)
        }
    override var text: String
        get() {
            return _tRG(__v_raw, "text", __v_raw.text, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("text")) {
                return
            }
            val oldValue = __v_raw.text
            __v_raw.text = value
            _tRS(__v_raw, "text", oldValue, value)
        }
}
val GenPagesInventoryLocationsFromClass = CreateVueComponent(GenPagesInventoryLocationsFrom::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesInventoryLocationsFrom.inheritAttrs, inject = GenPagesInventoryLocationsFrom.inject, props = GenPagesInventoryLocationsFrom.props, propsNeedCastKeys = GenPagesInventoryLocationsFrom.propsNeedCastKeys, emits = GenPagesInventoryLocationsFrom.emits, components = GenPagesInventoryLocationsFrom.components, styles = GenPagesInventoryLocationsFrom.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesInventoryLocationsFrom.setup(props as GenPagesInventoryLocationsFrom)
    }
    )
}
, fun(instance, renderer): GenPagesInventoryLocationsFrom {
    return GenPagesInventoryLocationsFrom(instance, renderer)
}
)
val GenPagesInventoryTransfersIndexClass = CreateVueComponent(GenPagesInventoryTransfersIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesInventoryTransfersIndex.inheritAttrs, inject = GenPagesInventoryTransfersIndex.inject, props = GenPagesInventoryTransfersIndex.props, propsNeedCastKeys = GenPagesInventoryTransfersIndex.propsNeedCastKeys, emits = GenPagesInventoryTransfersIndex.emits, components = GenPagesInventoryTransfersIndex.components, styles = GenPagesInventoryTransfersIndex.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesInventoryTransfersIndex.setup(props as GenPagesInventoryTransfersIndex)
    }
    )
}
, fun(instance, renderer): GenPagesInventoryTransfersIndex {
    return GenPagesInventoryTransfersIndex(instance, renderer)
}
)
val GenPagesInventoryTransfersFromClass = CreateVueComponent(GenPagesInventoryTransfersFrom::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesInventoryTransfersFrom.inheritAttrs, inject = GenPagesInventoryTransfersFrom.inject, props = GenPagesInventoryTransfersFrom.props, propsNeedCastKeys = GenPagesInventoryTransfersFrom.propsNeedCastKeys, emits = GenPagesInventoryTransfersFrom.emits, components = GenPagesInventoryTransfersFrom.components, styles = GenPagesInventoryTransfersFrom.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesInventoryTransfersFrom.setup(props as GenPagesInventoryTransfersFrom)
    }
    )
}
, fun(instance, renderer): GenPagesInventoryTransfersFrom {
    return GenPagesInventoryTransfersFrom(instance, renderer)
}
)
val GenPagesInventoryChecksIndexClass = CreateVueComponent(GenPagesInventoryChecksIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesInventoryChecksIndex.inheritAttrs, inject = GenPagesInventoryChecksIndex.inject, props = GenPagesInventoryChecksIndex.props, propsNeedCastKeys = GenPagesInventoryChecksIndex.propsNeedCastKeys, emits = GenPagesInventoryChecksIndex.emits, components = GenPagesInventoryChecksIndex.components, styles = GenPagesInventoryChecksIndex.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesInventoryChecksIndex.setup(props as GenPagesInventoryChecksIndex)
    }
    )
}
, fun(instance, renderer): GenPagesInventoryChecksIndex {
    return GenPagesInventoryChecksIndex(instance, renderer)
}
)
open class SelectOption__10 (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var text: String,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return SelectOption__10ReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class SelectOption__10ReactiveObject : SelectOption__10, IUTSReactive<SelectOption__10> {
    override var __v_raw: SelectOption__10
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: SelectOption__10, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(value = __v_raw.value, text = __v_raw.text) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): SelectOption__10ReactiveObject {
        return SelectOption__10ReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var value: String
        get() {
            return _tRG(__v_raw, "value", __v_raw.value, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("value")) {
                return
            }
            val oldValue = __v_raw.value
            __v_raw.value = value
            _tRS(__v_raw, "value", oldValue, value)
        }
    override var text: String
        get() {
            return _tRG(__v_raw, "text", __v_raw.text, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("text")) {
                return
            }
            val oldValue = __v_raw.text
            __v_raw.text = value
            _tRS(__v_raw, "text", oldValue, value)
        }
}
val GenPagesInventoryChecksFromClass = CreateVueComponent(GenPagesInventoryChecksFrom::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesInventoryChecksFrom.inheritAttrs, inject = GenPagesInventoryChecksFrom.inject, props = GenPagesInventoryChecksFrom.props, propsNeedCastKeys = GenPagesInventoryChecksFrom.propsNeedCastKeys, emits = GenPagesInventoryChecksFrom.emits, components = GenPagesInventoryChecksFrom.components, styles = GenPagesInventoryChecksFrom.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesInventoryChecksFrom.setup(props as GenPagesInventoryChecksFrom)
    }
    )
}
, fun(instance, renderer): GenPagesInventoryChecksFrom {
    return GenPagesInventoryChecksFrom(instance, renderer)
}
)
fun createApp(): UTSJSONObject {
    val app = createSSRApp(GenAppClass)
    return _uO("app" to app)
}
fun main(app: IApp) {
    definePageRoutes()
    defineAppConfig()
    (createApp()["app"] as VueApp).mount(app, GenUniApp())
}
open class UniAppConfig : io.dcloud.uniapp.appframe.AppConfig {
    override var name: String = "LILI_POS"
    override var appid: String = "__UNI__1CE1B14"
    override var versionName: String = "1.0.0"
    override var versionCode: String = "100"
    override var uniCompilerVersion: String = "5.07"
    constructor() : super() {}
}
fun definePageRoutes() {
    __uniRoutes.push(UniPageRoute(path = "pages/login/login", component = GenPagesLoginLoginClass, meta = UniPageMeta(isQuit = true), style = _uM("navigationStyle" to "custom")))
    __uniRoutes.push(UniPageRoute(path = "pages/tabbar/reports", component = GenPagesTabbarReportsClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "报表")))
    __uniRoutes.push(UniPageRoute(path = "pages/tabbar/products", component = GenPagesTabbarProductsClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "商品")))
    __uniRoutes.push(UniPageRoute(path = "pages/tabbar/settings", component = GenPagesTabbarSettingsClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "设置")))
    __uniRoutes.push(UniPageRoute(path = "pages/tabbar/mine", component = GenPagesTabbarMineClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "我的")))
    __uniRoutes.push(UniPageRoute(path = "pages/test/scan", component = GenPagesTestScanClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "扫码测试")))
    __uniRoutes.push(UniPageRoute(path = "uni_modules/uni-upgrade-center-app/pages/uni-app-x/upgrade-popup", component = GenUniModulesUniUpgradeCenterAppPagesUniAppXUpgradePopupClass, meta = UniPageMeta(isQuit = false), style = _uM()))
    __uniRoutes.push(UniPageRoute(path = "pages/webview/webview", component = GenPagesWebviewWebviewClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/privacy/privacy", component = GenPagesPrivacyPrivacyClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/suppliers/index", component = GenPagesSuppliersIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/suppliers/from", component = GenPagesSuppliersFromClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/transactions/index", component = GenPagesTransactionsIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/transactions/from", component = GenPagesTransactionsFromClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/kasa_category/index", component = GenPagesKasaCategoryIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/kasa_category/form", component = GenPagesKasaCategoryFormClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/category/index", component = GenPagesCategoryIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/category/from", component = GenPagesCategoryFromClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/shop/index", component = GenPagesShopIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/shop/media", component = GenPagesShopMediaClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/shop/from", component = GenPagesShopFromClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/products/from", component = GenPagesProductsFromClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/products/pricing-formula", component = GenPagesProductsPricingFormulaClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/products/pricing-formula/index", component = GenPagesProductsPricingFormulaIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/products/pricing-formula/from", component = GenPagesProductsPricingFormulaFromClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/products/config-model/index", component = GenPagesProductsConfigModelIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/products/config-model/from", component = GenPagesProductsConfigModelFromClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/ksef/index", component = GenPagesKsefIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/expenses/index", component = GenPagesExpensesIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/expenses/from", component = GenPagesExpensesFromClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/purchases/index", component = GenPagesPurchasesIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/purchases/from", component = GenPagesPurchasesFromClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/purchases/details/index", component = GenPagesPurchasesDetailsIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/purchases/details/from", component = GenPagesPurchasesDetailsFromClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/orders/index", component = GenPagesOrdersIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/orders/from", component = GenPagesOrdersFromClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/inventory-management/index", component = GenPagesInventoryManagementIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/inventory-management/from", component = GenPagesInventoryManagementFromClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/inventory-locations/index", component = GenPagesInventoryLocationsIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/inventory-locations/from", component = GenPagesInventoryLocationsFromClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/inventory-transfers/index", component = GenPagesInventoryTransfersIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/inventory-transfers/from", component = GenPagesInventoryTransfersFromClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/inventory-checks/index", component = GenPagesInventoryChecksIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/inventory-checks/from", component = GenPagesInventoryChecksFromClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
}
val __uniTabBar: Map<String, Any?>? = _uM("color" to "#94A3B8", "selectedColor" to "#0F172A", "backgroundColor" to "#FFFFFF", "borderStyle" to "black", "list" to _uA(
    _uM("pagePath" to "pages/tabbar/reports", "iconPath" to "static/tabBar/Report.png", "selectedIconPath" to "static/tabBar/Report (1).png", "text" to "报表"),
    _uM("pagePath" to "pages/tabbar/products", "iconPath" to "static/tabBar/product (1).png", "selectedIconPath" to "static/tabBar/product (2).png", "text" to "商品"),
    _uM("pagePath" to "pages/tabbar/settings", "iconPath" to "static/tabBar/set.png", "selectedIconPath" to "static/tabBar/set (1).png", "text" to "功能"),
    _uM("pagePath" to "pages/tabbar/mine", "iconPath" to "static/tabBar/me.png", "selectedIconPath" to "static/tabBar/me (1).png", "text" to "我的")
))
val __uniLaunchPage: Map<String, Any?> = _uM("url" to "pages/login/login", "style" to _uM("navigationStyle" to "custom"))
fun defineAppConfig() {
    __uniConfig.entryPagePath = "/pages/login/login"
    __uniConfig.globalStyle = _uM("navigationBarTextStyle" to "black", "navigationBarTitleText" to "uni-app x", "navigationBarBackgroundColor" to "#F8F8F8", "backgroundColor" to "#FFFFFF")
    __uniConfig.getTabBarConfig = fun(): Map<String, Any>? {
        return _uM("color" to "#94A3B8", "selectedColor" to "#0F172A", "backgroundColor" to "#FFFFFF", "borderStyle" to "black", "list" to _uA(
            _uM("pagePath" to "pages/tabbar/reports", "iconPath" to "static/tabBar/Report.png", "selectedIconPath" to "static/tabBar/Report (1).png", "text" to "报表"),
            _uM("pagePath" to "pages/tabbar/products", "iconPath" to "static/tabBar/product (1).png", "selectedIconPath" to "static/tabBar/product (2).png", "text" to "商品"),
            _uM("pagePath" to "pages/tabbar/settings", "iconPath" to "static/tabBar/set.png", "selectedIconPath" to "static/tabBar/set (1).png", "text" to "功能"),
            _uM("pagePath" to "pages/tabbar/mine", "iconPath" to "static/tabBar/me.png", "selectedIconPath" to "static/tabBar/me (1).png", "text" to "我的")
        ))
    }
    __uniConfig.tabBar = __uniConfig.getTabBarConfig()
    __uniConfig.conditionUrl = ""
    __uniConfig.uniIdRouter = _uM()
    __uniConfig.ready = true
}
open class UniCloudConfig : io.dcloud.unicloud.InternalUniCloudConfig {
    override var isDev: Boolean = false
    override var spaceList: String = "[{\"provider\":\"aliyun\",\"spaceName\":\"upgrade\",\"spaceId\":\"mp-96026ba1-85de-419a-a8cd-c0902d2756ff\",\"clientSecret\":\"vwY0sYNoIhF2lGtGTNVZeA==\",\"endpoint\":\"https://api.next.bspapp.com\"}]"
    override var debuggerInfo: String? = null
    override var secureNetworkEnable: Boolean = false
    override var secureNetworkConfig: String? = "[]"
    constructor() : super() {}
}
open class GenUniApp : UniAppImpl() {
    open val vm: GenApp?
        get() {
            return getAppVm() as GenApp?
        }
    open val `$vm`: GenApp?
        get() {
            return getAppVm() as GenApp?
        }
}
fun getApp(): GenUniApp {
    return getUniApp() as GenUniApp
}
