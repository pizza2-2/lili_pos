import _easycom_lili_universal_filter from '@/uni_modules/lili-universal-filter/components/lili-universal-filter/lili-universal-filter.uvue'
import _easycom_lili_UniversaForm from '@/uni_modules/lili-UniversaForm/components/lili-UniversaForm/lili-UniversaForm.uvue'
import { computed } from 'vue'
import { takeLatestResponseMessage } from '@/pkg/api/index.uts'
import { createInventoryLocation, getInventoryLocationDetail, InventoryMutationData, updateInventoryLocation } from '@/pkg/api/modules/inventory'

type SelectOption = { __$originalPosition?: UTSSourceMapPosition<"SelectOption", "pages/inventory-locations/from.uvue", 15, 6>;
	value: string
	text: string
}


const __sfc__ = defineComponent({
  __name: 'from',
  setup(__props) {
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

const refreshStorageKey = 'refresh:pages:inventory-locations:index'
const formMode = ref('create')
const itemId = ref('')
const leaveSignal = ref(0)
const submitting = ref(false)
const initialData = ref<UTSJSONObject>({ name: '', code: '', location_type: 'WAREHOUSE', location_type_text: '仓库', address: '', is_active: 'true', is_active_text: '启用' } as UTSJSONObject)

const typeOptions = ref<SelectOption[]>([
	{ value: 'SHOP', text: '店铺' } as SelectOption,
	{ value: 'WAREHOUSE', text: '仓库' } as SelectOption,
	{ value: 'TRANSIT', text: '在途' } as SelectOption,
	{ value: 'SUPPLIER', text: '供应商处' } as SelectOption,
])
const activeOptions = ref<SelectOption[]>([
	{ value: 'true', text: '启用' } as SelectOption,
	{ value: 'false', text: '停用' } as SelectOption,
])

function getStringField(obj: UTSJSONObject, key: string, fallback: string = ''): string {
	const value = obj[key]
	if (value == null) return fallback
	return '' + value
}

function parseErrorMessage(error: any, fallback: string): string {
	if (error == null) return fallback
	const text = JSON.stringify(error)
	if (text == null || text == '') return fallback
	return text
}

function buildSelectResponse(source: SelectOption[], params: UTSJSONObject): UTSJSONObject {
	const id = getStringField(params, 'id')
	const result: UTSJSONObject[] = []
	for (let index = 0; index < source.length; index += 1) {
		const option = source[index]
		if (id != '' && option.value != id) continue
		result.push({ value: option.value, text: option.text } as UTSJSONObject)
	}
	return { data: result, results: result, total: result.length, total_count: result.length } as UTSJSONObject
}

async function fetchTypeOptions(params: UTSJSONObject): Promise<UTSJSONObject> { return buildSelectResponse(typeOptions.value, params) }
async function fetchActiveOptions(params: UTSJSONObject): Promise<UTSJSONObject> { return buildSelectResponse(activeOptions.value, params) }

const formSections = ref<UTSJSONObject[]>([
	{
		key: 'base',
		title: '基础信息',
		description: '',
		defaultOpen: true,
		fields: [
			{ key: 'name', label: '位置名称', type: 'input', required: true, placeholder: '请输入库存位置名称' } as UTSJSONObject,
			{ key: 'code', label: '位置编码', type: 'input', required: true, placeholder: '请输入位置编码' } as UTSJSONObject,
			{ key: 'location_type', textKey: 'location_type_text', label: '位置类型', type: 'bottomSelect', title: '选择位置类型', placeholder: '请选择位置类型', showAddAction: false, showEditAction: false, fetchData: fetchTypeOptions } as UTSJSONObject,
			{ key: 'address', label: '地址', type: 'textarea', placeholder: '请输入地址' } as UTSJSONObject,
			{ key: 'is_active', textKey: 'is_active_text', label: '启用状态', type: 'bottomSelect', title: '选择启用状态', placeholder: '请选择启用状态', showAddAction: false, showEditAction: false, fetchData: fetchActiveOptions } as UTSJSONObject,
		] as UTSJSONObject[],
	} as UTSJSONObject,
])

const pageTitle = computed((): string => formMode.value == 'edit' ? '编辑库存位置' : '新建库存位置')

function markRefreshNeeded() { uni.setStorageSync(refreshStorageKey, '1') }
function goBackToList() {
	leaveSignal.value = leaveSignal.value + 1
	setTimeout(() => { uni.navigateBack({ delta: 1, fail: () => { uni.navigateTo({ url: '/pages/inventory-locations/index' }) } }) }, 16)
}

function buildPayload(data: UTSJSONObject): InventoryMutationData {
	return {
		payload: {
			name: getStringField(data, 'name'),
			code: getStringField(data, 'code'),
			location_type: getStringField(data, 'location_type', 'WAREHOUSE'),
			address: getStringField(data, 'address'),
			is_active: getStringField(data, 'is_active', 'true') == 'true',
		} as UTSJSONObject,
	} as InventoryMutationData
}

function optionText(source: SelectOption[], value: string, fallback: string): string {
	for (let index = 0; index < source.length; index += 1) {
		const option = source[index]
		if (option.value == value) return option.text
	}
	return fallback
}

async function loadDetail(idText: string) {
	if (idText == '') return
	try {
		const detail = await getInventoryLocationDetail(idText)
		const typeValue = getStringField(detail, 'location_type', 'WAREHOUSE')
		let typeText = getStringField(detail, 'location_type_display')
		if (typeText == '') typeText = optionText(typeOptions.value, typeValue, '仓库')
		const activeValue = getStringField(detail, 'is_active') == 'true' ? 'true' : 'false'
		initialData.value = {
			name: getStringField(detail, 'name'),
			code: getStringField(detail, 'code'),
			location_type: typeValue,
			location_type_text: typeText,
			address: getStringField(detail, 'address'),
			is_active: activeValue,
			is_active_text: optionText(activeOptions.value, activeValue, '启用'),
		} as UTSJSONObject
	} catch (error) {
		uni.showToast({ title: parseErrorMessage(error, '库存位置详情加载失败'), icon: 'none' })
	}
}

async function persistForm(payload: UTSJSONObject) {
	if (submitting.value) return
	const rawData = payload['formData']
	const data = rawData == null ? ({} as UTSJSONObject) : (rawData as UTSJSONObject)
	if (getStringField(data, 'name') == '' || getStringField(data, 'code') == '') {
		uni.showToast({ title: '请填写位置名称和位置编码', icon: 'none' })
		return
	}
	const actionText = formMode.value == 'edit' ? '保存库存位置' : '创建库存位置'
	submitting.value = true
	uni.showLoading({ title: actionText + '中...', mask: true })
	try {
		if (formMode.value == 'edit' && itemId.value != '') await updateInventoryLocation(itemId.value, buildPayload(data))
		else await createInventoryLocation(buildPayload(data))
		markRefreshNeeded()
		uni.showToast({ title: takeLatestResponseMessage(actionText + '成功'), icon: 'success' })
		goBackToList()
	} catch (error) {
		uni.showToast({ title: parseErrorMessage(error, actionText + '失败'), icon: 'none' })
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
		formMode.value = 'edit'
		itemId.value = idValue
		loadDetail(idValue)
	}
})

return (): any | null => {

const _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter",_easycom_lili_universal_filter)
const _component_lili_UniversaForm = resolveEasyComponent("lili-UniversaForm",_easycom_lili_UniversaForm)

  return _cE("view", _uM({ class: "page" }), [
    _cV(_component_lili_universal_filter, _uM({
      title: pageTitle.value,
      showBack: true,
      showSearch: false,
      showHome: true,
      homePath: "/pages/inventory-locations/index",
      backgroundColor: "#EEF2F7"
    }), null, 8 /* PROPS */, ["title"]),
    _cE("view", _uM({ class: "page-content" }), [
      _cV(_component_lili_UniversaForm, _uM({
        mode: unref(formMode),
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
      }), null, 8 /* PROPS */, ["mode", "formSections", "initialData", "leaveSignal"])
    ])
  ])
}
}

})
export default __sfc__
const GenPagesInventoryLocationsFromStyles = [_uM([["page", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#EEF2F7"]]))], ["page-content", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["paddingBottom", 0]]))]])]
