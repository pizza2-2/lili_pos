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
import io.dcloud.uniapp.extapi.downloadFile as uni_downloadFile
import io.dcloud.uniapp.extapi.hideLoading as uni_hideLoading
import io.dcloud.uniapp.extapi.saveImageToPhotosAlbum as uni_saveImageToPhotosAlbum
import io.dcloud.uniapp.extapi.setClipboardData as uni_setClipboardData
import io.dcloud.uniapp.extapi.shareWithSystem as uni_shareWithSystem
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenUniModulesLiliPreviewComponentsLiliPreviewLiliPreview : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var images: UTSArray<String> by `$props`
    open var initialIndex: Number by `$props`
    open var visible: Boolean by `$props`
    open var thumbSize: Number by `$props`
    open var radius: Number by `$props`
    open var gap: Number by `$props`
    open var enableSave: Boolean by `$props`
    open var enableShare: Boolean by `$props`
    open var emptyText: String by `$props`
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
            val previewVisible = ref<Boolean>(false)
            val itemStyle = computed<String>(fun(): String {
                return "width:" + props.thumbSize + "px;height:" + props.thumbSize + "px;margin-right:" + props.gap + "px;margin-bottom:" + props.gap + "px;border-radius:" + props.radius + "px;"
            }
            )
            val imageStyle = computed<String>(fun(): String {
                return "border-radius:" + props.radius + "px;"
            }
            )
            val indicatorText = computed<String>(fun(): String {
                if (imageList.value.length == 0) {
                    return "0 / 0"
                }
                return "" + (currentIndex.value + 1) + " / " + imageList.value.length
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
            fun gen_isRemotePath_fn(path: String): Boolean {
                return path.startsWith("http://") || path.startsWith("https://")
            }
            val isRemotePath = ::gen_isRemotePath_fn
            fun gen_withLocalImagePath_fn(path: String, actionName: String, next: (localPath: String) -> Unit) {
                if (path == "") {
                    uni_showToast(ShowToastOptions(title = "图片地址无效", icon = "none"))
                    return
                }
                if (!isRemotePath(path)) {
                    next(path)
                    return
                }
                uni_showLoading(ShowLoadingOptions(title = if (actionName == "share") {
                    "准备分享"
                } else {
                    "保存中"
                }
                , mask = true))
                uni_downloadFile(DownloadFileOptions(url = path, success = fun(res){
                    uni_hideLoading()
                    if (res.statusCode == 200) {
                        next(res.tempFilePath)
                        return
                    }
                    uni_showToast(ShowToastOptions(title = if (actionName == "share") {
                        "下载后分享失败"
                    } else {
                        "下载失败"
                    }
                    , icon = "none"))
                }
                , fail = fun(_){
                    uni_hideLoading()
                    uni_showToast(ShowToastOptions(title = if (actionName == "share") {
                        "分享失败"
                    } else {
                        "保存失败"
                    }
                    , icon = "none"))
                }
                ))
            }
            val withLocalImagePath = ::gen_withLocalImagePath_fn
            fun gen_openPreview_fn(index: Number) {
                if (index < 0 || index >= imageList.value.length) {
                    return
                }
                currentIndex.value = index
                previewVisible.value = true
                emit("update:index", currentIndex.value)
                emit("update:visible", true)
                val currentPath = getCurrentImagePath()
                emit("preview", buildPayload("preview", currentPath))
            }
            val openPreview = ::gen_openPreview_fn
            fun gen_closePreview_fn() {
                val currentPath = getCurrentImagePath()
                previewVisible.value = false
                emit("update:visible", false)
                emit("close", buildPayload("close", currentPath))
            }
            val closePreview = ::gen_closePreview_fn
            fun gen_handleSwiperChange_fn(event: SwiperChangeEvent) {
                currentIndex.value = event.detail.current
                emit("update:index", currentIndex.value)
            }
            val handleSwiperChange = ::gen_handleSwiperChange_fn
            fun gen_showPrevious_fn() {
                if (imageList.value.length <= 1) {
                    return
                }
                if (currentIndex.value <= 0) {
                    currentIndex.value = imageList.value.length - 1
                    return
                }
                currentIndex.value = currentIndex.value - 1
            }
            val showPrevious = ::gen_showPrevious_fn
            fun gen_showNext_fn() {
                if (imageList.value.length <= 1) {
                    return
                }
                if (currentIndex.value >= imageList.value.length - 1) {
                    currentIndex.value = 0
                    return
                }
                currentIndex.value = currentIndex.value + 1
            }
            val showNext = ::gen_showNext_fn
            fun gen_saveCurrentImage_fn() {
                val currentPath = getCurrentImagePath()
                withLocalImagePath(currentPath, "save", fun(localPath: String){
                    uni_saveImageToPhotosAlbum(SaveImageToPhotosAlbumOptions(filePath = localPath, success = fun(_){
                        uni_showToast(ShowToastOptions(title = "已保存到相册", icon = "success"))
                        emit("save", buildPayload("save", currentPath))
                    }
                    , fail = fun(_){
                        uni_showToast(ShowToastOptions(title = "保存失败", icon = "none"))
                    }
                    ))
                }
                )
            }
            val saveCurrentImage = ::gen_saveCurrentImage_fn
            fun gen_shareCurrentImage_fn() {
                val currentPath = getCurrentImagePath()
                withLocalImagePath(currentPath, "share", fun(localPath: String){
                    uni_shareWithSystem(ShareWithSystemOptions(type = "image", imagePaths = _uA(
                        localPath
                    ), success = fun(_){
                        emit("share", buildPayload("share", currentPath))
                    }
                    , fail = fun(_){
                        uni_setClipboardData(SetClipboardDataOptions(data = currentPath, success = fun(_){
                            uni_showToast(ShowToastOptions(title = "当前平台暂不支持系统分享，已复制图片地址", icon = "none"))
                            emit("share", buildPayload("share", currentPath))
                        }
                        , fail = fun(_){
                            uni_showToast(ShowToastOptions(title = "当前平台暂不支持分享", icon = "none"))
                        }
                        ))
                    }
                    ))
                }
                )
            }
            val shareCurrentImage = ::gen_shareCurrentImage_fn
            watch(fun(): UTSArray<String> {
                return props.images
            }
            , fun(newVal: UTSArray<String>){
                syncImages(newVal)
            }
            )
            watch(fun(): Number {
                return props.initialIndex
            }
            , fun(newVal: Number){
                currentIndex.value = clampIndex(newVal, imageList.value.length)
            }
            )
            watch(fun(): Boolean {
                return props.visible
            }
            , fun(newVal: Boolean){
                previewVisible.value = newVal
            }
            )
            syncImages(props.images)
            currentIndex.value = clampIndex(props.initialIndex, imageList.value.length)
            previewVisible.value = props.visible
            return fun(): Any? {
                return _cE("view", _uM("class" to "lp-root"), _uA(
                    if (imageList.value.length > 0) {
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
                        _cE("view", _uM("key" to 1, "class" to "lp-empty"), _uA(
                            _cE("text", _uM("class" to "lp-empty-text"), _tD(props.emptyText), 1)
                        ))
                    }
                    ,
                    if (isTrue(previewVisible.value)) {
                        _cE("view", _uM("key" to 2, "class" to "lp-preview"), _uA(
                            _cE("view", _uM("class" to "lp-topbar"), _uA(
                                _cE("view", _uM("class" to "lp-topbar-button", "onClick" to closePreview), _uA(
                                    _cE("text", _uM("class" to "lp-topbar-button-text"), "关闭")
                                )),
                                _cE("text", _uM("class" to "lp-indicator"), _tD(indicatorText.value), 1)
                            )),
                            _cE("view", _uM("class" to "lp-swiper-wrap"), _uA(
                                _cE("swiper", _uM("class" to "lp-swiper", "current" to currentIndex.value, "circular" to (imageList.value.length > 1), "onChange" to handleSwiperChange), _uA(
                                    _cE(Fragment, null, RenderHelpers.renderList(imageList.value, fun(item, index, __index, _cached): Any {
                                        return _cE("swiper-item", _uM("key" to ("preview-" + item + "-" + index)), _uA(
                                            _cE("view", _uM("class" to "lp-slide"), _uA(
                                                _cE("image", _uM("class" to "lp-preview-image", "src" to item, "mode" to "aspectFit"), null, 8, _uA(
                                                    "src"
                                                ))
                                            ))
                                        ))
                                    }), 128)
                                ), 40, _uA(
                                    "current",
                                    "circular"
                                ))
                            )),
                            _cE("view", _uM("class" to "lp-actionbar"), _uA(
                                if (imageList.value.length > 1) {
                                    _cE("view", _uM("key" to 0, "class" to "lp-action", "onClick" to showPrevious), _uA(
                                        _cE("text", _uM("class" to "lp-action-text"), "上一张")
                                    ))
                                } else {
                                    _cC("v-if", true)
                                },
                                if (isTrue(props.enableSave)) {
                                    _cE("view", _uM("key" to 1, "class" to "lp-action", "onClick" to saveCurrentImage), _uA(
                                        _cE("text", _uM("class" to "lp-action-text"), "保存")
                                    ))
                                } else {
                                    _cC("v-if", true)
                                },
                                if (isTrue(props.enableShare)) {
                                    _cE("view", _uM("key" to 2, "class" to "lp-action", "onClick" to shareCurrentImage), _uA(
                                        _cE("text", _uM("class" to "lp-action-text"), "分享")
                                    ))
                                } else {
                                    _cC("v-if", true)
                                },
                                if (imageList.value.length > 1) {
                                    _cE("view", _uM("key" to 3, "class" to "lp-action", "onClick" to showNext), _uA(
                                        _cE("text", _uM("class" to "lp-action-text"), "下一张")
                                    ))
                                } else {
                                    _cC("v-if", true)
                                }
                            ))
                        ))
                    } else {
                        _cC("v-if", true)
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
                return _uM("lp-root" to _pS(_uM("width" to "100%")), "lp-list" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap")), "lp-item" to _pS(_uM("overflow" to "hidden", "backgroundColor" to "#F3F4F6")), "lp-image" to _pS(_uM("width" to "100%", "height" to "100%")), "lp-empty" to _pS(_uM("width" to "100%", "paddingTop" to 16, "paddingBottom" to 16, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "#F8FAFC", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12)), "lp-empty-text" to _pS(_uM("fontSize" to 14, "color" to "#64748B")), "lp-preview" to _pS(_uM("position" to "fixed", "left" to 0, "right" to 0, "top" to 0, "bottom" to 0, "backgroundColor" to "rgba(0,0,0,0.96)", "zIndex" to 9999, "paddingTop" to "env(safe-area-inset-top)", "paddingBottom" to "env(safe-area-inset-bottom)")), "lp-topbar" to _pS(_uM("paddingLeft" to 16, "paddingRight" to 16, "paddingTop" to 12, "paddingBottom" to 12, "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between")), "lp-topbar-button" to _pS(_uM("paddingLeft" to 14, "paddingRight" to 14, "paddingTop" to 10, "paddingBottom" to 10, "backgroundColor" to "rgba(255,255,255,0.14)", "borderTopLeftRadius" to 999, "borderTopRightRadius" to 999, "borderBottomRightRadius" to 999, "borderBottomLeftRadius" to 999)), "lp-topbar-button-text" to _pS(_uM("fontSize" to 14, "color" to "#FFFFFF")), "lp-indicator" to _pS(_uM("fontSize" to 14, "color" to "#FFFFFF")), "lp-swiper-wrap" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "lp-swiper" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "lp-slide" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "alignItems" to "center", "justifyContent" to "center", "paddingLeft" to 20, "paddingRight" to 20, "paddingTop" to 120, "paddingBottom" to 140)), "lp-preview-image" to _pS(_uM("width" to "100%", "height" to "100%")), "lp-actionbar" to _pS(_uM("paddingLeft" to 16, "paddingRight" to 16, "paddingTop" to 12, "paddingBottom" to 28, "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "flexWrap" to "wrap")), "lp-action" to _pS(_uM("minWidth" to 88, "marginLeft" to 6, "marginRight" to 6, "marginTop" to 6, "paddingLeft" to 16, "paddingRight" to 16, "paddingTop" to 12, "paddingBottom" to 12, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "rgba(255,255,255,0.14)", "borderTopLeftRadius" to 999, "borderTopRightRadius" to 999, "borderBottomRightRadius" to 999, "borderBottomLeftRadius" to 999)), "lp-action-text" to _pS(_uM("fontSize" to 14, "color" to "#FFFFFF")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("preview" to null, "close" to null, "save" to null, "share" to null, "update:visible" to null, "update:index" to null)
        var props = _nP(_uM("images" to _uM("type" to "Array", "required" to false, "default" to fun(): UTSArray<String> {
            return _uA()
        }
        ), "initialIndex" to _uM("type" to "Number", "required" to false, "default" to 0), "visible" to _uM("type" to "Boolean", "required" to false, "default" to false), "thumbSize" to _uM("type" to "Number", "required" to false, "default" to 72), "radius" to _uM("type" to "Number", "required" to false, "default" to 12), "gap" to _uM("type" to "Number", "required" to false, "default" to 12), "enableSave" to _uM("type" to "Boolean", "required" to false, "default" to true), "enableShare" to _uM("type" to "Boolean", "required" to false, "default" to true), "emptyText" to _uM("type" to "String", "required" to false, "default" to "暂无图片")))
        var propsNeedCastKeys = _uA(
            "images",
            "initialIndex",
            "visible",
            "thumbSize",
            "radius",
            "gap",
            "enableSave",
            "enableShare",
            "emptyText"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
