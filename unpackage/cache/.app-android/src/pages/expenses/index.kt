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
open class GenPagesExpensesIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesExpensesIndex) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesExpensesIndex
            val _cache = __ins.renderCache
            val expenseListRefreshStorageKey = "refresh:pages:expenses:index"
            val keyword = ref("")
            val filterVisible = ref(false)
            val expenses = ref(_uA<ExpenseItem>())
            val isLoading = ref(false)
            val errorMessage = ref("")
            val currentPage = ref(1)
            val totalPages = ref(1)
            val totalCount = ref(0)
            val pageSize = ref(20)
            val pageTotalAmount = ref("0.00")
            val filterOptionsLoading = ref(false)
            val filterOptionsError = ref("")
            val filterOptions = ref<ExpenseFilterOptionsResponse?>(null)
            val selectedFilters = ref(_uA<ExpenseSelectedFilter>())
            val filterPanelHeight = ref(400)
            val filterContentHeight = ref(336)
            val fieldConfig = ref(_uA<UTSJSONObject>(_uO("key" to "typeText", "label" to "类型:"), _uO("key" to "supplierText", "label" to "供应商:"), _uO("key" to "invoiceText", "label" to "发票:"), _uO("key" to "noteText", "label" to "备注:")))
            val menuActions = ref(_uA<UTSJSONObject>(_uO("key" to "edit", "text" to "编辑"), _uO("key" to "delete", "text" to "删除")))
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
                val text = stringValue(error)
                if (text == "" || text == "[object Object]") {
                    return fallback
                }
                if (text.startsWith("Error: ")) {
                    return text.substring(7)
                }
                return text
            }
            val parseErrorMessage = ::gen_parseErrorMessage_fn
            fun gen_updateFilterPanelLayout_fn() {
                val info = uni_getWindowInfo()
                var nextPanelHeight = info.windowHeight - 168
                if (nextPanelHeight > 400) {
                    nextPanelHeight = 400
                }
                if (nextPanelHeight < 300) {
                    nextPanelHeight = 300
                }
                var nextContentHeight = nextPanelHeight - 64
                if (nextContentHeight < 220) {
                    nextContentHeight = 220
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
                val nextFilters: UTSArray<ExpenseSelectedFilter> = _uA()
                var updated = false
                run {
                    var index: Number = 0
                    while(index < selectedFilters.value.length){
                        val filter = selectedFilters.value[index]
                        if (filter.param == param) {
                            if (value != "") {
                                nextFilters.push(ExpenseSelectedFilter(param = param, value = value))
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
                    nextFilters.push(ExpenseSelectedFilter(param = param, value = value))
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
            fun gen_applyExpenseResponse_fn(response: ExpenseListResponse) {
                expenses.value = response.results
                currentPage.value = response.current_page
                totalPages.value = response.total_pages
                totalCount.value = response.total_count
                pageSize.value = response.page_size
                var total: Number = 0.0
                run {
                    var index: Number = 0
                    while(index < response.results.length){
                        val amount = parseFloat(response.results[index].amount)
                        if (!isNaN(amount)) {
                            total = total + amount
                        }
                        index += 1
                    }
                }
                pageTotalAmount.value = total.toFixed(2)
            }
            val applyExpenseResponse = ::gen_applyExpenseResponse_fn
            fun gen_loadExpenses_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (isLoading.value) {
                            return@w1
                        }
                        isLoading.value = true
                        errorMessage.value = ""
                        try {
                            val response = await(getExpenseList(ExpenseListQuery(search = if (keyword.value == "") {
                                null
                            } else {
                                keyword.value
                            }
                            , page = currentPage.value, page_size = pageSize.value, expenditure_type = if (selectedFilterValue("expenditure_type") == "") {
                                null
                            } else {
                                selectedFilterValue("expenditure_type")
                            }
                            , expenditure_type_id = null, supplier = if (selectedFilterValue("supplier") == "") {
                                null
                            } else {
                                selectedFilterValue("supplier")
                            }
                            , supplier_id = null, date_from = null, date_to = null, amount_min = null, amount_max = null)))
                            applyExpenseResponse(response)
                        }
                         catch (error: Throwable) {
                            expenses.value = _uA()
                            currentPage.value = 1
                            totalPages.value = 1
                            totalCount.value = 0
                            pageTotalAmount.value = "0.00"
                            errorMessage.value = parseErrorMessage(error, "支出记录加载失败")
                        }
                         finally {
                            isLoading.value = false
                        }
                })
            }
            val loadExpenses = ::gen_loadExpenses_fn
            fun gen_markExpenseListRefreshNeeded_fn() {
                uni_setStorageSync(expenseListRefreshStorageKey, "1")
            }
            val markExpenseListRefreshNeeded = ::gen_markExpenseListRefreshNeeded_fn
            fun gen_consumeExpenseListRefreshNeeded_fn(): Boolean {
                val storedValue = uni_getStorageSync(expenseListRefreshStorageKey)
                if (storedValue == null || ("" + storedValue) == "") {
                    return false
                }
                uni_removeStorageSync(expenseListRefreshStorageKey)
                return true
            }
            val consumeExpenseListRefreshNeeded = ::gen_consumeExpenseListRefreshNeeded_fn
            fun getDisplayText(value: String?, fallback: String = "-"): String {
                if (value == null || value == "") {
                    return fallback
                }
                return value
            }
            fun gen_expenseThumbnailUrl_fn(mediaFile: ExpenseMediaFile): String {
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
            val expenseThumbnailUrl = ::gen_expenseThumbnailUrl_fn
            fun gen_expenseFullUrl_fn(mediaFile: ExpenseMediaFile): String {
                if (mediaFile.signed_url != "") {
                    return mediaFile.signed_url
                }
                if (mediaFile.file_url != "") {
                    return mediaFile.file_url
                }
                return expenseThumbnailUrl(mediaFile)
            }
            val expenseFullUrl = ::gen_expenseFullUrl_fn
            fun gen_buildImages_fn(item: ExpenseItem): UTSArray<String> {
                val result: UTSArray<String> = _uA()
                run {
                    var index: Number = 0
                    while(index < item.media_files.length){
                        val mediaFile = item.media_files[index]
                        val imageUrl = expenseThumbnailUrl(mediaFile)
                        if (imageUrl != "") {
                            result.push(imageUrl)
                        }
                        index += 1
                    }
                }
                return result
            }
            val buildImages = ::gen_buildImages_fn
            fun gen_buildPreviewImages_fn(item: ExpenseItem): UTSArray<String> {
                val result: UTSArray<String> = _uA()
                run {
                    var index: Number = 0
                    while(index < item.media_files.length){
                        val imageUrl = expenseFullUrl(item.media_files[index])
                        if (imageUrl != "") {
                            result.push(imageUrl)
                        }
                        index += 1
                    }
                }
                return result
            }
            val buildPreviewImages = ::gen_buildPreviewImages_fn
            fun gen_buildMediaIds_fn(item: ExpenseItem): UTSArray<String> {
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
            fun gen_expenseToListItem_fn(item: ExpenseItem): UTSJSONObject {
                val typeText = getDisplayText(item.expenditure_type_name, "未分类")
                val supplierText = getDisplayText(item.supplier_name, "-")
                val invoiceText = getDisplayText(item.invoice_number, "-")
                val noteText = getDisplayText(item.note, getDisplayText(item.description, "-"))
                val images = buildImages(item)
                val previewImages = buildPreviewImages(item)
                return _uO("id" to item.id.toString(10), "rawId" to item.id.toString(10), "title" to typeText, "subtitle" to ("日期：" + getDisplayText(item.expenditure_date, "-")), "amountText" to ("¥ " + getDisplayText(item.amount, "0.00")), "typeText" to typeText, "supplierText" to supplierText, "invoiceText" to invoiceText, "noteText" to noteText, "cover" to if (images.length > 0) {
                    images[0]
                } else {
                    ""
                }
                , "images" to images, "previewCover" to if (previewImages.length > 0) {
                    previewImages[0]
                } else {
                    ""
                }
                , "previewImages" to previewImages, "mediaIds" to buildMediaIds(item), "tags" to _uA<String>(if (item.files_count > 0) {
                    "附件 " + item.files_count.toString(10)
                } else {
                    "无附件"
                }
                ))
            }
            val expenseToListItem = ::gen_expenseToListItem_fn
            fun gen_handleSearchInput_fn(value: String) {
                keyword.value = value
            }
            val handleSearchInput = ::gen_handleSearchInput_fn
            fun gen_handleSearchConfirm_fn(value: String) {
                keyword.value = value
                currentPage.value = 1
                loadExpenses()
            }
            val handleSearchConfirm = ::gen_handleSearchConfirm_fn
            fun gen_handleSearchClear_fn() {
                keyword.value = ""
                currentPage.value = 1
                loadExpenses()
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
                            filterOptions.value = await(getExpenseFilterOptions())
                        }
                         catch (error: Throwable) {
                            filterOptionsError.value = parseErrorMessage(error, "筛选选项加载失败")
                        }
                         finally {
                            filterOptionsLoading.value = false
                        }
                })
            }
            val handleFilterOpen = ::gen_handleFilterOpen_fn
            fun gen_handleFilterReset_fn() {
                selectedFilters.value = _uA<ExpenseSelectedFilter>()
                keyword.value = ""
                currentPage.value = 1
                closeFilterDrawer()
                loadExpenses()
            }
            val handleFilterReset = ::gen_handleFilterReset_fn
            fun gen_applySelectedFilters_fn() {
                currentPage.value = 1
                closeFilterDrawer()
                loadExpenses()
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
                loadExpenses()
            }
            val handlePageChange = ::gen_handlePageChange_fn
            fun gen_handleCreateExpense_fn(payload: UTSJSONObject) {
                uni_navigateTo(NavigateToOptions(url = "/pages/expenses/from"))
            }
            val handleCreateExpense = ::gen_handleCreateExpense_fn
            fun gen_handleItemClick_fn(payload: UTSJSONObject) {
                val id = stringValue(payload["rawId"], stringValue(payload["id"]))
                if (id == "") {
                    return
                }
                uni_navigateTo(NavigateToOptions(url = "/pages/expenses/from?id=" + id))
            }
            val handleItemClick = ::gen_handleItemClick_fn
            fun gen_handleSubtitleClick_fn(payload: UTSJSONObject) {
                copyText(stringValue(payload["value"]), "日期已复制", "暂无日期")
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
            fun gen_runDelete_fn(id: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        try {
                            await(deleteExpense(id))
                            markExpenseListRefreshNeeded()
                            uni_showToast(ShowToastOptions(title = takeLatestResponseMessage("删除成功"), icon = "success"))
                            loadExpenses()
                        }
                         catch (error: Throwable) {
                            showErrorToast(parseErrorMessage(error, "删除失败"))
                        }
                })
            }
            val runDelete = ::gen_runDelete_fn
            fun gen_handleMenu_fn(payload: UTSJSONObject) {
                val action = payload["action"]
                val item = payload["item"]
                if (action == null || item == null) {
                    return
                }
                val actionKey = stringValue((action as UTSJSONObject)["key"])
                val id = stringValue((item as UTSJSONObject)["rawId"])
                if (id == "") {
                    return
                }
                if (actionKey == "edit") {
                    uni_navigateTo(NavigateToOptions(url = "/pages/expenses/from?id=" + id))
                    return
                }
                if (actionKey == "delete") {
                    uni_showModal(ShowModalOptions(title = "删除支出", content = "确定删除这条支出记录吗？", success = fun(res){
                        if (res.confirm) {
                            runDelete(id)
                        }
                    }
                    ))
                }
            }
            val handleMenu = ::gen_handleMenu_fn
            val listItems = computed(fun(): UTSArray<UTSJSONObject> {
                val result: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < expenses.value.length){
                        result.push(expenseToListItem(expenses.value[index]))
                        index += 1
                    }
                }
                return result
            }
            )
            val hasActiveFilter = computed(fun(): Boolean {
                return keyword.value != "" || selectedFilters.value.length > 0
            }
            )
            val emptyText = computed(fun(): String {
                if (isLoading.value) {
                    return "正在加载"
                }
                if (hasActiveFilter.value) {
                    return "没有匹配的支出记录"
                }
                return "暂无支出记录"
            }
            )
            val summaryItems = computed(fun(): UTSArray<UTSJSONObject> {
                return _uA(
                    _uO("key" to "total", "label" to "记录数", "value" to totalCount.value.toString(10)),
                    _uO("key" to "pageAmount", "label" to "本页合计", "value" to ("¥ " + pageTotalAmount.value)),
                    _uO("key" to "page", "label" to "页码", "value" to (currentPage.value.toString(10) + "/" + totalPages.value.toString(10)))
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
            val filterDefinitions = computed(fun(): UTSArray<ExpenseFilterDefinition> {
                if (filterOptions.value == null) {
                    return _uA<ExpenseFilterDefinition>()
                }
                return filterOptions.value!!.filters
            }
            )
            onLoad(fun(_options){
                updateFilterPanelLayout()
                loadExpenses()
            }
            )
            onShow(fun(){
                updateFilterPanelLayout()
                if (consumeExpenseListRefreshNeeded()) {
                    loadExpenses()
                }
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList", GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalListClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to "支出管理", "searchPlaceholder" to "支出类型、供应商、发票号、备注", "searchValue" to unref(keyword), "filterVisible" to unref(filterVisible), "showBack" to true, "showSearch" to true, "showFilter" to true, "showHome" to true, "filterActive" to hasActiveFilter.value, "homePath" to "/pages/tabbar/settings", "onSearchInput" to handleSearchInput, "onSearchConfirm" to handleSearchConfirm, "onSearchClear" to handleSearchClear, "onUpdate:filterVisible" to handleFilterVisibleChange, "onFilterOpen" to handleFilterOpen), _uM("filter-panel" to withSlotCtx(fun(): UTSArray<Any> {
                        return _uA(
                            _cE("view", _uM("class" to "expense-filter-panel", "style" to _nS(filterPanelStyle.value)), _uA(
                                _cE("scroll-view", _uM("scroll-y" to "true", "class" to "expense-filter-content-scroll", "style" to _nS(filterContentScrollStyle.value)), _uA(
                                    _cE("view", _uM("class" to "expense-filter-scroll-inner"), _uA(
                                        if (isTrue(unref(filterOptionsLoading))) {
                                            _cE("view", _uM("key" to 0, "class" to "expense-filter-state"), _uA(
                                                _cE("text", _uM("class" to "expense-filter-state-text"), "筛选选项加载中...")
                                            ))
                                        } else {
                                            if (unref(filterOptionsError) != "") {
                                                _cE("view", _uM("key" to 1, "class" to "expense-filter-state"), _uA(
                                                    _cE("text", _uM("class" to "expense-filter-state-text"), _tD(unref(filterOptionsError)), 1)
                                                ))
                                            } else {
                                                if (filterDefinitions.value.length == 0) {
                                                    _cE("view", _uM("key" to 2, "class" to "expense-filter-state"), _uA(
                                                        _cE("text", _uM("class" to "expense-filter-state-text"), "暂无可用筛选项")
                                                    ))
                                                } else {
                                                    _cE("view", _uM("key" to 3, "class" to "expense-filter-groups"), _uA(
                                                        _cE(Fragment, null, RenderHelpers.renderList(filterDefinitions.value, fun(filter, __key, __index, _cached): Any {
                                                            return _cE("view", _uM("key" to filter.key, "class" to "expense-filter-group"), _uA(
                                                                _cE("text", _uM("class" to "expense-filter-group-title"), _tD(filter.label), 1),
                                                                _cE("view", _uM("class" to "expense-filter-options"), _uA(
                                                                    _cE(Fragment, null, RenderHelpers.renderList(filter.options, fun(option, __key, __index, _cached): Any {
                                                                        return _cE("view", _uM("key" to (filter.key + "-" + option.value), "class" to _nC(if (isFilterOptionSelected(filter.param, option.value)) {
                                                                            "expense-filter-option expense-filter-option-active"
                                                                        } else {
                                                                            "expense-filter-option"
                                                                        }
                                                                        ), "onClick" to fun(){
                                                                            toggleFilterOption(filter.param, option.value)
                                                                        }
                                                                        ), _uA(
                                                                            _cE("text", _uM("class" to _nC(if (isFilterOptionSelected(filter.param, option.value)) {
                                                                                "expense-filter-option-text expense-filter-option-text-active"
                                                                            } else {
                                                                                "expense-filter-option-text"
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
                                    ))
                                ), 4),
                                _cE("view", _uM("class" to "expense-filter-actions"), _uA(
                                    _cE("view", _uM("class" to "expense-filter-btn expense-filter-btn-light", "onClick" to handleFilterReset), _uA(
                                        _cE("text", _uM("class" to "expense-filter-btn-light-text"), "重置")
                                    )),
                                    _cE("view", _uM("class" to "expense-filter-btn expense-filter-btn-primary", "onClick" to applySelectedFilters), _uA(
                                        _cE("text", _uM("class" to "expense-filter-btn-primary-text"), "应用")
                                    ))
                                ))
                            ), 4)
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
                                    _cE("view", _uM("class" to "retry-btn", "onClick" to loadExpenses), _uA(
                                        _cE("text", _uM("class" to "retry-btn-text"), "重新加载")
                                    ))
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            _cV(_component_lili_UniversalList, _uM("items" to listItems.value, "keyField" to "id", "titleField" to "title", "subtitleField" to "subtitle", "metaField" to "amountText", "imageField" to "cover", "imageListField" to "images", "tagField" to "tags", "fields" to unref(fieldConfig), "loading" to unref(isLoading), "loadingText" to "正在加载支出记录", "keepContentOnLoading" to true, "inlineLoadingText" to "支出数据刷新中...", "emptyText" to emptyText.value, "emptyIcon" to "◎", "showMenu" to true, "menuActions" to unref(menuActions), "showChevron" to false, "showPagination" to true, "currentPage" to unref(currentPage), "totalPages" to unref(totalPages), "totalCount" to unref(totalCount), "summaryTitle" to "支出概览", "summaryItems" to summaryItems.value, "summaryCollapsedByDefault" to false, "showFloatingAdd" to true, "floatingAddText" to "新增", "onItemClick" to handleItemClick, "onSubtitleClick" to handleSubtitleClick, "onMetaClick" to handleMetaClick, "onFieldClick" to handleFieldClick, "onMenu" to handleMenu, "onPageChange" to handlePageChange, "onFloatingAdd" to handleCreateExpense), null, 8, _uA(
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-scroll" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-content" to _pS(_uM("paddingTop" to 6, "paddingRight" to 6, "paddingBottom" to 96, "paddingLeft" to 6)), "error-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 18, "paddingRight" to 18, "paddingBottom" to 18, "paddingLeft" to 18, "marginBottom" to 10, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FECACA", "borderRightColor" to "#FECACA", "borderBottomColor" to "#FECACA", "borderLeftColor" to "#FECACA", "alignItems" to "center")), "error-title" to _pS(_uM("fontSize" to 18, "lineHeight" to "24px", "color" to "#B42318", "fontWeight" to "bold")), "error-desc" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#7F1D1D", "marginTop" to 8, "textAlign" to "center")), "retry-btn" to _pS(_uM("marginTop" to 14, "height" to 40, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#0F172A", "paddingLeft" to 18, "paddingRight" to 18, "alignItems" to "center", "justifyContent" to "center")), "retry-btn-text" to _pS(_uM("fontSize" to 14, "color" to "#FFFFFF")), "expense-filter-panel" to _pS(_uM("position" to "relative", "paddingTop" to 2)), "expense-filter-content-scroll" to _pS(_uM("paddingRight" to 2)), "expense-filter-scroll-inner" to _pS(_uM("paddingBottom" to 58)), "expense-filter-state" to _pS(_uM("height" to 112, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#F8FAFC", "alignItems" to "center", "justifyContent" to "center")), "expense-filter-state-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "17px", "color" to "#64748B")), "expense-filter-groups" to _pS(_uM("marginBottom" to 6)), "expense-filter-group" to _pS(_uM("paddingLeft" to 10, "paddingRight" to 10, "paddingTop" to 10, "paddingBottom" to 10, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#FFFFFF", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E5EAF1", "borderRightColor" to "#E5EAF1", "borderBottomColor" to "#E5EAF1", "borderLeftColor" to "#E5EAF1", "marginBottom" to 6)), "expense-filter-group-title" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#0F172A", "fontWeight" to "bold")), "expense-filter-options" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 8)), "expense-filter-option" to _pS(_uM("minWidth" to 48, "height" to 30, "paddingLeft" to 10, "paddingRight" to 10, "borderTopLeftRadius" to 15, "borderTopRightRadius" to 15, "borderBottomRightRadius" to 15, "borderBottomLeftRadius" to 15, "backgroundColor" to "#F8FAFC", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "alignItems" to "center", "justifyContent" to "center", "marginRight" to 6, "marginBottom" to 6)), "expense-filter-option-active" to _pS(_uM("backgroundColor" to "#0F172A", "borderTopColor" to "#0F172A", "borderRightColor" to "#0F172A", "borderBottomColor" to "#0F172A", "borderLeftColor" to "#0F172A")), "expense-filter-option-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "17px", "color" to "#334155")), "expense-filter-option-text-active" to _pS(_uM("color" to "#FFFFFF")), "expense-filter-actions" to _pS(_uM("position" to "absolute", "left" to 0, "right" to 0, "bottom" to 0, "flexDirection" to "row", "paddingTop" to 6, "paddingLeft" to 2, "paddingRight" to 2, "paddingBottom" to 4, "borderTopWidth" to 1, "borderTopStyle" to "solid", "borderTopColor" to "rgba(226,232,240,0.78)", "backgroundColor" to "#FFFFFF")), "expense-filter-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 38, "borderTopLeftRadius" to 11, "borderTopRightRadius" to 11, "borderBottomRightRadius" to 11, "borderBottomLeftRadius" to 11, "alignItems" to "center", "justifyContent" to "center")), "expense-filter-btn-light" to _pS(_uM("backgroundColor" to "#F3F6FA", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "marginRight" to 8)), "expense-filter-btn-primary" to _pS(_uM("backgroundColor" to "#0F172A")), "expense-filter-btn-light-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#475569")), "expense-filter-btn-primary-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#FFFFFF")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
