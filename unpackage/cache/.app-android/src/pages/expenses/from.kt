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
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesExpensesFrom : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesExpensesFrom) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesExpensesFrom
            val _cache = __ins.renderCache
            val expenseListRefreshStorageKey = "refresh:pages:expenses:index"
            val formMode = ref("create")
            val expenseId = ref("")
            val leaveSignal = ref(0)
            val submitting = ref(false)
            val savingVisible = ref(false)
            val savingText = ref("处理中...")
            val pageTaskGuard = createAsyncGuard()
            val initialData = ref<UTSJSONObject>(_uO("expenditure_type_id" to "", "expenditure_type_text" to "", "supplier_id" to "", "supplier_text" to "", "amount" to "", "expenditure_date" to "", "invoice_number" to "", "description" to "", "note" to "", "images" to _uA<String>(), "imageItems" to _uA<UTSJSONObject>()))
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
                val headers: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("headers", "pages/expenses/from.uvue", 82, 8))
                if (authState.token != "") {
                    headers["Authorization"] = authState.token
                }
                return headers
            }
            val buildUploadHeaders = ::gen_buildUploadHeaders_fn
            fun gen_twoDigit_fn(value: Number): String {
                if (value < 10) {
                    return "0" + value.toString(10)
                }
                return value.toString(10)
            }
            val twoDigit = ::gen_twoDigit_fn
            fun gen_todayText_fn(): String {
                val now = Date()
                val y = now.getFullYear().toString(10)
                val m = twoDigit(now.getMonth() + 1)
                val d = twoDigit(now.getDate())
                return y + "-" + m + "-" + d
            }
            val todayText = ::gen_todayText_fn
            fun gen_parseErrorMessage_fn(error: Any, fallback: String): String {
                var message = fallback
                if (error != null) {
                    val errorText = JSON.stringify(error)
                    if (errorText != null && errorText != "") {
                        val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/expenses/from.uvue:105")
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
            fun gen_buildSelectResponse_fn(source: UTSArray<SelectOption__7>, params: UTSJSONObject): UTSJSONObject {
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
                        result.push(_uO("value" to option.value, "text" to option.text))
                        index += 1
                    }
                }
                return _uO("data" to result, "results" to result, "total" to result.length, "total_count" to result.length)
            }
            val buildSelectResponse = ::gen_buildSelectResponse_fn
            fun gen_buildOptionsFromGroup_fn(group: ExpenseOptionGroup?): UTSArray<SelectOption__7> {
                if (group == null) {
                    return _uA()
                }
                val result: UTSArray<SelectOption__7> = _uA()
                run {
                    var index: Number = 0
                    while(index < group.items.length){
                        val item = group.items[index]
                        result.push(SelectOption__7(value = item.value, text = item.label))
                        index += 1
                    }
                }
                return result
            }
            val buildOptionsFromGroup = ::gen_buildOptionsFromGroup_fn
            fun gen_findOptionGroup_fn(groups: UTSArray<ExpenseOptionGroup>, key: String): ExpenseOptionGroup? {
                run {
                    var index: Number = 0
                    while(index < groups.length){
                        val group = groups[index]
                        if (group.key == key) {
                            return group
                        }
                        index += 1
                    }
                }
                run {
                    var index: Number = 0
                    while(index < groups.length){
                        val group = groups[index]
                        if (group.label.indexOf(if (key == "supplier") {
                            "供应商"
                        } else {
                            "类型"
                        }
                        ) >= 0) {
                            return group
                        }
                        index += 1
                    }
                }
                return null
            }
            val findOptionGroup = ::gen_findOptionGroup_fn
            fun gen_fetchExpenseTypeOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        val keyword = getStringField(params, "keyword")
                        val id = getStringField(params, "id")
                        val response = await(getExpenseOptions("expenditure_type", if (keyword == "") {
                            null
                        } else {
                            keyword
                        }
                        , 50))
                        val options = buildOptionsFromGroup(findOptionGroup(response.groups, "expenditure_type"))
                        return@w1 buildSelectResponse(options, _uO("keyword" to keyword, "id" to id))
                })
            }
            val fetchExpenseTypeOptions = ::gen_fetchExpenseTypeOptions_fn
            fun gen_fetchSupplierOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        val keyword = getStringField(params, "keyword")
                        val id = getStringField(params, "id")
                        val response = await(getExpenseOptions("supplier", if (keyword == "") {
                            null
                        } else {
                            keyword
                        }
                        , 50))
                        val options = buildOptionsFromGroup(findOptionGroup(response.groups, "supplier"))
                        return@w1 buildSelectResponse(options, _uO("keyword" to "", "id" to id))
                })
            }
            val fetchSupplierOptions = ::gen_fetchSupplierOptions_fn
            fun gen_buildInitialDataFromExpense_fn(item: ExpenseItem): UTSJSONObject {
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
                return _uO("expenditure_type_id" to if (item.expenditure_type <= 0) {
                    ""
                } else {
                    item.expenditure_type.toString(10)
                }
                , "expenditure_type_text" to item.expenditure_type_name, "supplier_id" to if (item.supplier <= 0) {
                    ""
                } else {
                    item.supplier.toString(10)
                }
                , "supplier_text" to item.supplier_name, "amount" to item.amount, "expenditure_date" to item.expenditure_date, "invoice_number" to if (item.invoice_number == null) {
                    ""
                } else {
                    item.invoice_number
                }
                , "description" to if (item.description == null) {
                    ""
                } else {
                    item.description
                }
                , "note" to if (item.note == null) {
                    ""
                } else {
                    item.note
                }
                , "images" to images, "imageItems" to imageItems)
            }
            val buildInitialDataFromExpense = ::gen_buildInitialDataFromExpense_fn
            val formSections = ref(_uA<UTSJSONObject>(_uO("key" to "base", "title" to "基础信息", "description" to "", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "amount", "label" to "支出金额", "type" to "number", "required" to true, "placeholder" to "请输入支出金额"), _uO("key" to "expenditure_date", "label" to "支出日期", "type" to "datetime", "required" to true, "showTime" to false, "defaultToToday" to true, "title" to "选择支出日期", "placeholder" to "请选择支出日期"), _uO("key" to "expenditure_type_id", "textKey" to "expenditure_type_text", "label" to "支出类型", "type" to "bottomSelect", "title" to "选择支出类型", "placeholder" to "请选择支出类型", "showAddAction" to false, "showEditAction" to false, "fetchData" to fetchExpenseTypeOptions), _uO("key" to "supplier_id", "textKey" to "supplier_text", "label" to "供应商", "type" to "bottomSelect", "title" to "选择供应商", "placeholder" to "可选供应商", "showAddAction" to true, "showEditAction" to true, "addPath" to "/pages/suppliers/from", "editPath" to "/pages/suppliers/from", "fetchData" to fetchSupplierOptions), _uO("key" to "invoice_number", "label" to "发票号码", "type" to "input", "placeholder" to "请输入发票号码"), _uO("key" to "description", "label" to "支出描述", "type" to "textarea", "placeholder" to "请输入支出描述"), _uO("key" to "note", "label" to "备注", "type" to "textarea", "placeholder" to "请输入备注"), _uO("key" to "images", "label" to "附件图片", "type" to "upload", "action" to "", "name" to "files", "max" to 9, "uploadText" to "上传凭证", "fileItemsKey" to "imageItems", "headers" to buildUploadHeaders(), "formData" to _uO())))))
            val pageTitle = computed(fun(): String {
                return if (formMode.value == "edit") {
                    "编辑支出记录"
                } else {
                    "新建支出记录"
                }
            }
            )
            fun gen_markExpenseListRefreshNeeded_fn() {
                uni_setStorageSync(expenseListRefreshStorageKey, "1")
            }
            val markExpenseListRefreshNeeded = ::gen_markExpenseListRefreshNeeded_fn
            fun goBackToList(markLeaving: Boolean = true) {
                if (markLeaving) {
                    pageTaskGuard.leave()
                    savingVisible.value = false
                    uni_hideLoading(null)
                }
                leaveSignal.value = leaveSignal.value + 1
                setTimeout(fun(){
                    uni_navigateBack(NavigateBackOptions(delta = 1, fail = fun(_){
                        uni_navigateTo(NavigateToOptions(url = "/pages/expenses/index"))
                    }
                    ))
                }
                , 16)
            }
            fun gen_loadExpenseDetailData_fn(idText: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (idText == "") {
                            return@w1
                        }
                        try {
                            val detail = await(getExpenseDetail(idText))
                            initialData.value = buildInitialDataFromExpense(detail)
                        }
                         catch (error: Throwable) {
                            showErrorToast(parseErrorMessage(error, "支出详情加载失败"))
                        }
                })
            }
            val loadExpenseDetailData = ::gen_loadExpenseDetailData_fn
            fun gen_buildMutationPayload_fn(data: UTSJSONObject): ExpenseMutationData {
                return ExpenseMutationData(expenditure_type_id = if (getStringField(data, "expenditure_type_id") == "") {
                    null
                } else {
                    getStringField(data, "expenditure_type_id")
                }
                , supplier_id = if (getStringField(data, "supplier_id") == "") {
                    null
                } else {
                    getStringField(data, "supplier_id")
                }
                , amount = getStringField(data, "amount"), expenditure_date = getStringField(data, "expenditure_date"), invoice_number = if (getStringField(data, "invoice_number") == "") {
                    null
                } else {
                    getStringField(data, "invoice_number")
                }
                , description = if (getStringField(data, "description") == "") {
                    null
                } else {
                    getStringField(data, "description")
                }
                , note = if (getStringField(data, "note") == "") {
                    null
                } else {
                    getStringField(data, "note")
                }
                )
            }
            val buildMutationPayload = ::gen_buildMutationPayload_fn
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
                        if (imagePath != "" && !isRemoteImagePath(imagePath)) {
                            result.push(imagePath)
                        }
                        index += 1
                    }
                }
                return result
            }
            val collectPendingImagePaths = ::gen_collectPendingImagePaths_fn
            fun gen_uploadPendingImages_fn(formDataObject: UTSJSONObject, contentTypeModel: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (expenseId.value == "") {
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
                                uploadItems.push(MediaBatchUploadItem(filePath = pendingImagePaths[index], name = "files", formData = _uO("content_type_model" to contentTypeModel, "object_id" to expenseId.value)))
                                index += 1
                            }
                        }
                        val uploadResult = await(batchUploadMediaFiles(uploadItems))
                        if (uploadResult.failItems.length > 0) {
                            val firstFail = uploadResult.failItems[0]
                            throw UTSError(getStringField(firstFail, "message", "图片上传失败"))
                        }
                })
            }
            val uploadPendingImages = ::gen_uploadPendingImages_fn
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
                        val body = buildMutationPayload(data)
                        if (body.amount == "" || parseFloat(body.amount) <= 0 || isNaN(parseFloat(body.amount))) {
                            uni_showToast(ShowToastOptions(title = "请输入有效的支出金额", icon = "none", duration = 3500))
                            return@w1
                        }
                        if (body.expenditure_date == "") {
                            uni_showToast(ShowToastOptions(title = "请选择支出日期", icon = "none", duration = 3500))
                            return@w1
                        }
                        val uploadContentTypeModel = getStringField(payload, "uploadContentTypeModel").trim()
                        val actionText = if (formMode.value == "edit") {
                            "保存支出记录"
                        } else {
                            "创建支出记录"
                        }
                        val taskToken = pageTaskGuard.begin()
                        submitting.value = true
                        savingText.value = actionText + "中..."
                        savingVisible.value = true
                        uni_showLoading(ShowLoadingOptions(title = savingText.value, mask = true))
                        try {
                            var successMessage = actionText + "成功"
                            if (formMode.value == "edit" && expenseId.value != "") {
                                await(updateExpense(expenseId.value, body))
                                successMessage = takeLatestResponseMessage(successMessage)
                                savingText.value = "上传凭证中..."
                                await(uploadPendingImages(data, uploadContentTypeModel))
                            } else {
                                val createdExpense = await(createExpense(body))
                                successMessage = takeLatestResponseMessage(successMessage)
                                expenseId.value = createdExpense.id.toString(10)
                                try {
                                    savingText.value = "上传凭证中..."
                                    await(uploadPendingImages(data, uploadContentTypeModel))
                                }
                                 catch (uploadError: Throwable) {
                                    throw UTSError("支出记录已创建，但凭证上传失败")
                                }
                            }
                            markExpenseListRefreshNeeded()
                            if (!pageTaskGuard.canApply(taskToken)) {
                                return@w1
                            }
                            uni_showToast(ShowToastOptions(title = successMessage, icon = "success"))
                            goBackToList(false)
                        }
                         catch (error: Throwable) {
                            if (!pageTaskGuard.canApply(taskToken)) {
                                return@w1
                            }
                            showErrorToast(parseErrorMessage(error, actionText + "失败"))
                        }
                         finally {
                            if (pageTaskGuard.canApply(taskToken)) {
                                savingVisible.value = false
                                uni_hideLoading(null)
                                submitting.value = false
                            }
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
                val changed = payload["hasChanges"]
                if (changed != null && (changed as Boolean)) {
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
                uni_showToast(ShowToastOptions(title = "请先在后台维护支出类型或供应商", icon = "none", duration = 3500))
            }
            val handleBottomSelectAdd = ::gen_handleBottomSelectAdd_fn
            fun gen_handleBottomSelectEdit_fn(payload: UTSJSONObject) {
                uni_showToast(ShowToastOptions(title = "该字段不支持直接编辑", icon = "none", duration = 3500))
            }
            val handleBottomSelectEdit = ::gen_handleBottomSelectEdit_fn
            fun gen_handleUpload_fn(payload: UTSJSONObject) {
                uni_showToast(ShowToastOptions(title = "凭证已加入待保存列表", icon = "none", duration = 3500))
            }
            val handleUpload = ::gen_handleUpload_fn
            fun gen_handleUploadDelete_fn(payload: UTSJSONObject) {
                uni_showToast(ShowToastOptions(title = "凭证已删除", icon = "success"))
            }
            val handleUploadDelete = ::gen_handleUploadDelete_fn
            fun gen_handleUploadError_fn(payload: UTSJSONObject) {
                showErrorToast("凭证上传失败")
            }
            val handleUploadError = ::gen_handleUploadError_fn
            onLoad(fun(event: OnLoadOptions){
                pageTaskGuard.reset()
                val idValue = event["id"]
                expenseId.value = if (idValue == null) {
                    ""
                } else {
                    (idValue as String)
                }
                formMode.value = if (expenseId.value == "") {
                    "create"
                } else {
                    "edit"
                }
                initialData.value = _uO("expenditure_type_id" to "", "expenditure_type_text" to "", "supplier_id" to "", "supplier_text" to "", "amount" to "", "expenditure_date" to todayText(), "invoice_number" to "", "description" to "", "note" to "", "images" to _uA<String>(), "imageItems" to _uA<UTSJSONObject>())
                if (formMode.value == "edit") {
                    loadExpenseDetailData(expenseId.value)
                }
            }
            )
            onUnload(fun(){
                pageTaskGuard.leave()
                uni_hideLoading(null)
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversaForm = resolveEasyComponent("lili-UniversaForm", GenUniModulesLiliUniversaFormComponentsLiliUniversaFormLiliUniversaFormClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to pageTitle.value, "showBack" to true, "showSearch" to false, "showHome" to true, "homePath" to "/pages/expenses/index", "backgroundColor" to "#EEF2F7"), null, 8, _uA(
                        "title"
                    )),
                    _cE("view", _uM("class" to "page-content"), _uA(
                        _cV(_component_lili_UniversaForm, _uM("mode" to unref(formMode), "formSections" to unref(formSections), "initialData" to unref(initialData), "leaveSignal" to unref(leaveSignal), "uploadContentTypeModel" to "expenditure", "onSubmit" to handleSubmit, "onCancel" to handleCancel, "onDiscardLeave" to handleDiscardLeave, "onSaveRequest" to handleSaveRequest, "onDirtyChange" to handleDirtyChange, "onBottomSelectAdd" to handleBottomSelectAdd, "onBottomSelectEdit" to handleBottomSelectEdit, "onUpload" to handleUpload, "onUploadDelete" to handleUploadDelete, "onUploadError" to handleUploadError), null, 8, _uA(
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#EEF2F7")), "page-content" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingBottom" to 0)), "page-saving-mask" to _pS(_uM("position" to "fixed", "left" to 0, "right" to 0, "top" to 0, "bottom" to 0, "backgroundColor" to "rgba(15,23,42,0.18)", "alignItems" to "center", "justifyContent" to "center")), "page-saving-card" to _pS(_uM("minWidth" to 160, "minHeight" to 64, "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "backgroundColor" to "#FFFFFF", "alignItems" to "center", "justifyContent" to "center", "paddingLeft" to 18, "paddingRight" to 18)), "page-saving-text" to _pS(_uM("fontSize" to 14, "color" to "#111827")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
