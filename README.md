
Le programme peut être téléchargé sous la forme d'un jar à l'adresse suivante : https://github.com/Leowenex/RogueLikeBucket/releases

# Ouverture du projet dans IntelliJ IDEA :

  Pour la création de notre jeu-vidéo, nous avons décidé d’utiliser le langage Java avec la librairie libGDX. Il existe un utilitaire permettant de générer un projet Gradle avec la librairie préinstallée.
  
  Ainsi, après avoir cloné le projet depuis GitHub ou après avoir téléchargé et décompressé l’archive Zip, on peut l’importer dans l’IDE IntelliJ en allant dans : File -> Open puis en sélectionnant le fichier build.gradle du projet. On peut alors appuyer sur le bouton « Reload all gradle projects » dans l’onglet Gradle que l’on peut trouver à droite de l’écran.
  
  Avant de lancer le jeu, il faut s’assurer d’avoir Java 11 ou une version ultérieure installé sur son ordinateur. Il faut également régler le niveau de langue de IntelliJ à une version égale ou supérieure à 11 en allant dans : File -> Project Structure -> Project Settings -> Project -> Language Level et en sélectionnant la version de Java souhaitée.  On peut alors lancer le projet en cherchant dans l’ongle Gradle : desktop -> Tasks -> other et en cliquant sur « run ». 
  
  On peut également générer une archive .jar en rentrant la commande « ./gradlew desktop:dist » dans le terminal.

# Ouverture du programme sous forme de .jar :

  Pour exécuter le programme sous forme d’archive .jar (il apparait dans le dossier : desktop -> build -> libs après avoir entré la commande citée précedemment), il suffit de s’assurer d’avoir Java 11 ou une version ultérieure installé sur son ordinateur et double cliquer dessus, ou en entrant la commande « java -jar <nom_de_l’_archive>».

# Utilisation du programme :

  Une fois que le programme est exécuté, une fenêtre s’ouvre dévoilant le menu principal du jeu, proposant les options « New Game », « Resume Game » et « Quit ». « Resume Game » va automatiquement rechercher une sauvegarde sur l’ordinateur, et si une sauvegarde est trouvée, le joueur sera amené au village de départ avec les items, pièces et autres statistiques qu’il avait avant de quitter le jeu. Le joueur se retrouve dans le niveau du village. Il peut s’approcher du PNJ en bas de l’écran pour avoir une liste d’items qu’il peut acheter s’il en a les moyens, avec les touches A, Z et E pour acheter les 1e, 2e et 3e items de la liste respectivement. S’il emprunte la porte au Nord du village, il pénètre dans le donjon.
 
  Chaque salle du donjon est générée procéduralement, et comporte : une porte de sortie, une clé permettant d’ouvrir la porte, des pièces, des items et des monstres. Le joueur peut s’approcher des clés et pièces pour les collecter automatiquement, et s’approcher des items puis appuyer sur P pour les mettre dans son inventaire. Le joueur peut se défendre des monstres avec la touche Espace s’il a déjà ramassé une épée ou une hache, ou avec les touches F, G, ou H s’il a déjà ramassé respectivement les sorts de Feu, Glace et Nature. Enfin, s’il a ramassé une potion rouge, le joueur peut récupérer des points de vie en appuyant sur la touche V, et s’il a ramassé une potion bleue, le joueur peut récupérer ses points de magie en appuyant sur la touche B. Enfin ramasser les gants, le plastron et les bottes améliore respectivement la vitesse de frappe, la résistance aux attaques, et la vitesse de déplacement.
  
  Quand le joueur complète la salle n° (3 x niveau du joueur), le joueur est ramené au village où il peut dépenser ses pièces. Il gagne également un niveau, rallongeant de 3 salles sa prochaine escapade dans le donjon. Si le joueur appuie sur la touche Echap, les informations de son personnage sont sauvegardées et il est renvoyé sur le menu principal. Ces informations seront rechargées s’il sélectionne « Resume Game » sur le menu principal. Si la barre de vie du joueur tombe à 0, son niveau est enregistré et il est envoyé sur l’écran de « Game Over » où sont affiché son score (son niveau) pour cette partie et le meilleur score qu’il ait eu. Enfin, si le joueur appuie sur « Main Menu » puis sur « Quit », la fenêtre du jeu se ferme.

