package com.triforce_jsonsratch;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


public class TriForceJSONScratch extends Game {
	Preferences Score;
	int nHighscore, nCurrentHighscore;
	Skin skin;
	Stage stage;
	TextButton tbAdd;
	BitmapFontCache creators;
	Label lblHighscore, lblCurrentHighscore;
	SpriteBatch batch;
	
	@Override
	public void create () {                                     //Sites I looked at to better understand Preferences.
		Score = Gdx.app.getPreferences("High Score");           //http://stackoverflow.com/questions/25822178/preferences-libgdx-java
		nHighscore = Score.getInteger("Current High Score", 0); //https://github.com/libgdx/libgdx/wiki/Preferences
		nCurrentHighscore = 0;                                  //http://developer.android.com/training/basics/data-storage/shared-preferences.html
		lblHighscore = new Label(String.format("%03d", nHighscore), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		lblCurrentHighscore = new Label(String.format("%03d", nCurrentHighscore), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		Table tHudTable = new Table();
		tHudTable.top(); //Sets the table to the top of the screen.
		tHudTable.setFillParent(true); //Can easily be added to the stage.
		tHudTable.add(lblHighscore).expandX().padTop(10); //Expands labels, fills the extra space.
		tHudTable.row(); //Sets a new cell (row).
		tHudTable.add(lblCurrentHighscore).expandX();
		BitmapFont font = new BitmapFont();
		batch = new SpriteBatch();
		stage = new Stage();
		creators = new BitmapFontCache(font);
		skin = new Skin();
		skin.add("default", font);
		Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 10, Pixmap.Format.RGB888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("background", new Texture(pixmap));
		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("background", Color.BLACK);
		textButtonStyle.down = skin.newDrawable("background", Color.WHITE);
		textButtonStyle.over = skin.newDrawable("background", Color.WHITE);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);
		tbAdd = new TextButton("Add One", skin); // Use the initialized skin
		tbAdd.setPosition(Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / 2);
		stage.addActor(tbAdd);
		stage.addActor(tHudTable);
		Gdx.input.setInputProcessor(stage);
		System.out.println(nHighscore);
	}

	public void update(){
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
		if(tbAdd.isPressed()){
			nCurrentHighscore+=1; //This adds more than one as render happens so fast.
		}
		if(nCurrentHighscore > nHighscore){ //If your current score is higher than the saved high score, that becomes the new high score and gets saved.
			Score.putInteger("Current High Score", nCurrentHighscore);
		}
		update();
		Score.flush(); //Saves it after the program is closed.
		//Score.clear(); //This will clear the high score.
	}
}
