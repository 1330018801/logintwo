package com.seimun.logintwo.model;

/**
 * Created by lijun on 2/22/16.
 */

public class Education {
    private Integer item_id;
    private String title, content, create_at, create_by;

    public Education(){}

    public void setItemId(Integer id) {
        this.item_id = id;
    }

    public int getItemId() {
        return this.item_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public String getContentPreface() {
        if (this.content.length() > 30) {
            return this.content.substring(0, 30) + "...";
        } else {
            return this.content;
        }
    }

    public void setCreateAt(String create_at) {
        this.create_at = create_at;
    }

    public String getCreateAt() {
        return this.create_at;
    }

    public void setCreateBy(String create_by) {
        this.create_by = create_by;
    }

    public String getCreateBy() {
        return this.create_by;
    }
}
