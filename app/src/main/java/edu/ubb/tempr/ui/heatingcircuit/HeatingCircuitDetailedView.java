package edu.ubb.tempr.ui.heatingcircuit;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

import edu.ubb.tempr.R;
import me.angrybyte.circularslider.CircularSlider;


public class HeatingCircuitDetailedView extends AppCompatActivity implements CircularSlider.OnSliderMovedListener {

    LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heating_circuit_detailed_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        CircularSlider sl = (CircularSlider) findViewById(R.id.circular);
        sl.setOnSliderMovedListener(this);
        sl.setThumbColor(R.color.primary_light);

        mChart = (LineChart) findViewById(R.id.history_chart);

        mChart.setViewPortOffsets(0, 0, 0, 0);
        mChart.setBackgroundColor(Color.TRANSPARENT);

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(false);
        // enable scaling and dragging
        mChart.setDragEnabled(false);
        mChart.setScaleEnabled(false);

        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        mChart.setMaxHighlightDistance(300);

        XAxis x = mChart.getXAxis();
        x.setLabelCount(7);
        x.setTextColor(Color.BLACK);
        x.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        x.setDrawGridLines(true);
        x.setGranularity(1f);
        x.setValueFormatter(new CustomValueFormatter());
        x.setAvoidFirstLastClipping(true);
        x.setAxisMinimum(0);
        x.setAxisLineColor(Color.BLACK);

        YAxis y = mChart.getAxisLeft();
//        y.setTypeface(mTfLight);
        y.setLabelCount(5, false);
        y.setTextColor(Color.BLACK);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(true);
        y.setAxisLineColor(Color.GREEN);

        mChart.getAxisRight().setEnabled(false);

        // add data
        setData(7, 30);

        mChart.getLegend().setEnabled(false);

        //mChart.animateXY(2000, 2000);

        // dont forget to refresh the drawing

//
//        List<Entry> entries = new ArrayList<>();
//
//        for(int i=10; i<30; i++){
//            entries.add(new Entry(i,i+3));
//        }
//        LineDataSet lineDataSet = new LineDataSet(entries, "Label");
//        mChart.setData(new LineData(lineDataSet));
        mChart.invalidate();
        getSupportActionBar().setTitle("Room");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSliderMoved(double pos) {
        Log.i("HC", "The pos is now: "+pos);
    }

    private void setData(int count, float range) {

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult) + 20;// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals.add(new Entry(i, val));
        }

        LineDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet)mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals, "DataSet 1");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(1.8f);
            set1.setCircleRadius(4f);
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(getResources().getColor(R.color.primary_light));
            set1.setFillColor(getResources().getColor(R.color.primary));
            set1.setFillAlpha(1000);
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return -10;
                }
            });

            // create a data object with the datasets
            LineData data = new LineData(set1);
            //data.setValueTypeface(mTfLight);
            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            mChart.setData(data);
        }
    }

    public class CustomValueFormatter implements IAxisValueFormatter{

        private final String[] days = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return days[(int) value];
        }
    }

}
