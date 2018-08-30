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
    boolean displayScript;
    boolean vibPhone;
    boolean vibSmartWatch;
    Long modifiedDate;
    ArrayList<Script> scripts;
    ArrayList<KeyPoint> keyPoints;

    //0                "   id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
    //1                "   name TEXT ,\n" +
    //2                "   presentTime INTEGER,\n" +
    //3                "   displayTime INTEGER,\n" +
    //4                "   displayScript INTEGER,\n" +
    //5                "   vibPhone INTEGER,\n" +
    //6                "   vibSmartWatch INTEGER\n" +
    public Presentation(int id, String name, Long presentTime, int displayTime, int displayScript, int vibPhone, int vibSmartWatch, Long modifiedDate){
        this.id = id;
        this.name = name;
        this.presentTime = presentTime;
        this.displayTime = toBoolean(displayTime);
        this.displayScript = toBoolean(displayScript);
        this.vibPhone = toBoolean(vibPhone);
        this.vibSmartWatch = toBoolean(vibSmartWatch);
        this.modifiedDate = modifiedDate;
    }

    public Presentation(String name, Long presentTime, boolean displayTime, boolean displayScript, boolean vibPhone, boolean vibSmartWatch, ArrayList<Script> scripts, ArrayList<KeyPoint> keyPoints) {

        this.name = name;
        this.presentTime = presentTime;
        this.displayTime = displayTime;
        this.displayScript = displayScript;
        this.vibPhone = vibPhone;
        this.vibSmartWatch = vibSmartWatch;
        this.scripts = scripts;
        this.keyPoints = keyPoints;
    }
    public Presentation(int id, String name, Long presentTime, Long modifiedDate) {
        this.id = id;
        this.name = name;
        this.presentTime = presentTime;
        this.modifiedDate = modifiedDate;
    }

    public Presentation() {

    }

    public Long getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Long modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPresentTime() {
        return presentTime;
    }

    public void setPresentTime(Long presentTime) {
        this.presentTime = presentTime;
    }

    public boolean isDisplayTime() {
        return displayTime;
    }

    public int isDisplayTime(int tmp) {
        if(displayTime){
            return 1;
        }
        return 0;
    }

    public void setDisplayTime(boolean displayTime) {
        this.displayTime = displayTime;
    }

    public boolean isDisplayScript() {
        return displayScript;
    }

    public int isDisplayScript(int tmp) {
        if(displayTime){
            return 1;
        }
        return 0;
    }

    public void setDisplayScript(boolean displayScript) {
        this.displayScript = displayScript;
    }

    public boolean isVibPhone() {
        return vibPhone;
    }

    public int isVibPhone(int tmp) {
        if(displayTime){
            return 1;
        }
        return 0;
    }


    public void setVibPhone(boolean vibPhone) {
        this.vibPhone = vibPhone;
    }

    public boolean isVibSmartWatch() {
        return vibSmartWatch;
    }

    public int isVibSmartWatch(int tmp) {
        if(displayTime){
            return 1;
        }
        return 0;
    }

    public void setVibSmartWatch(boolean vibSmartWatch) {
        this.vibSmartWatch = vibSmartWatch;
    }

    public ArrayList<Script> getScripts() {
        return scripts;
    }

    public void setScripts(ArrayList<Script> scripts) {
        this.scripts = scripts;
    }

    public ArrayList<KeyPoint> getKeyPoints() {
        return keyPoints;
    }

    public void setKeyPoints(ArrayList<KeyPoint> keyPoints) {
        this.keyPoints = keyPoints;
    }

    public boolean toBoolean(int i){
        return i==1;
    }
}
