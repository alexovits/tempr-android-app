package edu.ubb.tempr.ui.heatingcircuit;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import java.util.List;

import edu.ubb.tempr.R;
import edu.ubb.tempr.data.model.HeatingCircuit;
import edu.ubb.tempr.data.model.SensorLog;
import edu.ubb.tempr.ui.base.view.BaseActivity;
import me.angrybyte.circularslider.CircularSlider;


public class DetailedView extends BaseActivity<DetailedViewModel> implements DetailedViewInteraction {

    private static final String TAG = DetailedView.class.getName();

    private CircularSlider desiredSlider;
    private LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heating_circuit_detailed_view);

        // Get the intent data
        Bundle extras = getIntent().getExtras();

        // Create the HC element from intent data
        HeatingCircuit heatingCircuit = new HeatingCircuit();
        heatingCircuit.setAiflag(extras.getBoolean("HeatingCircuit-aiflag"));
        heatingCircuit.setDesiredTemperature(extras.getInt("HeatingCircuit-desiredTemp"));
        heatingCircuit.setSuggestedTemperature(extras.getInt("HeatingCircuit-suggestedTemp"));
        heatingCircuit.setId(extras.getLong("HeatingCircuit-id"));
        heatingCircuit.setName(extras.getString("HeatingCircuit-name"));

        Log.i(TAG, "So desired: "+heatingCircuit.getDesiredTemperature()+" and suggested: "+heatingCircuit.getSuggestedTemperature() + "and Flag is: "+ heatingCircuit.isAiflag());

        // Set up ViewModel
        viewModel = new DetailedViewModel(this, heatingCircuit);
        setAndBindContentView(R.layout.activity_heating_circuit_detailed_view, viewModel);

        // Setting up the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Config slider and initialize
        desiredSlider = (CircularSlider) findViewById(R.id.circular);
        desiredSlider.setOnSliderMovedListener(viewModel);
        desiredSlider.setThumbColor(R.color.primary_dark);
//        viewModel.setSlider(heatingCircuit.getDesiredTemperature());
        initChartWithDefaultParams();
        setSliderEnabled(!heatingCircuit.isAiflag());


        // Set title for HC name
        getSupportActionBar().setTitle(extras.getString("HeatingCircuit-name"));
    }

    @Override
    public void setSliderValue(double angle) {
        if(desiredSlider == null)
            desiredSlider = (CircularSlider) findViewById(R.id.circular);
        desiredSlider.setAngle(angle);
    }

    @Override
    public void setSliderEnabled(boolean enabled) {
        if(!enabled) desiredSlider.setThumbSize(0);
        else desiredSlider.setThumbSize(31);
        desiredSlider.invalidate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            Log.i(TAG, "Getting destroyed MOTHEFUCKA");
            viewModel.destroyObserver();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setChartData(List<SensorLog> historyList) {
        ArrayList<Entry> yVals = new ArrayList<>();

        int temperatureSum = 0;
        int dayCounter = 0;
        for (int i = 0; i < historyList.size(); i++) {
            // Adding up temperatures
            temperatureSum += historyList.get(i).getTemperature();
            dayCounter++;
            if(dayCounter == 10) {
                // Normalizing the index values into the [0,7] interval
                float index = (i * 7) / (float) historyList.size();
                // Setting the new data entry and resetting average-counter variables
                yVals.add(new Entry(index, temperatureSum/dayCounter));
                dayCounter = 0;
                temperatureSum = 0;
            }
        }

        LineDataSet set1;

        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
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

            // data.setValueTypeface(mTfLight);
            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            mChart.setData(data);
            mChart.animateXY(1000,1000);
            mChart.invalidate();
        }
    }

    private void initChartWithDefaultParams(){
        mChart = (LineChart) findViewById(R.id.history_chart);
        mChart.setViewPortOffsets(0, 0, 0, 0);
        mChart.setBackgroundColor(Color.TRANSPARENT);

        // Disable touch gestures,scaling, description & dragging
        mChart.setTouchEnabled(false);
        mChart.setDragEnabled(false);
        mChart.setScaleEnabled(false);
        mChart.getDescription().setEnabled(false);

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
        y.setLabelCount(5, false);
        y.setTextColor(Color.BLACK);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(true);
        y.setAxisLineColor(Color.GREEN);

        mChart.getAxisRight().setEnabled(false);
        mChart.getLegend().setEnabled(false);
        mChart.animateXY(600,600);
        mChart.invalidate();
    }

    private class CustomValueFormatter implements IAxisValueFormatter{

        private final String[] days = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return days[(int) value];
        }
    }

}
