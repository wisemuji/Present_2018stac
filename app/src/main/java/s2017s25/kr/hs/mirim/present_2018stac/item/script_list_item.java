package s2017s25.kr.hs.mirim.present_2018stac.item;

public class script_list_item {
    private String timeStr;
    private String content;

    public script_list_item(String timeStr, String content) {
        this.timeStr = timeStr;
        this.content = content;
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
}
