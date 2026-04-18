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
open class GenPagesIndexRouter : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesIndexRouter) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesIndexRouter
            val _cache = __ins.renderCache
            val id = ref("1")
            val title = ref("标题11111")
            fun gen_routeClick_fn() {
                toTestPage(object : UTSJSONObject() {
                    var id = id.value
                    var title = title.value
                })
            }
            val routeClick = ::gen_routeClick_fn
            return fun(): Any? {
                return _cE("scroll-view", _uM("class" to "flex-ai-c p-x-20", "style" to _nS(_uM("flex" to "1"))), _uA(
                    _cE("text", _uM("class" to "m-y-20 bg-grey p-20"), _uA(
                        _cE("text", _uM("class" to "color-grey fs-28"), " 路由跳转代码在 "),
                        _cE("text", _uM("class" to "color-red fs-28"), " /pkg/router/router.uts "),
                        _cE("text", _uM("class" to "color-grey fs-28"), " 文件中。新创建路由都可以在此文件中编写，已经封装好是否需要路由传值。路由拦截在 "),
                        _cE("text", _uM("class" to "color-red fs-28"), " /pkg/router/permission.uts "),
                        _cE("text", _uM("class" to "color-grey fs-28"), " 文件中，新建页面一定要注意是否需要添加到 "),
                        _cE("text", _uM("class" to "color-red fs-28"), " 白名单routerWhiteList "),
                        _cE("text", _uM("class" to "color-grey fs-28"), " 中，不在白名单中的路由需要有 "),
                        _cE("text", _uM("class" to "color-red fs-28"), " authState.token "),
                        _cE("text", _uM("class" to "color-grey fs-28"), " 才能跳转，否则会拦截到登录页面 ")
                    )),
                    _cE("view", _uM("class" to "flexr w100"), _uA(
                        _cE("text", _uM("class" to "mr-20"), "传递id的值"),
                        _cE("input", _uM("class" to "uni-input flex1 bg-grey", "modelValue" to unref(id), "onInput" to fun(`$event`: UniInputEvent){
                            trySetRefValue(id, `$event`.detail.value)
                        }
                        , "type" to "number"), null, 40, _uA(
                            "modelValue"
                        ))
                    )),
                    _cE("view", _uM("class" to "flexr mt-20 w100"), _uA(
                        _cE("text", _uM("class" to "mr-20"), "传递title"),
                        _cE("input", _uM("class" to "uni-input flex1 bg-grey", "modelValue" to unref(title), "onInput" to fun(`$event`: UniInputEvent){
                            trySetRefValue(title, `$event`.detail.value)
                        }
                        ), null, 40, _uA(
                            "modelValue"
                        ))
                    )),
                    _cE("button", _uM("type" to "primary", "class" to "mt-40", "onClick" to routeClick), "点击测试路由传值和路由拦截"),
                    _cE("text", _uM("class" to "fs-28 mt-20"), " 上方跳转的路由没有添加到白名单，需要在”全局状态“中 设置token 或 清除token 测试路由拦截\n 有token将进入“路由传参和拦截测试页面”\n 没有token将进入“登录页面” "),
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
                return _uM("uni-input" to _pS(_uM("height" to 28)), "bottom" to _pS(_uM("position" to "absolute", "left" to 0, "bottom" to "var(--uni-safe-area-inset-bottom)")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
