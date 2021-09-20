package com.cropcircle.photocircle.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cropcircle.photocircle.model.PhotoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider


abstract class PhotoDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao

    class Callback @Inject constructor(
        private val photoDatabase: Provider<PhotoDatabase>,
        private val applicationScope: CoroutineScope,
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val dao = photoDatabase.get().photoDao()
            applicationScope.launch {

            }
        }
    }
}