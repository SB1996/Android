package any.audio.Adapters;

import androidx.core.app.Fragment;
import androidx.core.app.FragmentManager;
import androidx.core.app.FragmentStatePagerAdapter;

/**
 * Created by Ankit on 11/27/2016.
 */

public class MainContentViewPagerAdapter extends FragmentStatePagerAdapter {

    public MainContentViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

}

