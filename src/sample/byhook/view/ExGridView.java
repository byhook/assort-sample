package sample.byhook.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 扩展方格控件
 */
public class ExGridView extends GridView {

	public ExGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}

	public ExGridView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public ExGridView(Context context) {
		this(context,null);
	}


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
