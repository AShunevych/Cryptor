package com.ashunevich.cryptor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import com.ashunevich.cryptor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding;
    private val i = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.shiftValue.text = i.toString();

        binding.deCryptButton.setOnClickListener {
            binding.inputText.setText(CaesarCipher.decrypt (binding.ouputText.text.toString(),
                binding.shiftValue.text.toString().toInt()))
        }
        binding.cryptButton.setOnClickListener {
            binding.ouputText.setText(CaesarCipher.encrypt (binding.inputText.text.toString(),
                binding.shiftValue.text.toString().toInt()))}
        binding.plusButton.setOnClickListener {plusMinusShift("add")}
        binding.minusButton.setOnClickListener {plusMinusShift("rem")}
        setSpinner()
    }

    fun setSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.cipherType,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.cryptList.adapter = adapter
        }
    }


    object CaesarCipher{
        fun encrypt(s: String, key: Int): String {
            val offset = key % 26
            if (offset == 0) return s
            var d: Char
            val chars = CharArray(s.length)
            for ((index, c) in s.withIndex()) {
                if (c in 'A'..'Z') {
                    d = c + offset
                    if (d > 'Z') d -= 26
                }
                else if (c in 'a'..'z') {
                    d = c + offset
                    if (d > 'z') d -= 26
                }
                else
                    d = c
                chars[index] = d
            }
            return chars.joinToString("")
        }

        fun decrypt(s: String, key: Int): String {
            return encrypt(s, 26 - key)
        }
    }


    fun plusMinusShift(shiftType:String ){
        val num1 = binding.shiftValue.text.toString().toInt();
        val num2 = 1;
        when(shiftType ){
           "add" -> binding.shiftValue.text = (num1+num2).toString()
            "rem" -> binding.shiftValue.text = (num1-num2).toString()
       }
    }






}