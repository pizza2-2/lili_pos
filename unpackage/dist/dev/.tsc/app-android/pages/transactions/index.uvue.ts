import _easycom_lili_universal_filter from '@/uni_modules/lili-universal-filter/components/lili-universal-filter/lili-universal-filter.uvue'
import _easycom_lili_UniversalList from '@/uni_modules/lili-UniversalList/components/lili-UniversalList/lili-UniversalList.uvue'
import { computed } from 'vue'
import { takeLatestResponseMessage } from '@/pkg/api/index.uts'
import { deleteTransaction, getTransactionFilterOptions, getTransactionList, getTransactionStatistics, TransactionFilterDefinition, TransactionFilterOption, TransactionFilterOptionsResponse, TransactionItem, TransactionListResponse, TransactionMediaFile, TransactionSummary, TransactionStatisticsResponse } from '@/pkg/api/modules/transactions.uts'

type TransactionSelectedFilter = { __$originalPosition?: UTSSourceMapPosition<"TransactionSelectedFilter", "pages/transactions/index.uvue", 157, 6>;
	param: string
	value: string
}


const __sfc__ = defineComponent({
  __name: 'index',
  setup(__props) {
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

const transactionListRefreshStorageKey = 'refresh:pages:transactions:index'

const supplierId = ref('')
const supplierName = ref('')
const keyword = ref('')
const filterVisible = ref(false)
const transactions = ref<TransactionItem[]>([])
const isLoading = ref(false)
const errorMessage = ref('')
const currentPage = ref(1)
const totalPages = ref(1)
const totalCount = ref(0)
const pageSize = ref(20)
const summary = ref<TransactionSummary | null>(null)
const statistics = ref<TransactionStatisticsResponse | null>(null)
const filterOptionsLoading = ref(false)
const filterOptionsError = ref('')
const filterOptions = ref<TransactionFilterOptionsResponse | null>(null)
const selectedFilters = ref<TransactionSelectedFilter[]>([])
const filterDateFrom = ref('')
const filterDateTo = ref('')
const filterAmountMin = ref('')
const filterAmountMax = ref('')
const selectedOrdering = ref('-transaction_date')
const filterPanelHeight = ref(440)
const filterContentHeight = ref(376)

const fieldConfig = ref<UTSJSONObject[]>([
	{ key: 'transactionTypeText', label: '类型:' } as UTSJSONObject,
	{ key: 'filesText', label: '附件:' } as UTSJSONObject,
	{ key: 'noteText', label: '备注:' } as UTSJSONObject,
])

const menuActions = ref<UTSJSONObject[]>([
	{ key: 'edit', text: '编辑' } as UTSJSONObject,
	{ key: 'delete', text: '删除' } as UTSJSONObject,
])

const defaultFilterDefinitions = ref<TransactionFilterDefinition[]>([
	{
		key: 'transaction_type',
		param: 'transaction_type',
		label: '记录类型',
		control: 'choice',
		aliases: [] as string[],
		multiple: false,
		options: [
			{ value: '1', label: '采购' } as TransactionFilterOption,
			{ value: '2', label: '欠单' } as TransactionFilterOption,
			{ value: '3', label: '还款' } as TransactionFilterOption,
		],
	} as TransactionFilterDefinition,
])

const sortOptions = ref<UTSJSONObject[]>([
	{ value: '-transaction_date', label: '日期最新' } as UTSJSONObject,
	{ value: 'transaction_date', label: '日期最早' } as UTSJSONObject,
	{ value: '-amount', label: '金额最高' } as UTSJSONObject,
	{ value: 'amount', label: '金额最低' } as UTSJSONObject,
	{ value: '-created_at', label: '最近创建' } as UTSJSONObject,
])

const tagColorMap = ref<UTSJSONObject>({
	采购: 'success',
	欠单: 'warning',
	还款: 'info',
	无附件: 'muted',
} as UTSJSONObject)

function stringValue(value: any | null, fallback: string = ''): string {
	if (value == null) return fallback
	const text = '' + value
	if (text == '') return fallback
	return text
}

function applyTransactionResponse(response: TransactionListResponse) {
	transactions.value = response.results
	currentPage.value = response.current_page
	totalPages.value = response.total_pages
	totalCount.value = response.total_count
	pageSize.value = response.page_size
	summary.value = response.summary
}

function parseErrorMessage(error: any): string {
	let message = '采购记录加载失败'
	if (error != null) {
		const directMessage = (error as Error).message
		if (directMessage != null && directMessage != '') {
			message = directMessage
		}
		const errorText = JSON.stringify(error)
		if (errorText != null && errorText != '') {
			const parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/transactions/index.uvue:256")
			if (parsedError != null) {
				const rawMessage = parsedError['message']
				if (rawMessage != null) {
					const parsedMessage = rawMessage as string
					if (parsedMessage != '') {
						message = parsedMessage
					}
				}
			}
			if (message == '采购记录加载失败') {
				message = errorText
			}
		}
	}
	if (message.startsWith('Error: ')) return message.substring(7)
	return message
}

function readEventValue(event: any): string {
	if (event == null) return ''
	const inputEvent = event as UniInputEvent
	const detail = inputEvent.detail
	if (detail == null) return ''
	return detail.value
}

function updateFilterPanelLayout() {
	const info = uni.getWindowInfo()
	let nextPanelHeight = info.windowHeight - 168
	if (nextPanelHeight > 440) nextPanelHeight = 440
	if (nextPanelHeight < 320) nextPanelHeight = 320
	let nextContentHeight = nextPanelHeight - 64
	if (nextContentHeight < 240) nextContentHeight = 240
	filterPanelHeight.value = nextPanelHeight
	filterContentHeight.value = nextContentHeight
}

function closeFilterDrawer() {
	filterVisible.value = false
}

function handleFilterVisibleChange(value: boolean) {
	filterVisible.value = value
}

function setSelectedFilterValue(param: string, value: string) {
	const nextFilters: TransactionSelectedFilter[] = []
	let updated = false
	for (let index = 0; index < selectedFilters.value.length; index += 1) {
		const filter = selectedFilters.value[index]
		if (filter.param == param) {
			if (value != '') {
				nextFilters.push({ param: param, value: value } as TransactionSelectedFilter)
			}
			updated = true
			continue
		}
		nextFilters.push(filter)
	}
	if (!updated && value != '') {
		nextFilters.push({ param: param, value: value } as TransactionSelectedFilter)
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

function toggleFilterOption(param: string, value: string) {
	const currentValue = selectedFilterValue(param)
	setSelectedFilterValue(param, currentValue == value ? '' : value)
}

function optionValue(option: UTSJSONObject): string {
	return stringValue(option['value'])
}

function optionLabel(option: UTSJSONObject): string {
	return stringValue(option['label'])
}

function optionKey(option: UTSJSONObject): string {
	const value = optionValue(option)
	if (value != '') return value
	return optionLabel(option)
}

function selectOrdering(value: string) {
	selectedOrdering.value = value
}

function handleDateFromInput(event: any) {
	filterDateFrom.value = readEventValue(event).trim()
}

function handleDateToInput(event: any) {
	filterDateTo.value = readEventValue(event).trim()
}

function handleAmountMinInput(event: any) {
	filterAmountMin.value = readEventValue(event).trim()
}

function handleAmountMaxInput(event: any) {
	filterAmountMax.value = readEventValue(event).trim()
}

function transactionTypeFilterValue(): string | null {
	const value = selectedFilterValue('transaction_type')
	return value == '' ? null : value
}

function markTransactionListRefreshNeeded() {
	uni.setStorageSync(transactionListRefreshStorageKey, '1')
}

function consumeTransactionListRefreshNeeded(): boolean {
	const storedValue = uni.getStorageSync(transactionListRefreshStorageKey)
	if (storedValue == null) {
		return false
	}

	const storedText = '' + storedValue
	if (storedText == '') {
		return false
	}

	uni.removeStorageSync(transactionListRefreshStorageKey)
	return true
}

async function loadTransactions() {
	if (isLoading.value) {
		return
	}

	if (supplierId.value == '') {
		transactions.value = []
		totalCount.value = 0
		totalPages.value = 1
		errorMessage.value = '缺少供应商ID'
		return
	}

	isLoading.value = true
	errorMessage.value = ''

	try {
		const response = await getTransactionList({
			search: keyword.value == '' ? null : keyword.value,
			page: currentPage.value,
			page_size: pageSize.value,
			transaction_type: transactionTypeFilterValue(),
			supplier: null,
			supplier_id: supplierId.value,
			date_from: filterDateFrom.value == '' ? null : filterDateFrom.value,
			start_date: null,
			date_to: filterDateTo.value == '' ? null : filterDateTo.value,
			end_date: null,
			amount_min: filterAmountMin.value == '' ? null : filterAmountMin.value,
			amount_max: filterAmountMax.value == '' ? null : filterAmountMax.value,
			ordering: selectedOrdering.value == '' ? null : selectedOrdering.value,
			sort_by: null,
		})
		applyTransactionResponse(response)
	} catch (error) {
		transactions.value = []
		currentPage.value = 1
		totalPages.value = 1
		totalCount.value = 0
		summary.value = null
		errorMessage.value = parseErrorMessage(error)
	} finally {
		isLoading.value = false
	}
}

function getStatisticsText(key: string, fallback: string): string {
	if (statistics.value == null) {
		return fallback
	}

	const rawValue = statistics.value.data[key]
	if (rawValue == null) {
		return fallback
	}

	const text = '' + rawValue
	if (text == '') {
		return fallback
	}

	return text
}

async function loadTransactionStatistics() {
	if (supplierId.value == '') {
		statistics.value = null
		return
	}

	try {
		statistics.value = await getTransactionStatistics({
			search: keyword.value == '' ? null : keyword.value,
			page: currentPage.value,
			page_size: pageSize.value,
			transaction_type: transactionTypeFilterValue(),
			supplier: supplierId.value,
			supplier_id: supplierId.value,
			date_from: filterDateFrom.value == '' ? null : filterDateFrom.value,
			start_date: null,
			date_to: filterDateTo.value == '' ? null : filterDateTo.value,
			end_date: null,
			amount_min: filterAmountMin.value == '' ? null : filterAmountMin.value,
			amount_max: filterAmountMax.value == '' ? null : filterAmountMax.value,
			ordering: selectedOrdering.value == '' ? null : selectedOrdering.value,
			sort_by: null,
		})
	} catch (error) {
		statistics.value = null
	}
}

function refreshTransactionData() {
	loadTransactions()
	loadTransactionStatistics()
}

function getDisplayText(value: string | null): string {
	if (value == null || value == '') {
		return '-'
	}
	return value
}

function transactionThumbnailUrl(mediaFile: TransactionMediaFile): string {
	if (mediaFile.signed_thumbnail_url != '') {
		return mediaFile.signed_thumbnail_url
	}
	if (mediaFile.thumbnail_url != '') {
		return mediaFile.thumbnail_url
	}
	if (mediaFile.signed_url != '') {
		return mediaFile.signed_url
	}
	return mediaFile.file_url
}

function transactionFullUrl(mediaFile: TransactionMediaFile): string {
	if (mediaFile.signed_url != '') {
		return mediaFile.signed_url
	}
	if (mediaFile.file_url != '') {
		return mediaFile.file_url
	}
	return transactionThumbnailUrl(mediaFile)
}

function buildImages(item: TransactionItem): string[] {
	const result: string[] = []
	for (let index = 0; index < item.media_files.length; index += 1) {
		const mediaFile = item.media_files[index]
		const imageUrl = transactionThumbnailUrl(mediaFile)
		if (imageUrl != '') {
			result.push(imageUrl)
		}
	}
	return result
}

function buildPreviewImages(item: TransactionItem): string[] {
	const result: string[] = []
	for (let index = 0; index < item.media_files.length; index += 1) {
		const imageUrl = transactionFullUrl(item.media_files[index])
		if (imageUrl != '') {
			result.push(imageUrl)
		}
	}
	return result
}

function buildMediaIds(item: TransactionItem): string[] {
	const result: string[] = []
	for (let index = 0; index < item.media_files.length; index += 1) {
		const mediaId = item.media_files[index].id
		if (mediaId != '') {
			result.push(mediaId)
		}
	}
	return result
}

function formatDateText(value: string): string {
	if (value == '') {
		return '-'
	}
	if (value.length >= 16) {
		return value.substring(0, 16)
	}
	return value
}

function transactionToListItem(item: TransactionItem): UTSJSONObject {
	const images = buildImages(item)
	const previewImages = buildPreviewImages(item)
	const title = item.transaction_number != '' ? item.transaction_number : ('采购记录 #' + item.id.toString())
	const typeText = getDisplayText(item.transaction_type_display)
	const filesText = item.files_count.toString() + ' 个'
	const tags = [typeText, item.files_count > 0 ? '附件 ' + item.files_count.toString() : '无附件'] as string[]
	return {
		id: item.id.toString(),
		title: title,
		transactionDateText: '日期：' + formatDateText(item.transaction_date),
		amountText: '¥ ' + item.amount,
		transactionTypeText: typeText,
		filesText: filesText,
		noteText: getDisplayText(item.note),
		updatedText: formatDateText(item.updated_at),
		cover: images.length > 0 ? images[0] : '',
		images: images,
		previewCover: previewImages.length > 0 ? previewImages[0] : '',
		previewImages: previewImages,
		mediaIds: buildMediaIds(item),
		tags: tags,
		rawId: item.id.toString(),
	} as UTSJSONObject
}

function handleSearchInput(value: string) {
	keyword.value = value
}

function handleSearchConfirm(value: string) {
	keyword.value = value
	currentPage.value = 1
	refreshTransactionData()
}

function handleSearchClear() {
	keyword.value = ''
	currentPage.value = 1
	refreshTransactionData()
}

async function handleFilterOpen() {
	if (filterOptions.value != null || filterOptionsLoading.value) return
	filterOptionsLoading.value = true
	filterOptionsError.value = ''
	try {
		filterOptions.value = await getTransactionFilterOptions()
	} catch (error) {
		filterOptionsError.value = parseErrorMessage(error)
	} finally {
		filterOptionsLoading.value = false
	}
}

function handleFilterReset() {
	selectedFilters.value = [] as TransactionSelectedFilter[]
	filterDateFrom.value = ''
	filterDateTo.value = ''
	filterAmountMin.value = ''
	filterAmountMax.value = ''
	selectedOrdering.value = '-transaction_date'
	keyword.value = ''
	currentPage.value = 1
	closeFilterDrawer()
	refreshTransactionData()
}

function applySelectedFilters() {
	currentPage.value = 1
	closeFilterDrawer()
	refreshTransactionData()
}

function handlePageChange(payload: UTSJSONObject) {
	const pageValue = payload['page']
	if (pageValue == null) {
		return
	}

	const nextPageText = '' + pageValue
	const nextPage = parseInt(nextPageText)
	if (isNaN(nextPage) || nextPage <= 0 || nextPage == currentPage.value) {
		return
	}

	currentPage.value = nextPage
	refreshTransactionData()
}

function handleItemClick(payload: UTSJSONObject) {
	const titleText = stringValue(payload['title'], '采购记录')
	const itemValue = payload['item']
	if (itemValue == null) {
		uni.showToast({ title: titleText, icon: 'none' })
		return
	}
	const item = itemValue as UTSJSONObject
	const detailText = [
		'类型：' + stringValue(item['transactionTypeText'], '-'),
		'金额：' + stringValue(item['amountText'], '-'),
		stringValue(item['transactionDateText'], '日期：-'),
		'附件：' + stringValue(item['filesText'], '0 个'),
		'备注：' + stringValue(item['noteText'], '-'),
	].join('\n')
	uni.showModal({
		title: titleText,
		content: detailText,
		showCancel: false,
		confirmText: '知道了',
	})
}

function handleSubtitleClick(payload: UTSJSONObject) {
	const itemValue = payload['item']
	if (itemValue == null) {
		return
	}

	const item = itemValue as UTSJSONObject
	const transactionDateValue = item['transactionDateText']
	const transactionDateText = transactionDateValue == null ? '' : (transactionDateValue as string)
	uni.setClipboardData({
		data: transactionDateText,
		success: () => {
			uni.showToast({
				title: '日期已复制',
				icon: 'success',
			})
		},
	})
}

function handleMetaClick(payload: UTSJSONObject) {
	const itemValue = payload['item']
	if (itemValue == null) {
		return
	}

	const item = itemValue as UTSJSONObject
	const amountValue = item['amountText']
	const amountText = amountValue == null ? '' : (amountValue as string)
	uni.setClipboardData({
		data: amountText,
		success: () => {
			uni.showToast({
				title: '金额已复制',
				icon: 'success',
			})
		},
	})
}

function handleFieldClick(payload: UTSJSONObject) {
	const keyValue = payload['key']
	const itemValue = payload['item']
	if (keyValue == null || itemValue == null) {
		return
	}

	const key = keyValue as string
	const item = itemValue as UTSJSONObject
	const rawValue = item[key]
	if (rawValue == null) {
		return
	}

	uni.setClipboardData({
		data: rawValue as string,
		success: () => {
			uni.showToast({
				title: '内容已复制',
				icon: 'success',
			})
		},
	})
}

function handleCreateTransaction() {
	if (supplierId.value == '') {
		uni.showToast({
			title: '供应商ID缺失',
			icon: 'none',
		})
		return
	}

	uni.navigateTo({
		url: '/pages/transactions/from?supplier_id=' + supplierId.value,
	})
}

async function confirmDeleteTransaction(transactionId: string) {
	try {
		await deleteTransaction(transactionId)
		uni.showToast({
			title: takeLatestResponseMessage('删除成功'),
			icon: 'success',
		})
		markTransactionListRefreshNeeded()
		refreshTransactionData()
	} catch (error) {
		uni.showToast({
			title: parseErrorMessage(error),
			icon: 'none',
		})
	}
}

function handleMenu(payload: UTSJSONObject) {
	const action = payload['action']
	const item = payload['item']
	if (action == null || item == null) {
		return
	}

	const actionObject = action as UTSJSONObject
	const itemObject = item as UTSJSONObject
	const actionKey = actionObject['key']
	if (actionKey == null) {
		return
	}

	const key = actionKey as string
	const transactionIdValue = itemObject['rawId']
	const transactionId = transactionIdValue == null ? '' : (transactionIdValue as string)
	if (transactionId == '') {
		uni.showToast({
			title: '采购记录ID缺失',
			icon: 'none',
		})
		return
	}

	if (key == 'edit') {
		uni.navigateTo({
			url: '/pages/transactions/from?id=' + transactionId + '&supplier_id=' + supplierId.value,
		})
		return
	}

	if (key == 'Detail') {
		uni.showToast({
			title: '当前已在详情页',
			icon: 'none',
		})
		return
	}

	if (key == 'add') {
		uni.navigateTo({
			url: '/pages/transactions/from?supplier_id=' + supplierId.value,
		})
		return
	}

	if (key == 'delete') {
		uni.showModal({
			title: '删除采购记录',
			content: '确定删除这条采购记录吗？',
			success: (res) => {
				if (!res.confirm) {
					return
				}
				confirmDeleteTransaction(transactionId)
			},
		})
	}
}

const listItems = computed((): UTSJSONObject[] => {
	const result: UTSJSONObject[] = []
	for (let index = 0; index < transactions.value.length; index += 1) {
		result.push(transactionToListItem(transactions.value[index]))
	}
	return result
})

const pageTitle = computed((): string => {
	if (supplierName.value != '') return supplierName.value + ' 采购记录'
	return '采购记录'
})

const hasActiveFilter = computed((): boolean => {
	return keyword.value != ''
		|| selectedFilters.value.length > 0
		|| filterDateFrom.value != ''
		|| filterDateTo.value != ''
		|| filterAmountMin.value != ''
		|| filterAmountMax.value != ''
		|| selectedOrdering.value != '-transaction_date'
})

const emptyText = computed((): string => {
	if (supplierId.value == '') {
		return '缺少供应商ID'
	}
	if (hasActiveFilter.value) {
		return '没有匹配的采购记录'
	}
	return '暂无采购记录'
})

const transactionCountText = computed(() : string => {
	return getStatisticsText('purchaseCount', totalCount.value.toString())
})

const purchaseAmountText = computed(() : string => {
	const summaryValue = summary.value == null || summary.value.purchase_amount == '' ? '0.00' : summary.value.purchase_amount
	return getStatisticsText('purchaseAmount', summaryValue)
})

const arrearsAmountText = computed(() : string => {
	const summaryValue = summary.value == null || summary.value.arrears_amount == '' ? '0.00' : summary.value.arrears_amount
	return getStatisticsText('actualDebt', summaryValue)
})

const summaryItems = computed((): UTSJSONObject[] => {
	return [
		{ key: 'total-count', label: '记录数', value: totalCount.value.toString() } as UTSJSONObject,
		{ key: 'purchase-count', label: '采购次数', value: transactionCountText.value } as UTSJSONObject,
		{ key: 'purchase-amount', label: '采购金额', value: '¥ ' + purchaseAmountText.value } as UTSJSONObject,
		{ key: 'arrears-amount', label: '欠款金额', value: '¥ ' + arrearsAmountText.value } as UTSJSONObject,
		{ key: 'payment-amount', label: '还款金额', value: '¥ ' + getStatisticsText('paymentAmount', '0.00') } as UTSJSONObject,
	]
})

const filterPanelStyle = computed((): string => {
	return 'height:' + filterPanelHeight.value.toString() + 'px;'
})

const filterContentScrollStyle = computed((): string => {
	return 'height:' + filterContentHeight.value.toString() + 'px;'
})

const filterDefinitions = computed((): TransactionFilterDefinition[] => {
	if (filterOptions.value == null) return defaultFilterDefinitions.value
	if (filterOptions.value!.filters.length == 0) return defaultFilterDefinitions.value
	return filterOptions.value!.filters
})

onLoad((event : OnLoadOptions) => {
	updateFilterPanelLayout()
	filterDateFrom.value = ''
	filterDateTo.value = ''
	let supplierIdValue = event['supplier_id']
	if (supplierIdValue == null || supplierIdValue == '') supplierIdValue = event['supplierId']
	if (supplierIdValue == null || supplierIdValue == '') supplierIdValue = event['id']
	supplierId.value = supplierIdValue == null ? '' : (supplierIdValue as string)
	const supplierNameValue = event['supplier_name']
	if (supplierNameValue == null) {
		supplierName.value = ''
	} else {
		const decodedSupplierName = UTSAndroid.consoleDebugError(decodeURIComponent(supplierNameValue as string), " at pages/transactions/index.uvue:921")
		supplierName.value = decodedSupplierName == null ? '' : decodedSupplierName
	}
	refreshTransactionData()
})

onShow(() => {
	updateFilterPanelLayout()
	if (!consumeTransactionListRefreshNeeded()) {
		return
	}
	refreshTransactionData()
})

return (): any | null => {

const _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter",_easycom_lili_universal_filter)
const _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList",_easycom_lili_UniversalList)

  return _cE("view", _uM({ class: "page" }), [
    _cV(_component_lili_universal_filter, _uM({
      title: pageTitle.value,
      searchPlaceholder: "输入单号、备注搜索采购记录",
      searchValue: unref(keyword),
      filterVisible: unref(filterVisible),
      showBack: true,
      showSearch: true,
      showFilter: true,
      showHome: true,
      filterActive: hasActiveFilter.value,
      homePath: "/pages/suppliers/index",
      onSearchInput: handleSearchInput,
      onSearchConfirm: handleSearchConfirm,
      onSearchClear: handleSearchClear,
      "onUpdate:filterVisible": handleFilterVisibleChange,
      onFilterOpen: handleFilterOpen
    }), _uM({
      "filter-panel": withSlotCtx((): any[] => [
        _cE("view", _uM({
          class: "transaction-filter-panel",
          style: _nS(filterPanelStyle.value)
        }), [
          _cE("scroll-view", _uM({
            "scroll-y": "true",
            class: "transaction-filter-content-scroll",
            style: _nS(filterContentScrollStyle.value)
          }), [
            _cE("view", _uM({ class: "transaction-filter-scroll-inner" }), [
              isTrue(unref(filterOptionsLoading))
                ? _cE("view", _uM({
                    key: 0,
                    class: "transaction-filter-state"
                  }), [
                    _cE("text", _uM({ class: "transaction-filter-state-text" }), "筛选选项加载中...")
                  ])
                : unref(filterOptionsError) != ''
                  ? _cE("view", _uM({
                      key: 1,
                      class: "transaction-filter-state"
                    }), [
                      _cE("text", _uM({ class: "transaction-filter-state-text" }), _tD(unref(filterOptionsError)), 1 /* TEXT */)
                    ])
                  : _cE("view", _uM({
                      key: 2,
                      class: "transaction-filter-groups"
                    }), [
                      _cE(Fragment, null, RenderHelpers.renderList(filterDefinitions.value, (filter, __key, __index, _cached): any => {
                        return _cE("view", _uM({
                          key: filter.key,
                          class: "transaction-filter-group"
                        }), [
                          _cE("text", _uM({ class: "transaction-filter-group-title" }), _tD(filter.label), 1 /* TEXT */),
                          _cE("view", _uM({ class: "transaction-filter-options" }), [
                            _cE(Fragment, null, RenderHelpers.renderList(filter.options, (option, __key, __index, _cached): any => {
                              return _cE("view", _uM({
                                key: filter.key + '-' + option.value,
                                class: _nC(isFilterOptionSelected(filter.param, option.value) ? 'transaction-filter-option transaction-filter-option-active' : 'transaction-filter-option'),
                                onClick: () => {toggleFilterOption(filter.param, option.value)}
                              }), [
                                _cE("text", _uM({
                                  class: _nC(isFilterOptionSelected(filter.param, option.value) ? 'transaction-filter-option-text transaction-filter-option-text-active' : 'transaction-filter-option-text')
                                }), _tD(option.label), 3 /* TEXT, CLASS */)
                              ], 10 /* CLASS, PROPS */, ["onClick"])
                            }), 128 /* KEYED_FRAGMENT */)
                          ])
                        ])
                      }), 128 /* KEYED_FRAGMENT */),
                      _cE("view", _uM({ class: "transaction-filter-group" }), [
                        _cE("text", _uM({ class: "transaction-filter-group-title" }), "日期范围"),
                        _cE("view", _uM({ class: "transaction-filter-input-row" }), [
                          _cE("input", _uM({
                            class: "transaction-filter-input",
                            value: unref(filterDateFrom),
                            placeholder: "开始日期 YYYY-MM-DD",
                            onInput: handleDateFromInput
                          }), null, 40 /* PROPS, NEED_HYDRATION */, ["value"]),
                          _cE("input", _uM({
                            class: "transaction-filter-input",
                            value: unref(filterDateTo),
                            placeholder: "结束日期 YYYY-MM-DD",
                            onInput: handleDateToInput
                          }), null, 40 /* PROPS, NEED_HYDRATION */, ["value"])
                        ])
                      ]),
                      _cE("view", _uM({ class: "transaction-filter-group" }), [
                        _cE("text", _uM({ class: "transaction-filter-group-title" }), "金额范围"),
                        _cE("view", _uM({ class: "transaction-filter-input-row" }), [
                          _cE("input", _uM({
                            class: "transaction-filter-input",
                            type: "digit",
                            value: unref(filterAmountMin),
                            placeholder: "最低金额",
                            onInput: handleAmountMinInput
                          }), null, 40 /* PROPS, NEED_HYDRATION */, ["value"]),
                          _cE("input", _uM({
                            class: "transaction-filter-input",
                            type: "digit",
                            value: unref(filterAmountMax),
                            placeholder: "最高金额",
                            onInput: handleAmountMaxInput
                          }), null, 40 /* PROPS, NEED_HYDRATION */, ["value"])
                        ])
                      ]),
                      _cE("view", _uM({ class: "transaction-filter-group" }), [
                        _cE("text", _uM({ class: "transaction-filter-group-title" }), "排序方式"),
                        _cE("view", _uM({ class: "transaction-filter-options" }), [
                          _cE(Fragment, null, RenderHelpers.renderList(unref(sortOptions), (option, __key, __index, _cached): any => {
                            return _cE("view", _uM({
                              key: optionKey(option),
                              class: _nC(unref(selectedOrdering) == optionValue(option) ? 'transaction-filter-option transaction-filter-option-active' : 'transaction-filter-option'),
                              onClick: () => {selectOrdering(optionValue(option))}
                            }), [
                              _cE("text", _uM({
                                class: _nC(unref(selectedOrdering) == optionValue(option) ? 'transaction-filter-option-text transaction-filter-option-text-active' : 'transaction-filter-option-text')
                              }), _tD(optionLabel(option)), 3 /* TEXT, CLASS */)
                            ], 10 /* CLASS, PROPS */, ["onClick"])
                          }), 128 /* KEYED_FRAGMENT */)
                        ])
                      ])
                    ])
            ])
          ], 4 /* STYLE */),
          _cE("view", _uM({ class: "transaction-filter-actions" }), [
            _cE("view", _uM({
              class: "transaction-filter-btn transaction-filter-btn-light",
              onClick: handleFilterReset
            }), [
              _cE("text", _uM({ class: "transaction-filter-btn-light-text" }), "重置")
            ]),
            _cE("view", _uM({
              class: "transaction-filter-btn transaction-filter-btn-primary",
              onClick: applySelectedFilters
            }), [
              _cE("text", _uM({ class: "transaction-filter-btn-primary-text" }), "应用")
            ])
          ])
        ], 4 /* STYLE */)
      ]),
      _: 1 /* STABLE */
    }), 8 /* PROPS */, ["title", "searchValue", "filterVisible", "filterActive"]),
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
              _cE("button", _uM({
                class: "retry-btn",
                onClick: refreshTransactionData
              }), [
                _cE("text", _uM({ class: "retry-btn-text" }), "重新加载")
              ])
            ])
          : _cC("v-if", true),
        _cV(_component_lili_UniversalList, _uM({
          items: listItems.value,
          keyField: "id",
          titleField: "title",
          subtitleField: "transactionDateText",
          metaField: "amountText",
          imageField: "cover",
          imageListField: "images",
          tagField: "tags",
          tagColorMap: unref(tagColorMap),
          fields: unref(fieldConfig),
          loading: unref(isLoading),
          loadingText: "正在加载采购记录",
          keepContentOnLoading: true,
          inlineLoadingText: "采购记录刷新中...",
          emptyText: emptyText.value,
          emptyIcon: "◎",
          showMenu: true,
          menuActions: unref(menuActions),
          showChevron: false,
          showPagination: true,
          currentPage: unref(currentPage),
          totalPages: unref(totalPages),
          totalCount: unref(totalCount),
          summaryTitle: "采购统计",
          summaryItems: summaryItems.value,
          summaryCollapsedByDefault: false,
          showFloatingAdd: true,
          floatingAddText: "新增",
          onItemClick: handleItemClick,
          onSubtitleClick: handleSubtitleClick,
          onMetaClick: handleMetaClick,
          onFieldClick: handleFieldClick,
          onMenu: handleMenu,
          onPageChange: handlePageChange,
          onFloatingAdd: handleCreateTransaction
        }), null, 8 /* PROPS */, ["items", "tagColorMap", "fields", "loading", "emptyText", "menuActions", "currentPage", "totalPages", "totalCount", "summaryItems"])
      ])
    ], 4 /* STYLE */)
  ])
}
}

})
export default __sfc__
const GenPagesTransactionsIndexStyles = [_uM([["page", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["position", "relative"], ["backgroundColor", "#EEF2F7"]]))], ["page-scroll", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"]]))], ["page-content", _pS(_uM([["paddingLeft", 6], ["paddingRight", 6], ["paddingTop", 6], ["paddingBottom", 88]]))], ["error-card", _pS(_uM([["marginBottom", 12], ["paddingTop", 18], ["paddingRight", 16], ["paddingBottom", 18], ["paddingLeft", 16], ["borderTopLeftRadius", 16], ["borderTopRightRadius", 16], ["borderBottomRightRadius", 16], ["borderBottomLeftRadius", 16], ["backgroundColor", "#FFFFFF"]]))], ["error-title", _pS(_uM([["fontSize", 16], ["fontWeight", "600"], ["color", "#111827"]]))], ["error-desc", _pS(_uM([["marginTop", 8], ["fontSize", 14], ["lineHeight", "1.5em"], ["color", "#6B7280"]]))], ["retry-btn", _pS(_uM([["marginTop", 14], ["height", 40], ["borderTopLeftRadius", 10], ["borderTopRightRadius", 10], ["borderBottomRightRadius", 10], ["borderBottomLeftRadius", 10], ["backgroundColor", "#111827"], ["borderTopWidth", 0], ["borderRightWidth", 0], ["borderBottomWidth", 0], ["borderLeftWidth", 0]]))], ["retry-btn-text", _pS(_uM([["fontSize", 14], ["fontWeight", "600"], ["color", "#FFFFFF"]]))], ["transaction-filter-panel", _pS(_uM([["position", "relative"], ["paddingTop", 2]]))], ["transaction-filter-content-scroll", _pS(_uM([["paddingRight", 2]]))], ["transaction-filter-scroll-inner", _pS(_uM([["paddingBottom", 58]]))], ["transaction-filter-state", _pS(_uM([["height", 112], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["backgroundColor", "#F8FAFC"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["transaction-filter-state-text", _pS(_uM([["fontSize", 12], ["lineHeight", "17px"], ["color", "#64748B"]]))], ["transaction-filter-groups", _pS(_uM([["marginBottom", 6]]))], ["transaction-filter-group", _pS(_uM([["paddingLeft", 10], ["paddingRight", 10], ["paddingTop", 10], ["paddingBottom", 10], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["backgroundColor", "#FFFFFF"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E5EAF1"], ["borderRightColor", "#E5EAF1"], ["borderBottomColor", "#E5EAF1"], ["borderLeftColor", "#E5EAF1"], ["marginBottom", 6]]))], ["transaction-filter-group-title", _pS(_uM([["fontSize", 13], ["lineHeight", "18px"], ["color", "#0F172A"], ["fontWeight", "bold"]]))], ["transaction-filter-options", _pS(_uM([["flexDirection", "row"], ["flexWrap", "wrap"], ["marginTop", 8]]))], ["transaction-filter-option", _pS(_uM([["minWidth", 48], ["height", 30], ["paddingLeft", 10], ["paddingRight", 10], ["borderTopLeftRadius", 15], ["borderTopRightRadius", 15], ["borderBottomRightRadius", 15], ["borderBottomLeftRadius", 15], ["backgroundColor", "#F8FAFC"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E2E8F0"], ["borderRightColor", "#E2E8F0"], ["borderBottomColor", "#E2E8F0"], ["borderLeftColor", "#E2E8F0"], ["alignItems", "center"], ["justifyContent", "center"], ["marginRight", 6], ["marginBottom", 6]]))], ["transaction-filter-option-active", _pS(_uM([["backgroundColor", "#0F172A"], ["borderTopColor", "#0F172A"], ["borderRightColor", "#0F172A"], ["borderBottomColor", "#0F172A"], ["borderLeftColor", "#0F172A"]]))], ["transaction-filter-option-text", _pS(_uM([["fontSize", 12], ["lineHeight", "17px"], ["color", "#334155"]]))], ["transaction-filter-option-text-active", _pS(_uM([["color", "#FFFFFF"]]))], ["transaction-filter-input-row", _pS(_uM([["flexDirection", "row"], ["marginTop", 8]]))], ["transaction-filter-input", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["height", 36], ["borderTopLeftRadius", 18], ["borderTopRightRadius", 18], ["borderBottomRightRadius", 18], ["borderBottomLeftRadius", 18], ["backgroundColor", "#F8FAFC"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E2E8F0"], ["borderRightColor", "#E2E8F0"], ["borderBottomColor", "#E2E8F0"], ["borderLeftColor", "#E2E8F0"], ["paddingLeft", 12], ["paddingRight", 12], ["fontSize", 12], ["color", "#0F172A"], ["marginRight", 6]]))], ["transaction-filter-actions", _pS(_uM([["position", "absolute"], ["left", 0], ["right", 0], ["bottom", 0], ["flexDirection", "row"], ["paddingTop", 6], ["paddingLeft", 2], ["paddingRight", 2], ["paddingBottom", 4], ["borderTopWidth", 1], ["borderTopStyle", "solid"], ["borderTopColor", "rgba(226,232,240,0.78)"], ["backgroundColor", "#FFFFFF"]]))], ["transaction-filter-btn", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["height", 38], ["borderTopLeftRadius", 11], ["borderTopRightRadius", 11], ["borderBottomRightRadius", 11], ["borderBottomLeftRadius", 11], ["alignItems", "center"], ["justifyContent", "center"]]))], ["transaction-filter-btn-light", _pS(_uM([["backgroundColor", "#F3F6FA"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E2E8F0"], ["borderRightColor", "#E2E8F0"], ["borderBottomColor", "#E2E8F0"], ["borderLeftColor", "#E2E8F0"], ["marginRight", 8]]))], ["transaction-filter-btn-primary", _pS(_uM([["backgroundColor", "#0F172A"]]))], ["transaction-filter-btn-light-text", _pS(_uM([["fontSize", 13], ["lineHeight", "18px"], ["color", "#475569"]]))], ["transaction-filter-btn-primary-text", _pS(_uM([["fontSize", 13], ["lineHeight", "18px"], ["color", "#FFFFFF"]]))]])]
