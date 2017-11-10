package com.hpw.mvpframe.base;

import android.content.Context;

public interface CoreBaseView {
    Context getContext();

    void showError(String msg);
}
