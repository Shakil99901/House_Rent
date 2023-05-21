package com.ghorami.rongpencill.barivara;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AgentSettingDetails extends AppCompatActivity {

    String Topics;

    TextView tvHead, tvDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agent_setting_details);

        boolean isNetworkAvailable = Utils.isnetworkekAvable(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);

        Intent i = getIntent();
        Topics = i.getStringExtra("Topics");

        tvHead = (TextView)findViewById(R.id.tvHead);
        tvDetails = (TextView)findViewById(R.id.tvDetails);



        if(Topics.equals("privacy")){
            tvDetails.setText(R.string.privacy);
            tvHead.setText("Privacy Policy");
        } else if (Topics.equals("about")){
            tvDetails.setText(R.string.aboutus);
            tvHead.setText("About Us");
        } else if (Topics.equals("terms")){
            tvDetails.setText(R.string.terms);
            tvHead.setText("Terms & Condition");
        }

        initInstancesDrawer();


    }
    private void initInstancesDrawer() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolh = (TextView)findViewById(R.id.tool_header);
        toolh.setText(Topics);
        ImageView proPic = (ImageView)findViewById(R.id.userImage);

        proPic.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));



        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            //  getSupportActionBar().setHomeButtonEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


       // CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);


    }



    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            //onBackHome();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }
}