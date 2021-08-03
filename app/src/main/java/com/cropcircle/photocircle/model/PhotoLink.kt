package com.cropcircle.photocircle.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoLink(
    @SerializedName("download")
    @Expose
    val download: String = "", // https://unsplash.com/photos/sO-JmQj95ec/download
    @SerializedName("download_location")
    @Expose
    val downloadLocation: String = "", // https://api.unsplash.com/photos/sO-JmQj95ec/download?ixid=MnwyNTA0NTN8MHwxfGFsbHw1fHx8fHx8MXx8MTYyNzc5MTcwNg
    @SerializedName("html")
    @Expose
    val html: String = "", // https://unsplash.com/photos/sO-JmQj95ec
    @SerializedName("self")
    @Expose
    val self: String = "" // https://api.unsplash.com/photos/sO-JmQj95ec
): Parcelable
