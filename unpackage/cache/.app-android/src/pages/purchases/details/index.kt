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
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesPurchasesDetailsIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesPurchasesDetailsIndex) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesPurchasesDetailsIndex
            val _cache = __ins.renderCache
            val refreshStorageKey = "refresh:pages:purchases:details:index"
            val purchaseListRefreshStorageKey = "refresh:pages:purchases:index"
            val purchaseId = ref("")
            val purchaseInfo = ref<PurchaseItem?>(null)
            val keyword = ref("")
            val details = ref(_uA<PurchaseDetailItem>())
            val isLoading = ref(false)
            val errorMessage = ref("")
            val currentPage = ref(1)
            val totalPages = ref(1)
            val totalCount = ref(0)
            val pageSize = ref(20)
            val pageTotalAmount = ref("0.00")
            val fieldConfig = ref(_uA<UTSJSONObject>(_uO("key" to "skuText", "label" to "SKU:"), _uO("key" to "quantityText", "label" to "数量:"), _uO("key" to "progressText", "label" to "收货:"), _uO("key" to "notesText", "label" to "备注:")))
            val menuActions = ref(_uA<UTSJSONObject>(_uO("key" to "edit", "text" to "编辑"), _uO("key" to "receive", "text" to "收货"), _uO("key" to "delete", "text" to "删除")))
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
                        val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/purchases/details/index.uvue:111")
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
            fun gen_applyResponse_fn(response: PurchaseDetailListResponse) {
                details.value = response.results
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
            val applyResponse = ::gen_applyResponse_fn
            fun gen_loadPurchaseInfo_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (purchaseId.value == "") {
                            return@w1
                        }
                        try {
                            purchaseInfo.value = await(getPurchaseDetail(purchaseId.value))
                        }
                         catch (error: Throwable) {}
                })
            }
            val loadPurchaseInfo = ::gen_loadPurchaseInfo_fn
            fun gen_loadDetails_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (isLoading.value) {
                            return@w1
                        }
                        if (purchaseId.value == "") {
                            errorMessage.value = "缺少采购单 ID"
                            return@w1
                        }
                        isLoading.value = true
                        errorMessage.value = ""
                        try {
                            val response = await(getPurchaseDetailList(PurchaseDetailListQuery(search = if (keyword.value == "") {
                                null
                            } else {
                                keyword.value
                            }
                            , page = currentPage.value, page_size = pageSize.value, purchase = purchaseId.value, product = null, is_fully_received = null)))
                            applyResponse(response)
                            await(loadPurchaseInfo())
                        }
                         catch (error: Throwable) {
                            details.value = _uA()
                            currentPage.value = 1
                            totalPages.value = 1
                            totalCount.value = 0
                            pageTotalAmount.value = "0.00"
                            errorMessage.value = parseErrorMessage(error, "采购明细加载失败")
                        }
                         finally {
                            isLoading.value = false
                        }
                })
            }
            val loadDetails = ::gen_loadDetails_fn
            fun gen_markRefreshNeeded_fn() {
                uni_setStorageSync(refreshStorageKey + ":" + purchaseId.value, "1")
            }
            val markRefreshNeeded = ::gen_markRefreshNeeded_fn
            fun gen_markPurchaseListRefreshNeeded_fn() {
                uni_setStorageSync(purchaseListRefreshStorageKey, "1")
            }
            val markPurchaseListRefreshNeeded = ::gen_markPurchaseListRefreshNeeded_fn
            fun gen_consumeRefreshNeeded_fn(): Boolean {
                val storedValue = uni_getStorageSync(refreshStorageKey + ":" + purchaseId.value)
                if (storedValue == null || ("" + storedValue) == "") {
                    return false
                }
                uni_removeStorageSync(refreshStorageKey + ":" + purchaseId.value)
                return true
            }
            val consumeRefreshNeeded = ::gen_consumeRefreshNeeded_fn
            fun gen_detailToListItem_fn(item: PurchaseDetailItem): UTSJSONObject {
                val statusText = if (item.is_fully_received) {
                    "已收齐"
                } else {
                    "待收货"
                }
                return _uO("id" to item.id.toString(10), "rawId" to item.id.toString(10), "title" to stringValue(item.product_name, "商品 #" + item.product.toString(10)), "subtitle" to ("条码：" + stringValue(item.product_barcode, "-")), "amountText" to ("¥ " + stringValue(item.amount, "0.00")), "skuText" to stringValue(item.product_sku, "-"), "quantityText" to (item.received_quantity.toString(10) + "/" + item.quantity.toString(10) + "，剩余 " + item.remaining_quantity.toString(10)), "progressText" to (stringValue(item.receive_progress, "0") + "%"), "notesText" to stringValue(item.notes, "-"), "tags" to _uA<String>(statusText, "单价 ¥ " + stringValue(item.unit_price, "0.00")))
            }
            val detailToListItem = ::gen_detailToListItem_fn
            fun gen_handleSearchInput_fn(value: String) {
                keyword.value = value
            }
            val handleSearchInput = ::gen_handleSearchInput_fn
            fun gen_handleSearchConfirm_fn(value: String) {
                keyword.value = value
                currentPage.value = 1
                loadDetails()
            }
            val handleSearchConfirm = ::gen_handleSearchConfirm_fn
            fun gen_handleSearchClear_fn() {
                keyword.value = ""
                currentPage.value = 1
                loadDetails()
            }
            val handleSearchClear = ::gen_handleSearchClear_fn
            fun gen_handleCreate_fn(payload: UTSJSONObject) {
                uni_navigateTo(NavigateToOptions(url = "/pages/purchases/details/from?purchase=" + purchaseId.value))
            }
            val handleCreate = ::gen_handleCreate_fn
            fun gen_handleItemClick_fn(payload: UTSJSONObject) {
                val id = stringValue(payload["rawId"], stringValue(payload["id"]))
                if (id != "") {
                    uni_navigateTo(NavigateToOptions(url = "/pages/purchases/details/from?purchase=" + purchaseId.value + "&id=" + id))
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
                loadDetails()
            }
            val handlePageChange = ::gen_handlePageChange_fn
            fun gen_runDelete_fn(id: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        try {
                            await(deletePurchaseDetail(id))
                            markPurchaseListRefreshNeeded()
                            uni_showToast(ShowToastOptions(title = takeLatestResponseMessage("删除成功"), icon = "success"))
                            loadDetails()
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error, "删除失败"), icon = "none"))
                        }
                })
            }
            val runDelete = ::gen_runDelete_fn
            fun gen_confirmDelete_fn(id: String) {
                uni_showModal(ShowModalOptions(title = "删除明细", content = "确定删除这条采购明细吗？", success = fun(res){
                    if (res.confirm) {
                        runDelete(id)
                    }
                }
                ))
            }
            val confirmDelete = ::gen_confirmDelete_fn
            fun gen_runReceive_fn(id: String, quantity: Number): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        try {
                            await(receivePurchaseDetail(id, quantity, "前端收货"))
                            markPurchaseListRefreshNeeded()
                            uni_showToast(ShowToastOptions(title = takeLatestResponseMessage("收货成功"), icon = "success"))
                            loadDetails()
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error, "收货失败"), icon = "none"))
                        }
                })
            }
            val runReceive = ::gen_runReceive_fn
            fun gen_promptReceive_fn(item: UTSJSONObject) {
                val id = stringValue(item["rawId"])
                val defaultValue = stringValue(item["remainingValue"], "1")
                uni_showModal(ShowModalOptions(title = "采购收货", editable = true, placeholderText = "请输入本次数量", content = defaultValue, success = fun(res){
                    if (!res.confirm) {
                        return
                    }
                    val inputText = if (res.content == null) {
                        defaultValue
                    } else {
                        ("" + res.content!!)
                    }
                    val quantity = parseInt(inputText)
                    if (isNaN(quantity) || quantity <= 0) {
                        uni_showToast(ShowToastOptions(title = "请输入有效收货数量", icon = "none"))
                        return
                    }
                    runReceive(id, quantity)
                }
                ))
            }
            val promptReceive = ::gen_promptReceive_fn
            fun gen_handleMenu_fn(payload: UTSJSONObject) {
                val action = payload["action"]
                val item = payload["item"]
                if (action == null || item == null) {
                    return
                }
                val actionKey = stringValue((action as UTSJSONObject)["key"])
                val itemObject = item as UTSJSONObject
                val id = stringValue(itemObject["rawId"])
                if (id == "") {
                    return
                }
                if (actionKey == "edit") {
                    uni_navigateTo(NavigateToOptions(url = "/pages/purchases/details/from?purchase=" + purchaseId.value + "&id=" + id))
                    return
                }
                if (actionKey == "receive") {
                    promptReceive(itemObject)
                }
                if (actionKey == "delete") {
                    confirmDelete(id)
                }
            }
            val handleMenu = ::gen_handleMenu_fn
            val pageTitle = computed(fun(): String {
                if (purchaseInfo.value == null) {
                    return "采购单明细"
                }
                val info = purchaseInfo.value as PurchaseItem
                return "明细 " + info.purchase_number
            }
            )
            val listItems = computed(fun(): UTSArray<UTSJSONObject> {
                val result: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < details.value.length){
                        val row = detailToListItem(details.value[index])
                        row["remainingValue"] = details.value[index].remaining_quantity.toString(10)
                        result.push(row)
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
                    return "没有匹配的采购明细"
                }
                return "暂无采购明细"
            }
            )
            val summaryItems = computed(fun(): UTSArray<UTSJSONObject> {
                return _uA(
                    _uO("key" to "total", "label" to "明细数", "value" to totalCount.value.toString(10)),
                    _uO("key" to "amount", "label" to "本页金额", "value" to ("¥ " + pageTotalAmount.value)),
                    _uO("key" to "page", "label" to "页码", "value" to (currentPage.value.toString(10) + "/" + totalPages.value.toString(10)))
                )
            }
            )
            onLoad(fun(query: OnLoadOptions){
                val purchaseValue = query["purchase"]
                purchaseId.value = if (purchaseValue == null) {
                    ""
                } else {
                    ("" + purchaseValue)
                }
                loadDetails()
            }
            )
            onShow(fun(){
                if (consumeRefreshNeeded()) {
                    loadDetails()
                }
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList", GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalListClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to pageTitle.value, "searchPlaceholder" to "商品名、SKU、条码", "searchValue" to unref(keyword), "filterVisible" to false, "showBack" to true, "showSearch" to true, "showFilter" to false, "showHome" to true, "homePath" to "/pages/purchases/index", "onSearchInput" to handleSearchInput, "onSearchConfirm" to handleSearchConfirm, "onSearchClear" to handleSearchClear), null, 8, _uA(
                        "title",
                        "searchValue"
                    )),
                    _cE("scroll-view", _uM("style" to _nS(_uM("flex" to "1")), "class" to "page-scroll"), _uA(
                        _cE("view", _uM("class" to "page-content"), _uA(
                            if (isTrue(unref(errorMessage) != "" && !unref(isLoading))) {
                                _cE("view", _uM("key" to 0, "class" to "error-card"), _uA(
                                    _cE("text", _uM("class" to "error-title"), "加载失败"),
                                    _cE("text", _uM("class" to "error-desc"), _tD(unref(errorMessage)), 1),
                                    _cE("view", _uM("class" to "retry-btn", "onClick" to loadDetails), _uA(
                                        _cE("text", _uM("class" to "retry-btn-text"), "重新加载")
                                    ))
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            _cV(_component_lili_UniversalList, _uM("items" to listItems.value, "keyField" to "id", "titleField" to "title", "subtitleField" to "subtitle", "metaField" to "amountText", "tagField" to "tags", "fields" to unref(fieldConfig), "loading" to unref(isLoading), "loadingText" to "正在加载采购明细", "keepContentOnLoading" to true, "inlineLoadingText" to "采购明细刷新中...", "emptyText" to emptyText.value, "emptyIcon" to "◎", "showMenu" to true, "menuActions" to unref(menuActions), "showChevron" to false, "showPagination" to true, "currentPage" to unref(currentPage), "totalPages" to unref(totalPages), "totalCount" to unref(totalCount), "summaryTitle" to "明细概览", "summaryItems" to summaryItems.value, "showFloatingAdd" to true, "floatingAddText" to "新增明细", "onItemClick" to handleItemClick, "onMenu" to handleMenu, "onPageChange" to handlePageChange, "onFloatingAdd" to handleCreate), null, 8, _uA(
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
