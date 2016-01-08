package br.usp.icmc.vicg.gl.core;

import br.usp.icmc.vicg.gl.matrix.Matrix4;
import br.usp.icmc.vicg.gl.util.BoundingBox;
import br.usp.icmc.vicg.gl.util.FrustumCulling;
import br.usp.icmc.vicg.gl.util.Shader;
import javax.media.opengl.GL3;

public class Camera
{
    private static final float FOV = 60f;
    private static final float RATIO = (1920.0f / 1080.0f);
    private static final float DNEAR = 0.1f;
    private static final float DFAR = 10f;
    
    private final FrustumCulling frustum;
    private final Matrix4 projection;
    private final Matrix4 camera;
    
    private final FlashLight flashlight;
    
    private final float[] camPosition;
    private final float[] camViewUp;
    private final float[] camLookAt;
    
    public Camera()
    {
        this.frustum = new FrustumCulling();
        this.projection = new Matrix4();
        this.camera = new Matrix4();
        
        this.flashlight = new FlashLight();
        
        this.camPosition = new float[3];
        this.camViewUp = new float[3];
        this.camLookAt = new float[3];
    }
    
    public float[] getCamPosition(){ return this.camPosition; }
    
    public void initCamera( GL3 gl, Shader shader )
    {
        this.projection.init(gl, shader.getUniformLocation("u_projectionMatrix"));
        this.projection.loadIdentity();
        
        this.camera.init( gl, shader.getUniformLocation("u_viewMatrix") );
        this.camera.loadIdentity();
        
        this.flashlight.init( gl, shader );
    }
    
    public void setCamera()
    {
        this.projection.loadIdentity();
        this.projection.perspective(FOV, RATIO, DNEAR, DFAR);
        this.projection.bind();
        
        this.camera.loadIdentity();
        this.camera.lookAt(
                this.camPosition[0], this.camPosition[1], this.camPosition[2],//posicao
                this.camLookAt[0], this.camLookAt[1], this.camLookAt[2],//lookAt
                this.camViewUp[0], this.camViewUp[1], this.camViewUp[2]);//Vup
        this.camera.bind();
        
        this.flashlight.setLightBounds( this.camPosition, this.camLookAt );
        this.flashlight.bind();
    }
    
    public void setCamValues( float moveX, float moveY, float moveZ, float teta )
    {
        this.camPosition[0] = moveX;
        this.camPosition[1] = moveY;
        this.camPosition[2] = moveZ;
        
        this.camLookAt[0] = moveX+(float)Math.sin(teta/50);
        this.camLookAt[1] = moveY;
        this.camLookAt[2] = moveZ-(float)Math.cos(teta/50);
        
        this.camViewUp[0] = 0;
        this.camViewUp[1] = 1;
        this.camViewUp[2] = 0;
    }
    
    public boolean checkModels( BoundingBox box )
    {
        this.frustum.setMatrixMVP( this.camera, this.projection );
        
        return this.frustum.checkDraw( box );
    }
    
    public Matrix4 getViewMatrix(){ return this.camera; }
    public Matrix4 getProjectionMatrix(){ return this.projection; }
}
