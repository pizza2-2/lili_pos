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
import io.dcloud.uniapp.extapi.redirectTo as uni_redirectTo
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesPurchasesFrom : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesPurchasesFrom) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesPurchasesFrom
            val _cache = __ins.renderCache
            val refreshStorageKey = "refresh:pages:purchases:index"
            val formMode = ref("create")
            val purchaseId = ref("")
            val leaveSignal = ref(0)
            val submitting = ref(false)
            val initialData = ref<UTSJSONObject>(_uO())
            fun gen_twoDigit_fn(value: Number): String {
                if (value < 10) {
                    return "0" + value.toString(10)
                }
                return value.toString(10)
            }
            val twoDigit = ::gen_twoDigit_fn
            fun gen_todayText_fn(): String {
                val now = Date()
                return now.getFullYear().toString(10) + "-" + twoDigit(now.getMonth() + 1) + "-" + twoDigit(now.getDate())
            }
            val todayText = ::gen_todayText_fn
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
                    val errorText = JSON.stringify(error)
                    if (errorText != null && errorText != "") {
                        val parsedError = UTSAndroid.consoleDebugError(JSON.parseObject<UTSJSONObject>(errorText), " at pages/purchases/from.uvue:56")
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
            fun gen_fetchShopOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        val keyword = getStringField(params, "keyword")
                        val id = getStringField(params, "id")
                        val options = await(getPurchaseOptionList("/api/shops/shops/", if (keyword == "") {
                            null
                        } else {
                            keyword
                        }
                        , "name", "address"))
                        return@w1 buildSelectResponse(options, _uO("keyword" to keyword, "id" to id))
                })
            }
            val fetchShopOptions = ::gen_fetchShopOptions_fn
            fun gen_fetchSupplierOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        val keyword = getStringField(params, "keyword")
                        val id = getStringField(params, "id")
                        val options = await(getPurchaseOptionList("/api/procurement/suppliers/", if (keyword == "") {
                            null
                        } else {
                            keyword
                        }
                        , "name", "phone"))
                        return@w1 buildSelectResponse(options, _uO("keyword" to "", "id" to id))
                })
            }
            val fetchSupplierOptions = ::gen_fetchSupplierOptions_fn
            fun gen_initialCreateData_fn(): UTSJSONObject {
                return _uO("purchase_date" to todayText(), "shop" to "", "shop_text" to "", "supplier" to "", "supplier_text" to "", "remark" to "")
            }
            val initialCreateData = ::gen_initialCreateData_fn
            fun gen_buildInitialDataFromPurchase_fn(item: PurchaseItem): UTSJSONObject {
                return _uO("purchase_date" to item.purchase_date, "shop" to item.shop.toString(10), "shop_text" to item.shop_name, "supplier" to item.supplier.toString(10), "supplier_text" to item.supplier_name, "remark" to item.remark)
            }
            val buildInitialDataFromPurchase = ::gen_buildInitialDataFromPurchase_fn
            val formSections = ref(_uA<UTSJSONObject>(_uO("key" to "base", "title" to "基础信息", "description" to "", "defaultOpen" to true, "fields" to _uA<UTSJSONObject>(_uO("key" to "purchase_date", "label" to "采购日期", "type" to "datetime", "required" to true, "showTime" to false, "defaultToToday" to true, "title" to "选择采购日期", "placeholder" to "请选择采购日期"), _uO("key" to "shop", "textKey" to "shop_text", "label" to "采购店铺", "type" to "bottomSelect", "required" to true, "title" to "选择采购店铺", "placeholder" to "请选择采购店铺", "showAddAction" to true, "showEditAction" to true, "addPath" to "/pages/shop/from", "editPath" to "/pages/shop/from", "fetchData" to fetchShopOptions), _uO("key" to "supplier", "textKey" to "supplier_text", "label" to "供应商", "type" to "bottomSelect", "required" to true, "title" to "选择供应商", "placeholder" to "请选择供应商", "showAddAction" to true, "showEditAction" to true, "addPath" to "/pages/suppliers/from", "editPath" to "/pages/suppliers/from", "fetchData" to fetchSupplierOptions), _uO("key" to "remark", "label" to "备注", "type" to "textarea", "placeholder" to "请输入备注")))))
            val pageTitle = computed(fun(): String {
                return if (formMode.value == "edit") {
                    "编辑采购单"
                } else {
                    "新建采购单"
                }
            }
            )
            fun gen_markRefresh_fn() {
                uni_setStorageSync(refreshStorageKey, "1")
            }
            val markRefresh = ::gen_markRefresh_fn
            fun gen_goBackToList_fn() {
                leaveSignal.value = leaveSignal.value + 1
                setTimeout(fun(){
                    uni_navigateBack(NavigateBackOptions(delta = 1, fail = fun(_){
                        uni_navigateTo(NavigateToOptions(url = "/pages/purchases/index"))
                    }
                    ))
                }
                , 16)
            }
            val goBackToList = ::gen_goBackToList_fn
            fun gen_goToCreatedPurchaseDetail_fn(id: String) {
                leaveSignal.value = leaveSignal.value + 1
                initialData.value = initialCreateData()
                setTimeout(fun(){
                    uni_redirectTo(RedirectToOptions(url = "/pages/purchases/details/index?purchase=" + id, fail = fun(_){
                        uni_navigateTo(NavigateToOptions(url = "/pages/purchases/details/index?purchase=" + id))
                    }
                    ))
                }
                , 16)
            }
            val goToCreatedPurchaseDetail = ::gen_goToCreatedPurchaseDetail_fn
            fun gen_loadDetail_fn(idText: String): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (idText == "") {
                            return@w1
                        }
                        try {
                            val detail = await(getPurchaseDetail(idText))
                            initialData.value = buildInitialDataFromPurchase(detail)
                        }
                         catch (error: Throwable) {
                            showErrorToast(parseErrorMessage(error, "采购单详情加载失败"))
                        }
                })
            }
            val loadDetail = ::gen_loadDetail_fn
            fun gen_buildPayload_fn(data: UTSJSONObject): PurchaseMutationData {
                return PurchaseMutationData(purchase_date = getStringField(data, "purchase_date"), shop = getStringField(data, "shop"), supplier = getStringField(data, "supplier"), remark = if (getStringField(data, "remark") == "") {
                    null
                } else {
                    getStringField(data, "remark")
                }
                , items = _uA<UTSJSONObject>())
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
                        var body: PurchaseMutationData
                        try {
                            body = buildPayload(data)
                        }
                         catch (error: Throwable) {
                            showErrorToast(parseErrorMessage(error, "采购明细不完整"))
                            return@w1
                        }
                        if (body.purchase_date == "" || body.shop == "" || body.supplier == "") {
                            uni_showToast(ShowToastOptions(title = "请填写采购日期、店铺和供应商", icon = "none", duration = 3500))
                            return@w1
                        }
                        val actionText = if (formMode.value == "edit") {
                            "保存采购单"
                        } else {
                            "创建采购单"
                        }
                        submitting.value = true
                        uni_showLoading(ShowLoadingOptions(title = actionText + "中...", mask = true))
                        try {
                            var savedPurchaseId = purchaseId.value
                            if (formMode.value == "edit" && purchaseId.value != "") {
                                await(updatePurchase(purchaseId.value, body))
                            } else {
                                val created = await(createPurchase(body))
                                savedPurchaseId = created.id.toString(10)
                            }
                            markRefresh()
                            uni_showToast(ShowToastOptions(title = takeLatestResponseMessage(actionText + "成功"), icon = "success"))
                            if (formMode.value == "edit") {
                                goBackToList()
                            } else {
                                goToCreatedPurchaseDetail(savedPurchaseId)
                            }
                        }
                         catch (error: Throwable) {
                            showErrorToast(parseErrorMessage(error, actionText + "失败"))
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
                uni_showToast(ShowToastOptions(title = "请在对应模块维护选项", icon = "none", duration = 3500))
            }
            val handleBottomSelectAdd = ::gen_handleBottomSelectAdd_fn
            fun gen_handleBottomSelectEdit_fn(payload: UTSJSONObject) {
                uni_showToast(ShowToastOptions(title = "该字段不支持直接编辑", icon = "none", duration = 3500))
            }
            val handleBottomSelectEdit = ::gen_handleBottomSelectEdit_fn
            onLoad(fun(query: OnLoadOptions){
                initialData.value = initialCreateData()
                val idValue = query["id"]
                if (idValue != null && idValue != "") {
                    formMode.value = "edit"
                    purchaseId.value = idValue
                    loadDetail(idValue)
                }
            }
            )
            return fun(): Any? {
                val _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter", GenUniModulesLiliUniversalFilterComponentsLiliUniversalFilterLiliUniversalFilterClass)
                val _component_lili_UniversaForm = resolveEasyComponent("lili-UniversaForm", GenUniModulesLiliUniversaFormComponentsLiliUniversaFormLiliUniversaFormClass)
                return _cE("view", _uM("class" to "page"), _uA(
                    _cV(_component_lili_universal_filter, _uM("title" to pageTitle.value, "showBack" to true, "showSearch" to false, "showHome" to true, "homePath" to "/pages/purchases/index", "backgroundColor" to "#EEF2F7"), null, 8, _uA(
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
