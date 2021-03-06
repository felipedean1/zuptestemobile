

package app.philm.in.tasks;

import com.google.common.base.Preconditions;

import com.jakewharton.trakt.entities.RatingResponse;
import com.jakewharton.trakt.enumerations.Rating;
import com.jakewharton.trakt.services.RateService;

import app.philm.in.model.PhilmMovie;
import app.philm.in.network.NetworkError;
import app.philm.in.state.MoviesState;
import retrofit.RetrofitError;

public class SubmitTraktMovieRatingRunnable extends BaseMovieRunnable<RatingResponse> {

    private final String mId;
    private final Rating mRating;

    public SubmitTraktMovieRatingRunnable(int callingId, String id, Rating rating) {
        super(callingId);
        mId = Preconditions.checkNotNull(id, "id cannot be null");
        mRating = Preconditions.checkNotNull(rating, "rating cannot be null");
    }

    @Override
    public RatingResponse doBackgroundCall() throws RetrofitError {
        return getTraktClient().rateService().movie(new RateService.MovieRating(mId, mRating));
    }

    @Override
    public void onSuccess(RatingResponse result) {
        if (RESULT_TRAKT_SUCCESS.equals(result.status)) {
            PhilmMovie movie = mMoviesState.getMovie(mId);
            if (movie != null) {
                if (result.rating != null) {
                    movie.setUserRatingAdvanced(result.rating);
                } else {
                    movie.setUserRatingAdvanced(mRating);
                }
                getDbHelper().put(movie);

                getEventBus().post(new MoviesState.MovieUserRatingChangedEvent(getCallingId(), movie));
            }
        }
    }

    @Override
    protected int getSource() {
        return NetworkError.SOURCE_TRAKT;
    }
}