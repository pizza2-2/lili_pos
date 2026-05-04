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
            val keyword = ref("")
            val filterVisible = ref(false)
            val isLoading = ref(false)
            val errorMessage = ref("")
            val statusError = ref("")
            val invoices = ref(_uA<KsefInvoiceItem>())
            val status = ref<KsefAutoSyncStatus?>(null)
            val currentPage = ref(1)
            val totalPages = ref(1)
            val totalCount = ref(0)
            val pageSize = ref(20)
            val selectedSyncStatus = ref<String?>(null)
            val fieldConfig = ref(_uA<UTSJSONObject>(_uO("key" to "seller_nip", "label" to "卖方NIP"), _uO("key" to "gross_amount_text", "label" to "金额"), _uO("key" to "sync_status_text", "label" to "详情状态")))
            val menuActions = ref(_uA<UTSJSONObject>(_uO("key" to "download_xml", "text" to "同步详情"), _uO("key" to "copy_ksef", "text" to "复制KSeF号")))
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
                            , page = currentPage.value, page_size = pageSize.value, sync_status = selectedSyncStatus.value, is_paid = null)))
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
                        statusError.value = ""
                        try {
                            status.value = await(getKsefAutoSyncStatus())
                        }
                         catch (error: Throwable) {
                            status.value = null
                            statusError.value = parseErrorMessage(error, "自动同步状态加载失败")
                        }
                })
            }
            val loadAutoSyncStatus = ::gen_loadAutoSyncStatus_fn
            fun gen_handleFilterVisibleChange_fn(value: Boolean) {
                filterVisible.value = value
            }
            val handleFilterVisibleChange = ::gen_handleFilterVisibleChange_fn
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
            fun gen_selectSyncStatus_fn(value: String?) {
                selectedSyncStatus.value = value
            }
            val selectSyncStatus = ::gen_selectSyncStatus_fn
            fun gen_handleFilterReset_fn() {
                selectedSyncStatus.value = null
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
            fun gen_statusText_fn(value: String): String {
                if (value == "XML_DOWNLOADED") {
                    return "已同步详情"
                }
                if (value == "SYNC_ERROR") {
                    return "同步异常"
                }
                return "待同步详情"
            }
            val statusText = ::gen_statusText_fn
            fun gen_statusTag_fn(value: String): String {
                if (value == "XML_DOWNLOADED") {
                    return "已完成"
                }
                if (value == "SYNC_ERROR") {
                    return "异常"
                }
                return "待详情"
            }
            val statusTag = ::gen_statusTag_fn
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
            fun gen_invoiceToListItem_fn(item: KsefInvoiceItem): UTSJSONObject {
                return _uO("id" to item.id.toString(10), "name" to displayText(item.invoice_number), "codeText" to ("KSeF：" + displayText(item.ksef_number)), "metaText" to compactDate(item.issue_date), "seller_name" to displayText(item.seller_name), "seller_nip" to displayText(item.seller_nip), "gross_amount_text" to (displayText(item.gross_amount) + " " + displayText(item.currency)), "sync_status_text" to statusText(item.sync_status), "rawId" to item.id.toString(10), "rawKsefNumber" to item.ksef_number, "tags" to _uA<String>(statusTag(item.sync_status)))
            }
            val invoiceToListItem = ::gen_invoiceToListItem_fn
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
            fun gen_handleEnqueueAutoSync_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        try {
                            await(enqueueKsefAutoSync())
                            uni_showToast(ShowToastOptions(title = takeLatestResponseMessage("已加入队列"), icon = "success"))
                            loadAutoSyncStatus()
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error, "任务加入队列失败"), icon = "none"))
                        }
                })
            }
            val handleEnqueueAutoSync = ::gen_handleEnqueueAutoSync_fn
            fun gen_handleDownloadXml_fn(invoiceId: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        try {
                            await(downloadKsefInvoiceXml(invoiceId))
                            uni_showToast(ShowToastOptions(title = takeLatestResponseMessage("详情同步完成"), icon = "success"))
                            loadInvoices()
                            loadAutoSyncStatus()
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error, "详情同步失败"), icon = "none"))
                        }
                })
            }
            val handleDownloadXml = ::gen_handleDownloadXml_fn
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
                if (key == "download_xml") {
                    handleDownloadXml(invoiceId)
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
                if (key == "seller_nip") {
                    val value = itemObject["seller_nip"]
                    val text = if (value == null) {
                        ""
                    } else {
                        (value as String)
                    }
                    copyText(text, "卖方NIP已复制", "暂无卖方NIP")
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
            val hasActiveFilter = computed(fun(): Boolean {
                return keyword.value != "" || selectedSyncStatus.value != null
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
            val batchLimitText = computed(fun(): String {
                if (status.value == null) {
                    return "-"
                }
                return status.value!!.xml_batch_size.toString(10) + "张/" + status.value!!.xml_delay_seconds.toString(10) + "秒"
            }
            )
            val lastSuccessText = computed(fun(): String {
                if (status.value == null) {
                    return "-"
                }
                return compactDate(status.value!!.last_success_at)
            }
            )
            val autoSyncSubtitle = computed(fun(): String {
                if (status.value == null) {
                    return "读取自动同步配置中"
                }
                if (!status.value!!.enabled) {
                    return "自动同步已关闭"
                }
                return "每 5 小时增量同步，详情分批自动回填"
            }
            )
            val summaryItems = computed(fun(): UTSArray<UTSJSONObject> {
                return _uA(
                    _uO("key" to "total", "label" to "发票总数", "value" to totalCount.value.toString(10)),
                    _uO("key" to "pending", "label" to "待详情", "value" to pendingXmlText.value),
                    _uO("key" to "last", "label" to "最近成功", "value" to lastSuccessText.value)
                )
            }
            )
            onLoad(fun(_options){
                loadAutoSyncStatus()
                loadInvoices()
            }
            )
            onShow(fun(){
                loadAutoSyncStatus()
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList", GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalListClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to "KSeF 发票", "searchPlaceholder" to "发票号、KSeF号、卖方", "searchValue" to unref(keyword), "filterVisible" to unref(filterVisible), "showBack" to true, "showSearch" to true, "showFilter" to true, "showHome" to true, "filterActive" to hasActiveFilter.value, "filterText" to "重置", "homePath" to "/pages/tabbar/settings", "onSearchInput" to handleSearchInput, "onSearchConfirm" to handleSearchConfirm, "onSearchClear" to handleSearchClear, "onUpdate:filterVisible" to handleFilterVisibleChange), _uM("filter-panel" to withSlotCtx(fun(): UTSArray<Any> {
                        return _uA(
                            _cE("view", _uM("class" to "filter-panel"), _uA(
                                _cE("text", _uM("class" to "filter-title"), "同步状态"),
                                _cE("view", _uM("class" to "filter-row"), _uA(
                                    _cE("view", _uM("class" to _nC(if (unref(selectedSyncStatus) == null) {
                                        "filter-chip filter-chip-active"
                                    } else {
                                        "filter-chip"
                                    }
                                    ), "onClick" to fun(){
                                        selectSyncStatus(null)
                                    }
                                    ), _uA(
                                        _cE("text", _uM("class" to _nC(if (unref(selectedSyncStatus) == null) {
                                            "filter-chip-text filter-chip-text-active"
                                        } else {
                                            "filter-chip-text"
                                        }
                                        )), "全部", 2)
                                    ), 10, _uA(
                                        "onClick"
                                    )),
                                    _cE("view", _uM("class" to _nC(if (unref(selectedSyncStatus) == "METADATA_ONLY") {
                                        "filter-chip filter-chip-active"
                                    } else {
                                        "filter-chip"
                                    }
                                    ), "onClick" to fun(){
                                        selectSyncStatus("METADATA_ONLY")
                                    }
                                    ), _uA(
                                        _cE("text", _uM("class" to _nC(if (unref(selectedSyncStatus) == "METADATA_ONLY") {
                                            "filter-chip-text filter-chip-text-active"
                                        } else {
                                            "filter-chip-text"
                                        }
                                        )), "待详情", 2)
                                    ), 10, _uA(
                                        "onClick"
                                    )),
                                    _cE("view", _uM("class" to _nC(if (unref(selectedSyncStatus) == "XML_DOWNLOADED") {
                                        "filter-chip filter-chip-active"
                                    } else {
                                        "filter-chip"
                                    }
                                    ), "onClick" to fun(){
                                        selectSyncStatus("XML_DOWNLOADED")
                                    }
                                    ), _uA(
                                        _cE("text", _uM("class" to _nC(if (unref(selectedSyncStatus) == "XML_DOWNLOADED") {
                                            "filter-chip-text filter-chip-text-active"
                                        } else {
                                            "filter-chip-text"
                                        }
                                        )), "已完成", 2)
                                    ), 10, _uA(
                                        "onClick"
                                    )),
                                    _cE("view", _uM("class" to _nC(if (unref(selectedSyncStatus) == "SYNC_ERROR") {
                                        "filter-chip filter-chip-active"
                                    } else {
                                        "filter-chip"
                                    }
                                    ), "onClick" to fun(){
                                        selectSyncStatus("SYNC_ERROR")
                                    }
                                    ), _uA(
                                        _cE("text", _uM("class" to _nC(if (unref(selectedSyncStatus) == "SYNC_ERROR") {
                                            "filter-chip-text filter-chip-text-active"
                                        } else {
                                            "filter-chip-text"
                                        }
                                        )), "异常", 2)
                                    ), 10, _uA(
                                        "onClick"
                                    ))
                                )),
                                _cE("view", _uM("class" to "filter-actions"), _uA(
                                    _cE("view", _uM("class" to "filter-btn filter-btn-light", "onClick" to handleFilterReset), _uA(
                                        _cE("text", _uM("class" to "filter-btn-light-text"), "重置")
                                    )),
                                    _cE("view", _uM("class" to "filter-btn filter-btn-primary", "onClick" to applyFilter), _uA(
                                        _cE("text", _uM("class" to "filter-btn-primary-text"), "应用")
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
                            _cE("view", _uM("class" to "status-card"), _uA(
                                _cE("view", _uM("class" to "status-head"), _uA(
                                    _cE("view", null, _uA(
                                        _cE("text", _uM("class" to "status-title"), "自动同步"),
                                        _cE("text", _uM("class" to "status-subtitle"), _tD(autoSyncSubtitle.value), 1)
                                    )),
                                    _cE("view", _uM("class" to "sync-btn", "onClick" to handleEnqueueAutoSync), _uA(
                                        _cE("text", _uM("class" to "sync-btn-text"), "立即排队")
                                    ))
                                )),
                                _cE("view", _uM("class" to "status-grid"), _uA(
                                    _cE("view", _uM("class" to "status-cell"), _uA(
                                        _cE("text", _uM("class" to "status-value"), _tD(pendingXmlText.value), 1),
                                        _cE("text", _uM("class" to "status-label"), "待详情")
                                    )),
                                    _cE("view", _uM("class" to "status-cell"), _uA(
                                        _cE("text", _uM("class" to "status-value"), _tD(batchLimitText.value), 1),
                                        _cE("text", _uM("class" to "status-label"), "每批限制")
                                    )),
                                    _cE("view", _uM("class" to "status-cell"), _uA(
                                        _cE("text", _uM("class" to "status-value"), _tD(lastSuccessText.value), 1),
                                        _cE("text", _uM("class" to "status-label"), "最近成功")
                                    ))
                                )),
                                if (unref(statusError) != "") {
                                    _cE("text", _uM("key" to 0, "class" to "status-error"), _tD(unref(statusError)), 1)
                                } else {
                                    _cC("v-if", true)
                                }
                            )),
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
                            _cV(_component_lili_UniversalList, _uM("items" to listItems.value, "keyField" to "id", "titleField" to "name", "subtitleField" to "codeText", "metaField" to "metaText", "tagField" to "tags", "fields" to unref(fieldConfig), "loading" to unref(isLoading), "loadingText" to "正在加载 KSeF 发票", "keepContentOnLoading" to true, "inlineLoadingText" to "KSeF 发票刷新中...", "emptyText" to emptyText.value, "emptyIcon" to "◎", "showMenu" to true, "menuActions" to unref(menuActions), "showChevron" to false, "showPagination" to true, "currentPage" to unref(currentPage), "totalPages" to unref(totalPages), "totalCount" to unref(totalCount), "showFloatingAdd" to false, "summaryTitle" to "KSeF 汇总", "summaryItems" to summaryItems.value, "summaryCollapsedByDefault" to false, "onMenu" to handleMenu, "onPageChange" to handlePageChange, "onSubtitleClick" to handleSubtitleClick, "onFieldClick" to handleFieldClick), null, 8, _uA(
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-scroll" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-content" to _pS(_uM("paddingLeft" to 6, "paddingRight" to 6, "paddingTop" to 6, "paddingBottom" to 96)), "status-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 14, "paddingLeft" to 14, "marginBottom" to 10, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E5EAF1", "borderRightColor" to "#E5EAF1", "borderBottomColor" to "#E5EAF1", "borderLeftColor" to "#E5EAF1")), "status-head" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center")), "status-title" to _pS(_uM("fontSize" to 17, "lineHeight" to "22px", "color" to "#0F172A", "fontWeight" to "bold")), "status-subtitle" to _pS(_uM("fontSize" to 12, "lineHeight" to "18px", "color" to "#64748B", "marginTop" to 2)), "sync-btn" to _pS(_uM("height" to 36, "paddingLeft" to 14, "paddingRight" to 14, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#0F172A", "alignItems" to "center", "justifyContent" to "center")), "sync-btn-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "13px", "color" to "#FFFFFF")), "status-grid" to _pS(_uM("flexDirection" to "row", "marginTop" to 12)), "status-cell" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F8FAFC", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 10, "paddingBottom" to 10, "paddingLeft" to 8, "paddingRight" to 8, "marginRight" to 6)), "status-value" to _pS(_uM("fontSize" to 15, "lineHeight" to "20px", "color" to "#0F172A", "fontWeight" to "bold")), "status-label" to _pS(_uM("fontSize" to 11, "lineHeight" to "16px", "color" to "#64748B", "marginTop" to 2)), "status-error" to _pS(_uM("fontSize" to 12, "lineHeight" to "18px", "color" to "#B42318", "marginTop" to 8)), "filter-panel" to _pS(_uM("paddingBottom" to 8)), "filter-title" to _pS(_uM("fontSize" to 14, "lineHeight" to "18px", "color" to "#0F172A", "fontWeight" to "bold")), "filter-row" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 10)), "filter-chip" to _pS(_uM("height" to 34, "paddingLeft" to 12, "paddingRight" to 12, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#F8FAFC", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "alignItems" to "center", "justifyContent" to "center", "marginRight" to 8, "marginBottom" to 8)), "filter-chip-active" to _pS(_uM("backgroundColor" to "#0F172A", "borderTopColor" to "#0F172A", "borderRightColor" to "#0F172A", "borderBottomColor" to "#0F172A", "borderLeftColor" to "#0F172A")), "filter-chip-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "13px", "color" to "#475569")), "filter-chip-text-active" to _pS(_uM("color" to "#FFFFFF")), "filter-actions" to _pS(_uM("flexDirection" to "row", "marginTop" to 8)), "filter-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 40, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "alignItems" to "center", "justifyContent" to "center")), "filter-btn-light" to _pS(_uM("backgroundColor" to "#F3F6FA", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "marginRight" to 8)), "filter-btn-primary" to _pS(_uM("backgroundColor" to "#0F172A")), "filter-btn-light-text" to _pS(_uM("fontSize" to 14, "lineHeight" to "14px", "color" to "#475569")), "filter-btn-primary-text" to _pS(_uM("fontSize" to 14, "lineHeight" to "14px", "color" to "#FFFFFF")), "error-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 18, "paddingRight" to 18, "paddingBottom" to 18, "paddingLeft" to 18, "marginBottom" to 10, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FECACA", "borderRightColor" to "#FECACA", "borderBottomColor" to "#FECACA", "borderLeftColor" to "#FECACA", "alignItems" to "center")), "error-title" to _pS(_uM("fontSize" to 18, "lineHeight" to "24px", "color" to "#B42318", "fontWeight" to "bold")), "error-desc" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#7F1D1D", "marginTop" to 8, "textAlign" to "center")), "retry-btn" to _pS(_uM("marginTop" to 14, "height" to 40, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#0F172A", "paddingLeft" to 18, "paddingRight" to 18, "alignItems" to "center", "justifyContent" to "center")), "retry-btn-text" to _pS(_uM("fontSize" to 14, "color" to "#FFFFFF")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
