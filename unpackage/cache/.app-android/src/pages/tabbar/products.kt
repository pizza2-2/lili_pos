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
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
import uts.sdk.modules.liliKey.startVolumeKeyListener
import uts.sdk.modules.liliKey.stopVolumeKeyListener
import uts.sdk.modules.liliKey.VolumeKeyEvent
open class GenPagesTabbarProducts : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesTabbarProducts) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesTabbarProducts
            val _cache = __ins.renderCache
            val keyword = ref("")
            val filterVisible = ref(false)
            val isLoading = ref(false)
            val errorMessage = ref("")
            val products = ref(_uA<ProductItem>())
            val currentPage = ref(1)
            val totalPages = ref(1)
            val totalCount = ref(0)
            val pageSize = ref(20)
            val filterOptionsLoading = ref(false)
            val filterOptionsError = ref("")
            val filterOptions = ref<ProductFilterOptionsResponse?>(null)
            val selectedFilters = ref(_uA<ProductSelectedFilter>())
            val supplierFilterValue = ref("")
            val supplierFilterText = ref("")
            val categoryFilterValues = ref(_uA<String>())
            val sortOrdering = ref("-updated_at")
            val filterPanelHeight = ref(456)
            val filterContentHeight = ref(392)
            val volumeScanLocked = ref(false)
            val selectionMode = ref(false)
            val selectedProductIds = ref(_uA<String>())
            val batchSubmitting = ref(false)
            val batchEditorVisible = ref(false)
            val batchEditorType = ref("")
            val batchCategoryValue = ref("")
            val batchCategoryText = ref("")
            val batchSupplierValue = ref("")
            val batchSupplierText = ref("")
            val batchStatusValue = ref("")
            val batchStatusText = ref("")
            val fieldConfig = ref(_uA<UTSJSONObject>(_uO("key" to "supplierText", "label" to "供应商：", "hideWhenEmpty" to true), _uO("key" to "stockQuantityText", "label" to "库存数量："), _uO("key" to "purchasePriceText", "label" to "含税进价：", "hideWhenEmpty" to true), _uO("key" to "salesPriceText", "label" to "售价："), _uO("key" to "salesCountText", "label" to "销量：")))
            val menuActions = ref(_uA<UTSJSONObject>(_uO("key" to "detail", "text" to "详情"), _uO("key" to "copy-product", "text" to "复制商品"), _uO("key" to "inventory", "text" to "查看库存"), _uO("key" to "reload", "text" to "刷新")))
            val batchToolbarActions = ref(_uA<UTSJSONObject>(_uO("key" to "delete", "text" to "删除"), _uO("key" to "update-category", "text" to "改分类"), _uO("key" to "update-supplier", "text" to "改供应商"), _uO("key" to "update-status", "text" to "改状态")))
            val productStatusOptions = ref(_uA<ProductStatusOption>(ProductStatusOption(value = "DRAFT", text = "草稿"), ProductStatusOption(value = "ACTIVE", text = "启用"), ProductStatusOption(value = "INACTIVE", text = "停用"), ProductStatusOption(value = "DISCONTINUED", text = "停产")))
            val productSortOptions = ref(_uA<ProductSortOption>(ProductSortOption(value = "-updated_at", text = "最近更新"), ProductSortOption(value = "-created_at", text = "最新创建"), ProductSortOption(value = "sort_order", text = "手动排序"), ProductSortOption(value = "name_cn", text = "名称正序"), ProductSortOption(value = "-total_stock_quantity", text = "库存最多"), ProductSortOption(value = "-total_sales_quantity", text = "销量最高"), ProductSortOption(value = "base_sales_price", text = "售价最低"), ProductSortOption(value = "-base_sales_price", text = "售价最高")))
            val tagColorMap = ref(_uO("新品" to "violet", "精选" to "info", "热销" to "warning"))
            val productListRefreshStorageKey = "refresh:pages:products:index"
            fun gen_applyProductResponse_fn(response: ProductListResponse) {
                products.value = response.results
                currentPage.value = response.current_page
                totalPages.value = response.total_pages
                totalCount.value = response.total_count
                pageSize.value = response.page_size
            }
            val applyProductResponse = ::gen_applyProductResponse_fn
            fun gen_closeFilterDrawer_fn() {
                filterVisible.value = false
            }
            val closeFilterDrawer = ::gen_closeFilterDrawer_fn
            fun gen_handleFilterVisibleChange_fn(value: Boolean) {
                filterVisible.value = value
            }
            val handleFilterVisibleChange = ::gen_handleFilterVisibleChange_fn
            fun gen_parseErrorMessage_fn(error: Any): String {
                var message = "商品列表加载失败"
                if (error != null) {
                    val errorText = JSON.stringify(error)
                    if (errorText != null && errorText != "") {
                        val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/tabbar/products.uvue:390")
                        if (parsedError != null) {
                            val rawMessage = parsedError["message"]
                            if (rawMessage != null) {
                                val parsedMessage = rawMessage as String
                                if (parsedMessage != "") {
                                    message = parsedMessage
                                }
                            }
                        }
                        if (message == "商品列表加载失败") {
                            message = errorText
                        }
                    }
                }
                return message
            }
            val parseErrorMessage = ::gen_parseErrorMessage_fn
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
            fun gen_selectedCountText_fn(): String {
                return selectedProductIds.value.length.toString(10)
            }
            val selectedCountText = ::gen_selectedCountText_fn
            fun gen_clearSelectionState_fn() {
                selectionMode.value = false
                selectedProductIds.value = _uA<String>()
            }
            val clearSelectionState = ::gen_clearSelectionState_fn
            fun gen_handleSelectionModeChange_fn(value: Boolean) {
                selectionMode.value = value
                if (!value) {
                    selectedProductIds.value = _uA<String>()
                }
            }
            val handleSelectionModeChange = ::gen_handleSelectionModeChange_fn
            fun gen_handleSelectedProductIdsChange_fn(value: UTSArray<String>) {
                val nextIds: UTSArray<String> = _uA()
                run {
                    var index: Number = 0
                    while(index < value.length){
                        nextIds.push(value[index])
                        index += 1
                    }
                }
                selectedProductIds.value = nextIds
            }
            val handleSelectedProductIdsChange = ::gen_handleSelectedProductIdsChange_fn
            fun gen_handleSelectionExit_fn(payload: UTSJSONObject) {
                clearSelectionState()
            }
            val handleSelectionExit = ::gen_handleSelectionExit_fn
            fun gen_selectedProductIdsSnapshot_fn(): UTSArray<String> {
                val result: UTSArray<String> = _uA()
                run {
                    var index: Number = 0
                    while(index < selectedProductIds.value.length){
                        result.push(selectedProductIds.value[index])
                        index += 1
                    }
                }
                return result
            }
            val selectedProductIdsSnapshot = ::gen_selectedProductIdsSnapshot_fn
            fun gen_ensureBatchSelection_fn(): Boolean {
                if (selectedProductIds.value.length > 0) {
                    return true
                }
                uni_showToast(ShowToastOptions(title = "请先选择商品", icon = "none", duration = 3500))
                return false
            }
            val ensureBatchSelection = ::gen_ensureBatchSelection_fn
            fun gen_resetBatchDraft_fn() {
                batchCategoryValue.value = ""
                batchCategoryText.value = ""
                batchSupplierValue.value = ""
                batchSupplierText.value = ""
                batchStatusValue.value = ""
                batchStatusText.value = ""
            }
            val resetBatchDraft = ::gen_resetBatchDraft_fn
            fun gen_updateFilterPanelLayout_fn() {
                val info = uni_getWindowInfo()
                var nextPanelHeight = info.windowHeight - 168
                if (nextPanelHeight > 456) {
                    nextPanelHeight = 456
                }
                if (nextPanelHeight < 336) {
                    nextPanelHeight = 336
                }
                var nextContentHeight = nextPanelHeight - 64
                if (nextContentHeight < 250) {
                    nextContentHeight = 250
                }
                filterPanelHeight.value = nextPanelHeight
                filterContentHeight.value = nextContentHeight
            }
            val updateFilterPanelLayout = ::gen_updateFilterPanelLayout_fn
            fun gen_loadProducts_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (isLoading.value) {
                            return@w1
                        }
                        isLoading.value = true
                        errorMessage.value = ""
                        try {
                            val response = await(getProductList(ProductListQuery(search = if (keyword.value == "") {
                                null
                            } else {
                                keyword.value
                            }
                            , page = currentPage.value, page_size = pageSize.value, ordering = sortOrdering.value, filters = selectedFilters.value)))
                            applyProductResponse(response)
                        }
                         catch (error: Throwable) {
                            products.value = _uA()
                            currentPage.value = 1
                            totalPages.value = 1
                            totalCount.value = 0
                            errorMessage.value = parseErrorMessage(error)
                        }
                         finally {
                            isLoading.value = false
                        }
                })
            }
            val loadProducts = ::gen_loadProducts_fn
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
            fun gen_parseObject_fn(value: Any?): UTSJSONObject? {
                if (value == null) {
                    return null
                }
                val text = JSON.stringify(value)
                if (text == null || text == "") {
                    return null
                }
                return UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(text), " at pages/tabbar/products.uvue:551")
            }
            val parseObject = ::gen_parseObject_fn
            fun gen_parseObjectArray_fn(value: Any?): UTSArray<UTSJSONObject> {
                if (value == null) {
                    return _uA()
                }
                val text = JSON.stringify(value)
                if (text == null || text == "") {
                    return _uA()
                }
                val parsed = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(text), " at pages/tabbar/products.uvue:564")
                if (parsed == null) {
                    return _uA()
                }
                return parsed!!
            }
            val parseObjectArray = ::gen_parseObjectArray_fn
            fun getDisplayText(value: String?, fallback: String = "-"): String {
                if (value == null || value == "") {
                    return fallback
                }
                return value
            }
            fun gen_productThumbnailUrl_fn(mediaFile: ProductMediaFile): String {
                if (mediaFile.signed_thumbnail_url != "") {
                    return mediaFile.signed_thumbnail_url
                }
                if (mediaFile.thumbnail_url != "") {
                    return mediaFile.thumbnail_url
                }
                if (mediaFile.signed_url != "") {
                    return mediaFile.signed_url
                }
                return mediaFile.file_url
            }
            val productThumbnailUrl = ::gen_productThumbnailUrl_fn
            fun gen_productFullUrl_fn(mediaFile: ProductMediaFile): String {
                if (mediaFile.signed_url != "") {
                    return mediaFile.signed_url
                }
                if (mediaFile.file_url != "") {
                    return mediaFile.file_url
                }
                return productThumbnailUrl(mediaFile)
            }
            val productFullUrl = ::gen_productFullUrl_fn
            fun gen_buildImages_fn(item: ProductItem): UTSArray<String> {
                val result: UTSArray<String> = _uA()
                run {
                    var index: Number = 0
                    while(index < item.media_files.length){
                        val mediaFile = item.media_files[index]
                        val imageUrl = productThumbnailUrl(mediaFile)
                        if (imageUrl != "") {
                            result.push(imageUrl)
                        }
                        index += 1
                    }
                }
                return result
            }
            val buildImages = ::gen_buildImages_fn
            fun gen_buildPreviewImages_fn(item: ProductItem): UTSArray<String> {
                val result: UTSArray<String> = _uA()
                run {
                    var index: Number = 0
                    while(index < item.media_files.length){
                        val imageUrl = productFullUrl(item.media_files[index])
                        if (imageUrl != "") {
                            result.push(imageUrl)
                        }
                        index += 1
                    }
                }
                return result
            }
            val buildPreviewImages = ::gen_buildPreviewImages_fn
            fun gen_buildMediaIds_fn(item: ProductItem): UTSArray<String> {
                val result: UTSArray<String> = _uA()
                run {
                    var index: Number = 0
                    while(index < item.media_files.length){
                        val mediaId = item.media_files[index].id
                        if (mediaId != "") {
                            result.push(mediaId)
                        }
                        index += 1
                    }
                }
                return result
            }
            val buildMediaIds = ::gen_buildMediaIds_fn
            fun gen_setSelectedFilterValue_fn(param: String, value: String) {
                val nextFilters: UTSArray<ProductSelectedFilter> = _uA()
                var updated = false
                run {
                    var index: Number = 0
                    while(index < selectedFilters.value.length){
                        val filter = selectedFilters.value[index]
                        if (filter.param == param) {
                            if (value != "") {
                                nextFilters.push(ProductSelectedFilter(param = param, value = value))
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
                    nextFilters.push(ProductSelectedFilter(param = param, value = value))
                }
                selectedFilters.value = nextFilters
            }
            val setSelectedFilterValue = ::gen_setSelectedFilterValue_fn
            fun gen_splitSelectedValues_fn(value: String): UTSArray<String> {
                if (value == "") {
                    return _uA()
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
                    val optionsObject = parseObject(rawObject["options"])
                    if (optionsObject != null) {
                        for(key in resolveUTSKeyIterator(optionsObject)){
                            val optionItems = parseObjectArray(optionsObject[key])
                            if (optionItems.length > 0) {
                                return optionItems
                            }
                        }
                    }
                }
                return parseObjectArray(value)
            }
            val extractOptionObjects = ::gen_extractOptionObjects_fn
            fun gen_extractCategoryTreeSource_fn(value: Any?): UTSArray<UTSJSONObject> {
                val rawObject = parseObject(value)
                if (rawObject == null) {
                    return _uA()
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
                val titleValue = stringValue(item["title"])
                if (titleValue != "") {
                    return titleValue
                }
                return buildOptionValue(item)
            }
            val buildOptionText = ::gen_buildOptionText_fn
            fun gen_buildSelectOptions_fn(value: Any?): UTSArray<ProductSelectOption> {
                val source = extractOptionObjects(value)
                val result: UTSArray<ProductSelectOption> = _uA()
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
                        result.push(ProductSelectOption(value = optionValue, text = if (optionText == "") {
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
            fun gen_buildBottomSelectResponse_fn(source: UTSArray<ProductSelectOption>, params: UTSJSONObject): UTSJSONObject {
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
            fun gen_fetchSupplierFilterOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        val keywordValue = stringValue(params["keyword"])
                        val query: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("query", "pages/tabbar/products.uvue", 894, 8), "key" to "supplier", "limit" to 50)
                        if (keywordValue != "") {
                            query["search"] = keywordValue
                            query["keyword"] = keywordValue
                        }
                        val raw = await(request("/api/procurement/suppliers/options/", "GET", query, true))
                        return@w1 buildBottomSelectResponse(buildSelectOptions(raw), _uO("keyword" to "", "id" to stringValue(params["id"])))
                })
            }
            val fetchSupplierFilterOptions = ::gen_fetchSupplierFilterOptions_fn
            fun gen_fetchCategoryFilterOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        val keywordValue = stringValue(params["keyword"])
                        val pageValue = stringValue(params["page"], "1")
                        val parentValue = stringValue(params["parent"])
                        val pageSizeValue = stringValue(params["pageSize"], "20")
                        val queryParams: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("queryParams", "pages/tabbar/products.uvue", 917, 8), "key" to "parent", "page" to parseInt(if (pageValue == "") {
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
            val fetchCategoryFilterOptions = ::gen_fetchCategoryFilterOptions_fn
            fun gen_batchActionTitle_fn(actionKey: String): String {
                if (actionKey == "delete") {
                    return "批量删除"
                }
                if (actionKey == "update-category") {
                    return "批量修改分类"
                }
                if (actionKey == "update-supplier") {
                    return "批量修改供应商"
                }
                if (actionKey == "update-status") {
                    return "批量修改状态"
                }
                return "批量操作"
            }
            val batchActionTitle = ::gen_batchActionTitle_fn
            fun gen_batchActionConfirmText_fn(actionKey: String, count: Number): String {
                if (actionKey == "delete") {
                    return "确定删除选中的 " + count.toString(10) + " 个商品吗？"
                }
                if (actionKey == "update-category") {
                    return "确定将选中的 " + count.toString(10) + " 个商品修改到该分类吗？"
                }
                if (actionKey == "update-supplier") {
                    return "确定将选中的 " + count.toString(10) + " 个商品修改到该供应商吗？"
                }
                if (actionKey == "update-status") {
                    return "确定将选中的 " + count.toString(10) + " 个商品修改为该状态吗？"
                }
                return "确定执行批量操作吗？"
            }
            val batchActionConfirmText = ::gen_batchActionConfirmText_fn
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
            fun gen_executeProductBatchAction_fn(actionKey: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (batchSubmitting.value || !ensureBatchSelection()) {
                            return@w1
                        }
                        val ids = selectedProductIdsSnapshot()
                        batchSubmitting.value = true
                        try {
                            var response: ProductBatchActionResponse? = null
                            if (actionKey == "delete") {
                                response = await(batchDeleteProducts(ids))
                            } else if (actionKey == "update-category") {
                                response = await(batchUpdateProductCategory(ids, batchCategoryValue.value))
                            } else if (actionKey == "update-supplier") {
                                response = await(batchUpdateProductSupplier(ids, batchSupplierValue.value))
                            } else if (actionKey == "update-status") {
                                response = await(batchUpdateProductStatus(ids, batchStatusValue.value))
                            } else {
                                uni_showToast(ShowToastOptions(title = "暂不支持该操作", icon = "none", duration = 3500))
                                return@w1
                            }
                            val fallbackMessage = batchActionTitle(actionKey) + "成功"
                            var resultMessage = fallbackMessage
                            var resultIcon = "success"
                            if (response != null) {
                                val batchResponse = response as ProductBatchActionResponse
                                resultMessage = batchResultMessage(batchResponse, fallbackMessage)
                                if (batchResultHasIssue(batchResponse)) {
                                    resultIcon = "none"
                                }
                            }
                            uni_showToast(ShowToastOptions(title = resultMessage, icon = resultIcon))
                            batchEditorVisible.value = false
                            batchEditorType.value = ""
                            resetBatchDraft()
                            clearSelectionState()
                            loadProducts()
                        }
                         catch (error: Throwable) {
                            showErrorToast(parseErrorMessage(error))
                        }
                         finally {
                            batchSubmitting.value = false
                        }
                })
            }
            val executeProductBatchAction = ::gen_executeProductBatchAction_fn
            fun gen_confirmProductBatchAction_fn(actionKey: String) {
                if (!ensureBatchSelection()) {
                    return
                }
                val count = selectedProductIds.value.length
                uni_showModal(ShowModalOptions(title = batchActionTitle(actionKey), content = batchActionConfirmText(actionKey, count), success = fun(res){
                    if (!res.confirm) {
                        return
                    }
                    executeProductBatchAction(actionKey)
                }
                ))
            }
            val confirmProductBatchAction = ::gen_confirmProductBatchAction_fn
            fun gen_openBatchEditor_fn(actionKey: String) {
                if (!ensureBatchSelection()) {
                    return
                }
                resetBatchDraft()
                batchEditorType.value = actionKey
                batchEditorVisible.value = true
            }
            val openBatchEditor = ::gen_openBatchEditor_fn
            fun gen_closeBatchEditor_fn() {
                if (batchSubmitting.value) {
                    return
                }
                batchEditorVisible.value = false
                batchEditorType.value = ""
                resetBatchDraft()
            }
            val closeBatchEditor = ::gen_closeBatchEditor_fn
            fun gen_handleBatchCategoryChange_fn(payload: UTSJSONObject) {
                batchCategoryValue.value = stringValue(payload["value"])
                batchCategoryText.value = stringValue(payload["text"])
            }
            val handleBatchCategoryChange = ::gen_handleBatchCategoryChange_fn
            fun gen_handleBatchSupplierChange_fn(payload: UTSJSONObject) {
                batchSupplierValue.value = stringValue(payload["value"])
                batchSupplierText.value = stringValue(payload["text"])
            }
            val handleBatchSupplierChange = ::gen_handleBatchSupplierChange_fn
            fun gen_selectBatchStatus_fn(option: ProductStatusOption) {
                batchStatusValue.value = option.value
                batchStatusText.value = option.text
            }
            val selectBatchStatus = ::gen_selectBatchStatus_fn
            fun gen_isBatchStatusSelected_fn(option: ProductStatusOption): Boolean {
                return batchStatusValue.value == option.value
            }
            val isBatchStatusSelected = ::gen_isBatchStatusSelected_fn
            fun gen_validateBatchEditor_fn(): Boolean {
                if (batchEditorType.value == "update-category" && batchCategoryValue.value == "") {
                    uni_showToast(ShowToastOptions(title = "请选择分类", icon = "none", duration = 3500))
                    return false
                }
                if (batchEditorType.value == "update-supplier" && batchSupplierValue.value == "") {
                    uni_showToast(ShowToastOptions(title = "请选择供应商", icon = "none", duration = 3500))
                    return false
                }
                if (batchEditorType.value == "update-status" && batchStatusValue.value == "") {
                    uni_showToast(ShowToastOptions(title = "请选择状态", icon = "none", duration = 3500))
                    return false
                }
                return true
            }
            val validateBatchEditor = ::gen_validateBatchEditor_fn
            fun gen_confirmBatchEditor_fn() {
                if (batchEditorType.value == "" || !validateBatchEditor()) {
                    return
                }
                confirmProductBatchAction(batchEditorType.value)
            }
            val confirmBatchEditor = ::gen_confirmBatchEditor_fn
            fun gen_handleBatchToolbarAction_fn(payload: UTSJSONObject) {
                val actionValue = payload["action"]
                if (actionValue == null) {
                    return
                }
                val action = actionValue as UTSJSONObject
                val actionKey = stringValue(action["key"])
                if (actionKey == "delete") {
                    confirmProductBatchAction(actionKey)
                    return
                }
                if (actionKey == "update-category" || actionKey == "update-supplier" || actionKey == "update-status") {
                    openBatchEditor(actionKey)
                }
            }
            val handleBatchToolbarAction = ::gen_handleBatchToolbarAction_fn
            fun gen_buildProductTags_fn(item: ProductItem): UTSArray<String> {
                val result: UTSArray<String> = _uA()
                if (item.is_new) {
                    result.push("新品")
                }
                if (item.is_featured) {
                    result.push("精选")
                }
                if (item.is_bestseller) {
                    result.push("热销")
                }
                return result
            }
            val buildProductTags = ::gen_buildProductTags_fn
            fun gen_productDisplayName_fn(item: ProductItem): String {
                val chineseName = getDisplayText(item.name_cn, "")
                if (chineseName != "") {
                    return chineseName
                }
                val englishName = getDisplayText(item.name_en, "")
                if (englishName != "") {
                    return englishName
                }
                val otherName = getDisplayText(item.name_other, "")
                if (otherName != "") {
                    return otherName
                }
                return if (item.sku == "") {
                    ("商品 #" + item.id.toString(10))
                } else {
                    item.sku
                }
            }
            val productDisplayName = ::gen_productDisplayName_fn
            fun gen_productToListItem_fn(item: ProductItem): UTSJSONObject {
                val images = buildImages(item)
                val previewImages = buildPreviewImages(item)
                return _uO("id" to item.id.toString(10), "name" to productDisplayName(item), "name_en" to getDisplayText(item.name_en), "name_other" to getDisplayText(item.name_other), "sku" to getDisplayText(item.sku), "skuText" to ("SKU：" + getDisplayText(item.sku)), "barcode" to getDisplayText(item.barcode), "barcodeText" to ("条码：" + getDisplayText(item.barcode)), "foreignNameText" to getDisplayText(item.name_en), "otherNameText" to getDisplayText(item.name_other), "supplierText" to item.supplier_name, "stockQuantityText" to item.total_stock_quantity.toString(10), "purchasePriceText" to item.purchase_price, "netPurchasePriceText" to item.net_purchase_price, "costPriceText" to item.cost_price, "salesPriceText" to getDisplayText(item.base_sales_price), "salesCountText" to item.total_sales_quantity.toString(10), "variantCountText" to item.variant_count.toString(10), "updatedText" to getDisplayText(item.updated_at), "cover" to if (images.length > 0) {
                    images[0]
                } else {
                    ""
                }
                , "images" to images, "previewCover" to if (previewImages.length > 0) {
                    previewImages[0]
                } else {
                    ""
                }
                , "previewImages" to previewImages, "mediaIds" to buildMediaIds(item), "tags" to buildProductTags(item), "rawId" to item.id.toString(10))
            }
            val productToListItem = ::gen_productToListItem_fn
            fun gen_copyText_fn(text: String, successTitle: String, emptyTitle: String) {
                if (text == "" || text == "-") {
                    uni_showToast(ShowToastOptions(title = emptyTitle, icon = "none", duration = 3500))
                    return
                }
                uni_setClipboardData(SetClipboardDataOptions(data = text, success = fun(_){
                    uni_showToast(ShowToastOptions(title = successTitle, icon = "success"))
                }
                ))
            }
            val copyText = ::gen_copyText_fn
            fun gen_handleSearchInput_fn(value: String) {
                keyword.value = value
            }
            val handleSearchInput = ::gen_handleSearchInput_fn
            fun gen_handleSearchConfirm_fn(value: String) {
                keyword.value = value
                currentPage.value = 1
                loadProducts()
            }
            val handleSearchConfirm = ::gen_handleSearchConfirm_fn
            fun gen_handleSearchClear_fn() {
                keyword.value = ""
                currentPage.value = 1
                loadProducts()
            }
            val handleSearchClear = ::gen_handleSearchClear_fn
            fun gen_handleScanSearch_fn() {
                scanCode(ScanCodeOption(onlyFromCamera = true, success = fun(res: ScanCodeSuccessCallbackResult){
                    val scanResult = res.result
                    if (scanResult == "") {
                        return
                    }
                    keyword.value = scanResult
                    currentPage.value = 1
                    closeFilterDrawer()
                    loadProducts()
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
            fun gen_startProductVolumeKeyListener_fn() {
                startVolumeKeyListener(fun(event: VolumeKeyEvent){
                    handleVolumeKeyEvent(event)
                }
                )
            }
            val startProductVolumeKeyListener = ::gen_startProductVolumeKeyListener_fn
            fun gen_stopProductVolumeKeyListener_fn() {
                stopVolumeKeyListener()
                volumeScanLocked.value = false
            }
            val stopProductVolumeKeyListener = ::gen_stopProductVolumeKeyListener_fn
            fun gen_handleFilterOpen_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (filterOptions.value != null || filterOptionsLoading.value) {
                            return@w1
                        }
                        filterOptionsLoading.value = true
                        filterOptionsError.value = ""
                        try {
                            filterOptions.value = await(getProductFilterOptions())
                        }
                         catch (error: Throwable) {
                            filterOptionsError.value = parseErrorMessage(error)
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
                setSelectedFilterValue("supplier", supplierFilterValue.value)
            }
            val handleSupplierFilterChange = ::gen_handleSupplierFilterChange_fn
            fun gen_handleCategoryMultiChange_fn(payload: UTSJSONObject) {
                val values = payload["values"] as UTSArray<String>?
                val texts = payload["texts"] as UTSArray<String>?
                if (values != null && texts != null) {
                    categoryFilterValues.value = values
                    setSelectedFilterValue("category", values.join(","))
                }
            }
            val handleCategoryMultiChange = ::gen_handleCategoryMultiChange_fn
            fun gen_isFilterOptionSelected_fn(param: String, value: String): Boolean {
                run {
                    var index: Number = 0
                    while(index < selectedFilters.value.length){
                        val filter = selectedFilters.value[index]
                        if (filter.param != param) {
                            index += 1
                            continue
                        }
                        return splitSelectedValues(filter.value).includes(value)
                        index += 1
                    }
                }
                return false
            }
            val isFilterOptionSelected = ::gen_isFilterOptionSelected_fn
            fun gen_toggleFilterOption_fn(param: String, value: String, multiple: Boolean) {
                var currentValue = ""
                run {
                    var index: Number = 0
                    while(index < selectedFilters.value.length){
                        val filter = selectedFilters.value[index]
                        if (filter.param == param) {
                            currentValue = filter.value
                            break
                        }
                        index += 1
                    }
                }
                val currentValues = splitSelectedValues(currentValue)
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
                        val currentItem = currentValues[index]
                        if (currentItem == value) {
                            alreadySelected = true
                            index += 1
                            continue
                        }
                        nextValues.push(currentItem)
                        index += 1
                    }
                }
                if (!alreadySelected) {
                    nextValues.push(value)
                }
                setSelectedFilterValue(param, nextValues.join(","))
            }
            val toggleFilterOption = ::gen_toggleFilterOption_fn
            fun gen_selectSortOption_fn(option: ProductSortOption) {
                sortOrdering.value = option.value
            }
            val selectSortOption = ::gen_selectSortOption_fn
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
                loadProducts()
            }
            val handlePageChange = ::gen_handlePageChange_fn
            fun gen_handleFilterReset_fn() {
                selectedFilters.value = _uA<ProductSelectedFilter>()
                supplierFilterValue.value = ""
                supplierFilterText.value = ""
                categoryFilterValues.value = _uA()
                sortOrdering.value = "-updated_at"
                keyword.value = ""
                currentPage.value = 1
                closeFilterDrawer()
                loadProducts()
            }
            val handleFilterReset = ::gen_handleFilterReset_fn
            fun gen_applySelectedFilters_fn() {
                currentPage.value = 1
                closeFilterDrawer()
                loadProducts()
            }
            val applySelectedFilters = ::gen_applySelectedFilters_fn
            fun gen_handleItemClick_fn(payload: UTSJSONObject) {
                val itemName = stringValue(payload["name"], "商品")
                uni_showToast(ShowToastOptions(title = itemName, icon = "none", duration = 3500))
            }
            val handleItemClick = ::gen_handleItemClick_fn
            fun gen_handleSubtitleClick_fn(payload: UTSJSONObject) {
                val itemValue = payload["item"]
                if (itemValue == null) {
                    return
                }
                val item = itemValue as UTSJSONObject
                copyText(stringValue(item["sku"]), "SKU已复制", "暂无SKU")
            }
            val handleSubtitleClick = ::gen_handleSubtitleClick_fn
            fun gen_handleMetaClick_fn(payload: UTSJSONObject) {
                val itemValue = payload["item"]
                if (itemValue == null) {
                    return
                }
                val item = itemValue as UTSJSONObject
                copyText(stringValue(item["barcode"]), "条码已复制", "暂无条码")
            }
            val handleMetaClick = ::gen_handleMetaClick_fn
            fun gen_handleFieldClick_fn(payload: UTSJSONObject) {
                val keyValue = payload["key"]
                val itemValue = payload["item"]
                if (keyValue == null || itemValue == null) {
                    return
                }
                val key = keyValue as String
                val item = itemValue as UTSJSONObject
                if (key == "supplierText") {
                    copyText(stringValue(item["supplierText"]), "供应商已复制", "暂无供应商")
                    return
                }
                if (key == "foreignNameText") {
                    copyText(stringValue(item["foreignNameText"]), "波兰名已复制", "暂无波兰名")
                    return
                }
                if (key == "otherNameText") {
                    copyText(stringValue(item["otherNameText"]), "其他名已复制", "暂无其他名")
                    return
                }
                if (key == "purchasePriceText") {
                    copyText(stringValue(item["purchasePriceText"]), "含税进价已复制", "暂无含税进价")
                    return
                }
                if (key == "netPurchasePriceText") {
                    copyText(stringValue(item["netPurchasePriceText"]), "不含税进价已复制", "暂无不含税进价")
                    return
                }
                if (key == "costPriceText") {
                    copyText(stringValue(item["costPriceText"]), "成本已复制", "暂无成本")
                    return
                }
                if (key == "salesPriceText") {
                    copyText(stringValue(item["salesPriceText"]), "售价已复制", "暂无售价")
                    return
                }
                if (key == "salesCountText") {
                    copyText(stringValue(item["salesCountText"]), "销量已复制", "暂无销量")
                }
            }
            val handleFieldClick = ::gen_handleFieldClick_fn
            fun gen_navigateToProductInventory_fn(item: UTSJSONObject) {
                val rawId = stringValue(item["rawId"], stringValue(item["id"]))
                if (rawId == "") {
                    uni_showToast(ShowToastOptions(title = "缺少商品ID", icon = "none", duration = 3500))
                    return
                }
                val productName = stringValue(item["name"])
                var url = "/pages/inventory-management/from?product=" + rawId
                if (productName != "") {
                    url = url + "&productName=" + UTSAndroid.consoleDebugError(encodeURIComponent(productName), " at pages/tabbar/products.uvue:1461")
                }
                uni_navigateTo(NavigateToOptions(url = url))
            }
            val navigateToProductInventory = ::gen_navigateToProductInventory_fn
            fun gen_handleMenu_fn(payload: UTSJSONObject) {
                val actionValue = payload["action"]
                val itemValue = payload["item"]
                if (actionValue == null || itemValue == null) {
                    return
                }
                val action = actionValue as UTSJSONObject
                val item = itemValue as UTSJSONObject
                val key = stringValue(action["key"])
                if (key == "copy-sku") {
                    copyText(stringValue(item["sku"]), "SKU已复制", "暂无SKU")
                    return
                }
                if (key == "copy-barcode") {
                    copyText(stringValue(item["barcode"]), "条码已复制", "暂无条码")
                    return
                }
                if (key == "detail") {
                    val rawId = stringValue(item["rawId"])
                    if (rawId == "") {
                        return
                    }
                    uni_navigateTo(NavigateToOptions(url = "/pages/products/from?id=" + rawId))
                    return
                }
                if (key == "copy-product") {
                    val rawId = stringValue(item["rawId"])
                    if (rawId == "") {
                        uni_showToast(ShowToastOptions(title = "缺少商品ID", icon = "none", duration = 3500))
                        return
                    }
                    uni_navigateTo(NavigateToOptions(url = "/pages/products/from?copy_id=" + rawId))
                    return
                }
                if (key == "inventory") {
                    navigateToProductInventory(item)
                    return
                }
                if (key == "reload") {
                    loadProducts()
                }
            }
            val handleMenu = ::gen_handleMenu_fn
            fun gen_handleFloatingAdd_fn() {
                uni_navigateTo(NavigateToOptions(url = "/pages/products/from"))
            }
            val handleFloatingAdd = ::gen_handleFloatingAdd_fn
            fun gen_consumeProductListRefreshFlag_fn(): Boolean {
                val storedValue = uni_getStorageSync(productListRefreshStorageKey)
                val shouldRefresh = storedValue != null && stringValue(storedValue) == "1"
                if (shouldRefresh) {
                    uni_removeStorageSync(productListRefreshStorageKey)
                }
                return shouldRefresh
            }
            val consumeProductListRefreshFlag = ::gen_consumeProductListRefreshFlag_fn
            val listItems = computed(fun(): UTSArray<UTSJSONObject> {
                val result: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < products.value.length){
                        result.push(productToListItem(products.value[index]))
                        index += 1
                    }
                }
                return result
            }
            )
            val batchInfoText = computed(fun(): String {
                return "已选 " + selectedCountText() + " 个商品"
            }
            )
            val batchEditorTitle = computed(fun(): String {
                return batchActionTitle(batchEditorType.value)
            }
            )
            val batchEditorSubtitle = computed(fun(): String {
                return "当前已选 " + selectedCountText() + " 个商品"
            }
            )
            val summaryItems = computed(fun(): UTSArray<UTSJSONObject> {
                return _uA(
                    _uO("key" to "total", "label" to "商品总数", "value" to totalCount.value.toString(10)),
                    _uO("key" to "page", "label" to "当前页", "value" to (currentPage.value.toString(10) + "/" + totalPages.value.toString(10))),
                    _uO("key" to "loaded", "label" to "当前页条数", "value" to products.value.length.toString(10))
                )
            }
            )
            val hasActiveFilter = computed(fun(): Boolean {
                return keyword.value != "" || selectedFilters.value.length > 0 || sortOrdering.value != "-updated_at"
            }
            )
            val emptyText = computed(fun(): String {
                if (isLoading.value) {
                    return "正在加载"
                }
                if (hasActiveFilter.value) {
                    return "没有匹配的商品"
                }
                return "暂无商品"
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
            fun gen_isReservedFilterParam_fn(filter: ProductFilterDefinition): Boolean {
                if (filter.param == "supplier" || filter.param == "category") {
                    return true
                }
                if (filter.key == "supplier" || filter.key == "category") {
                    return true
                }
                run {
                    var index: Number = 0
                    while(index < filter.aliases.length){
                        val alias = filter.aliases[index]
                        if (alias == "supplier" || alias == "category") {
                            return true
                        }
                        index += 1
                    }
                }
                return false
            }
            val isReservedFilterParam = ::gen_isReservedFilterParam_fn
            val filterDefinitions = computed(fun(): UTSArray<ProductFilterDefinition> {
                if (filterOptions.value == null) {
                    return _uA()
                }
                val result: UTSArray<ProductFilterDefinition> = _uA()
                run {
                    var index: Number = 0
                    while(index < filterOptions.value!!.filters.length){
                        val filter = filterOptions.value!!.filters[index]
                        if (isReservedFilterParam(filter)) {
                            index += 1
                            continue
                        }
                        result.push(filter)
                        index += 1
                    }
                }
                return result
            }
            )
            onLoad(fun(_options){
                updateFilterPanelLayout()
                loadProducts()
            }
            )
            onShow(fun(){
                startProductVolumeKeyListener()
                updateFilterPanelLayout()
                if (consumeProductListRefreshFlag()) {
                    loadProducts()
                    return
                }
                if (products.value.length == 0 && !isLoading.value) {
                    loadProducts()
                }
            }
            )
            onHide(fun(){
                stopProductVolumeKeyListener()
            }
            )
            onUnload(fun(){
                stopProductVolumeKeyListener()
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList", GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalListClass)
                val _component_page_container = resolveComponent("page-container")
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to "商品", "searchPlaceholder" to "输入商品名称、SKU、条码", "searchValue" to unref(keyword), "filterVisible" to unref(filterVisible), "showBack" to false, "showSearch" to true, "showFilter" to true, "showScan" to true, "showHome" to false, "filterActive" to hasActiveFilter.value, "filterText" to "重置", "onSearchInput" to handleSearchInput, "onSearchConfirm" to handleSearchConfirm, "onSearchClear" to handleSearchClear, "onScan" to handleScanSearch, "onUpdate:filterVisible" to handleFilterVisibleChange, "onFilterOpen" to handleFilterOpen), _uM("filter-panel" to withSlotCtx(fun(): UTSArray<Any> {
                        return _uA(
                            _cE("view", _uM("class" to "product-filter-panel", "style" to _nS(filterPanelStyle.value)), _uA(
                                _cE("scroll-view", _uM("scroll-y" to "true", "class" to "product-filter-content-scroll", "style" to _nS(filterContentScrollStyle.value)), _uA(
                                    _cE("view", _uM("class" to "product-filter-scroll-inner"), _uA(
                                        _cE("view", _uM("class" to "product-filter-select-group"), _uA(
                                            _cE("text", _uM("class" to "product-filter-select-title"), "供应商"),
                                            _cE("view", _uM("class" to "product-filter-select-wrap"), _uA(
                                                _cV(unref(GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelectClass), _uM("value" to unref(supplierFilterValue), "valueText" to unref(supplierFilterText), "title" to "选择供应商", "placeholder" to "请选择供应商", "searchPlaceholder" to "请输入供应商名称", "emptyText" to "暂无供应商", "showAddAction" to false, "showEditAction" to false, "fetchData" to fetchSupplierFilterOptions, "onChange" to handleSupplierFilterChange), null, 8, _uA(
                                                    "value",
                                                    "valueText"
                                                ))
                                            ))
                                        )),
                                        _cE("view", _uM("class" to "product-filter-select-group"), _uA(
                                            _cE("text", _uM("class" to "product-filter-select-title"), "分类"),
                                            _cE("view", _uM("class" to "product-filter-select-wrap"), _uA(
                                                _cV(unref(GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelectClass), _uM("values" to unref(categoryFilterValues), "title" to "选择分类", "placeholder" to "请选择分类", "searchPlaceholder" to "请输入分类名称", "emptyText" to "暂无分类", "tree" to true, "multiple" to true, "checkStrictly" to false, "showAddAction" to false, "showEditAction" to false, "fetchData" to fetchCategoryFilterOptions, "onMultiChange" to handleCategoryMultiChange), null, 8, _uA(
                                                    "values"
                                                ))
                                            ))
                                        )),
                                        _cE("view", _uM("class" to "product-filter-select-group"), _uA(
                                            _cE("text", _uM("class" to "product-filter-select-title"), "排序"),
                                            _cE("view", _uM("class" to "product-filter-options"), _uA(
                                                _cE(Fragment, null, RenderHelpers.renderList(unref(productSortOptions), fun(option, __key, __index, _cached): Any {
                                                    return _cE("view", _uM("key" to option.value, "class" to _nC(if (unref(sortOrdering) == option.value) {
                                                        "product-filter-option product-filter-option-active"
                                                    } else {
                                                        "product-filter-option"
                                                    }
                                                    ), "onClick" to fun(){
                                                        selectSortOption(option)
                                                    }
                                                    ), _uA(
                                                        _cE("text", _uM("class" to _nC(if (unref(sortOrdering) == option.value) {
                                                            "product-filter-option-text product-filter-option-text-active"
                                                        } else {
                                                            "product-filter-option-text"
                                                        }
                                                        )), _tD(option.text), 3)
                                                    ), 10, _uA(
                                                        "onClick"
                                                    ))
                                                }
                                                ), 128)
                                            ))
                                        )),
                                        if (isTrue(unref(filterOptionsLoading))) {
                                            _cE("view", _uM("key" to 0, "class" to "product-filter-state"), _uA(
                                                _cE("text", _uM("class" to "product-filter-state-text"), "筛选选项加载中...")
                                            ))
                                        } else {
                                            if (unref(filterOptionsError) != "") {
                                                _cE("view", _uM("key" to 1, "class" to "product-filter-state"), _uA(
                                                    _cE("text", _uM("class" to "product-filter-state-text"), _tD(unref(filterOptionsError)), 1)
                                                ))
                                            } else {
                                                if (filterDefinitions.value.length > 0) {
                                                    _cE("view", _uM("key" to 2, "class" to "product-filter-groups"), _uA(
                                                        _cE(Fragment, null, RenderHelpers.renderList(filterDefinitions.value, fun(filter, __key, __index, _cached): Any {
                                                            return _cE("view", _uM("key" to filter.key, "class" to "product-filter-group"), _uA(
                                                                _cE("text", _uM("class" to "product-filter-group-title"), _tD(filter.label), 1),
                                                                _cE("view", _uM("class" to "product-filter-options"), _uA(
                                                                    _cE(Fragment, null, RenderHelpers.renderList(filter.options, fun(option, __key, __index, _cached): Any {
                                                                        return _cE("view", _uM("key" to (filter.key + "-" + option.value), "class" to _nC(if (isFilterOptionSelected(filter.param, option.value)) {
                                                                            "product-filter-option product-filter-option-active"
                                                                        } else {
                                                                            "product-filter-option"
                                                                        }), "onClick" to fun(){
                                                                            toggleFilterOption(filter.param, option.value, filter.multiple)
                                                                        }), _uA(
                                                                            _cE("text", _uM("class" to _nC(if (isFilterOptionSelected(filter.param, option.value)) {
                                                                                "product-filter-option-text product-filter-option-text-active"
                                                                            } else {
                                                                                "product-filter-option-text"
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
                                _cE("view", _uM("class" to "product-filter-actions"), _uA(
                                    _cE("view", _uM("class" to "product-filter-btn product-filter-btn-light", "onClick" to handleFilterReset), _uA(
                                        _cE("text", _uM("class" to "product-filter-btn-light-text"), "重置")
                                    )),
                                    _cE("view", _uM("class" to "product-filter-btn product-filter-btn-primary", "onClick" to applySelectedFilters), _uA(
                                        _cE("text", _uM("class" to "product-filter-btn-primary-text"), "应用")
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
                                    _cE("view", _uM("class" to "retry-btn", "onClick" to loadProducts), _uA(
                                        _cE("text", _uM("class" to "retry-btn-text"), "重新加载")
                                    ))
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            _cV(_component_lili_UniversalList, _uM("items" to listItems.value, "keyField" to "id", "titleField" to "name", "subtitleField" to "skuText", "metaField" to "barcodeText", "imageField" to "cover", "imageListField" to "images", "tagField" to "tags", "tagColorMap" to unref(tagColorMap), "fields" to unref(fieldConfig), "loading" to unref(isLoading), "loadingText" to "正在加载商品", "keepContentOnLoading" to true, "inlineLoadingText" to "商品数据刷新中...", "emptyText" to emptyText.value, "emptyIcon" to "◎", "showMenu" to true, "menuActions" to unref(menuActions), "showChevron" to false, "showPagination" to true, "currentPage" to unref(currentPage), "totalPages" to unref(totalPages), "totalCount" to unref(totalCount), "selectionMode" to unref(selectionMode), "selectedItems" to unref(selectedProductIds), "batchActions" to unref(batchToolbarActions), "batchInfoText" to batchInfoText.value, "summaryTitle" to "商品概览", "summaryItems" to summaryItems.value, "summaryCollapsedByDefault" to true, "showFloatingAdd" to true, "floatingAddText" to "新增商品", "onUpdate:selectionMode" to handleSelectionModeChange, "onUpdate:selectedItems" to handleSelectedProductIdsChange, "onSelectionExit" to handleSelectionExit, "onBatchAction" to handleBatchToolbarAction, "onItemClick" to handleItemClick, "onSubtitleClick" to handleSubtitleClick, "onMetaClick" to handleMetaClick, "onFieldClick" to handleFieldClick, "onMenu" to handleMenu, "onPageChange" to handlePageChange, "onFloatingAdd" to handleFloatingAdd), null, 8, _uA(
                                "items",
                                "tagColorMap",
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
                    _cV(_component_page_container, _uM("show" to unref(batchEditorVisible), "position" to "bottom", "round" to true, "overlay" to true, "duration" to 240, "overlay-style" to "background-color: rgba(15, 23, 42, 0.42);", "custom-style" to "background-color: #FFFFFF;", "onClickoverlay" to closeBatchEditor), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                        return _uA(
                            _cE("view", _uM("class" to "batch-panel"), _uA(
                                _cE("view", _uM("class" to "batch-handle")),
                                _cE("view", _uM("class" to "batch-head"), _uA(
                                    _cE("view", null, _uA(
                                        _cE("text", _uM("class" to "batch-title"), _tD(batchEditorTitle.value), 1),
                                        _cE("text", _uM("class" to "batch-subtitle"), _tD(batchEditorSubtitle.value), 1)
                                    )),
                                    _cE("view", _uM("class" to "batch-close", "onClick" to closeBatchEditor), _uA(
                                        _cE("text", _uM("class" to "batch-close-text"), "关闭")
                                    ))
                                )),
                                if (unref(batchEditorType) == "update-category") {
                                    _cE("view", _uM("key" to 0, "class" to "batch-field"), _uA(
                                        _cE("text", _uM("class" to "batch-label"), "目标分类"),
                                        _cV(unref(GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelectClass), _uM("value" to unref(batchCategoryValue), "valueText" to unref(batchCategoryText), "title" to "批量修改分类", "placeholder" to "请选择分类", "searchPlaceholder" to "请输入分类名称", "emptyText" to "暂无分类", "tree" to true, "checkStrictly" to false, "showAddAction" to false, "showEditAction" to false, "fetchData" to fetchCategoryFilterOptions, "onChange" to handleBatchCategoryChange), null, 8, _uA(
                                            "value",
                                            "valueText"
                                        ))
                                    ))
                                } else {
                                    if (unref(batchEditorType) == "update-supplier") {
                                        _cE("view", _uM("key" to 1, "class" to "batch-field"), _uA(
                                            _cE("text", _uM("class" to "batch-label"), "目标供应商"),
                                            _cV(unref(GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelectClass), _uM("value" to unref(batchSupplierValue), "valueText" to unref(batchSupplierText), "title" to "批量修改供应商", "placeholder" to "请选择供应商", "searchPlaceholder" to "请输入供应商名称", "emptyText" to "暂无供应商", "showAddAction" to false, "showEditAction" to false, "fetchData" to fetchSupplierFilterOptions, "onChange" to handleBatchSupplierChange), null, 8, _uA(
                                                "value",
                                                "valueText"
                                            ))
                                        ))
                                    } else {
                                        if (unref(batchEditorType) == "update-status") {
                                            _cE("view", _uM("key" to 2, "class" to "batch-field"), _uA(
                                                _cE("text", _uM("class" to "batch-label"), "目标状态"),
                                                _cE("view", _uM("class" to "batch-status-options"), _uA(
                                                    _cE(Fragment, null, RenderHelpers.renderList(unref(productStatusOptions), fun(option, __key, __index, _cached): Any {
                                                        return _cE("view", _uM("key" to option.value, "class" to _nC(if (isBatchStatusSelected(option)) {
                                                            "batch-status-option batch-status-option-active"
                                                        } else {
                                                            "batch-status-option"
                                                        }), "onClick" to fun(){
                                                            selectBatchStatus(option)
                                                        }), _uA(
                                                            _cE("text", _uM("class" to _nC(if (isBatchStatusSelected(option)) {
                                                                "batch-status-option-text batch-status-option-text-active"
                                                            } else {
                                                                "batch-status-option-text"
                                                            })), _tD(option.text), 3)
                                                        ), 10, _uA(
                                                            "onClick"
                                                        ))
                                                    }), 128)
                                                ))
                                            ))
                                        } else {
                                            _cC("v-if", true)
                                        }
                                    }
                                }
                                ,
                                _cE("view", _uM("class" to "batch-actions"), _uA(
                                    _cE("view", _uM("class" to "batch-secondary-btn", "onClick" to closeBatchEditor), _uA(
                                        _cE("text", _uM("class" to "batch-secondary-text"), "取消")
                                    )),
                                    _cE("view", _uM("class" to "batch-primary-btn", "onClick" to confirmBatchEditor), _uA(
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-scroll" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-content" to _pS(_uM("paddingLeft" to 6, "paddingRight" to 6, "paddingTop" to 6, "paddingBottom" to 96)), "error-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "paddingTop" to 18, "paddingRight" to 18, "paddingBottom" to 18, "paddingLeft" to 18, "marginBottom" to 14, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FECACA", "borderRightColor" to "#FECACA", "borderBottomColor" to "#FECACA", "borderLeftColor" to "#FECACA", "alignItems" to "center")), "error-title" to _pS(_uM("fontSize" to 18, "lineHeight" to "24px", "color" to "#B42318", "fontWeight" to "bold")), "error-desc" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#7F1D1D", "marginTop" to 8, "textAlign" to "center")), "retry-btn" to _pS(_uM("marginTop" to 14, "height" to 40, "paddingLeft" to 18, "paddingRight" to 18, "borderTopLeftRadius" to 20, "borderTopRightRadius" to 20, "borderBottomRightRadius" to 20, "borderBottomLeftRadius" to 20, "backgroundColor" to "#B42318", "alignItems" to "center", "justifyContent" to "center")), "retry-btn-text" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#FFFFFF", "fontWeight" to "bold")), "product-filter-panel" to _pS(_uM("position" to "relative", "paddingTop" to 2)), "product-filter-content-scroll" to _pS(_uM("paddingRight" to 2)), "product-filter-scroll-inner" to _pS(_uM("paddingBottom" to 58)), "product-filter-select-group" to _pS(_uM("paddingLeft" to 10, "paddingRight" to 10, "paddingTop" to 10, "paddingBottom" to 10, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#FFFFFF", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E5EAF1", "borderRightColor" to "#E5EAF1", "borderBottomColor" to "#E5EAF1", "borderLeftColor" to "#E5EAF1", "marginBottom" to 6)), "product-filter-select-title" to _pS(_uM("fontSize" to 13, "lineHeight" to "17px", "color" to "#0F172A", "fontWeight" to "bold")), "product-filter-select-wrap" to _pS(_uM("marginTop" to 8)), "product-filter-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 38, "borderTopLeftRadius" to 11, "borderTopRightRadius" to 11, "borderBottomRightRadius" to 11, "borderBottomLeftRadius" to 11, "alignItems" to "center", "justifyContent" to "center")), "product-filter-btn-light" to _pS(_uM("backgroundColor" to "#F3F6FA", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "marginRight" to 8)), "product-filter-btn-primary" to _pS(_uM("backgroundColor" to "#0F172A")), "product-filter-btn-light-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#475569")), "product-filter-btn-primary-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#FFFFFF")), "product-filter-state" to _pS(_uM("height" to 112, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#F8FAFC", "alignItems" to "center", "justifyContent" to "center")), "product-filter-state-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "17px", "color" to "#64748B")), "product-filter-groups" to _pS(_uM("marginBottom" to 6)), "product-filter-group" to _pS(_uM("paddingLeft" to 10, "paddingRight" to 10, "paddingTop" to 10, "paddingBottom" to 10, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#FFFFFF", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E5EAF1", "borderRightColor" to "#E5EAF1", "borderBottomColor" to "#E5EAF1", "borderLeftColor" to "#E5EAF1", "marginBottom" to 6)), "product-filter-group-title" to _pS(_uM("fontSize" to 13, "lineHeight" to "17px", "color" to "#0F172A", "fontWeight" to "bold")), "product-filter-options" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 8)), "product-filter-option" to _pS(_uM("minWidth" to 48, "height" to 30, "paddingLeft" to 10, "paddingRight" to 10, "borderTopLeftRadius" to 15, "borderTopRightRadius" to 15, "borderBottomRightRadius" to 15, "borderBottomLeftRadius" to 15, "backgroundColor" to "#F8FAFC", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "alignItems" to "center", "justifyContent" to "center", "marginRight" to 6, "marginBottom" to 6)), "product-filter-option-active" to _pS(_uM("backgroundColor" to "#0F172A", "borderTopColor" to "#0F172A", "borderRightColor" to "#0F172A", "borderBottomColor" to "#0F172A", "borderLeftColor" to "#0F172A")), "product-filter-option-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "17px", "color" to "#334155")), "product-filter-option-text-active" to _pS(_uM("color" to "#FFFFFF")), "product-filter-actions" to _pS(_uM("position" to "absolute", "left" to 0, "right" to 0, "bottom" to 0, "flexDirection" to "row", "marginTop" to 0, "paddingTop" to 6, "paddingLeft" to 2, "paddingRight" to 2, "paddingBottom" to 4, "borderTopWidth" to 1, "borderTopStyle" to "solid", "borderTopColor" to "rgba(226,232,240,0.78)", "backgroundColor" to "#FFFFFF")), "batch-panel" to _pS(_uM("paddingLeft" to 18, "paddingRight" to 18, "paddingTop" to 10, "paddingBottom" to 22, "backgroundColor" to "#FFFFFF")), "batch-handle" to _pS(_uM("width" to 42, "height" to 4, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2, "backgroundColor" to "#CBD5E1", "alignSelf" to "center", "marginBottom" to 14)), "batch-head" to _pS(_uM("flexDirection" to "row", "alignItems" to "flex-start", "justifyContent" to "space-between", "marginBottom" to 18)), "batch-title" to _pS(_uM("fontSize" to 18, "lineHeight" to "24px", "color" to "#0F172A", "fontWeight" to "bold")), "batch-subtitle" to _pS(_uM("fontSize" to 12, "lineHeight" to "18px", "color" to "#64748B", "marginTop" to 3)), "batch-close" to _pS(_uM("height" to 32, "paddingLeft" to 12, "paddingRight" to 12, "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "backgroundColor" to "#F1F5F9", "alignItems" to "center", "justifyContent" to "center")), "batch-close-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "17px", "color" to "#475569")), "batch-field" to _pS(_uM("marginBottom" to 16)), "batch-label" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#0F172A", "fontWeight" to "bold", "marginBottom" to 8)), "batch-status-options" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap")), "batch-status-option" to _pS(_uM("height" to 36, "minWidth" to 72, "paddingLeft" to 14, "paddingRight" to 14, "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "backgroundColor" to "#F8FAFC", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "alignItems" to "center", "justifyContent" to "center", "marginRight" to 8, "marginBottom" to 8)), "batch-status-option-active" to _pS(_uM("backgroundColor" to "#0F172A", "borderTopColor" to "#0F172A", "borderRightColor" to "#0F172A", "borderBottomColor" to "#0F172A", "borderLeftColor" to "#0F172A")), "batch-status-option-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#334155")), "batch-status-option-text-active" to _pS(_uM("color" to "#FFFFFF")), "batch-actions" to _pS(_uM("flexDirection" to "row", "marginTop" to 4)), "batch-secondary-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 42, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "#F8FAFC", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "marginRight" to 10)), "batch-primary-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 42, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "#0F172A")), "batch-secondary-text" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#475569", "fontWeight" to "bold")), "batch-primary-text" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#FFFFFF", "fontWeight" to "bold")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
