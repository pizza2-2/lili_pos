import { request } from '../index.uts'

export type KsefInvoiceListQuery = {
	search: string | null
	page: number
	page_size: number
	sync_status: string | null
	is_paid: string | null
}

export type KsefInvoiceItem = {
	id: number
	company: number
	subject_type: string
	ksef_number: string
	invoice_number: string
	invoice_type: string
	seller_name: string
	seller_nip: string
	buyer_name: string
	buyer_nip: string
	supplier: number
	supplier_name: string
	issue_date: string
	sale_date: string
	currency: string
	net_amount: string
	tax_amount: string
	gross_amount: string
	amount_due: string
	payment_due_date: string
	payment_method: string
	bank_account_number: string
	bank_name: string
	is_paid: boolean
	paid_amount: string
	paid_at: string
	remark: string
	sync_status: string
	raw_xml_downloaded_at: string
	files_count: number
	pdf_download_url: string
	created_at: string
	updated_at: string
}

export type KsefInvoiceDetail = {
	id: number
	company: number
	subject_type: string
	ksef_number: string
	invoice_number: string
	invoice_type: string
	seller_name: string
	seller_nip: string
	buyer_name: string
	buyer_nip: string
	supplier: number
	supplier_name: string
	issue_date: string
	sale_date: string
	currency: string
	net_amount: string
	tax_amount: string
	gross_amount: string
	amount_due: string
	payment_due_date: string
	payment_method: string
	bank_account_number: string
	bank_name: string
	is_paid: boolean
	paid_amount: string
	paid_at: string
	remark: string
	sync_status: string
	raw_xml_downloaded_at: string
	files_count: number
	pdf_download_url: string
	created_at: string
	updated_at: string
	metadata: UTSJSONObject
	raw_xml: string
	last_error: string
	payment_note: string
	xml_summary: UTSJSONObject
	media_files: UTSJSONObject[]
}

export type KsefPaymentUpdateData = {
	is_paid: boolean
	paid_amount: string
	paid_at: string | null
	payment_note: string
	remark: string
}

export type KsefInvoiceListResponse = {
	results: KsefInvoiceItem[]
	count: number
	total_count: number
	total_pages: number
	current_page: number
	page_size: number
	summary: UTSJSONObject
}

export type KsefAutoSyncStatus = {
	enabled: boolean
	metadata_interval_seconds: number
	xml_interval_seconds: number
	xml_batch_size: number
	xml_delay_seconds: number
	pending_xml_count: number
	last_success_at: string
	last_success_requested_to: string
	last_failed_at: string
	last_failed_message: string
}

function stringValue(value: any | null): string {
	if (value == null) {
		return ''
	}
	return '' + value
}

function intValue(value: any | null): number {
	if (value == null) {
		return 0
	}
	const parsed = parseInt('' + value)
	if (isNaN(parsed)) {
		return 0
	}
	return parsed
}

function floatValue(value: any | null): number {
	if (value == null) {
		return 0
	}
	const parsed = parseFloat('' + value)
	if (isNaN(parsed)) {
		return 0
	}
	return parsed
}

function boolValue(value: any | null): boolean {
	if (value == null) {
		return false
	}
	const text = ('' + value).toLowerCase()
	return text == 'true' || text == '1'
}

function objectValue(value: any | null): UTSJSONObject {
	if (value == null) {
		return {} as UTSJSONObject
	}
	const text = JSON.stringify(value)
	const parsed = text == null || text == '' ? null : JSON.parseObject<UTSJSONObject>(text)
	if (parsed == null) {
		return {} as UTSJSONObject
	}
	return parsed!
}

function objectArrayValue(value: any | null): UTSJSONObject[] {
	if (value == null) {
		return [] as UTSJSONObject[]
	}
	const text = JSON.stringify(value)
	const parsed = text == null || text == '' ? null : JSON.parseArray<UTSJSONObject>(text)
	if (parsed == null) {
		return [] as UTSJSONObject[]
	}
	return parsed!
}

function buildListQuery(data: KsefInvoiceListQuery): UTSJSONObject {
	const query = {
		page: data.page,
		page_size: data.page_size,
	} as UTSJSONObject
	if (data.search != null && data.search != '') {
		query['search'] = data.search
	}
	if (data.sync_status != null && data.sync_status != '') {
		query['sync_status'] = data.sync_status
	}
	if (data.is_paid != null && data.is_paid != '') {
		query['is_paid'] = data.is_paid
	}
	return query
}

function buildInvoiceItem(rawObject: UTSJSONObject): KsefInvoiceItem {
	return {
		id: intValue(rawObject['id']),
		company: intValue(rawObject['company']),
		subject_type: stringValue(rawObject['subject_type']),
		ksef_number: stringValue(rawObject['ksef_number']),
		invoice_number: stringValue(rawObject['invoice_number']),
		invoice_type: stringValue(rawObject['invoice_type']),
		seller_name: stringValue(rawObject['seller_name']),
		seller_nip: stringValue(rawObject['seller_nip']),
		buyer_name: stringValue(rawObject['buyer_name']),
		buyer_nip: stringValue(rawObject['buyer_nip']),
		supplier: intValue(rawObject['supplier']),
		supplier_name: stringValue(rawObject['supplier_name']),
		issue_date: stringValue(rawObject['issue_date']),
		sale_date: stringValue(rawObject['sale_date']),
		currency: stringValue(rawObject['currency']),
		net_amount: stringValue(rawObject['net_amount']),
		tax_amount: stringValue(rawObject['tax_amount']),
		gross_amount: stringValue(rawObject['gross_amount']),
		amount_due: stringValue(rawObject['amount_due']),
		payment_due_date: stringValue(rawObject['payment_due_date']),
		payment_method: stringValue(rawObject['payment_method']),
		bank_account_number: stringValue(rawObject['bank_account_number']),
		bank_name: stringValue(rawObject['bank_name']),
		is_paid: boolValue(rawObject['is_paid']),
		paid_amount: stringValue(rawObject['paid_amount']),
		paid_at: stringValue(rawObject['paid_at']),
		remark: stringValue(rawObject['remark']),
		sync_status: stringValue(rawObject['sync_status']),
		raw_xml_downloaded_at: stringValue(rawObject['raw_xml_downloaded_at']),
		files_count: intValue(rawObject['files_count']),
		pdf_download_url: stringValue(rawObject['pdf_download_url']),
		created_at: stringValue(rawObject['created_at']),
		updated_at: stringValue(rawObject['updated_at']),
	} as KsefInvoiceItem
}

function buildInvoiceDetail(raw: any): KsefInvoiceDetail {
	const rawText = JSON.stringify(raw)
	const rawObject = rawText == null || rawText == '' ? null : JSON.parseObject<UTSJSONObject>(rawText)
	if (rawObject == null) {
		throw new Error('KSeF 发票详情解析失败')
	}
	const item = buildInvoiceItem(rawObject!)
	return {
		id: item.id,
		company: item.company,
		subject_type: item.subject_type,
		ksef_number: item.ksef_number,
		invoice_number: item.invoice_number,
		invoice_type: item.invoice_type,
		seller_name: item.seller_name,
		seller_nip: item.seller_nip,
		buyer_name: item.buyer_name,
		buyer_nip: item.buyer_nip,
		supplier: item.supplier,
		supplier_name: item.supplier_name,
		issue_date: item.issue_date,
		sale_date: item.sale_date,
		currency: item.currency,
		net_amount: item.net_amount,
		tax_amount: item.tax_amount,
		gross_amount: item.gross_amount,
		amount_due: item.amount_due,
		payment_due_date: item.payment_due_date,
		payment_method: item.payment_method,
		bank_account_number: item.bank_account_number,
		bank_name: item.bank_name,
		is_paid: item.is_paid,
		paid_amount: item.paid_amount,
		paid_at: item.paid_at,
		remark: item.remark,
		sync_status: item.sync_status,
		raw_xml_downloaded_at: item.raw_xml_downloaded_at,
		files_count: item.files_count,
		pdf_download_url: item.pdf_download_url,
		created_at: item.created_at,
		updated_at: item.updated_at,
		metadata: objectValue(rawObject!['metadata']),
		raw_xml: stringValue(rawObject!['raw_xml']),
		last_error: stringValue(rawObject!['last_error']),
		payment_note: stringValue(rawObject!['payment_note']),
		xml_summary: objectValue(rawObject!['xml_summary']),
		media_files: objectArrayValue(rawObject!['media_files']),
	} as KsefInvoiceDetail
}

function buildInvoiceItems(value: any | null): KsefInvoiceItem[] {
	if (value == null) {
		return []
	}
	const text = JSON.stringify(value)
	const rawArray = text == null || text == '' ? null : JSON.parseArray<UTSJSONObject>(text)
	if (rawArray == null) {
		return []
	}
	const result: KsefInvoiceItem[] = []
	for (let index = 0; index < rawArray!.length; index += 1) {
		result.push(buildInvoiceItem(rawArray![index]))
	}
	return result
}

function buildListResponse(raw: any, query: KsefInvoiceListQuery): KsefInvoiceListResponse {
	const rawText = JSON.stringify(raw)
	const rawObject = rawText == null || rawText == '' ? null : JSON.parseObject<UTSJSONObject>(rawText)
	if (rawObject == null) {
		throw new Error('KSeF 发票列表解析失败')
	}

	let paginationObject: UTSJSONObject | null = null
	const rawPagination = rawObject!['pagination']
	if (rawPagination != null) {
		const paginationText = JSON.stringify(rawPagination)
		paginationObject = paginationText == null || paginationText == '' ? null : JSON.parseObject<UTSJSONObject>(paginationText)
	}

	const results = buildInvoiceItems(rawObject!['results'])
	let totalCount = intValue(rawObject!['count'])
	if (totalCount <= 0) totalCount = intValue(rawObject!['total_count'])
	if (totalCount <= 0 && paginationObject != null) totalCount = intValue(paginationObject!['total'])
	if (totalCount <= 0 && paginationObject != null) totalCount = intValue(paginationObject!['count'])
	if (totalCount <= 0) totalCount = results.length

	let currentPage = intValue(rawObject!['current_page'])
	if (currentPage <= 0) currentPage = intValue(rawObject!['page'])
	if (currentPage <= 0 && paginationObject != null) currentPage = intValue(paginationObject!['page'])
	if (currentPage <= 0) currentPage = query.page

	let pageSize = intValue(rawObject!['page_size'])
	if (pageSize <= 0 && paginationObject != null) pageSize = intValue(paginationObject!['page_size'])
	if (pageSize <= 0) pageSize = query.page_size

	let totalPages = intValue(rawObject!['total_pages'])
	if (totalPages <= 0 && paginationObject != null) totalPages = intValue(paginationObject!['total_pages'])
	if (totalPages <= 0 && pageSize > 0) totalPages = Math.ceil(totalCount / pageSize)
	if (totalPages <= 0) totalPages = 1

	let summary = {} as UTSJSONObject
	const rawSummary = rawObject!['summary']
	if (rawSummary != null) {
		const summaryText = JSON.stringify(rawSummary)
		const parsedSummary = summaryText == null || summaryText == '' ? null : JSON.parseObject<UTSJSONObject>(summaryText)
		if (parsedSummary != null) {
			summary = parsedSummary!
		}
	}

	return {
		results: results,
		count: totalCount,
		total_count: totalCount,
		total_pages: totalPages,
		current_page: currentPage,
		page_size: pageSize,
		summary: summary,
	} as KsefInvoiceListResponse
}

function buildAutoSyncStatus(raw: any): KsefAutoSyncStatus {
	const rawText = JSON.stringify(raw)
	const rawObject = rawText == null || rawText == '' ? null : JSON.parseObject<UTSJSONObject>(rawText)
	if (rawObject == null) {
		throw new Error('KSeF 自动同步状态解析失败')
	}
	return {
		enabled: boolValue(rawObject!['enabled']),
		metadata_interval_seconds: floatValue(rawObject!['metadata_interval_seconds']),
		xml_interval_seconds: floatValue(rawObject!['xml_interval_seconds']),
		xml_batch_size: intValue(rawObject!['xml_batch_size']),
		xml_delay_seconds: floatValue(rawObject!['xml_delay_seconds']),
		pending_xml_count: intValue(rawObject!['pending_xml_count']),
		last_success_at: stringValue(rawObject!['last_success_at']),
		last_success_requested_to: stringValue(rawObject!['last_success_requested_to']),
		last_failed_at: stringValue(rawObject!['last_failed_at']),
		last_failed_message: stringValue(rawObject!['last_failed_message']),
	} as KsefAutoSyncStatus
}

export async function getKsefInvoiceList(data: KsefInvoiceListQuery): Promise<KsefInvoiceListResponse> {
	const raw = await request('/api/procurement/ksef-invoices/', 'GET', buildListQuery(data), true)
	return buildListResponse(raw, data)
}

export async function getKsefInvoiceDetail(id: number | string): Promise<KsefInvoiceDetail> {
	const raw = await request('/api/procurement/ksef-invoices/' + stringValue(id) + '/', 'GET', {} as UTSJSONObject, true)
	return buildInvoiceDetail(raw)
}

export async function updateKsefInvoicePayment(id: number | string, data: KsefPaymentUpdateData): Promise<KsefInvoiceDetail> {
	const payload = {
		is_paid: data.is_paid,
		paid_amount: data.paid_amount,
		paid_at: data.paid_at,
		payment_note: data.payment_note,
		remark: data.remark,
	} as UTSJSONObject
	const raw = await request('/api/procurement/ksef-invoices/' + stringValue(id) + '/update_payment/', 'PATCH', payload, true)
	return buildInvoiceDetail(raw)
}

export async function linkKsefInvoiceSupplier(id: number | string, supplierId: string | null): Promise<KsefInvoiceDetail> {
	const payload = {} as UTSJSONObject
	if (supplierId == null || supplierId == '') {
		payload['supplier_id'] = null
	} else {
		payload['supplier_id'] = supplierId
	}
	const raw = await request('/api/procurement/ksef-invoices/' + stringValue(id) + '/link-supplier/', 'POST', payload, true)
	return buildInvoiceDetail(raw)
}

export async function getKsefAutoSyncStatus(): Promise<KsefAutoSyncStatus> {
	const raw = await request('/api/procurement/ksef-invoices/auto-sync-status/', 'GET', {} as UTSJSONObject, true)
	return buildAutoSyncStatus(raw)
}

export function enqueueKsefAutoSync(): Promise<any> {
	return request('/api/procurement/ksef-invoices/enqueue-auto-sync/', 'POST', {} as UTSJSONObject, true)
}

export function downloadKsefInvoiceXml(id: number | string): Promise<any> {
	return request('/api/procurement/ksef-invoices/' + stringValue(id) + '/download_xml/', 'POST', {} as UTSJSONObject, true)
}
