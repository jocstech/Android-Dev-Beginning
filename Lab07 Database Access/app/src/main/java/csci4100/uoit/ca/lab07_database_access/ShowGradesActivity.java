package csci4100.uoit.ca.lab07_database_access;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;

public class ShowGradesActivity extends AppCompatActivity {

    // List in memory
    public ArrayList<Grade> GradesList;
    public GradesDBHelper dbHelper;
    public ListView gradeViewList;
    public  GradeArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_grades);

        if(initialize()){

            showGrades();

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case 0: // add back intent
                //Toast.makeText(this,data.getStringExtra("newGrade"),Toast.LENGTH_LONG).show();
                addGrade(data.getStringExtra("newGrade"));
                break;
            case 1: // delete back intent
                int index = data.getIntExtra("position",-1);
                deleteGrade(index);
                //Toast.makeText(this,"Delete Pos: "+index,Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }


    private boolean initialize(){
        GradesList = new ArrayList<>();
        dbHelper = new GradesDBHelper(this);
        syncData();

        return true;
    }

    public void addGradeActivity(View view){
        Intent intent = new Intent(getApplicationContext(),AddGradeActivity.class);
        startActivityForResult(intent,0);
    }

    public void deleteGradeActivity(View view){
        Intent intent = new Intent(getApplicationContext(),DeleteGradeActivity.class);
        intent.putExtra("list",GradesList);
        startActivityForResult(intent,1);
    }


    public void addGrade(String str){
        Log.i("     [+Add]","Add Grade: "+str);
        GradesList.add(StrToGrade(str));
        updateData(GradesList);
        updateListView();
    }

    public void deleteGrade(int index){
        Log.i("     [-Delete]","Delete Position: "+index);
        GradesList.remove(index);
        updateData(GradesList);
        updateListView();

    }

    public void updateListView(){
        adapter.setList(this.GradesList);
        adapter.notifyDataSetChanged();
        gradeViewList.invalidateViews();
        //Toast.makeText(this, "Refreshed ListView", Toast.LENGTH_SHORT).show();
    }

    public void syncData(){
        Log.i("     [List size]", String.valueOf(GradesList.size()));
        Log.i("     [DB size]",String.valueOf(dbHelper.getCounts()));

        GradesList = dbHelper.getAllGrades();
        for (int i = 0; i < GradesList.size(); i++) {
            Grade current = GradesList.get(i);
            Log.i("     [+DBSync]", current.getStudentId()+ "\t" + current.getCourseComponent()+ "\t" + current.getMark());
        }
    }

    public void updateData(ArrayList<Grade> List){
        if(GradesList.size()>0){
            dbHelper.deleteAllGrades();
        }
        Log.i("     [List size]", String.valueOf(List.size()));
        Log.i("     [DB size]",String.valueOf(dbHelper.getCounts()));

        Iterator<Grade> itr = List.iterator();

        while (itr.hasNext()) {
            Grade current = itr.next();
            Log.i("     [+DBUpdate]", current.toString());
            dbHelper.addNewGrade(current.getStudentId(), current.getCourseComponent(), current.getMark());
        }

    }


    private Grade StrToGrade(String strs){
        String[] parm = strs.split(",");
        int id = Integer.parseInt(parm[0]);
        String comp = parm[1];
        float mark = Float.parseFloat(parm[2]);
        return new Grade(id,comp,mark);
    }


    private void showGrades() {
        gradeViewList = (ListView)findViewById(R.id.listView);
        adapter = new GradeArrayAdapter(this, this.GradesList);
        gradeViewList.setAdapter(adapter);

    }

    @Override
    protected void onStart(){
        syncData();
        super.onStart();
        //Toast.makeText(this,"onStart()",Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStop(){
        updateData(GradesList);
        super.onStop();
        //Toast.makeText(this,"onStop()",Toast.LENGTH_SHORT).show();
    }

}

