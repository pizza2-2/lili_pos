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
import io.dcloud.uniapp.extapi.getWindowInfo as uni_getWindowInfo
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.removeStorageSync as uni_removeStorageSync
import io.dcloud.uniapp.extapi.setClipboardData as uni_setClipboardData
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesTransactionsIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesTransactionsIndex) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesTransactionsIndex
            val _cache = __ins.renderCache
            val transactionListRefreshStorageKey = "refresh:pages:transactions:index"
            val supplierId = ref("")
            val supplierName = ref("")
            val keyword = ref("")
            val filterVisible = ref(false)
            val transactions = ref(_uA<TransactionItem>())
            val isLoading = ref(false)
            val errorMessage = ref("")
            val currentPage = ref(1)
            val totalPages = ref(1)
            val totalCount = ref(0)
            val pageSize = ref(20)
            val summary = ref<TransactionSummary?>(null)
            val statistics = ref<TransactionStatisticsResponse?>(null)
            val filterOptionsLoading = ref(false)
            val filterOptionsError = ref("")
            val filterOptions = ref<TransactionFilterOptionsResponse?>(null)
            val selectedFilters = ref(_uA<TransactionSelectedFilter>())
            val filterDateFrom = ref("")
            val filterDateTo = ref("")
            val filterAmountMin = ref("")
            val filterAmountMax = ref("")
            val selectedOrdering = ref("-transaction_date")
            val filterPanelHeight = ref(440)
            val filterContentHeight = ref(376)
            val fieldConfig = ref(_uA<UTSJSONObject>(_uO("key" to "transactionTypeText", "label" to "类型:"), _uO("key" to "filesText", "label" to "附件:"), _uO("key" to "noteText", "label" to "备注:")))
            val menuActions = ref(_uA<UTSJSONObject>(_uO("key" to "edit", "text" to "编辑"), _uO("key" to "delete", "text" to "删除")))
            val defaultFilterDefinitions = ref(_uA<TransactionFilterDefinition>(TransactionFilterDefinition(key = "transaction_type", param = "transaction_type", label = "记录类型", control = "choice", aliases = _uA<String>(), multiple = false, options = _uA(
                TransactionFilterOption(value = "1", label = "采购"),
                TransactionFilterOption(value = "2", label = "欠单"),
                TransactionFilterOption(value = "3", label = "还款")
            ))))
            val sortOptions = ref(_uA<UTSJSONObject>(_uO("value" to "-transaction_date", "label" to "日期最新"), _uO("value" to "transaction_date", "label" to "日期最早"), _uO("value" to "-amount", "label" to "金额最高"), _uO("value" to "amount", "label" to "金额最低"), _uO("value" to "-created_at", "label" to "最近创建")))
            val tagColorMap = ref<UTSJSONObject>(_uO("采购" to "success", "欠单" to "warning", "还款" to "info", "无附件" to "muted"))
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
            fun gen_applyTransactionResponse_fn(response: TransactionListResponse) {
                transactions.value = response.results
                currentPage.value = response.current_page
                totalPages.value = response.total_pages
                totalCount.value = response.total_count
                pageSize.value = response.page_size
                summary.value = response.summary
            }
            val applyTransactionResponse = ::gen_applyTransactionResponse_fn
            fun gen_parseErrorMessage_fn(error: Any): String {
                var message = "采购记录加载失败"
                if (error != null) {
                    val directMessage = (error as UTSError).message
                    if (directMessage != null && directMessage != "") {
                        message = directMessage
                    }
                    val errorText = JSON.stringify(error)
                    if (errorText != null && errorText != "") {
                        val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/transactions/index.uvue:256")
                        if (parsedError != null) {
                            val rawMessage = parsedError["message"]
                            if (rawMessage != null) {
                                val parsedMessage = rawMessage as String
                                if (parsedMessage != "") {
                                    message = parsedMessage
                                }
                            }
                        }
                        if (message == "采购记录加载失败") {
                            message = errorText
                        }
                    }
                }
                if (message.startsWith("Error: ")) {
                    return message.substring(7)
                }
                return message
            }
            val parseErrorMessage = ::gen_parseErrorMessage_fn
            fun gen_readEventValue_fn(event: Any): String {
                if (event == null) {
                    return ""
                }
                val inputEvent = event as UniInputEvent
                val detail = inputEvent.detail
                if (detail == null) {
                    return ""
                }
                return detail.value
            }
            val readEventValue = ::gen_readEventValue_fn
            fun gen_updateFilterPanelLayout_fn() {
                val info = uni_getWindowInfo()
                var nextPanelHeight = info.windowHeight - 168
                if (nextPanelHeight > 440) {
                    nextPanelHeight = 440
                }
                if (nextPanelHeight < 320) {
                    nextPanelHeight = 320
                }
                var nextContentHeight = nextPanelHeight - 64
                if (nextContentHeight < 240) {
                    nextContentHeight = 240
                }
                filterPanelHeight.value = nextPanelHeight
                filterContentHeight.value = nextContentHeight
            }
            val updateFilterPanelLayout = ::gen_updateFilterPanelLayout_fn
            fun gen_closeFilterDrawer_fn() {
                filterVisible.value = false
            }
            val closeFilterDrawer = ::gen_closeFilterDrawer_fn
            fun gen_handleFilterVisibleChange_fn(value: Boolean) {
                filterVisible.value = value
            }
            val handleFilterVisibleChange = ::gen_handleFilterVisibleChange_fn
            fun gen_setSelectedFilterValue_fn(param: String, value: String) {
                val nextFilters: UTSArray<TransactionSelectedFilter> = _uA()
                var updated = false
                run {
                    var index: Number = 0
                    while(index < selectedFilters.value.length){
                        val filter = selectedFilters.value[index]
                        if (filter.param == param) {
                            if (value != "") {
                                nextFilters.push(TransactionSelectedFilter(param = param, value = value))
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
                    nextFilters.push(TransactionSelectedFilter(param = param, value = value))
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
            fun gen_optionValue_fn(option: UTSJSONObject): String {
                return stringValue(option["value"])
            }
            val optionValue = ::gen_optionValue_fn
            fun gen_optionLabel_fn(option: UTSJSONObject): String {
                return stringValue(option["label"])
            }
            val optionLabel = ::gen_optionLabel_fn
            fun gen_optionKey_fn(option: UTSJSONObject): String {
                val value = optionValue(option)
                if (value != "") {
                    return value
                }
                return optionLabel(option)
            }
            val optionKey = ::gen_optionKey_fn
            fun gen_selectOrdering_fn(value: String) {
                selectedOrdering.value = value
            }
            val selectOrdering = ::gen_selectOrdering_fn
            fun gen_handleDateFromInput_fn(event: Any) {
                filterDateFrom.value = readEventValue(event).trim()
            }
            val handleDateFromInput = ::gen_handleDateFromInput_fn
            fun gen_handleDateToInput_fn(event: Any) {
                filterDateTo.value = readEventValue(event).trim()
            }
            val handleDateToInput = ::gen_handleDateToInput_fn
            fun gen_handleAmountMinInput_fn(event: Any) {
                filterAmountMin.value = readEventValue(event).trim()
            }
            val handleAmountMinInput = ::gen_handleAmountMinInput_fn
            fun gen_handleAmountMaxInput_fn(event: Any) {
                filterAmountMax.value = readEventValue(event).trim()
            }
            val handleAmountMaxInput = ::gen_handleAmountMaxInput_fn
            fun gen_transactionTypeFilterValue_fn(): String? {
                val value = selectedFilterValue("transaction_type")
                return if (value == "") {
                    null
                } else {
                    value
                }
            }
            val transactionTypeFilterValue = ::gen_transactionTypeFilterValue_fn
            fun gen_markTransactionListRefreshNeeded_fn() {
                uni_setStorageSync(transactionListRefreshStorageKey, "1")
            }
            val markTransactionListRefreshNeeded = ::gen_markTransactionListRefreshNeeded_fn
            fun gen_consumeTransactionListRefreshNeeded_fn(): Boolean {
                val storedValue = uni_getStorageSync(transactionListRefreshStorageKey)
                if (storedValue == null) {
                    return false
                }
                val storedText = "" + storedValue
                if (storedText == "") {
                    return false
                }
                uni_removeStorageSync(transactionListRefreshStorageKey)
                return true
            }
            val consumeTransactionListRefreshNeeded = ::gen_consumeTransactionListRefreshNeeded_fn
            fun gen_loadTransactions_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (isLoading.value) {
                            return@w1
                        }
                        if (supplierId.value == "") {
                            transactions.value = _uA()
                            totalCount.value = 0
                            totalPages.value = 1
                            errorMessage.value = "缺少供应商ID"
                            return@w1
                        }
                        isLoading.value = true
                        errorMessage.value = ""
                        try {
                            val response = await(getTransactionList(TransactionListQuery(search = if (keyword.value == "") {
                                null
                            } else {
                                keyword.value
                            }
                            , page = currentPage.value, page_size = pageSize.value, transaction_type = transactionTypeFilterValue(), supplier = null, supplier_id = supplierId.value, date_from = if (filterDateFrom.value == "") {
                                null
                            } else {
                                filterDateFrom.value
                            }
                            , start_date = null, date_to = if (filterDateTo.value == "") {
                                null
                            } else {
                                filterDateTo.value
                            }
                            , end_date = null, amount_min = if (filterAmountMin.value == "") {
                                null
                            } else {
                                filterAmountMin.value
                            }
                            , amount_max = if (filterAmountMax.value == "") {
                                null
                            } else {
                                filterAmountMax.value
                            }
                            , ordering = if (selectedOrdering.value == "") {
                                null
                            } else {
                                selectedOrdering.value
                            }
                            , sort_by = null)))
                            applyTransactionResponse(response)
                        }
                         catch (error: Throwable) {
                            transactions.value = _uA()
                            currentPage.value = 1
                            totalPages.value = 1
                            totalCount.value = 0
                            summary.value = null
                            errorMessage.value = parseErrorMessage(error)
                        }
                         finally {
                            isLoading.value = false
                        }
                })
            }
            val loadTransactions = ::gen_loadTransactions_fn
            fun gen_getStatisticsText_fn(key: String, fallback: String): String {
                if (statistics.value == null) {
                    return fallback
                }
                val rawValue = statistics.value!!.data[key]
                if (rawValue == null) {
                    return fallback
                }
                val text = "" + rawValue
                if (text == "") {
                    return fallback
                }
                return text
            }
            val getStatisticsText = ::gen_getStatisticsText_fn
            fun gen_loadTransactionStatistics_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (supplierId.value == "") {
                            statistics.value = null
                            return@w1
                        }
                        try {
                            statistics.value = await(getTransactionStatistics(TransactionListQuery(search = if (keyword.value == "") {
                                null
                            } else {
                                keyword.value
                            }
                            , page = currentPage.value, page_size = pageSize.value, transaction_type = transactionTypeFilterValue(), supplier = supplierId.value, supplier_id = supplierId.value, date_from = if (filterDateFrom.value == "") {
                                null
                            } else {
                                filterDateFrom.value
                            }
                            , start_date = null, date_to = if (filterDateTo.value == "") {
                                null
                            } else {
                                filterDateTo.value
                            }
                            , end_date = null, amount_min = if (filterAmountMin.value == "") {
                                null
                            } else {
                                filterAmountMin.value
                            }
                            , amount_max = if (filterAmountMax.value == "") {
                                null
                            } else {
                                filterAmountMax.value
                            }
                            , ordering = if (selectedOrdering.value == "") {
                                null
                            } else {
                                selectedOrdering.value
                            }
                            , sort_by = null)))
                        }
                         catch (error: Throwable) {
                            statistics.value = null
                        }
                })
            }
            val loadTransactionStatistics = ::gen_loadTransactionStatistics_fn
            fun gen_refreshTransactionData_fn() {
                loadTransactions()
                loadTransactionStatistics()
            }
            val refreshTransactionData = ::gen_refreshTransactionData_fn
            fun gen_getDisplayText_fn(value: String?): String {
                if (value == null || value == "") {
                    return "-"
                }
                return value
            }
            val getDisplayText = ::gen_getDisplayText_fn
            fun gen_transactionThumbnailUrl_fn(mediaFile: TransactionMediaFile): String {
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
            val transactionThumbnailUrl = ::gen_transactionThumbnailUrl_fn
            fun gen_transactionFullUrl_fn(mediaFile: TransactionMediaFile): String {
                if (mediaFile.signed_url != "") {
                    return mediaFile.signed_url
                }
                if (mediaFile.file_url != "") {
                    return mediaFile.file_url
                }
                return transactionThumbnailUrl(mediaFile)
            }
            val transactionFullUrl = ::gen_transactionFullUrl_fn
            fun gen_buildImages_fn(item: TransactionItem): UTSArray<String> {
                val result: UTSArray<String> = _uA()
                run {
                    var index: Number = 0
                    while(index < item.media_files.length){
                        val mediaFile = item.media_files[index]
                        val imageUrl = transactionThumbnailUrl(mediaFile)
                        if (imageUrl != "") {
                            result.push(imageUrl)
                        }
                        index += 1
                    }
                }
                return result
            }
            val buildImages = ::gen_buildImages_fn
            fun gen_buildPreviewImages_fn(item: TransactionItem): UTSArray<String> {
                val result: UTSArray<String> = _uA()
                run {
                    var index: Number = 0
                    while(index < item.media_files.length){
                        val imageUrl = transactionFullUrl(item.media_files[index])
                        if (imageUrl != "") {
                            result.push(imageUrl)
                        }
                        index += 1
                    }
                }
                return result
            }
            val buildPreviewImages = ::gen_buildPreviewImages_fn
            fun gen_buildMediaIds_fn(item: TransactionItem): UTSArray<String> {
                val result: UTSArray<String> = _uA()
                run {
                    var index: Number = 0
                    while(index < item.media_files.length){
                        val mediaId = item.media_files[index].id
                        if (mediaId != "") {
                            result.push(mediaId)
                        }
                        index += 1
                    }
                }
                return result
            }
            val buildMediaIds = ::gen_buildMediaIds_fn
            fun gen_formatDateText_fn(value: String): String {
                if (value == "") {
                    return "-"
                }
                if (value.length >= 16) {
                    return value.substring(0, 16)
                }
                return value
            }
            val formatDateText = ::gen_formatDateText_fn
            fun gen_transactionToListItem_fn(item: TransactionItem): UTSJSONObject {
                val images = buildImages(item)
                val previewImages = buildPreviewImages(item)
                val title = if (item.transaction_number != "") {
                    item.transaction_number
                } else {
                    ("采购记录 #" + item.id.toString(10))
                }
                val typeText = getDisplayText(item.transaction_type_display)
                val filesText = item.files_count.toString(10) + " 个"
                val tags = _uA<String>(typeText, if (item.files_count > 0) {
                    "附件 " + item.files_count.toString(10)
                } else {
                    "无附件"
                }
                )
                return _uO("id" to item.id.toString(10), "title" to title, "transactionDateText" to ("日期：" + formatDateText(item.transaction_date)), "amountText" to ("¥ " + item.amount), "transactionTypeText" to typeText, "filesText" to filesText, "noteText" to getDisplayText(item.note), "updatedText" to formatDateText(item.updated_at), "cover" to if (images.length > 0) {
                    images[0]
                } else {
                    ""
                }
                , "images" to images, "previewCover" to if (previewImages.length > 0) {
                    previewImages[0]
                } else {
                    ""
                }
                , "previewImages" to previewImages, "mediaIds" to buildMediaIds(item), "tags" to tags, "rawId" to item.id.toString(10))
            }
            val transactionToListItem = ::gen_transactionToListItem_fn
            fun gen_handleSearchInput_fn(value: String) {
                keyword.value = value
            }
            val handleSearchInput = ::gen_handleSearchInput_fn
            fun gen_handleSearchConfirm_fn(value: String) {
                keyword.value = value
                currentPage.value = 1
                refreshTransactionData()
            }
            val handleSearchConfirm = ::gen_handleSearchConfirm_fn
            fun gen_handleSearchClear_fn() {
                keyword.value = ""
                currentPage.value = 1
                refreshTransactionData()
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
                            filterOptions.value = await(getTransactionFilterOptions())
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
            fun gen_handleFilterReset_fn() {
                selectedFilters.value = _uA<TransactionSelectedFilter>()
                filterDateFrom.value = ""
                filterDateTo.value = ""
                filterAmountMin.value = ""
                filterAmountMax.value = ""
                selectedOrdering.value = "-transaction_date"
                keyword.value = ""
                currentPage.value = 1
                closeFilterDrawer()
                refreshTransactionData()
            }
            val handleFilterReset = ::gen_handleFilterReset_fn
            fun gen_applySelectedFilters_fn() {
                currentPage.value = 1
                closeFilterDrawer()
                refreshTransactionData()
            }
            val applySelectedFilters = ::gen_applySelectedFilters_fn
            fun gen_handlePageChange_fn(payload: UTSJSONObject) {
                val pageValue = payload["page"]
                if (pageValue == null) {
                    return
                }
                val nextPageText = "" + pageValue
                val nextPage = parseInt(nextPageText)
                if (isNaN(nextPage) || nextPage <= 0 || nextPage == currentPage.value) {
                    return
                }
                currentPage.value = nextPage
                refreshTransactionData()
            }
            val handlePageChange = ::gen_handlePageChange_fn
            fun gen_handleItemClick_fn(payload: UTSJSONObject) {
                val titleText = stringValue(payload["title"], "采购记录")
                val itemValue = payload["item"]
                if (itemValue == null) {
                    uni_showToast(ShowToastOptions(title = titleText, icon = "none"))
                    return
                }
                val item = itemValue as UTSJSONObject
                val detailText = _uA(
                    "类型：" + stringValue(item["transactionTypeText"], "-"),
                    "金额：" + stringValue(item["amountText"], "-"),
                    stringValue(item["transactionDateText"], "日期：-"),
                    "附件：" + stringValue(item["filesText"], "0 个"),
                    "备注：" + stringValue(item["noteText"], "-")
                ).join("\n")
                uni_showModal(ShowModalOptions(title = titleText, content = detailText, showCancel = false, confirmText = "知道了"))
            }
            val handleItemClick = ::gen_handleItemClick_fn
            fun gen_handleSubtitleClick_fn(payload: UTSJSONObject) {
                val itemValue = payload["item"]
                if (itemValue == null) {
                    return
                }
                val item = itemValue as UTSJSONObject
                val transactionDateValue = item["transactionDateText"]
                val transactionDateText = if (transactionDateValue == null) {
                    ""
                } else {
                    (transactionDateValue as String)
                }
                uni_setClipboardData(SetClipboardDataOptions(data = transactionDateText, success = fun(_){
                    uni_showToast(ShowToastOptions(title = "日期已复制", icon = "success"))
                }
                ))
            }
            val handleSubtitleClick = ::gen_handleSubtitleClick_fn
            fun gen_handleMetaClick_fn(payload: UTSJSONObject) {
                val itemValue = payload["item"]
                if (itemValue == null) {
                    return
                }
                val item = itemValue as UTSJSONObject
                val amountValue = item["amountText"]
                val amountText = if (amountValue == null) {
                    ""
                } else {
                    (amountValue as String)
                }
                uni_setClipboardData(SetClipboardDataOptions(data = amountText, success = fun(_){
                    uni_showToast(ShowToastOptions(title = "金额已复制", icon = "success"))
                }
                ))
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
                val rawValue = item[key]
                if (rawValue == null) {
                    return
                }
                uni_setClipboardData(SetClipboardDataOptions(data = rawValue as String, success = fun(_){
                    uni_showToast(ShowToastOptions(title = "内容已复制", icon = "success"))
                }
                ))
            }
            val handleFieldClick = ::gen_handleFieldClick_fn
            fun gen_handleCreateTransaction_fn() {
                if (supplierId.value == "") {
                    uni_showToast(ShowToastOptions(title = "供应商ID缺失", icon = "none"))
                    return
                }
                uni_navigateTo(NavigateToOptions(url = "/pages/transactions/from?supplier_id=" + supplierId.value))
            }
            val handleCreateTransaction = ::gen_handleCreateTransaction_fn
            fun gen_confirmDeleteTransaction_fn(transactionId: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        try {
                            await(deleteTransaction(transactionId))
                            uni_showToast(ShowToastOptions(title = takeLatestResponseMessage("删除成功"), icon = "success"))
                            markTransactionListRefreshNeeded()
                            refreshTransactionData()
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error), icon = "none"))
                        }
                })
            }
            val confirmDeleteTransaction = ::gen_confirmDeleteTransaction_fn
            fun gen_handleMenu_fn(payload: UTSJSONObject) {
                val action = payload["action"]
                val item = payload["item"]
                if (action == null || item == null) {
                    return
                }
                val actionObject = action as UTSJSONObject
                val itemObject = item as UTSJSONObject
                val actionKey = actionObject["key"]
                if (actionKey == null) {
                    return
                }
                val key = actionKey as String
                val transactionIdValue = itemObject["rawId"]
                val transactionId = if (transactionIdValue == null) {
                    ""
                } else {
                    (transactionIdValue as String)
                }
                if (transactionId == "") {
                    uni_showToast(ShowToastOptions(title = "采购记录ID缺失", icon = "none"))
                    return
                }
                if (key == "edit") {
                    uni_navigateTo(NavigateToOptions(url = "/pages/transactions/from?id=" + transactionId + "&supplier_id=" + supplierId.value))
                    return
                }
                if (key == "Detail") {
                    uni_showToast(ShowToastOptions(title = "当前已在详情页", icon = "none"))
                    return
                }
                if (key == "add") {
                    uni_navigateTo(NavigateToOptions(url = "/pages/transactions/from?supplier_id=" + supplierId.value))
                    return
                }
                if (key == "delete") {
                    uni_showModal(ShowModalOptions(title = "删除采购记录", content = "确定删除这条采购记录吗？", success = fun(res){
                        if (!res.confirm) {
                            return
                        }
                        confirmDeleteTransaction(transactionId)
                    }
                    ))
                }
            }
            val handleMenu = ::gen_handleMenu_fn
            val listItems = computed(fun(): UTSArray<UTSJSONObject> {
                val result: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < transactions.value.length){
                        result.push(transactionToListItem(transactions.value[index]))
                        index += 1
                    }
                }
                return result
            }
            )
            val pageTitle = computed(fun(): String {
                if (supplierName.value != "") {
                    return supplierName.value + " 采购记录"
                }
                return "采购记录"
            }
            )
            val hasActiveFilter = computed(fun(): Boolean {
                return keyword.value != "" || selectedFilters.value.length > 0 || filterDateFrom.value != "" || filterDateTo.value != "" || filterAmountMin.value != "" || filterAmountMax.value != "" || selectedOrdering.value != "-transaction_date"
            }
            )
            val emptyText = computed(fun(): String {
                if (supplierId.value == "") {
                    return "缺少供应商ID"
                }
                if (hasActiveFilter.value) {
                    return "没有匹配的采购记录"
                }
                return "暂无采购记录"
            }
            )
            val transactionCountText = computed(fun(): String {
                return getStatisticsText("purchaseCount", totalCount.value.toString(10))
            }
            )
            val purchaseAmountText = computed(fun(): String {
                val summaryValue = if (summary.value == null || summary.value!!.purchase_amount == "") {
                    "0.00"
                } else {
                    summary.value!!.purchase_amount
                }
                return getStatisticsText("purchaseAmount", summaryValue)
            }
            )
            val arrearsAmountText = computed(fun(): String {
                val summaryValue = if (summary.value == null || summary.value!!.arrears_amount == "") {
                    "0.00"
                } else {
                    summary.value!!.arrears_amount
                }
                return getStatisticsText("actualDebt", summaryValue)
            }
            )
            val summaryItems = computed(fun(): UTSArray<UTSJSONObject> {
                return _uA(
                    _uO("key" to "total-count", "label" to "记录数", "value" to totalCount.value.toString(10)),
                    _uO("key" to "purchase-count", "label" to "采购次数", "value" to transactionCountText.value),
                    _uO("key" to "purchase-amount", "label" to "采购金额", "value" to ("¥ " + purchaseAmountText.value)),
                    _uO("key" to "arrears-amount", "label" to "欠款金额", "value" to ("¥ " + arrearsAmountText.value)),
                    _uO("key" to "payment-amount", "label" to "还款金额", "value" to ("¥ " + getStatisticsText("paymentAmount", "0.00")))
                )
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
            val filterDefinitions = computed(fun(): UTSArray<TransactionFilterDefinition> {
                if (filterOptions.value == null) {
                    return defaultFilterDefinitions.value
                }
                if (filterOptions.value!!.filters.length == 0) {
                    return defaultFilterDefinitions.value
                }
                return filterOptions.value!!.filters
            }
            )
            onLoad(fun(event: OnLoadOptions){
                updateFilterPanelLayout()
                filterDateFrom.value = ""
                filterDateTo.value = ""
                var supplierIdValue = event["supplier_id"]
                if (supplierIdValue == null || supplierIdValue == "") {
                    supplierIdValue = event["supplierId"]
                }
                if (supplierIdValue == null || supplierIdValue == "") {
                    supplierIdValue = event["id"]
                }
                supplierId.value = if (supplierIdValue == null) {
                    ""
                } else {
                    (supplierIdValue as String)
                }
                val supplierNameValue = event["supplier_name"]
                if (supplierNameValue == null) {
                    supplierName.value = ""
                } else {
                    val decodedSupplierName = UTSAndroid.consoleDebugError(decodeURIComponent(supplierNameValue as String), " at pages/transactions/index.uvue:921")
                    supplierName.value = if (decodedSupplierName == null) {
                        ""
                    } else {
                        decodedSupplierName
                    }
                }
                refreshTransactionData()
            }
            )
            onShow(fun(){
                updateFilterPanelLayout()
                if (!consumeTransactionListRefreshNeeded()) {
                    return
                }
                refreshTransactionData()
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList", GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalListClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to pageTitle.value, "searchPlaceholder" to "输入单号、备注搜索采购记录", "searchValue" to unref(keyword), "filterVisible" to unref(filterVisible), "showBack" to true, "showSearch" to true, "showFilter" to true, "showHome" to true, "filterActive" to hasActiveFilter.value, "homePath" to "/pages/suppliers/index", "onSearchInput" to handleSearchInput, "onSearchConfirm" to handleSearchConfirm, "onSearchClear" to handleSearchClear, "onUpdate:filterVisible" to handleFilterVisibleChange, "onFilterOpen" to handleFilterOpen), _uM("filter-panel" to withSlotCtx(fun(): UTSArray<Any> {
                        return _uA(
                            _cE("view", _uM("class" to "transaction-filter-panel", "style" to _nS(filterPanelStyle.value)), _uA(
                                _cE("scroll-view", _uM("scroll-y" to "true", "class" to "transaction-filter-content-scroll", "style" to _nS(filterContentScrollStyle.value)), _uA(
                                    _cE("view", _uM("class" to "transaction-filter-scroll-inner"), _uA(
                                        if (isTrue(unref(filterOptionsLoading))) {
                                            _cE("view", _uM("key" to 0, "class" to "transaction-filter-state"), _uA(
                                                _cE("text", _uM("class" to "transaction-filter-state-text"), "筛选选项加载中...")
                                            ))
                                        } else {
                                            if (unref(filterOptionsError) != "") {
                                                _cE("view", _uM("key" to 1, "class" to "transaction-filter-state"), _uA(
                                                    _cE("text", _uM("class" to "transaction-filter-state-text"), _tD(unref(filterOptionsError)), 1)
                                                ))
                                            } else {
                                                _cE("view", _uM("key" to 2, "class" to "transaction-filter-groups"), _uA(
                                                    _cE(Fragment, null, RenderHelpers.renderList(filterDefinitions.value, fun(filter, __key, __index, _cached): Any {
                                                        return _cE("view", _uM("key" to filter.key, "class" to "transaction-filter-group"), _uA(
                                                            _cE("text", _uM("class" to "transaction-filter-group-title"), _tD(filter.label), 1),
                                                            _cE("view", _uM("class" to "transaction-filter-options"), _uA(
                                                                _cE(Fragment, null, RenderHelpers.renderList(filter.options, fun(option, __key, __index, _cached): Any {
                                                                    return _cE("view", _uM("key" to (filter.key + "-" + option.value), "class" to _nC(if (isFilterOptionSelected(filter.param, option.value)) {
                                                                        "transaction-filter-option transaction-filter-option-active"
                                                                    } else {
                                                                        "transaction-filter-option"
                                                                    }
                                                                    ), "onClick" to fun(){
                                                                        toggleFilterOption(filter.param, option.value)
                                                                    }
                                                                    ), _uA(
                                                                        _cE("text", _uM("class" to _nC(if (isFilterOptionSelected(filter.param, option.value)) {
                                                                            "transaction-filter-option-text transaction-filter-option-text-active"
                                                                        } else {
                                                                            "transaction-filter-option-text"
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
                                                    ), 128),
                                                    _cE("view", _uM("class" to "transaction-filter-group"), _uA(
                                                        _cE("text", _uM("class" to "transaction-filter-group-title"), "日期范围"),
                                                        _cE("view", _uM("class" to "transaction-filter-input-row"), _uA(
                                                            _cE("input", _uM("class" to "transaction-filter-input", "value" to unref(filterDateFrom), "placeholder" to "开始日期 YYYY-MM-DD", "onInput" to handleDateFromInput), null, 40, _uA(
                                                                "value"
                                                            )),
                                                            _cE("input", _uM("class" to "transaction-filter-input", "value" to unref(filterDateTo), "placeholder" to "结束日期 YYYY-MM-DD", "onInput" to handleDateToInput), null, 40, _uA(
                                                                "value"
                                                            ))
                                                        ))
                                                    )),
                                                    _cE("view", _uM("class" to "transaction-filter-group"), _uA(
                                                        _cE("text", _uM("class" to "transaction-filter-group-title"), "金额范围"),
                                                        _cE("view", _uM("class" to "transaction-filter-input-row"), _uA(
                                                            _cE("input", _uM("class" to "transaction-filter-input", "type" to "digit", "value" to unref(filterAmountMin), "placeholder" to "最低金额", "onInput" to handleAmountMinInput), null, 40, _uA(
                                                                "value"
                                                            )),
                                                            _cE("input", _uM("class" to "transaction-filter-input", "type" to "digit", "value" to unref(filterAmountMax), "placeholder" to "最高金额", "onInput" to handleAmountMaxInput), null, 40, _uA(
                                                                "value"
                                                            ))
                                                        ))
                                                    )),
                                                    _cE("view", _uM("class" to "transaction-filter-group"), _uA(
                                                        _cE("text", _uM("class" to "transaction-filter-group-title"), "排序方式"),
                                                        _cE("view", _uM("class" to "transaction-filter-options"), _uA(
                                                            _cE(Fragment, null, RenderHelpers.renderList(unref(sortOptions), fun(option, __key, __index, _cached): Any {
                                                                return _cE("view", _uM("key" to optionKey(option), "class" to _nC(if (unref(selectedOrdering) == optionValue(option)) {
                                                                    "transaction-filter-option transaction-filter-option-active"
                                                                } else {
                                                                    "transaction-filter-option"
                                                                }
                                                                ), "onClick" to fun(){
                                                                    selectOrdering(optionValue(option))
                                                                }
                                                                ), _uA(
                                                                    _cE("text", _uM("class" to _nC(if (unref(selectedOrdering) == optionValue(option)) {
                                                                        "transaction-filter-option-text transaction-filter-option-text-active"
                                                                    } else {
                                                                        "transaction-filter-option-text"
                                                                    }
                                                                    )), _tD(optionLabel(option)), 3)
                                                                ), 10, _uA(
                                                                    "onClick"
                                                                ))
                                                            }
                                                            ), 128)
                                                        ))
                                                    ))
                                                ))
                                            }
                                        }
                                    ))
                                ), 4),
                                _cE("view", _uM("class" to "transaction-filter-actions"), _uA(
                                    _cE("view", _uM("class" to "transaction-filter-btn transaction-filter-btn-light", "onClick" to handleFilterReset), _uA(
                                        _cE("text", _uM("class" to "transaction-filter-btn-light-text"), "重置")
                                    )),
                                    _cE("view", _uM("class" to "transaction-filter-btn transaction-filter-btn-primary", "onClick" to applySelectedFilters), _uA(
                                        _cE("text", _uM("class" to "transaction-filter-btn-primary-text"), "应用")
                                    ))
                                ))
                            ), 4)
                        )
                    }
                    ), "_" to 1), 8, _uA(
                        "title",
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
                                    _cE("button", _uM("class" to "retry-btn", "onClick" to refreshTransactionData), _uA(
                                        _cE("text", _uM("class" to "retry-btn-text"), "重新加载")
                                    ))
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            _cV(_component_lili_UniversalList, _uM("items" to listItems.value, "keyField" to "id", "titleField" to "title", "subtitleField" to "transactionDateText", "metaField" to "amountText", "imageField" to "cover", "imageListField" to "images", "tagField" to "tags", "tagColorMap" to unref(tagColorMap), "fields" to unref(fieldConfig), "loading" to unref(isLoading), "loadingText" to "正在加载采购记录", "keepContentOnLoading" to true, "inlineLoadingText" to "采购记录刷新中...", "emptyText" to emptyText.value, "emptyIcon" to "◎", "showMenu" to true, "menuActions" to unref(menuActions), "showChevron" to false, "showPagination" to true, "currentPage" to unref(currentPage), "totalPages" to unref(totalPages), "totalCount" to unref(totalCount), "summaryTitle" to "采购统计", "summaryItems" to summaryItems.value, "summaryCollapsedByDefault" to false, "showFloatingAdd" to true, "floatingAddText" to "新增", "onItemClick" to handleItemClick, "onSubtitleClick" to handleSubtitleClick, "onMetaClick" to handleMetaClick, "onFieldClick" to handleFieldClick, "onMenu" to handleMenu, "onPageChange" to handlePageChange, "onFloatingAdd" to handleCreateTransaction), null, 8, _uA(
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "position" to "relative", "backgroundColor" to "#EEF2F7")), "page-scroll" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "page-content" to _pS(_uM("paddingLeft" to 6, "paddingRight" to 6, "paddingTop" to 6, "paddingBottom" to 88)), "error-card" to _pS(_uM("marginBottom" to 12, "paddingTop" to 18, "paddingRight" to 16, "paddingBottom" to 18, "paddingLeft" to 16, "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "backgroundColor" to "#FFFFFF")), "error-title" to _pS(_uM("fontSize" to 16, "fontWeight" to "600", "color" to "#111827")), "error-desc" to _pS(_uM("marginTop" to 8, "fontSize" to 14, "lineHeight" to "1.5em", "color" to "#6B7280")), "retry-btn" to _pS(_uM("marginTop" to 14, "height" to 40, "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "backgroundColor" to "#111827", "borderTopWidth" to 0, "borderRightWidth" to 0, "borderBottomWidth" to 0, "borderLeftWidth" to 0)), "retry-btn-text" to _pS(_uM("fontSize" to 14, "fontWeight" to "600", "color" to "#FFFFFF")), "transaction-filter-panel" to _pS(_uM("position" to "relative", "paddingTop" to 2)), "transaction-filter-content-scroll" to _pS(_uM("paddingRight" to 2)), "transaction-filter-scroll-inner" to _pS(_uM("paddingBottom" to 58)), "transaction-filter-state" to _pS(_uM("height" to 112, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#F8FAFC", "alignItems" to "center", "justifyContent" to "center")), "transaction-filter-state-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "17px", "color" to "#64748B")), "transaction-filter-groups" to _pS(_uM("marginBottom" to 6)), "transaction-filter-group" to _pS(_uM("paddingLeft" to 10, "paddingRight" to 10, "paddingTop" to 10, "paddingBottom" to 10, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#FFFFFF", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E5EAF1", "borderRightColor" to "#E5EAF1", "borderBottomColor" to "#E5EAF1", "borderLeftColor" to "#E5EAF1", "marginBottom" to 6)), "transaction-filter-group-title" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#0F172A", "fontWeight" to "bold")), "transaction-filter-options" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 8)), "transaction-filter-option" to _pS(_uM("minWidth" to 48, "height" to 30, "paddingLeft" to 10, "paddingRight" to 10, "borderTopLeftRadius" to 15, "borderTopRightRadius" to 15, "borderBottomRightRadius" to 15, "borderBottomLeftRadius" to 15, "backgroundColor" to "#F8FAFC", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "alignItems" to "center", "justifyContent" to "center", "marginRight" to 6, "marginBottom" to 6)), "transaction-filter-option-active" to _pS(_uM("backgroundColor" to "#0F172A", "borderTopColor" to "#0F172A", "borderRightColor" to "#0F172A", "borderBottomColor" to "#0F172A", "borderLeftColor" to "#0F172A")), "transaction-filter-option-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "17px", "color" to "#334155")), "transaction-filter-option-text-active" to _pS(_uM("color" to "#FFFFFF")), "transaction-filter-input-row" to _pS(_uM("flexDirection" to "row", "marginTop" to 8)), "transaction-filter-input" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 36, "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "backgroundColor" to "#F8FAFC", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "paddingLeft" to 12, "paddingRight" to 12, "fontSize" to 12, "color" to "#0F172A", "marginRight" to 6)), "transaction-filter-actions" to _pS(_uM("position" to "absolute", "left" to 0, "right" to 0, "bottom" to 0, "flexDirection" to "row", "paddingTop" to 6, "paddingLeft" to 2, "paddingRight" to 2, "paddingBottom" to 4, "borderTopWidth" to 1, "borderTopStyle" to "solid", "borderTopColor" to "rgba(226,232,240,0.78)", "backgroundColor" to "#FFFFFF")), "transaction-filter-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 38, "borderTopLeftRadius" to 11, "borderTopRightRadius" to 11, "borderBottomRightRadius" to 11, "borderBottomLeftRadius" to 11, "alignItems" to "center", "justifyContent" to "center")), "transaction-filter-btn-light" to _pS(_uM("backgroundColor" to "#F3F6FA", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "marginRight" to 8)), "transaction-filter-btn-primary" to _pS(_uM("backgroundColor" to "#0F172A")), "transaction-filter-btn-light-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#475569")), "transaction-filter-btn-primary-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#FFFFFF")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
