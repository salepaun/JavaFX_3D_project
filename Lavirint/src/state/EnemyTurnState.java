/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package state;

import javafx.geometry.Point3D;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import objects.Protagonist;

/**
 *
 * @author Mina
 */
public class EnemyTurnState extends State {

    private int to;
    private int delta;

    public EnemyTurnState(Protagonist protagonist) {
        super(protagonist);
        to = Protagonist.Direction.getDegrees(protagonist.getDesiredDirection()) % 360;
        delta = Protagonist.Direction.getRotateDirection((int) protagonist.getRotate(),
                to) * 5;
    }

    @Override
    public void update(float passed, int acceleration) {
        protagonist.setRotationAxis(Rotate.Z_AXIS);
        double rot = protagonist.getRotate();
        if ((to - (rot % 360)) % 360 != 0) {
            protagonist.setRotate((rot + delta) % 360);
        } else {
            protagonist.setDirection(protagonist.getDesiredDirection());
            protagonist.setState(new WalkState(protagonist));
        }
    }

}
