# Migration des Services et Équipements vers des Listes Déroulantes

## Contexte
Cette migration transforme les champs texte `services` et `equipements` de la table `salles` en références vers des tables dédiées (`services` et `equipements`). Cela permet d'alimenter des listes déroulantes dans l'application avec des données cohérentes et évite les erreurs de saisie.

## Prérequis
- Sauvegarde complète de la base de données avant toute opération.
- Accès administrateur à la base de données (MySQL ou compatible).
- Vérification que l'application est arrêtée pendant la migration pour éviter les conflits.

## Étapes de Migration

### 1. Sauvegarde de la Base de Données
Avant toute modification, effectuez une sauvegarde complète :
```bash
mysqldump -u [username] -p [database_name] > backup_before_migration.sql
```
Remplacez `[username]` et `[database_name]` par vos valeurs.

### 2. Exécution du Script de Migration
Connectez-vous à votre base de données et exécutez le fichier `migration_services_equipements.sql` :
```bash
mysql -u [username] -p [database_name] < migration_services_equipements.sql
```
Ou via un outil comme phpMyAdmin : importez le fichier SQL.

### 3. Vérification des Données
Après exécution, vérifiez :
- Que les tables `services` et `equipements` ont été créées et remplies avec les valeurs distinctes.
- Que les colonnes `service_id` et `equipement_id` ont été ajoutées à `salles` et remplies correctement.
- Que les contraintes de clés étrangères sont actives.

Exemples de requêtes de vérification :
```sql
SELECT COUNT(*) FROM services;
SELECT COUNT(*) FROM equipements;
SELECT s.nom, svc.nom AS service, eq.nom AS equipement FROM salles s LEFT JOIN services svc ON s.service_id = svc.id LEFT JOIN equipements eq ON s.equipement_id = eq.id;
```

### 4. Test de l'Application
- Redémarrez l'application et l'API.
- Vérifiez que les listes déroulantes pour services et équipements se remplissent correctement.
- Testez l'ajout/modification de salles pour s'assurer que les sélections fonctionnent.

### 5. Nettoyage (Après Validation)
Une fois la migration validée et l'application testée :
- Supprimez les anciennes colonnes `services` et `equipements` de la table `salles` en décommentant les lignes dans le script et en les exécutant séparément.
- Mettez à jour le code de l'API et de l'application pour utiliser les nouvelles structures.

## Précautions pour Éviter la Perte de Données
- **Sauvegarde Obligatoire** : Toujours sauvegarder avant et après la migration.
- **Test en Environnement de Développement** : Effectuez la migration d'abord sur une copie de la base en développement.
- **Vérification des NULL** : Les salles sans services/équipements auront des IDs NULL, ce qui est acceptable.
- **Rollback Possible** : Si des problèmes surviennent, restaurez la sauvegarde. Les anciennes colonnes sont conservées temporairement.
- **Arrêt des Services** : Assurez-vous que l'API et l'application sont arrêtées pendant la migration pour éviter les écritures concurrentes.

## En Cas de Problème
- Si des erreurs surviennent lors de l'insertion (ex. : doublons), vérifiez les données existantes.
- Pour les salles avec des valeurs multiples (si applicable), cette migration suppose des valeurs simples ; ajustez si nécessaire.
- Contactez l'équipe de développement pour tout support.

## Validation Finale
Après tests réussis, documentez la migration dans le changelog du projet et supprimez le fichier de migration du dépôt de production.
