package truong.poly.asm.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import truong.poly.asm.Dao.KhoanThuChiDAO;
import truong.poly.asm.R;
import truong.poly.asm.model.KhoanThuChi;


public class KhoanChiAdapter extends BaseAdapter {
    private ArrayList<KhoanThuChi>list;
    private Context context;
    private KhoanThuChiDAO thuChiDAO;
    private ArrayList<HashMap<String, Object>> listSpinner;

    public KhoanChiAdapter(ArrayList<KhoanThuChi> list, Context context, KhoanThuChiDAO thuChiDAO, ArrayList<HashMap<String, Object>> listSpinner) {
        this.list = list;
        this.context = context;
        this.thuChiDAO = thuChiDAO;
        this.listSpinner = listSpinner;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public  static  class ViewOfitem{
        TextView txtTen , txtTien;
        ImageView ivSua , ivXoa;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        ViewOfitem viewOfitem;
        if(view == null){
            view = inflater.inflate(R.layout.item_khoanchi, viewGroup, false);
            viewOfitem = new ViewOfitem();
            viewOfitem.txtTen = view.findViewById(R.id.txtTen);
            viewOfitem.txtTien = view.findViewById(R.id.txtTien);
            viewOfitem.ivSua = view.findViewById(R.id.ivSua);
            viewOfitem.ivXoa = view.findViewById(R.id.ivXoa);
            view.setTag(viewOfitem);
        }else {
            viewOfitem = (ViewOfitem)   view.getTag();
        }
        viewOfitem.txtTen.setText(list.get(i).getTenloai());
        viewOfitem.txtTien.setText(String.valueOf(list.get(i).getTien()));
        viewOfitem.ivSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogSua(list.get(i));
            }
        });
        viewOfitem.ivXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Bạn có muốn xóa ?");
                builder.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int makhoan = list.get(i).getMakhoan();
                        if(thuChiDAO.xoakhoanthuchi(makhoan)){
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            readload();
                        }else{
                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                Dialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }

    private void showDialogSua(KhoanThuChi khoanThuChi){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_suakhoanthuchi, null);
        Spinner spnLoaithu = view.findViewById(R.id.spnLoaithu);
        EditText edtTien = view.findViewById(R.id.edtInput);
        builder.setView(view);
        SimpleAdapter adapter = new SimpleAdapter(
                context,
                listSpinner,
                android.R.layout.simple_list_item_1,
                new String[]{"tenloai"},
                new int[]{android.R.id.text1}
        );
        spnLoaithu.setAdapter(adapter);
        edtTien.setText(String.valueOf(khoanThuChi.getTien()));
        int index = 0;
        int vitri = -1;
        for(HashMap<String, Object> item : listSpinner){
            if((int)item.get("maloai") == khoanThuChi.getMaloai())
                vitri = index;
            index++;
        }
        spnLoaithu.setSelection(vitri);

        builder.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String tien= edtTien.getText().toString();
                HashMap<String, Object> selected =(HashMap<String, Object>) spnLoaithu.getSelectedItem();
                int maloai =(int) selected.get("maloai");
                khoanThuChi.setTien(Integer.parseInt(tien));
                khoanThuChi.setMaloai(maloai);
                if(thuChiDAO.capnhatkhoanthuchi(khoanThuChi)) {
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    readload();
                }else{
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog= builder.create();
        alertDialog.show();
    }

    private void readload(){
        list.clear();
        list = thuChiDAO.getDSKhoanThuChi("chi");
        notifyDataSetChanged();
    }

}
