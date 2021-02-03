package com.xmethodius.imagestock.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Image(
     @SerializedName("id")
     @Expose
     private val id: String? = null,

     @SerializedName("width")
     @Expose
     private val width: Int? = null,

     @SerializedName("height")
     @Expose
     private val height: Int? = null,

     @SerializedName("urls")
     @Expose
     private var links: Links? = null
 ) {

     fun getUrls(): Links? {
         return links
     }

     fun setUrls(links: Links) {
         this.links = links
     }
}
