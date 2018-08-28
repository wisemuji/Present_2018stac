package s2017s25.kr.hs.mirim.present_2018stac.Adapter;

import java.util.Date;

public class list_item {

    private String title;
    private String PTtime;
    private String PTdate;

    public list_item(String title, String PTtime, String PTdate, int option) {
        this.title = title;
        this.PTtime = PTtime;
        this.PTdate = PTdate;
        this.option = option;
    }

    private int option;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPTtime() {
        return PTtime;
    }

    public void setPTtime(String PTtime) {
        this.PTtime = PTtime;
    }

    public String getPTdate() {
        return PTdate;
    }

    public void setPTdate(String PTdate) {
        this.PTdate = PTdate;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }
}