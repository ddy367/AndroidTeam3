package com.bitc;

import android.view.View;

public class H_BoardItem {

    private String id;
    private String title;
    private String contents;
    private String name;
    private int visible;

    public H_BoardItem() {

    }

    public H_BoardItem(String id, String title, String contents, String name, int visible) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.name = name;
        this.visible = visible;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getName() {
        return name;
    }

    public void setName(String contents) {
        this.name = name;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
