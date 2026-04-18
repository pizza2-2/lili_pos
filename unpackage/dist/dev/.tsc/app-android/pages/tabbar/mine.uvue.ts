import { computed } from 'vue'
import { authState, clearAuthState } from '@/store/auth'

type MenuItem = { __$originalPosition?: UTSSourceMapPosition<"MenuItem", "pages/tabbar/mine.uvue", 48, 6>;
	id: number
	name: string
	icon: string
	key: string
	arrow: boolean
	tone?: string
}


const __sfc__ = defineComponent({
  __name: 'mine',
  setup(__props) {
const __ins = getCurrentInstance()!;
const _ctx = __ins.proxy as InstanceType<typeof __sfc__>;
const _cache = __ins.renderCache;

const menus = ref<MenuItem[]>([
	{ id: 1, name: '账本管理', icon: '☰', key: 'book', arrow: true },
	{ id: 2, name: '联系客服', icon: '✆', key: 'service', arrow: true },
	{ id: 3, name: '分享应用', icon: '⇪', key: 'share', arrow: false },
	{ id: 4, name: '添加桌面', icon: '⊞', key: 'desktop', arrow: false },
	{ id: 5, name: '退出登录', icon: '⎋', key: 'logout', arrow: false, tone: 'danger' },
])

const isLogin = computed(() : boolean => {
	return authState.token != ''
})

const displayName = computed(() : string => {
	if (authState.userInfo != null && authState.userInfo.name != '') {
		return authState.userInfo.name
	}
	return '易师傅'
})

const avatarText = computed(() : string => {
	const name = displayName.value
	if (name == '') return '易'
	return name.substring(0, 1)
})

const useDaysText = computed(() : string => {
	if (!isLogin.value) return '0'
	return '12'
})

const entryCountText = computed(() : string => {
	if (!isLogin.value) return '0'
	return '43'
})

function handleLogout() {
	if (!isLogin.value) {
		uni.navigateTo({
			url: '/pages/login/login',
		})
		return
	}

	uni.showModal({
		title: '提示',
		content: '确定退出登录吗？',
		success: (res) => {
			if (!res.confirm) return
			clearAuthState()
			uni.showToast({
				title: '已退出登录',
				icon: 'success',
			})
			setTimeout(() => {
				uni.navigateTo({
					url: '/pages/login/login',
				})
			}, 300)
		},
	})
}

function handleMenuClick(item: MenuItem) {
	if (item.key == 'logout') {
		handleLogout()
		return
	}

	if (item.key == 'book') {
		uni.showToast({
			title: '账本管理待接入',
			icon: 'none',
		})
		return
	}

	if (item.key == 'service') {
		uni.showToast({
			title: '联系客服待接入',
			icon: 'none',
		})
		return
	}

	if (item.key == 'share') {
		uni.showToast({
			title: '分享应用待接入',
			icon: 'none',
		})
		return
	}

	if (item.key == 'desktop') {
		uni.showToast({
			title: '添加桌面待接入',
			icon: 'none',
		})
	}
}

return (): any | null => {

  return _cE("scroll-view", _uM({
    class: "page-scroll",
    style: _nS(_uM({"flex":"1"})),
    direction: "vertical"
  }), [
    _cE("view", _uM({ class: "page" }), [
      _cE("view", _uM({ class: "status-bar-space" })),
      _cE("view", _uM({ class: "header" }), [
        _cE("view", _uM({ class: "user" }), [
          _cE("view", _uM({ class: "avatar" }), [
            _cE("text", _uM({ class: "avatar-text" }), _tD(avatarText.value), 1 /* TEXT */)
          ]),
          _cE("text", _uM({ class: "user-name" }), _tD(displayName.value), 1 /* TEXT */)
        ]),
        _cE("view", _uM({ class: "info" }), [
          _cE("view", _uM({ class: "info-item info-item-gap" }), [
            _cE("text", _uM({ class: "info-value" }), _tD(useDaysText.value), 1 /* TEXT */),
            _cE("text", _uM({ class: "info-label" }), "使用天数")
          ]),
          _cE("view", _uM({ class: "info-item" }), [
            _cE("text", _uM({ class: "info-value" }), _tD(entryCountText.value), 1 /* TEXT */),
            _cE("text", _uM({ class: "info-label" }), "记账笔数")
          ])
        ])
      ]),
      _cE("view", _uM({ class: "menu" }), [
        _cE(Fragment, null, RenderHelpers.renderList(unref(menus), (item, index, __index, _cached): any => {
          return _cE("view", _uM({
            key: item.id,
            class: _nC(index < unref(menus).length - 1 ? 'menu-item menu-item-border' : 'menu-item'),
            onClick: () => {handleMenuClick(item)}
          }), [
            _cE("view", _uM({ class: "menu-icon-wrap" }), [
              _cE("text", _uM({
                class: _nC(item.tone == 'danger' ? 'menu-icon menu-icon-danger' : 'menu-icon')
              }), _tD(item.icon), 3 /* TEXT, CLASS */)
            ]),
            _cE("text", _uM({
              class: _nC(item.tone == 'danger' ? 'menu-text menu-text-danger' : 'menu-text')
            }), _tD(item.name), 3 /* TEXT, CLASS */),
            isTrue(item.arrow)
              ? _cE("text", _uM({
                  key: 0,
                  class: "menu-arrow"
                }), "›")
              : _cC("v-if", true)
          ], 10 /* CLASS, PROPS */, ["onClick"])
        }), 128 /* KEYED_FRAGMENT */)
      ])
    ])
  ], 4 /* STYLE */)
}
}

})
export default __sfc__
const GenPagesTabbarMineStyles = [_uM([["page-scroll", _pS(_uM([["backgroundColor", "#F5F5F5"]]))], ["page", _pS(_uM([["backgroundColor", "#F5F5F5"]]))], ["status-bar-space", _pS(_uM([["height", CSS_VAR_STATUS_BAR_HEIGHT], ["backgroundColor", "#3C9BFD"]]))], ["header", _pS(_uM([["backgroundColor", "#3C9BFD"], ["paddingBottom", 30]]))], ["user", _pS(_uM([["alignItems", "center"], ["paddingTop", 12]]))], ["avatar", _pS(_uM([["width", 80], ["height", 80], ["borderTopLeftRadius", 40], ["borderTopRightRadius", 40], ["borderBottomRightRadius", 40], ["borderBottomLeftRadius", 40], ["backgroundColor", "rgba(255,255,255,0.3)"], ["alignItems", "center"], ["justifyContent", "center"]]))], ["avatar-text", _pS(_uM([["fontSize", 28], ["lineHeight", "28px"], ["color", "#FFFFFF"], ["fontWeight", "bold"]]))], ["user-name", _pS(_uM([["color", "#FFFFFF"], ["fontSize", 14], ["lineHeight", "20px"], ["marginTop", 10]]))], ["info", _pS(_uM([["flexDirection", "row"], ["marginTop", 30]]))], ["info-item", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["alignItems", "center"]]))], ["info-item-gap", _pS(_uM([["borderRightWidth", 1], ["borderRightStyle", "solid"], ["borderRightColor", "rgba(255,255,255,0.18)"]]))], ["info-value", _pS(_uM([["color", "#FFFFFF"], ["fontSize", 24], ["lineHeight", "28px"], ["fontWeight", "bold"]]))], ["info-label", _pS(_uM([["color", "#FFFFFF"], ["fontSize", 12], ["lineHeight", "18px"], ["marginTop", 8]]))], ["menu", _pS(_uM([["backgroundColor", "#FFFFFF"], ["marginTop", 12]]))], ["menu-item", _pS(_uM([["height", 56], ["flexDirection", "row"], ["alignItems", "center"], ["paddingLeft", 16], ["paddingRight", 16]]))], ["menu-item-border", _pS(_uM([["borderBottomWidth", 1], ["borderBottomStyle", "solid"], ["borderBottomColor", "#F0F0F0"]]))], ["menu-icon-wrap", _pS(_uM([["width", 28], ["alignItems", "center"], ["justifyContent", "center"]]))], ["menu-icon", _pS(_uM([["fontSize", 18], ["lineHeight", "18px"], ["color", "#3C9BFD"]]))], ["menu-icon-danger", _pS(_uM([["color", "#F04438"]]))], ["menu-text", _pS(_uM([["flexGrow", 1], ["flexShrink", 1], ["flexBasis", "0%"], ["fontSize", 14], ["lineHeight", "20px"], ["color", "#444444"], ["paddingLeft", 10]]))], ["menu-text-danger", _pS(_uM([["color", "#F04438"]]))], ["menu-arrow", _pS(_uM([["fontSize", 18], ["lineHeight", "18px"], ["color", "#BDBDBD"]]))]])]
