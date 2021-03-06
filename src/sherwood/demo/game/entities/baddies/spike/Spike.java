package sherwood.demo.game.entities.baddies.spike;

import sherwood.demo.game.entities.baddies.Baddie;
import sherwood.demo.game.graphics.SpriteBox;
import sherwood.demo.game.physics.BoundingBox;
import sherwood.demo.game.physics.Direction;
import sherwood.demo.game.physics.Vector;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Spike extends Baddie {

    private final Direction face;

    public Spike(BoundingBox bounds, Direction face) {
        super(bounds.resize(bounds.width(), bounds.height() - 1));
        this.face = face;
    }

    public Polygon poly() {
        int[] x, y;
        if (face.vertical()) {
            x = new int[]{bounds().position().xc(), bounds().position().xc() + bounds().size().xc() / 2, bounds().position().xc() + bounds().size().xc()};
            y = new int[]{bounds().position().yc() + bounds().size().yc(), bounds().position().yc(), bounds().position().yc() + bounds().size().yc()};
            if (face == Direction.DOWN) {
                for (int i = 0; i < 3; i++)
                    y[i] += (i == 1 ? 1 : -1) * bounds().size().yc();
            }
            return new Polygon(x, y, 3);
        }
        x = new int[]{bounds().position().xc(), bounds().position().xc() + bounds().size().xc(), bounds().position().xc()};
        y = new int[]{bounds().position().yc(), bounds().position().yc() + bounds().size().yc() / 2, bounds().position().yc() + bounds().size().yc()};
        if (face == Direction.LEFT) {
            for (int i = 0; i < 3; i++)
                x[i] += (i == 1 ? -1 : 1) * bounds().size().xc();
        }
        return new Polygon(x, y, 3);
    }

    @Override
    public void draw(Graphics2D g, Vector position) {
        BufferedImage sprite = SpriteBox.instance().sprite("res/img/spikes/CloudSpike" + face + ".png");
        g.drawImage(sprite, position.xc(), position.yc(), (int) bounds().width(), (int) bounds().height(), null);
    }
}
