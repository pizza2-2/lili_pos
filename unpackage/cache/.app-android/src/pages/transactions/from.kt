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
open class GenPagesTransactionsFrom : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesTransactionsFrom) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesTransactionsFrom
            val _cache = __ins.renderCache
            val transactionListRefreshStorageKey = "refresh:pages:transactions:index"
            val formMode = ref("create")
            val transactionId = ref("")
            val supplierId = ref("")
            val leaveSignal = ref(0)
            val submitting = ref(false)
            val savingVisible = ref(false)
            val savingText = ref("处理中...")
            val initialData = ref<UTSJSONObject>(_uO("supplier_id" to "", "supplier_name" to "", "transaction_number" to "", "transaction_type" to "", "amount" to "", "transaction_date" to "", "note" to "", "images" to _uA<String>(), "imageItems" to _uA<UTSJSONObject>()))
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
            fun gen_buildUploadHeaders_fn(): UTSJSONObject {
                val headers: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("headers", "pages/transactions/from.uvue", 91, 8))
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
                        val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/transactions/from.uvue:107")
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
            fun gen_buildInitialDataFromTransaction_fn(item: TransactionItem): UTSJSONObject {
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
                return _uO("supplier_id" to item.supplier.toString(10), "supplier_name" to item.supplier_name, "transaction_number" to item.transaction_number, "transaction_type" to item.transaction_type.toString(10), "amount" to item.amount, "transaction_date" to item.transaction_date, "note" to if (item.note == null) {
                    ""
                } else {
                    item.note
                }
                , "images" to images, "imageItems" to imageItems)
            }
            val buildInitialDataFromTransaction = ::gen_buildInitialDataFromTransaction_fn
            fun gen_buildSelectResponse_fn(source: UTSArray<SelectOption__1>, params: UTSJSONObject): UTSJSONObject {
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
            fun gen_buildOptionsFromGroup_fn(group: TransactionOptionGroup?): UTSArray<SelectOption__1> {
                if (group == null) {
                    return _uA()
                }
                val result: UTSArray<SelectOption__1> = _uA()
                run {
                    var index: Number = 0
                    while(index < group.items.length){
                        val item = group.items[index]
                        result.push(SelectOption__1(value = item.value, text = item.label))
                        index += 1
                    }
                }
                return result
            }
            val buildOptionsFromGroup = ::gen_buildOptionsFromGroup_fn
            fun gen_findTransactionTypeGroup_fn(groups: UTSArray<TransactionOptionGroup>): TransactionOptionGroup? {
                run {
                    var index: Number = 0
                    while(index < groups.length){
                        val group = groups[index]
                        if (group.key == "transaction_type" || group.key == "transaction_types") {
                            return group
                        }
                        index += 1
                    }
                }
                run {
                    var index: Number = 0
                    while(index < groups.length){
                        val group = groups[index]
                        if (group.label.indexOf("类型") >= 0) {
                            return group
                        }
                        index += 1
                    }
                }
                if (groups.length > 0) {
                    return groups[0]
                }
                return null
            }
            val findTransactionTypeGroup = ::gen_findTransactionTypeGroup_fn
            fun gen_fetchTransactionTypeOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        val keyword = getStringField(params, "keyword")
                        val id = getStringField(params, "id")
                        val response = await(getTransactionOptions("transaction_type", if (keyword == "") {
                            null
                        } else {
                            keyword
                        }
                        , 50))
                        var options = buildOptionsFromGroup(findTransactionTypeGroup(response.groups))
                        if (options.length == 0) {
                            val fallbackResponse = await(getTransactionOptions(null, if (keyword == "") {
                                null
                            } else {
                                keyword
                            }
                            , 50))
                            options = buildOptionsFromGroup(findTransactionTypeGroup(fallbackResponse.groups))
                        }
                        return@w1 buildSelectResponse(options, _uO("keyword" to keyword, "id" to id))
                })
            }
            val fetchTransactionTypeOptions = ::gen_fetchTransactionTypeOptions_fn
            fun gen_loadSupplierName_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (supplierId.value == "") {
                            return@w1
                        }
                        try {
                            val supplierDetail = await(getSupplierDetail(supplierId.value))
                            initialData.value = _uO("supplier_id" to getStringField(initialData.value, "supplier_id"), "supplier_name" to supplierDetail.name, "transaction_number" to getStringField(initialData.value, "transaction_number"), "transaction_type" to getStringField(initialData.value, "transaction_type"), "amount" to getStringField(initialData.value, "amount"), "transaction_date" to getStringField(initialData.value, "transaction_date"), "note" to getStringField(initialData.value, "note"), "images" to getArrayField(initialData.value, "images"), "imageItems" to if (initialData.value["imageItems"] == null) {
                                (_uA<UTSJSONObject>())
                            } else {
                                (initialData.value["imageItems"] as UTSArray<UTSJSONObject>)
                            }
                            )
                        }
                         catch (error: Throwable) {
                            initialData.value = _uO("supplier_id" to getStringField(initialData.value, "supplier_id"), "supplier_name" to "", "transaction_number" to getStringField(initialData.value, "transaction_number"), "transaction_type" to getStringField(initialData.value, "transaction_type"), "amount" to getStringField(initialData.value, "amount"), "transaction_date" to getStringField(initialData.value, "transaction_date"), "note" to getStringField(initialData.value, "note"), "images" to getArrayField(initialData.value, "images"), "imageItems" to if (initialData.value["imageItems"] == null) {
                                (_uA<UTSJSONObject>())
                            } else {
                                (initialData.value["imageItems"] as UTSArray<UTSJSONObject>)
                            }
                            )
                        }
                })
            }
            val loadSupplierName = ::gen_loadSupplierName_fn
            val formSections = ref(_uA<UTSJSONObject>(_uO("key" to "base", "title" to "基础信息", "description" to "", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "supplier_id", "type" to "input", "hidden" to true), _uO("key" to "supplier_name", "label" to "供应商", "type" to "input", "readonly" to true, "placeholder" to "供应商名称"), _uO("key" to "transaction_number", "label" to "采购单号", "type" to "input", "readonly" to true, "placeholder" to "提交后由系统自动生成"), _uO("key" to "transaction_type", "label" to "采购类型", "type" to "bottomSelect", "title" to "选择采购类型", "placeholder" to "请选择采购类型", "required" to true, "showAddAction" to false, "showEditAction" to false, "fetchData" to fetchTransactionTypeOptions), _uO("key" to "amount", "label" to "金额", "type" to "number", "required" to true, "placeholder" to "请输入金额"), _uO("key" to "transaction_date", "label" to "采购时间", "type" to "datetime", "required" to true, "title" to "选择采购日期", "placeholder" to "请选择采购日期", "showTime" to false, "defaultToToday" to true), _uO("key" to "note", "label" to "备注", "type" to "textarea", "placeholder" to "请输入备注"))), _uO("key" to "media", "title" to "图片资料", "description" to "可同时上传多张图片", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "images", "label" to "采购图片", "type" to "upload", "action" to "", "name" to "files", "max" to 9, "uploadText" to "上传图片", "fileItemsKey" to "imageItems", "headers" to buildUploadHeaders(), "formData" to _uO())))))
            val pageTitle = computed(fun(): String {
                return if (formMode.value == "edit") {
                    "编辑采购记录"
                } else {
                    "新建采购记录"
                }
            }
            )
            fun gen_clearDraftStorage_fn() {}
            val clearDraftStorage = ::gen_clearDraftStorage_fn
            fun gen_markTransactionListRefreshNeeded_fn() {
                uni_setStorageSync(transactionListRefreshStorageKey, "1")
            }
            val markTransactionListRefreshNeeded = ::gen_markTransactionListRefreshNeeded_fn
            fun gen_loadTransactionDetailData_fn(idText: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (idText == "") {
                            return@w1
                        }
                        try {
                            val detail = await(getTransactionDetail(idText))
                            supplierId.value = detail.supplier.toString(10)
                            initialData.value = buildInitialDataFromTransaction(detail)
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error, "采购记录详情加载失败"), icon = "none"))
                        }
                })
            }
            val loadTransactionDetailData = ::gen_loadTransactionDetailData_fn
            fun gen_goBackToList_fn() {
                leaveSignal.value = leaveSignal.value + 1
                setTimeout(fun(){
                    uni_navigateBack(NavigateBackOptions(delta = 1, fail = fun(_){
                        if (supplierId.value != "") {
                            uni_reLaunch(ReLaunchOptions(url = "/pages/transactions/index?supplier_id=" + supplierId.value))
                            return
                        }
                        uni_reLaunch(ReLaunchOptions(url = "/pages/suppliers/index"))
                    }
                    ))
                }
                , 16)
            }
            val goBackToList = ::gen_goBackToList_fn
            fun gen_buildTransactionMutationPayload_fn(formDataObject: UTSJSONObject): TransactionMutationData {
                val supplierValue = getStringField(formDataObject, "supplier_id")
                val transactionTypeValue = getStringField(formDataObject, "transaction_type")
                val amountValue = getStringField(formDataObject, "amount")
                val transactionDateValue = getStringField(formDataObject, "transaction_date")
                val noteValue = getStringField(formDataObject, "note")
                val transactionNumberValue = getStringField(formDataObject, "transaction_number")
                return TransactionMutationData(supplier = supplierValue, transaction_type = transactionTypeValue, amount = amountValue, transaction_date = transactionDateValue, transaction_number = if (transactionNumberValue == "") {
                    null
                } else {
                    transactionNumberValue
                }
                , note = if (noteValue == "") {
                    null
                } else {
                    noteValue
                }
                )
            }
            val buildTransactionMutationPayload = ::gen_buildTransactionMutationPayload_fn
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
            fun gen_uploadPendingTransactionImages_fn(formDataObject: UTSJSONObject, contentTypeModel: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (transactionId.value == "") {
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
                                uploadItems.push(MediaBatchUploadItem(filePath = pendingImagePaths[index], name = "files", formData = _uO("content_type_model" to contentTypeModel, "object_id" to transactionId.value)))
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
            val uploadPendingTransactionImages = ::gen_uploadPendingTransactionImages_fn
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
                        val uploadContentTypeModel = getStringField(payload, "uploadContentTypeModel").trim()
                        val actionText = if (formMode.value == "edit") {
                            "保存修改"
                        } else {
                            "创建采购记录"
                        }
                        submitting.value = true
                        savingText.value = actionText + "中..."
                        savingVisible.value = true
                        uni_showLoading(ShowLoadingOptions(title = savingText.value, mask = true))
                        try {
                            val body = buildTransactionMutationPayload(data)
                            var successMessage = actionText + "成功"
                            if (body.supplier == "") {
                                throw UTSError("供应商ID缺失")
                            }
                            if (formMode.value == "edit" && transactionId.value != "") {
                                savingText.value = "保存修改中..."
                                await(updateTransaction(transactionId.value, body))
                                successMessage = takeLatestResponseMessage(successMessage)
                                savingText.value = "上传图片中..."
                                await(uploadPendingTransactionImages(data, uploadContentTypeModel))
                            } else {
                                savingText.value = "创建采购记录中..."
                                val createdTransaction = await(createTransaction(body))
                                successMessage = takeLatestResponseMessage(successMessage)
                                transactionId.value = createdTransaction.id.toString(10)
                                supplierId.value = createdTransaction.supplier.toString(10)
                                try {
                                    savingText.value = "上传图片中..."
                                    await(uploadPendingTransactionImages(data, uploadContentTypeModel))
                                }
                                 catch (uploadError: Throwable) {
                                    throw UTSError("采购记录已创建，但图片上传失败")
                                }
                            }
                            clearDraftStorage()
                            markTransactionListRefreshNeeded()
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
                clearDraftStorage()
                goBackToList()
            }
            val handleDiscardLeave = ::gen_handleDiscardLeave_fn
            fun gen_handleDirtyChange_fn(value: Boolean) {}
            val handleDirtyChange = ::gen_handleDirtyChange_fn
            fun gen_handleBottomSelectAdd_fn(payload: UTSJSONObject) {
                uni_showToast(ShowToastOptions(title = "采购类型不支持新增", icon = "none"))
            }
            val handleBottomSelectAdd = ::gen_handleBottomSelectAdd_fn
            fun gen_handleBottomSelectEdit_fn(payload: UTSJSONObject) {
                uni_showToast(ShowToastOptions(title = "采购类型不支持编辑", icon = "none"))
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
                transactionId.value = if (idValue == null) {
                    ""
                } else {
                    (idValue as String)
                }
                val supplierIdValue = event["supplier_id"]
                supplierId.value = if (supplierIdValue == null) {
                    ""
                } else {
                    (supplierIdValue as String)
                }
                initialData.value = _uO("supplier_id" to supplierId.value, "supplier_name" to "", "transaction_number" to "", "transaction_type" to "", "amount" to "", "transaction_date" to "", "note" to "", "images" to _uA<String>(), "imageItems" to _uA<UTSJSONObject>())
                formMode.value = if (transactionId.value == "") {
                    "create"
                } else {
                    "edit"
                }
                if (formMode.value == "edit") {
                    loadTransactionDetailData(transactionId.value)
                    return
                }
                loadSupplierName()
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
                        _cV(_component_lili_UniversaForm, _uM("mode" to unref(formMode), "formSections" to unref(formSections), "initialData" to unref(initialData), "leaveSignal" to unref(leaveSignal), "uploadContentTypeModel" to "suppliertransaction", "onSubmit" to handleSubmit, "onCancel" to handleCancel, "onDiscardLeave" to handleDiscardLeave, "onSaveRequest" to handleSaveRequest, "onDirtyChange" to handleDirtyChange, "onBottomSelectAdd" to handleBottomSelectAdd, "onBottomSelectEdit" to handleBottomSelectEdit, "onUpload" to handleUpload, "onUploadDelete" to handleUploadDelete, "onUploadError" to handleUploadError), null, 8, _uA(
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "position" to "relative", "backgroundColor" to "#EEF2F7")), "page-content" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "page-saving-mask" to _pS(_uM("position" to "absolute", "left" to 0, "top" to 0, "right" to 0, "bottom" to 0, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "rgba(17,24,39,0.18)")), "page-saving-card" to _pS(_uM("paddingTop" to 16, "paddingRight" to 20, "paddingBottom" to 16, "paddingLeft" to 20, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#111827")), "page-saving-text" to _pS(_uM("fontSize" to 14, "fontWeight" to "600", "color" to "#FFFFFF")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
