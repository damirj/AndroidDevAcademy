package hr.ferit.brunozoric.taskie.di

import hr.ferit.brunozoric.taskie.networking.interactors.TaskieInteractor
import hr.ferit.brunozoric.taskie.networking.interactors.TaskieInteractorImpl
import hr.ferit.brunozoric.taskie.persistence.SharedPrefsAuthorization
import hr.ferit.brunozoric.taskie.persistence.TaskPrefs
import org.koin.dsl.module


val domainModule = module {
    factory<TaskieInteractor> { TaskieInteractorImpl(get()) }
}

