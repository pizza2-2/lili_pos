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
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import uts.sdk.modules.limeScan.scanCode
import uts.sdk.modules.limeScan.GeneralCallbackResult
import uts.sdk.modules.limeScan.ScanCodeOption
import uts.sdk.modules.limeScan.ScanCodeSuccessCallbackResult
import io.dcloud.uniapp.extapi.switchTab as uni_switchTab
open class GenPagesTestScan : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesTestScan) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesTestScan
            val _cache = __ins.renderCache
            val result = ref("")
            val type = ref("")
            val errorMessage = ref("")
            val resultText = computed(fun(): String {
                if (result.value == "") {
                    return "暂无结果"
                }
                return result.value
            }
            )
            val typeText = computed(fun(): String {
                if (type.value == "") {
                    return "暂无类型"
                }
                return type.value
            }
            )
            fun gen_scan_fn() {
                errorMessage.value = ""
                scanCode(ScanCodeOption(success = fun(res: ScanCodeSuccessCallbackResult){
                    result.value = res.result
                    type.value = res.scanType
                    console.log("扫码类型:", res.scanType)
                    console.log("扫码结果:", res.result)
                }
                , fail = fun(res: GeneralCallbackResult){
                    errorMessage.value = if (res.errMsg == "") {
                        "扫码失败"
                    } else {
                        res.errMsg
                    }
                    console.log("扫码失败:", errorMessage.value)
                }
                , complete = fun(res: GeneralCallbackResult){
                    console.log("扫码完成:", res.errMsg)
                }
                ))
            }
            val scan = ::gen_scan_fn
            fun gen_goBack_fn() {
                uni_navigateBack(NavigateBackOptions(delta = 1, fail = fun(_){
                    uni_switchTab(SwitchTabOptions(url = "/pages/tabbar/settings"))
                }
                ))
            }
            val goBack = ::gen_goBack_fn
            return fun(): Any? {
                return _cE("view", _uM("class" to "page"), _uA(
                    _cE("view", _uM("class" to "status-bar-space")),
                    _cE("view", _uM("class" to "topbar"), _uA(
                        _cE("text", _uM("class" to "title"), "扫码测试"),
                        _cE("view", _uM("class" to "back-btn", "onClick" to fun(){
                            goBack()
                        }
                        ), _uA(
                            _cE("text", _uM("class" to "back-text"), "返回")
                        ), 8, _uA(
                            "onClick"
                        ))
                    )),
                    _cE("view", _uM("class" to "panel"), _uA(
                        _cE("text", _uM("class" to "label"), "扫描结果"),
                        _cE("text", _uM("class" to "result-text"), _tD(unref(resultText)), 1),
                        _cE("text", _uM("class" to "label second-label"), "扫码类型"),
                        _cE("text", _uM("class" to "type-text"), _tD(unref(typeText)), 1)
                    )),
                    _cE("view", _uM("class" to "scan-btn", "onClick" to fun(){
                        scan()
                    }
                    ), _uA(
                        _cE("text", _uM("class" to "scan-btn-text"), "开启扫描")
                    ), 8, _uA(
                        "onClick"
                    )),
                    if (unref(errorMessage) != "") {
                        _cE("view", _uM("key" to 0, "class" to "error-box"), _uA(
                            _cE("text", _uM("class" to "error-text"), _tD(unref(errorMessage)), 1)
                        ))
                    } else {
                        _cC("v-if", true)
                    }
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
                return _uM("page" to _pS(_uM("backgroundColor" to "#F6F8FB", "paddingLeft" to 18, "paddingRight" to 18, "paddingBottom" to 24)), "status-bar-space" to _pS(_uM("height" to CSS_VAR_STATUS_BAR_HEIGHT)), "topbar" to _pS(_uM("height" to 52, "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between")), "title" to _pS(_uM("fontSize" to 22, "lineHeight" to "28px", "fontWeight" to "700", "color" to "#111827")), "back-btn" to _pS(_uM("height" to 36, "paddingLeft" to 16, "paddingRight" to 16, "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "#E5E7EB")), "back-text" to _pS(_uM("fontSize" to 14, "color" to "#334155")), "panel" to _pS(_uM("marginTop" to 16, "paddingTop" to 18, "paddingRight" to 18, "paddingBottom" to 18, "paddingLeft" to 18, "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "backgroundColor" to "#FFFFFF")), "label" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#64748B")), "second-label" to _pS(_uM("marginTop" to 18)), "result-text" to _pS(_uM("marginTop" to 8, "fontSize" to 18, "lineHeight" to "26px", "fontWeight" to "600", "color" to "#111827")), "type-text" to _pS(_uM("marginTop" to 8, "fontSize" to 16, "lineHeight" to "22px", "color" to "#334155")), "scan-btn" to _pS(_uM("marginTop" to 22, "height" to 48, "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "#2563EB")), "scan-btn-text" to _pS(_uM("fontSize" to 16, "fontWeight" to "600", "color" to "#FFFFFF")), "error-box" to _pS(_uM("marginTop" to 14, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#FEF2F2")), "error-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#B91C1C")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
