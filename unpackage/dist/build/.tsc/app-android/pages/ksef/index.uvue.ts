import _easycom_lili_universal_filter from '@/uni_modules/lili-universal-filter/components/lili-universal-filter/lili-universal-filter.uvue'
import _easycom_lili_UniversalList from '@/uni_modules/lili-UniversalList/components/lili-UniversalList/lili-UniversalList.uvue'
import { computed } from 'vue'
import { takeLatestResponseMessage } from '@/pkg/api/index.uts'
import { downloadKsefInvoiceXml, enqueueKsefAutoSync, getKsefAutoSyncStatus, getKsefInvoiceList, KsefAutoSyncStatus, KsefInvoiceItem, KsefInvoiceListResponse } from '@/pkg/api/modules/ksef'


const __sfc__ = defineComponent({
  __name: 'index',
  setup(__props) {
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

const keyword = ref('')
const filterVisible = ref(false)
const isLoading = ref(false)
const errorMessage = ref('')
const statusError = ref('')
const invoices = ref<KsefInvoiceItem[]>([])
const status = ref<KsefAutoSyncStatus | null>(null)
const currentPage = ref(1)
const totalPages = ref(1)
const totalCount = ref(0)
const pageSize = ref(20)
const selectedSyncStatus = ref<string | null>(null)

const fieldConfig = ref<UTSJSONObject[]>([
	{ key: 'seller_nip', label: '卖方NIP' } as UTSJSONObject,
	{ key: 'gross_amount_text', label: '金额' } as UTSJSONObject,
	{ key: 'sync_status_text', label: '详情状态' } as UTSJSONObject,
])

const menuActions = ref<UTSJSONObject[]>([
	{ key: 'download_xml', text: '同步详情' } as UTSJSONObject,
	{ key: 'copy_ksef', text: '复制KSeF号' } as UTSJSONObject,
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
			sync_status: selectedSyncStatus.value,
			is_paid: null,
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
	statusError.value = ''
	try {
		status.value = await getKsefAutoSyncStatus()
	} catch (error) {
		status.value = null
		statusError.value = parseErrorMessage(error, '自动同步状态加载失败')
	}
}

function handleFilterVisibleChange(value: boolean) {
	filterVisible.value = value
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

function selectSyncStatus(value: string | null) {
	selectedSyncStatus.value = value
}

function handleFilterReset() {
	selectedSyncStatus.value = null
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

function statusText(value: string): string {
	if (value == 'XML_DOWNLOADED') return '已同步详情'
	if (value == 'SYNC_ERROR') return '同步异常'
	return '待同步详情'
}

function statusTag(value: string): string {
	if (value == 'XML_DOWNLOADED') return '已完成'
	if (value == 'SYNC_ERROR') return '异常'
	return '待详情'
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

function invoiceToListItem(item: KsefInvoiceItem): UTSJSONObject {
	return {
		id: item.id.toString(),
		name: displayText(item.invoice_number),
		codeText: 'KSeF：' + displayText(item.ksef_number),
		metaText: compactDate(item.issue_date),
		seller_name: displayText(item.seller_name),
		seller_nip: displayText(item.seller_nip),
		gross_amount_text: displayText(item.gross_amount) + ' ' + displayText(item.currency),
		sync_status_text: statusText(item.sync_status),
		rawId: item.id.toString(),
		rawKsefNumber: item.ksef_number,
		tags: [statusTag(item.sync_status)] as string[],
	} as UTSJSONObject
}

function copyText(text: string, successTitle: string, emptyTitle: string) {
	if (text == '' || text == '-') {
		uni.showToast({ title: emptyTitle, icon: 'none' })
		return
	}
	uni.setClipboardData({
		data: text,
		success: () => {
			uni.showToast({ title: successTitle, icon: 'success' })
		},
	})
}

async function handleEnqueueAutoSync() {
	try {
		await enqueueKsefAutoSync()
		uni.showToast({ title: takeLatestResponseMessage('已加入队列'), icon: 'success' })
		loadAutoSyncStatus()
	} catch (error) {
		uni.showToast({ title: parseErrorMessage(error, '任务加入队列失败'), icon: 'none' })
	}
}

async function handleDownloadXml(invoiceId: string) {
	try {
		await downloadKsefInvoiceXml(invoiceId)
		uni.showToast({ title: takeLatestResponseMessage('详情同步完成'), icon: 'success' })
		loadInvoices()
		loadAutoSyncStatus()
	} catch (error) {
		uni.showToast({ title: parseErrorMessage(error, '详情同步失败'), icon: 'none' })
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
	const keyValue = actionObject['key']
	if (keyValue == null) {
		return
	}
	const key = keyValue as string
	const idValue = itemObject['rawId']
	const invoiceId = idValue == null ? '' : (idValue as string)
	if (key == 'download_xml') {
		handleDownloadXml(invoiceId)
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
	if (key == 'seller_nip') {
		const value = itemObject['seller_nip']
		const text = value == null ? '' : (value as string)
		copyText(text, '卖方NIP已复制', '暂无卖方NIP')
	}
}

const listItems = computed((): UTSJSONObject[] => {
	const result: UTSJSONObject[] = []
	for (let index = 0; index < invoices.value.length; index += 1) {
		result.push(invoiceToListItem(invoices.value[index]))
	}
	return result
})

const hasActiveFilter = computed((): boolean => {
	return keyword.value != '' || selectedSyncStatus.value != null
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

const batchLimitText = computed((): string => {
	if (status.value == null) return '-'
	return status.value.xml_batch_size.toString() + '张/' + status.value.xml_delay_seconds.toString() + '秒'
})

const lastSuccessText = computed((): string => {
	if (status.value == null) return '-'
	return compactDate(status.value.last_success_at)
})

const autoSyncSubtitle = computed((): string => {
	if (status.value == null) return '读取自动同步配置中'
	if (!status.value.enabled) return '自动同步已关闭'
	return '每 5 小时增量同步，详情分批自动回填'
})

const summaryItems = computed((): UTSJSONObject[] => {
	return [
		{ key: 'total', label: '发票总数', value: totalCount.value.toString() } as UTSJSONObject,
		{ key: 'pending', label: '待详情', value: pendingXmlText.value } as UTSJSONObject,
		{ key: 'last', label: '最近成功', value: lastSuccessText.value } as UTSJSONObject,
	]
})

onLoad(() => {
	loadAutoSyncStatus()
	loadInvoices()
})

onShow(() => {
	loadAutoSyncStatus()
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
      "onUpdate:filterVisible": handleFilterVisibleChange
    }), _uM({
      "filter-panel": withSlotCtx((): any[] => [
        _cE("view", _uM({ class: "filter-panel" }), [
          _cE("text", _uM({ class: "filter-title" }), "同步状态"),
          _cE("view", _uM({ class: "filter-row" }), [
            _cE("view", _uM({
              class: _nC(unref(selectedSyncStatus) == null ? 'filter-chip filter-chip-active' : 'filter-chip'),
              onClick: () => {selectSyncStatus(null)}
            }), [
              _cE("text", _uM({
                class: _nC(unref(selectedSyncStatus) == null ? 'filter-chip-text filter-chip-text-active' : 'filter-chip-text')
              }), "全部", 2 /* CLASS */)
            ], 10 /* CLASS, PROPS */, ["onClick"]),
            _cE("view", _uM({
              class: _nC(unref(selectedSyncStatus) == 'METADATA_ONLY' ? 'filter-chip filter-chip-active' : 'filter-chip'),
              onClick: () => {selectSyncStatus('METADATA_ONLY')}
            }), [
              _cE("text", _uM({
                class: _nC(unref(selectedSyncStatus) == 'METADATA_ONLY' ? 'filter-chip-text filter-chip-text-active' : 'filter-chip-text')
              }), "待详情", 2 /* CLASS */)
            ], 10 /* CLASS, PROPS */, ["onClick"]),
            _cE("view", _uM({
              class: _nC(unref(selectedSyncStatus) == 'XML_DOWNLOADED' ? 'filter-chip filter-chip-active' : 'filter-chip'),
              onClick: () => {selectSyncStatus('XML_DOWNLOADED')}
            }), [
              _cE("text", _uM({
                class: _nC(unref(selectedSyncStatus) == 'XML_DOWNLOADED' ? 'filter-chip-text filter-chip-text-active' : 'filter-chip-text')
              }), "已完成", 2 /* CLASS */)
            ], 10 /* CLASS, PROPS */, ["onClick"]),
            _cE("view", _uM({
              class: _nC(unref(selectedSyncStatus) == 'SYNC_ERROR' ? 'filter-chip filter-chip-active' : 'filter-chip'),
              onClick: () => {selectSyncStatus('SYNC_ERROR')}
            }), [
              _cE("text", _uM({
                class: _nC(unref(selectedSyncStatus) == 'SYNC_ERROR' ? 'filter-chip-text filter-chip-text-active' : 'filter-chip-text')
              }), "异常", 2 /* CLASS */)
            ], 10 /* CLASS, PROPS */, ["onClick"])
          ]),
          _cE("view", _uM({ class: "filter-actions" }), [
            _cE("view", _uM({
              class: "filter-btn filter-btn-light",
              onClick: handleFilterReset
            }), [
              _cE("text", _uM({ class: "filter-btn-light-text" }), "重置")
            ]),
            _cE("view", _uM({
              class: "filter-btn filter-btn-primary",
              onClick: applyFilter
            }), [
              _cE("text", _uM({ class: "filter-btn-primary-text" }), "应用")
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
        _cE("view", _uM({ class: "status-card" }), [
          _cE("view", _uM({ class: "status-head" }), [
            _cE("view", null, [
              _cE("text", _uM({ class: "status-title" }), "自动同步"),
              _cE("text", _uM({ class: "status-subtitle" }), _tD(autoSyncSubtitle.value), 1 /* TEXT */)
            ]),
            _cE("view", _uM({
              class: "sync-btn",
              onClick: handleEnqueueAutoSync
            }), [
              _cE("text", _uM({ class: "sync-btn-text" }), "立即排队")
            ])
          ]),
          _cE("view", _uM({ class: "status-grid" }), [
            _cE("view", _uM({ class: "status-cell" }), [
              _cE("text", _uM({ class: "status-value" }), _tD(pendingXmlText.value), 1 /* TEXT */),
              _cE("text", _uM({ class: "status-label" }), "待详情")
            ]),
            _cE("view", _uM({ class: "status-cell" }), [
              _cE("text", _uM({ class: "status-value" }), _tD(batchLimitText.value), 1 /* TEXT */),
              _cE("text", _uM({ class: "status-label" }), "每批限制")
            ]),
            _cE("view", _uM({ class: "status-cell" }), [
              _cE("text", _uM({ class: "status-value" }), _tD(lastSuccessText.value), 1 /* TEXT */),
              _cE("text", _uM({ class: "status-label" }), "最近成功")
            ])
          ]),
          unref(statusError) != ''
            ? _cE("text", _uM({
                key: 0,
                class: "status-error"
              }), _tD(unref(statusError)), 1 /* TEXT */)
            : _cC("v-if", true)
        ]),
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
          showChevron: false,
          showPagination: true,
          currentPage: unref(currentPage),
          totalPages: unref(totalPages),
          totalCount: unref(totalCount),
          showFloatingAdd: false,
          summaryTitle: "KSeF 汇总",
          summaryItems: summaryItems.value,
          summaryCollapsedByDefault: false,
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
const GenPagesKsefIndexStyles = [_uM([["page", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#F6F7FB"]]))], ["page-scroll", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#F6F7FB"]]))], ["page-content", _pS(_uM([["paddingLeft", 6], ["paddingRight", 6], ["paddingTop", 6], ["paddingBottom", 96]]))], ["status-card", _pS(_uM([["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["paddingTop", 14], ["paddingRight", 14], ["paddingBottom", 14], ["paddingLeft", 14], ["marginBottom", 10], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E5EAF1"], ["borderRightColor", "#E5EAF1"], ["borderBottomColor", "#E5EAF1"], ["borderLeftColor", "#E5EAF1"]]))], ["status-head", _pS(_uM([["flexDirection", "row"], ["justifyContent", "space-between"], ["alignItems", "center"]]))], ["status-title", _pS(_uM([["fontSize", 17], ["lineHeight", "22px"], ["color", "#0F172A"], ["fontWeight", "bold"]]))], ["status-subtitle", _pS(_uM([["fontSize", 12], ["lineHeight", "18px"], ["color", "#64748B"], ["marginTop", 2]]))], ["sync-btn", _pS(_uM([["height", 36], ["paddingLeft", 14], ["paddingRight", 14], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#0F172A"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["sync-btn-text", _pS(_uM([["fontSize", 13], ["lineHeight", "13px"], ["color", "#FFFFFF"]]))], ["status-grid", _pS(_uM([["flexDirection", "row"], ["marginTop", 12]]))], ["status-cell", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#F8FAFC"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["paddingTop", 10], ["paddingBottom", 10], ["paddingLeft", 8], ["paddingRight", 8], ["marginRight", 6]]))], ["status-value", _pS(_uM([["fontSize", 15], ["lineHeight", "20px"], ["color", "#0F172A"], ["fontWeight", "bold"]]))], ["status-label", _pS(_uM([["fontSize", 11], ["lineHeight", "16px"], ["color", "#64748B"], ["marginTop", 2]]))], ["status-error", _pS(_uM([["fontSize", 12], ["lineHeight", "18px"], ["color", "#B42318"], ["marginTop", 8]]))], ["filter-panel", _pS(_uM([["paddingBottom", 8]]))], ["filter-title", _pS(_uM([["fontSize", 14], ["lineHeight", "18px"], ["color", "#0F172A"], ["fontWeight", "bold"]]))], ["filter-row", _pS(_uM([["flexDirection", "row"], ["flexWrap", "wrap"], ["marginTop", 10]]))], ["filter-chip", _pS(_uM([["height", 34], ["paddingLeft", 12], ["paddingRight", 12], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#F8FAFC"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E2E8F0"], ["borderRightColor", "#E2E8F0"], ["borderBottomColor", "#E2E8F0"], ["borderLeftColor", "#E2E8F0"], ["alignItems", "center"], ["justifyContent", "center"], ["marginRight", 8], ["marginBottom", 8]]))], ["filter-chip-active", _pS(_uM([["backgroundColor", "#0F172A"], ["borderTopColor", "#0F172A"], ["borderRightColor", "#0F172A"], ["borderBottomColor", "#0F172A"], ["borderLeftColor", "#0F172A"]]))], ["filter-chip-text", _pS(_uM([["fontSize", 13], ["lineHeight", "13px"], ["color", "#475569"]]))], ["filter-chip-text-active", _pS(_uM([["color", "#FFFFFF"]]))], ["filter-actions", _pS(_uM([["flexDirection", "row"], ["marginTop", 8]]))], ["filter-btn", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["height", 40], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["alignItems", "center"], ["justifyContent", "center"]]))], ["filter-btn-light", _pS(_uM([["backgroundColor", "#F3F6FA"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E2E8F0"], ["borderRightColor", "#E2E8F0"], ["borderBottomColor", "#E2E8F0"], ["borderLeftColor", "#E2E8F0"], ["marginRight", 8]]))], ["filter-btn-primary", _pS(_uM([["backgroundColor", "#0F172A"]]))], ["filter-btn-light-text", _pS(_uM([["fontSize", 14], ["lineHeight", "14px"], ["color", "#475569"]]))], ["filter-btn-primary-text", _pS(_uM([["fontSize", 14], ["lineHeight", "14px"], ["color", "#FFFFFF"]]))], ["error-card", _pS(_uM([["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["paddingTop", 18], ["paddingRight", 18], ["paddingBottom", 18], ["paddingLeft", 18], ["marginBottom", 10], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#FECACA"], ["borderRightColor", "#FECACA"], ["borderBottomColor", "#FECACA"], ["borderLeftColor", "#FECACA"], ["alignItems", "center"]]))], ["error-title", _pS(_uM([["fontSize", 18], ["lineHeight", "24px"], ["color", "#B42318"], ["fontWeight", "bold"]]))], ["error-desc", _pS(_uM([["fontSize", 14], ["lineHeight", "20px"], ["color", "#7F1D1D"], ["marginTop", 8], ["textAlign", "center"]]))], ["retry-btn", _pS(_uM([["marginTop", 14], ["height", 40], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#0F172A"], ["paddingLeft", 18], ["paddingRight", 18], ["alignItems", "center"], ["justifyContent", "center"]]))], ["retry-btn-text", _pS(_uM([["fontSize", 14], ["color", "#FFFFFF"]]))]])]
