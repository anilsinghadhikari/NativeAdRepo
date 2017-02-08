package nativeandroidsample.android.com.nativeandroidsample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.formats.NativeCustomTemplateAd;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<MyData> myDataList;
    private ListView mListView;
//    private String mAdUnitId="/6499/example/native-backfill";
    private String mAdUnitId="/6499/example/native";
    private String temlateID="10063170";
    int AD_TYPE=0;
    int DATA_TYPE=1;
    private MyArrayAdapter mainAdapter;
    public NativeCustomTemplateAd nativeCustomTemplateAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.mListView);

        myDataList= new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            MyData myData=null;
            if(i==3 || i==5 || i==7 || i==20)
                myData = new MyData(AD_TYPE);
            else
            myData=new MyData("Country "+i, "State "+i, ContextCompat.getDrawable(this, R.drawable.appicon36), DATA_TYPE);


            myDataList.add(myData);
        }

        loadAd();
        setUpMYadapter();


    }

    private void setUpMYadapter() {

        mainAdapter =
                new MyArrayAdapter();
        mListView.setAdapter(mainAdapter);
    }


    class MyArrayAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return myDataList.size();
        }

        @Override
        public MyData getItem(int position) {
            return myDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LinearLayout view = (LinearLayout) convertView;
            MyCustomViewHolder viewHolder=null;
            MyData item = getItem(position);
          /*  if(convertView==null){*/
            if(getItemViewType(position)==DATA_TYPE) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                viewHolder = new MyCustomViewHolder();
                view = (LinearLayout) inflater.inflate(R.layout.item_normal, parent, false);
                viewHolder.image= (ImageView) view.findViewById(R.id.image);
                viewHolder.price= (TextView) view.findViewById(R.id.price);
                viewHolder.subtitle= (TextView) view.findViewById(R.id.subtitle);
                viewHolder.headline= (TextView) view.findViewById(R.id.headline);
                viewHolder.body= (TextView) view.findViewById(R.id.body);//                    view.setTag(viewHolder);
            }else if(getItemViewType(position)==AD_TYPE){
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                viewHolder = new MyCustomViewHolder();
                view = (LinearLayout) inflater.inflate(R.layout.item_ad, parent, false);
                viewHolder.image= (ImageView) view.findViewById(R.id.image);
                viewHolder.price= (TextView) view.findViewById(R.id.price);
                viewHolder.subtitle= (TextView) view.findViewById(R.id.subtitle);
                viewHolder.headline= (TextView) view.findViewById(R.id.headline);
                viewHolder.body= (TextView) view.findViewById(R.id.body);
//                    view.setTag(viewHolder);
            }

         /*   LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolder = new MyCustomViewHolder();
            view = (LinearLayout) inflater.inflate(R.layout.item_ad, parent, false);
            viewHolder.image= (ImageView) view.findViewById(R.id.image);
            viewHolder.price= (TextView) view.findViewById(R.id.price);
            viewHolder.subtitle= (TextView) view.findViewById(R.id.subtitle);
            viewHolder.headline= (TextView) view.findViewById(R.id.headline);
            viewHolder.body= (TextView) view.findViewById(R.id.body);*/

            if(item.getViewType()==DATA_TYPE) {
                viewHolder.headline.setText(getItem(position).getHeadline());
                viewHolder.subtitle.setText(getItem(position).getSubtitle());
                viewHolder.image.setImageDrawable(getItem(position).getDrawable());
            }else{
                if(nativeCustomTemplateAd!=null) {
                    Log.d("nativeCustomTemplateAd", "inside getView()");

                    viewHolder.headline.setText(nativeCustomTemplateAd.getText("Headline"));
                    viewHolder.subtitle.setText(nativeCustomTemplateAd.getText("Caption"));
                    viewHolder.image.setImageDrawable(nativeCustomTemplateAd.getImage("MainImage").getDrawable());
                    nativeCustomTemplateAd.recordImpression();
                    viewHolder.headline.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            nativeCustomTemplateAd.performClick("anil");


                        }
                    });
                }

            }
            return view;
        }

        @Override
        public int getItemViewType(int position) {
            return getItem(position).getViewType();
        }
    }

    private void loadAd() {
        AdLoader adLoader = new AdLoader.Builder(MainActivity.this, mAdUnitId)
                .forCustomTemplateAd(temlateID,
                        new NativeCustomTemplateAd.OnCustomTemplateAdLoadedListener() {


                            @Override
                            public void onCustomTemplateAdLoaded(NativeCustomTemplateAd ad) {
                                nativeCustomTemplateAd = ad;
                                Log.d("nativeCustomTemplateAd", "inside onCustomTemplateAdLoaded");
                                if (mainAdapter != null)
                                    mainAdapter.notifyDataSetChanged();

                                // Show the custom template and record an impression.
//                                mainAdapter.notifyDataSetChanged();
                            }
                        },
                        null)
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        super.onAdFailedToLoad(errorCode);
                        Log.d("nativeCustomTemplateAd", "inside onAdFailedToLoad: errorCode= "+errorCode);

                    }
                })
                .build();


        adLoader.loadAd(new PublisherAdRequest.Builder().build());
    }


    class MyCustomViewHolder{

        public TextView subtitle;
        public ImageView image;
        public TextView price;
        public TextView headline;
        public TextView body;
    }

    class MyAdViewHolder{

        public TextView title;
    }
}
