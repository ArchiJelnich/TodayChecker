package com.example.today.infra


fun inputCheckerText(string: String): Pair<String, Int> {
    var status = 0
    var newString: String
    val regex = Regex("\n\\s*\n")
    newString = string.replace(regex, " ")
    newString = newString.replace("*", "")


    if (newString.length>25) {
        status = 1
        newString = "Too long name!"
    }

    if (newString.isEmpty()) {
        status = 1
        newString = "Wrong string!"
    }


    return Pair(newString, status)
}
