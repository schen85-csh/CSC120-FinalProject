This file will contain documentation for all commands available in your game.

Note:  It's a good idea to also make this list available inside the game, in response to a `HELP` command.

all commands:
1. go [direction]: use the word "go" plus direction "up", "down", "left", "right"
2. take [item]
3. hide
4. quit: end the game
5. use [item]: the item here must bu food or drink
6. attack [part]: use the word "attack" plus the part of zombie. The [part] can be "limbs", "ears", "head", "torso", and is optional. If players did not give a target part, the system will pick a random part to attack.
7. use [weapon] to attack [part]: Players can name the weapon they have and want to use. Players can also name the part(limbs, head, ears, torso) of the zombie that they want to attack. If players did not give a target part, the system will pick a random part to attack.
         
         
         
MY ARCHUTECTURE DIAGRAM AND MAP ARE INCLUDED IN THE FOLDER IN pdf VERSION!!   

# SPOILER ALERT

If your game includes challenges that must be overcome to win, also list them below.

Since the zombies don’t actively attack and also don’t leave their assigned rooms, it’s possible for a player to follow the map route and reach the rooftop without engaging with them. 
To make the players get the best experience, my suggestion is to use my following command:

    (start in the library)
    hide
    (hide behind the shelf)

    go left
    (arrive the PEroom)

    take bow and arrow
    (get the first weapon)

    take baseball bat
    (get the second weapon)

    go right
    (arrive the library again)

    go right
    (arrive the StoreRoom)

    take energy drink
    (get the item that will help increase lifebar)

    take protein bar
    (get the item that will help increase lifebar)

    use energy drink
    (increase lifebar)

    use protein bar
    (increase lifebar)

    attack 
    (attack the zombie's random part)

    attack head
    (attack the zombie's head)

    use baseball bat to attack head

    go up
    (arrive medical room)

    take iodine
    (get the item that will help increase lifebar)

    take bandage
    (get the item that will help increase lifebar)

    use iodine
    (increase lifebar)

    use bandage
    (increase lifebar)

    use bow and arrow to attack
    (use bow and arrow to attack zombie's random part)
    (if the zombie is not dead, try to give more attack command)

    go left
    (arrive the broadcast room)

    take broadcasting equipment
    (get a weapon that will cause damage to zombie's ears)

    use broadcasting equipment to attack
    (use broadcasting equipment to randomly attack zombie)
    (if zombie is not dead, try to give more attack command)

    go left
    (arrive science classroom)

    take concentrated sulfuric acid
    (get a weapon)

    use concentrated sulfuric acid to attack head
    (if the zombie is not dead, try to give more attack command)

    go up
    (arrive the roofTop)

    WIN!!!


