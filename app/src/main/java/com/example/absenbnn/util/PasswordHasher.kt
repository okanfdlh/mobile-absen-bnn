package com.example.absenbnn.util

import java.security.SecureRandom
import java.util.Base64
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

object PasswordHasher {
    private const val Algorithm = "PBKDF2WithHmacSHA256"
    private const val Iterations = 120_000
    private const val KeyLengthBits = 256
    private const val SaltLengthBytes = 16
    private const val Prefix = "pbkdf2_sha256"

    fun hash(plainText: String): String {
        val salt = ByteArray(SaltLengthBytes)
        SecureRandom().nextBytes(salt)
        val derived = pbkdf2(plainText.toCharArray(), salt, Iterations, KeyLengthBits)
        val saltB64 = Base64.getEncoder().encodeToString(salt)
        val hashB64 = Base64.getEncoder().encodeToString(derived)
        return "$Prefix$$Iterations$$saltB64$$hashB64"
    }

    fun verify(plainText: String, encoded: String): Boolean {
        val parts = encoded.split("$")
        if (parts.size != 4) return false
        val prefix = parts[0]
        if (prefix != Prefix) return false
        val iterations = parts[1].toIntOrNull() ?: return false
        val salt = runCatching { Base64.getDecoder().decode(parts[2]) }.getOrNull() ?: return false
        val expected = runCatching { Base64.getDecoder().decode(parts[3]) }.getOrNull() ?: return false
        val actual = pbkdf2(plainText.toCharArray(), salt, iterations, expected.size * 8)
        return constantTimeEquals(actual, expected)
    }

    private fun pbkdf2(password: CharArray, salt: ByteArray, iterations: Int, keyLengthBits: Int): ByteArray {
        val spec = PBEKeySpec(password, salt, iterations, keyLengthBits)
        return SecretKeyFactory.getInstance(Algorithm).generateSecret(spec).encoded
    }

    private fun constantTimeEquals(a: ByteArray, b: ByteArray): Boolean {
        if (a.size != b.size) return false
        var result = 0
        for (i in a.indices) {
            result = result or (a[i].toInt() xor b[i].toInt())
        }
        return result == 0
    }
}
