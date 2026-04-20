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
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesSuppliersIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesSuppliersIndex) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesSuppliersIndex
            val _cache = __ins.renderCache
            val supplierListRefreshStorageKey = "refresh:pages:suppliers:index"
            val keyword = ref("")
            val filterVisible = ref(false)
            val isLoading = ref(false)
            val errorMessage = ref("")
            val suppliers = ref(_uA<SupplierItem>())
            val currentPage = ref(1)
            val totalPages = ref(1)
            val totalCount = ref(0)
            val pageSize = ref(20)
            val filterOptionsLoading = ref(false)
            val filterOptionsError = ref("")
            val filterOptions = ref<SupplierFilterOptionsResponse?>(null)
            val selectedIsActive = ref<String?>(null)
            val selectedHasArrears = ref<String?>(null)
            val fieldConfig = ref(_uA<UTSJSONObject>(_uO("key" to "phone", "label" to "电话"), _uO("key" to "address", "label" to "地址"), _uO("key" to "arrears_amount", "label" to "欠款")))
            val menuActions = ref(_uA<UTSJSONObject>(_uO("key" to "edit", "text" to "编辑"), _uO("key" to "Detail", "text" to "详情"), _uO("key" to "add", "text" to "增加采购记录"), _uO("key" to "delete", "text" to "删除")))
            fun gen_applySupplierResponse_fn(response: SupplierListResponse) {
                suppliers.value = response.results
                currentPage.value = response.current_page
                totalPages.value = response.total_pages
                totalCount.value = response.total_count
                pageSize.value = response.page_size
            }
            val applySupplierResponse = ::gen_applySupplierResponse_fn
            fun gen_closeFilterDrawer_fn() {
                filterVisible.value = false
            }
            val closeFilterDrawer = ::gen_closeFilterDrawer_fn
            fun gen_handleFilterVisibleChange_fn(value: Boolean) {
                filterVisible.value = value
            }
            val handleFilterVisibleChange = ::gen_handleFilterVisibleChange_fn
            fun gen_parseErrorMessage_fn(error: Any): String {
                var message = "供应商列表加载失败"
                if (error != null) {
                    val errorText = JSON.stringify(error)
                    if (errorText != null && errorText != "") {
                        val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/suppliers/index.uvue:177")
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
                            , page = currentPage.value, page_size = pageSize.value, is_active = selectedIsActive.value, has_arrears = selectedHasArrears.value)))
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
            fun gen_consumeSupplierListRefreshNeeded_fn(): Boolean {
                val storedValue = uni_getStorageSync(supplierListRefreshStorageKey)
                if (storedValue == null) {
                    return false
                }
                val storedText = "" + storedValue
                if (storedText == "") {
                    return false
                }
                uni_removeStorageSync(supplierListRefreshStorageKey)
                return true
            }
            val consumeSupplierListRefreshNeeded = ::gen_consumeSupplierListRefreshNeeded_fn
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
            fun gen_supplierToListItem_fn(item: SupplierItem): UTSJSONObject {
                val cover = getSupplierImage(item)
                val images = buildImages(item)
                return _uO("id" to item.id.toString(10), "name" to item.name, "code" to getDisplayText(item.code), "codeText" to ("编码：" + getDisplayText(item.code)), "contactText" to ("联系人：" + getDisplayText(item.contact)), "contact" to getDisplayText(item.contact), "phone" to getDisplayText(item.phone), "address" to getDisplayText(item.address), "description" to if (item.description == null) {
                    ""
                } else {
                    item.description
                }
                , "is_active" to item.is_active, "total_amount" to item.total_amount, "paid_amount_text" to formatPaidAmount(item.paid_amount), "arrears_amount" to item.arrears_amount, "updatedText" to formatDateText(item.updated_at), "filesText" to (item.files_count.toString(10) + " 个"), "cover" to cover, "images" to images, "rawId" to item.id.toString(10))
            }
            val supplierToListItem = ::gen_supplierToListItem_fn
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
            fun gen_handleFilterOpen_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (filterOptions.value != null || filterOptionsLoading.value) {
                            return@w1
                        }
                        filterOptionsLoading.value = true
                        filterOptionsError.value = ""
                        try {
                            filterOptions.value = await(getSupplierFilterOptions())
                        }
                         catch (error: Throwable) {
                            filterOptionsError.value = parseErrorMessage(error)
                        }
                         finally {
                            filterOptionsLoading.value = false
                        }
                })
            }
            val handleFilterOpen = ::gen_handleFilterOpen_fn
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
            fun gen_isFilterOptionSelected_fn(param: String, value: String): Boolean {
                if (param == "is_active") {
                    return selectedIsActive.value == value
                }
                if (param == "has_arrears") {
                    return selectedHasArrears.value == value
                }
                return false
            }
            val isFilterOptionSelected = ::gen_isFilterOptionSelected_fn
            fun gen_toggleFilterOption_fn(param: String, value: String) {
                if (param == "is_active") {
                    selectedIsActive.value = if (selectedIsActive.value == value) {
                        null
                    } else {
                        value
                    }
                    return
                }
                if (param == "has_arrears") {
                    selectedHasArrears.value = if (selectedHasArrears.value == value) {
                        null
                    } else {
                        value
                    }
                }
            }
            val toggleFilterOption = ::gen_toggleFilterOption_fn
            fun gen_handleFilterReset_fn() {
                selectedIsActive.value = null
                selectedHasArrears.value = null
                keyword.value = ""
                currentPage.value = 1
                closeFilterDrawer()
                loadSuppliers()
            }
            val handleFilterReset = ::gen_handleFilterReset_fn
            fun gen_applySelectedSupplier_fn() {
                currentPage.value = 1
                closeFilterDrawer()
                loadSuppliers()
            }
            val applySelectedSupplier = ::gen_applySelectedSupplier_fn
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
            fun gen_handleSubtitleClick_fn(payload: UTSJSONObject) {
                val item = payload["item"]
                if (item == null) {
                    return
                }
                val itemObject = item as UTSJSONObject
                val code = itemObject["code"]
                val codeText = if (code == null) {
                    ""
                } else {
                    (code as String)
                }
                copyText(codeText, "编码已复制", "暂无编码")
            }
            val handleSubtitleClick = ::gen_handleSubtitleClick_fn
            fun gen_handleFieldClick_fn(payload: UTSJSONObject) {
                val keyValue = payload["key"]
                val itemValue = payload["item"]
                if (keyValue == null || itemValue == null) {
                    return
                }
                val key = keyValue as String
                val item = itemValue as UTSJSONObject
                if (key == "phone") {
                    val phone = item["phone"]
                    val phoneText = if (phone == null) {
                        ""
                    } else {
                        (phone as String)
                    }
                    copyText(phoneText, "电话已复制", "暂无电话")
                    return
                }
                if (key == "contact") {
                    val contact = item["contact"]
                    val contactText = if (contact == null) {
                        ""
                    } else {
                        (contact as String)
                    }
                    copyText(contactText, "联系人已复制", "暂无联系人")
                    return
                }
                if (key == "address") {
                    val address = item["address"]
                    val addressText = if (address == null) {
                        ""
                    } else {
                        (address as String)
                    }
                    copyText(addressText, "地址已复制", "暂无地址")
                    return
                }
                if (key == "arrears_amount") {
                    val arrears = item["arrears_amount"]
                    val arrearsText = if (arrears == null) {
                        ""
                    } else {
                        ("" + arrears)
                    }
                    copyText(arrearsText, "欠款已复制", "暂无欠款")
                }
            }
            val handleFieldClick = ::gen_handleFieldClick_fn
            fun gen_handleMetaClick_fn(payload: UTSJSONObject) {
                val item = payload["item"]
                if (item == null) {
                    return
                }
                val itemObject = item as UTSJSONObject
                val contact = itemObject["contact"]
                val contactText = if (contact == null) {
                    ""
                } else {
                    (contact as String)
                }
                copyText(contactText, "联系人已复制", "暂无联系人")
            }
            val handleMetaClick = ::gen_handleMetaClick_fn
            fun gen_confirmDeleteSupplier_fn(supplierId: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        try {
                            await(deleteSupplier(supplierId))
                            uni_showToast(ShowToastOptions(title = "删除成功", icon = "success"))
                            loadSuppliers()
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error), icon = "none"))
                        }
                })
            }
            val confirmDeleteSupplier = ::gen_confirmDeleteSupplier_fn
            fun gen_navigateToTransactionsPage_fn(pagePath: String, supplierId: String) {
                if (supplierId == "") {
                    uni_showToast(ShowToastOptions(title = "供应商ID缺失", icon = "none"))
                    return
                }
                uni_navigateTo(NavigateToOptions(url = pagePath + "?supplier_id=" + supplierId))
            }
            val navigateToTransactionsPage = ::gen_navigateToTransactionsPage_fn
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
                val supplierIdValue = itemObject["rawId"]
                val supplierId = if (supplierIdValue == null) {
                    ""
                } else {
                    (supplierIdValue as String)
                }
                if (key == "edit") {
                    uni_navigateTo(NavigateToOptions(url = "/pages/suppliers/from?id=" + supplierId))
                    return
                }
                if (key == "Detail") {
                    navigateToTransactionsPage("/pages/transactions/index", supplierId)
                    return
                }
                if (key == "add") {
                    navigateToTransactionsPage("/pages/transactions/from", supplierId)
                    return
                }
                if (key == "delete") {
                    uni_showModal(ShowModalOptions(title = "删除供应商", content = "确定删除这个供应商吗？", success = fun(res){
                        if (!res.confirm) {
                            return
                        }
                        confirmDeleteSupplier(supplierId)
                    }
                    ))
                    return
                }
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
            fun gen_handleCreateSupplier_fn() {
                uni_navigateTo(NavigateToOptions(url = "/pages/suppliers/from"))
            }
            val handleCreateSupplier = ::gen_handleCreateSupplier_fn
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
            val hasActiveFilter = computed(fun(): Boolean {
                return keyword.value != "" || selectedIsActive.value != null || selectedHasArrears.value != null
            }
            )
            val emptyText = computed(fun(): String {
                if (isLoading.value) {
                    return "正在加载"
                }
                if (hasActiveFilter.value) {
                    return "没有匹配的供应商"
                }
                return "暂无供应商"
            }
            )
            val filterDefinitions = computed(fun(): UTSArray<SupplierFilterDefinition> {
                if (filterOptions.value == null) {
                    return _uA()
                }
                return filterOptions.value!!.filters
            }
            )
            onLoad(fun(_options){
                loadSuppliers()
            }
            )
            onShow(fun(){
                if (consumeSupplierListRefreshNeeded()) {
                    loadSuppliers()
                }
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList", GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalListClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to "供应商", "searchPlaceholder" to "输入供应商名称、编码、联系人", "searchValue" to unref(keyword), "filterVisible" to unref(filterVisible), "showBack" to true, "showSearch" to true, "showFilter" to true, "showHome" to true, "filterActive" to hasActiveFilter.value, "filterText" to "重置", "homePath" to "/pages/tabbar/products", "onSearchInput" to handleSearchInput, "onSearchConfirm" to handleSearchConfirm, "onSearchClear" to handleSearchClear, "onUpdate:filterVisible" to handleFilterVisibleChange, "onFilterOpen" to handleFilterOpen), _uM("filter-panel" to withSlotCtx(fun(): UTSArray<Any> {
                        return _uA(
                            _cE("view", _uM("class" to "supplier-filter-panel"), _uA(
                                _cE("view", _uM("class" to "supplier-filter-actions"), _uA(
                                    _cE("view", _uM("class" to "supplier-filter-btn supplier-filter-btn-light", "onClick" to handleFilterReset), _uA(
                                        _cE("text", _uM("class" to "supplier-filter-btn-light-text"), "重置")
                                    )),
                                    _cE("view", _uM("class" to "supplier-filter-btn supplier-filter-btn-primary", "onClick" to applySelectedSupplier), _uA(
                                        _cE("text", _uM("class" to "supplier-filter-btn-primary-text"), "应用")
                                    ))
                                )),
                                if (isTrue(unref(filterOptionsLoading))) {
                                    _cE("view", _uM("key" to 0, "class" to "supplier-filter-state"), _uA(
                                        _cE("text", _uM("class" to "supplier-filter-state-text"), "筛选选项加载中...")
                                    ))
                                } else {
                                    if (unref(filterOptionsError) != "") {
                                        _cE("view", _uM("key" to 1, "class" to "supplier-filter-state"), _uA(
                                            _cE("text", _uM("class" to "supplier-filter-state-text"), _tD(unref(filterOptionsError)), 1)
                                        ))
                                    } else {
                                        if (filterDefinitions.value.length == 0) {
                                            _cE("view", _uM("key" to 2, "class" to "supplier-filter-state"), _uA(
                                                _cE("text", _uM("class" to "supplier-filter-state-text"), "暂无可用筛选项")
                                            ))
                                        } else {
                                            _cE("scroll-view", _uM("key" to 3, "scroll-y" to "true", "class" to "supplier-filter-scroll"), _uA(
                                                _cE(Fragment, null, RenderHelpers.renderList(filterDefinitions.value, fun(filter, __key, __index, _cached): Any {
                                                    return _cE("view", _uM("key" to filter.key, "class" to "supplier-filter-group"), _uA(
                                                        _cE("text", _uM("class" to "supplier-filter-group-title"), _tD(filter.label), 1),
                                                        _cE("view", _uM("class" to "supplier-filter-options"), _uA(
                                                            _cE(Fragment, null, RenderHelpers.renderList(filter.options, fun(option, __key, __index, _cached): Any {
                                                                return _cE("view", _uM("key" to (filter.key + "-" + option.value), "class" to _nC(if (isFilterOptionSelected(filter.param, option.value)) {
                                                                    "supplier-filter-option supplier-filter-option-active"
                                                                } else {
                                                                    "supplier-filter-option"
                                                                }
                                                                ), "onClick" to fun(){
                                                                    toggleFilterOption(filter.param, option.value)
                                                                }
                                                                ), _uA(
                                                                    _cE("text", _uM("class" to _nC(if (isFilterOptionSelected(filter.param, option.value)) {
                                                                        "supplier-filter-option-text supplier-filter-option-text-active"
                                                                    } else {
                                                                        "supplier-filter-option-text"
                                                                    }
                                                                    )), _tD(option.label), 3)
                                                                ), 10, _uA(
                                                                    "onClick"
                                                                ))
                                                            }
                                                            ), 128)
                                                        ))
                                                    ))
                                                }
                                                ), 128)
                                            ))
                                        }
                                    }
                                }
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
                                    _cE("button", _uM("class" to "retry-btn", "onClick" to loadSuppliers), _uA(
                                        _cE("text", _uM("class" to "retry-btn-text"), "重新加载")
                                    ))
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            _cV(_component_lili_UniversalList, _uM("items" to listItems.value, "keyField" to "id", "titleField" to "name", "subtitleField" to "codeText", "metaField" to "contactText", "imageField" to "cover", "imageListField" to "images", "tagField" to "tags", "fields" to unref(fieldConfig), "loading" to unref(isLoading), "loadingText" to "正在加载供应商", "keepContentOnLoading" to true, "inlineLoadingText" to "供应商数据刷新中...", "emptyText" to emptyText.value, "emptyIcon" to "◎", "showMenu" to true, "menuActions" to unref(menuActions), "showChevron" to false, "showPagination" to true, "currentPage" to unref(currentPage), "totalPages" to unref(totalPages), "totalCount" to unref(totalCount), "showFloatingAdd" to true, "floatingAddText" to "新增", "onItemClick" to handleItemClick, "onSubtitleClick" to handleSubtitleClick, "onMetaClick" to handleMetaClick, "onFieldClick" to handleFieldClick, "onMenu" to handleMenu, "onPageChange" to handlePageChange, "onFloatingAdd" to handleCreateSupplier), null, 8, _uA(
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
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-scroll" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-content" to _pS(_uM("paddingLeft" to 16, "paddingRight" to 16, "paddingTop" to 12, "paddingBottom" to 96)), "summary-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "marginBottom" to 14, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E5E7EB", "borderRightColor" to "#E5E7EB", "borderBottomColor" to "#E5E7EB", "borderLeftColor" to "#E5E7EB", "flexDirection" to "row")), "summary-item" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "summary-item-gap" to _pS(_uM("marginRight" to 10)), "summary-value" to _pS(_uM("fontSize" to 22, "lineHeight" to "28px", "color" to "#0F172A", "fontWeight" to "bold")), "summary-label" to _pS(_uM("fontSize" to 12, "lineHeight" to "18px", "color" to "#94A3B8", "marginTop" to 6)), "error-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "paddingTop" to 18, "paddingRight" to 18, "paddingBottom" to 18, "paddingLeft" to 18, "marginBottom" to 14, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FECACA", "borderRightColor" to "#FECACA", "borderBottomColor" to "#FECACA", "borderLeftColor" to "#FECACA", "alignItems" to "center")), "error-title" to _pS(_uM("fontSize" to 18, "lineHeight" to "24px", "color" to "#B42318", "fontWeight" to "bold")), "error-desc" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#7F1D1D", "marginTop" to 8, "textAlign" to "center")), "retry-btn" to _pS(_uM("marginTop" to 14, "height" to 40, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#0F172A", "paddingLeft" to 18, "paddingRight" to 18)), "retry-btn-text" to _pS(_uM("fontSize" to 14, "color" to "#FFFFFF")), "supplier-filter-panel" to _pS(_uM("paddingBottom" to 8)), "supplier-filter-actions" to _pS(_uM("flexDirection" to "row", "marginBottom" to 12)), "supplier-filter-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 40, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "alignItems" to "center", "justifyContent" to "center")), "supplier-filter-btn-light" to _pS(_uM("backgroundColor" to "#F3F6FA", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "marginRight" to 8)), "supplier-filter-btn-primary" to _pS(_uM("backgroundColor" to "#0F172A")), "supplier-filter-btn-light-text" to _pS(_uM("fontSize" to 14, "lineHeight" to "14px", "color" to "#475569")), "supplier-filter-btn-primary-text" to _pS(_uM("fontSize" to 14, "lineHeight" to "14px", "color" to "#FFFFFF")), "supplier-filter-state" to _pS(_uM("height" to 140, "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "backgroundColor" to "#F8FAFC", "alignItems" to "center", "justifyContent" to "center")), "supplier-filter-state-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#64748B")), "supplier-filter-scroll" to _pS(_uM("height" to 360)), "supplier-filter-group" to _pS(_uM("paddingLeft" to 12, "paddingRight" to 12, "paddingTop" to 12, "paddingBottom" to 12, "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "backgroundColor" to "#FFFFFF", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E5EAF1", "borderRightColor" to "#E5EAF1", "borderBottomColor" to "#E5EAF1", "borderLeftColor" to "#E5EAF1", "marginBottom" to 8)), "supplier-filter-group-title" to _pS(_uM("fontSize" to 14, "lineHeight" to "18px", "color" to "#0F172A", "fontWeight" to "bold")), "supplier-filter-options" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 10)), "supplier-filter-option" to _pS(_uM("minWidth" to 56, "height" to 34, "paddingLeft" to 12, "paddingRight" to 12, "borderTopLeftRadius" to 17, "borderTopRightRadius" to 17, "borderBottomRightRadius" to 17, "borderBottomLeftRadius" to 17, "backgroundColor" to "#F8FAFC", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "alignItems" to "center", "justifyContent" to "center", "marginRight" to 8, "marginBottom" to 8)), "supplier-filter-option-active" to _pS(_uM("backgroundColor" to "#0F172A", "borderTopColor" to "#0F172A", "borderRightColor" to "#0F172A", "borderBottomColor" to "#0F172A", "borderLeftColor" to "#0F172A")), "supplier-filter-option-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "13px", "color" to "#475569")), "supplier-filter-option-text-active" to _pS(_uM("color" to "#FFFFFF")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
