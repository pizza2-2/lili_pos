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
open class GenPagesIndexPermission : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesIndexPermission) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesIndexPermission
            val _cache = __ins.renderCache
            val location = ref<GetLocationSuccess?>(null)
            fun gen_getLocation_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        try {
                            location.value = await(getLocationAsync())
                            console.log(location, " at pages/index/permission.uvue:59")
                        }
                         catch (error: Throwable) {
                            console.log(error, " at pages/index/permission.uvue:62")
                        }
                })
            }
            val getLocation = ::gen_getLocation_fn
            val image = ref("")
            fun gen_getCamera_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        try {
                            val imageInfo = await(getCameraAsync())
                            console.log(imageInfo, " at pages/index/permission.uvue:71")
                            image.value = imageInfo.tempFilePaths[0]
                        }
                         catch (error: Throwable) {
                            console.log(error, " at pages/index/permission.uvue:75")
                        }
                })
            }
            val getCamera = ::gen_getCamera_fn
            fun gen_getAlbum_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        try {
                            val imageInfo = await(getAlbumAsync(1))
                            console.log(imageInfo, " at pages/index/permission.uvue:82")
                            image.value = imageInfo.tempFilePaths[0]
                        }
                         catch (error: Throwable) {
                            console.log(error, " at pages/index/permission.uvue:86")
                        }
                })
            }
            val getAlbum = ::gen_getAlbum_fn
            return fun(): Any? {
                return _cE("scroll-view", _uM("class" to "flex-ai-c", "style" to _nS(_uM("flex" to "1"))), _uA(
                    _cE("text", _uM("class" to "m-20 bg-grey p-20"), _uA(
                        _cE("text", _uM("class" to "color-grey fs-28"), " 获取权限代码封装在了 "),
                        _cE("text", _uM("class" to "color-red fs-28"), " /pkg/util/osPermission.uts "),
                        _cE("text", _uM("class" to "color-grey fs-28"), " 文件中。一定要在 "),
                        _cE("text", _uM("class" to "color-red fs-28"), " manifest.json -> 安卓/iOS/鸿蒙 App配置 "),
                        _cE("text", _uM("class" to "color-grey fs-28"), " 中配置所需要的权限。用不到的权限不要配置。安卓端使用某些权限时要在 "),
                        _cE("text", _uM("class" to "color-red fs-28"), " 额外添加的权限 "),
                        _cE("text", _uM("class" to "color-grey fs-28"), " 中添加对应的权限。详见下方的文档。 "),
                        _cE("text", _uM("class" to "color-red fs-28"), " 在用户未同意隐私政策之前，不要调用任何权限API，否则审核不通过。 ")
                    )),
                    _cE("button", _uM("type" to "primary", "onClick" to getLocation), "获取定位"),
                    if (isTrue(unref(location))) {
                        _cE("text", _uM("key" to 0), "获取的经纬度：" + _tD(unref(location)?.latitude) + " -- " + _tD(unref(location)?.longitude), 1)
                    } else {
                        _cC("v-if", true)
                    }
                    ,
                    _cE("button", _uM("type" to "primary", "class" to "m-y-10", "onClick" to getCamera), "获取相机"),
                    _cE("button", _uM("type" to "primary", "onClick" to getAlbum), "获取相册"),
                    _cE("image", _uM("src" to unref(image), "mode" to "heightFix", "style" to _nS(_uM("height" to "400rpx"))), null, 12, _uA(
                        "src"
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
