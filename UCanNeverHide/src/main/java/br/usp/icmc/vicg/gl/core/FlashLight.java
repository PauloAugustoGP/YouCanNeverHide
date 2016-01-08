package br.usp.icmc.vicg.gl.core;

import br.usp.icmc.vicg.gl.util.Shader;
import javax.media.opengl.GL3;

import com.jogamp.common.nio.Buffers;
import java.util.Arrays;

public class FlashLight {

    private GL3 gl;

    private float[] position;
    private float[] ambientColor;
    private float[] diffuseColor;
    private float[] specularColor;
    private float[] direction;
    private float expoent;

    private int positionHandle;
    private int ambientColorHandle;
    private int diffuseColorHandle;
    private int specularColorHandle;
    private int directionHandle;
    private int expoentHandle;

    public FlashLight()
    {
        this.position = new float[4];
        this.direction = new float[4];
    }
    
    public void setLightBounds( float[] position, float[] direction )
    {
        setLightAmbientColor(new float[]{0.9f, 0.9f, 0.9f, 1.0f});
        setLightDiffuseColor(new float[]{1.0f, 1.0f, 1.0f, 1.0f});
        setLightSpecularColor(new float[]{1.0f, 1.0f, 1.0f, 1.0f});
        
        this.position[0] = position[0];
        this.position[1] = position[1];
        this.position[2] = position[2];
        this.position[3] = 1.0f;
        
        this.direction[0] = direction[0];
        this.direction[1] = direction[1];
        this.direction[2] = direction[2];
        this.direction[3] = 1.0f;
        
        this.expoent = 2;
    }

    public final void setLightAmbientColor( float[] ambientColor )
    {
        this.ambientColor = Arrays.copyOf(ambientColor, ambientColor.length);
    }

    public final void setLightDiffuseColor( float[] diffuseColor )
    {
        this.diffuseColor = Arrays.copyOf(diffuseColor, diffuseColor.length);
    }

    public final void setLightSpecularColor( float[] specularColor )
    {
        this.specularColor = Arrays.copyOf(specularColor, specularColor.length);
    }

    public void init( GL3 gl, Shader shader )
    {
        this.gl = gl;
        this.positionHandle = shader.getUniformLocation("u_light.position");
        this.ambientColorHandle = shader.getUniformLocation("u_light.ambientColor");
        this.diffuseColorHandle = shader.getUniformLocation("u_light.diffuseColor");
        this.specularColorHandle = shader.getUniformLocation("u_light.specularColor");
        this.directionHandle = shader.getUniformLocation("u_light.spotDirection");
        this.expoentHandle = shader.getUniformLocation("u_light.spotExpoent");
    }

    public void bind()
    {
        gl.glUniform4fv(this.positionHandle, 1, Buffers.newDirectFloatBuffer(this.position));
        gl.glUniform4fv(this.ambientColorHandle, 1, Buffers.newDirectFloatBuffer(this.ambientColor));
        gl.glUniform4fv(this.diffuseColorHandle, 1, Buffers.newDirectFloatBuffer(this.diffuseColor));
        gl.glUniform4fv(this.specularColorHandle, 1, Buffers.newDirectFloatBuffer(this.specularColor));
        gl.glUniform4fv(this.directionHandle, 1, Buffers.newDirectFloatBuffer(this.direction));
        gl.glUniform1f(this.expoentHandle, this.expoent);
    }
}
