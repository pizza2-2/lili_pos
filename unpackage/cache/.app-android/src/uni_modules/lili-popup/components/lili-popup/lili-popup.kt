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
open class GenUniModulesLiliPopupComponentsLiliPopupLiliPopup : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var visible: Boolean by `$props`
    open var title: String by `$props`
    open var content: String by `$props`
    open var cancelText: String by `$props`
    open var confirmText: String by `$props`
    open var auxiliaryText: String by `$props`
    open var width: String by `$props`
    open var zIndex: Number by `$props`
    open var closeOnMask: Boolean by `$props`
    open var showCloseIcon: Boolean by `$props`
    open var confirmDanger: Boolean by `$props`
    open var autoClose: Boolean by `$props`
    open fun open(config: UTSJSONObject? = null) {
        callKotlinFunction(this.`$exposed`["open"]!!, _uA(
            config
        ))
    }
    open fun openSaveConfirm(config: UTSJSONObject? = null) {
        callKotlinFunction(this.`$exposed`["openSaveConfirm"]!!, _uA(
            config
        ))
    }
    open fun close(action: String = "close") {
        callKotlinFunction(this.`$exposed`["close"]!!, _uA(
            action
        ))
    }
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenUniModulesLiliPopupComponentsLiliPopupLiliPopup, __setupCtx: SetupContext) -> Any? = fun(__props, __setupCtx): Any? {
            val __expose = __setupCtx.expose
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenUniModulesLiliPopupComponentsLiliPopupLiliPopup
            val _cache = __ins.renderCache
            val props = __props
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val renderVisible = ref<Boolean>(props.visible)
            val currentTitle = ref<String>(props.title)
            val currentContent = ref<String>(props.content)
            val cancelTextValue = ref<String>(props.cancelText)
            val confirmTextValue = ref<String>(props.confirmText)
            val auxiliaryTextValue = ref<String>(props.auxiliaryText)
            val panelStyle = ref<String>("")
            val rootStyle = ref<String>("")
            val closeOnMaskValue = ref<Boolean>(props.closeOnMask)
            val showCloseIconValue = ref<Boolean>(props.showCloseIcon)
            val confirmDangerValue = ref<Boolean>(props.confirmDanger)
            val autoCloseValue = ref<Boolean>(props.autoClose)
            fun gen_syncStyle_fn() {
                panelStyle.value = "width:" + props.width + ";"
                rootStyle.value = "z-index:" + props.zIndex + ";"
            }
            val syncStyle = ::gen_syncStyle_fn
            fun gen_readString_fn(config: UTSJSONObject, key: String, fallback: String): String {
                val value = config[key]
                if (value == null) {
                    return fallback
                }
                return "" + value
            }
            val readString = ::gen_readString_fn
            fun gen_readBoolean_fn(config: UTSJSONObject, key: String, fallback: Boolean): Boolean {
                val value = config[key]
                if (value == null) {
                    return fallback
                }
                return value as Boolean
            }
            val readBoolean = ::gen_readBoolean_fn
            fun gen_applyBaseProps_fn() {
                currentTitle.value = props.title
                currentContent.value = props.content
                cancelTextValue.value = props.cancelText
                confirmTextValue.value = props.confirmText
                auxiliaryTextValue.value = props.auxiliaryText
                closeOnMaskValue.value = props.closeOnMask
                showCloseIconValue.value = props.showCloseIcon
                confirmDangerValue.value = props.confirmDanger
                autoCloseValue.value = props.autoClose
            }
            val applyBaseProps = ::gen_applyBaseProps_fn
            fun gen_applyConfig_fn(config: UTSJSONObject?) {
                applyBaseProps()
                if (config == null) {
                    return
                }
                currentTitle.value = readString(config, "title", currentTitle.value)
                currentContent.value = readString(config, "content", currentContent.value)
                cancelTextValue.value = readString(config, "cancelText", cancelTextValue.value)
                confirmTextValue.value = readString(config, "confirmText", confirmTextValue.value)
                auxiliaryTextValue.value = readString(config, "auxiliaryText", auxiliaryTextValue.value)
                closeOnMaskValue.value = readBoolean(config, "closeOnMask", closeOnMaskValue.value)
                showCloseIconValue.value = readBoolean(config, "showCloseIcon", showCloseIconValue.value)
                confirmDangerValue.value = readBoolean(config, "confirmDanger", confirmDangerValue.value)
                autoCloseValue.value = readBoolean(config, "autoClose", autoCloseValue.value)
            }
            val applyConfig = ::gen_applyConfig_fn
            fun gen_changeVisible_fn(value: Boolean) {
                renderVisible.value = value
                emit("update:visible", value)
                if (value) {
                    emit("open")
                }
            }
            val changeVisible = ::gen_changeVisible_fn
            fun open(config: UTSJSONObject? = null) {
                applyConfig(config)
                changeVisible(true)
            }
            fun openSaveConfirm(config: UTSJSONObject? = null) {
                val defaultConfig: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("defaultConfig", "uni_modules/lili-popup/components/lili-popup/lili-popup.uvue", 145, 8), "title" to "是否保存", "content" to "当前内容已修改，是否保存后再退出？", "auxiliaryText" to "退出", "cancelText" to "取消保存", "confirmText" to "保存", "showCloseIcon" to false, "closeOnMask" to false, "confirmDanger" to false, "autoClose" to true)
                if (config != null) {
                    for(key in resolveUTSKeyIterator(config)){
                        defaultConfig[key] = config[key]
                    }
                }
                open(defaultConfig)
            }
            fun close(action: String = "close") {
                changeVisible(false)
                emit("action", action)
                emit("close", action)
            }
            fun gen_handleCancel_fn() {
                emit("cancel")
                if (autoCloseValue.value) {
                    close("cancel")
                }
            }
            val handleCancel = ::gen_handleCancel_fn
            fun gen_handleConfirm_fn() {
                emit("confirm")
                if (autoCloseValue.value) {
                    close("confirm")
                }
            }
            val handleConfirm = ::gen_handleConfirm_fn
            fun gen_handleAuxiliary_fn() {
                emit("auxiliary")
                if (autoCloseValue.value) {
                    close("auxiliary")
                }
            }
            val handleAuxiliary = ::gen_handleAuxiliary_fn
            fun gen_handleMaskClick_fn() {
                if (!closeOnMaskValue.value) {
                    return
                }
                close("mask")
            }
            val handleMaskClick = ::gen_handleMaskClick_fn
            fun gen_handleCloseIcon_fn() {
                close("icon")
            }
            val handleCloseIcon = ::gen_handleCloseIcon_fn
            watch(fun(): Boolean {
                return props.visible
            }
            , fun(newValue: Boolean){
                renderVisible.value = newValue
            }
            )
            watch(fun(): String {
                return props.title
            }
            , fun(){
                if (!renderVisible.value) {
                    applyBaseProps()
                }
            }
            )
            watch(fun(): String {
                return props.content
            }
            , fun(){
                if (!renderVisible.value) {
                    applyBaseProps()
                }
            }
            )
            watch(fun(): String {
                return props.cancelText
            }
            , fun(){
                if (!renderVisible.value) {
                    applyBaseProps()
                }
            }
            )
            watch(fun(): String {
                return props.confirmText
            }
            , fun(){
                if (!renderVisible.value) {
                    applyBaseProps()
                }
            }
            )
            watch(fun(): String {
                return props.auxiliaryText
            }
            , fun(){
                if (!renderVisible.value) {
                    applyBaseProps()
                }
            }
            )
            watch(fun(): String {
                return props.width
            }
            , fun(){
                syncStyle()
            }
            )
            watch(fun(): Number {
                return props.zIndex
            }
            , fun(){
                syncStyle()
            }
            )
            onMounted(fun(){
                applyBaseProps()
                syncStyle()
            }
            )
            __expose(_uM("open" to ::open, "openSaveConfirm" to ::openSaveConfirm, "close" to ::close))
            return fun(): Any? {
                return if (isTrue(unref(renderVisible))) {
                    _cE("view", _uM("key" to 0, "class" to "lp-root", "style" to _nS(unref(rootStyle))), _uA(
                        _cE("view", _uM("class" to "lp-mask", "onClick" to handleMaskClick)),
                        _cE("view", _uM("class" to "lp-panel", "style" to _nS(unref(panelStyle))), _uA(
                            if (isTrue(unref(showCloseIconValue))) {
                                _cE("view", _uM("key" to 0, "class" to "lp-close", "onClick" to handleCloseIcon), _uA(
                                    _cE("text", _uM("class" to "lp-close-text"), "×")
                                ))
                            } else {
                                _cC("v-if", true)
                            },
                            _cE("view", _uM("class" to "lp-header"), _uA(
                                _cE("text", _uM("class" to "lp-title"), _tD(unref(currentTitle)), 1)
                            )),
                            _cE("view", _uM("class" to "lp-body"), _uA(
                                renderSlot(_ctx.`$slots`, "default", _uO(), fun(): UTSArray<Any> {
                                    return _uA(
                                        if (unref(currentContent) != "") {
                                            _cE("text", _uM("key" to 0, "class" to "lp-content"), _tD(unref(currentContent)), 1)
                                        } else {
                                            _cC("v-if", true)
                                        }
                                    )
                                })
                            )),
                            _cE("view", _uM("class" to "lp-footer"), _uA(
                                if (unref(auxiliaryTextValue) != "") {
                                    _cE("view", _uM("key" to 0, "class" to "lp-btn lp-btn-gap-right lp-btn-light", "onClick" to handleAuxiliary), _uA(
                                        _cE("text", _uM("class" to "lp-btn-light-text"), _tD(unref(auxiliaryTextValue)), 1)
                                    ))
                                } else {
                                    _cC("v-if", true)
                                },
                                _cE("view", _uM("class" to _nC(if (unref(auxiliaryTextValue) != "") {
                                    "lp-btn lp-btn-gap-right lp-btn-light"
                                } else {
                                    "lp-btn lp-btn-light"
                                }), "onClick" to handleCancel), _uA(
                                    _cE("text", _uM("class" to "lp-btn-light-text"), _tD(unref(cancelTextValue)), 1)
                                ), 2),
                                _cE("view", _uM("class" to _nC(if (unref(confirmDangerValue)) {
                                    "lp-btn lp-btn-danger"
                                } else {
                                    "lp-btn lp-btn-primary"
                                }), "onClick" to handleConfirm), _uA(
                                    _cE("text", _uM("class" to "lp-btn-primary-text"), _tD(unref(confirmTextValue)), 1)
                                ), 2)
                            ))
                        ), 4)
                    ), 4)
                } else {
                    _cC("v-if", true)
                }
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("lp-root" to _pS(_uM("position" to "fixed", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0, "alignItems" to "center", "justifyContent" to "center", "paddingLeft" to 24, "paddingRight" to 24)), "lp-mask" to _pS(_uM("position" to "absolute", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0, "backgroundColor" to "rgba(15,23,42,0.48)")), "lp-panel" to _pS(_uM("position" to "relative", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 24, "borderTopRightRadius" to 24, "borderBottomRightRadius" to 24, "borderBottomLeftRadius" to 24, "paddingTop" to 28, "paddingLeft" to 24, "paddingRight" to 24, "paddingBottom" to 20)), "lp-close" to _pS(_uM("position" to "absolute", "top" to 16, "right" to 16, "width" to 28, "height" to 28, "alignItems" to "center", "justifyContent" to "center", "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "backgroundColor" to "#F3F4F6")), "lp-close-text" to _pS(_uM("fontSize" to 18, "lineHeight" to "18px", "color" to "#475569")), "lp-header" to _pS(_uM("alignItems" to "center", "paddingRight" to 36)), "lp-title" to _pS(_uM("fontSize" to 18, "lineHeight" to "26px", "color" to "#0F172A", "fontWeight" to "600", "textAlign" to "center")), "lp-body" to _pS(_uM("minHeight" to 56, "paddingTop" to 16, "paddingBottom" to 24, "alignItems" to "center", "justifyContent" to "center")), "lp-content" to _pS(_uM("fontSize" to 15, "lineHeight" to "23px", "color" to "#475569", "textAlign" to "center")), "lp-footer" to _pS(_uM("flexDirection" to "row")), "lp-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 44, "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "alignItems" to "center", "justifyContent" to "center", "paddingLeft" to 12, "paddingRight" to 12)), "lp-btn-gap-right" to _pS(_uM("marginLeft" to 12)), "lp-btn-light" to _pS(_uM("backgroundColor" to "#F8FAFC", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0")), "lp-btn-primary" to _pS(_uM("backgroundColor" to "#0F172A")), "lp-btn-danger" to _pS(_uM("backgroundColor" to "#DC2626")), "lp-btn-light-text" to _pS(_uM("fontSize" to 15, "lineHeight" to "15px", "color" to "#334155", "fontWeight" to "500")), "lp-btn-primary-text" to _pS(_uM("fontSize" to 15, "lineHeight" to "15px", "color" to "#FFFFFF", "fontWeight" to "600")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("update:visible" to null, "open" to null, "close" to null, "cancel" to null, "confirm" to null, "auxiliary" to null, "action" to null)
        var props = _nP(_uM("visible" to _uM("type" to "Boolean", "required" to false, "default" to false), "title" to _uM("type" to "String", "required" to false, "default" to "提示"), "content" to _uM("type" to "String", "required" to false, "default" to ""), "cancelText" to _uM("type" to "String", "required" to false, "default" to "取消"), "confirmText" to _uM("type" to "String", "required" to false, "default" to "确定"), "auxiliaryText" to _uM("type" to "String", "required" to false, "default" to ""), "width" to _uM("type" to "String", "required" to false, "default" to "620rpx"), "zIndex" to _uM("type" to "Number", "required" to false, "default" to 999), "closeOnMask" to _uM("type" to "Boolean", "required" to false, "default" to false), "showCloseIcon" to _uM("type" to "Boolean", "required" to false, "default" to false), "confirmDanger" to _uM("type" to "Boolean", "required" to false, "default" to false), "autoClose" to _uM("type" to "Boolean", "required" to false, "default" to true)))
        var propsNeedCastKeys = _uA(
            "visible",
            "title",
            "content",
            "cancelText",
            "confirmText",
            "auxiliaryText",
            "width",
            "zIndex",
            "closeOnMask",
            "showCloseIcon",
            "confirmDanger",
            "autoClose"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
