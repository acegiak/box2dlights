package shaders;

import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public final class LightShader {
	static final public ShaderProgram createLightShader() {
		String gamma = ""; 
		if (RayHandler.getGammaCorrection())
			gamma = "sqrt";
		
		final String vertexShader = 
				"attribute vec4 vertex_positions;\n" //
				+ "attribute vec4 quad_colors;\n" //
				+ "attribute float s;\n"
				+ "uniform mat4 u_projTrans;\n" //
				+ "varying vec4 v_color;\n" //				
				+ "void main()\n" //
				+ "{\n" //
				+ "   v_color = s * quad_colors;\n" //				
				+ "   gl_Position =  u_projTrans * vertex_positions;\n" //
				+ "}\n";
		final String fragmentShader = "#ifdef GL_ES\n" //
			+ "precision lowp float;\n" //
			+ "#define MED mediump\n"
			+ "#else\n"
			+ "#define MED \n"
			+ "#endif\n" //
				+ "varying vec4 v_color;\n" //
				+ "uniform MED sampler2D u_mask;\n" //
				+ "void main()\n"//
				+ "{\n" //
//				+ "  v_color.a = v_color.a * texture2D(u_mask,vTexCoord).a;\n" //
//				+ "  if(texture2D(u_mask,vTexCoord).a < 0.5){ discard; }\n"
//				+ "  vec4 masked = mix(v_color,new vec4(1,0,0,1),mask);\n" //
//				+ "  gl_FragColor = "+gamma+"(masked);\n" //
				+ "  gl_FragColor = "+gamma+"(v_color);\n" //
				+ "}";

		ShaderProgram.pedantic = false;
		ShaderProgram lightShader = new ShaderProgram(vertexShader,
				fragmentShader);
		if (lightShader.isCompiled() == false) {
			Gdx.app.log("ERROR", lightShader.getLog());
		}

		return lightShader;
	}
}
