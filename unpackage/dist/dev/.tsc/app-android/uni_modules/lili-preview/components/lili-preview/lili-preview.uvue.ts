import { computed, ref, watch } from 'vue'

type Props = { __$originalPosition?: UTSSourceMapPosition<"Props", "uni_modules/lili-preview/components/lili-preview/lili-preview.uvue", 63, 6>;
	images?: string[]
	initialIndex?: number
	visible?: boolean
	thumbSize?: number
	radius?: number
	gap?: number
	enableSave?: boolean
	enableShare?: boolean
	emptyText?: string
	showList?: boolean
}


const __sfc__ = defineComponent({
  __name: 'lili-preview',
  props: {
    images: { type: Array as PropType<string[]>, required: false, default: () : string[] => [] },
    initialIndex: { type: Number, required: false, default: 0 },
    visible: { type: Boolean, required: false, default: false },
    thumbSize: { type: Number, required: false, default: 72 },
    radius: { type: Number, required: false, default: 12 },
    gap: { type: Number, required: false, default: 12 },
    enableSave: { type: Boolean, required: false, default: true },
    enableShare: { type: Boolean, required: false, default: true },
    emptyText: { type: String, required: false, default: '暂无图片' },
    showList: { type: Boolean, required: false, default: true }
  },
  emits: ["preview", "close", "save", "share", "update:visible", "update:index"],
  setup(__props) {
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

const props = __props

function emit(event: string, ...do_not_transform_spread: Array<any | null>) {
__ins.emit(event, ...do_not_transform_spread)
}

const imageList = ref<string[]>([])
const currentIndex = ref<number>(0)
const previewVisible = ref<boolean>(false)

const itemStyle = computed<string>(() : string => {
	return 'width:' + props.thumbSize + 'px;height:' + props.thumbSize + 'px;margin-right:' + props.gap + 'px;margin-bottom:' + props.gap + 'px;border-radius:' + props.radius + 'px;'
})

const imageStyle = computed<string>(() : string => {
	return 'border-radius:' + props.radius + 'px;'
})

const indicatorText = computed<string>(() : string => {
	if (imageList.value.length == 0) {
		return '0 / 0'
	}
	return '' + (currentIndex.value + 1) + ' / ' + imageList.value.length
})

function cloneStringArray(list: string[]) : string[] {
	const result: string[] = []
	for (let i = 0; i < list.length; i++) {
		result.push(list[i])
	}
	return result
}

function clampIndex(index: number, length: number) : number {
	if (length <= 0) {
		return 0
	}
	if (index < 0) {
		return 0
	}
	if (index >= length) {
		return length - 1
	}
	return index
}

function syncImages(list: string[]) {
	imageList.value = cloneStringArray(list)
	currentIndex.value = clampIndex(currentIndex.value, imageList.value.length)
}

function buildPayload(action: string, path: string) : UTSJSONObject {
	return {
		action: action,
		index: currentIndex.value,
		path: path,
		list: cloneStringArray(imageList.value),
	} as UTSJSONObject
}

function getCurrentImagePath() : string {
	if (imageList.value.length == 0) {
		return ''
	}
	if (currentIndex.value < 0 || currentIndex.value >= imageList.value.length) {
		return ''
	}
	return imageList.value[currentIndex.value]
}

function isRemotePath(path: string) : boolean {
	return path.startsWith('http://') || path.startsWith('https://')
}

function withLocalImagePath(path: string, actionName: string, next: (localPath: string) => void) {
	if (path == '') {
		uni.showToast({
			title: '图片地址无效',
			icon: 'none',
		})
		return
	}

	if (!isRemotePath(path)) {
		next(path)
		return
	}

	uni.showLoading({
		title: actionName == 'share' ? '准备分享' : '保存中',
		mask: true,
	})

	uni.downloadFile({
		url: path,
		success: (res) => {
			uni.hideLoading()
			if (res.statusCode == 200) {
				next(res.tempFilePath)
				return
			}
			uni.showToast({
				title: actionName == 'share' ? '下载后分享失败' : '下载失败',
				icon: 'none',
			})
		},
		fail: () => {
			uni.hideLoading()
			uni.showToast({
				title: actionName == 'share' ? '分享失败' : '保存失败',
				icon: 'none',
			})
		},
	})
}

function openPreview(index: number) {
	if (index < 0 || index >= imageList.value.length) {
		return
	}
	currentIndex.value = index
	previewVisible.value = true
	emit('update:index', currentIndex.value)
	emit('update:visible', true)
	const currentPath = getCurrentImagePath()
	emit('preview', buildPayload('preview', currentPath))
}

function closePreview() {
	const currentPath = getCurrentImagePath()
	previewVisible.value = false
	emit('update:visible', false)
	emit('close', buildPayload('close', currentPath))
}

function handleSwiperChange(event: SwiperChangeEvent) {
	currentIndex.value = event.detail.current
	emit('update:index', currentIndex.value)
}

function showPrevious() {
	if (imageList.value.length <= 1) {
		return
	}
	if (currentIndex.value <= 0) {
		currentIndex.value = imageList.value.length - 1
		emit('update:index', currentIndex.value)
		return
	}
	currentIndex.value = currentIndex.value - 1
	emit('update:index', currentIndex.value)
}

function showNext() {
	if (imageList.value.length <= 1) {
		return
	}
	if (currentIndex.value >= imageList.value.length - 1) {
		currentIndex.value = 0
		emit('update:index', currentIndex.value)
		return
	}
	currentIndex.value = currentIndex.value + 1
	emit('update:index', currentIndex.value)
}

function saveCurrentImage() {
	const currentPath = getCurrentImagePath()
	withLocalImagePath(currentPath, 'save', (localPath: string) => {
		uni.saveImageToPhotosAlbum({
			filePath: localPath,
			success: () => {
				uni.showToast({
					title: '已保存到相册',
					icon: 'success',
				})
				emit('save', buildPayload('save', currentPath))
			},
			fail: () => {
				uni.showToast({
					title: '保存失败',
					icon: 'none',
				})
			},
		})
	})
}

function shareCurrentImage() {
	const currentPath = getCurrentImagePath()
	withLocalImagePath(currentPath, 'share', (localPath: string) => {
		uni.shareWithSystem({
			type: 'image',
			imagePaths: [localPath],
			success: () => {
				emit('share', buildPayload('share', currentPath))
			},
			fail: () => {
				uni.setClipboardData({
					data: currentPath,
					success: () => {
						uni.showToast({
							title: '当前平台暂不支持系统分享，已复制图片地址',
							icon: 'none',
						})
						emit('share', buildPayload('share', currentPath))
					},
					fail: () => {
						uni.showToast({
							title: '当前平台暂不支持分享',
							icon: 'none',
						})
					},
				})
			},
		})
	})
}

watch(
	() : string[] => props.images,
	(newVal: string[]) => {
		syncImages(newVal)
	},
	{
		immediate: true,
	}
)

watch(
	() : number => props.initialIndex,
	(newVal: number) => {
		currentIndex.value = clampIndex(newVal, imageList.value.length)
	},
	{
		immediate: true,
	}
)

watch(
	() : boolean => props.visible,
	(newVal: boolean) => {
		previewVisible.value = newVal
	},
	{
		immediate: true,
	}
)

return (): any | null => {

  return _cE("view", _uM({ class: "lp-root" }), [
    isTrue(props.showList && imageList.value.length > 0)
      ? _cE("view", _uM({
          key: 0,
          class: "lp-list"
        }), [
          _cE(Fragment, null, RenderHelpers.renderList(imageList.value, (item, index, __index, _cached): any => {
            return _cE("view", _uM({
              key: item + '-' + index,
              class: "lp-item",
              style: _nS(itemStyle.value),
              onClick: () => {openPreview(index)}
            }), [
              _cE("image", _uM({
                class: "lp-image",
                style: _nS(imageStyle.value),
                src: item,
                mode: "aspectFill"
              }), null, 12 /* STYLE, PROPS */, ["src"])
            ], 12 /* STYLE, PROPS */, ["onClick"])
          }), 128 /* KEYED_FRAGMENT */)
        ])
      : isTrue(props.showList)
        ? _cE("view", _uM({
            key: 1,
            class: "lp-empty"
          }), [
            _cE("text", _uM({ class: "lp-empty-text" }), _tD(props.emptyText), 1 /* TEXT */)
          ])
        : _cC("v-if", true),
    isTrue(previewVisible.value)
      ? _cE("view", _uM({
          key: 2,
          class: "lp-preview"
        }), [
          _cE("view", _uM({ class: "lp-topbar" }), [
            _cE("view", _uM({
              class: "lp-topbar-button",
              onClick: closePreview
            }), [
              _cE("text", _uM({ class: "lp-topbar-button-text" }), "关闭")
            ]),
            _cE("text", _uM({ class: "lp-indicator" }), _tD(indicatorText.value), 1 /* TEXT */)
          ]),
          _cE("view", _uM({ class: "lp-swiper-wrap" }), [
            _cE("swiper", _uM({
              class: "lp-swiper",
              current: currentIndex.value,
              circular: imageList.value.length > 1,
              onChange: handleSwiperChange
            }), [
              _cE(Fragment, null, RenderHelpers.renderList(imageList.value, (item, index, __index, _cached): any => {
                return _cE("swiper-item", _uM({
                  key: 'preview-' + item + '-' + index
                }), [
                  _cE("view", _uM({ class: "lp-slide" }), [
                    _cE("image", _uM({
                      class: "lp-preview-image",
                      src: item,
                      mode: "aspectFit"
                    }), null, 8 /* PROPS */, ["src"])
                  ])
                ])
              }), 128 /* KEYED_FRAGMENT */)
            ], 40 /* PROPS, NEED_HYDRATION */, ["current", "circular"])
          ]),
          _cE("view", _uM({ class: "lp-actionbar" }), [
            imageList.value.length > 1
              ? _cE("view", _uM({
                  key: 0,
                  class: "lp-action",
                  onClick: showPrevious
                }), [
                  _cE("text", _uM({ class: "lp-action-text" }), "上一张")
                ])
              : _cC("v-if", true),
            isTrue(props.enableSave)
              ? _cE("view", _uM({
                  key: 1,
                  class: "lp-action",
                  onClick: saveCurrentImage
                }), [
                  _cE("text", _uM({ class: "lp-action-text" }), "保存")
                ])
              : _cC("v-if", true),
            isTrue(props.enableShare)
              ? _cE("view", _uM({
                  key: 2,
                  class: "lp-action",
                  onClick: shareCurrentImage
                }), [
                  _cE("text", _uM({ class: "lp-action-text" }), "分享")
                ])
              : _cC("v-if", true),
            imageList.value.length > 1
              ? _cE("view", _uM({
                  key: 3,
                  class: "lp-action",
                  onClick: showNext
                }), [
                  _cE("text", _uM({ class: "lp-action-text" }), "下一张")
                ])
              : _cC("v-if", true)
          ])
        ])
      : _cC("v-if", true)
  ])
}
}

})
export default __sfc__
export type LiliPreviewComponentPublicInstance = InstanceType<typeof __sfc__>;
const GenUniModulesLiliPreviewComponentsLiliPreviewLiliPreviewStyles = [_uM([["lp-root", _pS(_uM([["width", "100%"]]))], ["lp-list", _pS(_uM([["flexDirection", "row"], ["flexWrap", "wrap"]]))], ["lp-item", _pS(_uM([["overflow", "hidden"], ["backgroundColor", "#F3F4F6"]]))], ["lp-image", _pS(_uM([["width", "100%"], ["height", "100%"]]))], ["lp-empty", _pS(_uM([["width", "100%"], ["paddingTop", 16], ["paddingBottom", 16], ["alignItems", "center"], ["justifyContent", "center"], ["backgroundColor", "#F8FAFC"], ["borderTopLeftRadius", 12], ["borderTopRightRadius", 12], ["borderBottomRightRadius", 12], ["borderBottomLeftRadius", 12]]))], ["lp-empty-text", _pS(_uM([["fontSize", 14], ["color", "#64748B"]]))], ["lp-preview", _pS(_uM([["position", "fixed"], ["left", 0], ["right", 0], ["top", 0], ["bottom", 0], ["backgroundColor", "rgba(0,0,0,0.96)"], ["zIndex", 9999], ["paddingTop", "env(safe-area-inset-top)"], ["paddingBottom", "env(safe-area-inset-bottom)"]]))], ["lp-topbar", _pS(_uM([["paddingLeft", 16], ["paddingRight", 16], ["paddingTop", 12], ["paddingBottom", 12], ["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "space-between"]]))], ["lp-topbar-button", _pS(_uM([["paddingLeft", 14], ["paddingRight", 14], ["paddingTop", 10], ["paddingBottom", 10], ["backgroundColor", "rgba(255,255,255,0.14)"], ["borderTopLeftRadius", 999], ["borderTopRightRadius", 999], ["borderBottomRightRadius", 999], ["borderBottomLeftRadius", 999]]))], ["lp-topbar-button-text", _pS(_uM([["fontSize", 14], ["color", "#FFFFFF"]]))], ["lp-indicator", _pS(_uM([["fontSize", 14], ["color", "#FFFFFF"]]))], ["lp-swiper-wrap", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"]]))], ["lp-swiper", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"]]))], ["lp-slide", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["alignItems", "center"], ["justifyContent", "center"], ["paddingLeft", 20], ["paddingRight", 20], ["paddingTop", 120], ["paddingBottom", 140]]))], ["lp-preview-image", _pS(_uM([["width", "100%"], ["height", "100%"]]))], ["lp-actionbar", _pS(_uM([["paddingLeft", 16], ["paddingRight", 16], ["paddingTop", 12], ["paddingBottom", 28], ["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "center"], ["flexWrap", "wrap"]]))], ["lp-action", _pS(_uM([["minWidth", 88], ["marginLeft", 6], ["marginRight", 6], ["marginTop", 6], ["paddingLeft", 16], ["paddingRight", 16], ["paddingTop", 12], ["paddingBottom", 12], ["alignItems", "center"], ["justifyContent", "center"], ["backgroundColor", "rgba(255,255,255,0.14)"], ["borderTopLeftRadius", 999], ["borderTopRightRadius", 999], ["borderBottomRightRadius", 999], ["borderBottomLeftRadius", 999]]))], ["lp-action-text", _pS(_uM([["fontSize", 14], ["color", "#FFFFFF"]]))]])]
