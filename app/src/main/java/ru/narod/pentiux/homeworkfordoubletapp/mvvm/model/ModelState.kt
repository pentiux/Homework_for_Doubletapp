package ru.narod.pentiux.homeworkfordoubletapp.mvvm.model

sealed class ModelState
class Success(message: String) : ModelState()
class Error(errorMessage: String) : ModelState()
class StringData(string: String) : ModelState()
