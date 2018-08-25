package s2017s25.kr.hs.mirim.present_2018stac.model;

import java.io.Serializable;

public class KeyPoint implements Serializable{
    int id;
    int KeyId;
    String name;
    String title;
    Long vibTime;

    public KeyPoint(String name, String title, Long vibTime) {
        this.name = name;
        this.title = title;
        this.vibTime = vibTime;
    }
    public KeyPoint(String name,Long vibTime) {
        this.name = name;
        this.vibTime = vibTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKeyId() {
        return KeyId;
    }

    public void setKeyId(int keyId) {
        KeyId = keyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getVibTime() {
        return vibTime;
    }

    public void setVibTime(Long vibTime) {
        this.vibTime = vibTime;
    }
}
