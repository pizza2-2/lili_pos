import _easycom_lili_universal_filter from '@/uni_modules/lili-universal-filter/components/lili-universal-filter/lili-universal-filter.uvue'
import _easycom_lili_UniversalList from '@/uni_modules/lili-UniversalList/components/lili-UniversalList/lili-UniversalList.uvue'
import { computed } from 'vue'
import liliBottomSelect from '@/uni_modules/lili_bottom-select/components/lili_bottom-select/lili_bottom-select.uvue'
import { request, takeLatestResponseMessage } from '@/pkg/api/index.uts'
import { adjustInventoryCheck, approveInventoryCheck, completeInventoryCheck, deleteInventoryCheck, getInventoryChecks, InventoryListQuery, InventoryListResponse, startInventoryCheck } from '@/pkg/api/modules/inventory'
import { showErrorToast } from '@/pkg/util/toast.uts'

type SelectOption = { __$originalPosition?: UTSSourceMapPosition<"SelectOption", "pages/inventory-checks/index.uvue", 142, 6>;
	value: string
	label: string
}


const __sfc__ = defineComponent({
  __name: 'index',
  setup(__props) {
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

const refreshStorageKey = 'refresh:pages:inventory-checks:index'
const keyword = ref('')
const filterVisible = ref(false)
const isLoading = ref(false)
const errorMessage = ref('')
const items = ref<UTSJSONObject[]>([])
const currentPage = ref(1)
const totalPages = ref(1)
const totalCount = ref(0)
const pageSize = ref(20)
const statusFilter = ref('')
const locationFilterValue = ref('')
const locationFilterText = ref('')
const categoryFilterValue = ref('')
const categoryFilterText = ref('')
const draftStatus = ref('')
const draftLocationValue = ref('')
const draftLocationText = ref('')
const draftCategoryValue = ref('')
const draftCategoryText = ref('')
const filterPanelHeight = ref(420)
const filterContentHeight = ref(356)

const statusOptions = [
	{ value: '', label: '全部' } as SelectOption,
	{ value: 'DRAFT', label: '草稿' } as SelectOption,
	{ value: 'IN_PROGRESS', label: '盘点中' } as SelectOption,
	{ value: 'ADJUSTED', label: '已完成' } as SelectOption,
	{ value: 'CANCELLED', label: '已取消' } as SelectOption,
]

const fieldConfig = ref<UTSJSONObject[]>([
	{ key: 'categoryText', label: '分类:' } as UTSJSONObject,
	{ key: 'progressText', label: '进度:' } as UTSJSONObject,
	{ key: 'totalItemsText', label: '明细:' } as UTSJSONObject,
	{ key: 'discrepancyText', label: '差异:' } as UTSJSONObject,
])

const menuActions = ref<UTSJSONObject[]>([
	{ key: 'details', text: '明细' } as UTSJSONObject,
	{ key: 'reload', text: '刷新' } as UTSJSONObject,
])

const tagColorMap = ref<UTSJSONObject>({
	草稿: 'muted',
	盘点中: 'warning',
	待完成: 'info',
	已完成: 'success',
	已取消: 'danger',
	DRAFT: 'muted',
	IN_PROGRESS: 'warning',
	PENDING_REVIEW: 'info',
	APPROVED: 'success',
	ADJUSTED: 'success',
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

function booleanValue(value: any | null): boolean {
	const text = stringValue(value).toLowerCase()
	return text == 'true' || text == '1' || text == 'yes'
}

function parseErrorMessage(error: any, fallback: string): string {
	let message = fallback
	if (error != null) {
		let errorText = ''
		try {
			const text = JSON.stringify(error)
			if (text != null) errorText = text
		} catch (stringifyError) {
			errorText = ''
		}
		if (errorText != null && errorText != '') {
			let parsedError: UTSJSONObject | null = null
			try {
				const trimmedText = errorText.trim()
				if (trimmedText != '' && trimmedText.substring(0, 1) == '{') parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(trimmedText), " at pages/inventory-checks/index.uvue:236")
			} catch (parseError) {
				parsedError = null
			}
			if (parsedError != null) {
				const rawMessage = parsedError!['message']
				if (rawMessage != null) {
					const parsedMessage = stringValue(rawMessage)
					if (parsedMessage != '') message = parsedMessage
				}
			}
			if (message == fallback && errorText != '{}' && errorText != 'null') message = errorText
		}
		if (message == fallback) {
			const textMessage = stringValue(error)
			if (textMessage != '' && textMessage != '[object Object]') message = textMessage
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
		return UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(trimmedText), " at pages/inventory-checks/index.uvue:264")
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
		parsed = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(trimmedText), " at pages/inventory-checks/index.uvue:278")
	} catch (error) {
		return [] as UTSJSONObject[]
	}
	if (parsed == null) return [] as UTSJSONObject[]
	return parsed!
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
	const dataObject = parseObject(rawObject!['data'])
	if (dataObject != null) {
		const nestedResults = parseObjectArray(dataObject!['results'])
		if (nestedResults.length > 0) return nestedResults
	}
	return [] as UTSJSONObject[]
}

function firstStringField(obj: UTSJSONObject, keys: string[]): string {
	for (let index = 0; index < keys.length; index += 1) {
		const text = stringValue(obj[keys[index]])
		if (text != '') return text
	}
	return ''
}

function normalizeOptionNode(item: UTSJSONObject): UTSJSONObject {
	const value = firstStringField(item, ['value', 'id', 'pk'])
	let text = firstStringField(item, ['text', 'label', 'name', 'name_cn', 'title'])
	if (text == '') text = value
	return { value: value, text: text } as UTSJSONObject
}

function buildOptionValue(item: UTSJSONObject): string {
	return firstStringField(item, ['value', 'id', 'pk'])
}

function buildOptionText(item: UTSJSONObject): string {
	const fullName = firstStringField(item, ['full_name', 'full_path', 'path'])
	if (fullName != '') return fullName
	const name = firstStringField(item, ['text', 'label', 'name', 'name_cn', 'title'])
	if (name != '') return name
	return buildOptionValue(item)
}

function convertCategoryTreeItems(items: UTSJSONObject[]): UTSJSONObject[] {
	const result: UTSJSONObject[] = []
	for (let index = 0; index < items.length; index += 1) {
		const item = items[index]
		const children = parseObjectArray(item['children'])
		const treeChildren = convertCategoryTreeItems(children)
		const label = buildOptionText(item)
		result.push({
			value: buildOptionValue(item),
			text: label,
			label: label,
			full_name: stringValue(item['full_name'], label),
			code: stringValue(item['code']),
			level: intValue(item['level']),
			disabled: booleanValue(item['disabled']),
			has_children: booleanValue(item['has_children']) || treeChildren.length > 0,
			children: treeChildren,
		} as UTSJSONObject)
	}
	return result
}

function extractCategoryTreeSource(value: any | null): UTSJSONObject[] {
	const rawObject = parseObject(value)
	if (rawObject == null) return [] as UTSJSONObject[]
	const groups = parseObjectArray(rawObject!['groups'])
	for (let index = 0; index < groups.length; index += 1) {
		const group = groups[index]
		if (stringValue(group['key']) == 'parent') return parseObjectArray(group['items'])
	}
	if (groups.length > 0) return parseObjectArray(groups[0]['items'])
	let items = parseObjectArray(rawObject!['items'])
	if (items.length > 0) return items
	items = parseObjectArray(rawObject!['results'])
	if (items.length > 0) return items
	return parseObjectArray(rawObject!['data'])
}

function buildBottomSelectResponse(raw: any | null): UTSJSONObject {
	const rows = extractRows(raw)
	const result: UTSJSONObject[] = []
	for (let index = 0; index < rows.length; index += 1) result.push(normalizeOptionNode(rows[index]))
	return { data: result, results: result, total: result.length, total_count: result.length } as UTSJSONObject
}

function buildOptionQuery(params: UTSJSONObject): UTSJSONObject {
	const pageValue = intValue(params['page'])
	const pageSizeValue = intValue(params['pageSize'])
	const query = { __$originalPosition: new UTSSourceMapPosition("query", "pages/inventory-checks/index.uvue", 378, 8),  page: pageValue <= 0 ? 1 : pageValue, page_size: pageSizeValue <= 0 ? 50 : pageSizeValue } as UTSJSONObject
	const keywordValue = stringValue(params['keyword'])
	if (keywordValue != '') {
		query['search'] = keywordValue
		query['keyword'] = keywordValue
	}
	const idValue = stringValue(params['id'])
	if (idValue != '') query['id'] = idValue
	const parentValue = stringValue(params['parent'])
	if (parentValue != '') query['parent'] = parentValue
	return query
}

async function fetchLocationFilterOptions(params: UTSJSONObject): Promise<UTSJSONObject> {
	const raw = await request('/api/inventory/locations/', 'GET', buildOptionQuery(params), true)
	return buildBottomSelectResponse(raw)
}

async function fetchCategoryFilterOptions(params: UTSJSONObject): Promise<UTSJSONObject> {
	const query = buildOptionQuery(params)
	query['key'] = 'parent'
	const raw = await request('/api/categories/categories/options/', 'GET', query, true)
	const rows = convertCategoryTreeItems(extractCategoryTreeSource(raw))
	return { data: rows, results: rows, total: rows.length, total_count: rows.length } as UTSJSONObject
}

function statusText(status: string, display: string): string {
	if (status == 'DRAFT') return '草稿'
	if (status == 'IN_PROGRESS') return '盘点中'
	if (status == 'PENDING_REVIEW') return '待完成'
	if (status == 'APPROVED') return '待完成'
	if (status == 'ADJUSTED') return '已完成'
	if (status == 'CANCELLED') return '已取消'
	if (display != '') return display
	return status == '' ? '-' : status
}

function menuActionsForStatus(status: string): UTSJSONObject[] {
	const actions: UTSJSONObject[] = [
		{ key: 'details', text: '明细' } as UTSJSONObject,
	]
	if (status == 'DRAFT') {
		actions.push({ key: 'edit', text: '编辑' } as UTSJSONObject)
		actions.push({ key: 'start-check', text: '开始盘点' } as UTSJSONObject)
	} else if (status == 'IN_PROGRESS' || status == 'PENDING_REVIEW' || status == 'APPROVED') {
		actions.push({ key: 'complete-check', text: '完成盘点' } as UTSJSONObject)
	}
	actions.push({ key: 'delete', text: '删除' } as UTSJSONObject)
	actions.push({ key: 'reload', text: '刷新' } as UTSJSONObject)
	return actions
}

function compactDate(value: string): string {
	if (value == '') return '-'
	if (value.length >= 10) return value.substring(0, 10)
	return value
}

function updateFilterPanelLayout() {
	const info = uni.getWindowInfo()
	let nextPanelHeight = info.windowHeight - 168
	if (nextPanelHeight > 460) nextPanelHeight = 460
	if (nextPanelHeight < 340) nextPanelHeight = 340
	let nextContentHeight = nextPanelHeight - 64
	if (nextContentHeight < 260) nextContentHeight = 260
	filterPanelHeight.value = nextPanelHeight
	filterContentHeight.value = nextContentHeight
}

function buildQuery(): InventoryListQuery {
	return {
		search: keyword.value == '' ? null : keyword.value,
		page: currentPage.value,
		page_size: pageSize.value,
		status: statusFilter.value == '' ? null : statusFilter.value,
		alert_status: null,
		supplier: null,
		category: categoryFilterValue.value == '' ? null : categoryFilterValue.value,
		is_listed: null,
		location: locationFilterValue.value == '' ? null : locationFilterValue.value,
		from_location: null,
		to_location: null,
		transfer_order: null,
		inventory_check: null,
		product: null,
		stock: null,
		check_type: null,
		is_checked: null,
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
}

async function loadItems() {
	if (isLoading.value) return
	isLoading.value = true
	errorMessage.value = ''
	try {
		applyListResponse(await getInventoryChecks(buildQuery()))
	} catch (error) {
		items.value = [] as UTSJSONObject[]
		currentPage.value = 1
		totalPages.value = 1
		totalCount.value = 0
		errorMessage.value = parseErrorMessage(error, '盘点单加载失败')
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
	draftLocationValue.value = locationFilterValue.value
	draftLocationText.value = locationFilterText.value
	draftCategoryValue.value = categoryFilterValue.value
	draftCategoryText.value = categoryFilterText.value
}

function handleLocationFilterChange(payload: UTSJSONObject) {
	draftLocationValue.value = stringValue(payload['value'])
	draftLocationText.value = stringValue(payload['text'])
}

function handleCategoryFilterChange(payload: UTSJSONObject) {
	draftCategoryValue.value = stringValue(payload['value'])
	draftCategoryText.value = stringValue(payload['text'])
}

function selectStatus(value: string) { draftStatus.value = value }

function closeFilterDrawer() { filterVisible.value = false }

function handleFilterReset() {
	statusFilter.value = ''
	locationFilterValue.value = ''
	locationFilterText.value = ''
	categoryFilterValue.value = ''
	categoryFilterText.value = ''
	draftStatus.value = ''
	draftLocationValue.value = ''
	draftLocationText.value = ''
	draftCategoryValue.value = ''
	draftCategoryText.value = ''
	keyword.value = ''
	currentPage.value = 1
	closeFilterDrawer()
	loadItems()
}

function applySelectedFilters() {
	statusFilter.value = draftStatus.value
	locationFilterValue.value = draftLocationValue.value
	locationFilterText.value = draftLocationText.value
	categoryFilterValue.value = draftCategoryValue.value
	categoryFilterText.value = draftCategoryText.value
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

function checkItem(item: UTSJSONObject): UTSJSONObject {
	const statusValue = stringValue(item['status'])
	const status = statusText(statusValue, stringValue(item['status_display']))
	const total = intValue(item['total_items'])
	const checked = intValue(item['checked_items'])
	const progress = total <= 0 ? '0%' : stringValue(item['progress_pct'], '0') + '%'
	return {
		id: stringValue(item['id']),
		rawId: stringValue(item['id']),
		statusValue: statusValue,
		title: stringValue(item['check_number'], '盘点单'),
		subtitle: stringValue(item['location_name'], '-'),
		meta: compactDate(stringValue(item['planned_date'])),
		statusText: status,
		categoryText: stringValue(item['category_names'], '-'),
		progressText: checked.toString() + '/' + total.toString() + ' (' + progress + ')',
		totalItemsText: total.toString(),
		discrepancyText: stringValue(item['discrepancy_items'], '0'),
		tags: [status] as string[],
		menuActions: menuActionsForStatus(statusValue),
	} as UTSJSONObject
}

function navigateToEdit(id: string) {
	if (id == '') return
	uni.navigateTo({ url: '/pages/inventory-checks/from?id=' + id })
}

function navigateToDetails(id: string) {
	if (id == '') return
	uni.navigateTo({ url: '/pages/inventory-checks/details/index?check=' + id })
}

function handleItemClick(payload: UTSJSONObject) {
	navigateToDetails(stringValue(payload['rawId'], stringValue(payload['id'])))
}

function showCompleteNavigatePrompt() {
	uni.showModal({
		title: '盘点已完成',
		content: '库存已按盘点差异更新，点击确定查看库存。',
		showCancel: false,
		confirmText: '查看库存',
		success: (res) => {
			if (res.confirm) uni.navigateTo({ url: '/pages/inventory-management/index' })
		},
	})
}

async function runDelete(id: string) {
	try {
		await deleteInventoryCheck(id)
		uni.showToast({ title: takeLatestResponseMessage('删除成功'), icon: 'success' })
		loadItems()
	} catch (error) {
		showErrorToast(parseErrorMessage(error, '删除失败'))
	}
}

async function runAction(actionName: string, id: string, statusValue: string) {
	try {
		if (actionName == 'start-check') {
			await startInventoryCheck(id)
			uni.showToast({ title: takeLatestResponseMessage('操作成功'), icon: 'success' })
			loadItems()
			return
		}
		if (actionName == 'complete-check') {
			if (statusValue == 'PENDING_REVIEW') {
				await approveInventoryCheck(id)
				await adjustInventoryCheck(id)
			} else if (statusValue == 'APPROVED') {
				await adjustInventoryCheck(id)
			} else {
				await completeInventoryCheck(id)
			}
			uni.setStorageSync(refreshStorageKey, '1')
			loadItems()
			showCompleteNavigatePrompt()
			return
		}
		uni.showToast({ title: takeLatestResponseMessage('操作成功'), icon: 'success' })
		loadItems()
	} catch (error) {
		showErrorToast(parseErrorMessage(error, '操作失败'))
	}
}

function confirmRunAction(actionKey: string, id: string, statusValue: string, title: string, content: string) {
	uni.showModal({ title: title, content: content, success: (res) => { if (res.confirm) runAction(actionKey, id, statusValue) } })
}

function confirmDelete(id: string) {
	uni.showModal({ title: '删除盘点单', content: '确定删除这张盘点单吗？删除后盘点明细也会一起删除。', success: (res) => { if (res.confirm) runDelete(id) } })
}

function handleMenu(payload: UTSJSONObject) {
	const action = payload['action']
	const item = payload['item']
	if (action == null || item == null) return
	const actionKey = stringValue((action as UTSJSONObject)['key'])
	const itemObject = item as UTSJSONObject
	const id = stringValue(itemObject['rawId'])
	const statusValue = stringValue(itemObject['statusValue'])
	if (actionKey == 'details') navigateToDetails(id)
	else if (actionKey == 'edit') navigateToEdit(id)
	else if (actionKey == 'start-check') confirmRunAction(actionKey, id, statusValue, '开始盘点', '确定开始这张盘点单吗？')
	else if (actionKey == 'complete-check') confirmRunAction(actionKey, id, statusValue, '完成盘点', '完成后会直接按差异调整库存，并跳转到库存管理。确定继续吗？')
	else if (actionKey == 'delete') confirmDelete(id)
	else if (actionKey == 'reload') loadItems()
}

function handleCreate() {
	uni.navigateTo({ url: '/pages/inventory-checks/create' })
}

function consumeRefresh(): boolean {
	const flag = uni.getStorageSync(refreshStorageKey)
	if (flag == null || ('' + flag) == '') return false
	uni.removeStorageSync(refreshStorageKey)
	return true
}

const listItems = computed((): UTSJSONObject[] => {
	const result: UTSJSONObject[] = []
	for (let index = 0; index < items.value.length; index += 1) result.push(checkItem(items.value[index]))
	return result
})

const hasActiveFilter = computed((): boolean => {
	return keyword.value != '' || statusFilter.value != '' || locationFilterValue.value != '' || categoryFilterValue.value != ''
})

const emptyText = computed((): string => {
	if (isLoading.value) return '正在加载'
	if (hasActiveFilter.value) return '没有匹配的盘点单'
	return '暂无盘点单'
})

const summaryItems = computed((): UTSJSONObject[] => {
	return [
		{ key: 'total', label: '盘点单数', value: totalCount.value.toString() } as UTSJSONObject,
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
      title: "盘点单",
      searchPlaceholder: "盘点单号、目的、备注",
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
          class: "check-filter-panel",
          style: _nS(filterPanelStyle.value)
        }), [
          _cE("scroll-view", _uM({
            "scroll-y": "true",
            class: "check-filter-content-scroll",
            style: _nS(filterContentScrollStyle.value)
          }), [
            _cE("view", _uM({ class: "check-filter-scroll-inner" }), [
              _cE("view", _uM({ class: "check-filter-select-group" }), [
                _cE("text", _uM({ class: "check-filter-select-title" }), "盘点位置"),
                _cE("view", _uM({ class: "check-filter-select-wrap" }), [
                  _cV(unref(liliBottomSelect), _uM({
                    value: unref(draftLocationValue),
                    valueText: unref(draftLocationText),
                    title: "选择盘点位置",
                    placeholder: "全部位置",
                    searchPlaceholder: "搜索库存位置",
                    emptyText: "暂无库存位置",
                    fetchData: fetchLocationFilterOptions,
                    showAddAction: false,
                    showEditAction: false,
                    onChange: handleLocationFilterChange
                  }), null, 8 /* PROPS */, ["value", "valueText"])
                ])
              ]),
              _cE("view", _uM({ class: "check-filter-group" }), [
                _cE("text", _uM({ class: "check-filter-group-title" }), "盘点状态"),
                _cE("view", _uM({ class: "check-filter-options" }), [
                  _cE(Fragment, null, RenderHelpers.renderList(statusOptions, (option, __key, __index, _cached): any => {
                    return _cE("view", _uM({
                      key: 'status-' + option.value,
                      class: _nC(unref(draftStatus) == option.value ? 'check-filter-option check-filter-option-active' : 'check-filter-option'),
                      onClick: () => {selectStatus(option.value)}
                    }), [
                      _cE("text", _uM({
                        class: _nC(unref(draftStatus) == option.value ? 'check-filter-option-text check-filter-option-text-active' : 'check-filter-option-text')
                      }), _tD(option.label), 3 /* TEXT, CLASS */)
                    ], 10 /* CLASS, PROPS */, ["onClick"])
                  }), 64 /* STABLE_FRAGMENT */)
                ])
              ]),
              _cE("view", _uM({ class: "check-filter-select-group" }), [
                _cE("text", _uM({ class: "check-filter-select-title" }), "盘点分类"),
                _cE("view", _uM({ class: "check-filter-select-wrap" }), [
                  _cV(unref(liliBottomSelect), _uM({
                    value: unref(draftCategoryValue),
                    valueText: unref(draftCategoryText),
                    title: "选择盘点分类",
                    placeholder: "全部分类",
                    searchPlaceholder: "搜索分类",
                    emptyText: "暂无分类",
                    fetchData: fetchCategoryFilterOptions,
                    tree: true,
                    childrenKey: "children",
                    expandOnClickNode: true,
                    showAddAction: false,
                    showEditAction: false,
                    onChange: handleCategoryFilterChange
                  }), null, 8 /* PROPS */, ["value", "valueText"])
                ])
              ])
            ])
          ], 4 /* STYLE */),
          _cE("view", _uM({ class: "check-filter-actions" }), [
            _cE("view", _uM({
              class: "check-filter-btn check-filter-btn-light",
              onClick: handleFilterReset
            }), [
              _cE("text", _uM({ class: "check-filter-btn-light-text" }), "重置")
            ]),
            _cE("view", _uM({
              class: "check-filter-btn check-filter-btn-primary",
              onClick: applySelectedFilters
            }), [
              _cE("text", _uM({ class: "check-filter-btn-primary-text" }), "应用")
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
          loadingText: "正在加载盘点单",
          keepContentOnLoading: true,
          inlineLoadingText: "盘点单刷新中...",
          emptyText: emptyText.value,
          emptyIcon: "◎",
          showMenu: true,
          menuActions: unref(menuActions),
          showChevron: false,
          showPagination: true,
          currentPage: unref(currentPage),
          totalPages: unref(totalPages),
          totalCount: unref(totalCount),
          summaryTitle: "盘点概览",
          summaryItems: summaryItems.value,
          showFloatingAdd: true,
          floatingAddText: "新增盘点",
          onItemClick: handleItemClick,
          onMenu: handleMenu,
          onPageChange: handlePageChange,
          onFloatingAdd: handleCreate
        }), null, 8 /* PROPS */, ["items", "tagColorMap", "fields", "loading", "emptyText", "menuActions", "currentPage", "totalPages", "totalCount", "summaryItems"])
      ])
    ], 4 /* STYLE */)
  ])
}
}

})
export default __sfc__
const GenPagesInventoryChecksIndexStyles = [_uM([["page", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#F6F7FB"]]))], ["page-scroll", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#F6F7FB"]]))], ["page-content", _pS(_uM([["paddingTop", 6], ["paddingRight", 6], ["paddingBottom", 96], ["paddingLeft", 6]]))], ["error-card", _pS(_uM([["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["paddingTop", 18], ["paddingRight", 18], ["paddingBottom", 18], ["paddingLeft", 18], ["marginBottom", 10], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#FECACA"], ["borderRightColor", "#FECACA"], ["borderBottomColor", "#FECACA"], ["borderLeftColor", "#FECACA"], ["alignItems", "center"]]))], ["error-title", _pS(_uM([["fontSize", 18], ["lineHeight", "24px"], ["color", "#B42318"], ["fontWeight", "bold"]]))], ["error-desc", _pS(_uM([["fontSize", 14], ["lineHeight", "20px"], ["color", "#7F1D1D"], ["marginTop", 8], ["textAlign", "center"]]))], ["retry-btn", _pS(_uM([["marginTop", 14], ["height", 40], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#0F172A"], ["paddingLeft", 18], ["paddingRight", 18], ["alignItems", "center"], ["justifyContent", "center"]]))], ["retry-btn-text", _pS(_uM([["fontSize", 14], ["color", "#FFFFFF"]]))], ["check-filter-panel", _pS(_uM([["position", "relative"], ["paddingTop", 2]]))], ["check-filter-content-scroll", _pS(_uM([["paddingRight", 2]]))], ["check-filter-scroll-inner", _pS(_uM([["paddingBottom", 58]]))], ["check-filter-select-group", _pS(_uM([["paddingLeft", 10], ["paddingRight", 10], ["paddingTop", 10], ["paddingBottom", 10], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["backgroundColor", "#FFFFFF"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E5EAF1"], ["borderRightColor", "#E5EAF1"], ["borderBottomColor", "#E5EAF1"], ["borderLeftColor", "#E5EAF1"], ["marginBottom", 6]]))], ["check-filter-group", _pS(_uM([["paddingLeft", 10], ["paddingRight", 10], ["paddingTop", 10], ["paddingBottom", 10], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["backgroundColor", "#FFFFFF"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E5EAF1"], ["borderRightColor", "#E5EAF1"], ["borderBottomColor", "#E5EAF1"], ["borderLeftColor", "#E5EAF1"], ["marginBottom", 6]]))], ["check-filter-select-title", _pS(_uM([["fontSize", 13], ["lineHeight", "17px"], ["color", "#0F172A"], ["fontWeight", "bold"]]))], ["check-filter-group-title", _pS(_uM([["fontSize", 13], ["lineHeight", "17px"], ["color", "#0F172A"], ["fontWeight", "bold"]]))], ["check-filter-select-wrap", _pS(_uM([["marginTop", 8]]))], ["check-filter-options", _pS(_uM([["flexDirection", "row"], ["flexWrap", "wrap"], ["marginTop", 8]]))], ["check-filter-option", _pS(_uM([["minWidth", 48], ["height", 30], ["paddingLeft", 10], ["paddingRight", 10], ["borderTopLeftRadius", 15], ["borderTopRightRadius", 15], ["borderBottomRightRadius", 15], ["borderBottomLeftRadius", 15], ["backgroundColor", "#F8FAFC"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E2E8F0"], ["borderRightColor", "#E2E8F0"], ["borderBottomColor", "#E2E8F0"], ["borderLeftColor", "#E2E8F0"], ["alignItems", "center"], ["justifyContent", "center"], ["marginRight", 6], ["marginBottom", 6]]))], ["check-filter-option-active", _pS(_uM([["backgroundColor", "#0F172A"], ["borderTopColor", "#0F172A"], ["borderRightColor", "#0F172A"], ["borderBottomColor", "#0F172A"], ["borderLeftColor", "#0F172A"]]))], ["check-filter-option-text", _pS(_uM([["fontSize", 12], ["lineHeight", "17px"], ["color", "#334155"]]))], ["check-filter-option-text-active", _pS(_uM([["color", "#FFFFFF"]]))], ["check-filter-actions", _pS(_uM([["position", "absolute"], ["left", 0], ["right", 0], ["bottom", 0], ["flexDirection", "row"], ["paddingTop", 6], ["paddingLeft", 2], ["paddingRight", 2], ["paddingBottom", 4], ["borderTopWidth", 1], ["borderTopStyle", "solid"], ["borderTopColor", "rgba(226,232,240,0.78)"], ["backgroundColor", "#FFFFFF"]]))], ["check-filter-btn", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["height", 38], ["borderTopLeftRadius", 11], ["borderTopRightRadius", 11], ["borderBottomRightRadius", 11], ["borderBottomLeftRadius", 11], ["alignItems", "center"], ["justifyContent", "center"]]))], ["check-filter-btn-light", _pS(_uM([["backgroundColor", "#F3F6FA"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E2E8F0"], ["borderRightColor", "#E2E8F0"], ["borderBottomColor", "#E2E8F0"], ["borderLeftColor", "#E2E8F0"], ["marginRight", 8]]))], ["check-filter-btn-primary", _pS(_uM([["backgroundColor", "#0F172A"]]))], ["check-filter-btn-light-text", _pS(_uM([["fontSize", 13], ["lineHeight", "18px"], ["color", "#475569"]]))], ["check-filter-btn-primary-text", _pS(_uM([["fontSize", 13], ["lineHeight", "18px"], ["color", "#FFFFFF"]]))]])]
