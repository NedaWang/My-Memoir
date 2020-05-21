package com.example.memoir.report;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.example.memoir.R;
import com.example.memoir.entity.Memoir;
import com.anychart.core.cartesian.series.Column;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ReportFragment extends Fragment {

    private EditText startDate;
    private EditText endDate;
    private Button createPie;
    AnyChartView pieChart;
    private Spinner spinner;
    private BarChart barChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        pieChart = view.findViewById(R.id.pie_chart);
        APIlib.getInstance().setActiveAnyChartView(pieChart); // for show multiple chart in one activity
        //columnChart = view.findViewById(R.id.column_chart);
        startDate = view.findViewById(R.id.start_date);
        endDate = view.findViewById(R.id.end_date);
        createPie = view.findViewById(R.id.create_pie);
        createPie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String start = startDate.getText().toString();
                String end = endDate.getText().toString();

                InitPieTask task = new InitPieTask();
                task.execute(start, end, "1");
            }
        });

        spinner = view.findViewById(R.id.year_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.years,android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        barChart = view.findViewById(R.id.bar_chart);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                //Toast.makeText(getActivity(),text, Toast.LENGTH_SHORT).show();
                InitBarChartTask initBarChartTask = new InitBarChartTask();
                initBarChartTask.execute(text,"1");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
           }
        });






        return view;
    }

    // parameter: start date, end date, id
    public class InitPieTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String param = strings[0] + "/" + strings[1] + "/" + strings[2];
            String url = "http://10.0.2.2:8080/MemoirDB/webresources/memoir.memoir/findByDates/" + param;
            OkHttpClient client = new OkHttpClient();
            Request.Builder builder = new Request.Builder();
            builder.url(url);
            Request request = builder.build();
            String results = "result";
            try {
                Response response = client.newCall(request).execute();
                results = response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            Pie pie = AnyChart.pie();
            List<String> postCodes = new ArrayList<String>();
            List<Integer> numbers = new ArrayList<Integer>();
            List<DataEntry> dataEntries = new ArrayList<>();
            try {
                JSONArray jsons = new JSONArray(s);
                for (int i = 0; i < jsons.length(); i++) {
                    JSONObject j = jsons.getJSONObject(i);
                    postCodes.add(j.getString("postcode"));
                    numbers.add(Integer.parseInt(j.getString("number")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < numbers.size(); i++) {
                dataEntries.add(new ValueDataEntry(postCodes.get(i), numbers.get(i)));
            }
            pie.data(dataEntries);
            pie.title("pie chart report");
            pieChart.setChart(pie);
        }
    }

    public class InitBarChartTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) { // param1: year, param2: id
            String param = strings[0] + "/" + strings[1];
            String url = "http://10.0.2.2:8080/MemoirDB/webresources/memoir.memoir/findByYear/" + param;
            OkHttpClient client = new OkHttpClient();
            Request.Builder builder = new Request.Builder();
            builder.url(url);
            Request request = builder.build();
            String results = "result";
            try {
                Response response = client.newCall(request).execute();
                results = response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void onPostExecute(String s) {
            //Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
            JSONArray jsons;
            ArrayList<BarEntry> entries = new ArrayList<>();
            BarDataSet bardataset = new BarDataSet(entries, "movie");
            ArrayList<String> labels = new ArrayList<String>();
            try {
                jsons = new JSONArray(s);
                System.out.println("lenge" + jsons.length());
                for (int i = 0; i < jsons.length(); i++) {
                    JSONObject j = jsons.getJSONObject(i);
                    labels.add(j.getString("month"));
                    System.out.println(j.getString("month"));
                    System.out.println(j.getInt("number")+"");
                    //Toast.makeText(getActivity(),j.getString("month"),Toast.LENGTH_SHORT).show();
                    int value = j.getInt("number");
                    entries.add(new BarEntry(value,i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
/*
            entries.add(new BarEntry(8f, 5));
            entries.add(new BarEntry(2f, 6));
            entries.add(new BarEntry(5f, 7));
            entries.add(new BarEntry(20f, 8));
            entries.add(new BarEntry(15f, 9));
            entries.add(new BarEntry(19f, 10));
            labels.add("2016");
            labels.add("2015");
            labels.add("2014");
            labels.add("2013");
            labels.add("2012");
            labels.add("2011");


 */
            BarData data = new BarData(labels, bardataset);
            barChart.setData(data); // set the data and list of labels into chart
            //barChart.setDescription("Set Bar Chart Description Here");  // set the description
            bardataset.setColors(ColorTemplate.JOYFUL_COLORS);
            barChart.animateY(5000);



        }
    }



}
