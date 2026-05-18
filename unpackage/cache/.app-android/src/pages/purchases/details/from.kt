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
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesPurchasesDetailsFrom : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesPurchasesDetailsFrom) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesPurchasesDetailsFrom
            val _cache = __ins.renderCache
            val refreshStorageKey = "refresh:pages:purchases:details:index"
            val purchaseListRefreshStorageKey = "refresh:pages:purchases:index"
            val formMode = ref("create")
            val purchaseId = ref("")
            val detailId = ref("")
            val leaveSignal = ref(0)
            val dirtySignal = ref(0)
            val submitting = ref(false)
            val initialData = ref<UTSJSONObject>(_uO())
            val currentDetail = ref<PurchaseDetailItem?>(null)
            val printProduct = ref<ProductItem?>(null)
            val printProductId = ref("")
            val printPopupVisible = ref(false)
            val selectedPrintTemplate = ref<PrintTemplateItem?>(null)
            val printing = ref(false)
            val printCopiesText = ref("1")
            val quickPrintLoading = ref(false)
            val productCategoryInfo = ref<ProductCategoryQuickInfo>(ProductCategoryQuickInfo(id = "", name = "", kasaCode = "", kasaText = ""))
            val productInfoLoading = ref(false)
            val productInfoError = ref("")
            var productInfoRequestSeq: Number = 0
            fun getStringField(obj: UTSJSONObject, key: String, fallback: String = ""): String {
                val value = obj[key]
                if (value == null) {
                    return fallback
                }
                return "" + value
            }
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
                if (error == null) {
                    return fallback
                }
                val errorText = JSON.stringify(error)
                if (errorText == null || errorText == "") {
                    return fallback
                }
                val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/purchases/details/from.uvue:174")
                if (parsedError == null) {
                    return errorText
                }
                val rawMessage = parsedError["message"]
                if (rawMessage == null) {
                    return errorText
                }
                val message = "" + rawMessage
                return if (message == "") {
                    fallback
                } else {
                    message
                }
            }
            val parseErrorMessage = ::gen_parseErrorMessage_fn
            fun gen_parseObject_fn(value: Any?): UTSJSONObject? {
                if (value == null) {
                    return null
                }
                if (UTSAndroid.`typeof`(value) != "object") {
                    return null
                }
                val text = JSON.stringify(value)
                if (text == null || text == "") {
                    return null
                }
                if (text.indexOf("{") != 0) {
                    return null
                }
                return UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(text), " at pages/purchases/details/from.uvue:188")
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
                val parsed = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(text), " at pages/purchases/details/from.uvue:195")
                if (parsed == null) {
                    return _uA<UTSJSONObject>()
                }
                return parsed!!
            }
            val parseObjectArray = ::gen_parseObjectArray_fn
            fun gen_intValue_fn(value: Any?): Number {
                if (value == null) {
                    return 0
                }
                val parsed = parseInt("" + value)
                if (isNaN(parsed)) {
                    return 0
                }
                return parsed
            }
            val intValue = ::gen_intValue_fn
            fun gen_firstStringField_fn(obj: UTSJSONObject, keys: UTSArray<String>): String {
                run {
                    var i: Number = 0
                    while(i < keys.length){
                        val text = getStringField(obj, keys[i])
                        if (text != "") {
                            return text
                        }
                        i++
                    }
                }
                return ""
            }
            val firstStringField = ::gen_firstStringField_fn
            fun gen_numberValue_fn(value: Any?, fallback: Number): Number {
                if (value == null) {
                    return fallback
                }
                val parsed = parseFloat("" + value)
                if (isNaN(parsed)) {
                    return fallback
                }
                return parsed
            }
            val numberValue = ::gen_numberValue_fn
            fun gen_boolValue_fn(value: Any?, fallback: Boolean): Boolean {
                if (value == null) {
                    return fallback
                }
                val text = ("" + value).toLowerCase()
                if (text == "true" || text == "1" || text == "yes") {
                    return true
                }
                if (text == "false" || text == "0" || text == "no") {
                    return false
                }
                return fallback
            }
            val boolValue = ::gen_boolValue_fn
            fun gen_extractRows_fn(raw: Any?): UTSArray<UTSJSONObject> {
                val rawObject = parseObject(raw)
                if (rawObject == null) {
                    return parseObjectArray(raw)
                }
                val results = parseObjectArray(rawObject["results"])
                if (results.length > 0) {
                    return results
                }
                val dataRows = parseObjectArray(rawObject["data"])
                if (dataRows.length > 0) {
                    return dataRows
                }
                val dataObject = parseObject(rawObject["data"])
                if (dataObject != null) {
                    val nestedResults = parseObjectArray(dataObject!!["results"])
                    if (nestedResults.length > 0) {
                        return nestedResults
                    }
                }
                return parseObjectArray(raw)
            }
            val extractRows = ::gen_extractRows_fn
            fun gen_productImageFromRow_fn(row: UTSJSONObject): String {
                val direct = firstStringField(row, _uA(
                    "image",
                    "product_image",
                    "cover_image"
                ))
                if (direct != "") {
                    return direct
                }
                val mediaFiles = parseObjectArray(row["media_files"])
                run {
                    var i: Number = 0
                    while(i < mediaFiles.length){
                        val image = firstStringField(mediaFiles[i], _uA(
                            "signed_thumbnail_url",
                            "signed_download_url",
                            "file_url",
                            "url",
                            "image"
                        ))
                        if (image != "") {
                            return image
                        }
                        i++
                    }
                }
                val productMediaFiles = parseObjectArray(row["product_media_files"])
                run {
                    var j: Number = 0
                    while(j < productMediaFiles.length){
                        val image = firstStringField(productMediaFiles[j], _uA(
                            "signed_thumbnail_url",
                            "signed_download_url",
                            "file_url",
                            "url",
                            "image"
                        ))
                        if (image != "") {
                            return image
                        }
                        j++
                    }
                }
                return ""
            }
            val productImageFromRow = ::gen_productImageFromRow_fn
            fun gen_buildSelectResponse_fn(source: UTSArray<ProductOption>, params: UTSJSONObject): UTSJSONObject {
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
                        result.push(_uO("value" to option.value, "text" to option.text, "image" to option.image, "subtitle" to option.subtitle))
                        index += 1
                    }
                }
                return _uO("data" to result, "results" to result, "total" to result.length, "total_count" to result.length)
            }
            val buildSelectResponse = ::gen_buildSelectResponse_fn
            fun gen_fetchProductOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        val keyword = getStringField(params, "keyword")
                        val id = getStringField(params, "id")
                        val query: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("query", "pages/purchases/details/from.uvue", 275, 8), "page" to if (intValue(params["page"]) <= 0) {
                            1
                        } else {
                            intValue(params["page"])
                        }
                        , "page_size" to if (intValue(params["pageSize"]) <= 0) {
                            20
                        } else {
                            intValue(params["pageSize"])
                        }
                        )
                        if (keyword != "") {
                            query["search"] = keyword
                            query["keyword"] = keyword
                        }
                        if (id != "") {
                            query["id"] = id
                        }
                        val raw = await(request("/api/products/products/", "GET", query, true))
                        val rows = extractRows(raw)
                        val options: UTSArray<ProductOption> = _uA()
                        run {
                            var i: Number = 0
                            while(i < rows.length){
                                val row = rows[i]
                                val value = getStringField(row, "id")
                                var text = getStringField(row, "name_cn")
                                val barcode = getStringField(row, "barcode")
                                if (barcode != "") {
                                    text = text + " / " + barcode
                                }
                                val subtitle = firstStringField(row, _uA(
                                    "sku",
                                    "barcode"
                                ))
                                options.push(ProductOption(value = value, text = text, image = productImageFromRow(row), subtitle = subtitle))
                                i++
                            }
                        }
                        return@w1 buildSelectResponse(options, _uO("keyword" to keyword, "id" to id))
                })
            }
            val fetchProductOptions = ::gen_fetchProductOptions_fn
            fun gen_categoryDisplayName_fn(row: UTSJSONObject): String {
                var text = getStringField(row, "text")
                if (text == "") {
                    text = getStringField(row, "label")
                }
                if (text == "") {
                    text = getStringField(row, "full_name")
                }
                if (text == "") {
                    text = getStringField(row, "path")
                }
                if (text == "") {
                    text = getStringField(row, "name")
                }
                if (text == "") {
                    text = getStringField(row, "name_cn")
                }
                if (text == "") {
                    text = getStringField(row, "code")
                }
                return text
            }
            val categoryDisplayName = ::gen_categoryDisplayName_fn
            fun gen_categoryKasaCode_fn(row: UTSJSONObject): String {
                val info = parseObject(row["kasa_category_info"])
                if (info != null) {
                    val infoCode = getStringField(info!!, "unique_kod")
                    if (infoCode != "") {
                        return infoCode
                    }
                }
                var code = getStringField(row, "kasa_unique_kod")
                if (code == "") {
                    code = getStringField(row, "kasa_category_unique_kod")
                }
                if (code == "") {
                    code = getStringField(row, "unique_kod")
                }
                return code
            }
            val categoryKasaCode = ::gen_categoryKasaCode_fn
            fun gen_categoryKasaText_fn(row: UTSJSONObject): String {
                val code = categoryKasaCode(row)
                if (code != "") {
                    return "KASA " + code
                }
                val kasaId = getStringField(row, "kasa_category")
                if (kasaId != "") {
                    return "KASA #" + kasaId
                }
                return "未关联 KASA"
            }
            val categoryKasaText = ::gen_categoryKasaText_fn
            fun gen_initialCreateData_fn(): UTSJSONObject {
                return _uO("product" to "", "product_text" to "", "product_image" to "", "quantity" to "", "received_quantity" to "0", "notes" to "")
            }
            val initialCreateData = ::gen_initialCreateData_fn
            fun gen_buildInitialDataFromDetail_fn(item: PurchaseDetailItem): UTSJSONObject {
                var productText = item.product_name
                if (item.product_barcode != "") {
                    productText = productText + " / " + item.product_barcode
                }
                val itemObject = parseObject(item)
                val productImage = if (itemObject == null) {
                    ""
                } else {
                    productImageFromRow(itemObject!!)
                }
                return _uO("product" to item.product.toString(10), "product_text" to productText, "product_image" to productImage, "quantity" to item.quantity.toString(10), "received_quantity" to item.received_quantity.toString(10), "notes" to item.notes)
            }
            val buildInitialDataFromDetail = ::gen_buildInitialDataFromDetail_fn
            val formSections = ref(_uA<UTSJSONObject>(_uO("key" to "base", "title" to "明细信息", "description" to "", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "product", "textKey" to "product_text", "imageValueKey" to "product_image", "label" to "商品", "type" to "bottomSelect", "required" to true, "title" to "选择商品", "placeholder" to "请选择商品", "searchPlaceholder" to "扫码或输入商品名/条码", "imageKey" to "image", "subtitleKey" to "subtitle", "showScan" to true, "showAddAction" to true, "showEditAction" to true, "addPath" to "/pages/products/from", "editPath" to "/pages/products/from", "fetchData" to fetchProductOptions), _uO("key" to "quantity", "label" to "采购数量", "type" to "number", "required" to true, "placeholder" to "请输入采购数量"), _uO("key" to "received_quantity", "label" to "已收货数量", "type" to "number", "showStepper" to false, "placeholder" to "通常由收货操作更新", "showAdd" to true, "addText" to "全部收货", "fillFromKey" to "quantity"), _uO("key" to "notes", "label" to "备注", "type" to "textarea", "placeholder" to "请输入备注")))))
            val homePath = computed(fun(): String {
                return "/pages/purchases/details/index?purchase=" + purchaseId.value
            }
            )
            val pageTitle = computed(fun(): String {
                return if (formMode.value == "edit") {
                    "编辑采购明细"
                } else {
                    "新建采购明细"
                }
            }
            )
            val selectedTemplateName = computed(fun(): String {
                val item = selectedPrintTemplate.value
                if (item == null) {
                    return "未选择模板"
                }
                return item.name
            }
            )
            fun gen_markRefresh_fn() {
                uni_setStorageSync(refreshStorageKey + ":" + purchaseId.value, "1")
                uni_setStorageSync(purchaseListRefreshStorageKey, "1")
            }
            val markRefresh = ::gen_markRefresh_fn
            fun gen_goBackToList_fn() {
                leaveSignal.value = leaveSignal.value + 1
                setTimeout(fun(){
                    uni_navigateBack(NavigateBackOptions(delta = 1, fail = fun(_){
                        uni_navigateTo(NavigateToOptions(url = homePath.value))
                    }
                    ))
                }
                , 16)
            }
            val goBackToList = ::gen_goBackToList_fn
            fun gen_markLeaveConfirmRequired_fn() {
                setTimeout(fun(){
                    dirtySignal.value = dirtySignal.value + 1
                }
                , 50)
            }
            val markLeaveConfirmRequired = ::gen_markLeaveConfirmRequired_fn
            fun gen_categoryQuickInfoFromRow_fn(row: UTSJSONObject): ProductCategoryQuickInfo {
                val id = getStringField(row, "value", getStringField(row, "id"))
                val name = categoryDisplayName(row)
                val kasaCode = categoryKasaCode(row)
                val kasaText = categoryKasaText(row)
                return ProductCategoryQuickInfo(id = id, name = name, kasaCode = kasaCode, kasaText = kasaText)
            }
            val categoryQuickInfoFromRow = ::gen_categoryQuickInfoFromRow_fn
            fun gen_resolveProductCategoryQuickInfo_fn(product: ProductItem): UTSPromise<ProductCategoryQuickInfo> {
                return wrapUTSPromise(suspend w1@{
                        val categoryObject = parseObject(product.category)
                        if (categoryObject != null) {
                            return@w1 categoryQuickInfoFromRow(categoryObject!!)
                        }
                        val categoryId = if (product.category == null) {
                            ""
                        } else {
                            stringValue(product.category)
                        }
                        if (categoryId == "") {
                            return@w1 ProductCategoryQuickInfo(id = "", name = "", kasaCode = "", kasaText = "")
                        }
                        val raw = await(request("/api/categories/categories/" + categoryId + "/", "GET", _uO(), true))
                        val rawObject = parseObject(raw)
                        if (rawObject == null) {
                            return@w1 ProductCategoryQuickInfo(id = categoryId, name = categoryId, kasaCode = "", kasaText = "")
                        }
                        return@w1 categoryQuickInfoFromRow(rawObject!!)
                })
            }
            val resolveProductCategoryQuickInfo = ::gen_resolveProductCategoryQuickInfo_fn
            fun gen_emptyProductCategoryInfo_fn(): ProductCategoryQuickInfo {
                return ProductCategoryQuickInfo(id = "", name = "", kasaCode = "", kasaText = "")
            }
            val emptyProductCategoryInfo = ::gen_emptyProductCategoryInfo_fn
            fun resetProductInfo(productIdText: String = "") {
                printProduct.value = null
                printProductId.value = productIdText
                productCategoryInfo.value = emptyProductCategoryInfo()
                productInfoError.value = ""
            }
            fun gen_loadProductInfo_fn(productIdText: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        val resolvedProductId = productIdText.trim()
                        productInfoRequestSeq = productInfoRequestSeq + 1
                        val seq = productInfoRequestSeq
                        if (resolvedProductId == "") {
                            resetProductInfo()
                            return@w1
                        }
                        val currentProduct = printProduct.value
                        if (currentProduct == null || currentProduct.id.toString(10) != resolvedProductId) {
                            printProduct.value = null
                            productCategoryInfo.value = emptyProductCategoryInfo()
                        }
                        printProductId.value = resolvedProductId
                        productInfoLoading.value = true
                        productInfoError.value = ""
                        try {
                            val product = await(getProductDetail(resolvedProductId))
                            if (seq != productInfoRequestSeq) {
                                return@w1
                            }
                            printProduct.value = product
                            var categoryInfo = emptyProductCategoryInfo()
                            try {
                                categoryInfo = await(resolveProductCategoryQuickInfo(product))
                            }
                             catch (categoryError: Throwable) {}
                            if (seq != productInfoRequestSeq) {
                                return@w1
                            }
                            productCategoryInfo.value = categoryInfo
                        }
                         catch (error: Throwable) {
                            if (seq != productInfoRequestSeq) {
                                return@w1
                            }
                            printProduct.value = null
                            productCategoryInfo.value = emptyProductCategoryInfo()
                            productInfoError.value = parseErrorMessage(error, "商品信息加载失败")
                        }
                         finally {
                            if (seq == productInfoRequestSeq) {
                                productInfoLoading.value = false
                            }
                        }
                })
            }
            val loadProductInfo = ::gen_loadProductInfo_fn
            fun gen_buildInitialDataWithProductInfo_fn(detail: PurchaseDetailItem): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        val data = buildInitialDataFromDetail(detail)
                        await(loadProductInfo(detail.product.toString(10)))
                        return@w1 data
                })
            }
            val buildInitialDataWithProductInfo = ::gen_buildInitialDataWithProductInfo_fn
            fun gen_loadDetail_fn(idText: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (idText == "") {
                            return@w1
                        }
                        try {
                            val detail = await(getPurchaseDetailItem(idText))
                            currentDetail.value = detail
                            initialData.value = await(buildInitialDataWithProductInfo(detail))
                            markLeaveConfirmRequired()
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error, "采购明细加载失败"), icon = "none"))
                        }
                })
            }
            val loadDetail = ::gen_loadDetail_fn
            fun gen_activePrintProduct_fn(): ProductItem? {
                return printProduct.value
            }
            val activePrintProduct = ::gen_activePrintProduct_fn
            fun gen_productNameText_fn(): String {
                val product = activePrintProduct()
                if (product == null) {
                    return "-"
                }
                if (product.name_cn != "") {
                    return product.name_cn
                }
                if (product.name_en != "") {
                    return product.name_en
                }
                if (product.name_other != "") {
                    return product.name_other
                }
                return "未命名商品"
            }
            val productNameText = ::gen_productNameText_fn
            fun gen_productOriginalPriceText_fn(): String {
                val product = activePrintProduct()
                if (product == null) {
                    return "0.00"
                }
                return if (product.base_sales_price == "") {
                    "0.00"
                } else {
                    product.base_sales_price
                }
            }
            val productOriginalPriceText = ::gen_productOriginalPriceText_fn
            fun gen_productDiscountPriceText_fn(): String {
                val product = activePrintProduct()
                if (product == null) {
                    return "0.00"
                }
                val discount = product.discount_info
                if (discount != null && discount.has_discount && discount.final_price != "") {
                    return discount.final_price
                }
                return if (product.base_sales_price == "") {
                    "0.00"
                } else {
                    product.base_sales_price
                }
            }
            val productDiscountPriceText = ::gen_productDiscountPriceText_fn
            fun gen_productBarcodeText_fn(): String {
                val product = activePrintProduct()
                if (product == null) {
                    return "-"
                }
                if (product.barcode != "") {
                    return product.barcode
                }
                if (product.sku != "") {
                    return product.sku
                }
                return "-"
            }
            val productBarcodeText = ::gen_productBarcodeText_fn
            fun gen_productSkuText_fn(): String {
                val product = activePrintProduct()
                if (product == null) {
                    return "-"
                }
                return if (product.sku == "") {
                    "-"
                } else {
                    product.sku
                }
            }
            val productSkuText = ::gen_productSkuText_fn
            val productInfoVisible = computed(fun(): Boolean {
                return productInfoLoading.value || productInfoError.value != "" || printProduct.value != null || printProductId.value != ""
            }
            )
            val productInfoTitle = computed(fun(): String {
                val product = activePrintProduct()
                if (product == null) {
                    return "商品信息"
                }
                return productNameText()
            }
            )
            val productInfoSalesPrice = computed(fun(): String {
                return productOriginalPriceText()
            }
            )
            val productInfoBarcode = computed(fun(): String {
                return productBarcodeText()
            }
            )
            val productInfoCategoryName = computed(fun(): String {
                val name = productCategoryInfo.value.name
                return if (name == "") {
                    "未分类"
                } else {
                    name
                }
            }
            )
            val productInfoKasaCode = computed(fun(): String {
                val code = productCategoryInfo.value.kasaCode
                return if (code == "") {
                    "未关联"
                } else {
                    code
                }
            }
            )
            fun gen_printValueForSource_fn(source: String, fallback: String): String {
                if (source == "name") {
                    return productNameText()
                }
                if (source == "price") {
                    return productDiscountPriceText()
                }
                if (source == "base_sales_price") {
                    return productOriginalPriceText()
                }
                if (source == "discount_price") {
                    return productDiscountPriceText()
                }
                if (source == "barcode") {
                    return productBarcodeText()
                }
                if (source == "sku") {
                    return productSkuText()
                }
                if (source == "kod") {
                    return productSkuText()
                }
                return fallback
            }
            val printValueForSource = ::gen_printValueForSource_fn
            fun gen_printLabelForSource_fn(source: UTSJSONObject, index: Number): String {
                val elementLabel = getStringField(source, "label")
                if (elementLabel != "") {
                    return elementLabel
                }
                val elementSource = getStringField(source, "source")
                if (elementSource == "name") {
                    return "商品名称"
                }
                if (elementSource == "price") {
                    return "打印价格"
                }
                if (elementSource == "base_sales_price") {
                    return "原价"
                }
                if (elementSource == "discount_price") {
                    return "折扣价"
                }
                if (elementSource == "barcode") {
                    return "条码"
                }
                if (elementSource == "sku" || elementSource == "kod") {
                    return "KOD"
                }
                return "字段 " + (index + 1).toString(10)
            }
            val printLabelForSource = ::gen_printLabelForSource_fn
            fun gen_printFieldKey_fn(source: UTSJSONObject, index: Number): String {
                val elementSource = getStringField(source, "source")
                if (elementSource != "" && elementSource != "custom") {
                    return elementSource
                }
                val elementKey = getStringField(source, "key")
                return if (elementKey == "") {
                    ("custom-" + index.toString(10))
                } else {
                    elementKey
                }
            }
            val printFieldKey = ::gen_printFieldKey_fn
            fun gen_isEmphasisPrintSource_fn(source: String): Boolean {
                return source == "price" || source == "discount_price" || source == "base_sales_price"
            }
            val isEmphasisPrintSource = ::gen_isEmphasisPrintSource_fn
            fun gen_hasPreviewField_fn(fields: UTSArray<PrintPreviewField__1>, key: String): Boolean {
                run {
                    var index: Number = 0
                    while(index < fields.length){
                        if (fields[index].key == key) {
                            return true
                        }
                        index += 1
                    }
                }
                return false
            }
            val hasPreviewField = ::gen_hasPreviewField_fn
            fun gen_buildPrintPreviewFields_fn(): UTSArray<PrintPreviewField__1> {
                val item = selectedPrintTemplate.value
                if (item == null) {
                    return _uA<PrintPreviewField__1>()
                }
                val result: UTSArray<PrintPreviewField__1> = _uA()
                run {
                    var index: Number = 0
                    while(index < item.elements.length){
                        val source = item.elements[index]
                        val type = getStringField(source, "type")
                        if (type == "hline" || type == "vline" || type == "rect") {
                            index += 1
                            continue
                        }
                        val key = printFieldKey(source, index)
                        if (hasPreviewField(result, key)) {
                            index += 1
                            continue
                        }
                        val elementSource = getStringField(source, "source")
                        val fallbackContent = getStringField(source, "content")
                        result.push(PrintPreviewField__1(key = key, label = printLabelForSource(source, index), value = printValueForSource(elementSource, fallbackContent), emphasis = isEmphasisPrintSource(elementSource)))
                        index += 1
                    }
                }
                return result
            }
            val buildPrintPreviewFields = ::gen_buildPrintPreviewFields_fn
            val printPreviewFields = computed(fun(): UTSArray<PrintPreviewField__1> {
                return buildPrintPreviewFields()
            }
            )
            fun gen_mappedPrintElement_fn(source: UTSJSONObject): UTSJSONObject {
                val elementSource = getStringField(source, "source")
                val fallbackContent = getStringField(source, "content")
                return _uO("type" to getStringField(source, "type", "text"), "x" to numberValue(source["x"], 0), "y" to numberValue(source["y"], 0), "width" to numberValue(source["width"], 10), "height" to numberValue(source["height"], 6), "fontSize" to numberValue(source["fontSize"], 4), "content" to printValueForSource(elementSource, fallbackContent), "source" to elementSource, "barcodeType" to getStringField(source, "barcodeType", "EAN13"), "showText" to boolValue(source["showText"], true), "lineWidth" to numberValue(source["lineWidth"], 1))
            }
            val mappedPrintElement = ::gen_mappedPrintElement_fn
            fun gen_buildProductPrintElements_fn(item: PrintTemplateItem): UTSArray<UTSJSONObject> {
                val result: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < item.elements.length){
                        result.push(mappedPrintElement(item.elements[index]))
                        index += 1
                    }
                }
                return result
            }
            val buildProductPrintElements = ::gen_buildProductPrintElements_fn
            fun gen_handlePrintCopiesChange_fn(value: String) {
                printCopiesText.value = value
            }
            val handlePrintCopiesChange = ::gen_handlePrintCopiesChange_fn
            fun gen_printCopiesValue_fn(): Number {
                val copies = intValue(printCopiesText.value)
                if (copies <= 0) {
                    return 1
                }
                if (copies > 999) {
                    return 999
                }
                return copies
            }
            val printCopiesValue = ::gen_printCopiesValue_fn
            fun gen_connectedPrinterAddress_fn(): String {
                val connected = getConnectedPrinter()
                if (!connected.success || connected.data == null) {
                    return ""
                }
                val data = connected.data as UTSJSONObject
                return getStringField(data, "address")
            }
            val connectedPrinterAddress = ::gen_connectedPrinterAddress_fn
            fun gen_goPrinterSettings_fn() {
                uni_showToast(ShowToastOptions(title = "请先连接打印机", icon = "none"))
                setTimeout(fun(){
                    uni_navigateTo(NavigateToOptions(url = "/pages/printer-settings/index"))
                }
                , 450)
            }
            val goPrinterSettings = ::gen_goPrinterSettings_fn
            fun gen_closePrintPopup_fn() {
                if (printing.value) {
                    return
                }
                printPopupVisible.value = false
            }
            val closePrintPopup = ::gen_closePrintPopup_fn
            fun gen_confirmPrintProduct_fn() {
                if (printing.value) {
                    return
                }
                val item = selectedPrintTemplate.value
                if (item == null) {
                    return
                }
                val address = connectedPrinterAddress()
                if (address == "") {
                    closePrintPopup()
                    goPrinterSettings()
                    return
                }
                printing.value = true
                val response = printLabelBitmap(_uO("address" to address, "paperWidthMm" to numberValue(item.paper_width_mm, 30), "paperHeightMm" to numberValue(item.paper_height_mm, 20), "dotsPerMm" to item.dots_per_mm, "elements" to buildProductPrintElements(item), "chunkSize" to 1024, "delayMs" to 20, "copies" to printCopiesValue()))
                printing.value = false
                if (response.success) {
                    uni_showToast(ShowToastOptions(title = "已发送打印", icon = "success"))
                    closePrintPopup()
                    return
                }
                uni_showToast(ShowToastOptions(title = if (response.message == "") {
                    "打印失败"
                } else {
                    response.message
                }
                , icon = "none"))
            }
            val confirmPrintProduct = ::gen_confirmPrintProduct_fn
            fun gen_productIdFromPayload_fn(payload: UTSJSONObject): String {
                val rawData = payload["formData"]
                if (rawData != null) {
                    val data = rawData as UTSJSONObject
                    val product = getStringField(data, "product")
                    if (product != "") {
                        return product
                    }
                }
                val initialProduct = getStringField(initialData.value, "product")
                if (initialProduct != "") {
                    return initialProduct
                }
                val detail = currentDetail.value
                if (detail != null) {
                    return detail.product.toString(10)
                }
                return ""
            }
            val productIdFromPayload = ::gen_productIdFromPayload_fn
            fun gen_resolveProductIdForPrint_fn(payload: UTSJSONObject): UTSPromise<String> {
                return wrapUTSPromise(suspend w1@{
                        val currentProductId = productIdFromPayload(payload)
                        if (currentProductId != "") {
                            return@w1 currentProductId
                        }
                        if (detailId.value == "") {
                            return@w1 ""
                        }
                        val detail = await(getPurchaseDetailItem(detailId.value))
                        currentDetail.value = detail
                        return@w1 detail.product.toString(10)
                })
            }
            val resolveProductIdForPrint = ::gen_resolveProductIdForPrint_fn
            fun gen_ensurePrintProduct_fn(productIdText: String): UTSPromise<Boolean> {
                return wrapUTSPromise(suspend w1@{
                        if (productIdText == "") {
                            return@w1 false
                        }
                        await(loadProductInfo(productIdText))
                        return@w1 printProduct.value != null && printProductId.value == productIdText
                })
            }
            val ensurePrintProduct = ::gen_ensurePrintProduct_fn
            fun gen_firstUsableDefaultTemplate_fn(items: UTSArray<PrintTemplateItem>): PrintTemplateItem? {
                run {
                    var index: Number = 0
                    while(index < items.length){
                        val item = items[index]
                        if (item.is_default && item.is_active) {
                            return item
                        }
                        index += 1
                    }
                }
                if (items.length > 0) {
                    return items[0]
                }
                return null
            }
            val firstUsableDefaultTemplate = ::gen_firstUsableDefaultTemplate_fn
            fun gen_getDefaultProductPrintTemplate_fn(): UTSPromise<PrintTemplateItem?> {
                return wrapUTSPromise(suspend w1@{
                        val response = await(getPrintTemplateList(PrintTemplateListQuery(search = null, page = 1, page_size = 20, template_type = "product_label", is_active = "true", is_default = "true")))
                        val productTemplate = firstUsableDefaultTemplate(response.results)
                        if (productTemplate != null) {
                            return@w1 productTemplate
                        }
                        val fallbackResponse = await(getPrintTemplateList(PrintTemplateListQuery(search = null, page = 1, page_size = 20, template_type = null, is_active = "true", is_default = "true")))
                        return@w1 firstUsableDefaultTemplate(fallbackResponse.results)
                })
            }
            val getDefaultProductPrintTemplate = ::gen_getDefaultProductPrintTemplate_fn
            fun gen_handleQuickPrint_fn(payload: UTSJSONObject): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (quickPrintLoading.value || printing.value) {
                            return@w1
                        }
                        quickPrintLoading.value = true
                        uni_showLoading(ShowLoadingOptions(title = "准备打印...", mask = true))
                        try {
                            val productIdText = await(resolveProductIdForPrint(payload))
                            if (productIdText == "") {
                                uni_showToast(ShowToastOptions(title = "当前明细缺少商品，无法打印", icon = "none"))
                                return@w1
                            }
                            val productReady = await(ensurePrintProduct(productIdText))
                            if (!productReady) {
                                uni_showToast(ShowToastOptions(title = "商品信息加载失败，无法打印", icon = "none"))
                                return@w1
                            }
                            val template = await(getDefaultProductPrintTemplate())
                            if (template == null) {
                                uni_showToast(ShowToastOptions(title = "未设置默认商品价签模板", icon = "none"))
                                return@w1
                            }
                            selectedPrintTemplate.value = template
                            printCopiesText.value = if (template.copies_default <= 0) {
                                "1"
                            } else {
                                template.copies_default.toString(10)
                            }
                            printPopupVisible.value = true
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error, "快速打印准备失败"), icon = "none"))
                        }
                         finally {
                            uni_hideLoading(null)
                            quickPrintLoading.value = false
                        }
                })
            }
            val handleQuickPrint = ::gen_handleQuickPrint_fn
            fun gen_buildPayload_fn(data: UTSJSONObject): PurchaseDetailMutationData {
                return PurchaseDetailMutationData(purchase = purchaseId.value, product = getStringField(data, "product"), quantity = getStringField(data, "quantity"), received_quantity = if (getStringField(data, "received_quantity") == "") {
                    null
                } else {
                    getStringField(data, "received_quantity")
                }
                , notes = if (getStringField(data, "notes") == "") {
                    null
                } else {
                    getStringField(data, "notes")
                }
                )
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
                        val quantity = parseInt(body.quantity)
                        if (body.purchase == "" || body.product == "" || isNaN(quantity) || quantity <= 0) {
                            uni_showToast(ShowToastOptions(title = "请填写商品和有效采购数量", icon = "none"))
                            return@w1
                        }
                        val actionText = if (formMode.value == "edit") {
                            "保存采购明细"
                        } else {
                            "创建采购明细"
                        }
                        submitting.value = true
                        uni_showLoading(ShowLoadingOptions(title = actionText + "中...", mask = true))
                        try {
                            if (formMode.value == "edit" && detailId.value != "") {
                                await(updatePurchaseDetail(detailId.value, body))
                            } else {
                                await(createPurchaseDetail(body))
                            }
                            markRefresh()
                            uni_showToast(ShowToastOptions(title = takeLatestResponseMessage(actionText + "成功"), icon = "success"))
                            goBackToList()
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error, actionText + "失败"), icon = "none"))
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
            fun gen_fillReceivedQuantityFromQuantity_fn(data: UTSJSONObject): Boolean {
                val quantity = getStringField(data, "quantity")
                val quantityNumber = parseInt(quantity)
                if (quantity == "" || isNaN(quantityNumber) || quantityNumber <= 0) {
                    uni_showToast(ShowToastOptions(title = "请先填写有效采购数量", icon = "none"))
                    return false
                }
                data["received_quantity"] = quantity
                return true
            }
            val fillReceivedQuantityFromQuantity = ::gen_fillReceivedQuantityFromQuantity_fn
            fun gen_handleSaveRequest_fn(payload: UTSJSONObject): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        val rawData = payload["formData"]
                        val data = if (rawData == null) {
                            (_uO())
                        } else {
                            (rawData as UTSJSONObject)
                        }
                        if (!fillReceivedQuantityFromQuantity(data)) {
                            return@w1
                        }
                        await(persistForm(_uO("formData" to data)))
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
            fun gen_handleFieldChange_fn(payload: UTSJSONObject) {
                val key = getStringField(payload, "key")
                if (key != "product") {
                    return
                }
                val productIdText = getStringField(payload, "value")
                loadProductInfo(productIdText)
            }
            val handleFieldChange = ::gen_handleFieldChange_fn
            fun gen_handleInputAdd_fn(payload: UTSJSONObject) {
                val key = getStringField(payload, "key")
                if (key != "received_quantity") {
                    return
                }
                val rawData = payload["formData"]
                val data = if (rawData == null) {
                    (_uO())
                } else {
                    (rawData as UTSJSONObject)
                }
                if (!fillReceivedQuantityFromQuantity(data)) {
                    return
                }
                uni_showToast(ShowToastOptions(title = "已填入采购数量", icon = "none"))
            }
            val handleInputAdd = ::gen_handleInputAdd_fn
            fun gen_handleBottomSelectAdd_fn(payload: UTSJSONObject) {
                uni_showToast(ShowToastOptions(title = "请在对应模块维护选项", icon = "none"))
            }
            val handleBottomSelectAdd = ::gen_handleBottomSelectAdd_fn
            fun gen_handleBottomSelectEdit_fn(payload: UTSJSONObject) {
                uni_showToast(ShowToastOptions(title = "该字段不支持直接编辑", icon = "none"))
            }
            val handleBottomSelectEdit = ::gen_handleBottomSelectEdit_fn
            onLoad(fun(query: OnLoadOptions){
                val purchaseValue = query["purchase"]
                val idValue = query["id"]
                purchaseId.value = if (purchaseValue == null) {
                    ""
                } else {
                    ("" + purchaseValue)
                }
                detailId.value = if (idValue == null) {
                    ""
                } else {
                    ("" + idValue)
                }
                formMode.value = if (detailId.value == "") {
                    "create"
                } else {
                    "edit"
                }
                currentDetail.value = null
                resetProductInfo()
                initialData.value = initialCreateData()
                if (formMode.value == "edit") {
                    loadDetail(detailId.value)
                } else {
                    markLeaveConfirmRequired()
                }
            }
            )
            onShow(fun(){
                val productIdText = printProductId.value
                if (productIdText != "") {
                    loadProductInfo(productIdText)
                }
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversaForm = resolveEasyComponent("lili-UniversaForm", GenUniModulesLiliUniversaFormComponentsLiliUniversaFormLiliUniversaFormClass)
                val _component_lili_print_copies_stepper = resolveEasyComponent("lili-print-copies-stepper", GenUniModulesLiliPrintCopiesStepperComponentsLiliPrintCopiesStepperLiliPrintCopiesStepperClass)
                val _component_page_container = resolveComponent("page-container")
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to pageTitle.value, "showBack" to true, "showSearch" to false, "showHome" to true, "homePath" to homePath.value, "backgroundColor" to "#EEF2F7"), null, 8, _uA(
                        "title",
                        "homePath"
                    )),
                    _cE("view", _uM("class" to "page-content"), _uA(
                        if (isTrue(productInfoVisible.value)) {
                            _cE("view", _uM("key" to 0, "class" to "product-card"), _uA(
                                _cE("view", _uM("class" to "product-card-head"), _uA(
                                    _cE("text", _uM("class" to "product-card-title"), _tD(productInfoTitle.value), 1),
                                    if (isTrue(unref(productInfoLoading))) {
                                        _cE("text", _uM("key" to 0, "class" to "product-card-state"), "刷新中")
                                    } else {
                                        if (unref(productInfoError) != "") {
                                            _cE("text", _uM("key" to 1, "class" to "product-card-state product-card-state-error"), "加载失败")
                                        } else {
                                            _cC("v-if", true)
                                        }
                                    }
                                )),
                                _cE("view", _uM("class" to "product-info-row"), _uA(
                                    _cE("view", _uM("class" to "product-info-cell"), _uA(
                                        _cE("text", _uM("class" to "product-info-label"), "销售价"),
                                        _cE("text", _uM("class" to "product-info-value product-info-price"), _tD(productInfoSalesPrice.value), 1)
                                    )),
                                    _cE("view", _uM("class" to "product-info-cell product-info-cell-right"), _uA(
                                        _cE("text", _uM("class" to "product-info-label"), "KASA编码"),
                                        _cE("text", _uM("class" to "product-info-value"), _tD(productInfoKasaCode.value), 1)
                                    ))
                                )),
                                _cE("view", _uM("class" to "product-info-row"), _uA(
                                    _cE("view", _uM("class" to "product-info-cell"), _uA(
                                        _cE("text", _uM("class" to "product-info-label"), "分类"),
                                        _cE("text", _uM("class" to "product-info-value"), _tD(productInfoCategoryName.value), 1)
                                    )),
                                    _cE("view", _uM("class" to "product-info-cell product-info-cell-right"), _uA(
                                        _cE("text", _uM("class" to "product-info-label"), "KOD/条码"),
                                        _cE("text", _uM("class" to "product-info-value"), _tD(productInfoBarcode.value), 1)
                                    ))
                                ))
                            ))
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        _cV(_component_lili_UniversaForm, _uM("mode" to unref(formMode), "formSections" to unref(formSections), "initialData" to unref(initialData), "leaveSignal" to unref(leaveSignal), "dirtySignal" to unref(dirtySignal), "showFloatingAction" to (unref(formMode) == "edit"), "floatingActionText" to if (unref(quickPrintLoading)) {
                            "准备中"
                        } else {
                            "快速打印"
                        }
                        , "floatingActionDisabled" to (unref(quickPrintLoading) || unref(printing)), "leaveConfirmContent" to "是否已收到全部数量", "leaveConfirmConfirmText" to "确认", "onSubmit" to handleSubmit, "onCancel" to handleCancel, "onDiscardLeave" to handleDiscardLeave, "onSaveRequest" to handleSaveRequest, "onDirtyChange" to handleDirtyChange, "onFieldChange" to handleFieldChange, "onInputAdd" to handleInputAdd, "onFloatingAction" to handleQuickPrint, "onBottomSelectAdd" to handleBottomSelectAdd, "onBottomSelectEdit" to handleBottomSelectEdit), null, 8, _uA(
                            "mode",
                            "formSections",
                            "initialData",
                            "leaveSignal",
                            "dirtySignal",
                            "showFloatingAction",
                            "floatingActionText",
                            "floatingActionDisabled"
                        ))
                    )),
                    _cV(_component_page_container, _uM("show" to unref(printPopupVisible), "position" to "bottom", "round" to true, "overlay" to true, "duration" to 240, "overlay-style" to "background-color: rgba(15, 23, 42, 0.42);", "custom-style" to "background-color: #FFFFFF;", "onClickoverlay" to closePrintPopup), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                        return _uA(
                            _cE("view", _uM("class" to "print-panel"), _uA(
                                _cE("view", _uM("class" to "print-handle")),
                                _cE("view", _uM("class" to "print-head"), _uA(
                                    _cE("view", null, _uA(
                                        _cE("text", _uM("class" to "print-title"), "确认打印"),
                                        _cE("text", _uM("class" to "print-subtitle"), _tD(selectedTemplateName.value), 1)
                                    )),
                                    _cE("view", _uM("class" to "print-close", "onClick" to closePrintPopup), _uA(
                                        _cE("text", _uM("class" to "print-close-text"), "关闭")
                                    ))
                                )),
                                _cE("view", _uM("class" to "print-content-card"), _uA(
                                    if (printPreviewFields.value.length == 0) {
                                        _cE("view", _uM("key" to 0, "class" to "print-empty-row"), _uA(
                                            _cE("text", _uM("class" to "print-empty-text"), "当前模板没有需要核对的字段")
                                        ))
                                    } else {
                                        _cC("v-if", true)
                                    }
                                    ,
                                    _cE(Fragment, null, RenderHelpers.renderList(printPreviewFields.value, fun(field, __key, __index, _cached): Any {
                                        return _cE("view", _uM("key" to field.key, "class" to "print-row"), _uA(
                                            _cE("text", _uM("class" to "print-label"), _tD(field.label), 1),
                                            _cE("text", _uM("class" to _nC(if (field.emphasis) {
                                                "print-value print-value-emphasis"
                                            } else {
                                                "print-value"
                                            }
                                            )), _tD(field.value), 3)
                                        ))
                                    }
                                    ), 128)
                                )),
                                _cE("view", _uM("class" to "print-copies-wrap"), _uA(
                                    _cV(_component_lili_print_copies_stepper, _uM("value" to unref(printCopiesText), "title" to "打印页数", "desc" to "一个商品可一次贴上多张价格", "onChange" to handlePrintCopiesChange), null, 8, _uA(
                                        "value"
                                    ))
                                )),
                                _cE("view", _uM("class" to "print-actions"), _uA(
                                    _cE("view", _uM("class" to "print-secondary-btn", "onClick" to closePrintPopup), _uA(
                                        _cE("text", _uM("class" to "print-secondary-text"), "取消")
                                    )),
                                    _cE("view", _uM("class" to "print-primary-btn", "onClick" to confirmPrintProduct), _uA(
                                        _cE("text", _uM("class" to "print-primary-text"), _tD(if (unref(printing)) {
                                            "打印中..."
                                        } else {
                                            "打印"
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#EEF2F7")), "page-content" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingBottom" to 0)), "product-card" to _pS(_uM("marginTop" to 6, "marginRight" to 10, "marginBottom" to 4, "marginLeft" to 10, "paddingTop" to 7, "paddingRight" to 10, "paddingBottom" to 7, "paddingLeft" to 10, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "#DDE6F2", "borderRightColor" to "#DDE6F2", "borderBottomColor" to "#DDE6F2", "borderLeftColor" to "#DDE6F2", "backgroundColor" to "#FFFFFF")), "product-card-head" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "marginBottom" to 2)), "product-card-title" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "fontSize" to 14, "lineHeight" to "18px", "color" to "#0F172A", "fontWeight" to "700")), "product-card-state" to _pS(_uM("marginLeft" to 6, "fontSize" to 11, "lineHeight" to "14px", "color" to "#64748B")), "product-card-state-error" to _pS(_uM("color" to "#B91C1C")), "product-info-row" to _pS(_uM("flexDirection" to "row", "marginTop" to 3)), "product-info-cell" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "minHeight" to 24, "paddingRight" to 6, "flexDirection" to "row", "alignItems" to "center")), "product-info-cell-right" to _pS(_uM("paddingLeft" to 6, "paddingRight" to 0, "borderLeftWidth" to 1, "borderLeftColor" to "#E2E8F0")), "product-info-label" to _pS(_uM("width" to 56, "fontSize" to 10, "lineHeight" to "14px", "color" to "#64748B")), "product-info-value" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "fontSize" to 13, "lineHeight" to "17px", "color" to "#111827")), "product-info-price" to _pS(_uM("color" to "#B91C1C", "fontWeight" to "700")), "print-panel" to _pS(_uM("paddingTop" to 12, "paddingRight" to 14, "paddingBottom" to 22, "paddingLeft" to 14, "backgroundColor" to "#FFFFFF")), "print-handle" to _pS(_uM("width" to 42, "height" to 4, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2, "backgroundColor" to "#CBD5E1", "alignSelf" to "center", "marginBottom" to 14)), "print-head" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "marginBottom" to 14)), "print-title" to _pS(_uM("fontSize" to 20, "color" to "#0F172A", "fontWeight" to "700", "lineHeight" to "28px")), "print-subtitle" to _pS(_uM("marginTop" to 3, "fontSize" to 13, "color" to "#64748B", "lineHeight" to "18px")), "print-close" to _pS(_uM("height" to 34, "paddingLeft" to 14, "paddingRight" to 14, "borderTopLeftRadius" to 17, "borderTopRightRadius" to 17, "borderBottomRightRadius" to 17, "borderBottomLeftRadius" to 17, "backgroundColor" to "#F1F5F9", "alignItems" to "center", "justifyContent" to "center")), "print-close-text" to _pS(_uM("fontSize" to 13, "color" to "#334155")), "print-content-card" to _pS(_uM("borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "#DDE6F2", "borderRightColor" to "#DDE6F2", "borderBottomColor" to "#DDE6F2", "borderLeftColor" to "#DDE6F2", "backgroundColor" to "#F8FAFC", "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "marginBottom" to 10)), "print-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "flex-start", "justifyContent" to "space-between", "paddingTop" to 9, "paddingBottom" to 9, "borderBottomWidth" to 1, "borderBottomColor" to "#E2E8F0")), "print-empty-row" to _pS(_uM("minHeight" to 42, "alignItems" to "center", "justifyContent" to "center")), "print-empty-text" to _pS(_uM("fontSize" to 13, "color" to "#64748B")), "print-label" to _pS(_uM("width" to 78, "fontSize" to 13, "color" to "#64748B", "lineHeight" to "20px")), "print-value" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "fontSize" to 14, "color" to "#0F172A", "lineHeight" to "20px", "textAlign" to "right")), "print-value-emphasis" to _pS(_uM("color" to "#B91C1C", "fontWeight" to "700")), "print-copies-wrap" to _pS(_uM("marginBottom" to 14)), "print-actions" to _pS(_uM("flexDirection" to "row")), "print-secondary-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 44, "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "backgroundColor" to "#F1F5F9", "alignItems" to "center", "justifyContent" to "center", "marginRight" to 12)), "print-secondary-text" to _pS(_uM("fontSize" to 15, "color" to "#334155", "fontWeight" to "700")), "print-primary-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 44, "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "backgroundColor" to "#111827", "alignItems" to "center", "justifyContent" to "center")), "print-primary-text" to _pS(_uM("fontSize" to 15, "color" to "#FFFFFF", "fontWeight" to "700")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
