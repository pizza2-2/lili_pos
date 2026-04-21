import _easycom_lili_universal_filter from '@/uni_modules/lili-universal-filter/components/lili-universal-filter/lili-universal-filter.uvue'
import _easycom_lili_UniversaForm from '@/uni_modules/lili-UniversaForm/components/lili-UniversaForm/lili-UniversaForm.uvue'
import { computed } from 'vue'
import { getSupplierDetail } from '@/pkg/api/modules/suppliers'
import { authState } from '@/store/auth'
import { batchUploadMediaFiles, MediaBatchUploadItem } from '@/pkg/api/modules/media.uts'
import { createTransaction, getTransactionDetail, getTransactionOptions, TransactionItem, TransactionMutationData, TransactionOptionGroup, updateTransaction } from '@/pkg/api/modules/transactions.uts'

type SelectOption = { __$originalPosition?: UTSSourceMapPosition<"SelectOption", "pages/transactions/from.uvue", 49, 6>;
	value: string
	text: string
}


const __sfc__ = defineComponent({
  __name: 'from',
  setup(__props) {
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

const transactionListRefreshStorageKey = 'refresh:pages:transactions:index'

const formMode = ref('create')
const transactionId = ref('')
const supplierId = ref('')
const leaveSignal = ref(0)
const submitting = ref(false)
const savingVisible = ref(false)
const savingText = ref('处理中...')
const initialData = ref<UTSJSONObject>({
	supplier_id: '',
	supplier_name: '',
	transaction_number: '',
	transaction_type: '',
	amount: '',
	transaction_date: '',
	note: '',
	images: [] as string[],
	imageItems: [] as UTSJSONObject[],
} as UTSJSONObject)

function getStringField(obj: UTSJSONObject, key: string, fallback: string = '') : string {
	const value = obj[key]
	if (value == null) {
		return fallback
	}
	return '' + value
}

function getArrayField(obj: UTSJSONObject, key: string) : string[] {
	const value = obj[key]
	if (value == null) {
		return []
	}
	return value as string[]
}

function buildUploadHeaders() : UTSJSONObject {
	const headers = { __$originalPosition: new UTSSourceMapPosition("headers", "pages/transactions/from.uvue", 90, 8), } as UTSJSONObject
	if (authState.token != '') {
		headers['Authorization'] = authState.token
	}
	return headers
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
			const parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/transactions/from.uvue:106")
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

function buildInitialDataFromTransaction(item: TransactionItem) : UTSJSONObject {
	const images: string[] = []
	const imageItems: UTSJSONObject[] = []
	for (let index = 0; index < item.media_files.length; index += 1) {
		const mediaFile = item.media_files[index]
		let imageUrl = ''
		if (mediaFile.signed_thumbnail_url != '') {
			imageUrl = mediaFile.signed_thumbnail_url
		} else if (mediaFile.thumbnail_url != '') {
			imageUrl = mediaFile.thumbnail_url
		} else if (mediaFile.signed_url != '') {
			imageUrl = mediaFile.signed_url
		} else if (mediaFile.file_url != '') {
			imageUrl = mediaFile.file_url
		}
		if (imageUrl != '') {
			images.push(imageUrl)
			imageItems.push({
				id: mediaFile.id,
				path: imageUrl,
				url: imageUrl,
				isRemote: true,
			} as UTSJSONObject)
		}
	}
	return {
		supplier_id: item.supplier.toString(),
		supplier_name: item.supplier_name,
		transaction_number: item.transaction_number,
		transaction_type: item.transaction_type.toString(),
		amount: item.amount,
		transaction_date: item.transaction_date,
		note: item.note == null ? '' : item.note,
		images: images,
		imageItems: imageItems,
	} as UTSJSONObject
}

function buildSelectResponse(source: SelectOption[], params: UTSJSONObject) : UTSJSONObject {
	const keyword = getStringField(params, 'keyword').toLowerCase()
	const id = getStringField(params, 'id')
	const result: UTSJSONObject[] = []
	for (let index = 0; index < source.length; index += 1) {
		const option = source[index]
		if (id != '' && option.value != id) {
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

function buildOptionsFromGroup(group: TransactionOptionGroup | null): SelectOption[] {
	if (group == null) {
		return []
	}
	const result: SelectOption[] = []
	for (let index = 0; index < group.items.length; index += 1) {
		const item = group.items[index]
		result.push({
			value: item.value,
			text: item.label,
		} as SelectOption)
	}
	return result
}

function findTransactionTypeGroup(groups: TransactionOptionGroup[]): TransactionOptionGroup | null {
	for (let index = 0; index < groups.length; index += 1) {
		const group = groups[index]
		if (group.key == 'transaction_type' || group.key == 'transaction_types') {
			return group
		}
	}
	for (let index = 0; index < groups.length; index += 1) {
		const group = groups[index]
		if (group.label.indexOf('类型') >= 0) {
			return group
		}
	}
	if (groups.length > 0) {
		return groups[0]
	}
	return null
}

async function fetchTransactionTypeOptions(params: UTSJSONObject) : Promise<UTSJSONObject> {
	const keyword = getStringField(params, 'keyword')
	const id = getStringField(params, 'id')
	const response = await getTransactionOptions('transaction_type', keyword == '' ? null : keyword, 50)
	let options = buildOptionsFromGroup(findTransactionTypeGroup(response.groups))
	if (options.length == 0) {
		const fallbackResponse = await getTransactionOptions(null, keyword == '' ? null : keyword, 50)
		options = buildOptionsFromGroup(findTransactionTypeGroup(fallbackResponse.groups))
	}
	return buildSelectResponse(options, {
		keyword: keyword,
		id: id,
	} as UTSJSONObject)
}

async function loadSupplierName() {
	if (supplierId.value == '') {
		return
	}
	try {
		const supplierDetail = await getSupplierDetail(supplierId.value)
		initialData.value = {
			supplier_id: getStringField(initialData.value, 'supplier_id'),
			supplier_name: supplierDetail.name,
			transaction_number: getStringField(initialData.value, 'transaction_number'),
			transaction_type: getStringField(initialData.value, 'transaction_type'),
			amount: getStringField(initialData.value, 'amount'),
			transaction_date: getStringField(initialData.value, 'transaction_date'),
			note: getStringField(initialData.value, 'note'),
			images: getArrayField(initialData.value, 'images'),
			imageItems: initialData.value['imageItems'] == null ? ([] as UTSJSONObject[]) : (initialData.value['imageItems'] as UTSJSONObject[]),
		} as UTSJSONObject
	} catch (error) {
		initialData.value = {
			supplier_id: getStringField(initialData.value, 'supplier_id'),
			supplier_name: '',
			transaction_number: getStringField(initialData.value, 'transaction_number'),
			transaction_type: getStringField(initialData.value, 'transaction_type'),
			amount: getStringField(initialData.value, 'amount'),
			transaction_date: getStringField(initialData.value, 'transaction_date'),
			note: getStringField(initialData.value, 'note'),
			images: getArrayField(initialData.value, 'images'),
			imageItems: initialData.value['imageItems'] == null ? ([] as UTSJSONObject[]) : (initialData.value['imageItems'] as UTSJSONObject[]),
		} as UTSJSONObject
	}
}

const formSections = ref<UTSJSONObject[]>([
	{
		key: 'base',
		title: '基础信息',
		description: '',
		defaultOpen: true,
		fields: [
			{
				key: 'supplier_id',
				type: 'input',
				hidden: true,
			} as UTSJSONObject,
			{
				key: 'supplier_name',
				label: '供应商',
				type: 'input',
				readonly: true,
				placeholder: '供应商名称',
			} as UTSJSONObject,
			{
				key: 'transaction_number',
				label: '采购单号',
				type: 'input',
				readonly: true,
				placeholder: '提交后由系统自动生成',
			} as UTSJSONObject,
			{
				key: 'transaction_type',
				label: '采购类型',
				type: 'bottomSelect',
				title: '选择采购类型',
				placeholder: '请选择采购类型',
				required: true,
				showAddAction: false,
				showEditAction: false,
				fetchData: fetchTransactionTypeOptions,
			} as UTSJSONObject,
			{
				key: 'amount',
				label: '金额',
				type: 'number',
				required: true,
				placeholder: '请输入金额',
			} as UTSJSONObject,
			{
				key: 'transaction_date',
				label: '采购时间',
				type: 'datetime',
				required: true,
				datePlaceholder: '选择日期',
				timePlaceholder: '选择时间',
			} as UTSJSONObject,
			{
				key: 'note',
				label: '备注',
				type: 'textarea',
				placeholder: '请输入备注',
			} as UTSJSONObject,
		] as UTSJSONObject[],
	} as UTSJSONObject,
	{
		key: 'media',
		title: '图片资料',
		description: '可同时上传多张图片',
		defaultOpen: true,
		fields: [
			{
				key: 'images',
				label: '采购图片',
				type: 'upload',
				action: '',
				name: 'files',
				max: 9,
				uploadText: '上传图片',
				fileItemsKey: 'imageItems',
				headers: buildUploadHeaders(),
				formData: {} as UTSJSONObject,
			} as UTSJSONObject,
		] as UTSJSONObject[],
	} as UTSJSONObject,
])

const pageTitle = computed(() : string => {
	return formMode.value == 'edit' ? '编辑采购记录' : '新建采购记录'
})

function clearDraftStorage() {
}

function markTransactionListRefreshNeeded() {
	uni.setStorageSync(transactionListRefreshStorageKey, '1')
}

async function loadTransactionDetailData(idText: string) {
	if (idText == '') {
		return
	}
	try {
		const detail = await getTransactionDetail(idText)
		supplierId.value = detail.supplier.toString()
		initialData.value = buildInitialDataFromTransaction(detail)
	} catch (error) {
		uni.showToast({
			title: parseErrorMessage(error, '采购记录详情加载失败'),
			icon: 'none',
		})
	}
}

function goBackToList() {
	leaveSignal.value = leaveSignal.value + 1
	setTimeout(() => {
		uni.navigateBack({
			delta: 1,
			fail: () => {
				if (supplierId.value != '') {
					uni.reLaunch({
						url: '/pages/transactions/index?supplier_id=' + supplierId.value,
					})
					return
				}
				uni.reLaunch({
					url: '/pages/suppliers/index',
				})
			},
		})
	}, 16)
}

function buildTransactionMutationPayload(formDataObject: UTSJSONObject) : TransactionMutationData {
	const supplierValue = getStringField(formDataObject, 'supplier_id')
	const transactionTypeValue = getStringField(formDataObject, 'transaction_type')
	const amountValue = getStringField(formDataObject, 'amount')
	const transactionDateValue = getStringField(formDataObject, 'transaction_date')
	const noteValue = getStringField(formDataObject, 'note')
	const transactionNumberValue = getStringField(formDataObject, 'transaction_number')
	return {
		supplier: supplierValue,
		transaction_type: transactionTypeValue,
		amount: amountValue,
		transaction_date: transactionDateValue,
		transaction_number: transactionNumberValue == '' ? null : transactionNumberValue,
		note: noteValue == '' ? null : noteValue,
	} as TransactionMutationData
}

function isRemoteImagePath(path: string) : boolean {
	if (path == '') {
		return false
	}
	return path.startsWith('http://') || path.startsWith('https://')
}

function collectPendingImagePaths(formDataObject: UTSJSONObject) : string[] {
	const images = getArrayField(formDataObject, 'images')
	const result: string[] = []
	for (let index = 0; index < images.length; index += 1) {
		const imagePath = images[index]
		if (imagePath == '') {
			continue
		}
		if (isRemoteImagePath(imagePath)) {
			continue
		}
		result.push(imagePath)
	}
	return result
}

async function uploadPendingTransactionImages(formDataObject: UTSJSONObject, contentTypeModel: string) {
	if (transactionId.value == '') {
		return
	}
	const pendingImagePaths = collectPendingImagePaths(formDataObject)
	if (pendingImagePaths.length == 0) {
		return
	}
	if (contentTypeModel == '') {
		throw new Error('缺少上传参数: content_type_model')
	}
	const uploadItems: MediaBatchUploadItem[] = []
	for (let index = 0; index < pendingImagePaths.length; index += 1) {
		uploadItems.push({
			filePath: pendingImagePaths[index],
			name: 'files',
			formData: {
				content_type_model: contentTypeModel,
				object_id: transactionId.value,
			} as UTSJSONObject,
		} as MediaBatchUploadItem)
	}
	const uploadResult = await batchUploadMediaFiles(uploadItems)
	if (uploadResult.failItems.length > 0) {
		const firstFail = uploadResult.failItems[0]
		const failMessage = getStringField(firstFail, 'message', '图片上传失败')
		throw new Error(failMessage)
	}
}

async function persistForm(payload: UTSJSONObject) {
	if (submitting.value) {
		return
	}
	const formDataValue = payload['formData']
	const data = formDataValue == null ? ({} as UTSJSONObject) : (formDataValue as UTSJSONObject)
	const uploadContentTypeModel = getStringField(payload, 'uploadContentTypeModel').trim()
	const actionText = formMode.value == 'edit' ? '保存修改' : '创建采购记录'
	submitting.value = true
	savingText.value = actionText + '中...'
	savingVisible.value = true
	uni.showLoading({
		title: savingText.value,
		mask: true,
	})
	try {
		const body = buildTransactionMutationPayload(data)
		if (body.supplier == '') {
			throw new Error('供应商ID缺失')
		}
		if (formMode.value == 'edit' && transactionId.value != '') {
			savingText.value = '保存修改中...'
			await updateTransaction(transactionId.value, body)
			savingText.value = '上传图片中...'
			await uploadPendingTransactionImages(data, uploadContentTypeModel)
		} else {
			savingText.value = '创建采购记录中...'
			const createdTransaction = await createTransaction(body)
			transactionId.value = createdTransaction.id.toString()
			supplierId.value = createdTransaction.supplier.toString()
			try {
				savingText.value = '上传图片中...'
				await uploadPendingTransactionImages(data, uploadContentTypeModel)
			} catch (uploadError) {
				throw new Error('采购记录已创建，但图片上传失败')
			}
		}
		clearDraftStorage()
		markTransactionListRefreshNeeded()
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
	clearDraftStorage()
	goBackToList()
}

function handleDirtyChange(value: boolean) {
}

function handleBottomSelectAdd(payload: UTSJSONObject) {
	uni.showToast({
		title: '采购类型不支持新增',
		icon: 'none',
	})
}

function handleBottomSelectEdit(payload: UTSJSONObject) {
	uni.showToast({
		title: '采购类型不支持编辑',
		icon: 'none',
	})
}

function handleUpload(payload: UTSJSONObject) {
	uni.showToast({
		title: '图片已加入待保存列表',
		icon: 'none',
	})
}

function handleUploadDelete(payload: UTSJSONObject) {
	uni.showToast({
		title: '图片已删除',
		icon: 'success',
	})
}

function handleUploadError(payload: UTSJSONObject) {
	const rawPayload = payload['payload']
	if (rawPayload != null) {
		const payloadObject = rawPayload as UTSJSONObject
		const message = getStringField(payloadObject, 'message')
		if (message != '') {
			uni.showToast({
				title: message,
				icon: 'none',
			})
			return
		}
	}
	uni.showToast({
		title: '图片上传失败',
		icon: 'none',
	})
}

onLoad((event : OnLoadOptions) => {
	leaveSignal.value = 0
	const idValue = event['id']
	transactionId.value = idValue == null ? '' : (idValue as string)
	const supplierIdValue = event['supplier_id']
	supplierId.value = supplierIdValue == null ? '' : (supplierIdValue as string)
	initialData.value = {
		supplier_id: supplierId.value,
		supplier_name: '',
		transaction_number: '',
		transaction_type: '',
		amount: '',
		transaction_date: '',
		note: '',
		images: [] as string[],
		imageItems: [] as UTSJSONObject[],
	} as UTSJSONObject
	formMode.value = transactionId.value == '' ? 'create' : 'edit'
	if (formMode.value == 'edit') {
		loadTransactionDetailData(transactionId.value)
		return
	}
	loadSupplierName()
	clearDraftStorage()
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
      homePath: "/pages/suppliers/index",
      backgroundColor: "#EEF2F7"
    }), null, 8 /* PROPS */, ["title"]),
    _cE("view", _uM({ class: "page-content" }), [
      _cV(_component_lili_UniversaForm, _uM({
        mode: unref(formMode),
        formSections: unref(formSections),
        initialData: unref(initialData),
        leaveSignal: unref(leaveSignal),
        uploadContentTypeModel: "suppliertransaction",
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
const GenPagesTransactionsFromStyles = [_uM([["page", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["position", "relative"], ["backgroundColor", "#EEF2F7"]]))], ["page-content", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"]]))], ["page-saving-mask", _pS(_uM([["position", "absolute"], ["left", 0], ["top", 0], ["right", 0], ["bottom", 0], ["alignItems", "center"], ["justifyContent", "center"], ["backgroundColor", "rgba(17,24,39,0.18)"]]))], ["page-saving-card", _pS(_uM([["paddingTop", 16], ["paddingRight", 20], ["paddingBottom", 16], ["paddingLeft", 20], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["backgroundColor", "#111827"]]))], ["page-saving-text", _pS(_uM([["fontSize", 14], ["fontWeight", "600"], ["color", "#FFFFFF"]]))]])]
