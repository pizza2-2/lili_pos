import _easycom_lili_universal_filter from '@/uni_modules/lili-universal-filter/components/lili-universal-filter/lili-universal-filter.uvue'
import _easycom_lili_UniversalList from '@/uni_modules/lili-UniversalList/components/lili-UniversalList/lili-UniversalList.uvue'
import { computed } from 'vue'
import { takeLatestResponseMessage } from '@/pkg/api/index.uts'
import { approveInventoryTransfer, cancelInventoryTransfer, completeInventoryTransfer, getInventoryTransfers, InventoryListQuery, InventoryListResponse } from '@/pkg/api/modules/inventory'


const __sfc__ = defineComponent({
  __name: 'index',
  setup(__props) {
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

const keyword = ref('')
const refreshStorageKey = 'refresh:pages:inventory-transfers:index'
const isLoading = ref(false)
const errorMessage = ref('')
const items = ref<UTSJSONObject[]>([])
const currentPage = ref(1)
const totalPages = ref(1)
const totalCount = ref(0)
const pageSize = ref(20)

const fieldConfig = ref<UTSJSONObject[]>([
	{ key: 'status_display', label: '状态' } as UTSJSONObject,
	{ key: 'total_quantity', label: '总数' } as UTSJSONObject,
	{ key: 'transferred_quantity', label: '已调拨' } as UTSJSONObject,
])
const menuActions = ref<UTSJSONObject[]>([
	{ key: 'edit', text: '编辑' } as UTSJSONObject,
	{ key: 'approve-transfer', text: '审核' } as UTSJSONObject,
	{ key: 'complete-transfer', text: '完成' } as UTSJSONObject,
	{ key: 'cancel-transfer', text: '取消' } as UTSJSONObject,
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

function compactDate(value: string): string {
	if (value == '') return '-'
	if (value.length >= 16) return value.substring(0, 16)
	return value
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
		const query = { search: keyword.value == '' ? null : keyword.value, page: currentPage.value, page_size: pageSize.value, status: null, alert_status: null, transaction_type: null, location_type: null, is_active: null } as InventoryListQuery
		applyListResponse(await getInventoryTransfers(query))
	} catch (error) {
		items.value = [] as UTSJSONObject[]
		currentPage.value = 1
		totalPages.value = 1
		totalCount.value = 0
		errorMessage.value = parseErrorMessage(error, '调拨单加载失败')
	} finally {
		isLoading.value = false
	}
}

function handleSearchInput(value: string) { keyword.value = value }
function handleSearchConfirm(value: string) { keyword.value = value; currentPage.value = 1; loadItems() }
function handleSearchClear() { keyword.value = ''; currentPage.value = 1; loadItems() }

function handlePageChange(payload: UTSJSONObject) {
	const pageValue = payload['page']
	if (pageValue == null) return
	const nextPage = parseInt('' + pageValue)
	if (isNaN(nextPage) || nextPage <= 0 || nextPage == currentPage.value) return
	currentPage.value = nextPage
	loadItems()
}

function transferItem(item: UTSJSONObject): UTSJSONObject {
	return {
		id: stringField(item, 'id'),
		rawId: stringField(item, 'id'),
		name: stringField(item, 'transfer_number', '调拨单'),
		subtitle: stringField(item, 'from_location_name', '-') + ' -> ' + stringField(item, 'to_location_name', '-'),
		meta: compactDate(stringField(item, 'transfer_date')),
		status_display: stringField(item, 'status_display', stringField(item, 'status')),
		total_quantity: stringField(item, 'total_quantity', '0'),
		transferred_quantity: stringField(item, 'transferred_quantity', '0'),
		tags: [stringField(item, 'status_display', stringField(item, 'status'))] as string[],
	} as UTSJSONObject
}

async function runAction(actionName: string, id: string) {
	try {
		if (actionName == 'approve-transfer') await approveInventoryTransfer(id)
		else if (actionName == 'complete-transfer') await completeInventoryTransfer(id)
		else if (actionName == 'cancel-transfer') await cancelInventoryTransfer(id)
		uni.showToast({ title: takeLatestResponseMessage('操作成功'), icon: 'success' })
		loadItems()
	} catch (error) {
		uni.showToast({ title: parseErrorMessage(error, '操作失败'), icon: 'none' })
	}
}

function confirmRunAction(actionKey: string, id: string, title: string, content: string) {
	uni.showModal({ title: title, content: content, success: (res) => { if (res.confirm) runAction(actionKey, id) } })
}

function handleMenu(payload: UTSJSONObject) {
	const action = payload['action']
	const item = payload['item']
	if (action == null || item == null) return
	const actionKey = stringField(action as UTSJSONObject, 'key')
	const id = stringField(item as UTSJSONObject, 'rawId')
	if (actionKey == 'edit') uni.navigateTo({ url: '/pages/inventory-transfers/from?id=' + id })
	else if (actionKey == 'approve-transfer') confirmRunAction(actionKey, id, '审核调拨', '确定审核通过这张调拨单吗？')
	else if (actionKey == 'complete-transfer') confirmRunAction(actionKey, id, '完成调拨', '确定完成这张调拨单吗？')
	else if (actionKey == 'cancel-transfer') confirmRunAction(actionKey, id, '取消调拨', '确定取消这张调拨单吗？')
	else if (actionKey == 'reload') loadItems()
}

function handleCreate(payload: UTSJSONObject) {
	uni.navigateTo({ url: '/pages/inventory-transfers/from' })
}

const listItems = computed((): UTSJSONObject[] => {
	const result: UTSJSONObject[] = []
	for (let index = 0; index < items.value.length; index += 1) result.push(transferItem(items.value[index]))
	return result
})

const emptyText = computed((): string => {
	if (isLoading.value) return '正在加载'
	if (keyword.value != '') return '没有匹配的调拨单'
	return '暂无调拨单'
})

const summaryItems = computed((): UTSJSONObject[] => {
	return [
		{ key: 'total', label: '调拨单数', value: totalCount.value.toString() } as UTSJSONObject,
		{ key: 'page', label: '页码', value: currentPage.value.toString() + '/' + totalPages.value.toString() } as UTSJSONObject,
	]
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
      title: "调拨单",
      searchPlaceholder: "调拨单号、备注",
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
          loadingText: "正在加载调拨单",
          emptyText: emptyText.value,
          emptyIcon: "◎",
          showMenu: true,
          menuActions: unref(menuActions),
          showPagination: true,
          currentPage: unref(currentPage),
          totalPages: unref(totalPages),
          totalCount: unref(totalCount),
          summaryTitle: "调拨概览",
          summaryItems: summaryItems.value,
          showFloatingAdd: true,
          floatingAddText: "新增",
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
const GenPagesInventoryTransfersIndexStyles = [_uM([["page", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#F6F7FB"]]))], ["page-scroll", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#F6F7FB"]]))], ["page-content", _pS(_uM([["paddingTop", 6], ["paddingRight", 6], ["paddingBottom", 96], ["paddingLeft", 6]]))], ["error-card", _pS(_uM([["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["paddingTop", 18], ["paddingRight", 18], ["paddingBottom", 18], ["paddingLeft", 18], ["marginBottom", 10], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#FECACA"], ["borderRightColor", "#FECACA"], ["borderBottomColor", "#FECACA"], ["borderLeftColor", "#FECACA"], ["alignItems", "center"]]))], ["error-title", _pS(_uM([["fontSize", 18], ["lineHeight", "24px"], ["color", "#B42318"], ["fontWeight", "bold"]]))], ["error-desc", _pS(_uM([["fontSize", 14], ["lineHeight", "20px"], ["color", "#7F1D1D"], ["marginTop", 8], ["textAlign", "center"]]))], ["retry-btn", _pS(_uM([["marginTop", 14], ["height", 40], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#0F172A"], ["paddingLeft", 18], ["paddingRight", 18], ["alignItems", "center"], ["justifyContent", "center"]]))], ["retry-btn-text", _pS(_uM([["fontSize", 14], ["color", "#FFFFFF"]]))]])]
