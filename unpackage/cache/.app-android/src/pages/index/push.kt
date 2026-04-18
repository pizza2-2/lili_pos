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
import io.dcloud.uniapp.extapi.getPushClientId as uni_getPushClientId
import io.dcloud.uniapp.extapi.hideLoading as uni_hideLoading
import io.dcloud.uniapp.extapi.setAppBadgeNumber as uni_setAppBadgeNumber
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesIndexPush : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesIndexPush) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesIndexPush
            val _cache = __ins.renderCache
            val second = ref(90)
            val interval = ref<Number>(0)
            val clientID = ref("")
            fun gen_getClientID_fn() {
                uni_getPushClientId(GetPushClientIdOptions(success = fun(res){
                    clientID.value = res.cid
                    console.log("客户端推送标识:", clientID.value, " at pages/index/push.uvue:65")
                }
                , fail = fun(err) {
                    console.log(err, " at pages/index/push.uvue:68")
                }
                ))
            }
            val getClientID = ::gen_getClientID_fn
            val title = ref("fc-baseX-uniCloud发来的推送标题")
            val content = ref("恭喜你收到了来自fc-baseX-uniCloud的推送信息")
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
            fun gen_testPushClick_fn() {
                if (second.value < 90 || title.value == "" || content.value == "") {
                    return
                }
                uni_showLoading(ShowLoadingOptions(title = "loading"))
                uni_getPushClientId(GetPushClientIdOptions(success = fun(res){
                    uniCloud.callFunction(UniCloudCallFunctionOptions(name = "testUniPush", data = object : UTSJSONObject() {
                        var title = title.value
                        var content = content.value
                        var clientID = res.cid
                        var payload = "这里可以传送自定义信息"
                    })).then(fun(res){
                        console.log(res, " at pages/index/push.uvue:106")
                        setTimeout(fun(){
                            uni_showToast(ShowToastOptions(title = "发送推送成功，请在通知栏中查看", icon = "none"))
                        }
                        , 200)
                        setPushTime(Date().getTime())
                        startInterval()
                    }
                    ).`catch`(fun(err){
                        console.log(err, " at pages/index/push.uvue:117")
                        setTimeout(fun(){
                            uni_showToast(ShowToastOptions(title = "消息发送失败，查看控制台信息。", icon = "none"))
                        }
                        , 200)
                    }
                    ).`finally`(fun(){
                        uni_hideLoading()
                    }
                    )
                }
                , fail = fun(err) {
                    console.log(err, " at pages/index/push.uvue:129")
                }
                ))
            }
            val testPushClick = ::gen_testPushClick_fn
            fun gen_clearBadgeClick_fn() {
                uni_setAppBadgeNumber(0, null)
            }
            val clearBadgeClick = ::gen_clearBadgeClick_fn
            onLoad(fun(_options){
                uni_showModal(ShowModalOptions(title = "注意", content = "该功能使用的是免费版uniCloud服务空间，请勿频繁刷此接口。\n如果没有收到推送那原因是：\n1.服务空间的资源使用完了。\n2.把服务空间关了。\n3.推送有延迟，稍等一下。\n但功能是正常的，可以把demo下载下来，使用自己的服务空间测试。", showCancel = false))
                getPushTime()
                var subSecond = Math.abs(fcTimeSubSecond(pushTime.value))
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
                return _cE("scroll-view", _uM("style" to _nS(_uM("flex" to "1"))), _uA(
                    _cE("text", _uM("class" to "m-20 bg-grey p-20"), _uA(
                        _cE("text", _uM("class" to "color-grey fs-28"), " 开发unipush 2.0 请参照官方文档。这里只实现了简单的发送功能。后续再添加其它功能。 ")
                    )),
                    _cE("text", _uM("class" to "m-y-20"), _tD(unref(clientID)), 1),
                    _cE("button", _uM("type" to "primary", "onClick" to getClientID), "获取ClientID"),
                    _cE("view", _uM("class" to "mt-40 bg-grey p-20"), _uA(
                        _cE("view", _uM("class" to "mt-10 flexr flex-ai-c"), _uA(
                            _cE("text", _uM("class" to "fs-28"), "推送标题："),
                            _cE("input", _uM("class" to "p-10 bg-white fs-32", "modelValue" to unref(title), "onInput" to fun(`$event`: UniInputEvent){
                                trySetRefValue(title, `$event`.detail.value)
                            }
                            , "type" to "text", "placeholder" to "推送的标题"), null, 40, _uA(
                                "modelValue"
                            ))
                        )),
                        _cE("view", _uM("class" to "mt-10 flexr flex-ai-c"), _uA(
                            _cE("text", _uM("class" to "fs-28"), "推送内容："),
                            _cE("input", _uM("class" to "p-10 bg-white fs-32", "modelValue" to unref(content), "onInput" to fun(`$event`: UniInputEvent){
                                trySetRefValue(content, `$event`.detail.value)
                            }
                            , "type" to "text", "placeholder" to "推送的内容"), null, 40, _uA(
                                "modelValue"
                            ))
                        ))
                    )),
                    _cE("button", _uM("class" to "mt-20", "type" to "primary", "disabled" to (unref(second) < 90 || unref(title) == "" || unref(content) == ""), "onClick" to testPushClick), _tD(if (unref(second) < 90) {
                        unref(second) + "秒后可重新发送"
                    } else {
                        "云函数发出推送"
                    }
                    ), 9, _uA(
                        "disabled"
                    )),
                    _cE("text", _uM("class" to "mt-20 color-red fs-28"), " 实现了发送到此设备ClientId的功能。实际业务中可以使用网络请求调用\n 一定要使用自定义基座测试。\n iOS 和 鸿蒙 要在DCloud开发者中心做配置。否则无效。 "),
                    _cE("button", _uM("class" to "mt-20", "type" to "primary", "onClick" to clearBadgeClick), " 清除应用图标的角标 "),
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
                return _uM("input" to _pS(_uM("height" to "50rpx")), "bottom" to _pS(_uM("position" to "absolute", "left" to 0, "bottom" to "var(--uni-safe-area-inset-bottom)")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
