package me.hannsi.test;

import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.physic.PhysicWorld;
import me.hannsi.lfjg.physic.RigidBody;
import me.hannsi.lfjg.render.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.system.scene.IScene;
import me.hannsi.lfjg.render.system.scene.Scene;

public class TestPhysic1 implements IScene {
    private final Scene scene;
    private final Frame frame;

    private PhysicWorld physicWorld;
    private GLRect glGround;
    private GLRect glRect;

    public TestPhysic1(Frame frame) {
        this.frame = frame;
        scene = new Scene("TestPhysic1", this);
    }

    @Override
    public void init() {
//        glGround = new GLRect("Ground");
//        glGround.rectWH(0, 0, frame.getFrameBufferWidth(), 10, Color.of(255, 255, 255, 255));
//
//        glRect = new GLRect("Rect");
//        glRect.rectWH(600, 600, 100, 100, Color.of(255, 255, 255, 255));

        physicWorld = PhysicWorld.initPhysicWorld()
                .addRigidBody(
                        RigidBody.createRigidBody()
                                .setMass(1)
                                .setNoGravity(true)
                                .attachGLObject(glRect)
                )
                .addRigidBody(
                        RigidBody.createRigidBody()
                                .setMass(1)
                                .attachGLObject(glRect)
                );
    }

    @Override
    public void drawFrame() {
        glGround.draw();
        glRect.draw();
//        System.out.println(glRect.getTransform().getY() + " : " + glRect.getTransform().getY());
        physicWorld.simulate(1f / frame.getFps());
    }

    @Override
    public void stopFrame() {

    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
