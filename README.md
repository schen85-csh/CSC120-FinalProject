# CSC120-FinalProject

## Deliverables:
 - Your final codebase
 - Your revised annotated architecture diagram
 - Design justification (including a brief discussion of at least one alternative you considered)
 - A map of your game's layout (if applicable)
 - `cheatsheet.md`
 - Completed `rubric.md`
  

## Design justification
In the current version of my game, zombies do not attack people actively. They only fight back if they are attacked but not killed.

In my alternative plan, I want to give the player a running speed and use the energy drink as an item to increase that speed. In this version, if a player enters a room with zombies, the zombies will attack actively. However, before the attack, the system will check the player's speed and there will be a question pops up in the terminal to ask if the player wants to run away. If the player is faster than the zombies and chooses to escape, they can avoid the attack.

This design is a bit complex for now. So, if I have a chance to improve the game and make it more interesting in the future, I will definitely add this design.

## Additional Reflection Questions
 - What was your **overall approach** to tackling this project?
 My plan was start from the structure and a broader view, and then add the details in the structures. For example, I first decide the setting of my game and the layout of the map. Then based on the settings, I choose the rooms and fill them into my map. I also consider the subjects that will appear in the game and then set the classes. The classes will allow me to think more about attributes and methods.

 - What **new thing(s)** did you learn / figure out in completing this project?
I learned that each step will influence the outcome and the big plan can be achieved step by step. It is difficult to build all these code in one day, but if I separate the programming tasks into many days and leave enough time for me to debug and rewrite, the programming will be less stressful. Also, during the process, I debug a lot. Once I make some changes of the code, many error pops up, so I learned to deal with them peacefully. 

 - Is there anything that you wish you had **implemented differently**?
I wish I can go to Professor Hia's office hour more, because it is a great chance to push myself to explain my code and find the bugs. I also wish to start to programming earlier, because at first, I spent a lot of time designing the map and the settings. Thus, my initial plan became a little bit too complex for me to achieve, like the "speed and energy drink" plan. If I could start coding earlier, even if my design wasn't so mature and interesting, I would still keep getting inspired during the coding process and continuously add new elements that I could implement.

 - If you had **unlimited time**, what additional features would you implement?
I would first let my zombies move around. Then I will make the map more complex, so the players need to keep find clues in the rooms to find their way out. I will also turn the game into a visual game with scaring pictures.

 - What was the most helpful **piece of feedback** you received while working on your project? Who gave it to you?
 When I talked to Professor Hia about my classes, she innspired me that I do not need to build 6 different classes for my 6 different rooms. The feedback was really helpful and helped me reduce a lot of burdens. In my final version I only have a class called Room, and I create 6 different room objects.

 - If you could go back in time and give your past self some **advice** about this project, what hints would you give?
 I would say be carefull for the word spelling, because at the very end of the project, I spent a lot of time debugging an error and I finally find it was caused by an incorrect word spelling.

 - _If you worked with a team:_ please comment on how your **team dynamics** influenced your experience working on this project.
 I worked individually, but by observing my neighbours, I believes that working with a team will bring a lot more fun.
