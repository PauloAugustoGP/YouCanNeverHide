package br.usp.icmc.vicg.gl.util;

import br.usp.icmc.vicg.gl.model.StaticModels;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ModelReader
{
    private static final int FILES = 55;
    
    private float[] position = new float[3];
    private float[] rotation = new float[3];
    private float[] scale = new float[3];
    private float theta;
    private float[] min = new float[3];
    private float[] max = new float[3];
    
    public ModelReader()
    {
        this.position = new float[3];
        this.rotation = new float[3];
        this.scale = new float[3];
        this.min = new float[3];
        this.max = new float[3];
    }
    
    public ArrayList<StaticModels> readFile()
    {
        try
        {
            ArrayList<StaticModels> array = new ArrayList<>();
        
            String line;
            String path;
            
            BufferedReader br = new BufferedReader( new FileReader("./files/modelList.dat") );
            
            for( int i = 0 ; i <= FILES ; i++ )
            {
                line = br.readLine();
                String[] contents = line.split("\\s+");
                
                path = contents[0];
                this.position[0] = Float.valueOf(contents[1]);
                this.position[1] = Float.valueOf(contents[2]);
                this.position[2] = Float.valueOf(contents[3]);
                
                this.theta = Float.valueOf(contents[4]);
                
                this.rotation[0] = Float.valueOf(contents[5]);
                this.rotation[1] = Float.valueOf(contents[6]);
                this.rotation[2] = Float.valueOf(contents[7]);
                
                this.scale[0] = Float.valueOf(contents[8]);
                this.scale[1] = Float.valueOf(contents[9]);
                this.scale[2] = Float.valueOf(contents[10]);
                
                this.min[0] = Float.valueOf(contents[11]);
                this.min[1] = Float.valueOf(contents[12]);
                this.min[2] = Float.valueOf(contents[13]);
                
                this.max[0] = Float.valueOf(contents[14]);
                this.max[1] = Float.valueOf(contents[15]);
                this.max[2] = Float.valueOf(contents[16]);
                
                StaticModels model = new StaticModels( path );
                model.setPosition( this.position[0], this.position[1], this.position[2] );
                model.setRotation( this.theta, this.rotation[0], this.rotation[1], this.rotation[2] );
                model.setScale( this.scale[0], this.scale[1], this.scale[2] );
                model.setCollisionBounds( this.min, this.max );

                array.add(i, model);
            }

            return array;
        }
        catch( IOException e )
        {
            System.out.println("Fail Load File!");
            return null;
        }
    }
}