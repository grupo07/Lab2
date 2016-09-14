package lab2activities.gr7.compumovil.udea.edu.co.lab2activities.lab2Activities;
/**
 * Created by dfrancisco.hernandez on 3/09/16.
 */

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import lab2activities.gr7.compumovil.udea.edu.co.lab2activities.R;

public class LugarList extends ListFragment {
    DbHelper dbHelper;
    SQLiteDatabase db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        dbHelper =new DbHelper(getActivity().getBaseContext());
        ListarSitios();
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    public void ListarSitios(){//operacion para listar sitios, muestra en el activity los sitios guardados
        ArrayList<String> lug = new ArrayList(),desc = new ArrayList(),place = new ArrayList(),scores= new ArrayList(),ids = new ArrayList();
        ArrayList picture = new ArrayList();
        boolean control=false;
        db= dbHelper.getReadableDatabase();
        Cursor test=db.rawQuery("select * from "+StatusContract.TABLE_PLACE+" order by "+ StatusContract.Column_Place.NPLACE, null);
        if (test.moveToFirst()) {
            do{
                ids.add(test.getString(0));
                lug.add(test.getString(1));
                desc.add(test.getString(2));
                place.add(test.getString(3));
                scores.add(test.getString(4));
                picture.add(test.getBlob(5));
            }while(test.moveToNext());
            control=true;
        } else{
            Toast.makeText(getActivity().getBaseContext(),"Sin sitios",Toast.LENGTH_LONG).show();
        }
        db.close();
        if(control) {
            ArrayList aList=new ArrayList();
            for (int i = 0; i < ids.size(); i++) {
                HashMap<String, Object> hm = new HashMap<String, Object>();
                hm.put("lug", "Lugar: " + lug.get(i));
                hm.put("desc", "Descripción : " + desc.get(i));
                hm.put("place", "Sitio : " + place.get(i));
                hm.put("score", "Temperatura : " + scores.get(i));
                hm.put("picture", BitmapFactory.decodeByteArray((byte[]) picture.get(i), 0, ((byte[]) picture.get(i)).length));
                aList.add(hm);
            }
            String from[];
            int to[];
            from = new String[]{"lug", "desc", "place", "score", "picture"};
            to = new int[]{R.id.nplaces, R.id.desc, R.id.place, R.id.score, R.id.picture};
            ExtendedSimpleAdapter adapter = new ExtendedSimpleAdapter(getActivity().getBaseContext(), aList, R.layout.listview_layout, from, to);
            setListAdapter(adapter);
        }
    }
}
