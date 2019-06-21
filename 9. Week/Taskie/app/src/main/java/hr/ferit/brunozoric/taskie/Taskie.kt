package hr.ferit.brunozoric.taskie

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import hr.ferit.brunozoric.taskie.di.domainModule
import hr.ferit.brunozoric.taskie.di.networkingModule
import hr.ferit.brunozoric.taskie.di.presentationModule
import hr.ferit.brunozoric.taskie.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Taskie: Application() {

    companion object {
        lateinit var instance: Taskie
            private set


        fun getAppContext(): Context = instance.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        startKoin {
            modules(listOf(networkingModule, presentationModule, domainModule, repositoryModule))
            androidContext(this@Taskie)
        }
    }
}