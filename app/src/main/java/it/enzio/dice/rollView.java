package it.enzio.dice;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Random;


public class rollView extends Activity implements View.OnClickListener, View.OnTouchListener {
	TextView number;
	ImageView lock;
	GestureDetector myG;
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	boolean locked;
	int lockNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_roll);
		ImageView img = (ImageView) findViewById(R.id.imageView);
		lock = (ImageView) findViewById(R.id.imageView2);
		number = (TextView)findViewById(R.id.textView);
		img.setOnClickListener(this);
		myG = new GestureDetector(this, new Gesture());
		img.setOnTouchListener(this);
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		this.myG.onTouchEvent(event);
		return false;
	}

	class Gesture extends GestureDetector.SimpleOnGestureListener
			implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener{

		public Gesture() {
			super();

		}

		public void onLongPress(MotionEvent ev) {
			if (locked){
				lockNumber = 1;
				locked = false;
				Log.d("","unlocked");
				lock.setBackground(getResources().getDrawable(R.drawable.unlocked));
			} else {
				lockNumber = Integer.parseInt(number.getText().toString());
				locked = true;
				lock.setBackground(getResources().getDrawable(R.drawable.locked));
				Log.d("","locked");
			}
		}

		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				return false; // Right to left
			}  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				return false; // Left to right
			}
			if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
				if (Integer.parseInt(number.getText().toString()) == 0 && !locked){
					YoYo.with(Techniques.Shake)
							.duration(700)
							.playOn(findViewById(R.id.fr));
					return false;
				}
				if (locked) {
					Random random = new Random();
					int rnd = random.nextInt(lockNumber);
					YoYo.with(Techniques.FlipOutX)
							.duration(700)
							.playOn(findViewById(R.id.textView));
					number.setText(Integer.toString(rnd));
					YoYo.with(Techniques.FlipInX)
							.duration(700)
							.playOn(findViewById(R.id.textView));
				}else{
					Random random = new Random();
					int rnd = random.nextInt(Integer.parseInt(number.getText().toString()));
					YoYo.with(Techniques.FlipOutX)
							.duration(700)
							.playOn(findViewById(R.id.textView));
					number.setText(Integer.toString(rnd));
					YoYo.with(Techniques.FlipInX)
							.duration(700)
							.playOn(findViewById(R.id.textView));
				}
				return false; // Bottom to top
			}  else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
				number.setText(Integer.toString(1));
				return false; // Top to bottom
			}
			return false;
		}

		public boolean onDown(MotionEvent e) {
			return super.onDown(e);
		}

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			Log.d("test", e.toString());
			int x = Integer.parseInt(number.getText().toString());
			x+= 5;
			number.setText(Integer.toString(x));
			return super.onDoubleTap(e);
		}

		@Override
		public boolean onDoubleTapEvent(MotionEvent e) {
			return super.onDoubleTapEvent(e);
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
				int x = Integer.parseInt(number.getText().toString());
				x += 1;
				number.setText(Integer.toString(x));
				return super.onSingleTapConfirmed(e);
		}
	}

}