package People

import Locations.LocationNames
import Locations.SmallPlaces
import Person
import Relevant

class Peasant(name: String, age: Int, location: LocationNames, vitality: Int) : Person(name, age, location, vitality), Relevant {
}