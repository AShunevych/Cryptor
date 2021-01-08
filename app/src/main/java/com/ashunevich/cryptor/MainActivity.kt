package com.ashunevich.cryptor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.ashunevich.cryptor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        binding.keyValue.visibility = View.INVISIBLE
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
        binding.textToEncode.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) Log.d("FOCUS:","textToEncode") else binding.textToEncode.text.clear()
        }
        binding.textToDecode.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) Log.d("FOCUS:","textToDecode") else binding.textToDecode.text.clear()
        }
    }


    private fun setSpinnerListeners(){

            binding.cryptList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val value = binding.cryptList.selectedItem.toString()
                if(value == "Substitution"){
                    binding.keyValue.text = CiphersHolder.SubstituionCipher.generateKey()
                }
                else {
                    binding.keyValue.text = resources.getString(R.string.spaceVertical)
                }
                uiHandler(binding.cryptList.selectedItem.toString())
                clearView()
            }

        }
    }

    private fun clearView(){
        binding.textToDecode.setText("")
        binding.textToEncode.setText("")
    }
    //Control view change when spinner change value
    private fun uiHandler(value:String){
        when(value){
            "Substitution" ->   viewStatus(false)
            "Morse" -> viewStatus(false)
            "Caesar shift"-> viewStatus(true)
        }
    }


    private fun viewStatus (boolean: Boolean) {

        if (boolean) {
            binding.plusButton.visibility = View.VISIBLE
            binding.minusButton.visibility = View.VISIBLE
            binding.shiftValue.visibility = View.VISIBLE
            binding.keyValue.visibility = View.INVISIBLE
            binding.plusButton.isClickable = true
            binding.minusButton.isClickable = true
        } else {
            binding.keyValue.visibility = View.VISIBLE
            binding.plusButton.visibility = View.INVISIBLE
            binding.minusButton.visibility = View.INVISIBLE
            binding.shiftValue.visibility = View.INVISIBLE
            binding.plusButton.isClickable = false
            binding.minusButton.isClickable = false
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
                    "Substitution" ->   binding.textToDecode.setText(CiphersHolder.SubstituionCipher.encode(binding.textToEncode.text.toString(),binding.keyValue.text.toString()))
                    "Morse" -> binding.textToDecode.setText(CiphersHolder.MorseCipher.encode(binding.textToEncode.text.toString()))
        }

    }

    private fun decodeCipher(spinnerText: String){
        when(spinnerText){
            "Caesar shift" ->    binding.textToEncode.setText(CiphersHolder.CaesarCipher.decode
                (binding.textToDecode.text.toString(),
                    binding.shiftValue.text.toString().toInt()))
            "Substitution" ->    binding.textToEncode.setText(CiphersHolder.SubstituionCipher.decode(binding.textToDecode.text.toString(),binding.keyValue.text.toString()))
            "Morse" -> binding.textToEncode.setText(CiphersHolder.MorseCipher.decode(binding.textToDecode.text.toString()))
        }
    }
    //add plus 1 / minus 1 to key for Caesar Shift
    private fun plusMinusShift(shiftType:String ){
        val num1 = binding.shiftValue.text.toString().toInt()
        when(shiftType ){
           "+" -> binding.shiftValue.text = (num1+1).toString()
            "-" -> binding.shiftValue.text = (num1-1).toString()
       }
    }






}