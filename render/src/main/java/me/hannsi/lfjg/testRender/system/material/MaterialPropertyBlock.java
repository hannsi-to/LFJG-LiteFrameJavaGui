package me.hannsi.lfjg.testRender.system.material;

public class MaterialPropertyBlock {
//    private final Map<Integer, MaterialProperty> overrides = new HashMap<>();
//
//    public MaterialPropertyBlock setColor(int id, float r, float g, float b, float a) {
//        overrides.put(id, new MaterialProperty.ColorProp(r, g, b, a));
//        return this;
//    }
//
//    public MaterialPropertyBlock setFloat(int id, float value) {
//        overrides.put(id, new MaterialProperty.FloatProp(value));
//        return this;
//    }
//
//    public MaterialPropertyBlock setTexture(int id, int textureId) {
//        overrides.put(id, new MaterialProperty.TextureProp(textureId));
//        return this;
//    }
//
//    public void clear() {
//        overrides.clear();
//    }
//
//    public void applyOver(Material mat, ShaderProgramPool pool) {
//        mat.bind(pool); // ベースをバインド
//
//        ShaderProgram sp = pool.get(mat.getShaderKey());
//        overrides.forEach((id, prop) -> {
//            String name = ShaderPropertyID.nameOf(id);
//            switch (prop) {
//                case MaterialProperty.FloatProp p ->
//                        sp.setUniform1f(name, p.value());
//                case MaterialProperty.ColorProp p ->
//                        sp.setUniform4f(name, p.r(), p.g(), p.b(), p.a());
//                case MaterialProperty.TextureProp p ->
//                        sp.setUniform1i(name, p.textureId());
//                default -> {
//                }
//            }
//        });
//    }
}
