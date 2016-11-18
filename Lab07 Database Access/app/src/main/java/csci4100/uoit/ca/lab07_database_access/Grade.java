package csci4100.uoit.ca.lab07_database_access;

import java.io.Serializable;

/**
 * Created by jocs on 2016-11-10.
 */

public class Grade implements Serializable {
    public int studentId;
    public String courseComponent;
    public float mark;

    public Grade() {
        studentId = -1;
        courseComponent = null;
        mark = -1;
    }
    public Grade( int a , String b , float c) {
        studentId = a;
        courseComponent = b;
        mark = c;
    }
    public void setMark( float m ) {
        this.mark = m;
    }
    public void setStudentId( int id ) {
        this.studentId = id;
    }
    public void setCourseComponent( String str ) {
        this.courseComponent = str;
    }
    public int getStudentId() {
        return studentId;
    }
    public String getCourseComponent() {
        return courseComponent;
    }
    public float getMark() {
        return mark;
    }

    public String toString(){
        return "Student ID: "+getStudentId()+"\nCourse Component: "+getCourseComponent()+" Mark: "+getMark();
    }
}
