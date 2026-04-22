type DateTimePayload = { __$originalPosition?: UTSSourceMapPosition<"DateTimePayload", "uni_modules/lili-data/components/lili-data/lili-data.uvue", 80, 6>;
	value: string
	date: string
	time: string
}

type Props = { __$originalPosition?: UTSSourceMapPosition<"Props", "uni_modules/lili-data/components/lili-data/lili-data.uvue", 86, 6>;
	value?: string
	title?: string
	placeholder?: string
	disabled?: boolean
	showTime?: boolean
	defaultToToday?: boolean
	startYear?: number
	endYear?: number
	minuteStep?: number
	closeOnOverlay?: boolean
}


const __sfc__ = defineComponent({
  __name: 'lili-data',
  props: {
    value: { type: String, required: false, default: '' },
    title: { type: String, required: false, default: '选择时间' },
    placeholder: { type: String, required: false, default: '请选择时间' },
    disabled: { type: Boolean, required: false, default: false },
    showTime: { type: Boolean, required: false, default: true },
    defaultToToday: { type: Boolean, required: false, default: false },
    startYear: { type: Number, required: false, default: 2000 },
    endYear: { type: Number, required: false, default: 2099 },
    minuteStep: { type: Number, required: false, default: 1 },
    closeOnOverlay: { type: Boolean, required: false, default: true }
  },
  emits: ["change", "confirm", "cancel", "open", "close"],
  setup(__props, __setupCtx: SetupContext) {
const __expose = __setupCtx.expose
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

const props = __props

function emit(event: string, ...do_not_transform_spread: Array<any | null>) {
__ins.emit(event, ...do_not_transform_spread)
}

const renderVisible = ref<boolean>(false)
const overlayVisible = ref<boolean>(false)
const panelVisible = ref<boolean>(false)
const internalValue = ref<string>('')
const draftValue = ref<string>('')
const pickerIndexes = ref<number[]>([0, 0, 0, 0, 0])

let enterTimer: number | null = null
let closeTimer: number | null = null
const PANEL_ANIMATION_DURATION = 340

function normalizeNumber(value: number) : string {
	if (value < 10) {
		return '0' + value
	}
	return '' + value
}

function parseNumber(text: string, fallback: number) : number {
	const value = parseInt(text)
	if (isNaN(value)) {
		return fallback
	}
	return value
}

function normalizeDateTime(value: string) : string {
	if (value.length >= 10 && value.length < 16) {
		const yearText = value.substring(0, 4)
		const monthText = value.substring(5, 7)
		const dayText = value.substring(8, 10)
		return `${yearText}-${monthText}-${dayText}`
	}
	if (value.length < 16) {
		return ''
	}
	const yearText = value.substring(0, 4)
	const monthText = value.substring(5, 7)
	const dayText = value.substring(8, 10)
	const hourText = value.substring(11, 13)
	const minuteText = value.substring(14, 16)
	const secondText = value.length >= 19 ? value.substring(17, 19) : '00'
	return `${yearText}-${monthText}-${dayText} ${hourText}:${minuteText}:${secondText}`
}

function getSafeMinuteStep() : number {
	if (props.minuteStep <= 0) {
		return 1
	}
	if (props.minuteStep > 30) {
		return 30
	}
	return props.minuteStep
}

function getCurrentYear() : number {
	const now = new Date()
	return now.getFullYear()
}

function getNormalizedEndYear() : number {
	const fallback = getCurrentYear() + 10
	return props.endYear > 0 ? props.endYear : fallback
}

function getNormalizedStartYear() : number {
	const fallback = getCurrentYear() - 10
	const startYear = props.startYear > 0 ? props.startYear : fallback
	const endYear = getNormalizedEndYear()
	if (startYear > endYear) {
		return endYear
	}
	return startYear
}

function isLeapYear(year: number) : boolean {
	if (year % 400 == 0) return true
	if (year % 100 == 0) return false
	return year % 4 == 0
}

function getDaysInMonth(year: number, month: number) : number {
	if (month == 2) {
		return isLeapYear(year) ? 29 : 28
	}
	if (month == 4 || month == 6 || month == 9 || month == 11) {
		return 30
	}
	return 31
}

function buildDefaultValue() : string {
	const now = new Date()
	const year = now.getFullYear()
	const month = now.getMonth() + 1
	const day = now.getDate()
	if (!props.showTime) {
		return `${year}-${normalizeNumber(month)}-${normalizeNumber(day)}`
	}
	const hour = now.getHours()
	const minuteStep = getSafeMinuteStep()
	const minute = Math.floor(now.getMinutes() / minuteStep) * minuteStep
	return `${year}-${normalizeNumber(month)}-${normalizeNumber(day)} ${normalizeNumber(hour)}:${normalizeNumber(minute)}:00`
}

function buildYearOptions() : string[] {
	const result: string[] = []
	const startYear = getNormalizedStartYear()
	const endYear = getNormalizedEndYear()
	for (let year = startYear; year <= endYear; year++) {
		result.push('' + year)
	}
	return result
}

function buildMonthOptions() : string[] {
	const result: string[] = []
	for (let month = 1; month <= 12; month++) {
		result.push(normalizeNumber(month))
	}
	return result
}

function buildHourOptions() : string[] {
	const result: string[] = []
	for (let hour = 0; hour < 24; hour++) {
		result.push(normalizeNumber(hour))
	}
	return result
}

function buildMinuteOptions() : string[] {
	const result: string[] = []
	const step = getSafeMinuteStep()
	for (let minute = 0; minute < 60; minute += step) {
		result.push(normalizeNumber(minute))
	}
	return result
}

function buildDayOptions(year: number, month: number) : string[] {
	const result: string[] = []
	const maxDay = getDaysInMonth(year, month)
	for (let day = 1; day <= maxDay; day++) {
		result.push(normalizeNumber(day))
	}
	return result
}

const yearOptions = computed<string[]>(() : string[] => {
	return buildYearOptions()
})

const showTime = computed<boolean>(() : boolean => {
	return props.showTime
})

const monthOptions = computed<string[]>(() : string[] => {
	return buildMonthOptions()
})

const hourOptions = computed<string[]>(() : string[] => {
	return buildHourOptions()
})

const minuteOptions = computed<string[]>(() : string[] => {
	return buildMinuteOptions()
})

const dayOptions = computed<string[]>(() : string[] => {
	const source = draftValue.value != '' ? draftValue.value : buildDefaultValue()
	const year = parseNumber(source.substring(0, 4), getCurrentYear())
	const month = parseNumber(source.substring(5, 7), 1)
	return buildDayOptions(year, month)
})

const displayText = computed<string>(() : string => {
	return internalValue.value
})

function clampIndex(index: number, length: number) : number {
	if (length <= 0) return 0
	if (index < 0) return 0
	if (index >= length) return length - 1
	return index
}

function buildDateTimeText(yearText: string, monthText: string, dayText: string, hourText: string, minuteText: string) : string {
	if (!props.showTime) {
		return `${yearText}-${monthText}-${dayText}`
	}
	return `${yearText}-${monthText}-${dayText} ${hourText}:${minuteText}:00`
}

function syncPickerIndexesFromValue(value: string) {
	const effectiveValue = value != '' ? value : buildDefaultValue()
	const yearText = effectiveValue.substring(0, 4)
	const monthText = effectiveValue.substring(5, 7)
	const dayText = effectiveValue.substring(8, 10)
	const hourText = props.showTime && effectiveValue.length >= 13 ? effectiveValue.substring(11, 13) : '00'
	const minuteText = props.showTime && effectiveValue.length >= 16 ? effectiveValue.substring(14, 16) : '00'

	let yearIndex = yearOptions.value.indexOf(yearText)
	if (yearIndex < 0) yearIndex = 0
	let monthIndex = monthOptions.value.indexOf(monthText)
	if (monthIndex < 0) monthIndex = 0
	const dayList = buildDayOptions(parseNumber(yearText, getCurrentYear()), parseNumber(monthText, 1))
	let dayIndex = dayList.indexOf(dayText)
	if (dayIndex < 0) dayIndex = 0
	let hourIndex = hourOptions.value.indexOf(hourText)
	if (hourIndex < 0) hourIndex = 0

	const step = getSafeMinuteStep()
	const minuteValue = parseNumber(minuteText, 0)
	const alignedMinute = Math.floor(minuteValue / step) * step
	let minuteIndex = minuteOptions.value.indexOf(normalizeNumber(alignedMinute))
	if (minuteIndex < 0) minuteIndex = 0

	if (props.showTime) {
		pickerIndexes.value = [yearIndex, monthIndex, dayIndex, hourIndex, minuteIndex]
	} else {
		pickerIndexes.value = [yearIndex, monthIndex, dayIndex]
	}
	draftValue.value = buildDateTimeText(
		yearOptions.value[yearIndex],
		monthOptions.value[monthIndex],
		dayList[dayIndex],
		hourOptions.value[hourIndex],
		minuteOptions.value[minuteIndex],
	)
}

function clearAnimationTimers() {
	if (enterTimer != null) {
		clearTimeout(enterTimer!)
		enterTimer = null
	}
	if (closeTimer != null) {
		clearTimeout(closeTimer!)
		closeTimer = null
	}
}

function activatePanelAnimation() {
	clearAnimationTimers()
	enterTimer = setTimeout(() => {
		overlayVisible.value = true
		panelVisible.value = true
		enterTimer = null
	}, 16)
}

function openPanel() {
	if (props.disabled) return
	if (renderVisible.value) return
	syncPickerIndexesFromValue(internalValue.value)
	renderVisible.value = true
	overlayVisible.value = false
	panelVisible.value = false
	activatePanelAnimation()
	emit('open')
}

function closePanel() {
	if (!renderVisible.value) return
	clearAnimationTimers()
	overlayVisible.value = false
	panelVisible.value = false
	closeTimer = setTimeout(() => {
		renderVisible.value = false
		closeTimer = null
		emit('close')
	}, PANEL_ANIMATION_DURATION)
}

function cancelSelection() {
	emit('cancel')
	closePanel()
}

function buildPayload(value: string) : UTSJSONObject {
	if (!props.showTime) {
		return {
			value: value,
			date: value,
			time: '',
		} as UTSJSONObject
	}
	return {
		value: value,
		date: value.substring(0, 10),
		time: value.substring(11, 19),
	} as UTSJSONObject
}

function confirmSelection() {
	internalValue.value = draftValue.value
	const payload = buildPayload(internalValue.value)
	emit('change', payload)
	emit('confirm', payload)
	closePanel()
}

function handleOverlayClick() {
	if (props.closeOnOverlay) {
		cancelSelection()
	}
}

function handlePickerChange(event: any) {
	const pickerEvent = event as UniPickerViewChangeEvent
	const detail = pickerEvent.detail
	if (detail == null) return
	const indexes = detail.value as number[]
	const yearIndex = clampIndex(indexes[0], yearOptions.value.length)
	const monthIndex = clampIndex(indexes[1], monthOptions.value.length)
	const yearText = yearOptions.value[yearIndex]
	const monthText = monthOptions.value[monthIndex]
	const days = buildDayOptions(parseNumber(yearText, getCurrentYear()), parseNumber(monthText, 1))
	const dayIndex = clampIndex(indexes[2], days.length)
	const hourIndex = props.showTime ? clampIndex(indexes[3], hourOptions.value.length) : 0
	const minuteIndex = props.showTime ? clampIndex(indexes[4], minuteOptions.value.length) : 0
	if (props.showTime) {
		pickerIndexes.value = [yearIndex, monthIndex, dayIndex, hourIndex, minuteIndex]
	} else {
		pickerIndexes.value = [yearIndex, monthIndex, dayIndex]
	}
	draftValue.value = buildDateTimeText(
		yearText,
		monthText,
		days[dayIndex],
		hourOptions.value[hourIndex],
		minuteOptions.value[minuteIndex],
	)
}

function setValue(value: string) {
	internalValue.value = normalizeDateTime(value)
	syncPickerIndexesFromValue(internalValue.value)
}

function getValue() : UTSJSONObject {
	const value = internalValue.value
	if (value == '') {
		return {
			value: '',
			date: '',
			time: '',
		} as UTSJSONObject
	}
	return buildPayload(value)
}

function clearValue() {
	internalValue.value = ''
	draftValue.value = buildDefaultValue()
	syncPickerIndexesFromValue(draftValue.value)
}

watch(
	() : string => props.value,
	(newValue: string) => {
		const normalized = normalizeDateTime(newValue)
		const nextValue = normalized == '' && props.defaultToToday ? buildDefaultValue() : normalized
		if (nextValue != internalValue.value) {
			internalValue.value = nextValue
			if (!renderVisible.value) {
				syncPickerIndexesFromValue(internalValue.value)
			}
		}
	}
)

onMounted(() => {
	internalValue.value = normalizeDateTime(props.value)
	if (internalValue.value == '' && props.defaultToToday) {
		internalValue.value = buildDefaultValue()
	}
	syncPickerIndexesFromValue(internalValue.value)
})

onUnmounted(() => {
	clearAnimationTimers()
})

__expose({
	openPanel,
	closePanel,
	setValue,
	getValue,
	clearValue,
})

return (): any | null => {

const _component_picker_view_column = resolveComponent("picker-view-column")
const _component_picker_view = resolveComponent("picker-view")

  return _cE(Fragment, null, [
    _cE("view", _uM({
      class: "dt-trigger-wrapper",
      onClick: openPanel
    }), [
      renderSlot(_ctx.$slots, "trigger", {}, (): any[] => [
        _cE("view", _uM({ class: "dt-trigger-default" }), [
          _cE("text", _uM({
            class: _nC(unref(displayText) != '' ? 'dt-trigger-text' : 'dt-trigger-placeholder')
          }), _tD(unref(displayText) != '' ? unref(displayText) : _ctx.placeholder), 3 /* TEXT, CLASS */),
          _cE("view", _uM({ class: "dt-trigger-arrow" }), [
            _cE("text", _uM({ class: "dt-arrow-icon" }), _tD(unref(renderVisible) ? '⌄' : '›'), 1 /* TEXT */)
          ])
        ])
      ])
    ]),
    isTrue(unref(renderVisible))
      ? _cE("view", _uM({
          key: 0,
          class: _nC(unref(overlayVisible) ? 'dt-overlay dt-overlay-active' : 'dt-overlay'),
          onClick: handleOverlayClick
        }), null, 2 /* CLASS */)
      : _cC("v-if", true),
    isTrue(unref(renderVisible))
      ? _cE("view", _uM({
          key: 1,
          class: _nC(unref(panelVisible) ? 'dt-panel dt-panel-active' : 'dt-panel')
        }), [
          _cE("view", _uM({ class: "dt-handle-wrap" }), [
            _cE("view", _uM({ class: "dt-handle" }))
          ]),
          _cE("view", _uM({ class: "dt-header" }), [
            _cE("view", _uM({
              class: "dt-header-action",
              onClick: cancelSelection
            }), [
              _cE("text", _uM({ class: "dt-header-action-text dt-header-action-text-light" }), "取消")
            ]),
            _cE("text", _uM({ class: "dt-header-title" }), _tD(_ctx.title), 1 /* TEXT */),
            _cE("view", _uM({
              class: "dt-header-action",
              onClick: confirmSelection
            }), [
              _cE("text", _uM({ class: "dt-header-action-text" }), "确定")
            ])
          ]),
          _cE("view", _uM({ class: "dt-preview-wrap" }), [
            _cE("text", _uM({ class: "dt-preview-text" }), _tD(unref(draftValue)), 1 /* TEXT */)
          ]),
          _cV(_component_picker_view, _uM({
            class: "dt-picker",
            value: unref(pickerIndexes),
            "indicator-style": "height:48px;",
            "mask-class": "dt-picker-mask",
            onChange: handlePickerChange
          }), _uM({
            default: withSlotCtx((): any[] => [
              _cV(_component_picker_view_column, null, _uM({
                default: withSlotCtx((): any[] => [
                  _cE(Fragment, null, RenderHelpers.renderList(unref(yearOptions), (item, index, __index, _cached): any => {
                    return _cE("view", _uM({
                      key: 'y_' + index,
                      class: "dt-picker-item"
                    }), [
                      _cE("text", _uM({ class: "dt-picker-item-text" }), _tD(item), 1 /* TEXT */)
                    ])
                  }), 128 /* KEYED_FRAGMENT */)
                ]),
                _: 1 /* STABLE */
              })),
              _cV(_component_picker_view_column, null, _uM({
                default: withSlotCtx((): any[] => [
                  _cE(Fragment, null, RenderHelpers.renderList(unref(monthOptions), (item, index, __index, _cached): any => {
                    return _cE("view", _uM({
                      key: 'm_' + index,
                      class: "dt-picker-item"
                    }), [
                      _cE("text", _uM({ class: "dt-picker-item-text" }), _tD(item), 1 /* TEXT */)
                    ])
                  }), 128 /* KEYED_FRAGMENT */)
                ]),
                _: 1 /* STABLE */
              })),
              _cV(_component_picker_view_column, null, _uM({
                default: withSlotCtx((): any[] => [
                  _cE(Fragment, null, RenderHelpers.renderList(unref(dayOptions), (item, index, __index, _cached): any => {
                    return _cE("view", _uM({
                      key: 'd_' + index,
                      class: "dt-picker-item"
                    }), [
                      _cE("text", _uM({ class: "dt-picker-item-text" }), _tD(item), 1 /* TEXT */)
                    ])
                  }), 128 /* KEYED_FRAGMENT */)
                ]),
                _: 1 /* STABLE */
              })),
              isTrue(unref(showTime))
                ? _cV(_component_picker_view_column, _uM({ key: 0 }), _uM({
                    default: withSlotCtx((): any[] => [
                      _cE(Fragment, null, RenderHelpers.renderList(unref(hourOptions), (item, index, __index, _cached): any => {
                        return _cE("view", _uM({
                          key: 'h_' + index,
                          class: "dt-picker-item"
                        }), [
                          _cE("text", _uM({ class: "dt-picker-item-text" }), _tD(item), 1 /* TEXT */)
                        ])
                      }), 128 /* KEYED_FRAGMENT */)
                    ]),
                    _: 1 /* STABLE */
                  }))
                : _cC("v-if", true),
              isTrue(unref(showTime))
                ? _cV(_component_picker_view_column, _uM({ key: 1 }), _uM({
                    default: withSlotCtx((): any[] => [
                      _cE(Fragment, null, RenderHelpers.renderList(unref(minuteOptions), (item, index, __index, _cached): any => {
                        return _cE("view", _uM({
                          key: 'i_' + index,
                          class: "dt-picker-item"
                        }), [
                          _cE("text", _uM({ class: "dt-picker-item-text" }), _tD(item), 1 /* TEXT */)
                        ])
                      }), 128 /* KEYED_FRAGMENT */)
                    ]),
                    _: 1 /* STABLE */
                  }))
                : _cC("v-if", true)
            ]),
            _: 1 /* STABLE */
          }), 8 /* PROPS */, ["value"])
        ], 2 /* CLASS */)
      : _cC("v-if", true)
  ], 64 /* STABLE_FRAGMENT */)
}
}

})
export default __sfc__
export type LiliDataComponentPublicInstance = InstanceType<typeof __sfc__>;
const GenUniModulesLiliDataComponentsLiliDataLiliDataStyles = [_uM([["dt-trigger-wrapper", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"]]))], ["dt-trigger-default", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["minHeight", 44], ["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "space-between"], ["paddingLeft", 12], ["paddingRight", 12], ["borderTopLeftRadius", 10], ["borderTopRightRadius", 10], ["borderBottomRightRadius", 10], ["borderBottomLeftRadius", 10], ["backgroundColor", "#F8FAFC"]]))], ["dt-trigger-text", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["fontSize", 14], ["color", "#111827"], ["lineHeight", "20px"]]))], ["dt-trigger-placeholder", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["fontSize", 14], ["color", "#9CA3AF"], ["lineHeight", "20px"]]))], ["dt-trigger-arrow", _pS(_uM([["width", 20], ["height", 20], ["alignItems", "center"], ["justifyContent", "center"], ["marginLeft", 8]]))], ["dt-arrow-icon", _pS(_uM([["fontSize", 20], ["color", "#CBD5E1"], ["lineHeight", "20px"]]))], ["dt-overlay", _pS(_uM([["position", "fixed"], ["top", 0], ["left", 0], ["right", 0], ["bottom", 0], ["backgroundColor", "rgba(0,0,0,0)"], ["zIndex", 998], ["opacity", 0], ["transitionProperty", "opacity,backgroundColor"], ["transitionDuration", "320ms"], ["transitionTimingFunction", "ease"]]))], ["dt-overlay-active", _pS(_uM([["backgroundColor", "rgba(10,18,30,0.32)"], ["opacity", 1]]))], ["dt-panel", _pS(_uM([["position", "fixed"], ["left", 0], ["right", 0], ["bottom", 0], ["height", 360], ["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", 22], ["borderTopRightRadius", 22], ["borderBottomRightRadius", 0], ["borderBottomLeftRadius", 0], ["zIndex", 999], ["flexDirection", "column"], ["opacity", 0], ["transform", "translateY(48px)"], ["boxShadow", "0 -18px 44px rgba(15, 23, 42, 0.14)"], ["transitionProperty", "transform,opacity"], ["transitionDuration", "340ms"], ["transitionTimingFunction", "ease"]]))], ["dt-panel-active", _pS(_uM([["opacity", 1], ["transform", "translateY(0px)"]]))], ["dt-handle-wrap", _pS(_uM([["alignItems", "center"], ["paddingTop", 10], ["paddingBottom", 4]]))], ["dt-handle", _pS(_uM([["width", 44], ["height", 5], ["borderTopLeftRadius", 999], ["borderTopRightRadius", 999], ["borderBottomRightRadius", 999], ["borderBottomLeftRadius", 999], ["backgroundColor", "#D9DEE7"]]))], ["dt-header", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "space-between"], ["paddingTop", 10], ["paddingBottom", 16], ["paddingLeft", 16], ["paddingRight", 16], ["borderBottomWidth", 1], ["borderBottomStyle", "solid"], ["borderBottomColor", "#F1F5F9"]]))], ["dt-header-title", _pS(_uM([["fontSize", 16], ["fontWeight", "600"], ["color", "#111827"], ["lineHeight", "20px"]]))], ["dt-header-action", _pS(_uM([["minWidth", 44], ["alignItems", "center"], ["justifyContent", "center"]]))], ["dt-header-action-text", _pS(_uM([["fontSize", 14], ["fontWeight", "600"], ["color", "#2563EB"], ["lineHeight", "20px"]]))], ["dt-header-action-text-light", _pS(_uM([["color", "#94A3B8"]]))], ["dt-preview-wrap", _pS(_uM([["alignItems", "center"], ["justifyContent", "center"], ["paddingTop", 12], ["paddingBottom", 12], ["backgroundColor", "#F8FAFC"], ["marginLeft", 16], ["marginRight", 16], ["marginTop", 12], ["borderTopLeftRadius", 14], ["borderTopRightRadius", 14], ["borderBottomRightRadius", 14], ["borderBottomLeftRadius", 14]]))], ["dt-preview-text", _pS(_uM([["fontSize", 15], ["fontWeight", "600"], ["color", "#0F172A"], ["lineHeight", "20px"]]))], ["dt-picker", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["height", 220], ["marginTop", 12]]))], ["dt-picker-item", _pS(_uM([["height", 48], ["alignItems", "center"], ["justifyContent", "center"]]))], ["dt-picker-item-text", _pS(_uM([["fontSize", 16], ["color", "#334155"], ["lineHeight", "20px"]]))], ["@TRANSITION", _uM([["dt-overlay", _uM([["property", "opacity,backgroundColor"], ["duration", "320ms"], ["timingFunction", "ease"]])], ["dt-panel", _uM([["property", "transform,opacity"], ["duration", "340ms"], ["timingFunction", "ease"]])]])]])]
