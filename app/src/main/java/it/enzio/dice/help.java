package it.enzio.dice;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class help extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
    }
    public void back(View v){
        Intent i = new Intent(this, rollView.class);
        i.putExtra("init", "yes");
        startActivity(i);
    }
}
