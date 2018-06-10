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
public class Coin extends MazeObject {

    public static final int WIDTH = 200;
    public static final int HEIGHT = 400;
    private static PhongMaterial bush_mat = new PhongMaterial(Color.GREEN);
    private static Image bush_image = new Image("resources/bush.jpeg");

    static {
        //    bush_mat.setBumpMap(bush_image);
        bush_mat.setDiffuseMap(bush_image);
    }

    public Coin(Vector position) {
        super(position, 0);

        Group c = new Group();
        Cylinder base = new Cylinder(HEIGHT / 5, WIDTH / 10);

        Cylinder base1 = new Cylinder(HEIGHT / 5, WIDTH / 10);
        Cylinder base2 = new Cylinder(HEIGHT / 5, WIDTH / 10);

        Box b1 = new Box(60, WIDTH / 8, 60);
        Box b2 = new Box(60, WIDTH / 8, 60);
        Rotate r = new Rotate(45, Rotate.Y_AXIS);
        b2.getTransforms().add(r);
        PhongMaterial material = new PhongMaterial(Color.YELLOW);
        b1.setMaterial(material);
        b2.setMaterial(material);
        base.setMaterial(material);
        c.getChildren().addAll(base, b1, b2);
        Box collider = new Box(WIDTH, WIDTH, HEIGHT);
        collider.setVisible(false);

        this.getChildren().addAll(c);
        RotateTransition rt = new RotateTransition(Duration.seconds(3), c);
        rt.setAxis(Rotate.Z_AXIS);
        rt.setToAngle(360);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.play();
        this.setTranslateX(position.getX());
        this.setTranslateY(position.getY());
        this.setTranslateZ(position.getZ() - HEIGHT / 2);
    }

}
