import _easycom_lili_universal_filter from '@/uni_modules/lili-universal-filter/components/lili-universal-filter/lili-universal-filter.uvue'
import _easycom_lili_UniversaForm from '@/uni_modules/lili-UniversaForm/components/lili-UniversaForm/lili-UniversaForm.uvue'
import { computed } from 'vue'
import { takeLatestResponseMessage } from '@/pkg/api/index.uts'
import { authState } from '@/store/auth'
import { batchUploadMediaFiles, MediaBatchUploadItem } from '@/pkg/api/modules/media.uts'
import { createExpense, ExpenseItem, ExpenseMutationData, ExpenseOptionGroup, getExpenseDetail, getExpenseOptions, updateExpense } from '@/pkg/api/modules/expenses.uts'
import { createAsyncGuard } from '@/uni_modules/lili-async-guard'
import { showErrorToast } from '@/pkg/util/toast.uts'

type SelectOption = { __$originalPosition?: UTSSourceMapPosition<"SelectOption", "pages/expenses/from.uvue", 42, 6>;
	value: string
	text: string
}


const __sfc__ = defineComponent({
  __name: 'from',
  setup(__props) {
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

const expenseListRefreshStorageKey = 'refresh:pages:expenses:index'
const formMode = ref('create')
const expenseId = ref('')
const leaveSignal = ref(0)
const submitting = ref(false)
const savingVisible = ref(false)
const savingText = ref('处理中...')
const pageTaskGuard = createAsyncGuard()
const initialData = ref<UTSJSONObject>({
	expenditure_type_id: '',
	expenditure_type_text: '',
	supplier_id: '',
	supplier_text: '',
	amount: '',
	expenditure_date: '',
	invoice_number: '',
	description: '',
	note: '',
	images: [] as string[],
	imageItems: [] as UTSJSONObject[],
} as UTSJSONObject)

function getStringField(obj: UTSJSONObject, key: string, fallback: string = ''): string {
	const value = obj[key]
	if (value == null) return fallback
	return '' + value
}

function getArrayField(obj: UTSJSONObject, key: string): string[] {
	const value = obj[key]
	if (value == null) return []
	return value as string[]
}

function buildUploadHeaders(): UTSJSONObject {
	const headers = { __$originalPosition: new UTSSourceMapPosition("headers", "pages/expenses/from.uvue", 82, 8), } as UTSJSONObject
	if (authState.token != '') headers['Authorization'] = authState.token
	return headers
}

function twoDigit(value: number): string {
	if (value < 10) return '0' + value.toString()
	return value.toString()
}

function todayText(): string {
	const now = new Date()
	const y = now.getFullYear().toString()
	const m = twoDigit(now.getMonth() + 1)
	const d = twoDigit(now.getDate())
	return y + '-' + m + '-' + d
}

function parseErrorMessage(error: any, fallback: string): string {
	let message = fallback
	if (error != null) {
		const errorText = JSON.stringify(error)
		if (errorText != null && errorText != '') {
			const parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/expenses/from.uvue:105")
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

function buildOptionsFromGroup(group: ExpenseOptionGroup | null): SelectOption[] {
	if (group == null) return []
	const result: SelectOption[] = []
	for (let index = 0; index < group.items.length; index += 1) {
		const item = group.items[index]
		result.push({ value: item.value, text: item.label } as SelectOption)
	}
	return result
}

function findOptionGroup(groups: ExpenseOptionGroup[], key: string): ExpenseOptionGroup | null {
	for (let index = 0; index < groups.length; index += 1) {
		const group = groups[index]
		if (group.key == key) return group
	}
	for (let index = 0; index < groups.length; index += 1) {
		const group = groups[index]
		if (group.label.indexOf(key == 'supplier' ? '供应商' : '类型') >= 0) return group
	}
	return null
}

async function fetchExpenseTypeOptions(params: UTSJSONObject): Promise<UTSJSONObject> {
	const keyword = getStringField(params, 'keyword')
	const id = getStringField(params, 'id')
	const response = await getExpenseOptions('expenditure_type', keyword == '' ? null : keyword, 50)
	const options = buildOptionsFromGroup(findOptionGroup(response.groups, 'expenditure_type'))
	return buildSelectResponse(options, { keyword: keyword, id: id } as UTSJSONObject)
}

async function fetchSupplierOptions(params: UTSJSONObject): Promise<UTSJSONObject> {
	const keyword = getStringField(params, 'keyword')
	const id = getStringField(params, 'id')
	const response = await getExpenseOptions('supplier', keyword == '' ? null : keyword, 50)
	const options = buildOptionsFromGroup(findOptionGroup(response.groups, 'supplier'))
	return buildSelectResponse(options, { keyword: '', id: id } as UTSJSONObject)
}

function buildInitialDataFromExpense(item: ExpenseItem): UTSJSONObject {
	const images: string[] = []
	const imageItems: UTSJSONObject[] = []
	for (let index = 0; index < item.media_files.length; index += 1) {
		const mediaFile = item.media_files[index]
		let imageUrl = ''
		if (mediaFile.signed_thumbnail_url != '') imageUrl = mediaFile.signed_thumbnail_url
		else if (mediaFile.thumbnail_url != '') imageUrl = mediaFile.thumbnail_url
		else if (mediaFile.signed_url != '') imageUrl = mediaFile.signed_url
		else if (mediaFile.file_url != '') imageUrl = mediaFile.file_url
		if (imageUrl != '') {
			images.push(imageUrl)
			imageItems.push({ id: mediaFile.id, path: imageUrl, url: imageUrl, isRemote: true } as UTSJSONObject)
		}
	}
	return {
		expenditure_type_id: item.expenditure_type <= 0 ? '' : item.expenditure_type.toString(),
		expenditure_type_text: item.expenditure_type_name,
		supplier_id: item.supplier <= 0 ? '' : item.supplier.toString(),
		supplier_text: item.supplier_name,
		amount: item.amount,
		expenditure_date: item.expenditure_date,
		invoice_number: item.invoice_number == null ? '' : item.invoice_number,
		description: item.description == null ? '' : item.description,
		note: item.note == null ? '' : item.note,
		images: images,
		imageItems: imageItems,
	} as UTSJSONObject
}

const formSections = ref<UTSJSONObject[]>([
	{
		key: 'base',
		title: '基础信息',
		description: '',
		defaultOpen: true,
		fields: [
			{ key: 'amount', label: '支出金额', type: 'number', required: true, placeholder: '请输入支出金额' } as UTSJSONObject,
			{ key: 'expenditure_date', label: '支出日期', type: 'datetime', required: true, showTime: false, defaultToToday: true, title: '选择支出日期', placeholder: '请选择支出日期' } as UTSJSONObject,
			{ key: 'expenditure_type_id', textKey: 'expenditure_type_text', label: '支出类型', type: 'bottomSelect', title: '选择支出类型', placeholder: '请选择支出类型', showAddAction: false, showEditAction: false, fetchData: fetchExpenseTypeOptions } as UTSJSONObject,
			{ key: 'supplier_id', textKey: 'supplier_text', label: '供应商', type: 'bottomSelect', title: '选择供应商', placeholder: '可选供应商', showAddAction: true, showEditAction: true, addPath: '/pages/suppliers/from', editPath: '/pages/suppliers/from', fetchData: fetchSupplierOptions } as UTSJSONObject,
			{ key: 'invoice_number', label: '发票号码', type: 'input', placeholder: '请输入发票号码' } as UTSJSONObject,
			{ key: 'description', label: '支出描述', type: 'textarea', placeholder: '请输入支出描述' } as UTSJSONObject,
			{ key: 'note', label: '备注', type: 'textarea', placeholder: '请输入备注' } as UTSJSONObject,
			{ key: 'images', label: '附件图片', type: 'upload', action: '', name: 'files', max: 9, uploadText: '上传凭证', fileItemsKey: 'imageItems', headers: buildUploadHeaders(), formData: {} as UTSJSONObject } as UTSJSONObject,
		] as UTSJSONObject[],
	} as UTSJSONObject,
])

const pageTitle = computed((): string => formMode.value == 'edit' ? '编辑支出记录' : '新建支出记录')

function markExpenseListRefreshNeeded() {
	uni.setStorageSync(expenseListRefreshStorageKey, '1')
}

function goBackToList(markLeaving: boolean = true) {
	if (markLeaving) {
		pageTaskGuard.leave()
		savingVisible.value = false
		uni.hideLoading()
	}
	leaveSignal.value = leaveSignal.value + 1
	setTimeout(() => {
		uni.navigateBack({ delta: 1, fail: () => { uni.navigateTo({ url: '/pages/expenses/index' }) } })
	}, 16)
}

async function loadExpenseDetailData(idText: string) {
	if (idText == '') return
	try {
		const detail = await getExpenseDetail(idText)
		initialData.value = buildInitialDataFromExpense(detail)
	} catch (error) {
		showErrorToast(parseErrorMessage(error, '支出详情加载失败'))
	}
}

function buildMutationPayload(data: UTSJSONObject): ExpenseMutationData {
	return {
		expenditure_type_id: getStringField(data, 'expenditure_type_id') == '' ? null : getStringField(data, 'expenditure_type_id'),
		supplier_id: getStringField(data, 'supplier_id') == '' ? null : getStringField(data, 'supplier_id'),
		amount: getStringField(data, 'amount'),
		expenditure_date: getStringField(data, 'expenditure_date'),
		invoice_number: getStringField(data, 'invoice_number') == '' ? null : getStringField(data, 'invoice_number'),
		description: getStringField(data, 'description') == '' ? null : getStringField(data, 'description'),
		note: getStringField(data, 'note') == '' ? null : getStringField(data, 'note'),
	} as ExpenseMutationData
}

function isRemoteImagePath(path: string): boolean {
	if (path == '') return false
	return path.startsWith('http://') || path.startsWith('https://')
}

function collectPendingImagePaths(formDataObject: UTSJSONObject): string[] {
	const images = getArrayField(formDataObject, 'images')
	const result: string[] = []
	for (let index = 0; index < images.length; index += 1) {
		const imagePath = images[index]
		if (imagePath != '' && !isRemoteImagePath(imagePath)) result.push(imagePath)
	}
	return result
}

async function uploadPendingImages(formDataObject: UTSJSONObject, contentTypeModel: string) {
	if (expenseId.value == '') return
	const pendingImagePaths = collectPendingImagePaths(formDataObject)
	if (pendingImagePaths.length == 0) return
	if (contentTypeModel == '') throw new Error('缺少上传参数: content_type_model')
	const uploadItems: MediaBatchUploadItem[] = []
	for (let index = 0; index < pendingImagePaths.length; index += 1) {
		uploadItems.push({
			filePath: pendingImagePaths[index],
			name: 'files',
			formData: { content_type_model: contentTypeModel, object_id: expenseId.value } as UTSJSONObject,
		} as MediaBatchUploadItem)
	}
	const uploadResult = await batchUploadMediaFiles(uploadItems)
	if (uploadResult.failItems.length > 0) {
		const firstFail = uploadResult.failItems[0]
		throw new Error(getStringField(firstFail, 'message', '图片上传失败'))
	}
}

async function persistForm(payload: UTSJSONObject) {
	if (submitting.value) return
	const formDataValue = payload['formData']
	const data = formDataValue == null ? ({} as UTSJSONObject) : (formDataValue as UTSJSONObject)
	const body = buildMutationPayload(data)
	if (body.amount == '' || parseFloat(body.amount) <= 0 || isNaN(parseFloat(body.amount))) {
		uni.showToast({ title: '请输入有效的支出金额', icon: 'none', duration: 3500 })
		return
	}
	if (body.expenditure_date == '') {
		uni.showToast({ title: '请选择支出日期', icon: 'none', duration: 3500 })
		return
	}
	const uploadContentTypeModel = getStringField(payload, 'uploadContentTypeModel').trim()
	const actionText = formMode.value == 'edit' ? '保存支出记录' : '创建支出记录'
	const taskToken = pageTaskGuard.begin()
	submitting.value = true
	savingText.value = actionText + '中...'
	savingVisible.value = true
	uni.showLoading({ title: savingText.value, mask: true })
	try {
		let successMessage = actionText + '成功'
		if (formMode.value == 'edit' && expenseId.value != '') {
			await updateExpense(expenseId.value, body)
			successMessage = takeLatestResponseMessage(successMessage)
			savingText.value = '上传凭证中...'
			await uploadPendingImages(data, uploadContentTypeModel)
		} else {
			const createdExpense = await createExpense(body)
			successMessage = takeLatestResponseMessage(successMessage)
			expenseId.value = createdExpense.id.toString()
			try {
				savingText.value = '上传凭证中...'
				await uploadPendingImages(data, uploadContentTypeModel)
			} catch (uploadError) {
				throw new Error('支出记录已创建，但凭证上传失败')
			}
		}
		markExpenseListRefreshNeeded()
		if (!pageTaskGuard.canApply(taskToken)) return
		uni.showToast({ title: successMessage, icon: 'success' })
		goBackToList(false)
	} catch (error) {
		if (!pageTaskGuard.canApply(taskToken)) return
		showErrorToast(parseErrorMessage(error, actionText + '失败'))
	} finally {
		if (pageTaskGuard.canApply(taskToken)) {
			savingVisible.value = false
			uni.hideLoading()
			submitting.value = false
		}
	}
}

async function handleSubmit(payload: UTSJSONObject) { await persistForm(payload) }
async function handleSaveRequest(payload: UTSJSONObject) { await persistForm(payload) }
function handleCancel(payload: UTSJSONObject) { const changed = payload['hasChanges']; if (changed != null && (changed as boolean)) return; goBackToList() }
function handleDiscardLeave(payload: UTSJSONObject) { goBackToList() }
function handleDirtyChange(value: boolean) {}
function handleBottomSelectAdd(payload: UTSJSONObject) { uni.showToast({ title: '请先在后台维护支出类型或供应商', icon: 'none', duration: 3500 }) }
function handleBottomSelectEdit(payload: UTSJSONObject) { uni.showToast({ title: '该字段不支持直接编辑', icon: 'none', duration: 3500 }) }
function handleUpload(payload: UTSJSONObject) { uni.showToast({ title: '凭证已加入待保存列表', icon: 'none', duration: 3500 }) }
function handleUploadDelete(payload: UTSJSONObject) { uni.showToast({ title: '凭证已删除', icon: 'success' }) }
function handleUploadError(payload: UTSJSONObject) { showErrorToast('凭证上传失败') }

onLoad((event: OnLoadOptions) => {
	pageTaskGuard.reset()
	const idValue = event['id']
	expenseId.value = idValue == null ? '' : (idValue as string)
	formMode.value = expenseId.value == '' ? 'create' : 'edit'
	initialData.value = {
		expenditure_type_id: '',
		expenditure_type_text: '',
		supplier_id: '',
		supplier_text: '',
		amount: '',
		expenditure_date: todayText(),
		invoice_number: '',
		description: '',
		note: '',
		images: [] as string[],
		imageItems: [] as UTSJSONObject[],
	} as UTSJSONObject
	if (formMode.value == 'edit') loadExpenseDetailData(expenseId.value)
})

onUnload(() => {
	pageTaskGuard.leave()
	uni.hideLoading()
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
      homePath: "/pages/expenses/index",
      backgroundColor: "#EEF2F7"
    }), null, 8 /* PROPS */, ["title"]),
    _cE("view", _uM({ class: "page-content" }), [
      _cV(_component_lili_UniversaForm, _uM({
        mode: unref(formMode),
        formSections: unref(formSections),
        initialData: unref(initialData),
        leaveSignal: unref(leaveSignal),
        uploadContentTypeModel: "expenditure",
        onSubmit: handleSubmit,
        onCancel: handleCancel,
        onDiscardLeave: handleDiscardLeave,
        onSaveRequest: handleSaveRequest,
        onDirtyChange: handleDirtyChange,
        onBottomSelectAdd: handleBottomSelectAdd,
        onBottomSelectEdit: handleBottomSelectEdit,
        onUpload: handleUpload,
        onUploadDelete: handleUploadDelete,
        onUploadError: handleUploadError
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
const GenPagesExpensesFromStyles = [_uM([["page", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#EEF2F7"]]))], ["page-content", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["paddingBottom", 0]]))], ["page-saving-mask", _pS(_uM([["position", "fixed"], ["left", 0], ["right", 0], ["top", 0], ["bottom", 0], ["backgroundColor", "rgba(15,23,42,0.18)"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["page-saving-card", _pS(_uM([["minWidth", 160], ["minHeight", 64], ["borderTopLeftRadius", 10], ["borderTopRightRadius", 10], ["borderBottomRightRadius", 10], ["borderBottomLeftRadius", 10], ["backgroundColor", "#FFFFFF"], ["alignItems", "center"], ["justifyContent", "center"], ["paddingLeft", 18], ["paddingRight", 18]]))], ["page-saving-text", _pS(_uM([["fontSize", 14], ["color", "#111827"]]))]])]
