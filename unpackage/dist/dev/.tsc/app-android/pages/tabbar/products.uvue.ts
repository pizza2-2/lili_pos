const __sfc__ = defineComponent({})
export default __sfc__
function GenPagesTabbarProductsRender(this: InstanceType<typeof __sfc__>): any | null {
const _ctx = this
const _cache = this.$.renderCache
  return _cE("scroll-view", _uM({
    class: "page-scroll",
    style: _nS(_uM({"flex":"1"})),
    direction: "vertical"
  }), [
    _cE("view", _uM({ class: "page" }), [
      _cE("view", _uM({ class: "status-bar-space" })),
      _cE("view", _uM({ class: "hero" }), [
        _cE("text", _uM({ class: "hero-title" }), "商品"),
        _cE("text", _uM({ class: "hero-desc" }), "商品管理、筛选组件和弹窗组件都可以从这里继续接业务。")
      ]),
      _cE("view", _uM({ class: "card" }), [
        _cE("text", _uM({ class: "card-label" }), "当前模块"),
        _cE("text", _uM({ class: "card-value" }), "商品中心"),
        _cE("text", _uM({ class: "card-desc" }), "这里作为底部导航首页，后续可继续接商品列表、分类、库存和价格体系。")
      ])
    ])
  ], 4 /* STYLE */)
}
const GenPagesTabbarProductsStyles = [_uM([["page-scroll", _pS(_uM([["backgroundColor", "#F6F7FB"]]))], ["page", _pS(_uM([["paddingLeft", 16], ["paddingRight", 16], ["paddingBottom", 24], ["backgroundColor", "#F6F7FB"]]))], ["status-bar-space", _pS(_uM([["height", CSS_VAR_STATUS_BAR_HEIGHT]]))], ["hero", _pS(_uM([["backgroundColor", "#14532D"], ["borderTopLeftRadius", 20], ["borderTopRightRadius", 20], ["borderBottomRightRadius", 20], ["borderBottomLeftRadius", 20], ["paddingTop", 20], ["paddingRight", 20], ["paddingBottom", 20], ["paddingLeft", 20]]))], ["hero-title", _pS(_uM([["fontSize", 24], ["lineHeight", "30px"], ["color", "#FFFFFF"], ["fontWeight", "bold"]]))], ["hero-desc", _pS(_uM([["fontSize", 14], ["lineHeight", "20px"], ["color", "#DCFCE7"], ["marginTop", 8]]))], ["card", _pS(_uM([["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", 18], ["borderTopRightRadius", 18], ["borderBottomRightRadius", 18], ["borderBottomLeftRadius", 18], ["paddingTop", 18], ["paddingRight", 18], ["paddingBottom", 18], ["paddingLeft", 18], ["marginTop", 14], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E5E7EB"], ["borderRightColor", "#E5E7EB"], ["borderBottomColor", "#E5E7EB"], ["borderLeftColor", "#E5E7EB"]]))], ["card-label", _pS(_uM([["fontSize", 13], ["lineHeight", "18px"], ["color", "#94A3B8"]]))], ["card-value", _pS(_uM([["fontSize", 24], ["lineHeight", "30px"], ["color", "#0F172A"], ["fontWeight", "bold"], ["marginTop", 10]]))], ["card-desc", _pS(_uM([["fontSize", 15], ["lineHeight", "22px"], ["color", "#475569"], ["marginTop", 10]]))]])]
