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
open class GenPagesIndexTest : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesIndexTest) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesIndexTest
            val _cache = __ins.renderCache
            val id = ref("")
            val title = ref("")
            onLoad(fun(event: OnLoadOptions){
                console.log(event, " at pages/index/test.uvue:21")
                console.log(event["id"], " at pages/index/test.uvue:22")
                console.log(event["title"], " at pages/index/test.uvue:23")
                id.value = event["id"] as String
                title.value = event["title"] as String
            }
            )
            return fun(): Any? {
                return _cE("scroll-view", _uM("class" to "p-20", "style" to _nS(_uM("flex" to "1"))), _uA(
                    _cE("text", _uM("class" to "color-grey"), "从上个页面传递过来的id值"),
                    _cE("text", null, _tD(unref(id)), 1),
                    _cE("text", _uM("class" to "mt-20 color-grey"), "从上个页面传递过来的title值"),
                    _cE("text", null, _tD(unref(title)), 1)
                ), 4)
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(), _uA(
                GenApp.styles
            ))
        }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
