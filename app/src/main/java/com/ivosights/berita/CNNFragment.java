package com.ivosights.berita;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by asuspc on 25/08/2017.
 */

public class CNNFragment extends Fragment {
    private ListView listBerita;
    private ArrayList<Berita> listDataBerita;
    private ArrayList<Berita> listDataHeader;
    private ArrayList<ImageView> dots;
    private ViewPager mViewPager;
    LinearLayout dotsLayout;
    private SimpleDateFormat sdf,sdfBaru;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listDataBerita =new ArrayList<>();
        listDataHeader=new ArrayList<>();
        sdf=new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        sdfBaru=new SimpleDateFormat("dd MMMM",Locale.ENGLISH);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_berita,container,false);
        dotsLayout = (LinearLayout)view.findViewById(R.id.dots);
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mViewPager.setClipToPadding(false);
        mViewPager.setPadding(30,0,30,0);
        mViewPager.setPageMargin(10);
        listBerita=(ListView) view.findViewById(R.id.list_berita);
        listBerita.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Berita berita = listDataBerita.get(i);
                Intent intent=new Intent(getActivity(),DetailActivity.class);
                intent.putExtra("author",berita.getAuthor());
                intent.putExtra("judul", berita.getTitle());
                intent.putExtra("tanggal", berita.getPublishedAt());
                intent.putExtra("isi_berita", berita.getDescription());
                intent.putExtra("urlGambar", berita.getUrlToImage());
                startActivity(intent);
            }
        });

        tampilHeadline();
        tampilBerita();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void tampilBerita(){
        String url="http://newsapi.org/v1/articles?source=cnbc&sortBy=top&apiKey=33146428d2a647f883b27c277d324eaa";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray= jsonObject.getJSONArray("articles");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jo=jsonArray.getJSONObject(i);
                        String author=jo.getString("author");
                        String title=jo.getString("title");
                        String description=jo.getString("description");
                        String url=jo.getString("url");
                        String urlToImage=jo.getString("urlToImage");
                       // String publishedAt=jo.getString("publishedAt");
                        String[] publishedAt=jo.getString("publishedAt").split("T",1);

                        Date date= sdf.parse(publishedAt[0]);
                        String tglUbah= sdfBaru.format(date);

                        Berita berita =new Berita();
                        berita.setAuthor(author);
                        berita.setTitle(title);
                        berita.setDescription(description);
                        berita.setUrl(url);
                        berita.setUrlToImage(urlToImage);
                        berita.setPublishedAt(tglUbah);
                        listDataBerita.add(berita);

                    }
                    BeritaAdapter beritaAdapter=new BeritaAdapter(getActivity(), listDataBerita);
                    listBerita.setAdapter(beritaAdapter);
                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppBerita.getInstance().addToRequestQueue(stringRequest);
    }
    public void tampilHeadline(){
        String url="http://newsapi.org/v1/articles?source=cnn&sortBy=top&apiKey=33146428d2a647f883b27c277d324eaa";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray= jsonObject.getJSONArray("articles");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jo=jsonArray.getJSONObject(i);
                        String author=jo.getString("author");
                        String title=jo.getString("title");
                        String description=jo.getString("description");
                        String url=jo.getString("url");
                        String urlToImage=jo.getString("urlToImage");
                        String publishedAt=jo.getString("publishedAt");
                        Berita berita =new Berita();
                        berita.setAuthor(author);
                        berita.setTitle(title);
                        berita.setDescription(description);
                        berita.setUrl(url);
                        berita.setUrlToImage(urlToImage);
                        berita.setPublishedAt(publishedAt);
                        //        listBerita.add(berita);
                        listDataHeader.add(berita);
                    }
                    CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(getActivity(), listDataHeader);

                    mViewPager.setAdapter(mCustomPagerAdapter);
                    addDots();
                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppBerita.getInstance().addToRequestQueue(stringRequest);
    }
    public void addDots() {
        dots = new ArrayList<>();
        for(int i = 0; i < listDataHeader.size(); i++) {
            ImageView dot = new ImageView(getActivity());
            dot.setImageDrawable(getResources().getDrawable(R.drawable.pager_dot_not_selected));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            dotsLayout.addView(dot, params);

            dots.add(dot);
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                selectDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void selectDot(int idx) {

        for(int i = 0; i < listDataHeader.size(); i++) {
            int drawableId = (i==idx)?(R.drawable.pager_dot_selected):(R.drawable.pager_dot_not_selected);
             Drawable drawable = ContextCompat.getDrawable(getActivity(), drawableId);

            dots.get(i).setImageDrawable(drawable);
        }
    }
}

