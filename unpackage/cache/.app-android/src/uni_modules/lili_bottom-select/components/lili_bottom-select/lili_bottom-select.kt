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
import io.dcloud.uniapp.extapi.getWindowInfo as uni_getWindowInfo
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelect : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var fetchData: (params: UTSJSONObject) -> UTSPromise<UTSJSONObject> by `$props`
    open var value: String by `$props`
    open var valueText: String by `$props`
    open var values: UTSArray<String> by `$props`
    open var multiple: Boolean by `$props`
    open var tree: Boolean by `$props`
    open var childrenKey: String by `$props`
    open var defaultExpandAll: Boolean by `$props`
    open var expandOnClickNode: Boolean by `$props`
    open var checkStrictly: Boolean by `$props`
    open var accordion: Boolean by `$props`
    open var placeholder: String by `$props`
    open var title: String by `$props`
    open var searchPlaceholder: String by `$props`
    open var emptyText: String by `$props`
    open var disabled: Boolean by `$props`
    open var labelKey: String by `$props`
    open var valueKey: String by `$props`
    open var pageSize: Number by `$props`
    open var searchDelay: Number by `$props`
    open var closeOnOverlay: Boolean by `$props`
    open var showEditAction: Boolean by `$props`
    open var showAddAction: Boolean by `$props`
    open var editActionText: String by `$props`
    open var addActionText: String by `$props`
    open var openPanel: () -> Unit
        get() {
            return unref(this.`$exposed`["openPanel"]) as () -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "openPanel", value)
        }
    open var closePanel: () -> Unit
        get() {
            return unref(this.`$exposed`["closePanel"]) as () -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "closePanel", value)
        }
    open var setValue: (value: String, text: String) -> Unit
        get() {
            return unref(this.`$exposed`["setValue"]) as (value: String, text: String) -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "setValue", value)
        }
    open var setValues: (items: UTSArray<UTSJSONObject>) -> Unit
        get() {
            return unref(this.`$exposed`["setValues"]) as (items: UTSArray<UTSJSONObject>) -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "setValues", value)
        }
    open var getValue: () -> SelectChangePayload
        get() {
            return unref(this.`$exposed`["getValue"]) as () -> SelectChangePayload
        }
        set(value) {
            setRefValue(this.`$exposed`, "getValue", value)
        }
    open var getValues: () -> MultiSelectChangePayload
        get() {
            return unref(this.`$exposed`["getValues"]) as () -> MultiSelectChangePayload
        }
        set(value) {
            setRefValue(this.`$exposed`, "getValues", value)
        }
    open var clearValue: () -> Unit
        get() {
            return unref(this.`$exposed`["clearValue"]) as () -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "clearValue", value)
        }
    open var clearValues: () -> Unit
        get() {
            return unref(this.`$exposed`["clearValues"]) as () -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "clearValues", value)
        }
    open var refreshData: () -> UTSPromise<Unit>
        get() {
            return unref(this.`$exposed`["refreshData"]) as () -> UTSPromise<Unit>
        }
        set(value) {
            setRefValue(this.`$exposed`, "refreshData", value)
        }
    open var preloadList: (kw: String) -> UTSPromise<Unit>
        get() {
            return unref(this.`$exposed`["preloadList"]) as (kw: String) -> UTSPromise<Unit>
        }
        set(value) {
            setRefValue(this.`$exposed`, "preloadList", value)
        }
    open var reset: () -> Unit
        get() {
            return unref(this.`$exposed`["reset"]) as () -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "reset", value)
        }
    open var expandAll: () -> Unit
        get() {
            return unref(this.`$exposed`["expandAll"]) as () -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "expandAll", value)
        }
    open var collapseAll: () -> Unit
        get() {
            return unref(this.`$exposed`["collapseAll"]) as () -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "collapseAll", value)
        }
    open var setExpandedKeys: (keys: UTSArray<String>) -> Unit
        get() {
            return unref(this.`$exposed`["setExpandedKeys"]) as (keys: UTSArray<String>) -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "setExpandedKeys", value)
        }
    open var getExpandedKeys: () -> UTSArray<String>
        get() {
            return unref(this.`$exposed`["getExpandedKeys"]) as () -> UTSArray<String>
        }
        set(value) {
            setRefValue(this.`$exposed`, "getExpandedKeys", value)
        }
    open var expandByValue: (value: String) -> Unit
        get() {
            return unref(this.`$exposed`["expandByValue"]) as (value: String) -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "expandByValue", value)
        }
    open var collapseByValue: (value: String) -> Unit
        get() {
            return unref(this.`$exposed`["collapseByValue"]) as (value: String) -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "collapseByValue", value)
        }
    open var toggleExpandByValue: (value: String) -> Unit
        get() {
            return unref(this.`$exposed`["toggleExpandByValue"]) as (value: String) -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "toggleExpandByValue", value)
        }
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelect, __setupCtx: SetupContext) -> Any? = fun(__props, __setupCtx): Any? {
            val __expose = __setupCtx.expose
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelect
            val _cache = __ins.renderCache
            val props = __props
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val renderVisible = ref<Boolean>(false)
            val overlayVisible = ref<Boolean>(false)
            val panelVisible = ref<Boolean>(false)
            val internalValue = ref<String>(props.value ?: "")
            val internalText = ref<String>(props.valueText ?: "")
            val textInitialized = ref<Boolean>((props.valueText ?: "") != "")
            val selectedItems = ref(_uA<UTSJSONObject>())
            val keyword = ref<String>("")
            val displayList = ref(_uA<UTSJSONObject>())
            val expandedKeys = ref(_uA<String>())
            val loadingChildKeys = ref(_uA<String>())
            val currentPage = ref<Number>(1)
            val total = ref<Number>(0)
            val loading = ref<Boolean>(false)
            val hasMore = ref<Boolean>(true)
            val listLoaded = ref<Boolean>(false)
            val panelStyle = ref<String>("")
            var searchTimer: Number? = null
            var enterTimer: Number? = null
            var closeTimer: Number? = null
            var requestSeq: Number = 0
            val PANEL_ANIMATION_DURATION: Number = 340
            fun gen_fieldToStr_fn(item: UTSJSONObject, key: String): String {
                val v = item[key]
                if (v == null) {
                    return ""
                }
                return "" + v
            }
            val fieldToStr = ::gen_fieldToStr_fn
            fun gen_fieldLabel_fn(item: UTSJSONObject, key: String): String {
                val v = item[key]
                if (v == null) {
                    return ""
                }
                return "" + v
            }
            val fieldLabel = ::gen_fieldLabel_fn
            fun gen_getItemValue_fn(item: UTSJSONObject): String {
                return fieldToStr(item, props.valueKey)
            }
            val getItemValue = ::gen_getItemValue_fn
            fun gen_getItemLabel_fn(item: UTSJSONObject): String {
                return fieldLabel(item, props.labelKey)
            }
            val getItemLabel = ::gen_getItemLabel_fn
            fun gen_getNumberField_fn(obj: UTSJSONObject, key: String): Number {
                val value = obj[key]
                if (value == null) {
                    return 0
                }
                return value as Number
            }
            val getNumberField = ::gen_getNumberField_fn
            fun gen_getListField_fn(obj: UTSJSONObject, key: String): UTSArray<UTSJSONObject> {
                val value = obj[key]
                if (value == null) {
                    return _uA()
                }
                return value as UTSArray<UTSJSONObject>
            }
            val getListField = ::gen_getListField_fn
            fun gen_getTreeChildren_fn(item: UTSJSONObject): UTSArray<UTSJSONObject> {
                val value = item[props.childrenKey]
                if (value == null) {
                    return _uA()
                }
                return value as UTSArray<UTSJSONObject>
            }
            val getTreeChildren = ::gen_getTreeChildren_fn
            fun gen_getBooleanField_fn(obj: UTSJSONObject, key: String): Boolean {
                val value = obj[key]
                if (value == null) {
                    return false
                }
                if (UTSAndroid.`typeof`(value) == "boolean") {
                    return value as Boolean
                }
                val text = ("" + value).toLowerCase()
                return text == "true" || text == "1" || text == "yes"
            }
            val getBooleanField = ::gen_getBooleanField_fn
            fun gen_cloneItem_fn(item: UTSJSONObject): UTSJSONObject {
                val next: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("next", "uni_modules/lili_bottom-select/components/lili_bottom-select/lili_bottom-select.uvue", 377, 8))
                for(key in resolveUTSKeyIterator(item)){
                    next[key] = item[key]
                }
                return next
            }
            val cloneItem = ::gen_cloneItem_fn
            fun gen_itemHasChildren_fn(item: UTSJSONObject): Boolean {
                if (getTreeChildren(item).length > 0) {
                    return true
                }
                return getBooleanField(item, "has_children")
            }
            val itemHasChildren = ::gen_itemHasChildren_fn
            fun gen_hasTreeChildren_fn(item: UTSJSONObject): Boolean {
                return itemHasChildren(item)
            }
            val hasTreeChildren = ::gen_hasTreeChildren_fn
            fun buildFetchParams(page: Number, keywordValue: String, id: String, parent: String = ""): UTSJSONObject {
                val params: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("params", "uni_modules/lili_bottom-select/components/lili_bottom-select/lili_bottom-select.uvue", 396, 8), "page" to page, "pageSize" to props.pageSize, "keyword" to keywordValue, "id" to id)
                if (parent != "") {
                    params["parent"] = parent
                }
                return params
            }
            fun gen_hasString_fn(list: UTSArray<String>, value: String): Boolean {
                run {
                    var i: Number = 0
                    while(i < list.length){
                        if (list[i] == value) {
                            return true
                        }
                        i++
                    }
                }
                return false
            }
            val hasString = ::gen_hasString_fn
            fun gen_uniqueStringList_fn(list: UTSArray<String>): UTSArray<String> {
                val result: UTSArray<String> = _uA()
                run {
                    var i: Number = 0
                    while(i < list.length){
                        if (!hasString(result, list[i])) {
                            result.push(list[i])
                        }
                        i++
                    }
                }
                return result
            }
            val uniqueStringList = ::gen_uniqueStringList_fn
            fun gen_mergeStringList_fn(a: UTSArray<String>, b: UTSArray<String>): UTSArray<String> {
                val result: UTSArray<String> = _uA()
                run {
                    var i: Number = 0
                    while(i < a.length){
                        if (!hasString(result, a[i])) {
                            result.push(a[i])
                        }
                        i++
                    }
                }
                run {
                    var j: Number = 0
                    while(j < b.length){
                        if (!hasString(result, b[j])) {
                            result.push(b[j])
                        }
                        j++
                    }
                }
                return result
            }
            val mergeStringList = ::gen_mergeStringList_fn
            fun gen_containsItemValue_fn(list: UTSArray<UTSJSONObject>, value: String): Boolean {
                run {
                    var i: Number = 0
                    while(i < list.length){
                        if (getItemValue(list[i]) == value) {
                            return true
                        }
                        i++
                    }
                }
                return false
            }
            val containsItemValue = ::gen_containsItemValue_fn
            fun gen_findSelectedItemByValue_fn(value: String): UTSJSONObject? {
                run {
                    var i: Number = 0
                    while(i < selectedItems.value.length){
                        val item = selectedItems.value[i]
                        if (getItemValue(item) == value) {
                            return item
                        }
                        i++
                    }
                }
                return null
            }
            val findSelectedItemByValue = ::gen_findSelectedItemByValue_fn
            fun gen_findItemByValueInList_fn(list: UTSArray<UTSJSONObject>, value: String): UTSJSONObject? {
                run {
                    var i: Number = 0
                    while(i < list.length){
                        val item = list[i]
                        if (getItemValue(item) == value) {
                            return item
                        }
                        val children = getTreeChildren(item)
                        if (children.length > 0) {
                            val childResult = gen_findItemByValueInList_fn(children, value)
                            if (childResult != null) {
                                return childResult
                            }
                        }
                        i++
                    }
                }
                return null
            }
            val findItemByValueInList = ::gen_findItemByValueInList_fn
            fun gen_collectDescendantItems_fn(item: UTSJSONObject, out: UTSArray<UTSJSONObject>) {
                val children = getTreeChildren(item)
                run {
                    var i: Number = 0
                    while(i < children.length){
                        val child = children[i]
                        out.push(child)
                        gen_collectDescendantItems_fn(child, out)
                        i++
                    }
                }
            }
            val collectDescendantItems = ::gen_collectDescendantItems_fn
            fun gen_collectSelfAndDescendantItems_fn(item: UTSJSONObject): UTSArray<UTSJSONObject> {
                val result: UTSArray<UTSJSONObject> = _uA()
                result.push(item)
                collectDescendantItems(item, result)
                return result
            }
            val collectSelfAndDescendantItems = ::gen_collectSelfAndDescendantItems_fn
            fun gen_valuesFromItems_fn(items: UTSArray<UTSJSONObject>): UTSArray<String> {
                val values: UTSArray<String> = _uA()
                run {
                    var i: Number = 0
                    while(i < items.length){
                        values.push(getItemValue(items[i]))
                        i++
                    }
                }
                return values
            }
            val valuesFromItems = ::gen_valuesFromItems_fn
            fun gen_addSelectedItems_fn(items: UTSArray<UTSJSONObject>) {
                val next: UTSArray<UTSJSONObject> = selectedItems.value.slice()
                run {
                    var i: Number = 0
                    while(i < items.length){
                        val item = items[i]
                        val value = getItemValue(item)
                        if (!containsItemValue(next, value)) {
                            next.push(item)
                        }
                        i++
                    }
                }
                selectedItems.value = next
            }
            val addSelectedItems = ::gen_addSelectedItems_fn
            fun gen_removeSelectedByValues_fn(values: UTSArray<String>) {
                val next: UTSArray<UTSJSONObject> = _uA()
                run {
                    var i: Number = 0
                    while(i < selectedItems.value.length){
                        val item = selectedItems.value[i]
                        val value = getItemValue(item)
                        if (!hasString(values, value)) {
                            next.push(item)
                        }
                        i++
                    }
                }
                selectedItems.value = next
            }
            val removeSelectedByValues = ::gen_removeSelectedByValues_fn
            fun gen_clearAnimationTimers_fn() {
                if (enterTimer != null) {
                    val timer = enterTimer
                    clearTimeout(timer!!)
                    enterTimer = null
                }
                if (closeTimer != null) {
                    val timer = closeTimer
                    clearTimeout(timer!!)
                    closeTimer = null
                }
            }
            val clearAnimationTimers = ::gen_clearAnimationTimers_fn
            fun gen_activatePanelAnimation_fn() {
                clearAnimationTimers()
                enterTimer = setTimeout(fun(){
                    overlayVisible.value = true
                    panelVisible.value = true
                    enterTimer = null
                }
                , 16)
            }
            val activatePanelAnimation = ::gen_activatePanelAnimation_fn
            fun gen_isExpanded_fn(item: UTSJSONObject): Boolean {
                val value = getItemValue(item)
                return hasString(expandedKeys.value, value)
            }
            val isExpanded = ::gen_isExpanded_fn
            fun gen_buildVisibleRows_fn(list: UTSArray<UTSJSONObject>, level: Number, parentKey: String, rows: UTSArray<TreeDisplayRow>) {
                run {
                    var i: Number = 0
                    while(i < list.length){
                        val item = list[i]
                        val itemVal = getItemValue(item)
                        val children = getTreeChildren(item)
                        val hasChildren = props.tree && itemHasChildren(item)
                        val expanded = hasChildren && isExpanded(item)
                        val rowKey = parentKey + "/" + itemVal + "_" + ("" + i)
                        rows.push(TreeDisplayRow(key = rowKey, item = item, level = level, hasChildren = hasChildren, expanded = expanded))
                        if (props.tree && hasChildren && expanded) {
                            gen_buildVisibleRows_fn(children, level + 1, rowKey, rows)
                        }
                        i++
                    }
                }
            }
            val buildVisibleRows = ::gen_buildVisibleRows_fn
            val visibleRows = computed<UTSArray<TreeDisplayRow>>(fun(): UTSArray<TreeDisplayRow> {
                val rows: UTSArray<TreeDisplayRow> = _uA()
                buildVisibleRows(displayList.value, 0, "", rows)
                return rows
            }
            )
            fun gen_getTreeIndentStyle_fn(row: TreeDisplayRow): String {
                val width = row.level * 18
                return "width:" + width + "px;"
            }
            val getTreeIndentStyle = ::gen_getTreeIndentStyle_fn
            fun gen_collectExpandableKeys_fn(list: UTSArray<UTSJSONObject>, out: UTSArray<String>) {
                run {
                    var i: Number = 0
                    while(i < list.length){
                        val item = list[i]
                        if (getTreeChildren(item).length > 0) {
                            val value = getItemValue(item)
                            if (!hasString(out, value)) {
                                out.push(value)
                            }
                            gen_collectExpandableKeys_fn(getTreeChildren(item), out)
                        }
                        i++
                    }
                }
            }
            val collectExpandableKeys = ::gen_collectExpandableKeys_fn
            fun gen_applyTreeExpandState_fn(isReset: Boolean, newData: UTSArray<UTSJSONObject>) {
                if (!props.tree) {
                    return
                }
                val shouldExpand = props.defaultExpandAll || keyword.value != ""
                if (isReset) {
                    if (shouldExpand) {
                        val keys: UTSArray<String> = _uA()
                        collectExpandableKeys(displayList.value, keys)
                        expandedKeys.value = uniqueStringList(keys)
                    } else {
                        expandedKeys.value = _uA()
                    }
                    return
                }
                if (shouldExpand) {
                    val keys: UTSArray<String> = _uA()
                    collectExpandableKeys(newData, keys)
                    expandedKeys.value = mergeStringList(expandedKeys.value, keys)
                }
            }
            val applyTreeExpandState = ::gen_applyTreeExpandState_fn
            fun gen_findSiblingExpandableKeys_fn(list: UTSArray<UTSJSONObject>, targetValue: String): UTSArray<String>? {
                val siblingKeys: UTSArray<String> = _uA()
                run {
                    var i: Number = 0
                    while(i < list.length){
                        val item = list[i]
                        if (itemHasChildren(item)) {
                            siblingKeys.push(getItemValue(item))
                        }
                        i++
                    }
                }
                run {
                    var j: Number = 0
                    while(j < list.length){
                        val item = list[j]
                        val value = getItemValue(item)
                        if (value == targetValue) {
                            return siblingKeys
                        }
                        val children = getTreeChildren(item)
                        if (children.length > 0) {
                            val found = gen_findSiblingExpandableKeys_fn(children, targetValue)
                            if (found != null) {
                                return found
                            }
                        }
                        j++
                    }
                }
                return null
            }
            val findSiblingExpandableKeys = ::gen_findSiblingExpandableKeys_fn
            fun gen_replaceTreeChildren_fn(list: UTSArray<UTSJSONObject>, targetValue: String, children: UTSArray<UTSJSONObject>): UTSArray<UTSJSONObject> {
                val next: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < list.length){
                        val current = list[index]
                        val currentValue = getItemValue(current)
                        if (currentValue == targetValue) {
                            val cloned = cloneItem(current)
                            cloned[props.childrenKey] = children
                            cloned["has_children"] = children.length > 0
                            next.push(cloned)
                            index += 1
                            continue
                        }
                        val currentChildren = getTreeChildren(current)
                        if (currentChildren.length > 0) {
                            val cloned = cloneItem(current)
                            cloned[props.childrenKey] = gen_replaceTreeChildren_fn(currentChildren, targetValue, children)
                            next.push(cloned)
                            index += 1
                            continue
                        }
                        next.push(current)
                        index += 1
                    }
                }
                return next
            }
            val replaceTreeChildren = ::gen_replaceTreeChildren_fn
            fun gen_addLoadingChildKey_fn(value: String) {
                if (!hasString(loadingChildKeys.value, value)) {
                    loadingChildKeys.value = loadingChildKeys.value.concat(_uA(
                        value
                    ))
                }
            }
            val addLoadingChildKey = ::gen_addLoadingChildKey_fn
            fun gen_removeLoadingChildKey_fn(value: String) {
                val next: UTSArray<String> = _uA()
                run {
                    var index: Number = 0
                    while(index < loadingChildKeys.value.length){
                        if (loadingChildKeys.value[index] != value) {
                            next.push(loadingChildKeys.value[index])
                        }
                        index += 1
                    }
                }
                loadingChildKeys.value = next
            }
            val removeLoadingChildKey = ::gen_removeLoadingChildKey_fn
            fun gen_ensureTreeChildrenLoaded_fn(item: UTSJSONObject): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (!props.tree) {
                            return@w1
                        }
                        if (!itemHasChildren(item)) {
                            return@w1
                        }
                        if (getTreeChildren(item).length > 0) {
                            return@w1
                        }
                        val parentValue = getItemValue(item)
                        if (parentValue == "") {
                            return@w1
                        }
                        if (hasString(loadingChildKeys.value, parentValue)) {
                            return@w1
                        }
                        addLoadingChildKey(parentValue)
                        try {
                            val res = await(props.fetchData(buildFetchParams(1, "", "", parentValue)))
                            val children = getListField(res, "data")
                            displayList.value = replaceTreeChildren(displayList.value, parentValue, children)
                        }
                         catch (e: Throwable) {
                            console.error("lili_bottom-select loadTreeChildren 失败:", e, " at uni_modules/lili_bottom-select/components/lili_bottom-select/lili_bottom-select.uvue:712")
                            uni_showToast(ShowToastOptions(title = "子节点加载失败", icon = "none"))
                        }
                         finally {
                            removeLoadingChildKey(parentValue)
                        }
                })
            }
            val ensureTreeChildrenLoaded = ::gen_ensureTreeChildrenLoaded_fn
            fun gen_expandItem_fn(item: UTSJSONObject): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (!props.tree) {
                            return@w1
                        }
                        if (!itemHasChildren(item)) {
                            return@w1
                        }
                        val value = getItemValue(item)
                        if (value == "") {
                            return@w1
                        }
                        await(ensureTreeChildrenLoaded(item))
                        if (props.accordion) {
                            val siblingKeys = findSiblingExpandableKeys(displayList.value, value)
                            if (siblingKeys != null) {
                                val next: UTSArray<String> = _uA()
                                run {
                                    var i: Number = 0
                                    while(i < expandedKeys.value.length){
                                        val key = expandedKeys.value[i]
                                        if (!hasString(siblingKeys, key) || key == value) {
                                            next.push(key)
                                        }
                                        i++
                                    }
                                }
                                expandedKeys.value = next
                            }
                        }
                        if (!hasString(expandedKeys.value, value)) {
                            expandedKeys.value = expandedKeys.value.concat(_uA(
                                value
                            ))
                        }
                })
            }
            val expandItem = ::gen_expandItem_fn
            fun gen_collapseItem_fn(item: UTSJSONObject) {
                val value = getItemValue(item)
                val next: UTSArray<String> = _uA()
                run {
                    var i: Number = 0
                    while(i < expandedKeys.value.length){
                        if (expandedKeys.value[i] != value) {
                            next.push(expandedKeys.value[i])
                        }
                        i++
                    }
                }
                expandedKeys.value = next
            }
            val collapseItem = ::gen_collapseItem_fn
            fun gen_toggleExpand_fn(item: UTSJSONObject): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (!props.tree) {
                            return@w1
                        }
                        if (!itemHasChildren(item)) {
                            return@w1
                        }
                        if (isExpanded(item)) {
                            collapseItem(item)
                        } else {
                            await(expandItem(item))
                        }
                })
            }
            val toggleExpand = ::gen_toggleExpand_fn
            fun gen_expandAll_fn() {
                if (!props.tree) {
                    return
                }
                val keys: UTSArray<String> = _uA()
                collectExpandableKeys(displayList.value, keys)
                expandedKeys.value = uniqueStringList(keys)
            }
            val expandAll = ::gen_expandAll_fn
            fun gen_collapseAll_fn() {
                expandedKeys.value = _uA()
            }
            val collapseAll = ::gen_collapseAll_fn
            fun gen_setExpandedKeys_fn(keys: UTSArray<String>) {
                expandedKeys.value = uniqueStringList(keys)
            }
            val setExpandedKeys = ::gen_setExpandedKeys_fn
            fun gen_getExpandedKeys_fn(): UTSArray<String> {
                return expandedKeys.value
            }
            val getExpandedKeys = ::gen_getExpandedKeys_fn
            fun gen_expandByValue_fn(value: String) {
                val item = findItemByValueInList(displayList.value, value)
                if (item != null) {
                    expandItem(item)
                }
            }
            val expandByValue = ::gen_expandByValue_fn
            fun gen_collapseByValue_fn(value: String) {
                val item = findItemByValueInList(displayList.value, value)
                if (item != null) {
                    collapseItem(item)
                }
            }
            val collapseByValue = ::gen_collapseByValue_fn
            fun gen_toggleExpandByValue_fn(value: String) {
                val item = findItemByValueInList(displayList.value, value)
                if (item != null) {
                    toggleExpand(item)
                }
            }
            val toggleExpandByValue = ::gen_toggleExpandByValue_fn
            fun gen_updateTextFromList_fn() {
                val item = findItemByValueInList(displayList.value, internalValue.value)
                if (item != null) {
                    internalText.value = getItemLabel(item)
                    textInitialized.value = true
                }
            }
            val updateTextFromList = ::gen_updateTextFromList_fn
            fun gen_syncFromList_fn(vals: UTSArray<String>) {
                if (vals.length == 0) {
                    selectedItems.value = _uA()
                    return
                }
                val next: UTSArray<UTSJSONObject> = _uA()
                run {
                    var i: Number = 0
                    while(i < vals.length){
                        val value = vals[i]
                        var item = findSelectedItemByValue(value)
                        if (item == null) {
                            item = findItemByValueInList(displayList.value, value)
                        }
                        if (item != null && !containsItemValue(next, value)) {
                            next.push(item)
                        }
                        i++
                    }
                }
                selectedItems.value = next
            }
            val syncFromList = ::gen_syncFromList_fn
            fun gen_loadData_fn(isReset: Boolean): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (loading.value && !isReset) {
                            return@w1
                        }
                        if (!isReset && !hasMore.value) {
                            return@w1
                        }
                        val page = if (isReset) {
                            1
                        } else {
                            currentPage.value
                        }
                        val seq = requestSeq + 1
                        requestSeq = seq
                        loading.value = true
                        try {
                            val res = await(props.fetchData(buildFetchParams(page, keyword.value, "")))
                            if (seq != requestSeq) {
                                return@w1
                            }
                            val data = getListField(res, "data")
                            val totalValue = getNumberField(res, "total")
                            total.value = totalValue
                            if (isReset) {
                                displayList.value = data
                                currentPage.value = 2
                                listLoaded.value = true
                            } else {
                                val merged: UTSArray<UTSJSONObject> = displayList.value.concat(data)
                                displayList.value = merged
                                currentPage.value = currentPage.value + 1
                            }
                            applyTreeExpandState(isReset, data)
                            hasMore.value = displayList.value.length < total.value && data.length >= props.pageSize
                            if (!props.multiple && internalValue.value != "" && !textInitialized.value) {
                                updateTextFromList()
                            }
                            if (props.multiple) {
                                val curVals = selectedItems.value.map<String>(fun(item: UTSJSONObject): String {
                                    return getItemValue(item)
                                }
                                )
                                if (curVals.length > 0) {
                                    syncFromList(curVals)
                                } else {
                                    syncFromList(props.values ?: _uA())
                                }
                            }
                        }
                         catch (e: Throwable) {
                            if (seq != requestSeq) {
                                return@w1
                            }
                            console.error("lili_bottom-select loadData 失败:", e, " at uni_modules/lili_bottom-select/components/lili_bottom-select/lili_bottom-select.uvue:889")
                            uni_showToast(ShowToastOptions(title = "数据加载失败", icon = "none"))
                        }
                         finally {
                            if (seq == requestSeq) {
                                loading.value = false
                            }
                        }
                })
            }
            val loadData = ::gen_loadData_fn
            fun gen_fetchTextByValue_fn(value: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (value == "" || textInitialized.value) {
                            return@w1
                        }
                        try {
                            val res = await(props.fetchData(buildFetchParams(1, "", value)))
                            val data = getListField(res, "data")
                            val item = findItemByValueInList(data, value)
                            if (item != null) {
                                internalText.value = getItemLabel(item)
                                textInitialized.value = true
                            }
                        }
                         catch (e: Throwable) {
                            console.error("lili_bottom-select fetchTextByValue 失败:", e, " at uni_modules/lili_bottom-select/components/lili_bottom-select/lili_bottom-select.uvue:911")
                        }
                })
            }
            val fetchTextByValue = ::gen_fetchTextByValue_fn
            fun gen_syncMultiValuesFromProps_fn(vals: UTSArray<String>) {
                if (vals.length == 0) {
                    selectedItems.value = _uA()
                    return
                }
                syncFromList(vals)
            }
            val syncMultiValuesFromProps = ::gen_syncMultiValuesFromProps_fn
            val displayText = computed<String>(fun(): String {
                if (props.multiple) {
                    if (selectedItems.value.length == 0) {
                        return ""
                    }
                    val texts = selectedItems.value.map<String>(fun(item: UTSJSONObject): String {
                        return getItemLabel(item)
                    }
                    )
                    return texts.join("、")
                }
                return internalText.value
            }
            )
            watch(fun(): String {
                return props.value ?: ""
            }
            , fun(newVal: String){
                if (newVal != internalValue.value) {
                    internalValue.value = newVal
                    if (newVal != "") {
                        if (props.valueText != "") {
                            internalText.value = props.valueText
                            textInitialized.value = true
                        } else {
                            textInitialized.value = false
                            fetchTextByValue(newVal)
                        }
                    } else {
                        internalText.value = ""
                        textInitialized.value = false
                    }
                }
            }
            )
            watch(fun(): String {
                return props.valueText ?: ""
            }
            , fun(newText: String){
                if (newText != "") {
                    internalText.value = newText
                    textInitialized.value = true
                    return
                }
                if (internalValue.value == "") {
                    internalText.value = ""
                    textInitialized.value = false
                }
            }
            )
            watch(fun(): UTSArray<String> {
                return props.values ?: _uA()
            }
            , fun(newVals: UTSArray<String>){
                if (props.multiple) {
                    syncMultiValuesFromProps(newVals)
                }
            }
            )
            onMounted(fun(){
                val info = uni_getWindowInfo()
                val winH = info.windowHeight
                var panelH = Math.floor(winH * 0.72)
                if (panelH < 420) {
                    panelH = 420
                }
                val maxPanelH = winH - 24
                if (panelH > maxPanelH) {
                    panelH = maxPanelH
                }
                panelStyle.value = "height:" + panelH + "px;"
                if (!props.multiple && internalValue.value != "") {
                    if (props.valueText != "") {
                        internalText.value = props.valueText
                        textInitialized.value = true
                    } else {
                        fetchTextByValue(internalValue.value)
                    }
                }
                if (props.multiple && props.values.length > 0) {
                    syncMultiValuesFromProps(props.values)
                }
            }
            )
            fun gen_open_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (props.disabled) {
                            return@w1
                        }
                        if (panelVisible.value || overlayVisible.value) {
                            return@w1
                        }
                        renderVisible.value = true
                        overlayVisible.value = false
                        panelVisible.value = false
                        activatePanelAnimation()
                        emit("open")
                        if (!listLoaded.value) {
                            displayList.value = _uA()
                            currentPage.value = 1
                            hasMore.value = true
                            listLoaded.value = false
                            await(loadData(true))
                        } else {
                            hasMore.value = displayList.value.length < total.value
                        }
                        if (props.multiple) {
                            val curVals = selectedItems.value.map<String>(fun(item: UTSJSONObject): String {
                                return getItemValue(item)
                            }
                            )
                            syncFromList(if (curVals.length > 0) {
                                curVals
                            } else {
                                (props.values ?: _uA())
                            }
                            )
                        }
                })
            }
            val open = ::gen_open_fn
            fun gen_close_fn() {
                if (!renderVisible.value) {
                    return
                }
                clearAnimationTimers()
                overlayVisible.value = false
                panelVisible.value = false
                closeTimer = setTimeout(fun(){
                    closeTimer = null
                    emit("close")
                }
                , PANEL_ANIMATION_DURATION)
            }
            val close = ::gen_close_fn
            fun gen_handleOverlayClick_fn() {
                if (props.closeOnOverlay) {
                    close()
                }
            }
            val handleOverlayClick = ::gen_handleOverlayClick_fn
            fun gen_handleEditAction_fn() {
                if (props.disabled) {
                    return
                }
                emit("edit")
            }
            val handleEditAction = ::gen_handleEditAction_fn
            fun gen_handleAddAction_fn() {
                if (props.disabled) {
                    return
                }
                emit("add")
            }
            val handleAddAction = ::gen_handleAddAction_fn
            fun gen_triggerSearch_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        if (searchTimer != null) {
                            clearTimeout(searchTimer!!)
                            searchTimer = null
                        }
                        displayList.value = _uA()
                        currentPage.value = 1
                        hasMore.value = true
                        listLoaded.value = false
                        await(loadData(true))
                })
            }
            val triggerSearch = ::gen_triggerSearch_fn
            fun gen_onSearchInput_fn() {
                if (searchTimer != null) {
                    clearTimeout(searchTimer!!)
                }
                searchTimer = setTimeout(fun(){
                    triggerSearch()
                }
                , props.searchDelay)
            }
            val onSearchInput = ::gen_onSearchInput_fn
            fun gen_clearSearch_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        keyword.value = ""
                        await(triggerSearch())
                })
            }
            val clearSearch = ::gen_clearSearch_fn
            fun gen_loadMore_fn() {
                if (hasMore.value && !loading.value) {
                    loadData(false)
                }
            }
            val loadMore = ::gen_loadMore_fn
            fun gen_isItemSelected_fn(item: UTSJSONObject): Boolean {
                val itemVal = getItemValue(item)
                if (props.multiple) {
                    run {
                        var i: Number = 0
                        while(i < selectedItems.value.length){
                            if (getItemValue(selectedItems.value[i]) == itemVal) {
                                return true
                            }
                            i++
                        }
                    }
                    return false
                }
                return internalValue.value == itemVal
            }
            val isItemSelected = ::gen_isItemSelected_fn
            fun gen_isItemHalfSelected_fn(item: UTSJSONObject): Boolean {
                if (!props.multiple) {
                    return false
                }
                if (!props.tree) {
                    return false
                }
                if (props.checkStrictly) {
                    return false
                }
                if (isItemSelected(item)) {
                    return false
                }
                val descendants: UTSArray<UTSJSONObject> = _uA()
                collectDescendantItems(item, descendants)
                if (descendants.length == 0) {
                    return false
                }
                var selectedCount: Number = 0
                run {
                    var i: Number = 0
                    while(i < descendants.length){
                        if (isItemSelected(descendants[i])) {
                            selectedCount = selectedCount + 1
                        }
                        i++
                    }
                }
                return selectedCount > 0
            }
            val isItemHalfSelected = ::gen_isItemHalfSelected_fn
            fun gen_getSelectIcon_fn(item: UTSJSONObject): String {
                if (isItemSelected(item)) {
                    return "✓"
                }
                if (isItemHalfSelected(item)) {
                    return "−"
                }
                return ""
            }
            val getSelectIcon = ::gen_getSelectIcon_fn
            fun gen_confirmSingleItem_fn(item: UTSJSONObject) {
                val value = getItemValue(item)
                val text = getItemLabel(item)
                internalValue.value = value
                internalText.value = text
                textInitialized.value = true
                emit("change", _uO("value" to value, "text" to text, "item" to item))
                close()
            }
            val confirmSingleItem = ::gen_confirmSingleItem_fn
            fun gen_toggleMultiItem_fn(item: UTSJSONObject) {
                if (props.tree && !props.checkStrictly) {
                    val allItems = collectSelfAndDescendantItems(item)
                    val allValues = valuesFromItems(allItems)
                    if (isItemSelected(item)) {
                        removeSelectedByValues(allValues)
                    } else {
                        addSelectedItems(allItems)
                    }
                    return
                }
                val kVal = getItemValue(item)
                val idx = selectedItems.value.findIndex(fun(s: UTSJSONObject): Boolean {
                    return getItemValue(s) == kVal
                }
                )
                if (idx >= 0) {
                    val newList: UTSArray<UTSJSONObject> = _uA()
                    run {
                        var i: Number = 0
                        while(i < selectedItems.value.length){
                            if (i != idx) {
                                newList.push(selectedItems.value[i])
                            }
                            i++
                        }
                    }
                    selectedItems.value = newList
                } else {
                    selectedItems.value = selectedItems.value.concat(_uA(
                        item
                    ))
                }
            }
            val toggleMultiItem = ::gen_toggleMultiItem_fn
            fun gen_onItemClick_fn(item: UTSJSONObject) {
                if (props.disabled) {
                    return
                }
                if (props.multiple) {
                    toggleMultiItem(item)
                } else {
                    confirmSingleItem(item)
                }
            }
            val onItemClick = ::gen_onItemClick_fn
            fun gen_onRowClick_fn(row: TreeDisplayRow) {
                if (props.disabled) {
                    return
                }
                if (props.tree && props.expandOnClickNode && row.hasChildren) {
                    toggleExpand(row.item)
                    return
                }
                onItemClick(row.item)
            }
            val onRowClick = ::gen_onRowClick_fn
            fun gen_removeSelectedItem_fn(item: UTSJSONObject) {
                if (props.tree && !props.checkStrictly) {
                    val allItems = collectSelfAndDescendantItems(item)
                    val allValues = valuesFromItems(allItems)
                    removeSelectedByValues(allValues)
                    return
                }
                val kVal = getItemValue(item)
                val newList: UTSArray<UTSJSONObject> = _uA()
                run {
                    var i: Number = 0
                    while(i < selectedItems.value.length){
                        if (getItemValue(selectedItems.value[i]) != kVal) {
                            newList.push(selectedItems.value[i])
                        }
                        i++
                    }
                }
                selectedItems.value = newList
            }
            val removeSelectedItem = ::gen_removeSelectedItem_fn
            fun gen_clearAllSelected_fn() {
                selectedItems.value = _uA()
            }
            val clearAllSelected = ::gen_clearAllSelected_fn
            fun gen_confirmMultiple_fn() {
                val values = selectedItems.value.map<String>(fun(item: UTSJSONObject): String {
                    return getItemValue(item)
                }
                )
                val texts = selectedItems.value.map<String>(fun(item: UTSJSONObject): String {
                    return getItemLabel(item)
                }
                )
                emit("multiChange", _uO("values" to values, "texts" to texts, "items" to selectedItems.value))
                close()
            }
            val confirmMultiple = ::gen_confirmMultiple_fn
            fun gen_openPanel_fn() {
                open()
            }
            val openPanel = ::gen_openPanel_fn
            fun gen_closePanel_fn() {
                close()
            }
            val closePanel = ::gen_closePanel_fn
            fun gen_setValue_fn(value: String, text: String) {
                internalValue.value = value
                if (text != "") {
                    internalText.value = text
                    textInitialized.value = true
                } else if (value != "") {
                    textInitialized.value = false
                    fetchTextByValue(value)
                } else {
                    internalText.value = ""
                    textInitialized.value = false
                }
            }
            val setValue = ::gen_setValue_fn
            fun gen_setValues_fn(items: UTSArray<UTSJSONObject>) {
                selectedItems.value = items
            }
            val setValues = ::gen_setValues_fn
            fun gen_getValue_fn(): SelectChangePayload {
                return SelectChangePayload(value = internalValue.value, text = internalText.value, item = _uO())
            }
            val getValue = ::gen_getValue_fn
            fun gen_getValues_fn(): MultiSelectChangePayload {
                val values = selectedItems.value.map<String>(fun(item: UTSJSONObject): String {
                    return getItemValue(item)
                }
                )
                val texts = selectedItems.value.map<String>(fun(item: UTSJSONObject): String {
                    return getItemLabel(item)
                }
                )
                return MultiSelectChangePayload(values = values, texts = texts, items = selectedItems.value)
            }
            val getValues = ::gen_getValues_fn
            fun gen_clearValue_fn() {
                internalValue.value = ""
                internalText.value = ""
                textInitialized.value = false
            }
            val clearValue = ::gen_clearValue_fn
            fun gen_clearValues_fn() {
                selectedItems.value = _uA()
            }
            val clearValues = ::gen_clearValues_fn
            fun gen_refreshData_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        displayList.value = _uA()
                        currentPage.value = 1
                        listLoaded.value = false
                        keyword.value = ""
                        hasMore.value = true
                        await(loadData(true))
                })
            }
            val refreshData = ::gen_refreshData_fn
            fun gen_preloadList_fn(kw: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        if (kw != "") {
                            keyword.value = kw
                        }
                        displayList.value = _uA()
                        currentPage.value = 1
                        listLoaded.value = false
                        hasMore.value = true
                        await(loadData(true))
                })
            }
            val preloadList = ::gen_preloadList_fn
            fun gen_reset_fn() {
                clearValue()
                clearValues()
                displayList.value = _uA()
                expandedKeys.value = _uA()
                loadingChildKeys.value = _uA()
                listLoaded.value = false
                keyword.value = ""
                hasMore.value = true
                currentPage.value = 1
                total.value = 0
            }
            val reset = ::gen_reset_fn
            onUnmounted(fun(){
                requestSeq = requestSeq + 1
                clearAnimationTimers()
                if (searchTimer != null) {
                    val timer = searchTimer
                    clearTimeout(timer!!)
                    searchTimer = null
                }
            }
            )
            __expose(_uM("openPanel" to openPanel, "closePanel" to closePanel, "setValue" to setValue, "setValues" to setValues, "getValue" to getValue, "getValues" to getValues, "clearValue" to clearValue, "clearValues" to clearValues, "refreshData" to refreshData, "preloadList" to preloadList, "reset" to reset, "expandAll" to expandAll, "collapseAll" to collapseAll, "setExpandedKeys" to setExpandedKeys, "getExpandedKeys" to getExpandedKeys, "expandByValue" to expandByValue, "collapseByValue" to collapseByValue, "toggleExpandByValue" to toggleExpandByValue))
            return fun(): Any? {
                return _cE(Fragment, null, _uA(
                    _cE("view", _uM("class" to "bs-trigger-wrapper", "onClick" to open), _uA(
                        renderSlot(_ctx.`$slots`, "trigger", _uO(), fun(): UTSArray<Any> {
                            return _uA(
                                _cE("view", _uM("class" to "bs-trigger-default"), _uA(
                                    _cE("text", _uM("class" to _nC(if (unref(displayText) != "") {
                                        "bs-trigger-text"
                                    } else {
                                        "bs-trigger-placeholder"
                                    }
                                    )), _tD(if (unref(displayText) != "") {
                                        unref(displayText)
                                    } else {
                                        _ctx.placeholder
                                    }
                                    ), 3),
                                    _cE("view", _uM("class" to "bs-trigger-actions"), _uA(
                                        if (isTrue(_ctx.showEditAction)) {
                                            _cE("view", _uM("key" to 0, "class" to "bs-trigger-action", "onClick" to withModifiers(handleEditAction, _uA(
                                                "stop"
                                            ))), _uA(
                                                _cE("text", _uM("class" to "bs-trigger-action-icon"), "✎")
                                            ))
                                        } else {
                                            _cC("v-if", true)
                                        }
                                        ,
                                        if (isTrue(_ctx.showAddAction)) {
                                            _cE("view", _uM("key" to 1, "class" to "bs-trigger-action", "onClick" to withModifiers(handleAddAction, _uA(
                                                "stop"
                                            ))), _uA(
                                                _cE("text", _uM("class" to "bs-trigger-action-icon"), "＋")
                                            ))
                                        } else {
                                            _cC("v-if", true)
                                        }
                                        ,
                                        _cE("view", _uM("class" to "bs-trigger-arrow"), _uA(
                                            _cE("text", _uM("class" to "bs-arrow-icon"), _tD(if (unref(panelVisible)) {
                                                "⌄"
                                            } else {
                                                "›"
                                            }
                                            ), 1)
                                        ))
                                    ))
                                ))
                            )
                        }
                        )
                    )),
                    if (isTrue(unref(renderVisible))) {
                        _cE("view", _uM("key" to 0, "class" to _nC(if (unref(overlayVisible)) {
                            "bs-overlay bs-overlay-active"
                        } else {
                            "bs-overlay"
                        }), "onClick" to handleOverlayClick), null, 2)
                    } else {
                        _cC("v-if", true)
                    }
                    ,
                    if (isTrue(unref(renderVisible))) {
                        _cE("view", _uM("key" to 1, "class" to _nC(if (unref(panelVisible)) {
                            "bs-panel bs-panel-active"
                        } else {
                            "bs-panel"
                        }), "style" to _nS(unref(panelStyle))), _uA(
                            _cE("view", _uM("class" to "bs-handle-wrap"), _uA(
                                _cE("view", _uM("class" to "bs-handle"))
                            )),
                            _cE("view", _uM("class" to "bs-header"), _uA(
                                _cE("text", _uM("class" to "bs-header-title"), _tD(_ctx.title), 1),
                                _cE("view", _uM("class" to "bs-header-close", "onClick" to close), _uA(
                                    _cE("text", _uM("class" to "bs-close-icon"), "✕")
                                ))
                            )),
                            _cE("view", _uM("class" to "bs-search-bar"), _uA(
                                _cE("view", _uM("class" to "bs-search-inner"), _uA(
                                    _cE("text", _uM("class" to "bs-search-icon"), "🔍"),
                                    _cE("input", _uM("class" to "bs-search-input", "modelValue" to unref(keyword), "onInput" to _uA<Any?>(fun(`$event`: UniInputEvent){
                                        trySetRefValue(keyword, `$event`.detail.value)
                                    }, onSearchInput), "placeholder" to _ctx.searchPlaceholder, "disabled" to _ctx.disabled, "onConfirm" to triggerSearch, "confirm-type" to "search"), null, 40, _uA(
                                        "modelValue",
                                        "placeholder",
                                        "disabled"
                                    )),
                                    if (unref(keyword) != "") {
                                        _cE("view", _uM("key" to 0, "class" to "bs-search-clear", "onClick" to clearSearch), _uA(
                                            _cE("text", _uM("class" to "bs-clear-icon"), "✕")
                                        ))
                                    } else {
                                        _cC("v-if", true)
                                    }
                                ))
                            )),
                            if (isTrue(_ctx.multiple && unref(selectedItems).length > 0)) {
                                _cE("view", _uM("key" to 0, "class" to "bs-tags-bar"), _uA(
                                    _cE("scroll-view", _uM("scroll-x" to "true", "style" to _nS(_uM("flex" to "1"))), _uA(
                                        _cE("view", _uM("class" to "bs-tags-inner"), _uA(
                                            _cE(Fragment, null, RenderHelpers.renderList(unref(selectedItems), fun(item, __key, __index, _cached): Any {
                                                return _cE("view", _uM("key" to fieldToStr(item, _ctx.valueKey), "class" to "bs-tag"), _uA(
                                                    _cE("text", _uM("class" to "bs-tag-text"), _tD(fieldLabel(item, _ctx.labelKey)), 1),
                                                    _cE("view", _uM("class" to "bs-tag-remove", "onClick" to withModifiers(fun(){
                                                        removeSelectedItem(item)
                                                    }, _uA(
                                                        "stop"
                                                    ))), _uA(
                                                        _cE("text", _uM("class" to "bs-tag-remove-icon"), "✕")
                                                    ), 8, _uA(
                                                        "onClick"
                                                    ))
                                                ))
                                            }), 128)
                                        ))
                                    ), 4)
                                ))
                            } else {
                                _cC("v-if", true)
                            },
                            _cE("scroll-view", _uM("scroll-y" to "true", "style" to _nS(_uM("flex" to "1")), "onScrolltolower" to loadMore, "show-scrollbar" to false), _uA(
                                if (isTrue(unref(loading) && unref(visibleRows).length == 0)) {
                                    _cE("view", _uM("key" to 0, "class" to "bs-state-wrapper"), _uA(
                                        _cE("text", _uM("class" to "bs-state-text"), "加载中...")
                                    ))
                                } else {
                                    if (isTrue(!unref(loading) && unref(visibleRows).length == 0 && unref(listLoaded))) {
                                        _cE("view", _uM("key" to 1, "class" to "bs-state-wrapper"), _uA(
                                            _cE("text", _uM("class" to "bs-state-text"), _tD(_ctx.emptyText), 1)
                                        ))
                                    } else {
                                        _cE("view", _uM("key" to 2), _uA(
                                            _cE(Fragment, null, RenderHelpers.renderList(unref(visibleRows), fun(row, __key, __index, _cached): Any {
                                                return _cE("view", _uM("key" to row.key, "class" to _nC(if (isItemSelected(row.item)) {
                                                    "bs-list-item bs-list-item-selected"
                                                } else {
                                                    "bs-list-item"
                                                }), "onClick" to fun(){
                                                    onRowClick(row)
                                                }), _uA(
                                                    _cE("view", _uM("class" to "bs-item-main"), _uA(
                                                        if (isTrue(_ctx.tree)) {
                                                            _cE("view", _uM("key" to 0, "class" to "bs-tree-prefix"), _uA(
                                                                _cE("view", _uM("class" to "bs-tree-indent", "style" to _nS(getTreeIndentStyle(row))), null, 4),
                                                                if (isTrue(row.hasChildren)) {
                                                                    _cE("view", _uM("key" to 0, "class" to "bs-tree-toggle", "onClick" to withModifiers(fun(){
                                                                        toggleExpand(row.item)
                                                                    }, _uA(
                                                                        "stop"
                                                                    ))), _uA(
                                                                        _cE("text", _uM("class" to "bs-tree-toggle-icon"), _tD(if (row.expanded) {
                                                                            "⌄"
                                                                        } else {
                                                                            "›"
                                                                        }), 1)
                                                                    ), 8, _uA(
                                                                        "onClick"
                                                                    ))
                                                                } else {
                                                                    _cE("view", _uM("key" to 1, "class" to "bs-tree-toggle-placeholder"))
                                                                }
                                                            ))
                                                        } else {
                                                            _cC("v-if", true)
                                                        },
                                                        _cE("text", _uM("class" to _nC(if (isItemSelected(row.item)) {
                                                            "bs-item-label bs-item-label-selected"
                                                        } else {
                                                            "bs-item-label"
                                                        })), _tD(fieldLabel(row.item, _ctx.labelKey)), 3)
                                                    )),
                                                    if (isTrue(isItemSelected(row.item) || isItemHalfSelected(row.item))) {
                                                        _cE("view", _uM("key" to 0, "class" to "bs-check-icon-wrapper"), _uA(
                                                            _cE("text", _uM("class" to "bs-check-icon"), _tD(getSelectIcon(row.item)), 1)
                                                        ))
                                                    } else {
                                                        _cC("v-if", true)
                                                    }
                                                ), 10, _uA(
                                                    "onClick"
                                                ))
                                            }), 128),
                                            if (isTrue(unref(loading) && unref(visibleRows).length > 0)) {
                                                _cE("view", _uM("key" to 0, "class" to "bs-load-more"), _uA(
                                                    _cE("text", _uM("class" to "bs-load-more-text"), "加载中...")
                                                ))
                                            } else {
                                                if (isTrue(!unref(hasMore) && unref(visibleRows).length > 0)) {
                                                    _cE("view", _uM("key" to 1, "class" to "bs-load-more"), _uA(
                                                        _cE("text", _uM("class" to "bs-load-more-text"), "没有更多了")
                                                    ))
                                                } else {
                                                    _cC("v-if", true)
                                                }
                                            }
                                        ))
                                    }
                                }
                            ), 36),
                            if (isTrue(_ctx.multiple)) {
                                _cE("view", _uM("key" to 1, "class" to "bs-confirm-bar"), _uA(
                                    _cE("view", _uM("class" to "bs-confirm-info"), _uA(
                                        if (unref(selectedItems).length > 0) {
                                            _cE("text", _uM("key" to 0, "class" to "bs-confirm-count"), " 已选 " + _tD(unref(selectedItems).length) + " 项 ", 1)
                                        } else {
                                            _cC("v-if", true)
                                        }
                                    )),
                                    _cE("view", _uM("class" to "bs-confirm-btns"), _uA(
                                        _cE("view", _uM("class" to "bs-btn-clear", "onClick" to clearAllSelected), _uA(
                                            _cE("text", _uM("class" to "bs-btn-clear-text"), "清空")
                                        )),
                                        _cE("view", _uM("class" to "bs-btn-confirm", "onClick" to confirmMultiple), _uA(
                                            _cE("text", _uM("class" to "bs-btn-confirm-text"), "确定")
                                        ))
                                    ))
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                        ), 6)
                    } else {
                        _cC("v-if", true)
                    }
                ), 64)
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("bs-trigger-wrapper" to _pS(_uM("flexDirection" to "row", "alignItems" to "center")), "bs-trigger-default" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to 20, "paddingBottom" to 20, "borderBottomWidth" to 1, "borderBottomColor" to "#E5E5E5", "borderBottomStyle" to "solid")), "bs-trigger-text" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "fontSize" to 14, "color" to "#333333")), "bs-trigger-placeholder" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "fontSize" to 14, "color" to "#999999")), "bs-trigger-actions" to _pS(_uM("flexDirection" to "row", "alignItems" to "center")), "bs-trigger-action" to _pS(_uM("alignItems" to "center", "justifyContent" to "center", "width" to 28, "height" to 28, "marginLeft" to 8, "borderTopLeftRadius" to 999, "borderTopRightRadius" to 999, "borderBottomRightRadius" to 999, "borderBottomLeftRadius" to 999, "backgroundColor" to "#F5F7FA")), "bs-trigger-action-icon" to _pS(_uM("fontSize" to 14, "color" to "#666666", "lineHeight" to "14px")), "bs-trigger-arrow" to _pS(_uM("width" to 20, "height" to 20, "alignItems" to "center", "justifyContent" to "center", "marginLeft" to 8)), "bs-arrow-icon" to _pS(_uM("fontSize" to 20, "color" to "#CCCCCC", "lineHeight" to "20px")), "bs-overlay" to _pS(_uM("position" to "fixed", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0, "backgroundColor" to "rgba(0,0,0,0)", "zIndex" to 998, "opacity" to 0, "pointerEvents" to "none", "transitionProperty" to "opacity,backgroundColor", "transitionDuration" to "320ms", "transitionTimingFunction" to "ease")), "bs-overlay-active" to _pS(_uM("backgroundColor" to "rgba(10,18,30,0.32)", "opacity" to 1, "pointerEvents" to "auto")), "bs-panel" to _pS(_uM("position" to "fixed", "left" to 0, "right" to 0, "bottom" to 0, "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 0, "borderBottomLeftRadius" to 0, "zIndex" to 999, "flexDirection" to "column", "opacity" to 0, "transform" to "translateY(48px)", "pointerEvents" to "none", "boxShadow" to "0 -18px 44px rgba(15, 23, 42, 0.14)", "transitionProperty" to "transform,opacity", "transitionDuration" to "340ms", "transitionTimingFunction" to "ease")), "bs-panel-active" to _pS(_uM("opacity" to 1, "transform" to "translateY(0px)", "pointerEvents" to "auto")), "bs-handle-wrap" to _pS(_uM("alignItems" to "center", "paddingTop" to 10, "paddingBottom" to 4)), "bs-handle" to _pS(_uM("width" to 44, "height" to 5, "borderTopLeftRadius" to 999, "borderTopRightRadius" to 999, "borderBottomRightRadius" to 999, "borderBottomLeftRadius" to 999, "backgroundColor" to "#D9DEE7")), "bs-header" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to 10, "paddingBottom" to 16, "paddingLeft" to 16, "paddingRight" to 16, "borderBottomWidth" to 1, "borderBottomColor" to "#F0F0F0", "borderBottomStyle" to "solid")), "bs-header-title" to _pS(_uM("fontSize" to 16, "fontWeight" to "bold", "color" to "#333333")), "bs-header-close" to _pS(_uM("width" to 32, "height" to 32, "alignItems" to "center", "justifyContent" to "center")), "bs-close-icon" to _pS(_uM("fontSize" to 16, "color" to "#999999")), "bs-search-bar" to _pS(_uM("paddingTop" to 12, "paddingBottom" to 12, "paddingLeft" to 16, "paddingRight" to 16, "borderBottomWidth" to 1, "borderBottomColor" to "#F0F0F0", "borderBottomStyle" to "solid")), "bs-search-inner" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "backgroundColor" to "#F5F5F5", "borderTopLeftRadius" to 20, "borderTopRightRadius" to 20, "borderBottomRightRadius" to 20, "borderBottomLeftRadius" to 20, "paddingLeft" to 12, "paddingRight" to 12, "height" to 36)), "bs-search-icon" to _pS(_uM("fontSize" to 14, "color" to "#999999", "marginRight" to 6)), "bs-search-input" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "fontSize" to 14, "color" to "#333333", "height" to 36)), "bs-search-clear" to _pS(_uM("width" to 20, "height" to 20, "alignItems" to "center", "justifyContent" to "center", "marginLeft" to 4)), "bs-clear-icon" to _pS(_uM("fontSize" to 12, "color" to "#999999")), "bs-tags-bar" to _pS(_uM("flexDirection" to "row", "paddingTop" to 8, "paddingBottom" to 8, "paddingLeft" to 16, "paddingRight" to 16, "borderBottomWidth" to 1, "borderBottomColor" to "#F0F0F0", "borderBottomStyle" to "solid")), "bs-tags-inner" to _pS(_uM("flexDirection" to "row", "flexWrap" to "nowrap")), "bs-tag" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "backgroundColor" to "#E8F0FE", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 4, "paddingBottom" to 4, "paddingLeft" to 10, "paddingRight" to 10, "marginRight" to 8)), "bs-tag-text" to _pS(_uM("fontSize" to 12, "color" to "#4A90E2")), "bs-tag-remove" to _pS(_uM("width" to 16, "height" to 16, "alignItems" to "center", "justifyContent" to "center", "marginLeft" to 4)), "bs-tag-remove-icon" to _pS(_uM("fontSize" to 10, "color" to "#4A90E2")), "bs-list-item" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to 14, "paddingBottom" to 14, "paddingLeft" to 16, "paddingRight" to 16, "borderBottomWidth" to 1, "borderBottomColor" to "#F5F5F5", "borderBottomStyle" to "solid")), "bs-list-item-selected" to _pS(_uM("backgroundColor" to "#F0F5FF")), "bs-item-main" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "row", "alignItems" to "center", "marginRight" to 12)), "bs-tree-prefix" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "flexShrink" to 0)), "bs-tree-indent" to _pS(_uM("height" to 1, "flexShrink" to 0)), "bs-tree-toggle" to _pS(_uM("width" to 24, "height" to 24, "alignItems" to "center", "justifyContent" to "center", "marginRight" to 4, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#F7F8FA")), "bs-tree-toggle-placeholder" to _pS(_uM("width" to 24, "height" to 24, "marginRight" to 4)), "bs-tree-toggle-icon" to _pS(_uM("fontSize" to 18, "color" to "#8A8F99", "lineHeight" to "18px")), "bs-item-label" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "fontSize" to 14, "color" to "#333333")), "bs-item-label-selected" to _pS(_uM("color" to "#4A90E2")), "bs-check-icon-wrapper" to _pS(_uM("width" to 22, "height" to 22, "alignItems" to "center", "justifyContent" to "center", "borderTopLeftRadius" to 11, "borderTopRightRadius" to 11, "borderBottomRightRadius" to 11, "borderBottomLeftRadius" to 11, "backgroundColor" to "#E8F0FE")), "bs-check-icon" to _pS(_uM("fontSize" to 15, "color" to "#4A90E2", "lineHeight" to "15px")), "bs-state-wrapper" to _pS(_uM("alignItems" to "center", "justifyContent" to "center", "paddingTop" to 60, "paddingBottom" to 60)), "bs-state-text" to _pS(_uM("fontSize" to 14, "color" to "#999999")), "bs-load-more" to _pS(_uM("alignItems" to "center", "paddingTop" to 16, "paddingBottom" to 16)), "bs-load-more-text" to _pS(_uM("fontSize" to 12, "color" to "#BBBBBB")), "bs-confirm-bar" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to 12, "paddingBottom" to 12, "paddingLeft" to 16, "paddingRight" to 16, "borderTopWidth" to 1, "borderTopColor" to "#F0F0F0", "borderTopStyle" to "solid")), "bs-confirm-info" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "bs-confirm-count" to _pS(_uM("fontSize" to 13, "color" to "#666666")), "bs-confirm-btns" to _pS(_uM("flexDirection" to "row", "alignItems" to "center")), "bs-btn-clear" to _pS(_uM("paddingTop" to 8, "paddingBottom" to 8, "paddingLeft" to 16, "paddingRight" to 16, "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "#DDDDDD", "borderRightColor" to "#DDDDDD", "borderBottomColor" to "#DDDDDD", "borderLeftColor" to "#DDDDDD", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "marginRight" to 12, "alignItems" to "center", "justifyContent" to "center")), "bs-btn-clear-text" to _pS(_uM("fontSize" to 14, "color" to "#666666")), "bs-btn-confirm" to _pS(_uM("paddingTop" to 8, "paddingBottom" to 8, "paddingLeft" to 24, "paddingRight" to 24, "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "backgroundColor" to "#4A90E2", "alignItems" to "center", "justifyContent" to "center")), "bs-btn-confirm-text" to _pS(_uM("fontSize" to 14, "color" to "#FFFFFF")), "@TRANSITION" to _uM("bs-overlay" to _uM("property" to "opacity,backgroundColor", "duration" to "320ms", "timingFunction" to "ease"), "bs-panel" to _uM("property" to "transform,opacity", "duration" to "340ms", "timingFunction" to "ease")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("change" to null, "multiChange" to null, "open" to null, "close" to null, "edit" to null, "add" to null)
        var props = _nP(_uM("fetchData" to _uM("type" to "Function", "required" to true), "value" to _uM("type" to "String", "required" to false, "default" to ""), "valueText" to _uM("type" to "String", "required" to false, "default" to ""), "values" to _uM("type" to "Array", "required" to false, "default" to fun(): UTSArray<String> {
            return _uA()
        }
        ), "multiple" to _uM("type" to "Boolean", "required" to false, "default" to false), "tree" to _uM("type" to "Boolean", "required" to false, "default" to false), "childrenKey" to _uM("type" to "String", "required" to false, "default" to "children"), "defaultExpandAll" to _uM("type" to "Boolean", "required" to false, "default" to false), "expandOnClickNode" to _uM("type" to "Boolean", "required" to false, "default" to false), "checkStrictly" to _uM("type" to "Boolean", "required" to false, "default" to true), "accordion" to _uM("type" to "Boolean", "required" to false, "default" to false), "placeholder" to _uM("type" to "String", "required" to false, "default" to "请选择"), "title" to _uM("type" to "String", "required" to false, "default" to "请选择"), "searchPlaceholder" to _uM("type" to "String", "required" to false, "default" to "请输入关键词搜索"), "emptyText" to _uM("type" to "String", "required" to false, "default" to "暂无数据"), "disabled" to _uM("type" to "Boolean", "required" to false, "default" to false), "labelKey" to _uM("type" to "String", "required" to false, "default" to "text"), "valueKey" to _uM("type" to "String", "required" to false, "default" to "value"), "pageSize" to _uM("type" to "Number", "required" to false, "default" to 20), "searchDelay" to _uM("type" to "Number", "required" to false, "default" to 300), "closeOnOverlay" to _uM("type" to "Boolean", "required" to false, "default" to true), "showEditAction" to _uM("type" to "Boolean", "required" to false, "default" to true), "showAddAction" to _uM("type" to "Boolean", "required" to false, "default" to true), "editActionText" to _uM("type" to "String", "required" to false, "default" to "编辑"), "addActionText" to _uM("type" to "String", "required" to false, "default" to "新增")))
        var propsNeedCastKeys = _uA(
            "value",
            "valueText",
            "values",
            "multiple",
            "tree",
            "childrenKey",
            "defaultExpandAll",
            "expandOnClickNode",
            "checkStrictly",
            "accordion",
            "placeholder",
            "title",
            "searchPlaceholder",
            "emptyText",
            "disabled",
            "labelKey",
            "valueKey",
            "pageSize",
            "searchDelay",
            "closeOnOverlay",
            "showEditAction",
            "showAddAction",
            "editActionText",
            "addActionText"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
