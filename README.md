# Deadwood: "The Cheapass Game of Acting Badly"
## A digital version, created with permission by Cheapass Games
### (A class project for CSCI 345: Object Oriented Programming)

Welcome to Deadwood!

This is a simple board game for 2-8 players (6 or fewer recommended)

Please read the English directions [here] (http://cheapass.com//wp-content/uploads/2016/07/Deadwood-Free-Edition-Rules.pdf)

Vea los instrucciones en espanol en el archivo incluido "instrucciones.txt".


## User/Player Commands
- who : Identifies the current player, their $ and cr count,  and any parts (roles) that the player is working.
- where : Displays te current player's room, any active scene, and available roles.
- move *room* : Active player moves to indicated room.
- work *part* : Active player starts working the indicated part (role).
- upgrade $ *level* : Active player upgrades their rank to indicated level using their money. Only possible in Casting Office.
- upgrade cr *level* : Active player upgrades their rank to indicated level using their credits. Only possible in Casting Office.
- rehearse : Active player rehearses. Only possible if the player has a part.
- act : Active player performs in their current role. Only possible if the player has a role.
- end : Ends the active player's turn.



## Input text file formats:

### adjRoomsInfo.txt
The following format repeats for every room

[RoomA]
[Room Adjacent To RoomA]_[Different Room Adjacent to RoomA]_[...]


### roominfo.txt format:
The following format repeats for every room, except the Casting Office and Trailers

[Room Name]_[totalShotCtrs]_[numOfRoles]
[roleRank]_[Role Name]_[Role Quote]
(repeat for additional roles)


### sceneInfo.txt format:
The following format repeats for every scene card (currently there are 40)

[Scene Name]_[Budget]_[SceneNumber]_[Scene Description]_[numberOfRoles]
[rankRequirement]_[Role Name]_[Quote]
(repeats above line for numberOfRoles times)

