package com.mctable.easybiz.core.local_storage

import platform.Foundation.NSUserDefaults

class IosEasyBizStorage : EasyBizStorage {

    private val userDefaults = NSUserDefaults.standardUserDefaults

    // ---------- STRING ----------

    override suspend fun setString(key: String, value: String) {
        userDefaults.setObject(value, forKey = key)
    }

    override suspend fun getString(key: String): String? {
        return userDefaults.stringForKey(key)
    }

    // ---------- INT ----------

    override suspend fun setInt(key: String, value: Int) {
        userDefaults.setInteger(value.toLong(), forKey = key)
    }

    override suspend fun getInt(key: String): Int? {
        return if (userDefaults.objectForKey(key) != null) {
            userDefaults.integerForKey(key).toInt()
        } else null
    }

    override suspend fun setBoolean(key: String, value: Boolean) {
        userDefaults.setBool(value, forKey = key)
    }

    override suspend fun getBoolean(key: String): Boolean? {
        return if (userDefaults.objectForKey(key) != null) {
            userDefaults.boolForKey(key)
        } else null
    }

    override suspend fun setFloat(key: String, value: Float) {
        userDefaults.setDouble(value.toDouble(), forKey = key)
    }

    override suspend fun getFloat(key: String): Float? {
        return if (userDefaults.objectForKey(key) != null) {
            userDefaults.doubleForKey(key).toFloat()
        } else null
    }

    // ---------- LONG ----------

    override suspend fun setLong(key: String, value: Long) {
        userDefaults.setDouble(value.toDouble(), forKey = key)
    }

    override suspend fun getLong(key: String): Long? {
        return if (userDefaults.objectForKey(key) != null) {
            userDefaults.doubleForKey(key).toLong()
        } else null
    }

    // ---------- DOUBLE ----------

    override suspend fun setDouble(key: String, value: Double) {
        userDefaults.setDouble(value, forKey = key)
    }

    override suspend fun getDouble(key: String): Double? {
        return if (userDefaults.objectForKey(key) != null) {
            userDefaults.doubleForKey(key)
        } else null
    }

    // ---------- REMOVE / CLEAR ----------

    override suspend fun removeValue(key: String) {
        userDefaults.removeObjectForKey(key)
    }

    override suspend fun clear() {
        val dictionary = userDefaults.dictionaryRepresentation()
        dictionary.keys.forEach {
            userDefaults.removeObjectForKey(it as String)
        }
    }
}