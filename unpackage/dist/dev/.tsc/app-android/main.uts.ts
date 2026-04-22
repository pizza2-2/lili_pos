import 'C:/Users/LENOVO/Desktop/HBuilderX.4.65.2025051206/HBuilderX/plugins/uniapp-cli-vite/node_modules/@dcloudio/uni-console/src/runtime/app/index.ts';import App from './App.uvue'

import { createSSRApp } from 'vue'
export function createApp() {
	const app = createSSRApp(App)
	return {
		app
	}
}
export function main(app: IApp) {
    definePageRoutes();
    defineAppConfig();
    (createApp()['app'] as VueApp).mount(app, GenUniApp());
}

export class UniAppConfig extends io.dcloud.uniapp.appframe.AppConfig {
    override name: string = "LILI_POS"
    override appid: string = "__UNI__1CE1B14"
    override versionName: string = "1.0.0"
    override versionCode: string = "100"
    override uniCompilerVersion: string = "5.07"
    
    constructor() { super() }
}

import GenPagesLoginLoginClass from './pages/login/login.uvue'
import GenPagesTabbarReportsClass from './pages/tabbar/reports.uvue'
import GenPagesTabbarProductsClass from './pages/tabbar/products.uvue'
import GenPagesTabbarSettingsClass from './pages/tabbar/settings.uvue'
import GenPagesTabbarMineClass from './pages/tabbar/mine.uvue'
import GenUniModulesUniUpgradeCenterAppPagesUniAppXUpgradePopupClass from './uni_modules/uni-upgrade-center-app/pages/uni-app-x/upgrade-popup.uvue'
import GenPagesWebviewWebviewClass from './pages/webview/webview.uvue'
import GenPagesPrivacyPrivacyClass from './pages/privacy/privacy.uvue'
import GenPagesSuppliersIndexClass from './pages/suppliers/index.uvue'
import GenPagesSuppliersFromClass from './pages/suppliers/from.uvue'
import GenPagesTransactionsIndexClass from './pages/transactions/index.uvue'
import GenPagesTransactionsFromClass from './pages/transactions/from.uvue'
import GenPagesKasaCategoryIndexClass from './pages/kasa_category/index.uvue'
import GenPagesKasaCategoryFormClass from './pages/kasa_category/form.uvue'
function definePageRoutes() {
__uniRoutes.push({ path: "pages/login/login", component: GenPagesLoginLoginClass, meta: { isQuit: true } as UniPageMeta, style: _uM([["navigationStyle","custom"]]) } as UniPageRoute)
__uniRoutes.push({ path: "pages/tabbar/reports", component: GenPagesTabbarReportsClass, meta: { isQuit: false } as UniPageMeta, style: _uM([["navigationStyle","custom"],["navigationBarTitleText","报表"]]) } as UniPageRoute)
__uniRoutes.push({ path: "pages/tabbar/products", component: GenPagesTabbarProductsClass, meta: { isQuit: false } as UniPageMeta, style: _uM([["navigationStyle","custom"],["navigationBarTitleText","商品"]]) } as UniPageRoute)
__uniRoutes.push({ path: "pages/tabbar/settings", component: GenPagesTabbarSettingsClass, meta: { isQuit: false } as UniPageMeta, style: _uM([["navigationStyle","custom"],["navigationBarTitleText","设置"]]) } as UniPageRoute)
__uniRoutes.push({ path: "pages/tabbar/mine", component: GenPagesTabbarMineClass, meta: { isQuit: false } as UniPageMeta, style: _uM([["navigationStyle","custom"],["navigationBarTitleText","我的"]]) } as UniPageRoute)
__uniRoutes.push({ path: "uni_modules/uni-upgrade-center-app/pages/uni-app-x/upgrade-popup", component: GenUniModulesUniUpgradeCenterAppPagesUniAppXUpgradePopupClass, meta: { isQuit: false } as UniPageMeta, style: _uM() } as UniPageRoute)
__uniRoutes.push({ path: "pages/webview/webview", component: GenPagesWebviewWebviewClass, meta: { isQuit: false } as UniPageMeta, style: _uM([["navigationBarTitleText",""]]) } as UniPageRoute)
__uniRoutes.push({ path: "pages/privacy/privacy", component: GenPagesPrivacyPrivacyClass, meta: { isQuit: false } as UniPageMeta, style: _uM([["navigationBarTitleText",""]]) } as UniPageRoute)
__uniRoutes.push({ path: "pages/suppliers/index", component: GenPagesSuppliersIndexClass, meta: { isQuit: false } as UniPageMeta, style: _uM([["navigationStyle","custom"],["navigationBarTitleText",""]]) } as UniPageRoute)
__uniRoutes.push({ path: "pages/suppliers/from", component: GenPagesSuppliersFromClass, meta: { isQuit: false } as UniPageMeta, style: _uM([["navigationStyle","custom"],["navigationBarTitleText",""]]) } as UniPageRoute)
__uniRoutes.push({ path: "pages/transactions/index", component: GenPagesTransactionsIndexClass, meta: { isQuit: false } as UniPageMeta, style: _uM([["navigationStyle","custom"],["navigationBarTitleText",""]]) } as UniPageRoute)
__uniRoutes.push({ path: "pages/transactions/from", component: GenPagesTransactionsFromClass, meta: { isQuit: false } as UniPageMeta, style: _uM([["navigationStyle","custom"],["navigationBarTitleText",""]]) } as UniPageRoute)
__uniRoutes.push({ path: "pages/kasa_category/index", component: GenPagesKasaCategoryIndexClass, meta: { isQuit: false } as UniPageMeta, style: _uM([["navigationStyle","custom"],["navigationBarTitleText",""]]) } as UniPageRoute)
__uniRoutes.push({ path: "pages/kasa_category/form", component: GenPagesKasaCategoryFormClass, meta: { isQuit: false } as UniPageMeta, style: _uM([["navigationStyle","custom"],["navigationBarTitleText",""]]) } as UniPageRoute)
}
const __uniTabBar: Map<string, any | null> | null = _uM([["color","#94A3B8"],["selectedColor","#0F172A"],["backgroundColor","#FFFFFF"],["borderStyle","black"],["list",[_uM([["pagePath","pages/tabbar/reports"],["iconPath","static/tabBar/Report.png"],["selectedIconPath","static/tabBar/Report (1).png"],["text","报表"]]),_uM([["pagePath","pages/tabbar/products"],["iconPath","static/tabBar/product (1).png"],["selectedIconPath","static/tabBar/product (2).png"],["text","商品"]]),_uM([["pagePath","pages/tabbar/settings"],["iconPath","static/tabBar/set.png"],["selectedIconPath","static/tabBar/set (1).png"],["text","功能"]]),_uM([["pagePath","pages/tabbar/mine"],["iconPath","static/tabBar/me.png"],["selectedIconPath","static/tabBar/me (1).png"],["text","我的"]])]]])
const __uniLaunchPage: Map<string, any | null> = _uM([["url","pages/login/login"],["style",_uM([["navigationStyle","custom"]])]])
function defineAppConfig(){
  __uniConfig.entryPagePath = '/pages/login/login'
  __uniConfig.globalStyle = _uM([["navigationBarTextStyle","black"],["navigationBarTitleText","uni-app x"],["navigationBarBackgroundColor","#F8F8F8"],["backgroundColor","#FFFFFF"]])
  __uniConfig.getTabBarConfig = ():Map<string, any> | null =>  _uM([["color","#94A3B8"],["selectedColor","#0F172A"],["backgroundColor","#FFFFFF"],["borderStyle","black"],["list",[_uM([["pagePath","pages/tabbar/reports"],["iconPath","static/tabBar/Report.png"],["selectedIconPath","static/tabBar/Report (1).png"],["text","报表"]]),_uM([["pagePath","pages/tabbar/products"],["iconPath","static/tabBar/product (1).png"],["selectedIconPath","static/tabBar/product (2).png"],["text","商品"]]),_uM([["pagePath","pages/tabbar/settings"],["iconPath","static/tabBar/set.png"],["selectedIconPath","static/tabBar/set (1).png"],["text","功能"]]),_uM([["pagePath","pages/tabbar/mine"],["iconPath","static/tabBar/me.png"],["selectedIconPath","static/tabBar/me (1).png"],["text","我的"]])]]])
  __uniConfig.tabBar = __uniConfig.getTabBarConfig()
  __uniConfig.conditionUrl = ''
  __uniConfig.uniIdRouter = _uM()
  
  __uniConfig.ready = true
}
