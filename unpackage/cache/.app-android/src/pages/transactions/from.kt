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
open class GenPagesTransactionsFrom : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesTransactionsFrom) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesTransactionsFrom
            val _cache = __ins.renderCache
            val supplierId = ref("")
            val supplierIdText = computed(fun(): String {
                return if (supplierId.value == "") {
                    "未传入"
                } else {
                    supplierId.value
                }
            }
            )
            onLoad(fun(event: OnLoadOptions){
                val supplierIdValue = event["supplier_id"]
                supplierId.value = if (supplierIdValue == null) {
                    ""
                } else {
                    (supplierIdValue as String)
                }
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to "新增采购记录", "showBack" to true, "showSearch" to false, "showHome" to true, "homePath" to "/pages/suppliers/index", "backgroundColor" to "#EEF2F7")),
                    _cE("scroll-view", _uM("style" to _nS(_uM("flex" to "1")), "class" to "page-scroll"), _uA(
                        _cE("view", _uM("class" to "page-content"), _uA(
                            _cE("view", _uM("class" to "hero-card"), _uA(
                                _cE("text", _uM("class" to "hero-title"), "采购记录录入页"),
                                _cE("text", _uM("class" to "hero-desc"), "当前已接入从供应商菜单进入的跳转，后续可以在这里补采购单表单。")
                            )),
                            _cE("view", _uM("class" to "info-card"), _uA(
                                _cE("text", _uM("class" to "info-label"), "供应商ID"),
                                _cE("text", _uM("class" to "info-value"), _tD(supplierIdText.value), 1)
                            )),
                            _cE("view", _uM("class" to "hint-card"), _uA(
                                _cE("text", _uM("class" to "hint-text"), "请基于该供应商补充采购时间、采购金额、备注和附件等字段。")
                            ))
                        ))
                    ), 4)
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#EEF2F7")), "page-scroll" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "page-content" to _pS(_uM("paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12)), "hero-card" to _pS(_uM("paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#FFFFFF")), "hero-title" to _pS(_uM("fontSize" to 18, "fontWeight" to "600", "color" to "#1F2937")), "hero-desc" to _pS(_uM("marginTop" to 8, "fontSize" to 14, "lineHeight" to "1.5em", "color" to "#6B7280")), "info-card" to _pS(_uM("marginTop" to 12, "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#FFFFFF")), "info-label" to _pS(_uM("fontSize" to 13, "color" to "#6B7280")), "info-value" to _pS(_uM("marginTop" to 8, "fontSize" to 16, "fontWeight" to "600", "color" to "#111827")), "hint-card" to _pS(_uM("marginTop" to 12, "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#DBEAFE")), "hint-text" to _pS(_uM("fontSize" to 14, "lineHeight" to "1.5em", "color" to "#1E3A8A")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
