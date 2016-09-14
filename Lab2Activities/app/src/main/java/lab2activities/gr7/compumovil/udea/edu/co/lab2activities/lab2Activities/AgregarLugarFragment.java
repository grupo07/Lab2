package lab2activities.gr7.compumovil.udea.edu.co.lab2activities.lab2Activities;

/**
 * Created by dfrancisco.hernandez on 3/09/16.
 */
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import lab2activities.gr7.compumovil.udea.edu.co.lab2activities.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgregarLugarFragment extends Fragment {//agrega un lugar a la abse de datos
    Bitmap pict;
    private static final int REQUEST_CODE_GALLERY=1;
    private static final int REQUEST_CODE_CAMERA=2;
    private ImageView targetImageR;
    DbHelper dbH;
    SQLiteDatabase db;
    static final int PICK_REQUEST =1337;
    Uri contact = null;
    Button btnR;
    EditText[] txtValidateR = new EditText[4];
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dbH=new DbHelper(getActivity().getBaseContext());
        view=inflater.inflate(R.layout.fragment_agregar_lugar,container,false);
        pict = BitmapFactory.decodeResource(getActivity().getResources(), R.mipmap.ic_arrive);
        targetImageR = (ImageView)view.findViewById(R.id.LugarImagen);
        targetImageR.setImageBitmap(pict);
        txtValidateR[0]=(EditText)view.findViewById(R.id.NombreUbicacion);
        txtValidateR[1]=(EditText)view.findViewById(R.id.Descripción);
        txtValidateR[2]=(EditText)view.findViewById(R.id.Place);
        txtValidateR[3]=(EditText)view.findViewById(R.id.Temperatura);
        btnR = (Button)view.findViewById(R.id.buttonLugar);
        btnR.setEnabled(false);
        TextWatcher btnActivation = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(verificarVaciosSinMessageR(txtValidateR)){btnR.setEnabled(true);}
                else{btnR.setEnabled(false);}
            }
        };
        for (int n = 0; n < txtValidateR.length; n++)
        {
            txtValidateR[n].addTextChangedListener(btnActivation);
        }
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == PICK_REQUEST){
            if(resultCode == getActivity().RESULT_OK){
                contact = data.getData();
            }
        }
        if (resultCode == getActivity().RESULT_OK && (requestCode==REQUEST_CODE_GALLERY || requestCode==REQUEST_CODE_CAMERA)){
            try {
                Uri targetUri = data.getData();
                pict = redimensionarImagenMaximo(BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(targetUri)),400,350);
                targetImageR.setImageBitmap(pict);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean verificarVaciosSinMessageR(EditText[] txtValidate)
    {
        for(int i=0; i<txtValidate.length;i++)
        {
            if((txtValidate[i].getText().toString()).isEmpty())
            {
                return false;
            }
        }
        return true;
    }
    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
    public void ValidarPlaces() {//valida los lugares, que ya esten en la BD
        db = dbH.getWritableDatabase();
        ContentValues values = new ContentValues();
        Cursor search = db.rawQuery("select count(*) from " + StatusContract.TABLE_PLACE, null);
        search.moveToFirst();
        int aux=Integer.parseInt(search.getString(0));
        values.put(StatusContract.Column_Place.ID,(aux+1));
        values.put(StatusContract.Column_Place.NPLACE,txtValidateR[0].getText().toString());
        values.put(StatusContract.Column_Place.DESCRIPTION,txtValidateR[1].getText().toString());
        values.put(StatusContract.Column_Place.PLACE, txtValidateR[2].getText().toString());
        values.put(StatusContract.Column_Place.PUNTS, txtValidateR[3].getText().toString());
        search = db.rawQuery("select * from " + StatusContract.TABLE_LOGIN, null);
        search.moveToFirst();
        values.put(StatusContract.Column_Place.PICTURE,getBitmapAsByteArray(pict));
        db.insertWithOnConflict(StatusContract.TABLE_PLACE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }
    public void ClickGalleryR() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_GALLERY);
    }
    public void ClickCameraR() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA);
    }
    public Bitmap redimensionarImagenMaximo(Bitmap mBitmap, float newWidth, float newHeigth){
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeigth) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
    }
}
