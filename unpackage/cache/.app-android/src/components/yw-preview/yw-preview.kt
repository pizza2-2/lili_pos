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
import io.dcloud.uniapp.extapi.getSystemInfoSync as uni_getSystemInfoSync
import io.dcloud.uniapp.extapi.saveImageToPhotosAlbum as uni_saveImageToPhotosAlbum
import io.dcloud.uniapp.extapi.setNavigationBarHidden as uni_setNavigationBarHidden
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenComponentsYwPreviewYwPreview : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return _cE("view", null, _uA(
            _cE("view", _uM("class" to "image-preview"), _uA(
                _cE("swiper", _uM("current" to _ctx.currentIndex, "onChange" to _ctx.onSwiperChange, "circular" to "", "class" to "image-swiper"), _uA(
                    _cE(Fragment, null, RenderHelpers.renderList(_ctx.images, fun(image, index, __index, _cached): Any {
                        return _cE("swiper-item", _uM("key" to index), _uA(
                            _cE("view", _uM("class" to "image-container"), _uA(
                                _cE("image", _uM("src" to image, "mode" to "aspect-fit", "style" to _nS(_uM("transform" to ("rotate(" + _ctx.rotateDegrees + "deg) scale(" + _ctx.scale + ")"), "transformOrigin" to "center center")), "onTouchstart" to _ctx.onTouchStart, "onTouchmove" to _ctx.onTouchMove, "onTouchend" to _ctx.onTouchEnd), null, 44, _uA(
                                    "src",
                                    "onTouchstart",
                                    "onTouchmove",
                                    "onTouchend"
                                ))
                            ))
                        ))
                    }
                    ), 128)
                ), 40, _uA(
                    "current",
                    "onChange"
                )),
                _cE("view", _uM("style" to _nS(_uM("position" to "absolute", "top" to "86px"))), _uA(
                    _cE("view", _uM("class" to "close-button", "onClick" to _ctx.closePreview), " × ", 8, _uA(
                        "onClick"
                    )),
                    _cE("view", _uM("class" to "download-button", "style" to _nS(_uM("width" to "80rpx")), "onClick" to withModifiers(_ctx.downloadImage, _uA(
                        "stop"
                    ))), " 下载 ", 12, _uA(
                        "onClick"
                    ))
                ), 4),
                _cE("view", _uM("class" to "control-buttons"), _uA(
                    _cE("image", _uM("src" to "/static/yw-preview/zoom_in.svg", "mode" to "aspect-fit", "onClick" to _ctx.zoomIn, "class" to "control-image"), null, 8, _uA(
                        "onClick"
                    )),
                    _cE("image", _uM("src" to "/static/yw-preview/zoom_out.svg", "mode" to "aspect-fit", "class" to "control-image", "onClick" to _ctx.zoomOut), null, 8, _uA(
                        "onClick"
                    )),
                    _cE("image", _uM("src" to "/static/yw-preview/rotate_right.svg", "mode" to "aspect-fit", "class" to "control-image", "onClick" to _ctx.rotateLeft), null, 8, _uA(
                        "onClick"
                    )),
                    _cE("image", _uM("src" to "/static/yw-preview/rotate_left.svg", "mode" to "aspect-fit", "onClick" to _ctx.rotateRight, "class" to "control-image"), null, 8, _uA(
                        "onClick"
                    ))
                )),
                _cE("view", _uM("class" to "page-indicator"), _tD(_ctx.currentIndex + 1) + " / " + _tD(_ctx.images.length), 1)
            ))
        ))
    }
    open var images: UTSArray<Any?> by `$props`
    open var initialIndex: Number by `$props`
    open var statusBarHeight: Any? by `$data`
    open var currentIndex: Any? by `$data`
    open var rotateDegrees: Number by `$data`
    open var scale: Number by `$data`
    open var isFullScreen: Boolean by `$data`
    open var startX: Number by `$data`
    open var startY: Number by `$data`
    open var startDistance: Number by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return _uM("statusBarHeight" to uni_getSystemInfoSync().statusBarHeight, "currentIndex" to this.initialIndex, "rotateDegrees" to 0, "scale" to 1, "isFullScreen" to false, "startX" to 0, "startY" to 0, "startDistance" to 0)
    }
    open var closePreview = ::gen_closePreview_fn
    open fun gen_closePreview_fn() {
        this.`$emit`("close")
    }
    open var onSwiperChange = ::gen_onSwiperChange_fn
    open fun gen_onSwiperChange_fn(e) {
        this.currentIndex = e.detail.current
    }
    open var rotateLeft = ::gen_rotateLeft_fn
    open fun gen_rotateLeft_fn() {
        this.rotateDegrees -= 90
    }
    open var rotateRight = ::gen_rotateRight_fn
    open fun gen_rotateRight_fn() {
        this.rotateDegrees += 90
    }
    open var zoomIn = ::gen_zoomIn_fn
    open fun gen_zoomIn_fn() {
        if (this.scale < 3) {
            this.scale += 0.1
        }
    }
    open var zoomOut = ::gen_zoomOut_fn
    open fun gen_zoomOut_fn() {
        if (this.scale > 0.5) {
            this.scale -= 0.1
        }
    }
    open var toggleFullScreen = ::gen_toggleFullScreen_fn
    open fun gen_toggleFullScreen_fn() {
        this.isFullScreen = !this.isFullScreen
        if (this.isFullScreen) {
            uni_setNavigationBarHidden(SetNavigationBarHiddenOptions(hidden = true, success = fun(_){}, fail = fun(err){
                console.error("隐藏导航栏失败:", err, " at components/yw-preview/yw-preview.vue:151")
            }))
        } else {
            uni_setNavigationBarHidden(SetNavigationBarHiddenOptions(hidden = false, success = fun(_){}, fail = fun(err){
                console.error("显示导航栏失败:", err, " at components/yw-preview/yw-preview.vue:162")
            }
            ))
        }
    }
    open var downloadImage = ::gen_downloadImage_fn
    open fun gen_downloadImage_fn() {
        console.log("开始下载图片", " at components/yw-preview/yw-preview.vue:169")
        val currentImage = this.images[this.currentIndex]
        uni_downloadFile(DownloadFileOptions(url = currentImage, success = fun(res){
            if (res.statusCode === 200) {
                uni_saveImageToPhotosAlbum(SaveImageToPhotosAlbumOptions(filePath = res.tempFilePath, success = fun(_){
                    console.log("图片保存成功", " at components/yw-preview/yw-preview.vue:178")
                    uni_showToast(ShowToastOptions(title = "下载成功", icon = "success"))
                }, fail = fun(err){
                    console.error("保存图片失败:", err, " at components/yw-preview/yw-preview.vue:185")
                    uni_showToast(ShowToastOptions(title = "保存失败", icon = "none"))
                }))
            } else {
                console.error("下载图片失败:", res.statusCode, " at components/yw-preview/yw-preview.vue:193")
                uni_showToast(ShowToastOptions(title = "下载失败", icon = "none"))
            }
        }
        , fail = fun(err){
            console.error("下载图片失败:", err, " at components/yw-preview/yw-preview.vue:201")
            uni_showToast(ShowToastOptions(title = "下载失败", icon = "none"))
        }
        ))
    }
    open var onTouchStart = ::gen_onTouchStart_fn
    open fun gen_onTouchStart_fn(e) {
        if (e.touches.length === 2) {
            val dx = e.touches[1].clientX - e.touches[0].clientX
            val dy = e.touches[1].clientY - e.touches[0].clientY
            this.startDistance = Math.sqrt(dx * dx + dy * dy)
        } else {
            this.startX = e.touches[0].clientX
            this.startY = e.touches[0].clientY
        }
    }
    open var onTouchMove = ::gen_onTouchMove_fn
    open fun gen_onTouchMove_fn(e) {
        if (e.touches.length === 2) {
            val dx = e.touches[1].clientX - e.touches[0].clientX
            val dy = e.touches[1].clientY - e.touches[0].clientY
            val currentDistance = Math.sqrt(dx * dx + dy * dy)
            val scaleChange = currentDistance / this.startDistance
            this.scale *= scaleChange
            this.startDistance = currentDistance
        }
    }
    open var onTouchEnd = ::gen_onTouchEnd_fn
    open fun gen_onTouchEnd_fn() {}
    companion object {
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("image-preview" to _pS(_uM("position" to "fixed", "top" to 0, "left" to 0, "width" to "100%", "height" to "100%", "backgroundColor" to "rgba(0,0,0,0.8)", "zIndex" to 999, "display" to "flex", "flexDirection" to "column", "justifyContent" to "center", "alignItems" to "center")), "mask" to _pS(_uM("position" to "absolute", "top" to 0, "left" to 0, "width" to "100%", "height" to "100%")), "image-swiper" to _pS(_uM("width" to "100%", "height" to "80%")), "image-container" to _pS(_uM("width" to "100%", "height" to "100%", "display" to "flex", "justifyContent" to "center", "alignItems" to "center")), "page-indicator" to _pS(_uM("position" to "absolute", "top" to 20, "left" to "50%", "transform" to "translateX(-50%)", "color" to "#FFFFFF", "fontSize" to 16)), "close-button" to _pS(_uM("position" to "absolute", "top" to 0, "left" to 140, "color" to "#FFFFFF", "fontSize" to 30)), "download-button" to _pS(_uM("position" to "absolute", "top" to 0, "right" to 100, "color" to "#FFFFFF", "backgroundColor" to "rgba(0,0,0,0.6)", "paddingTop" to 8, "paddingRight" to 16, "paddingBottom" to 8, "paddingLeft" to 16, "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4, "cursor" to "pointer")), "control-buttons" to _pS(_uM("position" to "absolute", "bottom" to 20, "left" to "50%", "transform" to "translateX(-50%)", "display" to "flex", "gap" to "10px", "backgroundImage" to "none", "backgroundColor" to "rgba(0,0,0,0.6)", "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "borderTopLeftRadius" to 100, "borderTopRightRadius" to 100, "borderBottomRightRadius" to 100, "borderBottomLeftRadius" to 100)), "control-image" to _pS(_uM("width" to "44rpx", "height" to "44rpx", "marginTop" to 0, "marginRight" to "18rpx", "marginBottom" to 0, "marginLeft" to "18rpx")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM("images" to _uM("type" to "Array", "default" to fun(): UTSArray<Any?> {
            return _uA()
        }
        ), "initialIndex" to _uM("type" to "Number", "default" to 0)))
        var propsNeedCastKeys = _uA(
            "images",
            "initialIndex"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
