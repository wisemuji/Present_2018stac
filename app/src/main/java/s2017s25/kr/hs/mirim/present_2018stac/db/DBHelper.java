package s2017s25.kr.hs.mirim.present_2018stac.db;

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
                "   vibSmartWatch INTEGER,\n" +
                "   modifiedDate INTEGER\n" +
                ");");

        //KEYPOINT
        db.execSQL("CREATE TABLE KeyPoint (\n" +
                "   id INTEGER,\n" +
                "   KeyId INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "   name TEXT,\n" +
                "   vibTime INTEGER,\n" +
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
        db.execSQL("INSERT INTO presentation(name, presentTime, displayTime, displayScript, vibPhone, vibSmartWatch, modifiedDate)\n" +
                "VALUES('"+pt.getName()+"',"+pt.getPresentTime()+","+pt.isDisplayTime(1)+","+pt.isDisplayScript(1)+","+pt.isVibPhone(1)+","+pt.isVibSmartWatch(1)+","+pt.getModifiedDate()+");");
//                db.execSQL("INSERT INTO PRESENTATION VALUES(null, '" + item + "', " + price + ", '" + create_at + "');");

        int lastId = 1;
        Cursor c = db.rawQuery("SELECT last_insert_rowid();",null);
        if (c.moveToFirst()) {
            lastId = c.getInt(0);
        }
        pt.setId(lastId);

        //KEYPOINT 테이블에 추가
        for(KeyPoint kp : pt.getKeyPoints()){
            db.execSQL("INSERT INTO KeyPoint(id, name, vibTime)\n" +
                    "VALUES("+pt.getId()+",'"+kp.getName()+"',"+kp.getVibTime()+");");
        }

        //SCRIPT 테이블에 추가
        for(Script sc : pt.getScripts()){
            db.execSQL("INSERT INTO Script(id, startTime, endTime, content)\n" +
                    "VALUES("+pt.getId()+","+sc.getStartTime()+","+sc.getEndTime()+",'"+sc.getContent()+"');");
        }

        db.close();
        return lastId;
    }

    public void update(Presentation pt) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
//        db.execSQL("UPDATE PRESENTATION SET price=" + price + " WHERE item='" + item + "';");

        //PRESENTATION 테이블 수정
        db.execSQL("UPDATE presentation SET\n" +
                "name='"+pt.getName()+"', presentTime="+pt.getPresentTime()+", displayTime="+pt.isDisplayTime(1)+", displayScript="+pt.isDisplayScript(1)+", vibPhone="+pt.isVibPhone(1)+", vibSmartWatch="+pt.isVibSmartWatch(1)+", modifiedDate="+pt.getModifiedDate()+" " +
                "WHERE id="+pt.getId()+";");

        //KEYPOINT 테이블 수정
        db.execSQL("DELETE FROM KeyPoint WHERE id=" + pt.getId() + ";");
        for(KeyPoint kp : pt.getKeyPoints()){
            db.execSQL("INSERT INTO KeyPoint(id, name, vibTime)\n" +
                    "VALUES("+pt.getId()+",'"+kp.getName()+"',"+kp.getVibTime()+");");
        }

        //SCRIPT 테이블 수정
        db.execSQL("DELETE FROM Script WHERE id=" + pt.getId() + ";");
        for(Script sc : pt.getScripts()){
            db.execSQL("INSERT INTO Script(id, startTime, endTime, content)\n" +
                    "VALUES("+pt.getId()+","+sc.getStartTime()+","+sc.getEndTime()+",'"+sc.getContent()+"');");
        }

        db.close();
    }

    public void delete(Presentation pt) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM PRESENTATION WHERE id=" + pt.getId() + ";");
        db.close();
    }
    public void delete(int id) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM PRESENTATION WHERE id=" + id + ";");
        db.close();
    }

    public Presentation getPresentation(int id){
        SQLiteDatabase db = getReadableDatabase();
        Presentation pt=new Presentation();
        ArrayList<KeyPoint> keyPoints = new ArrayList<>();
        KeyPoint kp;
        ArrayList<Script> scripts = new ArrayList<>();
        Script sc;

        Cursor cursor = db.rawQuery("SELECT * FROM PRESENTATION WHERE id="+id, null);
        while (cursor.moveToNext()) {
            pt=new Presentation(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getLong(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getInt(6),
                    cursor.getLong(7)
            );
        }
        cursor = db.rawQuery("SELECT * FROM KeyPoint WHERE id="+id, null);
        while (cursor.moveToNext()) {
            kp=new KeyPoint(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getLong(3)
            );
            keyPoints.add(kp);
        }
        cursor = db.rawQuery("SELECT * FROM Script WHERE id="+id, null);
        while (cursor.moveToNext()) {
            sc=new Script(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getLong(2),
                    cursor.getLong(3),
                    cursor.getString(4)
            );
            scripts.add(sc);
        }
        pt.setKeyPoints(keyPoints);
        pt.setScripts(scripts);

        return pt;
    }

    public Presentation getPresentation(String name){
        SQLiteDatabase db = getReadableDatabase();
        Presentation pt=new Presentation();
        ArrayList<KeyPoint> keyPoints = new ArrayList<>();
        KeyPoint kp;
        ArrayList<Script> scripts = new ArrayList<>();
        Script sc;

        Cursor cursor = db.rawQuery("SELECT * FROM PRESENTATION WHERE name='"+name+"'", null);
        while (cursor.moveToNext()) {
            pt=new Presentation(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getLong(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getInt(6),
                    cursor.getLong(7)
            );
        }
        cursor = db.rawQuery("SELECT * FROM KeyPoint WHERE id="+pt.getId(), null);
        while (cursor.moveToNext()) {
            kp=new KeyPoint(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getLong(3)
            );
            keyPoints.add(kp);
        }
        cursor = db.rawQuery("SELECT * FROM Script WHERE id="+pt.getId(), null);
        while (cursor.moveToNext()) {
            sc=new Script(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getLong(2),
                    cursor.getLong(3),
                    cursor.getString(4)
            );
            scripts.add(sc);
        }
        pt.setKeyPoints(keyPoints);
        pt.setScripts(scripts);

        return pt;
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
            ptTmp=new Presentation(cursor.getInt(0),cursor.getString(1),cursor.getLong(2),cursor.getLong(7));
            ptList.add(ptTmp);
        }

        return ptList;
    }
    public boolean isDoubleExists(String name) {
        ArrayList<Presentation> ptList = this.getResult();

        for(Presentation ptTmp:ptList){
            if(ptTmp.getName().equals(name)){
                return true;
            }
        }
        return false;

    }



}
