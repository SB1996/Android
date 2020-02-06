package any.audio.Adapters;

import android.content.Context;
import androidx.core.app.Fragment;
import androidx.core.app.FragmentManager;
import androidx.core.app.FragmentPagerAdapter;
import androidx.core.app.FragmentStatePagerAdapter;
import android.util.Log;

import any.audio.Fragments.DownloadedFragment;
import any.audio.Fragments.DownloadingFragment;

/**
 * Created by Ankit on 2/22/2017.
 */

public class DownloadsFragmentPagerAdapter extends FragmentStatePagerAdapter {

    Context mContext;

    public DownloadsFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        // Create fragment object
        Fragment fragment = null;

        if(position==0){
            fragment = new DownloadingFragment();
        }else {
            fragment = new DownloadedFragment();
        }
        Log.d("Downloads",position+ " returning fragment  pos - "+fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (position==0)?"DOWNLOADING":"DOWNLOADED";
    }

}
