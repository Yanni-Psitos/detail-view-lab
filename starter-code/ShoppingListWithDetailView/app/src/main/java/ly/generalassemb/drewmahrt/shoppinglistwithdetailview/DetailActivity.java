package ly.generalassemb.drewmahrt.shoppinglistwithdetailview;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    TextView mItemName, mDescription, mPrice, mType, mExtra;
    ShoppingSQLiteOpenHelper mHelper;
    Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mHelper = new ShoppingSQLiteOpenHelper(this);


        mItemName = (TextView)findViewById(R.id.nameTextView);
        mDescription = (TextView)findViewById(R.id.descriptionTextView);
        mPrice = (TextView)findViewById(R.id.priceTextView);
        mType = (TextView)findViewById(R.id.typeTextView);
        mExtra = (TextView)findViewById(R.id.extraTextView);



        int dataId = getIntent().getIntExtra("id",-1);

        if(dataId>=0){
            mCursor = mHelper.searchShoppingListId(dataId);
            mCursor.moveToFirst();
            mItemName.setText(mCursor.getString(mCursor.getColumnIndex(ShoppingSQLiteOpenHelper.COL_ITEM_NAME)));
            mDescription.setText(mCursor.getString(mCursor.getColumnIndex(ShoppingSQLiteOpenHelper.COL_ITEM_DESCRIPTION)));
            mPrice.setText(mCursor.getString(mCursor.getColumnIndex(ShoppingSQLiteOpenHelper.COL_ITEM_PRICE)));
            mType.setText(mCursor.getString(mCursor.getColumnIndex(ShoppingSQLiteOpenHelper.COL_ITEM_TYPE)));
            mExtra.setText(mCursor.getString(mCursor.getColumnIndex(ShoppingSQLiteOpenHelper.COL_ITEM_EXTRADETAIL)));

        }

    }
}
