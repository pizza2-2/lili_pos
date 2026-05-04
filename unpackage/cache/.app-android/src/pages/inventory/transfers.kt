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
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesInventoryTransfers : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesInventoryTransfers) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesInventoryTransfers
            val _cache = __ins.renderCache
            val keyword = ref("")
            val isLoading = ref(false)
            val errorMessage = ref("")
            val items = ref(_uA<UTSJSONObject>())
            val currentPage = ref(1)
            val totalPages = ref(1)
            val totalCount = ref(0)
            val pageSize = ref(20)
            val fieldConfig = ref(_uA<UTSJSONObject>(_uO("key" to "status_display", "label" to "状态"), _uO("key" to "total_quantity", "label" to "总数"), _uO("key" to "transferred_quantity", "label" to "已调拨")))
            val menuActions = ref(_uA<UTSJSONObject>(_uO("key" to "approve-transfer", "text" to "审核"), _uO("key" to "complete-transfer", "text" to "完成"), _uO("key" to "cancel-transfer", "text" to "取消"), _uO("key" to "reload", "text" to "刷新")))
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
                            , page = currentPage.value, page_size = pageSize.value, status = null, alert_status = null, transaction_type = null)
                            applyListResponse(await(getInventoryTransfers(query)))
                        }
                         catch (error: Throwable) {
                            items.value = _uA<UTSJSONObject>()
                            currentPage.value = 1
                            totalPages.value = 1
                            totalCount.value = 0
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
                return _uO("id" to stringField(item, "id"), "rawId" to stringField(item, "id"), "name" to stringField(item, "transfer_number", "调拨单"), "subtitle" to (stringField(item, "from_location_name", "-") + " -> " + stringField(item, "to_location_name", "-")), "meta" to compactDate(stringField(item, "transfer_date")), "status_display" to stringField(item, "status_display", stringField(item, "status")), "total_quantity" to stringField(item, "total_quantity", "0"), "transferred_quantity" to stringField(item, "transferred_quantity", "0"), "tags" to _uA<String>(stringField(item, "status_display", stringField(item, "status"))))
            }
            val transferItem = ::gen_transferItem_fn
            fun gen_runAction_fn(actionName: String, id: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        try {
                            if (actionName == "approve-transfer") {
                                await(approveInventoryTransfer(id))
                            } else if (actionName == "complete-transfer") {
                                await(completeInventoryTransfer(id))
                            } else if (actionName == "cancel-transfer") {
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
                val actionKey = stringField(action as UTSJSONObject, "key")
                val id = stringField(item as UTSJSONObject, "rawId")
                if (actionKey == "approve-transfer") {
                    confirmRunAction(actionKey, id, "审核调拨", "确定审核通过这张调拨单吗？")
                } else if (actionKey == "complete-transfer") {
                    confirmRunAction(actionKey, id, "完成调拨", "确定完成这张调拨单吗？")
                } else if (actionKey == "cancel-transfer") {
                    confirmRunAction(actionKey, id, "取消调拨", "确定取消这张调拨单吗？")
                } else if (actionKey == "reload") {
                    loadItems()
                }
            }
            val handleMenu = ::gen_handleMenu_fn
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
            val emptyText = computed(fun(): String {
                if (isLoading.value) {
                    return "正在加载"
                }
                if (keyword.value != "") {
                    return "没有匹配的调拨单"
                }
                return "暂无调拨单"
            }
            )
            val summaryItems = computed(fun(): UTSArray<UTSJSONObject> {
                return _uA(
                    _uO("key" to "total", "label" to "调拨单数", "value" to totalCount.value.toString(10)),
                    _uO("key" to "page", "label" to "页码", "value" to (currentPage.value.toString(10) + "/" + totalPages.value.toString(10)))
                )
            }
            )
            onLoad(fun(_options){
                loadItems()
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList", GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalListClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to "调拨单", "searchPlaceholder" to "调拨单号、备注", "searchValue" to unref(keyword), "filterVisible" to false, "showBack" to true, "showSearch" to true, "showFilter" to false, "showHome" to true, "homePath" to "/pages/tabbar/settings", "onSearchInput" to handleSearchInput, "onSearchConfirm" to handleSearchConfirm, "onSearchClear" to handleSearchClear), null, 8, _uA(
                        "searchValue"
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
                            _cV(_component_lili_UniversalList, _uM("items" to listItems.value, "keyField" to "id", "titleField" to "name", "subtitleField" to "subtitle", "metaField" to "meta", "tagField" to "tags", "fields" to unref(fieldConfig), "loading" to unref(isLoading), "loadingText" to "正在加载调拨单", "emptyText" to emptyText.value, "emptyIcon" to "◎", "showMenu" to true, "menuActions" to unref(menuActions), "showPagination" to true, "currentPage" to unref(currentPage), "totalPages" to unref(totalPages), "totalCount" to unref(totalCount), "summaryTitle" to "调拨概览", "summaryItems" to summaryItems.value, "showFloatingAdd" to false, "onMenu" to handleMenu, "onPageChange" to handlePageChange), null, 8, _uA(
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
