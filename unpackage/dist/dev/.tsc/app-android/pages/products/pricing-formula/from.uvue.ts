import _easycom_lili_universal_filter from '@/uni_modules/lili-universal-filter/components/lili-universal-filter/lili-universal-filter.uvue'
import { computed } from 'vue'
import { takeLatestResponseMessage } from '@/pkg/api/index.uts'
import {
	ProductPricingFormulaItem,
	ProductPricingFormulaMutationData,
	createProductPricingFormula,
	getProductPricingFormulaDetail,
	updateProductPricingFormula,
} from '@/pkg/api/modules/products.uts'
import { showErrorToast } from '@/pkg/util/toast.uts'

type FormulaChoice = { __$originalPosition?: UTSSourceMapPosition<"FormulaChoice", "pages/products/pricing-formula/from.uvue", 169, 6>;
	value: string
	label: string
}

type FormulaStep = { __$originalPosition?: UTSSourceMapPosition<"FormulaStep", "pages/products/pricing-formula/from.uvue", 174, 6>;
	kind: string
	operator: string
	operand: string
	mode: string
	parameter: string
}

type ParsedFormula = { __$originalPosition?: UTSSourceMapPosition<"ParsedFormula", "pages/products/pricing-formula/from.uvue", 182, 6>;
	success: boolean
	base_variable: string
	steps: FormulaStep[]
}


const __sfc__ = defineComponent({
  __name: 'from',
  setup(__props) {
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

const formulaId = ref('')
const formMode = ref('create')
const loading = ref(false)
const submitting = ref(false)
const errorMessage = ref('')
const parseWarning = ref('')
const rawExpression = ref('')
const parseFailed = ref(false)

const name = ref('')
const code = ref('')
const description = ref('')
const isActive = ref(true)
const baseVariable = ref('VALUE')
const steps = ref<FormulaStep[]>([])
const testValue = ref('9.90')

const sourceChoices = ref<FormulaChoice[]>([
	{ value: 'VALUE', label: '不固定数据源' } as FormulaChoice,
	{ value: 'COST_PRICE', label: '成本价' } as FormulaChoice,
	{ value: 'PURCHASE_PRICE', label: '含税采购价' } as FormulaChoice,
	{ value: 'NET_PURCHASE_PRICE', label: '不含税采购价' } as FormulaChoice,
	{ value: 'BASE_SALES_PRICE', label: '基础销售价' } as FormulaChoice,
])

const roundModeChoices = ref<FormulaChoice[]>([
	{ value: 'round_digits', label: '四舍五入' } as FormulaChoice,
	{ value: 'ceil_step', label: '按步长向上取整' } as FormulaChoice,
	{ value: 'floor_step', label: '按步长向下取整' } as FormulaChoice,
	{ value: 'ceil_tail_09', label: '向上取到尾数 0.9' } as FormulaChoice,
	{ value: 'floor_tail_09', label: '向下取到尾数 0.9' } as FormulaChoice,
])

function stringValue(value: any | null, fallback: string = ''): string {
	if (value == null) return fallback
	const text = '' + value
	return text == '' ? fallback : text
}

function readEventValue(event: any): string {
	if (event == null) return ''
	const inputEvent = event as UniInputEvent
	const detail = inputEvent.detail
	if (detail == null) return ''
	return detail.value
}

function isDigitChar(ch: string): boolean {
	return ch >= '0' && ch <= '9'
}

function isValidDecimal(value: string): boolean {
	const text = value.trim()
	if (text == '') return false
	let start = 0
	if (text.substring(0, 1) == '-') {
		start = 1
	}
	if (start >= text.length) return false
	let dotCount = 0
	let digitCount = 0
	for (let i = start; i < text.length; i += 1) {
		const ch = text.substring(i, i + 1)
		if (ch == '.') {
			dotCount += 1
			if (dotCount > 1) return false
			continue
		}
		if (!isDigitChar(ch)) return false
		digitCount += 1
	}
	return digitCount > 0
}

function parseDecimal(value: string): number {
	if (!isValidDecimal(value)) return 0
	return parseFloat(value.trim())
}

function roundHalfUp(value: number, digits: number): number {
	const factor = Math.pow(10, digits)
	if (value >= 0) {
		return Math.round(value * factor) / factor
	}
	return -Math.round(Math.abs(value) * factor) / factor
}

function normalizeNumber(value: number): number {
	return Math.round(value * 1000000000) / 1000000000
}

function ceilToStep(value: number, step: number): number {
	if (step <= 0) return value
	return normalizeNumber(Math.ceil(value / step) * step)
}

function floorToStep(value: number, step: number): number {
	if (step <= 0) return value
	return normalizeNumber(Math.floor(value / step) * step)
}

function ceilTo09(value: number): number {
	const integer = Math.floor(value)
	const candidate = integer + 0.9
	if (value <= candidate) return normalizeNumber(candidate)
	return normalizeNumber(integer + 1.9)
}

function floorTo09(value: number): number {
	const integer = Math.floor(value)
	const candidate = integer + 0.9
	if (value >= candidate) return normalizeNumber(candidate)
	return normalizeNumber(integer - 0.1)
}

function cloneSteps(source: FormulaStep[]): FormulaStep[] {
	const result: FormulaStep[] = []
	for (let i = 0; i < source.length; i += 1) {
		const item = source[i]
		result.push({
			kind: item.kind,
			operator: item.operator,
			operand: item.operand,
			mode: item.mode,
			parameter: item.parameter,
		} as FormulaStep)
	}
	return result
}

function sourceLabel(value: string): string {
	for (let index = 0; index < sourceChoices.value.length; index += 1) {
		const item = sourceChoices.value[index]
		if (item.value == value) return item.label
	}
	return '不固定数据源'
}

function normalizeFormulaSource(value: string): string {
	const text = value.trim().toUpperCase()
	for (let index = 0; index < sourceChoices.value.length; index += 1) {
		const item = sourceChoices.value[index]
		if (item.value == text) return item.value
	}
	return 'VALUE'
}

function operatorLabel(operator: string): string {
	if (operator == '*') return '乘以'
	if (operator == '/') return '除以'
	if (operator == '+') return '加'
	if (operator == '-') return '减'
	return '运算'
}

function roundModeLabel(mode: string): string {
	if (mode == 'round_digits') return '四舍五入'
	if (mode == 'ceil_step') return '按步长向上取整'
	if (mode == 'floor_step') return '按步长向下取整'
	if (mode == 'ceil_tail_09') return '向上取到尾数 0.9'
	if (mode == 'floor_tail_09') return '向下取到尾数 0.9'
	return '取整'
}

function stepTitle(step: FormulaStep): string {
	if (step.kind == 'binary') {
		if (step.operator == '*') return '乘法'
		if (step.operator == '/') return '除法'
		if (step.operator == '+') return '加法'
		if (step.operator == '-') return '减法'
		return '运算'
	}
	return roundModeLabel(step.mode)
}

function requiresRoundParameter(mode: string): boolean {
	return mode == 'round_digits' || mode == 'ceil_step' || mode == 'floor_step'
}

function roundParameterPlaceholder(mode: string): string {
	if (mode == 'round_digits') return '保留位数，例如 2'
	return '步长，例如 0.5、1、10'
}

function describeStep(step: FormulaStep): string {
	if (step.kind == 'binary') {
		return operatorLabel(step.operator) + ' ' + step.operand
	}
	if (step.mode == 'round_digits') return '四舍五入到 ' + step.parameter + ' 位小数'
	if (step.mode == 'ceil_step') return '向上取到步长 ' + step.parameter
	if (step.mode == 'floor_step') return '向下取到步长 ' + step.parameter
	if (step.mode == 'ceil_tail_09') return '向上取到尾数 0.9'
	if (step.mode == 'floor_tail_09') return '向下取到尾数 0.9'
	return '未完成步骤'
}

function buildExpression(baseValue: string, sourceSteps: FormulaStep[]): string {
	if (sourceSteps.length == 0) return ''
	let expression = normalizeFormulaSource(baseValue)
	for (let index = 0; index < sourceSteps.length; index += 1) {
		const step = sourceSteps[index]
		if (step.kind == 'binary') {
			if (!isValidDecimal(step.operand)) return ''
			expression = '(' + expression + ' ' + step.operator + ' ' + step.operand.trim() + ')'
			continue
		}
		if (step.kind == 'round') {
			if (step.mode == 'round_digits') {
				if (!isValidDecimal(step.parameter)) return ''
				expression = 'ROUND(' + expression + ', ' + step.parameter.trim() + ')'
			} else if (step.mode == 'ceil_step') {
				if (!isValidDecimal(step.parameter)) return ''
				expression = 'CEILING(' + expression + ', ' + step.parameter.trim() + ')'
			} else if (step.mode == 'floor_step') {
				if (!isValidDecimal(step.parameter)) return ''
				expression = 'FLOOR(' + expression + ', ' + step.parameter.trim() + ')'
			} else if (step.mode == 'ceil_tail_09') {
				expression = 'CEIL_TO_09(' + expression + ')'
			} else if (step.mode == 'floor_tail_09') {
				expression = 'FLOOR_TO_09(' + expression + ')'
			} else {
				return ''
			}
		}
	}
	return expression
}

function buildDisplayExpression(baseValue: string, sourceSteps: FormulaStep[]): string {
	if (sourceSteps.length == 0) return ''
	let expression = sourceLabel(normalizeFormulaSource(baseValue))
	for (let index = 0; index < sourceSteps.length; index += 1) {
		const step = sourceSteps[index]
		if (step.kind == 'binary') {
			if (!isValidDecimal(step.operand)) return ''
			expression = '(' + expression + ' ' + operatorLabel(step.operator) + ' ' + step.operand.trim() + ')'
			continue
		}
		if (step.kind == 'round') {
			if (step.mode == 'round_digits') {
				if (!isValidDecimal(step.parameter)) return ''
				expression = '四舍五入(' + expression + '，保留 ' + step.parameter.trim() + ' 位)'
			} else if (step.mode == 'ceil_step') {
				if (!isValidDecimal(step.parameter)) return ''
				expression = '向上取到步长(' + expression + '，' + step.parameter.trim() + ')'
			} else if (step.mode == 'floor_step') {
				if (!isValidDecimal(step.parameter)) return ''
				expression = '向下取到步长(' + expression + '，' + step.parameter.trim() + ')'
			} else if (step.mode == 'ceil_tail_09') {
				expression = '向上取到尾数 0.9(' + expression + ')'
			} else if (step.mode == 'floor_tail_09') {
				expression = '向下取到尾数 0.9(' + expression + ')'
			} else {
				return ''
			}
		}
	}
	return expression
}

function evaluateSteps(baseText: string, sourceSteps: FormulaStep[]): string {
	if (!isValidDecimal(baseText)) return ''
	if (sourceSteps.length == 0) return ''
	let current = parseDecimal(baseText)
	for (let index = 0; index < sourceSteps.length; index += 1) {
		const step = sourceSteps[index]
		if (step.kind == 'binary') {
			if (!isValidDecimal(step.operand)) return ''
			const operand = parseDecimal(step.operand)
			if (step.operator == '*') {
				current = normalizeNumber(current * operand)
			} else if (step.operator == '/') {
				if (operand == 0) return ''
				current = normalizeNumber(current / operand)
			} else if (step.operator == '+') {
				current = normalizeNumber(current + operand)
			} else if (step.operator == '-') {
				current = normalizeNumber(current - operand)
			} else {
				return ''
			}
			continue
		}
		if (step.kind == 'round') {
			if (step.mode == 'round_digits') {
				if (!isValidDecimal(step.parameter)) return ''
				current = roundHalfUp(current, parseInt(step.parameter))
			} else if (step.mode == 'ceil_step') {
				if (!isValidDecimal(step.parameter)) return ''
				current = ceilToStep(current, parseDecimal(step.parameter))
			} else if (step.mode == 'floor_step') {
				if (!isValidDecimal(step.parameter)) return ''
				current = floorToStep(current, parseDecimal(step.parameter))
			} else if (step.mode == 'ceil_tail_09') {
				current = ceilTo09(current)
			} else if (step.mode == 'floor_tail_09') {
				current = floorTo09(current)
			} else {
				return ''
			}
		}
	}
	return '' + roundHalfUp(current, 2)
}

function isWrappedByOuterPair(text: string): boolean {
	if (text.length < 2) return false
	if (text.substring(0, 1) != '(' || text.substring(text.length - 1, text.length) != ')') return false
	let depth = 0
	for (let index = 0; index < text.length; index += 1) {
		const ch = text.substring(index, index + 1)
		if (ch == '(') depth += 1
		if (ch == ')') depth -= 1
		if (depth == 0 && index < text.length - 1) return false
	}
	return depth == 0
}

function trimOuterPair(value: string): string {
	let text = value.trim()
	while (isWrappedByOuterPair(text)) {
		text = text.substring(1, text.length - 1).trim()
	}
	return text
}

function functionBody(text: string, nameValue: string): string | null {
	const prefix = nameValue + '('
	if (!text.startsWith(prefix)) return null
	if (text.substring(text.length - 1, text.length) != ')') return null
	return text.substring(prefix.length, text.length - 1)
}

function splitTopLevelComma(text: string): string[] {
	const result: string[] = []
	let depth = 0
	let start = 0
	for (let index = 0; index < text.length; index += 1) {
		const ch = text.substring(index, index + 1)
		if (ch == '(') depth += 1
		if (ch == ')') depth -= 1
		if (ch == ',' && depth == 0) {
			result.push(text.substring(start, index).trim())
			start = index + 1
		}
	}
	result.push(text.substring(start).trim())
	return result
}

function findTopLevelBinaryOperator(text: string): number {
	let depth = 0
	for (let index = text.length - 1; index >= 0; index -= 1) {
		const ch = text.substring(index, index + 1)
		if (ch == ')') depth += 1
		if (ch == '(') depth -= 1
		if (depth == 0 && (ch == '+' || ch == '-' || ch == '*' || ch == '/')) {
			if (index > 0 && index < text.length - 1) {
				const before = text.substring(index - 1, index)
				const after = text.substring(index + 1, index + 2)
				if (before == ' ' && after == ' ') {
					return index
				}
			}
		}
	}
	return -1
}

function parseExpressionNode(value: string, outSteps: FormulaStep[]): string {
	const text = trimOuterPair(value)
	let body = functionBody(text, 'CEIL_TO_09')
	if (body != null) {
		const base = parseExpressionNode(body!, outSteps)
		outSteps.push({ kind: 'round', operator: '', operand: '', mode: 'ceil_tail_09', parameter: '' } as FormulaStep)
		return base
	}
	body = functionBody(text, 'FLOOR_TO_09')
	if (body != null) {
		const base = parseExpressionNode(body!, outSteps)
		outSteps.push({ kind: 'round', operator: '', operand: '', mode: 'floor_tail_09', parameter: '' } as FormulaStep)
		return base
	}
	body = functionBody(text, 'ROUND')
	if (body != null) {
		const args = splitTopLevelComma(body!)
		const base = parseExpressionNode(args[0], outSteps)
		const parameter = args.length > 1 ? args[1] : '2'
		outSteps.push({ kind: 'round', operator: '', operand: '', mode: 'round_digits', parameter: parameter } as FormulaStep)
		return base
	}
	body = functionBody(text, 'CEILING')
	if (body != null) {
		const args = splitTopLevelComma(body!)
		const base = parseExpressionNode(args[0], outSteps)
		const parameter = args.length > 1 ? args[1] : '1'
		outSteps.push({ kind: 'round', operator: '', operand: '', mode: 'ceil_step', parameter: parameter } as FormulaStep)
		return base
	}
	body = functionBody(text, 'FLOOR')
	if (body != null) {
		const args = splitTopLevelComma(body!)
		const base = parseExpressionNode(args[0], outSteps)
		const parameter = args.length > 1 ? args[1] : '1'
		outSteps.push({ kind: 'round', operator: '', operand: '', mode: 'floor_step', parameter: parameter } as FormulaStep)
		return base
	}
	const opIndex = findTopLevelBinaryOperator(text)
	if (opIndex >= 0) {
		const left = text.substring(0, opIndex).trim()
		const operator = text.substring(opIndex, opIndex + 1)
		const right = text.substring(opIndex + 1).trim()
		const base = parseExpressionNode(left, outSteps)
		outSteps.push({ kind: 'binary', operator: operator, operand: right, mode: '', parameter: '' } as FormulaStep)
		return base
	}
	return normalizeFormulaSource(text)
}

function parseExpressionToBuilder(expression: string): ParsedFormula {
	const parsedSteps: FormulaStep[] = []
	try {
		const base = parseExpressionNode(expression.trim(), parsedSteps)
		return {
			success: true,
			base_variable: normalizeFormulaSource(base),
			steps: parsedSteps,
		} as ParsedFormula
	} catch (error) {
		return {
			success: false,
			base_variable: 'VALUE',
			steps: [] as FormulaStep[],
		} as ParsedFormula
	}
}

function applyFormula(item: ProductPricingFormulaItem) {
	name.value = item.name
	code.value = item.code
	description.value = item.description
	isActive.value = item.is_active
	rawExpression.value = item.expression
	const parsed = parseExpressionToBuilder(item.expression)
	if (parsed.success && parsed.steps.length > 0) {
		baseVariable.value = parsed.base_variable
		steps.value = parsed.steps
		parseFailed.value = false
		parseWarning.value = ''
		return
	}
	baseVariable.value = 'VALUE'
	steps.value = [] as FormulaStep[]
	parseFailed.value = item.expression != ''
	parseWarning.value = item.expression == '' ? '' : '当前表达式无法还原为步骤构建器。保存基础信息时会保留原表达式；添加步骤后将使用新表达式覆盖。'
}

function parseErrorMessage(error: any, fallback: string): string {
	let message = fallback
	if (error != null) {
	}
	return message
}

async function loadFormula() {
	if (formulaId.value == '') {
		return
	}
	loading.value = true
	errorMessage.value = ''
	try {
		const detail = await getProductPricingFormulaDetail(formulaId.value)
		applyFormula(detail)
	} catch (error) {
		errorMessage.value = parseErrorMessage(error, '价格公式加载失败')
	} finally {
		loading.value = false
	}
}

function handleNameInput(event: any) {
	name.value = readEventValue(event)
}

function handleCodeInput(event: any) {
	code.value = readEventValue(event)
}

function handleDescriptionInput(event: any) {
	description.value = readEventValue(event)
}

function handleTestValueInput(event: any) {
	testValue.value = readEventValue(event)
}

function handleActiveChange(event: any) {
	const switchEvent = event as UniSwitchChangeEvent
	const detail = switchEvent.detail
	if (detail == null) return
	isActive.value = detail.value
}

function selectBaseVariable(value: string) {
	baseVariable.value = value
}

function addBinaryStep(operator: string) {
	parseFailed.value = false
	parseWarning.value = ''
	const next = cloneSteps(steps.value)
	next.push({ kind: 'binary', operator: operator, operand: operator == '*' || operator == '/' ? '1' : '0', mode: '', parameter: '' } as FormulaStep)
	steps.value = next
}

function addRoundStep(mode: string) {
	parseFailed.value = false
	parseWarning.value = ''
	let parameter = ''
	if (mode == 'round_digits') parameter = '2'
	if (mode == 'ceil_step' || mode == 'floor_step') parameter = '0.5'
	const next = cloneSteps(steps.value)
	next.push({ kind: 'round', operator: '', operand: '', mode: mode, parameter: parameter } as FormulaStep)
	steps.value = next
}

function replaceStep(index: number, nextStep: FormulaStep) {
	const next = cloneSteps(steps.value)
	if (index < 0 || index >= next.length) return
	next[index] = nextStep
	steps.value = next
}

function setStepOperator(index: number, operator: string) {
	if (index < 0 || index >= steps.value.length) return
	const current = steps.value[index]
	replaceStep(index, { kind: current.kind, operator: operator, operand: current.operand, mode: current.mode, parameter: current.parameter } as FormulaStep)
}

function setStepRoundMode(index: number, mode: string) {
	if (index < 0 || index >= steps.value.length) return
	const current = steps.value[index]
	let parameter = current.parameter
	if (mode == 'round_digits' && parameter == '') parameter = '2'
	if ((mode == 'ceil_step' || mode == 'floor_step') && parameter == '') parameter = '0.5'
	if (!requiresRoundParameter(mode)) parameter = ''
	replaceStep(index, { kind: current.kind, operator: current.operator, operand: current.operand, mode: mode, parameter: parameter } as FormulaStep)
}

function handleStepOperandInput(index: number, event: any) {
	if (index < 0 || index >= steps.value.length) return
	const current = steps.value[index]
	replaceStep(index, { kind: current.kind, operator: current.operator, operand: readEventValue(event), mode: current.mode, parameter: current.parameter } as FormulaStep)
}

function handleStepParameterInput(index: number, event: any) {
	if (index < 0 || index >= steps.value.length) return
	const current = steps.value[index]
	replaceStep(index, { kind: current.kind, operator: current.operator, operand: current.operand, mode: current.mode, parameter: readEventValue(event) } as FormulaStep)
}

function removeStep(index: number) {
	const next: FormulaStep[] = []
	for (let i = 0; i < steps.value.length; i += 1) {
		if (i != index) next.push(steps.value[i])
	}
	steps.value = next
}

function moveStepUp(index: number) {
	if (index <= 0 || index >= steps.value.length) return
	const next = cloneSteps(steps.value)
	const current = next[index]
	next[index] = next[index - 1]
	next[index - 1] = current
	steps.value = next
}

function moveStepDown(index: number) {
	if (index < 0 || index >= steps.value.length - 1) return
	const next = cloneSteps(steps.value)
	const current = next[index]
	next[index] = next[index + 1]
	next[index + 1] = current
	steps.value = next
}

function resolveSaveExpression(): string {
	const generated = buildExpression(baseVariable.value, steps.value)
	if (generated != '') return generated
	if (parseFailed.value && steps.value.length == 0) return rawExpression.value
	return ''
}

const pricingFormulaListRefreshStorageKey = 'refresh:pages:products:pricing-formula:index'

function markPricingFormulaListRefreshNeeded() {
	uni.setStorageSync(pricingFormulaListRefreshStorageKey, '1')
}

function goBackToList() {
	uni.navigateBack({
		delta: 1,
		fail: () => {
			uni.navigateTo({ url: '/pages/products/pricing-formula/index' })
		},
	})
}

async function handleSave() {
	if (submitting.value) return
	const nameText = name.value.trim()
	const codeText = code.value.trim()
	if (nameText == '') {
		uni.showToast({ title: '公式名称不能为空', icon: 'none', duration: 3500 })
		return
	}
	if (codeText == '') {
		uni.showToast({ title: '公式编码不能为空', icon: 'none', duration: 3500 })
		return
	}
	const expression = resolveSaveExpression()
	if (expression == '') {
		uni.showToast({ title: '请完善公式步骤', icon: 'none', duration: 3500 })
		return
	}

	submitting.value = true
	try {
		const payload = {
			name: nameText,
			code: codeText,
			expression: expression,
			description: description.value.trim(),
			is_active: isActive.value,
		} as ProductPricingFormulaMutationData
		let successText = '价格公式创建成功'
		if (formMode.value == 'edit') {
			await updateProductPricingFormula(formulaId.value, payload)
			successText = '价格公式保存成功'
		} else {
			const created = await createProductPricingFormula(payload)
			formulaId.value = created.id.toString()
			formMode.value = 'edit'
		}
		const message = takeLatestResponseMessage(successText)
		uni.showToast({ title: message, icon: 'success' })
		rawExpression.value = expression
		parseFailed.value = false
		parseWarning.value = ''
		markPricingFormulaListRefreshNeeded()
		goBackToList()
	} catch (error) {
		showErrorToast(parseErrorMessage(error, '价格公式保存失败'))
	} finally {
		submitting.value = false
	}
}

function handleCancel() {
	goBackToList()
}

const expressionText = computed((): string => {
	const generated = buildExpression(baseVariable.value, steps.value)
	if (generated != '') return generated
	if (parseFailed.value) return rawExpression.value
	return ''
})

const displayExpressionText = computed((): string => {
	return buildDisplayExpression(baseVariable.value, steps.value)
})

const previewResult = computed((): string => {
	return evaluateSteps(testValue.value, steps.value)
})

const pageTitle = computed((): string => {
	return formMode.value == 'edit' ? '编辑价格公式' : '新建价格公式'
})

onLoad((event: OnLoadOptions) => {
	const idValue = event['id']
	formulaId.value = idValue == null ? '' : stringValue(idValue)
	formMode.value = formulaId.value == '' ? 'create' : 'edit'
	if (formMode.value == 'edit') {
		loadFormula()
	}
})

return (): any | null => {

const _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter",_easycom_lili_universal_filter)
const _component_switch = resolveComponent("switch")

  return _cE("view", _uM({ class: "page" }), [
    _cV(_component_lili_universal_filter, _uM({
      title: pageTitle.value,
      showBack: true,
      showSearch: false,
      showHome: true,
      homePath: "/pages/products/pricing-formula/index",
      backgroundColor: "#EEF2F7"
    }), null, 8 /* PROPS */, ["title"]),
    _cE("scroll-view", _uM({
      class: "page-scroll",
      "scroll-y": "true"
    }), [
      _cE("view", _uM({ class: "page-content" }), [
        isTrue(unref(loading))
          ? _cE("view", _uM({
              key: 0,
              class: "state-card"
            }), [
              _cE("text", _uM({ class: "state-text" }), "正在加载价格公式...")
            ])
          : unref(errorMessage) != ''
            ? _cE("view", _uM({
                key: 1,
                class: "state-card"
              }), [
                _cE("text", _uM({ class: "state-title" }), "加载失败"),
                _cE("text", _uM({ class: "state-text" }), _tD(unref(errorMessage)), 1 /* TEXT */),
                _cE("view", _uM({
                  class: "small-btn",
                  onClick: loadFormula
                }), [
                  _cE("text", _uM({ class: "small-btn-text" }), "重新加载")
                ])
              ])
            : _cE("view", _uM({ key: 2 }), [
                _cE("view", _uM({ class: "section" }), [
                  _cE("text", _uM({ class: "section-title" }), "基础信息"),
                  _cE("view", _uM({ class: "field" }), [
                    _cE("text", _uM({ class: "field-label" }), "公式名称"),
                    _cE("input", _uM({
                      class: "input",
                      value: unref(name),
                      placeholder: "请输入公式名称",
                      onInput: handleNameInput
                    }), null, 40 /* PROPS, NEED_HYDRATION */, ["value"])
                  ]),
                  _cE("view", _uM({ class: "field" }), [
                    _cE("text", _uM({ class: "field-label" }), "公式编码"),
                    _cE("input", _uM({
                      class: "input",
                      value: unref(code),
                      placeholder: "请输入公式编码",
                      onInput: handleCodeInput
                    }), null, 40 /* PROPS, NEED_HYDRATION */, ["value"])
                  ]),
                  _cE("view", _uM({ class: "field" }), [
                    _cE("text", _uM({ class: "field-label" }), "说明"),
                    _cE("textarea", _uM({
                      class: "textarea",
                      value: unref(description),
                      placeholder: "请输入说明",
                      onInput: handleDescriptionInput
                    }), null, 40 /* PROPS, NEED_HYDRATION */, ["value"])
                  ]),
                  _cE("view", _uM({ class: "switch-row" }), [
                    _cE("text", _uM({ class: "field-label" }), "启用状态"),
                    _cV(_component_switch, _uM({
                      checked: unref(isActive),
                      onChange: handleActiveChange
                    }), null, 8 /* PROPS */, ["checked"])
                  ])
                ]),
                _cE("view", _uM({ class: "section" }), [
                  _cE("text", _uM({ class: "section-title" }), "第一步：数据源"),
                  _cE("view", _uM({ class: "choice-list" }), [
                    _cE(Fragment, null, RenderHelpers.renderList(unref(sourceChoices), (item, __key, __index, _cached): any => {
                      return _cE("view", _uM({
                        key: item.value,
                        class: _nC(unref(baseVariable) == item.value ? 'choice choice-active' : 'choice'),
                        onClick: () => {selectBaseVariable(item.value)}
                      }), [
                        _cE("text", _uM({
                          class: _nC(unref(baseVariable) == item.value ? 'choice-text choice-text-active' : 'choice-text')
                        }), _tD(item.label), 3 /* TEXT, CLASS */)
                      ], 10 /* CLASS, PROPS */, ["onClick"])
                    }), 128 /* KEYED_FRAGMENT */)
                  ])
                ]),
                _cE("view", _uM({ class: "section" }), [
                  _cE("text", _uM({ class: "section-title" }), "第二步：公式步骤"),
                  _cE("view", _uM({ class: "tool-list" }), [
                    _cE("view", _uM({
                      class: "tool-btn",
                      onClick: () => {addBinaryStep('*')}
                    }), [
                      _cE("text", _uM({ class: "tool-btn-text" }), "乘法")
                    ], 8 /* PROPS */, ["onClick"]),
                    _cE("view", _uM({
                      class: "tool-btn",
                      onClick: () => {addBinaryStep('/')}
                    }), [
                      _cE("text", _uM({ class: "tool-btn-text" }), "除法")
                    ], 8 /* PROPS */, ["onClick"]),
                    _cE("view", _uM({
                      class: "tool-btn",
                      onClick: () => {addBinaryStep('+')}
                    }), [
                      _cE("text", _uM({ class: "tool-btn-text" }), "加法")
                    ], 8 /* PROPS */, ["onClick"]),
                    _cE("view", _uM({
                      class: "tool-btn",
                      onClick: () => {addBinaryStep('-')}
                    }), [
                      _cE("text", _uM({ class: "tool-btn-text" }), "减法")
                    ], 8 /* PROPS */, ["onClick"]),
                    _cE("view", _uM({
                      class: "tool-btn",
                      onClick: () => {addRoundStep('round_digits')}
                    }), [
                      _cE("text", _uM({ class: "tool-btn-text" }), "四舍五入")
                    ], 8 /* PROPS */, ["onClick"]),
                    _cE("view", _uM({
                      class: "tool-btn",
                      onClick: () => {addRoundStep('ceil_step')}
                    }), [
                      _cE("text", _uM({ class: "tool-btn-text" }), "步长上取")
                    ], 8 /* PROPS */, ["onClick"]),
                    _cE("view", _uM({
                      class: "tool-btn",
                      onClick: () => {addRoundStep('floor_step')}
                    }), [
                      _cE("text", _uM({ class: "tool-btn-text" }), "步长下取")
                    ], 8 /* PROPS */, ["onClick"]),
                    _cE("view", _uM({
                      class: "tool-btn",
                      onClick: () => {addRoundStep('ceil_tail_09')}
                    }), [
                      _cE("text", _uM({ class: "tool-btn-text" }), "尾数0.9上取")
                    ], 8 /* PROPS */, ["onClick"]),
                    _cE("view", _uM({
                      class: "tool-btn",
                      onClick: () => {addRoundStep('floor_tail_09')}
                    }), [
                      _cE("text", _uM({ class: "tool-btn-text" }), "尾数0.9下取")
                    ], 8 /* PROPS */, ["onClick"])
                  ]),
                  unref(parseWarning) != ''
                    ? _cE("view", _uM({
                        key: 0,
                        class: "warning-box"
                      }), [
                        _cE("text", _uM({ class: "warning-text" }), _tD(unref(parseWarning)), 1 /* TEXT */)
                      ])
                    : _cC("v-if", true),
                  unref(steps).length == 0
                    ? _cE("view", _uM({
                        key: 1,
                        class: "empty-box"
                      }), [
                        _cE("text", _uM({ class: "empty-text" }), "还没有步骤。请先添加乘法、加法或取整动作。")
                      ])
                    : _cC("v-if", true),
                  _cE(Fragment, null, RenderHelpers.renderList(unref(steps), (step, index, __index, _cached): any => {
                    return _cE("view", _uM({
                      key: 'step-' + index,
                      class: "step-card"
                    }), [
                      _cE("view", _uM({ class: "step-head" }), [
                        _cE("text", _uM({ class: "step-title" }), "第 " + _tD(index + 1) + " 步 · " + _tD(stepTitle(step)), 1 /* TEXT */),
                        _cE("view", _uM({ class: "step-actions" }), [
                          _cE("view", _uM({
                            class: "icon-btn",
                            onClick: () => {moveStepUp(index)}
                          }), [
                            _cE("text", _uM({ class: "icon-btn-text" }), "↑")
                          ], 8 /* PROPS */, ["onClick"]),
                          _cE("view", _uM({
                            class: "icon-btn",
                            onClick: () => {moveStepDown(index)}
                          }), [
                            _cE("text", _uM({ class: "icon-btn-text" }), "↓")
                          ], 8 /* PROPS */, ["onClick"]),
                          _cE("view", _uM({
                            class: "icon-btn icon-btn-danger",
                            onClick: () => {removeStep(index)}
                          }), [
                            _cE("text", _uM({ class: "icon-btn-danger-text" }), "删")
                          ], 8 /* PROPS */, ["onClick"])
                        ])
                      ]),
                      step.kind == 'binary'
                        ? _cE("view", _uM({
                            key: 0,
                            class: "field"
                          }), [
                            _cE("text", _uM({ class: "field-label" }), "运算类型"),
                            _cE("view", _uM({ class: "choice-list" }), [
                              _cE("view", _uM({
                                class: _nC(step.operator == '*' ? 'mini-choice mini-choice-active' : 'mini-choice'),
                                onClick: () => {setStepOperator(index, '*')}
                              }), [
                                _cE("text", _uM({
                                  class: _nC(step.operator == '*' ? 'mini-choice-text mini-choice-text-active' : 'mini-choice-text')
                                }), "乘", 2 /* CLASS */)
                              ], 10 /* CLASS, PROPS */, ["onClick"]),
                              _cE("view", _uM({
                                class: _nC(step.operator == '/' ? 'mini-choice mini-choice-active' : 'mini-choice'),
                                onClick: () => {setStepOperator(index, '/')}
                              }), [
                                _cE("text", _uM({
                                  class: _nC(step.operator == '/' ? 'mini-choice-text mini-choice-text-active' : 'mini-choice-text')
                                }), "除", 2 /* CLASS */)
                              ], 10 /* CLASS, PROPS */, ["onClick"]),
                              _cE("view", _uM({
                                class: _nC(step.operator == '+' ? 'mini-choice mini-choice-active' : 'mini-choice'),
                                onClick: () => {setStepOperator(index, '+')}
                              }), [
                                _cE("text", _uM({
                                  class: _nC(step.operator == '+' ? 'mini-choice-text mini-choice-text-active' : 'mini-choice-text')
                                }), "加", 2 /* CLASS */)
                              ], 10 /* CLASS, PROPS */, ["onClick"]),
                              _cE("view", _uM({
                                class: _nC(step.operator == '-' ? 'mini-choice mini-choice-active' : 'mini-choice'),
                                onClick: () => {setStepOperator(index, '-')}
                              }), [
                                _cE("text", _uM({
                                  class: _nC(step.operator == '-' ? 'mini-choice-text mini-choice-text-active' : 'mini-choice-text')
                                }), "减", 2 /* CLASS */)
                              ], 10 /* CLASS, PROPS */, ["onClick"])
                            ]),
                            _cE("input", _uM({
                              class: "input",
                              value: step.operand,
                              placeholder: "请输入运算数值",
                              onInput: ($event: UniInputEvent) => {handleStepOperandInput(index, $event)}
                            }), null, 40 /* PROPS, NEED_HYDRATION */, ["value", "onInput"])
                          ])
                        : _cE("view", _uM({
                            key: 1,
                            class: "field"
                          }), [
                            _cE("text", _uM({ class: "field-label" }), "取整模式"),
                            _cE("view", _uM({ class: "choice-list" }), [
                              _cE(Fragment, null, RenderHelpers.renderList(unref(roundModeChoices), (mode, __key, __index, _cached): any => {
                                return _cE("view", _uM({
                                  key: mode.value,
                                  class: _nC(step.mode == mode.value ? 'round-choice round-choice-active' : 'round-choice'),
                                  onClick: () => {setStepRoundMode(index, mode.value)}
                                }), [
                                  _cE("text", _uM({
                                    class: _nC(step.mode == mode.value ? 'round-choice-text round-choice-text-active' : 'round-choice-text')
                                  }), _tD(mode.label), 3 /* TEXT, CLASS */)
                                ], 10 /* CLASS, PROPS */, ["onClick"])
                              }), 128 /* KEYED_FRAGMENT */)
                            ]),
                            isTrue(requiresRoundParameter(step.mode))
                              ? _cE("input", _uM({
                                  key: 0,
                                  class: "input",
                                  value: step.parameter,
                                  placeholder: roundParameterPlaceholder(step.mode),
                                  onInput: ($event: UniInputEvent) => {handleStepParameterInput(index, $event)}
                                }), null, 40 /* PROPS, NEED_HYDRATION */, ["value", "placeholder", "onInput"])
                              : _cC("v-if", true)
                          ]),
                      _cE("text", _uM({ class: "step-desc" }), _tD(describeStep(step)), 1 /* TEXT */)
                    ])
                  }), 128 /* KEYED_FRAGMENT */)
                ]),
                _cE("view", _uM({ class: "section" }), [
                  _cE("text", _uM({ class: "section-title" }), "实时试算"),
                  _cE("view", _uM({ class: "field" }), [
                    _cE("text", _uM({ class: "field-label" }), "测试输入值"),
                    _cE("input", _uM({
                      class: "input",
                      value: unref(testValue),
                      type: "number",
                      placeholder: "例如 9.90",
                      onInput: handleTestValueInput
                    }), null, 40 /* PROPS, NEED_HYDRATION */, ["value"])
                  ]),
                  _cE("view", _uM({ class: "result-row" }), [
                    _cE("text", _uM({ class: "result-label" }), "试算结果"),
                    _cE("text", _uM({ class: "result-value" }), _tD(previewResult.value == '' ? '请完善步骤' : previewResult.value), 1 /* TEXT */)
                  ]),
                  _cE("view", _uM({ class: "expression-box" }), [
                    _cE("text", _uM({ class: "expression-label" }), "表达式"),
                    _cE("text", _uM({ class: "expression-text" }), _tD(expressionText.value == '' ? '请先完成公式步骤' : expressionText.value), 1 /* TEXT */)
                  ]),
                  _cE("view", _uM({ class: "expression-box" }), [
                    _cE("text", _uM({ class: "expression-label" }), "中文预览"),
                    _cE("text", _uM({ class: "expression-text" }), _tD(displayExpressionText.value == '' ? '请先完成公式步骤' : displayExpressionText.value), 1 /* TEXT */)
                  ])
                ])
              ])
      ])
    ]),
    isTrue(!unref(loading) && unref(errorMessage) == '')
      ? _cE("view", _uM({
          key: 0,
          class: "footer"
        }), [
          _cE("view", _uM({
            class: "footer-btn footer-btn-light",
            onClick: handleCancel
          }), [
            _cE("text", _uM({ class: "footer-btn-light-text" }), "取消")
          ]),
          _cE("view", _uM({
            class: "footer-btn footer-btn-primary",
            onClick: handleSave
          }), [
            _cE("text", _uM({ class: "footer-btn-primary-text" }), _tD(unref(submitting) ? '保存中...' : '保存公式'), 1 /* TEXT */)
          ])
        ])
      : _cC("v-if", true)
  ])
}
}

})
export default __sfc__
const GenPagesProductsPricingFormulaFromStyles = [_uM([["page", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["position", "relative"], ["backgroundColor", "#EEF2F7"]]))], ["page-scroll", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"]]))], ["page-content", _pS(_uM([["paddingLeft", 10], ["paddingRight", 10], ["paddingTop", 10], ["paddingBottom", 92]]))], ["section", _pS(_uM([["paddingLeft", 12], ["paddingRight", 12], ["paddingTop", 12], ["paddingBottom", 12], ["marginBottom", 10], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#FFFFFF"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E3E8F0"], ["borderRightColor", "#E3E8F0"], ["borderBottomColor", "#E3E8F0"], ["borderLeftColor", "#E3E8F0"]]))], ["section-title", _pS(_uM([["fontSize", 16], ["lineHeight", "22px"], ["color", "#111827"], ["fontWeight", "bold"]]))], ["field", _pS(_uM([["marginTop", 10]]))], ["field-label", _pS(_uM([["fontSize", 13], ["lineHeight", "18px"], ["color", "#334155"]]))], ["input", _pS(_uM([["height", 40], ["marginTop", 6], ["paddingLeft", 10], ["paddingRight", 10], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#D8DEE8"], ["borderRightColor", "#D8DEE8"], ["borderBottomColor", "#D8DEE8"], ["borderLeftColor", "#D8DEE8"], ["backgroundColor", "#FFFFFF"], ["fontSize", 14], ["color", "#111827"]]))], ["textarea", _pS(_uM([["height", 78], ["marginTop", 6], ["paddingLeft", 10], ["paddingRight", 10], ["paddingTop", 8], ["paddingBottom", 8], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#D8DEE8"], ["borderRightColor", "#D8DEE8"], ["borderBottomColor", "#D8DEE8"], ["borderLeftColor", "#D8DEE8"], ["backgroundColor", "#FFFFFF"], ["fontSize", 14], ["color", "#111827"]]))], ["switch-row", _pS(_uM([["marginTop", 12], ["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "space-between"]]))], ["choice-list", _pS(_uM([["flexDirection", "row"], ["flexWrap", "wrap"], ["marginTop", 8]]))], ["choice", _pS(_uM([["paddingLeft", 10], ["paddingRight", 10], ["paddingTop", 8], ["paddingBottom", 8], ["marginRight", 6], ["marginBottom", 6], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#D8DEE8"], ["borderRightColor", "#D8DEE8"], ["borderBottomColor", "#D8DEE8"], ["borderLeftColor", "#D8DEE8"], ["backgroundColor", "#F8FAFC"]]))], ["choice-active", _pS(_uM([["borderTopColor", "#0F172A"], ["borderRightColor", "#0F172A"], ["borderBottomColor", "#0F172A"], ["borderLeftColor", "#0F172A"], ["backgroundColor", "#0F172A"]]))], ["choice-text", _pS(_uM([["fontSize", 13], ["lineHeight", "16px"], ["color", "#334155"]]))], ["choice-text-active", _pS(_uM([["color", "#FFFFFF"]]))], ["tool-list", _pS(_uM([["flexDirection", "row"], ["flexWrap", "wrap"], ["marginTop", 10]]))], ["tool-btn", _pS(_uM([["paddingLeft", 10], ["paddingRight", 10], ["paddingTop", 8], ["paddingBottom", 8], ["marginRight", 6], ["marginBottom", 6], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#EAF2FF"]]))], ["tool-btn-text", _pS(_uM([["fontSize", 12], ["lineHeight", "15px"], ["color", "#1D4ED8"]]))], ["warning-box", _pS(_uM([["marginTop", 8], ["paddingLeft", 10], ["paddingRight", 10], ["paddingTop", 8], ["paddingBottom", 8], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#FFF7ED"]]))], ["warning-text", _pS(_uM([["fontSize", 12], ["lineHeight", "18px"], ["color", "#9A3412"]]))], ["empty-box", _pS(_uM([["marginTop", 10], ["paddingTop", 16], ["paddingBottom", 16], ["alignItems", "center"], ["backgroundColor", "#F8FAFC"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8]]))], ["empty-text", _pS(_uM([["fontSize", 13], ["lineHeight", "18px"], ["color", "#64748B"]]))], ["step-card", _pS(_uM([["marginTop", 10], ["paddingLeft", 10], ["paddingRight", 10], ["paddingTop", 10], ["paddingBottom", 10], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E3E8F0"], ["borderRightColor", "#E3E8F0"], ["borderBottomColor", "#E3E8F0"], ["borderLeftColor", "#E3E8F0"], ["backgroundColor", "#FAFBFD"]]))], ["step-head", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "space-between"]]))], ["step-title", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["fontSize", 14], ["lineHeight", "18px"], ["color", "#111827"], ["fontWeight", "bold"]]))], ["step-actions", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"]]))], ["icon-btn", _pS(_uM([["width", 28], ["height", 28], ["marginLeft", 5], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["alignItems", "center"], ["justifyContent", "center"], ["backgroundColor", "#EEF2F7"]]))], ["icon-btn-danger", _pS(_uM([["backgroundColor", "#FEE2E2"]]))], ["icon-btn-text", _pS(_uM([["fontSize", 13], ["lineHeight", "13px"], ["color", "#334155"]]))], ["icon-btn-danger-text", _pS(_uM([["fontSize", 12], ["lineHeight", "12px"], ["color", "#B91C1C"]]))], ["mini-choice", _pS(_uM([["minWidth", 42], ["height", 30], ["marginRight", 6], ["marginBottom", 6], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["alignItems", "center"], ["justifyContent", "center"], ["backgroundColor", "#EEF2F7"]]))], ["mini-choice-active", _pS(_uM([["backgroundColor", "#0F172A"]]))], ["mini-choice-text", _pS(_uM([["fontSize", 13], ["lineHeight", "13px"], ["color", "#334155"]]))], ["mini-choice-text-active", _pS(_uM([["color", "#FFFFFF"]]))], ["round-choice", _pS(_uM([["paddingLeft", 8], ["paddingRight", 8], ["paddingTop", 7], ["paddingBottom", 7], ["marginRight", 6], ["marginBottom", 6], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#EEF2F7"]]))], ["round-choice-active", _pS(_uM([["backgroundColor", "#0F172A"]]))], ["round-choice-text", _pS(_uM([["fontSize", 12], ["lineHeight", "15px"], ["color", "#334155"]]))], ["round-choice-text-active", _pS(_uM([["color", "#FFFFFF"]]))], ["step-desc", _pS(_uM([["marginTop", 8], ["fontSize", 12], ["lineHeight", "18px"], ["color", "#64748B"]]))], ["result-row", _pS(_uM([["marginTop", 10], ["paddingLeft", 10], ["paddingRight", 10], ["paddingTop", 10], ["paddingBottom", 10], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#F0FDF4"]]))], ["result-label", _pS(_uM([["fontSize", 12], ["lineHeight", "16px"], ["color", "#166534"]]))], ["result-value", _pS(_uM([["marginTop", 4], ["fontSize", 22], ["lineHeight", "28px"], ["color", "#166534"], ["fontWeight", "bold"]]))], ["expression-box", _pS(_uM([["marginTop", 10], ["paddingLeft", 10], ["paddingRight", 10], ["paddingTop", 10], ["paddingBottom", 10], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#F8FAFC"]]))], ["expression-label", _pS(_uM([["fontSize", 12], ["lineHeight", "16px"], ["color", "#64748B"]]))], ["expression-text", _pS(_uM([["marginTop", 4], ["fontSize", 12], ["lineHeight", "18px"], ["color", "#111827"]]))], ["state-card", _pS(_uM([["paddingTop", 36], ["paddingBottom", 36], ["paddingLeft", 14], ["paddingRight", 14], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#FFFFFF"], ["alignItems", "center"]]))], ["state-title", _pS(_uM([["fontSize", 16], ["lineHeight", "22px"], ["color", "#B91C1C"], ["fontWeight", "bold"]]))], ["state-text", _pS(_uM([["marginTop", 6], ["fontSize", 13], ["lineHeight", "18px"], ["color", "#64748B"], ["textAlign", "center"]]))], ["small-btn", _pS(_uM([["marginTop", 12], ["height", 36], ["paddingLeft", 16], ["paddingRight", 16], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#0F172A"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["small-btn-text", _pS(_uM([["fontSize", 13], ["lineHeight", "13px"], ["color", "#FFFFFF"]]))], ["footer", _pS(_uM([["position", "absolute"], ["left", 0], ["right", 0], ["bottom", 0], ["flexDirection", "row"], ["paddingLeft", 10], ["paddingRight", 10], ["paddingTop", 8], ["paddingBottom", 10], ["backgroundColor", "#FFFFFF"], ["borderTopWidth", 1], ["borderTopStyle", "solid"], ["borderTopColor", "#E3E8F0"]]))], ["footer-btn", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["height", 42], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["alignItems", "center"], ["justifyContent", "center"]]))], ["footer-btn-light", _pS(_uM([["marginRight", 8], ["backgroundColor", "#F3F6FA"]]))], ["footer-btn-primary", _pS(_uM([["backgroundColor", "#0F172A"]]))], ["footer-btn-light-text", _pS(_uM([["fontSize", 14], ["lineHeight", "14px"], ["color", "#334155"]]))], ["footer-btn-primary-text", _pS(_uM([["fontSize", 14], ["lineHeight", "14px"], ["color", "#FFFFFF"]]))]])]
