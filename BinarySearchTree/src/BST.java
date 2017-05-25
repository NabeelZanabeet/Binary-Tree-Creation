import javax.swing.JOptionPane;

public class BST {
 Node root; 



public BST(){
	root=null;
	}

public BST(int num){
	root=new Node(num);
	}

public void add(Node root ,Node n){
	if(root.num > n.num){
		if(root.left==null){
		root.left=n;
		return;
	}
		add(root.left , n);
	}
	else if(root.num < n.num){
		if(root.right==null){
		root.right =  n;
		return;
	}
	add(root.right , n);
	}
	
}


public boolean remover(int num){
	Node focus = root ;
	Node parent = root;
	
	boolean isItALeftChild =true;
	
	while(focus.num != num){
		parent =focus;
		
		if(num< focus.num){
			isItALeftChild =true;
			focus =focus.left;
		}else{
			isItALeftChild = false ;
			focus =focus.right;

		}
		if(focus == null){
			return false;
		}
	}
		if(focus.left==null && focus.right==null ){
			if(focus == root){
				root =null;
			}else if(isItALeftChild){
				parent.left = null ;
			}else{
				parent.right = null ;
			}
		}
		else if(focus.right == null){
			if(focus == root){
				root= focus.left;
			}else if(isItALeftChild){
				parent.left = focus.left;
			}else{
				parent.right = focus.left;
			}
		}
		else if(focus.left == null){
			if(focus == root){
				root= focus.right;
			}else if(isItALeftChild){
				parent.left = focus.right;
			}else{
				parent.right = focus.left;
			}
		}
		else{
			Node r = getReplacementNode(focus);
			if(focus == root)
				root= r;
			else if(isItALeftChild)
				parent.left =r;
			else
				parent.right=r;
			r.left=focus.left;
			
		}
	return true;
} // end method

public Node getReplacementNode(Node rNode){
	Node rParent = rNode;
	Node r = rNode;
	Node focus = rNode.right;
	while(focus != null){
		rParent = r;
		r = focus ;
		focus =focus.left;
	}
	if(r != rNode.right){
		rParent.left = r.right;
		r.right =rNode.right;
	}
	return r;
}
}