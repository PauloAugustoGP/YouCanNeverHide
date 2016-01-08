package br.usp.icmc.vicg.gl.app;

import br.usp.icmc.vicg.gl.model.Scene;
import br.usp.icmc.vicg.gl.util.GameAudio;
import br.usp.icmc.vicg.gl.util.GameEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GL;
import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;

import br.usp.icmc.vicg.gl.util.Shader;
import br.usp.icmc.vicg.gl.util.ShaderFactory;
import br.usp.icmc.vicg.gl.util.ShaderFactory.ShaderType;

import com.jogamp.opengl.util.AnimatorBase;
import com.jogamp.opengl.util.FPSAnimator;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class UCanNeverHide extends KeyAdapter implements GLEventListener, KeyEventDispatcher, MouseMotionListener, KeyListener {

    private final GameAudio audio;
    private final GameEvent event;
    private final Shader shader; // Gerenciador dos shaders
    private final Scene scene;
    
    private int tempo;

    public UCanNeverHide()
    {
        // Carrega os shaders
        this.shader = ShaderFactory.getInstance(ShaderType.COMPLETE_SHADER);
        this.scene = new Scene();
        this.event = new GameEvent();
        
        this.audio = new GameAudio();
        
    }

    @Override
    public void init( GLAutoDrawable drawable )
    {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
        
        // Get pipeline
        GL3 gl = drawable.getGL().getGL3();

        // Print OpenGL version
        //System.out.println("OpenGL Version: " + gl.glGetString(GL.GL_VERSION) + "\n");

        gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        gl.glClearDepth(1.0f);

        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glEnable(GL.GL_CULL_FACE);

        //inicializa os shaders
        this.shader.init(gl);

        //ativa os shaders
        this.shader.bind();

        try {
            this.scene.init(gl, shader);
            this.scene.unitize();
            //this.scene.dump();
        } catch (IOException ex) {
            Logger.getLogger(UCanNeverHide.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.tempo = 0;
        this.audio.loop("./audio/RainThunder.wav");
    }

    @Override
    public void display( GLAutoDrawable drawable )
    {
        this.tempo++;
        
        if( this.tempo == 4000 )
        {
            this.audio.play("./audio/Little_Demon_Girl_Song-KillahChipmunk-2101926733.wav");
            this.tempo = 0;
        }
        
        // Recupera o pipeline
        GL3 gl = drawable.getGL().getGL3();

        // Limpa o frame buffer com a cor definida
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);
        
        update();
        draw();
        
//        this.scene.drawBox(gl, shader);

        // Forca execucao das operacoes declaradas
        gl.glFlush();
    }

    private void update()
    {
        this.event.changeValues();
        if( this.scene.checkCollision(this.event.getMoveX(), this.event.getMoveY(), this.event.getMoveZ()) )
        {
            this.event.setMoveValeus( this.scene.getCamPosition() );
        }
        this.scene.setCamValues( this.event.getMoveX(), this.event.getMoveY(), this.event.getMoveZ(), this.event.getTeta() );
    }

    private void draw()
    {
        this.scene.draw();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    @Override
    public void dispose( GLAutoDrawable drawable )
    {
        this.scene.dispose();
    }

    public static void main( String[] args )
    {
        // Get GL3 profile (to work with OpenGL 4.0)
        GLProfile profile = GLProfile.get(GLProfile.GL3);
        
        // Configurations
        GLCapabilities glcaps = new GLCapabilities(profile);
        glcaps.setDoubleBuffered(true);
        glcaps.setHardwareAccelerated(true);

        // Create canvas
        GLCanvas glCanvas = new GLCanvas(glcaps);

        // Add listesss1ener to panel
        UCanNeverHide listener = new UCanNeverHide();
        glCanvas.addGLEventListener(listener);
        glCanvas.addKeyListener(listener);
        glCanvas.addMouseMotionListener(listener);
        
        JFrame frame = new JFrame("Jogo");
        frame.setSize(800, 600);
        frame.add(glCanvas);
        final AnimatorBase animator = new FPSAnimator(glCanvas, 60);

        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        frame.setVisible(true);
        animator.start();
    }

    @Override
    public boolean dispatchKeyEvent( KeyEvent e )
    {
        if( e.getID() == KeyEvent.KEY_PRESSED )
        {
            keyPressed( e );
        }
        else if( e.getID() == KeyEvent.KEY_RELEASED )
        {
            keyReleased( e );
        }
        
        return true;
    }
    
    @Override public void keyTyped(KeyEvent e){ event.keyTyped(e); }
    @Override public void keyPressed( KeyEvent e ){ event.keyPressed(e); }
    @Override public void keyReleased( KeyEvent e ){ event.keyReleased(e); }
    
    @Override public void mouseDragged( MouseEvent e ){ event.mouseDragged(e); }
    @Override public void mouseMoved( MouseEvent e ){ event.mouseMoved(e); }
}
