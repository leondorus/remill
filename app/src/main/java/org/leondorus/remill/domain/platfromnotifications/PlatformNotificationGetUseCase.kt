package org.leondorus.remill.domain.platfromnotifications

import kotlinx.coroutines.flow.Flow
import org.leondorus.remill.domain.model.PlatformNotification
import org.leondorus.remill.domain.model.PlatformNotificationId
import java.time.LocalDateTime

class PlatformNotificationGetUseCase(private val platformNotificationGetRepo: PlatformNotificationGetRepo) {
    fun getPlatformNotification(id: PlatformNotificationId): Flow<PlatformNotification?> {
        return platformNotificationGetRepo.getPlatformNotification(id)
    }

    fun getAllPlatformNotification(): Flow<List<PlatformNotification>> {
        return platformNotificationGetRepo.getAllPlatformNotification()
    }

    fun getPlatformNotificationByTime(localDateTime: LocalDateTime): Flow<PlatformNotification?> {
        return platformNotificationGetRepo.getPlatformNotificationByTime(localDateTime)
    }
}