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
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.setClipboardData as uni_setClipboardData
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesOrdersIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesOrdersIndex) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesOrdersIndex
            val _cache = __ins.renderCache
            val keyword = ref("")
            val statusValue = ref("")
            val paymentValue = ref("")
            val inventoryValue = ref("")
            val orders = ref(_uA<OrderItem>())
            val isLoading = ref(false)
            val errorMessage = ref("")
            val currentPage = ref(1)
            val totalPages = ref(1)
            val totalCount = ref(0)
            val pageSize = ref(20)
            val pageTotalAmount = ref("0.00")
            val statistics = ref<OrderStatistics>(OrderStatistics(total_count = 0, inventory_deducted_count = 0, inventory_pending_count = 0, received_count = 0, processed_count = 0, failed_count = 0))
            val statusFilters = ref(_uA<FilterItem>(FilterItem(key = "", text = "全部状态"), FilterItem(key = "received", text = "已接收"), FilterItem(key = "processed", text = "已处理"), FilterItem(key = "failed", text = "处理失败")))
            val paymentFilters = ref(_uA<FilterItem>(FilterItem(key = "", text = "全部支付"), FilterItem(key = "cash", text = "现金"), FilterItem(key = "card", text = "银行卡"), FilterItem(key = "mixed", text = "混合支付"), FilterItem(key = "other", text = "其他")))
            val inventoryFilters = ref(_uA<FilterItem>(FilterItem(key = "", text = "全部库存"), FilterItem(key = "false", text = "待扣减"), FilterItem(key = "true", text = "已扣减")))
            val fieldConfig = ref(_uA<UTSJSONObject>(_uO("key" to "shopText", "label" to "店铺:"), _uO("key" to "cashierText", "label" to "收银员:"), _uO("key" to "itemsText", "label" to "商品:"), _uO("key" to "inventoryText", "label" to "库存:")))
            val menuActions = ref(_uA<UTSJSONObject>(_uO("key" to "detail", "text" to "详情"), _uO("key" to "copy", "text" to "复制单号")))
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
                        val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/orders/index.uvue:168")
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
            fun gen_buildQuery_fn(page: Number): OrderListQuery {
                val query = OrderListQuery(search = if (keyword.value == "") {
                    null
                } else {
                    keyword.value
                }
                , page = page, page_size = pageSize.value, status = if (statusValue.value == "") {
                    null
                } else {
                    statusValue.value
                }
                , payment_method = if (paymentValue.value == "") {
                    null
                } else {
                    paymentValue.value
                }
                , inventory_deducted = if (inventoryValue.value == "") {
                    null
                } else {
                    inventoryValue.value
                }
                , date_from = null, date_to = null)
                return query
            }
            val buildQuery = ::gen_buildQuery_fn
            fun gen_applyResponse_fn(response: OrderListResponse) {
                orders.value = response.results
                currentPage.value = response.current_page
                totalPages.value = response.total_pages
                totalCount.value = response.total_count
                pageSize.value = response.page_size
                var total: Number = 0.0
                run {
                    var index: Number = 0
                    while(index < response.results.length){
                        val amount = parseFloat(response.results[index].total_amount)
                        if (!isNaN(amount)) {
                            total = total + amount
                        }
                        index += 1
                    }
                }
                pageTotalAmount.value = total.toFixed(2)
            }
            val applyResponse = ::gen_applyResponse_fn
            fun gen_loadStatistics_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        try {
                            statistics.value = await(getOrderStatistics(buildQuery(1)))
                        }
                         catch (error: Throwable) {}
                })
            }
            val loadStatistics = ::gen_loadStatistics_fn
            fun gen_loadOrders_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (isLoading.value) {
                            return@w1
                        }
                        isLoading.value = true
                        errorMessage.value = ""
                        try {
                            val response = await(getOrderList(buildQuery(currentPage.value)))
                            applyResponse(response)
                            await(loadStatistics())
                        }
                         catch (error: Throwable) {
                            orders.value = _uA()
                            currentPage.value = 1
                            totalPages.value = 1
                            totalCount.value = 0
                            pageTotalAmount.value = "0.00"
                            errorMessage.value = parseErrorMessage(error, "订单加载失败")
                        }
                         finally {
                            isLoading.value = false
                        }
                })
            }
            val loadOrders = ::gen_loadOrders_fn
            fun gen_orderToListItem_fn(item: OrderItem): UTSJSONObject {
                val shopText = stringValue(item.shop_name, if (item.shop > 0) {
                    "店铺 #" + item.shop.toString(10)
                } else {
                    "-"
                }
                )
                val timeText = stringValue(item.order_time, stringValue(item.created_at, "-"))
                val itemCountText = item.item_count.toString(10) + "项 / " + item.quantity_count.toString(10) + "件"
                return _uO("id" to item.id.toString(10), "rawId" to item.id.toString(10), "orderNumber" to item.order_number, "title" to stringValue(item.order_number, "订单 #" + item.id.toString(10)), "subtitle" to ("时间：" + timeText), "amountText" to ("¥ " + stringValue(item.total_amount, "0.00")), "shopText" to shopText, "cashierText" to (stringValue(item.cashier_id, "-") + " / " + stringValue(item.kasa_number, "-")), "itemsText" to itemCountText, "inventoryText" to if (item.inventory_deducted) {
                    "已扣减"
                } else {
                    "未扣减"
                }
                , "tags" to _uA<String>(statusText(item), paymentText(item)))
            }
            val orderToListItem = ::gen_orderToListItem_fn
            fun gen_reloadFirstPage_fn() {
                currentPage.value = 1
                loadOrders()
            }
            val reloadFirstPage = ::gen_reloadFirstPage_fn
            fun gen_selectStatus_fn(value: Any) {
                val next = stringValue(value)
                if (statusValue.value == next) {
                    return
                }
                statusValue.value = next
                reloadFirstPage()
            }
            val selectStatus = ::gen_selectStatus_fn
            fun gen_selectPayment_fn(value: Any) {
                val next = stringValue(value)
                if (paymentValue.value == next) {
                    return
                }
                paymentValue.value = next
                reloadFirstPage()
            }
            val selectPayment = ::gen_selectPayment_fn
            fun gen_selectInventory_fn(value: Any) {
                val next = stringValue(value)
                if (inventoryValue.value == next) {
                    return
                }
                inventoryValue.value = next
                reloadFirstPage()
            }
            val selectInventory = ::gen_selectInventory_fn
            fun gen_handleSearchInput_fn(value: String) {
                keyword.value = value
            }
            val handleSearchInput = ::gen_handleSearchInput_fn
            fun gen_handleSearchConfirm_fn(value: String) {
                keyword.value = value
                reloadFirstPage()
            }
            val handleSearchConfirm = ::gen_handleSearchConfirm_fn
            fun gen_handleSearchClear_fn() {
                keyword.value = ""
                reloadFirstPage()
            }
            val handleSearchClear = ::gen_handleSearchClear_fn
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
                loadOrders()
            }
            val handlePageChange = ::gen_handlePageChange_fn
            fun gen_openDetail_fn(id: String) {
                if (id == "") {
                    return
                }
                uni_navigateTo(NavigateToOptions(url = "/pages/orders/from?id=" + id))
            }
            val openDetail = ::gen_openDetail_fn
            fun gen_handleItemClick_fn(payload: UTSJSONObject) {
                openDetail(stringValue(payload["rawId"], stringValue(payload["id"])))
            }
            val handleItemClick = ::gen_handleItemClick_fn
            fun gen_handleSubtitleClick_fn(payload: UTSJSONObject) {
                copyText(stringValue(payload["value"]), "时间已复制", "暂无时间")
            }
            val handleSubtitleClick = ::gen_handleSubtitleClick_fn
            fun gen_handleMetaClick_fn(payload: UTSJSONObject) {
                copyText(stringValue(payload["value"]), "金额已复制", "暂无金额")
            }
            val handleMetaClick = ::gen_handleMetaClick_fn
            fun gen_handleFieldClick_fn(payload: UTSJSONObject) {
                copyText(stringValue(payload["value"]), "内容已复制", "暂无内容")
            }
            val handleFieldClick = ::gen_handleFieldClick_fn
            fun gen_handleMenu_fn(payload: UTSJSONObject) {
                val action = payload["action"]
                val item = payload["item"]
                if (action == null || item == null) {
                    return
                }
                val actionKey = stringValue((action as UTSJSONObject)["key"])
                val itemObject = item as UTSJSONObject
                val id = stringValue(itemObject["rawId"])
                if (actionKey == "detail") {
                    openDetail(id)
                    return
                }
                if (actionKey == "copy") {
                    copyText(stringValue(itemObject["orderNumber"]), "订单号已复制", "暂无订单号")
                }
            }
            val handleMenu = ::gen_handleMenu_fn
            val listItems = computed(fun(): UTSArray<UTSJSONObject> {
                val result: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < orders.value.length){
                        result.push(orderToListItem(orders.value[index]))
                        index += 1
                    }
                }
                return result
            }
            )
            val emptyText = computed(fun(): String {
                if (isLoading.value) {
                    return "正在加载"
                }
                if (keyword.value != "" || statusValue.value != "" || paymentValue.value != "" || inventoryValue.value != "") {
                    return "没有匹配的订单"
                }
                return "暂无订单"
            }
            )
            val summaryItems = computed(fun(): UTSArray<UTSJSONObject> {
                return _uA(
                    _uO("key" to "total", "label" to "订单数", "value" to totalCount.value.toString(10)),
                    _uO("key" to "pageAmount", "label" to "本页金额", "value" to ("¥ " + pageTotalAmount.value)),
                    _uO("key" to "processed", "label" to "已处理", "value" to statistics.value.processed_count.toString(10)),
                    _uO("key" to "pendingStock", "label" to "待扣库存", "value" to statistics.value.inventory_pending_count.toString(10))
                )
            }
            )
            onLoad(fun(_options){
                loadOrders()
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList", GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalListClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to "订单管理", "searchPlaceholder" to "订单号、收银员、收银台", "searchValue" to unref(keyword), "filterVisible" to false, "showBack" to true, "showSearch" to true, "showFilter" to false, "showHome" to true, "homePath" to "/pages/tabbar/settings", "onSearchInput" to handleSearchInput, "onSearchConfirm" to handleSearchConfirm, "onSearchClear" to handleSearchClear), null, 8, _uA(
                        "searchValue"
                    )),
                    _cE("scroll-view", _uM("style" to _nS(_uM("flex" to "1")), "class" to "page-scroll"), _uA(
                        _cE("view", _uM("class" to "page-content"), _uA(
                            _cE("view", _uM("class" to "filter-section"), _uA(
                                _cE("scroll-view", _uM("scroll-x" to "true", "class" to "filter-scroll"), _uA(
                                    _cE("view", _uM("class" to "filter-row"), _uA(
                                        _cE(Fragment, null, RenderHelpers.renderList(unref(statusFilters), fun(item, __key, __index, _cached): Any {
                                            return _cE("view", _uM("key" to item.key, "class" to _nC(if (unref(statusValue) == item.key) {
                                                "filter-chip filter-chip-active"
                                            } else {
                                                "filter-chip"
                                            }
                                            ), "onClick" to fun(){
                                                selectStatus(item.key)
                                            }
                                            ), _uA(
                                                _cE("text", _uM("class" to _nC(if (unref(statusValue) == item.key) {
                                                    "filter-chip-text filter-chip-text-active"
                                                } else {
                                                    "filter-chip-text"
                                                }
                                                )), _tD(item.text), 3)
                                            ), 10, _uA(
                                                "onClick"
                                            ))
                                        }
                                        ), 128)
                                    ))
                                )),
                                _cE("scroll-view", _uM("scroll-x" to "true", "class" to "filter-scroll"), _uA(
                                    _cE("view", _uM("class" to "filter-row"), _uA(
                                        _cE(Fragment, null, RenderHelpers.renderList(unref(paymentFilters), fun(item, __key, __index, _cached): Any {
                                            return _cE("view", _uM("key" to item.key, "class" to _nC(if (unref(paymentValue) == item.key) {
                                                "filter-chip filter-chip-active"
                                            } else {
                                                "filter-chip"
                                            }
                                            ), "onClick" to fun(){
                                                selectPayment(item.key)
                                            }
                                            ), _uA(
                                                _cE("text", _uM("class" to _nC(if (unref(paymentValue) == item.key) {
                                                    "filter-chip-text filter-chip-text-active"
                                                } else {
                                                    "filter-chip-text"
                                                }
                                                )), _tD(item.text), 3)
                                            ), 10, _uA(
                                                "onClick"
                                            ))
                                        }
                                        ), 128)
                                    ))
                                )),
                                _cE("scroll-view", _uM("scroll-x" to "true", "class" to "filter-scroll"), _uA(
                                    _cE("view", _uM("class" to "filter-row"), _uA(
                                        _cE(Fragment, null, RenderHelpers.renderList(unref(inventoryFilters), fun(item, __key, __index, _cached): Any {
                                            return _cE("view", _uM("key" to item.key, "class" to _nC(if (unref(inventoryValue) == item.key) {
                                                "filter-chip filter-chip-active"
                                            } else {
                                                "filter-chip"
                                            }
                                            ), "onClick" to fun(){
                                                selectInventory(item.key)
                                            }
                                            ), _uA(
                                                _cE("text", _uM("class" to _nC(if (unref(inventoryValue) == item.key) {
                                                    "filter-chip-text filter-chip-text-active"
                                                } else {
                                                    "filter-chip-text"
                                                }
                                                )), _tD(item.text), 3)
                                            ), 10, _uA(
                                                "onClick"
                                            ))
                                        }
                                        ), 128)
                                    ))
                                ))
                            )),
                            if (isTrue(unref(errorMessage) != "" && !unref(isLoading))) {
                                _cE("view", _uM("key" to 0, "class" to "error-card"), _uA(
                                    _cE("text", _uM("class" to "error-title"), "加载失败"),
                                    _cE("text", _uM("class" to "error-desc"), _tD(unref(errorMessage)), 1),
                                    _cE("view", _uM("class" to "retry-btn", "onClick" to loadOrders), _uA(
                                        _cE("text", _uM("class" to "retry-btn-text"), "重新加载")
                                    ))
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            _cV(_component_lili_UniversalList, _uM("items" to listItems.value, "keyField" to "id", "titleField" to "title", "subtitleField" to "subtitle", "metaField" to "amountText", "tagField" to "tags", "fields" to unref(fieldConfig), "loading" to unref(isLoading), "loadingText" to "正在加载订单", "keepContentOnLoading" to true, "inlineLoadingText" to "订单数据刷新中...", "emptyText" to emptyText.value, "emptyIcon" to "◎", "showMenu" to true, "menuActions" to unref(menuActions), "showChevron" to false, "showPagination" to true, "currentPage" to unref(currentPage), "totalPages" to unref(totalPages), "totalCount" to unref(totalCount), "summaryTitle" to "订单概览", "summaryItems" to summaryItems.value, "summaryCollapsedByDefault" to false, "showFloatingAdd" to false, "onItemClick" to handleItemClick, "onSubtitleClick" to handleSubtitleClick, "onMetaClick" to handleMetaClick, "onFieldClick" to handleFieldClick, "onMenu" to handleMenu, "onPageChange" to handlePageChange), null, 8, _uA(
                                "items",
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-scroll" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-content" to _pS(_uM("paddingTop" to 6, "paddingRight" to 6, "paddingBottom" to 96, "paddingLeft" to 6)), "filter-section" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 8, "paddingBottom" to 8, "marginBottom" to 8, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E5E7EB", "borderRightColor" to "#E5E7EB", "borderBottomColor" to "#E5E7EB", "borderLeftColor" to "#E5E7EB")), "filter-scroll" to _pS(_uM("height" to 42)), "filter-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "paddingLeft" to 8, "paddingRight" to 8)), "filter-chip" to _pS(_uM("height" to 32, "paddingLeft" to 12, "paddingRight" to 12, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#D7DEE8", "borderRightColor" to "#D7DEE8", "borderBottomColor" to "#D7DEE8", "borderLeftColor" to "#D7DEE8", "backgroundColor" to "#FFFFFF", "alignItems" to "center", "justifyContent" to "center", "marginRight" to 8)), "filter-chip-active" to _pS(_uM("backgroundColor" to "#0F172A", "borderTopColor" to "#0F172A", "borderRightColor" to "#0F172A", "borderBottomColor" to "#0F172A", "borderLeftColor" to "#0F172A")), "filter-chip-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#334155")), "filter-chip-text-active" to _pS(_uM("color" to "#FFFFFF", "fontWeight" to "bold")), "error-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 18, "paddingRight" to 18, "paddingBottom" to 18, "paddingLeft" to 18, "marginBottom" to 10, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FECACA", "borderRightColor" to "#FECACA", "borderBottomColor" to "#FECACA", "borderLeftColor" to "#FECACA", "alignItems" to "center")), "error-title" to _pS(_uM("fontSize" to 18, "lineHeight" to "24px", "color" to "#B42318", "fontWeight" to "bold")), "error-desc" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#7F1D1D", "marginTop" to 8, "textAlign" to "center")), "retry-btn" to _pS(_uM("marginTop" to 14, "height" to 40, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#0F172A", "paddingLeft" to 18, "paddingRight" to 18, "alignItems" to "center", "justifyContent" to "center")), "retry-btn-text" to _pS(_uM("fontSize" to 14, "color" to "#FFFFFF")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
