package com.example.tp9juego;

import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

import org.cocos2d.actions.interval.IntervalAction;
import org.cocos2d.actions.interval.JumpBy;
import org.cocos2d.actions.interval.JumpTo;
import org.cocos2d.actions.interval.MoveBy;
import org.cocos2d.actions.interval.MoveTo;
import org.cocos2d.actions.interval.ScaleBy;
import org.cocos2d.actions.interval.Sequence;
import org.cocos2d.layers.Layer;
import org.cocos2d.menus.Menu;
import org.cocos2d.menus.MenuItemLabel;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Label;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCColor3B;
import org.cocos2d.types.CCPoint;
import org.cocos2d.types.CCSize;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Juego {
    CCGLSurfaceView _JuegoVista;
    CCSize _Pantalla;
    //Sprite _Avatar;
    Sprite _Plataforma;
    Sprite _FlechaIz;
    Sprite _FlechaDe;
    Label _lblJugar;
    Label _lblTituloJuego;
    Label _Puntos;
    float RandomPos;
    int puntos = 0;
    ArrayList<Sprite> arrayAvatar;
    Button Jugar;
    Float _Timer = 1.0f;
    MediaPlayer _musicaDeFondo;

    public Juego(CCGLSurfaceView vistaAUsar) {
        _JuegoVista = vistaAUsar;
    }


    void PonerMusicaDeFondo(){
        _musicaDeFondo= MediaPlayer.create(Director.sharedDirector().getActivity(),R.raw.musica);
        _musicaDeFondo.setLooping(true);
        _musicaDeFondo.start();
    }

    public void InicioJuego() {

        Log.d("InicioJuego", "ARRANCA!");
        Director.sharedDirector().attachInView(_JuegoVista);

        _Pantalla = Director.sharedDirector().displaySize();
        Log.d("InicioJuego", "Pantalla - Ancho: " + _Pantalla.getWidth() + " - Alto: " + _Pantalla.getHeight());

        Log.d("InicioJuego", "Declaro e instancio la escena");
        Scene escenaAUsar;
        escenaAUsar = escenaMenuJuego();
     /*   Scene Menu;
        Menu= capaMenu();
        Director.sharedDirector().runWithScene(Menu);*/
        Log.d("InicioJuego", "Le digo al director que inicie la escena");
        Director.sharedDirector().runWithScene(escenaAUsar);
    }

    Scene escenaMenuJuego() {
        Scene devuelve;
        devuelve = Scene.node();
        capaMenu Menu;
        Menu = new capaMenu();
        devuelve.addChild(Menu);

        return devuelve;
    }

    Scene escenaComienzo() {
        Scene escenadevuelve;
        escenadevuelve = Scene.node();
        capaJuego capa1;
        capa1 = new capaJuego();
        escenadevuelve.addChild(capa1);
        return escenadevuelve;

    }

    Scene escenaFinal(){
        Scene escenadevuelve;
        escenadevuelve = Scene.node();
        capaFinal capa1;
        capa1 = new capaFinal();
        escenadevuelve.addChild(capa1);
        return escenadevuelve;

    }



    class capaMenu extends Layer {

        public capaMenu() {
            ponerImagenFondo();
            ponerMenu();
            PonerBotonJugar();
        }
            void ponerMenu(){
                _lblTituloJuego = Label.label("Salta Bolita Por favor!", "Verdana", 100);
                _lblTituloJuego.setPosition(_Pantalla.getWidth() / 2, _Pantalla.getHeight() / 2);

                CCColor3B colorPuntos = new CCColor3B(84, -72, 50);
                _lblTituloJuego.setColor(colorPuntos);

                super.addChild(_lblTituloJuego, -2);

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


        void PonerBotonJugar() {
            _lblJugar = Label.label("Jugar", "Boton", 100);
            CCColor3B colorPuntos;
            colorPuntos = new CCColor3B(255, 0, 0);
            _lblJugar.setColor(colorPuntos);
            MenuItemLabel BotonJugar;
            BotonJugar = MenuItemLabel.item(_lblJugar, this, "cambioEscenaJuego");
            Menu menuBotones;
            menuBotones = Menu.menu(BotonJugar);
            menuBotones.setPosition(_Pantalla.getWidth() / 2, (_Pantalla.getHeight() / 2 - _lblTituloJuego.getHeight() * 2));
            super.addChild(menuBotones);
        }

        public void cambioEscenaJuego() {
            Log.d("botonJugar", "presionaboton");
            Scene escena;
            escena = escenaComienzo();
            Director.sharedDirector().runWithScene(escena);

        }

    }

    class capaFinal extends Layer{
        public capaFinal(){
                ponerPantallaFinal();
        }

        void ponerPantallaFinal(){
            Sprite imagenFondo;
            Log.d("Poner Imagen", "Asigno imagen grafica al Sprite del avatar");
            imagenFondo = Sprite.sprite("FondoFinal.jpg");

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



    class capaJuego extends Layer {
        public capaJuego() {

            Log.d("CapaJuego", "Habilito el touch");
            setIsTouchEnabled(true);

            Log.d("CapaJuego", "Comienza el constructor");

            Log.d("CapaJuego", "Ubico al fondo en posicion inicial");
            ponerImagenFondo();

            PonerPuntos();

            Log.d("CapaJuego", "Ubico a la plataforma en posicion inicial");
            ponerPlataforma();

            Log.d("CapaJuego", "Ubico al avatar en posicion inicial");
            arrayAvatar=new ArrayList<Sprite>();
            super.schedule("ponerAvatar", _Timer);
            ponerAvatar(10);



            Log.d("CapaJuego", "Inicio el verificador de colisiones");
            super.schedule("detectarColisiones", (float) (1 / 60.0));
            detectarColisiones(0);
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

       public void ponerAvatar(float Timer) {




            Log.d("Poner Jugador", "Asigno imagen grafica al Sprite del avatar");
            Sprite _Avatar = Sprite.sprite("Avatar.png");

            Log.d("Poner Jugador", "Comienzo a ubicar al avatar");
            CCPoint posicionInicialAvatar;
            posicionInicialAvatar = new CCPoint();

            Random random;
           random =new Random();
            float anchoAvatar;
            anchoAvatar = _Avatar.getWidth();

            posicionInicialAvatar.x = random.nextInt((int) (_Pantalla.getWidth() -anchoAvatar));
            posicionInicialAvatar.x += anchoAvatar/2;
            posicionInicialAvatar.y = _Pantalla.getHeight();
            _Avatar.setPosition(posicionInicialAvatar.x, posicionInicialAvatar.y);


            CCPoint posicionFinalAvatar;
            posicionFinalAvatar = new CCPoint();
            posicionFinalAvatar.x = posicionInicialAvatar.x;
            posicionFinalAvatar.y = 0;

            Log.d("Poner avatar", "Inicio el movimiento");
            _Avatar.runAction(MoveTo.action(4, posicionFinalAvatar.x, posicionFinalAvatar.y));

            Log.d("Poner Jugador", "Lo agrego a la capa");
            arrayAvatar.add(_Avatar);
            Log.d("lista","hay "+arrayAvatar.size()+" pelotas");
            super.addChild(_Avatar,10);



       }

        void PonerPuntos() {
            _Puntos = Label.label("" + puntos, "", 100);
            _Puntos.setPosition(_Pantalla.getWidth() / 2, _Pantalla.getHeight() - _Puntos.getHeight() / 2);
            CCColor3B colorPuntos;
            colorPuntos = new CCColor3B(255, 255, 255);
            Log.d("puntos", "Tenes " + puntos + " puntos y " + _Puntos.toString());
            _Puntos.setColor(colorPuntos);
            super.addChild(_Puntos);
        }

        void ponerPlataforma() {
            Log.d("Poner plataforma", "Voy a armar el Sprite de la plataforma");
            _Plataforma = Sprite.sprite("Plataforma.png");

            Log.d("Poner plataforma", "Determino posicion inical");
            CCPoint posicionInicialPlataforma;
            float alturaPlataforma;
            alturaPlataforma = _Plataforma.getHeight();
            posicionInicialPlataforma = new CCPoint();

            posicionInicialPlataforma.x = _Pantalla.getWidth() / 2;
            posicionInicialPlataforma.y = _Pantalla.getHeight() / 2;

            Log.d("PonerPlataforma", "Determino posicion al azar");
            Random generadorAlAzar;
            generadorAlAzar = new Random();
            float anchoPlataforma;
            anchoPlataforma = _Plataforma.getWidth();
            posicionInicialPlataforma.x = generadorAlAzar.nextInt((int) (_Pantalla.getWidth() - anchoPlataforma));
            posicionInicialPlataforma.x += anchoPlataforma / 2;


            Log.d("Poner plataforma", "Ubico el Sprite");
            _Plataforma.setPosition(posicionInicialPlataforma.x, posicionInicialPlataforma.y);


            Log.d("Poner plataforma", "Lo agrego a la capa");
            super.addChild(_Plataforma, 7);


        }

        public boolean IntereseccionEntreSprites(Sprite Sprite1, Sprite Sprite2) {
            Boolean HayInterseccion = false;

            Float Sp1Arriba, Sp1Abajo, Sp1Derecha, Sp1Izquierda, Sp2Arriba, Sp2Abajo, Sp2Derecha, Sp2Izquierda;

            Sp1Arriba = Sprite1.getPositionY() + Sprite1.getHeight() / 2;
            Sp1Abajo = Sprite1.getPositionY() - Sprite1.getHeight() / 2;
            Sp1Derecha = Sprite1.getPositionX() + Sprite1.getWidth() / 2;
            Sp1Izquierda = Sprite1.getPositionX() - Sprite1.getWidth() / 2;
            Sp2Arriba = Sprite2.getPositionY() + Sprite2.getHeight() / 2;
            Sp2Abajo = Sprite2.getPositionY() - Sprite2.getHeight() / 2;
            Sp2Derecha = Sprite2.getPositionX() + Sprite2.getWidth() / 2;
            Sp2Izquierda = Sprite2.getPositionX() - Sprite2.getWidth() / 2;


//Me fijo si el vértice superior derecho de Sp1 está dentro de Sp2
            if (Sp1Arriba >= Sp2Abajo && Sp1Arriba <= Sp2Arriba &&
                    Sp1Derecha >= Sp2Izquierda && Sp1Derecha <= Sp2Derecha) {
                HayInterseccion = true;
            }
//Me fijo si el vértice superior izquierdo de Sp1 está dentro de Sp2
            if (Sp1Arriba >= Sp2Abajo && Sp1Arriba <= Sp2Arriba &&
                    Sp1Izquierda >= Sp2Izquierda && Sp1Izquierda <= Sp2Derecha) {
                HayInterseccion = true;
            }
//Me fijo si el vértice inferior derecho de Sp1 está dentro de Sp2
            if (Sp1Abajo >= Sp2Abajo && Sp1Abajo <= Sp2Arriba &&
                    Sp1Derecha >= Sp2Izquierda && Sp1Derecha <= Sp2Derecha) {
                HayInterseccion = true;

            }
//Me fijo si el vértice inferior izquierdo de Sp1 está dentro de Sp2
            if (Sp1Abajo >= Sp2Abajo && Sp1Abajo <= Sp2Arriba &&
                    Sp1Izquierda >= Sp2Izquierda && Sp1Izquierda <= Sp2Derecha) {
                HayInterseccion = true;
            }
//Me fijo si el vértice superior derecho de Sp2 está dentro de Sp1
            if (Sp2Arriba >= Sp1Abajo && Sp2Arriba <= Sp1Arriba &&
                    Sp2Derecha >= Sp1Izquierda && Sp2Derecha <= Sp1Derecha) {
                HayInterseccion = true;
            }

            return HayInterseccion;
        }


        CCPoint PosFinal= new CCPoint();

        public void detectarColisiones(float _) {

                for (Sprite _Avatar : arrayAvatar) {



                if (IntereseccionEntreSprites(_Avatar, _Plataforma)) {
                    Log.d("Eskere", "COLISIÓN");
                    puntos++;
                    _Puntos.setString("" + puntos);

                    if(puntos > 10 ){
                        _Timer = _Timer - 0.5f;
                        Log.d("Timer","puntos"+ puntos + _Timer.toString());
                    }
                    if(puntos > 15 ){
                        _Timer = _Timer - 1;
                        Log.d("Timer","puntos"+ puntos + _Timer.toString());
                    }
                    if(puntos > 20 ){
                        _Timer = _Timer - 1.5f;
                        Log.d("Timer","puntos"+ puntos + _Timer.toString());
                    }
                    if(puntos > 35 ){
                        _Timer = _Timer - 2;
                        Log.d("Timer","puntos"+ puntos + _Timer.toString());
                    }

                    Log.d("Pos", "la posicion en x del avatar es en " + _Avatar.getPositionX());

                    super.removeChild(_Avatar, true);
                    arrayAvatar.remove(_Avatar);



                } else {
                    Log.d("No", "no hay colision");


                    if (_Avatar.getPositionY() <= 0) {
                        Log.d("Perder", "perdio");
                        Scene escenaAusar;
                        escenaAusar =escenaFinal();
                        Director.sharedDirector().replaceScene(escenaAusar);


                    }
                }}

        }

       @Override
        public boolean ccTouchesBegan(MotionEvent event) {
            float xTocada, yTocada;
            xTocada = event.getX();
            yTocada = _Pantalla.getHeight() - event.getY();
            Log.d("ControlDeToque", "Comienza toque: X:" + xTocada + " - Y: " + yTocada);
            moverAvatar(xTocada, yTocada);
            return true;
        }

        @Override
        public boolean ccTouchesMoved(MotionEvent event) {
            float xTocada, yTocada;
            xTocada = event.getX();
            yTocada = _Pantalla.getHeight() - event.getY();
            Log.d("ControlDeToque", "Muevo toque: X:" + xTocada + " - Y: " + yTocada);
            moverAvatar(xTocada, yTocada);
            return true;
        }

        @Override
        public boolean ccTouchesEnded(MotionEvent event) {
            float xTocada, yTocada;
            xTocada = event.getX();
            yTocada = _Pantalla.getHeight() - event.getY();
            Log.d("ControlDeToque", "Final del toque: X:" + xTocada + " - Y: " + yTocada);
            return true;
        }

        void moverAvatar(float xAMover, float yAMover) {
            Log.d("Mover Avatar ", "Me pide que ubique en x: " + xAMover + " - y " + yAMover);
            _Plataforma.setPosition(xAMover, yAMover);
        }
    }
}



