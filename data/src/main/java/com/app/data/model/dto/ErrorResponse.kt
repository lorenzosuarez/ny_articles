package com.app.data.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val fault: Fault,
)

@Serializable
data class Fault(
    val faultstring: String,
    val detail: Detail,
)

@Serializable
data class Detail(
    val errorcode: String,
)
