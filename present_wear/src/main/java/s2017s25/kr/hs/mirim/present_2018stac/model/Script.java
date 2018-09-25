package s2017s25.kr.hs.mirim.present_2018stac.model;

import java.io.Serializable;

public class Script implements Serializable{
    int id;
    int ScriptId;
   // String scriptTitle;
    Long startTime;
    Long endTime;
    String content;

    public Script(Long startTime, Long endTime, String content) {
       // this.scriptTitle = scriptTitle;
        this.startTime = startTime;
        this.endTime = endTime;
        this.content = content;
    }

    public Script(int id, int scriptId, Long startTime, Long endTime, String content) {
      //  this.scriptTitle = scriptTitle;
        this.id = id;
        ScriptId = scriptId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.content = content;
    }

    public Script() {
    }
/*
    public String getScriptTitle() { return scriptTitle; }

    public void setScriptTitle(String scriptTitle) { this.scriptTitle = scriptTitle; }
*/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScriptId() {
        return ScriptId;
    }

    public void setScriptId(int scriptId) {
        ScriptId = scriptId;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
