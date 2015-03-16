package sherwood.demo.entities.baddies;

import sherwood.demo.entities.Collider;
import sherwood.demo.entities.Entity;
import sherwood.demo.entities.Mover;
import sherwood.demo.entities.Stepper;
import sherwood.demo.entities.blocks.Block;
import sherwood.demo.physics.BoundingBox;
import sherwood.demo.physics.Vector;
import sherwood.inputs.keyboard.control.Control;

import java.util.EnumSet;

public class MovingBaddie extends Baddie implements Mover, Collider, Stepper {

    private Vector velocity;

    public MovingBaddie (BoundingBox bounds, Vector velocity) {
        super(bounds);
        this.velocity = velocity;
    }


    @Override
    public Vector velocity () {
        return velocity;
    }

    @Override
    public void collide (Entity entity) {
        if (entity instanceof Block) {
            velocity = velocity.negate();
            super.moveTo(bounds().position().over(velocity));
        }
    }

    @Override
    public void step (EnumSet<Control> keys) {
        moveTo(bounds().over(velocity).position());
    }
}