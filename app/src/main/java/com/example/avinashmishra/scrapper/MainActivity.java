package com.example.avinashmishra.scrapper;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView textView, lastUpdated;
    Toolbar toolbar;
    ScrollView scrollView;
    ArrayList<Item> itemsList = new ArrayList<>();
    ArrayList<String> stringsList = new ArrayList<>();
    TableLayout table;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        toolbar = findViewById(R.id.toolbar);
        lastUpdated = findViewById(R.id.last_updated);
        scrollView = findViewById(R.id.scrollView);;
        table = findViewById(R.id.table);

        setSupportActionBar(toolbar);

        new RetrieveFeedTask().execute();


    }
    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        private Exception exception;
        private String feed;
        int j=0;

        protected String doInBackground(Void... params) {
            String page = "";
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet("http://agribiz.gov.np/dailyprice/pricelist/kalimati.app/");
                ResponseHandler<String> resHandler = new BasicResponseHandler();
                try {
                    page = httpClient.execute(httpGet, resHandler);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                feed = page;

                String date = "";

                int index = 0;

                for(int i=0;i<page.length();i++)
                {
                    index = page.indexOf("marketName");
                    index = page.indexOf("marketName", index+50);


                }

                for(j=index+23;j<page.length();j++)
                {
                    if(page.charAt(j) == '<')
                    {
                        break;
                    }
                    date = date + page.charAt(j);
                }

                for(int c=0;c<17;c++)
                {
                    makeStringList();
                }





                return date;
            } catch (Exception e) {
                this.exception = e;

                return null;
            } finally {
            }
        }

        protected void onPostExecute(String feed) {


            lastUpdated.setText("Last updated: " + feed);
            makeItems();
        }

        public void makeTable()
        {

//            Toast.makeText(MainActivity.this, "" + stringsList.get(9), Toast.LENGTH_SHORT).show();



            TableRow rowDayLabels = new TableRow(MainActivity.this);
            rowDayLabels.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            // day5 column
            TextView day5Label = new TextView(MainActivity.this);
            day5Label.setText("");
            //day5Label.setTypeface(Typeface.SERIF, Typeface.BOLD);
            day5Label.setGravity(Gravity.CENTER_HORIZONTAL);
            day5Label.setTextSize(18);
            day5Label.setPadding(5,5,5,5);

            rowDayLabels.addView(day5Label);

            // day 1 column
            TextView day1Label = new TextView(MainActivity.this);
            day1Label.setText("Name");
            day1Label.setTextSize(18);
            day1Label.setPadding(5,5,5,5);
            //day1Label.setTypeface(Typeface.SERIF, Typeface.BOLD);

            rowDayLabels.addView(day1Label);

            // day2 column
            TextView day2Label = new TextView(MainActivity.this);
            day2Label.setText("Wholesale Rs.");
            //day2Label.setTypeface(Typeface.SERIF, Typeface.BOLD);
            day2Label.setGravity(Gravity.CENTER_HORIZONTAL);
            day2Label.setTextSize(18);
            day2Label.setPadding(5,5,5,5);

            rowDayLabels.addView(day2Label);

            // day3 column
            TextView day3Label = new TextView(MainActivity.this);
            day3Label.setText("Retail Rs.");
            //day3Label.setTypeface(Typeface.SERIF, Typeface.BOLD);
            day3Label.setGravity(Gravity.CENTER_HORIZONTAL);
            day3Label.setTextSize(18);
            day3Label.setPadding(5,5,5,5);

            rowDayLabels.addView(day3Label);

            table.addView(rowDayLabels);



            for(int tr = 0;tr<itemsList.size();tr++)
            {
                TableRow tableRow = new TableRow(MainActivity.this);

                TextView textVal = new TextView(MainActivity.this);
                textVal.setText("" + (tr+1));
                textVal.setTextSize(18);
                textVal.setGravity(Gravity.CENTER_HORIZONTAL);
                textVal.setPadding(13, 13, 13,13);

                TextView textName = new TextView(MainActivity.this);
                textName.setText(itemsList.get(tr).getName());
                textName.setTextSize(18);
                textName.setPadding(13, 13, 13,13);

                TextView textWPrice = new TextView(MainActivity.this);
                textWPrice.setText("" + itemsList.get(tr).getWhole_price());
                textWPrice.setTextSize(18);
                textWPrice.setGravity(Gravity.CENTER_HORIZONTAL);
                textWPrice.setPadding(13, 13, 13,13);

                TextView textRPrice = new TextView(MainActivity.this);
                textRPrice.setText("" + itemsList.get(tr).getRetail_price());
                textRPrice.setTextSize(18);
                textRPrice.setGravity(Gravity.CENTER_HORIZONTAL);
                textRPrice.setPadding(13, 13, 13,13);

                tableRow.addView(textVal);
                tableRow.addView(textName);
                tableRow.addView(textWPrice);
                tableRow.addView(textRPrice);

                if(tr % 2 == 0)
                {
                    tableRow.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                }
                else
                {
                    tableRow.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }

                table.addView(tableRow);

            }







        }

        public void makeStringList()
        {
            String oddStr = "";
            if(count % 2 == 0)
            {
                oddStr = "<tr class=\"odd\">";
            }
            else
            {
                oddStr = "<tr class=\"even\">";
            }
            int indexOdd = feed.indexOf(oddStr, j);
            int indexEnd = feed.indexOf("</tr>", indexOdd);
            String full = "";

            for(j=indexOdd+oddStr.length();j<feed.length();j++)
            {
                if(j>=indexEnd)
                {
                    break;
                }
                full = full + feed.charAt(j);

            }

            full = full.trim();
            stringsList.add(full);
            count++;

        }

        public void makeItems()
        {
            for(int i=0;i<stringsList.size();i++)
            {
                String[] parts = stringsList.get(i).trim().split("</td>");

                String name = "";
                Double whole_price = 0.00, retail_price=0.00;

                for(int m=0;m<parts.length;m++)
                {
                    String data[] = parts[m].split(">");


                    switch (m)
                    {
                        case 1:
                            name = data[1];
                            break;

                        case 3:
                            whole_price = Double.parseDouble(data[1]);
                            break;

                        case 5:
                            retail_price = Double.parseDouble(data[1]);
                            break;
                    }



                }
                Item item = new Item(name, whole_price, retail_price);
                itemsList.add(item);
//                Toast.makeText(MainActivity.this, "Name: " + item.getName() + "Whole: " + item.getWhole_price() + "Retail: " + item.getRetail_price(), Toast.LENGTH_SHORT).show();
            }

            makeTable();

        }

    }
}
