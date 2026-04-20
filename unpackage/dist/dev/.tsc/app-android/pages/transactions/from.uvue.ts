import _easycom_lili_universal_filter from '@/uni_modules/lili-universal-filter/components/lili-universal-filter/lili-universal-filter.uvue'
import { computed } from 'vue'


const __sfc__ = defineComponent({
  __name: 'from',
  setup(__props) {
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

const supplierId = ref('')

const supplierIdText = computed(() : string => {
	return supplierId.value == '' ? '未传入' : supplierId.value
})

onLoad((event : OnLoadOptions) => {
	const supplierIdValue = event['supplier_id']
	supplierId.value = supplierIdValue == null ? '' : (supplierIdValue as string)
})

return (): any | null => {

const _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter",_easycom_lili_universal_filter)

  return _cE("view", _uM({ class: "page" }), [
    _cV(_component_lili_universal_filter, _uM({
      title: "新增采购记录",
      showBack: true,
      showSearch: false,
      showHome: true,
      homePath: "/pages/suppliers/index",
      backgroundColor: "#EEF2F7"
    })),
    _cE("scroll-view", _uM({
      style: _nS(_uM({"flex":"1"})),
      class: "page-scroll"
    }), [
      _cE("view", _uM({ class: "page-content" }), [
        _cE("view", _uM({ class: "hero-card" }), [
          _cE("text", _uM({ class: "hero-title" }), "采购记录录入页"),
          _cE("text", _uM({ class: "hero-desc" }), "当前已接入从供应商菜单进入的跳转，后续可以在这里补采购单表单。")
        ]),
        _cE("view", _uM({ class: "info-card" }), [
          _cE("text", _uM({ class: "info-label" }), "供应商ID"),
          _cE("text", _uM({ class: "info-value" }), _tD(supplierIdText.value), 1 /* TEXT */)
        ]),
        _cE("view", _uM({ class: "hint-card" }), [
          _cE("text", _uM({ class: "hint-text" }), "请基于该供应商补充采购时间、采购金额、备注和附件等字段。")
        ])
      ])
    ], 4 /* STYLE */)
  ])
}
}

})
export default __sfc__
const GenPagesTransactionsFromStyles = [_uM([["page", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#EEF2F7"]]))], ["page-scroll", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"]]))], ["page-content", _pS(_uM([["paddingTop", 12], ["paddingRight", 12], ["paddingBottom", 12], ["paddingLeft", 12]]))], ["hero-card", _pS(_uM([["paddingTop", 16], ["paddingRight", 16], ["paddingBottom", 16], ["paddingLeft", 16], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["backgroundColor", "#FFFFFF"]]))], ["hero-title", _pS(_uM([["fontSize", 18], ["fontWeight", "600"], ["color", "#1F2937"]]))], ["hero-desc", _pS(_uM([["marginTop", 8], ["fontSize", 14], ["lineHeight", "1.5em"], ["color", "#6B7280"]]))], ["info-card", _pS(_uM([["marginTop", 12], ["paddingTop", 16], ["paddingRight", 16], ["paddingBottom", 16], ["paddingLeft", 16], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["backgroundColor", "#FFFFFF"]]))], ["info-label", _pS(_uM([["fontSize", 13], ["color", "#6B7280"]]))], ["info-value", _pS(_uM([["marginTop", 8], ["fontSize", 16], ["fontWeight", "600"], ["color", "#111827"]]))], ["hint-card", _pS(_uM([["marginTop", 12], ["paddingTop", 16], ["paddingRight", 16], ["paddingBottom", 16], ["paddingLeft", 16], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["backgroundColor", "#DBEAFE"]]))], ["hint-text", _pS(_uM([["fontSize", 14], ["lineHeight", "1.5em"], ["color", "#1E3A8A"]]))]])]
