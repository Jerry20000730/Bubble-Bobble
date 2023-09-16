# Bubble Bobble
## Introduction
_Bubble Bobble_ is a popular game from 1986, in which you play as a dragon that spits bubbles at their enemies. This project is an extended version of the pre-developed game skeleton using Java Swing API, which was replaced by the JavaFX API. The Project adopts different design patterns and MVC pattern. In addition, some of the new game features were added. 

## Demo

Please see the demo at [here](https://drive.google.com/file/d/1PCE149YMx0pG84OZYI4pKECrXy8nOTgC/view?usp=drive_link)

## Instructions

You play as a red square that can move around, jump, and use a few tools to defend against the enemy blue and green squares. The objective of the game is to defeat all the enemies in the level by bubbling them and subsequently popping them. If you come into contact with an enemy (or the bullets they shoot), you lose a life and the level is restarted. If you lose all of your lives, you return to the first level and your score is reset to 0.

## Controls

- **Left and right arrow keys:** Move in a horizontal direction.
- **Up arrow key:** Jump upwards.
- **Space:** When held, you move faster than normal, as if you were dashing.
- **E:** Shoot a bubble.

## New Features
At the basis of original game, new features were added to the game.

### 1. Special items
Apart from fighting with enemies, some special items that are also added to the world, to make the game more fun. Items includes: 
- **Bomb![bomb icon](BubbleBobble/src/main/resources/com/example/BubbleBobble/picture/bomb.png):** Colliding with a bomb results in losing one live.
- **Ice:**![ice icon](BubbleBobble/src/main/resources/com/example/BubbleBobble/picture/ice.png) Colliding with a ice results in slow moving speed & jump speed of the hero.
- **Medicine:** ![medicine icon](BubbleBobble/src/main/resources/com/example/BubbleBobble/picture/medicine.png)Colliding with a medicine can make hero recover its live by one.

### 2. Boss
In the final level of the BubbleBobble, hero will fight with a boss. Unlike the enemy, boss has a health point (HP) value with a indication bar on the top. When user shoot projectile, the health point will be deducted. At the time when HP value deducts to zero, the boss is dead.

### 3. Difficulty
Three game difficulty are available, and each difficulty adjust the attributes of the enemy and the change action probability: (1) Easy (2)Medium (3)Hard
### 4. Cool down time
During the game, each time when the hero shoot the projectile, the energy of the hero is deducted. When the energy of the hero is less than a certain value, hero can no longer shoot the projectile. Once the energy of hero is full, the hero can ready to charge a big move, making enemies to "Bubbled" state.

### 5. Lives & Scores
At the beginning each level, hero has three lives, and once hero dies, the live deducts by one. If hero kills all the enemy before losing all the lives, hero upgrades to the next level, otherwise, the game is over.

### 6. Changing the background
At the begininng of each round, user can choose three themes of background image: (1)sea (2)mountain (3)jungle



## Used Design Pattern

In order to make the game more maintainable, several design patterns were adopted in the game.
### Factory
In this project, the `FloorUnit`, `WallUnit` and `CeilingUnit` was created by `StaticComponentFactory`. Other game objects, such as `hero`, `enemy`, and `boss`, are created by `DynamicComponentFactory`.
### State
In this project, the hero has many states when user plays the game. In the code, the hero state includes `NormalHeroState`, `ShieldHeroState`, `RecoverHeroState`, `FreezeHeroState` and `StunnedHeroState`. 
### Strategy
In this project, when hero collides with different special items, different actions involved in strategy (e.g. `BombStrategy.doOperation()`) would be triggered. 
### Singleton

In this project, the `UserHolder` class uses singleton contains one instance containing an attribute `UserSetting` storing user setting data to pass through different controllers.

### Prototype

In this project, the creating of duplicate `HeroProjectile` and `enemyProjectile` extending abstract class `ProjectileShape` using `ProjectileCache` to keep the performance. 

