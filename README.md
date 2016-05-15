# Workout-Status

Micro services pour une app mobile permettant de savoir quel poids l'utilisateur peut soulever par machine

**GET /weights/{userId}** --> Renvoie l'ensemble des couples poids/machine de l'utilisateur (OK)

**GET /weights/{userId}/{machineId}** --> Renvoie le poids de la machine pour l'utilisateur (TODO)

**POST /weights** --> Modifie la valeur du poids pour la machine pour l'utilisateur (OK)

**GET /users** --> Récupère l'ensemble des utilisateurs (OK)

**GET /users/{userId}** --> Récupère l'utilisateur ayant l'id en paramètre (OK)

**POST /users** --> Crée un user et renvoie le user (format JSON) (OK)

**GET /machines** --> Renvoie l'ensembles des machines (OK)

**GET /machines/{machineId}** --> Renvoie la machine ayant le machineId (OK)

**POST /machines** --> Crée une machine et renvoie la machine (format JSON) (OK)
