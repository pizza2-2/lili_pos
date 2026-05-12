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
open class GenPagesInventoryManagementFrom : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesInventoryManagementFrom) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesInventoryManagementFrom
            val _cache = __ins.renderCache
            val refreshStorageKey = "refresh:pages:inventory-management:index"
            val leaveSignal = ref(0)
            val submitting = ref(false)
            val stockId = ref("")
            val initialData = ref<UTSJSONObject>(_uO("stock_id" to "", "product_name" to "", "quantity_change" to "", "transaction_type" to "ADJUSTMENT", "unit_cost" to "", "notes" to ""))
            val typeOptions = ref(_uA<SelectOption__8>(SelectOption__8(value = "ADJUSTMENT", text = "盘点调整"), SelectOption__8(value = "DAMAGE", text = "损坏"), SelectOption__8(value = "LOSS", text = "丢失"), SelectOption__8(value = "INITIAL", text = "初始库存")))
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
            fun gen_buildSelectResponse_fn(source: UTSArray<SelectOption__8>, params: UTSJSONObject): UTSJSONObject {
                val keyword = getStringField(params, "keyword").toLowerCase()
                val result: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < source.length){
                        val option = source[index]
                        if (keyword == "" || option.text.toLowerCase().indexOf(keyword) >= 0) {
                            result.push(_uO("value" to option.value, "text" to option.text))
                        }
                        index += 1
                    }
                }
                return _uO("results" to result, "total_count" to result.length)
            }
            val buildSelectResponse = ::gen_buildSelectResponse_fn
            fun gen_fetchTypeOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        return@w1 buildSelectResponse(typeOptions.value, params)
                })
            }
            val fetchTypeOptions = ::gen_fetchTypeOptions_fn
            val formSections = ref(_uA<UTSJSONObject>(_uO("key" to "base", "title" to "调整信息", "description" to "", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "product_name", "label" to "商品", "type" to "input", "disabled" to true, "placeholder" to "从库存列表进入后自动带出"), _uO("key" to "stock_id", "label" to "库存ID", "type" to "input", "required" to true, "disabled" to true), _uO("key" to "quantity_change", "label" to "调整数量", "type" to "input", "required" to true, "placeholder" to "正数入库，负数出库"), _uO("key" to "transaction_type", "label" to "调整类型", "type" to "bottomSelect", "title" to "选择调整类型", "placeholder" to "请选择调整类型", "showAddAction" to false, "showEditAction" to false, "fetchData" to fetchTypeOptions), _uO("key" to "unit_cost", "label" to "单位成本", "type" to "input", "placeholder" to "可选"), _uO("key" to "notes", "label" to "备注", "type" to "textarea", "placeholder" to "请输入调整原因")))))
            fun gen_markRefreshNeeded_fn() {
                uni_setStorageSync(refreshStorageKey, "1")
            }
            val markRefreshNeeded = ::gen_markRefreshNeeded_fn
            fun gen_goBackToList_fn() {
                leaveSignal.value = leaveSignal.value + 1
                setTimeout(fun(){
                    uni_navigateBack(NavigateBackOptions(delta = 1, fail = fun(_){
                        uni_navigateTo(NavigateToOptions(url = "/pages/inventory-management/index"))
                    }
                    ))
                }
                , 16)
            }
            val goBackToList = ::gen_goBackToList_fn
            fun gen_loadDetail_fn(idText: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (idText == "") {
                            return@w1
                        }
                        try {
                            val detail = await(getInventoryStockDetail(idText))
                            initialData.value = _uO("stock_id" to idText, "product_name" to (getStringField(detail, "product_name", "-") + " / " + getStringField(detail, "location_name", "-")), "quantity_change" to "", "transaction_type" to "ADJUSTMENT", "unit_cost" to getStringField(detail, "average_cost"), "notes" to "")
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error, "库存详情加载失败"), icon = "none"))
                        }
                })
            }
            val loadDetail = ::gen_loadDetail_fn
            fun gen_buildPayload_fn(data: UTSJSONObject): StockAdjustmentData {
                val idText = getStringField(data, "stock_id", stockId.value)
                val quantityText = getStringField(data, "quantity_change")
                return StockAdjustmentData(stock_id = parseInt(idText), quantity_change = parseInt(quantityText), transaction_type = getStringField(data, "transaction_type", "ADJUSTMENT"), unit_cost = getStringField(data, "unit_cost"), notes = getStringField(data, "notes"))
            }
            val buildPayload = ::gen_buildPayload_fn
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
                        val body = buildPayload(data)
                        if (isNaN(body.stock_id) || isNaN(body.quantity_change) || body.quantity_change == 0) {
                            uni_showToast(ShowToastOptions(title = "请输入有效的调整数量", icon = "none"))
                            return@w1
                        }
                        submitting.value = true
                        uni_showLoading(ShowLoadingOptions(title = "调整库存中...", mask = true))
                        try {
                            await(adjustInventoryStock(body))
                            markRefreshNeeded()
                            uni_showToast(ShowToastOptions(title = takeLatestResponseMessage("库存调整成功"), icon = "success"))
                            goBackToList()
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error, "库存调整失败"), icon = "none"))
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
                    stockId.value = idValue
                    initialData.value["stock_id"] = idValue
                    loadDetail(idValue)
                }
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversaForm = resolveEasyComponent("lili-UniversaForm", GenUniModulesLiliUniversaFormComponentsLiliUniversaFormLiliUniversaFormClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to "调整库存", "showBack" to true, "showSearch" to false, "showHome" to true, "homePath" to "/pages/inventory-management/index", "backgroundColor" to "#EEF2F7")),
                    _cE("view", _uM("class" to "page-content"), _uA(
                        _cV(_component_lili_UniversaForm, _uM("mode" to "create", "formSections" to unref(formSections), "initialData" to unref(initialData), "leaveSignal" to unref(leaveSignal), "onSubmit" to handleSubmit, "onCancel" to handleCancel, "onDiscardLeave" to handleDiscardLeave, "onSaveRequest" to handleSaveRequest, "onDirtyChange" to handleDirtyChange, "onBottomSelectAdd" to handleBottomSelectAdd, "onBottomSelectEdit" to handleBottomSelectEdit), null, 8, _uA(
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
