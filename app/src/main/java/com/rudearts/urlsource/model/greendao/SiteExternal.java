package com.rudearts.urlsource.model.greendao;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

/**
 * Entity mapped to table "site".
 */
@Entity(nameInDb = "site")
public class SiteExternal {

    @Id(autoincrement = true)
    private Long id;
    private String url;
    private String source;

    @Generated
    public SiteExternal() {
    }

    public SiteExternal(Long id) {
        this.id = id;
    }

    @Generated
    public SiteExternal(Long id, String url, String source) {
        this.id = id;
        this.url = url;
        this.source = source;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
