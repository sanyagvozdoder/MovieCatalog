package com.example.testxml.presentation.activities.sign_up_activity.util

enum class Month(val value: String) {
    января("01"),
    февраля("02"),
    марта("03"),
    апреля("04"),
    мая("05"),
    июня("06"),
    июля("07"),
    августа("08"),
    сентября("09"),
    октября("10"),
    ноября("11"),
    декабря("12");

    companion object{
        fun convertToString(month:String):String{
            return values().find {
                it.name.toString() == month
            }?.value.toString()
        }
    }
}
