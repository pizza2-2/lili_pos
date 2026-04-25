import _easycom_lili_universal_filter from '@/uni_modules/lili-universal-filter/components/lili-universal-filter/lili-universal-filter.uvue'
import _easycom_lili_UniversaForm from '@/uni_modules/lili-UniversaForm/components/lili-UniversaForm/lili-UniversaForm.uvue'
import { computed } from 'vue'
import { takeLatestResponseMessage } from '@/pkg/api/index.uts'
import { createShopMedia, getShopDetail, getShopMediaDetail, ShopMediaItem, ShopMediaMutationData, updateShopMedia } from '@/pkg/api/modules/shops'
import { batchUploadMediaFiles, MediaBatchUploadItem } from '@/pkg/api/modules/media.uts'
import { authState } from '@/store/auth'

type SelectOption = { __$originalPosition?: UTSSourceMapPosition<"SelectOption", "pages/shop/from.uvue", 49, 6>;
	value: string
	text: string
}


const __sfc__ = defineComponent({
  __name: 'from',
  setup(__props) {
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

const shopMediaListRefreshStorageKey = 'refresh:pages:shop:media'

const formMode = ref('create')
const mediaId = ref('')
const leaveSignal = ref(0)
const submitting = ref(false)
const savingVisible = ref(false)
const savingText = ref('处理中...')

const initialData = ref<UTSJSONObject>({
	shop_id: '',
	shop_name: '',
	title: '',
	record_type: 'general',
	expiration_date: '',
	notes: '',
	images: [] as string[],
	imageItems: [] as UTSJSONObject[],
} as UTSJSONObject)

const recordTypeOptions = ref<SelectOption[]>([
	{ value: 'general', text: '常规文件' } as SelectOption,
])

function getStringField(obj: UTSJSONObject, key: string, fallback: string = ''): string {
	const value = obj[key]
	if (value == null) {
		return fallback
	}
	const text = '' + value
	return text == '' ? fallback : text
}

function getArrayField(obj: UTSJSONObject, key: string): string[] {
	const value = obj[key]
	if (value == null) {
		return [] as string[]
	}
	return value as string[]
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
			const parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/shop/from.uvue:102")
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

function buildUploadHeaders(): UTSJSONObject {
	const headers = { __$originalPosition: new UTSSourceMapPosition("headers", "pages/shop/from.uvue", 118, 8), } as UTSJSONObject
	if (authState.token != '') {
		headers['Authorization'] = authState.token
	}
	return headers
}

function buildSelectResponse(source: SelectOption[], params: UTSJSONObject): UTSJSONObject {
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

async function fetchRecordTypeOptions(params: UTSJSONObject): Promise<UTSJSONObject> {
	return buildSelectResponse(recordTypeOptions.value, params)
}

function buildInitialDataFromMedia(item: ShopMediaItem): UTSJSONObject {
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
		shop_id: item.shop.toString(),
		shop_name: item.shop_name,
		title: item.title,
		record_type: item.record_type == '' ? 'general' : item.record_type,
		expiration_date: item.expiration_date,
		notes: item.notes,
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
			{
				key: 'shop_id',
				type: 'input',
				hidden: true,
			} as UTSJSONObject,
			{
				key: 'shop_name',
				label: '所属商店',
				type: 'input',
				readonly: true,
				placeholder: '进入页面时自动带入',
				required: true,
			} as UTSJSONObject,
			{
				key: 'title',
				label: '资料标题',
				type: 'input',
				required: true,
				placeholder: '请输入资料标题',
			} as UTSJSONObject,
			{
				key: 'record_type',
				label: '资料类型',
				type: 'bottomSelect',
				title: '选择资料类型',
				placeholder: '请选择资料类型',
				required: true,
				showAddAction: false,
				showEditAction: false,
				fetchData: fetchRecordTypeOptions,
			} as UTSJSONObject,
			{
				key: 'expiration_date',
				label: '到期时间',
				type: 'datetime',
				required: false,
				title: '选择到期时间',
				placeholder: '请选择到期时间',
				showTime: false,
			} as UTSJSONObject,
			{
				key: 'notes',
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
				label: '资料图片',
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

const pageTitle = computed((): string => {
	return formMode.value == 'edit' ? '编辑商店资料' : '新建商店资料'
})

function markShopMediaRefreshNeeded(): void {
	uni.setStorageSync(shopMediaListRefreshStorageKey, '1')
}

async function loadShopMediaDetailData(idText: string): Promise<void> {
	if (idText == '') {
		return
	}
	try {
		const detail = await getShopMediaDetail(idText)
		initialData.value = buildInitialDataFromMedia(detail)
	} catch (error) {
		uni.showToast({
			title: parseErrorMessage(error, '商店资料详情加载失败'),
			icon: 'none',
		})
	}
}

async function resolveShopName(shopIdText: string): Promise<string> {
	if (shopIdText == '') {
		return ''
	}
	try {
		const detail = await getShopDetail(shopIdText)
		return detail.name
	} catch (error) {
		return ''
	}
}

function buildShopMediaMutationPayload(formDataObject: UTSJSONObject): ShopMediaMutationData {
	const shopIdValue = getStringField(formDataObject, 'shop_id')
	const title = getStringField(formDataObject, 'title')
	const recordType = getStringField(formDataObject, 'record_type', 'general')
	const expirationDate = getStringField(formDataObject, 'expiration_date')
	const notes = getStringField(formDataObject, 'notes')
	return {
		shop: shopIdValue == '' ? null : shopIdValue,
		title: title,
		record_type: recordType == '' ? null : recordType,
		expiration_date: expirationDate == '' ? null : expirationDate,
		notes: notes == '' ? null : notes,
	} as ShopMediaMutationData
}

function goBackToList(): void {
	leaveSignal.value = leaveSignal.value + 1
	setTimeout(() => {
		uni.navigateBack({
			delta: 1,
			fail: () => {
				uni.reLaunch({
					url: '/pages/shop/index',
				})
			},
		})
	}, 16)
}

function isRemoteImagePath(path: string): boolean {
	if (path == '') {
		return false
	}
	return path.startsWith('http://') || path.startsWith('https://')
}

function collectPendingImagePaths(formDataObject: UTSJSONObject): string[] {
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

async function uploadPendingMediaImages(formDataObject: UTSJSONObject, contentTypeModel: string): Promise<void> {
	if (mediaId.value == '') {
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
				object_id: mediaId.value,
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

async function persistForm(payload: UTSJSONObject): Promise<void> {
	if (submitting.value) {
		return
	}
	const formDataValue = payload['formData']
	const data = formDataValue == null ? ({} as UTSJSONObject) : (formDataValue as UTSJSONObject)
	const shopIdValue = getStringField(data, 'shop_id')
	const title = getStringField(data, 'title').trim()
	if (shopIdValue == '') {
		uni.showToast({
			title: '请选择商店',
			icon: 'none',
		})
		return
	}
	if (title == '') {
		uni.showToast({
			title: '资料标题不能为空',
			icon: 'none',
		})
		return
	}

	submitting.value = true
	const isEditing = formMode.value == 'edit' && mediaId.value != ''
	const uploadContentTypeModel = getStringField(payload, 'uploadContentTypeModel').trim()
	savingText.value = isEditing ? '保存资料中...' : '创建资料中...'
	savingVisible.value = true
	uni.showLoading({
		title: savingText.value,
		mask: true,
	})

	try {
		const body = buildShopMediaMutationPayload(data)
		let successMessage = isEditing ? '资料保存成功' : '资料创建成功'
		if (isEditing) {
			const updated = await updateShopMedia(mediaId.value, body)
			successMessage = takeLatestResponseMessage(successMessage)
			mediaId.value = updated.id.toString()
			savingText.value = '上传图片中...'
			await uploadPendingMediaImages(data, uploadContentTypeModel)
		} else {
			const created = await createShopMedia(body)
			successMessage = takeLatestResponseMessage(successMessage)
			mediaId.value = created.id.toString()
			formMode.value = 'edit'
			try {
				savingText.value = '上传图片中...'
				await uploadPendingMediaImages(data, uploadContentTypeModel)
			} catch (uploadError) {
				throw new Error('资料已创建，但图片上传失败')
			}
		}
		markShopMediaRefreshNeeded()
		uni.showToast({
			title: successMessage,
			icon: 'success',
		})
		goBackToList()
	} catch (error) {
		uni.showToast({
			title: parseErrorMessage(error, isEditing ? '资料保存失败' : '资料创建失败'),
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

async function initializeForm(event: OnLoadOptions): Promise<void> {
	leaveSignal.value = 0
	const idValue = event['id']
	const shopIdValue = event['shop_id']
	const shopNameValue = event['shop_name']
	mediaId.value = idValue == null ? '' : (idValue as string)
	formMode.value = mediaId.value == '' ? 'create' : 'edit'
	if (formMode.value == 'edit') {
		await loadShopMediaDetailData(mediaId.value)
		return
	}
	const initialShopId = shopIdValue == null ? '' : (shopIdValue as string)
	let initialShopName = ''
	if (shopNameValue != null) {
		const decodedShopName = UTSAndroid.consoleDebugError(decodeURIComponent(shopNameValue as string), " at pages/shop/from.uvue:540")
		initialShopName = decodedShopName == null ? '' : ('' + decodedShopName)
	}
	if (initialShopName == '' && initialShopId != '') {
		initialShopName = await resolveShopName(initialShopId)
	}
	if (initialShopId == '') {
		uni.showToast({
			title: '请先从商店资料页进入',
			icon: 'none',
		})
		setTimeout(() => {
			uni.navigateBack({
				delta: 1,
				fail: () => {
					uni.reLaunch({
						url: '/pages/shop/index',
					})
				},
			})
		}, 300)
		return
	}
	initialData.value = {
		shop_id: initialShopId,
		shop_name: initialShopName,
		title: '',
		record_type: 'general',
		expiration_date: '',
		notes: '',
		images: [] as string[],
		imageItems: [] as UTSJSONObject[],
	} as UTSJSONObject
}

onLoad((event: OnLoadOptions) => {
	initializeForm(event)
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
      homePath: "/pages/shop/index",
      backgroundColor: "#EEF2F7"
    }), null, 8 /* PROPS */, ["title"]),
    _cE("view", _uM({ class: "page-content" }), [
      _cV(_component_lili_UniversaForm, _uM({
        mode: unref(formMode),
        formSections: unref(formSections),
        initialData: unref(initialData),
        leaveSignal: unref(leaveSignal),
        uploadContentTypeModel: "shopmedia",
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
const GenPagesShopFromStyles = [_uM([["page", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["position", "relative"], ["backgroundColor", "#EEF2F7"]]))], ["page-content", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["paddingLeft", 0], ["paddingRight", 0], ["paddingBottom", 0]]))], ["page-saving-mask", _pS(_uM([["position", "absolute"], ["left", 0], ["top", 0], ["right", 0], ["bottom", 0], ["zIndex", 9999], ["alignItems", "center"], ["justifyContent", "center"], ["backgroundColor", "rgba(15,23,42,0.28)"]]))], ["page-saving-card", _pS(_uM([["height", 44], ["paddingLeft", 16], ["paddingRight", 16], ["borderTopLeftRadius", 22], ["borderTopRightRadius", 22], ["borderBottomRightRadius", 22], ["borderBottomLeftRadius", 22], ["alignItems", "center"], ["justifyContent", "center"], ["backgroundColor", "rgba(15,23,42,0.86)"]]))], ["page-saving-text", _pS(_uM([["fontSize", 13], ["lineHeight", "16px"], ["color", "#FFFFFF"]]))]])]
