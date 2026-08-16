package com.leeweeder.finlog.domain.model.account

@JvmInline
internal value class AssetSymbol private constructor(val value: String) {
    companion object {
        fun of(raw: String): AssetSymbol = AssetSymbol(raw.uppercase().replace(" ", ""))

        val EMPTY = AssetSymbol("")
    }
}
