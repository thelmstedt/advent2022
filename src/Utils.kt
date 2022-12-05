import java.math.BigInteger
import java.security.MessageDigest


fun Regex.fullMatchGroups(x: String, strict: Boolean = false): List<String> {
    val p = this.toPattern()
    val matcher = p.matcher(x)
    if (!matcher.matches() && strict) error("$x doesn't match $this and strict is on")
    val groupCount = matcher.groupCount()
    return (1..groupCount).map {
        matcher.group(it)
    }
}

fun Regex.allMatches(x: String): MutableList<String> {
    val matcher = toPattern().matcher(x)

    val xs = mutableListOf<String>()
    while (matcher.find()) {
        xs.add(matcher.group(0))
    }
    return xs
}

fun String.every(n: Int): List<String> {
    val xs = mutableListOf<String>()

    var i = 0
    while (i < this.length) {
        xs.add(this.substring(i, i + 1))
        i += n
    }
    return xs
}


/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')
