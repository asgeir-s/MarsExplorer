todo(_, xPos, yPos, _, home) :-
    xPos > 10
    ;
    xPos < -10
    ;
    yPos > 10
    ;
    yPos < -10.

todo(Agent, _, _, AgentNearType, drop) :-
    hasRock(Agent),
    AgentNearType == spaceship,
    !.

todo(Agent, _, _, AgentNearType, pickup) :-
    not(hasRock(Agent)),
    AgentNearType == rock,
    !.

todo(Agent, _, _, _, home) :-
    hasRock(Agent),
    !.

todo(_, _, _, _, search).
