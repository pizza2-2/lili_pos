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
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.redirectTo as uni_redirectTo
import io.dcloud.uniapp.extapi.setClipboardData as uni_setClipboardData
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesOrdersFrom : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesOrdersFrom) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesOrdersFrom
            val _cache = __ins.renderCache
            val orderId = ref("")
            val orderDetail = ref<OrderItem?>(null)
            val isLoading = ref(false)
            val errorMessage = ref("")
            val navShellStyle = ref("")
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
            fun gen_displayText_fn(value: String): String {
                if (value == "") {
                    return "-"
                }
                return value
            }
            val displayText = ::gen_displayText_fn
            fun gen_parseErrorMessage_fn(error: Any, fallback: String): String {
                var message = fallback
                if (error != null) {
                    val errorText = JSON.stringify(error)
                    if (errorText != null && errorText != "") {
                        val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/orders/from.uvue:191")
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
            fun gen_parseObjectArray_fn(value: Any?): UTSArray<UTSJSONObject> {
                if (value == null) {
                    return _uA<UTSJSONObject>()
                }
                val text = JSON.stringify(value)
                if (text == null || text == "") {
                    return _uA<UTSJSONObject>()
                }
                val parsed = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(text), " at pages/orders/from.uvue:209")
                if (parsed == null) {
                    return _uA<UTSJSONObject>()
                }
                return parsed!!
            }
            val parseObjectArray = ::gen_parseObjectArray_fn
            fun gen_parseObject_fn(value: Any?): UTSJSONObject? {
                if (value == null) {
                    return null
                }
                val text = JSON.stringify(value)
                if (text == null || text == "") {
                    return null
                }
                return UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(text), " at pages/orders/from.uvue:218")
            }
            val parseObject = ::gen_parseObject_fn
            fun gen_boolText_fn(value: Boolean): String {
                return if (value) {
                    "是"
                } else {
                    "否"
                }
            }
            val boolText = ::gen_boolText_fn
            fun gen_statusText_fn(item: OrderItem): String {
                val text = stringValue(item.status_display, item.status)
                if (text != "") {
                    return text
                }
                if (item.status == "received") {
                    return "已接收"
                }
                if (item.status == "processed") {
                    return "已处理"
                }
                if (item.status == "failed") {
                    return "处理失败"
                }
                return "-"
            }
            val statusText = ::gen_statusText_fn
            fun gen_paymentText_fn(item: OrderItem): String {
                val text = stringValue(item.payment_method_display, item.payment_method)
                if (text != "") {
                    return text
                }
                if (item.payment_method == "cash") {
                    return "现金"
                }
                if (item.payment_method == "card") {
                    return "银行卡"
                }
                if (item.payment_method == "mixed") {
                    return "混合支付"
                }
                if (item.payment_method == "other") {
                    return "其他"
                }
                return "-"
            }
            val paymentText = ::gen_paymentText_fn
            fun gen_compactDate_fn(value: String): String {
                if (value == "") {
                    return "-"
                }
                if (value.length >= 16) {
                    return value.substring(0, 16)
                }
                return value
            }
            val compactDate = ::gen_compactDate_fn
            fun gen_currencyText_fn(value: String): String {
                return stringValue(value, "0.00")
            }
            val currencyText = ::gen_currencyText_fn
            fun gen_payloadTextValue_fn(payload: UTSJSONObject): String {
                val text = JSON.stringify(payload)
                if (text == null || text == "") {
                    return "{}"
                }
                return text
            }
            val payloadTextValue = ::gen_payloadTextValue_fn
            fun gen_payloadOrderData_fn(payload: UTSJSONObject): UTSJSONObject {
                val snake = parseObject(payload["order_data"])
                if (snake != null) {
                    return snake!!
                }
                val camel = parseObject(payload["orderData"])
                if (camel != null) {
                    return camel!!
                }
                return payload
            }
            val payloadOrderData = ::gen_payloadOrderData_fn
            fun gen_getPayloadItems_fn(payload: UTSJSONObject): UTSArray<UTSJSONObject> {
                val orderData = payloadOrderData(payload)
                var rows = parseObjectArray(orderData["items"])
                if (rows.length > 0) {
                    return rows
                }
                rows = parseObjectArray(orderData["products"])
                if (rows.length > 0) {
                    return rows
                }
                rows = parseObjectArray(orderData["cart"])
                if (rows.length > 0) {
                    return rows
                }
                rows = parseObjectArray(payload["items"])
                if (rows.length > 0) {
                    return rows
                }
                rows = parseObjectArray(payload["products"])
                if (rows.length > 0) {
                    return rows
                }
                return parseObjectArray(payload["cart"])
            }
            val getPayloadItems = ::gen_getPayloadItems_fn
            fun gen_buildRowTitle_fn(row: UTSJSONObject, index: Number): String {
                val name = stringValue(row["name"], stringValue(row["product_name"], stringValue(row["title"])))
                if (name != "") {
                    return name
                }
                val barcode = stringValue(row["barcode"])
                if (barcode != "") {
                    return "商品 " + barcode
                }
                return "商品行 " + (index + 1).toString(10)
            }
            val buildRowTitle = ::gen_buildRowTitle_fn
            fun gen_buildRowDesc_fn(row: UTSJSONObject): String {
                val price = stringValue(row["price"], stringValue(row["unit_price"], "-"))
                val tax = stringValue(row["taxRate"], stringValue(row["tax_rate"], stringValue(row["vat_rate"], "-")))
                val discount = stringValue(row["discountAmount"], stringValue(row["discount"], stringValue(row["discount_amount"], "-")))
                return "单价 " + price + " / 税率 " + tax + " / 折扣 " + discount
            }
            val buildRowDesc = ::gen_buildRowDesc_fn
            fun gen_addSummaryRow_fn(rows: UTSArray<UTSJSONObject>, key: String, label: String, value: String) {
                if (value == "") {
                    return
                }
                rows.push(_uO("key" to key, "label" to label, "value" to value))
            }
            val addSummaryRow = ::gen_addSummaryRow_fn
            fun gen_buildPayloadSummary_fn(payload: UTSJSONObject): UTSArray<UTSJSONObject> {
                val rows: UTSArray<UTSJSONObject> = _uA()
                val orderData = payloadOrderData(payload)
                val payment = parseObject(orderData["payment"])
                addSummaryRow(rows, "order_number", "订单号", stringValue(payload["order_number"], stringValue(orderData["order_number"], stringValue(orderData["orderNumber"]))))
                addSummaryRow(rows, "cashier_id", "收银员", stringValue(payload["cashier_id"], stringValue(orderData["cashier_id"])))
                addSummaryRow(rows, "cashier_name", "收银员姓名", stringValue(payload["cashier_name"], stringValue(orderData["cashier_name"])))
                addSummaryRow(rows, "kasa_number", "收银台", stringValue(payload["kasa_number"], stringValue(orderData["kasa_number"])))
                addSummaryRow(rows, "payment_method", "支付方式", if (payment == null) {
                    stringValue(payload["payment_method"], stringValue(orderData["payment_method"]))
                } else {
                    stringValue(payment!!["method"])
                }
                )
                addSummaryRow(rows, "subtotal", "Payload 小计", stringValue(orderData["subtotal"], stringValue(payload["subtotal"])))
                addSummaryRow(rows, "discount_total", "Payload 折扣", stringValue(orderData["discountTotal"], stringValue(orderData["discount_total"], stringValue(payload["discountTotal"]))))
                addSummaryRow(rows, "tax_total", "Payload 税额", stringValue(orderData["taxTotal"], stringValue(orderData["tax_total"], stringValue(payload["taxTotal"]))))
                addSummaryRow(rows, "total_amount", "Payload 总额", stringValue(orderData["total"], stringValue(orderData["total_amount"], stringValue(payload["total_amount"]))))
                if (rows.length == 0) {
                    rows.push(_uO("key" to "empty", "label" to "摘要", "value" to "payload 未提供常用摘要字段"))
                }
                return rows
            }
            val buildPayloadSummary = ::gen_buildPayloadSummary_fn
            fun gen_numberValue_fn(value: Any?): Number {
                val parsed = parseFloat(stringValue(value, "0"))
                if (isNaN(parsed)) {
                    return 0
                }
                return parsed
            }
            val numberValue = ::gen_numberValue_fn
            fun gen_quantityText_fn(value: Number): String {
                if (value == Math.floor(value)) {
                    return value.toString(10)
                }
                return value.toFixed(2)
            }
            val quantityText = ::gen_quantityText_fn
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
            fun gen_goBack_fn() {
                uni_navigateBack(NavigateBackOptions(delta = 1, fail = fun(_){
                    uni_redirectTo(RedirectToOptions(url = "/pages/orders/index"))
                }
                ))
            }
            val goBack = ::gen_goBack_fn
            fun gen_goHome_fn() {
                uni_redirectTo(RedirectToOptions(url = "/pages/orders/index"))
            }
            val goHome = ::gen_goHome_fn
            fun gen_updateNavShellStyle_fn() {
                val info = uni_getWindowInfo()
                navShellStyle.value = "padding-top:" + info.statusBarHeight.toString(10) + "px;"
            }
            val updateNavShellStyle = ::gen_updateNavShellStyle_fn
            fun gen_loadDetail_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (orderId.value == "") {
                            errorMessage.value = "缺少订单 ID"
                            return@w1
                        }
                        if (isLoading.value) {
                            return@w1
                        }
                        isLoading.value = true
                        errorMessage.value = ""
                        try {
                            val detail = await(getOrderDetail(orderId.value))
                            orderDetail.value = detail
                        }
                         catch (error: Throwable) {
                            orderDetail.value = null
                            errorMessage.value = parseErrorMessage(error, "订单详情加载失败")
                        }
                         finally {
                            isLoading.value = false
                        }
                })
            }
            val loadDetail = ::gen_loadDetail_fn
            fun gen_copyOrderNumber_fn() {
                if (orderDetail.value == null) {
                    copyText("", "订单号已复制", "暂无订单号")
                    return
                }
                val detail = orderDetail.value as OrderItem
                copyText(stringValue(detail.order_number, "订单 #" + detail.id.toString(10)), "订单号已复制", "暂无订单号")
            }
            val copyOrderNumber = ::gen_copyOrderNumber_fn
            fun gen_copyPayload_fn() {
                if (orderDetail.value == null) {
                    copyText("", "Payload 已复制", "暂无 Payload")
                    return
                }
                val detail = orderDetail.value as OrderItem
                copyText(payloadTextValue(detail.payload), "Payload 已复制", "暂无 Payload")
            }
            val copyPayload = ::gen_copyPayload_fn
            fun gen_copyRowValue_fn(row: UTSJSONObject) {
                copyText(stringValue(row["value"]), "内容已复制", "暂无内容")
            }
            val copyRowValue = ::gen_copyRowValue_fn
            val pageTitle = computed(fun(): String {
                if (orderDetail.value == null) {
                    return "订单详情"
                }
                val detail = orderDetail.value as OrderItem
                return stringValue(detail.order_number, "订单 #" + detail.id.toString(10))
            }
            )
            val pageSubtitle = computed(fun(): String {
                if (orderDetail.value == null) {
                    return if (orderId.value == "") {
                        "订单详情"
                    } else {
                        "ID " + orderId.value
                    }
                }
                val detail = orderDetail.value as OrderItem
                return displayText(compactDate(stringValue(detail.order_time, detail.created_at)))
            }
            )
            val orderNumberText = computed(fun(): String {
                if (orderDetail.value == null) {
                    return "-"
                }
                val detail = orderDetail.value as OrderItem
                return stringValue(detail.order_number, "订单 #" + detail.id.toString(10))
            }
            )
            val orderTimeText = computed(fun(): String {
                if (orderDetail.value == null) {
                    return "-"
                }
                val detail = orderDetail.value as OrderItem
                return compactDate(stringValue(detail.order_time, detail.created_at))
            }
            )
            val statusDisplayText = computed(fun(): String {
                if (orderDetail.value == null) {
                    return "-"
                }
                return statusText(orderDetail.value as OrderItem)
            }
            )
            val paymentDisplayText = computed(fun(): String {
                if (orderDetail.value == null) {
                    return "-"
                }
                return paymentText(orderDetail.value as OrderItem)
            }
            )
            val inventoryStatusText = computed(fun(): String {
                if (orderDetail.value == null) {
                    return "-"
                }
                val detail = orderDetail.value as OrderItem
                return if (detail.inventory_deducted) {
                    "已扣减"
                } else {
                    "未扣减"
                }
            }
            )
            val totalAmountText = computed(fun(): String {
                if (orderDetail.value == null) {
                    return "0.00"
                }
                return currencyText((orderDetail.value as OrderItem).total_amount)
            }
            )
            val itemCountText = computed(fun(): String {
                if (orderDetail.value == null) {
                    return "0"
                }
                val detail = orderDetail.value as OrderItem
                return getPayloadItems(detail.payload).length.toString(10)
            }
            )
            val quantityCountText = computed(fun(): String {
                if (orderDetail.value == null) {
                    return "0"
                }
                val detail = orderDetail.value as OrderItem
                val rows = getPayloadItems(detail.payload)
                var total: Number = 0.0
                run {
                    var index: Number = 0
                    while(index < rows.length){
                        total = total + numberValue(rows[index]["quantity"])
                        index += 1
                    }
                }
                return quantityText(total)
            }
            )
            val payloadText = computed(fun(): String {
                if (orderDetail.value == null) {
                    return "{}"
                }
                val detail = orderDetail.value as OrderItem
                return payloadTextValue(detail.payload)
            }
            )
            val payloadSizeText = computed(fun(): String {
                return payloadText.value.length.toString(10) + " 字符"
            }
            )
            val statusPillClass = computed(fun(): String {
                if (orderDetail.value == null) {
                    return "status-pill"
                }
                val status = (orderDetail.value as OrderItem).status
                if (status == "processed") {
                    return "status-pill status-pill-ok"
                }
                if (status == "failed") {
                    return "status-pill status-pill-error"
                }
                return "status-pill status-pill-warn"
            }
            )
            val statusTextClass = computed(fun(): String {
                if (orderDetail.value == null) {
                    return "status-pill-text"
                }
                val status = (orderDetail.value as OrderItem).status
                if (status == "processed") {
                    return "status-pill-text status-pill-text-ok"
                }
                if (status == "failed") {
                    return "status-pill-text status-pill-text-error"
                }
                return "status-pill-text status-pill-text-warn"
            }
            )
            val baseRows = computed(fun(): UTSArray<UTSJSONObject> {
                if (orderDetail.value == null) {
                    return _uA<UTSJSONObject>()
                }
                val detail = orderDetail.value as OrderItem
                return _uA(
                    _uO("key" to "order_number", "label" to "订单号", "value" to orderNumberText.value),
                    _uO("key" to "shop", "label" to "店铺", "value" to stringValue(detail.shop_name, if (detail.shop > 0) {
                        "店铺 #" + detail.shop.toString(10)
                    } else {
                        "-"
                    }
                    )),
                    _uO("key" to "order_time", "label" to "订单时间", "value" to compactDate(detail.order_time)),
                    _uO("key" to "created_at", "label" to "接收时间", "value" to compactDate(detail.created_at)),
                    _uO("key" to "updated_at", "label" to "更新时间", "value" to compactDate(detail.updated_at)),
                    _uO("key" to "cashier_id", "label" to "收银员", "value" to stringValue(detail.cashier_id, "-")),
                    _uO("key" to "kasa_number", "label" to "收银台", "value" to stringValue(detail.kasa_number, "-"))
                )
            }
            )
            val amountRows = computed(fun(): UTSArray<UTSJSONObject> {
                if (orderDetail.value == null) {
                    return _uA<UTSJSONObject>()
                }
                val detail = orderDetail.value as OrderItem
                return _uA(
                    _uO("key" to "subtotal", "label" to "小计", "value" to currencyText(detail.subtotal)),
                    _uO("key" to "discount_amount", "label" to "折扣", "value" to currencyText(detail.discount_amount)),
                    _uO("key" to "tax_amount", "label" to "税额", "value" to currencyText(detail.tax_amount)),
                    _uO("key" to "total_amount", "label" to "总金额", "value" to currencyText(detail.total_amount))
                )
            }
            )
            val statusRows = computed(fun(): UTSArray<UTSJSONObject> {
                if (orderDetail.value == null) {
                    return _uA<UTSJSONObject>()
                }
                val detail = orderDetail.value as OrderItem
                return _uA(
                    _uO("key" to "payment_method", "label" to "支付方式", "value" to paymentText(detail)),
                    _uO("key" to "status", "label" to "订单状态", "value" to statusText(detail)),
                    _uO("key" to "error_message", "label" to "订单错误", "value" to stringValue(detail.error_message, "-")),
                    _uO("key" to "inventory_deducted", "label" to "库存已扣减", "value" to boolText(detail.inventory_deducted)),
                    _uO("key" to "inventory_deduct_time", "label" to "扣减时间", "value" to compactDate(detail.inventory_deduct_time)),
                    _uO("key" to "inventory_deduct_error", "label" to "扣减错误", "value" to stringValue(detail.inventory_deduct_error, "-"))
                )
            }
            )
            val itemRows = computed(fun(): UTSArray<PayloadRow> {
                val result: UTSArray<PayloadRow> = _uA()
                if (orderDetail.value == null) {
                    return result
                }
                val detail = orderDetail.value as OrderItem
                val rows = getPayloadItems(detail.payload)
                run {
                    var index: Number = 0
                    while(index < rows.length){
                        val row = rows[index]
                        val amount = stringValue(row["lineTotal"], stringValue(row["amount"], stringValue(row["total"], stringValue(row["total_amount"], "-"))))
                        result.push(PayloadRow(key = index.toString(10), title = buildRowTitle(row, index), desc = buildRowDesc(row), code = "条码 " + stringValue(row["barcode"], "-"), quantity = "x " + stringValue(row["quantity"], "0"), amount = if (amount == "-") {
                            "-"
                        } else {
                            "¥ " + amount
                        }
                        ))
                        index += 1
                    }
                }
                return result
            }
            )
            val payloadSummaryRows = computed(fun(): UTSArray<UTSJSONObject> {
                if (orderDetail.value == null) {
                    return _uA<UTSJSONObject>()
                }
                val detail = orderDetail.value as OrderItem
                return buildPayloadSummary(detail.payload)
            }
            )
            onLoad(fun(event: OnLoadOptions){
                updateNavShellStyle()
                val idValue = event["id"]
                orderId.value = if (idValue == null) {
                    ""
                } else {
                    ("" + idValue)
                }
                loadDetail()
            }
            )
            return fun(): Any? {
                return _cE("view", _uM("class" to "page"), _uA(
                    _cE("view", _uM("class" to "nav-shell", "style" to _nS(unref(navShellStyle))), _uA(
                        _cE("view", _uM("class" to "detail-nav"), _uA(
                            _cE("view", _uM("class" to "nav-btn", "onClick" to goBack), _uA(
                                _cE("text", _uM("class" to "nav-btn-text"), "<")
                            )),
                            _cE("view", _uM("class" to "nav-main"), _uA(
                                _cE("text", _uM("class" to "nav-title"), _tD(pageTitle.value), 1),
                                _cE("text", _uM("class" to "nav-subtitle"), _tD(pageSubtitle.value), 1)
                            )),
                            _cE("view", _uM("class" to "nav-home", "onClick" to goHome), _uA(
                                _cE("text", _uM("class" to "nav-home-text"), "列表")
                            ))
                        ))
                    ), 4),
                    _cE("scroll-view", _uM("style" to _nS(_uM("flex" to "1")), "class" to "page-scroll"), _uA(
                        _cE("view", _uM("class" to "page-content"), _uA(
                            if (isTrue(unref(isLoading))) {
                                _cE("view", _uM("key" to 0, "class" to "state-card"), _uA(
                                    _cE("text", _uM("class" to "state-title"), "正在加载订单详情"),
                                    _cE("text", _uM("class" to "state-desc"), "订单 ID：" + _tD(displayText(unref(orderId))), 1)
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            if (isTrue(unref(errorMessage) != "" && !unref(isLoading))) {
                                _cE("view", _uM("key" to 1, "class" to "error-card"), _uA(
                                    _cE("text", _uM("class" to "error-title"), "加载失败"),
                                    _cE("text", _uM("class" to "error-desc"), _tD(unref(errorMessage)), 1),
                                    _cE("view", _uM("class" to "retry-btn", "onClick" to loadDetail), _uA(
                                        _cE("text", _uM("class" to "retry-btn-text"), "重新加载")
                                    ))
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            if (isTrue(unref(orderDetail) == null && !unref(isLoading) && unref(errorMessage) == "")) {
                                _cE("view", _uM("key" to 2, "class" to "state-card"), _uA(
                                    _cE("text", _uM("class" to "state-title"), "暂无订单数据"),
                                    _cE("text", _uM("class" to "state-desc"), "没有读取到当前订单详情")
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            if (isTrue(unref(orderDetail) != null && !unref(isLoading))) {
                                _cE("view", _uM("key" to 3, "class" to "summary-card"), _uA(
                                    _cE("view", _uM("class" to "summary-head"), _uA(
                                        _cE("view", _uM("class" to "summary-main"), _uA(
                                            _cE("text", _uM("class" to "summary-title"), _tD(orderNumberText.value), 1),
                                            _cE("text", _uM("class" to "summary-subtitle"), _tD(orderTimeText.value), 1)
                                        )),
                                        _cE("view", _uM("class" to _nC(statusPillClass.value)), _uA(
                                            _cE("text", _uM("class" to _nC(statusTextClass.value)), _tD(statusDisplayText.value), 3)
                                        ), 2)
                                    )),
                                    _cE("view", _uM("class" to "amount-line"), _uA(
                                        _cE("text", _uM("class" to "amount-label"), "订单金额"),
                                        _cE("text", _uM("class" to "amount-value"), "¥ " + _tD(totalAmountText.value), 1)
                                    )),
                                    _cE("view", _uM("class" to "action-row"), _uA(
                                        _cE("view", _uM("class" to "action-btn action-btn-primary", "onClick" to copyOrderNumber), _uA(
                                            _cE("text", _uM("class" to "action-btn-primary-text"), "复制订单号")
                                        )),
                                        _cE("view", _uM("class" to "action-btn action-btn-light", "onClick" to copyPayload), _uA(
                                            _cE("text", _uM("class" to "action-btn-light-text"), "复制 Payload")
                                        ))
                                    ))
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            if (isTrue(unref(orderDetail) != null && !unref(isLoading))) {
                                _cE("view", _uM("key" to 4, "class" to "metric-grid"), _uA(
                                    _cE("view", _uM("class" to "metric-cell"), _uA(
                                        _cE("text", _uM("class" to "metric-label"), "支付方式"),
                                        _cE("text", _uM("class" to "metric-value"), _tD(paymentDisplayText.value), 1)
                                    )),
                                    _cE("view", _uM("class" to "metric-cell"), _uA(
                                        _cE("text", _uM("class" to "metric-label"), "库存状态"),
                                        _cE("text", _uM("class" to "metric-value"), _tD(inventoryStatusText.value), 1)
                                    )),
                                    _cE("view", _uM("class" to "metric-cell"), _uA(
                                        _cE("text", _uM("class" to "metric-label"), "商品项"),
                                        _cE("text", _uM("class" to "metric-value"), _tD(itemCountText.value), 1)
                                    )),
                                    _cE("view", _uM("class" to "metric-cell"), _uA(
                                        _cE("text", _uM("class" to "metric-label"), "商品件数"),
                                        _cE("text", _uM("class" to "metric-value"), _tD(quantityCountText.value), 1)
                                    ))
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            if (isTrue(unref(orderDetail) != null && !unref(isLoading))) {
                                _cE("view", _uM("key" to 5, "class" to "section-card"), _uA(
                                    _cE("text", _uM("class" to "section-title"), "订单基础信息"),
                                    _cE(Fragment, null, RenderHelpers.renderList(baseRows.value, fun(row, __key, __index, _cached): Any {
                                        return _cE("view", _uM("key" to row["key"], "class" to "info-row", "onClick" to fun(){
                                            copyRowValue(row)
                                        }), _uA(
                                            _cE("text", _uM("class" to "info-label"), _tD(row["label"]), 1),
                                            _cE("text", _uM("class" to "info-value"), _tD(row["value"]), 1)
                                        ), 8, _uA(
                                            "onClick"
                                        ))
                                    }), 128)
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            if (isTrue(unref(orderDetail) != null && !unref(isLoading))) {
                                _cE("view", _uM("key" to 6, "class" to "section-card"), _uA(
                                    _cE("text", _uM("class" to "section-title"), "金额信息"),
                                    _cE(Fragment, null, RenderHelpers.renderList(amountRows.value, fun(row, __key, __index, _cached): Any {
                                        return _cE("view", _uM("key" to row["key"], "class" to "info-row", "onClick" to fun(){
                                            copyRowValue(row)
                                        }), _uA(
                                            _cE("text", _uM("class" to "info-label"), _tD(row["label"]), 1),
                                            _cE("text", _uM("class" to "info-value"), _tD(row["value"]), 1)
                                        ), 8, _uA(
                                            "onClick"
                                        ))
                                    }), 128)
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            if (isTrue(unref(orderDetail) != null && !unref(isLoading))) {
                                _cE("view", _uM("key" to 7, "class" to "section-card"), _uA(
                                    _cE("text", _uM("class" to "section-title"), "支付与库存状态"),
                                    _cE(Fragment, null, RenderHelpers.renderList(statusRows.value, fun(row, __key, __index, _cached): Any {
                                        return _cE("view", _uM("key" to row["key"], "class" to "info-row", "onClick" to fun(){
                                            copyRowValue(row)
                                        }), _uA(
                                            _cE("text", _uM("class" to "info-label"), _tD(row["label"]), 1),
                                            _cE("text", _uM("class" to "info-value"), _tD(row["value"]), 1)
                                        ), 8, _uA(
                                            "onClick"
                                        ))
                                    }), 128)
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            if (isTrue(unref(orderDetail) != null && !unref(isLoading))) {
                                _cE("view", _uM("key" to 8, "class" to "section-card"), _uA(
                                    _cE("view", _uM("class" to "section-head"), _uA(
                                        _cE("text", _uM("class" to "section-title"), "商品明细"),
                                        _cE("text", _uM("class" to "section-note"), _tD(itemRows.value.length.toString(10)) + " 项", 1)
                                    )),
                                    if (itemRows.value.length == 0) {
                                        _cE("view", _uM("key" to 0, "class" to "empty-box"), _uA(
                                            _cE("text", _uM("class" to "empty-text"), "payload 中没有 items / products / cart 商品行")
                                        ))
                                    } else {
                                        _cC("v-if", true)
                                    },
                                    _cE(Fragment, null, RenderHelpers.renderList(itemRows.value, fun(row, __key, __index, _cached): Any {
                                        return _cE("view", _uM("key" to row.key, "class" to "item-row"), _uA(
                                            _cE("view", _uM("class" to "item-main"), _uA(
                                                _cE("text", _uM("class" to "item-title"), _tD(row.title), 1),
                                                _cE("text", _uM("class" to "item-desc"), _tD(row.desc), 1),
                                                _cE("text", _uM("class" to "item-code"), _tD(row.code), 1)
                                            )),
                                            _cE("view", _uM("class" to "item-side"), _uA(
                                                _cE("text", _uM("class" to "item-qty"), _tD(row.quantity), 1),
                                                _cE("text", _uM("class" to "item-amount"), _tD(row.amount), 1)
                                            ))
                                        ))
                                    }), 128)
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            if (isTrue(unref(orderDetail) != null && !unref(isLoading))) {
                                _cE("view", _uM("key" to 9, "class" to "section-card"), _uA(
                                    _cE("view", _uM("class" to "section-head"), _uA(
                                        _cE("text", _uM("class" to "section-title"), "Payload 摘要"),
                                        _cE("text", _uM("class" to "section-note"), _tD(payloadSizeText.value), 1)
                                    )),
                                    _cE(Fragment, null, RenderHelpers.renderList(payloadSummaryRows.value, fun(row, __key, __index, _cached): Any {
                                        return _cE("view", _uM("key" to row["key"], "class" to "info-row", "onClick" to fun(){
                                            copyRowValue(row)
                                        }), _uA(
                                            _cE("text", _uM("class" to "info-label"), _tD(row["label"]), 1),
                                            _cE("text", _uM("class" to "info-value"), _tD(row["value"]), 1)
                                        ), 8, _uA(
                                            "onClick"
                                        ))
                                    }), 128)
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            if (isTrue(unref(orderDetail) != null && !unref(isLoading))) {
                                _cE("view", _uM("key" to 10, "class" to "section-card"), _uA(
                                    _cE("view", _uM("class" to "section-head"), _uA(
                                        _cE("text", _uM("class" to "section-title"), "原始 Payload"),
                                        _cE("view", _uM("class" to "small-copy-btn", "onClick" to copyPayload), _uA(
                                            _cE("text", _uM("class" to "small-copy-text"), "复制")
                                        ))
                                    )),
                                    _cE("text", _uM("class" to "payload-text"), _tD(payloadText.value), 1)
                                ))
                            } else {
                                _cC("v-if", true)
                            }
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-scroll" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "nav-shell" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#E5EAF1")), "detail-nav" to _pS(_uM("height" to 58, "paddingLeft" to 8, "paddingRight" to 8, "flexDirection" to "row", "alignItems" to "center")), "nav-btn" to _pS(_uM("width" to 46, "height" to 40, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "#F3F6FA")), "nav-home" to _pS(_uM("width" to 46, "height" to 40, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "alignItems" to "center", "justifyContent" to "center")), "nav-btn-text" to _pS(_uM("fontSize" to 28, "lineHeight" to "28px", "color" to "#0F172A")), "nav-main" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingLeft" to 10, "paddingRight" to 10)), "nav-title" to _pS(_uM("fontSize" to 17, "lineHeight" to "22px", "fontWeight" to "bold", "color" to "#0F172A")), "nav-subtitle" to _pS(_uM("fontSize" to 12, "lineHeight" to "17px", "color" to "#64748B", "marginTop" to 1)), "nav-home-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#0F172A", "fontWeight" to "bold")), "page-content" to _pS(_uM("paddingTop" to 10, "paddingRight" to 10, "paddingBottom" to 96, "paddingLeft" to 10)), "summary-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E5EAF1", "borderRightColor" to "#E5EAF1", "borderBottomColor" to "#E5EAF1", "borderLeftColor" to "#E5EAF1", "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 14, "paddingLeft" to 14, "marginBottom" to 10)), "section-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E5EAF1", "borderRightColor" to "#E5EAF1", "borderBottomColor" to "#E5EAF1", "borderLeftColor" to "#E5EAF1", "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 14, "paddingLeft" to 14, "marginBottom" to 10)), "state-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E5EAF1", "borderRightColor" to "#E5EAF1", "borderBottomColor" to "#E5EAF1", "borderLeftColor" to "#E5EAF1", "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 14, "paddingLeft" to 14, "marginBottom" to 10, "alignItems" to "center")), "error-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FECACA", "borderRightColor" to "#FECACA", "borderBottomColor" to "#FECACA", "borderLeftColor" to "#FECACA", "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 14, "paddingLeft" to 14, "marginBottom" to 10, "alignItems" to "center")), "summary-head" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between")), "amount-line" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginTop" to 14, "backgroundColor" to "#F8FAFC", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "justifyContent" to "space-between")), "action-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginTop" to 12)), "metric-grid" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "flexWrap" to "wrap", "marginLeft" to -4, "marginRight" to -4, "marginBottom" to 2)), "section-head" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between")), "info-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to 10, "paddingBottom" to 10, "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#EEF2F7")), "item-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "flex-start", "justifyContent" to "space-between", "paddingTop" to 12, "paddingBottom" to 12, "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#EEF2F7")), "summary-main" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingRight" to 10)), "summary-title" to _pS(_uM("fontSize" to 20, "lineHeight" to "26px", "fontWeight" to "bold", "color" to "#0F172A")), "summary-subtitle" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#64748B", "marginTop" to 4)), "status-pill" to _pS(_uM("minWidth" to 62, "height" to 30, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingLeft" to 10, "paddingRight" to 10, "alignItems" to "center", "justifyContent" to "center")), "status-pill-ok" to _pS(_uM("backgroundColor" to "#DCFCE7")), "status-pill-warn" to _pS(_uM("backgroundColor" to "#FEF3C7")), "status-pill-error" to _pS(_uM("backgroundColor" to "#FEE2E2")), "status-pill-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "12px", "fontWeight" to "bold")), "status-pill-text-ok" to _pS(_uM("color" to "#166534")), "status-pill-text-warn" to _pS(_uM("color" to "#92400E")), "status-pill-text-error" to _pS(_uM("color" to "#991B1B")), "amount-label" to _pS(_uM("fontSize" to 12, "lineHeight" to "17px", "color" to "#64748B")), "metric-label" to _pS(_uM("fontSize" to 12, "lineHeight" to "17px", "color" to "#64748B", "backgroundColor" to "#FFFFFF")), "info-label" to _pS(_uM("fontSize" to 12, "lineHeight" to "17px", "color" to "#64748B", "width" to 96)), "section-note" to _pS(_uM("fontSize" to 12, "lineHeight" to "17px", "color" to "#64748B")), "state-desc" to _pS(_uM("fontSize" to 12, "lineHeight" to "17px", "color" to "#64748B", "marginTop" to 6)), "amount-value" to _pS(_uM("fontSize" to 22, "lineHeight" to "28px", "fontWeight" to "bold", "color" to "#0F172A")), "action-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 40, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "alignItems" to "center", "justifyContent" to "center")), "action-btn-primary" to _pS(_uM("backgroundColor" to "#0F172A", "marginRight" to 8)), "action-btn-light" to _pS(_uM("backgroundColor" to "#F3F6FA", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0")), "action-btn-primary-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "13px", "color" to "#FFFFFF")), "retry-btn-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "13px", "color" to "#FFFFFF")), "small-copy-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "13px", "color" to "#FFFFFF")), "action-btn-light-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "13px", "color" to "#334155")), "metric-cell" to _pS(_uM("width" to "50%", "paddingLeft" to 4, "paddingRight" to 4, "marginBottom" to 8, "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E5EAF1", "borderRightColor" to "#E5EAF1", "borderBottomColor" to "#E5EAF1", "borderLeftColor" to "#E5EAF1", "paddingTop" to 10, "paddingBottom" to 10)), "metric-value" to _pS(_uM("backgroundColor" to "#FFFFFF", "fontSize" to 15, "lineHeight" to "21px", "color" to "#0F172A", "fontWeight" to "bold", "marginTop" to 4)), "section-title" to _pS(_uM("fontSize" to 16, "lineHeight" to "22px", "color" to "#0F172A", "fontWeight" to "bold")), "info-value" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "fontSize" to 13, "lineHeight" to "19px", "color" to "#0F172A", "textAlign" to "right")), "empty-box" to _pS(_uM("marginTop" to 10, "backgroundColor" to "#F8FAFC", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "alignItems" to "center")), "empty-text" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#64748B", "textAlign" to "center")), "state-title" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#0F172A", "textAlign" to "center", "fontWeight" to "bold")), "item-main" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingRight" to 10)), "item-title" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#111827", "fontWeight" to "bold")), "item-desc" to _pS(_uM("fontSize" to 12, "lineHeight" to "18px", "color" to "#64748B", "marginTop" to 2)), "item-code" to _pS(_uM("fontSize" to 12, "lineHeight" to "18px", "color" to "#64748B", "marginTop" to 2)), "item-side" to _pS(_uM("width" to 82, "alignItems" to "flex-end")), "item-qty" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#0F172A", "fontWeight" to "bold")), "item-amount" to _pS(_uM("fontSize" to 12, "lineHeight" to "18px", "color" to "#475569", "marginTop" to 2)), "small-copy-btn" to _pS(_uM("height" to 34, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#0F172A", "paddingLeft" to 14, "paddingRight" to 14, "alignItems" to "center", "justifyContent" to "center")), "retry-btn" to _pS(_uM("height" to 34, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#0F172A", "paddingLeft" to 14, "paddingRight" to 14, "alignItems" to "center", "justifyContent" to "center", "marginTop" to 14)), "payload-text" to _pS(_uM("marginTop" to 10, "fontSize" to 12, "lineHeight" to "18px", "color" to "#334155")), "error-title" to _pS(_uM("fontSize" to 18, "lineHeight" to "24px", "color" to "#B42318", "fontWeight" to "bold")), "error-desc" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#7F1D1D", "marginTop" to 8, "textAlign" to "center")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
