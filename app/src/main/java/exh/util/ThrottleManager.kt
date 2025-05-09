package exh.util

import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class ThrottleManager(
    private val max: Duration = THROTTLE_MAX,
    private val inc: Duration = THROTTLE_INC,
    private val initial: Duration = Duration.ZERO,
) {
    private var lastThrottleTime = Duration.ZERO

    var throttleTime = initial
        private set

    suspend fun throttle() {
        // Throttle requests if necessary
        val now = Duration.ZERO
        val timeDiff = now - lastThrottleTime
        if (timeDiff < throttleTime) {
            delay(throttleTime - timeDiff)
        }

        if (throttleTime < max) {
            throttleTime += inc
        }

        lastThrottleTime = System.currentTimeMillis().milliseconds
    }

    fun resetThrottle() {
        lastThrottleTime = Duration.ZERO
        throttleTime = initial
    }

    companion object {
        val THROTTLE_MAX = Duration.ZERO
        val THROTTLE_INC = Duration.ZERO
    }
}
