import _easycom_lili_universal_filter from '@/uni_modules/lili-universal-filter/components/lili-universal-filter/lili-universal-filter.uvue'
import _easycom_lili_UniversaForm from '@/uni_modules/lili-UniversaForm/components/lili-UniversaForm/lili-UniversaForm.uvue'
import { computed } from 'vue'
import { takeLatestResponseMessage } from '@/pkg/api/index.uts'
import { createPurchase, getPurchaseDetail, getPurchaseOptionList, PurchaseItem, PurchaseMutationData, PurchaseOptionItem, updatePurchase } from '@/pkg/api/modules/purchases.uts'


const __sfc__ = defineComponent({
  __name: 'from',
  setup(__props) {
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

const refreshStorageKey = 'refresh:pages:purchases:index'
const formMode = ref('create')
const purchaseId = ref('')
const leaveSignal = ref(0)
const submitting = ref(false)
const initialData = ref<UTSJSONObject>({} as UTSJSONObject)

function twoDigit(value: number): string {
	if (value < 10) return '0' + value.toString()
	return value.toString()
}

function todayText(): string {
	const now = new Date()
	return now.getFullYear().toString() + '-' + twoDigit(now.getMonth() + 1) + '-' + twoDigit(now.getDate())
}

function getStringField(obj: UTSJSONObject, key: string, fallback: string = ''): string {
	const value = obj[key]
	if (value == null) return fallback
	return '' + value
}

function parseErrorMessage(error: any, fallback: string): string {
	let message = fallback
	if (error != null) {
		const directMessage = (error as Error).message
		if (directMessage != null && directMessage != '') message = directMessage
		const errorText = JSON.stringify(error)
		if (errorText != null && errorText != '') {
			const parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/purchases/from.uvue:57")
			if (parsedError != null) {
				const rawMessage = parsedError['message']
				if (rawMessage != null) {
					const parsedMessage = rawMessage as string
					if (parsedMessage != '') message = parsedMessage
				}
			}
		}
	}
	return message
}

function buildSelectResponse(source: PurchaseOptionItem[], params: UTSJSONObject): UTSJSONObject {
	const id = getStringField(params, 'id')
	const result: UTSJSONObject[] = []
	for (let index = 0; index < source.length; index += 1) {
		const option = source[index]
		if (id != '' && option.value != id) continue
		result.push({ value: option.value, text: option.text } as UTSJSONObject)
	}
	return { data: result, results: result, total: result.length, total_count: result.length } as UTSJSONObject
}

async function fetchShopOptions(params: UTSJSONObject): Promise<UTSJSONObject> {
	const keyword = getStringField(params, 'keyword')
	const id = getStringField(params, 'id')
	const options = await getPurchaseOptionList('/api/shops/shops/', keyword == '' ? null : keyword, 'name', 'address')
	return buildSelectResponse(options, { keyword: keyword, id: id } as UTSJSONObject)
}

async function fetchSupplierOptions(params: UTSJSONObject): Promise<UTSJSONObject> {
	const keyword = getStringField(params, 'keyword')
	const id = getStringField(params, 'id')
	const options = await getPurchaseOptionList('/api/procurement/suppliers/', keyword == '' ? null : keyword, 'name', 'phone')
	return buildSelectResponse(options, { keyword: '', id: id } as UTSJSONObject)
}

function initialCreateData(): UTSJSONObject {
	return {
		purchase_date: todayText(),
		shop: '',
		shop_text: '',
		supplier: '',
		supplier_text: '',
		remark: '',
	} as UTSJSONObject
}

function buildInitialDataFromPurchase(item: PurchaseItem): UTSJSONObject {
	return {
		purchase_date: item.purchase_date,
		shop: item.shop.toString(),
		shop_text: item.shop_name,
		supplier: item.supplier.toString(),
		supplier_text: item.supplier_name,
		remark: item.remark,
	} as UTSJSONObject
}

const formSections = ref<UTSJSONObject[]>([
	{
		key: 'base',
		title: '基础信息',
		description: '',
		defaultOpen: true,
		fields: [
			{ key: 'purchase_date', label: '采购日期', type: 'datetime', required: true, showTime: false, defaultToToday: true, title: '选择采购日期', placeholder: '请选择采购日期' } as UTSJSONObject,
			{ key: 'shop', textKey: 'shop_text', label: '采购店铺', type: 'bottomSelect', required: true, title: '选择采购店铺', placeholder: '请选择采购店铺', showAddAction: true, showEditAction: true, addPath: '/pages/shop/from', editPath: '/pages/shop/from', fetchData: fetchShopOptions } as UTSJSONObject,
			{ key: 'supplier', textKey: 'supplier_text', label: '供应商', type: 'bottomSelect', required: true, title: '选择供应商', placeholder: '请选择供应商', showAddAction: true, showEditAction: true, addPath: '/pages/suppliers/from', editPath: '/pages/suppliers/from', fetchData: fetchSupplierOptions } as UTSJSONObject,
			{ key: 'remark', label: '备注', type: 'textarea', placeholder: '请输入备注' } as UTSJSONObject,
		] as UTSJSONObject[],
	} as UTSJSONObject,
])

const pageTitle = computed((): string => formMode.value == 'edit' ? '编辑采购单' : '新建采购单')

function markRefresh() { uni.setStorageSync(refreshStorageKey, '1') }
function goBackToList() {
	leaveSignal.value = leaveSignal.value + 1
	setTimeout(() => { uni.navigateBack({ delta: 1, fail: () => { uni.navigateTo({ url: '/pages/purchases/index' }) } }) }, 16)
}

function goToCreatedPurchaseDetail(id: string) {
	leaveSignal.value = leaveSignal.value + 1
	initialData.value = initialCreateData()
	setTimeout(() => {
		uni.redirectTo({
			url: '/pages/purchases/details/index?purchase=' + id,
			fail: () => {
				uni.navigateTo({ url: '/pages/purchases/details/index?purchase=' + id })
			},
		})
	}, 16)
}

async function loadDetail(idText: string) {
	if (idText == '') return
	try {
		const detail = await getPurchaseDetail(idText)
		initialData.value = buildInitialDataFromPurchase(detail)
	} catch (error) {
		uni.showToast({ title: parseErrorMessage(error, '采购单详情加载失败'), icon: 'none' })
	}
}

function buildPayload(data: UTSJSONObject): PurchaseMutationData {
	return {
		purchase_date: getStringField(data, 'purchase_date'),
		shop: getStringField(data, 'shop'),
		supplier: getStringField(data, 'supplier'),
		remark: getStringField(data, 'remark') == '' ? null : getStringField(data, 'remark'),
		items: [] as UTSJSONObject[],
	} as PurchaseMutationData
}

async function persistForm(payload: UTSJSONObject) {
	if (submitting.value) return
	const rawData = payload['formData']
	const data = rawData == null ? ({} as UTSJSONObject) : (rawData as UTSJSONObject)
	let body: PurchaseMutationData
	try {
		body = buildPayload(data)
	} catch (error) {
		uni.showToast({ title: parseErrorMessage(error, '采购明细不完整'), icon: 'none' })
		return
	}
	if (body.purchase_date == '' || body.shop == '' || body.supplier == '') {
		uni.showToast({ title: '请填写采购日期、店铺和供应商', icon: 'none' })
		return
	}
	const actionText = formMode.value == 'edit' ? '保存采购单' : '创建采购单'
	submitting.value = true
	uni.showLoading({ title: actionText + '中...', mask: true })
	try {
		let savedPurchaseId = purchaseId.value
		if (formMode.value == 'edit' && purchaseId.value != '') {
			await updatePurchase(purchaseId.value, body)
		} else {
			const created = await createPurchase(body)
			savedPurchaseId = created.id.toString()
		}
		markRefresh()
		uni.showToast({ title: takeLatestResponseMessage(actionText + '成功'), icon: 'success' })
		if (formMode.value == 'edit') goBackToList()
		else goToCreatedPurchaseDetail(savedPurchaseId)
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
function handleBottomSelectAdd(payload: UTSJSONObject) { uni.showToast({ title: '请在对应模块维护选项', icon: 'none' }) }
function handleBottomSelectEdit(payload: UTSJSONObject) { uni.showToast({ title: '该字段不支持直接编辑', icon: 'none' }) }

onLoad((query: OnLoadOptions) => {
	initialData.value = initialCreateData()
	const idValue = query['id']
	if (idValue != null && idValue != '') {
		formMode.value = 'edit'
		purchaseId.value = idValue
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
      homePath: "/pages/purchases/index",
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
const GenPagesPurchasesFromStyles = [_uM([["page", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#EEF2F7"]]))], ["page-content", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["paddingBottom", 0]]))]])]
