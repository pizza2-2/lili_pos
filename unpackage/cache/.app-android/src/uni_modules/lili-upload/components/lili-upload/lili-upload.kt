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
import io.dcloud.uniapp.extapi.chooseImage as uni_chooseImage
import io.dcloud.uniapp.extapi.hideLoading as uni_hideLoading
import io.dcloud.uniapp.extapi.previewImage as uni_previewImage
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
import io.dcloud.uniapp.extapi.uploadFile as uni_uploadFile
open class GenUniModulesLiliUploadComponentsLiliUploadLiliUpload : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var modelValue: UTSArray<String> by `$props`
    open var fileItems: UTSArray<UTSJSONObject> by `$props`
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
            val imageItems = ref(_uA<UTSJSONObject>())
            val deletePopupVisible = ref<Boolean>(false)
            val pendingDeleteIndex = ref<Number>(-1)
            val deleting = ref<Boolean>(false)
            val uploadingCount = ref<Number>(0)
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
            fun gen_cloneObjectArray_fn(list: UTSArray<UTSJSONObject>): UTSArray<UTSJSONObject> {
                val result: UTSArray<UTSJSONObject> = _uA()
                run {
                    var i: Number = 0
                    while(i < list.length){
                        val item = list[i]
                        val clonedItem: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("clonedItem", "uni_modules/lili-upload/components/lili-upload/lili-upload.uvue", 105, 9))
                        for(key in resolveUTSKeyIterator(item)){
                            clonedItem[key] = item[key]
                        }
                        result.push(clonedItem)
                        i++
                    }
                }
                return result
            }
            val cloneObjectArray = ::gen_cloneObjectArray_fn
            fun getStringField(obj: UTSJSONObject, key: String, fallback: String = ""): String {
                val value = obj[key]
                if (value == null) {
                    return fallback
                }
                return "" + value
            }
            fun buildFileItem(path: String, id: String = ""): UTSJSONObject {
                return _uO("path" to path, "url" to path, "id" to id, "isRemote" to (id != ""))
            }
            fun gen_syncModelValue_fn(list: UTSArray<String>, metaItems: UTSArray<UTSJSONObject>) {
                imageList.value = cloneStringArray(list)
                val nextItems: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < list.length){
                        val currentPath = list[index]
                        var matchedItem: UTSJSONObject? = null
                        run {
                            var metaIndex: Number = 0
                            while(metaIndex < metaItems.length){
                                val metaItem = metaItems[metaIndex]
                                val metaPath = getStringField(metaItem, "path", getStringField(metaItem, "url"))
                                if (metaPath == currentPath) {
                                    matchedItem = metaItem
                                    break
                                }
                                metaIndex++
                            }
                        }
                        if (matchedItem != null) {
                            nextItems.push(buildFileItem(currentPath, getStringField(matchedItem, "id")))
                            index++
                            continue
                        }
                        nextItems.push(buildFileItem(currentPath))
                        index++
                    }
                }
                imageItems.value = nextItems
            }
            val syncModelValue = ::gen_syncModelValue_fn
            fun gen_emitModelValue_fn() {
                emit("update:modelValue", cloneStringArray(imageList.value))
            }
            val emitModelValue = ::gen_emitModelValue_fn
            fun gen_emitFileItems_fn() {
                emit("update:fileItems", cloneObjectArray(imageItems.value))
            }
            val emitFileItems = ::gen_emitFileItems_fn
            fun gen_buildDeletePayload_fn(index: Number, path: String, item: UTSJSONObject, apiDeleted: Boolean): UTSJSONObject {
                return _uO("index" to index, "path" to path, "id" to getStringField(item, "id"), "apiDeleted" to apiDeleted, "list" to cloneStringArray(imageList.value), "fileItems" to cloneObjectArray(imageItems.value))
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
                    return UTSAndroid.consoleDebugError(JSON.parse(text), " at uni_modules/lili-upload/components/lili-upload/lili-upload.uvue:192") as UTSJSONObject
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
            fun gen_beginUploading_fn() {
                val wasUploading = uploadingCount.value > 0
                uploadingCount.value = uploadingCount.value + 1
                if (!wasUploading) {
                    uni_showLoading(ShowLoadingOptions(title = "图片上传中...", mask = true))
                }
            }
            val beginUploading = ::gen_beginUploading_fn
            fun gen_endUploading_fn() {
                if (uploadingCount.value <= 0) {
                    uploadingCount.value = 0
                    return
                }
                uploadingCount.value = uploadingCount.value - 1
                if (uploadingCount.value <= 0) {
                    uploadingCount.value = 0
                    uni_hideLoading(null)
                }
            }
            val endUploading = ::gen_endUploading_fn
            fun gen_uploadImage_fn(path: String, index: Number) {
                console.log("lili-upload uploadImage start:", index, path, " at uni_modules/lili-upload/components/lili-upload/lili-upload.uvue:235")
                beginUploading()
                try {
                    uni_uploadFile(UploadFileOptions(url = props.action, filePath = path, name = props.name, header = props.headers, formData = props.formData, success = fun(res){
                        console.log("lili-upload uploadImage success:", index, path, res.statusCode, " at uni_modules/lili-upload/components/lili-upload/lili-upload.uvue:245")
                        emit("upload", buildUploadPayload(index, path, res.data))
                        endUploading()
                    }
                    , fail = fun(err){
                        val message = err.errMsg
                        console.log("lili-upload uploadImage fail:", index, path, message, " at uni_modules/lili-upload/components/lili-upload/lili-upload.uvue:251")
                        emitError("upload", path, message)
                        endUploading()
                    }
                    ))
                }
                 catch (error: Throwable) {
                    val message = if (error == null) {
                        "上传失败"
                    } else {
                        (error as UTSError).message
                    }
                    emitError("upload", path, if (message == "") {
                        "上传失败"
                    } else {
                        message
                    }
                    )
                    endUploading()
                }
            }
            val uploadImage = ::gen_uploadImage_fn
            fun gen_handleChoose_fn() {
                if (props.disabled) {
                    return
                }
                if (uploadingCount.value > 0) {
                    uni_showToast(ShowToastOptions(title = "图片上传中，请稍后", icon = "none"))
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
                            imageItems.value.push(buildFileItem(selectedPaths[i]))
                            i++
                        }
                    }
                    emitModelValue()
                    emitFileItems()
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
            fun gen_handleDeletePopupVisibleChange_fn(value: Boolean) {
                deletePopupVisible.value = value
            }
            val handleDeletePopupVisibleChange = ::gen_handleDeletePopupVisibleChange_fn
            fun gen_handleDeleteCancel_fn() {
                pendingDeleteIndex.value = -1
            }
            val handleDeleteCancel = ::gen_handleDeleteCancel_fn
            fun gen_removeImageAt_fn(index: Number, apiDeleted: Boolean) {
                if (index < 0 || index >= imageList.value.length) {
                    return
                }
                val currentPath = imageList.value[index]
                val currentItem = if (index < imageItems.value.length) {
                    imageItems.value[index]
                } else {
                    buildFileItem(currentPath)
                }
                imageList.value.splice(index, 1)
                if (index < imageItems.value.length) {
                    imageItems.value.splice(index, 1)
                }
                emitModelValue()
                emitFileItems()
                emit("delete", buildDeletePayload(index, currentPath, currentItem, apiDeleted))
            }
            val removeImageAt = ::gen_removeImageAt_fn
            fun gen_handleDelete_fn(index: Number) {
                if (uploadingCount.value > 0) {
                    uni_showToast(ShowToastOptions(title = "图片上传中，请稍后", icon = "none"))
                    return
                }
                if (index < 0 || index >= imageList.value.length) {
                    return
                }
                pendingDeleteIndex.value = index
                deletePopupVisible.value = true
            }
            val handleDelete = ::gen_handleDelete_fn
            fun gen_confirmDelete_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (deleting.value) {
                            return@w1
                        }
                        val index = pendingDeleteIndex.value
                        if (index < 0 || index >= imageList.value.length) {
                            deletePopupVisible.value = false
                            pendingDeleteIndex.value = -1
                            return@w1
                        }
                        val currentItem = if (index < imageItems.value.length) {
                            imageItems.value[index]
                        } else {
                            buildFileItem(imageList.value[index])
                        }
                        val fileId = getStringField(currentItem, "id")
                        if (fileId == "") {
                            removeImageAt(index, false)
                            deletePopupVisible.value = false
                            pendingDeleteIndex.value = -1
                            return@w1
                        }
                        deleting.value = true
                        try {
                            await(deleteMediaFile(fileId))
                            removeImageAt(index, true)
                            deletePopupVisible.value = false
                            pendingDeleteIndex.value = -1
                        }
                         catch (error: Throwable) {
                            val message = if (error == null) {
                                "删除图片失败"
                            } else {
                                (error as UTSError).message
                            }
                            emitError("delete", imageList.value[index], if (message == "") {
                                "删除图片失败"
                            } else {
                                message
                            }
                            )
                        }
                         finally {
                            deleting.value = false
                        }
                })
            }
            val confirmDelete = ::gen_confirmDelete_fn
            watch(fun(): UTSArray<String> {
                return props.modelValue
            }
            , fun(newVal: UTSArray<String>){
                syncModelValue(newVal, props.fileItems)
            }
            )
            watch(fun(): UTSArray<UTSJSONObject> {
                return props.fileItems
            }
            , fun(newVal: UTSArray<UTSJSONObject>){
                syncModelValue(props.modelValue, newVal)
            }
            )
            onMounted(fun(){
                syncModelValue(props.modelValue, props.fileItems)
            }
            )
            onUnmounted(fun(){
                if (uploadingCount.value > 0) {
                    uploadingCount.value = 0
                    uni_hideLoading(null)
                }
            }
            )
            return fun(): Any? {
                return _cE("view", _uM("class" to "lu-root"), _uA(
                    _cE("view", _uM("class" to "lu-list"), _uA(
                        _cE(Fragment, null, RenderHelpers.renderList(unref(imageList), fun(item, index, __index, _cached): Any {
                            return _cE("view", _uM("key" to (item + "-" + index), "class" to "lu-item"), _uA(
                                _cE("image", _uM("class" to "lu-image", "src" to item, "mode" to "aspectFill", "onClick" to fun(){
                                    handlePreview(index)
                                }
                                ), null, 8, _uA(
                                    "src",
                                    "onClick"
                                )),
                                if (isTrue(!props.disabled && unref(uploadingCount) <= 0)) {
                                    _cE("view", _uM("key" to 0, "class" to "lu-delete", "onClick" to fun(){
                                        handleDelete(index)
                                    }), _uA(
                                        _cE("text", _uM("class" to "lu-delete-text"), "×")
                                    ), 8, _uA(
                                        "onClick"
                                    ))
                                } else {
                                    _cC("v-if", true)
                                }
                            ))
                        }
                        ), 128),
                        if (isTrue(!props.disabled && unref(uploadingCount) <= 0 && unref(imageList).length < props.max)) {
                            _cE("view", _uM("key" to 0, "class" to "lu-item lu-item-add", "onClick" to handleChoose), _uA(
                                _cE("text", _uM("class" to "lu-add-icon"), "+"),
                                _cE("text", _uM("class" to "lu-add-text"), _tD(props.uploadText), 1)
                            ))
                        } else {
                            _cC("v-if", true)
                        }
                    )),
                    if (unref(uploadingCount) > 0) {
                        _cE("view", _uM("key" to 0, "class" to "lu-uploading-mask"), _uA(
                            _cE("text", _uM("class" to "lu-uploading-text"), "图片上传中...")
                        ))
                    } else {
                        _cC("v-if", true)
                    }
                    ,
                    _cV(unref(GenUniModulesLiliPopupComponentsLiliPopupLiliPopupClass), _uM("visible" to unref(deletePopupVisible), "title" to "删除图片", "content" to "确定删除这张图片吗？", "cancelText" to "取消", "confirmText" to "删除", "confirmDanger" to true, "onUpdate:visible" to handleDeletePopupVisibleChange, "onCancel" to handleDeleteCancel, "onConfirm" to confirmDelete), null, 8, _uA(
                        "visible"
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
                return _uM("lu-root" to _pS(_uM("width" to "100%", "position" to "relative")), "lu-list" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap")), "lu-item" to _pS(_uM("width" to 88, "height" to 88, "marginRight" to 12, "marginBottom" to 12, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "position" to "relative", "overflow" to "hidden", "backgroundColor" to "#F3F4F6")), "lu-image" to _pS(_uM("width" to 88, "height" to 88, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8)), "lu-item-add" to _pS(_uM("alignItems" to "center", "justifyContent" to "center", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "dashed", "borderRightStyle" to "dashed", "borderBottomStyle" to "dashed", "borderLeftStyle" to "dashed", "borderTopColor" to "#D1D5DB", "borderRightColor" to "#D1D5DB", "borderBottomColor" to "#D1D5DB", "borderLeftColor" to "#D1D5DB", "backgroundColor" to "#FAFAFA")), "lu-delete" to _pS(_uM("position" to "absolute", "top" to 0, "right" to 0, "width" to 24, "height" to 24, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "rgba(17,24,39,0.72)", "borderBottomLeftRadius" to 8)), "lu-delete-text" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 16, "lineHeight" to "16px")), "lu-add-icon" to _pS(_uM("color" to "#6B7280", "fontSize" to 28, "lineHeight" to "28px")), "lu-add-text" to _pS(_uM("marginTop" to 6, "color" to "#6B7280", "fontSize" to 12, "lineHeight" to "12px")), "lu-uploading-mask" to _pS(_uM("position" to "absolute", "left" to 0, "top" to 0, "right" to 0, "bottom" to 0, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "rgba(15,23,42,0.38)")), "lu-uploading-text" to _pS(_uM("color" to "#FFFFFF", "fontSize" to 13, "lineHeight" to "16px", "paddingTop" to 8, "paddingRight" to 12, "paddingBottom" to 8, "paddingLeft" to 12, "borderTopLeftRadius" to 999, "borderTopRightRadius" to 999, "borderBottomRightRadius" to 999, "borderBottomLeftRadius" to 999, "backgroundColor" to "rgba(15,23,42,0.72)")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("update:modelValue" to null, "update:fileItems" to null, "upload" to null, "delete" to null, "preview" to null, "error" to null)
        var props = _nP(_uM("modelValue" to _uM("type" to "Array", "required" to false, "default" to fun(): UTSArray<String> {
            return _uA()
        }
        ), "fileItems" to _uM("type" to "Array", "required" to false, "default" to fun(): UTSArray<UTSJSONObject> {
            return _uA()
        }
        ), "action" to _uM("type" to "String", "required" to false, "default" to ""), "name" to _uM("type" to "String", "required" to false, "default" to "file"), "headers" to _uM("type" to "UTSJSONObject", "required" to false, "default" to fun(): UTSJSONObject {
            return _uO()
        }
        ), "formData" to _uM("type" to "UTSJSONObject", "required" to false, "default" to fun(): UTSJSONObject {
            return _uO()
        }
        ), "max" to _uM("type" to "Number", "required" to false, "default" to 9), "disabled" to _uM("type" to "Boolean", "required" to false, "default" to false), "previewEnabled" to _uM("type" to "Boolean", "required" to false, "default" to true), "uploadText" to _uM("type" to "String", "required" to false, "default" to "上传图片")))
        var propsNeedCastKeys = _uA(
            "modelValue",
            "fileItems",
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
