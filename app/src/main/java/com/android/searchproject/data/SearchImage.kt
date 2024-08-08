package com.android.searchproject.data

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("meta")
    val searchMeta: SearchMeta?,
    @SerializedName("documents")
    val searchDocuments: MutableList<DocumentsItem>?
)

data class SearchMeta(
    @SerializedName("total_count")
    val total_count: Int?,
    @SerializedName("pageable_count")
    val pageable_count: Int?,
    @SerializedName("is_end")
    val is_end: Boolean?
)

data class DocumentsItem(
    @SerializedName("collection")
    val collection: String?,
    @SerializedName("datetime")
    val datetime: String?,
    @SerializedName("display_sitename")
    val display_sitename: String?,
    @SerializedName("doc_url")
    val doc_url: String?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("image_url")
    val image_url: String?,
    @SerializedName("thumbnail_url")
    val thumbnail_url: String?,
    @SerializedName("width")
    val width: Int?
)

data class MainActivityItem(
    val thumbnail_url: String?,
    val display_sitename: String?,
    val datetime: String?
)