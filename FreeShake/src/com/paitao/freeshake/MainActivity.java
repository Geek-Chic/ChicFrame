package com.paitao.freeshake;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.paitao.freeshake.comm.ctransview.CtransAdapter;
import com.paitao.freeshake.comm.ctransview.CtransMenu;
import com.paitao.freeshake.comm.ctransview.CtransMenuItem;
import com.paitao.freeshake.module.BaseActivity;

public class MainActivity extends BaseActivity implements OnClickListener
{
    private CtransMenu mCtransMenu;
    private CtransAdapter mCtransAdapter;
    private Button mPopupButton,mRotateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCtransMenu=(CtransMenu) findViewById(R.id.main_ctransmenu);
        ArrayList<CtransMenuItem> items=new ArrayList<CtransMenuItem>();
        items.add(new CtransMenuItem(0));
        items.add(new CtransMenuItem(1));
        items.add(new CtransMenuItem(2));
        items.add(new CtransMenuItem(3));
        items.add(new CtransMenuItem(4));
        items.add(new CtransMenuItem(5));
        items.add(new CtransMenuItem(6));
        items.add(new CtransMenuItem(7));
        items.add(new CtransMenuItem(8));
        mCtransAdapter=new CtransAdapter(this, items);
        mCtransMenu.setAdapter(mCtransAdapter);
        
        mPopupButton=(Button) findViewById(R.id.main_popup_button);
        mRotateButton=(Button) findViewById(R.id.main_rotate_button);
        mPopupButton.setOnClickListener(this);
        mRotateButton.setOnClickListener(this);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        if(v.getId()==R.id.main_popup_button){
            mCtransMenu.showDismissItem();
            
        }else if(v.getId()==R.id.main_rotate_button){
            mCtransMenu.circleItems();
        }
        
    }
    
}
