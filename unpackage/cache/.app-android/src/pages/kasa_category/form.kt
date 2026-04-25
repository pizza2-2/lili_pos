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
import io.dcloud.uniapp.extapi.hideLoading as uni_hideLoading
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.reLaunch as uni_reLaunch
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesKasaCategoryForm : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesKasaCategoryForm) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesKasaCategoryForm
            val _cache = __ins.renderCache
            val kasaCategoryListRefreshStorageKey = "refresh:pages:kasa_category:index"
            val formMode = ref("create")
            val kasaCategoryId = ref("")
            val leaveSignal = ref(0)
            val submitting = ref(false)
            val savingVisible = ref(false)
            val savingText = ref("处理中...")
            val taxRateResponse = ref<KasaCategoryTaxRatesResponse?>(null)
            val initialData = ref<UTSJSONObject>(_uO("name_cn" to "", "name_en" to "", "code" to "", "unique_kod" to "", "tax_rate" to "", "tax_rate_display" to "", "is_active" to "true", "products_count" to "0"))
            val statusOptions = ref(_uA<SelectOption__3>(SelectOption__3(value = "true", text = "启用"), SelectOption__3(value = "false", text = "停用")))
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
            fun gen_parseErrorMessage_fn(error: Any, fallback: String): String {
                var message = fallback
                if (error != null) {
                    val directMessage = (error as UTSError).message
                    if (directMessage != null && directMessage != "") {
                        message = directMessage
                    }
                    val errorText = JSON.stringify(error)
                    if (errorText != null && errorText != "") {
                        val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/kasa_category/form.uvue:89")
                        if (parsedError != null) {
                            val rawMessage = parsedError["message"]
                            if (rawMessage != null) {
                                val parsedMessage = rawMessage as String
                                if (parsedMessage != "") {
                                    message = parsedMessage
                                }
                            }
                        }
                    }
                }
                return message
            }
            val parseErrorMessage = ::gen_parseErrorMessage_fn
            fun gen_buildTaxRateLabel_fn(item: UTSJSONObject): String {
                val display = stringValue(item["tax_rate_display"])
                if (display != "") {
                    return display
                }
                val text = stringValue(item["text"])
                if (text != "") {
                    return text
                }
                val label = stringValue(item["label"])
                if (label != "") {
                    return label
                }
                val rate = stringValue(item["tax_rate"])
                if (rate != "") {
                    return rate
                }
                val value = stringValue(item["value"])
                if (value != "") {
                    return value
                }
                return ""
            }
            val buildTaxRateLabel = ::gen_buildTaxRateLabel_fn
            fun gen_buildTaxRateValue_fn(item: UTSJSONObject): String {
                val value = stringValue(item["value"])
                if (value != "") {
                    return value
                }
                val rate = stringValue(item["tax_rate"])
                if (rate != "") {
                    return rate
                }
                val id = stringValue(item["id"])
                if (id != "") {
                    return id
                }
                val code = stringValue(item["code"])
                if (code != "") {
                    return code
                }
                return buildTaxRateLabel(item)
            }
            val buildTaxRateValue = ::gen_buildTaxRateValue_fn
            fun gen_normalizeTaxRateToken_fn(value: String): String {
                if (value == "") {
                    return ""
                }
                val normalized = value.replace("%", "").trim()
                val parsed = parseFloat(normalized)
                if (isNaN(parsed)) {
                    return normalized
                }
                return parsed.toString(10)
            }
            val normalizeTaxRateToken = ::gen_normalizeTaxRateToken_fn
            fun gen_appendTaxRateOption_fn(result: UTSArray<SelectOption__3>, value: String, text: String) {
                if (value == "") {
                    return
                }
                var exists = false
                run {
                    var optionIndex: Number = 0
                    while(optionIndex < result.length){
                        if (result[optionIndex].value == value) {
                            exists = true
                            break
                        }
                        optionIndex += 1
                    }
                }
                if (!exists) {
                    result.push(SelectOption__3(value = value, text = if (text == "") {
                        value
                    } else {
                        text
                    }
                    ))
                }
            }
            val appendTaxRateOption = ::gen_appendTaxRateOption_fn
            fun gen_normalizeTaxRateOptions_fn(): UTSArray<SelectOption__3> {
                if (taxRateResponse.value == null) {
                    return _uA()
                }
                val result: UTSArray<SelectOption__3> = _uA()
                run {
                    var index: Number = 0
                    while(index < taxRateResponse.value!!.items.length){
                        val item = taxRateResponse.value!!.items[index]
                        val value = buildTaxRateValue(item)
                        if (value == "") {
                            index += 1
                            continue
                        }
                        appendTaxRateOption(result, value, buildTaxRateLabel(item))
                        index += 1
                    }
                }
                return result
            }
            val normalizeTaxRateOptions = ::gen_normalizeTaxRateOptions_fn
            fun gen_findTaxRateOptionByValue_fn(value: String): SelectOption__3? {
                if (value == "") {
                    return null
                }
                val normalizedValue = normalizeTaxRateToken(value)
                val options = normalizeTaxRateOptions()
                run {
                    var index: Number = 0
                    while(index < options.length){
                        val option = options[index]
                        if (option.value == value) {
                            return option
                        }
                        if (normalizedValue != "" && normalizeTaxRateToken(option.value) == normalizedValue) {
                            return option
                        }
                        index += 1
                    }
                }
                return null
            }
            val findTaxRateOptionByValue = ::gen_findTaxRateOptionByValue_fn
            fun gen_loadTaxRateOptions_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (taxRateResponse.value != null) {
                            return@w1
                        }
                        try {
                            taxRateResponse.value = await(getKasaCategoryTaxRates())
                            console.log(taxRateResponse.value, " at pages/kasa_category/form.uvue:219")
                        }
                         catch (error: Throwable) {
                            taxRateResponse.value = null
                        }
                })
            }
            val loadTaxRateOptions = ::gen_loadTaxRateOptions_fn
            fun gen_buildSelectResponse_fn(source: UTSArray<SelectOption__3>, params: UTSJSONObject): UTSJSONObject {
                val keyword = stringValue(params["keyword"]).toLowerCase()
                val id = stringValue(params["id"])
                val normalizedId = normalizeTaxRateToken(id)
                val result: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < source.length){
                        val option = source[index]
                        if (id != "") {
                            val exactMatched = option.value == id || option.text == id
                            val normalizedMatched = normalizedId != "" && (normalizeTaxRateToken(option.value) == normalizedId || normalizeTaxRateToken(option.text) == normalizedId)
                            if (!exactMatched && !normalizedMatched) {
                                index += 1
                                continue
                            }
                            result.push(_uO("value" to id, "text" to option.text))
                            index += 1
                            continue
                        }
                        if (keyword != "" && option.text.toLowerCase().indexOf(keyword) < 0) {
                            index += 1
                            continue
                        }
                        result.push(_uO("value" to option.value, "text" to option.text))
                        index += 1
                    }
                }
                return _uO("data" to result, "total" to result.length)
            }
            val buildSelectResponse = ::gen_buildSelectResponse_fn
            fun gen_fetchStatusOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        return@w1 buildSelectResponse(statusOptions.value, params)
                })
            }
            val fetchStatusOptions = ::gen_fetchStatusOptions_fn
            fun gen_fetchTaxRateOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        await(loadTaxRateOptions())
                        return@w1 buildSelectResponse(normalizeTaxRateOptions(), params)
                })
            }
            val fetchTaxRateOptions = ::gen_fetchTaxRateOptions_fn
            fun gen_buildInitialDataFromDetail_fn(item: KasaCategoryItem): UTSJSONObject {
                val matchedTaxRateOption = findTaxRateOptionByValue(item.tax_rate)
                val resolvedTaxRate = if (matchedTaxRateOption != null) {
                    matchedTaxRateOption.value
                } else {
                    item.tax_rate
                }
                val resolvedTaxRateDisplay = if (matchedTaxRateOption != null) {
                    matchedTaxRateOption.text
                } else {
                    item.tax_rate_display
                }
                return _uO("name_cn" to item.name_cn, "name_en" to item.name_en, "code" to item.code, "unique_kod" to item.unique_kod, "tax_rate" to resolvedTaxRate, "tax_rate_display" to resolvedTaxRateDisplay, "is_active" to if (item.is_active) {
                    "true"
                } else {
                    "false"
                }
                , "products_count" to item.products_count.toString(10))
            }
            val buildInitialDataFromDetail = ::gen_buildInitialDataFromDetail_fn
            fun gen_loadDetailData_fn(idText: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (idText == "") {
                            return@w1
                        }
                        try {
                            await(loadTaxRateOptions())
                            val detail = await(getKasaCategoryDetail(idText))
                            console.log(detail, " at pages/kasa_category/form.uvue:290")
                            initialData.value = buildInitialDataFromDetail(detail)
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error, "收银分类详情加载失败"), icon = "none"))
                        }
                })
            }
            val loadDetailData = ::gen_loadDetailData_fn
            fun gen_buildMutationPayload_fn(formDataObject: UTSJSONObject): KasaCategoryMutationData {
                val nameCn = stringValue(formDataObject["name_cn"])
                val nameEn = stringValue(formDataObject["name_en"])
                val code = stringValue(formDataObject["code"])
                val uniqueKod = stringValue(formDataObject["unique_kod"])
                val taxRate = stringValue(formDataObject["tax_rate"])
                val isActiveText = stringValue(formDataObject["is_active"], "true")
                return _uO("name_cn" to nameCn, "name_en" to nameEn, "code" to if (code == "") {
                    null
                } else {
                    code
                }
                , "unique_kod" to if (uniqueKod == "") {
                    null
                } else {
                    uniqueKod
                }
                , "tax_rate" to if (taxRate == "") {
                    null
                } else {
                    taxRate
                }
                , "is_active" to (isActiveText == "true"))
            }
            val buildMutationPayload = ::gen_buildMutationPayload_fn
            fun gen_goBackToList_fn() {
                leaveSignal.value = leaveSignal.value + 1
                setTimeout(fun(){
                    uni_navigateBack(NavigateBackOptions(delta = 1, fail = fun(_){
                        uni_reLaunch(ReLaunchOptions(url = "/pages/kasa_category/index"))
                    }
                    ))
                }
                , 16)
            }
            val goBackToList = ::gen_goBackToList_fn
            fun gen_markListRefreshNeeded_fn() {
                uni_setStorageSync(kasaCategoryListRefreshStorageKey, "1")
            }
            val markListRefreshNeeded = ::gen_markListRefreshNeeded_fn
            fun gen_persistForm_fn(payload: UTSJSONObject): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (submitting.value) {
                            return@w1
                        }
                        val formDataValue = payload["formData"]
                        val data = if (formDataValue == null) {
                            (_uO())
                        } else {
                            (formDataValue as UTSJSONObject)
                        }
                        val actionText = if (formMode.value == "edit") {
                            "保存修改"
                        } else {
                            "创建收银分类"
                        }
                        submitting.value = true
                        savingText.value = actionText + "中..."
                        savingVisible.value = true
                        uni_showLoading(ShowLoadingOptions(title = savingText.value, mask = true))
                        try {
                            val body = buildMutationPayload(data)
                            var successMessage = actionText + "成功"
                            if (formMode.value == "edit" && kasaCategoryId.value != "") {
                                await(updateKasaCategory(kasaCategoryId.value, body))
                                successMessage = takeLatestResponseMessage(successMessage)
                            } else {
                                val created = await(createKasaCategory(body))
                                successMessage = takeLatestResponseMessage(successMessage)
                                kasaCategoryId.value = created.id.toString(10)
                            }
                            markListRefreshNeeded()
                            uni_showToast(ShowToastOptions(title = successMessage, icon = "success"))
                            goBackToList()
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error, actionText + "失败"), icon = "none"))
                        }
                         finally {
                            savingVisible.value = false
                            uni_hideLoading(null)
                            submitting.value = false
                        }
                })
            }
            val persistForm = ::gen_persistForm_fn
            fun gen_handleSubmit_fn(payload: UTSJSONObject): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        await(persistForm(payload))
                })
            }
            val handleSubmit = ::gen_handleSubmit_fn
            fun gen_handleSaveRequest_fn(payload: UTSJSONObject): UTSPromise<Unit> {
                return wrapUTSPromise(suspend {
                        await(persistForm(payload))
                })
            }
            val handleSaveRequest = ::gen_handleSaveRequest_fn
            fun gen_handleCancel_fn(payload: UTSJSONObject) {
                val hasChangesValue = payload["hasChanges"]
                val changed = hasChangesValue != null && (hasChangesValue as Boolean)
                if (changed) {
                    return
                }
                goBackToList()
            }
            val handleCancel = ::gen_handleCancel_fn
            fun gen_handleDiscardLeave_fn(payload: UTSJSONObject) {
                goBackToList()
            }
            val handleDiscardLeave = ::gen_handleDiscardLeave_fn
            fun gen_handleDirtyChange_fn(value: Boolean) {}
            val handleDirtyChange = ::gen_handleDirtyChange_fn
            fun gen_handleBottomSelectAdd_fn(payload: UTSJSONObject) {
                uni_showToast(ShowToastOptions(title = "当前字段不支持新增", icon = "none"))
            }
            val handleBottomSelectAdd = ::gen_handleBottomSelectAdd_fn
            fun gen_handleBottomSelectEdit_fn(payload: UTSJSONObject) {
                uni_showToast(ShowToastOptions(title = "当前字段不支持编辑", icon = "none"))
            }
            val handleBottomSelectEdit = ::gen_handleBottomSelectEdit_fn
            val formSections = ref(_uA<UTSJSONObject>(_uO("key" to "base", "title" to "基础信息", "description" to "", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "name_cn", "label" to "中文名", "type" to "input", "required" to true, "placeholder" to "请输入中文名"), _uO("key" to "name_en", "label" to "英文名", "type" to "input", "placeholder" to "请输入英文名"), _uO("key" to "unique_kod", "label" to "唯一编码", "type" to "input", "placeholder" to "请输入唯一编码"), _uO("key" to "tax_rate", "label" to "税率", "type" to "bottomSelect", "textKey" to "tax_rate_display", "title" to "选择税率", "placeholder" to "请选择税率", "showAddAction" to false, "showEditAction" to false, "fetchData" to fetchTaxRateOptions))), _uO("key" to "status", "title" to "状态设置", "description" to "", "defaultOpen" to false, "fields" to _uA<UTSJSONObject>(_uO("key" to "is_active", "label" to "启用状态", "type" to "bottomSelect", "title" to "选择启用状态", "placeholder" to "请选择启用状态", "showAddAction" to false, "showEditAction" to false, "fetchData" to fetchStatusOptions), _uO("key" to "products_count", "label" to "关联商品数", "type" to "input", "readonly" to true, "editOnly" to true, "placeholder" to "自动统计")))))
            val pageTitle = computed(fun(): String {
                return if (formMode.value == "edit") {
                    "编辑收银分类"
                } else {
                    "新建收银分类"
                }
            }
            )
            onLoad(fun(event: OnLoadOptions){
                leaveSignal.value = 0
                val idValue = event["id"]
                kasaCategoryId.value = if (idValue == null) {
                    ""
                } else {
                    (idValue as String)
                }
                formMode.value = if (kasaCategoryId.value == "") {
                    "create"
                } else {
                    "edit"
                }
                loadTaxRateOptions()
                if (formMode.value == "edit") {
                    loadDetailData(kasaCategoryId.value)
                }
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversaForm = resolveEasyComponent("lili-UniversaForm", GenUniModulesLiliUniversaFormComponentsLiliUniversaFormLiliUniversaFormClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to pageTitle.value, "showBack" to true, "showSearch" to false, "showHome" to true, "homePath" to "/pages/kasa_category/index", "backgroundColor" to "#EEF2F7"), null, 8, _uA(
                        "title"
                    )),
                    _cE("view", _uM("class" to "page-content"), _uA(
                        _cV(_component_lili_UniversaForm, _uM("mode" to unref(formMode), "formSections" to unref(formSections), "initialData" to unref(initialData), "leaveSignal" to unref(leaveSignal), "onSubmit" to handleSubmit, "onCancel" to handleCancel, "onDiscardLeave" to handleDiscardLeave, "onSaveRequest" to handleSaveRequest, "onDirtyChange" to handleDirtyChange, "onBottomSelectAdd" to handleBottomSelectAdd, "onBottomSelectEdit" to handleBottomSelectEdit), null, 8, _uA(
                            "mode",
                            "formSections",
                            "initialData",
                            "leaveSignal"
                        ))
                    )),
                    if (isTrue(unref(savingVisible))) {
                        _cE("view", _uM("key" to 0, "class" to "page-saving-mask"), _uA(
                            _cE("view", _uM("class" to "page-saving-card"), _uA(
                                _cE("text", _uM("class" to "page-saving-text"), _tD(unref(savingText)), 1)
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "position" to "relative", "backgroundColor" to "#EEF2F7")), "page-content" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingLeft" to 0, "paddingRight" to 0, "paddingBottom" to 0)), "page-saving-mask" to _pS(_uM("position" to "absolute", "left" to 0, "top" to 0, "right" to 0, "bottom" to 0, "zIndex" to 9999, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "rgba(15,23,42,0.28)")), "page-saving-card" to _pS(_uM("height" to 44, "paddingLeft" to 16, "paddingRight" to 16, "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "rgba(15,23,42,0.86)")), "page-saving-text" to _pS(_uM("fontSize" to 13, "lineHeight" to "16px", "color" to "#FFFFFF")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
