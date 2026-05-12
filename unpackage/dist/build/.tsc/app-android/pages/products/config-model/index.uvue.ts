import _easycom_lili_universal_filter from '@/uni_modules/lili-universal-filter/components/lili-universal-filter/lili-universal-filter.uvue'
import _easycom_lili_UniversalList from '@/uni_modules/lili-UniversalList/components/lili-UniversalList/lili-UniversalList.uvue'
import { computed } from 'vue'
import {
	attributeTypesPath,
	attributeValuesPath,
	barcodeSequencesPath,
	deleteProductConfig,
	getProductConfigList,
	productDiscountsPath,
} from '@/pkg/api/modules/products.uts'
import { takeLatestResponseMessage } from '@/pkg/api/index.uts'


const __sfc__ = defineComponent({
  __name: 'index',
  setup(__props) {
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

const resource = ref('discount')
const parentAttributeTypeId = ref('')
const parentAttributeTypeName = ref('')
const keyword = ref('')
const filterVisible = ref(false)
const isLoading = ref(false)
const errorMessage = ref('')
const items = ref<UTSJSONObject[]>([])
const currentPage = ref(1)
const totalPages = ref(1)
const totalCount = ref(0)
const pageSize = ref(20)
const statusFilter = ref<string | null>(null)
const discountTypeFilter = ref<string | null>(null)
const selectedStatus = ref<string | null>(null)
const selectedDiscountType = ref<string | null>(null)

const menuActions = computed((): UTSJSONObject[] => {
	if (resource.value == 'attribute-type') {
		return [
			{ key: 'values', text: '属性值' } as UTSJSONObject,
			{ key: 'edit', text: '编辑' } as UTSJSONObject,
			{ key: 'delete', text: '删除' } as UTSJSONObject,
			{ key: 'reload', text: '刷新' } as UTSJSONObject,
		]
	}
	return [
		{ key: 'edit', text: '编辑' } as UTSJSONObject,
		{ key: 'delete', text: '删除' } as UTSJSONObject,
		{ key: 'reload', text: '刷新' } as UTSJSONObject,
	]
})

function getStringField(obj: UTSJSONObject, key: string, fallback: string = ''): string {
	const value = obj[key]
	if (value == null) return fallback
	const text = '' + value
	return text == '' ? fallback : text
}

function getNumberField(obj: UTSJSONObject, key: string): number {
	const value = obj[key]
	if (value == null) return 0
	const parsed = parseInt('' + value)
	if (isNaN(parsed)) return 0
	return parsed
}

function getBoolField(obj: UTSJSONObject, key: string): boolean {
	const text = getStringField(obj, key).toLowerCase()
	return text == 'true' || text == '1' || text == 'yes'
}

function resourceBasePath(): string {
	if (resource.value == 'attribute-type') return attributeTypesPath()
	if (resource.value == 'attribute-value') return attributeValuesPath()
	if (resource.value == 'barcode-sequence') return barcodeSequencesPath()
	return productDiscountsPath()
}

function resourceTitle(): string {
	if (resource.value == 'attribute-type') return '属性类型'
	if (resource.value == 'attribute-value') {
		if (parentAttributeTypeName.value != '') return parentAttributeTypeName.value + '属性值'
		return '属性值'
	}
	if (resource.value == 'barcode-sequence') return '条形码序列'
	return '商品折扣'
}

function refreshStorageKey(): string {
	return 'refresh:pages:products:config-model:' + resource.value
}

function openForm(id: string) {
	let url = '/pages/products/config-model/from?resource=' + resource.value
	if (resource.value == 'attribute-value' && parentAttributeTypeId.value != '') {
		url = url + '&attribute_type=' + parentAttributeTypeId.value + '&attribute_type_name=' + parentAttributeTypeName.value
	}
	if (id != '') {
		url = url + '&id=' + id
	}
	uni.navigateTo({ url: url })
}

function parseErrorMessage(error: any, fallback: string): string {
	let message = fallback
	if (error != null) {
		const directMessage = (error as Error).message
		if (directMessage != null && directMessage != '') message = directMessage
	}
	return message
}

function applyListResponse(response: UTSJSONObject) {
	const rawResults = response['results']
	if (rawResults == null) {
		items.value = [] as UTSJSONObject[]
	} else {
		items.value = rawResults as UTSJSONObject[]
	}
	totalCount.value = getNumberField(response, 'total_count')
	totalPages.value = getNumberField(response, 'total_pages')
	currentPage.value = getNumberField(response, 'current_page')
	pageSize.value = getNumberField(response, 'page_size')
	if (totalPages.value <= 0) totalPages.value = 1
	if (currentPage.value <= 0) currentPage.value = 1
	if (pageSize.value <= 0) pageSize.value = 20
}

async function loadItems() {
	if (isLoading.value) return
	isLoading.value = true
	errorMessage.value = ''
	try {
		const extra = {} as UTSJSONObject
		if (resource.value == 'attribute-value' && parentAttributeTypeId.value != '') {
			extra['attribute_type'] = parentAttributeTypeId.value
		}
		if (resource.value == 'discount') {
			if (statusFilter.value != null && statusFilter.value != '') extra['status'] = statusFilter.value
			if (discountTypeFilter.value != null && discountTypeFilter.value != '') extra['discount_type'] = discountTypeFilter.value
		}
		const response = await getProductConfigList(resourceBasePath(), keyword.value == '' ? null : keyword.value, currentPage.value, pageSize.value, extra)
		applyListResponse(response)
	} catch (error) {
		items.value = [] as UTSJSONObject[]
		totalCount.value = 0
		totalPages.value = 1
		currentPage.value = 1
		errorMessage.value = parseErrorMessage(error, resourceTitle() + '加载失败')
	} finally {
		isLoading.value = false
	}
}

function formatDateText(value: string): string {
	if (value == '') return '-'
	if (value.length >= 16) return value.substring(0, 16)
	return value
}

function discountName(item: UTSJSONObject): string {
	return getStringField(item, 'name', '未命名折扣')
}

function itemToListItem(item: UTSJSONObject): UTSJSONObject {
	if (resource.value == 'discount') {
		return {
			id: getStringField(item, 'id'),
			rawId: getStringField(item, 'id'),
			name: discountName(item),
			subtitle: getStringField(item, 'discount_display', '-'),
			meta: getStringField(item, 'status', '-'),
			min_quantity: getStringField(item, 'min_quantity', '-'),
			priority: getStringField(item, 'priority', '-'),
			updated: formatDateText(getStringField(item, 'updated_at')),
		} as UTSJSONObject
	}
	if (resource.value == 'attribute-value') {
		return {
			id: getStringField(item, 'id'),
			rawId: getStringField(item, 'id'),
			name: getStringField(item, 'value', '未命名属性值'),
			subtitle: '编码：' + getStringField(item, 'code', '-'),
			meta: getStringField(item, 'attribute_type_name', '-'),
			value_en: getStringField(item, 'value_en', '-'),
			color_hex: getStringField(item, 'color_hex', '-'),
			display_order: getStringField(item, 'display_order', '0'),
		} as UTSJSONObject
	}
	if (resource.value == 'barcode-sequence') {
		return {
			id: getStringField(item, 'id'),
			rawId: getStringField(item, 'id'),
			name: getStringField(item, 'sequence_name', '未命名序列'),
			subtitle: '前缀：' + getStringField(item, 'prefix', '-'),
			meta: '下一个：' + getStringField(item, 'current_number', '0'),
			min_number: getStringField(item, 'min_number', '-'),
			max_number: getStringField(item, 'max_number', '-'),
			description: getStringField(item, 'description', '-'),
		} as UTSJSONObject
	}
	return {
		id: getStringField(item, 'id'),
		rawId: getStringField(item, 'id'),
		name: getStringField(item, 'name', '未命名属性类型'),
		subtitle: '编码：' + getStringField(item, 'code', '-'),
		meta: '值数量：' + getStringField(item, 'values_count', '0'),
		name_en: getStringField(item, 'name_en', '-'),
		display_order: getStringField(item, 'display_order', '0'),
		description: getStringField(item, 'description', '-'),
	} as UTSJSONObject
}

function handleSearchInput(value: string) {
	keyword.value = value
}

function handleSearchConfirm(value: string) {
	keyword.value = value
	currentPage.value = 1
	loadItems()
}

function handleSearchClear() {
	keyword.value = ''
	currentPage.value = 1
	loadItems()
}

function handleFilterVisibleChange(value: boolean) { filterVisible.value = value }
function handleFilterOpen() {
	selectedStatus.value = statusFilter.value
	selectedDiscountType.value = discountTypeFilter.value
}
function selectStatus(value: string | null) { selectedStatus.value = value }
function selectDiscountType(value: string | null) { selectedDiscountType.value = value }
function handleFilterReset() {
	selectedStatus.value = null
	selectedDiscountType.value = null
	statusFilter.value = null
	discountTypeFilter.value = null
	currentPage.value = 1
	filterVisible.value = false
	loadItems()
}
function applyFilter() {
	statusFilter.value = selectedStatus.value
	discountTypeFilter.value = selectedDiscountType.value
	currentPage.value = 1
	filterVisible.value = false
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

function openAttributeValues(item: UTSJSONObject) {
	const typeId = getStringField(item, 'rawId')
	const typeName = getStringField(item, 'name')
	if (typeId == '') return
	uni.navigateTo({
		url: '/pages/products/config-model/index?resource=attribute-value&attribute_type=' + typeId + '&attribute_type_name=' + typeName,
	})
}

function handleItemClick(payload: UTSJSONObject) {
	const item = payload['item']
	if (item == null) return
	const itemObject = item as UTSJSONObject
	if (resource.value == 'attribute-type') {
		openAttributeValues(itemObject)
		return
	}
	openForm(getStringField(itemObject, 'rawId'))
}

function handleFieldClick(payload: UTSJSONObject) {
}

async function confirmDelete(id: string) {
	try {
		await deleteProductConfig(resourceBasePath(), id)
		uni.showToast({ title: takeLatestResponseMessage('删除成功'), icon: 'success' })
		loadItems()
	} catch (error) {
		uni.showToast({ title: parseErrorMessage(error, '删除失败'), icon: 'none' })
	}
}

function handleMenu(payload: UTSJSONObject) {
	const action = payload['action']
	const item = payload['item']
	if (action == null || item == null) return
	const key = getStringField(action as UTSJSONObject, 'key')
	const itemObject = item as UTSJSONObject
	const id = getStringField(itemObject, 'rawId')
	if (key == 'values') {
		openAttributeValues(itemObject)
		return
	}
	if (key == 'edit') {
		openForm(id)
		return
	}
	if (key == 'delete') {
		uni.showModal({
			title: '删除' + resourceTitle(),
			content: '确定删除这条配置吗？',
			success: (res) => {
				if (res.confirm) confirmDelete(id)
			},
		})
		return
	}
	if (key == 'reload') {
		loadItems()
	}
}

function handleCreate() {
	openForm('')
}

function consumeRefreshNeeded(): boolean {
	const storedValue = uni.getStorageSync(refreshStorageKey())
	if (storedValue == null) return false
	const storedText = '' + storedValue
	if (storedText == '') return false
	uni.removeStorageSync(refreshStorageKey())
	return true
}

const pageTitle = computed((): string => {
	return resourceTitle()
})

const searchPlaceholder = computed((): string => {
	return '搜索' + resourceTitle()
})

const showFilter = computed((): boolean => {
	return resource.value == 'discount'
})

const hasActiveFilter = computed((): boolean => {
	return statusFilter.value != null || discountTypeFilter.value != null
})

const loadingText = computed((): string => {
	return '正在加载' + resourceTitle()
})

const inlineLoadingText = computed((): string => {
	return resourceTitle() + '刷新中...'
})

const emptyText = computed((): string => {
	if (isLoading.value) return '正在加载'
	if (keyword.value != '') return '没有匹配的' + resourceTitle()
	return '暂无' + resourceTitle()
})

const fieldConfig = computed((): UTSJSONObject[] => {
	if (resource.value == 'discount') {
		return [
			{ key: 'min_quantity', label: '最低数量' } as UTSJSONObject,
			{ key: 'priority', label: '优先级' } as UTSJSONObject,
			{ key: 'updated', label: '更新' } as UTSJSONObject,
		]
	}
	if (resource.value == 'attribute-value') {
		return [
			{ key: 'value_en', label: '英文' } as UTSJSONObject,
			{ key: 'color_hex', label: '颜色' } as UTSJSONObject,
			{ key: 'display_order', label: '排序' } as UTSJSONObject,
		]
	}
	if (resource.value == 'barcode-sequence') {
		return [
			{ key: 'min_number', label: '最小' } as UTSJSONObject,
			{ key: 'max_number', label: '最大' } as UTSJSONObject,
			{ key: 'description', label: '说明' } as UTSJSONObject,
		]
	}
	return [
		{ key: 'name_en', label: '英文' } as UTSJSONObject,
		{ key: 'display_order', label: '排序' } as UTSJSONObject,
		{ key: 'description', label: '说明' } as UTSJSONObject,
	]
})

const listItems = computed((): UTSJSONObject[] => {
	const result: UTSJSONObject[] = []
	for (let index = 0; index < items.value.length; index += 1) {
		result.push(itemToListItem(items.value[index]))
	}
	return result
})

onLoad((event: OnLoadOptions) => {
	const resourceValue = event['resource']
	resource.value = resourceValue == null ? 'discount' : (resourceValue as string)
	const attributeTypeValue = event['attribute_type']
	parentAttributeTypeId.value = attributeTypeValue == null ? '' : (attributeTypeValue as string)
	const attributeTypeNameValue = event['attribute_type_name']
	parentAttributeTypeName.value = attributeTypeNameValue == null ? '' : (attributeTypeNameValue as string)
	loadItems()
})

onShow(() => {
	if (consumeRefreshNeeded()) {
		loadItems()
	}
})

return (): any | null => {

const _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter",_easycom_lili_universal_filter)
const _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList",_easycom_lili_UniversalList)

  return _cE("view", _uM({ class: "page" }), [
    _cV(_component_lili_universal_filter, _uM({
      title: pageTitle.value,
      searchPlaceholder: searchPlaceholder.value,
      searchValue: unref(keyword),
      filterVisible: unref(filterVisible),
      showBack: true,
      showSearch: true,
      showFilter: showFilter.value,
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
        _cE("view", _uM({ class: "config-filter-panel" }), [
          _cE("view", _uM({ class: "config-filter-actions" }), [
            _cE("view", _uM({
              class: "config-filter-btn config-filter-btn-light",
              onClick: handleFilterReset
            }), [
              _cE("text", _uM({ class: "config-filter-btn-light-text" }), "重置")
            ]),
            _cE("view", _uM({
              class: "config-filter-btn config-filter-btn-primary",
              onClick: applyFilter
            }), [
              _cE("text", _uM({ class: "config-filter-btn-primary-text" }), "应用")
            ])
          ]),
          _cE("view", _uM({ class: "config-filter-group" }), [
            _cE("text", _uM({ class: "config-filter-title" }), "折扣状态"),
            _cE("view", _uM({ class: "config-filter-options" }), [
              _cE("view", _uM({
                class: _nC(unref(selectedStatus) == null ? 'config-filter-option config-filter-option-active' : 'config-filter-option'),
                onClick: () => {selectStatus(null)}
              }), [
                _cE("text", _uM({
                  class: _nC(unref(selectedStatus) == null ? 'config-filter-option-text config-filter-option-text-active' : 'config-filter-option-text')
                }), "全部", 2 /* CLASS */)
              ], 10 /* CLASS, PROPS */, ["onClick"]),
              _cE("view", _uM({
                class: _nC(unref(selectedStatus) == 'DRAFT' ? 'config-filter-option config-filter-option-active' : 'config-filter-option'),
                onClick: () => {selectStatus('DRAFT')}
              }), [
                _cE("text", _uM({
                  class: _nC(unref(selectedStatus) == 'DRAFT' ? 'config-filter-option-text config-filter-option-text-active' : 'config-filter-option-text')
                }), "草稿", 2 /* CLASS */)
              ], 10 /* CLASS, PROPS */, ["onClick"]),
              _cE("view", _uM({
                class: _nC(unref(selectedStatus) == 'ACTIVE' ? 'config-filter-option config-filter-option-active' : 'config-filter-option'),
                onClick: () => {selectStatus('ACTIVE')}
              }), [
                _cE("text", _uM({
                  class: _nC(unref(selectedStatus) == 'ACTIVE' ? 'config-filter-option-text config-filter-option-text-active' : 'config-filter-option-text')
                }), "启用", 2 /* CLASS */)
              ], 10 /* CLASS, PROPS */, ["onClick"]),
              _cE("view", _uM({
                class: _nC(unref(selectedStatus) == 'INACTIVE' ? 'config-filter-option config-filter-option-active' : 'config-filter-option'),
                onClick: () => {selectStatus('INACTIVE')}
              }), [
                _cE("text", _uM({
                  class: _nC(unref(selectedStatus) == 'INACTIVE' ? 'config-filter-option-text config-filter-option-text-active' : 'config-filter-option-text')
                }), "停用", 2 /* CLASS */)
              ], 10 /* CLASS, PROPS */, ["onClick"]),
              _cE("view", _uM({
                class: _nC(unref(selectedStatus) == 'EXPIRED' ? 'config-filter-option config-filter-option-active' : 'config-filter-option'),
                onClick: () => {selectStatus('EXPIRED')}
              }), [
                _cE("text", _uM({
                  class: _nC(unref(selectedStatus) == 'EXPIRED' ? 'config-filter-option-text config-filter-option-text-active' : 'config-filter-option-text')
                }), "过期", 2 /* CLASS */)
              ], 10 /* CLASS, PROPS */, ["onClick"])
            ])
          ]),
          _cE("view", _uM({ class: "config-filter-group" }), [
            _cE("text", _uM({ class: "config-filter-title" }), "折扣类型"),
            _cE("view", _uM({ class: "config-filter-options" }), [
              _cE("view", _uM({
                class: _nC(unref(selectedDiscountType) == null ? 'config-filter-option config-filter-option-active' : 'config-filter-option'),
                onClick: () => {selectDiscountType(null)}
              }), [
                _cE("text", _uM({
                  class: _nC(unref(selectedDiscountType) == null ? 'config-filter-option-text config-filter-option-text-active' : 'config-filter-option-text')
                }), "全部", 2 /* CLASS */)
              ], 10 /* CLASS, PROPS */, ["onClick"]),
              _cE("view", _uM({
                class: _nC(unref(selectedDiscountType) == 'PERCENTAGE' ? 'config-filter-option config-filter-option-active' : 'config-filter-option'),
                onClick: () => {selectDiscountType('PERCENTAGE')}
              }), [
                _cE("text", _uM({
                  class: _nC(unref(selectedDiscountType) == 'PERCENTAGE' ? 'config-filter-option-text config-filter-option-text-active' : 'config-filter-option-text')
                }), "百分比", 2 /* CLASS */)
              ], 10 /* CLASS, PROPS */, ["onClick"]),
              _cE("view", _uM({
                class: _nC(unref(selectedDiscountType) == 'FIXED_AMOUNT' ? 'config-filter-option config-filter-option-active' : 'config-filter-option'),
                onClick: () => {selectDiscountType('FIXED_AMOUNT')}
              }), [
                _cE("text", _uM({
                  class: _nC(unref(selectedDiscountType) == 'FIXED_AMOUNT' ? 'config-filter-option-text config-filter-option-text-active' : 'config-filter-option-text')
                }), "固定金额", 2 /* CLASS */)
              ], 10 /* CLASS, PROPS */, ["onClick"])
            ])
          ])
        ])
      ]),
      _: 1 /* STABLE */
    }), 8 /* PROPS */, ["title", "searchPlaceholder", "searchValue", "filterVisible", "showFilter", "filterActive"]),
    _cE("scroll-view", _uM({
      class: "page-scroll",
      "scroll-y": "true"
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
          fields: fieldConfig.value,
          loading: unref(isLoading),
          loadingText: loadingText.value,
          keepContentOnLoading: true,
          inlineLoadingText: inlineLoadingText.value,
          emptyText: emptyText.value,
          emptyIcon: "◎",
          showMenu: true,
          menuActions: menuActions.value,
          showChevron: false,
          showPagination: true,
          currentPage: unref(currentPage),
          totalPages: unref(totalPages),
          totalCount: unref(totalCount),
          showFloatingAdd: true,
          floatingAddText: "新增",
          onItemClick: handleItemClick,
          onFieldClick: handleFieldClick,
          onMenu: handleMenu,
          onPageChange: handlePageChange,
          onFloatingAdd: handleCreate
        }), null, 8 /* PROPS */, ["items", "fields", "loading", "loadingText", "inlineLoadingText", "emptyText", "menuActions", "currentPage", "totalPages", "totalCount"])
      ])
    ])
  ])
}
}

})
export default __sfc__
const GenPagesProductsConfigModelIndexStyles = [_uM([["page", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#F6F7FB"]]))], ["page-scroll", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#F6F7FB"]]))], ["page-content", _pS(_uM([["paddingLeft", 6], ["paddingRight", 6], ["paddingTop", 6], ["paddingBottom", 96]]))], ["config-filter-panel", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#FFFFFF"], ["paddingTop", 14], ["paddingRight", 14], ["paddingBottom", 14], ["paddingLeft", 14]]))], ["config-filter-actions", _pS(_uM([["flexDirection", "row"], ["justifyContent", "flex-end"], ["marginBottom", 12]]))], ["config-filter-btn", _pS(_uM([["height", 38], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["paddingLeft", 18], ["paddingRight", 18], ["alignItems", "center"], ["justifyContent", "center"], ["marginLeft", 8]]))], ["config-filter-btn-light", _pS(_uM([["backgroundColor", "#F8FAFC"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#CBD5E1"], ["borderRightColor", "#CBD5E1"], ["borderBottomColor", "#CBD5E1"], ["borderLeftColor", "#CBD5E1"]]))], ["config-filter-btn-primary", _pS(_uM([["backgroundColor", "#0F172A"]]))], ["config-filter-btn-light-text", _pS(_uM([["fontSize", 14], ["color", "#334155"]]))], ["config-filter-btn-primary-text", _pS(_uM([["fontSize", 14], ["color", "#FFFFFF"]]))], ["config-filter-group", _pS(_uM([["marginTop", 10]]))], ["config-filter-title", _pS(_uM([["fontSize", 14], ["lineHeight", "20px"], ["color", "#0F172A"], ["fontWeight", "bold"]]))], ["config-filter-options", _pS(_uM([["flexDirection", "row"], ["flexWrap", "wrap"], ["marginTop", 10]]))], ["config-filter-option", _pS(_uM([["height", 34], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["paddingLeft", 12], ["paddingRight", 12], ["backgroundColor", "#F8FAFC"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E2E8F0"], ["borderRightColor", "#E2E8F0"], ["borderBottomColor", "#E2E8F0"], ["borderLeftColor", "#E2E8F0"], ["alignItems", "center"], ["justifyContent", "center"], ["marginRight", 8], ["marginBottom", 8]]))], ["config-filter-option-active", _pS(_uM([["backgroundColor", "#0F172A"], ["borderTopColor", "#0F172A"], ["borderRightColor", "#0F172A"], ["borderBottomColor", "#0F172A"], ["borderLeftColor", "#0F172A"]]))], ["config-filter-option-text", _pS(_uM([["fontSize", 13], ["color", "#475569"]]))], ["config-filter-option-text-active", _pS(_uM([["color", "#FFFFFF"]]))], ["error-card", _pS(_uM([["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["paddingTop", 18], ["paddingRight", 18], ["paddingBottom", 18], ["paddingLeft", 18], ["marginBottom", 14], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#FECACA"], ["borderRightColor", "#FECACA"], ["borderBottomColor", "#FECACA"], ["borderLeftColor", "#FECACA"], ["alignItems", "center"]]))], ["error-title", _pS(_uM([["fontSize", 18], ["lineHeight", "24px"], ["color", "#B42318"], ["fontWeight", "bold"]]))], ["error-desc", _pS(_uM([["fontSize", 14], ["lineHeight", "20px"], ["color", "#7F1D1D"], ["marginTop", 8], ["textAlign", "center"]]))], ["retry-btn", _pS(_uM([["marginTop", 14], ["height", 40], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#0F172A"], ["paddingLeft", 18], ["paddingRight", 18], ["alignItems", "center"], ["justifyContent", "center"]]))], ["retry-btn-text", _pS(_uM([["fontSize", 14], ["lineHeight", "14px"], ["color", "#FFFFFF"]]))]])]
