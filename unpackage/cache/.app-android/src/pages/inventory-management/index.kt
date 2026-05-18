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
import io.dcloud.uniapp.extapi.setClipboardData as uni_setClipboardData
import io.dcloud.uniapp.extapi.showToast as uni_showToast
import uts.sdk.modules.liliKey.startVolumeKeyListener
import uts.sdk.modules.liliKey.stopVolumeKeyListener
import uts.sdk.modules.liliKey.VolumeKeyEvent
open class GenPagesInventoryManagementIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesInventoryManagementIndex) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesInventoryManagementIndex
            val _cache = __ins.renderCache
            val refreshStorageKey = "refresh:pages:inventory-management:index"
            val keyword = ref("")
            val filterVisible = ref(false)
            val stocks = ref(_uA<UTSJSONObject>())
            val isLoading = ref(false)
            val errorMessage = ref("")
            val currentPage = ref(1)
            val totalPages = ref(1)
            val totalCount = ref(0)
            val pageSize = ref(20)
            val totalQuantityText = ref("0")
            val alertCountText = ref("0")
            val supplierFilterValue = ref("")
            val supplierFilterText = ref("")
            val categoryFilterValues = ref(_uA<String>())
            val locationFilterValue = ref("")
            val locationFilterText = ref("")
            val alertStatusFilter = ref("")
            val listedStatusFilter = ref("")
            val draftSupplierValue = ref("")
            val draftSupplierText = ref("")
            val draftCategoryValues = ref(_uA<String>())
            val draftLocationValue = ref("")
            val draftLocationText = ref("")
            val draftAlertStatus = ref("")
            val draftListedStatus = ref("")
            val filterPanelHeight = ref(420)
            val filterContentHeight = ref(356)
            val volumeScanLocked = ref(false)
            val scanLookupRunning = ref(false)
            val productFilterId = ref("")
            val productFilterName = ref("")
            var volumeKeyStartTimer: Number = 0
            val fieldConfig = ref(_uA<UTSJSONObject>(_uO("key" to "quantityText", "label" to "库存:"), _uO("key" to "availableText", "label" to "可用:"), _uO("key" to "costText", "label" to "成本:"), _uO("key" to "categoryText", "label" to "分类:")))
            val menuActions = ref(_uA<UTSJSONObject>(_uO("key" to "adjust", "text" to "调整库存"), _uO("key" to "reload", "text" to "刷新")))
            val tagColorMap = ref<UTSJSONObject>(_uO("正常" to "success", "低库存" to "warning", "售罄" to "danger", "久未变动" to "warning", "上架" to "success", "下架" to "muted"))
            val alertStatusOptions = _uA(
                SelectOption__8(value = "", label = "全部"),
                SelectOption__8(value = "NORMAL", label = "正常"),
                SelectOption__8(value = "LOW_STOCK", label = "低库存"),
                SelectOption__8(value = "OUT_OF_STOCK", label = "售罄"),
                SelectOption__8(value = "NO_MOVEMENT", label = "久未变动")
            )
            val listedStatusOptions = _uA(
                SelectOption__8(value = "", label = "全部"),
                SelectOption__8(value = "true", label = "上架"),
                SelectOption__8(value = "false", label = "下架")
            )
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
                    val directMessage = (error as UTSError).message
                    if (directMessage != null && directMessage != "") {
                        message = directMessage
                    }
                    val errorText = JSON.stringify(error)
                    if (errorText != null && errorText != "") {
                        val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/inventory-management/index.uvue:290")
                        if (parsedError != null) {
                            val rawMessage = parsedError["message"]
                            if (rawMessage != null) {
                                val parsedMessage = rawMessage as String
                                if (parsedMessage != "") {
                                    message = parsedMessage
                                }
                            }
                        }
                        if (message == fallback && errorText != "{}") {
                            message = errorText
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
                try {
                    return UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(text), " at pages/inventory-management/index.uvue:309")
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
                var parsed: UTSArray<UTSJSONObject>? = null
                try {
                    parsed = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(text), " at pages/inventory-management/index.uvue:321")
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
            fun gen_parseStringArray_fn(value: Any?): UTSArray<String> {
                if (value == null) {
                    return _uA<String>()
                }
                val text = JSON.stringify(value)
                if (text == null || text == "") {
                    return _uA<String>()
                }
                var parsed: UTSArray<String>? = null
                try {
                    parsed = UTSAndroid.consoleDebugError(JSON.parseArray<String>(text), " at pages/inventory-management/index.uvue:335")
                }
                 catch (error: Throwable) {
                    return _uA<String>()
                }
                if (parsed == null) {
                    return _uA<String>()
                }
                return parsed!!
            }
            val parseStringArray = ::gen_parseStringArray_fn
            fun gen_firstStringField_fn(obj: UTSJSONObject, keys: UTSArray<String>): String {
                run {
                    var index: Number = 0
                    while(index < keys.length){
                        val value = stringValue(obj[keys[index]])
                        if (value != "") {
                            return value
                        }
                        index += 1
                    }
                }
                return ""
            }
            val firstStringField = ::gen_firstStringField_fn
            fun gen_updateFilterPanelLayout_fn() {
                val info = uni_getWindowInfo()
                var nextPanelHeight = info.windowHeight - 168
                if (nextPanelHeight > 460) {
                    nextPanelHeight = 460
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
            fun gen_copyText_fn(text: String, successTitle: String, emptyTitle: String) {
                if (text == "" || text == "-") {
                    uni_showToast(ShowToastOptions(title = emptyTitle, icon = "none"))
                    return
                }
                uni_setClipboardData(SetClipboardDataOptions(data = text, success = fun(_){
                    uni_showToast(ShowToastOptions(title = successTitle, icon = "success"))
                }
                ))
            }
            val copyText = ::gen_copyText_fn
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
            fun gen_getChildren_fn(item: UTSJSONObject): UTSArray<UTSJSONObject> {
                val children = parseObjectArray(item["children"])
                if (children.length > 0) {
                    return children
                }
                val childList = parseObjectArray(item["child_list"])
                if (childList.length > 0) {
                    return childList
                }
                return _uA<UTSJSONObject>()
            }
            val getChildren = ::gen_getChildren_fn
            fun gen_normalizeOptionNode_fn(item: UTSJSONObject): UTSJSONObject {
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
                val children = getChildren(item)
                val normalizedChildren: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < children.length){
                        normalizedChildren.push(gen_normalizeOptionNode_fn(children[index]))
                        index += 1
                    }
                }
                return _uO("value" to value, "text" to text, "children" to normalizedChildren, "has_children" to (normalizedChildren.length > 0))
            }
            val normalizeOptionNode = ::gen_normalizeOptionNode_fn
            fun gen_buildBottomSelectResponse_fn(raw: Any?, params: UTSJSONObject): UTSJSONObject {
                val rows = extractRows(raw)
                val normalized: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < rows.length){
                        normalized.push(normalizeOptionNode(rows[index]))
                        index += 1
                    }
                }
                return _uO("data" to normalized, "results" to normalized, "total" to normalized.length, "total_count" to normalized.length)
            }
            val buildBottomSelectResponse = ::gen_buildBottomSelectResponse_fn
            fun gen_buildOptionQuery_fn(params: UTSJSONObject): UTSJSONObject {
                val pageValue = intValue(params["page"])
                val pageSizeValue = intValue(params["pageSize"])
                val query: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("query", "pages/inventory-management/index.uvue", 431, 8), "page" to if (pageValue <= 0) {
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
            fun gen_fetchSupplierFilterOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        val raw = await(request("/api/procurement/suppliers/options/", "GET", buildOptionQuery(params), true))
                        return@w1 buildBottomSelectResponse(raw, _uO("keyword" to ""))
                })
            }
            val fetchSupplierFilterOptions = ::gen_fetchSupplierFilterOptions_fn
            fun gen_fetchCategoryFilterOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        val raw = await(request("/api/categories/categories/options/", "GET", buildOptionQuery(params), true))
                        return@w1 buildBottomSelectResponse(raw, params)
                })
            }
            val fetchCategoryFilterOptions = ::gen_fetchCategoryFilterOptions_fn
            fun gen_fetchLocationFilterOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        val raw = await(request("/api/inventory/locations/", "GET", buildOptionQuery(params), true))
                        return@w1 buildBottomSelectResponse(raw, params)
                })
            }
            val fetchLocationFilterOptions = ::gen_fetchLocationFilterOptions_fn
            fun gen_alertStatusText_fn(value: String): String {
                if (value == "NORMAL" || value == "") {
                    return "正常"
                }
                if (value == "LOW_STOCK") {
                    return "低库存"
                }
                if (value == "OUT_OF_STOCK") {
                    return "售罄"
                }
                if (value == "NO_MOVEMENT") {
                    return "久未变动"
                }
                if (value == "LOW") {
                    return "低库存"
                }
                if (value == "OUT") {
                    return "售罄"
                }
                return value
            }
            val alertStatusText = ::gen_alertStatusText_fn
            fun gen_listedText_fn(value: String): String {
                if (value == "false") {
                    return "下架"
                }
                return "上架"
            }
            val listedText = ::gen_listedText_fn
            fun gen_numberText_fn(value: Any?): String {
                val text = stringValue(value)
                if (text == "") {
                    return "0"
                }
                return text
            }
            val numberText = ::gen_numberText_fn
            fun gen_moneyText_fn(value: Any?): String {
                val text = stringValue(value)
                if (text == "") {
                    return "¥ 0.00"
                }
                return "¥ " + text
            }
            val moneyText = ::gen_moneyText_fn
            fun gen_imageListFromStock_fn(stock: UTSJSONObject): UTSArray<String> {
                val result: UTSArray<String> = _uA()
                val directImage = stringValue(stock["product_image"])
                if (directImage != "") {
                    result.push(directImage)
                }
                val mediaFiles = parseObjectArray(stock["product_media_files"])
                run {
                    var index: Number = 0
                    while(index < mediaFiles.length){
                        val image = firstStringField(mediaFiles[index], _uA(
                            "signed_thumbnail_url",
                            "thumbnail_url",
                            "signed_url",
                            "signed_download_url",
                            "file_url",
                            "url",
                            "image"
                        ))
                        if (image != "" && result.indexOf(image) < 0) {
                            result.push(image)
                        }
                        index += 1
                    }
                }
                return result
            }
            val imageListFromStock = ::gen_imageListFromStock_fn
            fun gen_previewImageListFromStock_fn(stock: UTSJSONObject): UTSArray<String> {
                val result: UTSArray<String> = _uA()
                val directImage = stringValue(stock["product_image"])
                val mediaFiles = parseObjectArray(stock["product_media_files"])
                run {
                    var index: Number = 0
                    while(index < mediaFiles.length){
                        val image = firstStringField(mediaFiles[index], _uA(
                            "signed_url",
                            "signed_download_url",
                            "file_url",
                            "url",
                            "image",
                            "signed_thumbnail_url",
                            "thumbnail_url"
                        ))
                        if (image != "" && result.indexOf(image) < 0) {
                            result.push(image)
                        }
                        index += 1
                    }
                }
                if (result.length == 0 && directImage != "") {
                    result.push(directImage)
                }
                return result
            }
            val previewImageListFromStock = ::gen_previewImageListFromStock_fn
            fun gen_mediaIdListFromStock_fn(stock: UTSJSONObject): UTSArray<String> {
                val result: UTSArray<String> = _uA()
                val mediaFiles = parseObjectArray(stock["product_media_files"])
                run {
                    var index: Number = 0
                    while(index < mediaFiles.length){
                        val mediaId = firstStringField(mediaFiles[index], _uA(
                            "id",
                            "pk"
                        ))
                        if (mediaId != "" && result.indexOf(mediaId) < 0) {
                            result.push(mediaId)
                        }
                        index += 1
                    }
                }
                return result
            }
            val mediaIdListFromStock = ::gen_mediaIdListFromStock_fn
            fun gen_stockToListItem_fn(item: UTSJSONObject): UTSJSONObject {
                val id = stringValue(item["id"])
                val sku = stringValue(item["product_sku"])
                val barcode = stringValue(item["product_barcode"])
                val supplierName = stringValue(item["product_supplier_name"], "未设置供应商")
                val categoryName = stringValue(item["product_category_name"], "未分类")
                val alertStatus = stringValue(item["alert_status"])
                val listedValue = if (stringValue(item["is_listed"]) == "false") {
                    "false"
                } else {
                    "true"
                }
                val tags = _uA<String>(alertStatusText(alertStatus), listedText(listedValue))
                if (supplierName != "未设置供应商") {
                    tags.push(supplierName)
                }
                val images = imageListFromStock(item)
                val previewImages = previewImageListFromStock(item)
                return _uO("id" to id, "rawId" to id, "title" to stringValue(item["product_name"], "未命名商品"), "subtitle" to ("条码：" + (if (barcode == "") {
                    "-"
                } else {
                    barcode
                }
                )), "locationText" to stringValue(item["location_name"], "未设置库存位置"), "image" to if (images.length > 0) {
                    images[0]
                } else {
                    ""
                }
                , "images" to images, "previewCover" to if (previewImages.length > 0) {
                    previewImages[0]
                } else {
                    ""
                }
                , "previewImages" to previewImages, "mediaIds" to mediaIdListFromStock(item), "skuText" to if (sku == "") {
                    "-"
                } else {
                    sku
                }
                , "barcodeText" to if (barcode == "") {
                    "-"
                } else {
                    barcode
                }
                , "quantityText" to numberText(item["quantity"]), "availableText" to numberText(item["available_quantity"]), "reservedText" to numberText(item["reserved_quantity"]), "costText" to moneyText(item["average_cost"]), "categoryText" to categoryName, "supplierText" to supplierName, "alertText" to alertStatusText(alertStatus), "tags" to tags)
            }
            val stockToListItem = ::gen_stockToListItem_fn
            fun gen_applyResponse_fn(response: InventoryListResponse) {
                stocks.value = response.results
                currentPage.value = response.current_page
                totalPages.value = response.total_pages
                totalCount.value = response.total_count
                pageSize.value = response.page_size
                var quantityTotal: Number = 0
                var alertTotal: Number = 0
                run {
                    var index: Number = 0
                    while(index < response.results.length){
                        val row = response.results[index]
                        if (productFilterName.value == "") {
                            productFilterName.value = stringValue(row["product_name"])
                        }
                        quantityTotal = quantityTotal + intValue(row["quantity"])
                        val status = stringValue(row["alert_status"])
                        if (status != "" && status != "NORMAL") {
                            alertTotal = alertTotal + 1
                        }
                        index += 1
                    }
                }
                totalQuantityText.value = quantityTotal.toString(10)
                alertCountText.value = alertTotal.toString(10)
            }
            val applyResponse = ::gen_applyResponse_fn
            fun gen_buildQuery_fn(): InventoryListQuery {
                return InventoryListQuery(search = if (keyword.value == "") {
                    null
                } else {
                    keyword.value
                }
                , page = currentPage.value, page_size = pageSize.value, status = null, alert_status = if (alertStatusFilter.value == "") {
                    null
                } else {
                    alertStatusFilter.value
                }
                , supplier = if (supplierFilterValue.value == "") {
                    null
                } else {
                    supplierFilterValue.value
                }
                , category = if (categoryFilterValues.value.length == 0) {
                    null
                } else {
                    categoryFilterValues.value.join(",")
                }
                , is_listed = if (listedStatusFilter.value == "") {
                    null
                } else {
                    listedStatusFilter.value
                }
                , location = if (locationFilterValue.value == "") {
                    null
                } else {
                    locationFilterValue.value
                }
                , product = if (productFilterId.value == "") {
                    null
                } else {
                    productFilterId.value
                }
                , transaction_type = null, location_type = null, is_active = null)
            }
            val buildQuery = ::gen_buildQuery_fn
            fun gen_loadStocks_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (isLoading.value) {
                            return@w1
                        }
                        isLoading.value = true
                        errorMessage.value = ""
                        try {
                            applyResponse(await(getInventoryStocks(buildQuery())))
                        }
                         catch (error: Throwable) {
                            stocks.value = _uA<UTSJSONObject>()
                            currentPage.value = 1
                            totalPages.value = 1
                            totalCount.value = 0
                            totalQuantityText.value = "0"
                            alertCountText.value = "0"
                            errorMessage.value = parseErrorMessage(error, "库存加载失败")
                        }
                         finally {
                            isLoading.value = false
                        }
                })
            }
            val loadStocks = ::gen_loadStocks_fn
            fun gen_searchByScannedBarcode_fn(barcode: String) {
                keyword.value = barcode
                currentPage.value = 1
                closeFilterDrawer()
                loadStocks()
            }
            val searchByScannedBarcode = ::gen_searchByScannedBarcode_fn
            fun gen_handleSearchInput_fn(value: String) {
                keyword.value = value
            }
            val handleSearchInput = ::gen_handleSearchInput_fn
            fun gen_handleSearchConfirm_fn(value: String) {
                keyword.value = value
                currentPage.value = 1
                loadStocks()
            }
            val handleSearchConfirm = ::gen_handleSearchConfirm_fn
            fun gen_handleSearchClear_fn() {
                keyword.value = ""
                currentPage.value = 1
                loadStocks()
            }
            val handleSearchClear = ::gen_handleSearchClear_fn
            fun gen_handleFilterVisibleChange_fn(value: Boolean) {
                filterVisible.value = value
            }
            val handleFilterVisibleChange = ::gen_handleFilterVisibleChange_fn
            fun gen_handleFilterOpen_fn() {
                updateFilterPanelLayout()
                draftSupplierValue.value = supplierFilterValue.value
                draftSupplierText.value = supplierFilterText.value
                draftCategoryValues.value = categoryFilterValues.value.slice()
                draftLocationValue.value = locationFilterValue.value
                draftLocationText.value = locationFilterText.value
                draftAlertStatus.value = alertStatusFilter.value
                draftListedStatus.value = listedStatusFilter.value
            }
            val handleFilterOpen = ::gen_handleFilterOpen_fn
            fun gen_handleSupplierFilterChange_fn(payload: UTSJSONObject) {
                draftSupplierValue.value = stringValue(payload["value"])
                draftSupplierText.value = stringValue(payload["text"])
            }
            val handleSupplierFilterChange = ::gen_handleSupplierFilterChange_fn
            fun gen_handleCategoryFilterChange_fn(payload: UTSJSONObject) {
                draftCategoryValues.value = parseStringArray(payload["values"])
            }
            val handleCategoryFilterChange = ::gen_handleCategoryFilterChange_fn
            fun gen_handleLocationFilterChange_fn(payload: UTSJSONObject) {
                draftLocationValue.value = stringValue(payload["value"])
                draftLocationText.value = stringValue(payload["text"])
            }
            val handleLocationFilterChange = ::gen_handleLocationFilterChange_fn
            fun gen_selectAlertStatus_fn(value: String) {
                draftAlertStatus.value = value
            }
            val selectAlertStatus = ::gen_selectAlertStatus_fn
            fun gen_selectListedStatus_fn(value: String) {
                draftListedStatus.value = value
            }
            val selectListedStatus = ::gen_selectListedStatus_fn
            fun gen_handleFilterReset_fn() {
                supplierFilterValue.value = ""
                supplierFilterText.value = ""
                categoryFilterValues.value = _uA<String>()
                locationFilterValue.value = ""
                locationFilterText.value = ""
                alertStatusFilter.value = ""
                listedStatusFilter.value = ""
                draftSupplierValue.value = ""
                draftSupplierText.value = ""
                draftCategoryValues.value = _uA<String>()
                draftLocationValue.value = ""
                draftLocationText.value = ""
                draftAlertStatus.value = ""
                draftListedStatus.value = ""
                keyword.value = ""
                currentPage.value = 1
                closeFilterDrawer()
                loadStocks()
            }
            val handleFilterReset = ::gen_handleFilterReset_fn
            fun gen_applySelectedFilters_fn() {
                supplierFilterValue.value = draftSupplierValue.value
                supplierFilterText.value = draftSupplierText.value
                categoryFilterValues.value = draftCategoryValues.value.slice()
                locationFilterValue.value = draftLocationValue.value
                locationFilterText.value = draftLocationText.value
                alertStatusFilter.value = draftAlertStatus.value
                listedStatusFilter.value = draftListedStatus.value
                currentPage.value = 1
                closeFilterDrawer()
                loadStocks()
            }
            val applySelectedFilters = ::gen_applySelectedFilters_fn
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
                loadStocks()
            }
            val handlePageChange = ::gen_handlePageChange_fn
            fun gen_buildInventoryDetailUrl_fn(stockId: String, mode: String, productName: String): String {
                var url = "/pages/inventory-management/from"
                var separator = "?"
                if (productFilterId.value != "") {
                    url = url + separator + "product=" + productFilterId.value
                    separator = "&"
                }
                if (stockId != "") {
                    url = url + separator + "stock=" + stockId
                    separator = "&"
                }
                if (mode != "") {
                    url = url + separator + "mode=" + mode
                    separator = "&"
                }
                val titleText = if (productName == "") {
                    productFilterName.value
                } else {
                    productName
                }
                if (titleText != "") {
                    url = url + separator + "productName=" + UTSAndroid.consoleDebugError(encodeURIComponent(titleText), " at pages/inventory-management/index.uvue:719")
                }
                return url
            }
            val buildInventoryDetailUrl = ::gen_buildInventoryDetailUrl_fn
            fun gen_navigateToInventoryDetail_fn(stockId: String, mode: String, productName: String) {
                if (stockId == "" && productFilterId.value == "") {
                    uni_showToast(ShowToastOptions(title = "缺少商品或库存记录", icon = "none"))
                    return
                }
                uni_navigateTo(NavigateToOptions(url = buildInventoryDetailUrl(stockId, mode, productName)))
            }
            val navigateToInventoryDetail = ::gen_navigateToInventoryDetail_fn
            fun gen_navigateToCreateStock_fn() {
                if (productFilterId.value == "") {
                    uni_showToast(ShowToastOptions(title = "请先从商品页进入库存", icon = "none"))
                    return
                }
                navigateToInventoryDetail("", "create", productFilterName.value)
            }
            val navigateToCreateStock = ::gen_navigateToCreateStock_fn
            fun gen_navigateToAdjust_fn(id: String, productName: String) {
                if (id == "") {
                    return
                }
                navigateToInventoryDetail(id, "adjust", productName)
            }
            val navigateToAdjust = ::gen_navigateToAdjust_fn
            fun gen_handleItemClick_fn(payload: UTSJSONObject) {
                var productName = stringValue(payload["title"])
                val itemValue = payload["item"]
                if (productName == "" && itemValue != null) {
                    productName = stringValue((itemValue as UTSJSONObject)["title"])
                }
                navigateToAdjust(stringValue(payload["rawId"], stringValue(payload["id"])), productName)
            }
            val handleItemClick = ::gen_handleItemClick_fn
            fun gen_handleMenu_fn(payload: UTSJSONObject) {
                val action = payload["action"]
                val item = payload["item"]
                if (action == null) {
                    return
                }
                val actionKey = stringValue((action as UTSJSONObject)["key"])
                if (actionKey == "reload") {
                    loadStocks()
                    return
                }
                if (actionKey == "adjust" && item != null) {
                    val row = item as UTSJSONObject
                    navigateToAdjust(stringValue(row["rawId"]), stringValue(row["title"]))
                }
            }
            val handleMenu = ::gen_handleMenu_fn
            fun gen_handleSubtitleClick_fn(payload: UTSJSONObject) {
                val item = payload["item"]
                if (item == null) {
                    return
                }
                copyText(stringValue((item as UTSJSONObject)["barcodeText"]), "条码已复制", "暂无条码")
            }
            val handleSubtitleClick = ::gen_handleSubtitleClick_fn
            fun gen_handleMetaClick_fn(payload: UTSJSONObject) {
                copyText(stringValue(payload["value"]), "库存位置已复制", "暂无库存位置")
            }
            val handleMetaClick = ::gen_handleMetaClick_fn
            fun gen_handleFieldClick_fn(payload: UTSJSONObject) {
                val value = stringValue(payload["value"])
                val label = stringValue(payload["label"], "内容")
                copyText(value, label.replace(":", "") + "已复制", "暂无内容")
            }
            val handleFieldClick = ::gen_handleFieldClick_fn
            fun gen_handleScannedBarcode_fn(scanResult: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        val barcode = scanResult.trim()
                        if (barcode == "" || scanLookupRunning.value) {
                            return@w1
                        }
                        scanLookupRunning.value = true
                        try {
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
                    uni_showToast(ShowToastOptions(title = message, icon = "none"))
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
            fun gen_startInventoryVolumeKeyListener_fn() {
                startVolumeKeyListener(fun(event: VolumeKeyEvent){
                    handleVolumeKeyEvent(event)
                }
                )
            }
            val startInventoryVolumeKeyListener = ::gen_startInventoryVolumeKeyListener_fn
            fun gen_scheduleInventoryVolumeKeyListener_fn() {
                if (volumeKeyStartTimer != 0) {
                    clearTimeout(volumeKeyStartTimer)
                }
                volumeKeyStartTimer = setTimeout(fun(){
                    volumeKeyStartTimer = 0
                    startInventoryVolumeKeyListener()
                }
                , 260)
            }
            val scheduleInventoryVolumeKeyListener = ::gen_scheduleInventoryVolumeKeyListener_fn
            fun gen_stopInventoryVolumeKeyListener_fn() {
                if (volumeKeyStartTimer != 0) {
                    clearTimeout(volumeKeyStartTimer)
                    volumeKeyStartTimer = 0
                }
                stopVolumeKeyListener()
                volumeScanLocked.value = false
            }
            val stopInventoryVolumeKeyListener = ::gen_stopInventoryVolumeKeyListener_fn
            fun gen_consumeRefresh_fn(): Boolean {
                val flag = uni_getStorageSync(refreshStorageKey)
                if (flag == null || ("" + flag) == "") {
                    return false
                }
                uni_removeStorageSync(refreshStorageKey)
                return true
            }
            val consumeRefresh = ::gen_consumeRefresh_fn
            fun gen_applyRouteQuery_fn(query: OnLoadOptions) {
                val productValue = query["product"]
                productFilterId.value = if (productValue == null) {
                    ""
                } else {
                    ("" + productValue)
                }
                val productNameValue = query["productName"]
                productFilterName.value = if (productNameValue == null) {
                    ""
                } else {
                    ("" + productNameValue)
                }
                if (productFilterId.value != "") {
                    currentPage.value = 1
                }
            }
            val applyRouteQuery = ::gen_applyRouteQuery_fn
            val productMode = computed(fun(): Boolean {
                return productFilterId.value != ""
            }
            )
            val pageTitle = computed(fun(): String {
                if (productMode.value) {
                    return "商品库存"
                }
                return "库存管理"
            }
            )
            val homePath = computed(fun(): String {
                if (productMode.value) {
                    return "/pages/tabbar/products"
                }
                return "/pages/tabbar/settings"
            }
            )
            val productStockTitle = computed(fun(): String {
                if (productFilterName.value != "") {
                    return productFilterName.value
                }
                if (productFilterId.value != "") {
                    return "商品 #" + productFilterId.value
                }
                return "商品库存"
            }
            )
            val listItems = computed(fun(): UTSArray<UTSJSONObject> {
                val result: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < stocks.value.length){
                        result.push(stockToListItem(stocks.value[index]))
                        index += 1
                    }
                }
                return result
            }
            )
            val hasActiveFilter = computed(fun(): Boolean {
                return keyword.value != "" || supplierFilterValue.value != "" || categoryFilterValues.value.length > 0 || locationFilterValue.value != "" || alertStatusFilter.value != "" || listedStatusFilter.value != ""
            }
            )
            val emptyText = computed(fun(): String {
                if (isLoading.value) {
                    return "正在加载"
                }
                if (hasActiveFilter.value) {
                    return "没有匹配的库存记录"
                }
                if (productMode.value) {
                    return "该商品暂无库存记录"
                }
                return "暂无库存记录"
            }
            )
            val summaryItems = computed(fun(): UTSArray<UTSJSONObject> {
                return _uA(
                    _uO("key" to "total", "label" to "库存记录", "value" to totalCount.value.toString(10)),
                    _uO("key" to "quantity", "label" to "本页库存", "value" to totalQuantityText.value),
                    _uO("key" to "alerts", "label" to "本页预警", "value" to alertCountText.value),
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
            onLoad(fun(query: OnLoadOptions){
                applyRouteQuery(query)
                updateFilterPanelLayout()
                loadStocks()
            }
            )
            onShow(fun(){
                scheduleInventoryVolumeKeyListener()
                updateFilterPanelLayout()
                if (consumeRefresh()) {
                    loadStocks()
                }
            }
            )
            onHide(fun(){
                stopInventoryVolumeKeyListener()
            }
            )
            onUnload(fun(){
                stopInventoryVolumeKeyListener()
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList", GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalListClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to pageTitle.value, "searchPlaceholder" to "商品名、SKU、条码", "searchValue" to unref(keyword), "filterVisible" to unref(filterVisible), "showBack" to true, "showSearch" to true, "showFilter" to true, "showScan" to true, "showHome" to true, "filterActive" to hasActiveFilter.value, "filterText" to "重置", "homePath" to homePath.value, "onSearchInput" to handleSearchInput, "onSearchConfirm" to handleSearchConfirm, "onSearchClear" to handleSearchClear, "onScan" to handleScanSearch, "onUpdate:filterVisible" to handleFilterVisibleChange, "onFilterOpen" to handleFilterOpen), _uM("filter-panel" to withSlotCtx(fun(): UTSArray<Any> {
                        return _uA(
                            _cE("view", _uM("class" to "inventory-filter-panel", "style" to _nS(filterPanelStyle.value)), _uA(
                                _cE("scroll-view", _uM("scroll-y" to "true", "class" to "inventory-filter-content-scroll", "style" to _nS(filterContentScrollStyle.value)), _uA(
                                    _cE("view", _uM("class" to "inventory-filter-scroll-inner"), _uA(
                                        _cE("view", _uM("class" to "inventory-filter-select-group"), _uA(
                                            _cE("text", _uM("class" to "inventory-filter-select-title"), "供应商"),
                                            _cE("view", _uM("class" to "inventory-filter-select-wrap"), _uA(
                                                _cV(unref(GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelectClass), _uM("value" to unref(draftSupplierValue), "valueText" to unref(draftSupplierText), "title" to "选择供应商", "placeholder" to "全部供应商", "searchPlaceholder" to "搜索供应商", "emptyText" to "暂无供应商", "fetchData" to fetchSupplierFilterOptions, "showAddAction" to false, "showEditAction" to false, "onChange" to handleSupplierFilterChange), null, 8, _uA(
                                                    "value",
                                                    "valueText"
                                                ))
                                            ))
                                        )),
                                        _cE("view", _uM("class" to "inventory-filter-select-group"), _uA(
                                            _cE("text", _uM("class" to "inventory-filter-select-title"), "分类"),
                                            _cE("view", _uM("class" to "inventory-filter-select-wrap"), _uA(
                                                _cV(unref(GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelectClass), _uM("values" to unref(draftCategoryValues), "title" to "选择分类", "placeholder" to "全部分类", "searchPlaceholder" to "搜索分类", "emptyText" to "暂无分类", "fetchData" to fetchCategoryFilterOptions, "showEditAction" to false, "showAddAction" to false, "tree" to true, "multiple" to true, "checkStrictly" to false, "defaultExpandAll" to true, "childrenKey" to "children", "onMultiChange" to handleCategoryFilterChange), null, 8, _uA(
                                                    "values"
                                                ))
                                            ))
                                        )),
                                        _cE("view", _uM("class" to "inventory-filter-select-group"), _uA(
                                            _cE("text", _uM("class" to "inventory-filter-select-title"), "库存位置"),
                                            _cE("view", _uM("class" to "inventory-filter-select-wrap"), _uA(
                                                _cV(unref(GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelectClass), _uM("value" to unref(draftLocationValue), "valueText" to unref(draftLocationText), "title" to "选择库存位置", "placeholder" to "全部库存位置", "searchPlaceholder" to "搜索库存位置", "emptyText" to "暂无库存位置", "fetchData" to fetchLocationFilterOptions, "showAddAction" to false, "showEditAction" to false, "onChange" to handleLocationFilterChange), null, 8, _uA(
                                                    "value",
                                                    "valueText"
                                                ))
                                            ))
                                        )),
                                        _cE("view", _uM("class" to "inventory-filter-group"), _uA(
                                            _cE("text", _uM("class" to "inventory-filter-group-title"), "预警状态"),
                                            _cE("view", _uM("class" to "inventory-filter-options"), _uA(
                                                _cE(Fragment, null, RenderHelpers.renderList(alertStatusOptions, fun(option, __key, __index, _cached): Any {
                                                    return _cE("view", _uM("key" to ("alert-" + option.value), "class" to _nC(if (unref(draftAlertStatus) == option.value) {
                                                        "inventory-filter-option inventory-filter-option-active"
                                                    } else {
                                                        "inventory-filter-option"
                                                    }
                                                    ), "onClick" to fun(){
                                                        selectAlertStatus(option.value)
                                                    }
                                                    ), _uA(
                                                        _cE("text", _uM("class" to _nC(if (unref(draftAlertStatus) == option.value) {
                                                            "inventory-filter-option-text inventory-filter-option-text-active"
                                                        } else {
                                                            "inventory-filter-option-text"
                                                        }
                                                        )), _tD(option.label), 3)
                                                    ), 10, _uA(
                                                        "onClick"
                                                    ))
                                                }
                                                ), 64)
                                            ))
                                        )),
                                        _cE("view", _uM("class" to "inventory-filter-group"), _uA(
                                            _cE("text", _uM("class" to "inventory-filter-group-title"), "上架状态"),
                                            _cE("view", _uM("class" to "inventory-filter-options"), _uA(
                                                _cE(Fragment, null, RenderHelpers.renderList(listedStatusOptions, fun(option, __key, __index, _cached): Any {
                                                    return _cE("view", _uM("key" to ("listed-" + option.value), "class" to _nC(if (unref(draftListedStatus) == option.value) {
                                                        "inventory-filter-option inventory-filter-option-active"
                                                    } else {
                                                        "inventory-filter-option"
                                                    }
                                                    ), "onClick" to fun(){
                                                        selectListedStatus(option.value)
                                                    }
                                                    ), _uA(
                                                        _cE("text", _uM("class" to _nC(if (unref(draftListedStatus) == option.value) {
                                                            "inventory-filter-option-text inventory-filter-option-text-active"
                                                        } else {
                                                            "inventory-filter-option-text"
                                                        }
                                                        )), _tD(option.label), 3)
                                                    ), 10, _uA(
                                                        "onClick"
                                                    ))
                                                }
                                                ), 64)
                                            ))
                                        ))
                                    ))
                                ), 4),
                                _cE("view", _uM("class" to "inventory-filter-actions"), _uA(
                                    _cE("view", _uM("class" to "inventory-filter-btn inventory-filter-btn-light", "onClick" to handleFilterReset), _uA(
                                        _cE("text", _uM("class" to "inventory-filter-btn-light-text"), "重置")
                                    )),
                                    _cE("view", _uM("class" to "inventory-filter-btn inventory-filter-btn-primary", "onClick" to applySelectedFilters), _uA(
                                        _cE("text", _uM("class" to "inventory-filter-btn-primary-text"), "应用")
                                    ))
                                ))
                            ), 4)
                        )
                    }
                    ), "_" to 1), 8, _uA(
                        "title",
                        "searchValue",
                        "filterVisible",
                        "filterActive",
                        "homePath"
                    )),
                    _cE("scroll-view", _uM("style" to _nS(_uM("flex" to "1")), "class" to "page-scroll"), _uA(
                        _cE("view", _uM("class" to "page-content"), _uA(
                            if (isTrue(productMode.value)) {
                                _cE("view", _uM("key" to 0, "class" to "product-stock-header"), _uA(
                                    _cE("view", _uM("class" to "product-stock-title-wrap"), _uA(
                                        _cE("text", _uM("class" to "product-stock-title"), _tD(productStockTitle.value), 1),
                                        _cE("text", _uM("class" to "product-stock-subtitle"), "统一管理该商品在所有库存位置的数量")
                                    )),
                                    _cE("view", _uM("class" to "product-stock-action", "onClick" to navigateToCreateStock), _uA(
                                        _cE("text", _uM("class" to "product-stock-action-text"), "新增库存")
                                    ))
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            if (isTrue(unref(errorMessage) != "" && !unref(isLoading))) {
                                _cE("view", _uM("key" to 1, "class" to "error-card"), _uA(
                                    _cE("text", _uM("class" to "error-title"), "加载失败"),
                                    _cE("text", _uM("class" to "error-desc"), _tD(unref(errorMessage)), 1),
                                    _cE("view", _uM("class" to "retry-btn", "onClick" to loadStocks), _uA(
                                        _cE("text", _uM("class" to "retry-btn-text"), "重新加载")
                                    ))
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            _cV(_component_lili_UniversalList, _uM("items" to listItems.value, "keyField" to "id", "titleField" to "title", "subtitleField" to "subtitle", "metaField" to "locationText", "tagField" to "tags", "tagColorMap" to unref(tagColorMap), "fields" to unref(fieldConfig), "loading" to unref(isLoading), "loadingText" to "正在加载库存", "keepContentOnLoading" to true, "inlineLoadingText" to "库存刷新中...", "emptyText" to emptyText.value, "emptyIcon" to "◎", "showMenu" to true, "menuActions" to unref(menuActions), "showChevron" to false, "showPagination" to true, "currentPage" to unref(currentPage), "totalPages" to unref(totalPages), "totalCount" to unref(totalCount), "summaryTitle" to "库存概览", "summaryItems" to summaryItems.value, "showFloatingAdd" to productMode.value, "floatingAddText" to "新增库存", "onItemClick" to handleItemClick, "onMenu" to handleMenu, "onPageChange" to handlePageChange, "onSubtitleClick" to handleSubtitleClick, "onFieldClick" to handleFieldClick, "onMetaClick" to handleMetaClick, "onFloatingAdd" to navigateToCreateStock), null, 8, _uA(
                                "items",
                                "tagColorMap",
                                "fields",
                                "loading",
                                "emptyText",
                                "menuActions",
                                "currentPage",
                                "totalPages",
                                "totalCount",
                                "summaryItems",
                                "showFloatingAdd"
                            ))
                        ))
                    ), 4)
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-scroll" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-content" to _pS(_uM("paddingTop" to 6, "paddingRight" to 6, "paddingBottom" to 96, "paddingLeft" to 6)), "error-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 18, "paddingRight" to 18, "paddingBottom" to 18, "paddingLeft" to 18, "marginBottom" to 10, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FECACA", "borderRightColor" to "#FECACA", "borderBottomColor" to "#FECACA", "borderLeftColor" to "#FECACA", "alignItems" to "center")), "error-title" to _pS(_uM("fontSize" to 18, "lineHeight" to "24px", "color" to "#B42318", "fontWeight" to "bold")), "error-desc" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#7F1D1D", "marginTop" to 8, "textAlign" to "center")), "retry-btn" to _pS(_uM("marginTop" to 14, "height" to 40, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#0F172A", "paddingLeft" to 18, "paddingRight" to 18, "alignItems" to "center", "justifyContent" to "center")), "retry-btn-text" to _pS(_uM("fontSize" to 14, "color" to "#FFFFFF")), "inventory-filter-panel" to _pS(_uM("position" to "relative", "paddingTop" to 2)), "inventory-filter-content-scroll" to _pS(_uM("paddingRight" to 2)), "inventory-filter-scroll-inner" to _pS(_uM("paddingBottom" to 58)), "inventory-filter-select-group" to _pS(_uM("paddingLeft" to 10, "paddingRight" to 10, "paddingTop" to 10, "paddingBottom" to 10, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#FFFFFF", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E5EAF1", "borderRightColor" to "#E5EAF1", "borderBottomColor" to "#E5EAF1", "borderLeftColor" to "#E5EAF1", "marginBottom" to 6)), "inventory-filter-group" to _pS(_uM("paddingLeft" to 10, "paddingRight" to 10, "paddingTop" to 10, "paddingBottom" to 10, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#FFFFFF", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E5EAF1", "borderRightColor" to "#E5EAF1", "borderBottomColor" to "#E5EAF1", "borderLeftColor" to "#E5EAF1", "marginBottom" to 6)), "inventory-filter-select-title" to _pS(_uM("fontSize" to 13, "lineHeight" to "17px", "color" to "#0F172A", "fontWeight" to "bold")), "inventory-filter-group-title" to _pS(_uM("fontSize" to 13, "lineHeight" to "17px", "color" to "#0F172A", "fontWeight" to "bold")), "inventory-filter-select-wrap" to _pS(_uM("marginTop" to 8)), "inventory-filter-options" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 8)), "inventory-filter-option" to _pS(_uM("minWidth" to 48, "height" to 30, "paddingLeft" to 10, "paddingRight" to 10, "borderTopLeftRadius" to 15, "borderTopRightRadius" to 15, "borderBottomRightRadius" to 15, "borderBottomLeftRadius" to 15, "backgroundColor" to "#F8FAFC", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "alignItems" to "center", "justifyContent" to "center", "marginRight" to 6, "marginBottom" to 6)), "inventory-filter-option-active" to _pS(_uM("backgroundColor" to "#0F172A", "borderTopColor" to "#0F172A", "borderRightColor" to "#0F172A", "borderBottomColor" to "#0F172A", "borderLeftColor" to "#0F172A")), "inventory-filter-option-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "17px", "color" to "#334155")), "inventory-filter-option-text-active" to _pS(_uM("color" to "#FFFFFF")), "inventory-filter-actions" to _pS(_uM("position" to "absolute", "left" to 0, "right" to 0, "bottom" to 0, "flexDirection" to "row", "paddingTop" to 6, "paddingLeft" to 2, "paddingRight" to 2, "paddingBottom" to 4, "borderTopWidth" to 1, "borderTopStyle" to "solid", "borderTopColor" to "rgba(226,232,240,0.78)", "backgroundColor" to "#FFFFFF")), "inventory-filter-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 38, "borderTopLeftRadius" to 11, "borderTopRightRadius" to 11, "borderBottomRightRadius" to 11, "borderBottomLeftRadius" to 11, "alignItems" to "center", "justifyContent" to "center")), "inventory-filter-btn-light" to _pS(_uM("backgroundColor" to "#F3F6FA", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "marginRight" to 8)), "inventory-filter-btn-primary" to _pS(_uM("backgroundColor" to "#0F172A")), "inventory-filter-btn-light-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#475569")), "inventory-filter-btn-primary-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#FFFFFF")), "product-stock-header" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "backgroundColor" to "#FFFFFF", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "marginBottom" to 8)), "product-stock-title-wrap" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingRight" to 10)), "product-stock-title" to _pS(_uM("fontSize" to 17, "lineHeight" to "23px", "color" to "#0F172A", "fontWeight" to "bold")), "product-stock-subtitle" to _pS(_uM("marginTop" to 3, "fontSize" to 12, "lineHeight" to "17px", "color" to "#64748B")), "product-stock-action" to _pS(_uM("height" to 38, "paddingLeft" to 14, "paddingRight" to 14, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#0F172A", "alignItems" to "center", "justifyContent" to "center")), "product-stock-action-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#FFFFFF", "fontWeight" to "bold")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
