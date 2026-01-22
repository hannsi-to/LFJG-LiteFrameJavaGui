package me.hannsi.test;

import me.hannsi.lfjg.core.Core;
import me.hannsi.lfjg.core.utils.graphics.color.Color;
import me.hannsi.lfjg.core.utils.reflection.location.Location;
import me.hannsi.lfjg.core.utils.type.types.LocationType;
import me.hannsi.lfjg.frame.Frame;
import me.hannsi.lfjg.frame.setting.settings.*;
import me.hannsi.lfjg.frame.system.LFJGFrame;
import me.hannsi.lfjg.render.manager.AssetManager;
import me.hannsi.lfjg.render.renderers.InstanceParameter;
import me.hannsi.lfjg.render.renderers.polygon.GLRect;
import me.hannsi.lfjg.render.system.mesh.ObjectData;

import static me.hannsi.lfjg.core.Core.frameBufferSize;
import static me.hannsi.lfjg.frame.LFJGFrameContext.frame;
import static me.hannsi.lfjg.render.LFJGRenderContext.*;

public class TestNewGLObject implements LFJGFrame {
    public static void main(String[] args) {
        new TestNewGLObject().setFrame();
    }

    @Override
    public void init() {
        Core.init(frame.getFrameBufferWidth(), frame.getFrameBufferHeight(), frame.getWindowWidth(), frame.getWindowHeight());

        atlasPacker.addSprite("Texture1", AssetManager.getTextureAsset(new Location("texture/test/test1.jpg", LocationType.RESOURCE)))
                .generate();
        sparseTexture2DArray.commitTexture("Texture1", true)
                .updateFromAtlas();

        InstanceParameter[] instanceParameters = new InstanceParameter[]{
                InstanceParameter.createBuilder().spriteIndex(sparseTexture2DArray.getSpriteIndexFromName("Texture1"))
        };
        GLRect.createGLRect("GLRect1")
                .x1_y1_color1_2p(0, 0, Color.RED)
                .width3_height3_color3_2p(frameBufferSize.x, frameBufferSize.y, Color.GREEN)
                .fill()
                .rectUV(0, 1, 1, 0)
                .objectData(new ObjectData(
                        1,
                        instanceParameters
                ))
                .update();
    }

    @Override
    public void drawFrame() {
        update();
    }

    @Override
    public void stopFrame() {
        finish();
    }

    @Override
    public void setFrameSetting() {
        frame.setFrameSettingValue(RefreshRateSetting.class, -1);
        frame.setFrameSettingValue(VSyncSetting.class, VSyncType.V_SYNC_OFF);
        frame.setFrameSettingValue(CheckSeveritiesSetting.class, new SeverityType[]{SeverityType.LOW, SeverityType.MEDIUM, SeverityType.HIGH});
    }

    public void setFrame() {
        frame = new Frame(this, "TestNewMeshSystem");
    }
}
