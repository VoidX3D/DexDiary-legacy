package com.dexdiary.data.repository

import com.dexdiary.data.database.dao.MediaDao
import com.dexdiary.data.database.dao.LocationDao
import com.dexdiary.data.database.entities.Media
import com.dexdiary.data.database.entities.DiaryLocation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaRepository @Inject constructor(
    private val mediaDao: MediaDao,
    private val locationDao: LocationDao
) {
    fun getMediaForEntry(entryId: Long): Flow<List<Media>> = mediaDao.getMediaForEntry(entryId)

    suspend fun insertMedia(media: Media) = mediaDao.insertMedia(media)

    suspend fun deleteMediaForEntry(entryId: Long) = mediaDao.deleteMediaForEntry(entryId)

    suspend fun getLocationForEntry(entryId: Long): DiaryLocation? = locationDao.getLocationForEntry(entryId)

    suspend fun insertLocation(location: DiaryLocation) = locationDao.insertLocation(location)
}
