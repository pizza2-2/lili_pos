import _easycom_lili_universal_filter from '@/uni_modules/lili-universal-filter/components/lili-universal-filter/lili-universal-filter.uvue'
import _easycom_lili_UniversalList from '@/uni_modules/lili-UniversalList/components/lili-UniversalList/lili-UniversalList.uvue'
import { computed } from 'vue'
import { getOrderFilterOptions, getOrderList, getOrderStatistics, OrderFilterDefinition, OrderFilterOptionsResponse, OrderItem, OrderListQuery, OrderListResponse, OrderSelectedFilter, OrderStatistics } from '@/pkg/api/modules/orders.uts'

type DatePresetOption = { __$originalPosition?: UTSSourceMapPosition<"DatePresetOption", "pages/orders/index.uvue", 127, 6>;
	key: string
	text: string
}


const __sfc__ = defineComponent({
  __name: 'index',
  setup(__props) {
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

const keyword = ref('')
const filterVisible = ref(false)
const orders = ref<OrderItem[]>([])
const isLoading = ref(false)
const reloadAfterLoading = ref(false)
const errorMessage = ref('')
const currentPage = ref(1)
const totalPages = ref(1)
const totalCount = ref(0)
const pageSize = ref(20)
const pageTotalAmount = ref('0.00')
const filterOptionsLoading = ref(false)
const filterOptionsError = ref('')
const filterOptions = ref<OrderFilterOptionsResponse | null>(null)
const selectedFilters = ref<OrderSelectedFilter[]>([])
const datePresetValue = ref('today')
const datePresetOptions = ref<DatePresetOption[]>([
	{ key: 'today', text: '今天' } as DatePresetOption,
	{ key: 'week', text: '本周' } as DatePresetOption,
	{ key: 'month', text: '本月' } as DatePresetOption,
	{ key: 'year', text: '本年' } as DatePresetOption,
	{ key: 'all', text: '全部' } as DatePresetOption,
])
const statistics = ref<OrderStatistics>({
	total_count: 0,
	inventory_deducted_count: 0,
	inventory_pending_count: 0,
	received_count: 0,
	processed_count: 0,
	failed_count: 0,
} as OrderStatistics)

const fieldConfig = ref<UTSJSONObject[]>([
	{ key: 'shopText', label: '店铺:' } as UTSJSONObject,
	{ key: 'cashierText', label: '收银员:' } as UTSJSONObject,
	{ key: 'itemsText', label: '商品:' } as UTSJSONObject,
	{ key: 'amountDetailText', label: '金额:' } as UTSJSONObject,
	{ key: 'inventoryText', label: '库存:' } as UTSJSONObject,
])

const menuActions = ref<UTSJSONObject[]>([
	{ key: 'detail', text: '详情' } as UTSJSONObject,
	{ key: 'copy', text: '复制单号' } as UTSJSONObject,
])

function stringValue(value: any | null, fallback: string = ''): string {
	if (value == null) return fallback
	const text = '' + value
	if (text == '') return fallback
	return text
}

function parseErrorMessage(error: any, fallback: string): string {
	let message = fallback
	if (error != null) {
		const directMessage = (error as Error).message
		if (directMessage != null && directMessage != '') message = directMessage
		const errorText = JSON.stringify(error)
		if (errorText != null && errorText != '') {
			const parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/orders/index.uvue:191")
			if (parsedError != null) {
				const rawMessage = parsedError['message']
				if (rawMessage != null) {
					const parsedMessage = rawMessage as string
					if (parsedMessage != '') message = parsedMessage
				}
			}
			if (message == fallback) message = errorText
		}
	}
	return message
}

function copyText(text: string, successTitle: string, emptyTitle: string) {
	if (text == '' || text == '-') {
		uni.showToast({ title: emptyTitle, icon: 'none' })
		return
	}
	uni.setClipboardData({ data: text, success: () => { uni.showToast({ title: successTitle, icon: 'success' }) } })
}

function statusText(item: OrderItem): string {
	const text = stringValue(item.status_display, item.status)
	if (text != '') return text
	if (item.status == 'received') return '已接收'
	if (item.status == 'processed') return '已处理'
	if (item.status == 'failed') return '处理失败'
	return '-'
}

function paymentText(item: OrderItem): string {
	const text = stringValue(item.payment_method_display, item.payment_method)
	if (text != '') return text
	if (item.payment_method == 'cash') return '现金'
	if (item.payment_method == 'card') return '银行卡'
	if (item.payment_method == 'mixed') return '混合支付'
	if (item.payment_method == 'other') return '其他'
	return '-'
}

function setSelectedFilterValue(param: string, value: string) {
	const nextFilters: OrderSelectedFilter[] = []
	let updated = false
	for (let index = 0; index < selectedFilters.value.length; index += 1) {
		const filter = selectedFilters.value[index]
		if (filter.param == param) {
			if (value != '') {
				nextFilters.push({ param: param, value: value } as OrderSelectedFilter)
			}
			updated = true
			continue
		}
		nextFilters.push(filter)
	}
	if (!updated && value != '') {
		nextFilters.push({ param: param, value: value } as OrderSelectedFilter)
	}
	selectedFilters.value = nextFilters
}

function selectedFilterValue(param: string): string {
	for (let index = 0; index < selectedFilters.value.length; index += 1) {
		const filter = selectedFilters.value[index]
		if (filter.param == param) return filter.value
	}
	return ''
}

function isFilterOptionSelected(param: string, value: string): boolean {
	return selectedFilterValue(param) == value
}

function toggleFilterOption(param: string, value: string, multiple: boolean = false) {
	if (!multiple) {
		const currentValue = selectedFilterValue(param)
		setSelectedFilterValue(param, currentValue == value ? '' : value)
		return
	}
	const currentValue = selectedFilterValue(param)
	const parts = currentValue == '' ? [] as string[] : currentValue.split(',')
	const nextParts: string[] = []
	let removed = false
	for (let index = 0; index < parts.length; index += 1) {
		if (parts[index] == value) {
			removed = true
			continue
		}
		nextParts.push(parts[index])
	}
	if (!removed) nextParts.push(value)
	setSelectedFilterValue(param, nextParts.join(','))
}

function pad2(value: number): string {
	return value < 10 ? '0' + value.toString() : value.toString()
}

function dateValue(date: Date): string {
	return date.getFullYear().toString() + '-' + pad2(date.getMonth() + 1) + '-' + pad2(date.getDate())
}

function addDays(date: Date, days: number): Date {
	return new Date(date.getFullYear(), date.getMonth(), date.getDate() + days)
}

function todayDate(): Date {
	const now = new Date()
	return new Date(now.getFullYear(), now.getMonth(), now.getDate())
}

function datePresetLabel(): string {
	if (datePresetValue.value == 'week') return '本周'
	if (datePresetValue.value == 'month') return '本月'
	if (datePresetValue.value == 'year') return '本年'
	if (datePresetValue.value == 'all') return '全部'
	return '今天'
}

function dateRangeFrom(): string | null {
	if (datePresetValue.value == 'all') return null
	const today = todayDate()
	if (datePresetValue.value == 'week') {
		const weekday = today.getDay()
		const diff = weekday == 0 ? 6 : weekday - 1
		return dateValue(addDays(today, 0 - diff))
	}
	if (datePresetValue.value == 'month') return dateValue(new Date(today.getFullYear(), today.getMonth(), 1))
	if (datePresetValue.value == 'year') return dateValue(new Date(today.getFullYear(), 0, 1))
	return dateValue(today)
}

function dateRangeTo(): string | null {
	if (datePresetValue.value == 'all') return null
	return dateValue(todayDate())
}

function selectDatePreset(value: string) {
	datePresetValue.value = value
}

function buildQuery(page: number): OrderListQuery {
	const query: OrderListQuery = {
		search: keyword.value == '' ? null : keyword.value,
		page: page,
		page_size: pageSize.value,
		status: selectedFilterValue('status') == '' ? null : selectedFilterValue('status'),
		payment_method: selectedFilterValue('payment_method') == '' ? null : selectedFilterValue('payment_method'),
		cashier_id: selectedFilterValue('cashier_id') == '' ? null : selectedFilterValue('cashier_id'),
		inventory_deducted: selectedFilterValue('inventory_deducted') == '' ? null : selectedFilterValue('inventory_deducted'),
		date_from: dateRangeFrom(),
		date_to: dateRangeTo(),
	}
	return query
}

function applyResponse(response: OrderListResponse) {
	orders.value = response.results
	currentPage.value = response.current_page
	totalPages.value = response.total_pages
	totalCount.value = response.total_count
	pageSize.value = response.page_size
	let total = 0.0
	for (let index = 0; index < response.results.length; index += 1) {
		const amount = parseFloat(response.results[index].total_amount)
		if (!isNaN(amount)) total = total + amount
	}
	pageTotalAmount.value = total.toFixed(2)
}

async function loadStatistics() {
	try {
		statistics.value = await getOrderStatistics(buildQuery(1))
	} catch (error) {
	}
}

async function loadOrders() {
	if (isLoading.value) {
		reloadAfterLoading.value = true
		return
	}
	isLoading.value = true
	errorMessage.value = ''
	try {
		const response = await getOrderList(buildQuery(currentPage.value))
		applyResponse(response)
		await loadStatistics()
	} catch (error) {
		orders.value = []
		currentPage.value = 1
		totalPages.value = 1
		totalCount.value = 0
		pageTotalAmount.value = '0.00'
		errorMessage.value = parseErrorMessage(error, '订单加载失败')
	} finally {
		isLoading.value = false
		if (reloadAfterLoading.value) {
			reloadAfterLoading.value = false
			loadOrders()
		}
	}
}

function orderToListItem(item: OrderItem): UTSJSONObject {
	const shopText = stringValue(item.shop_name, item.shop > 0 ? '店铺 #' + item.shop.toString() : '-')
	const timeText = stringValue(item.order_time, stringValue(item.created_at, '-'))
	const itemCountText = item.item_count.toString() + '项 / ' + item.quantity_count.toString() + '件'
	const amountDetailText = '小计 ' + stringValue(item.subtotal, '0.00') + ' / 税 ' + stringValue(item.tax_amount, '0.00') + ' / 折扣 ' + stringValue(item.discount_amount, '0.00')
	return {
		id: item.id.toString(),
		rawId: item.id.toString(),
		orderNumber: item.order_number,
		title: stringValue(item.order_number, '订单 #' + item.id.toString()),
		subtitle: '时间：' + timeText,
		amountText: '¥ ' + stringValue(item.total_amount, '0.00'),
		shopText: shopText,
		cashierText: stringValue(item.cashier_id, '-') + ' / ' + stringValue(item.kasa_number, '-'),
		itemsText: itemCountText,
		amountDetailText: amountDetailText,
		inventoryText: item.inventory_deducted ? '已扣减' : '未扣减',
		tags: [statusText(item), paymentText(item)] as string[],
	} as UTSJSONObject
}

function reloadFirstPage() {
	currentPage.value = 1
	loadOrders()
}

function handleSearchInput(value: string) { keyword.value = value }
function handleSearchConfirm(value: string) { keyword.value = value; reloadFirstPage() }
function handleSearchClear() { keyword.value = ''; reloadFirstPage() }

function handleFilterVisibleChange(value: boolean) { filterVisible.value = value }

async function loadFilterOptions() {
	if (filterOptions.value != null || filterOptionsLoading.value) return
	filterOptionsLoading.value = true
	filterOptionsError.value = ''
	try {
		filterOptions.value = await getOrderFilterOptions()
	} catch (error) {
		filterOptionsError.value = parseErrorMessage(error, '筛选选项加载失败')
	} finally {
		filterOptionsLoading.value = false
	}
}

function handleFilterOpen() {
	loadFilterOptions()
}

function handleFilterReset() {
	selectedFilters.value = [] as OrderSelectedFilter[]
	datePresetValue.value = 'today'
	filterVisible.value = false
	reloadFirstPage()
}

function applySelectedFilters() {
	filterVisible.value = false
	reloadFirstPage()
}

function handlePageChange(payload: UTSJSONObject) {
	const pageValue = payload['page']
	if (pageValue == null) return
	const nextPage = parseInt('' + pageValue)
	if (isNaN(nextPage) || nextPage <= 0 || nextPage == currentPage.value) return
	currentPage.value = nextPage
	loadOrders()
}

function openDetail(id: string) {
	if (id == '') return
	uni.navigateTo({ url: '/pages/orders/from?id=' + id })
}

function handleItemClick(payload: UTSJSONObject) {
	openDetail(stringValue(payload['rawId'], stringValue(payload['id'])))
}

function handleSubtitleClick(payload: UTSJSONObject) {
	copyText(stringValue(payload['value']), '时间已复制', '暂无时间')
}

function handleMetaClick(payload: UTSJSONObject) {
	copyText(stringValue(payload['value']), '金额已复制', '暂无金额')
}

function handleFieldClick(payload: UTSJSONObject) {
	copyText(stringValue(payload['value']), '内容已复制', '暂无内容')
}

function handleMenu(payload: UTSJSONObject) {
	const action = payload['action']
	const item = payload['item']
	if (action == null || item == null) return
	const actionKey = stringValue((action as UTSJSONObject)['key'])
	const itemObject = item as UTSJSONObject
	const id = stringValue(itemObject['rawId'])
	if (actionKey == 'detail') {
		openDetail(id)
		return
	}
	if (actionKey == 'copy') {
		copyText(stringValue(itemObject['orderNumber']), '订单号已复制', '暂无订单号')
	}
}

const listItems = computed((): UTSJSONObject[] => {
	const result: UTSJSONObject[] = []
	for (let index = 0; index < orders.value.length; index += 1) result.push(orderToListItem(orders.value[index]))
	return result
})

const emptyText = computed((): string => {
	if (isLoading.value) return '正在加载'
	if (keyword.value != '' || selectedFilters.value.length > 0) return datePresetLabel() + '没有匹配的订单'
	if (datePresetValue.value != 'all') return datePresetLabel() + '暂无订单'
	return '暂无订单'
})

const hasActiveFilter = computed((): boolean => {
	return selectedFilters.value.length > 0 || datePresetValue.value != 'today'
})

const filterDefinitions = computed((): OrderFilterDefinition[] => {
	if (filterOptions.value == null) return [] as OrderFilterDefinition[]
	return filterOptions.value!.filters
})

const summaryItems = computed((): UTSJSONObject[] => {
	return [
		{ key: 'dateRange', label: '范围', value: datePresetLabel() } as UTSJSONObject,
		{ key: 'total', label: '订单数', value: totalCount.value.toString() } as UTSJSONObject,
		{ key: 'pageAmount', label: '本页金额', value: '¥ ' + pageTotalAmount.value } as UTSJSONObject,
		{ key: 'processed', label: '已处理', value: statistics.value.processed_count.toString() } as UTSJSONObject,
		{ key: 'pendingStock', label: '待扣库存', value: statistics.value.inventory_pending_count.toString() } as UTSJSONObject,
		{ key: 'failed', label: '异常', value: statistics.value.failed_count.toString() } as UTSJSONObject,
	]
})

onLoad(() => { loadOrders() })

return (): any | null => {

const _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter",_easycom_lili_universal_filter)
const _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList",_easycom_lili_UniversalList)

  return _cE("view", _uM({ class: "page" }), [
    _cV(_component_lili_universal_filter, _uM({
      title: "订单管理",
      searchPlaceholder: "订单号、收银员、收银台",
      searchValue: unref(keyword),
      filterVisible: unref(filterVisible),
      showBack: true,
      showSearch: true,
      showFilter: true,
      filterActive: hasActiveFilter.value,
      filterText: "筛选",
      showHome: true,
      homePath: "/pages/tabbar/settings",
      onSearchInput: handleSearchInput,
      onSearchConfirm: handleSearchConfirm,
      onSearchClear: handleSearchClear,
      "onUpdate:filterVisible": handleFilterVisibleChange,
      onFilterOpen: handleFilterOpen
    }), _uM({
      "filter-panel": withSlotCtx((): any[] => [
        _cE("view", _uM({ class: "order-filter-panel" }), [
          _cE("view", _uM({ class: "order-filter-groups" }), [
            _cE("view", _uM({ class: "order-filter-group" }), [
              _cE("text", _uM({ class: "order-filter-title" }), "日期范围"),
              _cE("view", _uM({ class: "order-filter-options" }), [
                _cE(Fragment, null, RenderHelpers.renderList(unref(datePresetOptions), (option, __key, __index, _cached): any => {
                  return _cE("view", _uM({
                    key: 'date-' + option.key,
                    class: _nC(unref(datePresetValue) == option.key ? 'order-filter-option order-filter-option-active' : 'order-filter-option'),
                    onClick: () => {selectDatePreset(option.key)}
                  }), [
                    _cE("text", _uM({
                      class: _nC(unref(datePresetValue) == option.key ? 'order-filter-option-text order-filter-option-text-active' : 'order-filter-option-text')
                    }), _tD(option.text), 3 /* TEXT, CLASS */)
                  ], 10 /* CLASS, PROPS */, ["onClick"])
                }), 128 /* KEYED_FRAGMENT */)
              ])
            ])
          ]),
          isTrue(unref(filterOptionsLoading))
            ? _cE("view", _uM({
                key: 0,
                class: "order-filter-state"
              }), [
                _cE("text", _uM({ class: "order-filter-state-text" }), "筛选选项加载中...")
              ])
            : unref(filterOptionsError) != ''
              ? _cE("view", _uM({
                  key: 1,
                  class: "order-filter-state"
                }), [
                  _cE("text", _uM({ class: "order-filter-state-text" }), _tD(unref(filterOptionsError)), 1 /* TEXT */)
                ])
              : filterDefinitions.value.length == 0
                ? _cE("view", _uM({
                    key: 2,
                    class: "order-filter-state order-filter-state-small"
                  }), [
                    _cE("text", _uM({ class: "order-filter-state-text" }), "暂无更多筛选项")
                  ])
                : _cE("view", _uM({
                    key: 3,
                    class: "order-filter-groups"
                  }), [
                    _cE(Fragment, null, RenderHelpers.renderList(filterDefinitions.value, (filter, __key, __index, _cached): any => {
                      return _cE("view", _uM({
                        key: filter.key,
                        class: "order-filter-group"
                      }), [
                        _cE("text", _uM({ class: "order-filter-title" }), _tD(filter.label), 1 /* TEXT */),
                        _cE("view", _uM({ class: "order-filter-options" }), [
                          _cE(Fragment, null, RenderHelpers.renderList(filter.options, (option, __key, __index, _cached): any => {
                            return _cE("view", _uM({
                              key: filter.key + '-' + option.value,
                              class: _nC(isFilterOptionSelected(filter.param, option.value) ? 'order-filter-option order-filter-option-active' : 'order-filter-option'),
                              onClick: () => {toggleFilterOption(filter.param, option.value, filter.multiple)}
                            }), [
                              _cE("text", _uM({
                                class: _nC(isFilterOptionSelected(filter.param, option.value) ? 'order-filter-option-text order-filter-option-text-active' : 'order-filter-option-text')
                              }), _tD(option.label), 3 /* TEXT, CLASS */)
                            ], 10 /* CLASS, PROPS */, ["onClick"])
                          }), 128 /* KEYED_FRAGMENT */)
                        ])
                      ])
                    }), 128 /* KEYED_FRAGMENT */)
                  ]),
          _cE("view", _uM({ class: "order-filter-actions" }), [
            _cE("view", _uM({
              class: "order-filter-btn order-filter-btn-light",
              onClick: handleFilterReset
            }), [
              _cE("text", _uM({ class: "order-filter-btn-light-text" }), "重置")
            ]),
            _cE("view", _uM({
              class: "order-filter-btn order-filter-btn-primary",
              onClick: applySelectedFilters
            }), [
              _cE("text", _uM({ class: "order-filter-btn-primary-text" }), "应用")
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
                onClick: loadOrders
              }), [
                _cE("text", _uM({ class: "retry-btn-text" }), "重新加载")
              ])
            ])
          : _cC("v-if", true),
        _cV(_component_lili_UniversalList, _uM({
          items: listItems.value,
          keyField: "id",
          titleField: "title",
          subtitleField: "subtitle",
          metaField: "amountText",
          tagField: "tags",
          fields: unref(fieldConfig),
          loading: unref(isLoading),
          loadingText: "正在加载订单",
          keepContentOnLoading: true,
          inlineLoadingText: "订单数据刷新中...",
          emptyText: emptyText.value,
          emptyIcon: "◎",
          showMenu: true,
          menuActions: unref(menuActions),
          showChevron: false,
          showPagination: true,
          currentPage: unref(currentPage),
          totalPages: unref(totalPages),
          totalCount: unref(totalCount),
          summaryTitle: "订单概览",
          summaryItems: summaryItems.value,
          summaryCollapsedByDefault: false,
          showFloatingAdd: false,
          onItemClick: handleItemClick,
          onSubtitleClick: handleSubtitleClick,
          onMetaClick: handleMetaClick,
          onFieldClick: handleFieldClick,
          onMenu: handleMenu,
          onPageChange: handlePageChange
        }), null, 8 /* PROPS */, ["items", "fields", "loading", "emptyText", "menuActions", "currentPage", "totalPages", "totalCount", "summaryItems"])
      ])
    ], 4 /* STYLE */)
  ])
}
}

})
export default __sfc__
const GenPagesOrdersIndexStyles = [_uM([["page", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#F6F7FB"]]))], ["page-scroll", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#F6F7FB"]]))], ["page-content", _pS(_uM([["paddingTop", 6], ["paddingRight", 6], ["paddingBottom", 96], ["paddingLeft", 6]]))], ["order-filter-panel", _pS(_uM([["position", "relative"], ["paddingTop", 2], ["paddingBottom", 52], ["backgroundColor", "#FFFFFF"]]))], ["order-filter-state", _pS(_uM([["height", 112], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#F8FAFC"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["order-filter-state-small", _pS(_uM([["height", 58]]))], ["order-filter-state-text", _pS(_uM([["fontSize", 12], ["lineHeight", "17px"], ["color", "#64748B"]]))], ["order-filter-groups", _pS(_uM([["marginBottom", 6]]))], ["order-filter-group", _pS(_uM([["paddingLeft", 10], ["paddingRight", 10], ["paddingTop", 10], ["paddingBottom", 10], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#FFFFFF"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E5EAF1"], ["borderRightColor", "#E5EAF1"], ["borderBottomColor", "#E5EAF1"], ["borderLeftColor", "#E5EAF1"], ["marginBottom", 6]]))], ["order-filter-title", _pS(_uM([["fontSize", 13], ["lineHeight", "18px"], ["color", "#0F172A"], ["fontWeight", "bold"]]))], ["order-filter-options", _pS(_uM([["flexDirection", "row"], ["flexWrap", "wrap"], ["marginTop", 8]]))], ["order-filter-option", _pS(_uM([["minWidth", 58], ["height", 30], ["paddingLeft", 10], ["paddingRight", 10], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#F8FAFC"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E2E8F0"], ["borderRightColor", "#E2E8F0"], ["borderBottomColor", "#E2E8F0"], ["borderLeftColor", "#E2E8F0"], ["alignItems", "center"], ["justifyContent", "center"], ["marginRight", 6], ["marginBottom", 6]]))], ["order-filter-option-active", _pS(_uM([["backgroundColor", "#0F172A"], ["borderTopColor", "#0F172A"], ["borderRightColor", "#0F172A"], ["borderBottomColor", "#0F172A"], ["borderLeftColor", "#0F172A"]]))], ["order-filter-option-text", _pS(_uM([["fontSize", 12], ["lineHeight", "17px"], ["color", "#334155"]]))], ["order-filter-option-text-active", _pS(_uM([["color", "#FFFFFF"], ["fontWeight", "bold"]]))], ["order-filter-actions", _pS(_uM([["position", "absolute"], ["left", 0], ["right", 0], ["bottom", 0], ["flexDirection", "row"], ["paddingTop", 6], ["paddingLeft", 2], ["paddingRight", 2], ["paddingBottom", 4], ["borderTopWidth", 1], ["borderTopStyle", "solid"], ["borderTopColor", "rgba(226,232,240,0.78)"], ["backgroundColor", "#FFFFFF"]]))], ["order-filter-btn", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["height", 38], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["alignItems", "center"], ["justifyContent", "center"]]))], ["order-filter-btn-light", _pS(_uM([["backgroundColor", "#F3F6FA"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E2E8F0"], ["borderRightColor", "#E2E8F0"], ["borderBottomColor", "#E2E8F0"], ["borderLeftColor", "#E2E8F0"], ["marginRight", 8]]))], ["order-filter-btn-primary", _pS(_uM([["backgroundColor", "#0F172A"]]))], ["order-filter-btn-light-text", _pS(_uM([["fontSize", 13], ["lineHeight", "18px"], ["color", "#475569"]]))], ["order-filter-btn-primary-text", _pS(_uM([["fontSize", 13], ["lineHeight", "18px"], ["color", "#FFFFFF"]]))], ["error-card", _pS(_uM([["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["paddingTop", 18], ["paddingRight", 18], ["paddingBottom", 18], ["paddingLeft", 18], ["marginBottom", 10], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#FECACA"], ["borderRightColor", "#FECACA"], ["borderBottomColor", "#FECACA"], ["borderLeftColor", "#FECACA"], ["alignItems", "center"]]))], ["error-title", _pS(_uM([["fontSize", 18], ["lineHeight", "24px"], ["color", "#B42318"], ["fontWeight", "bold"]]))], ["error-desc", _pS(_uM([["fontSize", 14], ["lineHeight", "20px"], ["color", "#7F1D1D"], ["marginTop", 8], ["textAlign", "center"]]))], ["retry-btn", _pS(_uM([["marginTop", 14], ["height", 40], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#0F172A"], ["paddingLeft", 18], ["paddingRight", 18], ["alignItems", "center"], ["justifyContent", "center"]]))], ["retry-btn-text", _pS(_uM([["fontSize", 14], ["color", "#FFFFFF"]]))]])]
