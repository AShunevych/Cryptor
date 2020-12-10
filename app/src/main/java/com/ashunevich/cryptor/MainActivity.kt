package com.ashunevich.cryptor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.ashunevich.cryptor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding
    private val i = 0
    var generatedKey = CiphersHolder.SubstituionCipher.generateKey();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSpinner()
        binding.shiftValue.text = i.toString();

        binding.deCryptButton.setOnClickListener {
            useDecodeCipher(binding.cryptList.selectedItem.toString())
        }
        binding.cryptButton.setOnClickListener {
            useEncodeCipher(binding.cryptList.selectedItem.toString())
        }

        binding.plusButton.setOnClickListener {plusMinusShift("+")}
        binding.minusButton.setOnClickListener {plusMinusShift("-")}
        setSpinnerListeners()
    }


    fun setSpinnerListeners(){
            binding.cryptList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    textViewAlpha();
            }

        }
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

    fun useEncodeCipher (spinnerText:String)   {
        when(spinnerText){
                    "Caesar shift" ->    binding.ouputText.setText(CiphersHolder.CaesarCipher.encode(binding.inputText.text.toString(),
                            binding.shiftValue.text.toString().toInt()))
                    "Substituion" ->   binding.ouputText.setText(CiphersHolder.SubstituionCipher.encode(binding.inputText.text.toString(),binding.keyValue.text.toString()))
        }
    }


    fun useDecodeCipher(spinnerText: String){
        when(spinnerText){
            "Caesar shift" ->    binding.inputText.setText(CiphersHolder.CaesarCipher.decode(binding.ouputText.text.toString(),
                    binding.shiftValue.text.toString().toInt()))
            "Substituion" ->    binding.inputText.setText(CiphersHolder.SubstituionCipher.decode(binding.ouputText.text.toString(),binding.keyValue.text.toString()))
        }
    }

    fun plusMinusShift(shiftType:String ){
        val num1 = binding.shiftValue.text.toString().toInt();
        when(shiftType ){
           "+" -> binding.shiftValue.text = (num1+1).toString()
            "-" -> binding.shiftValue.text = (num1-1).toString()
       }
    }






}