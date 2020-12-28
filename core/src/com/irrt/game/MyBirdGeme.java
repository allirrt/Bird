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

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.png");
		bird = new Texture[2];
        bird[0] = new Texture("bird_wings_up.png");
        bird[1] = new Texture("bird_wings_down.png");

        flyHeight =  Gdx.graphics.getHeight()/2 - bird[0].getHeight()/2;
	}
   // bird_wind_up
   //         bird_wind_up
	@Override
	public void render () {
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
