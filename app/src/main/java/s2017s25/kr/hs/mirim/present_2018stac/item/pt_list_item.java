package s2017s25.kr.hs.mirim.present_2018stac.item;

import java.util.Date;

public class pt_list_item {

    private String title;
    private String PTtime;
    private String PTdate;
    private int option;
    private int PTedit;
    private int PTplay;
    private int PTdelete;

    public pt_list_item(String title, String PTtime, String PTdate, int option, int PTedit, int PTplay, int PTdelete) {
        this.title = title;
        this.PTtime = PTtime;
        this.PTdate = PTdate;
        this.option = option;
        this.PTedit = PTedit;
        this.PTplay = PTplay;
        this.PTdelete = PTdelete;
    }

    public int getPTedit() {
        return PTedit;
    }

    public void setPTedit(int PTedit) {
        this.PTedit = PTedit;
    }

    public int getPTplay() {
        return PTplay;
    }

    public void setPTplay(int PTplay) {
        this.PTplay = PTplay;
    }

    public int getPTdelete() {
        return PTdelete;
    }

    public void setPTdelete(int PTdelete) {
        this.PTdelete = PTdelete;
    }

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
