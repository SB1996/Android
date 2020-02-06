package com.example.daggerdependenceyinjection.data.component.wheelSubComponent

class Tire {
    
    private val TAG: String = Tire::class.java.simpleName

    lateinit var trieCompanies: String
    var trieType: String = "Normal Trie"
    var trieSize: String = "Default Trie"
    var isTrieReady: Boolean = false

    internal fun setTireCompanies(type: String){
        trieCompanies = when(type) {
            "MRF Trie" -> { "MRF Trie" }
            "CEAT Trie" -> { "CEAT Trie" }
            else -> { "Normal Companies Trie" }
        }
    }

    internal fun setTrieType(type: String){
        trieType = when(type) {
            "Normal Trie" -> { "Normal Trie" }
            "TubeLess Trie" -> { "TubeLess Trie" }
            else -> { "Normal Trie" }
        }
    }

    internal fun setTrieSize(type: String){
        trieSize = when(type) {
            "Small Trie" -> { "Small Trie" }
            "Normal Trie" -> { "Normal Trie" }
            "Big Trie" -> { "Big Trie" }
            "Very Big Trie" -> { "Very Big Trie" }
            else -> { "Default Trie" }
        }
    }

    internal fun doReadyTrie() : Boolean{
        this.isTrieReady = true

        return isTrieReady
    }
}