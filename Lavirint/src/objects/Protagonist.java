/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import game.Lavirint;
import geometry.Vector;
import javafx.scene.transform.Rotate;
import state.*;

/**
 *
 * @author Mina
 */
public abstract class Protagonist extends MazeObject {

    private Lavirint main;
    protected double rotationSpeed = 4;
    protected double maxSpeed = 60;
    protected double currentSpeed = 0;
    protected double rotated = 0;
    protected State state = new StandState(this);
    protected double fixX = -400, fixY = -200;

    public enum Direction {
        UP,
        LEFT,
        RIGHT,
        DOWN;

        public static int getDegrees(Direction d) {
            switch (d) {
                case UP:
                    return 0;
                case RIGHT:
                    return 90;
                case DOWN:
                    return 180;
                case LEFT:
                    return 270;
            }
            return 0;
        }

        public static Direction getDirection(int rotate) {
            switch (rotate) {
                case 0:
                    return UP;
                case 90:
                    return RIGHT;
                case 180:
                    return DOWN;
                case 270:
                    return LEFT;
            }
            return UP;
        }

        public static int getRotateDirection(int from, int to) {
            int diff = (to - from);
            diff = (diff < 0) ? (diff + 360) : diff;
            if (diff == 270) {
                return -1;
            }
            return 1;
        }
    }
    protected Direction direction = Direction.UP;
    protected Direction desiredDirection = Direction.UP;

    public Protagonist(Vector position, double rotation, Lavirint main) {
        super(position, rotation);
        this.setRotationAxis(Rotate.X_AXIS);
        this.main = main;
        this.position = new Vector(position.getX(), position.getY(), position.getZ());
    }

    public abstract void update(float passed, int acceleration);

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(double rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public Direction getDesiredDirection() {
        return desiredDirection;
    }

    public void setDesiredDirection(Direction desiredDirection) {
        this.desiredDirection = desiredDirection;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Lavirint getMain() {
        return main;
    }

    public void setMain(Lavirint main) {
        this.main = main;
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(double currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public void incRotation(double rotation) {
        this.rotation += rotation;
    }

    public double getRotated() {
        return rotated;
    }

    public void setRotated(double rotated) {
        this.rotated = rotated;
    }

}
