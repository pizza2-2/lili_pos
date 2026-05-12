import _easycom_lili_universal_filter from '@/uni_modules/lili-universal-filter/components/lili-universal-filter/lili-universal-filter.uvue'
import _easycom_lili_UniversaForm from '@/uni_modules/lili-UniversaForm/components/lili-UniversaForm/lili-UniversaForm.uvue'
import { takeLatestResponseMessage } from '@/pkg/api/index.uts'
import { adjustInventoryStock, getInventoryStockDetail, StockAdjustmentData } from '@/pkg/api/modules/inventory'

type SelectOption = {
	value: string
	text: string
}


const __sfc__ = defineComponent({
  __name: 'from',
  setup(__props) {
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

const refreshStorageKey = 'refresh:pages:inventory-management:index'
const leaveSignal = ref(0)
const submitting = ref(false)
const stockId = ref('')
const initialData = ref<UTSJSONObject>({ stock_id: '', product_name: '', quantity_change: '', transaction_type: 'ADJUSTMENT', unit_cost: '', notes: '' } as UTSJSONObject)
const typeOptions = ref<SelectOption[]>([
	{ value: 'ADJUSTMENT', text: '盘点调整' } as SelectOption,
	{ value: 'DAMAGE', text: '损坏' } as SelectOption,
	{ value: 'LOSS', text: '丢失' } as SelectOption,
	{ value: 'INITIAL', text: '初始库存' } as SelectOption,
])

function getStringField(obj: UTSJSONObject, key: string, fallback: string = ''): string { const value = obj[key]; if (value == null) return fallback; return '' + value }
function parseErrorMessage(error: any, fallback: string): string { if (error == null) return fallback; const text = JSON.stringify(error); if (text == null || text == '') return fallback; return text }
function buildSelectResponse(source: SelectOption[], params: UTSJSONObject): UTSJSONObject {
	const keyword = getStringField(params, 'keyword').toLowerCase()
	const result: UTSJSONObject[] = []
	for (let index = 0; index < source.length; index += 1) {
		const option = source[index]
		if (keyword == '' || option.text.toLowerCase().indexOf(keyword) >= 0) result.push({ value: option.value, text: option.text } as UTSJSONObject)
	}
	return { results: result, total_count: result.length } as UTSJSONObject
}
async function fetchTypeOptions(params: UTSJSONObject): Promise<UTSJSONObject> { return buildSelectResponse(typeOptions.value, params) }

const formSections = ref<UTSJSONObject[]>([
	{
		key: 'base',
		title: '调整信息',
		description: '',
		defaultOpen: true,
		fields: [
			{ key: 'product_name', label: '商品', type: 'input', disabled: true, placeholder: '从库存列表进入后自动带出' } as UTSJSONObject,
			{ key: 'stock_id', label: '库存ID', type: 'input', required: true, disabled: true } as UTSJSONObject,
			{ key: 'quantity_change', label: '调整数量', type: 'input', required: true, placeholder: '正数入库，负数出库' } as UTSJSONObject,
			{ key: 'transaction_type', label: '调整类型', type: 'bottomSelect', title: '选择调整类型', placeholder: '请选择调整类型', showAddAction: false, showEditAction: false, fetchData: fetchTypeOptions } as UTSJSONObject,
			{ key: 'unit_cost', label: '单位成本', type: 'input', placeholder: '可选' } as UTSJSONObject,
			{ key: 'notes', label: '备注', type: 'textarea', placeholder: '请输入调整原因' } as UTSJSONObject,
		] as UTSJSONObject[],
	} as UTSJSONObject,
])

function markRefreshNeeded() { uni.setStorageSync(refreshStorageKey, '1') }
function goBackToList() { leaveSignal.value = leaveSignal.value + 1; setTimeout(() => { uni.navigateBack({ delta: 1, fail: () => { uni.navigateTo({ url: '/pages/inventory-management/index' }) } }) }, 16) }

async function loadDetail(idText: string) {
	if (idText == '') return
	try {
		const detail = await getInventoryStockDetail(idText)
		initialData.value = {
			stock_id: idText,
			product_name: getStringField(detail, 'product_name', '-') + ' / ' + getStringField(detail, 'location_name', '-'),
			quantity_change: '',
			transaction_type: 'ADJUSTMENT',
			unit_cost: getStringField(detail, 'average_cost'),
			notes: '',
		} as UTSJSONObject
	} catch (error) {
		uni.showToast({ title: parseErrorMessage(error, '库存详情加载失败'), icon: 'none' })
	}
}

function buildPayload(data: UTSJSONObject): StockAdjustmentData {
	const idText = getStringField(data, 'stock_id', stockId.value)
	const quantityText = getStringField(data, 'quantity_change')
	return { stock_id: parseInt(idText), quantity_change: parseInt(quantityText), transaction_type: getStringField(data, 'transaction_type', 'ADJUSTMENT'), unit_cost: getStringField(data, 'unit_cost'), notes: getStringField(data, 'notes') } as StockAdjustmentData
}

async function persistForm(payload: UTSJSONObject) {
	if (submitting.value) return
	const rawData = payload['formData']
	const data = rawData == null ? ({} as UTSJSONObject) : (rawData as UTSJSONObject)
	const body = buildPayload(data)
	if (isNaN(body.stock_id) || isNaN(body.quantity_change) || body.quantity_change == 0) {
		uni.showToast({ title: '请输入有效的调整数量', icon: 'none' })
		return
	}
	submitting.value = true
	uni.showLoading({ title: '调整库存中...', mask: true })
	try {
		await adjustInventoryStock(body)
		markRefreshNeeded()
		uni.showToast({ title: takeLatestResponseMessage('库存调整成功'), icon: 'success' })
		goBackToList()
	} catch (error) {
		uni.showToast({ title: parseErrorMessage(error, '库存调整失败'), icon: 'none' })
	} finally {
		uni.hideLoading()
		submitting.value = false
	}
}

async function handleSubmit(payload: UTSJSONObject) { await persistForm(payload) }
async function handleSaveRequest(payload: UTSJSONObject) { await persistForm(payload) }
function handleCancel(payload: UTSJSONObject) { const changed = payload['hasChanges']; if (changed != null && (changed as boolean)) return; goBackToList() }
function handleDiscardLeave(payload: UTSJSONObject) { goBackToList() }
function handleDirtyChange(value: boolean) {}
function handleBottomSelectAdd(payload: UTSJSONObject) { uni.showToast({ title: '该字段不支持新增', icon: 'none' }) }
function handleBottomSelectEdit(payload: UTSJSONObject) { uni.showToast({ title: '该字段不支持编辑', icon: 'none' }) }

onLoad((query: OnLoadOptions) => {
	const idValue = query['id']
	if (idValue != null && idValue != '') {
		stockId.value = idValue
		initialData.value['stock_id'] = idValue
		loadDetail(idValue)
	}
})

return (): any | null => {

const _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter",_easycom_lili_universal_filter)
const _component_lili_UniversaForm = resolveEasyComponent("lili-UniversaForm",_easycom_lili_UniversaForm)

  return _cE("view", _uM({ class: "page" }), [
    _cV(_component_lili_universal_filter, _uM({
      title: "调整库存",
      showBack: true,
      showSearch: false,
      showHome: true,
      homePath: "/pages/inventory-management/index",
      backgroundColor: "#EEF2F7"
    })),
    _cE("view", _uM({ class: "page-content" }), [
      _cV(_component_lili_UniversaForm, _uM({
        mode: "create",
        formSections: unref(formSections),
        initialData: unref(initialData),
        leaveSignal: unref(leaveSignal),
        onSubmit: handleSubmit,
        onCancel: handleCancel,
        onDiscardLeave: handleDiscardLeave,
        onSaveRequest: handleSaveRequest,
        onDirtyChange: handleDirtyChange,
        onBottomSelectAdd: handleBottomSelectAdd,
        onBottomSelectEdit: handleBottomSelectEdit
      }), null, 8 /* PROPS */, ["formSections", "initialData", "leaveSignal"])
    ])
  ])
}
}

})
export default __sfc__
const GenPagesInventoryManagementFromStyles = [_uM([["page", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#EEF2F7"]]))], ["page-content", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["paddingBottom", 0]]))]])]
