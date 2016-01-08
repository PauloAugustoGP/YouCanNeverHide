package br.usp.icmc.vicg.gl.util;

import br.usp.icmc.vicg.gl.matrix.Matrix4;

public class FrustumCulling
{
    private final float[] pvm;
    
    public FrustumCulling()
    {
        this.pvm = new float[16];
    }
    
    public void setMatrixMVP( Matrix4 View, Matrix4 Proj )
    {
        float[] view = View.getMatrix();
        float[] proj = Proj.getMatrix();
        
        /* PVM = proj*VM */
        this.pvm[0] = proj[0]*view[0] + proj[4]*view[1] + proj[8]*view[2] + proj[12]*view[3];
        this.pvm[1] = proj[0]*view[4] + proj[4]*view[5] + proj[8]*view[6] + proj[12]*view[7];
        this.pvm[2] = proj[0]*view[8] + proj[4]*view[9] + proj[8]*view[10] + proj[12]*view[11];
        this.pvm[3] = proj[0]*view[12] + proj[4]*view[13] + proj[8]*view[14] + proj[12]*view[15];
        
        this.pvm[4] = proj[1]*view[0] + proj[5]*view[1] + proj[9]*view[2] + proj[13]*view[3];
        this.pvm[5] = proj[1]*view[4] + proj[5]*view[5] + proj[9]*view[6] + proj[13]*view[7];
        this.pvm[6] = proj[1]*view[8] + proj[5]*view[9] + proj[9]*view[10] + proj[13]*view[11];
        this.pvm[7] = proj[1]*view[12] + proj[5]*view[13] + proj[9]*view[14] + proj[13]*view[15];
        
        this.pvm[8] = proj[2]*view[0] + proj[6]*view[1] + proj[10]*view[2] + proj[14]*view[3];
        this.pvm[9] = proj[2]*view[4] + proj[6]*view[5] + proj[10]*view[6] + proj[14]*view[7];
        this.pvm[10] = proj[2]*view[8] + proj[6]*view[9] + proj[10]*view[10] + proj[14]*view[11];
        this.pvm[11] = proj[2]*view[12] + proj[6]*view[13] + proj[10]*view[14] + proj[14]*view[15];
        
        this.pvm[12] = proj[3]*view[0] + proj[7]*view[1] + proj[11]*view[2] + proj[15]*view[3];
        this.pvm[13] = proj[3]*view[4] + proj[7]*view[5] + proj[11]*view[6] + proj[15]*view[7];
        this.pvm[14] = proj[3]*view[8] + proj[7]*view[9] + proj[11]*view[10] + proj[15]*view[11];
        this.pvm[15] = proj[3]*view[12] + proj[7]*view[13] + proj[11]*view[14] + proj[15]*view[15];
    }

    public boolean checkDraw( BoundingBox box )
    {
        int in = 0;
        float[][] points = getPoints(box.getMin(), box.getMax());
        
        for( int i = 0 ; i < 8 ; i++ )
        {
            if( checkInside( points[i] ) )
                in++;
        }
        
        return in != 0;
    }
    
    private boolean checkInside( float[] point )
    {
        float x = this.pvm[0]*point[0] + this.pvm[1]*point[1] + this.pvm[2]*point[2] + this.pvm[3];
        float y = this.pvm[4]*point[0] + this.pvm[5]*point[1] + this.pvm[6]*point[2] + this.pvm[7];
        float z = this.pvm[8]*point[0] + this.pvm[9]*point[1] + this.pvm[10]*point[2] + this.pvm[11];
        
        System.out.println("PONTO: "+x+" "+y+" "+z);
        
        if( x < -1 || x > 1 ) return false;
        if( y < -1 || y > 1 ) return false;
        return !(z < -1 || z > 1);
    }
    
    private float[][] getPoints( float[] min, float[] max )
    {
        float[][] points = new float[8][3];
        
        points[0][0] = min[0];
        points[0][1] = min[1];
        points[0][2] = min[2];
        
        points[1][0] = max[0];
        points[1][1] = min[1];
        points[1][2] = min[2];
        
        points[2][0] = max[0];
        points[2][1] = min[1];
        points[2][2] = max[2];
        
        points[3][0] = min[0];
        points[3][1] = min[1];
        points[3][2] = max[2];
        
        points[4][0] = min[0];
        points[4][1] = max[1];
        points[4][2] = min[2];
        
        points[5][0] = max[0];
        points[5][1] = max[1];
        points[5][2] = min[2];
        
        points[6][0] = max[0];
        points[6][1] = max[1];
        points[6][2] = max[2];
        
        points[7][0] = min[0];
        points[7][1] = max[1];
        points[7][2] = max[2];
        
        return points;
    }
}