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
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesProductsPricingFormulaIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesProductsPricingFormulaIndex) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesProductsPricingFormulaIndex
            val _cache = __ins.renderCache
            val pricingFormulaListRefreshStorageKey = "refresh:pages:products:pricing-formula:index"
            val keyword = ref("")
            val isLoading = ref(false)
            val errorMessage = ref("")
            val formulas = ref(_uA<ProductPricingFormulaItem>())
            val currentPage = ref(1)
            val totalPages = ref(1)
            val totalCount = ref(0)
            val pageSize = ref(20)
            val fieldConfig = ref(_uA<UTSJSONObject>(_uO("key" to "expression", "label" to "表达式"), _uO("key" to "descriptionText", "label" to "说明"), _uO("key" to "updatedText", "label" to "更新")))
            val menuActions = ref(_uA<UTSJSONObject>(_uO("key" to "edit", "text" to "编辑"), _uO("key" to "copy-expression", "text" to "复制表达式"), _uO("key" to "reload", "text" to "刷新")))
            fun gen_parseErrorMessage_fn(error: Any, fallback: String): String {
                var message = fallback
                if (error != null) {
                    val directMessage = (error as UTSError).message
                    if (directMessage != null && directMessage != "") {
                        message = directMessage
                    }
                    val errorText = JSON.stringify(error)
                    if (errorText != null && errorText != "") {
                        val parsedError = JSON.parseObject<UTSJSONObject>(errorText)
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
            fun gen_applyFormulaResponse_fn(response: ProductPricingFormulaListResponse) {
                formulas.value = response.results
                currentPage.value = response.current_page
                totalPages.value = response.total_pages
                totalCount.value = response.total_count
                pageSize.value = response.page_size
            }
            val applyFormulaResponse = ::gen_applyFormulaResponse_fn
            fun gen_loadPricingFormulas_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (isLoading.value) {
                            return@w1
                        }
                        isLoading.value = true
                        errorMessage.value = ""
                        try {
                            val response = await(getProductPricingFormulaList(ProductPricingFormulaListQuery(search = if (keyword.value == "") {
                                null
                            } else {
                                keyword.value
                            }
                            , page = currentPage.value, page_size = pageSize.value)))
                            applyFormulaResponse(response)
                        }
                         catch (error: Throwable) {
                            formulas.value = _uA<ProductPricingFormulaItem>()
                            currentPage.value = 1
                            totalPages.value = 1
                            totalCount.value = 0
                            errorMessage.value = parseErrorMessage(error, "价格公式列表加载失败")
                        }
                         finally {
                            isLoading.value = false
                        }
                })
            }
            val loadPricingFormulas = ::gen_loadPricingFormulas_fn
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
            fun gen_formulaToListItem_fn(item: ProductPricingFormulaItem): UTSJSONObject {
                return _uO("id" to item.id.toString(10), "rawId" to item.id.toString(10), "name" to item.name, "codeText" to ("编码：" + stringValue(item.code, "-")), "statusText" to if (item.is_active) {
                    "启用"
                } else {
                    "停用"
                }
                , "expression" to stringValue(item.expression, "-"), "descriptionText" to stringValue(item.description, "-"), "updatedText" to formatDateText(item.updated_at), "tags" to if (item.is_active) {
                    _uA(
                        "启用"
                    )
                } else {
                    _uA(
                        "停用"
                    )
                }
                )
            }
            val formulaToListItem = ::gen_formulaToListItem_fn
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
            fun gen_handleSearchInput_fn(value: String) {
                keyword.value = value
            }
            val handleSearchInput = ::gen_handleSearchInput_fn
            fun gen_handleSearchConfirm_fn(value: String) {
                keyword.value = value
                currentPage.value = 1
                loadPricingFormulas()
            }
            val handleSearchConfirm = ::gen_handleSearchConfirm_fn
            fun gen_handleSearchClear_fn() {
                keyword.value = ""
                currentPage.value = 1
                loadPricingFormulas()
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
                loadPricingFormulas()
            }
            val handlePageChange = ::gen_handlePageChange_fn
            fun gen_openFormulaForm_fn(id: String) {
                if (id == "") {
                    uni_navigateTo(NavigateToOptions(url = "/pages/products/pricing-formula/from"))
                    return
                }
                uni_navigateTo(NavigateToOptions(url = "/pages/products/pricing-formula/from?id=" + id))
            }
            val openFormulaForm = ::gen_openFormulaForm_fn
            fun gen_handleItemClick_fn(payload: UTSJSONObject) {
                val item = payload["item"]
                if (item == null) {
                    return
                }
                val itemObject = item as UTSJSONObject
                openFormulaForm(stringValue(itemObject["rawId"]))
            }
            val handleItemClick = ::gen_handleItemClick_fn
            fun gen_handleFieldClick_fn(payload: UTSJSONObject) {
                val keyValue = payload["key"]
                val itemValue = payload["item"]
                if (keyValue == null || itemValue == null) {
                    return
                }
                val key = keyValue as String
                val item = itemValue as UTSJSONObject
                if (key == "expression") {
                    copyText(stringValue(item["expression"]), "表达式已复制", "暂无表达式")
                }
            }
            val handleFieldClick = ::gen_handleFieldClick_fn
            fun gen_handleMenu_fn(payload: UTSJSONObject) {
                val action = payload["action"]
                val item = payload["item"]
                if (action == null || item == null) {
                    return
                }
                val actionObject = action as UTSJSONObject
                val itemObject = item as UTSJSONObject
                val key = stringValue(actionObject["key"])
                val formulaId = stringValue(itemObject["rawId"])
                if (key == "edit") {
                    openFormulaForm(formulaId)
                    return
                }
                if (key == "copy-expression") {
                    copyText(stringValue(itemObject["expression"]), "表达式已复制", "暂无表达式")
                    return
                }
                if (key == "reload") {
                    loadPricingFormulas()
                }
            }
            val handleMenu = ::gen_handleMenu_fn
            fun gen_handleCreateFormula_fn() {
                openFormulaForm("")
            }
            val handleCreateFormula = ::gen_handleCreateFormula_fn
            fun gen_consumePricingFormulaListRefreshNeeded_fn(): Boolean {
                val storedValue = uni_getStorageSync(pricingFormulaListRefreshStorageKey)
                if (storedValue == null) {
                    return false
                }
                val storedText = "" + storedValue
                if (storedText == "") {
                    return false
                }
                uni_removeStorageSync(pricingFormulaListRefreshStorageKey)
                return true
            }
            val consumePricingFormulaListRefreshNeeded = ::gen_consumePricingFormulaListRefreshNeeded_fn
            val listItems = computed(fun(): UTSArray<UTSJSONObject> {
                val result: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < formulas.value.length){
                        result.push(formulaToListItem(formulas.value[index]))
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
                    return "没有匹配的价格公式"
                }
                return "暂无价格公式"
            }
            )
            val summaryItems = computed(fun(): UTSArray<UTSJSONObject> {
                var activeCount: Number = 0
                run {
                    var index: Number = 0
                    while(index < formulas.value.length){
                        if (formulas.value[index].is_active) {
                            activeCount += 1
                        }
                        index += 1
                    }
                }
                return _uA(
                    _uO("key" to "total", "label" to "公式总数", "value" to totalCount.value.toString(10)),
                    _uO("key" to "active", "label" to "当前页启用", "value" to activeCount.toString(10))
                )
            }
            )
            onLoad(fun(_options){
                loadPricingFormulas()
            }
            )
            onShow(fun(){
                if (consumePricingFormulaListRefreshNeeded()) {
                    loadPricingFormulas()
                }
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList", GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalListClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to "价格计算公式", "searchPlaceholder" to "输入公式名称、编码、表达式", "searchValue" to unref(keyword), "showBack" to true, "showSearch" to true, "showHome" to true, "homePath" to "/pages/tabbar/settings", "onSearchInput" to handleSearchInput, "onSearchConfirm" to handleSearchConfirm, "onSearchClear" to handleSearchClear), null, 8, _uA(
                        "searchValue"
                    )),
                    _cE("scroll-view", _uM("class" to "page-scroll", "scroll-y" to "true"), _uA(
                        _cE("view", _uM("class" to "page-content"), _uA(
                            if (isTrue(unref(errorMessage) != "" && !unref(isLoading))) {
                                _cE("view", _uM("key" to 0, "class" to "error-card"), _uA(
                                    _cE("text", _uM("class" to "error-title"), "加载失败"),
                                    _cE("text", _uM("class" to "error-desc"), _tD(unref(errorMessage)), 1),
                                    _cE("view", _uM("class" to "retry-btn", "onClick" to loadPricingFormulas), _uA(
                                        _cE("text", _uM("class" to "retry-btn-text"), "重新加载")
                                    ))
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            _cV(_component_lili_UniversalList, _uM("items" to listItems.value, "keyField" to "id", "titleField" to "name", "subtitleField" to "codeText", "metaField" to "statusText", "tagField" to "tags", "fields" to unref(fieldConfig), "loading" to unref(isLoading), "loadingText" to "正在加载价格公式", "keepContentOnLoading" to true, "inlineLoadingText" to "价格公式刷新中...", "emptyText" to emptyText.value, "emptyIcon" to "◎", "showMenu" to true, "menuActions" to unref(menuActions), "showChevron" to false, "showPagination" to true, "currentPage" to unref(currentPage), "totalPages" to unref(totalPages), "totalCount" to unref(totalCount), "summaryTitle" to "价格公式统计", "summaryItems" to summaryItems.value, "summaryCollapsedByDefault" to true, "showFloatingAdd" to true, "floatingAddText" to "新增", "onItemClick" to handleItemClick, "onFieldClick" to handleFieldClick, "onMenu" to handleMenu, "onPageChange" to handlePageChange, "onFloatingAdd" to handleCreateFormula), null, 8, _uA(
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
                    ))
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-scroll" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-content" to _pS(_uM("paddingLeft" to 6, "paddingRight" to 6, "paddingTop" to 6, "paddingBottom" to 96)), "error-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingLeft" to 18, "paddingRight" to 18, "paddingTop" to 18, "paddingBottom" to 18, "marginBottom" to 14, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FECACA", "borderRightColor" to "#FECACA", "borderBottomColor" to "#FECACA", "borderLeftColor" to "#FECACA", "alignItems" to "center")), "error-title" to _pS(_uM("fontSize" to 18, "lineHeight" to "24px", "color" to "#B42318", "fontWeight" to "bold")), "error-desc" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#7F1D1D", "marginTop" to 8, "textAlign" to "center")), "retry-btn" to _pS(_uM("marginTop" to 14, "height" to 40, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#0F172A", "paddingLeft" to 18, "paddingRight" to 18, "alignItems" to "center", "justifyContent" to "center")), "retry-btn-text" to _pS(_uM("fontSize" to 14, "lineHeight" to "14px", "color" to "#FFFFFF")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
