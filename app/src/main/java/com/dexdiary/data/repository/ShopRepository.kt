package com.dexdiary.data.repository

import com.dexdiary.data.database.dao.InventoryDao
import com.dexdiary.data.database.dao.ThemeDao
import com.dexdiary.data.database.dao.PowerUpDao
import com.dexdiary.data.database.entities.InventoryItem
import com.dexdiary.data.database.entities.UnlockedTheme
import com.dexdiary.data.database.entities.ActivePowerUp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShopRepository @Inject constructor(
    private val inventoryDao: InventoryDao,
    private val themeDao: ThemeDao,
    private val powerUpDao: PowerUpDao
) {
    fun getInventory(): Flow<List<InventoryItem>> = inventoryDao.getInventory()

    suspend fun getItem(itemId: String): InventoryItem? = inventoryDao.getItem(itemId)

    suspend fun purchaseItem(item: InventoryItem) = inventoryDao.insertItem(item)

    suspend fun updateItem(item: InventoryItem) = inventoryDao.updateItem(item)

    fun getUnlockedThemes(): Flow<List<UnlockedTheme>> = themeDao.getUnlockedThemes()

    suspend fun unlockTheme(theme: UnlockedTheme) = themeDao.unlockTheme(theme)

    fun getActivePowerUps(currentTime: Long): Flow<List<ActivePowerUp>> = powerUpDao.getActivePowerUps(currentTime)

    suspend fun activatePowerUp(powerUp: ActivePowerUp) = powerUpDao.activatePowerUp(powerUp)
}
