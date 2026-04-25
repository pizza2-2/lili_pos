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
open class GenPagesShopMedia : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesShopMedia) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesShopMedia
            val _cache = __ins.renderCache
            val shopMediaListRefreshStorageKey = "refresh:pages:shop:media"
            val keyword = ref("")
            val isLoading = ref(false)
            val errorMessage = ref("")
            val records = ref(_uA<ShopMediaItem>())
            val currentPage = ref(1)
            val totalPages = ref(1)
            val totalCount = ref(0)
            val pageSize = ref(20)
            val shopId = ref("")
            val shopName = ref("")
            val fieldConfig = ref(_uA<UTSJSONObject>(_uO("key" to "expiration_date", "label" to "到期时间"), _uO("key" to "notes", "label" to "备注"), _uO("key" to "files_count", "label" to "文件数量"), _uO("key" to "updated_at", "label" to "更新时间")))
            val menuActions = ref(_uA<UTSJSONObject>(_uO("key" to "edit", "text" to "编辑")))
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
                var message = "商店资料加载失败"
                if (error != null) {
                    val directMessage = (error as UTSError).message
                    if (directMessage != null && directMessage != "") {
                        message = directMessage
                    }
                    val errorText = JSON.stringify(error)
                    if (errorText != null && errorText != "") {
                        val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/shop/media.uvue:114")
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
            fun gen_applyShopMediaResponse_fn(response: ShopMediaListResponse): Unit {
                records.value = response.results
                currentPage.value = response.current_page
                totalPages.value = response.total_pages
                totalCount.value = response.total_count
                pageSize.value = response.page_size
            }
            val applyShopMediaResponse = ::gen_applyShopMediaResponse_fn
            fun gen_loadShopMedia_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (isLoading.value) {
                            return@w1
                        }
                        isLoading.value = true
                        errorMessage.value = ""
                        try {
                            val response = await(getShopMediaList(ShopMediaListQuery(search = if (keyword.value == "") {
                                null
                            } else {
                                keyword.value
                            }
                            , shop = if (shopId.value == "") {
                                null
                            } else {
                                shopId.value
                            }
                            , page = currentPage.value, page_size = pageSize.value)))
                            applyShopMediaResponse(response)
                        }
                         catch (error: Throwable) {
                            records.value = _uA<ShopMediaItem>()
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
            val loadShopMedia = ::gen_loadShopMedia_fn
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
            fun gen_filePreviewUrl_fn(file: ShopMediaFile): String {
                if (file.signed_thumbnail_url != "") {
                    return file.signed_thumbnail_url
                }
                if (file.thumbnail_url != "") {
                    return file.thumbnail_url
                }
                if (file.signed_url != "") {
                    return file.signed_url
                }
                return file.file_url
            }
            val filePreviewUrl = ::gen_filePreviewUrl_fn
            fun gen_buildImages_fn(files: UTSArray<ShopMediaFile>): UTSArray<String> {
                val result: UTSArray<String> = _uA()
                run {
                    var index: Number = 0
                    while(index < files.length){
                        val previewUrl = filePreviewUrl(files[index])
                        if (previewUrl != "") {
                            result.push(previewUrl)
                        }
                        index += 1
                    }
                }
                return result
            }
            val buildImages = ::gen_buildImages_fn
            fun gen_buildCover_fn(files: UTSArray<ShopMediaFile>): String {
                if (files.length == 0) {
                    return ""
                }
                return filePreviewUrl(files[0])
            }
            val buildCover = ::gen_buildCover_fn
            fun gen_mediaRecordToListItem_fn(item: ShopMediaItem): UTSJSONObject {
                val images = buildImages(item.media_files)
                val expirationText = formatDateText(item.expiration_date)
                val recordTypeText = if (item.expiration_date == "") {
                    ("类型：" + getDisplayText(item.record_type_display))
                } else {
                    ("类型：" + getDisplayText(item.record_type_display) + " | 到期：" + expirationText)
                }
                return _uO("id" to item.id.toString(10), "title" to if (item.title == "") {
                    "未命名资料"
                } else {
                    item.title
                }
                , "record_type" to item.record_type_display, "recordTypeText" to recordTypeText, "shop_name" to item.shop_name, "shopNameText" to ("商店：" + getDisplayText(item.shop_name)), "expiration_date" to if (item.expiration_date == "") {
                    ""
                } else {
                    expirationText
                }
                , "notes" to getDisplayText(item.notes), "files_count" to item.files_count.toString(10), "updated_at" to formatDateText(item.updated_at), "cover" to buildCover(item.media_files), "images" to images, "rawId" to item.id.toString(10))
            }
            val mediaRecordToListItem = ::gen_mediaRecordToListItem_fn
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
            fun gen_consumeShopMediaRefreshNeeded_fn(): Boolean {
                val storedValue = uni_getStorageSync(shopMediaListRefreshStorageKey)
                if (storedValue == null) {
                    return false
                }
                val storedText = "" + storedValue
                if (storedText == "") {
                    return false
                }
                uni_removeStorageSync(shopMediaListRefreshStorageKey)
                return true
            }
            val consumeShopMediaRefreshNeeded = ::gen_consumeShopMediaRefreshNeeded_fn
            fun gen_handleSearchInput_fn(value: Any): Unit {
                keyword.value = stringValue(value)
            }
            val handleSearchInput = ::gen_handleSearchInput_fn
            fun gen_handleSearchConfirm_fn(value: Any?): Unit {
                keyword.value = stringValue(value)
                currentPage.value = 1
                loadShopMedia()
            }
            val handleSearchConfirm = ::gen_handleSearchConfirm_fn
            fun gen_handleSearchClear_fn(): Unit {
                keyword.value = ""
                currentPage.value = 1
                loadShopMedia()
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
                loadShopMedia()
            }
            val handlePageChange = ::gen_handlePageChange_fn
            fun gen_handleItemClick_fn(payload: UTSJSONObject): Unit {
                val title = stringValue(payload["title"], "商店资料")
                uni_showToast(ShowToastOptions(title = title, icon = "none"))
            }
            val handleItemClick = ::gen_handleItemClick_fn
            fun gen_handleMenu_fn(payload: UTSJSONObject): Unit {
                val actionValue = payload["action"]
                val itemValue = payload["item"]
                if (actionValue == null || itemValue == null) {
                    return
                }
                val action = actionValue as UTSJSONObject
                val item = itemValue as UTSJSONObject
                val actionKey = stringValue(action["key"])
                val id = stringValue(item["rawId"])
                if (actionKey == "edit" && id != "") {
                    uni_navigateTo(NavigateToOptions(url = "/pages/shop/from?id=" + id))
                }
            }
            val handleMenu = ::gen_handleMenu_fn
            fun gen_handleCreateMedia_fn(): Unit {
                val url = if (shopId.value == "") {
                    "/pages/shop/from"
                } else {
                    ("/pages/shop/from?shop_id=" + shopId.value + "&shop_name=" + UTSAndroid.consoleDebugError(encodeURIComponent(shopName.value), " at pages/shop/media.uvue:321"))
                }
                uni_navigateTo(NavigateToOptions(url = url))
            }
            val handleCreateMedia = ::gen_handleCreateMedia_fn
            fun gen_handleSubtitleClick_fn(payload: UTSJSONObject): Unit {
                val itemValue = payload["item"]
                if (itemValue == null) {
                    return
                }
                val item = itemValue as UTSJSONObject
                copyText(stringValue(item["record_type"]), "资料类型已复制", "暂无资料类型")
            }
            val handleSubtitleClick = ::gen_handleSubtitleClick_fn
            fun gen_handleMetaClick_fn(payload: UTSJSONObject): Unit {
                val itemValue = payload["item"]
                if (itemValue == null) {
                    return
                }
                val item = itemValue as UTSJSONObject
                copyText(stringValue(item["shop_name"]), "商店名已复制", "暂无商店名")
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
                if (key == "expiration_date") {
                    copyText(stringValue(item["expiration_date"]), "到期时间已复制", "暂无到期时间")
                    return
                }
                if (key == "notes") {
                    copyText(stringValue(item["notes"]), "备注已复制", "暂无备注")
                    return
                }
                if (key == "files_count") {
                    copyText(stringValue(item["files_count"]), "文件数量已复制", "暂无文件数量")
                    return
                }
                if (key == "updated_at") {
                    copyText(stringValue(item["updated_at"]), "更新时间已复制", "暂无更新时间")
                }
            }
            val handleFieldClick = ::gen_handleFieldClick_fn
            val pageTitle = computed(fun(): String {
                if (shopName.value != "") {
                    return shopName.value + " 资料"
                }
                return "商店资料"
            }
            )
            val listItems = computed(fun(): UTSArray<UTSJSONObject> {
                val result: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < records.value.length){
                        result.push(mediaRecordToListItem(records.value[index]))
                        index += 1
                    }
                }
                return result
            }
            )
            val summaryItems = computed(fun(): UTSArray<UTSJSONObject> {
                var fileCount: Number = 0
                run {
                    var index: Number = 0
                    while(index < records.value.length){
                        fileCount += records.value[index].files_count
                        index += 1
                    }
                }
                return _uA(
                    _uO("key" to "total", "label" to "资料总数", "value" to totalCount.value.toString(10)),
                    _uO("key" to "files", "label" to "当前页文件数", "value" to fileCount.toString(10))
                )
            }
            )
            val emptyText = computed(fun(): String {
                if (isLoading.value) {
                    return "正在加载"
                }
                if (keyword.value != "") {
                    return "没有匹配的商店资料"
                }
                return "暂无商店资料"
            }
            )
            onLoad(fun(event: OnLoadOptions){
                shopId.value = stringValue(event["shop_id"])
                val rawShopName = stringValue(event["shop_name"])
                shopName.value = if (rawShopName == "") {
                    ""
                } else {
                    stringValue(UTSAndroid.consoleDebugError(decodeURIComponent(rawShopName), " at pages/shop/media.uvue:417"))
                }
                loadShopMedia()
            }
            )
            onShow(fun(){
                if (consumeShopMediaRefreshNeeded()) {
                    loadShopMedia()
                }
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList", GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalListClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to pageTitle.value, "searchPlaceholder" to "输入资料标题或备注", "searchValue" to unref(keyword), "showBack" to true, "showSearch" to true, "showFilter" to false, "showHome" to true, "homePath" to "/pages/shop/index", "onSearchInput" to fun(`$event`: Any){
                        handleSearchInput(`$event`)
                    }
                    , "onSearchConfirm" to fun(`$event`: Any){
                        handleSearchConfirm(`$event`)
                    }
                    , "onSearchClear" to fun(){
                        handleSearchClear()
                    }
                    ), null, 8, _uA(
                        "title",
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
                                    _cE("button", _uM("class" to "retry-btn", "onClick" to loadShopMedia), _uA(
                                        _cE("text", _uM("class" to "retry-btn-text"), "重新加载")
                                    ))
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            _cV(_component_lili_UniversalList, _uM("items" to listItems.value, "keyField" to "id", "titleField" to "title", "subtitleField" to "recordTypeText", "metaField" to "shopNameText", "imageField" to "cover", "imageListField" to "images", "fields" to unref(fieldConfig), "loading" to unref(isLoading), "loadingText" to "正在加载商店资料", "keepContentOnLoading" to true, "inlineLoadingText" to "商店资料刷新中...", "emptyText" to emptyText.value, "emptyIcon" to "◎", "showMenu" to true, "menuActions" to unref(menuActions), "showChevron" to false, "showPagination" to true, "currentPage" to unref(currentPage), "totalPages" to unref(totalPages), "totalCount" to unref(totalCount), "summaryTitle" to "资料统计", "summaryItems" to summaryItems.value, "summaryCollapsedByDefault" to true, "showFloatingAdd" to true, "onItemClick" to handleItemClick, "onSubtitleClick" to handleSubtitleClick, "onMetaClick" to handleMetaClick, "onFieldClick" to handleFieldClick, "onMenu" to handleMenu, "onPageChange" to handlePageChange, "onFloatingAdd" to handleCreateMedia), null, 8, _uA(
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
