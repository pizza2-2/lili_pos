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
open class GenUniModulesLiliDataComponentsLiliDataLiliData : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var value: String by `$props`
    open var title: String by `$props`
    open var placeholder: String by `$props`
    open var disabled: Boolean by `$props`
    open var showTime: Boolean by `$props`
    open var defaultToToday: Boolean by `$props`
    open var startYear: Number by `$props`
    open var endYear: Number by `$props`
    open var minuteStep: Number by `$props`
    open var closeOnOverlay: Boolean by `$props`
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
    open var setValue: (value: String) -> Unit
        get() {
            return unref(this.`$exposed`["setValue"]) as (value: String) -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "setValue", value)
        }
    open var getValue: () -> UTSJSONObject
        get() {
            return unref(this.`$exposed`["getValue"]) as () -> UTSJSONObject
        }
        set(value) {
            setRefValue(this.`$exposed`, "getValue", value)
        }
    open var clearValue: () -> Unit
        get() {
            return unref(this.`$exposed`["clearValue"]) as () -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "clearValue", value)
        }
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenUniModulesLiliDataComponentsLiliDataLiliData, __setupCtx: SetupContext) -> Any? = fun(__props, __setupCtx): Any? {
            val __expose = __setupCtx.expose
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenUniModulesLiliDataComponentsLiliDataLiliData
            val _cache = __ins.renderCache
            val props = __props
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val renderVisible = ref<Boolean>(false)
            val overlayVisible = ref<Boolean>(false)
            val panelVisible = ref<Boolean>(false)
            val internalValue = ref<String>("")
            val draftValue = ref<String>("")
            val pickerIndexes = ref(_uA<Number>(0, 0, 0, 0, 0))
            var enterTimer: Number? = null
            var closeTimer: Number? = null
            val PANEL_ANIMATION_DURATION: Number = 340
            fun gen_normalizeNumber_fn(value: Number): String {
                if (value < 10) {
                    return "0" + value
                }
                return "" + value
            }
            val normalizeNumber = ::gen_normalizeNumber_fn
            fun gen_parseNumber_fn(text: String, fallback: Number): Number {
                val value = parseInt(text)
                if (isNaN(value)) {
                    return fallback
                }
                return value
            }
            val parseNumber = ::gen_parseNumber_fn
            fun gen_normalizeDateTime_fn(value: String): String {
                if (value.length >= 10 && value.length < 16) {
                    val yearText = value.substring(0, 4)
                    val monthText = value.substring(5, 7)
                    val dayText = value.substring(8, 10)
                    return "" + yearText + "-" + monthText + "-" + dayText
                }
                if (value.length < 16) {
                    return ""
                }
                val yearText = value.substring(0, 4)
                val monthText = value.substring(5, 7)
                val dayText = value.substring(8, 10)
                val hourText = value.substring(11, 13)
                val minuteText = value.substring(14, 16)
                val secondText = if (value.length >= 19) {
                    value.substring(17, 19)
                } else {
                    "00"
                }
                return "" + yearText + "-" + monthText + "-" + dayText + " " + hourText + ":" + minuteText + ":" + secondText
            }
            val normalizeDateTime = ::gen_normalizeDateTime_fn
            fun gen_getSafeMinuteStep_fn(): Number {
                if (props.minuteStep <= 0) {
                    return 1
                }
                if (props.minuteStep > 30) {
                    return 30
                }
                return props.minuteStep
            }
            val getSafeMinuteStep = ::gen_getSafeMinuteStep_fn
            fun gen_getCurrentYear_fn(): Number {
                val now = Date()
                return now.getFullYear()
            }
            val getCurrentYear = ::gen_getCurrentYear_fn
            fun gen_getNormalizedEndYear_fn(): Number {
                val fallback = getCurrentYear() + 10
                return if (props.endYear > 0) {
                    props.endYear
                } else {
                    fallback
                }
            }
            val getNormalizedEndYear = ::gen_getNormalizedEndYear_fn
            fun gen_getNormalizedStartYear_fn(): Number {
                val fallback = getCurrentYear() - 10
                val startYear = if (props.startYear > 0) {
                    props.startYear
                } else {
                    fallback
                }
                val endYear = getNormalizedEndYear()
                if (startYear > endYear) {
                    return endYear
                }
                return startYear
            }
            val getNormalizedStartYear = ::gen_getNormalizedStartYear_fn
            fun gen_isLeapYear_fn(year: Number): Boolean {
                if (year % 400 == 0) {
                    return true
                }
                if (year % 100 == 0) {
                    return false
                }
                return year % 4 == 0
            }
            val isLeapYear = ::gen_isLeapYear_fn
            fun gen_getDaysInMonth_fn(year: Number, month: Number): Number {
                if (month == 2) {
                    return if (isLeapYear(year)) {
                        29
                    } else {
                        28
                    }
                }
                if (month == 4 || month == 6 || month == 9 || month == 11) {
                    return 30
                }
                return 31
            }
            val getDaysInMonth = ::gen_getDaysInMonth_fn
            fun gen_buildDefaultValue_fn(): String {
                val now = Date()
                val year = now.getFullYear()
                val month = now.getMonth() + 1
                val day = now.getDate()
                if (!props.showTime) {
                    return "" + year + "-" + normalizeNumber(month) + "-" + normalizeNumber(day)
                }
                val hour = now.getHours()
                val minuteStep = getSafeMinuteStep()
                val minute = Math.floor(now.getMinutes() / minuteStep) * minuteStep
                return "" + year + "-" + normalizeNumber(month) + "-" + normalizeNumber(day) + " " + normalizeNumber(hour) + ":" + normalizeNumber(minute) + ":00"
            }
            val buildDefaultValue = ::gen_buildDefaultValue_fn
            fun gen_buildYearOptions_fn(): UTSArray<String> {
                val result: UTSArray<String> = _uA()
                val startYear = getNormalizedStartYear()
                val endYear = getNormalizedEndYear()
                run {
                    var year = startYear
                    while(year <= endYear){
                        result.push("" + year)
                        year++
                    }
                }
                return result
            }
            val buildYearOptions = ::gen_buildYearOptions_fn
            fun gen_buildMonthOptions_fn(): UTSArray<String> {
                val result: UTSArray<String> = _uA()
                run {
                    var month: Number = 1
                    while(month <= 12){
                        result.push(normalizeNumber(month))
                        month++
                    }
                }
                return result
            }
            val buildMonthOptions = ::gen_buildMonthOptions_fn
            fun gen_buildHourOptions_fn(): UTSArray<String> {
                val result: UTSArray<String> = _uA()
                run {
                    var hour: Number = 0
                    while(hour < 24){
                        result.push(normalizeNumber(hour))
                        hour++
                    }
                }
                return result
            }
            val buildHourOptions = ::gen_buildHourOptions_fn
            fun gen_buildMinuteOptions_fn(): UTSArray<String> {
                val result: UTSArray<String> = _uA()
                val step = getSafeMinuteStep()
                run {
                    var minute: Number = 0
                    while(minute < 60){
                        result.push(normalizeNumber(minute))
                        minute += step
                    }
                }
                return result
            }
            val buildMinuteOptions = ::gen_buildMinuteOptions_fn
            fun gen_buildDayOptions_fn(year: Number, month: Number): UTSArray<String> {
                val result: UTSArray<String> = _uA()
                val maxDay = getDaysInMonth(year, month)
                run {
                    var day: Number = 1
                    while(day <= maxDay){
                        result.push(normalizeNumber(day))
                        day++
                    }
                }
                return result
            }
            val buildDayOptions = ::gen_buildDayOptions_fn
            val yearOptions = computed<UTSArray<String>>(fun(): UTSArray<String> {
                return buildYearOptions()
            }
            )
            val showTime = computed<Boolean>(fun(): Boolean {
                return props.showTime
            }
            )
            val monthOptions = computed<UTSArray<String>>(fun(): UTSArray<String> {
                return buildMonthOptions()
            }
            )
            val hourOptions = computed<UTSArray<String>>(fun(): UTSArray<String> {
                return buildHourOptions()
            }
            )
            val minuteOptions = computed<UTSArray<String>>(fun(): UTSArray<String> {
                return buildMinuteOptions()
            }
            )
            val dayOptions = computed<UTSArray<String>>(fun(): UTSArray<String> {
                val source = if (draftValue.value != "") {
                    draftValue.value
                } else {
                    buildDefaultValue()
                }
                val year = parseNumber(source.substring(0, 4), getCurrentYear())
                val month = parseNumber(source.substring(5, 7), 1)
                return buildDayOptions(year, month)
            }
            )
            val displayText = computed<String>(fun(): String {
                return internalValue.value
            }
            )
            fun gen_clampIndex_fn(index: Number, length: Number): Number {
                if (length <= 0) {
                    return 0
                }
                if (index < 0) {
                    return 0
                }
                if (index >= length) {
                    return length - 1
                }
                return index
            }
            val clampIndex = ::gen_clampIndex_fn
            fun gen_buildDateTimeText_fn(yearText: String, monthText: String, dayText: String, hourText: String, minuteText: String): String {
                if (!props.showTime) {
                    return "" + yearText + "-" + monthText + "-" + dayText
                }
                return "" + yearText + "-" + monthText + "-" + dayText + " " + hourText + ":" + minuteText + ":00"
            }
            val buildDateTimeText = ::gen_buildDateTimeText_fn
            fun gen_syncPickerIndexesFromValue_fn(value: String) {
                val effectiveValue = if (value != "") {
                    value
                } else {
                    buildDefaultValue()
                }
                val yearText = effectiveValue.substring(0, 4)
                val monthText = effectiveValue.substring(5, 7)
                val dayText = effectiveValue.substring(8, 10)
                val hourText = if (props.showTime && effectiveValue.length >= 13) {
                    effectiveValue.substring(11, 13)
                } else {
                    "00"
                }
                val minuteText = if (props.showTime && effectiveValue.length >= 16) {
                    effectiveValue.substring(14, 16)
                } else {
                    "00"
                }
                var yearIndex = yearOptions.value.indexOf(yearText)
                if (yearIndex < 0) {
                    yearIndex = 0
                }
                var monthIndex = monthOptions.value.indexOf(monthText)
                if (monthIndex < 0) {
                    monthIndex = 0
                }
                val dayList = buildDayOptions(parseNumber(yearText, getCurrentYear()), parseNumber(monthText, 1))
                var dayIndex = dayList.indexOf(dayText)
                if (dayIndex < 0) {
                    dayIndex = 0
                }
                var hourIndex = hourOptions.value.indexOf(hourText)
                if (hourIndex < 0) {
                    hourIndex = 0
                }
                val step = getSafeMinuteStep()
                val minuteValue = parseNumber(minuteText, 0)
                val alignedMinute = Math.floor(minuteValue / step) * step
                var minuteIndex = minuteOptions.value.indexOf(normalizeNumber(alignedMinute))
                if (minuteIndex < 0) {
                    minuteIndex = 0
                }
                if (props.showTime) {
                    pickerIndexes.value = _uA(
                        yearIndex,
                        monthIndex,
                        dayIndex,
                        hourIndex,
                        minuteIndex
                    )
                } else {
                    pickerIndexes.value = _uA(
                        yearIndex,
                        monthIndex,
                        dayIndex
                    )
                }
                draftValue.value = buildDateTimeText(yearOptions.value[yearIndex], monthOptions.value[monthIndex], dayList[dayIndex], hourOptions.value[hourIndex], minuteOptions.value[minuteIndex])
            }
            val syncPickerIndexesFromValue = ::gen_syncPickerIndexesFromValue_fn
            fun gen_clearAnimationTimers_fn() {
                if (enterTimer != null) {
                    clearTimeout(enterTimer!!)
                    enterTimer = null
                }
                if (closeTimer != null) {
                    clearTimeout(closeTimer!!)
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
            fun gen_openPanel_fn() {
                if (props.disabled) {
                    return
                }
                if (renderVisible.value) {
                    return
                }
                syncPickerIndexesFromValue(internalValue.value)
                renderVisible.value = true
                overlayVisible.value = false
                panelVisible.value = false
                activatePanelAnimation()
                emit("open")
            }
            val openPanel = ::gen_openPanel_fn
            fun gen_closePanel_fn() {
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
            val closePanel = ::gen_closePanel_fn
            fun gen_cancelSelection_fn() {
                emit("cancel")
                closePanel()
            }
            val cancelSelection = ::gen_cancelSelection_fn
            fun gen_buildPayload_fn(value: String): UTSJSONObject {
                if (!props.showTime) {
                    return _uO("value" to value, "date" to value, "time" to "")
                }
                return _uO("value" to value, "date" to value.substring(0, 10), "time" to value.substring(11, 19))
            }
            val buildPayload = ::gen_buildPayload_fn
            fun gen_confirmSelection_fn() {
                internalValue.value = draftValue.value
                val payload = buildPayload(internalValue.value)
                emit("change", payload)
                emit("confirm", payload)
                closePanel()
            }
            val confirmSelection = ::gen_confirmSelection_fn
            fun gen_handleOverlayClick_fn() {
                if (props.closeOnOverlay) {
                    cancelSelection()
                }
            }
            val handleOverlayClick = ::gen_handleOverlayClick_fn
            fun gen_handlePickerChange_fn(event: Any) {
                val pickerEvent = event as UniPickerViewChangeEvent
                val detail = pickerEvent.detail
                if (detail == null) {
                    return
                }
                val indexes = detail.value as UTSArray<Number>
                val yearIndex = clampIndex(indexes[0], yearOptions.value.length)
                val monthIndex = clampIndex(indexes[1], monthOptions.value.length)
                val yearText = yearOptions.value[yearIndex]
                val monthText = monthOptions.value[monthIndex]
                val days = buildDayOptions(parseNumber(yearText, getCurrentYear()), parseNumber(monthText, 1))
                val dayIndex = clampIndex(indexes[2], days.length)
                val hourIndex = if (props.showTime) {
                    clampIndex(indexes[3], hourOptions.value.length)
                } else {
                    0
                }
                val minuteIndex = if (props.showTime) {
                    clampIndex(indexes[4], minuteOptions.value.length)
                } else {
                    0
                }
                if (props.showTime) {
                    pickerIndexes.value = _uA(
                        yearIndex,
                        monthIndex,
                        dayIndex,
                        hourIndex,
                        minuteIndex
                    )
                } else {
                    pickerIndexes.value = _uA(
                        yearIndex,
                        monthIndex,
                        dayIndex
                    )
                }
                draftValue.value = buildDateTimeText(yearText, monthText, days[dayIndex], hourOptions.value[hourIndex], minuteOptions.value[minuteIndex])
            }
            val handlePickerChange = ::gen_handlePickerChange_fn
            fun gen_setValue_fn(value: String) {
                internalValue.value = normalizeDateTime(value)
                syncPickerIndexesFromValue(internalValue.value)
            }
            val setValue = ::gen_setValue_fn
            fun gen_getValue_fn(): UTSJSONObject {
                val value = internalValue.value
                if (value == "") {
                    return _uO("value" to "", "date" to "", "time" to "")
                }
                return buildPayload(value)
            }
            val getValue = ::gen_getValue_fn
            fun gen_clearValue_fn() {
                internalValue.value = ""
                draftValue.value = buildDefaultValue()
                syncPickerIndexesFromValue(draftValue.value)
            }
            val clearValue = ::gen_clearValue_fn
            watch(fun(): String {
                return props.value
            }
            , fun(newValue: String){
                val normalized = normalizeDateTime(newValue)
                val nextValue = if (normalized == "" && props.defaultToToday) {
                    buildDefaultValue()
                } else {
                    normalized
                }
                if (nextValue != internalValue.value) {
                    internalValue.value = nextValue
                    if (!renderVisible.value) {
                        syncPickerIndexesFromValue(internalValue.value)
                    }
                }
            }
            )
            onMounted(fun(){
                internalValue.value = normalizeDateTime(props.value)
                if (internalValue.value == "" && props.defaultToToday) {
                    internalValue.value = buildDefaultValue()
                }
                syncPickerIndexesFromValue(internalValue.value)
            }
            )
            onUnmounted(fun(){
                clearAnimationTimers()
            }
            )
            __expose(_uM("openPanel" to openPanel, "closePanel" to closePanel, "setValue" to setValue, "getValue" to getValue, "clearValue" to clearValue))
            return fun(): Any? {
                val _component_picker_view_column = resolveComponent("picker-view-column")
                val _component_picker_view = resolveComponent("picker-view")
                return _cE(Fragment, null, _uA(
                    _cE("view", _uM("class" to "dt-trigger-wrapper", "onClick" to openPanel), _uA(
                        renderSlot(_ctx.`$slots`, "trigger", _uO(), fun(): UTSArray<Any> {
                            return _uA(
                                _cE("view", _uM("class" to "dt-trigger-default"), _uA(
                                    _cE("text", _uM("class" to _nC(if (unref(displayText) != "") {
                                        "dt-trigger-text"
                                    } else {
                                        "dt-trigger-placeholder"
                                    }
                                    )), _tD(if (unref(displayText) != "") {
                                        unref(displayText)
                                    } else {
                                        _ctx.placeholder
                                    }
                                    ), 3),
                                    _cE("view", _uM("class" to "dt-trigger-arrow"), _uA(
                                        _cE("text", _uM("class" to "dt-arrow-icon"), _tD(if (unref(renderVisible)) {
                                            "⌄"
                                        } else {
                                            "›"
                                        }
                                        ), 1)
                                    ))
                                ))
                            )
                        }
                        )
                    )),
                    if (isTrue(unref(renderVisible))) {
                        _cE("view", _uM("key" to 0, "class" to _nC(if (unref(overlayVisible)) {
                            "dt-overlay dt-overlay-active"
                        } else {
                            "dt-overlay"
                        }), "onClick" to handleOverlayClick), null, 2)
                    } else {
                        _cC("v-if", true)
                    }
                    ,
                    if (isTrue(unref(renderVisible))) {
                        _cE("view", _uM("key" to 1, "class" to _nC(if (unref(panelVisible)) {
                            "dt-panel dt-panel-active"
                        } else {
                            "dt-panel"
                        })), _uA(
                            _cE("view", _uM("class" to "dt-handle-wrap"), _uA(
                                _cE("view", _uM("class" to "dt-handle"))
                            )),
                            _cE("view", _uM("class" to "dt-header"), _uA(
                                _cE("view", _uM("class" to "dt-header-action", "onClick" to cancelSelection), _uA(
                                    _cE("text", _uM("class" to "dt-header-action-text dt-header-action-text-light"), "取消")
                                )),
                                _cE("text", _uM("class" to "dt-header-title"), _tD(_ctx.title), 1),
                                _cE("view", _uM("class" to "dt-header-action", "onClick" to confirmSelection), _uA(
                                    _cE("text", _uM("class" to "dt-header-action-text"), "确定")
                                ))
                            )),
                            _cE("view", _uM("class" to "dt-preview-wrap"), _uA(
                                _cE("text", _uM("class" to "dt-preview-text"), _tD(unref(draftValue)), 1)
                            )),
                            _cV(_component_picker_view, _uM("class" to "dt-picker", "value" to unref(pickerIndexes), "indicator-style" to "height:48px;", "mask-class" to "dt-picker-mask", "onChange" to handlePickerChange), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                return _uA(
                                    _cV(_component_picker_view_column, null, _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                        return _uA(
                                            _cE(Fragment, null, RenderHelpers.renderList(unref(yearOptions), fun(item, index, __index, _cached): Any {
                                                return _cE("view", _uM("key" to ("y_" + index), "class" to "dt-picker-item"), _uA(
                                                    _cE("text", _uM("class" to "dt-picker-item-text"), _tD(item), 1)
                                                ))
                                            }), 128)
                                        )
                                    }), "_" to 1)),
                                    _cV(_component_picker_view_column, null, _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                        return _uA(
                                            _cE(Fragment, null, RenderHelpers.renderList(unref(monthOptions), fun(item, index, __index, _cached): Any {
                                                return _cE("view", _uM("key" to ("m_" + index), "class" to "dt-picker-item"), _uA(
                                                    _cE("text", _uM("class" to "dt-picker-item-text"), _tD(item), 1)
                                                ))
                                            }), 128)
                                        )
                                    }), "_" to 1)),
                                    _cV(_component_picker_view_column, null, _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                        return _uA(
                                            _cE(Fragment, null, RenderHelpers.renderList(unref(dayOptions), fun(item, index, __index, _cached): Any {
                                                return _cE("view", _uM("key" to ("d_" + index), "class" to "dt-picker-item"), _uA(
                                                    _cE("text", _uM("class" to "dt-picker-item-text"), _tD(item), 1)
                                                ))
                                            }), 128)
                                        )
                                    }), "_" to 1)),
                                    if (isTrue(unref(showTime))) {
                                        _cV(_component_picker_view_column, _uM("key" to 0), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                            return _uA(
                                                _cE(Fragment, null, RenderHelpers.renderList(unref(hourOptions), fun(item, index, __index, _cached): Any {
                                                    return _cE("view", _uM("key" to ("h_" + index), "class" to "dt-picker-item"), _uA(
                                                        _cE("text", _uM("class" to "dt-picker-item-text"), _tD(item), 1)
                                                    ))
                                                }), 128)
                                            )
                                        }), "_" to 1))
                                    } else {
                                        _cC("v-if", true)
                                    },
                                    if (isTrue(unref(showTime))) {
                                        _cV(_component_picker_view_column, _uM("key" to 1), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                            return _uA(
                                                _cE(Fragment, null, RenderHelpers.renderList(unref(minuteOptions), fun(item, index, __index, _cached): Any {
                                                    return _cE("view", _uM("key" to ("i_" + index), "class" to "dt-picker-item"), _uA(
                                                        _cE("text", _uM("class" to "dt-picker-item-text"), _tD(item), 1)
                                                    ))
                                                }), 128)
                                            )
                                        }), "_" to 1))
                                    } else {
                                        _cC("v-if", true)
                                    }
                                )
                            }), "_" to 1), 8, _uA(
                                "value"
                            ))
                        ), 2)
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
                return _uM("dt-trigger-wrapper" to _pS(_uM("flexDirection" to "row", "alignItems" to "center")), "dt-trigger-default" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "minHeight" to 44, "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingLeft" to 12, "paddingRight" to 12, "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "backgroundColor" to "#F8FAFC")), "dt-trigger-text" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "fontSize" to 14, "color" to "#111827", "lineHeight" to "20px")), "dt-trigger-placeholder" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "fontSize" to 14, "color" to "#9CA3AF", "lineHeight" to "20px")), "dt-trigger-arrow" to _pS(_uM("width" to 20, "height" to 20, "alignItems" to "center", "justifyContent" to "center", "marginLeft" to 8)), "dt-arrow-icon" to _pS(_uM("fontSize" to 20, "color" to "#CBD5E1", "lineHeight" to "20px")), "dt-overlay" to _pS(_uM("position" to "fixed", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0, "backgroundColor" to "rgba(0,0,0,0)", "zIndex" to 998, "opacity" to 0, "transitionProperty" to "opacity,backgroundColor", "transitionDuration" to "320ms", "transitionTimingFunction" to "ease")), "dt-overlay-active" to _pS(_uM("backgroundColor" to "rgba(10,18,30,0.32)", "opacity" to 1)), "dt-panel" to _pS(_uM("position" to "fixed", "left" to 0, "right" to 0, "bottom" to 0, "height" to 360, "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 0, "borderBottomLeftRadius" to 0, "zIndex" to 999, "flexDirection" to "column", "opacity" to 0, "transform" to "translateY(48px)", "boxShadow" to "0 -18px 44px rgba(15, 23, 42, 0.14)", "transitionProperty" to "transform,opacity", "transitionDuration" to "340ms", "transitionTimingFunction" to "ease")), "dt-panel-active" to _pS(_uM("opacity" to 1, "transform" to "translateY(0px)")), "dt-handle-wrap" to _pS(_uM("alignItems" to "center", "paddingTop" to 10, "paddingBottom" to 4)), "dt-handle" to _pS(_uM("width" to 44, "height" to 5, "borderTopLeftRadius" to 999, "borderTopRightRadius" to 999, "borderBottomRightRadius" to 999, "borderBottomLeftRadius" to 999, "backgroundColor" to "#D9DEE7")), "dt-header" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to 10, "paddingBottom" to 16, "paddingLeft" to 16, "paddingRight" to 16, "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#F1F5F9")), "dt-header-title" to _pS(_uM("fontSize" to 16, "fontWeight" to "600", "color" to "#111827", "lineHeight" to "20px")), "dt-header-action" to _pS(_uM("minWidth" to 44, "alignItems" to "center", "justifyContent" to "center")), "dt-header-action-text" to _pS(_uM("fontSize" to 14, "fontWeight" to "600", "color" to "#2563EB", "lineHeight" to "20px")), "dt-header-action-text-light" to _pS(_uM("color" to "#94A3B8")), "dt-preview-wrap" to _pS(_uM("alignItems" to "center", "justifyContent" to "center", "paddingTop" to 12, "paddingBottom" to 12, "backgroundColor" to "#F8FAFC", "marginLeft" to 16, "marginRight" to 16, "marginTop" to 12, "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14)), "dt-preview-text" to _pS(_uM("fontSize" to 15, "fontWeight" to "600", "color" to "#0F172A", "lineHeight" to "20px")), "dt-picker" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 220, "marginTop" to 12)), "dt-picker-item" to _pS(_uM("height" to 48, "alignItems" to "center", "justifyContent" to "center")), "dt-picker-item-text" to _pS(_uM("fontSize" to 16, "color" to "#334155", "lineHeight" to "20px")), "@TRANSITION" to _uM("dt-overlay" to _uM("property" to "opacity,backgroundColor", "duration" to "320ms", "timingFunction" to "ease"), "dt-panel" to _uM("property" to "transform,opacity", "duration" to "340ms", "timingFunction" to "ease")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("change" to null, "confirm" to null, "cancel" to null, "open" to null, "close" to null)
        var props = _nP(_uM("value" to _uM("type" to "String", "required" to false, "default" to ""), "title" to _uM("type" to "String", "required" to false, "default" to "选择时间"), "placeholder" to _uM("type" to "String", "required" to false, "default" to "请选择时间"), "disabled" to _uM("type" to "Boolean", "required" to false, "default" to false), "showTime" to _uM("type" to "Boolean", "required" to false, "default" to true), "defaultToToday" to _uM("type" to "Boolean", "required" to false, "default" to false), "startYear" to _uM("type" to "Number", "required" to false, "default" to 2000), "endYear" to _uM("type" to "Number", "required" to false, "default" to 2099), "minuteStep" to _uM("type" to "Number", "required" to false, "default" to 1), "closeOnOverlay" to _uM("type" to "Boolean", "required" to false, "default" to true)))
        var propsNeedCastKeys = _uA(
            "value",
            "title",
            "placeholder",
            "disabled",
            "showTime",
            "defaultToToday",
            "startYear",
            "endYear",
            "minuteStep",
            "closeOnOverlay"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
