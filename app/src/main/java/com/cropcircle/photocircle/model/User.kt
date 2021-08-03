package com.cropcircle.photocircle.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("bio")
    @Expose
    val bio: String = "", // https://www.lanceplaine.com
    @SerializedName("first_name")
    @Expose
    val firstName: String = "", // Kevin
    @SerializedName("id")
    @Expose
    val id: String = "", // iC3huwD2uVU
    @SerializedName("instagram_username")
    @Expose
    val instagramUsername: String = "", // lanceplaine.k
    @SerializedName("last_name")
    @Expose
    val lastName: String = "", // Lanceplaine
    @SerializedName("links")
    @Expose
    val links: UserLink = UserLink(),
    @SerializedName("location")
    @Expose
    val location: String = "", // Palo Alto, California
    @SerializedName("name")
    @Expose
    val name: String = "", // Kevin Lanceplaine
    @SerializedName("portfolio_url")
    @Expose
    val portfolioUrl: String = "", // https://www.lanceplaine.com
    @SerializedName("profile_image")
    @Expose
    val profileImage: ProfileImage = ProfileImage(),
    @SerializedName("social")
    @Expose
    val social: Social = Social(),
    @SerializedName("total_collections")
    @Expose
    val totalCollections: Int = 0, // 14
    @SerializedName("total_likes")
    @Expose
    val totalLikes: Int = 0, // 19
    @SerializedName("total_photos")
    @Expose
    val totalPhotos: Int = 0, // 281
    @SerializedName("twitter_username")
    @Expose
    val twitterUsername: String = "", // lanceplaine
    @SerializedName("updated_at")
    @Expose
    val updatedAt: String = "", // 2021-07-30T20:48:50-04:00
    @SerializedName("username")
    @Expose
    val username: String = "" // lanceplaine
) : Parcelable {
    val attributionUrl get() = "https://unslpash.com/$username?utm_source=PhotoCircle&utm_medium=referral"

    @Parcelize
    data class ProfileImage(
        @SerializedName("large")
        @Expose
        val large: String = "", // https://images.unsplash.com/profile-1600096866391-b09a1a53451aimage?ixlib=rb-1.2.1&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128
        @SerializedName("medium")
        @Expose
        val medium: String = "", // https://images.unsplash.com/profile-1600096866391-b09a1a53451aimage?ixlib=rb-1.2.1&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64
        @SerializedName("small")
        @Expose
        val small: String = "" // https://images.unsplash.com/profile-1600096866391-b09a1a53451aimage?ixlib=rb-1.2.1&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32
    ) : Parcelable

    @Parcelize
    data class Social(
        @SerializedName("instagram_username")
        @Expose
        val instagramUsername: String = "", // dell // null
        @SerializedName("portfolio_url")
        @Expose
        val portfolioUrl: String = "", // http://www.dell.com/xps
        @SerializedName("twitter_username")
        @Expose
        val twitterUsername: String = "" // Dell
    ) : Parcelable

    @Parcelize
    data class UserLink(
        @SerializedName("followers")
        @Expose
        val followers: String = "", // https://api.unsplash.com/users/lanceplaine/followers
        @SerializedName("following")
        @Expose
        val following: String = "", // https://api.unsplash.com/users/lanceplaine/following
        @SerializedName("html")
        @Expose
        val html: String = "", // https://unsplash.com/@lanceplaine
        @SerializedName("likes")
        @Expose
        val likes: String = "", // https://api.unsplash.com/users/lanceplaine/likes
        @SerializedName("photos")
        @Expose
        val photos: String = "", // https://api.unsplash.com/users/lanceplaine/photos
        @SerializedName("portfolio")
        @Expose
        val portfolio: String = "", // https://api.unsplash.com/users/lanceplaine/portfolio
        @SerializedName("self")
        @Expose
        val self: String = "" // https://api.unsplash.com/users/lanceplaine
    ): Parcelable
}
