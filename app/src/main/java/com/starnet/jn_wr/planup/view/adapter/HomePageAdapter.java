package com.starnet.jn_wr.planup.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.starnet.jn_wr.planup.view.fragment.BaseFragment;
import java.util.List;

public class HomePageAdapter extends FragmentStatePagerAdapter {

	private List<BaseFragment> fragmentList;

	public HomePageAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
		super(fm);
		this.fragmentList = fragmentList;
	}

	@Override
	public Fragment getItem(int position) {
		return fragmentList.get(position);
	}

	@Override
	public int getCount() {
		return fragmentList.size();
	}
}
