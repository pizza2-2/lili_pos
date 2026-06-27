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
open class GenPagesInventoryTransfersFrom : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesInventoryTransfersFrom) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesInventoryTransfersFrom
            val _cache = __ins.renderCache
            fun gen_normalizeDateNumber_fn(value: Number): String {
                if (value < 10) {
                    return "0" + value
                }
                return "" + value
            }
            val normalizeDateNumber = ::gen_normalizeDateNumber_fn
            fun gen_todayDateText_fn(): String {
                val now = Date()
                return now.getFullYear().toString(10) + "-" + normalizeDateNumber(now.getMonth() + 1) + "-" + normalizeDateNumber(now.getDate())
            }
            val todayDateText = ::gen_todayDateText_fn
            fun gen_initialFormData_fn(): UTSJSONObject {
                return _uO("transfer_overview" to "", "transfer_number" to "", "status" to "DRAFT", "status_text" to "草稿", "total_quantity" to "0", "transferred_quantity" to "0", "items_count" to "0", "from_location" to "", "from_location_text" to "", "to_location" to "", "to_location_text" to "", "transfer_date" to todayDateText(), "remark" to "", "item_product" to "", "item_product_text" to "", "item_product_image" to "", "item_quantity" to "1", "item_notes" to "")
            }
            val initialFormData = ::gen_initialFormData_fn
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
            fun gen_parseErrorMessage_fn(error: Any, fallback: String): String {
                var message = fallback
                if (error != null) {
                    val text = JSON.stringify(error)
                    if (text != null && text != "") {
                        var parsedError: UTSJSONObject? = null
                        try {
                            parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(text), " at pages/inventory-transfers/from.uvue:115")
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
                    return UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(trimmedText), " at pages/inventory-transfers/from.uvue:139")
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
                    parsed = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(trimmedText), " at pages/inventory-transfers/from.uvue:153")
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
                val itemsArray = parseObjectArray(rawObject!!["items"])
                if (itemsArray.length > 0) {
                    return itemsArray
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
            fun gen_firstImageFromStock_fn(stock: UTSJSONObject): String {
                val direct = stringValue(stock["product_image"])
                if (direct != "") {
                    return direct
                }
                val mediaFiles = parseObjectArray(stock["product_media_files"])
                run {
                    var index: Number = 0
                    while(index < mediaFiles.length){
                        val image = firstStringField(mediaFiles[index], _uA(
                            "signed_thumbnail_url",
                            "signed_download_url",
                            "file_url",
                            "url",
                            "image"
                        ))
                        if (image != "") {
                            return image
                        }
                        index += 1
                    }
                }
                return ""
            }
            val firstImageFromStock = ::gen_firstImageFromStock_fn
            fun gen_statusLabel_fn(status: String, display: String): String {
                if (display != "") {
                    return display
                }
                if (status == "APPROVED") {
                    return "已审核"
                }
                if (status == "COMPLETED") {
                    return "已完成"
                }
                if (status == "CANCELLED") {
                    return "已取消"
                }
                return "草稿"
            }
            val statusLabel = ::gen_statusLabel_fn
            fun gen_buildOptionQuery_fn(params: UTSJSONObject): UTSJSONObject {
                val pageValue = intValue(params["page"])
                val pageSizeValue = intValue(params["pageSize"])
                val query: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("query", "pages/inventory-transfers/from.uvue", 210, 8), "page" to if (pageValue <= 0) {
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
            fun gen_stockOption_fn(item: UTSJSONObject): UTSJSONObject {
                val productValue = firstStringField(item, _uA(
                    "product",
                    "product_id"
                ))
                val title = stringValue(item["product_name"], productValue)
                val barcode = stringValue(item["product_barcode"])
                val sku = stringValue(item["product_sku"])
                val location = stringValue(item["location_name"])
                var subtitle = "可用 " + stringValue(item["available_quantity"], stringValue(item["quantity"], "0"))
                if (location != "") {
                    subtitle = "位置 " + location + " / " + subtitle
                }
                if (barcode != "") {
                    subtitle = subtitle + " / 条码 " + barcode
                } else if (sku != "") {
                    subtitle = subtitle + " / SKU " + sku
                }
                return _uO("value" to productValue, "text" to title, "subtitle" to subtitle, "image" to firstImageFromStock(item))
            }
            val stockOption = ::gen_stockOption_fn
            fun gen_buildSelectResponse_fn(rows: UTSArray<UTSJSONObject>): UTSJSONObject {
                return _uO("data" to rows, "results" to rows, "total" to rows.length, "total_count" to rows.length)
            }
            val buildSelectResponse = ::gen_buildSelectResponse_fn
            val refreshStorageKey = "refresh:pages:inventory-transfers:index"
            val formMode = ref("edit")
            val itemId = ref("")
            val leaveSignal = ref(0)
            val submitting = ref(false)
            val initialData = ref<UTSJSONObject>(initialFormData())
            val liveFormData = ref<UTSJSONObject>(initialFormData())
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
            fun gen_fetchProductStockOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        val query = buildOptionQuery(params)
                        val fromLocation = stringValue(liveFormData.value["from_location"], stringValue(initialData.value["from_location"]))
                        if (fromLocation != "") {
                            query["location"] = fromLocation
                        }
                        val raw = await(request("/api/inventory/stocks/", "GET", query, true))
                        val sourceRows = extractRows(raw)
                        val rows: UTSArray<UTSJSONObject> = _uA()
                        run {
                            var index: Number = 0
                            while(index < sourceRows.length){
                                val row = stockOption(sourceRows[index])
                                if (stringValue(row["value"]) != "") {
                                    rows.push(row)
                                }
                                index += 1
                            }
                        }
                        return@w1 buildSelectResponse(rows)
                })
            }
            val fetchProductStockOptions = ::gen_fetchProductStockOptions_fn
            val formSections = ref(_uA<UTSJSONObject>(_uO("key" to "summary", "title" to "调拨概览", "description" to "", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "transfer_overview", "label" to "调拨信息", "type" to "custom", "readonly" to true), _uO("key" to "transfer_number", "label" to "调拨单号", "type" to "input", "readonly" to true, "hidden" to true), _uO("key" to "status", "label" to "状态值", "type" to "input", "readonly" to true, "hidden" to true), _uO("key" to "status_text", "label" to "调拨状态", "type" to "input", "readonly" to true, "hidden" to true), _uO("key" to "total_quantity", "label" to "调拨总数", "type" to "input", "readonly" to true, "hidden" to true), _uO("key" to "transferred_quantity", "label" to "已调拨数量", "type" to "input", "readonly" to true, "hidden" to true), _uO("key" to "items_count", "label" to "明细数", "type" to "input", "readonly" to true, "hidden" to true))), _uO("key" to "route", "title" to "调拨位置", "description" to "", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "from_location", "textKey" to "from_location_text", "label" to "调出位置", "type" to "bottomSelect", "required" to true, "title" to "选择调出位置", "placeholder" to "请选择调出位置", "subtitleKey" to "subtitle", "showAddAction" to false, "showEditAction" to false, "fetchData" to fetchLocationOptions), _uO("key" to "to_location", "textKey" to "to_location_text", "label" to "调入位置", "type" to "bottomSelect", "required" to true, "title" to "选择调入位置", "placeholder" to "请选择调入位置", "subtitleKey" to "subtitle", "showAddAction" to false, "showEditAction" to false, "fetchData" to fetchLocationOptions))), _uO("key" to "date_remark", "title" to "日期与备注", "description" to "", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "transfer_date", "label" to "调拨日期", "type" to "datetime", "required" to true, "showTime" to false, "defaultToToday" to true, "placeholder" to "请选择调拨日期"), _uO("key" to "remark", "label" to "备注", "type" to "textarea", "placeholder" to "请输入备注")))))
            val pageTitle = computed(fun(): String {
                return "编辑调拨单"
            }
            )
            val transferNumberText = computed(fun(): String {
                return stringValue(initialData.value["transfer_number"], "调拨单")
            }
            )
            val transferStatusText = computed(fun(): String {
                return stringValue(initialData.value["status_text"], "草稿")
            }
            )
            val totalQuantityText = computed(fun(): String {
                return stringValue(initialData.value["total_quantity"], "0")
            }
            )
            val transferredQuantityText = computed(fun(): String {
                return stringValue(initialData.value["transferred_quantity"], "0")
            }
            )
            val itemsCountText = computed(fun(): String {
                return stringValue(initialData.value["items_count"], "0")
            }
            )
            val transferRouteText = computed(fun(): String {
                val fromName = stringValue(liveFormData.value["from_location_text"], stringValue(initialData.value["from_location_text"], "调出位置"))
                val toName = stringValue(liveFormData.value["to_location_text"], stringValue(initialData.value["to_location_text"], "调入位置"))
                return fromName + " -> " + toName
            }
            )
            fun gen_markRefreshNeeded_fn() {
                uni_setStorageSync(refreshStorageKey, "1")
            }
            val markRefreshNeeded = ::gen_markRefreshNeeded_fn
            fun gen_goBackToList_fn() {
                leaveSignal.value = leaveSignal.value + 1
                setTimeout(fun(){
                    uni_navigateBack(NavigateBackOptions(delta = 1, fail = fun(_){
                        uni_navigateTo(NavigateToOptions(url = "/pages/inventory-transfers/index"))
                    }
                    ))
                }
                , 16)
            }
            val goBackToList = ::gen_goBackToList_fn
            fun gen_buildInitialDataFromDetail_fn(detail: UTSJSONObject): UTSJSONObject {
                val data = initialFormData()
                data["transfer_number"] = stringValue(detail["transfer_number"])
                data["status"] = stringValue(detail["status"], "DRAFT")
                data["status_text"] = statusLabel(stringValue(detail["status"]), stringValue(detail["status_display"]))
                data["total_quantity"] = stringValue(detail["total_quantity"], "0")
                data["transferred_quantity"] = stringValue(detail["transferred_quantity"], "0")
                data["from_location"] = stringValue(detail["from_location"])
                data["from_location_text"] = stringValue(detail["from_location_name"])
                data["to_location"] = stringValue(detail["to_location"])
                data["to_location_text"] = stringValue(detail["to_location_name"])
                data["transfer_date"] = stringValue(detail["transfer_date"], todayDateText())
                data["remark"] = stringValue(detail["remark"])
                val detailItems = parseObjectArray(detail["items"])
                data["items_count"] = detailItems.length.toString(10)
                if (detailItems.length > 0) {
                    val firstItem = detailItems[0]
                    val productName = stringValue(firstItem["product_name"], stringValue(firstItem["product"]))
                    val sku = stringValue(firstItem["product_sku"])
                    data["item_product"] = stringValue(firstItem["product"])
                    data["item_product_text"] = if (sku == "") {
                        productName
                    } else {
                        productName + " / SKU " + sku
                    }
                    data["item_quantity"] = stringValue(firstItem["quantity"], "1")
                    data["item_notes"] = stringValue(firstItem["notes"])
                }
                return data
            }
            val buildInitialDataFromDetail = ::gen_buildInitialDataFromDetail_fn
            fun gen_loadDetail_fn(idText: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (idText == "") {
                            return@w1
                        }
                        try {
                            val detail = await(getInventoryTransferDetail(idText))
                            val data = buildInitialDataFromDetail(detail)
                            initialData.value = data
                            liveFormData.value = data
                        }
                         catch (error: Throwable) {
                            showErrorToast(parseErrorMessage(error, "调拨单详情加载失败"))
                        }
                })
            }
            val loadDetail = ::gen_loadDetail_fn
            fun gen_buildPayload_fn(data: UTSJSONObject): InventoryMutationData? {
                val fromLocation = intValue(data["from_location"])
                val toLocation = intValue(data["to_location"])
                val transferDate = stringValue(data["transfer_date"], todayDateText())
                if (fromLocation <= 0) {
                    uni_showToast(ShowToastOptions(title = "请选择调出位置", icon = "none", duration = 3500))
                    return null
                }
                if (toLocation <= 0) {
                    uni_showToast(ShowToastOptions(title = "请选择调入位置", icon = "none", duration = 3500))
                    return null
                }
                if (fromLocation == toLocation) {
                    uni_showToast(ShowToastOptions(title = "调入位置不能与调出位置相同", icon = "none", duration = 3500))
                    return null
                }
                val payload: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("payload", "pages/inventory-transfers/from.uvue", 412, 8), "from_location" to fromLocation, "to_location" to toLocation, "transfer_date" to transferDate, "remark" to stringValue(data["remark"]))
                return InventoryMutationData(payload = payload)
            }
            val buildPayload = ::gen_buildPayload_fn
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
                        if (itemId.value == "") {
                            uni_showToast(ShowToastOptions(title = "缺少调拨单 ID", icon = "none", duration = 3500))
                            return@w1
                        }
                        val actionText = "保存调拨单"
                        submitting.value = true
                        uni_showLoading(ShowLoadingOptions(title = actionText + "中...", mask = true))
                        try {
                            await(updateInventoryTransfer(itemId.value, body!!))
                            markRefreshNeeded()
                            uni_showToast(ShowToastOptions(title = takeLatestResponseMessage(actionText + "成功"), icon = "success"))
                            goBackToList()
                        }
                         catch (error: Throwable) {
                            showErrorToast(parseErrorMessage(error, actionText + "失败"))
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
                uni_showToast(ShowToastOptions(title = "该字段不支持新增", icon = "none", duration = 3500))
            }
            val handleBottomSelectAdd = ::gen_handleBottomSelectAdd_fn
            fun gen_handleBottomSelectEdit_fn(payload: UTSJSONObject) {
                uni_showToast(ShowToastOptions(title = "该字段不支持编辑", icon = "none", duration = 3500))
            }
            val handleBottomSelectEdit = ::gen_handleBottomSelectEdit_fn
            onLoad(fun(query: OnLoadOptions){
                val idValue = query["id"]
                val idText = if (idValue == null) {
                    ""
                } else {
                    ("" + idValue)
                }
                if (idText != "") {
                    itemId.value = idText
                    loadDetail(idText)
                    return
                }
                uni_redirectTo(RedirectToOptions(url = "/pages/inventory-transfers/create"))
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversaForm = resolveEasyComponent("lili-UniversaForm", GenUniModulesLiliUniversaFormComponentsLiliUniversaFormLiliUniversaFormClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to pageTitle.value, "showBack" to true, "showSearch" to false, "showHome" to true, "homePath" to "/pages/inventory-transfers/index", "backgroundColor" to "#EEF2F7"), null, 8, _uA(
                        "title"
                    )),
                    _cE("view", _uM("class" to "page-content"), _uA(
                        _cV(_component_lili_UniversaForm, _uM("mode" to unref(formMode), "formSections" to unref(formSections), "initialData" to unref(initialData), "leaveSignal" to unref(leaveSignal), "onSubmit" to handleSubmit, "onCancel" to handleCancel, "onDiscardLeave" to handleDiscardLeave, "onSaveRequest" to handleSaveRequest, "onDirtyChange" to handleDirtyChange, "onFormChange" to handleFormChange, "onBottomSelectAdd" to handleBottomSelectAdd, "onBottomSelectEdit" to handleBottomSelectEdit), _uM("field-transfer_overview" to withScopedSlotCtx(fun(slotProps: Record<String, Any?>): UTSArray<Any> {
                            val value = slotProps["value"]
                            return _uA(
                                _cE("view", _uM("class" to "transfer-card"), _uA(
                                    _cE("view", _uM("class" to "transfer-card-head"), _uA(
                                        _cE("view", _uM("class" to "transfer-title-wrap"), _uA(
                                            _cE("text", _uM("class" to "transfer-title"), _tD(transferNumberText.value), 1),
                                            _cE("text", _uM("class" to "transfer-subtitle"), _tD(transferRouteText.value), 1)
                                        )),
                                        _cE("text", _uM("class" to "transfer-status"), _tD(transferStatusText.value), 1)
                                    )),
                                    _cE("view", _uM("class" to "transfer-metrics"), _uA(
                                        _cE("view", _uM("class" to "transfer-metric"), _uA(
                                            _cE("text", _uM("class" to "transfer-metric-label"), "调拨总数"),
                                            _cE("text", _uM("class" to "transfer-metric-value"), _tD(totalQuantityText.value), 1)
                                        )),
                                        _cE("view", _uM("class" to "transfer-metric"), _uA(
                                            _cE("text", _uM("class" to "transfer-metric-label"), "已调拨"),
                                            _cE("text", _uM("class" to "transfer-metric-value"), _tD(transferredQuantityText.value), 1)
                                        )),
                                        _cE("view", _uM("class" to "transfer-metric transfer-metric-last"), _uA(
                                            _cE("text", _uM("class" to "transfer-metric-label"), "明细数"),
                                            _cE("text", _uM("class" to "transfer-metric-value"), _tD(itemsCountText.value), 1)
                                        ))
                                    ))
                                ))
                            )
                        }
                        ), "_" to 1), 8, _uA(
                            "mode",
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#EEF2F7")), "page-content" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingBottom" to 0)), "transfer-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12)), "transfer-card-head" to _pS(_uM("flexDirection" to "row", "alignItems" to "flex-start", "justifyContent" to "space-between")), "transfer-title-wrap" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingRight" to 8)), "transfer-title" to _pS(_uM("fontSize" to 16, "lineHeight" to "22px", "color" to "#0F172A", "fontWeight" to "bold")), "transfer-subtitle" to _pS(_uM("marginTop" to 3, "fontSize" to 12, "lineHeight" to "17px", "color" to "#475569")), "transfer-status" to _pS(_uM("height" to 24, "lineHeight" to "24px", "paddingLeft" to 8, "paddingRight" to 8, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#E0F2FE", "color" to "#0369A1", "fontSize" to 11, "fontWeight" to "bold")), "transfer-metrics" to _pS(_uM("flexDirection" to "row", "marginTop" to 12)), "transfer-metric" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "minHeight" to 54, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#F8FAFC", "alignItems" to "center", "justifyContent" to "center", "marginRight" to 6)), "transfer-metric-last" to _pS(_uM("marginRight" to 0)), "transfer-metric-label" to _pS(_uM("fontSize" to 11, "lineHeight" to "15px", "color" to "#64748B")), "transfer-metric-value" to _pS(_uM("marginTop" to 4, "fontSize" to 18, "lineHeight" to "24px", "color" to "#0F172A", "fontWeight" to "bold")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
