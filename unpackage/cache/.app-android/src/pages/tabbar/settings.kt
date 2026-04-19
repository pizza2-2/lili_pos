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
import io.dcloud.uniapp.extapi.showToast as uni_showToast
import io.dcloud.uniapp.extapi.switchTab as uni_switchTab
open class GenPagesTabbarSettings : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesTabbarSettings) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesTabbarSettings
            val _cache = __ins.renderCache
            val groups = ref(_uA<MenuGroup>(MenuGroup(label = "常用页面", rows = _uA(
                _uA(
                    MenuItem(label = "供应商", icon = "◫", path = "/pages/suppliers/index", action = "navigateTo", disabled = false),
                    MenuItem(label = "商品", icon = "□", path = "/pages/tabbar/products", action = "switchTab", disabled = false),
                    MenuItem(label = "我的", icon = "◉", path = "/pages/tabbar/mine", action = "switchTab", disabled = false),
                    MenuItem(label = "隐私", icon = "◎", path = "/pages/privacy/privacy", action = "navigateTo", disabled = false)
                )
            )), MenuGroup(label = "门店配置", rows = _uA(
                _uA(
                    MenuItem(label = "收银台", icon = "¤", path = null, action = "todo", disabled = true),
                    MenuItem(label = "打印机", icon = "▣", path = null, action = "todo", disabled = true),
                    MenuItem(label = "支付", icon = "¥", path = null, action = "todo", disabled = true),
                    MenuItem(label = "权限", icon = "⌂", path = null, action = "todo", disabled = true)
                )
            )), MenuGroup(label = "系统工具", rows = _uA(
                _uA(
                    MenuItem(label = "同步", icon = "↻", path = null, action = "todo", disabled = true),
                    MenuItem(label = "更新", icon = "↑", path = null, action = "todo", disabled = true),
                    MenuItem(label = "日志", icon = "≡", path = null, action = "todo", disabled = true),
                    MenuItem(label = "分享", icon = "⇪", path = null, action = "todo", disabled = true)
                )
            ))))
            fun gen_handleTap_fn(item: MenuItem) {
                if (item.disabled) {
                    uni_showToast(ShowToastOptions(title = "该功能正在开发中", icon = "none"))
                    return
                }
                if (item.action == "switchTab" && item.path != null) {
                    uni_switchTab(SwitchTabOptions(url = item.path!!))
                    return
                }
                if (item.action == "navigateTo" && item.path != null) {
                    uni_navigateTo(NavigateToOptions(url = item.path!!))
                }
            }
            val handleTap = ::gen_handleTap_fn
            fun gen_showSizeTip_fn() {
                uni_showToast(ShowToastOptions(title = "字号设置待接入", icon = "none"))
            }
            val showSizeTip = ::gen_showSizeTip_fn
            fun gen_showLocaleTip_fn() {
                uni_showToast(ShowToastOptions(title = "语言设置待接入", icon = "none"))
            }
            val showLocaleTip = ::gen_showLocaleTip_fn
            fun gen_getGroupCount_fn(group: MenuGroup): Number {
                var count: Number = 0
                run {
                    var rowIndex: Number = 0
                    while(rowIndex < group.rows.length){
                        count += group.rows[rowIndex].length
                        rowIndex += 1
                    }
                }
                return count
            }
            val getGroupCount = ::gen_getGroupCount_fn
            return fun(): Any? {
                return _cE("scroll-view", _uM("class" to "page-scroll", "style" to _nS(_uM("flex" to "1")), "direction" to "vertical"), _uA(
                    _cE("view", _uM("class" to "page"), _uA(
                        _cE("view", _uM("class" to "status-bar-space")),
                        _cE("view", _uM("class" to "topbar"), _uA(
                            _cE("text", _uM("class" to "page-title"), "功能菜单")
                        )),
                        _cE("view", _uM("class" to "content"), _uA(
                            _cE(Fragment, null, RenderHelpers.renderList(unref(groups), fun(group, __key, __index, _cached): Any {
                                return _cE("view", _uM("key" to group.label, "class" to "group"), _uA(
                                    _cE("text", _uM("class" to "group-label"), _tD(group.label) + "（" + _tD(getGroupCount(group)) + "）", 1),
                                    _cE(Fragment, null, RenderHelpers.renderList(group.rows, fun(row, __key, __index, _cached): Any {
                                        return _cE("view", _uM("key" to row[0].label, "class" to "grid-row"), _uA(
                                            _cE(Fragment, null, RenderHelpers.renderList(row, fun(item, index, __index, _cached): Any {
                                                return _cE("view", _uM("key" to item.label, "class" to _nC(if (index != 3) {
                                                    "grid-item grid-item-gap"
                                                } else {
                                                    "grid-item"
                                                }
                                                ), "onClick" to fun(){
                                                    handleTap(item)
                                                }
                                                ), _uA(
                                                    _cE("text", _uM("class" to "grid-icon"), _tD(item.icon), 1),
                                                    _cE("text", _uM("class" to "grid-text"), _tD(item.label), 1)
                                                ), 10, _uA(
                                                    "onClick"
                                                ))
                                            }
                                            ), 128)
                                        ))
                                    }
                                    ), 128)
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
                return _uM("page-scroll" to _pS(_uM("backgroundColor" to "#F7F7F7")), "page" to _pS(_uM("backgroundColor" to "#F7F7F7", "paddingBottom" to 20)), "status-bar-space" to _pS(_uM("height" to CSS_VAR_STATUS_BAR_HEIGHT)), "topbar" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "paddingLeft" to 12, "paddingRight" to 12, "paddingTop" to 12, "paddingBottom" to 12)), "brand-box" to _pS(_uM("width" to 32, "height" to 32, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#FFFFFF", "alignItems" to "center", "justifyContent" to "center")), "brand-text" to _pS(_uM("fontSize" to 16, "lineHeight" to "16px", "color" to "#111827", "fontWeight" to "bold")), "page-title" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "fontSize" to 16, "lineHeight" to "20px", "color" to "#111827", "fontWeight" to "bold", "marginLeft" to 8)), "action-button" to _pS(_uM("width" to 32, "height" to 32, "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "backgroundColor" to "#0F172A", "alignItems" to "center", "justifyContent" to "center")), "action-button-gap" to _pS(_uM("marginRight" to 12, "backgroundColor" to "#64748B")), "action-icon" to _pS(_uM("fontSize" to 14, "lineHeight" to "14px", "color" to "#FFFFFF")), "content" to _pS(_uM("paddingLeft" to 12, "paddingRight" to 12)), "group" to _pS(_uM("marginBottom" to 24)), "group-label" to _pS(_uM("fontSize" to 12, "lineHeight" to "18px", "color" to "#94A3B8", "marginLeft" to 8, "marginBottom" to 8)), "grid-row" to _pS(_uM("flexDirection" to "row", "marginBottom" to 8)), "grid-item" to _pS(_uM("width" to "23%", "height" to 70, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#FFFFFF", "alignItems" to "center", "paddingTop" to 18)), "grid-item-gap" to _pS(_uM("marginRight" to "2.6666%")), "grid-icon" to _pS(_uM("fontSize" to 18, "lineHeight" to "18px", "color" to "#111827")), "grid-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "16px", "color" to "#111827", "textAlign" to "center", "marginTop" to 8)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
