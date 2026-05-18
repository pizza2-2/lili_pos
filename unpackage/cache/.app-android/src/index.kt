@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME", "UNUSED_ANONYMOUS_PARAMETER", "SENSELESS_COMPARISON", "NAME_SHADOWING", "UNNECESSARY_NOT_NULL_ASSERTION")
package uni.UNI1CE1B14
import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
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
import java.lang.Thread
import java.nio.charset.Charset
import java.util.UUID
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
import io.dcloud.uniapp.extapi.redirectTo as uni_redirectTo
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
            console.log("初始化推送", " at store/global.uts:15")
            uni_getPushClientId(GetPushClientIdOptions(success = fun(res){
                var push_clientid = res.cid
                console.log("客户端推送标识:", push_clientid, " at store/global.uts:21")
            }
            , fail = fun(err) {
                console.log(err, " at store/global.uts:24")
            }
            ))
            val manager = uni_getPushChannelManager()
            manager.setPushChannel(SetPushChannelOptions(channelId = "channel-id", channelDesc = "通知渠道描述", enableLights = true, enableVibration = true, importance = 4, lockscreenVisibility = 1))
            uni_onPushMessage(fun(res){
                console.log("收到推送消息：", res, " at store/global.uts:42")
                if (res.type == "receive") {
                    if (uni_getAppAuthorizeSetting().notificationAuthorized == "authorized") {
                        console.log("推送权限已开", " at store/global.uts:46")
                        uni_createPushMessage(CreatePushMessageOptions(title = res.data["title"] as String?, content = res.data["content"] as String, cover = true, channelId = "channel-id", `when` = Date.now() + 10000, icon = "/static/logo.png", sound = "system", delay = 1, payload = _uO("pkey" to "pvalue1"), category = "IM", success = fun(res) {
                            console.log("res: " + res, " at store/global.uts:66")
                            uni_hideToast()
                            uni_showToast(ShowToastOptions(title = "创建本地通知消息成功"))
                        }, fail = fun(e) {
                            console.log("fail :" + e, " at store/global.uts:73")
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("AuthState", "store/auth.uts", 2, 13)
    }
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("UserInfoState", "store/auth.uts", 6, 13)
    }
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
    uni_hideLoading(null)
    setTimeout(fun(){
        uni_reLaunch(ReLaunchOptions(url = loginPageUrl, fail = fun(_){
            uni_redirectTo(RedirectToOptions(url = loginPageUrl, fail = fun(_){
                uni_navigateTo(NavigateToOptions(url = loginPageUrl))
            }
            ))
        }
        , complete = fun(_){
            resetRedirectFlagWithDelay()
        }
        ))
    }
    , 30)
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
        var storageResult = UTSAndroid.consoleDebugError(JSON.parse<AuthState>(authStateString), " at store/auth.uts:107")
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
var PermissionTips: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("PermissionTips", "pkg/util/osPermission.uts", 21, 5))
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
            console.log("App Launch", " at App.uvue:16")
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
            console.log("App Show", " at App.uvue:48")
            console.log(options, " at App.uvue:50")
        }
        , __ins)
        onAppHide(fun() {
            console.log("App Hide", " at App.uvue:53")
        }
        , __ins)
        onLastPageBackPress(fun() {
            console.log("App LastPageBackPress", " at App.uvue:57")
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
            console.log(err, " at App.uvue:74")
        }
        , __ins)
        onExit(fun() {
            console.log("App Exit", " at App.uvue:77")
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
val baseUrl: String = "http://43.157.91.24:1996"
val timeOut: Number = 10000
val loginApiUrl = "/api/accounts/auth/login/"
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ResponseMeta", "pkg/api/index.uts", 15, 6)
    }
}
var latestResponseMeta: ResponseMeta? = null
fun clearLatestResponseMeta() {
    latestResponseMeta = null
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
fun buildDownloadHeader(): UTSJSONObject {
    val header: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("header", "pkg/api/index.uts", 44, 11))
    if (authState.token != "") {
        header["Authorization"] = authState.token
    }
    return header
}
fun requestIntercept(reqData: UTSJSONObject): Map<String, UTSJSONObject> {
    val map = Map<String, UTSJSONObject>()
    val header: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("header", "pkg/api/index.uts", 54, 11), "content-type" to "application/json")
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
fun handleUnauthorizedResponse(url: String, showLoading: Boolean) {
    clearLatestResponseMeta()
    if (showLoading) {
        uni_hideLoading(null)
    }
    if (shouldHandleUnauthorized(url)) {
        redirectToLogin("登录状态已失效，请重新登录")
    }
}
fun stringValue(value: Any?): String {
    if (value == null) {
        return ""
    }
    return "" + value
}
fun intValue(value: Any?): Number {
    val parsed = parseInt(stringValue(value))
    if (isNaN(parsed)) {
        return 0
    }
    return parsed
}
fun boolValue(value: Any?): Boolean {
    val text = stringValue(value).toLowerCase()
    return text == "true" || text == "1"
}
fun parseObject(value: Any?): UTSJSONObject? {
    if (value == null) {
        return null
    }
    val text = JSON.stringify(value)
    if (text == null || text == "") {
        return null
    }
    try {
        return UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(text), " at pkg/api/index.uts:106")
    }
     catch (error: Throwable) {
        return null
    }
}
fun saveLatestResponseMetaObject(response: UTSJSONObject) {
    latestResponseMeta = ResponseMeta(success = boolValue(response["success"]), status = stringValue(response["status"]), status_code = intValue(response["status_code"]), message = stringValue(response["message"]), timestamp = stringValue(response["timestamp"]))
}
fun extractQuotedMessage(text: String): String {
    val marker = "\"message\":\""
    val markerIndex = text.indexOf(marker)
    if (markerIndex < 0) {
        return ""
    }
    val startIndex = markerIndex + marker.length
    val endIndex = text.indexOf("\"", startIndex)
    if (endIndex < 0) {
        return ""
    }
    return text.substring(startIndex, endIndex)
}
fun parseRequestFailMessage(err: Any?): String {
    val errorText = stringValue(err)
    val backendMessage = extractQuotedMessage(errorText)
    if (backendMessage != "") {
        return backendMessage
    }
    if (errorText != "" && errorText != "[object Object]") {
        return errorText
    }
    return "网络请求失败"
}
fun request(url: String, method: RequestMethod, reqData: UTSJSONObject = _uO(), showLoading: Boolean = false): UTSPromise<Any> {
    return wrapUTSPromise(suspend w@{
            return@w UTSPromise(fun(resolve, reject){
                clearLatestResponseMeta()
                if (showLoading) {
                    uni_showLoading(ShowLoadingOptions(title = "loading"))
                }
                val interceptMap = requestIntercept(reqData)
                console.log("请求地址:", baseUrl + url, " at pkg/api/index.uts:162")
                uni_request<Any>(RequestOptions(url = baseUrl + url, method = method, header = interceptMap.get("header"), data = interceptMap.get("data"), timeout = timeOut, success = fun(res){
                    if (res.statusCode == 401) {
                        handleUnauthorizedResponse(url, showLoading)
                        reject(UTSError("登录状态已失效"))
                        return
                    }
                    val responseData = res.data
                    val responseObject = parseObject(responseData)
                    if (res.statusCode >= 200 && res.statusCode < 300) {
                        if (responseObject != null && boolValue(responseObject!!["success"])) {
                            saveLatestResponseMetaObject(responseObject!!)
                            resolve(responseObject!!["data"])
                            return
                        }
                        if (responseObject != null && stringValue(responseObject!!["success"]) == "false") {
                            if (intValue(responseObject!!["status_code"]) == 401) {
                                handleUnauthorizedResponse(url, showLoading)
                                reject(UTSError("登录状态已失效"))
                                return
                            }
                            clearLatestResponseMeta()
                            val serverMessage = stringValue(responseObject!!["message"])
                            reject(UTSError(if (serverMessage == "") {
                                "请求失败"
                            } else {
                                serverMessage
                            }
                            ))
                            return
                        }
                        clearLatestResponseMeta()
                        resolve(responseData)
                        return
                    }
                    clearLatestResponseMeta()
                    if (responseObject != null && stringValue(responseObject!!["success"]) == "false") {
                        val serverMessage = stringValue(responseObject!!["message"])
                        if (serverMessage != "") {
                            reject(UTSError(serverMessage))
                            return
                        }
                    }
                    reject(UTSError("HTTP状态码错误: " + res.statusCode))
                }
                , fail = fun(err){
                    clearLatestResponseMeta()
                    reject(UTSError(parseRequestFailMessage(err)))
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("LoginData", "pkg/api/modules/login.uts", 6, 13)
    }
}
open class LoginResponse (
    @JsonNotNull
    open var access_token: String,
    @JsonNotNull
    open var refresh_token: String,
    @JsonNotNull
    open var token_type: String,
    @JsonNotNull
    open var expires_in: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("LoginResponse", "pkg/api/modules/login.uts", 11, 13)
    }
}
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ProfileResponse", "pkg/api/modules/login.uts", 18, 13)
    }
}
val useMockLogin = false
fun accountLogin(data: LoginData): UTSPromise<LoginResponse> {
    return wrapUTSPromise(suspend w@{
            if (useMockLogin) {
                return@w LoginResponse(access_token = "debug-access-token", refresh_token = "debug-refresh-token", token_type = "Bearer", expires_in = 86400)
            }
            val body: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("body", "pkg/api/modules/login.uts", 61, 11), "username" to data.username, "password" to data.password)
            val raw = await(request("/api/accounts/auth/login/", "POST", body, true))
            val parsed = UTSAndroid.consoleDebugError(JSON.parseObject<LoginResponse>(JSON.stringify(raw)), " at pkg/api/modules/login.uts:66")
            if (parsed == null) {
                throw UTSError("登录响应解析失败")
            }
            return@w parsed!!
    })
}
fun getProfile(): UTSPromise<ProfileResponse> {
    return wrapUTSPromise(suspend w@{
            if (useMockLogin) {
                return@w ProfileResponse(id = 1, username = "debug", email = "", first_name = "Debug", last_name = "User", is_active = true, date_joined = "2026-05-13T00:00:00+02:00", is_platform_admin = true, company_id = 1, company_name = "DEBUG", name = "调试账号", cashier_account = "", cashier_id = null, shop_ids = _uA(
                    1
                ), permissions = _uA(
                    "approve",
                    "export",
                    "manage_inventory",
                    "manage_purchase",
                    "manage_users",
                    "view_cost",
                    "void"
                ))
            }
            val raw = await(request("/api/accounts/auth/me/", "GET", _uO(), false))
            val parsed = UTSAndroid.consoleDebugError(JSON.parseObject<ProfileResponse>(JSON.stringify(raw)), " at pkg/api/modules/login.uts:134")
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ReportOverview", "pkg/api/modules/reports.uts", 3, 13)
    }
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ReportTrendItem", "pkg/api/modules/reports.uts", 12, 13)
    }
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ReportPaymentMethod", "pkg/api/modules/reports.uts", 17, 13)
    }
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ReportInventory", "pkg/api/modules/reports.uts", 23, 13)
    }
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ReportAlert", "pkg/api/modules/reports.uts", 32, 13)
    }
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
open class ReportRankItem (
    @JsonNotNull
    open var key: String,
    @JsonNotNull
    open var label: String,
    @JsonNotNull
    open var subtitle: String,
    @JsonNotNull
    open var code: String,
    @JsonNotNull
    open var amount: String,
    @JsonNotNull
    open var quantity: String,
    @JsonNotNull
    open var order_count: Number,
    @JsonNotNull
    open var line_count: Number,
    @JsonNotNull
    open var share: String,
    @JsonNotNull
    open var type: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ReportRankItem", "pkg/api/modules/reports.uts", 37, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ReportRankItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ReportRankItemReactiveObject : ReportRankItem, IUTSReactive<ReportRankItem> {
    override var __v_raw: ReportRankItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ReportRankItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(key = __v_raw.key, label = __v_raw.label, subtitle = __v_raw.subtitle, code = __v_raw.code, amount = __v_raw.amount, quantity = __v_raw.quantity, order_count = __v_raw.order_count, line_count = __v_raw.line_count, share = __v_raw.share, type = __v_raw.type) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ReportRankItemReactiveObject {
        return ReportRankItemReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
    override var subtitle: String
        get() {
            return _tRG(__v_raw, "subtitle", __v_raw.subtitle, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("subtitle")) {
                return
            }
            val oldValue = __v_raw.subtitle
            __v_raw.subtitle = value
            _tRS(__v_raw, "subtitle", oldValue, value)
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
    override var quantity: String
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
    override var line_count: Number
        get() {
            return _tRG(__v_raw, "line_count", __v_raw.line_count, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("line_count")) {
                return
            }
            val oldValue = __v_raw.line_count
            __v_raw.line_count = value
            _tRS(__v_raw, "line_count", oldValue, value)
        }
    override var share: String
        get() {
            return _tRG(__v_raw, "share", __v_raw.share, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("share")) {
                return
            }
            val oldValue = __v_raw.share
            __v_raw.share = value
            _tRS(__v_raw, "share", oldValue, value)
        }
    override var type: String
        get() {
            return _tRG(__v_raw, "type", __v_raw.type, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("type")) {
                return
            }
            val oldValue = __v_raw.type
            __v_raw.type = value
            _tRS(__v_raw, "type", oldValue, value)
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
    open var top_products: UTSArray<ReportRankItem>,
    @JsonNotNull
    open var top_categories: UTSArray<ReportRankItem>,
    @JsonNotNull
    open var top_kasa_categories: UTSArray<ReportRankItem>,
    @JsonNotNull
    open var inventory: ReportInventory,
    @JsonNotNull
    open var alerts: UTSArray<ReportAlert>,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("DashboardReport", "pkg/api/modules/reports.uts", 49, 13)
    }
}
fun intValue__1(value: Any?): Number {
    if (value == null) {
        return 0
    }
    val parsed = parseInt("" + value)
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
fun objectValue(value: Any?): UTSJSONObject {
    if (value == null) {
        return _uO()
    }
    val text = JSON.stringify(value)
    val parsed = if (text == null || text == "") {
        null
    } else {
        UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(text), " at pkg/api/modules/reports.uts:77")
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
        UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(text), " at pkg/api/modules/reports.uts:84")
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
        UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(rawText), " at pkg/api/modules/reports.uts:89")
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
    return ReportOverview(sales_amount = stringValue__1(raw["sales_amount"]), order_count = intValue__1(raw["order_count"]), average_order_value = stringValue__1(raw["average_order_value"]), purchase_amount = stringValue__1(raw["purchase_amount"]), expense_amount = stringValue__1(raw["expense_amount"]), arrears_amount = stringValue__1(raw["arrears_amount"]), net_cashflow = stringValue__1(raw["net_cashflow"]))
}
fun buildTrendItems(value: Any?): UTSArray<ReportTrendItem> {
    val rows = arrayValue(value)
    val result: UTSArray<ReportTrendItem> = _uA()
    run {
        var index: Number = 0
        while(index < rows.length){
            val row = rows[index]
            result.push(ReportTrendItem(date = stringValue__1(row["date"]), amount = stringValue__1(row["amount"]), order_count = intValue__1(row["order_count"])))
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
            result.push(ReportPaymentMethod(key = stringValue__1(row["key"]), label = stringValue__1(row["label"]), amount = stringValue__1(row["amount"]), count = intValue__1(row["count"])))
            index += 1
        }
    }
    return result
}
fun buildInventory(raw: UTSJSONObject): ReportInventory {
    return ReportInventory(stock_item_count = intValue__1(raw["stock_item_count"]), total_quantity = intValue__1(raw["total_quantity"]), available_quantity = intValue__1(raw["available_quantity"]), low_stock_count = intValue__1(raw["low_stock_count"]), out_of_stock_count = intValue__1(raw["out_of_stock_count"]), no_movement_count = intValue__1(raw["no_movement_count"]), inventory_value = stringValue__1(raw["inventory_value"]))
}
fun buildAlerts(value: Any?): UTSArray<ReportAlert> {
    val rows = arrayValue(value)
    val result: UTSArray<ReportAlert> = _uA()
    run {
        var index: Number = 0
        while(index < rows.length){
            val row = rows[index]
            result.push(ReportAlert(level = stringValue__1(row["level"]), label = stringValue__1(row["label"]), value = intValue__1(row["value"])))
            index += 1
        }
    }
    return result
}
fun buildRankItems(value: Any?): UTSArray<ReportRankItem> {
    val rows = arrayValue(value)
    val result: UTSArray<ReportRankItem> = _uA()
    run {
        var index: Number = 0
        while(index < rows.length){
            val row = rows[index]
            result.push(ReportRankItem(key = stringValue__1(row["key"]), label = stringValue__1(row["label"]), subtitle = stringValue__1(row["subtitle"]), code = stringValue__1(row["code"]), amount = stringValue__1(row["amount"]), quantity = stringValue__1(row["quantity"]), order_count = intValue__1(row["order_count"]), line_count = intValue__1(row["line_count"]), share = stringValue__1(row["share"]), type = stringValue__1(row["type"])))
            index += 1
        }
    }
    return result
}
fun buildDashboardReport(raw: Any): DashboardReport {
    val rawObject = rawDataObject(raw)
    return DashboardReport(overview = buildOverview(objectValue(rawObject["overview"])), sales_trend = buildTrendItems(rawObject["sales_trend"]), payment_methods = buildPaymentMethods(rawObject["payment_methods"]), order_status = objectValue(rawObject["order_status"]), top_products = buildRankItems(rawObject["top_products"]), top_categories = buildRankItems(rawObject["top_categories"]), top_kasa_categories = buildRankItems(rawObject["top_kasa_categories"]), inventory = buildInventory(objectValue(rawObject["inventory"])), alerts = buildAlerts(rawObject["alerts"]))
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PeriodOption", "pages/tabbar/reports.uvue", 227, 6)
    }
}
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ProductListQuery", "pkg/api/modules/products.uts", 2, 13)
    }
}
open class ProductSelectedFilter (
    @JsonNotNull
    open var param: String,
    @JsonNotNull
    open var value: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ProductSelectedFilter", "pkg/api/modules/products.uts", 8, 13)
    }
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ProductMediaFile", "pkg/api/modules/products.uts", 12, 13)
    }
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
    open var discount_info: ProductDiscountInfo? = null,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ProductItem", "pkg/api/modules/products.uts", 30, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ProductItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ProductItemReactiveObject : ProductItem, IUTSReactive<ProductItem> {
    override var __v_raw: ProductItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ProductItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, sku = __v_raw.sku, barcode = __v_raw.barcode, name_cn = __v_raw.name_cn, name_en = __v_raw.name_en, name_other = __v_raw.name_other, description = __v_raw.description, media_files = __v_raw.media_files, category = __v_raw.category, supplier = __v_raw.supplier, supplier_name = __v_raw.supplier_name, purchase_price = __v_raw.purchase_price, net_purchase_price = __v_raw.net_purchase_price, cost_price = __v_raw.cost_price, base_sales_price = __v_raw.base_sales_price, status = __v_raw.status, is_featured = __v_raw.is_featured, is_new = __v_raw.is_new, is_bestseller = __v_raw.is_bestseller, sort_order = __v_raw.sort_order, rating = __v_raw.rating, variant_count = __v_raw.variant_count, total_sales_quantity = __v_raw.total_sales_quantity, total_sales_amount = __v_raw.total_sales_amount, last_sale_date = __v_raw.last_sale_date, created_at = __v_raw.created_at, updated_at = __v_raw.updated_at, discount_info = __v_raw.discount_info) {
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
    override var discount_info: ProductDiscountInfo?
        get() {
            return _tRG(__v_raw, "discount_info", __v_raw.discount_info, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("discount_info")) {
                return
            }
            val oldValue = __v_raw.discount_info
            __v_raw.discount_info = value
            _tRS(__v_raw, "discount_info", oldValue, value)
        }
}
open class ProductDiscountInfo (
    @JsonNotNull
    open var has_discount: Boolean = false,
    open var discount_id: Number? = null,
    @JsonNotNull
    open var discount_name: String,
    @JsonNotNull
    open var discount_type: String,
    @JsonNotNull
    open var original_price: String,
    @JsonNotNull
    open var final_price: String,
    @JsonNotNull
    open var discount_amount: String,
    @JsonNotNull
    open var discount_percentage: String,
    @JsonNotNull
    open var discount_amount_fixed: String,
    @JsonNotNull
    open var min_quantity: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ProductDiscountInfo", "pkg/api/modules/products.uts", 60, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ProductDiscountInfoReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ProductDiscountInfoReactiveObject : ProductDiscountInfo, IUTSReactive<ProductDiscountInfo> {
    override var __v_raw: ProductDiscountInfo
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ProductDiscountInfo, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(has_discount = __v_raw.has_discount, discount_id = __v_raw.discount_id, discount_name = __v_raw.discount_name, discount_type = __v_raw.discount_type, original_price = __v_raw.original_price, final_price = __v_raw.final_price, discount_amount = __v_raw.discount_amount, discount_percentage = __v_raw.discount_percentage, discount_amount_fixed = __v_raw.discount_amount_fixed, min_quantity = __v_raw.min_quantity) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ProductDiscountInfoReactiveObject {
        return ProductDiscountInfoReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var has_discount: Boolean
        get() {
            return _tRG(__v_raw, "has_discount", __v_raw.has_discount, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("has_discount")) {
                return
            }
            val oldValue = __v_raw.has_discount
            __v_raw.has_discount = value
            _tRS(__v_raw, "has_discount", oldValue, value)
        }
    override var discount_id: Number?
        get() {
            return _tRG(__v_raw, "discount_id", __v_raw.discount_id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("discount_id")) {
                return
            }
            val oldValue = __v_raw.discount_id
            __v_raw.discount_id = value
            _tRS(__v_raw, "discount_id", oldValue, value)
        }
    override var discount_name: String
        get() {
            return _tRG(__v_raw, "discount_name", __v_raw.discount_name, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("discount_name")) {
                return
            }
            val oldValue = __v_raw.discount_name
            __v_raw.discount_name = value
            _tRS(__v_raw, "discount_name", oldValue, value)
        }
    override var discount_type: String
        get() {
            return _tRG(__v_raw, "discount_type", __v_raw.discount_type, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("discount_type")) {
                return
            }
            val oldValue = __v_raw.discount_type
            __v_raw.discount_type = value
            _tRS(__v_raw, "discount_type", oldValue, value)
        }
    override var original_price: String
        get() {
            return _tRG(__v_raw, "original_price", __v_raw.original_price, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("original_price")) {
                return
            }
            val oldValue = __v_raw.original_price
            __v_raw.original_price = value
            _tRS(__v_raw, "original_price", oldValue, value)
        }
    override var final_price: String
        get() {
            return _tRG(__v_raw, "final_price", __v_raw.final_price, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("final_price")) {
                return
            }
            val oldValue = __v_raw.final_price
            __v_raw.final_price = value
            _tRS(__v_raw, "final_price", oldValue, value)
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
    override var discount_percentage: String
        get() {
            return _tRG(__v_raw, "discount_percentage", __v_raw.discount_percentage, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("discount_percentage")) {
                return
            }
            val oldValue = __v_raw.discount_percentage
            __v_raw.discount_percentage = value
            _tRS(__v_raw, "discount_percentage", oldValue, value)
        }
    override var discount_amount_fixed: String
        get() {
            return _tRG(__v_raw, "discount_amount_fixed", __v_raw.discount_amount_fixed, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("discount_amount_fixed")) {
                return
            }
            val oldValue = __v_raw.discount_amount_fixed
            __v_raw.discount_amount_fixed = value
            _tRS(__v_raw, "discount_amount_fixed", oldValue, value)
        }
    override var min_quantity: Number
        get() {
            return _tRG(__v_raw, "min_quantity", __v_raw.min_quantity, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("min_quantity")) {
                return
            }
            val oldValue = __v_raw.min_quantity
            __v_raw.min_quantity = value
            _tRS(__v_raw, "min_quantity", oldValue, value)
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ProductMutationData", "pkg/api/modules/products.uts", 72, 13)
    }
}
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ProductListResponse", "pkg/api/modules/products.uts", 91, 13)
    }
}
open class ProductFilterOption (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var label: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ProductFilterOption", "pkg/api/modules/products.uts", 99, 13)
    }
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ProductFilterDefinition", "pkg/api/modules/products.uts", 103, 13)
    }
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ProductFilterOptionsResponse", "pkg/api/modules/products.uts", 112, 13)
    }
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ProductPricingFormulaItem", "pkg/api/modules/products.uts", 117, 13)
    }
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
    open var is_active: String? = null,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ProductPricingFormulaListQuery", "pkg/api/modules/products.uts", 129, 13)
    }
}
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ProductPricingFormulaListResponse", "pkg/api/modules/products.uts", 135, 13)
    }
}
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ProductPricingFormulaMutationData", "pkg/api/modules/products.uts", 143, 13)
    }
}
open class ProductPricingFormulaBatchPreviewResult (
    @JsonNotNull
    open var formula_id: Number,
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var code: String,
    @JsonNotNull
    open var expression: String,
    @JsonNotNull
    open var result: String,
    @JsonNotNull
    open var profit: String,
    @JsonNotNull
    open var margin_rate: String,
    @JsonNotNull
    open var source_label: String,
    @JsonNotNull
    open var profitable: Boolean = false,
    @JsonNotNull
    open var error: String,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ProductPricingFormulaBatchPreviewResult", "pkg/api/modules/products.uts", 150, 13)
    }
}
open class ProductPricingFormulaBatchPreviewResponse (
    @JsonNotNull
    open var results: UTSArray<ProductPricingFormulaBatchPreviewResult>,
    @JsonNotNull
    open var count: Number,
    @JsonNotNull
    open var limit: Number,
    @JsonNotNull
    open var inputs: UTSJSONObject,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ProductPricingFormulaBatchPreviewResponse", "pkg/api/modules/products.uts", 162, 13)
    }
}
open class ProductPricingFormulaBatchPreviewRequest (
    @JsonNotNull
    open var purchase_price: String,
    @JsonNotNull
    open var net_purchase_price: String,
    @JsonNotNull
    open var cost_price: String,
    @JsonNotNull
    open var base_sales_price: String,
    @JsonNotNull
    open var formula_ids: UTSArray<Number>,
    @JsonNotNull
    open var profitable_only: Boolean = false,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ProductPricingFormulaBatchPreviewRequest", "pkg/api/modules/products.uts", 168, 13)
    }
}
open class PrintTemplateItem (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var company: Number,
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var template_type: String,
    @JsonNotNull
    open var template_type_display: String,
    @JsonNotNull
    open var printer_language: String,
    @JsonNotNull
    open var paper_width_mm: String,
    @JsonNotNull
    open var paper_height_mm: String,
    @JsonNotNull
    open var dots_per_mm: Number,
    @JsonNotNull
    open var copies_default: Number,
    @JsonNotNull
    open var elements: UTSArray<UTSJSONObject>,
    @JsonNotNull
    open var is_default: Boolean = false,
    @JsonNotNull
    open var is_active: Boolean = false,
    @JsonNotNull
    open var description: String,
    @JsonNotNull
    open var created_at: String,
    @JsonNotNull
    open var updated_at: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PrintTemplateItem", "pkg/api/modules/products.uts", 181, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return PrintTemplateItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class PrintTemplateItemReactiveObject : PrintTemplateItem, IUTSReactive<PrintTemplateItem> {
    override var __v_raw: PrintTemplateItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: PrintTemplateItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, company = __v_raw.company, name = __v_raw.name, template_type = __v_raw.template_type, template_type_display = __v_raw.template_type_display, printer_language = __v_raw.printer_language, paper_width_mm = __v_raw.paper_width_mm, paper_height_mm = __v_raw.paper_height_mm, dots_per_mm = __v_raw.dots_per_mm, copies_default = __v_raw.copies_default, elements = __v_raw.elements, is_default = __v_raw.is_default, is_active = __v_raw.is_active, description = __v_raw.description, created_at = __v_raw.created_at, updated_at = __v_raw.updated_at) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): PrintTemplateItemReactiveObject {
        return PrintTemplateItemReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
    override var template_type: String
        get() {
            return _tRG(__v_raw, "template_type", __v_raw.template_type, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("template_type")) {
                return
            }
            val oldValue = __v_raw.template_type
            __v_raw.template_type = value
            _tRS(__v_raw, "template_type", oldValue, value)
        }
    override var template_type_display: String
        get() {
            return _tRG(__v_raw, "template_type_display", __v_raw.template_type_display, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("template_type_display")) {
                return
            }
            val oldValue = __v_raw.template_type_display
            __v_raw.template_type_display = value
            _tRS(__v_raw, "template_type_display", oldValue, value)
        }
    override var printer_language: String
        get() {
            return _tRG(__v_raw, "printer_language", __v_raw.printer_language, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("printer_language")) {
                return
            }
            val oldValue = __v_raw.printer_language
            __v_raw.printer_language = value
            _tRS(__v_raw, "printer_language", oldValue, value)
        }
    override var paper_width_mm: String
        get() {
            return _tRG(__v_raw, "paper_width_mm", __v_raw.paper_width_mm, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("paper_width_mm")) {
                return
            }
            val oldValue = __v_raw.paper_width_mm
            __v_raw.paper_width_mm = value
            _tRS(__v_raw, "paper_width_mm", oldValue, value)
        }
    override var paper_height_mm: String
        get() {
            return _tRG(__v_raw, "paper_height_mm", __v_raw.paper_height_mm, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("paper_height_mm")) {
                return
            }
            val oldValue = __v_raw.paper_height_mm
            __v_raw.paper_height_mm = value
            _tRS(__v_raw, "paper_height_mm", oldValue, value)
        }
    override var dots_per_mm: Number
        get() {
            return _tRG(__v_raw, "dots_per_mm", __v_raw.dots_per_mm, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("dots_per_mm")) {
                return
            }
            val oldValue = __v_raw.dots_per_mm
            __v_raw.dots_per_mm = value
            _tRS(__v_raw, "dots_per_mm", oldValue, value)
        }
    override var copies_default: Number
        get() {
            return _tRG(__v_raw, "copies_default", __v_raw.copies_default, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("copies_default")) {
                return
            }
            val oldValue = __v_raw.copies_default
            __v_raw.copies_default = value
            _tRS(__v_raw, "copies_default", oldValue, value)
        }
    override var elements: UTSArray<UTSJSONObject>
        get() {
            return _tRG(__v_raw, "elements", __v_raw.elements, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("elements")) {
                return
            }
            val oldValue = __v_raw.elements
            __v_raw.elements = value
            _tRS(__v_raw, "elements", oldValue, value)
        }
    override var is_default: Boolean
        get() {
            return _tRG(__v_raw, "is_default", __v_raw.is_default, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("is_default")) {
                return
            }
            val oldValue = __v_raw.is_default
            __v_raw.is_default = value
            _tRS(__v_raw, "is_default", oldValue, value)
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
open class PrintTemplateListQuery (
    open var search: String? = null,
    @JsonNotNull
    open var page: Number,
    @JsonNotNull
    open var page_size: Number,
    open var template_type: String? = null,
    open var is_active: String? = null,
    open var is_default: String? = null,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PrintTemplateListQuery", "pkg/api/modules/products.uts", 199, 13)
    }
}
open class PrintTemplateListResponse (
    @JsonNotNull
    open var results: UTSArray<PrintTemplateItem>,
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PrintTemplateListResponse", "pkg/api/modules/products.uts", 207, 13)
    }
}
open class PrintTemplateMutationData (
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var template_type: String,
    @JsonNotNull
    open var printer_language: String,
    @JsonNotNull
    open var paper_width_mm: String,
    @JsonNotNull
    open var paper_height_mm: String,
    @JsonNotNull
    open var dots_per_mm: Number,
    @JsonNotNull
    open var copies_default: Number,
    @JsonNotNull
    open var elements: UTSArray<UTSJSONObject>,
    @JsonNotNull
    open var is_default: Boolean = false,
    @JsonNotNull
    open var is_active: Boolean = false,
    @JsonNotNull
    open var description: String,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PrintTemplateMutationData", "pkg/api/modules/products.uts", 215, 13)
    }
}
val productsBasePath = "/api/products/products/"
val productPricingFormulasBasePath = "/api/products/pricing-formulas/"
val productDiscountsBasePath = "/api/products/discounts/"
val attributeTypesBasePath = "/api/products/attribute-types/"
val attributeValuesBasePath = "/api/products/attribute-values/"
val barcodeSequencesBasePath = "/api/products/barcode-sequences/"
fun buildListQuery(data: ProductListQuery): UTSJSONObject {
    val query: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("query", "pkg/api/modules/products.uts", 235, 11), "page" to data.page, "page_size" to data.page_size)
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
    val query: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("query", "pkg/api/modules/products.uts", 251, 11), "page" to data.page, "page_size" to data.page_size)
    if (data.search != null && data.search != "") {
        query["search"] = data.search
    }
    if (data.is_active != null && data.is_active != "") {
        query["is_active"] = data.is_active
    }
    return query
}
fun buildPrintTemplateListQuery(data: PrintTemplateListQuery): UTSJSONObject {
    val query: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("query", "pkg/api/modules/products.uts", 264, 11), "page" to data.page, "page_size" to data.page_size)
    if (data.search != null && data.search != "") {
        query["search"] = data.search
    }
    if (data.template_type != null && data.template_type != "") {
        query["template_type"] = data.template_type
    }
    if (data.is_active != null && data.is_active != "") {
        query["is_active"] = data.is_active
    }
    if (data.is_default != null && data.is_default != "") {
        query["is_default"] = data.is_default
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
fun parseObject__1(value: Any?): UTSJSONObject? {
    if (value == null) {
        return null
    }
    val text = JSON.stringify(value)
    if (text == null || text == "") {
        return null
    }
    return UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(text), " at pkg/api/modules/products.uts:323")
}
fun parseObjectArray(value: Any?): UTSArray<UTSJSONObject> {
    if (value == null) {
        return _uA<UTSJSONObject>()
    }
    val text = JSON.stringify(value)
    if (text == null || text == "") {
        return _uA<UTSJSONObject>()
    }
    val parsed = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(text), " at pkg/api/modules/products.uts:333")
    if (parsed == null) {
        return _uA<UTSJSONObject>()
    }
    return parsed!!
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
fun booleanValue(value: Any?): Boolean {
    val text = stringValue__2(value).toLowerCase()
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
        UTSAndroid.consoleDebugError(JSON.parseArray<Any>(text), " at pkg/api/modules/products.uts:368")
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
fun buildProductMediaFileFromObject(rawObject: UTSJSONObject): ProductMediaFile {
    return ProductMediaFile(id = stringValue__2(rawObject["id"]), company = intValue__2(rawObject["company"]), original_filename = stringValue__2(rawObject["original_filename"]), file_type = stringValue__2(rawObject["file_type"]), file_type_display = stringValue__2(rawObject["file_type_display"]), mime_type = stringValue__2(rawObject["mime_type"]), file_size = intValue__2(rawObject["file_size"]), file_size_display = stringValue__2(rawObject["file_size_display"]), file_url = normalizeServerUrl(stringValue__2(rawObject["file_url"])), thumbnail_url = normalizeServerUrl(stringValue__2(rawObject["thumbnail_url"])), signed_url = normalizeServerUrl(stringValue__2(rawObject["signed_url"])), signed_thumbnail_url = normalizeServerUrl(stringValue__2(rawObject["signed_thumbnail_url"])), object_id = stringValue__2(rawObject["object_id"]), is_deleted = booleanValue(rawObject["is_deleted"]), created_at = stringValue__2(rawObject["created_at"]), updated_at = stringValue__2(rawObject["updated_at"]))
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
    return ProductItem(id = intValue__2(rawObject["id"]), sku = stringValue__2(rawObject["sku"]), barcode = stringValue__2(rawObject["barcode"]), name_cn = stringValue__2(rawObject["name_cn"]), name_en = stringValue__2(rawObject["name_en"]), name_other = stringValue__2(rawObject["name_other"]), description = stringValue__2(rawObject["description"]), media_files = buildProductMediaFilesFromValue(rawObject["media_files"]), category = rawObject["category"], supplier = if (rawObject["supplier"] == null) {
        null
    } else {
        intValue__2(rawObject["supplier"])
    }
    , supplier_name = stringValue__2(rawObject["supplier_name"]), purchase_price = stringValue__2(rawObject["purchase_price"]), net_purchase_price = stringValue__2(rawObject["net_purchase_price"]), cost_price = stringValue__2(rawObject["cost_price"]), base_sales_price = stringValue__2(rawObject["base_sales_price"]), status = stringValue__2(rawObject["status"]), is_featured = booleanValue(rawObject["is_featured"]), is_new = booleanValue(rawObject["is_new"]), is_bestseller = booleanValue(rawObject["is_bestseller"]), sort_order = intValue__2(rawObject["sort_order"]), rating = stringValue__2(rawObject["rating"]), variant_count = intValue__2(rawObject["variant_count"]), total_sales_quantity = intValue__2(rawObject["total_sales_quantity"]), total_sales_amount = stringValue__2(rawObject["total_sales_amount"]), last_sale_date = if (rawObject["last_sale_date"] == null) {
        null
    } else {
        stringValue__2(rawObject["last_sale_date"])
    }
    , created_at = stringValue__2(rawObject["created_at"]), updated_at = stringValue__2(rawObject["updated_at"]), discount_info = parseProductDiscountInfo(rawObject["discount_info"]))
}
fun buildProductItemResponse(raw: Any): ProductItem {
    val rawObject = parseObject__1(raw)
    if (rawObject == null) {
        throw UTSError("商品详情响应解析失败")
    }
    return buildProductItemFromObject(rawObject!!)
}
fun buildPricingFormulaItemFromObject(rawObject: UTSJSONObject): ProductPricingFormulaItem {
    return ProductPricingFormulaItem(id = intValue__2(rawObject["id"]), company = intValue__2(rawObject["company"]), name = stringValue__2(rawObject["name"]), code = stringValue__2(rawObject["code"]), expression = stringValue__2(rawObject["expression"]), description = stringValue__2(rawObject["description"]), is_active = booleanValue(rawObject["is_active"]), supported_functions = stringArrayValue(rawObject["supported_functions"]), created_at = stringValue__2(rawObject["created_at"]), updated_at = stringValue__2(rawObject["updated_at"]))
}
fun buildPricingFormulaItemResponse(raw: Any): ProductPricingFormulaItem {
    val rawObject = parseObject__1(raw)
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
        UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(rawText), " at pkg/api/modules/products.uts:468")
    }
    if (rawObject == null) {
        throw UTSError("价格公式列表响应解析失败")
    }
    var paginationObject: UTSJSONObject? = null
    val rawPagination = rawObject["pagination"]
    if (rawPagination != null) {
        val paginationText = JSON.stringify(rawPagination)
        if (paginationText != null && paginationText != "") {
            paginationObject = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(paginationText), " at pkg/api/modules/products.uts:477")
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
    var totalCount = intValue__2(rawObject["count"])
    if (totalCount <= 0) {
        totalCount = intValue__2(rawObject["total"])
    }
    if (totalCount <= 0) {
        totalCount = intValue__2(rawObject["total_count"])
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
    var currentPage = intValue__2(rawObject["page"])
    if (currentPage <= 0) {
        currentPage = intValue__2(rawObject["current_page"])
    }
    if (currentPage <= 0 && paginationObject != null) {
        currentPage = intValue__2(paginationObject["page"])
    }
    if (currentPage <= 0) {
        currentPage = query.page
    }
    var pageSize = intValue__2(rawObject["page_size"])
    if (pageSize <= 0) {
        pageSize = intValue__2(rawObject["per_page"])
    }
    if (pageSize <= 0 && paginationObject != null) {
        pageSize = intValue__2(paginationObject["page_size"])
    }
    if (pageSize <= 0) {
        pageSize = query.page_size
    }
    var totalPages = intValue__2(rawObject["total_pages"])
    if (totalPages <= 0) {
        totalPages = intValue__2(rawObject["pages"])
    }
    if (totalPages <= 0) {
        totalPages = intValue__2(rawObject["num_pages"])
    }
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__2(paginationObject["total_pages"])
    }
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__2(paginationObject["pages"])
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
    return ProductPricingFormulaListResponse(results = results, count = totalCount, total_count = totalCount, total_pages = totalPages, current_page = currentPage, page_size = pageSize)
}
fun buildPricingFormulaMutationBody(data: ProductPricingFormulaMutationData): UTSJSONObject {
    return _uO("name" to data.name, "code" to data.code, "expression" to data.expression, "description" to data.description, "is_active" to data.is_active)
}
fun buildBatchPreviewResponse(raw: Any): ProductPricingFormulaBatchPreviewResponse {
    val rawObject = parseObject__1(raw)
    if (rawObject == null) {
        throw UTSError("价格公式批量试算响应解析失败")
    }
    val rawResults = parseObjectArray(rawObject["results"])
    val results: UTSArray<ProductPricingFormulaBatchPreviewResult> = _uA()
    run {
        var index: Number = 0
        while(index < rawResults.length){
            val item = rawResults[index]
            results.push(ProductPricingFormulaBatchPreviewResult(formula_id = intValue__2(item["formula_id"]), name = stringValue__2(item["name"]), code = stringValue__2(item["code"]), expression = stringValue__2(item["expression"]), result = stringValue__2(item["result"]), profit = stringValue__2(item["profit"]), margin_rate = stringValue__2(item["margin_rate"]), source_label = stringValue__2(item["source_label"]), profitable = booleanValue(item["profitable"]), error = stringValue__2(item["error"])))
            index += 1
        }
    }
    val inputs = if (rawObject["inputs"] == null) {
        _uO()
    } else {
        (rawObject["inputs"] as UTSJSONObject)
    }
    return ProductPricingFormulaBatchPreviewResponse(results = results, count = intValue__2(rawObject["count"]), limit = intValue__2(rawObject["limit"]), inputs = inputs)
}
fun buildProductListResponse(raw: Any, query: ProductListQuery): ProductListResponse {
    val rawText = JSON.stringify(raw)
    val rawObject = if (rawText == null || rawText == "") {
        null
    } else {
        UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(rawText), " at pkg/api/modules/products.uts:589")
    }
    if (rawObject == null) {
        throw UTSError("商品列表响应解析失败")
    }
    var paginationObject: UTSJSONObject? = null
    val rawPagination = rawObject["pagination"]
    if (rawPagination != null) {
        val paginationText = JSON.stringify(rawPagination)
        if (paginationText != null && paginationText != "") {
            paginationObject = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(paginationText), " at pkg/api/modules/products.uts:598")
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
    var totalCount = intValue__2(rawObject["count"])
    if (totalCount <= 0) {
        totalCount = intValue__2(rawObject["total"])
    }
    if (totalCount <= 0) {
        totalCount = intValue__2(rawObject["total_count"])
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
    var currentPage = intValue__2(rawObject["page"])
    if (currentPage <= 0) {
        currentPage = intValue__2(rawObject["current_page"])
    }
    if (currentPage <= 0 && paginationObject != null) {
        currentPage = intValue__2(paginationObject["page"])
    }
    if (currentPage <= 0) {
        currentPage = query.page
    }
    var pageSize = intValue__2(rawObject["page_size"])
    if (pageSize <= 0) {
        pageSize = intValue__2(rawObject["per_page"])
    }
    if (pageSize <= 0 && paginationObject != null) {
        pageSize = intValue__2(paginationObject["page_size"])
    }
    if (pageSize <= 0) {
        pageSize = query.page_size
    }
    var totalPages = intValue__2(rawObject["total_pages"])
    if (totalPages <= 0) {
        totalPages = intValue__2(rawObject["pages"])
    }
    if (totalPages <= 0) {
        totalPages = intValue__2(rawObject["num_pages"])
    }
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__2(paginationObject["total_pages"])
    }
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__2(paginationObject["pages"])
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
    return ProductListResponse(results = results, count = totalCount, total_count = totalCount, total_pages = totalPages, current_page = currentPage, page_size = pageSize)
}
fun buildProductFilterOptionsResponse(raw: Any): ProductFilterOptionsResponse {
    val rawText = JSON.stringify(raw)
    val rawObject = if (rawText == null || rawText == "") {
        null
    } else {
        UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(rawText), " at pkg/api/modules/products.uts:678")
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
            UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(filtersText), " at pkg/api/modules/products.uts:686")
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
                            UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(optionsText), " at pkg/api/modules/products.uts:695")
                        }
                        if (optionObjects != null) {
                            val nextOptions: UTSArray<ProductFilterOption> = _uA()
                            run {
                                var optionIndex: Number = 0
                                while(optionIndex < optionObjects!!.length){
                                    val optionObject = optionObjects!![optionIndex]
                                    nextOptions.push(ProductFilterOption(value = stringValue__2(optionObject["value"]), label = stringValue__2(optionObject["label"])))
                                    optionIndex += 1
                                }
                            }
                            options = nextOptions
                        }
                    }
                    nextFilters.push(ProductFilterDefinition(key = stringValue__2(filterObject["key"]), param = stringValue__2(filterObject["param"]), label = stringValue__2(filterObject["label"]), control = stringValue__2(filterObject["control"]), aliases = stringArrayValue(filterObject["aliases"]), multiple = booleanValue(filterObject["multiple"]), options = options))
                    filterIndex += 1
                }
            }
            filters = nextFilters
        }
    }
    return ProductFilterOptionsResponse(resource = stringValue__2(rawObject["resource"]), count = intValue__2(rawObject["count"]), filters = filters)
}
fun productDetailPath(id: Any): String {
    return productsBasePath + stringValue__2(id) + "/"
}
fun pricingFormulaDetailPath(id: Any): String {
    return productPricingFormulasBasePath + stringValue__2(id) + "/"
}
fun resourceDetailPath(basePath: String, id: Any): String {
    return basePath + stringValue__2(id) + "/"
}
fun buildConfigListQuery(search: String?, page: Number, pageSize: Number, extra: UTSJSONObject = _uO()): UTSJSONObject {
    val query: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("query", "pkg/api/modules/products.uts", 737, 11), "page" to page, "page_size" to pageSize)
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
    val product = extra["product"]
    if (product != null && ("" + product) != "") {
        query["product"] = product
    }
    return query
}
fun buildConfigListResponse(raw: Any, page: Number, pageSize: Number): UTSJSONObject {
    val rawText = JSON.stringify(raw)
    val rawObject = if (rawText == null || rawText == "") {
        null
    } else {
        UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(rawText), " at pkg/api/modules/products.uts:768")
    }
    if (rawObject == null) {
        throw UTSError("配置列表响应解析失败")
    }
    var paginationObject: UTSJSONObject? = null
    val rawPagination = rawObject["pagination"]
    if (rawPagination != null) {
        val paginationText = JSON.stringify(rawPagination)
        if (paginationText != null && paginationText != "") {
            paginationObject = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(paginationText), " at pkg/api/modules/products.uts:777")
        }
    }
    var results: UTSArray<UTSJSONObject> = _uA()
    val rawResults = rawObject["results"]
    if (rawResults != null) {
        results = parseObjectArray(rawResults)
    }
    var totalCount = intValue__2(rawObject["count"])
    if (totalCount <= 0) {
        totalCount = intValue__2(rawObject["total"])
    }
    if (totalCount <= 0) {
        totalCount = intValue__2(rawObject["total_count"])
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
    var currentPage = intValue__2(rawObject["page"])
    if (currentPage <= 0) {
        currentPage = intValue__2(rawObject["current_page"])
    }
    if (currentPage <= 0 && paginationObject != null) {
        currentPage = intValue__2(paginationObject["page"])
    }
    if (currentPage <= 0) {
        currentPage = page
    }
    var resolvedPageSize = intValue__2(rawObject["page_size"])
    if (resolvedPageSize <= 0) {
        resolvedPageSize = intValue__2(rawObject["per_page"])
    }
    if (resolvedPageSize <= 0 && paginationObject != null) {
        resolvedPageSize = intValue__2(paginationObject["page_size"])
    }
    if (resolvedPageSize <= 0) {
        resolvedPageSize = pageSize
    }
    var totalPages = intValue__2(rawObject["total_pages"])
    if (totalPages <= 0) {
        totalPages = intValue__2(rawObject["pages"])
    }
    if (totalPages <= 0) {
        totalPages = intValue__2(rawObject["num_pages"])
    }
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__2(paginationObject["total_pages"])
    }
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__2(paginationObject["pages"])
    }
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__2(paginationObject["num_pages"])
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
    val body: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("body", "pkg/api/modules/products.uts", 835, 11), "name_cn" to data.name_cn, "purchase_price" to data.purchase_price, "net_purchase_price" to data.net_purchase_price, "cost_price" to data.cost_price, "base_sales_price" to data.base_sales_price, "status" to data.status, "is_featured" to data.is_featured, "is_new" to data.is_new, "is_bestseller" to data.is_bestseller, "sort_order" to data.sort_order)
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
fun batchPreviewProductPricingFormulas(data: ProductPricingFormulaBatchPreviewRequest): UTSPromise<ProductPricingFormulaBatchPreviewResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(productPricingFormulasBasePath + "batch-preview/", "POST", _uO("purchase_price" to data.purchase_price, "net_purchase_price" to data.net_purchase_price, "cost_price" to data.cost_price, "base_sales_price" to data.base_sales_price, "formula_ids" to data.formula_ids, "profitable_only" to data.profitable_only), true))
            return@w buildBatchPreviewResponse(raw)
    })
}
fun getProductConfigList(basePath: String, search: String?, page: Number, pageSize: Number, extra: UTSJSONObject = _uO()): UTSPromise<UTSJSONObject> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(basePath, "GET", buildConfigListQuery(search, page, pageSize, extra), true))
            return@w buildConfigListResponse(raw, page, pageSize)
    })
}
fun getAvailableProductDiscountsForProduct(productId: Any, search: String?, page: Number, pageSize: Number, extra: UTSJSONObject = _uO()): UTSPromise<UTSJSONObject> {
    return wrapUTSPromise(suspend w@{
            val query: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("query", "pkg/api/modules/products.uts", 928, 11), "product" to productId, "page" to page, "page_size" to pageSize)
            if (search != null && search != "") {
                query["search"] = search
            }
            val status = stringValue__2(extra["status"])
            if (status != "") {
                query["status"] = status
            }
            val discountType = stringValue__2(extra["discount_type"])
            if (discountType != "") {
                query["discount_type"] = discountType
            }
            val shop = stringValue__2(extra["shop"])
            if (shop != "") {
                query["shop"] = shop
            }
            val raw = await(request(productDiscountsBasePath + "available-for-product/", "GET", query, true))
            return@w buildConfigListResponse(raw, page, pageSize)
    })
}
fun addProductDiscountToProduct(productId: Any, discountId: Any): UTSPromise<UTSJSONObject> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(productDiscountsBasePath + stringValue__2(discountId) + "/add-product/", "POST", _uO("product" to stringValue__2(productId)), true))
            val parsed = parseObject__1(raw)
            if (parsed == null) {
                throw UTSError("绑定折扣响应解析失败")
            }
            return@w parsed!!
    })
}
fun removeProductDiscountFromProduct(productId: Any, discountId: Any): UTSPromise<UTSJSONObject> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(productDiscountsBasePath + stringValue__2(discountId) + "/remove-product/", "POST", _uO("product" to stringValue__2(productId)), true))
            val parsed = parseObject__1(raw)
            if (parsed == null) {
                throw UTSError("移除折扣响应解析失败")
            }
            return@w parsed!!
    })
}
fun parseProductDiscountInfo(value: Any?): ProductDiscountInfo? {
    if (value == null) {
        return null
    }
    val source = parseObject__1(value)
    if (source == null) {
        return null
    }
    val discountIdValue = source["discount_id"]
    var discountId: Number? = null
    if (discountIdValue != null) {
        val parsedDiscountId = parseInt("" + discountIdValue)
        if (!isNaN(parsedDiscountId)) {
            discountId = parsedDiscountId
        }
    }
    return ProductDiscountInfo(has_discount = booleanValue(source["has_discount"]), discount_id = discountId, discount_name = stringValue__2(source["discount_name"]), discount_type = stringValue__2(source["discount_type"]), original_price = stringValue__2(source["original_price"]), final_price = stringValue__2(source["final_price"]), discount_amount = stringValue__2(source["discount_amount"]), discount_percentage = stringValue__2(source["discount_percentage"]), discount_amount_fixed = stringValue__2(source["discount_amount_fixed"]), min_quantity = intValue__2(source["min_quantity"]))
}
fun getProductConfigDetail(basePath: String, id: Any): UTSPromise<UTSJSONObject> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(resourceDetailPath(basePath, id), "GET", _uO(), true))
            val parsed = parseObject__1(raw)
            if (parsed == null) {
                throw UTSError("配置详情响应解析失败")
            }
            return@w parsed!!
    })
}
fun createProductConfig(basePath: String, data: UTSJSONObject): UTSPromise<UTSJSONObject> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(basePath, "POST", data, true))
            val parsed = parseObject__1(raw)
            if (parsed == null) {
                throw UTSError("配置创建响应解析失败")
            }
            return@w parsed!!
    })
}
fun updateProductConfig(basePath: String, id: Any, data: UTSJSONObject): UTSPromise<UTSJSONObject> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(resourceDetailPath(basePath, id), "PUT", data, true))
            val parsed = parseObject__1(raw)
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
fun parsePrintTemplateObject(value: Any?): UTSJSONObject? {
    if (value == null) {
        return null
    }
    val text = JSON.stringify(value)
    if (text == null || text == "") {
        return null
    }
    return UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(text), " at pkg/api/modules/products.uts:1045")
}
fun parsePrintTemplateArray(value: Any?): UTSArray<PrintTemplateItem> {
    if (value == null) {
        return _uA<PrintTemplateItem>()
    }
    val text = JSON.stringify(value)
    if (text == null || text == "") {
        return _uA<PrintTemplateItem>()
    }
    val parsed = UTSAndroid.consoleDebugError(JSON.parseArray<PrintTemplateItem>(text), " at pkg/api/modules/products.uts:1053")
    if (parsed == null) {
        return _uA<PrintTemplateItem>()
    }
    return parsed!!
}
fun parsePrintTemplateItem(value: Any?): PrintTemplateItem {
    val text = JSON.stringify(value)
    val parsed = if (text == null || text == "") {
        null
    } else {
        UTSAndroid.consoleDebugError(JSON.parseObject<PrintTemplateItem>(text), " at pkg/api/modules/products.uts:1060")
    }
    if (parsed == null) {
        throw UTSError("标签模板响应解析失败")
    }
    return parsed!!
}
fun printTemplateIntValue(value: Any?, fallback: Number): Number {
    if (value == null) {
        return fallback
    }
    val parsed = parseInt("" + value)
    if (isNaN(parsed)) {
        return fallback
    }
    return parsed
}
fun buildPrintTemplateListResponse(raw: Any, query: PrintTemplateListQuery): PrintTemplateListResponse {
    val rawObject = parsePrintTemplateObject(raw)
    if (rawObject == null) {
        throw UTSError("标签模板列表响应解析失败")
    }
    var paginationObject: UTSJSONObject? = null
    val rawPagination = rawObject!!["pagination"]
    if (rawPagination != null) {
        paginationObject = parsePrintTemplateObject(rawPagination)
    }
    val results = parsePrintTemplateArray(rawObject!!["results"])
    var totalCount = printTemplateIntValue(rawObject!!["count"], 0)
    if (totalCount <= 0) {
        totalCount = printTemplateIntValue(rawObject!!["total_count"], 0)
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = printTemplateIntValue(paginationObject!!["total"], 0)
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = printTemplateIntValue(paginationObject!!["count"], 0)
    }
    if (totalCount <= 0) {
        totalCount = results.length
    }
    var currentPage = printTemplateIntValue(rawObject!!["current_page"], 0)
    if (currentPage <= 0) {
        currentPage = printTemplateIntValue(rawObject!!["page"], 0)
    }
    if (currentPage <= 0 && paginationObject != null) {
        currentPage = printTemplateIntValue(paginationObject!!["page"], 0)
    }
    if (currentPage <= 0) {
        currentPage = query.page
    }
    var pageSize = printTemplateIntValue(rawObject!!["page_size"], 0)
    if (pageSize <= 0 && paginationObject != null) {
        pageSize = printTemplateIntValue(paginationObject!!["page_size"], 0)
    }
    if (pageSize <= 0) {
        pageSize = query.page_size
    }
    var totalPages = printTemplateIntValue(rawObject!!["total_pages"], 0)
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = printTemplateIntValue(paginationObject!!["total_pages"], 0)
    }
    if (totalPages <= 0 && pageSize > 0) {
        totalPages = Math.ceil(totalCount / pageSize)
    }
    if (totalPages <= 0) {
        totalPages = 1
    }
    return PrintTemplateListResponse(results = results, count = totalCount, total_count = totalCount, total_pages = totalPages, current_page = currentPage, page_size = pageSize)
}
fun getPrintTemplateList(data: PrintTemplateListQuery): UTSPromise<PrintTemplateListResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/products/print-templates/", "GET", buildPrintTemplateListQuery(data), true))
            return@w buildPrintTemplateListResponse(raw, data)
    })
}
fun getPrintTemplateDetail(id: String): UTSPromise<PrintTemplateItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/products/print-templates/" + id + "/", "GET", _uO(), true))
            return@w parsePrintTemplateItem(raw)
    })
}
fun buildPrintTemplateMutationBody(data: PrintTemplateMutationData): UTSJSONObject {
    val body: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("body", "pkg/api/modules/products.uts", 1128, 11))
    body["name"] = data.name
    body["template_type"] = data.template_type
    body["printer_language"] = data.printer_language
    body["paper_width_mm"] = data.paper_width_mm
    body["paper_height_mm"] = data.paper_height_mm
    body["dots_per_mm"] = data.dots_per_mm
    body["copies_default"] = data.copies_default
    body["elements"] = data.elements
    body["is_default"] = data.is_default
    body["is_active"] = data.is_active
    body["description"] = data.description
    return body
}
fun createPrintTemplate(data: PrintTemplateMutationData): UTSPromise<Any> {
    return request("/api/products/print-templates/", "POST", buildPrintTemplateMutationBody(data), true)
}
fun updatePrintTemplate(id: String, data: PrintTemplateMutationData): UTSPromise<Any> {
    return request("/api/products/print-templates/" + id + "/", "PUT", buildPrintTemplateMutationBody(data), true)
}
fun setDefaultPrintTemplate(id: String): UTSPromise<Any> {
    return request("/api/products/print-templates/" + id + "/set-default/", "POST", _uO(), true)
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
open class MediaShareResponse (
    @JsonNotNull
    open var url: String,
    @JsonNotNull
    open var signed_url: String,
    @JsonNotNull
    open var signed_thumbnail_url: String,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("MediaShareResponse", "pkg/api/modules/media.uts", 37, 13)
    }
}
open class MediaBatchUploadItem (
    @JsonNotNull
    open var filePath: String,
    open var name: String? = null,
    open var formData: UTSJSONObject? = null,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("MediaBatchUploadItem", "pkg/api/modules/media.uts", 45, 13)
    }
}
open class MediaBatchUploadResult (
    @JsonNotNull
    open var successItems: UTSArray<UTSJSONObject>,
    @JsonNotNull
    open var failItems: UTSArray<UTSJSONObject>,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("MediaBatchUploadResult", "pkg/api/modules/media.uts", 50, 13)
    }
}
fun stringValue__3(value: Any?): String {
    if (value == null) {
        return ""
    }
    return "" + value
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
fun buildMediaShareResponse(raw: Any): MediaShareResponse {
    val rawText = JSON.stringify(raw)
    val rawObject = if (rawText == null || rawText == "") {
        null
    } else {
        UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(rawText), " at pkg/api/modules/media.uts:226")
    }
    if (rawObject == null) {
        throw UTSError("媒体分享链接解析失败")
    }
    var signedUrl = normalizeServerUrl__1(stringValue__3(rawObject!!["signed_url"]))
    if (signedUrl == "") {
        signedUrl = normalizeServerUrl__1(stringValue__3(rawObject!!["url"]))
    }
    return MediaShareResponse(url = signedUrl, signed_url = signedUrl, signed_thumbnail_url = normalizeServerUrl__1(stringValue__3(rawObject!!["signed_thumbnail_url"])))
}
fun mediaFilePath(id: Any): String {
    return "/api/media/files/" + stringValue__3(id) + "/"
}
fun buildUploadHeaders(): UTSJSONObject {
    val headers: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("headers", "pkg/api/modules/media.uts", 254, 11))
    if (authState.token != "") {
        headers["Authorization"] = authState.token
    }
    return headers
}
fun parseResponseErrorMessage(text: String): String {
    if (text == "") {
        return ""
    }
    val rootObject = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(text), " at pkg/api/modules/media.uts:304")
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
    val target: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("target", "pkg/api/modules/media.uts", 319, 11))
    for(key in resolveUTSKeyIterator(source)){
        target[key] = source[key]
    }
    return target
}
fun buildBatchUploadFormData(items: UTSArray<MediaBatchUploadItem>): UTSJSONObject {
    val result: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("result", "pkg/api/modules/media.uts", 326, 11))
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
    val rootObject = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(text), " at pkg/api/modules/media.uts:373")
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
        return UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(text), " at pkg/api/modules/media.uts:396")
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
        return UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(text), " at pkg/api/modules/media.uts:407")
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
        val rawObject = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(rawText), " at pkg/api/modules/media.uts:478")
        if (rawObject != null) {
            val causeValue = rawObject!!["cause"]
            if (causeValue != null) {
                val causeText = JSON.stringify(causeValue)
                if (causeText != null && causeText != "") {
                    val causeObject = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(causeText), " at pkg/api/modules/media.uts:484")
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
        console.log("media batch upload start:", baseUrl + "/api/media/files/batch-upload/", files.length, " at pkg/api/modules/media.uts:551")
        try {
            uni_uploadFile(UploadFileOptions(url = baseUrl + "/api/media/files/batch-upload/", files = files, header = headers, formData = formData, timeout = uploadTimeout, success = fun(res: UploadFileSuccess){
                console.log("media batch upload success:", res.statusCode, items.length, " at pkg/api/modules/media.uts:561")
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
                console.log("media batch upload fail:", failMessage, err.errCode, " at pkg/api/modules/media.uts:575")
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
        console.log("请求地址:", requestUrl, " at pkg/api/modules/media.uts:632")
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
fun getMediaFileShare(id: Any): UTSPromise<MediaShareResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(mediaFilePath(id) + "share/", "GET", _uO(), true))
            return@w buildMediaShareResponse(raw)
    })
}
fun batchUploadMediaFiles(items: UTSArray<MediaBatchUploadItem>): UTSPromise<MediaBatchUploadResult> {
    return wrapUTSPromise(suspend w@{
            val successItems: UTSArray<UTSJSONObject> = _uA()
            val failItems: UTSArray<UTSJSONObject> = _uA()
            console.log("media batch upload count:", items.length, " at pkg/api/modules/media.uts:717")
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
    open var image: String,
    @JsonNotNull
    open var item: UTSJSONObject,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SelectChangePayload", "uni_modules/lili_bottom-select/components/lili_bottom-select/lili_bottom-select.uvue", 184, 6)
    }
}
open class MultiSelectChangePayload (
    @JsonNotNull
    open var values: UTSArray<String>,
    @JsonNotNull
    open var texts: UTSArray<String>,
    @JsonNotNull
    open var items: UTSArray<UTSJSONObject>,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("MultiSelectChangePayload", "uni_modules/lili_bottom-select/components/lili_bottom-select/lili_bottom-select.uvue", 191, 6)
    }
}
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("TreeDisplayRow", "uni_modules/lili_bottom-select/components/lili_bottom-select/lili_bottom-select.uvue", 197, 6)
    }
}
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ProductSelectOption", "pages/tabbar/products.uvue", 170, 6)
    }
}
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("MenuItem", "pages/tabbar/settings.uvue", 42, 6)
    }
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
    open var items: UTSArray<MenuItem>,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("MenuGroup", "pages/tabbar/settings.uvue", 51, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return MenuGroupReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class MenuGroupReactiveObject : MenuGroup, IUTSReactive<MenuGroup> {
    override var __v_raw: MenuGroup
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: MenuGroup, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(label = __v_raw.label, items = __v_raw.items) {
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
    override var items: UTSArray<MenuItem>
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("MenuItem", "pages/tabbar/mine.uvue", 48, 6)
    }
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("StoreListItem", "uni_modules/uni-upgrade-center-app/utils/call-check-version.uts", 1, 13)
    }
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("UniUpgradeCenterResult", "uni_modules/uni-upgrade-center-app/utils/call-check-version.uts", 8, 13)
    }
}
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SupplierListQuery", "pkg/api/modules/suppliers.uts", 2, 13)
    }
}
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SupplierMediaFile", "pkg/api/modules/suppliers.uts", 9, 13)
    }
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SupplierItem", "pkg/api/modules/suppliers.uts", 27, 13)
    }
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SupplierListResponse", "pkg/api/modules/suppliers.uts", 46, 13)
    }
}
open class SupplierFilterOption (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var label: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SupplierFilterOption", "pkg/api/modules/suppliers.uts", 54, 13)
    }
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SupplierFilterDefinition", "pkg/api/modules/suppliers.uts", 58, 13)
    }
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SupplierFilterOptionsResponse", "pkg/api/modules/suppliers.uts", 67, 13)
    }
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SupplierGlobalStatisticsResponse", "pkg/api/modules/suppliers.uts", 77, 13)
    }
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SupplierMutationData", "pkg/api/modules/suppliers.uts", 80, 13)
    }
}
open class SupplierBatchActionResponse (
    @JsonNotNull
    open var success: Boolean = false,
    @JsonNotNull
    open var message: String,
    @JsonNotNull
    open var data: UTSJSONObject,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SupplierBatchActionResponse", "pkg/api/modules/suppliers.uts", 90, 13)
    }
}
fun buildListQuery__1(data: SupplierListQuery): UTSJSONObject {
    val query: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("query", "pkg/api/modules/suppliers.uts", 96, 11), "page" to data.page, "page_size" to data.page_size)
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
            mediaFile.file_url = normalizeServerUrl__2(mediaFile.file_url)
            mediaFile.thumbnail_url = normalizeServerUrl__2(mediaFile.thumbnail_url)
            mediaFile.signed_url = normalizeServerUrl__2(mediaFile.signed_url)
            mediaFile.signed_thumbnail_url = normalizeServerUrl__2(mediaFile.signed_thumbnail_url)
            mediaIndex += 1
        }
    }
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
fun buildSupplierListResponse(raw: Any, query: SupplierListQuery): SupplierListResponse {
    val rawText = JSON.stringify(raw)
    val rawObject = if (rawText == null || rawText == "") {
        null
    } else {
        UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(rawText), " at pkg/api/modules/suppliers.uts:171")
    }
    if (rawObject == null) {
        throw UTSError("供应商列表响应解析失败")
    }
    var paginationObject: UTSJSONObject? = null
    val rawPagination = rawObject!!["pagination"]
    if (rawPagination != null) {
        val paginationText = JSON.stringify(rawPagination)
        if (paginationText != null && paginationText != "") {
            paginationObject = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(paginationText), " at pkg/api/modules/suppliers.uts:180")
        }
    }
    var results: UTSArray<SupplierItem> = _uA()
    val rawResults = rawObject!!["results"]
    if (rawResults != null) {
        val resultText = JSON.stringify(rawResults)
        val parsedResults = if (resultText == null || resultText == "") {
            null
        } else {
            UTSAndroid.consoleDebugError(JSON.parseArray<SupplierItem>(resultText), " at pkg/api/modules/suppliers.uts:187")
        }
        if (parsedResults != null) {
            results = parsedResults!!
        }
    }
    var totalCount = intValue__3(rawObject!!["count"])
    if (totalCount <= 0) {
        totalCount = intValue__3(rawObject!!["total"])
    }
    if (totalCount <= 0) {
        totalCount = intValue__3(rawObject!!["total_count"])
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
    var currentPage = intValue__3(rawObject!!["page"])
    if (currentPage <= 0) {
        currentPage = intValue__3(rawObject!!["current_page"])
    }
    if (currentPage <= 0 && paginationObject != null) {
        currentPage = intValue__3(paginationObject["page"])
    }
    if (currentPage <= 0) {
        currentPage = query.page
    }
    var pageSize = intValue__3(rawObject!!["page_size"])
    if (pageSize <= 0) {
        pageSize = intValue__3(rawObject!!["per_page"])
    }
    if (pageSize <= 0 && paginationObject != null) {
        pageSize = intValue__3(paginationObject["page_size"])
    }
    if (pageSize <= 0 && paginationObject != null) {
        pageSize = intValue__3(paginationObject["per_page"])
    }
    if (pageSize <= 0) {
        pageSize = query.page_size
    }
    var totalPages = intValue__3(rawObject!!["total_pages"])
    if (totalPages <= 0) {
        totalPages = intValue__3(rawObject!!["num_pages"])
    }
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__3(paginationObject["total_pages"])
    }
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__3(paginationObject["num_pages"])
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
        UTSAndroid.consoleDebugError(JSON.parseArray<Any>(text), " at pkg/api/modules/suppliers.uts:261")
    }
    if (parsed == null) {
        return _uA()
    }
    val result: UTSArray<String> = _uA()
    run {
        var index: Number = 0
        while(index < parsed!!.length){
            result.push(stringValue__4(parsed!![index]))
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
        UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(rawText), " at pkg/api/modules/suppliers.uts:273")
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
            UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(filtersText), " at pkg/api/modules/suppliers.uts:281")
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
                            UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(optionsText), " at pkg/api/modules/suppliers.uts:290")
                        }
                        if (optionObjects != null) {
                            val nextOptions: UTSArray<SupplierFilterOption> = _uA()
                            run {
                                var optionIndex: Number = 0
                                while(optionIndex < optionObjects!!.length){
                                    val optionObject = optionObjects!![optionIndex]
                                    nextOptions.push(SupplierFilterOption(value = stringValue__4(optionObject["value"]), label = stringValue__4(optionObject["label"])))
                                    optionIndex += 1
                                }
                            }
                            options = nextOptions
                        }
                    }
                    nextFilters.push(SupplierFilterDefinition(key = stringValue__4(filterObject["key"]), param = stringValue__4(filterObject["param"]), label = stringValue__4(filterObject["label"]), control = stringValue__4(filterObject["control"]), aliases = stringArrayValue__1(filterObject["aliases"]), multiple = stringValue__4(filterObject["multiple"]) == "true", options = options))
                    filterIndex += 1
                }
            }
            filters = nextFilters
        }
    }
    return SupplierFilterOptionsResponse(resource = stringValue__4(rawObject!!["resource"]), count = intValue__3(rawObject!!["count"]), filters = filters)
}
fun buildSupplierMediaFileFromObject(rawObject: UTSJSONObject): SupplierMediaFile {
    return SupplierMediaFile(id = stringValue__4(rawObject["id"]), company = intValue__3(rawObject["company"]), original_filename = stringValue__4(rawObject["original_filename"]), file_type = stringValue__4(rawObject["file_type"]), file_type_display = stringValue__4(rawObject["file_type_display"]), mime_type = stringValue__4(rawObject["mime_type"]), file_size = intValue__3(rawObject["file_size"]), file_size_display = stringValue__4(rawObject["file_size_display"]), file_url = normalizeServerUrl__2(stringValue__4(rawObject["file_url"])), thumbnail_url = normalizeServerUrl__2(stringValue__4(rawObject["thumbnail_url"])), signed_url = normalizeServerUrl__2(stringValue__4(rawObject["signed_url"])), signed_thumbnail_url = normalizeServerUrl__2(stringValue__4(rawObject["signed_thumbnail_url"])), object_id = stringValue__4(rawObject["object_id"]), is_deleted = stringValue__4(rawObject["is_deleted"]) == "true", created_at = stringValue__4(rawObject["created_at"]), updated_at = stringValue__4(rawObject["updated_at"]))
}
fun buildSupplierMediaFilesFromValue(value: Any?): UTSArray<SupplierMediaFile> {
    if (value == null) {
        return _uA()
    }
    val text = JSON.stringify(value)
    val rawArray = if (text == null || text == "") {
        null
    } else {
        UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(text), " at pkg/api/modules/suppliers.uts:347")
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
        UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(rawText), " at pkg/api/modules/suppliers.uts:359")
    }
    if (rawObject == null) {
        throw UTSError("供应商详情响应解析失败")
    }
    return SupplierItem(id = intValue__3(rawObject!!["id"]), code = stringValue__4(rawObject!!["code"]), name = stringValue__4(rawObject!!["name"]), address = stringValue__4(rawObject!!["address"]), phone = stringValue__4(rawObject!!["phone"]), contact = stringValue__4(rawObject!!["contact"]), description = if (rawObject!!["description"] == null) {
        null
    } else {
        stringValue__4(rawObject!!["description"])
    }
    , total_amount = stringValue__4(rawObject!!["total_amount"]), arrears_amount = stringValue__4(rawObject!!["arrears_amount"]), paid_amount = intValue__3(rawObject!!["paid_amount"]), is_active = stringValue__4(rawObject!!["is_active"]) == "true", files_count = intValue__3(rawObject!!["files_count"]), company_infos = (fun(): UTSArray<UTSJSONObject> {
        val companyInfosValue = rawObject!!["company_infos"]
        if (companyInfosValue == null) {
            return _uA<UTSJSONObject>()
        }
        val companyInfosText = JSON.stringify(companyInfosValue)
        val companyInfosArray = if (companyInfosText == null || companyInfosText == "") {
            null
        } else {
            UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(companyInfosText), " at pkg/api/modules/suppliers.uts:382")
        }
        if (companyInfosArray == null) {
            return _uA<UTSJSONObject>()
        }
        return companyInfosArray!!
    }
    )(), is_deleted = stringValue__4(rawObject!!["is_deleted"]) == "true", created_at = stringValue__4(rawObject!!["created_at"]), updated_at = stringValue__4(rawObject!!["updated_at"]), media_files = buildSupplierMediaFilesFromValue(rawObject!!["media_files"]))
}
fun buildSupplierGlobalStatisticsResponse(raw: Any): SupplierGlobalStatisticsResponse {
    val rawText = JSON.stringify(raw)
    val rawObject = if (rawText == null || rawText == "") {
        null
    } else {
        UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(rawText), " at pkg/api/modules/suppliers.uts:411")
    }
    if (rawObject == null) {
        throw UTSError("供应商全局统计解析失败")
    }
    return SupplierGlobalStatisticsResponse(data = rawObject!!)
}
fun buildSupplierMutationBody(data: SupplierMutationData): UTSJSONObject {
    val body: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("body", "pkg/api/modules/suppliers.uts", 420, 11), "name" to data.name)
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
    return "/api/procurement/suppliers/" + stringValue__4(id) + "/"
}
fun buildBatchActionBody(ids: UTSArray<String>, remark: String? = null): UTSJSONObject {
    val nextIds: UTSArray<Any> = _uA()
    run {
        var index: Number = 0
        while(index < ids.length){
            val text = stringValue__4(ids[index])
            val parsed = parseInt(text)
            if (!isNaN(parsed) && "" + parsed == text) {
                nextIds.push(parsed)
            } else {
                nextIds.push(text)
            }
            index += 1
        }
    }
    val body: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("body", "pkg/api/modules/suppliers.uts", 461, 11), "ids" to nextIds)
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
        UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(rawText), " at pkg/api/modules/suppliers.uts:474")
    }
    if (rawObject == null) {
        return SupplierBatchActionResponse(success = true, message = "操作成功", data = _uO())
    }
    return SupplierBatchActionResponse(success = true, message = stringValue__4(rawObject["message"]), data = rawObject)
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
val GenUniModulesLiliPrintCopiesStepperComponentsLiliPrintCopiesStepperLiliPrintCopiesStepperClass = CreateVueComponent(GenUniModulesLiliPrintCopiesStepperComponentsLiliPrintCopiesStepperLiliPrintCopiesStepper::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "component", name = "", inheritAttrs = GenUniModulesLiliPrintCopiesStepperComponentsLiliPrintCopiesStepperLiliPrintCopiesStepper.inheritAttrs, inject = GenUniModulesLiliPrintCopiesStepperComponentsLiliPrintCopiesStepperLiliPrintCopiesStepper.inject, props = GenUniModulesLiliPrintCopiesStepperComponentsLiliPrintCopiesStepperLiliPrintCopiesStepper.props, propsNeedCastKeys = GenUniModulesLiliPrintCopiesStepperComponentsLiliPrintCopiesStepperLiliPrintCopiesStepper.propsNeedCastKeys, emits = GenUniModulesLiliPrintCopiesStepperComponentsLiliPrintCopiesStepperLiliPrintCopiesStepper.emits, components = GenUniModulesLiliPrintCopiesStepperComponentsLiliPrintCopiesStepperLiliPrintCopiesStepper.components, styles = GenUniModulesLiliPrintCopiesStepperComponentsLiliPrintCopiesStepperLiliPrintCopiesStepper.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenUniModulesLiliPrintCopiesStepperComponentsLiliPrintCopiesStepperLiliPrintCopiesStepper.setup(props as GenUniModulesLiliPrintCopiesStepperComponentsLiliPrintCopiesStepperLiliPrintCopiesStepper)
    }
    )
}
, fun(instance, renderer): GenUniModulesLiliPrintCopiesStepperComponentsLiliPrintCopiesStepperLiliPrintCopiesStepper {
    return GenUniModulesLiliPrintCopiesStepperComponentsLiliPrintCopiesStepperLiliPrintCopiesStepper(instance)
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
open class LiliAsyncGuard : IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("LiliAsyncGuard", "uni_modules/lili-async-guard/index.uts", 1, 14)
    }
    open var alive: Boolean = true
    open var leaving: Boolean = false
    open var currentToken: Number = 0
    open fun begin(): Number {
        this.currentToken = this.currentToken + 1
        this.alive = true
        this.leaving = false
        return this.currentToken
    }
    open fun canApply(token: Number): Boolean {
        return this.alive && !this.leaving && this.currentToken == token
    }
    open fun leave() {
        this.alive = false
        this.leaving = true
        this.currentToken = this.currentToken + 1
    }
    open fun reset() {
        this.alive = true
        this.leaving = false
        this.currentToken = this.currentToken + 1
    }
}
fun createAsyncGuard(): LiliAsyncGuard {
    return LiliAsyncGuard()
}
open class SelectOption (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var text: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SelectOption", "pages/suppliers/from.uvue", 50, 6)
    }
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("TransactionListQuery", "pkg/api/modules/transactions.uts", 2, 13)
    }
}
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("TransactionMediaFile", "pkg/api/modules/transactions.uts", 18, 13)
    }
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("TransactionItem", "pkg/api/modules/transactions.uts", 36, 13)
    }
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
    open var total_paid: String,
    @JsonNotNull
    open var net_amount: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("TransactionSummary", "pkg/api/modules/transactions.uts", 51, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return TransactionSummaryReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class TransactionSummaryReactiveObject : TransactionSummary, IUTSReactive<TransactionSummary> {
    override var __v_raw: TransactionSummary
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: TransactionSummary, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(purchase_amount = __v_raw.purchase_amount, arrears_amount = __v_raw.arrears_amount, payment_amount = __v_raw.payment_amount, total_paid = __v_raw.total_paid, net_amount = __v_raw.net_amount) {
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
    override var total_paid: String
        get() {
            return _tRG(__v_raw, "total_paid", __v_raw.total_paid, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("total_paid")) {
                return
            }
            val oldValue = __v_raw.total_paid
            __v_raw.total_paid = value
            _tRS(__v_raw, "total_paid", oldValue, value)
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("TransactionListResponse", "pkg/api/modules/transactions.uts", 58, 13)
    }
}
open class TransactionFilterOption (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var label: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("TransactionFilterOption", "pkg/api/modules/transactions.uts", 67, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return TransactionFilterOptionReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class TransactionFilterOptionReactiveObject : TransactionFilterOption, IUTSReactive<TransactionFilterOption> {
    override var __v_raw: TransactionFilterOption
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: TransactionFilterOption, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(value = __v_raw.value, label = __v_raw.label) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): TransactionFilterOptionReactiveObject {
        return TransactionFilterOptionReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
open class TransactionFilterDefinition (
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
    open var options: UTSArray<TransactionFilterOption>,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("TransactionFilterDefinition", "pkg/api/modules/transactions.uts", 71, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return TransactionFilterDefinitionReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class TransactionFilterDefinitionReactiveObject : TransactionFilterDefinition, IUTSReactive<TransactionFilterDefinition> {
    override var __v_raw: TransactionFilterDefinition
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: TransactionFilterDefinition, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(key = __v_raw.key, param = __v_raw.param, label = __v_raw.label, control = __v_raw.control, aliases = __v_raw.aliases, multiple = __v_raw.multiple, options = __v_raw.options) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): TransactionFilterDefinitionReactiveObject {
        return TransactionFilterDefinitionReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
    override var options: UTSArray<TransactionFilterOption>
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
open class TransactionFilterOptionsResponse (
    @JsonNotNull
    open var resource: String,
    @JsonNotNull
    open var count: Number,
    @JsonNotNull
    open var filters: UTSArray<TransactionFilterDefinition>,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("TransactionFilterOptionsResponse", "pkg/api/modules/transactions.uts", 80, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return TransactionFilterOptionsResponseReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class TransactionFilterOptionsResponseReactiveObject : TransactionFilterOptionsResponse, IUTSReactive<TransactionFilterOptionsResponse> {
    override var __v_raw: TransactionFilterOptionsResponse
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: TransactionFilterOptionsResponse, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(resource = __v_raw.resource, count = __v_raw.count, filters = __v_raw.filters) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): TransactionFilterOptionsResponseReactiveObject {
        return TransactionFilterOptionsResponseReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
    override var filters: UTSArray<TransactionFilterDefinition>
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
open class TransactionOptionItem (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var label: String,
    @JsonNotNull
    open var extra: UTSJSONObject,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("TransactionOptionItem", "pkg/api/modules/transactions.uts", 85, 13)
    }
}
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("TransactionOptionGroup", "pkg/api/modules/transactions.uts", 90, 13)
    }
}
open class TransactionOptionsResponse (
    @JsonNotNull
    open var resource: String,
    @JsonNotNull
    open var total_groups: Number,
    @JsonNotNull
    open var groups: UTSArray<TransactionOptionGroup>,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("TransactionOptionsResponse", "pkg/api/modules/transactions.uts", 97, 13)
    }
}
open class TransactionStatisticsResponse (
    @JsonNotNull
    open var data: UTSJSONObject,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("TransactionStatisticsResponse", "pkg/api/modules/transactions.uts", 102, 13)
    }
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("TransactionMutationData", "pkg/api/modules/transactions.uts", 105, 13)
    }
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
fun intValue__4(value: Any?): Number {
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
fun stringValue__5(value: Any?): String {
    if (value == null) {
        return ""
    }
    return "" + value
}
fun booleanValue__1(value: Any?): Boolean {
    return stringValue__5(value) == "true"
}
fun stringArrayValue__2(value: Any?): UTSArray<String> {
    if (value == null) {
        return _uA()
    }
    val text = JSON.stringify(value)
    val parsed = if (text == null || text == "") {
        null
    } else {
        UTSAndroid.consoleDebugError(JSON.parseArray<Any>(text), " at pkg/api/modules/transactions.uts:159")
    }
    if (parsed == null) {
        return _uA()
    }
    val result: UTSArray<String> = _uA()
    run {
        var index: Number = 0
        while(index < parsed!!.length){
            result.push(stringValue__5(parsed!![index]))
            index += 1
        }
    }
    return result
}
fun buildTransactionListQuery(data: TransactionListQuery): UTSJSONObject {
    val query: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("query", "pkg/api/modules/transactions.uts", 170, 11), "page" to data.page, "page_size" to data.page_size)
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
    return TransactionMediaFile(id = stringValue__5(rawObject["id"]), company = intValue__4(rawObject["company"]), original_filename = stringValue__5(rawObject["original_filename"]), file_type = stringValue__5(rawObject["file_type"]), file_type_display = stringValue__5(rawObject["file_type_display"]), mime_type = stringValue__5(rawObject["mime_type"]), file_size = intValue__4(rawObject["file_size"]), file_size_display = stringValue__5(rawObject["file_size_display"]), file_url = normalizeServerUrl__3(stringValue__5(rawObject["file_url"])), thumbnail_url = normalizeServerUrl__3(stringValue__5(rawObject["thumbnail_url"])), signed_url = normalizeServerUrl__3(stringValue__5(rawObject["signed_url"])), signed_thumbnail_url = normalizeServerUrl__3(stringValue__5(rawObject["signed_thumbnail_url"])), object_id = stringValue__5(rawObject["object_id"]), is_deleted = booleanValue__1(rawObject["is_deleted"]), created_at = stringValue__5(rawObject["created_at"]), updated_at = stringValue__5(rawObject["updated_at"]))
}
fun buildTransactionMediaFilesFromValue(value: Any?): UTSArray<TransactionMediaFile> {
    if (value == null) {
        return _uA()
    }
    val text = JSON.stringify(value)
    val rawArray = if (text == null || text == "") {
        null
    } else {
        UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(text), " at pkg/api/modules/transactions.uts:237")
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
    return TransactionItem(id = intValue__4(rawObject["id"]), supplier = intValue__4(rawObject["supplier"]), supplier_name = stringValue__5(rawObject["supplier_name"]), transaction_type = intValue__4(rawObject["transaction_type"]), transaction_type_display = stringValue__5(rawObject["transaction_type_display"]), amount = stringValue__5(rawObject["amount"]), transaction_date = stringValue__5(rawObject["transaction_date"]), transaction_number = stringValue__5(rawObject["transaction_number"]), note = if (rawObject["note"] == null) {
        null
    } else {
        stringValue__5(rawObject["note"])
    }
    , media_files = buildTransactionMediaFilesFromValue(rawObject["media_files"]), files_count = intValue__4(rawObject["files_count"]), created_at = stringValue__5(rawObject["created_at"]), updated_at = stringValue__5(rawObject["updated_at"]))
}
fun buildTransactionSummary(value: Any?): TransactionSummary? {
    if (value == null) {
        return null
    }
    val text = JSON.stringify(value)
    val rawObject = if (text == null || text == "") {
        null
    } else {
        UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(text), " at pkg/api/modules/transactions.uts:269")
    }
    if (rawObject == null) {
        return null
    }
    return TransactionSummary(purchase_amount = if (stringValue__5(rawObject["purchase_amount"]) == "") {
        stringValue__5(rawObject["purchaseAmount"])
    } else {
        stringValue__5(rawObject["purchase_amount"])
    }
    , arrears_amount = if (stringValue__5(rawObject["arrears_amount"]) == "") {
        stringValue__5(rawObject["debtAmount"])
    } else {
        stringValue__5(rawObject["arrears_amount"])
    }
    , payment_amount = if (stringValue__5(rawObject["payment_amount"]) == "") {
        stringValue__5(rawObject["paymentAmount"])
    } else {
        stringValue__5(rawObject["payment_amount"])
    }
    , total_paid = if (stringValue__5(rawObject["total_paid"]) == "") {
        stringValue__5(rawObject["totalPaid"])
    } else {
        stringValue__5(rawObject["total_paid"])
    }
    , net_amount = if (stringValue__5(rawObject["net_amount"]) == "") {
        stringValue__5(rawObject["actualDebt"])
    } else {
        stringValue__5(rawObject["net_amount"])
    }
    )
}
fun buildTransactionListResponse(raw: Any, query: TransactionListQuery): TransactionListResponse {
    val rawText = JSON.stringify(raw)
    val rawObject = if (rawText == null || rawText == "") {
        null
    } else {
        UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(rawText), " at pkg/api/modules/transactions.uts:283")
    }
    if (rawObject == null) {
        throw UTSError("往来记录列表响应解析失败")
    }
    var paginationObject: UTSJSONObject? = null
    val rawPagination = rawObject["pagination"]
    if (rawPagination != null) {
        val paginationText = JSON.stringify(rawPagination)
        if (paginationText != null && paginationText != "") {
            paginationObject = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(paginationText), " at pkg/api/modules/transactions.uts:292")
        }
    }
    var results: UTSArray<TransactionItem> = _uA()
    val rawResults = rawObject["results"]
    if (rawResults != null) {
        val resultText = JSON.stringify(rawResults)
        val parsedResults = if (resultText == null || resultText == "") {
            null
        } else {
            UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(resultText), " at pkg/api/modules/transactions.uts:299")
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
        currentPage = query.page
    }
    var pageSize = intValue__4(rawObject["page_size"])
    if (pageSize <= 0 && paginationObject != null) {
        pageSize = intValue__4(paginationObject["page_size"])
    }
    if (pageSize <= 0) {
        pageSize = query.page_size
    }
    var totalPages = intValue__4(rawObject["total_pages"])
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__4(paginationObject["total_pages"])
    }
    if (totalPages <= 0 && pageSize > 0) {
        totalPages = Math.ceil(totalCount / pageSize)
    }
    if (totalPages <= 0) {
        totalPages = 1
    }
    return TransactionListResponse(results = results, count = totalCount, total_count = totalCount, total_pages = totalPages, current_page = currentPage, page_size = pageSize, summary = buildTransactionSummary(rawObject["summary"]))
}
fun buildTransactionFilterOptionsResponse(raw: Any): TransactionFilterOptionsResponse {
    val rawText = JSON.stringify(raw)
    val rawObject = if (rawText == null || rawText == "") {
        null
    } else {
        UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(rawText), " at pkg/api/modules/transactions.uts:363")
    }
    if (rawObject == null) {
        throw UTSError("往来记录过滤选项解析失败")
    }
    var filters: UTSArray<TransactionFilterDefinition> = _uA()
    val rawFilters = rawObject["filters"]
    if (rawFilters != null) {
        val filtersText = JSON.stringify(rawFilters)
        val filterObjects = if (filtersText == null || filtersText == "") {
            null
        } else {
            UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(filtersText), " at pkg/api/modules/transactions.uts:371")
        }
        if (filterObjects != null) {
            val nextFilters: UTSArray<TransactionFilterDefinition> = _uA()
            run {
                var filterIndex: Number = 0
                while(filterIndex < filterObjects!!.length){
                    val filterObject = filterObjects!![filterIndex]
                    var options: UTSArray<TransactionFilterOption> = _uA()
                    val rawOptions = filterObject["options"]
                    if (rawOptions != null) {
                        val optionsText = JSON.stringify(rawOptions)
                        val optionObjects = if (optionsText == null || optionsText == "") {
                            null
                        } else {
                            UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(optionsText), " at pkg/api/modules/transactions.uts:380")
                        }
                        if (optionObjects != null) {
                            val nextOptions: UTSArray<TransactionFilterOption> = _uA()
                            run {
                                var optionIndex: Number = 0
                                while(optionIndex < optionObjects!!.length){
                                    val optionObject = optionObjects!![optionIndex]
                                    nextOptions.push(TransactionFilterOption(value = stringValue__5(optionObject["value"]), label = stringValue__5(optionObject["label"])))
                                    optionIndex += 1
                                }
                            }
                            options = nextOptions
                        }
                    }
                    nextFilters.push(TransactionFilterDefinition(key = stringValue__5(filterObject["key"]), param = stringValue__5(filterObject["param"]), label = stringValue__5(filterObject["label"]), control = stringValue__5(filterObject["control"]), aliases = stringArrayValue__2(filterObject["aliases"]), multiple = booleanValue__1(filterObject["multiple"]), options = options))
                    filterIndex += 1
                }
            }
            filters = nextFilters
        }
    }
    return TransactionFilterOptionsResponse(resource = stringValue__5(rawObject["resource"]), count = intValue__4(rawObject["count"]), filters = filters)
}
fun buildTransactionOptionsResponse(raw: Any): TransactionOptionsResponse {
    val rawText = JSON.stringify(raw)
    val rawObject = if (rawText == null || rawText == "") {
        null
    } else {
        UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(rawText), " at pkg/api/modules/transactions.uts:414")
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
            UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(groupsText), " at pkg/api/modules/transactions.uts:422")
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
                            UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(itemsText), " at pkg/api/modules/transactions.uts:431")
                        }
                        if (itemObjects != null) {
                            val nextItems: UTSArray<TransactionOptionItem> = _uA()
                            run {
                                var itemIndex: Number = 0
                                while(itemIndex < itemObjects!!.length){
                                    val itemObject = itemObjects!![itemIndex]
                                    nextItems.push(TransactionOptionItem(value = stringValue__5(itemObject["value"]), label = stringValue__5(itemObject["label"]), extra = itemObject))
                                    itemIndex += 1
                                }
                            }
                            items = nextItems
                        }
                    }
                    nextGroups.push(TransactionOptionGroup(key = stringValue__5(groupObject["key"]), label = stringValue__5(groupObject["label"]), control = stringValue__5(groupObject["control"]), count = intValue__4(groupObject["count"]), items = items))
                    groupIndex += 1
                }
            }
            groups = nextGroups
        }
    }
    return TransactionOptionsResponse(resource = stringValue__5(rawObject["resource"]), total_groups = intValue__4(rawObject["total_groups"]), groups = groups)
}
fun buildTransactionStatisticsResponse(raw: Any): TransactionStatisticsResponse {
    val rawText = JSON.stringify(raw)
    val rawObject = if (rawText == null || rawText == "") {
        null
    } else {
        UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(rawText), " at pkg/api/modules/transactions.uts:464")
    }
    if (rawObject == null) {
        throw UTSError("往来记录统计解析失败")
    }
    return TransactionStatisticsResponse(data = rawObject)
}
fun buildTransactionMutationBody(data: TransactionMutationData): UTSJSONObject {
    val body: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("body", "pkg/api/modules/transactions.uts", 473, 11), "supplier" to data.supplier, "transaction_type" to data.transaction_type, "amount" to data.amount, "transaction_date" to data.transaction_date)
    if (data.transaction_number != null && data.transaction_number != "") {
        body["transaction_number"] = data.transaction_number
    }
    if (data.note != null) {
        body["note"] = data.note
    }
    return body
}
fun transactionDetailPath(id: Any): String {
    return "/api/procurement/transactions/" + stringValue__5(id) + "/"
}
fun getTransactionList(data: TransactionListQuery): UTSPromise<TransactionListResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/procurement/transactions/", "GET", buildTransactionListQuery(data), true))
            return@w buildTransactionListResponse(raw, data)
    })
}
fun getTransactionFilterOptions(): UTSPromise<TransactionFilterOptionsResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/procurement/transactions/filter-options/", "GET", _uO(), true))
            return@w buildTransactionFilterOptionsResponse(raw)
    })
}
fun getTransactionOptions(key: String? = null, search: String? = null, limit: Number = 20): UTSPromise<TransactionOptionsResponse> {
    return wrapUTSPromise(suspend w@{
            val query: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("query", "pkg/api/modules/transactions.uts", 499, 11))
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
                UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(rawText), " at pkg/api/modules/transactions.uts:515")
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
                UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(rawText), " at pkg/api/modules/transactions.uts:524")
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
                UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(rawText), " at pkg/api/modules/transactions.uts:533")
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
open class TransactionSelectedFilter (
    @JsonNotNull
    open var param: String,
    @JsonNotNull
    open var value: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("TransactionSelectedFilter", "pages/transactions/index.uvue", 157, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return TransactionSelectedFilterReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class TransactionSelectedFilterReactiveObject : TransactionSelectedFilter, IUTSReactive<TransactionSelectedFilter> {
    override var __v_raw: TransactionSelectedFilter
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: TransactionSelectedFilter, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(param = __v_raw.param, value = __v_raw.value) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): TransactionSelectedFilterReactiveObject {
        return TransactionSelectedFilterReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SelectOption", "pages/transactions/from.uvue", 51, 6)
    }
}
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
open class SummarySelectedFilter (
    @JsonNotNull
    open var param: String,
    @JsonNotNull
    open var value: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SummarySelectedFilter", "pages/suppliers_procure/index.uvue", 163, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return SummarySelectedFilterReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class SummarySelectedFilterReactiveObject : SummarySelectedFilter, IUTSReactive<SummarySelectedFilter> {
    override var __v_raw: SummarySelectedFilter
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: SummarySelectedFilter, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(param = __v_raw.param, value = __v_raw.value) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): SummarySelectedFilterReactiveObject {
        return SummarySelectedFilterReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
val GenPagesSuppliersProcureIndexClass = CreateVueComponent(GenPagesSuppliersProcureIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesSuppliersProcureIndex.inheritAttrs, inject = GenPagesSuppliersProcureIndex.inject, props = GenPagesSuppliersProcureIndex.props, propsNeedCastKeys = GenPagesSuppliersProcureIndex.propsNeedCastKeys, emits = GenPagesSuppliersProcureIndex.emits, components = GenPagesSuppliersProcureIndex.components, styles = GenPagesSuppliersProcureIndex.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesSuppliersProcureIndex.setup(props as GenPagesSuppliersProcureIndex)
    }
    )
}
, fun(instance, renderer): GenPagesSuppliersProcureIndex {
    return GenPagesSuppliersProcureIndex(instance, renderer)
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("KasaCategoryListQuery", "pkg/api/modules/kasa_category.uts", 3, 13)
    }
}
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("KasaCategoryItem", "pkg/api/modules/kasa_category.uts", 13, 13)
    }
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("KasaCategoryListResponse", "pkg/api/modules/kasa_category.uts", 28, 13)
    }
}
typealias KasaCategoryMutationData = UTSJSONObject
open class KasaCategoryStatisticsResponse (
    @JsonNotNull
    open var data: UTSJSONObject,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("KasaCategoryStatisticsResponse", "pkg/api/modules/kasa_category.uts", 37, 13)
    }
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("KasaCategoryTaxRatesResponse", "pkg/api/modules/kasa_category.uts", 40, 13)
    }
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("KasaCategoryOptionsQuery", "pkg/api/modules/kasa_category.uts", 44, 13)
    }
}
open class KasaCategoryOptionsResponse (
    open var data: UTSJSONObject? = null,
    @JsonNotNull
    open var groups: UTSArray<UTSJSONObject>,
    @JsonNotNull
    open var items: UTSArray<UTSJSONObject>,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("KasaCategoryOptionsResponse", "pkg/api/modules/kasa_category.uts", 51, 13)
    }
}
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
fun booleanValue__2(value: Any?): Boolean {
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
    return UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(text), " at pkg/api/modules/kasa_category.uts:88")
}
fun parseObjectArray__1(value: Any?): UTSArray<UTSJSONObject> {
    if (value == null) {
        return _uA()
    }
    val text = JSON.stringify(value)
    if (text == null || text == "") {
        return _uA()
    }
    val parsed = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(text), " at pkg/api/modules/kasa_category.uts:98")
    if (parsed == null) {
        return _uA()
    }
    return parsed!!
}
fun buildKasaCategoryItemFromObject(rawObject: UTSJSONObject): KasaCategoryItem {
    val nestedRawObject = parseObject__2(rawObject["raw"])
    val nameCn = if (stringValue__6(rawObject["name_cn"]) != "") {
        stringValue__6(rawObject["name_cn"])
    } else {
        stringValue__6(if (nestedRawObject != null) {
            nestedRawObject["name_cn"]
        } else {
            null
        }
        )
    }
    val nameEn = if (stringValue__6(rawObject["name_en"]) != "") {
        stringValue__6(rawObject["name_en"])
    } else {
        stringValue__6(if (nestedRawObject != null) {
            nestedRawObject["name_en"]
        } else {
            null
        }
        )
    }
    var displayName = stringValue__6(rawObject["name"])
    if (displayName == "") {
        if (nameCn != "" && nameEn != "") {
            displayName = nameCn + " / " + nameEn
        } else if (nameCn != "") {
            displayName = nameCn
        } else {
            displayName = nameEn
        }
    }
    return KasaCategoryItem(id = intValue__5(rawObject["id"]), name = displayName, name_cn = nameCn, name_en = nameEn, code = if (stringValue__6(rawObject["code"]) != "") {
        stringValue__6(rawObject["code"])
    } else {
        stringValue__6(if (nestedRawObject != null) {
            nestedRawObject["code"]
        } else {
            null
        }
        )
    }
    , unique_kod = if (stringValue__6(rawObject["unique_kod"]) != "") {
        stringValue__6(rawObject["unique_kod"])
    } else {
        stringValue__6(if (nestedRawObject != null) {
            nestedRawObject["unique_kod"]
        } else {
            null
        }
        )
    }
    , tax_rate = if (stringValue__6(rawObject["tax_rate"]) != "") {
        stringValue__6(rawObject["tax_rate"])
    } else {
        stringValue__6(if (nestedRawObject != null) {
            nestedRawObject["tax_rate"]
        } else {
            null
        }
        )
    }
    , tax_rate_display = if (stringValue__6(rawObject["tax_rate_display"]) != "") {
        stringValue__6(rawObject["tax_rate_display"])
    } else {
        stringValue__6(if (nestedRawObject != null) {
            nestedRawObject["tax_rate_display"]
        } else {
            null
        }
        )
    }
    , products_count = if (rawObject["products_count"] != null) {
        intValue__5(rawObject["products_count"])
    } else {
        intValue__5(if (nestedRawObject != null) {
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
    , created_at = if (stringValue__6(rawObject["created_at"]) != "") {
        stringValue__6(rawObject["created_at"])
    } else {
        stringValue__6(if (nestedRawObject != null) {
            nestedRawObject["created_at"]
        } else {
            null
        }
        )
    }
    , updated_at = if (stringValue__6(rawObject["updated_at"]) != "") {
        stringValue__6(rawObject["updated_at"])
    } else {
        stringValue__6(if (nestedRawObject != null) {
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
    val query: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("query", "pkg/api/modules/kasa_category.uts", 145, 11), "page" to data.page, "page_size" to data.page_size)
    if (data.search != null && data.search != "") {
        query["search"] = data.search
    }
    if (data.id != null && stringValue__6(data.id) != "") {
        query["id"] = data.id
    }
    if (data.is_active != null && stringValue__6(data.is_active) != "") {
        query["is_active"] = data.is_active
    }
    if (data.tax_rate != null && stringValue__6(data.tax_rate) != "") {
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
    val rawObject = parseObject__2(raw)
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
        paginationObject = parseObject__2(rawObject["pagination"])
    }
    var results: UTSArray<KasaCategoryItem> = _uA()
    if (rawObject["results"] != null) {
        results = buildKasaCategoryArrayFromValue(rawObject["results"])
    } else if (rawObject["items"] != null) {
        results = buildKasaCategoryArrayFromValue(rawObject["items"])
    } else {
        results = buildKasaCategoryArrayFromValue(raw)
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
    return KasaCategoryListResponse(results = results, count = totalCount, total_count = totalCount, total_pages = totalPages, current_page = currentPage, page_size = pageSize)
}
fun buildKasaCategoryItemResponse(raw: Any, errorMessage: String): KasaCategoryItem {
    val rawObject = parseObject__2(raw)
    if (rawObject == null) {
        throw UTSError(errorMessage)
    }
    return buildKasaCategoryItemFromObject(rawObject)
}
fun buildObjectResponse(raw: Any, errorMessage: String): UTSJSONObject {
    val rawObject = parseObject__2(raw)
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
    val query: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("query", "pkg/api/modules/kasa_category.uts", 282, 11))
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
    val rawObject = parseObject__2(raw)
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
    return kasaCategoryBasePath + stringValue__6(id) + "/"
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SelectOption", "pages/kasa_category/index.uvue", 135, 6)
    }
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SelectOption", "pages/kasa_category/form.uvue", 41, 6)
    }
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("CategoryListQuery", "pkg/api/modules/category.uts", 3, 13)
    }
}
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("CategoryItem", "pkg/api/modules/category.uts", 20, 13)
    }
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("CategoryListResponse", "pkg/api/modules/category.uts", 39, 13)
    }
}
typealias CategoryMutationData = UTSJSONObject
open class CategoryRootsQuery (
    open var search: String? = null,
    open var level: Any? = null,
    open var status: String? = null,
    open var ordering: String? = null,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("CategoryRootsQuery", "pkg/api/modules/category.uts", 52, 13)
    }
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
fun booleanValue__3(value: Any?): Boolean {
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
    return UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(text), " at pkg/api/modules/category.uts:90")
}
fun parseObjectArray__2(value: Any?): UTSArray<UTSJSONObject> {
    if (value == null) {
        return _uA()
    }
    val text = JSON.stringify(value)
    if (text == null || text == "") {
        return _uA()
    }
    val parsed = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(text), " at pkg/api/modules/category.uts:100")
    if (parsed == null) {
        return _uA()
    }
    return parsed!!
}
fun buildCategoryItemFromObject(rawObject: UTSJSONObject): CategoryItem {
    return CategoryItem(id = intValue__6(rawObject["id"]), name = stringValue__7(rawObject["name"]), code = stringValue__7(rawObject["code"]), level = intValue__6(rawObject["level"]), parent_id = intValue__6(if (rawObject["parent_id"] != null) {
        rawObject["parent_id"]
    } else {
        rawObject["parent"]
    }
    ), sort_order = intValue__6(rawObject["sort_order"]), tax_rate = stringValue__7(rawObject["tax_rate"]), kasa_category_id = intValue__6(if (rawObject["kasa_category_id"] != null) {
        rawObject["kasa_category_id"]
    } else {
        rawObject["kasa_category"]
    }
    ), products_count = intValue__6(rawObject["products_count"]), children_count = intValue__6(rawObject["children_count"]), is_active = booleanValue__3(rawObject["is_active"]), is_leaf = booleanValue__3(rawObject["is_leaf"]) || stringValue__7(rawObject["status"]) == "leaf", full_name = stringValue__7(rawObject["full_name"]), path = stringValue__7(rawObject["path"]), created_at = stringValue__7(rawObject["created_at"]), updated_at = stringValue__7(rawObject["updated_at"]), raw = rawObject)
}
fun buildCategoryArrayFromValue(value: Any?): UTSArray<CategoryItem> {
    val rawObject = parseObject__3(value)
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
    val query: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("query", "pkg/api/modules/category.uts", 151, 11), "page" to data.page, "page_size" to data.page_size)
    if (data.search != null && data.search != "") {
        query["search"] = data.search
    }
    if (data.id != null && stringValue__7(data.id) != "") {
        query["id"] = data.id
    }
    if (data.is_active != null && stringValue__7(data.is_active) != "") {
        query["is_active"] = data.is_active
    }
    if (data.level != null && stringValue__7(data.level) != "") {
        query["level"] = data.level
    }
    if (data.parent != null) {
        query["parent"] = data.parent
    }
    if (data.parent_id != null && stringValue__7(data.parent_id) != "") {
        query["parent_id"] = data.parent_id
    }
    if (data.code != null && data.code != "") {
        query["code"] = data.code
    }
    if (data.tax_rate != null && stringValue__7(data.tax_rate) != "") {
        query["tax_rate"] = data.tax_rate
    }
    if (data.kasa_category != null && stringValue__7(data.kasa_category) != "") {
        query["kasa_category"] = data.kasa_category
    }
    if (data.kasa_category_id != null && stringValue__7(data.kasa_category_id) != "") {
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
    val rawObject = parseObject__3(raw)
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
        paginationObject = parseObject__3(rawObject["pagination"])
    }
    var results: UTSArray<CategoryItem> = _uA()
    if (rawObject["results"] != null) {
        results = buildCategoryArrayFromValue(rawObject["results"])
    } else if (rawObject["items"] != null) {
        results = buildCategoryArrayFromValue(rawObject["items"])
    } else {
        results = buildCategoryArrayFromValue(raw)
    }
    var totalCount = intValue__6(rawObject["count"])
    if (totalCount <= 0) {
        totalCount = intValue__6(rawObject["total"])
    }
    if (totalCount <= 0) {
        totalCount = intValue__6(rawObject["total_count"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__6(paginationObject["total"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__6(paginationObject["count"])
    }
    if (totalCount <= 0) {
        totalCount = results.length
    }
    var currentPage = intValue__6(rawObject["page"])
    if (currentPage <= 0) {
        currentPage = intValue__6(rawObject["current_page"])
    }
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
    if (pageSize <= 0) {
        pageSize = intValue__6(rawObject["per_page"])
    }
    if (pageSize <= 0 && paginationObject != null) {
        pageSize = intValue__6(paginationObject["page_size"])
    }
    if (pageSize <= 0 && paginationObject != null) {
        pageSize = intValue__6(paginationObject["per_page"])
    }
    if (pageSize <= 0) {
        pageSize = if (query.page_size > 0) {
            query.page_size
        } else {
            results.length
        }
    }
    var totalPages = intValue__6(rawObject["total_pages"])
    if (totalPages <= 0) {
        totalPages = intValue__6(rawObject["num_pages"])
    }
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__6(paginationObject["total_pages"])
    }
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__6(paginationObject["num_pages"])
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
    val rawObject = parseObject__3(raw)
    if (rawObject == null) {
        throw UTSError(errorMessage)
    }
    return buildCategoryItemFromObject(rawObject)
}
fun buildObjectResponse__1(raw: Any, errorMessage: String): UTSJSONObject {
    val rawObject = parseObject__3(raw)
    if (rawObject == null) {
        throw UTSError(errorMessage)
    }
    return rawObject
}
fun buildSearchQuery(search: String?): UTSJSONObject {
    val query: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("query", "pkg/api/modules/category.uts", 302, 11))
    if (search != null && search != "") {
        query["search"] = search
    }
    return query
}
fun buildCategoryRootsQuery(data: CategoryRootsQuery): UTSJSONObject {
    val query: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("query", "pkg/api/modules/category.uts", 309, 11))
    if (data.search != null && data.search != "") {
        query["search"] = data.search
    }
    if (data.level != null && stringValue__7(data.level) != "") {
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
    return categoryBasePath + stringValue__7(id) + "/"
}
fun buildTranslateTextBody(text: String): UTSJSONObject {
    return _uO("text" to text, "source_lang" to "zh", "target_lang" to "pl")
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
fun translateCategoryName(text: String): UTSPromise<UTSJSONObject> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(categoryBasePath + "translate_text/", "POST", buildTranslateTextBody(text), false))
            return@w buildObjectResponse__1(raw, "分类名称翻译响应解析失败")
    })
}
open class FilterOption (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var text: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("FilterOption", "pages/category/index.uvue", 171, 6)
    }
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ChildGroup", "pages/category/index.uvue", 176, 6)
    }
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SelectOption", "pages/category/from.uvue", 44, 6)
    }
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ShopListQuery", "pkg/api/modules/shops.uts", 2, 13)
    }
}
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ShopItem", "pkg/api/modules/shops.uts", 7, 13)
    }
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ShopListResponse", "pkg/api/modules/shops.uts", 18, 13)
    }
}
open class ShopMediaListQuery (
    open var search: String? = null,
    open var shop: Any? = null,
    @JsonNotNull
    open var page: Number,
    @JsonNotNull
    open var page_size: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ShopMediaListQuery", "pkg/api/modules/shops.uts", 26, 13)
    }
}
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ShopMediaFile", "pkg/api/modules/shops.uts", 32, 13)
    }
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ShopMediaItem", "pkg/api/modules/shops.uts", 50, 13)
    }
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ShopMediaListResponse", "pkg/api/modules/shops.uts", 64, 13)
    }
}
open class ShopMediaMutationData (
    open var shop: Any? = null,
    @JsonNotNull
    open var title: String,
    open var record_type: String? = null,
    open var expiration_date: String? = null,
    open var notes: String? = null,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ShopMediaMutationData", "pkg/api/modules/shops.uts", 76, 13)
    }
}
val shopBasePath = "/api/shops/shops/"
val shopMediaBasePath = "/api/shops/media/"
fun buildListQuery__2(data: ShopListQuery): UTSJSONObject {
    val query: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("query", "pkg/api/modules/shops.uts", 86, 11), "page" to data.page, "page_size" to data.page_size)
    if (data.search != null && data.search != "") {
        query["search"] = data.search
    }
    return query
}
fun buildMediaListQuery(data: ShopMediaListQuery): UTSJSONObject {
    val query: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("query", "pkg/api/modules/shops.uts", 96, 11), "page" to data.page, "page_size" to data.page_size)
    if (data.search != null && data.search != "") {
        query["search"] = data.search
    }
    if (data.shop != null && stringValue__8(data.shop) != "") {
        query["shop"] = data.shop
    }
    return query
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
    val text = stringValue__8(value)
    if (text == "") {
        return 0
    }
    val parsed = parseInt(text)
    if (isNaN(parsed)) {
        return 0
    }
    return parsed
}
fun boolValue__1(value: Any?): Boolean {
    if (value == null) {
        return false
    }
    val text = stringValue__8(value).toLowerCase()
    return text == "true" || text == "1" || text == "yes"
}
fun parseObject__4(value: Any?): UTSJSONObject? {
    if (value == null) {
        return null
    }
    val text = JSON.stringify(value)
    if (text == null || text == "") {
        return null
    }
    return UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(text), " at pkg/api/modules/shops.uts:143")
}
fun parseArray(value: Any?): UTSArray<UTSJSONObject> {
    if (value == null) {
        return _uA<UTSJSONObject>()
    }
    val text = JSON.stringify(value)
    if (text == null || text == "") {
        return _uA<UTSJSONObject>()
    }
    val parsed = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(text), " at pkg/api/modules/shops.uts:153")
    if (parsed == null) {
        return _uA<UTSJSONObject>()
    }
    return parsed
}
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
fun buildShopItem(rawObject: UTSJSONObject): ShopItem {
    return ShopItem(id = intValue__7(rawObject["id"]), name = stringValue__8(rawObject["name"]), address = stringValue__8(rawObject["address"]), company = intValue__7(rawObject["company"]), company_name = stringValue__8(rawObject["company_name"]), media_records_count = intValue__7(rawObject["media_records_count"]), media_files = buildShopMediaFiles(rawObject["media_files"]), created_at = stringValue__8(rawObject["created_at"]), updated_at = stringValue__8(rawObject["updated_at"]))
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
    return ShopMediaFile(id = stringValue__8(rawObject["id"]), company = intValue__7(rawObject["company"]), original_filename = stringValue__8(rawObject["original_filename"]), file_type = stringValue__8(rawObject["file_type"]), file_type_display = stringValue__8(rawObject["file_type_display"]), mime_type = stringValue__8(rawObject["mime_type"]), file_size = intValue__7(rawObject["file_size"]), file_size_display = stringValue__8(rawObject["file_size_display"]), file_url = normalizeServerUrl__4(stringValue__8(rawObject["file_url"])), thumbnail_url = normalizeServerUrl__4(stringValue__8(rawObject["thumbnail_url"])), signed_url = normalizeServerUrl__4(stringValue__8(rawObject["signed_url"])), signed_thumbnail_url = normalizeServerUrl__4(stringValue__8(rawObject["signed_thumbnail_url"])), object_id = stringValue__8(rawObject["object_id"]), is_deleted = boolValue__1(rawObject["is_deleted"]), created_at = stringValue__8(rawObject["created_at"]), updated_at = stringValue__8(rawObject["updated_at"]))
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
    return ShopMediaItem(id = intValue__7(rawObject["id"]), shop = intValue__7(rawObject["shop"]), shop_name = stringValue__8(rawObject["shop_name"]), title = stringValue__8(rawObject["title"]), record_type = stringValue__8(rawObject["record_type"]), record_type_display = stringValue__8(rawObject["record_type_display"]), expiration_date = stringValue__8(rawObject["expiration_date"]), notes = stringValue__8(rawObject["notes"]), media_files = buildShopMediaFiles(rawObject["media_files"]), files_count = intValue__7(rawObject["files_count"]), created_at = stringValue__8(rawObject["created_at"]), updated_at = stringValue__8(rawObject["updated_at"]))
}
fun buildShopMediaItemResponse(raw: Any): ShopMediaItem {
    val rawObject = parseObject__4(raw)
    if (rawObject == null) {
        throw UTSError("商店资料详情响应解析失败")
    }
    return buildShopMediaItem(rawObject)
}
fun buildShopItemResponse(raw: Any): ShopItem {
    val rawObject = parseObject__4(raw)
    if (rawObject == null) {
        throw UTSError("商店详情响应解析失败")
    }
    return buildShopItem(rawObject)
}
fun shopDetailPath(id: Any): String {
    return shopBasePath + stringValue__8(id) + "/"
}
fun shopMediaDetailPath(id: Any): String {
    return shopMediaBasePath + stringValue__8(id) + "/"
}
fun buildShopListResponse(raw: Any, query: ShopListQuery): ShopListResponse {
    val rawObject = parseObject__4(raw)
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
    val paginationObject = parseObject__4(rawObject["pagination"])
    var totalCount = intValue__7(rawObject["count"])
    if (totalCount <= 0) {
        totalCount = intValue__7(rawObject["total"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__7(paginationObject["total"])
    }
    if (totalCount <= 0) {
        totalCount = results.length
    }
    var currentPage = intValue__7(rawObject["page"])
    if (currentPage <= 0 && paginationObject != null) {
        currentPage = intValue__7(paginationObject["page"])
    }
    if (currentPage <= 0) {
        currentPage = if (query.page > 0) {
            query.page
        } else {
            1
        }
    }
    var pageSize = intValue__7(rawObject["page_size"])
    if (pageSize <= 0 && paginationObject != null) {
        pageSize = intValue__7(paginationObject["page_size"])
    }
    if (pageSize <= 0) {
        pageSize = if (query.page_size > 0) {
            query.page_size
        } else {
            results.length
        }
    }
    var totalPages = intValue__7(rawObject["total_pages"])
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__7(paginationObject["total_pages"])
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
    val rawObject = parseObject__4(raw)
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
    val paginationObject = parseObject__4(rawObject["pagination"])
    var totalCount = intValue__7(rawObject["count"])
    if (totalCount <= 0) {
        totalCount = intValue__7(rawObject["total"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__7(paginationObject["total"])
    }
    if (totalCount <= 0) {
        totalCount = results.length
    }
    var currentPage = intValue__7(rawObject["page"])
    if (currentPage <= 0 && paginationObject != null) {
        currentPage = intValue__7(paginationObject["page"])
    }
    if (currentPage <= 0) {
        currentPage = if (query.page > 0) {
            query.page
        } else {
            1
        }
    }
    var pageSize = intValue__7(rawObject["page_size"])
    if (pageSize <= 0 && paginationObject != null) {
        pageSize = intValue__7(paginationObject["page_size"])
    }
    if (pageSize <= 0) {
        pageSize = if (query.page_size > 0) {
            query.page_size
        } else {
            results.length
        }
    }
    var totalPages = intValue__7(rawObject["total_pages"])
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__7(paginationObject["total_pages"])
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SelectOption", "pages/shop/from.uvue", 50, 6)
    }
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SelectOption", "pages/products/from.uvue", 125, 6)
    }
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
val GenPagesProductsDiscountSelectorClass = CreateVueComponent(GenPagesProductsDiscountSelector::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesProductsDiscountSelector.inheritAttrs, inject = GenPagesProductsDiscountSelector.inject, props = GenPagesProductsDiscountSelector.props, propsNeedCastKeys = GenPagesProductsDiscountSelector.propsNeedCastKeys, emits = GenPagesProductsDiscountSelector.emits, components = GenPagesProductsDiscountSelector.components, styles = GenPagesProductsDiscountSelector.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesProductsDiscountSelector.setup(props as GenPagesProductsDiscountSelector)
    }
    )
}
, fun(instance, renderer): GenPagesProductsDiscountSelector {
    return GenPagesProductsDiscountSelector(instance, renderer)
}
)
open class PriceContext (
    @JsonNotNull
    open var purchase_price: String,
    @JsonNotNull
    open var net_purchase_price: String,
    @JsonNotNull
    open var cost_price: String,
    @JsonNotNull
    open var base_sales_price: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PriceContext", "pages/products/price-calculator.uvue", 128, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return PriceContextReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class PriceContextReactiveObject : PriceContext, IUTSReactive<PriceContext> {
    override var __v_raw: PriceContext
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: PriceContext, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(purchase_price = __v_raw.purchase_price, net_purchase_price = __v_raw.net_purchase_price, cost_price = __v_raw.cost_price, base_sales_price = __v_raw.base_sales_price) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): PriceContextReactiveObject {
        return PriceContextReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
}
open class FormulaPreviewItem (
    @JsonNotNull
    open var formulaId: String,
    @JsonNotNull
    open var formulaName: String,
    @JsonNotNull
    open var formulaCode: String,
    @JsonNotNull
    open var expression: String,
    @JsonNotNull
    open var sourceLabel: String,
    @JsonNotNull
    open var sourceValue: String,
    @JsonNotNull
    open var result: String,
    @JsonNotNull
    open var error: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("FormulaPreviewItem", "pages/products/price-calculator.uvue", 135, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return FormulaPreviewItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class FormulaPreviewItemReactiveObject : FormulaPreviewItem, IUTSReactive<FormulaPreviewItem> {
    override var __v_raw: FormulaPreviewItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: FormulaPreviewItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(formulaId = __v_raw.formulaId, formulaName = __v_raw.formulaName, formulaCode = __v_raw.formulaCode, expression = __v_raw.expression, sourceLabel = __v_raw.sourceLabel, sourceValue = __v_raw.sourceValue, result = __v_raw.result, error = __v_raw.error) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): FormulaPreviewItemReactiveObject {
        return FormulaPreviewItemReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var formulaId: String
        get() {
            return _tRG(__v_raw, "formulaId", __v_raw.formulaId, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("formulaId")) {
                return
            }
            val oldValue = __v_raw.formulaId
            __v_raw.formulaId = value
            _tRS(__v_raw, "formulaId", oldValue, value)
        }
    override var formulaName: String
        get() {
            return _tRG(__v_raw, "formulaName", __v_raw.formulaName, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("formulaName")) {
                return
            }
            val oldValue = __v_raw.formulaName
            __v_raw.formulaName = value
            _tRS(__v_raw, "formulaName", oldValue, value)
        }
    override var formulaCode: String
        get() {
            return _tRG(__v_raw, "formulaCode", __v_raw.formulaCode, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("formulaCode")) {
                return
            }
            val oldValue = __v_raw.formulaCode
            __v_raw.formulaCode = value
            _tRS(__v_raw, "formulaCode", oldValue, value)
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
    override var sourceLabel: String
        get() {
            return _tRG(__v_raw, "sourceLabel", __v_raw.sourceLabel, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("sourceLabel")) {
                return
            }
            val oldValue = __v_raw.sourceLabel
            __v_raw.sourceLabel = value
            _tRS(__v_raw, "sourceLabel", oldValue, value)
        }
    override var sourceValue: String
        get() {
            return _tRG(__v_raw, "sourceValue", __v_raw.sourceValue, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("sourceValue")) {
                return
            }
            val oldValue = __v_raw.sourceValue
            __v_raw.sourceValue = value
            _tRS(__v_raw, "sourceValue", oldValue, value)
        }
    override var result: String
        get() {
            return _tRG(__v_raw, "result", __v_raw.result, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("result")) {
                return
            }
            val oldValue = __v_raw.result
            __v_raw.result = value
            _tRS(__v_raw, "result", oldValue, value)
        }
    override var error: String
        get() {
            return _tRG(__v_raw, "error", __v_raw.error, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("error")) {
                return
            }
            val oldValue = __v_raw.error
            __v_raw.error = value
            _tRS(__v_raw, "error", oldValue, value)
        }
}
val GenPagesProductsPriceCalculatorClass = CreateVueComponent(GenPagesProductsPriceCalculator::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesProductsPriceCalculator.inheritAttrs, inject = GenPagesProductsPriceCalculator.inject, props = GenPagesProductsPriceCalculator.props, propsNeedCastKeys = GenPagesProductsPriceCalculator.propsNeedCastKeys, emits = GenPagesProductsPriceCalculator.emits, components = GenPagesProductsPriceCalculator.components, styles = GenPagesProductsPriceCalculator.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesProductsPriceCalculator.setup(props as GenPagesProductsPriceCalculator)
    }
    )
}
, fun(instance, renderer): GenPagesProductsPriceCalculator {
    return GenPagesProductsPriceCalculator(instance, renderer)
}
)
open class FormulaChoice (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var label: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("FormulaChoice", "pages/products/pricing-formula.uvue", 167, 6)
    }
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("FormulaStep", "pages/products/pricing-formula.uvue", 172, 6)
    }
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ParsedFormula", "pages/products/pricing-formula.uvue", 180, 6)
    }
}
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("FormulaChoice", "pages/products/pricing-formula/from.uvue", 168, 6)
    }
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("FormulaStep", "pages/products/pricing-formula/from.uvue", 173, 6)
    }
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ParsedFormula", "pages/products/pricing-formula/from.uvue", 181, 6)
    }
}
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
open class LabelPrinterDevice (
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var address: String,
    @JsonNotNull
    open var type: String,
    @JsonNotNull
    open var bondState: String,
    @JsonNotNull
    open var rssi: Number,
    @JsonNotNull
    open var connected: Boolean = false,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("LabelPrinterDevice", "uni_modules/lili-label-printer/index.uts", 21, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return LabelPrinterDeviceReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class LabelPrinterDeviceReactiveObject : LabelPrinterDevice, IUTSReactive<LabelPrinterDevice> {
    override var __v_raw: LabelPrinterDevice
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: LabelPrinterDevice, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(name = __v_raw.name, address = __v_raw.address, type = __v_raw.type, bondState = __v_raw.bondState, rssi = __v_raw.rssi, connected = __v_raw.connected) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): LabelPrinterDeviceReactiveObject {
        return LabelPrinterDeviceReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
    override var type: String
        get() {
            return _tRG(__v_raw, "type", __v_raw.type, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("type")) {
                return
            }
            val oldValue = __v_raw.type
            __v_raw.type = value
            _tRS(__v_raw, "type", oldValue, value)
        }
    override var bondState: String
        get() {
            return _tRG(__v_raw, "bondState", __v_raw.bondState, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("bondState")) {
                return
            }
            val oldValue = __v_raw.bondState
            __v_raw.bondState = value
            _tRS(__v_raw, "bondState", oldValue, value)
        }
    override var rssi: Number
        get() {
            return _tRG(__v_raw, "rssi", __v_raw.rssi, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("rssi")) {
                return
            }
            val oldValue = __v_raw.rssi
            __v_raw.rssi = value
            _tRS(__v_raw, "rssi", oldValue, value)
        }
    override var connected: Boolean
        get() {
            return _tRG(__v_raw, "connected", __v_raw.connected, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("connected")) {
                return
            }
            val oldValue = __v_raw.connected
            __v_raw.connected = value
            _tRS(__v_raw, "connected", oldValue, value)
        }
}
open class LabelPrinterResult (
    @JsonNotNull
    open var success: Boolean = false,
    @JsonNotNull
    open var code: String,
    @JsonNotNull
    open var message: String,
    open var data: UTSJSONObject? = null,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("LabelPrinterResult", "uni_modules/lili-label-printer/index.uts", 29, 13)
    }
}
open class PrintTsplOptions (
    @JsonNotNull
    open var address: String,
    @JsonNotNull
    open var tspl: String,
    open var charset: String? = null,
    open var chunkSize: Number? = null,
    open var delayMs: Number? = null,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PrintTsplOptions", "uni_modules/lili-label-printer/index.uts", 35, 13)
    }
}
val sppUuidText = "00001101-0000-1000-8000-00805F9B34FB"
var activeSocket: BluetoothSocket? = null
var activeDevice: BluetoothDevice? = null
var discoveryReceiver: BroadcastReceiver? = null
var discoveryDevices: UTSArray<LabelPrinterDevice> = _uA()
var discoveryResolve: ((devices: UTSArray<LabelPrinterDevice>) -> Unit)? = null
var discoveryTimer: Number = -1
fun makeResult(success: Boolean, code: String, message: String, data: UTSJSONObject?): LabelPrinterResult {
    return LabelPrinterResult(success = success, code = code, message = message, data = data)
}
fun getActivity(): Activity {
    return UTSAndroid.getUniActivity() as Activity
}
fun getAdapter(): BluetoothAdapter? {
    val manager = getActivity().getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    if (manager == null) {
        return null
    }
    return manager.getAdapter()
}
fun isAndroid12(): Boolean {
    return Build.VERSION.SDK_INT >= 31
}
fun scanPermissions(): UTSArray<String> {
    if (isAndroid12()) {
        return _uA(
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT
        )
    }
    return _uA(
        Manifest.permission.ACCESS_FINE_LOCATION
    )
}
fun connectPermissions(): UTSArray<String> {
    if (isAndroid12()) {
        return _uA(
            Manifest.permission.BLUETOOTH_CONNECT
        )
    }
    return _uA()
}
fun hasPermissions(permissions: UTSArray<String>): Boolean {
    if (permissions.length == 0) {
        return true
    }
    return UTSAndroid.checkSystemPermissionGranted(getActivity(), permissions)
}
fun bluetoothEnabled(adapter: BluetoothAdapter): Boolean {
    try {
        return adapter.isEnabled()
    }
     catch (error: Throwable) {
        return false
    }
}
fun isLocationServiceEnabled(): Boolean {
    val manager = getActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
    if (manager == null) {
        return false
    }
    var gpsEnabled = false
    var networkEnabled = false
    try {
        gpsEnabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
     catch (error: Throwable) {
        gpsEnabled = false
    }
    try {
        networkEnabled = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
     catch (error: Throwable) {
        networkEnabled = false
    }
    return gpsEnabled || networkEnabled
}
fun deviceBondState(device: BluetoothDevice): String {
    val state = device.getBondState()
    if (state == BluetoothDevice.BOND_BONDED) {
        return "BONDED"
    }
    if (state == BluetoothDevice.BOND_BONDING) {
        return "BONDING"
    }
    return "NONE"
}
fun deviceType(device: BluetoothDevice): String {
    if (Build.VERSION.SDK_INT < 18) {
        return "CLASSIC"
    }
    val type = device.getType()
    if (type == BluetoothDevice.DEVICE_TYPE_LE) {
        return "BLE"
    }
    if (type == BluetoothDevice.DEVICE_TYPE_DUAL) {
        return "DUAL"
    }
    if (type == BluetoothDevice.DEVICE_TYPE_CLASSIC) {
        return "CLASSIC"
    }
    return "UNKNOWN"
}
fun safeDeviceName(device: BluetoothDevice): String {
    var name = ""
    try {
        name = device.getName()
    }
     catch (error: Throwable) {
        name = ""
    }
    if (name == null || name == "") {
        return "未知蓝牙设备"
    }
    return name
}
fun toPrinterDevice(device: BluetoothDevice, rssi: Number): LabelPrinterDevice {
    val address = device.getAddress()
    var connected = false
    if (activeDevice != null && activeSocket != null) {
        connected = activeDevice!!.getAddress() == address && activeSocket!!.isConnected()
    }
    return LabelPrinterDevice(name = safeDeviceName(device), address = address, type = deviceType(device), bondState = deviceBondState(device), rssi = rssi, connected = connected)
}
fun addOrUpdateDevice(device: BluetoothDevice, rssi: Number) {
    val item = toPrinterDevice(device, rssi)
    console.log("[lili-label-printer] found device name=" + item.name + ", address=" + item.address + ", type=" + item.type + ", bond=" + item.bondState + ", rssi=" + item.rssi.toString(10), " at uni_modules/lili-label-printer/index.uts:192")
    var found = false
    run {
        var i: Number = 0
        while(i < discoveryDevices.length){
            if (discoveryDevices[i].address == item.address) {
                discoveryDevices[i] = item
                found = true
                break
            }
            i++
        }
    }
    if (!found) {
        discoveryDevices.push(item)
    }
}
@Suppress("DEPRECATION")
fun getBluetoothDeviceExtra(intent: Intent): BluetoothDevice? {
    return intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
}
fun closeActiveSocket() {
    if (activeSocket != null) {
        try {
            activeSocket!!.close()
        }
         catch (error: Throwable) {}
    }
    activeSocket = null
    activeDevice = null
}
fun finishDiscovery() {
    console.log("[lili-label-printer] finish discovery, count=" + discoveryDevices.length.toString(10), " at uni_modules/lili-label-printer/index.uts:222")
    val adapter = getAdapter()
    if (adapter != null) {
        try {
            if (adapter.isDiscovering()) {
                adapter.cancelDiscovery()
            }
        }
         catch (error: Throwable) {}
    }
    if (discoveryReceiver != null) {
        try {
            getActivity().unregisterReceiver(discoveryReceiver)
        }
         catch (error: Throwable) {}
    }
    discoveryReceiver = null
    if (discoveryTimer >= 0) {
        clearTimeout(discoveryTimer)
        discoveryTimer = -1
    }
    if (discoveryResolve != null) {
        val resolver = discoveryResolve!!
        discoveryResolve = null
        resolver(discoveryDevices)
    }
}
open class LabelDiscoveryReceiver : BroadcastReceiver(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("LabelDiscoveryReceiver", "uni_modules/lili-label-printer/index.uts", 232, 7)
    }
    override fun onReceive(context: Context, intent: Intent): Unit {
        val action = intent.getAction()
        if (action == BluetoothDevice.ACTION_FOUND) {
            val device = getBluetoothDeviceExtra(intent)
            val rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, 0).toInt()
            if (device != null) {
                addOrUpdateDevice(device as BluetoothDevice, rssi)
            }
            return
        }
        if (action == BluetoothAdapter.ACTION_DISCOVERY_FINISHED) {
            finishDiscovery()
        }
    }
}
fun checkBluetoothEnvironment(): LabelPrinterResult {
    val adapter = getAdapter()
    if (adapter == null) {
        return makeResult(false, "BLUETOOTH_UNSUPPORTED", "当前设备不支持蓝牙", null)
    }
    val scanGranted = hasPermissions(scanPermissions())
    val connectGranted = hasPermissions(connectPermissions())
    val enabled = bluetoothEnabled(adapter)
    val locationEnabled = if (isAndroid12()) {
        true
    } else {
        isLocationServiceEnabled()
    }
    val data: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("data", "uni_modules/lili-label-printer/index.uts", 257, 11), "bluetoothSupported" to true, "bluetoothEnabled" to enabled, "locationPermissionGranted" to scanGranted, "bluetoothPermissionGranted" to connectGranted, "locationServiceEnabled" to locationEnabled)
    if (!enabled) {
        return makeResult(false, "BLUETOOTH_DISABLED", "蓝牙未开启", data)
    }
    if (!scanGranted || !connectGranted) {
        return makeResult(false, "LOCATION_PERMISSION_REQUIRED", "缺少蓝牙或位置权限", data)
    }
    if (!locationEnabled) {
        return makeResult(false, "LOCATION_SERVICE_DISABLED", "系统位置服务未开启，可能无法搜索蓝牙设备", data)
    }
    return makeResult(true, "OK", "蓝牙环境正常", data)
}
fun requestBluetoothEnable(): LabelPrinterResult {
    val adapter = getAdapter()
    if (adapter == null) {
        return makeResult(false, "BLUETOOTH_UNSUPPORTED", "当前设备不支持蓝牙", null)
    }
    if (bluetoothEnabled(adapter)) {
        return makeResult(true, "BLUETOOTH_ENABLED", "蓝牙已开启", null)
    }
    val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
    getActivity().startActivity(intent)
    return makeResult(true, "BLUETOOTH_ENABLE_REQUESTED", "已打开蓝牙开启请求，请确认后返回", null)
}
fun requestLocationPermission(): UTSPromise<LabelPrinterResult> {
    return UTSPromise(fun(resolve, _reject){
        val permissions = scanPermissions()
        if (hasPermissions(permissions)) {
            resolve(makeResult(true, "PERMISSION_GRANTED", "蓝牙和位置权限已授权", null))
            return
        }
        try {
            UTSAndroid.requestSystemPermission(getActivity(), permissions, fun(allRight: Boolean, grantedList: UTSArray<String>){
                if (allRight) {
                    resolve(makeResult(true, "PERMISSION_GRANTED", "蓝牙和位置权限已授权", null))
                    return
                }
                resolve(makeResult(false, "PERMISSION_DENIED", "权限未完全授权，无法搜索蓝牙打印机", null))
            }
            , fun(doNotAskAgain: Boolean, grantedList: UTSArray<String>){
                if (doNotAskAgain) {
                    resolve(makeResult(false, "PERMISSION_NEED_SETTINGS", "权限已被拒绝且不再询问，请到系统设置开启", null))
                    return
                }
                resolve(makeResult(false, "PERMISSION_DENIED", "权限授权失败，无法搜索蓝牙打印机", null))
            }
            )
        }
         catch (error: Throwable) {
            resolve(makeResult(false, "PERMISSION_MANIFEST_MISSING", "蓝牙权限未写入自定义基座，请重新打包自定义基座", null))
        }
    }
    )
}
fun openLocationSettings(): LabelPrinterResult {
    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
    getActivity().startActivity(intent)
    return makeResult(true, "LOCATION_SETTINGS_OPENED", "已打开系统位置设置", null)
}
fun searchBluetoothPrinters(durationMs: Number = 10000): UTSPromise<UTSArray<LabelPrinterDevice>> {
    return UTSPromise(fun(resolve, _reject){
        val environment = checkBluetoothEnvironment()
        if (!environment.success) {
            console.log("[lili-label-printer] discovery blocked: " + environment.code + ", " + environment.message, " at uni_modules/lili-label-printer/index.uts:361")
            resolve(_uA())
            return
        }
        val adapter = getAdapter()
        if (adapter == null) {
            console.log("[lili-label-printer] discovery blocked: bluetooth adapter is null", " at uni_modules/lili-label-printer/index.uts:367")
            resolve(_uA())
            return
        }
        stopBluetoothDiscovery()
        discoveryDevices = _uA()
        discoveryResolve = resolve
        discoveryReceiver = LabelDiscoveryReceiver()
        val filter = IntentFilter()
        filter.addAction(BluetoothDevice.ACTION_FOUND)
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        getActivity().registerReceiver(discoveryReceiver, filter)
        try {
            if (adapter.isDiscovering()) {
                adapter.cancelDiscovery()
            }
            val started = adapter.startDiscovery()
            console.log("[lili-label-printer] start discovery result=" + started.toString() + ", durationMs=" + durationMs.toString(10), " at uni_modules/lili-label-printer/index.uts:384")
            if (!started) {
                finishDiscovery()
                return
            }
        }
         catch (error: Throwable) {
            console.log("[lili-label-printer] start discovery failed", " at uni_modules/lili-label-printer/index.uts:390")
            finishDiscovery()
            return
        }
        discoveryTimer = setTimeout(fun(){
            finishDiscovery()
        }
        , durationMs)
    }
    )
}
fun stopBluetoothDiscovery(): LabelPrinterResult {
    finishDiscovery()
    return makeResult(true, "DISCOVERY_STOPPED", "已停止搜索蓝牙设备", null)
}
fun connectPrinter(address: String): LabelPrinterResult {
    val environment = checkBluetoothEnvironment()
    if (!environment.success && environment.code != "LOCATION_SERVICE_DISABLED") {
        return environment
    }
    val adapter = getAdapter()
    if (adapter == null) {
        return makeResult(false, "BLUETOOTH_UNSUPPORTED", "当前设备不支持蓝牙", null)
    }
    if (address == "") {
        return makeResult(false, "ADDRESS_EMPTY", "打印机地址不能为空", null)
    }
    stopBluetoothDiscovery()
    closeActiveSocket()
    try {
        val device = adapter.getRemoteDevice(address)
        val socket = device.createRfcommSocketToServiceRecord(UUID.fromString(sppUuidText))
        socket.connect()
        activeSocket = socket
        activeDevice = device
        val data: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("data", "uni_modules/lili-label-printer/index.uts", 403, 15), "name" to safeDeviceName(device), "address" to device.getAddress(), "type" to deviceType(device), "bondState" to deviceBondState(device))
        return makeResult(true, "CONNECTED", "已连接蓝牙打印机", data)
    }
     catch (error: Throwable) {
        closeActiveSocket()
        return makeResult(false, "CONNECT_FAILED", "连接蓝牙打印机失败，请确认设备已开机并处于可连接状态", null)
    }
}
fun disconnectPrinter(): LabelPrinterResult {
    closeActiveSocket()
    return makeResult(true, "DISCONNECTED", "已断开蓝牙打印机", null)
}
fun getConnectedPrinter(): LabelPrinterResult {
    if (activeDevice == null || activeSocket == null || !activeSocket!!.isConnected()) {
        return makeResult(false, "NOT_CONNECTED", "当前未连接蓝牙打印机", null)
    }
    val data: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("data", "uni_modules/lili-label-printer/index.uts", 424, 11), "name" to safeDeviceName(activeDevice!!), "address" to activeDevice!!.getAddress(), "type" to deviceType(activeDevice!!), "bondState" to deviceBondState(activeDevice!!))
    return makeResult(true, "CONNECTED", "已连接蓝牙打印机", data)
}
fun labelString(value: Any?, fallback: String = ""): String {
    if (value == null) {
        return fallback
    }
    val text = "" + value
    if (text == "") {
        return fallback
    }
    return text
}
fun labelNumber(value: Any?, fallback: Number): Number {
    val parsed = parseFloat(labelString(value))
    if (isNaN(parsed)) {
        return fallback
    }
    return parsed
}
fun labelBool(value: Any?, fallback: Boolean): Boolean {
    if (value == null) {
        return fallback
    }
    val text = labelString(value).toLowerCase()
    if (text == "true" || text == "1") {
        return true
    }
    if (text == "false" || text == "0") {
        return false
    }
    return fallback
}
fun onlyDigits(value: String): String {
    var result = ""
    run {
        var index: Number = 0
        while(index < value.length){
            val char = value.substring(index, index + 1)
            if (char >= "0" && char <= "9") {
                result = result + char
            }
            index += 1
        }
    }
    return result
}
fun ean13Checksum(first12: String): String {
    var sum: Number = 0
    run {
        var index: Number = 0
        while(index < 12){
            val digit = parseInt(first12.substring(index, index + 1))
            sum += if (index % 2 == 0) {
                digit
            } else {
                digit * 3
            }
            index += 1
        }
    }
    val check = (10 - (sum % 10)) % 10
    return check.toString(10)
}
fun normalizeEan13(value: String): String {
    var digits = onlyDigits(value)
    if (digits.length >= 13) {
        return digits.substring(0, 13)
    }
    if (digits.length == 12) {
        return digits + ean13Checksum(digits)
    }
    return "5901234123457"
}
fun ean13LeftPattern(digit: String, parity: String): String {
    if (digit == "0") {
        return if (parity == "G") {
            "0100111"
        } else {
            "0001101"
        }
    }
    if (digit == "1") {
        return if (parity == "G") {
            "0110011"
        } else {
            "0011001"
        }
    }
    if (digit == "2") {
        return if (parity == "G") {
            "0011011"
        } else {
            "0010011"
        }
    }
    if (digit == "3") {
        return if (parity == "G") {
            "0100001"
        } else {
            "0111101"
        }
    }
    if (digit == "4") {
        return if (parity == "G") {
            "0011101"
        } else {
            "0100011"
        }
    }
    if (digit == "5") {
        return if (parity == "G") {
            "0111001"
        } else {
            "0110001"
        }
    }
    if (digit == "6") {
        return if (parity == "G") {
            "0000101"
        } else {
            "0101111"
        }
    }
    if (digit == "7") {
        return if (parity == "G") {
            "0010001"
        } else {
            "0111011"
        }
    }
    if (digit == "8") {
        return if (parity == "G") {
            "0001001"
        } else {
            "0110111"
        }
    }
    return if (parity == "G") {
        "0010111"
    } else {
        "0001011"
    }
}
fun ean13RightPattern(digit: String): String {
    if (digit == "0") {
        return "1110010"
    }
    if (digit == "1") {
        return "1100110"
    }
    if (digit == "2") {
        return "1101100"
    }
    if (digit == "3") {
        return "1000010"
    }
    if (digit == "4") {
        return "1011100"
    }
    if (digit == "5") {
        return "1001110"
    }
    if (digit == "6") {
        return "1010000"
    }
    if (digit == "7") {
        return "1000100"
    }
    if (digit == "8") {
        return "1001000"
    }
    return "1110100"
}
fun ean13Parity(first: String): String {
    if (first == "0") {
        return "LLLLLL"
    }
    if (first == "1") {
        return "LLGLGG"
    }
    if (first == "2") {
        return "LLGGLG"
    }
    if (first == "3") {
        return "LLGGGL"
    }
    if (first == "4") {
        return "LGLLGG"
    }
    if (first == "5") {
        return "LGGLLG"
    }
    if (first == "6") {
        return "LGGGLL"
    }
    if (first == "7") {
        return "LGLGLG"
    }
    if (first == "8") {
        return "LGLGGL"
    }
    return "LGGLGL"
}
fun ean13Pattern(value: String): String {
    val digits = normalizeEan13(value)
    val parity = ean13Parity(digits.substring(0, 1))
    var result = "101"
    run {
        var index: Number = 1
        while(index <= 6){
            result = result + ean13LeftPattern(digits.substring(index, index + 1), parity.substring(index - 1, index))
            index += 1
        }
    }
    result = result + "01010"
    run {
        var index: Number = 7
        while(index <= 12){
            result = result + ean13RightPattern(digits.substring(index, index + 1))
            index += 1
        }
    }
    return result + "101"
}
fun drawBitmapText(canvas: Canvas, paint: Paint, element: UTSJSONObject, dotsPerMm: Number) {
    val content = labelString(element["content"], labelString(element["label"], "TEXT"))
    val x = Math.round(labelNumber(element["x"], 0) * dotsPerMm)
    val y = Math.round(labelNumber(element["y"], 0) * dotsPerMm)
    val fontSize = labelNumber(element["fontSize"], 18)
    paint.setColor(Color.BLACK)
    paint.setAntiAlias(true)
    paint.setTextSize(fontSize.toFloat())
    canvas.drawText(content, x.toFloat(), (y + fontSize * 0.82).toFloat(), paint)
}
fun drawPseudoBarcode(canvas: Canvas, paint: Paint, content: String, x: Number, y: Number, width: Number, height: Number) {
    val count: Number = 38
    val barWidth = width / count
    run {
        var index: Number = 0
        while(index < count){
            if (index % 3 != 1) {
                val left = x + index * barWidth
                canvas.drawRect(left.toFloat(), y.toFloat(), (left + Math.max(1, barWidth * 0.65)).toFloat(), (y + height).toFloat(), paint)
            }
            index += 1
        }
    }
}
fun drawEan13Barcode(canvas: Canvas, paint: Paint, content: String, x: Number, y: Number, width: Number, height: Number) {
    val pattern = ean13Pattern(content)
    val moduleWidth = width / pattern.length
    run {
        var index: Number = 0
        while(index < pattern.length){
            if (pattern.substring(index, index + 1) == "1") {
                val left = x + index * moduleWidth
                canvas.drawRect(left.toFloat(), y.toFloat(), (left + Math.max(1, moduleWidth)).toFloat(), (y + height).toFloat(), paint)
            }
            index += 1
        }
    }
}
fun drawBitmapBarcode(canvas: Canvas, paint: Paint, element: UTSJSONObject, dotsPerMm: Number) {
    val content = labelString(element["content"], "5901234123457")
    val barcodeType = labelString(element["barcodeType"], labelString(element["barcode_type"], "CODE128")).toUpperCase()
    val showText = labelBool(element["showText"], labelBool(element["show_text"], false))
    val x = Math.round(labelNumber(element["x"], 0) * dotsPerMm)
    val y = Math.round(labelNumber(element["y"], 0) * dotsPerMm)
    val width = Math.round(labelNumber(element["width"], 20) * dotsPerMm)
    val height = Math.round(labelNumber(element["height"], 5) * dotsPerMm)
    val textHeight = if (showText) {
        14
    } else {
        0
    }
    val barHeight = Math.max(8, height - textHeight)
    paint.setColor(Color.BLACK)
    paint.setAntiAlias(false)
    if (barcodeType == "EAN13") {
        drawEan13Barcode(canvas, paint, content, x, y, width, barHeight)
    } else {
        drawPseudoBarcode(canvas, paint, content, x, y, width, barHeight)
    }
    if (showText) {
        paint.setAntiAlias(true)
        val textSize = (height / 3).toFloat()
        paint.setTextSize(textSize)
        canvas.drawText(content, (x + 2).toFloat(), (y + height - 2).toFloat(), paint)
    }
}
fun drawBitmapGraphic(canvas: Canvas, paint: Paint, element: UTSJSONObject, dotsPerMm: Number) {
    val type = labelString(element["type"], "text")
    val x = Math.round(labelNumber(element["x"], 0) * dotsPerMm)
    val y = Math.round(labelNumber(element["y"], 0) * dotsPerMm)
    val width = Math.round(labelNumber(element["width"], 10) * dotsPerMm)
    val height = Math.round(labelNumber(element["height"], 5) * dotsPerMm)
    val lineWidth = Math.max(1, Math.round(labelNumber(element["lineWidth"], labelNumber(element["line_width"], 0.4)) * dotsPerMm))
    paint.setColor(Color.BLACK)
    paint.setAntiAlias(false)
    if (type == "hline") {
        canvas.drawRect(x.toFloat(), y.toFloat(), (x + width).toFloat(), (y + lineWidth).toFloat(), paint)
        return
    }
    if (type == "vline") {
        canvas.drawRect(x.toFloat(), y.toFloat(), (x + lineWidth).toFloat(), (y + height).toFloat(), paint)
        return
    }
    if (type == "rect") {
        canvas.drawRect(x.toFloat(), y.toFloat(), (x + width).toFloat(), (y + lineWidth).toFloat(), paint)
        canvas.drawRect(x.toFloat(), (y + height - lineWidth).toFloat(), (x + width).toFloat(), (y + height).toFloat(), paint)
        canvas.drawRect(x.toFloat(), y.toFloat(), (x + lineWidth).toFloat(), (y + height).toFloat(), paint)
        canvas.drawRect((x + width - lineWidth).toFloat(), y.toFloat(), (x + width).toFloat(), (y + height).toFloat(), paint)
    }
}
fun drawBitmapElement(canvas: Canvas, paint: Paint, element: UTSJSONObject, dotsPerMm: Number) {
    val type = labelString(element["type"], "text")
    if (type == "barcode") {
        drawBitmapBarcode(canvas, paint, element, dotsPerMm)
    } else if (type == "hline" || type == "vline" || type == "rect") {
        drawBitmapGraphic(canvas, paint, element, dotsPerMm)
    } else {
        drawBitmapText(canvas, paint, element, dotsPerMm)
    }
}
fun renderLabelBitmap(paperWidthMm: Number, paperHeightMm: Number, dotsPerMm: Number, elements: UTSArray<UTSJSONObject>): Bitmap {
    val width = Math.max(8, Math.round(paperWidthMm * dotsPerMm))
    val height = Math.max(8, Math.round(paperHeightMm * dotsPerMm))
    val bitmap = Bitmap.createBitmap(width.toInt(), height.toInt(), Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint()
    canvas.drawColor(Color.WHITE)
    run {
        var index: Number = 0
        while(index < elements.length){
            drawBitmapElement(canvas, paint, elements[index], dotsPerMm)
            index += 1
        }
    }
    return bitmap
}
fun bitmapToTsplBytes(bitmap: Bitmap): ByteArray {
    val width = bitmap.getWidth()
    val height = bitmap.getHeight()
    val widthBytes = ((width + 7) / 8).toInt()
    val result = ByteArray((widthBytes * height).toInt())
    var y: Int = 0
    while(y < height){
        var xByte: Int = 0
        while(xByte < widthBytes){
            var value: Int = 0
            var bit: Int = 0
            while(bit < 8){
                val x = (xByte * 8 + bit).toInt()
                if (x < width) {
                    val pixel = bitmap.getPixel(x.toInt(), y.toInt())
                    val gray = (Color.red(pixel) + Color.green(pixel) + Color.blue(pixel)) / 3
                    if (gray >= 160) {
                        value = value or (0x80 shr bit)
                    }
                }
                bit += 1
            }
            result[(y * widthBytes + xByte).toInt()] = value.toByte()
            xByte += 1
        }
        y += 1
    }
    return result
}
fun writePrinterBytes(bytes: ByteArray, chunkSize: Int, delayMs: Long) {
    val output = activeSocket!!.getOutputStream()
    var offset: Int = 0
    while(offset < bytes.size){
        var count = chunkSize
        if (offset + count > bytes.size) {
            count = (bytes.size - offset).toInt()
        }
        output.write(bytes, offset.toInt(), count.toInt())
        output.flush()
        if (delayMs > 0) {
            Thread.sleep(delayMs)
        }
        offset += count
    }
}
fun extraOptionInt(options: Any, key: String, fallback: Number): Number {
    try {
        val text = JSON.stringify(options)
        val kObject = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(text), " at uni_modules/lili-label-printer/index.uts:715")
        if (kObject == null) {
            return fallback
        }
        val value = kObject!![key]
        if (value == null) {
            return fallback
        }
        val parsed = parseInt("" + value)
        if (isNaN(parsed)) {
            return fallback
        }
        return parsed
    }
     catch (error: Throwable) {
        return fallback
    }
}
fun bitmapOptionObject(options: Any): UTSJSONObject {
    try {
        val text = JSON.stringify(options)
        if (text == null || text == "") {
            return _uO()
        }
        val kObject = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(text), " at uni_modules/lili-label-printer/index.uts:735")
        if (kObject == null) {
            return _uO()
        }
        return kObject!!
    }
     catch (error: Throwable) {
        return _uO()
    }
}
fun bitmapOptionString(options: UTSJSONObject, key: String, fallback: String): String {
    val value = options[key]
    if (value == null) {
        return fallback
    }
    val text = "" + value
    return if (text == "") {
        fallback
    } else {
        text
    }
}
fun bitmapOptionNumber(options: UTSJSONObject, key: String, fallback: Number): Number {
    val value = options[key]
    if (value == null) {
        return fallback
    }
    val parsed = parseFloat("" + value)
    if (isNaN(parsed)) {
        return fallback
    }
    return parsed
}
fun bitmapOptionElements(options: UTSJSONObject): UTSArray<UTSJSONObject> {
    val value = options["elements"]
    if (value == null) {
        return _uA<UTSJSONObject>()
    }
    try {
        val text = JSON.stringify(value)
        if (text == null || text == "") {
            return _uA<UTSJSONObject>()
        }
        val parsed = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(text), " at uni_modules/lili-label-printer/index.uts:768")
        if (parsed == null) {
            return _uA<UTSJSONObject>()
        }
        return parsed!!
    }
     catch (error: Throwable) {
        return _uA<UTSJSONObject>()
    }
}
fun printLabelBitmap(options: Any): LabelPrinterResult {
    val bitmapOptions = bitmapOptionObject(options)
    val address = bitmapOptionString(bitmapOptions, "address", "")
    val paperWidthMm = bitmapOptionNumber(bitmapOptions, "paperWidthMm", 30)
    val paperHeightMm = bitmapOptionNumber(bitmapOptions, "paperHeightMm", 20)
    val dotsPerMm = bitmapOptionNumber(bitmapOptions, "dotsPerMm", 8)
    val elements = bitmapOptionElements(bitmapOptions)
    if (address == "") {
        return makeResult(false, "ADDRESS_EMPTY", "请先选择蓝牙打印机", null)
    }
    if (elements.length == 0) {
        return makeResult(false, "ELEMENTS_EMPTY", "标签内容不能为空", null)
    }
    if (activeDevice == null || activeSocket == null || !activeSocket!!.isConnected() || activeDevice!!.getAddress() != address) {
        val connectResult = connectPrinter(address)
        if (!connectResult.success) {
            return connectResult
        }
    }
    try {
        var chunkSizeValue = extraOptionInt(bitmapOptions, "chunkSize", 1024)
        if (chunkSizeValue <= 0) {
            chunkSizeValue = 1024
        }
        var delayMsValue = extraOptionInt(bitmapOptions, "delayMs", 20)
        if (delayMsValue < 0) {
            delayMsValue = 20
        }
        val chunkSize = chunkSizeValue.toInt()
        val delayMs = delayMsValue.toLong()
        val bitmap = renderLabelBitmap(paperWidthMm, paperHeightMm, dotsPerMm, elements)
        val bitmapBytes = bitmapToTsplBytes(bitmap)
        val widthBytes = ((bitmap.getWidth() + 7) / 8).toInt()
        var copies = extraOptionInt(options, "copies", 1)
        if (copies <= 0) {
            copies = 1
        }
        val prefix = "SIZE " + paperWidthMm.toString(10) + " mm," + paperHeightMm.toString(10) + " mm\r\nGAP 2 mm,0\r\nDENSITY 8\r\nDIRECTION 1\r\nCLS\r\nBITMAP 0,0," + widthBytes.toString() + "," + bitmap.getHeight().toString() + ",0,"
        val suffix = "\r\nPRINT " + copies.toString(10) + "\r\n"
        writePrinterBytes(prefix.toByteArray(Charset.forName("GBK")), chunkSize, delayMs)
        writePrinterBytes(bitmapBytes, chunkSize, delayMs)
        writePrinterBytes(suffix.toByteArray(Charset.forName("GBK")), chunkSize, delayMs)
        bitmap.recycle()
        return makeResult(true, "PRINTED", "图片标签已发送", null)
    }
     catch (error: Throwable) {
        return makeResult(false, "BITMAP_PRINT_FAILED", "图片打印失败，请检查打印机连接", null)
    }
}
fun printTspl(options: PrintTsplOptions): LabelPrinterResult {
    if (options.address == "") {
        return makeResult(false, "ADDRESS_EMPTY", "请先选择蓝牙打印机", null)
    }
    if (options.tspl == "") {
        return makeResult(false, "TSPL_EMPTY", "打印内容不能为空", null)
    }
    if (activeDevice == null || activeSocket == null || !activeSocket!!.isConnected() || activeDevice!!.getAddress() != options.address) {
        val connectResult = connectPrinter(options.address)
        if (!connectResult.success) {
            return connectResult
        }
    }
    try {
        val charsetName = if (options.charset == null || options.charset == "") {
            "GBK"
        } else {
            options.charset!!
        }
        val chunkSize = (if (options.chunkSize == null || options.chunkSize!! <= 0) {
            256
        } else {
            options.chunkSize!!
        }
        ).toInt()
        val delayMs = (if (options.delayMs == null || options.delayMs!! < 0) {
            20
        } else {
            options.delayMs!!
        }
        ).toLong()
        val bytes = options.tspl.toByteArray(Charset.forName(charsetName))
        val output = activeSocket!!.getOutputStream()
        var offset: Int = 0
        while(offset < bytes.size){
            var count = chunkSize
            if (offset + count > bytes.size) {
                count = (bytes.size - offset).toInt()
            }
            output.write(bytes, offset.toInt(), count.toInt())
            output.flush()
            if (delayMs > 0) {
                Thread.sleep(delayMs)
            }
            offset += count
        }
        return makeResult(true, "PRINTED", "打印指令已发送", null)
    }
     catch (error: Throwable) {
        return makeResult(false, "PRINT_FAILED", "发送打印指令失败，请检查打印机连接", null)
    }
}
open class LabelElement (
    @JsonNotNull
    open var key: String,
    @JsonNotNull
    open var label: String,
    @JsonNotNull
    open var type: String,
    @JsonNotNull
    open var source: String,
    @JsonNotNull
    open var content: String,
    @JsonNotNull
    open var x: Number,
    @JsonNotNull
    open var y: Number,
    @JsonNotNull
    open var width: Number,
    @JsonNotNull
    open var height: Number,
    @JsonNotNull
    open var fontSize: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("LabelElement", "pages/label-print/index.uvue", 240, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return LabelElementReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class LabelElementReactiveObject : LabelElement, IUTSReactive<LabelElement> {
    override var __v_raw: LabelElement
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: LabelElement, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(key = __v_raw.key, label = __v_raw.label, type = __v_raw.type, source = __v_raw.source, content = __v_raw.content, x = __v_raw.x, y = __v_raw.y, width = __v_raw.width, height = __v_raw.height, fontSize = __v_raw.fontSize) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): LabelElementReactiveObject {
        return LabelElementReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
    override var type: String
        get() {
            return _tRG(__v_raw, "type", __v_raw.type, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("type")) {
                return
            }
            val oldValue = __v_raw.type
            __v_raw.type = value
            _tRS(__v_raw, "type", oldValue, value)
        }
    override var source: String
        get() {
            return _tRG(__v_raw, "source", __v_raw.source, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("source")) {
                return
            }
            val oldValue = __v_raw.source
            __v_raw.source = value
            _tRS(__v_raw, "source", oldValue, value)
        }
    override var content: String
        get() {
            return _tRG(__v_raw, "content", __v_raw.content, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("content")) {
                return
            }
            val oldValue = __v_raw.content
            __v_raw.content = value
            _tRS(__v_raw, "content", oldValue, value)
        }
    override var x: Number
        get() {
            return _tRG(__v_raw, "x", __v_raw.x, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("x")) {
                return
            }
            val oldValue = __v_raw.x
            __v_raw.x = value
            _tRS(__v_raw, "x", oldValue, value)
        }
    override var y: Number
        get() {
            return _tRG(__v_raw, "y", __v_raw.y, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("y")) {
                return
            }
            val oldValue = __v_raw.y
            __v_raw.y = value
            _tRS(__v_raw, "y", oldValue, value)
        }
    override var width: Number
        get() {
            return _tRG(__v_raw, "width", __v_raw.width, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("width")) {
                return
            }
            val oldValue = __v_raw.width
            __v_raw.width = value
            _tRS(__v_raw, "width", oldValue, value)
        }
    override var height: Number
        get() {
            return _tRG(__v_raw, "height", __v_raw.height, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("height")) {
                return
            }
            val oldValue = __v_raw.height
            __v_raw.height = value
            _tRS(__v_raw, "height", oldValue, value)
        }
    override var fontSize: Number
        get() {
            return _tRG(__v_raw, "fontSize", __v_raw.fontSize, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("fontSize")) {
                return
            }
            val oldValue = __v_raw.fontSize
            __v_raw.fontSize = value
            _tRS(__v_raw, "fontSize", oldValue, value)
        }
}
open class BarcodeBar (
    @JsonNotNull
    open var key: String,
    @JsonNotNull
    open var style: String,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("BarcodeBar", "pages/label-print/index.uvue", 253, 6)
    }
}
val GenPagesLabelPrintIndexClass = CreateVueComponent(GenPagesLabelPrintIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesLabelPrintIndex.inheritAttrs, inject = GenPagesLabelPrintIndex.inject, props = GenPagesLabelPrintIndex.props, propsNeedCastKeys = GenPagesLabelPrintIndex.propsNeedCastKeys, emits = GenPagesLabelPrintIndex.emits, components = GenPagesLabelPrintIndex.components, styles = GenPagesLabelPrintIndex.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesLabelPrintIndex.setup(props as GenPagesLabelPrintIndex)
    }
    )
}
, fun(instance, renderer): GenPagesLabelPrintIndex {
    return GenPagesLabelPrintIndex(instance, renderer)
}
)
val GenPagesPrinterSettingsIndexClass = CreateVueComponent(GenPagesPrinterSettingsIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesPrinterSettingsIndex.inheritAttrs, inject = GenPagesPrinterSettingsIndex.inject, props = GenPagesPrinterSettingsIndex.props, propsNeedCastKeys = GenPagesPrinterSettingsIndex.propsNeedCastKeys, emits = GenPagesPrinterSettingsIndex.emits, components = GenPagesPrinterSettingsIndex.components, styles = GenPagesPrinterSettingsIndex.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesPrinterSettingsIndex.setup(props as GenPagesPrinterSettingsIndex)
    }
    )
}
, fun(instance, renderer): GenPagesPrinterSettingsIndex {
    return GenPagesPrinterSettingsIndex(instance, renderer)
}
)
open class PrintPreviewField (
    @JsonNotNull
    open var key: String,
    @JsonNotNull
    open var label: String,
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var emphasis: Boolean = false,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PrintPreviewField", "pages/label-templates/index.uvue", 117, 6)
    }
}
val GenPagesLabelTemplatesIndexClass = CreateVueComponent(GenPagesLabelTemplatesIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesLabelTemplatesIndex.inheritAttrs, inject = GenPagesLabelTemplatesIndex.inject, props = GenPagesLabelTemplatesIndex.props, propsNeedCastKeys = GenPagesLabelTemplatesIndex.propsNeedCastKeys, emits = GenPagesLabelTemplatesIndex.emits, components = GenPagesLabelTemplatesIndex.components, styles = GenPagesLabelTemplatesIndex.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesLabelTemplatesIndex.setup(props as GenPagesLabelTemplatesIndex)
    }
    )
}
, fun(instance, renderer): GenPagesLabelTemplatesIndex {
    return GenPagesLabelTemplatesIndex(instance, renderer)
}
)
open class TemplateChoice (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var label: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("TemplateChoice", "pages/label-templates/from.uvue", 135, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return TemplateChoiceReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class TemplateChoiceReactiveObject : TemplateChoice, IUTSReactive<TemplateChoice> {
    override var __v_raw: TemplateChoice
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: TemplateChoice, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(value = __v_raw.value, label = __v_raw.label) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): TemplateChoiceReactiveObject {
        return TemplateChoiceReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
val GenPagesLabelTemplatesFromClass = CreateVueComponent(GenPagesLabelTemplatesFrom::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesLabelTemplatesFrom.inheritAttrs, inject = GenPagesLabelTemplatesFrom.inject, props = GenPagesLabelTemplatesFrom.props, propsNeedCastKeys = GenPagesLabelTemplatesFrom.propsNeedCastKeys, emits = GenPagesLabelTemplatesFrom.emits, components = GenPagesLabelTemplatesFrom.components, styles = GenPagesLabelTemplatesFrom.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesLabelTemplatesFrom.setup(props as GenPagesLabelTemplatesFrom)
    }
    )
}
, fun(instance, renderer): GenPagesLabelTemplatesFrom {
    return GenPagesLabelTemplatesFrom(instance, renderer)
}
)
open class LabelElement__1 (
    @JsonNotNull
    open var key: String,
    @JsonNotNull
    open var label: String,
    @JsonNotNull
    open var type: String,
    @JsonNotNull
    open var source: String,
    @JsonNotNull
    open var content: String,
    @JsonNotNull
    open var barcodeType: String,
    @JsonNotNull
    open var showText: Boolean = false,
    @JsonNotNull
    open var lineWidth: Number,
    @JsonNotNull
    open var x: Number,
    @JsonNotNull
    open var y: Number,
    @JsonNotNull
    open var width: Number,
    @JsonNotNull
    open var height: Number,
    @JsonNotNull
    open var fontSize: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("LabelElement", "uni_modules/lili-label-template-editor/components/lili-label-template-editor/lili-label-template-editor.uvue", 99, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return LabelElement__1ReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class LabelElement__1ReactiveObject : LabelElement__1, IUTSReactive<LabelElement__1> {
    override var __v_raw: LabelElement__1
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: LabelElement__1, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(key = __v_raw.key, label = __v_raw.label, type = __v_raw.type, source = __v_raw.source, content = __v_raw.content, barcodeType = __v_raw.barcodeType, showText = __v_raw.showText, lineWidth = __v_raw.lineWidth, x = __v_raw.x, y = __v_raw.y, width = __v_raw.width, height = __v_raw.height, fontSize = __v_raw.fontSize) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): LabelElement__1ReactiveObject {
        return LabelElement__1ReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
    override var type: String
        get() {
            return _tRG(__v_raw, "type", __v_raw.type, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("type")) {
                return
            }
            val oldValue = __v_raw.type
            __v_raw.type = value
            _tRS(__v_raw, "type", oldValue, value)
        }
    override var source: String
        get() {
            return _tRG(__v_raw, "source", __v_raw.source, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("source")) {
                return
            }
            val oldValue = __v_raw.source
            __v_raw.source = value
            _tRS(__v_raw, "source", oldValue, value)
        }
    override var content: String
        get() {
            return _tRG(__v_raw, "content", __v_raw.content, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("content")) {
                return
            }
            val oldValue = __v_raw.content
            __v_raw.content = value
            _tRS(__v_raw, "content", oldValue, value)
        }
    override var barcodeType: String
        get() {
            return _tRG(__v_raw, "barcodeType", __v_raw.barcodeType, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("barcodeType")) {
                return
            }
            val oldValue = __v_raw.barcodeType
            __v_raw.barcodeType = value
            _tRS(__v_raw, "barcodeType", oldValue, value)
        }
    override var showText: Boolean
        get() {
            return _tRG(__v_raw, "showText", __v_raw.showText, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("showText")) {
                return
            }
            val oldValue = __v_raw.showText
            __v_raw.showText = value
            _tRS(__v_raw, "showText", oldValue, value)
        }
    override var lineWidth: Number
        get() {
            return _tRG(__v_raw, "lineWidth", __v_raw.lineWidth, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("lineWidth")) {
                return
            }
            val oldValue = __v_raw.lineWidth
            __v_raw.lineWidth = value
            _tRS(__v_raw, "lineWidth", oldValue, value)
        }
    override var x: Number
        get() {
            return _tRG(__v_raw, "x", __v_raw.x, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("x")) {
                return
            }
            val oldValue = __v_raw.x
            __v_raw.x = value
            _tRS(__v_raw, "x", oldValue, value)
        }
    override var y: Number
        get() {
            return _tRG(__v_raw, "y", __v_raw.y, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("y")) {
                return
            }
            val oldValue = __v_raw.y
            __v_raw.y = value
            _tRS(__v_raw, "y", oldValue, value)
        }
    override var width: Number
        get() {
            return _tRG(__v_raw, "width", __v_raw.width, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("width")) {
                return
            }
            val oldValue = __v_raw.width
            __v_raw.width = value
            _tRS(__v_raw, "width", oldValue, value)
        }
    override var height: Number
        get() {
            return _tRG(__v_raw, "height", __v_raw.height, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("height")) {
                return
            }
            val oldValue = __v_raw.height
            __v_raw.height = value
            _tRS(__v_raw, "height", oldValue, value)
        }
    override var fontSize: Number
        get() {
            return _tRG(__v_raw, "fontSize", __v_raw.fontSize, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("fontSize")) {
                return
            }
            val oldValue = __v_raw.fontSize
            __v_raw.fontSize = value
            _tRS(__v_raw, "fontSize", oldValue, value)
        }
}
val GenUniModulesLiliLabelTemplateEditorComponentsLiliLabelTemplateEditorLiliLabelTemplateEditorClass = CreateVueComponent(GenUniModulesLiliLabelTemplateEditorComponentsLiliLabelTemplateEditorLiliLabelTemplateEditor::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "component", name = "", inheritAttrs = GenUniModulesLiliLabelTemplateEditorComponentsLiliLabelTemplateEditorLiliLabelTemplateEditor.inheritAttrs, inject = GenUniModulesLiliLabelTemplateEditorComponentsLiliLabelTemplateEditorLiliLabelTemplateEditor.inject, props = GenUniModulesLiliLabelTemplateEditorComponentsLiliLabelTemplateEditorLiliLabelTemplateEditor.props, propsNeedCastKeys = GenUniModulesLiliLabelTemplateEditorComponentsLiliLabelTemplateEditorLiliLabelTemplateEditor.propsNeedCastKeys, emits = GenUniModulesLiliLabelTemplateEditorComponentsLiliLabelTemplateEditorLiliLabelTemplateEditor.emits, components = GenUniModulesLiliLabelTemplateEditorComponentsLiliLabelTemplateEditorLiliLabelTemplateEditor.components, styles = GenUniModulesLiliLabelTemplateEditorComponentsLiliLabelTemplateEditorLiliLabelTemplateEditor.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenUniModulesLiliLabelTemplateEditorComponentsLiliLabelTemplateEditorLiliLabelTemplateEditor.setup(props as GenUniModulesLiliLabelTemplateEditorComponentsLiliLabelTemplateEditorLiliLabelTemplateEditor)
    }
    )
}
, fun(instance, renderer): GenUniModulesLiliLabelTemplateEditorComponentsLiliLabelTemplateEditorLiliLabelTemplateEditor {
    return GenUniModulesLiliLabelTemplateEditorComponentsLiliLabelTemplateEditorLiliLabelTemplateEditor(instance)
}
)
val GenPagesLabelTemplatesDetailsIndexClass = CreateVueComponent(GenPagesLabelTemplatesDetailsIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesLabelTemplatesDetailsIndex.inheritAttrs, inject = GenPagesLabelTemplatesDetailsIndex.inject, props = GenPagesLabelTemplatesDetailsIndex.props, propsNeedCastKeys = GenPagesLabelTemplatesDetailsIndex.propsNeedCastKeys, emits = GenPagesLabelTemplatesDetailsIndex.emits, components = GenPagesLabelTemplatesDetailsIndex.components, styles = GenPagesLabelTemplatesDetailsIndex.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesLabelTemplatesDetailsIndex.setup(props as GenPagesLabelTemplatesDetailsIndex)
    }
    )
}
, fun(instance, renderer): GenPagesLabelTemplatesDetailsIndex {
    return GenPagesLabelTemplatesDetailsIndex(instance, renderer)
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("KsefInvoiceListQuery", "pkg/api/modules/ksef.uts", 2, 13)
    }
}
open class KsefInvoiceItem (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var company: Number,
    @JsonNotNull
    open var subject_type: String,
    @JsonNotNull
    open var ksef_number: String,
    @JsonNotNull
    open var invoice_number: String,
    @JsonNotNull
    open var invoice_type: String,
    @JsonNotNull
    open var seller_name: String,
    @JsonNotNull
    open var seller_nip: String,
    @JsonNotNull
    open var buyer_name: String,
    @JsonNotNull
    open var buyer_nip: String,
    @JsonNotNull
    open var supplier: Number,
    @JsonNotNull
    open var supplier_name: String,
    @JsonNotNull
    open var issue_date: String,
    @JsonNotNull
    open var sale_date: String,
    @JsonNotNull
    open var currency: String,
    @JsonNotNull
    open var net_amount: String,
    @JsonNotNull
    open var tax_amount: String,
    @JsonNotNull
    open var gross_amount: String,
    @JsonNotNull
    open var amount_due: String,
    @JsonNotNull
    open var payment_due_date: String,
    @JsonNotNull
    open var payment_method: String,
    @JsonNotNull
    open var bank_account_number: String,
    @JsonNotNull
    open var bank_name: String,
    @JsonNotNull
    open var is_paid: Boolean = false,
    @JsonNotNull
    open var paid_amount: String,
    @JsonNotNull
    open var paid_at: String,
    @JsonNotNull
    open var remark: String,
    @JsonNotNull
    open var sync_status: String,
    @JsonNotNull
    open var raw_xml_downloaded_at: String,
    @JsonNotNull
    open var files_count: Number,
    @JsonNotNull
    open var pdf_download_url: String,
    @JsonNotNull
    open var created_at: String,
    @JsonNotNull
    open var updated_at: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("KsefInvoiceItem", "pkg/api/modules/ksef.uts", 9, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return KsefInvoiceItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class KsefInvoiceItemReactiveObject : KsefInvoiceItem, IUTSReactive<KsefInvoiceItem> {
    override var __v_raw: KsefInvoiceItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: KsefInvoiceItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, company = __v_raw.company, subject_type = __v_raw.subject_type, ksef_number = __v_raw.ksef_number, invoice_number = __v_raw.invoice_number, invoice_type = __v_raw.invoice_type, seller_name = __v_raw.seller_name, seller_nip = __v_raw.seller_nip, buyer_name = __v_raw.buyer_name, buyer_nip = __v_raw.buyer_nip, supplier = __v_raw.supplier, supplier_name = __v_raw.supplier_name, issue_date = __v_raw.issue_date, sale_date = __v_raw.sale_date, currency = __v_raw.currency, net_amount = __v_raw.net_amount, tax_amount = __v_raw.tax_amount, gross_amount = __v_raw.gross_amount, amount_due = __v_raw.amount_due, payment_due_date = __v_raw.payment_due_date, payment_method = __v_raw.payment_method, bank_account_number = __v_raw.bank_account_number, bank_name = __v_raw.bank_name, is_paid = __v_raw.is_paid, paid_amount = __v_raw.paid_amount, paid_at = __v_raw.paid_at, remark = __v_raw.remark, sync_status = __v_raw.sync_status, raw_xml_downloaded_at = __v_raw.raw_xml_downloaded_at, files_count = __v_raw.files_count, pdf_download_url = __v_raw.pdf_download_url, created_at = __v_raw.created_at, updated_at = __v_raw.updated_at) {
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
    override var subject_type: String
        get() {
            return _tRG(__v_raw, "subject_type", __v_raw.subject_type, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("subject_type")) {
                return
            }
            val oldValue = __v_raw.subject_type
            __v_raw.subject_type = value
            _tRS(__v_raw, "subject_type", oldValue, value)
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
    override var invoice_type: String
        get() {
            return _tRG(__v_raw, "invoice_type", __v_raw.invoice_type, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("invoice_type")) {
                return
            }
            val oldValue = __v_raw.invoice_type
            __v_raw.invoice_type = value
            _tRS(__v_raw, "invoice_type", oldValue, value)
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
    override var sale_date: String
        get() {
            return _tRG(__v_raw, "sale_date", __v_raw.sale_date, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("sale_date")) {
                return
            }
            val oldValue = __v_raw.sale_date
            __v_raw.sale_date = value
            _tRS(__v_raw, "sale_date", oldValue, value)
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
    override var payment_due_date: String
        get() {
            return _tRG(__v_raw, "payment_due_date", __v_raw.payment_due_date, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("payment_due_date")) {
                return
            }
            val oldValue = __v_raw.payment_due_date
            __v_raw.payment_due_date = value
            _tRS(__v_raw, "payment_due_date", oldValue, value)
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
    override var bank_account_number: String
        get() {
            return _tRG(__v_raw, "bank_account_number", __v_raw.bank_account_number, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("bank_account_number")) {
                return
            }
            val oldValue = __v_raw.bank_account_number
            __v_raw.bank_account_number = value
            _tRS(__v_raw, "bank_account_number", oldValue, value)
        }
    override var bank_name: String
        get() {
            return _tRG(__v_raw, "bank_name", __v_raw.bank_name, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("bank_name")) {
                return
            }
            val oldValue = __v_raw.bank_name
            __v_raw.bank_name = value
            _tRS(__v_raw, "bank_name", oldValue, value)
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
    override var paid_amount: String
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
    override var paid_at: String
        get() {
            return _tRG(__v_raw, "paid_at", __v_raw.paid_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("paid_at")) {
                return
            }
            val oldValue = __v_raw.paid_at
            __v_raw.paid_at = value
            _tRS(__v_raw, "paid_at", oldValue, value)
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
    override var pdf_download_url: String
        get() {
            return _tRG(__v_raw, "pdf_download_url", __v_raw.pdf_download_url, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("pdf_download_url")) {
                return
            }
            val oldValue = __v_raw.pdf_download_url
            __v_raw.pdf_download_url = value
            _tRS(__v_raw, "pdf_download_url", oldValue, value)
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
open class KsefInvoiceDetail (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var company: Number,
    @JsonNotNull
    open var subject_type: String,
    @JsonNotNull
    open var ksef_number: String,
    @JsonNotNull
    open var invoice_number: String,
    @JsonNotNull
    open var invoice_type: String,
    @JsonNotNull
    open var seller_name: String,
    @JsonNotNull
    open var seller_nip: String,
    @JsonNotNull
    open var buyer_name: String,
    @JsonNotNull
    open var buyer_nip: String,
    @JsonNotNull
    open var supplier: Number,
    @JsonNotNull
    open var supplier_name: String,
    @JsonNotNull
    open var issue_date: String,
    @JsonNotNull
    open var sale_date: String,
    @JsonNotNull
    open var currency: String,
    @JsonNotNull
    open var net_amount: String,
    @JsonNotNull
    open var tax_amount: String,
    @JsonNotNull
    open var gross_amount: String,
    @JsonNotNull
    open var amount_due: String,
    @JsonNotNull
    open var payment_due_date: String,
    @JsonNotNull
    open var payment_method: String,
    @JsonNotNull
    open var bank_account_number: String,
    @JsonNotNull
    open var bank_name: String,
    @JsonNotNull
    open var is_paid: Boolean = false,
    @JsonNotNull
    open var paid_amount: String,
    @JsonNotNull
    open var paid_at: String,
    @JsonNotNull
    open var remark: String,
    @JsonNotNull
    open var sync_status: String,
    @JsonNotNull
    open var raw_xml_downloaded_at: String,
    @JsonNotNull
    open var files_count: Number,
    @JsonNotNull
    open var pdf_download_url: String,
    @JsonNotNull
    open var created_at: String,
    @JsonNotNull
    open var updated_at: String,
    @JsonNotNull
    open var metadata: UTSJSONObject,
    @JsonNotNull
    open var raw_xml: String,
    @JsonNotNull
    open var last_error: String,
    @JsonNotNull
    open var payment_note: String,
    @JsonNotNull
    open var xml_summary: UTSJSONObject,
    @JsonNotNull
    open var media_files: UTSArray<UTSJSONObject>,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("KsefInvoiceDetail", "pkg/api/modules/ksef.uts", 44, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return KsefInvoiceDetailReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class KsefInvoiceDetailReactiveObject : KsefInvoiceDetail, IUTSReactive<KsefInvoiceDetail> {
    override var __v_raw: KsefInvoiceDetail
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: KsefInvoiceDetail, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, company = __v_raw.company, subject_type = __v_raw.subject_type, ksef_number = __v_raw.ksef_number, invoice_number = __v_raw.invoice_number, invoice_type = __v_raw.invoice_type, seller_name = __v_raw.seller_name, seller_nip = __v_raw.seller_nip, buyer_name = __v_raw.buyer_name, buyer_nip = __v_raw.buyer_nip, supplier = __v_raw.supplier, supplier_name = __v_raw.supplier_name, issue_date = __v_raw.issue_date, sale_date = __v_raw.sale_date, currency = __v_raw.currency, net_amount = __v_raw.net_amount, tax_amount = __v_raw.tax_amount, gross_amount = __v_raw.gross_amount, amount_due = __v_raw.amount_due, payment_due_date = __v_raw.payment_due_date, payment_method = __v_raw.payment_method, bank_account_number = __v_raw.bank_account_number, bank_name = __v_raw.bank_name, is_paid = __v_raw.is_paid, paid_amount = __v_raw.paid_amount, paid_at = __v_raw.paid_at, remark = __v_raw.remark, sync_status = __v_raw.sync_status, raw_xml_downloaded_at = __v_raw.raw_xml_downloaded_at, files_count = __v_raw.files_count, pdf_download_url = __v_raw.pdf_download_url, created_at = __v_raw.created_at, updated_at = __v_raw.updated_at, metadata = __v_raw.metadata, raw_xml = __v_raw.raw_xml, last_error = __v_raw.last_error, payment_note = __v_raw.payment_note, xml_summary = __v_raw.xml_summary, media_files = __v_raw.media_files) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): KsefInvoiceDetailReactiveObject {
        return KsefInvoiceDetailReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
    override var subject_type: String
        get() {
            return _tRG(__v_raw, "subject_type", __v_raw.subject_type, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("subject_type")) {
                return
            }
            val oldValue = __v_raw.subject_type
            __v_raw.subject_type = value
            _tRS(__v_raw, "subject_type", oldValue, value)
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
    override var invoice_type: String
        get() {
            return _tRG(__v_raw, "invoice_type", __v_raw.invoice_type, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("invoice_type")) {
                return
            }
            val oldValue = __v_raw.invoice_type
            __v_raw.invoice_type = value
            _tRS(__v_raw, "invoice_type", oldValue, value)
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
    override var sale_date: String
        get() {
            return _tRG(__v_raw, "sale_date", __v_raw.sale_date, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("sale_date")) {
                return
            }
            val oldValue = __v_raw.sale_date
            __v_raw.sale_date = value
            _tRS(__v_raw, "sale_date", oldValue, value)
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
    override var payment_due_date: String
        get() {
            return _tRG(__v_raw, "payment_due_date", __v_raw.payment_due_date, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("payment_due_date")) {
                return
            }
            val oldValue = __v_raw.payment_due_date
            __v_raw.payment_due_date = value
            _tRS(__v_raw, "payment_due_date", oldValue, value)
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
    override var bank_account_number: String
        get() {
            return _tRG(__v_raw, "bank_account_number", __v_raw.bank_account_number, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("bank_account_number")) {
                return
            }
            val oldValue = __v_raw.bank_account_number
            __v_raw.bank_account_number = value
            _tRS(__v_raw, "bank_account_number", oldValue, value)
        }
    override var bank_name: String
        get() {
            return _tRG(__v_raw, "bank_name", __v_raw.bank_name, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("bank_name")) {
                return
            }
            val oldValue = __v_raw.bank_name
            __v_raw.bank_name = value
            _tRS(__v_raw, "bank_name", oldValue, value)
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
    override var paid_amount: String
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
    override var paid_at: String
        get() {
            return _tRG(__v_raw, "paid_at", __v_raw.paid_at, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("paid_at")) {
                return
            }
            val oldValue = __v_raw.paid_at
            __v_raw.paid_at = value
            _tRS(__v_raw, "paid_at", oldValue, value)
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
    override var pdf_download_url: String
        get() {
            return _tRG(__v_raw, "pdf_download_url", __v_raw.pdf_download_url, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("pdf_download_url")) {
                return
            }
            val oldValue = __v_raw.pdf_download_url
            __v_raw.pdf_download_url = value
            _tRS(__v_raw, "pdf_download_url", oldValue, value)
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
    override var metadata: UTSJSONObject
        get() {
            return _tRG(__v_raw, "metadata", __v_raw.metadata, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("metadata")) {
                return
            }
            val oldValue = __v_raw.metadata
            __v_raw.metadata = value
            _tRS(__v_raw, "metadata", oldValue, value)
        }
    override var raw_xml: String
        get() {
            return _tRG(__v_raw, "raw_xml", __v_raw.raw_xml, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("raw_xml")) {
                return
            }
            val oldValue = __v_raw.raw_xml
            __v_raw.raw_xml = value
            _tRS(__v_raw, "raw_xml", oldValue, value)
        }
    override var last_error: String
        get() {
            return _tRG(__v_raw, "last_error", __v_raw.last_error, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("last_error")) {
                return
            }
            val oldValue = __v_raw.last_error
            __v_raw.last_error = value
            _tRS(__v_raw, "last_error", oldValue, value)
        }
    override var payment_note: String
        get() {
            return _tRG(__v_raw, "payment_note", __v_raw.payment_note, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("payment_note")) {
                return
            }
            val oldValue = __v_raw.payment_note
            __v_raw.payment_note = value
            _tRS(__v_raw, "payment_note", oldValue, value)
        }
    override var xml_summary: UTSJSONObject
        get() {
            return _tRG(__v_raw, "xml_summary", __v_raw.xml_summary, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("xml_summary")) {
                return
            }
            val oldValue = __v_raw.xml_summary
            __v_raw.xml_summary = value
            _tRS(__v_raw, "xml_summary", oldValue, value)
        }
    override var media_files: UTSArray<UTSJSONObject>
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
open class KsefPaymentUpdateData (
    @JsonNotNull
    open var is_paid: Boolean = false,
    @JsonNotNull
    open var paid_amount: String,
    open var paid_at: String? = null,
    @JsonNotNull
    open var payment_note: String,
    @JsonNotNull
    open var remark: String,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("KsefPaymentUpdateData", "pkg/api/modules/ksef.uts", 85, 13)
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("KsefInvoiceListResponse", "pkg/api/modules/ksef.uts", 92, 13)
    }
}
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("KsefAutoSyncStatus", "pkg/api/modules/ksef.uts", 101, 13)
    }
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
fun stringValue__9(value: Any?): String {
    if (value == null) {
        return ""
    }
    return "" + value
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
fun boolValue__2(value: Any?): Boolean {
    if (value == null) {
        return false
    }
    val text = ("" + value).toLowerCase()
    return text == "true" || text == "1"
}
fun objectValue__1(value: Any?): UTSJSONObject {
    if (value == null) {
        return _uO()
    }
    val text = JSON.stringify(value)
    val parsed = if (text == null || text == "") {
        null
    } else {
        UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(text), " at pkg/api/modules/ksef.uts:151")
    }
    if (parsed == null) {
        return _uO()
    }
    return parsed!!
}
fun objectArrayValue(value: Any?): UTSArray<UTSJSONObject> {
    if (value == null) {
        return _uA<UTSJSONObject>()
    }
    val text = JSON.stringify(value)
    val parsed = if (text == null || text == "") {
        null
    } else {
        UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(text), " at pkg/api/modules/ksef.uts:162")
    }
    if (parsed == null) {
        return _uA<UTSJSONObject>()
    }
    return parsed!!
}
fun buildListQuery__3(data: KsefInvoiceListQuery): UTSJSONObject {
    val query: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("query", "pkg/api/modules/ksef.uts", 169, 11), "page" to data.page, "page_size" to data.page_size)
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
    return KsefInvoiceItem(id = intValue__8(rawObject["id"]), company = intValue__8(rawObject["company"]), subject_type = stringValue__9(rawObject["subject_type"]), ksef_number = stringValue__9(rawObject["ksef_number"]), invoice_number = stringValue__9(rawObject["invoice_number"]), invoice_type = stringValue__9(rawObject["invoice_type"]), seller_name = stringValue__9(rawObject["seller_name"]), seller_nip = stringValue__9(rawObject["seller_nip"]), buyer_name = stringValue__9(rawObject["buyer_name"]), buyer_nip = stringValue__9(rawObject["buyer_nip"]), supplier = intValue__8(rawObject["supplier"]), supplier_name = stringValue__9(rawObject["supplier_name"]), issue_date = stringValue__9(rawObject["issue_date"]), sale_date = stringValue__9(rawObject["sale_date"]), currency = stringValue__9(rawObject["currency"]), net_amount = stringValue__9(rawObject["net_amount"]), tax_amount = stringValue__9(rawObject["tax_amount"]), gross_amount = stringValue__9(rawObject["gross_amount"]), amount_due = stringValue__9(rawObject["amount_due"]), payment_due_date = stringValue__9(rawObject["payment_due_date"]), payment_method = stringValue__9(rawObject["payment_method"]), bank_account_number = stringValue__9(rawObject["bank_account_number"]), bank_name = stringValue__9(rawObject["bank_name"]), is_paid = boolValue__2(rawObject["is_paid"]), paid_amount = stringValue__9(rawObject["paid_amount"]), paid_at = stringValue__9(rawObject["paid_at"]), remark = stringValue__9(rawObject["remark"]), sync_status = stringValue__9(rawObject["sync_status"]), raw_xml_downloaded_at = stringValue__9(rawObject["raw_xml_downloaded_at"]), files_count = intValue__8(rawObject["files_count"]), pdf_download_url = stringValue__9(rawObject["pdf_download_url"]), created_at = stringValue__9(rawObject["created_at"]), updated_at = stringValue__9(rawObject["updated_at"]))
}
fun buildInvoiceDetail(raw: Any): KsefInvoiceDetail {
    val rawText = JSON.stringify(raw)
    val rawObject = if (rawText == null || rawText == "") {
        null
    } else {
        UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(rawText), " at pkg/api/modules/ksef.uts:223")
    }
    if (rawObject == null) {
        throw UTSError("KSeF 发票详情解析失败")
    }
    val item = buildInvoiceItem(rawObject!!)
    return KsefInvoiceDetail(id = item.id, company = item.company, subject_type = item.subject_type, ksef_number = item.ksef_number, invoice_number = item.invoice_number, invoice_type = item.invoice_type, seller_name = item.seller_name, seller_nip = item.seller_nip, buyer_name = item.buyer_name, buyer_nip = item.buyer_nip, supplier = item.supplier, supplier_name = item.supplier_name, issue_date = item.issue_date, sale_date = item.sale_date, currency = item.currency, net_amount = item.net_amount, tax_amount = item.tax_amount, gross_amount = item.gross_amount, amount_due = item.amount_due, payment_due_date = item.payment_due_date, payment_method = item.payment_method, bank_account_number = item.bank_account_number, bank_name = item.bank_name, is_paid = item.is_paid, paid_amount = item.paid_amount, paid_at = item.paid_at, remark = item.remark, sync_status = item.sync_status, raw_xml_downloaded_at = item.raw_xml_downloaded_at, files_count = item.files_count, pdf_download_url = item.pdf_download_url, created_at = item.created_at, updated_at = item.updated_at, metadata = objectValue__1(rawObject!!["metadata"]), raw_xml = stringValue__9(rawObject!!["raw_xml"]), last_error = stringValue__9(rawObject!!["last_error"]), payment_note = stringValue__9(rawObject!!["payment_note"]), xml_summary = objectValue__1(rawObject!!["xml_summary"]), media_files = objectArrayValue(rawObject!!["media_files"]))
}
fun buildInvoiceItems(value: Any?): UTSArray<KsefInvoiceItem> {
    if (value == null) {
        return _uA()
    }
    val text = JSON.stringify(value)
    val rawArray = if (text == null || text == "") {
        null
    } else {
        UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(text), " at pkg/api/modules/ksef.uts:275")
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
        UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(rawText), " at pkg/api/modules/ksef.uts:287")
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
            UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(paginationText), " at pkg/api/modules/ksef.uts:295")
        }
    }
    val results = buildInvoiceItems(rawObject!!["results"])
    var totalCount = intValue__8(rawObject!!["count"])
    if (totalCount <= 0) {
        totalCount = intValue__8(rawObject!!["total_count"])
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
    var currentPage = intValue__8(rawObject!!["current_page"])
    if (currentPage <= 0) {
        currentPage = intValue__8(rawObject!!["page"])
    }
    if (currentPage <= 0 && paginationObject != null) {
        currentPage = intValue__8(paginationObject!!["page"])
    }
    if (currentPage <= 0) {
        currentPage = query.page
    }
    var pageSize = intValue__8(rawObject!!["page_size"])
    if (pageSize <= 0 && paginationObject != null) {
        pageSize = intValue__8(paginationObject!!["page_size"])
    }
    if (pageSize <= 0) {
        pageSize = query.page_size
    }
    var totalPages = intValue__8(rawObject!!["total_pages"])
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__8(paginationObject!!["total_pages"])
    }
    if (totalPages <= 0 && pageSize > 0) {
        totalPages = Math.ceil(totalCount / pageSize)
    }
    if (totalPages <= 0) {
        totalPages = 1
    }
    var summary: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("summary", "pkg/api/modules/ksef.uts", 326, 9))
    val rawSummary = rawObject!!["summary"]
    if (rawSummary != null) {
        val summaryText = JSON.stringify(rawSummary)
        val parsedSummary = if (summaryText == null || summaryText == "") {
            null
        } else {
            UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(summaryText), " at pkg/api/modules/ksef.uts:330")
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
        UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(rawText), " at pkg/api/modules/ksef.uts:347")
    }
    if (rawObject == null) {
        throw UTSError("KSeF 自动同步状态解析失败")
    }
    return KsefAutoSyncStatus(enabled = boolValue__2(rawObject!!["enabled"]), metadata_interval_seconds = floatValue(rawObject!!["metadata_interval_seconds"]), xml_interval_seconds = floatValue(rawObject!!["xml_interval_seconds"]), xml_batch_size = intValue__8(rawObject!!["xml_batch_size"]), xml_delay_seconds = floatValue(rawObject!!["xml_delay_seconds"]), pending_xml_count = intValue__8(rawObject!!["pending_xml_count"]), last_success_at = stringValue__9(rawObject!!["last_success_at"]), last_success_requested_to = stringValue__9(rawObject!!["last_success_requested_to"]), last_failed_at = stringValue__9(rawObject!!["last_failed_at"]), last_failed_message = stringValue__9(rawObject!!["last_failed_message"]))
}
fun getKsefInvoiceList(data: KsefInvoiceListQuery): UTSPromise<KsefInvoiceListResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/procurement/ksef-invoices/", "GET", buildListQuery__3(data), true))
            return@w buildListResponse(raw, data)
    })
}
fun getKsefInvoiceDetail(id: Any): UTSPromise<KsefInvoiceDetail> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/procurement/ksef-invoices/" + stringValue__9(id) + "/", "GET", _uO(), true))
            return@w buildInvoiceDetail(raw)
    })
}
fun updateKsefInvoicePayment(id: Any, data: KsefPaymentUpdateData): UTSPromise<KsefInvoiceDetail> {
    return wrapUTSPromise(suspend w@{
            val payload: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("payload", "pkg/api/modules/ksef.uts", 373, 11), "is_paid" to data.is_paid, "paid_amount" to data.paid_amount, "paid_at" to data.paid_at, "payment_note" to data.payment_note, "remark" to data.remark)
            val raw = await(request("/api/procurement/ksef-invoices/" + stringValue__9(id) + "/update_payment/", "PATCH", payload, true))
            return@w buildInvoiceDetail(raw)
    })
}
fun linkKsefInvoiceSupplier(id: Any, supplierId: String?): UTSPromise<KsefInvoiceDetail> {
    return wrapUTSPromise(suspend w@{
            val payload: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("payload", "pkg/api/modules/ksef.uts", 384, 11))
            if (supplierId == null || supplierId == "") {
                payload["supplier_id"] = null
            } else {
                payload["supplier_id"] = supplierId
            }
            val raw = await(request("/api/procurement/ksef-invoices/" + stringValue__9(id) + "/link-supplier/", "POST", payload, true))
            return@w buildInvoiceDetail(raw)
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
    return request("/api/procurement/ksef-invoices/" + stringValue__9(id) + "/download_xml/", "POST", _uO(), true)
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
val GenPagesKsefDetailClass = CreateVueComponent(GenPagesKsefDetail::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesKsefDetail.inheritAttrs, inject = GenPagesKsefDetail.inject, props = GenPagesKsefDetail.props, propsNeedCastKeys = GenPagesKsefDetail.propsNeedCastKeys, emits = GenPagesKsefDetail.emits, components = GenPagesKsefDetail.components, styles = GenPagesKsefDetail.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesKsefDetail.setup(props as GenPagesKsefDetail)
    }
    )
}
, fun(instance, renderer): GenPagesKsefDetail {
    return GenPagesKsefDetail(instance, renderer)
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ExpenseListQuery", "pkg/api/modules/expenses.uts", 2, 13)
    }
}
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ExpenseMediaFile", "pkg/api/modules/expenses.uts", 15, 13)
    }
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ExpenseItem", "pkg/api/modules/expenses.uts", 33, 13)
    }
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ExpenseListResponse", "pkg/api/modules/expenses.uts", 50, 13)
    }
}
open class ExpenseOptionItem (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var label: String,
    @JsonNotNull
    open var extra: UTSJSONObject,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ExpenseOptionItem", "pkg/api/modules/expenses.uts", 59, 13)
    }
}
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ExpenseOptionGroup", "pkg/api/modules/expenses.uts", 64, 13)
    }
}
open class ExpenseOptionsResponse (
    @JsonNotNull
    open var resource: String,
    @JsonNotNull
    open var total_groups: Number,
    @JsonNotNull
    open var groups: UTSArray<ExpenseOptionGroup>,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ExpenseOptionsResponse", "pkg/api/modules/expenses.uts", 71, 13)
    }
}
open class ExpenseFilterOption (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var label: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ExpenseFilterOption", "pkg/api/modules/expenses.uts", 76, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ExpenseFilterOptionReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ExpenseFilterOptionReactiveObject : ExpenseFilterOption, IUTSReactive<ExpenseFilterOption> {
    override var __v_raw: ExpenseFilterOption
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ExpenseFilterOption, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(value = __v_raw.value, label = __v_raw.label) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ExpenseFilterOptionReactiveObject {
        return ExpenseFilterOptionReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
open class ExpenseFilterDefinition (
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
    open var options: UTSArray<ExpenseFilterOption>,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ExpenseFilterDefinition", "pkg/api/modules/expenses.uts", 80, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ExpenseFilterDefinitionReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ExpenseFilterDefinitionReactiveObject : ExpenseFilterDefinition, IUTSReactive<ExpenseFilterDefinition> {
    override var __v_raw: ExpenseFilterDefinition
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ExpenseFilterDefinition, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(key = __v_raw.key, param = __v_raw.param, label = __v_raw.label, control = __v_raw.control, aliases = __v_raw.aliases, multiple = __v_raw.multiple, options = __v_raw.options) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ExpenseFilterDefinitionReactiveObject {
        return ExpenseFilterDefinitionReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
    override var options: UTSArray<ExpenseFilterOption>
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
open class ExpenseFilterOptionsResponse (
    @JsonNotNull
    open var resource: String,
    @JsonNotNull
    open var count: Number,
    @JsonNotNull
    open var filters: UTSArray<ExpenseFilterDefinition>,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ExpenseFilterOptionsResponse", "pkg/api/modules/expenses.uts", 89, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ExpenseFilterOptionsResponseReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ExpenseFilterOptionsResponseReactiveObject : ExpenseFilterOptionsResponse, IUTSReactive<ExpenseFilterOptionsResponse> {
    override var __v_raw: ExpenseFilterOptionsResponse
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ExpenseFilterOptionsResponse, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(resource = __v_raw.resource, count = __v_raw.count, filters = __v_raw.filters) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ExpenseFilterOptionsResponseReactiveObject {
        return ExpenseFilterOptionsResponseReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
    override var filters: UTSArray<ExpenseFilterDefinition>
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ExpenseMutationData", "pkg/api/modules/expenses.uts", 94, 13)
    }
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
    if (value == null) {
        return 0
    }
    val parsed = parseInt("" + value)
    if (isNaN(parsed)) {
        return 0
    }
    return parsed
}
fun stringValue__10(value: Any?): String {
    if (value == null) {
        return ""
    }
    return "" + value
}
fun boolValue__3(value: Any?): Boolean {
    return stringValue__10(value) == "true"
}
fun parseObject__5(value: Any?): UTSJSONObject? {
    if (value == null) {
        return null
    }
    val text = JSON.stringify(value)
    if (text == null || text == "") {
        return null
    }
    try {
        return UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(text), " at pkg/api/modules/expenses.uts:139")
    }
     catch (error: Throwable) {
        return null
    }
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
        parsed = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(text), " at pkg/api/modules/expenses.uts:153")
    }
     catch (error: Throwable) {
        return _uA<UTSJSONObject>()
    }
    if (parsed == null) {
        return _uA<UTSJSONObject>()
    }
    return parsed!!
}
fun stringArrayValue__3(value: Any?): UTSArray<String> {
    if (value == null) {
        return _uA<String>()
    }
    val text = JSON.stringify(value)
    if (text == null || text == "") {
        return _uA<String>()
    }
    var parsed: UTSArray<Any>? = null
    try {
        parsed = UTSAndroid.consoleDebugError(JSON.parseArray<Any>(text), " at pkg/api/modules/expenses.uts:170")
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
fun buildExpenseListQuery(data: ExpenseListQuery): UTSJSONObject {
    val query: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("query", "pkg/api/modules/expenses.uts", 188, 11), "page" to data.page, "page_size" to data.page_size)
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
    return ExpenseMediaFile(id = stringValue__10(rawObject["id"]), company = intValue__9(rawObject["company"]), original_filename = stringValue__10(rawObject["original_filename"]), file_type = stringValue__10(rawObject["file_type"]), file_type_display = stringValue__10(rawObject["file_type_display"]), mime_type = stringValue__10(rawObject["mime_type"]), file_size = intValue__9(rawObject["file_size"]), file_size_display = stringValue__10(rawObject["file_size_display"]), file_url = normalizeServerUrl__5(stringValue__10(rawObject["file_url"])), thumbnail_url = normalizeServerUrl__5(stringValue__10(rawObject["thumbnail_url"])), signed_url = normalizeServerUrl__5(stringValue__10(rawObject["signed_url"])), signed_thumbnail_url = normalizeServerUrl__5(stringValue__10(rawObject["signed_thumbnail_url"])), object_id = stringValue__10(rawObject["object_id"]), is_deleted = boolValue__3(rawObject["is_deleted"]), created_at = stringValue__10(rawObject["created_at"]), updated_at = stringValue__10(rawObject["updated_at"]))
}
fun buildMediaFilesFromValue(value: Any?): UTSArray<ExpenseMediaFile> {
    if (value == null) {
        return _uA()
    }
    val text = JSON.stringify(value)
    val rawArray = if (text == null || text == "") {
        null
    } else {
        UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(text), " at pkg/api/modules/expenses.uts:233")
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
    return ExpenseItem(id = intValue__9(rawObject["id"]), expenditure_type = intValue__9(rawObject["expenditure_type"]), expenditure_type_name = stringValue__10(rawObject["expenditure_type_name"]), amount = stringValue__10(rawObject["amount"]), expenditure_date = stringValue__10(rawObject["expenditure_date"]), invoice_number = stringValue__10(rawObject["invoice_number"]), supplier = intValue__9(rawObject["supplier"]), supplier_name = stringValue__10(rawObject["supplier_name"]), description = stringValue__10(rawObject["description"]), note = stringValue__10(rawObject["note"]), media_files = buildMediaFilesFromValue(rawObject["media_files"]), files_count = intValue__9(rawObject["files_count"]), is_deleted = boolValue__3(rawObject["is_deleted"]), created_at = stringValue__10(rawObject["created_at"]), updated_at = stringValue__10(rawObject["updated_at"]))
}
fun buildExpenseItemsFromValue(value: Any?): UTSArray<ExpenseItem> {
    if (value == null) {
        return _uA()
    }
    val text = JSON.stringify(value)
    val rawArray = if (text == null || text == "") {
        null
    } else {
        UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(text), " at pkg/api/modules/expenses.uts:265")
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
    val rawObject = parseObject__5(raw)
    if (rawObject == null) {
        throw UTSError("支出接口响应解析失败")
    }
    val dataValue = rawObject!!["data"]
    val dataObject = parseObject__5(dataValue)
    if (dataObject != null) {
        return dataObject!!
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
            paginationObject = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(paginationText), " at pkg/api/modules/expenses.uts:291")
        }
    }
    val results = buildExpenseItemsFromValue(rawObject["results"])
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
            UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(groupsText), " at pkg/api/modules/expenses.uts:338")
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
                            UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(itemsText), " at pkg/api/modules/expenses.uts:346")
                        }
                        if (itemObjects != null) {
                            run {
                                var itemIndex: Number = 0
                                while(itemIndex < itemObjects!!.length){
                                    val itemObject = itemObjects!![itemIndex]
                                    items.push(ExpenseOptionItem(value = stringValue__10(itemObject["value"]), label = stringValue__10(itemObject["label"]), extra = if (itemObject["extra"] == null) {
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
                    groups.push(ExpenseOptionGroup(key = stringValue__10(groupObject["key"]), label = stringValue__10(groupObject["label"]), control = stringValue__10(groupObject["control"]), count = intValue__9(groupObject["count"]), items = items))
                    groupIndex += 1
                }
            }
        }
    }
    return ExpenseOptionsResponse(resource = stringValue__10(rawObject["resource"]), total_groups = intValue__9(rawObject["total_groups"]), groups = groups)
}
fun buildExpenseFilterOptionsResponse(raw: Any): ExpenseFilterOptionsResponse {
    val rawObject = rawDataObject__1(raw)
    var filters: UTSArray<ExpenseFilterDefinition> = _uA()
    val filterObjects = parseObjectArray__3(rawObject["filters"])
    if (filterObjects.length > 0) {
        val nextFilters: UTSArray<ExpenseFilterDefinition> = _uA()
        run {
            var filterIndex: Number = 0
            while(filterIndex < filterObjects.length){
                val filterObject = filterObjects[filterIndex]
                val optionObjects = parseObjectArray__3(filterObject["options"])
                val options: UTSArray<ExpenseFilterOption> = _uA()
                run {
                    var optionIndex: Number = 0
                    while(optionIndex < optionObjects.length){
                        val optionObject = optionObjects[optionIndex]
                        options.push(ExpenseFilterOption(value = stringValue__10(optionObject["value"]), label = stringValue__10(optionObject["label"])))
                        optionIndex += 1
                    }
                }
                nextFilters.push(ExpenseFilterDefinition(key = stringValue__10(filterObject["key"]), param = stringValue__10(filterObject["param"]), label = stringValue__10(filterObject["label"]), control = stringValue__10(filterObject["control"]), aliases = stringArrayValue__3(filterObject["aliases"]), multiple = stringValue__10(filterObject["multiple"]) == "true", options = options))
                filterIndex += 1
            }
        }
        filters = nextFilters
    }
    return ExpenseFilterOptionsResponse(resource = stringValue__10(rawObject["resource"]), count = intValue__9(rawObject["count"]), filters = filters)
}
fun buildMutationBody(data: ExpenseMutationData): UTSJSONObject {
    val body: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("body", "pkg/api/modules/expenses.uts", 396, 11), "amount" to data.amount, "expenditure_date" to data.expenditure_date, "invoice_number" to if (data.invoice_number == null) {
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
    return "/api/expenses/expenditures/" + stringValue__10(id) + "/"
}
fun getExpenseList(data: ExpenseListQuery): UTSPromise<ExpenseListResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/expenses/expenditures/", "GET", buildExpenseListQuery(data), true))
            return@w buildExpenseListResponse(raw, data)
    })
}
fun getExpenseOptions(key: String? = null, search: String? = null, limit: Number = 20): UTSPromise<ExpenseOptionsResponse> {
    return wrapUTSPromise(suspend w@{
            val query: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("query", "pkg/api/modules/expenses.uts", 421, 11))
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
fun getExpenseFilterOptions(): UTSPromise<ExpenseFilterOptionsResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/expenses/expenditures/filter-options/", "GET", _uO(), true))
            return@w buildExpenseFilterOptionsResponse(raw)
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
open class ExpenseSelectedFilter (
    @JsonNotNull
    open var param: String,
    @JsonNotNull
    open var value: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ExpenseSelectedFilter", "pages/expenses/index.uvue", 128, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ExpenseSelectedFilterReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ExpenseSelectedFilterReactiveObject : ExpenseSelectedFilter, IUTSReactive<ExpenseSelectedFilter> {
    override var __v_raw: ExpenseSelectedFilter
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ExpenseSelectedFilter, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(param = __v_raw.param, value = __v_raw.value) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ExpenseSelectedFilterReactiveObject {
        return ExpenseSelectedFilterReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SelectOption", "pages/expenses/from.uvue", 41, 6)
    }
}
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PurchaseListQuery", "pkg/api/modules/purchases.uts", 2, 13)
    }
}
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
    @JsonNotNull
    open var supplier_excel_import_status: UTSJSONObject,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PurchaseItem", "pkg/api/modules/purchases.uts", 14, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return PurchaseItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class PurchaseItemReactiveObject : PurchaseItem, IUTSReactive<PurchaseItem> {
    override var __v_raw: PurchaseItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: PurchaseItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, purchase_number = __v_raw.purchase_number, purchase_date = __v_raw.purchase_date, status = __v_raw.status, status_display = __v_raw.status_display, shop = __v_raw.shop, shop_name = __v_raw.shop_name, supplier = __v_raw.supplier, supplier_name = __v_raw.supplier_name, total_quantity = __v_raw.total_quantity, received_quantity = __v_raw.received_quantity, total_amount = __v_raw.total_amount, receive_progress = __v_raw.receive_progress, is_fully_received = __v_raw.is_fully_received, remark = __v_raw.remark, created_at = __v_raw.created_at, updated_at = __v_raw.updated_at, items = __v_raw.items, supplier_excel_import_status = __v_raw.supplier_excel_import_status) {
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
    override var supplier_excel_import_status: UTSJSONObject
        get() {
            return _tRG(__v_raw, "supplier_excel_import_status", __v_raw.supplier_excel_import_status, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("supplier_excel_import_status")) {
                return
            }
            val oldValue = __v_raw.supplier_excel_import_status
            __v_raw.supplier_excel_import_status = value
            _tRS(__v_raw, "supplier_excel_import_status", oldValue, value)
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PurchaseListResponse", "pkg/api/modules/purchases.uts", 35, 13)
    }
}
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PurchaseMutationData", "pkg/api/modules/purchases.uts", 42, 13)
    }
}
open class PurchaseOptionItem (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var text: String,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PurchaseOptionItem", "pkg/api/modules/purchases.uts", 49, 13)
    }
}
open class PurchaseSelectedFilter (
    @JsonNotNull
    open var param: String,
    @JsonNotNull
    open var value: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PurchaseSelectedFilter", "pkg/api/modules/purchases.uts", 53, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return PurchaseSelectedFilterReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class PurchaseSelectedFilterReactiveObject : PurchaseSelectedFilter, IUTSReactive<PurchaseSelectedFilter> {
    override var __v_raw: PurchaseSelectedFilter
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: PurchaseSelectedFilter, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(param = __v_raw.param, value = __v_raw.value) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): PurchaseSelectedFilterReactiveObject {
        return PurchaseSelectedFilterReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
open class PurchaseFilterOption (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var label: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PurchaseFilterOption", "pkg/api/modules/purchases.uts", 57, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return PurchaseFilterOptionReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class PurchaseFilterOptionReactiveObject : PurchaseFilterOption, IUTSReactive<PurchaseFilterOption> {
    override var __v_raw: PurchaseFilterOption
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: PurchaseFilterOption, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(value = __v_raw.value, label = __v_raw.label) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): PurchaseFilterOptionReactiveObject {
        return PurchaseFilterOptionReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
open class PurchaseFilterDefinition (
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
    open var options: UTSArray<PurchaseFilterOption>,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PurchaseFilterDefinition", "pkg/api/modules/purchases.uts", 61, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return PurchaseFilterDefinitionReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class PurchaseFilterDefinitionReactiveObject : PurchaseFilterDefinition, IUTSReactive<PurchaseFilterDefinition> {
    override var __v_raw: PurchaseFilterDefinition
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: PurchaseFilterDefinition, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(key = __v_raw.key, param = __v_raw.param, label = __v_raw.label, control = __v_raw.control, aliases = __v_raw.aliases, multiple = __v_raw.multiple, options = __v_raw.options) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): PurchaseFilterDefinitionReactiveObject {
        return PurchaseFilterDefinitionReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
    override var options: UTSArray<PurchaseFilterOption>
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
open class PurchaseFilterOptionsResponse (
    @JsonNotNull
    open var resource: String,
    @JsonNotNull
    open var count: Number,
    @JsonNotNull
    open var filters: UTSArray<PurchaseFilterDefinition>,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PurchaseFilterOptionsResponse", "pkg/api/modules/purchases.uts", 70, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return PurchaseFilterOptionsResponseReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class PurchaseFilterOptionsResponseReactiveObject : PurchaseFilterOptionsResponse, IUTSReactive<PurchaseFilterOptionsResponse> {
    override var __v_raw: PurchaseFilterOptionsResponse
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: PurchaseFilterOptionsResponse, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(resource = __v_raw.resource, count = __v_raw.count, filters = __v_raw.filters) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): PurchaseFilterOptionsResponseReactiveObject {
        return PurchaseFilterOptionsResponseReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
    override var filters: UTSArray<PurchaseFilterDefinition>
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
    open var product_preview_images: UTSArray<String>,
    @JsonNotNull
    open var product_media_ids: UTSArray<String>,
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PurchaseDetailItem", "pkg/api/modules/purchases.uts", 75, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return PurchaseDetailItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class PurchaseDetailItemReactiveObject : PurchaseDetailItem, IUTSReactive<PurchaseDetailItem> {
    override var __v_raw: PurchaseDetailItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: PurchaseDetailItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, purchase = __v_raw.purchase, purchase_number = __v_raw.purchase_number, product = __v_raw.product, product_name = __v_raw.product_name, product_sku = __v_raw.product_sku, product_barcode = __v_raw.product_barcode, product_image = __v_raw.product_image, product_images = __v_raw.product_images, product_preview_images = __v_raw.product_preview_images, product_media_ids = __v_raw.product_media_ids, quantity = __v_raw.quantity, unit_price = __v_raw.unit_price, amount = __v_raw.amount, received_quantity = __v_raw.received_quantity, remaining_quantity = __v_raw.remaining_quantity, receive_progress = __v_raw.receive_progress, is_fully_received = __v_raw.is_fully_received, notes = __v_raw.notes, created_at = __v_raw.created_at, updated_at = __v_raw.updated_at) {
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
    override var product_preview_images: UTSArray<String>
        get() {
            return _tRG(__v_raw, "product_preview_images", __v_raw.product_preview_images, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("product_preview_images")) {
                return
            }
            val oldValue = __v_raw.product_preview_images
            __v_raw.product_preview_images = value
            _tRS(__v_raw, "product_preview_images", oldValue, value)
        }
    override var product_media_ids: UTSArray<String>
        get() {
            return _tRG(__v_raw, "product_media_ids", __v_raw.product_media_ids, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("product_media_ids")) {
                return
            }
            val oldValue = __v_raw.product_media_ids
            __v_raw.product_media_ids = value
            _tRS(__v_raw, "product_media_ids", oldValue, value)
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PurchaseDetailListQuery", "pkg/api/modules/purchases.uts", 98, 13)
    }
}
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PurchaseDetailListResponse", "pkg/api/modules/purchases.uts", 106, 13)
    }
}
open class PurchaseDetailFilterOption (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var label: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PurchaseDetailFilterOption", "pkg/api/modules/purchases.uts", 113, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return PurchaseDetailFilterOptionReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class PurchaseDetailFilterOptionReactiveObject : PurchaseDetailFilterOption, IUTSReactive<PurchaseDetailFilterOption> {
    override var __v_raw: PurchaseDetailFilterOption
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: PurchaseDetailFilterOption, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(value = __v_raw.value, label = __v_raw.label) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): PurchaseDetailFilterOptionReactiveObject {
        return PurchaseDetailFilterOptionReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
open class PurchaseDetailFilterDefinition (
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
    open var options: UTSArray<PurchaseDetailFilterOption>,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PurchaseDetailFilterDefinition", "pkg/api/modules/purchases.uts", 117, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return PurchaseDetailFilterDefinitionReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class PurchaseDetailFilterDefinitionReactiveObject : PurchaseDetailFilterDefinition, IUTSReactive<PurchaseDetailFilterDefinition> {
    override var __v_raw: PurchaseDetailFilterDefinition
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: PurchaseDetailFilterDefinition, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(key = __v_raw.key, param = __v_raw.param, label = __v_raw.label, control = __v_raw.control, aliases = __v_raw.aliases, multiple = __v_raw.multiple, options = __v_raw.options) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): PurchaseDetailFilterDefinitionReactiveObject {
        return PurchaseDetailFilterDefinitionReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
    override var options: UTSArray<PurchaseDetailFilterOption>
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
open class PurchaseDetailFilterOptionsResponse (
    @JsonNotNull
    open var resource: String,
    @JsonNotNull
    open var count: Number,
    @JsonNotNull
    open var filters: UTSArray<PurchaseDetailFilterDefinition>,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PurchaseDetailFilterOptionsResponse", "pkg/api/modules/purchases.uts", 126, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return PurchaseDetailFilterOptionsResponseReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class PurchaseDetailFilterOptionsResponseReactiveObject : PurchaseDetailFilterOptionsResponse, IUTSReactive<PurchaseDetailFilterOptionsResponse> {
    override var __v_raw: PurchaseDetailFilterOptionsResponse
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: PurchaseDetailFilterOptionsResponse, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(resource = __v_raw.resource, count = __v_raw.count, filters = __v_raw.filters) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): PurchaseDetailFilterOptionsResponseReactiveObject {
        return PurchaseDetailFilterOptionsResponseReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
    override var filters: UTSArray<PurchaseDetailFilterDefinition>
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
open class PurchaseDetailMutationData (
    @JsonNotNull
    open var purchase: String,
    @JsonNotNull
    open var product: String,
    @JsonNotNull
    open var quantity: String,
    open var received_quantity: String? = null,
    open var notes: String? = null,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PurchaseDetailMutationData", "pkg/api/modules/purchases.uts", 131, 13)
    }
}
open class PurchaseImportResponse (
    @JsonNotNull
    open var resource: String,
    @JsonNotNull
    open var mode: String,
    @JsonNotNull
    open var summary: UTSJSONObject,
    @JsonNotNull
    open var diagnostics: UTSJSONObject,
    @JsonNotNull
    open var results: UTSArray<UTSJSONObject>,
    @JsonNotNull
    open var errors: UTSArray<UTSJSONObject>,
    @JsonNotNull
    open var warnings: UTSArray<UTSJSONObject>,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PurchaseImportResponse", "pkg/api/modules/purchases.uts", 142, 13)
    }
}
open class SupplierExcelImportTask (
    @JsonNotNull
    open var task_id: String,
    @JsonNotNull
    open var status: String,
    @JsonNotNull
    open var message: String,
    @JsonNotNull
    open var percent: Number,
    @JsonNotNull
    open var data: UTSJSONObject,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SupplierExcelImportTask", "pkg/api/modules/purchases.uts", 151, 13)
    }
}
open class QuickProcurementItem (
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var price: String,
    @JsonNotNull
    open var quantity: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("QuickProcurementItem", "pkg/api/modules/purchases.uts", 158, 13)
    }
}
open class QuickProcurementResultItem (
    @JsonNotNull
    open var product_id: Number,
    @JsonNotNull
    open var product_name: String,
    @JsonNotNull
    open var product_sku: String,
    @JsonNotNull
    open var product_barcode: String,
    @JsonNotNull
    open var purchase_price: String,
    @JsonNotNull
    open var quantity: Number,
    @JsonNotNull
    open var total: String,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("QuickProcurementResultItem", "pkg/api/modules/purchases.uts", 163, 13)
    }
}
open class QuickProcurementResponse (
    @JsonNotNull
    open var procure_id: Number,
    @JsonNotNull
    open var items: UTSArray<QuickProcurementResultItem>,
    @JsonNotNull
    open var total_quantity: Number,
    @JsonNotNull
    open var total_amount: String,
    @JsonNotNull
    open var message: String,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("QuickProcurementResponse", "pkg/api/modules/purchases.uts", 172, 13)
    }
}
open class PurchaseProductCheckResult (
    @JsonNotNull
    open var exists: Boolean = false,
    @JsonNotNull
    open var purchase_detail_id: Number,
    @JsonNotNull
    open var product_id: Number,
    @JsonNotNull
    open var product_name: String,
    @JsonNotNull
    open var product_sku: String,
    @JsonNotNull
    open var product_barcode: String,
    @JsonNotNull
    open var quantity: Number,
    @JsonNotNull
    open var received_quantity: Number,
    @JsonNotNull
    open var remaining_quantity: Number,
    @JsonNotNull
    open var unit_price: String,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PurchaseProductCheckResult", "pkg/api/modules/purchases.uts", 179, 13)
    }
}
open class PurchaseAutoPriceResponse (
    @JsonNotNull
    open var formula: UTSJSONObject,
    @JsonNotNull
    open var total_count: Number,
    @JsonNotNull
    open var updated_count: Number,
    @JsonNotNull
    open var skipped_existing_count: Number,
    @JsonNotNull
    open var skipped_error_count: Number,
    @JsonNotNull
    open var updated_products: UTSArray<UTSJSONObject>,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PurchaseAutoPriceResponse", "pkg/api/modules/purchases.uts", 191, 13)
    }
}
fun stringValue__11(value: Any?): String {
    if (value == null) {
        return ""
    }
    return "" + value
}
fun normalizeServerUrl__6(url: String): String {
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
fun intValue__10(value: Any?): Number {
    val parsed = parseInt(stringValue__11(value))
    if (isNaN(parsed)) {
        return 0
    }
    return parsed
}
fun boolValue__4(value: Any?): Boolean {
    val text = stringValue__11(value).toLowerCase()
    return text == "true" || text == "1"
}
fun parseObject__6(value: Any?): UTSJSONObject? {
    if (value == null) {
        return null
    }
    val text = JSON.stringify(value)
    if (text == null || text == "") {
        return null
    }
    try {
        return UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(text), " at pkg/api/modules/purchases.uts:234")
    }
     catch (error: Throwable) {
        return null
    }
}
fun rawDataObject__2(raw: Any): UTSJSONObject {
    val rawObject = parseObject__6(raw)
    if (rawObject == null) {
        throw UTSError("采购接口响应解析失败")
    }
    val dataValue = rawObject["data"]
    val dataObject = parseObject__6(dataValue)
    if (dataObject != null) {
        return dataObject!!
    }
    return rawObject!!
}
fun rawDetailObject(raw: Any): UTSJSONObject {
    val rawObject = rawDataObject__2(raw)
    val detailObject = parseObject__6(rawObject["detail"])
    if (detailObject != null) {
        return detailObject!!
    }
    val purchaseDetailObject = parseObject__6(rawObject["purchase_detail"])
    if (purchaseDetailObject != null) {
        return purchaseDetailObject!!
    }
    return rawObject
}
fun parseObjectArray__4(value: Any?): UTSArray<UTSJSONObject> {
    if (value == null) {
        return _uA<UTSJSONObject>()
    }
    val text = JSON.stringify(value)
    if (text == null || text == "") {
        return _uA<UTSJSONObject>()
    }
    var parsed: UTSArray<UTSJSONObject>? = null
    try {
        parsed = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(text), " at pkg/api/modules/purchases.uts:268")
    }
     catch (error: Throwable) {
        return _uA<UTSJSONObject>()
    }
    if (parsed == null) {
        return _uA<UTSJSONObject>()
    }
    return parsed!!
}
fun stringArrayValue__4(value: Any?): UTSArray<String> {
    if (value == null) {
        return _uA<String>()
    }
    val text = JSON.stringify(value)
    if (text == null || text == "") {
        return _uA<String>()
    }
    var parsed: UTSArray<Any>? = null
    try {
        parsed = UTSAndroid.consoleDebugError(JSON.parseArray<Any>(text), " at pkg/api/modules/purchases.uts:285")
    }
     catch (error: Throwable) {
        val singleValue = stringValue__11(value)
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
            result.push(stringValue__11(parsed[index]))
            index += 1
        }
    }
    return result
}
fun pushImageUrl(images: UTSArray<String>, url: String) {
    val normalizedUrl = normalizeServerUrl__6(url)
    if (normalizedUrl != "" && images.indexOf(normalizedUrl) < 0) {
        images.push(normalizedUrl)
    }
}
fun mediaImageUrl(rawObject: UTSJSONObject): String {
    var imageUrl = stringValue__11(rawObject["signed_thumbnail_url"])
    if (imageUrl == "") {
        imageUrl = stringValue__11(rawObject["thumbnail_url"])
    }
    if (imageUrl == "") {
        imageUrl = stringValue__11(rawObject["signed_url"])
    }
    if (imageUrl == "") {
        imageUrl = stringValue__11(rawObject["file_url"])
    }
    return imageUrl
}
fun mediaFullImageUrl(rawObject: UTSJSONObject): String {
    var imageUrl = stringValue__11(rawObject["signed_url"])
    if (imageUrl == "") {
        imageUrl = stringValue__11(rawObject["file_url"])
    }
    if (imageUrl == "") {
        imageUrl = mediaImageUrl(rawObject)
    }
    return imageUrl
}
fun appendMediaImages(images: UTSArray<String>, value: Any?) {
    val mediaFiles = parseObjectArray__4(value)
    run {
        var index: Number = 0
        while(index < mediaFiles.length){
            pushImageUrl(images, mediaImageUrl(mediaFiles[index]))
            index += 1
        }
    }
}
fun appendMediaPreviewImages(images: UTSArray<String>, value: Any?) {
    val mediaFiles = parseObjectArray__4(value)
    run {
        var index: Number = 0
        while(index < mediaFiles.length){
            pushImageUrl(images, mediaFullImageUrl(mediaFiles[index]))
            index += 1
        }
    }
}
fun appendMediaIds(ids: UTSArray<String>, value: Any?) {
    val mediaFiles = parseObjectArray__4(value)
    run {
        var index: Number = 0
        while(index < mediaFiles.length){
            val mediaId = stringValue__11(mediaFiles[index]["id"])
            if (mediaId != "" && ids.indexOf(mediaId) < 0) {
                ids.push(mediaId)
            }
            index += 1
        }
    }
}
fun appendStringImages(images: UTSArray<String>, value: Any?) {
    val rawImages = stringArrayValue__4(value)
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
    pushImageUrl(images, stringValue__11(rawObject["product_image"]))
    appendStringImages(images, rawObject["product_images"])
    appendMediaImages(images, rawObject["product_media_files"])
    if (productObject != null) {
        pushImageUrl(images, stringValue__11(productObject!!["image"]))
        appendStringImages(images, productObject!!["images"])
        appendMediaImages(images, productObject!!["media_files"])
    }
    return images
}
fun buildProductPreviewImages(rawObject: UTSJSONObject, productObject: UTSJSONObject?): UTSArray<String> {
    val images: UTSArray<String> = _uA()
    appendMediaPreviewImages(images, rawObject["product_media_files"])
    if (productObject != null) {
        appendMediaPreviewImages(images, productObject!!["media_files"])
        pushImageUrl(images, stringValue__11(productObject!!["image"]))
        appendStringImages(images, productObject!!["images"])
    }
    if (images.length == 0) {
        pushImageUrl(images, stringValue__11(rawObject["product_image"]))
        appendStringImages(images, rawObject["product_images"])
    }
    return images
}
fun buildProductMediaIds(rawObject: UTSJSONObject, productObject: UTSJSONObject?): UTSArray<String> {
    val ids: UTSArray<String> = _uA()
    appendMediaIds(ids, rawObject["product_media_files"])
    if (productObject != null) {
        appendMediaIds(ids, productObject!!["media_files"])
    }
    return ids
}
fun buildQuery(data: PurchaseListQuery): UTSJSONObject {
    val query: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("query", "pkg/api/modules/purchases.uts", 387, 11), "page" to data.page, "page_size" to data.page_size)
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
    val query: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("query", "pkg/api/modules/purchases.uts", 407, 11), "page" to data.page, "page_size" to data.page_size)
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
    var shopName = stringValue__11(rawObject["shop_name"])
    val shopInfo = parseObject__6(rawObject["shop_info"])
    if (shopName == "" && shopInfo != null) {
        shopName = stringValue__11(shopInfo!!["name"])
    }
    var supplierName = stringValue__11(rawObject["supplier_name"])
    val supplierInfo = parseObject__6(rawObject["supplier_info"])
    if (supplierName == "" && supplierInfo != null) {
        supplierName = stringValue__11(supplierInfo!!["name"])
    }
    val supplierExcelImportStatus = parseObject__6(rawObject["supplier_excel_import_status"])
    return PurchaseItem(id = intValue__10(rawObject["id"]), purchase_number = stringValue__11(rawObject["purchase_number"]), purchase_date = stringValue__11(rawObject["purchase_date"]), status = stringValue__11(rawObject["status"]), status_display = stringValue__11(rawObject["status_display"]), shop = intValue__10(rawObject["shop"]), shop_name = shopName, supplier = intValue__10(rawObject["supplier"]), supplier_name = supplierName, total_quantity = intValue__10(rawObject["total_quantity"]), received_quantity = intValue__10(rawObject["received_quantity"]), total_amount = stringValue__11(rawObject["total_amount"]), receive_progress = stringValue__11(rawObject["receive_progress"]), is_fully_received = boolValue__4(rawObject["is_fully_received"]), remark = stringValue__11(rawObject["remark"]), created_at = stringValue__11(rawObject["created_at"]), updated_at = stringValue__11(rawObject["updated_at"]), items = parseObjectArray__4(rawObject["items"]), supplier_excel_import_status = if (supplierExcelImportStatus == null) {
        (_uO())
    } else {
        supplierExcelImportStatus!!
    }
    )
}
fun buildPurchaseDetailItem(rawObject: UTSJSONObject): PurchaseDetailItem {
    var purchaseObject = parseObject__6(rawObject["purchase"])
    val purchaseInfo = parseObject__6(rawObject["purchase_info"])
    if (purchaseObject == null && purchaseInfo != null) {
        purchaseObject = purchaseInfo
    }
    var purchaseId = intValue__10(rawObject["purchase"])
    if (purchaseId <= 0 && purchaseObject != null) {
        purchaseId = intValue__10(purchaseObject!!["id"])
    }
    var purchaseNumber = stringValue__11(rawObject["purchase_number"])
    if (purchaseNumber == "" && purchaseObject != null) {
        purchaseNumber = stringValue__11(purchaseObject!!["purchase_number"])
    }
    var productObject = parseObject__6(rawObject["product"])
    val productInfo = parseObject__6(rawObject["product_info"])
    val productDetail = parseObject__6(rawObject["product_detail"])
    if (productObject == null && productInfo != null) {
        productObject = productInfo
    }
    if (productObject == null && productDetail != null) {
        productObject = productDetail
    }
    var productId = intValue__10(rawObject["product"])
    if (productId <= 0 && productObject != null) {
        productId = intValue__10(productObject!!["id"])
    }
    var productName = stringValue__11(rawObject["product_name"])
    if (productName == "" && productObject != null) {
        productName = stringValue__11(productObject!!["name_cn"])
    }
    if (productName == "" && productObject != null) {
        productName = stringValue__11(productObject!!["name"])
    }
    if (productName == "" && productObject != null) {
        productName = stringValue__11(productObject!!["title"])
    }
    var productSku = stringValue__11(rawObject["product_sku"])
    if (productSku == "" && productObject != null) {
        productSku = stringValue__11(productObject!!["sku"])
    }
    var productBarcode = stringValue__11(rawObject["product_barcode"])
    if (productBarcode == "" && productObject != null) {
        productBarcode = stringValue__11(productObject!!["barcode"])
    }
    val productImages = buildProductImages(rawObject, productObject)
    val productPreviewImages = buildProductPreviewImages(rawObject, productObject)
    val productMediaIds = buildProductMediaIds(rawObject, productObject)
    return PurchaseDetailItem(id = intValue__10(rawObject["id"]), purchase = purchaseId, purchase_number = purchaseNumber, product = productId, product_name = productName, product_sku = productSku, product_barcode = productBarcode, product_image = if (productImages.length > 0) {
        productImages[0]
    } else {
        ""
    }
    , product_images = productImages, product_preview_images = productPreviewImages, product_media_ids = productMediaIds, quantity = intValue__10(rawObject["quantity"]), unit_price = stringValue__11(rawObject["unit_price"]), amount = stringValue__11(rawObject["amount"]), received_quantity = intValue__10(rawObject["received_quantity"]), remaining_quantity = intValue__10(rawObject["remaining_quantity"]), receive_progress = stringValue__11(rawObject["receive_progress"]), is_fully_received = boolValue__4(rawObject["is_fully_received"]), notes = stringValue__11(rawObject["notes"]), created_at = stringValue__11(rawObject["created_at"]), updated_at = stringValue__11(rawObject["updated_at"]))
}
fun buildItems(value: Any?): UTSArray<PurchaseItem> {
    val rawArray = parseObjectArray__4(value)
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
    val rawArray = parseObjectArray__4(value)
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
        paginationObject = parseObject__6(rawPagination)
    }
    val results = buildItems(rawObject["results"])
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
    return PurchaseListResponse(results = results, total_count = totalCount, total_pages = totalPages, current_page = currentPage, page_size = pageSize)
}
fun buildDetailListResponse(raw: Any, query: PurchaseDetailListQuery): PurchaseDetailListResponse {
    val rawObject = rawDataObject__2(raw)
    var paginationObject: UTSJSONObject? = null
    val rawPagination = rawObject["pagination"]
    if (rawPagination != null) {
        paginationObject = parseObject__6(rawPagination)
    }
    val results = buildDetailItems(rawObject["results"])
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
    return PurchaseDetailListResponse(results = results, total_count = totalCount, total_pages = totalPages, current_page = currentPage, page_size = pageSize)
}
fun buildPurchaseFilterOptionsResponse(raw: Any): PurchaseFilterOptionsResponse {
    val rawObject = rawDataObject__2(raw)
    var filters: UTSArray<PurchaseFilterDefinition> = _uA()
    val rawFilters = rawObject["filters"]
    if (rawFilters != null) {
        val filterObjects = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(JSON.stringify(rawFilters)), " at pkg/api/modules/purchases.uts:604")
        if (filterObjects != null) {
            val nextFilters: UTSArray<PurchaseFilterDefinition> = _uA()
            run {
                var filterIndex: Number = 0
                while(filterIndex < filterObjects.length){
                    val filterObject = filterObjects[filterIndex]
                    var options: UTSArray<PurchaseFilterOption> = _uA()
                    val rawOptions = filterObject["options"]
                    if (rawOptions != null) {
                        val optionObjects = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(JSON.stringify(rawOptions)), " at pkg/api/modules/purchases.uts:612")
                        if (optionObjects != null) {
                            val nextOptions: UTSArray<PurchaseFilterOption> = _uA()
                            run {
                                var optionIndex: Number = 0
                                while(optionIndex < optionObjects.length){
                                    val optionObject = optionObjects[optionIndex]
                                    nextOptions.push(PurchaseFilterOption(value = stringValue__11(optionObject["value"]), label = stringValue__11(optionObject["label"])))
                                    optionIndex += 1
                                }
                            }
                            options = nextOptions
                        }
                    }
                    nextFilters.push(PurchaseFilterDefinition(key = stringValue__11(filterObject["key"]), param = stringValue__11(filterObject["param"]), label = stringValue__11(filterObject["label"]), control = stringValue__11(filterObject["control"]), aliases = stringArrayValue__4(filterObject["aliases"]), multiple = stringValue__11(filterObject["multiple"]) == "true", options = options))
                    filterIndex += 1
                }
            }
            filters = nextFilters
        }
    }
    return PurchaseFilterOptionsResponse(resource = stringValue__11(rawObject["resource"]), count = intValue__10(rawObject["count"]), filters = filters)
}
fun buildPurchaseDetailFilterOptionsResponse(raw: Any): PurchaseDetailFilterOptionsResponse {
    val rawObject = rawDataObject__2(raw)
    var filters: UTSArray<PurchaseDetailFilterDefinition> = _uA()
    val rawFilters = rawObject["filters"]
    if (rawFilters != null) {
        val filterObjects = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(JSON.stringify(rawFilters)), " at pkg/api/modules/purchases.uts:649")
        if (filterObjects != null) {
            val nextFilters: UTSArray<PurchaseDetailFilterDefinition> = _uA()
            run {
                var filterIndex: Number = 0
                while(filterIndex < filterObjects.length){
                    val filterObject = filterObjects[filterIndex]
                    var options: UTSArray<PurchaseDetailFilterOption> = _uA()
                    val rawOptions = filterObject["options"]
                    if (rawOptions != null) {
                        val optionObjects = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(JSON.stringify(rawOptions)), " at pkg/api/modules/purchases.uts:657")
                        if (optionObjects != null) {
                            val nextOptions: UTSArray<PurchaseDetailFilterOption> = _uA()
                            run {
                                var optionIndex: Number = 0
                                while(optionIndex < optionObjects.length){
                                    val optionObject = optionObjects[optionIndex]
                                    nextOptions.push(PurchaseDetailFilterOption(value = stringValue__11(optionObject["value"]), label = stringValue__11(optionObject["label"])))
                                    optionIndex += 1
                                }
                            }
                            options = nextOptions
                        }
                    }
                    nextFilters.push(PurchaseDetailFilterDefinition(key = stringValue__11(filterObject["key"]), param = stringValue__11(filterObject["param"]), label = stringValue__11(filterObject["label"]), control = stringValue__11(filterObject["control"]), aliases = stringArrayValue__4(filterObject["aliases"]), multiple = stringValue__11(filterObject["multiple"]) == "true", options = options))
                    filterIndex += 1
                }
            }
            filters = nextFilters
        }
    }
    return PurchaseDetailFilterOptionsResponse(resource = stringValue__11(rawObject["resource"]), count = intValue__10(rawObject["count"]), filters = filters)
}
fun buildPurchaseImportResponse(rawObject: UTSJSONObject): PurchaseImportResponse {
    val summaryObject = parseObject__6(rawObject["summary"])
    val diagnosticsObject = parseObject__6(rawObject["diagnostics"])
    return PurchaseImportResponse(resource = stringValue__11(rawObject["resource"]), mode = stringValue__11(rawObject["mode"]), summary = if (summaryObject == null) {
        (_uO())
    } else {
        summaryObject!!
    }
    , diagnostics = if (diagnosticsObject == null) {
        (_uO())
    } else {
        diagnosticsObject!!
    }
    , results = parseObjectArray__4(rawObject["results"]), errors = parseObjectArray__4(rawObject["errors"]), warnings = parseObjectArray__4(rawObject["warnings"]))
}
fun buildQuickProcurementResponse(rawObject: UTSJSONObject): QuickProcurementResponse {
    val rows = parseObjectArray__4(rawObject["items"])
    val items: UTSArray<QuickProcurementResultItem> = _uA()
    run {
        var index: Number = 0
        while(index < rows.length){
            val row = rows[index]
            items.push(QuickProcurementResultItem(product_id = intValue__10(row["product_id"]), product_name = stringValue__11(row["product_name"]), product_sku = stringValue__11(row["product_sku"]), product_barcode = stringValue__11(row["product_barcode"]), purchase_price = stringValue__11(row["purchase_price"]), quantity = intValue__10(row["quantity"]), total = stringValue__11(row["total"])))
            index += 1
        }
    }
    return QuickProcurementResponse(procure_id = intValue__10(rawObject["procure_id"]), items = items, total_quantity = intValue__10(rawObject["total_quantity"]), total_amount = stringValue__11(rawObject["total_amount"]), message = stringValue__11(rawObject["message"]))
}
fun parseImportUploadError(text: String, fallback: String): String {
    if (text == "") {
        return fallback
    }
    try {
        val rootObject = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(text), " at pkg/api/modules/purchases.uts:744")
        if (rootObject == null) {
            return fallback
        }
        val message = stringValue__11(rootObject!!["message"])
        if (message != "") {
            return message
        }
    }
     catch (error: Throwable) {
        return fallback
    }
    return fallback
}
fun parseUploadDataObject(text: String): UTSJSONObject {
    val rootObject = if (text == "") {
        null
    } else {
        UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(text), " at pkg/api/modules/purchases.uts:757")
    }
    if (rootObject == null) {
        throw UTSError("接口响应解析失败")
    }
    if (stringValue__11(rootObject!!["success"]) == "false") {
        var message = stringValue__11(rootObject!!["message"])
        if (message == "") {
            message = "请求失败"
        }
        throw UTSError(message)
    }
    val dataObject = parseObject__6(rootObject!!["data"])
    if (dataObject != null) {
        return dataObject!!
    }
    return rootObject!!
}
fun mutationBody(data: PurchaseMutationData): UTSJSONObject {
    val body: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("body", "pkg/api/modules/purchases.uts", 772, 11), "purchase_date" to data.purchase_date, "shop" to parseInt(data.shop), "supplier" to parseInt(data.supplier), "remark" to if (data.remark == null) {
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
    val body: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("body", "pkg/api/modules/purchases.uts", 790, 11), "purchase" to parseInt(data.purchase), "product" to parseInt(data.product), "quantity" to parseInt(data.quantity), "notes" to if (data.notes == null) {
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
    return "/api/purchases/purchases/" + stringValue__11(id) + "/"
}
fun purchaseDetailPath(id: Any): String {
    return "/api/purchases/purchase-details/" + stringValue__11(id) + "/"
}
fun buildPurchaseProductCheckResult(rawObject: UTSJSONObject): PurchaseProductCheckResult {
    return PurchaseProductCheckResult(exists = boolValue__4(rawObject["exists"]), purchase_detail_id = intValue__10(rawObject["purchase_detail_id"]), product_id = intValue__10(rawObject["product_id"]), product_name = stringValue__11(rawObject["product_name"]), product_sku = stringValue__11(rawObject["product_sku"]), product_barcode = stringValue__11(rawObject["product_barcode"]), quantity = intValue__10(rawObject["quantity"]), received_quantity = intValue__10(rawObject["received_quantity"]), remaining_quantity = intValue__10(rawObject["remaining_quantity"]), unit_price = stringValue__11(rawObject["unit_price"]))
}
fun getPurchaseList(data: PurchaseListQuery): UTSPromise<PurchaseListResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/purchases/purchases/", "GET", buildQuery(data), true))
            return@w buildListResponse__1(raw, data)
    })
}
fun getPurchaseFilterOptions(): UTSPromise<PurchaseFilterOptionsResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/purchases/purchases/filter-options/", "GET", _uO(), true))
            return@w buildPurchaseFilterOptionsResponse(raw)
    })
}
fun getPurchaseDetail(id: Any): UTSPromise<PurchaseItem> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(detailPath__1(id), "GET", _uO(), true))
            return@w buildPurchaseItem(rawDataObject__2(raw))
    })
}
fun checkPurchaseProduct(id: Any, barcode: String, sku: String = ""): UTSPromise<PurchaseProductCheckResult> {
    return wrapUTSPromise(suspend w@{
            val body: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("body", "pkg/api/modules/purchases.uts", 836, 11), "barcode" to barcode, "sku" to sku)
            val raw = await(request(detailPath__1(id) + "check-product/", "POST", body, true))
            return@w buildPurchaseProductCheckResult(rawDataObject__2(raw))
    })
}
fun uploadSupplierExcelFile(id: Any, filePath: String, actionPath: String, config: UTSJSONObject): UTSPromise<UTSJSONObject> {
    return UTSPromise(fun(resolve, reject){
        val url = baseUrl + detailPath__1(id) + actionPath + "/"
        val uploadTimeout = if (timeOut < 120000) {
            120000
        } else {
            timeOut
        }
        val formData: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("formData", "pkg/api/modules/purchases.uts", 910, 15), "config" to JSON.stringify(config))
        console.log("供应商Excel上传:", url, filePath, " at pkg/api/modules/purchases.uts:892")
        uni_uploadFile(UploadFileOptions(url = url, filePath = filePath, name = "file", header = buildDownloadHeader(), formData = formData, timeout = uploadTimeout, success = fun(res){
            if (res.statusCode < 200 || res.statusCode >= 300) {
                reject(UTSError(parseImportUploadError(res.data, "HTTP状态码错误: " + res.statusCode)))
                return
            }
            try {
                resolve(parseUploadDataObject(res.data))
            }
             catch (error: Throwable) {
                reject(error)
            }
        }
        , fail = fun(err){
            var message = stringValue__11(err.errMsg)
            if (message == "") {
                message = "上传失败"
            }
            reject(UTSError(message))
        }
        ))
    }
    )
}
fun buildSupplierExcelImportResponse(raw: UTSJSONObject): PurchaseImportResponse {
    return buildPurchaseImportResponse(raw)
}
fun precheckSupplierExcelImport(id: Any, filePath: String, config: UTSJSONObject): UTSPromise<PurchaseImportResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(uploadSupplierExcelFile(id, filePath, "supplier-excel-precheck", config))
            return@w buildSupplierExcelImportResponse(raw)
    })
}
fun startSupplierExcelImportTask(id: Any, filePath: String, config: UTSJSONObject): UTSPromise<SupplierExcelImportTask> {
    return wrapUTSPromise(suspend w@{
            val raw = await(uploadSupplierExcelFile(id, filePath, "supplier-excel-import-async", config))
            val dataObject = parseObject__6(raw["data"])
            return@w SupplierExcelImportTask(task_id = stringValue__11(raw["task_id"]), status = stringValue__11(raw["status"]), message = stringValue__11(raw["message"]), percent = intValue__10(raw["percent"]), data = if (dataObject == null) {
                (_uO())
            } else {
                dataObject!!
            }
            )
    })
}
fun getSupplierExcelImportTaskStatus(id: Any, taskId: String, includeData: Boolean): UTSPromise<SupplierExcelImportTask> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(detailPath__1(id) + "supplier-excel-import-task/" + taskId + "/", "GET", _uO("include_data" to if (includeData) {
                "1"
            } else {
                "0"
            }
            ), true))
            val rawObject = rawDataObject__2(raw)
            val dataObject = parseObject__6(rawObject["data"])
            return@w SupplierExcelImportTask(task_id = stringValue__11(rawObject["task_id"]), status = stringValue__11(rawObject["status"]), message = stringValue__11(rawObject["message"]), percent = intValue__10(rawObject["percent"]), data = if (dataObject == null) {
                (_uO())
            } else {
                dataObject!!
            }
            )
    })
}
fun getPurchaseDetailList(data: PurchaseDetailListQuery): UTSPromise<PurchaseDetailListResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/purchases/purchase-details/", "GET", buildDetailQuery(data), true))
            return@w buildDetailListResponse(raw, data)
    })
}
fun getPurchaseDetailFilterOptions(purchase: String?): UTSPromise<PurchaseDetailFilterOptionsResponse> {
    return wrapUTSPromise(suspend w@{
            val query: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("query", "pkg/api/modules/purchases.uts", 1016, 11))
            if (purchase != null && purchase != "") {
                query["purchase"] = purchase
            }
            val raw = await(request("/api/purchases/purchase-details/filter-options/", "GET", query, true))
            return@w buildPurchaseDetailFilterOptionsResponse(raw)
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
fun quickProcurement(purchaseId: Any, items: UTSArray<QuickProcurementItem>): UTSPromise<QuickProcurementResponse> {
    return wrapUTSPromise(suspend w@{
            val requestItems: UTSArray<UTSJSONObject> = _uA()
            run {
                var index: Number = 0
                while(index < items.length){
                    val item = items[index]
                    requestItems.push(_uO("name" to item.name, "price" to item.price, "quantity" to item.quantity))
                    index += 1
                }
            }
            val raw = await(request("/api/purchases/purchases/quick_procurement/", "POST", _uO("procure_id" to parseInt(stringValue__11(purchaseId)), "items" to requestItems), true))
            return@w buildQuickProcurementResponse(rawDataObject__2(raw))
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
    val body: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("body", "pkg/api/modules/purchases.uts", 1065, 11), "action" to actionName)
    if (actionName == "cancel") {
        body["reason"] = "前端取消采购单"
    }
    return request(detailPath__1(id) + "action_purchase/", "POST", body, true)
}
fun autoGeneratePurchasePrices(id: Any, formulaId: Any): UTSPromise<PurchaseAutoPriceResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request(detailPath__1(id) + "auto_generate_prices/", "POST", _uO("formula_id" to formulaId), true))
            val data = rawDataObject__2(raw)
            val formula = parseObject__6(data["formula"])
            return@w PurchaseAutoPriceResponse(formula = if (formula == null) {
                (_uO())
            } else {
                formula!!
            }
            , total_count = intValue__10(data["total_count"]), updated_count = intValue__10(data["updated_count"]), skipped_existing_count = intValue__10(data["skipped_existing_count"]), skipped_error_count = intValue__10(data["skipped_error_count"]), updated_products = parseObjectArray__4(data["updated_products"]))
    })
}
fun receivePurchaseDetail(id: Any, quantity: Number, notes: String = ""): UTSPromise<Any> {
    return request(purchaseDetailPath(id) + "receive/", "POST", _uO("quantity" to quantity, "notes" to notes), true)
}
fun getPurchaseOptionList(path: String, search: String?, labelField: String, extraLabelField: String = ""): UTSPromise<UTSArray<PurchaseOptionItem>> {
    return wrapUTSPromise(suspend w@{
            val query: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("query", "pkg/api/modules/purchases.uts", 1087, 11), "page" to 1, "page_size" to 30)
            if (search != null && search != "") {
                query["search"] = search
            }
            val raw = await(request(path, "GET", query, true))
            val rawObject = rawDataObject__2(raw)
            val rows = parseObjectArray__4(rawObject["results"])
            val result: UTSArray<PurchaseOptionItem> = _uA()
            run {
                var index: Number = 0
                while(index < rows.length){
                    val row = rows[index]
                    var text = stringValue__11(row[labelField])
                    val nameEn = stringValue__11(row["name_en"])
                    val nameOther = stringValue__11(row["name_other"])
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
                    val extra = stringValue__11(row[extraLabelField])
                    if (extra != "") {
                        text = text + " / " + extra
                    }
                    result.push(PurchaseOptionItem(value = stringValue__11(row["id"]), text = text))
                    index += 1
                }
            }
            return@w result
    })
}
open class PurchaseSelectOption (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var text: String,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PurchaseSelectOption", "pages/purchases/index.uvue", 200, 6)
    }
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
open class PurchaseDetailSelectedFilter (
    @JsonNotNull
    open var param: String,
    @JsonNotNull
    open var value: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PurchaseDetailSelectedFilter", "pages/purchases/details/index.uvue", 123, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return PurchaseDetailSelectedFilterReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class PurchaseDetailSelectedFilterReactiveObject : PurchaseDetailSelectedFilter, IUTSReactive<PurchaseDetailSelectedFilter> {
    override var __v_raw: PurchaseDetailSelectedFilter
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: PurchaseDetailSelectedFilter, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(param = __v_raw.param, value = __v_raw.value) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): PurchaseDetailSelectedFilterReactiveObject {
        return PurchaseDetailSelectedFilterReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
open class ProductOption (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var text: String,
    @JsonNotNull
    open var image: String,
    @JsonNotNull
    open var subtitle: String,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ProductOption", "pages/purchases/details/from.uvue", 114, 6)
    }
}
open class PrintPreviewField__1 (
    @JsonNotNull
    open var key: String,
    @JsonNotNull
    open var label: String,
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var emphasis: Boolean = false,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PrintPreviewField", "pages/purchases/details/from.uvue", 121, 6)
    }
}
open class ProductCategoryQuickInfo (
    @JsonNotNull
    open var id: String,
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var kasaCode: String,
    @JsonNotNull
    open var kasaText: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ProductCategoryQuickInfo", "pages/purchases/details/from.uvue", 128, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ProductCategoryQuickInfoReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ProductCategoryQuickInfoReactiveObject : ProductCategoryQuickInfo, IUTSReactive<ProductCategoryQuickInfo> {
    override var __v_raw: ProductCategoryQuickInfo
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ProductCategoryQuickInfo, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, name = __v_raw.name, kasaCode = __v_raw.kasaCode, kasaText = __v_raw.kasaText) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ProductCategoryQuickInfoReactiveObject {
        return ProductCategoryQuickInfoReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
    override var kasaCode: String
        get() {
            return _tRG(__v_raw, "kasaCode", __v_raw.kasaCode, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("kasaCode")) {
                return
            }
            val oldValue = __v_raw.kasaCode
            __v_raw.kasaCode = value
            _tRS(__v_raw, "kasaCode", oldValue, value)
        }
    override var kasaText: String
        get() {
            return _tRG(__v_raw, "kasaText", __v_raw.kasaText, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("kasaText")) {
                return
            }
            val oldValue = __v_raw.kasaText
            __v_raw.kasaText = value
            _tRS(__v_raw, "kasaText", oldValue, value)
        }
}
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
open class QuickDraftRow (
    @JsonNotNull
    open var key: String,
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var price: String,
    @JsonNotNull
    open var quantity: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("QuickDraftRow", "pages/purchases/details/quick-input.uvue", 157, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return QuickDraftRowReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class QuickDraftRowReactiveObject : QuickDraftRow, IUTSReactive<QuickDraftRow> {
    override var __v_raw: QuickDraftRow
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: QuickDraftRow, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(key = __v_raw.key, name = __v_raw.name, price = __v_raw.price, quantity = __v_raw.quantity) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): QuickDraftRowReactiveObject {
        return QuickDraftRowReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
    override var price: String
        get() {
            return _tRG(__v_raw, "price", __v_raw.price, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("price")) {
                return
            }
            val oldValue = __v_raw.price
            __v_raw.price = value
            _tRS(__v_raw, "price", oldValue, value)
        }
    override var quantity: String
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
}
open class AIParsedRow (
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var price: String,
    @JsonNotNull
    open var quantity: Number,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("AIParsedRow", "pages/purchases/details/quick-input.uvue", 164, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return AIParsedRowReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class AIParsedRowReactiveObject : AIParsedRow, IUTSReactive<AIParsedRow> {
    override var __v_raw: AIParsedRow
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: AIParsedRow, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(name = __v_raw.name, price = __v_raw.price, quantity = __v_raw.quantity) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): AIParsedRowReactiveObject {
        return AIParsedRowReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
    override var price: String
        get() {
            return _tRG(__v_raw, "price", __v_raw.price, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("price")) {
                return
            }
            val oldValue = __v_raw.price
            __v_raw.price = value
            _tRS(__v_raw, "price", oldValue, value)
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
}
val GenPagesPurchasesDetailsQuickInputClass = CreateVueComponent(GenPagesPurchasesDetailsQuickInput::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesPurchasesDetailsQuickInput.inheritAttrs, inject = GenPagesPurchasesDetailsQuickInput.inject, props = GenPagesPurchasesDetailsQuickInput.props, propsNeedCastKeys = GenPagesPurchasesDetailsQuickInput.propsNeedCastKeys, emits = GenPagesPurchasesDetailsQuickInput.emits, components = GenPagesPurchasesDetailsQuickInput.components, styles = GenPagesPurchasesDetailsQuickInput.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesPurchasesDetailsQuickInput.setup(props as GenPagesPurchasesDetailsQuickInput)
    }
    )
}
, fun(instance, renderer): GenPagesPurchasesDetailsQuickInput {
    return GenPagesPurchasesDetailsQuickInput(instance, renderer)
}
)
open class PreviewCell (
    @JsonNotNull
    open var key: String,
    @JsonNotNull
    open var column: String,
    @JsonNotNull
    open var value: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PreviewCell", "pages/purchases/details/excel-upload.uvue", 191, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return PreviewCellReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class PreviewCellReactiveObject : PreviewCell, IUTSReactive<PreviewCell> {
    override var __v_raw: PreviewCell
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: PreviewCell, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(key = __v_raw.key, column = __v_raw.column, value = __v_raw.value) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): PreviewCellReactiveObject {
        return PreviewCellReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
    override var column: String
        get() {
            return _tRG(__v_raw, "column", __v_raw.column, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("column")) {
                return
            }
            val oldValue = __v_raw.column
            __v_raw.column = value
            _tRS(__v_raw, "column", oldValue, value)
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
open class PreviewRow (
    @JsonNotNull
    open var key: String,
    @JsonNotNull
    open var rowNumber: String,
    @JsonNotNull
    open var cells: UTSArray<PreviewCell>,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PreviewRow", "pages/purchases/details/excel-upload.uvue", 197, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return PreviewRowReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class PreviewRowReactiveObject : PreviewRow, IUTSReactive<PreviewRow> {
    override var __v_raw: PreviewRow
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: PreviewRow, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(key = __v_raw.key, rowNumber = __v_raw.rowNumber, cells = __v_raw.cells) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): PreviewRowReactiveObject {
        return PreviewRowReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
    override var rowNumber: String
        get() {
            return _tRG(__v_raw, "rowNumber", __v_raw.rowNumber, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("rowNumber")) {
                return
            }
            val oldValue = __v_raw.rowNumber
            __v_raw.rowNumber = value
            _tRS(__v_raw, "rowNumber", oldValue, value)
        }
    override var cells: UTSArray<PreviewCell>
        get() {
            return _tRG(__v_raw, "cells", __v_raw.cells, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("cells")) {
                return
            }
            val oldValue = __v_raw.cells
            __v_raw.cells = value
            _tRS(__v_raw, "cells", oldValue, value)
        }
}
open class SummaryItem (
    @JsonNotNull
    open var key: String,
    @JsonNotNull
    open var label: String,
    @JsonNotNull
    open var value: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SummaryItem", "pages/purchases/details/excel-upload.uvue", 203, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return SummaryItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class SummaryItemReactiveObject : SummaryItem, IUTSReactive<SummaryItem> {
    override var __v_raw: SummaryItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: SummaryItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(key = __v_raw.key, label = __v_raw.label, value = __v_raw.value) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): SummaryItemReactiveObject {
        return SummaryItemReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
open class ColumnOption (
    @JsonNotNull
    open var column: String,
    @JsonNotNull
    open var label: String,
    @JsonNotNull
    open var sample: String,
    @JsonNotNull
    open var previewTitle: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ColumnOption", "pages/purchases/details/excel-upload.uvue", 209, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return ColumnOptionReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class ColumnOptionReactiveObject : ColumnOption, IUTSReactive<ColumnOption> {
    override var __v_raw: ColumnOption
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: ColumnOption, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(column = __v_raw.column, label = __v_raw.label, sample = __v_raw.sample, previewTitle = __v_raw.previewTitle) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): ColumnOptionReactiveObject {
        return ColumnOptionReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var column: String
        get() {
            return _tRG(__v_raw, "column", __v_raw.column, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("column")) {
                return
            }
            val oldValue = __v_raw.column
            __v_raw.column = value
            _tRS(__v_raw, "column", oldValue, value)
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
    override var sample: String
        get() {
            return _tRG(__v_raw, "sample", __v_raw.sample, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("sample")) {
                return
            }
            val oldValue = __v_raw.sample
            __v_raw.sample = value
            _tRS(__v_raw, "sample", oldValue, value)
        }
    override var previewTitle: String
        get() {
            return _tRG(__v_raw, "previewTitle", __v_raw.previewTitle, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("previewTitle")) {
                return
            }
            val oldValue = __v_raw.previewTitle
            __v_raw.previewTitle = value
            _tRS(__v_raw, "previewTitle", oldValue, value)
        }
}
open class FieldDefinition (
    @JsonNotNull
    open var key: String,
    @JsonNotNull
    open var label: String,
    @JsonNotNull
    open var required: Boolean = false,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("FieldDefinition", "pages/purchases/details/excel-upload.uvue", 216, 6)
    }
}
open class FieldMappingRow (
    @JsonNotNull
    open var key: String,
    @JsonNotNull
    open var label: String,
    @JsonNotNull
    open var required: Boolean = false,
    @JsonNotNull
    open var column: String,
    @JsonNotNull
    open var columnText: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("FieldMappingRow", "pages/purchases/details/excel-upload.uvue", 222, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return FieldMappingRowReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class FieldMappingRowReactiveObject : FieldMappingRow, IUTSReactive<FieldMappingRow> {
    override var __v_raw: FieldMappingRow
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: FieldMappingRow, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(key = __v_raw.key, label = __v_raw.label, required = __v_raw.required, column = __v_raw.column, columnText = __v_raw.columnText) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): FieldMappingRowReactiveObject {
        return FieldMappingRowReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
    override var required: Boolean
        get() {
            return _tRG(__v_raw, "required", __v_raw.required, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("required")) {
                return
            }
            val oldValue = __v_raw.required
            __v_raw.required = value
            _tRS(__v_raw, "required", oldValue, value)
        }
    override var column: String
        get() {
            return _tRG(__v_raw, "column", __v_raw.column, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("column")) {
                return
            }
            val oldValue = __v_raw.column
            __v_raw.column = value
            _tRS(__v_raw, "column", oldValue, value)
        }
    override var columnText: String
        get() {
            return _tRG(__v_raw, "columnText", __v_raw.columnText, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("columnText")) {
                return
            }
            val oldValue = __v_raw.columnText
            __v_raw.columnText = value
            _tRS(__v_raw, "columnText", oldValue, value)
        }
}
open class AIParsedMapping (
    @JsonNotNull
    open var key: String,
    @JsonNotNull
    open var label: String,
    @JsonNotNull
    open var column: String,
    @JsonNotNull
    open var columnText: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("AIParsedMapping", "pages/purchases/details/excel-upload.uvue", 230, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return AIParsedMappingReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class AIParsedMappingReactiveObject : AIParsedMapping, IUTSReactive<AIParsedMapping> {
    override var __v_raw: AIParsedMapping
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: AIParsedMapping, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(key = __v_raw.key, label = __v_raw.label, column = __v_raw.column, columnText = __v_raw.columnText) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): AIParsedMappingReactiveObject {
        return AIParsedMappingReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
    override var column: String
        get() {
            return _tRG(__v_raw, "column", __v_raw.column, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("column")) {
                return
            }
            val oldValue = __v_raw.column
            __v_raw.column = value
            _tRS(__v_raw, "column", oldValue, value)
        }
    override var columnText: String
        get() {
            return _tRG(__v_raw, "columnText", __v_raw.columnText, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("columnText")) {
                return
            }
            val oldValue = __v_raw.columnText
            __v_raw.columnText = value
            _tRS(__v_raw, "columnText", oldValue, value)
        }
}
val GenPagesPurchasesDetailsExcelUploadClass = CreateVueComponent(GenPagesPurchasesDetailsExcelUpload::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesPurchasesDetailsExcelUpload.inheritAttrs, inject = GenPagesPurchasesDetailsExcelUpload.inject, props = GenPagesPurchasesDetailsExcelUpload.props, propsNeedCastKeys = GenPagesPurchasesDetailsExcelUpload.propsNeedCastKeys, emits = GenPagesPurchasesDetailsExcelUpload.emits, components = GenPagesPurchasesDetailsExcelUpload.components, styles = GenPagesPurchasesDetailsExcelUpload.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesPurchasesDetailsExcelUpload.setup(props as GenPagesPurchasesDetailsExcelUpload)
    }
    )
}
, fun(instance, renderer): GenPagesPurchasesDetailsExcelUpload {
    return GenPagesPurchasesDetailsExcelUpload(instance, renderer)
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
    open var cashier_id: String? = null,
    open var inventory_deducted: String? = null,
    open var date_from: String? = null,
    open var date_to: String? = null,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("OrderListQuery", "pkg/api/modules/orders.uts", 2, 13)
    }
}
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("OrderItem", "pkg/api/modules/orders.uts", 13, 13)
    }
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("OrderListResponse", "pkg/api/modules/orders.uts", 39, 13)
    }
}
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("OrderStatistics", "pkg/api/modules/orders.uts", 46, 13)
    }
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
open class OrderSelectedFilter (
    @JsonNotNull
    open var param: String,
    @JsonNotNull
    open var value: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("OrderSelectedFilter", "pkg/api/modules/orders.uts", 54, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return OrderSelectedFilterReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class OrderSelectedFilterReactiveObject : OrderSelectedFilter, IUTSReactive<OrderSelectedFilter> {
    override var __v_raw: OrderSelectedFilter
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: OrderSelectedFilter, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(param = __v_raw.param, value = __v_raw.value) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): OrderSelectedFilterReactiveObject {
        return OrderSelectedFilterReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
open class OrderFilterOption (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var label: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("OrderFilterOption", "pkg/api/modules/orders.uts", 58, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return OrderFilterOptionReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class OrderFilterOptionReactiveObject : OrderFilterOption, IUTSReactive<OrderFilterOption> {
    override var __v_raw: OrderFilterOption
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: OrderFilterOption, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(value = __v_raw.value, label = __v_raw.label) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): OrderFilterOptionReactiveObject {
        return OrderFilterOptionReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
open class OrderFilterDefinition (
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
    open var options: UTSArray<OrderFilterOption>,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("OrderFilterDefinition", "pkg/api/modules/orders.uts", 62, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return OrderFilterDefinitionReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class OrderFilterDefinitionReactiveObject : OrderFilterDefinition, IUTSReactive<OrderFilterDefinition> {
    override var __v_raw: OrderFilterDefinition
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: OrderFilterDefinition, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(key = __v_raw.key, param = __v_raw.param, label = __v_raw.label, control = __v_raw.control, aliases = __v_raw.aliases, multiple = __v_raw.multiple, options = __v_raw.options) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): OrderFilterDefinitionReactiveObject {
        return OrderFilterDefinitionReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
    override var options: UTSArray<OrderFilterOption>
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
open class OrderFilterOptionsResponse (
    @JsonNotNull
    open var resource: String,
    @JsonNotNull
    open var count: Number,
    @JsonNotNull
    open var filters: UTSArray<OrderFilterDefinition>,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("OrderFilterOptionsResponse", "pkg/api/modules/orders.uts", 71, 13)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return OrderFilterOptionsResponseReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class OrderFilterOptionsResponseReactiveObject : OrderFilterOptionsResponse, IUTSReactive<OrderFilterOptionsResponse> {
    override var __v_raw: OrderFilterOptionsResponse
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: OrderFilterOptionsResponse, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(resource = __v_raw.resource, count = __v_raw.count, filters = __v_raw.filters) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): OrderFilterOptionsResponseReactiveObject {
        return OrderFilterOptionsResponseReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
    override var filters: UTSArray<OrderFilterDefinition>
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
fun stringValue__12(value: Any?): String {
    if (value == null) {
        return ""
    }
    return "" + value
}
fun intValue__11(value: Any?): Number {
    val parsed = parseInt(stringValue__12(value))
    if (isNaN(parsed)) {
        return 0
    }
    return parsed
}
fun boolValue__5(value: Any?): Boolean {
    val text = stringValue__12(value).toLowerCase()
    return text == "true" || text == "1" || text == "yes"
}
fun stringArrayValue__5(value: Any?): UTSArray<String> {
    if (value == null) {
        return _uA<String>()
    }
    val rawArray = UTSAndroid.consoleDebugError(JSON.parseArray<Any>(JSON.stringify(value)), " at pkg/api/modules/orders.uts:94")
    if (rawArray == null) {
        return _uA<String>()
    }
    val result: UTSArray<String> = _uA()
    run {
        var index: Number = 0
        while(index < rawArray.length){
            result.push(stringValue__12(rawArray[index]))
            index += 1
        }
    }
    return result
}
fun parseObject__7(value: Any?): UTSJSONObject? {
    if (value == null) {
        return null
    }
    val text = JSON.stringify(value)
    if (text == null || text == "") {
        return null
    }
    return UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(text), " at pkg/api/modules/orders.uts:109")
}
fun parseObjectArray__5(value: Any?): UTSArray<UTSJSONObject> {
    if (value == null) {
        return _uA<UTSJSONObject>()
    }
    val text = JSON.stringify(value)
    if (text == null || text == "") {
        return _uA<UTSJSONObject>()
    }
    val parsed = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(text), " at pkg/api/modules/orders.uts:117")
    if (parsed == null) {
        return _uA<UTSJSONObject>()
    }
    return parsed!!
}
fun rawDataObject__3(raw: Any): UTSJSONObject {
    val rawObject = parseObject__7(raw)
    if (rawObject == null) {
        throw UTSError("订单接口响应解析失败")
    }
    val dataObject = parseObject__7(rawObject["data"])
    if (dataObject != null) {
        return dataObject!!
    }
    return rawObject!!
}
fun buildQuery__1(data: OrderListQuery): UTSJSONObject {
    val query: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("query", "pkg/api/modules/orders.uts", 132, 11), "page" to data.page, "page_size" to data.page_size)
    if (data.search != null && data.search != "") {
        query["search"] = data.search
    }
    if (data.status != null && data.status != "") {
        query["status"] = data.status
    }
    if (data.payment_method != null && data.payment_method != "") {
        query["payment_method"] = data.payment_method
    }
    if (data.cashier_id != null && data.cashier_id != "") {
        query["cashier_id"] = data.cashier_id
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
    var rows = parseObjectArray__5(payload["items"])
    if (rows.length > 0) {
        return rows
    }
    rows = parseObjectArray__5(payload["products"])
    if (rows.length > 0) {
        return rows
    }
    rows = parseObjectArray__5(payload["cart"])
    return rows
}
fun getPayloadQuantity(items: UTSArray<UTSJSONObject>): Number {
    var total: Number = 0
    run {
        var index: Number = 0
        while(index < items.length){
            val quantity = intValue__11(items[index]["quantity"])
            if (quantity > 0) {
                total = total + quantity
            }
            index += 1
        }
    }
    return total
}
fun buildOrderItem(rawObject: UTSJSONObject): OrderItem {
    val payload = parseObject__7(rawObject["payload"])
    val payloadObject = if (payload == null) {
        (_uO())
    } else {
        payload!!
    }
    val payloadItems = getPayloadItems(payloadObject)
    return OrderItem(id = intValue__11(rawObject["id"]), shop = intValue__11(rawObject["shop"]), shop_name = stringValue__12(rawObject["shop_name"]), order_number = stringValue__12(rawObject["order_number"]), payload = payloadObject, cashier_id = stringValue__12(rawObject["cashier_id"]), kasa_number = stringValue__12(rawObject["kasa_number"]), payment_method = stringValue__12(rawObject["payment_method"]), payment_method_display = stringValue__12(rawObject["payment_method_display"]), subtotal = stringValue__12(rawObject["subtotal"]), discount_amount = stringValue__12(rawObject["discount_amount"]), tax_amount = stringValue__12(rawObject["tax_amount"]), total_amount = stringValue__12(rawObject["total_amount"]), order_time = stringValue__12(rawObject["order_time"]), status = stringValue__12(rawObject["status"]), status_display = stringValue__12(rawObject["status_display"]), error_message = stringValue__12(rawObject["error_message"]), inventory_deducted = boolValue__5(rawObject["inventory_deducted"]), inventory_deduct_time = stringValue__12(rawObject["inventory_deduct_time"]), inventory_deduct_error = stringValue__12(rawObject["inventory_deduct_error"]), created_at = stringValue__12(rawObject["created_at"]), updated_at = stringValue__12(rawObject["updated_at"]), item_count = payloadItems.length, quantity_count = getPayloadQuantity(payloadItems))
}
fun buildItems__1(value: Any?): UTSArray<OrderItem> {
    val rawArray = parseObjectArray__5(value)
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
        paginationObject = parseObject__7(rawPagination)
    }
    val results = buildItems__1(rawObject["results"])
    var totalCount = intValue__11(rawObject["count"])
    if (totalCount <= 0) {
        totalCount = intValue__11(rawObject["total_count"])
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
    var currentPage = intValue__11(rawObject["current_page"])
    if (currentPage <= 0) {
        currentPage = intValue__11(rawObject["page"])
    }
    if (currentPage <= 0 && paginationObject != null) {
        currentPage = intValue__11(paginationObject!!["page"])
    }
    if (currentPage <= 0) {
        currentPage = query.page
    }
    var pageSize = intValue__11(rawObject["page_size"])
    if (pageSize <= 0 && paginationObject != null) {
        pageSize = intValue__11(paginationObject!!["page_size"])
    }
    if (pageSize <= 0) {
        pageSize = query.page_size
    }
    var totalPages = intValue__11(rawObject["total_pages"])
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__11(paginationObject!!["total_pages"])
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
    val statusDistribution = parseObject__7(rawObject["status_distribution"])
    return OrderStatistics(total_count = intValue__11(rawObject["total_count"]), inventory_deducted_count = intValue__11(rawObject["inventory_deducted_count"]), inventory_pending_count = intValue__11(rawObject["inventory_pending_count"]), received_count = if (statusDistribution == null) {
        0
    } else {
        intValue__11(statusDistribution!!["已接收"])
    }
    , processed_count = if (statusDistribution == null) {
        0
    } else {
        intValue__11(statusDistribution!!["已处理"])
    }
    , failed_count = if (statusDistribution == null) {
        0
    } else {
        intValue__11(statusDistribution!!["处理失败"])
    }
    )
}
fun buildOrderFilterOptionsResponse(raw: Any): OrderFilterOptionsResponse {
    val rawObject = rawDataObject__3(raw)
    var filters: UTSArray<OrderFilterDefinition> = _uA()
    val rawFilters = rawObject["filters"]
    if (rawFilters != null) {
        val filterObjects = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(JSON.stringify(rawFilters)), " at pkg/api/modules/orders.uts:260")
        if (filterObjects != null) {
            val nextFilters: UTSArray<OrderFilterDefinition> = _uA()
            run {
                var filterIndex: Number = 0
                while(filterIndex < filterObjects.length){
                    val filterObject = filterObjects[filterIndex]
                    var options: UTSArray<OrderFilterOption> = _uA()
                    val rawOptions = filterObject["options"]
                    if (rawOptions != null) {
                        val optionObjects = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(JSON.stringify(rawOptions)), " at pkg/api/modules/orders.uts:268")
                        if (optionObjects != null) {
                            val nextOptions: UTSArray<OrderFilterOption> = _uA()
                            run {
                                var optionIndex: Number = 0
                                while(optionIndex < optionObjects.length){
                                    val optionObject = optionObjects[optionIndex]
                                    nextOptions.push(OrderFilterOption(value = stringValue__12(optionObject["value"]), label = stringValue__12(optionObject["label"])))
                                    optionIndex += 1
                                }
                            }
                            options = nextOptions
                        }
                    }
                    nextFilters.push(OrderFilterDefinition(key = stringValue__12(filterObject["key"]), param = stringValue__12(filterObject["param"]), label = stringValue__12(filterObject["label"]), control = stringValue__12(filterObject["control"]), aliases = stringArrayValue__5(filterObject["aliases"]), multiple = stringValue__12(filterObject["multiple"]) == "true", options = options))
                    filterIndex += 1
                }
            }
            filters = nextFilters
        }
    }
    return OrderFilterOptionsResponse(resource = stringValue__12(rawObject["resource"]), count = intValue__11(rawObject["count"]), filters = filters)
}
fun detailPath__2(id: Any): String {
    return "/api/orders/orders/" + stringValue__12(id) + "/"
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
fun getOrderFilterOptions(): UTSPromise<OrderFilterOptionsResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/orders/orders/filter-options/", "GET", _uO(), true))
            return@w buildOrderFilterOptionsResponse(raw)
    })
}
fun getOrderStatistics(data: OrderListQuery): UTSPromise<OrderStatistics> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/orders/orders/statistics/", "GET", buildQuery__1(data), true))
            return@w buildStatistics(raw)
    })
}
open class DatePresetOption (
    @JsonNotNull
    open var key: String,
    @JsonNotNull
    open var text: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("DatePresetOption", "pages/orders/index.uvue", 127, 6)
    }
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return DatePresetOptionReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
class DatePresetOptionReactiveObject : DatePresetOption, IUTSReactive<DatePresetOption> {
    override var __v_raw: DatePresetOption
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: DatePresetOption, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(key = __v_raw.key, text = __v_raw.text) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): DatePresetOptionReactiveObject {
        return DatePresetOptionReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
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
    open var code: String,
    @JsonNotNull
    open var quantity: String,
    @JsonNotNull
    open var amount: String,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("PayloadRow", "pages/orders/from.uvue", 159, 6)
    }
}
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
    open var supplier: String? = null,
    open var category: String? = null,
    open var is_listed: String? = null,
    open var location: String? = null,
    open var from_location: String? = null,
    open var to_location: String? = null,
    open var transfer_order: String? = null,
    open var inventory_check: String? = null,
    open var product: String? = null,
    open var stock: String? = null,
    open var check_type: String? = null,
    open var is_checked: String? = null,
    open var transaction_type: String? = null,
    open var location_type: String? = null,
    open var is_active: String? = null,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("InventoryListQuery", "pkg/api/modules/inventory.uts", 2, 13)
    }
}
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("InventoryListResponse", "pkg/api/modules/inventory.uts", 24, 13)
    }
}
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("StockAdjustmentData", "pkg/api/modules/inventory.uts", 31, 13)
    }
}
open class InventoryStockCreateForProductData (
    @JsonNotNull
    open var product: Number,
    @JsonNotNull
    open var location: Number,
    @JsonNotNull
    open var quantity: Number,
    @JsonNotNull
    open var transaction_type: String,
    @JsonNotNull
    open var unit_cost: String,
    @JsonNotNull
    open var notes: String,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("InventoryStockCreateForProductData", "pkg/api/modules/inventory.uts", 38, 13)
    }
}
open class InventoryMutationData (
    @JsonNotNull
    open var payload: UTSJSONObject,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("InventoryMutationData", "pkg/api/modules/inventory.uts", 46, 13)
    }
}
fun stringValue__13(value: Any?): String {
    if (value == null) {
        return ""
    }
    return "" + value
}
fun intValue__12(value: Any?): Number {
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
    val query: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("query", "pkg/api/modules/inventory.uts", 63, 11), "page" to data.page, "page_size" to data.page_size)
    if (data.search != null && data.search != "") {
        query["search"] = data.search
    }
    if (data.status != null && data.status != "") {
        query["status"] = data.status
    }
    if (data.alert_status != null && data.alert_status != "") {
        query["alert_status"] = data.alert_status
    }
    if (data.supplier != null && data.supplier != "") {
        query["supplier"] = data.supplier
    }
    if (data.category != null && data.category != "") {
        query["category"] = data.category
    }
    if (data.is_listed != null && data.is_listed != "") {
        query["is_listed"] = data.is_listed
    }
    if (data.location != null && data.location != "") {
        query["location"] = data.location
    }
    if (data.from_location != null && data.from_location != "") {
        query["from_location"] = data.from_location
    }
    if (data.to_location != null && data.to_location != "") {
        query["to_location"] = data.to_location
    }
    if (data.transfer_order != null && data.transfer_order != "") {
        query["transfer_order"] = data.transfer_order
    }
    if (data.inventory_check != null && data.inventory_check != "") {
        query["inventory_check"] = data.inventory_check
    }
    if (data.product != null && data.product != "") {
        query["product"] = data.product
    }
    if (data.stock != null && data.stock != "") {
        query["stock"] = data.stock
    }
    if (data.check_type != null && data.check_type != "") {
        query["check_type"] = data.check_type
    }
    if (data.is_checked != null && data.is_checked != "") {
        query["is_checked"] = data.is_checked
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
fun parseObjectArray__6(value: Any?): UTSArray<UTSJSONObject> {
    if (value == null) {
        return _uA<UTSJSONObject>()
    }
    val text = JSON.stringify(value)
    if (text == null || text == "") {
        return _uA<UTSJSONObject>()
    }
    val trimmedText = text.trim()
    if (trimmedText == "" || trimmedText.substring(0, 1) != "[") {
        return _uA<UTSJSONObject>()
    }
    var parsed: UTSArray<UTSJSONObject>? = null
    try {
        parsed = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(trimmedText), " at pkg/api/modules/inventory.uts:116")
    }
     catch (error: Throwable) {
        return _uA<UTSJSONObject>()
    }
    if (parsed == null) {
        return _uA<UTSJSONObject>()
    }
    return parsed!!
}
fun parseObject__8(value: Any?): UTSJSONObject? {
    if (value == null) {
        return null
    }
    val text = JSON.stringify(value)
    if (text == null || text == "") {
        return null
    }
    val trimmedText = text.trim()
    if (trimmedText == "" || trimmedText.substring(0, 1) != "{") {
        return null
    }
    try {
        return UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(trimmedText), " at pkg/api/modules/inventory.uts:135")
    }
     catch (error: Throwable) {
        return null
    }
}
fun buildListResponse__3(raw: Any, query: InventoryListQuery): InventoryListResponse {
    val directRows = parseObjectArray__6(raw)
    val rawObject = parseObject__8(raw)
    if (rawObject == null) {
        return InventoryListResponse(results = directRows, total_count = directRows.length, total_pages = 1, current_page = query.page, page_size = query.page_size)
    }
    var sourceObject = rawObject!!
    val nestedDataObject = parseObject__8(rawObject!!["data"])
    if (parseObjectArray__6(sourceObject["results"]).length == 0 && nestedDataObject != null) {
        sourceObject = nestedDataObject!!
    }
    var paginationObject: UTSJSONObject? = null
    var rawPagination = sourceObject["pagination"]
    if (rawPagination == null) {
        rawPagination = rawObject!!["pagination"]
    }
    if (rawPagination != null) {
        paginationObject = parseObject__8(rawPagination)
    }
    var results = parseObjectArray__6(sourceObject["results"])
    if (results.length == 0) {
        results = parseObjectArray__6(sourceObject["data"])
    }
    if (results.length == 0) {
        results = directRows
    }
    var totalCount = intValue__12(sourceObject["count"])
    if (totalCount <= 0) {
        totalCount = intValue__12(sourceObject["total_count"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__12(paginationObject!!["total"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue__12(paginationObject!!["count"])
    }
    if (totalCount <= 0) {
        totalCount = results.length
    }
    var currentPage = intValue__12(sourceObject["current_page"])
    if (currentPage <= 0) {
        currentPage = intValue__12(sourceObject["page"])
    }
    if (currentPage <= 0 && paginationObject != null) {
        currentPage = intValue__12(paginationObject!!["page"])
    }
    if (currentPage <= 0) {
        currentPage = query.page
    }
    var pageSize = intValue__12(sourceObject["page_size"])
    if (pageSize <= 0 && paginationObject != null) {
        pageSize = intValue__12(paginationObject!!["page_size"])
    }
    if (pageSize <= 0) {
        pageSize = query.page_size
    }
    var totalPages = intValue__12(sourceObject["total_pages"])
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue__12(paginationObject!!["total_pages"])
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
fun stockCreateForProductBody(data: InventoryStockCreateForProductData): UTSJSONObject {
    return _uO("product" to data.product, "location" to data.location, "quantity" to data.quantity, "transaction_type" to data.transaction_type, "unit_cost" to data.unit_cost, "notes" to data.notes)
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
fun getInventoryTransferItems(data: InventoryListQuery): UTSPromise<InventoryListResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/inventory/transfer-order-items/", "GET", buildQuery__2(data), true))
            return@w buildListResponse__3(raw, data)
    })
}
fun getInventoryChecks(data: InventoryListQuery): UTSPromise<InventoryListResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/inventory/checks/", "GET", buildQuery__2(data), true))
            return@w buildListResponse__3(raw, data)
    })
}
fun getInventoryCheckItems(data: InventoryListQuery): UTSPromise<InventoryListResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/inventory/check-items/", "GET", buildQuery__2(data), true))
            return@w buildListResponse__3(raw, data)
    })
}
fun getInventoryTransactions(data: InventoryListQuery): UTSPromise<InventoryListResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/inventory/transactions/", "GET", buildQuery__2(data), true))
            return@w buildListResponse__3(raw, data)
    })
}
fun getInventoryStockDetail(id: String): UTSPromise<UTSJSONObject> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/inventory/stocks/" + stringValue__13(id) + "/", "GET", _uO(), true))
            val parsed = parseObject__8(raw)
            if (parsed == null) {
                throw UTSError("库存详情响应解析失败")
            }
            return@w parsed!!
    })
}
fun getInventoryLocationDetail(id: String): UTSPromise<UTSJSONObject> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/inventory/locations/" + stringValue__13(id) + "/", "GET", _uO(), true))
            val parsed = parseObject__8(raw)
            if (parsed == null) {
                throw UTSError("库存位置详情响应解析失败")
            }
            return@w parsed!!
    })
}
fun getInventoryTransferDetail(id: String): UTSPromise<UTSJSONObject> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/inventory/transfer-orders/" + stringValue__13(id) + "/", "GET", _uO(), true))
            val parsed = parseObject__8(raw)
            if (parsed == null) {
                throw UTSError("调拨单详情响应解析失败")
            }
            return@w parsed!!
    })
}
fun getInventoryCheckDetail(id: String): UTSPromise<UTSJSONObject> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/inventory/checks/" + stringValue__13(id) + "/", "GET", _uO(), true))
            val parsed = parseObject__8(raw)
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
    return request("/api/inventory/locations/" + stringValue__13(id) + "/", "PUT", data.payload, true)
}
fun createInventoryTransfer(data: InventoryMutationData): UTSPromise<Any> {
    return request("/api/inventory/transfer-orders/", "POST", data.payload, true)
}
fun updateInventoryTransfer(id: String, data: InventoryMutationData): UTSPromise<Any> {
    return request("/api/inventory/transfer-orders/" + stringValue__13(id) + "/", "PUT", data.payload, true)
}
fun createInventoryTransferItem(data: InventoryMutationData): UTSPromise<Any> {
    return request("/api/inventory/transfer-order-items/", "POST", data.payload, true)
}
fun createInventoryCheck(data: InventoryMutationData): UTSPromise<Any> {
    return request("/api/inventory/checks/", "POST", data.payload, true)
}
fun updateInventoryCheck(id: String, data: InventoryMutationData): UTSPromise<Any> {
    return request("/api/inventory/checks/" + stringValue__13(id) + "/", "PUT", data.payload, true)
}
fun createInventoryCheckItem(data: InventoryMutationData): UTSPromise<Any> {
    return request("/api/inventory/check-items/", "POST", data.payload, true)
}
fun adjustInventoryStock(data: StockAdjustmentData): UTSPromise<Any> {
    return request("/api/inventory/stocks/adjust/", "POST", stockAdjustmentBody(data), true)
}
fun createInventoryStockForProduct(data: InventoryStockCreateForProductData): UTSPromise<Any> {
    return request("/api/inventory/stocks/create-for-product/", "POST", stockCreateForProductBody(data), true)
}
fun approveInventoryTransfer(id: String): UTSPromise<Any> {
    return request("/api/inventory/transfer-orders/" + stringValue__13(id) + "/approve/", "POST", _uO(), true)
}
fun completeInventoryTransfer(id: String): UTSPromise<Any> {
    return request("/api/inventory/transfer-orders/" + stringValue__13(id) + "/complete/", "POST", _uO(), true)
}
fun cancelInventoryTransfer(id: String): UTSPromise<Any> {
    return request("/api/inventory/transfer-orders/" + stringValue__13(id) + "/cancel/", "POST", _uO(), true)
}
fun startInventoryCheck(id: String): UTSPromise<Any> {
    return request("/api/inventory/checks/" + stringValue__13(id) + "/start/", "POST", _uO(), true)
}
fun submitInventoryCheck(id: String): UTSPromise<Any> {
    return request("/api/inventory/checks/" + stringValue__13(id) + "/submit/", "POST", _uO(), true)
}
fun approveInventoryCheck(id: String): UTSPromise<Any> {
    return request("/api/inventory/checks/" + stringValue__13(id) + "/approve/", "POST", _uO(), true)
}
fun adjustInventoryCheck(id: String): UTSPromise<Any> {
    return request("/api/inventory/checks/" + stringValue__13(id) + "/adjust/", "POST", _uO(), true)
}
open class SelectOption__8 (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var label: String,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SelectOption", "pages/inventory-management/index.uvue", 196, 6)
    }
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
open class SelectOption__9 (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var label: String,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SelectOption", "pages/inventory-management/from.uvue", 324, 6)
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
open class SelectOption__10 (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var text: String,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SelectOption", "pages/inventory-locations/from.uvue", 15, 6)
    }
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
open class SelectOption__11 (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var label: String,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SelectOption", "pages/inventory-transfers/index.uvue", 146, 6)
    }
}
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
val GenPagesInventoryTransfersCreateClass = CreateVueComponent(GenPagesInventoryTransfersCreate::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesInventoryTransfersCreate.inheritAttrs, inject = GenPagesInventoryTransfersCreate.inject, props = GenPagesInventoryTransfersCreate.props, propsNeedCastKeys = GenPagesInventoryTransfersCreate.propsNeedCastKeys, emits = GenPagesInventoryTransfersCreate.emits, components = GenPagesInventoryTransfersCreate.components, styles = GenPagesInventoryTransfersCreate.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesInventoryTransfersCreate.setup(props as GenPagesInventoryTransfersCreate)
    }
    )
}
, fun(instance, renderer): GenPagesInventoryTransfersCreate {
    return GenPagesInventoryTransfersCreate(instance, renderer)
}
)
val GenPagesInventoryTransfersDetailsIndexClass = CreateVueComponent(GenPagesInventoryTransfersDetailsIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesInventoryTransfersDetailsIndex.inheritAttrs, inject = GenPagesInventoryTransfersDetailsIndex.inject, props = GenPagesInventoryTransfersDetailsIndex.props, propsNeedCastKeys = GenPagesInventoryTransfersDetailsIndex.propsNeedCastKeys, emits = GenPagesInventoryTransfersDetailsIndex.emits, components = GenPagesInventoryTransfersDetailsIndex.components, styles = GenPagesInventoryTransfersDetailsIndex.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesInventoryTransfersDetailsIndex.setup(props as GenPagesInventoryTransfersDetailsIndex)
    }
    )
}
, fun(instance, renderer): GenPagesInventoryTransfersDetailsIndex {
    return GenPagesInventoryTransfersDetailsIndex(instance, renderer)
}
)
open class SelectOption__12 (
    @JsonNotNull
    open var value: String,
    @JsonNotNull
    open var label: String,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("SelectOption", "pages/inventory-checks/index.uvue", 141, 6)
    }
}
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
val GenPagesInventoryChecksCreateClass = CreateVueComponent(GenPagesInventoryChecksCreate::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesInventoryChecksCreate.inheritAttrs, inject = GenPagesInventoryChecksCreate.inject, props = GenPagesInventoryChecksCreate.props, propsNeedCastKeys = GenPagesInventoryChecksCreate.propsNeedCastKeys, emits = GenPagesInventoryChecksCreate.emits, components = GenPagesInventoryChecksCreate.components, styles = GenPagesInventoryChecksCreate.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesInventoryChecksCreate.setup(props as GenPagesInventoryChecksCreate)
    }
    )
}
, fun(instance, renderer): GenPagesInventoryChecksCreate {
    return GenPagesInventoryChecksCreate(instance, renderer)
}
)
val GenPagesInventoryChecksDetailsIndexClass = CreateVueComponent(GenPagesInventoryChecksDetailsIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesInventoryChecksDetailsIndex.inheritAttrs, inject = GenPagesInventoryChecksDetailsIndex.inject, props = GenPagesInventoryChecksDetailsIndex.props, propsNeedCastKeys = GenPagesInventoryChecksDetailsIndex.propsNeedCastKeys, emits = GenPagesInventoryChecksDetailsIndex.emits, components = GenPagesInventoryChecksDetailsIndex.components, styles = GenPagesInventoryChecksDetailsIndex.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenPagesInventoryChecksDetailsIndex.setup(props as GenPagesInventoryChecksDetailsIndex)
    }
    )
}
, fun(instance, renderer): GenPagesInventoryChecksDetailsIndex {
    return GenPagesInventoryChecksDetailsIndex(instance, renderer)
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
    __uniRoutes.push(UniPageRoute(path = "pages/suppliers_procure/index", component = GenPagesSuppliersProcureIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/kasa_category/index", component = GenPagesKasaCategoryIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/kasa_category/form", component = GenPagesKasaCategoryFormClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/category/index", component = GenPagesCategoryIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/category/from", component = GenPagesCategoryFromClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/shop/index", component = GenPagesShopIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/shop/media", component = GenPagesShopMediaClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/shop/from", component = GenPagesShopFromClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/products/from", component = GenPagesProductsFromClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/products/discount-selector", component = GenPagesProductsDiscountSelectorClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/products/price-calculator", component = GenPagesProductsPriceCalculatorClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/products/pricing-formula", component = GenPagesProductsPricingFormulaClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/products/pricing-formula/index", component = GenPagesProductsPricingFormulaIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/products/pricing-formula/from", component = GenPagesProductsPricingFormulaFromClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/label-print/index", component = GenPagesLabelPrintIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/printer-settings/index", component = GenPagesPrinterSettingsIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/label-templates/index", component = GenPagesLabelTemplatesIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/label-templates/from", component = GenPagesLabelTemplatesFromClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/label-templates/details/index", component = GenPagesLabelTemplatesDetailsIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/products/config-model/index", component = GenPagesProductsConfigModelIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/products/config-model/from", component = GenPagesProductsConfigModelFromClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/ksef/index", component = GenPagesKsefIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/ksef/detail", component = GenPagesKsefDetailClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/expenses/index", component = GenPagesExpensesIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/expenses/from", component = GenPagesExpensesFromClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/purchases/index", component = GenPagesPurchasesIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/purchases/from", component = GenPagesPurchasesFromClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/purchases/details/index", component = GenPagesPurchasesDetailsIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/purchases/details/from", component = GenPagesPurchasesDetailsFromClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/purchases/details/quick-input", component = GenPagesPurchasesDetailsQuickInputClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/purchases/details/excel-upload", component = GenPagesPurchasesDetailsExcelUploadClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/orders/index", component = GenPagesOrdersIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/orders/from", component = GenPagesOrdersFromClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/inventory-management/index", component = GenPagesInventoryManagementIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/inventory-management/from", component = GenPagesInventoryManagementFromClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/inventory-locations/index", component = GenPagesInventoryLocationsIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/inventory-locations/from", component = GenPagesInventoryLocationsFromClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/inventory-transfers/index", component = GenPagesInventoryTransfersIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/inventory-transfers/from", component = GenPagesInventoryTransfersFromClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/inventory-transfers/create", component = GenPagesInventoryTransfersCreateClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/inventory-transfers/details/index", component = GenPagesInventoryTransfersDetailsIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/inventory-checks/index", component = GenPagesInventoryChecksIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/inventory-checks/from", component = GenPagesInventoryChecksFromClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/inventory-checks/create", component = GenPagesInventoryChecksCreateClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/inventory-checks/details/index", component = GenPagesInventoryChecksDetailsIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
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
