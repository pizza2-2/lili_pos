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
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesIndexIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesIndexIndex) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesIndexIndex
            val _cache = __ins.renderCache
            val navSearch = ref<String>("")
            val filterActive = ref<Boolean>(true)
            val navActionText = ref<String>("等待顶部栏交互")
            val basicPopupVisible = ref<Boolean>(false)
            val savePopupVisible = ref<Boolean>(false)
            fun gen_onNavSearchInput_fn(value: String) {
                navSearch.value = value
                navActionText.value = "输入中: " + value
            }
            val onNavSearchInput = ::gen_onNavSearchInput_fn
            fun gen_onNavSearchConfirm_fn(value: String) {
                navSearch.value = value
                navActionText.value = "确认搜索: " + value
                uni_showToast(ShowToastOptions(title = "搜索: " + value, icon = "none"))
            }
            val onNavSearchConfirm = ::gen_onNavSearchConfirm_fn
            fun gen_onNavSearchClear_fn() {
                navSearch.value = ""
                navActionText.value = "已清空搜索"
                uni_showToast(ShowToastOptions(title = "已清空搜索", icon = "none"))
            }
            val onNavSearchClear = ::gen_onNavSearchClear_fn
            fun gen_onNavFilter_fn() {
                filterActive.value = !filterActive.value
                navActionText.value = if (filterActive.value) {
                    "点击筛选: 当前有激活筛选"
                } else {
                    "点击筛选: 当前无激活筛选"
                }
                uni_showToast(ShowToastOptions(title = "筛选按钮已触发", icon = "none"))
            }
            val onNavFilter = ::gen_onNavFilter_fn
            fun gen_onNavMenu_fn() {
                navActionText.value = "点击菜单: 侧边栏待接入"
                uni_showToast(ShowToastOptions(title = "菜单按钮已触发", icon = "none"))
            }
            val onNavMenu = ::gen_onNavMenu_fn
            fun gen_onNavHome_fn() {
                navActionText.value = "点击首页: 已触发返回首页"
            }
            val onNavHome = ::gen_onNavHome_fn
            fun gen_onNavBack_fn() {
                navActionText.value = "点击返回: 已触发返回事件"
            }
            val onNavBack = ::gen_onNavBack_fn
            fun gen_onNavScan_fn() {
                navActionText.value = "点击扫码: 准备进入扫码页面"
                uni_showToast(ShowToastOptions(title = "扫码页面待接入", icon = "none"))
            }
            val onNavScan = ::gen_onNavScan_fn
            fun gen_toggleFilterActive_fn() {
                filterActive.value = !filterActive.value
                navActionText.value = if (filterActive.value) {
                    "手动设置: 筛选红点已打开"
                } else {
                    "手动设置: 筛选红点已关闭"
                }
            }
            val toggleFilterActive = ::gen_toggleFilterActive_fn
            fun gen_fillSearchDemo_fn() {
                navSearch.value = "测试订单A001"
                navActionText.value = "已填充测试搜索词"
            }
            val fillSearchDemo = ::gen_fillSearchDemo_fn
            fun gen_goToCryptoPage_fn() {
                uni_navigateTo(NavigateToOptions(url = "/pages/index/crypto"))
            }
            val goToCryptoPage = ::gen_goToCryptoPage_fn
            fun gen_goToUploadPage_fn() {
                uni_navigateTo(NavigateToOptions(url = "/pages/index/http"))
            }
            val goToUploadPage = ::gen_goToUploadPage_fn
            fun gen_openBasicPopup_fn() {
                basicPopupVisible.value = true
                navActionText.value = "打开基础确认弹窗"
            }
            val openBasicPopup = ::gen_openBasicPopup_fn
            fun gen_openSavePopup_fn() {
                savePopupVisible.value = true
                navActionText.value = "打开保存确认弹窗"
            }
            val openSavePopup = ::gen_openSavePopup_fn
            fun gen_onBasicPopupVisibleChange_fn(value: Boolean) {
                basicPopupVisible.value = value
            }
            val onBasicPopupVisibleChange = ::gen_onBasicPopupVisibleChange_fn
            fun gen_onSavePopupVisibleChange_fn(value: Boolean) {
                savePopupVisible.value = value
            }
            val onSavePopupVisibleChange = ::gen_onSavePopupVisibleChange_fn
            fun gen_onBasicPopupCancel_fn() {
                navActionText.value = "基础弹窗: 点击取消"
            }
            val onBasicPopupCancel = ::gen_onBasicPopupCancel_fn
            fun gen_onBasicPopupConfirm_fn() {
                navActionText.value = "基础弹窗: 点击确认删除"
            }
            val onBasicPopupConfirm = ::gen_onBasicPopupConfirm_fn
            fun gen_onBasicPopupClose_fn(action: String) {
                navActionText.value = "基础弹窗关闭: " + action
            }
            val onBasicPopupClose = ::gen_onBasicPopupClose_fn
            fun gen_onSavePopupAuxiliary_fn() {
                navActionText.value = "保存弹窗: 点击退出"
            }
            val onSavePopupAuxiliary = ::gen_onSavePopupAuxiliary_fn
            fun gen_onSavePopupCancel_fn() {
                navActionText.value = "保存弹窗: 点击取消保存"
            }
            val onSavePopupCancel = ::gen_onSavePopupCancel_fn
            fun gen_onSavePopupConfirm_fn() {
                navActionText.value = "保存弹窗: 点击保存"
            }
            val onSavePopupConfirm = ::gen_onSavePopupConfirm_fn
            fun gen_onSavePopupClose_fn(action: String) {
                navActionText.value = "保存弹窗关闭: " + action
            }
            val onSavePopupClose = ::gen_onSavePopupClose_fn
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_popup = resolveEasyComponent("lili-popup", GenUniModulesLiliPopupComponentsLiliPopupLiliPopupClass)
                return _cE("scroll-view", _uM("class" to "page-scroll", "style" to _nS(_uM("flex" to "1")), "direction" to "vertical"), _uA(
                    _cE("view", _uM("class" to "page"), _uA(
                        _cV(_component_lili_universal_filter, _uM("title" to "订单列表", "showBack" to true, "showHome" to true, "showSearch" to true, "showFilter" to true, "showMenu" to true, "showScan" to true, "filterActive" to filterActive.value, "searchValue" to navSearch.value, "searchPlaceholder" to "搜索订单/客户/手机号", "onSearchInput" to onNavSearchInput, "onSearchConfirm" to onNavSearchConfirm, "onSearchClear" to onNavSearchClear, "onFilter" to onNavFilter, "onMenu" to onNavMenu, "onHome" to onNavHome, "onBack" to onNavBack, "onScan" to onNavScan), null, 8, _uA(
                            "filterActive",
                            "searchValue"
                        )),
                        _cE("view", _uM("class" to "content"), _uA(
                            _cE("text", _uM("class" to "title"), "UniversalFilter 测试页"),
                            _cE("text", _uM("class" to "desc"), "这个页面用于验证返回、首页、搜索、筛选和菜单按钮是否正常触发。"),
                            _cE("view", _uM("class" to "card"), _uA(
                                _cE("text", _uM("class" to "card-label"), "当前搜索值"),
                                _cE("text", _uM("class" to "card-value"), _tD(if (navSearch.value == "") {
                                    "(空)"
                                } else {
                                    navSearch.value
                                }
                                ), 1)
                            )),
                            _cE("view", _uM("class" to "card"), _uA(
                                _cE("text", _uM("class" to "card-label"), "最近一次操作"),
                                _cE("text", _uM("class" to "card-value"), _tD(navActionText.value), 1)
                            )),
                            _cE("view", _uM("class" to "card"), _uA(
                                _cE("text", _uM("class" to "card-label"), "筛选红点状态"),
                                _cE("text", _uM("class" to "card-value"), _tD(if (filterActive.value) {
                                    "激活中"
                                } else {
                                    "未激活"
                                }
                                ), 1)
                            )),
                            _cE("view", _uM("class" to "btn-row"), _uA(
                                _cE("view", _uM("class" to "btn btn-primary", "onClick" to toggleFilterActive), _uA(
                                    _cE("text", _uM("class" to "btn-text"), "切换筛选红点")
                                )),
                                _cE("view", _uM("class" to "btn btn-secondary", "onClick" to fillSearchDemo), _uA(
                                    _cE("text", _uM("class" to "btn-text btn-text-dark"), "填充测试搜索词")
                                ))
                            )),
                            _cE("view", _uM("class" to "btn-row btn-row-single"), _uA(
                                _cE("view", _uM("class" to "btn btn-primary", "onClick" to goToCryptoPage), _uA(
                                    _cE("text", _uM("class" to "btn-text"), "跳转到 Crypto 页面")
                                ))
                            )),
                            _cE("view", _uM("class" to "btn-row btn-row-single"), _uA(
                                _cE("view", _uM("class" to "btn btn-primary", "onClick" to goToUploadPage), _uA(
                                    _cE("text", _uM("class" to "btn-text"), "跳转到 Upload 页面")
                                ))
                            )),
                            _cE("view", _uM("class" to "btn-row"), _uA(
                                _cE("view", _uM("class" to "btn btn-primary", "onClick" to openBasicPopup), _uA(
                                    _cE("text", _uM("class" to "btn-text"), "打开确认弹窗")
                                )),
                                _cE("view", _uM("class" to "btn btn-secondary", "onClick" to openSavePopup), _uA(
                                    _cE("text", _uM("class" to "btn-text btn-text-dark"), "打开保存弹窗")
                                ))
                            ))
                        )),
                        _cV(_component_lili_popup, _uM("visible" to basicPopupVisible.value, "title" to "删除提示", "content" to "确认删除这条测试数据吗？", "cancelText" to "取消", "confirmText" to "确认删除", "confirmDanger" to true, "onUpdate:visible" to onBasicPopupVisibleChange, "onCancel" to onBasicPopupCancel, "onConfirm" to onBasicPopupConfirm, "onClose" to onBasicPopupClose), null, 8, _uA(
                            "visible"
                        )),
                        _cV(_component_lili_popup, _uM("visible" to savePopupVisible.value, "title" to "是否保存", "content" to "当前内容已修改，是否保存后再退出？", "auxiliaryText" to "退出", "cancelText" to "取消保存", "confirmText" to "保存", "onUpdate:visible" to onSavePopupVisibleChange, "onAuxiliary" to onSavePopupAuxiliary, "onCancel" to onSavePopupCancel, "onConfirm" to onSavePopupConfirm, "onClose" to onSavePopupClose), null, 8, _uA(
                            "visible"
                        ))
                    ))
                ), 4)
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ), _uA(
                GenApp.styles
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("page-scroll" to _pS(_uM("backgroundColor" to "#F5F7FA")), "page" to _pS(_uM("backgroundColor" to "#F5F7FA")), "content" to _pS(_uM("paddingLeft" to 16, "paddingRight" to 16, "paddingTop" to 16, "paddingBottom" to 24)), "title" to _pS(_uM("fontSize" to 22, "fontWeight" to "bold", "color" to "#111827")), "desc" to _pS(_uM("fontSize" to 14, "color" to "#667085", "marginTop" to 8, "lineHeight" to "22px")), "card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "marginTop" to 14, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "#E7ECF2", "borderRightColor" to "#E7ECF2", "borderBottomColor" to "#E7ECF2", "borderLeftColor" to "#E7ECF2", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "card-label" to _pS(_uM("fontSize" to 13, "color" to "#94A3B8")), "card-value" to _pS(_uM("fontSize" to 16, "color" to "#0F172A", "marginTop" to 8, "lineHeight" to "22px")), "btn-row" to _pS(_uM("flexDirection" to "row", "marginTop" to 18)), "btn-row-single" to _pS(_uM("marginTop" to 10)), "btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 44, "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "alignItems" to "center", "justifyContent" to "center")), "btn-primary" to _pS(_uM("backgroundColor" to "#0F172A", "marginRight" to 10)), "btn-secondary" to _pS(_uM("backgroundColor" to "#E2E8F0")), "btn-text" to _pS(_uM("fontSize" to 14, "color" to "#FFFFFF")), "btn-text-dark" to _pS(_uM("color" to "#0F172A")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
