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
import io.dcloud.uniapp.extapi.closeDialogPage as uni_closeDialogPage
import io.dcloud.uniapp.extapi.exit as uni_exit
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.resetPrivacyAuthorization as uni_resetPrivacyAuthorization
import io.dcloud.uniapp.extapi.showModal as uni_showModal
open class GenPagesPrivacyPrivacy : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesPrivacyPrivacy) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesPrivacyPrivacy
            val _cache = __ins.renderCache
            val hrefLicense = ref("https://dcloud.io/license/DCloud.html")
            val hrefPrivacy = ref("https://dcloud.io/license/hello-uni-app-x.html")
            val currentInstance = getCurrentInstance()
            fun gen_agree_fn() {
                isAgreePrivacyState.value = true
                uni_closeDialogPage(CloseDialogPageOptions(dialogPage = currentInstance?.proxy?.`$page`))
            }
            val agree = ::gen_agree_fn
            fun gen_reject_fn() {
                uni_showModal(ShowModalOptions(content = "不同意《用户协议》和《隐私政策》将无法使用我们的应用。同意后可继续使用。", cancelText = "退出应用", cancelColor = "#c0c0c0", confirmText = "前往同意政策", confirmColor = "#007aff", success = fun(res){
                    if (res.cancel) {
                        console.log("退出", " at pages/privacy/privacy.uvue:48")
                        uni_resetPrivacyAuthorization()
                        uni_exit(null)
                    }
                }
                ))
            }
            val reject = ::gen_reject_fn
            fun gen_hrefClick_fn(href: String) {
                uni_navigateTo(NavigateToOptions(url = "/pages/webview/webview?url=" + href))
            }
            val hrefClick = ::gen_hrefClick_fn
            onBackPress(fun(_options): Boolean? {
                return true
            }
            )
            return fun(): Any? {
                return _cE("view", _uM("class" to "dialog-container wh100 flexc flex-jc-c flex-ai-c"), _uA(
                    _cE("view", _uM("class" to "dialog-content br-20"), _uA(
                        _cE("view", _uM("class" to "w100 flex-jc-c flex-ai-c bg-white"), _uA(
                            _cE("image", _uM("class" to "mt-40", "src" to `default`, "style" to _nS(_uM("width" to "60px", "height" to "60px"))), null, 4)
                        )),
                        _cE("text", _uM("class" to "ta-c pt-40 fs-40 color-black bg-white"), "个人信息保护指引"),
                        _cE("scroll-view", _uM("class" to "flex1 pt-20 p-x-40 bg-white", "style" to _nS(_uM("align-content" to "center")), "show-scrollbar" to "false"), _uA(
                            _cE("text", null, _uA(
                                _cE("text", _uM("class" to "color-grey"), "欢迎使用Hello uni-app x，我们将通过"),
                                _cE("text", _uM("class" to "privacy-href", "onClick" to fun(){
                                    hrefClick(unref(hrefLicense))
                                }
                                ), "《用户服务协议》", 8, _uA(
                                    "onClick"
                                )),
                                _cE("text", _uM("class" to "color-grey"), "及相关个人信息处理规则帮助你了解应用如何收集、处理个人信息。根据《常见类型移动互联网应用程序必要个人信息范围规定》，应用在演示 uni-app x 能力时仅收集、处理相关必要个人信息及数据。我们通过"),
                                _cE("text", _uM("class" to "privacy-href", "onClick" to fun(){
                                    hrefClick(unref(hrefPrivacy))
                                }
                                ), "《隐私政策》", 8, _uA(
                                    "onClick"
                                )),
                                _cE("text", _uM("class" to "color-grey"), "帮助你全面了解我们的服务及收集、处理个人信息的详细情况。")
                            ))
                        ), 4),
                        _cE("button", _uM("class" to "button", "onClick" to reject), "不同意"),
                        _cE("button", _uM("class" to "button", "type" to "primary", "open-type" to "agreePrivacyAuthorization", "onClick" to agree), "同意")
                    ))
                ))
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
                return _uM("dialog-container" to _pS(_uM("backgroundColor" to "rgba(0,0,0,0.3)")), "dialog-content" to _pS(_uM("height" to "55%", "width" to "80%")), "button" to _pS(_uM("borderTopLeftRadius" to 0, "borderTopRightRadius" to 0, "borderBottomRightRadius" to 0, "borderBottomLeftRadius" to 0, "borderTopWidth" to 0, "borderRightWidth" to 0, "borderBottomWidth" to 0, "borderLeftWidth" to 0, "borderTopStyle" to "none", "borderRightStyle" to "none", "borderBottomStyle" to "none", "borderLeftStyle" to "none", "borderTopColor" to "#000000", "borderRightColor" to "#000000", "borderBottomColor" to "#000000", "borderLeftColor" to "#000000", "fontSize" to 15, "color" to "#D3D3D3", "textAlign" to "center", "lineHeight" to "40px")), "button-hover1" to _pS(_uM("color" to "#000000", "backgroundColor" to "#ffffff")), "button-hover2" to _pS(_uM("color" to "#000000", "backgroundColor" to "#4169E1")), "privacy-href" to _pS(_uM("color" to "#5F9EA0", "fontWeight" to "bold", "textDecorationLine" to "underline")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
