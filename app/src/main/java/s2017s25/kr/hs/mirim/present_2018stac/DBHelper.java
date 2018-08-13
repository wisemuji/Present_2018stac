package s2017s25.kr.hs.mirim.present_2018stac;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.widget.Toast;

import java.util.ArrayList;

import s2017s25.kr.hs.mirim.present_2018stac.model.KeyPoint;
import s2017s25.kr.hs.mirim.present_2018stac.model.Presentation;
import s2017s25.kr.hs.mirim.present_2018stac.model.Script;

public class DBHelper extends SQLiteOpenHelper {

    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성

        //PRESENTATION
        db.execSQL("CREATE TABLE presentation (\n" +
                "   id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "   name TEXT ,\n" +
                "   presentTime INTEGER,\n" +
                "   displayTime INTEGER,\n" +
                "   displayScript INTEGER,\n" +
                "   vibPhone INTEGER,\n" +
                "   vibSmartWatch INTEGER\n" +
                ");");

        //KEYPOINT
        db.execSQL("CREATE TABLE KeyPoint (\n" +
                "   id INTEGER,\n" +
                "   KeyId INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "   name TEXT,\n" +
                "   vibTime INTEGER,\n" +
                "   title TEXT,\n" +
                "  FOREIGN KEY(id)\n" +
                "  REFERENCES presentation(id)\n" +
                ");");

        //SCRIPT
        db.execSQL("CREATE TABLE Script (\n" +
                "  id INTEGER,\n" +
                "  ScriptId INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  startTime INTEGER,\n" +
                "  endTime INTEGER,\n" +
                "  content TEXT,\n" +
                "  FOREIGN KEY(id)\n" +
                "  REFERENCES presentation(id)\n" +
                ");");
//        db.execSQL("CREATE TABLE PRESENTATION (_id INTEGER PRIMARY KEY AUTOINCREMENT, item TEXT, price INTEGER, create_at TEXT);");
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int insert(Presentation pt) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        //PRESENTATION 테이블에 추가
        db.execSQL("INSERT INTO presentation(name, presentTime, displayTime, displayScript, vibPhone, vibSmartWatch)\n" +
                "VALUES('"+pt.getName()+"',"+pt.getPresentTime()+","+pt.isDisplayTime(1)+","+pt.isDisplayScript(1)+","+pt.isVibPhone(1)+","+pt.isVibSmartWatch(1)+");");
//                db.execSQL("INSERT INTO PRESENTATION VALUES(null, '" + item + "', " + price + ", '" + create_at + "');");

        int lastId = 1;
        Cursor c = db.rawQuery("SELECT last_insert_rowid();",null);
        if (c.moveToFirst()) {
            lastId = c.getInt(0);
        }

        //KEYPOINT 테이블에 추가
        for(KeyPoint kp : pt.getKeyPoints()){
            db.execSQL("INSERT INTO KeyPoint(id, name, vibTime, title)\n" +
                    "VALUES("+lastId+",'"+kp.getName()+"',"+kp.getVibTime()+",'"+kp.getTitle()+"');");
        }

        //SCRIPT 테이블에 추가
        for(Script sc : pt.getScripts()){
            db.execSQL("INSERT INTO Script(id, startTime, endTime, content)\n" +
                    "VALUES("+lastId+","+sc.getStartTime()+","+sc.getEndTime()+",'"+sc.getContent()+"');");
        }

        db.close();
        return lastId;
    }

    public void update(String item, int price) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("UPDATE PRESENTATION SET price=" + price + " WHERE item='" + item + "';");
        db.close();
    }

    public void delete(String item) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM PRESENTATION WHERE item='" + item + "';");
        db.close();
    }

    public ArrayList<Presentation> getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
//        String result = "";
        ArrayList<Presentation> ptList = new ArrayList<>();
        Presentation ptTmp;

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM PRESENTATION", null);
        while (cursor.moveToNext()) {
//            result += cursor.getString(0)
//                    + " : "
//                    + cursor.getString(1)
//                    + " | "
//                    + cursor.getInt(2)
//                    + "원 "
//                    + cursor.getString(3)
//                    + "\n";
            ptTmp=new Presentation(cursor.getInt(0),cursor.getString(1),cursor.getLong(2));
            ptList.add(ptTmp);
        }

        return ptList;
    }



}
