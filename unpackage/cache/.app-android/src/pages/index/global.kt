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
open class GenPagesIndexGlobal : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesIndexGlobal) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesIndexGlobal
            val _cache = __ins.renderCache
            val avatarImages = ref(_uA<String>("https://img0.baidu.com/it/u=3701141354,3470104905&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=800", "https://img1.baidu.com/it/u=3290819308,452036545&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=667", "https://img2.baidu.com/it/u=784868447,2698648098&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500"))
            val galleryImages = ref(_uA<String>("https://img1.baidu.com/it/u=3290819308,452036545&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=667", "https://img2.baidu.com/it/u=784868447,2698648098&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500", "https://img0.baidu.com/it/u=3701141354,3470104905&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=800"))
            val eventLog = ref<String>("等待操作")
            fun gen_readPayloadString_fn(payload: UTSJSONObject, key: String): String {
                val value = payload[key]
                if (value == null) {
                    return ""
                }
                return value as String
            }
            val readPayloadString = ::gen_readPayloadString_fn
            fun gen_handlePreviewEvent_fn(payload: UTSJSONObject) {
                val path = readPayloadString(payload, "path")
                eventLog.value = "打开预览: " + path
            }
            val handlePreviewEvent = ::gen_handlePreviewEvent_fn
            fun gen_handleSaveEvent_fn(payload: UTSJSONObject) {
                val path = readPayloadString(payload, "path")
                eventLog.value = "已保存图片: " + path
            }
            val handleSaveEvent = ::gen_handleSaveEvent_fn
            fun gen_handleShareEvent_fn(payload: UTSJSONObject) {
                val path = readPayloadString(payload, "path")
                eventLog.value = "已触发分享: " + path
            }
            val handleShareEvent = ::gen_handleShareEvent_fn
            return fun(): Any? {
                val _component_lili_preview = resolveEasyComponent("lili-preview", GenUniModulesLiliPreviewComponentsLiliPreviewLiliPreviewClass)
                return _cE("scroll-view", _uM("class" to "page-scroll", "style" to _nS(_uM("flex" to "1")), "direction" to "vertical"), _uA(
                    _cE("view", _uM("class" to "page"), _uA(
                        _cE("view", _uM("class" to "hero"), _uA(
                            _cE("text", _uM("class" to "hero-title"), "Lili Preview"),
                            _cE("text", _uM("class" to "hero-desc"), "点击缩略图进入全屏预览，支持多图切换、系统分享和保存到相册。")
                        )),
                        _cE("view", _uM("class" to "section"), _uA(
                            _cE("text", _uM("class" to "section-title"), "头像尺寸预览"),
                            _cV(_component_lili_preview, _uM("images" to avatarImages.value, "thumbSize" to 72, "radius" to 36, "gap" to 12, "onPreview" to handlePreviewEvent, "onSave" to handleSaveEvent, "onShare" to handleShareEvent), null, 8, _uA(
                                "images"
                            ))
                        )),
                        _cE("view", _uM("class" to "section"), _uA(
                            _cE("text", _uM("class" to "section-title"), "方图预览"),
                            _cV(_component_lili_preview, _uM("images" to galleryImages.value, "thumbSize" to 96, "radius" to 16, "gap" to 12, "onPreview" to handlePreviewEvent, "onSave" to handleSaveEvent, "onShare" to handleShareEvent), null, 8, _uA(
                                "images"
                            ))
                        )),
                        _cE("view", _uM("class" to "section"), _uA(
                            _cE("text", _uM("class" to "section-title"), "最近一次操作"),
                            _cE("view", _uM("class" to "log-card"), _uA(
                                _cE("text", _uM("class" to "log-text"), _tD(eventLog.value), 1)
                            ))
                        ))
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
                return _uM("page-scroll" to _pS(_uM("backgroundColor" to "#F8FAFC")), "page" to _pS(_uM("paddingLeft" to 16, "paddingRight" to 16, "paddingTop" to 20, "paddingBottom" to 32)), "hero" to _pS(_uM("paddingLeft" to 20, "paddingRight" to 20, "paddingTop" to 20, "paddingBottom" to 20, "borderTopLeftRadius" to 20, "borderTopRightRadius" to 20, "borderBottomRightRadius" to 20, "borderBottomLeftRadius" to 20, "backgroundColor" to "#0F172A")), "hero-title" to _pS(_uM("fontSize" to 26, "color" to "#F8FAFC")), "hero-desc" to _pS(_uM("marginTop" to 10, "fontSize" to 14, "lineHeight" to "1.6em", "color" to "#CBD5E1")), "section" to _pS(_uM("marginTop" to 16, "paddingLeft" to 16, "paddingRight" to 16, "paddingTop" to 16, "paddingBottom" to 16, "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "backgroundColor" to "#FFFFFF")), "section-title" to _pS(_uM("marginBottom" to 14, "fontSize" to 16, "color" to "#0F172A")), "log-card" to _pS(_uM("paddingLeft" to 14, "paddingRight" to 14, "paddingTop" to 14, "paddingBottom" to 14, "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "backgroundColor" to "#E2E8F0")), "log-text" to _pS(_uM("fontSize" to 14, "lineHeight" to "1.6em", "color" to "#1E293B")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
