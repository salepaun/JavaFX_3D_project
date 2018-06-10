/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import com.sun.scenario.effect.PhongLighting;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

/**
 *
 * @author Mina
 */
public class Ground extends Group{
    private static PhongMaterial ground_mat = new PhongMaterial(Color.SANDYBROWN);
    private static Image ground_image = new Image("resources/ground.jpg");
    static{
        //ground_mat.setBumpMap(ground_image);
       ground_mat.setDiffuseMap(ground_image);
    }
    public Ground(double width, double depth) {
        Box ground = new Box(width*Wall.WIDTH, depth*Wall.WIDTH,20);
        ground.setMaterial(ground_mat);
        this.getChildren().add(ground);
    }

    public static PhongMaterial getGround_mat() {
        return ground_mat;
    }

    public static void setGround_mat(PhongMaterial ground_mat) {
        Ground.ground_mat = ground_mat;
    }
    
}
