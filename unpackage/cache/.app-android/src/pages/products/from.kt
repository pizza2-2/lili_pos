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
import io.dcloud.uniapp.extapi.hideLoading as uni_hideLoading
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.removeStorageSync as uni_removeStorageSync
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
import io.dcloud.uniapp.extapi.switchTab as uni_switchTab
open class GenPagesProductsFrom : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesProductsFrom) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesProductsFrom
            val _cache = __ins.renderCache
            val productListRefreshStorageKey = "refresh:pages:products:index"
            val productDiscountSelectionStorageKey = "selected_discount_for_product:"
            val formMode = ref("create")
            val productId = ref("")
            val copySourceId = ref("")
            val productFormRef = ref<ComponentPublicInstance?>(null)
            val leaveSignal = ref(0)
            val dirtySignal = ref(0)
            val submitting = ref(false)
            val savingVisible = ref(false)
            val savingText = ref("处理中...")
            val printPopupVisible = ref(false)
            val pageTaskGuard = createAsyncGuard()
            val discountCards = ref(_uA<UTSJSONObject>())
            val discountCardsLoading = ref(false)
            val categoryTaxRateCache = ref<UTSJSONObject>(_uO())
            val initialData = ref<UTSJSONObject>(_uO("sku" to "", "barcode" to "", "name_cn" to "", "name_en" to "", "name_other" to "", "description" to "", "category_id" to "", "category_text" to "", "category_kasa_kod" to "", "supplier_id" to "", "supplier_name" to "", "purchase_price" to "0.00", "net_purchase_price" to "0.00", "cost_price" to "0.00", "base_sales_price" to "0.00", "discount_rule" to "", "discount_rule_id" to "", "discounted_base_sales_price" to "0.00", "status" to "ACTIVE", "is_featured" to false, "is_new" to false, "is_bestseller" to false, "sort_order" to "0", "images" to _uA<String>(), "imageItems" to _uA<UTSJSONObject>()))
            val statusOptions = ref(_uA<SelectOption__6>(SelectOption__6(value = "ACTIVE", text = "启用"), SelectOption__6(value = "INACTIVE", text = "停用"), SelectOption__6(value = "DRAFT", text = "草稿")))
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
            fun intValue(value: Any?, fallback: Number = 0): Number {
                val text = stringValue(value)
                if (text == "") {
                    return fallback
                }
                val parsed = parseInt(text)
                if (isNaN(parsed)) {
                    return fallback
                }
                return parsed
            }
            fun gen_openProductPrintPage_fn() {
                if (productId.value == "") {
                    return
                }
                printPopupVisible.value = true
            }
            val openProductPrintPage = ::gen_openProductPrintPage_fn
            fun gen_handlePrintPopupVisibleChange_fn(value: Boolean) {
                printPopupVisible.value = value
            }
            val handlePrintPopupVisibleChange = ::gen_handlePrintPopupVisibleChange_fn
            fun productPrintField(key: String, fallback: String = ""): String {
                return stringValue(initialData.value[key], fallback)
            }
            fun gen_productPrintNameText_fn(): String {
                val nameCn = productPrintField("name_cn")
                if (nameCn != "") {
                    return nameCn
                }
                val nameEn = productPrintField("name_en")
                if (nameEn != "") {
                    return nameEn
                }
                val nameOther = productPrintField("name_other")
                if (nameOther != "") {
                    return nameOther
                }
                return "未命名商品"
            }
            val productPrintNameText = ::gen_productPrintNameText_fn
            fun gen_productPrintPriceText_fn(): String {
                val discountPrice = productPrintField("discounted_base_sales_price")
                if (discountPrice != "" && discountPrice != "0.00") {
                    return discountPrice
                }
                return productPrintField("base_sales_price", "0.00")
            }
            val productPrintPriceText = ::gen_productPrintPriceText_fn
            fun gen_productPrintKodText_fn(fallback: String): String {
                return productPrintField("category_kasa_kod", fallback)
            }
            val productPrintKodText = ::gen_productPrintKodText_fn
            val productPrintData = computed(fun(): UTSJSONObject {
                val data: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("data", "pages/products/from.uvue", 245, 8))
                data["name"] = productPrintNameText()
                data["name_cn"] = productPrintField("name_cn")
                data["name_en"] = productPrintField("name_en")
                data["name_other"] = productPrintField("name_other")
                data["price"] = productPrintPriceText()
                data["base_sales_price"] = productPrintField("base_sales_price", "0.00")
                data["discount_price"] = productPrintPriceText()
                data["barcode"] = productPrintField("barcode")
                data["sku"] = productPrintField("sku")
                data["kod"] = productPrintKodText("")
                data["category_kasa_kod"] = productPrintKodText("")
                return data
            }
            )
            fun gen_resolveProductPrintValue_fn(source: String, fallback: String): String {
                if (source == "name") {
                    return productPrintNameText()
                }
                if (source == "name_cn") {
                    return productPrintField("name_cn")
                }
                if (source == "name_en") {
                    return productPrintField("name_en")
                }
                if (source == "name_other") {
                    return productPrintField("name_other")
                }
                if (source == "price") {
                    return productPrintPriceText()
                }
                if (source == "base_sales_price") {
                    return productPrintField("base_sales_price", "0.00")
                }
                if (source == "discount_price") {
                    return productPrintPriceText()
                }
                if (source == "barcode") {
                    val barcode = productPrintField("barcode")
                    if (barcode != "") {
                        return barcode
                    }
                    return productPrintField("sku", fallback)
                }
                if (source == "sku") {
                    return productPrintField("sku", fallback)
                }
                if (source == "kod") {
                    return productPrintKodText(fallback)
                }
                return fallback
            }
            val resolveProductPrintValue = ::gen_resolveProductPrintValue_fn
            fun floatValue(value: Any?, fallback: Number = 0): Number {
                val text = stringValue(value)
                if (text == "") {
                    return fallback
                }
                val parsed = parseFloat(text)
                if (isNaN(parsed)) {
                    return fallback
                }
                return parsed
            }
            fun gen_booleanValue_fn(value: Any?): Boolean {
                if (value == null) {
                    return false
                }
                val text = stringValue(value).toLowerCase()
                return text == "true" || text == "1" || text == "yes"
            }
            val booleanValue = ::gen_booleanValue_fn
            fun getStringField(obj: UTSJSONObject, key: String, fallback: String = ""): String {
                return stringValue(obj[key], fallback)
            }
            fun formatMoneyText(value: String, fallback: String = "0.00"): String {
                val numberValue = floatValue(value, -1)
                if (numberValue < 0) {
                    return fallback
                }
                return numberValue.toFixed(2)
            }
            fun gen_normalizeTaxRate_fn(value: Any?): Number {
                var taxRate = floatValue(value, -1)
                if (taxRate < 0) {
                    return -1
                }
                if (taxRate > 1) {
                    taxRate = taxRate / 100
                }
                return taxRate
            }
            val normalizeTaxRate = ::gen_normalizeTaxRate_fn
            fun gen_resolveCategoryTaxRate_fn(categoryId: String): UTSPromise<Number> {
                return wrapUTSPromise(suspend w1@{
                        if (categoryId == "") {
                            return@w1 -1
                        }
                        val cachedValue = categoryTaxRateCache.value[categoryId]
                        if (cachedValue != null) {
                            return@w1 normalizeTaxRate(cachedValue)
                        }
                        try {
                            val raw = await(request("/api/categories/categories/" + categoryId + "/", "GET", _uO(), true))
                            val rawObject = parseObject(raw)
                            if (rawObject == null) {
                                return@w1 -1
                            }
                            val taxRate = normalizeTaxRate(rawObject["tax_rate"])
                            if (taxRate >= 0) {
                                categoryTaxRateCache.value[categoryId] = taxRate
                            }
                            return@w1 taxRate
                        }
                         catch (error: Throwable) {
                            return@w1 -1
                        }
                })
            }
            val resolveCategoryTaxRate = ::gen_resolveCategoryTaxRate_fn
            fun gen_applyPurchasePriceSync_fn(formDataObject: UTSJSONObject, taxRate: Number, sourceKey: String): Boolean {
                if (taxRate < 0) {
                    return false
                }
                val multiplier = 1 + taxRate
                if (multiplier <= 0) {
                    return false
                }
                val grossPrice = floatValue(formDataObject["purchase_price"], 0)
                val netPrice = floatValue(formDataObject["net_purchase_price"], 0)
                val canFillNet = sourceKey == "category_id" || sourceKey == "purchase_price"
                val canFillGross = sourceKey == "category_id" || sourceKey == "net_purchase_price"
                if (canFillNet && grossPrice > 0 && netPrice <= 0) {
                    formDataObject["net_purchase_price"] = formatMoneyText((grossPrice / multiplier).toString(10))
                    return true
                }
                if (canFillGross && netPrice > 0 && grossPrice <= 0) {
                    formDataObject["purchase_price"] = formatMoneyText((netPrice * multiplier).toString(10))
                    return true
                }
                return false
            }
            val applyPurchasePriceSync = ::gen_applyPurchasePriceSync_fn
            fun gen_calculateDiscountedPriceText_fn(productSalesPriceText: String, discountRule: UTSJSONObject): String {
                val basePrice = floatValue(productSalesPriceText, 0)
                if (basePrice <= 0) {
                    return "0.00"
                }
                val discountType = getStringField(discountRule, "discount_type")
                var percentValue = floatValue(discountRule["discount_percentage"])
                if (percentValue > 0 && discountType == "PERCENTAGE") {
                    return formatMoneyText((basePrice * (1 - percentValue / 100)).toString(10))
                }
                var amountValue = floatValue(discountRule["discount_amount"])
                if (amountValue <= 0 && discountRule["discount_amount_fixed"] != null) {
                    amountValue = floatValue(discountRule["discount_amount_fixed"])
                }
                if (amountValue > 0 && (discountType == "FIXED_AMOUNT" || discountType == "FIXED")) {
                    val nextPrice = basePrice - amountValue
                    if (nextPrice <= 0) {
                        return "0.00"
                    }
                    return formatMoneyText(nextPrice.toString(10))
                }
                return basePrice.toFixed(2)
            }
            val calculateDiscountedPriceText = ::gen_calculateDiscountedPriceText_fn
            fun gen_getArrayField_fn(obj: UTSJSONObject, key: String): UTSArray<String> {
                val value = obj[key]
                if (value == null) {
                    return _uA<String>()
                }
                return value as UTSArray<String>
            }
            val getArrayField = ::gen_getArrayField_fn
            fun gen_parseObject_fn(value: Any?): UTSJSONObject? {
                if (value == null) {
                    return null
                }
                val text = JSON.stringify(value)
                if (text == null || text == "") {
                    return null
                }
                return UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(text), " at pages/products/from.uvue:398")
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
                val parsed = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(text), " at pages/products/from.uvue:409")
                if (parsed == null) {
                    return _uA<UTSJSONObject>()
                }
                return parsed!!
            }
            val parseObjectArray = ::gen_parseObjectArray_fn
            fun gen_parseErrorMessage_fn(error: Any, fallback: String): String {
                var message = fallback
                if (error != null) {
                    val errorText = JSON.stringify(error)
                    if (errorText != null && errorText != "") {
                        val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/products/from.uvue:421")
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
            fun gen_buildUploadHeaders_fn(): UTSJSONObject {
                val headers: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("headers", "pages/products/from.uvue", 437, 8))
                if (authState.token != "") {
                    headers["Authorization"] = authState.token
                }
                return headers
            }
            val buildUploadHeaders = ::gen_buildUploadHeaders_fn
            fun gen_buildSelectResponse_fn(source: UTSArray<SelectOption__6>, params: UTSJSONObject): UTSJSONObject {
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
                return _uO("data" to result, "total" to result.length)
            }
            val buildSelectResponse = ::gen_buildSelectResponse_fn
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
                            run {
                                var itemIndex: Number = 0
                                while(itemIndex < groupItems.length){
                                    items.push(groupItems[itemIndex])
                                    itemIndex += 1
                                }
                            }
                            groupIndex += 1
                        }
                    }
                    if (items.length > 0) {
                        return items
                    }
                    val optionsObject = parseObject(rawObject["options"])
                    if (optionsObject != null) {
                        val safeOptionsObject = optionsObject as UTSJSONObject
                        for(key in resolveUTSKeyIterator(safeOptionsObject)){
                            val optionItems = parseObjectArray(safeOptionsObject[key])
                            run {
                                var itemIndex: Number = 0
                                while(itemIndex < optionItems.length){
                                    items.push(optionItems[itemIndex])
                                    itemIndex += 1
                                }
                            }
                        }
                        if (items.length > 0) {
                            return items
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
            fun gen_buildSelectOptions_fn(value: Any?): UTSArray<SelectOption__6> {
                val source = extractOptionObjects(value)
                val result: UTSArray<SelectOption__6> = _uA()
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
                        result.push(SelectOption__6(value = optionValue, text = if (optionText == "") {
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
            fun gen_fetchStatusOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        return@w1 buildSelectResponse(statusOptions.value, params)
                })
            }
            val fetchStatusOptions = ::gen_fetchStatusOptions_fn
            fun gen_buildSupplierOptionQuery_fn(params: UTSJSONObject): UTSJSONObject {
                val keywordValue = stringValue(params["keyword"])
                val query: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("query", "pages/products/from.uvue", 559, 8), "key" to "supplier", "limit" to 50)
                if (keywordValue != "") {
                    query["search"] = keywordValue
                    query["keyword"] = keywordValue
                }
                return query
            }
            val buildSupplierOptionQuery = ::gen_buildSupplierOptionQuery_fn
            fun gen_fetchSupplierOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        val raw = await(request("/api/procurement/suppliers/options/", "GET", buildSupplierOptionQuery(params), true))
                        return@w1 buildSelectResponse(buildSelectOptions(raw), _uO("keyword" to "", "id" to stringValue(params["id"])))
                })
            }
            val fetchSupplierOptions = ::gen_fetchSupplierOptions_fn
            fun gen_convertCategoryTreeItems_fn(items: UTSArray<UTSJSONObject>): UTSArray<UTSJSONObject> {
                val result: UTSArray<UTSJSONObject> = _uA()
                run {
                    var i: Number = 0
                    while(i < items.length){
                        val item = items[i]
                        val children = parseObjectArray(item["children"])
                        val treeChildren = gen_convertCategoryTreeItems_fn(children)
                        val label = buildOptionText(item)
                        val option: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("option", "pages/products/from.uvue", 587, 9), "value" to buildOptionValue(item), "text" to label, "label" to label, "full_name" to stringValue(item["full_name"], label), "code" to stringValue(item["code"]), "level" to intValue(item["level"], -1), "parent_value" to stringValue(item["parent_value"]), "disabled" to booleanValue(item["disabled"]), "has_children" to (booleanValue(item["has_children"]) || treeChildren.length > 0), "children" to treeChildren)
                        result.push(option)
                        i++
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
                var items = parseObjectArray(rawObject["items"])
                if (items.length > 0) {
                    return items
                }
                items = parseObjectArray(rawObject["results"])
                if (items.length > 0) {
                    return items
                }
                return parseObjectArray(rawObject["data"])
            }
            val extractCategoryTreeSource = ::gen_extractCategoryTreeSource_fn
            fun gen_fetchCategoryOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        val keywordValue = getStringField(params, "keyword")
                        val idValue = getStringField(params, "id")
                        val parentValue = getStringField(params, "parent")
                        val pageValue = getStringField(params, "page", "1")
                        val pageSizeValue = getStringField(params, "pageSize", "20")
                        val queryParams: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("queryParams", "pages/products/from.uvue", 639, 8), "key" to "parent", "page" to intValue(pageValue, 1), "page_size" to intValue(pageSizeValue, 20))
                        if (keywordValue != "") {
                            queryParams["search"] = keywordValue
                        }
                        if (idValue != "") {
                            queryParams["id"] = idValue
                        }
                        if (parentValue != "") {
                            queryParams["parent"] = parentValue
                        }
                        val raw = await(request("/api/categories/categories/options/", "GET", queryParams, true))
                        val source = extractCategoryTreeSource(raw)
                        val treeItems = convertCategoryTreeItems(source)
                        return@w1 _uO("data" to treeItems, "total" to treeItems.length)
                })
            }
            val fetchCategoryOptions = ::gen_fetchCategoryOptions_fn
            fun gen_findCategoryTextInTree_fn(items: UTSArray<UTSJSONObject>, categoryId: String): String {
                run {
                    var index: Number = 0
                    while(index < items.length){
                        val item = items[index]
                        if (stringValue(item["value"]) == categoryId) {
                            return stringValue(item["text"], categoryId)
                        }
                        val children = parseObjectArray(item["children"])
                        if (children.length > 0) {
                            val found = gen_findCategoryTextInTree_fn(children, categoryId)
                            if (found != "") {
                                return found
                            }
                        }
                        index += 1
                    }
                }
                return ""
            }
            val findCategoryTextInTree = ::gen_findCategoryTextInTree_fn
            fun gen_resolveCategoryOptionText_fn(categoryId: String): UTSPromise<String> {
                return wrapUTSPromise(suspend w1@{
                        if (categoryId == "") {
                            return@w1 ""
                        }
                        try {
                            val response = await(fetchCategoryOptions(_uO("id" to categoryId, "keyword" to "", "page" to "1", "pageSize" to "20")))
                            val items = parseObjectArray(response["data"])
                            val found = findCategoryTextInTree(items, categoryId)
                            if (found != "") {
                                return@w1 found
                            }
                        }
                         catch (error: Throwable) {}
                        return@w1 ""
                })
            }
            val resolveCategoryOptionText = ::gen_resolveCategoryOptionText_fn
            fun gen_extractCategoryIdFromProduct_fn(item: ProductItem): String {
                val categoryObject = parseObject(item.category)
                if (categoryObject != null) {
                    return buildOptionValue(categoryObject as UTSJSONObject)
                }
                if (item.category != null) {
                    return stringValue(item.category)
                }
                return ""
            }
            val extractCategoryIdFromProduct = ::gen_extractCategoryIdFromProduct_fn
            fun buildInitialDataFromProduct(item: ProductItem, categoryTextOverride: String = ""): UTSJSONObject {
                val images: UTSArray<String> = _uA()
                val imageItems: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < item.media_files.length){
                        val mediaFile = item.media_files[index]
                        var imageUrl = ""
                        var previewUrl = ""
                        if (mediaFile.signed_thumbnail_url != "") {
                            imageUrl = mediaFile.signed_thumbnail_url
                        } else if (mediaFile.thumbnail_url != "") {
                            imageUrl = mediaFile.thumbnail_url
                        } else if (mediaFile.signed_url != "") {
                            imageUrl = mediaFile.signed_url
                        } else if (mediaFile.file_url != "") {
                            imageUrl = mediaFile.file_url
                        }
                        if (mediaFile.signed_url != "") {
                            previewUrl = mediaFile.signed_url
                        } else if (mediaFile.file_url != "") {
                            previewUrl = mediaFile.file_url
                        } else {
                            previewUrl = imageUrl
                        }
                        if (imageUrl != "") {
                            images.push(imageUrl)
                            imageItems.push(_uO("id" to mediaFile.id, "path" to imageUrl, "url" to imageUrl, "previewUrl" to previewUrl, "signed_url" to mediaFile.signed_url, "file_url" to mediaFile.file_url, "isRemote" to true))
                        }
                        index += 1
                    }
                }
                var categoryId = ""
                var categoryText = ""
                val categoryObject = parseObject(item.category)
                if (categoryObject != null) {
                    val safeCategoryObject = categoryObject as UTSJSONObject
                    categoryId = buildOptionValue(safeCategoryObject)
                    categoryText = buildOptionText(safeCategoryObject)
                } else if (item.category != null) {
                    categoryId = stringValue(item.category)
                    categoryText = categoryId
                }
                if (categoryTextOverride != "") {
                    categoryText = categoryTextOverride
                }
                val supplierId = if (item.supplier == null) {
                    ""
                } else {
                    stringValue(item.supplier)
                }
                val discountRuleText = if (item.discount_info == null || !item.discount_info!!.has_discount) {
                    ""
                } else {
                    item.discount_info!!.discount_name
                }
                val discountedBaseSalesPrice = if (item.discount_info == null || !item.discount_info!!.has_discount) {
                    ""
                } else {
                    item.discount_info!!.final_price
                }
                return _uO("sku" to item.sku, "barcode" to item.barcode, "name_cn" to item.name_cn, "name_en" to item.name_en, "name_other" to item.name_other, "description" to item.description, "category_id" to categoryId, "category_text" to categoryText, "category_kasa_kod" to item.category_kasa_kod, "supplier_id" to supplierId, "supplier_name" to item.supplier_name, "purchase_price" to if (item.purchase_price == "") {
                    "0.00"
                } else {
                    item.purchase_price
                }
                , "net_purchase_price" to if (item.net_purchase_price == "") {
                    "0.00"
                } else {
                    item.net_purchase_price
                }
                , "cost_price" to if (item.cost_price == "") {
                    "0.00"
                } else {
                    item.cost_price
                }
                , "base_sales_price" to if (item.base_sales_price == "") {
                    "0.00"
                } else {
                    item.base_sales_price
                }
                , "discount_rule" to discountRuleText, "discount_rule_id" to if (item.discount_info == null) {
                    ""
                } else {
                    stringValue(item.discount_info!!.discount_id)
                }
                , "discounted_base_sales_price" to discountedBaseSalesPrice, "status" to if (item.status == "") {
                    "ACTIVE"
                } else {
                    item.status
                }
                , "is_featured" to item.is_featured, "is_new" to item.is_new, "is_bestseller" to item.is_bestseller, "sort_order" to item.sort_order.toString(10), "images" to images, "imageItems" to imageItems)
            }
            fun buildCopiedInitialDataFromProduct(item: ProductItem, categoryTextOverride: String = ""): UTSJSONObject {
                val data = buildInitialDataFromProduct(item, categoryTextOverride)
                data["sku"] = ""
                data["barcode"] = ""
                data["discount_rule"] = ""
                data["discount_rule_id"] = ""
                data["discounted_base_sales_price"] = "0.00"
                data["images"] = _uA<String>()
                data["imageItems"] = _uA<UTSJSONObject>()
                return data
            }
            val formSections = ref(_uA<UTSJSONObject>(_uO("key" to "base", "title" to "基础信息", "description" to "", "defaultOpen" to false, "fields" to _uA<UTSJSONObject>(_uO("key" to "name_cn", "label" to "中文名称", "type" to "input", "required" to true, "placeholder" to "请输入中文名称"), _uO("key" to "name_en", "label" to "波兰名称", "type" to "input", "placeholder" to "请输入波兰名称"), _uO("key" to "name_other", "label" to "其他名称", "type" to "input", "placeholder" to "请输入其他名称"), _uO("key" to "sku", "label" to "SKU", "type" to "input", "placeholder" to "请输入SKU"), _uO("key" to "barcode", "label" to "条码", "type" to "input", "showScan" to true, "placeholder" to "请输入或扫描条码"), _uO("key" to "supplier_id", "label" to "供应商", "type" to "bottomSelect", "textKey" to "supplier_name", "title" to "选择供应商", "placeholder" to "请选择供应商", "searchPlaceholder" to "请输入供应商名称", "showAddAction" to true, "showEditAction" to true, "addPath" to "/pages/suppliers/from", "editPath" to "/pages/suppliers/from", "fetchData" to fetchSupplierOptions), _uO("key" to "category_id", "label" to "商品分类", "type" to "bottomSelect", "textKey" to "category_text", "title" to "选择商品分类", "placeholder" to "请选择商品分类", "searchPlaceholder" to "请输入分类名称", "showAddAction" to true, "showEditAction" to true, "addPath" to "/pages/category/from", "editPath" to "/pages/category/from", "tree" to true, "expandOnClickNode" to true, "selectableLevel" to 2, "selectableLevelMessage" to "只能选择 level 2 分类", "fetchData" to fetchCategoryOptions), _uO("key" to "description", "label" to "描述", "type" to "textarea", "placeholder" to "请输入商品描述"))), _uO("key" to "price", "title" to "价格信息", "description" to "", "defaultOpen" to false, "fields" to _uA<UTSJSONObject>(_uO("key" to "purchase_price", "label" to "含税采购价", "type" to "number", "required" to true, "decimal" to true, "placeholder" to "请输入含税采购价"), _uO("key" to "net_purchase_price", "label" to "不含税采购价", "type" to "number", "required" to true, "decimal" to true, "placeholder" to "请输入不含税采购价"), _uO("key" to "cost_price", "label" to "成本价", "type" to "number", "required" to true, "decimal" to true, "placeholder" to "请输入成本价"), _uO("key" to "base_sales_price", "label" to "基础售价", "type" to "number", "required" to true, "decimal" to true, "placeholder" to "请输入基础售价"), _uO("key" to "discount_rule", "label" to "折扣规则", "type" to "custom", "readonly" to true))), _uO("key" to "status", "title" to "状态设置", "description" to "", "defaultOpen" to false, "fields" to _uA<UTSJSONObject>(_uO("key" to "status", "label" to "商品状态", "type" to "bottomSelect", "title" to "选择商品状态", "placeholder" to "请选择商品状态", "showAddAction" to false, "showEditAction" to false, "fetchData" to fetchStatusOptions), _uO("key" to "is_featured", "label" to "精选商品", "type" to "switch"), _uO("key" to "is_new", "label" to "新品", "type" to "switch"), _uO("key" to "is_bestseller", "label" to "热销商品", "type" to "switch"), _uO("key" to "sort_order", "label" to "排序", "type" to "number", "placeholder" to "数字越小越靠前"))), _uO("key" to "media", "title" to "商品图片", "description" to "可同时上传多张图片", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "images", "label" to "商品图片", "type" to "upload", "action" to "", "name" to "files", "max" to 9, "uploadText" to "上传图片", "fileItemsKey" to "imageItems", "headers" to buildUploadHeaders(), "formData" to _uO())))))
            val pageTitle = computed(fun(): String {
                if (formMode.value == "create" && copySourceId.value != "") {
                    return "复制商品"
                }
                return if (formMode.value == "edit") {
                    "编辑商品"
                } else {
                    "新建商品"
                }
            }
            )
            fun gen_markProductListRefreshNeeded_fn(): Unit {
                uni_setStorageSync(productListRefreshStorageKey, "1")
            }
            val markProductListRefreshNeeded = ::gen_markProductListRefreshNeeded_fn
            fun gen_loadProductDiscountCards_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (productId.value == "") {
                            discountCards.value = _uA<UTSJSONObject>()
                            return@w1
                        }
                        if (discountCardsLoading.value) {
                            return@w1
                        }
                        discountCardsLoading.value = true
                        try {
                            val response = await(getProductConfigList(productDiscountsPath(), null, 1, 100, _uO("product" to productId.value)))
                            val rawResults = response["results"]
                            if (rawResults == null) {
                                discountCards.value = _uA<UTSJSONObject>()
                            } else {
                                discountCards.value = rawResults as UTSArray<UTSJSONObject>
                            }
                        }
                         catch (error: Throwable) {
                            discountCards.value = _uA<UTSJSONObject>()
                            showErrorToast(parseErrorMessage(error, "折扣规则加载失败"))
                        }
                         finally {
                            discountCardsLoading.value = false
                        }
                })
            }
            val loadProductDiscountCards = ::gen_loadProductDiscountCards_fn
            fun gen_loadProductDetailData_fn(idText: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (idText == "") {
                            return@w1
                        }
                        try {
                            val detail = await(getProductDetail(idText))
                            var categoryText = ""
                            val categoryId = extractCategoryIdFromProduct(detail)
                            if (categoryId != "") {
                                categoryText = await(resolveCategoryOptionText(categoryId))
                            }
                            initialData.value = buildInitialDataFromProduct(detail, categoryText)
                            await(loadProductDiscountCards())
                        }
                         catch (error: Throwable) {
                            showErrorToast(parseErrorMessage(error, "商品详情加载失败"))
                        }
                })
            }
            val loadProductDetailData = ::gen_loadProductDetailData_fn
            fun gen_loadCopiedProductData_fn(idText: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (idText == "") {
                            return@w1
                        }
                        var loaded = false
                        try {
                            uni_showLoading(ShowLoadingOptions(title = "复制中", mask = true))
                            val detail = await(getProductDetail(idText))
                            var categoryText = ""
                            val categoryId = extractCategoryIdFromProduct(detail)
                            if (categoryId != "") {
                                categoryText = await(resolveCategoryOptionText(categoryId))
                            }
                            initialData.value = buildCopiedInitialDataFromProduct(detail, categoryText)
                            discountCards.value = _uA<UTSJSONObject>()
                            loaded = true
                        }
                         catch (error: Throwable) {
                            showErrorToast(parseErrorMessage(error, "复制商品加载失败"))
                        }
                         finally {
                            uni_hideLoading(null)
                        }
                        if (loaded) {
                            uni_showToast(ShowToastOptions(title = "已填入商品信息", icon = "success"))
                        }
                })
            }
            val loadCopiedProductData = ::gen_loadCopiedProductData_fn
            fun gen_discountCardKey_fn(discount: UTSJSONObject, index: Number): String {
                val id = getStringField(discount, "id")
                if (id != "") {
                    return id
                }
                return "discount-" + index.toString(10)
            }
            val discountCardKey = ::gen_discountCardKey_fn
            fun gen_discountCardName_fn(discount: UTSJSONObject): String {
                return getStringField(discount, "name", getStringField(discount, "discount_name", "未命名折扣"))
            }
            val discountCardName = ::gen_discountCardName_fn
            fun gen_discountRuleText_fn(discount: UTSJSONObject): String {
                val display = getStringField(discount, "discount_display")
                if (display != "") {
                    return display
                }
                val discountType = getStringField(discount, "discount_type")
                val percentValue = getStringField(discount, "discount_percentage")
                if (discountType == "PERCENTAGE" && percentValue != "") {
                    return percentValue + "% 折扣"
                }
                val amount = getStringField(discount, "discount_amount", getStringField(discount, "discount_amount_fixed"))
                if (amount != "") {
                    return "减 " + amount
                }
                return "未设置规则"
            }
            val discountRuleText = ::gen_discountRuleText_fn
            fun gen_discountTypeText_fn(discount: UTSJSONObject): String {
                val discountType = getStringField(discount, "discount_type")
                if (discountType == "PERCENTAGE") {
                    return "百分比"
                }
                if (discountType == "FIXED_AMOUNT" || discountType == "FIXED") {
                    return "固定金额"
                }
                return if (discountType == "") {
                    "-"
                } else {
                    discountType
                }
            }
            val discountTypeText = ::gen_discountTypeText_fn
            fun gen_discountFinalPriceText_fn(discount: UTSJSONObject): String {
                val directValue = getStringField(discount, "final_price")
                if (directValue != "") {
                    return formatMoneyText(directValue, directValue)
                }
                return calculateDiscountedPriceText(getStringField(initialData.value, "base_sales_price", "0.00"), discount)
            }
            val discountFinalPriceText = ::gen_discountFinalPriceText_fn
            fun gen_upsertDiscountCardFromSelection_fn(selected: UTSJSONObject): Unit {
                val discountId = getStringField(selected, "discount_id")
                if (discountId == "") {
                    return
                }
                val nextCard: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("nextCard", "pages/products/from.uvue", 1133, 8), "id" to discountId, "name" to getStringField(selected, "discount_name", getStringField(selected, "name", "未命名折扣")), "discount_type" to getStringField(selected, "discount_type"), "discount_percentage" to getStringField(selected, "discount_percentage"), "discount_amount" to getStringField(selected, "discount_amount", getStringField(selected, "discount_amount_fixed")), "discount_amount_fixed" to getStringField(selected, "discount_amount_fixed"), "min_quantity" to getStringField(selected, "min_quantity", "1"), "priority" to "-", "final_price" to getStringField(selected, "final_price"))
                val nextCards: UTSArray<UTSJSONObject> = _uA()
                var replaced = false
                run {
                    var index: Number = 0
                    while(index < discountCards.value.length){
                        val currentCard = discountCards.value[index]
                        if (getStringField(currentCard, "id") == discountId) {
                            nextCards.push(nextCard)
                            replaced = true
                        } else {
                            nextCards.push(currentCard)
                        }
                        index += 1
                    }
                }
                if (!replaced) {
                    nextCards.unshift(nextCard)
                }
                discountCards.value = nextCards
            }
            val upsertDiscountCardFromSelection = ::gen_upsertDiscountCardFromSelection_fn
            fun gen_removeDiscountFromProduct_fn(discountId: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (productId.value == "" || discountId == "") {
                            return@w1
                        }
                        try {
                            uni_showLoading(ShowLoadingOptions(title = "删除中", mask = true))
                            await(removeProductDiscountFromProduct(productId.value, discountId))
                            await(loadProductDetailData(productId.value))
                            uni_showToast(ShowToastOptions(title = "折扣已删除", icon = "success"))
                        }
                         catch (error: Throwable) {
                            showErrorToast(parseErrorMessage(error, "折扣删除失败"))
                        }
                         finally {
                            uni_hideLoading(null)
                        }
                })
            }
            val removeDiscountFromProduct = ::gen_removeDiscountFromProduct_fn
            fun gen_confirmRemoveDiscount_fn(discount: UTSJSONObject): Unit {
                val discountId = getStringField(discount, "id")
                if (discountId == "") {
                    return
                }
                uni_showModal(ShowModalOptions(title = "删除折扣", content = "确定移除这个折扣规则吗？", success = fun(res){
                    if (res.confirm) {
                        removeDiscountFromProduct(discountId)
                    }
                }
                ))
            }
            val confirmRemoveDiscount = ::gen_confirmRemoveDiscount_fn
            fun gen_buildDiscountSelectionStorageKey_fn(): String {
                return productDiscountSelectionStorageKey + productId.value
            }
            val buildDiscountSelectionStorageKey = ::gen_buildDiscountSelectionStorageKey_fn
            fun gen_readStorageText_fn(key: String): String {
                val rawValue = uni_getStorageSync(key)
                if (rawValue == null) {
                    return ""
                }
                val text = "" + rawValue
                return if (text == null) {
                    ""
                } else {
                    text
                }
            }
            val readStorageText = ::gen_readStorageText_fn
            fun gen_parseStoredJson_fn(value: Any): UTSJSONObject? {
                val rawText = "" + value
                if (rawText == "") {
                    return null
                }
                val parsed = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(rawText), " at pages/products/from.uvue:1217")
                if (parsed != null) {
                    return parsed
                }
                return null
            }
            val parseStoredJson = ::gen_parseStoredJson_fn
            fun gen_cloneInitialData_fn(): UTSJSONObject {
                val source = initialData.value
                val result: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("result", "pages/products/from.uvue", 1226, 8))
                for(key in resolveUTSKeyIterator(source)){
                    result[key] = source[key]
                }
                return result
            }
            val cloneInitialData = ::gen_cloneInitialData_fn
            fun gen_applySelectedDiscount_fn() {
                if (productId.value == "") {
                    return
                }
                val selectedStorageText = readStorageText(buildDiscountSelectionStorageKey())
                if (selectedStorageText == "") {
                    return
                }
                val selected = parseStoredJson(selectedStorageText)
                if (selected == null) {
                    return
                }
                val selectedProductId = getStringField(selected, "product_id")
                if (selectedProductId != "" && selectedProductId != productId.value) {
                    return
                }
                val nextData = cloneInitialData()
                val currentBaseSalesPrice = getStringField(nextData, "base_sales_price", "0.00")
                val baseSalesPriceFromSelection = getStringField(selected, "original_price", currentBaseSalesPrice)
                val discountPriceFromSelection = getStringField(selected, "final_price")
                var finalPrice = ""
                if (discountPriceFromSelection == "") {
                    finalPrice = calculateDiscountedPriceText(baseSalesPriceFromSelection, selected)
                } else {
                    finalPrice = discountPriceFromSelection
                }
                nextData["discount_rule"] = getStringField(selected, "discount_name", getStringField(selected, "name"))
                nextData["discount_rule_id"] = getStringField(selected, "discount_id")
                nextData["discounted_base_sales_price"] = formatMoneyText(finalPrice, "")
                nextData["base_sales_price"] = baseSalesPriceFromSelection
                initialData.value = nextData
                upsertDiscountCardFromSelection(selected)
                loadProductDiscountCards()
                uni_removeStorageSync(buildDiscountSelectionStorageKey())
                uni_showToast(ShowToastOptions(title = "折扣已添加", icon = "success"))
            }
            val applySelectedDiscount = ::gen_applySelectedDiscount_fn
            fun gen_openDiscountSelector_fn() {
                val safeProductId = "" + productId.value
                if (safeProductId == "") {
                    uni_showToast(ShowToastOptions(title = "请先保存商品后再选择折扣", icon = "none", duration = 3500))
                    return
                }
                var baseSalesPrice = getStringField(initialData.value, "base_sales_price", "0.00")
                if (baseSalesPrice == "") {
                    baseSalesPrice = "0.00"
                }
                uni_navigateTo(NavigateToOptions(url = "/pages/products/config-model/index?resource=discount&mode=select&product_id=" + safeProductId + "&base_sales_price=" + UTSAndroid.consoleDebugError(encodeURIComponent(baseSalesPrice), " at pages/products/from.uvue:1287")))
            }
            val openDiscountSelector = ::gen_openDiscountSelector_fn
            fun gen_handleInputAdd_fn(payload: UTSJSONObject) {
                val keyValue = getStringField(payload, "key")
                if (keyValue == "discount_rule") {
                    openDiscountSelector()
                    return
                }
            }
            val handleInputAdd = ::gen_handleInputAdd_fn
            fun goBackToList(markLeaving: Boolean = true): Unit {
                if (markLeaving) {
                    pageTaskGuard.leave()
                    savingVisible.value = false
                    uni_hideLoading(null)
                }
                leaveSignal.value = leaveSignal.value + 1
                setTimeout(fun(){
                    uni_navigateBack(NavigateBackOptions(delta = 1, fail = fun(_){
                        uni_switchTab(SwitchTabOptions(url = "/pages/tabbar/products"))
                    }
                    ))
                }
                , 16)
            }
            fun gen_buildProductMutationPayload_fn(formDataObject: UTSJSONObject): ProductMutationData {
                val sku = getStringField(formDataObject, "sku").trim()
                val barcode = getStringField(formDataObject, "barcode").trim()
                val nameCn = getStringField(formDataObject, "name_cn").trim()
                val nameEn = getStringField(formDataObject, "name_en").trim()
                val nameOther = getStringField(formDataObject, "name_other").trim()
                val description = getStringField(formDataObject, "description").trim()
                val categoryId = getStringField(formDataObject, "category_id").trim()
                val supplierId = getStringField(formDataObject, "supplier_id").trim()
                val purchasePrice = getStringField(formDataObject, "purchase_price", "0.00").trim()
                val netPurchasePrice = getStringField(formDataObject, "net_purchase_price", "0.00").trim()
                val costPrice = getStringField(formDataObject, "cost_price", "0.00").trim()
                val baseSalesPrice = getStringField(formDataObject, "base_sales_price", "0.00").trim()
                val status = getStringField(formDataObject, "status", "ACTIVE").trim()
                return ProductMutationData(sku = if (sku == "") {
                    null
                } else {
                    sku
                }
                , barcode = if (barcode == "") {
                    null
                } else {
                    barcode
                }
                , name_cn = nameCn, name_en = if (nameEn == "") {
                    null
                } else {
                    nameEn
                }
                , name_other = if (nameOther == "") {
                    null
                } else {
                    nameOther
                }
                , description = if (description == "") {
                    null
                } else {
                    description
                }
                , category = if (categoryId == "") {
                    null
                } else {
                    categoryId
                }
                , supplier = if (supplierId == "") {
                    null
                } else {
                    supplierId
                }
                , purchase_price = if (purchasePrice == "") {
                    "0.00"
                } else {
                    purchasePrice
                }
                , net_purchase_price = if (netPurchasePrice == "") {
                    "0.00"
                } else {
                    netPurchasePrice
                }
                , cost_price = if (costPrice == "") {
                    "0.00"
                } else {
                    costPrice
                }
                , base_sales_price = if (baseSalesPrice == "") {
                    "0.00"
                } else {
                    baseSalesPrice
                }
                , status = if (status == "") {
                    "ACTIVE"
                } else {
                    status
                }
                , is_featured = booleanValue(formDataObject["is_featured"]), is_new = booleanValue(formDataObject["is_new"]), is_bestseller = booleanValue(formDataObject["is_bestseller"]), sort_order = intValue(formDataObject["sort_order"], 0))
            }
            val buildProductMutationPayload = ::gen_buildProductMutationPayload_fn
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
            fun gen_uploadPendingProductImages_fn(formDataObject: UTSJSONObject, contentTypeModel: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (productId.value == "") {
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
                                uploadItems.push(MediaBatchUploadItem(filePath = pendingImagePaths[index], name = "files", formData = _uO("content_type_model" to contentTypeModel, "object_id" to productId.value)))
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
            val uploadPendingProductImages = ::gen_uploadPendingProductImages_fn
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
                        val nameCn = getStringField(data, "name_cn").trim()
                        if (nameCn == "") {
                            uni_showToast(ShowToastOptions(title = "中文名称不能为空", icon = "none", duration = 3500))
                            return@w1
                        }
                        val taskToken = pageTaskGuard.begin()
                        submitting.value = true
                        val isEditing = formMode.value == "edit" && productId.value != ""
                        val uploadContentTypeModel = getStringField(payload, "uploadContentTypeModel").trim()
                        savingText.value = if (isEditing) {
                            "保存商品中..."
                        } else {
                            "创建商品中..."
                        }
                        savingVisible.value = true
                        uni_showLoading(ShowLoadingOptions(title = savingText.value, mask = true))
                        try {
                            val body = buildProductMutationPayload(data)
                            var successMessage = if (isEditing) {
                                "商品保存成功"
                            } else {
                                "商品创建成功"
                            }
                            if (isEditing) {
                                val updated = await(updateProduct(productId.value, body))
                                successMessage = takeLatestResponseMessage(successMessage)
                                productId.value = updated.id.toString(10)
                                savingText.value = "上传图片中..."
                                await(uploadPendingProductImages(data, uploadContentTypeModel))
                            } else {
                                val created = await(createProduct(body))
                                successMessage = takeLatestResponseMessage(successMessage)
                                productId.value = created.id.toString(10)
                                formMode.value = "edit"
                                try {
                                    savingText.value = "上传图片中..."
                                    await(uploadPendingProductImages(data, uploadContentTypeModel))
                                }
                                 catch (uploadError: Throwable) {
                                    throw UTSError("商品已创建，但图片上传失败")
                                }
                            }
                            markProductListRefreshNeeded()
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
                            showErrorToast(parseErrorMessage(error, if (isEditing) {
                                "商品保存失败"
                            } else {
                                "商品创建失败"
                            }
                            ))
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
            fun gen_handleFieldChange_fn(payload: UTSJSONObject): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        val keyValue = getStringField(payload, "key")
                        if (keyValue != "category_id" && keyValue != "purchase_price" && keyValue != "net_purchase_price") {
                            return@w1
                        }
                        val formDataValue = payload["formData"]
                        if (formDataValue == null) {
                            return@w1
                        }
                        val formDataObject = formDataValue as UTSJSONObject
                        val categoryId = getStringField(formDataObject, "category_id").trim()
                        if (categoryId == "") {
                            return@w1
                        }
                        val taxRate = await(resolveCategoryTaxRate(categoryId))
                        if (getStringField(formDataObject, "category_id").trim() != categoryId) {
                            return@w1
                        }
                        applyPurchasePriceSync(formDataObject, taxRate, keyValue)
                })
            }
            val handleFieldChange = ::gen_handleFieldChange_fn
            fun gen_handleBottomSelectAdd_fn(payload: UTSJSONObject) {
                uni_showToast(ShowToastOptions(title = "当前字段不支持新增", icon = "none", duration = 3500))
            }
            val handleBottomSelectAdd = ::gen_handleBottomSelectAdd_fn
            fun gen_handleBottomSelectEdit_fn(payload: UTSJSONObject) {
                uni_showToast(ShowToastOptions(title = "当前字段不支持编辑", icon = "none", duration = 3500))
            }
            val handleBottomSelectEdit = ::gen_handleBottomSelectEdit_fn
            fun gen_handleUpload_fn(payload: UTSJSONObject) {
                uni_showToast(ShowToastOptions(title = "图片已加入待保存列表", icon = "none", duration = 3500))
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
                        uni_showToast(ShowToastOptions(title = message, icon = "none", duration = 3500))
                        return
                    }
                }
                uni_showToast(ShowToastOptions(title = "图片上传失败", icon = "none", duration = 3500))
            }
            val handleUploadError = ::gen_handleUploadError_fn
            fun gen_openPriceCalculator_fn() {
                if (productId.value == "") {
                    uni_showToast(ShowToastOptions(title = "请先保存商品后再计算", icon = "none", duration = 3500))
                    return
                }
                uni_navigateTo(NavigateToOptions(url = "/pages/products/price-calculator?id=" + productId.value))
            }
            val openPriceCalculator = ::gen_openPriceCalculator_fn
            fun gen_applyCalculatedPrice_fn() {
                if (productId.value == "") {
                    return
                }
                val storageKey = "calc_result:" + productId.value
                val calculatedPrice = readStorageText(storageKey)
                if (calculatedPrice == "") {
                    return
                }
                uni_removeStorageSync(storageKey)
                val nextBaseSalesPrice = calculatedPrice.trim()
                productFormRef.value?.`$callMethod`("setFieldValue", "base_sales_price", if (nextBaseSalesPrice == "") {
                    "0.00"
                } else {
                    nextBaseSalesPrice
                }
                )
                uni_showToast(ShowToastOptions(title = "基础售价已填入计算结果", icon = "success"))
            }
            val applyCalculatedPrice = ::gen_applyCalculatedPrice_fn
            onLoad(fun(event: OnLoadOptions){
                pageTaskGuard.reset()
                leaveSignal.value = 0
                val idValue = event["id"]
                val copyValue = event["copy_id"]
                productId.value = if (idValue == null) {
                    ""
                } else {
                    (idValue as String)
                }
                copySourceId.value = if (copyValue == null) {
                    ""
                } else {
                    (copyValue as String)
                }
                formMode.value = if (productId.value == "") {
                    "create"
                } else {
                    "edit"
                }
                if (formMode.value == "edit") {
                    loadProductDetailData(productId.value)
                } else if (copySourceId.value != "") {
                    loadCopiedProductData(copySourceId.value)
                }
            }
            )
            onUnload(fun(){
                pageTaskGuard.leave()
                uni_hideLoading(null)
            }
            )
            onShow(fun(){
                applyCalculatedPrice()
                applySelectedDiscount()
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversaForm = resolveEasyComponent("lili-UniversaForm", GenUniModulesLiliUniversaFormComponentsLiliUniversaFormLiliUniversaFormClass)
                val _component_lili_print_confirm_popup = resolveEasyComponent("lili-print-confirm-popup", GenUniModulesLiliPrintConfirmPopupComponentsLiliPrintConfirmPopupLiliPrintConfirmPopupClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to pageTitle.value, "showBack" to true, "showSearch" to false, "showHome" to true, "showRightText" to (unref(formMode) == "edit" && unref(productId) != ""), "rightText" to "打印", "homePath" to "/pages/tabbar/products", "backgroundColor" to "#EEF2F7", "onRight" to openProductPrintPage), null, 8, _uA(
                        "title",
                        "showRightText"
                    )),
                    _cE("view", _uM("class" to "page-content"), _uA(
                        _cV(_component_lili_UniversaForm, _uM("ref_key" to "productFormRef", "ref" to productFormRef, "mode" to unref(formMode), "formSections" to unref(formSections), "initialData" to unref(initialData), "leaveSignal" to unref(leaveSignal), "dirtySignal" to unref(dirtySignal), "uploadContentTypeModel" to "product", "onSubmit" to handleSubmit, "onCancel" to handleCancel, "onDiscardLeave" to handleDiscardLeave, "onSaveRequest" to handleSaveRequest, "onFieldChange" to handleFieldChange, "onDirtyChange" to handleDirtyChange, "onBottomSelectAdd" to handleBottomSelectAdd, "onBottomSelectEdit" to handleBottomSelectEdit, "onInputAdd" to handleInputAdd, "onUpload" to handleUpload, "onUploadDelete" to handleUploadDelete, "onUploadError" to handleUploadError), _uM("field-discount_rule" to withScopedSlotCtx(fun(slotProps: Record<String, Any?>): UTSArray<Any> {
                            val value = slotProps["value"]
                            return _uA(
                                _cE("view", _uM("class" to "discount-card-section"), _uA(
                                    if (isTrue(unref(discountCards).length == 0 && !unref(discountCardsLoading))) {
                                        _cE("view", _uM("key" to 0, "class" to "discount-empty-card"), _uA(
                                            _cE("text", _uM("class" to "discount-empty-title"), "暂无折扣规则"),
                                            _cE("text", _uM("class" to "discount-empty-desc"), "可以为当前商品添加一个或多个折扣规则")
                                        ))
                                    } else {
                                        _cC("v-if", true)
                                    }
                                    ,
                                    _cE(Fragment, null, RenderHelpers.renderList(unref(discountCards), fun(discount, discountIndex, __index, _cached): Any {
                                        return _cE("view", _uM("key" to discountCardKey(discount, discountIndex), "class" to "discount-card"), _uA(
                                            _cE("view", _uM("class" to "discount-card-head"), _uA(
                                                _cE("view", _uM("class" to "discount-card-title-wrap"), _uA(
                                                    _cE("text", _uM("class" to "discount-card-title"), _tD(discountCardName(discount)), 1),
                                                    _cE("text", _uM("class" to "discount-card-rule"), _tD(discountRuleText(discount)), 1)
                                                )),
                                                _cE("view", _uM("class" to "discount-delete-btn", "onClick" to withModifiers(fun(){
                                                    confirmRemoveDiscount(discount)
                                                }
                                                , _uA(
                                                    "stop"
                                                ))), _uA(
                                                    _cE("text", _uM("class" to "discount-delete-text"), "删除")
                                                ), 8, _uA(
                                                    "onClick"
                                                ))
                                            )),
                                            _cE("view", _uM("class" to "discount-card-grid"), _uA(
                                                _cE("view", _uM("class" to "discount-card-cell"), _uA(
                                                    _cE("text", _uM("class" to "discount-card-label"), "折后价"),
                                                    _cE("text", _uM("class" to "discount-card-value discount-card-price"), _tD(discountFinalPriceText(discount)), 1)
                                                )),
                                                _cE("view", _uM("class" to "discount-card-cell"), _uA(
                                                    _cE("text", _uM("class" to "discount-card-label"), "类型"),
                                                    _cE("text", _uM("class" to "discount-card-value"), _tD(discountTypeText(discount)), 1)
                                                )),
                                                _cE("view", _uM("class" to "discount-card-cell"), _uA(
                                                    _cE("text", _uM("class" to "discount-card-label"), "最低数量"),
                                                    _cE("text", _uM("class" to "discount-card-value"), _tD(getStringField(discount, "min_quantity", "1")), 1)
                                                )),
                                                _cE("view", _uM("class" to "discount-card-cell"), _uA(
                                                    _cE("text", _uM("class" to "discount-card-label"), "优先级"),
                                                    _cE("text", _uM("class" to "discount-card-value"), _tD(getStringField(discount, "priority", "-")), 1)
                                                ))
                                            ))
                                        ))
                                    }
                                    ), 128),
                                    if (isTrue(unref(discountCardsLoading))) {
                                        _cE("view", _uM("key" to 1, "class" to "discount-loading-card"), _uA(
                                            _cE("text", _uM("class" to "discount-loading-text"), "正在加载折扣规则...")
                                        ))
                                    } else {
                                        _cC("v-if", true)
                                    }
                                    ,
                                    if (isTrue(value != "" && value != "0.00")) {
                                        _cE("text", _uM("key" to 2, "class" to "discount-active-price"), " 当前折后售价：" + _tD(value), 1)
                                    } else {
                                        _cC("v-if", true)
                                    }
                                    ,
                                    _cE("view", _uM("class" to "discount-action-btn", "onClick" to openDiscountSelector), _uA(
                                        _cE("text", _uM("class" to "discount-action-btn-text"), "添加折扣")
                                    ))
                                ))
                            )
                        }
                        ), "_" to 1), 8, _uA(
                            "mode",
                            "formSections",
                            "initialData",
                            "leaveSignal",
                            "dirtySignal"
                        ))
                    )),
                    if (isTrue(unref(formMode) == "edit" && unref(productId) != "")) {
                        _cE("view", _uM("key" to 0, "class" to "float-btn", "onClick" to openPriceCalculator), _uA(
                            _cE("text", _uM("class" to "float-btn-text"), "计算价格")
                        ))
                    } else {
                        _cC("v-if", true)
                    }
                    ,
                    if (isTrue(unref(savingVisible))) {
                        _cE("view", _uM("key" to 1, "class" to "page-saving-mask"), _uA(
                            _cE("view", _uM("class" to "page-saving-card"), _uA(
                                _cE("text", _uM("class" to "page-saving-text"), _tD(unref(savingText)), 1)
                            ))
                        ))
                    } else {
                        _cC("v-if", true)
                    }
                    ,
                    _cV(_component_lili_print_confirm_popup, _uM("visible" to unref(printPopupVisible), "templateType" to "product_label", "printData" to productPrintData.value, "onUpdate:visible" to handlePrintPopupVisibleChange), null, 8, _uA(
                        "visible",
                        "printData"
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "position" to "relative", "backgroundColor" to "#EEF2F7")), "page-content" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingLeft" to 0, "paddingRight" to 0, "paddingBottom" to 0)), "page-saving-mask" to _pS(_uM("position" to "absolute", "left" to 0, "top" to 0, "right" to 0, "bottom" to 0, "zIndex" to 9999, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "rgba(15,23,42,0.28)")), "page-saving-card" to _pS(_uM("height" to 44, "paddingLeft" to 16, "paddingRight" to 16, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "rgba(15,23,42,0.86)")), "page-saving-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "16px", "color" to "#FFFFFF")), "float-btn" to _pS(_uM("position" to "absolute", "right" to 14, "bottom" to 112, "height" to 32, "paddingLeft" to 12, "paddingRight" to 12, "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "alignItems" to "center", "justifyContent" to "center", "flexDirection" to "row", "backgroundColor" to "#111827", "zIndex" to 9998)), "float-btn-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "14px", "color" to "#FFFFFF", "textAlign" to "center")), "discount-card-section" to _pS(_uM("paddingTop" to 8, "paddingBottom" to 8)), "discount-empty-card" to _pS(_uM("borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "backgroundColor" to "#F8FAFC", "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "marginBottom" to 10)), "discount-loading-card" to _pS(_uM("borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "backgroundColor" to "#F8FAFC", "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "marginBottom" to 10)), "discount-empty-title" to _pS(_uM("fontSize" to 14, "lineHeight" to "18px", "color" to "#0F172A", "fontWeight" to "bold")), "discount-empty-desc" to _pS(_uM("marginTop" to 4, "fontSize" to 12, "lineHeight" to "16px", "color" to "#64748B")), "discount-loading-text" to _pS(_uM("marginTop" to 4, "fontSize" to 12, "lineHeight" to "16px", "color" to "#64748B")), "discount-card" to _pS(_uM("borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#CBD5E1", "borderRightColor" to "#CBD5E1", "borderBottomColor" to "#CBD5E1", "borderLeftColor" to "#CBD5E1", "backgroundColor" to "#FFFFFF", "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "marginBottom" to 10)), "discount-card-head" to _pS(_uM("flexDirection" to "row", "alignItems" to "flex-start", "justifyContent" to "space-between")), "discount-card-title-wrap" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingRight" to 10)), "discount-card-title" to _pS(_uM("fontSize" to 15, "lineHeight" to "20px", "color" to "#0F172A", "fontWeight" to "bold")), "discount-card-rule" to _pS(_uM("marginTop" to 3, "fontSize" to 12, "lineHeight" to "16px", "color" to "#475569")), "discount-delete-btn" to _pS(_uM("height" to 28, "borderTopLeftRadius" to 6, "borderTopRightRadius" to 6, "borderBottomRightRadius" to 6, "borderBottomLeftRadius" to 6, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FCA5A5", "borderRightColor" to "#FCA5A5", "borderBottomColor" to "#FCA5A5", "borderLeftColor" to "#FCA5A5", "backgroundColor" to "#FEF2F2", "paddingLeft" to 10, "paddingRight" to 10, "alignItems" to "center", "justifyContent" to "center")), "discount-delete-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "14px", "color" to "#B91C1C")), "discount-card-grid" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 10)), "discount-card-cell" to _pS(_uM("width" to "50%", "paddingTop" to 6, "paddingBottom" to 6)), "discount-card-label" to _pS(_uM("fontSize" to 11, "lineHeight" to "14px", "color" to "#94A3B8")), "discount-card-value" to _pS(_uM("marginTop" to 2, "fontSize" to 13, "lineHeight" to "17px", "color" to "#334155")), "discount-card-price" to _pS(_uM("color" to "#0F766E", "fontSize" to 15, "fontWeight" to "bold")), "discount-active-price" to _pS(_uM("marginBottom" to 10, "fontSize" to 13, "lineHeight" to "18px", "color" to "#0F766E")), "discount-action-btn" to _pS(_uM("alignItems" to "center", "justifyContent" to "center", "height" to 38, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#0F172A", "paddingLeft" to 12, "paddingRight" to 12)), "discount-action-btn-text" to _pS(_uM("fontSize" to 14, "lineHeight" to "16px", "color" to "#FFFFFF")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
