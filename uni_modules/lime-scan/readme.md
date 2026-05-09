# lime-scan 扫码组件
一个基于华为统一扫码(ScanKit)的扫码插件，支持单码模式，兼容安卓、iOS、鸿蒙和H5平台。提供了扫描二维码、条形码等多种码制的功能，同时支持生成二维码。组件提供了简单易用的API，使开发者能够方便地在应用中集成扫码功能。

## 文档链接
📚 组件详细文档请访问以下站点：
- [扫码组件文档 - 站点1](https://limex.qcoon.cn/native/scan.html)
- [扫码组件文档 - 站点2](https://limeui.netlify.app/native/scan.html)
- [扫码组件文档 - 站点3](https://limeui.familyzone.top/native/scan.html)

## 安装方法
1. 在uni-app插件市场中搜索并导入`lime-scan`
2. 导入后在页面引入相关方法
3. 需要自定义基座才能使用
4. 试用符合需求后才购买，插件无法退款
5. ios可能存在一维码识别不准的问题
6. 是华为的默认界面，无法更改文本和图标（慎重）如果不需要相册的可以考虑[lime-scancode](https://ext.dcloud.net.cn/plugin?id=25427)

## 代码演示

### 基础用法
简单的扫码功能实现：

```html
<view style="padding: 30rpx 0;">扫描结果：{{result}}</view>
<button type="primary" @click="scan">开启扫描</button>
```

```ts
import {scanCode, type ScanCodeOption} from '@/uni_modules/lime-scan';

export default {
    data() {
        return {
            type: '',
            result: '',
        }
    },
    methods:{
        scan() {
            scanCode({
                success: (res)=> {
                    this.result = res.result
                    console.log('扫码类型:', res.scanType)
                }
            } as ScanCodeOption)
        }
    }
}
```



### 生成二维码
在APP端生成二维码图片：

```ts
import { createQRCode, type CreateQRCodeOptions } from '@/uni_modules/lime-scan';

createQRCode({
    width: 300,
    height: 300,
    content: 'limeui.qcoon.cn',
    success(src: string) {
        console.log('二维码图片地址：', src);
    },
    fail(error: string) {
        console.log('生成失败：', error);
    }
} as CreateQRCodeOptions);
```



## H5端使用说明

1. 移动端访问摄像头权限需要 HTTPS 协议
2. 上传图片解码时，带logo的码可能识别不了，但直接扫码可以

### 配置HTTPS开发环境
由于H5端需要HTTPS协议才能访问摄像头，本地开发时可以配置Vite的HTTPS支持：

```bash
# 安装插件
pnpm i @vitejs/plugin-basic-ssl -D
```

在vite.config.ts中配置：
```ts
import basicSsl from '@vitejs/plugin-basic-ssl'
import uni from '@dcloudio/vite-plugin-uni';
import { defineConfig } from 'vite';
export default defineConfig({ 
    server: { 
        https: true 
    }, 
    plugins: [ basicSsl(), uni()] 
})
```

## 快速预览
导入插件后，可以直接使用以下标签查看演示效果：

```html
<!-- 代码位于 uni_modules/lime-scan/components/lime-scan -->
<lime-scan />
```

## 插件标签说明

| 标签名 | 说明 | 
| --- | --- | 
| `l-scan` | 组件标签 |
| `lime-scan` | 演示标签 |


## API文档

### scanCode 方法

| 参数 | 说明 | 类型 | 必填 |
| --- | --- | --- | --- |
| options | 扫码选项 | _ScanCodeOption_ | 是 |

### ScanCodeOption 选项

| 参数 | 类型 | 必填 | 说明 |
| --- | --- | --- | --- |
| scanType | _ScanFormatTypeCode[]_ | 否 | 扫码类型，默认所有类型 |
| onlyFromCamera | _boolean_ | 否 | 是否只使用相机扫码(仅不申请相册权限) |
| success | _(result: ScanCodeSuccessCallbackResult) => void_ | 否 | 接口调用成功的回调函数 |
| fail | _(res: GeneralCallbackResult) => void_ | 否 | 接口调用失败的回调函数 |
| complete | _(res: GeneralCallbackResult) => void_ | 否 | 接口调用结束的回调函数 |

### ScanCodeSuccessCallbackResult 返回参数

| 参数 | 类型 | 说明 |
| --- | --- | --- |
| result | _string_ | 所扫码的内容 |
| scanType | _ScanType_ | 所扫码的类型 |
| charSet | _string_ | 所扫码的字符集 |
| path | _string_ | 当所扫的码为当前小程序二维码时，会返回此字段 |
| rawData | _string_ | 原始数据，base64编码 |
| errMsg | _string_ | 错误信息 |

### createQRCode 方法

| 参数 | 说明 | 类型 | 必填 |
| --- | --- | --- | --- |
| options | 生成二维码选项 | _CreateQRCodeOptions_ | 是 |

### CreateQRCodeOptions 选项

| 参数 | 类型 | 必填 | 说明 |
| --- | --- | --- | --- |
| content | _string_ | 是 | 二维码内容 |
| width | _number_ | 是 | 二维码宽度 |
| height | _number_ | 是 | 二维码高度 |
| success | _(result: string) => void_ | 否 | 接口调用成功的回调函数 |
| fail | _(result: string) => void_ | 否 | 接口调用失败的回调函数 |
| complete | _(result: string) => void_ | 否 | 接口调用结束的回调函数 |

### 支持的扫码类型

#### ScanType 扫码结果类型

| 值 | 说明 |
| --- | --- |
| QR_CODE | 二维码 |
| AZTEC | 一维码 |
| CODABAR | 一维码 |
| CODE_39 | 一维码 |
| CODE_93 | 一维码 |
| CODE_128 | 一维码 |
| DATA_MATRIX | 二维码 |
| EAN_8 | 一维码 |
| EAN_13 | 一维码 |
| ITF | 一维码 |
| MAXICODE | 一维码 |
| PDF_417 | 二维码 |
| RSS_14 | 一维码 |
| RSS_EXPANDED | 一维码 |
| UPC_A | 一维码 |
| UPC_E | 一维码 |
| UPC_EAN_EXTENSION | 一维码 |
| WX_CODE | 二维码 |
| CODE_25 | 一维码 |

#### ScanFormatTypeCode 扫码类型选项

| 值 | 说明 |
| --- | --- |
| barCode | 一维码 |
| qrCode | 二维码 |
| datamatrix/dataMatrix | Data Matrix 码 |
| pdf417 | PDF417 条码 |
| codabar | Codabar 码 |
| code39 | Code 39 码 |
| code93 | Code 93 码 |
| code128 | Code 128 码 |
| ean8 | EAN-8 码 |
| ean13 | EAN-13 码 |
| itf | ITF 码 |
| upcA | UPC-A 码 |
| upcE | UPC-E 码 |
| aztec | Aztec 码 |

## 功能特点

- 支持多种码制的扫描，包括二维码、条形码等
- 支持生成二维码功能（仅APP端）
- 兼容安卓、iOS、鸿蒙和H5平台
- 提供简单易用的API接口
- H5端基于@zxing/library实现，无需额外配置
- 支持指定扫码类型，提高识别效率

## 常见问题

- H5端需要HTTPS协议才能访问摄像头
- H5端识别带logo的二维码可能会有困难
- APP端需要自定义基座才能使用
- 生成二维码功能仅在APP端支持
- 目前仅支持单码模式，不支持同时识别多个码

## 支持与赞赏

如果你觉得本插件解决了你的问题，可以考虑支持作者：

| 支付宝赞助 | 微信赞助 |
|------------|------------|
| ![](https://testingcf.jsdelivr.net/gh/liangei/image@1.9/alipay.png) | ![](https://testingcf.jsdelivr.net/gh/liangei/image@1.9/wpay.png) |