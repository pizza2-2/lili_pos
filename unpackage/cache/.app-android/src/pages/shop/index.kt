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
open class GenPagesShopIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesShopIndex) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesShopIndex
            val _cache = __ins.renderCache
            val shopListRefreshStorageKey = "refresh:pages:shop:index"
            val keyword = ref("")
            val isLoading = ref(false)
            val errorMessage = ref("")
            val shops = ref(_uA<ShopItem>())
            val currentPage = ref(1)
            val totalPages = ref(1)
            val totalCount = ref(0)
            val pageSize = ref(20)
            val fieldConfig = ref(_uA<UTSJSONObject>(_uO("key" to "address", "label" to "地址"), _uO("key" to "media_records_count", "label" to "媒体数量")))
            val menuActions = ref(_uA<UTSJSONObject>(_uO("key" to "media", "text" to "资料")))
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
            fun gen_parseErrorMessage_fn(error: Any): String {
                var message = "商店列表加载失败"
                if (error != null) {
                    val directMessage = (error as UTSError).message
                    if (directMessage != null && directMessage != "") {
                        message = directMessage
                    }
                    val errorText = JSON.stringify(error)
                    if (errorText != null && errorText != "") {
                        val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/shop/index.uvue:107")
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
            fun gen_applyShopResponse_fn(response: ShopListResponse): Unit {
                shops.value = response.results
                currentPage.value = response.current_page
                totalPages.value = response.total_pages
                totalCount.value = response.total_count
                pageSize.value = response.page_size
            }
            val applyShopResponse = ::gen_applyShopResponse_fn
            fun gen_loadShops_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (isLoading.value) {
                            return@w1
                        }
                        isLoading.value = true
                        errorMessage.value = ""
                        try {
                            val response = await(getShopList(ShopListQuery(search = if (keyword.value == "") {
                                null
                            } else {
                                keyword.value
                            }
                            , page = currentPage.value, page_size = pageSize.value)))
                            applyShopResponse(response)
                        }
                         catch (error: Throwable) {
                            shops.value = _uA<ShopItem>()
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
            val loadShops = ::gen_loadShops_fn
            fun gen_getDisplayText_fn(value: String): String {
                return if (value == "") {
                    "-"
                } else {
                    value
                }
            }
            val getDisplayText = ::gen_getDisplayText_fn
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
            fun gen_shopToListItem_fn(item: ShopItem): UTSJSONObject {
                return _uO("id" to item.id.toString(10), "name" to item.name, "address" to item.address, "addressText" to ("地址：" + getDisplayText(item.address)), "company_name" to item.company_name, "companyText" to ("公司：" + getDisplayText(item.company_name)), "media_records_count" to item.media_records_count.toString(10), "updated_at" to formatDateText(item.updated_at), "rawId" to item.id.toString(10))
            }
            val shopToListItem = ::gen_shopToListItem_fn
            fun gen_copyText_fn(text: String, successTitle: String, emptyTitle: String): Unit {
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
            fun gen_goToShopMedia_fn(shopId: String, shopName: String): Unit {
                if (shopId == "") {
                    return
                }
                uni_navigateTo(NavigateToOptions(url = "/pages/shop/media?shop_id=" + shopId + "&shop_name=" + UTSAndroid.consoleDebugError(encodeURIComponent(shopName), " at pages/shop/index.uvue:208")))
            }
            val goToShopMedia = ::gen_goToShopMedia_fn
            fun gen_consumeShopListRefreshNeeded_fn(): Boolean {
                val storedValue = uni_getStorageSync(shopListRefreshStorageKey)
                if (storedValue == null) {
                    return false
                }
                val storedText = "" + storedValue
                if (storedText == "") {
                    return false
                }
                uni_removeStorageSync(shopListRefreshStorageKey)
                return true
            }
            val consumeShopListRefreshNeeded = ::gen_consumeShopListRefreshNeeded_fn
            fun gen_handleSearchInput_fn(value: Any): Unit {
                keyword.value = stringValue(value)
            }
            val handleSearchInput = ::gen_handleSearchInput_fn
            fun gen_handleSearchConfirm_fn(value: Any?): Unit {
                keyword.value = stringValue(value)
                currentPage.value = 1
                loadShops()
            }
            val handleSearchConfirm = ::gen_handleSearchConfirm_fn
            fun gen_handleSearchClear_fn(): Unit {
                keyword.value = ""
                currentPage.value = 1
                loadShops()
            }
            val handleSearchClear = ::gen_handleSearchClear_fn
            fun gen_handlePageChange_fn(payload: UTSJSONObject): Unit {
                val pageValue = payload["page"]
                if (pageValue == null) {
                    return
                }
                val nextPage = parseInt("" + pageValue)
                if (isNaN(nextPage) || nextPage <= 0 || nextPage == currentPage.value) {
                    return
                }
                currentPage.value = nextPage
                loadShops()
            }
            val handlePageChange = ::gen_handlePageChange_fn
            fun gen_handleItemClick_fn(payload: UTSJSONObject): Unit {
                val itemName = stringValue(payload["name"], "商店")
                uni_showToast(ShowToastOptions(title = itemName, icon = "none"))
            }
            val handleItemClick = ::gen_handleItemClick_fn
            fun gen_handleSubtitleClick_fn(payload: UTSJSONObject): Unit {
                val itemValue = payload["item"]
                if (itemValue == null) {
                    return
                }
                val item = itemValue as UTSJSONObject
                copyText(stringValue(item["address"]), "地址已复制", "暂无地址")
            }
            val handleSubtitleClick = ::gen_handleSubtitleClick_fn
            fun gen_handleMetaClick_fn(payload: UTSJSONObject): Unit {
                val itemValue = payload["item"]
                if (itemValue == null) {
                    return
                }
                val item = itemValue as UTSJSONObject
                copyText(stringValue(item["company_name"]), "公司名已复制", "暂无公司名")
            }
            val handleMetaClick = ::gen_handleMetaClick_fn
            fun gen_handleFieldClick_fn(payload: UTSJSONObject): Unit {
                val keyValue = payload["key"]
                val itemValue = payload["item"]
                if (keyValue == null || itemValue == null) {
                    return
                }
                val key = keyValue as String
                val item = itemValue as UTSJSONObject
                if (key == "address") {
                    copyText(stringValue(item["address"]), "地址已复制", "暂无地址")
                    return
                }
                if (key == "company_name") {
                    copyText(stringValue(item["company_name"]), "公司名已复制", "暂无公司名")
                    return
                }
                if (key == "media_records_count") {
                    copyText(stringValue(item["media_records_count"]), "媒体数量已复制", "暂无媒体数量")
                    return
                }
                if (key == "updated_at") {
                    copyText(stringValue(item["updated_at"]), "更新时间已复制", "暂无更新时间")
                }
            }
            val handleFieldClick = ::gen_handleFieldClick_fn
            fun gen_handleMenu_fn(payload: UTSJSONObject): Unit {
                val actionValue = payload["action"]
                val itemValue = payload["item"]
                if (actionValue == null || itemValue == null) {
                    return
                }
                val action = actionValue as UTSJSONObject
                val item = itemValue as UTSJSONObject
                val actionKey = stringValue(action["key"])
                val shopId = stringValue(item["rawId"])
                val shopNameValue = stringValue(item["name"])
                if (actionKey == "media") {
                    goToShopMedia(shopId, shopNameValue)
                    return
                }
            }
            val handleMenu = ::gen_handleMenu_fn
            val listItems = computed(fun(): UTSArray<UTSJSONObject> {
                val result: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < shops.value.length){
                        result.push(shopToListItem(shops.value[index]))
                        index += 1
                    }
                }
                return result
            }
            )
            val summaryItems = computed(fun(): UTSArray<UTSJSONObject> {
                return _uA(
                    _uO("key" to "total", "label" to "商店总数", "value" to totalCount.value.toString(10)),
                    _uO("key" to "page", "label" to "当前页", "value" to (currentPage.value.toString(10) + " / " + totalPages.value.toString(10)))
                )
            }
            )
            val emptyText = computed(fun(): String {
                if (isLoading.value) {
                    return "正在加载"
                }
                if (keyword.value != "") {
                    return "没有匹配的商店"
                }
                return "暂无商店"
            }
            )
            onLoad(fun(_options){
                loadShops()
            }
            )
            onShow(fun(){
                if (consumeShopListRefreshNeeded()) {
                    loadShops()
                }
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList", GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalListClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to "商店", "searchPlaceholder" to "输入商店名称或地址", "searchValue" to unref(keyword), "showBack" to true, "showSearch" to true, "showFilter" to false, "showHome" to true, "homePath" to "/pages/tabbar/settings", "onSearchInput" to fun(`$event`: Any){
                        handleSearchInput(`$event`)
                    }
                    , "onSearchConfirm" to fun(`$event`: Any){
                        handleSearchConfirm(`$event`)
                    }
                    , "onSearchClear" to fun(){
                        handleSearchClear()
                    }
                    ), null, 8, _uA(
                        "searchValue",
                        "onSearchInput",
                        "onSearchConfirm",
                        "onSearchClear"
                    )),
                    _cE("scroll-view", _uM("style" to _nS(_uM("flex" to "1")), "class" to "page-scroll"), _uA(
                        _cE("view", _uM("class" to "page-content"), _uA(
                            if (isTrue(unref(errorMessage) != "" && !unref(isLoading))) {
                                _cE("view", _uM("key" to 0, "class" to "error-card"), _uA(
                                    _cE("text", _uM("class" to "error-title"), "加载失败"),
                                    _cE("text", _uM("class" to "error-desc"), _tD(unref(errorMessage)), 1),
                                    _cE("button", _uM("class" to "retry-btn", "onClick" to loadShops), _uA(
                                        _cE("text", _uM("class" to "retry-btn-text"), "重新加载")
                                    ))
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            _cV(_component_lili_UniversalList, _uM("items" to listItems.value, "keyField" to "id", "titleField" to "name", "subtitleField" to "addressText", "metaField" to "companyText", "fields" to unref(fieldConfig), "loading" to unref(isLoading), "loadingText" to "正在加载商店", "keepContentOnLoading" to true, "inlineLoadingText" to "商店数据刷新中...", "emptyText" to emptyText.value, "emptyIcon" to "◎", "showMenu" to true, "menuActions" to unref(menuActions), "showChevron" to false, "showPagination" to true, "currentPage" to unref(currentPage), "totalPages" to unref(totalPages), "totalCount" to unref(totalCount), "summaryTitle" to "商店统计", "summaryItems" to summaryItems.value, "summaryCollapsedByDefault" to true, "showFloatingAdd" to false, "onItemClick" to handleItemClick, "onSubtitleClick" to handleSubtitleClick, "onMetaClick" to handleMetaClick, "onFieldClick" to handleFieldClick, "onMenu" to handleMenu, "onPageChange" to handlePageChange), null, 8, _uA(
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-scroll" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-content" to _pS(_uM("paddingLeft" to 6, "paddingRight" to 6, "paddingTop" to 6, "paddingBottom" to 24)), "error-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "paddingTop" to 18, "paddingRight" to 18, "paddingBottom" to 18, "paddingLeft" to 18, "marginBottom" to 14, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FECACA", "borderRightColor" to "#FECACA", "borderBottomColor" to "#FECACA", "borderLeftColor" to "#FECACA", "alignItems" to "center")), "error-title" to _pS(_uM("fontSize" to 18, "lineHeight" to "24px", "color" to "#B42318", "fontWeight" to "bold")), "error-desc" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#7F1D1D", "marginTop" to 8, "textAlign" to "center")), "retry-btn" to _pS(_uM("marginTop" to 14, "height" to 40, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#0F172A", "paddingLeft" to 18, "paddingRight" to 18)), "retry-btn-text" to _pS(_uM("fontSize" to 14, "color" to "#FFFFFF")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
