/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import game.Lavirint;
import geometry.Vector;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

/**
 *
 * @author Mina
 */
public class Circle extends Protagonist {

    private static final int RADIUS = 80;
    private static final int EYE_RADIUS = 10;
    PhongMaterial body_mat = new PhongMaterial(Color.YELLOW);
    PhongMaterial eyes_mat = new PhongMaterial(Color.BLACK);

    public Circle(Vector position, double rotation, Lavirint main) {
        super(position, rotation, main);

        Sphere body = new Sphere(RADIUS);
        body.setMaterial(body_mat);

        Sphere left_eye = new Sphere(EYE_RADIUS);
        left_eye.setTranslateY(-(RADIUS) / 2 * Math.sqrt(2.5));
        left_eye.setTranslateX(-(RADIUS) / 2 * Math.sqrt(0.7));
        left_eye.setTranslateZ(-(RADIUS) / 2 * Math.sqrt(2));
        left_eye.setMaterial(eyes_mat);

        Sphere right_eye = new Sphere(EYE_RADIUS);
        right_eye.setTranslateY(-(RADIUS) / 2 * Math.sqrt(2.5));
        right_eye.setTranslateX((RADIUS) / 2 * Math.sqrt(0.7));
        right_eye.setTranslateZ(-(RADIUS) / 2 * Math.sqrt(2));
        right_eye.setMaterial(eyes_mat);
        
        this.getChildren().addAll(body, left_eye, right_eye);
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
