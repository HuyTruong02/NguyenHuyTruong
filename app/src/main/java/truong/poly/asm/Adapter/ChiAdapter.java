package truong.poly.asm.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import truong.poly.asm.fragment.KhoanchiFragment;
import truong.poly.asm.fragment.LoaichiFragment;


public class ChiAdapter extends FragmentStateAdapter {
    public ChiAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0)
            return new LoaichiFragment();
        return new KhoanchiFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
