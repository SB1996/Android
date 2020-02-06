package com.example.server.data

class DataRepository {
    companion object{
        var data: Map<String, String> = mapOf<String, String>(
            "Iron Man" to "2000",
            "Thor" to "1000",
            "Captain America" to "500",
            "Hulk" to "3000",
            "Spider Man" to "100",
            "Nick Fury" to "50",
            "HawkEye" to "60",
            "Black Widow" to "30"
        )
    }
}