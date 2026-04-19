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
import io.dcloud.uniapp.extapi.showToast as uni_showToast
import io.dcloud.uniapp.extapi.switchTab as uni_switchTab
open class GenPagesLoginLogin : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesLoginLogin) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesLoginLogin
            val _cache = __ins.renderCache
            val username = ref("733063605")
            val password = ref("Luochaowei88")
            val showPassword = ref(false)
            val isSubmitting = ref(false)
            fun gen_togglePassword_fn() {
                showPassword.value = !showPassword.value
            }
            val togglePassword = ::gen_togglePassword_fn
            fun gen_buildDisplayName_fn(profile: ProfileResponse): String {
                if (profile.name != "") {
                    return profile.name
                }
                if (profile.first_name != "" || profile.last_name != "") {
                    return profile.first_name + profile.last_name
                }
                return profile.username
            }
            val buildDisplayName = ::gen_buildDisplayName_fn
            fun gen_handleLogin_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (isSubmitting.value) {
                            return@w1
                        }
                        if (username.value == "") {
                            uni_showToast(ShowToastOptions(title = "请输入账号", icon = "none"))
                            return@w1
                        }
                        if (password.value == "") {
                            uni_showToast(ShowToastOptions(title = "请输入密码", icon = "none"))
                            return@w1
                        }
                        isSubmitting.value = true
                        try {
                            val loginRes = await(accountLogin(LoginData(username = username.value, password = password.value)))
                            setAuthToken(loginRes.token_type + " " + loginRes.access_token)
                            val profile = await(getProfile())
                            setAuthUserInfo(UserInfoState(id = profile.id, name = buildDisplayName(profile), avatar = ""))
                            uni_switchTab(SwitchTabOptions(url = "/pages/tabbar/products"))
                        }
                         catch (error: Throwable) {
                            clearAuthState()
                            var message = "登录失败"
                            if (error != null) {
                                val errorText = JSON.stringify(error)
                                if (errorText != null && errorText != "") {
                                    val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/login/login.uvue:105")
                                    if (parsedError != null) {
                                        val rawMessage = parsedError!!["message"]
                                        if (rawMessage != null) {
                                            val parsedMessage = rawMessage as String
                                            if (parsedMessage != "") {
                                                message = parsedMessage
                                            }
                                        }
                                    }
                                    if (message == "登录失败") {
                                        message = errorText
                                    }
                                }
                            }
                            uni_showToast(ShowToastOptions(title = message, icon = "none"))
                        }
                         finally {
                            isSubmitting.value = false
                        }
                })
            }
            val handleLogin = ::gen_handleLogin_fn
            return fun(): Any? {
                return _cE("view", _uM("class" to "container"), _uA(
                    _cE("view", _uM("class" to "status-bar-space")),
                    _cE("view", _uM("class" to "login-card"), _uA(
                        _cE("view", _uM("class" to "logo-area"), _uA(
                            _cE("view", _uM("class" to "logo-circle"), _uA(
                                _cE("text", _uM("class" to "logo-text"), "Hi")
                            )),
                            _cE("text", _uM("class" to "app-name"), "欢迎回来"),
                            _cE("text", _uM("class" to "app-desc"), "请登录以继续")
                        )),
                        _cE("view", _uM("class" to "input-wrap"), _uA(
                            _cE("text", _uM("class" to "input-icon"), "👤"),
                            _cE("input", _uM("class" to "input", "type" to "text", "placeholder" to "请输入账号", "placeholder-style" to "color: #bbbbbb;", "modelValue" to unref(username), "onInput" to fun(`$event`: UniInputEvent){
                                trySetRefValue(username, `$event`.detail.value)
                            }
                            ), null, 40, _uA(
                                "modelValue"
                            ))
                        )),
                        _cE("view", _uM("class" to "input-wrap"), _uA(
                            _cE("text", _uM("class" to "input-icon"), "🔒"),
                            _cE("input", _uM("class" to "input", "type" to if (unref(showPassword)) {
                                "text"
                            } else {
                                "password"
                            }
                            , "placeholder" to "请输入密码", "placeholder-style" to "color: #bbbbbb;", "modelValue" to unref(password), "onInput" to fun(`$event`: UniInputEvent){
                                trySetRefValue(password, `$event`.detail.value)
                            }
                            ), null, 40, _uA(
                                "type",
                                "modelValue"
                            )),
                            _cE("text", _uM("class" to "eye-icon", "onClick" to togglePassword), _tD(if (unref(showPassword)) {
                                "👁️"
                            } else {
                                "👁️‍🗨️"
                            }
                            ), 1)
                        )),
                        _cE("button", _uM("class" to _nC(if (unref(isSubmitting)) {
                            "btn btn-primary btn-primary-disabled"
                        } else {
                            "btn btn-primary"
                        }
                        ), "disabled" to unref(isSubmitting), "onClick" to handleLogin), _uA(
                            _cE("text", _uM("class" to "btn-text"), _tD(if (unref(isSubmitting)) {
                                "登录中..."
                            } else {
                                "登录"
                            }
                            ), 1)
                        ), 10, _uA(
                            "disabled"
                        ))
                    ))
                ))
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("container" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#f5f7fa", "justifyContent" to "center", "alignItems" to "center", "paddingTop" to 15, "paddingRight" to 15, "paddingBottom" to 15, "paddingLeft" to 15)), "status-bar-space" to _pS(_uM("height" to CSS_VAR_STATUS_BAR_HEIGHT, "width" to "100%")), "login-card" to _pS(_uM("width" to "100%", "backgroundColor" to "#ffffff", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 30, "paddingRight" to 20, "paddingBottom" to 30, "paddingLeft" to 20)), "logo-area" to _pS(_uM("alignItems" to "center", "marginBottom" to 25)), "logo-circle" to _pS(_uM("width" to 60, "height" to 60, "backgroundColor" to "#07c160", "borderTopLeftRadius" to 30, "borderTopRightRadius" to 30, "borderBottomRightRadius" to 30, "borderBottomLeftRadius" to 30, "justifyContent" to "center", "alignItems" to "center", "marginBottom" to 12)), "logo-text" to _pS(_uM("fontSize" to 20, "color" to "#ffffff", "fontWeight" to "bold")), "app-name" to _pS(_uM("fontSize" to 20, "color" to "#333333", "fontWeight" to "bold", "marginBottom" to 5)), "app-desc" to _pS(_uM("fontSize" to 13, "color" to "#999999")), "input-wrap" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "backgroundColor" to "#f8f9fa", "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "paddingTop" to 0, "paddingRight" to 15, "paddingBottom" to 0, "paddingLeft" to 15, "marginBottom" to 10, "height" to 45)), "input-icon" to _pS(_uM("fontSize" to 14, "marginRight" to 8)), "input" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "fontSize" to 14, "color" to "#333333", "height" to 45)), "eye-icon" to _pS(_uM("fontSize" to 14, "paddingTop" to 5, "paddingRight" to 5, "paddingBottom" to 5, "paddingLeft" to 5)), "btn" to _pS(_uM("width" to "100%", "height" to 45, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "marginBottom" to 10)), "btn-primary" to _pS(_uM("backgroundColor" to "#2b85e4", "marginTop" to 5)), "btn-primary-disabled" to _pS(_uM("opacity" to 0.7)), "btn-text" to _pS(_uM("fontSize" to 15, "color" to "#ffffff", "fontWeight" to "400")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
