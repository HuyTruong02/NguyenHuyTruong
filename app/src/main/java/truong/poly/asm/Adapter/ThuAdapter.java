package truong.poly.asm.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import truong.poly.asm.fragment.KhoanthuFragment;
import truong.poly.asm.fragment.LoaithuFragment;


public class ThuAdapter extends FragmentStateAdapter {
    public ThuAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0)
        return new LoaithuFragment();
        return new KhoanthuFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
