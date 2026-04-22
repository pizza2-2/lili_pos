type SelectChangePayload = { __$originalPosition?: UTSSourceMapPosition<"SelectChangePayload", "uni_modules/lili_bottom-select/components/lili_bottom-select/lili_bottom-select.uvue", 152, 6>;
	value: string
	text: string
	item: UTSJSONObject
}

type MultiSelectChangePayload = { __$originalPosition?: UTSSourceMapPosition<"MultiSelectChangePayload", "uni_modules/lili_bottom-select/components/lili_bottom-select/lili_bottom-select.uvue", 158, 6>;
	values: string[]
	texts: string[]
	items: UTSJSONObject[]
}

type Props = { __$originalPosition?: UTSSourceMapPosition<"Props", "uni_modules/lili_bottom-select/components/lili_bottom-select/lili_bottom-select.uvue", 164, 6>;
	fetchData: (params: UTSJSONObject) => Promise<UTSJSONObject>
	value?: string
	valueText?: string
	values?: string[]
	multiple?: boolean
	placeholder?: string
	title?: string
	searchPlaceholder?: string
	emptyText?: string
	disabled?: boolean
	labelKey?: string
	valueKey?: string
	pageSize?: number
	searchDelay?: number
	closeOnOverlay?: boolean
	showEditAction?: boolean
	showAddAction?: boolean
	editActionText?: string
	addActionText?: string
}


const __sfc__ = defineComponent({
  __name: 'lili_bottom-select',
  props: {
    fetchData: { type: Function as PropType<(params: UTSJSONObject) => Promise<UTSJSONObject>>, required: true },
    value: { type: String, required: false, default: '' },
    valueText: { type: String, required: false, default: '' },
    values: { type: Array as PropType<string[]>, required: false, default: () : string[] => [] },
    multiple: { type: Boolean, required: false, default: false },
    placeholder: { type: String, required: false, default: '请选择' },
    title: { type: String, required: false, default: '请选择' },
    searchPlaceholder: { type: String, required: false, default: '请输入关键词搜索' },
    emptyText: { type: String, required: false, default: '暂无数据' },
    disabled: { type: Boolean, required: false, default: false },
    labelKey: { type: String, required: false, default: 'text' },
    valueKey: { type: String, required: false, default: 'value' },
    pageSize: { type: Number, required: false, default: 20 },
    searchDelay: { type: Number, required: false, default: 300 },
    closeOnOverlay: { type: Boolean, required: false, default: true },
    showEditAction: { type: Boolean, required: false, default: true },
    showAddAction: { type: Boolean, required: false, default: true },
    editActionText: { type: String, required: false, default: '编辑' },
    addActionText: { type: String, required: false, default: '新增' }
  },
  emits: ["change", "multiChange", "open", "close", "edit", "add"],
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
const internalValue = ref<string>(props.value ?? '')
const internalText = ref<string>(props.valueText ?? '')
const textInitialized = ref<boolean>((props.valueText ?? '') != '')
const selectedItems = ref<UTSJSONObject[]>([])
const keyword = ref<string>('')
const displayList = ref<UTSJSONObject[]>([])
const currentPage = ref<number>(1)
const total = ref<number>(0)
const loading = ref<boolean>(false)
const hasMore = ref<boolean>(true)
const listLoaded = ref<boolean>(false)
const panelStyle = ref<string>('')

let searchTimer: number | null = null
let enterTimer: number | null = null
let closeTimer: number | null = null
const PANEL_ANIMATION_DURATION = 340

function fieldToStr(item: UTSJSONObject, key: string) : string {
	const v = item[key]
	if (v == null) return ''
	return '' + v
}

function fieldLabel(item: UTSJSONObject, key: string) : string {
	const v = item[key]
	if (v == null) return ''
	return v as string
}

function getNumberField(obj: UTSJSONObject, key: string) : number {
	const value = obj[key]
	if (value == null) return 0
	return value as number
}

function getListField(obj: UTSJSONObject, key: string) : UTSJSONObject[] {
	const value = obj[key]
	if (value == null) return []
	return value as UTSJSONObject[]
}

function buildFetchParams(page: number, keywordValue: string, id: string) : UTSJSONObject {
	return {
		page: page,
		pageSize: props.pageSize,
		keyword: keywordValue,
		id: id,
	} as UTSJSONObject
}

function clearAnimationTimers() {
	if (enterTimer != null) {
		const timer = enterTimer
		clearTimeout(timer!)
		enterTimer = null
	}
	if (closeTimer != null) {
		const timer = closeTimer
		clearTimeout(timer!)
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

function updateTextFromList() {
	for (let i = 0; i < displayList.value.length; i++) {
		const item = displayList.value[i]
		if (fieldToStr(item, props.valueKey) == internalValue.value) {
			internalText.value = fieldLabel(item, props.labelKey)
			textInitialized.value = true
			break
		}
	}
}

function syncFromList(vals: string[]) {
	if (vals.length == 0) {
		selectedItems.value = []
		return
	}
	const matched: UTSJSONObject[] = []
	for (let i = 0; i < displayList.value.length; i++) {
		const item = displayList.value[i]
		if (vals.includes(fieldToStr(item, props.valueKey))) {
			matched.push(item)
		}
	}
	selectedItems.value = matched
}

async function loadData(isReset: boolean) {
	if (loading.value) return
	if (!isReset && !hasMore.value) return

	loading.value = true
	const page = isReset ? 1 : currentPage.value

	try {
		const res = await props.fetchData(buildFetchParams(page, keyword.value, ''))
		const data = getListField(res, 'data')
		total.value = getNumberField(res, 'total')

		if (isReset) {
			displayList.value = data
			currentPage.value = 2
			listLoaded.value = true
		} else {
			const merged: UTSJSONObject[] = [...displayList.value, ...data]
			displayList.value = merged
			currentPage.value = currentPage.value + 1
		}

		hasMore.value = displayList.value.length < total.value && data.length >= props.pageSize

		if (!props.multiple && internalValue.value != '' && !textInitialized.value) {
			updateTextFromList()
		}
		if (props.multiple) {
			const curVals = selectedItems.value.map<string>((item: UTSJSONObject) : string => fieldToStr(item, props.valueKey))
			syncFromList(curVals)
		}
	} catch (e) {
		console.error('lili_bottom-select loadData 失败:', e, " at uni_modules/lili_bottom-select/components/lili_bottom-select/lili_bottom-select.uvue:350")
		uni.showToast({ title: '数据加载失败', icon: 'none' })
	} finally {
		loading.value = false
	}
}

async function fetchTextByValue(value: string) {
	if (value == '' || textInitialized.value) return
	try {
		const res = await props.fetchData(buildFetchParams(1, '', value))
		const data = getListField(res, 'data')
		for (let i = 0; i < data.length; i++) {
			const item = data[i]
			if (fieldToStr(item, props.valueKey) == value) {
				internalText.value = fieldLabel(item, props.labelKey)
				textInitialized.value = true
				break
			}
		}
	} catch (e) {
		console.error('lili_bottom-select fetchTextByValue 失败:', e, " at uni_modules/lili_bottom-select/components/lili_bottom-select/lili_bottom-select.uvue:371")
	}
}

function syncMultiValuesFromProps(vals: string[]) {
	if (vals.length == 0) {
		selectedItems.value = []
		return
	}
	if (displayList.value.length > 0) {
		syncFromList(vals)
	}
}

const displayText = computed<string>(() : string => {
	if (props.multiple) {
		if (selectedItems.value.length == 0) return ''
		const texts = selectedItems.value.map<string>((item: UTSJSONObject) : string => fieldLabel(item, props.labelKey))
		return texts.join('、')
	}
	return internalText.value
})

watch(
	() : string => props.value ?? '',
	(newVal: string) => {
		if (newVal != internalValue.value) {
			internalValue.value = newVal
			if (newVal != '') {
				if (props.valueText != '') {
					internalText.value = props.valueText
					textInitialized.value = true
				} else if (!textInitialized.value) {
					fetchTextByValue(newVal)
				}
			} else {
				internalText.value = ''
				textInitialized.value = false
			}
		}
	}
)

watch(
	() : string => props.valueText ?? '',
	(newText: string) => {
		if (newText != '') {
			internalText.value = newText
			textInitialized.value = true
			return
		}
		if (internalValue.value == '') {
			internalText.value = ''
			textInitialized.value = false
		}
	}
)

watch(
	() : string[] => props.values ?? [],
	(newVals: string[]) => {
		if (props.multiple) {
			syncMultiValuesFromProps(newVals)
		}
	}
)

onMounted(() => {
	const info = uni.getWindowInfo()
	const winH = info.windowHeight
	let panelH = Math.floor(winH * 0.72)
	if (panelH < 420) {
		panelH = 420
	}
	const maxPanelH = winH - 24
	if (panelH > maxPanelH) {
		panelH = maxPanelH
	}
	panelStyle.value = `height:${panelH}px;`

	if (!props.multiple && internalValue.value != '') {
		if (props.valueText != '') {
			internalText.value = props.valueText
			textInitialized.value = true
		} else {
			fetchTextByValue(internalValue.value)
		}
	}
	if (props.multiple && props.values.length > 0) {
		syncMultiValuesFromProps(props.values)
	}
})

async function open() {
	if (props.disabled) return
	if (renderVisible.value) return
	renderVisible.value = true
	overlayVisible.value = false
	panelVisible.value = false
	activatePanelAnimation()
	emit('open')
	keyword.value = ''
	if (!listLoaded.value) {
		await loadData(true)
	} else {
		currentPage.value = 1
		hasMore.value = displayList.value.length < total.value
	}
	if (props.multiple) {
		const curVals = selectedItems.value.map<string>((item: UTSJSONObject) : string => fieldToStr(item, props.valueKey))
		syncFromList(curVals.length > 0 ? curVals : (props.values ?? []))
	}
}

function close() {
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

function handleOverlayClick() {
	if (props.closeOnOverlay) {
		close()
	}
}

function handleEditAction() {
	if (props.disabled) return
	emit('edit')
}

function handleAddAction() {
	if (props.disabled) return
	emit('add')
}

async function triggerSearch() {
	if (searchTimer != null) {
		clearTimeout(searchTimer!)
		searchTimer = null
	}
	displayList.value = []
	hasMore.value = true
	listLoaded.value = false
	await loadData(true)
}

function onSearchInput() {
	if (searchTimer != null) {
		clearTimeout(searchTimer!)
	}
	searchTimer = setTimeout(() => {
		triggerSearch()
	}, props.searchDelay)
}

async function clearSearch() {
	keyword.value = ''
	await triggerSearch()
}

function loadMore() {
	if (hasMore.value && !loading.value) {
		loadData(false)
	}
}

function isItemSelected(item: UTSJSONObject) : boolean {
	const itemVal = fieldToStr(item, props.valueKey)
	if (props.multiple) {
		for (let i = 0; i < selectedItems.value.length; i++) {
			if (fieldToStr(selectedItems.value[i], props.valueKey) == itemVal) {
				return true
			}
		}
		return false
	}
	return internalValue.value == itemVal
}

function confirmSingleItem(item: UTSJSONObject) {
	const value = fieldToStr(item, props.valueKey)
	const text = fieldLabel(item, props.labelKey)
	internalValue.value = value
	internalText.value = text
	textInitialized.value = true
	emit('change', { value: value, text: text, item: item } as UTSJSONObject)
	close()
}

function toggleMultiItem(item: UTSJSONObject) {
	const val = fieldToStr(item, props.valueKey)
	const idx = selectedItems.value.findIndex((s: UTSJSONObject) : boolean => {
		return fieldToStr(s, props.valueKey) == val
	})
	if (idx >= 0) {
		const newList: UTSJSONObject[] = []
		for (let i = 0; i < selectedItems.value.length; i++) {
			if (i != idx) {
				newList.push(selectedItems.value[i])
			}
		}
		selectedItems.value = newList
	} else {
		selectedItems.value = [...selectedItems.value, item]
	}
}

function onItemClick(item: UTSJSONObject) {
	if (props.disabled) return
	if (props.multiple) {
		toggleMultiItem(item)
	} else {
		confirmSingleItem(item)
	}
}

function removeSelectedItem(item: UTSJSONObject) {
	const val = fieldToStr(item, props.valueKey)
	const newList: UTSJSONObject[] = []
	for (let i = 0; i < selectedItems.value.length; i++) {
		if (fieldToStr(selectedItems.value[i], props.valueKey) != val) {
			newList.push(selectedItems.value[i])
		}
	}
	selectedItems.value = newList
}

function clearAllSelected() {
	selectedItems.value = []
}

function confirmMultiple() {
	const values = selectedItems.value.map<string>((item: UTSJSONObject) : string => fieldToStr(item, props.valueKey))
	const texts = selectedItems.value.map<string>((item: UTSJSONObject) : string => fieldLabel(item, props.labelKey))
	emit('multiChange', {
		values: values,
		texts: texts,
		items: selectedItems.value,
	} as UTSJSONObject)
	close()
}

function openPanel() { open() }
function closePanel() { close() }

function setValue(value: string, text: string) {
	internalValue.value = value
	if (text != '') {
		internalText.value = text
		textInitialized.value = true
	} else if (value != '') {
		fetchTextByValue(value)
	} else {
		internalText.value = ''
		textInitialized.value = false
	}
}

function setValues(items: UTSJSONObject[]) {
	selectedItems.value = items
}

function getValue() : SelectChangePayload {
	return {
		value: internalValue.value,
		text: internalText.value,
		item: {} as UTSJSONObject,
	} as SelectChangePayload
}

function getValues() : MultiSelectChangePayload {
	const values = selectedItems.value.map<string>((item: UTSJSONObject) : string => fieldToStr(item, props.valueKey))
	const texts = selectedItems.value.map<string>((item: UTSJSONObject) : string => fieldLabel(item, props.labelKey))
	return {
		values: values,
		texts: texts,
		items: selectedItems.value,
	} as MultiSelectChangePayload
}

function clearValue() {
	internalValue.value = ''
	internalText.value = ''
	textInitialized.value = false
}

function clearValues() {
	selectedItems.value = []
}

async function refreshData() {
	displayList.value = []
	listLoaded.value = false
	keyword.value = ''
	hasMore.value = true
	await loadData(true)
}

async function preloadList(kw: string) {
	if (kw != '') {
		keyword.value = kw
	}
	displayList.value = []
	listLoaded.value = false
	hasMore.value = true
	await loadData(true)
}

function reset() {
	clearValue()
	clearValues()
	displayList.value = []
	listLoaded.value = false
	keyword.value = ''
	hasMore.value = true
	currentPage.value = 1
}

onUnmounted(() => {
	clearAnimationTimers()
	if (searchTimer != null) {
		const timer = searchTimer
		clearTimeout(timer!)
		searchTimer = null
	}
})

__expose({
	openPanel,
	closePanel,
	setValue,
	setValues,
	getValue,
	getValues,
	clearValue,
	clearValues,
	refreshData,
	preloadList,
	reset,
})

return (): any | null => {

  return _cE(Fragment, null, [
    _cE("view", _uM({
      class: "bs-trigger-wrapper",
      onClick: open
    }), [
      renderSlot(_ctx.$slots, "trigger", {}, (): any[] => [
        _cE("view", _uM({ class: "bs-trigger-default" }), [
          _cE("text", _uM({
            class: _nC(unref(displayText) != '' ? 'bs-trigger-text' : 'bs-trigger-placeholder')
          }), _tD(unref(displayText) != '' ? unref(displayText) : _ctx.placeholder), 3 /* TEXT, CLASS */),
          _cE("view", _uM({ class: "bs-trigger-actions" }), [
            isTrue(_ctx.showEditAction)
              ? _cE("view", _uM({
                  key: 0,
                  class: "bs-trigger-action",
                  onClick: withModifiers(handleEditAction, ["stop"])
                }), [
                  _cE("text", _uM({ class: "bs-trigger-action-icon" }), "✎")
                ])
              : _cC("v-if", true),
            isTrue(_ctx.showAddAction)
              ? _cE("view", _uM({
                  key: 1,
                  class: "bs-trigger-action",
                  onClick: withModifiers(handleAddAction, ["stop"])
                }), [
                  _cE("text", _uM({ class: "bs-trigger-action-icon" }), "＋")
                ])
              : _cC("v-if", true),
            _cE("view", _uM({ class: "bs-trigger-arrow" }), [
              _cE("text", _uM({ class: "bs-arrow-icon" }), _tD(unref(renderVisible) ? '⌄' : '›'), 1 /* TEXT */)
            ])
          ])
        ])
      ])
    ]),
    isTrue(unref(renderVisible))
      ? _cE("view", _uM({
          key: 0,
          class: _nC(unref(overlayVisible) ? 'bs-overlay bs-overlay-active' : 'bs-overlay'),
          onClick: handleOverlayClick
        }), null, 2 /* CLASS */)
      : _cC("v-if", true),
    isTrue(unref(renderVisible))
      ? _cE("view", _uM({
          key: 1,
          class: _nC(unref(panelVisible) ? 'bs-panel bs-panel-active' : 'bs-panel'),
          style: _nS(unref(panelStyle))
        }), [
          _cE("view", _uM({ class: "bs-handle-wrap" }), [
            _cE("view", _uM({ class: "bs-handle" }))
          ]),
          _cE("view", _uM({ class: "bs-header" }), [
            _cE("text", _uM({ class: "bs-header-title" }), _tD(_ctx.title), 1 /* TEXT */),
            _cE("view", _uM({
              class: "bs-header-close",
              onClick: close
            }), [
              _cE("text", _uM({ class: "bs-close-icon" }), "✕")
            ])
          ]),
          _cE("view", _uM({ class: "bs-search-bar" }), [
            _cE("view", _uM({ class: "bs-search-inner" }), [
              _cE("text", _uM({ class: "bs-search-icon" }), "🔍"),
              _cE("input", _uM({
                class: "bs-search-input",
                modelValue: unref(keyword),
                onInput: [($event: UniInputEvent) => {trySetRefValue(keyword, $event.detail.value)}, onSearchInput] as Array<any | null>,
                placeholder: _ctx.searchPlaceholder,
                disabled: _ctx.disabled,
                onConfirm: triggerSearch,
                "confirm-type": "search"
              }), null, 40 /* PROPS, NEED_HYDRATION */, ["modelValue", "placeholder", "disabled"]),
              unref(keyword) != ''
                ? _cE("view", _uM({
                    key: 0,
                    class: "bs-search-clear",
                    onClick: clearSearch
                  }), [
                    _cE("text", _uM({ class: "bs-clear-icon" }), "✕")
                  ])
                : _cC("v-if", true)
            ])
          ]),
          isTrue(_ctx.multiple && unref(selectedItems).length > 0)
            ? _cE("view", _uM({
                key: 0,
                class: "bs-tags-bar"
              }), [
                _cE("scroll-view", _uM({
                  "scroll-x": "true",
                  style: _nS(_uM({"flex":"1"}))
                }), [
                  _cE("view", _uM({ class: "bs-tags-inner" }), [
                    _cE(Fragment, null, RenderHelpers.renderList(unref(selectedItems), (item, __key, __index, _cached): any => {
                      return _cE("view", _uM({
                        key: item[_ctx.valueKey],
                        class: "bs-tag"
                      }), [
                        _cE("text", _uM({ class: "bs-tag-text" }), _tD(item[_ctx.labelKey]), 1 /* TEXT */),
                        _cE("view", _uM({
                          class: "bs-tag-remove",
                          onClick: withModifiers(() => {removeSelectedItem(item)}, ["stop"])
                        }), [
                          _cE("text", _uM({ class: "bs-tag-remove-icon" }), "✕")
                        ], 8 /* PROPS */, ["onClick"])
                      ])
                    }), 128 /* KEYED_FRAGMENT */)
                  ])
                ], 4 /* STYLE */)
              ])
            : _cC("v-if", true),
          _cE("scroll-view", _uM({
            "scroll-y": "true",
            style: _nS(_uM({"flex":"1"})),
            onScrolltolower: loadMore,
            "show-scrollbar": false
          }), [
            isTrue(unref(loading) && unref(displayList).length == 0)
              ? _cE("view", _uM({
                  key: 0,
                  class: "bs-state-wrapper"
                }), [
                  _cE("text", _uM({ class: "bs-state-text" }), "加载中...")
                ])
              : isTrue(!unref(loading) && unref(displayList).length == 0 && unref(listLoaded))
                ? _cE("view", _uM({
                    key: 1,
                    class: "bs-state-wrapper"
                  }), [
                    _cE("text", _uM({ class: "bs-state-text" }), _tD(_ctx.emptyText), 1 /* TEXT */)
                  ])
                : _cE("view", _uM({ key: 2 }), [
                    _cE(Fragment, null, RenderHelpers.renderList(unref(displayList), (item, __key, __index, _cached): any => {
                      return _cE("view", _uM({
                        key: item[_ctx.valueKey],
                        class: _nC(isItemSelected(item) ? 'bs-list-item bs-list-item-selected' : 'bs-list-item'),
                        onClick: () => {onItemClick(item)}
                      }), [
                        _cE("text", _uM({
                          class: _nC(isItemSelected(item) ? 'bs-item-label bs-item-label-selected' : 'bs-item-label')
                        }), _tD(item[_ctx.labelKey]), 3 /* TEXT, CLASS */),
                        isTrue(isItemSelected(item))
                          ? _cE("view", _uM({
                              key: 0,
                              class: "bs-check-icon-wrapper"
                            }), [
                              _cE("text", _uM({ class: "bs-check-icon" }), "✓")
                            ])
                          : _cC("v-if", true)
                      ], 10 /* CLASS, PROPS */, ["onClick"])
                    }), 128 /* KEYED_FRAGMENT */),
                    isTrue(unref(loading) && unref(displayList).length > 0)
                      ? _cE("view", _uM({
                          key: 0,
                          class: "bs-load-more"
                        }), [
                          _cE("text", _uM({ class: "bs-load-more-text" }), "加载中...")
                        ])
                      : isTrue(!unref(hasMore) && unref(displayList).length > 0)
                        ? _cE("view", _uM({
                            key: 1,
                            class: "bs-load-more"
                          }), [
                            _cE("text", _uM({ class: "bs-load-more-text" }), "没有更多了")
                          ])
                        : _cC("v-if", true)
                  ])
          ], 36 /* STYLE, NEED_HYDRATION */),
          isTrue(_ctx.multiple)
            ? _cE("view", _uM({
                key: 1,
                class: "bs-confirm-bar"
              }), [
                _cE("view", _uM({ class: "bs-confirm-info" }), [
                  unref(selectedItems).length > 0
                    ? _cE("text", _uM({
                        key: 0,
                        class: "bs-confirm-count"
                      }), " 已选 " + _tD(unref(selectedItems).length) + " 项 ", 1 /* TEXT */)
                    : _cC("v-if", true)
                ]),
                _cE("view", _uM({ class: "bs-confirm-btns" }), [
                  _cE("view", _uM({
                    class: "bs-btn-clear",
                    onClick: clearAllSelected
                  }), [
                    _cE("text", _uM({ class: "bs-btn-clear-text" }), "清空")
                  ]),
                  _cE("view", _uM({
                    class: "bs-btn-confirm",
                    onClick: confirmMultiple
                  }), [
                    _cE("text", _uM({ class: "bs-btn-confirm-text" }), "确定")
                  ])
                ])
              ])
            : _cC("v-if", true)
        ], 6 /* CLASS, STYLE */)
      : _cC("v-if", true)
  ], 64 /* STABLE_FRAGMENT */)
}
}

})
export default __sfc__
export type Lili_bottomSelectComponentPublicInstance = InstanceType<typeof __sfc__>;
const GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelectStyles = [_uM([["bs-trigger-wrapper", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"]]))], ["bs-trigger-default", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "space-between"], ["paddingTop", 20], ["paddingBottom", 20], ["borderBottomWidth", 1], ["borderBottomColor", "#E5E5E5"], ["borderBottomStyle", "solid"]]))], ["bs-trigger-text", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["fontSize", 14], ["color", "#333333"]]))], ["bs-trigger-placeholder", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["fontSize", 14], ["color", "#999999"]]))], ["bs-trigger-actions", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"]]))], ["bs-trigger-action", _pS(_uM([["alignItems", "center"], ["justifyContent", "center"], ["width", 28], ["height", 28], ["marginLeft", 8], ["borderTopLeftRadius", 999], ["borderTopRightRadius", 999], ["borderBottomRightRadius", 999], ["borderBottomLeftRadius", 999], ["backgroundColor", "#F5F7FA"]]))], ["bs-trigger-action-icon", _pS(_uM([["fontSize", 14], ["color", "#666666"], ["lineHeight", "14px"]]))], ["bs-trigger-arrow", _pS(_uM([["width", 20], ["height", 20], ["alignItems", "center"], ["justifyContent", "center"], ["marginLeft", 8]]))], ["bs-arrow-icon", _pS(_uM([["fontSize", 20], ["color", "#CCCCCC"], ["lineHeight", "20px"]]))], ["bs-overlay", _pS(_uM([["position", "fixed"], ["top", 0], ["left", 0], ["right", 0], ["bottom", 0], ["backgroundColor", "rgba(0,0,0,0)"], ["zIndex", 998], ["opacity", 0], ["transitionProperty", "opacity,backgroundColor"], ["transitionDuration", "320ms"], ["transitionTimingFunction", "ease"]]))], ["bs-overlay-active", _pS(_uM([["backgroundColor", "rgba(10,18,30,0.32)"], ["opacity", 1]]))], ["bs-panel", _pS(_uM([["position", "fixed"], ["left", 0], ["right", 0], ["bottom", 0], ["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", 22], ["borderTopRightRadius", 22], ["borderBottomRightRadius", 0], ["borderBottomLeftRadius", 0], ["zIndex", 999], ["flexDirection", "column"], ["opacity", 0], ["transform", "translateY(48px)"], ["boxShadow", "0 -18px 44px rgba(15, 23, 42, 0.14)"], ["transitionProperty", "transform,opacity"], ["transitionDuration", "340ms"], ["transitionTimingFunction", "ease"]]))], ["bs-panel-active", _pS(_uM([["opacity", 1], ["transform", "translateY(0px)"]]))], ["bs-handle-wrap", _pS(_uM([["alignItems", "center"], ["paddingTop", 10], ["paddingBottom", 4]]))], ["bs-handle", _pS(_uM([["width", 44], ["height", 5], ["borderTopLeftRadius", 999], ["borderTopRightRadius", 999], ["borderBottomRightRadius", 999], ["borderBottomLeftRadius", 999], ["backgroundColor", "#D9DEE7"]]))], ["bs-header", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "space-between"], ["paddingTop", 10], ["paddingBottom", 16], ["paddingLeft", 16], ["paddingRight", 16], ["borderBottomWidth", 1], ["borderBottomColor", "#F0F0F0"], ["borderBottomStyle", "solid"]]))], ["bs-header-title", _pS(_uM([["fontSize", 16], ["fontWeight", "bold"], ["color", "#333333"]]))], ["bs-header-close", _pS(_uM([["width", 32], ["height", 32], ["alignItems", "center"], ["justifyContent", "center"]]))], ["bs-close-icon", _pS(_uM([["fontSize", 16], ["color", "#999999"]]))], ["bs-search-bar", _pS(_uM([["paddingTop", 12], ["paddingBottom", 12], ["paddingLeft", 16], ["paddingRight", 16], ["borderBottomWidth", 1], ["borderBottomColor", "#F0F0F0"], ["borderBottomStyle", "solid"]]))], ["bs-search-inner", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"], ["backgroundColor", "#F5F5F5"], ["borderTopLeftRadius", 20], ["borderTopRightRadius", 20], ["borderBottomRightRadius", 20], ["borderBottomLeftRadius", 20], ["paddingLeft", 12], ["paddingRight", 12], ["height", 36]]))], ["bs-search-icon", _pS(_uM([["fontSize", 14], ["color", "#999999"], ["marginRight", 6]]))], ["bs-search-input", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["fontSize", 14], ["color", "#333333"], ["height", 36]]))], ["bs-search-clear", _pS(_uM([["width", 20], ["height", 20], ["alignItems", "center"], ["justifyContent", "center"], ["marginLeft", 4]]))], ["bs-clear-icon", _pS(_uM([["fontSize", 12], ["color", "#999999"]]))], ["bs-tags-bar", _pS(_uM([["flexDirection", "row"], ["paddingTop", 8], ["paddingBottom", 8], ["paddingLeft", 16], ["paddingRight", 16], ["borderBottomWidth", 1], ["borderBottomColor", "#F0F0F0"], ["borderBottomStyle", "solid"]]))], ["bs-tags-inner", _pS(_uM([["flexDirection", "row"], ["flexWrap", "nowrap"]]))], ["bs-tag", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"], ["backgroundColor", "#E8F0FE"], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["paddingTop", 4], ["paddingBottom", 4], ["paddingLeft", 10], ["paddingRight", 10], ["marginRight", 8]]))], ["bs-tag-text", _pS(_uM([["fontSize", 12], ["color", "#4A90E2"]]))], ["bs-tag-remove", _pS(_uM([["width", 16], ["height", 16], ["alignItems", "center"], ["justifyContent", "center"], ["marginLeft", 4]]))], ["bs-tag-remove-icon", _pS(_uM([["fontSize", 10], ["color", "#4A90E2"]]))], ["bs-list-item", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "space-between"], ["paddingTop", 14], ["paddingBottom", 14], ["paddingLeft", 16], ["paddingRight", 16], ["borderBottomWidth", 1], ["borderBottomColor", "#F5F5F5"], ["borderBottomStyle", "solid"]]))], ["bs-list-item-selected", _pS(_uM([["backgroundColor", "#F0F5FF"]]))], ["bs-item-label", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["fontSize", 14], ["color", "#333333"], ["marginRight", 12]]))], ["bs-item-label-selected", _pS(_uM([["color", "#4A90E2"]]))], ["bs-check-icon-wrapper", _pS(_uM([["width", 20], ["height", 20], ["alignItems", "center"], ["justifyContent", "center"]]))], ["bs-check-icon", _pS(_uM([["fontSize", 16], ["color", "#4A90E2"]]))], ["bs-state-wrapper", _pS(_uM([["alignItems", "center"], ["justifyContent", "center"], ["paddingTop", 60], ["paddingBottom", 60]]))], ["bs-state-text", _pS(_uM([["fontSize", 14], ["color", "#999999"]]))], ["bs-load-more", _pS(_uM([["alignItems", "center"], ["paddingTop", 16], ["paddingBottom", 16]]))], ["bs-load-more-text", _pS(_uM([["fontSize", 12], ["color", "#BBBBBB"]]))], ["bs-confirm-bar", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "space-between"], ["paddingTop", 12], ["paddingBottom", 12], ["paddingLeft", 16], ["paddingRight", 16], ["borderTopWidth", 1], ["borderTopColor", "#F0F0F0"], ["borderTopStyle", "solid"]]))], ["bs-confirm-info", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"]]))], ["bs-confirm-count", _pS(_uM([["fontSize", 13], ["color", "#666666"]]))], ["bs-confirm-btns", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"]]))], ["bs-btn-clear", _pS(_uM([["paddingTop", 8], ["paddingBottom", 8], ["paddingLeft", 16], ["paddingRight", 16], ["borderTopLeftRadius", 18], ["borderTopRightRadius", 18], ["borderBottomRightRadius", 18], ["borderBottomLeftRadius", 18], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopColor", "#DDDDDD"], ["borderRightColor", "#DDDDDD"], ["borderBottomColor", "#DDDDDD"], ["borderLeftColor", "#DDDDDD"], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["marginRight", 12], ["alignItems", "center"], ["justifyContent", "center"]]))], ["bs-btn-clear-text", _pS(_uM([["fontSize", 14], ["color", "#666666"]]))], ["bs-btn-confirm", _pS(_uM([["paddingTop", 8], ["paddingBottom", 8], ["paddingLeft", 24], ["paddingRight", 24], ["borderTopLeftRadius", 18], ["borderTopRightRadius", 18], ["borderBottomRightRadius", 18], ["borderBottomLeftRadius", 18], ["backgroundColor", "#4A90E2"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["bs-btn-confirm-text", _pS(_uM([["fontSize", 14], ["color", "#FFFFFF"]]))], ["@TRANSITION", _uM([["bs-overlay", _uM([["property", "opacity,backgroundColor"], ["duration", "320ms"], ["timingFunction", "ease"]])], ["bs-panel", _uM([["property", "transform,opacity"], ["duration", "340ms"], ["timingFunction", "ease"]])]])]])]
