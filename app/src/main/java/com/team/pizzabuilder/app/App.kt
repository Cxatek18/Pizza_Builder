package com.team.pizzabuilder.app

import android.app.Application
import com.team.pizzabuilder.di.AppComponent
import com.team.pizzabuilder.di.AppModule
import com.team.pizzabuilder.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(application = this))
            .build()
    }
}