package com.chooblarin.kusa;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class KusaView extends View {

    private static final String TAG = KusaView.class.getSimpleName();

    private static String[] MONTHS
            = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private static String[] DAY_OF_WEEKS = {"S", "M", "T", "W", "T", "F", "S"};

    final int year;
    final int month;
    final int day;
    final int dayOfWeek;

    private int width;
    private int height;
    private Rect rect;
    private Paint textPaint;
    private Paint paint;

    private int cellSize;
    private int space;
    private int monthsLabelHeight;

    private int cellHorizontalCount;

    private List<ChartData> chartDataList = new ArrayList<>();

    // Options
    private boolean dayOfWeekLabelVisibility = true;

    @ColorInt
    private int color;

    public KusaView(Context context) {
        this(context, null);
    }

    public KusaView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KusaView(Context context, AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        rect = new Rect();
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        Resources res = getResources();
        cellSize = res.getDimensionPixelSize(R.dimen.default_cell_size);
        space = res.getDimensionPixelSize(R.dimen.default_cell_space);
        monthsLabelHeight = res.getDimensionPixelOffset(R.dimen.default_months_label_height);

        color = Color.GRAY;

        textPaint.setTextSize(res.getDimensionPixelSize(R.dimen.default_text_size));
        textPaint.setColor(Color.DKGRAY);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        cellSize = (h - monthsLabelHeight) / 7 - space;
        cellHorizontalCount = w / (cellSize + space);
        // final int surplus = width - cellHorizontalCount * (cellSize + space);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < cellHorizontalCount - 1; i++) {
            float l = width - (i + 1) * (cellSize + space);
            float r = l + cellSize;
        }

        if (dayOfWeekLabelVisibility) {
            drawDayOfWeeksLabel(canvas);
        }

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < cellHorizontalCount - 1; j++) {
                rect.left = width - (j + 1) * (cellSize + space);
                rect.right = rect.left + cellSize;
                rect.top = monthsLabelHeight + i * (cellSize + space);
                rect.bottom = rect.top + cellSize;
                paint.setColor(color);
                canvas.drawRect(rect, paint);
            }
        }
    }

    public void setChartDataList(List<ChartData> chartDataList) {
        this.chartDataList = chartDataList;
    }

    private void drawDayOfWeeksLabel(Canvas canvas) {
        Paint.FontMetrics fm = textPaint.getFontMetrics();

        for (int i = 0; i < 7; i++) {
            if (i % 2 == 0) {
                continue;
            }

            String text = DAY_OF_WEEKS[i];
            float textWidth = textPaint.measureText(text);
            float length = cellSize / 2;
            float x = length - textWidth / 2;
            float y = monthsLabelHeight
                    + i * (cellSize + space) + (length - (fm.ascent + fm.descent) / 2);
            canvas.drawText(text, x, y, textPaint);
        }
    }
}
