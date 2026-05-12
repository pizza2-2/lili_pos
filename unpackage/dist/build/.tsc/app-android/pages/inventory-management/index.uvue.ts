import _easycom_lili_universal_filter from '@/uni_modules/lili-universal-filter/components/lili-universal-filter/lili-universal-filter.uvue'
import _easycom_lili_UniversalList from '@/uni_modules/lili-UniversalList/components/lili-UniversalList/lili-UniversalList.uvue'
import { computed } from 'vue'
import { getInventoryStocks, InventoryListQuery, InventoryListResponse } from '@/pkg/api/modules/inventory'
import { scanCode, type GeneralCallbackResult, type ScanCodeOption, type ScanCodeSuccessCallbackResult } from '@/uni_modules/lime-scan'


const __sfc__ = defineComponent({
  __name: 'index',
  setup(__props) {
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

const keyword = ref('')
const refreshStorageKey = 'refresh:pages:inventory-management:index'
const filterVisible = ref(false)
const isLoading = ref(false)
const errorMessage = ref('')
const items = ref<UTSJSONObject[]>([])
const currentPage = ref(1)
const totalPages = ref(1)
const totalCount = ref(0)
const pageSize = ref(20)
const alertStatus = ref<string | null>(null)
const selectedAlertStatus = ref<string | null>(null)

const fieldConfig = ref<UTSJSONObject[]>([
	{ key: 'quantity_text', label: '现存' } as UTSJSONObject,
	{ key: 'available_text', label: '可用' } as UTSJSONObject,
	{ key: 'alert_text', label: '状态' } as UTSJSONObject,
])

const menuActions = ref<UTSJSONObject[]>([
	{ key: 'adjust-stock', text: '调整库存' } as UTSJSONObject,
	{ key: 'reload', text: '刷新' } as UTSJSONObject,
])

function stringField(obj: UTSJSONObject, key: string, fallback: string = ''): string {
	const value = obj[key]
	if (value == null) return fallback
	const text = '' + value
	if (text == '') return fallback
	return text
}

function parseErrorMessage(error: any, fallback: string): string {
	if (error == null) return fallback
	const text = JSON.stringify(error)
	if (text == null || text == '') return fallback
	return text
}

function copyText(text: string, successTitle: string, emptyTitle: string) {
	if (text == '' || text == '-') {
		uni.showToast({ title: emptyTitle, icon: 'none' })
		return
	}
	uni.setClipboardData({ data: text, success: () => { uni.showToast({ title: successTitle, icon: 'success' }) } })
}

function alertLabel(value: string): string {
	if (value == 'LOW_STOCK') return '低库存'
	if (value == 'OUT_OF_STOCK') return '缺货'
	if (value == 'NO_MOVEMENT') return '久未动'
	return '正常'
}

function applyListResponse(response: InventoryListResponse) {
	items.value = response.results
	currentPage.value = response.current_page
	totalPages.value = response.total_pages
	totalCount.value = response.total_count
	pageSize.value = response.page_size
}

async function loadItems() {
	if (isLoading.value) return
	isLoading.value = true
	errorMessage.value = ''
	try {
		const query = {
			search: keyword.value == '' ? null : keyword.value,
			page: currentPage.value,
			page_size: pageSize.value,
			status: null,
			alert_status: alertStatus.value,
			transaction_type: null,
			location_type: null,
			is_active: null,
		} as InventoryListQuery
		applyListResponse(await getInventoryStocks(query))
	} catch (error) {
		items.value = [] as UTSJSONObject[]
		currentPage.value = 1
		totalPages.value = 1
		totalCount.value = 0
		errorMessage.value = parseErrorMessage(error, '库存数据加载失败')
	} finally {
		isLoading.value = false
	}
}

function selectAlertStatus(value: string | null) {
	alertStatus.value = value
	currentPage.value = 1
	loadItems()
}

function handleSearchInput(value: string) { keyword.value = value }

function handleSearchConfirm(value: string) {
	keyword.value = value
	currentPage.value = 1
	loadItems()
}

function handleSearchClear() {
	keyword.value = ''
	currentPage.value = 1
	loadItems()
}

function handleScanSearch() {
	scanCode({
		onlyFromCamera: true,
		success: (res: ScanCodeSuccessCallbackResult) => {
			const scanResult = res.result
			if (scanResult == '') return
			keyword.value = scanResult
			currentPage.value = 1
			filterVisible.value = false
			loadItems()
		},
		fail: (res: GeneralCallbackResult) => {
			const message = res.errMsg == '' ? '扫码失败' : res.errMsg
			uni.showToast({ title: message, icon: 'none' })
		},
	} as ScanCodeOption)
}

function handleFilterVisibleChange(value: boolean) { filterVisible.value = value }
function handleFilterOpen() { selectedAlertStatus.value = alertStatus.value }
function selectFilterStatus(value: string | null) { selectedAlertStatus.value = value }
function handleFilterReset() {
	selectedAlertStatus.value = null
	alertStatus.value = null
	currentPage.value = 1
	filterVisible.value = false
	loadItems()
}
function applyFilter() {
	alertStatus.value = selectedAlertStatus.value
	currentPage.value = 1
	filterVisible.value = false
	loadItems()
}

function handlePageChange(payload: UTSJSONObject) {
	const pageValue = payload['page']
	if (pageValue == null) return
	const nextPage = parseInt('' + pageValue)
	if (isNaN(nextPage) || nextPage <= 0 || nextPage == currentPage.value) return
	currentPage.value = nextPage
	loadItems()
}

function stockItem(item: UTSJSONObject): UTSJSONObject {
	const status = stringField(item, 'alert_status', 'NORMAL')
	return {
		id: stringField(item, 'id'),
		rawId: stringField(item, 'id'),
		name: stringField(item, 'product_name', '未命名商品'),
		subtitle: 'SKU：' + stringField(item, 'product_sku', '-'),
		meta: stringField(item, 'location_name', '-'),
		quantity_text: stringField(item, 'quantity', '0'),
		available_text: stringField(item, 'available_quantity', '0'),
		alert_text: alertLabel(status),
		tags: [alertLabel(status)] as string[],
	} as UTSJSONObject
}

function openStockAdjust(id: string) {
	if (id == '') return
	uni.navigateTo({ url: '/pages/inventory-management/from?id=' + id })
}

function handleMenu(payload: UTSJSONObject) {
	const action = payload['action']
	const item = payload['item']
	if (action == null || item == null) return
	const actionKey = stringField(action as UTSJSONObject, 'key')
	const itemObject = item as UTSJSONObject
	const id = stringField(itemObject, 'rawId')
	if (actionKey == 'adjust-stock') openStockAdjust(id)
	else if (actionKey == 'reload') loadItems()
}

function handleSubtitleClick(payload: UTSJSONObject) {
	const item = payload['item']
	if (item == null) return
	copyText(stringField(item as UTSJSONObject, 'subtitle'), '内容已复制', '暂无内容')
}

function handleFieldClick(payload: UTSJSONObject) {
	const item = payload['item']
	const keyValue = payload['key']
	if (item == null || keyValue == null) return
	copyText(stringField(item as UTSJSONObject, keyValue as string), '内容已复制', '暂无内容')
}

const listItems = computed((): UTSJSONObject[] => {
	const result: UTSJSONObject[] = []
	for (let index = 0; index < items.value.length; index += 1) result.push(stockItem(items.value[index]))
	return result
})

const emptyText = computed((): string => {
	if (isLoading.value) return '正在加载'
	if (keyword.value != '') return '没有匹配的库存'
	return '暂无库存'
})

const summaryItems = computed((): UTSJSONObject[] => {
	return [
		{ key: 'total', label: '库存记录', value: totalCount.value.toString() } as UTSJSONObject,
		{ key: 'page', label: '页码', value: currentPage.value.toString() + '/' + totalPages.value.toString() } as UTSJSONObject,
	]
})

const hasActiveFilter = computed((): boolean => {
	return alertStatus.value != null
})

onLoad(() => { loadItems() })
onShow(() => {
	const flag = uni.getStorageSync(refreshStorageKey)
	if (flag != null && flag != '') {
		uni.removeStorageSync(refreshStorageKey)
		loadItems()
	}
})

return (): any | null => {

const _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter",_easycom_lili_universal_filter)
const _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList",_easycom_lili_UniversalList)

  return _cE("view", _uM({ class: "page" }), [
    _cV(_component_lili_universal_filter, _uM({
      title: "库存管理",
      searchPlaceholder: "商品名称、SKU、条码",
      searchValue: unref(keyword),
      filterVisible: unref(filterVisible),
      showBack: true,
      showSearch: true,
      showFilter: true,
      showScan: true,
      showHome: true,
      filterActive: hasActiveFilter.value,
      filterText: "重置",
      homePath: "/pages/tabbar/settings",
      onSearchInput: handleSearchInput,
      onSearchConfirm: handleSearchConfirm,
      onSearchClear: handleSearchClear,
      onScan: handleScanSearch,
      "onUpdate:filterVisible": handleFilterVisibleChange,
      onFilterOpen: handleFilterOpen
    }), _uM({
      "filter-panel": withSlotCtx((): any[] => [
        _cE("view", _uM({ class: "inventory-filter-panel" }), [
          _cE("view", _uM({ class: "inventory-filter-actions" }), [
            _cE("view", _uM({
              class: "inventory-filter-btn inventory-filter-btn-light",
              onClick: handleFilterReset
            }), [
              _cE("text", _uM({ class: "inventory-filter-btn-light-text" }), "重置")
            ]),
            _cE("view", _uM({
              class: "inventory-filter-btn inventory-filter-btn-primary",
              onClick: applyFilter
            }), [
              _cE("text", _uM({ class: "inventory-filter-btn-primary-text" }), "应用")
            ])
          ]),
          _cE("view", _uM({ class: "inventory-filter-group" }), [
            _cE("text", _uM({ class: "inventory-filter-group-title" }), "库存状态"),
            _cE("view", _uM({ class: "inventory-filter-options" }), [
              _cE("view", _uM({
                class: _nC(unref(selectedAlertStatus) == null ? 'inventory-filter-option inventory-filter-option-active' : 'inventory-filter-option'),
                onClick: () => {selectFilterStatus(null)}
              }), [
                _cE("text", _uM({
                  class: _nC(unref(selectedAlertStatus) == null ? 'inventory-filter-option-text inventory-filter-option-text-active' : 'inventory-filter-option-text')
                }), "全部", 2 /* CLASS */)
              ], 10 /* CLASS, PROPS */, ["onClick"]),
              _cE("view", _uM({
                class: _nC(unref(selectedAlertStatus) == 'LOW_STOCK' ? 'inventory-filter-option inventory-filter-option-active' : 'inventory-filter-option'),
                onClick: () => {selectFilterStatus('LOW_STOCK')}
              }), [
                _cE("text", _uM({
                  class: _nC(unref(selectedAlertStatus) == 'LOW_STOCK' ? 'inventory-filter-option-text inventory-filter-option-text-active' : 'inventory-filter-option-text')
                }), "低库存", 2 /* CLASS */)
              ], 10 /* CLASS, PROPS */, ["onClick"]),
              _cE("view", _uM({
                class: _nC(unref(selectedAlertStatus) == 'OUT_OF_STOCK' ? 'inventory-filter-option inventory-filter-option-active' : 'inventory-filter-option'),
                onClick: () => {selectFilterStatus('OUT_OF_STOCK')}
              }), [
                _cE("text", _uM({
                  class: _nC(unref(selectedAlertStatus) == 'OUT_OF_STOCK' ? 'inventory-filter-option-text inventory-filter-option-text-active' : 'inventory-filter-option-text')
                }), "缺货", 2 /* CLASS */)
              ], 10 /* CLASS, PROPS */, ["onClick"]),
              _cE("view", _uM({
                class: _nC(unref(selectedAlertStatus) == 'NO_MOVEMENT' ? 'inventory-filter-option inventory-filter-option-active' : 'inventory-filter-option'),
                onClick: () => {selectFilterStatus('NO_MOVEMENT')}
              }), [
                _cE("text", _uM({
                  class: _nC(unref(selectedAlertStatus) == 'NO_MOVEMENT' ? 'inventory-filter-option-text inventory-filter-option-text-active' : 'inventory-filter-option-text')
                }), "久未动", 2 /* CLASS */)
              ], 10 /* CLASS, PROPS */, ["onClick"])
            ])
          ])
        ])
      ]),
      _: 1 /* STABLE */
    }), 8 /* PROPS */, ["searchValue", "filterVisible", "filterActive"]),
    _cE("scroll-view", _uM({
      style: _nS(_uM({"flex":"1"})),
      class: "page-scroll"
    }), [
      _cE("view", _uM({ class: "page-content" }), [
        isTrue(unref(errorMessage) != '' && !unref(isLoading))
          ? _cE("view", _uM({
              key: 0,
              class: "error-card"
            }), [
              _cE("text", _uM({ class: "error-title" }), "加载失败"),
              _cE("text", _uM({ class: "error-desc" }), _tD(unref(errorMessage)), 1 /* TEXT */),
              _cE("view", _uM({
                class: "retry-btn",
                onClick: loadItems
              }), [
                _cE("text", _uM({ class: "retry-btn-text" }), "重新加载")
              ])
            ])
          : _cC("v-if", true),
        _cV(_component_lili_UniversalList, _uM({
          items: listItems.value,
          keyField: "id",
          titleField: "name",
          subtitleField: "subtitle",
          metaField: "meta",
          tagField: "tags",
          fields: unref(fieldConfig),
          loading: unref(isLoading),
          loadingText: "正在加载库存",
          keepContentOnLoading: true,
          inlineLoadingText: "库存数据刷新中...",
          emptyText: emptyText.value,
          emptyIcon: "◎",
          showMenu: true,
          menuActions: unref(menuActions),
          showChevron: false,
          showPagination: true,
          currentPage: unref(currentPage),
          totalPages: unref(totalPages),
          totalCount: unref(totalCount),
          summaryTitle: "库存概览",
          summaryItems: summaryItems.value,
          summaryCollapsedByDefault: false,
          showFloatingAdd: false,
          onMenu: handleMenu,
          onPageChange: handlePageChange,
          onSubtitleClick: handleSubtitleClick,
          onFieldClick: handleFieldClick
        }), null, 8 /* PROPS */, ["items", "fields", "loading", "emptyText", "menuActions", "currentPage", "totalPages", "totalCount", "summaryItems"])
      ])
    ], 4 /* STYLE */)
  ])
}
}

})
export default __sfc__
const GenPagesInventoryManagementIndexStyles = [_uM([["page", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#F6F7FB"]]))], ["page-scroll", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#F6F7FB"]]))], ["page-content", _pS(_uM([["paddingTop", 6], ["paddingRight", 6], ["paddingBottom", 96], ["paddingLeft", 6]]))], ["inventory-filter-panel", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#FFFFFF"], ["paddingTop", 14], ["paddingRight", 14], ["paddingBottom", 14], ["paddingLeft", 14]]))], ["inventory-filter-actions", _pS(_uM([["flexDirection", "row"], ["justifyContent", "flex-end"], ["marginBottom", 12]]))], ["inventory-filter-btn", _pS(_uM([["height", 38], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["paddingLeft", 18], ["paddingRight", 18], ["alignItems", "center"], ["justifyContent", "center"], ["marginLeft", 8]]))], ["inventory-filter-btn-light", _pS(_uM([["backgroundColor", "#F8FAFC"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#CBD5E1"], ["borderRightColor", "#CBD5E1"], ["borderBottomColor", "#CBD5E1"], ["borderLeftColor", "#CBD5E1"]]))], ["inventory-filter-btn-primary", _pS(_uM([["backgroundColor", "#0F172A"]]))], ["inventory-filter-btn-light-text", _pS(_uM([["fontSize", 14], ["color", "#334155"]]))], ["inventory-filter-btn-primary-text", _pS(_uM([["fontSize", 14], ["color", "#FFFFFF"]]))], ["inventory-filter-group", _pS(_uM([["marginTop", 6]]))], ["inventory-filter-group-title", _pS(_uM([["fontSize", 14], ["lineHeight", "20px"], ["color", "#0F172A"], ["fontWeight", "bold"]]))], ["inventory-filter-options", _pS(_uM([["flexDirection", "row"], ["flexWrap", "wrap"], ["marginTop", 10]]))], ["inventory-filter-option", _pS(_uM([["height", 34], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["paddingLeft", 12], ["paddingRight", 12], ["backgroundColor", "#F8FAFC"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E2E8F0"], ["borderRightColor", "#E2E8F0"], ["borderBottomColor", "#E2E8F0"], ["borderLeftColor", "#E2E8F0"], ["alignItems", "center"], ["justifyContent", "center"], ["marginRight", 8], ["marginBottom", 8]]))], ["inventory-filter-option-active", _pS(_uM([["backgroundColor", "#0F172A"], ["borderTopColor", "#0F172A"], ["borderRightColor", "#0F172A"], ["borderBottomColor", "#0F172A"], ["borderLeftColor", "#0F172A"]]))], ["inventory-filter-option-text", _pS(_uM([["fontSize", 13], ["color", "#475569"]]))], ["inventory-filter-option-text-active", _pS(_uM([["color", "#FFFFFF"]]))], ["error-card", _pS(_uM([["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["paddingTop", 18], ["paddingRight", 18], ["paddingBottom", 18], ["paddingLeft", 18], ["marginBottom", 10], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#FECACA"], ["borderRightColor", "#FECACA"], ["borderBottomColor", "#FECACA"], ["borderLeftColor", "#FECACA"], ["alignItems", "center"]]))], ["error-title", _pS(_uM([["fontSize", 18], ["lineHeight", "24px"], ["color", "#B42318"], ["fontWeight", "bold"]]))], ["error-desc", _pS(_uM([["fontSize", 14], ["lineHeight", "20px"], ["color", "#7F1D1D"], ["marginTop", 8], ["textAlign", "center"]]))], ["retry-btn", _pS(_uM([["marginTop", 14], ["height", 40], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#0F172A"], ["paddingLeft", 18], ["paddingRight", 18], ["alignItems", "center"], ["justifyContent", "center"]]))], ["retry-btn-text", _pS(_uM([["fontSize", 14], ["color", "#FFFFFF"]]))]])]
