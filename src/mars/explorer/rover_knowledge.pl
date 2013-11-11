todo(_, XPos, YPos, _, home) :-
    XPos > 10
    ;
    XPos < -10
    ;
    YPos > 10
    ;
    YPos < -10.

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

% top level behaviour
todo(_, _, _, _, search).
