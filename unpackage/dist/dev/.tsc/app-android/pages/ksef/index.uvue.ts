import _easycom_lili_universal_filter from '@/uni_modules/lili-universal-filter/components/lili-universal-filter/lili-universal-filter.uvue'
import _easycom_lili_UniversalList from '@/uni_modules/lili-UniversalList/components/lili-UniversalList/lili-UniversalList.uvue'
import { computed } from 'vue'
import { takeLatestResponseMessage } from '@/pkg/api/index.uts'
import { downloadKsefInvoiceXml, getKsefAutoSyncStatus, getKsefInvoiceFilterOptions, getKsefInvoiceList, KsefAutoSyncStatus, KsefFilterDefinition, KsefFilterOption, KsefFilterOptionsResponse, KsefInvoiceItem, KsefInvoiceListResponse } from '@/pkg/api/modules/ksef'
import { showErrorToast } from '@/pkg/util/toast.uts'

type KsefSelectedFilter = { __$originalPosition?: UTSSourceMapPosition<"KsefSelectedFilter", "pages/ksef/index.uvue", 120, 6>;
	param: string
	value: string
}


const __sfc__ = defineComponent({
  __name: 'index',
  setup(__props) {
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

const refreshStorageKey = 'refresh:pages:ksef:index'
const keyword = ref('')
const filterVisible = ref(false)
const isLoading = ref(false)
const errorMessage = ref('')
const invoices = ref<KsefInvoiceItem[]>([])
const status = ref<KsefAutoSyncStatus | null>(null)
const supplierFilterId = ref('')
const supplierFilterName = ref('')
const currentPage = ref(1)
const totalPages = ref(1)
const totalCount = ref(0)
const pageSize = ref(20)
const filterOptionsLoading = ref(false)
const filterOptionsError = ref('')
const filterOptions = ref<KsefFilterOptionsResponse | null>(null)
const selectedFilters = ref<KsefSelectedFilter[]>([])

const fieldConfig = ref<UTSJSONObject[]>([
	{ key: 'supplier_name', label: '供应商' } as UTSJSONObject,
	{ key: 'payment_due_text', label: '到期日' } as UTSJSONObject,
	{ key: 'payment_review_text', label: '审核' } as UTSJSONObject,
	{ key: 'bank_account_text', label: '银行账号' } as UTSJSONObject,
])

const menuActions = ref<UTSJSONObject[]>([
	{ key: 'view_detail', text: '付款详情' } as UTSJSONObject,
	{ key: 'download_xml', text: '同步详情' } as UTSJSONObject,
	{ key: 'copy_account', text: '复制账号' } as UTSJSONObject,
	{ key: 'copy_ksef', text: '复制KSeF号' } as UTSJSONObject,
])

const defaultFilterDefinitions = ref<KsefFilterDefinition[]>([
	{
		key: 'sync_status',
		param: 'sync_status',
		label: '同步状态',
		control: 'select',
		aliases: [] as string[],
		multiple: false,
		options: [
			{ value: 'METADATA_ONLY', label: '待详情' } as KsefFilterOption,
			{ value: 'XML_DOWNLOADED', label: '已完成' } as KsefFilterOption,
			{ value: 'SYNC_ERROR', label: '异常' } as KsefFilterOption,
		],
	} as KsefFilterDefinition,
	{
		key: 'is_paid',
		param: 'is_paid',
		label: '付款状态',
		control: 'boolean',
		aliases: [] as string[],
		multiple: false,
		options: [
			{ value: 'false', label: '未付款' } as KsefFilterOption,
			{ value: 'true', label: '已付款' } as KsefFilterOption,
		],
	} as KsefFilterDefinition,
	{
		key: 'payment_review_status',
		param: 'payment_review_status',
		label: '支付审核',
		control: 'select',
		aliases: [] as string[],
		multiple: false,
		options: [
			{ value: 'PENDING', label: '待审核' } as KsefFilterOption,
			{ value: 'PAYABLE', label: '可支付' } as KsefFilterOption,
			{ value: 'NOT_PAYABLE', label: '不支付' } as KsefFilterOption,
		],
	} as KsefFilterDefinition,
	{
		key: 'has_xml',
		param: 'has_xml',
		label: 'XML 状态',
		control: 'boolean',
		aliases: [] as string[],
		multiple: false,
		options: [
			{ value: 'false', label: '未下载' } as KsefFilterOption,
			{ value: 'true', label: '已下载' } as KsefFilterOption,
		],
	} as KsefFilterDefinition,
	{
		key: 'ordering',
		param: 'ordering',
		label: '排序方式',
		control: 'select',
		aliases: [] as string[],
		multiple: false,
		options: [
			{ value: 'payment_due_date', label: '到期日最近' } as KsefFilterOption,
			{ value: '-payment_due_date', label: '到期日最远' } as KsefFilterOption,
			{ value: '-issue_date', label: '开票日期最新' } as KsefFilterOption,
			{ value: 'issue_date', label: '开票日期最早' } as KsefFilterOption,
			{ value: '-gross_amount', label: '金额最高' } as KsefFilterOption,
			{ value: 'gross_amount', label: '金额最低' } as KsefFilterOption,
			{ value: '-created_at', label: '最近创建' } as KsefFilterOption,
		],
	} as KsefFilterDefinition,
])

function parseErrorMessage(error: any, fallback: string = '操作失败'): string {
	let message = fallback
	if (error != null) {
		const text = JSON.stringify(error)
		if (text != null && text != '') {
			message = text
		}
	}
	return message
}

function applyListResponse(response: KsefInvoiceListResponse) {
	invoices.value = response.results
	currentPage.value = response.current_page
	totalPages.value = response.total_pages
	totalCount.value = response.total_count
	pageSize.value = response.page_size
}

function setSelectedFilterValue(param: string, value: string) {
	const nextFilters: KsefSelectedFilter[] = []
	let updated = false
	for (let index = 0; index < selectedFilters.value.length; index += 1) {
		const filter = selectedFilters.value[index]
		if (filter.param == param) {
			if (value != '') {
				nextFilters.push({ param: param, value: value } as KsefSelectedFilter)
			}
			updated = true
			continue
		}
		nextFilters.push(filter)
	}
	if (!updated && value != '') {
		nextFilters.push({ param: param, value: value } as KsefSelectedFilter)
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

function filterValueOrNull(param: string): string | null {
	const value = selectedFilterValue(param)
	return value == '' ? null : value
}

function isFilterOptionSelected(param: string, value: string): boolean {
	return selectedFilterValue(param) == value
}

function clearFilterOption(param: string) {
	setSelectedFilterValue(param, '')
}

function toggleFilterOption(param: string, value: string) {
	const currentValue = selectedFilterValue(param)
	setSelectedFilterValue(param, currentValue == value ? '' : value)
}

async function loadFilterOptions() {
	if (filterOptionsLoading.value) return
	filterOptionsLoading.value = true
	filterOptionsError.value = ''
	try {
		filterOptions.value = await getKsefInvoiceFilterOptions()
	} catch (error) {
		filterOptions.value = null
		filterOptionsError.value = parseErrorMessage(error, '筛选选项加载失败')
	} finally {
		filterOptionsLoading.value = false
	}
}

async function loadInvoices() {
	if (isLoading.value) {
		return
	}
	isLoading.value = true
	errorMessage.value = ''
	try {
		const response = await getKsefInvoiceList({
			search: keyword.value == '' ? null : keyword.value,
			page: currentPage.value,
			page_size: pageSize.value,
			supplier: supplierFilterId.value == '' ? null : supplierFilterId.value,
			supplier_id: supplierFilterId.value == '' ? null : supplierFilterId.value,
			sync_status: filterValueOrNull('sync_status'),
			is_paid: filterValueOrNull('is_paid'),
			has_xml: filterValueOrNull('has_xml'),
			payment_review_status: filterValueOrNull('payment_review_status'),
			ordering: filterValueOrNull('ordering'),
		})
		applyListResponse(response)
	} catch (error) {
		invoices.value = [] as KsefInvoiceItem[]
		errorMessage.value = parseErrorMessage(error, 'KSeF 发票加载失败')
	} finally {
		isLoading.value = false
	}
}

async function loadAutoSyncStatus() {
	try {
		status.value = await getKsefAutoSyncStatus()
	} catch (error) {
		status.value = null
	}
}

function handleFilterVisibleChange(value: boolean) {
	filterVisible.value = value
}

function handleFilterOpen() {
	loadFilterOptions()
}

function handleSearchInput(value: string) {
	keyword.value = value
}

function handleSearchConfirm(value: string) {
	keyword.value = value
	currentPage.value = 1
	loadInvoices()
}

function handleSearchClear() {
	keyword.value = ''
	currentPage.value = 1
	loadInvoices()
}

function handleFilterReset() {
	selectedFilters.value = [] as KsefSelectedFilter[]
	keyword.value = ''
	currentPage.value = 1
	filterVisible.value = false
	loadInvoices()
}

function applyFilter() {
	currentPage.value = 1
	filterVisible.value = false
	loadInvoices()
}

function handlePageChange(payload: UTSJSONObject) {
	const pageValue = payload['page']
	if (pageValue == null) {
		return
	}
	const nextPage = parseInt('' + pageValue)
	if (isNaN(nextPage) || nextPage <= 0 || nextPage == currentPage.value) {
		return
	}
	currentPage.value = nextPage
	loadInvoices()
}

function paidText(value: boolean): string {
	return value ? '已付款' : '未付款'
}

function reviewStatusText(item: KsefInvoiceItem): string {
	if (item.payment_review_status_display != '') return item.payment_review_status_display
	if (item.payment_review_status == 'PAYABLE') return '可支付'
	if (item.payment_review_status == 'NOT_PAYABLE') return '不支付'
	return '待审核'
}

function supplierText(item: KsefInvoiceItem): string {
	if (item.supplier_name != '') return item.supplier_name
	return '未关联供应商'
}

function compactDate(value: string): string {
	if (value == '') {
		return '-'
	}
	if (value.length >= 16) {
		return value.substring(0, 16)
	}
	return value
}

function displayText(value: string): string {
	if (value == '') {
		return '-'
	}
	return value
}

function normalizeAccount(value: string): string {
	if (value == '') {
		return ''
	}
	let result = ''
	for (let index = 0; index < value.length; index += 1) {
		const char = value.substring(index, index + 1)
		if (char >= '0' && char <= '9') {
			result += char
		}
	}
	return result == '' ? value : result
}

function invoiceToListItem(item: KsefInvoiceItem): UTSJSONObject {
	const statusTags: string[] = [paidText(item.is_paid), reviewStatusText(item)]
	if (item.sync_status == 'SYNC_ERROR') {
		statusTags.push('异常')
	}
	return {
		id: item.id.toString(),
		name: displayText(item.invoice_number),
		codeText: displayText(item.seller_name),
		metaText: displayText(item.amount_due) + ' ' + displayText(item.currency) + ' · ' + paidText(item.is_paid),
		supplier_name: supplierText(item),
		seller_nip: displayText(item.seller_nip),
		payment_due_text: compactDate(item.payment_due_date),
		payment_review_text: reviewStatusText(item),
		bank_account_text: displayText(normalizeAccount(item.bank_account_number)),
		rawId: item.id.toString(),
		rawKsefNumber: item.ksef_number,
		rawBankAccount: normalizeAccount(item.bank_account_number),
		tags: statusTags,
	} as UTSJSONObject
}

function copyText(text: string, successTitle: string, emptyTitle: string) {
	if (text == '' || text == '-') {
		uni.showToast({ title: emptyTitle, icon: 'none', duration: 3500 })
		return
	}
	uni.setClipboardData({
		data: text,
		success: () => {
			uni.showToast({ title: successTitle, icon: 'success' })
		},
	})
}

async function handleDownloadXml(invoiceId: string) {
	try {
		await downloadKsefInvoiceXml(invoiceId)
		uni.showToast({ title: takeLatestResponseMessage('详情同步完成'), icon: 'success' })
		loadInvoices()
		loadAutoSyncStatus()
	} catch (error) {
		showErrorToast(parseErrorMessage(error, '详情同步失败'))
	}
}

function openInvoiceDetail(invoiceId: string) {
	if (invoiceId == '') {
		return
	}
	uni.navigateTo({ url: '/pages/ksef/detail?id=' + invoiceId })
}

function handleItemClick(payload: UTSJSONObject) {
	const idValue = payload['rawId']
	const invoiceId = idValue == null ? '' : (idValue as string)
	openInvoiceDetail(invoiceId)
}

function handleMenu(payload: UTSJSONObject) {
	const action = payload['action']
	const item = payload['item']
	if (action == null || item == null) {
		return
	}
	const actionObject = action as UTSJSONObject
	const itemObject = item as UTSJSONObject
	const keyValue = actionObject['key']
	if (keyValue == null) {
		return
	}
	const key = keyValue as string
	const idValue = itemObject['rawId']
	const invoiceId = idValue == null ? '' : (idValue as string)
	if (key == 'view_detail') {
		openInvoiceDetail(invoiceId)
		return
	}
	if (key == 'download_xml') {
		handleDownloadXml(invoiceId)
		return
	}
	if (key == 'copy_account') {
		const accountValue = itemObject['rawBankAccount']
		const accountText = accountValue == null ? '' : (accountValue as string)
		copyText(accountText, '账号已复制', '暂无银行账号')
		return
	}
	if (key == 'copy_ksef') {
		const ksefValue = itemObject['rawKsefNumber']
		const ksefText = ksefValue == null ? '' : (ksefValue as string)
		copyText(ksefText, 'KSeF号已复制', '暂无KSeF号')
	}
}

function handleSubtitleClick(payload: UTSJSONObject) {
	const item = payload['item']
	if (item == null) {
		return
	}
	const itemObject = item as UTSJSONObject
	const ksefValue = itemObject['rawKsefNumber']
	const ksefText = ksefValue == null ? '' : (ksefValue as string)
	copyText(ksefText, 'KSeF号已复制', '暂无KSeF号')
}

function handleFieldClick(payload: UTSJSONObject) {
	const item = payload['item']
	const keyValue = payload['key']
	if (item == null || keyValue == null) {
		return
	}
	const key = keyValue as string
	const itemObject = item as UTSJSONObject
	if (key == 'seller_nip' || key == 'bank_account_text') {
		const value = key == 'seller_nip' ? itemObject['seller_nip'] : itemObject['rawBankAccount']
		const text = value == null ? '' : (value as string)
		copyText(text, key == 'seller_nip' ? '卖方NIP已复制' : '账号已复制', key == 'seller_nip' ? '暂无卖方NIP' : '暂无银行账号')
	}
}

const listItems = computed((): UTSJSONObject[] => {
	const result: UTSJSONObject[] = []
	for (let index = 0; index < invoices.value.length; index += 1) {
		result.push(invoiceToListItem(invoices.value[index]))
	}
	return result
})

const filterDefinitions = computed((): KsefFilterDefinition[] => {
	if (filterOptions.value != null && filterOptions.value!.filters.length > 0) return filterOptions.value!.filters
	return defaultFilterDefinitions.value
})

const hasActiveFilter = computed((): boolean => {
	return keyword.value != '' || selectedFilters.value.length > 0 || supplierFilterId.value != ''
})

const emptyText = computed((): string => {
	if (isLoading.value) return '正在加载'
	if (hasActiveFilter.value) return '没有匹配的 KSeF 发票'
	return '暂无 KSeF 发票'
})

const pendingXmlText = computed((): string => {
	if (status.value == null) return '-'
	return status.value.pending_xml_count.toString()
})

const lastSuccessText = computed((): string => {
	if (status.value == null) return '-'
	return compactDate(status.value.last_success_at)
})

const summaryItems = computed((): UTSJSONObject[] => {
	const paymentFilter = selectedFilterValue('is_paid')
	const reviewFilter = selectedFilterValue('payment_review_status')
	return [
		{ key: 'total', label: '发票总数', value: totalCount.value.toString() } as UTSJSONObject,
		{ key: 'supplier', label: '供应商', value: supplierFilterId.value == '' ? '全部' : (supplierFilterName.value == '' ? ('#' + supplierFilterId.value) : supplierFilterName.value) } as UTSJSONObject,
		{ key: 'pending', label: '待详情', value: pendingXmlText.value } as UTSJSONObject,
		{ key: 'payment', label: '付款筛选', value: paymentFilter == '' ? '全部' : (paymentFilter == 'true' ? '已付款' : '未付款') } as UTSJSONObject,
		{ key: 'review', label: '支付审核', value: reviewFilter == '' ? '全部' : (reviewFilter == 'PAYABLE' ? '可支付' : (reviewFilter == 'NOT_PAYABLE' ? '不支付' : '待审核')) } as UTSJSONObject,
		{ key: 'last', label: '最近成功', value: lastSuccessText.value } as UTSJSONObject,
	]
})

onLoad((event: OnLoadOptions) => {
	const supplierIdValue = event['supplier_id'] == null ? event['supplier'] : event['supplier_id']
	supplierFilterId.value = supplierIdValue == null ? '' : (supplierIdValue as string)
	const supplierNameValue = event['supplier_name']
	if (supplierNameValue != null) {
		const decodedSupplierName = UTSAndroid.consoleDebugError(decodeURIComponent(supplierNameValue as string), " at pages/ksef/index.uvue:612")
		supplierFilterName.value = decodedSupplierName == null ? '' : decodedSupplierName
	}
	const searchValue = event['search']
	if (searchValue != null && searchValue != '') {
		const decodedSearch = UTSAndroid.consoleDebugError(decodeURIComponent(searchValue as string), " at pages/ksef/index.uvue:617")
		keyword.value = decodedSearch == null ? '' : decodedSearch
	}
	loadAutoSyncStatus()
	loadInvoices()
})

onShow(() => {
	loadAutoSyncStatus()
	const flag = uni.getStorageSync(refreshStorageKey)
	if (flag != null && ('' + flag) != '') {
		uni.removeStorageSync(refreshStorageKey)
		loadInvoices()
	}
})

return (): any | null => {

const _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter",_easycom_lili_universal_filter)
const _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList",_easycom_lili_UniversalList)

  return _cE("view", _uM({ class: "page" }), [
    _cV(_component_lili_universal_filter, _uM({
      title: "KSeF 发票",
      searchPlaceholder: "发票号、KSeF号、卖方",
      searchValue: unref(keyword),
      filterVisible: unref(filterVisible),
      showBack: true,
      showSearch: true,
      showFilter: true,
      showHome: true,
      filterActive: hasActiveFilter.value,
      filterText: "重置",
      homePath: "/pages/tabbar/settings",
      onSearchInput: handleSearchInput,
      onSearchConfirm: handleSearchConfirm,
      onSearchClear: handleSearchClear,
      "onUpdate:filterVisible": handleFilterVisibleChange,
      onFilterOpen: handleFilterOpen
    }), _uM({
      "filter-panel": withSlotCtx((): any[] => [
        _cE("view", _uM({ class: "list-filter-panel" }), [
          isTrue(unref(filterOptionsLoading))
            ? _cE("view", _uM({
                key: 0,
                class: "list-filter-state"
              }), [
                _cE("text", _uM({ class: "list-filter-state-text" }), "筛选选项加载中...")
              ])
            : unref(filterOptionsError) != ''
              ? _cE("view", _uM({
                  key: 1,
                  class: "list-filter-state"
                }), [
                  _cE("text", _uM({ class: "list-filter-state-text" }), _tD(unref(filterOptionsError)), 1 /* TEXT */)
                ])
              : _cE("view", _uM({
                  key: 2,
                  class: "list-filter-groups"
                }), [
                  _cE(Fragment, null, RenderHelpers.renderList(filterDefinitions.value, (filter, __key, __index, _cached): any => {
                    return _cE("view", _uM({
                      key: filter.key,
                      class: "list-filter-group"
                    }), [
                      _cE("text", _uM({ class: "list-filter-group-title" }), _tD(filter.label), 1 /* TEXT */),
                      _cE("view", _uM({ class: "list-filter-options" }), [
                        _cE("view", _uM({
                          class: _nC(isFilterOptionSelected(filter.param, '') ? 'list-filter-option list-filter-option-active' : 'list-filter-option'),
                          onClick: () => {clearFilterOption(filter.param)}
                        }), [
                          _cE("text", _uM({
                            class: _nC(isFilterOptionSelected(filter.param, '') ? 'list-filter-option-text list-filter-option-text-active' : 'list-filter-option-text')
                          }), "全部", 2 /* CLASS */)
                        ], 10 /* CLASS, PROPS */, ["onClick"]),
                        _cE(Fragment, null, RenderHelpers.renderList(filter.options, (option, __key, __index, _cached): any => {
                          return _cE("view", _uM({
                            key: filter.key + '-' + option.value,
                            class: _nC(isFilterOptionSelected(filter.param, option.value) ? 'list-filter-option list-filter-option-active' : 'list-filter-option'),
                            onClick: () => {toggleFilterOption(filter.param, option.value)}
                          }), [
                            _cE("text", _uM({
                              class: _nC(isFilterOptionSelected(filter.param, option.value) ? 'list-filter-option-text list-filter-option-text-active' : 'list-filter-option-text')
                            }), _tD(option.label), 3 /* TEXT, CLASS */)
                          ], 10 /* CLASS, PROPS */, ["onClick"])
                        }), 128 /* KEYED_FRAGMENT */)
                      ])
                    ])
                  }), 128 /* KEYED_FRAGMENT */)
                ]),
          _cE("view", _uM({ class: "list-filter-actions" }), [
            _cE("view", _uM({
              class: "list-filter-btn list-filter-btn-light",
              onClick: handleFilterReset
            }), [
              _cE("text", _uM({ class: "list-filter-btn-light-text" }), "重置")
            ]),
            _cE("view", _uM({
              class: "list-filter-btn list-filter-btn-primary",
              onClick: applyFilter
            }), [
              _cE("text", _uM({ class: "list-filter-btn-primary-text" }), "应用")
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
                onClick: loadInvoices
              }), [
                _cE("text", _uM({ class: "retry-btn-text" }), "重新加载")
              ])
            ])
          : _cC("v-if", true),
        _cV(_component_lili_UniversalList, _uM({
          items: listItems.value,
          keyField: "id",
          titleField: "name",
          subtitleField: "codeText",
          metaField: "metaText",
          tagField: "tags",
          fields: unref(fieldConfig),
          loading: unref(isLoading),
          loadingText: "正在加载 KSeF 发票",
          keepContentOnLoading: true,
          inlineLoadingText: "KSeF 发票刷新中...",
          emptyText: emptyText.value,
          emptyIcon: "◎",
          showMenu: true,
          menuActions: unref(menuActions),
          showChevron: true,
          showPagination: true,
          currentPage: unref(currentPage),
          totalPages: unref(totalPages),
          totalCount: unref(totalCount),
          showFloatingAdd: false,
          summaryTitle: "KSeF 汇总",
          summaryItems: summaryItems.value,
          summaryCollapsedByDefault: true,
          onItemClick: handleItemClick,
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
const GenPagesKsefIndexStyles = [_uM([["page", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#F6F7FB"]]))], ["page-scroll", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#F6F7FB"]]))], ["page-content", _pS(_uM([["paddingLeft", 6], ["paddingRight", 6], ["paddingTop", 6], ["paddingBottom", 96]]))], ["list-filter-panel", _pS(_uM([["paddingBottom", 8]]))], ["list-filter-state", _pS(_uM([["minHeight", 64], ["alignItems", "center"], ["justifyContent", "center"]]))], ["list-filter-state-text", _pS(_uM([["fontSize", 13], ["lineHeight", "18px"], ["color", "#64748B"]]))], ["list-filter-groups", _pS(_uM([["paddingBottom", 2]]))], ["list-filter-group", _pS(_uM([["marginBottom", 10]]))], ["list-filter-group-title", _pS(_uM([["fontSize", 14], ["lineHeight", "18px"], ["color", "#0F172A"], ["fontWeight", "bold"]]))], ["list-filter-options", _pS(_uM([["flexDirection", "row"], ["flexWrap", "wrap"], ["marginTop", 10]]))], ["list-filter-option", _pS(_uM([["height", 34], ["paddingLeft", 12], ["paddingRight", 12], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#F8FAFC"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E2E8F0"], ["borderRightColor", "#E2E8F0"], ["borderBottomColor", "#E2E8F0"], ["borderLeftColor", "#E2E8F0"], ["alignItems", "center"], ["justifyContent", "center"], ["marginRight", 8], ["marginBottom", 8]]))], ["list-filter-option-active", _pS(_uM([["backgroundColor", "#0F172A"], ["borderTopColor", "#0F172A"], ["borderRightColor", "#0F172A"], ["borderBottomColor", "#0F172A"], ["borderLeftColor", "#0F172A"]]))], ["list-filter-option-text", _pS(_uM([["fontSize", 13], ["lineHeight", "13px"], ["color", "#475569"]]))], ["list-filter-option-text-active", _pS(_uM([["color", "#FFFFFF"]]))], ["list-filter-actions", _pS(_uM([["flexDirection", "row"], ["marginTop", 8]]))], ["list-filter-btn", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["height", 40], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["alignItems", "center"], ["justifyContent", "center"]]))], ["list-filter-btn-light", _pS(_uM([["backgroundColor", "#F3F6FA"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E2E8F0"], ["borderRightColor", "#E2E8F0"], ["borderBottomColor", "#E2E8F0"], ["borderLeftColor", "#E2E8F0"], ["marginRight", 8]]))], ["list-filter-btn-primary", _pS(_uM([["backgroundColor", "#0F172A"]]))], ["list-filter-btn-light-text", _pS(_uM([["fontSize", 14], ["lineHeight", "14px"], ["color", "#475569"]]))], ["list-filter-btn-primary-text", _pS(_uM([["fontSize", 14], ["lineHeight", "14px"], ["color", "#FFFFFF"]]))], ["error-card", _pS(_uM([["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["paddingTop", 18], ["paddingRight", 18], ["paddingBottom", 18], ["paddingLeft", 18], ["marginBottom", 10], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#FECACA"], ["borderRightColor", "#FECACA"], ["borderBottomColor", "#FECACA"], ["borderLeftColor", "#FECACA"], ["alignItems", "center"]]))], ["error-title", _pS(_uM([["fontSize", 18], ["lineHeight", "24px"], ["color", "#B42318"], ["fontWeight", "bold"]]))], ["error-desc", _pS(_uM([["fontSize", 14], ["lineHeight", "20px"], ["color", "#7F1D1D"], ["marginTop", 8], ["textAlign", "center"]]))], ["retry-btn", _pS(_uM([["marginTop", 14], ["height", 40], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#0F172A"], ["paddingLeft", 18], ["paddingRight", 18], ["alignItems", "center"], ["justifyContent", "center"]]))], ["retry-btn-text", _pS(_uM([["fontSize", 14], ["color", "#FFFFFF"]]))]])]
