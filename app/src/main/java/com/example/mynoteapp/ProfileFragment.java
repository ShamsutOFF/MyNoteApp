package com.example.mynoteapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;


public class ProfileFragment extends Fragment {
    public static final String DOSSIER_ARGS_KEY = "DOSSIER_ARGS_KEY";
    private static final String TAG = "@@@ ProfileFragment";
    private DossierEntity dossier = null;
    private EditText nameEt;
    private EditText surnameEt;
    private EditText emailEt;
    private Button saveButton;

    public static ProfileFragment newInstance(DossierEntity dossierEntity) {
        ProfileFragment profileFragment = new ProfileFragment();
        Bundle args = new Bundle();

        args.putParcelable(DOSSIER_ARGS_KEY, dossierEntity);

        profileFragment.setArguments(args);
        return profileFragment;
    }

    public interface Controller {
        void saveResult(DossierEntity dossier);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called with: inflater = [" + inflater + "], container = [" + container + "], savedInstanceState = [" + savedInstanceState + "]");
        View view = inflater.inflate(R.layout.fragment_profile, null);

        nameEt = view.findViewById(R.id.name_edit_text);
        surnameEt = view.findViewById(R.id.surname_edit_text);
        emailEt = view.findViewById(R.id.email_edit_text);

        saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> {
            Controller controller = (Controller) getActivity();
            controller.saveResult(new DossierEntity(
                    nameEt.getText().toString(),
                    surnameEt.getText().toString(),
                    emailEt.getText().toString()
            ));
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated() called with: view = [" + view + "], savedInstanceState = [" + savedInstanceState + "]");

        nameEt.setText(dossier.name);
        surnameEt.setText(dossier.surname);
        emailEt.setText(dossier.email);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach() called with: context = [" + context + "]");
        if (!(context instanceof Controller)) {
            throw new RuntimeException("Activity must implements ProfileController!");
        }
        if (getArguments() != null) {
            dossier = getArguments().getParcelable(DOSSIER_ARGS_KEY);
        }
    }
}


