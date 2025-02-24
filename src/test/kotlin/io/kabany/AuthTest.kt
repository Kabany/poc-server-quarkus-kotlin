package io.kabany

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.kabany.operations.OperationsService
import io.kabany.auth.AuthService
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import jakarta.inject.Inject

@QuarkusTest
class AuthTest {

  @Inject
  lateinit var authService: AuthService

  @Test
  fun shouldCreateJwtFromString() {
    val message = "Hello World!";
    val token = authService.createJwtFromString(message);
    Assertions.assertEquals("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJtZXNzYWdlIjoiSGVsbG8gV29ybGQhIn0.q_OAmeTO_BJ0X1_6SMzGEat8qHvcMZ6LtPhLg8yKXR4", token);
  }

  @Test
  fun shouldCreateJwtThenDecodeAndMustMatchWithOriginalMessage() {
    val message = "Hello World!";
    val token = authService.createJwtFromString(message);
    val decoded = authService.validateJwtFromToken(token);
    Assertions.assertEquals(message, decoded);
    
    // From Ruby & Python
    val decoded2 = authService.validateJwtFromToken("eyJhbGciOiJIUzI1NiJ9.eyJtZXNzYWdlIjoiSGVsbG8gV29ybGQhIn0.yX3llK_oxmp-qhJ7l-B0AL8wOlzCzsDHlw7xtCU2d4s");
    Assertions.assertEquals(message, decoded2);
    // From Swift
    val decoded3 = authService.validateJwtFromToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtZXNzYWdlIjoiSGVsbG8gV29ybGQhIn0.Qn62lWxZ5VZKovUbE8KTu_xGeDSp739uapAuBDK360Y");
    Assertions.assertEquals(message, decoded3);
  }

  @Test
  fun shouldCreateHashWithSha512Algorithm() {
    val message = "Hello World!";
    val hash = authService.createHash(message);
    Assertions.assertEquals("861844d6704e8573fec34d967e20bcfef3d424cf48be04e6dc08f2bd58c729743371015ead891cc3cf1c9d34b49264b510751b1ff9e537937bc46b5d6ff4ecc8", hash);
  }

  @Test
  fun shouldCreateTotp() {
    val message = "JBSWY3DPEHPK3PXP";
    val token = authService.createTotp(message);
    val token2 = authService.createTotp(message);
    Assertions.assertEquals(token, token2);
  }
}