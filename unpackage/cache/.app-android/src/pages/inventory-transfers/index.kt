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
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesInventoryTransfersIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesInventoryTransfersIndex) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesInventoryTransfersIndex
            val _cache = __ins.renderCache
            val refreshStorageKey = "refresh:pages:inventory-transfers:index"
            val keyword = ref("")
            val filterVisible = ref(false)
            val isLoading = ref(false)
            val errorMessage = ref("")
            val items = ref(_uA<UTSJSONObject>())
            val currentPage = ref(1)
            val totalPages = ref(1)
            val totalCount = ref(0)
            val pageSize = ref(20)
            val pageTotalQuantity = ref("0")
            val pageTransferredQuantity = ref("0")
            val statusFilter = ref("")
            val fromLocationFilterValue = ref("")
            val fromLocationFilterText = ref("")
            val toLocationFilterValue = ref("")
            val toLocationFilterText = ref("")
            val draftStatus = ref("")
            val draftFromLocationValue = ref("")
            val draftFromLocationText = ref("")
            val draftToLocationValue = ref("")
            val draftToLocationText = ref("")
            val filterPanelHeight = ref(380)
            val filterContentHeight = ref(316)
            val statusOptions = _uA(
                SelectOption__11(value = "", label = "全部"),
                SelectOption__11(value = "DRAFT", label = "草稿"),
                SelectOption__11(value = "APPROVED", label = "已审核"),
                SelectOption__11(value = "COMPLETED", label = "已完成"),
                SelectOption__11(value = "CANCELLED", label = "已取消")
            )
            val fieldConfig = ref(_uA<UTSJSONObject>(_uO("key" to "dateText", "label" to "日期:"), _uO("key" to "itemsCountText", "label" to "明细:"), _uO("key" to "totalQuantityText", "label" to "总数:"), _uO("key" to "transferredQuantityText", "label" to "已调拨:")))
            val menuActions = ref(_uA<UTSJSONObject>(_uO("key" to "details", "text" to "明细"), _uO("key" to "edit", "text" to "编辑"), _uO("key" to "approve", "text" to "审核"), _uO("key" to "complete", "text" to "完成"), _uO("key" to "cancel", "text" to "取消"), _uO("key" to "reload", "text" to "刷新")))
            val tagColorMap = ref<UTSJSONObject>(_uO("草稿" to "muted", "已审核" to "info", "已完成" to "success", "已取消" to "danger", "DRAFT" to "muted", "APPROVED" to "info", "COMPLETED" to "success", "CANCELLED" to "danger"))
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
            fun gen_parseErrorMessage_fn(error: Any, fallback: String): String {
                var message = fallback
                if (error != null) {
                    val directMessage = (error as UTSError).message
                    if (directMessage != null && directMessage != "") {
                        message = directMessage
                    }
                    val errorText = JSON.stringify(error)
                    if (errorText != null && errorText != "") {
                        val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/inventory-transfers/index.uvue:231")
                        if (parsedError != null) {
                            val rawMessage = parsedError["message"]
                            if (rawMessage != null) {
                                val parsedMessage = rawMessage as String
                                if (parsedMessage != "") {
                                    message = parsedMessage
                                }
                            }
                        }
                        if (message == fallback && errorText != "{}") {
                            message = errorText
                        }
                    }
                }
                return message
            }
            val parseErrorMessage = ::gen_parseErrorMessage_fn
            fun gen_parseObject_fn(value: Any?): UTSJSONObject? {
                if (value == null) {
                    return null
                }
                val text = JSON.stringify(value)
                if (text == null || text == "") {
                    return null
                }
                val trimmedText = text.trim()
                if (trimmedText == "" || trimmedText.substring(0, 1) != "{") {
                    return null
                }
                try {
                    return UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(trimmedText), " at pages/inventory-transfers/index.uvue:252")
                }
                 catch (error: Throwable) {
                    return null
                }
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
                val trimmedText = text.trim()
                if (trimmedText == "" || trimmedText.substring(0, 1) != "[") {
                    return _uA<UTSJSONObject>()
                }
                var parsed: UTSArray<UTSJSONObject>? = null
                try {
                    parsed = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(trimmedText), " at pages/inventory-transfers/index.uvue:266")
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
            fun gen_firstStringField_fn(obj: UTSJSONObject, keys: UTSArray<String>): String {
                run {
                    var index: Number = 0
                    while(index < keys.length){
                        val value = stringValue(obj[keys[index]])
                        if (value != "") {
                            return value
                        }
                        index += 1
                    }
                }
                return ""
            }
            val firstStringField = ::gen_firstStringField_fn
            fun gen_extractRows_fn(raw: Any?): UTSArray<UTSJSONObject> {
                val directArray = parseObjectArray(raw)
                if (directArray.length > 0) {
                    return directArray
                }
                val rawObject = parseObject(raw)
                if (rawObject == null) {
                    return _uA<UTSJSONObject>()
                }
                val dataArray = parseObjectArray(rawObject!!["data"])
                if (dataArray.length > 0) {
                    return dataArray
                }
                val resultsArray = parseObjectArray(rawObject!!["results"])
                if (resultsArray.length > 0) {
                    return resultsArray
                }
                val itemsArray = parseObjectArray(rawObject!!["items"])
                if (itemsArray.length > 0) {
                    return itemsArray
                }
                val dataObject = parseObject(rawObject!!["data"])
                if (dataObject != null) {
                    val nestedResults = parseObjectArray(dataObject!!["results"])
                    if (nestedResults.length > 0) {
                        return nestedResults
                    }
                }
                return _uA<UTSJSONObject>()
            }
            val extractRows = ::gen_extractRows_fn
            fun gen_normalizeOptionNode_fn(item: UTSJSONObject): UTSJSONObject {
                val value = firstStringField(item, _uA(
                    "value",
                    "id",
                    "pk"
                ))
                var text = firstStringField(item, _uA(
                    "text",
                    "label",
                    "name",
                    "name_cn",
                    "title"
                ))
                if (text == "") {
                    text = value
                }
                return _uO("value" to value, "text" to text)
            }
            val normalizeOptionNode = ::gen_normalizeOptionNode_fn
            fun gen_buildBottomSelectResponse_fn(raw: Any?): UTSJSONObject {
                val rows = extractRows(raw)
                val result: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < rows.length){
                        result.push(normalizeOptionNode(rows[index]))
                        index += 1
                    }
                }
                return _uO("data" to result, "results" to result, "total" to result.length, "total_count" to result.length)
            }
            val buildBottomSelectResponse = ::gen_buildBottomSelectResponse_fn
            fun gen_buildOptionQuery_fn(params: UTSJSONObject): UTSJSONObject {
                val pageValue = intValue(params["page"])
                val pageSizeValue = intValue(params["pageSize"])
                val query: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("query", "pages/inventory-transfers/index.uvue", 326, 8), "page" to if (pageValue <= 0) {
                    1
                } else {
                    pageValue
                }
                , "page_size" to if (pageSizeValue <= 0) {
                    50
                } else {
                    pageSizeValue
                }
                )
                val keywordValue = stringValue(params["keyword"])
                if (keywordValue != "") {
                    query["search"] = keywordValue
                    query["keyword"] = keywordValue
                }
                return query
            }
            val buildOptionQuery = ::gen_buildOptionQuery_fn
            fun gen_fetchLocationFilterOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        val raw = await(request("/api/inventory/locations/", "GET", buildOptionQuery(params), true))
                        return@w1 buildBottomSelectResponse(raw)
                })
            }
            val fetchLocationFilterOptions = ::gen_fetchLocationFilterOptions_fn
            fun gen_statusText_fn(status: String, display: String): String {
                if (display != "") {
                    return display
                }
                if (status == "DRAFT") {
                    return "草稿"
                }
                if (status == "APPROVED") {
                    return "已审核"
                }
                if (status == "COMPLETED") {
                    return "已完成"
                }
                if (status == "CANCELLED") {
                    return "已取消"
                }
                return if (status == "") {
                    "-"
                } else {
                    status
                }
            }
            val statusText = ::gen_statusText_fn
            fun gen_compactDate_fn(value: String): String {
                if (value == "") {
                    return "-"
                }
                if (value.length >= 10) {
                    return value.substring(0, 10)
                }
                return value
            }
            val compactDate = ::gen_compactDate_fn
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
            fun gen_updateFilterPanelLayout_fn() {
                val info = uni_getWindowInfo()
                var nextPanelHeight = info.windowHeight - 168
                if (nextPanelHeight > 420) {
                    nextPanelHeight = 420
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
            fun gen_buildQuery_fn(): InventoryListQuery {
                return InventoryListQuery(search = if (keyword.value == "") {
                    null
                } else {
                    keyword.value
                }
                , page = currentPage.value, page_size = pageSize.value, status = if (statusFilter.value == "") {
                    null
                } else {
                    statusFilter.value
                }
                , alert_status = null, supplier = null, category = null, is_listed = null, location = null, from_location = if (fromLocationFilterValue.value == "") {
                    null
                } else {
                    fromLocationFilterValue.value
                }
                , to_location = if (toLocationFilterValue.value == "") {
                    null
                } else {
                    toLocationFilterValue.value
                }
                , transaction_type = null, location_type = null, is_active = null)
            }
            val buildQuery = ::gen_buildQuery_fn
            fun gen_applyListResponse_fn(response: InventoryListResponse) {
                items.value = response.results
                currentPage.value = response.current_page
                totalPages.value = response.total_pages
                totalCount.value = response.total_count
                pageSize.value = response.page_size
                var total: Number = 0
                var transferred: Number = 0
                run {
                    var index: Number = 0
                    while(index < response.results.length){
                        val row = response.results[index]
                        total = total + intValue(row["total_quantity"])
                        transferred = transferred + intValue(row["transferred_quantity"])
                        index += 1
                    }
                }
                pageTotalQuantity.value = total.toString(10)
                pageTransferredQuantity.value = transferred.toString(10)
            }
            val applyListResponse = ::gen_applyListResponse_fn
            fun gen_loadItems_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (isLoading.value) {
                            return@w1
                        }
                        isLoading.value = true
                        errorMessage.value = ""
                        try {
                            applyListResponse(await(getInventoryTransfers(buildQuery())))
                        }
                         catch (error: Throwable) {
                            items.value = _uA<UTSJSONObject>()
                            currentPage.value = 1
                            totalPages.value = 1
                            totalCount.value = 0
                            pageTotalQuantity.value = "0"
                            pageTransferredQuantity.value = "0"
                            errorMessage.value = parseErrorMessage(error, "调拨单加载失败")
                        }
                         finally {
                            isLoading.value = false
                        }
                })
            }
            val loadItems = ::gen_loadItems_fn
            fun gen_handleSearchInput_fn(value: String) {
                keyword.value = value
            }
            val handleSearchInput = ::gen_handleSearchInput_fn
            fun gen_handleSearchConfirm_fn(value: String) {
                keyword.value = value
                currentPage.value = 1
                loadItems()
            }
            val handleSearchConfirm = ::gen_handleSearchConfirm_fn
            fun gen_handleSearchClear_fn() {
                keyword.value = ""
                currentPage.value = 1
                loadItems()
            }
            val handleSearchClear = ::gen_handleSearchClear_fn
            fun gen_handleFilterVisibleChange_fn(value: Boolean) {
                filterVisible.value = value
            }
            val handleFilterVisibleChange = ::gen_handleFilterVisibleChange_fn
            fun gen_handleFilterOpen_fn() {
                updateFilterPanelLayout()
                draftStatus.value = statusFilter.value
                draftFromLocationValue.value = fromLocationFilterValue.value
                draftFromLocationText.value = fromLocationFilterText.value
                draftToLocationValue.value = toLocationFilterValue.value
                draftToLocationText.value = toLocationFilterText.value
            }
            val handleFilterOpen = ::gen_handleFilterOpen_fn
            fun gen_handleFromLocationFilterChange_fn(payload: UTSJSONObject) {
                draftFromLocationValue.value = stringValue(payload["value"])
                draftFromLocationText.value = stringValue(payload["text"])
            }
            val handleFromLocationFilterChange = ::gen_handleFromLocationFilterChange_fn
            fun gen_handleToLocationFilterChange_fn(payload: UTSJSONObject) {
                draftToLocationValue.value = stringValue(payload["value"])
                draftToLocationText.value = stringValue(payload["text"])
            }
            val handleToLocationFilterChange = ::gen_handleToLocationFilterChange_fn
            fun gen_selectStatus_fn(value: String) {
                draftStatus.value = value
            }
            val selectStatus = ::gen_selectStatus_fn
            fun gen_handleFilterReset_fn() {
                statusFilter.value = ""
                fromLocationFilterValue.value = ""
                fromLocationFilterText.value = ""
                toLocationFilterValue.value = ""
                toLocationFilterText.value = ""
                draftStatus.value = ""
                draftFromLocationValue.value = ""
                draftFromLocationText.value = ""
                draftToLocationValue.value = ""
                draftToLocationText.value = ""
                keyword.value = ""
                currentPage.value = 1
                closeFilterDrawer()
                loadItems()
            }
            val handleFilterReset = ::gen_handleFilterReset_fn
            fun gen_applySelectedFilters_fn() {
                statusFilter.value = draftStatus.value
                fromLocationFilterValue.value = draftFromLocationValue.value
                fromLocationFilterText.value = draftFromLocationText.value
                toLocationFilterValue.value = draftToLocationValue.value
                toLocationFilterText.value = draftToLocationText.value
                currentPage.value = 1
                closeFilterDrawer()
                loadItems()
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
                loadItems()
            }
            val handlePageChange = ::gen_handlePageChange_fn
            fun gen_transferItem_fn(item: UTSJSONObject): UTSJSONObject {
                val status = statusText(stringValue(item["status"]), stringValue(item["status_display"]))
                val fromName = stringValue(item["from_location_name"], "-")
                val toName = stringValue(item["to_location_name"], "-")
                val dateText = compactDate(stringValue(item["transfer_date"]))
                return _uO("id" to stringValue(item["id"]), "rawId" to stringValue(item["id"]), "title" to stringValue(item["transfer_number"], "调拨单"), "subtitle" to (fromName + " -> " + toName), "meta" to dateText, "statusText" to status, "dateText" to dateText, "itemsCountText" to stringValue(item["items_count"], "0"), "totalQuantityText" to stringValue(item["total_quantity"], "0"), "transferredQuantityText" to stringValue(item["transferred_quantity"], "0"), "remarkText" to stringValue(item["remark"], "-"), "tags" to _uA<String>(status))
            }
            val transferItem = ::gen_transferItem_fn
            fun gen_navigateToEdit_fn(id: String) {
                if (id == "") {
                    return
                }
                uni_navigateTo(NavigateToOptions(url = "/pages/inventory-transfers/from?id=" + id))
            }
            val navigateToEdit = ::gen_navigateToEdit_fn
            fun gen_navigateToDetails_fn(id: String) {
                if (id == "") {
                    return
                }
                uni_navigateTo(NavigateToOptions(url = "/pages/inventory-transfers/details/index?transfer=" + id))
            }
            val navigateToDetails = ::gen_navigateToDetails_fn
            fun gen_handleItemClick_fn(payload: UTSJSONObject) {
                navigateToEdit(stringValue(payload["rawId"], stringValue(payload["id"])))
            }
            val handleItemClick = ::gen_handleItemClick_fn
            fun gen_runAction_fn(actionName: String, id: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        try {
                            if (actionName == "approve") {
                                await(approveInventoryTransfer(id))
                            } else if (actionName == "complete") {
                                await(completeInventoryTransfer(id))
                            } else if (actionName == "cancel") {
                                await(cancelInventoryTransfer(id))
                            }
                            uni_showToast(ShowToastOptions(title = takeLatestResponseMessage("操作成功"), icon = "success"))
                            loadItems()
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error, "操作失败"), icon = "none"))
                        }
                })
            }
            val runAction = ::gen_runAction_fn
            fun gen_confirmRunAction_fn(actionKey: String, id: String, title: String, content: String) {
                uni_showModal(ShowModalOptions(title = title, content = content, success = fun(res){
                    if (res.confirm) {
                        runAction(actionKey, id)
                    }
                }
                ))
            }
            val confirmRunAction = ::gen_confirmRunAction_fn
            fun gen_handleMenu_fn(payload: UTSJSONObject) {
                val action = payload["action"]
                val item = payload["item"]
                if (action == null || item == null) {
                    return
                }
                val actionKey = stringValue((action as UTSJSONObject)["key"])
                val id = stringValue((item as UTSJSONObject)["rawId"])
                if (actionKey == "details") {
                    navigateToDetails(id)
                } else if (actionKey == "edit") {
                    navigateToEdit(id)
                } else if (actionKey == "approve") {
                    confirmRunAction(actionKey, id, "审核调拨", "确定审核通过这张调拨单吗？")
                } else if (actionKey == "complete") {
                    confirmRunAction(actionKey, id, "完成调拨", "确定完成这张调拨单吗？")
                } else if (actionKey == "cancel") {
                    confirmRunAction(actionKey, id, "取消调拨", "确定取消这张调拨单吗？")
                } else if (actionKey == "reload") {
                    loadItems()
                }
            }
            val handleMenu = ::gen_handleMenu_fn
            fun gen_handleSubtitleClick_fn(payload: UTSJSONObject) {
                copyText(stringValue(payload["value"]), "调拨路线已复制", "暂无调拨路线")
            }
            val handleSubtitleClick = ::gen_handleSubtitleClick_fn
            fun gen_handleMetaClick_fn(payload: UTSJSONObject) {
                copyText(stringValue(payload["value"]), "调拨日期已复制", "暂无调拨日期")
            }
            val handleMetaClick = ::gen_handleMetaClick_fn
            fun gen_handleFieldClick_fn(payload: UTSJSONObject) {
                val value = stringValue(payload["value"])
                val label = stringValue(payload["label"], "内容")
                copyText(value, label.replace(":", "") + "已复制", "暂无内容")
            }
            val handleFieldClick = ::gen_handleFieldClick_fn
            fun gen_handleCreate_fn() {
                uni_navigateTo(NavigateToOptions(url = "/pages/inventory-transfers/create"))
            }
            val handleCreate = ::gen_handleCreate_fn
            fun gen_consumeRefresh_fn(): Boolean {
                val flag = uni_getStorageSync(refreshStorageKey)
                if (flag == null || ("" + flag) == "") {
                    return false
                }
                uni_removeStorageSync(refreshStorageKey)
                return true
            }
            val consumeRefresh = ::gen_consumeRefresh_fn
            val listItems = computed(fun(): UTSArray<UTSJSONObject> {
                val result: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < items.value.length){
                        result.push(transferItem(items.value[index]))
                        index += 1
                    }
                }
                return result
            }
            )
            val hasActiveFilter = computed(fun(): Boolean {
                return keyword.value != "" || statusFilter.value != "" || fromLocationFilterValue.value != "" || toLocationFilterValue.value != ""
            }
            )
            val emptyText = computed(fun(): String {
                if (isLoading.value) {
                    return "正在加载"
                }
                if (hasActiveFilter.value) {
                    return "没有匹配的调拨单"
                }
                return "暂无调拨单"
            }
            )
            val summaryItems = computed(fun(): UTSArray<UTSJSONObject> {
                return _uA(
                    _uO("key" to "total", "label" to "调拨单数", "value" to totalCount.value.toString(10)),
                    _uO("key" to "quantity", "label" to "本页数量", "value" to pageTotalQuantity.value),
                    _uO("key" to "transferred", "label" to "本页已调拨", "value" to pageTransferredQuantity.value),
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
            onLoad(fun(_options){
                updateFilterPanelLayout()
                loadItems()
            }
            )
            onShow(fun(){
                updateFilterPanelLayout()
                if (consumeRefresh()) {
                    loadItems()
                }
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList", GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalListClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to "调拨单", "searchPlaceholder" to "调拨单号、备注", "searchValue" to unref(keyword), "filterVisible" to unref(filterVisible), "showBack" to true, "showSearch" to true, "showFilter" to true, "showHome" to true, "filterActive" to hasActiveFilter.value, "filterText" to "重置", "homePath" to "/pages/tabbar/settings", "onSearchInput" to handleSearchInput, "onSearchConfirm" to handleSearchConfirm, "onSearchClear" to handleSearchClear, "onUpdate:filterVisible" to handleFilterVisibleChange, "onFilterOpen" to handleFilterOpen), _uM("filter-panel" to withSlotCtx(fun(): UTSArray<Any> {
                        return _uA(
                            _cE("view", _uM("class" to "transfer-filter-panel", "style" to _nS(filterPanelStyle.value)), _uA(
                                _cE("scroll-view", _uM("scroll-y" to "true", "class" to "transfer-filter-content-scroll", "style" to _nS(filterContentScrollStyle.value)), _uA(
                                    _cE("view", _uM("class" to "transfer-filter-scroll-inner"), _uA(
                                        _cE("view", _uM("class" to "transfer-filter-select-group"), _uA(
                                            _cE("text", _uM("class" to "transfer-filter-select-title"), "调出位置"),
                                            _cE("view", _uM("class" to "transfer-filter-select-wrap"), _uA(
                                                _cV(unref(GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelectClass), _uM("value" to unref(draftFromLocationValue), "valueText" to unref(draftFromLocationText), "title" to "选择调出位置", "placeholder" to "全部调出位置", "searchPlaceholder" to "搜索库存位置", "emptyText" to "暂无库存位置", "fetchData" to fetchLocationFilterOptions, "showAddAction" to false, "showEditAction" to false, "onChange" to handleFromLocationFilterChange), null, 8, _uA(
                                                    "value",
                                                    "valueText"
                                                ))
                                            ))
                                        )),
                                        _cE("view", _uM("class" to "transfer-filter-select-group"), _uA(
                                            _cE("text", _uM("class" to "transfer-filter-select-title"), "调入位置"),
                                            _cE("view", _uM("class" to "transfer-filter-select-wrap"), _uA(
                                                _cV(unref(GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelectClass), _uM("value" to unref(draftToLocationValue), "valueText" to unref(draftToLocationText), "title" to "选择调入位置", "placeholder" to "全部调入位置", "searchPlaceholder" to "搜索库存位置", "emptyText" to "暂无库存位置", "fetchData" to fetchLocationFilterOptions, "showAddAction" to false, "showEditAction" to false, "onChange" to handleToLocationFilterChange), null, 8, _uA(
                                                    "value",
                                                    "valueText"
                                                ))
                                            ))
                                        )),
                                        _cE("view", _uM("class" to "transfer-filter-group"), _uA(
                                            _cE("text", _uM("class" to "transfer-filter-group-title"), "调拨状态"),
                                            _cE("view", _uM("class" to "transfer-filter-options"), _uA(
                                                _cE(Fragment, null, RenderHelpers.renderList(statusOptions, fun(option, __key, __index, _cached): Any {
                                                    return _cE("view", _uM("key" to ("status-" + option.value), "class" to _nC(if (unref(draftStatus) == option.value) {
                                                        "transfer-filter-option transfer-filter-option-active"
                                                    } else {
                                                        "transfer-filter-option"
                                                    }
                                                    ), "onClick" to fun(){
                                                        selectStatus(option.value)
                                                    }
                                                    ), _uA(
                                                        _cE("text", _uM("class" to _nC(if (unref(draftStatus) == option.value) {
                                                            "transfer-filter-option-text transfer-filter-option-text-active"
                                                        } else {
                                                            "transfer-filter-option-text"
                                                        }
                                                        )), _tD(option.label), 3)
                                                    ), 10, _uA(
                                                        "onClick"
                                                    ))
                                                }
                                                ), 64)
                                            ))
                                        ))
                                    ))
                                ), 4),
                                _cE("view", _uM("class" to "transfer-filter-actions"), _uA(
                                    _cE("view", _uM("class" to "transfer-filter-btn transfer-filter-btn-light", "onClick" to handleFilterReset), _uA(
                                        _cE("text", _uM("class" to "transfer-filter-btn-light-text"), "重置")
                                    )),
                                    _cE("view", _uM("class" to "transfer-filter-btn transfer-filter-btn-primary", "onClick" to applySelectedFilters), _uA(
                                        _cE("text", _uM("class" to "transfer-filter-btn-primary-text"), "应用")
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
                                    _cE("view", _uM("class" to "retry-btn", "onClick" to loadItems), _uA(
                                        _cE("text", _uM("class" to "retry-btn-text"), "重新加载")
                                    ))
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            _cV(_component_lili_UniversalList, _uM("items" to listItems.value, "keyField" to "id", "titleField" to "title", "subtitleField" to "subtitle", "metaField" to "meta", "tagField" to "tags", "tagColorMap" to unref(tagColorMap), "fields" to unref(fieldConfig), "loading" to unref(isLoading), "loadingText" to "正在加载调拨单", "keepContentOnLoading" to true, "inlineLoadingText" to "调拨单刷新中...", "emptyText" to emptyText.value, "emptyIcon" to "◎", "showMenu" to true, "menuActions" to unref(menuActions), "showChevron" to false, "showPagination" to true, "currentPage" to unref(currentPage), "totalPages" to unref(totalPages), "totalCount" to unref(totalCount), "summaryTitle" to "调拨概览", "summaryItems" to summaryItems.value, "showFloatingAdd" to true, "floatingAddText" to "新增调拨", "onItemClick" to handleItemClick, "onMenu" to handleMenu, "onPageChange" to handlePageChange, "onSubtitleClick" to handleSubtitleClick, "onFieldClick" to handleFieldClick, "onMetaClick" to handleMetaClick, "onFloatingAdd" to handleCreate), null, 8, _uA(
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-scroll" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-content" to _pS(_uM("paddingTop" to 6, "paddingRight" to 6, "paddingBottom" to 96, "paddingLeft" to 6)), "error-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 18, "paddingRight" to 18, "paddingBottom" to 18, "paddingLeft" to 18, "marginBottom" to 10, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FECACA", "borderRightColor" to "#FECACA", "borderBottomColor" to "#FECACA", "borderLeftColor" to "#FECACA", "alignItems" to "center")), "error-title" to _pS(_uM("fontSize" to 18, "lineHeight" to "24px", "color" to "#B42318", "fontWeight" to "bold")), "error-desc" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#7F1D1D", "marginTop" to 8, "textAlign" to "center")), "retry-btn" to _pS(_uM("marginTop" to 14, "height" to 40, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#0F172A", "paddingLeft" to 18, "paddingRight" to 18, "alignItems" to "center", "justifyContent" to "center")), "retry-btn-text" to _pS(_uM("fontSize" to 14, "color" to "#FFFFFF")), "transfer-filter-panel" to _pS(_uM("position" to "relative", "paddingTop" to 2)), "transfer-filter-content-scroll" to _pS(_uM("paddingRight" to 2)), "transfer-filter-scroll-inner" to _pS(_uM("paddingBottom" to 58)), "transfer-filter-select-group" to _pS(_uM("paddingLeft" to 10, "paddingRight" to 10, "paddingTop" to 10, "paddingBottom" to 10, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#FFFFFF", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E5EAF1", "borderRightColor" to "#E5EAF1", "borderBottomColor" to "#E5EAF1", "borderLeftColor" to "#E5EAF1", "marginBottom" to 6)), "transfer-filter-group" to _pS(_uM("paddingLeft" to 10, "paddingRight" to 10, "paddingTop" to 10, "paddingBottom" to 10, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#FFFFFF", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E5EAF1", "borderRightColor" to "#E5EAF1", "borderBottomColor" to "#E5EAF1", "borderLeftColor" to "#E5EAF1", "marginBottom" to 6)), "transfer-filter-select-title" to _pS(_uM("fontSize" to 13, "lineHeight" to "17px", "color" to "#0F172A", "fontWeight" to "bold")), "transfer-filter-group-title" to _pS(_uM("fontSize" to 13, "lineHeight" to "17px", "color" to "#0F172A", "fontWeight" to "bold")), "transfer-filter-select-wrap" to _pS(_uM("marginTop" to 8)), "transfer-filter-options" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 8)), "transfer-filter-option" to _pS(_uM("minWidth" to 48, "height" to 30, "paddingLeft" to 10, "paddingRight" to 10, "borderTopLeftRadius" to 15, "borderTopRightRadius" to 15, "borderBottomRightRadius" to 15, "borderBottomLeftRadius" to 15, "backgroundColor" to "#F8FAFC", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "alignItems" to "center", "justifyContent" to "center", "marginRight" to 6, "marginBottom" to 6)), "transfer-filter-option-active" to _pS(_uM("backgroundColor" to "#0F172A", "borderTopColor" to "#0F172A", "borderRightColor" to "#0F172A", "borderBottomColor" to "#0F172A", "borderLeftColor" to "#0F172A")), "transfer-filter-option-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "17px", "color" to "#334155")), "transfer-filter-option-text-active" to _pS(_uM("color" to "#FFFFFF")), "transfer-filter-actions" to _pS(_uM("position" to "absolute", "left" to 0, "right" to 0, "bottom" to 0, "flexDirection" to "row", "paddingTop" to 6, "paddingLeft" to 2, "paddingRight" to 2, "paddingBottom" to 4, "borderTopWidth" to 1, "borderTopStyle" to "solid", "borderTopColor" to "rgba(226,232,240,0.78)", "backgroundColor" to "#FFFFFF")), "transfer-filter-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 38, "borderTopLeftRadius" to 11, "borderTopRightRadius" to 11, "borderBottomRightRadius" to 11, "borderBottomLeftRadius" to 11, "alignItems" to "center", "justifyContent" to "center")), "transfer-filter-btn-light" to _pS(_uM("backgroundColor" to "#F3F6FA", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "marginRight" to 8)), "transfer-filter-btn-primary" to _pS(_uM("backgroundColor" to "#0F172A")), "transfer-filter-btn-light-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#475569")), "transfer-filter-btn-primary-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#FFFFFF")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
