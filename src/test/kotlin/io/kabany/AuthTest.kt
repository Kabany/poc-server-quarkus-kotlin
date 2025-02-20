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
    Assertions.assertEquals("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJtZXNzYWdlIjoiSGVsbG8gV29ybGQhIn0.IuiP9G4uZRbreQi4qWaZFlZMnvEtHig0AmYx-8NT7Q4", token);
  }

  @Test
  fun shouldCreateJwtThenDecodeAndMustMatchWithOriginalMessage() {
    val message = "Hello World!";
    val token = authService.createJwtFromString(message);
    val decoded = authService.validateJwtFromToken(token);
    Assertions.assertEquals(message, decoded);

    // From Ruby
    val decoded2 = authService.validateJwtFromToken("eyJhbGciOiJIUzI1NiJ9.eyJtZXNzYWdlIjoiSGVsbG8gV29ybGQhIn0.kiLVWiCroYBS-sgSmTP_u74OmiLt_l3UeUBGfM-lmE8");
    Assertions.assertEquals(message, decoded2);
    // From Swift
    val decoded3 = authService.validateJwtFromToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtZXNzYWdlIjoiSGVsbG8gV29ybGQhIn0.mahh4kwbMrH_-7Np_G88KLJmkW5GtFYzbLiWAfpRiJQ");
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