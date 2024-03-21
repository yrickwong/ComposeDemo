package com.example.giftpanelcompose.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by wangyi.huohuo on 14/3/24
 * @author wangyi.huohuo@bytedance.com
 */

@JsonClass(generateAdapter = true)
data class HttpResult(
    @Json(name = "data") val data: GiftResultData
)

@JsonClass(generateAdapter = true)
data class GiftResultData(
    @Json(name = "doodle_templates")
    var doodleTemplates: List<Any?>? = null,

    @Json(name = "pages")
    val giftPages: List<GiftPage>,

    @Json(name = "gifts")
    var giftList: List<Gift>? = null,
    )


@JsonClass(generateAdapter = true)
data class GiftPage(
    @Json(name = "page_type")
    val pageType: Int = 0,

    @Json(name = "page_name")
    val pageName: String? = null,

    @Json(name = "gifts")
    val gifts: List<Gift>? = null,

    @Json(name = "display")
    val display: Boolean = false,
)


@JsonClass(generateAdapter = true)
data class Gift(

    @Json(name = "id")
    val giftID: Long,

    @Json(name = "name")
    val giftName: String,

    @Json(name = "image")
    val mImage: ImageModel,

    @Json(name = "preview_image")
    var mPreviewImage: ImageModel? = null,

    @Json(name = "color_infos")
    val colorInfos: List<ColorInfo>,

    @Json(name = "gift_rank_recommend_info")
    val giftRankRecommendInfo: String? = null,

    @Json(name = "gift_scene")
    val giftScene: Long,

    @Json(name = "lock_info")
    val lockInfo: LockInfo,

    @Json(name = "item_type")
    val itemType: Long,

    @Json(name = "diamond_count")
    val diamondCount: Long,

    @Json(name = "is_displayed_on_panel")
    val isDisplayedOnPanel: Boolean,

    @Json(name = "primary_effect_id")
    val primaryEffectID: Long,

    @Json(name = "special_effects")
    val specialEffects: Map<String, Long>,

    @Json(name = "trigger_words")
    val triggerWords: List<Any?>,

    @Json(name = "gift_label_icon")
    val giftLabelIcon: ImageModel? = null,

    @Json(name = "random_effect_info")
    val randomEffectInfo: RandomEffectInfo? = null,

    @Json(name = "gift_panel_banner")
    val giftPanelBanner: GiftPanelBanner? = null,

    val notify: Boolean? = null
)


@JsonClass(generateAdapter = true)
data class ColorInfo(
    @Json(name = "color_effect_id")
    val colorEffectID: Long,

    @Json(name = "color_id")
    val colorID: Long,

    @Json(name = "color_name")
    val colorName: String,

    @Json(name = "color_values")
    val colorValues: List<String>,

    @Json(name = "is_default")
    val isDefault: Boolean? = null
)

@JsonClass(generateAdapter = true)
data class GiftPanelBanner(
    @Json(name = "bg_color_values")
    val bgColorValues: List<Any?>,

    @Json(name = "display_text")
    val displayText: DisplayText,
)

@JsonClass(generateAdapter = true)
data class DisplayText(
    @Json(name = "default_format")
    val defaultFormat: DefaultFormat,

    @Json(name = "default_pattern")
    val defaultPattern: String,

    val key: String,
    val pieces: List<Piece>
)

@JsonClass(generateAdapter = true)
data class DefaultFormat(

    @Json(name = "font_size")
    val fontSize: Long,

    val italic: Boolean,
    val weight: Long
)


@JsonClass(generateAdapter = true)
data class Piece(
    @Json(name = "string_value")
    val stringValue: String,

    val type: Long
)

data class LockInfo(
    val lock: Boolean,

    @Json(name = "lock_type")
    val lockType: Long
)

@JsonClass(generateAdapter = true)
data class RandomEffectInfo(
    @Json(name = "audience_key")
    val audienceKey: String,

    @Json(name = "effect_ids")
    val effectIDS: List<Long>,

    @Json(name = "host_key")
    val hostKey: String,

    @Json(name = "random_gift_bubble")
    val randomGiftBubble: RandomGiftBubble,

    @Json(name = "random_gift_panel_banner")
    val randomGiftPanelBanner: RandomGiftPanelBanner
)

data class RandomGiftBubble(
    @Json(name = "display_text")
    val displayText: String? = null,
)

@JsonClass(generateAdapter = true)
data class RandomGiftPanelBanner(
    @Json(name = "bg_color_values")
    val bgColors: List<String>,

    @Json(name = "collect_num")
    val collectNum: Long,

    @Json(name = "display_text")
    val displayText: String? = null,

    @Json(name = "left_icon")
    val leftIcon: ImageModel? = null,

    val round: Long,

    @Json(name = "schema_url")
    val schemaURL: String,

    @Json(name = "shading_image")
    val shadingImage: ImageModel? = null,

    @Json(name = "target_num")
    val targetNum: Long
)


data class ImageModel(
    @Json(name = "avg_color") val avgColor: String,
    @Json(name = "uri") val uri: String,
    @Json(name = "url_list") val uriList: List<String>,
    @Json(name = "open_web_url") var openWebUrl: String? = null,
    @Json(name = "image_type") val imageType: Int,
    @Json(name = "content") var content: Content? = null,
    @Json(name = "is_animated") val isAnimated: Boolean,
    @Json(name = "width") val width: Int,
    @Json(name = "height") val height: Int,
)

data class Content(
    @Json(name = "name") val name: String,
    @Json(name = "font_color") val fontColor: String,
    @Json(name = "level") val level: Long = 0
)