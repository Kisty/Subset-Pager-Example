package uk.co.imagitech.subsetpagerexample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	private SimplePagerAdapter mPagerAdapter = new SimplePagerAdapter(getSupportFragmentManager());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(mPagerAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_subset) {
			mPagerAdapter.toggleSubset();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	public class SimplePagerAdapter extends FragmentStatePagerAdapter {

		boolean mSubset = false;
		/**
		 * @param fm
		 */
		public SimplePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		/**
		 * 
		 */
		public void toggleSubset() {
			mSubset = !mSubset;
			notifyDataSetChanged();
		}

		/* (non-Javadoc)
		 * @see android.support.v4.app.FragmentStatePagerAdapter#getItem(int)
		 */
		@Override
		public Fragment getItem(int position) {
			return SimpleFragment.newInstance(position);
		}

		/* (non-Javadoc)
		 * @see android.support.v4.view.PagerAdapter#getItemPosition(java.lang.Object)
		 */
		@Override
		public int getItemPosition(Object object) {
			if (mSubset && 
					object instanceof Fragment) {
				Fragment f = (Fragment) object;
				final int page = f.getArguments().getInt(SimpleFragment.ARG_ID);
				if (page % 2 == 0) {
					return page / 2;
				} else {
					return getCount() + page / 2/*POSITION_NONE*/;
				}
			} else {
				return POSITION_NONE;
			}
		}

		/* (non-Javadoc)
		 * @see android.support.v4.view.PagerAdapter#getCount()
		 */
		@Override
		public int getCount() {
			if (mSubset) {
				return 3;
			} else {
				return 6;
			}
		}
		
		
	}
	
	public static class SimpleFragment extends Fragment {

		/**
		 * 
		 */
		private static final String ARG_ID = "id";
		private TextView mTv;

		public static SimpleFragment newInstance(int id) {
			final SimpleFragment simpleFragment = new SimpleFragment();
			final Bundle args = new Bundle();
			args.putInt(ARG_ID, id);
			simpleFragment.setArguments(args);
			return simpleFragment;
		}
		
		/* (non-Javadoc)
		 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
		 */
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			final View view = inflater.inflate(R.layout.fragment_simple, container, false);
			mTv = (TextView) view.findViewById(R.id.text);
			return view;
		}

		/* (non-Javadoc)
		 * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
		 */
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			
			final Bundle arguments = getArguments();
			if (arguments != null) {
				mTv.setText(Integer.toString(arguments.getInt(ARG_ID)));
			}
		}
	}
}
