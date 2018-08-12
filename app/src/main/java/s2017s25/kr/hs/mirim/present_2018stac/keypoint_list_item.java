package s2017s25.kr.hs.mirim.present_2018stac;

public class keypoint_list_item {
    private String title;
    private String vibTime;
//    private int viblen;


    public keypoint_list_item(String title, String vibTime) {
        this.title = title;
        this.vibTime = vibTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVibTime() {
        return vibTime;
    }

    public void setVibTime(String vibTime) {
        this.vibTime = vibTime;
    }
}
