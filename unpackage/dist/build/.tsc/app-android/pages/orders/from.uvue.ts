import _easycom_lili_universal_filter from '@/uni_modules/lili-universal-filter/components/lili-universal-filter/lili-universal-filter.uvue'
import _easycom_lili_UniversaForm from '@/uni_modules/lili-UniversaForm/components/lili-UniversaForm/lili-UniversaForm.uvue'
import { computed } from 'vue'
import { getOrderDetail, OrderItem } from '@/pkg/api/modules/orders.uts'

type PayloadRow = {
	key: string
	title: string
	desc: string
	quantity: string
}


const __sfc__ = defineComponent({
  __name: 'from',
  setup(__props) {
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

const orderId = ref('')
const orderDetail = ref<OrderItem | null>(null)
const isLoading = ref(false)
const errorMessage = ref('')
const initialData = ref<UTSJSONObject>({} as UTSJSONObject)

function stringValue(value: any | null, fallback: string = ''): string {
	if (value == null) return fallback
	const text = '' + value
	if (text == '') return fallback
	return text
}

function parseErrorMessage(error: any, fallback: string): string {
	let message = fallback
	if (error != null) {
		const directMessage = (error as Error).message
		if (directMessage != null && directMessage != '') message = directMessage
		const errorText = JSON.stringify(error)
		if (errorText != null && errorText != '') {
			const parsedError = JSON.parseObject<UTSJSONObject>(errorText)
			if (parsedError != null) {
				const rawMessage = parsedError['message']
				if (rawMessage != null) {
					const parsedMessage = rawMessage as string
					if (parsedMessage != '') message = parsedMessage
				}
			}
			if (message == fallback) message = errorText
		}
	}
	return message
}

function parseObjectArray(value: any | null): UTSJSONObject[] {
	if (value == null) return [] as UTSJSONObject[]
	const text = JSON.stringify(value)
	if (text == null || text == '') return [] as UTSJSONObject[]
	const parsed = JSON.parseArray<UTSJSONObject>(text)
	if (parsed == null) return [] as UTSJSONObject[]
	return parsed!
}

function boolText(value: boolean): string {
	return value ? '是' : '否'
}

function statusText(item: OrderItem): string {
	const text = stringValue(item.status_display, item.status)
	if (text != '') return text
	if (item.status == 'received') return '已接收'
	if (item.status == 'processed') return '已处理'
	if (item.status == 'failed') return '处理失败'
	return '-'
}

function paymentText(item: OrderItem): string {
	const text = stringValue(item.payment_method_display, item.payment_method)
	if (text != '') return text
	if (item.payment_method == 'cash') return '现金'
	if (item.payment_method == 'card') return '银行卡'
	if (item.payment_method == 'mixed') return '混合支付'
	if (item.payment_method == 'other') return '其他'
	return '-'
}

function payloadTextValue(payload: UTSJSONObject): string {
	const text = JSON.stringify(payload)
	if (text == null || text == '') return '{}'
	return text
}

function getPayloadItems(payload: UTSJSONObject): UTSJSONObject[] {
	let rows = parseObjectArray(payload['items'])
	if (rows.length > 0) return rows
	rows = parseObjectArray(payload['products'])
	if (rows.length > 0) return rows
	return parseObjectArray(payload['cart'])
}

function buildRowTitle(row: UTSJSONObject, index: number): string {
	const name = stringValue(row['name'], stringValue(row['product_name'], stringValue(row['title'])))
	if (name != '') return name
	const barcode = stringValue(row['barcode'])
	if (barcode != '') return '商品 ' + barcode
	return '商品行 ' + (index + 1).toString()
}

function buildRowDesc(row: UTSJSONObject): string {
	const barcode = stringValue(row['barcode'], '-')
	const price = stringValue(row['price'], stringValue(row['unit_price'], '-'))
	const amount = stringValue(row['amount'], stringValue(row['total'], '-'))
	return '条码 ' + barcode + ' / 单价 ' + price + ' / 金额 ' + amount
}

function buildInitialData(item: OrderItem): UTSJSONObject {
	return {
		order_number: item.order_number,
		shop_name: stringValue(item.shop_name, item.shop > 0 ? '店铺 #' + item.shop.toString() : '-'),
		order_time: stringValue(item.order_time, '-'),
		created_at: stringValue(item.created_at, '-'),
		cashier_id: stringValue(item.cashier_id, '-'),
		kasa_number: stringValue(item.kasa_number, '-'),
		payment_method_text: paymentText(item),
		subtotal: stringValue(item.subtotal, '0.00'),
		discount_amount: stringValue(item.discount_amount, '0.00'),
		tax_amount: stringValue(item.tax_amount, '0.00'),
		total_amount: stringValue(item.total_amount, '0.00'),
		status_text: statusText(item),
		error_message: stringValue(item.error_message, '-'),
		inventory_deducted_text: boolText(item.inventory_deducted),
		inventory_deduct_time: stringValue(item.inventory_deduct_time, '-'),
		inventory_deduct_error: stringValue(item.inventory_deduct_error, '-'),
	} as UTSJSONObject
}

async function loadDetail() {
	if (orderId.value == '') {
		errorMessage.value = '缺少订单 ID'
		return
	}
	if (isLoading.value) return
	isLoading.value = true
	errorMessage.value = ''
	try {
		const detail = await getOrderDetail(orderId.value)
		orderDetail.value = detail
		initialData.value = buildInitialData(detail)
	} catch (error) {
		orderDetail.value = null
		initialData.value = {} as UTSJSONObject
		errorMessage.value = parseErrorMessage(error, '订单详情加载失败')
	} finally {
		isLoading.value = false
	}
}

function handleNoop(payload: UTSJSONObject) {
}

function handleDirtyChange(value: boolean) {
}

const pageTitle = computed((): string => {
	if (orderDetail.value == null) return '订单详情'
	const detail = orderDetail.value as OrderItem
	return '订单 ' + detail.order_number
})

const payloadText = computed((): string => {
	if (orderDetail.value == null) return '{}'
	const detail = orderDetail.value as OrderItem
	return payloadTextValue(detail.payload)
})

const itemRows = computed((): PayloadRow[] => {
	const result: PayloadRow[] = []
	if (orderDetail.value == null) return result
	const detail = orderDetail.value as OrderItem
	const rows = getPayloadItems(detail.payload)
	for (let index = 0; index < rows.length; index += 1) {
		const row = rows[index]
		result.push({
			key: index.toString(),
			title: buildRowTitle(row, index),
			desc: buildRowDesc(row),
			quantity: 'x ' + stringValue(row['quantity'], '0'),
		} as PayloadRow)
	}
	return result
})

function copyPayload() {
	uni.setClipboardData({
		data: payloadText.value,
		success: () => { uni.showToast({ title: 'Payload 已复制', icon: 'success' }) },
	})
}

const formSections = ref<UTSJSONObject[]>([
	{
		key: 'base',
		title: '订单信息',
		defaultOpen: true,
		fields: [
			{ key: 'order_number', label: '订单号', type: 'input', readonly: true } as UTSJSONObject,
			{ key: 'shop_name', label: '店铺', type: 'input', readonly: true } as UTSJSONObject,
			{ key: 'order_time', label: '订单时间', type: 'input', readonly: true } as UTSJSONObject,
			{ key: 'created_at', label: '接收时间', type: 'input', readonly: true } as UTSJSONObject,
			{ key: 'cashier_id', label: '收银员', type: 'input', readonly: true } as UTSJSONObject,
			{ key: 'kasa_number', label: '收银台', type: 'input', readonly: true } as UTSJSONObject,
		] as UTSJSONObject[],
	} as UTSJSONObject,
	{
		key: 'amount',
		title: '金额信息',
		defaultOpen: true,
		fields: [
			{ key: 'payment_method_text', label: '支付方式', type: 'input', readonly: true } as UTSJSONObject,
			{ key: 'subtotal', label: '小计', type: 'input', readonly: true } as UTSJSONObject,
			{ key: 'discount_amount', label: '折扣', type: 'input', readonly: true } as UTSJSONObject,
			{ key: 'tax_amount', label: '税额', type: 'input', readonly: true } as UTSJSONObject,
			{ key: 'total_amount', label: '总金额', type: 'input', readonly: true } as UTSJSONObject,
		] as UTSJSONObject[],
	} as UTSJSONObject,
	{
		key: 'status',
		title: '处理状态',
		defaultOpen: true,
		fields: [
			{ key: 'status_text', label: '订单状态', type: 'input', readonly: true } as UTSJSONObject,
			{ key: 'error_message', label: '订单错误', type: 'textarea', readonly: true } as UTSJSONObject,
			{ key: 'inventory_deducted_text', label: '库存已扣减', type: 'input', readonly: true } as UTSJSONObject,
			{ key: 'inventory_deduct_time', label: '扣减时间', type: 'input', readonly: true } as UTSJSONObject,
			{ key: 'inventory_deduct_error', label: '扣减错误', type: 'textarea', readonly: true } as UTSJSONObject,
		] as UTSJSONObject[],
	} as UTSJSONObject,
])

onLoad((event: OnLoadOptions) => {
	const idValue = event['id']
	orderId.value = idValue == null ? '' : ('' + idValue)
	loadDetail()
})

return (): any | null => {

const _component_lili_universal_filter = resolveEasyComponent("lili-universal-filter",_easycom_lili_universal_filter)
const _component_lili_UniversaForm = resolveEasyComponent("lili-UniversaForm",_easycom_lili_UniversaForm)

  return _cE("view", _uM({ class: "page" }), [
    _cV(_component_lili_universal_filter, _uM({
      title: pageTitle.value,
      showBack: true,
      showSearch: false,
      showHome: true,
      homePath: "/pages/orders/index",
      backgroundColor: "#EEF2F7"
    }), null, 8 /* PROPS */, ["title"]),
    _cE("scroll-view", _uM({
      style: _nS(_uM({"flex":"1"})),
      class: "page-scroll"
    }), [
      _cE("view", _uM({ class: "page-content" }), [
        isTrue(unref(errorMessage) != '' && !unref(isLoading))
          ? _cE("view", _uM({
              key: 0,
              class: "error-card"
            }), [
              _cE("text", _uM({ class: "error-title" }), "加载失败"),
              _cE("text", _uM({ class: "error-desc" }), _tD(unref(errorMessage)), 1 /* TEXT */),
              _cE("view", _uM({
                class: "retry-btn",
                onClick: loadDetail
              }), [
                _cE("text", _uM({ class: "retry-btn-text" }), "重新加载")
              ])
            ])
          : _cC("v-if", true),
        isTrue(unref(isLoading))
          ? _cE("view", _uM({
              key: 1,
              class: "loading-card"
            }), [
              _cE("text", _uM({ class: "loading-text" }), "正在加载订单详情")
            ])
          : _cC("v-if", true),
        isTrue(!unref(isLoading))
          ? _cV(_component_lili_UniversaForm, _uM({
              key: 2,
              mode: "edit",
              formSections: unref(formSections),
              initialData: unref(initialData),
              showFooter: false,
              enableBackConfirm: false,
              onSubmit: handleNoop,
              onCancel: handleNoop,
              onDiscardLeave: handleNoop,
              onSaveRequest: handleNoop,
              onDirtyChange: handleDirtyChange
            }), null, 8 /* PROPS */, ["formSections", "initialData"])
          : _cC("v-if", true),
        isTrue(!unref(isLoading))
          ? _cE("view", _uM({
              key: 3,
              class: "section-card"
            }), [
              _cE("view", _uM({ class: "section-header" }), [
                _cE("text", _uM({ class: "section-title" }), "商品行"),
                _cE("text", _uM({ class: "section-subtitle" }), _tD(itemRows.value.length.toString()) + " 项", 1 /* TEXT */)
              ]),
              itemRows.value.length == 0
                ? _cE("view", _uM({
                    key: 0,
                    class: "empty-line"
                  }), [
                    _cE("text", _uM({ class: "empty-line-text" }), "payload 中没有 items / products / cart 商品行")
                  ])
                : _cC("v-if", true),
              _cE(Fragment, null, RenderHelpers.renderList(itemRows.value, (row, __key, __index, _cached): any => {
                return _cE("view", _uM({
                  key: row.key,
                  class: "item-row"
                }), [
                  _cE("view", _uM({ class: "item-row-main" }), [
                    _cE("text", _uM({ class: "item-title" }), _tD(row.title), 1 /* TEXT */),
                    _cE("text", _uM({ class: "item-desc" }), _tD(row.desc), 1 /* TEXT */)
                  ]),
                  _cE("text", _uM({ class: "item-qty" }), _tD(row.quantity), 1 /* TEXT */)
                ])
              }), 128 /* KEYED_FRAGMENT */)
            ])
          : _cC("v-if", true),
        isTrue(!unref(isLoading))
          ? _cE("view", _uM({
              key: 4,
              class: "section-card"
            }), [
              _cE("view", _uM({ class: "section-header" }), [
                _cE("text", _uM({ class: "section-title" }), "原始 Payload"),
                _cE("view", _uM({
                  class: "copy-btn",
                  onClick: copyPayload
                }), [
                  _cE("text", _uM({ class: "copy-btn-text" }), "复制")
                ])
              ]),
              _cE("text", _uM({ class: "payload-text" }), _tD(payloadText.value), 1 /* TEXT */)
            ])
          : _cC("v-if", true)
      ])
    ], 4 /* STYLE */)
  ])
}
}

})
export default __sfc__
const GenPagesOrdersFromStyles = [_uM([["page", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#EEF2F7"]]))], ["page-scroll", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["backgroundColor", "#EEF2F7"]]))], ["page-content", _pS(_uM([["paddingTop", 6], ["paddingRight", 6], ["paddingBottom", 96], ["paddingLeft", 6]]))], ["loading-card", _pS(_uM([["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["paddingTop", 20], ["paddingRight", 20], ["paddingBottom", 20], ["paddingLeft", 20], ["marginBottom", 8], ["alignItems", "center"], ["justifyContent", "center"]]))], ["loading-text", _pS(_uM([["fontSize", 14], ["lineHeight", "20px"], ["color", "#64748B"]]))], ["error-card", _pS(_uM([["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["paddingTop", 18], ["paddingRight", 18], ["paddingBottom", 18], ["paddingLeft", 18], ["marginBottom", 10], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#FECACA"], ["borderRightColor", "#FECACA"], ["borderBottomColor", "#FECACA"], ["borderLeftColor", "#FECACA"], ["alignItems", "center"]]))], ["error-title", _pS(_uM([["fontSize", 18], ["lineHeight", "24px"], ["color", "#B42318"], ["fontWeight", "bold"]]))], ["error-desc", _pS(_uM([["fontSize", 14], ["lineHeight", "20px"], ["color", "#7F1D1D"], ["marginTop", 8], ["textAlign", "center"]]))], ["retry-btn", _pS(_uM([["marginTop", 14], ["height", 40], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#0F172A"], ["paddingLeft", 18], ["paddingRight", 18], ["alignItems", "center"], ["justifyContent", "center"]]))], ["retry-btn-text", _pS(_uM([["fontSize", 14], ["color", "#FFFFFF"]]))], ["section-card", _pS(_uM([["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["paddingTop", 14], ["paddingRight", 14], ["paddingBottom", 14], ["paddingLeft", 14], ["marginTop", 8], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E5E7EB"], ["borderRightColor", "#E5E7EB"], ["borderBottomColor", "#E5E7EB"], ["borderLeftColor", "#E5E7EB"]]))], ["section-header", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "space-between"], ["marginBottom", 10]]))], ["section-title", _pS(_uM([["fontSize", 16], ["lineHeight", "22px"], ["color", "#0F172A"], ["fontWeight", "bold"]]))], ["section-subtitle", _pS(_uM([["fontSize", 13], ["lineHeight", "18px"], ["color", "#64748B"]]))], ["empty-line", _pS(_uM([["paddingTop", 12], ["paddingBottom", 12], ["alignItems", "center"]]))], ["empty-line-text", _pS(_uM([["fontSize", 13], ["lineHeight", "18px"], ["color", "#94A3B8"]]))], ["item-row", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "space-between"], ["paddingTop", 10], ["paddingBottom", 10], ["borderTopWidth", 1], ["borderTopStyle", "solid"], ["borderTopColor", "#EEF2F7"]]))], ["item-row-main", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["paddingRight", 10]]))], ["item-title", _pS(_uM([["fontSize", 14], ["lineHeight", "20px"], ["color", "#111827"], ["fontWeight", "bold"]]))], ["item-desc", _pS(_uM([["fontSize", 12], ["lineHeight", "18px"], ["color", "#64748B"], ["marginTop", 2]]))], ["item-qty", _pS(_uM([["fontSize", 14], ["lineHeight", "20px"], ["color", "#0F172A"], ["fontWeight", "bold"]]))], ["copy-btn", _pS(_uM([["height", 30], ["paddingLeft", 12], ["paddingRight", 12], ["borderTopLeftRadius", 8], ["borderTopRightRadius", 8], ["borderBottomRightRadius", 8], ["borderBottomLeftRadius", 8], ["backgroundColor", "#0F172A"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["copy-btn-text", _pS(_uM([["fontSize", 13], ["color", "#FFFFFF"]]))], ["payload-text", _pS(_uM([["fontSize", 12], ["lineHeight", "18px"], ["color", "#334155"]]))]])]
