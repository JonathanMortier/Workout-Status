# Workout-Status

Micro services pour une app mobile permettant de savoir quel poids l'utilisateur peut soulever par machine

GET /weight/{userId} --> Renvoie l'ensemble des couples poids/machine de l'utilisateur
GET /weight/{userId}/{machineId} --> Renvoie le poids de la machine pour l'utilisateur
PUT /weight/{userId}/{machineId}/{weight} --> Modifie la valeur du poids pour la machine pour l'utilisateur
PUT /user/{lastname}/{firstname} --> Crée un user et renvoie son id
PUT /machine/{machineName} --> Crée une machine et renvoie son id
