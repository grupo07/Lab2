package lab2activities.gr7.compumovil.udea.edu.co.lab2activities.lab2Activities;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lab2activities.gr7.compumovil.udea.edu.co.lab2activities.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AcercadeFragment extends Fragment {


    public AcercadeFragment() {//fragmen creado para visualizar el acerca de de la aplicacion
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_acercade, container, false);
    }

}
