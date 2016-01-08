
package br.usp.icmc.vicg.gl.model;

import br.usp.icmc.vicg.gl.jwavefront.JWavefrontObject;
import br.usp.icmc.vicg.gl.matrix.Matrix4;
import br.usp.icmc.vicg.gl.util.BoundingBox;
import br.usp.icmc.vicg.gl.util.Shader;
import java.io.File;
import javax.media.opengl.GL3;

public class StaticModels
{
    private final String path;
    private final JWavefrontObject wfo;
    
    private final float[] position;
    private final float[] rotation;
    private final float[] scale;

    private float theta;
    
    private final BoundingBox box;
    
    public StaticModels( String path )
    {
        this.path = path;
        this.wfo = new JWavefrontObject( new File(path) );
        this.box = new BoundingBox();
        
        this.position = new float[3];
        this.rotation = new float[3];
        this.scale = new float[3];
        
        this.theta = 0;
    }
    
    public void setCollisionBounds( float[] min, float[] max )
    {
        this.box.setBounds( min, max );
    }
    
    public boolean checkCollison( float posX, float posY, float posZ )
    {
        return this.box.contains( posX, posY, posZ );
    }
    
    public void setPosition( float xP, float yP, float zP )
    {
        this.position[0] = xP;
        this.position[1] = yP;
        this.position[2] = zP;
    }
    
    public void setRotation( float theta, float xR, float yR, float zR )
    {
        this.theta = theta;
        this.rotation[0] = xR;
        this.rotation[1] = yR;
        this.rotation[2] = zR;
    }
    
    public void setScale( float xS, float yS, float zS )
    {
        this.scale[0] = xS;
        this.scale[1] = yS;
        this.scale[2] = zS;
    }
    
    public float[] getRotation(){ return this.rotation; }
    public float[] getPosition(){ return this.position; }
    public float[] getScale(){ return this.scale; }
    public float getTheta(){ return this.theta; }
    
    public JWavefrontObject getWavefront(){ return this.wfo; }
    public BoundingBox getCollisionBox(){ return this.box; }
    
    
    public void drawBox( GL3 gl, Shader shader )
    {
        this.box.drawBox(gl, shader);
    }
}
