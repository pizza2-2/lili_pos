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
import uts.sdk.modules.limeScan.scanCode
import uts.sdk.modules.limeScan.GeneralCallbackResult
import uts.sdk.modules.limeScan.ScanCodeOption
import uts.sdk.modules.limeScan.ScanCodeSuccessCallbackResult
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
import uts.sdk.modules.liliKey.startVolumeKeyListener
import uts.sdk.modules.liliKey.stopVolumeKeyListener
import uts.sdk.modules.liliKey.VolumeKeyEvent
open class GenPagesPurchasesDetailsIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesPurchasesDetailsIndex) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesPurchasesDetailsIndex
            val _cache = __ins.renderCache
            val refreshStorageKey = "refresh:pages:purchases:details:index"
            val purchaseListRefreshStorageKey = "refresh:pages:purchases:index"
            val purchaseId = ref("")
            val purchaseInfo = ref<PurchaseItem?>(null)
            val keyword = ref("")
            val filterVisible = ref(false)
            val details = ref(_uA<PurchaseDetailItem>())
            val isLoading = ref(false)
            val errorMessage = ref("")
            val currentPage = ref(1)
            val totalPages = ref(1)
            val totalCount = ref(0)
            val pageSize = ref(20)
            val pageTotalAmount = ref("0.00")
            val filterOptionsLoading = ref(false)
            val filterOptionsError = ref("")
            val filterOptions = ref<PurchaseDetailFilterOptionsResponse?>(null)
            val selectedFilters = ref(_uA<PurchaseDetailSelectedFilter>())
            val filterPanelHeight = ref(420)
            val filterContentHeight = ref(356)
            val volumeScanLocked = ref(false)
            val scanLookupRunning = ref(false)
            val selectionMode = ref(false)
            val selectedDetailIds = ref(_uA<String>())
            val batchCategoryVisible = ref(false)
            val batchSubmitting = ref(false)
            val batchCategoryValue = ref("")
            val batchCategoryText = ref("")
            var volumeKeyStartTimer: Number = 0
            val fieldConfig = ref(_uA<UTSJSONObject>(_uO("key" to "skuText", "label" to "SKU:"), _uO("key" to "quantityText", "label" to "数量:"), _uO("key" to "progressText", "label" to "收货:")))
            val menuActions = ref(_uA<UTSJSONObject>(_uO("key" to "edit", "text" to "编辑"), _uO("key" to "receive", "text" to "收货"), _uO("key" to "delete", "text" to "删除")))
            val batchToolbarActions = ref(_uA<UTSJSONObject>(_uO("key" to "update-category", "text" to "改分类")))
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
            fun gen_booleanValue_fn(value: Any?): Boolean {
                if (value == null) {
                    return false
                }
                val text = ("" + value).toLowerCase()
                return text == "true" || text == "1" || text == "yes"
            }
            val booleanValue = ::gen_booleanValue_fn
            fun gen_numberValue_fn(value: Any?): Number {
                if (value == null) {
                    return 0
                }
                val parsed = parseInt("" + value)
                if (isNaN(parsed)) {
                    return 0
                }
                return parsed
            }
            val numberValue = ::gen_numberValue_fn
            fun gen_parseObject_fn(value: Any?): UTSJSONObject? {
                if (value == null) {
                    return null
                }
                val text = JSON.stringify(value)
                if (text == null || text == "") {
                    return null
                }
                return UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(text), " at pages/purchases/details/index.uvue:263")
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
                val parsed = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(text), " at pages/purchases/details/index.uvue:270")
                if (parsed == null) {
                    return _uA<UTSJSONObject>()
                }
                return parsed!!
            }
            val parseObjectArray = ::gen_parseObjectArray_fn
            fun gen_normalizeErrorText_fn(text: String): String {
                if (text.startsWith("Error: ")) {
                    return text.substring(7)
                }
                return text
            }
            val normalizeErrorText = ::gen_normalizeErrorText_fn
            fun gen_readErrorMessageFromObject_fn(errorObject: UTSJSONObject): String {
                val keys = _uA(
                    "message",
                    "detail",
                    "error",
                    "errMsg",
                    "msg"
                ) as UTSArray<String>
                run {
                    var index: Number = 0
                    while(index < keys.length){
                        val rawMessage = errorObject[keys[index]]
                        if (rawMessage != null) {
                            val message = normalizeErrorText(stringValue(rawMessage))
                            if (message != "") {
                                return message
                            }
                        }
                        index += 1
                    }
                }
                return ""
            }
            val readErrorMessageFromObject = ::gen_readErrorMessageFromObject_fn
            fun gen_parseErrorMessage_fn(error: Any, fallback: String): String {
                if (error == null) {
                    return fallback
                }
                val directText = normalizeErrorText(stringValue(error))
                if (directText != "" && directText != "[object Object]" && directText != "{}") {
                    return directText
                }
                val errorText = JSON.stringify(error)
                if (errorText == null || errorText == "" || errorText == "{}") {
                    return fallback
                }
                val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/purchases/details/index.uvue:298")
                if (parsedError != null) {
                    val parsedMessage = readErrorMessageFromObject(parsedError)
                    if (parsedMessage != "") {
                        return parsedMessage
                    }
                }
                return errorText
            }
            val parseErrorMessage = ::gen_parseErrorMessage_fn
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
            fun gen_setSelectedFilterValue_fn(param: String, value: String) {
                val nextFilters: UTSArray<PurchaseDetailSelectedFilter> = _uA()
                var updated = false
                run {
                    var index: Number = 0
                    while(index < selectedFilters.value.length){
                        val filter = selectedFilters.value[index]
                        if (filter.param == param) {
                            if (value != "") {
                                nextFilters.push(PurchaseDetailSelectedFilter(param = param, value = value))
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
                    nextFilters.push(PurchaseDetailSelectedFilter(param = param, value = value))
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
            fun gen_extractCategoryTreeSource_fn(value: Any?): UTSArray<UTSJSONObject> {
                val rawObject = parseObject(value)
                if (rawObject == null) {
                    return _uA<UTSJSONObject>()
                }
                val groups = parseObjectArray(rawObject["groups"])
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
                return parseObjectArray(rawObject["items"])
            }
            val extractCategoryTreeSource = ::gen_extractCategoryTreeSource_fn
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
                val nameCn = stringValue(item["name_cn"])
                if (nameCn != "") {
                    return nameCn
                }
                return buildOptionValue(item)
            }
            val buildOptionText = ::gen_buildOptionText_fn
            fun gen_buildTreeSelectItem_fn(item: UTSJSONObject): UTSJSONObject {
                val rawChildren = parseObjectArray(item["children"])
                val children: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < rawChildren.length){
                        children.push(gen_buildTreeSelectItem_fn(rawChildren[index]))
                        index += 1
                    }
                }
                val label = stringValue(item["label"], buildOptionText(item))
                val fullName = stringValue(item["full_name"])
                return _uO("value" to buildOptionValue(item), "text" to label, "label" to label, "full_name" to if (fullName == "") {
                    label
                } else {
                    fullName
                }
                , "code" to stringValue(item["code"]), "level" to stringValue(item["level"]), "parent_value" to stringValue(item["parent_value"]), "disabled" to booleanValue(item["disabled"]), "has_children" to (booleanValue(item["has_children"]) || children.length > 0), "children" to children)
            }
            val buildTreeSelectItem = ::gen_buildTreeSelectItem_fn
            fun gen_buildCategoryTreeResponse_fn(raw: Any): UTSJSONObject {
                var source = extractCategoryTreeSource(raw)
                if (source.length == 0) {
                    val rawObject = parseObject(raw)
                    if (rawObject != null) {
                        var items = parseObjectArray(rawObject["items"])
                        if (items.length == 0) {
                            items = parseObjectArray(rawObject["results"])
                        }
                        if (items.length == 0) {
                            items = parseObjectArray(rawObject["data"])
                        }
                        source = items
                    }
                }
                val result: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < source.length){
                        result.push(buildTreeSelectItem(source[index]))
                        index += 1
                    }
                }
                return _uO("data" to result, "total" to result.length)
            }
            val buildCategoryTreeResponse = ::gen_buildCategoryTreeResponse_fn
            fun gen_fetchCategoryOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        val keywordValue = stringValue(params["keyword"])
                        val pageValue = stringValue(params["page"], "1")
                        val parentValue = stringValue(params["parent"])
                        val pageSizeValue = stringValue(params["pageSize"], "20")
                        val queryParams: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("queryParams", "pages/purchases/details/index.uvue", 453, 8), "key" to "parent", "page" to parseInt(if (pageValue == "") {
                            "1"
                        } else {
                            pageValue
                        }
                        ), "page_size" to parseInt(pageSizeValue))
                        if (keywordValue != "") {
                            queryParams["search"] = keywordValue
                        }
                        if (parentValue != "") {
                            queryParams["parent"] = parentValue
                        }
                        val raw = await(request("/api/categories/categories/options/", "GET", queryParams, true))
                        return@w1 buildCategoryTreeResponse(raw)
                })
            }
            val fetchCategoryOptions = ::gen_fetchCategoryOptions_fn
            fun gen_applyResponse_fn(response: PurchaseDetailListResponse) {
                details.value = response.results
                currentPage.value = response.current_page
                totalPages.value = response.total_pages
                totalCount.value = response.total_count
                pageSize.value = response.page_size
                var total: Number = 0.0
                run {
                    var index: Number = 0
                    while(index < response.results.length){
                        val amount = parseFloat(response.results[index].amount)
                        if (!isNaN(amount)) {
                            total = total + amount
                        }
                        index += 1
                    }
                }
                pageTotalAmount.value = total.toFixed(2)
            }
            val applyResponse = ::gen_applyResponse_fn
            fun gen_clearSelectionState_fn() {
                selectionMode.value = false
                selectedDetailIds.value = _uA<String>()
            }
            val clearSelectionState = ::gen_clearSelectionState_fn
            fun gen_handleSelectionModeChange_fn(value: Boolean) {
                selectionMode.value = value
                if (!value) {
                    selectedDetailIds.value = _uA<String>()
                }
            }
            val handleSelectionModeChange = ::gen_handleSelectionModeChange_fn
            fun gen_handleSelectedDetailIdsChange_fn(value: UTSArray<String>) {
                val nextIds: UTSArray<String> = _uA()
                run {
                    var index: Number = 0
                    while(index < value.length){
                        nextIds.push(value[index])
                        index += 1
                    }
                }
                selectedDetailIds.value = nextIds
            }
            val handleSelectedDetailIdsChange = ::gen_handleSelectedDetailIdsChange_fn
            fun gen_handleSelectionExit_fn(payload: UTSJSONObject) {
                clearSelectionState()
            }
            val handleSelectionExit = ::gen_handleSelectionExit_fn
            fun gen_selectedProductIds_fn(): UTSArray<String> {
                val result: UTSArray<String> = _uA()
                run {
                    var detailIndex: Number = 0
                    while(detailIndex < details.value.length){
                        val detail = details.value[detailIndex]
                        val detailId = detail.id.toString(10)
                        if (selectedDetailIds.value.includes(detailId)) {
                            val productId = detail.product.toString(10)
                            if (productId != "" && !result.includes(productId)) {
                                result.push(productId)
                            }
                        }
                        detailIndex += 1
                    }
                }
                return result
            }
            val selectedProductIds = ::gen_selectedProductIds_fn
            fun gen_ensureBatchSelection_fn(): Boolean {
                if (selectedDetailIds.value.length > 0 && selectedProductIds().length > 0) {
                    return true
                }
                uni_showToast(ShowToastOptions(title = "请先选择采购明细", icon = "none", duration = 3500))
                return false
            }
            val ensureBatchSelection = ::gen_ensureBatchSelection_fn
            fun gen_loadPurchaseInfo_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (purchaseId.value == "") {
                            return@w1
                        }
                        try {
                            purchaseInfo.value = await(getPurchaseDetail(purchaseId.value))
                        }
                         catch (error: Throwable) {}
                })
            }
            val loadPurchaseInfo = ::gen_loadPurchaseInfo_fn
            fun gen_openDetailForm_fn(id: String) {
                if (id == "") {
                    return
                }
                uni_navigateTo(NavigateToOptions(url = "/pages/purchases/details/from?purchase=" + purchaseId.value + "&id=" + id))
            }
            val openDetailForm = ::gen_openDetailForm_fn
            fun gen_openUniqueSearchResult_fn(response: PurchaseDetailListResponse, autoOpenUnique: Boolean) {
                if (!autoOpenUnique) {
                    return
                }
                if (response.total_count != 1 || response.results.length != 1) {
                    return
                }
                val detail = response.results[0]
                openDetailForm(detail.id.toString(10))
            }
            val openUniqueSearchResult = ::gen_openUniqueSearchResult_fn
            fun loadDetails(autoOpenUnique: Boolean = false): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (isLoading.value) {
                            return@w1
                        }
                        if (purchaseId.value == "") {
                            errorMessage.value = "缺少采购单 ID"
                            return@w1
                        }
                        isLoading.value = true
                        errorMessage.value = ""
                        try {
                            val response = await(getPurchaseDetailList(PurchaseDetailListQuery(search = if (keyword.value == "") {
                                null
                            } else {
                                keyword.value
                            }
                            , page = currentPage.value, page_size = pageSize.value, purchase = purchaseId.value, product = null, is_fully_received = if (selectedFilterValue("is_fully_received") == "") {
                                null
                            } else {
                                selectedFilterValue("is_fully_received")
                            }
                            )))
                            applyResponse(response)
                            await(loadPurchaseInfo())
                            openUniqueSearchResult(response, autoOpenUnique)
                        }
                         catch (error: Throwable) {
                            details.value = _uA()
                            currentPage.value = 1
                            totalPages.value = 1
                            totalCount.value = 0
                            pageTotalAmount.value = "0.00"
                            errorMessage.value = parseErrorMessage(error, "采购明细加载失败")
                        }
                         finally {
                            isLoading.value = false
                        }
                })
            }
            fun gen_markRefreshNeeded_fn() {
                uni_setStorageSync(refreshStorageKey + ":" + purchaseId.value, "1")
            }
            val markRefreshNeeded = ::gen_markRefreshNeeded_fn
            fun gen_markPurchaseListRefreshNeeded_fn() {
                uni_setStorageSync(purchaseListRefreshStorageKey, "1")
            }
            val markPurchaseListRefreshNeeded = ::gen_markPurchaseListRefreshNeeded_fn
            fun gen_consumeRefreshNeeded_fn(): Boolean {
                val storedValue = uni_getStorageSync(refreshStorageKey + ":" + purchaseId.value)
                if (storedValue == null || ("" + storedValue) == "") {
                    return false
                }
                uni_removeStorageSync(refreshStorageKey + ":" + purchaseId.value)
                return true
            }
            val consumeRefreshNeeded = ::gen_consumeRefreshNeeded_fn
            fun gen_detailToListItem_fn(item: PurchaseDetailItem): UTSJSONObject {
                val statusText = if (item.is_fully_received) {
                    "已收齐"
                } else {
                    "待收货"
                }
                return _uO("id" to item.id.toString(10), "rawId" to item.id.toString(10), "title" to stringValue(item.product_name, "商品 #" + item.product.toString(10)), "subtitle" to ("条码：" + stringValue(item.product_barcode, "-")), "image" to item.product_image, "images" to item.product_images, "previewCover" to if (item.product_preview_images.length > 0) {
                    item.product_preview_images[0]
                } else {
                    item.product_image
                }
                , "previewImages" to item.product_preview_images, "mediaIds" to item.product_media_ids, "amountText" to ("¥ " + stringValue(item.amount, "0.00")), "skuText" to stringValue(item.product_sku, "-"), "quantityText" to (item.received_quantity.toString(10) + "/" + item.quantity.toString(10) + "，剩余 " + item.remaining_quantity.toString(10)), "progressText" to (stringValue(item.receive_progress, "0") + "%"), "notesText" to stringValue(item.notes, "-"), "tags" to _uA<String>(statusText, "单价 ¥ " + stringValue(item.unit_price, "0.00")))
            }
            val detailToListItem = ::gen_detailToListItem_fn
            fun gen_handleSearchInput_fn(value: String) {
                keyword.value = value
            }
            val handleSearchInput = ::gen_handleSearchInput_fn
            fun gen_handleSearchConfirm_fn(value: String) {
                keyword.value = value
                currentPage.value = 1
                loadDetails(value.trim() != "")
            }
            val handleSearchConfirm = ::gen_handleSearchConfirm_fn
            fun gen_handleSearchClear_fn() {
                keyword.value = ""
                currentPage.value = 1
                loadDetails()
            }
            val handleSearchClear = ::gen_handleSearchClear_fn
            fun gen_searchByScannedBarcode_fn(barcode: String) {
                keyword.value = barcode
                currentPage.value = 1
                closeFilterDrawer()
                loadDetails(barcode.trim() != "")
            }
            val searchByScannedBarcode = ::gen_searchByScannedBarcode_fn
            fun gen_handleFilterOpen_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (filterOptions.value != null || filterOptionsLoading.value) {
                            return@w1
                        }
                        filterOptionsLoading.value = true
                        filterOptionsError.value = ""
                        try {
                            filterOptions.value = await(getPurchaseDetailFilterOptions(if (purchaseId.value == "") {
                                null
                            } else {
                                purchaseId.value
                            }
                            ))
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
                selectedFilters.value = _uA<PurchaseDetailSelectedFilter>()
                keyword.value = ""
                currentPage.value = 1
                closeFilterDrawer()
                loadDetails()
            }
            val handleFilterReset = ::gen_handleFilterReset_fn
            fun gen_applySelectedFilters_fn() {
                currentPage.value = 1
                closeFilterDrawer()
                loadDetails()
            }
            val applySelectedFilters = ::gen_applySelectedFilters_fn
            fun gen_handleScannedBarcode_fn(scanResult: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        val barcode = scanResult.trim()
                        if (barcode == "" || scanLookupRunning.value) {
                            return@w1
                        }
                        if (purchaseId.value == "") {
                            searchByScannedBarcode(barcode)
                            return@w1
                        }
                        scanLookupRunning.value = true
                        try {
                            val result = await(checkPurchaseProduct(purchaseId.value, barcode, ""))
                            if (result.exists && result.purchase_detail_id > 0) {
                                closeFilterDrawer()
                                openDetailForm(result.purchase_detail_id.toString(10))
                                return@w1
                            }
                            searchByScannedBarcode(barcode)
                        }
                         catch (error: Throwable) {
                            searchByScannedBarcode(barcode)
                        }
                         finally {
                            scanLookupRunning.value = false
                        }
                })
            }
            val handleScannedBarcode = ::gen_handleScannedBarcode_fn
            fun gen_handleScanSearch_fn() {
                scanCode(ScanCodeOption(onlyFromCamera = true, success = fun(res: ScanCodeSuccessCallbackResult){
                    val scanResult = res.result
                    if (scanResult == "") {
                        return
                    }
                    handleScannedBarcode(scanResult)
                }
                , fail = fun(res: GeneralCallbackResult){
                    val message = if (res.errMsg == "") {
                        "扫码失败"
                    } else {
                        res.errMsg
                    }
                    uni_showToast(ShowToastOptions(title = message, icon = "none", duration = 3500))
                }
                ))
            }
            val handleScanSearch = ::gen_handleScanSearch_fn
            fun gen_unlockVolumeScanSoon_fn() {
                setTimeout(fun(){
                    volumeScanLocked.value = false
                }
                , 1200)
            }
            val unlockVolumeScanSoon = ::gen_unlockVolumeScanSoon_fn
            fun gen_handleVolumeKeyEvent_fn(event: VolumeKeyEvent) {
                if (event.key != "VOLUME_UP" && event.key != "VOLUME_DOWN") {
                    return
                }
                if (volumeScanLocked.value) {
                    return
                }
                volumeScanLocked.value = true
                closeFilterDrawer()
                handleScanSearch()
                unlockVolumeScanSoon()
            }
            val handleVolumeKeyEvent = ::gen_handleVolumeKeyEvent_fn
            fun gen_startPurchaseDetailVolumeKeyListener_fn() {
                startVolumeKeyListener(fun(event: VolumeKeyEvent){
                    handleVolumeKeyEvent(event)
                }
                )
            }
            val startPurchaseDetailVolumeKeyListener = ::gen_startPurchaseDetailVolumeKeyListener_fn
            fun gen_schedulePurchaseDetailVolumeKeyListener_fn() {
                if (volumeKeyStartTimer != 0) {
                    clearTimeout(volumeKeyStartTimer)
                }
                volumeKeyStartTimer = setTimeout(fun(){
                    volumeKeyStartTimer = 0
                    startPurchaseDetailVolumeKeyListener()
                }
                , 260)
            }
            val schedulePurchaseDetailVolumeKeyListener = ::gen_schedulePurchaseDetailVolumeKeyListener_fn
            fun gen_stopPurchaseDetailVolumeKeyListener_fn() {
                if (volumeKeyStartTimer != 0) {
                    clearTimeout(volumeKeyStartTimer)
                    volumeKeyStartTimer = 0
                }
                stopVolumeKeyListener()
                volumeScanLocked.value = false
            }
            val stopPurchaseDetailVolumeKeyListener = ::gen_stopPurchaseDetailVolumeKeyListener_fn
            fun gen_handleCreate_fn() {
                uni_navigateTo(NavigateToOptions(url = "/pages/purchases/details/from?purchase=" + purchaseId.value))
            }
            val handleCreate = ::gen_handleCreate_fn
            fun gen_handleItemClick_fn(payload: UTSJSONObject) {
                val id = stringValue(payload["rawId"], stringValue(payload["id"]))
                openDetailForm(id)
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
                loadDetails()
            }
            val handlePageChange = ::gen_handlePageChange_fn
            fun gen_runDelete_fn(id: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        try {
                            await(deletePurchaseDetail(id))
                            markPurchaseListRefreshNeeded()
                            uni_showToast(ShowToastOptions(title = takeLatestResponseMessage("删除成功"), icon = "success"))
                            loadDetails()
                        }
                         catch (error: Throwable) {
                            showErrorToast(parseErrorMessage(error, "删除失败"))
                        }
                })
            }
            val runDelete = ::gen_runDelete_fn
            fun gen_confirmDelete_fn(id: String) {
                uni_showModal(ShowModalOptions(title = "删除明细", content = "确定删除这条采购明细吗？", success = fun(res){
                    if (res.confirm) {
                        runDelete(id)
                    }
                }
                ))
            }
            val confirmDelete = ::gen_confirmDelete_fn
            fun gen_runReceive_fn(id: String, quantity: Number): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        try {
                            await(receivePurchaseDetail(id, quantity, "前端收货"))
                            markPurchaseListRefreshNeeded()
                            uni_showToast(ShowToastOptions(title = takeLatestResponseMessage("收货成功"), icon = "success"))
                            loadDetails()
                        }
                         catch (error: Throwable) {
                            showErrorToast(parseErrorMessage(error, "收货失败"))
                        }
                })
            }
            val runReceive = ::gen_runReceive_fn
            fun gen_handleBatchCategoryChange_fn(payload: UTSJSONObject) {
                batchCategoryValue.value = stringValue(payload["value"])
                batchCategoryText.value = stringValue(payload["text"])
            }
            val handleBatchCategoryChange = ::gen_handleBatchCategoryChange_fn
            fun gen_openBatchCategoryPopup_fn() {
                if (!ensureBatchSelection()) {
                    return
                }
                batchCategoryValue.value = ""
                batchCategoryText.value = ""
                batchCategoryVisible.value = true
            }
            val openBatchCategoryPopup = ::gen_openBatchCategoryPopup_fn
            fun gen_closeBatchCategoryPopup_fn() {
                if (batchSubmitting.value) {
                    return
                }
                batchCategoryVisible.value = false
                batchCategoryValue.value = ""
                batchCategoryText.value = ""
            }
            val closeBatchCategoryPopup = ::gen_closeBatchCategoryPopup_fn
            fun gen_batchResultMessage_fn(response: ProductBatchActionResponse, fallback: String): String {
                var message = takeLatestResponseMessage(fallback)
                if (message == "") {
                    message = fallback
                }
                val summaryObject = parseObject(response.data["summary"])
                if (summaryObject == null) {
                    return message
                }
                val successCount = numberValue(summaryObject["success_count"])
                val failureCount = numberValue(summaryObject["failure_count"])
                val skippedCount = numberValue(summaryObject["skipped_count"])
                if (failureCount > 0) {
                    return "成功" + successCount.toString(10) + "，失败" + failureCount.toString(10)
                }
                if (skippedCount > 0) {
                    return "成功" + successCount.toString(10) + "，跳过" + skippedCount.toString(10)
                }
                return message
            }
            val batchResultMessage = ::gen_batchResultMessage_fn
            fun gen_batchResultHasIssue_fn(response: ProductBatchActionResponse): Boolean {
                val summaryObject = parseObject(response.data["summary"])
                if (summaryObject == null) {
                    return false
                }
                return numberValue(summaryObject["failure_count"]) > 0 || numberValue(summaryObject["skipped_count"]) > 0
            }
            val batchResultHasIssue = ::gen_batchResultHasIssue_fn
            fun gen_executeBatchUpdateCategory_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (batchSubmitting.value) {
                            return@w1
                        }
                        if (!ensureBatchSelection()) {
                            return@w1
                        }
                        if (batchCategoryValue.value == "") {
                            uni_showToast(ShowToastOptions(title = "请选择分类", icon = "none", duration = 3500))
                            return@w1
                        }
                        val productIds = selectedProductIds()
                        batchSubmitting.value = true
                        try {
                            val response = await(batchUpdateProductCategory(productIds, batchCategoryValue.value))
                            val message = batchResultMessage(response, "批量修改分类成功")
                            val hasIssue = batchResultHasIssue(response)
                            if (hasIssue) {
                                showErrorToast(message)
                            } else {
                                uni_showToast(ShowToastOptions(title = message, icon = "success", duration = 1500))
                            }
                            batchCategoryVisible.value = false
                            batchCategoryValue.value = ""
                            batchCategoryText.value = ""
                            clearSelectionState()
                            loadDetails()
                        }
                         catch (error: Throwable) {
                            showErrorToast(parseErrorMessage(error, "批量修改分类失败"))
                        }
                         finally {
                            batchSubmitting.value = false
                        }
                })
            }
            val executeBatchUpdateCategory = ::gen_executeBatchUpdateCategory_fn
            fun gen_confirmBatchUpdateCategory_fn() {
                if (!ensureBatchSelection()) {
                    return
                }
                if (batchCategoryValue.value == "") {
                    uni_showToast(ShowToastOptions(title = "请选择分类", icon = "none", duration = 3500))
                    return
                }
                uni_showModal(ShowModalOptions(title = "批量修改分类", content = "确定将选中的 " + selectedDetailIds.value.length.toString(10) + " 条采购明细对应商品修改到该分类吗？", success = fun(res){
                    if (!res.confirm) {
                        return
                    }
                    executeBatchUpdateCategory()
                }
                ))
            }
            val confirmBatchUpdateCategory = ::gen_confirmBatchUpdateCategory_fn
            fun gen_handleBatchToolbarAction_fn(payload: UTSJSONObject) {
                val actionValue = payload["action"]
                if (actionValue == null) {
                    return
                }
                val actionKey = stringValue((actionValue as UTSJSONObject)["key"])
                if (actionKey == "update-category") {
                    openBatchCategoryPopup()
                }
            }
            val handleBatchToolbarAction = ::gen_handleBatchToolbarAction_fn
            fun gen_promptReceive_fn(item: UTSJSONObject) {
                val id = stringValue(item["rawId"])
                val defaultValue = stringValue(item["remainingValue"], "1")
                uni_showModal(ShowModalOptions(title = "采购收货", editable = true, placeholderText = "请输入本次数量", content = defaultValue, success = fun(res){
                    if (!res.confirm) {
                        return
                    }
                    val inputText = if (res.content == null) {
                        defaultValue
                    } else {
                        ("" + res.content!!)
                    }
                    val quantity = parseInt(inputText)
                    if (isNaN(quantity) || quantity <= 0) {
                        uni_showToast(ShowToastOptions(title = "请输入有效收货数量", icon = "none", duration = 3500))
                        return
                    }
                    runReceive(id, quantity)
                }
                ))
            }
            val promptReceive = ::gen_promptReceive_fn
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
                if (actionKey == "edit") {
                    openDetailForm(id)
                    return
                }
                if (actionKey == "receive") {
                    promptReceive(itemObject)
                }
                if (actionKey == "delete") {
                    confirmDelete(id)
                }
            }
            val handleMenu = ::gen_handleMenu_fn
            val pageTitle = computed(fun(): String {
                if (purchaseInfo.value == null) {
                    return "采购单明细"
                }
                val info = purchaseInfo.value as PurchaseItem
                return "明细 " + info.purchase_number
            }
            )
            val listItems = computed(fun(): UTSArray<UTSJSONObject> {
                val result: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < details.value.length){
                        val row = detailToListItem(details.value[index])
                        row["remainingValue"] = details.value[index].remaining_quantity.toString(10)
                        result.push(row)
                        index += 1
                    }
                }
                return result
            }
            )
            val batchInfoText = computed(fun(): String {
                return "已选 " + selectedDetailIds.value.length.toString(10) + " 条明细"
            }
            )
            val hasActiveFilter = computed(fun(): Boolean {
                return keyword.value != "" || selectedFilters.value.length > 0
            }
            )
            val emptyText = computed(fun(): String {
                if (isLoading.value) {
                    return "正在加载"
                }
                if (hasActiveFilter.value) {
                    return "没有匹配的采购明细"
                }
                return "暂无采购明细"
            }
            )
            val summaryItems = computed(fun(): UTSArray<UTSJSONObject> {
                return _uA(
                    _uO("key" to "total", "label" to "明细数", "value" to totalCount.value.toString(10)),
                    _uO("key" to "amount", "label" to "本页金额", "value" to ("¥ " + pageTotalAmount.value)),
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
            val filterDefinitions = computed(fun(): UTSArray<PurchaseDetailFilterDefinition> {
                if (filterOptions.value == null) {
                    return _uA<PurchaseDetailFilterDefinition>()
                }
                val result: UTSArray<PurchaseDetailFilterDefinition> = _uA()
                run {
                    var index: Number = 0
                    while(index < filterOptions.value!!.filters.length){
                        val filter = filterOptions.value!!.filters[index]
                        if (filter.param != "product" && filter.key != "product") {
                            result.push(filter)
                        }
                        index += 1
                    }
                }
                return result
            }
            )
            onLoad(fun(query: OnLoadOptions){
                updateFilterPanelLayout()
                val purchaseValue = query["purchase"]
                purchaseId.value = if (purchaseValue == null) {
                    ""
                } else {
                    ("" + purchaseValue)
                }
                loadDetails()
            }
            )
            onShow(fun(){
                schedulePurchaseDetailVolumeKeyListener()
                updateFilterPanelLayout()
                if (consumeRefreshNeeded()) {
                    loadDetails()
                }
            }
            )
            onHide(fun(){
                stopPurchaseDetailVolumeKeyListener()
            }
            )
            onUnload(fun(){
                stopPurchaseDetailVolumeKeyListener()
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList", GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalListClass)
                val _component_page_container = resolveComponent("page-container")
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to pageTitle.value, "searchPlaceholder" to "商品名、SKU、条码", "searchValue" to unref(keyword), "filterVisible" to unref(filterVisible), "showBack" to true, "showSearch" to true, "showFilter" to true, "showScan" to true, "showHome" to true, "filterActive" to hasActiveFilter.value, "filterText" to "重置", "homePath" to "/pages/purchases/index", "onSearchInput" to handleSearchInput, "onSearchConfirm" to handleSearchConfirm, "onSearchClear" to handleSearchClear, "onScan" to handleScanSearch, "onUpdate:filterVisible" to handleFilterVisibleChange, "onFilterOpen" to handleFilterOpen), _uM("filter-panel" to withSlotCtx(fun(): UTSArray<Any> {
                        return _uA(
                            _cE("view", _uM("class" to "purchase-filter-panel", "style" to _nS(filterPanelStyle.value)), _uA(
                                _cE("scroll-view", _uM("scroll-y" to "true", "class" to "purchase-filter-content-scroll", "style" to _nS(filterContentScrollStyle.value)), _uA(
                                    _cE("view", _uM("class" to "purchase-filter-scroll-inner"), _uA(
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
                        "title",
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
                                    _cE("view", _uM("class" to "retry-btn", "onClick" to fun(){
                                        loadDetails()
                                    }), _uA(
                                        _cE("text", _uM("class" to "retry-btn-text"), "重新加载")
                                    ), 8, _uA(
                                        "onClick"
                                    ))
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            _cV(_component_lili_UniversalList, _uM("items" to listItems.value, "keyField" to "id", "titleField" to "title", "subtitleField" to "subtitle", "metaField" to "amountText", "tagField" to "tags", "fields" to unref(fieldConfig), "loading" to unref(isLoading), "loadingText" to "正在加载采购明细", "keepContentOnLoading" to true, "inlineLoadingText" to "采购明细刷新中...", "emptyText" to emptyText.value, "emptyIcon" to "◎", "showMenu" to true, "menuActions" to unref(menuActions), "showChevron" to false, "showPagination" to true, "currentPage" to unref(currentPage), "totalPages" to unref(totalPages), "totalCount" to unref(totalCount), "selectionMode" to unref(selectionMode), "selectedItems" to unref(selectedDetailIds), "batchActions" to unref(batchToolbarActions), "batchInfoText" to batchInfoText.value, "summaryTitle" to "明细概览", "summaryItems" to summaryItems.value, "showFloatingAdd" to true, "floatingAddText" to "新增明细", "onUpdate:selectionMode" to handleSelectionModeChange, "onUpdate:selectedItems" to handleSelectedDetailIdsChange, "onSelectionExit" to handleSelectionExit, "onBatchAction" to handleBatchToolbarAction, "onItemClick" to handleItemClick, "onMenu" to handleMenu, "onPageChange" to handlePageChange, "onFloatingAdd" to handleCreate), null, 8, _uA(
                                "items",
                                "fields",
                                "loading",
                                "emptyText",
                                "menuActions",
                                "currentPage",
                                "totalPages",
                                "totalCount",
                                "selectionMode",
                                "selectedItems",
                                "batchActions",
                                "batchInfoText",
                                "summaryItems"
                            ))
                        ))
                    ), 4),
                    _cV(_component_page_container, _uM("show" to unref(batchCategoryVisible), "position" to "bottom", "round" to true, "overlay" to true, "duration" to 240, "overlay-style" to "background-color: rgba(15, 23, 42, 0.42);", "custom-style" to "background-color: #FFFFFF;", "onClickoverlay" to closeBatchCategoryPopup), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                        return _uA(
                            _cE("view", _uM("class" to "batch-panel"), _uA(
                                _cE("view", _uM("class" to "batch-handle")),
                                _cE("view", _uM("class" to "batch-head"), _uA(
                                    _cE("view", null, _uA(
                                        _cE("text", _uM("class" to "batch-title"), "批量修改分类"),
                                        _cE("text", _uM("class" to "batch-subtitle"), _tD(batchInfoText.value), 1)
                                    )),
                                    _cE("view", _uM("class" to "batch-close", "onClick" to closeBatchCategoryPopup), _uA(
                                        _cE("text", _uM("class" to "batch-close-text"), "关闭")
                                    ))
                                )),
                                _cE("view", _uM("class" to "batch-field"), _uA(
                                    _cE("text", _uM("class" to "batch-label"), "目标分类"),
                                    _cV(unref(GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelectClass), _uM("value" to unref(batchCategoryValue), "valueText" to unref(batchCategoryText), "title" to "选择分类", "placeholder" to "请选择分类", "searchPlaceholder" to "请输入分类名称", "emptyText" to "暂无分类", "tree" to true, "checkStrictly" to false, "expandOnClickNode" to true, "selectableLevel" to 2, "selectableLevelMessage" to "只能选择二级分类", "showAddAction" to false, "showEditAction" to false, "fetchData" to fetchCategoryOptions, "onChange" to handleBatchCategoryChange), null, 8, _uA(
                                        "value",
                                        "valueText"
                                    ))
                                )),
                                _cE("view", _uM("class" to "batch-actions"), _uA(
                                    _cE("view", _uM("class" to "batch-secondary-btn", "onClick" to closeBatchCategoryPopup), _uA(
                                        _cE("text", _uM("class" to "batch-secondary-text"), "取消")
                                    )),
                                    _cE("view", _uM("class" to "batch-primary-btn", "onClick" to confirmBatchUpdateCategory), _uA(
                                        _cE("text", _uM("class" to "batch-primary-text"), _tD(if (unref(batchSubmitting)) {
                                            "处理中..."
                                        } else {
                                            "确认修改"
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-scroll" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-content" to _pS(_uM("paddingTop" to 6, "paddingRight" to 6, "paddingBottom" to 96, "paddingLeft" to 6)), "error-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 18, "paddingRight" to 18, "paddingBottom" to 18, "paddingLeft" to 18, "marginBottom" to 10, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FECACA", "borderRightColor" to "#FECACA", "borderBottomColor" to "#FECACA", "borderLeftColor" to "#FECACA", "alignItems" to "center")), "error-title" to _pS(_uM("fontSize" to 18, "lineHeight" to "24px", "color" to "#B42318", "fontWeight" to "bold")), "error-desc" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#7F1D1D", "marginTop" to 8, "textAlign" to "center")), "retry-btn" to _pS(_uM("marginTop" to 14, "height" to 40, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#0F172A", "paddingLeft" to 18, "paddingRight" to 18, "alignItems" to "center", "justifyContent" to "center")), "retry-btn-text" to _pS(_uM("fontSize" to 14, "color" to "#FFFFFF")), "purchase-filter-panel" to _pS(_uM("position" to "relative", "paddingTop" to 2)), "purchase-filter-content-scroll" to _pS(_uM("paddingRight" to 2)), "purchase-filter-scroll-inner" to _pS(_uM("paddingBottom" to 58)), "purchase-filter-group" to _pS(_uM("paddingLeft" to 10, "paddingRight" to 10, "paddingTop" to 10, "paddingBottom" to 10, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#FFFFFF", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E5EAF1", "borderRightColor" to "#E5EAF1", "borderBottomColor" to "#E5EAF1", "borderLeftColor" to "#E5EAF1", "marginBottom" to 6)), "purchase-filter-group-title" to _pS(_uM("fontSize" to 13, "lineHeight" to "17px", "color" to "#0F172A", "fontWeight" to "bold")), "purchase-filter-state" to _pS(_uM("height" to 112, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#F8FAFC", "alignItems" to "center", "justifyContent" to "center")), "purchase-filter-state-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "17px", "color" to "#64748B")), "purchase-filter-groups" to _pS(_uM("marginBottom" to 6)), "purchase-filter-options" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 8)), "purchase-filter-option" to _pS(_uM("minWidth" to 48, "height" to 30, "paddingLeft" to 10, "paddingRight" to 10, "borderTopLeftRadius" to 15, "borderTopRightRadius" to 15, "borderBottomRightRadius" to 15, "borderBottomLeftRadius" to 15, "backgroundColor" to "#F8FAFC", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "alignItems" to "center", "justifyContent" to "center", "marginRight" to 6, "marginBottom" to 6)), "purchase-filter-option-active" to _pS(_uM("backgroundColor" to "#0F172A", "borderTopColor" to "#0F172A", "borderRightColor" to "#0F172A", "borderBottomColor" to "#0F172A", "borderLeftColor" to "#0F172A")), "purchase-filter-option-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "17px", "color" to "#334155")), "purchase-filter-option-text-active" to _pS(_uM("color" to "#FFFFFF")), "purchase-filter-actions" to _pS(_uM("position" to "absolute", "left" to 0, "right" to 0, "bottom" to 0, "flexDirection" to "row", "paddingTop" to 6, "paddingLeft" to 2, "paddingRight" to 2, "paddingBottom" to 4, "borderTopWidth" to 1, "borderTopStyle" to "solid", "borderTopColor" to "rgba(226,232,240,0.78)", "backgroundColor" to "#FFFFFF")), "purchase-filter-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 38, "borderTopLeftRadius" to 11, "borderTopRightRadius" to 11, "borderBottomRightRadius" to 11, "borderBottomLeftRadius" to 11, "alignItems" to "center", "justifyContent" to "center")), "purchase-filter-btn-light" to _pS(_uM("backgroundColor" to "#F3F6FA", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "marginRight" to 8)), "purchase-filter-btn-primary" to _pS(_uM("backgroundColor" to "#0F172A")), "purchase-filter-btn-light-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#475569")), "purchase-filter-btn-primary-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#FFFFFF")), "batch-panel" to _pS(_uM("paddingLeft" to 18, "paddingRight" to 18, "paddingTop" to 10, "paddingBottom" to 22, "backgroundColor" to "#FFFFFF")), "batch-handle" to _pS(_uM("width" to 42, "height" to 4, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2, "backgroundColor" to "#CBD5E1", "alignSelf" to "center", "marginBottom" to 14)), "batch-head" to _pS(_uM("flexDirection" to "row", "alignItems" to "flex-start", "justifyContent" to "space-between", "marginBottom" to 18)), "batch-title" to _pS(_uM("fontSize" to 18, "lineHeight" to "24px", "color" to "#0F172A", "fontWeight" to "bold")), "batch-subtitle" to _pS(_uM("fontSize" to 12, "lineHeight" to "18px", "color" to "#64748B", "marginTop" to 3)), "batch-close" to _pS(_uM("height" to 32, "paddingLeft" to 12, "paddingRight" to 12, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#F1F5F9", "alignItems" to "center", "justifyContent" to "center")), "batch-close-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "17px", "color" to "#475569")), "batch-field" to _pS(_uM("marginBottom" to 16)), "batch-label" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#0F172A", "fontWeight" to "bold", "marginBottom" to 8)), "batch-actions" to _pS(_uM("flexDirection" to "row", "marginTop" to 4)), "batch-secondary-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 42, "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "#F8FAFC", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "marginRight" to 10)), "batch-primary-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 42, "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "#0F172A")), "batch-secondary-text" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#475569", "fontWeight" to "bold")), "batch-primary-text" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#FFFFFF", "fontWeight" to "bold")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
