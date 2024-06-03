package org.leondorus.remill.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DbDrug::class, DbNotifGroup::class, DbNotifGroupTime::class], version = 1)
abstract class RemillDatabase: RoomDatabase() {
    abstract fun drugDao(): DbDrugDao
    abstract fun notifGroupDao(): DbNotifGroupDao

    companion object {
        @Volatile
        private var Instance: RemillDatabase? = null

        fun getDatabase(applicationContext: Context): RemillDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(applicationContext,
                    RemillDatabase::class.java, "remill_database").fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}