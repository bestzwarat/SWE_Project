package com.swe.wakeupnow;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.Toast;

public class TicTacToeActivity extends Activity {
	
	private AlarmScreen alarmScreen;
	private TicTacToeGame mGame;
	private BoardView mBoardView;
	private TextView mInfoTextView;
	
	private boolean mGameOver = false;
	private boolean isLock;
	private boolean humanFirst = true;
	
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
		unlockMove();
    	// Human goes first
    	if(humanFirst){
    		mInfoTextView.setText("You first");
    	}else{
    		computerTurn(true);
    	}
    	humanFirst = !humanFirst;
	}
	
	
	
	private OnTouchListener mTouchListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// Determine which cell was touched
    		int col = (int) event.getX() / mBoardView.getBoardCellWidth();
    		int row = (int) event.getY() / mBoardView.getBoardCellHeight();
    		int pos = row * 3 + col;
    		
    		    		
    		if(setMove(TicTacToeGame.HUMAN_PLAYER, pos)){
    			
    			int winner = mGame.checkForWinner();
    			
    			// If no winner yet, let the computer make a move    			
    			if(winner == 0){
    				lockMove();
    				computerTurn(false);
    			}else{
    				displayWinner(winner);
    			}
    			
    			return true;
    		}
    		// So we aren't notified of continued events when finger is moved
    	return false;
    	}
	};
	
	private void computerTurn(boolean noDelay){
    	final int move = mGame.getComputerMove();
    	
		if(noDelay){
			computerTurnDisplay(move);	
		}
		else{
			final Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
			    @Override
			    public void run() {
			        // Do something after 1s = 1000ms
			    	computerTurnDisplay(move);
			    }
			}, 1000);
		}
    }
	
	private void computerTurnDisplay(int move){
    	setMove(TicTacToeGame.COMPUTER_PLAYER, move);	
		mInfoTextView.setText("");
		if(mGame.checkForWinner()!=0){
			displayWinner(mGame.checkForWinner());
		}else{
			unlockMove();
		}
    }
	
	private void displayWinner(int winner){
    	if(winner == 0){
			return;
		}
		else
			lockMove();
		
		if(winner == 1){
			//tie
			Toast.makeText(getBaseContext(), "It's a tie", Toast.LENGTH_LONG).show();
			startNewGame();
		}
		else if(winner == 2){
			//humanwin
			Toast.makeText(getBaseContext(), "Wake Up Now!!!", Toast.LENGTH_LONG).show();
//			alarmScreen.stopMediaOrVibrate();
			finish();
		}
		else{
			//computer win
			Toast.makeText(getBaseContext(), "Computer win", Toast.LENGTH_LONG).show();
			startNewGame();
		}
    }
	
	private void lockMove(){
    	mBoardView.setOnTouchListener(null);
    	isLock = true;
    }
    
    private void unlockMove(){
    	mBoardView.setOnTouchListener(mTouchListener);
    	isLock = false;
    }
		
	
	
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



