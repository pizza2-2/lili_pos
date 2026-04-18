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
open class GenPagesIndexCss : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _cache = this.`$`.renderCache
        return _cE("scroll-view", _uM("style" to _nS(_uM("flex" to "1"))), _uA(
            _cE("text", _uM("class" to "m-20 bg-grey p-20"), _uA(
                _cE("text", _uM("class" to "color-grey fs-28"), " 自定义的class样式，在 "),
                _cE("text", _uM("class" to "color-red fs-28"), " /pkg/style/app.css "),
                _cE("text", _uM("class" to "color-grey fs-28"), " 中。其中定义了自定义的iconfont和字体，iconfont和字体在 "),
                _cE("text", _uM("class" to "color-red fs-28"), " /static "),
                _cE("text", _uM("class" to "color-grey fs-28"), " 文件夹中的 "),
                _cE("text", _uM("class" to "color-red fs-28"), " AlimamaDaoLiTi.ttf和iconfont.ttf "),
                _cE("text", _uM("class" to "color-grey fs-28"), " 请根据实际项目替换或删除。 ")
            )),
            _cE("view", _uM("class" to "flexc flex-jc-c flex-ai-c p-40 bg-grey"), _uA(
                _cE("text", _uM("class" to "iconfont", "style" to _nS(_uM("font-size" to "100rpx", "color" to "red"))), "", 4),
                _cE("text", _uM("class" to "alimama-daoliti fs-40"), "阿里妈妈刀隶体-ttf")
            )),
            _cE("view", _uM("class" to "bottom w100 flex-jc-c flex-ai-c"), _uA(
                _cE("text", _uM("class" to "color-base fs-30"), " 微信公众号搜索：FC程序开发 ")
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
