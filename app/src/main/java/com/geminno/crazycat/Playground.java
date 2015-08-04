package com.geminno.crazycat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.os.IInterface;
import android.view.MotionEvent;
import android.view.SurfaceHolder.Callback2;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;
public class Playground extends SurfaceView implements OnTouchListener{
	 private static int WIDTH=20;
	 private static final int ROW=10; //行
     private static final int COl=10; //列
     private static final int BLOCK=15; //路障
	 Dot  matrix[][];
	 Dot cat;
    
	public Playground(Context context) {
		super(context);
		getHolder().addCallback(callback2);
		matrix = new Dot[ROW][COl];
		for(int i=0;i<ROW;i++){
			for(int j=0;j<COl;j++){
				matrix[i][j] = new Dot(j, i);
				
			}
		}
		setOnTouchListener(this);
		initGame();
			
	}

     Callback2 callback2 = new Callback2() {
		
		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			redraw(); 
			
		}
		
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			WIDTH = width/(COl+1);
			redraw();
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void surfaceRedrawNeeded(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			
		}
	};
	
	 private boolean isAtEdge(Dot d){		 
		 if((d.getX()*d.getY())==0||(d.getX()+1)==COl||(d.getY()+1)==ROW){
			 	 return true;
		 }
		 return false;
	 }
	 private Dot getDot(int x,int y){
		return matrix[y][x];
	}
     private Dot getNeighbour(Dot d,int dir){
    	switch (dir) {
		case 1:
			return getDot(d.getX()-1, d.getY());
		case 2:
			if(d.getY()%2==0){
				return getDot(d.getX()-1, d.getY()-1);
			}else {
				 return getDot(d.getX(), d.getY()-1);
			}
		case 3:
			if(d.getY()%2==0){
				return getDot(d.getX(), d.getY()-1);
			}else {
				 return getDot(d.getX()+1, d.getY()-1);
			}
		case 4:
			return getDot(d.getX()+1, d.getY());
		case 5:
			if(d.getY()%2==0){
				return getDot(d.getX(), d.getY()+1);
			}else {
				 return getDot(d.getX()+1, d.getY()+1);
			}
		case 6:
			if(d.getY()%2==0){
				return getDot(d.getX()-1, d.getY()+1);
			}else {
				 return getDot(d.getX(), d.getY()+1);
			}
		
		default:
			break;
		}
    	return null;
    }
     private int getDistance(Dot d,int dir){
    	 int distance=0;
    	 if(isAtEdge(d)){
    		 return 1;
    	 }
    	 Dot ori = d;
    	 Dot next;
    	 while (true){
    		 next = getNeighbour(ori, dir); 
    		 if(next.getStatus()==Dot.STATUS_ON){
    			 return  distance * (-1);
    		 }
    		 if(isAtEdge(next)){
    			 distance++;
    			 return distance;
    		 }
    		
    		 distance++;
    		 ori = next ;
    	 }
    	 
     }
     private void moveTo(Dot d){
    	 d.setStatus(Dot.STATUS_IN);
    	 getDot(cat.getX(),cat.getY()).setStatus(Dot.STATUS_OFF);
    	 cat.setXY(d.getX(), d.getY());	 
     }
     private void move(){
    	
    		if(isAtEdge(cat)){
    	
    		 lose();
    		 return;
    		}
     	List<Dot> avaliable =new  ArrayList<>(); //可以走的路径
     	List<Dot> positive =new  ArrayList<>();//没有设置路障的路径
     	HashMap<Dot, Integer>  map =new HashMap<Dot,Integer>();//记录点和其方向
     	 for (int i = 1; i < 7; i++) {
     		 Dot d=getNeighbour(cat, i);
     		 if (d.getStatus()==Dot.STATUS_OFF){
				 avaliable.add(d);
     			 map.put(d,i);
     			 if(getDistance(d,i)>0){
     				  positive.add(d);
     		     }
				
            }	
		}
	
     	 if(avaliable.size()==0){
     		win();
     	 }else if(avaliable.size()==1){
     		moveTo(avaliable.get(0));
     	 }else {
     		 Dot best = null;
     		 if(positive.size()!= 0){
     			 int min = 999;
     			 for (int i = 0; i < positive.size();i++) {
     				 int a=getDistance(positive.get(i), map.get(positive.get(i)));
     				 if(a<min){
     					 min =a;
     					 best =positive.get(i);
     				 }
					
				}
     			 
     		 }else {
     			 int max =0;
     			 for (int i = 0; i < avaliable.size(); i++) {
     				 int b=getDistance(avaliable.get(i), map.get(avaliable.get(i)));
				 if(b< max){
					 max = b;
					 best=avaliable.get(i);
				 }
				}	   			 
     		 }
     		moveTo(best);	 
     	 }
     }
     
     
     private  void lose(){
    	 Toast.makeText(getContext(),"lose", Toast.LENGTH_SHORT).show();
     }
     private  void win(){
    	 Toast.makeText(getContext(),"You Win", Toast.LENGTH_SHORT).show();
     }
     private void redraw(){
    	 Canvas c=getHolder().lockCanvas();
    	 c.drawColor(Color.CYAN);
    	 Paint paint =new Paint();
    	 for (int i = 0; i < ROW; i++) {
    		 int offset =0;
    		 if(i%2!=0)
    			 offset=WIDTH/2;
			for (int j = 0; j < COl; j++) {
				Dot d =getDot(j, i);
				switch (d.getStatus()) {
				case Dot.STATUS_OFF:
					paint.setColor(0xFFEEEEEE);
					break;
				case Dot.STATUS_ON:
					paint.setColor(0xFFFFAA00);
					break;
				case Dot.STATUS_IN:
					paint.setColor(0xFFFF0000);
					break;

				default:
					break;
				}
				c.drawOval(
						new RectF(d.getX()*WIDTH+offset, d.getY()*WIDTH, 
								(d.getX()+1)*WIDTH+offset, (d.getY()+1)*WIDTH), paint);
				
			}
		}
    	 getHolder().unlockCanvasAndPost(c);
     }
     private void initGame(){
    	  for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COl; j++) {
				 matrix[i][j].setStatus(Dot.STATUS_OFF);
				
			}
		}
    	  cat =new Dot(4,5);
    	  getDot(4, 5).setStatus(Dot.STATUS_IN);
    	  for (int i = 0; i < BLOCK;) {
    		  int x =(int) ((Math.random() * 1000) % COl);
    		  int y =(int) ((Math.random() * 1000) % ROW);  
			  if(getDot(x, y).getStatus()==Dot.STATUS_OFF){
			    getDot(x, y).setStatus(Dot.STATUS_ON);
			    i++;
			  }
		}
			  
     }
     
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
			if(event.getAction()==MotionEvent.ACTION_UP){
		
			int y = (int) event.getY()/WIDTH;
            int x;
			if(y%2!=0){
				 x = (int) ((event.getX()-WIDTH/2)/WIDTH);
			}else{ 
				 x = (int) (event.getX()/WIDTH);
			}
		    if(x+1>COl||y+1>ROW) {
		    	initGame();
		    }else if(getDot(x, y).getStatus()==Dot.STATUS_OFF){
		    	getDot(x, y).setStatus(Dot.STATUS_ON);	
		    	move();
		    }
			 redraw();
		}
		return true; 
		}  
		
	}

