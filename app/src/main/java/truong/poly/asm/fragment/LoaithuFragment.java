package truong.poly.asm.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import truong.poly.asm.Adapter.LoaiThuAdapter;
import truong.poly.asm.Dao.KhoanThuChiDAO;
import truong.poly.asm.R;
import truong.poly.asm.model.Loai;


public class LoaithuFragment extends Fragment {
    ListView lvloaithu;
    FloatingActionButton floatadd;
    LoaiThuAdapter loaiThuAdapter;
    ArrayList<Loai> list;
    KhoanThuChiDAO khoanThuChiDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loaithu_fragment,container,false);

        lvloaithu =view.findViewById(R.id.lvloaithu);
        floatadd = view.findViewById(R.id.floatadd);

        khoanThuChiDAO= new KhoanThuChiDAO(getContext());
        loadData();

        floatadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogThem();
            }
        });

        return view;
    }
    private  void loadData(){
        list = khoanThuChiDAO.getDSloai("thu");

        loaiThuAdapter = new LoaiThuAdapter(list, getContext(), khoanThuChiDAO);
        lvloaithu.setAdapter(loaiThuAdapter);
    }

    private void showDialogThem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_themloaithu, null);

        EditText editText = view.findViewById(R.id.edtInput);
        builder.setView(view);

        builder.setPositiveButton("Thêm ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String tenloai =editText.getText().toString();
                Loai loaiThem = new Loai(tenloai,"thu");
                if(khoanThuChiDAO.themMoiLoaiThuChi(loaiThem)){
                    Toast.makeText(getContext(), "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                }else{
                    Toast.makeText(getContext(), "Thêm mới thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
