package com.task.tvmanager.utils

import java.text.SimpleDateFormat

class Utils {

    companion object {

        private val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        private val formatter = SimpleDateFormat("yyyy, d MMMM")

        fun parse(source: String) : String{
            return formatter.format(parser.parse(source))
        }
    }
}