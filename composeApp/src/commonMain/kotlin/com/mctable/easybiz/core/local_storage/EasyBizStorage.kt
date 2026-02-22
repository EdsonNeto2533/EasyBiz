package com.mctable.easybiz.core.local_storage

interface EasyBizStorage {
    suspend fun setString(key: String, value: String)
    suspend fun getString(key: String): String?
    suspend fun setInt(key: String, value: Int)
    suspend fun getInt(key: String): Int?
    suspend fun setBoolean(key: String, value: Boolean)
    suspend fun getBoolean(key: String): Boolean?
    suspend fun setFloat(key: String, value: Float)
    suspend fun getFloat(key: String): Float?
    suspend fun setLong(key: String, value: Long)
    suspend fun getLong(key: String): Long?
    suspend fun setDouble(key: String, value: Double)
    suspend fun getDouble(key: String): Double?
    suspend fun removeValue(key: String)
    suspend fun clear()
}