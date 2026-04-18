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
import io.dcloud.uniapp.extapi.getWindowInfo as uni_getWindowInfo
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.reLaunch as uni_reLaunch
open class GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilter : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var title: String by `$props`
    open var searchPlaceholder: String by `$props`
    open var searchValue: String by `$props`
    open var showBack: Boolean by `$props`
    open var showSearch: Boolean by `$props`
    open var showFilter: Boolean by `$props`
    open var showHome: Boolean by `$props`
    open var showMenu: Boolean by `$props`
    open var showScan: Boolean by `$props`
    open var filterActive: Boolean by `$props`
    open var filterText: String by `$props`
    open var fixed: Boolean by `$props`
    open var backgroundColor: String by `$props`
    open var homePath: String by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilter) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilter
            val _cache = __ins.renderCache
            val props = __props
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val statusBarHeight = ref<Number>(0)
            val inputValue = ref<String>(props.searchValue)
            val rootStyle = ref<String>("")
            fun gen_syncRootStyle_fn() {
                val positionStyle = if (props.fixed) {
                    "position:sticky;top:0;"
                } else {
                    ""
                }
                rootStyle.value = "padding-top:" + statusBarHeight.value + "px;background-color:" + props.backgroundColor + ";" + positionStyle
            }
            val syncRootStyle = ::gen_syncRootStyle_fn
            fun gen_updateStatusBarHeight_fn() {
                val info = uni_getWindowInfo()
                statusBarHeight.value = info.statusBarHeight
                syncRootStyle()
            }
            val updateStatusBarHeight = ::gen_updateStatusBarHeight_fn
            fun gen_eventValueToString_fn(e: UTSJSONObject): String {
                val detail = e["detail"]
                if (detail == null) {
                    return ""
                }
                val detailObj = detail as UTSJSONObject
                val value = detailObj["value"]
                if (value == null) {
                    return ""
                }
                return "" + value
            }
            val eventValueToString = ::gen_eventValueToString_fn
            fun gen_onSearchInput_fn(e: UTSJSONObject) {
                emit("searchInput", if (inputValue.value != "") {
                    inputValue.value
                } else {
                    eventValueToString(e)
                }
                )
            }
            val onSearchInput = ::gen_onSearchInput_fn
            fun gen_onSearchConfirm_fn(e: UTSJSONObject) {
                emit("searchConfirm", if (inputValue.value != "") {
                    inputValue.value
                } else {
                    eventValueToString(e)
                }
                )
            }
            val onSearchConfirm = ::gen_onSearchConfirm_fn
            fun gen_clearSearch_fn() {
                inputValue.value = ""
                emit("searchClear")
            }
            val clearSearch = ::gen_clearSearch_fn
            fun gen_handleBack_fn() {
                emit("back")
                uni_navigateBack(NavigateBackOptions(delta = 1, fail = fun(_){
                    uni_reLaunch(ReLaunchOptions(url = props.homePath))
                }
                ))
            }
            val handleBack = ::gen_handleBack_fn
            fun gen_handleHome_fn() {
                emit("home")
                uni_reLaunch(ReLaunchOptions(url = props.homePath))
            }
            val handleHome = ::gen_handleHome_fn
            fun gen_handleFilter_fn() {
                emit("filter")
            }
            val handleFilter = ::gen_handleFilter_fn
            fun gen_handleMenu_fn() {
                emit("menu")
            }
            val handleMenu = ::gen_handleMenu_fn
            fun gen_handleScan_fn() {
                emit("scan")
            }
            val handleScan = ::gen_handleScan_fn
            watch(fun(): String {
                return props.searchValue
            }
            , fun(newVal: String){
                if (newVal != inputValue.value) {
                    inputValue.value = newVal
                }
            }
            )
            watch(fun(): String {
                return props.backgroundColor
            }
            , fun(){
                syncRootStyle()
            }
            )
            watch(fun(): Boolean {
                return props.fixed
            }
            , fun(){
                syncRootStyle()
            }
            )
            onMounted(fun(){
                updateStatusBarHeight()
            }
            )
            return fun(): Any? {
                return _cE("view", _uM("class" to "uf-root", "style" to _nS(unref(rootStyle))), _uA(
                    _cE("view", _uM("class" to "uf-bar"), _uA(
                        if (isTrue(_ctx.showBack || _ctx.showHome)) {
                            _cE("view", _uM("key" to 0, "class" to "uf-segment-group uf-segment-group-left"), _uA(
                                if (isTrue(_ctx.showBack)) {
                                    _cE("view", _uM("key" to 0, "class" to _nC(if (_ctx.showBack && _ctx.showHome) {
                                        "uf-segment uf-segment-split"
                                    } else {
                                        "uf-segment"
                                    }), "onClick" to handleBack), _uA(
                                        _cE("text", _uM("class" to "uf-segment-icon"), "←")
                                    ), 2)
                                } else {
                                    _cC("v-if", true)
                                },
                                if (isTrue(_ctx.showHome)) {
                                    _cE("view", _uM("key" to 1, "class" to "uf-segment", "onClick" to handleHome), _uA(
                                        _cE("text", _uM("class" to "uf-segment-icon"), "⌂")
                                    ))
                                } else {
                                    _cC("v-if", true)
                                }
                            ))
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        if (isTrue(_ctx.showSearch)) {
                            _cE("view", _uM("key" to 1, "class" to "uf-search-wrap"), _uA(
                                _cE("view", _uM("class" to "uf-search-box"), _uA(
                                    if (isTrue(_ctx.showScan)) {
                                        _cE("view", _uM("key" to 0, "class" to "uf-scan-btn", "onClick" to handleScan), _uA(
                                            _cE("text", _uM("class" to "uf-scan-icon"), "▣")
                                        ))
                                    } else {
                                        _cC("v-if", true)
                                    },
                                    _cE("input", _uM("class" to "uf-search-input", "modelValue" to unref(inputValue), "onInput" to _uA<Any?>(fun(`$event`: UniInputEvent){
                                        trySetRefValue(inputValue, `$event`.detail.value)
                                    }, onSearchInput), "placeholder" to _ctx.searchPlaceholder, "onConfirm" to onSearchConfirm, "confirm-type" to "search"), null, 40, _uA(
                                        "modelValue",
                                        "placeholder"
                                    )),
                                    if (unref(inputValue) != "") {
                                        _cE("view", _uM("key" to 1, "class" to "uf-clear-btn", "onClick" to clearSearch), _uA(
                                            _cE("text", _uM("class" to "uf-clear-icon"), "×")
                                        ))
                                    } else {
                                        _cC("v-if", true)
                                    }
                                ))
                            ))
                        } else {
                            _cE("view", _uM("key" to 2, "class" to "uf-title-wrap"), _uA(
                                _cE("text", _uM("class" to "uf-title"), _tD(_ctx.title), 1)
                            ))
                        }
                        ,
                        if (isTrue(_ctx.showFilter || _ctx.showMenu)) {
                            _cE("view", _uM("key" to 3, "class" to "uf-segment-group uf-segment-group-right"), _uA(
                                if (isTrue(_ctx.showFilter)) {
                                    _cE("view", _uM("key" to 0, "class" to _nC(if (_ctx.showFilter && _ctx.showMenu) {
                                        "uf-segment uf-segment-split uf-segment-dark"
                                    } else {
                                        "uf-segment uf-segment-dark"
                                    }), "onClick" to handleFilter), _uA(
                                        _cE("text", _uM("class" to "uf-segment-text-light"), _tD(_ctx.filterText), 1),
                                        if (isTrue(_ctx.filterActive)) {
                                            _cE("view", _uM("key" to 0, "class" to "uf-dot"))
                                        } else {
                                            _cC("v-if", true)
                                        }
                                    ), 2)
                                } else {
                                    _cC("v-if", true)
                                },
                                if (isTrue(_ctx.showMenu)) {
                                    _cE("view", _uM("key" to 1, "class" to "uf-segment uf-segment-dark", "onClick" to handleMenu), _uA(
                                        _cE("text", _uM("class" to "uf-segment-icon-light"), "≡")
                                    ))
                                } else {
                                    _cC("v-if", true)
                                }
                            ))
                        } else {
                            _cC("v-if", true)
                        }
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
                return _uM("uf-root" to _pS(_uM("zIndex" to 90, "backgroundColor" to "#FFFFFF", "borderBottomWidth" to 1, "borderBottomColor" to "#EEF1F5", "borderBottomStyle" to "solid")), "uf-bar" to _pS(_uM("height" to 56, "flexDirection" to "row", "alignItems" to "center", "paddingLeft" to 12, "paddingRight" to 12)), "uf-segment-group" to _pS(_uM("height" to 34, "flexDirection" to "row", "alignItems" to "center", "borderTopLeftRadius" to 17, "borderTopRightRadius" to 17, "borderBottomRightRadius" to 17, "borderBottomLeftRadius" to 17, "backgroundColor" to "#FFFFFF", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "overflow" to "hidden")), "uf-segment-group-left" to _pS(_uM("marginRight" to 8)), "uf-segment-group-right" to _pS(_uM("marginLeft" to 8)), "uf-segment" to _pS(_uM("minWidth" to 34, "height" to 34, "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "paddingLeft" to 10, "paddingRight" to 10)), "uf-segment-split" to _pS(_uM("borderRightWidth" to 1, "borderRightColor" to "rgba(148,163,184,0.32)", "borderRightStyle" to "solid")), "uf-segment-dark" to _pS(_uM("backgroundColor" to "#0F172A")), "uf-segment-icon" to _pS(_uM("fontSize" to 15, "color" to "#1F2937", "lineHeight" to "15px")), "uf-segment-icon-light" to _pS(_uM("fontSize" to 15, "color" to "#FFFFFF", "lineHeight" to "15px")), "uf-search-wrap" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "uf-search-box" to _pS(_uM("height" to 40, "flexDirection" to "row", "alignItems" to "center", "borderTopLeftRadius" to 20, "borderTopRightRadius" to 20, "borderBottomRightRadius" to 20, "borderBottomLeftRadius" to 20, "backgroundColor" to "#FFFFFF", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "paddingLeft" to 12, "paddingRight" to 10)), "uf-scan-btn" to _pS(_uM("width" to 24, "height" to 24, "alignItems" to "center", "justifyContent" to "center", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#E2E8F0", "marginRight" to 8)), "uf-scan-icon" to _pS(_uM("fontSize" to 12, "color" to "#334155", "lineHeight" to "12px")), "uf-search-input" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 40, "fontSize" to 14, "color" to "#1F2937", "paddingRight" to 6)), "uf-clear-btn" to _pS(_uM("width" to 24, "height" to 24, "flexShrink" to 0, "alignItems" to "center", "justifyContent" to "center", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#D8DEE6")), "uf-clear-icon" to _pS(_uM("fontSize" to 14, "color" to "#536171", "lineHeight" to "14px")), "uf-title-wrap" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "justifyContent" to "center")), "uf-title" to _pS(_uM("fontSize" to 17, "fontWeight" to "bold", "color" to "#111827")), "uf-segment-text-light" to _pS(_uM("fontSize" to 12, "color" to "#FFFFFF")), "uf-dot" to _pS(_uM("width" to 6, "height" to 6, "borderTopLeftRadius" to 3, "borderTopRightRadius" to 3, "borderBottomRightRadius" to 3, "borderBottomLeftRadius" to 3, "backgroundColor" to "#FB7185", "marginLeft" to 5)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("back" to null, "home" to null, "menu" to null, "filter" to null, "scan" to null, "searchInput" to null, "searchConfirm" to null, "searchClear" to null)
        var props = _nP(_uM("title" to _uM("type" to "String", "required" to false, "default" to ""), "searchPlaceholder" to _uM("type" to "String", "required" to false, "default" to "请输入搜索内容"), "searchValue" to _uM("type" to "String", "required" to false, "default" to ""), "showBack" to _uM("type" to "Boolean", "required" to false, "default" to false), "showSearch" to _uM("type" to "Boolean", "required" to false, "default" to true), "showFilter" to _uM("type" to "Boolean", "required" to false, "default" to false), "showHome" to _uM("type" to "Boolean", "required" to false, "default" to false), "showMenu" to _uM("type" to "Boolean", "required" to false, "default" to false), "showScan" to _uM("type" to "Boolean", "required" to false, "default" to false), "filterActive" to _uM("type" to "Boolean", "required" to false, "default" to false), "filterText" to _uM("type" to "String", "required" to false, "default" to "筛选"), "fixed" to _uM("type" to "Boolean", "required" to false, "default" to true), "backgroundColor" to _uM("type" to "String", "required" to false, "default" to "#FFFFFF"), "homePath" to _uM("type" to "String", "required" to false, "default" to "/pages/tabbar/products")))
        var propsNeedCastKeys = _uA(
            "title",
            "searchPlaceholder",
            "searchValue",
            "showBack",
            "showSearch",
            "showFilter",
            "showHome",
            "showMenu",
            "showScan",
            "filterActive",
            "filterText",
            "fixed",
            "backgroundColor",
            "homePath"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
