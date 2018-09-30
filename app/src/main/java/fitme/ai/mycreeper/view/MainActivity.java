package fitme.ai.mycreeper.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import fitme.ai.mycreeper.R;
import fitme.ai.mycreeper.adapter.MyRecyclerAdapter;
import fitme.ai.mycreeper.bean.DailyHomeBean;
import fitme.ai.mycreeper.utils.L;

public class MainActivity extends Activity {

    private DailyHomeBean dailyHomeBean;
    private List<DailyHomeBean> mList;
    private MyRecyclerAdapter myRecyclerAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Document doc = Jsoup.connect("http://daily.zhihu.com/").get();
                    //L.logE("HTML內容:"+doc.toString());
                    Elements elements = doc.select("div.box");
//                    L.logE("box:"+elements.toString());
//                    L.i("href:"+elements.get(1).select("a").attr("href"));
//                    L.i("src:"+elements.get(1).select("img").attr("src"));
//                    L.i("title:"+elements.get(1).select("span.title").text());
                    for (int i=0;i<elements.size();i++){
                        dailyHomeBean = new DailyHomeBean();
                        dailyHomeBean.setHref(elements.get(i).select("a").attr("href"));
                        dailyHomeBean.setImgUrl(elements.get(i).select("img").attr("src"));
                        dailyHomeBean.setTitle(elements.get(i).select("span.title").text());
                        mList.add(dailyHomeBean);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            myRecyclerAdapter.notifyDataSetChanged();
                        }
                    });
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void initView(){
        mList = new ArrayList<>();
        myRecyclerAdapter = new MyRecyclerAdapter(mList);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myRecyclerAdapter);
    }
}
