[![Java CI with Maven](https://github.com/gen-classroom/projet-blanc_maillard_pozniakoff/actions/workflows/maven.yml/badge.svg)](https://github.com/gen-classroom/projet-blanc_maillard_pozniakoff/actions/workflows/maven.yml) [![Total alerts](https://img.shields.io/lgtm/alerts/g/gen-classroom/projet-blanc_maillard_pozniakoff.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/gen-classroom/projet-blanc_maillard_pozniakoff/alerts/) [![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/gen-classroom/projet-blanc_maillard_pozniakoff.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/gen-classroom/projet-blanc_maillard_pozniakoff/context:java)

Membre: Mathias Maillard, Jean-Luc Blanc, Lev Pozniakoff

------

# Générateur de site statique

## Résumé

Ce projet a été créé lors du cours de Génie Logiciel donné par Mr Bertil Chapuis à la HEIG-VD.

Ce générateur de site statique permet de générer des pages webs à partir de fichiers markdown, de template. Il offre également une solution pour nettoyer les fichiers généré et effectuer des benchmarks des performances de notre logiciel.

## Installation

1. Téléchargez le fichier statique.zip
	  * Ce fichier se trouve dans la release
2. Dézippez le fichier zip dans votre arborescence de travail
	  * Cette arborescence contiendra vos pages web
3. Ouvrez un terminal dans le dossier bin"
4. Tapez cette commande : ``./statique init /monSiteStatique``
5. Un dossier du nom de "monSiteStatique" a été créé et il contient un fichier mardown index.md qui sert d'exemple ainsi qu'un fichier config.yaml qui contient les configurations à rentrer
6. Si une configuration existe déjà dans ce dossier vous pouvez utiliser l'option -O pour les écraser: ``./statique init -O /monSiteStatique``

## Compilation

1. Tapez cette commande : ``./statique build /monSiteStatique``
2. Cette commande va compiler les fichiers du dossier monSiteStatique et générer des pages html à partir des fichiers markdown
3. un dossier build a été créé et à l'intérieur se trouve vos pages web

Il est possible d'ajouter l'option ``-t`` à la commande, ce dernier permet d'activer le templating, pour utiliser le templating il vous faut au préalable : 

1. Un dossier ``templates`` au même niveau que le dossier ``monSiteStatique``
2. Dans le dossier ``templates`` il vous faudra 2 fichiers
	* Un fichier ``layout.html``, dans lequel on ajoutera le contenu des fichiers markdown.
	* Un fichier ``menu.html``, qui servira de menu aux pages web, il sera également intégré au fichier ``layout.html``
3. Vous pourrez trouver dans le repo un dossier ``templates`` que nous vous conseillons de copier, cela vous permettra d'avoir des fichiers "de base" sur lesquels travailler

## Serveur HTTP

1. Tapez cette commande : ``./statique serve /monSiteStatique``
2. La commande ouvrira vos page web dans un navigateur. 

## Suppression et nettoyage

1. Tapez cette commande : ``./statique clean /monSiteStatique``
2. Cette commande va supprimez votre répertoire monSiteStatique

## Watch

Une option existe pour les commandes build et serve qui permet de re-charger les fichiers HTML lors de modification du fichier markdown

Tapez ces commandes pour expérimenter cette option : 

* ``./statique build --watch /monSiteStatique``
* ``./statique serve --watch /monSiteStatique``

## Version

Pour afficher la version actuelle du générateur de site statique, il faut taper cette commande : 

 ``./statique --version``

## Compilation et modification du code

Voici la commande maven à taper pour générer un nouveau script statique

```
mvn clean install
```

Si vous souhaitez modifier notre code, sentez-vous libre de forker ce repo.
