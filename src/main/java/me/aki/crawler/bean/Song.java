package me.aki.crawler.bean;

import java.util.List;

/**
 * Created by Akis on 5/9/2017.
 */
public class Song {

    public enum Status {
        crawled, uncrawled
    }

    private int id;
    private String title;
    private int commentCount;
    private Status status;
    private List<Comment> commentList;

    public Song() {}

    public Song(int id, String title, Status status) {
        this.id = id;
        this.title = title;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Song song = (Song) o;

        return id == song.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
