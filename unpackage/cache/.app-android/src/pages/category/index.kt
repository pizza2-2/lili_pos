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
import io.dcloud.uniapp.extapi.showActionSheet as uni_showActionSheet
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesCategoryIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesCategoryIndex) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesCategoryIndex
            val _cache = __ins.renderCache
            val categoryListRefreshStorageKey = "refresh:pages:category:index"
            val categoryListRefreshPayloadStorageKey = "refresh:pages:category:index:payload"
            val keyword = ref("")
            val filterVisible = ref(false)
            val isLoading = ref(false)
            val isOperating = ref(false)
            val pageError = ref("")
            val rootCategories = ref(_uA<CategoryItem>())
            val childGroups = ref(_uA<ChildGroup>())
            val expandedNodeIds = ref(_uA<String>())
            val loadingNodeIds = ref(_uA<String>())
            val loadedNodeIds = ref(_uA<String>())
            val selectedLevel = ref("")
            val selectedStatus = ref("")
            val selectedOrdering = ref("name")
            val searchTimer = ref(0)
            val levelFilterOptions = ref(_uA<FilterOption>(FilterOption(value = "", text = "全部层级"), FilterOption(value = "1", text = "一级分类"), FilterOption(value = "2", text = "二级分类"), FilterOption(value = "3", text = "三级分类")))
            val statusFilterOptions = ref(_uA<FilterOption>(FilterOption(value = "", text = "全部状态"), FilterOption(value = "branch", text = "分支节点"), FilterOption(value = "leaf", text = "叶子节点")))
            val orderingFilterOptions = ref(_uA<FilterOption>(FilterOption(value = "name", text = "按名称"), FilterOption(value = "level", text = "按层级"), FilterOption(value = "id", text = "按编号")))
            fun gen_parseErrorMessage_fn(error: Any): String {
                var message = "分类列表加载失败"
                if (error != null) {
                    val directMessage = (error as UTSError).message
                    if (directMessage != null && directMessage != "") {
                        message = directMessage
                    }
                    val errorText = JSON.stringify(error)
                    if (errorText != null && errorText != "") {
                        val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/category/index.uvue:226")
                        if (parsedError != null) {
                            val rawMessage = parsedError["message"]
                            if (rawMessage != null) {
                                val parsedMessage = rawMessage as String
                                if (parsedMessage != "") {
                                    message = parsedMessage
                                }
                            }
                        }
                        if (message == "分类列表加载失败") {
                            message = errorText
                        }
                    }
                }
                return message
            }
            val parseErrorMessage = ::gen_parseErrorMessage_fn
            fun gen_nodeIdText_fn(id: Number): String {
                return id.toString(10)
            }
            val nodeIdText = ::gen_nodeIdText_fn
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
            fun intValue(value: Any?, fallback: Number = 0): Number {
                if (value == null) {
                    return fallback
                }
                val text = "" + value
                if (text == "") {
                    return fallback
                }
                val parsed = parseInt(text)
                if (isNaN(parsed)) {
                    return fallback
                }
                return parsed
            }
            fun booleanValue(value: Any?, fallback: Boolean = false): Boolean {
                if (value == null) {
                    return fallback
                }
                val text = ("" + value).toLowerCase()
                return text == "true" || text == "1" || text == "yes"
            }
            fun gen_parseStoredObject_fn(value: Any?): UTSJSONObject? {
                if (value == null) {
                    return null
                }
                val text = "" + value
                if (text == "") {
                    return null
                }
                return UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(text), " at pages/category/index.uvue:287")
            }
            val parseStoredObject = ::gen_parseStoredObject_fn
            fun gen_containsNodeId_fn(list: UTSArray<String>, id: Number): Boolean {
                return list.includes(nodeIdText(id))
            }
            val containsNodeId = ::gen_containsNodeId_fn
            fun gen_removeNodeId_fn(list: UTSArray<String>, id: Number): UTSArray<String> {
                val targetId = nodeIdText(id)
                val nextIds: UTSArray<String> = _uA()
                run {
                    var index: Number = 0
                    while(index < list.length){
                        if (list[index] != targetId) {
                            nextIds.push(list[index])
                        }
                        index += 1
                    }
                }
                return nextIds
            }
            val removeNodeId = ::gen_removeNodeId_fn
            fun gen_clearTreeCache_fn(): Unit {
                childGroups.value = _uA<ChildGroup>()
                loadingNodeIds.value = _uA<String>()
                loadedNodeIds.value = _uA<String>()
            }
            val clearTreeCache = ::gen_clearTreeCache_fn
            fun gen_findChildGroupIndex_fn(parentId: Number): Number {
                run {
                    var index: Number = 0
                    while(index < childGroups.value.length){
                        if (childGroups.value[index].parentId == parentId) {
                            return index
                        }
                        index += 1
                    }
                }
                return -1
            }
            val findChildGroupIndex = ::gen_findChildGroupIndex_fn
            fun gen_getChildren_fn(parentId: Number): UTSArray<CategoryItem> {
                val groupIndex = findChildGroupIndex(parentId)
                if (groupIndex < 0) {
                    return _uA<CategoryItem>()
                }
                return childGroups.value[groupIndex].items
            }
            val getChildren = ::gen_getChildren_fn
            fun gen_setChildren_fn(parentId: Number, items: UTSArray<CategoryItem>): Unit {
                val nextItems: UTSArray<CategoryItem> = _uA()
                run {
                    var index: Number = 0
                    while(index < items.length){
                        val item = items[index]
                        if (item.level <= 3) {
                            nextItems.push(item)
                        }
                        index += 1
                    }
                }
                val groupIndex = findChildGroupIndex(parentId)
                if (groupIndex >= 0) {
                    childGroups.value[groupIndex].items = nextItems
                    return
                }
                childGroups.value.push(ChildGroup(parentId = parentId, items = nextItems))
            }
            val setChildren = ::gen_setChildren_fn
            fun gen_isExpanded_fn(id: Number): Boolean {
                return containsNodeId(expandedNodeIds.value, id)
            }
            val isExpanded = ::gen_isExpanded_fn
            fun gen_isNodeLoading_fn(id: Number): Boolean {
                return containsNodeId(loadingNodeIds.value, id)
            }
            val isNodeLoading = ::gen_isNodeLoading_fn
            fun gen_isNodeLoaded_fn(id: Number): Boolean {
                return containsNodeId(loadedNodeIds.value, id)
            }
            val isNodeLoaded = ::gen_isNodeLoaded_fn
            fun gen_canExpand_fn(item: CategoryItem): Boolean {
                if (item.level >= 2) {
                    return false
                }
                if (item.is_leaf) {
                    return false
                }
                return item.children_count > 0 || !item.is_leaf
            }
            val canExpand = ::gen_canExpand_fn
            fun gen_clearLegacyExpandedState_fn(): Unit {
                try {
                    uni_removeStorageSync("pages:category:index:expanded")
                }
                 catch (error: Throwable) {}
            }
            val clearLegacyExpandedState = ::gen_clearLegacyExpandedState_fn
            fun gen_buildRootsQuery_fn(): CategoryRootsQuery {
                return CategoryRootsQuery(search = if (keyword.value == "") {
                    null
                } else {
                    keyword.value
                }
                , level = if (selectedLevel.value == "") {
                    null
                } else {
                    selectedLevel.value
                }
                , status = if (selectedStatus.value == "") {
                    null
                } else {
                    selectedStatus.value
                }
                , ordering = if (selectedOrdering.value == "") {
                    null
                } else {
                    selectedOrdering.value
                }
                )
            }
            val buildRootsQuery = ::gen_buildRootsQuery_fn
            fun gen_ensureChildrenLoaded_fn(item: CategoryItem): UTSPromise<Boolean> {
                return wrapUTSPromise(suspend w1@{
                        if (!canExpand(item)) {
                            return@w1 false
                        }
                        if (isNodeLoaded(item.id)) {
                            return@w1 true
                        }
                        if (isNodeLoading(item.id)) {
                            return@w1 false
                        }
                        if (!containsNodeId(loadingNodeIds.value, item.id)) {
                            loadingNodeIds.value.push(nodeIdText(item.id))
                        }
                        try {
                            val children = await(getCategoryChildren(item.id, if (keyword.value == "") {
                                null
                            } else {
                                keyword.value
                            }
                            ))
                            setChildren(item.id, children)
                            if (!containsNodeId(loadedNodeIds.value, item.id)) {
                                loadedNodeIds.value.push(nodeIdText(item.id))
                            }
                            return@w1 true
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error), icon = "none"))
                            return@w1 false
                        }
                         finally {
                            loadingNodeIds.value = removeNodeId(loadingNodeIds.value, item.id)
                        }
                })
            }
            val ensureChildrenLoaded = ::gen_ensureChildrenLoaded_fn
            fun gen_restoreExpandedBranches_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        run {
                            var index: Number = 0
                            while(index < rootCategories.value.length){
                                val root = rootCategories.value[index]
                                if (!isExpanded(root.id)) {
                                    index += 1
                                    continue
                                }
                                val loadedRootChildren = await(ensureChildrenLoaded(root))
                                if (!loadedRootChildren) {
                                    expandedNodeIds.value = removeNodeId(expandedNodeIds.value, root.id)
                                    index += 1
                                    continue
                                }
                                val secondLevelChildren = getChildren(root.id)
                                run {
                                    var childIndex: Number = 0
                                    while(childIndex < secondLevelChildren.length){
                                        val second = secondLevelChildren[childIndex]
                                        if (!isExpanded(second.id)) {
                                            childIndex += 1
                                            continue
                                        }
                                        val loadedSecondChildren = await(ensureChildrenLoaded(second))
                                        if (!loadedSecondChildren) {
                                            expandedNodeIds.value = removeNodeId(expandedNodeIds.value, second.id)
                                        }
                                        childIndex += 1
                                    }
                                }
                                index += 1
                            }
                        }
                })
            }
            val restoreExpandedBranches = ::gen_restoreExpandedBranches_fn
            fun gen_loadRootCategories_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (isLoading.value) {
                            return@w1
                        }
                        isLoading.value = true
                        pageError.value = ""
                        clearTreeCache()
                        try {
                            val roots = await(getCategoryRoots(buildRootsQuery()))
                            val nextRoots: UTSArray<CategoryItem> = _uA()
                            run {
                                var index: Number = 0
                                while(index < roots.length){
                                    val item = roots[index]
                                    if (item.level <= 3) {
                                        nextRoots.push(item)
                                    }
                                    index += 1
                                }
                            }
                            rootCategories.value = nextRoots
                            await(restoreExpandedBranches())
                        }
                         catch (error: Throwable) {
                            rootCategories.value = _uA<CategoryItem>()
                            pageError.value = parseErrorMessage(error)
                        }
                         finally {
                            isLoading.value = false
                        }
                })
            }
            val loadRootCategories = ::gen_loadRootCategories_fn
            fun gen_closeFilterDrawer_fn(): Unit {
                filterVisible.value = false
            }
            val closeFilterDrawer = ::gen_closeFilterDrawer_fn
            fun gen_handleFilterVisibleChange_fn(value: Any): Unit {
                filterVisible.value = value as Boolean
            }
            val handleFilterVisibleChange = ::gen_handleFilterVisibleChange_fn
            fun gen_toggleLevelFilter_fn(value: String): Unit {
                selectedLevel.value = if (selectedLevel.value == value) {
                    ""
                } else {
                    value
                }
            }
            val toggleLevelFilter = ::gen_toggleLevelFilter_fn
            fun gen_toggleStatusFilter_fn(value: String): Unit {
                selectedStatus.value = if (selectedStatus.value == value) {
                    ""
                } else {
                    value
                }
            }
            val toggleStatusFilter = ::gen_toggleStatusFilter_fn
            fun gen_toggleOrderingFilter_fn(value: String): Unit {
                selectedOrdering.value = value
            }
            val toggleOrderingFilter = ::gen_toggleOrderingFilter_fn
            fun gen_applyFilters_fn(): Unit {
                closeFilterDrawer()
                loadRootCategories()
            }
            val applyFilters = ::gen_applyFilters_fn
            fun gen_handleFilterReset_fn(): Unit {
                keyword.value = ""
                selectedLevel.value = ""
                selectedStatus.value = ""
                selectedOrdering.value = "name"
                closeFilterDrawer()
                loadRootCategories()
            }
            val handleFilterReset = ::gen_handleFilterReset_fn
            fun gen_startSearchReload_fn(): Unit {
                if (searchTimer.value > 0) {
                    clearTimeout(searchTimer.value)
                }
                searchTimer.value = setTimeout(fun(){
                    loadRootCategories()
                }
                , 400)
            }
            val startSearchReload = ::gen_startSearchReload_fn
            fun gen_handleSearchInput_fn(value: Any): Unit {
                keyword.value = value as String
                startSearchReload()
            }
            val handleSearchInput = ::gen_handleSearchInput_fn
            fun handleSearchConfirm(value: Any? = null): Unit {
                if (value != null) {
                    keyword.value = value as String
                }
                if (searchTimer.value > 0) {
                    clearTimeout(searchTimer.value)
                    searchTimer.value = 0
                }
                loadRootCategories()
            }
            fun gen_handleSearchClear_fn(): Unit {
                keyword.value = ""
                if (searchTimer.value > 0) {
                    clearTimeout(searchTimer.value)
                    searchTimer.value = 0
                }
                loadRootCategories()
            }
            val handleSearchClear = ::gen_handleSearchClear_fn
            fun gen_pruneExpandedIdsAfterCollapse_fn(parentId: Number): Unit {
                val directChildren = getChildren(parentId)
                run {
                    var index: Number = 0
                    while(index < directChildren.length){
                        val child = directChildren[index]
                        expandedNodeIds.value = removeNodeId(expandedNodeIds.value, child.id)
                        index += 1
                    }
                }
            }
            val pruneExpandedIdsAfterCollapse = ::gen_pruneExpandedIdsAfterCollapse_fn
            fun gen_canAddChild_fn(item: CategoryItem): Boolean {
                return item.level < 2
            }
            val canAddChild = ::gen_canAddChild_fn
            fun gen_hasPendingQueryState_fn(): Boolean {
                return keyword.value != "" || selectedLevel.value != "" || selectedStatus.value != "" || selectedOrdering.value != "name"
            }
            val hasPendingQueryState = ::gen_hasPendingQueryState_fn
            fun gen_patchCategoryItem_fn(target: CategoryItem, payload: UTSJSONObject): Unit {
                target.name = stringValue(payload["name"], target.name)
                target.code = stringValue(payload["code"], target.code)
                target.level = intValue(payload["level"], target.level)
                target.parent_id = intValue(payload["parent_id"], target.parent_id)
                target.sort_order = intValue(payload["sort_order"], target.sort_order)
                target.tax_rate = stringValue(payload["tax_rate"], target.tax_rate)
                target.kasa_category_id = intValue(payload["kasa_category_id"], target.kasa_category_id)
                target.products_count = intValue(payload["products_count"], target.products_count)
                target.children_count = intValue(payload["children_count"], target.children_count)
                target.is_active = booleanValue(payload["is_active"], target.is_active)
                target.is_leaf = booleanValue(payload["is_leaf"], target.is_leaf)
                target.full_name = stringValue(payload["full_name"], target.full_name)
                target.path = stringValue(payload["path"], target.path)
                target.created_at = stringValue(payload["created_at"], target.created_at)
                target.updated_at = stringValue(payload["updated_at"], target.updated_at)
            }
            val patchCategoryItem = ::gen_patchCategoryItem_fn
            fun gen_patchCategoryNode_fn(payload: UTSJSONObject): Boolean {
                val targetId = intValue(payload["id"], 0)
                if (targetId <= 0) {
                    return false
                }
                run {
                    var index: Number = 0
                    while(index < rootCategories.value.length){
                        val item = rootCategories.value[index]
                        if (item.id != targetId) {
                            index += 1
                            continue
                        }
                        patchCategoryItem(item, payload)
                        return true
                        index += 1
                    }
                }
                run {
                    var groupIndex: Number = 0
                    while(groupIndex < childGroups.value.length){
                        val items = childGroups.value[groupIndex].items
                        run {
                            var itemIndex: Number = 0
                            while(itemIndex < items.length){
                                val item = items[itemIndex]
                                if (item.id != targetId) {
                                    itemIndex += 1
                                    continue
                                }
                                patchCategoryItem(item, payload)
                                return true
                                itemIndex += 1
                            }
                        }
                        groupIndex += 1
                    }
                }
                return false
            }
            val patchCategoryNode = ::gen_patchCategoryNode_fn
            fun gen_consumeRefreshState_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        var payload: UTSJSONObject? = null
                        try {
                            payload = parseStoredObject(uni_getStorageSync(categoryListRefreshPayloadStorageKey))
                            uni_removeStorageSync(categoryListRefreshPayloadStorageKey)
                            uni_removeStorageSync(categoryListRefreshStorageKey)
                        }
                         catch (error: Throwable) {
                            payload = null
                        }
                        if (payload == null) {
                            var legacyRefresh = ""
                            try {
                                legacyRefresh = stringValue(uni_getStorageSync(categoryListRefreshStorageKey))
                                uni_removeStorageSync(categoryListRefreshStorageKey)
                            }
                             catch (error: Throwable) {
                                legacyRefresh = ""
                            }
                            if (legacyRefresh == "1") {
                                await(loadRootCategories())
                            }
                            return@w1
                        }
                        val action = stringValue(payload["action"])
                        if (action == "") {
                            return@w1
                        }
                        if (action != "edit") {
                            await(loadRootCategories())
                            return@w1
                        }
                        if (hasPendingQueryState()) {
                            await(loadRootCategories())
                            return@w1
                        }
                        val previousParentId = stringValue(payload["previous_parent_id"])
                        val currentParentId = stringValue(payload["current_parent_id"])
                        if (previousParentId != currentParentId) {
                            await(loadRootCategories())
                            return@w1
                        }
                        val patched = patchCategoryNode(payload)
                        if (!patched) {
                            await(loadRootCategories())
                        }
                })
            }
            val consumeRefreshState = ::gen_consumeRefreshState_fn
            fun gen_goToEdit_fn(item: CategoryItem): Unit {
                uni_navigateTo(NavigateToOptions(url = "/pages/category/from?id=" + item.id + "&mode=edit"))
            }
            val goToEdit = ::gen_goToEdit_fn
            fun gen_goToAddChild_fn(item: CategoryItem): Unit {
                uni_navigateTo(NavigateToOptions(url = "/pages/category/from?parent_id=" + item.id + "&parent_name=" + UTSAndroid.consoleDebugError(encodeURIComponent(item.name), " at pages/category/index.uvue:659") + "&mode=add"))
            }
            val goToAddChild = ::gen_goToAddChild_fn
            fun gen_handleCreateRoot_fn(): Unit {
                uni_navigateTo(NavigateToOptions(url = "/pages/category/from?mode=create"))
            }
            val handleCreateRoot = ::gen_handleCreateRoot_fn
            fun gen_removeExpandedIdDeep_fn(id: Number): Unit {
                expandedNodeIds.value = removeNodeId(expandedNodeIds.value, id)
                val children = getChildren(id)
                run {
                    var index: Number = 0
                    while(index < children.length){
                        expandedNodeIds.value = removeNodeId(expandedNodeIds.value, children[index].id)
                        index += 1
                    }
                }
            }
            val removeExpandedIdDeep = ::gen_removeExpandedIdDeep_fn
            fun gen_executeDelete_fn(item: CategoryItem): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        try {
                            isOperating.value = true
                            await(deleteCategory(item.id))
                            removeExpandedIdDeep(item.id)
                            uni_showToast(ShowToastOptions(title = takeLatestResponseMessage("删除成功"), icon = "success"))
                            loadRootCategories()
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error), icon = "none"))
                        }
                         finally {
                            isOperating.value = false
                        }
                })
            }
            val executeDelete = ::gen_executeDelete_fn
            fun gen_confirmDelete_fn(item: CategoryItem): Unit {
                uni_showModal(ShowModalOptions(title = "确认删除", content = "确定删除分类「" + item.name + "」吗？此操作不可恢复。", confirmColor = "#DC2626", success = fun(res){
                    if (!res.confirm) {
                        return
                    }
                    executeDelete(item)
                }
                ))
            }
            val confirmDelete = ::gen_confirmDelete_fn
            fun gen_openActionSheet_fn(item: CategoryItem): Unit {
                val itemList: UTSArray<String> = _uA<String>()
                itemList.push("编辑分类")
                if (canAddChild(item)) {
                    itemList.push("新增子分类")
                }
                itemList.push("删除分类")
                uni_showActionSheet(ShowActionSheetOptions(itemList = itemList, success = fun(res){
                    val tapIndex = res.tapIndex
                    if (tapIndex == 0) {
                        goToEdit(item)
                        return
                    }
                    if (canAddChild(item) && tapIndex == 1) {
                        goToAddChild(item)
                        return
                    }
                    confirmDelete(item)
                }
                ))
            }
            val openActionSheet = ::gen_openActionSheet_fn
            fun gen_toggleNode_fn(item: CategoryItem): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (!canExpand(item)) {
                            openActionSheet(item)
                            return@w1
                        }
                        if (isExpanded(item.id)) {
                            expandedNodeIds.value = removeNodeId(expandedNodeIds.value, item.id)
                            if (item.level == 1) {
                                pruneExpandedIdsAfterCollapse(item.id)
                            }
                            return@w1
                        }
                        val loaded = await(ensureChildrenLoaded(item))
                        if (!loaded) {
                            return@w1
                        }
                        if (!containsNodeId(expandedNodeIds.value, item.id)) {
                            expandedNodeIds.value.push(nodeIdText(item.id))
                        }
                })
            }
            val toggleNode = ::gen_toggleNode_fn
            val hasActiveFilter = computed(fun(): Boolean {
                return selectedLevel.value != "" || selectedStatus.value != "" || selectedOrdering.value != "name"
            }
            )
            onLoad(fun(_options){
                clearLegacyExpandedState()
                loadRootCategories()
            }
            )
            onShow(fun(){
                consumeRefreshState()
            }
            )
            onUnmounted(fun(){
                if (searchTimer.value > 0) {
                    clearTimeout(searchTimer.value)
                }
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to "分类", "searchPlaceholder" to "输入分类名称", "searchValue" to unref(keyword), "filterVisible" to unref(filterVisible), "showBack" to true, "showSearch" to true, "showFilter" to true, "showHome" to true, "filterActive" to hasActiveFilter.value, "filterText" to "重置", "homePath" to "/pages/tabbar/settings", "onSearchInput" to fun(`$event`: Any){
                        handleSearchInput(`$event`)
                    }
                    , "onSearchConfirm" to fun(`$event`: Any){
                        handleSearchConfirm(`$event`)
                    }
                    , "onSearchClear" to fun(){
                        handleSearchClear()
                    }
                    , "onUpdate:filterVisible" to fun(`$event`: Any){
                        handleFilterVisibleChange(`$event`)
                    }
                    ), _uM("filter-panel" to withSlotCtx(fun(): UTSArray<Any> {
                        return _uA(
                            _cE("view", _uM("class" to "filter-panel"), _uA(
                                _cE("view", _uM("class" to "filter-actions"), _uA(
                                    _cE("view", _uM("class" to "filter-btn filter-btn-light", "onClick" to handleFilterReset), _uA(
                                        _cE("text", _uM("class" to "filter-btn-light-text"), "重置")
                                    )),
                                    _cE("view", _uM("class" to "filter-btn filter-btn-primary", "onClick" to applyFilters), _uA(
                                        _cE("text", _uM("class" to "filter-btn-primary-text"), "应用")
                                    ))
                                )),
                                _cE("scroll-view", _uM("scroll-y" to "true", "class" to "filter-scroll"), _uA(
                                    _cE("view", _uM("class" to "filter-group"), _uA(
                                        _cE("text", _uM("class" to "filter-group-title"), "分类层级"),
                                        _cE("view", _uM("class" to "filter-options"), _uA(
                                            _cE(Fragment, null, RenderHelpers.renderList(unref(levelFilterOptions), fun(option, __key, __index, _cached): Any {
                                                return _cE("view", _uM("key" to ("level-" + option.value), "class" to _nC(if (unref(selectedLevel) == option.value) {
                                                    "filter-option filter-option-active"
                                                } else {
                                                    "filter-option"
                                                }
                                                ), "onClick" to fun(){
                                                    toggleLevelFilter(option.value)
                                                }
                                                ), _uA(
                                                    _cE("text", _uM("class" to _nC(if (unref(selectedLevel) == option.value) {
                                                        "filter-option-text filter-option-text-active"
                                                    } else {
                                                        "filter-option-text"
                                                    }
                                                    )), _tD(option.text), 3)
                                                ), 10, _uA(
                                                    "onClick"
                                                ))
                                            }
                                            ), 128)
                                        ))
                                    )),
                                    _cE("view", _uM("class" to "filter-group"), _uA(
                                        _cE("text", _uM("class" to "filter-group-title"), "节点状态"),
                                        _cE("view", _uM("class" to "filter-options"), _uA(
                                            _cE(Fragment, null, RenderHelpers.renderList(unref(statusFilterOptions), fun(option, __key, __index, _cached): Any {
                                                return _cE("view", _uM("key" to ("status-" + option.value), "class" to _nC(if (unref(selectedStatus) == option.value) {
                                                    "filter-option filter-option-active"
                                                } else {
                                                    "filter-option"
                                                }
                                                ), "onClick" to fun(){
                                                    toggleStatusFilter(option.value)
                                                }
                                                ), _uA(
                                                    _cE("text", _uM("class" to _nC(if (unref(selectedStatus) == option.value) {
                                                        "filter-option-text filter-option-text-active"
                                                    } else {
                                                        "filter-option-text"
                                                    }
                                                    )), _tD(option.text), 3)
                                                ), 10, _uA(
                                                    "onClick"
                                                ))
                                            }
                                            ), 128)
                                        ))
                                    )),
                                    _cE("view", _uM("class" to "filter-group"), _uA(
                                        _cE("text", _uM("class" to "filter-group-title"), "排序方式"),
                                        _cE("view", _uM("class" to "filter-options"), _uA(
                                            _cE(Fragment, null, RenderHelpers.renderList(unref(orderingFilterOptions), fun(option, __key, __index, _cached): Any {
                                                return _cE("view", _uM("key" to ("ordering-" + option.value), "class" to _nC(if (unref(selectedOrdering) == option.value) {
                                                    "filter-option filter-option-active"
                                                } else {
                                                    "filter-option"
                                                }
                                                ), "onClick" to fun(){
                                                    toggleOrderingFilter(option.value)
                                                }
                                                ), _uA(
                                                    _cE("text", _uM("class" to _nC(if (unref(selectedOrdering) == option.value) {
                                                        "filter-option-text filter-option-text-active"
                                                    } else {
                                                        "filter-option-text"
                                                    }
                                                    )), _tD(option.text), 3)
                                                ), 10, _uA(
                                                    "onClick"
                                                ))
                                            }
                                            ), 128)
                                        ))
                                    ))
                                ))
                            ))
                        )
                    }
                    ), "_" to 1), 8, _uA(
                        "searchValue",
                        "filterVisible",
                        "filterActive",
                        "onSearchInput",
                        "onSearchConfirm",
                        "onSearchClear",
                        "onUpdate:filterVisible"
                    )),
                    _cE("scroll-view", _uM("style" to _nS(_uM("flex" to "1")), "class" to "page-scroll"), _uA(
                        _cE("view", _uM("class" to "page-content"), _uA(
                            if (isTrue(unref(pageError) != "" && !unref(isLoading))) {
                                _cE("view", _uM("key" to 0, "class" to "state-card state-card-error"), _uA(
                                    _cE("text", _uM("class" to "state-title"), "分类加载失败"),
                                    _cE("text", _uM("class" to "state-desc"), _tD(unref(pageError)), 1),
                                    _cE("button", _uM("class" to "state-btn", "onClick" to loadRootCategories), _uA(
                                        _cE("text", _uM("class" to "state-btn-text"), "重新加载")
                                    ))
                                ))
                            } else {
                                if (isTrue(unref(isLoading) && unref(rootCategories).length == 0)) {
                                    _cE("view", _uM("key" to 1, "class" to "state-card"), _uA(
                                        _cE("text", _uM("class" to "state-title"), "加载中"),
                                        _cE("text", _uM("class" to "state-desc"), "正在获取根分类及已展开分支...")
                                    ))
                                } else {
                                    if (unref(rootCategories).length == 0) {
                                        _cE("view", _uM("key" to 2, "class" to "state-card"), _uA(
                                            _cE("text", _uM("class" to "state-title"), "暂无分类数据"),
                                            _cE("text", _uM("class" to "state-desc"), "可以调整筛选条件，或直接新增一个根分类。")
                                        ))
                                    } else {
                                        _cE("view", _uM("key" to 3, "class" to "tree-list"), _uA(
                                            _cE(Fragment, null, RenderHelpers.renderList(unref(rootCategories), fun(root, __key, __index, _cached): Any {
                                                return _cE("view", _uM("key" to ("root-" + root.id), "class" to "tree-section"), _uA(
                                                    _cE("view", _uM("class" to "tree-node tree-node-level1"), _uA(
                                                        _cE("view", _uM("class" to "tree-main", "onClick" to fun(){
                                                            openActionSheet(root)
                                                        }
                                                        ), _uA(
                                                            _cE("text", _uM("class" to "tree-title"), _tD(root.name), 1)
                                                        ), 8, _uA(
                                                            "onClick"
                                                        )),
                                                        if (isTrue(canExpand(root))) {
                                                            _cE("view", _uM("key" to 0, "class" to "tree-toggle-wrap", "onClick" to fun(){
                                                                toggleNode(root)
                                                            }), _uA(
                                                                _cE("text", _uM("class" to "tree-toggle"), _tD(if (isExpanded(root.id)) {
                                                                    "收起"
                                                                } else {
                                                                    "展开"
                                                                }), 1)
                                                            ), 8, _uA(
                                                                "onClick"
                                                            ))
                                                        } else {
                                                            _cC("v-if", true)
                                                        }
                                                    )),
                                                    if (isTrue(isExpanded(root.id))) {
                                                        _cE("view", _uM("key" to 0, "class" to "tree-children tree-children-level2"), _uA(
                                                            if (isTrue(isNodeLoading(root.id))) {
                                                                _cE("view", _uM("key" to 0, "class" to "child-state"), _uA(
                                                                    _cE("text", _uM("class" to "child-state-text"), "二级分类加载中...")
                                                                ))
                                                            } else {
                                                                if (getChildren(root.id).length == 0) {
                                                                    _cE("view", _uM("key" to 1, "class" to "child-state"), _uA(
                                                                        _cE("text", _uM("class" to "child-state-text"), "暂无二级分类")
                                                                    ))
                                                                } else {
                                                                    _cE("view", _uM("key" to 2), _uA(
                                                                        _cE(Fragment, null, RenderHelpers.renderList(getChildren(root.id), fun(second, __key, __index, _cached): Any {
                                                                            return _cE("view", _uM("key" to ("second-" + second.id), "class" to "tree-child-wrap"), _uA(
                                                                                _cE("view", _uM("class" to "tree-node tree-node-level2"), _uA(
                                                                                    _cE("view", _uM("class" to "tree-main", "onClick" to fun(){
                                                                                        openActionSheet(second)
                                                                                    }), _uA(
                                                                                        _cE("text", _uM("class" to "tree-title"), _tD(second.name), 1)
                                                                                    ), 8, _uA(
                                                                                        "onClick"
                                                                                    )),
                                                                                    if (isTrue(canExpand(second))) {
                                                                                        _cE("view", _uM("key" to 0, "class" to "tree-toggle-wrap", "onClick" to fun(){
                                                                                            toggleNode(second)
                                                                                        }), _uA(
                                                                                            _cE("text", _uM("class" to "tree-toggle"), _tD(if (isExpanded(second.id)) {
                                                                                                "收起"
                                                                                            } else {
                                                                                                "展开"
                                                                                            }), 1)
                                                                                        ), 8, _uA(
                                                                                            "onClick"
                                                                                        ))
                                                                                    } else {
                                                                                        _cC("v-if", true)
                                                                                    }
                                                                                )),
                                                                                if (isTrue(isExpanded(second.id))) {
                                                                                    _cE("view", _uM("key" to 0, "class" to "tree-children tree-children-level3"), _uA(
                                                                                        if (isTrue(isNodeLoading(second.id))) {
                                                                                            _cE("view", _uM("key" to 0, "class" to "child-state"), _uA(
                                                                                                _cE("text", _uM("class" to "child-state-text"), "三级分类加载中...")
                                                                                            ))
                                                                                        } else {
                                                                                            if (getChildren(second.id).length == 0) {
                                                                                                _cE("view", _uM("key" to 1, "class" to "child-state"), _uA(
                                                                                                    _cE("text", _uM("class" to "child-state-text"), "暂无三级分类")
                                                                                                ))
                                                                                            } else {
                                                                                                _cE("view", _uM("key" to 2), _uA(
                                                                                                    _cE(Fragment, null, RenderHelpers.renderList(getChildren(second.id), fun(third, __key, __index, _cached): Any {
                                                                                                        return _cE("view", _uM("key" to ("third-" + third.id), "class" to "tree-node tree-node-level3"), _uA(
                                                                                                            _cE("view", _uM("class" to "tree-main", "onClick" to fun(){
                                                                                                                openActionSheet(third)
                                                                                                            }), _uA(
                                                                                                                _cE("text", _uM("class" to "tree-title"), _tD(third.name), 1)
                                                                                                            ), 8, _uA(
                                                                                                                "onClick"
                                                                                                            ))
                                                                                                        ))
                                                                                                    }), 128)
                                                                                                ))
                                                                                            }
                                                                                        }
                                                                                    ))
                                                                                } else {
                                                                                    _cC("v-if", true)
                                                                                }
                                                                            ))
                                                                        }), 128)
                                                                    ))
                                                                }
                                                            }
                                                        ))
                                                    } else {
                                                        _cC("v-if", true)
                                                    }
                                                ))
                                            }
                                            ), 128)
                                        ))
                                    }
                                }
                            }
                        ))
                    ), 4),
                    _cE("view", _uM("class" to "fab", "onClick" to handleCreateRoot), _uA(
                        _cE("text", _uM("class" to "fab-text"), "+")
                    )),
                    if (isTrue(unref(isOperating))) {
                        _cE("view", _uM("key" to 0, "class" to "operate-mask"), _uA(
                            _cE("view", _uM("class" to "operate-card"), _uA(
                                _cE("text", _uM("class" to "operate-text"), "处理中...")
                            ))
                        ))
                    } else {
                        _cC("v-if", true)
                    }
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F4F6F8")), "page-scroll" to _pS(_uM("backgroundColor" to "#F4F6F8")), "page-content" to _pS(_uM("paddingLeft" to 16, "paddingRight" to 16, "paddingTop" to 12, "paddingBottom" to 100, "backgroundColor" to "#F4F6F8")), "filter-panel" to _pS(_uM("paddingTop" to 16, "paddingRight" to 16, "paddingBottom" to 16, "paddingLeft" to 16, "backgroundColor" to "#FFFFFF")), "filter-actions" to _pS(_uM("flexDirection" to "row")), "filter-btn" to _pS(_uM("height" to 40, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingLeft" to 16, "paddingRight" to 16, "marginRight" to 10, "alignItems" to "center", "justifyContent" to "center")), "filter-btn-light" to _pS(_uM("backgroundColor" to "#E2E8F0")), "filter-btn-primary" to _pS(_uM("backgroundColor" to "#0F766E")), "filter-btn-light-text" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#334155", "fontWeight" to "bold")), "filter-btn-primary-text" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#FFFFFF", "fontWeight" to "bold")), "filter-scroll" to _pS(_uM("maxHeight" to 360, "marginTop" to 14)), "filter-group" to _pS(_uM("marginTop" to 14)), "filter-group-title" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#0F172A", "fontWeight" to "bold")), "filter-options" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 10)), "filter-option" to _pS(_uM("paddingLeft" to 12, "paddingRight" to 12, "paddingTop" to 8, "paddingBottom" to 8, "borderTopLeftRadius" to 999, "borderTopRightRadius" to 999, "borderBottomRightRadius" to 999, "borderBottomLeftRadius" to 999, "backgroundColor" to "#F1F5F9", "marginRight" to 10, "marginBottom" to 10)), "filter-option-active" to _pS(_uM("backgroundColor" to "#CCFBF1")), "filter-option-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#475569")), "filter-option-text-active" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#115E59", "fontWeight" to "bold")), "state-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "paddingTop" to 18, "paddingRight" to 18, "paddingBottom" to 18, "paddingLeft" to 18, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0")), "state-card-error" to _pS(_uM("borderTopColor" to "#FECACA", "borderRightColor" to "#FECACA", "borderBottomColor" to "#FECACA", "borderLeftColor" to "#FECACA")), "state-title" to _pS(_uM("fontSize" to 16, "lineHeight" to "22px", "color" to "#0F172A", "fontWeight" to "bold")), "state-desc" to _pS(_uM("fontSize" to 14, "lineHeight" to "21px", "color" to "#64748B", "marginTop" to 8)), "state-btn" to _pS(_uM("height" to 40, "marginTop" to 14, "backgroundColor" to "#0F766E", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "borderTopWidth" to 0, "borderRightWidth" to 0, "borderBottomWidth" to 0, "borderLeftWidth" to 0)), "state-btn-text" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#FFFFFF", "fontWeight" to "bold")), "tree-list" to _pS(_uM("paddingBottom" to 10)), "tree-section" to _pS(_uM("marginBottom" to 12)), "tree-node" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "paddingTop" to 14, "paddingRight" to 14, "paddingBottom" to 14, "paddingLeft" to 14, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0")), "tree-node-level1" to _pS(_uM("backgroundColor" to "#FFFFFF")), "tree-node-level2" to _pS(_uM("backgroundColor" to "#FCFFFD")), "tree-node-level3" to _pS(_uM("backgroundColor" to "#FFFDF8")), "tree-main" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "tree-title" to _pS(_uM("fontSize" to 15, "lineHeight" to "22px", "color" to "#0F172A", "fontWeight" to "bold")), "tree-toggle-wrap" to _pS(_uM("marginLeft" to 12, "paddingLeft" to 12, "paddingRight" to 12, "paddingTop" to 8, "paddingBottom" to 8, "borderTopLeftRadius" to 999, "borderTopRightRadius" to 999, "borderBottomRightRadius" to 999, "borderBottomLeftRadius" to 999, "backgroundColor" to "#ECFEFF")), "tree-toggle" to _pS(_uM("fontSize" to 12, "lineHeight" to "18px", "color" to "#155E75", "fontWeight" to "bold")), "tree-children" to _pS(_uM("marginLeft" to 14, "paddingLeft" to 12, "marginTop" to 10, "borderLeftWidth" to 2, "borderLeftStyle" to "solid")), "tree-children-level2" to _pS(_uM("borderLeftColor" to "#99F6E4")), "tree-children-level3" to _pS(_uM("borderLeftColor" to "#FDE68A")), "tree-child-wrap" to _pS(_uM("marginBottom" to 10)), "child-state" to _pS(_uM("paddingTop" to 8, "paddingBottom" to 8)), "child-state-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "18px", "color" to "#64748B")), "fab" to _pS(_uM("position" to "fixed", "right" to 20, "bottom" to 28, "width" to 56, "height" to 56, "borderTopLeftRadius" to 28, "borderTopRightRadius" to 28, "borderBottomRightRadius" to 28, "borderBottomLeftRadius" to 28, "backgroundColor" to "#0F766E", "alignItems" to "center", "justifyContent" to "center")), "fab-text" to _pS(_uM("fontSize" to 30, "lineHeight" to "36px", "color" to "#FFFFFF", "fontWeight" to "bold")), "operate-mask" to _pS(_uM("position" to "fixed", "left" to 0, "right" to 0, "top" to 0, "bottom" to 0, "backgroundColor" to "rgba(0,0,0,0.25)", "alignItems" to "center", "justifyContent" to "center")), "operate-card" to _pS(_uM("paddingLeft" to 20, "paddingRight" to 20, "paddingTop" to 14, "paddingBottom" to 14, "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "backgroundColor" to "#FFFFFF")), "operate-text" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "color" to "#0F172A", "fontWeight" to "bold")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
