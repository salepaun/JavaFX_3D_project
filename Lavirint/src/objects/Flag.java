/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import geometry.Vector;
import javafx.scene.PointLight;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Translate;

/**
 *
 * @author Mina
 */
public class Flag extends MazeObject {

    public static final double WIDTH = 200;
    public static final double HEIGHT = 200;

    public static final double NUM_HOR = 6;
    public static final double NUM_VERT = 4;

    private static PhongMaterial[] color;
    private int current = 0;

    public PointLight pointLight = new PointLight(Color.GREEN);

    public Flag(Vector position) {
        super(position, 0);
        color = new PhongMaterial[2];
        color[0] = new PhongMaterial(Color.BLACK);
        color[1] = new PhongMaterial(Color.WHITE);

        double width = WIDTH / NUM_HOR;
        double height = WIDTH / NUM_VERT;

        for (int i = 0; i < NUM_VERT; i++) {
            current = (current + 1) % 2;
            for (int j = 0; j < NUM_HOR; j++) {
                Box box = new Box(width, 20, height);
                box.setMaterial(color[current]);
                current = (current + 1) % 2;
                box.setTranslateX(j * width);
                box.setTranslateZ(i * height);
                getChildren().add(box);
            }
        }
        /* Box pass_block = new Box(WIDTH, 1, WIDTH * 2);

        pass_block.setVisible(true);
        pass_block.setMaterial(new PhongMaterial(Color.TRANSPARENT));
        pass_block.setTranslateX(WIDTH / 2);
        pass_block.setTranslateZ(150);
        getChildren().add(pass_block);
         */
        pointLight.setLightOn(true);
        pointLight.setTranslateX(WIDTH/2);
        pointLight.setTranslateZ(-WIDTH/2);
        
        getChildren().add(pointLight);
        this.setTranslateX(position.getX() - WIDTH / 2 + 10);
        this.setTranslateY(position.getY());
        this.setTranslateZ(position.getZ() - 200);
    }
    
    public void SwitchIsLightOn()
    {
        pointLight.setLightOn(!pointLight.isLightOn());
    }
}
