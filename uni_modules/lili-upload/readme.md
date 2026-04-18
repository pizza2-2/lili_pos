# lili-upload

面向 `uni-app x` 的轻量图片上传组件，当前版本只覆盖图片场景，适合 Android 项目使用。

## 已实现能力

- 图片选择
- 图片预览
- 上传事件
- 删除事件
- `v-model` 同步图片列表

## 基本示例

```vue
<template>
  <lili-upload
    v-model="images"
    action="https://example.com/upload"
    name="file"
    :max="6"
    @upload="handleUpload"
    @delete="handleDelete"
    @error="handleError"
  />
</template>

<script setup lang="uts">
const images = ref<string[]>([])

function handleUpload(payload: UTSJSONObject) {
  console.log('upload', payload)
}

function handleDelete(payload: UTSJSONObject) {
  console.log('delete', payload)
}

function handleError(payload: UTSJSONObject) {
  console.log('error', payload)
}
</script>
```

## Props

- `modelValue: string[]` 图片列表，支持本地路径或网络地址
- `action: string` 上传地址
- `name: string` 上传字段名，默认 `file`
- `headers: UTSJSONObject` 上传请求头
- `formData: UTSJSONObject` 上传附加参数
- `max: number` 最大上传数量，默认 `9`
- `disabled: boolean` 是否禁用，默认 `false`
- `previewEnabled: boolean` 是否允许预览，默认 `true`
- `uploadText: string` 添加按钮文案，默认 `上传图片`

## Events

- `update:modelValue` 图片列表变化
- `upload` 单张图片上传成功
- `delete` 删除图片
- `preview` 点击图片预览
- `error` 选择或上传失败
