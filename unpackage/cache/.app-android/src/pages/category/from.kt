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
import io.dcloud.uniapp.extapi.removeStorageSync as uni_removeStorageSync
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesCategoryFrom : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesCategoryFrom) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesCategoryFrom
            val _cache = __ins.renderCache
            val categoryListRefreshStorageKey = "refresh:pages:category:index"
            val categoryListRefreshPayloadStorageKey = "refresh:pages:category:index:payload"
            val formMode = ref("create")
            val operationMode = ref("create")
            val categoryId = ref("")
            val leaveSignal = ref(0)
            val submitting = ref(false)
            val savingVisible = ref(false)
            val savingText = ref("处理中...")
            val initialData = ref<UTSJSONObject>(_uO("name" to "", "name_en" to "", "code" to "", "parent_id" to "", "parent_text" to "", "description" to "", "kasa_category_id" to "", "kasa_category_text" to "", "tax_rate" to "0.23", "tax_rate_text" to "标准税率 23%", "sort_order" to "0", "is_active" to true))
            val parentInfo = ref<UTSJSONObject>(_uO("id" to "", "text" to "根分类（无父分类）"))
            val taxRateOptions = ref(_uA<SelectOption__4>(SelectOption__4(value = "0.00", text = "免税 0%"), SelectOption__4(value = "0.05", text = "低税率 5%"), SelectOption__4(value = "0.08", text = "中税率 8%"), SelectOption__4(value = "0.23", text = "标准税率 23%")))
            val statusOptions = ref(_uA<SelectOption__4>(SelectOption__4(value = "true", text = "启用"), SelectOption__4(value = "false", text = "停用")))
            fun stringValue(value: Any?, fallback: String = ""): String {
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
            fun booleanValue(value: Any?, fallback: Boolean = false): Boolean {
                if (value == null) {
                    return fallback
                }
                val text = ("" + value).toLowerCase()
                return text == "true" || text == "1" || text == "yes"
            }
            fun intValue(value: Any?, fallback: Number = 0): Number {
                if (value == null) {
                    return fallback
                }
                val text = "" + value
                if (text == "") {
                    return fallback
                }
                val parsed = parseInt(text)
                if (isNaN(parsed)) {
                    return fallback
                }
                return parsed
            }
            fun gen_parseErrorMessage_fn(error: Any, fallback: String): String {
                var message = fallback
                if (error != null) {
                    val directMessage = (error as UTSError).message
                    if (directMessage != null && directMessage != "") {
                        message = directMessage
                    }
                    val errorText = JSON.stringify(error)
                    if (errorText != null && errorText != "") {
                        val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/category/from.uvue:130")
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
            fun gen_normalizeNullableId_fn(value: Any?): String? {
                val text = stringValue(value)
                if (text == "" || text == "0" || text == "null") {
                    return null
                }
                return text
            }
            val normalizeNullableId = ::gen_normalizeNullableId_fn
            fun gen_findTaxRateText_fn(value: String): String {
                run {
                    var index: Number = 0
                    while(index < taxRateOptions.value.length){
                        val option = taxRateOptions.value[index]
                        if (option.value == value) {
                            return option.text
                        }
                        index += 1
                    }
                }
                return value
            }
            val findTaxRateText = ::gen_findTaxRateText_fn
            fun gen_buildSelectResponse_fn(source: UTSArray<SelectOption__4>, params: UTSJSONObject): UTSJSONObject {
                val keyword = stringValue(params["keyword"]).toLowerCase()
                val id = stringValue(params["id"])
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
            fun gen_fetchTaxRateOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        return@w1 buildSelectResponse(taxRateOptions.value, params)
                })
            }
            val fetchTaxRateOptions = ::gen_fetchTaxRateOptions_fn
            fun gen_fetchKasaCategoryOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        val keyword = stringValue(params["keyword"])
                        val id = stringValue(params["id"])
                        val response = await(getKasaCategoryOptions(KasaCategoryOptionsQuery(key = "kasa_category", search = if (keyword == "") {
                            null
                        } else {
                            keyword
                        }
                        , q = null, keyword = null, limit = 50)))
                        val result: UTSArray<UTSJSONObject> = _uA()
                        run {
                            var index: Number = 0
                            while(index < response.items.length){
                                val item = response.items[index]
                                val value = stringValue(item["value"], stringValue(item["id"]))
                                val text = stringValue(item["text"], stringValue(item["label"]))
                                if (id != "" && value != id) {
                                    index += 1
                                    continue
                                }
                                result.push(_uO("value" to value, "text" to text))
                                index += 1
                            }
                        }
                        return@w1 _uO("data" to result, "total" to result.length)
                })
            }
            val fetchKasaCategoryOptions = ::gen_fetchKasaCategoryOptions_fn
            fun resolveKasaCategoryTextByValue(kasaCategoryIdText: String, fallback: String = ""): UTSPromise<String> {
                return wrapUTSPromise(suspend w1@{
                        if (kasaCategoryIdText == "") {
                            return@w1 ""
                        }
                        try {
                            val response = await(getKasaCategoryOptions(KasaCategoryOptionsQuery(key = "kasa_category", search = null, q = null, keyword = null, limit = 200)))
                            run {
                                var index: Number = 0
                                while(index < response.items.length){
                                    val item = response.items[index]
                                    val value = stringValue(item["value"], stringValue(item["id"]))
                                    if (value != kasaCategoryIdText) {
                                        index += 1
                                        continue
                                    }
                                    val text = stringValue(item["text"], stringValue(item["label"]))
                                    if (text != "") {
                                        return@w1 text
                                    }
                                    index += 1
                                }
                            }
                        }
                         catch (error: Throwable) {}
                        return@w1 fallback
                })
            }
            fun gen_fetchParentOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        val keyword = stringValue(params["keyword"])
                        val id = stringValue(params["id"])
                        val response = await(getCategoryList(CategoryListQuery(search = if (keyword == "") {
                            null
                        } else {
                            keyword
                        }
                        , page = 1, page_size = 50, id = if (id == "") {
                            null
                        } else {
                            id
                        }
                        , is_active = null, level = null, parent = null, parent_id = null, code = null, tax_rate = null, kasa_category = null, kasa_category_id = null, status = null, ordering = "name", simple = true)))
                        val result: UTSArray<UTSJSONObject> = _uA()
                        if (id == "") {
                            result.push(_uO("value" to "", "text" to "根分类（无父分类）"))
                        }
                        run {
                            var index: Number = 0
                            while(index < response.results.length){
                                val item = response.results[index]
                                if (categoryId.value != "" && item.id.toString(10) == categoryId.value) {
                                    index += 1
                                    continue
                                }
                                result.push(_uO("value" to item.id.toString(10), "text" to if (item.full_name == "") {
                                    item.name
                                } else {
                                    item.full_name
                                }
                                ))
                                index += 1
                            }
                        }
                        return@w1 _uO("data" to result, "total" to result.length)
                })
            }
            val fetchParentOptions = ::gen_fetchParentOptions_fn
            fun gen_resolveParentText_fn(parentIdText: String): UTSPromise<String> {
                return wrapUTSPromise(suspend w1@{
                        if (parentIdText == "") {
                            return@w1 "根分类（无父分类）"
                        }
                        try {
                            val parent = await(getCategoryDetail(parentIdText))
                            if (parent.full_name != "") {
                                return@w1 parent.full_name
                            }
                            return@w1 parent.name
                        }
                         catch (error: Throwable) {
                            return@w1 parentIdText
                        }
                })
            }
            val resolveParentText = ::gen_resolveParentText_fn
            fun gen_parseObject_fn(value: Any?): UTSJSONObject? {
                if (value == null) {
                    return null
                }
                val text = JSON.stringify(value)
                if (text == null || text == "") {
                    return null
                }
                return UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(text), " at pages/category/from.uvue:321")
            }
            val parseObject = ::gen_parseObject_fn
            fun gen_buildKasaCategoryTextFromInfo_fn(info: UTSJSONObject?): String {
                if (info == null) {
                    return ""
                }
                val text = stringValue(info["text"])
                if (text != "") {
                    return text
                }
                val label = stringValue(info["label"])
                if (label != "") {
                    return label
                }
                val name = stringValue(info["name"])
                if (name != "") {
                    return name
                }
                val nameCn = stringValue(info["name_cn"])
                val nameEn = stringValue(info["name_en"])
                if (nameCn != "" && nameEn != "") {
                    return nameCn + " / " + nameEn
                }
                if (nameCn != "") {
                    return nameCn
                }
                if (nameEn != "") {
                    return nameEn
                }
                return stringValue(info["unique_kod"])
            }
            val buildKasaCategoryTextFromInfo = ::gen_buildKasaCategoryTextFromInfo_fn
            fun gen_buildInitialDataFromDetail_fn(item: CategoryItem, parentText: String): UTSJSONObject {
                val raw = item.raw
                val rawNameEn = raw["name_en"]
                val rawDescription = raw["description"]
                val rawKasaCategoryId = if (raw["kasa_category_id"] != null) {
                    raw["kasa_category_id"]
                } else {
                    raw["kasa_category"]
                }
                val rawKasaCategoryName = raw["kasa_category_name"]
                val rawKasaCategoryInfo = parseObject(raw["kasa_category_info"])
                val fallbackKasaCategoryText = if (rawKasaCategoryName == null) {
                    buildKasaCategoryTextFromInfo(rawKasaCategoryInfo)
                } else {
                    (rawKasaCategoryName as String)
                }
                return _uO("name" to item.name, "name_en" to if (rawNameEn == null) {
                    ""
                } else {
                    (rawNameEn as String)
                }
                , "code" to item.code, "parent_id" to if (item.parent_id > 0) {
                    item.parent_id.toString(10)
                } else {
                    ""
                }
                , "parent_text" to parentText, "description" to if (rawDescription == null) {
                    ""
                } else {
                    (rawDescription as String)
                }
                , "kasa_category_id" to if (rawKasaCategoryId == null) {
                    ""
                } else {
                    ("" + rawKasaCategoryId)
                }
                , "kasa_category_text" to fallbackKasaCategoryText, "tax_rate" to if (item.tax_rate == "") {
                    "0.23"
                } else {
                    item.tax_rate
                }
                , "tax_rate_text" to if (item.tax_rate == "") {
                    "标准税率 23%"
                } else {
                    findTaxRateText(item.tax_rate)
                }
                , "sort_order" to item.sort_order.toString(10), "is_active" to item.is_active)
            }
            val buildInitialDataFromDetail = ::gen_buildInitialDataFromDetail_fn
            fun gen_loadDetailData_fn(idText: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        try {
                            val detail = await(getCategoryDetail(idText))
                            val parentText = await(resolveParentText(if (detail.parent_id > 0) {
                                detail.parent_id.toString(10)
                            } else {
                                ""
                            }
                            ))
                            val detailData = buildInitialDataFromDetail(detail, parentText)
                            val kasaCategoryIdText = stringValue(detailData["kasa_category_id"])
                            val currentKasaCategoryText = stringValue(detailData["kasa_category_text"])
                            if (kasaCategoryIdText != "") {
                                detailData["kasa_category_text"] = await(resolveKasaCategoryTextByValue(kasaCategoryIdText, currentKasaCategoryText))
                            }
                            initialData.value = detailData
                            parentInfo.value = _uO("id" to if (detail.parent_id > 0) {
                                detail.parent_id.toString(10)
                            } else {
                                ""
                            }
                            , "text" to parentText)
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error, "分类详情加载失败"), icon = "none"))
                        }
                })
            }
            val loadDetailData = ::gen_loadDetailData_fn
            fun gen_buildMutationPayload_fn(formDataObject: UTSJSONObject): CategoryMutationData {
                val normalizedParentId = normalizeNullableId(formDataObject["parent_id"])
                val normalizedKasaCategoryId = normalizeNullableId(formDataObject["kasa_category_id"])
                val name = stringValue(formDataObject["name"])
                val nameEn = stringValue(formDataObject["name_en"])
                val code = stringValue(formDataObject["code"])
                val description = stringValue(formDataObject["description"])
                val taxRate = stringValue(formDataObject["tax_rate"], "0.23")
                val sortOrder = intValue(formDataObject["sort_order"], 0)
                val isActive = booleanValue(formDataObject["is_active"], true)
                return _uO("name" to name, "name_en" to if (nameEn == "") {
                    null
                } else {
                    nameEn
                }
                , "code" to if (code == "") {
                    null
                } else {
                    code
                }
                , "parent" to normalizedParentId, "parent_id" to normalizedParentId, "description" to if (description == "") {
                    null
                } else {
                    description
                }
                , "kasa_category" to normalizedKasaCategoryId, "kasa_category_id" to normalizedKasaCategoryId, "tax_rate" to if (taxRate == "") {
                    null
                } else {
                    taxRate
                }
                , "sort_order" to sortOrder, "is_active" to isActive)
            }
            val buildMutationPayload = ::gen_buildMutationPayload_fn
            fun gen_buildRefreshPayload_fn(action: String, item: CategoryItem, previousParentId: String?, currentParentId: String?): UTSJSONObject {
                return _uO("action" to action, "id" to item.id.toString(10), "name" to item.name, "code" to item.code, "level" to item.level.toString(10), "parent_id" to item.parent_id.toString(10), "sort_order" to item.sort_order.toString(10), "tax_rate" to item.tax_rate, "kasa_category_id" to item.kasa_category_id.toString(10), "products_count" to item.products_count.toString(10), "children_count" to item.children_count.toString(10), "is_active" to item.is_active, "is_leaf" to item.is_leaf, "full_name" to item.full_name, "path" to item.path, "created_at" to item.created_at, "updated_at" to item.updated_at, "previous_parent_id" to if (previousParentId == null) {
                    ""
                } else {
                    previousParentId
                }
                , "current_parent_id" to if (currentParentId == null) {
                    ""
                } else {
                    currentParentId
                }
                )
            }
            val buildRefreshPayload = ::gen_buildRefreshPayload_fn
            fun markListRefreshNeeded(payload: UTSJSONObject? = null): Unit {
                uni_setStorageSync(categoryListRefreshStorageKey, "1")
                if (payload == null) {
                    uni_removeStorageSync(categoryListRefreshPayloadStorageKey)
                    return
                }
                uni_setStorageSync(categoryListRefreshPayloadStorageKey, JSON.stringify(payload))
            }
            fun gen_goBackToList_fn(): Unit {
                leaveSignal.value = leaveSignal.value + 1
                setTimeout(fun(){
                    uni_navigateBack(NavigateBackOptions(delta = 1, fail = fun(_){
                        uni_reLaunch(ReLaunchOptions(url = "/pages/category/index"))
                    }
                    ))
                }
                , 16)
            }
            val goBackToList = ::gen_goBackToList_fn
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
                        val name = stringValue(data["name"]).trim()
                        if (name == "") {
                            uni_showToast(ShowToastOptions(title = "分类名称不能为空", icon = "none"))
                            return@w1
                        }
                        submitting.value = true
                        val isEditing = formMode.value == "edit" && categoryId.value != ""
                        savingText.value = if (isEditing) {
                            "保存分类中..."
                        } else {
                            "创建分类中..."
                        }
                        savingVisible.value = true
                        uni_showLoading(ShowLoadingOptions(title = savingText.value, mask = true))
                        try {
                            val body = buildMutationPayload(data)
                            val previousParentId = normalizeNullableId(initialData.value["parent_id"])
                            val currentParentId = normalizeNullableId(data["parent_id"])
                            var refreshPayload: UTSJSONObject? = null
                            var successMessage = if (isEditing) {
                                "分类保存成功"
                            } else {
                                "分类创建成功"
                            }
                            if (isEditing) {
                                val updated = await(updateCategory(categoryId.value, body))
                                successMessage = takeLatestResponseMessage(successMessage)
                                val action = if (previousParentId == currentParentId) {
                                    "edit"
                                } else {
                                    "reload"
                                }
                                refreshPayload = buildRefreshPayload(action, updated, previousParentId, currentParentId)
                            } else {
                                val created = await(createCategory(body))
                                successMessage = takeLatestResponseMessage(successMessage)
                                categoryId.value = created.id.toString(10)
                                formMode.value = "edit"
                                refreshPayload = buildRefreshPayload("reload", created, previousParentId, currentParentId)
                            }
                            markListRefreshNeeded(refreshPayload)
                            uni_showToast(ShowToastOptions(title = successMessage, icon = "success"))
                            goBackToList()
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error, if (isEditing) {
                                "分类保存失败"
                            } else {
                                "分类创建失败"
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
            val formSections = ref(_uA<UTSJSONObject>(_uO("key" to "base", "title" to "基础信息", "description" to "", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "name", "label" to "分类名称", "type" to "input", "required" to true, "placeholder" to "请输入分类名称"), _uO("key" to "name_en", "label" to "英文名称", "type" to "input", "placeholder" to "请输入英文名称"), _uO("key" to "code", "label" to "分类编码", "type" to "input", "placeholder" to "留空则自动生成"), _uO("key" to "parent_text", "label" to "当前父分类", "type" to "input", "readonly" to true, "placeholder" to "根分类（无父分类）"), _uO("key" to "parent_id", "label" to "调整父分类", "type" to "bottomSelect", "textKey" to "parent_text", "title" to "选择父分类", "placeholder" to "请选择父分类", "showAddAction" to false, "showEditAction" to false, "editOnly" to true, "fetchData" to fetchParentOptions), _uO("key" to "description", "label" to "分类描述", "type" to "textarea", "placeholder" to "请输入分类描述"))), _uO("key" to "relation", "title" to "关联设置", "description" to "", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "kasa_category_id", "label" to "关联收银分类", "type" to "bottomSelect", "textKey" to "kasa_category_text", "title" to "选择收银分类", "placeholder" to "请选择收银分类", "searchPlaceholder" to "请输入收银分类名称", "emptyText" to "暂无收银分类", "showAddAction" to false, "showEditAction" to false, "fetchData" to fetchKasaCategoryOptions), _uO("key" to "tax_rate", "label" to "税率", "type" to "bottomSelect", "textKey" to "tax_rate_text", "title" to "选择税率", "placeholder" to "请选择税率", "showAddAction" to false, "showEditAction" to false, "fetchData" to fetchTaxRateOptions), _uO("key" to "sort_order", "label" to "排序号", "type" to "number", "placeholder" to "请输入排序号"))), _uO("key" to "status", "title" to "状态设置", "description" to "", "defaultOpen" to false, "fields" to _uA<UTSJSONObject>(_uO("key" to "is_active", "label" to "启用状态", "type" to "switch", "defaultValue" to true)))))
            val pageTitle = computed(fun(): String {
                if (operationMode.value == "edit") {
                    return "编辑分类"
                }
                if (operationMode.value == "add") {
                    return "新增子分类"
                }
                return "新建分类"
            }
            )
            onLoad(fun(event: OnLoadOptions){
                leaveSignal.value = 0
                val modeValue = event["mode"]
                val idValue = event["id"]
                val parentIdValue = event["parent_id"]
                val parentNameValue = event["parent_name"]
                categoryId.value = if (idValue == null) {
                    ""
                } else {
                    (idValue as String)
                }
                if (modeValue != null && (modeValue as String) == "edit" && categoryId.value != "") {
                    operationMode.value = "edit"
                    formMode.value = "edit"
                    loadDetailData(categoryId.value)
                    return
                }
                if (modeValue != null && (modeValue as String) == "add" && parentIdValue != null) {
                    operationMode.value = "add"
                    formMode.value = "create"
                    val parentIdText = parentIdValue as String
                    val parentNameText = if (parentNameValue == null) {
                        ""
                    } else {
                        UTSAndroid.consoleDebugError(decodeURIComponent(parentNameValue as String), " at pages/category/from.uvue:708")
                    }
                    parentInfo.value = _uO("id" to parentIdText, "text" to if (parentNameText == "") {
                        parentIdText
                    } else {
                        parentNameText
                    }
                    )
                    initialData.value = _uO("name" to "", "name_en" to "", "code" to "", "parent_id" to parentIdText, "parent_text" to if (parentNameText == "") {
                        parentIdText
                    } else {
                        parentNameText
                    }
                    , "description" to "", "kasa_category_id" to "", "kasa_category_text" to "", "tax_rate" to "0.23", "tax_rate_text" to "标准税率 23%", "sort_order" to "0", "is_active" to true)
                    return
                }
                operationMode.value = "create"
                formMode.value = "create"
                parentInfo.value = _uO("id" to "", "text" to "根分类（无父分类）")
                initialData.value = _uO("name" to "", "name_en" to "", "code" to "", "parent_id" to "", "parent_text" to "根分类（无父分类）", "description" to "", "kasa_category_id" to "", "kasa_category_text" to "", "tax_rate" to "0.23", "tax_rate_text" to "标准税率 23%", "sort_order" to "0", "is_active" to true)
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversaForm = resolveEasyComponent("lili-UniversaForm", GenUniModulesLiliUniversaFormComponentsLiliUniversaFormLiliUniversaFormClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to pageTitle.value, "showBack" to true, "showSearch" to false, "showHome" to true, "homePath" to "/pages/category/index", "backgroundColor" to "#EEF2F7"), null, 8, _uA(
                        "title"
                    )),
                    _cE("view", _uM("class" to "page-content"), _uA(
                        _cV(_component_lili_UniversaForm, _uM("mode" to unref(formMode), "formSections" to unref(formSections), "initialData" to unref(initialData), "leaveSignal" to unref(leaveSignal), "onSubmit" to handleSubmit, "onCancel" to handleCancel, "onDiscardLeave" to handleDiscardLeave, "onSaveRequest" to handleSaveRequest, "onDirtyChange" to handleDirtyChange, "onBottomSelectAdd" to handleBottomSelectAdd, "onBottomSelectEdit" to handleBottomSelectEdit), null, 8, _uA(
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "position" to "relative", "backgroundColor" to "#EEF2F7")), "page-content" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingLeft" to 0, "paddingRight" to 0, "paddingBottom" to 0)), "page-saving-mask" to _pS(_uM("position" to "absolute", "left" to 0, "top" to 0, "right" to 0, "bottom" to 0, "zIndex" to 9999, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "rgba(15,23,42,0.28)")), "page-saving-card" to _pS(_uM("minWidth" to 150, "paddingLeft" to 20, "paddingRight" to 20, "paddingTop" to 16, "paddingBottom" to 16, "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "backgroundColor" to "#FFFFFF", "alignItems" to "center", "justifyContent" to "center")), "page-saving-text" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#0F172A", "fontWeight" to "bold")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
