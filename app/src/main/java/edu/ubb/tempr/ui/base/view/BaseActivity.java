package edu.ubb.tempr.ui.base.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import edu.ubb.tempr.BR;
import edu.ubb.tempr.ui.base.viewmodel.MvvmViewModel;

/**
 * Created by zsoltszabo on 6/17/17.
 */

public abstract class BaseActivity<V extends MvvmViewModel> extends AppCompatActivity implements MvvmView{

    protected ViewDataBinding binding;
//    @Inject protected V viewModel;
    protected V viewModel;

    protected final void setAndBindContentView(@LayoutRes int layoutResId, V viewModel) {
        binding = DataBindingUtil.setContentView(this, layoutResId);
        binding.setVariable(BR.viewModel, viewModel);
        viewModel.attachView(this);
    }

    @Override @CallSuper protected void onDestroy() {
        super.onDestroy();
        if(viewModel != null) { viewModel.detachView(); }
        binding = null;
        viewModel = null;
    }
}
