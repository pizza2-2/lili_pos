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
open class GenPagesTabbarReports : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesTabbarReports) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesTabbarReports
            val _cache = __ins.renderCache
            val periodOptions = _uA<PeriodOption>(PeriodOption(key = "today", label = "今日"), PeriodOption(key = "week", label = "本周"), PeriodOption(key = "month", label = "本月"))
            val selectedPeriod = ref<ReportPeriod>("today")
            val isLoading = ref(false)
            val errorMessage = ref("")
            val overview = ref<ReportOverview>(ReportOverview(sales_amount = "0.00", order_count = 0, average_order_value = "0.00", purchase_amount = "0.00", expense_amount = "0.00", arrears_amount = "0.00", net_cashflow = "0.00"))
            val salesTrend = ref<UTSArray<ReportTrendItem>>(_uA<ReportTrendItem>())
            val paymentMethods = ref<UTSArray<ReportPaymentMethod>>(_uA<ReportPaymentMethod>())
            val inventory = ref<ReportInventory>(ReportInventory(stock_item_count = 0, total_quantity = 0, available_quantity = 0, low_stock_count = 0, out_of_stock_count = 0, no_movement_count = 0, inventory_value = "0.00"))
            val alerts = ref<UTSArray<ReportAlert>>(_uA<ReportAlert>())
            val currentPeriodLabel = computed(fun(): String {
                run {
                    var index: Number = 0
                    while(index < periodOptions.length){
                        if (periodOptions[index].key == selectedPeriod.value) {
                            return periodOptions[index].label
                        }
                        index += 1
                    }
                }
                return "今日"
            }
            )
            val maxTrendAmount = computed(fun(): Number {
                var maxValue: Number = 0
                run {
                    var index: Number = 0
                    while(index < salesTrend.value.length){
                        val parsed = parseFloat(salesTrend.value[index].amount)
                        if (!isNaN(parsed) && parsed > maxValue) {
                            maxValue = parsed
                        }
                        index += 1
                    }
                }
                return maxValue
            }
            )
            fun gen_moneyText_fn(value: String): String {
                if (value == "") {
                    return "¥ 0.00"
                }
                return "¥ " + value
            }
            val moneyText = ::gen_moneyText_fn
            fun gen_compactDate_fn(value: String): String {
                if (value.length >= 10) {
                    return value.substring(5, 10)
                }
                return value
            }
            val compactDate = ::gen_compactDate_fn
            fun gen_trendWidth_fn(value: String): Number {
                val maxValue = maxTrendAmount.value
                if (maxValue <= 0) {
                    return 0
                }
                val parsed = parseFloat(value)
                if (isNaN(parsed) || parsed <= 0) {
                    return 0
                }
                val width = Math.round((parsed / maxValue) * 100)
                if (width < 8) {
                    return 8
                }
                return width
            }
            val trendWidth = ::gen_trendWidth_fn
            fun gen_applyReport_fn(data: DashboardReport) {
                overview.value = data.overview
                salesTrend.value = data.sales_trend
                paymentMethods.value = data.payment_methods
                inventory.value = data.inventory
                alerts.value = data.alerts
            }
            val applyReport = ::gen_applyReport_fn
            fun gen_parseErrorMessage_fn(error: Any): String {
                if (error == null) {
                    return "报表加载失败"
                }
                val directMessage = (error as UTSError).message
                if (directMessage != null && directMessage != "") {
                    return directMessage
                }
                val errorText = JSON.stringify(error)
                if (errorText != null && errorText != "") {
                    return errorText
                }
                return "报表加载失败"
            }
            val parseErrorMessage = ::gen_parseErrorMessage_fn
            fun gen_loadReport_fn(): UTSPromise<Unit> {
                return wrapUTSPromise(suspend w1@{
                        if (isLoading.value) {
                            return@w1
                        }
                        isLoading.value = true
                        errorMessage.value = ""
                        try {
                            val data = await(getDashboardReport(selectedPeriod.value))
                            applyReport(data)
                        }
                         catch (error: Throwable) {
                            errorMessage.value = parseErrorMessage(error)
                        }
                         finally {
                            isLoading.value = false
                        }
                })
            }
            val loadReport = ::gen_loadReport_fn
            fun gen_changePeriod_fn(period: ReportPeriod) {
                if (selectedPeriod.value == period) {
                    return
                }
                selectedPeriod.value = period
                loadReport()
            }
            val changePeriod = ::gen_changePeriod_fn
            onLoad(fun(_options){
                loadReport()
            }
            )
            onShow(fun(){
                loadReport()
            }
            )
            return fun(): Any? {
                return _cE("scroll-view", _uM("class" to "page-scroll", "style" to _nS(_uM("flex" to "1")), "direction" to "vertical"), _uA(
                    _cE("view", _uM("class" to "page"), _uA(
                        _cE("view", _uM("class" to "status-bar-space")),
                        _cE("view", _uM("class" to "topbar"), _uA(
                            _cE("text", _uM("class" to "page-title"), "报表"),
                            _cE("button", _uM("class" to "refresh-btn", "onClick" to loadReport), _uA(
                                _cE("text", _uM("class" to "refresh-text"), "刷新")
                            ))
                        )),
                        _cE("view", _uM("class" to "period-tabs"), _uA(
                            _cE(Fragment, null, RenderHelpers.renderList(periodOptions, fun(item, __key, __index, _cached): Any {
                                return _cE("view", _uM("key" to item.key, "class" to _nC(if (unref(selectedPeriod) == item.key) {
                                    "period-tab period-tab-active"
                                } else {
                                    "period-tab"
                                }
                                ), "onClick" to fun(){
                                    changePeriod(item.key)
                                }
                                ), _uA(
                                    _cE("text", _uM("class" to _nC(if (unref(selectedPeriod) == item.key) {
                                        "period-text period-text-active"
                                    } else {
                                        "period-text"
                                    }
                                    )), _tD(item.label), 3)
                                ), 10, _uA(
                                    "onClick"
                                ))
                            }
                            ), 64)
                        )),
                        if (isTrue(unref(errorMessage) != "" && !unref(isLoading))) {
                            _cE("view", _uM("key" to 0, "class" to "notice-card"), _uA(
                                _cE("text", _uM("class" to "notice-title"), "加载失败"),
                                _cE("text", _uM("class" to "notice-desc"), _tD(unref(errorMessage)), 1)
                            ))
                        } else {
                            _cC("v-if", true)
                        }
                        ,
                        _cE("view", _uM("class" to "hero-card surface-animate"), _uA(
                            _cE("text", _uM("class" to "hero-label"), _tD(currentPeriodLabel.value) + "销售额", 1),
                            _cE("text", _uM("class" to "hero-value"), _tD(moneyText(unref(overview).sales_amount)), 1),
                            _cE("view", _uM("class" to "hero-meta"), _uA(
                                _cE("text", _uM("class" to "hero-meta-text"), "订单 " + _tD(unref(overview).order_count.toString(10)), 1),
                                _cE("text", _uM("class" to "hero-meta-text"), "客单价 " + _tD(moneyText(unref(overview).average_order_value)), 1)
                            ))
                        )),
                        _cE("view", _uM("class" to "metrics-grid"), _uA(
                            _cE("view", _uM("class" to "metric-card surface-animate"), _uA(
                                _cE("text", _uM("class" to "metric-label"), "采购金额"),
                                _cE("text", _uM("class" to "metric-value"), _tD(moneyText(unref(overview).purchase_amount)), 1)
                            )),
                            _cE("view", _uM("class" to "metric-card metric-right surface-animate"), _uA(
                                _cE("text", _uM("class" to "metric-label"), "支出金额"),
                                _cE("text", _uM("class" to "metric-value"), _tD(moneyText(unref(overview).expense_amount)), 1)
                            )),
                            _cE("view", _uM("class" to "metric-card surface-animate"), _uA(
                                _cE("text", _uM("class" to "metric-label"), "供应商欠款"),
                                _cE("text", _uM("class" to "metric-value"), _tD(moneyText(unref(overview).arrears_amount)), 1)
                            )),
                            _cE("view", _uM("class" to "metric-card metric-right surface-animate"), _uA(
                                _cE("text", _uM("class" to "metric-label"), "净现金流"),
                                _cE("text", _uM("class" to "metric-value"), _tD(moneyText(unref(overview).net_cashflow)), 1)
                            ))
                        )),
                        _cE("view", _uM("class" to "section surface-animate"), _uA(
                            _cE("text", _uM("class" to "section-title"), "销售趋势"),
                            if (unref(salesTrend).length == 0) {
                                _cE("view", _uM("key" to 0, "class" to "empty-row"), _uA(
                                    _cE("text", _uM("class" to "empty-text"), "暂无销售数据")
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            _cE(Fragment, null, RenderHelpers.renderList(unref(salesTrend), fun(item, __key, __index, _cached): Any {
                                return _cE("view", _uM("key" to item.date, "class" to "trend-row"), _uA(
                                    _cE("text", _uM("class" to "trend-date"), _tD(compactDate(item.date)), 1),
                                    _cE("view", _uM("class" to "trend-bar-track"), _uA(
                                        _cE("view", _uM("class" to "trend-bar", "style" to _nS("width:" + trendWidth(item.amount) + "%")), null, 4)
                                    )),
                                    _cE("text", _uM("class" to "trend-amount"), _tD(moneyText(item.amount)), 1)
                                ))
                            }
                            ), 128)
                        )),
                        _cE("view", _uM("class" to "section surface-animate"), _uA(
                            _cE("text", _uM("class" to "section-title"), "支付方式"),
                            _cE(Fragment, null, RenderHelpers.renderList(unref(paymentMethods), fun(item, __key, __index, _cached): Any {
                                return _cE("view", _uM("key" to item.key, "class" to "list-row"), _uA(
                                    _cE("text", _uM("class" to "list-label"), _tD(item.label), 1),
                                    _cE("text", _uM("class" to "list-value"), _tD(item.count.toString(10)) + " 单 / " + _tD(moneyText(item.amount)), 1)
                                ))
                            }
                            ), 128)
                        )),
                        _cE("view", _uM("class" to "section surface-animate"), _uA(
                            _cE("text", _uM("class" to "section-title"), "库存概览"),
                            _cE("view", _uM("class" to "inventory-grid"), _uA(
                                _cE("view", _uM("class" to "inventory-item"), _uA(
                                    _cE("text", _uM("class" to "inventory-value"), _tD(unref(inventory).stock_item_count.toString(10)), 1),
                                    _cE("text", _uM("class" to "inventory-label"), "库存项")
                                )),
                                _cE("view", _uM("class" to "inventory-item"), _uA(
                                    _cE("text", _uM("class" to "inventory-value"), _tD(unref(inventory).total_quantity.toString(10)), 1),
                                    _cE("text", _uM("class" to "inventory-label"), "库存总量")
                                )),
                                _cE("view", _uM("class" to "inventory-item"), _uA(
                                    _cE("text", _uM("class" to "inventory-value"), _tD(unref(inventory).low_stock_count.toString(10)), 1),
                                    _cE("text", _uM("class" to "inventory-label"), "低库存")
                                )),
                                _cE("view", _uM("class" to "inventory-item"), _uA(
                                    _cE("text", _uM("class" to "inventory-value"), _tD(unref(inventory).out_of_stock_count.toString(10)), 1),
                                    _cE("text", _uM("class" to "inventory-label"), "售罄")
                                ))
                            ))
                        )),
                        _cE("view", _uM("class" to "section surface-animate"), _uA(
                            _cE("text", _uM("class" to "section-title"), "提醒"),
                            if (unref(alerts).length == 0) {
                                _cE("view", _uM("key" to 0, "class" to "empty-row"), _uA(
                                    _cE("text", _uM("class" to "empty-text"), "暂无异常提醒")
                                ))
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            _cE(Fragment, null, RenderHelpers.renderList(unref(alerts), fun(item, __key, __index, _cached): Any {
                                return _cE("view", _uM("key" to item.label, "class" to "alert-row"), _uA(
                                    _cE("text", _uM("class" to _nC(if (item.level == "danger") {
                                        "alert-dot alert-dot-danger"
                                    } else {
                                        "alert-dot"
                                    }
                                    )), null, 2),
                                    _cE("text", _uM("class" to "alert-label"), _tD(item.label), 1),
                                    _cE("text", _uM("class" to "alert-value"), _tD(item.value.toString(10)), 1)
                                ))
                            }
                            ), 128)
                        )),
                        if (isTrue(unref(isLoading))) {
                            _cE("view", _uM("key" to 1, "class" to "loading-mask"), _uA(
                                _cE("view", _uM("class" to "loading-card"), _uA(
                                    _cE("text", _uM("class" to "loading-text"), "正在加载报表")
                                ))
                            ))
                        } else {
                            _cC("v-if", true)
                        }
                    ))
                ), 4)
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("page-scroll" to _pS(_uM("backgroundColor" to "#F6F7FB")), "page" to _pS(_uM("position" to "relative", "paddingLeft" to 10, "paddingRight" to 10, "paddingBottom" to 20, "backgroundColor" to "#F6F7FB")), "status-bar-space" to _pS(_uM("height" to CSS_VAR_STATUS_BAR_HEIGHT)), "topbar" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to 8, "paddingBottom" to 8)), "page-title" to _pS(_uM("fontSize" to 21, "lineHeight" to "26px", "fontWeight" to "700", "color" to "#111827")), "refresh-btn" to _pS(_uM("height" to 30, "paddingLeft" to 12, "paddingRight" to 12, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "borderTopWidth" to 0, "borderRightWidth" to 0, "borderBottomWidth" to 0, "borderLeftWidth" to 0, "backgroundColor" to "#111827", "transitionProperty" to "transform,backgroundColor", "transitionDuration" to "180ms", "transform:active" to "scale(0.96)", "backgroundColor:active" to "#334155")), "refresh-text" to _pS(_uM("fontSize" to 12, "fontWeight" to "600", "color" to "#FFFFFF")), "period-tabs" to _pS(_uM("flexDirection" to "row", "paddingTop" to 3, "paddingRight" to 3, "paddingBottom" to 3, "paddingLeft" to 3, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#E5E7EB")), "period-tab" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 30, "borderTopLeftRadius" to 6, "borderTopRightRadius" to 6, "borderBottomRightRadius" to 6, "borderBottomLeftRadius" to 6, "alignItems" to "center", "justifyContent" to "center", "transitionProperty" to "backgroundColor,transform", "transitionDuration" to "180ms")), "period-tab-active" to _pS(_uM("backgroundColor" to "#FFFFFF", "transform" to "scale(1.02)")), "period-text" to _pS(_uM("fontSize" to 12, "color" to "#64748B")), "period-text-active" to _pS(_uM("fontWeight" to "700", "color" to "#111827")), "notice-card" to _pS(_uM("borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E5E7EB", "borderRightColor" to "#E5E7EB", "borderBottomColor" to "#E5E7EB", "borderLeftColor" to "#E5E7EB", "backgroundColor" to "#FFFFFF", "marginTop" to 10, "paddingTop" to 12, "paddingRight" to 12, "paddingBottom" to 12, "paddingLeft" to 12)), "hero-card" to _pS(_uM("borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#111827", "borderRightColor" to "#111827", "borderBottomColor" to "#111827", "borderLeftColor" to "#111827", "backgroundColor" to "#111827", "marginTop" to 10, "paddingTop" to 15, "paddingRight" to 15, "paddingBottom" to 15, "paddingLeft" to 15)), "section" to _pS(_uM("borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E5E7EB", "borderRightColor" to "#E5E7EB", "borderBottomColor" to "#E5E7EB", "borderLeftColor" to "#E5E7EB", "backgroundColor" to "#FFFFFF", "marginTop" to 10, "paddingTop" to 11, "paddingRight" to 11, "paddingBottom" to 11, "paddingLeft" to 11)), "metric-card" to _pS(_uM("borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E5E7EB", "borderRightColor" to "#E5E7EB", "borderBottomColor" to "#E5E7EB", "borderLeftColor" to "#E5E7EB", "backgroundColor" to "#FFFFFF", "width" to "48%", "marginBottom" to 8, "paddingTop" to 11, "paddingRight" to 11, "paddingBottom" to 11, "paddingLeft" to 11, "transitionProperty" to "transform,borderColor", "transitionDuration" to "180ms", "transform:active" to "scale(0.985)", "borderTopColor:active" to "#CBD5E1", "borderRightColor:active" to "#CBD5E1", "borderBottomColor:active" to "#CBD5E1", "borderLeftColor:active" to "#CBD5E1")), "notice-title" to _pS(_uM("fontSize" to 14, "fontWeight" to "700", "color" to "#B91C1C")), "notice-desc" to _pS(_uM("marginTop" to 5, "fontSize" to 12, "lineHeight" to "18px", "color" to "#7F1D1D")), "hero-label" to _pS(_uM("fontSize" to 12, "color" to "#CBD5E1")), "hero-value" to _pS(_uM("marginTop" to 8, "fontSize" to 26, "lineHeight" to "32px", "fontWeight" to "700", "color" to "#FFFFFF")), "hero-meta" to _pS(_uM("flexDirection" to "row", "marginTop" to 9)), "hero-meta-text" to _pS(_uM("marginRight" to 12, "fontSize" to 12, "color" to "#D1D5DB")), "metrics-grid" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 10)), "metric-right" to _pS(_uM("marginLeft" to "4%")), "metric-label" to _pS(_uM("fontSize" to 11, "color" to "#64748B")), "metric-value" to _pS(_uM("marginTop" to 6, "fontSize" to 15, "lineHeight" to "21px", "fontWeight" to "700", "color" to "#111827")), "section-title" to _pS(_uM("fontSize" to 14, "lineHeight" to "20px", "fontWeight" to "700", "color" to "#111827")), "empty-row" to _pS(_uM("marginTop" to 8, "height" to 30, "justifyContent" to "center")), "empty-text" to _pS(_uM("fontSize" to 12, "color" to "#94A3B8")), "trend-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "marginTop" to 9)), "trend-date" to _pS(_uM("width" to 38, "fontSize" to 11, "color" to "#64748B")), "trend-bar-track" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 8, "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4, "backgroundColor" to "#E5E7EB", "overflow" to "hidden")), "trend-bar" to _pS(_uM("height" to 8, "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4, "backgroundColor" to "#2563EB")), "trend-amount" to _pS(_uM("width" to 74, "textAlign" to "right", "fontSize" to 11, "color" to "#111827")), "list-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "minHeight" to 32, "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#F1F5F9")), "alert-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "minHeight" to 32, "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#F1F5F9")), "list-label" to _pS(_uM("fontSize" to 13, "color" to "#334155")), "alert-label" to _pS(_uM("fontSize" to 13, "color" to "#334155", "flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "list-value" to _pS(_uM("fontSize" to 12, "fontWeight" to "600", "color" to "#111827")), "alert-value" to _pS(_uM("fontSize" to 12, "fontWeight" to "600", "color" to "#111827")), "inventory-grid" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 6)), "inventory-item" to _pS(_uM("width" to "50%", "paddingTop" to 8, "paddingBottom" to 6)), "inventory-value" to _pS(_uM("fontSize" to 17, "lineHeight" to "23px", "fontWeight" to "700", "color" to "#111827")), "inventory-label" to _pS(_uM("marginTop" to 3, "fontSize" to 11, "color" to "#64748B")), "alert-dot" to _pS(_uM("width" to 7, "height" to 7, "marginRight" to 8, "borderTopLeftRadius" to 4, "borderTopRightRadius" to 4, "borderBottomRightRadius" to 4, "borderBottomLeftRadius" to 4, "backgroundColor" to "#F59E0B")), "alert-dot-danger" to _pS(_uM("backgroundColor" to "#DC2626")), "loading-mask" to _pS(_uM("position" to "absolute", "left" to 0, "top" to 0, "right" to 0, "bottom" to 0, "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "rgba(15,23,42,0.12)")), "loading-card" to _pS(_uM("paddingTop" to 12, "paddingRight" to 16, "paddingBottom" to 12, "paddingLeft" to 16, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#111827")), "loading-text" to _pS(_uM("fontSize" to 13, "fontWeight" to "600", "color" to "#FFFFFF")), "@TRANSITION" to _uM("refresh-btn" to _uM("property" to "transform,backgroundColor", "duration" to "180ms"), "period-tab" to _uM("property" to "backgroundColor,transform", "duration" to "180ms"), "metric-card" to _uM("property" to "transform,borderColor", "duration" to "180ms")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
