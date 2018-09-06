package s2017s25.kr.hs.mirim.present_2018stac.model;

import java.io.Serializable;

public class  KeyPoint implements Serializable{
    int id;
    int KeyId;
    String name;
    Long vibTime;

    public KeyPoint(String name,Long vibTime) {
        this.name = name;
        this.vibTime = vibTime;
    }

    public KeyPoint() {
    }

    public KeyPoint(int id, int keyId, String name, Long vibTime) {
        this.id = id;
        KeyId = keyId;
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

    public Long getVibTime() {
        return vibTime;
    }

    public void setVibTime(Long vibTime) {
        this.vibTime = vibTime;
    }
}
