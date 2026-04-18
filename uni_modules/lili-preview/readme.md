# lili-preview

uni-app x 图片预览组件，支持：

- 缩略图列表展示
- 点击后全屏多图预览
- 左右切换图片
- 保存图片到相册
- 系统分享图片

## 用法

```vue
<lili-preview
  :images="images"
  :thumbSize="72"
  :radius="36"
  :gap="12"
  @preview="handlePreview"
  @save="handleSave"
  @share="handleShare"
/>
```

## Props

- `images: string[]` 图片地址数组，支持本地路径和网络地址
- `initialIndex: number` 初始预览索引
- `thumbSize: number` 缩略图尺寸，默认 `72`
- `radius: number` 缩略图圆角，默认 `12`
- `gap: number` 缩略图间距，默认 `12`
- `enableSave: boolean` 是否显示保存按钮，默认 `true`
- `enableShare: boolean` 是否显示分享按钮，默认 `true`
- `emptyText: string` 空状态文案，默认 `暂无图片`

## Events

- `preview` 打开预览时触发
- `save` 保存成功后触发
- `share` 触发分享后触发
