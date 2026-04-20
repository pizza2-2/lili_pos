import _easycom_lili_universal_filter from '@/uni_modules/lili-universal-filter/components/lili-universal-filter/lili-universal-filter.uvue'
import _easycom_lili_UniversaForm from '@/uni_modules/lili-UniversaForm/components/lili-UniversaForm/lili-UniversaForm.uvue'
import { computed } from 'vue'
import { SupplierItem, SupplierMutationData, createSupplier, getSupplierDetail, updateSupplier } from '@/pkg/api/modules/suppliers'
import { batchUploadMediaFiles, MediaBatchUploadItem } from '@/pkg/api/modules/media.uts'
import { authState } from '@/store/auth'

type SelectOption = { __$originalPosition?: UTSSourceMapPosition<"SelectOption", "pages/suppliers/from.uvue", 48, 6>;
	value: string
	text: string
}


const __sfc__ = defineComponent({
  __name: 'from',
  setup(__props) {
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

const supplierListRefreshStorageKey = 'refresh:pages:suppliers:index'

const formMode = ref('create')
const supplierId = ref('')
const leaveSignal = ref(0)
const submitting = ref(false)
const savingVisible = ref(false)
const savingText = ref('处理中...')
const initialData = ref<UTSJSONObject>({
	code: '',
	name: '',
	contact: '',
	phone: '',
	address: '',
	description: '',
	is_active: 'true',
	images: [] as string[],
	imageItems: [] as UTSJSONObject[],
} as UTSJSONObject)

const statusOptions = ref<SelectOption[]>([
	{ value: 'true', text: '启用' } as SelectOption,
	{ value: 'false', text: '停用' } as SelectOption,
])

function getStringField(obj: UTSJSONObject, key: string, fallback: string = '') : string {
	const value = obj[key]
	if (value == null) return fallback
	return '' + value
}

function getArrayField(obj: UTSJSONObject, key: string) : string[] {
	const value = obj[key]
	if (value == null) return []
	return value as string[]
}

function cloneStringArray(list: string[]) : string[] {
	const result: string[] = []
	for (let index = 0; index < list.length; index += 1) {
		result.push(list[index])
	}
	return result
}

function buildInitialDataFromSupplier(item: SupplierItem) : UTSJSONObject {
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
		code: item.code,
		name: item.name,
		contact: item.contact,
		phone: item.phone,
		address: item.address,
		description: item.description == null ? '' : item.description,
		is_active: item.is_active ? 'true' : 'false',
		images: images,
		imageItems: imageItems,
	} as UTSJSONObject
}

function buildUploadHeaders() : UTSJSONObject {
	const headers = { __$originalPosition: new UTSSourceMapPosition("headers", "pages/suppliers/from.uvue", 135, 8), } as UTSJSONObject
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
			const parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/suppliers/from.uvue:151")
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

async function fetchStatusOptions(params: UTSJSONObject) : Promise<UTSJSONObject> {
	return buildSelectResponse(statusOptions.value, params)
}

const formSections = ref<UTSJSONObject[]>([
	{
		key: 'base',
		title: '基础信息',
		description: '。',
		defaultOpen: true,
		fields: [
			{
				key: 'name',
				label: '供应商名称',
				type: 'input',
				required: true,
				placeholder: '请输入供应商名称',
			} as UTSJSONObject,
			{
				key: 'code',
				label: '供应商编码',
				type: 'input',
				placeholder: '请输入编码',
			} as UTSJSONObject,
			{
				key: 'contact',
				label: '联系人',
				type: 'input',
				placeholder: '请输入联系人',
			} as UTSJSONObject,
			{
				key: 'phone',
				label: '联系电话',
				type: 'input',
				placeholder: '请输入联系电话',
			} as UTSJSONObject,
			{
				key: 'address',
				label: '地址',
				type: 'textarea',
				placeholder: '请输入地址',
			} as UTSJSONObject,
			{
				key: 'description',
				label: '备注',
				type: 'textarea',
				placeholder: '请输入备注',
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
				label: '供应商图片',
				type: 'upload',
				action: '',
				name: 'files',
				max: 6,
				uploadText: '上传图片',
				fileItemsKey: 'imageItems',
				headers: buildUploadHeaders(),
				formData: {} as UTSJSONObject,
			} as UTSJSONObject,
		] as UTSJSONObject[],
	} as UTSJSONObject,
])

const pageTitle = computed(() : string => {
	return formMode.value == 'edit' ? '编辑供应商' : '新建供应商'
})

function clearDraftStorage() {
}

async function loadSupplierDetailData(idText: string) {
	if (idText == '') {
		return
	}
	try {
		const detail = await getSupplierDetail(idText)
		console.log(detail, " at pages/suppliers/from.uvue:292")
		initialData.value = buildInitialDataFromSupplier(detail)
	} catch (error) {
		uni.showToast({
			title: parseErrorMessage(error, '供应商详情加载失败'),
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
				uni.reLaunch({
					url: '/pages/suppliers/index',
				})
			},
		})
	}, 16)
}

function markSupplierListRefreshNeeded() {
	uni.setStorageSync(supplierListRefreshStorageKey, '1')
}

function stringifyPayload(payload: UTSJSONObject) : string {
	const text = JSON.stringify(payload)
	if (text == null) return ''
	return text
}

function buildSupplierMutationPayload(formDataObject: UTSJSONObject) : SupplierMutationData {
	const name = getStringField(formDataObject, 'name')
	const codeValue = getStringField(formDataObject, 'code')
	const contactValue = getStringField(formDataObject, 'contact')
	const phoneValue = getStringField(formDataObject, 'phone')
	const addressValue = getStringField(formDataObject, 'address')
	const descriptionValue = getStringField(formDataObject, 'description')
	const activeText = getStringField(formDataObject, 'is_active', 'true')
	return {
		code: codeValue == '' ? null : codeValue,
		name: name,
		address: addressValue == '' ? null : addressValue,
		phone: phoneValue == '' ? null : phoneValue,
		contact: contactValue == '' ? null : contactValue,
		description: descriptionValue == '' ? null : descriptionValue,
		is_active: activeText == 'true',
		company_infos: null,
	} as SupplierMutationData
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

async function uploadPendingSupplierImages(formDataObject: UTSJSONObject, contentTypeModel: string) {
	if (supplierId.value == '') {
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
				object_id: supplierId.value,
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

async function persistForm(payload: UTSJSONObject, fromPrompt: boolean) {
	if (submitting.value) {
		return
	}
	const formDataValue = payload['formData']
	const data = formDataValue == null ? ({} as UTSJSONObject) : (formDataValue as UTSJSONObject)
	const uploadContentTypeModel = getStringField(payload, 'uploadContentTypeModel').trim()
	const actionText = formMode.value == 'edit' ? '保存修改' : '创建供应商'
	submitting.value = true
	savingText.value = actionText + '中...'
	savingVisible.value = true
	uni.showLoading({
		title: savingText.value,
		mask: true,
	})
	try {
		const body = buildSupplierMutationPayload(data)
		if (formMode.value == 'edit' && supplierId.value != '') {
			savingText.value = '上传图片中...'
			await uploadPendingSupplierImages(data, uploadContentTypeModel)
			savingText.value = '保存修改中...'
			await updateSupplier(supplierId.value, body)
		} else {
			savingText.value = '创建供应商中...'
			const createdSupplier = await createSupplier(body)
			supplierId.value = createdSupplier.id.toString()
			try {
				savingText.value = '上传图片中...'
				await uploadPendingSupplierImages(data, uploadContentTypeModel)
			} catch (uploadError) {
				throw new Error('供应商已创建，但图片上传失败')
			}
		}
		clearDraftStorage()
		markSupplierListRefreshNeeded()
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
	await persistForm(payload, false)
}

async function handleSaveRequest(payload: UTSJSONObject) {
	await persistForm(payload, true)
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
		title: '状态字段不支持新增',
		icon: 'none',
	})
}

function handleBottomSelectEdit(payload: UTSJSONObject) {
	uni.showToast({
		title: '状态字段不支持编辑',
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
	supplierId.value = idValue == null ? '' : (idValue as string)
	formMode.value = supplierId.value == '' ? 'create' : 'edit'
	if (formMode.value == 'edit') {
		loadSupplierDetailData(supplierId.value)
		return
	}
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
        uploadContentTypeModel: "supplier",
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
const GenPagesSuppliersFromStyles = [_uM([["page", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["position", "relative"], ["backgroundColor", "#EEF2F7"]]))], ["page-header", _pS(_uM([["paddingTop", 8], ["paddingRight", 10], ["paddingBottom", 6], ["paddingLeft", 10], ["backgroundColor", "#EEF2F7"]]))], ["page-mode", _pS(_uM([["alignSelf", "flex-start"], ["paddingTop", 4], ["paddingRight", 10], ["paddingBottom", 4], ["paddingLeft", 10], ["borderTopLeftRadius", 999], ["borderTopRightRadius", 999], ["borderBottomRightRadius", 999], ["borderBottomLeftRadius", 999], ["fontSize", 12], ["lineHeight", "16px"], ["color", "#155E75"], ["backgroundColor", "#CFFAFE"]]))], ["page-desc", _pS(_uM([["marginTop", 8], ["fontSize", 13], ["lineHeight", "18px"], ["color", "#64748B"]]))], ["page-content", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["paddingLeft", 0], ["paddingRight", 0], ["paddingBottom", 0]]))], ["page-saving-mask", _pS(_uM([["position", "absolute"], ["left", 0], ["top", 0], ["right", 0], ["bottom", 0], ["zIndex", 9999], ["alignItems", "center"], ["justifyContent", "center"], ["backgroundColor", "rgba(15,23,42,0.28)"]]))], ["page-saving-card", _pS(_uM([["height", 44], ["paddingLeft", 16], ["paddingRight", 16], ["borderTopLeftRadius", 22], ["borderTopRightRadius", 22], ["borderBottomRightRadius", 22], ["borderBottomLeftRadius", 22], ["alignItems", "center"], ["justifyContent", "center"], ["backgroundColor", "rgba(15,23,42,0.86)"]]))], ["page-saving-text", _pS(_uM([["fontSize", 13], ["lineHeight", "16px"], ["color", "#FFFFFF"]]))]])]
