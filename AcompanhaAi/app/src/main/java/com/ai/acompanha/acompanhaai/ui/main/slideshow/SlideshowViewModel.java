package com.ai.acompanha.acompanhaai.ui.main.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SlideshowViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SlideshowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Ainda não foi possível prevermos seu consumo, tente de novo mais tarde.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}