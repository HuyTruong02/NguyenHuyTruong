package truong.poly.asm.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

import truong.poly.asm.Adapter.KhoanThuAdapter;
import truong.poly.asm.Dao.KhoanThuChiDAO;
import truong.poly.asm.R;
import truong.poly.asm.model.KhoanThuChi;
import truong.poly.asm.model.Loai;


public class KhoanthuFragment extends Fragment {
    ListView listViewKhoanthu;
    ArrayList<KhoanThuChi> list;
    KhoanThuChiDAO khoanThuChiDAO;
    KhoanThuAdapter adapter;
    ArrayList<HashMap<String, Object>> listSpinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.khoanthu_fragment,container,false);
        listViewKhoanthu = view.findViewById(R.id.lvkhoanthu);
        FloatingActionButton floatADD = view.findViewById(R.id.floatadd);
        khoanThuChiDAO = new KhoanThuChiDAO(getContext());

        getData();

        floatADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogThem();
            }
        });

        return view;
    }
    private void getData(){
        list = khoanThuChiDAO.getDSKhoanThuChi("thu");

        adapter =  new KhoanThuAdapter(list,getContext(),khoanThuChiDAO, getDataSpiner());

        listViewKhoanthu.setAdapter(adapter);
    }
    private void showDialogThem(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_themkhoanthuchi, null);
        Spinner spnloaithu = view.findViewById(R.id.spnLoaithu);
        EditText edtTien = view.findViewById(R.id.edtInput);
        builder.setView(view);
        SimpleAdapter adapter = new SimpleAdapter(
                getContext(),
                getDataSpiner(),
                android.R.layout.simple_list_item_1,
                new String[]{"tenloai"},
                new int[]{android.R.id.text1}
        );
        spnloaithu.setAdapter(adapter);
        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                   String tien = edtTien.getText().toString();
                   HashMap<String, Object> selected =(HashMap<String, Object>) spnloaithu.getSelectedItem();
                   int maloai =(int) selected.get("maloai");
                   KhoanThuChi khoanThuChi = new KhoanThuChi(Integer.parseInt(tien), maloai);
                   if(khoanThuChiDAO.themmoikhoanthuchi(khoanThuChi)){
                       Toast.makeText(getContext(), "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                       getData();
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

    private ArrayList<HashMap<String, Object>> getDataSpiner(){
        ArrayList<Loai> listLoai = khoanThuChiDAO.getDSloai("thu");
        listSpinner = new ArrayList<>();

        for(Loai loai : listLoai){
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("maloai", loai.getMaloai());
            hashMap.put("tenloai", loai.getTenloai());
            listSpinner.add(hashMap);
        }
        return listSpinner;
    }
}
