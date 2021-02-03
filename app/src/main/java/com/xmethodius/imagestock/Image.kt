package com.xmethodius.imagestock

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Image {
    class Image {
        @SerializedName("id")
        @Expose
        private val id: String? = null

        @SerializedName("width")
        @Expose
        private val width: Int? = null

        @SerializedName("height")
        @Expose
        private val height: Int? = null

        @SerializedName("urls")
        @Expose
        var links: Links? = null
    }
}