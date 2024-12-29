package com.example.smartwatchdarkmodeapp

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase


@Entity(tableName = "users")
data class Users(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nom: String,
    val prenom: String,
    val mail: String,
    val password: String
)

@Entity(tableName = "smart_watches")
data class SmartWatch(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val price: Double,
    val isWaterResistant: Boolean,
    val imageURL: String
)


@Dao
interface SmartWatchDao {
    @Insert
    fun insert(smartWatch: SmartWatch)

    @Query("SELECT * FROM smart_watches")
    fun getAllWatches(): List<SmartWatch>
}


@Dao
interface UsersDao {
    @Insert
    fun insertUser(user: Users)

    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserById(userId: Int): Users?

    @Query("SELECT * FROM users WHERE mail = :email AND password = :password")
    fun getUserByEmailAndPassword(email: String, password: String): Users?
}


@Database(entities = [Users::class, SmartWatch::class], version = 2, exportSchema = false)  // Incremented version
abstract class UsersDataBase : RoomDatabase() {
    abstract fun usersDao(): UsersDao
    abstract fun smartWatchDao(): SmartWatchDao

    companion object {
        private var INSTANCE: UsersDataBase? = null

        fun getDataBase(context: Context): UsersDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UsersDataBase::class.java,
                    "users_database"
                ).allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}