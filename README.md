# GMovie

## Get all movies that are available
------------------------------------
URI : /api/movies

Request :

Response : 200 oK

Sample Response :
-------------
[
  {
    "title": "The Avengers",
    "director": "Joss Whedon",
    "actors": "Robert Downey Jr., Chris Evans, Mark Ruffalo, Chris Hemsworth",
    "release": "2012",
    "description": "Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity.",
    "rating": null
  },
  {
    "title": "Superman Returns",
    "director": "Bryan Singer",
    "actors": "Brandon Routh, Kate Bosworth, Kevin Spacey, James Marsden",
    "release": "2006",
    "description": "Superman returns to Earth after spending five years in space examining his homeworld Krypton. But he finds things have changed while he was gone, and he must once again prove himself important to the world.",
    "rating": null
  },
  {
    "title": "Steel",
    "director": "Kenneth Johnson",
    "actors": "Shaquille O'Neal, Annabeth Gish, Judd Nelson, Richard Roundtree",
    "release": "1997",
    "description": "A scientist for the military turns himself into a cartoon-like superhero when a version of one of his own weapons is being used against enemies.",
    "rating": null
  }
]

## Get a single movie's details

URI: /api/movies/movie_title

Request: /api/movies/The Avengers

Response if found: 200 oK

sample Response if found:
-------------

{
"title": "The Avengers",
"director": "Joss Whedon",
"actors": "Robert Downey Jr., Chris Evans, Mark Ruffalo, Chris Hemsworth",
"release": "2012",
"description": "Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity.",
"rating": null
}

Response if NOT found: 404

sample Response if NOT found:
-------------

"The movie you're looking for was not found"

-----------------------------------------------

## Update movie rating for a given movie

URI : /api/movies

Request payload:
 - movie title
 - movie rating

Response : Movie Details

{
"title": "The Avengers",
"director": "Joss Whedon",
"actors": "Robert Downey Jr., Chris Evans, Mark Ruffalo, Chris Hemsworth",
"release": "2012",
"description": "Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity.",
"rating": "5"
}

---------------------------------------------
## Update movie rating AND review for a given movie

URI : /api/movies

Request payload:
 - movie title
 - movie rating
 - movie review

Response : Movie Details

{
"title": "The Avengers",
"director": "Joss Whedon",
"actors": "Robert Downey Jr., Chris Evans, Mark Ruffalo, Chris Hemsworth",
"release": "2012",
"description": "Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity.",
"rating": "5"
}
