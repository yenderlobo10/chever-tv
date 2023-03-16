package io.chever.domain.enums

enum class GenderEnum(
    val value: Int
) {

    Woman(1),
    Man(2),
    Other(0);

    companion object {

        fun fromValue(value: Int): GenderEnum = when (value) {
            1 -> Woman
            2 -> Man
            else -> Other
        }
    }
}