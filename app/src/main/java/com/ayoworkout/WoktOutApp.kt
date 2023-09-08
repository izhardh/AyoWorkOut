package com.ayoworkout

import android.app.Application

class WoktOutApp: Application() {
    val db by lazy {
        HistoryDatabase.getInstance(this)
    }
}