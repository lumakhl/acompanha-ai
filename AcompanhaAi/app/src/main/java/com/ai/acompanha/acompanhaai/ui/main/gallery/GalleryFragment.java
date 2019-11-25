package com.ai.acompanha.acompanhaai.ui.main.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.acompanha.acompanhaai.R;
import com.ai.acompanha.acompanhaai.data.RegistroRepository;
import com.ai.acompanha.acompanhaai.data.model.Registro;

import java.util.ArrayList;
import java.util.Date;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        recyclerView = root.findViewById(R.id.rvRegistros);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RegistroAdapter(getRegistros());
        recyclerView.setAdapter(mAdapter);

        if (mAdapter.getItemCount() == 0){
            Toast.makeText(getContext(), R.string.msg_historico, Toast.LENGTH_LONG).show();
        }

        //final TextView textView = (TextView) root.findViewById(R.id.text_gallery);
        //galleryViewModel.getText().observe(this, new Observer<String>() {
          //  @Override
           // public void onChanged(@Nullable String s) {
             //   textView.setText(s);
           // }
        //});
        return root;
    }

    private ArrayList<Registro> getRegistros() {
        RegistroRepository registroRepository = new RegistroRepository(getContext());
        return registroRepository.buscarRegistros();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}