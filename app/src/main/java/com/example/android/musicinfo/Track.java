package com.example.android.musicinfo;

/**
 * {@link Track} represents a music track that is currently trending.
 * It contains a track name, an artist name, and an image for that track.
 */
public class Track {

    private String mTrackName;
    private String mArtistName;
    private String mArtistImageUrl;


    public Track(String trackName, String artistName, String artistImageUrl) {
        this.mTrackName = trackName;
        this.mArtistName = artistName;
        this.mArtistImageUrl = artistImageUrl;
    }

    public String getTrackName() {
        return mTrackName;
    }

    public String getArtistName() {
        return mArtistName;
    }

    public String getImageUrl() {
        return mArtistImageUrl;
    }

    @Override
    public String toString() {
        return "Track{" +
                "mTrackName='" + mTrackName + '\'' +
                ", mArtistName='" + mArtistName + '\'' +
                ", mArtistImageUrl='" + mArtistImageUrl + '\'' +
                '}';
    }
}
