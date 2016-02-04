package ly.generalassemb.drewmahrt.shoppinglistwithdetailview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddItemActivity extends AppCompatActivity {
    String mItemName;
    String mDescription;
    String mPrice;
    String mType;
    String mExtra;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        final EditText addNameEditText = (EditText)findViewById(R.id.addItemNameEditText);
        final EditText addDescriptionEditText = (EditText)findViewById(R.id.addItemDescriptionEditText);
        final EditText addPriceEditText = (EditText)findViewById(R.id.addItemPriceEditText);
        final EditText addTypeEditText = (EditText)findViewById(R.id.addItemTypeEditText);
        final EditText addExtraEditText = (EditText)findViewById(R.id.addItemExtraEditText);

        Button addButton = (Button)findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent();
                mItemName = addNameEditText.getText().toString();
                mDescription = addDescriptionEditText.getText().toString();
                mPrice = addPriceEditText.getText().toString();
                mType = addTypeEditText.getText().toString();
                mExtra = addExtraEditText.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("name",mItemName);
                bundle.putString("description",mDescription);
                bundle.putString("price",mPrice);
                bundle.putString("type",mType);
                bundle.putString("extra", mExtra);
                nextIntent.putExtras(bundle);
                setResult(MainActivity.RESULT, nextIntent);
                finish();

            }
        });

        Button cancelButton = (Button)findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToHome = new Intent(AddItemActivity.this,MainActivity.class);
                startActivity(backToHome);
            }
        });


    }
}
