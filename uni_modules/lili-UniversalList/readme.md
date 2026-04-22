# lili-UniversalList

通用业务列表组件，基于 uni-app x 重写，支持：

- 卡片式列表展示
- 图片缩略图 + `lili-preview` 预览
- 动态字段渲染
- 彩色标签展示
- 长按进入选择模式
- 行内操作按钮
- 加载态、空状态

## 基础用法

```vue
<template>
  <lili-UniversalList
    :items="listData"
    keyField="id"
    titleField="name"
    subtitleField="sn"
    imageField="thumbnail"
    imageListField="images"
    tagField="tags"
    :fields="fieldConfig"
    :actions="actionConfig"
    @item-click="handleItemClick"
    @action="handleAction"
  />
</template>
```

## `fields` 配置

每项为 `UTSJSONObject`，常用字段：

- `key`: 数据字段名，支持 `user.name`
- `label`: 字段标题
- `icon`: 字段前图标
- `type`: 可选 `text` / `price` / `status`

## `actions` 配置

每项为 `UTSJSONObject`，常用字段：

- `key`
- `text`
- `icon`
- `tone`: `info` / `success` / `warning` / `danger`

## 批量选择

支持长按任意卡片进入选择模式：

- `longPressToSelect`: 是否允许长按进入选择模式，默认 `true`
- `selectionMode`: 可选，父组件也可以直接控制是否处于选择模式
- `selectedItems`: 已选中项 id 数组
- `batchActions`: 底部批量操作按钮配置
- `showSelectAll`: 是否显示左下角全选，默认 `true`

批量操作按钮示例：

```json
[
  { "key": "delete", "text": "删除" },
  { "key": "export", "text": "导出" }
]
```

相关事件：

- `update:selectionMode`
- `update:selectedItems`
- `selection-enter`
- `selection-exit`
- `selection-change`
- `select-all`
- `batch-action`

## 标签颜色

通过 `tagColorMap` 传入映射，例如：

```json
{
  "已完成": "success",
  "待处理": "warning",
  "已取消": "danger",
  "新品": "violet"
}
```
