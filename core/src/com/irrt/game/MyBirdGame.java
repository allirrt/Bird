package com.irrt.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class MyBirdGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture[] bird;
	int birdStateFlag = 0;
	float flyHeight;
	float fallingSpeed = 0;//скорость падения
    int gameStateFlag = 0;
    Texture topTube;
    Texture bottomTube;
    int spaceBetweenTube = 500; //величина пространчтва между трубами
    Random random;
    int tubeSpeed = 5;
    int tubeNumbers = 5;
    float tubeX[] = new float[tubeNumbers];
    float tubeShift[] = new float[tubeNumbers];//сдвиг труб
    float distanceBetweenTubes;
    Circle birdCircle; //создаем круг
   Rectangle[] topTubeRectangle;
   Rectangle[] bottomTubeRectangle;
  //  ShapeRenderer shapeRenderer; отображение прямоугольникоп
   int gameScore = 0;//счет игры
   int passedTubeIndex = 0;//индекс удачных проходов между труб
    BitmapFont scoreFont;//счет на экран
    Texture gameOver;

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.png");
        topTubeRectangle = new Rectangle[tubeNumbers];
        bottomTubeRectangle = new Rectangle[tubeNumbers];
   //     shapeRenderer = new ShapeRenderer();
        birdCircle = new Circle();
		bird = new Texture[2];
        bird[0] = new Texture("bird_wings_up.png");
        bird[1] = new Texture("bird_wings_down.png");
        topTube = new Texture("top_tube.png");
        bottomTube = new Texture("bottom_tube.png");
        random = new Random();
        scoreFont = new BitmapFont();
        scoreFont.setColor(Color.CYAN);
        scoreFont.getData().setScale(10);

        gameOver =new Texture("game_over.png");

        distanceBetweenTubes = Gdx.graphics.getWidth();
        for (int i = 0; i < tubeNumbers; i++){
            tubeX[i] = Gdx.graphics.getWidth()/2
                    - topTube.getWidth()/2 + Gdx.graphics.getWidth() + i * distanceBetweenTubes;
       tubeShift[i] =  (random.nextFloat() - 0.5f) *
               (Gdx.graphics.getHeight() - spaceBetweenTube - 200);//двигаем трубы вверх и вниз рандомно
        topTubeRectangle[i] = new Rectangle();
        bottomTubeRectangle[i] = new Rectangle();


        }


	}

	@Override
	public void render () {
        batch.begin();//запускаем игру
        batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());//рисуем background
	    if (Gdx.input.justTouched()){ //когда произошло прикосновение
        gameStateFlag = 1;
        }
	    if (gameStateFlag == 1){//игра запустится ести пользователь прикоснулся к экрану

	        if (tubeX[passedTubeIndex] < Gdx.graphics.getWidth() /2){
	            gameScore++;
	            if (passedTubeIndex < tubeNumbers - 1){
	                passedTubeIndex++;
                }else {
	                passedTubeIndex = 0;
                }
            }

            if (Gdx.input.justTouched()){ //когда произошло прикосновение
                fallingSpeed = -30;
                }
            if (flyHeight > 0){//чтобы птичка не улетала выше горизонта
                fallingSpeed++;
                flyHeight -= fallingSpeed;
            } else {
                gameStateFlag = 2;
            }
           }else if(gameStateFlag == 0){
            if (Gdx.input.justTouched()) { //когда произошло прикосновение
                gameStateFlag = 1;
            }
        } else if (gameStateFlag == 2){
	        batch.draw(gameOver, Gdx.graphics.getWidth() / 2,
                    gameOver.getWidth() / 2,
                    Gdx.graphics.getHeight() / 2,
                    gameOver.getHeight() / 2);
        }
        for (int i = 0; i < tubeNumbers; i++) {
            if (tubeX[i] < - topTube.getWidth()){
                tubeX[i] = tubeNumbers * distanceBetweenTubes;
            } else {
                tubeX[i] -= tubeSpeed;
            }
            //рисуем трубы
            batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 +
                    spaceBetweenTube / 2 + tubeShift[i]);//рисуем верхнюю трубу(координаты)
            batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 -
                    spaceBetweenTube / 2 - bottomTube.getHeight() + tubeShift[i]);//рисуем нижнюю трубу(координаты)

        topTubeRectangle[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 +
                spaceBetweenTube / 2 + tubeShift[i], topTube.getWidth(), topTube.getHeight());

        bottomTubeRectangle[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 -
                spaceBetweenTube / 2 - bottomTube.getHeight() + tubeShift[i], bottomTube.getWidth(), bottomTube.getHeight());
        }
      if (birdStateFlag == 0){
          birdStateFlag = 1;
      }else {
          birdStateFlag= 0;
      }



   batch.draw(bird[birdStateFlag], Gdx.graphics.getWidth()/2 - bird[birdStateFlag].getWidth()/2,
           flyHeight);//рисуем птичку

        scoreFont.draw(batch, String.valueOf(gameScore), 100, 200);//показания счетчика




        batch.end();

   birdCircle.set(Gdx.graphics.getWidth()/2, flyHeight + bird[birdStateFlag].getWidth() /2,
           bird[birdStateFlag].getWidth() / 2);
  // shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//   shapeRenderer.setColor(Color.CYAN);
//   shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);

        for (int i = 0; i < tubeNumbers; i++) {
 //   shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 +
 //           spaceBetweenTube / 2 + tubeShift[i], topTube.getWidth(), topTube.getHeight());
//    shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 -
 //           spaceBetweenTube / 2 - bottomTube.getHeight() + tubeShift[i], bottomTube.getWidth(), bottomTube.getHeight());

     //пересечение птички с трубой
       if (Intersector.overlaps(birdCircle, topTubeRectangle[i]) ||
               Intersector.overlaps(birdCircle, bottomTubeRectangle[i])){
        birdStateFlag = 2;
       }

        }

 //  shapeRenderer.end();

	}
	

}
