package starter;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Playlist {
	public static void main(String[] args){
		ArrayList genres = new ArrayList();
		ArrayList genre_name = new ArrayList();
		ArrayList artists = new ArrayList();
		ArrayList art_name = new ArrayList();
		boolean cont = true;
		try (BufferedReader fileReader = new BufferedReader(new FileReader("Music.txt"))) {
			/**
			 * Check every line of this file, check if the artist exists, if not create a new artist, add the genre to
			 * the genre list. If they do exist, do not add a new artist, however you must still add the genre to the
			 * genre list.
			 *  "\\s" = single blank space
			 *  "\\," = a comma
			 *  "\\|" = |
			 *  ArtistName, Country | genre1 | genre2 | ... | genreN |
			 */

			// 1. Check if the artist has already been created
			// 		a) it has
			// 			i) find that object in artists
			//			ii) for every genre check if the genre has been created already
			//				a) it has
			//					i) try adding the genre to the artist (the function will take care of the rest)
			//				b) it has not
			//					i) add the genre to the artist
			//		b) it has not
			//			i) Create a new artist
			// 			ii) for every genre check if the genre has been created already
			//				a) it has
			//					i) try adding the genre to the artist (the function will take care of the rest)
			//				b) it has not
			//					i) add the genre to the artist

			// Get the first line of text \

			String line = fileReader.readLine();
			// loop through every line
			while (line != null) {

				// split up the text into an easy to parse array.
				// ArtistName, Country | genre1 | genre2 | ... | genreN |
				// 		becomes
				//  {"ArtistName, Country", "genre1", "genre2", "genreN"}

				String[] split_line = line.split(" \\|");
				// need to remove last 2 characters from last element in split_line

				int i = 1;
				while (i < split_line.length){
					split_line[i] = split_line[i].substring(1, split_line[i].length());
					i ++;
				}

				// make a new array like so {"ArtistName", "Country"}
				String[] name_country = split_line[0].split(", ");

				// Create an artist, this will be swapped out with an existing artist if such an object exists,
				// 		otherwise this will be used.
				Artist temp = new Artist(name_country[0], name_country[1]);

				// this steps refers to 1. a)
				if (art_name.contains(split_line[0])) {
					// 1. a) i) find the existing artist object.
					for (Object a : artists) {
						if (a instanceof Artist) {
							// use artist.equal() *NEED TO SPLIT NAME AND COUNTRY FIRST THEN USE THAT*
							if (((Artist) a).equals(temp)) {
								Artist person = (Artist) a;

								// This entire section can, in fact should be a helper function.
								// 1. a) ii) Check if the genre has been created yet.

								String[] cp = Arrays.copyOfRange(split_line, 1, split_line.length);

								for (String x : cp) {
									// 1. a) ii) a) the genre has been created already. Find it.
									if (genre_name.contains(x)) {
										for (Object g : genres) {
											if (g instanceof Genre) {
												if (((Genre) g).getName().equals(x)) {
													Genre this_genre = (Genre) g;
													this_genre.addArtist(person);
												}
											}
										}
									}
									// 1. a) ii) b) the genre has not been created, so create a new one and add the artist.
									else {
										// add it to the genre name list
										genre_name.add(x);
										// create the new genre
										Genre this_genre = new Genre(x);
										// add the new genre to the genres list
										genres.add(this_genre);
										// 1. a) ii) b) i) Add this genre to this artists repertoire
										this_genre.addArtist(person);
									}


								}
							}
						}
					}
				}
				// case where this artist has not been added yet
				else {
					art_name.add(split_line[0]);
					// 1. b) i) has already been done up top with temp.
					// This entire section can, in fact should be a helper function.
					// 1. a) ii) Check if the genre has been created yet.
					String[] cp = Arrays.copyOfRange(split_line, 1, split_line.length);
					for (String x : cp) {
						// 1. b) ii) a) the genre has been created already. Find it.
						if (genre_name.contains(x)) {
							for (Object g : genres) {
								if (g instanceof Genre) {
									if (((Genre) g).getName().equals(x)) {
										Genre this_genre = (Genre) g;
										this_genre.addArtist(temp);
									}
								}
							}
						}
						// 1. b) ii) b) the genre has not been created, so create a new one and add the artist.
						else {
							// add it to the genre name list
							genre_name.add(x);
							// create the new genre
							Genre this_genre = new Genre(x);
							// add the new genre to the genres list
							genres.add(this_genre);
							// 1. a) ii) b) i) Add this genre to this artists repertoire
							this_genre.addArtist(temp);
						}
					}
					artists.add(temp);
				}

				line = fileReader.readLine();

			}

			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// This is the part of the program that queries the user.
		while (cont) {
			Scanner scanner = new Scanner(System.in);
			System.out.print("Enter a genre. ");
			String response = scanner.nextLine();

			// this might not be right
			Genre this_genre = new Genre(response);
			// search for the genre, if it's found, replace this_genre.
			if (genre_name.contains(response)){
				// Find this genre.
				for (Object g : genres) {
					if (g instanceof Genre) {
						if (((Genre) g).getName().equals(response)) {
							this_genre = (Genre) g;
						}
					}
				}

				System.out.println(this_genre.toString());
			}
			else if (response.equals("exit")) {
				cont = false;
			}
			else {
				System.out.println("This is not a valid game.");

			}
		}
	}
}