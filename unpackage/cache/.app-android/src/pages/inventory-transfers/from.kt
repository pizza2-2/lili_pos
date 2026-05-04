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
open class GenPagesInventoryTransfersFrom : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesInventoryTransfersFrom) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesInventoryTransfersFrom
            val _cache = __ins.renderCache
            val refreshStorageKey = "refresh:pages:inventory-transfers:index"
            val formMode = ref("create")
            val itemId = ref("")
            val leaveSignal = ref(0)
            val submitting = ref(false)
            val initialData = ref<UTSJSONObject>(_uO("from_location" to "", "to_location" to "", "transfer_date" to "", "remark" to "", "item_stock" to "", "item_quantity" to ""))
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
            val formSections = ref(_uA<UTSJSONObject>(_uO("key" to "base", "title" to "调拨信息", "description" to "", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "from_location", "label" to "调出位置ID", "type" to "input", "required" to true, "placeholder" to "请输入调出库存位置ID"), _uO("key" to "to_location", "label" to "调入位置ID", "type" to "input", "required" to true, "placeholder" to "请输入调入库存位置ID"), _uO("key" to "transfer_date", "label" to "调拨日期", "type" to "input", "placeholder" to "例如 2026-05-03"), _uO("key" to "remark", "label" to "备注", "type" to "textarea", "placeholder" to "请输入备注"))), _uO("key" to "items", "title" to "调拨明细", "description" to "", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "item_stock", "label" to "库存ID", "type" to "input", "required" to true, "placeholder" to "请输入要调拨的库存ID"), _uO("key" to "item_quantity", "label" to "调拨数量", "type" to "input", "required" to true, "placeholder" to "请输入调拨数量")))))
            val pageTitle = computed(fun(): String {
                return if (formMode.value == "edit") {
                    "编辑调拨单"
                } else {
                    "新建调拨单"
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
                        uni_navigateTo(NavigateToOptions(url = "/pages/inventory-transfers/index"))
                    }
                    ))
                }
                , 16)
            }
            val goBackToList = ::gen_goBackToList_fn
            fun gen_buildPayload_fn(data: UTSJSONObject): InventoryMutationData {
                val itemStockText = getStringField(data, "item_stock")
                val itemQuantityText = getStringField(data, "item_quantity")
                val items = _uA(
                    _uO("stock" to parseInt(itemStockText), "quantity" to parseInt(itemQuantityText), "notes" to "")
                ) as UTSArray<UTSJSONObject>
                return InventoryMutationData(payload = _uO("from_location" to parseInt(getStringField(data, "from_location")), "to_location" to parseInt(getStringField(data, "to_location")), "transfer_date" to getStringField(data, "transfer_date"), "remark" to getStringField(data, "remark"), "items" to items))
            }
            val buildPayload = ::gen_buildPayload_fn
            fun gen_loadDetail_fn(idText: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (idText == "") {
                            return@w1
                        }
                        try {
                            val detail = await(getInventoryTransferDetail(idText))
                            initialData.value = _uO("from_location" to getStringField(detail, "from_location"), "to_location" to getStringField(detail, "to_location"), "transfer_date" to getStringField(detail, "transfer_date"), "remark" to getStringField(detail, "remark"), "item_stock" to "", "item_quantity" to "")
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error, "调拨单详情加载失败"), icon = "none"))
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
                        val body = buildPayload(data)
                        val actionText = if (formMode.value == "edit") {
                            "保存调拨单"
                        } else {
                            "创建调拨单"
                        }
                        submitting.value = true
                        uni_showLoading(ShowLoadingOptions(title = actionText + "中...", mask = true))
                        try {
                            if (formMode.value == "edit" && itemId.value != "") {
                                await(updateInventoryTransfer(itemId.value, body))
                            } else {
                                await(createInventoryTransfer(body))
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
                    _cV(_component_lili_universal_filter, _uM("title" to pageTitle.value, "showBack" to true, "showSearch" to false, "showHome" to true, "homePath" to "/pages/inventory-transfers/index", "backgroundColor" to "#EEF2F7"), null, 8, _uA(
                        "title"
                    )),
                    _cE("view", _uM("class" to "page-content"), _uA(
                        _cV(_component_lili_UniversaForm, _uM("mode" to unref(formMode), "formSections" to unref(formSections), "initialData" to unref(initialData), "leaveSignal" to unref(leaveSignal), "onSubmit" to handleSubmit, "onCancel" to handleCancel, "onDiscardLeave" to handleDiscardLeave, "onSaveRequest" to handleSaveRequest, "onDirtyChange" to handleDirtyChange), null, 8, _uA(
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
