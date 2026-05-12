import _easycom_lili_universal_filter from '@/uni_modules/lili-universal-filter/components/lili-universal-filter/lili-universal-filter.uvue'
import _easycom_lili_UniversalList from '@/uni_modules/lili-UniversalList/components/lili-UniversalList/lili-UniversalList.uvue'
import { computed } from 'vue'
import { takeLatestResponseMessage } from '@/pkg/api/index.uts'
import { deletePurchase, getPurchaseList, PurchaseItem, PurchaseListResponse, runPurchaseAction } from '@/pkg/api/modules/purchases.uts'


const __sfc__ = defineComponent({
  __name: 'index',
  setup(__props) {
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

const refreshStorageKey = 'refresh:pages:purchases:index'
const keyword = ref('')
const purchases = ref<PurchaseItem[]>([])
const isLoading = ref(false)
const errorMessage = ref('')
const currentPage = ref(1)
const totalPages = ref(1)
const totalCount = ref(0)
const pageSize = ref(20)
const pageTotalAmount = ref('0.00')

const fieldConfig = ref<UTSJSONObject[]>([
	{ key: 'supplierText', label: '供应商:' } as UTSJSONObject,
	{ key: 'quantityText', label: '数量:' } as UTSJSONObject,
	{ key: 'progressText', label: '收货:' } as UTSJSONObject,
	{ key: 'remarkText', label: '备注:' } as UTSJSONObject,
])

const menuActions = ref<UTSJSONObject[]>([
	{ key: 'details', text: '明细' } as UTSJSONObject,
	{ key: 'edit', text: '编辑' } as UTSJSONObject,
	{ key: 'approve', text: '审核' } as UTSJSONObject,
	{ key: 'complete', text: '完成' } as UTSJSONObject,
	{ key: 'cancel', text: '取消' } as UTSJSONObject,
	{ key: 'delete', text: '删除' } as UTSJSONObject,
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

function applyResponse(response: PurchaseListResponse) {
	purchases.value = response.results
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

async function loadPurchases() {
	if (isLoading.value) return
	isLoading.value = true
	errorMessage.value = ''
	try {
		const response = await getPurchaseList({
			search: keyword.value == '' ? null : keyword.value,
			page: currentPage.value,
			page_size: pageSize.value,
			status: null,
			receive_status: null,
			supplier: null,
			date_from: null,
			date_to: null,
			min_amount: null,
			max_amount: null,
		})
		applyResponse(response)
	} catch (error) {
		purchases.value = []
		currentPage.value = 1
		totalPages.value = 1
		totalCount.value = 0
		pageTotalAmount.value = '0.00'
		errorMessage.value = parseErrorMessage(error, '采购单加载失败')
	} finally {
		isLoading.value = false
	}
}

function purchaseToListItem(item: PurchaseItem): UTSJSONObject {
	const numberText = stringValue(item.purchase_number, '采购单')
	const statusText = stringValue(item.status_display, item.status)
	return {
		id: item.id.toString(),
		rawId: item.id.toString(),
		title: numberText,
		subtitle: '日期：' + stringValue(item.purchase_date, '-'),
		amountText: '¥ ' + stringValue(item.total_amount, '0.00'),
		supplierText: stringValue(item.supplier_name, '-'),
		quantityText: item.received_quantity.toString() + '/' + item.total_quantity.toString(),
		progressText: stringValue(item.receive_progress, '0') + '%',
		remarkText: stringValue(item.remark, '-'),
		tags: [statusText, item.is_fully_received ? '已收齐' : '未收齐'] as string[],
	} as UTSJSONObject
}

function consumeRefresh(): boolean {
	const flag = uni.getStorageSync(refreshStorageKey)
	if (flag == null || ('' + flag) == '') return false
	uni.removeStorageSync(refreshStorageKey)
	return true
}

function handleSearchInput(value: string) { keyword.value = value }
function handleSearchConfirm(value: string) { keyword.value = value; currentPage.value = 1; loadPurchases() }
function handleSearchClear() { keyword.value = ''; currentPage.value = 1; loadPurchases() }
function handleCreate() { uni.navigateTo({ url: '/pages/purchases/from' }) }
function handleItemClick(payload: UTSJSONObject) {
	const id = stringValue(payload['rawId'], stringValue(payload['id']))
	if (id != '') uni.navigateTo({ url: '/pages/purchases/details/index?purchase=' + id })
}

function handlePageChange(payload: UTSJSONObject) {
	const pageValue = payload['page']
	if (pageValue == null) return
	const nextPage = parseInt('' + pageValue)
	if (isNaN(nextPage) || nextPage <= 0 || nextPage == currentPage.value) return
	currentPage.value = nextPage
	loadPurchases()
}

async function runAction(id: string, actionName: string) {
	try {
		await runPurchaseAction(id, actionName)
		uni.showToast({ title: takeLatestResponseMessage('操作成功'), icon: 'success' })
		loadPurchases()
	} catch (error) {
		uni.showToast({ title: parseErrorMessage(error, '操作失败'), icon: 'none' })
	}
}

function confirmAction(id: string, actionName: string, title: string, content: string) {
	uni.showModal({ title: title, content: content, success: (res) => { if (res.confirm) runAction(id, actionName) } })
}

async function runDelete(id: string) {
	try {
		await deletePurchase(id)
		uni.showToast({ title: takeLatestResponseMessage('删除成功'), icon: 'success' })
		loadPurchases()
	} catch (error) {
		uni.showToast({ title: parseErrorMessage(error, '删除失败'), icon: 'none' })
	}
}

function confirmDelete(id: string) {
	uni.showModal({ title: '删除采购单', content: '确定删除这张采购单吗？', success: (res) => { if (res.confirm) runDelete(id) } })
}

function handleMenu(payload: UTSJSONObject) {
	const action = payload['action']
	const item = payload['item']
	if (action == null || item == null) return
	const actionKey = stringValue((action as UTSJSONObject)['key'])
	const id = stringValue((item as UTSJSONObject)['rawId'])
	if (id == '') return
	if (actionKey == 'details') {
		uni.navigateTo({ url: '/pages/purchases/details/index?purchase=' + id })
		return
	}
	if (actionKey == 'edit') {
		uni.navigateTo({ url: '/pages/purchases/from?id=' + id })
		return
	}
	if (actionKey == 'delete') confirmDelete(id)
	if (actionKey == 'approve') confirmAction(id, 'approve', '审核采购单', '确定审核这张采购单吗？')
	if (actionKey == 'complete') confirmAction(id, 'complete', '完成采购单', '确定完成这张采购单吗？')
	if (actionKey == 'cancel') confirmAction(id, 'cancel', '取消采购单', '确定取消这张采购单吗？')
}

const listItems = computed((): UTSJSONObject[] => {
	const result: UTSJSONObject[] = []
	for (let index = 0; index < purchases.value.length; index += 1) result.push(purchaseToListItem(purchases.value[index]))
	return result
})

const emptyText = computed((): string => {
	if (isLoading.value) return '正在加载'
	if (keyword.value != '') return '没有匹配的采购单'
	return '暂无采购单'
})

const summaryItems = computed((): UTSJSONObject[] => {
	return [
		{ key: 'total', label: '采购单数', value: totalCount.value.toString() } as UTSJSONObject,
		{ key: 'amount', label: '本页金额', value: '¥ ' + pageTotalAmount.value } as UTSJSONObject,
		{ key: 'page', label: '页码', value: currentPage.value.toString() + '/' + totalPages.value.toString() } as UTSJSONObject,
	]
})

onLoad(() => { loadPurchases() })
onShow(() => { if (consumeRefresh()) loadPurchases() })

return (): any | null => {

const _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter",_easycom_lili_universal_filter)
const _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList",_easycom_lili_UniversalList)

  return _cE("view", _uM({ class: "page" }), [
    _cV(_component_lili_universal_filter, _uM({
      title: "采购单",
      searchPlaceholder: "采购单号、供应商、备注",
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
        isTrue(unref(errorMessage) != '' && !unref(isLoading))
          ? _cE("view", _uM({
              key: 0,
              class: "error-card"
            }), [
              _cE("text", _uM({ class: "error-title" }), "加载失败"),
              _cE("text", _uM({ class: "error-desc" }), _tD(unref(errorMessage)), 1 /* TEXT */),
              _cE("view", _uM({
                class: "retry-btn",
                onClick: loadPurchases
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
          loadingText: "正在加载采购单",
          keepContentOnLoading: true,
          inlineLoadingText: "采购单刷新中...",
          emptyText: emptyText.value,
          emptyIcon: "◎",
          showMenu: true,
          menuActions: unref(menuActions),
          showChevron: false,
          showPagination: true,
          currentPage: unref(currentPage),
          totalPages: unref(totalPages),
          totalCount: unref(totalCount),
          summaryTitle: "采购概览",
          summaryItems: summaryItems.value,
          showFloatingAdd: true,
          floatingAddText: "新增",
          onItemClick: handleItemClick,
          onMenu: handleMenu,
          onPageChange: handlePageChange,
          onFloatingAdd: handleCreate
        }), null, 8 /* PROPS */, ["items", "fields", "loading", "emptyText", "menuActions", "currentPage", "totalPages", "totalCount", "summaryItems"])
      ])
    ], 4 /* STYLE */)
  ])
}
}

})
export default __sfc__
const GenPagesPurchasesIndexStyles = [_uM([["page", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#F6F7FB"]]))], ["page-scroll", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#F6F7FB"]]))], ["page-content", _pS(_uM([["paddingTop", 6], ["paddingRight", 6], ["paddingBottom", 96], ["paddingLeft", 6]]))], ["error-card", _pS(_uM([["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["paddingTop", 18], ["paddingRight", 18], ["paddingBottom", 18], ["paddingLeft", 18], ["marginBottom", 10], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#FECACA"], ["borderRightColor", "#FECACA"], ["borderBottomColor", "#FECACA"], ["borderLeftColor", "#FECACA"], ["alignItems", "center"]]))], ["error-title", _pS(_uM([["fontSize", 18], ["lineHeight", "24px"], ["color", "#B42318"], ["fontWeight", "bold"]]))], ["error-desc", _pS(_uM([["fontSize", 14], ["lineHeight", "20px"], ["color", "#7F1D1D"], ["marginTop", 8], ["textAlign", "center"]]))], ["retry-btn", _pS(_uM([["marginTop", 14], ["height", 40], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#0F172A"], ["paddingLeft", 18], ["paddingRight", 18], ["alignItems", "center"], ["justifyContent", "center"]]))], ["retry-btn-text", _pS(_uM([["fontSize", 14], ["color", "#FFFFFF"]]))]])]
