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
            val filterVisible = ref(false)
            val orders = ref(_uA<OrderItem>())
            val isLoading = ref(false)
            val reloadAfterLoading = ref(false)
            val errorMessage = ref("")
            val currentPage = ref(1)
            val totalPages = ref(1)
            val totalCount = ref(0)
            val pageSize = ref(20)
            val pageTotalAmount = ref("0.00")
            val filterOptionsLoading = ref(false)
            val filterOptionsError = ref("")
            val filterOptions = ref<OrderFilterOptionsResponse?>(null)
            val selectedFilters = ref(_uA<OrderSelectedFilter>())
            val datePresetValue = ref("all")
            val datePresetOptions = ref(_uA<DatePresetOption>(DatePresetOption(key = "all", text = "全部"), DatePresetOption(key = "today", text = "今天"), DatePresetOption(key = "week", text = "本周"), DatePresetOption(key = "month", text = "本月"), DatePresetOption(key = "year", text = "本年")))
            val statistics = ref<OrderStatistics>(OrderStatistics(total_count = 0, inventory_deducted_count = 0, inventory_pending_count = 0, received_count = 0, processed_count = 0, failed_count = 0))
            val fieldConfig = ref(_uA<UTSJSONObject>(_uO("key" to "shopText", "label" to "店铺:"), _uO("key" to "cashierText", "label" to "收银员:"), _uO("key" to "itemsText", "label" to "商品:"), _uO("key" to "amountDetailText", "label" to "金额:"), _uO("key" to "inventoryText", "label" to "库存:")))
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
                    val errorText = JSON.stringify(error)
                    if (errorText != null && errorText != "") {
                        val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/orders/index.uvue:189")
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
                    uni_showToast(ShowToastOptions(title = emptyTitle, icon = "none", duration = 3500))
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
            fun gen_setSelectedFilterValue_fn(param: String, value: String) {
                val nextFilters: UTSArray<OrderSelectedFilter> = _uA()
                var updated = false
                run {
                    var index: Number = 0
                    while(index < selectedFilters.value.length){
                        val filter = selectedFilters.value[index]
                        if (filter.param == param) {
                            if (value != "") {
                                nextFilters.push(OrderSelectedFilter(param = param, value = value))
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
                    nextFilters.push(OrderSelectedFilter(param = param, value = value))
                }
                selectedFilters.value = nextFilters
            }
            val setSelectedFilterValue = ::gen_setSelectedFilterValue_fn
            fun gen_selectedFilterValue_fn(param: String): String {
                run {
                    var index: Number = 0
                    while(index < selectedFilters.value.length){
                        val filter = selectedFilters.value[index]
                        if (filter.param == param) {
                            return filter.value
                        }
                        index += 1
                    }
                }
                return ""
            }
            val selectedFilterValue = ::gen_selectedFilterValue_fn
            fun gen_isFilterOptionSelected_fn(param: String, value: String): Boolean {
                return selectedFilterValue(param) == value
            }
            val isFilterOptionSelected = ::gen_isFilterOptionSelected_fn
            fun toggleFilterOption(param: String, value: String, multiple: Boolean = false) {
                if (!multiple) {
                    val currentValue = selectedFilterValue(param)
                    setSelectedFilterValue(param, if (currentValue == value) {
                        ""
                    } else {
                        value
                    }
                    )
                    return
                }
                val currentValue = selectedFilterValue(param)
                val parts = if (currentValue == "") {
                    _uA<String>()
                } else {
                    currentValue.split(",")
                }
                val nextParts: UTSArray<String> = _uA()
                var removed = false
                run {
                    var index: Number = 0
                    while(index < parts.length){
                        if (parts[index] == value) {
                            removed = true
                            index += 1
                            continue
                        }
                        nextParts.push(parts[index])
                        index += 1
                    }
                }
                if (!removed) {
                    nextParts.push(value)
                }
                setSelectedFilterValue(param, nextParts.join(","))
            }
            fun gen_pad2_fn(value: Number): String {
                return if (value < 10) {
                    "0" + value.toString(10)
                } else {
                    value.toString(10)
                }
            }
            val pad2 = ::gen_pad2_fn
            fun gen_dateValue_fn(date: Date): String {
                return date.getFullYear().toString(10) + "-" + pad2(date.getMonth() + 1) + "-" + pad2(date.getDate())
            }
            val dateValue = ::gen_dateValue_fn
            fun gen_addDays_fn(date: Date, days: Number): Date {
                return Date(date.getFullYear(), date.getMonth(), date.getDate() + days)
            }
            val addDays = ::gen_addDays_fn
            fun gen_todayDate_fn(): Date {
                val now = Date()
                return Date(now.getFullYear(), now.getMonth(), now.getDate())
            }
            val todayDate = ::gen_todayDate_fn
            fun gen_datePresetLabel_fn(): String {
                if (datePresetValue.value == "week") {
                    return "本周"
                }
                if (datePresetValue.value == "month") {
                    return "本月"
                }
                if (datePresetValue.value == "year") {
                    return "本年"
                }
                if (datePresetValue.value == "all") {
                    return "全部"
                }
                return "今天"
            }
            val datePresetLabel = ::gen_datePresetLabel_fn
            fun gen_dateRangeFrom_fn(): String? {
                if (datePresetValue.value == "all") {
                    return null
                }
                val today = todayDate()
                if (datePresetValue.value == "week") {
                    val weekday = today.getDay()
                    val diff = if (weekday == 0) {
                        6
                    } else {
                        weekday - 1
                    }
                    return dateValue(addDays(today, 0 - diff))
                }
                if (datePresetValue.value == "month") {
                    return dateValue(Date(today.getFullYear(), today.getMonth(), 1))
                }
                if (datePresetValue.value == "year") {
                    return dateValue(Date(today.getFullYear(), 0, 1))
                }
                return dateValue(today)
            }
            val dateRangeFrom = ::gen_dateRangeFrom_fn
            fun gen_dateRangeTo_fn(): String? {
                if (datePresetValue.value == "all") {
                    return null
                }
                return dateValue(todayDate())
            }
            val dateRangeTo = ::gen_dateRangeTo_fn
            fun gen_selectDatePreset_fn(value: String) {
                datePresetValue.value = value
            }
            val selectDatePreset = ::gen_selectDatePreset_fn
            fun gen_buildQuery_fn(page: Number): OrderListQuery {
                val query = OrderListQuery(search = if (keyword.value == "") {
                    null
                } else {
                    keyword.value
                }
                , page = page, page_size = pageSize.value, status = if (selectedFilterValue("status") == "") {
                    null
                } else {
                    selectedFilterValue("status")
                }
                , payment_method = if (selectedFilterValue("payment_method") == "") {
                    null
                } else {
                    selectedFilterValue("payment_method")
                }
                , cashier_id = if (selectedFilterValue("cashier_id") == "") {
                    null
                } else {
                    selectedFilterValue("cashier_id")
                }
                , inventory_deducted = if (selectedFilterValue("inventory_deducted") == "") {
                    null
                } else {
                    selectedFilterValue("inventory_deducted")
                }
                , date_from = dateRangeFrom(), date_to = dateRangeTo())
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
                            reloadAfterLoading.value = true
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
                            if (reloadAfterLoading.value) {
                                reloadAfterLoading.value = false
                                gen_loadOrders_fn()
                            }
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
                val amountDetailText = "小计 " + stringValue(item.subtotal, "0.00") + " / 税 " + stringValue(item.tax_amount, "0.00") + " / 折扣 " + stringValue(item.discount_amount, "0.00")
                return _uO("id" to item.id.toString(10), "rawId" to item.id.toString(10), "orderNumber" to item.order_number, "title" to stringValue(item.order_number, "订单 #" + item.id.toString(10)), "subtitle" to ("时间：" + timeText), "amountText" to ("¥ " + stringValue(item.total_amount, "0.00")), "shopText" to shopText, "cashierText" to (stringValue(item.cashier_id, "-") + " / " + stringValue(item.kasa_number, "-")), "itemsText" to itemCountText, "amountDetailText" to amountDetailText, "inventoryText" to if (item.inventory_deducted) {
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
            fun gen_handleFilterVisibleChange_fn(value: Boolean) {
                filterVisible.value = value
            }
            val handleFilterVisibleChange = ::gen_handleFilterVisibleChange_fn
            fun gen_loadFilterOptions_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (filterOptions.value != null || filterOptionsLoading.value) {
                            return@w1
                        }
                        filterOptionsLoading.value = true
                        filterOptionsError.value = ""
                        try {
                            filterOptions.value = await(getOrderFilterOptions())
                        }
                         catch (error: Throwable) {
                            filterOptionsError.value = parseErrorMessage(error, "筛选选项加载失败")
                        }
                         finally {
                            filterOptionsLoading.value = false
                        }
                })
            }
            val loadFilterOptions = ::gen_loadFilterOptions_fn
            fun gen_handleFilterOpen_fn() {
                loadFilterOptions()
            }
            val handleFilterOpen = ::gen_handleFilterOpen_fn
            fun gen_handleFilterReset_fn() {
                selectedFilters.value = _uA<OrderSelectedFilter>()
                datePresetValue.value = "all"
                filterVisible.value = false
                reloadFirstPage()
            }
            val handleFilterReset = ::gen_handleFilterReset_fn
            fun gen_applySelectedFilters_fn() {
                filterVisible.value = false
                reloadFirstPage()
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
                if (keyword.value != "" || selectedFilters.value.length > 0) {
                    return datePresetLabel() + "没有匹配的订单"
                }
                if (datePresetValue.value != "all") {
                    return datePresetLabel() + "暂无订单"
                }
                return "暂无订单"
            }
            )
            val hasActiveFilter = computed(fun(): Boolean {
                return selectedFilters.value.length > 0 || datePresetValue.value != "all"
            }
            )
            val filterDefinitions = computed(fun(): UTSArray<OrderFilterDefinition> {
                if (filterOptions.value == null) {
                    return _uA<OrderFilterDefinition>()
                }
                return filterOptions.value!!.filters
            }
            )
            val summaryItems = computed(fun(): UTSArray<UTSJSONObject> {
                return _uA(
                    _uO("key" to "dateRange", "label" to "范围", "value" to datePresetLabel()),
                    _uO("key" to "total", "label" to "订单数", "value" to totalCount.value.toString(10)),
                    _uO("key" to "pageAmount", "label" to "本页金额", "value" to ("¥ " + pageTotalAmount.value)),
                    _uO("key" to "processed", "label" to "已处理", "value" to statistics.value.processed_count.toString(10)),
                    _uO("key" to "pendingStock", "label" to "待扣库存", "value" to statistics.value.inventory_pending_count.toString(10)),
                    _uO("key" to "failed", "label" to "异常", "value" to statistics.value.failed_count.toString(10))
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
                    _cV(_component_lili_universal_filter, _uM("title" to "订单管理", "searchPlaceholder" to "订单号、收银员、收银台", "searchValue" to unref(keyword), "filterVisible" to unref(filterVisible), "showBack" to true, "showSearch" to true, "showFilter" to true, "filterActive" to hasActiveFilter.value, "filterText" to "筛选", "showHome" to true, "homePath" to "/pages/tabbar/settings", "onSearchInput" to handleSearchInput, "onSearchConfirm" to handleSearchConfirm, "onSearchClear" to handleSearchClear, "onUpdate:filterVisible" to handleFilterVisibleChange, "onFilterOpen" to handleFilterOpen), _uM("filter-panel" to withSlotCtx(fun(): UTSArray<Any> {
                        return _uA(
                            _cE("view", _uM("class" to "order-filter-panel"), _uA(
                                _cE("view", _uM("class" to "order-filter-groups"), _uA(
                                    _cE("view", _uM("class" to "order-filter-group"), _uA(
                                        _cE("text", _uM("class" to "order-filter-title"), "日期范围"),
                                        _cE("view", _uM("class" to "order-filter-options"), _uA(
                                            _cE(Fragment, null, RenderHelpers.renderList(unref(datePresetOptions), fun(option, __key, __index, _cached): Any {
                                                return _cE("view", _uM("key" to ("date-" + option.key), "class" to _nC(if (unref(datePresetValue) == option.key) {
                                                    "order-filter-option order-filter-option-active"
                                                } else {
                                                    "order-filter-option"
                                                }
                                                ), "onClick" to fun(){
                                                    selectDatePreset(option.key)
                                                }
                                                ), _uA(
                                                    _cE("text", _uM("class" to _nC(if (unref(datePresetValue) == option.key) {
                                                        "order-filter-option-text order-filter-option-text-active"
                                                    } else {
                                                        "order-filter-option-text"
                                                    }
                                                    )), _tD(option.text), 3)
                                                ), 10, _uA(
                                                    "onClick"
                                                ))
                                            }
                                            ), 128)
                                        ))
                                    ))
                                )),
                                if (isTrue(unref(filterOptionsLoading))) {
                                    _cE("view", _uM("key" to 0, "class" to "order-filter-state"), _uA(
                                        _cE("text", _uM("class" to "order-filter-state-text"), "筛选选项加载中...")
                                    ))
                                } else {
                                    if (unref(filterOptionsError) != "") {
                                        _cE("view", _uM("key" to 1, "class" to "order-filter-state"), _uA(
                                            _cE("text", _uM("class" to "order-filter-state-text"), _tD(unref(filterOptionsError)), 1)
                                        ))
                                    } else {
                                        if (filterDefinitions.value.length == 0) {
                                            _cE("view", _uM("key" to 2, "class" to "order-filter-state order-filter-state-small"), _uA(
                                                _cE("text", _uM("class" to "order-filter-state-text"), "暂无更多筛选项")
                                            ))
                                        } else {
                                            _cE("view", _uM("key" to 3, "class" to "order-filter-groups"), _uA(
                                                _cE(Fragment, null, RenderHelpers.renderList(filterDefinitions.value, fun(filter, __key, __index, _cached): Any {
                                                    return _cE("view", _uM("key" to filter.key, "class" to "order-filter-group"), _uA(
                                                        _cE("text", _uM("class" to "order-filter-title"), _tD(filter.label), 1),
                                                        _cE("view", _uM("class" to "order-filter-options"), _uA(
                                                            _cE(Fragment, null, RenderHelpers.renderList(filter.options, fun(option, __key, __index, _cached): Any {
                                                                return _cE("view", _uM("key" to (filter.key + "-" + option.value), "class" to _nC(if (isFilterOptionSelected(filter.param, option.value)) {
                                                                    "order-filter-option order-filter-option-active"
                                                                } else {
                                                                    "order-filter-option"
                                                                }
                                                                ), "onClick" to fun(){
                                                                    toggleFilterOption(filter.param, option.value, filter.multiple)
                                                                }
                                                                ), _uA(
                                                                    _cE("text", _uM("class" to _nC(if (isFilterOptionSelected(filter.param, option.value)) {
                                                                        "order-filter-option-text order-filter-option-text-active"
                                                                    } else {
                                                                        "order-filter-option-text"
                                                                    }
                                                                    )), _tD(option.label), 3)
                                                                ), 10, _uA(
                                                                    "onClick"
                                                                ))
                                                            }
                                                            ), 128)
                                                        ))
                                                    ))
                                                }
                                                ), 128)
                                            ))
                                        }
                                    }
                                }
                                ,
                                _cE("view", _uM("class" to "order-filter-actions"), _uA(
                                    _cE("view", _uM("class" to "order-filter-btn order-filter-btn-light", "onClick" to handleFilterReset), _uA(
                                        _cE("text", _uM("class" to "order-filter-btn-light-text"), "重置")
                                    )),
                                    _cE("view", _uM("class" to "order-filter-btn order-filter-btn-primary", "onClick" to applySelectedFilters), _uA(
                                        _cE("text", _uM("class" to "order-filter-btn-primary-text"), "应用")
                                    ))
                                ))
                            ))
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-scroll" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-content" to _pS(_uM("paddingTop" to 6, "paddingRight" to 6, "paddingBottom" to 96, "paddingLeft" to 6)), "order-filter-panel" to _pS(_uM("position" to "relative", "paddingTop" to 2, "paddingBottom" to 52, "backgroundColor" to "#FFFFFF")), "order-filter-state" to _pS(_uM("height" to 112, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#F8FAFC", "alignItems" to "center", "justifyContent" to "center")), "order-filter-state-small" to _pS(_uM("height" to 58)), "order-filter-state-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "17px", "color" to "#64748B")), "order-filter-groups" to _pS(_uM("marginBottom" to 6)), "order-filter-group" to _pS(_uM("paddingLeft" to 10, "paddingRight" to 10, "paddingTop" to 10, "paddingBottom" to 10, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#FFFFFF", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E5EAF1", "borderRightColor" to "#E5EAF1", "borderBottomColor" to "#E5EAF1", "borderLeftColor" to "#E5EAF1", "marginBottom" to 6)), "order-filter-title" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#0F172A", "fontWeight" to "bold")), "order-filter-options" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 8)), "order-filter-option" to _pS(_uM("minWidth" to 58, "height" to 30, "paddingLeft" to 10, "paddingRight" to 10, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#F8FAFC", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "alignItems" to "center", "justifyContent" to "center", "marginRight" to 6, "marginBottom" to 6)), "order-filter-option-active" to _pS(_uM("backgroundColor" to "#0F172A", "borderTopColor" to "#0F172A", "borderRightColor" to "#0F172A", "borderBottomColor" to "#0F172A", "borderLeftColor" to "#0F172A")), "order-filter-option-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "17px", "color" to "#334155")), "order-filter-option-text-active" to _pS(_uM("color" to "#FFFFFF", "fontWeight" to "bold")), "order-filter-actions" to _pS(_uM("position" to "absolute", "left" to 0, "right" to 0, "bottom" to 0, "flexDirection" to "row", "paddingTop" to 6, "paddingLeft" to 2, "paddingRight" to 2, "paddingBottom" to 4, "borderTopWidth" to 1, "borderTopStyle" to "solid", "borderTopColor" to "rgba(226,232,240,0.78)", "backgroundColor" to "#FFFFFF")), "order-filter-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 38, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "alignItems" to "center", "justifyContent" to "center")), "order-filter-btn-light" to _pS(_uM("backgroundColor" to "#F3F6FA", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "marginRight" to 8)), "order-filter-btn-primary" to _pS(_uM("backgroundColor" to "#0F172A")), "order-filter-btn-light-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#475569")), "order-filter-btn-primary-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#FFFFFF")), "error-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 18, "paddingRight" to 18, "paddingBottom" to 18, "paddingLeft" to 18, "marginBottom" to 10, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FECACA", "borderRightColor" to "#FECACA", "borderBottomColor" to "#FECACA", "borderLeftColor" to "#FECACA", "alignItems" to "center")), "error-title" to _pS(_uM("fontSize" to 18, "lineHeight" to "24px", "color" to "#B42318", "fontWeight" to "bold")), "error-desc" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#7F1D1D", "marginTop" to 8, "textAlign" to "center")), "retry-btn" to _pS(_uM("marginTop" to 14, "height" to 40, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#0F172A", "paddingLeft" to 18, "paddingRight" to 18, "alignItems" to "center", "justifyContent" to "center")), "retry-btn-text" to _pS(_uM("fontSize" to 14, "color" to "#FFFFFF")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
