package me.hannsi.test;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.type.types.LocationType;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.settings.*;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.render.renderers.InstanceParameter;
import me.hannsi.lfjg.render.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.renderers.video.GLVideo;
import me.hannsi.lfjg.render.system.mesh.Vertex;
import me.hannsi.lfjg.render.system.rendering.texture.atlas.Sprite;
import me.hannsi.lfjg.render.system.rendering.texture.atlas.SpriteMemoryPolicy;
import me.hannsi.lfjg.render.system.video.VideoDecoder;

import static me.hannsi.lfjg.core.Core.ASSET_MANAGER;
import static me.hannsi.lfjg.frame.LFJGFrameContext.frame;
import static me.hannsi.lfjg.render.LFJGRenderContext.sparseTexture2DArray;
import static me.hannsi.lfjg.render.LFJGRenderContext.update;

public class TestNewGLVideo implements LFJGFrame {
    public static void main(String[] args) {
        new TestNewGLVideo().setFrame();
    }

    @Override
    public void init() {
        Core.init(frame.getFrameBufferWidth(), frame.getFrameBufferHeight(), frame.getWindowWidth(), frame.getWindowHeight());

        ASSET_MANAGER.registerAsset("Texture1", new Location("texture/test/test1.jpg", LocationType.RESOURCE), Sprite.class);
        sparseTexture2DArray.addSprite("Texture1");
        sparseTexture2DArray.addSprite("RandomTexture", Sprite.createRandomColor(frame.getWindowWidth(), frame.getWindowHeight(), false, SpriteMemoryPolicy.STREAMING));

        sparseTexture2DArray.commitTexture("Texture1", true)
                .commitTexture("RandomTexture", true)
                .updateFromAtlas();

        ASSET_MANAGER.registerAsset("Video1", Location.fromResource("video/[FMV] world.execute(me); - MILI [cn4M-fH08XY].webm"), VideoDecoder.class);

        GLVideo.createGLVideo("GLVideo")
                .from(new Vertex(0, 0, 0, Color.of(1f, 1f, 1f, 1f), 0, 0))
                .end(new Vertex(frame.getFrameBufferWidth(), frame.getFrameBufferHeight(), 0, Color.of(1f, 1f, 1f, 1f), 1, 1))
                .assetVideoName("Video1");
        for (InstanceParameter instanceParameter : GLRect.createGLRect("GLRectTexture")
                .from(new Vertex(0, 0, 0, Color.of(1f, 1f, 1f, 1f), 0, 0))
                .end(new Vertex(100, 100, 0, Color.of(1f, 1f, 1f, 1f), 1, 1))
                .fill()
                .getObjectData()
                .getInstanceParameters()) {
            instanceParameter.spriteName("Texture1");
        }
    }

    @Override
    public void drawFrame() {
        update();

//        ByteBuffer buffer = BufferUtils.createByteBuffer(frame.getFrameBufferWidth() * frame.getFrameBufferHeight() * 4);
//
//        byte r;
//        byte g;
//        byte b;
//        byte a = (byte) 255;
//
//        r = (byte) (random() * 255);
//        g = (byte) (random() * 255);
//        b = (byte) (random() * 255);
//
//        for (int i = 0; i < frame.getFrameBufferWidth() * frame.getFrameBufferHeight(); i++) {
//            buffer.put(r).put(g).put(b).put(a);
//        }
//        buffer.flip();
//        sparseTexture2DArray.updateSprite("RandomTexture", buffer);
    }

    @Override
    public void stopFrame() {

    }

    @Override
    public void setFrameSetting() {
        frame.setFrameSettingValue(RefreshRateSetting.class, -1);
        frame.setFrameSettingValue(VSyncSetting.class, VSyncType.V_SYNC_OFF);
        frame.setFrameSettingValue(CheckSeveritiesSetting.class, new SeverityType[]{SeverityType.LOW, SeverityType.MEDIUM, SeverityType.HIGH});
    }

    public void setFrame() {
        frame = new Frame(this, "TestNewGLVideo");
    }
}
