package com.example.contact.Data


class UserDataProvider {

    companion object {
        var userDataList: MutableList<ContactUserData> = mutableListOf<ContactUserData>()
        var userDataMap: MutableMap<String?, ContactUserData> = mutableMapOf<String?, ContactUserData>()

        private fun addData(contactUserData: ContactUserData) {
            userDataList.add(contactUserData)
            userDataMap.put(contactUserData.contactId, contactUserData)
        }

        init {
            addData(ContactUserData(null, "Santanu", "santanu.banik1996@gmail.com", "male.png", 8637828337))
            addData(ContactUserData(null, "Biki", "santanu.banik1996@gmail.com", "male.png", 8637828337))
            addData(ContactUserData(null, "Partha", "santanu.banik1996@gmail.com", "male.png", 8637828337))
            addData(ContactUserData(null, "Titu", "santanu.banik1996@gmail.com", "male.png", 8637828337))
            addData(ContactUserData(null, "Shibaji", "santanu.banik1996@gmail.com", "male.png", 8637828337))
            addData(ContactUserData(null, "Arka", "santanu.banik1996@gmail.com", "male.png", 8637828337))
            addData(ContactUserData(null, "Rimi", "santanu.banik1996@gmail.com", "female.png", 8637828337))
            addData(ContactUserData(null, "Tuki", "santanu.banik1996@gmail.com", "female.png", 8637828337))
            addData(ContactUserData(null, "Banka", "santanu.banik1996@gmail.com", "male.png", 8637828337))
            addData(ContactUserData(null, "Samar", "santanu.banik1996@gmail.com", "male.png", 8637828337))
            addData(ContactUserData(null, "Samir", "santanu.banik1996@gmail.com", "male.png", 8637828337))
            addData(ContactUserData(null, "Bappa", "santanu.banik1996@gmail.com", "male.png", 8637828337))
            addData(ContactUserData(null, "Chinmay", "santanu.banik1996@gmail.com", "male.png", 8637828337))
            addData(ContactUserData(null, "Bumba", "santanu.banik1996@gmail.com", "male.png", 8637828337))
        }
    }


}