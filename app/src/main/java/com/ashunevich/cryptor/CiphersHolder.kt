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
        // will be refactored in future --> to complicated and double  the text
        // K/V
        private val chars = hashMapOf(
            'a' to ".-", 'b' to "-...", 'c' to "-.-.",  'd' to "-..", 'e' to ".", 'f' to "..-.", 'g' to "--.", 'h' to "....", 'i' to "..",
            'j' to ".---", 'k' to "-.-", 'l' to ".-..", 'm' to "--", 'n' to "-.", 'o' to "---", 'p' to ".--.", 'q' to "--.-", 'r' to ".-.",
            's' to "...", 't' to "-", 'u' to "..-", 'v' to "...-", 'w' to ".--", 'x' to "-..-", 'y' to "-.--", 'z' to "--..",
            '0' to ".....", '1' to "-....", '2' to "--...", '3' to "---..", '4' to "----.", '5' to "-----", '6' to ".----", '7' to "..---",
             '8' to "...--", '9' to "....-", ' ' to " ", ',' to "--..--",  '.' to ".-.-.-", '?' to "..--.."
        )

        private val alpha = arrayOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
                "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
                "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8",
                "9", "0", "!", ",", "?", ".")
        private val morse = arrayOf(".-", "-...", "-.-.", "-..", ".", "..-.", "--.",
                "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.",
                "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-",
                "-.--", "--..", ".----", "..---", "...--", "....-", ".....",
                "-....", "--...", "---..", "----.", "-----", "-.-.--", "--..--", "..--..", ".-.-.-")


        fun encode(message: String): String {
            val spaceAfterEveryChar = message.replace(".".toRegex(), "$0 ") //add space after every 1 (== one dot)
            return spaceAfterEveryChar.toLowerCase(Locale.ROOT).filter { chars.containsKey(it) }
                .fold(" ") { acc, ch ->
                    acc + chars[ch]!!
                }.toString()
        }

        //Thank you u/bipidiboop for morse decoder algorithm !
        fun decode(message: String): String {
            var build = ""
            val change: String = message.trim()
            val words = change.split(" {3}".toRegex()).toTypedArray()
            for (word in words) {
                for (letter in word.split(" ".toRegex()).toTypedArray()) {
                    for (x in morse.indices) {
                        if (letter == morse[x]) build += alpha[x]
                    }
                }
                build += " "
            }
            return build
        }
    }
}



