import _easycom_lili_universal_filter from '@/uni_modules/lili-universal-filter/components/lili-universal-filter/lili-universal-filter.uvue'
import _easycom_lili_UniversalList from '@/uni_modules/lili-UniversalList/components/lili-UniversalList/lili-UniversalList.uvue'
import { computed } from 'vue'
import liliBottomSelect from '@/uni_modules/lili_bottom-select/components/lili_bottom-select/lili_bottom-select.uvue'
import { request, takeLatestResponseMessage } from '@/pkg/api/index.uts'
import { approveInventoryTransfer, cancelInventoryTransfer, completeInventoryTransfer, getInventoryTransfers, InventoryListQuery, InventoryListResponse } from '@/pkg/api/modules/inventory'
import { showErrorToast } from '@/pkg/util/toast.uts'

type SelectOption = { __$originalPosition?: UTSSourceMapPosition<"SelectOption", "pages/inventory-transfers/index.uvue", 147, 6>;
	value: string
	label: string
}


const __sfc__ = defineComponent({
  __name: 'index',
  setup(__props) {
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

const refreshStorageKey = 'refresh:pages:inventory-transfers:index'
const keyword = ref('')
const filterVisible = ref(false)
const isLoading = ref(false)
const errorMessage = ref('')
const items = ref<UTSJSONObject[]>([])
const currentPage = ref(1)
const totalPages = ref(1)
const totalCount = ref(0)
const pageSize = ref(20)
const pageTotalQuantity = ref('0')
const pageTransferredQuantity = ref('0')
const statusFilter = ref('')
const fromLocationFilterValue = ref('')
const fromLocationFilterText = ref('')
const toLocationFilterValue = ref('')
const toLocationFilterText = ref('')
const draftStatus = ref('')
const draftFromLocationValue = ref('')
const draftFromLocationText = ref('')
const draftToLocationValue = ref('')
const draftToLocationText = ref('')
const filterPanelHeight = ref(380)
const filterContentHeight = ref(316)

const statusOptions = [
	{ value: '', label: '全部' } as SelectOption,
	{ value: 'DRAFT', label: '草稿' } as SelectOption,
	{ value: 'APPROVED', label: '已审核' } as SelectOption,
	{ value: 'COMPLETED', label: '已完成' } as SelectOption,
	{ value: 'CANCELLED', label: '已取消' } as SelectOption,
]

const fieldConfig = ref<UTSJSONObject[]>([
	{ key: 'dateText', label: '日期:' } as UTSJSONObject,
	{ key: 'itemsCountText', label: '明细:' } as UTSJSONObject,
	{ key: 'totalQuantityText', label: '总数:' } as UTSJSONObject,
	{ key: 'transferredQuantityText', label: '已调拨:' } as UTSJSONObject,
])

const menuActions = ref<UTSJSONObject[]>([
	{ key: 'details', text: '明细' } as UTSJSONObject,
	{ key: 'edit', text: '编辑' } as UTSJSONObject,
	{ key: 'approve', text: '审核' } as UTSJSONObject,
	{ key: 'complete', text: '完成' } as UTSJSONObject,
	{ key: 'cancel', text: '取消' } as UTSJSONObject,
	{ key: 'reload', text: '刷新' } as UTSJSONObject,
])

const tagColorMap = ref<UTSJSONObject>({
	草稿: 'muted',
	已审核: 'info',
	已完成: 'success',
	已取消: 'danger',
	DRAFT: 'muted',
	APPROVED: 'info',
	COMPLETED: 'success',
	CANCELLED: 'danger',
} as UTSJSONObject)

function stringValue(value: any | null, fallback: string = ''): string {
	if (value == null) return fallback
	const text = '' + value
	if (text == '') return fallback
	return text
}

function intValue(value: any | null): number {
	const parsed = parseInt(stringValue(value))
	if (isNaN(parsed)) return 0
	return parsed
}

function parseErrorMessage(error: any, fallback: string): string {
	let message = fallback
	if (error != null) {
		const errorText = JSON.stringify(error)
		if (errorText != null && errorText != '') {
			const parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/inventory-transfers/index.uvue:230")
			if (parsedError != null) {
				const rawMessage = parsedError['message']
				if (rawMessage != null) {
					const parsedMessage = rawMessage as string
					if (parsedMessage != '') message = parsedMessage
				}
			}
			if (message == fallback && errorText != '{}') message = errorText
		}
	}
	return message
}

function parseObject(value: any | null): UTSJSONObject | null {
	if (value == null) return null
	const text = JSON.stringify(value)
	if (text == null || text == '') return null
	const trimmedText = text.trim()
	if (trimmedText == '' || trimmedText.substring(0, 1) != '{') return null
	try {
		return UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(trimmedText), " at pages/inventory-transfers/index.uvue:251")
	} catch (error) {
		return null
	}
}

function parseObjectArray(value: any | null): UTSJSONObject[] {
	if (value == null) return [] as UTSJSONObject[]
	const text = JSON.stringify(value)
	if (text == null || text == '') return [] as UTSJSONObject[]
	const trimmedText = text.trim()
	if (trimmedText == '' || trimmedText.substring(0, 1) != '[') return [] as UTSJSONObject[]
	let parsed: UTSJSONObject[] | null = null
	try {
		parsed = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(trimmedText), " at pages/inventory-transfers/index.uvue:265")
	} catch (error) {
		return [] as UTSJSONObject[]
	}
	if (parsed == null) return [] as UTSJSONObject[]
	return parsed!
}

function firstStringField(obj: UTSJSONObject, keys: string[]): string {
	for (let index = 0; index < keys.length; index += 1) {
		const value = stringValue(obj[keys[index]])
		if (value != '') return value
	}
	return ''
}

function extractRows(raw: any | null): UTSJSONObject[] {
	const directArray = parseObjectArray(raw)
	if (directArray.length > 0) return directArray
	const rawObject = parseObject(raw)
	if (rawObject == null) return [] as UTSJSONObject[]
	const dataArray = parseObjectArray(rawObject!['data'])
	if (dataArray.length > 0) return dataArray
	const resultsArray = parseObjectArray(rawObject!['results'])
	if (resultsArray.length > 0) return resultsArray
	const itemsArray = parseObjectArray(rawObject!['items'])
	if (itemsArray.length > 0) return itemsArray
	const dataObject = parseObject(rawObject!['data'])
	if (dataObject != null) {
		const nestedResults = parseObjectArray(dataObject!['results'])
		if (nestedResults.length > 0) return nestedResults
	}
	return [] as UTSJSONObject[]
}

function normalizeOptionNode(item: UTSJSONObject): UTSJSONObject {
	const value = firstStringField(item, ['value', 'id', 'pk'])
	let text = firstStringField(item, ['text', 'label', 'name', 'name_cn', 'title'])
	if (text == '') text = value
	return {
		value: value,
		text: text,
	} as UTSJSONObject
}

function buildBottomSelectResponse(raw: any | null): UTSJSONObject {
	const rows = extractRows(raw)
	const result: UTSJSONObject[] = []
	for (let index = 0; index < rows.length; index += 1) result.push(normalizeOptionNode(rows[index]))
	return {
		data: result,
		results: result,
		total: result.length,
		total_count: result.length,
	} as UTSJSONObject
}

function buildOptionQuery(params: UTSJSONObject): UTSJSONObject {
	const pageValue = intValue(params['page'])
	const pageSizeValue = intValue(params['pageSize'])
	const query = { __$originalPosition: new UTSSourceMapPosition("query", "pages/inventory-transfers/index.uvue", 325, 8), 
		page: pageValue <= 0 ? 1 : pageValue,
		page_size: pageSizeValue <= 0 ? 50 : pageSizeValue,
	} as UTSJSONObject
	const keywordValue = stringValue(params['keyword'])
	if (keywordValue != '') {
		query['search'] = keywordValue
		query['keyword'] = keywordValue
	}
	return query
}

async function fetchLocationFilterOptions(params: UTSJSONObject): Promise<UTSJSONObject> {
	const raw = await request('/api/inventory/locations/', 'GET', buildOptionQuery(params), true)
	return buildBottomSelectResponse(raw)
}

function statusText(status: string, display: string): string {
	if (display != '') return display
	if (status == 'DRAFT') return '草稿'
	if (status == 'APPROVED') return '已审核'
	if (status == 'COMPLETED') return '已完成'
	if (status == 'CANCELLED') return '已取消'
	return status == '' ? '-' : status
}

function compactDate(value: string): string {
	if (value == '') return '-'
	if (value.length >= 10) return value.substring(0, 10)
	return value
}

function copyText(text: string, successTitle: string, emptyTitle: string) {
	if (text == '' || text == '-') {
		uni.showToast({ title: emptyTitle, icon: 'none', duration: 3500 })
		return
	}
	uni.setClipboardData({ data: text, success: () => { uni.showToast({ title: successTitle, icon: 'success' }) } })
}

function updateFilterPanelLayout() {
	const info = uni.getWindowInfo()
	let nextPanelHeight = info.windowHeight - 168
	if (nextPanelHeight > 420) nextPanelHeight = 420
	if (nextPanelHeight < 300) nextPanelHeight = 300
	let nextContentHeight = nextPanelHeight - 64
	if (nextContentHeight < 220) nextContentHeight = 220
	filterPanelHeight.value = nextPanelHeight
	filterContentHeight.value = nextContentHeight
}

function closeFilterDrawer() {
	filterVisible.value = false
}

function buildQuery(): InventoryListQuery {
	return {
		search: keyword.value == '' ? null : keyword.value,
		page: currentPage.value,
		page_size: pageSize.value,
		status: statusFilter.value == '' ? null : statusFilter.value,
		alert_status: null,
		supplier: null,
		category: null,
		is_listed: null,
		location: null,
		from_location: fromLocationFilterValue.value == '' ? null : fromLocationFilterValue.value,
		to_location: toLocationFilterValue.value == '' ? null : toLocationFilterValue.value,
		transaction_type: null,
		location_type: null,
		is_active: null,
	} as InventoryListQuery
}

function applyListResponse(response: InventoryListResponse) {
	items.value = response.results
	currentPage.value = response.current_page
	totalPages.value = response.total_pages
	totalCount.value = response.total_count
	pageSize.value = response.page_size
	let total = 0
	let transferred = 0
	for (let index = 0; index < response.results.length; index += 1) {
		const row = response.results[index]
		total = total + intValue(row['total_quantity'])
		transferred = transferred + intValue(row['transferred_quantity'])
	}
	pageTotalQuantity.value = total.toString()
	pageTransferredQuantity.value = transferred.toString()
}

async function loadItems() {
	if (isLoading.value) return
	isLoading.value = true
	errorMessage.value = ''
	try {
		applyListResponse(await getInventoryTransfers(buildQuery()))
	} catch (error) {
		items.value = [] as UTSJSONObject[]
		currentPage.value = 1
		totalPages.value = 1
		totalCount.value = 0
		pageTotalQuantity.value = '0'
		pageTransferredQuantity.value = '0'
		errorMessage.value = parseErrorMessage(error, '调拨单加载失败')
	} finally {
		isLoading.value = false
	}
}

function handleSearchInput(value: string) { keyword.value = value }
function handleSearchConfirm(value: string) { keyword.value = value; currentPage.value = 1; loadItems() }
function handleSearchClear() { keyword.value = ''; currentPage.value = 1; loadItems() }
function handleFilterVisibleChange(value: boolean) { filterVisible.value = value }

function handleFilterOpen() {
	updateFilterPanelLayout()
	draftStatus.value = statusFilter.value
	draftFromLocationValue.value = fromLocationFilterValue.value
	draftFromLocationText.value = fromLocationFilterText.value
	draftToLocationValue.value = toLocationFilterValue.value
	draftToLocationText.value = toLocationFilterText.value
}

function handleFromLocationFilterChange(payload: UTSJSONObject) {
	draftFromLocationValue.value = stringValue(payload['value'])
	draftFromLocationText.value = stringValue(payload['text'])
}

function handleToLocationFilterChange(payload: UTSJSONObject) {
	draftToLocationValue.value = stringValue(payload['value'])
	draftToLocationText.value = stringValue(payload['text'])
}

function selectStatus(value: string) {
	draftStatus.value = value
}

function handleFilterReset() {
	statusFilter.value = ''
	fromLocationFilterValue.value = ''
	fromLocationFilterText.value = ''
	toLocationFilterValue.value = ''
	toLocationFilterText.value = ''
	draftStatus.value = ''
	draftFromLocationValue.value = ''
	draftFromLocationText.value = ''
	draftToLocationValue.value = ''
	draftToLocationText.value = ''
	keyword.value = ''
	currentPage.value = 1
	closeFilterDrawer()
	loadItems()
}

function applySelectedFilters() {
	statusFilter.value = draftStatus.value
	fromLocationFilterValue.value = draftFromLocationValue.value
	fromLocationFilterText.value = draftFromLocationText.value
	toLocationFilterValue.value = draftToLocationValue.value
	toLocationFilterText.value = draftToLocationText.value
	currentPage.value = 1
	closeFilterDrawer()
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

function transferItem(item: UTSJSONObject): UTSJSONObject {
	const status = statusText(stringValue(item['status']), stringValue(item['status_display']))
	const fromName = stringValue(item['from_location_name'], '-')
	const toName = stringValue(item['to_location_name'], '-')
	const dateText = compactDate(stringValue(item['transfer_date']))
	return {
		id: stringValue(item['id']),
		rawId: stringValue(item['id']),
		title: stringValue(item['transfer_number'], '调拨单'),
		subtitle: fromName + ' -> ' + toName,
		meta: dateText,
		statusText: status,
		dateText: dateText,
		itemsCountText: stringValue(item['items_count'], '0'),
		totalQuantityText: stringValue(item['total_quantity'], '0'),
		transferredQuantityText: stringValue(item['transferred_quantity'], '0'),
		remarkText: stringValue(item['remark'], '-'),
		tags: [status] as string[],
	} as UTSJSONObject
}

function navigateToEdit(id: string) {
	if (id == '') return
	uni.navigateTo({ url: '/pages/inventory-transfers/from?id=' + id })
}

function navigateToDetails(id: string) {
	if (id == '') return
	uni.navigateTo({ url: '/pages/inventory-transfers/details/index?transfer=' + id })
}

function handleItemClick(payload: UTSJSONObject) {
	navigateToEdit(stringValue(payload['rawId'], stringValue(payload['id'])))
}

async function runAction(actionName: string, id: string) {
	try {
		if (actionName == 'approve') await approveInventoryTransfer(id)
		else if (actionName == 'complete') await completeInventoryTransfer(id)
		else if (actionName == 'cancel') await cancelInventoryTransfer(id)
		uni.showToast({ title: takeLatestResponseMessage('操作成功'), icon: 'success' })
		loadItems()
	} catch (error) {
		showErrorToast(parseErrorMessage(error, '操作失败'))
	}
}

function confirmRunAction(actionKey: string, id: string, title: string, content: string) {
	uni.showModal({ title: title, content: content, success: (res) => { if (res.confirm) runAction(actionKey, id) } })
}

function handleMenu(payload: UTSJSONObject) {
	const action = payload['action']
	const item = payload['item']
	if (action == null || item == null) return
	const actionKey = stringValue((action as UTSJSONObject)['key'])
	const id = stringValue((item as UTSJSONObject)['rawId'])
	if (actionKey == 'details') navigateToDetails(id)
	else if (actionKey == 'edit') navigateToEdit(id)
	else if (actionKey == 'approve') confirmRunAction(actionKey, id, '审核调拨', '确定审核通过这张调拨单吗？')
	else if (actionKey == 'complete') confirmRunAction(actionKey, id, '完成调拨', '确定完成这张调拨单吗？')
	else if (actionKey == 'cancel') confirmRunAction(actionKey, id, '取消调拨', '确定取消这张调拨单吗？')
	else if (actionKey == 'reload') loadItems()
}

function handleSubtitleClick(payload: UTSJSONObject) {
	copyText(stringValue(payload['value']), '调拨路线已复制', '暂无调拨路线')
}

function handleMetaClick(payload: UTSJSONObject) {
	copyText(stringValue(payload['value']), '调拨日期已复制', '暂无调拨日期')
}

function handleFieldClick(payload: UTSJSONObject) {
	const value = stringValue(payload['value'])
	const label = stringValue(payload['label'], '内容')
	copyText(value, label.replace(':', '') + '已复制', '暂无内容')
}

function handleCreate() {
	uni.navigateTo({ url: '/pages/inventory-transfers/create' })
}

function consumeRefresh(): boolean {
	const flag = uni.getStorageSync(refreshStorageKey)
	if (flag == null || ('' + flag) == '') return false
	uni.removeStorageSync(refreshStorageKey)
	return true
}

const listItems = computed((): UTSJSONObject[] => {
	const result: UTSJSONObject[] = []
	for (let index = 0; index < items.value.length; index += 1) result.push(transferItem(items.value[index]))
	return result
})

const hasActiveFilter = computed((): boolean => {
	return keyword.value != '' || statusFilter.value != '' || fromLocationFilterValue.value != '' || toLocationFilterValue.value != ''
})

const emptyText = computed((): string => {
	if (isLoading.value) return '正在加载'
	if (hasActiveFilter.value) return '没有匹配的调拨单'
	return '暂无调拨单'
})

const summaryItems = computed((): UTSJSONObject[] => {
	return [
		{ key: 'total', label: '调拨单数', value: totalCount.value.toString() } as UTSJSONObject,
		{ key: 'quantity', label: '本页数量', value: pageTotalQuantity.value } as UTSJSONObject,
		{ key: 'transferred', label: '本页已调拨', value: pageTransferredQuantity.value } as UTSJSONObject,
		{ key: 'page', label: '页码', value: currentPage.value.toString() + '/' + totalPages.value.toString() } as UTSJSONObject,
	]
})

const filterPanelStyle = computed((): string => {
	return 'height:' + filterPanelHeight.value.toString() + 'px;'
})

const filterContentScrollStyle = computed((): string => {
	return 'height:' + filterContentHeight.value.toString() + 'px;'
})

onLoad(() => {
	updateFilterPanelLayout()
	loadItems()
})

onShow(() => {
	updateFilterPanelLayout()
	if (consumeRefresh()) loadItems()
})

return (): any | null => {

const _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter",_easycom_lili_universal_filter)
const _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList",_easycom_lili_UniversalList)

  return _cE("view", _uM({ class: "page" }), [
    _cV(_component_lili_universal_filter, _uM({
      title: "调拨单",
      searchPlaceholder: "调拨单号、备注",
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
        _cE("view", _uM({
          class: "transfer-filter-panel",
          style: _nS(filterPanelStyle.value)
        }), [
          _cE("scroll-view", _uM({
            "scroll-y": "true",
            class: "transfer-filter-content-scroll",
            style: _nS(filterContentScrollStyle.value)
          }), [
            _cE("view", _uM({ class: "transfer-filter-scroll-inner" }), [
              _cE("view", _uM({ class: "transfer-filter-select-group" }), [
                _cE("text", _uM({ class: "transfer-filter-select-title" }), "调出位置"),
                _cE("view", _uM({ class: "transfer-filter-select-wrap" }), [
                  _cV(unref(liliBottomSelect), _uM({
                    value: unref(draftFromLocationValue),
                    valueText: unref(draftFromLocationText),
                    title: "选择调出位置",
                    placeholder: "全部调出位置",
                    searchPlaceholder: "搜索库存位置",
                    emptyText: "暂无库存位置",
                    fetchData: fetchLocationFilterOptions,
                    showAddAction: false,
                    showEditAction: false,
                    onChange: handleFromLocationFilterChange
                  }), null, 8 /* PROPS */, ["value", "valueText"])
                ])
              ]),
              _cE("view", _uM({ class: "transfer-filter-select-group" }), [
                _cE("text", _uM({ class: "transfer-filter-select-title" }), "调入位置"),
                _cE("view", _uM({ class: "transfer-filter-select-wrap" }), [
                  _cV(unref(liliBottomSelect), _uM({
                    value: unref(draftToLocationValue),
                    valueText: unref(draftToLocationText),
                    title: "选择调入位置",
                    placeholder: "全部调入位置",
                    searchPlaceholder: "搜索库存位置",
                    emptyText: "暂无库存位置",
                    fetchData: fetchLocationFilterOptions,
                    showAddAction: false,
                    showEditAction: false,
                    onChange: handleToLocationFilterChange
                  }), null, 8 /* PROPS */, ["value", "valueText"])
                ])
              ]),
              _cE("view", _uM({ class: "transfer-filter-group" }), [
                _cE("text", _uM({ class: "transfer-filter-group-title" }), "调拨状态"),
                _cE("view", _uM({ class: "transfer-filter-options" }), [
                  _cE(Fragment, null, RenderHelpers.renderList(statusOptions, (option, __key, __index, _cached): any => {
                    return _cE("view", _uM({
                      key: 'status-' + option.value,
                      class: _nC(unref(draftStatus) == option.value ? 'transfer-filter-option transfer-filter-option-active' : 'transfer-filter-option'),
                      onClick: () => {selectStatus(option.value)}
                    }), [
                      _cE("text", _uM({
                        class: _nC(unref(draftStatus) == option.value ? 'transfer-filter-option-text transfer-filter-option-text-active' : 'transfer-filter-option-text')
                      }), _tD(option.label), 3 /* TEXT, CLASS */)
                    ], 10 /* CLASS, PROPS */, ["onClick"])
                  }), 64 /* STABLE_FRAGMENT */)
                ])
              ])
            ])
          ], 4 /* STYLE */),
          _cE("view", _uM({ class: "transfer-filter-actions" }), [
            _cE("view", _uM({
              class: "transfer-filter-btn transfer-filter-btn-light",
              onClick: handleFilterReset
            }), [
              _cE("text", _uM({ class: "transfer-filter-btn-light-text" }), "重置")
            ]),
            _cE("view", _uM({
              class: "transfer-filter-btn transfer-filter-btn-primary",
              onClick: applySelectedFilters
            }), [
              _cE("text", _uM({ class: "transfer-filter-btn-primary-text" }), "应用")
            ])
          ])
        ], 4 /* STYLE */)
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
          titleField: "title",
          subtitleField: "subtitle",
          metaField: "meta",
          tagField: "tags",
          tagColorMap: unref(tagColorMap),
          fields: unref(fieldConfig),
          loading: unref(isLoading),
          loadingText: "正在加载调拨单",
          keepContentOnLoading: true,
          inlineLoadingText: "调拨单刷新中...",
          emptyText: emptyText.value,
          emptyIcon: "◎",
          showMenu: true,
          menuActions: unref(menuActions),
          showChevron: false,
          showPagination: true,
          currentPage: unref(currentPage),
          totalPages: unref(totalPages),
          totalCount: unref(totalCount),
          summaryTitle: "调拨概览",
          summaryItems: summaryItems.value,
          showFloatingAdd: true,
          floatingAddText: "新增调拨",
          onItemClick: handleItemClick,
          onMenu: handleMenu,
          onPageChange: handlePageChange,
          onSubtitleClick: handleSubtitleClick,
          onFieldClick: handleFieldClick,
          onMetaClick: handleMetaClick,
          onFloatingAdd: handleCreate
        }), null, 8 /* PROPS */, ["items", "tagColorMap", "fields", "loading", "emptyText", "menuActions", "currentPage", "totalPages", "totalCount", "summaryItems"])
      ])
    ], 4 /* STYLE */)
  ])
}
}

})
export default __sfc__
const GenPagesInventoryTransfersIndexStyles = [_uM([["page", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#F6F7FB"]]))], ["page-scroll", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#F6F7FB"]]))], ["page-content", _pS(_uM([["paddingTop", 6], ["paddingRight", 6], ["paddingBottom", 96], ["paddingLeft", 6]]))], ["error-card", _pS(_uM([["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["paddingTop", 18], ["paddingRight", 18], ["paddingBottom", 18], ["paddingLeft", 18], ["marginBottom", 10], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#FECACA"], ["borderRightColor", "#FECACA"], ["borderBottomColor", "#FECACA"], ["borderLeftColor", "#FECACA"], ["alignItems", "center"]]))], ["error-title", _pS(_uM([["fontSize", 18], ["lineHeight", "24px"], ["color", "#B42318"], ["fontWeight", "bold"]]))], ["error-desc", _pS(_uM([["fontSize", 14], ["lineHeight", "20px"], ["color", "#7F1D1D"], ["marginTop", 8], ["textAlign", "center"]]))], ["retry-btn", _pS(_uM([["marginTop", 14], ["height", 40], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#0F172A"], ["paddingLeft", 18], ["paddingRight", 18], ["alignItems", "center"], ["justifyContent", "center"]]))], ["retry-btn-text", _pS(_uM([["fontSize", 14], ["color", "#FFFFFF"]]))], ["transfer-filter-panel", _pS(_uM([["position", "relative"], ["paddingTop", 2]]))], ["transfer-filter-content-scroll", _pS(_uM([["paddingRight", 2]]))], ["transfer-filter-scroll-inner", _pS(_uM([["paddingBottom", 58]]))], ["transfer-filter-select-group", _pS(_uM([["paddingLeft", 10], ["paddingRight", 10], ["paddingTop", 10], ["paddingBottom", 10], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["backgroundColor", "#FFFFFF"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E5EAF1"], ["borderRightColor", "#E5EAF1"], ["borderBottomColor", "#E5EAF1"], ["borderLeftColor", "#E5EAF1"], ["marginBottom", 6]]))], ["transfer-filter-group", _pS(_uM([["paddingLeft", 10], ["paddingRight", 10], ["paddingTop", 10], ["paddingBottom", 10], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["backgroundColor", "#FFFFFF"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E5EAF1"], ["borderRightColor", "#E5EAF1"], ["borderBottomColor", "#E5EAF1"], ["borderLeftColor", "#E5EAF1"], ["marginBottom", 6]]))], ["transfer-filter-select-title", _pS(_uM([["fontSize", 13], ["lineHeight", "17px"], ["color", "#0F172A"], ["fontWeight", "bold"]]))], ["transfer-filter-group-title", _pS(_uM([["fontSize", 13], ["lineHeight", "17px"], ["color", "#0F172A"], ["fontWeight", "bold"]]))], ["transfer-filter-select-wrap", _pS(_uM([["marginTop", 8]]))], ["transfer-filter-options", _pS(_uM([["flexDirection", "row"], ["flexWrap", "wrap"], ["marginTop", 8]]))], ["transfer-filter-option", _pS(_uM([["minWidth", 48], ["height", 30], ["paddingLeft", 10], ["paddingRight", 10], ["borderTopLeftRadius", 15], ["borderTopRightRadius", 15], ["borderBottomRightRadius", 15], ["borderBottomLeftRadius", 15], ["backgroundColor", "#F8FAFC"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E2E8F0"], ["borderRightColor", "#E2E8F0"], ["borderBottomColor", "#E2E8F0"], ["borderLeftColor", "#E2E8F0"], ["alignItems", "center"], ["justifyContent", "center"], ["marginRight", 6], ["marginBottom", 6]]))], ["transfer-filter-option-active", _pS(_uM([["backgroundColor", "#0F172A"], ["borderTopColor", "#0F172A"], ["borderRightColor", "#0F172A"], ["borderBottomColor", "#0F172A"], ["borderLeftColor", "#0F172A"]]))], ["transfer-filter-option-text", _pS(_uM([["fontSize", 12], ["lineHeight", "17px"], ["color", "#334155"]]))], ["transfer-filter-option-text-active", _pS(_uM([["color", "#FFFFFF"]]))], ["transfer-filter-actions", _pS(_uM([["position", "absolute"], ["left", 0], ["right", 0], ["bottom", 0], ["flexDirection", "row"], ["paddingTop", 6], ["paddingLeft", 2], ["paddingRight", 2], ["paddingBottom", 4], ["borderTopWidth", 1], ["borderTopStyle", "solid"], ["borderTopColor", "rgba(226,232,240,0.78)"], ["backgroundColor", "#FFFFFF"]]))], ["transfer-filter-btn", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["height", 38], ["borderTopLeftRadius", 11], ["borderTopRightRadius", 11], ["borderBottomRightRadius", 11], ["borderBottomLeftRadius", 11], ["alignItems", "center"], ["justifyContent", "center"]]))], ["transfer-filter-btn-light", _pS(_uM([["backgroundColor", "#F3F6FA"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E2E8F0"], ["borderRightColor", "#E2E8F0"], ["borderBottomColor", "#E2E8F0"], ["borderLeftColor", "#E2E8F0"], ["marginRight", 8]]))], ["transfer-filter-btn-primary", _pS(_uM([["backgroundColor", "#0F172A"]]))], ["transfer-filter-btn-light-text", _pS(_uM([["fontSize", 13], ["lineHeight", "18px"], ["color", "#475569"]]))], ["transfer-filter-btn-primary-text", _pS(_uM([["fontSize", 13], ["lineHeight", "18px"], ["color", "#FFFFFF"]]))]])]
