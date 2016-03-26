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
import android.util.SparseArray;
import android.view.View;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class KusaView extends View {

    private static final String TAG = KusaView.class.getSimpleName();

    private static String[] MONTHS
            = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private static String[] DAY_OF_WEEKS = {"S", "M", "T", "W", "T", "F", "S"};

    final Date now;
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

    SparseArray<ChartData> chartDataSparseArray = new SparseArray<>();

    // Options
    private boolean monthsLabelVisibility = true;
    private boolean dayOfWeekLabelVisibility = true;

    @ColorInt
    private int emptyCellColor;

    public KusaView(Context context) {
        this(context, null);
    }

    public KusaView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KusaView(Context context, AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Calendar calendar = Calendar.getInstance();
        now = calendar.getTime();
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

        emptyCellColor = Color.parseColor("#EEEEEE");

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
        surplus = width - cellHorizontalCount * (cellSize + space);
    }

    int surplus;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (monthsLabelVisibility) {
            drawMonthsLabel(canvas);
        }

        if (dayOfWeekLabelVisibility) {
            drawDayOfWeeksLabel(canvas);
        }

        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < cellHorizontalCount - 1; i++) {
            for (int j = 0; j < 7; j++) {
                if (i == 0 && dayOfWeek - 1 < j) {
                    continue;
                }

                cal.setTime(now);
                int daysAgo = i * 7 + j;
                ChartData data = chartDataSparseArray.get(daysAgo);

                rect.left = width - (i + 1) * (cellSize + space);
                rect.right = rect.left + cellSize;
                rect.top = monthsLabelHeight + j * (cellSize + space);
                rect.bottom = rect.top + cellSize;

                if (null != data) {
                    paint.setColor(data.color);
                } else {
                    paint.setColor(emptyCellColor);
                }
                canvas.drawRect(rect, paint);
            }
        }
    }

    public void setChartDataList(List<ChartData> chartDataList) {
        for (ChartData chartData : chartDataList) {
            chartDataSparseArray.append(chartData.daysAgo, chartData);
        }
    }
    
    private void drawMonthsLabel(Canvas canvas) {
        Calendar cal = Calendar.getInstance();
        final int total = (cellHorizontalCount - 1) * 7;

        int prevMonth = -1;
        for (int i = 0; i < cellHorizontalCount - 1; i++) {
            for (int j = 0; j < 7; j++) {
                cal.setTime(now);
                int daysAgo = total - (i * 7 + (j + 1));
                cal.add(Calendar.DAY_OF_MONTH, -daysAgo);
                int month = cal.get(Calendar.MONTH);
                if (month != prevMonth) {
                    String monthLabel = MONTHS[month];
                    int x = surplus + (1 + (i + 1)) * (cellSize + space);
                    canvas.drawText(monthLabel, x, cellSize, textPaint);
                }
                prevMonth = month;
            }
        }
    }

    private void drawDayOfWeeksLabel(Canvas canvas) {
        Paint.FontMetrics fm = textPaint.getFontMetrics();

        for (int i = 0; i < 7; i++) {
            if (0 == i % 2) {
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
