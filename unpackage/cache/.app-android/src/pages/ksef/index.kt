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
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.removeStorageSync as uni_removeStorageSync
import io.dcloud.uniapp.extapi.setClipboardData as uni_setClipboardData
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesKsefIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesKsefIndex) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesKsefIndex
            val _cache = __ins.renderCache
            val refreshStorageKey = "refresh:pages:ksef:index"
            val keyword = ref("")
            val filterVisible = ref(false)
            val isLoading = ref(false)
            val errorMessage = ref("")
            val invoices = ref(_uA<KsefInvoiceItem>())
            val status = ref<KsefAutoSyncStatus?>(null)
            val supplierFilterId = ref("")
            val supplierFilterName = ref("")
            val currentPage = ref(1)
            val totalPages = ref(1)
            val totalCount = ref(0)
            val pageSize = ref(20)
            val filterOptionsLoading = ref(false)
            val filterOptionsError = ref("")
            val filterOptions = ref<KsefFilterOptionsResponse?>(null)
            val selectedFilters = ref(_uA<KsefSelectedFilter>())
            val fieldConfig = ref(_uA<UTSJSONObject>(_uO("key" to "supplier_name", "label" to "供应商"), _uO("key" to "payment_due_text", "label" to "到期日"), _uO("key" to "payment_review_text", "label" to "审核"), _uO("key" to "bank_account_text", "label" to "银行账号")))
            val menuActions = ref(_uA<UTSJSONObject>(_uO("key" to "view_detail", "text" to "付款详情"), _uO("key" to "download_xml", "text" to "同步详情"), _uO("key" to "copy_account", "text" to "复制账号"), _uO("key" to "copy_ksef", "text" to "复制KSeF号")))
            val defaultFilterDefinitions = ref(_uA<KsefFilterDefinition>(KsefFilterDefinition(key = "sync_status", param = "sync_status", label = "同步状态", control = "select", aliases = _uA<String>(), multiple = false, options = _uA(
                KsefFilterOption(value = "METADATA_ONLY", label = "待详情"),
                KsefFilterOption(value = "XML_DOWNLOADED", label = "已完成"),
                KsefFilterOption(value = "SYNC_ERROR", label = "异常")
            )), KsefFilterDefinition(key = "is_paid", param = "is_paid", label = "付款状态", control = "boolean", aliases = _uA<String>(), multiple = false, options = _uA(
                KsefFilterOption(value = "false", label = "未付款"),
                KsefFilterOption(value = "true", label = "已付款")
            )), KsefFilterDefinition(key = "payment_review_status", param = "payment_review_status", label = "支付审核", control = "select", aliases = _uA<String>(), multiple = false, options = _uA(
                KsefFilterOption(value = "PENDING", label = "待审核"),
                KsefFilterOption(value = "PAYABLE", label = "可支付"),
                KsefFilterOption(value = "NOT_PAYABLE", label = "不支付")
            )), KsefFilterDefinition(key = "has_xml", param = "has_xml", label = "XML 状态", control = "boolean", aliases = _uA<String>(), multiple = false, options = _uA(
                KsefFilterOption(value = "false", label = "未下载"),
                KsefFilterOption(value = "true", label = "已下载")
            )), KsefFilterDefinition(key = "ordering", param = "ordering", label = "排序方式", control = "select", aliases = _uA<String>(), multiple = false, options = _uA(
                KsefFilterOption(value = "payment_due_date", label = "到期日最近"),
                KsefFilterOption(value = "-payment_due_date", label = "到期日最远"),
                KsefFilterOption(value = "-issue_date", label = "开票日期最新"),
                KsefFilterOption(value = "issue_date", label = "开票日期最早"),
                KsefFilterOption(value = "-gross_amount", label = "金额最高"),
                KsefFilterOption(value = "gross_amount", label = "金额最低"),
                KsefFilterOption(value = "-created_at", label = "最近创建")
            ))))
            fun parseErrorMessage(error: Any, fallback: String = "操作失败"): String {
                var message = fallback
                if (error != null) {
                    val text = JSON.stringify(error)
                    if (text != null && text != "") {
                        message = text
                    }
                }
                return message
            }
            fun gen_applyListResponse_fn(response: KsefInvoiceListResponse) {
                invoices.value = response.results
                currentPage.value = response.current_page
                totalPages.value = response.total_pages
                totalCount.value = response.total_count
                pageSize.value = response.page_size
            }
            val applyListResponse = ::gen_applyListResponse_fn
            fun gen_setSelectedFilterValue_fn(param: String, value: String) {
                val nextFilters: UTSArray<KsefSelectedFilter> = _uA()
                var updated = false
                run {
                    var index: Number = 0
                    while(index < selectedFilters.value.length){
                        val filter = selectedFilters.value[index]
                        if (filter.param == param) {
                            if (value != "") {
                                nextFilters.push(KsefSelectedFilter(param = param, value = value))
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
                    nextFilters.push(KsefSelectedFilter(param = param, value = value))
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
            fun gen_filterValueOrNull_fn(param: String): String? {
                val value = selectedFilterValue(param)
                return if (value == "") {
                    null
                } else {
                    value
                }
            }
            val filterValueOrNull = ::gen_filterValueOrNull_fn
            fun gen_isFilterOptionSelected_fn(param: String, value: String): Boolean {
                return selectedFilterValue(param) == value
            }
            val isFilterOptionSelected = ::gen_isFilterOptionSelected_fn
            fun gen_clearFilterOption_fn(param: String) {
                setSelectedFilterValue(param, "")
            }
            val clearFilterOption = ::gen_clearFilterOption_fn
            fun gen_toggleFilterOption_fn(param: String, value: String) {
                val currentValue = selectedFilterValue(param)
                setSelectedFilterValue(param, if (currentValue == value) {
                    ""
                } else {
                    value
                }
                )
            }
            val toggleFilterOption = ::gen_toggleFilterOption_fn
            fun gen_loadFilterOptions_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (filterOptionsLoading.value) {
                            return@w1
                        }
                        filterOptionsLoading.value = true
                        filterOptionsError.value = ""
                        try {
                            filterOptions.value = await(getKsefInvoiceFilterOptions())
                        }
                         catch (error: Throwable) {
                            filterOptions.value = null
                            filterOptionsError.value = parseErrorMessage(error, "筛选选项加载失败")
                        }
                         finally {
                            filterOptionsLoading.value = false
                        }
                })
            }
            val loadFilterOptions = ::gen_loadFilterOptions_fn
            fun gen_loadInvoices_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (isLoading.value) {
                            return@w1
                        }
                        isLoading.value = true
                        errorMessage.value = ""
                        try {
                            val response = await(getKsefInvoiceList(KsefInvoiceListQuery(search = if (keyword.value == "") {
                                null
                            } else {
                                keyword.value
                            }
                            , page = currentPage.value, page_size = pageSize.value, supplier = if (supplierFilterId.value == "") {
                                null
                            } else {
                                supplierFilterId.value
                            }
                            , supplier_id = if (supplierFilterId.value == "") {
                                null
                            } else {
                                supplierFilterId.value
                            }
                            , sync_status = filterValueOrNull("sync_status"), is_paid = filterValueOrNull("is_paid"), has_xml = filterValueOrNull("has_xml"), payment_review_status = filterValueOrNull("payment_review_status"), ordering = filterValueOrNull("ordering"))))
                            applyListResponse(response)
                        }
                         catch (error: Throwable) {
                            invoices.value = _uA<KsefInvoiceItem>()
                            errorMessage.value = parseErrorMessage(error, "KSeF 发票加载失败")
                        }
                         finally {
                            isLoading.value = false
                        }
                })
            }
            val loadInvoices = ::gen_loadInvoices_fn
            fun gen_loadAutoSyncStatus_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        try {
                            status.value = await(getKsefAutoSyncStatus())
                        }
                         catch (error: Throwable) {
                            status.value = null
                        }
                })
            }
            val loadAutoSyncStatus = ::gen_loadAutoSyncStatus_fn
            fun gen_handleFilterVisibleChange_fn(value: Boolean) {
                filterVisible.value = value
            }
            val handleFilterVisibleChange = ::gen_handleFilterVisibleChange_fn
            fun gen_handleFilterOpen_fn() {
                loadFilterOptions()
            }
            val handleFilterOpen = ::gen_handleFilterOpen_fn
            fun gen_handleSearchInput_fn(value: String) {
                keyword.value = value
            }
            val handleSearchInput = ::gen_handleSearchInput_fn
            fun gen_handleSearchConfirm_fn(value: String) {
                keyword.value = value
                currentPage.value = 1
                loadInvoices()
            }
            val handleSearchConfirm = ::gen_handleSearchConfirm_fn
            fun gen_handleSearchClear_fn() {
                keyword.value = ""
                currentPage.value = 1
                loadInvoices()
            }
            val handleSearchClear = ::gen_handleSearchClear_fn
            fun gen_handleFilterReset_fn() {
                selectedFilters.value = _uA<KsefSelectedFilter>()
                keyword.value = ""
                currentPage.value = 1
                filterVisible.value = false
                loadInvoices()
            }
            val handleFilterReset = ::gen_handleFilterReset_fn
            fun gen_applyFilter_fn() {
                currentPage.value = 1
                filterVisible.value = false
                loadInvoices()
            }
            val applyFilter = ::gen_applyFilter_fn
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
                loadInvoices()
            }
            val handlePageChange = ::gen_handlePageChange_fn
            fun gen_paidText_fn(value: Boolean): String {
                return if (value) {
                    "已付款"
                } else {
                    "未付款"
                }
            }
            val paidText = ::gen_paidText_fn
            fun gen_reviewStatusText_fn(item: KsefInvoiceItem): String {
                if (item.payment_review_status_display != "") {
                    return item.payment_review_status_display
                }
                if (item.payment_review_status == "PAYABLE") {
                    return "可支付"
                }
                if (item.payment_review_status == "NOT_PAYABLE") {
                    return "不支付"
                }
                return "待审核"
            }
            val reviewStatusText = ::gen_reviewStatusText_fn
            fun gen_supplierText_fn(item: KsefInvoiceItem): String {
                if (item.supplier_name != "") {
                    return item.supplier_name
                }
                return "未关联供应商"
            }
            val supplierText = ::gen_supplierText_fn
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
            fun gen_displayText_fn(value: String): String {
                if (value == "") {
                    return "-"
                }
                return value
            }
            val displayText = ::gen_displayText_fn
            fun gen_normalizeAccount_fn(value: String): String {
                if (value == "") {
                    return ""
                }
                var result = ""
                run {
                    var index: Number = 0
                    while(index < value.length){
                        val char = value.substring(index, index + 1)
                        if (char >= "0" && char <= "9") {
                            result += char
                        }
                        index += 1
                    }
                }
                return if (result == "") {
                    value
                } else {
                    result
                }
            }
            val normalizeAccount = ::gen_normalizeAccount_fn
            fun gen_invoiceToListItem_fn(item: KsefInvoiceItem): UTSJSONObject {
                val statusTags = _uA(
                    paidText(item.is_paid),
                    reviewStatusText(item)
                ) as UTSArray<String>
                if (item.sync_status == "SYNC_ERROR") {
                    statusTags.push("异常")
                }
                return _uO("id" to item.id.toString(10), "name" to displayText(item.invoice_number), "codeText" to displayText(item.seller_name), "metaText" to (displayText(item.amount_due) + " " + displayText(item.currency) + " · " + paidText(item.is_paid)), "supplier_name" to supplierText(item), "seller_nip" to displayText(item.seller_nip), "payment_due_text" to compactDate(item.payment_due_date), "payment_review_text" to reviewStatusText(item), "bank_account_text" to displayText(normalizeAccount(item.bank_account_number)), "rawId" to item.id.toString(10), "rawKsefNumber" to item.ksef_number, "rawBankAccount" to normalizeAccount(item.bank_account_number), "tags" to statusTags)
            }
            val invoiceToListItem = ::gen_invoiceToListItem_fn
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
            fun gen_handleDownloadXml_fn(invoiceId: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        try {
                            await(downloadKsefInvoiceXml(invoiceId))
                            uni_showToast(ShowToastOptions(title = takeLatestResponseMessage("详情同步完成"), icon = "success"))
                            loadInvoices()
                            loadAutoSyncStatus()
                        }
                         catch (error: Throwable) {
                            showErrorToast(parseErrorMessage(error, "详情同步失败"))
                        }
                })
            }
            val handleDownloadXml = ::gen_handleDownloadXml_fn
            fun gen_openInvoiceDetail_fn(invoiceId: String) {
                if (invoiceId == "") {
                    return
                }
                uni_navigateTo(NavigateToOptions(url = "/pages/ksef/detail?id=" + invoiceId))
            }
            val openInvoiceDetail = ::gen_openInvoiceDetail_fn
            fun gen_handleItemClick_fn(payload: UTSJSONObject) {
                val idValue = payload["rawId"]
                val invoiceId = if (idValue == null) {
                    ""
                } else {
                    (idValue as String)
                }
                openInvoiceDetail(invoiceId)
            }
            val handleItemClick = ::gen_handleItemClick_fn
            fun gen_handleMenu_fn(payload: UTSJSONObject) {
                val action = payload["action"]
                val item = payload["item"]
                if (action == null || item == null) {
                    return
                }
                val actionObject = action as UTSJSONObject
                val itemObject = item as UTSJSONObject
                val keyValue = actionObject["key"]
                if (keyValue == null) {
                    return
                }
                val key = keyValue as String
                val idValue = itemObject["rawId"]
                val invoiceId = if (idValue == null) {
                    ""
                } else {
                    (idValue as String)
                }
                if (key == "view_detail") {
                    openInvoiceDetail(invoiceId)
                    return
                }
                if (key == "download_xml") {
                    handleDownloadXml(invoiceId)
                    return
                }
                if (key == "copy_account") {
                    val accountValue = itemObject["rawBankAccount"]
                    val accountText = if (accountValue == null) {
                        ""
                    } else {
                        (accountValue as String)
                    }
                    copyText(accountText, "账号已复制", "暂无银行账号")
                    return
                }
                if (key == "copy_ksef") {
                    val ksefValue = itemObject["rawKsefNumber"]
                    val ksefText = if (ksefValue == null) {
                        ""
                    } else {
                        (ksefValue as String)
                    }
                    copyText(ksefText, "KSeF号已复制", "暂无KSeF号")
                }
            }
            val handleMenu = ::gen_handleMenu_fn
            fun gen_handleSubtitleClick_fn(payload: UTSJSONObject) {
                val item = payload["item"]
                if (item == null) {
                    return
                }
                val itemObject = item as UTSJSONObject
                val ksefValue = itemObject["rawKsefNumber"]
                val ksefText = if (ksefValue == null) {
                    ""
                } else {
                    (ksefValue as String)
                }
                copyText(ksefText, "KSeF号已复制", "暂无KSeF号")
            }
            val handleSubtitleClick = ::gen_handleSubtitleClick_fn
            fun gen_handleFieldClick_fn(payload: UTSJSONObject) {
                val item = payload["item"]
                val keyValue = payload["key"]
                if (item == null || keyValue == null) {
                    return
                }
                val key = keyValue as String
                val itemObject = item as UTSJSONObject
                if (key == "seller_nip" || key == "bank_account_text") {
                    val value = if (key == "seller_nip") {
                        itemObject["seller_nip"]
                    } else {
                        itemObject["rawBankAccount"]
                    }
                    val text = if (value == null) {
                        ""
                    } else {
                        (value as String)
                    }
                    copyText(text, if (key == "seller_nip") {
                        "卖方NIP已复制"
                    } else {
                        "账号已复制"
                    }
                    , if (key == "seller_nip") {
                        "暂无卖方NIP"
                    } else {
                        "暂无银行账号"
                    }
                    )
                }
            }
            val handleFieldClick = ::gen_handleFieldClick_fn
            val listItems = computed(fun(): UTSArray<UTSJSONObject> {
                val result: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < invoices.value.length){
                        result.push(invoiceToListItem(invoices.value[index]))
                        index += 1
                    }
                }
                return result
            }
            )
            val filterDefinitions = computed(fun(): UTSArray<KsefFilterDefinition> {
                if (filterOptions.value != null && filterOptions.value!!.filters.length > 0) {
                    return filterOptions.value!!.filters
                }
                return defaultFilterDefinitions.value
            }
            )
            val hasActiveFilter = computed(fun(): Boolean {
                return keyword.value != "" || selectedFilters.value.length > 0 || supplierFilterId.value != ""
            }
            )
            val emptyText = computed(fun(): String {
                if (isLoading.value) {
                    return "正在加载"
                }
                if (hasActiveFilter.value) {
                    return "没有匹配的 KSeF 发票"
                }
                return "暂无 KSeF 发票"
            }
            )
            val pendingXmlText = computed(fun(): String {
                if (status.value == null) {
                    return "-"
                }
                return status.value!!.pending_xml_count.toString(10)
            }
            )
            val lastSuccessText = computed(fun(): String {
                if (status.value == null) {
                    return "-"
                }
                return compactDate(status.value!!.last_success_at)
            }
            )
            val summaryItems = computed(fun(): UTSArray<UTSJSONObject> {
                val paymentFilter = selectedFilterValue("is_paid")
                val reviewFilter = selectedFilterValue("payment_review_status")
                return _uA(
                    _uO("key" to "total", "label" to "发票总数", "value" to totalCount.value.toString(10)),
                    _uO("key" to "supplier", "label" to "供应商", "value" to if (supplierFilterId.value == "") {
                        "全部"
                    } else {
                        if (supplierFilterName.value == "") {
                            ("#" + supplierFilterId.value)
                        } else {
                            supplierFilterName.value
                        }
                    }
                    ),
                    _uO("key" to "pending", "label" to "待详情", "value" to pendingXmlText.value),
                    _uO("key" to "payment", "label" to "付款筛选", "value" to if (paymentFilter == "") {
                        "全部"
                    } else {
                        if (paymentFilter == "true") {
                            "已付款"
                        } else {
                            "未付款"
                        }
                    }
                    ),
                    _uO("key" to "review", "label" to "支付审核", "value" to if (reviewFilter == "") {
                        "全部"
                    } else {
                        if (reviewFilter == "PAYABLE") {
                            "可支付"
                        } else {
                            if (reviewFilter == "NOT_PAYABLE") {
                                "不支付"
                            } else {
                                "待审核"
                            }
                        }
                    }
                    ),
                    _uO("key" to "last", "label" to "最近成功", "value" to lastSuccessText.value)
                )
            }
            )
            onLoad(fun(event: OnLoadOptions){
                val supplierIdValue = if (event["supplier_id"] == null) {
                    event["supplier"]
                } else {
                    event["supplier_id"]
                }
                supplierFilterId.value = if (supplierIdValue == null) {
                    ""
                } else {
                    (supplierIdValue as String)
                }
                val supplierNameValue = event["supplier_name"]
                if (supplierNameValue != null) {
                    val decodedSupplierName = UTSAndroid.consoleDebugError(decodeURIComponent(supplierNameValue as String), " at pages/ksef/index.uvue:612")
                    supplierFilterName.value = if (decodedSupplierName == null) {
                        ""
                    } else {
                        decodedSupplierName
                    }
                }
                val searchValue = event["search"]
                if (searchValue != null && searchValue != "") {
                    val decodedSearch = UTSAndroid.consoleDebugError(decodeURIComponent(searchValue as String), " at pages/ksef/index.uvue:617")
                    keyword.value = if (decodedSearch == null) {
                        ""
                    } else {
                        decodedSearch
                    }
                }
                loadAutoSyncStatus()
                loadInvoices()
            }
            )
            onShow(fun(){
                loadAutoSyncStatus()
                val flag = uni_getStorageSync(refreshStorageKey)
                if (flag != null && ("" + flag) != "") {
                    uni_removeStorageSync(refreshStorageKey)
                    loadInvoices()
                }
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList", GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalListClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to "KSeF 发票", "searchPlaceholder" to "发票号、KSeF号、卖方", "searchValue" to unref(keyword), "filterVisible" to unref(filterVisible), "showBack" to true, "showSearch" to true, "showFilter" to true, "showHome" to true, "filterActive" to hasActiveFilter.value, "filterText" to "重置", "homePath" to "/pages/tabbar/settings", "onSearchInput" to handleSearchInput, "onSearchConfirm" to handleSearchConfirm, "onSearchClear" to handleSearchClear, "onUpdate:filterVisible" to handleFilterVisibleChange, "onFilterOpen" to handleFilterOpen), _uM("filter-panel" to withSlotCtx(fun(): UTSArray<Any> {
                        return _uA(
                            _cE("view", _uM("class" to "list-filter-panel"), _uA(
                                if (isTrue(unref(filterOptionsLoading))) {
                                    _cE("view", _uM("key" to 0, "class" to "list-filter-state"), _uA(
                                        _cE("text", _uM("class" to "list-filter-state-text"), "筛选选项加载中...")
                                    ))
                                } else {
                                    if (unref(filterOptionsError) != "") {
                                        _cE("view", _uM("key" to 1, "class" to "list-filter-state"), _uA(
                                            _cE("text", _uM("class" to "list-filter-state-text"), _tD(unref(filterOptionsError)), 1)
                                        ))
                                    } else {
                                        _cE("view", _uM("key" to 2, "class" to "list-filter-groups"), _uA(
                                            _cE(Fragment, null, RenderHelpers.renderList(filterDefinitions.value, fun(filter, __key, __index, _cached): Any {
                                                return _cE("view", _uM("key" to filter.key, "class" to "list-filter-group"), _uA(
                                                    _cE("text", _uM("class" to "list-filter-group-title"), _tD(filter.label), 1),
                                                    _cE("view", _uM("class" to "list-filter-options"), _uA(
                                                        _cE("view", _uM("class" to _nC(if (isFilterOptionSelected(filter.param, "")) {
                                                            "list-filter-option list-filter-option-active"
                                                        } else {
                                                            "list-filter-option"
                                                        }
                                                        ), "onClick" to fun(){
                                                            clearFilterOption(filter.param)
                                                        }
                                                        ), _uA(
                                                            _cE("text", _uM("class" to _nC(if (isFilterOptionSelected(filter.param, "")) {
                                                                "list-filter-option-text list-filter-option-text-active"
                                                            } else {
                                                                "list-filter-option-text"
                                                            }
                                                            )), "全部", 2)
                                                        ), 10, _uA(
                                                            "onClick"
                                                        )),
                                                        _cE(Fragment, null, RenderHelpers.renderList(filter.options, fun(option, __key, __index, _cached): Any {
                                                            return _cE("view", _uM("key" to (filter.key + "-" + option.value), "class" to _nC(if (isFilterOptionSelected(filter.param, option.value)) {
                                                                "list-filter-option list-filter-option-active"
                                                            } else {
                                                                "list-filter-option"
                                                            }
                                                            ), "onClick" to fun(){
                                                                toggleFilterOption(filter.param, option.value)
                                                            }
                                                            ), _uA(
                                                                _cE("text", _uM("class" to _nC(if (isFilterOptionSelected(filter.param, option.value)) {
                                                                    "list-filter-option-text list-filter-option-text-active"
                                                                } else {
                                                                    "list-filter-option-text"
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
                                ,
                                _cE("view", _uM("class" to "list-filter-actions"), _uA(
                                    _cE("view", _uM("class" to "list-filter-btn list-filter-btn-light", "onClick" to handleFilterReset), _uA(
                                        _cE("text", _uM("class" to "list-filter-btn-light-text"), "重置")
                                    )),
                                    _cE("view", _uM("class" to "list-filter-btn list-filter-btn-primary", "onClick" to applyFilter), _uA(
                                        _cE("text", _uM("class" to "list-filter-btn-primary-text"), "应用")
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
                                    _cE("view", _uM("class" to "retry-btn", "onClick" to loadInvoices), _uA(
                                        _cE("text", _uM("class" to "retry-btn-text"), "重新加载")
                                    ))
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            _cV(_component_lili_UniversalList, _uM("items" to listItems.value, "keyField" to "id", "titleField" to "name", "subtitleField" to "codeText", "metaField" to "metaText", "tagField" to "tags", "fields" to unref(fieldConfig), "loading" to unref(isLoading), "loadingText" to "正在加载 KSeF 发票", "keepContentOnLoading" to true, "inlineLoadingText" to "KSeF 发票刷新中...", "emptyText" to emptyText.value, "emptyIcon" to "◎", "showMenu" to true, "menuActions" to unref(menuActions), "showChevron" to true, "showPagination" to true, "currentPage" to unref(currentPage), "totalPages" to unref(totalPages), "totalCount" to unref(totalCount), "showFloatingAdd" to false, "summaryTitle" to "KSeF 汇总", "summaryItems" to summaryItems.value, "summaryCollapsedByDefault" to true, "onItemClick" to handleItemClick, "onMenu" to handleMenu, "onPageChange" to handlePageChange, "onSubtitleClick" to handleSubtitleClick, "onFieldClick" to handleFieldClick), null, 8, _uA(
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-scroll" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-content" to _pS(_uM("paddingLeft" to 6, "paddingRight" to 6, "paddingTop" to 6, "paddingBottom" to 96)), "list-filter-panel" to _pS(_uM("paddingBottom" to 8)), "list-filter-state" to _pS(_uM("minHeight" to 64, "alignItems" to "center", "justifyContent" to "center")), "list-filter-state-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#64748B")), "list-filter-groups" to _pS(_uM("paddingBottom" to 2)), "list-filter-group" to _pS(_uM("marginBottom" to 10)), "list-filter-group-title" to _pS(_uM("fontSize" to 14, "lineHeight" to "18px", "color" to "#0F172A", "fontWeight" to "bold")), "list-filter-options" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 10)), "list-filter-option" to _pS(_uM("height" to 34, "paddingLeft" to 12, "paddingRight" to 12, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#F8FAFC", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "alignItems" to "center", "justifyContent" to "center", "marginRight" to 8, "marginBottom" to 8)), "list-filter-option-active" to _pS(_uM("backgroundColor" to "#0F172A", "borderTopColor" to "#0F172A", "borderRightColor" to "#0F172A", "borderBottomColor" to "#0F172A", "borderLeftColor" to "#0F172A")), "list-filter-option-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "13px", "color" to "#475569")), "list-filter-option-text-active" to _pS(_uM("color" to "#FFFFFF")), "list-filter-actions" to _pS(_uM("flexDirection" to "row", "marginTop" to 8)), "list-filter-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 40, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "alignItems" to "center", "justifyContent" to "center")), "list-filter-btn-light" to _pS(_uM("backgroundColor" to "#F3F6FA", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "marginRight" to 8)), "list-filter-btn-primary" to _pS(_uM("backgroundColor" to "#0F172A")), "list-filter-btn-light-text" to _pS(_uM("fontSize" to 14, "lineHeight" to "14px", "color" to "#475569")), "list-filter-btn-primary-text" to _pS(_uM("fontSize" to 14, "lineHeight" to "14px", "color" to "#FFFFFF")), "error-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 18, "paddingRight" to 18, "paddingBottom" to 18, "paddingLeft" to 18, "marginBottom" to 10, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FECACA", "borderRightColor" to "#FECACA", "borderBottomColor" to "#FECACA", "borderLeftColor" to "#FECACA", "alignItems" to "center")), "error-title" to _pS(_uM("fontSize" to 18, "lineHeight" to "24px", "color" to "#B42318", "fontWeight" to "bold")), "error-desc" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#7F1D1D", "marginTop" to 8, "textAlign" to "center")), "retry-btn" to _pS(_uM("marginTop" to 14, "height" to 40, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#0F172A", "paddingLeft" to 18, "paddingRight" to 18, "alignItems" to "center", "justifyContent" to "center")), "retry-btn-text" to _pS(_uM("fontSize" to 14, "color" to "#FFFFFF")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
