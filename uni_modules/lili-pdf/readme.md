# lili-pdf

Android 端 PDF 内嵌预览插件。当前实现会使用系统 `PdfRenderer` 把应用缓存内的 PDF 渲染为 PNG 页面，然后由业务页面用普通 `<image>` 渲染，不需要调用外部 PDF App。

```uts
import { renderPdfPages } from '@/uni_modules/lili-pdf'

renderPdfPages({
	filePath: '/path/to/file.pdf',
	scale: 2.0,
	maxPages: 20,
	success: (res) => {
		console.log(res.pages)
	},
})
```

### 开发文档
[UTS 语法](https://uniapp.dcloud.net.cn/tutorial/syntax-uts.html)
[UTS API插件](https://uniapp.dcloud.net.cn/plugin/uts-plugin.html)
[UTS uni-app兼容模式组件](https://uniapp.dcloud.net.cn/plugin/uts-component.html)
[UTS 标准模式组件](https://doc.dcloud.net.cn/uni-app-x/plugin/uts-vue-component.html)
[Hello UTS](https://gitcode.net/dcloud/hello-uts)
