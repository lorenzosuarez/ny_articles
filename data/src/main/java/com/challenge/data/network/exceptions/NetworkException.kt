package com.challenge.data.network.exceptions

class NetworkException(
    val statusCode: Int,
    override val message: String,
    val errorBody: String? = null,
) : Exception(message)
