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
open class GenPagesIndexTime : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesIndexTime) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesIndexTime
            val _cache = __ins.renderCache
            val dateValue = ref("")
            fun btnClick1(type: String, format: String = "YYYY/MM/DD HH:mm:ss") {
                if (type == "number") {
                    var number = Date().getTime()
                    dateValue.value = fcTimeFormat(number, format)
                } else if (type == "date") {
                    var date = Date()
                    dateValue.value = fcTimeFormat(date, format)
                } else {
                    var string = "1949-10-01T21:00:00.171+08:00"
                    dateValue.value = fcTimeFormat(string, format)
                }
            }
            val offsetTime1 = ref("")
            val offsetTime2 = ref("")
            val offsetTime3 = ref("")
            val offsetTime4 = ref("")
            val offsetTime5 = ref("")
            val offsetTime6 = ref("")
            fun gen_btnClick2_fn() {
                var val1 = fcTimeOffsetSecond(-fcTimeM2S(5))
                var val2 = fcTimeOffsetSecond(fcTimeM2S(5))
                offsetTime1.value = fcTimeFormat(val1)
                offsetTime2.value = fcTimeFormat(val2)
            }
            val btnClick2 = ::gen_btnClick2_fn
            fun gen_btnClick3_fn() {
                var val1 = fcTimeOffsetSecond(-fcTimeH2S(5))
                var val2 = fcTimeOffsetSecond(fcTimeH2S(5))
                offsetTime3.value = fcTimeFormat(val1)
                offsetTime4.value = fcTimeFormat(val2)
            }
            val btnClick3 = ::gen_btnClick3_fn
            fun gen_btnClick4_fn() {
                var val1 = fcTimeOffsetSecond(-fcTimeD2S(1), "2025/10/01 20:08:08")
                var val2 = fcTimeOffsetSecond(fcTimeD2S(1), "2025/10/01 20:08:08")
                offsetTime5.value = fcTimeFormat(val1)
                offsetTime6.value = fcTimeFormat(val2)
            }
            val btnClick4 = ::gen_btnClick4_fn
            val offsetTime7 = ref("")
            val subSecond = ref(0)
            fun gen_btnClick5_fn() {
                var date = Date()
                var minTime = fcTimeOffsetSecond(-fcTimeD2S(1), date) as Number
                var maxTime = fcTimeOffsetSecond(fcTimeD2S(1), date) as Number
                val _ref = if (minTime < maxTime) {
                    _uA(
                        minTime,
                        maxTime
                    )
                } else {
                    _uA(
                        maxTime,
                        minTime
                    )
                }
                val actualMin = _ref[0]
                val actualMax = _ref[1]
                var timeNum = Math.floor(actualMin + (actualMax - actualMin + 1) * Math.random())
                offsetTime7.value = fcTimeFormat(timeNum, "YYYY/MM/DD HH:mm:ss")
            }
            val btnClick5 = ::gen_btnClick5_fn
            watch(fun(): String {
                return offsetTime7.value
            }
            , fun(){
                if (offsetTime7.value != "") {
                    subSecond.value = fcTimeSubSecond(offsetTime7.value)
                }
            }
            )
            return fun(): Any? {
                return _cE("scroll-view", _uM("style" to _nS(_uM("flex" to "1"))), _uA(
                    _cE("text", _uM("class" to "m-20 bg-grey p-20"), _uA(
                        _cE("text", _uM("class" to "color-grey fs-28"), " 文件位置在 "),
                        _cE("text", _uM("class" to "color-red fs-28"), " /pkg/util/time.uts "),
                        _cE("text", _uM("class" to "color-grey fs-28"), " 中，封装一些常用的时间处理方法。由于是使用uts语言生成的，理论上来说是支持全平台。 ")
                    )),
                    _cE("view", _uM("class" to "flexr p-x-20 mt-40"), _uA(
                        _cE("view", _uM("class" to "bg-base", "style" to _nS(_uM("width" to "20rpx", "border-radius" to "8rpx"))), null, 4),
                        _cE("text", _uM("class" to "ml-10"), "格式化时间")
                    )),
                    _cE("view", _uM("class" to "m-20 bg-grey p-20"), _uA(
                        _cE("text", _uM("class" to "color-red"), "格式化后的时间：" + _tD(unref(dateValue)), 1),
                        _cE("button", _uM("type" to "primary", "class" to "mt-20", "onClick" to fun(){
                            btnClick1("number")
                        }
                        ), "格式化时间（时间戳number类型）", 8, _uA(
                            "onClick"
                        )),
                        _cE("button", _uM("type" to "primary", "class" to "mt-20", "onClick" to fun(){
                            btnClick1("date")
                        }
                        ), "格式化时间（时间Date类型）", 8, _uA(
                            "onClick"
                        )),
                        _cE("button", _uM("type" to "primary", "class" to "mt-20", "onClick" to fun(){
                            btnClick1("string")
                        }
                        ), "格式化时间（UTC字符串类型）", 8, _uA(
                            "onClick"
                        )),
                        _cE("button", _uM("type" to "primary", "class" to "mt-20", "onClick" to fun(){
                            btnClick1("number", "YYYY-MM-DD")
                        }
                        ), "格式化时间（显示年月日）", 8, _uA(
                            "onClick"
                        )),
                        _cE("button", _uM("type" to "primary", "class" to "mt-20", "onClick" to fun(){
                            btnClick1("number", "HH:mm:ss")
                        }
                        ), "格式化时间（显示时分秒）", 8, _uA(
                            "onClick"
                        ))
                    )),
                    _cE("view", _uM("class" to "flexr p-x-20 mt-40"), _uA(
                        _cE("view", _uM("class" to "bg-base", "style" to _nS(_uM("width" to "20rpx", "border-radius" to "8rpx"))), null, 4),
                        _cE("text", _uM("class" to "ml-10"), "获取指定时间的前后时间")
                    )),
                    _cE("view", _uM("class" to "m-20 bg-grey p-20"), _uA(
                        _cE("text", _uM("class" to "color-red"), "前5分钟的时间：" + _tD(unref(offsetTime1)), 1),
                        _cE("text", _uM("class" to "color-red"), "后5分钟的时间：" + _tD(unref(offsetTime2)), 1),
                        _cE("button", _uM("type" to "primary", "class" to "mt-20", "onClick" to fun(){
                            btnClick2()
                        }
                        ), "获取5分钟前后的时间", 8, _uA(
                            "onClick"
                        )),
                        _cE("text", _uM("class" to "color-red"), "前5小时的时间：" + _tD(unref(offsetTime3)), 1),
                        _cE("text", _uM("class" to "color-red"), "后5小时的时间：" + _tD(unref(offsetTime4)), 1),
                        _cE("button", _uM("type" to "primary", "class" to "mt-20", "onClick" to fun(){
                            btnClick3()
                        }
                        ), "获取5小时前后的时间", 8, _uA(
                            "onClick"
                        )),
                        _cE("text", null, "2025-10-1 20:08:08前一天的时间："),
                        _cE("text", _uM("class" to "color-red"), _tD(unref(offsetTime5)), 1),
                        _cE("text", null, "2025-10-1 20:08:08后一天的时间："),
                        _cE("text", _uM("class" to "color-red"), _tD(unref(offsetTime6)), 1),
                        _cE("button", _uM("type" to "primary", "class" to "mt-20", "onClick" to fun(){
                            btnClick4()
                        }
                        ), "2025/10/01 20:08:08前后1天时间", 8, _uA(
                            "onClick"
                        ))
                    )),
                    _cE("view", _uM("class" to "flexr p-x-20 mt-40"), _uA(
                        _cE("view", _uM("class" to "bg-base", "style" to _nS(_uM("width" to "20rpx", "border-radius" to "8rpx"))), null, 4),
                        _cE("text", _uM("class" to "ml-10"), "获取两个时间相差的秒数")
                    )),
                    _cE("view", _uM("class" to "m-20 bg-grey p-20"), _uA(
                        _cE("text", _uM("class" to "color-red"), "随机获取的时间：" + _tD(unref(offsetTime7)), 1),
                        _cE("text", _uM("class" to "color-red"), "距离当前时间相差的秒数为: " + _tD(unref(subSecond)), 1),
                        _cE("button", _uM("type" to "primary", "class" to "mt-20", "onClick" to fun(){
                            btnClick5()
                        }
                        ), "随机获取一个时间", 8, _uA(
                            "onClick"
                        )),
                        _cE("text", _uM("class" to "color-red"), " 可以根据此，做时间判断，比如常见显示的\n 刚刚、n分钟前、n小时前、n天前、n星期前、n月前...\n 不想要负数，请使用绝对值 Math.abs() 处理 ")
                    )),
                    _cE("view", _uM("class" to "mt-40 bottom w100 flex-jc-c flex-ai-c"), _uA(
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
                return _uM("bottom" to _pS(_uM("marginBottom" to "var(--uni-safe-area-inset-bottom)")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
