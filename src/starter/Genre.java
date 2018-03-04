package starter;

import java.util.ArrayList;

public class Genre {
	private String name;
	private ArrayList artists;

	public Genre(String name) {
		this.name = name;
		this.artists = new ArrayList();
	}

	public boolean isPlayedBy(Artist a) {
		return a.getGenres().contains(this);
	}

	public boolean hasSameArtist(Genre g) {
		if (this.artists.size() == 0 && g.artists.size() == 0) {
			return false;
		}
		boolean x = true;

		int i = 0;
		while (i != this.artists.size()) {
			if (!g.artists.contains(this.artists.get(i))) {
				x = false;
			}
			// call this.artists.get(i), cast it to type Genre, and do something with it.
			i = i + 1;
		}
		return x;
	}

	public void addArtist(Artist a) {
		/**
		 * Check over this one eh
		 */
		if (!isPlayedBy(a)) {
			a.addGenre(this);
			// this next one might be unnecessary
			if (!artists.contains(a)) {
				artists.add(a);
			}
		} else {
			if (!artists.contains(a)) {
				artists.add(a);
			}
		}
	}

	public boolean equals(Genre g) {
		return this.getName().equals(g.getName()) && this.artists.size() == g.artists.size() &&
				this.artists.containsAll(g.artists) && g.artists.containsAll(this.artists);
	}

	public String getName() {
		return name;
	}

	public String toString() {

		StringBuilder s = new StringBuilder("(");
		String x = ")";

		int i = 0;
		for (Object a : this.artists) {
			if (a instanceof Artist && i == 0){
				s.append(((Artist) a).getName());
			} else if ( a instanceof Artist && i > 0){
				s.append(", " + ((Artist) a).getName());
			}
			i++;
		}
		return this.getName() + " " + s + x;

	}
}