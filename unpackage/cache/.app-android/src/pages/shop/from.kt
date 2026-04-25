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
import io.dcloud.uniapp.extapi.hideLoading as uni_hideLoading
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.reLaunch as uni_reLaunch
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesShopFrom : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesShopFrom) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesShopFrom
            val _cache = __ins.renderCache
            val shopMediaListRefreshStorageKey = "refresh:pages:shop:media"
            val formMode = ref("create")
            val mediaId = ref("")
            val leaveSignal = ref(0)
            val submitting = ref(false)
            val savingVisible = ref(false)
            val savingText = ref("处理中...")
            val initialData = ref<UTSJSONObject>(_uO("shop_id" to "", "shop_name" to "", "title" to "", "record_type" to "general", "expiration_date" to "", "notes" to "", "images" to _uA<String>(), "imageItems" to _uA<UTSJSONObject>()))
            val recordTypeOptions = ref(_uA<SelectOption__5>(SelectOption__5(value = "general", text = "常规文件")))
            fun getStringField(obj: UTSJSONObject, key: String, fallback: String = ""): String {
                val value = obj[key]
                if (value == null) {
                    return fallback
                }
                val text = "" + value
                return if (text == "") {
                    fallback
                } else {
                    text
                }
            }
            fun gen_getArrayField_fn(obj: UTSJSONObject, key: String): UTSArray<String> {
                val value = obj[key]
                if (value == null) {
                    return _uA<String>()
                }
                return value as UTSArray<String>
            }
            val getArrayField = ::gen_getArrayField_fn
            fun gen_parseErrorMessage_fn(error: Any, fallback: String): String {
                var message = fallback
                if (error != null) {
                    val directMessage = (error as UTSError).message
                    if (directMessage != null && directMessage != "") {
                        message = directMessage
                    }
                    val errorText = JSON.stringify(error)
                    if (errorText != null && errorText != "") {
                        val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/shop/from.uvue:102")
                        if (parsedError != null) {
                            val rawMessage = parsedError["message"]
                            if (rawMessage != null) {
                                val parsedMessage = rawMessage as String
                                if (parsedMessage != "") {
                                    message = parsedMessage
                                }
                            }
                        }
                    }
                }
                return message
            }
            val parseErrorMessage = ::gen_parseErrorMessage_fn
            fun gen_buildUploadHeaders_fn(): UTSJSONObject {
                val headers: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("headers", "pages/shop/from.uvue", 118, 8))
                if (authState.token != "") {
                    headers["Authorization"] = authState.token
                }
                return headers
            }
            val buildUploadHeaders = ::gen_buildUploadHeaders_fn
            fun gen_buildSelectResponse_fn(source: UTSArray<SelectOption__5>, params: UTSJSONObject): UTSJSONObject {
                val keyword = getStringField(params, "keyword").toLowerCase()
                val id = getStringField(params, "id")
                val result: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < source.length){
                        val option = source[index]
                        if (id != "" && option.value != id) {
                            index += 1
                            continue
                        }
                        if (keyword != "" && option.text.toLowerCase().indexOf(keyword) < 0) {
                            index += 1
                            continue
                        }
                        result.push(_uO("value" to option.value, "text" to option.text))
                        index += 1
                    }
                }
                return _uO("data" to result, "total" to result.length)
            }
            val buildSelectResponse = ::gen_buildSelectResponse_fn
            fun gen_fetchRecordTypeOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        return@w1 buildSelectResponse(recordTypeOptions.value, params)
                })
            }
            val fetchRecordTypeOptions = ::gen_fetchRecordTypeOptions_fn
            fun gen_buildInitialDataFromMedia_fn(item: ShopMediaItem): UTSJSONObject {
                val images: UTSArray<String> = _uA()
                val imageItems: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < item.media_files.length){
                        val mediaFile = item.media_files[index]
                        var imageUrl = ""
                        if (mediaFile.signed_thumbnail_url != "") {
                            imageUrl = mediaFile.signed_thumbnail_url
                        } else if (mediaFile.thumbnail_url != "") {
                            imageUrl = mediaFile.thumbnail_url
                        } else if (mediaFile.signed_url != "") {
                            imageUrl = mediaFile.signed_url
                        } else if (mediaFile.file_url != "") {
                            imageUrl = mediaFile.file_url
                        }
                        if (imageUrl != "") {
                            images.push(imageUrl)
                            imageItems.push(_uO("id" to mediaFile.id, "path" to imageUrl, "url" to imageUrl, "isRemote" to true))
                        }
                        index += 1
                    }
                }
                return _uO("shop_id" to item.shop.toString(10), "shop_name" to item.shop_name, "title" to item.title, "record_type" to if (item.record_type == "") {
                    "general"
                } else {
                    item.record_type
                }
                , "expiration_date" to item.expiration_date, "notes" to item.notes, "images" to images, "imageItems" to imageItems)
            }
            val buildInitialDataFromMedia = ::gen_buildInitialDataFromMedia_fn
            val formSections = ref(_uA<UTSJSONObject>(_uO("key" to "base", "title" to "基础信息", "description" to "", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "shop_id", "type" to "input", "hidden" to true), _uO("key" to "shop_name", "label" to "所属商店", "type" to "input", "readonly" to true, "placeholder" to "进入页面时自动带入", "required" to true), _uO("key" to "title", "label" to "资料标题", "type" to "input", "required" to true, "placeholder" to "请输入资料标题"), _uO("key" to "record_type", "label" to "资料类型", "type" to "bottomSelect", "title" to "选择资料类型", "placeholder" to "请选择资料类型", "required" to true, "showAddAction" to false, "showEditAction" to false, "fetchData" to fetchRecordTypeOptions), _uO("key" to "expiration_date", "label" to "到期时间", "type" to "datetime", "required" to false, "title" to "选择到期时间", "placeholder" to "请选择到期时间", "showTime" to false), _uO("key" to "notes", "label" to "备注", "type" to "textarea", "placeholder" to "请输入备注"))), _uO("key" to "media", "title" to "图片资料", "description" to "可同时上传多张图片", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "images", "label" to "资料图片", "type" to "upload", "action" to "", "name" to "files", "max" to 9, "uploadText" to "上传图片", "fileItemsKey" to "imageItems", "headers" to buildUploadHeaders(), "formData" to _uO())))))
            val pageTitle = computed(fun(): String {
                return if (formMode.value == "edit") {
                    "编辑商店资料"
                } else {
                    "新建商店资料"
                }
            }
            )
            fun gen_markShopMediaRefreshNeeded_fn(): Unit {
                uni_setStorageSync(shopMediaListRefreshStorageKey, "1")
            }
            val markShopMediaRefreshNeeded = ::gen_markShopMediaRefreshNeeded_fn
            fun gen_loadShopMediaDetailData_fn(idText: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (idText == "") {
                            return@w1
                        }
                        try {
                            val detail = await(getShopMediaDetail(idText))
                            initialData.value = buildInitialDataFromMedia(detail)
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error, "商店资料详情加载失败"), icon = "none"))
                        }
                })
            }
            val loadShopMediaDetailData = ::gen_loadShopMediaDetailData_fn
            fun gen_resolveShopName_fn(shopIdText: String): UTSPromise<String> {
                return wrapUTSPromise(suspend w1@{
                        if (shopIdText == "") {
                            return@w1 ""
                        }
                        try {
                            val detail = await(getShopDetail(shopIdText))
                            return@w1 detail.name
                        }
                         catch (error: Throwable) {
                            return@w1 ""
                        }
                })
            }
            val resolveShopName = ::gen_resolveShopName_fn
            fun gen_buildShopMediaMutationPayload_fn(formDataObject: UTSJSONObject): ShopMediaMutationData {
                val shopIdValue = getStringField(formDataObject, "shop_id")
                val title = getStringField(formDataObject, "title")
                val recordType = getStringField(formDataObject, "record_type", "general")
                val expirationDate = getStringField(formDataObject, "expiration_date")
                val notes = getStringField(formDataObject, "notes")
                return ShopMediaMutationData(shop = if (shopIdValue == "") {
                    null
                } else {
                    shopIdValue
                }
                , title = title, record_type = if (recordType == "") {
                    null
                } else {
                    recordType
                }
                , expiration_date = if (expirationDate == "") {
                    null
                } else {
                    expirationDate
                }
                , notes = if (notes == "") {
                    null
                } else {
                    notes
                }
                )
            }
            val buildShopMediaMutationPayload = ::gen_buildShopMediaMutationPayload_fn
            fun gen_goBackToList_fn(): Unit {
                leaveSignal.value = leaveSignal.value + 1
                setTimeout(fun(){
                    uni_navigateBack(NavigateBackOptions(delta = 1, fail = fun(_){
                        uni_reLaunch(ReLaunchOptions(url = "/pages/shop/index"))
                    }
                    ))
                }
                , 16)
            }
            val goBackToList = ::gen_goBackToList_fn
            fun gen_isRemoteImagePath_fn(path: String): Boolean {
                if (path == "") {
                    return false
                }
                return path.startsWith("http://") || path.startsWith("https://")
            }
            val isRemoteImagePath = ::gen_isRemoteImagePath_fn
            fun gen_collectPendingImagePaths_fn(formDataObject: UTSJSONObject): UTSArray<String> {
                val images = getArrayField(formDataObject, "images")
                val result: UTSArray<String> = _uA()
                run {
                    var index: Number = 0
                    while(index < images.length){
                        val imagePath = images[index]
                        if (imagePath == "") {
                            index += 1
                            continue
                        }
                        if (isRemoteImagePath(imagePath)) {
                            index += 1
                            continue
                        }
                        result.push(imagePath)
                        index += 1
                    }
                }
                return result
            }
            val collectPendingImagePaths = ::gen_collectPendingImagePaths_fn
            fun gen_uploadPendingMediaImages_fn(formDataObject: UTSJSONObject, contentTypeModel: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (mediaId.value == "") {
                            return@w1
                        }
                        val pendingImagePaths = collectPendingImagePaths(formDataObject)
                        if (pendingImagePaths.length == 0) {
                            return@w1
                        }
                        if (contentTypeModel == "") {
                            throw UTSError("缺少上传参数: content_type_model")
                        }
                        val uploadItems: UTSArray<MediaBatchUploadItem> = _uA()
                        run {
                            var index: Number = 0
                            while(index < pendingImagePaths.length){
                                uploadItems.push(MediaBatchUploadItem(filePath = pendingImagePaths[index], name = "files", formData = _uO("content_type_model" to contentTypeModel, "object_id" to mediaId.value)))
                                index += 1
                            }
                        }
                        val uploadResult = await(batchUploadMediaFiles(uploadItems))
                        if (uploadResult.failItems.length > 0) {
                            val firstFail = uploadResult.failItems[0]
                            val failMessage = getStringField(firstFail, "message", "图片上传失败")
                            throw UTSError(failMessage)
                        }
                })
            }
            val uploadPendingMediaImages = ::gen_uploadPendingMediaImages_fn
            fun gen_persistForm_fn(payload: UTSJSONObject): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (submitting.value) {
                            return@w1
                        }
                        val formDataValue = payload["formData"]
                        val data = if (formDataValue == null) {
                            (_uO())
                        } else {
                            (formDataValue as UTSJSONObject)
                        }
                        val shopIdValue = getStringField(data, "shop_id")
                        val title = getStringField(data, "title").trim()
                        if (shopIdValue == "") {
                            uni_showToast(ShowToastOptions(title = "请选择商店", icon = "none"))
                            return@w1
                        }
                        if (title == "") {
                            uni_showToast(ShowToastOptions(title = "资料标题不能为空", icon = "none"))
                            return@w1
                        }
                        submitting.value = true
                        val isEditing = formMode.value == "edit" && mediaId.value != ""
                        val uploadContentTypeModel = getStringField(payload, "uploadContentTypeModel").trim()
                        savingText.value = if (isEditing) {
                            "保存资料中..."
                        } else {
                            "创建资料中..."
                        }
                        savingVisible.value = true
                        uni_showLoading(ShowLoadingOptions(title = savingText.value, mask = true))
                        try {
                            val body = buildShopMediaMutationPayload(data)
                            var successMessage = if (isEditing) {
                                "资料保存成功"
                            } else {
                                "资料创建成功"
                            }
                            if (isEditing) {
                                val updated = await(updateShopMedia(mediaId.value, body))
                                successMessage = takeLatestResponseMessage(successMessage)
                                mediaId.value = updated.id.toString(10)
                                savingText.value = "上传图片中..."
                                await(uploadPendingMediaImages(data, uploadContentTypeModel))
                            } else {
                                val created = await(createShopMedia(body))
                                successMessage = takeLatestResponseMessage(successMessage)
                                mediaId.value = created.id.toString(10)
                                formMode.value = "edit"
                                try {
                                    savingText.value = "上传图片中..."
                                    await(uploadPendingMediaImages(data, uploadContentTypeModel))
                                }
                                 catch (uploadError: Throwable) {
                                    throw UTSError("资料已创建，但图片上传失败")
                                }
                            }
                            markShopMediaRefreshNeeded()
                            uni_showToast(ShowToastOptions(title = successMessage, icon = "success"))
                            goBackToList()
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error, if (isEditing) {
                                "资料保存失败"
                            } else {
                                "资料创建失败"
                            }
                            ), icon = "none"))
                        }
                         finally {
                            savingVisible.value = false
                            uni_hideLoading(null)
                            submitting.value = false
                        }
                })
            }
            val persistForm = ::gen_persistForm_fn
            fun gen_handleSubmit_fn(payload: UTSJSONObject): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        await(persistForm(payload))
                })
            }
            val handleSubmit = ::gen_handleSubmit_fn
            fun gen_handleSaveRequest_fn(payload: UTSJSONObject): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        await(persistForm(payload))
                })
            }
            val handleSaveRequest = ::gen_handleSaveRequest_fn
            fun gen_handleCancel_fn(payload: UTSJSONObject) {
                val hasChangesValue = payload["hasChanges"]
                val changed = hasChangesValue != null && (hasChangesValue as Boolean)
                if (changed) {
                    return
                }
                goBackToList()
            }
            val handleCancel = ::gen_handleCancel_fn
            fun gen_handleDiscardLeave_fn(payload: UTSJSONObject) {
                goBackToList()
            }
            val handleDiscardLeave = ::gen_handleDiscardLeave_fn
            fun gen_handleDirtyChange_fn(value: Boolean) {}
            val handleDirtyChange = ::gen_handleDirtyChange_fn
            fun gen_handleBottomSelectAdd_fn(payload: UTSJSONObject) {
                uni_showToast(ShowToastOptions(title = "当前字段不支持新增", icon = "none"))
            }
            val handleBottomSelectAdd = ::gen_handleBottomSelectAdd_fn
            fun gen_handleBottomSelectEdit_fn(payload: UTSJSONObject) {
                uni_showToast(ShowToastOptions(title = "当前字段不支持编辑", icon = "none"))
            }
            val handleBottomSelectEdit = ::gen_handleBottomSelectEdit_fn
            fun gen_handleUpload_fn(payload: UTSJSONObject) {
                uni_showToast(ShowToastOptions(title = "图片已加入待保存列表", icon = "none"))
            }
            val handleUpload = ::gen_handleUpload_fn
            fun gen_handleUploadDelete_fn(payload: UTSJSONObject) {
                uni_showToast(ShowToastOptions(title = "图片已删除", icon = "success"))
            }
            val handleUploadDelete = ::gen_handleUploadDelete_fn
            fun gen_handleUploadError_fn(payload: UTSJSONObject) {
                val rawPayload = payload["payload"]
                if (rawPayload != null) {
                    val payloadObject = rawPayload as UTSJSONObject
                    val message = getStringField(payloadObject, "message")
                    if (message != "") {
                        uni_showToast(ShowToastOptions(title = message, icon = "none"))
                        return
                    }
                }
                uni_showToast(ShowToastOptions(title = "图片上传失败", icon = "none"))
            }
            val handleUploadError = ::gen_handleUploadError_fn
            fun gen_initializeForm_fn(event: OnLoadOptions): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        leaveSignal.value = 0
                        val idValue = event["id"]
                        val shopIdValue = event["shop_id"]
                        val shopNameValue = event["shop_name"]
                        mediaId.value = if (idValue == null) {
                            ""
                        } else {
                            (idValue as String)
                        }
                        formMode.value = if (mediaId.value == "") {
                            "create"
                        } else {
                            "edit"
                        }
                        if (formMode.value == "edit") {
                            await(loadShopMediaDetailData(mediaId.value))
                            return@w1
                        }
                        val initialShopId = if (shopIdValue == null) {
                            ""
                        } else {
                            (shopIdValue as String)
                        }
                        var initialShopName = ""
                        if (shopNameValue != null) {
                            val decodedShopName = UTSAndroid.consoleDebugError(decodeURIComponent(shopNameValue as String), " at pages/shop/from.uvue:540")
                            initialShopName = if (decodedShopName == null) {
                                ""
                            } else {
                                ("" + decodedShopName)
                            }
                        }
                        if (initialShopName == "" && initialShopId != "") {
                            initialShopName = await(resolveShopName(initialShopId))
                        }
                        if (initialShopId == "") {
                            uni_showToast(ShowToastOptions(title = "请先从商店资料页进入", icon = "none"))
                            setTimeout(fun(){
                                uni_navigateBack(NavigateBackOptions(delta = 1, fail = fun(_){
                                    uni_reLaunch(ReLaunchOptions(url = "/pages/shop/index"))
                                }
                                ))
                            }
                            , 300)
                            return@w1
                        }
                        initialData.value = _uO("shop_id" to initialShopId, "shop_name" to initialShopName, "title" to "", "record_type" to "general", "expiration_date" to "", "notes" to "", "images" to _uA<String>(), "imageItems" to _uA<UTSJSONObject>())
                })
            }
            val initializeForm = ::gen_initializeForm_fn
            onLoad(fun(event: OnLoadOptions){
                initializeForm(event)
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversaForm = resolveEasyComponent("lili-UniversaForm", GenUniModulesLiliUniversaFormComponentsLiliUniversaFormLiliUniversaFormClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to pageTitle.value, "showBack" to true, "showSearch" to false, "showHome" to true, "homePath" to "/pages/shop/index", "backgroundColor" to "#EEF2F7"), null, 8, _uA(
                        "title"
                    )),
                    _cE("view", _uM("class" to "page-content"), _uA(
                        _cV(_component_lili_UniversaForm, _uM("mode" to unref(formMode), "formSections" to unref(formSections), "initialData" to unref(initialData), "leaveSignal" to unref(leaveSignal), "uploadContentTypeModel" to "shopmedia", "onSubmit" to handleSubmit, "onCancel" to handleCancel, "onDiscardLeave" to handleDiscardLeave, "onSaveRequest" to handleSaveRequest, "onDirtyChange" to handleDirtyChange, "onBottomSelectAdd" to handleBottomSelectAdd, "onBottomSelectEdit" to handleBottomSelectEdit, "onUpload" to handleUpload, "onUploadDelete" to handleUploadDelete, "onUploadError" to handleUploadError), null, 8, _uA(
                            "mode",
                            "formSections",
                            "initialData",
                            "leaveSignal"
                        ))
                    )),
                    if (isTrue(unref(savingVisible))) {
                        _cE("view", _uM("key" to 0, "class" to "page-saving-mask"), _uA(
                            _cE("view", _uM("class" to "page-saving-card"), _uA(
                                _cE("text", _uM("class" to "page-saving-text"), _tD(unref(savingText)), 1)
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "position" to "relative", "backgroundColor" to "#EEF2F7")), "page-content" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingLeft" to 0, "paddingRight" to 0, "paddingBottom" to 0)), "page-saving-mask" to _pS(_uM("position" to "absolute", "left" to 0, "top" to 0, "right" to 0, "bottom" to 0, "zIndex" to 9999, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "rgba(15,23,42,0.28)")), "page-saving-card" to _pS(_uM("height" to 44, "paddingLeft" to 16, "paddingRight" to 16, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "rgba(15,23,42,0.86)")), "page-saving-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "16px", "color" to "#FFFFFF")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
