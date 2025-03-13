# Application de Détection du Diabète de type 2

## Vue d'ensemble

Ce projet est conçu pour aider les médecins à détecter le diabète de type 2 chez leurs patients en analysant leurs données de santé. De nombreux patients souffrent de mauvais choix nutritionnels, optant souvent pour des produits bon marché, riches en sucre et en calories, au lieu de repas plus sains à base de légumes. Ces choix les exposent à un risque accru de maladies, notamment le diabète de type 2. Cette application a pour objectif d'aider les médecins à identifier les patients à haut risque, suivre leur évolution et générer des rapports sur leur niveau de risque de diabète.

## Fonctionnalités principales

- **Gestion des patients :** Consultation, modification et création de nouvelles fiches patients, en renseigant les informations personnelles : le prénom, le nom, la date de naissance, le genre, l'adresse et le numéro de téléphone.
- **Notes des médecins :** Les médecins peuvent consulter l'historique des notes cliniques des patients et en ajouter après chaque visite pour documenter les observations et recommandations, et pour un suivi plus précis d'une consultation à l'autre.
- **Évaluation du risque :** Génération de rapports indiquant la probabilité qu'un patient développe un diabète de type 2. Les patients sont classés en quatre niveaux de risque : Aucun risque, Risque limité, Danger et Apparition précoce.

## Architecture

L'application est découpée en plusieurs microservices, développés avec Spring Boot.

### Microservices

1. **eureka-server :** Service de registre avec **Spring Cloud Netflix Eureka**.
2. **microservice-gateway :** Gère la sécurité avec **Spring Security** et achemine les requêtes vers les autres microservices via **Spring Cloud Gateway**. Il se connecte à une base de données MySQL (Medilabo_Solutions).
3. **microservice-frontend :** Gère l'affichage côté client avec **Thymeleaf** et interagit avec les autres microservices (Patient, Note, Risque de Diabète).
4. **microservice-patient :** Gère les informations des patients et se connecte à une base de données MySQL.
5. **microservice-note :** Gère les notes des médecins et se connecte à une base de données MongoDB.
6. **microservice-diabetes-risk :** Analyse les données des patients provenant des microservices patient et note pour calculer le risque de diabète de type 2.

### Base de données et sécurité

- Toutes les bases de données sont normalisées selon la 3ème forme normale (3NF) afin d'assurer la qualité des données, conformément aux exigences de certification ISO.
- Les données des patients sont protégées de manière sécurisée par l'implémentation de **Spring Security** pour l'authentification des utilisateurs.

## Données de test

Afin de tester correctement l'application, il est nécessaire de charger des données de test dans les bases de données correspondantes. En fonction de la base de données, vous trouverez un fichier SQL ou BSON dans le microservice concerné :

- **MySQL :** Utilisez les fichiers SQL fournis pour peupler les tables "patient" et "doctor".
- **MongoDB :** Utilisez le fichier BSON pour peupler la collection "notes".

## Comment faire fonctionner l'application

### Configuration Docker

Chaque microservice possède une image Docker correspondante.
Mais il est plus simple de lancer la commande `docker compose up` à la racine du projet, un fichier docker-compose.yml ayant été créé pour ce but.

### Authentification

Pour se connecter à l'application, utilisez les identifiants de l'utilisateur de test :

- **Nom d'utilisateur :** `patrick.collombat`
- **Mot de passe :** `a`

Cet utilisateur est destiné uniquement aux tests.

---

## Medilabo_Solutions et le Green Code

Le Green Code fait référence à des pratiques de développement visant à réduire la consommation d'énergie des logiciels et à diminuer leur empreinte carbone. Cela inclut l'optimisation du code, la réduction des dépendances inutiles et l'amélioration de l'efficacité des systèmes. Ce concept est crucial pour le projet Medilabo Solutions, car il permet de limiter l'impact écologique tout en garantissant des performances optimales pour les utilisateurs (les médecins). L'application repose sur une architecture de microservices, et bien que les microservices offrent une meilleure scalabilité, ils peuvent augmenter la consommation d'énergie en raison de leur complexité et des nombreuses requêtes réseau.

Pour appliquer le Green Code à notre projet, il nous faut optimiser le code, réduire les ressources utilisées par les microservices, et choisir des technologies économes en énergie. Des pratiques telles que l’optimisation des requêtes, la gestion des API et l’utilisation de containers Docker légers doivent être mises en place afin d'améliorer l'efficacité énergétique de l’application. L’adoption de ces pratiques vise à réduire l’empreinte carbone du projet tout en assurant sa performance et sa durabilité à long terme.

