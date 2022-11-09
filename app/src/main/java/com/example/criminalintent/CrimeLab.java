package com.example.criminalintent;

import static com.example.criminalintent.database.CrimeDbSchema.CrimeTable.Cols.DATE;
import static com.example.criminalintent.database.CrimeDbSchema.CrimeTable.Cols.SOLVED;
import static com.example.criminalintent.database.CrimeDbSchema.CrimeTable.Cols.TITLE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.criminalintent.database.CrimeBaseHelper;
import com.example.criminalintent.database.CrimeCursorWrapper;
import com.example.criminalintent.database.CrimeDbSchema;
import com.example.criminalintent.database.CrimeDbSchema.CrimeTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;
//    private List<Crime> mCrimes;
    private Context mContext;
    private SQLiteDatabase mDatabase;


    public static CrimeLab get(Context context) {

        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context){
//        mCrimes=new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            Crime crime=new Crime();
//            crime.setTile("Crime #"+i);
//            crime.setSolved(i%2==0);
//            mCrimes.add(crime);
//        }
        mContext=context.getApplicationContext();
        mDatabase=new CrimeBaseHelper(mContext).getWritableDatabase();
    }

    public List<Crime> getCrimes(){
//        return new ArrayList<>();
//        return mCrimes;

        List<Crime>crimes=new ArrayList<>();
        CrimeCursorWrapper cursor=queryCrime(null,null);
        try {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return crimes;
    }

//    public void addCrime(Crime c){
////        mCrimes.add(c);
//    }

    public Crime getCrime(UUID id){
//        for (Crime crime:mCrimes
//             ) {
//            if(crime.getMid().equals(id)){
//                return crime;
//            }
//        }
//        return null;
        CrimeCursorWrapper cursor=queryCrime(CrimeTable.Cols.UUID+"=?",new String[]{ id.toString()});
        try{
            if (cursor.getCount()==0){ 
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        }finally {
            cursor.close();
        }
    }

    private static ContentValues getContentValues(Crime crime){
        ContentValues values=new ContentValues();
        values.put(CrimeTable.Cols.UUID,crime.getMid().toString());
        values.put(TITLE, crime.getTile());
        values.put(DATE, crime.getDate().getTime());
        values.put(SOLVED, crime.isSolved() ? 1 : 0);
        return values;
    }

    public void addCrime(Crime c){
        ContentValues values=getContentValues(c);
        mDatabase.insert(CrimeTable.NAME,null,values);
    }
    public void updateCrime(Crime crime){
        String uuidString =crime.getMid().toString();
        ContentValues values=getContentValues(crime);
        mDatabase.update(CrimeTable.NAME,values,CrimeTable.Cols.UUID+"=?",new String[]{uuidString});
    }
    private CrimeCursorWrapper queryCrime(String whereClause, String[] whereArgs){
        Cursor cursor=mDatabase.query(
                CrimeTable.NAME,
                null,
                whereClause,
                whereArgs,null,null,null
        );
        return new CrimeCursorWrapper(cursor);
    }
}
