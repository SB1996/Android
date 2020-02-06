package com.example.mvvmarchitecture.di.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides

@Module
class FirebaseModule {

    @Provides
    fun firebaseAuthProvider() : FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun firebaseDatabaseProvider() : FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    @Provides
    fun firebaseFireStoreProvider() : FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}