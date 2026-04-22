import _easycom_lili_universal_filter from '@/uni_modules/lili-universal-filter/components/lili-universal-filter/lili-universal-filter.uvue'
import _easycom_lili_UniversalList from '@/uni_modules/lili-UniversalList/components/lili-UniversalList/lili-UniversalList.uvue'
import { computed } from 'vue'
import { deleteTransaction, getTransactionList, getTransactionStatistics, TransactionItem, TransactionListResponse, TransactionSummary, TransactionStatisticsResponse } from '@/pkg/api/modules/transactions.uts'


const __sfc__ = defineComponent({
  __name: 'index',
  setup(__props) {
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

const transactionListRefreshStorageKey = 'refresh:pages:transactions:index'

const supplierId = ref('')
const keyword = ref('')
const transactions = ref<TransactionItem[]>([])
const isLoading = ref(false)
const errorMessage = ref('')
const currentPage = ref(1)
const totalPages = ref(1)
const totalCount = ref(0)
const pageSize = ref(20)
const summary = ref<TransactionSummary | null>(null)
const statistics = ref<TransactionStatisticsResponse | null>(null)

const fieldConfig = ref<UTSJSONObject[]>([
	{ key: 'transactionTypeText', label: '类型:' } as UTSJSONObject,
	{ key: 'noteText', label: '备注:' } as UTSJSONObject,
])

const menuActions = ref<UTSJSONObject[]>([
	{ key: 'edit', text: '编辑' } as UTSJSONObject,
	{ key: 'delete', text: '删除' } as UTSJSONObject,
])

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
		const errorText = JSON.stringify(error)
		if (errorText != null && errorText != '') {
			const parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/transactions/index.uvue:109")
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
	return message
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
			transaction_type: null,
			supplier: null,
			supplier_id: supplierId.value,
			date_from: null,
			start_date: null,
			date_to: null,
			end_date: null,
			amount_min: null,
			amount_max: null,
			ordering: null,
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
			transaction_type: null,
			supplier: supplierId.value,
			supplier_id: supplierId.value,
			date_from: null,
			start_date: null,
			date_to: null,
			end_date: null,
			amount_min: null,
			amount_max: null,
			ordering: null,
			sort_by: null,
		})
	} catch (error) {
		statistics.value = null
	}
}

function getDisplayText(value: string | null): string {
	if (value == null || value == '') {
		return '-'
	}
	return value
}

function buildImages(item: TransactionItem): string[] {
	const result: string[] = []
	for (let index = 0; index < item.media_files.length; index += 1) {
		const mediaFile = item.media_files[index]
		if (mediaFile.signed_thumbnail_url != '') {
			result.push(mediaFile.signed_thumbnail_url)
			continue
		}
		if (mediaFile.thumbnail_url != '') {
			result.push(mediaFile.thumbnail_url)
			continue
		}
		if (mediaFile.signed_url != '') {
			result.push(mediaFile.signed_url)
			continue
		}
		if (mediaFile.file_url != '') {
			result.push(mediaFile.file_url)
		}
	}
	return result
}

function getTransactionImage(item: TransactionItem): string {
	if (item.media_files.length == 0) {
		return ''
	}

	const mediaFile = item.media_files[0]
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
	const cover = getTransactionImage(item)
	const images = buildImages(item)
	const title = item.transaction_number != '' ? item.transaction_number : ('采购记录 #' + item.id.toString())
	return {
		id: item.id.toString(),
		title: title,
		transactionDateText: '日期：' + formatDateText(item.transaction_date),
		amountText: '金额：' + item.amount,
		transactionTypeText: getDisplayText(item.transaction_type_display),
		filesText: item.files_count.toString() + ' 个',
		noteText: getDisplayText(item.note),
		updatedText: formatDateText(item.updated_at),
		cover: cover,
		images: images,
		rawId: item.id.toString(),
	} as UTSJSONObject
}

function handleSearchInput(value: string) {
	keyword.value = value
}

function handleSearchConfirm(value: string) {
	keyword.value = value
	currentPage.value = 1
	loadTransactions()
	loadTransactionStatistics()
}

function handleSearchClear() {
	keyword.value = ''
	currentPage.value = 1
	loadTransactions()
	loadTransactionStatistics()
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
	loadTransactions()
	loadTransactionStatistics()
}

function handleItemClick(payload: UTSJSONObject) {
	const titleValue = payload['title']
	const titleText = titleValue == null ? '采购记录' : (titleValue as string)
	uni.showToast({
		title: titleText,
		icon: 'none',
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
			title: '删除成功',
			icon: 'success',
		})
		markTransactionListRefreshNeeded()
		loadTransactions()
		loadTransactionStatistics()
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

const emptyText = computed((): string => {
	if (supplierId.value == '') {
		return '缺少供应商ID'
	}
	if (keyword.value != '') {
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
		{ key: 'purchase-count', label: '采购次数', value: transactionCountText.value } as UTSJSONObject,
		{ key: 'purchase-amount', label: '采购总数', value: purchaseAmountText.value } as UTSJSONObject,
		{ key: 'arrears-amount', label: '欠款总数', value: arrearsAmountText.value } as UTSJSONObject,
	]
})

onLoad((event : OnLoadOptions) => {
	const supplierIdValue = event['supplier_id']
	supplierId.value = supplierIdValue == null ? '' : (supplierIdValue as string)
	loadTransactions()
	loadTransactionStatistics()
})

onShow(() => {
	if (!consumeTransactionListRefreshNeeded()) {
		return
	}
	loadTransactions()
	loadTransactionStatistics()
})

return (): any | null => {

const _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter",_easycom_lili_universal_filter)
const _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList",_easycom_lili_UniversalList)

  return _cE("view", _uM({ class: "page" }), [
    _cV(_component_lili_universal_filter, _uM({
      title: "采购详情",
      searchPlaceholder: "输入单号、备注搜索采购记录",
      searchValue: unref(keyword),
      showBack: true,
      showSearch: true,
      showFilter: false,
      showHome: true,
      homePath: "/pages/suppliers/index",
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
              _cE("button", _uM({
                class: "retry-btn",
                onClick: loadTransactions
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
          fields: unref(fieldConfig),
          loading: unref(isLoading),
          loadingText: "正在加载采购记录",
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
          summaryCollapsedByDefault: true,
          onItemClick: handleItemClick,
          onSubtitleClick: handleSubtitleClick,
          onMetaClick: handleMetaClick,
          onFieldClick: handleFieldClick,
          onMenu: handleMenu,
          onPageChange: handlePageChange,
          onFloatingAdd: handleCreateTransaction
        }), null, 8 /* PROPS */, ["items", "fields", "loading", "emptyText", "menuActions", "currentPage", "totalPages", "totalCount", "summaryItems"])
      ])
    ], 4 /* STYLE */)
  ])
}
}

})
export default __sfc__
const GenPagesTransactionsIndexStyles = [_uM([["page", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["position", "relative"], ["backgroundColor", "#EEF2F7"]]))], ["page-scroll", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"]]))], ["page-content", _pS(_uM([["paddingLeft", 6], ["paddingRight", 6], ["paddingTop", 6], ["paddingBottom", 88]]))], ["error-card", _pS(_uM([["marginBottom", 12], ["paddingTop", 18], ["paddingRight", 16], ["paddingBottom", 18], ["paddingLeft", 16], ["borderTopLeftRadius", 16], ["borderTopRightRadius", 16], ["borderBottomRightRadius", 16], ["borderBottomLeftRadius", 16], ["backgroundColor", "#FFFFFF"]]))], ["error-title", _pS(_uM([["fontSize", 16], ["fontWeight", "600"], ["color", "#111827"]]))], ["error-desc", _pS(_uM([["marginTop", 8], ["fontSize", 14], ["lineHeight", "1.5em"], ["color", "#6B7280"]]))], ["retry-btn", _pS(_uM([["marginTop", 14], ["height", 40], ["borderTopLeftRadius", 10], ["borderTopRightRadius", 10], ["borderBottomRightRadius", 10], ["borderBottomLeftRadius", 10], ["backgroundColor", "#111827"], ["borderTopWidth", 0], ["borderRightWidth", 0], ["borderBottomWidth", 0], ["borderLeftWidth", 0]]))], ["retry-btn-text", _pS(_uM([["fontSize", 14], ["fontWeight", "600"], ["color", "#FFFFFF"]]))]])]
