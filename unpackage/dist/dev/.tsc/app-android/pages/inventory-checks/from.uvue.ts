import _easycom_lili_universal_filter from '@/uni_modules/lili-universal-filter/components/lili-universal-filter/lili-universal-filter.uvue'
import _easycom_lili_UniversaForm from '@/uni_modules/lili-UniversaForm/components/lili-UniversaForm/lili-UniversaForm.uvue'
import { request, takeLatestResponseMessage } from '@/pkg/api/index.uts'
import { getInventoryCheckDetail, InventoryMutationData, updateInventoryCheck } from '@/pkg/api/modules/inventory'
import { showErrorToast } from '@/pkg/util/toast.uts'


const __sfc__ = defineComponent({
  __name: 'from',
  setup(__props) {
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

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
		let text = ''
		try {
			const errorText = JSON.stringify(error)
			if (errorText != null) text = errorText
		} catch (stringifyError) {
			text = ''
		}
		if (text != null && text != '') {
			let parsedError: UTSJSONObject | null = null
			try {
				const trimmedText = text.trim()
				if (trimmedText != '' && trimmedText.substring(0, 1) == '{') parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(trimmedText), " at pages/inventory-checks/from.uvue:60")
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
			if (message == fallback && text != '{}' && text != 'null') message = text
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
		return UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(trimmedText), " at pages/inventory-checks/from.uvue:88")
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
		parsed = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(trimmedText), " at pages/inventory-checks/from.uvue:102")
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

function buildOptionQuery(params: UTSJSONObject): UTSJSONObject {
	const pageValue = intValue(params['page'])
	const pageSizeValue = intValue(params['pageSize'])
	const query = { __$originalPosition: new UTSSourceMapPosition("query", "pages/inventory-checks/from.uvue", 138, 8),  page: pageValue <= 0 ? 1 : pageValue, page_size: pageSizeValue <= 0 ? 50 : pageSizeValue } as UTSJSONObject
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

function locationOption(item: UTSJSONObject): UTSJSONObject {
	const value = firstStringField(item, ['value', 'id', 'pk'])
	let text = firstStringField(item, ['text', 'label', 'name', 'name_cn', 'title'])
	if (text == '') text = value
	const code = stringValue(item['code'])
	return { value: value, text: text, subtitle: code == '' ? '' : '编码 ' + code } as UTSJSONObject
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

function firstArrayValue(value: any | null): string {
	if (value == null) return ''
	const text = JSON.stringify(value)
	if (text == null || text == '') return ''
	const trimmedText = text.trim()
	if (trimmedText == '' || trimmedText.substring(0, 1) != '[') return stringValue(value)
	let parsed: any[] | null = null
	try {
		parsed = UTSAndroid.consoleDebugError(JSON.parseArray<any>(trimmedText), " at pages/inventory-checks/from.uvue:217")
	} catch (error) {
		return ''
	}
	if (parsed == null || parsed!.length == 0) return ''
	const firstItem = parsed![0]
	const firstObject = parseObject(firstItem)
	if (firstObject != null) return buildOptionValue(firstObject!)
	return stringValue(firstItem)
}

function buildSelectResponse(rows: UTSJSONObject[]): UTSJSONObject {
	return { data: rows, results: rows, total: rows.length, total_count: rows.length } as UTSJSONObject
}

const refreshStorageKey = 'refresh:pages:inventory-checks:index'
const itemId = ref('')
const leaveSignal = ref(0)
const submitting = ref(false)
const initialData = ref<UTSJSONObject>({ location: '', location_text: '', category: '', category_text: '', planned_date: '', purpose: '', description: '' } as UTSJSONObject)
const liveFormData = ref<UTSJSONObject>({} as UTSJSONObject)

async function fetchLocationOptions(params: UTSJSONObject): Promise<UTSJSONObject> {
	const raw = await request('/api/inventory/locations/', 'GET', buildOptionQuery(params), true)
	const sourceRows = extractRows(raw)
	const rows: UTSJSONObject[] = []
	for (let index = 0; index < sourceRows.length; index += 1) rows.push(locationOption(sourceRows[index]))
	return buildSelectResponse(rows)
}

async function fetchCategoryOptions(params: UTSJSONObject): Promise<UTSJSONObject> {
	const query = buildOptionQuery(params)
	query['key'] = 'parent'
	const raw = await request('/api/categories/categories/options/', 'GET', query, true)
	const rows = convertCategoryTreeItems(extractCategoryTreeSource(raw))
	return buildSelectResponse(rows)
}

const formSections = ref<UTSJSONObject[]>([
	{
		key: 'base',
		title: '盘点信息',
		description: '',
		defaultOpen: true,
		fields: [
			{ key: 'location', textKey: 'location_text', label: '盘点位置', type: 'bottomSelect', required: true, title: '选择盘点位置', placeholder: '请选择盘点位置', subtitleKey: 'subtitle', showAddAction: false, showEditAction: false, fetchData: fetchLocationOptions } as UTSJSONObject,
			{ key: 'category', textKey: 'category_text', label: '盘点分类', type: 'bottomSelect', required: true, title: '选择盘点分类', placeholder: '请选择盘点分类', tree: true, childrenKey: 'children', expandOnClickNode: true, showAddAction: true, showEditAction: true, addPath: '/pages/category/from', editPath: '/pages/category/from', fetchData: fetchCategoryOptions } as UTSJSONObject,
			{ key: 'planned_date', label: '计划日期', type: 'datetime', required: true, showTime: false, defaultToToday: true, placeholder: '请选择计划日期' } as UTSJSONObject,
		] as UTSJSONObject[],
	} as UTSJSONObject,
	{
		key: 'notes',
		title: '目的与说明',
		description: '',
		defaultOpen: true,
		fields: [
			{ key: 'purpose', label: '盘点目的', type: 'input', placeholder: '请输入盘点目的' } as UTSJSONObject,
			{ key: 'description', label: '盘点说明', type: 'textarea', placeholder: '请输入盘点说明' } as UTSJSONObject,
		] as UTSJSONObject[],
	} as UTSJSONObject,
])

function markRefreshNeeded() { uni.setStorageSync(refreshStorageKey, '1') }

function goBackToList() {
	leaveSignal.value = leaveSignal.value + 1
	setTimeout(() => {
		uni.navigateBack({ delta: 1, fail: () => { uni.navigateTo({ url: '/pages/inventory-checks/index' }) } })
	}, 16)
}

function buildPayload(data: UTSJSONObject): InventoryMutationData | null {
	const locationId = intValue(data['location'])
	if (locationId <= 0) {
		uni.showToast({ title: '请选择盘点位置', icon: 'none', duration: 3500 })
		return null
	}
	const categoryId = intValue(data['category'])
	if (categoryId <= 0) {
		uni.showToast({ title: '请选择盘点分类', icon: 'none', duration: 3500 })
		return null
	}
	return {
		payload: {
			location: locationId,
			check_type: 'CATEGORY',
			categories: [categoryId],
			planned_date: stringValue(data['planned_date']),
			purpose: stringValue(data['purpose']),
			description: stringValue(data['description']),
		} as UTSJSONObject,
	} as InventoryMutationData
}

async function loadDetail(idText: string) {
	if (idText == '') return
	try {
		const detail = await getInventoryCheckDetail(idText)
		const categoryValue = firstArrayValue(detail['categories'])
		initialData.value = {
			location: stringValue(detail['location']),
			location_text: stringValue(detail['location_name']),
			category: categoryValue,
			category_text: stringValue(detail['category_names'], categoryValue),
			planned_date: stringValue(detail['planned_date']),
			purpose: stringValue(detail['purpose']),
			description: stringValue(detail['description']),
		} as UTSJSONObject
	} catch (error) {
		showErrorToast(parseErrorMessage(error, '盘点单详情加载失败'))
	}
}

async function persistForm(payload: UTSJSONObject) {
	if (submitting.value) return
	const rawData = payload['formData']
	const data = rawData == null ? ({} as UTSJSONObject) : (rawData as UTSJSONObject)
	const body = buildPayload(data)
	if (body == null) return
	submitting.value = true
	uni.showLoading({ title: '保存盘点单中...', mask: true })
	try {
		await updateInventoryCheck(itemId.value, body!)
		markRefreshNeeded()
		uni.showToast({ title: takeLatestResponseMessage('保存盘点单成功'), icon: 'success' })
		goBackToList()
	} catch (error) {
		showErrorToast(parseErrorMessage(error, '保存盘点单失败'))
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
function handleFormChange(payload: UTSJSONObject) {
	const rawData = payload['formData']
	if (rawData != null) liveFormData.value = rawData as UTSJSONObject
}
function handleBottomSelectAdd(payload: UTSJSONObject) { uni.showToast({ title: '该字段不支持新增', icon: 'none', duration: 3500 }) }
function handleBottomSelectEdit(payload: UTSJSONObject) { uni.showToast({ title: '该字段不支持编辑', icon: 'none', duration: 3500 }) }

onLoad((query: OnLoadOptions) => {
	const idValue = query['id']
	if (idValue == null || idValue == '') {
		uni.redirectTo({ url: '/pages/inventory-checks/create' })
		return
	}
	itemId.value = '' + idValue
	loadDetail(itemId.value)
})

return (): any | null => {

const _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter",_easycom_lili_universal_filter)
const _component_lili_UniversaForm = resolveEasyComponent("lili-UniversaForm",_easycom_lili_UniversaForm)

  return _cE("view", _uM({ class: "page" }), [
    _cV(_component_lili_universal_filter, _uM({
      title: "编辑盘点单",
      showBack: true,
      showSearch: false,
      showHome: true,
      homePath: "/pages/inventory-checks/index",
      backgroundColor: "#EEF2F7"
    })),
    _cE("view", _uM({ class: "page-content" }), [
      _cV(_component_lili_UniversaForm, _uM({
        mode: "edit",
        formSections: unref(formSections),
        initialData: unref(initialData),
        leaveSignal: unref(leaveSignal),
        onSubmit: handleSubmit,
        onCancel: handleCancel,
        onDiscardLeave: handleDiscardLeave,
        onSaveRequest: handleSaveRequest,
        onDirtyChange: handleDirtyChange,
        onFormChange: handleFormChange,
        onBottomSelectAdd: handleBottomSelectAdd,
        onBottomSelectEdit: handleBottomSelectEdit
      }), null, 8 /* PROPS */, ["formSections", "initialData", "leaveSignal"])
    ])
  ])
}
}

})
export default __sfc__
const GenPagesInventoryChecksFromStyles = [_uM([["page", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#EEF2F7"]]))], ["page-content", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["paddingBottom", 0]]))]])]
