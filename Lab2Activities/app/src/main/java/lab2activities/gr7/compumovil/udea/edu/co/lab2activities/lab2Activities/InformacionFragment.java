package lab2activities.gr7.compumovil.udea.edu.co.lab2activities.lab2Activities;
/**
 * Created by dfrancisco.hernandez on 3/09/16.
 */

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import lab2activities.gr7.compumovil.udea.edu.co.lab2activities.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class InformacionFragment extends Fragment {
    private ImageView targetImageR;
    DbHelper dbHelper;
    SQLiteDatabase db;
    TextView[] txtValidateR = new TextView[3];
    View view;

    public InformacionFragment() {//activity que enseña información

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dbHelper =new DbHelper(getActivity().getBaseContext());

        view=inflater.inflate(R.layout.fragment_informacion, container, false);
        txtValidateR[0]=(TextView)view.findViewById(R.id.viewName);
        txtValidateR[1]=(TextView)view.findViewById(R.id.viewPhone);
        txtValidateR[2]=(TextView)view.findViewById(R.id.viewMail);
        targetImageR=(ImageView)view.findViewById(R.id.viewProfile);
        db= dbHelper.getWritableDatabase();
        Cursor search = db.rawQuery("select * from " + StatusContract.TABLE_LOGIN, null);
        search.moveToFirst();
        String aux = search.getString(1);
        search = db.rawQuery("select * from "+StatusContract.TABLE_USER+ " where "+StatusContract.Column_User.NAME+"='"+aux+"'", null);
        search.moveToFirst();
        txtValidateR[0].setText("Usuario: "+search.getString(1));
        txtValidateR[1].setText("Teléfono: "+search.getString(4));
        txtValidateR[2].setText("E-mail:"+search.getString(2));
        byte[] auxx=search.getBlob(5);
        Bitmap pict= BitmapFactory.decodeByteArray(auxx, 0, (auxx).length);
        targetImageR.setImageBitmap(pict);
        db.close();
        return view;
    }
}
