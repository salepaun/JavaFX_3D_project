/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package state;

import game.Lavirint;
import objects.Protagonist;

/**
 *
 * @author Mina
 */
public class WalkState extends State {

    private static int lastID = 0;
    private int id = ++lastID;
    private double totalTime = 0;

    public WalkState(Protagonist p) {
        super(p);
    }
    boolean slow = false;

    @Override
    public void update(float passed, int acceleration) {
        double currentSpeed = protagonist.getCurrentSpeed();
        double maxSpeed = protagonist.getMaxSpeed();
        currentSpeed += acceleration;
        currentSpeed = (currentSpeed > maxSpeed) ? maxSpeed : currentSpeed;
        currentSpeed = (currentSpeed < 0) ? 0 : currentSpeed;
        protagonist.setCurrentSpeed(currentSpeed);

        double move = currentSpeed * passed;

        switch (protagonist.getDirection()) {
            case UP:
                //protagonist.getTransforms().add(new Translate(0, -move, 0));
                if (protagonist.getTranslateY() > Lavirint.getGround().getBoundsInParent().getMinY()) {
                    protagonist.setTranslateY(protagonist.getTranslateY() - move);
                }
                break;
            case LEFT:
                if (protagonist.getTranslateX() > Lavirint.getGround().getBoundsInParent().getMinX()) {
                    protagonist.setTranslateX(protagonist.getTranslateX() - move);
                }
                break;
            case DOWN:
                if (protagonist.getTranslateY() < Lavirint.getGround().getBoundsInParent().getMaxY()) {
                    protagonist.setTranslateY(protagonist.getTranslateY() + move);
                }
                break;

            case RIGHT:
                if (protagonist.getTranslateX() < Lavirint.getGround().getBoundsInParent().getMaxX()) {
                    protagonist.setTranslateX(protagonist.getTranslateX() + move);
                }
                break;

        }
        if (currentSpeed == 0) {
            protagonist.setState(new StandState(protagonist));
        }
    }

}
