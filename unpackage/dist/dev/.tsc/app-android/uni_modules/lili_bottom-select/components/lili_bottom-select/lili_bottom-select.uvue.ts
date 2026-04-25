type SelectChangePayload = { __$originalPosition?: UTSSourceMapPosition<"SelectChangePayload", "uni_modules/lili_bottom-select/components/lili_bottom-select/lili_bottom-select.uvue", 170, 6>;
	value: string
	text: string
	item: UTSJSONObject
}

type MultiSelectChangePayload = { __$originalPosition?: UTSSourceMapPosition<"MultiSelectChangePayload", "uni_modules/lili_bottom-select/components/lili_bottom-select/lili_bottom-select.uvue", 176, 6>;
	values: string[]
	texts: string[]
	items: UTSJSONObject[]
}

type TreeDisplayRow = { __$originalPosition?: UTSSourceMapPosition<"TreeDisplayRow", "uni_modules/lili_bottom-select/components/lili_bottom-select/lili_bottom-select.uvue", 182, 6>;
	key: string
	item: UTSJSONObject
	level: number
	hasChildren: boolean
	expanded: boolean
}

type Props = { __$originalPosition?: UTSSourceMapPosition<"Props", "uni_modules/lili_bottom-select/components/lili_bottom-select/lili_bottom-select.uvue", 190, 6>;
	fetchData: (params: UTSJSONObject) => Promise<UTSJSONObject>

	/**
	 * 单选值
	 */
	value?: string

	/**
	 * 单选显示文本
	 */
	valueText?: string

	/**
	 * 多选值
	 */
	values?: string[]

	/**
	 * 是否多选
	 */
	multiple?: boolean

	/**
	 * 是否启用树结构
	 */
	tree?: boolean

	/**
	 * 树子节点字段名
	 */
	childrenKey?: string

	/**
	 * 是否默认展开所有树节点
	 */
	defaultExpandAll?: boolean

	/**
	 * 点击节点主体时是否优先展开/收起
	 * false：点击节点主体执行选择，点击箭头展开
	 * true：点击有子节点的节点主体执行展开，叶子节点执行选择
	 */
	expandOnClickNode?: boolean

	/**
	 * 多选树是否严格模式
	 * true：父子节点互不影响
	 * false：选择父节点时会级联选择/取消子节点
	 */
	checkStrictly?: boolean

	/**
	 * 是否手风琴展开
	 * true：同级节点只展开一个
	 */
	accordion?: boolean

	placeholder?: string
	title?: string
	searchPlaceholder?: string
	emptyText?: string
	disabled?: boolean
	labelKey?: string
	valueKey?: string
	pageSize?: number
	searchDelay?: number
	closeOnOverlay?: boolean
	showEditAction?: boolean
	showAddAction?: boolean
	editActionText?: string
	addActionText?: string
}


const __sfc__ = defineComponent({
  __name: 'lili_bottom-select',
  props: {
    fetchData: { type: Function as PropType<(params: UTSJSONObject) => Promise<UTSJSONObject>>, required: true },
    value: { type: String, required: false, default: '' },
    valueText: { type: String, required: false, default: '' },
    values: { type: Array as PropType<string[]>, required: false, default: () : string[] => [] },
    multiple: { type: Boolean, required: false, default: false },
    tree: { type: Boolean, required: false, default: false },
    childrenKey: { type: String, required: false, default: 'children' },
    defaultExpandAll: { type: Boolean, required: false, default: false },
    expandOnClickNode: { type: Boolean, required: false, default: false },
    checkStrictly: { type: Boolean, required: false, default: true },
    accordion: { type: Boolean, required: false, default: false },
    placeholder: { type: String, required: false, default: '请选择' },
    title: { type: String, required: false, default: '请选择' },
    searchPlaceholder: { type: String, required: false, default: '请输入关键词搜索' },
    emptyText: { type: String, required: false, default: '暂无数据' },
    disabled: { type: Boolean, required: false, default: false },
    labelKey: { type: String, required: false, default: 'text' },
    valueKey: { type: String, required: false, default: 'value' },
    pageSize: { type: Number, required: false, default: 20 },
    searchDelay: { type: Number, required: false, default: 300 },
    closeOnOverlay: { type: Boolean, required: false, default: true },
    showEditAction: { type: Boolean, required: false, default: true },
    showAddAction: { type: Boolean, required: false, default: true },
    editActionText: { type: String, required: false, default: '编辑' },
    addActionText: { type: String, required: false, default: '新增' }
  },
  emits: ["change", "multiChange", "open", "close", "edit", "add"],
  setup(__props, __setupCtx: SetupContext) {
const __expose = __setupCtx.expose
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

const props = __props

function emit(event: string, ...do_not_transform_spread: Array<any | null>) {
__ins.emit(event, ...do_not_transform_spread)
}

const renderVisible = ref<boolean>(false)
const overlayVisible = ref<boolean>(false)
const panelVisible = ref<boolean>(false)

const internalValue = ref<string>(props.value ?? '')
const internalText = ref<string>(props.valueText ?? '')
const textInitialized = ref<boolean>((props.valueText ?? '') != '')

const selectedItems = ref<UTSJSONObject[]>([])
const keyword = ref<string>('')
const displayList = ref<UTSJSONObject[]>([])
const expandedKeys = ref<string[]>([])
const loadingChildKeys = ref<string[]>([])

const currentPage = ref<number>(1)
const total = ref<number>(0)
const loading = ref<boolean>(false)
const hasMore = ref<boolean>(true)
const listLoaded = ref<boolean>(false)
const panelStyle = ref<string>('')

let searchTimer: number | null = null
let enterTimer: number | null = null
let closeTimer: number | null = null
let requestSeq = 0

const PANEL_ANIMATION_DURATION = 340

function fieldToStr(item: UTSJSONObject, key: string) : string {
	const v = item[key]
	if (v == null) return ''
	return '' + v
}

function fieldLabel(item: UTSJSONObject, key: string) : string {
	const v = item[key]
	if (v == null) return ''
	return '' + v
}

function getItemValue(item: UTSJSONObject) : string {
	return fieldToStr(item, props.valueKey)
}

function getItemLabel(item: UTSJSONObject) : string {
	return fieldLabel(item, props.labelKey)
}

function getNumberField(obj: UTSJSONObject, key: string) : number {
	const value = obj[key]
	if (value == null) return 0
	return value as number
}

function getListField(obj: UTSJSONObject, key: string) : UTSJSONObject[] {
	const value = obj[key]
	if (value == null) return []
	return value as UTSJSONObject[]
}

function getTreeChildren(item: UTSJSONObject) : UTSJSONObject[] {
	const value = item[props.childrenKey]
	if (value == null) return []
	return value as UTSJSONObject[]
}

function getBooleanField(obj: UTSJSONObject, key: string) : boolean {
	const value = obj[key]
	if (value == null) return false
	if (typeof value == 'boolean') return value as boolean
	const text = ('' + value).toLowerCase()
	return text == 'true' || text == '1' || text == 'yes'
}

function cloneItem(item: UTSJSONObject) : UTSJSONObject {
	const next = { __$originalPosition: new UTSSourceMapPosition("next", "uni_modules/lili_bottom-select/components/lili_bottom-select/lili_bottom-select.uvue", 377, 8), } as UTSJSONObject
	for (const key in item) {
		next[key] = item[key]
	}
	return next
}

function itemHasChildren(item: UTSJSONObject) : boolean {
	if (getTreeChildren(item).length > 0) {
		return true
	}
	return getBooleanField(item, 'has_children')
}

function hasTreeChildren(item: UTSJSONObject) : boolean {
	return itemHasChildren(item)
}

function buildFetchParams(page: number, keywordValue: string, id: string, parent: string = '') : UTSJSONObject {
	const params = { __$originalPosition: new UTSSourceMapPosition("params", "uni_modules/lili_bottom-select/components/lili_bottom-select/lili_bottom-select.uvue", 396, 8), 
		page: page,
		pageSize: props.pageSize,
		keyword: keywordValue,
		id: id,
	} as UTSJSONObject
	if (parent != '') {
		params['parent'] = parent
	}
	return params
}

function hasString(list: string[], value: string) : boolean {
	for (let i = 0; i < list.length; i++) {
		if (list[i] == value) {
			return true
		}
	}
	return false
}

function uniqueStringList(list: string[]) : string[] {
	const result: string[] = []
	for (let i = 0; i < list.length; i++) {
		if (!hasString(result, list[i])) {
			result.push(list[i])
		}
	}
	return result
}

function mergeStringList(a: string[], b: string[]) : string[] {
	const result: string[] = []
	for (let i = 0; i < a.length; i++) {
		if (!hasString(result, a[i])) {
			result.push(a[i])
		}
	}
	for (let j = 0; j < b.length; j++) {
		if (!hasString(result, b[j])) {
			result.push(b[j])
		}
	}
	return result
}

function containsItemValue(list: UTSJSONObject[], value: string) : boolean {
	for (let i = 0; i < list.length; i++) {
		if (getItemValue(list[i]) == value) {
			return true
		}
	}
	return false
}

function findSelectedItemByValue(value: string) : UTSJSONObject | null {
	for (let i = 0; i < selectedItems.value.length; i++) {
		const item = selectedItems.value[i]
		if (getItemValue(item) == value) {
			return item
		}
	}
	return null
}

function findItemByValueInList(list: UTSJSONObject[], value: string) : UTSJSONObject | null {
	for (let i = 0; i < list.length; i++) {
		const item = list[i]
		if (getItemValue(item) == value) {
			return item
		}

		const children = getTreeChildren(item)
		if (children.length > 0) {
			const childResult = findItemByValueInList(children, value)
			if (childResult != null) {
				return childResult
			}
		}
	}
	return null
}

function collectDescendantItems(item: UTSJSONObject, out: UTSJSONObject[]) {
	const children = getTreeChildren(item)
	for (let i = 0; i < children.length; i++) {
		const child = children[i]
		out.push(child)
		collectDescendantItems(child, out)
	}
}

function collectSelfAndDescendantItems(item: UTSJSONObject) : UTSJSONObject[] {
	const result: UTSJSONObject[] = []
	result.push(item)
	collectDescendantItems(item, result)
	return result
}

function valuesFromItems(items: UTSJSONObject[]) : string[] {
	const values: string[] = []
	for (let i = 0; i < items.length; i++) {
		values.push(getItemValue(items[i]))
	}
	return values
}

function addSelectedItems(items: UTSJSONObject[]) {
	const next: UTSJSONObject[] = [...selectedItems.value]
	for (let i = 0; i < items.length; i++) {
		const item = items[i]
		const value = getItemValue(item)
		if (!containsItemValue(next, value)) {
			next.push(item)
		}
	}
	selectedItems.value = next
}

function removeSelectedByValues(values: string[]) {
	const next: UTSJSONObject[] = []
	for (let i = 0; i < selectedItems.value.length; i++) {
		const item = selectedItems.value[i]
		const value = getItemValue(item)
		if (!hasString(values, value)) {
			next.push(item)
		}
	}
	selectedItems.value = next
}

function clearAnimationTimers() {
	if (enterTimer != null) {
		const timer = enterTimer
		clearTimeout(timer!)
		enterTimer = null
	}
	if (closeTimer != null) {
		const timer = closeTimer
		clearTimeout(timer!)
		closeTimer = null
	}
}

function activatePanelAnimation() {
	clearAnimationTimers()
	enterTimer = setTimeout(() => {
		overlayVisible.value = true
		panelVisible.value = true
		enterTimer = null
	}, 16)
}

function isExpanded(item: UTSJSONObject) : boolean {
	const value = getItemValue(item)
	return hasString(expandedKeys.value, value)
}

function buildVisibleRows(list: UTSJSONObject[], level: number, parentKey: string, rows: TreeDisplayRow[]) {
	for (let i = 0; i < list.length; i++) {
		const item = list[i]
		const itemVal = getItemValue(item)
		const children = getTreeChildren(item)
		const hasChildren = props.tree && itemHasChildren(item)
		const expanded = hasChildren && isExpanded(item)
		const rowKey = parentKey + '/' + itemVal + '_' + ('' + i)

		rows.push({
			key: rowKey,
			item: item,
			level: level,
			hasChildren: hasChildren,
			expanded: expanded,
		} as TreeDisplayRow)

		if (props.tree && hasChildren && expanded) {
			buildVisibleRows(children, level + 1, rowKey, rows)
		}
	}
}

const visibleRows = computed<TreeDisplayRow[]>(() : TreeDisplayRow[] => {
	const rows: TreeDisplayRow[] = []
	buildVisibleRows(displayList.value, 0, '', rows)
	return rows
})

function getTreeIndentStyle(row: TreeDisplayRow) : string {
	const width = row.level * 18
	return `width:${width}px;`
}

function collectExpandableKeys(list: UTSJSONObject[], out: string[]) {
	for (let i = 0; i < list.length; i++) {
		const item = list[i]
		if (getTreeChildren(item).length > 0) {
			const value = getItemValue(item)
			if (!hasString(out, value)) {
				out.push(value)
			}
			collectExpandableKeys(getTreeChildren(item), out)
		}
	}
}

function applyTreeExpandState(isReset: boolean, newData: UTSJSONObject[]) {
	if (!props.tree) return

	const shouldExpand = props.defaultExpandAll || keyword.value != ''

	if (isReset) {
		if (shouldExpand) {
			const keys: string[] = []
			collectExpandableKeys(displayList.value, keys)
			expandedKeys.value = uniqueStringList(keys)
		} else {
			expandedKeys.value = []
		}
		return
	}

	if (shouldExpand) {
		const keys: string[] = []
		collectExpandableKeys(newData, keys)
		expandedKeys.value = mergeStringList(expandedKeys.value, keys)
	}
}

function findSiblingExpandableKeys(list: UTSJSONObject[], targetValue: string) : string[] | null {
	const siblingKeys: string[] = []

	for (let i = 0; i < list.length; i++) {
		const item = list[i]
		if (itemHasChildren(item)) {
			siblingKeys.push(getItemValue(item))
		}
	}

	for (let j = 0; j < list.length; j++) {
		const item = list[j]
		const value = getItemValue(item)

		if (value == targetValue) {
			return siblingKeys
		}

		const children = getTreeChildren(item)
		if (children.length > 0) {
			const found = findSiblingExpandableKeys(children, targetValue)
			if (found != null) {
				return found
			}
		}
	}

	return null
}

function replaceTreeChildren(list: UTSJSONObject[], targetValue: string, children: UTSJSONObject[]) : UTSJSONObject[] {
	const next: UTSJSONObject[] = []
	for (let index = 0; index < list.length; index += 1) {
		const current = list[index]
		const currentValue = getItemValue(current)
		if (currentValue == targetValue) {
			const cloned = cloneItem(current)
			cloned[props.childrenKey] = children
			cloned['has_children'] = children.length > 0
			next.push(cloned)
			continue
		}

		const currentChildren = getTreeChildren(current)
		if (currentChildren.length > 0) {
			const cloned = cloneItem(current)
			cloned[props.childrenKey] = replaceTreeChildren(currentChildren, targetValue, children)
			next.push(cloned)
			continue
		}

		next.push(current)
	}
	return next
}

function addLoadingChildKey(value: string) {
	if (!hasString(loadingChildKeys.value, value)) {
		loadingChildKeys.value = [...loadingChildKeys.value, value]
	}
}

function removeLoadingChildKey(value: string) {
	const next: string[] = []
	for (let index = 0; index < loadingChildKeys.value.length; index += 1) {
		if (loadingChildKeys.value[index] != value) {
			next.push(loadingChildKeys.value[index])
		}
	}
	loadingChildKeys.value = next
}

async function ensureTreeChildrenLoaded(item: UTSJSONObject) : Promise<void> {
	if (!props.tree) return
	if (!itemHasChildren(item)) return
	if (getTreeChildren(item).length > 0) return

	const parentValue = getItemValue(item)
	if (parentValue == '') return
	if (hasString(loadingChildKeys.value, parentValue)) return

	addLoadingChildKey(parentValue)

	try {
		const res = await props.fetchData(buildFetchParams(1, '', '', parentValue))
		const children = getListField(res, 'data')
		displayList.value = replaceTreeChildren(displayList.value, parentValue, children)
	} catch (e) {
		console.error('lili_bottom-select loadTreeChildren 失败:', e, " at uni_modules/lili_bottom-select/components/lili_bottom-select/lili_bottom-select.uvue:712")
		uni.showToast({ title: '子节点加载失败', icon: 'none' })
	} finally {
		removeLoadingChildKey(parentValue)
	}
}

async function expandItem(item: UTSJSONObject) {
	if (!props.tree) return
	if (!itemHasChildren(item)) return

	const value = getItemValue(item)
	if (value == '') return

	await ensureTreeChildrenLoaded(item)

	if (props.accordion) {
		const siblingKeys = findSiblingExpandableKeys(displayList.value, value)
		if (siblingKeys != null) {
			const next: string[] = []
			for (let i = 0; i < expandedKeys.value.length; i++) {
				const key = expandedKeys.value[i]
				if (!hasString(siblingKeys, key) || key == value) {
					next.push(key)
				}
			}
			expandedKeys.value = next
		}
	}

	if (!hasString(expandedKeys.value, value)) {
		expandedKeys.value = [...expandedKeys.value, value]
	}
}

function collapseItem(item: UTSJSONObject) {
	const value = getItemValue(item)
	const next: string[] = []

	for (let i = 0; i < expandedKeys.value.length; i++) {
		if (expandedKeys.value[i] != value) {
			next.push(expandedKeys.value[i])
		}
	}

	expandedKeys.value = next
}

async function toggleExpand(item: UTSJSONObject) {
	if (!props.tree) return
	if (!itemHasChildren(item)) return

	if (isExpanded(item)) {
		collapseItem(item)
	} else {
		await expandItem(item)
	}
}

function expandAll() {
	if (!props.tree) return
	const keys: string[] = []
	collectExpandableKeys(displayList.value, keys)
	expandedKeys.value = uniqueStringList(keys)
}

function collapseAll() {
	expandedKeys.value = []
}

function setExpandedKeys(keys: string[]) {
	expandedKeys.value = uniqueStringList(keys)
}

function getExpandedKeys() : string[] {
	return expandedKeys.value
}

function expandByValue(value: string) {
	const item = findItemByValueInList(displayList.value, value)
	if (item != null) {
		expandItem(item)
	}
}

function collapseByValue(value: string) {
	const item = findItemByValueInList(displayList.value, value)
	if (item != null) {
		collapseItem(item)
	}
}

function toggleExpandByValue(value: string) {
	const item = findItemByValueInList(displayList.value, value)
	if (item != null) {
		toggleExpand(item)
	}
}

function updateTextFromList() {
	const item = findItemByValueInList(displayList.value, internalValue.value)
	if (item != null) {
		internalText.value = getItemLabel(item)
		textInitialized.value = true
	}
}

function syncFromList(vals: string[]) {
	if (vals.length == 0) {
		selectedItems.value = []
		return
	}

	const next: UTSJSONObject[] = []

	for (let i = 0; i < vals.length; i++) {
		const value = vals[i]
		let item = findSelectedItemByValue(value)

		if (item == null) {
			item = findItemByValueInList(displayList.value, value)
		}

		if (item != null && !containsItemValue(next, value)) {
			next.push(item)
		}
	}

	selectedItems.value = next
}

async function loadData(isReset: boolean) {
	if (loading.value && !isReset) return
	if (!isReset && !hasMore.value) return

	const page = isReset ? 1 : currentPage.value
	const seq = requestSeq + 1
	requestSeq = seq
	loading.value = true

	try {
		const res = await props.fetchData(buildFetchParams(page, keyword.value, ''))

		if (seq != requestSeq) return

		const data = getListField(res, 'data')
		const totalValue = getNumberField(res, 'total')
		total.value = totalValue

		if (isReset) {
			displayList.value = data
			currentPage.value = 2
			listLoaded.value = true
		} else {
			const merged: UTSJSONObject[] = [...displayList.value, ...data]
			displayList.value = merged
			currentPage.value = currentPage.value + 1
		}

		applyTreeExpandState(isReset, data)

		hasMore.value = displayList.value.length < total.value && data.length >= props.pageSize

		if (!props.multiple && internalValue.value != '' && !textInitialized.value) {
			updateTextFromList()
		}

		if (props.multiple) {
			const curVals = selectedItems.value.map<string>((item: UTSJSONObject) : string => getItemValue(item))
			if (curVals.length > 0) {
				syncFromList(curVals)
			} else {
				syncFromList(props.values ?? [])
			}
		}
	} catch (e) {
		if (seq != requestSeq) return
		console.error('lili_bottom-select loadData 失败:', e, " at uni_modules/lili_bottom-select/components/lili_bottom-select/lili_bottom-select.uvue:889")
		uni.showToast({ title: '数据加载失败', icon: 'none' })
	} finally {
		if (seq == requestSeq) {
			loading.value = false
		}
	}
}

async function fetchTextByValue(value: string) {
	if (value == '' || textInitialized.value) return

	try {
		const res = await props.fetchData(buildFetchParams(1, '', value))
		const data = getListField(res, 'data')
		const item = findItemByValueInList(data, value)

		if (item != null) {
			internalText.value = getItemLabel(item)
			textInitialized.value = true
		}
	} catch (e) {
		console.error('lili_bottom-select fetchTextByValue 失败:', e, " at uni_modules/lili_bottom-select/components/lili_bottom-select/lili_bottom-select.uvue:911")
	}
}

function syncMultiValuesFromProps(vals: string[]) {
	if (vals.length == 0) {
		selectedItems.value = []
		return
	}
	syncFromList(vals)
}

const displayText = computed<string>(() : string => {
	if (props.multiple) {
		if (selectedItems.value.length == 0) return ''
		const texts = selectedItems.value.map<string>((item: UTSJSONObject) : string => getItemLabel(item))
		return texts.join('、')
	}
	return internalText.value
})

watch(
	() : string => props.value ?? '',
	(newVal: string) => {
		if (newVal != internalValue.value) {
			internalValue.value = newVal

			if (newVal != '') {
				if (props.valueText != '') {
					internalText.value = props.valueText
					textInitialized.value = true
				} else {
					textInitialized.value = false
					fetchTextByValue(newVal)
				}
			} else {
				internalText.value = ''
				textInitialized.value = false
			}
		}
	}
)

watch(
	() : string => props.valueText ?? '',
	(newText: string) => {
		if (newText != '') {
			internalText.value = newText
			textInitialized.value = true
			return
		}

		if (internalValue.value == '') {
			internalText.value = ''
			textInitialized.value = false
		}
	}
)

watch(
	() : string[] => props.values ?? [],
	(newVals: string[]) => {
		if (props.multiple) {
			syncMultiValuesFromProps(newVals)
		}
	}
)

onMounted(() => {
	const info = uni.getWindowInfo()
	const winH = info.windowHeight
	let panelH = Math.floor(winH * 0.72)

	if (panelH < 420) {
		panelH = 420
	}

	const maxPanelH = winH - 24

	if (panelH > maxPanelH) {
		panelH = maxPanelH
	}

	panelStyle.value = `height:${panelH}px;`

	if (!props.multiple && internalValue.value != '') {
		if (props.valueText != '') {
			internalText.value = props.valueText
			textInitialized.value = true
		} else {
			fetchTextByValue(internalValue.value)
		}
	}

	if (props.multiple && props.values.length > 0) {
		syncMultiValuesFromProps(props.values)
	}
})

async function open() {
	if (props.disabled) return
	if (panelVisible.value || overlayVisible.value) return

	renderVisible.value = true
	overlayVisible.value = false
	panelVisible.value = false
	activatePanelAnimation()

	emit('open')

	if (!listLoaded.value) {
		displayList.value = []
		currentPage.value = 1
		hasMore.value = true
		listLoaded.value = false
		await loadData(true)
	} else {
		hasMore.value = displayList.value.length < total.value
	}

	if (props.multiple) {
		const curVals = selectedItems.value.map<string>((item: UTSJSONObject) : string => getItemValue(item))
		syncFromList(curVals.length > 0 ? curVals : (props.values ?? []))
	}
}

function close() {
	if (!renderVisible.value) return

	clearAnimationTimers()

	overlayVisible.value = false
	panelVisible.value = false

	closeTimer = setTimeout(() => {
		closeTimer = null
		emit('close')
	}, PANEL_ANIMATION_DURATION)
}

function handleOverlayClick() {
	if (props.closeOnOverlay) {
		close()
	}
}

function handleEditAction() {
	if (props.disabled) return
	emit('edit')
}

function handleAddAction() {
	if (props.disabled) return
	emit('add')
}

async function triggerSearch() {
	if (searchTimer != null) {
		clearTimeout(searchTimer!)
		searchTimer = null
	}

	displayList.value = []
	currentPage.value = 1
	hasMore.value = true
	listLoaded.value = false

	await loadData(true)
}

function onSearchInput() {
	if (searchTimer != null) {
		clearTimeout(searchTimer!)
	}

	searchTimer = setTimeout(() => {
		triggerSearch()
	}, props.searchDelay)
}

async function clearSearch() {
	keyword.value = ''
	await triggerSearch()
}

function loadMore() {
	if (hasMore.value && !loading.value) {
		loadData(false)
	}
}

function isItemSelected(item: UTSJSONObject) : boolean {
	const itemVal = getItemValue(item)

	if (props.multiple) {
		for (let i = 0; i < selectedItems.value.length; i++) {
			if (getItemValue(selectedItems.value[i]) == itemVal) {
				return true
			}
		}
		return false
	}

	return internalValue.value == itemVal
}

function isItemHalfSelected(item: UTSJSONObject) : boolean {
	if (!props.multiple) return false
	if (!props.tree) return false
	if (props.checkStrictly) return false
	if (isItemSelected(item)) return false

	const descendants: UTSJSONObject[] = []
	collectDescendantItems(item, descendants)

	if (descendants.length == 0) return false

	let selectedCount = 0

	for (let i = 0; i < descendants.length; i++) {
		if (isItemSelected(descendants[i])) {
			selectedCount = selectedCount + 1
		}
	}

	return selectedCount > 0
}

function getSelectIcon(item: UTSJSONObject) : string {
	if (isItemSelected(item)) return '✓'
	if (isItemHalfSelected(item)) return '−'
	return ''
}

function confirmSingleItem(item: UTSJSONObject) {
	const value = getItemValue(item)
	const text = getItemLabel(item)

	internalValue.value = value
	internalText.value = text
	textInitialized.value = true

	emit('change', { value: value, text: text, item: item } as UTSJSONObject)

	close()
}

function toggleMultiItem(item: UTSJSONObject) {
	if (props.tree && !props.checkStrictly) {
		const allItems = collectSelfAndDescendantItems(item)
		const allValues = valuesFromItems(allItems)

		if (isItemSelected(item)) {
			removeSelectedByValues(allValues)
		} else {
			addSelectedItems(allItems)
		}

		return
	}

	const val = getItemValue(item)
	const idx = selectedItems.value.findIndex((s: UTSJSONObject) : boolean => {
		return getItemValue(s) == val
	})

	if (idx >= 0) {
		const newList: UTSJSONObject[] = []

		for (let i = 0; i < selectedItems.value.length; i++) {
			if (i != idx) {
				newList.push(selectedItems.value[i])
			}
		}

		selectedItems.value = newList
	} else {
		selectedItems.value = [...selectedItems.value, item]
	}
}

function onItemClick(item: UTSJSONObject) {
	if (props.disabled) return

	if (props.multiple) {
		toggleMultiItem(item)
	} else {
		confirmSingleItem(item)
	}
}

function onRowClick(row: TreeDisplayRow) {
	if (props.disabled) return

	if (props.tree && props.expandOnClickNode && row.hasChildren) {
		toggleExpand(row.item)
		return
	}

	onItemClick(row.item)
}

function removeSelectedItem(item: UTSJSONObject) {
	if (props.tree && !props.checkStrictly) {
		const allItems = collectSelfAndDescendantItems(item)
		const allValues = valuesFromItems(allItems)
		removeSelectedByValues(allValues)
		return
	}

	const val = getItemValue(item)
	const newList: UTSJSONObject[] = []

	for (let i = 0; i < selectedItems.value.length; i++) {
		if (getItemValue(selectedItems.value[i]) != val) {
			newList.push(selectedItems.value[i])
		}
	}

	selectedItems.value = newList
}

function clearAllSelected() {
	selectedItems.value = []
}

function confirmMultiple() {
	const values = selectedItems.value.map<string>((item: UTSJSONObject) : string => getItemValue(item))
	const texts = selectedItems.value.map<string>((item: UTSJSONObject) : string => getItemLabel(item))

	emit('multiChange', {
		values: values,
		texts: texts,
		items: selectedItems.value,
	} as UTSJSONObject)

	close()
}

function openPanel() {
	open()
}

function closePanel() {
	close()
}

function setValue(value: string, text: string) {
	internalValue.value = value

	if (text != '') {
		internalText.value = text
		textInitialized.value = true
	} else if (value != '') {
		textInitialized.value = false
		fetchTextByValue(value)
	} else {
		internalText.value = ''
		textInitialized.value = false
	}
}

function setValues(items: UTSJSONObject[]) {
	selectedItems.value = items
}

function getValue() : SelectChangePayload {
	return {
		value: internalValue.value,
		text: internalText.value,
		item: {} as UTSJSONObject,
	} as SelectChangePayload
}

function getValues() : MultiSelectChangePayload {
	const values = selectedItems.value.map<string>((item: UTSJSONObject) : string => getItemValue(item))
	const texts = selectedItems.value.map<string>((item: UTSJSONObject) : string => getItemLabel(item))

	return {
		values: values,
		texts: texts,
		items: selectedItems.value,
	} as MultiSelectChangePayload
}

function clearValue() {
	internalValue.value = ''
	internalText.value = ''
	textInitialized.value = false
}

function clearValues() {
	selectedItems.value = []
}

async function refreshData() {
	displayList.value = []
	currentPage.value = 1
	listLoaded.value = false
	keyword.value = ''
	hasMore.value = true

	await loadData(true)
}

async function preloadList(kw: string) {
	if (kw != '') {
		keyword.value = kw
	}

	displayList.value = []
	currentPage.value = 1
	listLoaded.value = false
	hasMore.value = true

	await loadData(true)
}

function reset() {
	clearValue()
	clearValues()

	displayList.value = []
	expandedKeys.value = []
	loadingChildKeys.value = []
	listLoaded.value = false
	keyword.value = ''
	hasMore.value = true
	currentPage.value = 1
	total.value = 0
}

onUnmounted(() => {
	requestSeq = requestSeq + 1

	clearAnimationTimers()

	if (searchTimer != null) {
		const timer = searchTimer
		clearTimeout(timer!)
		searchTimer = null
	}
})

__expose({
	openPanel,
	closePanel,

	setValue,
	setValues,
	getValue,
	getValues,
	clearValue,
	clearValues,

	refreshData,
	preloadList,
	reset,

	expandAll,
	collapseAll,
	setExpandedKeys,
	getExpandedKeys,
	expandByValue,
	collapseByValue,
	toggleExpandByValue,
})

return (): any | null => {

  return _cE(Fragment, null, [
    _cE("view", _uM({
      class: "bs-trigger-wrapper",
      onClick: open
    }), [
      renderSlot(_ctx.$slots, "trigger", {}, (): any[] => [
        _cE("view", _uM({ class: "bs-trigger-default" }), [
          _cE("text", _uM({
            class: _nC(unref(displayText) != '' ? 'bs-trigger-text' : 'bs-trigger-placeholder')
          }), _tD(unref(displayText) != '' ? unref(displayText) : _ctx.placeholder), 3 /* TEXT, CLASS */),
          _cE("view", _uM({ class: "bs-trigger-actions" }), [
            isTrue(_ctx.showEditAction)
              ? _cE("view", _uM({
                  key: 0,
                  class: "bs-trigger-action",
                  onClick: withModifiers(handleEditAction, ["stop"])
                }), [
                  _cE("text", _uM({ class: "bs-trigger-action-icon" }), "✎")
                ])
              : _cC("v-if", true),
            isTrue(_ctx.showAddAction)
              ? _cE("view", _uM({
                  key: 1,
                  class: "bs-trigger-action",
                  onClick: withModifiers(handleAddAction, ["stop"])
                }), [
                  _cE("text", _uM({ class: "bs-trigger-action-icon" }), "＋")
                ])
              : _cC("v-if", true),
            _cE("view", _uM({ class: "bs-trigger-arrow" }), [
              _cE("text", _uM({ class: "bs-arrow-icon" }), _tD(unref(panelVisible) ? '⌄' : '›'), 1 /* TEXT */)
            ])
          ])
        ])
      ])
    ]),
    isTrue(unref(renderVisible))
      ? _cE("view", _uM({
          key: 0,
          class: _nC(unref(overlayVisible) ? 'bs-overlay bs-overlay-active' : 'bs-overlay'),
          onClick: handleOverlayClick
        }), null, 2 /* CLASS */)
      : _cC("v-if", true),
    isTrue(unref(renderVisible))
      ? _cE("view", _uM({
          key: 1,
          class: _nC(unref(panelVisible) ? 'bs-panel bs-panel-active' : 'bs-panel'),
          style: _nS(unref(panelStyle))
        }), [
          _cE("view", _uM({ class: "bs-handle-wrap" }), [
            _cE("view", _uM({ class: "bs-handle" }))
          ]),
          _cE("view", _uM({ class: "bs-header" }), [
            _cE("text", _uM({ class: "bs-header-title" }), _tD(_ctx.title), 1 /* TEXT */),
            _cE("view", _uM({
              class: "bs-header-close",
              onClick: close
            }), [
              _cE("text", _uM({ class: "bs-close-icon" }), "✕")
            ])
          ]),
          _cE("view", _uM({ class: "bs-search-bar" }), [
            _cE("view", _uM({ class: "bs-search-inner" }), [
              _cE("text", _uM({ class: "bs-search-icon" }), "🔍"),
              _cE("input", _uM({
                class: "bs-search-input",
                modelValue: unref(keyword),
                onInput: [($event: UniInputEvent) => {trySetRefValue(keyword, $event.detail.value)}, onSearchInput] as Array<any | null>,
                placeholder: _ctx.searchPlaceholder,
                disabled: _ctx.disabled,
                onConfirm: triggerSearch,
                "confirm-type": "search"
              }), null, 40 /* PROPS, NEED_HYDRATION */, ["modelValue", "placeholder", "disabled"]),
              unref(keyword) != ''
                ? _cE("view", _uM({
                    key: 0,
                    class: "bs-search-clear",
                    onClick: clearSearch
                  }), [
                    _cE("text", _uM({ class: "bs-clear-icon" }), "✕")
                  ])
                : _cC("v-if", true)
            ])
          ]),
          isTrue(_ctx.multiple && unref(selectedItems).length > 0)
            ? _cE("view", _uM({
                key: 0,
                class: "bs-tags-bar"
              }), [
                _cE("scroll-view", _uM({
                  "scroll-x": "true",
                  style: _nS(_uM({"flex":"1"}))
                }), [
                  _cE("view", _uM({ class: "bs-tags-inner" }), [
                    _cE(Fragment, null, RenderHelpers.renderList(unref(selectedItems), (item, __key, __index, _cached): any => {
                      return _cE("view", _uM({
                        key: fieldToStr(item, _ctx.valueKey),
                        class: "bs-tag"
                      }), [
                        _cE("text", _uM({ class: "bs-tag-text" }), _tD(fieldLabel(item, _ctx.labelKey)), 1 /* TEXT */),
                        _cE("view", _uM({
                          class: "bs-tag-remove",
                          onClick: withModifiers(() => {removeSelectedItem(item)}, ["stop"])
                        }), [
                          _cE("text", _uM({ class: "bs-tag-remove-icon" }), "✕")
                        ], 8 /* PROPS */, ["onClick"])
                      ])
                    }), 128 /* KEYED_FRAGMENT */)
                  ])
                ], 4 /* STYLE */)
              ])
            : _cC("v-if", true),
          _cE("scroll-view", _uM({
            "scroll-y": "true",
            style: _nS(_uM({"flex":"1"})),
            onScrolltolower: loadMore,
            "show-scrollbar": false
          }), [
            isTrue(unref(loading) && unref(visibleRows).length == 0)
              ? _cE("view", _uM({
                  key: 0,
                  class: "bs-state-wrapper"
                }), [
                  _cE("text", _uM({ class: "bs-state-text" }), "加载中...")
                ])
              : isTrue(!unref(loading) && unref(visibleRows).length == 0 && unref(listLoaded))
                ? _cE("view", _uM({
                    key: 1,
                    class: "bs-state-wrapper"
                  }), [
                    _cE("text", _uM({ class: "bs-state-text" }), _tD(_ctx.emptyText), 1 /* TEXT */)
                  ])
                : _cE("view", _uM({ key: 2 }), [
                    _cE(Fragment, null, RenderHelpers.renderList(unref(visibleRows), (row, __key, __index, _cached): any => {
                      return _cE("view", _uM({
                        key: row.key,
                        class: _nC(isItemSelected(row.item) ? 'bs-list-item bs-list-item-selected' : 'bs-list-item'),
                        onClick: () => {onRowClick(row)}
                      }), [
                        _cE("view", _uM({ class: "bs-item-main" }), [
                          isTrue(_ctx.tree)
                            ? _cE("view", _uM({
                                key: 0,
                                class: "bs-tree-prefix"
                              }), [
                                _cE("view", _uM({
                                  class: "bs-tree-indent",
                                  style: _nS(getTreeIndentStyle(row))
                                }), null, 4 /* STYLE */),
                                isTrue(row.hasChildren)
                                  ? _cE("view", _uM({
                                      key: 0,
                                      class: "bs-tree-toggle",
                                      onClick: withModifiers(() => {toggleExpand(row.item)}, ["stop"])
                                    }), [
                                      _cE("text", _uM({ class: "bs-tree-toggle-icon" }), _tD(row.expanded ? '⌄' : '›'), 1 /* TEXT */)
                                    ], 8 /* PROPS */, ["onClick"])
                                  : _cE("view", _uM({
                                      key: 1,
                                      class: "bs-tree-toggle-placeholder"
                                    }))
                              ])
                            : _cC("v-if", true),
                          _cE("text", _uM({
                            class: _nC(isItemSelected(row.item) ? 'bs-item-label bs-item-label-selected' : 'bs-item-label')
                          }), _tD(fieldLabel(row.item, _ctx.labelKey)), 3 /* TEXT, CLASS */)
                        ]),
                        isTrue(isItemSelected(row.item) || isItemHalfSelected(row.item))
                          ? _cE("view", _uM({
                              key: 0,
                              class: "bs-check-icon-wrapper"
                            }), [
                              _cE("text", _uM({ class: "bs-check-icon" }), _tD(getSelectIcon(row.item)), 1 /* TEXT */)
                            ])
                          : _cC("v-if", true)
                      ], 10 /* CLASS, PROPS */, ["onClick"])
                    }), 128 /* KEYED_FRAGMENT */),
                    isTrue(unref(loading) && unref(visibleRows).length > 0)
                      ? _cE("view", _uM({
                          key: 0,
                          class: "bs-load-more"
                        }), [
                          _cE("text", _uM({ class: "bs-load-more-text" }), "加载中...")
                        ])
                      : isTrue(!unref(hasMore) && unref(visibleRows).length > 0)
                        ? _cE("view", _uM({
                            key: 1,
                            class: "bs-load-more"
                          }), [
                            _cE("text", _uM({ class: "bs-load-more-text" }), "没有更多了")
                          ])
                        : _cC("v-if", true)
                  ])
          ], 36 /* STYLE, NEED_HYDRATION */),
          isTrue(_ctx.multiple)
            ? _cE("view", _uM({
                key: 1,
                class: "bs-confirm-bar"
              }), [
                _cE("view", _uM({ class: "bs-confirm-info" }), [
                  unref(selectedItems).length > 0
                    ? _cE("text", _uM({
                        key: 0,
                        class: "bs-confirm-count"
                      }), " 已选 " + _tD(unref(selectedItems).length) + " 项 ", 1 /* TEXT */)
                    : _cC("v-if", true)
                ]),
                _cE("view", _uM({ class: "bs-confirm-btns" }), [
                  _cE("view", _uM({
                    class: "bs-btn-clear",
                    onClick: clearAllSelected
                  }), [
                    _cE("text", _uM({ class: "bs-btn-clear-text" }), "清空")
                  ]),
                  _cE("view", _uM({
                    class: "bs-btn-confirm",
                    onClick: confirmMultiple
                  }), [
                    _cE("text", _uM({ class: "bs-btn-confirm-text" }), "确定")
                  ])
                ])
              ])
            : _cC("v-if", true)
        ], 6 /* CLASS, STYLE */)
      : _cC("v-if", true)
  ], 64 /* STABLE_FRAGMENT */)
}
}

})
export default __sfc__
export type Lili_bottomSelectComponentPublicInstance = InstanceType<typeof __sfc__>;
const GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelectStyles = [_uM([["bs-trigger-wrapper", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"]]))], ["bs-trigger-default", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "space-between"], ["paddingTop", 20], ["paddingBottom", 20], ["borderBottomWidth", 1], ["borderBottomColor", "#E5E5E5"], ["borderBottomStyle", "solid"]]))], ["bs-trigger-text", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["fontSize", 14], ["color", "#333333"]]))], ["bs-trigger-placeholder", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["fontSize", 14], ["color", "#999999"]]))], ["bs-trigger-actions", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"]]))], ["bs-trigger-action", _pS(_uM([["alignItems", "center"], ["justifyContent", "center"], ["width", 28], ["height", 28], ["marginLeft", 8], ["borderTopLeftRadius", 999], ["borderTopRightRadius", 999], ["borderBottomRightRadius", 999], ["borderBottomLeftRadius", 999], ["backgroundColor", "#F5F7FA"]]))], ["bs-trigger-action-icon", _pS(_uM([["fontSize", 14], ["color", "#666666"], ["lineHeight", "14px"]]))], ["bs-trigger-arrow", _pS(_uM([["width", 20], ["height", 20], ["alignItems", "center"], ["justifyContent", "center"], ["marginLeft", 8]]))], ["bs-arrow-icon", _pS(_uM([["fontSize", 20], ["color", "#CCCCCC"], ["lineHeight", "20px"]]))], ["bs-overlay", _pS(_uM([["position", "fixed"], ["top", 0], ["left", 0], ["right", 0], ["bottom", 0], ["backgroundColor", "rgba(0,0,0,0)"], ["zIndex", 998], ["opacity", 0], ["pointerEvents", "none"], ["transitionProperty", "opacity,backgroundColor"], ["transitionDuration", "320ms"], ["transitionTimingFunction", "ease"]]))], ["bs-overlay-active", _pS(_uM([["backgroundColor", "rgba(10,18,30,0.32)"], ["opacity", 1], ["pointerEvents", "auto"]]))], ["bs-panel", _pS(_uM([["position", "fixed"], ["left", 0], ["right", 0], ["bottom", 0], ["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", 22], ["borderTopRightRadius", 22], ["borderBottomRightRadius", 0], ["borderBottomLeftRadius", 0], ["zIndex", 999], ["flexDirection", "column"], ["opacity", 0], ["transform", "translateY(48px)"], ["pointerEvents", "none"], ["boxShadow", "0 -18px 44px rgba(15, 23, 42, 0.14)"], ["transitionProperty", "transform,opacity"], ["transitionDuration", "340ms"], ["transitionTimingFunction", "ease"]]))], ["bs-panel-active", _pS(_uM([["opacity", 1], ["transform", "translateY(0px)"], ["pointerEvents", "auto"]]))], ["bs-handle-wrap", _pS(_uM([["alignItems", "center"], ["paddingTop", 10], ["paddingBottom", 4]]))], ["bs-handle", _pS(_uM([["width", 44], ["height", 5], ["borderTopLeftRadius", 999], ["borderTopRightRadius", 999], ["borderBottomRightRadius", 999], ["borderBottomLeftRadius", 999], ["backgroundColor", "#D9DEE7"]]))], ["bs-header", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "space-between"], ["paddingTop", 10], ["paddingBottom", 16], ["paddingLeft", 16], ["paddingRight", 16], ["borderBottomWidth", 1], ["borderBottomColor", "#F0F0F0"], ["borderBottomStyle", "solid"]]))], ["bs-header-title", _pS(_uM([["fontSize", 16], ["fontWeight", "bold"], ["color", "#333333"]]))], ["bs-header-close", _pS(_uM([["width", 32], ["height", 32], ["alignItems", "center"], ["justifyContent", "center"]]))], ["bs-close-icon", _pS(_uM([["fontSize", 16], ["color", "#999999"]]))], ["bs-search-bar", _pS(_uM([["paddingTop", 12], ["paddingBottom", 12], ["paddingLeft", 16], ["paddingRight", 16], ["borderBottomWidth", 1], ["borderBottomColor", "#F0F0F0"], ["borderBottomStyle", "solid"]]))], ["bs-search-inner", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"], ["backgroundColor", "#F5F5F5"], ["borderTopLeftRadius", 20], ["borderTopRightRadius", 20], ["borderBottomRightRadius", 20], ["borderBottomLeftRadius", 20], ["paddingLeft", 12], ["paddingRight", 12], ["height", 36]]))], ["bs-search-icon", _pS(_uM([["fontSize", 14], ["color", "#999999"], ["marginRight", 6]]))], ["bs-search-input", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["fontSize", 14], ["color", "#333333"], ["height", 36]]))], ["bs-search-clear", _pS(_uM([["width", 20], ["height", 20], ["alignItems", "center"], ["justifyContent", "center"], ["marginLeft", 4]]))], ["bs-clear-icon", _pS(_uM([["fontSize", 12], ["color", "#999999"]]))], ["bs-tags-bar", _pS(_uM([["flexDirection", "row"], ["paddingTop", 8], ["paddingBottom", 8], ["paddingLeft", 16], ["paddingRight", 16], ["borderBottomWidth", 1], ["borderBottomColor", "#F0F0F0"], ["borderBottomStyle", "solid"]]))], ["bs-tags-inner", _pS(_uM([["flexDirection", "row"], ["flexWrap", "nowrap"]]))], ["bs-tag", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"], ["backgroundColor", "#E8F0FE"], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["paddingTop", 4], ["paddingBottom", 4], ["paddingLeft", 10], ["paddingRight", 10], ["marginRight", 8]]))], ["bs-tag-text", _pS(_uM([["fontSize", 12], ["color", "#4A90E2"]]))], ["bs-tag-remove", _pS(_uM([["width", 16], ["height", 16], ["alignItems", "center"], ["justifyContent", "center"], ["marginLeft", 4]]))], ["bs-tag-remove-icon", _pS(_uM([["fontSize", 10], ["color", "#4A90E2"]]))], ["bs-list-item", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "space-between"], ["paddingTop", 14], ["paddingBottom", 14], ["paddingLeft", 16], ["paddingRight", 16], ["borderBottomWidth", 1], ["borderBottomColor", "#F5F5F5"], ["borderBottomStyle", "solid"]]))], ["bs-list-item-selected", _pS(_uM([["backgroundColor", "#F0F5FF"]]))], ["bs-item-main", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["flexDirection", "row"], ["alignItems", "center"], ["marginRight", 12]]))], ["bs-tree-prefix", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"], ["flexShrink", 0]]))], ["bs-tree-indent", _pS(_uM([["height", 1], ["flexShrink", 0]]))], ["bs-tree-toggle", _pS(_uM([["width", 24], ["height", 24], ["alignItems", "center"], ["justifyContent", "center"], ["marginRight", 4], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["backgroundColor", "#F7F8FA"]]))], ["bs-tree-toggle-placeholder", _pS(_uM([["width", 24], ["height", 24], ["marginRight", 4]]))], ["bs-tree-toggle-icon", _pS(_uM([["fontSize", 18], ["color", "#8A8F99"], ["lineHeight", "18px"]]))], ["bs-item-label", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["fontSize", 14], ["color", "#333333"]]))], ["bs-item-label-selected", _pS(_uM([["color", "#4A90E2"]]))], ["bs-check-icon-wrapper", _pS(_uM([["width", 22], ["height", 22], ["alignItems", "center"], ["justifyContent", "center"], ["borderTopLeftRadius", 11], ["borderTopRightRadius", 11], ["borderBottomRightRadius", 11], ["borderBottomLeftRadius", 11], ["backgroundColor", "#E8F0FE"]]))], ["bs-check-icon", _pS(_uM([["fontSize", 15], ["color", "#4A90E2"], ["lineHeight", "15px"]]))], ["bs-state-wrapper", _pS(_uM([["alignItems", "center"], ["justifyContent", "center"], ["paddingTop", 60], ["paddingBottom", 60]]))], ["bs-state-text", _pS(_uM([["fontSize", 14], ["color", "#999999"]]))], ["bs-load-more", _pS(_uM([["alignItems", "center"], ["paddingTop", 16], ["paddingBottom", 16]]))], ["bs-load-more-text", _pS(_uM([["fontSize", 12], ["color", "#BBBBBB"]]))], ["bs-confirm-bar", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "space-between"], ["paddingTop", 12], ["paddingBottom", 12], ["paddingLeft", 16], ["paddingRight", 16], ["borderTopWidth", 1], ["borderTopColor", "#F0F0F0"], ["borderTopStyle", "solid"]]))], ["bs-confirm-info", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"]]))], ["bs-confirm-count", _pS(_uM([["fontSize", 13], ["color", "#666666"]]))], ["bs-confirm-btns", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"]]))], ["bs-btn-clear", _pS(_uM([["paddingTop", 8], ["paddingBottom", 8], ["paddingLeft", 16], ["paddingRight", 16], ["borderTopLeftRadius", 18], ["borderTopRightRadius", 18], ["borderBottomRightRadius", 18], ["borderBottomLeftRadius", 18], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopColor", "#DDDDDD"], ["borderRightColor", "#DDDDDD"], ["borderBottomColor", "#DDDDDD"], ["borderLeftColor", "#DDDDDD"], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["marginRight", 12], ["alignItems", "center"], ["justifyContent", "center"]]))], ["bs-btn-clear-text", _pS(_uM([["fontSize", 14], ["color", "#666666"]]))], ["bs-btn-confirm", _pS(_uM([["paddingTop", 8], ["paddingBottom", 8], ["paddingLeft", 24], ["paddingRight", 24], ["borderTopLeftRadius", 18], ["borderTopRightRadius", 18], ["borderBottomRightRadius", 18], ["borderBottomLeftRadius", 18], ["backgroundColor", "#4A90E2"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["bs-btn-confirm-text", _pS(_uM([["fontSize", 14], ["color", "#FFFFFF"]]))], ["@TRANSITION", _uM([["bs-overlay", _uM([["property", "opacity,backgroundColor"], ["duration", "320ms"], ["timingFunction", "ease"]])], ["bs-panel", _uM([["property", "transform,opacity"], ["duration", "340ms"], ["timingFunction", "ease"]])]])]])]
