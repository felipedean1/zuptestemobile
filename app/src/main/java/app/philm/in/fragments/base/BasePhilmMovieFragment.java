

package app.philm.in.fragments.base;


import com.github.johnpersano.supertoasts.SuperCardToast;
import com.github.johnpersano.supertoasts.SuperToast;
import com.github.johnpersano.supertoasts.util.Style;

import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import app.philm.in.PhilmApplication;
import app.philm.in.controllers.MovieController;
import app.philm.in.model.ColorScheme;
import app.philm.in.network.NetworkError;
import app.philm.in.view.StringManager;


public abstract class BasePhilmMovieFragment extends BasePhilmFragment
        implements MovieController.MovieUi {

    private MovieController.MovieUiCallbacks mCallbacks;
    private ColorScheme mColorScheme;

    private SuperCardToast mToast;

    @Override
    public void onResume() {
        super.onResume();
        getController().attachUi(this);
    }

    @Override
    public void onPause() {
        cancelToast();
        getController().detachUi(this);
        super.onPause();
    }

    @Override
    public void showLoadingProgress(boolean visible) {
        // TODO: Implement
    }

    @Override
    public void showSecondaryLoadingProgress(boolean visible) {
        // NO-OP
    }

    @Override
    public void showError(NetworkError error) {
        showToast(StringManager.getStringResId(error), Style.getStyle(Style.RED));
    }

    protected final void cancelToast() {
        if (mToast != null) {
            mToast.dismiss();
        }
    }

    protected final void showToast(int text, Style style) {
        cancelToast();

        mToast = SuperCardToast.create(
                getActivity(), getText(text), SuperToast.Duration.MEDIUM, style);
        mToast.setIcon(SuperToast.Icon.Dark.INFO, SuperToast.IconPosition.LEFT);
        mToast.show();
    }

    protected final boolean hasCallbacks() {
        return mCallbacks != null;
    }

    protected final MovieController.MovieUiCallbacks getCallbacks() {
        return mCallbacks;
    }

    @Override
    public void setCallbacks(MovieController.MovieUiCallbacks callbacks) {
        mCallbacks = callbacks;
    }

    private MovieController getController() {
        return PhilmApplication.from(getActivity()).getMainController().getMovieController();
    }

    @Override
    public void setColorScheme(ColorScheme colorScheme) {
        if (mColorScheme != colorScheme) {
            mColorScheme = colorScheme;

            onColorSchemeChanged(colorScheme);
        }
    }

    protected ColorScheme getColorScheme() {
        return mColorScheme;
    }

    protected void onColorSchemeChanged(ColorScheme colorScheme) {
    }
}
