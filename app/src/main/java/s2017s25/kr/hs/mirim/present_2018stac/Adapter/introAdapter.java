package s2017s25.kr.hs.mirim.present_2018stac.Adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import s2017s25.kr.hs.mirim.present_2018stac.R;

public class introAdapter extends PagerAdapter{

    LayoutInflater inflater;


    public introAdapter(LayoutInflater inflater) {
        //전달 받은 LayoutInflater를 멤버변수로 전달
        this.inflater=inflater;
    }



    @Override
    public int getCount() {
        return 5;
    }

    @Override

    public Object instantiateItem(ViewGroup container, int position) {



        View view=null;



        //새로운 View 객체를 Layoutinflater를 이용해서 생성

        //만들어질 View의 설계는 res폴더>>layout폴더>>viewpater_childview.xml 레이아웃 파일 사용

        view= inflater.inflate(R.layout.viewpager_children, null);



        //만들어진 View안에 있는 ImageView 객체 참조

        //위에서 inflated 되어 만들어진 view로부터 findViewById()를 해야 하는 것에 주의.

        ImageView img= (ImageView)view.findViewById(R.id.viewpager_children);



        //ImageView에 현재 position 번째에 해당하는 이미지를 보여주기 위한 작업

        //현재 position에 해당하는 이미지를 setting

        img.setImageResource(R.drawable.intro1+position);



        //ViewPager에 만들어 낸 View 추가

        container.addView(view);



        //Image가 세팅된 View를 리턴

        return view;

    }

    @Override

    public void destroyItem(ViewGroup container, int position, Object object) {

        //ViewPager에서 보이지 않는 View는 제거

        //세번째 파라미터가 View 객체 이지만 데이터 타입이 Object여서 형변환 실시

        container.removeView((View)object);

    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}