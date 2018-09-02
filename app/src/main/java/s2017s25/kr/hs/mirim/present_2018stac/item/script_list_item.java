package s2017s25.kr.hs.mirim.present_2018stac.item;

public class script_list_item {
    private String timeStr;
    private String content;
   // private  String script_title;

    public script_list_item(String timeStr, String content) {
        this.timeStr = timeStr;
        this.content = content;
       // this.script_title = script_title;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

   /* public String getScript_title() { return script_title; }

    public void setScript_title(String script_title) { this.script_title = script_title; }
*/

}
