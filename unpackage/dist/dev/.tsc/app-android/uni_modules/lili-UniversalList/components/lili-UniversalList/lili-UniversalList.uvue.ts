import _easycom_lili_preview from '@/uni_modules/lili-preview/components/lili-preview/lili-preview.uvue'
type Props = { __$originalPosition?: UTSSourceMapPosition<"Props", "uni_modules/lili-UniversalList/components/lili-UniversalList/lili-UniversalList.uvue", 131, 6>;
	items?: UTSJSONObject[]
	keyField?: string
	titleField?: string
	subtitleField?: string
	metaField?: string
	imageField?: string
	imageListField?: string
	showImage?: boolean
	fields?: UTSJSONObject[]
	tagField?: string
	tagColorMap?: UTSJSONObject
	showActions?: boolean
	actions?: UTSJSONObject[]
	selectionMode?: boolean
	selectedItems?: string[]
	loading?: boolean
	loadingText?: string
	keepContentOnLoading?: boolean
	inlineLoadingText?: string
	emptyText?: string
	emptyIcon?: string
	showChevron?: boolean
	showMenu?: boolean
	menuActions?: UTSJSONObject[]
	showPagination?: boolean
	currentPage?: number
	totalPages?: number
	totalCount?: number
	showFloatingAdd?: boolean
	floatingAddText?: string
	enablePreviewSave?: boolean
	enablePreviewShare?: boolean
}


const __sfc__ = defineComponent({
  __name: 'lili-UniversalList',
  props: {
    items: { type: Array as PropType<UTSJSONObject[]>, required: false, default: () : UTSJSONObject[] => [] },
    keyField: { type: String, required: false, default: 'id' },
    titleField: { type: String, required: false, default: 'title' },
    subtitleField: { type: String, required: false, default: '' },
    metaField: { type: String, required: false, default: '' },
    imageField: { type: String, required: false, default: 'image' },
    imageListField: { type: String, required: false, default: 'images' },
    showImage: { type: Boolean, required: false, default: true },
    fields: { type: Array as PropType<UTSJSONObject[]>, required: false, default: () : UTSJSONObject[] => [] },
    tagField: { type: String, required: false, default: 'tags' },
    tagColorMap: { type: UTSJSONObject, required: false, default: () : UTSJSONObject => ({
		进行中: 'info',
		已完成: 'success',
		已取消: 'danger',
		待处理: 'warning',
		草稿: 'muted',
		启用: 'success',
		禁用: 'danger',
		新品: 'violet',
		热销: 'warning',
		推荐: 'info',
	}) },
    showActions: { type: Boolean, required: false, default: false },
    actions: { type: Array as PropType<UTSJSONObject[]>, required: false, default: () : UTSJSONObject[] => [
		{ key: 'detail', text: '详情', icon: '⌕', tone: 'info' } as UTSJSONObject,
		{ key: 'edit', text: '编辑', icon: '✎', tone: 'success' } as UTSJSONObject,
	] },
    selectionMode: { type: Boolean, required: false, default: false },
    selectedItems: { type: Array as PropType<string[]>, required: false, default: () : string[] => [] },
    loading: { type: Boolean, required: false, default: false },
    loadingText: { type: String, required: false, default: '加载中...' },
    keepContentOnLoading: { type: Boolean, required: false, default: false },
    inlineLoadingText: { type: String, required: false, default: '刷新中...' },
    emptyText: { type: String, required: false, default: '暂无数据' },
    emptyIcon: { type: String, required: false, default: '◌' },
    showChevron: { type: Boolean, required: false, default: true },
    showMenu: { type: Boolean, required: false, default: false },
    menuActions: { type: Array as PropType<UTSJSONObject[]>, required: false, default: () : UTSJSONObject[] => [] },
    showPagination: { type: Boolean, required: false, default: false },
    currentPage: { type: Number, required: false, default: 1 },
    totalPages: { type: Number, required: false, default: 1 },
    totalCount: { type: Number, required: false, default: 0 },
    showFloatingAdd: { type: Boolean, required: false, default: false },
    floatingAddText: { type: String, required: false, default: '新增' },
    enablePreviewSave: { type: Boolean, required: false, default: true },
    enablePreviewShare: { type: Boolean, required: false, default: true }
  },
  emits: ["item-click", "action", "update:selectedItems", "selection-change", "image-preview", "preview-close", "menu", "page-change", "subtitle-click", "field-click", "meta-click", "floating-add"],
  setup(__props) {
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

const props = __props

function emit(event: string, ...do_not_transform_spread: Array<any | null>) {
__ins.emit(event, ...do_not_transform_spread)
}

const previewImages = ref<string[]>([])
const previewIndex = ref<number>(0)
const previewVisible = ref<boolean>(false)
const previewItem = ref<UTSJSONObject | null>(null)

const showBlockingLoading = computed<boolean>(() : boolean => {
	if (!props.loading) {
		return false
	}
	if (!props.keepContentOnLoading) {
		return true
	}
	return props.items.length == 0
})

const showInlineLoading = computed<boolean>(() : boolean => {
	return props.loading && props.keepContentOnLoading && props.items.length > 0
})

function stringValue(value: any | null) : string {
	if (value == null) return ''
	return '' + value
}

function safeCurrentPage() : number {
	if (props.currentPage <= 0) return 1
	return props.currentPage
}

function safeTotalPages() : number {
	if (props.totalPages <= 0) return 1
	return props.totalPages
}

function safeTotalCount() : number {
	if (props.totalCount < 0) return 0
	return props.totalCount
}

const currentPageText = computed<string>(() : string => {
	return '' + safeCurrentPage()
})

const totalPagesText = computed<string>(() : string => {
	return '' + safeTotalPages()
})

const totalCountText = computed<string>(() : string => {
	return '' + safeTotalCount()
})

function objectField(obj: UTSJSONObject, key: string) : any | null {
	return obj[key]
}

function fieldText(item: UTSJSONObject, path: string) : string {
	if (path == '') return ''
	let current: any | null = item
	const parts = path.split('.')
	for (let i = 0; i < parts.length; i++) {
		if (current == null) return ''
		const currentObj = current as UTSJSONObject
		current = currentObj[parts[i]]
	}
	return stringValue(current)
}

function fieldLabel(field: UTSJSONObject) : string {
	return stringValue(field['label'])
}

function fieldIcon(field: UTSJSONObject) : string {
	return stringValue(field['icon'])
}

function fieldType(field: UTSJSONObject) : string {
	return stringValue(field['type'])
}

function fieldKey(field: UTSJSONObject, index: number) : string {
	const key = stringValue(field['key'])
	if (key != '') return key
	return 'field-' + index
}

function actionKey(action: UTSJSONObject, index: number) : string {
	const key = stringValue(action['key'])
	if (key != '') return key
	return 'action-' + index
}

function actionText(action: UTSJSONObject) : string {
	return stringValue(action['text'])
}

function menuText(action: UTSJSONObject) : string {
	return stringValue(action['text'])
}

function actionIcon(action: UTSJSONObject) : string {
	return stringValue(action['icon'])
}

function actionTone(action: UTSJSONObject) : string {
	const tone = stringValue(action['tone'])
	if (tone != '') return tone
	return 'info'
}

function stringArrayContains(list: string[], value: string) : boolean {
	for (let i = 0; i < list.length; i++) {
		if (list[i] == value) {
			return true
		}
	}
	return false
}

function displayField(item: UTSJSONObject, field: UTSJSONObject) : string {
	const raw = fieldText(item, stringValue(field['key']))
	const type = fieldType(field)
	if (type == 'price') {
		if (raw == '') return ''
		return '¥ ' + raw
	}
	if (type == 'status') {
		return raw
	}
	return raw
}

function itemId(item: UTSJSONObject) : string {
	return fieldText(item, props.keyField)
}

function itemKey(item: UTSJSONObject, index: number) : string {
	const key = itemId(item)
	if (key != '') return key
	return 'item-' + index
}

function isSelected(item: UTSJSONObject) : boolean {
	const id = itemId(item)
	if (id == '') return false
	for (let i = 0; i < props.selectedItems.length; i++) {
		if (props.selectedItems[i] == id) {
			return true
		}
	}
	return false
}

function toggleSelection(item: UTSJSONObject) {
	const id = itemId(item)
	if (id == '') return
	const nextSelected: string[] = []
	let removed = false
	for (let i = 0; i < props.selectedItems.length; i++) {
		const current = props.selectedItems[i]
		if (current == id) {
			removed = true
			continue
		}
		nextSelected.push(current)
	}
	if (!removed) {
		nextSelected.push(id)
	}
	emit('update:selectedItems', nextSelected)
	emit('selection-change', nextSelected)
}

function handleItemClick(item: UTSJSONObject) {
	if (props.selectionMode) {
		toggleSelection(item)
		return
	}
	emit('item-click', item)
}

function handleSubtitleClick(item: UTSJSONObject) {
	emit('subtitle-click', {
		item: item,
		field: props.subtitleField,
		value: fieldText(item, props.subtitleField),
	} as UTSJSONObject)
}

function handleMetaClick(item: UTSJSONObject) {
	emit('meta-click', {
		item: item,
		field: props.metaField,
		value: fieldText(item, props.metaField),
	} as UTSJSONObject)
}

function handleFieldClick(item: UTSJSONObject, field: UTSJSONObject) {
	emit('field-click', {
		item: item,
		field: field,
		key: stringValue(field['key']),
		label: fieldLabel(field),
		value: displayField(item, field),
	} as UTSJSONObject)
}

function imageListFromItem(item: UTSJSONObject) : string[] {
	const images: string[] = []
	const mainImage = fieldText(item, props.imageField)
	if (mainImage != '') {
		images.push(mainImage)
	}
	if (props.imageListField != '') {
		const raw = objectField(item, props.imageListField)
		if (raw != null) {
			if (raw instanceof Array) {
				const list = raw as Array<any>
				for (let i = 0; i < list.length; i++) {
					const imageUrl = stringValue(list[i])
					if (imageUrl != '' && !stringArrayContains(images, imageUrl)) {
						images.push(imageUrl)
					}
				}
			} else {
				const text = stringValue(raw)
				if (text != '') {
					const parts = text.split(',')
					for (let i = 0; i < parts.length; i++) {
						const imageUrl = parts[i].trim()
						if (imageUrl != '' && !stringArrayContains(images, imageUrl)) {
							images.push(imageUrl)
						}
					}
				}
			}
		}
	}
	return images
}

function firstImage(item: UTSJSONObject) : string {
	const list = imageListFromItem(item)
	if (list.length == 0) return ''
	return list[0]
}

function imageCount(item: UTSJSONObject) : number {
	return imageListFromItem(item).length
}

function openPreview(item: UTSJSONObject, index: number) {
	const list = imageListFromItem(item)
	if (list.length == 0) return
	previewImages.value = list
	previewIndex.value = index
	previewVisible.value = true
	previewItem.value = item
}

function handlePreviewVisibleChange(value: boolean) {
	previewVisible.value = value
}

function handlePreviewIndexChange(value: number) {
	previewIndex.value = value
}

function handlePreviewOpen(payload: UTSJSONObject) {
	emit('image-preview', {
		item: previewItem.value,
		preview: payload,
	} as UTSJSONObject)
}

function handlePreviewClose(payload: UTSJSONObject) {
	emit('preview-close', {
		item: previewItem.value,
		preview: payload,
	} as UTSJSONObject)
	previewItem.value = null
}

function tagList(item: UTSJSONObject) : string[] {
	const result: string[] = []
	if (props.tagField == '') return result
	const raw = objectField(item, props.tagField)
	if (raw == null) return result
	if (raw instanceof Array) {
		const list = raw as Array<any>
		for (let i = 0; i < list.length; i++) {
			const tag = stringValue(list[i])
			if (tag != '') {
				result.push(tag)
			}
		}
		return result
	}
	const text = stringValue(raw)
	if (text == '') return result
	const parts = text.split(',')
	for (let i = 0; i < parts.length; i++) {
		const tag = parts[i].trim()
		if (tag != '') {
			result.push(tag)
		}
	}
	return result
}

function toneForTag(tag: string) : string {
	const mapped = objectField(props.tagColorMap, tag)
	const mappedText = stringValue(mapped)
	if (mappedText != '') return mappedText
	if (tag.indexOf('完成') >= 0 || tag.indexOf('成功') >= 0 || tag.indexOf('启用') >= 0) return 'success'
	if (tag.indexOf('取消') >= 0 || tag.indexOf('失败') >= 0 || tag.indexOf('禁用') >= 0) return 'danger'
	if (tag.indexOf('待') >= 0 || tag.indexOf('预警') >= 0 || tag.indexOf('热销') >= 0) return 'warning'
	if (tag.indexOf('新品') >= 0 || tag.indexOf('推荐') >= 0) return 'violet'
	return 'info'
}

function tagClass(tag: string) : string {
	const tone = toneForTag(tag)
	return 'lul-tag lul-tag-' + tone
}

function tagTextClass(tag: string) : string {
	const tone = toneForTag(tag)
	return 'lul-tag-text lul-tag-text-' + tone
}

function actionClass(action: UTSJSONObject) : string {
	return 'lul-action lul-action-' + actionTone(action)
}

function actionTextClass(action: UTSJSONObject) : string {
	return 'lul-action-text lul-action-text-' + actionTone(action)
}

function actionIconClass(action: UTSJSONObject) : string {
	return 'lul-action-icon lul-action-text-' + actionTone(action)
}

function handleAction(item: UTSJSONObject, action: UTSJSONObject) {
	emit('action', {
		item: item,
		action: action,
	} as UTSJSONObject)
}

function handleMenu(item: UTSJSONObject) {
	if (props.menuActions.length == 0) {
		return
	}

	const itemList: string[] = []
	for (let i = 0; i < props.menuActions.length; i++) {
		itemList.push(menuText(props.menuActions[i]))
	}

	uni.showActionSheet({
		itemList: itemList,
		success: (res) => {
			const selectedIndex = res.tapIndex
			if (selectedIndex < 0 || selectedIndex >= props.menuActions.length) {
				return
			}
			emit('menu', {
				item: item,
				action: props.menuActions[selectedIndex],
				index: selectedIndex,
			} as UTSJSONObject)
		},
	})
}

function canGoPrev() : boolean {
	return safeCurrentPage() > 1
}

function canGoNext() : boolean {
	return safeCurrentPage() < safeTotalPages()
}

function emitPageChange(page: number) {
	emit('page-change', {
		page: page,
		currentPage: safeCurrentPage(),
		totalPages: safeTotalPages(),
		totalCount: safeTotalCount(),
	} as UTSJSONObject)
}

function goPrevPage() {
	if (!canGoPrev()) {
		return
	}
	emitPageChange(safeCurrentPage() - 1)
}

function goNextPage() {
	if (!canGoNext()) {
		return
	}
	emitPageChange(safeCurrentPage() + 1)
}

function handleFloatingAdd() {
	emit('floating-add')
}

return (): any | null => {

const _component_lili_preview = resolveEasyComponent("lili-preview",_easycom_lili_preview)

  return _cE("view", _uM({ class: "lul-root" }), [
    isTrue(unref(showBlockingLoading))
      ? _cE("view", _uM({
          key: 0,
          class: "lul-state-card"
        }), [
          _cE("view", _uM({ class: "lul-loading-dot" })),
          _cE("text", _uM({ class: "lul-state-title" }), _tD(_ctx.loadingText), 1 /* TEXT */),
          _cE("text", _uM({ class: "lul-state-desc" }), "请稍候，列表正在更新。")
        ])
      : _ctx.items.length == 0
        ? _cE("view", _uM({
            key: 1,
            class: "lul-state-card"
          }), [
            _cE("view", _uM({ class: "lul-empty-badge" }), [
              _cE("text", _uM({ class: "lul-empty-badge-text" }), _tD(_ctx.emptyIcon), 1 /* TEXT */)
            ]),
            _cE("text", _uM({ class: "lul-state-title" }), _tD(_ctx.emptyText), 1 /* TEXT */),
            _cE("text", _uM({ class: "lul-state-desc" }), "当前没有可展示的数据。")
          ])
        : _cE("view", _uM({
            key: 2,
            class: "lul-list"
          }), [
            isTrue(unref(showInlineLoading))
              ? _cE("view", _uM({
                  key: 0,
                  class: "lul-inline-loading"
                }), [
                  _cE("view", _uM({ class: "lul-inline-loading-dot" })),
                  _cE("text", _uM({ class: "lul-inline-loading-text" }), _tD(_ctx.inlineLoadingText), 1 /* TEXT */)
                ])
              : _cC("v-if", true),
            _cE(Fragment, null, RenderHelpers.renderList(_ctx.items, (item, itemIndex, __index, _cached): any => {
              return _cE("view", _uM({
                key: itemKey(item, itemIndex),
                class: _nC(isSelected(item) ? 'lul-card lul-card-selected' : 'lul-card'),
                onClick: () => {handleItemClick(item)}
              }), [
                _cE("view", _uM({ class: "lul-card-top" }), [
                  _cE("view", _uM({ class: "lul-card-top-left" }), [
                    isTrue(_ctx.selectionMode)
                      ? _cE("view", _uM({
                          key: 0,
                          class: "lul-check-wrap",
                          onClick: withModifiers(() => {toggleSelection(item)}, ["stop"])
                        }), [
                          _cE("view", _uM({
                            class: _nC(isSelected(item) ? 'lul-check lul-check-active' : 'lul-check')
                          }), [
                            isTrue(isSelected(item))
                              ? _cE("text", _uM({
                                  key: 0,
                                  class: "lul-check-icon"
                                }), "✓")
                              : _cC("v-if", true)
                          ], 2 /* CLASS */)
                        ], 8 /* PROPS */, ["onClick"])
                      : _cC("v-if", true),
                    isTrue(_ctx.showImage && firstImage(item) != '')
                      ? _cE("view", _uM({
                          key: 1,
                          class: "lul-cover-wrap"
                        }), [
                          _cE("image", _uM({
                            class: "lul-cover",
                            src: firstImage(item),
                            mode: "aspectFill",
                            onClick: withModifiers(() => {openPreview(item, 0)}, ["stop"])
                          }), null, 8 /* PROPS */, ["src", "onClick"]),
                          imageCount(item) > 1
                            ? _cE("view", _uM({
                                key: 0,
                                class: "lul-cover-count"
                              }), [
                                _cE("text", _uM({ class: "lul-cover-count-text" }), "+" + _tD(imageCount(item) - 1), 1 /* TEXT */)
                              ])
                            : _cC("v-if", true)
                        ])
                      : _cC("v-if", true),
                    _cE("view", _uM({ class: "lul-main" }), [
                      _cE("view", _uM({ class: "lul-headline-row" }), [
                        _cE("view", _uM({ class: "lul-title-wrap" }), [
                          _ctx.titleField != ''
                            ? _cE("text", _uM({
                                key: 0,
                                class: "lul-title"
                              }), _tD(fieldText(item, _ctx.titleField)), 1 /* TEXT */)
                            : _cC("v-if", true),
                          isTrue(_ctx.subtitleField != '' && fieldText(item, _ctx.subtitleField) != '')
                            ? _cE("text", _uM({
                                key: 1,
                                class: "lul-subtitle",
                                onClick: withModifiers(() => {handleSubtitleClick(item)}, ["stop"])
                              }), _tD(fieldText(item, _ctx.subtitleField)), 9 /* TEXT, PROPS */, ["onClick"])
                            : _cC("v-if", true),
                          isTrue(_ctx.metaField != '' && fieldText(item, _ctx.metaField) != '')
                            ? _cE("text", _uM({
                                key: 2,
                                class: "lul-meta",
                                onClick: withModifiers(() => {handleMetaClick(item)}, ["stop"])
                              }), _tD(fieldText(item, _ctx.metaField)), 9 /* TEXT, PROPS */, ["onClick"])
                            : _cC("v-if", true)
                        ]),
                        isTrue(_ctx.showMenu)
                          ? _cE("view", _uM({
                              key: 0,
                              class: "lul-menu-wrap",
                              onClick: withModifiers(() => {handleMenu(item)}, ["stop"])
                            }), [
                              _cE("image", _uM({
                                class: "lul-menu-image",
                                src: "/static/icon/更多.png",
                                mode: "aspectFit"
                              }))
                            ], 8 /* PROPS */, ["onClick"])
                          : _cC("v-if", true),
                        isTrue(_ctx.showChevron)
                          ? _cE("view", _uM({
                              key: 1,
                              class: "lul-chevron-wrap"
                            }), [
                              _cE("text", _uM({ class: "lul-chevron" }), "›")
                            ])
                          : _cC("v-if", true)
                      ]),
                      tagList(item).length > 0
                        ? _cE("view", _uM({
                            key: 0,
                            class: "lul-tags"
                          }), [
                            _cE(Fragment, null, RenderHelpers.renderList(tagList(item), (tag, tagIndex, __index, _cached): any => {
                              return _cE("view", _uM({
                                key: tag + '-' + tagIndex,
                                class: _nC(tagClass(tag))
                              }), [
                                _cE("text", _uM({
                                  class: _nC(tagTextClass(tag))
                                }), _tD(tag), 3 /* TEXT, CLASS */)
                              ], 2 /* CLASS */)
                            }), 128 /* KEYED_FRAGMENT */)
                          ])
                        : _cC("v-if", true)
                    ])
                  ]),
                  _ctx.fields.length > 0
                    ? _cE("view", _uM({
                        key: 0,
                        class: "lul-fields"
                      }), [
                        _cE(Fragment, null, RenderHelpers.renderList(_ctx.fields, (field, fieldIndex, __index, _cached): any => {
                          return _cE("view", _uM({
                            key: fieldKey(field, fieldIndex),
                            class: "lul-field-chip",
                            onClick: withModifiers(() => {handleFieldClick(item, field)}, ["stop"])
                          }), [
                            fieldIcon(field) != ''
                              ? _cE("text", _uM({
                                  key: 0,
                                  class: "lul-field-icon"
                                }), _tD(fieldIcon(field)), 1 /* TEXT */)
                              : _cC("v-if", true),
                            fieldLabel(field) != ''
                              ? _cE("text", _uM({
                                  key: 1,
                                  class: "lul-field-label"
                                }), _tD(fieldLabel(field)), 1 /* TEXT */)
                              : _cC("v-if", true),
                            _cE("text", _uM({ class: "lul-field-value" }), _tD(displayField(item, field)), 1 /* TEXT */)
                          ], 8 /* PROPS */, ["onClick"])
                        }), 128 /* KEYED_FRAGMENT */)
                      ])
                    : _cC("v-if", true)
                ])
              ], 10 /* CLASS, PROPS */, ["onClick"])
            }), 128 /* KEYED_FRAGMENT */),
            isTrue(_ctx.showPagination)
              ? _cE("view", _uM({
                  key: 1,
                  class: "lul-pagination"
                }), [
                  _cE("view", _uM({ class: "lul-pagination-summary" }), [
                    _cE("text", _uM({ class: "lul-pagination-summary-text" }), "共 " + _tD(unref(totalCountText)) + " 条", 1 /* TEXT */),
                    _cE("text", _uM({ class: "lul-pagination-summary-text" }), "第 " + _tD(unref(currentPageText)) + " / " + _tD(unref(totalPagesText)) + " 页", 1 /* TEXT */)
                  ]),
                  _cE("view", _uM({ class: "lul-pagination-actions" }), [
                    _cE("view", _uM({
                      class: _nC(canGoPrev() ? 'lul-page-btn' : 'lul-page-btn lul-page-btn-disabled'),
                      onClick: goPrevPage
                    }), [
                      _cE("text", _uM({
                        class: _nC(canGoPrev() ? 'lul-page-btn-text' : 'lul-page-btn-text lul-page-btn-text-disabled')
                      }), "上一页", 2 /* CLASS */)
                    ], 2 /* CLASS */),
                    _cE("view", _uM({
                      class: _nC(canGoNext() ? 'lul-page-btn lul-page-btn-primary' : 'lul-page-btn lul-page-btn-primary lul-page-btn-disabled-primary'),
                      onClick: goNextPage
                    }), [
                      _cE("text", _uM({
                        class: _nC(canGoNext() ? 'lul-page-btn-text lul-page-btn-text-light' : 'lul-page-btn-text lul-page-btn-text-disabled-light')
                      }), "下一页", 2 /* CLASS */)
                    ], 2 /* CLASS */)
                  ])
                ])
              : _cC("v-if", true)
          ]),
    isTrue(unref(previewVisible))
      ? _cV(_component_lili_preview, _uM({
          key: 3,
          images: unref(previewImages),
          initialIndex: unref(previewIndex),
          visible: unref(previewVisible),
          showList: false,
          enableSave: _ctx.enablePreviewSave,
          enableShare: _ctx.enablePreviewShare,
          "onUpdate:visible": handlePreviewVisibleChange,
          "onUpdate:index": handlePreviewIndexChange,
          onPreview: handlePreviewOpen,
          onClose: handlePreviewClose
        }), null, 8 /* PROPS */, ["images", "initialIndex", "visible", "enableSave", "enableShare"])
      : _cC("v-if", true),
    isTrue(props.showFloatingAdd)
      ? _cE("view", _uM({
          key: 4,
          class: "lul-floating-add",
          onClick: handleFloatingAdd
        }), [
          _cE("text", _uM({ class: "lul-floating-add-text" }), _tD(props.floatingAddText), 1 /* TEXT */)
        ])
      : _cC("v-if", true)
  ])
}
}

})
export default __sfc__
export type LiliUniversalListComponentPublicInstance = InstanceType<typeof __sfc__>;
const GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalListStyles = [_uM([["lul-root", _pS(_uM([["width", "100%"]]))], ["lul-list", _pS(_uM([["width", "100%"]]))], ["lul-inline-loading", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"], ["paddingLeft", 12], ["paddingRight", 12], ["paddingTop", 10], ["paddingBottom", 10], ["marginBottom", 8], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["backgroundColor", "#EFF6FF"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#BFDBFE"], ["borderRightColor", "#BFDBFE"], ["borderBottomColor", "#BFDBFE"], ["borderLeftColor", "#BFDBFE"]]))], ["lul-inline-loading-dot", _pS(_uM([["width", 8], ["height", 8], ["borderTopLeftRadius", 4], ["borderTopRightRadius", 4], ["borderBottomRightRadius", 4], ["borderBottomLeftRadius", 4], ["backgroundColor", "#2563EB"]]))], ["lul-inline-loading-text", _pS(_uM([["marginLeft", 8], ["fontSize", 12], ["lineHeight", "16px"], ["color", "#1D4ED8"], ["fontWeight", "600"]]))], ["lul-card", _pS(_uM([["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", 14], ["borderTopRightRadius", 14], ["borderBottomRightRadius", 14], ["borderBottomLeftRadius", 14], ["paddingLeft", 10], ["paddingRight", 10], ["paddingTop", 10], ["paddingBottom", 10], ["marginBottom", 6], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E7ECF3"], ["borderRightColor", "#E7ECF3"], ["borderBottomColor", "#E7ECF3"], ["borderLeftColor", "#E7ECF3"]]))], ["lul-card-selected", _pS(_uM([["backgroundColor", "#F8FBFF"], ["borderTopColor", "#7CC4FF"], ["borderRightColor", "#7CC4FF"], ["borderBottomColor", "#7CC4FF"], ["borderLeftColor", "#7CC4FF"]]))], ["lul-card-top", _pS(_uM([["width", "100%"]]))], ["lul-card-top-left", _pS(_uM([["flexDirection", "row"]]))], ["lul-check-wrap", _pS(_uM([["width", 26], ["paddingTop", 4], ["alignItems", "flex-start"]]))], ["lul-check", _pS(_uM([["width", 18], ["height", 18], ["borderTopLeftRadius", 9], ["borderTopRightRadius", 9], ["borderBottomRightRadius", 9], ["borderBottomLeftRadius", 9], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#CBD5E1"], ["borderRightColor", "#CBD5E1"], ["borderBottomColor", "#CBD5E1"], ["borderLeftColor", "#CBD5E1"], ["alignItems", "center"], ["justifyContent", "center"], ["backgroundColor", "#FFFFFF"]]))], ["lul-check-active", _pS(_uM([["borderTopColor", "#0F172A"], ["borderRightColor", "#0F172A"], ["borderBottomColor", "#0F172A"], ["borderLeftColor", "#0F172A"], ["backgroundColor", "#0F172A"]]))], ["lul-check-icon", _pS(_uM([["fontSize", 10], ["lineHeight", "10px"], ["color", "#FFFFFF"]]))], ["lul-cover-wrap", _pS(_uM([["width", 64], ["height", 64], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["overflow", "hidden"], ["backgroundColor", "#E2E8F0"], ["position", "relative"], ["marginRight", 8]]))], ["lul-cover", _pS(_uM([["width", 64], ["height", 64]]))], ["lul-cover-count", _pS(_uM([["position", "absolute"], ["right", 6], ["bottom", 6], ["paddingLeft", 5], ["paddingRight", 5], ["paddingTop", 2], ["paddingBottom", 2], ["borderTopLeftRadius", 999], ["borderTopRightRadius", 999], ["borderBottomRightRadius", 999], ["borderBottomLeftRadius", 999], ["backgroundColor", "rgba(15,23,42,0.72)"]]))], ["lul-cover-count-text", _pS(_uM([["fontSize", 10], ["lineHeight", "10px"], ["color", "#FFFFFF"]]))], ["lul-main", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"]]))], ["lul-headline-row", _pS(_uM([["flexDirection", "row"], ["alignItems", "flex-start"], ["justifyContent", "space-between"]]))], ["lul-title-wrap", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["paddingRight", 6]]))], ["lul-title", _pS(_uM([["fontSize", 15], ["lineHeight", "18px"], ["color", "#0F172A"], ["fontWeight", "700"]]))], ["lul-subtitle", _pS(_uM([["fontSize", 12], ["lineHeight", "14px"], ["color", "#64748B"], ["marginTop", 2]]))], ["lul-meta", _pS(_uM([["fontSize", 12], ["lineHeight", "14px"], ["color", "#475569"], ["marginTop", 2]]))], ["lul-menu-wrap", _pS(_uM([["width", 24], ["height", 24], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["backgroundColor", "#F8FAFC"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E2E8F0"], ["borderRightColor", "#E2E8F0"], ["borderBottomColor", "#E2E8F0"], ["borderLeftColor", "#E2E8F0"], ["alignItems", "center"], ["justifyContent", "center"], ["marginRight", 6]]))], ["lul-menu-icon", _pS(_uM([["fontSize", 15], ["lineHeight", "15px"], ["color", "#64748B"]]))], ["lul-menu-image", _pS(_uM([["width", 16], ["height", 16]]))], ["lul-chevron-wrap", _pS(_uM([["width", 16], ["alignItems", "flex-end"], ["paddingTop", 1]]))], ["lul-chevron", _pS(_uM([["fontSize", 16], ["lineHeight", "16px"], ["color", "#94A3B8"]]))], ["lul-tags", _pS(_uM([["flexDirection", "row"], ["flexWrap", "wrap"], ["marginTop", 6], ["alignItems", "center"]]))], ["lul-tag", _pS(_uM([["alignItems", "center"], ["justifyContent", "center"], ["height", 22], ["paddingLeft", 8], ["paddingRight", 8], ["borderTopLeftRadius", 999], ["borderTopRightRadius", 999], ["borderBottomRightRadius", 999], ["borderBottomLeftRadius", 999], ["marginRight", 5], ["marginBottom", 4]]))], ["lul-tag-info", _pS(_uM([["backgroundColor", "#E0F2FE"]]))], ["lul-tag-success", _pS(_uM([["backgroundColor", "#DCFCE7"]]))], ["lul-tag-warning", _pS(_uM([["backgroundColor", "#FEF3C7"]]))], ["lul-tag-danger", _pS(_uM([["backgroundColor", "#FEE2E2"]]))], ["lul-tag-violet", _pS(_uM([["backgroundColor", "#EDE9FE"]]))], ["lul-tag-muted", _pS(_uM([["backgroundColor", "#E2E8F0"]]))], ["lul-tag-text", _pS(_uM([["fontSize", 10], ["lineHeight", "16px"], ["fontWeight", "600"]]))], ["lul-tag-text-info", _pS(_uM([["color", "#0369A1"]]))], ["lul-tag-text-success", _pS(_uM([["color", "#15803D"]]))], ["lul-tag-text-warning", _pS(_uM([["color", "#B45309"]]))], ["lul-tag-text-danger", _pS(_uM([["color", "#B91C1C"]]))], ["lul-tag-text-violet", _pS(_uM([["color", "#6D28D9"]]))], ["lul-tag-text-muted", _pS(_uM([["color", "#475569"]]))], ["lul-fields", _pS(_uM([["flexDirection", "row"], ["flexWrap", "wrap"], ["marginTop", 8], ["paddingTop", 8], ["borderTopWidth", 1], ["borderTopStyle", "solid"], ["borderTopColor", "#E7ECF3"]]))], ["lul-field-chip", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"], ["paddingLeft", 8], ["paddingRight", 8], ["paddingTop", 5], ["paddingBottom", 5], ["borderTopLeftRadius", 10], ["borderTopRightRadius", 10], ["borderBottomRightRadius", 10], ["borderBottomLeftRadius", 10], ["backgroundColor", "#F8FAFC"], ["marginRight", 5], ["marginTop", 4], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E2E8F0"], ["borderRightColor", "#E2E8F0"], ["borderBottomColor", "#E2E8F0"], ["borderLeftColor", "#E2E8F0"]]))], ["lul-field-icon", _pS(_uM([["fontSize", 11], ["lineHeight", "15px"]]))], ["lul-field-label", _pS(_uM([["fontSize", 11], ["lineHeight", "15px"]]))], ["lul-field-value", _pS(_uM([["fontSize", 11], ["lineHeight", "15px"]]))], ["lul-actions", _pS(_uM([["flexDirection", "row"], ["flexWrap", "wrap"], ["marginTop", 14]]))], ["lul-action", _pS(_uM([["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "center"], ["paddingLeft", 12], ["paddingRight", 12], ["paddingTop", 9], ["paddingBottom", 9], ["borderTopLeftRadius", 14], ["borderTopRightRadius", 14], ["borderBottomRightRadius", 14], ["borderBottomLeftRadius", 14], ["marginRight", 10], ["marginTop", 8], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"]]))], ["lul-action-info", _pS(_uM([["backgroundColor", "#F8FAFC"], ["borderTopColor", "#BFDBFE"], ["borderRightColor", "#BFDBFE"], ["borderBottomColor", "#BFDBFE"], ["borderLeftColor", "#BFDBFE"]]))], ["lul-action-success", _pS(_uM([["backgroundColor", "#F0FDF4"], ["borderTopColor", "#BBF7D0"], ["borderRightColor", "#BBF7D0"], ["borderBottomColor", "#BBF7D0"], ["borderLeftColor", "#BBF7D0"]]))], ["lul-action-warning", _pS(_uM([["backgroundColor", "#FFF7ED"], ["borderTopColor", "#FED7AA"], ["borderRightColor", "#FED7AA"], ["borderBottomColor", "#FED7AA"], ["borderLeftColor", "#FED7AA"]]))], ["lul-action-danger", _pS(_uM([["backgroundColor", "#FEF2F2"], ["borderTopColor", "#FECACA"], ["borderRightColor", "#FECACA"], ["borderBottomColor", "#FECACA"], ["borderLeftColor", "#FECACA"]]))], ["lul-action-icon", _pS(_uM([["fontSize", 12], ["lineHeight", "12px"], ["marginRight", 4]]))], ["lul-action-text", _pS(_uM([["fontSize", 12], ["lineHeight", "12px"], ["fontWeight", "700"]]))], ["lul-action-text-info", _pS(_uM([["color", "#1D4ED8"]]))], ["lul-action-text-success", _pS(_uM([["color", "#15803D"]]))], ["lul-action-text-warning", _pS(_uM([["color", "#C2410C"]]))], ["lul-action-text-danger", _pS(_uM([["color", "#B91C1C"]]))], ["lul-state-card", _pS(_uM([["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", 22], ["borderTopRightRadius", 22], ["borderBottomRightRadius", 22], ["borderBottomLeftRadius", 22], ["paddingTop", 34], ["paddingBottom", 34], ["paddingLeft", 20], ["paddingRight", 20], ["alignItems", "center"], ["justifyContent", "center"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E7ECF3"], ["borderRightColor", "#E7ECF3"], ["borderBottomColor", "#E7ECF3"], ["borderLeftColor", "#E7ECF3"]]))], ["lul-empty-badge", _pS(_uM([["width", 56], ["height", 56], ["borderTopLeftRadius", 28], ["borderTopRightRadius", 28], ["borderBottomRightRadius", 28], ["borderBottomLeftRadius", 28], ["backgroundColor", "#F1F5F9"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["lul-empty-badge-text", _pS(_uM([["fontSize", 24], ["lineHeight", "24px"], ["color", "#64748B"]]))], ["lul-loading-dot", _pS(_uM([["width", 18], ["height", 18], ["borderTopLeftRadius", 9], ["borderTopRightRadius", 9], ["borderBottomRightRadius", 9], ["borderBottomLeftRadius", 9], ["backgroundColor", "#0F172A"]]))], ["lul-state-title", _pS(_uM([["fontSize", 18], ["lineHeight", "24px"], ["color", "#0F172A"], ["fontWeight", "700"], ["marginTop", 14]]))], ["lul-state-desc", _pS(_uM([["fontSize", 13], ["lineHeight", "19px"], ["color", "#64748B"], ["marginTop", 6]]))], ["lul-pagination", _pS(_uM([["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", 18], ["borderTopRightRadius", 18], ["borderBottomRightRadius", 18], ["borderBottomLeftRadius", 18], ["paddingLeft", 16], ["paddingRight", 16], ["paddingTop", 14], ["paddingBottom", 14], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E7ECF3"], ["borderRightColor", "#E7ECF3"], ["borderBottomColor", "#E7ECF3"], ["borderLeftColor", "#E7ECF3"]]))], ["lul-pagination-summary", _pS(_uM([["flexDirection", "row"], ["justifyContent", "space-between"]]))], ["lul-pagination-summary-text", _pS(_uM([["fontSize", 12], ["lineHeight", "18px"], ["color", "#64748B"]]))], ["lul-pagination-actions", _pS(_uM([["flexDirection", "row"], ["marginTop", 12]]))], ["lul-page-btn", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["height", 38], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12], ["backgroundColor", "#F8FAFC"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#E2E8F0"], ["borderRightColor", "#E2E8F0"], ["borderBottomColor", "#E2E8F0"], ["borderLeftColor", "#E2E8F0"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["lul-page-btn-primary", _pS(_uM([["backgroundColor", "#0F172A"], ["borderTopColor", "#0F172A"], ["borderRightColor", "#0F172A"], ["borderBottomColor", "#0F172A"], ["borderLeftColor", "#0F172A"], ["marginLeft", 10]]))], ["lul-page-btn-disabled", _pS(_uM([["backgroundColor", "#F8FAFC"], ["borderTopColor", "#E5E7EB"], ["borderRightColor", "#E5E7EB"], ["borderBottomColor", "#E5E7EB"], ["borderLeftColor", "#E5E7EB"]]))], ["lul-page-btn-disabled-primary", _pS(_uM([["backgroundColor", "#CBD5E1"], ["borderTopColor", "#CBD5E1"], ["borderRightColor", "#CBD5E1"], ["borderBottomColor", "#CBD5E1"], ["borderLeftColor", "#CBD5E1"]]))], ["lul-page-btn-text", _pS(_uM([["fontSize", 14], ["lineHeight", "14px"], ["color", "#334155"], ["fontWeight", "700"]]))], ["lul-page-btn-text-light", _pS(_uM([["color", "#FFFFFF"]]))], ["lul-page-btn-text-disabled", _pS(_uM([["color", "#94A3B8"]]))], ["lul-page-btn-text-disabled-light", _pS(_uM([["color", "#E2E8F0"]]))], ["lul-floating-add", _pS(_uM([["position", "fixed"], ["right", 14], ["bottom", 18], ["height", 36], ["paddingLeft", 14], ["paddingRight", 14], ["borderTopLeftRadius", 18], ["borderTopRightRadius", 18], ["borderBottomRightRadius", 18], ["borderBottomLeftRadius", 18], ["alignItems", "center"], ["justifyContent", "center"], ["backgroundColor", "rgba(15,23,42,0.92)"]]))], ["lul-floating-add-text", _pS(_uM([["fontSize", 13], ["lineHeight", "16px"], ["color", "#FFFFFF"]]))]])]
