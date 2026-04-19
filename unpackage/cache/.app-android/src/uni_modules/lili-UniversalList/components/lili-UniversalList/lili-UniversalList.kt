@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME", "UNUSED_ANONYMOUS_PARAMETER", "SENSELESS_COMPARISON", "NAME_SHADOWING", "UNNECESSARY_NOT_NULL_ASSERTION")
package uni.UNI1CE1B14
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
import kotlin.properties.Delegates
import io.dcloud.uniapp.extapi.showActionSheet as uni_showActionSheet
open class GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalList : VueComponent {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    open var items: UTSArray<UTSJSONObject> by `$props`
    open var keyField: String by `$props`
    open var titleField: String by `$props`
    open var subtitleField: String by `$props`
    open var metaField: String by `$props`
    open var imageField: String by `$props`
    open var imageListField: String by `$props`
    open var showImage: Boolean by `$props`
    open var fields: UTSArray<UTSJSONObject> by `$props`
    open var tagField: String by `$props`
    open var tagColorMap: UTSJSONObject by `$props`
    open var showActions: Boolean by `$props`
    open var actions: UTSArray<UTSJSONObject> by `$props`
    open var selectionMode: Boolean by `$props`
    open var selectedItems: UTSArray<String> by `$props`
    open var loading: Boolean by `$props`
    open var loadingText: String by `$props`
    open var emptyText: String by `$props`
    open var emptyIcon: String by `$props`
    open var showChevron: Boolean by `$props`
    open var showMenu: Boolean by `$props`
    open var menuActions: UTSArray<UTSJSONObject> by `$props`
    open var showPagination: Boolean by `$props`
    open var currentPage: Number by `$props`
    open var totalPages: Number by `$props`
    open var totalCount: Number by `$props`
    open var enablePreviewSave: Boolean by `$props`
    open var enablePreviewShare: Boolean by `$props`
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalList) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!
            val _ctx = __ins.proxy as GenUniModulesLiliUniversalListComponentsLiliUniversalListLiliUniversalList
            val _cache = __ins.renderCache
            val props = __props
            fun emit(event: String, vararg do_not_transform_spread: Any?) {
                __ins.emit(event, *do_not_transform_spread)
            }
            val previewImages = ref(_uA<String>())
            val previewIndex = ref<Number>(0)
            val previewVisible = ref<Boolean>(false)
            val previewItem = ref<UTSJSONObject?>(null)
            fun gen_stringValue_fn(value: Any?): String {
                if (value == null) {
                    return ""
                }
                return "" + value
            }
            val stringValue = ::gen_stringValue_fn
            fun gen_safeCurrentPage_fn(): Number {
                if (props.currentPage <= 0) {
                    return 1
                }
                return props.currentPage
            }
            val safeCurrentPage = ::gen_safeCurrentPage_fn
            fun gen_safeTotalPages_fn(): Number {
                if (props.totalPages <= 0) {
                    return 1
                }
                return props.totalPages
            }
            val safeTotalPages = ::gen_safeTotalPages_fn
            fun gen_safeTotalCount_fn(): Number {
                if (props.totalCount < 0) {
                    return 0
                }
                return props.totalCount
            }
            val safeTotalCount = ::gen_safeTotalCount_fn
            val currentPageText = computed<String>(fun(): String {
                return "" + safeCurrentPage()
            }
            )
            val totalPagesText = computed<String>(fun(): String {
                return "" + safeTotalPages()
            }
            )
            val totalCountText = computed<String>(fun(): String {
                return "" + safeTotalCount()
            }
            )
            fun gen_objectField_fn(obj: UTSJSONObject, key: String): Any? {
                return obj[key]
            }
            val objectField = ::gen_objectField_fn
            fun gen_fieldText_fn(item: UTSJSONObject, path: String): String {
                if (path == "") {
                    return ""
                }
                var current: Any? = item
                val parts = path.split(".")
                run {
                    var i: Number = 0
                    while(i < parts.length){
                        if (current == null) {
                            return ""
                        }
                        val currentObj = current as UTSJSONObject
                        current = currentObj[parts[i]]
                        i++
                    }
                }
                return stringValue(current)
            }
            val fieldText = ::gen_fieldText_fn
            fun gen_fieldLabel_fn(field: UTSJSONObject): String {
                return stringValue(field["label"])
            }
            val fieldLabel = ::gen_fieldLabel_fn
            fun gen_fieldIcon_fn(field: UTSJSONObject): String {
                return stringValue(field["icon"])
            }
            val fieldIcon = ::gen_fieldIcon_fn
            fun gen_fieldType_fn(field: UTSJSONObject): String {
                return stringValue(field["type"])
            }
            val fieldType = ::gen_fieldType_fn
            fun gen_fieldKey_fn(field: UTSJSONObject, index: Number): String {
                val key = stringValue(field["key"])
                if (key != "") {
                    return key
                }
                return "field-" + index
            }
            val fieldKey = ::gen_fieldKey_fn
            fun gen_actionKey_fn(action: UTSJSONObject, index: Number): String {
                val key = stringValue(action["key"])
                if (key != "") {
                    return key
                }
                return "action-" + index
            }
            val actionKey = ::gen_actionKey_fn
            fun gen_actionText_fn(action: UTSJSONObject): String {
                return stringValue(action["text"])
            }
            val actionText = ::gen_actionText_fn
            fun gen_menuText_fn(action: UTSJSONObject): String {
                return stringValue(action["text"])
            }
            val menuText = ::gen_menuText_fn
            fun gen_actionIcon_fn(action: UTSJSONObject): String {
                return stringValue(action["icon"])
            }
            val actionIcon = ::gen_actionIcon_fn
            fun gen_actionTone_fn(action: UTSJSONObject): String {
                val tone = stringValue(action["tone"])
                if (tone != "") {
                    return tone
                }
                return "info"
            }
            val actionTone = ::gen_actionTone_fn
            fun gen_stringArrayContains_fn(list: UTSArray<String>, value: String): Boolean {
                run {
                    var i: Number = 0
                    while(i < list.length){
                        if (list[i] == value) {
                            return true
                        }
                        i++
                    }
                }
                return false
            }
            val stringArrayContains = ::gen_stringArrayContains_fn
            fun gen_displayField_fn(item: UTSJSONObject, field: UTSJSONObject): String {
                val raw = fieldText(item, stringValue(field["key"]))
                val type = fieldType(field)
                if (type == "price") {
                    if (raw == "") {
                        return ""
                    }
                    return "¥ " + raw
                }
                if (type == "status") {
                    return raw
                }
                return raw
            }
            val displayField = ::gen_displayField_fn
            fun gen_itemId_fn(item: UTSJSONObject): String {
                return fieldText(item, props.keyField)
            }
            val itemId = ::gen_itemId_fn
            fun gen_itemKey_fn(item: UTSJSONObject, index: Number): String {
                val key = itemId(item)
                if (key != "") {
                    return key
                }
                return "item-" + index
            }
            val itemKey = ::gen_itemKey_fn
            fun gen_isSelected_fn(item: UTSJSONObject): Boolean {
                val id = itemId(item)
                if (id == "") {
                    return false
                }
                run {
                    var i: Number = 0
                    while(i < props.selectedItems.length){
                        if (props.selectedItems[i] == id) {
                            return true
                        }
                        i++
                    }
                }
                return false
            }
            val isSelected = ::gen_isSelected_fn
            fun gen_toggleSelection_fn(item: UTSJSONObject) {
                val id = itemId(item)
                if (id == "") {
                    return
                }
                val nextSelected: UTSArray<String> = _uA()
                var removed = false
                run {
                    var i: Number = 0
                    while(i < props.selectedItems.length){
                        val current = props.selectedItems[i]
                        if (current == id) {
                            removed = true
                            i++
                            continue
                        }
                        nextSelected.push(current)
                        i++
                    }
                }
                if (!removed) {
                    nextSelected.push(id)
                }
                emit("update:selectedItems", nextSelected)
                emit("selection-change", nextSelected)
            }
            val toggleSelection = ::gen_toggleSelection_fn
            fun gen_handleItemClick_fn(item: UTSJSONObject) {
                if (props.selectionMode) {
                    toggleSelection(item)
                    return
                }
                emit("item-click", item)
            }
            val handleItemClick = ::gen_handleItemClick_fn
            fun gen_handleSubtitleClick_fn(item: UTSJSONObject) {
                emit("subtitle-click", _uO("item" to item, "field" to props.subtitleField, "value" to fieldText(item, props.subtitleField)))
            }
            val handleSubtitleClick = ::gen_handleSubtitleClick_fn
            fun gen_handleMetaClick_fn(item: UTSJSONObject) {
                emit("meta-click", _uO("item" to item, "field" to props.metaField, "value" to fieldText(item, props.metaField)))
            }
            val handleMetaClick = ::gen_handleMetaClick_fn
            fun gen_handleFieldClick_fn(item: UTSJSONObject, field: UTSJSONObject) {
                emit("field-click", _uO("item" to item, "field" to field, "key" to stringValue(field["key"]), "label" to fieldLabel(field), "value" to displayField(item, field)))
            }
            val handleFieldClick = ::gen_handleFieldClick_fn
            fun gen_imageListFromItem_fn(item: UTSJSONObject): UTSArray<String> {
                val images: UTSArray<String> = _uA()
                val mainImage = fieldText(item, props.imageField)
                if (mainImage != "") {
                    images.push(mainImage)
                }
                if (props.imageListField != "") {
                    val raw = objectField(item, props.imageListField)
                    if (raw != null) {
                        if (raw is UTSArray<*>) {
                            val list = raw as UTSArray<Any>
                            run {
                                var i: Number = 0
                                while(i < list.length){
                                    val imageUrl = stringValue(list[i])
                                    if (imageUrl != "" && !stringArrayContains(images, imageUrl)) {
                                        images.push(imageUrl)
                                    }
                                    i++
                                }
                            }
                        } else {
                            val text = stringValue(raw)
                            if (text != "") {
                                val parts = text.split(",")
                                run {
                                    var i: Number = 0
                                    while(i < parts.length){
                                        val imageUrl = parts[i].trim()
                                        if (imageUrl != "" && !stringArrayContains(images, imageUrl)) {
                                            images.push(imageUrl)
                                        }
                                        i++
                                    }
                                }
                            }
                        }
                    }
                }
                return images
            }
            val imageListFromItem = ::gen_imageListFromItem_fn
            fun gen_firstImage_fn(item: UTSJSONObject): String {
                val list = imageListFromItem(item)
                if (list.length == 0) {
                    return ""
                }
                return list[0]
            }
            val firstImage = ::gen_firstImage_fn
            fun gen_imageCount_fn(item: UTSJSONObject): Number {
                return imageListFromItem(item).length
            }
            val imageCount = ::gen_imageCount_fn
            fun gen_openPreview_fn(item: UTSJSONObject, index: Number) {
                val list = imageListFromItem(item)
                if (list.length == 0) {
                    return
                }
                previewImages.value = list
                previewIndex.value = index
                previewVisible.value = true
                previewItem.value = item
            }
            val openPreview = ::gen_openPreview_fn
            fun gen_handlePreviewVisibleChange_fn(value: Boolean) {
                previewVisible.value = value
            }
            val handlePreviewVisibleChange = ::gen_handlePreviewVisibleChange_fn
            fun gen_handlePreviewIndexChange_fn(value: Number) {
                previewIndex.value = value
            }
            val handlePreviewIndexChange = ::gen_handlePreviewIndexChange_fn
            fun gen_handlePreviewOpen_fn(payload: UTSJSONObject) {
                emit("image-preview", _uO("item" to previewItem.value, "preview" to payload))
            }
            val handlePreviewOpen = ::gen_handlePreviewOpen_fn
            fun gen_handlePreviewClose_fn(payload: UTSJSONObject) {
                emit("preview-close", _uO("item" to previewItem.value, "preview" to payload))
                previewItem.value = null
            }
            val handlePreviewClose = ::gen_handlePreviewClose_fn
            fun gen_tagList_fn(item: UTSJSONObject): UTSArray<String> {
                val result: UTSArray<String> = _uA()
                if (props.tagField == "") {
                    return result
                }
                val raw = objectField(item, props.tagField)
                if (raw == null) {
                    return result
                }
                if (raw is UTSArray<*>) {
                    val list = raw as UTSArray<Any>
                    run {
                        var i: Number = 0
                        while(i < list.length){
                            val tag = stringValue(list[i])
                            if (tag != "") {
                                result.push(tag)
                            }
                            i++
                        }
                    }
                    return result
                }
                val text = stringValue(raw)
                if (text == "") {
                    return result
                }
                val parts = text.split(",")
                run {
                    var i: Number = 0
                    while(i < parts.length){
                        val tag = parts[i].trim()
                        if (tag != "") {
                            result.push(tag)
                        }
                        i++
                    }
                }
                return result
            }
            val tagList = ::gen_tagList_fn
            fun gen_toneForTag_fn(tag: String): String {
                val mapped = objectField(props.tagColorMap, tag)
                val mappedText = stringValue(mapped)
                if (mappedText != "") {
                    return mappedText
                }
                if (tag.indexOf("完成") >= 0 || tag.indexOf("成功") >= 0 || tag.indexOf("启用") >= 0) {
                    return "success"
                }
                if (tag.indexOf("取消") >= 0 || tag.indexOf("失败") >= 0 || tag.indexOf("禁用") >= 0) {
                    return "danger"
                }
                if (tag.indexOf("待") >= 0 || tag.indexOf("预警") >= 0 || tag.indexOf("热销") >= 0) {
                    return "warning"
                }
                if (tag.indexOf("新品") >= 0 || tag.indexOf("推荐") >= 0) {
                    return "violet"
                }
                return "info"
            }
            val toneForTag = ::gen_toneForTag_fn
            fun gen_tagClass_fn(tag: String): String {
                val tone = toneForTag(tag)
                return "lul-tag lul-tag-" + tone
            }
            val tagClass = ::gen_tagClass_fn
            fun gen_tagTextClass_fn(tag: String): String {
                val tone = toneForTag(tag)
                return "lul-tag-text lul-tag-text-" + tone
            }
            val tagTextClass = ::gen_tagTextClass_fn
            fun gen_actionClass_fn(action: UTSJSONObject): String {
                return "lul-action lul-action-" + actionTone(action)
            }
            val actionClass = ::gen_actionClass_fn
            fun gen_actionTextClass_fn(action: UTSJSONObject): String {
                return "lul-action-text lul-action-text-" + actionTone(action)
            }
            val actionTextClass = ::gen_actionTextClass_fn
            fun gen_actionIconClass_fn(action: UTSJSONObject): String {
                return "lul-action-icon lul-action-text-" + actionTone(action)
            }
            val actionIconClass = ::gen_actionIconClass_fn
            fun gen_handleAction_fn(item: UTSJSONObject, action: UTSJSONObject) {
                emit("action", _uO("item" to item, "action" to action))
            }
            val handleAction = ::gen_handleAction_fn
            fun gen_handleMenu_fn(item: UTSJSONObject) {
                if (props.menuActions.length == 0) {
                    return
                }
                val itemList: UTSArray<String> = _uA()
                run {
                    var i: Number = 0
                    while(i < props.menuActions.length){
                        itemList.push(menuText(props.menuActions[i]))
                        i++
                    }
                }
                uni_showActionSheet(ShowActionSheetOptions(itemList = itemList, success = fun(res){
                    val selectedIndex = res.tapIndex
                    if (selectedIndex < 0 || selectedIndex >= props.menuActions.length) {
                        return
                    }
                    emit("menu", _uO("item" to item, "action" to props.menuActions[selectedIndex], "index" to selectedIndex))
                }
                ))
            }
            val handleMenu = ::gen_handleMenu_fn
            fun gen_canGoPrev_fn(): Boolean {
                return safeCurrentPage() > 1
            }
            val canGoPrev = ::gen_canGoPrev_fn
            fun gen_canGoNext_fn(): Boolean {
                return safeCurrentPage() < safeTotalPages()
            }
            val canGoNext = ::gen_canGoNext_fn
            fun gen_emitPageChange_fn(page: Number) {
                emit("page-change", _uO("page" to page, "currentPage" to safeCurrentPage(), "totalPages" to safeTotalPages(), "totalCount" to safeTotalCount()))
            }
            val emitPageChange = ::gen_emitPageChange_fn
            fun gen_goPrevPage_fn() {
                if (!canGoPrev()) {
                    return
                }
                emitPageChange(safeCurrentPage() - 1)
            }
            val goPrevPage = ::gen_goPrevPage_fn
            fun gen_goNextPage_fn() {
                if (!canGoNext()) {
                    return
                }
                emitPageChange(safeCurrentPage() + 1)
            }
            val goNextPage = ::gen_goNextPage_fn
            return fun(): Any? {
                val _component_lili_preview = resolveEasyComponent("lili-preview", GenUniModulesLiliPreviewComponentsLiliPreviewLiliPreviewClass)
                return _cE("view", _uM("class" to "lul-root"), _uA(
                    if (isTrue(_ctx.loading)) {
                        _cE("view", _uM("key" to 0, "class" to "lul-state-card"), _uA(
                            _cE("view", _uM("class" to "lul-loading-dot")),
                            _cE("text", _uM("class" to "lul-state-title"), _tD(_ctx.loadingText), 1),
                            _cE("text", _uM("class" to "lul-state-desc"), "请稍候，列表正在更新。")
                        ))
                    } else {
                        if (_ctx.items.length == 0) {
                            _cE("view", _uM("key" to 1, "class" to "lul-state-card"), _uA(
                                _cE("view", _uM("class" to "lul-empty-badge"), _uA(
                                    _cE("text", _uM("class" to "lul-empty-badge-text"), _tD(_ctx.emptyIcon), 1)
                                )),
                                _cE("text", _uM("class" to "lul-state-title"), _tD(_ctx.emptyText), 1),
                                _cE("text", _uM("class" to "lul-state-desc"), "当前没有可展示的数据。")
                            ))
                        } else {
                            _cE("view", _uM("key" to 2, "class" to "lul-list"), _uA(
                                _cE(Fragment, null, RenderHelpers.renderList(_ctx.items, fun(item, itemIndex, __index, _cached): Any {
                                    return _cE("view", _uM("key" to itemKey(item, itemIndex), "class" to _nC(if (isSelected(item)) {
                                        "lul-card lul-card-selected"
                                    } else {
                                        "lul-card"
                                    }
                                    ), "onClick" to fun(){
                                        handleItemClick(item)
                                    }
                                    ), _uA(
                                        _cE("view", _uM("class" to "lul-card-top"), _uA(
                                            _cE("view", _uM("class" to "lul-card-top-left"), _uA(
                                                if (isTrue(_ctx.selectionMode)) {
                                                    _cE("view", _uM("key" to 0, "class" to "lul-check-wrap", "onClick" to withModifiers(fun(){
                                                        toggleSelection(item)
                                                    }, _uA(
                                                        "stop"
                                                    ))), _uA(
                                                        _cE("view", _uM("class" to _nC(if (isSelected(item)) {
                                                            "lul-check lul-check-active"
                                                        } else {
                                                            "lul-check"
                                                        })), _uA(
                                                            if (isTrue(isSelected(item))) {
                                                                _cE("text", _uM("key" to 0, "class" to "lul-check-icon"), "✓")
                                                            } else {
                                                                _cC("v-if", true)
                                                            }
                                                        ), 2)
                                                    ), 8, _uA(
                                                        "onClick"
                                                    ))
                                                } else {
                                                    _cC("v-if", true)
                                                }
                                                ,
                                                if (isTrue(_ctx.showImage && firstImage(item) != "")) {
                                                    _cE("view", _uM("key" to 1, "class" to "lul-cover-wrap"), _uA(
                                                        _cE("image", _uM("class" to "lul-cover", "src" to firstImage(item), "mode" to "aspectFill", "onClick" to withModifiers(fun(){
                                                            openPreview(item, 0)
                                                        }, _uA(
                                                            "stop"
                                                        ))), null, 8, _uA(
                                                            "src",
                                                            "onClick"
                                                        )),
                                                        if (imageCount(item) > 1) {
                                                            _cE("view", _uM("key" to 0, "class" to "lul-cover-count"), _uA(
                                                                _cE("text", _uM("class" to "lul-cover-count-text"), "+" + _tD(imageCount(item) - 1), 1)
                                                            ))
                                                        } else {
                                                            _cC("v-if", true)
                                                        }
                                                    ))
                                                } else {
                                                    _cC("v-if", true)
                                                }
                                                ,
                                                _cE("view", _uM("class" to "lul-main"), _uA(
                                                    _cE("view", _uM("class" to "lul-headline-row"), _uA(
                                                        _cE("view", _uM("class" to "lul-title-wrap"), _uA(
                                                            if (_ctx.titleField != "") {
                                                                _cE("text", _uM("key" to 0, "class" to "lul-title"), _tD(fieldText(item, _ctx.titleField)), 1)
                                                            } else {
                                                                _cC("v-if", true)
                                                            }
                                                            ,
                                                            if (isTrue(_ctx.subtitleField != "" && fieldText(item, _ctx.subtitleField) != "")) {
                                                                _cE("text", _uM("key" to 1, "class" to "lul-subtitle", "onClick" to withModifiers(fun(){
                                                                    handleSubtitleClick(item)
                                                                }, _uA(
                                                                    "stop"
                                                                ))), _tD(fieldText(item, _ctx.subtitleField)), 9, _uA(
                                                                    "onClick"
                                                                ))
                                                            } else {
                                                                _cC("v-if", true)
                                                            }
                                                            ,
                                                            if (isTrue(_ctx.metaField != "" && fieldText(item, _ctx.metaField) != "")) {
                                                                _cE("text", _uM("key" to 2, "class" to "lul-meta", "onClick" to withModifiers(fun(){
                                                                    handleMetaClick(item)
                                                                }, _uA(
                                                                    "stop"
                                                                ))), _tD(fieldText(item, _ctx.metaField)), 9, _uA(
                                                                    "onClick"
                                                                ))
                                                            } else {
                                                                _cC("v-if", true)
                                                            }
                                                        )),
                                                        if (isTrue(_ctx.showMenu)) {
                                                            _cE("view", _uM("key" to 0, "class" to "lul-menu-wrap", "onClick" to withModifiers(fun(){
                                                                handleMenu(item)
                                                            }, _uA(
                                                                "stop"
                                                            ))), _uA(
                                                                _cE("image", _uM("class" to "lul-menu-image", "src" to "/static/icon/更多.png", "mode" to "aspectFit"))
                                                            ), 8, _uA(
                                                                "onClick"
                                                            ))
                                                        } else {
                                                            _cC("v-if", true)
                                                        }
                                                        ,
                                                        if (isTrue(_ctx.showChevron)) {
                                                            _cE("view", _uM("key" to 1, "class" to "lul-chevron-wrap"), _uA(
                                                                _cE("text", _uM("class" to "lul-chevron"), "›")
                                                            ))
                                                        } else {
                                                            _cC("v-if", true)
                                                        }
                                                    )),
                                                    if (tagList(item).length > 0) {
                                                        _cE("view", _uM("key" to 0, "class" to "lul-tags"), _uA(
                                                            _cE(Fragment, null, RenderHelpers.renderList(tagList(item), fun(tag, tagIndex, __index, _cached): Any {
                                                                return _cE("view", _uM("key" to (tag + "-" + tagIndex), "class" to _nC(tagClass(tag))), _uA(
                                                                    _cE("text", _uM("class" to _nC(tagTextClass(tag))), _tD(tag), 3)
                                                                ), 2)
                                                            }), 128)
                                                        ))
                                                    } else {
                                                        _cC("v-if", true)
                                                    }
                                                ))
                                            )),
                                            if (_ctx.fields.length > 0) {
                                                _cE("view", _uM("key" to 0, "class" to "lul-fields"), _uA(
                                                    _cE(Fragment, null, RenderHelpers.renderList(_ctx.fields, fun(field, fieldIndex, __index, _cached): Any {
                                                        return _cE("view", _uM("key" to fieldKey(field, fieldIndex), "class" to "lul-field-chip", "onClick" to withModifiers(fun(){
                                                            handleFieldClick(item, field)
                                                        }, _uA(
                                                            "stop"
                                                        ))), _uA(
                                                            if (fieldIcon(field) != "") {
                                                                _cE("text", _uM("key" to 0, "class" to "lul-field-icon"), _tD(fieldIcon(field)), 1)
                                                            } else {
                                                                _cC("v-if", true)
                                                            },
                                                            if (fieldLabel(field) != "") {
                                                                _cE("text", _uM("key" to 1, "class" to "lul-field-label"), _tD(fieldLabel(field)), 1)
                                                            } else {
                                                                _cC("v-if", true)
                                                            },
                                                            _cE("text", _uM("class" to "lul-field-value"), _tD(displayField(item, field)), 1)
                                                        ), 8, _uA(
                                                            "onClick"
                                                        ))
                                                    }), 128)
                                                ))
                                            } else {
                                                _cC("v-if", true)
                                            }
                                        ))
                                    ), 10, _uA(
                                        "onClick"
                                    ))
                                }
                                ), 128),
                                if (isTrue(_ctx.showPagination)) {
                                    _cE("view", _uM("key" to 0, "class" to "lul-pagination"), _uA(
                                        _cE("view", _uM("class" to "lul-pagination-summary"), _uA(
                                            _cE("text", _uM("class" to "lul-pagination-summary-text"), "共 " + _tD(unref(totalCountText)) + " 条", 1),
                                            _cE("text", _uM("class" to "lul-pagination-summary-text"), "第 " + _tD(unref(currentPageText)) + " / " + _tD(unref(totalPagesText)) + " 页", 1)
                                        )),
                                        _cE("view", _uM("class" to "lul-pagination-actions"), _uA(
                                            _cE("view", _uM("class" to _nC(if (canGoPrev()) {
                                                "lul-page-btn"
                                            } else {
                                                "lul-page-btn lul-page-btn-disabled"
                                            }), "onClick" to goPrevPage), _uA(
                                                _cE("text", _uM("class" to _nC(if (canGoPrev()) {
                                                    "lul-page-btn-text"
                                                } else {
                                                    "lul-page-btn-text lul-page-btn-text-disabled"
                                                })), "上一页", 2)
                                            ), 2),
                                            _cE("view", _uM("class" to _nC(if (canGoNext()) {
                                                "lul-page-btn lul-page-btn-primary"
                                            } else {
                                                "lul-page-btn lul-page-btn-primary lul-page-btn-disabled-primary"
                                            }), "onClick" to goNextPage), _uA(
                                                _cE("text", _uM("class" to _nC(if (canGoNext()) {
                                                    "lul-page-btn-text lul-page-btn-text-light"
                                                } else {
                                                    "lul-page-btn-text lul-page-btn-text-disabled-light"
                                                })), "下一页", 2)
                                            ), 2)
                                        ))
                                    ))
                                } else {
                                    _cC("v-if", true)
                                }
                            ))
                        }
                    }
                    ,
                    _cV(_component_lili_preview, _uM("images" to unref(previewImages), "initialIndex" to unref(previewIndex), "visible" to unref(previewVisible), "enableSave" to _ctx.enablePreviewSave, "enableShare" to _ctx.enablePreviewShare, "onUpdate:visible" to handlePreviewVisibleChange, "onUpdate:index" to handlePreviewIndexChange, "onPreview" to handlePreviewOpen, "onClose" to handlePreviewClose), null, 8, _uA(
                        "images",
                        "initialIndex",
                        "visible",
                        "enableSave",
                        "enableShare"
                    ))
                ))
            }
        }
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("lul-root" to _pS(_uM("width" to "100%")), "lul-list" to _pS(_uM("width" to "100%")), "lul-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "paddingLeft" to 10, "paddingRight" to 10, "paddingTop" to 10, "paddingBottom" to 10, "marginBottom" to 6, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E7ECF3", "borderRightColor" to "#E7ECF3", "borderBottomColor" to "#E7ECF3", "borderLeftColor" to "#E7ECF3")), "lul-card-selected" to _pS(_uM("backgroundColor" to "#F8FBFF", "borderTopColor" to "#7CC4FF", "borderRightColor" to "#7CC4FF", "borderBottomColor" to "#7CC4FF", "borderLeftColor" to "#7CC4FF")), "lul-card-top" to _pS(_uM("width" to "100%")), "lul-card-top-left" to _pS(_uM("flexDirection" to "row")), "lul-check-wrap" to _pS(_uM("width" to 26, "paddingTop" to 4, "alignItems" to "flex-start")), "lul-check" to _pS(_uM("width" to 18, "height" to 18, "borderTopLeftRadius" to 9, "borderTopRightRadius" to 9, "borderBottomRightRadius" to 9, "borderBottomLeftRadius" to 9, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#CBD5E1", "borderRightColor" to "#CBD5E1", "borderBottomColor" to "#CBD5E1", "borderLeftColor" to "#CBD5E1", "alignItems" to "center", "justifyContent" to "center", "backgroundColor" to "#FFFFFF")), "lul-check-active" to _pS(_uM("borderTopColor" to "#0F172A", "borderRightColor" to "#0F172A", "borderBottomColor" to "#0F172A", "borderLeftColor" to "#0F172A", "backgroundColor" to "#0F172A")), "lul-check-icon" to _pS(_uM("fontSize" to 10, "lineHeight" to "10px", "color" to "#FFFFFF")), "lul-cover-wrap" to _pS(_uM("width" to 64, "height" to 64, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "overflow" to "hidden", "backgroundColor" to "#E2E8F0", "position" to "relative", "marginRight" to 8)), "lul-cover" to _pS(_uM("width" to 64, "height" to 64)), "lul-cover-count" to _pS(_uM("position" to "absolute", "right" to 6, "bottom" to 6, "paddingLeft" to 5, "paddingRight" to 5, "paddingTop" to 2, "paddingBottom" to 2, "borderTopLeftRadius" to 999, "borderTopRightRadius" to 999, "borderBottomRightRadius" to 999, "borderBottomLeftRadius" to 999, "backgroundColor" to "rgba(15,23,42,0.72)")), "lul-cover-count-text" to _pS(_uM("fontSize" to 10, "lineHeight" to "10px", "color" to "#FFFFFF")), "lul-main" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%")), "lul-headline-row" to _pS(_uM("flexDirection" to "row", "alignItems" to "flex-start", "justifyContent" to "space-between")), "lul-title-wrap" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "paddingRight" to 6)), "lul-title" to _pS(_uM("fontSize" to 15, "lineHeight" to "18px", "color" to "#0F172A", "fontWeight" to "700")), "lul-subtitle" to _pS(_uM("fontSize" to 12, "lineHeight" to "14px", "color" to "#64748B", "marginTop" to 2)), "lul-meta" to _pS(_uM("fontSize" to 12, "lineHeight" to "14px", "color" to "#475569", "marginTop" to 2)), "lul-menu-wrap" to _pS(_uM("width" to 24, "height" to 24, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#F8FAFC", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "alignItems" to "center", "justifyContent" to "center", "marginRight" to 6)), "lul-menu-icon" to _pS(_uM("fontSize" to 15, "lineHeight" to "15px", "color" to "#64748B")), "lul-menu-image" to _pS(_uM("width" to 16, "height" to 16)), "lul-chevron-wrap" to _pS(_uM("width" to 16, "alignItems" to "flex-end", "paddingTop" to 1)), "lul-chevron" to _pS(_uM("fontSize" to 16, "lineHeight" to "16px", "color" to "#94A3B8")), "lul-tags" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 6, "alignItems" to "center")), "lul-tag" to _pS(_uM("alignItems" to "center", "justifyContent" to "center", "height" to 22, "paddingLeft" to 8, "paddingRight" to 8, "borderTopLeftRadius" to 999, "borderTopRightRadius" to 999, "borderBottomRightRadius" to 999, "borderBottomLeftRadius" to 999, "marginRight" to 5, "marginBottom" to 4)), "lul-tag-info" to _pS(_uM("backgroundColor" to "#E0F2FE")), "lul-tag-success" to _pS(_uM("backgroundColor" to "#DCFCE7")), "lul-tag-warning" to _pS(_uM("backgroundColor" to "#FEF3C7")), "lul-tag-danger" to _pS(_uM("backgroundColor" to "#FEE2E2")), "lul-tag-violet" to _pS(_uM("backgroundColor" to "#EDE9FE")), "lul-tag-muted" to _pS(_uM("backgroundColor" to "#E2E8F0")), "lul-tag-text" to _pS(_uM("fontSize" to 10, "lineHeight" to "16px", "fontWeight" to "600")), "lul-tag-text-info" to _pS(_uM("color" to "#0369A1")), "lul-tag-text-success" to _pS(_uM("color" to "#15803D")), "lul-tag-text-warning" to _pS(_uM("color" to "#B45309")), "lul-tag-text-danger" to _pS(_uM("color" to "#B91C1C")), "lul-tag-text-violet" to _pS(_uM("color" to "#6D28D9")), "lul-tag-text-muted" to _pS(_uM("color" to "#475569")), "lul-fields" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 8, "paddingTop" to 8, "borderTopWidth" to 1, "borderTopStyle" to "solid", "borderTopColor" to "#E7ECF3")), "lul-field-chip" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "paddingLeft" to 8, "paddingRight" to 8, "paddingTop" to 5, "paddingBottom" to 5, "borderTopLeftRadius" to 10, "borderTopRightRadius" to 10, "borderBottomRightRadius" to 10, "borderBottomLeftRadius" to 10, "backgroundColor" to "#F8FAFC", "marginRight" to 5, "marginTop" to 4, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0")), "lul-field-icon" to _pS(_uM("fontSize" to 11, "lineHeight" to "15px")), "lul-field-label" to _pS(_uM("fontSize" to 11, "lineHeight" to "15px")), "lul-field-value" to _pS(_uM("fontSize" to 11, "lineHeight" to "15px")), "lul-actions" to _pS(_uM("flexDirection" to "row", "flexWrap" to "wrap", "marginTop" to 14)), "lul-action" to _pS(_uM("flexDirection" to "row", "alignItems" to "center", "justifyContent" to "center", "paddingLeft" to 12, "paddingRight" to 12, "paddingTop" to 9, "paddingBottom" to 9, "borderTopLeftRadius" to 14, "borderTopRightRadius" to 14, "borderBottomRightRadius" to 14, "borderBottomLeftRadius" to 14, "marginRight" to 10, "marginTop" to 8, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid")), "lul-action-info" to _pS(_uM("backgroundColor" to "#F8FAFC", "borderTopColor" to "#BFDBFE", "borderRightColor" to "#BFDBFE", "borderBottomColor" to "#BFDBFE", "borderLeftColor" to "#BFDBFE")), "lul-action-success" to _pS(_uM("backgroundColor" to "#F0FDF4", "borderTopColor" to "#BBF7D0", "borderRightColor" to "#BBF7D0", "borderBottomColor" to "#BBF7D0", "borderLeftColor" to "#BBF7D0")), "lul-action-warning" to _pS(_uM("backgroundColor" to "#FFF7ED", "borderTopColor" to "#FED7AA", "borderRightColor" to "#FED7AA", "borderBottomColor" to "#FED7AA", "borderLeftColor" to "#FED7AA")), "lul-action-danger" to _pS(_uM("backgroundColor" to "#FEF2F2", "borderTopColor" to "#FECACA", "borderRightColor" to "#FECACA", "borderBottomColor" to "#FECACA", "borderLeftColor" to "#FECACA")), "lul-action-icon" to _pS(_uM("fontSize" to 12, "lineHeight" to "12px", "marginRight" to 4)), "lul-action-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "12px", "fontWeight" to "700")), "lul-action-text-info" to _pS(_uM("color" to "#1D4ED8")), "lul-action-text-success" to _pS(_uM("color" to "#15803D")), "lul-action-text-warning" to _pS(_uM("color" to "#C2410C")), "lul-action-text-danger" to _pS(_uM("color" to "#B91C1C")), "lul-state-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 22, "borderTopRightRadius" to 22, "borderBottomRightRadius" to 22, "borderBottomLeftRadius" to 22, "paddingTop" to 34, "paddingBottom" to 34, "paddingLeft" to 20, "paddingRight" to 20, "alignItems" to "center", "justifyContent" to "center", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E7ECF3", "borderRightColor" to "#E7ECF3", "borderBottomColor" to "#E7ECF3", "borderLeftColor" to "#E7ECF3")), "lul-empty-badge" to _pS(_uM("width" to 56, "height" to 56, "borderTopLeftRadius" to 28, "borderTopRightRadius" to 28, "borderBottomRightRadius" to 28, "borderBottomLeftRadius" to 28, "backgroundColor" to "#F1F5F9", "alignItems" to "center", "justifyContent" to "center")), "lul-empty-badge-text" to _pS(_uM("fontSize" to 24, "lineHeight" to "24px", "color" to "#64748B")), "lul-loading-dot" to _pS(_uM("width" to 18, "height" to 18, "borderTopLeftRadius" to 9, "borderTopRightRadius" to 9, "borderBottomRightRadius" to 9, "borderBottomLeftRadius" to 9, "backgroundColor" to "#0F172A")), "lul-state-title" to _pS(_uM("fontSize" to 18, "lineHeight" to "24px", "color" to "#0F172A", "fontWeight" to "700", "marginTop" to 14)), "lul-state-desc" to _pS(_uM("fontSize" to 13, "lineHeight" to "19px", "color" to "#64748B", "marginTop" to 6)), "lul-pagination" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to 18, "borderTopRightRadius" to 18, "borderBottomRightRadius" to 18, "borderBottomLeftRadius" to 18, "paddingLeft" to 16, "paddingRight" to 16, "paddingTop" to 14, "paddingBottom" to 14, "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E7ECF3", "borderRightColor" to "#E7ECF3", "borderBottomColor" to "#E7ECF3", "borderLeftColor" to "#E7ECF3")), "lul-pagination-summary" to _pS(_uM("flexDirection" to "row", "justifyContent" to "space-between")), "lul-pagination-summary-text" to _pS(_uM("fontSize" to 12, "lineHeight" to "18px", "color" to "#64748B")), "lul-pagination-actions" to _pS(_uM("flexDirection" to "row", "marginTop" to 12)), "lul-page-btn" to _pS(_uM("flexGrow" to 1, "flexShrink" to 1, "flexBasis" to "0%", "height" to 38, "borderTopLeftRadius" to 12, "borderTopRightRadius" to 12, "borderBottomRightRadius" to 12, "borderBottomLeftRadius" to 12, "backgroundColor" to "#F8FAFC", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#E2E8F0", "borderRightColor" to "#E2E8F0", "borderBottomColor" to "#E2E8F0", "borderLeftColor" to "#E2E8F0", "alignItems" to "center", "justifyContent" to "center")), "lul-page-btn-primary" to _pS(_uM("backgroundColor" to "#0F172A", "borderTopColor" to "#0F172A", "borderRightColor" to "#0F172A", "borderBottomColor" to "#0F172A", "borderLeftColor" to "#0F172A", "marginLeft" to 10)), "lul-page-btn-disabled" to _pS(_uM("backgroundColor" to "#F8FAFC", "borderTopColor" to "#E5E7EB", "borderRightColor" to "#E5E7EB", "borderBottomColor" to "#E5E7EB", "borderLeftColor" to "#E5E7EB")), "lul-page-btn-disabled-primary" to _pS(_uM("backgroundColor" to "#CBD5E1", "borderTopColor" to "#CBD5E1", "borderRightColor" to "#CBD5E1", "borderBottomColor" to "#CBD5E1", "borderLeftColor" to "#CBD5E1")), "lul-page-btn-text" to _pS(_uM("fontSize" to 14, "lineHeight" to "14px", "color" to "#334155", "fontWeight" to "700")), "lul-page-btn-text-light" to _pS(_uM("color" to "#FFFFFF")), "lul-page-btn-text-disabled" to _pS(_uM("color" to "#94A3B8")), "lul-page-btn-text-disabled-light" to _pS(_uM("color" to "#E2E8F0")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM("item-click" to null, "action" to null, "update:selectedItems" to null, "selection-change" to null, "image-preview" to null, "preview-close" to null, "menu" to null, "page-change" to null, "subtitle-click" to null, "field-click" to null, "meta-click" to null)
        var props = _nP(_uM("items" to _uM("type" to "Array", "required" to false, "default" to fun(): UTSArray<UTSJSONObject> {
            return _uA()
        }
        ), "keyField" to _uM("type" to "String", "required" to false, "default" to "id"), "titleField" to _uM("type" to "String", "required" to false, "default" to "title"), "subtitleField" to _uM("type" to "String", "required" to false, "default" to ""), "metaField" to _uM("type" to "String", "required" to false, "default" to ""), "imageField" to _uM("type" to "String", "required" to false, "default" to "image"), "imageListField" to _uM("type" to "String", "required" to false, "default" to "images"), "showImage" to _uM("type" to "Boolean", "required" to false, "default" to true), "fields" to _uM("type" to "Array", "required" to false, "default" to fun(): UTSArray<UTSJSONObject> {
            return _uA()
        }
        ), "tagField" to _uM("type" to "String", "required" to false, "default" to "tags"), "tagColorMap" to _uM("type" to "UTSJSONObject", "required" to false, "default" to fun(): UTSJSONObject {
            return (_uO("进行中" to "info", "已完成" to "success", "已取消" to "danger", "待处理" to "warning", "草稿" to "muted", "启用" to "success", "禁用" to "danger", "新品" to "violet", "热销" to "warning", "推荐" to "info"))
        }
        ), "showActions" to _uM("type" to "Boolean", "required" to false, "default" to false), "actions" to _uM("type" to "Array", "required" to false, "default" to fun(): UTSArray<UTSJSONObject> {
            return _uA(
                _uO("key" to "detail", "text" to "详情", "icon" to "⌕", "tone" to "info"),
                _uO("key" to "edit", "text" to "编辑", "icon" to "✎", "tone" to "success")
            )
        }
        ), "selectionMode" to _uM("type" to "Boolean", "required" to false, "default" to false), "selectedItems" to _uM("type" to "Array", "required" to false, "default" to fun(): UTSArray<String> {
            return _uA()
        }
        ), "loading" to _uM("type" to "Boolean", "required" to false, "default" to false), "loadingText" to _uM("type" to "String", "required" to false, "default" to "加载中..."), "emptyText" to _uM("type" to "String", "required" to false, "default" to "暂无数据"), "emptyIcon" to _uM("type" to "String", "required" to false, "default" to "◌"), "showChevron" to _uM("type" to "Boolean", "required" to false, "default" to true), "showMenu" to _uM("type" to "Boolean", "required" to false, "default" to false), "menuActions" to _uM("type" to "Array", "required" to false, "default" to fun(): UTSArray<UTSJSONObject> {
            return _uA()
        }
        ), "showPagination" to _uM("type" to "Boolean", "required" to false, "default" to false), "currentPage" to _uM("type" to "Number", "required" to false, "default" to 1), "totalPages" to _uM("type" to "Number", "required" to false, "default" to 1), "totalCount" to _uM("type" to "Number", "required" to false, "default" to 0), "enablePreviewSave" to _uM("type" to "Boolean", "required" to false, "default" to true), "enablePreviewShare" to _uM("type" to "Boolean", "required" to false, "default" to true)))
        var propsNeedCastKeys = _uA(
            "items",
            "keyField",
            "titleField",
            "subtitleField",
            "metaField",
            "imageField",
            "imageListField",
            "showImage",
            "fields",
            "tagField",
            "tagColorMap",
            "showActions",
            "actions",
            "selectionMode",
            "selectedItems",
            "loading",
            "loadingText",
            "emptyText",
            "emptyIcon",
            "showChevron",
            "showMenu",
            "menuActions",
            "showPagination",
            "currentPage",
            "totalPages",
            "totalCount",
            "enablePreviewSave",
            "enablePreviewShare"
        )
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
