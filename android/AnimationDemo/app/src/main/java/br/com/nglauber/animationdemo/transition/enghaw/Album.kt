package br.com.nglauber.animationdemo.transition.enghaw

import com.google.gson.annotations.SerializedName
import org.parceler.Parcel

@Parcel
data class Album(
        @SerializedName("titulo") var title: String = "",
        @SerializedName("capa") var cover: String = "",
        @SerializedName("capa_big") var coverBig: String = "",
        @SerializedName("ano") var year: Int = 0,
        @SerializedName("gravadora") var recordCompany: String = "",
        @SerializedName("formacao") var formation: List<String> = emptyList(),
        @SerializedName("faixas") var tracks: List<String> = emptyList())
