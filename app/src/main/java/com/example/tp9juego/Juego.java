package com.example.tp9juego;

import android.util.Log;
import android.view.MotionEvent;

import org.cocos2d.actions.interval.IntervalAction;
import org.cocos2d.actions.interval.MoveBy;
import org.cocos2d.actions.interval.MoveTo;
import org.cocos2d.actions.interval.ScaleBy;
import org.cocos2d.actions.interval.Sequence;
import org.cocos2d.layers.Layer;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCPoint;
import org.cocos2d.types.CCSize;

import java.util.ArrayList;
import java.util.Random;

public class Juego {
    CCGLSurfaceView _JuegoVista;
    CCSize _Pantalla;
    Sprite _Avatar;
    Sprite _Plataforma;
    Sprite _FlechaIz;
    Sprite _FlechaDe;

    public Juego(CCGLSurfaceView vistaAUsar){
        _JuegoVista=vistaAUsar;
    }

    public void InicioJuego() {

        Log.d("InicioJuego", "ARRANCA!");
        Director.sharedDirector().attachInView(_JuegoVista);

        _Pantalla = Director.sharedDirector().displaySize();
        Log.d("InicioJuego", "Pantalla - Ancho: " + _Pantalla.getWidth() + " - Alto: " + _Pantalla.getHeight());

        Log.d("InicioJuego","Declaro e instancio la escena");
        Scene escenaAUsar;
        escenaAUsar=EscenaComienzo();

        Log.d("InicioJuego","Le digo al director que inicie la escena");
        Director.sharedDirector().runWithScene(escenaAUsar);
    }

    private Scene EscenaComienzo(){
        Log.d("EscenaComienzo","Comienza");
        Scene escenaADevolver;
        escenaADevolver=Scene.node();

        capaJuego capa = new capaJuego();
        escenaADevolver.addChild(capa);

        Log.d("EscenaComienzo","Devuelvo la escena creada");
        return escenaADevolver;
    }

    class capaJuego extends Layer{
        public capaJuego() {
            Log.d("CapaJuego", "Comienza el constructor");

            Log.d("CapaJuego", "Ubico al fondo en posicion inicial");
            ponerImagenFondo();

            Log.d("CapaJuego", "Ubico al avatar en posicion inicial");
            ponerAvatar();

            Log.d("CapaJuego", "Ubico a la plataforma en posicion inicial");
            ponerPlataforma();

            Log.d("CapaJuego", "Habilito el touch");
            setIsTouchEnabled(true);

            Log.d("CapaJuego", "Inicio el verificador de colisiones");
            super.schedule("detectarColisiones", (float) (1.0 / 60.0));
            detectarColisiones(0);
        }

        void ponerImagenFondo(){
            Sprite imagenFondo;
            Log.d("Poner Imagen", "Asigno imagen grafica al Sprite del avatar");
            imagenFondo=Sprite.sprite("Fondo.jpg");

            Log.d("Poner Imagen", "Lo ubico");
            imagenFondo.setPosition(_Pantalla.getWidth()/2,_Pantalla.getHeight()/2);


            float factorAncho, factorAlto;
            factorAncho=_Pantalla.getWidth()/imagenFondo.getWidth();
            factorAlto=_Pantalla.getHeight()/imagenFondo.getHeight();
            Log.d("Poner Imagen", "Lo escalo");
            imagenFondo.runAction(ScaleBy.action(0.01f,3,3));

            Log.d("Poner Imagen", "Lo agrego a la capa");
            super.addChild(imagenFondo);

        }

        void ponerAvatar(){
            Log.d("Poner Jugador", "Asigno imagen grafica al Sprite del avatar");
            _Avatar=Sprite.sprite("Avatar.png");

            Log.d("Poner Jugador", "Comienzo a ubicar al avatar");
            CCPoint posicionInicialAvatar;
            posicionInicialAvatar=new CCPoint();
            posicionInicialAvatar.x=_Pantalla.getWidth()/2;
            posicionInicialAvatar.y=_Pantalla.getHeight();
            _Avatar.setPosition(posicionInicialAvatar.x,posicionInicialAvatar.y);


            CCPoint posicionFinalAvatar;
            posicionFinalAvatar=new CCPoint();
            posicionFinalAvatar.x=posicionInicialAvatar.x;
            posicionFinalAvatar.y=0;

             Log.d("Poner avatar","Inicio el movimiento");
            _Avatar.runAction(MoveTo.action(3,posicionFinalAvatar.x,posicionFinalAvatar.y));


            Log.d("Poner Jugador","Lo agrego a la capa");
            super.addChild(_Avatar);
        }

        void ponerPlataforma(){
            Log.d("Poner plataforma","Voy a armar el Sprite de la plataforma");
            _Plataforma=Sprite.sprite("Plataforma.png");

            Log.d("Poner plataforma","Determino posicion inical");
            CCPoint posicionInicialPlataforma;
            float alturaPlataforma;
            alturaPlataforma=_Plataforma.getHeight();
            posicionInicialPlataforma=new CCPoint();

            posicionInicialPlataforma.x=_Pantalla.getWidth()/2;
            posicionInicialPlataforma.y=_Pantalla.getHeight()/2;

            Log.d("PonerPlataforma", "Determino posicion al azar");
            /*Random generadorAlAzar;
            generadorAlAzar = new Random();
            float anchoPlataforma;
            anchoPlataforma = _Plataforma.getWidth();
            posicionInicialPlataforma.x = generadorAlAzar.nextInt((int) (_Pantalla.getWidth() - anchoPlataforma));
            posicionInicialPlataforma.x += anchoPlataforma / 2;
            */

            Log.d("Poner plataforma","Ubico el Sprite");
            _Plataforma.setPosition(posicionInicialPlataforma.x,posicionInicialPlataforma.y);


            Log.d("Poner plataforma","Lo agrego a la capa");
            super.addChild(_Plataforma,7);


        }

        public boolean IntereseccionEntreSprites(Sprite Sprite1, Sprite Sprite2) {
            Boolean HayInterseccion = false;

            Float Sp1Arriba, Sp1Abajo, Sp1Derecha, Sp1Izquierda, Sp2Arriba, Sp2Abajo, Sp2Derecha, Sp2Izquierda;

            Sp1Arriba=Sprite1.getPositionY() + Sprite1.getHeight()/2;
            Sp1Abajo=Sprite1.getPositionY() - Sprite1.getHeight()/2;
            Sp1Derecha=Sprite1.getPositionX() + Sprite1.getWidth()/2;
            Sp1Izquierda=Sprite1.getPositionX() - Sprite1.getWidth()/2;
            Sp2Arriba=Sprite2.getPositionY() + Sprite2.getHeight()/2;
            Sp2Abajo=Sprite2.getPositionY() - Sprite2.getHeight()/2;
            Sp2Derecha=Sprite2.getPositionX() + Sprite2.getWidth()/2;
            Sp2Izquierda=Sprite2.getPositionX() - Sprite2.getWidth()/2;


//Me fijo si el vértice superior derecho de Sp1 está dentro de Sp2
            if (Sp1Arriba>=Sp2Abajo && Sp1Arriba<=Sp2Arriba &&
            Sp1Derecha>=Sp2Izquierda && Sp1Derecha<=Sp2Derecha) {
                HayInterseccion=true;
            }
//Me fijo si el vértice superior izquierdo de Sp1 está dentro de Sp2
            if (Sp1Arriba>=Sp2Abajo && Sp1Arriba<=Sp2Arriba &&
            Sp1Izquierda>=Sp2Izquierda && Sp1Izquierda<=Sp2Derecha) {
                HayInterseccion=true;
            }
//Me fijo si el vértice inferior derecho de Sp1 está dentro de Sp2
            if (Sp1Abajo>=Sp2Abajo && Sp1Abajo<=Sp2Arriba &&
            Sp1Derecha>=Sp2Izquierda && Sp1Derecha<=Sp2Derecha) {
                HayInterseccion=true;

            }
//Me fijo si el vértice inferior izquierdo de Sp1 está dentro de Sp2
            if (Sp1Abajo>=Sp2Abajo && Sp1Abajo<=Sp2Arriba &&
            Sp1Izquierda>=Sp2Izquierda && Sp1Izquierda<=Sp2Derecha) {
                HayInterseccion=true;
            }
//Me fijo si el vértice superior derecho de Sp2 está dentro de Sp1
            if (Sp2Arriba>=Sp1Abajo && Sp2Arriba<=Sp1Arriba &&
            Sp2Derecha>=Sp1Izquierda && Sp2Derecha<=Sp1Derecha) {
                HayInterseccion=true;
            }

            return  HayInterseccion;
        }

        public void detectarColisiones(float _) {
            if (IntereseccionEntreSprites(_Avatar, _Plataforma)) {
                Log.d("Eskere", "MATEO GOL!!!!!!!");
                float AvatarParaArribax,AvatarParaArribay;
                AvatarParaArribax=_Plataforma.getPositionX();
                AvatarParaArribay=_Plataforma.getPositionY();


                _Avatar.setPosition(AvatarParaArribax,AvatarParaArribay+10);
            }
        }

        @Override
        public boolean ccTouchesBegan(MotionEvent event){
            float xTocada, yTocada;
            xTocada=event.getX();
            yTocada=_Pantalla.getHeight()-event.getY();
            Log.d("ControlDeToque","Comienza toque: X:"+xTocada+" - Y: "+ yTocada);
            moverAvatar(xTocada,yTocada);
            return true;
        }

        @Override
        public boolean ccTouchesMoved(MotionEvent event){
            float xTocada, yTocada;
            xTocada=event.getX();
            yTocada=_Pantalla.getHeight()-event.getY();
            Log.d("ControlDeToque","Muevo toque: X:"+xTocada+" - Y: "+ yTocada);
            moverAvatar(xTocada,yTocada);
            return true;
        }
        @Override
        public boolean ccTouchesEnded(MotionEvent event){
            float xTocada, yTocada;
            xTocada=event.getX();
            yTocada=_Pantalla.getHeight()-event.getY();
            Log.d("ControlDeToque","Final del toque: X:"+xTocada+" - Y: "+ yTocada);
            return true;
        }

        void moverAvatar(float xAMover, float yAMover){
            Log.d("Mover Avatar ","Me pide que ubique en x: "+ xAMover+" - y "+ yAMover);
            _Plataforma.setPosition(xAMover,yAMover);
        }
    }
}


