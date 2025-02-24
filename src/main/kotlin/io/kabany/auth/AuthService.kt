package io.kabany.auth

import jakarta.enterprise.context.ApplicationScoped
import com.google.gson.Gson
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.interfaces.DecodedJWT
import com.bastiaanjansen.otp.TOTPGenerator
import com.bastiaanjansen.otp.HMACAlgorithm
import java.util.Base64
import java.security.NoSuchAlgorithmException
import java.security.MessageDigest
import java.math.BigInteger
import java.time.Duration

@ApplicationScoped
object AuthService {

  private val FIRST_JWT_SECRET = "ThisIsAVeryLongStringToAlignWithTheIDX10720RequirementForDotNetWebApps"
  private val gson = Gson()

  fun createJwtFromString(message: String): String {
    val algorithm = Algorithm.HMAC256(FIRST_JWT_SECRET)
    val payload = AuthJwtPayload(message)
    return JWT.create().withPayload(gson.toJson(payload)).sign(algorithm)
  }

  fun validateJwtFromToken(token: String): String {
    val algorithm = Algorithm.HMAC256(FIRST_JWT_SECRET)
    val verifier = JWT.require(algorithm).build()
    val decodedjwt = verifier.verify(token)
    val decoder = Base64.getUrlDecoder()
    val payload = String(decoder.decode(decodedjwt.payload))
    val p = gson.fromJson(payload, AuthJwtPayload::class.java)
    return p.message
  }

  fun createHash(text: String): String {
    try {
      val md = MessageDigest.getInstance("SHA-512")
      val messageDigest = md.digest(text.toByteArray())
      val no = BigInteger(1, messageDigest)
      var hashText = no.toString(16)
      while(hashText.length < 128) {
        hashText += "0$hashText"
      }
      return hashText
    } catch(e: NoSuchAlgorithmException) {
      return ""
    }
  }

  fun createTotp(text: String): String {
    val totp = TOTPGenerator.Builder(text).withHOTPGenerator{ builder ->
      builder.withAlgorithm(HMACAlgorithm.SHA512).withPasswordLength(8)
    }.withPeriod(Duration.ofSeconds(30)).build()
    return totp.now()
  }
}

data class AuthJwtPayload(val message: String)