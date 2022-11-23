package objects.elements.linears.water.logs;

import com.badlogic.gdx.graphics.Texture;

import objects.elements.linears.water.WaterElement;
import objects.elements.linears.water.WaterStream;
import textures.TextureFactory;

public class TreeLog extends WaterElement {
	
	public TreeLog(double x, double y, WaterStream line) {
		super(x, y, line);
	}

	public Texture getTexture() {
		return TextureFactory.getInstance().getTextureLog();
	}
}
