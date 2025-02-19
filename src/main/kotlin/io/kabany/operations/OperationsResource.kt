package io.kabany.operations

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

@Path("/operations")
class OperationsResource {

  @Inject
  lateinit var response: Response
  @Inject
  lateinit var operationsService: OperationsService

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/list/params/{times}")
  fun sendListWithParams(times: Int): JsonBody<OperationListResponse> {
    val list = OperationListResponse(operationsService.createList(times))
    return response.Success<OperationListResponse>(list, null)
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/list/query")
  fun sendListWithQuery(@QueryParam("times") times: Int): JsonBody<OperationListResponse> {
    val list = OperationListResponse(operationsService.createList(times))
    return response.Success<OperationListResponse>(list, null)
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/list/body")
  fun sendListWithBody(req: OperationTimesRequest): JsonBody<OperationListResponse> {
    val list = OperationListResponse(operationsService.createList(req.times))
    return response.Success<OperationListResponse>(list, null)
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/list/headers")
  fun sendListWithHeaders(@HeaderParam("times") times: Int): JsonBody<OperationListResponse> {
    val list = OperationListResponse(operationsService.createList(times))
    return response.Success<OperationListResponse>(list, null)
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/fibonacci/sum/{times}")
  fun sendFibonacciSum(times: Int): JsonBody<OperationFibonacciSumResponse> {
    val sum = OperationFibonacciSumResponse(operationsService.fibonacciSum(times))
    return response.Success<OperationFibonacciSumResponse>(sum, null)
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/fibonacci/list/{times}")
  fun sendFibonacciList(times: Int): JsonBody<OperationFibonacciListResponse> {
    val list = OperationFibonacciListResponse(operationsService.fibonacciList(times))
    return response.Success<OperationFibonacciListResponse>(list, null)
  }
}

data class OperationListResponse(val list: List<OperationItem>)

data class OperationTimesRequest(val times: Int)

data class OperationFibonacciSumResponse(val sum: Long)

data class OperationFibonacciListResponse(val list: List<Long>)