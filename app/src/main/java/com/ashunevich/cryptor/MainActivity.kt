package com.ashunevich.cryptor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import com.ashunevich.cryptor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding
    var generatedKey = CiphersHolder.SubstituionCipher.generateKey();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSpinner()
        binding.shiftValue.text = "0"

        binding.deCryptButton.setOnClickListener {
            decodeCipher(binding.cryptList.selectedItem.toString())

        }
        binding.cryptButton.setOnClickListener {
            encodeCipher(binding.cryptList.selectedItem.toString())

        }

        setFocusChangeListeners()

        binding.plusButton.setOnClickListener {plusMinusShift("+")}
        binding.minusButton.setOnClickListener {plusMinusShift("-")}
        setSpinnerListeners()
    }


    private fun setFocusChangeListeners(){
        binding.textToEncode.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) Log.d("FOCUS:","textToEncode") else binding.textToEncode.text.clear()
        }
        binding.textToDecode.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) Log.d("FOCUS:","textToDecode") else binding.textToDecode.text.clear()
        }
    }


    private fun setSpinnerListeners(){
            binding.cryptList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    textViewAlpha();
                    clearView()
            }

        }
    }

    private fun clearView(){
        binding.textToDecode.setText("")
        binding.textToEncode.setText("")
    }

    private fun clearTextAndSetNew(editText: EditText) {
        editText.text.clear()
    }

    fun textViewAlpha(){
        val spinnerValue = binding.cryptList.selectedItem.toString()
       if (spinnerValue == "Caesar shift"){
           binding.keyValue.alpha = 0.0F
           binding.keyValue.text = ""
           binding.shiftValue.isEnabled=true
       }
        if (spinnerValue == "Substituion"){
            binding.keyValue.alpha = 1.0F
            binding.keyValue.text = generatedKey
            binding.shiftValue.isEnabled=false
        }

    }


    private fun setSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.cipherType,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.cryptList.adapter = adapter
        }
    }

    private fun encodeCipher (spinnerText:String)   {
        when(spinnerText){
                    "Caesar shift" ->    binding.textToDecode.setText(CiphersHolder.CaesarCipher.encode
                        (binding.textToEncode.text.toString(),
                            binding.shiftValue.text.toString().toInt()))
                    "Substituion" ->   binding.textToDecode.setText(CiphersHolder.SubstituionCipher.encode(binding.textToEncode.text.toString(),binding.keyValue.text.toString()))
                    "Morse Code" -> binding.textToDecode.setText(CiphersHolder.MorseCipher.encode(binding.textToEncode.text.toString()))
        }

    }


    private fun decodeCipher(spinnerText: String){
        when(spinnerText){
            "Caesar shift" ->    binding.textToEncode.setText(CiphersHolder.CaesarCipher.decode
                (binding.textToDecode.text.toString(),
                    binding.shiftValue.text.toString().toInt()))
            "Substituion" ->    binding.textToEncode.setText(CiphersHolder.SubstituionCipher.decode(binding.textToDecode.text.toString(),binding.keyValue.text.toString()))
        }

    }

    private fun plusMinusShift(shiftType:String ){
        val num1 = binding.shiftValue.text.toString().toInt();
        when(shiftType ){
           "+" -> binding.shiftValue.text = (num1+1).toString()
            "-" -> binding.shiftValue.text = (num1-1).toString()
       }
    }






}