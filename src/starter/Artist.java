package starter;

import java.util.ArrayList;

public class Artist {
	private String name;
	private ArrayList genres;
	private String country;

	public Artist(String name, String country) {
		this.name = name;
		this.country = country;
		this.genres = new ArrayList();
	}

	public void addGenre(Genre g) {
		if (! genres.contains(g)) {
			genres.add(g);
			g.addArtist(this);
		}
		else {
			g.addArtist(this);
		}
	}

	public boolean equals(Artist b) {
		return this.getName().equals(b.getName()) && this.getCountry().equals(b.getCountry());
	}
	public ArrayList getGenres() {
		return genres;
	}
	public String getName() {
		return name;
	}
	public String getCountry() {
		return country;
	}
	public String toString() {
		int i = 0;
		StringBuilder s = new StringBuilder("");
		for (Object a: this.genres){
			if (a instanceof Genre)
				s.append(System.lineSeparator() + ((Genre) a).getName());

		}
		if (s.toString().equals(""))
			return name + ", " + country;
		else
			return name + ", " + country  + s;
	}

}
