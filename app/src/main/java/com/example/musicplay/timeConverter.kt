package com.example.musicplay

fun timeConverter(milis: Int): String{
    var sec = milis/1000
    var min = sec/60
    sec -= min*60

    val time: String = (if(min<10){"0$min"}else{"$min"}) + ":${if(sec<10){"0$sec"}else{"$sec"}}"


    return time
}