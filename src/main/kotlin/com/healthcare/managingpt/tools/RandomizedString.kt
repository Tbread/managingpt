package com.healthcare.managingpt.tools

class RandomizedString {

    fun randomAlphabetNumber(wishLength:Int): String{

        val charPool: List<Char> = ('a'..'z')+('A'..'Z')+('0'..'9')

        return (6..wishLength)
            .map { kotlin.random.Random.nextInt(0,charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }
}