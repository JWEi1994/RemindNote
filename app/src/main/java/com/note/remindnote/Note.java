package com.note.remindnote;

public class Note {
    private long id;
    private String title;
    private String content;
    private String time;
    private int tag;

    public Note() {
    }

    public Note(String title,String content, String time, int tag) {
        this.title = title;
        this.content = content;
        this.time = time;
        this.tag = tag;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public int getTag() {
        return tag;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return title + "\n" + time.substring(5, 16) + " " + id;
    }

    //CRUD
    public void add(Note note) {
    }
}


