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
open class GenPagesPurchasesDetailsFrom : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesPurchasesDetailsFrom) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesPurchasesDetailsFrom
            val _cache = __ins.renderCache
            val refreshStorageKey = "refresh:pages:purchases:details:index"
            val purchaseListRefreshStorageKey = "refresh:pages:purchases:index"
            val formMode = ref("create")
            val purchaseId = ref("")
            val detailId = ref("")
            val leaveSignal = ref(0)
            val submitting = ref(false)
            val initialData = ref<UTSJSONObject>(_uO())
            fun getStringField(obj: UTSJSONObject, key: String, fallback: String = ""): String {
                val value = obj[key]
                if (value == null) {
                    return fallback
                }
                return "" + value
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
                        val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/purchases/details/from.uvue:49")
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
            fun gen_buildSelectResponse_fn(source: UTSArray<PurchaseOptionItem>, params: UTSJSONObject): UTSJSONObject {
                val keyword = getStringField(params, "keyword").toLowerCase()
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
                        if (keyword != "" && option.text.toLowerCase().indexOf(keyword) < 0) {
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
            fun gen_fetchProductOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        val keyword = getStringField(params, "keyword")
                        val id = getStringField(params, "id")
                        val options = await(getPurchaseOptionList("/api/products/products/", if (keyword == "") {
                            null
                        } else {
                            keyword
                        }
                        , "name_cn", "barcode"))
                        return@w1 buildSelectResponse(options, _uO("keyword" to keyword, "id" to id))
                })
            }
            val fetchProductOptions = ::gen_fetchProductOptions_fn
            fun gen_initialCreateData_fn(): UTSJSONObject {
                return _uO("product" to "", "product_text" to "", "quantity" to "", "received_quantity" to "0", "notes" to "")
            }
            val initialCreateData = ::gen_initialCreateData_fn
            fun gen_buildInitialDataFromDetail_fn(item: PurchaseDetailItem): UTSJSONObject {
                var productText = item.product_name
                if (item.product_barcode != "") {
                    productText = productText + " / " + item.product_barcode
                }
                return _uO("product" to item.product.toString(10), "product_text" to productText, "quantity" to item.quantity.toString(10), "received_quantity" to item.received_quantity.toString(10), "notes" to item.notes)
            }
            val buildInitialDataFromDetail = ::gen_buildInitialDataFromDetail_fn
            val formSections = ref(_uA<UTSJSONObject>(_uO("key" to "base", "title" to "明细信息", "description" to "", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "product", "textKey" to "product_text", "label" to "商品", "type" to "bottomSelect", "required" to true, "title" to "选择商品", "placeholder" to "请选择商品", "showAddAction" to true, "showEditAction" to true, "addPath" to "/pages/products/from", "editPath" to "/pages/products/from", "fetchData" to fetchProductOptions), _uO("key" to "quantity", "label" to "采购数量", "type" to "number", "required" to true, "placeholder" to "请输入采购数量"), _uO("key" to "received_quantity", "label" to "已收货数量", "type" to "number", "placeholder" to "通常由收货操作更新"), _uO("key" to "notes", "label" to "备注", "type" to "textarea", "placeholder" to "请输入备注")))))
            val homePath = computed(fun(): String {
                return "/pages/purchases/details/index?purchase=" + purchaseId.value
            }
            )
            val pageTitle = computed(fun(): String {
                return if (formMode.value == "edit") {
                    "编辑采购明细"
                } else {
                    "新建采购明细"
                }
            }
            )
            fun gen_markRefresh_fn() {
                uni_setStorageSync(refreshStorageKey + ":" + purchaseId.value, "1")
                uni_setStorageSync(purchaseListRefreshStorageKey, "1")
            }
            val markRefresh = ::gen_markRefresh_fn
            fun gen_goBackToList_fn() {
                leaveSignal.value = leaveSignal.value + 1
                setTimeout(fun(){
                    uni_navigateBack(NavigateBackOptions(delta = 1, fail = fun(_){
                        uni_navigateTo(NavigateToOptions(url = homePath.value))
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
                            val detail = await(getPurchaseDetailItem(idText))
                            initialData.value = buildInitialDataFromDetail(detail)
                        }
                         catch (error: Throwable) {
                            uni_showToast(ShowToastOptions(title = parseErrorMessage(error, "采购明细加载失败"), icon = "none"))
                        }
                })
            }
            val loadDetail = ::gen_loadDetail_fn
            fun gen_buildPayload_fn(data: UTSJSONObject): PurchaseDetailMutationData {
                return PurchaseDetailMutationData(purchase = purchaseId.value, product = getStringField(data, "product"), quantity = getStringField(data, "quantity"), received_quantity = if (getStringField(data, "received_quantity") == "") {
                    null
                } else {
                    getStringField(data, "received_quantity")
                }
                , notes = if (getStringField(data, "notes") == "") {
                    null
                } else {
                    getStringField(data, "notes")
                }
                )
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
                        val quantity = parseInt(body.quantity)
                        if (body.purchase == "" || body.product == "" || isNaN(quantity) || quantity <= 0) {
                            uni_showToast(ShowToastOptions(title = "请填写商品和有效采购数量", icon = "none"))
                            return@w1
                        }
                        val actionText = if (formMode.value == "edit") {
                            "保存采购明细"
                        } else {
                            "创建采购明细"
                        }
                        submitting.value = true
                        uni_showLoading(ShowLoadingOptions(title = actionText + "中...", mask = true))
                        try {
                            if (formMode.value == "edit" && detailId.value != "") {
                                await(updatePurchaseDetail(detailId.value, body))
                            } else {
                                await(createPurchaseDetail(body))
                            }
                            markRefresh()
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
                uni_showToast(ShowToastOptions(title = "请在对应模块维护选项", icon = "none"))
            }
            val handleBottomSelectAdd = ::gen_handleBottomSelectAdd_fn
            fun gen_handleBottomSelectEdit_fn(payload: UTSJSONObject) {
                uni_showToast(ShowToastOptions(title = "该字段不支持直接编辑", icon = "none"))
            }
            val handleBottomSelectEdit = ::gen_handleBottomSelectEdit_fn
            onLoad(fun(query: OnLoadOptions){
                val purchaseValue = query["purchase"]
                val idValue = query["id"]
                purchaseId.value = if (purchaseValue == null) {
                    ""
                } else {
                    ("" + purchaseValue)
                }
                detailId.value = if (idValue == null) {
                    ""
                } else {
                    ("" + idValue)
                }
                formMode.value = if (detailId.value == "") {
                    "create"
                } else {
                    "edit"
                }
                initialData.value = initialCreateData()
                if (formMode.value == "edit") {
                    loadDetail(detailId.value)
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
