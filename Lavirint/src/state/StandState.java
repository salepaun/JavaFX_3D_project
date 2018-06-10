/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package state;

import java.util.logging.Logger;
import objects.Protagonist;

/**
 *
 * @author Mina
 */
public class StandState extends State {

    public StandState(Protagonist protagonist) {
        super(protagonist);
    }

    @Override
    public void update(float passed, int acceleration) {
        
    }

}
