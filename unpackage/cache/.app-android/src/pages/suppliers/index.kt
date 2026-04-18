@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME", "UNUSED_ANONYMOUS_PARAMETER", "NAME_SHADOWING", "UNNECESSARY_NOT_NULL_ASSERTION")
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
open class GenPagesSuppliersIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesSuppliersIndex) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesSuppliersIndex
            val _cache = __ins.renderCache
            val keyword = ref("")
            val isLoading = ref(false)
            val errorMessage = ref("")
            val suppliers = ref(_uA<SupplierItem>())
            val currentPage = ref(1)
            val totalPages = ref(1)
            val totalCount = ref(0)
            val pageSize = ref(20)
            val fieldConfig = ref(_uA<UTSJSONObject>(object : UTSJSONObject() {
                var key = "contact"
                var label = "联系人"
            }, object : UTSJSONObject() {
                var key = "phone"
                var label = "电话"
            }, object : UTSJSONObject() {
                var key = "address"
                var label = "地址"
            }, object : UTSJSONObject() {
                var key = "arrears_amount"
                var label = "欠款"
            }))
            val menuActions = ref(_uA<UTSJSONObject>(object : UTSJSONObject() {
                var key = "copy-phone"
                var text = "复制电话"
            }, object : UTSJSONObject() {
                var key = "reload"
                var text = "刷新列表"
            }))
            fun gen_applySupplierResponse_fn(response: SupplierListResponse) {
                suppliers.value = response.results
                currentPage.value = response.current_page
                totalPages.value = response.total_pages
                totalCount.value = response.total_count
                pageSize.value = response.page_size
            }
            val applySupplierResponse = ::gen_applySupplierResponse_fn
            fun gen_parseErrorMessage_fn(error: Any): String {
                var message = "供应商列表加载失败"
                if (error != null) {
                    val errorText = JSON.stringify(error)
                    if (errorText != null && errorText != "") {
                        val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/suppliers/index.uvue:117")
                        if (parsedError != null) {
                            val rawMessage = parsedError!!["message"]
                            if (rawMessage != null) {
                                val parsedMessage = rawMessage as String
                                if (parsedMessage != "") {
                                    message = parsedMessage
                                }
                            }
                        }
                        if (message == "供应商列表加载失败") {
                            message = errorText
                        }
                    }
                }
                return message
            }
            val parseErrorMessage = ::gen_parseErrorMessage_fn
            fun gen_loadSuppliers_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (isLoading.value) {
                            return@w1
                        }
                        isLoading.value = true
                        errorMessage.value = ""
                        try {
                            val response = await(getSupplierList(SupplierListQuery(search = if (keyword.value == "") {
                                null
                            } else {
                                keyword.value
                            }
                            , page = currentPage.value, page_size = pageSize.value)))
                            applySupplierResponse(response)
                        }
                         catch (error: Throwable) {
                            suppliers.value = _uA()
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
            val loadSuppliers = ::gen_loadSuppliers_fn
            fun gen_getDisplayText_fn(value: String?): String {
                if (value == null || value == "") {
                    return "-"
                }
                return value
            }
            val getDisplayText = ::gen_getDisplayText_fn
            fun gen_buildImages_fn(item: SupplierItem): UTSArray<String> {
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
            fun gen_getSupplierImage_fn(item: SupplierItem): String {
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
            val getSupplierImage = ::gen_getSupplierImage_fn
            fun gen_formatPaidAmount_fn(value: Number): String {
                return value.toFixed(2)
            }
            val formatPaidAmount = ::gen_formatPaidAmount_fn
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
            fun gen_buildTags_fn(item: SupplierItem): UTSArray<String> {
                val tags: UTSArray<String> = _uA()
                tags.push(if (item.is_active) {
                    "启用"
                } else {
                    "停用"
                }
                )
                if (item.files_count > 0) {
                    tags.push("有附件")
                }
                if (item.arrears_amount != "0.00") {
                    tags.push("有欠款")
                }
                return tags
            }
            val buildTags = ::gen_buildTags_fn
            fun gen_supplierToListItem_fn(item: SupplierItem): UTSJSONObject {
                val cover = getSupplierImage(item)
                val images = buildImages(item)
                return _uO("id" to item.id.toString(10), "name" to item.name, "codeText" to ("编码：" + getDisplayText(item.code)), "contact" to getDisplayText(item.contact), "phone" to getDisplayText(item.phone), "address" to getDisplayText(item.address), "total_amount" to item.total_amount, "paid_amount_text" to formatPaidAmount(item.paid_amount), "arrears_amount" to item.arrears_amount, "updatedText" to formatDateText(item.updated_at), "filesText" to (item.files_count.toString(10) + " 个"), "cover" to cover, "images" to images, "tags" to buildTags(item), "rawId" to item.id.toString(10))
            }
            val supplierToListItem = ::gen_supplierToListItem_fn
            fun gen_handleSearchInput_fn(value: String) {
                keyword.value = value
            }
            val handleSearchInput = ::gen_handleSearchInput_fn
            fun gen_handleSearchConfirm_fn(value: String) {
                keyword.value = value
                currentPage.value = 1
                loadSuppliers()
            }
            val handleSearchConfirm = ::gen_handleSearchConfirm_fn
            fun gen_handleSearchClear_fn() {
                keyword.value = ""
                currentPage.value = 1
                loadSuppliers()
            }
            val handleSearchClear = ::gen_handleSearchClear_fn
            fun gen_handleReset_fn() {
                keyword.value = ""
                currentPage.value = 1
                loadSuppliers()
            }
            val handleReset = ::gen_handleReset_fn
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
                loadSuppliers()
            }
            val handlePageChange = ::gen_handlePageChange_fn
            fun gen_handleItemClick_fn(payload: UTSJSONObject) {
                val name = payload["name"]
                val text = if (name == null) {
                    "供应商"
                } else {
                    (name as String)
                }
                uni_showToast(ShowToastOptions(title = text, icon = "none"))
            }
            val handleItemClick = ::gen_handleItemClick_fn
            fun gen_handleMenu_fn(payload: UTSJSONObject) {
                val action = payload["action"]
                val item = payload["item"]
                if (action == null || item == null) {
                    return
                }
                val actionObject = action as UTSJSONObject
                val itemObject = item as UTSJSONObject
                val actionKey = actionObject["key"]
                if (actionKey == null) {
                    return
                }
                val key = actionKey as String
                if (key == "reload") {
                    loadSuppliers()
                    return
                }
                if (key == "copy-phone") {
                    val phone = itemObject["phone"]
                    val phoneText = if (phone == null) {
                        ""
                    } else {
                        (phone as String)
                    }
                    if (phoneText == "" || phoneText == "-") {
                        uni_showToast(ShowToastOptions(title = "暂无电话", icon = "none"))
                        return
                    }
                    uni_setClipboardData(SetClipboardDataOptions(data = phoneText, success = fun(_){
                        uni_showToast(ShowToastOptions(title = "电话已复制", icon = "success"))
                    }
                    ))
                }
            }
            val handleMenu = ::gen_handleMenu_fn
            val listItems = computed(fun(): UTSArray<UTSJSONObject> {
                val result: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < suppliers.value.length){
                        result.push(supplierToListItem(suppliers.value[index]))
                        index += 1
                    }
                }
                return result
            }
            )
            val supplierCountText = computed(fun(): String {
                return totalCount.value.toString(10)
            }
            )
            val activeCountText = computed(fun(): String {
                var count: Number = 0
                run {
                    var index: Number = 0
                    while(index < suppliers.value.length){
                        if (suppliers.value[index].is_active) {
                            count += 1
                        }
                        index += 1
                    }
                }
                return count.toString(10)
            }
            )
            val withFileCountText = computed(fun(): String {
                var count: Number = 0
                run {
                    var index: Number = 0
                    while(index < suppliers.value.length){
                        if (suppliers.value[index].files_count > 0) {
                            count += 1
                        }
                        index += 1
                    }
                }
                return count.toString(10)
            }
            )
            val emptyText = computed(fun(): String {
                if (isLoading.value) {
                    return "正在加载"
                }
                if (keyword.value != "") {
                    return "没有匹配的供应商"
                }
                return "暂无供应商"
            }
            )
            onLoad(fun(_options){
                loadSuppliers()
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList", GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalListClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to "供应商", "searchPlaceholder" to "输入供应商名称、编码、联系人", "searchValue" to unref(keyword), "showBack" to true, "showSearch" to true, "showFilter" to true, "showHome" to true, "filterActive" to (unref(keyword) != ""), "filterText" to "重置", "homePath" to "/pages/tabbar/products", "onSearchInput" to handleSearchInput, "onSearchConfirm" to handleSearchConfirm, "onSearchClear" to handleSearchClear, "onFilter" to handleReset), null, 8, _uA(
                        "searchValue",
                        "filterActive"
                    )),
                    _cE("scroll-view", _uM("style" to _nS(_uM("flex" to "1")), "class" to "page-scroll"), _uA(
                        _cE("view", _uM("class" to "page-content"), _uA(
                            _cE("view", _uM("class" to "summary-card"), _uA(
                                _cE("view", _uM("class" to "summary-item summary-item-gap"), _uA(
                                    _cE("text", _uM("class" to "summary-value"), _tD(supplierCountText.value), 1),
                                    _cE("text", _uM("class" to "summary-label"), "当前结果")
                                )),
                                _cE("view", _uM("class" to "summary-item summary-item-gap"), _uA(
                                    _cE("text", _uM("class" to "summary-value"), _tD(activeCountText.value), 1),
                                    _cE("text", _uM("class" to "summary-label"), "启用中")
                                )),
                                _cE("view", _uM("class" to "summary-item"), _uA(
                                    _cE("text", _uM("class" to "summary-value"), _tD(withFileCountText.value), 1),
                                    _cE("text", _uM("class" to "summary-label"), "含附件")
                                ))
                            )),
                            if (isTrue(unref(errorMessage) != "" && !unref(isLoading))) {
                                _cE("view", _uM("key" to 0, "class" to "error-card"), _uA(
                                    _cE("text", _uM("class" to "error-title"), "加载失败"),
                                    _cE("text", _uM("class" to "error-desc"), _tD(unref(errorMessage)), 1),
                                    _cE("button", _uM("class" to "retry-btn", "onClick" to loadSuppliers), _uA(
                                        _cE("text", _uM("class" to "retry-btn-text"), "重新加载")
                                    ))
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            _cV(_component_lili_UniversalList, _uM("items" to listItems.value, "keyField" to "id", "titleField" to "name", "subtitleField" to "codeText", "imageField" to "cover", "imageListField" to "images", "tagField" to "tags", "fields" to unref(fieldConfig), "loading" to unref(isLoading), "loadingText" to "正在加载供应商", "emptyText" to emptyText.value, "emptyIcon" to "◎", "showMenu" to true, "menuActions" to unref(menuActions), "showChevron" to false, "showPagination" to true, "currentPage" to unref(currentPage), "totalPages" to unref(totalPages), "totalCount" to unref(totalCount), "onItemClick" to handleItemClick, "onMenu" to handleMenu, "onPageChange" to handlePageChange), null, 8, _uA(
                                "items",
                                "fields",
                                "loading",
                                "emptyText",
                                "menuActions",
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
            ), _uA(
                GenApp.styles
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-scroll" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-content" to _pS(_uM("paddingLeft" to 16, "paddingRight" to 16, "paddingTop" to 12, "paddingBottom" to 24)), "summary-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "marginBottom" to 14, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E5E7EB", "borderRightColor" to "#E5E7EB", "borderBottomColor" to "#E5E7EB", "borderLeftColor" to "#E5E7EB", "flexDirection" to "row")), "summary-item" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "summary-item-gap" to _pS(_uM("marginRight" to 10)), "summary-value" to _pS(_uM("fontSize" to 22, "lineHeight" to "28px", "color" to "#0F172A", "fontWeight" to "bold")), "summary-label" to _pS(_uM("fontSize" to 12, "lineHeight" to "18px", "color" to "#94A3B8", "marginTop" to 6)), "error-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "paddingTop" to 18, "paddingRight" to 18, "paddingBottom" to 18, "paddingLeft" to 18, "marginBottom" to 14, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FECACA", "borderRightColor" to "#FECACA", "borderBottomColor" to "#FECACA", "borderLeftColor" to "#FECACA", "alignItems" to "center")), "error-title" to _pS(_uM("fontSize" to 18, "lineHeight" to "24px", "color" to "#B42318", "fontWeight" to "bold")), "error-desc" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#7F1D1D", "marginTop" to 8, "textAlign" to "center")), "retry-btn" to _pS(_uM("marginTop" to 14, "height" to 40, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#0F172A", "paddingLeft" to 18, "paddingRight" to 18)), "retry-btn-text" to _pS(_uM("fontSize" to 14, "color" to "#FFFFFF")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
