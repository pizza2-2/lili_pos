type MenuItem = { __$originalPosition?: UTSSourceMapPosition<"MenuItem", "pages/tabbar/settings.uvue", 42, 6>;
	label: string
	icon: string
	iconPath: string | null
	path: string | null
	action: string
	disabled: boolean
}

type MenuGroup = { __$originalPosition?: UTSSourceMapPosition<"MenuGroup", "pages/tabbar/settings.uvue", 51, 6>;
	label: string
	items: MenuItem[]
}


const __sfc__ = defineComponent({
  __name: 'settings',
  setup(__props) {
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

const groups = ref<MenuGroup[]>([
	{
		label: '店铺与经营概览',
		items: [
			{ label: '商店', icon: '', iconPath: '/static/menu-icons/shop.svg', path: '/pages/shop/index', action: 'navigateTo', disabled: false },
			{ label: '供应商', icon: '', iconPath: '/static/menu-icons/suppliers.svg', path: '/pages/suppliers/index', action: 'navigateTo', disabled: false },
			{ label: '采购汇总', icon: '', iconPath: '/static/menu-icons/purchases.svg', path: '/pages/suppliers_procure/index', action: 'navigateTo', disabled: false },
			{ label: '订单管理', icon: '', iconPath: '/static/menu-icons/orders.svg', path: '/pages/orders/index', action: 'navigateTo', disabled: false },
			{ label: '采购单', icon: '', iconPath: '/static/menu-icons/purchases.svg', path: '/pages/purchases/index', action: 'navigateTo', disabled: false },
			{ label: '支出管理', icon: '', iconPath: '/static/menu-icons/expenses.svg', path: '/pages/expenses/index', action: 'navigateTo', disabled: false },
		],
	},
	{
		label: '商品与标签配置',
		items: [
			{ label: '分类', icon: '', iconPath: '/static/menu-icons/category.svg', path: '/pages/category/index', action: 'navigateTo', disabled: false },
			{ label: '收银分类', icon: '', iconPath: '/static/menu-icons/cash-category.svg', path: '/pages/kasa_category/index', action: 'navigateTo', disabled: false },
			{ label: '商品属性', icon: '', iconPath: '/static/menu-icons/attributes.svg', path: '/pages/products/config-model/index?resource=attribute-type', action: 'navigateTo', disabled: false },
			{ label: '商品折扣', icon: '', iconPath: '/static/menu-icons/discount.svg', path: '/pages/products/config-model/index?resource=discount', action: 'navigateTo', disabled: false },
			{ label: '条形码序列', icon: '', iconPath: '/static/menu-icons/barcode.svg', path: '/pages/products/config-model/index?resource=barcode-sequence', action: 'navigateTo', disabled: false },
			{ label: '标签模板', icon: '', iconPath: '/static/menu-icons/printer.svg', path: '/pages/label-templates/index', action: 'navigateTo', disabled: false },
			{ label: '扫码测试', icon: '', iconPath: '/static/menu-icons/barcode.svg', path: '/pages/test/scan', action: 'navigateTo', disabled: false },
		],
	},
	{
		label: '收银与店务配置',
		items: [
			{ label: '收银台', icon: '', iconPath: '/static/menu-icons/cash-register.svg', path: null, action: 'todo', disabled: true },
			{ label: '打印机设置', icon: '', iconPath: '/static/menu-icons/printer.svg', path: '/pages/printer-settings/index', action: 'navigateTo', disabled: false },
			{ label: '支付', icon: '', iconPath: '/static/menu-icons/payment.svg', path: null, action: 'todo', disabled: true },
			{ label: '权限', icon: '', iconPath: '/static/menu-icons/permission.svg', path: null, action: 'todo', disabled: true },
		],
	},
	{
		label: '库存与盘点',
		items: [
			{ label: '库存管理', icon: '', iconPath: '/static/menu-icons/inventory.svg', path: '/pages/inventory-management/index', action: 'navigateTo', disabled: false },
			{ label: '库存位置', icon: '', iconPath: '/static/menu-icons/inventory-location.svg', path: '/pages/inventory-locations/index', action: 'navigateTo', disabled: false },
			{ label: '调拨单', icon: '', iconPath: '/static/menu-icons/inventory-transfer.svg', path: '/pages/inventory-transfers/index', action: 'navigateTo', disabled: false },
			{ label: '盘点单', icon: '', iconPath: '/static/menu-icons/inventory-check.svg', path: '/pages/inventory-checks/index', action: 'navigateTo', disabled: false },
		],
	},
	{
		label: '价格与税务',
		items: [
			{ label: '价格计算公式', icon: '', iconPath: '/static/menu-icons/pricing-formula.svg', path: '/pages/products/pricing-formula/index', action: 'navigateTo', disabled: false },
			{ label: 'KSeF发票', icon: '', iconPath: '/static/menu-icons/ksef-invoice.svg', path: '/pages/ksef/index', action: 'navigateTo', disabled: false },
			{ label: '分享', icon: '', iconPath: '/static/menu-icons/share.svg', path: null, action: 'todo', disabled: true },
		],
	},
])

function handleTap(item: MenuItem) {
	if (item.disabled) {
		uni.showToast({
			title: '该功能正在开发中',
			icon: 'none', duration: 3500,
		})
		return
	}

	if (item.action == 'switchTab' && item.path != null) {
		uni.switchTab({
			url: item.path,
		})
		return
	}

	if (item.action == 'navigateTo' && item.path != null) {
		uni.navigateTo({
			url: item.path,
		})
	}
}

function chunkMenu(items: MenuItem[], size: number = 4): MenuItem[][] {
	const rows: MenuItem[][] = []
	for (let i = 0; i < items.length; i += size) {
		rows.push(items.slice(i, i + size))
	}
	return rows
}

function getGroupCount(group: MenuGroup): number {
	return group.items.length
}

return (): any | null => {

  return _cE("scroll-view", _uM({
    class: "page-scroll",
    style: _nS(_uM({"flex":"1"})),
    direction: "vertical"
  }), [
    _cE("view", _uM({ class: "page" }), [
      _cE("view", _uM({ class: "status-bar-space" })),
      _cE("view", _uM({ class: "topbar" }), [
        _cE("text", _uM({ class: "page-title" }), "功能菜单")
      ]),
      _cE("view", _uM({ class: "content" }), [
        _cE(Fragment, null, RenderHelpers.renderList(unref(groups), (group, __key, __index, _cached): any => {
          return _cE("view", _uM({
            key: group.label,
            class: "group"
          }), [
            _cE("text", _uM({ class: "group-label" }), _tD(group.label) + "（" + _tD(getGroupCount(group)) + "）", 1 /* TEXT */),
            _cE(Fragment, null, RenderHelpers.renderList(chunkMenu(group.items), (row, rowIndex, __index, _cached): any => {
              return _cE("view", _uM({
                key: group.label + '-' + rowIndex,
                class: "grid-row"
              }), [
                _cE(Fragment, null, RenderHelpers.renderList(row, (item, index, __index, _cached): any => {
                  return _cE("view", _uM({
                    key: item.label,
                    class: _nC(['grid-item', index !== row.length - 1 ? 'grid-item-gap' : '', item.disabled ? 'grid-item-disabled' : '']),
                    onClick: () => {handleTap(item)}
                  }), [
                    isTrue(item.iconPath != null && item.iconPath != '')
                      ? _cE("image", _uM({
                          key: 0,
                          class: "grid-image-icon",
                          src: item.iconPath,
                          mode: "aspectFit"
                        }), null, 8 /* PROPS */, ["src"])
                      : _cE("text", _uM({
                          key: 1,
                          class: "grid-icon"
                        }), _tD(item.icon), 1 /* TEXT */),
                    _cE("text", _uM({ class: "grid-text" }), _tD(item.label), 1 /* TEXT */)
                  ], 10 /* CLASS, PROPS */, ["onClick"])
                }), 128 /* KEYED_FRAGMENT */)
              ])
            }), 128 /* KEYED_FRAGMENT */)
          ])
        }), 128 /* KEYED_FRAGMENT */)
      ])
    ])
  ], 4 /* STYLE */)
}
}

})
export default __sfc__
const GenPagesTabbarSettingsStyles = [_uM([["page-scroll", _pS(_uM([["backgroundColor", "#F7F7F7"]]))], ["page", _pS(_uM([["backgroundColor", "#F7F7F7"], ["paddingBottom", 20]]))], ["status-bar-space", _pS(_uM([["height", CSS_VAR_STATUS_BAR_HEIGHT]]))], ["topbar", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"], ["paddingLeft", 12], ["paddingRight", 12], ["paddingTop", 12], ["paddingBottom", 12]]))], ["brand-box", _pS(_uM([["width", 32], ["height", 32], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#FFFFFF"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["brand-text", _pS(_uM([["fontSize", 16], ["lineHeight", "16px"], ["color", "#111827"], ["fontWeight", "bold"]]))], ["page-title", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["fontSize", 16], ["lineHeight", "20px"], ["color", "#111827"], ["fontWeight", "bold"]]))], ["content", _pS(_uM([["paddingLeft", 12], ["paddingRight", 12]]))], ["group", _pS(_uM([["marginBottom", 24]]))], ["group-label", _pS(_uM([["fontSize", 12], ["lineHeight", "18px"], ["color", "#94A3B8"], ["marginLeft", 8], ["marginBottom", 8]]))], ["grid-row", _pS(_uM([["flexDirection", "row"], ["marginBottom", 8]]))], ["grid-item", _pS(_uM([["width", "23%"], ["height", 70], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["backgroundColor", "#FFFFFF"], ["alignItems", "center"], ["paddingTop", 18], ["opacity", 1]]))], ["grid-item-disabled", _pS(_uM([["opacity", 0.45]]))], ["grid-item-gap", _pS(_uM([["marginRight", "2.6666%"]]))], ["grid-icon", _pS(_uM([["fontSize", 18], ["lineHeight", "18px"], ["color", "#111827"]]))], ["grid-image-icon", _pS(_uM([["width", 24], ["height", 24]]))], ["grid-text", _pS(_uM([["fontSize", 12], ["lineHeight", "16px"], ["color", "#111827"], ["textAlign", "center"], ["marginTop", 8]]))]])]
