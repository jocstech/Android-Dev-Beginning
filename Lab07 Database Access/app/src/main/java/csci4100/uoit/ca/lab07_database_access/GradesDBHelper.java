package csci4100.uoit.ca.lab07_database_access;

/**
 * Created by jocs on 2016-11-10.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class GradesDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_FILENAME = "Grades.db";

    private static final String CREATE_STATEMENT = "" +
            "CREATE TABLE IF NOT EXISTS Grades("+
                "studentId int primary key,"+
                "courseComponent varchar(100) not null,"+
                "mark decimal not null)";

    private static final String DROP_STATEMENT = "" +
            "drop table Grades";

    public GradesDBHelper(Context context) {
        super(context, DATABASE_FILENAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_STATEMENT);
        //db.execSQL("alter table contacts add column address text");
        //db.execSQL("update contacts set address = ''");
        db.execSQL(CREATE_STATEMENT);
    }

    public int getCounts(){
        String countQuery = "SELECT * FROM Grades";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public void deleteAllGrades() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Grades", "", new String[] {});
        /*
        db.delete("contacts", "lastName = ?", new String[] {searchFor});

        // WARNING:  Do not use this method (SQL injection)
        db.delete("contacts", "phone = '"+searchFor+"'", new String[] {});
        */
    }


    public void deleteGradeById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Grades", "studentId = ?", new String[] { ""+id });
    }

    public Grade addNewGrade(int studentId, String courseComponent, float mark) {
        // insert the contact data into the database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("studentId", studentId);
        values.put("courseComponent", courseComponent);
        values.put("mark", mark);

        db.insert("Grades", null, values);

        // create a new contact object
        Grade grade = new Grade(studentId, courseComponent, mark);
        return grade;
    }

    public ArrayList<Grade> getAllGrades() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Grade> results = new ArrayList<>();

        String[] columns = new String[] {
                "studentId",
                "courseComponent",
                "mark"};
        String where = "";  // all Grades
        String[] whereArgs = new String[] {};
        String groupBy = "";  // no grouping
        String groupArgs = "";
        String orderBy = "";

        Cursor cursor = db.query("Grades", columns, where, whereArgs,
                groupBy, groupArgs, orderBy);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            int studentId = cursor.getInt(0);
            String courseComponent = cursor.getString(1);
            float mark = cursor.getFloat(2);

            results.add(new Grade(studentId,courseComponent,mark ));

            cursor.moveToNext();
        }

        return results;
    }

    public boolean updateGrade(Grade grade) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("studentId", grade.getStudentId());
        values.put("courseComponent", grade.getCourseComponent());
        values.put("mark", grade.getMark());


        int numRows = db.update("Grades",
                values,
                "studentId = ?",
                new String[] {""+grade.getStudentId()});
        return (numRows == 1);
    }

}
