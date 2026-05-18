import _easycom_lili_universal_filter from '@/uni_modules/lili-universal-filter/components/lili-universal-filter/lili-universal-filter.uvue'
import { computed } from 'vue'
import liliBottomSelect from '@/uni_modules/lili_bottom-select/components/lili_bottom-select/lili_bottom-select.uvue'
import { takeLatestResponseMessage } from '@/pkg/api/index.uts'
import { adjustInventoryStock, createInventoryStockForProduct, getInventoryLocations, getInventoryStockDetail, getInventoryStocks, getInventoryTransactions, InventoryListQuery, InventoryStockCreateForProductData, StockAdjustmentData } from '@/pkg/api/modules/inventory'

type SelectOption = { __$originalPosition?: UTSSourceMapPosition<"SelectOption", "pages/inventory-management/from.uvue", 324, 6>;
	value: string
	label: string
}


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

function floatValue(value: any | null): number {
	const parsed = parseFloat(stringValue(value))
	if (isNaN(parsed)) return 0
	return parsed
}

function parseObjectArray(value: any | null): UTSJSONObject[] {
	if (value == null) return [] as UTSJSONObject[]
	const text = JSON.stringify(value)
	if (text == null || text == '') return [] as UTSJSONObject[]
	let parsed: UTSJSONObject[] | null = null
	try {
		parsed = UTSAndroid.consoleDebugError(JSON.parseArray<UTSJSONObject>(text), " at pages/inventory-management/from.uvue:354")
	} catch (error) {
		return [] as UTSJSONObject[]
	}
	if (parsed == null) return [] as UTSJSONObject[]
	return parsed!
}

function parseErrorMessage(error: any, fallback: string): string {
	if (error == null) return fallback
	const directMessage = (error as Error).message
	if (directMessage != null && directMessage != '') return directMessage
	const text = JSON.stringify(error)
	if (text == null || text == '' || text == '{}') return fallback
	return text
}

function readInputValue(event: any): string {
	const inputEvent = event as UniInputEvent
	return inputEvent.detail.value
}

function decimalText(value: string): string {
	let text = value.trim()
	text = text.split(',').join('.')
	let result = ''
	let dotCount = 0
	for (let index = 0; index < text.length; index += 1) {
		const ch = text.charAt(index)
		if (ch == '.') {
			dotCount = dotCount + 1
			if (dotCount <= 1) result = result + ch
			continue
		}
		if (ch >= '0' && ch <= '9') result = result + ch
	}
	return result
}

function signedIntegerText(value: string): string {
	const text = value.trim()
	let result = ''
	for (let index = 0; index < text.length; index += 1) {
		const ch = text.charAt(index)
		if (ch == '-' && result == '') {
			result = '-'
			continue
		}
		if (ch >= '0' && ch <= '9') result = result + ch
	}
	return result
}

function positiveIntegerText(value: string): string {
	const text = value.trim()
	let result = ''
	for (let index = 0; index < text.length; index += 1) {
		const ch = text.charAt(index)
		if (ch >= '0' && ch <= '9') result = result + ch
	}
	return result
}

function firstStringField(obj: UTSJSONObject, keys: string[]): string {
	for (let index = 0; index < keys.length; index += 1) {
		const text = stringValue(obj[keys[index]])
		if (text != '') return text
	}
	return ''
}

function firstImageFromStock(detail: UTSJSONObject): string {
	const direct = stringValue(detail['product_image'])
	if (direct != '') return direct
	const mediaFiles = parseObjectArray(detail['product_media_files'])
	for (let index = 0; index < mediaFiles.length; index += 1) {
		const image = firstStringField(mediaFiles[index], ['signed_thumbnail_url', 'thumbnail_url', 'signed_download_url', 'file_url', 'url', 'image'])
		if (image != '') return image
	}
	return ''
}

function numberText(value: any | null): string {
	return intValue(value).toString()
}

function moneyText(value: any | null): string {
	return floatValue(value).toFixed(2)
}

function normalizedCostText(value: string): string {
	const text = decimalText(value)
	if (text == '' || text == '.') return '0.00'
	return text
}

function signedNumberText(value: number): string {
	if (value > 0) return '+' + value.toString()
	return value.toString()
}

function dateTimeText(value: any | null): string {
	const text = stringValue(value)
	if (text == '') return '-'
	const compact = text.split('T').join(' ')
	if (compact.length > 19) return compact.substring(0, 19)
	return compact
}

function alertLabel(value: string): string {
	if (value == 'LOW_STOCK') return '低库存'
	if (value == 'OUT_OF_STOCK') return '售罄'
	if (value == 'NO_MOVEMENT') return '久未变动'
	if (value == 'LOW') return '低库存'
	if (value == 'OUT') return '售罄'
	return '正常'
}

function listedLabel(value: string): string {
	if (value == 'false') return '下架'
	return '上架'
}

function optionText(source: SelectOption[], value: string, fallback: string): string {
	for (let index = 0; index < source.length; index += 1) {
		const option = source[index]
		if (option.value == value) return option.label
	}
	return fallback
}

function buildStaticSelectResponse(source: SelectOption[], params: UTSJSONObject): UTSJSONObject {
	const id = stringValue(params['id'])
	const rows: UTSJSONObject[] = []
	for (let index = 0; index < source.length; index += 1) {
		const option = source[index]
		if (id != '' && option.value != id) continue
		rows.push({ value: option.value, text: option.label, label: option.label } as UTSJSONObject)
	}
	return { data: rows, results: rows, total: rows.length, total_count: rows.length } as UTSJSONObject
}

function baseInventoryQuery(page: number, pageSize: number): InventoryListQuery {
	return {
		search: null,
		page: page,
		page_size: pageSize,
		status: null,
		alert_status: null,
		transaction_type: null,
		location_type: null,
		is_active: null,
	} as InventoryListQuery
}

const refreshStorageKey = 'refresh:pages:inventory-management:index'
const productId = ref('')
const productName = ref('')
const selectedStockId = ref('')
const initialMode = ref('')
const stocks = ref<UTSJSONObject[]>([])
const movementRecords = ref<UTSJSONObject[]>([])
const movementRecordTotal = ref(0)
const isLoading = ref(false)
const errorMessage = ref('')
const createSubmitting = ref(false)
const adjustSubmitting = ref(false)
const createSheetVisible = ref(false)
const adjustSheetVisible = ref(false)
const initialSheetConsumed = ref(false)
const sheetPanelHeight = ref(620)
const sheetScrollHeight = ref(420)
const createLocationValue = ref('')
const createLocationText = ref('')
const createQuantityText = ref('')
const createTypeValue = ref('INITIAL')
const createTypeText = ref('初始库存')
const createUnitCostText = ref('')
const createNotesText = ref('')
const adjustQuantityText = ref('')
const adjustTypeValue = ref('ADJUSTMENT')
const adjustTypeText = ref('盘点调整')
const adjustUnitCostText = ref('')
const adjustNotesText = ref('')

const createTypeOptions = [
	{ value: 'INITIAL', label: '初始库存' } as SelectOption,
	{ value: 'PURCHASE', label: '采购入库' } as SelectOption,
]

const adjustTypeOptions = [
	{ value: 'ADJUSTMENT', label: '盘点调整' } as SelectOption,
	{ value: 'DAMAGE', label: '损坏' } as SelectOption,
	{ value: 'LOSS', label: '丢失' } as SelectOption,
	{ value: 'INITIAL', label: '初始库存' } as SelectOption,
	{ value: 'PURCHASE', label: '采购入库' } as SelectOption,
	{ value: 'RETURN_IN', label: '退货入库' } as SelectOption,
]

function stockById(id: string): UTSJSONObject | null {
	for (let index = 0; index < stocks.value.length; index += 1) {
		const stock = stocks.value[index]
		if (stringValue(stock['id']) == id) return stock
	}
	return null
}

function firstStock(): UTSJSONObject | null {
	if (stocks.value.length == 0) return null
	return stocks.value[0]
}

function selectedStock(): UTSJSONObject | null {
	if (selectedStockId.value == '') return null
	return stockById(selectedStockId.value)
}

function hasStockAtLocation(locationId: string): boolean {
	for (let index = 0; index < stocks.value.length; index += 1) {
		const stock = stocks.value[index]
		if (stringValue(stock['location']) == locationId) return true
	}
	return false
}

function locationTypeText(stock: UTSJSONObject): string {
	const type = stringValue(stock['location_type'])
	if (type == 'WAREHOUSE') return '仓库'
	if (type == 'SHOP') return '门店'
	if (type == 'TRANSIT') return '在途'
	return type == '' ? '位置' : type
}

function updateProductFromStock(stock: UTSJSONObject) {
	if (productId.value == '') productId.value = stringValue(stock['product'])
	if (productName.value == '') productName.value = stringValue(stock['product_name'])
}

function markRefreshNeeded() {
	uni.setStorageSync(refreshStorageKey, '1')
}

function updateSheetLayout() {
	const info = uni.getWindowInfo()
	let panelHeight = info.windowHeight - 96
	if (panelHeight > 680) panelHeight = 680
	if (panelHeight < 500) panelHeight = 500
	let scrollHeight = panelHeight - 192
	if (scrollHeight < 280) scrollHeight = 280
	sheetPanelHeight.value = panelHeight
	sheetScrollHeight.value = scrollHeight
}

const homePath = computed((): string => {
	if (productId.value != '') return '/pages/tabbar/products'
	return '/pages/inventory-management/index'
})

const pageTitle = computed((): string => {
	if (productName.value != '') return '库存详情'
	if (productId.value != '') return '商品库存'
	return '库存详情'
})

const productTitle = computed((): string => {
	if (productName.value != '') return productName.value
	const stock = firstStock()
	if (stock != null) return stringValue(stock!['product_name'], '未命名商品')
	if (productId.value != '') return '商品 #' + productId.value
	return '商品库存'
})

const productSubtitle = computed((): string => {
	const stock = firstStock()
	if (stock == null) return productId.value == '' ? '缺少商品信息' : '商品 ID ' + productId.value
	const sku = stringValue(stock!['product_sku'])
	const barcode = stringValue(stock!['product_barcode'])
	if (sku != '' && barcode != '') return 'SKU ' + sku + ' / 条码 ' + barcode
	if (sku != '') return 'SKU ' + sku
	if (barcode != '') return '条码 ' + barcode
	return '未设置 SKU/条码'
})

const productImage = computed((): string => {
	const stock = firstStock()
	if (stock == null) return ''
	return firstImageFromStock(stock!)
})

const totalQuantityText = computed((): string => {
	let total = 0
	for (let index = 0; index < stocks.value.length; index += 1) total = total + intValue(stocks.value[index]['quantity'])
	return total.toString()
})

const totalAvailableText = computed((): string => {
	let total = 0
	for (let index = 0; index < stocks.value.length; index += 1) total = total + intValue(stocks.value[index]['available_quantity'])
	return total.toString()
})

const stockLocationCountText = computed((): string => {
	return stocks.value.length.toString()
})

const movementRecordCountText = computed((): string => {
	return movementRecordTotal.value.toString()
})

const selectedStockHint = computed((): string => {
	const stock = selectedStock()
	if (stock == null) return '选择一个库存位置后进行调整'
	return stringValue(stock!['location_name'], '未设置库存位置') + '，当前库存 ' + numberText(stock!['quantity'])
})

const adjustBeforeText = computed((): string => {
	const stock = selectedStock()
	if (stock == null) return '-'
	return numberText(stock!['quantity'])
})

const adjustChangeValue = computed((): number => {
	return intValue(adjustQuantityText.value)
})

const adjustChangeText = computed((): string => {
	if (adjustQuantityText.value == '') return '-'
	return signedNumberText(adjustChangeValue.value)
})

const adjustChangeClass = computed((): string => {
	if (adjustChangeValue.value > 0) return 'adjust-preview-value adjust-preview-in'
	if (adjustChangeValue.value < 0) return 'adjust-preview-value adjust-preview-out'
	return 'adjust-preview-value'
})

const adjustAfterText = computed((): string => {
	const stock = selectedStock()
	if (stock == null || adjustQuantityText.value == '') return '-'
	return (intValue(stock!['quantity']) + adjustChangeValue.value).toString()
})

const createButtonText = computed((): string => {
	return createSubmitting.value ? '创建中...' : '创建库存'
})

const adjustButtonText = computed((): string => {
	return adjustSubmitting.value ? '保存中...' : '保存调整'
})

const sheetPanelStyle = computed((): string => {
	return 'height:' + sheetPanelHeight.value.toString() + 'px;'
})

const sheetScrollStyle = computed((): string => {
	return 'height:' + sheetScrollHeight.value.toString() + 'px;'
})

function stockCardClass(stock: UTSJSONObject): string {
	if (stringValue(stock['id']) == selectedStockId.value) return 'stock-card stock-card-active'
	return 'stock-card'
}

function stockAlertClass(stock: UTSJSONObject): string {
	const status = stringValue(stock['alert_status'])
	if (status != '' && status != 'NORMAL') return 'stock-alert stock-alert-warning'
	return 'stock-alert'
}

function selectStock(stock: UTSJSONObject) {
	selectedStockId.value = stringValue(stock['id'])
	adjustUnitCostText.value = moneyText(stock['average_cost'])
	adjustQuantityText.value = ''
	adjustTypeValue.value = 'ADJUSTMENT'
	adjustTypeText.value = optionText(adjustTypeOptions, 'ADJUSTMENT', '盘点调整')
}

function setAdjustQuantityValue(value: number) {
	let next = value
	if (next > 999999) next = 999999
	if (next < -999999) next = -999999
	adjustQuantityText.value = next.toString()
}

function stepAdjustQuantity(delta: number) {
	setAdjustQuantityValue(intValue(adjustQuantityText.value) + delta)
}

function setAdjustQuantityQuick(value: number) {
	setAdjustQuantityValue(value)
}

function locationOptionText(item: UTSJSONObject): string {
	const name = stringValue(item['name'], stringValue(item['location_name'], '库存位置'))
	const code = stringValue(item['code'])
	if (code != '') return name + ' / ' + code
	return name
}

async function fetchLocationOptions(params: UTSJSONObject): Promise<UTSJSONObject> {
	const query = baseInventoryQuery(intValue(params['page']), intValue(params['pageSize']))
	query.page = query.page <= 0 ? 1 : query.page
	query.page_size = query.page_size <= 0 ? 50 : query.page_size
	query.search = stringValue(params['keyword']) == '' ? null : stringValue(params['keyword'])
	query.is_active = 'true'
	const response = await getInventoryLocations(query)
	const rows: UTSJSONObject[] = []
	for (let index = 0; index < response.results.length; index += 1) {
		const item = response.results[index]
		const id = stringValue(item['id'])
		if (id == '' || hasStockAtLocation(id)) continue
		rows.push({
			value: id,
			text: locationOptionText(item),
			label: locationOptionText(item),
			subtitle: locationTypeText(item),
		} as UTSJSONObject)
	}
	return { data: rows, results: rows, total: rows.length, total_count: rows.length } as UTSJSONObject
}

async function fetchCreateTypeOptions(params: UTSJSONObject): Promise<UTSJSONObject> {
	return buildStaticSelectResponse(createTypeOptions, params)
}

async function fetchAdjustTypeOptions(params: UTSJSONObject): Promise<UTSJSONObject> {
	return buildStaticSelectResponse(adjustTypeOptions, params)
}

function buildProductStockQuery(): InventoryListQuery {
	const query = baseInventoryQuery(1, 100)
	if (productId.value != '') query.product = productId.value
	if (productId.value == '' && selectedStockId.value != '') query.stock = selectedStockId.value
	return query
}

function buildMovementQuery(): InventoryListQuery {
	const query = baseInventoryQuery(1, 50)
	if (productId.value != '') query.product = productId.value
	if (productId.value == '' && selectedStockId.value != '') query.stock = selectedStockId.value
	return query
}

async function loadInitialStockIfNeeded() {
	if (selectedStockId.value == '') return
	try {
		const detail = await getInventoryStockDetail(selectedStockId.value)
		updateProductFromStock(detail)
	} catch (error) {
		errorMessage.value = parseErrorMessage(error, '库存详情加载失败')
	}
}

async function loadProductStocks() {
	const response = await getInventoryStocks(buildProductStockQuery())
	stocks.value = response.results
	for (let index = 0; index < stocks.value.length; index += 1) updateProductFromStock(stocks.value[index])
	if (selectedStockId.value != '' && stockById(selectedStockId.value) == null && stocks.value.length > 0) selectedStockId.value = stringValue(stocks.value[0]['id'])
}

async function loadMovementRecords() {
	if (productId.value == '' && selectedStockId.value == '') {
		movementRecords.value = [] as UTSJSONObject[]
		movementRecordTotal.value = 0
		return
	}
	const response = await getInventoryTransactions(buildMovementQuery())
	movementRecords.value = response.results
	let count = response.total_count
	if (count < response.results.length) count = response.results.length
	movementRecordTotal.value = count
}

async function loadAll() {
	if (isLoading.value) return
	isLoading.value = true
	errorMessage.value = ''
	try {
		await loadInitialStockIfNeeded()
		await loadProductStocks()
		await loadMovementRecords()
	} catch (error) {
		errorMessage.value = parseErrorMessage(error, '库存详情加载失败')
	} finally {
		isLoading.value = false
	}
}

function resetCreateForm() {
	createLocationValue.value = ''
	createLocationText.value = ''
	createQuantityText.value = ''
	createTypeValue.value = 'INITIAL'
	createTypeText.value = optionText(createTypeOptions, 'INITIAL', '初始库存')
	createUnitCostText.value = ''
	createNotesText.value = ''
}

function openCreateSheet() {
	if (productId.value == '') {
		uni.showToast({ title: '缺少商品ID', icon: 'none' })
		return
	}
	resetCreateForm()
	updateSheetLayout()
	createSheetVisible.value = true
}

function closeCreateSheet() {
	if (createSubmitting.value) return
	createSheetVisible.value = false
}

function openAdjustSheet(stock: UTSJSONObject) {
	selectStock(stock)
	updateSheetLayout()
	adjustSheetVisible.value = true
}

function closeAdjustSheet() {
	if (adjustSubmitting.value) return
	adjustSheetVisible.value = false
}

function openSelectedAdjustSheet() {
	const stock = selectedStock()
	if (stock == null) {
		uni.showToast({ title: '请选择库存位置', icon: 'none' })
		return
	}
	openAdjustSheet(stock!)
}

function openInitialSheetIfNeeded() {
	if (initialSheetConsumed.value) return
	if (initialMode.value == 'create') {
		initialSheetConsumed.value = true
		openCreateSheet()
		return
	}
	if (initialMode.value == 'adjust' && selectedStockId.value != '') {
		const stock = selectedStock()
		if (stock == null) return
		initialSheetConsumed.value = true
		openAdjustSheet(stock!)
	}
}

function buildCreateStockPayload(): InventoryStockCreateForProductData | null {
	const parsedProductId = parseInt(productId.value)
	if (isNaN(parsedProductId) || parsedProductId <= 0) {
		uni.showToast({ title: '缺少商品ID', icon: 'none' })
		return null
	}
	const locationId = parseInt(createLocationValue.value)
	if (isNaN(locationId) || locationId <= 0) {
		uni.showToast({ title: '请选择库存位置', icon: 'none' })
		return null
	}
	if (hasStockAtLocation(createLocationValue.value)) {
		uni.showToast({ title: '该位置已有库存记录', icon: 'none' })
		return null
	}
	const quantity = createQuantityText.value == '' ? 0 : parseInt(createQuantityText.value)
	if (isNaN(quantity) || quantity < 0) {
		uni.showToast({ title: '初始数量不能小于 0', icon: 'none' })
		return null
	}
	return {
		product: parsedProductId,
		location: locationId,
		quantity: quantity,
		transaction_type: createTypeValue.value == '' ? 'INITIAL' : createTypeValue.value,
		unit_cost: normalizedCostText(createUnitCostText.value),
		notes: createNotesText.value,
	} as InventoryStockCreateForProductData
}

async function submitCreateStock() {
	if (createSubmitting.value) return
	const payload = buildCreateStockPayload()
	if (payload == null) return
	createSubmitting.value = true
	try {
		const created = await createInventoryStockForProduct(payload!)
		const createdText = JSON.stringify(created)
		const createdObject = createdText == null || createdText == '' ? null : UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(createdText), " at pages/inventory-management/from.uvue:940")
		if (createdObject != null) selectedStockId.value = stringValue(createdObject!['id'])
		markRefreshNeeded()
		resetCreateForm()
		createSheetVisible.value = false
		uni.showToast({ title: takeLatestResponseMessage('库存创建成功'), icon: 'success' })
		await loadAll()
	} catch (error) {
		uni.showToast({ title: parseErrorMessage(error, '库存创建失败'), icon: 'none' })
	} finally {
		createSubmitting.value = false
	}
}

function buildAdjustStockPayload(): StockAdjustmentData | null {
	const stockId = parseInt(selectedStockId.value)
	if (isNaN(stockId) || stockId <= 0) {
		uni.showToast({ title: '请选择库存位置', icon: 'none' })
		return null
	}
	const change = parseInt(adjustQuantityText.value)
	if (isNaN(change) || change == 0) {
		uni.showToast({ title: '请输入非 0 的调整数量', icon: 'none' })
		return null
	}
	return {
		stock_id: stockId,
		quantity_change: change,
		transaction_type: adjustTypeValue.value == '' ? 'ADJUSTMENT' : adjustTypeValue.value,
		unit_cost: normalizedCostText(adjustUnitCostText.value),
		notes: adjustNotesText.value,
	} as StockAdjustmentData
}

async function submitAdjustStock() {
	if (adjustSubmitting.value) return
	const payload = buildAdjustStockPayload()
	if (payload == null) return
	adjustSubmitting.value = true
	try {
		await adjustInventoryStock(payload!)
		markRefreshNeeded()
		adjustQuantityText.value = ''
		adjustNotesText.value = ''
		adjustSheetVisible.value = false
		uni.showToast({ title: takeLatestResponseMessage('库存调整成功'), icon: 'success' })
		await loadAll()
	} catch (error) {
		uni.showToast({ title: parseErrorMessage(error, '库存调整失败'), icon: 'none' })
	} finally {
		adjustSubmitting.value = false
	}
}

function handleCreateLocationChange(payload: UTSJSONObject) {
	createLocationValue.value = stringValue(payload['value'])
	createLocationText.value = stringValue(payload['text'])
}

function handleCreateTypeChange(payload: UTSJSONObject) {
	createTypeValue.value = stringValue(payload['value'], 'INITIAL')
	createTypeText.value = stringValue(payload['text'], optionText(createTypeOptions, createTypeValue.value, '初始库存'))
}

function handleAdjustTypeChange(payload: UTSJSONObject) {
	adjustTypeValue.value = stringValue(payload['value'], 'ADJUSTMENT')
	adjustTypeText.value = stringValue(payload['text'], optionText(adjustTypeOptions, adjustTypeValue.value, '盘点调整'))
}

function handleCreateQuantityInput(event: any) {
	createQuantityText.value = positiveIntegerText(readInputValue(event))
}

function handleCreateUnitCostInput(event: any) {
	createUnitCostText.value = decimalText(readInputValue(event))
}

function handleCreateNotesInput(event: any) {
	createNotesText.value = readInputValue(event)
}

function handleAdjustQuantityInput(event: any) {
	adjustQuantityText.value = signedIntegerText(readInputValue(event))
}

function handleAdjustUnitCostInput(event: any) {
	adjustUnitCostText.value = decimalText(readInputValue(event))
}

function handleAdjustNotesInput(event: any) {
	adjustNotesText.value = readInputValue(event)
}

function movementTypeText(record: UTSJSONObject): string {
	const display = stringValue(record['transaction_type_display'])
	if (display != '') return display
	const type = stringValue(record['transaction_type'])
	if (type == 'PURCHASE') return '采购入库'
	if (type == 'SALE') return '销售出库'
	if (type == 'RETURN_IN') return '退货入库'
	if (type == 'RETURN_OUT') return '退货出库'
	if (type == 'TRANSFER_IN') return '调拨入库'
	if (type == 'TRANSFER_OUT') return '调拨出库'
	if (type == 'ADJUSTMENT') return '盘点调整'
	if (type == 'DAMAGE') return '损坏'
	if (type == 'LOSS') return '丢失'
	if (type == 'INITIAL') return '初始库存'
	return type == '' ? '库存变动' : type
}

function movementTitleText(record: UTSJSONObject): string {
	const location = stringValue(record['location_name'], '未知位置')
	return movementTypeText(record) + ' · ' + location
}

function movementSubtitleText(record: UTSJSONObject): string {
	const type = stringValue(record['transaction_type'])
	const orderNumber = stringValue(record['order_number'])
	if (orderNumber != '') return '订单 ' + orderNumber
	const notes = stringValue(record['notes'])
	if (notes != '') return notes
	const referenceId = stringValue(record['reference_id'])
	const referenceType = stringValue(record['reference_type'])
	if (referenceId != '' && referenceType != '') return referenceType + ' #' + referenceId
	if (referenceId != '') return '关联记录 #' + referenceId
	if (type == 'SALE') return '未关联订单'
	return '库存 ' + numberText(record['quantity_before']) + ' -> ' + numberText(record['quantity_after'])
}

function movementQuantityText(record: UTSJSONObject): string {
	const value = intValue(record['quantity'])
	return signedNumberText(value)
}

function movementQuantityClass(record: UTSJSONObject): string {
	const value = intValue(record['quantity'])
	if (value > 0) return 'movement-quantity movement-quantity-in'
	if (value < 0) return 'movement-quantity movement-quantity-out'
	return 'movement-quantity'
}

function movementTimeText(record: UTSJSONObject): string {
	const orderTime = stringValue(record['order_time'])
	if (orderTime != '') return dateTimeText(orderTime)
	return dateTimeText(record['created_at'])
}

function movementActorText(record: UTSJSONObject): string {
	const seller = stringValue(record['seller_name'])
	if (stringValue(record['transaction_type']) == 'SALE' && seller != '') return '销售人 ' + seller
	const cashierId = stringValue(record['cashier_id'])
	if (cashierId != '') return '收银员 ' + cashierId
	const userName = stringValue(record['created_by_name'])
	if (userName != '') return '操作人 ' + userName
	return '操作人 系统'
}

onLoad((query: OnLoadOptions) => {
	const productValue = query['product']
	productId.value = productValue == null ? '' : ('' + productValue)
	const stockValue = query['stock']
	if (stockValue == null) {
		const oldIdValue = query['id']
		selectedStockId.value = oldIdValue == null ? '' : ('' + oldIdValue)
	} else {
		selectedStockId.value = '' + stockValue
	}
	const modeValue = query['mode']
	initialMode.value = modeValue == null ? '' : ('' + modeValue)
	const productNameValue = query['productName']
	if (productNameValue == null) {
		productName.value = ''
	} else {
		productName.value = stringValue(UTSAndroid.consoleDebugError(decodeURIComponent('' + productNameValue), " at pages/inventory-management/from.uvue:1113"))
	}
	updateSheetLayout()
	if (initialMode.value == 'adjust' && selectedStockId.value == '') uni.showToast({ title: '请选择库存位置', icon: 'none' })
	loadAll().then(() => {
		openInitialSheetIfNeeded()
	})
})

return (): any | null => {

const _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter",_easycom_lili_universal_filter)
const _component_page_container = resolveComponent("page-container")

  return _cE("view", _uM({ class: "page" }), [
    _cV(_component_lili_universal_filter, _uM({
      title: pageTitle.value,
      showBack: true,
      showSearch: false,
      showHome: true,
      homePath: homePath.value,
      backgroundColor: "#F6F7FB"
    }), null, 8 /* PROPS */, ["title", "homePath"]),
    _cE("scroll-view", _uM({
      "scroll-y": "true",
      style: _nS(_uM({"flex":"1"})),
      class: "page-scroll"
    }), [
      _cE("view", _uM({ class: "page-content" }), [
        isTrue(unref(errorMessage) != '' && !unref(isLoading))
          ? _cE("view", _uM({
              key: 0,
              class: "error-band"
            }), [
              _cE("text", _uM({ class: "error-title" }), "加载失败"),
              _cE("text", _uM({ class: "error-desc" }), _tD(unref(errorMessage)), 1 /* TEXT */),
              _cE("view", _uM({
                class: "error-action",
                onClick: loadAll
              }), [
                _cE("text", _uM({ class: "error-action-text" }), "重新加载")
              ])
            ])
          : _cC("v-if", true),
        _cE("view", _uM({ class: "product-band" }), [
          _cE("view", _uM({ class: "product-main" }), [
            productImage.value != ''
              ? _cE("image", _uM({
                  key: 0,
                  class: "product-image",
                  src: productImage.value,
                  mode: "aspectFill"
                }), null, 8 /* PROPS */, ["src"])
              : _cE("view", _uM({
                  key: 1,
                  class: "product-image product-image-empty"
                }), [
                  _cE("text", _uM({ class: "product-image-empty-text" }), "货")
                ]),
            _cE("view", _uM({ class: "product-title-wrap" }), [
              _cE("text", _uM({ class: "product-title" }), _tD(productTitle.value), 1 /* TEXT */),
              _cE("text", _uM({ class: "product-subtitle" }), _tD(productSubtitle.value), 1 /* TEXT */)
            ])
          ]),
          _cE("view", _uM({ class: "summary-grid" }), [
            _cE("view", _uM({ class: "summary-item" }), [
              _cE("text", _uM({ class: "summary-label" }), "总库存"),
              _cE("text", _uM({ class: "summary-value" }), _tD(totalQuantityText.value), 1 /* TEXT */)
            ]),
            _cE("view", _uM({ class: "summary-item" }), [
              _cE("text", _uM({ class: "summary-label" }), "可用"),
              _cE("text", _uM({ class: "summary-value" }), _tD(totalAvailableText.value), 1 /* TEXT */)
            ]),
            _cE("view", _uM({ class: "summary-item" }), [
              _cE("text", _uM({ class: "summary-label" }), "位置"),
              _cE("text", _uM({ class: "summary-value" }), _tD(stockLocationCountText.value), 1 /* TEXT */)
            ]),
            _cE("view", _uM({ class: "summary-item" }), [
              _cE("text", _uM({ class: "summary-label" }), "流水记录"),
              _cE("text", _uM({ class: "summary-value" }), _tD(movementRecordCountText.value), 1 /* TEXT */)
            ])
          ])
        ]),
        _cE("view", _uM({ class: "section-head section-head-row" }), [
          _cE("view", _uM({ class: "section-title-wrap" }), [
            _cE("text", _uM({ class: "section-title" }), "多位置库存"),
            _cE("text", _uM({ class: "section-subtitle" }), "点击一个位置调整库存")
          ]),
          _cE("view", _uM({
            class: "section-action",
            onClick: openCreateSheet
          }), [
            _cE("text", _uM({ class: "section-action-text" }), "新增库存")
          ])
        ]),
        isTrue(unref(isLoading))
          ? _cE("view", _uM({
              key: 1,
              class: "empty-band"
            }), [
              _cE("text", _uM({ class: "empty-text" }), "正在加载库存")
            ])
          : unref(stocks).length == 0
            ? _cE("view", _uM({
                key: 2,
                class: "empty-band"
              }), [
                _cE("text", _uM({ class: "empty-text" }), "该商品还没有库存位置")
              ])
            : _cC("v-if", true),
        _cE(Fragment, null, RenderHelpers.renderList(unref(stocks), (stock, __key, __index, _cached): any => {
          return _cE("view", _uM({
            key: 'stock-' + stringValue(stock['id']),
            class: _nC(stockCardClass(stock)),
            onClick: () => {openAdjustSheet(stock)}
          }), [
            _cE("view", _uM({ class: "stock-row-top" }), [
              _cE("view", _uM({ class: "stock-location-wrap" }), [
                _cE("text", _uM({ class: "stock-location" }), _tD(stringValue(stock['location_name'], '未设置库存位置')), 1 /* TEXT */),
                _cE("text", _uM({ class: "stock-location-sub" }), _tD(locationTypeText(stock)), 1 /* TEXT */)
              ]),
              _cE("text", _uM({
                class: _nC(stockAlertClass(stock))
              }), _tD(alertLabel(stringValue(stock['alert_status']))), 3 /* TEXT, CLASS */)
            ]),
            _cE("view", _uM({ class: "stock-metrics" }), [
              _cE("view", _uM({ class: "stock-metric" }), [
                _cE("text", _uM({ class: "stock-metric-label" }), "当前"),
                _cE("text", _uM({ class: "stock-metric-value" }), _tD(numberText(stock['quantity'])), 1 /* TEXT */)
              ]),
              _cE("view", _uM({ class: "stock-metric" }), [
                _cE("text", _uM({ class: "stock-metric-label" }), "可用"),
                _cE("text", _uM({ class: "stock-metric-value" }), _tD(numberText(stock['available_quantity'])), 1 /* TEXT */)
              ]),
              _cE("view", _uM({ class: "stock-metric" }), [
                _cE("text", _uM({ class: "stock-metric-label" }), "占用"),
                _cE("text", _uM({ class: "stock-metric-value" }), _tD(numberText(stock['reserved_quantity'])), 1 /* TEXT */)
              ]),
              _cE("view", _uM({ class: "stock-metric" }), [
                _cE("text", _uM({ class: "stock-metric-label" }), "均价"),
                _cE("text", _uM({ class: "stock-metric-value stock-metric-money" }), _tD(moneyText(stock['average_cost'])), 1 /* TEXT */)
              ])
            ]),
            _cE("view", _uM({ class: "stock-row-bottom" }), [
              _cE("text", _uM({ class: "stock-tag" }), _tD(listedLabel(stringValue(stock['is_listed']))), 1 /* TEXT */),
              _cE("text", _uM({ class: "stock-muted" }), "最近变动 " + _tD(dateTimeText(stock['last_movement_at'])), 1 /* TEXT */)
            ])
          ], 10 /* CLASS, PROPS */, ["onClick"])
        }), 128 /* KEYED_FRAGMENT */),
        _cE("view", _uM({ class: "section-head" }), [
          _cE("text", _uM({ class: "section-title" }), "库存变动记录"),
          _cE("text", _uM({ class: "section-subtitle" }), "包含订单销售、手动调整、采购入库等库存流水")
        ]),
        unref(movementRecords).length == 0
          ? _cE("view", _uM({
              key: 3,
              class: "empty-band"
            }), [
              _cE("text", _uM({ class: "empty-text" }), "暂无库存变动记录")
            ])
          : _cC("v-if", true),
        _cE(Fragment, null, RenderHelpers.renderList(unref(movementRecords), (record, __key, __index, _cached): any => {
          return _cE("view", _uM({
            key: 'movement-' + stringValue(record['id']),
            class: "movement-row"
          }), [
            _cE("view", _uM({ class: "movement-row-top" }), [
              _cE("view", _uM({ class: "movement-main" }), [
                _cE("text", _uM({ class: "movement-title" }), _tD(movementTitleText(record)), 1 /* TEXT */),
                _cE("text", _uM({ class: "movement-subtitle" }), _tD(movementSubtitleText(record)), 1 /* TEXT */)
              ]),
              _cE("text", _uM({
                class: _nC(movementQuantityClass(record))
              }), _tD(movementQuantityText(record)), 3 /* TEXT, CLASS */)
            ]),
            _cE("view", _uM({ class: "movement-row-bottom" }), [
              _cE("text", _uM({ class: "movement-meta" }), _tD(movementTimeText(record)), 1 /* TEXT */),
              _cE("text", _uM({ class: "movement-meta" }), _tD(movementActorText(record)), 1 /* TEXT */)
            ])
          ])
        }), 128 /* KEYED_FRAGMENT */)
      ])
    ], 4 /* STYLE */),
    _cV(_component_page_container, _uM({
      show: unref(createSheetVisible),
      position: "bottom",
      round: true,
      overlay: true,
      duration: 240,
      "overlay-style": "background-color: rgba(15, 23, 42, 0.42);",
      "custom-style": "background-color: #FFFFFF;",
      onClickoverlay: closeCreateSheet
    }), _uM({
      default: withSlotCtx((): any[] => [
        _cE("view", _uM({
          class: "sheet-panel",
          style: _nS(sheetPanelStyle.value)
        }), [
          _cE("view", _uM({ class: "sheet-handle-wrap" }), [
            _cE("view", _uM({ class: "sheet-handle" }))
          ]),
          _cE("view", _uM({ class: "sheet-header" }), [
            _cE("view", _uM({ class: "sheet-title-wrap" }), [
              _cE("text", _uM({ class: "sheet-title" }), "新增库存"),
              _cE("text", _uM({ class: "sheet-subtitle" }), _tD(productTitle.value), 1 /* TEXT */)
            ]),
            _cE("view", _uM({
              class: "sheet-close",
              onClick: closeCreateSheet
            }), [
              _cE("text", _uM({ class: "sheet-close-text" }), "×")
            ])
          ]),
          _cE("scroll-view", _uM({
            "scroll-y": "true",
            class: "sheet-scroll",
            style: _nS(sheetScrollStyle.value)
          }), [
            _cE("view", _uM({ class: "field" }), [
              _cE("text", _uM({ class: "field-label" }), "库存位置"),
              _cV(unref(liliBottomSelect), _uM({
                value: unref(createLocationValue),
                valueText: unref(createLocationText),
                title: "选择库存位置",
                placeholder: "请选择库存位置",
                searchPlaceholder: "搜索库存位置",
                emptyText: "暂无可用库存位置",
                fetchData: fetchLocationOptions,
                showAddAction: false,
                showEditAction: false,
                onChange: handleCreateLocationChange
              }), null, 8 /* PROPS */, ["value", "valueText"])
            ]),
            _cE("view", _uM({ class: "field-row" }), [
              _cE("view", _uM({ class: "field-half" }), [
                _cE("text", _uM({ class: "field-label" }), "初始数量"),
                _cE("input", _uM({
                  class: "input",
                  type: "number",
                  value: unref(createQuantityText),
                  placeholder: "0",
                  onInput: handleCreateQuantityInput
                }), null, 40 /* PROPS, NEED_HYDRATION */, ["value"])
              ]),
              _cE("view", _uM({ class: "field-half field-half-right" }), [
                _cE("text", _uM({ class: "field-label" }), "单位成本"),
                _cE("input", _uM({
                  class: "input",
                  type: "digit",
                  value: unref(createUnitCostText),
                  placeholder: "0.00",
                  onInput: handleCreateUnitCostInput
                }), null, 40 /* PROPS, NEED_HYDRATION */, ["value"])
              ])
            ]),
            _cE("view", _uM({ class: "field" }), [
              _cE("text", _uM({ class: "field-label" }), "入库类型"),
              _cV(unref(liliBottomSelect), _uM({
                value: unref(createTypeValue),
                valueText: unref(createTypeText),
                title: "选择入库类型",
                placeholder: "请选择入库类型",
                fetchData: fetchCreateTypeOptions,
                showAddAction: false,
                showEditAction: false,
                onChange: handleCreateTypeChange
              }), null, 8 /* PROPS */, ["value", "valueText"])
            ]),
            _cE("view", _uM({ class: "field" }), [
              _cE("text", _uM({ class: "field-label" }), "备注"),
              _cE("textarea", _uM({
                class: "textarea",
                value: unref(createNotesText),
                placeholder: "请输入备注",
                onInput: handleCreateNotesInput
              }), null, 40 /* PROPS, NEED_HYDRATION */, ["value"])
            ])
          ], 4 /* STYLE */),
          _cE("view", _uM({ class: "sheet-actions" }), [
            _cE("view", _uM({
              class: "sheet-btn sheet-btn-light",
              onClick: closeCreateSheet
            }), [
              _cE("text", _uM({ class: "sheet-btn-light-text" }), "取消")
            ]),
            _cE("view", _uM({
              class: "sheet-btn sheet-btn-primary",
              onClick: submitCreateStock
            }), [
              _cE("text", _uM({ class: "sheet-btn-primary-text" }), _tD(createButtonText.value), 1 /* TEXT */)
            ])
          ])
        ], 4 /* STYLE */)
      ]),
      _: 1 /* STABLE */
    }), 8 /* PROPS */, ["show"]),
    _cV(_component_page_container, _uM({
      show: unref(adjustSheetVisible),
      position: "bottom",
      round: true,
      overlay: true,
      duration: 240,
      "overlay-style": "background-color: rgba(15, 23, 42, 0.42);",
      "custom-style": "background-color: #FFFFFF;",
      onClickoverlay: closeAdjustSheet
    }), _uM({
      default: withSlotCtx((): any[] => [
        _cE("view", _uM({
          class: "sheet-panel",
          style: _nS(sheetPanelStyle.value)
        }), [
          _cE("view", _uM({ class: "sheet-handle-wrap" }), [
            _cE("view", _uM({ class: "sheet-handle" }))
          ]),
          _cE("view", _uM({ class: "sheet-header" }), [
            _cE("view", _uM({ class: "sheet-title-wrap" }), [
              _cE("text", _uM({ class: "sheet-title" }), "调整库存"),
              _cE("text", _uM({ class: "sheet-subtitle" }), _tD(selectedStockHint.value), 1 /* TEXT */)
            ]),
            _cE("view", _uM({
              class: "sheet-close",
              onClick: closeAdjustSheet
            }), [
              _cE("text", _uM({ class: "sheet-close-text" }), "×")
            ])
          ]),
          _cE("scroll-view", _uM({
            "scroll-y": "true",
            class: "sheet-scroll",
            style: _nS(sheetScrollStyle.value)
          }), [
            _cE("view", _uM({ class: "adjust-preview" }), [
              _cE("view", _uM({ class: "adjust-preview-item" }), [
                _cE("text", _uM({ class: "adjust-preview-label" }), "变动前"),
                _cE("text", _uM({ class: "adjust-preview-value" }), _tD(adjustBeforeText.value), 1 /* TEXT */)
              ]),
              _cE("view", _uM({ class: "adjust-preview-item" }), [
                _cE("text", _uM({ class: "adjust-preview-label" }), "本次变化"),
                _cE("text", _uM({
                  class: _nC(adjustChangeClass.value)
                }), _tD(adjustChangeText.value), 3 /* TEXT, CLASS */)
              ]),
              _cE("view", _uM({ class: "adjust-preview-item" }), [
                _cE("text", _uM({ class: "adjust-preview-label" }), "预计调整后"),
                _cE("text", _uM({ class: "adjust-preview-value adjust-preview-strong" }), _tD(adjustAfterText.value), 1 /* TEXT */)
              ])
            ]),
            _cE("view", _uM({ class: "field adjust-quantity-field" }), [
              _cE("view", _uM({ class: "field-title-row" }), [
                _cE("text", _uM({ class: "field-label" }), "调整数量"),
                _cE("text", _uM({ class: "field-help" }), "正数入库，负数出库")
              ]),
              _cE("view", _uM({ class: "quantity-input-row" }), [
                _cE("view", _uM({
                  class: "quantity-step quantity-step-out",
                  onClick: () => {stepAdjustQuantity(-1)}
                }), [
                  _cE("text", _uM({ class: "quantity-step-text" }), "-")
                ], 8 /* PROPS */, ["onClick"]),
                _cE("input", _uM({
                  class: "quantity-input",
                  type: "number",
                  value: unref(adjustQuantityText),
                  placeholder: "0",
                  onInput: handleAdjustQuantityInput
                }), null, 40 /* PROPS, NEED_HYDRATION */, ["value"]),
                _cE("view", _uM({
                  class: "quantity-step quantity-step-in",
                  onClick: () => {stepAdjustQuantity(1)}
                }), [
                  _cE("text", _uM({ class: "quantity-step-text" }), "+")
                ], 8 /* PROPS */, ["onClick"])
              ]),
              _cE("view", _uM({ class: "quantity-quick-row" }), [
                _cE("view", _uM({
                  class: "quantity-quick quantity-quick-out",
                  onClick: () => {setAdjustQuantityQuick(-10)}
                }), [
                  _cE("text", _uM({ class: "quantity-quick-out-text" }), "-10")
                ], 8 /* PROPS */, ["onClick"]),
                _cE("view", _uM({
                  class: "quantity-quick quantity-quick-out",
                  onClick: () => {setAdjustQuantityQuick(-5)}
                }), [
                  _cE("text", _uM({ class: "quantity-quick-out-text" }), "-5")
                ], 8 /* PROPS */, ["onClick"]),
                _cE("view", _uM({
                  class: "quantity-quick quantity-quick-out",
                  onClick: () => {setAdjustQuantityQuick(-1)}
                }), [
                  _cE("text", _uM({ class: "quantity-quick-out-text" }), "-1")
                ], 8 /* PROPS */, ["onClick"]),
                _cE("view", _uM({
                  class: "quantity-quick quantity-quick-in",
                  onClick: () => {setAdjustQuantityQuick(1)}
                }), [
                  _cE("text", _uM({ class: "quantity-quick-in-text" }), "+1")
                ], 8 /* PROPS */, ["onClick"]),
                _cE("view", _uM({
                  class: "quantity-quick quantity-quick-in",
                  onClick: () => {setAdjustQuantityQuick(5)}
                }), [
                  _cE("text", _uM({ class: "quantity-quick-in-text" }), "+5")
                ], 8 /* PROPS */, ["onClick"]),
                _cE("view", _uM({
                  class: "quantity-quick quantity-quick-in quantity-quick-last",
                  onClick: () => {setAdjustQuantityQuick(10)}
                }), [
                  _cE("text", _uM({ class: "quantity-quick-in-text" }), "+10")
                ], 8 /* PROPS */, ["onClick"])
              ])
            ]),
            _cE("view", _uM({ class: "field" }), [
              _cE("text", _uM({ class: "field-label" }), "单位成本"),
              _cE("input", _uM({
                class: "input",
                type: "digit",
                value: unref(adjustUnitCostText),
                placeholder: "0.00",
                onInput: handleAdjustUnitCostInput
              }), null, 40 /* PROPS, NEED_HYDRATION */, ["value"])
            ]),
            _cE("view", _uM({ class: "field" }), [
              _cE("text", _uM({ class: "field-label" }), "调整类型"),
              _cV(unref(liliBottomSelect), _uM({
                value: unref(adjustTypeValue),
                valueText: unref(adjustTypeText),
                title: "选择调整类型",
                placeholder: "请选择调整类型",
                fetchData: fetchAdjustTypeOptions,
                showAddAction: false,
                showEditAction: false,
                onChange: handleAdjustTypeChange
              }), null, 8 /* PROPS */, ["value", "valueText"])
            ]),
            _cE("view", _uM({ class: "field" }), [
              _cE("text", _uM({ class: "field-label" }), "备注"),
              _cE("textarea", _uM({
                class: "textarea",
                value: unref(adjustNotesText),
                placeholder: "请输入调整原因",
                onInput: handleAdjustNotesInput
              }), null, 40 /* PROPS, NEED_HYDRATION */, ["value"])
            ])
          ], 4 /* STYLE */),
          _cE("view", _uM({ class: "sheet-actions" }), [
            _cE("view", _uM({
              class: "sheet-btn sheet-btn-light",
              onClick: closeAdjustSheet
            }), [
              _cE("text", _uM({ class: "sheet-btn-light-text" }), "取消")
            ]),
            _cE("view", _uM({
              class: "sheet-btn sheet-btn-primary",
              onClick: submitAdjustStock
            }), [
              _cE("text", _uM({ class: "sheet-btn-primary-text" }), _tD(adjustButtonText.value), 1 /* TEXT */)
            ])
          ])
        ], 4 /* STYLE */)
      ]),
      _: 1 /* STABLE */
    }), 8 /* PROPS */, ["show"])
  ])
}
}

})
export default __sfc__
const GenPagesInventoryManagementFromStyles = [_uM([["page", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#F6F7FB"]]))], ["page-scroll", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#F6F7FB"]]))], ["page-content", _pS(_uM([["paddingTop", 8], ["paddingRight", 8], ["paddingBottom", 96], ["paddingLeft", 8]]))], ["error-band", _pS(_uM([["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#FECACA"], ["borderRightColor", "#FECACA"], ["borderBottomColor", "#FECACA"], ["borderLeftColor", "#FECACA"], ["paddingTop", 14], ["paddingRight", 14], ["paddingBottom", 14], ["paddingLeft", 14], ["marginBottom", 10], ["alignItems", "center"]]))], ["error-title", _pS(_uM([["fontSize", 16], ["lineHeight", "22px"], ["color", "#B42318"], ["fontWeight", "bold"]]))], ["error-desc", _pS(_uM([["marginTop", 6], ["fontSize", 13], ["lineHeight", "18px"], ["color", "#7F1D1D"], ["textAlign", "center"]]))], ["error-action", _pS(_uM([["marginTop", 12], ["height", 38], ["paddingLeft", 18], ["paddingRight", 18], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#0F172A"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["error-action-text", _pS(_uM([["fontSize", 13], ["lineHeight", "18px"], ["color", "#FFFFFF"], ["fontWeight", "bold"]]))], ["product-band", _pS(_uM([["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E2E8F0"], ["borderRightColor", "#E2E8F0"], ["borderBottomColor", "#E2E8F0"], ["borderLeftColor", "#E2E8F0"], ["paddingTop", 12], ["paddingRight", 12], ["paddingBottom", 12], ["paddingLeft", 12], ["marginBottom", 12]]))], ["product-main", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"]]))], ["product-image", _pS(_uM([["width", 56], ["height", 56], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#E2E8F0"]]))], ["product-image-empty", _pS(_uM([["alignItems", "center"], ["justifyContent", "center"]]))], ["product-image-empty-text", _pS(_uM([["fontSize", 18], ["lineHeight", "24px"], ["color", "#64748B"], ["fontWeight", "bold"]]))], ["product-title-wrap", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["marginLeft", 10]]))], ["product-title", _pS(_uM([["fontSize", 18], ["lineHeight", "24px"], ["color", "#0F172A"], ["fontWeight", "bold"]]))], ["product-subtitle", _pS(_uM([["marginTop", 4], ["fontSize", 12], ["lineHeight", "17px"], ["color", "#64748B"]]))], ["summary-grid", _pS(_uM([["flexDirection", "row"], ["marginTop", 12]]))], ["summary-item", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["minHeight", 58], ["backgroundColor", "#F8FAFC"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["alignItems", "center"], ["justifyContent", "center"], ["marginRight", 6]]))], ["summary-label", _pS(_uM([["fontSize", 11], ["lineHeight", "15px"], ["color", "#64748B"]]))], ["summary-value", _pS(_uM([["marginTop", 4], ["fontSize", 18], ["lineHeight", "24px"], ["color", "#0F172A"], ["fontWeight", "bold"]]))], ["section-head", _pS(_uM([["marginTop", 6], ["marginBottom", 8]]))], ["section-head-row", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "space-between"]]))], ["section-title-wrap", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["paddingRight", 8]]))], ["section-title", _pS(_uM([["fontSize", 16], ["lineHeight", "22px"], ["color", "#0F172A"], ["fontWeight", "bold"]]))], ["section-subtitle", _pS(_uM([["marginTop", 3], ["fontSize", 12], ["lineHeight", "17px"], ["color", "#64748B"]]))], ["section-action", _pS(_uM([["height", 36], ["paddingLeft", 12], ["paddingRight", 12], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#0F172A"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["section-action-text", _pS(_uM([["fontSize", 13], ["lineHeight", "18px"], ["color", "#FFFFFF"], ["fontWeight", "bold"]]))], ["empty-band", _pS(_uM([["minHeight", 62], ["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E2E8F0"], ["borderRightColor", "#E2E8F0"], ["borderBottomColor", "#E2E8F0"], ["borderLeftColor", "#E2E8F0"], ["alignItems", "center"], ["justifyContent", "center"], ["marginBottom", 10]]))], ["empty-text", _pS(_uM([["fontSize", 13], ["lineHeight", "18px"], ["color", "#64748B"]]))], ["stock-card", _pS(_uM([["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E2E8F0"], ["borderRightColor", "#E2E8F0"], ["borderBottomColor", "#E2E8F0"], ["borderLeftColor", "#E2E8F0"], ["paddingTop", 12], ["paddingRight", 12], ["paddingBottom", 12], ["paddingLeft", 12], ["marginBottom", 8]]))], ["stock-card-active", _pS(_uM([["borderTopColor", "#0F172A"], ["borderRightColor", "#0F172A"], ["borderBottomColor", "#0F172A"], ["borderLeftColor", "#0F172A"], ["backgroundColor", "#F8FAFC"]]))], ["stock-row-top", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "space-between"]]))], ["stock-location-wrap", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["paddingRight", 8]]))], ["stock-location", _pS(_uM([["fontSize", 15], ["lineHeight", "21px"], ["color", "#0F172A"], ["fontWeight", "bold"]]))], ["stock-location-sub", _pS(_uM([["marginTop", 3], ["fontSize", 12], ["lineHeight", "17px"], ["color", "#64748B"]]))], ["stock-alert", _pS(_uM([["height", 24], ["lineHeight", "24px"], ["paddingLeft", 8], ["paddingRight", 8], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["backgroundColor", "#E8F7EF"], ["color", "#047857"], ["fontSize", 11]]))], ["stock-alert-warning", _pS(_uM([["backgroundColor", "#FFF7ED"], ["color", "#B45309"]]))], ["stock-metrics", _pS(_uM([["flexDirection", "row"], ["marginTop", 10]]))], ["stock-metric", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["minHeight", 50], ["alignItems", "center"], ["justifyContent", "center"], ["borderRightWidth", 1], ["borderRightStyle", "solid"], ["borderRightColor", "#E2E8F0"]]))], ["stock-metric-label", _pS(_uM([["fontSize", 11], ["lineHeight", "15px"], ["color", "#64748B"]]))], ["stock-metric-value", _pS(_uM([["marginTop", 3], ["fontSize", 16], ["lineHeight", "22px"], ["color", "#0F172A"], ["fontWeight", "bold"]]))], ["stock-metric-money", _pS(_uM([["fontSize", 13], ["lineHeight", "18px"]]))], ["stock-row-bottom", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"], ["marginTop", 10]]))], ["stock-tag", _pS(_uM([["height", 24], ["lineHeight", "24px"], ["paddingLeft", 8], ["paddingRight", 8], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["backgroundColor", "#EEF2FF"], ["color", "#334155"], ["fontSize", 11], ["marginRight", 8]]))], ["stock-muted", _pS(_uM([["fontSize", 12], ["lineHeight", "17px"], ["color", "#64748B"]]))], ["field", _pS(_uM([["marginBottom", 10]]))], ["field-row", _pS(_uM([["flexDirection", "row"], ["marginBottom", 10]]))], ["field-half", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"]]))], ["field-half-right", _pS(_uM([["marginLeft", 8]]))], ["field-label", _pS(_uM([["fontSize", 13], ["lineHeight", "18px"], ["color", "#0F172A"], ["fontWeight", "bold"], ["marginBottom", 6]]))], ["input", _pS(_uM([["height", 42], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#F8FAFC"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#CBD5E1"], ["borderRightColor", "#CBD5E1"], ["borderBottomColor", "#CBD5E1"], ["borderLeftColor", "#CBD5E1"], ["paddingLeft", 10], ["paddingRight", 10], ["fontSize", 14], ["color", "#0F172A"]]))], ["textarea", _pS(_uM([["height", 82], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#F8FAFC"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#CBD5E1"], ["borderRightColor", "#CBD5E1"], ["borderBottomColor", "#CBD5E1"], ["borderLeftColor", "#CBD5E1"], ["paddingTop", 10], ["paddingRight", 10], ["paddingBottom", 10], ["paddingLeft", 10], ["fontSize", 14], ["color", "#0F172A"]]))], ["field-title-row", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "space-between"], ["marginBottom", 6]]))], ["field-help", _pS(_uM([["fontSize", 12], ["lineHeight", "17px"], ["color", "#64748B"]]))], ["adjust-quantity-field", _pS(_uM([["backgroundColor", "#FFFFFF"]]))], ["quantity-input-row", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"]]))], ["quantity-step", _pS(_uM([["width", 46], ["height", 46], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["alignItems", "center"], ["justifyContent", "center"]]))], ["quantity-step-out", _pS(_uM([["backgroundColor", "#FEF2F2"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#FCA5A5"], ["borderRightColor", "#FCA5A5"], ["borderBottomColor", "#FCA5A5"], ["borderLeftColor", "#FCA5A5"]]))], ["quantity-step-in", _pS(_uM([["backgroundColor", "#E8F7EF"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#86EFAC"], ["borderRightColor", "#86EFAC"], ["borderBottomColor", "#86EFAC"], ["borderLeftColor", "#86EFAC"]]))], ["quantity-step-text", _pS(_uM([["fontSize", 24], ["lineHeight", "28px"], ["color", "#0F172A"], ["fontWeight", "bold"]]))], ["quantity-input", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["height", 46], ["marginLeft", 8], ["marginRight", 8], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#F8FAFC"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#CBD5E1"], ["borderRightColor", "#CBD5E1"], ["borderBottomColor", "#CBD5E1"], ["borderLeftColor", "#CBD5E1"], ["textAlign", "center"], ["fontSize", 18], ["color", "#0F172A"], ["fontWeight", "bold"]]))], ["quantity-quick-row", _pS(_uM([["flexDirection", "row"], ["marginTop", 8]]))], ["quantity-quick", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["height", 34], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["alignItems", "center"], ["justifyContent", "center"], ["marginRight", 5]]))], ["quantity-quick-out", _pS(_uM([["backgroundColor", "#FFF7ED"], ["borderTopColor", "#FDBA74"], ["borderRightColor", "#FDBA74"], ["borderBottomColor", "#FDBA74"], ["borderLeftColor", "#FDBA74"]]))], ["quantity-quick-in", _pS(_uM([["backgroundColor", "#F0FDF4"], ["borderTopColor", "#86EFAC"], ["borderRightColor", "#86EFAC"], ["borderBottomColor", "#86EFAC"], ["borderLeftColor", "#86EFAC"]]))], ["quantity-quick-last", _pS(_uM([["marginRight", 0]]))], ["quantity-quick-out-text", _pS(_uM([["fontSize", 12], ["lineHeight", "17px"], ["color", "#B42318"], ["fontWeight", "bold"]]))], ["quantity-quick-in-text", _pS(_uM([["fontSize", 12], ["lineHeight", "17px"], ["color", "#047857"], ["fontWeight", "bold"]]))], ["adjust-preview", _pS(_uM([["flexDirection", "row"], ["backgroundColor", "#F8FAFC"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E2E8F0"], ["borderRightColor", "#E2E8F0"], ["borderBottomColor", "#E2E8F0"], ["borderLeftColor", "#E2E8F0"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["paddingTop", 10], ["paddingBottom", 10], ["marginBottom", 10]]))], ["adjust-preview-item", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["alignItems", "center"], ["borderRightWidth", 1], ["borderRightStyle", "solid"], ["borderRightColor", "#E2E8F0"]]))], ["adjust-preview-label", _pS(_uM([["fontSize", 11], ["lineHeight", "15px"], ["color", "#64748B"]]))], ["adjust-preview-value", _pS(_uM([["marginTop", 4], ["fontSize", 16], ["lineHeight", "22px"], ["color", "#0F172A"], ["fontWeight", "bold"]]))], ["adjust-preview-in", _pS(_uM([["color", "#047857"]]))], ["adjust-preview-out", _pS(_uM([["color", "#B42318"]]))], ["adjust-preview-strong", _pS(_uM([["color", "#0F172A"]]))], ["movement-row", _pS(_uM([["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E2E8F0"], ["borderRightColor", "#E2E8F0"], ["borderBottomColor", "#E2E8F0"], ["borderLeftColor", "#E2E8F0"], ["paddingTop", 12], ["paddingRight", 12], ["paddingBottom", 12], ["paddingLeft", 12], ["marginBottom", 8]]))], ["movement-row-top", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "space-between"]]))], ["movement-main", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["paddingRight", 8]]))], ["movement-title", _pS(_uM([["fontSize", 14], ["lineHeight", "20px"], ["color", "#0F172A"], ["fontWeight", "bold"]]))], ["movement-subtitle", _pS(_uM([["marginTop", 3], ["fontSize", 12], ["lineHeight", "17px"], ["color", "#64748B"]]))], ["movement-quantity", _pS(_uM([["fontSize", 15], ["lineHeight", "21px"], ["color", "#0F172A"], ["fontWeight", "bold"]]))], ["movement-quantity-in", _pS(_uM([["color", "#047857"]]))], ["movement-quantity-out", _pS(_uM([["color", "#B42318"]]))], ["movement-row-bottom", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "space-between"], ["marginTop", 10], ["paddingTop", 8], ["borderTopWidth", 1], ["borderTopStyle", "solid"], ["borderTopColor", "#E2E8F0"]]))], ["movement-meta", _pS(_uM([["fontSize", 12], ["lineHeight", "17px"], ["color", "#475569"]]))], ["sheet-panel", _pS(_uM([["backgroundColor", "#FFFFFF"], ["paddingLeft", 14], ["paddingRight", 14], ["paddingBottom", 12], ["borderTopLeftRadius", 16], ["borderTopRightRadius", 16], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E2E8F0"], ["borderRightColor", "#E2E8F0"], ["borderBottomColor", "#E2E8F0"], ["borderLeftColor", "#E2E8F0"]]))], ["sheet-handle-wrap", _pS(_uM([["height", 22], ["alignItems", "center"], ["justifyContent", "center"]]))], ["sheet-handle", _pS(_uM([["width", 42], ["height", 4], ["borderTopLeftRadius", 2], ["borderTopRightRadius", 2], ["borderBottomRightRadius", 2], ["borderBottomLeftRadius", 2], ["backgroundColor", "#CBD5E1"]]))], ["sheet-header", _pS(_uM([["minHeight", 56], ["flexDirection", "row"], ["alignItems", "center"], ["borderBottomWidth", 1], ["borderBottomStyle", "solid"], ["borderBottomColor", "#E2E8F0"], ["paddingBottom", 10]]))], ["sheet-title-wrap", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["paddingRight", 10]]))], ["sheet-title", _pS(_uM([["fontSize", 17], ["lineHeight", "23px"], ["color", "#0F172A"], ["fontWeight", "bold"]]))], ["sheet-subtitle", _pS(_uM([["marginTop", 3], ["fontSize", 12], ["lineHeight", "17px"], ["color", "#64748B"]]))], ["sheet-close", _pS(_uM([["width", 38], ["height", 38], ["borderTopLeftRadius", 19], ["borderTopRightRadius", 19], ["borderBottomRightRadius", 19], ["borderBottomLeftRadius", 19], ["backgroundColor", "#F8FAFC"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E2E8F0"], ["borderRightColor", "#E2E8F0"], ["borderBottomColor", "#E2E8F0"], ["borderLeftColor", "#E2E8F0"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["sheet-close-text", _pS(_uM([["fontSize", 24], ["lineHeight", "24px"], ["color", "#64748B"], ["fontWeight", "bold"]]))], ["sheet-scroll", _pS(_uM([["paddingTop", 12], ["paddingBottom", 12]]))], ["sheet-actions", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"], ["paddingTop", 10], ["borderTopWidth", 1], ["borderTopStyle", "solid"], ["borderTopColor", "#E2E8F0"]]))], ["sheet-btn", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["height", 42], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["alignItems", "center"], ["justifyContent", "center"]]))], ["sheet-btn-light", _pS(_uM([["backgroundColor", "#F8FAFC"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#CBD5E1"], ["borderRightColor", "#CBD5E1"], ["borderBottomColor", "#CBD5E1"], ["borderLeftColor", "#CBD5E1"], ["marginRight", 8]]))], ["sheet-btn-primary", _pS(_uM([["backgroundColor", "#0F172A"], ["marginLeft", 8]]))], ["sheet-btn-light-text", _pS(_uM([["fontSize", 14], ["lineHeight", "19px"], ["color", "#334155"], ["fontWeight", "bold"]]))]]),_uM([["sheet-btn-primary-text", _pS(_uM([["fontSize", 14], ["lineHeight", "19px"], ["color", "#FFFFFF"], ["fontWeight", "bold"]]))]])]
