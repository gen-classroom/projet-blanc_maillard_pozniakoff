[![Java CI with Maven](https://github.com/gen-classroom/projet-blanc_maillard_pozniakoff/actions/workflows/maven.yml/badge.svg)](https://github.com/gen-classroom/projet-blanc_maillard_pozniakoff/actions/workflows/maven.yml) [![Total alerts](https://img.shields.io/lgtm/alerts/g/gen-classroom/projet-blanc_maillard_pozniakoff.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/gen-classroom/projet-blanc_maillard_pozniakoff/alerts/) [![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/gen-classroom/projet-blanc_maillard_pozniakoff.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/gen-classroom/projet-blanc_maillard_pozniakoff/context:java)

Membre: Mathias Maillard, Jean-Luc Blanc, Lev Pozniakoff

------

# Générateur de site statique

## Résumé

Ce projet a été créé lors du cours de Génie Logiciel donné par Mr Bertil Chapuis à la HEIG-VD.

Ce générateur de site statique permet de générer des pages webs à partir de fichiers markdown, de template. Il offre également une solution pour nettoyer les fichiers généré et effectuer des benchmarks des performances de notre logiciel.

## Table des matières



## Installation

1. Téléchargez le fichier statique.zip
2. Dézippez le fichier zip dans votre arborescence de travail
  * Cette arborescence contiendra vos pages web
3. Ouvrez un terminal à l'endroit où se situe le script "statique"
4. Tapez cette commande : java -jar xxxx.jar init /monSiteStatique
5. Un dossier du nom de "monSiteStatique" a été créé et il contient un fichier mardown index.md qui sert d'exemple ainsi qu'un fichier config.yaml qui contient les configurations à rentrer



## Compilation

1. Tapez cette commande : java -jar xxxx.jar build /monSiteStatique
2. Cette commande va compiler les fichiers du dossier monSiteStatique et générer des pages html à partir des fichiers markdown
3. un dossier build a été créé et à l'intérieur se trouve vos pages web

## Serveur HTTP

1. Tapez cette commande : java -jar xxxx.jar serve /monSiteStatique
2. Vous pouvez maintenant accéder à vos pages web à cette addresse : 

## Suppression et nettoyage

1. Tapez cette commande : java -jar xxxx.jar clean /monSiteStatique
2. Cette commande va supprimez votre répertoire monSiteStatique

## Version

Pour afficher la version actuelle du générateur de site statique, il faut taper cette commande : 

 java -jar xxxx.jar --version

## Compilation et modification du code

Voici la liste des commandes maven à taper pour générer un nouveau fichier .jar

```

```

Si vous souhaitez modifier notre code, sentez-vous libre de forker ce repo, git hub actions est également configurez pour générer une nouvelle release à chaque commit.