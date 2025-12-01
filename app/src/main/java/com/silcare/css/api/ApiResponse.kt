package com.silcare.css.api

import com.google.gson.annotations.SerializedName

data class CloudinaryResponse(
    @SerializedName("secure_url") val secure_url: String
)