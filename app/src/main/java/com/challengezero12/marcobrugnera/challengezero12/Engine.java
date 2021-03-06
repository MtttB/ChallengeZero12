package com.challengezero12.marcobrugnera.challengezero12;

import android.content.Context;
import android.graphics.Rect;

import java.io.IOException;
import java.util.ArrayList;

//Questa classe gestisce la logica di funzionamento del gioco
public class Engine {

    private HandleView hw;
    private Ball ball;
    private Paddle paddle;
    private BricksWall bricks_wall;
    private boolean game_is_running; //il gioco è in esecuzione o in pausa
    private boolean game_is_ended;   //la partita è in corso o terminata
    private Context context;
    private int score;
    private final int lives = 3;
    private int remaining_lives;    //quante vite sono rimaste al giocatore
    private boolean end_game_with_victory;


    public Engine (Context context) throws IOException {
        this.context = context;
        createLevel();

    }

    public void setHandleViewReference() {
        hw = ((GameActivity) context).getHandleView();
    }

    //Creazione del livello del gioco (Il gioco prevede un solo livello di gioco)
    private void createLevel () throws IOException {
        //Inizializzazione delle variabili
        score = 0;
        remaining_lives = lives;
        end_game_with_victory = false;
        game_is_ended = false;
        game_is_running = false;
        //Creazione degli elementi grafici che costituiscono il gioco
        createBall();
        createPaddle();
        createBricksWall();
    }

    //Creazione del muro di mattoni (BricksWall)
    private void createBricksWall() throws IOException {

        //Creo l'oggetto
        bricks_wall = new BricksWall();
        //Recupero le configurazioni che determinano la dimensione del muro e la sua colorazione
        ArrayList<Integer> brick_and_wall_properties = Util.getBricksAndWallProperties(context);
        ArrayList<Integer> brick_components_color =  Util.getBrickColorProperties(context);

        int screen_height      = context.getResources().getDisplayMetrics().heightPixels;
        int screen_width       = context.getResources().getDisplayMetrics().widthPixels;
        int brick_width        = brick_and_wall_properties.get(2);
        int brick_height       = brick_and_wall_properties.get(3);
        int half_screen_height = screen_height / 2;
        int half_screen_width  = screen_width / 2;
        int half_brick_width   = brick_width / 2;
        int half_brick_height  = brick_height / 2;
        int top_first_bricks_line = (screen_height * 10) / 100;

        int number_of_linees = brick_and_wall_properties.get(0);
        int bricks_per_line  = brick_and_wall_properties.get(1);


        int left  = half_screen_width;
        int right = half_screen_width;
        boolean color = true;
        if (bricks_per_line % 2 != 0) {
            //creo la fila di mattoni (Bricks) centrale.
            //Nel caso ogni riga di mattoni del muro preveda un numero
            //dispari di Brick creo inizialmente i mattoni centrali

            //calcolo le cordinate x_left e x_right dove devono essere
            //collocati i mattoni della colonna centrale del muro
            left  = half_screen_width - half_brick_width;
            right = half_screen_width + half_brick_width;
            color = true;
            //Tutti i mattoni di una stessa colonna del muro hanno le stesse
            //cordinate x_left e x_right ma diverse coordinate top e bottom
            for ( int n = 0; n < number_of_linees; n++) {
                Brick tmp = new Brick(left,
                        top_first_bricks_line + (n * brick_height),
                        right,
                        top_first_bricks_line + ((n + 1) * brick_height));
                //Alterno i colori ad ogni riga
                if (color) {
                    tmp.setColor(brick_components_color.get(0),
                            brick_components_color.get(1),
                            brick_components_color.get(2),
                            brick_components_color.get(3));
                    color = false;
                }else {
                    tmp.setColor(brick_components_color.get(0),
                            brick_components_color.get(3),
                            brick_components_color.get(2),
                            brick_components_color.get(1));
                    color = true;
                }
                bricks_wall.addBrick(tmp);
            }
        }

        //Creo i restanti oggetti Brick
        //I mattoni è previsto che siano posizionati in modo speculare rispetto alla colonna centrale del muro
        //per questo motivo itero sulla metà dei mattoni
        for (int i = 1; i <= (bricks_per_line / 2) ; i++) {
            for (int n = 0; n < number_of_linees; n++ ) {
                Brick tmp_1 = new Brick(left - (brick_width * i),
                        top_first_bricks_line + (n * brick_height),
                        left - (brick_width * (i - 1)),
                        top_first_bricks_line + ((n + 1) * brick_height));
                Brick tmp_2 = new Brick(right + (brick_width * (i - 1)),
                        top_first_bricks_line + (n * brick_height),
                        right + (brick_width * i),
                        top_first_bricks_line + ((n + 1) * brick_height));
                //Alterno i colori dei mattoni
                if (color) {
                    tmp_1.setColor(brick_components_color.get(0),
                            brick_components_color.get(1),
                            brick_components_color.get(2),
                            brick_components_color.get(3));
                    tmp_2.setColor(brick_components_color.get(0),
                            brick_components_color.get(1),
                            brick_components_color.get(2),
                            brick_components_color.get(3));
                    color = false;
                }else {
                    tmp_1.setColor(brick_components_color.get(0)
                            , brick_components_color.get(3)
                            , brick_components_color.get(2)
                            , brick_components_color.get(1));
                    tmp_2.setColor(brick_components_color.get(0)
                            , brick_components_color.get(3)
                            , brick_components_color.get(2)
                            , brick_components_color.get(1));
                    color = true;
                }
                //Aggiungo i Brick creati al muro
                bricks_wall.addBrick(tmp_1);
                bricks_wall.addBrick(tmp_2);
            }
        }
    }


    //Creo l'oggetto Ball
    private void createBall() throws IOException{
        //Recupero le configurazioni previste per l'oggetto
        ArrayList<Integer> ball_components_color = Util.getBallColorProperties(context);
        ArrayList<Object> ball_properties = Util.getBallProperties(context);
        ball = new Ball((double)ball_properties.get(0),
                (double)ball_properties.get(1),
                (double)ball_properties.get(2),
                (double)ball_properties.get(3),
                (int)ball_properties.get(4));
        ball.setColor(ball_components_color.get(0),
                ball_components_color.get(1),
                ball_components_color.get(2),
                ball_components_color.get(3));
    }

    //Creo l'oggetto paddle
    private void createPaddle() throws IOException{
        //Recupero le configurazioni previste per l'oggetto
        ArrayList<Integer> paddle_components_color = Util.getPaddleColorProperties(context);
        ArrayList<Integer> paddle_properties = Util.getPaddleProperties(context);
        paddle = new Paddle(paddle_properties.get(0),
                paddle_properties.get(1),
                paddle_properties.get(2),
                paddle_properties.get(3));
        paddle.setColor(paddle_components_color.get(0),
                paddle_components_color.get(1),
                paddle_components_color.get(2),
                paddle_components_color.get(3));
    }


    //Determino il movimento dell'oggetto Ball nello schermo
    public void moveBall() {
        double vx       = ball.getVx();
        double vy       = ball.getVy();

        //aggiorno le variabili posx e posy
        ball.upgradePosx(vx);
        ball.upgradePosy(vy);

        int code = detectBallContainerWallCollision(vx, vy);
        //Determino se è avvenuta una collizione con una delle pareti dello schermo
        if(code == 0) {
            if (!detectBallPaddleCollision()) {
                //Se non determino una collisione con il paddle verifico
                // se si è verificata una collisione con il muro di mattoni (BricksWall)
                detectBallBricksWallDetection();
            }
        }
        else {
            if (code == 1) {
                //La palla ha colpito la parte bassa dello schermo --> il giocatore ha perso una vita
                remaining_lives -= 1;
                //se le vite raggiungono il valore 0, la partita
                //è finita per sconfitta
                if (remaining_lives == 0) {
                    game_is_ended = true;
                    hw.visualizeEndGameDialog(score);
                }
                //Ho ancora vite, la partita non è finita, metto in pausa il gioco
                //in attesa che il giocatore tocchi lo schermo per poter riprendere
                pauseGame();
                //ripristino la posizione della Ball alla posizione iniziale
                ball.resetToInitialSetup();
                hw.updateNumberOfLives(remaining_lives);
            }
        }
    }


    /**
     * Determino se la ball ha colliso con la parte bassa dello schermo
     * causando la perdita di una vita
     * @param vx
     * @param vy
     * @return
     * 0 --> nessuna parete colpita
     * 1 --> collisione con la parte bassa dello schermo che causa la perdita di una vita
     * 2 --> collisione con la parte alta dello schermo
     * 3 --> collisione con la parete verticale di sinistra
     * 4 --> collisione con la parete verticale di destra
     */
    public int detectBallContainerWallCollision (double vx, double vy) {
        double posx     = ball.getPosx();
        double posy     = ball.getPosy();
        double height   = ball.getHeight();
        double width    = ball.getWidth();
        int radius      = ball.getRadius();

        //Inizializzo il codice di ritorno della funzione
        int code        = 0;

        if (posy >= (height - radius)) {
            ball.setVy(0 - Math.abs(vy));
            code = 1; //bottom
        }

        if (posy <= (0 + radius)) {
            ball.setVy(Math.abs(vy));
            code = 2; //top
        }

        if (posx >= (width - radius)) {
            ball.setVx(0 - Math.abs(vx));
            code = 3; //left
        }

        if (posx <= (0 + radius)) {
            ball.setVx(Math.abs(vx));
            code = 4; //right
        }

        return code;
    }

    //Determino se è avvenuta una collisione tra la ball e il muro di mattoni
    private void detectBallBricksWallDetection() {
        for (int i = bricks_wall.getBricksWallSize() - 1; i >= 0; i--) {
            Brick tmp = bricks_wall.getBrickAtPos(i);
            boolean collision = detectBallBrickCollision(tmp);
            if (collision){
                //è avvenuta la collisione con un Brick
                //determino se sono esaurite le vite del Brick
                score += tmp.getScoreForCollision();
                hw.displayScore(score);
                tmp.decreaseBrickLife();
                if (tmp.isBrickBroken()) {
                    //Se l'oggetto Brick ha esaurito le sue vite lo rimuovo dal muro
                    bricks_wall.removeBrick(tmp);
                    //controllo se era l'ultimo rimasto --> il giocatore ha vinto
                    if (bricks_wall.getBricksWallSize() == 0) {
                        setGameIsEnded();
                        setGameEndedWithVictory(true);
                        hw.visualizeEndGameDialog(score);
                    }
                    break;
                }
            }
        }
    }


    /**
     * Determino se è avvenuta una collisione tra la Ball e l'oggetto r
     * passato come paramentro in ingresso alla funzione
     */
    private boolean detectBallObjectCollision(Rect r) {
        double posx       = ball.getPosx();
        double posy       = ball.getPosy();
        double vx         = ball.getVx();
        double vy         = ball.getVy();
        int radius        = ball.getRadius();

        //Collision on Top
        if ((r.contains((int)posx, (int)posy + radius))) {
            ball.setVy(0 - Math.abs(vy));
            return true;
        }

        //Collision on Bottom
        if ((r.contains((int)posx, (int)posy - radius))) {
            ball.setVy(Math.abs(vy));
            return true;
        }

        if ((r.contains((int)posx + radius, (int)posy))) {
            ball.setVx(0 - Math.abs(vx));
            return true;
        }

        if ((r.contains((int)posx - radius, (int)posy))) {
            ball.setVx(Math.abs(vx));
            return true;
        }

        return false;

    }

    //Determino se è avvenuta una collisione tra la ball e un Brick
    private boolean detectBallBrickCollision(Brick brick) {
        return detectBallObjectCollision(brick.getRect());
    }

    //Determino se è avvenuta la collisione tra la ball e il Paddle
    private boolean detectBallPaddleCollision(){
        return detectBallObjectCollision(paddle.getRect());
    }


    //Determino la nuova posizione del paddle sullo schermo
    //Il paddle si muove nel momento in cui il giocatore
    //preme un punto dello schermo oppure nel caso in cui effettuasse uno scroll sullo schermo
    public void movePaddle(int x){
        int left = paddle.getLeft();
        int top = paddle.getTop();
        int right= paddle.getRight();
        int bottom = paddle.getBottom();
        int paddle_width = right - left;
        int screen_width = context.getResources().getDisplayMetrics().widthPixels;

        //controllo se il paddle va fuori schermo
        if ((x + paddle_width) > screen_width ) return;

        //imposto la nuova posizione sullo schermo
        paddle.setPosition(x, top, x + paddle_width, bottom);
    }


    //Inizio il gioco
    public void startGame() {
        setGameRunningStatus(true);
    }

    //Metto in pausa il gioco
    public void pauseGame(){
        setGameRunningStatus(false);
        //Visualizzo un'opportuna scritta a schermo per indicare al giocatore
        //come poter riprendere la partita
        hw.visualizeTextView(true);
    }

    //Imposto lo stato del gioco (attivo/pausa)
    public void setGameRunningStatus (boolean game_is_running) {
        this.game_is_running = game_is_running;
    }

    //Imposto che il gioco è terminato
    public void setGameIsEnded () { this.game_is_ended = true; }

    public void setGameEndedWithVictory (boolean b) { this.end_game_with_victory = b;}

    public boolean gameIsRunning() { return game_is_running; }

    public Ball getBall() { return ball; }

    public Paddle getPaddle () { return paddle; }

    public BricksWall getBricksWall () { return  bricks_wall; }

    public boolean gameIsEnded() { return game_is_ended; }

}
