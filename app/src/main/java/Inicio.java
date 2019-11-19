import android.util.Log;

import com.example.tp9juego.Juego;

import org.cocos2d.actions.interval.ScaleBy;
import org.cocos2d.layers.Layer;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCSize;

public class Inicio {
    CCGLSurfaceView _JuegoVista;
    CCSize _Pantalla;

    public Inicio(CCGLSurfaceView vistaAUsar) {
        _JuegoVista = vistaAUsar;
    }

    public void InicioJuego() {

        Log.d("InicioJuego", "ARRANCA!");
        Director.sharedDirector().attachInView(_JuegoVista);

        _Pantalla = Director.sharedDirector().displaySize();
        Log.d("InicioJuego", "Pantalla - Ancho: " + _Pantalla.getWidth() + " - Alto: " + _Pantalla.getHeight());

        Log.d("InicioJuego", "Declaro e instancio la escena");
        Scene escenaAUsar;
        escenaAUsar = EscenaComienzo();

        Log.d("InicioJuego", "Le digo al director que inicie la escena");
        Director.sharedDirector().runWithScene(escenaAUsar);
    }

    private Scene EscenaComienzo() {
        Log.d("EscenaComienzo", "Comienza");
        Scene escenaADevolver;
        escenaADevolver = Scene.node();

        Juego.capaJuego capa = new Juego.capaJuego();
        escenaADevolver.addChild(capa);

        Log.d("EscenaComienzo", "Devuelvo la escena creada");
        return escenaADevolver;
    }
    class capaJuego extends Layer {
        public capaJuego() {

            Log.d("CapaJuego", "Ubico al fondo en posicion inicial");
            ponerImagenFondo();
        }
    }


    void ponerImagenFondo() {
        Sprite imagenFondo;
        Log.d("Poner Imagen", "Asigno imagen grafica al Sprite del avatar");
        imagenFondo = Sprite.sprite("Fondo.jpg");

        Log.d("Poner Imagen", "Lo ubico");
        imagenFondo.setPosition(_Pantalla.getWidth() / 2, _Pantalla.getHeight() / 2);


        float factorAncho, factorAlto;
        factorAncho = _Pantalla.getWidth() / imagenFondo.getWidth();
        factorAlto = _Pantalla.getHeight() / imagenFondo.getHeight();
        Log.d("Poner Imagen", "Lo escalo");
        imagenFondo.runAction(ScaleBy.action(0.01f, 3, 3));

        Log.d("Poner Imagen", "Lo agrego a la capa");
        super.addChild(imagenFondo);

    }
}
