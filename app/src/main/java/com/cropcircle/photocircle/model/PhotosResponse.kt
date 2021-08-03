package com.cropcircle.photocircle.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PhotosResponse(
    @SerializedName("total")
    @Expose
    val total:Int = 0,
    @SerializedName("total_pages")
    @Expose
    val totalPages:Int = 0,
    @SerializedName("results")
    @Expose
    val results:List<PhotoItem> = emptyList()
    )