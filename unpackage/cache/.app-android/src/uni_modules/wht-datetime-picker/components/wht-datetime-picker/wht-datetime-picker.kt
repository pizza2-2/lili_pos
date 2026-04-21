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
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenUniModulesWhtDatetimePickerComponentsWhtDatetimePickerWhtDatetimePicker : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {
        this.`$watch`(fun(): Any? {
            return this.value
        }
        , fun(kVal: Any) {
            if (!this.visible) {
                if (this.isRange) {
                    this.rangeValues = if (UTSArray.isArray(kVal)) {
                        (kVal as UTSArray<Any>).map(fun(v): Any {
                            return this.parseDate(v)
                        })
                    } else {
                        _uA(
                            Date(),
                            Date()
                        )
                    }
                } else {
                    this.currentDate = this.parseDate(kVal)
                }
                this.`$nextTick`(fun(){
                    this.initData()
                    this.updateCurrentValue()
                }
                )
            }
        }
        , WatchOptions(immediate = true))
        this.`$watch`(fun(): Any? {
            return this.mode
        }
        , fun() {
            this.`$nextTick`(fun(){
                this.initData()
                this.updateCurrentValue()
            }
            )
        }
        , WatchOptions(immediate = true))
        this.`$watch`(fun(): Any? {
            return this.popupVisible
        }
        , fun(kVal: Boolean) {
            if (kVal) {
                this.show(this.value)
                return
            }
            if (this.visible) {
                this.hide()
            }
        }
        , WatchOptions(immediate = true))
        this.`$watch`(fun(): Any? {
            return this.currentValue
        }
        , fun(kVal: UTSArray<Any?>) {
            if (!UTSArray.isArray(kVal) || (kVal as UTSArray<Any>).some(fun(v): Boolean {
                return UTSAndroid.`typeof`(v) !== "number"
            }
            )) {
                console.warn("Invalid currentValue:", kVal, " at uni_modules/wht-datetime-picker/components/wht-datetime-picker/wht-datetime-picker.vue:272")
                return
            }
            this.updateDateFromValue()
        }
        , WatchOptions(deep = true))
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        val _component_picker_view_column = resolveComponent("picker-view-column")
        val _component_picker_view = resolveComponent("picker-view")
        return _cE("view", _uM("class" to "datetime-picker"), _uA(
            if (isTrue(_ctx.visible)) {
                _cE("view", _uM("key" to 0, "class" to "datetime-picker__mask", "onClick" to _ctx.handleCancel, "onTouchmove" to withModifiers(fun(){}, _uA(
                    "stop",
                    "prevent"
                ))), null, 40, _uA(
                    "onClick"
                ))
            } else {
                _cC("v-if", true)
            }
            ,
            _cE("view", _uM("class" to _nC(_uA(
                "datetime-picker__container",
                _uM("datetime-picker__container--show" to _ctx.visible)
            ))), _uA(
                _cE("view", _uM("class" to "datetime-picker__header"), _uA(
                    _cE("text", _uM("class" to "datetime-picker__btn", "onClick" to _ctx.handleCancel), "取消", 8, _uA(
                        "onClick"
                    )),
                    _cE("text", _uM("class" to "datetime-picker__title"), _tD(_ctx.displayTitle), 1),
                    _cE("text", _uM("class" to "datetime-picker__btn datetime-picker__btn--confirm", "onClick" to _ctx.handleConfirm), "确定", 8, _uA(
                        "onClick"
                    ))
                )),
                if (_ctx.quickOptions.length > 0) {
                    _cE("view", _uM("key" to 0, "class" to "datetime-picker__quick"), _uA(
                        _cE(Fragment, null, RenderHelpers.renderList(_ctx.quickOptions, fun(option, index, __index, _cached): Any {
                            return _cE("text", _uM("key" to index, "class" to _nC(_uA(
                                "datetime-picker__quick-item",
                                _uM("datetime-picker__quick-item--active" to (_ctx.currentQuickIndex === index))
                            )), "onClick" to fun(){
                                _ctx.handleQuickSelect(option, index)
                            }), _tD(option.label), 11, _uA(
                                "onClick"
                            ))
                        }), 128)
                    ))
                } else {
                    _cC("v-if", true)
                }
                ,
                if (isTrue(_ctx.isRange)) {
                    _cE("view", _uM("key" to 1, "class" to "datetime-picker__range-tabs"), _uA(
                        _cE("text", _uM("class" to _nC(_uA(
                            "datetime-picker__range-tab",
                            _uM("datetime-picker__range-tab--active" to (_ctx.rangeIndex === 0))
                        )), "onClick" to fun(){
                            _ctx.handleRangeChange(0)
                        }), "开始时间", 10, _uA(
                            "onClick"
                        )),
                        _cE("text", _uM("class" to _nC(_uA(
                            "datetime-picker__range-tab",
                            _uM("datetime-picker__range-tab--active" to (_ctx.rangeIndex === 1))
                        )), "onClick" to fun(){
                            _ctx.handleRangeChange(1)
                        }), "结束时间", 10, _uA(
                            "onClick"
                        ))
                    ))
                } else {
                    _cC("v-if", true)
                }
                ,
                _cE("view", _uM("class" to "datetime-picker__body"), _uA(
                    _cV(_component_picker_view, _uM("value" to _ctx.pickerValue, "onChange" to _ctx.handleChange, "class" to "datetime-picker__picker", "indicator-style" to _ctx.indicatorStyle, "mask-style" to _ctx.maskStyle, "style" to _nS(_uM("height" to ("" + _ctx.height + "px")))), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                        return _uA(
                            if (isTrue(_ctx.showYear)) {
                                _cV(_component_picker_view_column, _uM("key" to 0), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                    return _uA(
                                        _cE(Fragment, null, RenderHelpers.renderList(_ctx.years, fun(year, __key, __index, _cached): Any {
                                            return _cE("view", _uM("class" to "datetime-picker__item", "key" to year), _tD(year) + "年", 1)
                                        }), 128)
                                    )
                                }), "_" to 1))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            if (isTrue(_ctx.showMonth)) {
                                _cV(_component_picker_view_column, _uM("key" to 1), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                    return _uA(
                                        _cE(Fragment, null, RenderHelpers.renderList(_ctx.months, fun(month, __key, __index, _cached): Any {
                                            return _cE("view", _uM("class" to "datetime-picker__item", "key" to month), _tD(month) + "月", 1)
                                        }), 128)
                                    )
                                }), "_" to 1))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            if (isTrue(_ctx.showDay)) {
                                _cV(_component_picker_view_column, _uM("key" to 2), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                    return _uA(
                                        _cE(Fragment, null, RenderHelpers.renderList(_ctx.days, fun(day, __key, __index, _cached): Any {
                                            return _cE("view", _uM("class" to "datetime-picker__item", "key" to day), _tD(day) + "日", 1)
                                        }), 128)
                                    )
                                }), "_" to 1))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            if (isTrue(_ctx.showHour)) {
                                _cV(_component_picker_view_column, _uM("key" to 3), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                    return _uA(
                                        _cE(Fragment, null, RenderHelpers.renderList(_ctx.hours, fun(hour, __key, __index, _cached): Any {
                                            return _cE("view", _uM("class" to "datetime-picker__item", "key" to hour), _tD(hour) + "时", 1)
                                        }), 128)
                                    )
                                }), "_" to 1))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            if (isTrue(_ctx.showMinute)) {
                                _cV(_component_picker_view_column, _uM("key" to 4), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                    return _uA(
                                        _cE(Fragment, null, RenderHelpers.renderList(_ctx.minutes, fun(minute, __key, __index, _cached): Any {
                                            return _cE("view", _uM("class" to "datetime-picker__item", "key" to minute), _tD(minute) + "分", 1)
                                        }), 128)
                                    )
                                }), "_" to 1))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            if (isTrue(_ctx.showSecond)) {
                                _cV(_component_picker_view_column, _uM("key" to 5), _uM("default" to withSlotCtx(fun(): UTSArray<Any> {
                                    return _uA(
                                        _cE(Fragment, null, RenderHelpers.renderList(_ctx.seconds, fun(second, __key, __index, _cached): Any {
                                            return _cE("view", _uM("class" to "datetime-picker__item", "key" to second), _tD(second) + "秒", 1)
                                        }), 128)
                                    )
                                }), "_" to 1))
                            } else {
                                _cC("v-if", true)
                            }
                        )
                    }
                    ), "_" to 1), 8, _uA(
                        "value",
                        "onChange",
                        "indicator-style",
                        "mask-style",
                        "style"
                    ))
                ))
            ), 2)
        ))
    }
    open var value: Any? by `$props`
    open var mode: String by `$props`
    open var title: String by `$props`
    open var popupVisible: Boolean by `$props`
    open var showSeconds: Boolean by `$props`
    open var startYear: Number by `$props`
    open var endYear: Number by `$props`
    open var quickOptions: UTSArray<Any?> by `$props`
    open var height: Number by `$props`
    open var visible: Boolean by `$data`
    open var currentDate: Date by `$data`
    open var rangeValues: UTSArray<Any?> by `$data`
    open var rangeIndex: Number by `$data`
    open var currentValue: UTSArray<Any?> by `$data`
    open var pickerValue: UTSArray<Any?> by `$data`
    open var currentQuickIndex: Number by `$data`
    open var years: UTSArray<Any?> by `$data`
    open var months: UTSArray<Any?> by `$data`
    open var days: UTSArray<Any?> by `$data`
    open var hours: UTSArray<Any?> by `$data`
    open var minutes: UTSArray<Any?> by `$data`
    open var seconds: UTSArray<Any?> by `$data`
    open var isInitialized: Boolean by `$data`
    open var isRange: Boolean by `$data`
    open var showYear: Boolean by `$data`
    open var showMonth: Boolean by `$data`
    open var showDay: Boolean by `$data`
    open var showHour: Boolean by `$data`
    open var showMinute: Boolean by `$data`
    open var showSecond: Boolean by `$data`
    open var columns: UTSArray<Any?> by `$data`
    open var indicatorStyle: String by `$data`
    open var maskStyle: String by `$data`
    open var displayTitle: Any by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        val now = Date()
        return _uM("visible" to false, "currentDate" to now, "rangeValues" to _uA(
            now,
            now
        ), "rangeIndex" to 0, "currentValue" to _uA(), "pickerValue" to _uA(), "currentQuickIndex" to -1, "years" to _uA(), "months" to _uA(), "days" to _uA(), "hours" to _uA(), "minutes" to _uA(), "seconds" to _uA(), "isInitialized" to false, "isRange" to computed<Boolean>(fun(): Boolean {
            return this.mode.includes("range")
        }
        ), "showYear" to computed<Boolean>(fun(): Boolean {
            return !_uA(
                "time",
                "hour-minute",
                "hour-minute-second"
            ).includes(this.mode)
        }
        ), "showMonth" to computed<Boolean>(fun(): Boolean {
            return _uA(
                "datetime",
                "date",
                "year-month",
                "month"
            ).includes(this.mode) || _uA(
                "datetime-range",
                "date-range"
            ).includes(this.mode)
        }
        ), "showDay" to computed<Boolean>(fun(): Boolean {
            return _uA(
                "datetime",
                "date"
            ).includes(this.mode) || _uA(
                "datetime-range",
                "date-range"
            ).includes(this.mode)
        }
        ), "showHour" to computed<Boolean>(fun(): Boolean {
            return _uA(
                "datetime",
                "time",
                "hour-minute",
                "hour-minute-second"
            ).includes(this.mode) || _uA(
                "datetime-range",
                "time-range"
            ).includes(this.mode)
        }
        ), "showMinute" to computed<Boolean>(fun(): Boolean {
            return _uA(
                "datetime",
                "time",
                "hour-minute",
                "hour-minute-second"
            ).includes(this.mode) || _uA(
                "datetime-range",
                "time-range"
            ).includes(this.mode)
        }
        ), "showSecond" to computed<Boolean>(fun(): Boolean {
            return (this.showSeconds && _uA(
                "datetime",
                "time",
                "hour-minute-second"
            ).includes(this.mode)) || (this.showSeconds && _uA(
                "datetime-range",
                "time-range"
            ).includes(this.mode))
        }
        ), "columns" to computed<UTSArray<Any?>>(fun(): UTSArray<Any?> {
            val columns = _uA()
            if (this.showYear) {
                columns.push(this.years)
            }
            if (this.showMonth) {
                columns.push(this.months)
            }
            if (this.showDay) {
                columns.push(this.days)
            }
            if (this.showHour) {
                columns.push(this.hours)
            }
            if (this.showMinute) {
                columns.push(this.minutes)
            }
            if (this.showSecond) {
                columns.push(this.seconds)
            }
            return columns
        }
        ), "indicatorStyle" to computed<String>(fun(): String {
            return "height: 44px; border-top: 1px solid #eee; border-bottom: 1px solid #eee;"
        }
        ), "maskStyle" to computed<String>(fun(): String {
            return "background-image: linear-gradient(180deg, rgba(255,255,255,0.95), rgba(255,255,255,0.6)), linear-gradient(0deg, rgba(255,255,255,0.95), rgba(255,255,255,0.6));"
        }
        ), "displayTitle" to computed<Any>(fun(): Any {
            if (this.title !== "选择时间") {
                return this.title
            }
            val modeMap: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("modeMap", "uni_modules/wht-datetime-picker/components/wht-datetime-picker/wht-datetime-picker.vue", 213, 13), "datetime" to "选择日期时间", "date" to "选择日期", "time" to "选择时间", "year" to "选择年份", "year-month" to "选择年月", "month" to "选择月份", "day" to "选择日期", "hour-minute" to "选择时间", "hour-minute-second" to "选择时间", "datetime-range" to "选择日期时间范围", "date-range" to "选择日期范围", "time-range" to "选择时间范围")
            return modeMap[this.mode] || "选择时间"
        }
        ))
    }
    open fun formatDate(date, type: String = "datetime"): String {
        if (!date) {
            return ""
        }
        try {
            val d = if (date is Date) {
                date as Date
            } else {
                Date(date)
            }
            if (!this.validateDate(d)) {
                return ""
            }
            val year = d.getFullYear()
            val month = String(d.getMonth() + 1).padStart(2, "0")
            val day = String(d.getDate()).padStart(2, "0")
            val hour = String(d.getHours()).padStart(2, "0")
            val minute = String(d.getMinutes()).padStart(2, "0")
            val second = String(d.getSeconds()).padStart(2, "0")
            when (type) {
                "datetime" -> 
                    return "" + year + "-" + month + "-" + day + " " + hour + ":" + minute + (if (this.showSeconds) {
                        ":" + second
                    } else {
                        ""
                    }
                    )
                "date" -> 
                    return "" + year + "-" + month + "-" + day
                "time" -> 
                    return "" + hour + ":" + minute + (if (this.showSeconds) {
                        ":" + second
                    } else {
                        ""
                    }
                    )
                "year" -> 
                    return "" + year
                "year-month" -> 
                    return "" + year + "-" + month
                "month" -> 
                    return month
                "day" -> 
                    return day
                "hour-minute" -> 
                    return "" + hour + ":" + minute
                "hour-minute-second" -> 
                    return "" + hour + ":" + minute + ":" + second
                else -> 
                    return "" + year + "-" + month + "-" + day + " " + hour + ":" + minute + (if (this.showSeconds) {
                        ":" + second
                    } else {
                        ""
                    }
                    )
            }
        }
         catch (error: Throwable) {
            console.error("Format date error:", error, date, " at uni_modules/wht-datetime-picker/components/wht-datetime-picker/wht-datetime-picker.vue:320")
            return ""
        }
    }
    open var parseDate = ::gen_parseDate_fn
    open fun gen_parseDate_fn(value): Any {
        if (!value) {
            return Date()
        }
        try {
            var date
            if (value is Date) {
                date = Date((value as Date).getTime())
            } else if (UTSAndroid.`typeof`(value) === "number" && !isNaN(value as Number)) {
                date = Date(value as Number)
            } else if (UTSAndroid.`typeof`(value) === "string") {
                if ((value as String).includes("T")) {
                    date = Date(value as String)
                } else if ((value as String).includes("-") || (value as String).includes("/")) {
                    val parts = (value as String).split(UTSRegExp("[-\\s:/]", "")).map(fun(p): Number {
                        return parseInt(p)
                    })
                    if (parts.length >= 3) {
                        date = Date(parts[0], parts[1] - 1, parts[2], if (parts.length > 3 && !isNaN(parts[3])) {
                            parts[3]
                        } else {
                            0
                        }
                        , if (parts.length > 4 && !isNaN(parts[4])) {
                            parts[4]
                        } else {
                            0
                        }
                        , if (parts.length > 5 && !isNaN(parts[5])) {
                            parts[5]
                        } else {
                            0
                        }
                        )
                    }
                } else {
                    val timestamp = parseInt(value as String)
                    if (!isNaN(timestamp)) {
                        date = Date(timestamp)
                    }
                }
            }
            return if (date != null && !isNaN(date.getTime())) {
                date
            } else {
                Date()
            }
        }
         catch (error: Throwable) {
            console.error("Parse date error:", error, " at uni_modules/wht-datetime-picker/components/wht-datetime-picker/wht-datetime-picker.vue:360")
            return Date()
        }
    }
    open var show = ::gen_show_fn
    open fun gen_show_fn(value) {
        this.visible = true
        this.currentQuickIndex = -1
        this.rangeIndex = 0
        try {
            if (this.isRange) {
                if (UTSArray.isArray(value) && (value as UTSArray<Any>).length === 2) {
                    this.rangeValues = (value as UTSArray<Any>).map(fun(v): Any {
                        return this.parseDate(v)
                    })
                } else if (UTSAndroid.`typeof`(value) === "string") {
                    val date = this.parseDate(value)
                    this.rangeValues = _uA(
                        date,
                        date
                    )
                } else {
                    val now = Date()
                    this.rangeValues = _uA(
                        now,
                        now
                    )
                }
            } else {
                this.currentDate = this.parseDate(value)
            }
            this.`$nextTick`(fun(){
                this.initData()
                this.updateCurrentValue()
            }
            )
        }
         catch (error: Throwable) {
            console.error("Show picker error:", error, " at uni_modules/wht-datetime-picker/components/wht-datetime-picker/wht-datetime-picker.vue:393")
            val now = Date()
            if (this.isRange) {
                this.rangeValues = _uA(
                    now,
                    now
                )
            } else {
                this.currentDate = now
            }
        }
    }
    open var hide = ::gen_hide_fn
    open fun gen_hide_fn() {
        this.visible = false
    }
    open var handleCancel = ::gen_handleCancel_fn
    open fun gen_handleCancel_fn() {
        this.hide()
        this.`$emit`("cancel")
    }
    open var handleConfirm = ::gen_handleConfirm_fn
    open fun gen_handleConfirm_fn() {
        try {
            if (this.isRange) {
                if (!this.validateDate(this.rangeValues[0]) || !this.validateDate(this.rangeValues[1])) {
                    uni_showToast(ShowToastOptions(title = "日期格式无效", icon = "none"))
                    return
                }
                if (this.rangeValues[1] < this.rangeValues[0]) {
                    uni_showToast(ShowToastOptions(title = "结束时间不能早于开始时间", icon = "none"))
                    return
                }
                val value = this.rangeValues.map(fun(date): Date {
                    return Date(date.getTime())
                })
                val formatted = value.map(fun(date): String {
                    return this.formatDate(date, this.mode.replace("-range", ""))
                })
                this.`$emit`("input", value)
                this.`$emit`("change", value)
                this.`$emit`("confirm", _uO("value" to value, "formatted" to formatted.join(" 至 ")))
            } else {
                if (!this.validateDate(this.currentDate)) {
                    uni_showToast(ShowToastOptions(title = "日期格式无效", icon = "none"))
                    return
                }
                val value = Date(this.currentDate.getTime())
                val formatted = this.formatDate(value, this.mode)
                this.`$emit`("input", value)
                this.`$emit`("change", value)
                this.`$emit`("confirm", _uO("value" to value, "formatted" to formatted))
            }
            this.hide()
        }
         catch (error: Throwable) {
            console.error("Confirm error:", error, " at uni_modules/wht-datetime-picker/components/wht-datetime-picker/wht-datetime-picker.vue:462")
            uni_showToast(ShowToastOptions(title = "操作失败", icon = "none"))
        }
    }
    open var handleQuickSelect = ::gen_handleQuickSelect_fn
    open fun gen_handleQuickSelect_fn(option, index) {
        if (!option || !option.value) {
            console.warn("Invalid quick option:", option, " at uni_modules/wht-datetime-picker/components/wht-datetime-picker/wht-datetime-picker.vue:472")
            return
        }
        this.currentQuickIndex = index
        this.rangeIndex = 0
        try {
            if (this.isRange) {
                var rangeValue = option.value
                if (!UTSArray.isArray(rangeValue)) {
                    val date = this.parseDate(rangeValue)
                    rangeValue = _uA(
                        date,
                        date
                    )
                }
                if (rangeValue.length !== 2) {
                    console.warn("Quick option value should have 2 items for range mode:", option, " at uni_modules/wht-datetime-picker/components/wht-datetime-picker/wht-datetime-picker.vue:490")
                    return
                }
                this.rangeValues = rangeValue.map(fun(v): Any {
                    return this.parseDate(v)
                })
            } else {
                this.currentDate = this.parseDate(option.value)
            }
            this.`$nextTick`(fun(){
                this.initData()
                this.updateCurrentValue()
            }
            )
            if (option.autoConfirm) {
                this.handleConfirm()
            }
        }
         catch (error: Throwable) {
            console.error("Quick select error:", error, " at uni_modules/wht-datetime-picker/components/wht-datetime-picker/wht-datetime-picker.vue:509")
            uni_showToast(ShowToastOptions(title = "快捷选项格式无效", icon = "none"))
        }
    }
    open var handleChange = ::gen_handleChange_fn
    open fun gen_handleChange_fn(e) {
        this.currentValue = e.detail.value
        this.currentQuickIndex = -1
    }
    open var handleColumnChange = ::gen_handleColumnChange_fn
    open fun gen_handleColumnChange_fn() {
        this.initData()
    }
    open var handleRangeChange = ::gen_handleRangeChange_fn
    open fun gen_handleRangeChange_fn(index) {
        if (this.rangeIndex === index) {
            return
        }
        this.rangeIndex = index
        this.`$nextTick`(fun(){
            this.updateCurrentValue()
        }
        )
    }
    open var validateDate = ::gen_validateDate_fn
    open fun gen_validateDate_fn(date): Boolean {
        if (!(date is Date) || isNaN((date as Date).getTime())) {
            console.warn("Invalid date:", date, " at uni_modules/wht-datetime-picker/components/wht-datetime-picker/wht-datetime-picker.vue:536")
            return false
        }
        val year = (date as Date).getFullYear()
        if (year < this.startYear || year > this.endYear) {
            console.warn("Date out of range:", date, " at uni_modules/wht-datetime-picker/components/wht-datetime-picker/wht-datetime-picker.vue:542")
            return false
        }
        return true
    }
    open var initData = ::gen_initData_fn
    open fun gen_initData_fn() {
        if (!this.isInitialized) {
            this.years = _uA()
            run {
                var i = this.startYear
                while(i <= this.endYear){
                    this.years.push(String(i))
                    i++
                }
            }
            this.months = _uA()
            run {
                var i: Number = 1
                while(i <= 12){
                    this.months.push(String(i).padStart(2, "0"))
                    i++
                }
            }
            this.hours = _uA()
            run {
                var i: Number = 0
                while(i <= 23){
                    this.hours.push(String(i).padStart(2, "0"))
                    i++
                }
            }
            this.minutes = _uA()
            run {
                var i: Number = 0
                while(i <= 59){
                    this.minutes.push(String(i).padStart(2, "0"))
                    i++
                }
            }
            this.seconds = _uA()
            run {
                var i: Number = 0
                while(i <= 59){
                    this.seconds.push(String(i).padStart(2, "0"))
                    i++
                }
            }
            this.isInitialized = true
        }
        val date = if (this.isRange) {
            this.rangeValues[this.rangeIndex]
        } else {
            this.currentDate
        }
        val year = date.getFullYear()
        val month = date.getMonth() + 1
        val daysInMonth = Date(year, month, 0).getDate()
        this.days = _uA()
        run {
            var i: Number = 1
            while(i <= daysInMonth){
                this.days.push(String(i).padStart(2, "0"))
                i++
            }
        }
    }
    open var updateCurrentValue = ::gen_updateCurrentValue_fn
    open fun gen_updateCurrentValue_fn() {
        val date = if (this.isRange) {
            this.rangeValues[this.rangeIndex]
        } else {
            this.currentDate
        }
        if (!date || isNaN(date.getTime())) {
            console.warn("Invalid date in updateCurrentValue:", date, " at uni_modules/wht-datetime-picker/components/wht-datetime-picker/wht-datetime-picker.vue:599")
            return
        }
        val values = _uA()
        if (this.showYear) {
            val yearIndex = this.years.findIndex(fun(y): Boolean {
                return parseInt(y) === date.getFullYear()
            }
            )
            values.push(if (yearIndex >= 0) {
                yearIndex
            } else {
                0
            }
            )
        }
        if (this.showMonth) {
            val monthStr = String(date.getMonth() + 1).padStart(2, "0")
            val monthIndex = this.months.findIndex(fun(m): Boolean {
                return m === monthStr
            }
            )
            values.push(if (monthIndex >= 0) {
                monthIndex
            } else {
                0
            }
            )
        }
        if (this.showDay) {
            val dayStr = String(date.getDate()).padStart(2, "0")
            val dayIndex = this.days.findIndex(fun(d): Boolean {
                return d === dayStr
            }
            )
            values.push(if (dayIndex >= 0) {
                dayIndex
            } else {
                0
            }
            )
        }
        if (this.showHour) {
            val hourStr = String(date.getHours()).padStart(2, "0")
            val hourIndex = this.hours.findIndex(fun(h): Boolean {
                return h === hourStr
            }
            )
            values.push(if (hourIndex >= 0) {
                hourIndex
            } else {
                0
            }
            )
        }
        if (this.showMinute) {
            val minuteStr = String(date.getMinutes()).padStart(2, "0")
            val minuteIndex = this.minutes.findIndex(fun(m): Boolean {
                return m === minuteStr
            }
            )
            values.push(if (minuteIndex >= 0) {
                minuteIndex
            } else {
                0
            }
            )
        }
        if (this.showSecond) {
            val secondStr = String(date.getSeconds()).padStart(2, "0")
            val secondIndex = this.seconds.findIndex(fun(s): Boolean {
                return s === secondStr
            }
            )
            values.push(if (secondIndex >= 0) {
                secondIndex
            } else {
                0
            }
            )
        }
        this.currentValue = values
        this.pickerValue = values.slice()
    }
    open var updateDateFromValue = ::gen_updateDateFromValue_fn
    open fun gen_updateDateFromValue_fn() {
        if (!UTSArray.isArray(this.currentValue)) {
            return
        }
        var index: Number = 0
        var year = this.currentDate.getFullYear()
        var month = this.currentDate.getMonth()
        var day = this.currentDate.getDate()
        var hour = this.currentDate.getHours()
        var minute = this.currentDate.getMinutes()
        var second = this.currentDate.getSeconds()
        if (this.showYear && this.currentValue[index] !== undefined) {
            year = parseInt(this.years[this.currentValue[index]])
            index++
        }
        if (this.showMonth && this.currentValue[index] !== undefined) {
            month = parseInt(this.months[this.currentValue[index]]) - 1
            index++
        }
        if (this.showDay && this.currentValue[index] !== undefined) {
            day = parseInt(this.days[this.currentValue[index]])
            index++
        }
        if (this.showHour && this.currentValue[index] !== undefined) {
            hour = parseInt(this.hours[this.currentValue[index]])
            index++
        }
        if (this.showMinute && this.currentValue[index] !== undefined) {
            minute = parseInt(this.minutes[this.currentValue[index]])
            index++
        }
        if (this.showSecond && this.currentValue[index] !== undefined) {
            second = parseInt(this.seconds[this.currentValue[index]])
        }
        val newDate = Date(year, month, day, hour, minute, second)
        if (this.isRange) {
            this.rangeValues[this.rangeIndex] = newDate
            if (this.rangeIndex === 0 && this.rangeValues[1] < newDate) {
                this.rangeValues[1] = Date(newDate)
            }
        } else {
            this.currentDate = newDate
        }
        this.initData()
    }
    companion object {
        var name = "wht-datetime-picker"
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("datetime-picker" to _pS(_uM("position" to "relative", "zIndex" to 999)), "datetime-picker__mask" to _pS(_uM("position" to "fixed", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0, "backgroundImage" to "none", "backgroundColor" to "rgba(0,0,0,0.4)", "zIndex" to 999)), "datetime-picker__container" to _pS(_uM("position" to "fixed", "left" to 0, "right" to 0, "bottom" to 0, "backgroundColor" to "#ffffff", "transform" to "translateY(100%)", "transitionProperty" to "transform", "transitionDuration" to "0.3s", "zIndex" to 1000)), "datetime-picker__container--show" to _pS(_uM("transform" to "translateY(0)")), "datetime-picker__header" to _pS(_uM("display" to "flex", "alignItems" to "center", "justifyContent" to "space-between", "height" to "88rpx", "paddingTop" to 0, "paddingRight" to "30rpx", "paddingBottom" to 0, "paddingLeft" to "30rpx", "fontSize" to "32rpx", "position" to "relative", "content::after" to "''", "position::after" to "absolute", "left::after" to 0, "right::after" to 0, "bottom::after" to 0, "height::after" to "1rpx", "backgroundColor::after" to "#eeeeee", "transform::after" to "scaleY(0.5)")), "datetime-picker__title" to _pS(_uM("color" to "#333333", "fontWeight" to "500")), "datetime-picker__btn" to _pS(_uM("color" to "#999999", "paddingTop" to "20rpx", "paddingRight" to "20rpx", "paddingBottom" to "20rpx", "paddingLeft" to "20rpx")), "datetime-picker__btn--confirm" to _pS(_uM("color" to "#576B95", "fontWeight" to "500")), "datetime-picker__body" to _pS(_uM("position" to "relative")), "datetime-picker__picker" to _pS(_uM("width" to "100%")), "datetime-picker__item" to _pS(_uM("display" to "flex", "justifyContent" to "center", "alignItems" to "center", "height" to 44, "overflow" to "hidden", "fontSize" to 16, "color" to "#333333")), "datetime-picker__quick" to _pS(_uM("display" to "flex", "flexWrap" to "wrap", "paddingTop" to "10rpx", "paddingRight" to "20rpx", "paddingBottom" to "10rpx", "paddingLeft" to "20rpx", "borderBottomWidth" to "1rpx", "borderBottomStyle" to "solid", "borderBottomColor" to "#eeeeee")), "datetime-picker__quick-item" to _pS(_uM("paddingTop" to "6rpx", "paddingRight" to "20rpx", "paddingBottom" to "6rpx", "paddingLeft" to "20rpx", "marginTop" to "6rpx", "marginRight" to "6rpx", "marginBottom" to "6rpx", "marginLeft" to "6rpx", "fontSize" to "24rpx", "color" to "#666666", "backgroundImage" to "none", "backgroundColor" to "#f5f5f5", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx")), "datetime-picker__quick-item--active" to _pS(_uM("color" to "#ffffff", "backgroundImage" to "none", "backgroundColor" to "#007AFF")), "datetime-picker__range-tabs" to _pS(_uM("display" to "flex", "paddingTop" to "20rpx", "paddingRight" to "20rpx", "paddingBottom" to "20rpx", "paddingLeft" to "20rpx", "borderBottomWidth" to "1rpx", "borderBottomStyle" to "solid", "borderBottomColor" to "#eeeeee")), "datetime-picker__range-tab" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "textAlign" to "center", "fontSize" to "28rpx", "color" to "#666666", "paddingTop" to "10rpx", "paddingRight" to 0, "paddingBottom" to "10rpx", "paddingLeft" to 0)), "datetime-picker__range-tab--active" to _pS(_uM("color" to "#007AFF", "position" to "relative", "content::after" to "''", "position::after" to "absolute", "bottom::after" to "-20rpx", "left::after" to "50%", "transform::after" to "translateX(-50%)", "width::after" to "40rpx", "height::after" to "4rpx", "backgroundImage::after" to "none", "backgroundColor::after" to "#007AFF")), "@TRANSITION" to _uM("datetime-picker__container" to _uM("property" to "transform", "duration" to "0.3s")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM("value" to _uM("type" to _uA(
            "Date",
            "Array",
            "String",
            "Number"
        ), "default" to null), "mode" to _uM("type" to "String", "default" to "datetime", "validator" to fun(value): Boolean {
            return _uA(
                "datetime",
                "date",
                "time",
                "year",
                "year-month",
                "month",
                "day",
                "hour-minute",
                "hour-minute-second",
                "datetime-range",
                "date-range",
                "time-range"
            ).includes(value)
        }
        ), "title" to _uM("type" to "String", "default" to "选择时间"), "popupVisible" to _uM("type" to "Boolean", "default" to false), "showSeconds" to _uM("type" to "Boolean", "default" to false), "startYear" to _uM("type" to "Number", "default" to fun(): Number {
            return Date().getFullYear() - 5
        }
        ), "endYear" to _uM("type" to "Number", "default" to fun(): Number {
            return Date().getFullYear() + 5
        }
        ), "quickOptions" to _uM("type" to "Array", "default" to fun(): UTSArray<Any?> {
            return _uA()
        }
        , "validator" to fun(value) {
            return value.every(fun(option): Boolean {
                if (!option.label) {
                    return false
                }
                if (option.value === undefined) {
                    return false
                }
                return true
            }
            )
        }
        ), "height" to _uM("type" to "Number", "default" to fun(): Number {
            return 264
        }
        )))
        var propsNeedCastKeys = _uA(
            "value",
            "mode",
            "title",
            "popupVisible",
            "showSeconds",
            "startYear",
            "endYear",
            "quickOptions",
            "height"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
