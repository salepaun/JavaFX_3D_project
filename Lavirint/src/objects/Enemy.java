/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import game.Lavirint;
import geometry.Vector;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
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
    private Group right_leg_group = new Group();
    private Group left_leg_group = new Group();
    private Group body_group = new Group();
    private Group head_group = new Group();
    private Group right_arm_group = new Group();
    private Group left_arm_group = new Group();

    public Enemy(Vector position, double rotation, Lavirint main) {
        super(position, rotation, main);

        Box head = new Box(100.0D, 66.66666666666667D, 60.0D);
        Cylinder left_eye = new Cylinder(10.0D, 10.0D);
        Cylinder right_eye = new Cylinder(10.0D, 10.0D);
        Box left_arm = new Box(27.500000000000004D, 27.500000000000004D, 90.0D);
        Box right_arm = new Box(27.500000000000004D, 27.500000000000004D, 90.0D);
        Box mouth = new Box(20.0D, 5.0D, 5.0D);
        Box body = new Box(110.00000000000001D, 73.33333333333334D, 90.0D);
        Box left_leg = new Box(50.0D, 50.0D, 90.0D);
        Box right_leg = new Box(50.0D, 50.0D, 90.0D);
        Cylinder head_wire = new Cylinder(3.0D, 30.0D);
        Sphere head_ball = new Sphere(10.0D);

        PhongMaterial eyes = new PhongMaterial(Color.BLACK);
        PhongMaterial metal_dark = new PhongMaterial(Color.GRAY);
        PhongMaterial metal = new PhongMaterial(Color.DARKGRAY);
        PhongMaterial light_yellow = new PhongMaterial(Color.LIGHTYELLOW);

        head.setMaterial(metal_dark);
        left_eye.setMaterial(eyes);
        right_eye.setMaterial(eyes);
        left_arm.setMaterial(metal_dark);
        right_arm.setMaterial(metal_dark);
        left_leg.setMaterial(metal_dark);
        right_leg.setMaterial(metal_dark);
        head_wire.setMaterial(metal);
        head_ball.setMaterial(light_yellow);

        body.setMaterial(metal);
        mouth.setMaterial(light_yellow);

        head.getTransforms().add(new Translate(0, 0.0D, -210));
        left_eye.getTransforms().add(new Translate(-16 - 31, -210.));
        right_eye.getTransforms().add(new Translate(16, -31, -210));
        mouth.getTransforms().add(new Translate(0, -31, -195));
        body.getTransforms().add(new Translate(0, 0.0D, -135));
        left_arm.getTransforms().add(new Translate(-68, 0.0D, -135));
        right_arm.getTransforms().add(new Translate(68, 0.0D, -135));
        left_leg.getTransforms().add(new Translate(-27, 0.0D, -45));
        right_leg.getTransforms().add(new Translate(27, 0.0D, -45));
        head_wire.getTransforms().addAll(new Transform[]{new Translate(0.0D, 0.0D, -255.0D), new Rotate(90.0D, Rotate.X_AXIS)});
        head_ball.getTransforms().add(new Translate(0.0D, 0.0D, -270.0D));
        head_group.getChildren().addAll(new Node[]{head, left_eye, right_eye, mouth});
        left_arm_group.getChildren().addAll(new Node[]{left_arm});
        right_arm_group.getChildren().addAll(new Node[]{right_arm});
        left_leg_group.getChildren().addAll(new Node[]{left_leg});
        right_leg_group.getChildren().addAll(new Node[]{right_leg});
        body_group.getChildren().addAll(new Node[]{body});

        getChildren().addAll(new Node[]{head_group, left_arm_group, right_arm_group, body_group, left_leg_group, right_leg_group, head_ball, head_wire});
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

        this.setTranslateX(this.getTranslateX() + position.getX());
        this.setTranslateY(this.getTranslateY() + position.getY());
        this.setTranslateZ(this.getTranslateZ() + position.getZ() - RADIUS / 2);
        this.setRotationAxis(Rotate.Y_AXIS);
        this.setRotate(rotation);
    }

    @Override
    public void update(float passed, int acceleration) {
        //state.update(passed, acceleration);
    }

}
