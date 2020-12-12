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
/*
    object Morse{

        val chars = mapOf('a' to ".-", 'b' to "-..." ,'c' to "-.-." , 'd' to "-..", 'e' to "." , 'f' to "..-." , 'g' to "--." , 'h' to "....",'i' to "..",
                'j' to ".---", 'k' to "-.-" , 'l' to ".-..", 'm' to "--" , 'n' to "-." , 'o' to "---" , 'p' to ".--.", 'q' to "--.-" ,
                'r' to ".-.", 's' to "...", 't' to "-", 'u' to "..-", 'v' to "...-", 'w' to ".--", 'x' to "-..-", 'y' to "-.--", 'z' to "--..",
                '1' to ".----", '2' to "..---", '3' to "...--", '4' to "....-", '5' to ".....", '6' to "-....", '7' to "--...", '8' to "---..", '9' to "----.", '0' to "-----",
                ',' to " --..--" , '.' to " .-.-.-", '?' to " ..--..")


        fun encode (valueString:String) : String{
            val s:CharArray = stringToChar(valueString)
            return chars.getValue(s.forEach {  },)
        }

        fun decode(s:String) : String{

        }

        fun stringToChar(s:String):CharArray{
            return s.toCharArray()
        }

        fun charToString(c:CharArray):String{
            return String(c)
        }


        fun <K, V> getKey(hashMap: Map<K, V>, target: V): K {
            return hashMap.filter { target == it.value }.keys.first()
        }

        fun <V, K> getValue(map: Map<K, V>, target: V): V {
            return map.filter { target == it.value }.values.first()
        }
    }
*/
}