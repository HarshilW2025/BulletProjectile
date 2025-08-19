package com.mygame.projectiles;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import java.util.ArrayList;
import java.util.Iterator;
/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class ProjectileAnimation extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private ShapeRenderer shapeRenderer;
    final int TILE_SIZE = 64;
    final int BULLET_WIDTH = TILE_SIZE / 6;
    final int BULLET_HEIGHT = TILE_SIZE / 3;
    final float BULLET_SPEED = 400; // pixels/sec
    final int ROWS = 9;               // vertical tiles
    final int COLUMNS = 16;           // horizontal tiles
    final int FRAME_WIDTH = TILE_SIZE * COLUMNS;   // 1024 pixels
    final int FRAME_HEIGHT = TILE_SIZE * ROWS;
    ArrayList<Bullet> bullets = new ArrayList<>();

    class Bullet {
        float x, y;
        boolean active;

        Bullet(float startX, float startY) {
            x = startX;
            y = startY;
            active = true;
        }

        void update(float delta) {
            y += BULLET_SPEED * delta;
            if (y > Gdx.graphics.getHeight()) active = false;
        }
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        // --- Update bullets ---
        Iterator<Bullet> it = bullets.iterator();
        while (it.hasNext()) {
            Bullet b = it.next();
            if (b.active) b.update(delta);
            else it.remove();
        }

        // --- Shoot new bullets ---
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
            bullets.add(new Bullet(140 + image.getWidth() / 2f - BULLET_WIDTH / 2f,
                210 + image.getHeight()));
        }

        // --- Clear screen ---
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        // --- Draw everything ---
        batch.begin();
        batch.draw(image, 140, 210);
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        for (Bullet b : bullets) {
            shapeRenderer.rect(b.x, b.y, BULLET_WIDTH, BULLET_HEIGHT);
        }
        shapeRenderer.end();
    }


    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
