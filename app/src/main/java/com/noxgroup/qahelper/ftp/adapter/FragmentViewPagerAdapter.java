package com.noxgroup.qahelper.ftp.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.noxgroup.qahelper.base.BaseFragment;

import java.util.List;

/**
 * @author huangjian
 * @create 2019/1/24
 * @Description
 */
public class FragmentViewPagerAdapter extends FragmentPagerAdapter {
    private final List<? extends BaseFragment> fragmentList;
    private final List<String> titleList;

    public FragmentViewPagerAdapter(FragmentManager fm, List<? extends BaseFragment> fragmentList,
                                    List<String> titleList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titleList = titleList;
    }


    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
