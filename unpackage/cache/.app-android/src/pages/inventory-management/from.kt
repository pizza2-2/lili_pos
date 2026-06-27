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
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesInventoryManagementFrom : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesInventoryManagementFrom) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesInventoryManagementFrom
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
            fun gen_floatValue_fn(value: Any?): Number {
                val parsed = parseFloat(stringValue(value))
                if (isNaN(parsed)) {
                    return 0
                }
                return parsed
            }
            val floatValue = ::gen_floatValue_fn
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
                    parsed = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(text), " at pages/inventory-management/from.uvue:355")
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
            fun gen_parseErrorMessage_fn(error: Any, fallback: String): String {
                if (error == null) {
                    return fallback
                }
                var text = ""
                try {
                    val errorText = JSON.stringify(error)
                    if (errorText != null) {
                        text = errorText
                    }
                }
                 catch (stringifyError: Throwable) {
                    text = ""
                }
                if (text != "" && text != "{}" && text != "null") {
                    var parsedError: UTSJSONObject? = null
                    try {
                        val trimmedText = text.trim()
                        if (trimmedText != "" && trimmedText.substring(0, 1) == "{") {
                            parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(trimmedText), " at pages/inventory-management/from.uvue:376")
                        }
                    }
                     catch (parseError: Throwable) {
                        parsedError = null
                    }
                    if (parsedError != null) {
                        val rawMessage = parsedError!!["message"]
                        val parsedMessage = stringValue(rawMessage)
                        if (parsedMessage != "") {
                            return parsedMessage
                        }
                    }
                    return text
                }
                val textMessage = stringValue(error)
                if (textMessage != "" && textMessage != "[object Object]") {
                    return textMessage
                }
                return fallback
            }
            val parseErrorMessage = ::gen_parseErrorMessage_fn
            fun gen_readInputValue_fn(event: Any): String {
                val inputEvent = event as UniInputEvent
                return inputEvent.detail.value
            }
            val readInputValue = ::gen_readInputValue_fn
            fun gen_decimalText_fn(value: String): String {
                var text = value.trim()
                text = text.split(",").join(".")
                var result = ""
                var dotCount: Number = 0
                run {
                    var index: Number = 0
                    while(index < text.length){
                        val ch = text.charAt(index)
                        if (ch == ".") {
                            dotCount = dotCount + 1
                            if (dotCount <= 1) {
                                result = result + ch
                            }
                            index += 1
                            continue
                        }
                        if (ch >= "0" && ch <= "9") {
                            result = result + ch
                        }
                        index += 1
                    }
                }
                return result
            }
            val decimalText = ::gen_decimalText_fn
            fun gen_signedIntegerText_fn(value: String): String {
                val text = value.trim()
                var result = ""
                run {
                    var index: Number = 0
                    while(index < text.length){
                        val ch = text.charAt(index)
                        if (ch == "-" && result == "") {
                            result = "-"
                            index += 1
                            continue
                        }
                        if (ch >= "0" && ch <= "9") {
                            result = result + ch
                        }
                        index += 1
                    }
                }
                return result
            }
            val signedIntegerText = ::gen_signedIntegerText_fn
            fun gen_positiveIntegerText_fn(value: String): String {
                val text = value.trim()
                var result = ""
                run {
                    var index: Number = 0
                    while(index < text.length){
                        val ch = text.charAt(index)
                        if (ch >= "0" && ch <= "9") {
                            result = result + ch
                        }
                        index += 1
                    }
                }
                return result
            }
            val positiveIntegerText = ::gen_positiveIntegerText_fn
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
            fun gen_firstImageFromStock_fn(detail: UTSJSONObject): String {
                val direct = stringValue(detail["product_image"])
                if (direct != "") {
                    return direct
                }
                val mediaFiles = parseObjectArray(detail["product_media_files"])
                run {
                    var index: Number = 0
                    while(index < mediaFiles.length){
                        val image = firstStringField(mediaFiles[index], _uA(
                            "signed_thumbnail_url",
                            "thumbnail_url",
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
            fun gen_numberText_fn(value: Any?): String {
                return intValue(value).toString(10)
            }
            val numberText = ::gen_numberText_fn
            fun gen_moneyText_fn(value: Any?): String {
                return floatValue(value).toFixed(2)
            }
            val moneyText = ::gen_moneyText_fn
            fun gen_normalizedCostText_fn(value: String): String {
                val text = decimalText(value)
                if (text == "" || text == ".") {
                    return "0.00"
                }
                return text
            }
            val normalizedCostText = ::gen_normalizedCostText_fn
            fun gen_signedNumberText_fn(value: Number): String {
                if (value > 0) {
                    return "+" + value.toString(10)
                }
                return value.toString(10)
            }
            val signedNumberText = ::gen_signedNumberText_fn
            fun gen_dateTimeText_fn(value: Any?): String {
                val text = stringValue(value)
                if (text == "") {
                    return "-"
                }
                val compact = text.split("T").join(" ")
                if (compact.length > 19) {
                    return compact.substring(0, 19)
                }
                return compact
            }
            val dateTimeText = ::gen_dateTimeText_fn
            fun gen_alertLabel_fn(value: String): String {
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
                return "正常"
            }
            val alertLabel = ::gen_alertLabel_fn
            fun gen_listedLabel_fn(value: String): String {
                if (value == "false") {
                    return "下架"
                }
                return "上架"
            }
            val listedLabel = ::gen_listedLabel_fn
            fun gen_optionText_fn(source: UTSArray<SelectOption__9>, value: String, fallback: String): String {
                run {
                    var index: Number = 0
                    while(index < source.length){
                        val option = source[index]
                        if (option.value == value) {
                            return option.label
                        }
                        index += 1
                    }
                }
                return fallback
            }
            val optionText = ::gen_optionText_fn
            fun gen_buildStaticSelectResponse_fn(source: UTSArray<SelectOption__9>, params: UTSJSONObject): UTSJSONObject {
                val id = stringValue(params["id"])
                val rows: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < source.length){
                        val option = source[index]
                        if (id != "" && option.value != id) {
                            index += 1
                            continue
                        }
                        rows.push(_uO("value" to option.value, "text" to option.label, "label" to option.label))
                        index += 1
                    }
                }
                return _uO("data" to rows, "results" to rows, "total" to rows.length, "total_count" to rows.length)
            }
            val buildStaticSelectResponse = ::gen_buildStaticSelectResponse_fn
            fun gen_baseInventoryQuery_fn(page: Number, pageSize: Number): InventoryListQuery {
                return InventoryListQuery(search = null, page = page, page_size = pageSize, status = null, alert_status = null, transaction_type = null, location_type = null, is_active = null)
            }
            val baseInventoryQuery = ::gen_baseInventoryQuery_fn
            val refreshStorageKey = "refresh:pages:inventory-management:index"
            val productId = ref("")
            val productName = ref("")
            val selectedStockId = ref("")
            val initialMode = ref("")
            val stocks = ref(_uA<UTSJSONObject>())
            val movementRecords = ref(_uA<UTSJSONObject>())
            val movementRecordTotal = ref(0)
            val isLoading = ref(false)
            val errorMessage = ref("")
            val createSubmitting = ref(false)
            val adjustSubmitting = ref(false)
            val createSheetVisible = ref(false)
            val adjustSheetVisible = ref(false)
            val initialSheetConsumed = ref(false)
            val sheetPanelHeight = ref(620)
            val sheetScrollHeight = ref(420)
            val createLocationValue = ref("")
            val createLocationText = ref("")
            val createQuantityText = ref("")
            val createTypeValue = ref("INITIAL")
            val createTypeText = ref("初始库存")
            val createUnitCostText = ref("")
            val createNotesText = ref("")
            val adjustQuantityText = ref("")
            val adjustTypeValue = ref("ADJUSTMENT")
            val adjustTypeText = ref("盘点调整")
            val adjustUnitCostText = ref("")
            val adjustNotesText = ref("")
            val createTypeOptions = _uA(
                SelectOption__9(value = "INITIAL", label = "初始库存"),
                SelectOption__9(value = "PURCHASE", label = "采购入库")
            )
            val adjustTypeOptions = _uA(
                SelectOption__9(value = "ADJUSTMENT", label = "盘点调整"),
                SelectOption__9(value = "DAMAGE", label = "损坏"),
                SelectOption__9(value = "LOSS", label = "丢失"),
                SelectOption__9(value = "INITIAL", label = "初始库存"),
                SelectOption__9(value = "PURCHASE", label = "采购入库"),
                SelectOption__9(value = "RETURN_IN", label = "退货入库")
            )
            fun gen_stockById_fn(id: String): UTSJSONObject? {
                run {
                    var index: Number = 0
                    while(index < stocks.value.length){
                        val stock = stocks.value[index]
                        if (stringValue(stock["id"]) == id) {
                            return stock
                        }
                        index += 1
                    }
                }
                return null
            }
            val stockById = ::gen_stockById_fn
            fun gen_firstStock_fn(): UTSJSONObject? {
                if (stocks.value.length == 0) {
                    return null
                }
                return stocks.value[0]
            }
            val firstStock = ::gen_firstStock_fn
            fun gen_selectedStock_fn(): UTSJSONObject? {
                if (selectedStockId.value == "") {
                    return null
                }
                return stockById(selectedStockId.value)
            }
            val selectedStock = ::gen_selectedStock_fn
            fun gen_hasStockAtLocation_fn(locationId: String): Boolean {
                run {
                    var index: Number = 0
                    while(index < stocks.value.length){
                        val stock = stocks.value[index]
                        if (stringValue(stock["location"]) == locationId) {
                            return true
                        }
                        index += 1
                    }
                }
                return false
            }
            val hasStockAtLocation = ::gen_hasStockAtLocation_fn
            fun gen_locationTypeText_fn(stock: UTSJSONObject): String {
                val type = stringValue(stock["location_type"])
                if (type == "WAREHOUSE") {
                    return "仓库"
                }
                if (type == "SHOP") {
                    return "门店"
                }
                if (type == "TRANSIT") {
                    return "在途"
                }
                return if (type == "") {
                    "位置"
                } else {
                    type
                }
            }
            val locationTypeText = ::gen_locationTypeText_fn
            fun gen_updateProductFromStock_fn(stock: UTSJSONObject) {
                if (productId.value == "") {
                    productId.value = stringValue(stock["product"])
                }
                if (productName.value == "") {
                    productName.value = stringValue(stock["product_name"])
                }
            }
            val updateProductFromStock = ::gen_updateProductFromStock_fn
            fun gen_markRefreshNeeded_fn() {
                uni_setStorageSync(refreshStorageKey, "1")
            }
            val markRefreshNeeded = ::gen_markRefreshNeeded_fn
            fun gen_updateSheetLayout_fn() {
                val info = uni_getWindowInfo()
                var panelHeight = info.windowHeight - 96
                if (panelHeight > 680) {
                    panelHeight = 680
                }
                if (panelHeight < 500) {
                    panelHeight = 500
                }
                var scrollHeight = panelHeight - 192
                if (scrollHeight < 280) {
                    scrollHeight = 280
                }
                sheetPanelHeight.value = panelHeight
                sheetScrollHeight.value = scrollHeight
            }
            val updateSheetLayout = ::gen_updateSheetLayout_fn
            val homePath = computed(fun(): String {
                if (productId.value != "") {
                    return "/pages/tabbar/products"
                }
                return "/pages/inventory-management/index"
            }
            )
            val pageTitle = computed(fun(): String {
                if (productName.value != "") {
                    return "库存详情"
                }
                if (productId.value != "") {
                    return "商品库存"
                }
                return "库存详情"
            }
            )
            val productTitle = computed(fun(): String {
                if (productName.value != "") {
                    return productName.value
                }
                val stock = firstStock()
                if (stock != null) {
                    return stringValue(stock!!["product_name"], "未命名商品")
                }
                if (productId.value != "") {
                    return "商品 #" + productId.value
                }
                return "商品库存"
            }
            )
            val productSubtitle = computed(fun(): String {
                val stock = firstStock()
                if (stock == null) {
                    return if (productId.value == "") {
                        "缺少商品信息"
                    } else {
                        "商品 ID " + productId.value
                    }
                }
                val sku = stringValue(stock!!["product_sku"])
                val barcode = stringValue(stock!!["product_barcode"])
                if (sku != "" && barcode != "") {
                    return "SKU " + sku + " / 条码 " + barcode
                }
                if (sku != "") {
                    return "SKU " + sku
                }
                if (barcode != "") {
                    return "条码 " + barcode
                }
                return "未设置 SKU/条码"
            }
            )
            val productImage = computed(fun(): String {
                val stock = firstStock()
                if (stock == null) {
                    return ""
                }
                return firstImageFromStock(stock!!)
            }
            )
            val totalQuantityText = computed(fun(): String {
                var total: Number = 0
                run {
                    var index: Number = 0
                    while(index < stocks.value.length){
                        total = total + intValue(stocks.value[index]["quantity"])
                        index += 1
                    }
                }
                return total.toString(10)
            }
            )
            val totalAvailableText = computed(fun(): String {
                var total: Number = 0
                run {
                    var index: Number = 0
                    while(index < stocks.value.length){
                        total = total + intValue(stocks.value[index]["available_quantity"])
                        index += 1
                    }
                }
                return total.toString(10)
            }
            )
            val stockLocationCountText = computed(fun(): String {
                return stocks.value.length.toString(10)
            }
            )
            val movementRecordCountText = computed(fun(): String {
                return movementRecordTotal.value.toString(10)
            }
            )
            val selectedStockHint = computed(fun(): String {
                val stock = selectedStock()
                if (stock == null) {
                    return "选择一个库存位置后进行调整"
                }
                return stringValue(stock!!["location_name"], "未设置库存位置") + "，当前库存 " + numberText(stock!!["quantity"])
            }
            )
            val adjustBeforeText = computed(fun(): String {
                val stock = selectedStock()
                if (stock == null) {
                    return "-"
                }
                return numberText(stock!!["quantity"])
            }
            )
            val adjustChangeValue = computed(fun(): Number {
                return intValue(adjustQuantityText.value)
            }
            )
            val adjustChangeText = computed(fun(): String {
                if (adjustQuantityText.value == "") {
                    return "-"
                }
                return signedNumberText(adjustChangeValue.value)
            }
            )
            val adjustChangeClass = computed(fun(): String {
                if (adjustChangeValue.value > 0) {
                    return "adjust-preview-value adjust-preview-in"
                }
                if (adjustChangeValue.value < 0) {
                    return "adjust-preview-value adjust-preview-out"
                }
                return "adjust-preview-value"
            }
            )
            val adjustAfterText = computed(fun(): String {
                val stock = selectedStock()
                if (stock == null || adjustQuantityText.value == "") {
                    return "-"
                }
                return (intValue(stock!!["quantity"]) + adjustChangeValue.value).toString(10)
            }
            )
            val createButtonText = computed(fun(): String {
                return if (createSubmitting.value) {
                    "创建中..."
                } else {
                    "创建库存"
                }
            }
            )
            val adjustButtonText = computed(fun(): String {
                return if (adjustSubmitting.value) {
                    "保存中..."
                } else {
                    "保存调整"
                }
            }
            )
            val sheetPanelStyle = computed(fun(): String {
                return "height:" + sheetPanelHeight.value.toString(10) + "px;"
            }
            )
            val sheetScrollStyle = computed(fun(): String {
                return "height:" + sheetScrollHeight.value.toString(10) + "px;"
            }
            )
            fun gen_stockCardClass_fn(stock: UTSJSONObject): String {
                if (stringValue(stock["id"]) == selectedStockId.value) {
                    return "stock-card stock-card-active"
                }
                return "stock-card"
            }
            val stockCardClass = ::gen_stockCardClass_fn
            fun gen_stockAlertClass_fn(stock: UTSJSONObject): String {
                val status = stringValue(stock["alert_status"])
                if (status != "" && status != "NORMAL") {
                    return "stock-alert stock-alert-warning"
                }
                return "stock-alert"
            }
            val stockAlertClass = ::gen_stockAlertClass_fn
            fun gen_selectStock_fn(stock: UTSJSONObject) {
                selectedStockId.value = stringValue(stock["id"])
                adjustUnitCostText.value = moneyText(stock["average_cost"])
                adjustQuantityText.value = ""
                adjustTypeValue.value = "ADJUSTMENT"
                adjustTypeText.value = optionText(adjustTypeOptions, "ADJUSTMENT", "盘点调整")
            }
            val selectStock = ::gen_selectStock_fn
            fun gen_setAdjustQuantityValue_fn(value: Number) {
                var next = value
                if (next > 999999) {
                    next = 999999
                }
                if (next < -999999) {
                    next = -999999
                }
                adjustQuantityText.value = next.toString(10)
            }
            val setAdjustQuantityValue = ::gen_setAdjustQuantityValue_fn
            fun gen_stepAdjustQuantity_fn(delta: Number) {
                setAdjustQuantityValue(intValue(adjustQuantityText.value) + delta)
            }
            val stepAdjustQuantity = ::gen_stepAdjustQuantity_fn
            fun gen_setAdjustQuantityQuick_fn(value: Number) {
                setAdjustQuantityValue(value)
            }
            val setAdjustQuantityQuick = ::gen_setAdjustQuantityQuick_fn
            fun gen_locationOptionText_fn(item: UTSJSONObject): String {
                val name = stringValue(item["name"], stringValue(item["location_name"], "库存位置"))
                val code = stringValue(item["code"])
                if (code != "") {
                    return name + " / " + code
                }
                return name
            }
            val locationOptionText = ::gen_locationOptionText_fn
            fun gen_fetchLocationOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        val query = baseInventoryQuery(intValue(params["page"]), intValue(params["pageSize"]))
                        query.page = if (query.page <= 0) {
                            1
                        } else {
                            query.page
                        }
                        query.page_size = if (query.page_size <= 0) {
                            50
                        } else {
                            query.page_size
                        }
                        query.search = if (stringValue(params["keyword"]) == "") {
                            null
                        } else {
                            stringValue(params["keyword"])
                        }
                        query.is_active = "true"
                        val response = await(getInventoryLocations(query))
                        val rows: UTSArray<UTSJSONObject> = _uA()
                        run {
                            var index: Number = 0
                            while(index < response.results.length){
                                val item = response.results[index]
                                val id = stringValue(item["id"])
                                if (id == "" || hasStockAtLocation(id)) {
                                    index += 1
                                    continue
                                }
                                rows.push(_uO("value" to id, "text" to locationOptionText(item), "label" to locationOptionText(item), "subtitle" to locationTypeText(item)))
                                index += 1
                            }
                        }
                        return@w1 _uO("data" to rows, "results" to rows, "total" to rows.length, "total_count" to rows.length)
                })
            }
            val fetchLocationOptions = ::gen_fetchLocationOptions_fn
            fun gen_fetchCreateTypeOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        return@w1 buildStaticSelectResponse(createTypeOptions, params)
                })
            }
            val fetchCreateTypeOptions = ::gen_fetchCreateTypeOptions_fn
            fun gen_fetchAdjustTypeOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        return@w1 buildStaticSelectResponse(adjustTypeOptions, params)
                })
            }
            val fetchAdjustTypeOptions = ::gen_fetchAdjustTypeOptions_fn
            fun gen_buildProductStockQuery_fn(): InventoryListQuery {
                val query = baseInventoryQuery(1, 100)
                if (productId.value != "") {
                    query.product = productId.value
                }
                if (productId.value == "" && selectedStockId.value != "") {
                    query.stock = selectedStockId.value
                }
                return query
            }
            val buildProductStockQuery = ::gen_buildProductStockQuery_fn
            fun gen_buildMovementQuery_fn(): InventoryListQuery {
                val query = baseInventoryQuery(1, 50)
                if (productId.value != "") {
                    query.product = productId.value
                }
                if (productId.value == "" && selectedStockId.value != "") {
                    query.stock = selectedStockId.value
                }
                return query
            }
            val buildMovementQuery = ::gen_buildMovementQuery_fn
            fun gen_loadInitialStockIfNeeded_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (selectedStockId.value == "") {
                            return@w1
                        }
                        try {
                            val detail = await(getInventoryStockDetail(selectedStockId.value))
                            updateProductFromStock(detail)
                        }
                         catch (error: Throwable) {
                            errorMessage.value = parseErrorMessage(error, "库存详情加载失败")
                        }
                })
            }
            val loadInitialStockIfNeeded = ::gen_loadInitialStockIfNeeded_fn
            fun gen_loadProductStocks_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        val response = await(getInventoryStocks(buildProductStockQuery()))
                        stocks.value = response.results
                        run {
                            var index: Number = 0
                            while(index < stocks.value.length){
                                updateProductFromStock(stocks.value[index])
                                index += 1
                            }
                        }
                        if (selectedStockId.value != "" && stockById(selectedStockId.value) == null && stocks.value.length > 0) {
                            selectedStockId.value = stringValue(stocks.value[0]["id"])
                        }
                })
            }
            val loadProductStocks = ::gen_loadProductStocks_fn
            fun gen_loadMovementRecords_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (productId.value == "" && selectedStockId.value == "") {
                            movementRecords.value = _uA<UTSJSONObject>()
                            movementRecordTotal.value = 0
                            return@w1
                        }
                        val response = await(getInventoryTransactions(buildMovementQuery()))
                        movementRecords.value = response.results
                        var count = response.total_count
                        if (count < response.results.length) {
                            count = response.results.length
                        }
                        movementRecordTotal.value = count
                })
            }
            val loadMovementRecords = ::gen_loadMovementRecords_fn
            fun gen_loadAll_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (isLoading.value) {
                            return@w1
                        }
                        isLoading.value = true
                        errorMessage.value = ""
                        try {
                            await(loadInitialStockIfNeeded())
                            await(loadProductStocks())
                            await(loadMovementRecords())
                        }
                         catch (error: Throwable) {
                            errorMessage.value = parseErrorMessage(error, "库存详情加载失败")
                        }
                         finally {
                            isLoading.value = false
                        }
                })
            }
            val loadAll = ::gen_loadAll_fn
            fun gen_resetCreateForm_fn() {
                createLocationValue.value = ""
                createLocationText.value = ""
                createQuantityText.value = ""
                createTypeValue.value = "INITIAL"
                createTypeText.value = optionText(createTypeOptions, "INITIAL", "初始库存")
                createUnitCostText.value = ""
                createNotesText.value = ""
            }
            val resetCreateForm = ::gen_resetCreateForm_fn
            fun gen_openCreateSheet_fn() {
                if (productId.value == "") {
                    uni_showToast(ShowToastOptions(title = "缺少商品ID", icon = "none", duration = 3500))
                    return
                }
                resetCreateForm()
                updateSheetLayout()
                createSheetVisible.value = true
            }
            val openCreateSheet = ::gen_openCreateSheet_fn
            fun gen_closeCreateSheet_fn() {
                if (createSubmitting.value) {
                    return
                }
                createSheetVisible.value = false
            }
            val closeCreateSheet = ::gen_closeCreateSheet_fn
            fun gen_openAdjustSheet_fn(stock: UTSJSONObject) {
                selectStock(stock)
                updateSheetLayout()
                adjustSheetVisible.value = true
            }
            val openAdjustSheet = ::gen_openAdjustSheet_fn
            fun gen_closeAdjustSheet_fn() {
                if (adjustSubmitting.value) {
                    return
                }
                adjustSheetVisible.value = false
            }
            val closeAdjustSheet = ::gen_closeAdjustSheet_fn
            fun gen_openSelectedAdjustSheet_fn() {
                val stock = selectedStock()
                if (stock == null) {
                    uni_showToast(ShowToastOptions(title = "请选择库存位置", icon = "none", duration = 3500))
                    return
                }
                openAdjustSheet(stock!!)
            }
            val openSelectedAdjustSheet = ::gen_openSelectedAdjustSheet_fn
            fun gen_openInitialSheetIfNeeded_fn() {
                if (initialSheetConsumed.value) {
                    return
                }
                if (initialMode.value == "create") {
                    initialSheetConsumed.value = true
                    openCreateSheet()
                    return
                }
                if (initialMode.value == "adjust" && selectedStockId.value != "") {
                    val stock = selectedStock()
                    if (stock == null) {
                        return
                    }
                    initialSheetConsumed.value = true
                    openAdjustSheet(stock!!)
                }
            }
            val openInitialSheetIfNeeded = ::gen_openInitialSheetIfNeeded_fn
            fun gen_buildCreateStockPayload_fn(): InventoryStockCreateForProductData? {
                val parsedProductId = parseInt(productId.value)
                if (isNaN(parsedProductId) || parsedProductId <= 0) {
                    uni_showToast(ShowToastOptions(title = "缺少商品ID", icon = "none", duration = 3500))
                    return null
                }
                val locationId = parseInt(createLocationValue.value)
                if (isNaN(locationId) || locationId <= 0) {
                    uni_showToast(ShowToastOptions(title = "请选择库存位置", icon = "none", duration = 3500))
                    return null
                }
                if (hasStockAtLocation(createLocationValue.value)) {
                    uni_showToast(ShowToastOptions(title = "该位置已有库存记录", icon = "none", duration = 3500))
                    return null
                }
                val quantity = if (createQuantityText.value == "") {
                    0
                } else {
                    parseInt(createQuantityText.value)
                }
                if (isNaN(quantity) || quantity < 0) {
                    uni_showToast(ShowToastOptions(title = "初始数量不能小于 0", icon = "none", duration = 3500))
                    return null
                }
                return InventoryStockCreateForProductData(product = parsedProductId, location = locationId, quantity = quantity, transaction_type = if (createTypeValue.value == "") {
                    "INITIAL"
                } else {
                    createTypeValue.value
                }
                , unit_cost = normalizedCostText(createUnitCostText.value), notes = createNotesText.value)
            }
            val buildCreateStockPayload = ::gen_buildCreateStockPayload_fn
            fun gen_submitCreateStock_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (createSubmitting.value) {
                            return@w1
                        }
                        val payload = buildCreateStockPayload()
                        if (payload == null) {
                            return@w1
                        }
                        createSubmitting.value = true
                        try {
                            val created = await(createInventoryStockForProduct(payload!!))
                            val createdText = JSON.stringify(created)
                            val createdObject = if (createdText == null || createdText == "") {
                                null
                            } else {
                                UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(createdText), " at pages/inventory-management/from.uvue:961")
                            }
                            if (createdObject != null) {
                                selectedStockId.value = stringValue(createdObject!!["id"])
                            }
                            markRefreshNeeded()
                            resetCreateForm()
                            createSheetVisible.value = false
                            uni_showToast(ShowToastOptions(title = takeLatestResponseMessage("库存创建成功"), icon = "success"))
                            await(loadAll())
                        }
                         catch (error: Throwable) {
                            showErrorToast(parseErrorMessage(error, "库存创建失败"))
                        }
                         finally {
                            createSubmitting.value = false
                        }
                })
            }
            val submitCreateStock = ::gen_submitCreateStock_fn
            fun gen_buildAdjustStockPayload_fn(): StockAdjustmentData? {
                val stockId = parseInt(selectedStockId.value)
                if (isNaN(stockId) || stockId <= 0) {
                    uni_showToast(ShowToastOptions(title = "请选择库存位置", icon = "none", duration = 3500))
                    return null
                }
                val change = parseInt(adjustQuantityText.value)
                if (isNaN(change) || change == 0) {
                    uni_showToast(ShowToastOptions(title = "请输入非 0 的调整数量", icon = "none", duration = 3500))
                    return null
                }
                return StockAdjustmentData(stock_id = stockId, quantity_change = change, transaction_type = if (adjustTypeValue.value == "") {
                    "ADJUSTMENT"
                } else {
                    adjustTypeValue.value
                }
                , unit_cost = normalizedCostText(adjustUnitCostText.value), notes = adjustNotesText.value)
            }
            val buildAdjustStockPayload = ::gen_buildAdjustStockPayload_fn
            fun gen_submitAdjustStock_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (adjustSubmitting.value) {
                            return@w1
                        }
                        val payload = buildAdjustStockPayload()
                        if (payload == null) {
                            return@w1
                        }
                        adjustSubmitting.value = true
                        try {
                            await(adjustInventoryStock(payload!!))
                            markRefreshNeeded()
                            adjustQuantityText.value = ""
                            adjustNotesText.value = ""
                            adjustSheetVisible.value = false
                            uni_showToast(ShowToastOptions(title = takeLatestResponseMessage("库存调整成功"), icon = "success"))
                            await(loadAll())
                        }
                         catch (error: Throwable) {
                            showErrorToast(parseErrorMessage(error, "库存调整失败"))
                        }
                         finally {
                            adjustSubmitting.value = false
                        }
                })
            }
            val submitAdjustStock = ::gen_submitAdjustStock_fn
            fun gen_handleCreateLocationChange_fn(payload: UTSJSONObject) {
                createLocationValue.value = stringValue(payload["value"])
                createLocationText.value = stringValue(payload["text"])
            }
            val handleCreateLocationChange = ::gen_handleCreateLocationChange_fn
            fun gen_handleCreateTypeChange_fn(payload: UTSJSONObject) {
                createTypeValue.value = stringValue(payload["value"], "INITIAL")
                createTypeText.value = stringValue(payload["text"], optionText(createTypeOptions, createTypeValue.value, "初始库存"))
            }
            val handleCreateTypeChange = ::gen_handleCreateTypeChange_fn
            fun gen_handleAdjustTypeChange_fn(payload: UTSJSONObject) {
                adjustTypeValue.value = stringValue(payload["value"], "ADJUSTMENT")
                adjustTypeText.value = stringValue(payload["text"], optionText(adjustTypeOptions, adjustTypeValue.value, "盘点调整"))
            }
            val handleAdjustTypeChange = ::gen_handleAdjustTypeChange_fn
            fun gen_handleCreateQuantityInput_fn(event: Any) {
                createQuantityText.value = positiveIntegerText(readInputValue(event))
            }
            val handleCreateQuantityInput = ::gen_handleCreateQuantityInput_fn
            fun gen_handleCreateUnitCostInput_fn(event: Any) {
                createUnitCostText.value = decimalText(readInputValue(event))
            }
            val handleCreateUnitCostInput = ::gen_handleCreateUnitCostInput_fn
            fun gen_handleCreateNotesInput_fn(event: Any) {
                createNotesText.value = readInputValue(event)
            }
            val handleCreateNotesInput = ::gen_handleCreateNotesInput_fn
            fun gen_handleAdjustQuantityInput_fn(event: Any) {
                adjustQuantityText.value = signedIntegerText(readInputValue(event))
            }
            val handleAdjustQuantityInput = ::gen_handleAdjustQuantityInput_fn
            fun gen_handleAdjustUnitCostInput_fn(event: Any) {
                adjustUnitCostText.value = decimalText(readInputValue(event))
            }
            val handleAdjustUnitCostInput = ::gen_handleAdjustUnitCostInput_fn
            fun gen_handleAdjustNotesInput_fn(event: Any) {
                adjustNotesText.value = readInputValue(event)
            }
            val handleAdjustNotesInput = ::gen_handleAdjustNotesInput_fn
            fun gen_movementTypeText_fn(record: UTSJSONObject): String {
                val display = stringValue(record["transaction_type_display"])
                if (display != "") {
                    return display
                }
                val type = stringValue(record["transaction_type"])
                if (type == "PURCHASE") {
                    return "采购入库"
                }
                if (type == "SALE") {
                    return "销售出库"
                }
                if (type == "RETURN_IN") {
                    return "退货入库"
                }
                if (type == "RETURN_OUT") {
                    return "退货出库"
                }
                if (type == "TRANSFER_IN") {
                    return "调拨入库"
                }
                if (type == "TRANSFER_OUT") {
                    return "调拨出库"
                }
                if (type == "ADJUSTMENT") {
                    return "盘点调整"
                }
                if (type == "DAMAGE") {
                    return "损坏"
                }
                if (type == "LOSS") {
                    return "丢失"
                }
                if (type == "INITIAL") {
                    return "初始库存"
                }
                return if (type == "") {
                    "库存变动"
                } else {
                    type
                }
            }
            val movementTypeText = ::gen_movementTypeText_fn
            fun gen_movementTitleText_fn(record: UTSJSONObject): String {
                val location = stringValue(record["location_name"], "未知位置")
                return movementTypeText(record) + " · " + location
            }
            val movementTitleText = ::gen_movementTitleText_fn
            fun gen_movementSubtitleText_fn(record: UTSJSONObject): String {
                val type = stringValue(record["transaction_type"])
                val orderNumber = stringValue(record["order_number"])
                if (orderNumber != "") {
                    return "订单 " + orderNumber
                }
                val notes = stringValue(record["notes"])
                if (notes != "") {
                    return notes
                }
                val referenceId = stringValue(record["reference_id"])
                val referenceType = stringValue(record["reference_type"])
                if (referenceId != "" && referenceType != "") {
                    return referenceType + " #" + referenceId
                }
                if (referenceId != "") {
                    return "关联记录 #" + referenceId
                }
                if (type == "SALE") {
                    return "未关联订单"
                }
                return "库存 " + numberText(record["quantity_before"]) + " -> " + numberText(record["quantity_after"])
            }
            val movementSubtitleText = ::gen_movementSubtitleText_fn
            fun gen_movementQuantityText_fn(record: UTSJSONObject): String {
                val value = intValue(record["quantity"])
                return signedNumberText(value)
            }
            val movementQuantityText = ::gen_movementQuantityText_fn
            fun gen_movementQuantityClass_fn(record: UTSJSONObject): String {
                val value = intValue(record["quantity"])
                if (value > 0) {
                    return "movement-quantity movement-quantity-in"
                }
                if (value < 0) {
                    return "movement-quantity movement-quantity-out"
                }
                return "movement-quantity"
            }
            val movementQuantityClass = ::gen_movementQuantityClass_fn
            fun gen_movementTimeText_fn(record: UTSJSONObject): String {
                val orderTime = stringValue(record["order_time"])
                if (orderTime != "") {
                    return dateTimeText(orderTime)
                }
                return dateTimeText(record["created_at"])
            }
            val movementTimeText = ::gen_movementTimeText_fn
            fun gen_movementActorText_fn(record: UTSJSONObject): String {
                val seller = stringValue(record["seller_name"])
                if (stringValue(record["transaction_type"]) == "SALE" && seller != "") {
                    return "销售人 " + seller
                }
                val cashierId = stringValue(record["cashier_id"])
                if (cashierId != "") {
                    return "收银员 " + cashierId
                }
                val userName = stringValue(record["created_by_name"])
                if (userName != "") {
                    return "操作人 " + userName
                }
                return "操作人 系统"
            }
            val movementActorText = ::gen_movementActorText_fn
            onLoad(fun(query: OnLoadOptions){
                val productValue = query["product"]
                productId.value = if (productValue == null) {
                    ""
                } else {
                    ("" + productValue)
                }
                val stockValue = query["stock"]
                if (stockValue == null) {
                    val oldIdValue = query["id"]
                    selectedStockId.value = if (oldIdValue == null) {
                        ""
                    } else {
                        ("" + oldIdValue)
                    }
                } else {
                    selectedStockId.value = "" + stockValue
                }
                val modeValue = query["mode"]
                initialMode.value = if (modeValue == null) {
                    ""
                } else {
                    ("" + modeValue)
                }
                val productNameValue = query["productName"]
                if (productNameValue == null) {
                    productName.value = ""
                } else {
                    productName.value = stringValue(UTSAndroid.consoleDebugError(decodeURIComponent("" + productNameValue), " at pages/inventory-management/from.uvue:1134"))
                }
                updateSheetLayout()
                if (initialMode.value == "adjust" && selectedStockId.value == "") {
                    uni_showToast(ShowToastOptions(title = "请选择库存位置", icon = "none", duration = 3500))
                }
                loadAll().then(fun(){
                    openInitialSheetIfNeeded()
                }
                )
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_page_container = resolveComponent("page-container")
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to pageTitle.value, "showBack" to true, "showSearch" to false, "showHome" to true, "homePath" to homePath.value, "backgroundColor" to "#F6F7FB"), null, 8, _uA(
                        "title",
                        "homePath"
                    )),
                    _cE("scroll-view", _uM("scroll-y" to "true", "style" to _nS(_uM("flex" to "1")), "class" to "page-scroll"), _uA(
                        _cE("view", _uM("class" to "page-content"), _uA(
                            if (isTrue(unref(errorMessage) != "" && !unref(isLoading))) {
                                _cE("view", _uM("key" to 0, "class" to "error-band"), _uA(
                                    _cE("text", _uM("class" to "error-title"), "加载失败"),
                                    _cE("text", _uM("class" to "error-desc"), _tD(unref(errorMessage)), 1),
                                    _cE("view", _uM("class" to "error-action", "onClick" to loadAll), _uA(
                                        _cE("text", _uM("class" to "error-action-text"), "重新加载")
                                    ))
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            _cE("view", _uM("class" to "product-band"), _uA(
                                _cE("view", _uM("class" to "product-main"), _uA(
                                    if (productImage.value != "") {
                                        _cE("image", _uM("key" to 0, "class" to "product-image", "src" to productImage.value, "mode" to "aspectFill"), null, 8, _uA(
                                            "src"
                                        ))
                                    } else {
                                        _cE("view", _uM("key" to 1, "class" to "product-image product-image-empty"), _uA(
                                            _cE("text", _uM("class" to "product-image-empty-text"), "货")
                                        ))
                                    }
                                    ,
                                    _cE("view", _uM("class" to "product-title-wrap"), _uA(
                                        _cE("text", _uM("class" to "product-title"), _tD(productTitle.value), 1),
                                        _cE("text", _uM("class" to "product-subtitle"), _tD(productSubtitle.value), 1)
                                    ))
                                )),
                                _cE("view", _uM("class" to "summary-grid"), _uA(
                                    _cE("view", _uM("class" to "summary-item"), _uA(
                                        _cE("text", _uM("class" to "summary-label"), "总库存"),
                                        _cE("text", _uM("class" to "summary-value"), _tD(totalQuantityText.value), 1)
                                    )),
                                    _cE("view", _uM("class" to "summary-item"), _uA(
                                        _cE("text", _uM("class" to "summary-label"), "可用"),
                                        _cE("text", _uM("class" to "summary-value"), _tD(totalAvailableText.value), 1)
                                    )),
                                    _cE("view", _uM("class" to "summary-item"), _uA(
                                        _cE("text", _uM("class" to "summary-label"), "位置"),
                                        _cE("text", _uM("class" to "summary-value"), _tD(stockLocationCountText.value), 1)
                                    )),
                                    _cE("view", _uM("class" to "summary-item"), _uA(
                                        _cE("text", _uM("class" to "summary-label"), "流水记录"),
                                        _cE("text", _uM("class" to "summary-value"), _tD(movementRecordCountText.value), 1)
                                    ))
                                ))
                            )),
                            _cE("view", _uM("class" to "section-head section-head-row"), _uA(
                                _cE("view", _uM("class" to "section-title-wrap"), _uA(
                                    _cE("text", _uM("class" to "section-title"), "多位置库存"),
                                    _cE("text", _uM("class" to "section-subtitle"), "点击一个位置调整库存")
                                )),
                                _cE("view", _uM("class" to "section-action", "onClick" to openCreateSheet), _uA(
                                    _cE("text", _uM("class" to "section-action-text"), "新增库存")
                                ))
                            )),
                            if (isTrue(unref(isLoading))) {
                                _cE("view", _uM("key" to 1, "class" to "empty-band"), _uA(
                                    _cE("text", _uM("class" to "empty-text"), "正在加载库存")
                                ))
                            } else {
                                if (unref(stocks).length == 0) {
                                    _cE("view", _uM("key" to 2, "class" to "empty-band"), _uA(
                                        _cE("text", _uM("class" to "empty-text"), "该商品还没有库存位置")
                                    ))
                                } else {
                                    _cC("v-if", true)
                                }
                            }
                            ,
                            _cE(Fragment, null, RenderHelpers.renderList(unref(stocks), fun(stock, __key, __index, _cached): Any {
                                return _cE("view", _uM("key" to ("stock-" + stringValue(stock["id"])), "class" to _nC(stockCardClass(stock)), "onClick" to fun(){
                                    openAdjustSheet(stock)
                                }
                                ), _uA(
                                    _cE("view", _uM("class" to "stock-row-top"), _uA(
                                        _cE("view", _uM("class" to "stock-location-wrap"), _uA(
                                            _cE("text", _uM("class" to "stock-location"), _tD(stringValue(stock["location_name"], "未设置库存位置")), 1),
                                            _cE("text", _uM("class" to "stock-location-sub"), _tD(locationTypeText(stock)), 1)
                                        )),
                                        _cE("text", _uM("class" to _nC(stockAlertClass(stock))), _tD(alertLabel(stringValue(stock["alert_status"]))), 3)
                                    )),
                                    _cE("view", _uM("class" to "stock-metrics"), _uA(
                                        _cE("view", _uM("class" to "stock-metric"), _uA(
                                            _cE("text", _uM("class" to "stock-metric-label"), "当前"),
                                            _cE("text", _uM("class" to "stock-metric-value"), _tD(numberText(stock["quantity"])), 1)
                                        )),
                                        _cE("view", _uM("class" to "stock-metric"), _uA(
                                            _cE("text", _uM("class" to "stock-metric-label"), "可用"),
                                            _cE("text", _uM("class" to "stock-metric-value"), _tD(numberText(stock["available_quantity"])), 1)
                                        )),
                                        _cE("view", _uM("class" to "stock-metric"), _uA(
                                            _cE("text", _uM("class" to "stock-metric-label"), "占用"),
                                            _cE("text", _uM("class" to "stock-metric-value"), _tD(numberText(stock["reserved_quantity"])), 1)
                                        )),
                                        _cE("view", _uM("class" to "stock-metric"), _uA(
                                            _cE("text", _uM("class" to "stock-metric-label"), "均价"),
                                            _cE("text", _uM("class" to "stock-metric-value stock-metric-money"), _tD(moneyText(stock["average_cost"])), 1)
                                        ))
                                    )),
                                    _cE("view", _uM("class" to "stock-row-bottom"), _uA(
                                        _cE("text", _uM("class" to "stock-tag"), _tD(listedLabel(stringValue(stock["is_listed"]))), 1),
                                        _cE("text", _uM("class" to "stock-muted"), "最近变动 " + _tD(dateTimeText(stock["last_movement_at"])), 1)
                                    ))
                                ), 10, _uA(
                                    "onClick"
                                ))
                            }
                            ), 128),
                            _cE("view", _uM("class" to "section-head"), _uA(
                                _cE("text", _uM("class" to "section-title"), "库存变动记录"),
                                _cE("text", _uM("class" to "section-subtitle"), "包含订单销售、手动调整、采购入库等库存流水")
                            )),
                            if (unref(movementRecords).length == 0) {
                                _cE("view", _uM("key" to 3, "class" to "empty-band"), _uA(
                                    _cE("text", _uM("class" to "empty-text"), "暂无库存变动记录")
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            _cE(Fragment, null, RenderHelpers.renderList(unref(movementRecords), fun(record, __key, __index, _cached): Any {
                                return _cE("view", _uM("key" to ("movement-" + stringValue(record["id"])), "class" to "movement-row"), _uA(
                                    _cE("view", _uM("class" to "movement-row-top"), _uA(
                                        _cE("view", _uM("class" to "movement-main"), _uA(
                                            _cE("text", _uM("class" to "movement-title"), _tD(movementTitleText(record)), 1),
                                            _cE("text", _uM("class" to "movement-subtitle"), _tD(movementSubtitleText(record)), 1)
                                        )),
                                        _cE("text", _uM("class" to _nC(movementQuantityClass(record))), _tD(movementQuantityText(record)), 3)
                                    )),
                                    _cE("view", _uM("class" to "movement-row-bottom"), _uA(
                                        _cE("text", _uM("class" to "movement-meta"), _tD(movementTimeText(record)), 1),
                                        _cE("text", _uM("class" to "movement-meta"), _tD(movementActorText(record)), 1)
                                    ))
                                ))
                            }
                            ), 128)
                        ))
                    ), 4),
                    _cV(_component_page_container, _uM("show" to unref(createSheetVisible), "position" to "bottom", "round" to true, "overlay" to true, "duration" to 240, "overlay-style" to "background-color: rgba(15, 23, 42, 0.42);", "custom-style" to "background-color: #FFFFFF;", "onClickoverlay" to closeCreateSheet), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                        return _uA(
                            _cE("view", _uM("class" to "sheet-panel", "style" to _nS(sheetPanelStyle.value)), _uA(
                                _cE("view", _uM("class" to "sheet-handle-wrap"), _uA(
                                    _cE("view", _uM("class" to "sheet-handle"))
                                )),
                                _cE("view", _uM("class" to "sheet-header"), _uA(
                                    _cE("view", _uM("class" to "sheet-title-wrap"), _uA(
                                        _cE("text", _uM("class" to "sheet-title"), "新增库存"),
                                        _cE("text", _uM("class" to "sheet-subtitle"), _tD(productTitle.value), 1)
                                    )),
                                    _cE("view", _uM("class" to "sheet-close", "onClick" to closeCreateSheet), _uA(
                                        _cE("text", _uM("class" to "sheet-close-text"), "×")
                                    ))
                                )),
                                _cE("scroll-view", _uM("scroll-y" to "true", "class" to "sheet-scroll", "style" to _nS(sheetScrollStyle.value)), _uA(
                                    _cE("view", _uM("class" to "field"), _uA(
                                        _cE("text", _uM("class" to "field-label"), "库存位置"),
                                        _cV(unref(GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelectClass), _uM("value" to unref(createLocationValue), "valueText" to unref(createLocationText), "title" to "选择库存位置", "placeholder" to "请选择库存位置", "searchPlaceholder" to "搜索库存位置", "emptyText" to "暂无可用库存位置", "fetchData" to fetchLocationOptions, "showAddAction" to false, "showEditAction" to false, "onChange" to handleCreateLocationChange), null, 8, _uA(
                                            "value",
                                            "valueText"
                                        ))
                                    )),
                                    _cE("view", _uM("class" to "field-row"), _uA(
                                        _cE("view", _uM("class" to "field-half"), _uA(
                                            _cE("text", _uM("class" to "field-label"), "初始数量"),
                                            _cE("input", _uM("class" to "input", "type" to "number", "value" to unref(createQuantityText), "placeholder" to "0", "onInput" to handleCreateQuantityInput), null, 40, _uA(
                                                "value"
                                            ))
                                        )),
                                        _cE("view", _uM("class" to "field-half field-half-right"), _uA(
                                            _cE("text", _uM("class" to "field-label"), "单位成本"),
                                            _cE("input", _uM("class" to "input", "type" to "digit", "value" to unref(createUnitCostText), "placeholder" to "0.00", "onInput" to handleCreateUnitCostInput), null, 40, _uA(
                                                "value"
                                            ))
                                        ))
                                    )),
                                    _cE("view", _uM("class" to "field"), _uA(
                                        _cE("text", _uM("class" to "field-label"), "入库类型"),
                                        _cV(unref(GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelectClass), _uM("value" to unref(createTypeValue), "valueText" to unref(createTypeText), "title" to "选择入库类型", "placeholder" to "请选择入库类型", "fetchData" to fetchCreateTypeOptions, "showAddAction" to false, "showEditAction" to false, "onChange" to handleCreateTypeChange), null, 8, _uA(
                                            "value",
                                            "valueText"
                                        ))
                                    )),
                                    _cE("view", _uM("class" to "field"), _uA(
                                        _cE("text", _uM("class" to "field-label"), "备注"),
                                        _cE("textarea", _uM("class" to "textarea", "value" to unref(createNotesText), "placeholder" to "请输入备注", "onInput" to handleCreateNotesInput), null, 40, _uA(
                                            "value"
                                        ))
                                    ))
                                ), 4),
                                _cE("view", _uM("class" to "sheet-actions"), _uA(
                                    _cE("view", _uM("class" to "sheet-btn sheet-btn-light", "onClick" to closeCreateSheet), _uA(
                                        _cE("text", _uM("class" to "sheet-btn-light-text"), "取消")
                                    )),
                                    _cE("view", _uM("class" to "sheet-btn sheet-btn-primary", "onClick" to submitCreateStock), _uA(
                                        _cE("text", _uM("class" to "sheet-btn-primary-text"), _tD(createButtonText.value), 1)
                                    ))
                                ))
                            ), 4)
                        )
                    }
                    ), "_" to 1), 8, _uA(
                        "show"
                    )),
                    _cV(_component_page_container, _uM("show" to unref(adjustSheetVisible), "position" to "bottom", "round" to true, "overlay" to true, "duration" to 240, "overlay-style" to "background-color: rgba(15, 23, 42, 0.42);", "custom-style" to "background-color: #FFFFFF;", "onClickoverlay" to closeAdjustSheet), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                        return _uA(
                            _cE("view", _uM("class" to "sheet-panel", "style" to _nS(sheetPanelStyle.value)), _uA(
                                _cE("view", _uM("class" to "sheet-handle-wrap"), _uA(
                                    _cE("view", _uM("class" to "sheet-handle"))
                                )),
                                _cE("view", _uM("class" to "sheet-header"), _uA(
                                    _cE("view", _uM("class" to "sheet-title-wrap"), _uA(
                                        _cE("text", _uM("class" to "sheet-title"), "调整库存"),
                                        _cE("text", _uM("class" to "sheet-subtitle"), _tD(selectedStockHint.value), 1)
                                    )),
                                    _cE("view", _uM("class" to "sheet-close", "onClick" to closeAdjustSheet), _uA(
                                        _cE("text", _uM("class" to "sheet-close-text"), "×")
                                    ))
                                )),
                                _cE("scroll-view", _uM("scroll-y" to "true", "class" to "sheet-scroll", "style" to _nS(sheetScrollStyle.value)), _uA(
                                    _cE("view", _uM("class" to "adjust-preview"), _uA(
                                        _cE("view", _uM("class" to "adjust-preview-item"), _uA(
                                            _cE("text", _uM("class" to "adjust-preview-label"), "变动前"),
                                            _cE("text", _uM("class" to "adjust-preview-value"), _tD(adjustBeforeText.value), 1)
                                        )),
                                        _cE("view", _uM("class" to "adjust-preview-item"), _uA(
                                            _cE("text", _uM("class" to "adjust-preview-label"), "本次变化"),
                                            _cE("text", _uM("class" to _nC(adjustChangeClass.value)), _tD(adjustChangeText.value), 3)
                                        )),
                                        _cE("view", _uM("class" to "adjust-preview-item"), _uA(
                                            _cE("text", _uM("class" to "adjust-preview-label"), "预计调整后"),
                                            _cE("text", _uM("class" to "adjust-preview-value adjust-preview-strong"), _tD(adjustAfterText.value), 1)
                                        ))
                                    )),
                                    _cE("view", _uM("class" to "field adjust-quantity-field"), _uA(
                                        _cE("view", _uM("class" to "field-title-row"), _uA(
                                            _cE("text", _uM("class" to "field-label"), "调整数量"),
                                            _cE("text", _uM("class" to "field-help"), "正数入库，负数出库")
                                        )),
                                        _cE("view", _uM("class" to "quantity-input-row"), _uA(
                                            _cE("view", _uM("class" to "quantity-step quantity-step-out", "onClick" to fun(){
                                                stepAdjustQuantity(-1)
                                            }
                                            ), _uA(
                                                _cE("text", _uM("class" to "quantity-step-text"), "-")
                                            ), 8, _uA(
                                                "onClick"
                                            )),
                                            _cE("input", _uM("class" to "quantity-input", "type" to "number", "value" to unref(adjustQuantityText), "placeholder" to "0", "onInput" to handleAdjustQuantityInput), null, 40, _uA(
                                                "value"
                                            )),
                                            _cE("view", _uM("class" to "quantity-step quantity-step-in", "onClick" to fun(){
                                                stepAdjustQuantity(1)
                                            }
                                            ), _uA(
                                                _cE("text", _uM("class" to "quantity-step-text"), "+")
                                            ), 8, _uA(
                                                "onClick"
                                            ))
                                        )),
                                        _cE("view", _uM("class" to "quantity-quick-row"), _uA(
                                            _cE("view", _uM("class" to "quantity-quick quantity-quick-out", "onClick" to fun(){
                                                setAdjustQuantityQuick(-10)
                                            }
                                            ), _uA(
                                                _cE("text", _uM("class" to "quantity-quick-out-text"), "-10")
                                            ), 8, _uA(
                                                "onClick"
                                            )),
                                            _cE("view", _uM("class" to "quantity-quick quantity-quick-out", "onClick" to fun(){
                                                setAdjustQuantityQuick(-5)
                                            }
                                            ), _uA(
                                                _cE("text", _uM("class" to "quantity-quick-out-text"), "-5")
                                            ), 8, _uA(
                                                "onClick"
                                            )),
                                            _cE("view", _uM("class" to "quantity-quick quantity-quick-out", "onClick" to fun(){
                                                setAdjustQuantityQuick(-1)
                                            }
                                            ), _uA(
                                                _cE("text", _uM("class" to "quantity-quick-out-text"), "-1")
                                            ), 8, _uA(
                                                "onClick"
                                            )),
                                            _cE("view", _uM("class" to "quantity-quick quantity-quick-in", "onClick" to fun(){
                                                setAdjustQuantityQuick(1)
                                            }
                                            ), _uA(
                                                _cE("text", _uM("class" to "quantity-quick-in-text"), "+1")
                                            ), 8, _uA(
                                                "onClick"
                                            )),
                                            _cE("view", _uM("class" to "quantity-quick quantity-quick-in", "onClick" to fun(){
                                                setAdjustQuantityQuick(5)
                                            }
                                            ), _uA(
                                                _cE("text", _uM("class" to "quantity-quick-in-text"), "+5")
                                            ), 8, _uA(
                                                "onClick"
                                            )),
                                            _cE("view", _uM("class" to "quantity-quick quantity-quick-in quantity-quick-last", "onClick" to fun(){
                                                setAdjustQuantityQuick(10)
                                            }
                                            ), _uA(
                                                _cE("text", _uM("class" to "quantity-quick-in-text"), "+10")
                                            ), 8, _uA(
                                                "onClick"
                                            ))
                                        ))
                                    )),
                                    _cE("view", _uM("class" to "field"), _uA(
                                        _cE("text", _uM("class" to "field-label"), "单位成本"),
                                        _cE("input", _uM("class" to "input", "type" to "digit", "value" to unref(adjustUnitCostText), "placeholder" to "0.00", "onInput" to handleAdjustUnitCostInput), null, 40, _uA(
                                            "value"
                                        ))
                                    )),
                                    _cE("view", _uM("class" to "field"), _uA(
                                        _cE("text", _uM("class" to "field-label"), "调整类型"),
                                        _cV(unref(GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelectClass), _uM("value" to unref(adjustTypeValue), "valueText" to unref(adjustTypeText), "title" to "选择调整类型", "placeholder" to "请选择调整类型", "fetchData" to fetchAdjustTypeOptions, "showAddAction" to false, "showEditAction" to false, "onChange" to handleAdjustTypeChange), null, 8, _uA(
                                            "value",
                                            "valueText"
                                        ))
                                    )),
                                    _cE("view", _uM("class" to "field"), _uA(
                                        _cE("text", _uM("class" to "field-label"), "备注"),
                                        _cE("textarea", _uM("class" to "textarea", "value" to unref(adjustNotesText), "placeholder" to "请输入调整原因", "onInput" to handleAdjustNotesInput), null, 40, _uA(
                                            "value"
                                        ))
                                    ))
                                ), 4),
                                _cE("view", _uM("class" to "sheet-actions"), _uA(
                                    _cE("view", _uM("class" to "sheet-btn sheet-btn-light", "onClick" to closeAdjustSheet), _uA(
                                        _cE("text", _uM("class" to "sheet-btn-light-text"), "取消")
                                    )),
                                    _cE("view", _uM("class" to "sheet-btn sheet-btn-primary", "onClick" to submitAdjustStock), _uA(
                                        _cE("text", _uM("class" to "sheet-btn-primary-text"), _tD(adjustButtonText.value), 1)
                                    ))
                                ))
                            ), 4)
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
                styles0,
                styles1
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-scroll" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-content" to _pS(_uM("paddingTop" to 8, "paddingRight" to 8, "paddingBottom" to 96, "paddingLeft" to 8)), "error-band" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FECACA", "borderRightColor" to "#FECACA", "borderBottomColor" to "#FECACA", "borderLeftColor" to "#FECACA", "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 14, "paddingLeft" to 14, "marginBottom" to 10, "alignItems" to "center")), "error-title" to _pS(_uM("fontSize" to 16, "lineHeight" to "22px", "color" to "#B42318", "fontWeight" to "bold")), "error-desc" to _pS(_uM("marginTop" to 6, "fontSize" to 13, "lineHeight" to "18px", "color" to "#7F1D1D", "textAlign" to "center")), "error-action" to _pS(_uM("marginTop" to 12, "height" to 38, "paddingLeft" to 18, "paddingRight" to 18, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#0F172A", "alignItems" to "center", "justifyContent" to "center")), "error-action-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#FFFFFF", "fontWeight" to "bold")), "product-band" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "marginBottom" to 12)), "product-main" to _pS(_uM("flexDirection" to "row", "alignItems" to "center")), "product-image" to _pS(_uM("width" to 56, "height" to 56, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#E2E8F0")), "product-image-empty" to _pS(_uM("alignItems" to "center", "justifyContent" to "center")), "product-image-empty-text" to _pS(_uM("fontSize" to 18, "lineHeight" to "24px", "color" to "#64748B", "fontWeight" to "bold")), "product-title-wrap" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "marginLeft" to 10)), "product-title" to _pS(_uM("fontSize" to 18, "lineHeight" to "24px", "color" to "#0F172A", "fontWeight" to "bold")), "product-subtitle" to _pS(_uM("marginTop" to 4, "fontSize" to 12, "lineHeight" to "17px", "color" to "#64748B")), "summary-grid" to _pS(_uM("flexDirection" to "row", "marginTop" to 12)), "summary-item" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "minHeight" to 58, "backgroundColor" to "#F8FAFC", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "alignItems" to "center", "justifyContent" to "center", "marginRight" to 6)), "summary-label" to _pS(_uM("fontSize" to 11, "lineHeight" to "15px", "color" to "#64748B")), "summary-value" to _pS(_uM("marginTop" to 4, "fontSize" to 18, "lineHeight" to "24px", "color" to "#0F172A", "fontWeight" to "bold")), "section-head" to _pS(_uM("marginTop" to 6, "marginBottom" to 8)), "section-head-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between")), "section-title-wrap" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingRight" to 8)), "section-title" to _pS(_uM("fontSize" to 16, "lineHeight" to "22px", "color" to "#0F172A", "fontWeight" to "bold")), "section-subtitle" to _pS(_uM("marginTop" to 3, "fontSize" to 12, "lineHeight" to "17px", "color" to "#64748B")), "section-action" to _pS(_uM("height" to 36, "paddingLeft" to 12, "paddingRight" to 12, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#0F172A", "alignItems" to "center", "justifyContent" to "center")), "section-action-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#FFFFFF", "fontWeight" to "bold")), "empty-band" to _pS(_uM("minHeight" to 62, "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "alignItems" to "center", "justifyContent" to "center", "marginBottom" to 10)), "empty-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#64748B")), "stock-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "marginBottom" to 8)), "stock-card-active" to _pS(_uM("borderTopColor" to "#0F172A", "borderRightColor" to "#0F172A", "borderBottomColor" to "#0F172A", "borderLeftColor" to "#0F172A", "backgroundColor" to "#F8FAFC")), "stock-row-top" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between")), "stock-location-wrap" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingRight" to 8)), "stock-location" to _pS(_uM("fontSize" to 15, "lineHeight" to "21px", "color" to "#0F172A", "fontWeight" to "bold")), "stock-location-sub" to _pS(_uM("marginTop" to 3, "fontSize" to 12, "lineHeight" to "17px", "color" to "#64748B")), "stock-alert" to _pS(_uM("height" to 24, "lineHeight" to "24px", "paddingLeft" to 8, "paddingRight" to 8, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#E8F7EF", "color" to "#047857", "fontSize" to 11)), "stock-alert-warning" to _pS(_uM("backgroundColor" to "#FFF7ED", "color" to "#B45309")), "stock-metrics" to _pS(_uM("flexDirection" to "row", "marginTop" to 10)), "stock-metric" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "minHeight" to 50, "alignItems" to "center", "justifyContent" to "center", "borderRightWidth" to 1, "borderRightStyle" to "solid", "borderRightColor" to "#E2E8F0")), "stock-metric-label" to _pS(_uM("fontSize" to 11, "lineHeight" to "15px", "color" to "#64748B")), "stock-metric-value" to _pS(_uM("marginTop" to 3, "fontSize" to 16, "lineHeight" to "22px", "color" to "#0F172A", "fontWeight" to "bold")), "stock-metric-money" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px")), "stock-row-bottom" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginTop" to 10)), "stock-tag" to _pS(_uM("height" to 24, "lineHeight" to "24px", "paddingLeft" to 8, "paddingRight" to 8, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#EEF2FF", "color" to "#334155", "fontSize" to 11, "marginRight" to 8)), "stock-muted" to _pS(_uM("fontSize" to 12, "lineHeight" to "17px", "color" to "#64748B")), "field" to _pS(_uM("marginBottom" to 10)), "field-row" to _pS(_uM("flexDirection" to "row", "marginBottom" to 10)), "field-half" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "field-half-right" to _pS(_uM("marginLeft" to 8)), "field-label" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#0F172A", "fontWeight" to "bold", "marginBottom" to 6)), "input" to _pS(_uM("height" to 42, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#F8FAFC", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#CBD5E1", "borderRightColor" to "#CBD5E1", "borderBottomColor" to "#CBD5E1", "borderLeftColor" to "#CBD5E1", "paddingLeft" to 10, "paddingRight" to 10, "fontSize" to 14, "color" to "#0F172A")), "textarea" to _pS(_uM("height" to 82, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#F8FAFC", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#CBD5E1", "borderRightColor" to "#CBD5E1", "borderBottomColor" to "#CBD5E1", "borderLeftColor" to "#CBD5E1", "paddingTop" to 10, "paddingRight" to 10, "paddingBottom" to 10, "paddingLeft" to 10, "fontSize" to 14, "color" to "#0F172A")), "field-title-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "marginBottom" to 6)), "field-help" to _pS(_uM("fontSize" to 12, "lineHeight" to "17px", "color" to "#64748B")), "adjust-quantity-field" to _pS(_uM("backgroundColor" to "#FFFFFF")), "quantity-input-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "center")), "quantity-step" to _pS(_uM("width" to 46, "height" to 46, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "alignItems" to "center", "justifyContent" to "center")), "quantity-step-out" to _pS(_uM("backgroundColor" to "#FEF2F2", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FCA5A5", "borderRightColor" to "#FCA5A5", "borderBottomColor" to "#FCA5A5", "borderLeftColor" to "#FCA5A5")), "quantity-step-in" to _pS(_uM("backgroundColor" to "#E8F7EF", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#86EFAC", "borderRightColor" to "#86EFAC", "borderBottomColor" to "#86EFAC", "borderLeftColor" to "#86EFAC")), "quantity-step-text" to _pS(_uM("fontSize" to 24, "lineHeight" to "28px", "color" to "#0F172A", "fontWeight" to "bold")), "quantity-input" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 46, "marginLeft" to 8, "marginRight" to 8, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#F8FAFC", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#CBD5E1", "borderRightColor" to "#CBD5E1", "borderBottomColor" to "#CBD5E1", "borderLeftColor" to "#CBD5E1", "textAlign" to "center", "fontSize" to 18, "color" to "#0F172A", "fontWeight" to "bold")), "quantity-quick-row" to _pS(_uM("flexDirection" to "row", "marginTop" to 8)), "quantity-quick" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 34, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "alignItems" to "center", "justifyContent" to "center", "marginRight" to 5)), "quantity-quick-out" to _pS(_uM("backgroundColor" to "#FFF7ED", "borderTopColor" to "#FDBA74", "borderRightColor" to "#FDBA74", "borderBottomColor" to "#FDBA74", "borderLeftColor" to "#FDBA74")), "quantity-quick-in" to _pS(_uM("backgroundColor" to "#F0FDF4", "borderTopColor" to "#86EFAC", "borderRightColor" to "#86EFAC", "borderBottomColor" to "#86EFAC", "borderLeftColor" to "#86EFAC")), "quantity-quick-last" to _pS(_uM("marginRight" to 0)), "quantity-quick-out-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "17px", "color" to "#B42318", "fontWeight" to "bold")), "quantity-quick-in-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "17px", "color" to "#047857", "fontWeight" to "bold")), "adjust-preview" to _pS(_uM("flexDirection" to "row", "backgroundColor" to "#F8FAFC", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 10, "paddingBottom" to 10, "marginBottom" to 10)), "adjust-preview-item" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "alignItems" to "center", "borderRightWidth" to 1, "borderRightStyle" to "solid", "borderRightColor" to "#E2E8F0")), "adjust-preview-label" to _pS(_uM("fontSize" to 11, "lineHeight" to "15px", "color" to "#64748B")), "adjust-preview-value" to _pS(_uM("marginTop" to 4, "fontSize" to 16, "lineHeight" to "22px", "color" to "#0F172A", "fontWeight" to "bold")), "adjust-preview-in" to _pS(_uM("color" to "#047857")), "adjust-preview-out" to _pS(_uM("color" to "#B42318")), "adjust-preview-strong" to _pS(_uM("color" to "#0F172A")), "movement-row" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "marginBottom" to 8)), "movement-row-top" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between")), "movement-main" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingRight" to 8)), "movement-title" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#0F172A", "fontWeight" to "bold")), "movement-subtitle" to _pS(_uM("marginTop" to 3, "fontSize" to 12, "lineHeight" to "17px", "color" to "#64748B")), "movement-quantity" to _pS(_uM("fontSize" to 15, "lineHeight" to "21px", "color" to "#0F172A", "fontWeight" to "bold")), "movement-quantity-in" to _pS(_uM("color" to "#047857")), "movement-quantity-out" to _pS(_uM("color" to "#B42318")), "movement-row-bottom" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "marginTop" to 10, "paddingTop" to 8, "borderTopWidth" to 1, "borderTopStyle" to "solid", "borderTopColor" to "#E2E8F0")), "movement-meta" to _pS(_uM("fontSize" to 12, "lineHeight" to "17px", "color" to "#475569")), "sheet-panel" to _pS(_uM("backgroundColor" to "#FFFFFF", "paddingLeft" to 14, "paddingRight" to 14, "paddingBottom" to 12, "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0")), "sheet-handle-wrap" to _pS(_uM("height" to 22, "alignItems" to "center", "justifyContent" to "center")), "sheet-handle" to _pS(_uM("width" to 42, "height" to 4, "borderTopLeftRadius" to 2, "borderTopRightRadius" to 2, "borderBottomRightRadius" to 2, "borderBottomLeftRadius" to 2, "backgroundColor" to "#CBD5E1")), "sheet-header" to _pS(_uM("minHeight" to 56, "flexDirection" to "row", "alignItems" to "center", "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#E2E8F0", "paddingBottom" to 10)), "sheet-title-wrap" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingRight" to 10)), "sheet-title" to _pS(_uM("fontSize" to 17, "lineHeight" to "23px", "color" to "#0F172A", "fontWeight" to "bold")), "sheet-subtitle" to _pS(_uM("marginTop" to 3, "fontSize" to 12, "lineHeight" to "17px", "color" to "#64748B")), "sheet-close" to _pS(_uM("width" to 38, "height" to 38, "borderTopLeftRadius" to 19, "borderTopRightRadius" to 19, "borderBottomRightRadius" to 19, "borderBottomLeftRadius" to 19, "backgroundColor" to "#F8FAFC", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "alignItems" to "center", "justifyContent" to "center")), "sheet-close-text" to _pS(_uM("fontSize" to 24, "lineHeight" to "24px", "color" to "#64748B", "fontWeight" to "bold")), "sheet-scroll" to _pS(_uM("paddingTop" to 12, "paddingBottom" to 12)), "sheet-actions" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "paddingTop" to 10, "borderTopWidth" to 1, "borderTopStyle" to "solid", "borderTopColor" to "#E2E8F0")), "sheet-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 42, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "alignItems" to "center", "justifyContent" to "center")), "sheet-btn-light" to _pS(_uM("backgroundColor" to "#F8FAFC", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#CBD5E1", "borderRightColor" to "#CBD5E1", "borderBottomColor" to "#CBD5E1", "borderLeftColor" to "#CBD5E1", "marginRight" to 8)), "sheet-btn-primary" to _pS(_uM("backgroundColor" to "#0F172A", "marginLeft" to 8)), "sheet-btn-light-text" to _pS(_uM("fontSize" to 14, "lineHeight" to "19px", "color" to "#334155", "fontWeight" to "bold")))
            }
        val styles1: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("sheet-btn-primary-text" to _pS(_uM("fontSize" to 14, "lineHeight" to "19px", "color" to "#FFFFFF", "fontWeight" to "bold")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
