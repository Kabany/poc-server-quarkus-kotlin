package io.kabany.response

import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
object Response {

  fun <T>Success(data: T?, message: String?): JsonBody<T> {
    return JsonBody(data, message, true)
  }
}

data class JsonBody<T>(val data: T?, val message: String?, val success: Boolean)