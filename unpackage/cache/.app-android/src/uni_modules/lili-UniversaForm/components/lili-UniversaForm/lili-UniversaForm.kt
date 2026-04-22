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
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenUniModulesLiliUniversaFormComponentsLiliUniversaFormLiliUniversaForm : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var mode: String by `$props`
    open var formSections: UTSArray<UTSJSONObject> by `$props`
    open var initialData: UTSJSONObject by `$props`
    open var showFooter: Boolean by `$props`
    open var enableBackConfirm: Boolean by `$props`
    open var leaveSignal: Number by `$props`
    open var uploadContentTypeModel: String by `$props`
    open var showFloatingAction: Boolean by `$props`
    open var floatingActionText: String by `$props`
    open var floatingActionDisabled: Boolean by `$props`
    open var validate: () -> Boolean
        get() {
            return unref(this.`$exposed`["validate"]) as () -> Boolean
        }
        set(value) {
            setRefValue(this.`$exposed`, "validate", value)
        }
    open var getFormData: () -> UTSJSONObject
        get() {
            return unref(this.`$exposed`["getFormData"]) as () -> UTSJSONObject
        }
        set(value) {
            setRefValue(this.`$exposed`, "getFormData", value)
        }
    open var setFormData: (data: UTSJSONObject) -> Unit
        get() {
            return unref(this.`$exposed`["setFormData"]) as (data: UTSJSONObject) -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "setFormData", value)
        }
    open var resetForm: () -> Unit
        get() {
            return unref(this.`$exposed`["resetForm"]) as () -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "resetForm", value)
        }
    open var resetDirty: () -> Unit
        get() {
            return unref(this.`$exposed`["resetDirty"]) as () -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "resetDirty", value)
        }
    open var hasPendingChanges: () -> Boolean
        get() {
            return unref(this.`$exposed`["hasPendingChanges"]) as () -> Boolean
        }
        set(value) {
            setRefValue(this.`$exposed`, "hasPendingChanges", value)
        }
    open var confirmLeave: () -> Unit
        get() {
            return unref(this.`$exposed`["confirmLeave"]) as () -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "confirmLeave", value)
        }
    open var submit: () -> Unit
        get() {
            return unref(this.`$exposed`["submit"]) as () -> Unit
        }
        set(value) {
            setRefValue(this.`$exposed`, "submit", value)
        }
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenUniModulesLiliUniversaFormComponentsLiliUniversaFormLiliUniversaForm, __setupCtx: SetupContext) -> Any? = fun(__props, __setupCtx): Any? {
            val __expose = __setupCtx.expose
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenUniModulesLiliUniversaFormComponentsLiliUniversaFormLiliUniversaForm
            val _cache = __ins.renderCache
            val props = __props
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val formData = ref<UTSJSONObject>(_uO())
            val fieldErrors = ref<UTSJSONObject>(_uO())
            val openSections = ref(_uA<Boolean>())
            val dirty = ref<Boolean>(false)
            val snapshot = ref<String>("")
            val allowNativeBackOnce = ref<Boolean>(false)
            fun gen_getObjectField_fn(obj: UTSJSONObject, key: String): UTSJSONObject {
                val value = obj[key]
                if (value == null) {
                    return _uO()
                }
                return value as UTSJSONObject
            }
            val getObjectField = ::gen_getObjectField_fn
            fun gen_cloneObject_fn(source: UTSJSONObject): UTSJSONObject {
                val target: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("target", "uni_modules/lili-UniversaForm/components/lili-UniversaForm/lili-UniversaForm.uvue", 222, 8))
                for(key in resolveUTSKeyIterator(source)){
                    target[key] = source[key]
                }
                return target
            }
            val cloneObject = ::gen_cloneObject_fn
            fun gen_getArrayField_fn(obj: UTSJSONObject, key: String): UTSArray<UTSJSONObject> {
                val value = obj[key]
                if (value == null) {
                    return _uA()
                }
                return value as UTSArray<UTSJSONObject>
            }
            val getArrayField = ::gen_getArrayField_fn
            fun getStringField(obj: UTSJSONObject, key: String, fallback: String = ""): String {
                val value = obj[key]
                if (value == null) {
                    return fallback
                }
                return "" + value
            }
            fun getBooleanField(obj: UTSJSONObject, key: String, fallback: Boolean = false): Boolean {
                val value = obj[key]
                if (value == null) {
                    return fallback
                }
                return value as Boolean
            }
            fun getNumberField(obj: UTSJSONObject, key: String, fallback: Number = 0): Number {
                val value = obj[key]
                if (value == null) {
                    return fallback
                }
                return value as Number
            }
            fun gen_getFieldKey_fn(field: UTSJSONObject): String {
                return getStringField(field, "key")
            }
            val getFieldKey = ::gen_getFieldKey_fn
            fun gen_getFieldKeyWithIndex_fn(field: UTSJSONObject, index: Number): String {
                val key = getFieldKey(field)
                if (key != "") {
                    return key
                }
                return "field_" + index
            }
            val getFieldKeyWithIndex = ::gen_getFieldKeyWithIndex_fn
            fun gen_getSectionKey_fn(section: UTSJSONObject, index: Number): String {
                val key = getStringField(section, "key")
                if (key != "") {
                    return key
                }
                return "section_" + index
            }
            val getSectionKey = ::gen_getSectionKey_fn
            fun gen_getSectionTitle_fn(section: UTSJSONObject, index: Number): String {
                val title = getStringField(section, "title")
                if (title != "") {
                    return title
                }
                return "分组" + (index + 1)
            }
            val getSectionTitle = ::gen_getSectionTitle_fn
            fun gen_getSectionDescription_fn(section: UTSJSONObject): String {
                return getStringField(section, "description")
            }
            val getSectionDescription = ::gen_getSectionDescription_fn
            fun gen_getSectionFields_fn(section: UTSJSONObject): UTSArray<UTSJSONObject> {
                return getArrayField(section, "fields")
            }
            val getSectionFields = ::gen_getSectionFields_fn
            fun gen_getFieldLabel_fn(field: UTSJSONObject): String {
                val label = getStringField(field, "label")
                if (label != "") {
                    return label
                }
                return getFieldKey(field)
            }
            val getFieldLabel = ::gen_getFieldLabel_fn
            fun gen_getFieldDescription_fn(field: UTSJSONObject): String {
                return getStringField(field, "description")
            }
            val getFieldDescription = ::gen_getFieldDescription_fn
            fun gen_getFieldPlaceholder_fn(field: UTSJSONObject): String {
                val placeholder = getStringField(field, "placeholder")
                if (placeholder != "") {
                    return placeholder
                }
                return "请输入" + getFieldLabel(field)
            }
            val getFieldPlaceholder = ::gen_getFieldPlaceholder_fn
            fun gen_getFieldType_fn(field: UTSJSONObject): String {
                return getStringField(field, "type", "input")
            }
            val getFieldType = ::gen_getFieldType_fn
            fun gen_isInputField_fn(field: UTSJSONObject): Boolean {
                return getFieldType(field) == "input"
            }
            val isInputField = ::gen_isInputField_fn
            fun gen_isTextareaField_fn(field: UTSJSONObject): Boolean {
                return getFieldType(field) == "textarea"
            }
            val isTextareaField = ::gen_isTextareaField_fn
            fun gen_isNumberField_fn(field: UTSJSONObject): Boolean {
                return getFieldType(field) == "number"
            }
            val isNumberField = ::gen_isNumberField_fn
            fun gen_isDatetimeField_fn(field: UTSJSONObject): Boolean {
                return getFieldType(field) == "datetime"
            }
            val isDatetimeField = ::gen_isDatetimeField_fn
            fun gen_isSwitchField_fn(field: UTSJSONObject): Boolean {
                return getFieldType(field) == "switch"
            }
            val isSwitchField = ::gen_isSwitchField_fn
            fun gen_isBottomSelectField_fn(field: UTSJSONObject): Boolean {
                val fieldType = getFieldType(field)
                return fieldType == "bottomSelect" || fieldType == "select"
            }
            val isBottomSelectField = ::gen_isBottomSelectField_fn
            fun gen_isUploadField_fn(field: UTSJSONObject): Boolean {
                return getFieldType(field) == "upload"
            }
            val isUploadField = ::gen_isUploadField_fn
            fun gen_isRequired_fn(field: UTSJSONObject): Boolean {
                return getBooleanField(field, "required", false)
            }
            val isRequired = ::gen_isRequired_fn
            fun gen_isCreateOnly_fn(field: UTSJSONObject): Boolean {
                return getBooleanField(field, "createOnly", false)
            }
            val isCreateOnly = ::gen_isCreateOnly_fn
            fun gen_isEditOnly_fn(field: UTSJSONObject): Boolean {
                return getBooleanField(field, "editOnly", false)
            }
            val isEditOnly = ::gen_isEditOnly_fn
            fun gen_isReadonly_fn(field: UTSJSONObject): Boolean {
                if (getBooleanField(field, "readonly", false)) {
                    return true
                }
                if (props.mode == "create" && isEditOnly(field)) {
                    return true
                }
                if (props.mode == "edit" && isCreateOnly(field)) {
                    return true
                }
                return false
            }
            val isReadonly = ::gen_isReadonly_fn
            fun gen_shouldShowField_fn(field: UTSJSONObject): Boolean {
                if (getBooleanField(field, "hidden", false)) {
                    return false
                }
                val hideMode = getStringField(field, "hideInMode")
                if (hideMode != "" && hideMode == props.mode) {
                    return false
                }
                return true
            }
            val shouldShowField = ::gen_shouldShowField_fn
            fun gen_normalizeDateNumber_fn(value: Number): String {
                if (value < 10) {
                    return "0" + value
                }
                return "" + value
            }
            val normalizeDateNumber = ::gen_normalizeDateNumber_fn
            fun gen_buildCurrentDatetimeValue_fn(showTime: Boolean): String {
                val now = Date()
                val year = now.getFullYear()
                val month = normalizeDateNumber(now.getMonth() + 1)
                val day = normalizeDateNumber(now.getDate())
                if (!showTime) {
                    return "" + year + "-" + month + "-" + day
                }
                val hour = normalizeDateNumber(now.getHours())
                val minute = normalizeDateNumber(now.getMinutes())
                return "" + year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00"
            }
            val buildCurrentDatetimeValue = ::gen_buildCurrentDatetimeValue_fn
            fun gen_getDefaultValue_fn(field: UTSJSONObject): Any {
                val fieldType = getFieldType(field)
                val customDefault = field["defaultValue"]
                if (customDefault != null) {
                    return customDefault
                }
                if (fieldType == "datetime" && getBooleanField(field, "defaultToToday", false)) {
                    return buildCurrentDatetimeValue(getBooleanField(field, "showTime", true))
                }
                if (fieldType == "switch") {
                    return false
                }
                if (fieldType == "upload") {
                    return _uA<String>()
                }
                if (fieldType == "number") {
                    return ""
                }
                return ""
            }
            val getDefaultValue = ::gen_getDefaultValue_fn
            fun gen_getFieldValue_fn(field: UTSJSONObject): Any {
                val key = getFieldKey(field)
                if (key == "") {
                    return getDefaultValue(field)
                }
                val current = formData.value[key]
                if (current == null) {
                    return getDefaultValue(field)
                }
                return current
            }
            val getFieldValue = ::gen_getFieldValue_fn
            fun gen_getStringFieldValue_fn(field: UTSJSONObject): String {
                val value = getFieldValue(field)
                if (value == null) {
                    return ""
                }
                if (UTSArray.isArray(value)) {
                    return ""
                }
                return "" + value
            }
            val getStringFieldValue = ::gen_getStringFieldValue_fn
            fun gen_getBooleanFieldValue_fn(field: UTSJSONObject): Boolean {
                val value = getFieldValue(field)
                if (value == null) {
                    return false
                }
                return value as Boolean
            }
            val getBooleanFieldValue = ::gen_getBooleanFieldValue_fn
            fun gen_getUploadValue_fn(field: UTSJSONObject): UTSArray<String> {
                val value = getFieldValue(field)
                if (value == null) {
                    return _uA()
                }
                return value as UTSArray<String>
            }
            val getUploadValue = ::gen_getUploadValue_fn
            fun gen_getUploadFileItemsKey_fn(field: UTSJSONObject): String {
                val customKey = getStringField(field, "fileItemsKey")
                if (customKey != "") {
                    return customKey
                }
                return getFieldKey(field) + "Items"
            }
            val getUploadFileItemsKey = ::gen_getUploadFileItemsKey_fn
            fun gen_getUploadFileItems_fn(field: UTSJSONObject): UTSArray<UTSJSONObject> {
                val itemsKey = getUploadFileItemsKey(field)
                val value = formData.value[itemsKey]
                if (value == null) {
                    return _uA()
                }
                return value as UTSArray<UTSJSONObject>
            }
            val getUploadFileItems = ::gen_getUploadFileItems_fn
            fun gen_getFieldError_fn(field: UTSJSONObject): String {
                val key = getFieldKey(field)
                if (key == "") {
                    return ""
                }
                return getStringField(fieldErrors.value, key)
            }
            val getFieldError = ::gen_getFieldError_fn
            fun gen_getBottomSelectTitle_fn(field: UTSJSONObject): String {
                val title = getStringField(field, "title")
                if (title != "") {
                    return title
                }
                return getFieldLabel(field)
            }
            val getBottomSelectTitle = ::gen_getBottomSelectTitle_fn
            fun gen_getBottomSelectSearchPlaceholder_fn(field: UTSJSONObject): String {
                val value = getStringField(field, "searchPlaceholder")
                if (value != "") {
                    return value
                }
                return "请输入关键词搜索"
            }
            val getBottomSelectSearchPlaceholder = ::gen_getBottomSelectSearchPlaceholder_fn
            fun gen_getBottomSelectEmptyText_fn(field: UTSJSONObject): String {
                val value = getStringField(field, "emptyText")
                if (value != "") {
                    return value
                }
                return "暂无数据"
            }
            val getBottomSelectEmptyText = ::gen_getBottomSelectEmptyText_fn
            fun gen_getBottomSelectLabelKey_fn(field: UTSJSONObject): String {
                val value = getStringField(field, "labelKey")
                if (value != "") {
                    return value
                }
                return "text"
            }
            val getBottomSelectLabelKey = ::gen_getBottomSelectLabelKey_fn
            fun gen_getBottomSelectValueKey_fn(field: UTSJSONObject): String {
                val value = getStringField(field, "valueKey")
                if (value != "") {
                    return value
                }
                return "value"
            }
            val getBottomSelectValueKey = ::gen_getBottomSelectValueKey_fn
            fun gen_getBottomSelectTextKey_fn(field: UTSJSONObject): String {
                return getStringField(field, "textKey")
            }
            val getBottomSelectTextKey = ::gen_getBottomSelectTextKey_fn
            fun gen_getBottomSelectValueText_fn(field: UTSJSONObject): String {
                val textKey = getBottomSelectTextKey(field)
                if (textKey == "") {
                    return ""
                }
                val value = formData.value[textKey]
                if (value == null) {
                    return ""
                }
                return "" + value
            }
            val getBottomSelectValueText = ::gen_getBottomSelectValueText_fn
            fun gen_getBottomSelectPageSize_fn(field: UTSJSONObject): Number {
                val value = getNumberField(field, "pageSize", 20)
                if (value <= 0) {
                    return 20
                }
                return value
            }
            val getBottomSelectPageSize = ::gen_getBottomSelectPageSize_fn
            fun gen_getBottomSelectSearchDelay_fn(field: UTSJSONObject): Number {
                val value = getNumberField(field, "searchDelay", 300)
                if (value <= 0) {
                    return 300
                }
                return value
            }
            val getBottomSelectSearchDelay = ::gen_getBottomSelectSearchDelay_fn
            fun gen_getDatetimeTitle_fn(field: UTSJSONObject): String {
                val title = getStringField(field, "title")
                if (title != "") {
                    return title
                }
                return "选择" + getFieldLabel(field)
            }
            val getDatetimeTitle = ::gen_getDatetimeTitle_fn
            fun gen_getDatetimePlaceholder_fn(field: UTSJSONObject): String {
                val placeholder = getStringField(field, "placeholder")
                if (placeholder != "") {
                    return placeholder
                }
                val datePlaceholder = getStringField(field, "datePlaceholder")
                val timePlaceholder = getStringField(field, "timePlaceholder")
                if (datePlaceholder != "" && timePlaceholder != "") {
                    return datePlaceholder + " " + timePlaceholder
                }
                return "请选择" + getFieldLabel(field)
            }
            val getDatetimePlaceholder = ::gen_getDatetimePlaceholder_fn
            fun gen_getDatetimeShowTime_fn(field: UTSJSONObject): Boolean {
                return getBooleanField(field, "showTime", true)
            }
            val getDatetimeShowTime = ::gen_getDatetimeShowTime_fn
            fun gen_getDatetimeDefaultToToday_fn(field: UTSJSONObject): Boolean {
                return getBooleanField(field, "defaultToToday", false)
            }
            val getDatetimeDefaultToToday = ::gen_getDatetimeDefaultToToday_fn
            fun gen_getDatetimeStartYear_fn(field: UTSJSONObject): Number {
                val value = getNumberField(field, "startYear", 2000)
                if (value <= 0) {
                    return 2000
                }
                return value
            }
            val getDatetimeStartYear = ::gen_getDatetimeStartYear_fn
            fun gen_getDatetimeEndYear_fn(field: UTSJSONObject): Number {
                val value = getNumberField(field, "endYear", 2099)
                if (value <= 0) {
                    return 2099
                }
                return value
            }
            val getDatetimeEndYear = ::gen_getDatetimeEndYear_fn
            fun gen_getDatetimeMinuteStep_fn(field: UTSJSONObject): Number {
                val value = getNumberField(field, "minuteStep", 1)
                if (value <= 0) {
                    return 1
                }
                return value
            }
            val getDatetimeMinuteStep = ::gen_getDatetimeMinuteStep_fn
            fun gen_showBottomSelectEdit_fn(field: UTSJSONObject): Boolean {
                return getBooleanField(field, "showEditAction", true)
            }
            val showBottomSelectEdit = ::gen_showBottomSelectEdit_fn
            fun gen_showBottomSelectAdd_fn(field: UTSJSONObject): Boolean {
                return getBooleanField(field, "showAddAction", true)
            }
            val showBottomSelectAdd = ::gen_showBottomSelectAdd_fn
            fun gen_getFieldFetchData_fn(field: UTSJSONObject): FetchDataFn {
                return field["fetchData"] as FetchDataFn
            }
            val getFieldFetchData = ::gen_getFieldFetchData_fn
            fun gen_getUploadAction_fn(field: UTSJSONObject): String {
                return getStringField(field, "action")
            }
            val getUploadAction = ::gen_getUploadAction_fn
            fun gen_getUploadName_fn(field: UTSJSONObject): String {
                val name = getStringField(field, "name")
                if (name != "") {
                    return name
                }
                return "file"
            }
            val getUploadName = ::gen_getUploadName_fn
            fun gen_getUploadHeaders_fn(field: UTSJSONObject): UTSJSONObject {
                return getObjectField(field, "headers")
            }
            val getUploadHeaders = ::gen_getUploadHeaders_fn
            fun gen_getUploadFormData_fn(field: UTSJSONObject): UTSJSONObject {
                val fieldFormData = getObjectField(field, "formData")
                val result = cloneObject(fieldFormData)
                val fieldContentTypeModel = getStringField(result, "content_type_model").trim()
                if (fieldContentTypeModel == "") {
                    val pageContentTypeModel = props.uploadContentTypeModel.trim()
                    if (pageContentTypeModel != "") {
                        result["content_type_model"] = pageContentTypeModel
                    }
                }
                return result
            }
            val getUploadFormData = ::gen_getUploadFormData_fn
            fun gen_getUploadMax_fn(field: UTSJSONObject): Number {
                val maxValue = getNumberField(field, "max", 9)
                if (maxValue <= 0) {
                    return 9
                }
                return maxValue
            }
            val getUploadMax = ::gen_getUploadMax_fn
            fun gen_getUploadText_fn(field: UTSJSONObject): String {
                val value = getStringField(field, "uploadText")
                if (value != "") {
                    return value
                }
                return "上传图片"
            }
            val getUploadText = ::gen_getUploadText_fn
            fun gen_setFieldValueByKey_fn(key: String, value: Any) {
                formData.value[key] = value
            }
            val setFieldValueByKey = ::gen_setFieldValueByKey_fn
            fun gen_clearFieldError_fn(key: String) {
                if (fieldErrors.value[key] != null) {
                    fieldErrors.value[key] = ""
                }
            }
            val clearFieldError = ::gen_clearFieldError_fn
            fun gen_emitFieldChange_fn(field: UTSJSONObject, value: Any) {
                val payload: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("payload", "uni_modules/lili-UniversaForm/components/lili-UniversaForm/lili-UniversaForm.uvue", 602, 8), "field" to field, "key" to getFieldKey(field), "value" to value, "mode" to props.mode, "formData" to formData.value)
                emit("field-change", payload)
                emit("form-change", payload)
            }
            val emitFieldChange = ::gen_emitFieldChange_fn
            fun gen_serializeState_fn(): String {
                val state: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("state", "uni_modules/lili-UniversaForm/components/lili-UniversaForm/lili-UniversaForm.uvue", 614, 8), "mode" to props.mode, "formData" to formData.value)
                return JSON.stringify(state)
            }
            val serializeState = ::gen_serializeState_fn
            fun gen_refreshDirtyState_fn() {
                val nextDirty = serializeState() != snapshot.value
                if (nextDirty != dirty.value) {
                    dirty.value = nextDirty
                    emit("dirty-change", nextDirty)
                }
            }
            val refreshDirtyState = ::gen_refreshDirtyState_fn
            fun gen_markSnapshot_fn() {
                snapshot.value = serializeState()
                if (dirty.value) {
                    dirty.value = false
                    emit("dirty-change", false)
                }
            }
            val markSnapshot = ::gen_markSnapshot_fn
            fun gen_applyInitialValues_fn() {
                val nextData: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("nextData", "uni_modules/lili-UniversaForm/components/lili-UniversaForm/lili-UniversaForm.uvue", 638, 8))
                run {
                    var i: Number = 0
                    while(i < props.formSections.length){
                        val fields = getSectionFields(props.formSections[i])
                        run {
                            var j: Number = 0
                            while(j < fields.length){
                                val field = fields[j]
                                val key = getFieldKey(field)
                                if (key == "") {
                                    j++
                                    continue
                                }
                                val incoming = props.initialData[key]
                                if (incoming != null) {
                                    nextData[key] = incoming
                                } else {
                                    nextData[key] = getDefaultValue(field)
                                }
                                val textKey = getBottomSelectTextKey(field)
                                if (textKey != "") {
                                    val incomingText = props.initialData[textKey]
                                    nextData[textKey] = if (incomingText == null) {
                                        ""
                                    } else {
                                        incomingText
                                    }
                                }
                                if (isUploadField(field)) {
                                    val itemsKey = getUploadFileItemsKey(field)
                                    val incomingItems = props.initialData[itemsKey]
                                    if (incomingItems != null) {
                                        nextData[itemsKey] = incomingItems
                                    } else {
                                        nextData[itemsKey] = _uA<UTSJSONObject>()
                                    }
                                }
                                j++
                            }
                        }
                        i++
                    }
                }
                formData.value = nextData
                fieldErrors.value = _uO()
                markSnapshot()
            }
            val applyInitialValues = ::gen_applyInitialValues_fn
            fun gen_initOpenSections_fn() {
                val result: UTSArray<Boolean> = _uA()
                run {
                    var i: Number = 0
                    while(i < props.formSections.length){
                        result.push(getBooleanField(props.formSections[i], "defaultOpen", i == 0))
                        i++
                    }
                }
                openSections.value = result
            }
            val initOpenSections = ::gen_initOpenSections_fn
            fun gen_isSectionOpen_fn(index: Number): Boolean {
                if (index < 0 || index >= openSections.value.length) {
                    return false
                }
                return openSections.value[index]
            }
            val isSectionOpen = ::gen_isSectionOpen_fn
            fun gen_toggleSection_fn(index: Number) {
                if (index < 0 || index >= openSections.value.length) {
                    return
                }
                openSections.value[index] = !openSections.value[index]
            }
            val toggleSection = ::gen_toggleSection_fn
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
            fun gen_handleTextInput_fn(field: UTSJSONObject, event: Any) {
                val key = getFieldKey(field)
                if (key == "") {
                    return
                }
                val value = readEventValue(event)
                setFieldValueByKey(key, value)
                clearFieldError(key)
                refreshDirtyState()
                emitFieldChange(field, value)
            }
            val handleTextInput = ::gen_handleTextInput_fn
            fun gen_handleNumberInput_fn(field: UTSJSONObject, event: Any) {
                val key = getFieldKey(field)
                if (key == "") {
                    return
                }
                val value = readEventValue(event)
                setFieldValueByKey(key, value)
                clearFieldError(key)
                refreshDirtyState()
                emitFieldChange(field, value)
            }
            val handleNumberInput = ::gen_handleNumberInput_fn
            fun gen_handleDatetimeChange_fn(field: UTSJSONObject, payload: Any) {
                val key = getFieldKey(field)
                if (key == "") {
                    return
                }
                val payloadObject = payload as UTSJSONObject
                val value = getStringField(payloadObject, "value")
                setFieldValueByKey(key, value)
                clearFieldError(key)
                refreshDirtyState()
                emitFieldChange(field, value)
            }
            val handleDatetimeChange = ::gen_handleDatetimeChange_fn
            fun gen_handleSwitchChange_fn(field: UTSJSONObject, event: Any) {
                val key = getFieldKey(field)
                if (key == "") {
                    return
                }
                val switchEvent = event as UniSwitchChangeEvent
                val detail = switchEvent.detail
                if (detail == null) {
                    return
                }
                val value = detail.value
                setFieldValueByKey(key, value)
                clearFieldError(key)
                refreshDirtyState()
                emitFieldChange(field, value)
            }
            val handleSwitchChange = ::gen_handleSwitchChange_fn
            fun gen_handleBottomSelectChange_fn(field: UTSJSONObject, payload: Any) {
                val key = getFieldKey(field)
                if (key == "") {
                    return
                }
                val payloadObject = payload as UTSJSONObject
                val value = getStringField(payloadObject, "value")
                setFieldValueByKey(key, value)
                val textKey = getBottomSelectTextKey(field)
                if (textKey != "") {
                    setFieldValueByKey(textKey, getStringField(payloadObject, "text"))
                }
                clearFieldError(key)
                refreshDirtyState()
                emitFieldChange(field, value)
            }
            val handleBottomSelectChange = ::gen_handleBottomSelectChange_fn
            fun gen_handleBottomSelectAdd_fn(field: UTSJSONObject) {
                emit("bottom-select-add", _uO("field" to field, "key" to getFieldKey(field), "mode" to props.mode, "formData" to formData.value))
            }
            val handleBottomSelectAdd = ::gen_handleBottomSelectAdd_fn
            fun gen_handleBottomSelectEdit_fn(field: UTSJSONObject) {
                emit("bottom-select-edit", _uO("field" to field, "key" to getFieldKey(field), "value" to getFieldValue(field), "mode" to props.mode, "formData" to formData.value))
            }
            val handleBottomSelectEdit = ::gen_handleBottomSelectEdit_fn
            fun gen_handleUploadModelChange_fn(field: UTSJSONObject, value: Any) {
                val key = getFieldKey(field)
                if (key == "") {
                    return
                }
                val listValue = value as UTSArray<String>
                val nextList: UTSArray<String> = _uA()
                run {
                    var i: Number = 0
                    while(i < listValue.length){
                        nextList.push(listValue[i])
                        i++
                    }
                }
                setFieldValueByKey(key, nextList)
                clearFieldError(key)
                refreshDirtyState()
                emitFieldChange(field, nextList)
            }
            val handleUploadModelChange = ::gen_handleUploadModelChange_fn
            fun gen_handleUploadFileItemsChange_fn(field: UTSJSONObject, value: Any) {
                val itemsKey = getUploadFileItemsKey(field)
                val sourceItems = value as UTSArray<UTSJSONObject>
                val nextItems: UTSArray<UTSJSONObject> = _uA()
                run {
                    var index: Number = 0
                    while(index < sourceItems.length){
                        val sourceItem = sourceItems[index]
                        val clonedItem: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("clonedItem", "uni_modules/lili-UniversaForm/components/lili-UniversaForm/lili-UniversaForm.uvue", 796, 9))
                        for(key in resolveUTSKeyIterator(sourceItem)){
                            clonedItem[key] = sourceItem[key]
                        }
                        nextItems.push(clonedItem)
                        index++
                    }
                }
                setFieldValueByKey(itemsKey, nextItems)
                refreshDirtyState()
            }
            val handleUploadFileItemsChange = ::gen_handleUploadFileItemsChange_fn
            fun gen_handleUploadSuccess_fn(field: UTSJSONObject, payload: Any) {
                val payloadObject = payload as UTSJSONObject
                emit("upload", _uO("field" to field, "key" to getFieldKey(field), "payload" to payloadObject, "formData" to formData.value, "mode" to props.mode))
            }
            val handleUploadSuccess = ::gen_handleUploadSuccess_fn
            fun gen_handleUploadDelete_fn(field: UTSJSONObject, payload: Any) {
                val payloadObject = payload as UTSJSONObject
                refreshDirtyState()
                emit("upload-delete", _uO("field" to field, "key" to getFieldKey(field), "payload" to payloadObject, "formData" to formData.value, "mode" to props.mode))
            }
            val handleUploadDelete = ::gen_handleUploadDelete_fn
            fun gen_handleUploadError_fn(field: UTSJSONObject, payload: Any) {
                val payloadObject = payload as UTSJSONObject
                emit("upload-error", _uO("field" to field, "key" to getFieldKey(field), "payload" to payloadObject, "formData" to formData.value, "mode" to props.mode))
            }
            val handleUploadError = ::gen_handleUploadError_fn
            fun gen_validateField_fn(field: UTSJSONObject): String {
                if (!shouldShowField(field)) {
                    return ""
                }
                val key = getFieldKey(field)
                if (key == "") {
                    return ""
                }
                val value = formData.value[key]
                if (isRequired(field)) {
                    if (value == null) {
                        return getFieldLabel(field) + "不能为空"
                    }
                    if (UTSArray.isArray(value) && (value as UTSArray<String>).length == 0) {
                        return getFieldLabel(field) + "不能为空"
                    }
                    if (!UTSArray.isArray(value) && ("" + value) == "") {
                        return getFieldLabel(field) + "不能为空"
                    }
                }
                val validator = field["validator"]
                if (validator != null) {
                    val validatorValue = if (value == null) {
                        ""
                    } else {
                        value
                    }
                    val errorText = (validator as ValidatorFn)(validatorValue, formData.value, props.mode)
                    if (errorText != "") {
                        return errorText
                    }
                }
                return ""
            }
            val validateField = ::gen_validateField_fn
            fun gen_validate_fn(): Boolean {
                val errors: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("errors", "uni_modules/lili-UniversaForm/components/lili-UniversaForm/lili-UniversaForm.uvue", 868, 8))
                var hasError = false
                run {
                    var i: Number = 0
                    while(i < props.formSections.length){
                        val fields = getSectionFields(props.formSections[i])
                        run {
                            var j: Number = 0
                            while(j < fields.length){
                                val field = fields[j]
                                val errorText = validateField(field)
                                if (errorText != "") {
                                    errors[getFieldKey(field)] = errorText
                                    hasError = true
                                }
                                j++
                            }
                        }
                        i++
                    }
                }
                fieldErrors.value = errors
                return !hasError
            }
            val validate = ::gen_validate_fn
            fun gen_buildSubmitPayload_fn(): UTSJSONObject {
                return _uO("mode" to props.mode, "formData" to formData.value, "hasChanges" to dirty.value, "uploadContentTypeModel" to props.uploadContentTypeModel)
            }
            val buildSubmitPayload = ::gen_buildSubmitPayload_fn
            fun gen_discardAndLeave_fn() {
                allowNativeBackOnce.value = true
                dirty.value = false
                emit("discard-leave", buildSubmitPayload())
            }
            val discardAndLeave = ::gen_discardAndLeave_fn
            fun gen_confirmLeave_fn() {
                uni_showModal(ShowModalOptions(title = "提示", content = "页面内容已修改，是否先保存？", cancelText = "直接离开", confirmText = "去保存", success = fun(res){
                    if (res.confirm) {
                        emit("save-request", buildSubmitPayload())
                        return
                    }
                    if (res.cancel) {
                        discardAndLeave()
                    }
                }
                ))
            }
            val confirmLeave = ::gen_confirmLeave_fn
            fun gen_handleSubmit_fn() {
                if (!validate()) {
                    uni_showToast(ShowToastOptions(title = "请检查必填项", icon = "none"))
                    return
                }
                emit("submit", buildSubmitPayload())
            }
            val handleSubmit = ::gen_handleSubmit_fn
            fun gen_handleCancel_fn() {
                if (!dirty.value) {
                    emit("cancel", buildSubmitPayload())
                    return
                }
                confirmLeave()
            }
            val handleCancel = ::gen_handleCancel_fn
            fun gen_handleFloatingActionClick_fn() {
                if (props.floatingActionDisabled) {
                    return
                }
                val payload = buildSubmitPayload()
                payload["source"] = "floating-action"
                emit("floating-action", payload)
            }
            val handleFloatingActionClick = ::gen_handleFloatingActionClick_fn
            watch(fun(): UTSArray<UTSJSONObject> {
                return props.formSections
            }
            , fun(){
                initOpenSections()
                applyInitialValues()
            }
            , WatchOptions(immediate = true))
            watch(fun(): UTSJSONObject {
                return props.initialData
            }
            , fun(){
                applyInitialValues()
            }
            )
            watch(fun(): String {
                return props.mode
            }
            , fun(){
                refreshDirtyState()
            }
            )
            watch(fun(): Number {
                return props.leaveSignal
            }
            , fun(){
                allowNativeBackOnce.value = true
                dirty.value = false
            }
            )
            onBackPress(fun(_options): Boolean? {
                if (!props.enableBackConfirm) {
                    return null
                }
                if (allowNativeBackOnce.value) {
                    allowNativeBackOnce.value = false
                    return null
                }
                if (!dirty.value) {
                    return null
                }
                uni_showModal(ShowModalOptions(title = "提示", content = "当前页面有未保存内容，是否保存？", cancelText = "直接离开", confirmText = "保存", success = fun(res){
                    if (res.confirm) {
                        emit("save-request", buildSubmitPayload())
                        return
                    }
                    if (res.cancel) {
                        discardAndLeave()
                    }
                }
                ))
                return true
            }
            )
            __expose(_uM("validate" to validate, "getFormData" to fun(): UTSJSONObject {
                return formData.value
            }
            , "setFormData" to fun(data: UTSJSONObject){
                formData.value = data
                refreshDirtyState()
            }
            , "resetForm" to fun(){
                applyInitialValues()
            }
            , "resetDirty" to fun(){
                markSnapshot()
            }
            , "hasPendingChanges" to fun(): Boolean {
                return dirty.value
            }
            , "confirmLeave" to confirmLeave, "submit" to handleSubmit))
            return fun(): Any? {
                val _component_switch = resolveComponent("switch")
                return _cE("view", _uM("class" to "uf-root"), _uA(
                    _cE("scroll-view", _uM("class" to "uf-scroll", "style" to _nS(_uM("flex" to "1")), "direction" to "vertical", "show-scrollbar" to "false"), _uA(
                        _cE(Fragment, null, RenderHelpers.renderList(props.formSections, fun(section, sectionIndex, __index, _cached): Any {
                            return _cE("view", _uM("key" to getSectionKey(section, sectionIndex), "class" to "uf-section"), _uA(
                                _cE("view", _uM("class" to "uf-section-header", "onClick" to fun(){
                                    toggleSection(sectionIndex)
                                }
                                ), _uA(
                                    _cE("view", _uM("class" to "uf-section-title-wrap"), _uA(
                                        _cE("text", _uM("class" to "uf-section-title"), _tD(getSectionTitle(section, sectionIndex)), 1),
                                        if (getSectionDescription(section) != "") {
                                            _cE("text", _uM("key" to 0, "class" to "uf-section-desc"), _tD(getSectionDescription(section)), 1)
                                        } else {
                                            _cC("v-if", true)
                                        }
                                    )),
                                    _cE("text", _uM("class" to "uf-section-arrow"), _tD(if (isSectionOpen(sectionIndex)) {
                                        "⌃"
                                    } else {
                                        "⌄"
                                    }
                                    ), 1)
                                ), 8, _uA(
                                    "onClick"
                                )),
                                if (isTrue(isSectionOpen(sectionIndex))) {
                                    _cE("view", _uM("key" to 0, "class" to "uf-section-body"), _uA(
                                        _cE(Fragment, null, RenderHelpers.renderList(getSectionFields(section), fun(field, fieldIndex, __index, _cached): Any {
                                            return _cE("view", _uM("key" to getFieldKeyWithIndex(field, fieldIndex)), _uA(
                                                if (isTrue(shouldShowField(field))) {
                                                    _cE("view", _uM("key" to 0, "class" to "uf-field"), _uA(
                                                        _cE("view", _uM("class" to "uf-field-head"), _uA(
                                                            _cE("view", _uM("class" to "uf-field-title-line"), _uA(
                                                                _cE("text", _uM("class" to "uf-field-label"), _tD(getFieldLabel(field)), 1),
                                                                if (isTrue(isRequired(field))) {
                                                                    _cE("text", _uM("key" to 0, "class" to "uf-required"), "*")
                                                                } else {
                                                                    _cC("v-if", true)
                                                                },
                                                                if (isTrue(isReadonly(field))) {
                                                                    _cE("text", _uM("key" to 1, "class" to "uf-mode-tag"), "只读")
                                                                } else {
                                                                    if (isTrue(isCreateOnly(field) && props.mode == "create")) {
                                                                        _cE("text", _uM("key" to 2, "class" to "uf-mode-tag"), "新增")
                                                                    } else {
                                                                        if (isTrue(isEditOnly(field) && props.mode == "edit")) {
                                                                            _cE("text", _uM("key" to 3, "class" to "uf-mode-tag"), "编辑")
                                                                        } else {
                                                                            _cC("v-if", true)
                                                                        }
                                                                    }
                                                                }
                                                            )),
                                                            if (getFieldDescription(field) != "") {
                                                                _cE("text", _uM("key" to 0, "class" to "uf-field-desc"), _tD(getFieldDescription(field)), 1)
                                                            } else {
                                                                _cC("v-if", true)
                                                            }
                                                        )),
                                                        _cE("view", _uM("class" to "uf-control"), _uA(
                                                            if (isTrue(isInputField(field))) {
                                                                _cE("input", _uM("key" to 0, "class" to "uf-input", "value" to getStringFieldValue(field), "placeholder" to getFieldPlaceholder(field), "disabled" to isReadonly(field), "onInput" to fun(`$event`: UniInputEvent){
                                                                    handleTextInput(field, `$event`)
                                                                }), null, 40, _uA(
                                                                    "value",
                                                                    "placeholder",
                                                                    "disabled",
                                                                    "onInput"
                                                                ))
                                                            } else {
                                                                if (isTrue(isTextareaField(field))) {
                                                                    _cE("textarea", _uM("key" to 1, "class" to "uf-textarea", "value" to getStringFieldValue(field), "placeholder" to getFieldPlaceholder(field), "disabled" to isReadonly(field), "onInput" to fun(`$event`: Any){
                                                                        handleTextInput(field, `$event`)
                                                                    }), null, 40, _uA(
                                                                        "value",
                                                                        "placeholder",
                                                                        "disabled",
                                                                        "onInput"
                                                                    ))
                                                                } else {
                                                                    if (isTrue(isNumberField(field))) {
                                                                        _cE("input", _uM("key" to 2, "class" to "uf-input", "type" to "number", "value" to getStringFieldValue(field), "placeholder" to getFieldPlaceholder(field), "disabled" to isReadonly(field), "onInput" to fun(`$event`: UniInputEvent){
                                                                            handleNumberInput(field, `$event`)
                                                                        }), null, 40, _uA(
                                                                            "value",
                                                                            "placeholder",
                                                                            "disabled",
                                                                            "onInput"
                                                                        ))
                                                                    } else {
                                                                        if (isTrue(isDatetimeField(field))) {
                                                                            _cE("view", _uM("key" to 3, "class" to "uf-datetime-wrap"), _uA(
                                                                                _cV(unref(GenUniModulesLiliDataComponentsLiliDataLiliDataClass), _uM("value" to getStringFieldValue(field), "title" to getDatetimeTitle(field), "placeholder" to getDatetimePlaceholder(field), "disabled" to isReadonly(field), "showTime" to getDatetimeShowTime(field), "defaultToToday" to getDatetimeDefaultToToday(field), "startYear" to getDatetimeStartYear(field), "endYear" to getDatetimeEndYear(field), "minuteStep" to getDatetimeMinuteStep(field), "onChange" to fun(`$event`: Any){
                                                                                    handleDatetimeChange(field, `$event`)
                                                                                }), null, 8, _uA(
                                                                                    "value",
                                                                                    "title",
                                                                                    "placeholder",
                                                                                    "disabled",
                                                                                    "showTime",
                                                                                    "defaultToToday",
                                                                                    "startYear",
                                                                                    "endYear",
                                                                                    "minuteStep",
                                                                                    "onChange"
                                                                                ))
                                                                            ))
                                                                        } else {
                                                                            if (isTrue(isSwitchField(field))) {
                                                                                _cV(_component_switch, _uM("key" to 4, "checked" to getBooleanFieldValue(field), "disabled" to isReadonly(field), "onChange" to fun(`$event`: Any){
                                                                                    handleSwitchChange(field, `$event`)
                                                                                }), null, 8, _uA(
                                                                                    "checked",
                                                                                    "disabled",
                                                                                    "onChange"
                                                                                ))
                                                                            } else {
                                                                                if (isTrue(isBottomSelectField(field))) {
                                                                                    _cE("view", _uM("key" to 5, "class" to "uf-bottom-select-wrap"), _uA(
                                                                                        _cV(unref(GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelectClass), _uM("value" to getStringFieldValue(field), "valueText" to getBottomSelectValueText(field), "title" to getBottomSelectTitle(field), "placeholder" to getFieldPlaceholder(field), "searchPlaceholder" to getBottomSelectSearchPlaceholder(field), "emptyText" to getBottomSelectEmptyText(field), "disabled" to isReadonly(field), "labelKey" to getBottomSelectLabelKey(field), "valueKey" to getBottomSelectValueKey(field), "pageSize" to getBottomSelectPageSize(field), "searchDelay" to getBottomSelectSearchDelay(field), "showEditAction" to showBottomSelectEdit(field), "showAddAction" to showBottomSelectAdd(field), "fetchData" to getFieldFetchData(field), "onChange" to fun(`$event`: Any){
                                                                                            handleBottomSelectChange(field, `$event`)
                                                                                        }, "onEdit" to fun(){
                                                                                            handleBottomSelectEdit(field)
                                                                                        }, "onAdd" to fun(){
                                                                                            handleBottomSelectAdd(field)
                                                                                        }), null, 8, _uA(
                                                                                            "value",
                                                                                            "valueText",
                                                                                            "title",
                                                                                            "placeholder",
                                                                                            "searchPlaceholder",
                                                                                            "emptyText",
                                                                                            "disabled",
                                                                                            "labelKey",
                                                                                            "valueKey",
                                                                                            "pageSize",
                                                                                            "searchDelay",
                                                                                            "showEditAction",
                                                                                            "showAddAction",
                                                                                            "fetchData",
                                                                                            "onChange",
                                                                                            "onEdit",
                                                                                            "onAdd"
                                                                                        ))
                                                                                    ))
                                                                                } else {
                                                                                    if (isTrue(isUploadField(field))) {
                                                                                        _cE("view", _uM("key" to 6, "class" to "uf-upload-wrap"), _uA(
                                                                                            _cV(unref(GenUniModulesLiliUploadComponentsLiliUploadLiliUploadClass), _uM("modelValue" to getUploadValue(field), "action" to getUploadAction(field), "name" to getUploadName(field), "headers" to getUploadHeaders(field), "formData" to getUploadFormData(field), "max" to getUploadMax(field), "disabled" to isReadonly(field), "uploadText" to getUploadText(field), "onUpdate:modelValue" to fun(`$event`: Any){
                                                                                                handleUploadModelChange(field, `$event`)
                                                                                            }, "onUpdate:fileItems" to fun(`$event`: Any){
                                                                                                handleUploadFileItemsChange(field, `$event`)
                                                                                            }, "onUpload" to fun(`$event`: Any){
                                                                                                handleUploadSuccess(field, `$event`)
                                                                                            }, "onDelete" to fun(`$event`: Any){
                                                                                                handleUploadDelete(field, `$event`)
                                                                                            }, "onError" to fun(`$event`: Any){
                                                                                                handleUploadError(field, `$event`)
                                                                                            }), null, 8, _uA(
                                                                                                "modelValue",
                                                                                                "action",
                                                                                                "name",
                                                                                                "headers",
                                                                                                "formData",
                                                                                                "max",
                                                                                                "disabled",
                                                                                                "uploadText",
                                                                                                "onUpdate:modelValue",
                                                                                                "onUpdate:fileItems",
                                                                                                "onUpload",
                                                                                                "onDelete",
                                                                                                "onError"
                                                                                            ))
                                                                                        ))
                                                                                    } else {
                                                                                        _cE("view", _uM("key" to 7, "class" to "uf-plain-value"), _uA(
                                                                                            _cE("text", _uM("class" to "uf-plain-value-text"), _tD(getStringFieldValue(field)), 1)
                                                                                        ))
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        )),
                                                        if (getFieldError(field) != "") {
                                                            _cE("text", _uM("key" to 0, "class" to "uf-error-text"), _tD(getFieldError(field)), 1)
                                                        } else {
                                                            _cC("v-if", true)
                                                        }
                                                    ))
                                                } else {
                                                    _cC("v-if", true)
                                                }
                                            ))
                                        }), 128)
                                    ))
                                } else {
                                    _cC("v-if", true)
                                }
                            ))
                        }
                        ), 128)
                    ), 4),
                    if (isTrue(props.showFooter)) {
                        _cE("view", _uM("key" to 0, "class" to "uf-footer"), _uA(
                            _cE("button", _uM("class" to "uf-btn uf-btn-light", "onClick" to handleCancel), "取消"),
                            _cE("button", _uM("class" to "uf-btn uf-btn-primary", "onClick" to handleSubmit), _tD(if (props.mode == "edit") {
                                "保存修改"
                            } else {
                                "创建"
                            }), 1)
                        ))
                    } else {
                        _cC("v-if", true)
                    }
                    ,
                    if (isTrue(props.showFloatingAction)) {
                        _cE("view", _uM("key" to 1, "class" to "uf-floating-action", "onClick" to handleFloatingActionClick), _uA(
                            _cE("text", _uM("class" to "uf-floating-action-text"), _tD(props.floatingActionText), 1)
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
                return _uM("uf-root" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F5F7FB")), "uf-scroll" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingTop" to 8, "paddingRight" to 8, "paddingBottom" to 96, "paddingLeft" to 8)), "uf-section" to _pS(_uM("marginBottom" to 8, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#FFFFFF")), "uf-section-header" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to 14, "paddingRight" to 16, "paddingBottom" to 14, "paddingLeft" to 16)), "uf-section-title-wrap" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "uf-section-title" to _pS(_uM("fontSize" to 16, "fontWeight" to "600", "color" to "#1F2937", "lineHeight" to "20px")), "uf-section-desc" to _pS(_uM("marginTop" to 4, "fontSize" to 12, "color" to "#9CA3AF", "lineHeight" to "16px")), "uf-section-arrow" to _pS(_uM("fontSize" to 18, "color" to "#9CA3AF", "lineHeight" to "18px")), "uf-section-body" to _pS(_uM("paddingTop" to 0, "paddingRight" to 16, "paddingBottom" to 12, "paddingLeft" to 16)), "uf-field" to _pS(_uM("paddingTop" to 12, "paddingBottom" to 12, "borderTopWidth" to 1, "borderTopStyle" to "solid", "borderTopColor" to "#F1F5F9")), "uf-field-head" to _pS(_uM("marginBottom" to 8)), "uf-field-title-line" to _pS(_uM("flexDirection" to "row", "alignItems" to "center")), "uf-field-label" to _pS(_uM("fontSize" to 14, "color" to "#111827", "lineHeight" to "18px")), "uf-required" to _pS(_uM("marginLeft" to 4, "fontSize" to 14, "color" to "#DC2626", "lineHeight" to "18px")), "uf-mode-tag" to _pS(_uM("marginLeft" to 8, "paddingTop" to 2, "paddingRight" to 8, "paddingBottom" to 2, "paddingLeft" to 8, "borderTopLeftRadius" to 999, "borderTopRightRadius" to 999, "borderBottomRightRadius" to 999, "borderBottomLeftRadius" to 999, "fontSize" to 11, "color" to "#92400E", "lineHeight" to "14px", "backgroundColor" to "#FEF3C7")), "uf-field-desc" to _pS(_uM("marginTop" to 4, "fontSize" to 12, "color" to "#6B7280", "lineHeight" to "16px")), "uf-control" to _pS(_uM("minHeight" to 44)), "uf-input" to _pS(_uM("height" to 44, "paddingLeft" to 12, "paddingRight" to 12, "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "backgroundColor" to "#F8FAFC", "fontSize" to 14, "color" to "#111827")), "uf-textarea" to _pS(_uM("height" to 96, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "backgroundColor" to "#F8FAFC", "fontSize" to 14, "color" to "#111827")), "uf-datetime-wrap" to _pS(_uM("minHeight" to 44)), "uf-bottom-select-wrap" to _pS(_uM("minHeight" to 44)), "uf-upload-wrap" to _pS(_uM("paddingTop" to 4)), "uf-plain-value" to _pS(_uM("minHeight" to 44, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "backgroundColor" to "#F8FAFC")), "uf-plain-value-text" to _pS(_uM("fontSize" to 14, "color" to "#111827", "lineHeight" to "20px")), "uf-error-text" to _pS(_uM("marginTop" to 6, "fontSize" to 12, "color" to "#DC2626", "lineHeight" to "16px")), "uf-footer" to _pS(_uM("position" to "absolute", "left" to 0, "right" to 0, "bottom" to 0, "flexDirection" to "row", "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12, "backgroundColor" to "#FFFFFF", "borderTopWidth" to 1, "borderTopStyle" to "solid", "borderTopColor" to "#E5E7EB")), "uf-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 44, "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "fontSize" to 15, "lineHeight" to "44px")), "uf-btn-light" to _pS(_uM("marginRight" to 10, "color" to "#374151", "backgroundColor" to "#E5E7EB")), "uf-btn-primary" to _pS(_uM("color" to "#FFFFFF", "backgroundColor" to "#2563EB")), "uf-floating-action" to _pS(_uM("position" to "absolute", "left" to 12, "bottom" to 84, "height" to 34, "paddingLeft" to 12, "paddingRight" to 12, "borderTopLeftRadius" to 17, "borderTopRightRadius" to 17, "borderBottomRightRadius" to 17, "borderBottomLeftRadius" to 17, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "rgba(37,99,235,0.92)")), "uf-floating-action-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "16px", "color" to "#FFFFFF")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("submit" to null, "cancel" to null, "field-change" to null, "form-change" to null, "dirty-change" to null, "save-request" to null, "discard-leave" to null, "upload" to null, "upload-delete" to null, "upload-error" to null, "bottom-select-add" to null, "bottom-select-edit" to null, "floating-action" to null)
        var props = _nP(_uM("mode" to _uM("type" to "String", "required" to false, "default" to "create"), "formSections" to _uM("type" to "Array", "required" to false, "default" to fun(): UTSArray<UTSJSONObject> {
            return _uA()
        }
        ), "initialData" to _uM("type" to "UTSJSONObject", "required" to false, "default" to fun(): UTSJSONObject {
            return _uO()
        }
        ), "showFooter" to _uM("type" to "Boolean", "required" to false, "default" to true), "enableBackConfirm" to _uM("type" to "Boolean", "required" to false, "default" to true), "leaveSignal" to _uM("type" to "Number", "required" to false, "default" to 0), "uploadContentTypeModel" to _uM("type" to "String", "required" to false, "default" to ""), "showFloatingAction" to _uM("type" to "Boolean", "required" to false, "default" to false), "floatingActionText" to _uM("type" to "String", "required" to false, "default" to "快捷"), "floatingActionDisabled" to _uM("type" to "Boolean", "required" to false, "default" to false)))
        var propsNeedCastKeys = _uA(
            "mode",
            "formSections",
            "initialData",
            "showFooter",
            "enableBackConfirm",
            "leaveSignal",
            "uploadContentTypeModel",
            "showFloatingAction",
            "floatingActionText",
            "floatingActionDisabled"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
