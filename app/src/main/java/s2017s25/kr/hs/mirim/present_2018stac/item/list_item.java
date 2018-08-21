package s2017s25.kr.hs.mirim.present_2018stac.item;

import java.util.Date;

public class list_item {

    private int start_icon;
    private String title;
    private String PTtime;
    private int menu;

    public void setStart_icon(int start_icon) {
        this.start_icon = start_icon;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public list_item(int start_icon, String title, String PTtime, int menu) {
        this.start_icon = start_icon;
        this.title = title;
        this.PTtime = PTtime;
        this.menu=menu;
    }

    public void setPTtime(String PTtime) {
        this.PTtime = PTtime;

    }

    public void setMenu(int Menu) {
        this.menu = menu;
    }

    public int getStart_icon() {

        return start_icon;
    }

    public String getTitle() {
        return title;
    }

    public String getPTtime() {
        return PTtime;
    }

    public int getMenu() {
        return menu;
    }
}
