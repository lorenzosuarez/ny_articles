package com.challenge.presentation.utils

/**
 * Converts a string into a list of non-empty strings, based on a delimiter, or returns null if the resulting list is empty.
 *
 * @param delimiter The delimiter used to split the string. By default, it is a comma.
 * @return A list of non-empty strings or null if no non-empty strings can be generated.
 */
fun String.toNonEmptyListOrNull(delimiter: String = ","): List<String>? {
    return this.split(delimiter)
        .filterNot(String::isBlank)
        .takeIf(List<String>::isNotEmpty)
}
