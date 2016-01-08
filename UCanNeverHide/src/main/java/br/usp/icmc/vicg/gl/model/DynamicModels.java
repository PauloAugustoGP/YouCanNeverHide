
package br.usp.icmc.vicg.gl.model;

import br.usp.icmc.vicg.gl.jwavefront.JWavefrontObject;
import java.io.File;
import java.util.ArrayList;

public class DynamicModels
{
    private String path;
    private JWavefrontObject wfo;
    
    private float xP;
    private float yP;
    private float zP;
    
    private float xR;
    private float yR;
    private float zR;
    
    private float xS;
    private float yS;
    private float zS;
    private float theta;
    
    public DynamicModels(String path){
        this.path = path;
        wfo = new JWavefrontObject(new File(path));
        xP = yP = zP = 0;
        xR = yR = zR = xS = yS = zS = 1;
        theta = 0;
    }
    
    public void setPosition( float xP, float yP, float zP){
        this.xP = xP;
        this.yP = yP;
        this.zP = zP;
    }
    
    public void setRotation(float theta, float xR, float yR, float zR){
        this.theta = theta;
        this.xR = xR;
        this.yR = yR;
        this.zR = zR;
    }
    
    public void setScale( float xS, float yS, float zS){
        this.xS = xS;
        this.yS = yS;
        this.zS = zS;
    }
    
    public ArrayList<Float> getRotation(){
        ArrayList<Float> tmp;
        tmp = new ArrayList<Float>();
        tmp.add(theta);
        tmp.add(xR);
        tmp.add(yR);
        tmp.add(zR);
        return tmp;
    }
    
    public ArrayList<Float> getPosition(){
        ArrayList<Float> tmp;
        tmp = new ArrayList<Float>();
        tmp.add(xP);
        tmp.add(yP);
        tmp.add(zP);
        return tmp;
    }
    
      public ArrayList<Float> getScale(){
        ArrayList<Float> tmp;
        tmp = new ArrayList<Float>();
        tmp.add(xS);
        tmp.add(yS);
        tmp.add(zS);
        return tmp;
    }
    
    public JWavefrontObject getWavefront(){
        return this.wfo;
    }
    
}
