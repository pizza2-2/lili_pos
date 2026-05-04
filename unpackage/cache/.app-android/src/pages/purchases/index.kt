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
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesPurchasesIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesPurchasesIndex) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesPurchasesIndex
            val _cache = __ins.renderCache
            val refreshStorageKey = "refresh:pages:purchases:index"
            val keyword = ref("")
            val purchases = ref(_uA<PurchaseItem>())
            val isLoading = ref(false)
            val errorMessage = ref("")
            val currentPage = ref(1)
            val totalPages = ref(1)
            val totalCount = ref(0)
            val pageSize = ref(20)
            val pageTotalAmount = ref("0.00")
            val fieldConfig = ref(_uA<UTSJSONObject>(_uO("key" to "supplierText", "label" to "供应商:"), _uO("key" to "quantityText", "label" to "数量:"), _uO("key" to "progressText", "label" to "收货:"), _uO("key" to "remarkText", "label" to "备注:")))
            val menuActions = ref(_uA<UTSJSONObject>(_uO("key" to "details", "text" to "明细"), _uO("key" to "edit", "text" to "编辑"), _uO("key" to "approve", "text" to "审核"), _uO("key" to "complete", "text" to "完成"), _uO("key" to "cancel", "text" to "取消"), _uO("key" to "delete", "text" to "删除")))
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
                        val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/purchases/index.uvue:109")
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
            fun gen_applyResponse_fn(response: PurchaseListResponse) {
                purchases.value = response.results
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
            fun gen_loadPurchases_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (isLoading.value) {
                            return@w1
                        }
                        isLoading.value = true
                        errorMessage.value = ""
                        try {
                            val response = await(getPurchaseList(PurchaseListQuery(search = if (keyword.value == "") {
                                null
                            } else {
                                keyword.value
                            }
                            , page = currentPage.value, page_size = pageSize.value, status = null, receive_status = null, supplier = null, date_from = null, date_to = null, min_amount = null, max_amount = null)))
                            applyResponse(response)
                        }
                         catch (error: Throwable) {
                            purchases.value = _uA()
                            currentPage.value = 1
                            totalPages.value = 1
                            totalCount.value = 0
                            pageTotalAmount.value = "0.00"
                            errorMessage.value = parseErrorMessage(error, "采购单加载失败")
                        }
                         finally {
                            isLoading.value = false
                        }
                })
            }
            val loadPurchases = ::gen_loadPurchases_fn
            fun gen_purchaseToListItem_fn(item: PurchaseItem): UTSJSONObject {
                val numberText = stringValue(item.purchase_number, "采购单")
                val statusText = stringValue(item.status_display, item.status)
                return _uO("id" to item.id.toString(10), "rawId" to item.id.toString(10), "title" to numberText, "subtitle" to ("日期：" + stringValue(item.purchase_date, "-")), "amountText" to ("¥ " + stringValue(item.total_amount, "0.00")), "supplierText" to stringValue(item.supplier_name, "-"), "quantityText" to (item.received_quantity.toString(10) + "/" + item.total_quantity.toString(10)), "progressText" to (stringValue(item.receive_progress, "0") + "%"), "remarkText" to stringValue(item.remark, "-"), "tags" to _uA<String>(statusText, if (item.is_fully_received) {
                    "已收齐"
                } else {
                    "未收齐"
                }
                ))
            }
            val purchaseToListItem = ::gen_purchaseToListItem_fn
            fun gen_consumeRefresh_fn(): Boolean {
                val flag = uni_getStorageSync(refreshStorageKey)
                if (flag == null || ("" + flag) == "") {
                    return false
                }
                uni_removeStorageSync(refreshStorageKey)
                return true
            }
            val consumeRefresh = ::gen_consumeRefresh_fn
            fun gen_handleSearchInput_fn(value: String) {
                keyword.value = value
            }
            val handleSearchInput = ::gen_handleSearchInput_fn
            fun gen_handleSearchConfirm_fn(value: String) {
                keyword.value = value
                currentPage.value = 1
                loadPurchases()
            }
            val handleSearchConfirm = ::gen_handleSearchConfirm_fn
            fun gen_handleSearchClear_fn() {
                keyword.value = ""
                currentPage.value = 1
                loadPurchases()
            }
            val handleSearchClear = ::gen_handleSearchClear_fn
            fun gen_handleCreate_fn(payload: UTSJSONObject) {
                uni_navigateTo(NavigateToOptions(url = "/pages/purchases/from"))
            }
            val handleCreate = ::gen_handleCreate_fn
            fun gen_handleItemClick_fn(payload: UTSJSONObject) {
                val id = stringValue(payload["rawId"], stringValue(payload["id"]))
                if (id != "") {
                    uni_navigateTo(NavigateToOptions(url = "/pages/purchases/details/index?purchase=" + id))
                }
            }
            val handleItemClick = ::gen_handleItemClick_fn
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
                loadPurchases()
            }
            val handlePageChange = ::gen_handlePageChange_fn
            fun gen_runAction_fn(id: String, actionName: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        try {
                            await(runPurchaseAction(id, actionName))
                            uni_showToast(ShowToastOptions(title = takeLatestResponseMessage("操作成功"), icon = "success"))
                            loadPurchases()
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error, "操作失败"), icon = "none"))
                        }
                })
            }
            val runAction = ::gen_runAction_fn
            fun gen_confirmAction_fn(id: String, actionName: String, title: String, content: String) {
                uni_showModal(ShowModalOptions(title = title, content = content, success = fun(res){
                    if (res.confirm) {
                        runAction(id, actionName)
                    }
                }
                ))
            }
            val confirmAction = ::gen_confirmAction_fn
            fun gen_runDelete_fn(id: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        try {
                            await(deletePurchase(id))
                            uni_showToast(ShowToastOptions(title = takeLatestResponseMessage("删除成功"), icon = "success"))
                            loadPurchases()
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error, "删除失败"), icon = "none"))
                        }
                })
            }
            val runDelete = ::gen_runDelete_fn
            fun gen_confirmDelete_fn(id: String) {
                uni_showModal(ShowModalOptions(title = "删除采购单", content = "确定删除这张采购单吗？", success = fun(res){
                    if (res.confirm) {
                        runDelete(id)
                    }
                }
                ))
            }
            val confirmDelete = ::gen_confirmDelete_fn
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
                if (actionKey == "details") {
                    uni_navigateTo(NavigateToOptions(url = "/pages/purchases/details/index?purchase=" + id))
                    return
                }
                if (actionKey == "edit") {
                    uni_navigateTo(NavigateToOptions(url = "/pages/purchases/from?id=" + id))
                    return
                }
                if (actionKey == "delete") {
                    confirmDelete(id)
                }
                if (actionKey == "approve") {
                    confirmAction(id, "approve", "审核采购单", "确定审核这张采购单吗？")
                }
                if (actionKey == "complete") {
                    confirmAction(id, "complete", "完成采购单", "确定完成这张采购单吗？")
                }
                if (actionKey == "cancel") {
                    confirmAction(id, "cancel", "取消采购单", "确定取消这张采购单吗？")
                }
            }
            val handleMenu = ::gen_handleMenu_fn
            val listItems = computed(fun(): UTSArray<UTSJSONObject> {
                val result: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < purchases.value.length){
                        result.push(purchaseToListItem(purchases.value[index]))
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
                    return "没有匹配的采购单"
                }
                return "暂无采购单"
            }
            )
            val summaryItems = computed(fun(): UTSArray<UTSJSONObject> {
                return _uA(
                    _uO("key" to "total", "label" to "采购单数", "value" to totalCount.value.toString(10)),
                    _uO("key" to "amount", "label" to "本页金额", "value" to ("¥ " + pageTotalAmount.value)),
                    _uO("key" to "page", "label" to "页码", "value" to (currentPage.value.toString(10) + "/" + totalPages.value.toString(10)))
                )
            }
            )
            onLoad(fun(_options){
                loadPurchases()
            }
            )
            onShow(fun(){
                if (consumeRefresh()) {
                    loadPurchases()
                }
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList", GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalListClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to "采购单", "searchPlaceholder" to "采购单号、供应商、备注", "searchValue" to unref(keyword), "filterVisible" to false, "showBack" to true, "showSearch" to true, "showFilter" to false, "showHome" to true, "homePath" to "/pages/tabbar/settings", "onSearchInput" to handleSearchInput, "onSearchConfirm" to handleSearchConfirm, "onSearchClear" to handleSearchClear), null, 8, _uA(
                        "searchValue"
                    )),
                    _cE("scroll-view", _uM("style" to _nS(_uM("flex" to "1")), "class" to "page-scroll"), _uA(
                        _cE("view", _uM("class" to "page-content"), _uA(
                            if (isTrue(unref(errorMessage) != "" && !unref(isLoading))) {
                                _cE("view", _uM("key" to 0, "class" to "error-card"), _uA(
                                    _cE("text", _uM("class" to "error-title"), "加载失败"),
                                    _cE("text", _uM("class" to "error-desc"), _tD(unref(errorMessage)), 1),
                                    _cE("view", _uM("class" to "retry-btn", "onClick" to loadPurchases), _uA(
                                        _cE("text", _uM("class" to "retry-btn-text"), "重新加载")
                                    ))
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            _cV(_component_lili_UniversalList, _uM("items" to listItems.value, "keyField" to "id", "titleField" to "title", "subtitleField" to "subtitle", "metaField" to "amountText", "tagField" to "tags", "fields" to unref(fieldConfig), "loading" to unref(isLoading), "loadingText" to "正在加载采购单", "keepContentOnLoading" to true, "inlineLoadingText" to "采购单刷新中...", "emptyText" to emptyText.value, "emptyIcon" to "◎", "showMenu" to true, "menuActions" to unref(menuActions), "showChevron" to false, "showPagination" to true, "currentPage" to unref(currentPage), "totalPages" to unref(totalPages), "totalCount" to unref(totalCount), "summaryTitle" to "采购概览", "summaryItems" to summaryItems.value, "showFloatingAdd" to true, "floatingAddText" to "新增", "onItemClick" to handleItemClick, "onMenu" to handleMenu, "onPageChange" to handlePageChange, "onFloatingAdd" to handleCreate), null, 8, _uA(
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-scroll" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-content" to _pS(_uM("paddingTop" to 6, "paddingRight" to 6, "paddingBottom" to 96, "paddingLeft" to 6)), "error-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 18, "paddingRight" to 18, "paddingBottom" to 18, "paddingLeft" to 18, "marginBottom" to 10, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FECACA", "borderRightColor" to "#FECACA", "borderBottomColor" to "#FECACA", "borderLeftColor" to "#FECACA", "alignItems" to "center")), "error-title" to _pS(_uM("fontSize" to 18, "lineHeight" to "24px", "color" to "#B42318", "fontWeight" to "bold")), "error-desc" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#7F1D1D", "marginTop" to 8, "textAlign" to "center")), "retry-btn" to _pS(_uM("marginTop" to 14, "height" to 40, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#0F172A", "paddingLeft" to 18, "paddingRight" to 18, "alignItems" to "center", "justifyContent" to "center")), "retry-btn-text" to _pS(_uM("fontSize" to 14, "color" to "#FFFFFF")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
