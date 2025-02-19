package io.kabany.meta

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import io.kabany.response.Response
import io.kabany.response.JsonBody
import org.eclipse.microprofile.config.inject.ConfigProperty

@Path("/meta")
class MetaResource {

  @Inject
  lateinit var response: Response

  @ConfigProperty(name = "app.version")
  lateinit var version: String

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/ping")
  fun ping(): JsonBody<Unit> {
    return response.Success<Unit>(null, "pong!")
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/health-check")
  fun healthCheck(): JsonBody<Unit> {
    return response.Success<Unit>(null, "ok!")
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/version")
  fun version(): JsonBody<Unit> {
    return response.Success<Unit>(null, version)
  }
}