

package app.philm.in.model;

import com.uwetrottmann.tmdb.entities.PersonCastCredit;
import com.uwetrottmann.tmdb.entities.PersonCrewCredit;

import java.util.Comparator;

public class PhilmPersonCredit extends PhilmModel<PhilmPersonCredit> {

    public static final Comparator<PhilmPersonCredit> COMPARATOR_SORT_DATE
            = new Comparator<PhilmPersonCredit>() {
        @Override
        public int compare(PhilmPersonCredit movie, PhilmPersonCredit movie2) {
            if (movie.releaseDate > movie2.releaseDate) {
                return -1;
            } else if (movie.releaseDate < movie2.releaseDate) {
                return 1;
            }
            return 0;
        }
    };

    final String title;
    final int id;
    final String job;
    final String posterPath;
    final long releaseDate;

    public PhilmPersonCredit(PersonCastCredit credit) {
        this.id = credit.id;
        this.title = credit.title;
        this.posterPath = credit.poster_path;
        this.job = credit.character;
        this.releaseDate = credit.release_date != null ? credit.release_date.getTime() : 0;
    }

    public PhilmPersonCredit(PersonCrewCredit credit) {
        this.id = credit.id;
        this.title = credit.title;
        this.posterPath = credit.poster_path;
        this.job = credit.job;
        this.releaseDate = credit.release_date != null ? credit.release_date.getTime() : 0;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public String getJob() {
        return job;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public long getReleaseDate() {
        return releaseDate;
    }
}
