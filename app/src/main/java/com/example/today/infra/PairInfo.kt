package com.example.today.infra

fun stringToPair(input: String): Pair<String, String>? {
    val parts = input.split(":", limit = 2)
    return if (parts.size == 2) {
        Pair(parts[0], parts[1])
    } else {
        null // или можно выбросить исключение
    }
}

fun pairToString(pair: Pair<String, String>): String {
    return "${pair.first}:${pair.second}"
}