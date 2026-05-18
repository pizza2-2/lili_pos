# lili-preview

uni-app x 图片预览组件，支持：

- 缩略图列表展示
- 点击后调用系统原生全屏预览
- 原生预览内左右切换图片
- 原生预览内手势缩放

## 用法

```vue
<lili-preview
  :images="images"
  :previewImages="previewImages"
  :thumbSize="72"
  :radius="36"
  :gap="12"
  @preview="handlePreview"
  @save="handleSave"
  @share="handleShare"
/>
```

## Props

- `images: string[]` 外层缩略图地址数组，支持本地路径和网络地址
- `previewImages: string[]` 点开预览时使用的高清图地址数组；为空时回退使用 `images`
- `initialIndex: number` 初始预览索引
- `thumbSize: number` 缩略图尺寸，默认 `72`
- `radius: number` 缩略图圆角，默认 `12`
- `gap: number` 缩略图间距，默认 `12`
- `emptyText: string` 空状态文案，默认 `暂无图片`

## Events

- `preview` 打开预览时触发
