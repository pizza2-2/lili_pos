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
open class GenPagesTabbarProducts : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _cache = this.`$`.renderCache
        return _cE("scroll-view", _uM("class" to "page-scroll", "style" to _nS(_uM("flex" to "1")), "direction" to "vertical"), _uA(
            _cE("view", _uM("class" to "page"), _uA(
                _cE("view", _uM("class" to "status-bar-space")),
                _cE("view", _uM("class" to "hero"), _uA(
                    _cE("text", _uM("class" to "hero-title"), "商品"),
                    _cE("text", _uM("class" to "hero-desc"), "商品管理、筛选组件和弹窗组件都可以从这里继续接业务。")
                )),
                _cE("view", _uM("class" to "card"), _uA(
                    _cE("text", _uM("class" to "card-label"), "当前模块"),
                    _cE("text", _uM("class" to "card-value"), "商品中心"),
                    _cE("text", _uM("class" to "card-desc"), "这里作为底部导航首页，后续可继续接商品列表、分类、库存和价格体系。")
                ))
            ))
        ), 4)
    }
    companion object {
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ), _uA(
                GenApp.styles
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("page-scroll" to _pS(_uM("backgroundColor" to "#F6F7FB")), "page" to _pS(_uM("paddingLeft" to 16, "paddingRight" to 16, "paddingBottom" to 24, "backgroundColor" to "#F6F7FB")), "status-bar-space" to _pS(_uM("height" to CSS_VAR_STATUS_BAR_HEIGHT)), "hero" to _pS(_uM("backgroundColor" to "#14532D", "borderTopLeftRadius" to 20, "borderTopRightRadius" to 20, "borderBottomRightRadius" to 20, "borderBottomLeftRadius" to 20, "paddingTop" to 20, "paddingRight" to 20, "paddingBottom" to 20, "paddingLeft" to 20)), "hero-title" to _pS(_uM("fontSize" to 24, "lineHeight" to "30px", "color" to "#FFFFFF", "fontWeight" to "bold")), "hero-desc" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#DCFCE7", "marginTop" to 8)), "card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "paddingTop" to 18, "paddingRight" to 18, "paddingBottom" to 18, "paddingLeft" to 18, "marginTop" to 14, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E5E7EB", "borderRightColor" to "#E5E7EB", "borderBottomColor" to "#E5E7EB", "borderLeftColor" to "#E5E7EB")), "card-label" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#94A3B8")), "card-value" to _pS(_uM("fontSize" to 24, "lineHeight" to "30px", "color" to "#0F172A", "fontWeight" to "bold", "marginTop" to 10)), "card-desc" to _pS(_uM("fontSize" to 15, "lineHeight" to "22px", "color" to "#475569", "marginTop" to 10)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
