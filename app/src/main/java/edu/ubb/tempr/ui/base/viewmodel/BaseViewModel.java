package edu.ubb.tempr.ui.base.viewmodel;

import android.databinding.BaseObservable;
import android.support.annotation.CallSuper;

import edu.ubb.tempr.ui.base.view.MvvmView;

/**
 * Created by zsoltszabo on 6/17/17.
 */

public abstract class BaseViewModel<V extends MvvmView> extends BaseObservable implements MvvmViewModel<V>{
    private V view;

    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @CallSuper
    public void detachView() {
        this.view = null;
    }

    protected final V view() {
        return view;
    }
}