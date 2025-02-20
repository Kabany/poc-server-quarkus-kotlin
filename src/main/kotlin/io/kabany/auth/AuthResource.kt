package io.kabany.auth

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.QueryParam
import jakarta.ws.rs.POST
import jakarta.ws.rs.HeaderParam
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import io.kabany.response.Response
import io.kabany.response.JsonBody
import org.eclipse.microprofile.config.inject.ConfigProperty

@Path("/auth")
class AuthResource {

  @Inject
  lateinit var response: Response
  @Inject
  lateinit var authService: AuthService

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/jwt/string")
  fun sendJwtToken(req: AuthMessageRequest): JsonBody<AuthTokenResponse> {
    val body = AuthTokenResponse(authService.createJwtFromString(req.message))
    return response.Success<AuthTokenResponse>(body, null)
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/hash/string")
  fun sendHash(req: AuthMessageRequest): JsonBody<AuthHashResponse> {
    val body = AuthHashResponse(authService.createHash(req.message))
    return response.Success<AuthHashResponse>(body, null)
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/totp/string")
  fun sendTotpCode(req: AuthMessageRequest): JsonBody<AuthCodeResponse> {
    val body = AuthCodeResponse(authService.createTotp(req.message))
    return response.Success<AuthCodeResponse>(body, null)
  }
}

data class AuthMessageRequest(val message: String)

data class AuthTokenResponse(val token: String)

data class AuthHashResponse(val hash: String)

data class AuthCodeResponse(val code: String)