package com.xmethodius.imagestock

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TotalResults {
    @SerializedName("total")
    @Expose
    private var total: Int? = null

    @SerializedName("total_pages")
    @Expose
    private val totalPages: Int? = null

    @SerializedName("results")
    @Expose
    private val results: List<Image?>? = null

    fun getResults(): List<Image?>? {
        return results
    }

    fun getTotal(): Int? {
        return total
    }

    fun setTotal(total: Int?) {
        this.total = total
    }
}