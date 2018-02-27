package com.example.android.musicinfo;

/**
 * {@link Artist} represents an artist who is currently trending.
 * It contains an artist name, the artist's www.last.fm webpage url, and an image for that artist.
 */
public class Artist {

    private String mArtistName;
    private String mArtistUrl;
    private String mArtistImageUrl;

    public Artist(String artistName, String artistUrl, String artistImageUrl) {
        this.mArtistName = artistName;
        this.mArtistUrl = artistUrl;
        this.mArtistImageUrl = artistImageUrl;
    }

    public String getArtistName() {
        return mArtistName;
    }

    public String getArtistUrl() {
        return mArtistUrl;
    }

    public String getImageUrl() {
        return mArtistImageUrl;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "mArtistName='" + mArtistName + '\'' +
                ", mArtistUrl='" + mArtistUrl + '\'' +
                ", mArtistImageUrl='" + mArtistImageUrl + '\'' +
                '}';
    }

}
