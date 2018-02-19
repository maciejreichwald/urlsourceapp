package com.rudearts.urlsource.database

import android.content.Context
import com.rudearts.urlsource.model.greendao.DaoMaster
import com.rudearts.urlsource.model.greendao.DaoSession

class DatabaseController constructor(context: Context){

    companion object {
        private val DATABASE_NAME = "urlsource.db"
    }

    var session:DaoSession

    init {
        val helper = DaoMaster.DevOpenHelper(context, DATABASE_NAME)
        session = DaoMaster(helper.writableDatabase).newSession()
    }
}