package org.leondorus.remill.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DbDrug::class, DbNotifGroup::class, DrugNotifGroupCrossRef::class], version = 1, exportSchema = false)
@TypeConverters(LocalDateTimeConverter::class)
abstract class RemillDatabase : RoomDatabase() {
    abstract fun drugDao(): DrugDao
    abstract fun drugNotifGroupCrossRefDao(): DrugNotifGroupCrossRefDao

    companion object {
        @Volatile
        private var Instance: RemillDatabase? = null

        fun getDatabase(context: Context): RemillDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, RemillDatabase::class.java, "remill_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
