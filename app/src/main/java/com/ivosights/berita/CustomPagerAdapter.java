package com.ivosights.berita;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class CustomPagerAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<Berita> listBerita;

    public CustomPagerAdapter(Context context, ArrayList<Berita> listBerita) {
        mContext = context;
        this.listBerita=listBerita;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listBerita.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        Glide.with(mContext)
                .load(listBerita.get(position).getUrlToImage())
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
        container.addView(itemView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Berita berita = listBerita.get(position);
                Intent intent=new Intent(mContext,DetailActivity.class);
                intent.putExtra("author",berita.getAuthor());
                intent.putExtra("judul", berita.getTitle());
                intent.putExtra("tanggal", berita.getPublishedAt());
                intent.putExtra("isi_berita", berita.getDescription());
                intent.putExtra("urlGambar", berita.getUrlToImage());
                mContext.startActivity(intent);

            }
        });
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}
