package com.ivosights.berita;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by asuspc on 25/08/2017.
 */

public class DetailActivity extends AppCompatActivity{
    private TextView tanggal,author,judul,isi_berita;
    private ImageView gambar,kembali;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle bundle=getIntent().getExtras();
        String dataTanggal=bundle.getString("tanggal");
        String dataAuthor=bundle.getString("author");
        String dataJudul=bundle.getString("judul");
        String dataIsiBerita=bundle.getString("isi_berita");
        String dataUrlGambar=bundle.getString("urlGambar");
      //  Toast.makeText(this,dataJenisBerita+dataJudul+dataTanggal,Toast.LENGTH_SHORT).show();
        tanggal=(TextView)findViewById(R.id.tanggal);
        author=(TextView)findViewById(R.id.author);
        judul=(TextView)findViewById(R.id.judul);
        isi_berita=(TextView)findViewById(R.id.isi_berita);
        gambar=(ImageView)findViewById(R.id.gambar);
        kembali=(ImageView)findViewById(R.id.kembali);
        tanggal.setText(dataTanggal);
        author.setText(dataAuthor);
        judul.setText(dataJudul);
        isi_berita.setText(dataIsiBerita);
        Glide.with(this).load(dataUrlGambar)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(gambar);
        gambar.setScaleType(ImageView.ScaleType.FIT_XY);
    kembali.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    });
    }
}
