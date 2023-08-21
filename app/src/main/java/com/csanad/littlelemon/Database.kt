package com.csanad.littlelemon

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MenuItemDatabase::class], version = 1, exportSchema = false)
abstract class LittleLemonDatabase : RoomDatabase() {
    abstract fun menuDao(): MenuDao

    companion object {
        @Volatile
        private var INSTANCE: LittleLemonDatabase? = null

        fun getDatabase(context: Context): LittleLemonDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LittleLemonDatabase::class.java,
                    "little_lemon_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

@Dao
interface MenuDao {
    suspend fun insertItems(list: MenuNetworkData) {
        for (item in list.menu) {
            insertItem(
                MenuItemDatabase(
                    item.id,
                    item.title,
                    item.description,
                    item.price,
                    item.image,
                    item.category
                )
            )
        }
    }

    @Insert(onConflict = REPLACE)
    suspend fun insertItem(item: MenuItemDatabase)

    @Query("SELECT * FROM menu_item_table")
    fun getItems(): LiveData<Array<MenuItemDatabase>>
}

@Entity(tableName = "menu_item_table")
data class MenuItemDatabase(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val image: String,
    val category: String
)