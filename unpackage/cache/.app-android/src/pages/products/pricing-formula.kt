@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME", "UNUSED_ANONYMOUS_PARAMETER", "SENSELESS_COMPARISON", "NAME_SHADOWING", "UNNECESSARY_NOT_NULL_ASSERTION")
package uni.UNI1CE1B14
import io.dcloud.uniapp.*
import io.dcloud.uniapp.extapi.*
import io.dcloud.uniapp.framework.*
import io.dcloud.uniapp.runtime.*
import io.dcloud.uniapp.vue.*
import io.dcloud.uniapp.vue.shared.*
import io.dcloud.unicloud.*
import io.dcloud.uts.*
import io.dcloud.uts.Map
import io.dcloud.uts.Set
import io.dcloud.uts.UTSAndroid
import kotlin.properties.Delegates
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.reLaunch as uni_reLaunch
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesProductsPricingFormula : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesProductsPricingFormula) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesProductsPricingFormula
            val _cache = __ins.renderCache
            val formulaId = ref("")
            val loading = ref(false)
            val submitting = ref(false)
            val errorMessage = ref("")
            val parseWarning = ref("")
            val rawExpression = ref("")
            val parseFailed = ref(false)
            val name = ref("")
            val code = ref("")
            val description = ref("")
            val isActive = ref(true)
            val baseVariable = ref("VALUE")
            val steps = ref(_uA<FormulaStep>())
            val testValue = ref("9.90")
            val sourceChoices = ref(_uA<FormulaChoice>(FormulaChoice(value = "VALUE", label = "不固定数据源"), FormulaChoice(value = "COST_PRICE", label = "成本价"), FormulaChoice(value = "PURCHASE_PRICE", label = "含税采购价"), FormulaChoice(value = "NET_PURCHASE_PRICE", label = "不含税采购价"), FormulaChoice(value = "BASE_SALES_PRICE", label = "基础销售价")))
            val roundModeChoices = ref(_uA<FormulaChoice>(FormulaChoice(value = "round_digits", label = "四舍五入"), FormulaChoice(value = "ceil_step", label = "按步长向上取整"), FormulaChoice(value = "floor_step", label = "按步长向下取整"), FormulaChoice(value = "ceil_tail_09", label = "向上取到尾数 0.9"), FormulaChoice(value = "floor_tail_09", label = "向下取到尾数 0.9")))
            fun stringValue(value: Any?, fallback: String = ""): String {
                if (value == null) {
                    return fallback
                }
                val text = "" + value
                return if (text == "") {
                    fallback
                } else {
                    text
                }
            }
            fun gen_readEventValue_fn(event: Any): String {
                if (event == null) {
                    return ""
                }
                val inputEvent = event as UniInputEvent
                val detail = inputEvent.detail
                if (detail == null) {
                    return ""
                }
                return detail.value
            }
            val readEventValue = ::gen_readEventValue_fn
            fun gen_isDigitChar_fn(ch: String): Boolean {
                return ch >= "0" && ch <= "9"
            }
            val isDigitChar = ::gen_isDigitChar_fn
            fun gen_isValidDecimal_fn(value: String): Boolean {
                val text = value.trim()
                if (text == "") {
                    return false
                }
                var start: Number = 0
                if (text.substring(0, 1) == "-") {
                    start = 1
                }
                if (start >= text.length) {
                    return false
                }
                var dotCount: Number = 0
                var digitCount: Number = 0
                run {
                    var i = start
                    while(i < text.length){
                        val ch = text.substring(i, i + 1)
                        if (ch == ".") {
                            dotCount += 1
                            if (dotCount > 1) {
                                return false
                            }
                            i += 1
                            continue
                        }
                        if (!isDigitChar(ch)) {
                            return false
                        }
                        digitCount += 1
                        i += 1
                    }
                }
                return digitCount > 0
            }
            val isValidDecimal = ::gen_isValidDecimal_fn
            fun gen_parseDecimal_fn(value: String): Number {
                if (!isValidDecimal(value)) {
                    return 0
                }
                return parseFloat(value.trim())
            }
            val parseDecimal = ::gen_parseDecimal_fn
            fun gen_roundHalfUp_fn(value: Number, digits: Number): Number {
                val factor = Math.pow(10, digits)
                if (value >= 0) {
                    return Math.round(value * factor) / factor
                }
                return -Math.round(Math.abs(value) * factor) / factor
            }
            val roundHalfUp = ::gen_roundHalfUp_fn
            fun gen_normalizeNumber_fn(value: Number): Number {
                return Math.round(value * 1000000000) / 1000000000
            }
            val normalizeNumber = ::gen_normalizeNumber_fn
            fun gen_ceilToStep_fn(value: Number, step: Number): Number {
                if (step <= 0) {
                    return value
                }
                return normalizeNumber(Math.ceil(value / step) * step)
            }
            val ceilToStep = ::gen_ceilToStep_fn
            fun gen_floorToStep_fn(value: Number, step: Number): Number {
                if (step <= 0) {
                    return value
                }
                return normalizeNumber(Math.floor(value / step) * step)
            }
            val floorToStep = ::gen_floorToStep_fn
            fun gen_ceilTo09_fn(value: Number): Number {
                val integer = Math.floor(value)
                val candidate = integer + 0.9
                if (value <= candidate) {
                    return normalizeNumber(candidate)
                }
                return normalizeNumber(integer + 1.9)
            }
            val ceilTo09 = ::gen_ceilTo09_fn
            fun gen_floorTo09_fn(value: Number): Number {
                val integer = Math.floor(value)
                val candidate = integer + 0.9
                if (value >= candidate) {
                    return normalizeNumber(candidate)
                }
                return normalizeNumber(integer - 0.1)
            }
            val floorTo09 = ::gen_floorTo09_fn
            fun gen_cloneSteps_fn(source: UTSArray<FormulaStep>): UTSArray<FormulaStep> {
                val result: UTSArray<FormulaStep> = _uA()
                run {
                    var i: Number = 0
                    while(i < source.length){
                        val item = source[i]
                        result.push(FormulaStep(kind = item.kind, operator = item.operator, operand = item.operand, mode = item.mode, parameter = item.parameter))
                        i += 1
                    }
                }
                return result
            }
            val cloneSteps = ::gen_cloneSteps_fn
            fun gen_sourceLabel_fn(value: String): String {
                run {
                    var index: Number = 0
                    while(index < sourceChoices.value.length){
                        val item = sourceChoices.value[index]
                        if (item.value == value) {
                            return item.label
                        }
                        index += 1
                    }
                }
                return "不固定数据源"
            }
            val sourceLabel = ::gen_sourceLabel_fn
            fun gen_normalizeFormulaSource_fn(value: String): String {
                val text = value.trim().toUpperCase()
                run {
                    var index: Number = 0
                    while(index < sourceChoices.value.length){
                        val item = sourceChoices.value[index]
                        if (item.value == text) {
                            return item.value
                        }
                        index += 1
                    }
                }
                return "VALUE"
            }
            val normalizeFormulaSource = ::gen_normalizeFormulaSource_fn
            fun gen_operatorLabel_fn(operator: String): String {
                if (operator == "*") {
                    return "乘以"
                }
                if (operator == "/") {
                    return "除以"
                }
                if (operator == "+") {
                    return "加"
                }
                if (operator == "-") {
                    return "减"
                }
                return "运算"
            }
            val operatorLabel = ::gen_operatorLabel_fn
            fun gen_roundModeLabel_fn(mode: String): String {
                if (mode == "round_digits") {
                    return "四舍五入"
                }
                if (mode == "ceil_step") {
                    return "按步长向上取整"
                }
                if (mode == "floor_step") {
                    return "按步长向下取整"
                }
                if (mode == "ceil_tail_09") {
                    return "向上取到尾数 0.9"
                }
                if (mode == "floor_tail_09") {
                    return "向下取到尾数 0.9"
                }
                return "取整"
            }
            val roundModeLabel = ::gen_roundModeLabel_fn
            fun gen_stepTitle_fn(step: FormulaStep): String {
                if (step.kind == "binary") {
                    if (step.operator == "*") {
                        return "乘法"
                    }
                    if (step.operator == "/") {
                        return "除法"
                    }
                    if (step.operator == "+") {
                        return "加法"
                    }
                    if (step.operator == "-") {
                        return "减法"
                    }
                    return "运算"
                }
                return roundModeLabel(step.mode)
            }
            val stepTitle = ::gen_stepTitle_fn
            fun gen_requiresRoundParameter_fn(mode: String): Boolean {
                return mode == "round_digits" || mode == "ceil_step" || mode == "floor_step"
            }
            val requiresRoundParameter = ::gen_requiresRoundParameter_fn
            fun gen_roundParameterPlaceholder_fn(mode: String): String {
                if (mode == "round_digits") {
                    return "保留位数，例如 2"
                }
                return "步长，例如 0.5、1、10"
            }
            val roundParameterPlaceholder = ::gen_roundParameterPlaceholder_fn
            fun gen_describeStep_fn(step: FormulaStep): String {
                if (step.kind == "binary") {
                    return operatorLabel(step.operator) + " " + step.operand
                }
                if (step.mode == "round_digits") {
                    return "四舍五入到 " + step.parameter + " 位小数"
                }
                if (step.mode == "ceil_step") {
                    return "向上取到步长 " + step.parameter
                }
                if (step.mode == "floor_step") {
                    return "向下取到步长 " + step.parameter
                }
                if (step.mode == "ceil_tail_09") {
                    return "向上取到尾数 0.9"
                }
                if (step.mode == "floor_tail_09") {
                    return "向下取到尾数 0.9"
                }
                return "未完成步骤"
            }
            val describeStep = ::gen_describeStep_fn
            fun gen_buildExpression_fn(baseValue: String, sourceSteps: UTSArray<FormulaStep>): String {
                if (sourceSteps.length == 0) {
                    return ""
                }
                var expression = normalizeFormulaSource(baseValue)
                run {
                    var index: Number = 0
                    while(index < sourceSteps.length){
                        val step = sourceSteps[index]
                        if (step.kind == "binary") {
                            if (!isValidDecimal(step.operand)) {
                                return ""
                            }
                            expression = "(" + expression + " " + step.operator + " " + step.operand.trim() + ")"
                            index += 1
                            continue
                        }
                        if (step.kind == "round") {
                            if (step.mode == "round_digits") {
                                if (!isValidDecimal(step.parameter)) {
                                    return ""
                                }
                                expression = "ROUND(" + expression + ", " + step.parameter.trim() + ")"
                            } else if (step.mode == "ceil_step") {
                                if (!isValidDecimal(step.parameter)) {
                                    return ""
                                }
                                expression = "CEILING(" + expression + ", " + step.parameter.trim() + ")"
                            } else if (step.mode == "floor_step") {
                                if (!isValidDecimal(step.parameter)) {
                                    return ""
                                }
                                expression = "FLOOR(" + expression + ", " + step.parameter.trim() + ")"
                            } else if (step.mode == "ceil_tail_09") {
                                expression = "CEIL_TO_09(" + expression + ")"
                            } else if (step.mode == "floor_tail_09") {
                                expression = "FLOOR_TO_09(" + expression + ")"
                            } else {
                                return ""
                            }
                        }
                        index += 1
                    }
                }
                return expression
            }
            val buildExpression = ::gen_buildExpression_fn
            fun gen_buildDisplayExpression_fn(baseValue: String, sourceSteps: UTSArray<FormulaStep>): String {
                if (sourceSteps.length == 0) {
                    return ""
                }
                var expression = sourceLabel(normalizeFormulaSource(baseValue))
                run {
                    var index: Number = 0
                    while(index < sourceSteps.length){
                        val step = sourceSteps[index]
                        if (step.kind == "binary") {
                            if (!isValidDecimal(step.operand)) {
                                return ""
                            }
                            expression = "(" + expression + " " + operatorLabel(step.operator) + " " + step.operand.trim() + ")"
                            index += 1
                            continue
                        }
                        if (step.kind == "round") {
                            if (step.mode == "round_digits") {
                                if (!isValidDecimal(step.parameter)) {
                                    return ""
                                }
                                expression = "四舍五入(" + expression + "，保留 " + step.parameter.trim() + " 位)"
                            } else if (step.mode == "ceil_step") {
                                if (!isValidDecimal(step.parameter)) {
                                    return ""
                                }
                                expression = "向上取到步长(" + expression + "，" + step.parameter.trim() + ")"
                            } else if (step.mode == "floor_step") {
                                if (!isValidDecimal(step.parameter)) {
                                    return ""
                                }
                                expression = "向下取到步长(" + expression + "，" + step.parameter.trim() + ")"
                            } else if (step.mode == "ceil_tail_09") {
                                expression = "向上取到尾数 0.9(" + expression + ")"
                            } else if (step.mode == "floor_tail_09") {
                                expression = "向下取到尾数 0.9(" + expression + ")"
                            } else {
                                return ""
                            }
                        }
                        index += 1
                    }
                }
                return expression
            }
            val buildDisplayExpression = ::gen_buildDisplayExpression_fn
            fun gen_evaluateSteps_fn(baseText: String, sourceSteps: UTSArray<FormulaStep>): String {
                if (!isValidDecimal(baseText)) {
                    return ""
                }
                if (sourceSteps.length == 0) {
                    return ""
                }
                var current = parseDecimal(baseText)
                run {
                    var index: Number = 0
                    while(index < sourceSteps.length){
                        val step = sourceSteps[index]
                        if (step.kind == "binary") {
                            if (!isValidDecimal(step.operand)) {
                                return ""
                            }
                            val operand = parseDecimal(step.operand)
                            if (step.operator == "*") {
                                current = normalizeNumber(current * operand)
                            } else if (step.operator == "/") {
                                if (operand == 0) {
                                    return ""
                                }
                                current = normalizeNumber(current / operand)
                            } else if (step.operator == "+") {
                                current = normalizeNumber(current + operand)
                            } else if (step.operator == "-") {
                                current = normalizeNumber(current - operand)
                            } else {
                                return ""
                            }
                            index += 1
                            continue
                        }
                        if (step.kind == "round") {
                            if (step.mode == "round_digits") {
                                if (!isValidDecimal(step.parameter)) {
                                    return ""
                                }
                                current = roundHalfUp(current, parseInt(step.parameter))
                            } else if (step.mode == "ceil_step") {
                                if (!isValidDecimal(step.parameter)) {
                                    return ""
                                }
                                current = ceilToStep(current, parseDecimal(step.parameter))
                            } else if (step.mode == "floor_step") {
                                if (!isValidDecimal(step.parameter)) {
                                    return ""
                                }
                                current = floorToStep(current, parseDecimal(step.parameter))
                            } else if (step.mode == "ceil_tail_09") {
                                current = ceilTo09(current)
                            } else if (step.mode == "floor_tail_09") {
                                current = floorTo09(current)
                            } else {
                                return ""
                            }
                        }
                        index += 1
                    }
                }
                return "" + roundHalfUp(current, 2)
            }
            val evaluateSteps = ::gen_evaluateSteps_fn
            fun gen_isWrappedByOuterPair_fn(text: String): Boolean {
                if (text.length < 2) {
                    return false
                }
                if (text.substring(0, 1) != "(" || text.substring(text.length - 1, text.length) != ")") {
                    return false
                }
                var depth: Number = 0
                run {
                    var index: Number = 0
                    while(index < text.length){
                        val ch = text.substring(index, index + 1)
                        if (ch == "(") {
                            depth += 1
                        }
                        if (ch == ")") {
                            depth -= 1
                        }
                        if (depth == 0 && index < text.length - 1) {
                            return false
                        }
                        index += 1
                    }
                }
                return depth == 0
            }
            val isWrappedByOuterPair = ::gen_isWrappedByOuterPair_fn
            fun gen_trimOuterPair_fn(value: String): String {
                var text = value.trim()
                while(isWrappedByOuterPair(text)){
                    text = text.substring(1, text.length - 1).trim()
                }
                return text
            }
            val trimOuterPair = ::gen_trimOuterPair_fn
            fun gen_functionBody_fn(text: String, nameValue: String): String? {
                val prefix = nameValue + "("
                if (!text.startsWith(prefix)) {
                    return null
                }
                if (text.substring(text.length - 1, text.length) != ")") {
                    return null
                }
                return text.substring(prefix.length, text.length - 1)
            }
            val functionBody = ::gen_functionBody_fn
            fun gen_splitTopLevelComma_fn(text: String): UTSArray<String> {
                val result: UTSArray<String> = _uA()
                var depth: Number = 0
                var start: Number = 0
                run {
                    var index: Number = 0
                    while(index < text.length){
                        val ch = text.substring(index, index + 1)
                        if (ch == "(") {
                            depth += 1
                        }
                        if (ch == ")") {
                            depth -= 1
                        }
                        if (ch == "," && depth == 0) {
                            result.push(text.substring(start, index).trim())
                            start = index + 1
                        }
                        index += 1
                    }
                }
                result.push(text.substring(start).trim())
                return result
            }
            val splitTopLevelComma = ::gen_splitTopLevelComma_fn
            fun gen_findTopLevelBinaryOperator_fn(text: String): Number {
                var depth: Number = 0
                run {
                    var index = text.length - 1
                    while(index >= 0){
                        val ch = text.substring(index, index + 1)
                        if (ch == ")") {
                            depth += 1
                        }
                        if (ch == "(") {
                            depth -= 1
                        }
                        if (depth == 0 && (ch == "+" || ch == "-" || ch == "*" || ch == "/")) {
                            if (index > 0 && index < text.length - 1) {
                                val before = text.substring(index - 1, index)
                                val after = text.substring(index + 1, index + 2)
                                if (before == " " && after == " ") {
                                    return index
                                }
                            }
                        }
                        index -= 1
                    }
                }
                return -1
            }
            val findTopLevelBinaryOperator = ::gen_findTopLevelBinaryOperator_fn
            fun gen_parseExpressionNode_fn(value: String, outSteps: UTSArray<FormulaStep>): String {
                val text = trimOuterPair(value)
                var body = functionBody(text, "CEIL_TO_09")
                if (body != null) {
                    val base = gen_parseExpressionNode_fn(body!!, outSteps)
                    outSteps.push(FormulaStep(kind = "round", operator = "", operand = "", mode = "ceil_tail_09", parameter = ""))
                    return base
                }
                body = functionBody(text, "FLOOR_TO_09")
                if (body != null) {
                    val base = gen_parseExpressionNode_fn(body!!, outSteps)
                    outSteps.push(FormulaStep(kind = "round", operator = "", operand = "", mode = "floor_tail_09", parameter = ""))
                    return base
                }
                body = functionBody(text, "ROUND")
                if (body != null) {
                    val args = splitTopLevelComma(body!!)
                    val base = gen_parseExpressionNode_fn(args[0], outSteps)
                    val parameter = if (args.length > 1) {
                        args[1]
                    } else {
                        "2"
                    }
                    outSteps.push(FormulaStep(kind = "round", operator = "", operand = "", mode = "round_digits", parameter = parameter))
                    return base
                }
                body = functionBody(text, "CEILING")
                if (body != null) {
                    val args = splitTopLevelComma(body!!)
                    val base = gen_parseExpressionNode_fn(args[0], outSteps)
                    val parameter = if (args.length > 1) {
                        args[1]
                    } else {
                        "1"
                    }
                    outSteps.push(FormulaStep(kind = "round", operator = "", operand = "", mode = "ceil_step", parameter = parameter))
                    return base
                }
                body = functionBody(text, "FLOOR")
                if (body != null) {
                    val args = splitTopLevelComma(body!!)
                    val base = gen_parseExpressionNode_fn(args[0], outSteps)
                    val parameter = if (args.length > 1) {
                        args[1]
                    } else {
                        "1"
                    }
                    outSteps.push(FormulaStep(kind = "round", operator = "", operand = "", mode = "floor_step", parameter = parameter))
                    return base
                }
                val opIndex = findTopLevelBinaryOperator(text)
                if (opIndex >= 0) {
                    val left = text.substring(0, opIndex).trim()
                    val operator = text.substring(opIndex, opIndex + 1)
                    val right = text.substring(opIndex + 1).trim()
                    val base = gen_parseExpressionNode_fn(left, outSteps)
                    outSteps.push(FormulaStep(kind = "binary", operator = operator, operand = right, mode = "", parameter = ""))
                    return base
                }
                return normalizeFormulaSource(text)
            }
            val parseExpressionNode = ::gen_parseExpressionNode_fn
            fun gen_parseExpressionToBuilder_fn(expression: String): ParsedFormula {
                val parsedSteps: UTSArray<FormulaStep> = _uA()
                try {
                    val base = parseExpressionNode(expression.trim(), parsedSteps)
                    return ParsedFormula(success = true, base_variable = normalizeFormulaSource(base), steps = parsedSteps)
                }
                 catch (error: Throwable) {
                    return ParsedFormula(success = false, base_variable = "VALUE", steps = _uA<FormulaStep>())
                }
            }
            val parseExpressionToBuilder = ::gen_parseExpressionToBuilder_fn
            fun gen_applyFormula_fn(item: ProductPricingFormulaItem) {
                name.value = item.name
                code.value = item.code
                description.value = item.description
                isActive.value = item.is_active
                rawExpression.value = item.expression
                val parsed = parseExpressionToBuilder(item.expression)
                if (parsed.success && parsed.steps.length > 0) {
                    baseVariable.value = parsed.base_variable
                    steps.value = parsed.steps
                    parseFailed.value = false
                    parseWarning.value = ""
                    return
                }
                baseVariable.value = "VALUE"
                steps.value = _uA<FormulaStep>()
                parseFailed.value = item.expression != ""
                parseWarning.value = if (item.expression == "") {
                    ""
                } else {
                    "当前表达式无法还原为步骤构建器。保存基础信息时会保留原表达式；添加步骤后将使用新表达式覆盖。"
                }
            }
            val applyFormula = ::gen_applyFormula_fn
            fun gen_parseErrorMessage_fn(error: Any, fallback: String): String {
                var message = fallback
                if (error != null) {}
                return message
            }
            val parseErrorMessage = ::gen_parseErrorMessage_fn
            fun gen_loadFormula_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (formulaId.value == "") {
                            errorMessage.value = "缺少价格公式ID"
                            return@w1
                        }
                        loading.value = true
                        errorMessage.value = ""
                        try {
                            val detail = await(getProductPricingFormulaDetail(formulaId.value))
                            applyFormula(detail)
                        }
                         catch (error: Throwable) {
                            errorMessage.value = parseErrorMessage(error, "价格公式加载失败")
                        }
                         finally {
                            loading.value = false
                        }
                })
            }
            val loadFormula = ::gen_loadFormula_fn
            fun gen_handleNameInput_fn(event: Any) {
                name.value = readEventValue(event)
            }
            val handleNameInput = ::gen_handleNameInput_fn
            fun gen_handleCodeInput_fn(event: Any) {
                code.value = readEventValue(event)
            }
            val handleCodeInput = ::gen_handleCodeInput_fn
            fun gen_handleDescriptionInput_fn(event: Any) {
                description.value = readEventValue(event)
            }
            val handleDescriptionInput = ::gen_handleDescriptionInput_fn
            fun gen_handleTestValueInput_fn(event: Any) {
                testValue.value = readEventValue(event)
            }
            val handleTestValueInput = ::gen_handleTestValueInput_fn
            fun gen_handleActiveChange_fn(event: Any) {
                val switchEvent = event as UniSwitchChangeEvent
                val detail = switchEvent.detail
                if (detail == null) {
                    return
                }
                isActive.value = detail.value
            }
            val handleActiveChange = ::gen_handleActiveChange_fn
            fun gen_selectBaseVariable_fn(value: String) {
                baseVariable.value = value
            }
            val selectBaseVariable = ::gen_selectBaseVariable_fn
            fun gen_addBinaryStep_fn(operator: String) {
                parseFailed.value = false
                parseWarning.value = ""
                val next = cloneSteps(steps.value)
                next.push(FormulaStep(kind = "binary", operator = operator, operand = if (operator == "*" || operator == "/") {
                    "1"
                } else {
                    "0"
                }
                , mode = "", parameter = ""))
                steps.value = next
            }
            val addBinaryStep = ::gen_addBinaryStep_fn
            fun gen_addRoundStep_fn(mode: String) {
                parseFailed.value = false
                parseWarning.value = ""
                var parameter = ""
                if (mode == "round_digits") {
                    parameter = "2"
                }
                if (mode == "ceil_step" || mode == "floor_step") {
                    parameter = "0.5"
                }
                val next = cloneSteps(steps.value)
                next.push(FormulaStep(kind = "round", operator = "", operand = "", mode = mode, parameter = parameter))
                steps.value = next
            }
            val addRoundStep = ::gen_addRoundStep_fn
            fun gen_replaceStep_fn(index: Number, nextStep: FormulaStep) {
                val next = cloneSteps(steps.value)
                if (index < 0 || index >= next.length) {
                    return
                }
                next[index] = nextStep
                steps.value = next
            }
            val replaceStep = ::gen_replaceStep_fn
            fun gen_setStepOperator_fn(index: Number, operator: String) {
                if (index < 0 || index >= steps.value.length) {
                    return
                }
                val current = steps.value[index]
                replaceStep(index, FormulaStep(kind = current.kind, operator = operator, operand = current.operand, mode = current.mode, parameter = current.parameter))
            }
            val setStepOperator = ::gen_setStepOperator_fn
            fun gen_setStepRoundMode_fn(index: Number, mode: String) {
                if (index < 0 || index >= steps.value.length) {
                    return
                }
                val current = steps.value[index]
                var parameter = current.parameter
                if (mode == "round_digits" && parameter == "") {
                    parameter = "2"
                }
                if ((mode == "ceil_step" || mode == "floor_step") && parameter == "") {
                    parameter = "0.5"
                }
                if (!requiresRoundParameter(mode)) {
                    parameter = ""
                }
                replaceStep(index, FormulaStep(kind = current.kind, operator = current.operator, operand = current.operand, mode = mode, parameter = parameter))
            }
            val setStepRoundMode = ::gen_setStepRoundMode_fn
            fun gen_handleStepOperandInput_fn(index: Number, event: Any) {
                if (index < 0 || index >= steps.value.length) {
                    return
                }
                val current = steps.value[index]
                replaceStep(index, FormulaStep(kind = current.kind, operator = current.operator, operand = readEventValue(event), mode = current.mode, parameter = current.parameter))
            }
            val handleStepOperandInput = ::gen_handleStepOperandInput_fn
            fun gen_handleStepParameterInput_fn(index: Number, event: Any) {
                if (index < 0 || index >= steps.value.length) {
                    return
                }
                val current = steps.value[index]
                replaceStep(index, FormulaStep(kind = current.kind, operator = current.operator, operand = current.operand, mode = current.mode, parameter = readEventValue(event)))
            }
            val handleStepParameterInput = ::gen_handleStepParameterInput_fn
            fun gen_removeStep_fn(index: Number) {
                val next: UTSArray<FormulaStep> = _uA()
                run {
                    var i: Number = 0
                    while(i < steps.value.length){
                        if (i != index) {
                            next.push(steps.value[i])
                        }
                        i += 1
                    }
                }
                steps.value = next
            }
            val removeStep = ::gen_removeStep_fn
            fun gen_moveStepUp_fn(index: Number) {
                if (index <= 0 || index >= steps.value.length) {
                    return
                }
                val next = cloneSteps(steps.value)
                val current = next[index]
                next[index] = next[index - 1]
                next[index - 1] = current
                steps.value = next
            }
            val moveStepUp = ::gen_moveStepUp_fn
            fun gen_moveStepDown_fn(index: Number) {
                if (index < 0 || index >= steps.value.length - 1) {
                    return
                }
                val next = cloneSteps(steps.value)
                val current = next[index]
                next[index] = next[index + 1]
                next[index + 1] = current
                steps.value = next
            }
            val moveStepDown = ::gen_moveStepDown_fn
            fun gen_resolveSaveExpression_fn(): String {
                val generated = buildExpression(baseVariable.value, steps.value)
                if (generated != "") {
                    return generated
                }
                if (parseFailed.value && steps.value.length == 0) {
                    return rawExpression.value
                }
                return ""
            }
            val resolveSaveExpression = ::gen_resolveSaveExpression_fn
            fun gen_handleSave_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (submitting.value) {
                            return@w1
                        }
                        val nameText = name.value.trim()
                        val codeText = code.value.trim()
                        if (nameText == "") {
                            uni_showToast(ShowToastOptions(title = "公式名称不能为空", icon = "none", duration = 3500))
                            return@w1
                        }
                        if (codeText == "") {
                            uni_showToast(ShowToastOptions(title = "公式编码不能为空", icon = "none", duration = 3500))
                            return@w1
                        }
                        val expression = resolveSaveExpression()
                        if (expression == "") {
                            uni_showToast(ShowToastOptions(title = "请完善公式步骤", icon = "none", duration = 3500))
                            return@w1
                        }
                        submitting.value = true
                        try {
                            await(updateProductPricingFormula(formulaId.value, ProductPricingFormulaMutationData(name = nameText, code = codeText, expression = expression, description = description.value.trim(), is_active = isActive.value)))
                            val message = takeLatestResponseMessage("价格公式保存成功")
                            uni_showToast(ShowToastOptions(title = message, icon = "success"))
                            rawExpression.value = expression
                            parseFailed.value = false
                            parseWarning.value = ""
                        }
                         catch (error: Throwable) {
                            showErrorToast(parseErrorMessage(error, "价格公式保存失败"))
                        }
                         finally {
                            submitting.value = false
                        }
                })
            }
            val handleSave = ::gen_handleSave_fn
            fun gen_handleCancel_fn() {
                uni_navigateBack(NavigateBackOptions(delta = 1, fail = fun(_){
                    uni_reLaunch(ReLaunchOptions(url = "/pages/tabbar/products"))
                }
                ))
            }
            val handleCancel = ::gen_handleCancel_fn
            val expressionText = computed(fun(): String {
                val generated = buildExpression(baseVariable.value, steps.value)
                if (generated != "") {
                    return generated
                }
                if (parseFailed.value) {
                    return rawExpression.value
                }
                return ""
            }
            )
            val displayExpressionText = computed(fun(): String {
                return buildDisplayExpression(baseVariable.value, steps.value)
            }
            )
            val previewResult = computed(fun(): String {
                return evaluateSteps(testValue.value, steps.value)
            }
            )
            onLoad(fun(event: OnLoadOptions){
                val idValue = event["id"]
                formulaId.value = if (idValue == null) {
                    ""
                } else {
                    stringValue(idValue)
                }
                loadFormula()
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_switch = resolveComponent("switch")
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to "编辑价格公式", "showBack" to true, "showSearch" to false, "showHome" to true, "homePath" to "/pages/tabbar/products", "backgroundColor" to "#EEF2F7")),
                    _cE("scroll-view", _uM("class" to "page-scroll", "scroll-y" to "true"), _uA(
                        _cE("view", _uM("class" to "page-content"), _uA(
                            if (isTrue(unref(loading))) {
                                _cE("view", _uM("key" to 0, "class" to "state-card"), _uA(
                                    _cE("text", _uM("class" to "state-text"), "正在加载价格公式...")
                                ))
                            } else {
                                if (unref(errorMessage) != "") {
                                    _cE("view", _uM("key" to 1, "class" to "state-card"), _uA(
                                        _cE("text", _uM("class" to "state-title"), "加载失败"),
                                        _cE("text", _uM("class" to "state-text"), _tD(unref(errorMessage)), 1),
                                        _cE("button", _uM("class" to "small-btn", "onClick" to loadFormula), _uA(
                                            _cE("text", _uM("class" to "small-btn-text"), "重新加载")
                                        ))
                                    ))
                                } else {
                                    _cE("view", _uM("key" to 2), _uA(
                                        _cE("view", _uM("class" to "section"), _uA(
                                            _cE("text", _uM("class" to "section-title"), "基础信息"),
                                            _cE("view", _uM("class" to "field"), _uA(
                                                _cE("text", _uM("class" to "field-label"), "公式名称"),
                                                _cE("input", _uM("class" to "input", "value" to unref(name), "placeholder" to "请输入公式名称", "onInput" to handleNameInput), null, 40, _uA(
                                                    "value"
                                                ))
                                            )),
                                            _cE("view", _uM("class" to "field"), _uA(
                                                _cE("text", _uM("class" to "field-label"), "公式编码"),
                                                _cE("input", _uM("class" to "input", "value" to unref(code), "placeholder" to "请输入公式编码", "onInput" to handleCodeInput), null, 40, _uA(
                                                    "value"
                                                ))
                                            )),
                                            _cE("view", _uM("class" to "field"), _uA(
                                                _cE("text", _uM("class" to "field-label"), "说明"),
                                                _cE("textarea", _uM("class" to "textarea", "value" to unref(description), "placeholder" to "请输入说明", "onInput" to handleDescriptionInput), null, 40, _uA(
                                                    "value"
                                                ))
                                            )),
                                            _cE("view", _uM("class" to "switch-row"), _uA(
                                                _cE("text", _uM("class" to "field-label"), "启用状态"),
                                                _cV(_component_switch, _uM("checked" to unref(isActive), "onChange" to handleActiveChange), null, 8, _uA(
                                                    "checked"
                                                ))
                                            ))
                                        )),
                                        _cE("view", _uM("class" to "section"), _uA(
                                            _cE("text", _uM("class" to "section-title"), "第一步：数据源"),
                                            _cE("view", _uM("class" to "choice-list"), _uA(
                                                _cE(Fragment, null, RenderHelpers.renderList(unref(sourceChoices), fun(item, __key, __index, _cached): Any {
                                                    return _cE("view", _uM("key" to item.value, "class" to _nC(if (unref(baseVariable) == item.value) {
                                                        "choice choice-active"
                                                    } else {
                                                        "choice"
                                                    }
                                                    ), "onClick" to fun(){
                                                        selectBaseVariable(item.value)
                                                    }
                                                    ), _uA(
                                                        _cE("text", _uM("class" to _nC(if (unref(baseVariable) == item.value) {
                                                            "choice-text choice-text-active"
                                                        } else {
                                                            "choice-text"
                                                        }
                                                        )), _tD(item.label), 3)
                                                    ), 10, _uA(
                                                        "onClick"
                                                    ))
                                                }
                                                ), 128)
                                            ))
                                        )),
                                        _cE("view", _uM("class" to "section"), _uA(
                                            _cE("text", _uM("class" to "section-title"), "第二步：公式步骤"),
                                            _cE("view", _uM("class" to "tool-list"), _uA(
                                                _cE("view", _uM("class" to "tool-btn", "onClick" to fun(){
                                                    addBinaryStep("*")
                                                }
                                                ), _uA(
                                                    _cE("text", _uM("class" to "tool-btn-text"), "乘法")
                                                ), 8, _uA(
                                                    "onClick"
                                                )),
                                                _cE("view", _uM("class" to "tool-btn", "onClick" to fun(){
                                                    addBinaryStep("/")
                                                }
                                                ), _uA(
                                                    _cE("text", _uM("class" to "tool-btn-text"), "除法")
                                                ), 8, _uA(
                                                    "onClick"
                                                )),
                                                _cE("view", _uM("class" to "tool-btn", "onClick" to fun(){
                                                    addBinaryStep("+")
                                                }
                                                ), _uA(
                                                    _cE("text", _uM("class" to "tool-btn-text"), "加法")
                                                ), 8, _uA(
                                                    "onClick"
                                                )),
                                                _cE("view", _uM("class" to "tool-btn", "onClick" to fun(){
                                                    addBinaryStep("-")
                                                }
                                                ), _uA(
                                                    _cE("text", _uM("class" to "tool-btn-text"), "减法")
                                                ), 8, _uA(
                                                    "onClick"
                                                )),
                                                _cE("view", _uM("class" to "tool-btn", "onClick" to fun(){
                                                    addRoundStep("round_digits")
                                                }
                                                ), _uA(
                                                    _cE("text", _uM("class" to "tool-btn-text"), "四舍五入")
                                                ), 8, _uA(
                                                    "onClick"
                                                )),
                                                _cE("view", _uM("class" to "tool-btn", "onClick" to fun(){
                                                    addRoundStep("ceil_step")
                                                }
                                                ), _uA(
                                                    _cE("text", _uM("class" to "tool-btn-text"), "步长上取")
                                                ), 8, _uA(
                                                    "onClick"
                                                )),
                                                _cE("view", _uM("class" to "tool-btn", "onClick" to fun(){
                                                    addRoundStep("floor_step")
                                                }
                                                ), _uA(
                                                    _cE("text", _uM("class" to "tool-btn-text"), "步长下取")
                                                ), 8, _uA(
                                                    "onClick"
                                                )),
                                                _cE("view", _uM("class" to "tool-btn", "onClick" to fun(){
                                                    addRoundStep("ceil_tail_09")
                                                }
                                                ), _uA(
                                                    _cE("text", _uM("class" to "tool-btn-text"), "尾数0.9上取")
                                                ), 8, _uA(
                                                    "onClick"
                                                )),
                                                _cE("view", _uM("class" to "tool-btn", "onClick" to fun(){
                                                    addRoundStep("floor_tail_09")
                                                }
                                                ), _uA(
                                                    _cE("text", _uM("class" to "tool-btn-text"), "尾数0.9下取")
                                                ), 8, _uA(
                                                    "onClick"
                                                ))
                                            )),
                                            if (unref(parseWarning) != "") {
                                                _cE("view", _uM("key" to 0, "class" to "warning-box"), _uA(
                                                    _cE("text", _uM("class" to "warning-text"), _tD(unref(parseWarning)), 1)
                                                ))
                                            } else {
                                                _cC("v-if", true)
                                            }
                                            ,
                                            if (unref(steps).length == 0) {
                                                _cE("view", _uM("key" to 1, "class" to "empty-box"), _uA(
                                                    _cE("text", _uM("class" to "empty-text"), "还没有步骤。请先添加乘法、加法或取整动作。")
                                                ))
                                            } else {
                                                _cC("v-if", true)
                                            }
                                            ,
                                            _cE(Fragment, null, RenderHelpers.renderList(unref(steps), fun(step, index, __index, _cached): Any {
                                                return _cE("view", _uM("key" to ("step-" + index), "class" to "step-card"), _uA(
                                                    _cE("view", _uM("class" to "step-head"), _uA(
                                                        _cE("text", _uM("class" to "step-title"), "第 " + _tD(index + 1) + " 步 · " + _tD(stepTitle(step)), 1),
                                                        _cE("view", _uM("class" to "step-actions"), _uA(
                                                            _cE("view", _uM("class" to "icon-btn", "onClick" to fun(){
                                                                moveStepUp(index)
                                                            }
                                                            ), _uA(
                                                                _cE("text", _uM("class" to "icon-btn-text"), "↑")
                                                            ), 8, _uA(
                                                                "onClick"
                                                            )),
                                                            _cE("view", _uM("class" to "icon-btn", "onClick" to fun(){
                                                                moveStepDown(index)
                                                            }
                                                            ), _uA(
                                                                _cE("text", _uM("class" to "icon-btn-text"), "↓")
                                                            ), 8, _uA(
                                                                "onClick"
                                                            )),
                                                            _cE("view", _uM("class" to "icon-btn icon-btn-danger", "onClick" to fun(){
                                                                removeStep(index)
                                                            }
                                                            ), _uA(
                                                                _cE("text", _uM("class" to "icon-btn-danger-text"), "删")
                                                            ), 8, _uA(
                                                                "onClick"
                                                            ))
                                                        ))
                                                    )),
                                                    if (step.kind == "binary") {
                                                        _cE("view", _uM("key" to 0, "class" to "field"), _uA(
                                                            _cE("text", _uM("class" to "field-label"), "运算类型"),
                                                            _cE("view", _uM("class" to "choice-list"), _uA(
                                                                _cE("view", _uM("class" to _nC(if (step.operator == "*") {
                                                                    "mini-choice mini-choice-active"
                                                                } else {
                                                                    "mini-choice"
                                                                }), "onClick" to fun(){
                                                                    setStepOperator(index, "*")
                                                                }), _uA(
                                                                    _cE("text", _uM("class" to _nC(if (step.operator == "*") {
                                                                        "mini-choice-text mini-choice-text-active"
                                                                    } else {
                                                                        "mini-choice-text"
                                                                    })), "乘", 2)
                                                                ), 10, _uA(
                                                                    "onClick"
                                                                )),
                                                                _cE("view", _uM("class" to _nC(if (step.operator == "/") {
                                                                    "mini-choice mini-choice-active"
                                                                } else {
                                                                    "mini-choice"
                                                                }), "onClick" to fun(){
                                                                    setStepOperator(index, "/")
                                                                }), _uA(
                                                                    _cE("text", _uM("class" to _nC(if (step.operator == "/") {
                                                                        "mini-choice-text mini-choice-text-active"
                                                                    } else {
                                                                        "mini-choice-text"
                                                                    })), "除", 2)
                                                                ), 10, _uA(
                                                                    "onClick"
                                                                )),
                                                                _cE("view", _uM("class" to _nC(if (step.operator == "+") {
                                                                    "mini-choice mini-choice-active"
                                                                } else {
                                                                    "mini-choice"
                                                                }), "onClick" to fun(){
                                                                    setStepOperator(index, "+")
                                                                }), _uA(
                                                                    _cE("text", _uM("class" to _nC(if (step.operator == "+") {
                                                                        "mini-choice-text mini-choice-text-active"
                                                                    } else {
                                                                        "mini-choice-text"
                                                                    })), "加", 2)
                                                                ), 10, _uA(
                                                                    "onClick"
                                                                )),
                                                                _cE("view", _uM("class" to _nC(if (step.operator == "-") {
                                                                    "mini-choice mini-choice-active"
                                                                } else {
                                                                    "mini-choice"
                                                                }), "onClick" to fun(){
                                                                    setStepOperator(index, "-")
                                                                }), _uA(
                                                                    _cE("text", _uM("class" to _nC(if (step.operator == "-") {
                                                                        "mini-choice-text mini-choice-text-active"
                                                                    } else {
                                                                        "mini-choice-text"
                                                                    })), "减", 2)
                                                                ), 10, _uA(
                                                                    "onClick"
                                                                ))
                                                            )),
                                                            _cE("input", _uM("class" to "input", "value" to step.operand, "placeholder" to "请输入运算数值", "onInput" to fun(`$event`: UniInputEvent){
                                                                handleStepOperandInput(index, `$event`)
                                                            }), null, 40, _uA(
                                                                "value",
                                                                "onInput"
                                                            ))
                                                        ))
                                                    } else {
                                                        _cE("view", _uM("key" to 1, "class" to "field"), _uA(
                                                            _cE("text", _uM("class" to "field-label"), "取整模式"),
                                                            _cE("view", _uM("class" to "choice-list"), _uA(
                                                                _cE(Fragment, null, RenderHelpers.renderList(unref(roundModeChoices), fun(mode, __key, __index, _cached): Any {
                                                                    return _cE("view", _uM("key" to mode.value, "class" to _nC(if (step.mode == mode.value) {
                                                                        "round-choice round-choice-active"
                                                                    } else {
                                                                        "round-choice"
                                                                    }
                                                                    ), "onClick" to fun(){
                                                                        setStepRoundMode(index, mode.value)
                                                                    }
                                                                    ), _uA(
                                                                        _cE("text", _uM("class" to _nC(if (step.mode == mode.value) {
                                                                            "round-choice-text round-choice-text-active"
                                                                        } else {
                                                                            "round-choice-text"
                                                                        }
                                                                        )), _tD(mode.label), 3)
                                                                    ), 10, _uA(
                                                                        "onClick"
                                                                    ))
                                                                }
                                                                ), 128)
                                                            )),
                                                            if (isTrue(requiresRoundParameter(step.mode))) {
                                                                _cE("input", _uM("key" to 0, "class" to "input", "value" to step.parameter, "placeholder" to roundParameterPlaceholder(step.mode), "onInput" to fun(`$event`: UniInputEvent){
                                                                    handleStepParameterInput(index, `$event`)
                                                                }), null, 40, _uA(
                                                                    "value",
                                                                    "placeholder",
                                                                    "onInput"
                                                                ))
                                                            } else {
                                                                _cC("v-if", true)
                                                            }
                                                        ))
                                                    }
                                                    ,
                                                    _cE("text", _uM("class" to "step-desc"), _tD(describeStep(step)), 1)
                                                ))
                                            }
                                            ), 128)
                                        )),
                                        _cE("view", _uM("class" to "section"), _uA(
                                            _cE("text", _uM("class" to "section-title"), "实时试算"),
                                            _cE("view", _uM("class" to "field"), _uA(
                                                _cE("text", _uM("class" to "field-label"), "测试输入值"),
                                                _cE("input", _uM("class" to "input", "value" to unref(testValue), "type" to "number", "placeholder" to "例如 9.90", "onInput" to handleTestValueInput), null, 40, _uA(
                                                    "value"
                                                ))
                                            )),
                                            _cE("view", _uM("class" to "result-row"), _uA(
                                                _cE("text", _uM("class" to "result-label"), "试算结果"),
                                                _cE("text", _uM("class" to "result-value"), _tD(if (previewResult.value == "") {
                                                    "请完善步骤"
                                                } else {
                                                    previewResult.value
                                                }
                                                ), 1)
                                            )),
                                            _cE("view", _uM("class" to "expression-box"), _uA(
                                                _cE("text", _uM("class" to "expression-label"), "表达式"),
                                                _cE("text", _uM("class" to "expression-text"), _tD(if (expressionText.value == "") {
                                                    "请先完成公式步骤"
                                                } else {
                                                    expressionText.value
                                                }
                                                ), 1)
                                            )),
                                            _cE("view", _uM("class" to "expression-box"), _uA(
                                                _cE("text", _uM("class" to "expression-label"), "中文预览"),
                                                _cE("text", _uM("class" to "expression-text"), _tD(if (displayExpressionText.value == "") {
                                                    "请先完成公式步骤"
                                                } else {
                                                    displayExpressionText.value
                                                }
                                                ), 1)
                                            ))
                                        ))
                                    ))
                                }
                            }
                        ))
                    )),
                    if (isTrue(!unref(loading) && unref(errorMessage) == "")) {
                        _cE("view", _uM("key" to 0, "class" to "footer"), _uA(
                            _cE("view", _uM("class" to "footer-btn footer-btn-light", "onClick" to handleCancel), _uA(
                                _cE("text", _uM("class" to "footer-btn-light-text"), "取消")
                            )),
                            _cE("view", _uM("class" to "footer-btn footer-btn-primary", "onClick" to handleSave), _uA(
                                _cE("text", _uM("class" to "footer-btn-primary-text"), _tD(if (unref(submitting)) {
                                    "保存中..."
                                } else {
                                    "保存公式"
                                }), 1)
                            ))
                        ))
                    } else {
                        _cC("v-if", true)
                    }
                ))
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "position" to "relative", "backgroundColor" to "#EEF2F7")), "page-scroll" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "page-content" to _pS(_uM("paddingLeft" to 10, "paddingRight" to 10, "paddingTop" to 10, "paddingBottom" to 92)), "section" to _pS(_uM("paddingLeft" to 12, "paddingRight" to 12, "paddingTop" to 12, "paddingBottom" to 12, "marginBottom" to 10, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#FFFFFF", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E3E8F0", "borderRightColor" to "#E3E8F0", "borderBottomColor" to "#E3E8F0", "borderLeftColor" to "#E3E8F0")), "section-title" to _pS(_uM("fontSize" to 16, "lineHeight" to "22px", "color" to "#111827", "fontWeight" to "bold")), "field" to _pS(_uM("marginTop" to 10)), "field-label" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#334155")), "input" to _pS(_uM("height" to 40, "marginTop" to 6, "paddingLeft" to 10, "paddingRight" to 10, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#D8DEE8", "borderRightColor" to "#D8DEE8", "borderBottomColor" to "#D8DEE8", "borderLeftColor" to "#D8DEE8", "backgroundColor" to "#FFFFFF", "fontSize" to 14, "color" to "#111827")), "textarea" to _pS(_uM("height" to 78, "marginTop" to 6, "paddingLeft" to 10, "paddingRight" to 10, "paddingTop" to 8, "paddingBottom" to 8, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#D8DEE8", "borderRightColor" to "#D8DEE8", "borderBottomColor" to "#D8DEE8", "borderLeftColor" to "#D8DEE8", "backgroundColor" to "#FFFFFF", "fontSize" to 14, "color" to "#111827")), "switch-row" to _pS(_uM("marginTop" to 12, "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between")), "choice-list" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 8)), "choice" to _pS(_uM("paddingLeft" to 10, "paddingRight" to 10, "paddingTop" to 8, "paddingBottom" to 8, "marginRight" to 6, "marginBottom" to 6, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#D8DEE8", "borderRightColor" to "#D8DEE8", "borderBottomColor" to "#D8DEE8", "borderLeftColor" to "#D8DEE8", "backgroundColor" to "#F8FAFC")), "choice-active" to _pS(_uM("borderTopColor" to "#0F172A", "borderRightColor" to "#0F172A", "borderBottomColor" to "#0F172A", "borderLeftColor" to "#0F172A", "backgroundColor" to "#0F172A")), "choice-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "16px", "color" to "#334155")), "choice-text-active" to _pS(_uM("color" to "#FFFFFF")), "tool-list" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 10)), "tool-btn" to _pS(_uM("paddingLeft" to 10, "paddingRight" to 10, "paddingTop" to 8, "paddingBottom" to 8, "marginRight" to 6, "marginBottom" to 6, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#EAF2FF")), "tool-btn-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "15px", "color" to "#1D4ED8")), "warning-box" to _pS(_uM("marginTop" to 8, "paddingLeft" to 10, "paddingRight" to 10, "paddingTop" to 8, "paddingBottom" to 8, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#FFF7ED")), "warning-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "18px", "color" to "#9A3412")), "empty-box" to _pS(_uM("marginTop" to 10, "paddingTop" to 16, "paddingBottom" to 16, "alignItems" to "center", "backgroundColor" to "#F8FAFC", "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8)), "empty-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "18px", "color" to "#64748B")), "step-card" to _pS(_uM("marginTop" to 10, "paddingLeft" to 10, "paddingRight" to 10, "paddingTop" to 10, "paddingBottom" to 10, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E3E8F0", "borderRightColor" to "#E3E8F0", "borderBottomColor" to "#E3E8F0", "borderLeftColor" to "#E3E8F0", "backgroundColor" to "#FAFBFD")), "step-head" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between")), "step-title" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "fontSize" to 14, "lineHeight" to "18px", "color" to "#111827", "fontWeight" to "bold")), "step-actions" to _pS(_uM("flexDirection" to "row", "alignItems" to "center")), "icon-btn" to _pS(_uM("width" to 28, "height" to 28, "marginLeft" to 5, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "#EEF2F7")), "icon-btn-danger" to _pS(_uM("backgroundColor" to "#FEE2E2")), "icon-btn-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "13px", "color" to "#334155")), "icon-btn-danger-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "12px", "color" to "#B91C1C")), "mini-choice" to _pS(_uM("minWidth" to 42, "height" to 30, "marginRight" to 6, "marginBottom" to 6, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "#EEF2F7")), "mini-choice-active" to _pS(_uM("backgroundColor" to "#0F172A")), "mini-choice-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "13px", "color" to "#334155")), "mini-choice-text-active" to _pS(_uM("color" to "#FFFFFF")), "round-choice" to _pS(_uM("paddingLeft" to 8, "paddingRight" to 8, "paddingTop" to 7, "paddingBottom" to 7, "marginRight" to 6, "marginBottom" to 6, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#EEF2F7")), "round-choice-active" to _pS(_uM("backgroundColor" to "#0F172A")), "round-choice-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "15px", "color" to "#334155")), "round-choice-text-active" to _pS(_uM("color" to "#FFFFFF")), "step-desc" to _pS(_uM("marginTop" to 8, "fontSize" to 12, "lineHeight" to "18px", "color" to "#64748B")), "result-row" to _pS(_uM("marginTop" to 10, "paddingLeft" to 10, "paddingRight" to 10, "paddingTop" to 10, "paddingBottom" to 10, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#F0FDF4")), "result-label" to _pS(_uM("fontSize" to 12, "lineHeight" to "16px", "color" to "#166534")), "result-value" to _pS(_uM("marginTop" to 4, "fontSize" to 22, "lineHeight" to "28px", "color" to "#166534", "fontWeight" to "bold")), "expression-box" to _pS(_uM("marginTop" to 10, "paddingLeft" to 10, "paddingRight" to 10, "paddingTop" to 10, "paddingBottom" to 10, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#F8FAFC")), "expression-label" to _pS(_uM("fontSize" to 12, "lineHeight" to "16px", "color" to "#64748B")), "expression-text" to _pS(_uM("marginTop" to 4, "fontSize" to 12, "lineHeight" to "18px", "color" to "#111827")), "state-card" to _pS(_uM("paddingTop" to 36, "paddingBottom" to 36, "paddingLeft" to 14, "paddingRight" to 14, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#FFFFFF", "alignItems" to "center")), "state-title" to _pS(_uM("fontSize" to 16, "lineHeight" to "22px", "color" to "#B91C1C", "fontWeight" to "bold")), "state-text" to _pS(_uM("marginTop" to 6, "fontSize" to 13, "lineHeight" to "18px", "color" to "#64748B", "textAlign" to "center")), "small-btn" to _pS(_uM("marginTop" to 12, "height" to 36, "paddingLeft" to 16, "paddingRight" to 16, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#0F172A")), "small-btn-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "13px", "color" to "#FFFFFF")), "footer" to _pS(_uM("position" to "absolute", "left" to 0, "right" to 0, "bottom" to 0, "flexDirection" to "row", "paddingLeft" to 10, "paddingRight" to 10, "paddingTop" to 8, "paddingBottom" to 10, "backgroundColor" to "#FFFFFF", "borderTopWidth" to 1, "borderTopStyle" to "solid", "borderTopColor" to "#E3E8F0")), "footer-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 42, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "alignItems" to "center", "justifyContent" to "center")), "footer-btn-light" to _pS(_uM("marginRight" to 8, "backgroundColor" to "#F3F6FA")), "footer-btn-primary" to _pS(_uM("backgroundColor" to "#0F172A")), "footer-btn-light-text" to _pS(_uM("fontSize" to 14, "lineHeight" to "14px", "color" to "#334155")), "footer-btn-primary-text" to _pS(_uM("fontSize" to 14, "lineHeight" to "14px", "color" to "#FFFFFF")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
