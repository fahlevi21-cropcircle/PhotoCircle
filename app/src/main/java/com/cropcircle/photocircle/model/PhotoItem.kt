package com.cropcircle.photocircle.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoItem(
    @SerializedName("alt_description")
    @Expose
    val altDescription: String = "", // brown cliff
    @SerializedName("blur_hash")
    @Expose
    val blurHash: String = "", // LYDkl*r:0|Ng$*RiJAs;WrWXaxxG
    @SerializedName("color")
    @Expose
    val color: String = "", // #262640
    @SerializedName("created_at")
    @Expose
    val createdAt: String = "", // 2017-04-20T17:45:45-04:00
    @SerializedName("description")
    @Expose
    val description: String = "", // Antelope Canyon
    @SerializedName("height")
    @Expose
    val height: Int = 0, // 3024
    @SerializedName("id")
    @Expose
    val id: String = "", // sO-JmQj95ec
    @SerializedName("liked_by_user")
    @Expose
    val likedByUser: Boolean = false, // false
    @SerializedName("likes")
    @Expose
    val likes: Int = 0, // 1299
    @SerializedName("links")
    @Expose
    val links: PhotoLink = PhotoLink(),
    @SerializedName("promoted_at")
    @Expose
    val promotedAt: String = "", // 2017-04-21T12:39:24-04:00
    @SerializedName("updated_at")
    @Expose
    val updatedAt: String = "", // 2021-07-31T20:02:04-04:00
    @SerializedName("urls")
    @Expose
    val urls: PhotoUrl = PhotoUrl(),
    @SerializedName("user")
    @Expose
    val user: User = User(),
    @SerializedName("width")
    @Expose
    val width: Int = 0 // 4032
): Parcelable
