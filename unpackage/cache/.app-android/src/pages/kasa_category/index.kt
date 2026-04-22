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
import io.dcloud.uniapp.extapi.showActionSheet as uni_showActionSheet
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesKasaCategoryIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesKasaCategoryIndex) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesKasaCategoryIndex
            val _cache = __ins.renderCache
            val kasaCategoryListRefreshStorageKey = "refresh:pages:kasa_category:index"
            val keyword = ref("")
            val filterVisible = ref(false)
            val isLoading = ref(false)
            val errorMessage = ref("")
            val kasaCategories = ref(_uA<KasaCategoryItem>())
            val currentPage = ref(1)
            val totalPages = ref(1)
            val totalCount = ref(0)
            val pageSize = ref(20)
            val statistics = ref<KasaCategoryStatisticsResponse?>(null)
            val taxRateLoading = ref(false)
            val taxRateResponse = ref<KasaCategoryTaxRatesResponse?>(null)
            val selectedIsActive = ref<String?>(null)
            val selectedTaxRate = ref<String?>(null)
            val selectionMode = ref(false)
            val selectedCategoryIds = ref(_uA<String>())
            val statusFilterOptions = ref(_uA<SelectOption__2>(SelectOption__2(value = "true", text = "启用"), SelectOption__2(value = "false", text = "停用")))
            val fieldConfig = ref(_uA<UTSJSONObject>(_uO("key" to "taxRateText", "label" to "税率"), _uO("key" to "productsCountText", "label" to "商品数")))
            val menuActions = ref(_uA<UTSJSONObject>(_uO("key" to "edit", "text" to "编辑"), _uO("key" to "toggle-status", "text" to "切换状态"), _uO("key" to "delete", "text" to "删除")))
            val batchToolbarActions = ref(_uA<UTSJSONObject>(_uO("key" to "more", "text" to "操作")))
            val defaultBatchActionMap = ref(_uA<UTSJSONObject>(_uO("key" to "activate", "text" to "批量启用"), _uO("key" to "deactivate", "text" to "批量停用"), _uO("key" to "delete", "text" to "批量删除")))
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
            fun gen_intValue_fn(value: Any?): Number {
                if (value == null) {
                    return 0
                }
                val text = "" + value
                if (text == "") {
                    return 0
                }
                val parsed = parseInt(text)
                if (isNaN(parsed)) {
                    return 0
                }
                return parsed
            }
            val intValue = ::gen_intValue_fn
            fun gen_parseErrorMessage_fn(error: Any): String {
                var message = "收银分类列表加载失败"
                if (error != null) {
                    val directMessage = (error as UTSError).message
                    if (directMessage != null && directMessage != "") {
                        message = directMessage
                    }
                    val errorText = JSON.stringify(error)
                    if (errorText != null && errorText != "") {
                        val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/kasa_category/index.uvue:216")
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
            fun gen_applyResponse_fn(response: KasaCategoryListResponse) {
                kasaCategories.value = response.results
                currentPage.value = response.current_page
                totalPages.value = response.total_pages
                totalCount.value = response.total_count
                pageSize.value = response.page_size
            }
            val applyResponse = ::gen_applyResponse_fn
            fun gen_loadKasaCategories_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (isLoading.value) {
                            return@w1
                        }
                        isLoading.value = true
                        errorMessage.value = ""
                        try {
                            val response = await(getKasaCategoryList(KasaCategoryListQuery(search = if (keyword.value == "") {
                                null
                            } else {
                                keyword.value
                            }
                            , page = currentPage.value, page_size = pageSize.value, id = null, is_active = selectedIsActive.value, tax_rate = selectedTaxRate.value, unique_kod = null, simple = null)))
                            applyResponse(response)
                        }
                         catch (error: Throwable) {
                            kasaCategories.value = _uA()
                            currentPage.value = 1
                            totalPages.value = 1
                            totalCount.value = 0
                            errorMessage.value = parseErrorMessage(error)
                        }
                         finally {
                            isLoading.value = false
                        }
                })
            }
            val loadKasaCategories = ::gen_loadKasaCategories_fn
            fun gen_loadStatistics_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        try {
                            statistics.value = await(getKasaCategoryStatistics(KasaCategoryListQuery(search = if (keyword.value == "") {
                                null
                            } else {
                                keyword.value
                            }
                            , page = currentPage.value, page_size = pageSize.value, id = null, is_active = selectedIsActive.value, tax_rate = selectedTaxRate.value, unique_kod = null, simple = null)))
                        }
                         catch (error: Throwable) {
                            statistics.value = null
                        }
                })
            }
            val loadStatistics = ::gen_loadStatistics_fn
            fun gen_buildTaxRateLabel_fn(item: UTSJSONObject): String {
                val text = stringValue(item["text"])
                if (text != "") {
                    return text
                }
                val label = stringValue(item["label"])
                if (label != "") {
                    return label
                }
                val rate = stringValue(item["tax_rate"])
                if (rate != "") {
                    return rate
                }
                val value = stringValue(item["value"])
                if (value != "") {
                    return value
                }
                return "-"
            }
            val buildTaxRateLabel = ::gen_buildTaxRateLabel_fn
            fun gen_buildTaxRateValue_fn(item: UTSJSONObject): String {
                val value = stringValue(item["value"])
                if (value != "") {
                    return value
                }
                val rate = stringValue(item["tax_rate"])
                if (rate != "") {
                    return rate
                }
                val id = stringValue(item["id"])
                if (id != "") {
                    return id
                }
                val code = stringValue(item["code"])
                if (code != "") {
                    return code
                }
                return buildTaxRateLabel(item)
            }
            val buildTaxRateValue = ::gen_buildTaxRateValue_fn
            fun gen_normalizeTaxRateOptions_fn(): UTSArray<SelectOption__2> {
                if (taxRateResponse.value == null) {
                    return _uA()
                }
                val result: UTSArray<SelectOption__2> = _uA()
                run {
                    var index: Number = 0
                    while(index < taxRateResponse.value!!.items.length){
                        val item = taxRateResponse.value!!.items[index]
                        val value = buildTaxRateValue(item)
                        if (value == "") {
                            index += 1
                            continue
                        }
                        val text = buildTaxRateLabel(item)
                        var exists = false
                        run {
                            var optionIndex: Number = 0
                            while(optionIndex < result.length){
                                if (result[optionIndex].value == value) {
                                    exists = true
                                    break
                                }
                                optionIndex += 1
                            }
                        }
                        if (!exists) {
                            result.push(SelectOption__2(value = value, text = text))
                        }
                        index += 1
                    }
                }
                return result
            }
            val normalizeTaxRateOptions = ::gen_normalizeTaxRateOptions_fn
            fun gen_loadTaxRateOptions_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (taxRateResponse.value != null || taxRateLoading.value) {
                            return@w1
                        }
                        taxRateLoading.value = true
                        try {
                            taxRateResponse.value = await(getKasaCategoryTaxRates())
                        }
                         catch (error: Throwable) {
                            taxRateResponse.value = null
                        }
                         finally {
                            taxRateLoading.value = false
                        }
                })
            }
            val loadTaxRateOptions = ::gen_loadTaxRateOptions_fn
            fun gen_handleFilterOpen_fn() {
                loadTaxRateOptions()
            }
            val handleFilterOpen = ::gen_handleFilterOpen_fn
            fun gen_closeFilterDrawer_fn() {
                filterVisible.value = false
            }
            val closeFilterDrawer = ::gen_closeFilterDrawer_fn
            fun gen_handleFilterVisibleChange_fn(value: Boolean) {
                filterVisible.value = value
            }
            val handleFilterVisibleChange = ::gen_handleFilterVisibleChange_fn
            fun gen_isSelectedStatusOption_fn(value: String): Boolean {
                return selectedIsActive.value == value
            }
            val isSelectedStatusOption = ::gen_isSelectedStatusOption_fn
            fun gen_isSelectedTaxRateOption_fn(value: String): Boolean {
                return selectedTaxRate.value == value
            }
            val isSelectedTaxRateOption = ::gen_isSelectedTaxRateOption_fn
            fun gen_toggleStatusFilter_fn(value: String) {
                selectedIsActive.value = if (selectedIsActive.value == value) {
                    null
                } else {
                    value
                }
            }
            val toggleStatusFilter = ::gen_toggleStatusFilter_fn
            fun gen_toggleTaxRateFilter_fn(value: String) {
                selectedTaxRate.value = if (selectedTaxRate.value == value) {
                    null
                } else {
                    value
                }
            }
            val toggleTaxRateFilter = ::gen_toggleTaxRateFilter_fn
            fun gen_handleFilterReset_fn() {
                selectedIsActive.value = null
                selectedTaxRate.value = null
                keyword.value = ""
                currentPage.value = 1
                closeFilterDrawer()
                loadKasaCategories()
                loadStatistics()
            }
            val handleFilterReset = ::gen_handleFilterReset_fn
            fun gen_applySelectedFilters_fn() {
                currentPage.value = 1
                closeFilterDrawer()
                loadKasaCategories()
                loadStatistics()
            }
            val applySelectedFilters = ::gen_applySelectedFilters_fn
            fun gen_handleSearchInput_fn(value: String) {
                keyword.value = value
            }
            val handleSearchInput = ::gen_handleSearchInput_fn
            fun gen_handleSearchConfirm_fn(value: String) {
                keyword.value = value
                currentPage.value = 1
                loadKasaCategories()
                loadStatistics()
            }
            val handleSearchConfirm = ::gen_handleSearchConfirm_fn
            fun gen_handleSearchClear_fn() {
                keyword.value = ""
                currentPage.value = 1
                loadKasaCategories()
                loadStatistics()
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
                loadKasaCategories()
            }
            val handlePageChange = ::gen_handlePageChange_fn
            fun gen_kasaCategoryToListItem_fn(item: KasaCategoryItem): UTSJSONObject {
                val chineseName = stringValue(item.name_cn, "-")
                val englishName = stringValue(item.name_en, "-")
                var displayName = chineseName
                if (displayName == "-") {
                    displayName = englishName
                }
                return _uO("id" to item.id.toString(10), "name" to displayName, "name_cn" to chineseName, "name_en" to englishName, "englishNameText" to ("英文名：" + englishName), "unique_kod" to stringValue(item.unique_kod, "-"), "uniqueKodText" to ("uniqueKod：" + stringValue(item.unique_kod, "-")), "taxRateText" to stringValue(item.tax_rate_display, stringValue(item.tax_rate, "-")), "productsCountText" to item.products_count.toString(10), "statusText" to if (item.is_active) {
                    "启用"
                } else {
                    "停用"
                }
                , "is_active" to item.is_active, "rawId" to item.id.toString(10))
            }
            val kasaCategoryToListItem = ::gen_kasaCategoryToListItem_fn
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
            fun gen_handleItemClick_fn(payload: UTSJSONObject) {
                val item = payload["item"]
                if (item == null) {
                    return
                }
                val itemObject = item as UTSJSONObject
                val rawId = stringValue(itemObject["rawId"])
                if (rawId == "") {
                    return
                }
                uni_navigateTo(NavigateToOptions(url = "/pages/kasa_category/form?id=" + rawId))
            }
            val handleItemClick = ::gen_handleItemClick_fn
            fun gen_handleSubtitleClick_fn(payload: UTSJSONObject) {
                val item = payload["item"]
                if (item == null) {
                    return
                }
                val itemObject = item as UTSJSONObject
                copyText(stringValue(itemObject["name_en"]), "英文名已复制", "暂无英文名")
            }
            val handleSubtitleClick = ::gen_handleSubtitleClick_fn
            fun gen_handleMetaClick_fn(payload: UTSJSONObject) {
                val item = payload["item"]
                if (item == null) {
                    return
                }
                val itemObject = item as UTSJSONObject
                copyText(stringValue(itemObject["unique_kod"]), "uniqueKod已复制", "暂无uniqueKod")
            }
            val handleMetaClick = ::gen_handleMetaClick_fn
            fun gen_handleFieldClick_fn(payload: UTSJSONObject) {
                val key = stringValue(payload["key"])
                val itemValue = payload["item"]
                if (key == "" || itemValue == null) {
                    return
                }
                val item = itemValue as UTSJSONObject
                if (key == "taxRateText") {
                    copyText(stringValue(item["taxRateText"]), "税率已复制", "暂无税率")
                    return
                }
                if (key == "uniqueKodText") {
                    copyText(stringValue(item["unique_kod"]), "唯一编码已复制", "暂无唯一编码")
                    return
                }
                if (key == "productsCountText") {
                    copyText(stringValue(item["productsCountText"]), "商品数已复制", "暂无商品数")
                    return
                }
                if (key == "statusText") {
                    copyText(stringValue(item["statusText"]), "状态已复制", "暂无状态")
                }
            }
            val handleFieldClick = ::gen_handleFieldClick_fn
            fun gen_selectedItems_fn(): UTSArray<KasaCategoryItem> {
                val result: UTSArray<KasaCategoryItem> = _uA()
                run {
                    var index: Number = 0
                    while(index < kasaCategories.value.length){
                        val item = kasaCategories.value[index]
                        if (selectedCategoryIds.value.includes(item.id.toString(10))) {
                            result.push(item)
                        }
                        index += 1
                    }
                }
                return result
            }
            val selectedItems = ::gen_selectedItems_fn
            fun gen_clearSelectionState_fn() {
                selectionMode.value = false
                selectedCategoryIds.value = _uA<String>()
            }
            val clearSelectionState = ::gen_clearSelectionState_fn
            fun gen_handleSelectionModeChange_fn(value: Boolean) {
                selectionMode.value = value
                if (!value) {
                    selectedCategoryIds.value = _uA<String>()
                }
            }
            val handleSelectionModeChange = ::gen_handleSelectionModeChange_fn
            fun gen_handleSelectedCategoryIdsChange_fn(value: UTSArray<String>) {
                val nextIds: UTSArray<String> = _uA()
                run {
                    var index: Number = 0
                    while(index < value.length){
                        nextIds.push(value[index])
                        index += 1
                    }
                }
                selectedCategoryIds.value = nextIds
            }
            val handleSelectedCategoryIdsChange = ::gen_handleSelectedCategoryIdsChange_fn
            fun gen_handleSelectionExit_fn(payload: UTSJSONObject) {
                clearSelectionState()
            }
            val handleSelectionExit = ::gen_handleSelectionExit_fn
            fun gen_batchActionTitle_fn(actionKey: String): String {
                if (actionKey == "activate") {
                    return "批量启用"
                }
                if (actionKey == "deactivate") {
                    return "批量停用"
                }
                if (actionKey == "delete") {
                    return "批量删除"
                }
                return "批量操作"
            }
            val batchActionTitle = ::gen_batchActionTitle_fn
            fun gen_batchActionConfirmText_fn(actionKey: String, count: Number): String {
                if (actionKey == "activate") {
                    return "确定批量启用选中的 " + count + " 个收银分类吗？"
                }
                if (actionKey == "deactivate") {
                    return "确定批量停用选中的 " + count + " 个收银分类吗？"
                }
                if (actionKey == "delete") {
                    return "确定批量删除选中的 " + count + " 个收银分类吗？"
                }
                return "确定执行批量操作吗？"
            }
            val batchActionConfirmText = ::gen_batchActionConfirmText_fn
            fun gen_executeBatchAction_fn(actionKey: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        val items = selectedItems()
                        if (items.length == 0) {
                            uni_showToast(ShowToastOptions(title = "请先选择收银分类", icon = "none"))
                            return@w1
                        }
                        try {
                            run {
                                var index: Number = 0
                                while(index < items.length){
                                    val item = items[index]
                                    if (actionKey == "delete") {
                                        await(deleteKasaCategory(item.id))
                                        index += 1
                                        continue
                                    }
                                    if (actionKey == "activate") {
                                        await(patchKasaCategory(item.id, _uO("is_active" to true)))
                                        index += 1
                                        continue
                                    }
                                    if (actionKey == "deactivate") {
                                        await(patchKasaCategory(item.id, _uO("is_active" to false)))
                                        index += 1
                                        continue
                                    }
                                    index += 1
                                }
                            }
                            uni_showToast(ShowToastOptions(title = batchActionTitle(actionKey) + "成功", icon = "success"))
                            clearSelectionState()
                            loadKasaCategories()
                            loadStatistics()
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error), icon = "none"))
                        }
                })
            }
            val executeBatchAction = ::gen_executeBatchAction_fn
            fun gen_confirmBatchAction_fn(actionKey: String) {
                val count = selectedCategoryIds.value.length
                uni_showModal(ShowModalOptions(title = batchActionTitle(actionKey), content = batchActionConfirmText(actionKey, count), success = fun(res){
                    if (!res.confirm) {
                        return
                    }
                    executeBatchAction(actionKey)
                }
                ))
            }
            val confirmBatchAction = ::gen_confirmBatchAction_fn
            fun gen_openBatchActionSheet_fn() {
                val itemList: UTSArray<String> = _uA()
                run {
                    var index: Number = 0
                    while(index < defaultBatchActionMap.value.length){
                        itemList.push(stringValue(defaultBatchActionMap.value[index]["text"]))
                        index += 1
                    }
                }
                uni_showActionSheet(ShowActionSheetOptions(itemList = itemList, success = fun(res){
                    val tapIndex = res.tapIndex
                    if (tapIndex < 0 || tapIndex >= defaultBatchActionMap.value.length) {
                        return
                    }
                    val action = defaultBatchActionMap.value[tapIndex]
                    val actionKey = stringValue(action["key"])
                    if (actionKey == "") {
                        return
                    }
                    confirmBatchAction(actionKey)
                }
                ))
            }
            val openBatchActionSheet = ::gen_openBatchActionSheet_fn
            fun gen_handleBatchToolbarAction_fn(payload: UTSJSONObject) {
                val actionValue = payload["action"]
                if (actionValue == null) {
                    return
                }
                val action = actionValue as UTSJSONObject
                val actionKey = stringValue(action["key"])
                if (actionKey == "more") {
                    openBatchActionSheet()
                }
            }
            val handleBatchToolbarAction = ::gen_handleBatchToolbarAction_fn
            fun gen_confirmDelete_fn(id: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        try {
                            await(deleteKasaCategory(id))
                            uni_showToast(ShowToastOptions(title = "删除成功", icon = "success"))
                            loadKasaCategories()
                            loadStatistics()
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error), icon = "none"))
                        }
                })
            }
            val confirmDelete = ::gen_confirmDelete_fn
            fun gen_toggleSingleStatus_fn(id: String, currentActive: Boolean): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        try {
                            await(patchKasaCategory(id, _uO("is_active" to !currentActive)))
                            uni_showToast(ShowToastOptions(title = "状态更新成功", icon = "success"))
                            loadKasaCategories()
                            loadStatistics()
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error), icon = "none"))
                        }
                })
            }
            val toggleSingleStatus = ::gen_toggleSingleStatus_fn
            fun gen_handleMenu_fn(payload: UTSJSONObject) {
                val actionValue = payload["action"]
                val itemValue = payload["item"]
                if (actionValue == null || itemValue == null) {
                    return
                }
                val action = actionValue as UTSJSONObject
                val item = itemValue as UTSJSONObject
                val actionKey = stringValue(action["key"])
                val rawId = stringValue(item["rawId"])
                if (actionKey == "edit") {
                    uni_navigateTo(NavigateToOptions(url = "/pages/kasa_category/form?id=" + rawId))
                    return
                }
                if (actionKey == "toggle-status") {
                    val currentActive = stringValue(item["statusText"]) == "启用"
                    toggleSingleStatus(rawId, currentActive)
                    return
                }
                if (actionKey == "delete") {
                    uni_showModal(ShowModalOptions(title = "删除收银分类", content = "确定删除这个收银分类吗？", success = fun(res){
                        if (!res.confirm) {
                            return
                        }
                        confirmDelete(rawId)
                    }
                    ))
                }
            }
            val handleMenu = ::gen_handleMenu_fn
            fun gen_handleCreateKasaCategory_fn() {
                uni_navigateTo(NavigateToOptions(url = "/pages/kasa_category/form"))
            }
            val handleCreateKasaCategory = ::gen_handleCreateKasaCategory_fn
            fun gen_consumeRefreshNeeded_fn(): Boolean {
                val storedValue = uni_getStorageSync(kasaCategoryListRefreshStorageKey)
                if (storedValue == null) {
                    return false
                }
                val storedText = "" + storedValue
                if (storedText == "") {
                    return false
                }
                uni_removeStorageSync(kasaCategoryListRefreshStorageKey)
                return true
            }
            val consumeRefreshNeeded = ::gen_consumeRefreshNeeded_fn
            fun gen_getStatisticsValue_fn(keys: UTSArray<String>, fallback: String): String {
                if (statistics.value == null) {
                    return fallback
                }
                run {
                    var index: Number = 0
                    while(index < keys.length){
                        val rawValue = statistics.value!!.data[keys[index]]
                        if (rawValue != null) {
                            val text = "" + rawValue
                            if (text != "") {
                                return text
                            }
                        }
                        index += 1
                    }
                }
                return fallback
            }
            val getStatisticsValue = ::gen_getStatisticsValue_fn
            val taxRateOptions = computed(fun(): UTSArray<SelectOption__2> {
                return normalizeTaxRateOptions()
            }
            )
            val listItems = computed(fun(): UTSArray<UTSJSONObject> {
                val result: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < kasaCategories.value.length){
                        result.push(kasaCategoryToListItem(kasaCategories.value[index]))
                        index += 1
                    }
                }
                return result
            }
            )
            val summaryItems = computed(fun(): UTSArray<UTSJSONObject> {
                return _uA(
                    _uO("key" to "total", "label" to "分类总数", "value" to getStatisticsValue(_uA(
                        "total",
                        "count",
                        "total_count",
                        "total_categories"
                    ), totalCount.value.toString(10))),
                    _uO("key" to "active", "label" to "启用数量", "value" to getStatisticsValue(_uA(
                        "active_count",
                        "active",
                        "enabled_count"
                    ), "0")),
                    _uO("key" to "inactive", "label" to "停用数量", "value" to getStatisticsValue(_uA(
                        "inactive_count",
                        "inactive",
                        "disabled_count"
                    ), "0"))
                )
            }
            )
            val hasActiveFilter = computed(fun(): Boolean {
                return keyword.value != "" || selectedIsActive.value != null || selectedTaxRate.value != null
            }
            )
            val emptyText = computed(fun(): String {
                if (isLoading.value) {
                    return "正在加载"
                }
                if (hasActiveFilter.value) {
                    return "没有匹配的收银分类"
                }
                return "暂无收银分类"
            }
            )
            onLoad(fun(_options){
                loadKasaCategories()
                loadStatistics()
            }
            )
            onShow(fun(){
                if (consumeRefreshNeeded()) {
                    loadKasaCategories()
                    loadStatistics()
                }
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList", GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalListClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to "收银分类", "searchPlaceholder" to "输入名称、编码、唯一编码", "searchValue" to unref(keyword), "filterVisible" to unref(filterVisible), "showBack" to true, "showSearch" to true, "showFilter" to true, "showHome" to true, "filterActive" to hasActiveFilter.value, "filterText" to "重置", "homePath" to "/pages/tabbar/settings", "onSearchInput" to handleSearchInput, "onSearchConfirm" to handleSearchConfirm, "onSearchClear" to handleSearchClear, "onUpdate:filterVisible" to handleFilterVisibleChange, "onFilterOpen" to handleFilterOpen), _uM("filter-panel" to withSlotCtx(fun(): UTSArray<Any> {
                        return _uA(
                            _cE("view", _uM("class" to "filter-panel"), _uA(
                                _cE("view", _uM("class" to "filter-actions"), _uA(
                                    _cE("view", _uM("class" to "filter-btn filter-btn-light", "onClick" to handleFilterReset), _uA(
                                        _cE("text", _uM("class" to "filter-btn-light-text"), "重置")
                                    )),
                                    _cE("view", _uM("class" to "filter-btn filter-btn-primary", "onClick" to applySelectedFilters), _uA(
                                        _cE("text", _uM("class" to "filter-btn-primary-text"), "应用")
                                    ))
                                )),
                                _cE("scroll-view", _uM("scroll-y" to "true", "class" to "filter-scroll"), _uA(
                                    _cE("view", _uM("class" to "filter-group"), _uA(
                                        _cE("text", _uM("class" to "filter-group-title"), "启用状态"),
                                        _cE("view", _uM("class" to "filter-options"), _uA(
                                            _cE(Fragment, null, RenderHelpers.renderList(unref(statusFilterOptions), fun(option, __key, __index, _cached): Any {
                                                return _cE("view", _uM("key" to ("status-" + option.value), "class" to _nC(if (isSelectedStatusOption(option.value)) {
                                                    "filter-option filter-option-active"
                                                } else {
                                                    "filter-option"
                                                }
                                                ), "onClick" to fun(){
                                                    toggleStatusFilter(option.value)
                                                }
                                                ), _uA(
                                                    _cE("text", _uM("class" to _nC(if (isSelectedStatusOption(option.value)) {
                                                        "filter-option-text filter-option-text-active"
                                                    } else {
                                                        "filter-option-text"
                                                    }
                                                    )), _tD(option.text), 3)
                                                ), 10, _uA(
                                                    "onClick"
                                                ))
                                            }
                                            ), 128)
                                        ))
                                    )),
                                    _cE("view", _uM("class" to "filter-group"), _uA(
                                        _cE("text", _uM("class" to "filter-group-title"), "税率"),
                                        if (isTrue(unref(taxRateLoading))) {
                                            _cE("view", _uM("key" to 0, "class" to "filter-state"), _uA(
                                                _cE("text", _uM("class" to "filter-state-text"), "税率选项加载中...")
                                            ))
                                        } else {
                                            if (taxRateOptions.value.length == 0) {
                                                _cE("view", _uM("key" to 1, "class" to "filter-state"), _uA(
                                                    _cE("text", _uM("class" to "filter-state-text"), "暂无税率选项")
                                                ))
                                            } else {
                                                _cE("view", _uM("key" to 2, "class" to "filter-options"), _uA(
                                                    _cE(Fragment, null, RenderHelpers.renderList(taxRateOptions.value, fun(option, __key, __index, _cached): Any {
                                                        return _cE("view", _uM("key" to ("tax-" + option.value), "class" to _nC(if (isSelectedTaxRateOption(option.value)) {
                                                            "filter-option filter-option-active"
                                                        } else {
                                                            "filter-option"
                                                        }
                                                        ), "onClick" to fun(){
                                                            toggleTaxRateFilter(option.value)
                                                        }
                                                        ), _uA(
                                                            _cE("text", _uM("class" to _nC(if (isSelectedTaxRateOption(option.value)) {
                                                                "filter-option-text filter-option-text-active"
                                                            } else {
                                                                "filter-option-text"
                                                            }
                                                            )), _tD(option.text), 3)
                                                        ), 10, _uA(
                                                            "onClick"
                                                        ))
                                                    }
                                                    ), 128)
                                                ))
                                            }
                                        }
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
                                    _cE("button", _uM("class" to "retry-btn", "onClick" to loadKasaCategories), _uA(
                                        _cE("text", _uM("class" to "retry-btn-text"), "重新加载")
                                    ))
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            _cV(_component_lili_UniversalList, _uM("items" to listItems.value, "keyField" to "id", "titleField" to "name", "subtitleField" to "englishNameText", "metaField" to "uniqueKodText", "fields" to unref(fieldConfig), "loading" to unref(isLoading), "loadingText" to "正在加载收银分类", "keepContentOnLoading" to true, "inlineLoadingText" to "收银分类数据刷新中...", "emptyText" to emptyText.value, "emptyIcon" to "◎", "showMenu" to true, "menuActions" to unref(menuActions), "showChevron" to false, "showPagination" to true, "currentPage" to unref(currentPage), "totalPages" to unref(totalPages), "totalCount" to unref(totalCount), "selectionMode" to unref(selectionMode), "selectedItems" to unref(selectedCategoryIds), "batchActions" to unref(batchToolbarActions), "summaryTitle" to "收银分类统计", "summaryItems" to summaryItems.value, "summaryCollapsedByDefault" to true, "showFloatingAdd" to true, "floatingAddText" to "新增", "onUpdate:selectionMode" to handleSelectionModeChange, "onUpdate:selectedItems" to handleSelectedCategoryIdsChange, "onSelectionExit" to handleSelectionExit, "onBatchAction" to handleBatchToolbarAction, "onItemClick" to handleItemClick, "onSubtitleClick" to handleSubtitleClick, "onMetaClick" to handleMetaClick, "onFieldClick" to handleFieldClick, "onMenu" to handleMenu, "onPageChange" to handlePageChange, "onFloatingAdd" to handleCreateKasaCategory), null, 8, _uA(
                                "items",
                                "fields",
                                "loading",
                                "emptyText",
                                "menuActions",
                                "currentPage",
                                "totalPages",
                                "totalCount",
                                "selectionMode",
                                "selectedItems",
                                "batchActions",
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-scroll" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-content" to _pS(_uM("paddingLeft" to 6, "paddingRight" to 6, "paddingTop" to 6, "paddingBottom" to 96)), "error-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "paddingTop" to 18, "paddingRight" to 18, "paddingBottom" to 18, "paddingLeft" to 18, "marginBottom" to 14, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FECACA", "borderRightColor" to "#FECACA", "borderBottomColor" to "#FECACA", "borderLeftColor" to "#FECACA", "alignItems" to "center")), "error-title" to _pS(_uM("fontSize" to 18, "lineHeight" to "24px", "color" to "#B42318", "fontWeight" to "bold")), "error-desc" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#7F1D1D", "marginTop" to 8, "textAlign" to "center")), "retry-btn" to _pS(_uM("marginTop" to 14, "height" to 40, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#0F172A", "paddingLeft" to 18, "paddingRight" to 18)), "retry-btn-text" to _pS(_uM("fontSize" to 14, "color" to "#FFFFFF")), "filter-panel" to _pS(_uM("paddingBottom" to 8)), "filter-actions" to _pS(_uM("flexDirection" to "row", "marginBottom" to 12)), "filter-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 40, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "alignItems" to "center", "justifyContent" to "center")), "filter-btn-light" to _pS(_uM("backgroundColor" to "#F3F6FA", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "marginRight" to 8)), "filter-btn-primary" to _pS(_uM("backgroundColor" to "#0F172A")), "filter-btn-light-text" to _pS(_uM("fontSize" to 14, "lineHeight" to "14px", "color" to "#475569")), "filter-btn-primary-text" to _pS(_uM("fontSize" to 14, "lineHeight" to "14px", "color" to "#FFFFFF")), "filter-scroll" to _pS(_uM("height" to 360)), "filter-group" to _pS(_uM("paddingLeft" to 12, "paddingRight" to 12, "paddingTop" to 12, "paddingBottom" to 12, "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "backgroundColor" to "#FFFFFF", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E5EAF1", "borderRightColor" to "#E5EAF1", "borderBottomColor" to "#E5EAF1", "borderLeftColor" to "#E5EAF1", "marginBottom" to 8)), "filter-group-title" to _pS(_uM("fontSize" to 14, "lineHeight" to "18px", "color" to "#0F172A", "fontWeight" to "bold")), "filter-options" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 10)), "filter-option" to _pS(_uM("minWidth" to 56, "height" to 34, "paddingLeft" to 12, "paddingRight" to 12, "borderTopLeftRadius" to 17, "borderTopRightRadius" to 17, "borderBottomRightRadius" to 17, "borderBottomLeftRadius" to 17, "backgroundColor" to "#F8FAFC", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "alignItems" to "center", "justifyContent" to "center", "marginRight" to 8, "marginBottom" to 8)), "filter-option-active" to _pS(_uM("backgroundColor" to "#0F172A", "borderTopColor" to "#0F172A", "borderRightColor" to "#0F172A", "borderBottomColor" to "#0F172A", "borderLeftColor" to "#0F172A")), "filter-option-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "13px", "color" to "#475569")), "filter-option-text-active" to _pS(_uM("color" to "#FFFFFF")), "filter-state" to _pS(_uM("height" to 92, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#F8FAFC", "alignItems" to "center", "justifyContent" to "center", "marginTop" to 10)), "filter-state-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#64748B")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
