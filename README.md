```markdown
# 🚚 Système de Gestion Optimisée de Tournées de Livraison

## 📋 Description du Projet

Application Spring Boot pour l'optimisation intelligente des tournées de livraison, développée pour réduire les coûts de carburant et améliorer l'efficacité logistique grâce à deux algorithmes d'optimisation.

## 🎯 Objectifs

- Gérer une flotte de véhicules hétérogène avec leurs contraintes spécifiques
- Planifier et optimiser automatiquement les tournées de livraison
- Comparer les performances de deux algorithmes d'optimisation
- Réduire les distances parcourues de 28% en moyenne

## 🏗️ Architecture

### Couches Applicatives
- **Controller** : REST API
- **Service** : Logique métier et algorithmes d'optimisation
- **Repository** : Accès aux données avec Spring Data JPA
- **DTO** : Transfert de données
- **Model** : Entités métier

### Technologies
- **Java 17+**
- **Spring Boot 3.5.7**
- **Spring Data JPA**
- **H2 Database**
- **Maven**
- **REST API**
- **XML Configuration** (sans annotations d'injection)

##  Diagramme de classe

<img width="526" height="751" alt="Capture d&#39;écran 2025-11-04 170558" src="https://github.com/user-attachments/assets/cdcaf6dd-9780-489e-a809-bb7eb85d36cb" />

## 📊 Entités Métier

### 🚗 Vehicle
- Types : BIKE, VAN, TRUCK
- Contraintes : poids max, volume max, nombre de livraisons max
- Exemple : VÉLO (50kg, 0.5m³, 15 livraisons)

### 📦 Delivery
- Coordonnées GPS (latitude, longitude)
- Poids, volume, créneau horaire
- Statuts : PENDING, IN_TRANSIT, DELIVERED, FAILED

### 🏭 Warehouse
- Point de départ et d'arrivée des tournées
- Adresse et horaires d'ouverture (06:00-22:00)

### 🗺️ Tour
- Assigné à un véhicule pour une journée
- Contient des livraisons ordonnées par algorithme

## 🔬 Algorithmes d'Optimisation

### 1. Nearest Neighbor Optimizer
- **Principe** : Toujours choisir la livraison la plus proche
- **Performance** : Rapide (~50ms pour 100 livraisons)
- **Résultat** : 180km moyenne pour 100 livraisons

### 2. Clarke & Wright Optimizer  
- **Principe** : Fusionner les paires qui économisent le plus de kilomètres
- **Formule** : `Économie = Distance(W,A) + Distance(W,B) - Distance(A,B)`
- **Performance** : 200ms pour 100 livraisons
- **Résultat** : 130km moyenne (28% de réduction)

## 🚀 Installation et Démarrage

### Prérequis
- Java 17 ou supérieur
- Maven 3.6+
- IDE IntelliJ

### Installation
```bash
git clone delivery_tour_optimizer
cd delivery_tour_optimizer
mvn clean install
```

### Démarrage
```bash
mvn spring-boot:run
```

L'application sera accessible sur : `http://localhost:8080`

## 📡 API Endpoints

### 🏭 Warehouses
- `GET /api/warehouses` - Lister tous les entrepôts
- `GET /api/warehouses/{id}` - Obtenir un entrepôt par ID
- `POST /api/warehouses` - Créer un nouvel entrepôt
- `PUT /api/warehouses/{id}` - Modifier un entrepôt
- `DELETE /api/warehouses/{id}` - Supprimer un entrepôt

### 🚗 Vehicles
- `GET /api/vehicles` - Lister tous les véhicules
- `GET /api/vehicles/{id}` - Obtenir un véhicule par ID
- `POST /api/vehicles` - Créer un nouveau véhicule
- `PUT /api/vehicles/{id}` - Modifier un véhicule
- `DELETE /api/vehicles/{id}` - Supprimer un véhicule

### 📦 Deliveries
- `GET /api/deliveries` - Lister toutes les livraisons
- `GET /api/deliveries/{id}` - Obtenir une livraison par ID
- `POST /api/deliveries` - Créer une nouvelle livraison
- `PUT /api/deliveries/{id}` - Modifier une livraison
- `DELETE /api/deliveries/{id}` - Supprimer une livraison

### 🗺️ Tours
- `POST /api/tours/optimize` - Optimiser une tournée
- `GET /api/tours/test` - Test de l'API

## 🔧 Configuration

### Fichier application.properties
```properties
server.port=8080
spring.datasource.url=jdbc:h2:mem:deliverydb
spring.jpa.hibernate.ddl-auto=create-drop
spring.h2.console.enabled=true
```

### Configuration XML
L'injection de dépendances est configurée manuellement dans `applicationContext.xml` sans annotations.

## 🧪 Tests

### Tests Unitaires
```bash
mvn test
```

### Tests d'API avec Postman
Collection Postman incluse pour tester tous les endpoints.

## 📈 Performance

### Comparaison des Algorithmes
| Algorithme | Temps (100 livraisons) | Distance | Réduction |
|------------|------------------------|----------|-----------|
| Nearest Neighbor | 50ms | 180km | - |
| Clarke & Wright | 200ms | 130km | 28% |

## 🗂️ Structure du Projet

```
src/main/java/com/dto/delivery_tour_optimizer/
├── controller/          # Contrôleurs REST
├── service/            # Services métier et algorithmes
├── repository/         # Interfaces Spring Data JPA
├── model/             # Entités JPA
├── dto/               # Data Transfer Objects
└── config/            # Configuration
```

## 👥 Gestion de Projet

- **Methodologie** : SCRUM avec JIRA
- **Versioning** : Git avec branches
- **Qualité** : SonarLint, Tests Unitaires
- **Documentation** : Swagger/Postman

## Capture d'écran


<img width="1872" height="854" alt="Capture d&#39;écran 2025-11-04 170418" src="https://github.com/user-attachments/assets/2a708b40-f30b-46e1-b69d-047f2aaa8d17" />

## 🚧 Contraintes Techniques Respectées

- ✅ Injection de dépendances via XML uniquement
- ✅ Pas d'annotations @Autowired, @Service, @Repository
- ✅ H2 comme SGBD
- ✅ Java 8+ avec Stream API
- ✅ Couche DTO
- ✅ Design Patterns Repository, DTO, Mapper

## 📝 Licence

Projet académique - Tous droits réservés

## 👨‍💻 Auteur

Développé dans le cadre d'un projet de formation en ingénierie logicielle.

---

**💡 Note** : Ce système permet de réduire significativement les coûts logistiques grâce à l'optimisation intelligente des tournées de livraison.
```
