package csci4100.uoit.ca.lab07_database_access;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jocs on 2016-11-10.
 */

public class GradeArrayAdapter extends BaseAdapter {

    private Context context = null;
    private ArrayList<Grade> grades = null;


    public GradeArrayAdapter(Context context, ArrayList<Grade> grades) {
        this.context = context;
        this.grades = grades;
    }


    public ArrayList<Grade> getList(){
        return this.grades;
    }

    public void setList(ArrayList<Grade> newList){
        this.grades = newList;
    }

    @Override
    public int getCount() {
        return grades.size();
    }

    @Override
    public Object getItem(int position) {
        return grades.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Grade grade = grades.get(position);

        if (convertView == null) {
            // no previous view:  create a new view, based on our custom list item layout
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_grade, parent, false);
        } else {
            // previous view exists:  let's reuse it
        }

        // populate the UI elements with our data
        TextView id = (TextView)convertView.findViewById(R.id.tvStudentID);
        id.setText(String.valueOf(grade.getStudentId()));

        TextView course = (TextView)convertView.findViewById(R.id.tvCourseComponent);
        course.setText(grade.getCourseComponent());

        TextView mark = (TextView)convertView.findViewById(R.id.tvMark);
        mark.setText(String.valueOf(grade.getMark()));

        return convertView;


    }
}
