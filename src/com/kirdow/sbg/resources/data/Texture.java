package com.kirdow.sbg.resources.data;

import com.kirdow.sbg.render.RenderHelper;
import com.kirdow.sbg.resources.Resource;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Texture extends BaseResource<Integer> {

    public Texture(Resource resource) {
        super(resource, 0);
    }

    @Override
    protected void loadResource() {
        RenderHelper.enableTexture();

        try {

            int texture = GL11.glGenTextures();

            RenderHelper.texture(texture);

            BufferedImage image = null;
            try {
                image = ImageIO.read(resource.openStream());
            } catch (IOException e) {
                image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
                final int[] inv = new int[]{0xFF000000, 0xFFFF00FF};
                for (int y = 0; y < 2; y++) {
                    for (int x = 0; x < 2; x++) {
                        int i = (x + y) % 2;
                        int color = inv[i];
                        image.setRGB(x, y, color);
                    }
                }
            }

            final int width = image.getWidth(), height = image.getHeight();
            ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);
            for (int y = height - 1; y >= 0; y--) {
                for (int x = 0; x < width; x++) {
                    int color = image.getRGB(x, y);

                    int r = (color >> 16) & 0xFF;
                    int g = (color >> 8) & 0xFF;
                    int b = (color) & 0xFF;
                    int a = (color >> 24) & 0xFF;

                    pixels.put((byte)r);
                    pixels.put((byte)g);
                    pixels.put((byte)b);
                    pixels.put((byte)a);
                }
            }
            pixels.rewind();

            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);

            RenderHelper.texture();

            this.setData(texture);

        } catch (Exception e) {
            System.out.println("Error while setting up texture for " + resource);
        } finally {
            RenderHelper.disableTexture();
        }

    }

    public void bind() {
        int data = getData();

        RenderHelper.texture(data);
    }
}
