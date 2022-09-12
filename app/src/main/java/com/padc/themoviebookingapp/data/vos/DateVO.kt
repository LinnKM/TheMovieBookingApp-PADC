package com.padc.themoviebookingapp.data.vos

data class DateVO(
    val date: String,
    val day: String,
    val dayName: String,
    var isSelected: Boolean = false
)