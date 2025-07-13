package ru.nda.users.utils

fun Appendable.appendLine(header: String, args: List<Any>, separator: String = " ") {
    append("$header: ${args.joinToString(separator = separator)}${System.lineSeparator()}")
}