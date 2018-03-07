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
		ArrayList genreName = new ArrayList();
		ArrayList artists = new ArrayList();
		ArrayList artName = new ArrayList();
		boolean cont = true;
		try (BufferedReader fileReader = new BufferedReader(new FileReader("Music.txt"))) {

			String line = fileReader.readLine();
			while (line != null) {

				String[] splitLine = line.split(" \\|");

				int i = 1;
				while (i < splitLine.length){
					splitLine[i] = splitLine[i].substring(1, splitLine[i].length());
					i ++;
				}

				String[] nameCountry = splitLine[0].split(", ");

				// Create an artist, this will be swapped out with an existing artist if such an object exists,
				// 		otherwise this will be used.
				Artist temp = new Artist(nameCountry[0], nameCountry[1]);

				if (artName.contains(splitLine[0])) {
					for (Object a : artists) {
						if (a instanceof Artist) {
							if (((Artist) a).equals(temp)) {
								Artist person = (Artist) a;

								String[] cp = Arrays.copyOfRange(splitLine, 1, splitLine.length);

								for (String x : cp) {
									if (genreName.contains(x)) {
										for (Object g : genres) {
											if (g instanceof Genre) {
												if (((Genre) g).getName().equals(x)) {
													Genre thisGenre = (Genre) g;
													thisGenre.addArtist(person);
												}
											}
										}
									}
									else {
										// add it to the genre name list
										genreName.add(x);
										// create the new genre
										Genre thisGenre = new Genre(x);
										// add the new genre to the genres list
										genres.add(thisGenre);
										thisGenre.addArtist(person);
									}


								}
							}
						}
					}
				}
				// case where this artist has not been added yet
				else {
					artName.add(splitLine[0]);
					String[] cp = Arrays.copyOfRange(splitLine, 1, splitLine.length);
					for (String x : cp) {
						if (genreName.contains(x)) {
							for (Object g : genres) {
								if (g instanceof Genre) {
									if (((Genre) g).getName().equals(x)) {
										Genre thisGenre = (Genre) g;
										thisGenre.addArtist(temp);
									}
								}
							}
						}
						else {
							genreName.add(x);
							Genre thisGenre = new Genre(x);
							genres.add(thisGenre);
							thisGenre.addArtist(temp);
						}
					}
					artists.add(temp);
				}

				line = fileReader.readLine();

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		// This is the part of the program that queries the user.
		while (cont) {
			Scanner scanner = new Scanner(System.in);
			System.out.print("Enter a genre. ");
			String response = scanner.nextLine();

			Genre thisGenre = new Genre(response);
			// search for the genre, if it's found, replace thisGenre.
			if (genreName.contains(response)){
				// Find this genre.
				for (Object g : genres) {
					if (g instanceof Genre) {
						if (((Genre) g).getName().equals(response)) {
							thisGenre = (Genre) g;
						}
					}
				}

				System.out.println(thisGenre.toString());
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
