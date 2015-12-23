package info.dyndns.jasonperkins.bunnyrangler;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import db.PracticeDatabaseHelper;
import models.Bunny;
import nl.qbusict.cupboard.QueryResultIterable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class MainActivity extends AppCompatActivity {

    static SQLiteDatabase db;

    EditText etBunnyName;
    Button btnAdd;
    ListView lvBunnies;

    ArrayAdapter<String> bunnyAdapter;
    ArrayList<Bunny> bunnyArray;
    ArrayList<String> bunnyNameArray;

    final SwipeDetector swipey = new SwipeDetector();   // Get something to detect swipes - JTP 12/22/2015 1:34 pm
    private static final String logTag = "Main Activity"; // Log tag for this activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init views
        etBunnyName = (EditText) findViewById(R.id.edit_text);
        btnAdd = (Button) findViewById(R.id.add_bunny);
        lvBunnies = (ListView) findViewById(R.id.listView);

        lvBunnies.setOnTouchListener(swipey); // Make sure we set a touch listener

        // setup database
        PracticeDatabaseHelper dbHelper = new PracticeDatabaseHelper(this);
        dbHelper.onUpgrade(dbHelper.getWritableDatabase(), 1, 2);
        db = dbHelper.getWritableDatabase();

        // here is where you associate the name array.
        bunnyNameArray = getAllBunniesNames();

        // Updated to be based on String template - JTP 12/22/2015 11:39 am
        bunnyAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_2, android.R.id.text1, bunnyNameArray) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                String txt1 = getResources().getString(R.string.bunny_display_text_part)+bunnyArray.get(position).getName();        // Moving concat to single lines
                String txt2 = "It is a "+bunnyArray.get(position).getCutenessTypeEnum()+" bunny";// JTP 12/22/2015 11:42 am
                text1.setText(txt1);
                text2.setText(txt2);
                return view;
            }
        };

        // clicking this button adds a new bunny with your chosen name to the database
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = etBunnyName.getText().toString();
                if (!s.isEmpty()) {
                    Bunny b = new Bunny(s);
                    cupboard().withDatabase(db).put(b);
                    bunnyArray.add(b);
                    bunnyAdapter.add(b.getName());
                    bunnyAdapter.notifyDataSetChanged();
                    // empty the edit text
                    etBunnyName.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), "no empty bunnies", Toast.LENGTH_SHORT).show();
                }

            }
        });

        lvBunnies.setAdapter(bunnyAdapter);

        // long clicking on a list item will remove the bunny from the list AND from the db
        lvBunnies.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Bunny b = bunnyArray.get(pos);  // Let's get our bunny
                SwipeDetector.Action act = swipey.getAction();
                Log.i(logTag, "Item Clicker Long -- Swiped -- " + String.valueOf(act));
                switch(act){
                    case LR:
                        b.makeCuter(); // Let's make him cuter
                        break;
                    case RL:
                        b.makeUglier(); // Let's make him uglier
                        break;
                    case TB:
                        break;
                    case BT:
                        break;
                    case None:
                        break;
                }
                // Now let's finish and update the view and the database
                cupboard().withDatabase(db).put(b);
                bunnyAdapter.notifyDataSetChanged();

                if(act == SwipeDetector.Action.None){
                    cupboard().withDatabase(db).delete(Bunny.class, b.get_id());
                    bunnyArray.remove(pos);
                    bunnyNameArray.remove(pos);
                    bunnyAdapter.notifyDataSetChanged();
                }

               return false;
            }
        });

        //clicking on the bunny updates their cuteness value to VERYCUTE and persists that change
        // Removing this version to add a swipe right left to set cuteness - JTP 12/22/2015 12:59 pm
        /*
        lvBunnies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                // as an example of updating a value, and persisting it back to the db
                Bunny b = bunnyArray.get(pos);
                b.setCuteValue(85);
                b.setCutenessTypeEnum(Bunny.cutenessType.VERYCUTE);
                cupboard().withDatabase(db).put(b);
                bunnyAdapter.notifyDataSetChanged();
            }
        });
        */


        lvBunnies.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Bunny b = bunnyArray.get(pos);  // Let's get our bunny
                SwipeDetector.Action act = swipey.getAction();
                Log.i(logTag, "Item Clicker Short -- Swiped -- " + String.valueOf(act));
                switch(act){
                    case LR:
                        b.makeCuter(); // Let's make him cuter
                        break;
                    case RL:
                        b.makeUglier(); // Let's make him uglier
                        break;
                    case TB:
                        break;
                    case BT:
                        break;
                    case None:
                        break;
                }
                // Now let's finish and update the view and the database
                cupboard().withDatabase(db).put(b);
                bunnyAdapter.notifyDataSetChanged();
            }
        });

        getListOfVeryCuteBunniesNamedSteve();

    }

    // Create the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    // Create the actions once the menu has been selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.kill_bunnies:
                cupboard().withDatabase(db).delete(Bunny.class,"_id != NULL");
                bunnyArray.clear();
                bunnyNameArray.clear();
                bunnyAdapter.notifyDataSetChanged();
                break;
            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }

     /* Private Methods */

    private static List<Bunny> getListFromQueryResultIterator(QueryResultIterable<Bunny> iter) {

        final List<Bunny> bunnies = new ArrayList<Bunny>();
        for (Bunny bunny : iter) {
            bunnies.add(bunny);
        }
        iter.close();

        return bunnies;
    }

    public ArrayList<String> getAllBunniesNames() {
        final QueryResultIterable<Bunny> iter = cupboard().withDatabase(db).query(Bunny.class).query();
        bunnyArray = (ArrayList<Bunny>) getListFromQueryResultIterator(iter);

        ArrayList<String> bunnyNameArray = new ArrayList<String>();
        for (Bunny b : bunnyArray) {
            bunnyNameArray.add(b.getName());
        }

        return bunnyNameArray;
    }

    // this is an example method demonstrating how one would query cupboard for a specific subset
    // of objects. In this case, very cute bunnies named steve.
    public static List<Bunny> getListOfVeryCuteBunniesNamedSteve() {
        String selectionString = "name='steve' AND cuteValue > 66";
//        Long time = Calendar.getInstance().getTimeInMillis();
        QueryResultIterable<Bunny> iterable =
                cupboard().withDatabase(db).query(Bunny.class).withSelection(selectionString).query();
        List<Bunny> list = getListFromQueryResultIterator(iterable);
        return list;
    }
}
