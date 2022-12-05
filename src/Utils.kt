import java.math.BigInteger
import java.security.MessageDigest


fun matchingGroups(regex: Regex, x: String, strict: Boolean = false): List<String> {
    val p = regex.toPattern()
    val matcher = p.matcher(x)
    if (!matcher.matches() && strict) error("$x doesn't match $regex and strict is on")
    val groupCount = matcher.groupCount()
    return (1..groupCount).map {
        matcher.group(it)
    }
}

fun allMatches(regex: Regex, x: String): List<String> {
    val p = regex.toPattern()
    val matcher = p.matcher(x)

    val xs = mutableListOf<String>()
    while (matcher.find()) {
        xs.add(matcher.group(0))
    }
    return xs
}

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')
