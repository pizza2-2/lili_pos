# lili-UniversaForm

uni-app x 通用表单组件，支持以下能力：

- 创建 / 编辑两种模式
- 折叠分组
- `lili_bottom-select` 底部选择
- `lili-upload` 图片上传
- 脏数据检测与返回拦截

## 基本用法

```vue
<template>
  <lili-UniversaForm
    mode="create"
    :formSections="formSections"
    :initialData="initialData"
    @submit="handleSubmit"
    @save-request="handleSaveRequest"
    @cancel="handleCancel"
    @bottom-select-add="handleAdd"
    @bottom-select-edit="handleEdit"
  />
</template>

<script setup lang="uts">
type Props = {
  title: string
}

const initialData = ref<UTSJSONObject>({
  name: '',
  categoryId: '',
  images: [] as string[],
} as UTSJSONObject)

async function fetchCategoryList(params: UTSJSONObject) : Promise<UTSJSONObject> {
  return {
    data: [
      { value: '1', text: '分类A' },
      { value: '2', text: '分类B' },
    ] as UTSJSONObject[],
    total: 2,
  } as UTSJSONObject
}

const formSections = ref<UTSJSONObject[]>([
  {
    key: 'base',
    title: '基础信息',
    defaultOpen: true,
    fields: [
      {
        key: 'name',
        label: '名称',
        type: 'input',
        required: true,
        placeholder: '请输入名称',
      },
      {
        key: 'categoryId',
        label: '分类',
        type: 'bottomSelect',
        placeholder: '请选择分类',
        fetchData: fetchCategoryList,
      },
      {
        key: 'images',
        label: '图片',
        type: 'upload',
        action: 'https://example.com/upload',
        max: 6,
      },
    ] as UTSJSONObject[],
  },
] as UTSJSONObject[])

function handleSubmit(payload: UTSJSONObject) {
  console.log('submit', payload)
}

function handleSaveRequest(payload: UTSJSONObject) {
  console.log('save-request', payload)
}

function handleCancel(payload: UTSJSONObject) {
  console.log('cancel', payload)
}

function handleAdd(payload: UTSJSONObject) {
  console.log('bottom-select-add', payload)
}

function handleEdit(payload: UTSJSONObject) {
  console.log('bottom-select-edit', payload)
}
</script>
```

## 字段配置

- `type: 'input' | 'textarea' | 'number' | 'switch' | 'bottomSelect' | 'select' | 'upload'`
- `required: boolean` 必填校验
- `readonly: boolean` 只读
- `createOnly: boolean` 仅创建模式可编辑
- `editOnly: boolean` 仅编辑模式可编辑
- `fetchData: (params) => Promise<UTSJSONObject>` 给 `bottomSelect` / `select` 使用
- `action / headers / formData / max / uploadText` 给 `upload` 使用

## 暴露方法

- `validate()`
- `getFormData()`
- `setFormData(data)`
- `resetForm()`
- `resetDirty()`
- `hasPendingChanges()`
- `confirmLeave()`
- `submit()`
