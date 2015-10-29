package com.example.arjun.midtermexample;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        String title = (String) getIntent().getExtras().get(AppsActivity.TITLE);
        String image = (String) getIntent().getExtras().get(AppsActivity.IMAGE);
        final String url = (String) getIntent().getExtras().get(AppsActivity.APPURL);

        TextView t = (TextView) findViewById(R.id.previewtitle);
        ImageButton ib = (ImageButton) findViewById(R.id.imageButton);


        t.setText(title);
        Picasso.with(this).load(image).into(ib);

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });
    }
}
