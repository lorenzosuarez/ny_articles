package com.challenge.domain.model

sealed class Period(val days: Int) {
    object One : Period(days = 1)

    object Seven : Period(days = 7)

    object Thirty : Period(days = 30)

    companion object {
        val values by lazy { listOf(One, Seven, Thirty) }
    }
}
