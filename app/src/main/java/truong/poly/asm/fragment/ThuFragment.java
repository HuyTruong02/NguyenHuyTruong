package truong.poly.asm.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import truong.poly.asm.Adapter.ThuAdapter;
import truong.poly.asm.R;


public class ThuFragment extends Fragment {
    public static ThuFragment newInstance() {
        ThuFragment fragment = new ThuFragment();
        return fragment;
    }
    public ThuFragment() {
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thu_fragment,container,false);
        TabLayout tableLayoutthu = view.findViewById(R.id.tabLayoutthu);
        ViewPager2 viewPager2thu = view.findViewById(R.id.viewViewPager2thu);

        ThuAdapter adapter= new ThuAdapter(getActivity());
        viewPager2thu.setAdapter(adapter);
        new TabLayoutMediator(tableLayoutthu, viewPager2thu, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                   if(position==0)
                       tab.setText("Loại thu");
                   else
                       tab.setText("Khoản thu");
            }
        }).attach();
        return view;
    }
}

