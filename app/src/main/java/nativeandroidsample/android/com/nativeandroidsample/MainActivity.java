package nativeandroidsample.android.com.nativeandroidsample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.formats.NativeCustomTemplateAd;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<MyData> myDataList;
    private ListView mListView;
    private String mAdUnitId="6499/example/native-backfill";
    private String temlateID="10063170";
     int AD_TYPE=0;
    int DATA_TYPE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.mListView);

        myDataList= new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            MyData myData=null;
            if(i%2==0)
                myData = new MyData("Data "+i,DATA_TYPE);
            else
                myData=new MyData("Data "+i, AD_TYPE);
            myDataList.add(myData);
        }

        initAd();

        setUpMYadapter();


    }

    private void initAd() {

        AdLoader.Builder builder = new AdLoader.Builder(this, mAdUnitId).forCustomTemplateAd(temlateID, new NativeCustomTemplateAd.OnCustomTemplateAdLoadedListener() {
            @Override
            public void onCustomTemplateAdLoaded(NativeCustomTemplateAd nativeCustomTemplateAd) {

            }
        }, new NativeCustomTemplateAd.OnCustomClickListener() {
            @Override
            public void onCustomClick(NativeCustomTemplateAd nativeCustomTemplateAd, String s) {

            }
        }).withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
            }
        });
        builder.build().loadAd(new PublisherAdRequest.Builder().build());

    }

    private void setUpMYadapter() {

        MyArrayAdapter mainAdapter =
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
            if (view == null) {
                if(item.getViewType()==DATA_TYPE) {
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = (LinearLayout) inflater.inflate(R.layout.item_normal, parent, false);
                    view.findViewById(R.id.activity_main);
                    viewHolder = new MyCustomViewHolder();
                    viewHolder.title = (TextView) view.findViewById(R.id.subtitle);
                    view.setTag(viewHolder);
                }else{
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = (LinearLayout) inflater.inflate(R.layout.item, parent, false);
                    view.findViewById(R.id.activity_main);
                    viewHolder = new MyCustomViewHolder();
                    view.setTag(viewHolder);
                }

            }else{
                viewHolder = (MyCustomViewHolder) view.getTag();
            }

            viewHolder.title.setText(getItem(position).getTitle());


            return view;
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }
    }


    class MyCustomViewHolder{

        public TextView title;
    }

    class MyAdViewHolder{

        public TextView title;
    }
}
