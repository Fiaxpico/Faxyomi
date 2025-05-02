package faxyomi.domain.track.interactor

import faxyomi.domain.track.model.Track

class IsTrackUnfollowed {

    fun await(track: Track) =
        // TrackManager.MDLIST
        track.trackerId == 60L &&
            // FollowStatus.UNFOLLOWED
            track.status == 0L
}
