package edu.ubb.tempr.ui.base.viewmodel;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Created by zsoltszabo on 6/17/17.
 */

public interface MvvmViewModel<V> extends Observable {
    void attachView(V view);
    void detachView();
}
