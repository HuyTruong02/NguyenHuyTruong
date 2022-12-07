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

import truong.poly.asm.Adapter.ChiAdapter;
import truong.poly.asm.R;


public class ChiFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chi_fragment,container,false);
        TabLayout tableLayoutchi = view.findViewById(R.id.tabLayoutchi);
        ViewPager2 viewPager2chi = view.findViewById(R.id.viewViewPager2chi);

        ChiAdapter adapter= new ChiAdapter(getActivity());
        viewPager2chi.setAdapter(adapter);
        new TabLayoutMediator(tableLayoutchi, viewPager2chi, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position==0)
                    tab.setText("Loại chi");
                else
                    tab.setText("Khoản chi");
            }
        }).attach();
        return view;
    }
}

