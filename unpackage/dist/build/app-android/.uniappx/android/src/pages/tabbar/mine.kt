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
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesTabbarMine : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesTabbarMine) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesTabbarMine
            val _cache = __ins.renderCache
            val menus = ref(_uA<MenuItem__1>(MenuItem__1(id = 1, name = "账本管理", icon = "☰", key = "book", arrow = true), MenuItem__1(id = 2, name = "联系客服", icon = "✆", key = "service", arrow = true), MenuItem__1(id = 3, name = "分享应用", icon = "⇪", key = "share", arrow = false), MenuItem__1(id = 4, name = "添加桌面", icon = "⊞", key = "desktop", arrow = false), MenuItem__1(id = 5, name = "退出登录", icon = "⎋", key = "logout", arrow = false, tone = "danger")))
            val isLogin = computed(fun(): Boolean {
                return authState.token != ""
            }
            )
            val displayName = computed(fun(): String {
                if (authState.userInfo != null && authState.userInfo!!.name != "") {
                    return authState.userInfo!!.name
                }
                return "易师傅"
            }
            )
            val avatarText = computed(fun(): String {
                val name = displayName.value
                if (name == "") {
                    return "易"
                }
                return name.substring(0, 1)
            }
            )
            val useDaysText = computed(fun(): String {
                if (!isLogin.value) {
                    return "0"
                }
                return "12"
            }
            )
            val entryCountText = computed(fun(): String {
                if (!isLogin.value) {
                    return "0"
                }
                return "43"
            }
            )
            fun gen_handleLogout_fn() {
                if (!isLogin.value) {
                    uni_navigateTo(NavigateToOptions(url = "/pages/login/login"))
                    return
                }
                uni_showModal(ShowModalOptions(title = "提示", content = "确定退出登录吗？", success = fun(res){
                    if (!res.confirm) {
                        return
                    }
                    clearAuthState()
                    uni_showToast(ShowToastOptions(title = "已退出登录", icon = "success"))
                    setTimeout(fun(){
                        uni_navigateTo(NavigateToOptions(url = "/pages/login/login"))
                    }
                    , 300)
                }
                ))
            }
            val handleLogout = ::gen_handleLogout_fn
            fun gen_handleMenuClick_fn(item: MenuItem__1) {
                if (item.key == "logout") {
                    handleLogout()
                    return
                }
                if (item.key == "book") {
                    uni_showToast(ShowToastOptions(title = "账本管理待接入", icon = "none"))
                    return
                }
                if (item.key == "service") {
                    uni_showToast(ShowToastOptions(title = "联系客服待接入", icon = "none"))
                    return
                }
                if (item.key == "share") {
                    uni_showToast(ShowToastOptions(title = "分享应用待接入", icon = "none"))
                    return
                }
                if (item.key == "desktop") {
                    uni_showToast(ShowToastOptions(title = "添加桌面待接入", icon = "none"))
                }
            }
            val handleMenuClick = ::gen_handleMenuClick_fn
            return fun(): Any? {
                return _cE("scroll-view", _uM("class" to "page-scroll", "style" to _nS(_uM("flex" to "1")), "direction" to "vertical"), _uA(
                    _cE("view", _uM("class" to "page"), _uA(
                        _cE("view", _uM("class" to "status-bar-space")),
                        _cE("view", _uM("class" to "header"), _uA(
                            _cE("view", _uM("class" to "user"), _uA(
                                _cE("view", _uM("class" to "avatar"), _uA(
                                    _cE("text", _uM("class" to "avatar-text"), _tD(avatarText.value), 1)
                                )),
                                _cE("text", _uM("class" to "user-name"), _tD(displayName.value), 1)
                            )),
                            _cE("view", _uM("class" to "info"), _uA(
                                _cE("view", _uM("class" to "info-item info-item-gap"), _uA(
                                    _cE("text", _uM("class" to "info-value"), _tD(useDaysText.value), 1),
                                    _cE("text", _uM("class" to "info-label"), "使用天数")
                                )),
                                _cE("view", _uM("class" to "info-item"), _uA(
                                    _cE("text", _uM("class" to "info-value"), _tD(entryCountText.value), 1),
                                    _cE("text", _uM("class" to "info-label"), "记账笔数")
                                ))
                            ))
                        )),
                        _cE("view", _uM("class" to "menu"), _uA(
                            _cE(Fragment, null, RenderHelpers.renderList(unref(menus), fun(item, index, __index, _cached): Any {
                                return _cE("view", _uM("key" to item.id, "class" to _nC(if (index < unref(menus).length - 1) {
                                    "menu-item menu-item-border"
                                } else {
                                    "menu-item"
                                }
                                ), "onClick" to fun(){
                                    handleMenuClick(item)
                                }
                                ), _uA(
                                    _cE("view", _uM("class" to "menu-icon-wrap"), _uA(
                                        _cE("text", _uM("class" to _nC(if (item.tone == "danger") {
                                            "menu-icon menu-icon-danger"
                                        } else {
                                            "menu-icon"
                                        }
                                        )), _tD(item.icon), 3)
                                    )),
                                    _cE("text", _uM("class" to _nC(if (item.tone == "danger") {
                                        "menu-text menu-text-danger"
                                    } else {
                                        "menu-text"
                                    }
                                    )), _tD(item.name), 3),
                                    if (isTrue(item.arrow)) {
                                        _cE("text", _uM("key" to 0, "class" to "menu-arrow"), "›")
                                    } else {
                                        _cC("v-if", true)
                                    }
                                ), 10, _uA(
                                    "onClick"
                                ))
                            }
                            ), 128)
                        ))
                    ))
                ), 4)
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("page-scroll" to _pS(_uM("backgroundColor" to "#F5F5F5")), "page" to _pS(_uM("backgroundColor" to "#F5F5F5")), "status-bar-space" to _pS(_uM("height" to CSS_VAR_STATUS_BAR_HEIGHT, "backgroundColor" to "#3C9BFD")), "header" to _pS(_uM("backgroundColor" to "#3C9BFD", "paddingBottom" to 30)), "user" to _pS(_uM("alignItems" to "center", "paddingTop" to 12)), "avatar" to _pS(_uM("width" to 80, "height" to 80, "borderTopLeftRadius" to 40, "borderTopRightRadius" to 40, "borderBottomRightRadius" to 40, "borderBottomLeftRadius" to 40, "backgroundColor" to "rgba(255,255,255,0.3)", "alignItems" to "center", "justifyContent" to "center")), "avatar-text" to _pS(_uM("fontSize" to 28, "lineHeight" to "28px", "color" to "#FFFFFF", "fontWeight" to "bold")), "user-name" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 14, "lineHeight" to "20px", "marginTop" to 10)), "info" to _pS(_uM("flexDirection" to "row", "marginTop" to 30)), "info-item" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "alignItems" to "center")), "info-item-gap" to _pS(_uM("borderRightWidth" to 1, "borderRightStyle" to "solid", "borderRightColor" to "rgba(255,255,255,0.18)")), "info-value" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 24, "lineHeight" to "28px", "fontWeight" to "bold")), "info-label" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 12, "lineHeight" to "18px", "marginTop" to 8)), "menu" to _pS(_uM("backgroundColor" to "#FFFFFF", "marginTop" to 12)), "menu-item" to _pS(_uM("height" to 56, "flexDirection" to "row", "alignItems" to "center", "paddingLeft" to 16, "paddingRight" to 16)), "menu-item-border" to _pS(_uM("borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#F0F0F0")), "menu-icon-wrap" to _pS(_uM("width" to 28, "alignItems" to "center", "justifyContent" to "center")), "menu-icon" to _pS(_uM("fontSize" to 18, "lineHeight" to "18px", "color" to "#3C9BFD")), "menu-icon-danger" to _pS(_uM("color" to "#F04438")), "menu-text" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "fontSize" to 14, "lineHeight" to "20px", "color" to "#444444", "paddingLeft" to 10)), "menu-text-danger" to _pS(_uM("color" to "#F04438")), "menu-arrow" to _pS(_uM("fontSize" to 18, "lineHeight" to "18px", "color" to "#BDBDBD")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
