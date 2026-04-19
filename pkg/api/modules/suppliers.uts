import { baseUrl, request } from '../index.uts'

export type SupplierListQuery = {
	search: string | null
	page: number
	page_size: number
	is_active: string | null
	has_arrears: string | null
}

export type SupplierMediaFile = {
	id: string
	company: number
	original_filename: string
	file_type: string
	file_type_display: string
	mime_type: string
	file_size: number
	file_size_display: string
	file_url: string
	thumbnail_url: string
	signed_url: string
	signed_thumbnail_url: string
	object_id: string
	is_deleted: boolean
	created_at: string
	updated_at: string
}

export type SupplierItem = {
	id: number
	code: string
	name: string
	address: string
	phone: string
	contact: string
	description: string | null
	total_amount: string
	arrears_amount: string
	paid_amount: number
	is_active: boolean
	files_count: number
	company_infos: UTSJSONObject[]
	is_deleted: boolean
	created_at: string
	updated_at: string
	media_files: SupplierMediaFile[]
}

export type SupplierListResponse = {
	results: SupplierItem[]
	count: number
	total_count: number
	total_pages: number
	current_page: number
	page_size: number
}

export type SupplierFilterOption = {
	value: string
	label: string
}

export type SupplierFilterDefinition = {
	key: string
	param: string
	label: string
	control: string
	aliases: string[]
	multiple: boolean
	options: SupplierFilterOption[]
}

export type SupplierFilterOptionsResponse = {
	resource: string
	count: number
	filters: SupplierFilterDefinition[]
}

export type SupplierFormOptionsResponse = {
	resource: string
	count: number
	options: UTSJSONObject
}

export type SupplierGlobalStatisticsResponse = {
	data: UTSJSONObject
}

export type SupplierMutationData = {
	code: string | null
	name: string
	address: string | null
	phone: string | null
	contact: string | null
	description: string | null
	is_active: boolean | null
	company_infos: UTSJSONObject[] | null
}

function buildListQuery(data: SupplierListQuery): UTSJSONObject {
	const query = {
		page: data.page,
		page_size: data.page_size
	} as UTSJSONObject

	if (data.search != null && data.search != '') {
		query['search'] = data.search
	}

	if (data.is_active != null && data.is_active != '') {
		query['is_active'] = data.is_active
	}

	if (data.has_arrears != null && data.has_arrears != '') {
		query['has_arrears'] = data.has_arrears
	}

	return query
}

function normalizeServerUrl(url: string): string {
	if (url == '') {
		return ''
	}

	if (url.startsWith('http://localhost:8000')) {
		return baseUrl + url.substring('http://localhost:8000'.length)
	}

	if (url.startsWith('https://localhost:8000')) {
		return baseUrl + url.substring('https://localhost:8000'.length)
	}

	if (url.startsWith('http://127.0.0.1:8000')) {
		return baseUrl + url.substring('http://127.0.0.1:8000'.length)
	}

	if (url.startsWith('https://127.0.0.1:8000')) {
		return baseUrl + url.substring('https://127.0.0.1:8000'.length)
	}

	return url
}

function normalizeSupplierList(data: SupplierListResponse): SupplierListResponse {
	for (let supplierIndex = 0; supplierIndex < data.results.length; supplierIndex += 1) {
		const supplier = data.results[supplierIndex]
		normalizeSupplierMediaFiles(supplier.media_files)
	}

	return data
}

function normalizeSupplierMediaFiles(files: SupplierMediaFile[]) {
	for (let mediaIndex = 0; mediaIndex < files.length; mediaIndex += 1) {
		const mediaFile = files[mediaIndex]
		mediaFile.file_url = normalizeServerUrl(mediaFile.file_url)
		mediaFile.thumbnail_url = normalizeServerUrl(mediaFile.thumbnail_url)
		mediaFile.signed_url = normalizeServerUrl(mediaFile.signed_url)
		mediaFile.signed_thumbnail_url = normalizeServerUrl(mediaFile.signed_thumbnail_url)
	}
}

function normalizeSupplierItem(item: SupplierItem): SupplierItem {
	normalizeSupplierMediaFiles(item.media_files)
	return item
}

function intValue(value: any | null): number {
	if (value == null) {
		return 0
	}

	const text = '' + value
	if (text == '') {
		return 0
	}

	const parsed = parseInt(text)
	if (isNaN(parsed)) {
		return 0
	}
	return parsed
}

function stringValue(value: any | null): string {
	if (value == null) {
		return ''
	}
	return '' + value
}

function buildSupplierListResponse(raw: any, query: SupplierListQuery): SupplierListResponse {
	const rawText = JSON.stringify(raw)
	const rawObject = rawText == null || rawText == '' ? null : JSON.parseObject<UTSJSONObject>(rawText)
	if (rawObject == null) {
		throw new Error('供应商列表响应解析失败')
	}

	let paginationObject: UTSJSONObject | null = null
	const rawPagination = rawObject!['pagination']
	if (rawPagination != null) {
		const paginationText = JSON.stringify(rawPagination)
		if (paginationText != null && paginationText != '') {
			paginationObject = JSON.parseObject<UTSJSONObject>(paginationText)
		}
	}

	let results: SupplierItem[] = []
	const rawResults = rawObject!['results']
	if (rawResults != null) {
		const resultText = JSON.stringify(rawResults)
		const parsedResults = resultText == null || resultText == '' ? null : JSON.parseArray<SupplierItem>(resultText)
		if (parsedResults != null) {
			results = parsedResults!
		}
	}

	let totalCount = intValue(rawObject!['count'])
	if (totalCount <= 0) {
		totalCount = intValue(rawObject!['total'])
	}
	if (totalCount <= 0) {
		totalCount = intValue(rawObject!['total_count'])
	}
	if (totalCount <= 0 && paginationObject != null) {
		totalCount = intValue(paginationObject['total'])
	}
	if (totalCount <= 0 && paginationObject != null) {
		totalCount = intValue(paginationObject['count'])
	}
	if (totalCount <= 0) {
		totalCount = results.length
	}

	let currentPage = intValue(rawObject!['page'])
	if (currentPage <= 0) {
		currentPage = intValue(rawObject!['current_page'])
	}
	if (currentPage <= 0 && paginationObject != null) {
		currentPage = intValue(paginationObject['page'])
	}
	if (currentPage <= 0) {
		currentPage = query.page
	}

	let pageSize = intValue(rawObject!['page_size'])
	if (pageSize <= 0) {
		pageSize = intValue(rawObject!['per_page'])
	}
	if (pageSize <= 0 && paginationObject != null) {
		pageSize = intValue(paginationObject['page_size'])
	}
	if (pageSize <= 0 && paginationObject != null) {
		pageSize = intValue(paginationObject['per_page'])
	}
	if (pageSize <= 0) {
		pageSize = query.page_size
	}

	let totalPages = intValue(rawObject!['total_pages'])
	if (totalPages <= 0) {
		totalPages = intValue(rawObject!['num_pages'])
	}
	if (totalPages <= 0 && paginationObject != null) {
		totalPages = intValue(paginationObject['total_pages'])
	}
	if (totalPages <= 0 && paginationObject != null) {
		totalPages = intValue(paginationObject['num_pages'])
	}
	if (totalPages <= 0 && pageSize > 0) {
		totalPages = Math.ceil(totalCount / pageSize)
	}
	if (totalPages <= 0) {
		totalPages = 1
	}

	return {
		results: results,
		count: totalCount,
		total_count: totalCount,
		total_pages: totalPages,
		current_page: currentPage,
		page_size: pageSize,
	} as SupplierListResponse
}

function stringArrayValue(value: any | null): string[] {
	if (value == null) {
		return []
	}

	const text = JSON.stringify(value)
	const parsed = text == null || text == '' ? null : JSON.parseArray<any>(text)
	if (parsed == null) {
		return []
	}

	const result: string[] = []
	for (let index = 0; index < parsed!.length; index += 1) {
		result.push(stringValue(parsed![index]))
	}
	return result
}

function buildSupplierFilterOptionsResponse(raw: any): SupplierFilterOptionsResponse {
	const rawText = JSON.stringify(raw)
	const rawObject = rawText == null || rawText == '' ? null : JSON.parseObject<UTSJSONObject>(rawText)
	if (rawObject == null) {
		throw new Error('供应商过滤选项解析失败')
	}

	let filters: SupplierFilterDefinition[] = []
	const rawFilters = rawObject!['filters']
	if (rawFilters != null) {
		const filtersText = JSON.stringify(rawFilters)
		const filterObjects = filtersText == null || filtersText == '' ? null : JSON.parseArray<UTSJSONObject>(filtersText)
		if (filterObjects != null) {
			const nextFilters: SupplierFilterDefinition[] = []
			for (let filterIndex = 0; filterIndex < filterObjects!.length; filterIndex += 1) {
				const filterObject = filterObjects![filterIndex]
				let options: SupplierFilterOption[] = []
				const rawOptions = filterObject['options']
				if (rawOptions != null) {
					const optionsText = JSON.stringify(rawOptions)
					const optionObjects = optionsText == null || optionsText == '' ? null : JSON.parseArray<UTSJSONObject>(optionsText)
					if (optionObjects != null) {
						const nextOptions: SupplierFilterOption[] = []
						for (let optionIndex = 0; optionIndex < optionObjects!.length; optionIndex += 1) {
							const optionObject = optionObjects![optionIndex]
							nextOptions.push({
								value: stringValue(optionObject['value']),
								label: stringValue(optionObject['label']),
							} as SupplierFilterOption)
						}
						options = nextOptions
					}
				}

				nextFilters.push({
					key: stringValue(filterObject['key']),
					param: stringValue(filterObject['param']),
					label: stringValue(filterObject['label']),
					control: stringValue(filterObject['control']),
					aliases: stringArrayValue(filterObject['aliases']),
					multiple: stringValue(filterObject['multiple']) == 'true',
					options: options,
				} as SupplierFilterDefinition)
			}
			filters = nextFilters
		}
	}

	return {
		resource: stringValue(rawObject!['resource']),
		count: intValue(rawObject!['count']),
		filters: filters,
	} as SupplierFilterOptionsResponse
}

function buildSupplierMediaFileFromObject(rawObject: UTSJSONObject): SupplierMediaFile {
	return {
		id: stringValue(rawObject['id']),
		company: intValue(rawObject['company']),
		original_filename: stringValue(rawObject['original_filename']),
		file_type: stringValue(rawObject['file_type']),
		file_type_display: stringValue(rawObject['file_type_display']),
		mime_type: stringValue(rawObject['mime_type']),
		file_size: intValue(rawObject['file_size']),
		file_size_display: stringValue(rawObject['file_size_display']),
		file_url: normalizeServerUrl(stringValue(rawObject['file_url'])),
		thumbnail_url: normalizeServerUrl(stringValue(rawObject['thumbnail_url'])),
		signed_url: normalizeServerUrl(stringValue(rawObject['signed_url'])),
		signed_thumbnail_url: normalizeServerUrl(stringValue(rawObject['signed_thumbnail_url'])),
		object_id: stringValue(rawObject['object_id']),
		is_deleted: stringValue(rawObject['is_deleted']) == 'true',
		created_at: stringValue(rawObject['created_at']),
		updated_at: stringValue(rawObject['updated_at']),
	} as SupplierMediaFile
}

function buildSupplierMediaFilesFromValue(value: any | null): SupplierMediaFile[] {
	if (value == null) {
		return []
	}
	const text = JSON.stringify(value)
	const rawArray = text == null || text == '' ? null : JSON.parseArray<UTSJSONObject>(text)
	if (rawArray == null) {
		return []
	}
	const result: SupplierMediaFile[] = []
	for (let index = 0; index < rawArray!.length; index += 1) {
		result.push(buildSupplierMediaFileFromObject(rawArray![index]))
	}
	return result
}

function buildSupplierItemResponse(raw: any): SupplierItem {
	const rawText = JSON.stringify(raw)
	const rawObject = rawText == null || rawText == '' ? null : JSON.parseObject<UTSJSONObject>(rawText)
	if (rawObject == null) {
		throw new Error('供应商详情响应解析失败')
	}
	return {
		id: intValue(rawObject!['id']),
		code: stringValue(rawObject!['code']),
		name: stringValue(rawObject!['name']),
		address: stringValue(rawObject!['address']),
		phone: stringValue(rawObject!['phone']),
		contact: stringValue(rawObject!['contact']),
		description: rawObject!['description'] == null ? null : stringValue(rawObject!['description']),
		total_amount: stringValue(rawObject!['total_amount']),
		arrears_amount: stringValue(rawObject!['arrears_amount']),
		paid_amount: intValue(rawObject!['paid_amount']),
		is_active: stringValue(rawObject!['is_active']) == 'true',
		files_count: intValue(rawObject!['files_count']),
		company_infos: (() : UTSJSONObject[] => {
			const companyInfosValue = rawObject!['company_infos']
			if (companyInfosValue == null) {
				return [] as UTSJSONObject[]
			}
			const companyInfosText = JSON.stringify(companyInfosValue)
			const companyInfosArray = companyInfosText == null || companyInfosText == '' ? null : JSON.parseArray<UTSJSONObject>(companyInfosText)
			if (companyInfosArray == null) {
				return [] as UTSJSONObject[]
			}
			return companyInfosArray!
		})(),
		is_deleted: stringValue(rawObject!['is_deleted']) == 'true',
		created_at: stringValue(rawObject!['created_at']),
		updated_at: stringValue(rawObject!['updated_at']),
		media_files: buildSupplierMediaFilesFromValue(rawObject!['media_files']),
	} as SupplierItem
}

function buildSupplierImagesResponse(raw: any): SupplierMediaFile[] {
	return buildSupplierMediaFilesFromValue(raw)
}

function buildSupplierFormOptionsResponse(raw: any): SupplierFormOptionsResponse {
	const rawText = JSON.stringify(raw)
	const rawObject = rawText == null || rawText == '' ? null : JSON.parseObject<UTSJSONObject>(rawText)
	if (rawObject == null) {
		throw new Error('供应商表单选项解析失败')
	}
	return {
		resource: stringValue(rawObject!['resource']),
		count: intValue(rawObject!['count']),
		options: rawObject!,
	} as SupplierFormOptionsResponse
}

function buildSupplierGlobalStatisticsResponse(raw: any): SupplierGlobalStatisticsResponse {
	const rawText = JSON.stringify(raw)
	const rawObject = rawText == null || rawText == '' ? null : JSON.parseObject<UTSJSONObject>(rawText)
	if (rawObject == null) {
		throw new Error('供应商全局统计解析失败')
	}
	return {
		data: rawObject!,
	} as SupplierGlobalStatisticsResponse
}

function buildSupplierMutationBody(data: SupplierMutationData): UTSJSONObject {
	const body = {
		name: data.name,
	} as UTSJSONObject
	if (data.code != null) {
		body['code'] = data.code
	}
	if (data.address != null) {
		body['address'] = data.address
	}
	if (data.phone != null) {
		body['phone'] = data.phone
	}
	if (data.contact != null) {
		body['contact'] = data.contact
	}
	if (data.description != null) {
		body['description'] = data.description
	}
	if (data.is_active != null) {
		body['is_active'] = data.is_active
	}
	if (data.company_infos != null) {
		body['company_infos'] = data.company_infos
	}
	return body
}

function supplierDetailPath(id: number | string): string {
	return '/api/procurement/suppliers/' + stringValue(id) + '/'
}

export async function getSupplierList(data: SupplierListQuery): Promise<SupplierListResponse> {
	const raw = await request(
		'/api/procurement/suppliers/',
		'GET',
		buildListQuery(data),
		true
	)

	return normalizeSupplierList(buildSupplierListResponse(raw, data))
}

export async function getSupplierFilterOptions(): Promise<SupplierFilterOptionsResponse> {
	const raw = await request(
		'/api/procurement/suppliers/filter-options/',
		'GET',
		{} as UTSJSONObject,
		true
	)

	return buildSupplierFilterOptionsResponse(raw)
}

export async function getSupplierDetail(id: number | string): Promise<SupplierItem> {
	const raw = await request(
		supplierDetailPath(id),
		'GET',
		{} as UTSJSONObject,
		true
	)
	return buildSupplierItemResponse(raw)
}

export async function createSupplier(data: SupplierMutationData): Promise<SupplierItem> {
	const raw = await request(
		'/api/procurement/suppliers/',
		'POST',
		buildSupplierMutationBody(data),
		true
	)
	return buildSupplierItemResponse(raw)
}

export async function updateSupplier(id: number | string, data: SupplierMutationData): Promise<SupplierItem> {
	const raw = await request(
		supplierDetailPath(id),
		'PUT',
		buildSupplierMutationBody(data),
		true
	)
	return buildSupplierItemResponse(raw)
}

export async function patchSupplier(id: number | string, data: SupplierMutationData): Promise<SupplierItem> {
	const raw = await request(
		supplierDetailPath(id),
		'PATCH',
		buildSupplierMutationBody(data),
		true
	)
	return buildSupplierItemResponse(raw)
}

export function deleteSupplier(id: number | string): Promise<any> {
	return request(
		supplierDetailPath(id),
		'DELETE',
		{} as UTSJSONObject,
		true
	)
}

export async function getSupplierOptions(): Promise<SupplierFormOptionsResponse> {
	const raw = await request(
		'/api/procurement/suppliers/options/',
		'GET',
		{} as UTSJSONObject,
		true
	)
	return buildSupplierFormOptionsResponse(raw)
}

export async function restoreSupplier(id: number | string): Promise<SupplierItem> {
	const raw = await request(
		supplierDetailPath(id) + 'restore/',
		'POST',
		{} as UTSJSONObject,
		true
	)
	return buildSupplierItemResponse(raw)
}

export async function getSupplierAllImages(id: number | string): Promise<SupplierMediaFile[]> {
	const raw = await request(
		supplierDetailPath(id) + 'all_images/',
		'GET',
		{} as UTSJSONObject,
		true
	)
	return buildSupplierImagesResponse(raw)
}

export async function getSupplierGlobalStatistics(): Promise<SupplierGlobalStatisticsResponse> {
	const raw = await request(
		'/api/procurement/suppliers/global_statistics/',
		'GET',
		{} as UTSJSONObject,
		true
	)
	return buildSupplierGlobalStatisticsResponse(raw)
}

export async function activateSupplier(id: number | string): Promise<SupplierItem> {
	const raw = await request(
		supplierDetailPath(id) + 'activate/',
		'POST',
		{} as UTSJSONObject,
		true
	)
	return buildSupplierItemResponse(raw)
}

export async function deactivateSupplier(id: number | string): Promise<SupplierItem> {
	const raw = await request(
		supplierDetailPath(id) + 'deactivate/',
		'POST',
		{} as UTSJSONObject,
		true
	)
	return buildSupplierItemResponse(raw)
}
