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
open class GenPagesProductsConfigModelIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesProductsConfigModelIndex) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesProductsConfigModelIndex
            val _cache = __ins.renderCache
            val resource = ref("discount")
            val parentAttributeTypeId = ref("")
            val parentAttributeTypeName = ref("")
            val keyword = ref("")
            val filterVisible = ref(false)
            val isLoading = ref(false)
            val errorMessage = ref("")
            val items = ref(_uA<UTSJSONObject>())
            val currentPage = ref(1)
            val totalPages = ref(1)
            val totalCount = ref(0)
            val pageSize = ref(20)
            val statusFilter = ref<String?>(null)
            val discountTypeFilter = ref<String?>(null)
            val selectedStatus = ref<String?>(null)
            val selectedDiscountType = ref<String?>(null)
            val menuActions = computed(fun(): UTSArray<UTSJSONObject> {
                if (resource.value == "attribute-type") {
                    return _uA(
                        _uO("key" to "values", "text" to "属性值"),
                        _uO("key" to "edit", "text" to "编辑"),
                        _uO("key" to "delete", "text" to "删除"),
                        _uO("key" to "reload", "text" to "刷新")
                    )
                }
                return _uA(
                    _uO("key" to "edit", "text" to "编辑"),
                    _uO("key" to "delete", "text" to "删除"),
                    _uO("key" to "reload", "text" to "刷新")
                )
            }
            )
            fun getStringField(obj: UTSJSONObject, key: String, fallback: String = ""): String {
                val value = obj[key]
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
            fun gen_getNumberField_fn(obj: UTSJSONObject, key: String): Number {
                val value = obj[key]
                if (value == null) {
                    return 0
                }
                val parsed = parseInt("" + value)
                if (isNaN(parsed)) {
                    return 0
                }
                return parsed
            }
            val getNumberField = ::gen_getNumberField_fn
            fun gen_getBoolField_fn(obj: UTSJSONObject, key: String): Boolean {
                val text = getStringField(obj, key).toLowerCase()
                return text == "true" || text == "1" || text == "yes"
            }
            val getBoolField = ::gen_getBoolField_fn
            fun gen_resourceBasePath_fn(): String {
                if (resource.value == "attribute-type") {
                    return attributeTypesPath()
                }
                if (resource.value == "attribute-value") {
                    return attributeValuesPath()
                }
                if (resource.value == "barcode-sequence") {
                    return barcodeSequencesPath()
                }
                return productDiscountsPath()
            }
            val resourceBasePath = ::gen_resourceBasePath_fn
            fun gen_resourceTitle_fn(): String {
                if (resource.value == "attribute-type") {
                    return "属性类型"
                }
                if (resource.value == "attribute-value") {
                    if (parentAttributeTypeName.value != "") {
                        return parentAttributeTypeName.value + "属性值"
                    }
                    return "属性值"
                }
                if (resource.value == "barcode-sequence") {
                    return "条形码序列"
                }
                return "商品折扣"
            }
            val resourceTitle = ::gen_resourceTitle_fn
            fun gen_refreshStorageKey_fn(): String {
                return "refresh:pages:products:config-model:" + resource.value
            }
            val refreshStorageKey = ::gen_refreshStorageKey_fn
            fun gen_openForm_fn(id: String) {
                var url = "/pages/products/config-model/from?resource=" + resource.value
                if (resource.value == "attribute-value" && parentAttributeTypeId.value != "") {
                    url = url + "&attribute_type=" + parentAttributeTypeId.value + "&attribute_type_name=" + parentAttributeTypeName.value
                }
                if (id != "") {
                    url = url + "&id=" + id
                }
                uni_navigateTo(NavigateToOptions(url = url))
            }
            val openForm = ::gen_openForm_fn
            fun gen_parseErrorMessage_fn(error: Any, fallback: String): String {
                var message = fallback
                if (error != null) {
                    val directMessage = (error as UTSError).message
                    if (directMessage != null && directMessage != "") {
                        message = directMessage
                    }
                }
                return message
            }
            val parseErrorMessage = ::gen_parseErrorMessage_fn
            fun gen_applyListResponse_fn(response: UTSJSONObject) {
                val rawResults = response["results"]
                if (rawResults == null) {
                    items.value = _uA<UTSJSONObject>()
                } else {
                    items.value = rawResults as UTSArray<UTSJSONObject>
                }
                totalCount.value = getNumberField(response, "total_count")
                totalPages.value = getNumberField(response, "total_pages")
                currentPage.value = getNumberField(response, "current_page")
                pageSize.value = getNumberField(response, "page_size")
                if (totalPages.value <= 0) {
                    totalPages.value = 1
                }
                if (currentPage.value <= 0) {
                    currentPage.value = 1
                }
                if (pageSize.value <= 0) {
                    pageSize.value = 20
                }
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
                            val extra: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("extra", "pages/products/config-model/index.uvue", 237, 9))
                            if (resource.value == "attribute-value" && parentAttributeTypeId.value != "") {
                                extra["attribute_type"] = parentAttributeTypeId.value
                            }
                            if (resource.value == "discount") {
                                if (statusFilter.value != null && statusFilter.value != "") {
                                    extra["status"] = statusFilter.value
                                }
                                if (discountTypeFilter.value != null && discountTypeFilter.value != "") {
                                    extra["discount_type"] = discountTypeFilter.value
                                }
                            }
                            val response = await(getProductConfigList(resourceBasePath(), if (keyword.value == "") {
                                null
                            } else {
                                keyword.value
                            }
                            , currentPage.value, pageSize.value, extra))
                            applyListResponse(response)
                        }
                         catch (error: Throwable) {
                            items.value = _uA<UTSJSONObject>()
                            totalCount.value = 0
                            totalPages.value = 1
                            currentPage.value = 1
                            errorMessage.value = parseErrorMessage(error, resourceTitle() + "加载失败")
                        }
                         finally {
                            isLoading.value = false
                        }
                })
            }
            val loadItems = ::gen_loadItems_fn
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
            fun gen_discountName_fn(item: UTSJSONObject): String {
                return getStringField(item, "name", "未命名折扣")
            }
            val discountName = ::gen_discountName_fn
            fun gen_itemToListItem_fn(item: UTSJSONObject): UTSJSONObject {
                if (resource.value == "discount") {
                    return _uO("id" to getStringField(item, "id"), "rawId" to getStringField(item, "id"), "name" to discountName(item), "subtitle" to getStringField(item, "discount_display", "-"), "meta" to getStringField(item, "status", "-"), "min_quantity" to getStringField(item, "min_quantity", "-"), "priority" to getStringField(item, "priority", "-"), "updated" to formatDateText(getStringField(item, "updated_at")))
                }
                if (resource.value == "attribute-value") {
                    return _uO("id" to getStringField(item, "id"), "rawId" to getStringField(item, "id"), "name" to getStringField(item, "value", "未命名属性值"), "subtitle" to ("编码：" + getStringField(item, "code", "-")), "meta" to getStringField(item, "attribute_type_name", "-"), "value_en" to getStringField(item, "value_en", "-"), "color_hex" to getStringField(item, "color_hex", "-"), "display_order" to getStringField(item, "display_order", "0"))
                }
                if (resource.value == "barcode-sequence") {
                    return _uO("id" to getStringField(item, "id"), "rawId" to getStringField(item, "id"), "name" to getStringField(item, "sequence_name", "未命名序列"), "subtitle" to ("前缀：" + getStringField(item, "prefix", "-")), "meta" to ("下一个：" + getStringField(item, "current_number", "0")), "min_number" to getStringField(item, "min_number", "-"), "max_number" to getStringField(item, "max_number", "-"), "description" to getStringField(item, "description", "-"))
                }
                return _uO("id" to getStringField(item, "id"), "rawId" to getStringField(item, "id"), "name" to getStringField(item, "name", "未命名属性类型"), "subtitle" to ("编码：" + getStringField(item, "code", "-")), "meta" to ("值数量：" + getStringField(item, "values_count", "0")), "name_en" to getStringField(item, "name_en", "-"), "display_order" to getStringField(item, "display_order", "0"), "description" to getStringField(item, "description", "-"))
            }
            val itemToListItem = ::gen_itemToListItem_fn
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
            fun gen_handleFilterVisibleChange_fn(value: Boolean) {
                filterVisible.value = value
            }
            val handleFilterVisibleChange = ::gen_handleFilterVisibleChange_fn
            fun gen_handleFilterOpen_fn() {
                selectedStatus.value = statusFilter.value
                selectedDiscountType.value = discountTypeFilter.value
            }
            val handleFilterOpen = ::gen_handleFilterOpen_fn
            fun gen_selectStatus_fn(value: String?) {
                selectedStatus.value = value
            }
            val selectStatus = ::gen_selectStatus_fn
            fun gen_selectDiscountType_fn(value: String?) {
                selectedDiscountType.value = value
            }
            val selectDiscountType = ::gen_selectDiscountType_fn
            fun gen_handleFilterReset_fn() {
                selectedStatus.value = null
                selectedDiscountType.value = null
                statusFilter.value = null
                discountTypeFilter.value = null
                currentPage.value = 1
                filterVisible.value = false
                loadItems()
            }
            val handleFilterReset = ::gen_handleFilterReset_fn
            fun gen_applyFilter_fn() {
                statusFilter.value = selectedStatus.value
                discountTypeFilter.value = selectedDiscountType.value
                currentPage.value = 1
                filterVisible.value = false
                loadItems()
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
                loadItems()
            }
            val handlePageChange = ::gen_handlePageChange_fn
            fun gen_openAttributeValues_fn(item: UTSJSONObject) {
                val typeId = getStringField(item, "rawId")
                val typeName = getStringField(item, "name")
                if (typeId == "") {
                    return
                }
                uni_navigateTo(NavigateToOptions(url = "/pages/products/config-model/index?resource=attribute-value&attribute_type=" + typeId + "&attribute_type_name=" + typeName))
            }
            val openAttributeValues = ::gen_openAttributeValues_fn
            fun gen_handleItemClick_fn(payload: UTSJSONObject) {
                val item = payload["item"]
                if (item == null) {
                    return
                }
                val itemObject = item as UTSJSONObject
                if (resource.value == "attribute-type") {
                    openAttributeValues(itemObject)
                    return
                }
                openForm(getStringField(itemObject, "rawId"))
            }
            val handleItemClick = ::gen_handleItemClick_fn
            fun gen_handleFieldClick_fn(payload: UTSJSONObject) {}
            val handleFieldClick = ::gen_handleFieldClick_fn
            fun gen_confirmDelete_fn(id: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        try {
                            await(deleteProductConfig(resourceBasePath(), id))
                            uni_showToast(ShowToastOptions(title = takeLatestResponseMessage("删除成功"), icon = "success"))
                            loadItems()
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error, "删除失败"), icon = "none"))
                        }
                })
            }
            val confirmDelete = ::gen_confirmDelete_fn
            fun gen_handleMenu_fn(payload: UTSJSONObject) {
                val action = payload["action"]
                val item = payload["item"]
                if (action == null || item == null) {
                    return
                }
                val key = getStringField(action as UTSJSONObject, "key")
                val itemObject = item as UTSJSONObject
                val id = getStringField(itemObject, "rawId")
                if (key == "values") {
                    openAttributeValues(itemObject)
                    return
                }
                if (key == "edit") {
                    openForm(id)
                    return
                }
                if (key == "delete") {
                    uni_showModal(ShowModalOptions(title = "删除" + resourceTitle(), content = "确定删除这条配置吗？", success = fun(res){
                        if (res.confirm) {
                            confirmDelete(id)
                        }
                    }
                    ))
                    return
                }
                if (key == "reload") {
                    loadItems()
                }
            }
            val handleMenu = ::gen_handleMenu_fn
            fun gen_handleCreate_fn() {
                openForm("")
            }
            val handleCreate = ::gen_handleCreate_fn
            fun gen_consumeRefreshNeeded_fn(): Boolean {
                val storedValue = uni_getStorageSync(refreshStorageKey())
                if (storedValue == null) {
                    return false
                }
                val storedText = "" + storedValue
                if (storedText == "") {
                    return false
                }
                uni_removeStorageSync(refreshStorageKey())
                return true
            }
            val consumeRefreshNeeded = ::gen_consumeRefreshNeeded_fn
            val pageTitle = computed(fun(): String {
                return resourceTitle()
            }
            )
            val searchPlaceholder = computed(fun(): String {
                return "搜索" + resourceTitle()
            }
            )
            val showFilter = computed(fun(): Boolean {
                return resource.value == "discount"
            }
            )
            val hasActiveFilter = computed(fun(): Boolean {
                return statusFilter.value != null || discountTypeFilter.value != null
            }
            )
            val loadingText = computed(fun(): String {
                return "正在加载" + resourceTitle()
            }
            )
            val inlineLoadingText = computed(fun(): String {
                return resourceTitle() + "刷新中..."
            }
            )
            val emptyText = computed(fun(): String {
                if (isLoading.value) {
                    return "正在加载"
                }
                if (keyword.value != "") {
                    return "没有匹配的" + resourceTitle()
                }
                return "暂无" + resourceTitle()
            }
            )
            val fieldConfig = computed(fun(): UTSArray<UTSJSONObject> {
                if (resource.value == "discount") {
                    return _uA(
                        _uO("key" to "min_quantity", "label" to "最低数量"),
                        _uO("key" to "priority", "label" to "优先级"),
                        _uO("key" to "updated", "label" to "更新")
                    )
                }
                if (resource.value == "attribute-value") {
                    return _uA(
                        _uO("key" to "value_en", "label" to "英文"),
                        _uO("key" to "color_hex", "label" to "颜色"),
                        _uO("key" to "display_order", "label" to "排序")
                    )
                }
                if (resource.value == "barcode-sequence") {
                    return _uA(
                        _uO("key" to "min_number", "label" to "最小"),
                        _uO("key" to "max_number", "label" to "最大"),
                        _uO("key" to "description", "label" to "说明")
                    )
                }
                return _uA(
                    _uO("key" to "name_en", "label" to "英文"),
                    _uO("key" to "display_order", "label" to "排序"),
                    _uO("key" to "description", "label" to "说明")
                )
            }
            )
            val listItems = computed(fun(): UTSArray<UTSJSONObject> {
                val result: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < items.value.length){
                        result.push(itemToListItem(items.value[index]))
                        index += 1
                    }
                }
                return result
            }
            )
            onLoad(fun(event: OnLoadOptions){
                val resourceValue = event["resource"]
                resource.value = if (resourceValue == null) {
                    "discount"
                } else {
                    (resourceValue as String)
                }
                val attributeTypeValue = event["attribute_type"]
                parentAttributeTypeId.value = if (attributeTypeValue == null) {
                    ""
                } else {
                    (attributeTypeValue as String)
                }
                val attributeTypeNameValue = event["attribute_type_name"]
                parentAttributeTypeName.value = if (attributeTypeNameValue == null) {
                    ""
                } else {
                    (attributeTypeNameValue as String)
                }
                loadItems()
            }
            )
            onShow(fun(){
                if (consumeRefreshNeeded()) {
                    loadItems()
                }
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList", GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalListClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to pageTitle.value, "searchPlaceholder" to searchPlaceholder.value, "searchValue" to unref(keyword), "filterVisible" to unref(filterVisible), "showBack" to true, "showSearch" to true, "showFilter" to showFilter.value, "showHome" to true, "filterActive" to hasActiveFilter.value, "filterText" to "重置", "homePath" to "/pages/tabbar/settings", "onSearchInput" to handleSearchInput, "onSearchConfirm" to handleSearchConfirm, "onSearchClear" to handleSearchClear, "onUpdate:filterVisible" to handleFilterVisibleChange, "onFilterOpen" to handleFilterOpen), _uM("filter-panel" to withSlotCtx(fun(): UTSArray<Any> {
                        return _uA(
                            _cE("view", _uM("class" to "config-filter-panel"), _uA(
                                _cE("view", _uM("class" to "config-filter-actions"), _uA(
                                    _cE("view", _uM("class" to "config-filter-btn config-filter-btn-light", "onClick" to handleFilterReset), _uA(
                                        _cE("text", _uM("class" to "config-filter-btn-light-text"), "重置")
                                    )),
                                    _cE("view", _uM("class" to "config-filter-btn config-filter-btn-primary", "onClick" to applyFilter), _uA(
                                        _cE("text", _uM("class" to "config-filter-btn-primary-text"), "应用")
                                    ))
                                )),
                                _cE("view", _uM("class" to "config-filter-group"), _uA(
                                    _cE("text", _uM("class" to "config-filter-title"), "折扣状态"),
                                    _cE("view", _uM("class" to "config-filter-options"), _uA(
                                        _cE("view", _uM("class" to _nC(if (unref(selectedStatus) == null) {
                                            "config-filter-option config-filter-option-active"
                                        } else {
                                            "config-filter-option"
                                        }
                                        ), "onClick" to fun(){
                                            selectStatus(null)
                                        }
                                        ), _uA(
                                            _cE("text", _uM("class" to _nC(if (unref(selectedStatus) == null) {
                                                "config-filter-option-text config-filter-option-text-active"
                                            } else {
                                                "config-filter-option-text"
                                            }
                                            )), "全部", 2)
                                        ), 10, _uA(
                                            "onClick"
                                        )),
                                        _cE("view", _uM("class" to _nC(if (unref(selectedStatus) == "DRAFT") {
                                            "config-filter-option config-filter-option-active"
                                        } else {
                                            "config-filter-option"
                                        }
                                        ), "onClick" to fun(){
                                            selectStatus("DRAFT")
                                        }
                                        ), _uA(
                                            _cE("text", _uM("class" to _nC(if (unref(selectedStatus) == "DRAFT") {
                                                "config-filter-option-text config-filter-option-text-active"
                                            } else {
                                                "config-filter-option-text"
                                            }
                                            )), "草稿", 2)
                                        ), 10, _uA(
                                            "onClick"
                                        )),
                                        _cE("view", _uM("class" to _nC(if (unref(selectedStatus) == "ACTIVE") {
                                            "config-filter-option config-filter-option-active"
                                        } else {
                                            "config-filter-option"
                                        }
                                        ), "onClick" to fun(){
                                            selectStatus("ACTIVE")
                                        }
                                        ), _uA(
                                            _cE("text", _uM("class" to _nC(if (unref(selectedStatus) == "ACTIVE") {
                                                "config-filter-option-text config-filter-option-text-active"
                                            } else {
                                                "config-filter-option-text"
                                            }
                                            )), "启用", 2)
                                        ), 10, _uA(
                                            "onClick"
                                        )),
                                        _cE("view", _uM("class" to _nC(if (unref(selectedStatus) == "INACTIVE") {
                                            "config-filter-option config-filter-option-active"
                                        } else {
                                            "config-filter-option"
                                        }
                                        ), "onClick" to fun(){
                                            selectStatus("INACTIVE")
                                        }
                                        ), _uA(
                                            _cE("text", _uM("class" to _nC(if (unref(selectedStatus) == "INACTIVE") {
                                                "config-filter-option-text config-filter-option-text-active"
                                            } else {
                                                "config-filter-option-text"
                                            }
                                            )), "停用", 2)
                                        ), 10, _uA(
                                            "onClick"
                                        )),
                                        _cE("view", _uM("class" to _nC(if (unref(selectedStatus) == "EXPIRED") {
                                            "config-filter-option config-filter-option-active"
                                        } else {
                                            "config-filter-option"
                                        }
                                        ), "onClick" to fun(){
                                            selectStatus("EXPIRED")
                                        }
                                        ), _uA(
                                            _cE("text", _uM("class" to _nC(if (unref(selectedStatus) == "EXPIRED") {
                                                "config-filter-option-text config-filter-option-text-active"
                                            } else {
                                                "config-filter-option-text"
                                            }
                                            )), "过期", 2)
                                        ), 10, _uA(
                                            "onClick"
                                        ))
                                    ))
                                )),
                                _cE("view", _uM("class" to "config-filter-group"), _uA(
                                    _cE("text", _uM("class" to "config-filter-title"), "折扣类型"),
                                    _cE("view", _uM("class" to "config-filter-options"), _uA(
                                        _cE("view", _uM("class" to _nC(if (unref(selectedDiscountType) == null) {
                                            "config-filter-option config-filter-option-active"
                                        } else {
                                            "config-filter-option"
                                        }
                                        ), "onClick" to fun(){
                                            selectDiscountType(null)
                                        }
                                        ), _uA(
                                            _cE("text", _uM("class" to _nC(if (unref(selectedDiscountType) == null) {
                                                "config-filter-option-text config-filter-option-text-active"
                                            } else {
                                                "config-filter-option-text"
                                            }
                                            )), "全部", 2)
                                        ), 10, _uA(
                                            "onClick"
                                        )),
                                        _cE("view", _uM("class" to _nC(if (unref(selectedDiscountType) == "PERCENTAGE") {
                                            "config-filter-option config-filter-option-active"
                                        } else {
                                            "config-filter-option"
                                        }
                                        ), "onClick" to fun(){
                                            selectDiscountType("PERCENTAGE")
                                        }
                                        ), _uA(
                                            _cE("text", _uM("class" to _nC(if (unref(selectedDiscountType) == "PERCENTAGE") {
                                                "config-filter-option-text config-filter-option-text-active"
                                            } else {
                                                "config-filter-option-text"
                                            }
                                            )), "百分比", 2)
                                        ), 10, _uA(
                                            "onClick"
                                        )),
                                        _cE("view", _uM("class" to _nC(if (unref(selectedDiscountType) == "FIXED_AMOUNT") {
                                            "config-filter-option config-filter-option-active"
                                        } else {
                                            "config-filter-option"
                                        }
                                        ), "onClick" to fun(){
                                            selectDiscountType("FIXED_AMOUNT")
                                        }
                                        ), _uA(
                                            _cE("text", _uM("class" to _nC(if (unref(selectedDiscountType) == "FIXED_AMOUNT") {
                                                "config-filter-option-text config-filter-option-text-active"
                                            } else {
                                                "config-filter-option-text"
                                            }
                                            )), "固定金额", 2)
                                        ), 10, _uA(
                                            "onClick"
                                        ))
                                    ))
                                ))
                            ))
                        )
                    }
                    ), "_" to 1), 8, _uA(
                        "title",
                        "searchPlaceholder",
                        "searchValue",
                        "filterVisible",
                        "showFilter",
                        "filterActive"
                    )),
                    _cE("scroll-view", _uM("class" to "page-scroll", "scroll-y" to "true"), _uA(
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
                            _cV(_component_lili_UniversalList, _uM("items" to listItems.value, "keyField" to "id", "titleField" to "name", "subtitleField" to "subtitle", "metaField" to "meta", "fields" to fieldConfig.value, "loading" to unref(isLoading), "loadingText" to loadingText.value, "keepContentOnLoading" to true, "inlineLoadingText" to inlineLoadingText.value, "emptyText" to emptyText.value, "emptyIcon" to "◎", "showMenu" to true, "menuActions" to menuActions.value, "showChevron" to false, "showPagination" to true, "currentPage" to unref(currentPage), "totalPages" to unref(totalPages), "totalCount" to unref(totalCount), "showFloatingAdd" to true, "floatingAddText" to "新增", "onItemClick" to handleItemClick, "onFieldClick" to handleFieldClick, "onMenu" to handleMenu, "onPageChange" to handlePageChange, "onFloatingAdd" to handleCreate), null, 8, _uA(
                                "items",
                                "fields",
                                "loading",
                                "loadingText",
                                "inlineLoadingText",
                                "emptyText",
                                "menuActions",
                                "currentPage",
                                "totalPages",
                                "totalCount"
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-scroll" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F6F7FB")), "page-content" to _pS(_uM("paddingLeft" to 6, "paddingRight" to 6, "paddingTop" to 6, "paddingBottom" to 96)), "config-filter-panel" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#FFFFFF", "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 14, "paddingLeft" to 14)), "config-filter-actions" to _pS(_uM("flexDirection" to "row", "justifyContent" to "flex-end", "marginBottom" to 12)), "config-filter-btn" to _pS(_uM("height" to 38, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingLeft" to 18, "paddingRight" to 18, "alignItems" to "center", "justifyContent" to "center", "marginLeft" to 8)), "config-filter-btn-light" to _pS(_uM("backgroundColor" to "#F8FAFC", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#CBD5E1", "borderRightColor" to "#CBD5E1", "borderBottomColor" to "#CBD5E1", "borderLeftColor" to "#CBD5E1")), "config-filter-btn-primary" to _pS(_uM("backgroundColor" to "#0F172A")), "config-filter-btn-light-text" to _pS(_uM("fontSize" to 14, "color" to "#334155")), "config-filter-btn-primary-text" to _pS(_uM("fontSize" to 14, "color" to "#FFFFFF")), "config-filter-group" to _pS(_uM("marginTop" to 10)), "config-filter-title" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#0F172A", "fontWeight" to "bold")), "config-filter-options" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 10)), "config-filter-option" to _pS(_uM("height" to 34, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingLeft" to 12, "paddingRight" to 12, "backgroundColor" to "#F8FAFC", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "alignItems" to "center", "justifyContent" to "center", "marginRight" to 8, "marginBottom" to 8)), "config-filter-option-active" to _pS(_uM("backgroundColor" to "#0F172A", "borderTopColor" to "#0F172A", "borderRightColor" to "#0F172A", "borderBottomColor" to "#0F172A", "borderLeftColor" to "#0F172A")), "config-filter-option-text" to _pS(_uM("fontSize" to 13, "color" to "#475569")), "config-filter-option-text-active" to _pS(_uM("color" to "#FFFFFF")), "error-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "paddingTop" to 18, "paddingRight" to 18, "paddingBottom" to 18, "paddingLeft" to 18, "marginBottom" to 14, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FECACA", "borderRightColor" to "#FECACA", "borderBottomColor" to "#FECACA", "borderLeftColor" to "#FECACA", "alignItems" to "center")), "error-title" to _pS(_uM("fontSize" to 18, "lineHeight" to "24px", "color" to "#B42318", "fontWeight" to "bold")), "error-desc" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#7F1D1D", "marginTop" to 8, "textAlign" to "center")), "retry-btn" to _pS(_uM("marginTop" to 14, "height" to 40, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#0F172A", "paddingLeft" to 18, "paddingRight" to 18, "alignItems" to "center", "justifyContent" to "center")), "retry-btn-text" to _pS(_uM("fontSize" to 14, "lineHeight" to "14px", "color" to "#FFFFFF")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
