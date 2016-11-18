package lab.csci4100u.jocstech.lab_06_storage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

public class ShowContacts extends AppCompatActivity {

    public ArrayList<Contact> contactList;
    private ArrayAdapter<Contact> arrayAdapter;
    private ListView contactListView;
    private String filename;
    private File file;
    private int currentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contacts);

        try {
            init(); // initialize
        } catch (IOException e) {
            e.printStackTrace();
        }

        // create array adapter
        //ArrayAdapter<Contact> arrayAdapter = new ArrayAdapter<>(this , android.R.layout.simple_expandable_list_item_1 , contactList);
        arrayAdapter = new ArrayAdapter<>(this , R.layout.list_items , R.id.itemID , contactList);
        // Set The Adapter
        contactListView.setAdapter(arrayAdapter);
    }

    private void init() throws IOException {

        contactList = new ArrayList<>();
        contactListView = (ListView) findViewById(R.id.listViewContact);
        filename = "contactData.txt";
        file = new File(getApplicationContext().getFilesDir(),filename);
        file.createNewFile();
        currentID = 0;
        readDataFile();
    }


    public void process(View view){
        if(view.getId()==R.id.btnAdd){
            //When the add button clicked
            //msg("Add Button");
            Intent addItems = new Intent(this,AddContact.class);
            addItems.putExtra("id",(currentID+1));
            startActivityForResult(addItems,0);
        }
        if(view.getId()==R.id.btnDelete){
            //When the delete button clicked
            //msg("Delete Button");
            Intent deleteItems = new Intent(this, DeleteContact.class);
            deleteItems.putExtra("list",contactList);
            startActivityForResult(deleteItems,2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case 0: // add back intent
                contactList.add(strToContact(data.getStringExtra("newContact")));
                updateID();
                break;
            case 2: // delete back intent
                int index = data.getIntExtra("position",-1);
                msg("Ready to delete Position: "+String.valueOf(index));
                deleteItem(index);
                break;
            default:
                break;
        }

        updateDataFile();
    }


    public void readDataFile()
    {
        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(file);
        } catch (IOException e) {e.printStackTrace();}
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        String buffer;
        try
        {
            while ((buffer=br.readLine()) != null)
            {
                contactList.add(strToContact(buffer));
            }
        }
        catch (IOException e) {e.printStackTrace();}

        updateID();

    }

    public void updateDataFile()
    {
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {e.printStackTrace();}
        try
        {
            try
            {
                fos.write(listToString().getBytes());
            } catch (IOException e) {e.printStackTrace();}
        }
        finally
        {
            try
            {
                fos.close();
            } catch (IOException e) {e.printStackTrace();}
        }
    }

    private void updateID(){
        if(!contactList.isEmpty()){
        currentID = contactList.get(contactList.size()-1).getID();
        }
    }

    private Contact strToContact(String con){
        String data[] = con.split(",");
        return new Contact(Integer.parseInt(data[0]), data[1], data[2], data[3]);
    }

    public String listToString(){
        String data="";
        Iterator<Contact> iterator = contactList.iterator();
        while(iterator.hasNext()){ data+=iterator.next().stringType();}
        if(contactList.isEmpty())
            return "";
        else
            return data;
    }


    private void deleteItem(int id){
        contactList.remove(id);
        updateID();
        contactListView.setAdapter(arrayAdapter);
    }


    private void msg(String m){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, m, duration);
        toast.show();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            this.onStop();
            this.finish();
        }
        return false;
    }

}
