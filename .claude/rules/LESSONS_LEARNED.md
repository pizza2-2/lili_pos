# 经验教训（运行验证后记录）

## UTS / Android 数据层

### Map 数字键的装箱相等性陷阱（2026-07-06，分类树重写时踩坑）
- **现象**：JSON 解析出 1568 个节点全部成功，但 `Map<number, T>.get(0)` 永远查不到根分组，页面空列表。
- **根因**：UTS 的 `==` 运算符对 number 是值语义，但 **Map/Set/Array.includes 等集合 API 在 Android 端走平台层 equals**。`JSON` 类型化直读（getNumber）得到的数字底层是 Double 装箱，字面量/parseInt 得到 Int 装箱，Kotlin 中 `Double(0.0).equals(Int(0)) == false`。
- **规则**：凡是用数字做 Map 键或跨来源做集合查找，一律 `.toString()` 转字符串键（UTS number.toString() 遵循 JS 语义，`0.0 → "0"` 稳定）。同源数字（同一解析路径产出）之间的集合操作可以直接用。

### 大响应解析必须走类型化直读（同日）
- 通用防御式解析（JSON.stringify → JSON.parseObject 往返 + 逐字段 `'' + value` / parseInt）对小响应无感，但对 1500+ 条目的大响应是**主线程数百毫秒冻结**（request 层已 parse 一次，再 stringify/parse 就是 3-4 遍全量序列化）。
- 大响应用 `UTSJSONObject.getArray/getNumber/getString/getBoolean` 单遍直读，外面包 try/catch 落回防御式解析兜底。

## 图片上传

### chooseImage 的 sizeType 传两个值等于不压缩（2026-07-06，排查上传慢时确认）
- `sizeType: ['compressed', 'original']` 在 App 端实际按原图返回，相机原图 3-8MB 直传是上传慢的根因。
- 统一方案：上传链路入口用 `pkg/util/imageCompress.uts` 的 `compressImageForUpload()`（getImageInfo 判尺寸 → 长边>1600 等比缩边 + 质量80；非图片扩展名跳过；任何失败原样回退）。已接入 lili-upload 组件与 media.uts 单传/批量。
- 真机验证：1080×1440 相册图走"仅质量压缩"分支 24ms；`[imgCompress]` 日志保留用于排障。

## UTS / Android 渲染层

### touchmove 驱动响应式 ref = 整列表每帧重渲染（同日）
- 左滑跟手若把位移写进 ref 并用 `:style` 绑定，每个 touchmove 触发所有行的绑定函数重算 + vdom diff，必掉帧。
- **正确做法**：拖拽过程用 `uni.getElementById(id).style.setProperty('transform', ...)` 直改元素（先把 transition-duration 置 0ms），只有松手后的"停靠状态"进响应式；松手时须手动把 transform 归位（状态未变化时不会触发重渲染）。

### onShow 不要无条件重拉大数据
- 每次页面返回都触发 onShow；无条件重载大响应 = 每次返回都卡一下。用 storage 刷新信号（编辑页写入、列表页消费）门控，页内自身的增删改移各自显式刷新。
