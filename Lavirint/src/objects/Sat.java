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
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

/**
 *
 * @author Mina
 */
public class Sat extends MazeObject {

    public static final int WIDTH = 200;
    public static final int HEIGHT = 400;
    private static PhongMaterial bush_mat = new PhongMaterial(Color.GREEN);
    private static Image bush_image = new Image("resources/bush.jpeg");

    static {
        //    bush_mat.setBumpMap(bush_image);
        bush_mat.setDiffuseMap(bush_image);
    }

    public Sat(Vector position) {
        super(position, 0);

        Cylinder outer = new Cylinder(50.0D, 15.0D);
        Cylinder inner = new Cylinder(45.0D, 16.5D);
        Cylinder c1 = new Cylinder(25.0D, 12.0D);
        Cylinder c2 = new Cylinder(25.0D, 12.0D);

        Cylinder handle1 = new Cylinder(3.0D, 150.0D);
        Cylinder handle2 = new Cylinder(3.0D, 150.0D);

        Group g1 = new Group(new Node[]{c1, handle1});
        Group g2 = new Group(new Node[]{c2, handle2});

        Box mins = new Box(5.0D, 5.0D, 40.0D);
        Box hours = new Box(5.0D, 5.0D, 30.0D);

        hours.setRotationAxis(Rotate.Y_AXIS);
        hours.setRotate(-45.0D);

        mins.setTranslateZ(-20.0D);
        hours.setTranslateZ(-10.0D);
        hours.setTranslateX(10.0D);

        mins.setTranslateY(12.0D);
        hours.setTranslateY(12.0D);

        outer.getTransforms().addAll(new Transform[]{new Translate(0.0D, -2.0D, 0.0D)});
        c1.getTransforms().addAll(new Transform[]{new Rotate(90.0D, Rotate.X_AXIS), new Translate(-50.0D, -50.0D, 0.0D), new Rotate(-45.0D, Rotate.Z_AXIS)});
        c2.getTransforms().addAll(new Transform[]{new Rotate(90.0D, Rotate.X_AXIS), new Translate(50.0D, -50.0D, 0.0D), new Rotate(45.0D, Rotate.Z_AXIS)});
        handle1.getTransforms().addAll(new Transform[]{new Rotate(90.0D, Rotate.X_AXIS), new Translate(-10.0D, -10.0D, 0.0D), new Rotate(-45.0D, Rotate.Z_AXIS)});
        handle2.getTransforms().addAll(new Transform[]{new Rotate(90.0D, Rotate.X_AXIS), new Translate(10.0D, -10.0D, 0.0D), new Rotate(45.0D, Rotate.Z_AXIS)});
        PhongMaterial gold = new PhongMaterial(Color.GOLD);
        PhongMaterial crimson = new PhongMaterial(Color.CRIMSON);
        PhongMaterial black = new PhongMaterial(Color.BLACK);
        PhongMaterial white = new PhongMaterial(Color.ANTIQUEWHITE);

        outer.setMaterial(crimson);
        inner.setMaterial(white);
        mins.setMaterial(black);
        hours.setMaterial(black);
        handle1.setMaterial(gold);
        handle2.setMaterial(gold);

        c1.setMaterial(crimson);
        c2.setMaterial(crimson);

        getChildren().addAll(new Node[]{outer, inner, mins, hours, g1, g2});

        RotateTransition rt = new RotateTransition(Duration.seconds(3), this);
        rt.setAxis(Rotate.Z_AXIS);
        rt.setToAngle(360);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.setCycleCount(Animation.INDEFINITE);
        //rt.play();
        this.setTranslateX(position.getX());
        this.setTranslateY(position.getY());
        this.setTranslateZ(position.getZ() - HEIGHT / 2);
    }

}
