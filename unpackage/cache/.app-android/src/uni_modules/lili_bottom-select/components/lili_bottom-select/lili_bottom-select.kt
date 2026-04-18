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
import io.dcloud.uniapp.extapi.getWindowInfo as uni_getWindowInfo
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelect : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var fetchData: (params: UTSJSONObject) -> UTSPromise<UTSJSONObject> by `$props`
    open var value: String by `$props`
    open var values: UTSArray<String> by `$props`
    open var multiple: Boolean by `$props`
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
            val internalText = ref<String>("")
            val textInitialized = ref<Boolean>(false)
            val selectedItems = ref(_uA<UTSJSONObject>())
            val keyword = ref<String>("")
            val displayList = ref(_uA<UTSJSONObject>())
            val currentPage = ref<Number>(1)
            val total = ref<Number>(0)
            val loading = ref<Boolean>(false)
            val hasMore = ref<Boolean>(true)
            val listLoaded = ref<Boolean>(false)
            val panelStyle = ref<String>("")
            var searchTimer: Number? = null
            var enterTimer: Number? = null
            var closeTimer: Number? = null
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
                return v as String
            }
            val fieldLabel = ::gen_fieldLabel_fn
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
            fun gen_buildFetchParams_fn(page: Number, keywordValue: String, id: String): UTSJSONObject {
                return _uO("page" to page, "pageSize" to props.pageSize, "keyword" to keywordValue, "id" to id)
            }
            val buildFetchParams = ::gen_buildFetchParams_fn
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
            fun gen_updateTextFromList_fn() {
                run {
                    var i: Number = 0
                    while(i < displayList.value.length){
                        val item = displayList.value[i]
                        if (fieldToStr(item, props.valueKey) == internalValue.value) {
                            internalText.value = fieldLabel(item, props.labelKey)
                            textInitialized.value = true
                            break
                        }
                        i++
                    }
                }
            }
            val updateTextFromList = ::gen_updateTextFromList_fn
            fun gen_syncFromList_fn(vals: UTSArray<String>) {
                if (vals.length == 0) {
                    selectedItems.value = _uA()
                    return
                }
                val matched: UTSArray<UTSJSONObject> = _uA()
                run {
                    var i: Number = 0
                    while(i < displayList.value.length){
                        val item = displayList.value[i]
                        if (vals.includes(fieldToStr(item, props.valueKey))) {
                            matched.push(item)
                        }
                        i++
                    }
                }
                selectedItems.value = matched
            }
            val syncFromList = ::gen_syncFromList_fn
            fun gen_loadData_fn(isReset: Boolean): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (loading.value) {
                            return@w1
                        }
                        if (!isReset && !hasMore.value) {
                            return@w1
                        }
                        loading.value = true
                        val page = if (isReset) {
                            1
                        } else {
                            currentPage.value
                        }
                        try {
                            val res = await(props.fetchData(buildFetchParams(page, keyword.value, "")))
                            val data = getListField(res, "data")
                            total.value = getNumberField(res, "total")
                            if (isReset) {
                                displayList.value = data
                                currentPage.value = 2
                                listLoaded.value = true
                            } else {
                                val merged: UTSArray<UTSJSONObject> = displayList.value.concat(data)
                                displayList.value = merged
                                currentPage.value = currentPage.value + 1
                            }
                            hasMore.value = displayList.value.length < total.value && data.length >= props.pageSize
                            if (!props.multiple && internalValue.value != "" && !textInitialized.value) {
                                updateTextFromList()
                            }
                            if (props.multiple) {
                                val curVals = selectedItems.value.map<String>(fun(item: UTSJSONObject): String {
                                    return fieldToStr(item, props.valueKey)
                                }
                                )
                                syncFromList(curVals)
                            }
                        }
                         catch (e: Throwable) {
                            console.error("lili_bottom-select loadData 失败:", e, " at uni_modules/lili_bottom-select/components/lili_bottom-select/lili_bottom-select.uvue:348")
                            uni_showToast(ShowToastOptions(title = "数据加载失败", icon = "none"))
                        }
                         finally {
                            loading.value = false
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
                            run {
                                var i: Number = 0
                                while(i < data.length){
                                    val item = data[i]
                                    if (fieldToStr(item, props.valueKey) == value) {
                                        internalText.value = fieldLabel(item, props.labelKey)
                                        textInitialized.value = true
                                        break
                                    }
                                    i++
                                }
                            }
                        }
                         catch (e: Throwable) {
                            console.error("lili_bottom-select fetchTextByValue 失败:", e, " at uni_modules/lili_bottom-select/components/lili_bottom-select/lili_bottom-select.uvue:369")
                        }
                })
            }
            val fetchTextByValue = ::gen_fetchTextByValue_fn
            fun gen_syncMultiValuesFromProps_fn(vals: UTSArray<String>) {
                if (vals.length == 0) {
                    selectedItems.value = _uA()
                    return
                }
                if (displayList.value.length > 0) {
                    syncFromList(vals)
                }
            }
            val syncMultiValuesFromProps = ::gen_syncMultiValuesFromProps_fn
            val displayText = computed<String>(fun(): String {
                if (props.multiple) {
                    if (selectedItems.value.length == 0) {
                        return ""
                    }
                    val texts = selectedItems.value.map<String>(fun(item: UTSJSONObject): String {
                        return fieldLabel(item, props.labelKey)
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
                        if (!textInitialized.value) {
                            fetchTextByValue(newVal)
                        }
                    } else {
                        internalText.value = ""
                        textInitialized.value = false
                    }
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
                    fetchTextByValue(internalValue.value)
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
                        if (renderVisible.value) {
                            return@w1
                        }
                        renderVisible.value = true
                        overlayVisible.value = false
                        panelVisible.value = false
                        activatePanelAnimation()
                        emit("open")
                        keyword.value = ""
                        if (!listLoaded.value) {
                            await(loadData(true))
                        } else {
                            currentPage.value = 1
                            hasMore.value = displayList.value.length < total.value
                        }
                        if (props.multiple) {
                            val curVals = selectedItems.value.map<String>(fun(item: UTSJSONObject): String {
                                return fieldToStr(item, props.valueKey)
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
                    renderVisible.value = false
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
                val itemVal = fieldToStr(item, props.valueKey)
                if (props.multiple) {
                    run {
                        var i: Number = 0
                        while(i < selectedItems.value.length){
                            if (fieldToStr(selectedItems.value[i], props.valueKey) == itemVal) {
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
            fun gen_confirmSingleItem_fn(item: UTSJSONObject) {
                val value = fieldToStr(item, props.valueKey)
                val text = fieldLabel(item, props.labelKey)
                internalValue.value = value
                internalText.value = text
                textInitialized.value = true
                emit("change", _uO("value" to value, "text" to text, "item" to item))
                close()
            }
            val confirmSingleItem = ::gen_confirmSingleItem_fn
            fun gen_toggleMultiItem_fn(item: UTSJSONObject) {
                val kVal = fieldToStr(item, props.valueKey)
                val idx = selectedItems.value.findIndex(fun(s: UTSJSONObject): Boolean {
                    return fieldToStr(s, props.valueKey) == kVal
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
            fun gen_removeSelectedItem_fn(item: UTSJSONObject) {
                val kVal = fieldToStr(item, props.valueKey)
                val newList: UTSArray<UTSJSONObject> = _uA()
                run {
                    var i: Number = 0
                    while(i < selectedItems.value.length){
                        if (fieldToStr(selectedItems.value[i], props.valueKey) != kVal) {
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
                    return fieldToStr(item, props.valueKey)
                }
                )
                val texts = selectedItems.value.map<String>(fun(item: UTSJSONObject): String {
                    return fieldLabel(item, props.labelKey)
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
                return SelectChangePayload(value = internalValue.value, text = internalText.value, item = UTSJSONObject())
            }
            val getValue = ::gen_getValue_fn
            fun gen_getValues_fn(): MultiSelectChangePayload {
                val values = selectedItems.value.map<String>(fun(item: UTSJSONObject): String {
                    return fieldToStr(item, props.valueKey)
                }
                )
                val texts = selectedItems.value.map<String>(fun(item: UTSJSONObject): String {
                    return fieldLabel(item, props.labelKey)
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
                listLoaded.value = false
                keyword.value = ""
                hasMore.value = true
                currentPage.value = 1
            }
            val reset = ::gen_reset_fn
            onUnmounted(fun(){
                clearAnimationTimers()
                if (searchTimer != null) {
                    val timer = searchTimer
                    clearTimeout(timer!!)
                    searchTimer = null
                }
            }
            )
            __expose(_uM("openPanel" to openPanel, "closePanel" to closePanel, "setValue" to setValue, "setValues" to setValues, "getValue" to getValue, "getValues" to getValues, "clearValue" to clearValue, "clearValues" to clearValues, "refreshData" to refreshData, "preloadList" to preloadList, "reset" to reset))
            return fun(): Any? {
                return _cE(Fragment, null, _uA(
                    _cE("view", _uM("class" to "bs-trigger-wrapper", "onClick" to open), _uA(
                        renderSlot(_ctx.`$slots`, "trigger", UTSJSONObject(), fun(): UTSArray<Any> {
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
                                            _cE("text", _uM("class" to "bs-arrow-icon"), _tD(if (unref(renderVisible)) {
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
                                                return _cE("view", _uM("key" to item[_ctx.valueKey], "class" to "bs-tag"), _uA(
                                                    _cE("text", _uM("class" to "bs-tag-text"), _tD(item[_ctx.labelKey]), 1),
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
                                if (isTrue(unref(loading) && unref(displayList).length == 0)) {
                                    _cE("view", _uM("key" to 0, "class" to "bs-state-wrapper"), _uA(
                                        _cE("text", _uM("class" to "bs-state-text"), "加载中...")
                                    ))
                                } else {
                                    if (isTrue(!unref(loading) && unref(displayList).length == 0 && unref(listLoaded))) {
                                        _cE("view", _uM("key" to 1, "class" to "bs-state-wrapper"), _uA(
                                            _cE("text", _uM("class" to "bs-state-text"), _tD(_ctx.emptyText), 1)
                                        ))
                                    } else {
                                        _cE("view", _uM("key" to 2), _uA(
                                            _cE(Fragment, null, RenderHelpers.renderList(unref(displayList), fun(item, __key, __index, _cached): Any {
                                                return _cE("view", _uM("key" to item[_ctx.valueKey], "class" to _nC(if (isItemSelected(item)) {
                                                    "bs-list-item bs-list-item-selected"
                                                } else {
                                                    "bs-list-item"
                                                }), "onClick" to fun(){
                                                    onItemClick(item)
                                                }), _uA(
                                                    _cE("text", _uM("class" to _nC(if (isItemSelected(item)) {
                                                        "bs-item-label bs-item-label-selected"
                                                    } else {
                                                        "bs-item-label"
                                                    })), _tD(item[_ctx.labelKey]), 3),
                                                    if (isTrue(isItemSelected(item))) {
                                                        _cE("view", _uM("key" to 0, "class" to "bs-check-icon-wrapper"), _uA(
                                                            _cE("text", _uM("class" to "bs-check-icon"), "✓")
                                                        ))
                                                    } else {
                                                        _cC("v-if", true)
                                                    }
                                                ), 10, _uA(
                                                    "onClick"
                                                ))
                                            }), 128),
                                            if (isTrue(unref(loading) && unref(displayList).length > 0)) {
                                                _cE("view", _uM("key" to 0, "class" to "bs-load-more"), _uA(
                                                    _cE("text", _uM("class" to "bs-load-more-text"), "加载中...")
                                                ))
                                            } else {
                                                if (isTrue(!unref(hasMore) && unref(displayList).length > 0)) {
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
                return _uM("bs-trigger-wrapper" to _pS(_uM("flexDirection" to "row", "alignItems" to "center")), "bs-trigger-default" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to 20, "paddingBottom" to 20, "borderBottomWidth" to 1, "borderBottomColor" to "#E5E5E5", "borderBottomStyle" to "solid")), "bs-trigger-text" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "fontSize" to 14, "color" to "#333333")), "bs-trigger-placeholder" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "fontSize" to 14, "color" to "#999999")), "bs-trigger-actions" to _pS(_uM("flexDirection" to "row", "alignItems" to "center")), "bs-trigger-action" to _pS(_uM("alignItems" to "center", "justifyContent" to "center", "width" to 28, "height" to 28, "marginLeft" to 8, "borderTopLeftRadius" to 999, "borderTopRightRadius" to 999, "borderBottomRightRadius" to 999, "borderBottomLeftRadius" to 999, "backgroundColor" to "#F5F7FA")), "bs-trigger-action-icon" to _pS(_uM("fontSize" to 14, "color" to "#666666", "lineHeight" to "14px")), "bs-trigger-arrow" to _pS(_uM("width" to 20, "height" to 20, "alignItems" to "center", "justifyContent" to "center", "marginLeft" to 8)), "bs-arrow-icon" to _pS(_uM("fontSize" to 20, "color" to "#CCCCCC", "lineHeight" to "20px")), "bs-overlay" to _pS(_uM("position" to "fixed", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0, "backgroundColor" to "rgba(0,0,0,0)", "zIndex" to 998, "opacity" to 0, "transitionProperty" to "opacity,backgroundColor", "transitionDuration" to "320ms", "transitionTimingFunction" to "ease")), "bs-overlay-active" to _pS(_uM("backgroundColor" to "rgba(10,18,30,0.32)", "opacity" to 1)), "bs-panel" to _pS(_uM("position" to "fixed", "left" to 0, "right" to 0, "bottom" to 0, "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 0, "borderBottomLeftRadius" to 0, "zIndex" to 999, "flexDirection" to "column", "opacity" to 0, "transform" to "translateY(48px)", "boxShadow" to "0 -18px 44px rgba(15, 23, 42, 0.14)", "transitionProperty" to "transform,opacity", "transitionDuration" to "340ms", "transitionTimingFunction" to "ease")), "bs-panel-active" to _pS(_uM("opacity" to 1, "transform" to "translateY(0px)")), "bs-handle-wrap" to _pS(_uM("alignItems" to "center", "paddingTop" to 10, "paddingBottom" to 4)), "bs-handle" to _pS(_uM("width" to 44, "height" to 5, "borderTopLeftRadius" to 999, "borderTopRightRadius" to 999, "borderBottomRightRadius" to 999, "borderBottomLeftRadius" to 999, "backgroundColor" to "#D9DEE7")), "bs-header" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to 10, "paddingBottom" to 16, "paddingLeft" to 16, "paddingRight" to 16, "borderBottomWidth" to 1, "borderBottomColor" to "#F0F0F0", "borderBottomStyle" to "solid")), "bs-header-title" to _pS(_uM("fontSize" to 16, "fontWeight" to "bold", "color" to "#333333")), "bs-header-close" to _pS(_uM("width" to 32, "height" to 32, "alignItems" to "center", "justifyContent" to "center")), "bs-close-icon" to _pS(_uM("fontSize" to 16, "color" to "#999999")), "bs-search-bar" to _pS(_uM("paddingTop" to 12, "paddingBottom" to 12, "paddingLeft" to 16, "paddingRight" to 16, "borderBottomWidth" to 1, "borderBottomColor" to "#F0F0F0", "borderBottomStyle" to "solid")), "bs-search-inner" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "backgroundColor" to "#F5F5F5", "borderTopLeftRadius" to 20, "borderTopRightRadius" to 20, "borderBottomRightRadius" to 20, "borderBottomLeftRadius" to 20, "paddingLeft" to 12, "paddingRight" to 12, "height" to 36)), "bs-search-icon" to _pS(_uM("fontSize" to 14, "color" to "#999999", "marginRight" to 6)), "bs-search-input" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "fontSize" to 14, "color" to "#333333", "height" to 36)), "bs-search-clear" to _pS(_uM("width" to 20, "height" to 20, "alignItems" to "center", "justifyContent" to "center", "marginLeft" to 4)), "bs-clear-icon" to _pS(_uM("fontSize" to 12, "color" to "#999999")), "bs-tags-bar" to _pS(_uM("flexDirection" to "row", "paddingTop" to 8, "paddingBottom" to 8, "paddingLeft" to 16, "paddingRight" to 16, "borderBottomWidth" to 1, "borderBottomColor" to "#F0F0F0", "borderBottomStyle" to "solid")), "bs-tags-inner" to _pS(_uM("flexDirection" to "row", "flexWrap" to "nowrap")), "bs-tag" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "backgroundColor" to "#E8F0FE", "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "paddingTop" to 4, "paddingBottom" to 4, "paddingLeft" to 10, "paddingRight" to 10, "marginRight" to 8)), "bs-tag-text" to _pS(_uM("fontSize" to 12, "color" to "#4A90E2")), "bs-tag-remove" to _pS(_uM("width" to 16, "height" to 16, "alignItems" to "center", "justifyContent" to "center", "marginLeft" to 4)), "bs-tag-remove-icon" to _pS(_uM("fontSize" to 10, "color" to "#4A90E2")), "bs-list-item" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to 14, "paddingBottom" to 14, "paddingLeft" to 16, "paddingRight" to 16, "borderBottomWidth" to 1, "borderBottomColor" to "#F5F5F5", "borderBottomStyle" to "solid")), "bs-list-item-selected" to _pS(_uM("backgroundColor" to "#F0F5FF")), "bs-item-label" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "fontSize" to 14, "color" to "#333333", "marginRight" to 12)), "bs-item-label-selected" to _pS(_uM("color" to "#4A90E2")), "bs-check-icon-wrapper" to _pS(_uM("width" to 20, "height" to 20, "alignItems" to "center", "justifyContent" to "center")), "bs-check-icon" to _pS(_uM("fontSize" to 16, "color" to "#4A90E2")), "bs-state-wrapper" to _pS(_uM("alignItems" to "center", "justifyContent" to "center", "paddingTop" to 60, "paddingBottom" to 60)), "bs-state-text" to _pS(_uM("fontSize" to 14, "color" to "#999999")), "bs-load-more" to _pS(_uM("alignItems" to "center", "paddingTop" to 16, "paddingBottom" to 16)), "bs-load-more-text" to _pS(_uM("fontSize" to 12, "color" to "#BBBBBB")), "bs-confirm-bar" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to 12, "paddingBottom" to 12, "paddingLeft" to 16, "paddingRight" to 16, "borderTopWidth" to 1, "borderTopColor" to "#F0F0F0", "borderTopStyle" to "solid")), "bs-confirm-info" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "bs-confirm-count" to _pS(_uM("fontSize" to 13, "color" to "#666666")), "bs-confirm-btns" to _pS(_uM("flexDirection" to "row", "alignItems" to "center")), "bs-btn-clear" to _pS(_uM("paddingTop" to 8, "paddingBottom" to 8, "paddingLeft" to 16, "paddingRight" to 16, "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopColor" to "#DDDDDD", "borderRightColor" to "#DDDDDD", "borderBottomColor" to "#DDDDDD", "borderLeftColor" to "#DDDDDD", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "marginRight" to 12, "alignItems" to "center", "justifyContent" to "center")), "bs-btn-clear-text" to _pS(_uM("fontSize" to 14, "color" to "#666666")), "bs-btn-confirm" to _pS(_uM("paddingTop" to 8, "paddingBottom" to 8, "paddingLeft" to 24, "paddingRight" to 24, "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "backgroundColor" to "#4A90E2", "alignItems" to "center", "justifyContent" to "center")), "bs-btn-confirm-text" to _pS(_uM("fontSize" to 14, "color" to "#FFFFFF")), "@TRANSITION" to _uM("bs-overlay" to _uM("property" to "opacity,backgroundColor", "duration" to "320ms", "timingFunction" to "ease"), "bs-panel" to _uM("property" to "transform,opacity", "duration" to "340ms", "timingFunction" to "ease")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("change" to null, "multiChange" to null, "open" to null, "close" to null, "edit" to null, "add" to null)
        var props = _nP(_uM("fetchData" to _uM("type" to "Function", "required" to true), "value" to _uM("type" to "String", "required" to false, "default" to ""), "values" to _uM("type" to "Array", "required" to false, "default" to fun(): UTSArray<String> {
            return _uA()
        }
        ), "multiple" to _uM("type" to "Boolean", "required" to false, "default" to false), "placeholder" to _uM("type" to "String", "required" to false, "default" to "请选择"), "title" to _uM("type" to "String", "required" to false, "default" to "请选择"), "searchPlaceholder" to _uM("type" to "String", "required" to false, "default" to "请输入关键词搜索"), "emptyText" to _uM("type" to "String", "required" to false, "default" to "暂无数据"), "disabled" to _uM("type" to "Boolean", "required" to false, "default" to false), "labelKey" to _uM("type" to "String", "required" to false, "default" to "text"), "valueKey" to _uM("type" to "String", "required" to false, "default" to "value"), "pageSize" to _uM("type" to "Number", "required" to false, "default" to 20), "searchDelay" to _uM("type" to "Number", "required" to false, "default" to 300), "closeOnOverlay" to _uM("type" to "Boolean", "required" to false, "default" to true), "showEditAction" to _uM("type" to "Boolean", "required" to false, "default" to true), "showAddAction" to _uM("type" to "Boolean", "required" to false, "default" to true), "editActionText" to _uM("type" to "String", "required" to false, "default" to "编辑"), "addActionText" to _uM("type" to "String", "required" to false, "default" to "新增")))
        var propsNeedCastKeys = _uA(
            "value",
            "values",
            "multiple",
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
