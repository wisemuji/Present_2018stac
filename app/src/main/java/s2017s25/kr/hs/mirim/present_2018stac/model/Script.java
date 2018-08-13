package s2017s25.kr.hs.mirim.present_2018stac.model;

public class Script {
    int id;
    int ScriptId;
    Long startTime;
    Long endTime;
    String content;

    public Script(Long startTime, Long endTime, String content) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.content = content;
    }

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
