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
import uts.sdk.modules.limeScan.scanCode
import uts.sdk.modules.limeScan.GeneralCallbackResult
import uts.sdk.modules.limeScan.ScanCodeOption
import uts.sdk.modules.limeScan.ScanCodeSuccessCallbackResult
import io.dcloud.uniapp.extapi.setClipboardData as uni_setClipboardData
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesInventoryManagementIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesInventoryManagementIndex) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesInventoryManagementIndex
            val _cache = __ins.renderCache
            val keyword = ref("")
            val refreshStorageKey = "refresh:pages:inventory-management:index"
            val filterVisible = ref(false)
            val isLoading = ref(false)
            val errorMessage = ref("")
            val items = ref(_uA<UTSJSONObject>())
            val currentPage = ref(1)
            val totalPages = ref(1)
            val totalCount = ref(0)
            val pageSize = ref(20)
            val alertStatus = ref<String?>(null)
            val selectedAlertStatus = ref<String?>(null)
            val fieldConfig = ref(_uA<UTSJSONObject>(_uO("key" to "quantity_text", "label" to "现存"), _uO("key" to "available_text", "label" to "可用"), _uO("key" to "alert_text", "label" to "状态")))
            val menuActions = ref(_uA<UTSJSONObject>(_uO("key" to "adjust-stock", "text" to "调整库存"), _uO("key" to "reload", "text" to "刷新")))
            fun stringField(obj: UTSJSONObject, key: String, fallback: String = ""): String {
                val value = obj[key]
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
                if (error == null) {
                    return fallback
                }
                val text = JSON.stringify(error)
                if (text == null || text == "") {
                    return fallback
                }
                return text
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
            fun gen_alertLabel_fn(value: String): String {
                if (value == "LOW_STOCK") {
                    return "低库存"
                }
                if (value == "OUT_OF_STOCK") {
                    return "缺货"
                }
                if (value == "NO_MOVEMENT") {
                    return "久未动"
                }
                return "正常"
            }
            val alertLabel = ::gen_alertLabel_fn
            fun gen_applyListResponse_fn(response: InventoryListResponse) {
                items.value = response.results
                currentPage.value = response.current_page
                totalPages.value = response.total_pages
                totalCount.value = response.total_count
                pageSize.value = response.page_size
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
                            val query = InventoryListQuery(search = if (keyword.value == "") {
                                null
                            } else {
                                keyword.value
                            }
                            , page = currentPage.value, page_size = pageSize.value, status = null, alert_status = alertStatus.value, transaction_type = null, location_type = null, is_active = null)
                            applyListResponse(await(getInventoryStocks(query)))
                        }
                         catch (error: Throwable) {
                            items.value = _uA<UTSJSONObject>()
                            currentPage.value = 1
                            totalPages.value = 1
                            totalCount.value = 0
                            errorMessage.value = parseErrorMessage(error, "库存数据加载失败")
                        }
                         finally {
                            isLoading.value = false
                        }
                })
            }
            val loadItems = ::gen_loadItems_fn
            fun gen_selectAlertStatus_fn(value: String?) {
                alertStatus.value = value
                currentPage.value = 1
                loadItems()
            }
            val selectAlertStatus = ::gen_selectAlertStatus_fn
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
            fun gen_handleScanSearch_fn() {
                scanCode(ScanCodeOption(onlyFromCamera = true, success = fun(res: ScanCodeSuccessCallbackResult){
                    val scanResult = res.result
                    if (scanResult == "") {
                        return
                    }
                    keyword.value = scanResult
                    currentPage.value = 1
                    filterVisible.value = false
                    loadItems()
                }
                , fail = fun(res: GeneralCallbackResult){
                    val message = if (res.errMsg == "") {
                        "扫码失败"
                    } else {
                        res.errMsg
                    }
                    uni_showToast(ShowToastOptions(title = message, icon = "none"))
                }
                ))
            }
            val handleScanSearch = ::gen_handleScanSearch_fn
            fun gen_handleFilterVisibleChange_fn(value: Boolean) {
                filterVisible.value = value
            }
            val handleFilterVisibleChange = ::gen_handleFilterVisibleChange_fn
            fun gen_handleFilterOpen_fn() {
                selectedAlertStatus.value = alertStatus.value
            }
            val handleFilterOpen = ::gen_handleFilterOpen_fn
            fun gen_selectFilterStatus_fn(value: String?) {
                selectedAlertStatus.value = value
            }
            val selectFilterStatus = ::gen_selectFilterStatus_fn
            fun gen_handleFilterReset_fn() {
                selectedAlertStatus.value = null
                alertStatus.value = null
                currentPage.value = 1
                filterVisible.value = false
                loadItems()
            }
            val handleFilterReset = ::gen_handleFilterReset_fn
            fun gen_applyFilter_fn() {
                alertStatus.value = selectedAlertStatus.value
                currentPage.value = 1
                filterVisible.value = false
                loadItems()
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
                loadItems()
            }
            val handlePageChange = ::gen_handlePageChange_fn
            fun gen_stockItem_fn(item: UTSJSONObject): UTSJSONObject {
                val status = stringField(item, "alert_status", "NORMAL")
                return _uO("id" to stringField(item, "id"), "rawId" to stringField(item, "id"), "name" to stringField(item, "product_name", "未命名商品"), "subtitle" to ("SKU：" + stringField(item, "product_sku", "-")), "meta" to stringField(item, "location_name", "-"), "quantity_text" to stringField(item, "quantity", "0"), "available_text" to stringField(item, "available_quantity", "0"), "alert_text" to alertLabel(status), "tags" to _uA<String>(alertLabel(status)))
            }
            val stockItem = ::gen_stockItem_fn
            fun gen_openStockAdjust_fn(id: String) {
                if (id == "") {
                    return
                }
                uni_navigateTo(NavigateToOptions(url = "/pages/inventory-management/from?id=" + id))
            }
            val openStockAdjust = ::gen_openStockAdjust_fn
            fun gen_handleMenu_fn(payload: UTSJSONObject) {
                val action = payload["action"]
                val item = payload["item"]
                if (action == null || item == null) {
                    return
                }
                val actionKey = stringField(action as UTSJSONObject, "key")
                val itemObject = item as UTSJSONObject
                val id = stringField(itemObject, "rawId")
                if (actionKey == "adjust-stock") {
                    openStockAdjust(id)
                } else if (actionKey == "reload") {
                    loadItems()
                }
            }
            val handleMenu = ::gen_handleMenu_fn
            fun gen_handleSubtitleClick_fn(payload: UTSJSONObject) {
                val item = payload["item"]
                if (item == null) {
                    return
                }
                copyText(stringField(item as UTSJSONObject, "subtitle"), "内容已复制", "暂无内容")
            }
            val handleSubtitleClick = ::gen_handleSubtitleClick_fn
            fun gen_handleFieldClick_fn(payload: UTSJSONObject) {
                val item = payload["item"]
                val keyValue = payload["key"]
                if (item == null || keyValue == null) {
                    return
                }
                copyText(stringField(item as UTSJSONObject, keyValue as String), "内容已复制", "暂无内容")
            }
            val handleFieldClick = ::gen_handleFieldClick_fn
            val listItems = computed(fun(): UTSArray<UTSJSONObject> {
                val result: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < items.value.length){
                        result.push(stockItem(items.value[index]))
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
                if (keyword.value != "") {
                    return "没有匹配的库存"
                }
                return "暂无库存"
            }
            )
            val summaryItems = computed(fun(): UTSArray<UTSJSONObject> {
                return _uA(
                    _uO("key" to "total", "label" to "库存记录", "value" to totalCount.value.toString(10)),
                    _uO("key" to "page", "label" to "页码", "value" to (currentPage.value.toString(10) + "/" + totalPages.value.toString(10)))
                )
            }
            )
            val hasActiveFilter = computed(fun(): Boolean {
                return alertStatus.value != null
            }
            )
            onLoad(fun(_options){
                loadItems()
            }
            )
            onShow(fun(){
                val flag = uni_getStorageSync(refreshStorageKey)
                if (flag != null && flag != "") {
                    uni_removeStorageSync(refreshStorageKey)
                    loadItems()
                }
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList", GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalListClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to "库存管理", "searchPlaceholder" to "商品名称、SKU、条码", "searchValue" to unref(keyword), "filterVisible" to unref(filterVisible), "showBack" to true, "showSearch" to true, "showFilter" to true, "showScan" to true, "showHome" to true, "filterActive" to hasActiveFilter.value, "filterText" to "重置", "homePath" to "/pages/tabbar/settings", "onSearchInput" to handleSearchInput, "onSearchConfirm" to handleSearchConfirm, "onSearchClear" to handleSearchClear, "onScan" to handleScanSearch, "onUpdate:filterVisible" to handleFilterVisibleChange, "onFilterOpen" to handleFilterOpen), _uM("filter-panel" to withSlotCtx(fun(): UTSArray<Any> {
                        return _uA(
                            _cE("view", _uM("class" to "inventory-filter-panel"), _uA(
                                _cE("view", _uM("class" to "inventory-filter-actions"), _uA(
                                    _cE("view", _uM("class" to "inventory-filter-btn inventory-filter-btn-light", "onClick" to handleFilterReset), _uA(
                                        _cE("text", _uM("class" to "inventory-filter-btn-light-text"), "重置")
                                    )),
                                    _cE("view", _uM("class" to "inventory-filter-btn inventory-filter-btn-primary", "onClick" to applyFilter), _uA(
                                        _cE("text", _uM("class" to "inventory-filter-btn-primary-text"), "应用")
                                    ))
                                )),
                                _cE("view", _uM("class" to "inventory-filter-group"), _uA(
                                    _cE("text", _uM("class" to "inventory-filter-group-title"), "库存状态"),
                                    _cE("view", _uM("class" to "inventory-filter-options"), _uA(
                                        _cE("view", _uM("class" to _nC(if (unref(selectedAlertStatus) == null) {
                                            "inventory-filter-option inventory-filter-option-active"
                                        } else {
                                            "inventory-filter-option"
                                        }
                                        ), "onClick" to fun(){
                                            selectFilterStatus(null)
                                        }
                                        ), _uA(
                                            _cE("text", _uM("class" to _nC(if (unref(selectedAlertStatus) == null) {
                                                "inventory-filter-option-text inventory-filter-option-text-active"
                                            } else {
                                                "inventory-filter-option-text"
                                            }
                                            )), "全部", 2)
                                        ), 10, _uA(
                                            "onClick"
                                        )),
                                        _cE("view", _uM("class" to _nC(if (unref(selectedAlertStatus) == "LOW_STOCK") {
                                            "inventory-filter-option inventory-filter-option-active"
                                        } else {
                                            "inventory-filter-option"
                                        }
                                        ), "onClick" to fun(){
                                            selectFilterStatus("LOW_STOCK")
                                        }
                                        ), _uA(
                                            _cE("text", _uM("class" to _nC(if (unref(selectedAlertStatus) == "LOW_STOCK") {
                                                "inventory-filter-option-text inventory-filter-option-text-active"
                                            } else {
                                                "inventory-filter-option-text"
                                            }
                                            )), "低库存", 2)
                                        ), 10, _uA(
                                            "onClick"
                                        )),
                                        _cE("view", _uM("class" to _nC(if (unref(selectedAlertStatus) == "OUT_OF_STOCK") {
                                            "inventory-filter-option inventory-filter-option-active"
                                        } else {
                                            "inventory-filter-option"
                                        }
                                        ), "onClick" to fun(){
                                            selectFilterStatus("OUT_OF_STOCK")
                                        }
                                        ), _uA(
                                            _cE("text", _uM("class" to _nC(if (unref(selectedAlertStatus) == "OUT_OF_STOCK") {
                                                "inventory-filter-option-text inventory-filter-option-text-active"
                                            } else {
                                                "inventory-filter-option-text"
                                            }
                                            )), "缺货", 2)
                                        ), 10, _uA(
                                            "onClick"
                                        )),
                                        _cE("view", _uM("class" to _nC(if (unref(selectedAlertStatus) == "NO_MOVEMENT") {
                                            "inventory-filter-option inventory-filter-option-active"
                                        } else {
                                            "inventory-filter-option"
                                        }
                                        ), "onClick" to fun(){
                                            selectFilterStatus("NO_MOVEMENT")
                                        }
                                        ), _uA(
                                            _cE("text", _uM("class" to _nC(if (unref(selectedAlertStatus) == "NO_MOVEMENT") {
                                                "inventory-filter-option-text inventory-filter-option-text-active"
                                            } else {
                                                "inventory-filter-option-text"
                                            }
                                            )), "久未动", 2)
                                        ), 10, _uA(
                                            "onClick"
                                        ))
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
                                    _cE("view", _uM("class" to "retry-btn", "onClick" to loadItems), _uA(
                                        _cE("text", _uM("class" to "retry-btn-text"), "重新加载")
                                    ))
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            _cV(_component_lili_UniversalList, _uM("items" to listItems.value, "keyField" to "id", "titleField" to "name", "subtitleField" to "subtitle", "metaField" to "meta", "tagField" to "tags", "fields" to unref(fieldConfig), "loading" to unref(isLoading), "loadingText" to "正在加载库存", "keepContentOnLoading" to true, "inlineLoadingText" to "库存数据刷新中...", "emptyText" to emptyText.value, "emptyIcon" to "◎", "showMenu" to true, "menuActions" to unref(menuActions), "showChevron" to false, "showPagination" to true, "currentPage" to unref(currentPage), "totalPages" to unref(totalPages), "totalCount" to unref(totalCount), "summaryTitle" to "库存概览", "summaryItems" to summaryItems.value, "summaryCollapsedByDefault" to false, "showFloatingAdd" to false, "onMenu" to handleMenu, "onPageChange" to handlePageChange, "onSubtitleClick" to handleSubtitleClick, "onFieldClick" to handleFieldClick), null, 8, _uA(
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-scroll" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-content" to _pS(_uM("paddingTop" to 6, "paddingRight" to 6, "paddingBottom" to 96, "paddingLeft" to 6)), "inventory-filter-panel" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#FFFFFF", "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 14, "paddingLeft" to 14)), "inventory-filter-actions" to _pS(_uM("flexDirection" to "row", "justifyContent" to "flex-end", "marginBottom" to 12)), "inventory-filter-btn" to _pS(_uM("height" to 38, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingLeft" to 18, "paddingRight" to 18, "alignItems" to "center", "justifyContent" to "center", "marginLeft" to 8)), "inventory-filter-btn-light" to _pS(_uM("backgroundColor" to "#F8FAFC", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#CBD5E1", "borderRightColor" to "#CBD5E1", "borderBottomColor" to "#CBD5E1", "borderLeftColor" to "#CBD5E1")), "inventory-filter-btn-primary" to _pS(_uM("backgroundColor" to "#0F172A")), "inventory-filter-btn-light-text" to _pS(_uM("fontSize" to 14, "color" to "#334155")), "inventory-filter-btn-primary-text" to _pS(_uM("fontSize" to 14, "color" to "#FFFFFF")), "inventory-filter-group" to _pS(_uM("marginTop" to 6)), "inventory-filter-group-title" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#0F172A", "fontWeight" to "bold")), "inventory-filter-options" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 10)), "inventory-filter-option" to _pS(_uM("height" to 34, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingLeft" to 12, "paddingRight" to 12, "backgroundColor" to "#F8FAFC", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "alignItems" to "center", "justifyContent" to "center", "marginRight" to 8, "marginBottom" to 8)), "inventory-filter-option-active" to _pS(_uM("backgroundColor" to "#0F172A", "borderTopColor" to "#0F172A", "borderRightColor" to "#0F172A", "borderBottomColor" to "#0F172A", "borderLeftColor" to "#0F172A")), "inventory-filter-option-text" to _pS(_uM("fontSize" to 13, "color" to "#475569")), "inventory-filter-option-text-active" to _pS(_uM("color" to "#FFFFFF")), "error-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 18, "paddingRight" to 18, "paddingBottom" to 18, "paddingLeft" to 18, "marginBottom" to 10, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FECACA", "borderRightColor" to "#FECACA", "borderBottomColor" to "#FECACA", "borderLeftColor" to "#FECACA", "alignItems" to "center")), "error-title" to _pS(_uM("fontSize" to 18, "lineHeight" to "24px", "color" to "#B42318", "fontWeight" to "bold")), "error-desc" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#7F1D1D", "marginTop" to 8, "textAlign" to "center")), "retry-btn" to _pS(_uM("marginTop" to 14, "height" to 40, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#0F172A", "paddingLeft" to 18, "paddingRight" to 18, "alignItems" to "center", "justifyContent" to "center")), "retry-btn-text" to _pS(_uM("fontSize" to 14, "color" to "#FFFFFF")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
