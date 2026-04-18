@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME", "UNUSED_ANONYMOUS_PARAMETER", "NAME_SHADOWING", "UNNECESSARY_NOT_NULL_ASSERTION")
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
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesIndexCrypto : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesIndexCrypto) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesIndexCrypto
            val _cache = __ins.renderCache
            val selectedValue = ref<String>("")
            val selectedText = ref<String>("")
            val latestAction = ref<String>("等待交互")
            val cryptoSeed: UTSArray<CryptoOption> = _uA<CryptoOption>(CryptoOption(value = "btc", text = "Bitcoin"), CryptoOption(value = "eth", text = "Ethereum"), CryptoOption(value = "sol", text = "Solana"), CryptoOption(value = "bnb", text = "BNB"), CryptoOption(value = "xrp", text = "XRP"), CryptoOption(value = "ada", text = "Cardano"), CryptoOption(value = "doge", text = "Dogecoin"), CryptoOption(value = "dot", text = "Polkadot"))
            fun gen_normalizeKeyword_fn(params: UTSJSONObject): String {
                val keywordValue = params["keyword"]
                if (keywordValue == null) {
                    return ""
                }
                return ("" + keywordValue).toLowerCase()
            }
            val normalizeKeyword = ::gen_normalizeKeyword_fn
            fun gen_normalizePage_fn(params: UTSJSONObject): Number {
                val pageValue = params["page"]
                if (pageValue == null) {
                    return 1
                }
                return pageValue as Number
            }
            val normalizePage = ::gen_normalizePage_fn
            fun gen_normalizePageSize_fn(params: UTSJSONObject): Number {
                val pageSizeValue = params["pageSize"]
                if (pageSizeValue == null) {
                    return 20
                }
                return pageSizeValue as Number
            }
            val normalizePageSize = ::gen_normalizePageSize_fn
            fun gen_normalizeId_fn(params: UTSJSONObject): String {
                val idValue = params["id"]
                if (idValue == null) {
                    return ""
                }
                return "" + idValue
            }
            val normalizeId = ::gen_normalizeId_fn
            fun gen_optionToJson_fn(option: CryptoOption): UTSJSONObject {
                return object : UTSJSONObject() {
                    var value = option.value
                    var text = option.text
                }
            }
            val optionToJson = ::gen_optionToJson_fn
            fun gen_fetchCryptoOptions_fn(params: UTSJSONObject): UTSPromise<UTSJSONObject> {
                return wrapUTSPromise(suspend w1@{
                        val keyword = normalizeKeyword(params)
                        val page = normalizePage(params)
                        val pageSize = normalizePageSize(params)
                        val id = normalizeId(params)
                        var filtered: UTSArray<CryptoOption> = _uA()
                        if (id != "") {
                            run {
                                var i: Number = 0
                                while(i < cryptoSeed.length){
                                    val item = cryptoSeed[i]
                                    if (item.value == id) {
                                        filtered.push(item)
                                        break
                                    }
                                    i++
                                }
                            }
                        } else {
                            run {
                                var i: Number = 0
                                while(i < cryptoSeed.length){
                                    val item = cryptoSeed[i]
                                    val lowerText = item.text.toLowerCase()
                                    val lowerValue = item.value.toLowerCase()
                                    if (keyword == "" || lowerText.indexOf(keyword) >= 0 || lowerValue.indexOf(keyword) >= 0) {
                                        filtered.push(item)
                                    }
                                    i++
                                }
                            }
                        }
                        val start = (page - 1) * pageSize
                        var end = start + pageSize
                        if (end > filtered.length) {
                            end = filtered.length
                        }
                        val pageItems: UTSArray<UTSJSONObject> = _uA()
                        run {
                            var i = start
                            while(i < end){
                                pageItems.push(optionToJson(filtered[i]))
                                i++
                            }
                        }
                        return@w1 object : UTSJSONObject() {
                            var data = pageItems
                            var total = filtered.length
                        }
                })
            }
            val fetchCryptoOptions = ::gen_fetchCryptoOptions_fn
            fun gen_handleChange_fn(payload: UTSJSONObject) {
                val value = if (payload["value"] == null) {
                    ""
                } else {
                    "" + payload["value"]
                }
                val textValue = payload["text"]
                val text = if (textValue == null) {
                    ""
                } else {
                    textValue as String
                }
                selectedValue.value = value
                selectedText.value = text
                latestAction.value = "选择了 " + text + " (" + value + ")"
                uni_showToast(ShowToastOptions(title = "已选 " + text, icon = "none"))
            }
            val handleChange = ::gen_handleChange_fn
            fun gen_handleEdit_fn() {
                latestAction.value = "点击了编辑，后续可跳转到编辑页"
                uni_showToast(ShowToastOptions(title = "编辑事件已触发", icon = "none"))
            }
            val handleEdit = ::gen_handleEdit_fn
            fun gen_handleAdd_fn() {
                latestAction.value = "点击了新增，后续可跳转到新增页"
                uni_showToast(ShowToastOptions(title = "新增事件已触发", icon = "none"))
            }
            val handleAdd = ::gen_handleAdd_fn
            return fun(): Any? {
                val _component_lili_bottom_select = resolveEasyComponent("lili_bottom-select", GenUniModulesLiliBottomSelectComponentsLiliBottomSelectLiliBottomSelectClass)
                return _cE("scroll-view", _uM("class" to "page-scroll", "scroll-y" to "true"), _uA(
                    _cE("view", _uM("class" to "page"), _uA(
                        _cE("view", _uM("class" to "hero-card"), _uA(
                            _cE("text", _uM("class" to "hero-title"), "Bottom Select 测试页"),
                            _cE("text", _uM("class" to "hero-desc"), "用于验证触发器右侧“编辑 / 新增”入口的位置、点击事件和选择交互。")
                        )),
                        _cE("view", _uM("class" to "panel-card"), _uA(
                            _cE("text", _uM("class" to "section-title"), "默认触发器测试"),
                            _cE("text", _uM("class" to "section-desc"), "点击输入框区域会打开底部选择器，点击“编辑 / 新增”只触发事件，不会展开面板。"),
                            _cV(_component_lili_bottom_select, _uM("fetchData" to fetchCryptoOptions, "value" to selectedValue.value, "title" to "选择币种", "placeholder" to "请选择测试币种", "searchPlaceholder" to "输入币种名称搜索", "onChange" to handleChange, "onEdit" to handleEdit, "onAdd" to handleAdd), null, 8, _uA(
                                "value"
                            ))
                        )),
                        _cE("view", _uM("class" to "panel-card"), _uA(
                            _cE("text", _uM("class" to "section-title"), "当前结果"),
                            _cE("view", _uM("class" to "info-row"), _uA(
                                _cE("text", _uM("class" to "info-label"), "当前 value"),
                                _cE("text", _uM("class" to "info-value"), _tD(if (selectedValue.value == "") {
                                    "(空)"
                                } else {
                                    selectedValue.value
                                }
                                ), 1)
                            )),
                            _cE("view", _uM("class" to "info-row"), _uA(
                                _cE("text", _uM("class" to "info-label"), "当前 text"),
                                _cE("text", _uM("class" to "info-value"), _tD(if (selectedText.value == "") {
                                    "(空)"
                                } else {
                                    selectedText.value
                                }
                                ), 1)
                            )),
                            _cE("view", _uM("class" to "info-row"), _uA(
                                _cE("text", _uM("class" to "info-label"), "最近动作"),
                                _cE("text", _uM("class" to "info-value"), _tD(latestAction.value), 1)
                            ))
                        ))
                    ))
                ))
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ), _uA(
                GenApp.styles
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("page-scroll" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "backgroundColor" to "#F4F6F8")), "page" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingLeft" to 16, "paddingRight" to 16, "paddingTop" to 16, "paddingBottom" to 24, "backgroundColor" to "#F4F6F8")), "hero-card" to _pS(_uM("backgroundColor" to "#0F172A", "borderTopLeftRadius" to 20, "borderTopRightRadius" to 20, "borderBottomRightRadius" to 20, "borderBottomLeftRadius" to 20, "paddingTop" to 20, "paddingBottom" to 20, "paddingLeft" to 18, "paddingRight" to 18)), "hero-title" to _pS(_uM("fontSize" to 22, "fontWeight" to "bold", "color" to "#FFFFFF")), "hero-desc" to _pS(_uM("fontSize" to 14, "lineHeight" to "22px", "color" to "#CBD5E1", "marginTop" to 8)), "panel-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "paddingTop" to 18, "paddingBottom" to 18, "paddingLeft" to 16, "paddingRight" to 16, "marginTop" to 14, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0")), "section-title" to _pS(_uM("fontSize" to 16, "fontWeight" to "bold", "color" to "#0F172A")), "section-desc" to _pS(_uM("fontSize" to 13, "lineHeight" to "20px", "color" to "#64748B", "marginTop" to 6, "marginBottom" to 14)), "info-row" to _pS(_uM("paddingTop" to 12, "paddingBottom" to 12, "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#EEF2F6")), "info-label" to _pS(_uM("fontSize" to 12, "color" to "#94A3B8")), "info-value" to _pS(_uM("fontSize" to 15, "lineHeight" to "22px", "color" to "#0F172A", "marginTop" to 6)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
