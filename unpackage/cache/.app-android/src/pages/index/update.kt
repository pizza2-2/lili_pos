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
import io.dcloud.uniapp.extapi.hideLoading as uni_hideLoading
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesIndexUpdate : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesIndexUpdate) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesIndexUpdate
            val _cache = __ins.renderCache
            val second = ref(90)
            val interval = ref<Number>(0)
            fun gen_startInterval_fn() {
                interval.value = setInterval(fun(){
                    second.value--
                    if (second.value <= 0) {
                        clearInterval(interval.value)
                        second.value = 90
                    }
                }
                , 1000)
            }
            val startInterval = ::gen_startInterval_fn
            fun gen_appUpdateClick_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (second.value < 90) {
                            return@w1
                        }
                        uni_showLoading(ShowLoadingOptions(title = "loading"))
                        try {
                            await(default__2())
                            uni_hideLoading()
                            setUpdateTime(Date().getTime())
                            startInterval()
                        }
                         catch (error: Throwable) {
                            console.log(error, " at pages/index/update.uvue:65")
                            uni_hideLoading()
                            uni_showToast(ShowToastOptions(title = "消息发送失败，查看控制台信息。", icon = "none"))
                        }
                })
            }
            val appUpdateClick = ::gen_appUpdateClick_fn
            onLoad(fun(_options){
                uni_showModal(ShowModalOptions(title = "注意", content = "该功能使用的是免费版uniCloud服务空间，请勿频繁刷此接口。\n如果没有弹出更新弹窗原因是：\n1.服务空间的资源使用完了。\n2.把服务空间关了。\n3.网络请求有延迟，稍等一下。\n但功能是正常的，可以把demo下载下来，使用自己的服务空间测试。", showCancel = false))
                getUpdateTime()
                var subSecond = Math.abs(fcTimeSubSecond(updateTime.value))
                if (subSecond > 90) {
                    second.value = 90
                } else {
                    second.value = 90 - subSecond
                    startInterval()
                }
            }
            )
            onUnload(fun(){
                clearInterval(interval.value)
            }
            )
            return fun(): Any? {
                return _cE("scroll-view", _uM("class" to "flex-ai-c", "style" to _nS(_uM("flex" to "1"))), _uA(
                    _cE("text", _uM("class" to "m-20 bg-grey p-20"), _uA(
                        _cE("text", _uM("class" to "color-grey fs-28"), " 版本更新使用到了uniapp官方的uni-admin和uniCloud，使用这个前提需要把这两个配置好，可以看下面的文档。注意：uniapp-x App端目前不支持热更新。只能整包更新。（官方后续会出热更新） \\n 可以把获取App更新放在 "),
                        _cE("text", _uM("class" to "color-red fs-28"), " /store/global.uts "),
                        _cE("text", _uM("class" to "color-grey fs-28"), " 文件中（代码中注释了，打开注释即可）。这样就能在每次启动app的时候，检查更新。 ")
                    )),
                    _cE("button", _uM("class" to "mt-40", "type" to "primary", "disabled" to (unref(second) < 90), "onClick" to appUpdateClick), _tD(if (unref(second) < 90) {
                        unref(second) + "秒后可重新获取"
                    } else {
                        "获取应用更新"
                    }
                    ), 9, _uA(
                        "disabled"
                    )),
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
