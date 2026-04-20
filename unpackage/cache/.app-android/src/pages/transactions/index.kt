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
open class GenPagesTransactionsIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesTransactionsIndex) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesTransactionsIndex
            val _cache = __ins.renderCache
            val supplierId = ref("")
            val keyword = ref("")
            val transactions = ref(_uA<TransactionItem>())
            val isLoading = ref(false)
            val errorMessage = ref("")
            val currentPage = ref(1)
            val totalPages = ref(1)
            val totalCount = ref(0)
            val pageSize = ref(20)
            val summary = ref<TransactionSummary?>(null)
            val fieldConfig = ref(_uA<UTSJSONObject>(_uO("key" to "transactionTypeText", "label" to "类型:"), _uO("key" to "noteText", "label" to "备注:")))
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
                    val errorText = JSON.stringify(error)
                    if (errorText != null && errorText != "") {
                        val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/transactions/index.uvue:122")
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
                return message
            }
            val parseErrorMessage = ::gen_parseErrorMessage_fn
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
                            , page = currentPage.value, page_size = pageSize.value, transaction_type = null, supplier = null, supplier_id = supplierId.value, date_from = null, start_date = null, date_to = null, end_date = null, amount_min = null, amount_max = null, ordering = null, sort_by = null)))
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
            fun gen_getDisplayText_fn(value: String?): String {
                if (value == null || value == "") {
                    return "-"
                }
                return value
            }
            val getDisplayText = ::gen_getDisplayText_fn
            fun gen_buildImages_fn(item: TransactionItem): UTSArray<String> {
                val result: UTSArray<String> = _uA()
                run {
                    var index: Number = 0
                    while(index < item.media_files.length){
                        val mediaFile = item.media_files[index]
                        if (mediaFile.signed_thumbnail_url != "") {
                            result.push(mediaFile.signed_thumbnail_url)
                            index += 1
                            continue
                        }
                        if (mediaFile.thumbnail_url != "") {
                            result.push(mediaFile.thumbnail_url)
                            index += 1
                            continue
                        }
                        if (mediaFile.signed_url != "") {
                            result.push(mediaFile.signed_url)
                            index += 1
                            continue
                        }
                        if (mediaFile.file_url != "") {
                            result.push(mediaFile.file_url)
                        }
                        index += 1
                    }
                }
                return result
            }
            val buildImages = ::gen_buildImages_fn
            fun gen_getTransactionImage_fn(item: TransactionItem): String {
                if (item.media_files.length == 0) {
                    return ""
                }
                val mediaFile = item.media_files[0]
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
            val getTransactionImage = ::gen_getTransactionImage_fn
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
                val cover = getTransactionImage(item)
                val images = buildImages(item)
                val title = if (item.transaction_number != "") {
                    item.transaction_number
                } else {
                    ("采购记录 #" + item.id.toString(10))
                }
                return _uO("id" to item.id.toString(10), "title" to title, "transactionDateText" to ("日期：" + formatDateText(item.transaction_date)), "amountText" to ("金额：" + item.amount), "transactionTypeText" to getDisplayText(item.transaction_type_display), "filesText" to (item.files_count.toString(10) + " 个"), "noteText" to getDisplayText(item.note), "updatedText" to formatDateText(item.updated_at), "cover" to cover, "images" to images, "rawId" to item.id.toString(10))
            }
            val transactionToListItem = ::gen_transactionToListItem_fn
            fun gen_handleSearchInput_fn(value: String) {
                keyword.value = value
            }
            val handleSearchInput = ::gen_handleSearchInput_fn
            fun gen_handleSearchConfirm_fn(value: String) {
                keyword.value = value
                currentPage.value = 1
                loadTransactions()
            }
            val handleSearchConfirm = ::gen_handleSearchConfirm_fn
            fun gen_handleSearchClear_fn() {
                keyword.value = ""
                currentPage.value = 1
                loadTransactions()
            }
            val handleSearchClear = ::gen_handleSearchClear_fn
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
                loadTransactions()
            }
            val handlePageChange = ::gen_handlePageChange_fn
            fun gen_handleItemClick_fn(payload: UTSJSONObject) {
                val titleValue = payload["title"]
                val titleText = if (titleValue == null) {
                    "采购记录"
                } else {
                    (titleValue as String)
                }
                uni_showToast(ShowToastOptions(title = titleText, icon = "none"))
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
            val emptyText = computed(fun(): String {
                if (supplierId.value == "") {
                    return "缺少供应商ID"
                }
                if (keyword.value != "") {
                    return "没有匹配的采购记录"
                }
                return "暂无采购记录"
            }
            )
            val transactionCountText = computed(fun(): String {
                return totalCount.value.toString(10)
            }
            )
            val supplierIdText = computed(fun(): String {
                return if (supplierId.value == "") {
                    "-"
                } else {
                    supplierId.value
                }
            }
            )
            val purchaseAmountText = computed(fun(): String {
                if (summary.value == null || summary.value!!.purchase_amount == "") {
                    return "0.00"
                }
                return summary.value!!.purchase_amount
            }
            )
            val arrearsAmountText = computed(fun(): String {
                if (summary.value == null || summary.value!!.arrears_amount == "") {
                    return "0.00"
                }
                return summary.value!!.arrears_amount
            }
            )
            onLoad(fun(event: OnLoadOptions){
                val supplierIdValue = event["supplier_id"]
                supplierId.value = if (supplierIdValue == null) {
                    ""
                } else {
                    (supplierIdValue as String)
                }
                loadTransactions()
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList", GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalListClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to "采购详情", "searchPlaceholder" to "输入单号、备注搜索采购记录", "searchValue" to unref(keyword), "showBack" to true, "showSearch" to true, "showFilter" to false, "showHome" to true, "homePath" to "/pages/suppliers/index", "onSearchInput" to handleSearchInput, "onSearchConfirm" to handleSearchConfirm, "onSearchClear" to handleSearchClear), null, 8, _uA(
                        "searchValue"
                    )),
                    _cE("scroll-view", _uM("style" to _nS(_uM("flex" to "1")), "class" to "page-scroll"), _uA(
                        _cE("view", _uM("class" to "page-content"), _uA(
                            _cE("view", _uM("class" to "summary-row"), _uA(
                                _cE("view", _uM("class" to "summary-card"), _uA(
                                    _cE("text", _uM("class" to "summary-label"), "记录数"),
                                    _cE("text", _uM("class" to "summary-value"), _tD(transactionCountText.value), 1)
                                )),
                                _cE("view", _uM("class" to "summary-gap")),
                                _cE("view", _uM("class" to "summary-card"), _uA(
                                    _cE("text", _uM("class" to "summary-label"), "采购金额"),
                                    _cE("text", _uM("class" to "summary-value"), _tD(purchaseAmountText.value), 1)
                                ))
                            )),
                            _cE("view", _uM("class" to "summary-row"), _uA(
                                _cE("view", _uM("class" to "summary-card"), _uA(
                                    _cE("text", _uM("class" to "summary-label"), "待付金额"),
                                    _cE("text", _uM("class" to "summary-value"), _tD(arrearsAmountText.value), 1)
                                )),
                                _cE("view", _uM("class" to "summary-gap")),
                                _cE("view", _uM("class" to "summary-card"), _uA(
                                    _cE("text", _uM("class" to "summary-label"), "供应商ID"),
                                    _cE("text", _uM("class" to "summary-value"), _tD(supplierIdText.value), 1)
                                ))
                            )),
                            if (isTrue(unref(errorMessage) != "" && !unref(isLoading))) {
                                _cE("view", _uM("key" to 0, "class" to "error-card"), _uA(
                                    _cE("text", _uM("class" to "error-title"), "加载失败"),
                                    _cE("text", _uM("class" to "error-desc"), _tD(unref(errorMessage)), 1),
                                    _cE("button", _uM("class" to "retry-btn", "onClick" to loadTransactions), _uA(
                                        _cE("text", _uM("class" to "retry-btn-text"), "重新加载")
                                    ))
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            _cV(_component_lili_UniversalList, _uM("items" to listItems.value, "keyField" to "id", "titleField" to "title", "subtitleField" to "transactionDateText", "metaField" to "amountText", "imageField" to "cover", "imageListField" to "images", "fields" to unref(fieldConfig), "loading" to unref(isLoading), "loadingText" to "正在加载采购记录", "emptyText" to emptyText.value, "emptyIcon" to "◎", "showMenu" to false, "showChevron" to false, "showPagination" to true, "currentPage" to unref(currentPage), "totalPages" to unref(totalPages), "totalCount" to unref(totalCount), "showFloatingAdd" to true, "floatingAddText" to "新增", "onItemClick" to handleItemClick, "onSubtitleClick" to handleSubtitleClick, "onMetaClick" to handleMetaClick, "onFieldClick" to handleFieldClick, "onPageChange" to handlePageChange, "onFloatingAdd" to handleCreateTransaction), null, 8, _uA(
                                "items",
                                "fields",
                                "loading",
                                "emptyText",
                                "currentPage",
                                "totalPages",
                                "totalCount"
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "position" to "relative", "backgroundColor" to "#EEF2F7")), "page-scroll" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "page-content" to _pS(_uM("paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 88, "paddingLeft" to 12)), "summary-row" to _pS(_uM("flexDirection" to "row", "marginBottom" to 12)), "summary-card" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 14, "paddingLeft" to 14, "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "backgroundColor" to "#FFFFFF")), "summary-gap" to _pS(_uM("width" to 10)), "summary-label" to _pS(_uM("fontSize" to 13, "color" to "#6B7280")), "summary-value" to _pS(_uM("marginTop" to 8, "fontSize" to 18, "fontWeight" to "600", "color" to "#111827")), "error-card" to _pS(_uM("marginBottom" to 12, "paddingTop" to 18, "paddingRight" to 16, "paddingBottom" to 18, "paddingLeft" to 16, "borderTopLeftRadius" to 16, "borderTopRightRadius" to 16, "borderBottomRightRadius" to 16, "borderBottomLeftRadius" to 16, "backgroundColor" to "#FFFFFF")), "error-title" to _pS(_uM("fontSize" to 16, "fontWeight" to "600", "color" to "#111827")), "error-desc" to _pS(_uM("marginTop" to 8, "fontSize" to 14, "lineHeight" to "1.5em", "color" to "#6B7280")), "retry-btn" to _pS(_uM("marginTop" to 14, "height" to 40, "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "backgroundColor" to "#111827", "borderTopWidth" to 0, "borderRightWidth" to 0, "borderBottomWidth" to 0, "borderLeftWidth" to 0)), "retry-btn-text" to _pS(_uM("fontSize" to 14, "fontWeight" to "600", "color" to "#FFFFFF")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
