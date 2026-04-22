import _easycom_lili_universal_filter from '@/uni_modules/lili-universal-filter/components/lili-universal-filter/lili-universal-filter.uvue'
import _easycom_lili_UniversaForm from '@/uni_modules/lili-UniversaForm/components/lili-UniversaForm/lili-UniversaForm.uvue'
import { computed } from 'vue'
import { createKasaCategory, getKasaCategoryDetail, getKasaCategoryTaxRates, KasaCategoryItem, KasaCategoryMutationData, KasaCategoryTaxRatesResponse, updateKasaCategory } from '@/pkg/api/modules/kasa_category'

type SelectOption = { __$originalPosition?: UTSSourceMapPosition<"SelectOption", "pages/kasa_category/form.uvue", 40, 6>;
	value: string
	text: string
}


const __sfc__ = defineComponent({
  __name: 'form',
  setup(__props) {
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

const kasaCategoryListRefreshStorageKey = 'refresh:pages:kasa_category:index'

const formMode = ref('create')
const kasaCategoryId = ref('')
const leaveSignal = ref(0)
const submitting = ref(false)
const savingVisible = ref(false)
const savingText = ref('处理中...')
const taxRateResponse = ref<KasaCategoryTaxRatesResponse | null>(null)

const initialData = ref<UTSJSONObject>({
	name_cn: '',
	name_en: '',
	code: '',
	unique_kod: '',
	tax_rate: '',
	tax_rate_display: '',
	is_active: 'true',
	products_count: '0',
} as UTSJSONObject)

const statusOptions = ref<SelectOption[]>([
	{ value: 'true', text: '启用' } as SelectOption,
	{ value: 'false', text: '停用' } as SelectOption,
])

function stringValue(value: any | null, fallback: string = ''): string {
	if (value == null) {
		return fallback
	}
	const text = '' + value
	return text == '' ? fallback : text
}

function parseErrorMessage(error: any, fallback: string): string {
	let message = fallback
	if (error != null) {
		const directMessage = (error as Error).message
		if (directMessage != null && directMessage != '') {
			message = directMessage
		}
		const errorText = JSON.stringify(error)
		if (errorText != null && errorText != '') {
			const parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/kasa_category/form.uvue:88")
			if (parsedError != null) {
				const rawMessage = parsedError['message']
				if (rawMessage != null) {
					const parsedMessage = rawMessage as string
					if (parsedMessage != '') {
						message = parsedMessage
					}
				}
			}
		}
	}
	return message
}

function buildTaxRateLabel(item: UTSJSONObject): string {
	const display = stringValue(item['tax_rate_display'])
	if (display != '') {
		return display
	}
	const text = stringValue(item['text'])
	if (text != '') {
		return text
	}
	const label = stringValue(item['label'])
	if (label != '') {
		return label
	}
	const rate = stringValue(item['tax_rate'])
	if (rate != '') {
		return rate
	}
	const value = stringValue(item['value'])
	if (value != '') {
		return value
	}
	return ''
}

function buildTaxRateValue(item: UTSJSONObject): string {
	const value = stringValue(item['value'])
	if (value != '') {
		return value
	}
	const rate = stringValue(item['tax_rate'])
	if (rate != '') {
		return rate
	}
	const id = stringValue(item['id'])
	if (id != '') {
		return id
	}
	const code = stringValue(item['code'])
	if (code != '') {
		return code
	}
	return buildTaxRateLabel(item)
}

function normalizeTaxRateToken(value: string): string {
	if (value == '') {
		return ''
	}
	const normalized = value.replace('%', '').trim()
	const parsed = parseFloat(normalized)
	if (isNaN(parsed)) {
		return normalized
	}
	return parsed.toString()
}

function appendTaxRateOption(result: SelectOption[], value: string, text: string) {
	if (value == '') {
		return
	}
	let exists = false
	for (let optionIndex = 0; optionIndex < result.length; optionIndex += 1) {
		if (result[optionIndex].value == value) {
			exists = true
			break
		}
	}
	if (!exists) {
		result.push({
			value: value,
			text: text == '' ? value : text,
		} as SelectOption)
	}
}

function normalizeTaxRateOptions(): SelectOption[] {
	if (taxRateResponse.value == null) {
		return []
	}
	const result: SelectOption[] = []
	for (let index = 0; index < taxRateResponse.value.items.length; index += 1) {
		const item = taxRateResponse.value.items[index]
		const value = buildTaxRateValue(item)
		if (value == '') {
			continue
		}
		appendTaxRateOption(result, value, buildTaxRateLabel(item))
	}
	return result
}

function findTaxRateOptionByValue(value: string): SelectOption | null {
	if (value == '') {
		return null
	}
	const normalizedValue = normalizeTaxRateToken(value)
	const options = normalizeTaxRateOptions()
	for (let index = 0; index < options.length; index += 1) {
		const option = options[index]
		if (option.value == value) {
			return option
		}
		if (normalizedValue != '' && normalizeTaxRateToken(option.value) == normalizedValue) {
			return option
		}
	}
	return null
}

async function loadTaxRateOptions() {
	if (taxRateResponse.value != null) {
		return
	}
	try {
		taxRateResponse.value = await getKasaCategoryTaxRates()
		console.log(taxRateResponse.value, " at pages/kasa_category/form.uvue:218")
	} catch (error) {
		taxRateResponse.value = null
	}
}

function buildSelectResponse(source: SelectOption[], params: UTSJSONObject): UTSJSONObject {
	const keyword = stringValue(params['keyword']).toLowerCase()
	const id = stringValue(params['id'])
	const normalizedId = normalizeTaxRateToken(id)
	const result: UTSJSONObject[] = []
	for (let index = 0; index < source.length; index += 1) {
		const option = source[index]
		if (id != '') {
			const exactMatched = option.value == id || option.text == id
			const normalizedMatched = normalizedId != '' && (normalizeTaxRateToken(option.value) == normalizedId || normalizeTaxRateToken(option.text) == normalizedId)
			if (!exactMatched && !normalizedMatched) {
				continue
			}
			result.push({
				value: id,
				text: option.text,
			} as UTSJSONObject)
			continue
		}
		if (keyword != '' && option.text.toLowerCase().indexOf(keyword) < 0) {
			continue
		}
		result.push({
			value: option.value,
			text: option.text,
		} as UTSJSONObject)
	}
	return {
		data: result,
		total: result.length,
	} as UTSJSONObject
}

async function fetchStatusOptions(params: UTSJSONObject): Promise<UTSJSONObject> {
	return buildSelectResponse(statusOptions.value, params)
}

async function fetchTaxRateOptions(params: UTSJSONObject): Promise<UTSJSONObject> {
	await loadTaxRateOptions()
	return buildSelectResponse(normalizeTaxRateOptions(), params)
}

function buildInitialDataFromDetail(item: KasaCategoryItem): UTSJSONObject {
	const matchedTaxRateOption = findTaxRateOptionByValue(item.tax_rate)
	const resolvedTaxRate = matchedTaxRateOption != null ? matchedTaxRateOption.value : item.tax_rate
	const resolvedTaxRateDisplay = matchedTaxRateOption != null ? matchedTaxRateOption.text : item.tax_rate_display
	return {
		name_cn: item.name_cn,
		name_en: item.name_en,
		code: item.code,
		unique_kod: item.unique_kod,
		tax_rate: resolvedTaxRate,
		tax_rate_display: resolvedTaxRateDisplay,
		is_active: item.is_active ? 'true' : 'false',
		products_count: item.products_count.toString(),
	} as UTSJSONObject
}

async function loadDetailData(idText: string) {
	if (idText == '') {
		return
	}
	try {
		await loadTaxRateOptions()
		const detail = await getKasaCategoryDetail(idText)
		console.log(detail, " at pages/kasa_category/form.uvue:289")
		initialData.value = buildInitialDataFromDetail(detail)
	} catch (error) {
		uni.showToast({
			title: parseErrorMessage(error, '收银分类详情加载失败'),
			icon: 'none',
		})
	}
}

function buildMutationPayload(formDataObject: UTSJSONObject): KasaCategoryMutationData {
	const nameCn = stringValue(formDataObject['name_cn'])
	const nameEn = stringValue(formDataObject['name_en'])
	const code = stringValue(formDataObject['code'])
	const uniqueKod = stringValue(formDataObject['unique_kod'])
	const taxRate = stringValue(formDataObject['tax_rate'])
	const isActiveText = stringValue(formDataObject['is_active'], 'true')
	return {
		name_cn: nameCn,
		name_en: nameEn,
		code: code == '' ? null : code,
		unique_kod: uniqueKod == '' ? null : uniqueKod,
		tax_rate: taxRate == '' ? null : taxRate,
		is_active: isActiveText == 'true',
	} as UTSJSONObject
}

function goBackToList() {
	leaveSignal.value = leaveSignal.value + 1
	setTimeout(() => {
		uni.navigateBack({
			delta: 1,
			fail: () => {
				uni.reLaunch({
					url: '/pages/kasa_category/index',
				})
			},
		})
	}, 16)
}

function markListRefreshNeeded() {
	uni.setStorageSync(kasaCategoryListRefreshStorageKey, '1')
}

async function persistForm(payload: UTSJSONObject) {
	if (submitting.value) {
		return
	}
	const formDataValue = payload['formData']
	const data = formDataValue == null ? ({} as UTSJSONObject) : (formDataValue as UTSJSONObject)
	const actionText = formMode.value == 'edit' ? '保存修改' : '创建收银分类'
	submitting.value = true
	savingText.value = actionText + '中...'
	savingVisible.value = true
	uni.showLoading({
		title: savingText.value,
		mask: true,
	})
	try {
		const body = buildMutationPayload(data)
		if (formMode.value == 'edit' && kasaCategoryId.value != '') {
			await updateKasaCategory(kasaCategoryId.value, body)
		} else {
			const created = await createKasaCategory(body)
			kasaCategoryId.value = created.id.toString()
		}
		markListRefreshNeeded()
		uni.showToast({
			title: actionText + '成功',
			icon: 'success',
		})
		goBackToList()
	} catch (error) {
		uni.showToast({
			title: parseErrorMessage(error, actionText + '失败'),
			icon: 'none',
		})
	} finally {
		savingVisible.value = false
		uni.hideLoading()
		submitting.value = false
	}
}

async function handleSubmit(payload: UTSJSONObject) {
	await persistForm(payload)
}

async function handleSaveRequest(payload: UTSJSONObject) {
	await persistForm(payload)
}

function handleCancel(payload: UTSJSONObject) {
	const hasChangesValue = payload['hasChanges']
	const changed = hasChangesValue != null && (hasChangesValue as boolean)
	if (changed) {
		return
	}
	goBackToList()
}

function handleDiscardLeave(payload: UTSJSONObject) {
	goBackToList()
}

function handleDirtyChange(value: boolean) {
}

function handleBottomSelectAdd(payload: UTSJSONObject) {
	uni.showToast({
		title: '当前字段不支持新增',
		icon: 'none',
	})
}

function handleBottomSelectEdit(payload: UTSJSONObject) {
	uni.showToast({
		title: '当前字段不支持编辑',
		icon: 'none',
	})
}

const formSections = ref<UTSJSONObject[]>([
	{
		key: 'base',
		title: '基础信息',
		description: '',
		defaultOpen: true,
		fields: [
			{
				key: 'name_cn',
				label: '中文名',
				type: 'input',
				required: true,
				placeholder: '请输入中文名',
			} as UTSJSONObject,
			{
				key: 'name_en',
				label: '英文名',
				type: 'input',
				placeholder: '请输入英文名',
			} as UTSJSONObject,
			{
				key: 'unique_kod',
				label: '唯一编码',
				type: 'input',
				placeholder: '请输入唯一编码',
			} as UTSJSONObject,
			{
				key: 'tax_rate',
				label: '税率',
				type: 'bottomSelect',
				textKey: 'tax_rate_display',
				title: '选择税率',
				placeholder: '请选择税率',
				showAddAction: false,
				showEditAction: false,
				fetchData: fetchTaxRateOptions,
			} as UTSJSONObject,
		] as UTSJSONObject[],
	} as UTSJSONObject,
	{
		key: 'status',
		title: '状态设置',
		description: '',
		defaultOpen: false,
		fields: [
			{
				key: 'is_active',
				label: '启用状态',
				type: 'bottomSelect',
				title: '选择启用状态',
				placeholder: '请选择启用状态',
				showAddAction: false,
				showEditAction: false,
				fetchData: fetchStatusOptions,
			} as UTSJSONObject,
			{
				key: 'products_count',
				label: '关联商品数',
				type: 'input',
				readonly: true,
				editOnly: true,
				placeholder: '自动统计',
			} as UTSJSONObject,
		] as UTSJSONObject[],
	} as UTSJSONObject,
])

const pageTitle = computed((): string => {
	return formMode.value == 'edit' ? '编辑收银分类' : '新建收银分类'
})

onLoad((event: OnLoadOptions) => {
	leaveSignal.value = 0
	const idValue = event['id']
	kasaCategoryId.value = idValue == null ? '' : (idValue as string)
	formMode.value = kasaCategoryId.value == '' ? 'create' : 'edit'
	loadTaxRateOptions()
	if (formMode.value == 'edit') {
		loadDetailData(kasaCategoryId.value)
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
      homePath: "/pages/kasa_category/index",
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
    ]),
    isTrue(unref(savingVisible))
      ? _cE("view", _uM({
          key: 0,
          class: "page-saving-mask"
        }), [
          _cE("view", _uM({ class: "page-saving-card" }), [
            _cE("text", _uM({ class: "page-saving-text" }), _tD(unref(savingText)), 1 /* TEXT */)
          ])
        ])
      : _cC("v-if", true)
  ])
}
}

})
export default __sfc__
const GenPagesKasaCategoryFormStyles = [_uM([["page", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["position", "relative"], ["backgroundColor", "#EEF2F7"]]))], ["page-content", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["paddingLeft", 0], ["paddingRight", 0], ["paddingBottom", 0]]))], ["page-saving-mask", _pS(_uM([["position", "absolute"], ["left", 0], ["top", 0], ["right", 0], ["bottom", 0], ["zIndex", 9999], ["alignItems", "center"], ["justifyContent", "center"], ["backgroundColor", "rgba(15,23,42,0.28)"]]))], ["page-saving-card", _pS(_uM([["height", 44], ["paddingLeft", 16], ["paddingRight", 16], ["borderTopLeftRadius", 22], ["borderTopRightRadius", 22], ["borderBottomRightRadius", 22], ["borderBottomLeftRadius", 22], ["alignItems", "center"], ["justifyContent", "center"], ["backgroundColor", "rgba(15,23,42,0.86)"]]))], ["page-saving-text", _pS(_uM([["fontSize", 13], ["lineHeight", "16px"], ["color", "#FFFFFF"]]))]])]
