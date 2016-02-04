package ly.generalassemb.drewmahrt.shoppinglistwithdetailview;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import ly.generalassemb.drewmahrt.shoppinglistwithdetailview.setup.DBAssetHelper;

public class MainActivity extends AppCompatActivity {
    private ListView mShoppingListView;
    private CursorAdapter mCursorAdapter;
    private ShoppingSQLiteOpenHelper mHelper;
    public static final int RESULT = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ignore the two lines below, they are for setup
        DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();


        handleIntent(getIntent());
        mHelper = new ShoppingSQLiteOpenHelper(MainActivity.this);
        final Cursor cursor = mHelper.getShoppingList();
        mCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[]{ShoppingSQLiteOpenHelper.COL_ITEM_NAME}, new int[]{android.R.id.text1}, 0);


        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendItem = new Intent(MainActivity.this, AddItemActivity.class);
                startActivityForResult(sendItem, RESULT);

            }
        });

        mShoppingListView = (ListView) findViewById(R.id.shopping_list_view);
        mShoppingListView.setAdapter(mCursorAdapter);

        mShoppingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent toDetails = new Intent(MainActivity.this, DetailActivity.class);
                cursor.moveToPosition(position);
                toDetails.putExtra("id", cursor.getInt(cursor.getColumnIndex(ShoppingSQLiteOpenHelper.COL_ID)));
                startActivity(toDetails);
            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Cursor cursor = mHelper.searchShoppingList(query);
            mCursorAdapter.changeCursor(cursor);
            mCursorAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT) {
            String name;
            String description;
            String price;
            String type;
            String extra;

            Bundle bundle = data.getExtras();
            name = bundle.getString("name");
            description = bundle.getString("description");
            price = bundle.getString("price");
            type = bundle.getString("type");
            extra = bundle.getString("extra");


            ShoppingSQLiteOpenHelper db = new ShoppingSQLiteOpenHelper(this);
            db.addItem(name, description, price, type, extra);
            String query = getIntent().getStringExtra(SearchManager.QUERY);
            Cursor cursor = mHelper.searchShoppingList(query);
            mCursorAdapter.swapCursor(cursor);
            mCursorAdapter.notifyDataSetChanged();
        }
    }
}
