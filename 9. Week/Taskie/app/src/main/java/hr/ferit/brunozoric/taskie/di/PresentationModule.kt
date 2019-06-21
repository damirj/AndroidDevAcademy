package hr.ferit.brunozoric.taskie.di

import hr.ferit.brunozoric.taskie.presentation.*
import hr.ferit.brunozoric.taskie.ui.login.LoginActivityContract
import hr.ferit.brunozoric.taskie.ui.register.RegisterActivityContract
import hr.ferit.brunozoric.taskie.ui.taskdetails.fragment.EditTaskFragmentDialogContract
import hr.ferit.brunozoric.taskie.ui.taskdetails.fragment.TaskDetailsFragmentContract
import hr.ferit.brunozoric.taskie.ui.tasklist.fragment.AddTaskFragmentContract
import hr.ferit.brunozoric.taskie.ui.tasklist.fragment.TasksFragmentContract
import org.koin.dsl.module


val presentationModule = module {
    factory<LoginActivityContract.Presenter> { LoginActivityPresenter(get(), get()) }
    factory<RegisterActivityContract.Presenter> { RegisterActivityPresenter(get()) }
    factory<TasksFragmentContract.Presenter> { TasksFragmentPresenter(get(), get()) }
    factory<AddTaskFragmentContract.Presenter> { AddTaskFragmentPresenter(get(), get()) }
    factory<TaskDetailsFragmentContract.Presenter> { TaskDetailsFragmentPresenter(get(), get()) }
    factory<EditTaskFragmentDialogContract.Presenter> { EditTaskFragmentDialogPresenter(get()) }

}