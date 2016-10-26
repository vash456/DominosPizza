package com.estrada.darlin.appdominospizza;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    SharedPreferences prefs;

    private TextView t_name, t_email;
    private String name, email;

    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        t_name = (TextView) view.findViewById(R.id.t_name);
        t_email = (TextView) view.findViewById(R.id.t_email);
        prefs = this.getActivity().getSharedPreferences("preferencia", Context.MODE_PRIVATE);
        refreshPrefs();
        t_name.setText(name);
        t_email.setText(email);

        return view;
    }

    public void refreshPrefs(){
        name = String.valueOf(prefs.getString("var_name","Nombre no definido"));
        email = String.valueOf(prefs.getString("var_email","Email no definido"));
    }

}
