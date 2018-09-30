package fitme.ai.mycreeper.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import fitme.ai.mycreeper.R;
import fitme.ai.mycreeper.utils.L;

public class DetailActivity extends Activity{

    private TextView tvContect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
    }

    private void initView(){
        tvContect = findViewById(R.id.tv_content);
        final String href = getIntent().getStringExtra("href");
        L.i("href:"+href);
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    final Document document = Jsoup.connect("http://daily.zhihu.com"+href).get();
                    L.logE("document:"+document.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvContect.setText(document.toString());
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
