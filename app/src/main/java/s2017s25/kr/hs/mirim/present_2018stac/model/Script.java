package s2017s25.kr.hs.mirim.present_2018stac.model;

import android.view.View;
import android.widget.Button;

import java.io.Serializable;

import s2017s25.kr.hs.mirim.present_2018stac.R;

public class Script implements Serializable, PresentationItem
{
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

    public long getStartTime() {
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


    @Override
    public void render(View view) {
        Button button = view.findViewById(R.id.btn_destroy);
        button.setText(content);
    }
}
