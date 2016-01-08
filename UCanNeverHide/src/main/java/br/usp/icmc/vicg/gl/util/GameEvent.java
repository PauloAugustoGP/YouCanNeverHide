package br.usp.icmc.vicg.gl.util;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameEvent
{
    private float moveX;
    private float moveY;
    private float moveZ;
    private float delta;
    private float teta;
    
    private double walkSen;
    
    private int rotVel;
    private int mouseXPos;

    private boolean rPressed;
    private boolean lPressed;
    private boolean uPressed;
    private boolean dPressed;
    
    public GameEvent()
    {
        this.moveX = 1;
        this.moveY = 1;
        this.moveZ = 1;
        
        this.teta = 0;
        this.delta = 5;
        this.walkSen = 0;
        
        this.rotVel = 0;
        this.mouseXPos = 0;

        this.rPressed = false;
        this.lPressed = false;
        this.dPressed = false;
        this.uPressed = false;
    }
    
    public float getMoveX(){ return this.moveX; }
    public float getMoveY(){ return this.moveY; }
    public float getMoveZ(){ return this.moveZ; }
    public float getDelta(){ return this.delta; }
    public float getTeta(){ return this.teta; }
    
    public void changeValues()
    {
        if ( this.uPressed )
        {
            this.moveY = (float)(Math.sin(2*this.walkSen)/10) + 1;
            this.walkSen += Math.PI/60;
            
            float zRef = this.moveZ-(float)Math.cos(this.teta/50);
            if( this.moveZ - zRef > 0.05 ){
                this.moveZ -= (this.moveZ - zRef)/10;
            } else if( this.moveZ - zRef < -0.05 ) {
                this.moveZ += (zRef - this.moveZ)/10;
            }
            
            float xRef = this.moveX+(float)Math.sin(this.teta/50);
            if( this.moveX - xRef > 0.05 ){
                this.moveX -= (this.moveX - xRef)/10;
            }else if( this.moveX - xRef < -0.05 ){
                this.moveX += (xRef - this.moveX)/10;
            }
        }
        else if( this.dPressed )
        {
            this.moveY = (float)(Math.sin(2*this.walkSen)/10) + 1;
            this.walkSen += Math.PI/60;
            
            float zRef = this.moveZ-(float)Math.cos(this.teta/50);
            if( this.moveZ - zRef > 0.05 ){
                this.moveZ += (this.moveZ - zRef)/10;
            } else if( this.moveZ - zRef < -0.05 ) {
                this.moveZ -= (zRef - this.moveZ)/10;
            }
            
            float xRef = this.moveX+(float)Math.sin(this.teta/50);
            if( this.moveX - xRef > 0.05 ){
                this.moveX += (this.moveX - xRef)/10;
            }else if( this.moveX - xRef < -0.05 ){
                this.moveX -= (xRef - this.moveX)/10;
            }
        }
        else if( this.rPressed )
        {
            this.teta += 3.0f;
        } 
        else if( this.lPressed )
        {
            this.teta -= 3.0f;
        }
        else if(  20 < this.rotVel && this.rotVel <= 100 )
        {
            if( this.mouseXPos > 400 )
            {
                this.teta += 0.5f;
            } else {
                this.teta -= 0.5f;
            }
        }
        else if( 100 < this.rotVel && this.rotVel <= 200 )
        {
            if( this.mouseXPos > 400 )
            {
                this.teta += 1.0f;
            } else {
                this.teta -= 1.0f;
            }
        }
        else if( 200 < this.rotVel && this.rotVel <= 400 )
        {
            if( this.mouseXPos > 400 )
            {
                this.teta += 2.0f;
            } else {
                this.teta -= 2.0f;
            }
        }
    }
    
    public void keyTyped( KeyEvent e ){}

    public void keyPressed( KeyEvent e )
    {
        int code = e.getKeyCode();
        
        if( code == KeyEvent.VK_PAGE_UP )
        {
            this.delta = this.delta * 0.809f;
        }
        else if( code == KeyEvent.VK_PAGE_DOWN )
        {
            this.delta = this.delta * 1.1f;
        }
        else if( code == KeyEvent.VK_UP || code == KeyEvent.VK_W )
        {
            this.uPressed = true;
            this.dPressed = false;
        }
        else if( code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S )
        {
            this.dPressed = true;
            this.uPressed = false;
        }
        else if( code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A )
        {
            this.lPressed = true;
            this.rPressed = false;
        }
        else if( code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D )
        {
            this.rPressed = true;
            this.lPressed = false;
        }
        else if( code == KeyEvent.VK_ESCAPE )
        {
            System.exit(0);
        }
    }

    public void keyReleased( KeyEvent e )
    {
        int code = e.getKeyCode();
        
        if( code == KeyEvent.VK_UP || code == KeyEvent.VK_W )
        {
            this.uPressed = false;
            this.moveY = 1;
            this.walkSen = 0;
        }
        else if( code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S )
        {
            this.dPressed = false;
            this.moveY = 1;
            this.walkSen = 0;
        }
        else if( code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A )
        {
            this.lPressed = false;
        }
        else if( code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D )
        {
            this.rPressed = false;
        }
    }
    
    public void mouseDragged( MouseEvent e ){}
    
    public void mouseMoved( MouseEvent e )
    {
        this.mouseXPos = e.getX();
        this.rotVel = Math.abs(400 - this.mouseXPos);
    }
    
    public void setMoveValeus( float[] values )
    {
        this.moveX = values[0];
        this.moveY = values[1];
        this.moveZ = values[2];
    }
}
