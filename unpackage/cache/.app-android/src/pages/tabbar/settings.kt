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
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.showToast as uni_showToast
import io.dcloud.uniapp.extapi.switchTab as uni_switchTab
open class GenPagesTabbarSettings : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesTabbarSettings) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenPagesTabbarSettings
            val _cache = __ins.renderCache
            val groups = ref(_uA<MenuGroup>(MenuGroup(label = "店铺与经营概览", items = _uA(
                MenuItem(label = "商店", icon = "", iconPath = "/static/menu-icons/shop.svg", path = "/pages/shop/index", action = "navigateTo", disabled = false),
                MenuItem(label = "供应商", icon = "", iconPath = "/static/menu-icons/suppliers.svg", path = "/pages/suppliers/index", action = "navigateTo", disabled = false),
                MenuItem(label = "采购汇总", icon = "", iconPath = "/static/menu-icons/purchases.svg", path = "/pages/suppliers_procure/index", action = "navigateTo", disabled = false),
                MenuItem(label = "订单管理", icon = "", iconPath = "/static/menu-icons/orders.svg", path = "/pages/orders/index", action = "navigateTo", disabled = false),
                MenuItem(label = "采购单", icon = "", iconPath = "/static/menu-icons/purchases.svg", path = "/pages/purchases/index", action = "navigateTo", disabled = false),
                MenuItem(label = "支出管理", icon = "", iconPath = "/static/menu-icons/expenses.svg", path = "/pages/expenses/index", action = "navigateTo", disabled = false)
            )), MenuGroup(label = "商品与标签配置", items = _uA(
                MenuItem(label = "分类", icon = "", iconPath = "/static/menu-icons/category.svg", path = "/pages/category/index", action = "navigateTo", disabled = false),
                MenuItem(label = "收银分类", icon = "", iconPath = "/static/menu-icons/cash-category.svg", path = "/pages/kasa_category/index", action = "navigateTo", disabled = false),
                MenuItem(label = "商品属性", icon = "", iconPath = "/static/menu-icons/attributes.svg", path = "/pages/products/config-model/index?resource=attribute-type", action = "navigateTo", disabled = false),
                MenuItem(label = "商品折扣", icon = "", iconPath = "/static/menu-icons/discount.svg", path = "/pages/products/config-model/index?resource=discount", action = "navigateTo", disabled = false),
                MenuItem(label = "条形码序列", icon = "", iconPath = "/static/menu-icons/barcode.svg", path = "/pages/products/config-model/index?resource=barcode-sequence", action = "navigateTo", disabled = false),
                MenuItem(label = "标签模板", icon = "", iconPath = "/static/menu-icons/printer.svg", path = "/pages/label-templates/index", action = "navigateTo", disabled = false),
                MenuItem(label = "扫码测试", icon = "", iconPath = "/static/menu-icons/barcode.svg", path = "/pages/test/scan", action = "navigateTo", disabled = false)
            )), MenuGroup(label = "收银与店务配置", items = _uA(
                MenuItem(label = "收银台", icon = "", iconPath = "/static/menu-icons/cash-register.svg", path = null, action = "todo", disabled = true),
                MenuItem(label = "打印机设置", icon = "", iconPath = "/static/menu-icons/printer.svg", path = "/pages/printer-settings/index", action = "navigateTo", disabled = false),
                MenuItem(label = "支付", icon = "", iconPath = "/static/menu-icons/payment.svg", path = null, action = "todo", disabled = true),
                MenuItem(label = "权限", icon = "", iconPath = "/static/menu-icons/permission.svg", path = null, action = "todo", disabled = true)
            )), MenuGroup(label = "库存与盘点", items = _uA(
                MenuItem(label = "库存管理", icon = "", iconPath = "/static/menu-icons/inventory.svg", path = "/pages/inventory-management/index", action = "navigateTo", disabled = false),
                MenuItem(label = "库存位置", icon = "", iconPath = "/static/menu-icons/inventory-location.svg", path = "/pages/inventory-locations/index", action = "navigateTo", disabled = false),
                MenuItem(label = "调拨单", icon = "", iconPath = "/static/menu-icons/inventory-transfer.svg", path = "/pages/inventory-transfers/index", action = "navigateTo", disabled = false),
                MenuItem(label = "盘点单", icon = "", iconPath = "/static/menu-icons/inventory-check.svg", path = "/pages/inventory-checks/index", action = "navigateTo", disabled = false)
            )), MenuGroup(label = "价格与税务", items = _uA(
                MenuItem(label = "价格计算公式", icon = "", iconPath = "/static/menu-icons/pricing-formula.svg", path = "/pages/products/pricing-formula/index", action = "navigateTo", disabled = false),
                MenuItem(label = "KSeF发票", icon = "", iconPath = "/static/menu-icons/ksef-invoice.svg", path = "/pages/ksef/index", action = "navigateTo", disabled = false),
                MenuItem(label = "分享", icon = "", iconPath = "/static/menu-icons/share.svg", path = null, action = "todo", disabled = true)
            ))))
            fun gen_handleTap_fn(item: MenuItem) {
                if (item.disabled) {
                    uni_showToast(ShowToastOptions(title = "该功能正在开发中", icon = "none", duration = 3500))
                    return
                }
                if (item.action == "switchTab" && item.path != null) {
                    uni_switchTab(SwitchTabOptions(url = item.path!!))
                    return
                }
                if (item.action == "navigateTo" && item.path != null) {
                    uni_navigateTo(NavigateToOptions(url = item.path!!))
                }
            }
            val handleTap = ::gen_handleTap_fn
            fun chunkMenu(items: UTSArray<MenuItem>, size: Number = 4): UTSArray<UTSArray<MenuItem>> {
                val rows: UTSArray<UTSArray<MenuItem>> = _uA()
                run {
                    var i: Number = 0
                    while(i < items.length){
                        rows.push(items.slice(i, i + size))
                        i += size
                    }
                }
                return rows
            }
            fun gen_getGroupCount_fn(group: MenuGroup): Number {
                return group.items.length
            }
            val getGroupCount = ::gen_getGroupCount_fn
            return fun(): Any? {
                return _cE("scroll-view", _uM("class" to "page-scroll", "style" to _nS(_uM("flex" to "1")), "direction" to "vertical"), _uA(
                    _cE("view", _uM("class" to "page"), _uA(
                        _cE("view", _uM("class" to "status-bar-space")),
                        _cE("view", _uM("class" to "topbar"), _uA(
                            _cE("text", _uM("class" to "page-title"), "功能菜单")
                        )),
                        _cE("view", _uM("class" to "content"), _uA(
                            _cE(Fragment, null, RenderHelpers.renderList(unref(groups), fun(group, __key, __index, _cached): Any {
                                return _cE("view", _uM("key" to group.label, "class" to "group"), _uA(
                                    _cE("text", _uM("class" to "group-label"), _tD(group.label) + "（" + _tD(getGroupCount(group)) + "）", 1),
                                    _cE(Fragment, null, RenderHelpers.renderList(chunkMenu(group.items), fun(row, rowIndex, __index, _cached): Any {
                                        return _cE("view", _uM("key" to (group.label + "-" + rowIndex), "class" to "grid-row"), _uA(
                                            _cE(Fragment, null, RenderHelpers.renderList(row, fun(item, index, __index, _cached): Any {
                                                return _cE("view", _uM("key" to item.label, "class" to _nC(_uA(
                                                    "grid-item",
                                                    if (index !== row.length - 1) {
                                                        "grid-item-gap"
                                                    } else {
                                                        ""
                                                    }
                                                    ,
                                                    if (item.disabled) {
                                                        "grid-item-disabled"
                                                    } else {
                                                        ""
                                                    }
                                                )), "onClick" to fun(){
                                                    handleTap(item)
                                                }
                                                ), _uA(
                                                    if (isTrue(item.iconPath != null && item.iconPath != "")) {
                                                        _cE("image", _uM("key" to 0, "class" to "grid-image-icon", "src" to item.iconPath, "mode" to "aspectFit"), null, 8, _uA(
                                                            "src"
                                                        ))
                                                    } else {
                                                        _cE("text", _uM("key" to 1, "class" to "grid-icon"), _tD(item.icon), 1)
                                                    }
                                                    ,
                                                    _cE("text", _uM("class" to "grid-text"), _tD(item.label), 1)
                                                ), 10, _uA(
                                                    "onClick"
                                                ))
                                            }
                                            ), 128)
                                        ))
                                    }
                                    ), 128)
                                ))
                            }
                            ), 128)
                        ))
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
                return _uM("page-scroll" to _pS(_uM("backgroundColor" to "#F7F7F7")), "page" to _pS(_uM("backgroundColor" to "#F7F7F7", "paddingBottom" to 20)), "status-bar-space" to _pS(_uM("height" to CSS_VAR_STATUS_BAR_HEIGHT)), "topbar" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "paddingLeft" to 12, "paddingRight" to 12, "paddingTop" to 12, "paddingBottom" to 12)), "brand-box" to _pS(_uM("width" to 32, "height" to 32, "borderTopLeftRadius" to 8, "borderTopRightRadius" to 8, "borderBottomRightRadius" to 8, "borderBottomLeftRadius" to 8, "backgroundColor" to "#FFFFFF", "alignItems" to "center", "justifyContent" to "center")), "brand-text" to _pS(_uM("fontSize" to 16, "lineHeight" to "16px", "color" to "#111827", "fontWeight" to "bold")), "page-title" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "fontSize" to 16, "lineHeight" to "20px", "color" to "#111827", "fontWeight" to "bold")), "content" to _pS(_uM("paddingLeft" to 12, "paddingRight" to 12)), "group" to _pS(_uM("marginBottom" to 24)), "group-label" to _pS(_uM("fontSize" to 12, "lineHeight" to "18px", "color" to "#94A3B8", "marginLeft" to 8, "marginBottom" to 8)), "grid-row" to _pS(_uM("flexDirection" to "row", "marginBottom" to 8)), "grid-item" to _pS(_uM("width" to "23%", "height" to 70, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#FFFFFF", "alignItems" to "center", "paddingTop" to 18, "opacity" to 1)), "grid-item-disabled" to _pS(_uM("opacity" to 0.45)), "grid-item-gap" to _pS(_uM("marginRight" to "2.6666%")), "grid-icon" to _pS(_uM("fontSize" to 18, "lineHeight" to "18px", "color" to "#111827")), "grid-image-icon" to _pS(_uM("width" to 24, "height" to 24)), "grid-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "16px", "color" to "#111827", "textAlign" to "center", "marginTop" to 8)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
