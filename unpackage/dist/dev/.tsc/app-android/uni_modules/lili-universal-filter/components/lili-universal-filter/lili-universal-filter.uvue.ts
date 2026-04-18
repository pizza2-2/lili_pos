type Props = { __$originalPosition?: UTSSourceMapPosition<"Props", "uni_modules/lili-universal-filter/components/lili-universal-filter/lili-universal-filter.uvue", 50, 6>;
	title?: string
	searchPlaceholder?: string
	searchValue?: string
	showBack?: boolean
	showSearch?: boolean
	showFilter?: boolean
	showHome?: boolean
	showMenu?: boolean
	showScan?: boolean
	filterActive?: boolean
	filterText?: string
	fixed?: boolean
	backgroundColor?: string
	homePath?: string
}


const __sfc__ = defineComponent({
  __name: 'lili-universal-filter',
  props: {
    title: { type: String, required: false, default: '' },
    searchPlaceholder: { type: String, required: false, default: '请输入搜索内容' },
    searchValue: { type: String, required: false, default: '' },
    showBack: { type: Boolean, required: false, default: false },
    showSearch: { type: Boolean, required: false, default: true },
    showFilter: { type: Boolean, required: false, default: false },
    showHome: { type: Boolean, required: false, default: false },
    showMenu: { type: Boolean, required: false, default: false },
    showScan: { type: Boolean, required: false, default: false },
    filterActive: { type: Boolean, required: false, default: false },
    filterText: { type: String, required: false, default: '筛选' },
    fixed: { type: Boolean, required: false, default: true },
    backgroundColor: { type: String, required: false, default: '#FFFFFF' },
    homePath: { type: String, required: false, default: '/pages/tabbar/products' }
  },
  emits: ["back", "home", "menu", "filter", "scan", "searchInput", "searchConfirm", "searchClear"],
  setup(__props) {
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

const props = __props

function emit(event: string, ...do_not_transform_spread: Array<any | null>) {
__ins.emit(event, ...do_not_transform_spread)
}

const statusBarHeight = ref<number>(0)
const inputValue = ref<string>(props.searchValue)
const rootStyle = ref<string>('')

function syncRootStyle() {
	const positionStyle = props.fixed ? 'position:sticky;top:0;' : ''
	rootStyle.value = `padding-top:${statusBarHeight.value}px;background-color:${props.backgroundColor};${positionStyle}`
}

function updateStatusBarHeight() {
	const info = uni.getWindowInfo()
	statusBarHeight.value = info.statusBarHeight
	syncRootStyle()
}

function eventValueToString(e: UTSJSONObject) : string {
	const detail = e["detail"]
	if (detail == null) return ''
	const detailObj = detail as UTSJSONObject
	const value = detailObj["value"]
	if (value == null) return ''
	return '' + value
}

function onSearchInput(e: UTSJSONObject) {
	emit('searchInput', inputValue.value != '' ? inputValue.value : eventValueToString(e))
}

function onSearchConfirm(e: UTSJSONObject) {
	emit('searchConfirm', inputValue.value != '' ? inputValue.value : eventValueToString(e))
}

function clearSearch() {
	inputValue.value = ''
	emit('searchClear')
}

function handleBack() {
	emit('back')
	uni.navigateBack({
		delta: 1,
		fail: () => {
			uni.reLaunch({
				url: props.homePath,
			})
		},
	})
}

function handleHome() {
	emit('home')
	uni.reLaunch({
		url: props.homePath,
	})
}

function handleFilter() {
	emit('filter')
}

function handleMenu() {
	emit('menu')
}

function handleScan() {
	emit('scan')
}

watch(
	() : string => props.searchValue,
	(newVal: string) => {
		if (newVal != inputValue.value) {
			inputValue.value = newVal
		}
	}
)

watch(
	() : string => props.backgroundColor,
	() => {
		syncRootStyle()
	}
)

watch(
	() : boolean => props.fixed,
	() => {
		syncRootStyle()
	}
)

onMounted(() => {
	updateStatusBarHeight()
})

return (): any | null => {

  return _cE("view", _uM({
    class: "uf-root",
    style: _nS(unref(rootStyle))
  }), [
    _cE("view", _uM({ class: "uf-bar" }), [
      isTrue(_ctx.showBack || _ctx.showHome)
        ? _cE("view", _uM({
            key: 0,
            class: "uf-segment-group uf-segment-group-left"
          }), [
            isTrue(_ctx.showBack)
              ? _cE("view", _uM({
                  key: 0,
                  class: _nC(_ctx.showBack && _ctx.showHome ? 'uf-segment uf-segment-split' : 'uf-segment'),
                  onClick: handleBack
                }), [
                  _cE("text", _uM({ class: "uf-segment-icon" }), "←")
                ], 2 /* CLASS */)
              : _cC("v-if", true),
            isTrue(_ctx.showHome)
              ? _cE("view", _uM({
                  key: 1,
                  class: "uf-segment",
                  onClick: handleHome
                }), [
                  _cE("text", _uM({ class: "uf-segment-icon" }), "⌂")
                ])
              : _cC("v-if", true)
          ])
        : _cC("v-if", true),
      isTrue(_ctx.showSearch)
        ? _cE("view", _uM({
            key: 1,
            class: "uf-search-wrap"
          }), [
            _cE("view", _uM({ class: "uf-search-box" }), [
              isTrue(_ctx.showScan)
                ? _cE("view", _uM({
                    key: 0,
                    class: "uf-scan-btn",
                    onClick: handleScan
                  }), [
                    _cE("text", _uM({ class: "uf-scan-icon" }), "▣")
                  ])
                : _cC("v-if", true),
              _cE("input", _uM({
                class: "uf-search-input",
                modelValue: unref(inputValue),
                onInput: [($event: UniInputEvent) => {trySetRefValue(inputValue, $event.detail.value)}, onSearchInput] as Array<any | null>,
                placeholder: _ctx.searchPlaceholder,
                onConfirm: onSearchConfirm,
                "confirm-type": "search"
              }), null, 40 /* PROPS, NEED_HYDRATION */, ["modelValue", "placeholder"]),
              unref(inputValue) != ''
                ? _cE("view", _uM({
                    key: 1,
                    class: "uf-clear-btn",
                    onClick: clearSearch
                  }), [
                    _cE("text", _uM({ class: "uf-clear-icon" }), "×")
                  ])
                : _cC("v-if", true)
            ])
          ])
        : _cE("view", _uM({
            key: 2,
            class: "uf-title-wrap"
          }), [
            _cE("text", _uM({ class: "uf-title" }), _tD(_ctx.title), 1 /* TEXT */)
          ]),
      isTrue(_ctx.showFilter || _ctx.showMenu)
        ? _cE("view", _uM({
            key: 3,
            class: "uf-segment-group uf-segment-group-right"
          }), [
            isTrue(_ctx.showFilter)
              ? _cE("view", _uM({
                  key: 0,
                  class: _nC(_ctx.showFilter && _ctx.showMenu ? 'uf-segment uf-segment-split uf-segment-dark' : 'uf-segment uf-segment-dark'),
                  onClick: handleFilter
                }), [
                  _cE("text", _uM({ class: "uf-segment-text-light" }), _tD(_ctx.filterText), 1 /* TEXT */),
                  isTrue(_ctx.filterActive)
                    ? _cE("view", _uM({
                        key: 0,
                        class: "uf-dot"
                      }))
                    : _cC("v-if", true)
                ], 2 /* CLASS */)
              : _cC("v-if", true),
            isTrue(_ctx.showMenu)
              ? _cE("view", _uM({
                  key: 1,
                  class: "uf-segment uf-segment-dark",
                  onClick: handleMenu
                }), [
                  _cE("text", _uM({ class: "uf-segment-icon-light" }), "≡")
                ])
              : _cC("v-if", true)
          ])
        : _cC("v-if", true)
    ])
  ], 4 /* STYLE */)
}
}

})
export default __sfc__
export type LiliUniversalFilterComponentPublicInstance = InstanceType<typeof __sfc__>;
const GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterStyles = [_uM([["uf-root", _pS(_uM([["zIndex", 90], ["backgroundColor", "#FFFFFF"], ["borderBottomWidth", 1], ["borderBottomColor", "#EEF1F5"], ["borderBottomStyle", "solid"]]))], ["uf-bar", _pS(_uM([["height", 56], ["flexDirection", "row"], ["alignItems", "center"], ["paddingLeft", 12], ["paddingRight", 12]]))], ["uf-segment-group", _pS(_uM([["height", 34], ["flexDirection", "row"], ["alignItems", "center"], ["borderTopLeftRadius", 17], ["borderTopRightRadius", 17], ["borderBottomRightRadius", 17], ["borderBottomLeftRadius", 17], ["backgroundColor", "#FFFFFF"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E2E8F0"], ["borderRightColor", "#E2E8F0"], ["borderBottomColor", "#E2E8F0"], ["borderLeftColor", "#E2E8F0"], ["overflow", "hidden"]]))], ["uf-segment-group-left", _pS(_uM([["marginRight", 8]]))], ["uf-segment-group-right", _pS(_uM([["marginLeft", 8]]))], ["uf-segment", _pS(_uM([["minWidth", 34], ["height", 34], ["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "center"], ["paddingLeft", 10], ["paddingRight", 10]]))], ["uf-segment-split", _pS(_uM([["borderRightWidth", 1], ["borderRightColor", "rgba(148,163,184,0.32)"], ["borderRightStyle", "solid"]]))], ["uf-segment-dark", _pS(_uM([["backgroundColor", "#0F172A"]]))], ["uf-segment-icon", _pS(_uM([["fontSize", 15], ["color", "#1F2937"], ["lineHeight", "15px"]]))], ["uf-segment-icon-light", _pS(_uM([["fontSize", 15], ["color", "#FFFFFF"], ["lineHeight", "15px"]]))], ["uf-search-wrap", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"]]))], ["uf-search-box", _pS(_uM([["height", 40], ["flexDirection", "row"], ["alignItems", "center"], ["borderTopLeftRadius", 20], ["borderTopRightRadius", 20], ["borderBottomRightRadius", 20], ["borderBottomLeftRadius", 20], ["backgroundColor", "#FFFFFF"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E2E8F0"], ["borderRightColor", "#E2E8F0"], ["borderBottomColor", "#E2E8F0"], ["borderLeftColor", "#E2E8F0"], ["paddingLeft", 12], ["paddingRight", 10]]))], ["uf-scan-btn", _pS(_uM([["width", 24], ["height", 24], ["alignItems", "center"], ["justifyContent", "center"], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["backgroundColor", "#E2E8F0"], ["marginRight", 8]]))], ["uf-scan-icon", _pS(_uM([["fontSize", 12], ["color", "#334155"], ["lineHeight", "12px"]]))], ["uf-search-input", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["height", 40], ["fontSize", 14], ["color", "#1F2937"], ["paddingRight", 6]]))], ["uf-clear-btn", _pS(_uM([["width", 24], ["height", 24], ["flexShrink", 0], ["alignItems", "center"], ["justifyContent", "center"], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["backgroundColor", "#D8DEE6"]]))], ["uf-clear-icon", _pS(_uM([["fontSize", 14], ["color", "#536171"], ["lineHeight", "14px"]]))], ["uf-title-wrap", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["justifyContent", "center"]]))], ["uf-title", _pS(_uM([["fontSize", 17], ["fontWeight", "bold"], ["color", "#111827"]]))], ["uf-segment-text-light", _pS(_uM([["fontSize", 12], ["color", "#FFFFFF"]]))], ["uf-dot", _pS(_uM([["width", 6], ["height", 6], ["borderTopLeftRadius", 3], ["borderTopRightRadius", 3], ["borderBottomRightRadius", 3], ["borderBottomLeftRadius", 3], ["backgroundColor", "#FB7185"], ["marginLeft", 5]]))]])]
