import _easycom_lili_universal_filter from '@/uni_modules/lili-universal-filter/components/lili-universal-filter/lili-universal-filter.uvue'
import _easycom_lili_UniversalList from '@/uni_modules/lili-UniversalList/components/lili-UniversalList/lili-UniversalList.uvue'
import { computed } from 'vue'
import { takeLatestResponseMessage } from '@/pkg/api/index.uts'
import { deletePurchaseDetail, getPurchaseDetailList, getPurchaseDetail, PurchaseDetailItem, PurchaseDetailListResponse, PurchaseItem, receivePurchaseDetail } from '@/pkg/api/modules/purchases.uts'
import { scanCode, type GeneralCallbackResult, type ScanCodeOption, type ScanCodeSuccessCallbackResult } from '@/uni_modules/lime-scan'


const __sfc__ = defineComponent({
  __name: 'index',
  setup(__props) {
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

const refreshStorageKey = 'refresh:pages:purchases:details:index'
const purchaseListRefreshStorageKey = 'refresh:pages:purchases:index'
const purchaseId = ref('')
const purchaseInfo = ref<PurchaseItem | null>(null)
const keyword = ref('')
const details = ref<PurchaseDetailItem[]>([])
const isLoading = ref(false)
const errorMessage = ref('')
const currentPage = ref(1)
const totalPages = ref(1)
const totalCount = ref(0)
const pageSize = ref(20)
const pageTotalAmount = ref('0.00')

const fieldConfig = ref<UTSJSONObject[]>([
	{ key: 'skuText', label: 'SKU:' } as UTSJSONObject,
	{ key: 'quantityText', label: '数量:' } as UTSJSONObject,
	{ key: 'progressText', label: '收货:' } as UTSJSONObject,
])

const menuActions = ref<UTSJSONObject[]>([
	{ key: 'edit', text: '编辑' } as UTSJSONObject,
	{ key: 'receive', text: '收货' } as UTSJSONObject,
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

function applyResponse(response: PurchaseDetailListResponse) {
	details.value = response.results
	currentPage.value = response.current_page
	totalPages.value = response.total_pages
	totalCount.value = response.total_count
	pageSize.value = response.page_size
	let total = 0.0
	for (let index = 0; index < response.results.length; index += 1) {
		const amount = parseFloat(response.results[index].amount)
		if (!isNaN(amount)) total = total + amount
	}
	pageTotalAmount.value = total.toFixed(2)
}

async function loadPurchaseInfo() {
	if (purchaseId.value == '') return
	try {
		purchaseInfo.value = await getPurchaseDetail(purchaseId.value)
	} catch (error) {
	}
}

async function loadDetails() {
	if (isLoading.value) return
	if (purchaseId.value == '') {
		errorMessage.value = '缺少采购单 ID'
		return
	}
	isLoading.value = true
	errorMessage.value = ''
	try {
		const response = await getPurchaseDetailList({
			search: keyword.value == '' ? null : keyword.value,
			page: currentPage.value,
			page_size: pageSize.value,
			purchase: purchaseId.value,
			product: null,
			is_fully_received: null,
		})
		applyResponse(response)
		await loadPurchaseInfo()
	} catch (error) {
		details.value = []
		currentPage.value = 1
		totalPages.value = 1
		totalCount.value = 0
		pageTotalAmount.value = '0.00'
		errorMessage.value = parseErrorMessage(error, '采购明细加载失败')
	} finally {
		isLoading.value = false
	}
}

function markRefreshNeeded() {
	uni.setStorageSync(refreshStorageKey + ':' + purchaseId.value, '1')
}

function markPurchaseListRefreshNeeded() {
	uni.setStorageSync(purchaseListRefreshStorageKey, '1')
}

function consumeRefreshNeeded(): boolean {
	const storedValue = uni.getStorageSync(refreshStorageKey + ':' + purchaseId.value)
	if (storedValue == null || ('' + storedValue) == '') return false
	uni.removeStorageSync(refreshStorageKey + ':' + purchaseId.value)
	return true
}

function detailToListItem(item: PurchaseDetailItem): UTSJSONObject {
	const statusText = item.is_fully_received ? '已收齐' : '待收货'
	return {
		id: item.id.toString(),
		rawId: item.id.toString(),
		title: stringValue(item.product_name, '商品 #' + item.product.toString()),
		subtitle: '条码：' + stringValue(item.product_barcode, '-'),
		image: item.product_image,
		images: item.product_images,
		amountText: '¥ ' + stringValue(item.amount, '0.00'),
		skuText: stringValue(item.product_sku, '-'),
		quantityText: item.received_quantity.toString() + '/' + item.quantity.toString() + '，剩余 ' + item.remaining_quantity.toString(),
		progressText: stringValue(item.receive_progress, '0') + '%',
		notesText: stringValue(item.notes, '-'),
		tags: [statusText, '单价 ¥ ' + stringValue(item.unit_price, '0.00')] as string[],
	} as UTSJSONObject
}

function handleSearchInput(value: string) { keyword.value = value }
function handleSearchConfirm(value: string) { keyword.value = value; currentPage.value = 1; loadDetails() }
function handleSearchClear() { keyword.value = ''; currentPage.value = 1; loadDetails() }
function handleScanSearch() {
	scanCode({
		onlyFromCamera: true,
		success: (res: ScanCodeSuccessCallbackResult) => {
			const scanResult = res.result
			if (scanResult == '') return
			keyword.value = scanResult
			currentPage.value = 1
			loadDetails()
		},
		fail: (res: GeneralCallbackResult) => {
			const message = res.errMsg == '' ? '扫码失败' : res.errMsg
			uni.showToast({ title: message, icon: 'none' })
		},
	} as ScanCodeOption)
}
function handleCreate() { uni.navigateTo({ url: '/pages/purchases/details/from?purchase=' + purchaseId.value }) }

function handleItemClick(payload: UTSJSONObject) {
	const id = stringValue(payload['rawId'], stringValue(payload['id']))
	if (id != '') uni.navigateTo({ url: '/pages/purchases/details/from?purchase=' + purchaseId.value + '&id=' + id })
}

function handlePageChange(payload: UTSJSONObject) {
	const pageValue = payload['page']
	if (pageValue == null) return
	const nextPage = parseInt('' + pageValue)
	if (isNaN(nextPage) || nextPage <= 0 || nextPage == currentPage.value) return
	currentPage.value = nextPage
	loadDetails()
}

async function runDelete(id: string) {
	try {
		await deletePurchaseDetail(id)
		markPurchaseListRefreshNeeded()
		uni.showToast({ title: takeLatestResponseMessage('删除成功'), icon: 'success' })
		loadDetails()
	} catch (error) {
		uni.showToast({ title: parseErrorMessage(error, '删除失败'), icon: 'none' })
	}
}

function confirmDelete(id: string) {
	uni.showModal({ title: '删除明细', content: '确定删除这条采购明细吗？', success: (res) => { if (res.confirm) runDelete(id) } })
}

async function runReceive(id: string, quantity: number) {
	try {
		await receivePurchaseDetail(id, quantity, '前端收货')
		markPurchaseListRefreshNeeded()
		uni.showToast({ title: takeLatestResponseMessage('收货成功'), icon: 'success' })
		loadDetails()
	} catch (error) {
		uni.showToast({ title: parseErrorMessage(error, '收货失败'), icon: 'none' })
	}
}

function promptReceive(item: UTSJSONObject) {
	const id = stringValue(item['rawId'])
	const defaultValue = stringValue(item['remainingValue'], '1')
	uni.showModal({
		title: '采购收货',
		editable: true,
		placeholderText: '请输入本次数量',
		content: defaultValue,
		success: (res) => {
			if (!res.confirm) return
			const inputText = res.content == null ? defaultValue : ('' + res.content)
			const quantity = parseInt(inputText)
			if (isNaN(quantity) || quantity <= 0) {
				uni.showToast({ title: '请输入有效收货数量', icon: 'none' })
				return
			}
			runReceive(id, quantity)
		},
	})
}

function handleMenu(payload: UTSJSONObject) {
	const action = payload['action']
	const item = payload['item']
	if (action == null || item == null) return
	const actionKey = stringValue((action as UTSJSONObject)['key'])
	const itemObject = item as UTSJSONObject
	const id = stringValue(itemObject['rawId'])
	if (id == '') return
	if (actionKey == 'edit') {
		uni.navigateTo({ url: '/pages/purchases/details/from?purchase=' + purchaseId.value + '&id=' + id })
		return
	}
	if (actionKey == 'receive') promptReceive(itemObject)
	if (actionKey == 'delete') confirmDelete(id)
}

const pageTitle = computed((): string => {
	if (purchaseInfo.value == null) return '采购单明细'
	const info = purchaseInfo.value as PurchaseItem
	return '明细 ' + info.purchase_number
})

const listItems = computed((): UTSJSONObject[] => {
	const result: UTSJSONObject[] = []
	for (let index = 0; index < details.value.length; index += 1) {
		const row = detailToListItem(details.value[index])
		row['remainingValue'] = details.value[index].remaining_quantity.toString()
		result.push(row)
	}
	return result
})

const emptyText = computed((): string => {
	if (isLoading.value) return '正在加载'
	if (keyword.value != '') return '没有匹配的采购明细'
	return '暂无采购明细'
})

const summaryItems = computed((): UTSJSONObject[] => {
	return [
		{ key: 'total', label: '明细数', value: totalCount.value.toString() } as UTSJSONObject,
		{ key: 'amount', label: '本页金额', value: '¥ ' + pageTotalAmount.value } as UTSJSONObject,
		{ key: 'page', label: '页码', value: currentPage.value.toString() + '/' + totalPages.value.toString() } as UTSJSONObject,
	]
})

onLoad((query: OnLoadOptions) => {
	const purchaseValue = query['purchase']
	purchaseId.value = purchaseValue == null ? '' : ('' + purchaseValue)
	loadDetails()
})

onShow(() => { if (consumeRefreshNeeded()) loadDetails() })

return (): any | null => {

const _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter",_easycom_lili_universal_filter)
const _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList",_easycom_lili_UniversalList)

  return _cE("view", _uM({ class: "page" }), [
    _cV(_component_lili_universal_filter, _uM({
      title: pageTitle.value,
      searchPlaceholder: "商品名、SKU、条码",
      searchValue: unref(keyword),
      filterVisible: false,
      showBack: true,
      showSearch: true,
      showFilter: false,
      showScan: true,
      showHome: true,
      homePath: "/pages/purchases/index",
      onSearchInput: handleSearchInput,
      onSearchConfirm: handleSearchConfirm,
      onSearchClear: handleSearchClear,
      onScan: handleScanSearch
    }), null, 8 /* PROPS */, ["title", "searchValue"]),
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
                onClick: loadDetails
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
          loadingText: "正在加载采购明细",
          keepContentOnLoading: true,
          inlineLoadingText: "采购明细刷新中...",
          emptyText: emptyText.value,
          emptyIcon: "◎",
          showMenu: true,
          menuActions: unref(menuActions),
          showChevron: false,
          showPagination: true,
          currentPage: unref(currentPage),
          totalPages: unref(totalPages),
          totalCount: unref(totalCount),
          summaryTitle: "明细概览",
          summaryItems: summaryItems.value,
          showFloatingAdd: true,
          floatingAddText: "新增明细",
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
const GenPagesPurchasesDetailsIndexStyles = [_uM([["page", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#F6F7FB"]]))], ["page-scroll", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#F6F7FB"]]))], ["page-content", _pS(_uM([["paddingTop", 6], ["paddingRight", 6], ["paddingBottom", 96], ["paddingLeft", 6]]))], ["error-card", _pS(_uM([["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["paddingTop", 18], ["paddingRight", 18], ["paddingBottom", 18], ["paddingLeft", 18], ["marginBottom", 10], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#FECACA"], ["borderRightColor", "#FECACA"], ["borderBottomColor", "#FECACA"], ["borderLeftColor", "#FECACA"], ["alignItems", "center"]]))], ["error-title", _pS(_uM([["fontSize", 18], ["lineHeight", "24px"], ["color", "#B42318"], ["fontWeight", "bold"]]))], ["error-desc", _pS(_uM([["fontSize", 14], ["lineHeight", "20px"], ["color", "#7F1D1D"], ["marginTop", 8], ["textAlign", "center"]]))], ["retry-btn", _pS(_uM([["marginTop", 14], ["height", 40], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#0F172A"], ["paddingLeft", 18], ["paddingRight", 18], ["alignItems", "center"], ["justifyContent", "center"]]))], ["retry-btn-text", _pS(_uM([["fontSize", 14], ["color", "#FFFFFF"]]))]])]
