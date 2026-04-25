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
import io.dcloud.uniapp.extapi.getWindowInfo as uni_getWindowInfo
import io.dcloud.uniapp.extapi.setClipboardData as uni_setClipboardData
import io.dcloud.uniapp.extapi.showToast as uni_showToast
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
            val categoryFilterValue = ref("")
            val categoryFilterText = ref("")
            val filterPanelHeight = ref(456)
            val filterContentHeight = ref(392)
            val fieldConfig = ref(_uA<UTSJSONObject>(_uO("key" to "supplierText", "label" to "供应商"), _uO("key" to "purchasePriceText", "label" to "进价"), _uO("key" to "salesPriceText", "label" to "售价"), _uO("key" to "salesCountText", "label" to "销量")))
            val menuActions = ref(_uA<UTSJSONObject>(_uO("key" to "copy-sku", "text" to "复制SKU"), _uO("key" to "copy-barcode", "text" to "复制条码"), _uO("key" to "detail", "text" to "详情"), _uO("key" to "reload", "text" to "刷新")))
            val tagColorMap = ref(_uO("ACTIVE" to "success", "INACTIVE" to "danger", "DRAFT" to "warning", "新品" to "violet", "精选" to "info", "热销" to "warning"))
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
                        val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/tabbar/products.uvue:233")
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
                            , page = currentPage.value, page_size = pageSize.value, filters = selectedFilters.value)))
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
                return UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(text), " at pages/tabbar/products.uvue:326")
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
                val parsed = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(text), " at pages/tabbar/products.uvue:339")
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
            fun gen_buildImages_fn(item: ProductItem): UTSArray<String> {
                val result: UTSArray<String> = _uA()
                run {
                    var index: Number = 0
                    while(index < item.media_files.length){
                        val mediaFile = item.media_files[index]
                        if (mediaFile.signed_thumbnail_url != "") {
                            result.push(mediaFile.signed_thumbnail_url)
                            index += 1
                            continue
                        }
                        if (mediaFile.thumbnail_url != "") {
                            result.push(mediaFile.thumbnail_url)
                            index += 1
                            continue
                        }
                        if (mediaFile.signed_url != "") {
                            result.push(mediaFile.signed_url)
                            index += 1
                            continue
                        }
                        if (mediaFile.file_url != "") {
                            result.push(mediaFile.file_url)
                        }
                        index += 1
                    }
                }
                return result
            }
            val buildImages = ::gen_buildImages_fn
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
                val keywordValue = stringValue(params["keyword"]).toLowerCase()
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
                        val optionTextLowerCase = option.text.toLowerCase()
                        val optionValueLowerCase = option.value.toLowerCase()
                        if (keywordValue != "" && optionTextLowerCase.indexOf(keywordValue) < 0 && optionValueLowerCase.indexOf(keywordValue) < 0) {
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
                val source = extractCategoryTreeSource(raw)
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
                        val raw = await(request("/api/procurement/suppliers/options/", "GET", _uO(), true))
                        return@w1 buildBottomSelectResponse(buildSelectOptions(raw), params)
                })
            }
            val fetchSupplierFilterOptions = ::gen_fetchSupplierFilterOptions_fn
            fun gen_fetchCategoryFilterOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        val keywordValue = stringValue(params["keyword"])
                        val pageValue = stringValue(params["page"], "1")
                        val raw = await(request("/api/categories/categories/options/", "GET", _uO("key" to "parent", "search" to if (keywordValue == "") {
                            null
                        } else {
                            keywordValue
                        }
                        , "page" to if (pageValue == "") {
                            1
                        } else {
                            pageValue
                        }
                        , "page_size" to 200), true))
                        return@w1 buildCategoryTreeResponse(raw)
                })
            }
            val fetchCategoryFilterOptions = ::gen_fetchCategoryFilterOptions_fn
            fun gen_getProductImage_fn(item: ProductItem): String {
                if (item.media_files.length == 0) {
                    return ""
                }
                val mediaFile = item.media_files[0]
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
            val getProductImage = ::gen_getProductImage_fn
            fun gen_buildProductTags_fn(item: ProductItem): UTSArray<String> {
                val result: UTSArray<String> = _uA()
                val statusText = getDisplayText(item.status, "")
                if (statusText != "") {
                    result.push(statusText)
                }
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
                return if (item.sku == "") {
                    ("商品 #" + item.id.toString(10))
                } else {
                    item.sku
                }
            }
            val productDisplayName = ::gen_productDisplayName_fn
            fun gen_productToListItem_fn(item: ProductItem): UTSJSONObject {
                return _uO("id" to item.id.toString(10), "name" to productDisplayName(item), "name_en" to getDisplayText(item.name_en), "sku" to getDisplayText(item.sku), "skuText" to ("SKU：" + getDisplayText(item.sku)), "barcode" to getDisplayText(item.barcode), "barcodeText" to ("条码：" + getDisplayText(item.barcode)), "supplierText" to getDisplayText(item.supplier_name), "purchasePriceText" to getDisplayText(item.purchase_price), "salesPriceText" to getDisplayText(item.base_sales_price), "salesCountText" to item.total_sales_quantity.toString(10), "variantCountText" to item.variant_count.toString(10), "updatedText" to getDisplayText(item.updated_at), "cover" to getProductImage(item), "images" to buildImages(item), "tags" to buildProductTags(item), "rawId" to item.id.toString(10))
            }
            val productToListItem = ::gen_productToListItem_fn
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
            fun gen_handleCategoryFilterChange_fn(payload: UTSJSONObject) {
                categoryFilterValue.value = stringValue(payload["value"])
                categoryFilterText.value = stringValue(payload["text"])
                setSelectedFilterValue("category", categoryFilterValue.value)
            }
            val handleCategoryFilterChange = ::gen_handleCategoryFilterChange_fn
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
                categoryFilterValue.value = ""
                categoryFilterText.value = ""
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
                uni_showToast(ShowToastOptions(title = itemName, icon = "none"))
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
                if (key == "purchasePriceText") {
                    copyText(stringValue(item["purchasePriceText"]), "进价已复制", "暂无进价")
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
                    uni_showToast(ShowToastOptions(title = "商品详情页待接入", icon = "none"))
                    return
                }
                if (key == "reload") {
                    loadProducts()
                }
            }
            val handleMenu = ::gen_handleMenu_fn
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
            val summaryItems = computed(fun(): UTSArray<UTSJSONObject> {
                return _uA(
                    _uO("key" to "total", "label" to "商品总数", "value" to totalCount.value.toString(10)),
                    _uO("key" to "page", "label" to "当前页", "value" to (currentPage.value.toString(10) + "/" + totalPages.value.toString(10))),
                    _uO("key" to "loaded", "label" to "当前页条数", "value" to products.value.length.toString(10))
                )
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
                updateFilterPanelLayout()
                if (products.value.length == 0 && !isLoading.value) {
                    loadProducts()
                }
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList", GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalListClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to "商品", "searchPlaceholder" to "输入商品名称、SKU、条码", "searchValue" to unref(keyword), "filterVisible" to unref(filterVisible), "showBack" to false, "showSearch" to true, "showFilter" to true, "showHome" to false, "filterActive" to hasActiveFilter.value, "filterText" to "重置", "onSearchInput" to handleSearchInput, "onSearchConfirm" to handleSearchConfirm, "onSearchClear" to handleSearchClear, "onUpdate:filterVisible" to handleFilterVisibleChange, "onFilterOpen" to handleFilterOpen), _uM("filter-panel" to withSlotCtx(fun(): UTSArray<Any> {
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
                                                _cV(unref(GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelectClass), _uM("value" to unref(categoryFilterValue), "valueText" to unref(categoryFilterText), "title" to "选择分类", "placeholder" to "请选择分类", "searchPlaceholder" to "请输入分类名称", "emptyText" to "暂无分类", "tree" to true, "expandOnClickNode" to true, "showAddAction" to false, "showEditAction" to false, "fetchData" to fetchCategoryFilterOptions, "onChange" to handleCategoryFilterChange), null, 8, _uA(
                                                    "value",
                                                    "valueText"
                                                ))
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
                                    _cE("button", _uM("class" to "retry-btn", "onClick" to loadProducts), _uA(
                                        _cE("text", _uM("class" to "retry-btn-text"), "重新加载")
                                    ))
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            _cV(_component_lili_UniversalList, _uM("items" to listItems.value, "keyField" to "id", "titleField" to "name", "subtitleField" to "skuText", "metaField" to "barcodeText", "imageField" to "cover", "imageListField" to "images", "tagField" to "tags", "tagColorMap" to unref(tagColorMap), "fields" to unref(fieldConfig), "loading" to unref(isLoading), "loadingText" to "正在加载商品", "keepContentOnLoading" to true, "inlineLoadingText" to "商品数据刷新中...", "emptyText" to emptyText.value, "emptyIcon" to "◎", "showMenu" to true, "menuActions" to unref(menuActions), "showChevron" to false, "showPagination" to true, "currentPage" to unref(currentPage), "totalPages" to unref(totalPages), "totalCount" to unref(totalCount), "summaryTitle" to "商品概览", "summaryItems" to summaryItems.value, "summaryCollapsedByDefault" to true, "showFloatingAdd" to false, "onItemClick" to handleItemClick, "onSubtitleClick" to handleSubtitleClick, "onMetaClick" to handleMetaClick, "onFieldClick" to handleFieldClick, "onMenu" to handleMenu, "onPageChange" to handlePageChange), null, 8, _uA(
                                "items",
                                "tagColorMap",
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-scroll" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-content" to _pS(_uM("paddingLeft" to 6, "paddingRight" to 6, "paddingTop" to 6, "paddingBottom" to 96)), "error-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "paddingTop" to 18, "paddingRight" to 18, "paddingBottom" to 18, "paddingLeft" to 18, "marginBottom" to 14, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FECACA", "borderRightColor" to "#FECACA", "borderBottomColor" to "#FECACA", "borderLeftColor" to "#FECACA", "alignItems" to "center")), "error-title" to _pS(_uM("fontSize" to 18, "lineHeight" to "24px", "color" to "#B42318", "fontWeight" to "bold")), "error-desc" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#7F1D1D", "marginTop" to 8, "textAlign" to "center")), "retry-btn" to _pS(_uM("marginTop" to 14, "height" to 40, "paddingLeft" to 18, "paddingRight" to 18, "borderTopLeftRadius" to 20, "borderTopRightRadius" to 20, "borderBottomRightRadius" to 20, "borderBottomLeftRadius" to 20, "backgroundColor" to "#B42318", "alignItems" to "center", "justifyContent" to "center")), "retry-btn-text" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#FFFFFF", "fontWeight" to "bold")), "product-filter-panel" to _pS(_uM("position" to "relative", "paddingTop" to 2)), "product-filter-content-scroll" to _pS(_uM("paddingRight" to 2)), "product-filter-scroll-inner" to _pS(_uM("paddingBottom" to 58)), "product-filter-select-group" to _pS(_uM("paddingLeft" to 10, "paddingRight" to 10, "paddingTop" to 10, "paddingBottom" to 10, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#FFFFFF", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E5EAF1", "borderRightColor" to "#E5EAF1", "borderBottomColor" to "#E5EAF1", "borderLeftColor" to "#E5EAF1", "marginBottom" to 6)), "product-filter-select-title" to _pS(_uM("fontSize" to 13, "lineHeight" to "17px", "color" to "#0F172A", "fontWeight" to "bold")), "product-filter-select-wrap" to _pS(_uM("marginTop" to 8)), "product-filter-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 38, "borderTopLeftRadius" to 11, "borderTopRightRadius" to 11, "borderBottomRightRadius" to 11, "borderBottomLeftRadius" to 11, "alignItems" to "center", "justifyContent" to "center")), "product-filter-btn-light" to _pS(_uM("backgroundColor" to "#F3F6FA", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "marginRight" to 8)), "product-filter-btn-primary" to _pS(_uM("backgroundColor" to "#0F172A")), "product-filter-btn-light-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "13px", "color" to "#475569")), "product-filter-btn-primary-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "13px", "color" to "#FFFFFF")), "product-filter-state" to _pS(_uM("height" to 112, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#F8FAFC", "alignItems" to "center", "justifyContent" to "center")), "product-filter-state-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "17px", "color" to "#64748B")), "product-filter-groups" to _pS(_uM("marginBottom" to 6)), "product-filter-group" to _pS(_uM("paddingLeft" to 10, "paddingRight" to 10, "paddingTop" to 10, "paddingBottom" to 10, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#FFFFFF", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E5EAF1", "borderRightColor" to "#E5EAF1", "borderBottomColor" to "#E5EAF1", "borderLeftColor" to "#E5EAF1", "marginBottom" to 6)), "product-filter-group-title" to _pS(_uM("fontSize" to 13, "lineHeight" to "17px", "color" to "#0F172A", "fontWeight" to "bold")), "product-filter-options" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 8)), "product-filter-option" to _pS(_uM("minWidth" to 48, "height" to 30, "paddingLeft" to 10, "paddingRight" to 10, "borderTopLeftRadius" to 15, "borderTopRightRadius" to 15, "borderBottomRightRadius" to 15, "borderBottomLeftRadius" to 15, "backgroundColor" to "#F8FAFC", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "alignItems" to "center", "justifyContent" to "center", "marginRight" to 6, "marginBottom" to 6)), "product-filter-option-active" to _pS(_uM("backgroundColor" to "#0F172A", "borderTopColor" to "#0F172A", "borderRightColor" to "#0F172A", "borderBottomColor" to "#0F172A", "borderLeftColor" to "#0F172A")), "product-filter-option-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "12px", "color" to "#334155")), "product-filter-option-text-active" to _pS(_uM("color" to "#FFFFFF")), "product-filter-actions" to _pS(_uM("position" to "absolute", "left" to 0, "right" to 0, "bottom" to 0, "flexDirection" to "row", "marginTop" to 0, "paddingTop" to 6, "paddingLeft" to 2, "paddingRight" to 2, "paddingBottom" to 4, "borderTopWidth" to 1, "borderTopStyle" to "solid", "borderTopColor" to "rgba(226,232,240,0.78)", "backgroundColor" to "#FFFFFF")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
