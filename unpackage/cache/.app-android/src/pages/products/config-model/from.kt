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
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesProductsConfigModelFrom : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesProductsConfigModelFrom) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesProductsConfigModelFrom
            val _cache = __ins.renderCache
            val resource = ref("discount")
            val recordId = ref("")
            val parentAttributeTypeId = ref("")
            val parentAttributeTypeName = ref("")
            val formMode = ref("create")
            val leaveSignal = ref(0)
            val submitting = ref(false)
            val initialData = ref<UTSJSONObject>(_uO())
            fun getStringField(obj: UTSJSONObject, key: String, fallback: String = ""): String {
                val value = obj[key]
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
            fun gen_getBooleanField_fn(obj: UTSJSONObject, key: String): Boolean {
                val text = getStringField(obj, key).toLowerCase()
                return text == "true" || text == "1" || text == "yes"
            }
            val getBooleanField = ::gen_getBooleanField_fn
            fun gen_resourceBasePath_fn(): String {
                if (resource.value == "attribute-type") {
                    return attributeTypesPath()
                }
                if (resource.value == "attribute-value") {
                    return attributeValuesPath()
                }
                if (resource.value == "barcode-sequence") {
                    return barcodeSequencesPath()
                }
                return productDiscountsPath()
            }
            val resourceBasePath = ::gen_resourceBasePath_fn
            fun gen_resourceTitle_fn(): String {
                if (resource.value == "attribute-type") {
                    return "属性类型"
                }
                if (resource.value == "attribute-value") {
                    return "属性值"
                }
                if (resource.value == "barcode-sequence") {
                    return "条形码序列"
                }
                return "商品折扣"
            }
            val resourceTitle = ::gen_resourceTitle_fn
            fun gen_refreshStorageKey_fn(): String {
                return "refresh:pages:products:config-model:" + resource.value
            }
            val refreshStorageKey = ::gen_refreshStorageKey_fn
            fun gen_markListRefreshNeeded_fn() {
                uni_setStorageSync(refreshStorageKey(), "1")
            }
            val markListRefreshNeeded = ::gen_markListRefreshNeeded_fn
            fun gen_goBackToList_fn() {
                leaveSignal.value = leaveSignal.value + 1
                setTimeout(fun(){
                    uni_navigateBack(NavigateBackOptions(delta = 1))
                }
                , 16)
            }
            val goBackToList = ::gen_goBackToList_fn
            fun gen_parseErrorMessage_fn(error: Any, fallback: String): String {
                var message = fallback
                if (error != null) {}
                return message
            }
            val parseErrorMessage = ::gen_parseErrorMessage_fn
            fun gen_twoDigit_fn(value: Number): String {
                if (value < 10) {
                    return "0" + value.toString(10)
                }
                return value.toString(10)
            }
            val twoDigit = ::gen_twoDigit_fn
            fun gen_nowDatetimeText_fn(): String {
                val now = Date()
                val y = now.getFullYear().toString(10)
                val m = twoDigit(now.getMonth() + 1)
                val d = twoDigit(now.getDate())
                val h = twoDigit(now.getHours())
                val mi = twoDigit(now.getMinutes())
                return y + "-" + m + "-" + d + " " + h + ":" + mi
            }
            val nowDatetimeText = ::gen_nowDatetimeText_fn
            fun gen_defaultInitialData_fn(): UTSJSONObject {
                if (resource.value == "discount") {
                    return _uO("name" to "", "discount_type" to "PERCENTAGE", "discount_percentage" to "10", "discount_amount" to "0", "min_quantity" to "1", "start_date" to nowDatetimeText(), "end_date" to "2099-12-31 23:59", "is_permanent" to false, "status" to "DRAFT", "priority" to "1", "description" to "")
                }
                if (resource.value == "attribute-value") {
                    return _uO("attribute_type" to parentAttributeTypeId.value, "attribute_type_name" to parentAttributeTypeName.value, "value" to "", "value_en" to "", "code" to "", "color_hex" to "", "display_order" to "0")
                }
                if (resource.value == "barcode-sequence") {
                    return _uO("sequence_name" to "auto_product_barcode", "prefix" to "690", "current_number" to "1", "min_number" to "1", "max_number" to "9999999", "description" to "")
                }
                return _uO("name" to "", "name_en" to "", "code" to "", "display_order" to "0", "description" to "")
            }
            val defaultInitialData = ::gen_defaultInitialData_fn
            fun gen_normalizeDetail_fn(data: UTSJSONObject): UTSJSONObject {
                val result = defaultInitialData()
                if (resource.value == "discount") {
                    result["name"] = getStringField(data, "name")
                    result["discount_type"] = getStringField(data, "discount_type", "PERCENTAGE")
                    result["discount_percentage"] = getStringField(data, "discount_percentage", "10")
                    result["discount_amount"] = getStringField(data, "discount_amount", "0")
                    result["min_quantity"] = getStringField(data, "min_quantity", "1")
                    result["start_date"] = getStringField(data, "start_date")
                    result["end_date"] = getStringField(data, "end_date")
                    result["is_permanent"] = getBooleanField(data, "is_permanent")
                    result["status"] = getStringField(data, "status", "DRAFT")
                    result["priority"] = getStringField(data, "priority", "1")
                    result["description"] = getStringField(data, "description")
                    return result
                }
                if (resource.value == "attribute-value") {
                    result["attribute_type"] = getStringField(data, "attribute_type")
                    result["attribute_type_name"] = getStringField(data, "attribute_type_name")
                    result["value"] = getStringField(data, "value")
                    result["value_en"] = getStringField(data, "value_en")
                    result["code"] = getStringField(data, "code")
                    result["color_hex"] = getStringField(data, "color_hex")
                    result["display_order"] = getStringField(data, "display_order", "0")
                    return result
                }
                if (resource.value == "barcode-sequence") {
                    result["sequence_name"] = getStringField(data, "sequence_name", "auto_product_barcode")
                    result["prefix"] = getStringField(data, "prefix", "690")
                    result["current_number"] = getStringField(data, "current_number", "1")
                    result["min_number"] = getStringField(data, "min_number", "1")
                    result["max_number"] = getStringField(data, "max_number", "9999999")
                    result["description"] = getStringField(data, "description")
                    return result
                }
                result["name"] = getStringField(data, "name")
                result["name_en"] = getStringField(data, "name_en")
                result["code"] = getStringField(data, "code")
                result["display_order"] = getStringField(data, "display_order", "0")
                result["description"] = getStringField(data, "description")
                return result
            }
            val normalizeDetail = ::gen_normalizeDetail_fn
            fun gen_loadDetail_fn(idText: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (idText == "") {
                            return@w1
                        }
                        try {
                            val detail = await(getProductConfigDetail(resourceBasePath(), idText))
                            initialData.value = normalizeDetail(detail)
                        }
                         catch (error: Throwable) {
                            showErrorToast(parseErrorMessage(error, resourceTitle() + "详情加载失败"))
                        }
                })
            }
            val loadDetail = ::gen_loadDetail_fn
            fun gen_buildAttributeTypeOption_fn(item: UTSJSONObject): UTSJSONObject {
                return _uO("value" to getStringField(item, "id"), "text" to (getStringField(item, "name") + " (" + getStringField(item, "code") + ")"))
            }
            val buildAttributeTypeOption = ::gen_buildAttributeTypeOption_fn
            fun gen_fetchAttributeTypeOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        val keyword = getStringField(params, "keyword")
                        val response = await(getProductConfigList(attributeTypesPath(), if (keyword == "") {
                            null
                        } else {
                            keyword
                        }
                        , 1, 50))
                        val rawResults = response["results"]
                        val source = if (rawResults == null) {
                            (_uA<UTSJSONObject>())
                        } else {
                            (rawResults as UTSArray<UTSJSONObject>)
                        }
                        val result: UTSArray<UTSJSONObject> = _uA()
                        run {
                            var index: Number = 0
                            while(index < source.length){
                                result.push(buildAttributeTypeOption(source[index]))
                                index += 1
                            }
                        }
                        return@w1 _uO("data" to result, "total" to result.length)
                })
            }
            val fetchAttributeTypeOptions = ::gen_fetchAttributeTypeOptions_fn
            fun gen_fetchDiscountTypeOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        return@w1 _uO("data" to _uA<UTSJSONObject>(_uO("value" to "PERCENTAGE", "text" to "百分比折扣"), _uO("value" to "FIXED_AMOUNT", "text" to "固定金额折扣")), "total" to 2)
                })
            }
            val fetchDiscountTypeOptions = ::gen_fetchDiscountTypeOptions_fn
            fun gen_fetchDiscountStatusOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        return@w1 _uO("data" to _uA<UTSJSONObject>(_uO("value" to "DRAFT", "text" to "草稿"), _uO("value" to "ACTIVE", "text" to "启用"), _uO("value" to "INACTIVE", "text" to "停用"), _uO("value" to "EXPIRED", "text" to "已过期")), "total" to 4)
                })
            }
            val fetchDiscountStatusOptions = ::gen_fetchDiscountStatusOptions_fn
            fun gen_buildPayload_fn(formData: UTSJSONObject): UTSJSONObject {
                if (resource.value == "discount") {
                    return _uO("name" to getStringField(formData, "name"), "discount_type" to getStringField(formData, "discount_type", "PERCENTAGE"), "discount_percentage" to getStringField(formData, "discount_percentage", "0"), "discount_amount" to getStringField(formData, "discount_amount", "0"), "min_quantity" to parseInt(getStringField(formData, "min_quantity", "1")), "start_date" to getStringField(formData, "start_date"), "end_date" to getStringField(formData, "end_date"), "is_permanent" to getBooleanField(formData, "is_permanent"), "status" to getStringField(formData, "status", "DRAFT"), "priority" to parseInt(getStringField(formData, "priority", "1")), "description" to getStringField(formData, "description"))
                }
                if (resource.value == "attribute-value") {
                    return _uO("attribute_type" to getStringField(formData, "attribute_type"), "value" to getStringField(formData, "value"), "value_en" to getStringField(formData, "value_en"), "code" to getStringField(formData, "code"), "color_hex" to getStringField(formData, "color_hex"), "display_order" to parseInt(getStringField(formData, "display_order", "0")))
                }
                if (resource.value == "barcode-sequence") {
                    return _uO("sequence_name" to getStringField(formData, "sequence_name"), "prefix" to getStringField(formData, "prefix"), "current_number" to parseInt(getStringField(formData, "current_number", "1")), "min_number" to parseInt(getStringField(formData, "min_number", "1")), "max_number" to parseInt(getStringField(formData, "max_number", "9999999")), "description" to getStringField(formData, "description"))
                }
                return _uO("name" to getStringField(formData, "name"), "name_en" to getStringField(formData, "name_en"), "code" to getStringField(formData, "code"), "display_order" to parseInt(getStringField(formData, "display_order", "0")), "description" to getStringField(formData, "description"))
            }
            val buildPayload = ::gen_buildPayload_fn
            fun gen_persistForm_fn(payload: UTSJSONObject): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (submitting.value) {
                            return@w1
                        }
                        val formDataValue = payload["formData"]
                        val formData = if (formDataValue == null) {
                            (_uO())
                        } else {
                            (formDataValue as UTSJSONObject)
                        }
                        submitting.value = true
                        uni_showLoading(ShowLoadingOptions(title = "保存中...", mask = true))
                        try {
                            val body = buildPayload(formData)
                            if (formMode.value == "edit") {
                                await(updateProductConfig(resourceBasePath(), recordId.value, body))
                            } else {
                                val created = await(createProductConfig(resourceBasePath(), body))
                                recordId.value = getStringField(created, "id")
                                formMode.value = "edit"
                            }
                            uni_showToast(ShowToastOptions(title = takeLatestResponseMessage("保存成功"), icon = "success"))
                            markListRefreshNeeded()
                            goBackToList()
                        }
                         catch (error: Throwable) {
                            showErrorToast(parseErrorMessage(error, "保存失败"))
                        }
                         finally {
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
                uni_showToast(ShowToastOptions(title = "请先到属性类型页面新增", icon = "none", duration = 3500))
            }
            val handleBottomSelectAdd = ::gen_handleBottomSelectAdd_fn
            fun gen_handleBottomSelectEdit_fn(payload: UTSJSONObject) {
                uni_showToast(ShowToastOptions(title = "请先到属性类型页面编辑", icon = "none", duration = 3500))
            }
            val handleBottomSelectEdit = ::gen_handleBottomSelectEdit_fn
            val homePath = computed(fun(): String {
                var path = "/pages/products/config-model/index?resource=" + resource.value
                if (resource.value == "attribute-value" && parentAttributeTypeId.value != "") {
                    path = path + "&attribute_type=" + parentAttributeTypeId.value + "&attribute_type_name=" + parentAttributeTypeName.value
                }
                return path
            }
            )
            val pageTitle = computed(fun(): String {
                val prefix = if (formMode.value == "edit") {
                    "编辑"
                } else {
                    "新建"
                }
                return prefix + resourceTitle()
            }
            )
            val formSections = computed(fun(): UTSArray<UTSJSONObject> {
                if (resource.value == "discount") {
                    return _uA(
                        _uO("key" to "base", "title" to "折扣信息", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "name", "label" to "折扣名称", "type" to "input", "required" to true, "placeholder" to "请输入折扣名称"), _uO("key" to "discount_type", "label" to "折扣类型", "type" to "bottomSelect", "title" to "选择折扣类型", "textKey" to "discount_type_text", "showAddAction" to false, "showEditAction" to false, "fetchData" to fetchDiscountTypeOptions), _uO("key" to "discount_percentage", "label" to "折扣百分比", "type" to "number", "placeholder" to "例如 10"), _uO("key" to "discount_amount", "label" to "折扣金额", "type" to "number", "placeholder" to "例如 5.00"), _uO("key" to "min_quantity", "label" to "最低购买数量", "type" to "number", "required" to true))),
                        _uO("key" to "time", "title" to "有效期", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "start_date", "label" to "开始时间", "type" to "datetime", "required" to true, "showTime" to true), _uO("key" to "end_date", "label" to "结束时间", "type" to "datetime", "required" to true, "showTime" to true), _uO("key" to "is_permanent", "label" to "永久有效", "type" to "switch"), _uO("key" to "status", "label" to "状态", "type" to "bottomSelect", "title" to "选择状态", "showAddAction" to false, "showEditAction" to false, "fetchData" to fetchDiscountStatusOptions), _uO("key" to "priority", "label" to "优先级", "type" to "number"), _uO("key" to "description", "label" to "说明", "type" to "textarea")))
                    )
                }
                if (resource.value == "attribute-value") {
                    return _uA(
                        _uO("key" to "base", "title" to "属性值", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "attribute_type", "label" to "属性类型", "type" to "bottomSelect", "title" to "选择属性类型", "required" to true, "textKey" to "attribute_type_name", "showAddAction" to true, "showEditAction" to true, "addPath" to "/pages/products/config-model/from?resource=attribute-type", "editPath" to "/pages/products/config-model/from?resource=attribute-type", "fetchData" to fetchAttributeTypeOptions), _uO("key" to "value", "label" to "属性值", "type" to "input", "required" to true, "placeholder" to "例如 红色、XL"), _uO("key" to "value_en", "label" to "英文值", "type" to "input"), _uO("key" to "code", "label" to "编码", "type" to "input", "required" to true), _uO("key" to "color_hex", "label" to "颜色代码", "type" to "input", "placeholder" to "例如 #FF0000"), _uO("key" to "display_order", "label" to "排序", "type" to "number")))
                    )
                }
                if (resource.value == "barcode-sequence") {
                    return _uA(
                        _uO("key" to "base", "title" to "条形码序列", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "sequence_name", "label" to "序列名称", "type" to "input", "required" to true), _uO("key" to "prefix", "label" to "条形码前缀", "type" to "input", "required" to true), _uO("key" to "current_number", "label" to "当前序号", "type" to "number", "required" to true), _uO("key" to "min_number", "label" to "最小序号", "type" to "number", "required" to true), _uO("key" to "max_number", "label" to "最大序号", "type" to "number", "required" to true), _uO("key" to "description", "label" to "说明", "type" to "textarea")))
                    )
                }
                return _uA(
                    _uO("key" to "base", "title" to "属性类型", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "name", "label" to "属性名称", "type" to "input", "required" to true, "placeholder" to "例如 颜色、尺寸"), _uO("key" to "name_en", "label" to "英文名称", "type" to "input"), _uO("key" to "code", "label" to "属性编码", "type" to "input", "required" to true), _uO("key" to "display_order", "label" to "排序", "type" to "number"), _uO("key" to "description", "label" to "描述", "type" to "textarea")))
                )
            }
            )
            onLoad(fun(event: OnLoadOptions){
                leaveSignal.value = 0
                val resourceValue = event["resource"]
                resource.value = if (resourceValue == null) {
                    "discount"
                } else {
                    (resourceValue as String)
                }
                val attributeTypeValue = event["attribute_type"]
                parentAttributeTypeId.value = if (attributeTypeValue == null) {
                    ""
                } else {
                    (attributeTypeValue as String)
                }
                val attributeTypeNameValue = event["attribute_type_name"]
                parentAttributeTypeName.value = if (attributeTypeNameValue == null) {
                    ""
                } else {
                    (attributeTypeNameValue as String)
                }
                val idValue = event["id"]
                recordId.value = if (idValue == null) {
                    ""
                } else {
                    (idValue as String)
                }
                formMode.value = if (recordId.value == "") {
                    "create"
                } else {
                    "edit"
                }
                initialData.value = defaultInitialData()
                if (formMode.value == "edit") {
                    loadDetail(recordId.value)
                }
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversaForm = resolveEasyComponent("lili-UniversaForm", GenUniModulesLiliUniversaFormComponentsLiliUniversaFormLiliUniversaFormClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to pageTitle.value, "showBack" to true, "showSearch" to false, "showHome" to true, "homePath" to homePath.value, "backgroundColor" to "#EEF2F7"), null, 8, _uA(
                        "title",
                        "homePath"
                    )),
                    _cE("view", _uM("class" to "page-content"), _uA(
                        _cV(_component_lili_UniversaForm, _uM("mode" to unref(formMode), "formSections" to formSections.value, "initialData" to unref(initialData), "leaveSignal" to unref(leaveSignal), "onSubmit" to handleSubmit, "onCancel" to handleCancel, "onDiscardLeave" to handleDiscardLeave, "onSaveRequest" to handleSaveRequest, "onDirtyChange" to handleDirtyChange, "onBottomSelectAdd" to handleBottomSelectAdd, "onBottomSelectEdit" to handleBottomSelectEdit), null, 8, _uA(
                            "mode",
                            "formSections",
                            "initialData",
                            "leaveSignal"
                        ))
                    ))
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "position" to "relative", "backgroundColor" to "#EEF2F7")), "page-content" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
