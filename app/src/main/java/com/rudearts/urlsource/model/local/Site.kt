package com.rudearts.urlsource.model.local

data class Site(val url:String="", val source:String="") {

    override fun toString(): String {
        return url
    }
}