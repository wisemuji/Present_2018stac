package s2017s25.kr.hs.mirim.present_2018stac.model;

import java.util.ArrayList;

import s2017s25.kr.hs.mirim.present_2018stac.model.Script;
import s2017s25.kr.hs.mirim.present_2018stac.model.KeyPoint;


public class Presentation {
    int id;
    String name;
    Long presentTime;
    boolean displayTime;
    boolean displayScript;
    boolean vibPhone;
    boolean vibSmartWatch;
    ArrayList<Script> scripts;
    ArrayList<KeyPoint> keyPoints;

}
