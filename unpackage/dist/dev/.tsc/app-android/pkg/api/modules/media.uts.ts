import { baseUrl, request, timeOut } from '../index.uts'
import { authState } from '@/store/auth'

export type MediaFileQuery = {
	company_id: string | null
	file_type: string | null
	content_type_model: string | null
	object_id: string | null
	page: number
	page_size: number
}

export type MediaFileItem = {
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

export type MediaFileListResponse = {
	results: MediaFileItem[]
	count: number
	total_count: number
	total_pages: number
	current_page: number
	page_size: number
}

export type MediaShareResponse = {
	url: string
}

export type MediaStatisticsResponse = {
	data: UTSJSONObject
}

export type MediaBatchUploadItem = {
	filePath: string
	name: string | null
	formData: UTSJSONObject | null
}

export type MediaBatchUploadResult = {
	successItems: UTSJSONObject[]
	failItems: UTSJSONObject[]
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

function boolValue(value: any | null): boolean {
	if (value == null) {
		return false
	}
	return stringValue(value) == 'true'
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

function buildMediaListQuery(data: MediaFileQuery): UTSJSONObject {
	const query = {
		page: data.page,
		page_size: data.page_size,
	} as UTSJSONObject
	if (data.company_id != null && data.company_id != '') {
		query['company_id'] = data.company_id
	}
	if (data.file_type != null && data.file_type != '') {
		query['file_type'] = data.file_type
	}
	if (data.content_type_model != null && data.content_type_model != '') {
		query['content_type_model'] = data.content_type_model
	}
	if (data.object_id != null && data.object_id != '') {
		query['object_id'] = data.object_id
	}
	return query
}

function buildMediaFileFromObject(rawObject: UTSJSONObject): MediaFileItem {
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
		is_deleted: boolValue(rawObject['is_deleted']),
		created_at: stringValue(rawObject['created_at']),
		updated_at: stringValue(rawObject['updated_at']),
	} as MediaFileItem
}

function buildMediaFilesFromValue(value: any | null): MediaFileItem[] {
	if (value == null) {
		return []
	}
	const text = JSON.stringify(value)
	const rawArray = text == null || text == '' ? null : JSON.parseArray<UTSJSONObject>(text)
	if (rawArray == null) {
		return []
	}
	const result: MediaFileItem[] = []
	for (let index = 0; index < rawArray!.length; index += 1) {
		result.push(buildMediaFileFromObject(rawArray![index]))
	}
	return result
}

function buildMediaFileResponse(raw: any): MediaFileItem {
	const rawText = JSON.stringify(raw)
	const rawObject = rawText == null || rawText == '' ? null : JSON.parseObject<UTSJSONObject>(rawText)
	if (rawObject == null) {
		throw new Error('媒体文件详情解析失败')
	}
	return buildMediaFileFromObject(rawObject!)
}

function buildMediaFileListResponse(raw: any, query: MediaFileQuery): MediaFileListResponse {
	const rawText = JSON.stringify(raw)
	const rawObject = rawText == null || rawText == '' ? null : JSON.parseObject<UTSJSONObject>(rawText)
	if (rawObject == null) {
		throw new Error('媒体文件列表解析失败')
	}

	let paginationObject: UTSJSONObject | null = null
	const rawPagination = rawObject!['pagination']
	if (rawPagination != null) {
		const paginationText = JSON.stringify(rawPagination)
		if (paginationText != null && paginationText != '') {
			paginationObject = JSON.parseObject<UTSJSONObject>(paginationText)
		}
	}

	const results = buildMediaFilesFromValue(rawObject!['results'])

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
	if (pageSize <= 0 && paginationObject != null) {
		pageSize = intValue(paginationObject['page_size'])
	}
	if (pageSize <= 0) {
		pageSize = query.page_size
	}

	let totalPages = intValue(rawObject!['total_pages'])
	if (totalPages <= 0 && paginationObject != null) {
		totalPages = intValue(paginationObject['total_pages'])
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
	} as MediaFileListResponse
}

function buildMediaShareResponse(raw: any): MediaShareResponse {
	const rawText = JSON.stringify(raw)
	const rawObject = rawText == null || rawText == '' ? null : JSON.parseObject<UTSJSONObject>(rawText)
	if (rawObject == null) {
		throw new Error('媒体分享链接解析失败')
	}
	return {
		url: normalizeServerUrl(stringValue(rawObject!['url'])),
	} as MediaShareResponse
}

function buildMediaStatisticsResponse(raw: any): MediaStatisticsResponse {
	const rawText = JSON.stringify(raw)
	const rawObject = rawText == null || rawText == '' ? null : JSON.parseObject<UTSJSONObject>(rawText)
	if (rawObject == null) {
		throw new Error('媒体统计解析失败')
	}
	return {
		data: rawObject!,
	} as MediaStatisticsResponse
}

function mediaFilePath(id: number | string): string {
	return '/api/media/files/' + stringValue(id) + '/'
}

function buildUploadHeaders(): UTSJSONObject {
	const headers = {} as UTSJSONObject
	if (authState.token != '') {
		headers['Authorization'] = authState.token
	}
	return headers
}

function parseUploadResponseText(text: string): UTSJSONObject {
	const rootObject = text == '' ? null : JSON.parseObject<UTSJSONObject>(text)
	if (rootObject == null) {
		throw new Error('上传响应解析失败')
	}
	const successValue = rootObject['success']
	if (successValue != null) {
		const successText = stringValue(successValue)
		if (successText != 'true') {
			let message = stringValue(rootObject['message'])
			if (message == '') {
				message = stringValue(rootObject['detail'])
			}
			throw new Error(message == '' ? '上传失败' : message)
		}
		const dataValue = rootObject['data']
		if (dataValue == null) {
			return {} as UTSJSONObject
		}
		const dataText = JSON.stringify(dataValue)
		const dataObject = dataText == null || dataText == '' ? null : JSON.parseObject<UTSJSONObject>(dataText)
		if (dataObject == null) {
			return {} as UTSJSONObject
		}
		return dataObject!
	}

	// 兼容后端直接返回 serializer.data 的情况
	if (rootObject['id'] != null || rootObject['original_filename'] != null || rootObject['file_url'] != null || rootObject['signed_url'] != null) {
		return rootObject
	}

	const detailMessage = stringValue(rootObject['detail'])
	if (detailMessage != '') {
		throw new Error(detailMessage)
	}
	const message = stringValue(rootObject['message'])
	if (message != '') {
		throw new Error(message)
	}
	throw new Error('上传失败')
}

function parseResponseErrorMessage(text: string): string {
	if (text == '') {
		return ''
	}
	const rootObject = JSON.parseObject<UTSJSONObject>(text)
	if (rootObject == null) {
		return ''
	}
	const detailMessage = stringValue(rootObject['detail'])
	if (detailMessage != '') {
		return detailMessage
	}
	const message = stringValue(rootObject['message'])
	if (message != '') {
		return message
	}
	return ''
}

function cloneObject(source: UTSJSONObject): UTSJSONObject {
	const target = {} as UTSJSONObject
	for (const key in source) {
		target[key] = source[key]
	}
	return target
}

function buildBatchUploadFormData(items: MediaBatchUploadItem[]): UTSJSONObject {
	const result = {} as UTSJSONObject
	let initialized = false
	for (let index = 0; index < items.length; index += 1) {
		const itemFormData = items[index].formData
		if (itemFormData == null) {
			continue
		}
		if (!initialized) {
			const cloned = cloneObject(itemFormData!)
			for (const key in cloned) {
				result[key] = cloned[key]
			}
			initialized = true
			continue
		}
		const currentContentTypeModel = stringValue(itemFormData!['content_type_model']).trim()
		const currentObjectId = stringValue(itemFormData!['object_id']).trim()
		const currentCompanyId = stringValue(itemFormData!['company_id']).trim()
		const baseContentTypeModel = stringValue(result['content_type_model']).trim()
		const baseObjectId = stringValue(result['object_id']).trim()
		const baseCompanyId = stringValue(result['company_id']).trim()
		if (currentContentTypeModel != '' && baseContentTypeModel != '' && currentContentTypeModel != baseContentTypeModel) {
			throw new Error('批量上传参数冲突: content_type_model 不一致')
		}
		if (currentObjectId != '' && baseObjectId != '' && currentObjectId != baseObjectId) {
			throw new Error('批量上传参数冲突: object_id 不一致')
		}
		if (currentCompanyId != '' && baseCompanyId != '' && currentCompanyId != baseCompanyId) {
			throw new Error('批量上传参数冲突: company_id 不一致')
		}
		for (const key in itemFormData!) {
			if (result[key] == null || stringValue(result[key]).trim() == '') {
				result[key] = itemFormData![key]
			}
		}
	}
	const contentTypeModel = stringValue(result['content_type_model']).trim()
	const objectId = stringValue(result['object_id']).trim()
	if (contentTypeModel == '' || objectId == '') {
		throw new Error('批量上传缺少必填参数: content_type_model 和 object_id')
	}
	return result
}

function parseBatchUploadResponseText(text: string): UTSJSONObject[] {
	if (text == '') {
		return []
	}
	const rootObject = JSON.parseObject<UTSJSONObject>(text)
	if (rootObject == null) {
		throw new Error('批量上传响应解析失败')
	}
	const successValue = rootObject['success']
	if (successValue != null) {
		const successText = stringValue(successValue)
		if (successText != 'true') {
			let message = stringValue(rootObject['message'])
			if (message == '') {
				message = stringValue(rootObject['detail'])
			}
			throw new Error(message == '' ? '批量上传失败' : message)
		}
		return extractUploadedItems(rootObject['data'])
	}
	return extractUploadedItems(rootObject)
}

function tryParseObject(text: string): UTSJSONObject | null {
	if (text == '') {
		return null
	}
	try {
		return JSON.parseObject<UTSJSONObject>(text)
	} catch (error) {
		return null
	}
}

function tryParseArray(text: string): UTSJSONObject[] | null {
	if (text == '') {
		return null
	}
	try {
		return JSON.parseArray<UTSJSONObject>(text)
	} catch (error) {
		return null
	}
}

function extractUploadedItems(value: any | null): UTSJSONObject[] {
	if (value == null) {
		return []
	}
	const valueText = JSON.stringify(value)
	if (valueText == null || valueText == '') {
		return []
	}
	const uploadedArray = tryParseArray(valueText)
	if (uploadedArray != null) {
		return uploadedArray!
	}
	const valueObject = tryParseObject(valueText)
	if (valueObject == null) {
		return []
	}
	const uploadedValue = valueObject['uploaded']
	if (uploadedValue != null) {
		const uploadedText = JSON.stringify(uploadedValue)
		const parsedUploadedArray = uploadedText == null || uploadedText == '' ? null : tryParseArray(uploadedText)
		if (parsedUploadedArray != null) {
			return parsedUploadedArray!
		}
	}
	if (valueObject['id'] != null || valueObject['original_filename'] != null || valueObject['file_url'] != null || valueObject['signed_url'] != null) {
		return [valueObject]
	}
	const detailMessage = stringValue(valueObject['detail'])
	if (detailMessage != '') {
		throw new Error(detailMessage)
	}
	const message = stringValue(valueObject['message'])
	if (message != '') {
		throw new Error(message)
	}
	return []
}

function normalizeUploadFilePath(filePath: string): string {
	return filePath.trim()
}

function buildUploadFailMessage(err: UploadFileFail): string {
	let message = stringValue(err.errMsg)
	const rawText = JSON.stringify(err)
	if (rawText != null && rawText != '') {
		const rawObject = JSON.parseObject<UTSJSONObject>(rawText)
		if (rawObject != null) {
			const causeValue = rawObject!['cause']
			if (causeValue != null) {
				const causeText = JSON.stringify(causeValue)
				if (causeText != null && causeText != '') {
					const causeObject = JSON.parseObject<UTSJSONObject>(causeText)
					if (causeObject != null) {
						const causeMessage = stringValue(causeObject!['message'])
						if (causeMessage != '') {
							message = message == '' ? causeMessage : (message + ' | ' + causeMessage)
						}
					}
				}
			}
		}
	}
	if (message == '') {
		return '上传失败'
	}
	return message
}

function uploadBatchMediaFilesRequest(items: MediaBatchUploadItem[], formData: UTSJSONObject): Promise<UTSJSONObject[]> {
	return new Promise((resolve, reject) => {
		const headers = buildUploadHeaders()
		const uploadTimeout = timeOut < 120000 ? 120000 : timeOut
		const files: UploadFileOptionFiles[] = []
		for (let index = 0; index < items.length; index += 1) {
			const resolvedFilePath = normalizeUploadFilePath(items[index].filePath)
			files.push({
				name: 'files',
				uri: resolvedFilePath,
			} as UploadFileOptionFiles)
		}
		__f__('log','at pkg/api/modules/media.uts:521','media batch upload start:', baseUrl + '/api/media/files/batch-upload/', files.length)
		try {

			uni.uploadFile({
				url: baseUrl + '/api/media/files/batch-upload/',
				files: files,
				header: headers,
				formData: formData,
				timeout: uploadTimeout,
				success: (res : UploadFileSuccess) => {
					__f__('log','at pkg/api/modules/media.uts:531','media batch upload success:', res.statusCode, items.length)
					if (res.statusCode < 200 || res.statusCode >= 300) {
						const responseMessage = parseResponseErrorMessage(res.data)
						reject(new Error(responseMessage == '' ? ('HTTP状态码错误: ' + res.statusCode) : responseMessage))
						return
					}
					try {
						resolve(parseBatchUploadResponseText(res.data))
					} catch (error) {
						reject(error)
					}
				},
				fail: (err : UploadFileFail) => {
					const failMessage = buildUploadFailMessage(err)
					__f__('log','at pkg/api/modules/media.uts:545','media batch upload fail:', failMessage, err.errCode)
					reject(new Error(failMessage))
				},
			})




		} catch (error) {
			reject(error)
		}
	})
}

function uploadSingleMediaFile(item: MediaBatchUploadItem): Promise<UTSJSONObject> {
	return new Promise((resolve, reject) => {
		const formData = item.formData == null ? ({} as UTSJSONObject) : item.formData!
		const resolvedFilePath = normalizeUploadFilePath(item.filePath)
		const fieldName = item.name == null || item.name == '' ? 'file' : item.name!
		const headers = buildUploadHeaders()
		const uploadTimeout = timeOut < 120000 ? 120000 : timeOut
		__f__('log','at pkg/api/modules/media.uts:566','media upload start:', baseUrl + '/api/media/files/', resolvedFilePath)
		try {
			uni.uploadFile({
				url: baseUrl + '/api/media/files/',
				filePath: resolvedFilePath,
				name: fieldName,
				header: headers,
				formData: formData,
				timeout: uploadTimeout,
				success: (res : UploadFileSuccess) => {
					__f__('log','at pkg/api/modules/media.uts:576','media upload success:', resolvedFilePath, res.statusCode)
					try {
						resolve(parseUploadResponseText(res.data))
					} catch (error) {
						__f__('log','at pkg/api/modules/media.uts:580','media upload parse fail:', resolvedFilePath)
						reject(error)
					}
				},
				fail: (err : UploadFileFail) => {
					const failMessage = buildUploadFailMessage(err)
					__f__('log','at pkg/api/modules/media.uts:586','media upload fail:', resolvedFilePath, failMessage, err.errCode)
					reject(new Error(failMessage))
				},
			})
		} catch (error) {
			__f__('log','at pkg/api/modules/media.uts:591','media upload throw:', resolvedFilePath, stringValue((error as Error).message))
			reject(error)
		}
	})
}

function deleteMediaFileRequest(id: number | string): Promise<boolean> {
	return new Promise((resolve, reject) => {
		const headers = buildUploadHeaders()
		headers['content-type'] = 'application/json'
		const requestUrl = baseUrl + mediaFilePath(id)
		__f__('log','at pkg/api/modules/media.uts:602','请求地址:', requestUrl)
		uni.request({
			url: requestUrl,
			method: 'DELETE',
			header: headers,
			timeout: timeOut,
			success: (res) => {
				if (res.statusCode == 204 || res.statusCode == 200) {
					resolve(true)
					return
				}
				reject(new Error('HTTP状态码错误: ' + res.statusCode))
			},
			fail: (err) => {
				reject(new Error(stringValue(err.errMsg)))
			},
		})
	})
}

export async function getMediaFileList(data: MediaFileQuery): Promise<MediaFileListResponse> {
	const raw = await request(
		'/api/media/files/',
		'GET',
		buildMediaListQuery(data),
		true
	)
	return buildMediaFileListResponse(raw, data)
}

export async function getMediaFileDetail(id: number | string): Promise<MediaFileItem> {
	const raw = await request(
		mediaFilePath(id),
		'GET',
		{} as UTSJSONObject,
		true
	)
	return buildMediaFileResponse(raw)
}

export function deleteMediaFile(id: number | string): Promise<boolean> {
	return deleteMediaFileRequest(id)
}

export function restoreMediaFile(id: number | string): Promise<any> {
	return request(
		mediaFilePath(id) + 'restore/',
		'POST',
		{} as UTSJSONObject,
		true
	)
}

export async function getMediaFileShare(id: number | string): Promise<MediaShareResponse> {
	const raw = await request(
		mediaFilePath(id) + 'share/',
		'GET',
		{} as UTSJSONObject,
		true
	)
	return buildMediaShareResponse(raw)
}

export async function getMediaStatistics(): Promise<MediaStatisticsResponse> {
	const raw = await request(
		'/api/media/files/statistics/',
		'GET',
		{} as UTSJSONObject,
		true
	)
	return buildMediaStatisticsResponse(raw)
}

export function regenerateMediaThumbnail(id: number | string): Promise<any> {
	return request(
		mediaFilePath(id) + 'regenerate-thumbnail/',
		'POST',
		{} as UTSJSONObject,
		true
	)
}

export async function batchUploadMediaFiles(items: MediaBatchUploadItem[]): Promise<MediaBatchUploadResult> {
	const successItems: UTSJSONObject[] = []
	const failItems: UTSJSONObject[] = []
	__f__('log','at pkg/api/modules/media.uts:687','media batch upload count:', items.length)
	if (items.length == 0) {
		return {
			successItems: successItems,
			failItems: failItems,
		} as MediaBatchUploadResult
	}
	try {
		const formData = buildBatchUploadFormData(items)
		const uploadedItems = await uploadBatchMediaFilesRequest(items, formData)
		if (uploadedItems.length == items.length) {
			for (let index = 0; index < items.length; index += 1) {
				successItems.push({
					filePath: items[index].filePath,
					result: uploadedItems[index],
				} as UTSJSONObject)
			}
		} else {
			const message = '批量上传返回数量异常: 请求 ' + items.length + ' 个，返回 ' + uploadedItems.length + ' 个'
			for (let index = 0; index < items.length; index += 1) {
				if (index < uploadedItems.length) {
					successItems.push({
						filePath: items[index].filePath,
						result: uploadedItems[index],
					} as UTSJSONObject)
				} else {
					failItems.push({
						filePath: items[index].filePath,
						message: message,
					} as UTSJSONObject)
				}
			}
		}
	} catch (error) {
		const message = stringValue((error as Error).message)
		for (let index = 0; index < items.length; index += 1) {
			failItems.push({
				filePath: items[index].filePath,
				message: message,
			} as UTSJSONObject)
		}
	}
	return {
		successItems: successItems,
		failItems: failItems,
	} as MediaBatchUploadResult
}

export function getMediaServeUrl(id: number | string): string {
	return baseUrl + '/api/media/serve/' + stringValue(id) + '/'
}

export function getMediaThumbnailServeUrl(id: number | string): string {
	return baseUrl + '/api/media/serve/' + stringValue(id) + '/thumbnail/'
}

export function getMediaDownloadUrl(id: number | string): string {
	return baseUrl + '/api/media/serve/' + stringValue(id) + '/download/'
}
