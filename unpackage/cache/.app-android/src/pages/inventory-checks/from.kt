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
import io.dcloud.uniapp.extapi.redirectTo as uni_redirectTo
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesInventoryChecksFrom : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesInventoryChecksFrom) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesInventoryChecksFrom
            val _cache = __ins.renderCache
            fun stringValue(value: Any?, fallback: String = ""): String {
                if (value == null) {
                    return fallback
                }
                val text = "" + value
                if (text == "") {
                    return fallback
                }
                return text
            }
            fun gen_intValue_fn(value: Any?): Number {
                val parsed = parseInt(stringValue(value))
                if (isNaN(parsed)) {
                    return 0
                }
                return parsed
            }
            val intValue = ::gen_intValue_fn
            fun gen_booleanValue_fn(value: Any?): Boolean {
                val text = stringValue(value).toLowerCase()
                return text == "true" || text == "1" || text == "yes"
            }
            val booleanValue = ::gen_booleanValue_fn
            fun gen_parseErrorMessage_fn(error: Any, fallback: String): String {
                var message = fallback
                if (error != null) {
                    val directMessage = (error as UTSError).message
                    if (directMessage != null && directMessage != "") {
                        message = directMessage
                    }
                    val text = JSON.stringify(error)
                    if (text != null && text != "") {
                        var parsedError: UTSJSONObject? = null
                        try {
                            val trimmedText = text.trim()
                            if (trimmedText != "" && trimmedText.substring(0, 1) == "{") {
                                parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(trimmedText), " at pages/inventory-checks/from.uvue:55")
                            }
                        }
                         catch (parseError: Throwable) {
                            parsedError = null
                        }
                        if (parsedError != null) {
                            val rawMessage = parsedError!!["message"]
                            if (rawMessage != null) {
                                val parsedMessage = rawMessage as String
                                if (parsedMessage != "") {
                                    message = parsedMessage
                                }
                            }
                        }
                        if (message == fallback && text != "{}") {
                            message = text
                        }
                    }
                }
                return message
            }
            val parseErrorMessage = ::gen_parseErrorMessage_fn
            fun gen_parseObject_fn(value: Any?): UTSJSONObject? {
                if (value == null) {
                    return null
                }
                val text = JSON.stringify(value)
                if (text == null || text == "") {
                    return null
                }
                val trimmedText = text.trim()
                if (trimmedText == "" || trimmedText.substring(0, 1) != "{") {
                    return null
                }
                try {
                    return UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(trimmedText), " at pages/inventory-checks/from.uvue:79")
                }
                 catch (error: Throwable) {
                    return null
                }
            }
            val parseObject = ::gen_parseObject_fn
            fun gen_parseObjectArray_fn(value: Any?): UTSArray<UTSJSONObject> {
                if (value == null) {
                    return _uA<UTSJSONObject>()
                }
                val text = JSON.stringify(value)
                if (text == null || text == "") {
                    return _uA<UTSJSONObject>()
                }
                val trimmedText = text.trim()
                if (trimmedText == "" || trimmedText.substring(0, 1) != "[") {
                    return _uA<UTSJSONObject>()
                }
                var parsed: UTSArray<UTSJSONObject>? = null
                try {
                    parsed = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(trimmedText), " at pages/inventory-checks/from.uvue:93")
                }
                 catch (error: Throwable) {
                    return _uA<UTSJSONObject>()
                }
                if (parsed == null) {
                    return _uA<UTSJSONObject>()
                }
                return parsed!!
            }
            val parseObjectArray = ::gen_parseObjectArray_fn
            fun gen_extractRows_fn(raw: Any?): UTSArray<UTSJSONObject> {
                val directArray = parseObjectArray(raw)
                if (directArray.length > 0) {
                    return directArray
                }
                val rawObject = parseObject(raw)
                if (rawObject == null) {
                    return _uA<UTSJSONObject>()
                }
                val dataArray = parseObjectArray(rawObject!!["data"])
                if (dataArray.length > 0) {
                    return dataArray
                }
                val resultsArray = parseObjectArray(rawObject!!["results"])
                if (resultsArray.length > 0) {
                    return resultsArray
                }
                val dataObject = parseObject(rawObject!!["data"])
                if (dataObject != null) {
                    val nestedResults = parseObjectArray(dataObject!!["results"])
                    if (nestedResults.length > 0) {
                        return nestedResults
                    }
                }
                return _uA<UTSJSONObject>()
            }
            val extractRows = ::gen_extractRows_fn
            fun gen_firstStringField_fn(obj: UTSJSONObject, keys: UTSArray<String>): String {
                run {
                    var index: Number = 0
                    while(index < keys.length){
                        val text = stringValue(obj[keys[index]])
                        if (text != "") {
                            return text
                        }
                        index += 1
                    }
                }
                return ""
            }
            val firstStringField = ::gen_firstStringField_fn
            fun gen_buildOptionQuery_fn(params: UTSJSONObject): UTSJSONObject {
                val pageValue = intValue(params["page"])
                val pageSizeValue = intValue(params["pageSize"])
                val query: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("query", "pages/inventory-checks/from.uvue", 129, 8), "page" to if (pageValue <= 0) {
                    1
                } else {
                    pageValue
                }
                , "page_size" to if (pageSizeValue <= 0) {
                    50
                } else {
                    pageSizeValue
                }
                )
                val keywordValue = stringValue(params["keyword"])
                if (keywordValue != "") {
                    query["search"] = keywordValue
                    query["keyword"] = keywordValue
                }
                val idValue = stringValue(params["id"])
                if (idValue != "") {
                    query["id"] = idValue
                }
                val parentValue = stringValue(params["parent"])
                if (parentValue != "") {
                    query["parent"] = parentValue
                }
                return query
            }
            val buildOptionQuery = ::gen_buildOptionQuery_fn
            fun gen_locationOption_fn(item: UTSJSONObject): UTSJSONObject {
                val value = firstStringField(item, _uA(
                    "value",
                    "id",
                    "pk"
                ))
                var text = firstStringField(item, _uA(
                    "text",
                    "label",
                    "name",
                    "name_cn",
                    "title"
                ))
                if (text == "") {
                    text = value
                }
                val code = stringValue(item["code"])
                return _uO("value" to value, "text" to text, "subtitle" to if (code == "") {
                    ""
                } else {
                    "编码 " + code
                }
                )
            }
            val locationOption = ::gen_locationOption_fn
            fun gen_buildOptionValue_fn(item: UTSJSONObject): String {
                return firstStringField(item, _uA(
                    "value",
                    "id",
                    "pk"
                ))
            }
            val buildOptionValue = ::gen_buildOptionValue_fn
            fun gen_buildOptionText_fn(item: UTSJSONObject): String {
                val fullName = firstStringField(item, _uA(
                    "full_name",
                    "full_path",
                    "path"
                ))
                if (fullName != "") {
                    return fullName
                }
                val name = firstStringField(item, _uA(
                    "text",
                    "label",
                    "name",
                    "name_cn",
                    "title"
                ))
                if (name != "") {
                    return name
                }
                return buildOptionValue(item)
            }
            val buildOptionText = ::gen_buildOptionText_fn
            fun gen_convertCategoryTreeItems_fn(items: UTSArray<UTSJSONObject>): UTSArray<UTSJSONObject> {
                val result: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < items.length){
                        val item = items[index]
                        val children = parseObjectArray(item["children"])
                        val treeChildren = gen_convertCategoryTreeItems_fn(children)
                        val label = buildOptionText(item)
                        result.push(_uO("value" to buildOptionValue(item), "text" to label, "label" to label, "full_name" to stringValue(item["full_name"], label), "code" to stringValue(item["code"]), "level" to intValue(item["level"]), "disabled" to booleanValue(item["disabled"]), "has_children" to (booleanValue(item["has_children"]) || treeChildren.length > 0), "children" to treeChildren))
                        index += 1
                    }
                }
                return result
            }
            val convertCategoryTreeItems = ::gen_convertCategoryTreeItems_fn
            fun gen_extractCategoryTreeSource_fn(value: Any?): UTSArray<UTSJSONObject> {
                val rawObject = parseObject(value)
                if (rawObject == null) {
                    return _uA<UTSJSONObject>()
                }
                val groups = parseObjectArray(rawObject!!["groups"])
                run {
                    var index: Number = 0
                    while(index < groups.length){
                        val group = groups[index]
                        if (stringValue(group["key"]) == "parent") {
                            return parseObjectArray(group["items"])
                        }
                        index += 1
                    }
                }
                if (groups.length > 0) {
                    return parseObjectArray(groups[0]["items"])
                }
                var items = parseObjectArray(rawObject!!["items"])
                if (items.length > 0) {
                    return items
                }
                items = parseObjectArray(rawObject!!["results"])
                if (items.length > 0) {
                    return items
                }
                return parseObjectArray(rawObject!!["data"])
            }
            val extractCategoryTreeSource = ::gen_extractCategoryTreeSource_fn
            fun gen_firstArrayValue_fn(value: Any?): String {
                if (value == null) {
                    return ""
                }
                val text = JSON.stringify(value)
                if (text == null || text == "") {
                    return ""
                }
                val trimmedText = text.trim()
                if (trimmedText == "" || trimmedText.substring(0, 1) != "[") {
                    return stringValue(value)
                }
                var parsed: UTSArray<Any>? = null
                try {
                    parsed = UTSAndroid.consoleDebugError(JSON.parseArray<Any>(trimmedText), " at pages/inventory-checks/from.uvue:208")
                }
                 catch (error: Throwable) {
                    return ""
                }
                if (parsed == null || parsed!!.length == 0) {
                    return ""
                }
                val firstItem = parsed!![0]
                val firstObject = parseObject(firstItem)
                if (firstObject != null) {
                    return buildOptionValue(firstObject!!)
                }
                return stringValue(firstItem)
            }
            val firstArrayValue = ::gen_firstArrayValue_fn
            fun gen_buildSelectResponse_fn(rows: UTSArray<UTSJSONObject>): UTSJSONObject {
                return _uO("data" to rows, "results" to rows, "total" to rows.length, "total_count" to rows.length)
            }
            val buildSelectResponse = ::gen_buildSelectResponse_fn
            val refreshStorageKey = "refresh:pages:inventory-checks:index"
            val itemId = ref("")
            val leaveSignal = ref(0)
            val submitting = ref(false)
            val initialData = ref<UTSJSONObject>(_uO("location" to "", "location_text" to "", "category" to "", "category_text" to "", "planned_date" to "", "purpose" to "", "description" to ""))
            val liveFormData = ref<UTSJSONObject>(_uO())
            fun gen_fetchLocationOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        val raw = await(request("/api/inventory/locations/", "GET", buildOptionQuery(params), true))
                        val sourceRows = extractRows(raw)
                        val rows: UTSArray<UTSJSONObject> = _uA()
                        run {
                            var index: Number = 0
                            while(index < sourceRows.length){
                                rows.push(locationOption(sourceRows[index]))
                                index += 1
                            }
                        }
                        return@w1 buildSelectResponse(rows)
                })
            }
            val fetchLocationOptions = ::gen_fetchLocationOptions_fn
            fun gen_fetchCategoryOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        val query = buildOptionQuery(params)
                        query["key"] = "parent"
                        val raw = await(request("/api/categories/categories/options/", "GET", query, true))
                        val rows = convertCategoryTreeItems(extractCategoryTreeSource(raw))
                        return@w1 buildSelectResponse(rows)
                })
            }
            val fetchCategoryOptions = ::gen_fetchCategoryOptions_fn
            val formSections = ref(_uA<UTSJSONObject>(_uO("key" to "base", "title" to "盘点信息", "description" to "", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "location", "textKey" to "location_text", "label" to "盘点位置", "type" to "bottomSelect", "required" to true, "title" to "选择盘点位置", "placeholder" to "请选择盘点位置", "subtitleKey" to "subtitle", "showAddAction" to false, "showEditAction" to false, "fetchData" to fetchLocationOptions), _uO("key" to "category", "textKey" to "category_text", "label" to "盘点分类", "type" to "bottomSelect", "required" to true, "title" to "选择盘点分类", "placeholder" to "请选择盘点分类", "tree" to true, "childrenKey" to "children", "expandOnClickNode" to true, "showAddAction" to true, "showEditAction" to true, "addPath" to "/pages/category/from", "editPath" to "/pages/category/from", "fetchData" to fetchCategoryOptions), _uO("key" to "planned_date", "label" to "计划日期", "type" to "datetime", "required" to true, "showTime" to false, "defaultToToday" to true, "placeholder" to "请选择计划日期"))), _uO("key" to "notes", "title" to "目的与说明", "description" to "", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "purpose", "label" to "盘点目的", "type" to "input", "placeholder" to "请输入盘点目的"), _uO("key" to "description", "label" to "盘点说明", "type" to "textarea", "placeholder" to "请输入盘点说明")))))
            fun gen_markRefreshNeeded_fn() {
                uni_setStorageSync(refreshStorageKey, "1")
            }
            val markRefreshNeeded = ::gen_markRefreshNeeded_fn
            fun gen_goBackToList_fn() {
                leaveSignal.value = leaveSignal.value + 1
                setTimeout(fun(){
                    uni_navigateBack(NavigateBackOptions(delta = 1, fail = fun(_){
                        uni_navigateTo(NavigateToOptions(url = "/pages/inventory-checks/index"))
                    }
                    ))
                }
                , 16)
            }
            val goBackToList = ::gen_goBackToList_fn
            fun gen_buildPayload_fn(data: UTSJSONObject): InventoryMutationData? {
                val locationId = intValue(data["location"])
                if (locationId <= 0) {
                    uni_showToast(ShowToastOptions(title = "请选择盘点位置", icon = "none"))
                    return null
                }
                val categoryId = intValue(data["category"])
                if (categoryId <= 0) {
                    uni_showToast(ShowToastOptions(title = "请选择盘点分类", icon = "none"))
                    return null
                }
                return InventoryMutationData(payload = _uO("location" to locationId, "check_type" to "CATEGORY", "categories" to _uA(
                    categoryId
                ), "planned_date" to stringValue(data["planned_date"]), "purpose" to stringValue(data["purpose"]), "description" to stringValue(data["description"])))
            }
            val buildPayload = ::gen_buildPayload_fn
            fun gen_loadDetail_fn(idText: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (idText == "") {
                            return@w1
                        }
                        try {
                            val detail = await(getInventoryCheckDetail(idText))
                            val categoryValue = firstArrayValue(detail["categories"])
                            initialData.value = _uO("location" to stringValue(detail["location"]), "location_text" to stringValue(detail["location_name"]), "category" to categoryValue, "category_text" to stringValue(detail["category_names"], categoryValue), "planned_date" to stringValue(detail["planned_date"]), "purpose" to stringValue(detail["purpose"]), "description" to stringValue(detail["description"]))
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error, "盘点单详情加载失败"), icon = "none"))
                        }
                })
            }
            val loadDetail = ::gen_loadDetail_fn
            fun gen_persistForm_fn(payload: UTSJSONObject): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (submitting.value) {
                            return@w1
                        }
                        val rawData = payload["formData"]
                        val data = if (rawData == null) {
                            (_uO())
                        } else {
                            (rawData as UTSJSONObject)
                        }
                        val body = buildPayload(data)
                        if (body == null) {
                            return@w1
                        }
                        submitting.value = true
                        uni_showLoading(ShowLoadingOptions(title = "保存盘点单中...", mask = true))
                        try {
                            await(updateInventoryCheck(itemId.value, body!!))
                            markRefreshNeeded()
                            uni_showToast(ShowToastOptions(title = takeLatestResponseMessage("保存盘点单成功"), icon = "success"))
                            goBackToList()
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error, "保存盘点单失败"), icon = "none"))
                        }
                         finally {
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
            fun gen_handleFormChange_fn(payload: UTSJSONObject) {
                val rawData = payload["formData"]
                if (rawData != null) {
                    liveFormData.value = rawData as UTSJSONObject
                }
            }
            val handleFormChange = ::gen_handleFormChange_fn
            fun gen_handleBottomSelectAdd_fn(payload: UTSJSONObject) {
                uni_showToast(ShowToastOptions(title = "该字段不支持新增", icon = "none"))
            }
            val handleBottomSelectAdd = ::gen_handleBottomSelectAdd_fn
            fun gen_handleBottomSelectEdit_fn(payload: UTSJSONObject) {
                uni_showToast(ShowToastOptions(title = "该字段不支持编辑", icon = "none"))
            }
            val handleBottomSelectEdit = ::gen_handleBottomSelectEdit_fn
            onLoad(fun(query: OnLoadOptions){
                val idValue = query["id"]
                if (idValue == null || idValue == "") {
                    uni_redirectTo(RedirectToOptions(url = "/pages/inventory-checks/create"))
                    return
                }
                itemId.value = "" + idValue
                loadDetail(itemId.value)
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversaForm = resolveEasyComponent("lili-UniversaForm", GenUniModulesLiliUniversaFormComponentsLiliUniversaFormLiliUniversaFormClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to "编辑盘点单", "showBack" to true, "showSearch" to false, "showHome" to true, "homePath" to "/pages/inventory-checks/index", "backgroundColor" to "#EEF2F7")),
                    _cE("view", _uM("class" to "page-content"), _uA(
                        _cV(_component_lili_UniversaForm, _uM("mode" to "edit", "formSections" to unref(formSections), "initialData" to unref(initialData), "leaveSignal" to unref(leaveSignal), "onSubmit" to handleSubmit, "onCancel" to handleCancel, "onDiscardLeave" to handleDiscardLeave, "onSaveRequest" to handleSaveRequest, "onDirtyChange" to handleDirtyChange, "onFormChange" to handleFormChange, "onBottomSelectAdd" to handleBottomSelectAdd, "onBottomSelectEdit" to handleBottomSelectEdit), null, 8, _uA(
                            "formSections",
                            "initialData",
                            "leaveSignal"
                        ))
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#EEF2F7")), "page-content" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingBottom" to 0)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
