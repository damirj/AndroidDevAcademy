package hr.ferit.brunozoric.taskie.di

import hr.ferit.brunozoric.taskie.persistence.Repository
import hr.ferit.brunozoric.taskie.persistence.SharedPrefsAuthorization
import hr.ferit.brunozoric.taskie.persistence.TaskPrefs
import hr.ferit.brunozoric.taskie.persistence.TaskRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<SharedPrefsAuthorization> { TaskPrefs.provideSharedPrefs() }
    factory<TaskRepository> { Repository() }
}