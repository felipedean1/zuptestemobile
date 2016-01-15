

package app.philm.in.state.mappers;

import com.uwetrottmann.tmdb.entities.Credits;
import com.uwetrottmann.tmdb.entities.CrewMember;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import app.philm.in.model.PhilmMovieCredit;
import app.philm.in.model.PhilmPerson;
import app.philm.in.state.MoviesState;

@Singleton
public class TmdbCrewEntityMapper extends BaseEntityMapper<CrewMember, PhilmPerson> {

    @Inject
    public TmdbCrewEntityMapper(MoviesState state) {
        super(state);
    }

    @Override
    public PhilmPerson map(CrewMember entity) {
        PhilmPerson item = getEntity(String.valueOf(entity.id));

        if (item == null) {
            // No item, so create one
            item = new PhilmPerson();
        }

        // We already have a movie, so just update it wrapped value
        item.setFromTmdb(entity);
        putEntity(item);

        return item;
    }

    public List<PhilmMovieCredit> mapCredits(List<CrewMember> entities) {
        final ArrayList<PhilmMovieCredit> credits = new ArrayList<>(entities.size());
        for (CrewMember entity : entities) {
            credits.add(new PhilmMovieCredit(map(entity), entity.job, entity.department));
        }
        Collections.sort(credits);
        return credits;
    }

    @Override
    PhilmPerson getEntity(String id) {
        return mMoviesState.getPeople().get(id);
    }

    @Override
    void putEntity(PhilmPerson entity) {
        mMoviesState.getPeople().put(String.valueOf(entity.getTmdbId()), entity);
    }
}
