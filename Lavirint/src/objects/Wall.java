/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import geometry.Vector;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

/**
 *
 * @author Mina
 */
public class Wall extends MazeObject{
    public static final int WIDTH = 200;
    public static final int HEIGHT = 400;
    private static PhongMaterial bush_mat = new PhongMaterial(Color.GREEN);
    private static Image bush_image = new Image("resources/bush.jpeg");
    static{
    //    bush_mat.setBumpMap(bush_image);
        bush_mat.setDiffuseMap(bush_image);
    }
    
    public Wall(Vector position) {
        super(position, 0);
        Box bush = new Box(WIDTH,WIDTH,HEIGHT);
        bush.setMaterial(bush_mat);
        this.getChildren().add(bush);
        this.setTranslateX(position.getX());
        this.setTranslateY(position.getY());
        this.setTranslateZ(position.getZ() - HEIGHT/2);
    }
}
