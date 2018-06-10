/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package state;

import objects.Protagonist;

/**
 *
 * @author Mina
 */
public abstract class State {
    protected Protagonist protagonist;

    public State(Protagonist protagonist) {
        this.protagonist = protagonist;
    }
    
    public abstract void update(float passed, int acceleration);
}
