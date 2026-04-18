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
import io.dcloud.uniapp.extapi.chooseImage as uni_chooseImage
import io.dcloud.uniapp.extapi.previewImage as uni_previewImage
import io.dcloud.uniapp.extapi.showToast as uni_showToast
import io.dcloud.uniapp.extapi.uploadFile as uni_uploadFile
open class GenUniModulesLiliUploadComponentsLiliUploadLiliUpload : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var modelValue: UTSArray<String> by `$props`
    open var action: String by `$props`
    open var name: String by `$props`
    open var headers: UTSJSONObject by `$props`
    open var formData: UTSJSONObject by `$props`
    open var max: Number by `$props`
    open var disabled: Boolean by `$props`
    open var previewEnabled: Boolean by `$props`
    open var uploadText: String by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenUniModulesLiliUploadComponentsLiliUploadLiliUpload) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenUniModulesLiliUploadComponentsLiliUploadLiliUpload
            val _cache = __ins.renderCache
            val props = __props
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val imageList = ref(_uA<String>())
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
            fun gen_syncModelValue_fn(list: UTSArray<String>) {
                imageList.value = cloneStringArray(list)
            }
            val syncModelValue = ::gen_syncModelValue_fn
            fun gen_emitModelValue_fn() {
                emit("update:modelValue", cloneStringArray(imageList.value))
            }
            val emitModelValue = ::gen_emitModelValue_fn
            fun gen_buildDeletePayload_fn(index: Number, path: String): UTSJSONObject {
                return _uO("index" to index, "path" to path, "list" to cloneStringArray(imageList.value))
            }
            val buildDeletePayload = ::gen_buildDeletePayload_fn
            fun gen_buildPreviewPayload_fn(index: Number, path: String): UTSJSONObject {
                return _uO("index" to index, "path" to path, "list" to cloneStringArray(imageList.value))
            }
            val buildPreviewPayload = ::gen_buildPreviewPayload_fn
            fun gen_buildErrorPayload_fn(type: String, path: String, message: String): UTSJSONObject {
                return _uO("type" to type, "path" to path, "message" to message)
            }
            val buildErrorPayload = ::gen_buildErrorPayload_fn
            fun gen_tryParseResponse_fn(text: String): UTSJSONObject? {
                if (text == "") {
                    return null
                }
                try {
                    return UTSAndroid.consoleDebugError(JSON.parse(text), " at uni_modules/lili-upload/components/lili-upload/lili-upload.uvue:112") as UTSJSONObject
                }
                 catch (e: Throwable) {
                    return null
                }
            }
            val tryParseResponse = ::gen_tryParseResponse_fn
            fun gen_buildUploadPayload_fn(index: Number, path: String, responseText: String): UTSJSONObject {
                return _uO("index" to index, "path" to path, "responseText" to responseText, "response" to tryParseResponse(responseText))
            }
            val buildUploadPayload = ::gen_buildUploadPayload_fn
            fun gen_emitError_fn(type: String, path: String, message: String) {
                emit("error", buildErrorPayload(type, path, message))
            }
            val emitError = ::gen_emitError_fn
            fun gen_uploadImage_fn(path: String, index: Number) {
                uni_uploadFile(UploadFileOptions(url = props.action, filePath = path, name = props.name, header = props.headers, formData = props.formData, success = fun(res){
                    emit("upload", buildUploadPayload(index, path, res.data))
                }
                , fail = fun(err){
                    val message = err.errMsg
                    emitError("upload", path, message)
                }
                ))
            }
            val uploadImage = ::gen_uploadImage_fn
            fun gen_handleChoose_fn() {
                if (props.disabled) {
                    return
                }
                val remain = props.max - imageList.value.length
                if (remain <= 0) {
                    uni_showToast(ShowToastOptions(title = "已达到最大数量", icon = "none"))
                    return
                }
                uni_chooseImage(ChooseImageOptions(count = remain, sizeType = _uA(
                    "compressed",
                    "original"
                ), sourceType = _uA(
                    "album",
                    "camera"
                ), success = fun(res){
                    val selectedPaths = res.tempFilePaths
                    if (selectedPaths.length == 0) {
                        return
                    }
                    run {
                        var i: Number = 0
                        while(i < selectedPaths.length){
                            imageList.value.push(selectedPaths[i])
                            i++
                        }
                    }
                    emitModelValue()
                    if (props.action != "") {
                        run {
                            var i: Number = 0
                            while(i < selectedPaths.length){
                                val selectedPath = selectedPaths[i]
                                val currentIndex = imageList.value.indexOf(selectedPath)
                                if (currentIndex >= 0) {
                                    uploadImage(selectedPath, currentIndex)
                                }
                                i++
                            }
                        }
                    }
                }
                , fail = fun(err){
                    val message = err.errMsg
                    emitError("choose", "", message)
                }
                ))
            }
            val handleChoose = ::gen_handleChoose_fn
            fun gen_handlePreview_fn(index: Number) {
                if (!props.previewEnabled) {
                    return
                }
                if (index < 0 || index >= imageList.value.length) {
                    return
                }
                val currentPath = imageList.value[index]
                emit("preview", buildPreviewPayload(index, currentPath))
                uni_previewImage(PreviewImageOptions(current = currentPath, urls = cloneStringArray(imageList.value)))
            }
            val handlePreview = ::gen_handlePreview_fn
            fun gen_handleDelete_fn(index: Number) {
                if (index < 0 || index >= imageList.value.length) {
                    return
                }
                val currentPath = imageList.value[index]
                imageList.value.splice(index, 1)
                emitModelValue()
                emit("delete", buildDeletePayload(index, currentPath))
            }
            val handleDelete = ::gen_handleDelete_fn
            watch(fun(): UTSArray<String> {
                return props.modelValue
            }
            , fun(newVal: UTSArray<String>){
                syncModelValue(newVal)
            }
            )
            onMounted(fun(){
                syncModelValue(props.modelValue)
            }
            )
            return fun(): Any? {
                return _cE("view", _uM("class" to "lu-root"), _uA(
                    _cE("view", _uM("class" to "lu-list"), _uA(
                        _cE(Fragment, null, RenderHelpers.renderList(unref(imageList), fun(item, index, __index, _cached): Any {
                            return _cE("view", _uM("key" to (item + "-" + index), "class" to "lu-item", "onClick" to fun(){
                                handlePreview(index)
                            }
                            ), _uA(
                                _cE("image", _uM("class" to "lu-image", "src" to item, "mode" to "aspectFill"), null, 8, _uA(
                                    "src"
                                )),
                                if (isTrue(!props.disabled)) {
                                    _cE("view", _uM("key" to 0, "class" to "lu-delete", "onClick" to withModifiers(fun(){
                                        handleDelete(index)
                                    }, _uA(
                                        "stop"
                                    ))), _uA(
                                        _cE("text", _uM("class" to "lu-delete-text"), "×")
                                    ), 8, _uA(
                                        "onClick"
                                    ))
                                } else {
                                    _cC("v-if", true)
                                }
                            ), 8, _uA(
                                "onClick"
                            ))
                        }
                        ), 128),
                        if (isTrue(!props.disabled && unref(imageList).length < props.max)) {
                            _cE("view", _uM("key" to 0, "class" to "lu-item lu-item-add", "onClick" to handleChoose), _uA(
                                _cE("text", _uM("class" to "lu-add-icon"), "+"),
                                _cE("text", _uM("class" to "lu-add-text"), _tD(props.uploadText), 1)
                            ))
                        } else {
                            _cC("v-if", true)
                        }
                    ))
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
                return _uM("lu-root" to _pS(_uM("width" to "100%")), "lu-list" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap")), "lu-item" to _pS(_uM("width" to 88, "height" to 88, "marginRight" to 12, "marginBottom" to 12, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "position" to "relative", "overflow" to "hidden", "backgroundColor" to "#F3F4F6")), "lu-image" to _pS(_uM("width" to 88, "height" to 88, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8)), "lu-item-add" to _pS(_uM("alignItems" to "center", "justifyContent" to "center", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "dashed", "borderRightStyle" to "dashed", "borderBottomStyle" to "dashed", "borderLeftStyle" to "dashed", "borderTopColor" to "#D1D5DB", "borderRightColor" to "#D1D5DB", "borderBottomColor" to "#D1D5DB", "borderLeftColor" to "#D1D5DB", "backgroundColor" to "#FAFAFA")), "lu-delete" to _pS(_uM("position" to "absolute", "top" to 0, "right" to 0, "width" to 24, "height" to 24, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "rgba(17,24,39,0.72)", "borderBottomLeftRadius" to 8)), "lu-delete-text" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 16, "lineHeight" to "16px")), "lu-add-icon" to _pS(_uM("color" to "#6B7280", "fontSize" to 28, "lineHeight" to "28px")), "lu-add-text" to _pS(_uM("marginTop" to 6, "color" to "#6B7280", "fontSize" to 12, "lineHeight" to "12px")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("update:modelValue" to null, "upload" to null, "delete" to null, "preview" to null, "error" to null)
        var props = _nP(_uM("modelValue" to _uM("type" to "Array", "required" to false, "default" to fun(): UTSArray<String> {
            return _uA()
        }
        ), "action" to _uM("type" to "String", "required" to false, "default" to ""), "name" to _uM("type" to "String", "required" to false, "default" to "file"), "headers" to _uM("type" to "UTSJSONObject", "required" to false, "default" to fun(): UTSJSONObject {
            return UTSJSONObject()
        }
        ), "formData" to _uM("type" to "UTSJSONObject", "required" to false, "default" to fun(): UTSJSONObject {
            return UTSJSONObject()
        }
        ), "max" to _uM("type" to "Number", "required" to false, "default" to 9), "disabled" to _uM("type" to "Boolean", "required" to false, "default" to false), "previewEnabled" to _uM("type" to "Boolean", "required" to false, "default" to true), "uploadText" to _uM("type" to "String", "required" to false, "default" to "上传图片")))
        var propsNeedCastKeys = _uA(
            "modelValue",
            "action",
            "name",
            "headers",
            "formData",
            "max",
            "disabled",
            "previewEnabled",
            "uploadText"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
