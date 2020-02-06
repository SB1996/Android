package com.example.reactivex.data

import kotlin.random.Random

class DataProvider {
    companion object {
        fun userDataProvider() : List<UserData> {
            val userData : MutableList<UserData> = ArrayList()
            userData.add(UserData(false, Random.nextInt(),"Santanu Banik", "Banti", "santanu.banik1996@gmail.com", "8637828337"))
            userData.add(UserData(false, Random.nextInt(),"Partha Ghosal", "Situ", "partha995@gmail.com", "8987654297"))
            userData.add(UserData(false, Random.nextInt(),"Atanu Banik", "Biki", "atanu03@gmail.com", "6578828337"))
            userData.add(UserData(false, Random.nextInt(),"Souvik Mondal", "Titu", "souvik1994@gmail.com", "8637828337"))
            userData.add(UserData(false, Random.nextInt(),"Banka Halder", "Banka", "santanu.banik1996@gmail.com", "7008828337"))

            return userData
        }
    }
}