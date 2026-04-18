import _easycom_lili_universal_filter from '@/uni_modules/lili-universal-filter/components/lili-universal-filter/lili-universal-filter.uvue'
import _easycom_lili_UniversalList from '@/uni_modules/lili-UniversalList/components/lili-UniversalList/lili-UniversalList.uvue'
import { computed } from 'vue'
import { getSupplierList, SupplierItem, SupplierListResponse } from '@/pkg/api/modules/suppliers'


const __sfc__ = defineComponent({
  __name: 'index',
  setup(__props) {
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

const keyword = ref('')
const isLoading = ref(false)
const errorMessage = ref('')
const suppliers = ref<SupplierItem[]>([])
const currentPage = ref(1)
const totalPages = ref(1)
const totalCount = ref(0)
const pageSize = ref(20)

const fieldConfig = ref<UTSJSONObject[]>([
	{ key: 'contact', label: '联系人' } as UTSJSONObject,
	{ key: 'phone', label: '电话' } as UTSJSONObject,
	{ key: 'address', label: '地址' } as UTSJSONObject,
	{ key: 'arrears_amount', label: '欠款'} as UTSJSONObject,
	
])

const menuActions = ref<UTSJSONObject[]>([
	{ key: 'copy-phone', text: '复制电话' } as UTSJSONObject,
	{ key: 'reload', text: '刷新列表' } as UTSJSONObject,
])

function applySupplierResponse(response: SupplierListResponse) {
	suppliers.value = response.results
	currentPage.value = response.current_page
	totalPages.value = response.total_pages
	totalCount.value = response.total_count
	pageSize.value = response.page_size
}

function parseErrorMessage(error: any): string {
	let message = '供应商列表加载失败'
	if (error != null) {
		const errorText = JSON.stringify(error)
		if (errorText != null && errorText != '') {
			const parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/suppliers/index.uvue:117")
			if (parsedError != null) {
				const rawMessage = parsedError!['message']
				if (rawMessage != null) {
					const parsedMessage = rawMessage as string
					if (parsedMessage != '') {
						message = parsedMessage
					}
				}
			}
			if (message == '供应商列表加载失败') {
				message = errorText
			}
		}
	}
	return message
}

async function loadSuppliers() {
	if (isLoading.value) {
		return
	}

	isLoading.value = true
	errorMessage.value = ''

	try {
		const response = await getSupplierList({
			search: keyword.value == '' ? null : keyword.value,
			page: currentPage.value,
			page_size: pageSize.value,
		})
		applySupplierResponse(response)
	} catch (error) {
		suppliers.value = []
		currentPage.value = 1
		totalPages.value = 1
		totalCount.value = 0
		errorMessage.value = parseErrorMessage(error)
	} finally {
		isLoading.value = false
	}
}

function getDisplayText(value: string | null): string {
	if (value == null || value == '') {
		return '-'
	}
	return value
}

function buildImages(item: SupplierItem): string[] {
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

function getSupplierImage(item: SupplierItem): string {
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

function formatPaidAmount(value: number): string {
	return value.toFixed(2)
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

function buildTags(item: SupplierItem): string[] {
	const tags: string[] = []
	tags.push(item.is_active ? '启用' : '停用')
	if (item.files_count > 0) {
		tags.push('有附件')
	}
	if (item.arrears_amount != '0.00') {
		tags.push('有欠款')
	}
	return tags
}

function supplierToListItem(item: SupplierItem): UTSJSONObject {
	const cover = getSupplierImage(item)
	const images = buildImages(item)
	return {
		id: item.id.toString(),
		name: item.name,
		codeText: '编码：' + getDisplayText(item.code),
		contact: getDisplayText(item.contact),
		phone: getDisplayText(item.phone),
		address: getDisplayText(item.address),
		total_amount: item.total_amount,
		paid_amount_text: formatPaidAmount(item.paid_amount),
		arrears_amount: item.arrears_amount,
		updatedText: formatDateText(item.updated_at),
		filesText: item.files_count.toString() + ' 个',
		cover: cover,
		images: images,
		tags: buildTags(item),
		rawId: item.id.toString(),
	} as UTSJSONObject
}

function handleSearchInput(value: string) {
	keyword.value = value
}

function handleSearchConfirm(value: string) {
	keyword.value = value
	currentPage.value = 1
	loadSuppliers()
}

function handleSearchClear() {
	keyword.value = ''
	currentPage.value = 1
	loadSuppliers()
}

function handleReset() {
	keyword.value = ''
	currentPage.value = 1
	loadSuppliers()
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
	loadSuppliers()
}

function handleItemClick(payload: UTSJSONObject) {
	const name = payload['name']
	const text = name == null ? '供应商' : (name as string)
	uni.showToast({
		title: text,
		icon: 'none',
	})
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
	if (key == 'reload') {
		loadSuppliers()
		return
	}

	if (key == 'copy-phone') {
		const phone = itemObject['phone']
		const phoneText = phone == null ? '' : (phone as string)
		if (phoneText == '' || phoneText == '-') {
			uni.showToast({
				title: '暂无电话',
				icon: 'none',
			})
			return
		}

		uni.setClipboardData({
			data: phoneText,
			success: () => {
				uni.showToast({
					title: '电话已复制',
					icon: 'success',
				})
			},
		})
	}
}

const listItems = computed((): UTSJSONObject[] => {
	const result: UTSJSONObject[] = []
	for (let index = 0; index < suppliers.value.length; index += 1) {
		result.push(supplierToListItem(suppliers.value[index]))
	}
	return result
})

const supplierCountText = computed((): string => {
	return totalCount.value.toString()
})

const activeCountText = computed((): string => {
	let count = 0
	for (let index = 0; index < suppliers.value.length; index += 1) {
		if (suppliers.value[index].is_active) {
			count += 1
		}
	}
	return count.toString()
})

const withFileCountText = computed((): string => {
	let count = 0
	for (let index = 0; index < suppliers.value.length; index += 1) {
		if (suppliers.value[index].files_count > 0) {
			count += 1
		}
	}
	return count.toString()
})

const emptyText = computed((): string => {
	if (isLoading.value) {
		return '正在加载'
	}
	if (keyword.value != '') {
		return '没有匹配的供应商'
	}
	return '暂无供应商'
})

onLoad(() => {
	loadSuppliers()
})

return (): any | null => {

const _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter",_easycom_lili_universal_filter)
const _component_lili_UniversalList = resolveEasyComponent("lili-UniversalList",_easycom_lili_UniversalList)

  return _cE("view", _uM({ class: "page" }), [
    _cV(_component_lili_universal_filter, _uM({
      title: "供应商",
      searchPlaceholder: "输入供应商名称、编码、联系人",
      searchValue: unref(keyword),
      showBack: true,
      showSearch: true,
      showFilter: true,
      showHome: true,
      filterActive: unref(keyword) != '',
      filterText: "重置",
      homePath: "/pages/tabbar/products",
      onSearchInput: handleSearchInput,
      onSearchConfirm: handleSearchConfirm,
      onSearchClear: handleSearchClear,
      onFilter: handleReset
    }), null, 8 /* PROPS */, ["searchValue", "filterActive"]),
    _cE("scroll-view", _uM({
      style: _nS(_uM({"flex":"1"})),
      class: "page-scroll"
    }), [
      _cE("view", _uM({ class: "page-content" }), [
        _cE("view", _uM({ class: "summary-card" }), [
          _cE("view", _uM({ class: "summary-item summary-item-gap" }), [
            _cE("text", _uM({ class: "summary-value" }), _tD(supplierCountText.value), 1 /* TEXT */),
            _cE("text", _uM({ class: "summary-label" }), "当前结果")
          ]),
          _cE("view", _uM({ class: "summary-item summary-item-gap" }), [
            _cE("text", _uM({ class: "summary-value" }), _tD(activeCountText.value), 1 /* TEXT */),
            _cE("text", _uM({ class: "summary-label" }), "启用中")
          ]),
          _cE("view", _uM({ class: "summary-item" }), [
            _cE("text", _uM({ class: "summary-value" }), _tD(withFileCountText.value), 1 /* TEXT */),
            _cE("text", _uM({ class: "summary-label" }), "含附件")
          ])
        ]),
        isTrue(unref(errorMessage) != '' && !unref(isLoading))
          ? _cE("view", _uM({
              key: 0,
              class: "error-card"
            }), [
              _cE("text", _uM({ class: "error-title" }), "加载失败"),
              _cE("text", _uM({ class: "error-desc" }), _tD(unref(errorMessage)), 1 /* TEXT */),
              _cE("button", _uM({
                class: "retry-btn",
                onClick: loadSuppliers
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
          imageField: "cover",
          imageListField: "images",
          tagField: "tags",
          fields: unref(fieldConfig),
          loading: unref(isLoading),
          loadingText: "正在加载供应商",
          emptyText: emptyText.value,
          emptyIcon: "◎",
          showMenu: true,
          menuActions: unref(menuActions),
          showChevron: false,
          showPagination: true,
          currentPage: unref(currentPage),
          totalPages: unref(totalPages),
          totalCount: unref(totalCount),
          onItemClick: handleItemClick,
          onMenu: handleMenu,
          onPageChange: handlePageChange
        }), null, 8 /* PROPS */, ["items", "fields", "loading", "emptyText", "menuActions", "currentPage", "totalPages", "totalCount"])
      ])
    ], 4 /* STYLE */)
  ])
}
}

})
export default __sfc__
const GenPagesSuppliersIndexStyles = [_uM([["page", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#F6F7FB"]]))], ["page-scroll", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#F6F7FB"]]))], ["page-content", _pS(_uM([["paddingLeft", 16], ["paddingRight", 16], ["paddingTop", 12], ["paddingBottom", 24]]))], ["summary-card", _pS(_uM([["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", 18], ["borderTopRightRadius", 18], ["borderBottomRightRadius", 18], ["borderBottomLeftRadius", 18], ["paddingTop", 16], ["paddingRight", 16], ["paddingBottom", 16], ["paddingLeft", 16], ["marginBottom", 14], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E5E7EB"], ["borderRightColor", "#E5E7EB"], ["borderBottomColor", "#E5E7EB"], ["borderLeftColor", "#E5E7EB"], ["flexDirection", "row"]]))], ["summary-item", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"]]))], ["summary-item-gap", _pS(_uM([["marginRight", 10]]))], ["summary-value", _pS(_uM([["fontSize", 22], ["lineHeight", "28px"], ["color", "#0F172A"], ["fontWeight", "bold"]]))], ["summary-label", _pS(_uM([["fontSize", 12], ["lineHeight", "18px"], ["color", "#94A3B8"], ["marginTop", 6]]))], ["error-card", _pS(_uM([["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", 18], ["borderTopRightRadius", 18], ["borderBottomRightRadius", 18], ["borderBottomLeftRadius", 18], ["paddingTop", 18], ["paddingRight", 18], ["paddingBottom", 18], ["paddingLeft", 18], ["marginBottom", 14], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#FECACA"], ["borderRightColor", "#FECACA"], ["borderBottomColor", "#FECACA"], ["borderLeftColor", "#FECACA"], ["alignItems", "center"]]))], ["error-title", _pS(_uM([["fontSize", 18], ["lineHeight", "24px"], ["color", "#B42318"], ["fontWeight", "bold"]]))], ["error-desc", _pS(_uM([["fontSize", 14], ["lineHeight", "20px"], ["color", "#7F1D1D"], ["marginTop", 8], ["textAlign", "center"]]))], ["retry-btn", _pS(_uM([["marginTop", 14], ["height", 40], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["backgroundColor", "#0F172A"], ["paddingLeft", 18], ["paddingRight", 18]]))], ["retry-btn-text", _pS(_uM([["fontSize", 14], ["color", "#FFFFFF"]]))]])]
