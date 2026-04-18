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
import io.dcloud.uniapp.extapi.resetPrivacyAuthorization as uni_resetPrivacyAuthorization
open class GenPagesIndexPrivacy : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesIndexPrivacy) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesIndexPrivacy
            val _cache = __ins.renderCache
            fun gen_resetPrivacy_fn() {
                uni_resetPrivacyAuthorization()
            }
            val resetPrivacy = ::gen_resetPrivacy_fn
            return fun(): Any? {
                return _cE("scroll-view", _uM("class" to "flex-ai-c", "style" to _nS(_uM("flex" to "1"))), _uA(
                    _cE("text", _uM("class" to "m-20 bg-grey p-20"), _uA(
                        _cE("text", _uM("class" to "color-grey fs-28"), " 首次进入应用要有用户协议和隐私状态的弹窗，弹窗样式和内容页面在 "),
                        _cE("text", _uM("class" to "color-red fs-28"), " /pages/privacy/privacy.uvue "),
                        _cE("text", _uM("class" to "color-grey fs-28"), " 文件中，内容和样式可以自定义。注意： "),
                        _cE("text", _uM("class" to "color-red fs-28"), " 用户未同意隐私政策之前，建议不要做任何操作。 "),
                        _cE("text", _uM("class" to "color-grey fs-28"), " 否则安卓端上架审核会不通过。此demo中的代码已经做了相应的处理。 ")
                    )),
                    _cE("button", _uM("type" to "primary", "onClick" to resetPrivacy), "重置隐私协议状态"),
                    _cE("text", _uM("class" to "color-grey fs-28 mt-20"), "点击上面的按钮，可以重置为用户未同意隐私协议。此时清理程序进程再重新打开程序，或者调用系统权限将会再次弹出隐私协议窗口。"),
                    _cE("view", _uM("class" to "bottom w100 flex-jc-c flex-ai-c"), _uA(
                        _cE("text", _uM("class" to "color-base fs-30"), " 微信公众号搜索：FC程序开发 ")
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
                return _uM("bottom" to _pS(_uM("position" to "absolute", "left" to 0, "bottom" to "var(--uni-safe-area-inset-bottom)")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
