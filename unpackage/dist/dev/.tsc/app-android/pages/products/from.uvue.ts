import _easycom_lili_universal_filter from '@/uni_modules/lili-universal-filter/components/lili-universal-filter/lili-universal-filter.uvue'
import _easycom_lili_UniversaForm from '@/uni_modules/lili-UniversaForm/components/lili-UniversaForm/lili-UniversaForm.uvue'
import _easycom_lili_print_confirm_popup from '@/uni_modules/lili-print-confirm-popup/components/lili-print-confirm-popup/lili-print-confirm-popup.uvue'
import { computed } from 'vue'
import { request, takeLatestResponseMessage } from '@/pkg/api/index.uts'
import { ProductItem, ProductMutationData, createProduct, getProductConfigList, getProductDetail, productDiscountsPath, removeProductDiscountFromProduct, updateProduct } from '@/pkg/api/modules/products.uts'
import { batchUploadMediaFiles, MediaBatchUploadItem } from '@/pkg/api/modules/media.uts'
import { authState } from '@/store/auth'
import { createAsyncGuard } from '@/uni_modules/lili-async-guard'
import { showErrorToast } from '@/pkg/util/toast.uts'

type SelectOption = { __$originalPosition?: UTSSourceMapPosition<"SelectOption", "pages/products/from.uvue", 135, 6>;
	value: string
	text: string
}


const __sfc__ = defineComponent({
  __name: 'from',
  setup(__props) {
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

const productListRefreshStorageKey = 'refresh:pages:products:index'
const productDiscountSelectionStorageKey = 'selected_discount_for_product:'

const formMode = ref('create')
const productId = ref('')
const copySourceId = ref('')
const productFormRef = ref<ComponentPublicInstance|null>(null)
const leaveSignal = ref(0)
const dirtySignal = ref(0)
const submitting = ref(false)
const savingVisible = ref(false)
const savingText = ref('处理中...')
const printPopupVisible = ref(false)
const pageTaskGuard = createAsyncGuard()
const discountCards = ref<UTSJSONObject[]>([])
const discountCardsLoading = ref(false)
const categoryTaxRateCache = ref<UTSJSONObject>({} as UTSJSONObject)

const initialData = ref<UTSJSONObject>({
	sku: '',
	barcode: '',
	name_cn: '',
	name_en: '',
	name_other: '',
	description: '',
	category_id: '',
	category_text: '',
	category_kasa_kod: '',
	supplier_id: '',
	supplier_name: '',
	purchase_price: '0.00',
	net_purchase_price: '0.00',
	cost_price: '0.00',
	base_sales_price: '0.00',
	discount_rule: '',
	discount_rule_id: '',
	discounted_base_sales_price: '0.00',
	status: 'ACTIVE',
	is_featured: false,
	is_new: false,
	is_bestseller: false,
	sort_order: '0',
	images: [] as string[],
	imageItems: [] as UTSJSONObject[],
} as UTSJSONObject)

const statusOptions = ref<SelectOption[]>([
	{ value: 'ACTIVE', text: '启用' } as SelectOption,
	{ value: 'INACTIVE', text: '停用' } as SelectOption,
	{ value: 'DRAFT', text: '草稿' } as SelectOption,
])

function stringValue(value: any | null, fallback: string = ''): string {
	if (value == null) {
		return fallback
	}
	const text = '' + value
	return text == '' ? fallback : text
}

function intValue(value: any | null, fallback: number = 0): number {
	const text = stringValue(value)
	if (text == '') {
		return fallback
	}
	const parsed = parseInt(text)
	if (isNaN(parsed)) {
		return fallback
	}
	return parsed
}

function openProductPrintPage() {
	if (productId.value == '') {
		return
	}
	printPopupVisible.value = true
}

function handlePrintPopupVisibleChange(value: boolean) {
	printPopupVisible.value = value
}

function productPrintField(key: string, fallback: string = ''): string {
	return stringValue(initialData.value[key], fallback)
}

function productPrintNameText(): string {
	const nameCn = productPrintField('name_cn')
	if (nameCn != '') return nameCn
	const nameEn = productPrintField('name_en')
	if (nameEn != '') return nameEn
	const nameOther = productPrintField('name_other')
	if (nameOther != '') return nameOther
	return '未命名商品'
}

function productPrintPriceText(): string {
	const discountPrice = productPrintField('discounted_base_sales_price')
	if (discountPrice != '' && discountPrice != '0.00') return discountPrice
	return productPrintField('base_sales_price', '0.00')
}

function productPrintKodText(fallback: string): string {
	return productPrintField('category_kasa_kod', fallback)
}

const productPrintData = computed((): UTSJSONObject => {
	const data = { __$originalPosition: new UTSSourceMapPosition("data", "pages/products/from.uvue", 245, 8), } as UTSJSONObject
	data['name'] = productPrintNameText()
	data['name_cn'] = productPrintField('name_cn')
	data['name_en'] = productPrintField('name_en')
	data['name_other'] = productPrintField('name_other')
	data['price'] = productPrintPriceText()
	data['base_sales_price'] = productPrintField('base_sales_price', '0.00')
	data['discount_price'] = productPrintPriceText()
	data['barcode'] = productPrintField('barcode')
	data['sku'] = productPrintField('sku')
	data['kod'] = productPrintKodText('')
	data['category_kasa_kod'] = productPrintKodText('')
	return data
})

function resolveProductPrintValue(source: string, fallback: string): string {
	if (source == 'name') return productPrintNameText()
	if (source == 'name_cn') return productPrintField('name_cn')
	if (source == 'name_en') return productPrintField('name_en')
	if (source == 'name_other') return productPrintField('name_other')
	if (source == 'price') return productPrintPriceText()
	if (source == 'base_sales_price') return productPrintField('base_sales_price', '0.00')
	if (source == 'discount_price') return productPrintPriceText()
	if (source == 'barcode') {
		const barcode = productPrintField('barcode')
		if (barcode != '') return barcode
		return productPrintField('sku', fallback)
	}
	if (source == 'sku') return productPrintField('sku', fallback)
	if (source == 'kod') return productPrintKodText(fallback)
	return fallback
}

function floatValue(value: any | null, fallback: number = 0): number {
	const text = stringValue(value)
	if (text == '') {
		return fallback
	}
	const parsed = parseFloat(text)
	if (isNaN(parsed)) {
		return fallback
	}
	return parsed
}

function booleanValue(value: any | null): boolean {
	if (value == null) {
		return false
	}
	const text = stringValue(value).toLowerCase()
	return text == 'true' || text == '1' || text == 'yes'
}

function getStringField(obj: UTSJSONObject, key: string, fallback: string = ''): string {
	return stringValue(obj[key], fallback)
}

function formatMoneyText(value: string, fallback: string = '0.00'): string {
	const numberValue = floatValue(value, -1)
	if (numberValue < 0) {
		return fallback
	}
	return numberValue.toFixed(2)
}

function normalizeTaxRate(value: any | null): number {
	let taxRate = floatValue(value, -1)
	if (taxRate < 0) return -1
	if (taxRate > 1) {
		taxRate = taxRate / 100
	}
	return taxRate
}

async function resolveCategoryTaxRate(categoryId: string): Promise<number> {
	if (categoryId == '') return -1
	const cachedValue = categoryTaxRateCache.value[categoryId]
	if (cachedValue != null) {
		return normalizeTaxRate(cachedValue)
	}
	try {
		const raw = await request('/api/categories/categories/' + categoryId + '/', 'GET', {} as UTSJSONObject, true)
		const rawObject = parseObject(raw)
		if (rawObject == null) return -1
		const taxRate = normalizeTaxRate(rawObject['tax_rate'])
		if (taxRate >= 0) {
			categoryTaxRateCache.value[categoryId] = taxRate
		}
		return taxRate
	} catch (error) {
		return -1
	}
}

function applyPurchasePriceSync(formDataObject: UTSJSONObject, taxRate: number, sourceKey: string): boolean {
	if (taxRate < 0) return false
	const multiplier = 1 + taxRate
	if (multiplier <= 0) return false

	const grossPrice = floatValue(formDataObject['purchase_price'], 0)
	const netPrice = floatValue(formDataObject['net_purchase_price'], 0)
	const canFillNet = sourceKey == 'category_id' || sourceKey == 'purchase_price'
	const canFillGross = sourceKey == 'category_id' || sourceKey == 'net_purchase_price'
	if (canFillNet && grossPrice > 0 && netPrice <= 0) {
		formDataObject['net_purchase_price'] = formatMoneyText((grossPrice / multiplier).toString())
		return true
	}
	if (canFillGross && netPrice > 0 && grossPrice <= 0) {
		formDataObject['purchase_price'] = formatMoneyText((netPrice * multiplier).toString())
		return true
	}
	return false
}

function calculateDiscountedPriceText(productSalesPriceText: string, discountRule: UTSJSONObject): string {
	const basePrice = floatValue(productSalesPriceText, 0)
	if (basePrice <= 0) {
		return '0.00'
	}
	const discountType = getStringField(discountRule, 'discount_type')
	let percentValue = floatValue(discountRule['discount_percentage'])
	if (percentValue > 0 && discountType == 'PERCENTAGE') {
		return formatMoneyText((basePrice * (1 - percentValue / 100)).toString())
	}

	let amountValue = floatValue(discountRule['discount_amount'])
	if (amountValue <= 0 && discountRule['discount_amount_fixed'] != null) {
		amountValue = floatValue(discountRule['discount_amount_fixed'])
	}
	if (amountValue > 0 && (discountType == 'FIXED_AMOUNT' || discountType == 'FIXED')) {
		const nextPrice = basePrice - amountValue
		if (nextPrice <= 0) return '0.00'
		return formatMoneyText(nextPrice.toString())
	}
	return basePrice.toFixed(2)
}

function getArrayField(obj: UTSJSONObject, key: string): string[] {
	const value = obj[key]
	if (value == null) {
		return [] as string[]
	}
	return value as string[]
}

function parseObject(value: any | null): UTSJSONObject | null {
	if (value == null) {
		return null
	}
	const text = JSON.stringify(value)
	if (text == null || text == '') {
		return null
	}
	return UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(text), " at pages/products/from.uvue:398")
}

function parseObjectArray(value: any | null): UTSJSONObject[] {
	if (value == null) {
		return [] as UTSJSONObject[]
	}
	const text = JSON.stringify(value)
	if (text == null || text == '') {
		return [] as UTSJSONObject[]
	}
	const parsed = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(text), " at pages/products/from.uvue:409")
	if (parsed == null) {
		return [] as UTSJSONObject[]
	}
	return parsed!
}

function parseErrorMessage(error: any, fallback: string): string {
	let message = fallback
	if (error != null) {
		const errorText = JSON.stringify(error)
		if (errorText != null && errorText != '') {
			const parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/products/from.uvue:421")
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
	const headers = { __$originalPosition: new UTSSourceMapPosition("headers", "pages/products/from.uvue", 437, 8), } as UTSJSONObject
	if (authState.token != '') {
		headers['Authorization'] = authState.token
	}
	return headers
}

function buildSelectResponse(source: SelectOption[], params: UTSJSONObject): UTSJSONObject {
	const id = getStringField(params, 'id')
	const result: UTSJSONObject[] = []
	for (let index = 0; index < source.length; index += 1) {
		const option = source[index]
		if (id != '' && option.value != id) {
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

function extractOptionObjects(value: any | null): UTSJSONObject[] {
	const rawObject = parseObject(value)
	if (rawObject != null) {
		let items = parseObjectArray(rawObject['items'])
		if (items.length > 0) {
			return items
		}
		items = parseObjectArray(rawObject['results'])
		if (items.length > 0) {
			return items
		}
		items = parseObjectArray(rawObject['data'])
		if (items.length > 0) {
			return items
		}
		items = parseObjectArray(rawObject['options'])
		if (items.length > 0) {
			return items
		}
		const groups = parseObjectArray(rawObject['groups'])
		for (let groupIndex = 0; groupIndex < groups.length; groupIndex += 1) {
			const groupItems = parseObjectArray(groups[groupIndex]['items'])
			for (let itemIndex = 0; itemIndex < groupItems.length; itemIndex += 1) {
				items.push(groupItems[itemIndex])
			}
		}
		if (items.length > 0) {
			return items
		}
		const optionsObject = parseObject(rawObject['options'])
		if (optionsObject != null) {
			const safeOptionsObject = optionsObject as UTSJSONObject
			for (const key in safeOptionsObject) {
				const optionItems = parseObjectArray(safeOptionsObject[key])
				for (let itemIndex = 0; itemIndex < optionItems.length; itemIndex += 1) {
					items.push(optionItems[itemIndex])
				}
			}
			if (items.length > 0) {
				return items
			}
		}
	}
	return parseObjectArray(value)
}

function buildOptionValue(item: UTSJSONObject): string {
	const directValue = stringValue(item['value'])
	if (directValue != '') return directValue
	const idValue = stringValue(item['id'])
	if (idValue != '') return idValue
	const codeValue = stringValue(item['code'])
	if (codeValue != '') return codeValue
	return stringValue(item['key'])
}

function buildOptionText(item: UTSJSONObject): string {
	const textValue = stringValue(item['text'])
	if (textValue != '') return textValue
	const labelValue = stringValue(item['label'])
	if (labelValue != '') return labelValue
	const fullNameValue = stringValue(item['full_name'])
	if (fullNameValue != '') return fullNameValue
	const nameValue = stringValue(item['name'])
	if (nameValue != '') return nameValue
	const nameCn = stringValue(item['name_cn'])
	if (nameCn != '') return nameCn
	const titleValue = stringValue(item['title'])
	if (titleValue != '') return titleValue
	return buildOptionValue(item)
}

function buildSelectOptions(value: any | null): SelectOption[] {
	const source = extractOptionObjects(value)
	const result: SelectOption[] = []
	for (let index = 0; index < source.length; index += 1) {
		const item = source[index]
		const optionValue = buildOptionValue(item)
		const optionText = buildOptionText(item)
		if (optionValue == '' && optionText == '') {
			continue
		}
		result.push({
			value: optionValue,
			text: optionText == '' ? optionValue : optionText,
		} as SelectOption)
	}
	return result
}

async function fetchStatusOptions(params: UTSJSONObject): Promise<UTSJSONObject> {
	return buildSelectResponse(statusOptions.value, params)
}

function buildSupplierOptionQuery(params: UTSJSONObject): UTSJSONObject {
	const keywordValue = stringValue(params['keyword'])
	const query: UTSJSONObject = { __$originalPosition: new UTSSourceMapPosition("query", "pages/products/from.uvue", 559, 8), 
		key: 'supplier',
		limit: 50,
	} as UTSJSONObject
	if (keywordValue != '') {
		query['search'] = keywordValue
		query['keyword'] = keywordValue
	}
	return query
}

async function fetchSupplierOptions(params: UTSJSONObject): Promise<UTSJSONObject> {
	const raw = await request(
		'/api/procurement/suppliers/options/',
		'GET',
		buildSupplierOptionQuery(params),
		true
	)
	return buildSelectResponse(buildSelectOptions(raw), { keyword: '', id: stringValue(params['id']) } as UTSJSONObject)
}

function convertCategoryTreeItems(items: UTSJSONObject[]): UTSJSONObject[] {
	const result: UTSJSONObject[] = []
	for (let i = 0; i < items.length; i++) {
		const item = items[i]
		const children = parseObjectArray(item['children'])
		const treeChildren = convertCategoryTreeItems(children)
		const label = buildOptionText(item)
		const option: UTSJSONObject = {__$originalPosition: new UTSSourceMapPosition("option", "pages/products/from.uvue", 587, 9),
			value: buildOptionValue(item),
			text: label,
			label: label,
			full_name: stringValue(item['full_name'], label),
			code: stringValue(item['code']),
			level: intValue(item['level'], -1),
			parent_value: stringValue(item['parent_value']),
			disabled: booleanValue(item['disabled']),
			has_children: booleanValue(item['has_children']) || treeChildren.length > 0,
			children: treeChildren,
		}
		result.push(option)
	}
	return result
}

function extractCategoryTreeSource(value: any | null): UTSJSONObject[] {
	const rawObject = parseObject(value)
	if (rawObject == null) {
		return [] as UTSJSONObject[]
	}

	const groups = parseObjectArray(rawObject['groups'])
	for (let index = 0; index < groups.length; index += 1) {
		const group = groups[index]
		if (stringValue(group['key']) == 'parent') {
			return parseObjectArray(group['items'])
		}
	}

	if (groups.length > 0) {
		return parseObjectArray(groups[0]['items'])
	}

	let items = parseObjectArray(rawObject['items'])
	if (items.length > 0) {
		return items
	}
	items = parseObjectArray(rawObject['results'])
	if (items.length > 0) {
		return items
	}
	return parseObjectArray(rawObject['data'])
}

async function fetchCategoryOptions(params: UTSJSONObject): Promise<UTSJSONObject> {
	const keywordValue = getStringField(params, 'keyword')
	const idValue = getStringField(params, 'id')
	const parentValue = getStringField(params, 'parent')
	const pageValue = getStringField(params, 'page', '1')
	const pageSizeValue = getStringField(params, 'pageSize', '20')
	const queryParams = { __$originalPosition: new UTSSourceMapPosition("queryParams", "pages/products/from.uvue", 639, 8), 
		key: 'parent',
		page: intValue(pageValue, 1),
		page_size: intValue(pageSizeValue, 20),
	} as UTSJSONObject
	if (keywordValue != '') {
		queryParams['search'] = keywordValue
	}
	if (idValue != '') {
		queryParams['id'] = idValue
	}
	if (parentValue != '') {
		queryParams['parent'] = parentValue
	}
	const raw = await request(
		'/api/categories/categories/options/',
		'GET',
		queryParams,
		true
	)
	const source = extractCategoryTreeSource(raw)
	const treeItems = convertCategoryTreeItems(source)
	return {
		data: treeItems,
		total: treeItems.length,
	} as UTSJSONObject
}

function findCategoryTextInTree(items: UTSJSONObject[], categoryId: string): string {
	for (let index = 0; index < items.length; index += 1) {
		const item = items[index]
		if (stringValue(item['value']) == categoryId) {
			return stringValue(item['text'], categoryId)
		}
		const children = parseObjectArray(item['children'])
		if (children.length > 0) {
			const found = findCategoryTextInTree(children, categoryId)
			if (found != '') {
				return found
			}
		}
	}
	return ''
}

async function resolveCategoryOptionText(categoryId: string): Promise<string> {
	if (categoryId == '') {
		return ''
	}
	try {
		const response = await fetchCategoryOptions({
			id: categoryId,
			keyword: '',
			page: '1',
			pageSize: '20',
		} as UTSJSONObject)
		const items = parseObjectArray(response['data'])
		const found = findCategoryTextInTree(items, categoryId)
		if (found != '') {
			return found
		}
	} catch (error) {
	}
	return ''
}

function extractCategoryIdFromProduct(item: ProductItem): string {
	const categoryObject = parseObject(item.category)
	if (categoryObject != null) {
		return buildOptionValue(categoryObject as UTSJSONObject)
	}
	if (item.category != null) {
		return stringValue(item.category)
	}
	return ''
}

function buildInitialDataFromProduct(item: ProductItem, categoryTextOverride: string = ''): UTSJSONObject {
	const images: string[] = []
	const imageItems: UTSJSONObject[] = []
	for (let index = 0; index < item.media_files.length; index += 1) {
		const mediaFile = item.media_files[index]
		let imageUrl = ''
		let previewUrl = ''
		if (mediaFile.signed_thumbnail_url != '') {
			imageUrl = mediaFile.signed_thumbnail_url
		} else if (mediaFile.thumbnail_url != '') {
			imageUrl = mediaFile.thumbnail_url
		} else if (mediaFile.signed_url != '') {
			imageUrl = mediaFile.signed_url
		} else if (mediaFile.file_url != '') {
			imageUrl = mediaFile.file_url
		}
		if (mediaFile.signed_url != '') {
			previewUrl = mediaFile.signed_url
		} else if (mediaFile.file_url != '') {
			previewUrl = mediaFile.file_url
		} else {
			previewUrl = imageUrl
		}
		if (imageUrl != '') {
			images.push(imageUrl)
			imageItems.push({
				id: mediaFile.id,
				path: imageUrl,
				url: imageUrl,
				previewUrl: previewUrl,
				signed_url: mediaFile.signed_url,
				file_url: mediaFile.file_url,
				isRemote: true,
			} as UTSJSONObject)
		}
	}

	let categoryId = ''
	let categoryText = ''
	const categoryObject = parseObject(item.category)
	if (categoryObject != null) {
		const safeCategoryObject = categoryObject as UTSJSONObject
		categoryId = buildOptionValue(safeCategoryObject)
		categoryText = buildOptionText(safeCategoryObject)
	} else if (item.category != null) {
		categoryId = stringValue(item.category)
		categoryText = categoryId
	}
	if (categoryTextOverride != '') {
		categoryText = categoryTextOverride
	}
	const supplierId = item.supplier == null ? '' : stringValue(item.supplier)
	const discountRuleText = item.discount_info == null || !item.discount_info.has_discount ? '' : item.discount_info.discount_name
	const discountedBaseSalesPrice = item.discount_info == null || !item.discount_info.has_discount ? '' : item.discount_info.final_price

	return {
		sku: item.sku,
		barcode: item.barcode,
		name_cn: item.name_cn,
		name_en: item.name_en,
		name_other: item.name_other,
		description: item.description,
		category_id: categoryId,
		category_text: categoryText,
		category_kasa_kod: item.category_kasa_kod,
		supplier_id: supplierId,
		supplier_name: item.supplier_name,
		purchase_price: item.purchase_price == '' ? '0.00' : item.purchase_price,
		net_purchase_price: item.net_purchase_price == '' ? '0.00' : item.net_purchase_price,
		cost_price: item.cost_price == '' ? '0.00' : item.cost_price,
		base_sales_price: item.base_sales_price == '' ? '0.00' : item.base_sales_price,
		discount_rule: discountRuleText,
		discount_rule_id: item.discount_info == null ? '' : stringValue(item.discount_info.discount_id),
		discounted_base_sales_price: discountedBaseSalesPrice,
		status: item.status == '' ? 'ACTIVE' : item.status,
		is_featured: item.is_featured,
		is_new: item.is_new,
		is_bestseller: item.is_bestseller,
		sort_order: item.sort_order.toString(),
		images: images,
		imageItems: imageItems,
	} as UTSJSONObject
}

function buildCopiedInitialDataFromProduct(item: ProductItem, categoryTextOverride: string = ''): UTSJSONObject {
	const data = buildInitialDataFromProduct(item, categoryTextOverride)
	data['sku'] = ''
	data['barcode'] = ''
	data['discount_rule'] = ''
	data['discount_rule_id'] = ''
	data['discounted_base_sales_price'] = '0.00'
	data['images'] = [] as string[]
	data['imageItems'] = [] as UTSJSONObject[]
	return data
}

const formSections = ref<UTSJSONObject[]>([
	{
		key: 'base',
		title: '基础信息',
		description: '',
		defaultOpen: false,
		fields: [
			{
				key: 'name_cn',
				label: '中文名称',
				type: 'input',
				required: true,
				placeholder: '请输入中文名称',
			} as UTSJSONObject,
			{
				key: 'name_en',
				label: '波兰名称',
				type: 'input',
				placeholder: '请输入波兰名称',
			} as UTSJSONObject,
			{
				key: 'name_other',
				label: '其他名称',
				type: 'input',
				placeholder: '请输入其他名称',
			} as UTSJSONObject,
			{
				key: 'sku',
				label: 'SKU',
				type: 'input',
				placeholder: '请输入SKU',
			} as UTSJSONObject,
			{
				key: 'barcode',
				label: '条码',
				type: 'input',
				showScan: true,
				placeholder: '请输入或扫描条码',
			} as UTSJSONObject,
			{
				key: 'supplier_id',
				label: '供应商',
				type: 'bottomSelect',
				textKey: 'supplier_name',
				title: '选择供应商',
				placeholder: '请选择供应商',
				searchPlaceholder: '请输入供应商名称',
				showAddAction: true,
				showEditAction: true,
				addPath: '/pages/suppliers/from',
				editPath: '/pages/suppliers/from',
				fetchData: fetchSupplierOptions,
			} as UTSJSONObject,
			{
				key: 'category_id',
				label: '商品分类',
				type: 'bottomSelect',
				textKey: 'category_text',
				title: '选择商品分类',
				placeholder: '请选择商品分类',
				searchPlaceholder: '请输入分类名称',
				showAddAction: true,
				showEditAction: true,
				addPath: '/pages/category/from',
				editPath: '/pages/category/from',
				tree: true,
				expandOnClickNode: true,
				selectableLevel: 2,
				selectableLevelMessage: '只能选择 level 2 分类',
				fetchData: fetchCategoryOptions,
			} as UTSJSONObject,
			{
				key: 'description',
				label: '描述',
				type: 'textarea',
				placeholder: '请输入商品描述',
			} as UTSJSONObject,
		] as UTSJSONObject[],
	} as UTSJSONObject,
	{
		key: 'price',
		title: '价格信息',
		description: '',
		defaultOpen: false,
		fields: [
			{
				key: 'purchase_price',
				label: '含税采购价',
				type: 'number',
				required: true,
				decimal: true,
				placeholder: '请输入含税采购价',
			} as UTSJSONObject,
			{
				key: 'net_purchase_price',
				label: '不含税采购价',
				type: 'number',
				required: true,
				decimal: true,
				placeholder: '请输入不含税采购价',
			} as UTSJSONObject,
			{
				key: 'cost_price',
				label: '成本价',
				type: 'number',
				required: true,
				decimal: true,
				placeholder: '请输入成本价',
			} as UTSJSONObject,
			{
				key: 'base_sales_price',
				label: '基础售价',
				type: 'number',
				required: true,
				decimal: true,
				placeholder: '请输入基础售价',
			} as UTSJSONObject,
			{
				key: 'discount_rule',
				label: '折扣规则',
				type: 'custom',
				readonly: true,
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
				key: 'status',
				label: '商品状态',
				type: 'bottomSelect',
				title: '选择商品状态',
				placeholder: '请选择商品状态',
				showAddAction: false,
				showEditAction: false,
				fetchData: fetchStatusOptions,
			} as UTSJSONObject,
			{
				key: 'is_featured',
				label: '精选商品',
				type: 'switch',
			} as UTSJSONObject,
			{
				key: 'is_new',
				label: '新品',
				type: 'switch',
			} as UTSJSONObject,
			{
				key: 'is_bestseller',
				label: '热销商品',
				type: 'switch',
			} as UTSJSONObject,
			{
				key: 'sort_order',
				label: '排序',
				type: 'number',
				placeholder: '数字越小越靠前',
			} as UTSJSONObject,
		] as UTSJSONObject[],
	} as UTSJSONObject,
	{
		key: 'media',
		title: '商品图片',
		description: '可同时上传多张图片',
		defaultOpen: true,
		fields: [
			{
				key: 'images',
				label: '商品图片',
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
	if (formMode.value == 'create' && copySourceId.value != '') return '复制商品'
	return formMode.value == 'edit' ? '编辑商品' : '新建商品'
})

function markProductListRefreshNeeded(): void {
	uni.setStorageSync(productListRefreshStorageKey, '1')
}

async function loadProductDiscountCards(): Promise<void> {
	if (productId.value == '') {
		discountCards.value = [] as UTSJSONObject[]
		return
	}
	if (discountCardsLoading.value) {
		return
	}
	discountCardsLoading.value = true
	try {
		const response = await getProductConfigList(
			productDiscountsPath(),
			null,
			1,
			100,
			{ product: productId.value } as UTSJSONObject,
		)
		const rawResults = response['results']
		if (rawResults == null) {
			discountCards.value = [] as UTSJSONObject[]
		} else {
			discountCards.value = rawResults as UTSJSONObject[]
		}
	} catch (error) {
		discountCards.value = [] as UTSJSONObject[]
		showErrorToast(parseErrorMessage(error, '折扣规则加载失败'))
	} finally {
		discountCardsLoading.value = false
	}
}

async function loadProductDetailData(idText: string): Promise<void> {
	if (idText == '') {
		return
	}
	try {
		const detail = await getProductDetail(idText)
		let categoryText = ''
		const categoryId = extractCategoryIdFromProduct(detail)
		if (categoryId != '') {
			categoryText = await resolveCategoryOptionText(categoryId)
		}
		initialData.value = buildInitialDataFromProduct(detail, categoryText)
		await loadProductDiscountCards()
	} catch (error) {
		showErrorToast(parseErrorMessage(error, '商品详情加载失败'))
	}
}

async function loadCopiedProductData(idText: string): Promise<void> {
	if (idText == '') {
		return
	}
	let loaded = false
	try {
		uni.showLoading({
			title: '复制中',
			mask: true,
		})
		const detail = await getProductDetail(idText)
		let categoryText = ''
		const categoryId = extractCategoryIdFromProduct(detail)
		if (categoryId != '') {
			categoryText = await resolveCategoryOptionText(categoryId)
		}
		initialData.value = buildCopiedInitialDataFromProduct(detail, categoryText)
		discountCards.value = [] as UTSJSONObject[]
		loaded = true
	} catch (error) {
		showErrorToast(parseErrorMessage(error, '复制商品加载失败'))
	} finally {
		uni.hideLoading()
	}
	if (loaded) {
		uni.showToast({
			title: '已填入商品信息',
			icon: 'success',
		})
	}
}

function discountCardKey(discount: UTSJSONObject, index: number): string {
	const id = getStringField(discount, 'id')
	if (id != '') return id
	return 'discount-' + index.toString()
}

function discountCardName(discount: UTSJSONObject): string {
	return getStringField(discount, 'name', getStringField(discount, 'discount_name', '未命名折扣'))
}

function discountRuleText(discount: UTSJSONObject): string {
	const display = getStringField(discount, 'discount_display')
	if (display != '') return display
	const discountType = getStringField(discount, 'discount_type')
	const percentValue = getStringField(discount, 'discount_percentage')
	if (discountType == 'PERCENTAGE' && percentValue != '') {
		return percentValue + '% 折扣'
	}
	const amount = getStringField(discount, 'discount_amount', getStringField(discount, 'discount_amount_fixed'))
	if (amount != '') {
		return '减 ' + amount
	}
	return '未设置规则'
}

function discountTypeText(discount: UTSJSONObject): string {
	const discountType = getStringField(discount, 'discount_type')
	if (discountType == 'PERCENTAGE') return '百分比'
	if (discountType == 'FIXED_AMOUNT' || discountType == 'FIXED') return '固定金额'
	return discountType == '' ? '-' : discountType
}

function discountFinalPriceText(discount: UTSJSONObject): string {
	const directValue = getStringField(discount, 'final_price')
	if (directValue != '') {
		return formatMoneyText(directValue, directValue)
	}
	return calculateDiscountedPriceText(getStringField(initialData.value, 'base_sales_price', '0.00'), discount)
}

function upsertDiscountCardFromSelection(selected: UTSJSONObject): void {
	const discountId = getStringField(selected, 'discount_id')
	if (discountId == '') {
		return
	}
	const nextCard = { __$originalPosition: new UTSSourceMapPosition("nextCard", "pages/products/from.uvue", 1133, 8), 
		id: discountId,
		name: getStringField(selected, 'discount_name', getStringField(selected, 'name', '未命名折扣')),
		discount_type: getStringField(selected, 'discount_type'),
		discount_percentage: getStringField(selected, 'discount_percentage'),
		discount_amount: getStringField(selected, 'discount_amount', getStringField(selected, 'discount_amount_fixed')),
		discount_amount_fixed: getStringField(selected, 'discount_amount_fixed'),
		min_quantity: getStringField(selected, 'min_quantity', '1'),
		priority: '-',
		final_price: getStringField(selected, 'final_price'),
	} as UTSJSONObject
	const nextCards: UTSJSONObject[] = []
	let replaced = false
	for (let index = 0; index < discountCards.value.length; index += 1) {
		const currentCard = discountCards.value[index]
		if (getStringField(currentCard, 'id') == discountId) {
			nextCards.push(nextCard)
			replaced = true
		} else {
			nextCards.push(currentCard)
		}
	}
	if (!replaced) {
		nextCards.unshift(nextCard)
	}
	discountCards.value = nextCards
}

async function removeDiscountFromProduct(discountId: string): Promise<void> {
	if (productId.value == '' || discountId == '') {
		return
	}
	try {
		uni.showLoading({
			title: '删除中',
			mask: true,
		})
		await removeProductDiscountFromProduct(productId.value, discountId)
		await loadProductDetailData(productId.value)
		uni.showToast({
			title: '折扣已删除',
			icon: 'success',
		})
	} catch (error) {
		showErrorToast(parseErrorMessage(error, '折扣删除失败'))
	} finally {
		uni.hideLoading()
	}
}

function confirmRemoveDiscount(discount: UTSJSONObject): void {
	const discountId = getStringField(discount, 'id')
	if (discountId == '') {
		return
	}
	uni.showModal({
		title: '删除折扣',
		content: '确定移除这个折扣规则吗？',
		success: (res) => {
			if (res.confirm) {
				removeDiscountFromProduct(discountId)
			}
		},
	})
}

function buildDiscountSelectionStorageKey(): string {
	return productDiscountSelectionStorageKey + productId.value
}

function readStorageText(key: string): string {
	const rawValue = uni.getStorageSync(key)
	if (rawValue == null) {
		return ''
	}
	const text = '' + rawValue
	return text == null ? '' : text
}

function parseStoredJson(value: any): UTSJSONObject | null {
	const rawText = '' + value
	if (rawText == '') {
		return null
	}
	const parsed = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(rawText), " at pages/products/from.uvue:1217")
	if (parsed != null) {
		return parsed
	}
	return null
}

function cloneInitialData(): UTSJSONObject {
	const source = initialData.value
	const result = { __$originalPosition: new UTSSourceMapPosition("result", "pages/products/from.uvue", 1226, 8), } as UTSJSONObject
	for (const key in source) {
		result[key] = source[key]
	}
	return result
}

function applySelectedDiscount() {
	if (productId.value == '') {
		return
	}
	const selectedStorageText = readStorageText(buildDiscountSelectionStorageKey())
	if (selectedStorageText == '') {
		return
	}
	const selected = parseStoredJson(selectedStorageText)
	if (selected == null) {
		return
	}
	const selectedProductId = getStringField(selected, 'product_id')
	if (selectedProductId != '' && selectedProductId != productId.value) {
		return
	}
	const nextData = cloneInitialData()
	const currentBaseSalesPrice = getStringField(nextData, 'base_sales_price', '0.00')
	const baseSalesPriceFromSelection = getStringField(selected, 'original_price', currentBaseSalesPrice)
	const discountPriceFromSelection = getStringField(selected, 'final_price')
	let finalPrice = ''
	if (discountPriceFromSelection == '') {
		finalPrice = calculateDiscountedPriceText(baseSalesPriceFromSelection, selected)
	} else {
		finalPrice = discountPriceFromSelection
	}
	nextData['discount_rule'] = getStringField(selected, 'discount_name', getStringField(selected, 'name'))
	nextData['discount_rule_id'] = getStringField(selected, 'discount_id')
	nextData['discounted_base_sales_price'] = formatMoneyText(finalPrice, '')
	nextData['base_sales_price'] = baseSalesPriceFromSelection
	initialData.value = nextData
	upsertDiscountCardFromSelection(selected)
	loadProductDiscountCards()
	uni.removeStorageSync(buildDiscountSelectionStorageKey())
	uni.showToast({
		title: '折扣已添加',
		icon: 'success',
	})
}

function openDiscountSelector() {
	const safeProductId = '' + productId.value
	if (safeProductId == '') {
		uni.showToast({
			title: '请先保存商品后再选择折扣',
			icon: 'none', duration: 3500,
		})
		return
	}
	let baseSalesPrice = getStringField(initialData.value, 'base_sales_price', '0.00')
	if (baseSalesPrice == '') {
		baseSalesPrice = '0.00'
	}
	uni.navigateTo({
		url: '/pages/products/config-model/index?resource=discount&mode=select&product_id=' + safeProductId + '&base_sales_price=' + UTSAndroid.consoleDebugError(encodeURIComponent(baseSalesPrice), " at pages/products/from.uvue:1287"),
	})
}

function handleInputAdd(payload: UTSJSONObject) {
	const keyValue = getStringField(payload, 'key')
	if (keyValue == 'discount_rule') {
		openDiscountSelector()
		return
	}
}

function goBackToList(markLeaving: boolean = true): void {
	if (markLeaving) {
		pageTaskGuard.leave()
		savingVisible.value = false
		uni.hideLoading()
	}
	leaveSignal.value = leaveSignal.value + 1
	setTimeout(() => {
		uni.navigateBack({
			delta: 1,
			fail: () => {
				uni.switchTab({
					url: '/pages/tabbar/products',
				})
			},
		})
	}, 16)
}

function buildProductMutationPayload(formDataObject: UTSJSONObject): ProductMutationData {
	const sku = getStringField(formDataObject, 'sku').trim()
	const barcode = getStringField(formDataObject, 'barcode').trim()
	const nameCn = getStringField(formDataObject, 'name_cn').trim()
	const nameEn = getStringField(formDataObject, 'name_en').trim()
	const nameOther = getStringField(formDataObject, 'name_other').trim()
	const description = getStringField(formDataObject, 'description').trim()
	const categoryId = getStringField(formDataObject, 'category_id').trim()
	const supplierId = getStringField(formDataObject, 'supplier_id').trim()
	const purchasePrice = getStringField(formDataObject, 'purchase_price', '0.00').trim()
	const netPurchasePrice = getStringField(formDataObject, 'net_purchase_price', '0.00').trim()
	const costPrice = getStringField(formDataObject, 'cost_price', '0.00').trim()
	const baseSalesPrice = getStringField(formDataObject, 'base_sales_price', '0.00').trim()
	const status = getStringField(formDataObject, 'status', 'ACTIVE').trim()
	return {
		sku: sku == '' ? null : sku,
		barcode: barcode == '' ? null : barcode,
		name_cn: nameCn,
		name_en: nameEn == '' ? null : nameEn,
		name_other: nameOther == '' ? null : nameOther,
		description: description == '' ? null : description,
		category: categoryId == '' ? null : categoryId,
		supplier: supplierId == '' ? null : supplierId,
		purchase_price: purchasePrice == '' ? '0.00' : purchasePrice,
		net_purchase_price: netPurchasePrice == '' ? '0.00' : netPurchasePrice,
		cost_price: costPrice == '' ? '0.00' : costPrice,
		base_sales_price: baseSalesPrice == '' ? '0.00' : baseSalesPrice,
		status: status == '' ? 'ACTIVE' : status,
		is_featured: booleanValue(formDataObject['is_featured']),
		is_new: booleanValue(formDataObject['is_new']),
		is_bestseller: booleanValue(formDataObject['is_bestseller']),
		sort_order: intValue(formDataObject['sort_order'], 0),
	} as ProductMutationData
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

async function uploadPendingProductImages(formDataObject: UTSJSONObject, contentTypeModel: string): Promise<void> {
	if (productId.value == '') {
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
				object_id: productId.value,
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
	const nameCn = getStringField(data, 'name_cn').trim()
	if (nameCn == '') {
		uni.showToast({
			title: '中文名称不能为空',
			icon: 'none', duration: 3500,
		})
		return
	}

	const taskToken = pageTaskGuard.begin()
	submitting.value = true
	const isEditing = formMode.value == 'edit' && productId.value != ''
	const uploadContentTypeModel = getStringField(payload, 'uploadContentTypeModel').trim()
	savingText.value = isEditing ? '保存商品中...' : '创建商品中...'
	savingVisible.value = true
	uni.showLoading({
		title: savingText.value,
		mask: true,
	})

	try {
		const body = buildProductMutationPayload(data)
		let successMessage = isEditing ? '商品保存成功' : '商品创建成功'
		if (isEditing) {
			const updated = await updateProduct(productId.value, body)
			successMessage = takeLatestResponseMessage(successMessage)
			productId.value = updated.id.toString()
			savingText.value = '上传图片中...'
			await uploadPendingProductImages(data, uploadContentTypeModel)
		} else {
			const created = await createProduct(body)
			successMessage = takeLatestResponseMessage(successMessage)
			productId.value = created.id.toString()
			formMode.value = 'edit'
			try {
				savingText.value = '上传图片中...'
				await uploadPendingProductImages(data, uploadContentTypeModel)
			} catch (uploadError) {
				throw new Error('商品已创建，但图片上传失败')
			}
		}
		markProductListRefreshNeeded()
		if (!pageTaskGuard.canApply(taskToken)) {
			return
		}
		uni.showToast({
			title: successMessage,
			icon: 'success',
		})
		goBackToList(false)
	} catch (error) {
		if (!pageTaskGuard.canApply(taskToken)) {
			return
		}
		showErrorToast(parseErrorMessage(error, isEditing ? '商品保存失败' : '商品创建失败'))
	} finally {
		if (pageTaskGuard.canApply(taskToken)) {
			savingVisible.value = false
			uni.hideLoading()
			submitting.value = false
		}
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

async function handleFieldChange(payload: UTSJSONObject): Promise<void> {
	const keyValue = getStringField(payload, 'key')
	if (keyValue != 'category_id' && keyValue != 'purchase_price' && keyValue != 'net_purchase_price') {
		return
	}
	const formDataValue = payload['formData']
	if (formDataValue == null) return
	const formDataObject = formDataValue as UTSJSONObject
	const categoryId = getStringField(formDataObject, 'category_id').trim()
	if (categoryId == '') return
	const taxRate = await resolveCategoryTaxRate(categoryId)
	if (getStringField(formDataObject, 'category_id').trim() != categoryId) return
	applyPurchasePriceSync(formDataObject, taxRate, keyValue)
}

function handleBottomSelectAdd(payload: UTSJSONObject) {
	uni.showToast({
		title: '当前字段不支持新增',
		icon: 'none', duration: 3500,
	})
}

function handleBottomSelectEdit(payload: UTSJSONObject) {
	uni.showToast({
		title: '当前字段不支持编辑',
		icon: 'none', duration: 3500,
	})
}

function handleUpload(payload: UTSJSONObject) {
	uni.showToast({
		title: '图片已加入待保存列表',
		icon: 'none', duration: 3500,
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
				icon: 'none', duration: 3500,
			})
			return
		}
	}
	uni.showToast({
		title: '图片上传失败',
		icon: 'none', duration: 3500,
	})
}

function openPriceCalculator() {
	if (productId.value == '') {
		uni.showToast({
			title: '请先保存商品后再计算',
			icon: 'none', duration: 3500,
		})
		return
	}
	uni.navigateTo({
		url: '/pages/products/price-calculator?id=' + productId.value,
	})
}

function applyCalculatedPrice() {
	if (productId.value == '') {
		return
	}
	const storageKey = 'calc_result:' + productId.value
	const calculatedPrice = readStorageText(storageKey)
	if (calculatedPrice == '') {
		return
	}
	uni.removeStorageSync(storageKey)
	const nextBaseSalesPrice = calculatedPrice.trim()
	productFormRef.value?.$callMethod('setFieldValue', 'base_sales_price', nextBaseSalesPrice == '' ? '0.00' : nextBaseSalesPrice)
	uni.showToast({
		title: '基础售价已填入计算结果',
		icon: 'success',
	})
}

onLoad((event: OnLoadOptions) => {
	pageTaskGuard.reset()
	leaveSignal.value = 0
	const idValue = event['id']
	const copyValue = event['copy_id']
	productId.value = idValue == null ? '' : (idValue as string)
	copySourceId.value = copyValue == null ? '' : (copyValue as string)
	formMode.value = productId.value == '' ? 'create' : 'edit'
	if (formMode.value == 'edit') {
		loadProductDetailData(productId.value)
	} else if (copySourceId.value != '') {
		loadCopiedProductData(copySourceId.value)
	}
})

onUnload(() => {
	pageTaskGuard.leave()
	uni.hideLoading()
})

onShow(() => {
	applyCalculatedPrice()
	applySelectedDiscount()
})

return (): any | null => {

const _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter",_easycom_lili_universal_filter)
const _component_lili_UniversaForm = resolveEasyComponent("lili-UniversaForm",_easycom_lili_UniversaForm)
const _component_lili_print_confirm_popup = resolveEasyComponent("lili-print-confirm-popup",_easycom_lili_print_confirm_popup)

  return _cE("view", _uM({ class: "page" }), [
    _cV(_component_lili_universal_filter, _uM({
      title: pageTitle.value,
      showBack: true,
      showSearch: false,
      showHome: true,
      showRightText: unref(formMode) == 'edit' && unref(productId) != '',
      rightText: "打印",
      homePath: "/pages/tabbar/products",
      backgroundColor: "#EEF2F7",
      onRight: openProductPrintPage
    }), null, 8 /* PROPS */, ["title", "showRightText"]),
    _cE("view", _uM({ class: "page-content" }), [
      _cV(_component_lili_UniversaForm, _uM({
        ref_key: "productFormRef",
        ref: productFormRef,
        mode: unref(formMode),
        formSections: unref(formSections),
        initialData: unref(initialData),
        leaveSignal: unref(leaveSignal),
        dirtySignal: unref(dirtySignal),
        uploadContentTypeModel: "product",
        onSubmit: handleSubmit,
        onCancel: handleCancel,
        onDiscardLeave: handleDiscardLeave,
        onSaveRequest: handleSaveRequest,
        onFieldChange: handleFieldChange,
        onDirtyChange: handleDirtyChange,
        onBottomSelectAdd: handleBottomSelectAdd,
        onBottomSelectEdit: handleBottomSelectEdit,
        onInputAdd: handleInputAdd,
        onUpload: handleUpload,
        onUploadDelete: handleUploadDelete,
        onUploadError: handleUploadError
      }), _uM({
        "field-discount_rule": withScopedSlotCtx((slotProps: Record<string, any | null>): any[] => {
        const value = slotProps["value"]
        return [
          _cE("view", _uM({ class: "discount-card-section" }), [
            isTrue(unref(discountCards).length == 0 && !unref(discountCardsLoading))
              ? _cE("view", _uM({
                  key: 0,
                  class: "discount-empty-card"
                }), [
                  _cE("text", _uM({ class: "discount-empty-title" }), "暂无折扣规则"),
                  _cE("text", _uM({ class: "discount-empty-desc" }), "可以为当前商品添加一个或多个折扣规则")
                ])
              : _cC("v-if", true),
            _cE(Fragment, null, RenderHelpers.renderList(unref(discountCards), (discount, discountIndex, __index, _cached): any => {
              return _cE("view", _uM({
                key: discountCardKey(discount, discountIndex),
                class: "discount-card"
              }), [
                _cE("view", _uM({ class: "discount-card-head" }), [
                  _cE("view", _uM({ class: "discount-card-title-wrap" }), [
                    _cE("text", _uM({ class: "discount-card-title" }), _tD(discountCardName(discount)), 1 /* TEXT */),
                    _cE("text", _uM({ class: "discount-card-rule" }), _tD(discountRuleText(discount)), 1 /* TEXT */)
                  ]),
                  _cE("view", _uM({
                    class: "discount-delete-btn",
                    onClick: withModifiers(() => {confirmRemoveDiscount(discount)}, ["stop"])
                  }), [
                    _cE("text", _uM({ class: "discount-delete-text" }), "删除")
                  ], 8 /* PROPS */, ["onClick"])
                ]),
                _cE("view", _uM({ class: "discount-card-grid" }), [
                  _cE("view", _uM({ class: "discount-card-cell" }), [
                    _cE("text", _uM({ class: "discount-card-label" }), "折后价"),
                    _cE("text", _uM({ class: "discount-card-value discount-card-price" }), _tD(discountFinalPriceText(discount)), 1 /* TEXT */)
                  ]),
                  _cE("view", _uM({ class: "discount-card-cell" }), [
                    _cE("text", _uM({ class: "discount-card-label" }), "类型"),
                    _cE("text", _uM({ class: "discount-card-value" }), _tD(discountTypeText(discount)), 1 /* TEXT */)
                  ]),
                  _cE("view", _uM({ class: "discount-card-cell" }), [
                    _cE("text", _uM({ class: "discount-card-label" }), "最低数量"),
                    _cE("text", _uM({ class: "discount-card-value" }), _tD(getStringField(discount, 'min_quantity', '1')), 1 /* TEXT */)
                  ]),
                  _cE("view", _uM({ class: "discount-card-cell" }), [
                    _cE("text", _uM({ class: "discount-card-label" }), "优先级"),
                    _cE("text", _uM({ class: "discount-card-value" }), _tD(getStringField(discount, 'priority', '-')), 1 /* TEXT */)
                  ])
                ])
              ])
            }), 128 /* KEYED_FRAGMENT */),
            isTrue(unref(discountCardsLoading))
              ? _cE("view", _uM({
                  key: 1,
                  class: "discount-loading-card"
                }), [
                  _cE("text", _uM({ class: "discount-loading-text" }), "正在加载折扣规则...")
                ])
              : _cC("v-if", true),
            isTrue(value != '' && value != '0.00')
              ? _cE("text", _uM({
                  key: 2,
                  class: "discount-active-price"
                }), " 当前折后售价：" + _tD(value), 1 /* TEXT */)
              : _cC("v-if", true),
            _cE("view", _uM({
              class: "discount-action-btn",
              onClick: openDiscountSelector
            }), [
              _cE("text", _uM({ class: "discount-action-btn-text" }), "添加折扣")
            ])
          ])
        ]}),
        _: 1 /* STABLE */
      }), 8 /* PROPS */, ["mode", "formSections", "initialData", "leaveSignal", "dirtySignal"])
    ]),
    isTrue(unref(formMode) == 'edit' && unref(productId) != '')
      ? _cE("view", _uM({
          key: 0,
          class: "float-btn",
          onClick: openPriceCalculator
        }), [
          _cE("text", _uM({ class: "float-btn-text" }), "计算价格")
        ])
      : _cC("v-if", true),
    isTrue(unref(savingVisible))
      ? _cE("view", _uM({
          key: 1,
          class: "page-saving-mask"
        }), [
          _cE("view", _uM({ class: "page-saving-card" }), [
            _cE("text", _uM({ class: "page-saving-text" }), _tD(unref(savingText)), 1 /* TEXT */)
          ])
        ])
      : _cC("v-if", true),
    _cV(_component_lili_print_confirm_popup, _uM({
      visible: unref(printPopupVisible),
      templateType: "product_label",
      printData: productPrintData.value,
      "onUpdate:visible": handlePrintPopupVisibleChange
    }), null, 8 /* PROPS */, ["visible", "printData"])
  ])
}
}

})
export default __sfc__
const GenPagesProductsFromStyles = [_uM([["page", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["position", "relative"], ["backgroundColor", "#EEF2F7"]]))], ["page-content", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["paddingLeft", 0], ["paddingRight", 0], ["paddingBottom", 0]]))], ["page-saving-mask", _pS(_uM([["position", "absolute"], ["left", 0], ["top", 0], ["right", 0], ["bottom", 0], ["zIndex", 9999], ["alignItems", "center"], ["justifyContent", "center"], ["backgroundColor", "rgba(15,23,42,0.28)"]]))], ["page-saving-card", _pS(_uM([["height", 44], ["paddingLeft", 16], ["paddingRight", 16], ["borderTopLeftRadius", 22], ["borderTopRightRadius", 22], ["borderBottomRightRadius", 22], ["borderBottomLeftRadius", 22], ["alignItems", "center"], ["justifyContent", "center"], ["backgroundColor", "rgba(15,23,42,0.86)"]]))], ["page-saving-text", _pS(_uM([["fontSize", 13], ["lineHeight", "16px"], ["color", "#FFFFFF"]]))], ["float-btn", _pS(_uM([["position", "absolute"], ["right", 14], ["bottom", 112], ["height", 32], ["paddingLeft", 12], ["paddingRight", 12], ["borderTopLeftRadius", 16], ["borderTopRightRadius", 16], ["borderBottomRightRadius", 16], ["borderBottomLeftRadius", 16], ["alignItems", "center"], ["justifyContent", "center"], ["flexDirection", "row"], ["backgroundColor", "#111827"], ["zIndex", 9998]]))], ["float-btn-text", _pS(_uM([["fontSize", 12], ["lineHeight", "14px"], ["color", "#FFFFFF"], ["textAlign", "center"]]))], ["discount-card-section", _pS(_uM([["paddingTop", 8], ["paddingBottom", 8]]))], ["discount-empty-card", _pS(_uM([["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E2E8F0"], ["borderRightColor", "#E2E8F0"], ["borderBottomColor", "#E2E8F0"], ["borderLeftColor", "#E2E8F0"], ["backgroundColor", "#F8FAFC"], ["paddingTop", 12], ["paddingRight", 12], ["paddingBottom", 12], ["paddingLeft", 12], ["marginBottom", 10]]))], ["discount-loading-card", _pS(_uM([["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E2E8F0"], ["borderRightColor", "#E2E8F0"], ["borderBottomColor", "#E2E8F0"], ["borderLeftColor", "#E2E8F0"], ["backgroundColor", "#F8FAFC"], ["paddingTop", 12], ["paddingRight", 12], ["paddingBottom", 12], ["paddingLeft", 12], ["marginBottom", 10]]))], ["discount-empty-title", _pS(_uM([["fontSize", 14], ["lineHeight", "18px"], ["color", "#0F172A"], ["fontWeight", "bold"]]))], ["discount-empty-desc", _pS(_uM([["marginTop", 4], ["fontSize", 12], ["lineHeight", "16px"], ["color", "#64748B"]]))], ["discount-loading-text", _pS(_uM([["marginTop", 4], ["fontSize", 12], ["lineHeight", "16px"], ["color", "#64748B"]]))], ["discount-card", _pS(_uM([["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#CBD5E1"], ["borderRightColor", "#CBD5E1"], ["borderBottomColor", "#CBD5E1"], ["borderLeftColor", "#CBD5E1"], ["backgroundColor", "#FFFFFF"], ["paddingTop", 12], ["paddingRight", 12], ["paddingBottom", 12], ["paddingLeft", 12], ["marginBottom", 10]]))], ["discount-card-head", _pS(_uM([["flexDirection", "row"], ["alignItems", "flex-start"], ["justifyContent", "space-between"]]))], ["discount-card-title-wrap", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["paddingRight", 10]]))], ["discount-card-title", _pS(_uM([["fontSize", 15], ["lineHeight", "20px"], ["color", "#0F172A"], ["fontWeight", "bold"]]))], ["discount-card-rule", _pS(_uM([["marginTop", 3], ["fontSize", 12], ["lineHeight", "16px"], ["color", "#475569"]]))], ["discount-delete-btn", _pS(_uM([["height", 28], ["borderTopLeftRadius", 6], ["borderTopRightRadius", 6], ["borderBottomRightRadius", 6], ["borderBottomLeftRadius", 6], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#FCA5A5"], ["borderRightColor", "#FCA5A5"], ["borderBottomColor", "#FCA5A5"], ["borderLeftColor", "#FCA5A5"], ["backgroundColor", "#FEF2F2"], ["paddingLeft", 10], ["paddingRight", 10], ["alignItems", "center"], ["justifyContent", "center"]]))], ["discount-delete-text", _pS(_uM([["fontSize", 12], ["lineHeight", "14px"], ["color", "#B91C1C"]]))], ["discount-card-grid", _pS(_uM([["flexDirection", "row"], ["flexWrap", "wrap"], ["marginTop", 10]]))], ["discount-card-cell", _pS(_uM([["width", "50%"], ["paddingTop", 6], ["paddingBottom", 6]]))], ["discount-card-label", _pS(_uM([["fontSize", 11], ["lineHeight", "14px"], ["color", "#94A3B8"]]))], ["discount-card-value", _pS(_uM([["marginTop", 2], ["fontSize", 13], ["lineHeight", "17px"], ["color", "#334155"]]))], ["discount-card-price", _pS(_uM([["color", "#0F766E"], ["fontSize", 15], ["fontWeight", "bold"]]))], ["discount-active-price", _pS(_uM([["marginBottom", 10], ["fontSize", 13], ["lineHeight", "18px"], ["color", "#0F766E"]]))], ["discount-action-btn", _pS(_uM([["alignItems", "center"], ["justifyContent", "center"], ["height", 38], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#0F172A"], ["paddingLeft", 12], ["paddingRight", 12]]))], ["discount-action-btn-text", _pS(_uM([["fontSize", 14], ["lineHeight", "16px"], ["color", "#FFFFFF"]]))]])]
