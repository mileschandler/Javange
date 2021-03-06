package sherwood.demo.game.entities.player;

import sherwood.demo.game.entities.*;
import sherwood.demo.game.entities.baddies.Baddie;
import sherwood.demo.game.entities.baddies.spike.JumperSpike;
import sherwood.demo.game.entities.baddies.spike.MovingSpike;
import sherwood.demo.game.entities.baddies.spike.Spike;
import sherwood.demo.game.entities.blocks.Block;
import sherwood.demo.game.entities.switches.SwitchingSpike;
import sherwood.demo.game.physics.BoundingBox;
import sherwood.demo.game.physics.Direction;
import sherwood.demo.game.physics.Vector;
import sherwood.demo.game.structures.levels.Level;
import sherwood.demo.game.structures.levels.event.Event;
import sherwood.demo.perfect.boss.attacks.Fruit;
import sherwood.inputs.keyboard.control.Control;

import java.awt.Graphics2D;
import java.util.EnumSet;

public class Player implements Collider, Stepper, Mover, Drawable {

    private PlayerMovement movement;
    private PlayerSprite sprite;

    public Player(Vector start) {
        movement = new PlayerMovement(start);
        sprite = new PlayerSprite(this);
    }

    @Override
    public void step(EnumSet<Control> keys) {
        sprite.step();
        movement.step(keys);
        movement.addVelocity();
    }

    @Override
    public Vector velocity() {
        return movement.velocity();
    }

    public void velocity(Vector nv) {
        this.movement.changeVelocity(nv);
    }

    @Override
    public BoundingBox bounds() {
        return movement.bounds();
    }

    @Override
    public void collide(Entity entity) {
        if (entity instanceof Baddie) {
            if (entity instanceof Fruit)
                return;
            if (entity instanceof JumperSpike) {
                JumperSpike m = (JumperSpike) entity;
                if (m.poly().intersects(bounds().x(), bounds().y(), bounds().width(), bounds().height())) {
                    die();
                    return;
                } else return;
            }

            if (entity instanceof MovingSpike) {
                MovingSpike m = (MovingSpike) entity;
                if (m.poly().intersects(bounds().x(), bounds().y(), bounds().width(), bounds().height())) {
                    die();
                    return;
                } else return;
            }
            if (entity instanceof Spike) {
                if (entity instanceof SwitchingSpike)
                    if (!((SwitchingSpike) entity).isOn())
                        return;
                // if its a spike we need to check to see if they are really colliding
                // because spike is represented by a square bounding box however
                // it still has a triangular shape
                Spike s = (Spike) entity;
                if (!s.poly().intersects(bounds().x(), bounds().y(), bounds().width(), bounds().height()))
                    return;
            }
            // uh-oh! We're dead.
            die();
        }
        if (entity instanceof Block) {
            if (movement.bounds().over(movement.velocity().zx().negate()).intersects(entity.bounds())) {
                // would have collided with the block even if we weren't moving horizontally
                movement.horizontalCollision(entity);
            } else {
                movement.verticalCollision(entity);
            }
        }
    }

    @Override
    public void draw(Graphics2D g, Vector position) {
        sprite.draw(g, position);
    }

    private void die() {
        Level.currentLevel().activate(Event.playerDeath);
    }

    public Direction direction() {
        return movement.direction();
    }
}
