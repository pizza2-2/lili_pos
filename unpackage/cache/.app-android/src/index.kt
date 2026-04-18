@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME", "UNUSED_ANONYMOUS_PARAMETER", "NAME_SHADOWING", "UNNECESSARY_NOT_NULL_ASSERTION")
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
import io.dcloud.uniapp.extapi.connectSocket as uni_connectSocket
import io.dcloud.uniapp.extapi.createPushMessage as uni_createPushMessage
import io.dcloud.uniapp.extapi.exit as uni_exit
import io.dcloud.uniapp.extapi.getAppAuthorizeSetting as uni_getAppAuthorizeSetting
import io.dcloud.uniapp.extapi.getPrivacySetting as uni_getPrivacySetting
import io.dcloud.uniapp.extapi.getPushChannelManager as uni_getPushChannelManager
import io.dcloud.uniapp.extapi.getPushClientId as uni_getPushClientId
import io.dcloud.uniapp.extapi.getStorageSync as uni_getStorageSync
import io.dcloud.uniapp.extapi.hideLoading as uni_hideLoading
import io.dcloud.uniapp.extapi.hideToast as uni_hideToast
import io.dcloud.uniapp.extapi.onPrivacyAuthorizationChange as uni_onPrivacyAuthorizationChange
import io.dcloud.uniapp.extapi.onPushMessage as uni_onPushMessage
import io.dcloud.uniapp.extapi.openDialogPage as uni_openDialogPage
import uts.sdk.modules.uniRegisterRequestPermissionTips.registerRequestPermissionTipsListener
import uts.sdk.modules.uniRegisterRequestPermissionTips.unregisterRequestPermissionTipsListener
import uts.sdk.modules.uniRegisterRequestPermissionTips.setRequestPermissionTips
import io.dcloud.uniapp.extapi.removeInterceptor as uni_removeInterceptor
import io.dcloud.uniapp.extapi.removeStorage as uni_removeStorage
import io.dcloud.uniapp.extapi.request as uni_request
import io.dcloud.uniapp.extapi.setStorage as uni_setStorage
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
val runBlock1 = run {
    __uniConfig.getAppStyles = fun(): Map<String, Map<String, Map<String, Any>>> {
        return GenApp.styles
    }
}
fun initRuntimeSocket(hosts: String, port: String, id: String): UTSPromise<SocketTask?> {
    if (hosts == "" || port == "" || id == "") {
        return UTSPromise.resolve(null)
    }
    return hosts.split(",").reduce<UTSPromise<SocketTask?>>(fun(promise: UTSPromise<SocketTask?>, host: String): UTSPromise<SocketTask?> {
        return promise.then(fun(socket): UTSPromise<SocketTask?> {
            if (socket != null) {
                return UTSPromise.resolve(socket)
            }
            return tryConnectSocket(host, port, id)
        }
        )
    }
    , UTSPromise.resolve(null))
}
val SOCKET_TIMEOUT: Number = 500
fun tryConnectSocket(host: String, port: String, id: String): UTSPromise<SocketTask?> {
    return UTSPromise(fun(resolve, reject){
        val socket = uni_connectSocket(ConnectSocketOptions(url = "ws://" + host + ":" + port + "/" + id, fail = fun(_) {
            resolve(null)
        }
        ))
        val timer = setTimeout(fun(){
            socket.close(CloseSocketOptions(code = 1006, reason = "connect timeout"))
            resolve(null)
        }
        , SOCKET_TIMEOUT)
        socket.onOpen(fun(e){
            clearTimeout(timer)
            resolve(socket)
        }
        )
        socket.onClose(fun(e){
            clearTimeout(timer)
            resolve(null)
        }
        )
        socket.onError(fun(e){
            clearTimeout(timer)
            resolve(null)
        }
        )
    }
    )
}
fun initRuntimeSocketService(): UTSPromise<Boolean> {
    val hosts: String = "192.168.0.163,127.0.0.1,172.27.192.1"
    val port: String = "8090"
    val id: String = "app-android_IEmG4l"
    if (hosts == "" || port == "" || id == "") {
        return UTSPromise.resolve(false)
    }
    var socketTask: SocketTask? = null
    __registerWebViewUniConsole(fun(): String {
        return "!function(){\"use strict\";\"function\"==typeof SuppressedError&&SuppressedError;var e=[\"log\",\"warn\",\"error\",\"info\",\"debug\"],n=e.reduce((function(e,n){return e[n]=console[n].bind(console),e}),{}),t=null,r=new Set,o={};function i(e){if(null!=t){var n=e.map((function(e){if(\"string\"==typeof e)return e;var n=e&&\"promise\"in e&&\"reason\"in e,t=n?\"UnhandledPromiseRejection: \":\"\";if(n&&(e=e.reason),e instanceof Error&&e.stack)return e.message&&!e.stack.includes(e.message)?\"\".concat(t).concat(e.message,\"\\n\").concat(e.stack):\"\".concat(t).concat(e.stack);if(\"object\"==typeof e&&null!==e)try{return t+JSON.stringify(e)}catch(e){return t+String(e)}return t+String(e)})).filter(Boolean);n.length>0&&t(JSON.stringify(Object.assign({type:\"error\",data:n},o)))}else e.forEach((function(e){r.add(e)}))}function a(e,n){try{return{type:e,args:u(n)}}catch(e){}return{type:e,args:[]}}function u(e){return e.map((function(e){return c(e)}))}function c(e,n){if(void 0===n&&(n=0),n>=7)return{type:\"object\",value:\"[Maximum depth reached]\"};switch(typeof e){case\"string\":return{type:\"string\",value:e};case\"number\":return function(e){return{type:\"number\",value:String(e)}}(e);case\"boolean\":return function(e){return{type:\"boolean\",value:String(e)}}(e);case\"object\":try{return function(e,n){if(null===e)return{type:\"null\"};if(function(e){return e.\$&&s(e.\$)}(e))return function(e,n){return{type:\"object\",className:\"ComponentPublicInstance\",value:{properties:Object.entries(e.\$.type).map((function(e){return f(e[0],e[1],n+1)}))}}}(e,n);if(s(e))return function(e,n){return{type:\"object\",className:\"ComponentInternalInstance\",value:{properties:Object.entries(e.type).map((function(e){return f(e[0],e[1],n+1)}))}}}(e,n);if(function(e){return e.style&&null!=e.tagName&&null!=e.nodeName}(e))return function(e,n){return{type:\"object\",value:{properties:Object.entries(e).filter((function(e){var n=e[0];return[\"id\",\"tagName\",\"nodeName\",\"dataset\",\"offsetTop\",\"offsetLeft\",\"style\"].includes(n)})).map((function(e){return f(e[0],e[1],n+1)}))}}}(e,n);if(function(e){return\"function\"==typeof e.getPropertyValue&&\"function\"==typeof e.setProperty&&e.\$styles}(e))return function(e,n){return{type:\"object\",value:{properties:Object.entries(e.\$styles).map((function(e){return f(e[0],e[1],n+1)}))}}}(e,n);if(Array.isArray(e))return{type:\"object\",subType:\"array\",value:{properties:e.map((function(e,t){return function(e,n,t){var r=c(e,t);return r.name=\"\".concat(n),r}(e,t,n+1)}))}};if(e instanceof Set)return{type:\"object\",subType:\"set\",className:\"Set\",description:\"Set(\".concat(e.size,\")\"),value:{entries:Array.from(e).map((function(e){return function(e,n){return{value:c(e,n)}}(e,n+1)}))}};if(e instanceof Map)return{type:\"object\",subType:\"map\",className:\"Map\",description:\"Map(\".concat(e.size,\")\"),value:{entries:Array.from(e.entries()).map((function(e){return function(e,n){return{key:c(e[0],n),value:c(e[1],n)}}(e,n+1)}))}};if(e instanceof Promise)return{type:\"object\",subType:\"promise\",value:{properties:[]}};if(e instanceof RegExp)return{type:\"object\",subType:\"regexp\",value:String(e),className:\"Regexp\"};if(e instanceof Date)return{type:\"object\",subType:\"date\",value:String(e),className:\"Date\"};if(e instanceof Error)return{type:\"object\",subType:\"error\",value:e.message||String(e),className:e.name||\"Error\"};var t=void 0,r=e.constructor;r&&r.get\$UTSMetadata\$&&(t=r.get\$UTSMetadata\$().name);var o=Object.entries(e);(function(e){return e.modifier&&e.modifier._attribute&&e.nodeContent})(e)&&(o=o.filter((function(e){var n=e[0];return\"modifier\"!==n&&\"nodeContent\"!==n})));return{type:\"object\",className:t,value:{properties:o.map((function(e){return f(e[0],e[1],n+1)}))}}}(e,n)}catch(e){return{type:\"object\",value:{properties:[]}}}case\"undefined\":return{type:\"undefined\"};case\"function\":return function(e){return{type:\"function\",value:\"function \".concat(e.name,\"() {}\")}}(e);case\"symbol\":return function(e){return{type:\"symbol\",value:e.description}}(e);case\"bigint\":return function(e){return{type:\"bigint\",value:String(e)}}(e)}}function s(e){return e.type&&null!=e.uid&&e.appContext}function f(e,n,t){var r=c(n,t);return r.name=e,r}var l=null,p=[],y={},g=\"---BEGIN:EXCEPTION---\",d=\"---END:EXCEPTION---\";function v(e){null!=l?l(JSON.stringify(Object.assign({type:\"console\",data:e},y))):p.push.apply(p,e)}var m=/^\\s*at\\s+[\\w/./-]+:\\d+\$/;function b(){function t(e){return function(){for(var t=[],r=0;r<arguments.length;r++)t[r]=arguments[r];var o=function(e,n,t){if(t||2===arguments.length)for(var r,o=0,i=n.length;o<i;o++)!r&&o in n||(r||(r=Array.prototype.slice.call(n,0,o)),r[o]=n[o]);return e.concat(r||Array.prototype.slice.call(n))}([],t,!0);if(o.length){var u=o[o.length-1];\"string\"==typeof u&&m.test(u)&&o.pop()}if(n[e].apply(n,o),\"error\"===e&&1===t.length){var c=t[0];if(\"string\"==typeof c&&c.startsWith(g)){var s=g.length,f=c.length-d.length;return void i([c.slice(s,f)])}if(c instanceof Error)return void i([c])}v([a(e,t)])}}return function(){var e=console.log,n=Symbol();try{console.log=n}catch(e){return!1}var t=console.log===n;return console.log=e,t}()?(e.forEach((function(e){console[e]=t(e)})),function(){e.forEach((function(e){console[e]=n[e]}))}):function(){}}function _(e){var n={type:\"WEB_INVOKE_APPSERVICE\",args:{data:{name:\"console\",arg:e}}};return window.__uniapp_x_postMessageToService?window.__uniapp_x_postMessageToService(n):window.__uniapp_x_.postMessageToService(JSON.stringify(n))}!function(){if(!window.__UNI_CONSOLE_WEBVIEW__){window.__UNI_CONSOLE_WEBVIEW__=!0;var e=\"[web-view]\".concat(window.__UNI_PAGE_ROUTE__?\"[\".concat(window.__UNI_PAGE_ROUTE__,\"]\"):\"\");b(),function(e,n){if(void 0===n&&(n={}),l=e,Object.assign(y,n),null!=e&&p.length>0){var t=p.slice();p.length=0,v(t)}}((function(e){_(e)}),{channel:e}),function(e,n){if(void 0===n&&(n={}),t=e,Object.assign(o,n),null!=e&&r.size>0){var a=Array.from(r);r.clear(),i(a)}}((function(e){_(e)}),{channel:e}),window.addEventListener(\"error\",(function(e){i([e.error])})),window.addEventListener(\"unhandledrejection\",(function(e){i([e])}))}}()}();"
    }
    , fun(data: String){
        socketTask?.send(SendSocketMessageOptions(data = data))
    }
    )
    return UTSPromise.resolve().then(fun(): UTSPromise<Boolean> {
        return initRuntimeSocket(hosts, port, id).then(fun(socket): Boolean {
            if (socket == null) {
                return false
            }
            socketTask = socket
            return true
        }
        )
    }
    ).`catch`(fun(): Boolean {
        return false
    }
    )
}
val runBlock2 = run {
    initRuntimeSocketService()
}
val isAgreePrivacyState = ref(false)
val runBlock3 = run {
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
                        uni_createPushMessage(CreatePushMessageOptions(title = res.data["title"] as String?, content = res.data["content"] as String, cover = true, channelId = "channel-id", `when` = Date.now() + 10000, icon = "/static/logo.png", sound = "system", delay = 1, payload = object : UTSJSONObject() {
                            var pkey = "pvalue1"
                        }, category = "IM", success = fun(res) {
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
@JvmField
val authState = reactive(AuthState(token = "", userInfo = UserInfoState(id = 0, name = "", avatar = "")))
fun authLocalStorage() {
    uni_setStorage(SetStorageOptions(key = "authStateKey", data = JSON.stringify(authState)))
}
val clearAuthState = fun(){
    authState.token = ""
    authState.userInfo = null
    uni_removeStorage(RemoveStorageOptions(key = "authStateKey"))
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
        var storageResult = UTSAndroid.consoleDebugError(JSON.parse<AuthState>(authStateString), " at store/auth.uts:56")
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
, success = fun(res: NavigateBackSuccess) {
    console.log("拦截 navigateTo 接口 success 返回参数为：", res, " at pkg/router/permission.uts:26")
}
, fail = fun(err: NavigateToFail) {
    console.log("拦截 navigateTo 接口 fail 返回参数为：", err, " at pkg/router/permission.uts:29")
}
)
val switchTabInterceptor = AddInterceptorOptions(invoke = fun(options: SwitchTabOptions) {
    console.log("拦截 switchTab 接口传入参数为：", options, " at pkg/router/permission.uts:36")
    val url: String = options.url.split("?")[0]
    console.log(url, " at pkg/router/permission.uts:39")
}
, success = fun(res: SwitchTabSuccess) {
    console.log("拦截 switchTab 接口 success 返回参数为：", res, " at pkg/router/permission.uts:42")
}
, fail = fun(err: SwitchTabFail) {
    console.log("拦截 switchTab 接口 fail 返回参数为：", err, " at pkg/router/permission.uts:45")
}
, complete = fun(res: SwitchTabComplete) {
    console.log("拦截 switchTab 接口 complete 返回参数为：", res, " at pkg/router/permission.uts:48")
}
)
fun routerPermission() {
    uni_addInterceptor("navigateTo", navigateToInterceptor)
    uni_addInterceptor("switchTab", switchTabInterceptor)
}
fun removeRouterPermission() {
    uni_removeInterceptor("navigateTo", null)
    uni_removeInterceptor("switchTab", null)
}
val timeNumber = ref<Number>(15)
val disAllowLocation = ref<Number>(0)
val disAllowCamera = ref<Number>(0)
val disAllowAlbum = ref<Number>(0)
var PermissionTips: UTSJSONObject = object : UTSJSONObject(UTSSourceMapPosition("PermissionTips", "pkg/util/osPermission.uts", 17, 5)) {
}
val runBlock4 = run {
    PermissionTips["android.permission.ACCESS_COARSE_LOCATION"] = "<h1>定位权限说明</h1><p style=\"color:#cccccc\">为了提供您所在区域的信息服务，我们需要获取您设备所在区域信息。以便于向您展示商品信息。</p>"
    PermissionTips["android.permission.ACCESS_FINE_LOCATION"] = "<h1>定位权限说明</h1><p style=\"color:#cccccc\">为了提供您所在区域的信息服务，我们需要获取您设备所在区域信息。以便于向您展示商品信息。</p>"
    PermissionTips["android.permission.CAMERA"] = "<h1>相机权限说明</h1><p style=\"color:#cccccc\">便于您使用该功能上传您的照片用于更换头像、扫码、拍照，发布商品等场景</p>"
    PermissionTips["android.permission.READ_EXTERNAL_STORAGE"] = "<h1>相册权限说明</h1><p style=\"color:#cccccc\">便于您使用该功能上传您的照片/图片/视频及用于更换头像、拍照，发布商品等场景中读取相册和文件内容</p>"
    PermissionTips["android.permission.WRITE_EXTERNAL_STORAGE"] = "<h1>相册权限说明</h1><p style=\"color:#cccccc\">便于您使用该功能上传您的照片/图片/视频及用于更换头像、拍照，发布商品等场景中写入相册和文件内容</p>"
    PermissionTips["android.permission.CALL_PHONE"] = "<h1>电话权限说明</h1><p style=\"color:#cccccc\">为了您能和商家客户进行电话通话，我们需要获取您拨打电话的权限。</p>"
}
fun registerOSPermission() {
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
val baseUrl: String = "http://192.168.0.163:8000"
val timeOut: Number = 10000
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
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("RootType", "pkg/api/index.uts", 5, 6)
    }
}
fun requestIntercept(reqData: UTSJSONObject): Map<String, UTSJSONObject> {
    val map = Map<String, UTSJSONObject>()
    val header: UTSJSONObject = object : UTSJSONObject(UTSSourceMapPosition("header", "pkg/api/index.uts", 17, 11)) {
        var `content-type` = "application/json"
    }
    if (authState.token != "") {
        header["Authorization"] = authState.token
    }
    var timestamp = Date().getTime().toString(10)
    reqData["timestamp"] = timestamp
    map.set("header", header as UTSJSONObject)
    map.set("data", reqData as UTSJSONObject)
    return map
}
fun request(url: String, method: RequestMethod, reqData: UTSJSONObject = UTSJSONObject(), showLoading: Boolean = false): UTSPromise<Any> {
    return wrapUTSPromise(suspend w@{
            return@w UTSPromise(fun(resolve, reject){
                if (showLoading) {
                    uni_showLoading(ShowLoadingOptions(title = "loading"))
                }
                val interceptMap = requestIntercept(reqData)
                console.log("请求地址:", baseUrl + url, " at pkg/api/index.uts:46")
                uni_request<RootType>(RequestOptions(url = baseUrl + url, method = method, header = interceptMap.get("header"), data = interceptMap.get("data"), timeout = timeOut, success = fun(res){
                    if (res.statusCode == 200) {
                        if (res.data != null && res.data!!.success == true) {
                            resolve(res.data!!.data)
                            return
                        } else {
                            reject(UTSError(res.data?.message ?: "请求失败"))
                        }
                    } else {
                        reject(UTSError("HTTP状态码错误: " + res.statusCode))
                    }
                }
                , fail = fun(err){
                    var message = "网络请求失败"
                    if (err != null) {
                        val errorText = JSON.stringify(err)
                        if (errorText != null && errorText != "") {
                            val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pkg/api/index.uts:75")
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
                        uni_hideLoading()
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
fun accountLogin(data: LoginData): UTSPromise<LoginResponse> {
    return wrapUTSPromise(suspend w@{
            val body: UTSJSONObject = object : UTSJSONObject(UTSSourceMapPosition("body", "pkg/api/modules/login.uts", 52, 11)) {
                var username = data.username
                var password = data.password
            }
            val raw = await(request("/api/accounts/auth/login/", "POST", body, true))
            val parsed = UTSAndroid.consoleDebugError(JSON.parseObject<LoginResponse>(JSON.stringify(raw)), " at pkg/api/modules/login.uts:57")
            if (parsed == null) {
                throw UTSError("登录响应解析失败")
            }
            return@w parsed!!
    })
}
fun getProfile(): UTSPromise<ProfileResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/accounts/auth/me/", "GET", UTSJSONObject(), false))
            val parsed = UTSAndroid.consoleDebugError(JSON.parseObject<ProfileResponse>(JSON.stringify(raw)), " at pkg/api/modules/login.uts:98")
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
val GenPagesTabbarReportsClass = CreateVueComponent(GenPagesTabbarReports::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesTabbarReports.inheritAttrs, inject = GenPagesTabbarReports.inject, props = GenPagesTabbarReports.props, propsNeedCastKeys = GenPagesTabbarReports.propsNeedCastKeys, emits = GenPagesTabbarReports.emits, components = GenPagesTabbarReports.components, styles = GenPagesTabbarReports.styles)
}
, fun(instance, renderer): GenPagesTabbarReports {
    return GenPagesTabbarReports(instance, renderer)
}
)
val GenPagesTabbarProductsClass = CreateVueComponent(GenPagesTabbarProducts::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesTabbarProducts.inheritAttrs, inject = GenPagesTabbarProducts.inject, props = GenPagesTabbarProducts.props, propsNeedCastKeys = GenPagesTabbarProducts.propsNeedCastKeys, emits = GenPagesTabbarProducts.emits, components = GenPagesTabbarProducts.components, styles = GenPagesTabbarProducts.styles)
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
    open var path: String? = null,
    @JsonNotNull
    open var action: String,
    @JsonNotNull
    open var disabled: Boolean = false,
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("MenuItem", "pages/tabbar/settings.uvue", 34, 6)
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
    constructor(__v_raw: MenuItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(label = __v_raw.label, icon = __v_raw.icon, path = __v_raw.path, action = __v_raw.action, disabled = __v_raw.disabled) {
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
) : UTSReactiveObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("MenuGroup", "pages/tabbar/settings.uvue", 42, 6)
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
        return UTSSourceMapPosition("SupplierMediaFile", "pkg/api/modules/suppliers.uts", 7, 13)
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
        return UTSSourceMapPosition("SupplierItem", "pkg/api/modules/suppliers.uts", 25, 13)
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
        return UTSSourceMapPosition("SupplierListResponse", "pkg/api/modules/suppliers.uts", 44, 13)
    }
}
fun buildListQuery(data: SupplierListQuery): UTSJSONObject {
    val query: UTSJSONObject = object : UTSJSONObject(UTSSourceMapPosition("query", "pkg/api/modules/suppliers.uts", 53, 11)) {
        var page = data.page
        var page_size = data.page_size
    }
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
fun normalizeSupplierList(data: SupplierListResponse): SupplierListResponse {
    run {
        var supplierIndex: Number = 0
        while(supplierIndex < data.results.length){
            val supplier = data.results[supplierIndex]
            run {
                var mediaIndex: Number = 0
                while(mediaIndex < supplier.media_files.length){
                    val mediaFile = supplier.media_files[mediaIndex]
                    mediaFile.file_url = normalizeServerUrl(mediaFile.file_url)
                    mediaFile.thumbnail_url = normalizeServerUrl(mediaFile.thumbnail_url)
                    mediaFile.signed_url = normalizeServerUrl(mediaFile.signed_url)
                    mediaFile.signed_thumbnail_url = normalizeServerUrl(mediaFile.signed_thumbnail_url)
                    mediaIndex += 1
                }
            }
            supplierIndex += 1
        }
    }
    return data
}
fun intValue(value: Any?): Number {
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
fun buildSupplierListResponse(raw: Any, query: SupplierListQuery): SupplierListResponse {
    val rawText = JSON.stringify(raw)
    val rawObject = if (rawText == null || rawText == "") {
        null
    } else {
        UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(rawText), " at pkg/api/modules/suppliers.uts:109")
    }
    if (rawObject == null) {
        throw UTSError("供应商列表响应解析失败")
    }
    var paginationObject: UTSJSONObject? = null
    val rawPagination = rawObject!!["pagination"]
    if (rawPagination != null) {
        val paginationText = JSON.stringify(rawPagination)
        if (paginationText != null && paginationText != "") {
            paginationObject = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(paginationText), " at pkg/api/modules/suppliers.uts:118")
        }
    }
    var results: UTSArray<SupplierItem> = _uA()
    val rawResults = rawObject!!["results"]
    if (rawResults != null) {
        val resultText = JSON.stringify(rawResults)
        val parsedResults = if (resultText == null || resultText == "") {
            null
        } else {
            UTSAndroid.consoleDebugError(JSON.parseArray<SupplierItem>(resultText), " at pkg/api/modules/suppliers.uts:125")
        }
        if (parsedResults != null) {
            results = parsedResults!!
        }
    }
    var totalCount = intValue(rawObject!!["count"])
    if (totalCount <= 0) {
        totalCount = intValue(rawObject!!["total"])
    }
    if (totalCount <= 0) {
        totalCount = intValue(rawObject!!["total_count"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue(paginationObject["total"])
    }
    if (totalCount <= 0 && paginationObject != null) {
        totalCount = intValue(paginationObject["count"])
    }
    if (totalCount <= 0) {
        totalCount = results.length
    }
    var currentPage = intValue(rawObject!!["page"])
    if (currentPage <= 0) {
        currentPage = intValue(rawObject!!["current_page"])
    }
    if (currentPage <= 0 && paginationObject != null) {
        currentPage = intValue(paginationObject["page"])
    }
    if (currentPage <= 0) {
        currentPage = query.page
    }
    var pageSize = intValue(rawObject!!["page_size"])
    if (pageSize <= 0) {
        pageSize = intValue(rawObject!!["per_page"])
    }
    if (pageSize <= 0 && paginationObject != null) {
        pageSize = intValue(paginationObject["page_size"])
    }
    if (pageSize <= 0 && paginationObject != null) {
        pageSize = intValue(paginationObject["per_page"])
    }
    if (pageSize <= 0) {
        pageSize = query.page_size
    }
    var totalPages = intValue(rawObject!!["total_pages"])
    if (totalPages <= 0) {
        totalPages = intValue(rawObject!!["num_pages"])
    }
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue(paginationObject["total_pages"])
    }
    if (totalPages <= 0 && paginationObject != null) {
        totalPages = intValue(paginationObject["num_pages"])
    }
    if (totalPages <= 0 && pageSize > 0) {
        totalPages = Math.ceil(totalCount / pageSize)
    }
    if (totalPages <= 0) {
        totalPages = 1
    }
    return SupplierListResponse(results = results, count = totalCount, total_count = totalCount, total_pages = totalPages, current_page = currentPage, page_size = pageSize)
}
fun getSupplierList(data: SupplierListQuery): UTSPromise<SupplierListResponse> {
    return wrapUTSPromise(suspend w@{
            val raw = await(request("/api/procurement/suppliers/", "GET", buildListQuery(data), true))
            return@w normalizeSupplierList(buildSupplierListResponse(raw, data))
    })
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
    return VueComponentOptions(type = "component", name = "", inheritAttrs = GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalList.inheritAttrs, inject = GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalList.inject, props = GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalList.props, propsNeedCastKeys = GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalList.propsNeedCastKeys, emits = GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalList.emits, components = GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalList.components, styles = GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalList.styles, setup = fun(props: ComponentPublicInstance): Any? {
        return GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalList.setup(props as GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalList)
    }
    )
}
, fun(instance, renderer): GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalList {
    return GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalList(instance)
}
)
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
val GenPagesSuppliersFromClass = CreateVueComponent(GenPagesSuppliersFrom::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesSuppliersFrom.inheritAttrs, inject = GenPagesSuppliersFrom.inject, props = GenPagesSuppliersFrom.props, propsNeedCastKeys = GenPagesSuppliersFrom.propsNeedCastKeys, emits = GenPagesSuppliersFrom.emits, components = GenPagesSuppliersFrom.components, styles = GenPagesSuppliersFrom.styles)
}
, fun(instance, renderer): GenPagesSuppliersFrom {
    return GenPagesSuppliersFrom(instance, renderer)
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
    override var uniCompilerVersion: String = "4.87"
    constructor() : super() {}
}
fun definePageRoutes() {
    __uniRoutes.push(UniPageRoute(path = "pages/login/login", component = GenPagesLoginLoginClass, meta = UniPageMeta(isQuit = true), style = _uM("navigationStyle" to "custom")))
    __uniRoutes.push(UniPageRoute(path = "pages/tabbar/reports", component = GenPagesTabbarReportsClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "报表")))
    __uniRoutes.push(UniPageRoute(path = "pages/tabbar/products", component = GenPagesTabbarProductsClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "商品")))
    __uniRoutes.push(UniPageRoute(path = "pages/tabbar/settings", component = GenPagesTabbarSettingsClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "设置")))
    __uniRoutes.push(UniPageRoute(path = "pages/tabbar/mine", component = GenPagesTabbarMineClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "我的")))
    __uniRoutes.push(UniPageRoute(path = "uni_modules/uni-upgrade-center-app/pages/uni-app-x/upgrade-popup", component = GenUniModulesUniUpgradeCenterAppPagesUniAppXUpgradePopupClass, meta = UniPageMeta(isQuit = false), style = _uM()))
    __uniRoutes.push(UniPageRoute(path = "pages/webview/webview", component = GenPagesWebviewWebviewClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/privacy/privacy", component = GenPagesPrivacyPrivacyClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/suppliers/index", component = GenPagesSuppliersIndexClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationStyle" to "custom", "navigationBarTitleText" to "")))
    __uniRoutes.push(UniPageRoute(path = "pages/suppliers/from", component = GenPagesSuppliersFromClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationBarTitleText" to "")))
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
