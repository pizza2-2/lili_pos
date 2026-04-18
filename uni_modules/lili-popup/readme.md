# lili-popup

通用业务弹窗组件，支持：

- 基础确认/取消
- 三按钮场景，例如“保存 / 取消保存 / 退出”
- `v-model:visible` 风格控制显示
- `open(config)`、`openSaveConfirm(config)` 命令式打开
- 默认插槽自定义内容

## 基础用法

```vue
<template>
  <lili-popup
    :visible="visible"
    title="删除提示"
    content="确认删除这条数据吗？"
    cancelText="取消"
    confirmText="确认"
    @update:visible="onVisibleChange"
    @cancel="onCancel"
    @confirm="onConfirm"
    @close="onClose"
  />
</template>
```

## 保存提示

```vue
<template>
  <lili-popup
    :visible="saveVisible"
    title="是否保存"
    content="当前内容已修改，是否保存后再退出？"
    auxiliaryText="退出"
    cancelText="取消保存"
    confirmText="保存"
    @auxiliary="onExit"
    @cancel="onDiscard"
    @confirm="onSave"
  />
</template>
```

## 暴露方法

- `open(config?: UTSJSONObject)`
- `openSaveConfirm(config?: UTSJSONObject)`
- `close(action?: string)`

## Props

- `visible`: 是否显示
- `title`: 标题
- `content`: 内容文案
- `cancelText`: 取消按钮文案
- `confirmText`: 确认按钮文案
- `auxiliaryText`: 第三个按钮文案，为空时不显示
- `width`: 弹窗宽度，默认 `620rpx`
- `zIndex`: 层级，默认 `999`
- `closeOnMask`: 点击遮罩是否关闭
- `showCloseIcon`: 是否显示右上角关闭按钮
- `confirmDanger`: 确认按钮是否危险态
- `autoClose`: 点击按钮后是否自动关闭

## Events

- `update:visible`
- `open`
- `close`
- `cancel`
- `confirm`
- `auxiliary`
- `action`
