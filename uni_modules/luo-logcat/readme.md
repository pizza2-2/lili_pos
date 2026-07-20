# luo-logcat

读取本进程的 logcat 输出（仅 Android），供调试日志收集功能使用。

- `currentLogcatTime(): string` — 当前时间转为 logcat `-t` 参数格式（`MM-dd HH:mm:ss.SSS`，本地时区）。
- `dumpLogcatSince(sinceTime: string): Promise<string>` — 异步拉取自该时间以来的本进程日志；传空串拉整个系统缓冲；失败 resolve 空串。

说明：无 READ_LOGS 权限时 Android 只返回本应用的日志，因此不需要任何额外权限。
单次拉取上限 2MB。console.log 在 Android 上写入 logcat，可被本插件捕获。

该插件在 POS 与 LILI_POS 各有一份拷贝，改动请两边同步。
