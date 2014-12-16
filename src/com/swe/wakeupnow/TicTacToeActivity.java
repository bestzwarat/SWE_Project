package com.swe.wakeupnow;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TicTacToeActivity extends Activity {
	
	private AlarmScreen alarmScreen;
	private TicTacToeGame mGame;
	private BoardView mBoardView;
	private TextView mInfoTextView;
	
	private boolean mGameOver = false;
	
	MediaPlayer mHumanMediaPlayer;
	MediaPlayer mComputerMediaPlayer;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ttt);
		
		mGame = new TicTacToeGame();
		mBoardView = (BoardView) findViewById(R.id.board);
		mBoardView.setGame(mGame);
		mBoardView.setOnTouchListener(mTouchListener);
		
		mInfoTextView = (TextView) findViewById(R.id.information);
		
		mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Expert);
		
		if (savedInstanceState == null) {
			startNewGame();
			}
		else {
			// Restore the game's state
			mGame.setBoardState(savedInstanceState.getCharArray("board"));
			mGameOver = savedInstanceState.getBoolean("mGameOver");
			mInfoTextView.setText(savedInstanceState.getCharSequence("info"));
		}
	}
	
	
	private void startNewGame() {
		mGame.clearBoard();
		mBoardView.invalidate();
		mInfoTextView.setText("Your turn");
	}
	
	
	
	private OnTouchListener mTouchListener = new OnTouchListener() {
		public boolean onTouch(View v, MotionEvent event) {
			
			// Determine which cell was touched
			int col = (int) event.getX() / mBoardView.getBoardCellWidth();
			int row = (int) event.getY() / mBoardView.getBoardCellHeight();
			final int pos = row * 3 + col;
			if (!mGameOver && setMove(TicTacToeGame.HUMAN_PLAYER, pos)) {
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
				public void run() {
					
					setMove(TicTacToeGame.HUMAN_PLAYER, pos);
					// If no winner yet, let the computer make a move
					int winner = mGame.checkForWinner(); 
					
					if (winner == 0) { 
						mInfoTextView.setText("Computer turn");
						int move = mGame.getComputerMove(); 
						setMove(TicTacToeGame.COMPUTER_PLAYER, move);
						winner = mGame.checkForWinner();
					}
					
					if (winner == 0) {
						mInfoTextView.setText("Your turn");
					}

					else {
						if (winner == 1) {
						//tie
							Toast.makeText(getBaseContext(), "It's a tie", Toast.LENGTH_LONG).show();
							startNewGame();
						}

						else if (winner == 2) {
						//humanwin
							Toast.makeText(getBaseContext(), "You win", Toast.LENGTH_SHORT).show();
							
							finish();
						}
					
						else  {
						//computer win
							Toast.makeText(getBaseContext(), "Computer win", Toast.LENGTH_LONG).show();
							startNewGame();
						}
					}
				}
				}, 1000);
				return true;
				
			}
			// So we aren't notified of continued events when finger is moved
			return false;
		}
	};
	
	
	
	private boolean setMove(char player, int location) {
		if (mGame.setMove(player, location)) {
			mBoardView.invalidate();
			// Redraw the board
			if (player == 'X') {
				mHumanMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.player);
				mHumanMediaPlayer.start(); // Play the sound effect
			}
			if (player == 'O') {
				mComputerMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.player);
				mComputerMediaPlayer.start(); // Play the sound effect
			}
			
			return true;
		}
		return false;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mHumanMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.player);
		mComputerMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.com);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mHumanMediaPlayer.release();
		mComputerMediaPlayer.release();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putCharArray("board", mGame.getBoardState());
		outState.putBoolean("mGameOver", mGameOver);
		outState.putCharSequence("info", mInfoTextView.getText());
	}
	
}



