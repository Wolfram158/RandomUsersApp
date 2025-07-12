package ru.nda.users.utils

import android.text.SpannableStringBuilder

fun SpannableStringBuilder.appendLine(header: String, args: List<Any>, separator: String = " ") {
    append("$header: ${args.joinToString(separator = separator)}${System.lineSeparator()}")
}