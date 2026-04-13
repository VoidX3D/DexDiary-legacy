package com.dexdiary.data.repository

import com.dexdiary.data.database.dao.RewardDao
import com.dexdiary.data.database.dao.MissionDao
import com.dexdiary.data.database.entities.DailyReward
import com.dexdiary.data.database.entities.DailyMission
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RewardRepository @Inject constructor(
    private val rewardDao: RewardDao,
    private val missionDao: MissionDao
) {
    suspend fun getRewardForDate(date: String) = rewardDao.getRewardForDate(date)
    suspend fun insertReward(reward: DailyReward) = rewardDao.insertReward(reward)

    fun getMissionsForDate(date: String): Flow<List<DailyMission>> = missionDao.getMissionsForDate(date)
    suspend fun insertMissions(missions: List<DailyMission>) = missionDao.insertMissions(missions)
    suspend fun updateMission(mission: DailyMission) = missionDao.updateMission(mission)
}
