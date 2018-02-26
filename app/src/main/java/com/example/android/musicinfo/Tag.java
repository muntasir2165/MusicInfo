package com.example.android.musicinfo;

/**
 * {@link Tag} represents a tag that is currently trending.
 * It contains a tag name, and the tag's www.last.fm webpage url.
 */
public class Tag {

    private String mTagName;
    private String mTagUrl;

    public Tag(String tagName, String tagUrl) {
        this.mTagName = tagName;
        this.mTagUrl = tagUrl;
    }

    public String getTagName() {
        return mTagName;
    }

    public String getTagUrl() {
        return mTagUrl;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "mTagName='" + mTagName + '\'' +
                ", mTagUrl='" + mTagUrl + '\'' +
                '}';
    }

}
