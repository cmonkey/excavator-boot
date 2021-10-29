package org.excavator.boot.experiment.test

object Films {

  case class Film(title:String)

  def currentUserRank = 7

  def suggestFilm(suggestionSeed: Int):Film = {
    val defaultFilms = List(Film("Godfather"), Film("The Room"), Film("Fargo"))
    val additionalFilms = if (currentUserRank < 10) Nil else List(Film("Ran"), Film("Stalker"))

    if(suggestionSeed % 3 == 0) defaultFilms(3) // IndexOutOfBoundsException
    else if (suggestionSeed % 3 == 1) defaultFilms(2) // No problems
    else additionalFilms.head
  }

}
