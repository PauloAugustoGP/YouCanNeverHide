package br.usp.icmc.vicg.gl.util;

import com.jogamp.common.nio.Buffers;
import javax.media.opengl.GL3;

public class BoundingBox
{
    private final float[] min;
    private final float[] max;
    private final float[] center;
    
    public BoundingBox()
    {
        this.min = new float[3];
        this.max = new float[3];
        this.center = new float[3];
    }
    
    public void setBounds( float[] min, float[] max )
    {        
        this.min[0] = min[0];
        this.min[1] = min[1];
        this.min[2] = min[2];
        
        this.max[0] = max[0];
        this.max[1] = max[1];
        this.max[2] = max[2];
        
        centerBox();
    }
    
    public boolean contains( float x, float y, float z )
    {
        if( x < this.min[0] || x > this.max[0] ) return false;
        if( y < this.min[1] || y > this.max[1] ) return false;
        return !(z < this.min[2] || z > this.max[2]);
    }
    
    private void centerBox()
    {
        this.center[0] = (this.min[0] + this.max[0])/2;
        this.center[1] = (this.min[1] + this.max[1])/2;
        this.center[2] = (this.min[2] + this.max[2])/2;
    }

    public float[] getMin(){ return this.min; }
    public float[] getMax(){ return this.max; }
    
    public void drawBox( GL3 gl, Shader shader )
    {
        float[] vertex_buffer = new float[]{
            // Front face
            this.min[0], this.min[1], this.max[2], 
            this.min[0], this.max[1], this.min[2], 
            this.min[0], this.min[1], this.max[2], 
            this.max[0], this.min[1], this.max[2], 
            this.max[0], this.min[1], this.max[2], 
            this.max[0], this.max[1], this.max[2],
            this.min[0], this.max[1], this.min[2],
            this.max[0], this.max[1], this.max[2],
          
            // Right face
            this.max[0], this.min[1], this.max[2], 
            this.max[0], this.min[1], this.min[2],
            this.max[0], this.min[1], this.max[2],
            this.max[0], this.max[1], this.max[2],
            this.max[0], this.min[1], this.min[2],
            this.max[0], this.max[1], this.min[2],
            this.max[0], this.max[1], this.min[2],
            this.max[0], this.max[1], this.max[2], 
            
            // Back face
            this.min[0], this.min[1], this.min[2],            
            this.max[0], this.min[1], this.min[2],
            this.min[0], this.min[1], this.min[2],
            this.min[0], this.max[1], this.min[2],
            this.max[0], this.min[1], this.min[2],
            this.max[0], this.max[1], this.min[2],
            this.min[0], this.max[1], this.min[2],            
            this.max[0], this.max[1], this.min[2], 
            
            // Left face
            this.min[0], this.min[1], this.min[2],
            this.min[0], this.min[1], this.max[2],
            this.min[0], this.min[1], this.min[2],
            this.min[0], this.max[1], this.min[2],
            this.min[0], this.max[1], this.min[2],
            this.min[0], this.max[1], this.max[2],
            this.min[0], this.min[1], this.max[2],
            this.min[0], this.max[1], this.max[2],

            // Bottom face
            this.min[0], this.min[1], this.min[2],
            this.max[0], this.min[1], this.min[2],
            this.min[0], this.min[1], this.min[2],
            this.min[0], this.min[1], this.max[2],
            this.min[0], this.min[1], this.max[2],
            this.max[0], this.min[1], this.max[2],            
            this.max[0], this.min[1], this.min[2],
            this.max[0], this.min[1], this.max[2],
            
            // Top face
            this.min[0], this.max[1], this.min[2],
            this.min[0], this.max[1], this.max[2],
            this.min[0], this.max[1], this.min[2],
            this.max[0], this.max[1], this.min[2],
            this.max[0], this.max[1], this.min[2],
            this.max[0], this.max[1], this.max[2],
            this.min[0], this.max[1], this.max[2],
            this.max[0], this.max[1], this.max[2]
        };
  
        int vertex_positions_handle = shader.getAttribLocation("a_position");
        
        int[] vao = new int[1];
        gl.glGenVertexArrays(1, vao, 0);
        gl.glBindVertexArray(vao[0]);

        // create vertex positions buffer
        int vbo[] = new int[2];
        gl.glGenBuffers(2, vbo, 0);

        //the positions buffer
        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vbo[0]); // Bind vertex buffer 
        gl.glBufferData(GL3.GL_ARRAY_BUFFER, vertex_buffer.length * Buffers.SIZEOF_FLOAT,
                Buffers.newDirectFloatBuffer(vertex_buffer), GL3.GL_STATIC_DRAW);
        gl.glEnableVertexAttribArray(vertex_positions_handle);
        gl.glVertexAttribPointer(vertex_positions_handle, 3, GL3.GL_FLOAT, false, 0, 0);
        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, 0);
        
        gl.glBindVertexArray(vao[0]);
        
        gl.glDrawArrays(GL3.GL_LINES, 0, vertex_buffer.length / 3);
        gl.glBindVertexArray(0);
    }
}