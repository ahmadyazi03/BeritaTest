package com.ivosights.berita;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by asuspc on 25/08/2017.
 */

public class BeritaAdapter extends ArrayAdapter<Berita>{
    private Context context;
    private ArrayList<Berita> list_berita;
    private TextView tanggal,isi;
    private ImageView gambar;
public BeritaAdapter(Context context, ArrayList<Berita> list_berita){
    super(context,R.layout.bagian_berita, list_berita);
    this.context=context;
    this.list_berita = list_berita;
}


    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
        Berita berita = list_berita.get(position);
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.bagian_berita,null);
        tanggal=(TextView)view.findViewById(R.id.tanggal);
        isi=(TextView)view.findViewById(R.id.isi);
        gambar=(ImageView)view.findViewById(R.id.gambar);
        tanggal.setText(berita.getPublishedAt());
        isi.setText(berita.getTitle());
        Glide.with(context).load(berita.getUrlToImage())
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(gambar);
        gambar.setScaleType(ImageView.ScaleType.FIT_XY);
        return view;
    }
}
