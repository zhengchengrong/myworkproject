package com.eaphone.g08android.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.eaphone.g08android.R;

/**
 * 项目名称：心相随
 * 类描述：自定义recycleview的分割线
 * 创建人：zlq
 * 创建时间：2017/9/12 9:41
 * 修改人：Administrator
 * 修改时间：2017/9/12 9:41
 * 修改备注：
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private int deviderHeight = -1;
    private Paint paintDevider;

    public DividerItemDecoration(Context context) {
        paintDevider = new Paint();
        paintDevider.setColor(context.getResources().getColor(R.color.gray_deep));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
            deviderHeight = 2;
            outRect.bottom = deviderHeight;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount - 1; i++) {
            View v = parent.getChildAt(i);

            if (deviderHeight > 0) {
                float top = v.getBottom();
                float buttom = v.getBottom() + deviderHeight;
                c.drawRect(0, top, 0, buttom, paintDevider);
            }
        }
    }

}
