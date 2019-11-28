package com.ai.acompanha.acompanhaai.ui.main.tools;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ToolsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ToolsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Não temos nenhuma configuração disponível para sua localidade");
    }

    public LiveData<String> getText() {
        return mText;
    }
}