package com.ashunevich.cryptor

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
            return chars.joinToString("")
        }

        fun decode(s: String, key: Int): String {
            return decode(s, 26 - key)
        }
    }

    object SubstituionCipher {

        fun encode(s: String, key : String): String {
            val sb = StringBuilder()
                for (c in s) sb.append(key[c.toInt() - 32])
            return sb.toString()
        }

        fun decode(s: String, key : String): String {
            val sb = StringBuilder()
            for (c in s) sb.append((key.indexOf(c) + 32).toChar())
        return sb.toString()
    }

        fun generateKey () :String {
            return "]kYV}(!7P\$n5_0i R:?jOWtF/=-pe'AD&@r6%ZXs\"v*N[#wSl9zq2^+g;LoB`aGh{3.HIu4fbK)mU8|dMET><,Qc\\C1yxJ"
        }

    }
}