package s2017s25.kr.hs.mirim.present_2018stac.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import s2017s25.kr.hs.mirim.present_2018stac.Adapter.introAdapter;
import s2017s25.kr.hs.mirim.present_2018stac.R;

public class AppInfo extends AppCompatActivity {
    ViewPager pager;
    TextView nextBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
        pager= (ViewPager)findViewById(R.id.pager);

        //ViewPager에 설정할 Adapter 객체 생성

        //ListView에서 사용하는 Adapter와 같은 역할.

        //다만. ViewPager로 스크롤 될 수 있도록 되어 있다는 것이 다름

        //PagerAdapter를 상속받은 CustomAdapter 객체 생성

        //CustomAdapter에게 LayoutInflater 객체 전달

        introAdapter adapter= new introAdapter(getLayoutInflater());



        //ViewPager에 Adapter 설정

        pager.setAdapter(adapter);

        nextBtn = findViewById(R.id.infor_next);
    }



    public void mOnClick(View v){
        int position;



           if(v.getId() ==  R.id.infor_next) {//다음버튼 클릭

               position = pager.getCurrentItem();//현재 보여지는 아이템의 위치를 리턴

               //현재 위치(position)에서 +1 을 해서 다음 position으로 변경

               //다음 Item으로 현재의 아이템 변경 설정(가장 마지막이면 더이상 이동하지 않음)

               //첫번째 파라미터: 설정할 현재 위치

               //두번째 파라미터: 변경할 때 부드럽게 이동하는가? false면 팍팍 바뀜

               pager.setCurrentItem(position + 1, true);
               if (position == 3)
                   nextBtn.setText("프리젠트 시작하기");
               if (position == 4) {
                   finish();
               }
           }

        }

}
