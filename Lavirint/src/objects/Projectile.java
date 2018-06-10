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
import state.WalkState;

/**
 *
 * @author Mina
 */
public class Projectile extends Protagonist {

    private static final int RADIUS = 80;
    private static final int EYE_RADIUS = 10;
    PhongMaterial body_mat = new PhongMaterial(Color.CHARTREUSE);
    PhongMaterial eyes_mat = new PhongMaterial(Color.BLACK);
    
    
    Direction dir;

    public Projectile(Vector position,  Protagonist main) {
        super(position, 0, null);

        Sphere body = new Sphere(RADIUS/5);
        body.setMaterial(body_mat);
        this.getChildren().addAll(body);
        
        dir = main.direction;
       
        state = new WalkState(this); 
        setDirection(dir);
        currentSpeed =60;
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
