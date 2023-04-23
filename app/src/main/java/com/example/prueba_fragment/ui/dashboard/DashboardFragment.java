package com.example.prueba_fragment.ui.dashboard;

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
import com.example.prueba_fragment.databinding.FragmentDashboardBinding;
import com.example.prueba_fragment.utils.CustomTapTargetSequence;
import com.getkeepsafe.taptargetview.TapTarget;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private  CustomTapTargetSequence tapTargetSequence;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        Button btnDashboard=root.findViewById(R.id.button2);
        Button startTutorial = root.findViewById(R.id.startTutorial2);
        startTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapTargetSequence.repeat();
            }
        });

        TapTarget[] targets={
                TapTarget.forView(btnDashboard, "Boton dashboard", "Este boton  es de dashboard").targetRadius(80)
        };

        tapTargetSequence = new CustomTapTargetSequence(getActivity(),"Tutorial_2",targets);
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
}