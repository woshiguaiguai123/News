package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;

/**
 * Created by xcdq on 2017/2/20.
 */

public class NewsFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragment;
    private List<String> mTitles;

    public NewsFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(List<Fragment> mFragment) {
        this.mFragment = mFragment;
    }

    public void addmtitles(List<String> mTitles) {
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
