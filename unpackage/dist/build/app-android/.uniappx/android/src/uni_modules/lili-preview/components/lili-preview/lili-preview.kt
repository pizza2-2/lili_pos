@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME", "UNUSED_ANONYMOUS_PARAMETER", "SENSELESS_COMPARISON", "NAME_SHADOWING", "UNNECESSARY_NOT_NULL_ASSERTION")
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
import io.dcloud.uniapp.extapi.previewImage as uni_previewImage
open class GenUniModulesLiliPreviewComponentsLiliPreviewLiliPreview : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var images: UTSArray<String> by `$props`
    open var initialIndex: Number by `$props`
    open var visible: Boolean by `$props`
    open var thumbSize: Number by `$props`
    open var radius: Number by `$props`
    open var gap: Number by `$props`
    open var emptyText: String by `$props`
    open var showList: Boolean by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenUniModulesLiliPreviewComponentsLiliPreviewLiliPreview) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenUniModulesLiliPreviewComponentsLiliPreviewLiliPreview
            val _cache = __ins.renderCache
            val props = __props
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val imageList = ref(_uA<String>())
            val currentIndex = ref<Number>(0)
            val itemStyle = computed<String>(fun(): String {
                return "width:" + props.thumbSize + "px;height:" + props.thumbSize + "px;margin-right:" + props.gap + "px;margin-bottom:" + props.gap + "px;border-radius:" + props.radius + "px;"
            }
            )
            val imageStyle = computed<String>(fun(): String {
                return "border-radius:" + props.radius + "px;"
            }
            )
            fun gen_cloneStringArray_fn(list: UTSArray<String>): UTSArray<String> {
                val result: UTSArray<String> = _uA()
                run {
                    var i: Number = 0
                    while(i < list.length){
                        result.push(list[i])
                        i++
                    }
                }
                return result
            }
            val cloneStringArray = ::gen_cloneStringArray_fn
            fun gen_clampIndex_fn(index: Number, length: Number): Number {
                if (length <= 0) {
                    return 0
                }
                if (index < 0) {
                    return 0
                }
                if (index >= length) {
                    return length - 1
                }
                return index
            }
            val clampIndex = ::gen_clampIndex_fn
            fun gen_syncImages_fn(list: UTSArray<String>) {
                imageList.value = cloneStringArray(list)
                currentIndex.value = clampIndex(currentIndex.value, imageList.value.length)
            }
            val syncImages = ::gen_syncImages_fn
            fun gen_buildPayload_fn(action: String, path: String): UTSJSONObject {
                return _uO("action" to action, "index" to currentIndex.value, "path" to path, "list" to cloneStringArray(imageList.value))
            }
            val buildPayload = ::gen_buildPayload_fn
            fun gen_getCurrentImagePath_fn(): String {
                if (imageList.value.length == 0) {
                    return ""
                }
                if (currentIndex.value < 0 || currentIndex.value >= imageList.value.length) {
                    return ""
                }
                return imageList.value[currentIndex.value]
            }
            val getCurrentImagePath = ::gen_getCurrentImagePath_fn
            fun gen_openPreview_fn(index: Number) {
                if (index < 0 || index >= imageList.value.length) {
                    return
                }
                currentIndex.value = index
                emit("update:index", currentIndex.value)
                emit("update:visible", true)
                val currentPath = getCurrentImagePath()
                emit("preview", buildPayload("preview", currentPath))
                uni_previewImage(PreviewImageOptions(current = currentPath, urls = cloneStringArray(imageList.value), complete = fun(_){
                    emit("update:visible", false)
                    emit("close", buildPayload("close", getCurrentImagePath()))
                }
                ))
            }
            val openPreview = ::gen_openPreview_fn
            fun gen_closePreview_fn() {
                val currentPath = getCurrentImagePath()
                emit("update:visible", false)
                emit("close", buildPayload("close", currentPath))
            }
            val closePreview = ::gen_closePreview_fn
            watch(fun(): UTSArray<String> {
                return props.images
            }
            , fun(newVal: UTSArray<String>){
                syncImages(newVal)
            }
            , WatchOptions(immediate = true))
            watch(fun(): Number {
                return props.initialIndex
            }
            , fun(newVal: Number){
                currentIndex.value = clampIndex(newVal, imageList.value.length)
            }
            , WatchOptions(immediate = true))
            watch(fun(): Boolean {
                return props.visible
            }
            , fun(newVal: Boolean){
                if (newVal) {
                    openPreview(currentIndex.value)
                }
            }
            , WatchOptions(immediate = true))
            return fun(): Any? {
                return _cE("view", _uM("class" to "lp-root"), _uA(
                    if (isTrue(props.showList && imageList.value.length > 0)) {
                        _cE("view", _uM("key" to 0, "class" to "lp-list"), _uA(
                            _cE(Fragment, null, RenderHelpers.renderList(imageList.value, fun(item, index, __index, _cached): Any {
                                return _cE("view", _uM("key" to (item + "-" + index), "class" to "lp-item", "style" to _nS(itemStyle.value), "onClick" to fun(){
                                    openPreview(index)
                                }), _uA(
                                    _cE("image", _uM("class" to "lp-image", "style" to _nS(imageStyle.value), "src" to item, "mode" to "aspectFill"), null, 12, _uA(
                                        "src"
                                    ))
                                ), 12, _uA(
                                    "onClick"
                                ))
                            }), 128)
                        ))
                    } else {
                        if (isTrue(props.showList)) {
                            _cE("view", _uM("key" to 1, "class" to "lp-empty"), _uA(
                                _cE("text", _uM("class" to "lp-empty-text"), _tD(props.emptyText), 1)
                            ))
                        } else {
                            _cC("v-if", true)
                        }
                    }
                ))
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("lp-root" to _pS(_uM("width" to "100%")), "lp-list" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap")), "lp-item" to _pS(_uM("overflow" to "hidden", "backgroundColor" to "#F3F4F6")), "lp-image" to _pS(_uM("width" to "100%", "height" to "100%")), "lp-empty" to _pS(_uM("width" to "100%", "paddingTop" to 16, "paddingBottom" to 16, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "#F8FAFC", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12)), "lp-empty-text" to _pS(_uM("fontSize" to 14, "color" to "#64748B")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("preview" to null, "close" to null, "update:visible" to null, "update:index" to null)
        var props = _nP(_uM("images" to _uM("type" to "Array", "required" to false, "default" to fun(): UTSArray<String> {
            return _uA()
        }
        ), "initialIndex" to _uM("type" to "Number", "required" to false, "default" to 0), "visible" to _uM("type" to "Boolean", "required" to false, "default" to false), "thumbSize" to _uM("type" to "Number", "required" to false, "default" to 72), "radius" to _uM("type" to "Number", "required" to false, "default" to 12), "gap" to _uM("type" to "Number", "required" to false, "default" to 12), "emptyText" to _uM("type" to "String", "required" to false, "default" to "暂无图片"), "showList" to _uM("type" to "Boolean", "required" to false, "default" to true)))
        var propsNeedCastKeys = _uA(
            "images",
            "initialIndex",
            "visible",
            "thumbSize",
            "radius",
            "gap",
            "emptyText",
            "showList"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
