import _easycom_lili_universal_filter from '@/uni_modules/lili-universal-filter/components/lili-universal-filter/lili-universal-filter.uvue'
import _easycom_lili_UniversalList from '@/uni_modules/lili-UniversalList/components/lili-UniversalList/lili-UniversalList.uvue'
import { computed } from 'vue'
import { getOrderList, getOrderStatistics, OrderItem, OrderListQuery, OrderListResponse, OrderStatistics } from '@/pkg/api/modules/orders.uts'

type FilterItem = {
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
const statusValue = ref('')
const paymentValue = ref('')
const inventoryValue = ref('')
const orders = ref<OrderItem[]>([])
const isLoading = ref(false)
const errorMessage = ref('')
const currentPage = ref(1)
const totalPages = ref(1)
const totalCount = ref(0)
const pageSize = ref(20)
const pageTotalAmount = ref('0.00')
const statistics = ref<OrderStatistics>({
	total_count: 0,
	inventory_deducted_count: 0,
	inventory_pending_count: 0,
	received_count: 0,
	processed_count: 0,
	failed_count: 0,
} as OrderStatistics)

const statusFilters = ref<FilterItem[]>([
	{ key: '', text: '全部状态' } as FilterItem,
	{ key: 'received', text: '已接收' } as FilterItem,
	{ key: 'processed', text: '已处理' } as FilterItem,
	{ key: 'failed', text: '处理失败' } as FilterItem,
])

const paymentFilters = ref<FilterItem[]>([
	{ key: '', text: '全部支付' } as FilterItem,
	{ key: 'cash', text: '现金' } as FilterItem,
	{ key: 'card', text: '银行卡' } as FilterItem,
	{ key: 'mixed', text: '混合支付' } as FilterItem,
	{ key: 'other', text: '其他' } as FilterItem,
])

const inventoryFilters = ref<FilterItem[]>([
	{ key: '', text: '全部库存' } as FilterItem,
	{ key: 'false', text: '待扣减' } as FilterItem,
	{ key: 'true', text: '已扣减' } as FilterItem,
])

const fieldConfig = ref<UTSJSONObject[]>([
	{ key: 'shopText', label: '店铺:' } as UTSJSONObject,
	{ key: 'cashierText', label: '收银员:' } as UTSJSONObject,
	{ key: 'itemsText', label: '商品:' } as UTSJSONObject,
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
			const parsedError = JSON.parseObject<UTSJSONObject>(errorText)
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

function buildQuery(page: number): OrderListQuery {
	const query: OrderListQuery = {
		search: keyword.value == '' ? null : keyword.value,
		page: page,
		page_size: pageSize.value,
		status: statusValue.value == '' ? null : statusValue.value,
		payment_method: paymentValue.value == '' ? null : paymentValue.value,
		inventory_deducted: inventoryValue.value == '' ? null : inventoryValue.value,
		date_from: null,
		date_to: null,
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
	if (isLoading.value) return
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
	}
}

function orderToListItem(item: OrderItem): UTSJSONObject {
	const shopText = stringValue(item.shop_name, item.shop > 0 ? '店铺 #' + item.shop.toString() : '-')
	const timeText = stringValue(item.order_time, stringValue(item.created_at, '-'))
	const itemCountText = item.item_count.toString() + '项 / ' + item.quantity_count.toString() + '件'
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
		inventoryText: item.inventory_deducted ? '已扣减' : '未扣减',
		tags: [statusText(item), paymentText(item)] as string[],
	} as UTSJSONObject
}

function reloadFirstPage() {
	currentPage.value = 1
	loadOrders()
}

function selectStatus(value: any) {
	const next = stringValue(value)
	if (statusValue.value == next) return
	statusValue.value = next
	reloadFirstPage()
}

function selectPayment(value: any) {
	const next = stringValue(value)
	if (paymentValue.value == next) return
	paymentValue.value = next
	reloadFirstPage()
}

function selectInventory(value: any) {
	const next = stringValue(value)
	if (inventoryValue.value == next) return
	inventoryValue.value = next
	reloadFirstPage()
}

function handleSearchInput(value: string) { keyword.value = value }
function handleSearchConfirm(value: string) { keyword.value = value; reloadFirstPage() }
function handleSearchClear() { keyword.value = ''; reloadFirstPage() }

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
	if (keyword.value != '' || statusValue.value != '' || paymentValue.value != '' || inventoryValue.value != '') return '没有匹配的订单'
	return '暂无订单'
})

const summaryItems = computed((): UTSJSONObject[] => {
	return [
		{ key: 'total', label: '订单数', value: totalCount.value.toString() } as UTSJSONObject,
		{ key: 'pageAmount', label: '本页金额', value: '¥ ' + pageTotalAmount.value } as UTSJSONObject,
		{ key: 'processed', label: '已处理', value: statistics.value.processed_count.toString() } as UTSJSONObject,
		{ key: 'pendingStock', label: '待扣库存', value: statistics.value.inventory_pending_count.toString() } as UTSJSONObject,
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
      filterVisible: false,
      showBack: true,
      showSearch: true,
      showFilter: false,
      showHome: true,
      homePath: "/pages/tabbar/settings",
      onSearchInput: handleSearchInput,
      onSearchConfirm: handleSearchConfirm,
      onSearchClear: handleSearchClear
    }), null, 8 /* PROPS */, ["searchValue"]),
    _cE("scroll-view", _uM({
      style: _nS(_uM({"flex":"1"})),
      class: "page-scroll"
    }), [
      _cE("view", _uM({ class: "page-content" }), [
        _cE("view", _uM({ class: "filter-section" }), [
          _cE("scroll-view", _uM({
            "scroll-x": "true",
            class: "filter-scroll"
          }), [
            _cE("view", _uM({ class: "filter-row" }), [
              _cE(Fragment, null, RenderHelpers.renderList(unref(statusFilters), (item, __key, __index, _cached): any => {
                return _cE("view", _uM({
                  key: item.key,
                  class: _nC(unref(statusValue) == item.key ? 'filter-chip filter-chip-active' : 'filter-chip'),
                  onClick: () => {selectStatus(item.key)}
                }), [
                  _cE("text", _uM({
                    class: _nC(unref(statusValue) == item.key ? 'filter-chip-text filter-chip-text-active' : 'filter-chip-text')
                  }), _tD(item.text), 3 /* TEXT, CLASS */)
                ], 10 /* CLASS, PROPS */, ["onClick"])
              }), 128 /* KEYED_FRAGMENT */)
            ])
          ]),
          _cE("scroll-view", _uM({
            "scroll-x": "true",
            class: "filter-scroll"
          }), [
            _cE("view", _uM({ class: "filter-row" }), [
              _cE(Fragment, null, RenderHelpers.renderList(unref(paymentFilters), (item, __key, __index, _cached): any => {
                return _cE("view", _uM({
                  key: item.key,
                  class: _nC(unref(paymentValue) == item.key ? 'filter-chip filter-chip-active' : 'filter-chip'),
                  onClick: () => {selectPayment(item.key)}
                }), [
                  _cE("text", _uM({
                    class: _nC(unref(paymentValue) == item.key ? 'filter-chip-text filter-chip-text-active' : 'filter-chip-text')
                  }), _tD(item.text), 3 /* TEXT, CLASS */)
                ], 10 /* CLASS, PROPS */, ["onClick"])
              }), 128 /* KEYED_FRAGMENT */)
            ])
          ]),
          _cE("scroll-view", _uM({
            "scroll-x": "true",
            class: "filter-scroll"
          }), [
            _cE("view", _uM({ class: "filter-row" }), [
              _cE(Fragment, null, RenderHelpers.renderList(unref(inventoryFilters), (item, __key, __index, _cached): any => {
                return _cE("view", _uM({
                  key: item.key,
                  class: _nC(unref(inventoryValue) == item.key ? 'filter-chip filter-chip-active' : 'filter-chip'),
                  onClick: () => {selectInventory(item.key)}
                }), [
                  _cE("text", _uM({
                    class: _nC(unref(inventoryValue) == item.key ? 'filter-chip-text filter-chip-text-active' : 'filter-chip-text')
                  }), _tD(item.text), 3 /* TEXT, CLASS */)
                ], 10 /* CLASS, PROPS */, ["onClick"])
              }), 128 /* KEYED_FRAGMENT */)
            ])
          ])
        ]),
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
const GenPagesOrdersIndexStyles = [_uM([["page", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#F6F7FB"]]))], ["page-scroll", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#F6F7FB"]]))], ["page-content", _pS(_uM([["paddingTop", 6], ["paddingRight", 6], ["paddingBottom", 96], ["paddingLeft", 6]]))], ["filter-section", _pS(_uM([["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["paddingTop", 8], ["paddingBottom", 8], ["marginBottom", 8], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E5E7EB"], ["borderRightColor", "#E5E7EB"], ["borderBottomColor", "#E5E7EB"], ["borderLeftColor", "#E5E7EB"]]))], ["filter-scroll", _pS(_uM([["height", 42]]))], ["filter-row", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"], ["paddingLeft", 8], ["paddingRight", 8]]))], ["filter-chip", _pS(_uM([["height", 32], ["paddingLeft", 12], ["paddingRight", 12], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#D7DEE8"], ["borderRightColor", "#D7DEE8"], ["borderBottomColor", "#D7DEE8"], ["borderLeftColor", "#D7DEE8"], ["backgroundColor", "#FFFFFF"], ["alignItems", "center"], ["justifyContent", "center"], ["marginRight", 8]]))], ["filter-chip-active", _pS(_uM([["backgroundColor", "#0F172A"], ["borderTopColor", "#0F172A"], ["borderRightColor", "#0F172A"], ["borderBottomColor", "#0F172A"], ["borderLeftColor", "#0F172A"]]))], ["filter-chip-text", _pS(_uM([["fontSize", 13], ["lineHeight", "18px"], ["color", "#334155"]]))], ["filter-chip-text-active", _pS(_uM([["color", "#FFFFFF"], ["fontWeight", "bold"]]))], ["error-card", _pS(_uM([["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["paddingTop", 18], ["paddingRight", 18], ["paddingBottom", 18], ["paddingLeft", 18], ["marginBottom", 10], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#FECACA"], ["borderRightColor", "#FECACA"], ["borderBottomColor", "#FECACA"], ["borderLeftColor", "#FECACA"], ["alignItems", "center"]]))], ["error-title", _pS(_uM([["fontSize", 18], ["lineHeight", "24px"], ["color", "#B42318"], ["fontWeight", "bold"]]))], ["error-desc", _pS(_uM([["fontSize", 14], ["lineHeight", "20px"], ["color", "#7F1D1D"], ["marginTop", 8], ["textAlign", "center"]]))], ["retry-btn", _pS(_uM([["marginTop", 14], ["height", 40], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#0F172A"], ["paddingLeft", 18], ["paddingRight", 18], ["alignItems", "center"], ["justifyContent", "center"]]))], ["retry-btn-text", _pS(_uM([["fontSize", 14], ["color", "#FFFFFF"]]))]])]
