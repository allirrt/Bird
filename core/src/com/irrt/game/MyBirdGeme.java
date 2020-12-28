package com.irrt.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyBirdGeme extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture[] bird;
	int birdStateFlag = 0;
	float flyHeight;
	float fallingSpeed = 0;//скорость падения
    int gameStateFlag = 0;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.png");
		bird = new Texture[2];
        bird[0] = new Texture("bird_wings_up.png");
        bird[1] = new Texture("bird_wings_down.png");


        flyHeight =  Gdx.graphics.getHeight()/2 - bird[0].getHeight()/2;//высота полета
	}

	@Override
	public void render () {
	    if (Gdx.input.justTouched()){ //когда произошло прикосновение
        gameStateFlag = 1;
        }
	    if (gameStateFlag == 1){//игра запустится ести пользователь прикоснулся к экрану

            if (Gdx.input.justTouched()){ //когда произошло прикосновение
                fallingSpeed = -30;
            }
            if (flyHeight > 0 || fallingSpeed < 0){//чтобы птичка не улетала выше горизонта
                fallingSpeed++;
                flyHeight -= fallingSpeed;
            }
           }else {
            if (Gdx.input.justTouched()) { //когда произошло прикосновение
                gameStateFlag = 1;
            }
        }

      if (birdStateFlag == 0){
          birdStateFlag = 1;
      }else {birdStateFlag= 0;}

   batch.begin();//запускаем игру
   batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());//рисуем

   batch.draw(bird[birdStateFlag], Gdx.graphics.getWidth()/2 - bird[birdStateFlag].getWidth()/2,
           flyHeight);//рисуем
   batch.end();
	}
	

}
