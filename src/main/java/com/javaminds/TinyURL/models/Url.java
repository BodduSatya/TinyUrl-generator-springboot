package com.javaminds.TinyURL.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="GENERATE_SHORTURL")
public class Url {
    @Id
    @Column(name="ID")
    @GenericGenerator(name = "system-uuid", strategy = "guid")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name="SHORTURL")
    private String shortUrl;

    @Column(name="URL")
    private String url;

    @Column(name="CREATED")
    private LocalDateTime created;

    @Column(name="EXPIRYDATE")
    private LocalDateTime expiryDate;

    public Url() {
    }

    public Url(String id, String shortUrl, String url, LocalDateTime created) {
        this.id = id;
        this.shortUrl = shortUrl;
        this.url = url;
        this.created = created;
    }

    public Url(String id, String shortUrl, String url, LocalDateTime created, LocalDateTime expiryDate) {
        this.id = id;
        this.shortUrl = shortUrl;
        this.url = url;
        this.created = created;
        this.expiryDate = expiryDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
