package org.leondorus.remill

import android.app.Application

class RemillApplication: Application() {
    lateinit var container: RemillContainer

    override fun onCreate() {
        super.onCreate()
        container = AndroidRemillContainer(this)
    }
}
