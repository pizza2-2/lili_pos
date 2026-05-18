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
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesInventoryLocationsFrom : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesInventoryLocationsFrom) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesInventoryLocationsFrom
            val _cache = __ins.renderCache
            val refreshStorageKey = "refresh:pages:inventory-locations:index"
            val formMode = ref("create")
            val itemId = ref("")
            val leaveSignal = ref(0)
            val submitting = ref(false)
            val initialData = ref<UTSJSONObject>(_uO("name" to "", "code" to "", "location_type" to "WAREHOUSE", "location_type_text" to "仓库", "address" to "", "is_active" to "true", "is_active_text" to "启用"))
            val typeOptions = ref(_uA<SelectOption__10>(SelectOption__10(value = "SHOP", text = "店铺"), SelectOption__10(value = "WAREHOUSE", text = "仓库"), SelectOption__10(value = "TRANSIT", text = "在途"), SelectOption__10(value = "SUPPLIER", text = "供应商处")))
            val activeOptions = ref(_uA<SelectOption__10>(SelectOption__10(value = "true", text = "启用"), SelectOption__10(value = "false", text = "停用")))
            fun getStringField(obj: UTSJSONObject, key: String, fallback: String = ""): String {
                val value = obj[key]
                if (value == null) {
                    return fallback
                }
                return "" + value
            }
            fun gen_parseErrorMessage_fn(error: Any, fallback: String): String {
                if (error == null) {
                    return fallback
                }
                val text = JSON.stringify(error)
                if (text == null || text == "") {
                    return fallback
                }
                return text
            }
            val parseErrorMessage = ::gen_parseErrorMessage_fn
            fun gen_buildSelectResponse_fn(source: UTSArray<SelectOption__10>, params: UTSJSONObject): UTSJSONObject {
                val id = getStringField(params, "id")
                val result: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < source.length){
                        val option = source[index]
                        if (id != "" && option.value != id) {
                            index += 1
                            continue
                        }
                        result.push(_uO("value" to option.value, "text" to option.text))
                        index += 1
                    }
                }
                return _uO("data" to result, "results" to result, "total" to result.length, "total_count" to result.length)
            }
            val buildSelectResponse = ::gen_buildSelectResponse_fn
            fun gen_fetchTypeOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        return@w1 buildSelectResponse(typeOptions.value, params)
                })
            }
            val fetchTypeOptions = ::gen_fetchTypeOptions_fn
            fun gen_fetchActiveOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        return@w1 buildSelectResponse(activeOptions.value, params)
                })
            }
            val fetchActiveOptions = ::gen_fetchActiveOptions_fn
            val formSections = ref(_uA<UTSJSONObject>(_uO("key" to "base", "title" to "基础信息", "description" to "", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "name", "label" to "位置名称", "type" to "input", "required" to true, "placeholder" to "请输入库存位置名称"), _uO("key" to "code", "label" to "位置编码", "type" to "input", "required" to true, "placeholder" to "请输入位置编码"), _uO("key" to "location_type", "textKey" to "location_type_text", "label" to "位置类型", "type" to "bottomSelect", "title" to "选择位置类型", "placeholder" to "请选择位置类型", "showAddAction" to false, "showEditAction" to false, "fetchData" to fetchTypeOptions), _uO("key" to "address", "label" to "地址", "type" to "textarea", "placeholder" to "请输入地址"), _uO("key" to "is_active", "textKey" to "is_active_text", "label" to "启用状态", "type" to "bottomSelect", "title" to "选择启用状态", "placeholder" to "请选择启用状态", "showAddAction" to false, "showEditAction" to false, "fetchData" to fetchActiveOptions)))))
            val pageTitle = computed(fun(): String {
                return if (formMode.value == "edit") {
                    "编辑库存位置"
                } else {
                    "新建库存位置"
                }
            }
            )
            fun gen_markRefreshNeeded_fn() {
                uni_setStorageSync(refreshStorageKey, "1")
            }
            val markRefreshNeeded = ::gen_markRefreshNeeded_fn
            fun gen_goBackToList_fn() {
                leaveSignal.value = leaveSignal.value + 1
                setTimeout(fun(){
                    uni_navigateBack(NavigateBackOptions(delta = 1, fail = fun(_){
                        uni_navigateTo(NavigateToOptions(url = "/pages/inventory-locations/index"))
                    }
                    ))
                }
                , 16)
            }
            val goBackToList = ::gen_goBackToList_fn
            fun gen_buildPayload_fn(data: UTSJSONObject): InventoryMutationData {
                return InventoryMutationData(payload = _uO("name" to getStringField(data, "name"), "code" to getStringField(data, "code"), "location_type" to getStringField(data, "location_type", "WAREHOUSE"), "address" to getStringField(data, "address"), "is_active" to (getStringField(data, "is_active", "true") == "true")))
            }
            val buildPayload = ::gen_buildPayload_fn
            fun gen_optionText_fn(source: UTSArray<SelectOption__10>, value: String, fallback: String): String {
                run {
                    var index: Number = 0
                    while(index < source.length){
                        val option = source[index]
                        if (option.value == value) {
                            return option.text
                        }
                        index += 1
                    }
                }
                return fallback
            }
            val optionText = ::gen_optionText_fn
            fun gen_loadDetail_fn(idText: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (idText == "") {
                            return@w1
                        }
                        try {
                            val detail = await(getInventoryLocationDetail(idText))
                            val typeValue = getStringField(detail, "location_type", "WAREHOUSE")
                            var typeText = getStringField(detail, "location_type_display")
                            if (typeText == "") {
                                typeText = optionText(typeOptions.value, typeValue, "仓库")
                            }
                            val activeValue = if (getStringField(detail, "is_active") == "true") {
                                "true"
                            } else {
                                "false"
                            }
                            initialData.value = _uO("name" to getStringField(detail, "name"), "code" to getStringField(detail, "code"), "location_type" to typeValue, "location_type_text" to typeText, "address" to getStringField(detail, "address"), "is_active" to activeValue, "is_active_text" to optionText(activeOptions.value, activeValue, "启用"))
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error, "库存位置详情加载失败"), icon = "none"))
                        }
                })
            }
            val loadDetail = ::gen_loadDetail_fn
            fun gen_persistForm_fn(payload: UTSJSONObject): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (submitting.value) {
                            return@w1
                        }
                        val rawData = payload["formData"]
                        val data = if (rawData == null) {
                            (_uO())
                        } else {
                            (rawData as UTSJSONObject)
                        }
                        if (getStringField(data, "name") == "" || getStringField(data, "code") == "") {
                            uni_showToast(ShowToastOptions(title = "请填写位置名称和位置编码", icon = "none"))
                            return@w1
                        }
                        val actionText = if (formMode.value == "edit") {
                            "保存库存位置"
                        } else {
                            "创建库存位置"
                        }
                        submitting.value = true
                        uni_showLoading(ShowLoadingOptions(title = actionText + "中...", mask = true))
                        try {
                            if (formMode.value == "edit" && itemId.value != "") {
                                await(updateInventoryLocation(itemId.value, buildPayload(data)))
                            } else {
                                await(createInventoryLocation(buildPayload(data)))
                            }
                            markRefreshNeeded()
                            uni_showToast(ShowToastOptions(title = takeLatestResponseMessage(actionText + "成功"), icon = "success"))
                            goBackToList()
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error, actionText + "失败"), icon = "none"))
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
                val changed = payload["hasChanges"]
                if (changed != null && (changed as Boolean)) {
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
                uni_showToast(ShowToastOptions(title = "该字段不支持新增", icon = "none"))
            }
            val handleBottomSelectAdd = ::gen_handleBottomSelectAdd_fn
            fun gen_handleBottomSelectEdit_fn(payload: UTSJSONObject) {
                uni_showToast(ShowToastOptions(title = "该字段不支持编辑", icon = "none"))
            }
            val handleBottomSelectEdit = ::gen_handleBottomSelectEdit_fn
            onLoad(fun(query: OnLoadOptions){
                val idValue = query["id"]
                if (idValue != null && idValue != "") {
                    formMode.value = "edit"
                    itemId.value = idValue
                    loadDetail(idValue)
                }
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversaForm = resolveEasyComponent("lili-UniversaForm", GenUniModulesLiliUniversaFormComponentsLiliUniversaFormLiliUniversaFormClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to pageTitle.value, "showBack" to true, "showSearch" to false, "showHome" to true, "homePath" to "/pages/inventory-locations/index", "backgroundColor" to "#EEF2F7"), null, 8, _uA(
                        "title"
                    )),
                    _cE("view", _uM("class" to "page-content"), _uA(
                        _cV(_component_lili_UniversaForm, _uM("mode" to unref(formMode), "formSections" to unref(formSections), "initialData" to unref(initialData), "leaveSignal" to unref(leaveSignal), "onSubmit" to handleSubmit, "onCancel" to handleCancel, "onDiscardLeave" to handleDiscardLeave, "onSaveRequest" to handleSaveRequest, "onDirtyChange" to handleDirtyChange, "onBottomSelectAdd" to handleBottomSelectAdd, "onBottomSelectEdit" to handleBottomSelectEdit), null, 8, _uA(
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
                return _uM("page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#EEF2F7")), "page-content" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingBottom" to 0)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
