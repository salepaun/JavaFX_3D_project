/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import geometry.Vector;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 *
 * @author Mina
 */
public class Sat extends MazeObject{
    public static final int WIDTH = 200;
    public static final int HEIGHT = 400;
    private static PhongMaterial bush_mat = new PhongMaterial(Color.GREEN);
    private static Image bush_image = new Image("resources/bush.jpeg");
    static{
    //    bush_mat.setBumpMap(bush_image);
        bush_mat.setDiffuseMap(bush_image);
    }
    
    public Sat(Vector position) {
        super(position, 0);
        
        Group c = new Group();
        Cylinder base = new Cylinder(HEIGHT/5, WIDTH/8);
        Rotate r = new Rotate(90, Rotate.X_AXIS);
        Rotate r1 = new Rotate(45, Rotate.Z_AXIS);
        Cylinder sipka1 = new Cylinder(4, HEIGHT*2/3 );
        sipka1.getTransforms().addAll(r,r1);
         Rotate r2 = new Rotate(-45, Rotate.Z_AXIS);
        Cylinder sipka2 = new Cylinder(4, HEIGHT*2/3 );
        sipka2.getTransforms().addAll(r,r2);
        
       
        
        PhongMaterial material = new PhongMaterial(Color.RED);
        base.setMaterial(material);
        
        
        c.getChildren().addAll(base,sipka1,sipka2);
        Box collider = new Box(WIDTH, WIDTH, HEIGHT);
        collider.setVisible(false);
        
        this.getChildren().addAll(c);
        RotateTransition rt = new RotateTransition(Duration.seconds(3), c);
        rt.setAxis(Rotate.Z_AXIS);
        rt.setToAngle(360);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.setCycleCount(Animation.INDEFINITE);
        //rt.play();
        this.setTranslateX(position.getX());
        this.setTranslateY(position.getY());
        this.setTranslateZ(position.getZ() - HEIGHT/2);
    }
  
}
