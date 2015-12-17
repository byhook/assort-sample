package sample.byhook.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * 滑动Bar条
 */
public class SideBar extends View {
	// 触摸事件
	private OnTouchingLetterChangedListener onTouchingLetterChangedListener;

	/**
	 * 默认26个字母
	 */
	public static String[] basetext = {"#", "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L"};  //, "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V","W", "X", "Y", "Z"

	private int choose = -1;// 选中

	private Paint paint = new Paint();

	private TextView mTextDialog;

	private int first = -1;

	private int count = 0;

	public void setTextView(TextView mTextDialog) {
		this.mTextDialog = mTextDialog;
	}

	public SideBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SideBar(Context context) {
		super(context);
	}

	public void setItem(int first,int count){
        this.first = first;
		this.count = count;;
		invalidate();
	}

	public void setBasetext(String[] text){
		this.basetext = text;
		invalidate();
	}

	/**
	 * 重写这个方法
	 */
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 获取焦点改变背景颜色.
		int height = getHeight();// 获取对应高度
		int width = getWidth(); // 获取对应宽度
		int singleHeight = height / basetext.length;// 获取每一个字母的高度

		for (int i = 0; i < basetext.length; i++) {
			paint.setColor(Color.parseColor("#50FFFFFF"));
			// paint.setColor(Color.WHITE);
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setAntiAlias(true);
			paint.setTextSize(20);
			// 选中的状态
			if (i == choose) {
				paint.setColor(Color.parseColor("#123456"));
				paint.setFakeBoldText(true);
			}

			if(i>=first && i<=first+count){
				paint.setColor(Color.parseColor("#FFFFFF"));
				paint.setFakeBoldText(true);
			}
			// x坐标等于中间-字符串宽度的一半.
			float xPos = width / 2 - paint.measureText(basetext[i]) / 2;
			float yPos = singleHeight * i + singleHeight/2;
			canvas.drawText(basetext[i], xPos, yPos, paint);
			paint.reset();// 重置画笔
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float y = event.getY();// 点击y坐标
		final int oldChoose = choose;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		final int c = (int) (y / getHeight() * basetext.length);// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.

		switch (action) {
		case MotionEvent.ACTION_UP:
			setBackgroundColor(Color.parseColor("#00000000"));
			choose = -1;//
			invalidate();
			if (mTextDialog != null) {
				mTextDialog.setVisibility(View.INVISIBLE);
			}
			break;

		default:
			//setBackgroundResource(R.drawable.sidebar_background);
			setBackgroundColor(Color.parseColor("#50FFFFFF"));
			if (oldChoose != c) {
				if (c >= 0 && c < basetext.length) {
					if (listener != null) {
						listener.onTouchingLetterChanged(basetext[c]);
					}
					if (mTextDialog != null) {
						mTextDialog.setText(basetext[c]);
						mTextDialog.setVisibility(View.VISIBLE);
					}
					
					choose = c;
					invalidate();
				}
			}

			break;
		}
		return true;
	}

	/**
	 * 向外公开的方法
	 * 
	 * @param onTouchingLetterChangedListener
	 */
	public void setOnTouchingLetterChangedListener(
			OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}


	public interface OnTouchingLetterChangedListener {
		void onTouchingLetterChanged(String s);
	}

}