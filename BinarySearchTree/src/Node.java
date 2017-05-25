import java.awt.Point;
public class Node {
 int num;
 Point father;
 Node left;
 Node right;
 boolean added;

public Node(int num){
	this.num= num;
	this.father= new Point(0,0);
	this.left=null;
	this.right=null;
	this.added =false ;
	}
public boolean isLeft(Node parent){
	if(this == parent.left)
		return true;
	else{
		return false;
		}
	}
public boolean isRight(Node parent){
	if(this == parent.right)
		return true;
	else{
		return false;
		}
	}

}