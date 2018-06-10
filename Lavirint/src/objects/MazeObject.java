/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import geometry.Vector;
import javafx.scene.Group;

/**
 *
 * @author Mina
 */
public abstract class MazeObject extends Group{
    protected Vector position;
    protected double rotation;

    public MazeObject(Vector position, double rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public MazeObject() {
        position = new Vector(0, 0, 0);
        rotation = 0;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }
    
}
