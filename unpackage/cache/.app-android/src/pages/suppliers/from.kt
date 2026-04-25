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
open class GenPagesSuppliersFrom : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesSuppliersFrom) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesSuppliersFrom
            val _cache = __ins.renderCache
            val supplierListRefreshStorageKey = "refresh:pages:suppliers:index"
            val formMode = ref("create")
            val supplierId = ref("")
            val leaveSignal = ref(0)
            val submitting = ref(false)
            val savingVisible = ref(false)
            val savingText = ref("处理中...")
            val initialData = ref<UTSJSONObject>(_uO("code" to "", "name" to "", "contact" to "", "phone" to "", "address" to "", "description" to "", "is_active" to "true", "images" to _uA<String>(), "imageItems" to _uA<UTSJSONObject>()))
            val statusOptions = ref(_uA<SelectOption>(SelectOption(value = "true", text = "启用"), SelectOption(value = "false", text = "停用")))
            fun getStringField(obj: UTSJSONObject, key: String, fallback: String = ""): String {
                val value = obj[key]
                if (value == null) {
                    return fallback
                }
                return "" + value
            }
            fun gen_getArrayField_fn(obj: UTSJSONObject, key: String): UTSArray<String> {
                val value = obj[key]
                if (value == null) {
                    return _uA()
                }
                return value as UTSArray<String>
            }
            val getArrayField = ::gen_getArrayField_fn
            fun gen_cloneStringArray_fn(list: UTSArray<String>): UTSArray<String> {
                val result: UTSArray<String> = _uA()
                run {
                    var index: Number = 0
                    while(index < list.length){
                        result.push(list[index])
                        index += 1
                    }
                }
                return result
            }
            val cloneStringArray = ::gen_cloneStringArray_fn
            fun gen_buildInitialDataFromSupplier_fn(item: SupplierItem): UTSJSONObject {
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
                return _uO("code" to item.code, "name" to item.name, "contact" to item.contact, "phone" to item.phone, "address" to item.address, "description" to if (item.description == null) {
                    ""
                } else {
                    item.description
                }
                , "is_active" to if (item.is_active) {
                    "true"
                } else {
                    "false"
                }
                , "images" to images, "imageItems" to imageItems)
            }
            val buildInitialDataFromSupplier = ::gen_buildInitialDataFromSupplier_fn
            fun gen_buildUploadHeaders_fn(): UTSJSONObject {
                val headers: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("headers", "pages/suppliers/from.uvue", 136, 8))
                if (authState.token != "") {
                    headers["Authorization"] = authState.token
                }
                return headers
            }
            val buildUploadHeaders = ::gen_buildUploadHeaders_fn
            fun gen_parseErrorMessage_fn(error: Any, fallback: String): String {
                var message = fallback
                if (error != null) {
                    val directMessage = (error as UTSError).message
                    if (directMessage != null && directMessage != "") {
                        message = directMessage
                    }
                    val errorText = JSON.stringify(error)
                    if (errorText != null && errorText != "") {
                        val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/suppliers/from.uvue:152")
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
            fun gen_buildSelectResponse_fn(source: UTSArray<SelectOption>, params: UTSJSONObject): UTSJSONObject {
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
            fun gen_fetchStatusOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        return@w1 buildSelectResponse(statusOptions.value, params)
                })
            }
            val fetchStatusOptions = ::gen_fetchStatusOptions_fn
            val formSections = ref(_uA<UTSJSONObject>(_uO("key" to "base", "title" to "基础信息", "description" to "。", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "name", "label" to "供应商名称", "type" to "input", "required" to true, "placeholder" to "请输入供应商名称"), _uO("key" to "code", "label" to "供应商编码", "type" to "input", "placeholder" to "请输入编码"), _uO("key" to "contact", "label" to "联系人", "type" to "input", "placeholder" to "请输入联系人"), _uO("key" to "phone", "label" to "联系电话", "type" to "input", "placeholder" to "请输入联系电话"), _uO("key" to "address", "label" to "地址", "type" to "textarea", "placeholder" to "请输入地址"), _uO("key" to "description", "label" to "备注", "type" to "textarea", "placeholder" to "请输入备注"))), _uO("key" to "status", "title" to "状态设置", "description" to "", "defaultOpen" to false, "fields" to _uA<UTSJSONObject>(_uO("key" to "is_active", "label" to "启用状态", "type" to "bottomSelect", "title" to "选择启用状态", "placeholder" to "请选择启用状态", "showAddAction" to false, "showEditAction" to false, "fetchData" to fetchStatusOptions))), _uO("key" to "media", "title" to "图片资料", "description" to "可同时上传多张图片", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "images", "label" to "供应商图片", "type" to "upload", "action" to "", "name" to "files", "max" to 6, "uploadText" to "上传图片", "fileItemsKey" to "imageItems", "headers" to buildUploadHeaders(), "formData" to _uO())))))
            val pageTitle = computed(fun(): String {
                return if (formMode.value == "edit") {
                    "编辑供应商"
                } else {
                    "新建供应商"
                }
            }
            )
            fun gen_clearDraftStorage_fn() {}
            val clearDraftStorage = ::gen_clearDraftStorage_fn
            fun gen_loadSupplierDetailData_fn(idText: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (idText == "") {
                            return@w1
                        }
                        try {
                            val detail = await(getSupplierDetail(idText))
                            console.log(detail, " at pages/suppliers/from.uvue:293")
                            initialData.value = buildInitialDataFromSupplier(detail)
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error, "供应商详情加载失败"), icon = "none"))
                        }
                })
            }
            val loadSupplierDetailData = ::gen_loadSupplierDetailData_fn
            fun gen_goBackToList_fn() {
                leaveSignal.value = leaveSignal.value + 1
                setTimeout(fun(){
                    uni_navigateBack(NavigateBackOptions(delta = 1, fail = fun(_){
                        uni_reLaunch(ReLaunchOptions(url = "/pages/suppliers/index"))
                    }
                    ))
                }
                , 16)
            }
            val goBackToList = ::gen_goBackToList_fn
            fun gen_markSupplierListRefreshNeeded_fn() {
                uni_setStorageSync(supplierListRefreshStorageKey, "1")
            }
            val markSupplierListRefreshNeeded = ::gen_markSupplierListRefreshNeeded_fn
            fun gen_stringifyPayload_fn(payload: UTSJSONObject): String {
                val text = JSON.stringify(payload)
                if (text == null) {
                    return ""
                }
                return text
            }
            val stringifyPayload = ::gen_stringifyPayload_fn
            fun gen_buildSupplierMutationPayload_fn(formDataObject: UTSJSONObject): SupplierMutationData {
                val name = getStringField(formDataObject, "name")
                val codeValue = getStringField(formDataObject, "code")
                val contactValue = getStringField(formDataObject, "contact")
                val phoneValue = getStringField(formDataObject, "phone")
                val addressValue = getStringField(formDataObject, "address")
                val descriptionValue = getStringField(formDataObject, "description")
                val activeText = getStringField(formDataObject, "is_active", "true")
                return SupplierMutationData(code = if (codeValue == "") {
                    null
                } else {
                    codeValue
                }
                , name = name, address = if (addressValue == "") {
                    null
                } else {
                    addressValue
                }
                , phone = if (phoneValue == "") {
                    null
                } else {
                    phoneValue
                }
                , contact = if (contactValue == "") {
                    null
                } else {
                    contactValue
                }
                , description = if (descriptionValue == "") {
                    null
                } else {
                    descriptionValue
                }
                , is_active = activeText == "true", company_infos = null)
            }
            val buildSupplierMutationPayload = ::gen_buildSupplierMutationPayload_fn
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
            fun gen_uploadPendingSupplierImages_fn(formDataObject: UTSJSONObject, contentTypeModel: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (supplierId.value == "") {
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
                                uploadItems.push(MediaBatchUploadItem(filePath = pendingImagePaths[index], name = "files", formData = _uO("content_type_model" to contentTypeModel, "object_id" to supplierId.value)))
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
            val uploadPendingSupplierImages = ::gen_uploadPendingSupplierImages_fn
            fun gen_persistForm_fn(payload: UTSJSONObject, fromPrompt: Boolean): UTSPromise<Unit> {
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
                        val uploadContentTypeModel = getStringField(payload, "uploadContentTypeModel").trim()
                        val actionText = if (formMode.value == "edit") {
                            "保存修改"
                        } else {
                            "创建供应商"
                        }
                        submitting.value = true
                        savingText.value = actionText + "中..."
                        savingVisible.value = true
                        uni_showLoading(ShowLoadingOptions(title = savingText.value, mask = true))
                        try {
                            val body = buildSupplierMutationPayload(data)
                            var successMessage = actionText + "成功"
                            if (formMode.value == "edit" && supplierId.value != "") {
                                savingText.value = "上传图片中..."
                                await(uploadPendingSupplierImages(data, uploadContentTypeModel))
                                savingText.value = "保存修改中..."
                                await(updateSupplier(supplierId.value, body))
                                successMessage = takeLatestResponseMessage(successMessage)
                            } else {
                                savingText.value = "创建供应商中..."
                                val createdSupplier = await(createSupplier(body))
                                successMessage = takeLatestResponseMessage(successMessage)
                                supplierId.value = createdSupplier.id.toString(10)
                                try {
                                    savingText.value = "上传图片中..."
                                    await(uploadPendingSupplierImages(data, uploadContentTypeModel))
                                }
                                 catch (uploadError: Throwable) {
                                    throw UTSError("供应商已创建，但图片上传失败")
                                }
                            }
                            clearDraftStorage()
                            markSupplierListRefreshNeeded()
                            uni_showToast(ShowToastOptions(title = successMessage, icon = "success"))
                            goBackToList()
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error, actionText + "失败"), icon = "none"))
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
                        await(persistForm(payload, false))
                })
            }
            val handleSubmit = ::gen_handleSubmit_fn
            fun gen_handleSaveRequest_fn(payload: UTSJSONObject): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        await(persistForm(payload, true))
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
                clearDraftStorage()
                goBackToList()
            }
            val handleDiscardLeave = ::gen_handleDiscardLeave_fn
            fun gen_handleDirtyChange_fn(value: Boolean) {}
            val handleDirtyChange = ::gen_handleDirtyChange_fn
            fun gen_handleBottomSelectAdd_fn(payload: UTSJSONObject) {
                uni_showToast(ShowToastOptions(title = "状态字段不支持新增", icon = "none"))
            }
            val handleBottomSelectAdd = ::gen_handleBottomSelectAdd_fn
            fun gen_handleBottomSelectEdit_fn(payload: UTSJSONObject) {
                uni_showToast(ShowToastOptions(title = "状态字段不支持编辑", icon = "none"))
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
            onLoad(fun(event: OnLoadOptions){
                leaveSignal.value = 0
                val idValue = event["id"]
                supplierId.value = if (idValue == null) {
                    ""
                } else {
                    (idValue as String)
                }
                formMode.value = if (supplierId.value == "") {
                    "create"
                } else {
                    "edit"
                }
                if (formMode.value == "edit") {
                    loadSupplierDetailData(supplierId.value)
                    return
                }
                clearDraftStorage()
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversaForm = resolveEasyComponent("lili-UniversaForm", GenUniModulesLiliUniversaFormComponentsLiliUniversaFormLiliUniversaFormClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to pageTitle.value, "showBack" to true, "showSearch" to false, "showHome" to true, "homePath" to "/pages/suppliers/index", "backgroundColor" to "#EEF2F7"), null, 8, _uA(
                        "title"
                    )),
                    _cE("view", _uM("class" to "page-content"), _uA(
                        _cV(_component_lili_UniversaForm, _uM("mode" to unref(formMode), "formSections" to unref(formSections), "initialData" to unref(initialData), "leaveSignal" to unref(leaveSignal), "uploadContentTypeModel" to "supplier", "onSubmit" to handleSubmit, "onCancel" to handleCancel, "onDiscardLeave" to handleDiscardLeave, "onSaveRequest" to handleSaveRequest, "onDirtyChange" to handleDirtyChange, "onBottomSelectAdd" to handleBottomSelectAdd, "onBottomSelectEdit" to handleBottomSelectEdit, "onUpload" to handleUpload, "onUploadDelete" to handleUploadDelete, "onUploadError" to handleUploadError), null, 8, _uA(
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "position" to "relative", "backgroundColor" to "#EEF2F7")), "page-header" to _pS(_uM("paddingTop" to 8, "paddingRight" to 10, "paddingBottom" to 6, "paddingLeft" to 10, "backgroundColor" to "#EEF2F7")), "page-mode" to _pS(_uM("alignSelf" to "flex-start", "paddingTop" to 4, "paddingRight" to 10, "paddingBottom" to 4, "paddingLeft" to 10, "borderTopLeftRadius" to 999, "borderTopRightRadius" to 999, "borderBottomRightRadius" to 999, "borderBottomLeftRadius" to 999, "fontSize" to 12, "lineHeight" to "16px", "color" to "#155E75", "backgroundColor" to "#CFFAFE")), "page-desc" to _pS(_uM("marginTop" to 8, "fontSize" to 13, "lineHeight" to "18px", "color" to "#64748B")), "page-content" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingLeft" to 0, "paddingRight" to 0, "paddingBottom" to 0)), "page-saving-mask" to _pS(_uM("position" to "absolute", "left" to 0, "top" to 0, "right" to 0, "bottom" to 0, "zIndex" to 9999, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "rgba(15,23,42,0.28)")), "page-saving-card" to _pS(_uM("height" to 44, "paddingLeft" to 16, "paddingRight" to 16, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "rgba(15,23,42,0.86)")), "page-saving-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "16px", "color" to "#FFFFFF")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
