package example

import java.text.Normalizer

fun main() {

    val coach1 = Coach("Miro", 178, 97, 57)
    val coach2 = Coach("Zvonimir", 199, 68, 65)
    val coach3 = Coach("Gladijatus", 155, 66, 40)
    val coach4 = Coach("Kruno", 144, 77, 70)
    val coach5 = Coach("Marko", 177, 100, 67)
    val coach6 = Coach("Vajko", 176, 71, 97)

    val player1 = Player("Kruno", 175, 71, 61, true, PlayingPosition.DF)
    val player2 = Player("Bakreni", 166, 63, 94, true, PlayingPosition.FW)
    val player3 = Player("Zoran", 197, 99, 78, false, PlayingPosition.GK)
    val player4 = Player("Dinko", 169, 69, 30, true, PlayingPosition.MF)
    val player5 = Player("Travian", 201, 103, 24, true, PlayingPosition.FW)
    val player6 = Player("Jan", 165, 71, 61, true, PlayingPosition.FW)
    val player7 = Player("Miroslav", 177, 67, 94, true, PlayingPosition.DF)
    val player8 = Player("Slavko", 149, 96, 88, false,PlayingPosition.MF)
    val player9 = Player("Donko", 193, 69, 39, true, PlayingPosition.MF)
    val player10 = Player("Goran", 205, 127, 53, true, PlayingPosition.GK)
    val player11 = Player("Miroslav", 177, 87, 94, true, PlayingPosition.DF)
    val player12 = Player("Slaven", 149, 91, 88, false, PlayingPosition.FW)
    val player13 = Player("Davor", 193, 64, 100, false, PlayingPosition.GK)
    val player14 = Player("Zeleni", 204, 173, 53, true, PlayingPosition.DF)

    val dinamo = Team("Dinamo", listOf(player1, player2, player3), coach1, Formation.F433)
    val cibalija = Team("Cibalija", listOf(player4, player5, player6), coach2, Formation.F442)
    val osijek = Team("NK Osijek", listOf(player7, player8, player9), coach3, Formation.F532)
    val hajduk = Team("Hajduk", listOf(player10, player11, player12), coach4, Formation.F442)
    val lokomotiva = Team("Lokomotiva", listOf(player13, player14, player7), coach5, Formation.F433)
    val gorica = Team("NK Gorica", listOf(player1, player2, player11), coach6, Formation.F532)
    val varazdin = Team("Varazdin", listOf(player1, player2, player14), coach1, Formation.F433)
    val rudes = Team("Rudes", listOf(player4, player8, player6), coach2, Formation.F442)
    val rijeka = Team("Rijeka", listOf(player13, player8, player9), coach3, Formation.F532)
    val zapresic = Team("Inter Zapresic", listOf(player10, player4, player12), coach4, Formation.F442)
    val belupo = Team("Slaven Belupo", listOf(player13, player14, player3), coach5, Formation.F433)
    val istra = Team("Istra 1961", listOf(player1, player2, player11), coach6, Formation.F532)

    val liga1 = League("1.HNL", listOf(dinamo, hajduk, osijek, rijeka, zapresic, belupo))
    val liga2 = League("3.HNL", listOf(cibalija, lokomotiva, gorica, istra, rudes, varazdin))


    println(liga1.getSetOfPlayers())

    println(liga1.getSetOfTeams())

    println(liga1.getListOfPlayingSkills())

    println(dinamo.getListOfReadyPlayers())

    println(liga2.getAllCoachHeights())

    println(liga1.getSetOfSkilledPlayers(70))

    println(osijek.allPlayersReady())

    println(liga2.anyPerfectPlayer())

    println(liga1.bestPlayer())

    println(liga2.sortedPlayers())

    println(liga1.bestTeam())

    println(liga1.playersByPosition()[PlayingPosition.DF])

    println(liga2.teamsByFormation())

    println(liga1.splitTeamsRating(70).first)
}

//Get set of teams
fun League.getSetOfTeams(): Set<Team> = this.teams.toSet()

//Get set of players from League
fun League.getSetOfPlayers(): Set<Player> = this.teams.flatMap { it.players }.toSet()

//Get list of all player skills from league
fun League.getListOfPlayingSkills(): List<Int> = this.teams.flatMap { it.players }.map { it.playingSkill }

//Get average team rating
fun Team.rating(): Double = this.players.map { it.playingSkill }.average()

//Get list of all heights of coaches
fun League.getAllCoachHeights(): List<Int> = this.teams.map { it.coach.height }

//Get set of players that have playing skill above of [skill]
fun League.getSetOfSkilledPlayers(skill: Int = 50): Set<Player> = this.teams.flatMap { it.players }.filter { it.playingSkill > skill}.toSet()

//Get list of players from team that are ready 2 play
fun Team.getListOfReadyPlayers(): List<Player> = this.players.filter { it.ready == true }

//Are all players ready for the game?
fun Team.allPlayersReady(): Boolean = this.players.all { it.ready == true }

//Check if there is any perfect player (playing skill: 100) in league
fun League.anyPerfectPlayer(): Boolean = this.teams.flatMap { it.players }.any { it.playingSkill == 100 }

//Count number of teams in league
fun League.numberOfTeams(): Int = this.teams.count()

//Count number of players in league
fun League.numberOfPlayers(): Int = this.teams.flatMap { it.players }.count()

//Get player with biggest playing skill in league
fun League.bestPlayer(): Player? = this.teams.flatMap { it.players }.maxBy { it.playingSkill }

//Get list of players sorted by ther playing skill
fun League.sortedPlayers(): List<Player> = this.teams.flatMap { it.players }.sortedByDescending { it.playingSkill }

//Get the team with the biggest sum of playing skill
fun League.bestTeam(): Team? = this.teams.maxBy { it.players.sumBy { it.playingSkill } }

//Group players by theyr playing positions
fun League.playersByPosition(): Map<PlayingPosition, List<Player>> = this.teams.flatMap { it.players }.groupBy { it.playingPosition }

//Group teams by thery formation
fun League.teamsByFormation(): Map<Formation, List<Team>> = this.teams.groupBy { it.formation }

//Split teams who are above and below [neededRating]
fun League.splitTeamsRating(neededRating: Int = 60): Pair<List<Team>, List<Team>> = this.teams.partition { it.rating() > neededRating }





data class League(val LeagueName: String, val teams: List<Team>)

data class Team(val TeamName: String, val players: List<Player>, val coach: Coach, val formation: Formation)

data class Player(val name: String, val height: Int, val weight: Int, val playingSkill: Int, val ready: Boolean, val playingPosition: PlayingPosition)

data class Coach(val name: String, val height: Int, val weight: Int, val coachingSkill: Int)

enum class PlayingPosition(){
    DF,MF,FW,GK
}

enum class Formation(){
    F442, F433, F532
}
