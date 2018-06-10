/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import game.Lavirint;
import geometry.Vector;
import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import state.WalkState;

/**
 *
 * @author Mina
 */
public class Enemy extends Protagonist {

    public static final int WIDTH = 200;
    public static final int HEIGHT = 400;
    private static final int RADIUS = 80;
    private static final int EYE_RADIUS = 10;
    public Direction current;
    PhongMaterial body_mat = new PhongMaterial(Color.LIGHTGRAY);
    PhongMaterial eyes_mat = new PhongMaterial(Color.GRAY);

    public Enemy(Vector position, double rotation, Lavirint main) {
        super(position, rotation, main);

        Box body = new Box(WIDTH / 2, WIDTH / 5, WIDTH / 3);
        body.setMaterial(body_mat);

        /*Box arm1 = new Box(WIDTH / 8, WIDTH / 5, WIDTH / 3);
        arm1.setTranslateX(WIDTH / 4);
        Box arm2 = new Box(WIDTH / 8, WIDTH / 5, WIDTH / 3);
        arm2.setTranslateX(-WIDTH / 4);
*/
        /*Box leg1 = new Box(WIDTH/4, WIDTH/5, WIDTH/3);
        leg1.setTranslateZ(WIDTH/3);
         */
        Random r = new Random();
        float f = r.nextFloat();
        if (f < .25) {
            current = Direction.DOWN;
        } else if (f < 0.5) {
            current = Direction.LEFT;
        } else if (f < 0.75) {
            current = Direction.RIGHT;
        } else {
            current = Direction.UP;
        }
        
        
        state = new WalkState(this);
        setDirection(current);
        this.getChildren().addAll(body);
        this.setTranslateX(this.getTranslateX() + position.getX());
        this.setTranslateY(this.getTranslateY() + position.getY());
        this.setTranslateZ(this.getTranslateZ() + position.getZ() - RADIUS / 2);
        this.setRotationAxis(Rotate.Y_AXIS);
        this.setRotate(rotation);
    }

    @Override
    public void update(float passed, int acceleration) {
        state.update(passed, acceleration);
    }

}
