package com.team.pizzabuilder.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppModule(val application: Application) {

    @Provides
    fun provideAppContext(): Context {
        return application.applicationContext
    }
}