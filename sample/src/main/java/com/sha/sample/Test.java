package com.sha.sample;

import com.sha.bulletin.InfoSheetAlertable;

import org.jetbrains.annotations.Nullable;

import androidx.fragment.app.FragmentActivity;

public class Test implements InfoSheetAlertable {
    @Nullable
    @Override
    public FragmentActivity activity() {
        return null;
    }

}
