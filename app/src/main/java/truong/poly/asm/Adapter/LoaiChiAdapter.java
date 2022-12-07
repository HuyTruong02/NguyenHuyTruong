package truong.poly.asm.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

import truong.poly.asm.Dao.KhoanThuChiDAO;
import truong.poly.asm.R;
import truong.poly.asm.model.Loai;


public class LoaiChiAdapter extends BaseAdapter {
    private ArrayList<Loai> list;
    private Context context;
    private KhoanThuChiDAO khoanThuChiDAO;

    public LoaiChiAdapter(ArrayList<Loai> list, Context context, KhoanThuChiDAO khoanThuChiDAO) {
        this.list = list;
        this.context = context;
        this.khoanThuChiDAO = khoanThuChiDAO;
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
    public  static  class ViewOfItem{
        TextView txTen;
        ImageView ivSua, ivXoa;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        ViewOfItem viewOfItem;

        if(view== null){
            viewOfItem = new ViewOfItem();
            view = inflater.inflate(R.layout.item_loaichi, viewGroup, false);
            viewOfItem.txTen = view.findViewById(R.id.txtTen);
            viewOfItem.ivSua = view.findViewById(R.id.ivSua);
            viewOfItem.ivXoa = view.findViewById(R.id.ivXoa);
            view.setTag(viewOfItem);
        }else{
            viewOfItem =(ViewOfItem) view.getTag();
        }
        viewOfItem.txTen.setText(list.get(i).getTenloai());
        viewOfItem.ivSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogSuaLoaiThu(list.get(i));
            }
        });
        viewOfItem.ivXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                builder.setTitle("Bạn có muốn xóa ?");
                builder.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int idCanxoa = list.get(i).getMaloai();
                        if(khoanThuChiDAO.xoatLoaiThuChi(idCanxoa)){
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            readLoadData();

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
    private void showDialogSuaLoaiThu(Loai loai) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater  inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sualoaithu,null);
        EditText edtInput = view.findViewById(R.id.edtInput);
        builder.setView(view);

        edtInput.setText(loai.getTenloai());

        builder.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String tenloai = edtInput.getText().toString();
                loai.setTenloai(tenloai);
                if(khoanThuChiDAO.capnhatLoaiThuChi(loai)){
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    readLoadData();
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
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void readLoadData(){
        list.clear();
        list = khoanThuChiDAO.getDSloai("chi");
        notifyDataSetChanged();
    }

}