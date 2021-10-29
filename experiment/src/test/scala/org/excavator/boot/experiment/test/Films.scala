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

  def sequences(f: Int => Boolean): Unit = {
    val original = List(4, 6, 20, 55)
    val filtered = original.filter(f)
    original.size == 3 // always false, size is 4
    filtered.size > 3 // unknown, size in range [0, 4]
    filtered.size < 6 // always true, size in range [ 0, list.size] == [0, 4]
    filtered == original // unknown, filtered can be original or its subsequence
    original.map(x => 2 * x).size == 4
    // original hs size 4, map doesn't change it 
  }

}
