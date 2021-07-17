package ru.narod.pentiux.homeworkfordoubletapp.usecases

enum class HabitListErrors(val message: String) {
    NO_SUCH_NAME("No such habit name."),
    EXIST("Habit is already exist")
}

enum class HabitListSuccess(val message: String){
    INSERTED("Habit was successfully inserted"),
    UPDATED("Habit was successfully updated."),
    DELETED("Habit was successfully deleted")
}
