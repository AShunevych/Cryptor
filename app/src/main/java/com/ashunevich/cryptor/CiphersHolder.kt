package com.ashunevich.cryptor

import android.util.Log
import java.util.*


abstract class CiphersHolder {
    object CaesarCipher {
        fun encode(s: String, key: Int): String {
            val offset = key % 26
            if (offset == 0) return s
            var d: Char
            val chars = CharArray(s.length)
            for ((index, c) in s.withIndex()) {
                if (c in 'A'..'Z') {
                    d = c + offset
                    if (d > 'Z') d -= 26
                } else if (c in 'a'..'z') {
                    d = c + offset
                    if (d > 'z') d -= 26
                } else
                    d = c
                chars[index] = d
            }
            Log.d("Encoded Value ", chars.joinToString(""))
            return chars.joinToString("")
        }

        fun decode(s: String, key: Int): String {
            Log.d("Decoded Value  ", encode(s, 26 - key))
            return encode(s, 26 - key)
        }
    }

    object SubstituionCipher {

        fun encode(s: String, key: String): String {
            val sb = StringBuilder()
            for (c in s) sb.append(key[c.toInt() - 32])
            return sb.toString()
        }

        fun decode(s: String, key: String): String {
            val sb = StringBuilder()
            for (c in s) sb.append((key.indexOf(c) + 32).toChar())
            return sb.toString()
        }

        fun generateKey(): String {
            return "]kYV}(!7P\$n5_0i R:?jOWtF/=-pe'AD&@r6%ZXs\"v*N[#wSl9zq2^+g;LoB`aGh{3.HIu4fbK)mU8|dMET><,Qc\\C1yxJ"
        }

    }

    object MorseCipher {
        private val EnglishAlphabet = arrayOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
                "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
                "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8",
                "9", "0", "!", ",", "?", ".")
        private val MorseAlphabet = arrayOf(".-", "-...", "-.-.", "-..", ".", "..-.", "--.",
                "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.",
                "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-",
                "-.--", "--..", ".----", "..---", "...--", "....-", ".....",
                "-....", "--...", "---..", "----.", "-----", "-.-.--", "--..--", "..--..", ".-.-.-")


        fun encode(message: String): String {
          return useArray(message.replace(".".toRegex(), "$0 ").toLowerCase(Locale.ROOT),
                  EnglishAlphabet, MorseAlphabet,false)
        }


        //Thank you u/bipidiboop for morse decoder algorithm !
        fun decode(message: String): String {
            return useArray(message, MorseAlphabet, EnglishAlphabet,true)
        }


        //not ideal , should be replaced, need to think and  test
       private fun useArray(message: String, arrayToEncode: Array<String>, arrayToDecode: Array<String>,decode: Boolean): String {
            var build = ""
            val change: String = message.trim()
            if (decode){
                val words = change.split(" {3}".toRegex()).toTypedArray()
                for (word in words) {
                    for (letter in word.split(" ".toRegex()).toTypedArray()) {
                        for (x in arrayToEncode.indices) {
                            if (letter == arrayToEncode[x]) build += arrayToDecode[x]
                        }
                    }
                    build += " "
                }
            }
            else{
                val words = change.split(" ".toRegex()).toTypedArray()
                for (word in words) {
                    for (letter in word.split(" ".toRegex()).toTypedArray()) {
                        for (x in arrayToEncode.indices) {
                            if (letter == arrayToEncode[x]) build += arrayToDecode[x]
                        }
                    }
                    build += " "
                }
            }
            return build
        }
    }
}



