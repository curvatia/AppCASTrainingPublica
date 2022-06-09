package com.castraining.app_castraining_publica.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.castraining.app_castraining_publica.adapter.ConvocatoriaAdapter;
import com.castraining.app_castraining_publica.adapter.CursosAdapter;
import com.castraining.app_castraining_publica.databinding.FragmentDashboardBinding;
import com.castraining.app_castraining_publica.model.RcyViewDatosConvocatoria;
import com.castraining.app_castraining_publica.view.PubApi;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    //NEW
    private DashboardViewModel dashboardViewModel;
    private RecyclerView miReciclador;
    private ConvocatoriaAdapter adapter = new ConvocatoriaAdapter(new ArrayList<>());

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        this.miReciclador = binding.RecicladorView;
        this.miReciclador.setHasFixedSize(true);

        miReciclador.setLayoutManager((new LinearLayoutManager(getContext())));
        PubApi api = new PubApi();

        dashboardViewModel.adapter.observe(getViewLifecycleOwner(), new Observer<RecyclerView.Adapter>() {
            @Override
            public void onChanged(RecyclerView.Adapter adapter) {

            }
        });






        //final TextView textView = binding.textDashboard;
        //dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
    }
}