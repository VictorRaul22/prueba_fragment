package com.example.prueba_fragment.ui.home;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.prueba_fragment.R;
import com.example.prueba_fragment.databinding.FragmentHomeBinding;
import com.example.prueba_fragment.utils.CustomTapTargetSequence;
import com.example.prueba_fragment.utils.SMSLocationSender;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private  CustomTapTargetSequence tapTargetSequence;
    private SMSLocationSender smsLocationSender;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // handle SendSMS
        Button sendButton = root.findViewById(R.id.button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] Phonenumbers = {"967571629"};
                smsLocationSender = new SMSLocationSender(getActivity(), Phonenumbers);
                smsLocationSender.sendSMS();
            }
        });

        // TapTargetSequence
        TapTarget[] targets={
                TapTarget.forView(textView, "Texto", "Esto es un texto de prueba").targetRadius(90),
                TapTarget.forView(sendButton, "Boton SMS", "Este boton sirve para enviar SMS").targetRadius(60)
        };

        tapTargetSequence = new CustomTapTargetSequence(getActivity(),"Tutorial_1",targets);
        Button startTutorial=root.findViewById(R.id.startTutorial);
        startTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapTargetSequence.repeat();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void  onStart() {
        super.onStart();
        tapTargetSequence.startSequence();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (smsLocationSender != null) {
            smsLocationSender.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}