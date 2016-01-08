package br.usp.icmc.vicg.gl.model;

import br.usp.icmc.vicg.gl.core.Camera;
import br.usp.icmc.vicg.gl.matrix.Matrix4;
import br.usp.icmc.vicg.gl.util.ModelReader;
import br.usp.icmc.vicg.gl.util.Shader;
import java.io.IOException;
import java.util.ArrayList;
import javax.media.opengl.GL3;

public class Scene
{
    private ArrayList<StaticModels> staticModels;
    private final Matrix4 model;
    private final Matrix4 normal;
    private final ModelReader reader;
    
    private final Camera camera;
    
    public Scene()
    {
        this.staticModels = new ArrayList<>();
        this.camera = new Camera();
        this.model = new Matrix4();
        this.normal = new Matrix4();
        this.reader = new ModelReader();
        loadModels();
    }
    
    private void loadModels()
    {
        this.staticModels = this.reader.readFile();
    }
    
    public void init(GL3 gl, Shader shader) throws IOException
    {
        this.model.init(gl, shader.getUniformLocation("u_modelMatrix"));
        this.normal.init(gl, shader.getUniformLocation("u_normalMatrix"));
        this.camera.initCamera(gl, shader);
        
        for( StaticModels sModel : this.staticModels )
            sModel.getWavefront().init(gl, shader);
    }
    
    public void unitize(){
        for( StaticModels sModel : this.staticModels )
            sModel.getWavefront().unitize();
    }
    
    public void dump(){
        for( StaticModels sModel : this.staticModels )
            sModel.getWavefront().dump();
    }
    
    public void dispose(){
        for( StaticModels sModel : this.staticModels )
            sModel.getWavefront().dispose();
    }
    
    public boolean checkCollision( float posX, float posY, float posZ )
    {
        for( StaticModels sModel : this.staticModels )
        {
            if( sModel.checkCollison(posX, posY, posZ) )
            {
                return true;
            }
        }
        return false;
    }
    
    public void draw()
    {
        this.camera.setCamera();
        
//        System.out.println("\nMODEL STATUS: ");
        for( StaticModels sModel : this.staticModels )
        {
            float[] T = sModel.getPosition();
            float[] R = sModel.getRotation();
            float[] S = sModel.getScale();
            float angle = sModel.getTheta();
            
            this.model.loadIdentity();
            this.model.translate( T[0], T[1], T[2]);
            this.model.rotate( angle, R[0], R[1], R[2] );
            this.model.scale( S[0], S[1], S[2] );
            this.model.bind();
            
            this.normal.loadIdentity();
            this.normal.rotate(angle, R[0], R[1], R[2]);
            this.normal.bind();
            
//            if( this.camera.checkModels(sModel.getCollisionBox()) )
//            {
//                System.out.println("true");
                sModel.getWavefront().draw();
//            }else
//            {
//                System.out.println("false");
//            }
        }
    }
    
    public void setCamValues( float moveX, float moveY, float moveZ, float teta )
    {
        this.camera.setCamValues( moveX, moveY, moveZ, teta );
    }
    
    public float[] getCamPosition(){ return this.camera.getCamPosition(); }
    
    
    
    public void drawBox( GL3 gl, Shader shader )
    {
        for( StaticModels sModel : this.staticModels )
            sModel.drawBox(gl, shader);
    }
}