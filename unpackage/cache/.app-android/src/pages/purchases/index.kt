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
import io.dcloud.uniapp.extapi.getStorageSync as uni_getStorageSync
import io.dcloud.uniapp.extapi.getWindowInfo as uni_getWindowInfo
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.removeStorageSync as uni_removeStorageSync
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesPurchasesIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesPurchasesIndex) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesPurchasesIndex
            val _cache = __ins.renderCache
            val refreshStorageKey = "refresh:pages:purchases:index"
            val keyword = ref("")
            val filterVisible = ref(false)
            val purchases = ref(_uA<PurchaseItem>())
            val isLoading = ref(false)
            val errorMessage = ref("")
            val currentPage = ref(1)
            val totalPages = ref(1)
            val totalCount = ref(0)
            val pageSize = ref(20)
            val pageNetTotalAmount = ref("0.00")
            val filterOptionsLoading = ref(false)
            val filterOptionsError = ref("")
            val filterOptions = ref<PurchaseFilterOptionsResponse?>(null)
            val selectedFilters = ref(_uA<PurchaseSelectedFilter>())
            val supplierFilterValue = ref("")
            val supplierFilterText = ref("")
            val filterPanelHeight = ref(420)
            val filterContentHeight = ref(356)
            val pricePopupVisible = ref(false)
            val priceCalculating = ref(false)
            val pricePurchaseId = ref("")
            val pricePurchaseTitle = ref("")
            val priceFormulaValue = ref("")
            val priceFormulaText = ref("")
            val priceFormulaExpression = ref("")
            val priceFormulaDescription = ref("")
            val priceResultText = ref("")
            var importRefreshTimer: Number = 0
            var importRefreshCount: Number = 0
            val fieldConfig = ref(_uA<UTSJSONObject>(_uO("key" to "supplierText", "label" to "供应商:"), _uO("key" to "quantityText", "label" to "数量:"), _uO("key" to "progressText", "label" to "收货:"), _uO("key" to "remarkText", "label" to "备注:")))
            val menuActions = ref(_uA<UTSJSONObject>(_uO("key" to "details", "text" to "明细"), _uO("key" to "quick_input", "text" to "手写录入"), _uO("key" to "excel_upload", "text" to "Excel上传"), _uO("key" to "calculate_prices", "text" to "计算价格"), _uO("key" to "edit", "text" to "编辑"), _uO("key" to "approve", "text" to "审核"), _uO("key" to "complete", "text" to "完成"), _uO("key" to "cancel", "text" to "取消"), _uO("key" to "delete", "text" to "删除")))
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
            fun gen_parseErrorMessage_fn(error: Any, fallback: String): String {
                var message = fallback
                if (error != null) {
                    val errorText = JSON.stringify(error)
                    if (errorText != null && errorText != "") {
                        val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/purchases/index.uvue:268")
                        if (parsedError != null) {
                            val rawMessage = parsedError["message"]
                            if (rawMessage != null) {
                                val parsedMessage = rawMessage as String
                                if (parsedMessage != "") {
                                    message = parsedMessage
                                }
                            }
                        }
                        if (message == fallback) {
                            message = errorText
                        }
                    }
                }
                return message
            }
            val parseErrorMessage = ::gen_parseErrorMessage_fn
            fun gen_purchaseImportStatusValue_fn(status: UTSJSONObject): String {
                return stringValue(status["status"]).toLowerCase()
            }
            val purchaseImportStatusValue = ::gen_purchaseImportStatusValue_fn
            fun gen_isActiveImportStatusValue_fn(statusValue: String): Boolean {
                return statusValue == "queued" || statusValue == "running"
            }
            val isActiveImportStatusValue = ::gen_isActiveImportStatusValue_fn
            fun gen_purchaseImportStatusText_fn(status: UTSJSONObject): String {
                val statusValue = purchaseImportStatusValue(status)
                if (statusValue == "queued") {
                    return "上传中"
                }
                if (statusValue == "running") {
                    val percentText = stringValue(status["percent"])
                    if (percentText != "" && percentText != "0") {
                        return "导入中" + percentText + "%"
                    }
                    return "导入中"
                }
                if (statusValue == "failed") {
                    return "导入失败"
                }
                return ""
            }
            val purchaseImportStatusText = ::gen_purchaseImportStatusText_fn
            fun gen_purchaseImportMessage_fn(status: UTSJSONObject): String {
                val message = stringValue(status["message"])
                if (message != "") {
                    return message
                }
                return "该采购单正在后台导入"
            }
            val purchaseImportMessage = ::gen_purchaseImportMessage_fn
            fun gen_hasActivePurchaseImport_fn(): Boolean {
                run {
                    var index: Number = 0
                    while(index < purchases.value.length){
                        if (isActiveImportStatusValue(purchaseImportStatusValue(purchases.value[index].supplier_excel_import_status))) {
                            return true
                        }
                        index += 1
                    }
                }
                return false
            }
            val hasActivePurchaseImport = ::gen_hasActivePurchaseImport_fn
            fun gen_stopImportRefreshTimer_fn() {
                if (importRefreshTimer == 0) {
                    return
                }
                clearTimeout(importRefreshTimer)
                importRefreshTimer = 0
            }
            val stopImportRefreshTimer = ::gen_stopImportRefreshTimer_fn
            fun gen_updateFilterPanelLayout_fn() {
                val info = uni_getWindowInfo()
                var nextPanelHeight = info.windowHeight - 168
                if (nextPanelHeight > 420) {
                    nextPanelHeight = 420
                }
                if (nextPanelHeight < 320) {
                    nextPanelHeight = 320
                }
                var nextContentHeight = nextPanelHeight - 64
                if (nextContentHeight < 240) {
                    nextContentHeight = 240
                }
                filterPanelHeight.value = nextPanelHeight
                filterContentHeight.value = nextContentHeight
            }
            val updateFilterPanelLayout = ::gen_updateFilterPanelLayout_fn
            fun gen_closeFilterDrawer_fn() {
                filterVisible.value = false
            }
            val closeFilterDrawer = ::gen_closeFilterDrawer_fn
            fun gen_handleFilterVisibleChange_fn(value: Boolean) {
                filterVisible.value = value
            }
            val handleFilterVisibleChange = ::gen_handleFilterVisibleChange_fn
            fun gen_parseObject_fn(value: Any?): UTSJSONObject? {
                if (value == null) {
                    return null
                }
                val text = JSON.stringify(value)
                if (text == null || text == "") {
                    return null
                }
                return UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(text), " at pages/purchases/index.uvue:344")
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
                val parsed = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(text), " at pages/purchases/index.uvue:351")
                if (parsed == null) {
                    return _uA<UTSJSONObject>()
                }
                return parsed!!
            }
            val parseObjectArray = ::gen_parseObjectArray_fn
            fun gen_setSelectedFilterValue_fn(param: String, value: String) {
                val nextFilters: UTSArray<PurchaseSelectedFilter> = _uA()
                var updated = false
                run {
                    var index: Number = 0
                    while(index < selectedFilters.value.length){
                        val filter = selectedFilters.value[index]
                        if (filter.param == param) {
                            if (value != "") {
                                nextFilters.push(PurchaseSelectedFilter(param = param, value = value))
                            }
                            updated = true
                            index += 1
                            continue
                        }
                        nextFilters.push(filter)
                        index += 1
                    }
                }
                if (!updated && value != "") {
                    nextFilters.push(PurchaseSelectedFilter(param = param, value = value))
                }
                selectedFilters.value = nextFilters
            }
            val setSelectedFilterValue = ::gen_setSelectedFilterValue_fn
            fun gen_selectedFilterValue_fn(param: String): String {
                run {
                    var index: Number = 0
                    while(index < selectedFilters.value.length){
                        val filter = selectedFilters.value[index]
                        if (filter.param == param) {
                            return filter.value
                        }
                        index += 1
                    }
                }
                return ""
            }
            val selectedFilterValue = ::gen_selectedFilterValue_fn
            fun gen_splitSelectedValues_fn(value: String): UTSArray<String> {
                if (value == "") {
                    return _uA<String>()
                }
                val parts = value.split(",")
                val result: UTSArray<String> = _uA()
                run {
                    var index: Number = 0
                    while(index < parts.length){
                        val text = parts[index].trim()
                        if (text != "") {
                            result.push(text)
                        }
                        index += 1
                    }
                }
                return result
            }
            val splitSelectedValues = ::gen_splitSelectedValues_fn
            fun gen_extractOptionObjects_fn(value: Any?): UTSArray<UTSJSONObject> {
                val rawObject = parseObject(value)
                if (rawObject != null) {
                    var items = parseObjectArray(rawObject["items"])
                    if (items.length > 0) {
                        return items
                    }
                    items = parseObjectArray(rawObject["results"])
                    if (items.length > 0) {
                        return items
                    }
                    items = parseObjectArray(rawObject["data"])
                    if (items.length > 0) {
                        return items
                    }
                    items = parseObjectArray(rawObject["options"])
                    if (items.length > 0) {
                        return items
                    }
                    val groups = parseObjectArray(rawObject["groups"])
                    run {
                        var groupIndex: Number = 0
                        while(groupIndex < groups.length){
                            val groupItems = parseObjectArray(groups[groupIndex]["items"])
                            if (groupItems.length > 0) {
                                return groupItems
                            }
                            groupIndex += 1
                        }
                    }
                }
                return parseObjectArray(value)
            }
            val extractOptionObjects = ::gen_extractOptionObjects_fn
            fun gen_buildOptionValue_fn(item: UTSJSONObject): String {
                val directValue = stringValue(item["value"])
                if (directValue != "") {
                    return directValue
                }
                val idValue = stringValue(item["id"])
                if (idValue != "") {
                    return idValue
                }
                val codeValue = stringValue(item["code"])
                if (codeValue != "") {
                    return codeValue
                }
                return stringValue(item["key"])
            }
            val buildOptionValue = ::gen_buildOptionValue_fn
            fun gen_buildOptionText_fn(item: UTSJSONObject): String {
                val textValue = stringValue(item["text"])
                if (textValue != "") {
                    return textValue
                }
                val labelValue = stringValue(item["label"])
                if (labelValue != "") {
                    return labelValue
                }
                val fullNameValue = stringValue(item["full_name"])
                if (fullNameValue != "") {
                    return fullNameValue
                }
                val nameValue = stringValue(item["name"])
                if (nameValue != "") {
                    return nameValue
                }
                return buildOptionValue(item)
            }
            val buildOptionText = ::gen_buildOptionText_fn
            fun gen_buildSelectOptions_fn(value: Any?): UTSArray<PurchaseSelectOption> {
                val source = extractOptionObjects(value)
                val result: UTSArray<PurchaseSelectOption> = _uA()
                run {
                    var index: Number = 0
                    while(index < source.length){
                        val item = source[index]
                        val optionValue = buildOptionValue(item)
                        val optionText = buildOptionText(item)
                        if (optionValue == "" && optionText == "") {
                            index += 1
                            continue
                        }
                        result.push(PurchaseSelectOption(value = optionValue, text = if (optionText == "") {
                            optionValue
                        } else {
                            optionText
                        }
                        ))
                        index += 1
                    }
                }
                return result
            }
            val buildSelectOptions = ::gen_buildSelectOptions_fn
            fun gen_buildBottomSelectResponse_fn(source: UTSArray<PurchaseSelectOption>, params: UTSJSONObject): UTSJSONObject {
                val idValue = stringValue(params["id"])
                val result: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < source.length){
                        val option = source[index]
                        if (idValue != "" && option.value != idValue) {
                            index += 1
                            continue
                        }
                        result.push(_uO("value" to option.value, "text" to option.text))
                        index += 1
                    }
                }
                return _uO("data" to result, "total" to result.length)
            }
            val buildBottomSelectResponse = ::gen_buildBottomSelectResponse_fn
            fun gen_buildSupplierOptionQuery_fn(params: UTSJSONObject): UTSJSONObject {
                val keywordValue = stringValue(params["keyword"])
                val query: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("query", "pages/purchases/index.uvue", 466, 8), "key" to "supplier", "limit" to 50)
                if (keywordValue != "") {
                    query["search"] = keywordValue
                    query["keyword"] = keywordValue
                }
                return query
            }
            val buildSupplierOptionQuery = ::gen_buildSupplierOptionQuery_fn
            fun gen_fetchSupplierFilterOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        val raw = await(request("/api/procurement/suppliers/options/", "GET", buildSupplierOptionQuery(params), true))
                        return@w1 buildBottomSelectResponse(buildSelectOptions(raw), _uO("keyword" to "", "id" to stringValue(params["id"])))
                })
            }
            val fetchSupplierFilterOptions = ::gen_fetchSupplierFilterOptions_fn
            fun gen_fetchPricingFormulaOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        val keywordValue = stringValue(params["keyword"])
                        val idValue = stringValue(params["id"])
                        val response = await(getProductPricingFormulaList(ProductPricingFormulaListQuery(search = if (keywordValue == "") {
                            null
                        } else {
                            keywordValue
                        }
                        , page = 1, page_size = 50, is_active = "true")))
                        val result: UTSArray<UTSJSONObject> = _uA()
                        run {
                            var index: Number = 0
                            while(index < response.results.length){
                                val formula = response.results[index]
                                if (!formula.is_active) {
                                    index += 1
                                    continue
                                }
                                if (idValue != "" && formula.id.toString(10) != idValue) {
                                    index += 1
                                    continue
                                }
                                result.push(_uO("value" to formula.id.toString(10), "text" to formula.name, "subtitle" to (formula.code + " / " + formula.expression), "expression" to formula.expression, "description" to formula.description))
                                index += 1
                            }
                        }
                        return@w1 _uO("data" to result, "total" to result.length)
                })
            }
            val fetchPricingFormulaOptions = ::gen_fetchPricingFormulaOptions_fn
            fun gen_applyResponse_fn(response: PurchaseListResponse) {
                purchases.value = response.results
                currentPage.value = response.current_page
                totalPages.value = response.total_pages
                totalCount.value = response.total_count
                pageSize.value = response.page_size
                var total: Number = 0.0
                run {
                    var index: Number = 0
                    while(index < response.results.length){
                        val amount = parseFloat(response.results[index].net_total_amount)
                        if (!isNaN(amount)) {
                            total = total + amount
                        }
                        index += 1
                    }
                }
                pageNetTotalAmount.value = total.toFixed(2)
            }
            val applyResponse = ::gen_applyResponse_fn
            fun gen_loadPurchases_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (isLoading.value) {
                            return@w1
                        }
                        isLoading.value = true
                        errorMessage.value = ""
                        try {
                            val response = await(getPurchaseList(PurchaseListQuery(search = if (keyword.value == "") {
                                null
                            } else {
                                keyword.value
                            }
                            , page = currentPage.value, page_size = pageSize.value, status = if (selectedFilterValue("status") == "") {
                                null
                            } else {
                                selectedFilterValue("status")
                            }
                            , receive_status = if (selectedFilterValue("receive_status") == "") {
                                null
                            } else {
                                selectedFilterValue("receive_status")
                            }
                            , supplier = if (supplierFilterValue.value == "") {
                                null
                            } else {
                                supplierFilterValue.value
                            }
                            , date_from = null, date_to = null, min_amount = null, max_amount = null)))
                            applyResponse(response)
                        }
                         catch (error: Throwable) {
                            purchases.value = _uA()
                            currentPage.value = 1
                            totalPages.value = 1
                            totalCount.value = 0
                            pageNetTotalAmount.value = "0.00"
                            errorMessage.value = parseErrorMessage(error, "采购单加载失败")
                        }
                         finally {
                            isLoading.value = false
                            if (!hasActivePurchaseImport()) {
                                stopImportRefreshTimer()
                                importRefreshCount = 0
                            } else if (importRefreshTimer == 0) {
                                if (importRefreshCount >= 24) {
                                    return@w1
                                }
                                importRefreshCount = importRefreshCount + 1
                                importRefreshTimer = setTimeout(fun(){
                                    importRefreshTimer = 0
                                    gen_loadPurchases_fn()
                                }
                                , 10000)
                            }
                        }
                })
            }
            val loadPurchases = ::gen_loadPurchases_fn
            fun gen_purchaseToListItem_fn(item: PurchaseItem): UTSJSONObject {
                val numberText = stringValue(item.purchase_number, "采购单")
                val statusText = stringValue(item.status_display, item.status)
                val importStatus = item.supplier_excel_import_status
                val importStatusValue = purchaseImportStatusValue(importStatus)
                val importStatusText = purchaseImportStatusText(importStatus)
                val tags = _uA<String>(statusText, if (item.is_fully_received) {
                    "已收齐"
                } else {
                    "未收齐"
                }
                )
                if (importStatusText != "") {
                    tags.push(importStatusText)
                }
                return _uO("id" to item.id.toString(10), "rawId" to item.id.toString(10), "statusValue" to item.status, "importStatusValue" to importStatusValue, "importStatusMessage" to purchaseImportMessage(importStatus), "title" to numberText, "subtitle" to ("日期：" + stringValue(item.purchase_date, "-")), "amountText" to ("¥ " + stringValue(item.total_amount, "0.00")), "supplierText" to stringValue(item.supplier_name, "-"), "quantityText" to (item.received_quantity.toString(10) + "/" + item.total_quantity.toString(10)), "progressText" to (stringValue(item.receive_progress, "0") + "%"), "remarkText" to stringValue(item.remark, "-"), "tags" to tags)
            }
            val purchaseToListItem = ::gen_purchaseToListItem_fn
            fun gen_consumeRefresh_fn(): Boolean {
                val flag = uni_getStorageSync(refreshStorageKey)
                if (flag == null || ("" + flag) == "") {
                    return false
                }
                uni_removeStorageSync(refreshStorageKey)
                return true
            }
            val consumeRefresh = ::gen_consumeRefresh_fn
            fun gen_handleSearchInput_fn(value: String) {
                keyword.value = value
            }
            val handleSearchInput = ::gen_handleSearchInput_fn
            fun gen_handleSearchConfirm_fn(value: String) {
                keyword.value = value
                currentPage.value = 1
                loadPurchases()
            }
            val handleSearchConfirm = ::gen_handleSearchConfirm_fn
            fun gen_handleSearchClear_fn() {
                keyword.value = ""
                currentPage.value = 1
                loadPurchases()
            }
            val handleSearchClear = ::gen_handleSearchClear_fn
            fun gen_handleFilterOpen_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (filterOptions.value != null || filterOptionsLoading.value) {
                            return@w1
                        }
                        filterOptionsLoading.value = true
                        filterOptionsError.value = ""
                        try {
                            filterOptions.value = await(getPurchaseFilterOptions())
                        }
                         catch (error: Throwable) {
                            filterOptionsError.value = parseErrorMessage(error, "筛选选项加载失败")
                        }
                         finally {
                            filterOptionsLoading.value = false
                        }
                })
            }
            val handleFilterOpen = ::gen_handleFilterOpen_fn
            fun gen_handleSupplierFilterChange_fn(payload: UTSJSONObject) {
                supplierFilterValue.value = stringValue(payload["value"])
                supplierFilterText.value = stringValue(payload["text"])
            }
            val handleSupplierFilterChange = ::gen_handleSupplierFilterChange_fn
            fun gen_isFilterOptionSelected_fn(param: String, value: String): Boolean {
                return splitSelectedValues(selectedFilterValue(param)).includes(value)
            }
            val isFilterOptionSelected = ::gen_isFilterOptionSelected_fn
            fun gen_toggleFilterOption_fn(param: String, value: String, multiple: Boolean) {
                val currentValues = splitSelectedValues(selectedFilterValue(param))
                if (!multiple) {
                    setSelectedFilterValue(param, if (currentValues.includes(value)) {
                        ""
                    } else {
                        value
                    }
                    )
                    return
                }
                val nextValues: UTSArray<String> = _uA()
                var alreadySelected = false
                run {
                    var index: Number = 0
                    while(index < currentValues.length){
                        val currentValue = currentValues[index]
                        if (currentValue == value) {
                            alreadySelected = true
                            index += 1
                            continue
                        }
                        nextValues.push(currentValue)
                        index += 1
                    }
                }
                if (!alreadySelected) {
                    nextValues.push(value)
                }
                setSelectedFilterValue(param, nextValues.join(","))
            }
            val toggleFilterOption = ::gen_toggleFilterOption_fn
            fun gen_handleFilterReset_fn() {
                selectedFilters.value = _uA<PurchaseSelectedFilter>()
                supplierFilterValue.value = ""
                supplierFilterText.value = ""
                keyword.value = ""
                currentPage.value = 1
                closeFilterDrawer()
                loadPurchases()
            }
            val handleFilterReset = ::gen_handleFilterReset_fn
            fun gen_applySelectedFilters_fn() {
                currentPage.value = 1
                closeFilterDrawer()
                loadPurchases()
            }
            val applySelectedFilters = ::gen_applySelectedFilters_fn
            fun gen_handleCreate_fn() {
                uni_navigateTo(NavigateToOptions(url = "/pages/purchases/from"))
            }
            val handleCreate = ::gen_handleCreate_fn
            fun gen_handleItemClick_fn(payload: UTSJSONObject) {
                val id = stringValue(payload["rawId"], stringValue(payload["id"]))
                if (id != "") {
                    uni_navigateTo(NavigateToOptions(url = "/pages/purchases/details/index?purchase=" + id))
                }
            }
            val handleItemClick = ::gen_handleItemClick_fn
            fun gen_handlePageChange_fn(payload: UTSJSONObject) {
                val pageValue = payload["page"]
                if (pageValue == null) {
                    return
                }
                val nextPage = parseInt("" + pageValue)
                if (isNaN(nextPage) || nextPage <= 0 || nextPage == currentPage.value) {
                    return
                }
                currentPage.value = nextPage
                loadPurchases()
            }
            val handlePageChange = ::gen_handlePageChange_fn
            fun gen_runAction_fn(id: String, actionName: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        try {
                            await(runPurchaseAction(id, actionName))
                            uni_showToast(ShowToastOptions(title = takeLatestResponseMessage("操作成功"), icon = "success"))
                            loadPurchases()
                        }
                         catch (error: Throwable) {
                            showErrorToast(parseErrorMessage(error, "操作失败"))
                        }
                })
            }
            val runAction = ::gen_runAction_fn
            fun gen_confirmAction_fn(id: String, actionName: String, title: String, content: String) {
                uni_showModal(ShowModalOptions(title = title, content = content, success = fun(res){
                    if (res.confirm) {
                        runAction(id, actionName)
                    }
                }
                ))
            }
            val confirmAction = ::gen_confirmAction_fn
            fun gen_runDelete_fn(id: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        try {
                            await(deletePurchase(id))
                            uni_showToast(ShowToastOptions(title = takeLatestResponseMessage("删除成功"), icon = "success"))
                            loadPurchases()
                        }
                         catch (error: Throwable) {
                            showErrorToast(parseErrorMessage(error, "删除失败"))
                        }
                })
            }
            val runDelete = ::gen_runDelete_fn
            fun gen_confirmDelete_fn(id: String) {
                uni_showModal(ShowModalOptions(title = "删除采购单", content = "确定删除这张采购单吗？", success = fun(res){
                    if (res.confirm) {
                        runDelete(id)
                    }
                }
                ))
            }
            val confirmDelete = ::gen_confirmDelete_fn
            fun gen_openPricePopup_fn(itemObject: UTSJSONObject) {
                pricePurchaseId.value = stringValue(itemObject["rawId"])
                pricePurchaseTitle.value = stringValue(itemObject["title"], "采购单")
                priceFormulaValue.value = ""
                priceFormulaText.value = ""
                priceFormulaExpression.value = ""
                priceFormulaDescription.value = ""
                priceResultText.value = ""
                pricePopupVisible.value = true
            }
            val openPricePopup = ::gen_openPricePopup_fn
            fun gen_closePricePopup_fn() {
                if (priceCalculating.value) {
                    return
                }
                pricePopupVisible.value = false
            }
            val closePricePopup = ::gen_closePricePopup_fn
            fun gen_handlePriceFormulaChange_fn(payload: UTSJSONObject) {
                priceFormulaValue.value = stringValue(payload["value"])
                priceFormulaText.value = stringValue(payload["text"])
                val item = parseObject(payload["item"])
                if (item != null) {
                    priceFormulaExpression.value = stringValue(item["expression"])
                    priceFormulaDescription.value = stringValue(item["description"])
                } else {
                    priceFormulaExpression.value = ""
                    priceFormulaDescription.value = ""
                }
                priceResultText.value = ""
            }
            val handlePriceFormulaChange = ::gen_handlePriceFormulaChange_fn
            fun gen_confirmPriceCalculation_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (priceCalculating.value) {
                            return@w1
                        }
                        if (pricePurchaseId.value == "") {
                            return@w1
                        }
                        if (priceFormulaValue.value == "") {
                            uni_showToast(ShowToastOptions(title = "请选择售价公式", icon = "none", duration = 3500))
                            return@w1
                        }
                        priceCalculating.value = true
                        priceResultText.value = ""
                        try {
                            val result = await(autoGeneratePurchasePrices(pricePurchaseId.value, priceFormulaValue.value))
                            priceResultText.value = "已更新 " + result.updated_count.toString(10) + " 个，跳过已有价格 " + result.skipped_existing_count.toString(10) + " 个，异常 " + result.skipped_error_count.toString(10) + " 个。"
                            uni_showToast(ShowToastOptions(title = takeLatestResponseMessage("价格计算完成"), icon = "success"))
                            loadPurchases()
                        }
                         catch (error: Throwable) {
                            showErrorToast(parseErrorMessage(error, "价格计算失败"))
                        }
                         finally {
                            priceCalculating.value = false
                        }
                })
            }
            val confirmPriceCalculation = ::gen_confirmPriceCalculation_fn
            fun gen_handleMenu_fn(payload: UTSJSONObject) {
                val action = payload["action"]
                val item = payload["item"]
                if (action == null || item == null) {
                    return
                }
                val actionKey = stringValue((action as UTSJSONObject)["key"])
                val itemObject = item as UTSJSONObject
                val id = stringValue(itemObject["rawId"])
                if (id == "") {
                    return
                }
                if (actionKey == "details") {
                    uni_navigateTo(NavigateToOptions(url = "/pages/purchases/details/index?purchase=" + id))
                    return
                }
                if (actionKey == "quick_input") {
                    val statusValue = stringValue(itemObject["statusValue"])
                    if (statusValue != "DRAFT" && statusValue != "draft") {
                        uni_showToast(ShowToastOptions(title = "只能在草稿采购单录入", icon = "none", duration = 3500))
                        return
                    }
                    uni_navigateTo(NavigateToOptions(url = "/pages/purchases/details/quick-input?purchase=" + id))
                    return
                }
                if (actionKey == "excel_upload") {
                    val statusValue = stringValue(itemObject["statusValue"])
                    if (statusValue != "DRAFT" && statusValue != "draft") {
                        uni_showToast(ShowToastOptions(title = "只能向草稿采购单上传", icon = "none", duration = 3500))
                        return
                    }
                    val importStatusValue = stringValue(itemObject["importStatusValue"]).toLowerCase()
                    if (isActiveImportStatusValue(importStatusValue)) {
                        uni_showToast(ShowToastOptions(title = stringValue(itemObject["importStatusMessage"], "该采购单正在后台导入"), icon = "none", duration = 3500))
                        return
                    }
                    uni_navigateTo(NavigateToOptions(url = "/pages/purchases/details/excel-upload?purchase=" + id))
                    return
                }
                if (actionKey == "calculate_prices") {
                    openPricePopup(itemObject)
                    return
                }
                if (actionKey == "edit") {
                    uni_navigateTo(NavigateToOptions(url = "/pages/purchases/from?id=" + id))
                    return
                }
                if (actionKey == "delete") {
                    confirmDelete(id)
                }
                if (actionKey == "approve") {
                    confirmAction(id, "approve", "审核采购单", "确定审核这张采购单吗？")
                }
                if (actionKey == "complete") {
                    confirmAction(id, "complete", "完成采购单", "确定完成这张采购单吗？")
                }
                if (actionKey == "cancel") {
                    confirmAction(id, "cancel", "取消采购单", "确定取消这张采购单吗？")
                }
            }
            val handleMenu = ::gen_handleMenu_fn
            val listItems = computed(fun(): UTSArray<UTSJSONObject> {
                val result: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < purchases.value.length){
                        result.push(purchaseToListItem(purchases.value[index]))
                        index += 1
                    }
                }
                return result
            }
            )
            val hasActiveFilter = computed(fun(): Boolean {
                return keyword.value != "" || supplierFilterValue.value != "" || selectedFilters.value.length > 0
            }
            )
            val emptyText = computed(fun(): String {
                if (isLoading.value) {
                    return "正在加载"
                }
                if (hasActiveFilter.value) {
                    return "没有匹配的采购单"
                }
                return "暂无采购单"
            }
            )
            val summaryItems = computed(fun(): UTSArray<UTSJSONObject> {
                return _uA(
                    _uO("key" to "total", "label" to "采购单数", "value" to totalCount.value.toString(10)),
                    _uO("key" to "amount", "label" to "本页不含税", "value" to ("¥ " + pageNetTotalAmount.value)),
                    _uO("key" to "page", "label" to "页码", "value" to (currentPage.value.toString(10) + "/" + totalPages.value.toString(10)))
                )
            }
            )
            val filterPanelStyle = computed(fun(): String {
                return "height:" + filterPanelHeight.value.toString(10) + "px;"
            }
            )
            val filterContentScrollStyle = computed(fun(): String {
                return "height:" + filterContentHeight.value.toString(10) + "px;"
            }
            )
            val filterDefinitions = computed(fun(): UTSArray<PurchaseFilterDefinition> {
                if (filterOptions.value == null) {
                    return _uA<PurchaseFilterDefinition>()
                }
                return filterOptions.value!!.filters
            }
            )
            onLoad(fun(_options){
                updateFilterPanelLayout()
                loadPurchases()
            }
            )
            onShow(fun(){
                updateFilterPanelLayout()
                if (consumeRefresh()) {
                    loadPurchases()
                }
            }
            )
            onHide(fun(){
                stopImportRefreshTimer()
            }
            )
            onUnload(fun(){
                stopImportRefreshTimer()
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList", GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalListClass)
                val _component_page_container = resolveComponent("page-container")
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to "采购单", "searchPlaceholder" to "采购单号、供应商、备注", "searchValue" to unref(keyword), "filterVisible" to unref(filterVisible), "showBack" to true, "showSearch" to true, "showFilter" to true, "showHome" to true, "filterActive" to hasActiveFilter.value, "filterText" to "重置", "homePath" to "/pages/tabbar/settings", "onSearchInput" to handleSearchInput, "onSearchConfirm" to handleSearchConfirm, "onSearchClear" to handleSearchClear, "onUpdate:filterVisible" to handleFilterVisibleChange, "onFilterOpen" to handleFilterOpen), _uM("filter-panel" to withSlotCtx(fun(): UTSArray<Any> {
                        return _uA(
                            _cE("view", _uM("class" to "purchase-filter-panel", "style" to _nS(filterPanelStyle.value)), _uA(
                                _cE("scroll-view", _uM("scroll-y" to "true", "class" to "purchase-filter-content-scroll", "style" to _nS(filterContentScrollStyle.value)), _uA(
                                    _cE("view", _uM("class" to "purchase-filter-scroll-inner"), _uA(
                                        _cE("view", _uM("class" to "purchase-filter-select-group"), _uA(
                                            _cE("text", _uM("class" to "purchase-filter-select-title"), "供应商"),
                                            _cE("view", _uM("class" to "purchase-filter-select-wrap"), _uA(
                                                _cV(unref(GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelectClass), _uM("value" to unref(supplierFilterValue), "valueText" to unref(supplierFilterText), "title" to "选择供应商", "placeholder" to "请选择供应商", "searchPlaceholder" to "请输入供应商名称", "emptyText" to "暂无供应商", "showAddAction" to false, "showEditAction" to false, "fetchData" to fetchSupplierFilterOptions, "onChange" to handleSupplierFilterChange), null, 8, _uA(
                                                    "value",
                                                    "valueText"
                                                ))
                                            ))
                                        )),
                                        if (isTrue(unref(filterOptionsLoading))) {
                                            _cE("view", _uM("key" to 0, "class" to "purchase-filter-state"), _uA(
                                                _cE("text", _uM("class" to "purchase-filter-state-text"), "筛选选项加载中...")
                                            ))
                                        } else {
                                            if (unref(filterOptionsError) != "") {
                                                _cE("view", _uM("key" to 1, "class" to "purchase-filter-state"), _uA(
                                                    _cE("text", _uM("class" to "purchase-filter-state-text"), _tD(unref(filterOptionsError)), 1)
                                                ))
                                            } else {
                                                if (filterDefinitions.value.length > 0) {
                                                    _cE("view", _uM("key" to 2, "class" to "purchase-filter-groups"), _uA(
                                                        _cE(Fragment, null, RenderHelpers.renderList(filterDefinitions.value, fun(filter, __key, __index, _cached): Any {
                                                            return _cE("view", _uM("key" to filter.key, "class" to "purchase-filter-group"), _uA(
                                                                _cE("text", _uM("class" to "purchase-filter-group-title"), _tD(filter.label), 1),
                                                                _cE("view", _uM("class" to "purchase-filter-options"), _uA(
                                                                    _cE(Fragment, null, RenderHelpers.renderList(filter.options, fun(option, __key, __index, _cached): Any {
                                                                        return _cE("view", _uM("key" to (filter.key + "-" + option.value), "class" to _nC(if (isFilterOptionSelected(filter.param, option.value)) {
                                                                            "purchase-filter-option purchase-filter-option-active"
                                                                        } else {
                                                                            "purchase-filter-option"
                                                                        }), "onClick" to fun(){
                                                                            toggleFilterOption(filter.param, option.value, filter.multiple)
                                                                        }), _uA(
                                                                            _cE("text", _uM("class" to _nC(if (isFilterOptionSelected(filter.param, option.value)) {
                                                                                "purchase-filter-option-text purchase-filter-option-text-active"
                                                                            } else {
                                                                                "purchase-filter-option-text"
                                                                            })), _tD(option.label), 3)
                                                                        ), 10, _uA(
                                                                            "onClick"
                                                                        ))
                                                                    }), 128)
                                                                ))
                                                            ))
                                                        }), 128)
                                                    ))
                                                } else {
                                                    _cC("v-if", true)
                                                }
                                            }
                                        }
                                    ))
                                ), 4),
                                _cE("view", _uM("class" to "purchase-filter-actions"), _uA(
                                    _cE("view", _uM("class" to "purchase-filter-btn purchase-filter-btn-light", "onClick" to handleFilterReset), _uA(
                                        _cE("text", _uM("class" to "purchase-filter-btn-light-text"), "重置")
                                    )),
                                    _cE("view", _uM("class" to "purchase-filter-btn purchase-filter-btn-primary", "onClick" to applySelectedFilters), _uA(
                                        _cE("text", _uM("class" to "purchase-filter-btn-primary-text"), "应用")
                                    ))
                                ))
                            ), 4)
                        )
                    }
                    ), "_" to 1), 8, _uA(
                        "searchValue",
                        "filterVisible",
                        "filterActive"
                    )),
                    _cE("scroll-view", _uM("style" to _nS(_uM("flex" to "1")), "class" to "page-scroll"), _uA(
                        _cE("view", _uM("class" to "page-content"), _uA(
                            if (isTrue(unref(errorMessage) != "" && !unref(isLoading))) {
                                _cE("view", _uM("key" to 0, "class" to "error-card"), _uA(
                                    _cE("text", _uM("class" to "error-title"), "加载失败"),
                                    _cE("text", _uM("class" to "error-desc"), _tD(unref(errorMessage)), 1),
                                    _cE("view", _uM("class" to "retry-btn", "onClick" to loadPurchases), _uA(
                                        _cE("text", _uM("class" to "retry-btn-text"), "重新加载")
                                    ))
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            _cV(_component_lili_UniversalList, _uM("items" to listItems.value, "keyField" to "id", "titleField" to "title", "subtitleField" to "subtitle", "metaField" to "amountText", "tagField" to "tags", "fields" to unref(fieldConfig), "loading" to unref(isLoading), "loadingText" to "正在加载采购单", "keepContentOnLoading" to true, "inlineLoadingText" to "采购单刷新中...", "emptyText" to emptyText.value, "emptyIcon" to "◎", "showMenu" to true, "menuActions" to unref(menuActions), "showChevron" to false, "showPagination" to true, "currentPage" to unref(currentPage), "totalPages" to unref(totalPages), "totalCount" to unref(totalCount), "summaryTitle" to "采购概览", "summaryItems" to summaryItems.value, "showFloatingAdd" to true, "floatingAddText" to "新增", "onItemClick" to handleItemClick, "onMenu" to handleMenu, "onPageChange" to handlePageChange, "onFloatingAdd" to handleCreate), null, 8, _uA(
                                "items",
                                "fields",
                                "loading",
                                "emptyText",
                                "menuActions",
                                "currentPage",
                                "totalPages",
                                "totalCount",
                                "summaryItems"
                            ))
                        ))
                    ), 4),
                    _cV(_component_page_container, _uM("show" to unref(pricePopupVisible), "position" to "bottom", "round" to true, "overlay" to true, "duration" to 240, "overlay-style" to "background-color: rgba(15, 23, 42, 0.42);", "custom-style" to "background-color: #FFFFFF;", "onClickoverlay" to closePricePopup), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                        return _uA(
                            _cE("view", _uM("class" to "price-panel"), _uA(
                                _cE("view", _uM("class" to "price-handle")),
                                _cE("view", _uM("class" to "price-head"), _uA(
                                    _cE("view", null, _uA(
                                        _cE("text", _uM("class" to "price-title"), "计算基础售价"),
                                        _cE("text", _uM("class" to "price-subtitle"), _tD(unref(pricePurchaseTitle)), 1)
                                    )),
                                    _cE("view", _uM("class" to "price-close", "onClick" to closePricePopup), _uA(
                                        _cE("text", _uM("class" to "price-close-text"), "关闭")
                                    ))
                                )),
                                _cE("view", _uM("class" to "price-field"), _uA(
                                    _cE("text", _uM("class" to "price-label"), "售价公式"),
                                    _cV(unref(GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelectClass), _uM("value" to unref(priceFormulaValue), "valueText" to unref(priceFormulaText), "title" to "选择售价公式", "placeholder" to "请选择公式", "searchPlaceholder" to "搜索公式名称、编码", "emptyText" to "暂无启用公式", "subtitleKey" to "subtitle", "showAddAction" to false, "showEditAction" to true, "editPath" to "/pages/products/pricing-formula/from", "fetchData" to fetchPricingFormulaOptions, "onChange" to handlePriceFormulaChange), null, 8, _uA(
                                        "value",
                                        "valueText"
                                    ))
                                )),
                                if (unref(priceFormulaExpression) != "") {
                                    _cE("view", _uM("key" to 0, "class" to "price-formula-box"), _uA(
                                        _cE("text", _uM("class" to "price-formula-label"), "公式"),
                                        _cE("text", _uM("class" to "price-formula-text"), _tD(unref(priceFormulaExpression)), 1)
                                    ))
                                } else {
                                    _cC("v-if", true)
                                }
                                ,
                                if (unref(priceFormulaDescription) != "") {
                                    _cE("view", _uM("key" to 1, "class" to "price-formula-box"), _uA(
                                        _cE("text", _uM("class" to "price-formula-label"), "说明"),
                                        _cE("text", _uM("class" to "price-formula-text"), _tD(unref(priceFormulaDescription)), 1)
                                    ))
                                } else {
                                    _cC("v-if", true)
                                }
                                ,
                                if (unref(priceResultText) != "") {
                                    _cE("view", _uM("key" to 2, "class" to "price-result-box"), _uA(
                                        _cE("text", _uM("class" to "price-result-text"), _tD(unref(priceResultText)), 1)
                                    ))
                                } else {
                                    _cC("v-if", true)
                                }
                                ,
                                _cE("view", _uM("class" to "price-actions"), _uA(
                                    _cE("view", _uM("class" to "price-secondary-btn", "onClick" to closePricePopup), _uA(
                                        _cE("text", _uM("class" to "price-secondary-text"), "取消")
                                    )),
                                    _cE("view", _uM("class" to "price-primary-btn", "onClick" to confirmPriceCalculation), _uA(
                                        _cE("text", _uM("class" to "price-primary-text"), _tD(if (unref(priceCalculating)) {
                                            "计算中..."
                                        } else {
                                            "确认计算"
                                        }
                                        ), 1)
                                    ))
                                ))
                            ))
                        )
                    }
                    ), "_" to 1), 8, _uA(
                        "show"
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-scroll" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-content" to _pS(_uM("paddingTop" to 6, "paddingRight" to 6, "paddingBottom" to 96, "paddingLeft" to 6)), "error-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 18, "paddingRight" to 18, "paddingBottom" to 18, "paddingLeft" to 18, "marginBottom" to 10, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FECACA", "borderRightColor" to "#FECACA", "borderBottomColor" to "#FECACA", "borderLeftColor" to "#FECACA", "alignItems" to "center")), "error-title" to _pS(_uM("fontSize" to 18, "lineHeight" to "24px", "color" to "#B42318", "fontWeight" to "bold")), "error-desc" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#7F1D1D", "marginTop" to 8, "textAlign" to "center")), "retry-btn" to _pS(_uM("marginTop" to 14, "height" to 40, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#0F172A", "paddingLeft" to 18, "paddingRight" to 18, "alignItems" to "center", "justifyContent" to "center")), "retry-btn-text" to _pS(_uM("fontSize" to 14, "color" to "#FFFFFF")), "purchase-filter-panel" to _pS(_uM("position" to "relative", "paddingTop" to 2)), "purchase-filter-content-scroll" to _pS(_uM("paddingRight" to 2)), "purchase-filter-scroll-inner" to _pS(_uM("paddingBottom" to 58)), "purchase-filter-select-group" to _pS(_uM("paddingLeft" to 10, "paddingRight" to 10, "paddingTop" to 10, "paddingBottom" to 10, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#FFFFFF", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E5EAF1", "borderRightColor" to "#E5EAF1", "borderBottomColor" to "#E5EAF1", "borderLeftColor" to "#E5EAF1", "marginBottom" to 6)), "purchase-filter-group" to _pS(_uM("paddingLeft" to 10, "paddingRight" to 10, "paddingTop" to 10, "paddingBottom" to 10, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#FFFFFF", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E5EAF1", "borderRightColor" to "#E5EAF1", "borderBottomColor" to "#E5EAF1", "borderLeftColor" to "#E5EAF1", "marginBottom" to 6)), "purchase-filter-select-title" to _pS(_uM("fontSize" to 13, "lineHeight" to "17px", "color" to "#0F172A", "fontWeight" to "bold")), "purchase-filter-group-title" to _pS(_uM("fontSize" to 13, "lineHeight" to "17px", "color" to "#0F172A", "fontWeight" to "bold")), "purchase-filter-select-wrap" to _pS(_uM("marginTop" to 8)), "purchase-filter-state" to _pS(_uM("height" to 112, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#F8FAFC", "alignItems" to "center", "justifyContent" to "center")), "purchase-filter-state-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "17px", "color" to "#64748B")), "purchase-filter-groups" to _pS(_uM("marginBottom" to 6)), "purchase-filter-options" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 8)), "purchase-filter-option" to _pS(_uM("minWidth" to 48, "height" to 30, "paddingLeft" to 10, "paddingRight" to 10, "borderTopLeftRadius" to 15, "borderTopRightRadius" to 15, "borderBottomRightRadius" to 15, "borderBottomLeftRadius" to 15, "backgroundColor" to "#F8FAFC", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "alignItems" to "center", "justifyContent" to "center", "marginRight" to 6, "marginBottom" to 6)), "purchase-filter-option-active" to _pS(_uM("backgroundColor" to "#0F172A", "borderTopColor" to "#0F172A", "borderRightColor" to "#0F172A", "borderBottomColor" to "#0F172A", "borderLeftColor" to "#0F172A")), "purchase-filter-option-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "17px", "color" to "#334155")), "purchase-filter-option-text-active" to _pS(_uM("color" to "#FFFFFF")), "purchase-filter-actions" to _pS(_uM("position" to "absolute", "left" to 0, "right" to 0, "bottom" to 0, "flexDirection" to "row", "paddingTop" to 6, "paddingLeft" to 2, "paddingRight" to 2, "paddingBottom" to 4, "borderTopWidth" to 1, "borderTopStyle" to "solid", "borderTopColor" to "rgba(226,232,240,0.78)", "backgroundColor" to "#FFFFFF")), "purchase-filter-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 38, "borderTopLeftRadius" to 11, "borderTopRightRadius" to 11, "borderBottomRightRadius" to 11, "borderBottomLeftRadius" to 11, "alignItems" to "center", "justifyContent" to "center")), "purchase-filter-btn-light" to _pS(_uM("backgroundColor" to "#F3F6FA", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "marginRight" to 8)), "purchase-filter-btn-primary" to _pS(_uM("backgroundColor" to "#0F172A")), "purchase-filter-btn-light-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#475569")), "purchase-filter-btn-primary-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#FFFFFF")), "price-panel" to _pS(_uM("paddingLeft" to 16, "paddingRight" to 16, "paddingTop" to 8, "paddingBottom" to 18, "backgroundColor" to "#FFFFFF")), "price-handle" to _pS(_uM("width" to 38, "height" to 4, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2, "backgroundColor" to "#CBD5E1", "alignSelf" to "center", "marginBottom" to 12)), "price-head" to _pS(_uM("flexDirection" to "row", "alignItems" to "flex-start", "justifyContent" to "space-between", "marginBottom" to 14)), "price-title" to _pS(_uM("fontSize" to 18, "lineHeight" to "24px", "color" to "#0F172A", "fontWeight" to "bold")), "price-subtitle" to _pS(_uM("fontSize" to 12, "lineHeight" to "17px", "color" to "#64748B", "marginTop" to 2)), "price-close" to _pS(_uM("height" to 32, "paddingLeft" to 12, "paddingRight" to 12, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#F1F5F9", "alignItems" to "center", "justifyContent" to "center")), "price-close-text" to _pS(_uM("fontSize" to 12, "color" to "#475569")), "price-field" to _pS(_uM("marginBottom" to 12)), "price-label" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#334155", "fontWeight" to "bold", "marginBottom" to 6)), "price-formula-box" to _pS(_uM("paddingLeft" to 12, "paddingRight" to 12, "paddingTop" to 10, "paddingBottom" to 10, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "backgroundColor" to "#F8FAFC", "marginBottom" to 8)), "price-formula-label" to _pS(_uM("fontSize" to 12, "lineHeight" to "16px", "color" to "#64748B", "marginBottom" to 4)), "price-formula-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "18px", "color" to "#0F172A")), "price-result-box" to _pS(_uM("paddingLeft" to 12, "paddingRight" to 12, "paddingTop" to 10, "paddingBottom" to 10, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#ECFDF3", "marginBottom" to 10)), "price-result-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "18px", "color" to "#047857")), "price-actions" to _pS(_uM("flexDirection" to "row", "paddingTop" to 8)), "price-secondary-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 42, "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "#F3F6FA", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "marginRight" to 10)), "price-primary-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 42, "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "#0F172A")), "price-secondary-text" to _pS(_uM("fontSize" to 14, "color" to "#475569")), "price-primary-text" to _pS(_uM("fontSize" to 14, "color" to "#FFFFFF")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
