package com.example.contact.Data

open class ContactUserData() {

    var contactId: String? = null
        get() = field
        set(value) {field = value}

    var name: String? = null
        get() = field
        set(value) {field = value}

    var email: String? = null
        get() = field
        set(value) {field = value}

    var profileImg: String? = null
        get() = field
        set(value) {field = value}

    var contactNumber: Long? = null
        get() = field
        set(value) {field = value}

    init {


    }
    constructor(_contactId:String?, _name: String, _email: String, _profileImg: String, _contactNumber: Long?) : this() {
        this.contactId = _contactId
        this.name = _name
        this.email = _email
        this.profileImg = _profileImg
        this.contactNumber = _contactNumber
    }

}