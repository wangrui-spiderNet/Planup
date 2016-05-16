package com.starnet.jn_wr.planup.view.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.starnet.jn_wr.planup.R;
import com.starnet.jn_wr.planup.view.adapter.HomePageAdapter;
import com.starnet.jn_wr.planup.view.fragment.BaseFragment;
import com.starnet.jn_wr.planup.view.fragment.JNHomeSentencePageFragment;
import com.starnet.jn_wr.planup.view.fragment.JNHomeDiscoverPageFragment;
import com.starnet.jn_wr.planup.view.fragment.JNHomeFrontPageFragment;
import com.starnet.jn_wr.planup.view.fragment.JNHomeMePageFragment;
import com.starnet.jn_wr.planup.view.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private ImageButton addButton;
    private ImageView homeIcon, adviseIcon, discoverIcon, meIcon;
    private RelativeLayout homeBtn, adviseBtn, discoverBtn, meBtn;
    private ImageView[] iconList;
    private TextView[] textList;
    private TextView homeText, adviseText, discoverText, meText;
    private NoScrollViewPager mViewPager;
    private HomePageAdapter pageAdapter;
    private List<BaseFragment> fragmentList;

    private RelativeLayout common_layout_title;

    private FragmentManager mFragmentManager;
    private JNHomeFrontPageFragment frontPageFragment;
    private JNHomeSentencePageFragment sentencePageFragment;
    private JNHomeDiscoverPageFragment discoverPageFragment;
    private JNHomeMePageFragment mePageFragment;

    private int[] iconResNor;
    private int[] iconRespress;
    //数据
    private final int PAGE_HOME = 0;
    private final int PAGE_ADVISE = 1;
    private final int PAGE_DISCOVER = 2;
    private final int PAGE_ME = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
        initListner();
        initHomeViewPager();

    }

    private void init(){
        mViewPager = (NoScrollViewPager) findViewById(R.id.main_viewpager);
        addButton = (ImageButton) findViewById(R.id.main_page_add);
        homeBtn = (RelativeLayout) findViewById(R.id.main_home_button_rel);
        adviseBtn = (RelativeLayout) findViewById(R.id.main_advise_button_rel);
        discoverBtn = (RelativeLayout) findViewById(R.id.main_discover_button_rel);
        meBtn = (RelativeLayout) findViewById(R.id.main_me_button_rel);

        homeIcon = (ImageView) findViewById(R.id.main_home_icon);
        adviseIcon = (ImageView) findViewById(R.id.main_advise_icon);
        discoverIcon = (ImageView) findViewById(R.id.main_discover_icon);
        meIcon = (ImageView) findViewById(R.id.main_me_icon);
        iconList = new ImageView[]{homeIcon, adviseIcon, discoverIcon, meIcon};
        homeText = (TextView) findViewById(R.id.main_home_text);
        adviseText = (TextView) findViewById(R.id.main_advise_text);
        discoverText = (TextView) findViewById(R.id.main_discover_text);
        meText = (TextView) findViewById(R.id.main_me_text);
        textList = new TextView[]{homeText, adviseText, discoverText, meText};

        iconResNor = new int[]{R.drawable.main_bottom_bar_home_nor, R.drawable.main_bottom_bar_advise_nor,
                R.drawable.main_bottom_bar_discover_nor, R.drawable.main_bottom_bar_me_nor};
        iconRespress = new int[]{R.drawable.main_bottom_bar_home_press, R.drawable.main_bottom_bar_advise_press,
                R.drawable.main_bottom_bar_discover_press, R.drawable.main_bottom_bar_me_press};

        common_layout_title=(RelativeLayout) findViewById(R.id.common_layout_title);
    }

    private void initListner(){
        homeBtn.setOnClickListener(this);
        adviseBtn.setOnClickListener(this);
        discoverBtn.setOnClickListener(this);
        meBtn.setOnClickListener(this);
        addButton.setOnClickListener(this);
    }

    private void initHomeViewPager() {
        fragmentList = new ArrayList<BaseFragment>();

        frontPageFragment = JNHomeFrontPageFragment.newInstance(null);
        sentencePageFragment = JNHomeSentencePageFragment.newInstance(null);
        discoverPageFragment = JNHomeDiscoverPageFragment.newInstance(null);
        mePageFragment = JNHomeMePageFragment.newInstance(null);

        fragmentList.add(0, frontPageFragment);
        fragmentList.add(1, sentencePageFragment);
        fragmentList.add(2, discoverPageFragment);
        fragmentList.add(3, mePageFragment);

        mFragmentManager = getSupportFragmentManager();
        pageAdapter = new HomePageAdapter(mFragmentManager, fragmentList);
        mViewPager.setNoScroll(true);
        mViewPager.setAdapter(pageAdapter);

        mViewPager.setCurrentItem(0);
        changeButtonStyle(0);

    }

    @Override
    public void onClick(View view) {

        if(view==homeBtn){
            changeButtonStyle(PAGE_HOME);
            mViewPager.setCurrentItem(PAGE_HOME);
        }else if(view==adviseBtn){
            changeButtonStyle(PAGE_ADVISE);
            mViewPager.setCurrentItem(PAGE_ADVISE);
        }else if(view==discoverBtn){
            changeButtonStyle(PAGE_DISCOVER);
            mViewPager.setCurrentItem(PAGE_DISCOVER);
        }else if(view==meBtn){
            changeButtonStyle(PAGE_ME);
            mViewPager.setCurrentItem(PAGE_ME);
        }else if(view==addButton){
            Intent intent=new Intent(this,AddPlanActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        changeButtonStyle(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void changeButtonStyle(int index) {

        if(index==PAGE_ME){
            common_layout_title.setVisibility(View.GONE);
        }else{
            common_layout_title.setVisibility(View.VISIBLE);
        }

        for (int i = 0; i < iconList.length; i++) {
            if (index == i) {
                iconList[i].setImageResource(iconRespress[i]);
                textList[i].setTextColor(getResources().getColor(R.color.footbar_selected_color));
            } else {
                iconList[i].setImageResource(iconResNor[i]);
                textList[i].setTextColor((getResources().getColor(R.color.footbar_unselected_color)));
            }
        }
    }

}
