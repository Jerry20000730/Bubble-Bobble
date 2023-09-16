package com.example.BubbleBobble.Model.InteractableWorld;

import com.example.BubbleBobble.Model.Character.Boss;
import com.example.BubbleBobble.Model.Cooldown.CoolDownTimer;
import com.example.BubbleBobble.GameObject;
import com.example.BubbleBobble.Main;
import com.example.BubbleBobble.Model.Bubble.Bubble;
import com.example.BubbleBobble.Model.Character.Enemy;
import com.example.BubbleBobble.Model.SpecialItem.Fruit;
import com.example.BubbleBobble.Model.Character.Hero;
import com.example.BubbleBobble.Model.Projectile.EnemyProjectile;
import com.example.BubbleBobble.Model.Projectile.HeroProjectile;
import com.example.BubbleBobble.Model.SpecialItem.BombItem;
import com.example.BubbleBobble.Model.SpecialItem.IceItem;
import com.example.BubbleBobble.Model.SpecialItem.MedicineItem;
import com.example.BubbleBobble.Model.SpecialItem.SpecialItem;
import com.example.BubbleBobble.Model.Unit.CeilingUnit;
import com.example.BubbleBobble.Model.Unit.FloorUnit;
import com.example.BubbleBobble.Model.Unit.WallUnit;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.io.InputStream;
import java.util.*;

/**
 * The interactable world where all static and dynamic component are in, it is the core of the game.
 */
public class InteractableWorld extends Canvas {
	private static final String DEFAULT_HERO_COLOR = "yellow";
	private static final String DEFAULT_BACKGROUND_IMAGE = "sea";
	private static final String DEFAULT_DIFFICULTY = "easy";
	private static final double HARD_LEVEL_COEFFICIENT = 1.8;
	private static final double MEDIUM_LEVEL_COEFFICIENT = 1.2;
	private static final double EASY_LEVEL_COEFFICIENT = 0.8;

	private ArrayList<CeilingUnit> ceilingUnits;
	private ArrayList<FloorUnit> floorUnits;
	private ArrayList<WallUnit> wallUnits;
	private ArrayList<Hero> heroes;
	private ArrayList<Enemy> enemies;
	private ArrayList<HeroProjectile> heroProjectiles;
	private ArrayList<EnemyProjectile> enemyProjectiles;
	private ArrayList<Fruit> fruits;
	private ArrayList<Bubble> bubbles;
	private ArrayList<BombItem> bombItems;
	private ArrayList<IceItem> iceItems;
	private ArrayList<MedicineItem> medicineItems;
	private ArrayList<Boss> bosses;
	private List<String> itemList = new ArrayList<String>() {{
		add("ice");
		add("bomb");
		add("medicine");
	}};
	private ArrayList<GameObject> toBeRemoved;
	private ArrayList<Integer> scores;

	private boolean readyToReset;
	private String HeroColor;
	private String Background;

	private SimpleIntegerProperty currentLevel = new SimpleIntegerProperty(this, "level", 1);
	private SimpleIntegerProperty score = new SimpleIntegerProperty(this, "score", 0);
	private SimpleIntegerProperty lives = new SimpleIntegerProperty(this, "live", 3);
	private SimpleStringProperty difficulty = new SimpleStringProperty(DEFAULT_DIFFICULTY);
	private SimpleBooleanProperty isNextLevel = new SimpleBooleanProperty(this, "isNextLevel", false);
	private SimpleBooleanProperty isWin = new SimpleBooleanProperty(this, "isWin", false);
	private SimpleBooleanProperty isGameOver = new SimpleBooleanProperty(this, "gameOver", false);
	private DynamicComponentFactory DCF = new DynamicComponentFactory();
	private StaticComponentFactory SCF = new StaticComponentFactory();
	private ItemFactory IF = new ItemFactory();

	private int EnemyNumber;

	/**
	 * The interactable world constructor to initialize some variables
	 * @param width the width of the canvas
	 * @param height the height of the canvas
	 */
	public InteractableWorld(int width, int height)  {
		super(width, height);

		// ArrayList of all component initialization
		ceilingUnits = new ArrayList<CeilingUnit>();
		floorUnits = new ArrayList<FloorUnit>();
		wallUnits = new ArrayList<WallUnit>();
		heroes = new ArrayList<Hero>();
		enemies = new ArrayList<Enemy>();
		heroProjectiles = new ArrayList<HeroProjectile>();
		enemyProjectiles = new ArrayList<EnemyProjectile>();
		toBeRemoved = new ArrayList<GameObject>();
		fruits = new ArrayList<Fruit>();
		bubbles = new ArrayList<Bubble>();
		bombItems = new ArrayList<BombItem>();
		iceItems = new ArrayList<IceItem>();
		medicineItems = new ArrayList<MedicineItem>();
		scores = new ArrayList<Integer>();
		bosses = new ArrayList<Boss>();
		currentLevel.set(1);
		scores.add(0);

		readyToReset = false;
		HeroColor = DEFAULT_HERO_COLOR;
		Background = DEFAULT_BACKGROUND_IMAGE;
		EnemyNumber = 0;
	}

	/**
	 * Add all component on the graphics Context
	 * @param gc the graphics context that created by the interactable world.
	 */
	public void paintStaticComponent(GraphicsContext gc) {
		//paints everything on the world
		for (CeilingUnit ceilingUnit : ceilingUnits) {
			ceilingUnit.drawOn(gc);
		}
		for (FloorUnit floorUnit : floorUnits) {
			floorUnit.drawOn(gc);
		}
		for (WallUnit wallUnit : wallUnits) {
			wallUnit.drawOn(gc);
		}
	}

	public void paintDynamicComponent(GraphicsContext gc) {
		for (Hero hero : heroes) {
			hero.drawOn(gc);
		}
		for (Enemy enemy : enemies) {
			enemy.drawOn(gc);
		}
		for (EnemyProjectile enemyProjectile : enemyProjectiles) {
			enemyProjectile.drawOn(gc);
		}
		for (HeroProjectile heroProjectile : heroProjectiles) {
			heroProjectile.drawOn(gc);
		}
		for (Fruit fruit : fruits) {
			fruit.drawOn(gc);
		}
		for (Bubble bubble : bubbles) {
			bubble.drawOn(gc);
		}
		for (BombItem bombItem : bombItems) {
			bombItem.drawOn(gc);
		}
		for (IceItem iceItem : iceItems) {
			iceItem.drawOn(gc);
		}
		for (MedicineItem medicineItem : medicineItems) {
			medicineItem.drawOn(gc);
		}
		for (Boss boss : bosses) {
			boss.drawOn(gc);
		}
	}

	/**
	 * updates the positions of everything on the screen
	 */
	public void updatePosition() {
		// update character positions
		for (Hero hero : heroes) {
			hero.update();
		}
		for (Enemy enemy : enemies) {
			enemy.update();
			if(enemy.canRemove) {
				toBeRemoved.add(enemy);
			}
		}
		for (Boss boss : bosses) {
			boss.update();
			if (boss.canRemove) {
				toBeRemoved.add(boss);
			}
		}
		for (EnemyProjectile enemyProjectile : enemyProjectiles) {
			enemyProjectile.update();
			if (enemyProjectile.canRemove) {
				toBeRemoved.add(enemyProjectile);
			}
		}
		for (HeroProjectile heroProjectile : heroProjectiles) {
			heroProjectile.update();
			if (heroProjectile.canRemove) {
				toBeRemoved.add(heroProjectile);
			}
		}
		for (Fruit fruit : fruits) {
			fruit.update();
			if (fruit.canRemove) {
				toBeRemoved.add(fruit);
			}
		}
		for (Bubble bubble : bubbles) {
			//charge = 0;
			bubble.update();
			if (bubble.canRemove) {
				toBeRemoved.add(bubble);
			}
		}
		for (BombItem bombItem : bombItems) {
			bombItem.update();
			if (bombItem.canRemove) {
				toBeRemoved.add(bombItem);
			}
		}
		for (IceItem iceItem : iceItems) {
			iceItem.update();
			if (iceItem.canRemove) {
				toBeRemoved.add(iceItem);
			}
		}
		for (MedicineItem medicineItem : medicineItems) {
			medicineItem.update();
			if (medicineItem.canRemove) {
				toBeRemoved.add(medicineItem);
			}
		}

		// updating collision with static component
		// Units initiate collisions with Heroes, Enemies, and Fruits
		for (CeilingUnit ceilingUnit : ceilingUnits) {
			for (Hero hero : heroes) {
				ceilingUnit.collideWith(hero);
			}
			for (Enemy enemy : enemies) {
				ceilingUnit.collideWith(enemy);
				enemy.collideWith(ceilingUnit);
			}
			for (Boss boss : bosses) {
				ceilingUnit.collideWith(boss);
			}
			for (Fruit fruit : fruits) {
				ceilingUnit.collideWith(fruit);
			}
			for (EnemyProjectile enemyProjectile : enemyProjectiles) {
				ceilingUnit.collideWith(enemyProjectile);
			}
			for (HeroProjectile heroProjectile : heroProjectiles) {
				ceilingUnit.collideWith(heroProjectile);
			}
			for (BombItem bombItem : bombItems) {
				ceilingUnit.collideWith(bombItem);
			}
			for (IceItem iceItem : iceItems) {
				ceilingUnit.collideWith(iceItem);
			}
			for (MedicineItem medicineItem : medicineItems) {
				ceilingUnit.collideWith(medicineItem);
			}
		}
		for (FloorUnit floorUnit: floorUnits) {
			for (Hero hero : heroes) {
				floorUnit.collideWith(hero);
			}
			for (Enemy enemy : enemies) {
				floorUnit.collideWith(enemy);
				enemy.collideWith(floorUnit);
			}
			for (Boss boss : bosses) {
				floorUnit.collideWith(boss);
			}
			for (Fruit fruit : fruits) {
				floorUnit.collideWith(fruit);
			}
			for (EnemyProjectile enemyProjectile : enemyProjectiles) {
				floorUnit.collideWith(enemyProjectile);
			}
			for (HeroProjectile heroProjectile : heroProjectiles) {
				floorUnit.collideWith(heroProjectile);
			}
			for (BombItem bombItem : bombItems) {
				floorUnit.collideWith(bombItem);
			}
			for (IceItem iceItem : iceItems) {
				floorUnit.collideWith(iceItem);
			}
			for (MedicineItem medicineItem : medicineItems) {
				floorUnit.collideWith(medicineItem);
			}
		}

		for (WallUnit wallUnit : wallUnits) {
			for (Hero hero : heroes) {
				wallUnit.collideWith(hero);
			}
			for (Enemy enemy : enemies) {
				wallUnit.collideWith(enemy);
				enemy.collideWith(wallUnit);
			}
			for (Boss boss : bosses) {
				wallUnit.collideWith(boss);
			}
			for (Fruit fruit : fruits) {
				wallUnit.collideWith(fruit);
			}
			for (EnemyProjectile enemyProjectile : enemyProjectiles) {
				wallUnit.collideWith(enemyProjectile);
			}
			for (HeroProjectile heroProjectile : heroProjectiles) {
				wallUnit.collideWith(heroProjectile);
			}
			for (BombItem bombItem : bombItems) {
				wallUnit.collideWith(bombItem);
			}
			for (IceItem iceItem : iceItems) {
				wallUnit.collideWith(iceItem);
			}
			for (MedicineItem medicineItem : medicineItems) {
				wallUnit.collideWith(medicineItem);
			}
		}

		// Enemies initiate collisions with Heroes
		for (Enemy enemy : enemies) {
			for (Hero hero : heroes) {
				enemy.collideWith(hero);
			}
		}

		// boss initiate collisions with heroes
		for (Boss boss : bosses) {
			for (Hero hero : heroes) {
				boss.collideWith(hero);
			}
		}

		// HeroProjectiles initiate collisions with Heroes and Enemies
		for (HeroProjectile heroProjectile : heroProjectiles) {
			for (Hero hero : heroes) {
				heroProjectile.collideWith(hero);
			}
			for (Enemy enemy : enemies) {
				heroProjectile.collideWith(enemy);
			}
			for (Boss boss : bosses) {
				heroProjectile.collideWith(boss);
			}
		}
		for (EnemyProjectile enemyProjectile  : enemyProjectiles) {
			for (Hero hero : heroes) {
				enemyProjectile.collideWith(hero);
			}
			for (Enemy enemy : enemies) {
				enemyProjectile.collideWith(enemy);
			}
		}

		// Fruits intiate collisions with Heroes
		for (Fruit fruit : fruits) {
			for (Hero hero : heroes) {
				fruit.collideWith(hero);
			}
		}
		for (Bubble bubble : bubbles) {
			for (Enemy enemy : enemies) {
				bubble.collideWith(enemy);
			}
		}
		for (BombItem bombItem : bombItems) {
			for (Hero hero: heroes) {
				bombItem.collideWith(hero);
			}
		}
		for (IceItem iceItem : iceItems) {
			for (Hero hero: heroes) {
				iceItem.collideWith(hero);
			}
		}
		for (MedicineItem medicineItem : medicineItems) {
			for (Hero hero: heroes) {
				medicineItem.collideWith(hero);
			}
		}

		// Removing objects
		for (GameObject obj : toBeRemoved) {
			remove(obj);
		}
		toBeRemoved.removeAll(toBeRemoved);
		if (readyToReset)
			startGame();
	}

	/**
	 * To add ceiling unit recognized in world.txt in the arraylist
	 * @param ceilingUnit the ceiling unit
	 */
	public void addCeilingUnit(CeilingUnit ceilingUnit) {
		ceilingUnits.add(ceilingUnit);
	}

	/**
	 * To add floor unit recognized in world.txt in the arraylist
	 * @param floorUnit the floor unit
	 */
	public void addFloorUnit(FloorUnit floorUnit) {
		floorUnits.add(floorUnit);
	}

	/**
	 * To add wall unit recognized in world.txt in the arraylist
	 * @param wallUnit the wall unit
	 */
	public void addWallUnit(WallUnit wallUnit) {
		wallUnits.add(wallUnit);
	}

	/**
	 * To add hero recognized in world.txt in the arraylist
	 * @param hero the hero
	 */
	void addHero(Hero hero) {
		//adds a hero to the map
		heroes.add(hero);
	}

	/**
	 * To add enemy recognized in world.txt in the arraylist
	 * @param enemy the enemy
	 */
	void addEnemy(Enemy enemy) {
		//adds a mook to the map
		enemies.add(enemy);
	}

	/**
	 * To add the projectile shot by the hero
	 * @param heroProjectile the projectile shot by hero
	 */
	public void addHeroProjectile(HeroProjectile heroProjectile) {
		heroProjectiles.add(heroProjectile);
	}

	/**
	 * To add the projectile shot by the enemy
	 * @param enemyProjectile the projectile shot by hero
	 */
	public void addEnemyProjectile(EnemyProjectile enemyProjectile) {
		enemyProjectiles.add(enemyProjectile);
	}

	/**
	 * To add the fruit produced by the enemy's corpse
	 * @param fruit the fruit
	 */
	public void addFruit(Fruit fruit) {
		fruits.add(fruit);
	}

	/**
	 * To add the bubble status if the projectile produced by hero hit the enemy
	 * @param bubble the bubble
	 */
	public void addBubble(Bubble bubble) {
		bubbles.add(bubble);
	}

	/**
	 * To add the bombItem into the interactable world
	 * @param bombItem the bombItem of BombItem class
	 */
	public void addBombItem(BombItem bombItem) {
		bombItems.add(bombItem);
	}

	/**
	 * To add the IceItem into the interactable world
	 * @param iceItem the iceItem of IceItem class
	 */
	public void addIceItem(IceItem iceItem) {
		iceItems.add(iceItem);
	}

	/**
	 * To add the MedicineItem into the interactable world
	 * @param medicineItem the medicineItem of MedicineItem class
	 */
	public void addMedicineItem(MedicineItem medicineItem) {
		medicineItems.add(medicineItem);
	}

	public void addBoss(Boss boss) {
		bosses.add(boss);
	}

	/**
	 * Methods to add special items according to its type
	 * @param specialItem the type that is adding to the interactable world
	 */
	public void addItem(SpecialItem specialItem) {
		if (specialItem.getClass().equals(BombItem.class)) {
			addBombItem((BombItem) specialItem);
		} else if (specialItem.getClass().equals(IceItem.class)) {
			addIceItem((IceItem) specialItem);
		} else if (specialItem.getClass().equals(MedicineItem.class)) {
			addMedicineItem((MedicineItem) specialItem);
		}
 	}

	/**
	 * Methods that clears every components from the screen.
	 * by removing all objects in the arraylist
	 */
	void clearContents() {
		//clears everything from the screen
		ceilingUnits.removeAll(ceilingUnits);
		floorUnits.removeAll(floorUnits);
		wallUnits.removeAll(wallUnits);
		heroes.removeAll(heroes);
		enemies.removeAll(enemies);
		enemyProjectiles.removeAll(enemyProjectiles);
		heroProjectiles.removeAll(heroProjectiles);
		fruits.removeAll(fruits);
		bombItems.removeAll(bombItems);
		iceItems.removeAll(iceItems);
		medicineItems.removeAll(medicineItems);
		bosses.removeAll(bosses);
	}

	/**
	 * remove a single object from the screen
	 * @param obj any game object
	 */
	public void remove(GameObject obj) {
		//removes a single object from the screen
		ceilingUnits.remove(obj);
		floorUnits.remove(obj);
		wallUnits.remove(obj);
		heroes.remove(obj);
		enemies.remove(obj);
		enemyProjectiles.remove(obj);
		heroProjectiles.remove(obj);
		fruits.remove(obj);
		bubbles.remove(obj);
		bombItems.remove(obj);
		iceItems.remove(obj);
		medicineItems.remove(obj);
		bosses.remove(obj);
	}

	/**
	 * set boolean to make sure the world is ready to be reset
	 */
	public void markToReset() {
		readyToReset = true;
	}

	/**
	 * Method to calculate the final score once game is over, or game is win
	 * By taking the following parameters into account:
	 * @param difficulty the string value of the difficulties
	 * @param RemainingLives the remaining lives once is upgraded or died.
	 */
	public void calculateScore(String difficulty, int RemainingLives) {
		if (RemainingLives == 3) {
			this.score.set(this.getScore() + 500);
		} else if (RemainingLives == 2) {
			this.score.set(this.getScore() + 200);
		} else {
			this.score.set(this.getScore() + 100);
		}
		if (currentLevel.get() == 4) {
			this.score.set(this.getScore() + 1000);
		}
		if (difficulty.equalsIgnoreCase("hard")) {
			this.score.set((int) Math.round(this.getScore() * HARD_LEVEL_COEFFICIENT));
		} else if (difficulty.equalsIgnoreCase("medium")) {
			this.score.set((int) Math.round(this.getScore() * MEDIUM_LEVEL_COEFFICIENT));
		} else if (difficulty.equalsIgnoreCase("easy")) {
			this.score.set((int) Math.round(this.getScore() * EASY_LEVEL_COEFFICIENT));
		}
	}

	/**
	 * Methods to check whether the game can be upgraded to the next level
	 * by checking the remaining number of enemies.
	 */
	public void CheckNextLevel() {
		if (EnemyNumber == 0) {
			calculateScore(this.getDifficulty(), this.getLives());
			this.scores.add(this.getScore());
			this.setCurrentLevel(this.getCurrentLevel() + 1);
			this.setLives(3);
			this.EnemyNumber = 0;
			if (currentLevel.get() != 5) {
				isNextLevel.set(true);
			}
			CoolDownTimer.getInstance().reset();
		} else {
			isNextLevel.set(false);
		}
	}

	/**
	 * Methods checking whether the game is win
	 * by judging the number of the enemy
	 */
	public void CheckWin() {
		if (currentLevel.get() == 5 && EnemyNumber == 0) {
			isWin.set(true);
		}
		isWin.set(false);
	}

	/**
	 * Methods checking whether the game is over
	 * by judging the lives of the current world
	 */
	public void CheckGameOver() {
		if (lives.get() == 0) {
			isGameOver.set(true);
		}
	}

	/**
	 * Before every game start, this method is used to read from the world template
	 * in Resources/world. So that proper map can be produced.
	 */
	public void startGame() {
		InputStream input = null;
		this.EnemyNumber = 0;
		if (currentLevel.get() == 1) {
			input = this.getClass().getClassLoader().getResourceAsStream("world/World1.txt");
		} else if (currentLevel.get() == 2) {
			input = this.getClass().getClassLoader().getResourceAsStream("world/World2.txt");
		} else if (currentLevel.get() == 3) {
			input = this.getClass().getClassLoader().getResourceAsStream("world/World3.txt");
		} else if (currentLevel.get() == 4) {
			input = this.getClass().getClassLoader().getResourceAsStream("world/World4.txt");
		} else {
			input = this.getClass().getClassLoader().getResourceAsStream("world/WorldTemplate.txt");
		}
		Random r = new Random();
		Scanner scanner = new Scanner(input);
		clearContents();
		for (int row = 0; row < Main.HEIGHT; row++) {
			String currentLine = scanner.next();
			for (int col = 0; col < Main.WIDTH; col++) {
				if (currentLine.charAt(col) == '*') {
					addFloorUnit((FloorUnit) SCF.CreateUnit('*',this, col, row));
				} else if (currentLine.charAt(col) == 'H') {
					addHero((Hero) DCF.CreateCharacter('H', this, col, row));
				} else if (currentLine.charAt(col) == '|') {
					addWallUnit((WallUnit) SCF.CreateUnit('|', this, col, row));
				} else if (currentLine.charAt(col) == '_') {
					addCeilingUnit((CeilingUnit) SCF.CreateUnit('_', this, col, row));
				} else if (currentLine.charAt(col) == 'M') {
					addEnemy((Enemy) DCF.CreateCharacter('M', this, col, row));
					EnemyNumber++;
				} else if (currentLine.charAt(col) == 'I') {
					int i = r.nextInt(3);
					addItem(IF.CreateItem(itemList.get(i), this, col, row));
					Collections.shuffle(itemList);
				} else if (currentLine.charAt(col) == 'B') {
					addBoss((Boss) DCF.CreateCharacter('B', this, col, row));
					EnemyNumber++;
				}
			}
			if (scanner.hasNextLine()) {
				scanner.nextLine();
			}
		}
		scanner.close();

		readyToReset = false;
	}

	/**
	 * Getter method to get the score of the interactable world
	 * @return the int value of the current score
	 */
	public int getScore() {
		return score.get();
	}

	/**
	 * Getter method to get the score property of the interactable world
	 * @return the score property in SimpleIntegerProperty
	 */
	public SimpleIntegerProperty scoreProperty() {
		return score;
	}

	/**
	 * Setter method to set the score of the interactable world
	 * @param score the score value in int type
	 */
	public void setScore(int score) {
		this.score.set(score);
	}

	/**
	 * Getter method to get the boolean indicating whether the world is ready for next level
	 * @return a boolean value, true indicates that world is ready to upgrade to next level, false otherwise
	 */
	public boolean isIsNextLevel() {
		return isNextLevel.get();
	}

	/**
	 * Getter method to get the boolean property indicating whether the world is ready for next level
	 * @return a simple boolean property
	 */
	public SimpleBooleanProperty isNextLevelProperty() {
		return isNextLevel;
	}

	/**
	 * Setter method to set the boolean value indicating whether the world is ready for next level
	 * @param isNextLevel boolean value, true indicates that world is ready to upgrade to next level, false otherwise
	 */
	public void setIsNextLevel(boolean isNextLevel) {
		this.isNextLevel.set(isNextLevel);
	}

	/**
	 * Getter method to get current level int value of the current interactable world
	 * @return a int value indicating the current level of the interactable world
	 */
	public int getCurrentLevel() {
		return currentLevel.get();
	}

	/**
	 * Getter method to get current level property of the current interactable world
	 * @return a simple integer property storing the current level of the interactable world
	 */
	public SimpleIntegerProperty currentLevelProperty() {
		return currentLevel;
	}

	/**
	 * Setter method to set the int value of the current level of the interactable world
	 * @param currentLevel int value of the current level
	 */
	public void setCurrentLevel(int currentLevel) {
		this.currentLevel.set(currentLevel);
	}

	/**
	 * Getter method to get the hero color of the interactable world
	 * @return the color of the hero in string value in following format:
	 * <ol>
	 *     <li>"yellow"(DEFAULT)</li>
	 *     <li>"blue"</li>
	 *     <li>"red"</li>
	 *     <li>"green"</li>
	 *     <li>"orange"</li>
	 *     <li>"purple"</li>
	 *     <li>"grey"</li>
	 *     <li>"pink"</li>
	 * </ol>
	 */
	public String getHeroColor() {
		return HeroColor;
	}

	/**
	 * Setter method to set the color of the hero in string format
	 * @param heroColor the string color of hero in following format:
	 * <ol>
	 * <li>"yellow"(DEFAULT)</li>
	 * <li>"blue"</li>
	 * <li>"red"</li>
	 * <li>"green"</li>
	 * <li>"orange"</li>
	 * <li>"purple"</li>
	 * <li>"grey"</li>
	 * <li>"pink"</li>
	 * </ol>
	 */
	public void setHeroColor(String heroColor) {
		HeroColor = heroColor;
	}

	/**
	 * Getter method to get background theme of the interactable world
	 * @return the background theme of the world in string value in following format:
	 * <ol>
	 *     <li>"sea"(DEFAULT)</li>
	 *     <li>"mountain"</li>
	 *     <li>"jungle"</li>
	 * </ol>
	 */
	public String getBackground() {
		return Background;
	}

	/**
	 * Setter method to set background theme of the interactable world
	 * @param background the background theme of the world in string value in following format:
	 * <ol>
	 *     <li>"sea"(DEFAULT)</li>
	 *     <li>"mountain"</li>
	 *     <li>"jungle"</li>
	 * </ol>
	 */
	public void setBackground(String background) {
		Background = background;
	}

	/**
	 * Getter method to get the lives number of the current hero
	 * @return the lives number of hero in int value
	 */
	public int getLives() {
		return lives.get();
	}

	/**
	 * Getter method to get the property of the lives number of current hero
	 * @return the simple integer property of the lives number of current hero
	 */
	public SimpleIntegerProperty livesProperty() {
		return lives;
	}

	/**
	 * Setter method to set the lives of the current hero
	 * @param lives the lives number of current hero in int value
	 */
	public void setLives(int lives) {
		if (lives > 3) {
			this.lives.set(3);
		} else if (lives < 0) {
			this.lives.set(0);
		} else {
			this.lives.set(lives);
		}
	}

	/**
	 * Getter method to get the difficulty of the current interactable world
	 * @return the string value in following format:
	 * <ol>
	 *     <li>"easy"</li>
	 *     <li>"medium"</li>
	 *     <li>"hard"</li>
	 * </ol>
	 */
	public String getDifficulty() {
		return difficulty.get();
	}

	/**
	 * Getter method to get the simple integer property of the current interactable world
	 * @return the simple string property of the difficulty
	 */
	public SimpleStringProperty difficultyProperty() {
		return difficulty;
	}

	/**
	 * Setter method to set the difficulty of the current interactable world
	 * @param difficulty the string indicating the difficulty of the current interactable world in following format:
	 * <ol>
	 *     <li>"easy"</li>
	 *     <li>"medium"</li>
	 *     <li>"hard"</li>
	 * </ol>
	 */
	public void setDifficulty(String difficulty) {
		this.difficulty.set(difficulty);
	}

	/**
	 * Getter method to get the boolean value of whether the game is over
	 * @return a boolean value, true indicates that game is over, false otherwise
	 */
	public boolean isIsGameOver() {
		return isGameOver.get();
	}

	/**
	 * Getter method to get the boolean property of whether the game is over
	 * @return a boolean property indicating that game is over, false otherwise
	 */
	public SimpleBooleanProperty isGameOverProperty() {
		return isGameOver;
	}

	/**
	 * Setter method to set the boolean value of whether the game is over
	 * @param isGameOver true for game is over, false otherwise
	 */
	public void setIsGameOver(boolean isGameOver) {
		this.isGameOver.set(isGameOver);
	}

	/**
	 * Getter method to get the boolean value of whether the game is win
	 * @return a boolean value indicating that game is win, false otherwise
	 */
	public boolean isIsWin() {
		return isWin.get();
	}

	/**
	 * Getter method to get the boolean property of whether the game is win
	 * @return a boolean property indicating that game is win, false otherwise
	 */
	public SimpleBooleanProperty isWinProperty() {
		return isWin;
	}

	/**
	 * Setter method to set the boolean value of whether the game is win
	 * @param isWin true for game is over, false otherwise
	 */
	public void setIsWin(boolean isWin) {
		this.isWin.set(isWin);
	}

	/**
	 * Getter method to get the enemy number of the interactable world
	 * @return the int value indicating the enemy number
	 */
	public int getEnemyNumber() {
		return EnemyNumber;
	}

	/**
	 * Setter method to set the enemy number of the interactable world
	 * @param enemyNumber the int value indicating the enemy number
	 */
	public void setEnemyNumber(int enemyNumber) {
		EnemyNumber = enemyNumber;
	}
}