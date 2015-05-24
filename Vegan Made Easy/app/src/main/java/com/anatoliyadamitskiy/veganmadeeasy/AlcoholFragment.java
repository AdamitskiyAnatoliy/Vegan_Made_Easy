package com.anatoliyadamitskiy.veganmadeeasy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Anatoliy on 5/22/15.
 */
public class AlcoholFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    @InjectView(R.id.alcoholTextView)
    TextView textView;

    private int position;

    public static AlcoholFragment newInstance(int position) {
        AlcoholFragment f = new AlcoholFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.alcohol_fragment,container,false);
        ButterKnife.inject(this, rootView);
        ViewCompat.setElevation(rootView, 50);

        //Toast.makeText(getActivity(), "CARD "+position, Toast.LENGTH_SHORT).show();

        textView.setText("This is our alcohol fragment");
        return rootView;
    }

}
