---
paths: **/*.uvue, **/*.vue
---
# uvue rules
## vue support
- 仅使用vue3语法，避免使用vue2。
- 新页面尽量使用组合式API。
- 组件尽量使用easycom规范。
- 非easycom的自定义vue组件，调用组件方法时需使用组件实例的`$callMethod`方式调用。
- 不使用 pinia、vuex、i18n 等uni-app x不支持的vue插件。
- 使用vue语法时需注意uni-app x官网的平台和版本兼容性，平台特殊代码需包裹在条件编译中。

## component
- 组件可使用uni-app x内置组件，以及项目下的自定义组件。通过mcp工具查询项目下可用的easycom插件。
- 项目可使用vuejs组件规范，对应的文件扩展名为uvue。
- 符合easycom规范的组件无需import和注册，可直接在template中使用。
- 使用内置组件时需注意uni-app x官网的平台和版本兼容性，平台特殊代码需包裹在条件编译中。
- UTS 不支持函数/变量声明提升，模板事件直接绑定的方法（如 `@click`）必须先声明后再被调用，避免出现
  `找不到名称`/`UTS110111150` 类似编译错误；递归调用时使用函数变量先声明再赋值。
