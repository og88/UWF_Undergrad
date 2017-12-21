package Game; 

import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.animation.*;

var frame = 0;
var posx = 320;
var posy = 80;

// sprites
def up    = for(i in [0..2]) { Image {url: "{__DIR__}up{i}.png"    } }
def right = for(i in [0..2]) { Image {url: "{__DIR__}right{i}.png" } }
def down  = for(i in [0..2]) { Image {url: "{__DIR__}down{i}.png"  } }
def left  = for(i in [0..2]) { Image {url: "{__DIR__}left{i}.png"  } }

// house background
def house = ImageView{ image: Image {url: "{__DIR__}house.png"} };

// keyboard
var    upkey = false;
var rightkey = false;
var  downkey = false;
var  leftkey = false;

// player
var player = ImageView{
   x: bind posx y: bind posy image: down[1]
   onKeyPressed: function(e:KeyEvent){
      if (e.code == KeyCode.VK_DOWN) {
         downkey = true;
      } else if (e.code == KeyCode.VK_UP) {
         upkey = true;
      }else if (e.code == KeyCode.VK_LEFT) {
         leftkey = true;
      }else if (e.code == KeyCode.VK_RIGHT) {
         rightkey = true;
      }

		if(e.code == KeyCode.VK_SPACE){
         if(fade==0.0){
         	fadein.playFromStart();
			}
			if(fade==1.0){
				fadeout.playFromStart();
			}
		}
   } // onKeyPressed

   onKeyReleased: function(e: KeyEvent){
      if (e.code == KeyCode.VK_DOWN) {
         downkey = false;
      } else if (e.code == KeyCode.VK_UP) {
         upkey = false;
      }else if (e.code == KeyCode.VK_LEFT) {
         leftkey = false;
      }else if (e.code == KeyCode.VK_RIGHT) {
         rightkey = false;
      }
   } // onKeyReleased
}

// collidable obstacles
def obstacles = [
	Rectangle { x:   0 y:   0 width:  32 height: 382 stroke: Color.RED },
	Rectangle { x:   0 y:   0 width: 414 height:  64 stroke: Color.RED },
	Rectangle { x: 384 y:   0 width:  32 height: 382 stroke: Color.RED },
	Rectangle { x:   0 y: 192 width: 128 height:  64 stroke: Color.RED },
	Rectangle { x: 192 y: 192 width:  64 height:  64 stroke: Color.RED },
	Rectangle { x: 224 y:   0 width:  32 height: 288 stroke: Color.RED },
	Rectangle { x: 288 y: 128 width:  96 height:  64 stroke: Color.RED },
	Rectangle { x:   0 y: 352 width: 128 height:  32 stroke: Color.RED },
	Rectangle { x: 192 y: 352 width: 192 height:  32 stroke: Color.RED },
	Rectangle { x: 224 y: 320 width:  32 height:  32 stroke: Color.RED },
	Rectangle { x:  32 y:  64 width: 32 height: 32 stroke: Color.YELLOW },
	Rectangle { x:  64 y:  64 width: 32 height: 32 stroke: Color.YELLOW },
	Rectangle { x:  96 y:  64 width: 32 height: 32 stroke: Color.YELLOW },
	Rectangle { x: 128 y:  64 width: 64 height: 32 stroke: Color.YELLOW },
	Rectangle { x: 192 y:  32 width: 32 height: 32 stroke: Color.YELLOW },
	Rectangle { x:  64 y: 128 width: 64 height: 32 stroke: Color.YELLOW },
	Rectangle { x:  32 y: 250 width: 32 height: 32 stroke: Color.YELLOW },
	Rectangle { x:  64 y: 250 width: 64 height: 32 stroke: Color.YELLOW },
	Rectangle { x: 200 y: 255 width: 20 height: 20 stroke: Color.YELLOW },
	Rectangle { x: 200 y: 170 width: 20 height: 20 stroke: Color.YELLOW },
	Rectangle { x: 257 y:  32 width: 32 height: 32 stroke: Color.YELLOW },
	Rectangle { x: 288 y:  32 width: 32 height: 32 stroke: Color.YELLOW },
	Rectangle { x: 320 y: 192 width: 64 height: 64 stroke: Color.YELLOW },
	Rectangle { x: 352 y: 295 width: 32 height: 60 stroke: Color.YELLOW },
	Rectangle { x:  32 y: 327 width: 64 height: 23 stroke: Color.YELLOW },
];

// game logics
var gamelogics = Timeline {
   repeatCount: Timeline.INDEFINITE
   keyFrames: KeyFrame {
      time : 1s/8
      action: function() {
         var nextposx = posx;
         var nextposy = posy;
         if(downkey) {
            nextposy += 5;
            player.image = down[++frame mod 3];
         }			
         if(upkey) {  
            nextposy -= 5;
            player.image = up[++frame mod 3];
         }
         if(rightkey) {
            nextposx += 5;
            player.image = right[++frame mod 3];
         }
         if(leftkey) {
            nextposx -= 5;
            player.image = left[++frame mod 3];
         }
         for(obst in obstacles) {
            if(obst.boundsInLocal.intersects(nextposx + 4, nextposy + 25, 19, 10)) {
               return;
            }
         }
         posx = nextposx;
         posy = nextposy;         
      }
   }
}

gamelogics.play();

// obstacles view
var fade = 0.0;

var obstacleslayer = Group {
   opacity: bind fade
   content: [
      Rectangle { x:0 y:0 width:500 height: 500 fill: Color.BLACK },
      obstacles,
      Rectangle {
        x: bind posx + 4 y: bind posy + 25 width: 19 height: 10
        fill: Color.LIME 
      }
   ]
}

var fadein = Timeline {
	keyFrames: [
   	at (0s) {fade => 0.0}
   	at (1s) {fade => 1.0}
   ]
}

var fadeout = Timeline {
	keyFrames: [
   	at (0s) {fade => 1.0}
   	at (1s) {fade => 0.0}
   ]
}


// game stage
Stage {
	title: "RPG-like demo", width: 424, height: 412
	visible: true
	scene: Scene{
      fill: Color.BLACK
		content: [house, player, obstacleslayer]
	}
}
