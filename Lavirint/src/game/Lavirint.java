/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import geometry.Vector;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import objects.Circle;
import objects.Coin;
import objects.Enemy;
import objects.Flag;
import objects.MazeObject;
import objects.Protagonist;
import objects.Ground;
import objects.Projectile;
import objects.Protagonist.Direction;
import objects.Sat;
import objects.Wall;
import state.StandState;
import state.TurnState;
import state.WalkState;

/**
 *
 * @author Mina
 */
public class Lavirint extends Application {

    private static int WINDOW_HEIGHT = 700;
    private static int WINDOW_WIDTH = 900;

    private int score = 0;
    private Text scoreText;
    private Text gameOverText;
    private boolean gameover = false;
    int rows = 0, columns = 0;
    private String fileName = "Maze1.txt";
    private Group root;
    private Group mainSceneRoot;
    private Group displaySceneRoot;

    private double time = 30;

    private Scene scene;
    private SubScene mainSubscene, displaySubscene;

    private Protagonist protagonist;
    private List<Wall> walls = new ArrayList<>();
    private List<Coin> coins = new ArrayList<>();
    private List<Sat> sats = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private List<Projectile> projectiles = new ArrayList<>();
    private ArrayList<ArrayList<MazeObject>> all_objects = new ArrayList<>();
    private Flag flag;

    private static Ground ground;
    private static final int CAMERA_MIN_ZOOM_OUT = -2000;
    private static final int CAMERA_MAX_ZOOM_OUT = -200;
    private UpdateTimer timer = new UpdateTimer();

    private PerspectiveCamera defaultCamera = new PerspectiveCamera();
    private Camera camera;

    private boolean stopping = false;
    private boolean w_pressed = false, a_pressed = false,
            s_pressed = false, d_pressed = false;
    private boolean followPlayer = false, fps = false;
    private Text timeText;

    private class UpdateTimer extends AnimationTimer {

        private long previous = 0;

        @Override
        public void handle(long now) {
            if (previous == 0) {
                previous = now;
            }
            float passed = (now - previous) / 100000000f;

            int acceleration = 5;
            if (stopping) {
                acceleration *= -1;
            }
            if (!gameover) {
                time -= passed / 10;
                if (time < 0) {
                    time = 0;
                    gameover = true;
                    gameOverText.setVisible(true);
                }
            }
            timeText.setText("Left: " + (int) time);
            protagonist.update(passed, acceleration);
            for (Enemy e : enemies) {
                e.update(passed, acceleration);
            }
            for (Projectile p : projectiles) {
                p.update(passed, 0);
            }
            checkForCollisions();
            moveBirdViewCamera();
            previous = now;
        }
    }

    private void checkForCollisions() {

        Iterator<Wall> iterator_w = walls.iterator();
        while (iterator_w.hasNext()) {
            Wall wall = iterator_w.next();
            if (wall.getBoundsInParent().intersects(protagonist.getBoundsInParent())) {
                // if collides with the wall, undo move
                protagonist.setTranslateX(protagonist.getPosition().getX());
                protagonist.setTranslateY(protagonist.getPosition().getY());
                break;
            }
        }

        Iterator<Coin> iterator_c = coins.iterator();
        while (iterator_c.hasNext()) {
            Coin coin = iterator_c.next();
            if (coin.getBoundsInParent().intersects(protagonist.getBoundsInParent())) {
                coin.setTranslateZ(10000);
                mainSceneRoot.getChildren().remove(coin);
                score++;
                scoreText.setText("Score: " + score);
                break;
            }
        }

        Iterator<Sat> iterator_s = sats.iterator();
        while (iterator_s.hasNext()) {
            Sat sat = iterator_s.next();
            if (sat.getBoundsInParent().intersects(protagonist.getBoundsInParent())) {
                sat.setTranslateZ(10000);
                mainSceneRoot.getChildren().remove(sat);
                time += 5;
                timeText.setText("Left: " + (int) time);
                break;
            }
        }
        for (Enemy e : enemies) {
            iterator_w = walls.iterator();
            while (iterator_w.hasNext()) {
                Wall wall = iterator_w.next();
                if (wall.getBoundsInParent().intersects(e.getBoundsInParent())) {
                    // if collides with the wall, undo move
                    e.setTranslateX(e.getPosition().getX());
                    e.setTranslateY(e.getPosition().getY());
                    if (e.current == Protagonist.Direction.DOWN) {
                        e.current = Protagonist.Direction.UP;
                    } else if (e.current == Protagonist.Direction.UP) {
                        e.current = Protagonist.Direction.DOWN;
                    } else if (e.current == Protagonist.Direction.LEFT) {
                        e.current = Protagonist.Direction.RIGHT;
                    } else {
                        e.current = Protagonist.Direction.LEFT;
                    }
                    break;
                }
            }
            if (e.getBoundsInParent().intersects(protagonist.getBoundsInParent())) {
                gameOverText.setVisible(true);
                gameover = true;
                break;
            }

        }
        for (Projectile p : projectiles) {
            iterator_w = walls.iterator();
            while (iterator_w.hasNext()) {
                Wall wall = iterator_w.next();
                if (wall.getBoundsInParent().intersects(p.getBoundsInParent())) {
                    mainSceneRoot.getChildren().remove(p);
                    p.setTranslateY(1000);
                    break;
                }
            }

            Iterator<Enemy> iterator_e = enemies.iterator();
            while (iterator_e.hasNext()) {
                Enemy e = iterator_e.next();
                if (e.getBoundsInParent().intersects(p.getBoundsInParent())) {
                    mainSceneRoot.getChildren().remove(p);
                    p.setTranslateY(1000);
                    mainSceneRoot.getChildren().remove(e);
                    e.setTranslateY(2000);
                    score++;
                    scoreText.setText("Score: " + score);
                    break;
                }
            }

        }

        if (flag.getBoundsInParent().intersects(protagonist.getBoundsInParent())) {
            Direction dir = protagonist.getDirection();
            if (dir.toString().equals(Direction.UP.toString()) && !gameover) {
                gameover = true;
                gameOverText.setVisible(true);
            }
        }
        protagonist.getPosition().setX(protagonist.getTranslateX());
        protagonist.getPosition().setY(protagonist.getTranslateY());
    }

    private void moveBirdViewCamera() {
        boolean b = defaultCamera.isVerticalFieldOfView();
        double alfa = defaultCamera.getFieldOfView();
        double beta = Math.toDegrees(Math.asin(Math.sin(Math.toRadians(alfa)) / WINDOW_HEIGHT * WINDOW_WIDTH));
        double y = defaultCamera.getBoundsInParent().getMaxY();
        double x = defaultCamera.getBoundsInParent().getMaxX();
        double z = defaultCamera.getBoundsInParent().getMaxZ();
        double deltaY = Math.sin(Math.toRadians(alfa / 2)) * (-z);
        double deltaX = Math.sin(Math.toRadians(beta / 2)) * (-z);

        if (!followPlayer && !fps) {
            if (w_pressed) {
                if (y - deltaY > -WINDOW_HEIGHT * 5 / 4) {
                    defaultCamera.getTransforms().add(new Translate(0, -10, 0));
                }
            }
            if (a_pressed) {
                if (x - deltaX > -WINDOW_WIDTH * 5 / 4) {
                    defaultCamera.getTransforms().add(new Translate(-10, 0, 0));
                }
            }
            if (s_pressed) {
                if (y + deltaY < WINDOW_HEIGHT / 4) {
                    defaultCamera.getTransforms().add(new Translate(0, 10, 0));
                }
            }
            if (d_pressed) {
                if (x + deltaX < WINDOW_WIDTH / 4) {
                    defaultCamera.getTransforms().add(new Translate(10, 0, 0));
                }
            }
        } else {
            if (followPlayer) {
                defaultCamera.setTranslateX(protagonist.getPosition().getX());
                defaultCamera.setTranslateY(protagonist.getPosition().getY());
            } else {
                defaultCamera.setTranslateX(protagonist.getPosition().getX());
                defaultCamera.setTranslateY(protagonist.getPosition().getY());
                defaultCamera.setTranslateZ(protagonist.getPosition().getZ());
                defaultCamera.getTransforms().clear();
                defaultCamera.setRotationAxis(Rotate.Z_AXIS);
                defaultCamera.setRotate(protagonist.getRotate());

            }
        }
    }

    private void instantiateCameras() {
        defaultCamera.getTransforms().add(new Translate(-100 * ((double) columns / 2), -100 * ((double) rows / 2), -2000));
        root.setOnScroll((event) -> {
            double deltaY = event.getDeltaY();
            double current = defaultCamera.getBoundsInParent().getMaxZ();
            if (deltaY > 0) {
                if (current + deltaY < CAMERA_MAX_ZOOM_OUT) {
                    // Zoom in
                    defaultCamera.getTransforms().add(new Translate(0, 0, event.getDeltaY()));

                }
            } else {
                if (current + deltaY > CAMERA_MIN_ZOOM_OUT) {
                    //Zoom out
                    defaultCamera.getTransforms().add(new Translate(0, 0, event.getDeltaY()));
                    double alfa = defaultCamera.getFieldOfView();
                    double y = defaultCamera.getBoundsInParent().getMaxY();
                    double x = defaultCamera.getBoundsInParent().getMaxX();
                    double z = defaultCamera.getBoundsInParent().getMaxZ();
                    double beta = Math.toDegrees(Math.asin(Math.sin(Math.toRadians(alfa)) / WINDOW_HEIGHT * WINDOW_WIDTH));
                    double deltaAngleY = Math.sin(Math.toRadians(alfa / 2)) * (-z);
                    double deltaAngleX = Math.sin(Math.toRadians(beta / 2)) * (-z);
                    // ispravka za zoom out
                    if (y - deltaAngleY < -WINDOW_HEIGHT * 5 / 4) {
                        defaultCamera.getTransforms().add(new Translate(0, (-WINDOW_HEIGHT - WINDOW_HEIGHT / 4) - (y - deltaAngleY), 0));
                    }
                    if (x - deltaAngleX < -WINDOW_WIDTH * 5 / 4) {
                        defaultCamera.getTransforms().add(new Translate((-WINDOW_WIDTH - WINDOW_WIDTH / 4) - (x - deltaAngleX), 0, 0));
                    }
                    if (y + deltaAngleY > WINDOW_HEIGHT / 4) {
                        defaultCamera.getTransforms().add(new Translate(0, (WINDOW_HEIGHT - WINDOW_HEIGHT * 3 / 4) - (y + deltaAngleY), 0));
                    }
                    if (x + deltaAngleX > WINDOW_WIDTH / 4) {
                        defaultCamera.getTransforms().add(new Translate((WINDOW_WIDTH - WINDOW_WIDTH * 3 / 4) - (x + deltaAngleX), 0, 0));
                    }

                }
            }
        });

        camera = defaultCamera;
    }

    private void readFile() {
        ArrayList<String> rows_lines = new ArrayList<>();

        try {
            InputStream in = getClass().getClassLoader().getResourceAsStream(fileName);
            String line;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            while ((line = bufferedReader.readLine()) != null) {
                rows_lines.add(line);
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '"
                    + fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                    + fileName + "'");
        }

        if (!rows_lines.isEmpty()) {
            columns = rows_lines.get(0).length();
            rows = rows_lines.size();
            for (int j = 0; j < rows_lines.size(); j++) {
                String line = rows_lines.get(j);
                all_objects.add(new ArrayList<>());
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    Vector position = new Vector((-((double) columns) / 2 + 0.5f + i) * Wall.WIDTH, (-((double) rows) / 2 + 0.5f + j) * Wall.WIDTH, 0);
                    switch (c) {
                        case 'w':
                        case 'W':
                            // Wall
                            Wall wall = new Wall(position);
                            mainSceneRoot.getChildren().add(wall);
                            walls.add(wall);
                            break;
                        case 'p':
                        case 'P':
                            // Protagonist
                            protagonist = new Circle(position, 0, Lavirint.this);
                            mainSceneRoot.getChildren().add(protagonist);
                            break;
                        case 'c':
                        case 'C':
                            Coin coin = new Coin(position);
                            mainSceneRoot.getChildren().add(coin);
                            coins.add(coin);
                            break;
                        case 's':
                        case 'S':
                            Sat sat = new Sat(position);
                            mainSceneRoot.getChildren().add(sat);
                            sats.add(sat);
                            break;
                        case 'e':
                        case 'E':
                            Enemy e = new Enemy(position, 0, this);
                            mainSceneRoot.getChildren().add(e);
                            enemies.add(e);
                            break;
                        case 'f':
                        case 'F':
                            flag = new Flag(position);
                            mainSceneRoot.getChildren().add(flag);

                            break;
                        default:
                            break;
                    }
                }
            }
            ground = new Ground(columns * 4 / 3, rows * 4 / 3);
            mainSceneRoot.getChildren().add(ground);
        }
    }

    private void createMainScene() {
        mainSceneRoot = new Group();
        displaySceneRoot = new Group();
        readFile();
        WINDOW_WIDTH = 100 * columns;
        WINDOW_HEIGHT = 100 * rows;
        mainSubscene = new SubScene(mainSceneRoot, WINDOW_WIDTH, WINDOW_HEIGHT, true, SceneAntialiasing.BALANCED);
        displaySubscene = new SubScene(displaySceneRoot, WINDOW_WIDTH, WINDOW_HEIGHT, true, SceneAntialiasing.BALANCED);
        mainSubscene.setFill(Color.SKYBLUE);
        PointLight pointLight = new PointLight();
        pointLight.setTranslateZ(-2000);
        scoreText = new Text(WINDOW_WIDTH * 2 / 3, 25, "Score: 0");
        scoreText.setStrokeWidth(5);
        scoreText.setScaleX(3);
        scoreText.setScaleY(3);
        scoreText.setScaleZ(3);

        timeText = new Text(WINDOW_WIDTH / 3, 25, "Left: 30");
        timeText.setStrokeWidth(5);
        timeText.setScaleX(3);
        timeText.setScaleY(3);
        timeText.setScaleZ(3);

        gameOverText = new Text(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2, "Game Over!");

        gameOverText.setStrokeWidth(5);
        gameOverText.setScaleX(3);
        gameOverText.setScaleY(3);
        gameOverText.setScaleZ(3);
        gameOverText.setVisible(false);
        scoreText.setFill(Color.RED);
        gameOverText.setFill(Color.BLUEVIOLET);
        mainSceneRoot.getChildren().addAll(pointLight);
        displaySceneRoot.getChildren().add(gameOverText);
        displaySceneRoot.getChildren().add(scoreText);
        displaySceneRoot.getChildren().add(timeText);

        instantiateCameras();
        mainSubscene.setCamera(camera);

    }

    @Override
    public void start(Stage primaryStage) {
        root = new Group();
        createMainScene();
        root.getChildren().addAll(mainSubscene, displaySubscene);

        scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, true);
        scene.setOnKeyPressed(e -> onKeyPressed(e));
        scene.setOnKeyReleased(e -> onKeyReleased(e));

        primaryStage.setTitle("Lavirint: lab 3");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
        timer.start();
    }

    private void onKeyPressed(KeyEvent e) {
        Protagonist.Direction desiredDirection;
        switch (e.getCode()) {
            case UP:
                stopping = false;
                desiredDirection = Protagonist.Direction.UP;
                if (protagonist.getDirection() != desiredDirection) {
                    protagonist.setDesiredDirection(desiredDirection);
                    protagonist.setState(new TurnState(protagonist));
                } else {
                    if (protagonist.getState() instanceof StandState) {
                        protagonist.setState(new WalkState(protagonist));
                    }
                }
                break;
            case DOWN:
                stopping = false;
                desiredDirection = Protagonist.Direction.DOWN;
                if (protagonist.getDirection() != desiredDirection) {
                    protagonist.setDesiredDirection(desiredDirection);
                    protagonist.setState(new TurnState(protagonist));
                } else {
                    if (protagonist.getState() instanceof StandState) {
                        protagonist.setState(new WalkState(protagonist));
                    }
                }
                break;
            case LEFT:
                stopping = false;
                desiredDirection = Protagonist.Direction.LEFT;
                if (protagonist.getDirection() != desiredDirection) {
                    protagonist.setDesiredDirection(desiredDirection);
                    protagonist.setState(new TurnState(protagonist));
                } else {
                    if (protagonist.getState() instanceof StandState) {
                        protagonist.setState(new WalkState(protagonist));
                    }
                }
                break;
            case RIGHT:
                stopping = false;
                desiredDirection = Protagonist.Direction.RIGHT;
                if (protagonist.getDirection() != desiredDirection) {
                    protagonist.setDesiredDirection(desiredDirection);
                    protagonist.setState(new TurnState(protagonist));
                } else {
                    if (protagonist.getState() instanceof StandState) {
                        protagonist.setState(new WalkState(protagonist));
                    }
                }
                break;
            case W:
                w_pressed = true;
                break;
            case A:
                a_pressed = true;
                break;
            case S:
                s_pressed = true;
                break;
            case D:
                d_pressed = true;
                break;
            case L:
                if (flag != null) {
                    flag.SwitchIsLightOn();
                }
                break;

            case DIGIT1:
                followPlayer = !followPlayer;
                fps = false;
                defaultCamera.getTransforms().clear();
                instantiateCameras();
                break;
            case DIGIT3:
                fps = true;
                followPlayer = false;
                break;

            case SPACE:
                Projectile projectile = new Projectile(protagonist.getPosition(), protagonist);
                mainSceneRoot.getChildren().add(projectile);
                projectiles.add(projectile);
                break;

        }
    }

    private void onKeyReleased(KeyEvent e) {
        switch (e.getCode()) {
            case UP:
                stopping = true;
                break;
            case DOWN:
                stopping = true;
                break;
            case LEFT:
                stopping = true;
                break;
            case RIGHT:
                stopping = true;
                break;
            case W:
                w_pressed = false;
                break;
            case A:
                a_pressed = false;
                break;
            case S:
                s_pressed = false;
                break;
            case D:
                d_pressed = false;
                break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static int getWINDOW_HEIGHT() {
        return WINDOW_HEIGHT;
    }

    public static void setWINDOW_HEIGHT(int WINDOW_HEIGHT) {
        Lavirint.WINDOW_HEIGHT = WINDOW_HEIGHT;
    }

    public static int getWINDOW_WIDTH() {
        return WINDOW_WIDTH;
    }

    public static void setWINDOW_WIDTH(int WINDOW_WIDTH) {
        Lavirint.WINDOW_WIDTH = WINDOW_WIDTH;
    }

    public static Ground getGround() {
        return ground;
    }

    public static void setGround(Ground ground) {
        Lavirint.ground = ground;
    }

}
