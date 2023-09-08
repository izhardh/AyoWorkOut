package com.ayoworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.ayoworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FinishActivity : AppCompatActivity() {

    private var binding: ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarFinishExercise)
        if(supportActionBar!= null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarFinishExercise?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btFinish?.setOnClickListener {
            finish()
        }

        val dao = (application as WoktOutApp).db.historyDao()
        addDateToDatabase(dao)
    }

    private fun addDateToDatabase(historyDao: HistoryDao){
        val kalender = Calendar.getInstance()
        val dateTime = kalender.time
        Log.e("Date: ", "" + dateTime)

        val dateFormat = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val tanggal = dateFormat.format(dateTime)
        Log.e("Date: ", "" + tanggal )

        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(tanggal))
            Log.e("Date: ", "Added", )
        }
    }

}