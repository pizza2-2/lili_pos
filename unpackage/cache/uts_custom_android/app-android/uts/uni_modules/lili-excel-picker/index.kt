@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME", "UNUSED_ANONYMOUS_PARAMETER", "SENSELESS_COMPARISON", "NAME_SHADOWING", "UNNECESSARY_NOT_NULL_ASSERTION")
package uts.sdk.modules.liliExcelPicker
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import io.dcloud.uniapp.*
import io.dcloud.uniapp.extapi.*
import io.dcloud.uniapp.framework.*
import io.dcloud.uniapp.runtime.*
import io.dcloud.uniapp.vue.*
import io.dcloud.uniapp.vue.shared.*
import io.dcloud.unicloud.*
import io.dcloud.uts.*
import io.dcloud.uts.Map
import io.dcloud.uts.Set
import io.dcloud.uts.UTSAndroid
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.util.zip.ZipFile
import kotlin.properties.Delegates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
open class ExcelPickerResult (
    @JsonNotNull
    open var success: Boolean = false,
    @JsonNotNull
    open var code: String,
    @JsonNotNull
    open var message: String,
    @JsonNotNull
    open var filePath: String,
    @JsonNotNull
    open var fileName: String,
    @JsonNotNull
    open var uri: String,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ExcelPickerResult", "uni_modules/lili-excel-picker/utssdk/interface.uts", 1, 13)
    }
}
typealias ExcelPickerCallback = (result: ExcelPickerResult) -> Unit
open class ExcelParseCell (
    @JsonNotNull
    open var column: String,
    @JsonNotNull
    open var value: String,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ExcelParseCell", "uni_modules/lili-excel-picker/utssdk/interface.uts", 11, 13)
    }
}
open class ExcelParseRow (
    @JsonNotNull
    open var row_number: Number,
    @JsonNotNull
    open var cells: UTSArray<ExcelParseCell>,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ExcelParseRow", "uni_modules/lili-excel-picker/utssdk/interface.uts", 15, 13)
    }
}
open class ExcelParseResult (
    @JsonNotNull
    open var success: Boolean = false,
    @JsonNotNull
    open var code: String,
    @JsonNotNull
    open var message: String,
    @JsonNotNull
    open var filePath: String,
    @JsonNotNull
    open var max_row: Number,
    @JsonNotNull
    open var max_column: Number,
    @JsonNotNull
    open var rows: UTSArray<ExcelParseRow>,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ExcelParseResult", "uni_modules/lili-excel-picker/utssdk/interface.uts", 19, 13)
    }
}
var currentCallback: ExcelPickerCallback? = null
fun setExcelPickerCallback(callback: ExcelPickerCallback?): Unit {
    currentCallback = callback
}
fun dispatchExcelPickerResult(result: ExcelPickerResult): Unit {
    val callback = currentCallback
    currentCallback = null
    if (callback != null) {
        callback!!(result)
    }
}
val REQUEST_PICK_EXCEL: Int = 19021
fun makeResult(success: Boolean, code: String, message: String, filePath: String = "", fileName: String = "", uri: String = ""): ExcelPickerResult {
    return ExcelPickerResult(success = success, code = code, message = message, filePath = filePath, fileName = fileName, uri = uri)
}
fun sanitizeFileName(name: String): String {
    var nextName = name.trim()
    if (nextName == "") {
        nextName = "purchase-import-" + Date().getTime().toString(10) + ".xlsx"
    }
    nextName = nextName.replace(UTSRegExp("[\\\\/:*?\"<>|]", "g"), "_")
    if (!isSupportedSpreadsheetName(nextName)) {
        nextName = nextName + ".xlsx"
    }
    return nextName
}
fun isSupportedSpreadsheetName(name: String): Boolean {
    val lower = name.toLowerCase()
    return lower.endsWith(".xlsx") || lower.endsWith(".xls") || lower.endsWith(".csv") || lower.endsWith(".tsv") || lower.endsWith(".txt")
}
open class ExcelPickerActivity : Activity, IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ExcelPickerActivity", "uni_modules/lili-excel-picker/utssdk/app-android/ExcelPickerActivity.uts", 38, 14)
    }
    constructor() : super() {}
    override fun onCreate(savedInstanceState: Bundle?): Unit {
        super.onCreate(savedInstanceState)
        console.log("lili-excel-picker: activity onCreate", " at uni_modules/lili-excel-picker/utssdk/app-android/ExcelPickerActivity.uts:50")
        this.openPicker()
    }
    private fun openPicker(): Unit {
        try {
            console.log("lili-excel-picker: open ACTION_OPEN_DOCUMENT", " at uni_modules/lili-excel-picker/utssdk/app-android/ExcelPickerActivity.uts:56")
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.setType("*/*")
            this.startActivityForResult(intent, REQUEST_PICK_EXCEL)
        }
         catch (error: Throwable) {
            console.log("lili-excel-picker: ACTION_OPEN_DOCUMENT failed", error, " at uni_modules/lili-excel-picker/utssdk/app-android/ExcelPickerActivity.uts:62")
            try {
                console.log("lili-excel-picker: open ACTION_GET_CONTENT", " at uni_modules/lili-excel-picker/utssdk/app-android/ExcelPickerActivity.uts:64")
                val fallbackIntent = Intent(Intent.ACTION_GET_CONTENT)
                fallbackIntent.addCategory(Intent.CATEGORY_OPENABLE)
                fallbackIntent.setType("*/*")
                this.startActivityForResult(fallbackIntent, REQUEST_PICK_EXCEL)
            }
             catch (fallbackError: Throwable) {
                console.log("lili-excel-picker: ACTION_GET_CONTENT failed", fallbackError, " at uni_modules/lili-excel-picker/utssdk/app-android/ExcelPickerActivity.uts:70")
                dispatchExcelPickerResult(makeResult(false, "OPEN_FAILED", "打开系统文件选择器失败"))
                this.finish()
            }
        }
    }
    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Unit {
        super.onActivityResult(requestCode, resultCode, data)
        console.log("lili-excel-picker: onActivityResult", requestCode, resultCode, " at uni_modules/lili-excel-picker/utssdk/app-android/ExcelPickerActivity.uts:80")
        if (requestCode != REQUEST_PICK_EXCEL) {
            this.finish()
            return
        }
        if (resultCode != Activity.RESULT_OK || data == null || data!!.getData() == null) {
            dispatchExcelPickerResult(makeResult(false, "CANCELLED", "已取消选择"))
            this.finish()
            return
        }
        val uri = data!!.getData()!!
        val uriText = uri.toString()
        val displayName = this.readDisplayName(uri)
        if (!isSupportedSpreadsheetName(displayName)) {
            console.log("lili-excel-picker: unsupported file", displayName, " at uni_modules/lili-excel-picker/utssdk/app-android/ExcelPickerActivity.uts:94")
            dispatchExcelPickerResult(makeResult(false, "UNSUPPORTED_FILE", "请选择 .xlsx、.xls、.csv 或 .tsv 文件", "", displayName, uriText))
            this.finish()
            return
        }
        val copiedPath = this.copyUriToCache(uri, displayName)
        if (copiedPath == "") {
            console.log("lili-excel-picker: copy failed", displayName, " at uni_modules/lili-excel-picker/utssdk/app-android/ExcelPickerActivity.uts:101")
            dispatchExcelPickerResult(makeResult(false, "COPY_FAILED", "复制Excel文件失败", "", displayName, uriText))
            this.finish()
            return
        }
        console.log("lili-excel-picker: pick success", copiedPath, " at uni_modules/lili-excel-picker/utssdk/app-android/ExcelPickerActivity.uts:106")
        dispatchExcelPickerResult(makeResult(true, "OK", "已选择Excel文件", copiedPath, displayName, uriText))
        this.finish()
    }
    private fun readDisplayName(uri: Uri): String {
        var result = ""
        var cursor: Cursor? = null
        try {
            cursor = this.getContentResolver().query(uri, null, null, null, null)
            if (cursor != null && cursor!!.moveToFirst()) {
                val index = cursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (index >= 0) {
                    val nameValue = cursor!!.getString(index)
                    if (nameValue != null) {
                        result = nameValue!!
                    }
                }
            }
        }
         catch (error: Throwable) {
            result = ""
        }
         finally {
            if (cursor != null) {
                cursor!!.close()
            }
        }
        if (result == "") {
            result = "purchase-import-" + Date().getTime().toString(10) + ".xlsx"
        }
        return sanitizeFileName(result)
    }
    private fun copyUriToCache(uri: Uri, displayName: String): String {
        var input: InputStream? = null
        var output: FileOutputStream? = null
        try {
            input = this.getContentResolver().openInputStream(uri)
            if (input == null) {
                return ""
            }
            val dir = File(this.getCacheDir(), "excel-picker")
            if (!dir.exists()) {
                dir.mkdirs()
            }
            val targetFile = File(dir, Date().getTime().toString(10) + "-" + sanitizeFileName(displayName))
            output = FileOutputStream(targetFile)
            val buffer = ByteArray(8192)
            var length = input!!.read(buffer)
            while(length > 0){
                output!!.write(buffer, 0, length)
                length = input!!.read(buffer)
            }
            output!!.flush()
            return targetFile.getAbsolutePath()
        }
         catch (error: Throwable) {
            return ""
        }
         finally {
            if (output != null) {
                try {
                    output!!.close()
                }
                 catch (closeOutputError: Throwable) {}
            }
            if (input != null) {
                try {
                    input!!.close()
                }
                 catch (closeInputError: Throwable) {}
            }
        }
    }
}
val REQUEST_PICK_EXCEL__1: Int = 19021
var excelActivityResultListener: ((requestCode: Int, resultCode: Int, data: Intent?) -> Unit)? = null
fun makeResult__1(success: Boolean, code: String, message: String, filePath: String = "", fileName: String = "", uri: String = ""): ExcelPickerResult {
    return ExcelPickerResult(success = success, code = code, message = message, filePath = filePath, fileName = fileName, uri = uri)
}
fun sanitizeFileName__1(name: String): String {
    var nextName = name.trim()
    if (nextName == "") {
        nextName = "purchase-import-" + Date().getTime().toString(10) + ".xlsx"
    }
    nextName = nextName.replace(UTSRegExp("[\\\\/:*?\"<>|]", "g"), "_")
    if (!isSupportedSpreadsheetName__1(nextName)) {
        nextName = nextName + ".xlsx"
    }
    return nextName
}
fun isSupportedSpreadsheetName__1(name: String): Boolean {
    val lower = name.toLowerCase()
    return lower.endsWith(".xlsx") || lower.endsWith(".xls") || lower.endsWith(".csv") || lower.endsWith(".tsv") || lower.endsWith(".txt")
}
fun readDisplayName(activity: Activity, uri: Uri): String {
    var result = ""
    var cursor: Cursor? = null
    try {
        cursor = activity.getContentResolver().query(uri, null, null, null, null)
        if (cursor != null && cursor!!.moveToFirst()) {
            val index = cursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (index >= 0) {
                val nameValue = cursor!!.getString(index)
                if (nameValue != null) {
                    result = nameValue!!
                }
            }
        }
    }
     catch (error: Throwable) {
        result = ""
    }
     finally {
        if (cursor != null) {
            cursor!!.close()
        }
    }
    if (result == "") {
        result = "purchase-import-" + Date().getTime().toString(10) + ".xlsx"
    }
    return sanitizeFileName__1(result)
}
fun copyUriToCache(activity: Activity, uri: Uri, displayName: String): String {
    var input: InputStream? = null
    var output: FileOutputStream? = null
    try {
        input = activity.getContentResolver().openInputStream(uri)
        if (input == null) {
            return ""
        }
        val dir = File(activity.getCacheDir(), "excel-picker")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val targetFile = File(dir, Date().getTime().toString(10) + "-" + sanitizeFileName__1(displayName))
        output = FileOutputStream(targetFile)
        val buffer = ByteArray(8192)
        var length = input!!.read(buffer)
        while(length > 0){
            output!!.write(buffer, 0, length)
            length = input!!.read(buffer)
        }
        output!!.flush()
        return targetFile.getAbsolutePath()
    }
     catch (error: Throwable) {
        return ""
    }
     finally {
        if (output != null) {
            try {
                output!!.close()
            }
             catch (closeOutputError: Throwable) {}
        }
        if (input != null) {
            try {
                input!!.close()
            }
             catch (closeInputError: Throwable) {}
        }
    }
}
fun clearActivityResultListener() {
    if (excelActivityResultListener != null) {
        UTSAndroid.offAppActivityResult(excelActivityResultListener!!)
        excelActivityResultListener = null
    }
}
fun dispatchPickerFromResult(activity: Activity, resultCode: Int, data: Intent?): Unit {
    clearActivityResultListener()
    if (resultCode != Activity.RESULT_OK || data == null || data!!.getData() == null) {
        dispatchExcelPickerResult(makeResult__1(false, "CANCELLED", "已取消选择"))
        return
    }
    val uri = data!!.getData()!!
    val uriText = uri.toString()
    val displayName = readDisplayName(activity, uri)
    if (!isSupportedSpreadsheetName__1(displayName)) {
        dispatchExcelPickerResult(makeResult__1(false, "UNSUPPORTED_FILE", "请选择 .xlsx、.xls、.csv 或 .tsv 文件", "", displayName, uriText))
        return
    }
    val copiedPath = copyUriToCache(activity, uri, displayName)
    if (copiedPath == "") {
        dispatchExcelPickerResult(makeResult__1(false, "COPY_FAILED", "复制Excel文件失败", "", displayName, uriText))
        return
    }
    dispatchExcelPickerResult(makeResult__1(true, "OK", "已选择Excel文件", copiedPath, displayName, uriText))
}
fun openSystemPicker(activity: Activity, action: String): Unit {
    val intent = Intent(action)
    intent.addCategory(Intent.CATEGORY_OPENABLE)
    intent.setType("*/*")
    activity.startActivityForResult(intent, REQUEST_PICK_EXCEL__1)
}
fun pickExcelFile(callback: ExcelPickerCallback): Unit {
    console.log("lili-excel-picker: pickExcelFile called", " at uni_modules/lili-excel-picker/utssdk/app-android/index.uts:148")
    val activity = UTSAndroid.getUniActivity() as Activity
    if (activity == null) {
        console.log("lili-excel-picker: no activity", " at uni_modules/lili-excel-picker/utssdk/app-android/index.uts:151")
        callback(makeResult__1(false, "NO_ACTIVITY", "当前页面无法打开文件选择器"))
        return
    }
    try {
        clearActivityResultListener()
        setExcelPickerCallback(callback)
        excelActivityResultListener = fun(requestCode: Int, resultCode: Int, data: Intent?){
            console.log("lili-excel-picker: onAppActivityResult", requestCode, resultCode, " at uni_modules/lili-excel-picker/utssdk/app-android/index.uts:159")
            if (requestCode != REQUEST_PICK_EXCEL__1) {
                return
            }
            dispatchPickerFromResult(activity, resultCode, data)
        }
        UTSAndroid.onAppActivityResult(excelActivityResultListener!!)
        console.log("lili-excel-picker: open ACTION_OPEN_DOCUMENT", " at uni_modules/lili-excel-picker/utssdk/app-android/index.uts:164")
        openSystemPicker(activity, Intent.ACTION_OPEN_DOCUMENT)
    }
     catch (error: Throwable) {
        console.log("lili-excel-picker: ACTION_OPEN_DOCUMENT failed", error, " at uni_modules/lili-excel-picker/utssdk/app-android/index.uts:167")
        try {
            console.log("lili-excel-picker: open ACTION_GET_CONTENT", " at uni_modules/lili-excel-picker/utssdk/app-android/index.uts:169")
            openSystemPicker(activity, Intent.ACTION_GET_CONTENT)
        }
         catch (fallbackError: Throwable) {
            console.log("lili-excel-picker: ACTION_GET_CONTENT failed", fallbackError, " at uni_modules/lili-excel-picker/utssdk/app-android/index.uts:172")
            clearActivityResultListener()
            setExcelPickerCallback(null)
            dispatchExcelPickerResult(makeResult__1(false, "OPEN_FAILED", "打开文件选择器失败"))
        }
    }
}
fun makeParseResult(success: Boolean, code: String, message: String, filePath: String, rows: UTSArray<ExcelParseRow> = _uA<ExcelParseRow>(), maxRow: Number = 0, maxColumn: Number = 0): ExcelParseResult {
    return ExcelParseResult(success = success, code = code, message = message, filePath = filePath, max_row = maxRow, max_column = maxColumn, rows = rows)
}
fun readZipText(zip: ZipFile, name: String): String {
    val entry = zip.getEntry(name)
    if (entry == null) {
        return ""
    }
    val input = zip.getInputStream(entry)
    val reader = BufferedReader(InputStreamReader(input, "UTF-8"))
    var text = ""
    var line = reader.readLine()
    while(line != null){
        text = text + line
        line = reader.readLine()
    }
    reader.close()
    input.close()
    return text
}
fun xmlUnescape(value: String): String {
    var result = value.replace(UTSRegExp("&lt;", "g"), "<")
    result = result.replace(UTSRegExp("&gt;", "g"), ">")
    result = result.replace(UTSRegExp("&amp;", "g"), "&")
    result = result.replace(UTSRegExp("&quot;", "g"), "\"")
    result = result.replace(UTSRegExp("&apos;", "g"), "'")
    return result
}
fun attrValue(tag: String, name: String): String {
    var needle = " " + name + "=\""
    var start = tag.indexOf(needle)
    if (start >= 0) {
        start = start + needle.length
        val end = tag.indexOf("\"", start)
        return if (end > start) {
            tag.substring(start, end)
        } else {
            ""
        }
    }
    needle = " " + name + "='"
    start = tag.indexOf(needle)
    if (start >= 0) {
        start = start + needle.length
        val end = tag.indexOf("'", start)
        return if (end > start) {
            tag.substring(start, end)
        } else {
            ""
        }
    }
    return ""
}
fun textBetween(source: String, startToken: String, endToken: String): String {
    val start = source.indexOf(startToken)
    if (start < 0) {
        return ""
    }
    val valueStart = start + startToken.length
    val end = source.indexOf(endToken, valueStart)
    if (end < valueStart) {
        return ""
    }
    return source.substring(valueStart, end)
}
fun collectTextTags(source: String): String {
    var result = ""
    var cursor: Number = 0
    while(true){
        val tagStart = source.indexOf("<t", cursor)
        if (tagStart < 0) {
            break
        }
        val valueStart = source.indexOf(">", tagStart)
        if (valueStart < 0) {
            break
        }
        val valueEnd = source.indexOf("</t>", valueStart + 1)
        if (valueEnd < 0) {
            break
        }
        result = result + xmlUnescape(source.substring(valueStart + 1, valueEnd))
        cursor = valueEnd + 4
    }
    return result
}
fun parseSharedStrings(xml: String): UTSArray<String> {
    val values: UTSArray<String> = _uA()
    var cursor: Number = 0
    while(true){
        val start = xml.indexOf("<si", cursor)
        if (start < 0) {
            break
        }
        val startEnd = xml.indexOf(">", start)
        if (startEnd < 0) {
            break
        }
        val end = xml.indexOf("</si>", startEnd + 1)
        if (end < 0) {
            break
        }
        val block = xml.substring(startEnd + 1, end)
        values.push(collectTextTags(block))
        cursor = end + 5
    }
    return values
}
fun columnFromRef(ref: String): String {
    val letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    var result = ""
    run {
        var index: Number = 0
        while(index < ref.length){
            val ch = ref.substring(index, index + 1).toUpperCase()
            if (letters.indexOf(ch) >= 0) {
                result = result + ch
            } else {
                break
            }
            index += 1
        }
    }
    return result
}
fun rowFromRef(ref: String, fallback: Number): Number {
    var digits = ""
    run {
        var index: Number = 0
        while(index < ref.length){
            val ch = ref.substring(index, index + 1)
            if ("0123456789".indexOf(ch) >= 0) {
                digits = digits + ch
            }
            index += 1
        }
    }
    val value = parseInt(digits)
    return if (isNaN(value)) {
        fallback
    } else {
        value
    }
}
fun columnIndex(column: String): Number {
    val letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    var result: Number = 0
    run {
        var index: Number = 0
        while(index < column.length){
            val ch = column.substring(index, index + 1).toUpperCase()
            val offset = letters.indexOf(ch)
            if (offset < 0) {
                return result
            }
            result = result * 26 + offset + 1
            index += 1
        }
    }
    return result
}
fun parseDimensionMax(xml: String): UTSJSONObject {
    var maxRow: Number = 0
    var maxColumn: Number = 0
    val dimensionStart = xml.indexOf("<dimension")
    if (dimensionStart >= 0) {
        val dimensionEnd = xml.indexOf(">", dimensionStart)
        if (dimensionEnd > dimensionStart) {
            val ref = attrValue(xml.substring(dimensionStart, dimensionEnd + 1), "ref")
            val parts = ref.split(":")
            val lastRef = if (parts.length > 1) {
                parts[parts.length - 1]
            } else {
                ref
            }
            maxRow = rowFromRef(lastRef, 0)
            maxColumn = columnIndex(columnFromRef(lastRef))
        }
    }
    return _uO("max_row" to maxRow, "max_column" to maxColumn)
}
fun parseWorksheetRows(xml: String, sharedStrings: UTSArray<String>): UTSArray<ExcelParseRow> {
    val rows: UTSArray<ExcelParseRow> = _uA()
    var cursor: Number = 0
    while(true){
        val rowStart = xml.indexOf("<row", cursor)
        if (rowStart < 0) {
            break
        }
        val rowOpenEnd = xml.indexOf(">", rowStart)
        if (rowOpenEnd < 0) {
            break
        }
        val rowEnd = xml.indexOf("</row>", rowOpenEnd + 1)
        if (rowEnd < 0) {
            break
        }
        val rowTag = xml.substring(rowStart, rowOpenEnd + 1)
        val rowBlock = xml.substring(rowOpenEnd + 1, rowEnd)
        val rowNumber = rowFromRef(attrValue(rowTag, "r"), rows.length + 1)
        val cells: UTSArray<ExcelParseCell> = _uA()
        var cellCursor: Number = 0
        while(true){
            val cellStart = rowBlock.indexOf("<c", cellCursor)
            if (cellStart < 0) {
                break
            }
            val cellOpenEnd = rowBlock.indexOf(">", cellStart)
            if (cellOpenEnd < 0) {
                break
            }
            val cellEnd = rowBlock.indexOf("</c>", cellOpenEnd + 1)
            if (cellEnd < 0) {
                cellCursor = cellOpenEnd + 1
                continue
            }
            val cellTag = rowBlock.substring(cellStart, cellOpenEnd + 1)
            val cellBlock = rowBlock.substring(cellOpenEnd + 1, cellEnd)
            val ref = attrValue(cellTag, "r")
            val column = columnFromRef(ref)
            val valueType = attrValue(cellTag, "t")
            var value = ""
            if (valueType == "inlineStr") {
                value = collectTextTags(cellBlock)
            } else {
                value = xmlUnescape(textBetween(cellBlock, "<v>", "</v>"))
                if (valueType == "s") {
                    val sharedIndex = parseInt(value)
                    if (!isNaN(sharedIndex) && sharedIndex >= 0 && sharedIndex < sharedStrings.length) {
                        value = sharedStrings[sharedIndex]
                    }
                }
            }
            if (column != "") {
                cells.push(ExcelParseCell(column = column, value = value))
            }
            cellCursor = cellEnd + 4
        }
        rows.push(ExcelParseRow(row_number = rowNumber, cells = cells))
        cursor = rowEnd + 6
    }
    return rows
}
fun readLocalTextFile(filePath: String): String {
    var input: FileInputStream? = null
    var reader: BufferedReader? = null
    try {
        input = FileInputStream(File(filePath))
        reader = BufferedReader(InputStreamReader(input!!, "UTF-8"))
        var text = ""
        var line = reader!!.readLine()
        while(line != null){
            text = text + line + "\n"
            line = reader!!.readLine()
        }
        return text
    }
     catch (error: Throwable) {
        return ""
    }
     finally {
        if (reader != null) {
            try {
                reader!!.close()
            }
             catch (closeReaderError: Throwable) {}
        }
        if (input != null) {
            try {
                input!!.close()
            }
             catch (closeInputError: Throwable) {}
        }
    }
}
fun isBinaryXlsFile(filePath: String): Boolean {
    var input: FileInputStream? = null
    try {
        input = FileInputStream(File(filePath))
        val buffer = ByteArray(4)
        val length = input!!.read(buffer)
        if (length < 4) {
            return false
        }
        return (buffer[0].toInt() and 255) == 208 && (buffer[1].toInt() and 255) == 207 && (buffer[2].toInt() and 255) == 17 && (buffer[3].toInt() and 255) == 224
    }
     catch (error: Throwable) {
        return false
    }
     finally {
        if (input != null) {
            try {
                input!!.close()
            }
             catch (closeInputError: Throwable) {}
        }
    }
}
fun parseDelimitedLine(line: String, delimiter: String): UTSArray<String> {
    val values: UTSArray<String> = _uA()
    var current = ""
    var inQuotes = false
    var index: Number = 0
    while(index < line.length){
        val charText = line.substring(index, index + 1)
        if (charText == "\"") {
            if (inQuotes && index + 1 < line.length && line.substring(index + 1, index + 2) == "\"") {
                current = current + "\""
                index = index + 2
                continue
            }
            inQuotes = !inQuotes
        } else if (charText == delimiter && !inQuotes) {
            values.push(current.trim())
            current = ""
        } else {
            current = current + charText
        }
        index = index + 1
    }
    values.push(current.trim())
    return values
}
fun detectDelimiter(text: String, fallback: String): String {
    val firstLines = text.split("\n")
    var commaCount: Number = 0
    var semicolonCount: Number = 0
    var tabCount: Number = 0
    run {
        var lineIndex: Number = 0
        while(lineIndex < firstLines.length && lineIndex < 5){
            val line = firstLines[lineIndex]
            run {
                var index: Number = 0
                while(index < line.length){
                    val charText = line.substring(index, index + 1)
                    if (charText == ",") {
                        commaCount = commaCount + 1
                    }
                    if (charText == ";") {
                        semicolonCount = semicolonCount + 1
                    }
                    if (charText == "\t") {
                        tabCount = tabCount + 1
                    }
                    index += 1
                }
            }
            lineIndex += 1
        }
    }
    if (tabCount >= commaCount && tabCount >= semicolonCount && tabCount > 0) {
        return "\t"
    }
    if (semicolonCount >= commaCount && semicolonCount > 0) {
        return ";"
    }
    if (commaCount > 0) {
        return ","
    }
    return fallback
}
fun columnLetterFromIndex(index: Number): String {
    var value = index
    var result = ""
    val letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    while(value > 0){
        val remainder = (value - 1) % 26
        result = letters.substring(remainder, remainder + 1) + result
        value = Math.floor((value - 1) / 26)
    }
    return result
}
fun rowsFromDelimitedText(text: String, delimiter: String): UTSArray<ExcelParseRow> {
    val rawLines = text.split("\n")
    val rows: UTSArray<ExcelParseRow> = _uA()
    run {
        var rowIndex: Number = 0
        while(rowIndex < rawLines.length){
            val line = rawLines[rowIndex].split("\r").join("")
            if (line.trim() == "") {
                rowIndex += 1
                continue
            }
            val values = parseDelimitedLine(line, delimiter)
            val cells: UTSArray<ExcelParseCell> = _uA()
            run {
                var cellIndex: Number = 0
                while(cellIndex < values.length){
                    cells.push(ExcelParseCell(column = columnLetterFromIndex(cellIndex + 1), value = values[cellIndex]))
                    cellIndex += 1
                }
            }
            rows.push(ExcelParseRow(row_number = rows.length + 1, cells = cells))
            rowIndex += 1
        }
    }
    return rows
}
fun stripHtmlTags(value: String): String {
    var result = ""
    var inTag = false
    run {
        var index: Number = 0
        while(index < value.length){
            val charText = value.substring(index, index + 1)
            if (charText == "<") {
                inTag = true
            } else if (charText == ">") {
                inTag = false
            } else if (!inTag) {
                result = result + charText
            }
            index += 1
        }
    }
    return xmlUnescape(result).trim()
}
fun rowsFromHtmlTable(text: String): UTSArray<ExcelParseRow> {
    val rows: UTSArray<ExcelParseRow> = _uA()
    var cursor: Number = 0
    val lower = text.toLowerCase()
    while(true){
        val rowStart = lower.indexOf("<tr", cursor)
        if (rowStart < 0) {
            break
        }
        val rowOpenEnd = lower.indexOf(">", rowStart)
        if (rowOpenEnd < 0) {
            break
        }
        val rowEnd = lower.indexOf("</tr>", rowOpenEnd + 1)
        if (rowEnd < 0) {
            break
        }
        val rowBlock = text.substring(rowOpenEnd + 1, rowEnd)
        val rowBlockLower = rowBlock.toLowerCase()
        val cells: UTSArray<ExcelParseCell> = _uA()
        var cellCursor: Number = 0
        while(true){
            var cellStart = rowBlockLower.indexOf("<td", cellCursor)
            var tagName = "td"
            if (cellStart < 0) {
                cellStart = rowBlockLower.indexOf("<th", cellCursor)
                tagName = "th"
            }
            if (cellStart < 0) {
                break
            }
            val cellOpenEnd = rowBlockLower.indexOf(">", cellStart)
            if (cellOpenEnd < 0) {
                break
            }
            val cellEnd = rowBlockLower.indexOf("</" + tagName + ">", cellOpenEnd + 1)
            if (cellEnd < 0) {
                break
            }
            val value = stripHtmlTags(rowBlock.substring(cellOpenEnd + 1, cellEnd))
            cells.push(ExcelParseCell(column = columnLetterFromIndex(cells.length + 1), value = value))
            cellCursor = cellEnd + 5
        }
        if (cells.length > 0) {
            rows.push(ExcelParseRow(row_number = rows.length + 1, cells = cells))
        }
        cursor = rowEnd + 5
    }
    return rows
}
fun resultFromRows(filePath: String, rows: UTSArray<ExcelParseRow>, message: String): ExcelParseResult {
    var maxColumn: Number = 0
    run {
        var rowIndex: Number = 0
        while(rowIndex < rows.length){
            val row = rows[rowIndex]
            run {
                var cellIndex: Number = 0
                while(cellIndex < row.cells.length){
                    val nextColumn = columnIndex(row.cells[cellIndex].column)
                    if (nextColumn > maxColumn) {
                        maxColumn = nextColumn
                    }
                    cellIndex += 1
                }
            }
            rowIndex += 1
        }
    }
    return makeParseResult(true, "OK", message, filePath, rows, rows.length, maxColumn)
}
fun parseTextSpreadsheetFile(filePath: String, fallbackDelimiter: String): ExcelParseResult {
    val text = readLocalTextFile(filePath)
    if (text == "") {
        return makeParseResult(false, "TEXT_READ_FAILED", "文本表格读取失败", filePath)
    }
    val lower = text.toLowerCase()
    if (lower.indexOf("<table") >= 0 && lower.indexOf("<tr") >= 0) {
        val htmlRows = rowsFromHtmlTable(text)
        if (htmlRows.length > 0) {
            return resultFromRows(filePath, htmlRows, "HTML表格解析完成")
        }
    }
    val delimiter = detectDelimiter(text, fallbackDelimiter)
    val rows = rowsFromDelimitedText(text, delimiter)
    if (rows.length == 0) {
        return makeParseResult(false, "NO_ROWS", "没有读取到表格行", filePath)
    }
    return resultFromRows(filePath, rows, "文本表格解析完成")
}
fun parseExcelFile(filePath: String): ExcelParseResult {
    if (filePath == "") {
        return makeParseResult(false, "EMPTY_PATH", "未选择Excel文件", filePath)
    }
    val lowerPath = filePath.toLowerCase()
    if (lowerPath.endsWith(".csv")) {
        return parseTextSpreadsheetFile(filePath, ",")
    }
    if (lowerPath.endsWith(".tsv") || lowerPath.endsWith(".txt")) {
        return parseTextSpreadsheetFile(filePath, "\t")
    }
    if (lowerPath.endsWith(".xls")) {
        if (isBinaryXlsFile(filePath)) {
            return makeParseResult(false, "UNSUPPORTED_BINARY_XLS", "这是旧版二进制 .xls，手机端需要另存为 .xlsx、.csv 或使用原生Excel库解析", filePath)
        }
        return parseTextSpreadsheetFile(filePath, "\t")
    }
    var zip: ZipFile? = null
    try {
        zip = ZipFile(filePath)
        val sharedXml = readZipText(zip!!, "xl/sharedStrings.xml")
        val sheetXml = readZipText(zip!!, "xl/worksheets/sheet1.xml")
        if (sheetXml == "") {
            return makeParseResult(false, "NO_SHEET", "Excel工作表读取失败", filePath)
        }
        val sharedStrings = parseSharedStrings(sharedXml)
        val rows = parseWorksheetRows(sheetXml, sharedStrings)
        val dimension = parseDimensionMax(sheetXml)
        var maxRow = parseInt("" + dimension["max_row"])
        var maxColumn = parseInt("" + dimension["max_column"])
        if (isNaN(maxRow)) {
            maxRow = 0
        }
        if (isNaN(maxColumn)) {
            maxColumn = 0
        }
        run {
            var rowIndex: Number = 0
            while(rowIndex < rows.length){
                val row = rows[rowIndex]
                if (row.row_number > maxRow) {
                    maxRow = row.row_number
                }
                run {
                    var cellIndex: Number = 0
                    while(cellIndex < row.cells.length){
                        val nextColumn = columnIndex(row.cells[cellIndex].column)
                        if (nextColumn > maxColumn) {
                            maxColumn = nextColumn
                        }
                        cellIndex += 1
                    }
                }
                rowIndex += 1
            }
        }
        return makeParseResult(true, "OK", "Excel解析完成", filePath, rows, maxRow, maxColumn)
    }
     catch (error: Throwable) {
        return makeParseResult(false, "PARSE_FAILED", "Excel解析失败", filePath)
    }
     finally {
        if (zip != null) {
            try {
                zip!!.close()
            }
             catch (closeError: Throwable) {}
        }
    }
}
