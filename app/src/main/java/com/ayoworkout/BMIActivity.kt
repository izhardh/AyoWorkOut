package com.ayoworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ayoworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    companion object{
        private const val Satuan_Metric_Active = "METRIC_UNIT_VIEW"
        private const val Satuan_US_Active = "US_UNIT_View"
    }

    private var satuanYangTerlihat: String = Satuan_Metric_Active
    private var binding: ActivityBmiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.tbBMI)


        controlSatuanMetric()
        binding?.rgSatuan?.setOnCheckedChangeListener { _, checkedId: Int ->
            if(checkedId == R.id.rbSatuanMetric){
                controlSatuanMetric()
            }else{
                controlSatuanUS()
            }
        }

        if(supportActionBar!= null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Calculator BMI"
        }

        binding?.tbBMI?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btHasilBMI?.setOnClickListener {
            calculateSatuan()
        }
    }

    private fun controlSatuanMetric(){
        satuanYangTerlihat = Satuan_Metric_Active
        binding?.tilInputBerat?.visibility = View.VISIBLE
        binding?.tilInputTinggi?.visibility = View.VISIBLE
        binding?.tilSatuanUSFeet?.visibility = View.GONE
        binding?.tilSatuanUSInch?.visibility = View.GONE
        binding?.tilInputUSBerat?.visibility = View.GONE

        binding?.etBeratBadan?.text!!.clear()
        binding?.etTinggiBadan?.text!!.clear()

        binding?.llHasilBMI?.visibility = View.INVISIBLE
    }

    private fun controlSatuanUS(){
        satuanYangTerlihat = Satuan_US_Active
        binding?.tilInputBerat?.visibility = View.INVISIBLE
        binding?.tilInputTinggi?.visibility = View.INVISIBLE
        binding?.tilSatuanUSFeet?.visibility = View.VISIBLE
        binding?.tilSatuanUSInch?.visibility = View.VISIBLE
        binding?.tilInputUSBerat?.visibility = View.VISIBLE

        binding?.etUSBeratBadan?.text!!.clear()
        binding?.etSatuanUSPanjangFeet?.text!!.clear()
        binding?.etSatuanUSPanjangInch?.text!!.clear()

        binding?.llHasilBMI?.visibility = View.INVISIBLE
    }

    private fun hasilBMI(bmi: Float){
        val bmiLabel: String
        val bmiDescription: String

        if(bmi.compareTo(15f) <= 0){
            bmiLabel = "Kategori : Kurus Berbahaya"
            bmiDescription = "Mulai Perbaiki Diri, Perbanyak Makan!!"
        }else if(bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0){
            bmiLabel = "Kategori : Sangat Kurus"
            bmiDescription = "Mulai Perbaiki Diri, Perbanyak Makan!!"
        }else if(bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0){
            bmiLabel = "Kategori : Kurus"
            bmiDescription = "Mulai Perbaiki Diri, Perbanyak Makan!!"
        }else if(bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0){
            bmiLabel = "Kategori : Normal"
            bmiDescription = "Selamat!, Kamu Ideal"
        }else if(bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0){
            bmiLabel = "Kategori : Overweight"
            bmiDescription = "Mulai Perbaiki Diri, Perbanyak Workout, Jaga Asupan Makan"
        }else if(bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0){
            bmiLabel = "Kategori : Obesitas 1 Medium(Obesitas)"
            bmiDescription = "Mulai Perbaiki Diri, Perbanyak Workout, Jaga Asupan Makan"
        }else if(bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0){
            bmiLabel = "Kategori : Obesitas 2 Severly(Obesitas)"
            bmiDescription = "Bahaya!, Kamu Harus Mulai Bergerak"
        }else {
            bmiLabel = "Kategori : Obesitas 3 Very Severly(Obesitas)"
            bmiDescription = "Bahaya!, Kamu Harus Mulai Bergerak"
        }

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.llHasilBMI?.visibility = View.VISIBLE
        binding?.tvNilaiBMI?.text = bmiValue
        binding?.tvTipeBMI?.text = bmiLabel
        binding?.tvDeskripsiBMI?.text = bmiDescription
    }

    private fun checkNilai():Boolean{
        var isValid = true

        if(binding?.etBeratBadan?.text.toString().isEmpty()){
            isValid = false
        }else if(binding?.etTinggiBadan?.text.toString().isEmpty()){
            isValid = false
        }
        return isValid
    }

    private fun checkNilaiUS():Boolean{
        var isValid = true

        when{
            binding?.etUSBeratBadan?.text.toString().isEmpty() ->
            {isValid = false}
            binding?.etSatuanUSPanjangFeet?.text.toString().isEmpty() ->
            {isValid = false}
            binding?.etSatuanUSPanjangInch?.text.toString().isEmpty() ->
            {isValid = false}
        }
        return isValid
    }

    private fun calculateSatuan(){
        if(satuanYangTerlihat == Satuan_Metric_Active){
            if(checkNilai()){
                val nilaiBerat : Float = binding?.etBeratBadan?.text.toString().toFloat()
                val nilaiTinggi : Float = binding?.etTinggiBadan?.text.toString().toFloat()/ 100
                val bmi = nilaiBerat / (nilaiTinggi*nilaiTinggi)
                hasilBMI(bmi)

            }else{
                Toast.makeText(this,"Masukkan dengan nilai yang benar", Toast.LENGTH_SHORT).show()
            }
        }else{
            if(checkNilaiUS()){
                val nilaiTinggiUSFeet: String = binding?.etSatuanUSPanjangFeet?.text.toString()
                val nilaiTinggiUSInch: String = binding?.etSatuanUSPanjangInch?.text.toString()
                val nilaiBeratUS: Float = binding?.etUSBeratBadan?.text.toString().toFloat()

                val nilaiTinggiUS = nilaiTinggiUSFeet.toFloat() + nilaiTinggiUSInch.toFloat() * 12
                val bmiUS =  703 * (nilaiBeratUS / (nilaiTinggiUS * nilaiTinggiUS))

                hasilBMI(bmiUS)
            }else{
                Toast.makeText(this,"Masukkan dengan nilai yang benar", Toast.LENGTH_SHORT).show()
            }
        }
    }


}