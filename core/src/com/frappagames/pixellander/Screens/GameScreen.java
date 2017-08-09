package com.frappagames.pixellander.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.frappagames.pixellander.PixelLander;
import com.frappagames.pixellander.Tools.Assets;
import com.frappagames.pixellander.Tools.abstractGameScreen;

/**
 * Game class
 *
 * Created by Jérémy MOREAU on 14/08/15.
 */
public class GameScreen extends abstractGameScreen {
    private static final int ACCELERATION = 15;
    private static final int CRASH_SPEED = 10;
    private static final float CRASH_ANGLE = 0.2f;

    private Stage uiStage;

    private float stateTime;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Body ship, groundBody;

    public GameScreen(final PixelLander gameApp) {
        super(gameApp);

        stateTime = 0f;

        uiStage = new Stage(viewport);
        Gdx.input.setInputProcessor(uiStage);

        // Play Music ♫
//        if (Settings.soundEnabled) Assets.music.play();

        // Try Box2D
        // Init Box2D World

        Box2D.init();
        world = new World(new Vector2(0, -10), true);
        world.setContactListener(new ListenerClass());

        // Add debug infos
        debugRenderer = new Box2DDebugRenderer();

        // First we create a body definition
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // Set our body's starting position in the world
        bodyDef.position.set(28, 32);

        // Create our body in the world using our body definition
        ship = world.createBody(bodyDef);

        // Create a circle shape and set its radius to 6
        PolygonShape shipShape = new PolygonShape();
        shipShape.setAsBox(4, 4);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shipShape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.6f;
        fixtureDef.restitution = 0f; // Make it bounce a little bit

        // Create our fixture and attach it to the body
        Fixture fixture = ship.createFixture(fixtureDef);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        shipShape.dispose();

        // Create our body definition
        BodyDef groundBodyDef = new BodyDef();
        // Set its world position
        groundBodyDef.position.set(new Vector2(0, 2));

        // Create a body from the defintion and add it to the world
        groundBody = world.createBody(groundBodyDef);

        // Create a polygon shape
        PolygonShape groundBox = new PolygonShape();
        // Set the polygon shape as a box which is twice the size of our view port and 20 high
        // (setAsBox takes half-width and half-height as arguments)
        groundBox.setAsBox(camera.viewportWidth, 2.0f);
        // Create a fixture from our polygon shape and add it to our ground body
        groundBody.createFixture(groundBox, 0.0f);
        // Clean up after ourselves
        groundBox.dispose();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        // Exit to game menu on ESCAPE
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
//            Assets.playSound(Assets.clickSound);
//            game.setScreen(new MenuScreen(game));
            Gdx.app.exit();
        }

        TextureRegion landerRegion;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            ship.setAngularVelocity(2);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            ship.setAngularVelocity(-2);
        } else {
            ship.setAngularVelocity(0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            landerRegion = (TextureRegion) Assets.landerAnim.getKeyFrame(stateTime, true);
            Double xSpeed = Math.cos(ship.getAngle() + Math.PI / 2) * ACCELERATION;
            Double ySpeed = Math.sin(ship.getAngle()+ Math.PI / 2) * ACCELERATION;

            ship.applyLinearImpulse(new Vector2(xSpeed.floatValue(), ySpeed.floatValue()), ship.getWorldCenter(), true);
            stateTime += Gdx.graphics.getDeltaTime();
        } else {
            stateTime = 0f;
            landerRegion = Assets.lander.getRegion();
        }

        game.batch.begin();
        game.batch.draw(
                landerRegion,
                ship.getPosition().x - landerRegion.getRegionWidth() / 2,
                ship.getPosition().y - landerRegion.getRegionHeight() / 2,
                4,
                4,
                landerRegion.getRegionWidth(),
                landerRegion.getRegionHeight(),
                1,
                1,
                ((float) Math.toDegrees(ship.getAngle()))
        );
        game.batch.end();

        uiStage.act(delta);
        uiStage.draw();

        debugRenderer.render(world, camera.combined);
        world.step(1/60f, 6, 2);
    }

    public class ListenerClass implements ContactListener {
        @Override
        public void endContact(Contact contact) {

        }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {

        }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {

        }

        @Override
        public void beginContact(Contact contact) {
            if((contact.getFixtureA().getBody() == ship && contact.getFixtureB().getBody() == groundBody)
                || (contact.getFixtureA().getBody() == groundBody && contact.getFixtureB().getBody() == ship)) {

                long speed = Math.round(Math.sqrt(Math.pow(ship.getLinearVelocity().x, 2) + Math.pow(ship.getLinearVelocity().y, 2)));

                if (speed <= CRASH_SPEED && Math.abs(ship.getAngle()) <= CRASH_ANGLE) {
                    System.out.println("Poser");
                } else {
                    System.out.println("BOOOOMMM !");
                }
            }
        }
    };
}
