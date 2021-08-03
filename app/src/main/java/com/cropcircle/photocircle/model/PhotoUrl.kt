package com.cropcircle.photocircle.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoUrl(
    @SerializedName("full")
    @Expose
    val full: String = "", // https://images.unsplash.com/photo-1492724724894-7464c27d0ceb?crop=entropy&cs=srgb&fm=jpg&ixid=MnwyNTA0NTN8MHwxfGFsbHw1fHx8fHx8MXx8MTYyNzc5MTcwNg&ixlib=rb-1.2.1&q=85
    @SerializedName("raw")
    @Expose
    val raw: String = "", // https://images.unsplash.com/photo-1492724724894-7464c27d0ceb?ixid=MnwyNTA0NTN8MHwxfGFsbHw1fHx8fHx8MXx8MTYyNzc5MTcwNg&ixlib=rb-1.2.1
    @SerializedName("regular")
    @Expose
    val regular: String = "", // https://images.unsplash.com/photo-1492724724894-7464c27d0ceb?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwyNTA0NTN8MHwxfGFsbHw1fHx8fHx8MXx8MTYyNzc5MTcwNg&ixlib=rb-1.2.1&q=80&w=1080
    @SerializedName("small")
    @Expose
    val small: String = "", // https://images.unsplash.com/photo-1492724724894-7464c27d0ceb?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwyNTA0NTN8MHwxfGFsbHw1fHx8fHx8MXx8MTYyNzc5MTcwNg&ixlib=rb-1.2.1&q=80&w=400
    @SerializedName("thumb")
    @Expose
    val thumb: String = "" // https://images.unsplash.com/photo-1492724724894-7464c27d0ceb?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwyNTA0NTN8MHwxfGFsbHw1fHx8fHx8MXx8MTYyNzc5MTcwNg&ixlib=rb-1.2.1&q=80&w=200
) : Parcelable
