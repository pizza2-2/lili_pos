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
            val initialData = ref<UTSJSONObject>(_uO())
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
                    val directMessage = (error as UTSError).message
                    if (directMessage != null && directMessage != "") {
                        message = directMessage
                    }
                    val errorText = JSON.stringify(error)
                    if (errorText != null && errorText != "") {
                        val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/orders/from.uvue:95")
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
                val parsed = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(text), " at pages/orders/from.uvue:113")
                if (parsed == null) {
                    return _uA<UTSJSONObject>()
                }
                return parsed!!
            }
            val parseObjectArray = ::gen_parseObjectArray_fn
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
            fun gen_payloadTextValue_fn(payload: UTSJSONObject): String {
                val text = JSON.stringify(payload)
                if (text == null || text == "") {
                    return "{}"
                }
                return text
            }
            val payloadTextValue = ::gen_payloadTextValue_fn
            fun gen_getPayloadItems_fn(payload: UTSJSONObject): UTSArray<UTSJSONObject> {
                var rows = parseObjectArray(payload["items"])
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
                val barcode = stringValue(row["barcode"], "-")
                val price = stringValue(row["price"], stringValue(row["unit_price"], "-"))
                val amount = stringValue(row["amount"], stringValue(row["total"], "-"))
                return "条码 " + barcode + " / 单价 " + price + " / 金额 " + amount
            }
            val buildRowDesc = ::gen_buildRowDesc_fn
            fun gen_buildInitialData_fn(item: OrderItem): UTSJSONObject {
                return _uO("order_number" to item.order_number, "shop_name" to stringValue(item.shop_name, if (item.shop > 0) {
                    "店铺 #" + item.shop.toString(10)
                } else {
                    "-"
                }
                ), "order_time" to stringValue(item.order_time, "-"), "created_at" to stringValue(item.created_at, "-"), "cashier_id" to stringValue(item.cashier_id, "-"), "kasa_number" to stringValue(item.kasa_number, "-"), "payment_method_text" to paymentText(item), "subtotal" to stringValue(item.subtotal, "0.00"), "discount_amount" to stringValue(item.discount_amount, "0.00"), "tax_amount" to stringValue(item.tax_amount, "0.00"), "total_amount" to stringValue(item.total_amount, "0.00"), "status_text" to statusText(item), "error_message" to stringValue(item.error_message, "-"), "inventory_deducted_text" to boolText(item.inventory_deducted), "inventory_deduct_time" to stringValue(item.inventory_deduct_time, "-"), "inventory_deduct_error" to stringValue(item.inventory_deduct_error, "-"))
            }
            val buildInitialData = ::gen_buildInitialData_fn
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
                            initialData.value = buildInitialData(detail)
                        }
                         catch (error: Throwable) {
                            orderDetail.value = null
                            initialData.value = _uO()
                            errorMessage.value = parseErrorMessage(error, "订单详情加载失败")
                        }
                         finally {
                            isLoading.value = false
                        }
                })
            }
            val loadDetail = ::gen_loadDetail_fn
            fun gen_handleNoop_fn(payload: UTSJSONObject) {}
            val handleNoop = ::gen_handleNoop_fn
            fun gen_handleDirtyChange_fn(value: Boolean) {}
            val handleDirtyChange = ::gen_handleDirtyChange_fn
            val pageTitle = computed(fun(): String {
                if (orderDetail.value == null) {
                    return "订单详情"
                }
                val detail = orderDetail.value as OrderItem
                return "订单 " + detail.order_number
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
                        result.push(PayloadRow(key = index.toString(10), title = buildRowTitle(row, index), desc = buildRowDesc(row), quantity = "x " + stringValue(row["quantity"], "0")))
                        index += 1
                    }
                }
                return result
            }
            )
            fun gen_copyPayload_fn() {
                uni_setClipboardData(SetClipboardDataOptions(data = payloadText.value, success = fun(_){
                    uni_showToast(ShowToastOptions(title = "Payload 已复制", icon = "success"))
                }
                ))
            }
            val copyPayload = ::gen_copyPayload_fn
            val formSections = ref(_uA<UTSJSONObject>(_uO("key" to "base", "title" to "订单信息", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "order_number", "label" to "订单号", "type" to "input", "readonly" to true), _uO("key" to "shop_name", "label" to "店铺", "type" to "input", "readonly" to true), _uO("key" to "order_time", "label" to "订单时间", "type" to "input", "readonly" to true), _uO("key" to "created_at", "label" to "接收时间", "type" to "input", "readonly" to true), _uO("key" to "cashier_id", "label" to "收银员", "type" to "input", "readonly" to true), _uO("key" to "kasa_number", "label" to "收银台", "type" to "input", "readonly" to true))), _uO("key" to "amount", "title" to "金额信息", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "payment_method_text", "label" to "支付方式", "type" to "input", "readonly" to true), _uO("key" to "subtotal", "label" to "小计", "type" to "input", "readonly" to true), _uO("key" to "discount_amount", "label" to "折扣", "type" to "input", "readonly" to true), _uO("key" to "tax_amount", "label" to "税额", "type" to "input", "readonly" to true), _uO("key" to "total_amount", "label" to "总金额", "type" to "input", "readonly" to true))), _uO("key" to "status", "title" to "处理状态", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "status_text", "label" to "订单状态", "type" to "input", "readonly" to true), _uO("key" to "error_message", "label" to "订单错误", "type" to "textarea", "readonly" to true), _uO("key" to "inventory_deducted_text", "label" to "库存已扣减", "type" to "input", "readonly" to true), _uO("key" to "inventory_deduct_time", "label" to "扣减时间", "type" to "input", "readonly" to true), _uO("key" to "inventory_deduct_error", "label" to "扣减错误", "type" to "textarea", "readonly" to true)))))
            onLoad(fun(event: OnLoadOptions){
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
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversaForm = resolveEasyComponent("lili-UniversaForm", GenUniModulesLiliUniversaFormComponentsLiliUniversaFormLiliUniversaFormClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to pageTitle.value, "showBack" to true, "showSearch" to false, "showHome" to true, "homePath" to "/pages/orders/index", "backgroundColor" to "#EEF2F7"), null, 8, _uA(
                        "title"
                    )),
                    _cE("scroll-view", _uM("style" to _nS(_uM("flex" to "1")), "class" to "page-scroll"), _uA(
                        _cE("view", _uM("class" to "page-content"), _uA(
                            if (isTrue(unref(errorMessage) != "" && !unref(isLoading))) {
                                _cE("view", _uM("key" to 0, "class" to "error-card"), _uA(
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
                            if (isTrue(unref(isLoading))) {
                                _cE("view", _uM("key" to 1, "class" to "loading-card"), _uA(
                                    _cE("text", _uM("class" to "loading-text"), "正在加载订单详情")
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            if (isTrue(!unref(isLoading))) {
                                _cV(_component_lili_UniversaForm, _uM("key" to 2, "mode" to "edit", "formSections" to unref(formSections), "initialData" to unref(initialData), "showFooter" to false, "enableBackConfirm" to false, "onSubmit" to handleNoop, "onCancel" to handleNoop, "onDiscardLeave" to handleNoop, "onSaveRequest" to handleNoop, "onDirtyChange" to handleDirtyChange), null, 8, _uA(
                                    "formSections",
                                    "initialData"
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            if (isTrue(!unref(isLoading))) {
                                _cE("view", _uM("key" to 3, "class" to "section-card"), _uA(
                                    _cE("view", _uM("class" to "section-header"), _uA(
                                        _cE("text", _uM("class" to "section-title"), "商品行"),
                                        _cE("text", _uM("class" to "section-subtitle"), _tD(itemRows.value.length.toString(10)) + " 项", 1)
                                    )),
                                    if (itemRows.value.length == 0) {
                                        _cE("view", _uM("key" to 0, "class" to "empty-line"), _uA(
                                            _cE("text", _uM("class" to "empty-line-text"), "payload 中没有 items / products / cart 商品行")
                                        ))
                                    } else {
                                        _cC("v-if", true)
                                    },
                                    _cE(Fragment, null, RenderHelpers.renderList(itemRows.value, fun(row, __key, __index, _cached): Any {
                                        return _cE("view", _uM("key" to row.key, "class" to "item-row"), _uA(
                                            _cE("view", _uM("class" to "item-row-main"), _uA(
                                                _cE("text", _uM("class" to "item-title"), _tD(row.title), 1),
                                                _cE("text", _uM("class" to "item-desc"), _tD(row.desc), 1)
                                            )),
                                            _cE("text", _uM("class" to "item-qty"), _tD(row.quantity), 1)
                                        ))
                                    }), 128)
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            if (isTrue(!unref(isLoading))) {
                                _cE("view", _uM("key" to 4, "class" to "section-card"), _uA(
                                    _cE("view", _uM("class" to "section-header"), _uA(
                                        _cE("text", _uM("class" to "section-title"), "原始 Payload"),
                                        _cE("view", _uM("class" to "copy-btn", "onClick" to copyPayload), _uA(
                                            _cE("text", _uM("class" to "copy-btn-text"), "复制")
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#EEF2F7")), "page-scroll" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#EEF2F7")), "page-content" to _pS(_uM("paddingTop" to 6, "paddingRight" to 6, "paddingBottom" to 96, "paddingLeft" to 6)), "loading-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 20, "paddingRight" to 20, "paddingBottom" to 20, "paddingLeft" to 20, "marginBottom" to 8, "alignItems" to "center", "justifyContent" to "center")), "loading-text" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#64748B")), "error-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 18, "paddingRight" to 18, "paddingBottom" to 18, "paddingLeft" to 18, "marginBottom" to 10, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FECACA", "borderRightColor" to "#FECACA", "borderBottomColor" to "#FECACA", "borderLeftColor" to "#FECACA", "alignItems" to "center")), "error-title" to _pS(_uM("fontSize" to 18, "lineHeight" to "24px", "color" to "#B42318", "fontWeight" to "bold")), "error-desc" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#7F1D1D", "marginTop" to 8, "textAlign" to "center")), "retry-btn" to _pS(_uM("marginTop" to 14, "height" to 40, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#0F172A", "paddingLeft" to 18, "paddingRight" to 18, "alignItems" to "center", "justifyContent" to "center")), "retry-btn-text" to _pS(_uM("fontSize" to 14, "color" to "#FFFFFF")), "section-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 14, "paddingLeft" to 14, "marginTop" to 8, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E5E7EB", "borderRightColor" to "#E5E7EB", "borderBottomColor" to "#E5E7EB", "borderLeftColor" to "#E5E7EB")), "section-header" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "marginBottom" to 10)), "section-title" to _pS(_uM("fontSize" to 16, "lineHeight" to "22px", "color" to "#0F172A", "fontWeight" to "bold")), "section-subtitle" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#64748B")), "empty-line" to _pS(_uM("paddingTop" to 12, "paddingBottom" to 12, "alignItems" to "center")), "empty-line-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#94A3B8")), "item-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to 10, "paddingBottom" to 10, "borderTopWidth" to 1, "borderTopStyle" to "solid", "borderTopColor" to "#EEF2F7")), "item-row-main" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingRight" to 10)), "item-title" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#111827", "fontWeight" to "bold")), "item-desc" to _pS(_uM("fontSize" to 12, "lineHeight" to "18px", "color" to "#64748B", "marginTop" to 2)), "item-qty" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#0F172A", "fontWeight" to "bold")), "copy-btn" to _pS(_uM("height" to 30, "paddingLeft" to 12, "paddingRight" to 12, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#0F172A", "alignItems" to "center", "justifyContent" to "center")), "copy-btn-text" to _pS(_uM("fontSize" to 13, "color" to "#FFFFFF")), "payload-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "18px", "color" to "#334155")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
