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
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
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
            val formMode = ref("create")
            val productId = ref("")
            val leaveSignal = ref(0)
            val submitting = ref(false)
            val savingVisible = ref(false)
            val savingText = ref("处理中...")
            val initialData = ref<UTSJSONObject>(_uO("sku" to "", "barcode" to "", "name_cn" to "", "name_en" to "", "description" to "", "category_id" to "", "category_text" to "", "supplier_id" to "", "supplier_name" to "", "purchase_price" to "0.00", "cost_price" to "0.00", "base_sales_price" to "0.00", "status" to "ACTIVE", "is_featured" to false, "is_new" to false, "is_bestseller" to false, "sort_order" to "0", "images" to _uA<String>(), "imageItems" to _uA<UTSJSONObject>()))
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
                return UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(text), " at pages/products/from.uvue:137")
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
                val parsed = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(text), " at pages/products/from.uvue:148")
                if (parsed == null) {
                    return _uA<UTSJSONObject>()
                }
                return parsed!!
            }
            val parseObjectArray = ::gen_parseObjectArray_fn
            fun gen_parseErrorMessage_fn(error: Any, fallback: String): String {
                var message = fallback
                if (error != null) {
                    val directMessage = (error as UTSError).message
                    if (directMessage != null && directMessage != "") {
                        message = directMessage
                    }
                    val errorText = JSON.stringify(error)
                    if (errorText != null && errorText != "") {
                        val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/products/from.uvue:164")
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
                val headers: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("headers", "pages/products/from.uvue", 180, 8))
                if (authState.token != "") {
                    headers["Authorization"] = authState.token
                }
                return headers
            }
            val buildUploadHeaders = ::gen_buildUploadHeaders_fn
            fun gen_buildSelectResponse_fn(source: UTSArray<SelectOption__6>, params: UTSJSONObject): UTSJSONObject {
                val keyword = getStringField(params, "keyword").toLowerCase()
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
                        if (keyword != "" && option.text.toLowerCase().indexOf(keyword) < 0 && option.value.toLowerCase().indexOf(keyword) < 0) {
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
            fun gen_fetchSupplierOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        val raw = await(request("/api/procurement/suppliers/options/", "GET", _uO(), true))
                        return@w1 buildSelectResponse(buildSelectOptions(raw), params)
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
                        val option: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("option", "pages/products/from.uvue", 321, 9), "value" to buildOptionValue(item), "text" to label, "label" to label, "full_name" to stringValue(item["full_name"], label), "code" to stringValue(item["code"]), "level" to intValue(item["level"], -1), "parent_value" to stringValue(item["parent_value"]), "disabled" to booleanValue(item["disabled"]), "has_children" to (booleanValue(item["has_children"]) || treeChildren.length > 0), "children" to treeChildren)
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
                        val queryParams: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("queryParams", "pages/products/from.uvue", 373, 8), "key" to "parent", "page" to intValue(pageValue, 1), "page_size" to intValue(pageSizeValue, 20))
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
                return _uO("sku" to item.sku, "barcode" to item.barcode, "name_cn" to item.name_cn, "name_en" to item.name_en, "description" to item.description, "category_id" to categoryId, "category_text" to categoryText, "supplier_id" to supplierId, "supplier_name" to item.supplier_name, "purchase_price" to if (item.purchase_price == "") {
                    "0.00"
                } else {
                    item.purchase_price
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
                , "status" to if (item.status == "") {
                    "ACTIVE"
                } else {
                    item.status
                }
                , "is_featured" to item.is_featured, "is_new" to item.is_new, "is_bestseller" to item.is_bestseller, "sort_order" to item.sort_order.toString(10), "images" to images, "imageItems" to imageItems)
            }
            val formSections = ref(_uA<UTSJSONObject>(_uO("key" to "base", "title" to "基础信息", "description" to "", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "name_cn", "label" to "中文名称", "type" to "input", "required" to true, "placeholder" to "请输入中文名称"), _uO("key" to "name_en", "label" to "外文名称", "type" to "input", "placeholder" to "请输入外文名称"), _uO("key" to "sku", "label" to "SKU", "type" to "input", "placeholder" to "请输入SKU"), _uO("key" to "barcode", "label" to "条码", "type" to "input", "showScan" to true, "placeholder" to "请输入或扫描条码"), _uO("key" to "supplier_id", "label" to "供应商", "type" to "bottomSelect", "textKey" to "supplier_name", "title" to "选择供应商", "placeholder" to "请选择供应商", "searchPlaceholder" to "请输入供应商名称", "showAddAction" to true, "showEditAction" to true, "addPath" to "/pages/suppliers/from", "editPath" to "/pages/suppliers/from", "fetchData" to fetchSupplierOptions), _uO("key" to "category_id", "label" to "商品分类", "type" to "bottomSelect", "textKey" to "category_text", "title" to "选择商品分类", "placeholder" to "请选择商品分类", "searchPlaceholder" to "请输入分类名称", "showAddAction" to true, "showEditAction" to true, "addPath" to "/pages/category/from", "editPath" to "/pages/category/from", "tree" to true, "expandOnClickNode" to true, "selectableLevel" to 2, "selectableLevelMessage" to "只能选择 level 2 分类", "fetchData" to fetchCategoryOptions), _uO("key" to "description", "label" to "描述", "type" to "textarea", "placeholder" to "请输入商品描述"))), _uO("key" to "price", "title" to "价格信息", "description" to "", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "purchase_price", "label" to "采购价", "type" to "number", "required" to true, "placeholder" to "请输入采购价"), _uO("key" to "cost_price", "label" to "成本价", "type" to "number", "required" to true, "placeholder" to "请输入成本价"), _uO("key" to "base_sales_price", "label" to "基础售价", "type" to "number", "required" to true, "placeholder" to "请输入基础售价"))), _uO("key" to "status", "title" to "状态设置", "description" to "", "defaultOpen" to false, "fields" to _uA<UTSJSONObject>(_uO("key" to "status", "label" to "商品状态", "type" to "bottomSelect", "title" to "选择商品状态", "placeholder" to "请选择商品状态", "showAddAction" to false, "showEditAction" to false, "fetchData" to fetchStatusOptions), _uO("key" to "is_featured", "label" to "精选商品", "type" to "switch"), _uO("key" to "is_new", "label" to "新品", "type" to "switch"), _uO("key" to "is_bestseller", "label" to "热销商品", "type" to "switch"), _uO("key" to "sort_order", "label" to "排序", "type" to "number", "placeholder" to "数字越小越靠前"))), _uO("key" to "media", "title" to "商品图片", "description" to "可同时上传多张图片", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "images", "label" to "商品图片", "type" to "upload", "action" to "", "name" to "files", "max" to 9, "uploadText" to "上传图片", "fileItemsKey" to "imageItems", "headers" to buildUploadHeaders(), "formData" to _uO())))))
            val pageTitle = computed(fun(): String {
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
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error, "商品详情加载失败"), icon = "none"))
                        }
                })
            }
            val loadProductDetailData = ::gen_loadProductDetailData_fn
            fun gen_goBackToList_fn(): Unit {
                leaveSignal.value = leaveSignal.value + 1
                setTimeout(fun(){
                    uni_navigateBack(NavigateBackOptions(delta = 1, fail = fun(_){
                        uni_switchTab(SwitchTabOptions(url = "/pages/tabbar/products"))
                    }
                    ))
                }
                , 16)
            }
            val goBackToList = ::gen_goBackToList_fn
            fun gen_buildProductMutationPayload_fn(formDataObject: UTSJSONObject): ProductMutationData {
                val sku = getStringField(formDataObject, "sku").trim()
                val barcode = getStringField(formDataObject, "barcode").trim()
                val nameCn = getStringField(formDataObject, "name_cn").trim()
                val nameEn = getStringField(formDataObject, "name_en").trim()
                val description = getStringField(formDataObject, "description").trim()
                val categoryId = getStringField(formDataObject, "category_id").trim()
                val supplierId = getStringField(formDataObject, "supplier_id").trim()
                val purchasePrice = getStringField(formDataObject, "purchase_price", "0.00").trim()
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
                            uni_showToast(ShowToastOptions(title = "中文名称不能为空", icon = "none"))
                            return@w1
                        }
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
                            uni_showToast(ShowToastOptions(title = successMessage, icon = "success"))
                            goBackToList()
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error, if (isEditing) {
                                "商品保存失败"
                            } else {
                                "商品创建失败"
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
            fun gen_handleUpload_fn(payload: UTSJSONObject) {
                uni_showToast(ShowToastOptions(title = "图片已加入待保存列表", icon = "none"))
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
                        uni_showToast(ShowToastOptions(title = message, icon = "none"))
                        return
                    }
                }
                uni_showToast(ShowToastOptions(title = "图片上传失败", icon = "none"))
            }
            val handleUploadError = ::gen_handleUploadError_fn
            onLoad(fun(event: OnLoadOptions){
                leaveSignal.value = 0
                val idValue = event["id"]
                productId.value = if (idValue == null) {
                    ""
                } else {
                    (idValue as String)
                }
                formMode.value = if (productId.value == "") {
                    "create"
                } else {
                    "edit"
                }
                if (formMode.value == "edit") {
                    loadProductDetailData(productId.value)
                }
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversaForm = resolveEasyComponent("lili-UniversaForm", GenUniModulesLiliUniversaFormComponentsLiliUniversaFormLiliUniversaFormClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to pageTitle.value, "showBack" to true, "showSearch" to false, "showHome" to true, "homePath" to "/pages/tabbar/products", "backgroundColor" to "#EEF2F7"), null, 8, _uA(
                        "title"
                    )),
                    _cE("view", _uM("class" to "page-content"), _uA(
                        _cV(_component_lili_UniversaForm, _uM("mode" to unref(formMode), "formSections" to unref(formSections), "initialData" to unref(initialData), "leaveSignal" to unref(leaveSignal), "uploadContentTypeModel" to "product", "onSubmit" to handleSubmit, "onCancel" to handleCancel, "onDiscardLeave" to handleDiscardLeave, "onSaveRequest" to handleSaveRequest, "onDirtyChange" to handleDirtyChange, "onBottomSelectAdd" to handleBottomSelectAdd, "onBottomSelectEdit" to handleBottomSelectEdit, "onUpload" to handleUpload, "onUploadDelete" to handleUploadDelete, "onUploadError" to handleUploadError), null, 8, _uA(
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "position" to "relative", "backgroundColor" to "#EEF2F7")), "page-content" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingLeft" to 0, "paddingRight" to 0, "paddingBottom" to 0)), "page-saving-mask" to _pS(_uM("position" to "absolute", "left" to 0, "top" to 0, "right" to 0, "bottom" to 0, "zIndex" to 9999, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "rgba(15,23,42,0.28)")), "page-saving-card" to _pS(_uM("height" to 44, "paddingLeft" to 16, "paddingRight" to 16, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "rgba(15,23,42,0.86)")), "page-saving-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "16px", "color" to "#FFFFFF")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
