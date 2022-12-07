package truong.poly.asm.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import truong.poly.asm.Database.KhoanThuChiDB;
import truong.poly.asm.model.KhoanThuChi;
import truong.poly.asm.model.Loai;


public class KhoanThuChiDAO {
    KhoanThuChiDB khoanthuchiDB;
    public KhoanThuChiDAO(Context context){
        khoanthuchiDB = new KhoanThuChiDB(context);
    }

    //lấy danh sách loại thu/chi
    public ArrayList<Loai> getDSloai(String loai){
        ArrayList<Loai> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = khoanthuchiDB.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT*FROM LOAI" ,null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                String trangthai = cursor.getString(2);
                if(trangthai.equals(loai)){
                    list.add(new Loai(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
                }
            }while (cursor.moveToNext());
        }
        return list;
    }
    //Thêm loại thu/chi
    public boolean themMoiLoaiThuChi(Loai loai){
        SQLiteDatabase sqLiteDatabase = khoanthuchiDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai",loai.getTenloai());
        contentValues.put("trangthai",loai.getTrangthai());
        long check = sqLiteDatabase.insert("LOAI",null, contentValues);
        if(check== -1)
            return false;
        return true;
    }

    //cap nhat
    public boolean capnhatLoaiThuChi (Loai loai){
        SQLiteDatabase sqLiteDatabase = khoanthuchiDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maloai",loai.getMaloai());
        contentValues.put("tenloai",loai.getTenloai());
        contentValues.put("trangthai",loai.getTrangthai());
        long check = sqLiteDatabase.update("LOAI", contentValues,"maloai = ?",new String[]{String.valueOf(loai.getMaloai())});
        if(check== -1)
            return false;
        return true;
    }

    //xoa
    public boolean xoatLoaiThuChi (int maloai){
        SQLiteDatabase sqLiteDatabase = khoanthuchiDB.getWritableDatabase();
        long check = sqLiteDatabase.delete("LOAI","maloai = ?", new String[]{String.valueOf(maloai)} );
        if(check== -1)
            return false;
        return true;
    }

    public ArrayList<KhoanThuChi> getDSKhoanThuChi(String loai){
        ArrayList<KhoanThuChi> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = khoanthuchiDB.getReadableDatabase();
        String query = "select k.makhoan, k.tien , k.maloai, l.tenloai from khoanthuchi k,loai l where k.maloai = l.maloai and l.trangthai = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{loai});
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                list.add(new KhoanThuChi(cursor.getInt(0), cursor.getInt(1),cursor.getInt(2), cursor.getString(3)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public boolean themmoikhoanthuchi(KhoanThuChi khoanThuChi){
        SQLiteDatabase sqLiteDatabase = khoanthuchiDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tien", khoanThuChi.getTien());
        contentValues.put("maloai", khoanThuChi.getMaloai());
        long check = sqLiteDatabase.insert("khoanthuchi", null, contentValues);
        if(check == -1){
            return false;
        }
        return true;
    }

    public boolean capnhatkhoanthuchi(KhoanThuChi khoanThuChi){
        SQLiteDatabase sqLiteDatabase = khoanthuchiDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tien", khoanThuChi.getTien());
        contentValues.put("maloai", khoanThuChi.getMaloai());
        long check = sqLiteDatabase.update("KHOANTHUCHI", contentValues ,"makhoan = ?", new String[]{String.valueOf(khoanThuChi.getMakhoan())});
        if(check == -1){
            return false;
        }
        return true;
    }
    public boolean xoakhoanthuchi(int makhoan){
        SQLiteDatabase sqLiteDatabase = khoanthuchiDB.getWritableDatabase();
        long check = sqLiteDatabase.delete("KHOANTHUCHI", "makhoan = ?", new String[]{String.valueOf(makhoan)});
        if(check == -1){
            return false;
        }
        return true;
    }
}

