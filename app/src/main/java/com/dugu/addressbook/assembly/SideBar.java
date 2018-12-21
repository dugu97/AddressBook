package com.dugu.addressbook.assembly;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dugu.addressbook.R;

public class SideBar extends View {
	// 触摸事件
	private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	// 26个字母
	public static String[] b = { "#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
			"R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	private int choose = -1;// 选中
	private Paint paint = new Paint();

	private TextView mTextDialog;
	private float singleHeight;

	private int mHeight;// 获取对应高度
	private int mWidth; // 获取对应宽度

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


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// 获取宽-测量规则的模式和大小
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);

		// 获取高-测量规则的模式和大小
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		// 设置wrap_content的默认宽 / 高值
		// 默认宽/高的设定并无固定依据,根据需要灵活设置
		// 类似TextView,ImageView等针对wrap_content均在onMeasure()对设置默认宽 / 高值有特殊处理
		int mWidth = 25;  //这里设置字体默认宽度
		int mHeight = 400;

		// 当布局参数设置为wrap_content时，设置默认值
		if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
			setMeasuredDimension(mWidth, mHeight);
			// 宽 / 高任意一个布局参数为= wrap_content时，都设置默认值
		} else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
			setMeasuredDimension(mWidth, heightSize);
		} else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
			setMeasuredDimension(widthSize, mHeight);
		}
	}

	/**
	 * 重写这个方法
	 */
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 获取焦点改变背景颜色.
		mHeight = getHeight();// 获取对应高度
		mWidth = getWidth(); // 获取对应宽度

		// 获取每一个字母的高度
		singleHeight = (mHeight * 0.95f) / b.length;
		singleHeight = (mHeight * 0.95f - singleHeight / 2) / b.length;
		for (int i = 0; i < b.length; i++) {
			paint.setColor(Color.parseColor("#D9D9D9"));
			// paint.setColor(Color.WHITE);
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setAntiAlias(true);
			paint.setTextSize(25);
			// 选中的状态
			if (i == choose) {
				paint.setColor(Color.parseColor("#1EA0B0"));
				paint.setFakeBoldText(true);
			}
			// x坐标等于中间-字符串宽度的一半.
			float xPos = mWidth / 2 - paint.measureText(b[i]) / 2;
			float yPos = singleHeight * i + singleHeight;
			canvas.drawText(b[i], xPos, yPos, paint);
			paint.reset();// 重置画笔
		}

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float y = event.getY();// 点击y坐标
		final int oldChoose = choose;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		final int c = (int) (y / getHeight() * b.length);// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.

		switch (action) {
		case MotionEvent.ACTION_UP:
			setBackgroundDrawable(new ColorDrawable(0x00000000));
			choose = -1;//
			invalidate();
			if (mTextDialog != null) {
				mTextDialog.setVisibility(View.INVISIBLE);
			}
			break;
		// 除开松开事件的任何触摸事件
		default:
//			setBackgroundResource(R.drawable.sidebar_background);
			if (listener != null) {
				listener.onTouchingLetterChanged(b[c]);
			}
			if (oldChoose != c) {
				if (c >= 0 && c < b.length) {
					if (mTextDialog != null) {
						mTextDialog.setText(b[c]);
						mTextDialog.setVisibility(View.VISIBLE);
						mTextDialog.setBackground(getContext().getResources().getDrawable(R.drawable.vector_drawable_seekbar_index));
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
	public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}

	/**
	 * 接口
	 *
	 * @author coder
	 *
	 */
	public interface OnTouchingLetterChangedListener {
		public void onTouchingLetterChanged(String s);
	}

}
