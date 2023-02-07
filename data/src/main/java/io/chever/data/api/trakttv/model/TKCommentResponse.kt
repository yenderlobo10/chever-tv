package io.chever.data.api.trakttv.model

import com.squareup.moshi.Json
import java.util.Date

/**
 * Trakt.tv standard comment object.
 */
data class TKCommentResponse(
    val id: Long,

    @Json(name = "parent_id")
    val parentId: Int,

    @Json(name = "created_at")
    val createdAt: Date?,

    @Json(name = "updated_at")
    val updatedAt: Date?,

    val comment: String,
    val spoiler: Boolean,
    val review: Boolean,
    val replies: Int,
    val likes: Int,

    @Json(name = "user_stats")
    val userStats: TKUserStats,

    val user: TKUser,
)