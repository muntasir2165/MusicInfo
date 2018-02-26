package com.example.android.musicinfo;

/**
 * {@link Artist} represents an artist who is currently trending.
 * It contains an artist name, the artist's www.last.fm webpage url, and an image for that artist.
 */
public class Artist {

    private String mArtistName;
    private String mArtistUrl;
    private String mImageUrl;

    public Artist(String artistName, String artistUrl, String imageUrl) {
        this.mArtistName = artistName;
        this.mArtistUrl = artistUrl;
        this.mImageUrl = imageUrl;
    }

    public String getArtistName() {
        return mArtistName;
    }

    public String getArtistUrl() {
        return mArtistUrl;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "mArtistName='" + mArtistName + '\'' +
                ", mArtistUrl='" + mArtistUrl + '\'' +
                ", mImageUrl='" + mImageUrl + '\'' +
                '}';
    }

}
