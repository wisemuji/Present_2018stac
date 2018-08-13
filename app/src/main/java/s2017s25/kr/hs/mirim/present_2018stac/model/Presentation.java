package s2017s25.kr.hs.mirim.present_2018stac.model;

import java.io.Serializable;
import java.util.ArrayList;

import s2017s25.kr.hs.mirim.present_2018stac.model.Script;
import s2017s25.kr.hs.mirim.present_2018stac.model.KeyPoint;


public class Presentation implements Serializable{
    int id;
    String name;
    Long presentTime;
    boolean displayTime;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPresentTime() {
        return presentTime;
    }

    public boolean isDisplayTime() {
        return displayTime;
    }

    public boolean isDisplayScript() {
        return displayScript;
    }

    public boolean isVibPhone() {
        return vibPhone;
    }

    public boolean isVibSmartWatch() {
        return vibSmartWatch;
    }

    public ArrayList<Script> getScripts() {
        return scripts;
    }

    public ArrayList<KeyPoint> getKeyPoints() {
        return keyPoints;
    }

    boolean displayScript;
    boolean vibPhone;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPresentTime(Long presentTime) {
        this.presentTime = presentTime;
    }

    public void setDisplayTime(boolean displayTime) {
        this.displayTime = displayTime;
    }

    public void setDisplayScript(boolean displayScript) {
        this.displayScript = displayScript;
    }

    public void setVibPhone(boolean vibPhone) {
        this.vibPhone = vibPhone;
    }

    public void setVibSmartWatch(boolean vibSmartWatch) {
        this.vibSmartWatch = vibSmartWatch;
    }

    public void setScripts(ArrayList<Script> scripts) {
        this.scripts = scripts;
    }

    public void setKeyPoints(ArrayList<KeyPoint> keyPoints) {
        this.keyPoints = keyPoints;
    }

    boolean vibSmartWatch;
    ArrayList<Script> scripts;
    ArrayList<KeyPoint> keyPoints;

}
