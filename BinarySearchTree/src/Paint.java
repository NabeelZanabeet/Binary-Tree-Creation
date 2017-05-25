
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Paint extends JFrame{
	private Panel board ; 
	private JPanel south;
	private JButton addroot ,addnode ,deletenode ;
	private Rectangle currRec , newRec;
	private Point p1 ;
	private BST newTree;
	private Vector<Node>tree ;
	private Vector<Rectangle>shape ;
	private Vector<String>string;
	private String number;
	private Node currNode;
	private boolean visiable ;
	
	public Paint(){
		super("Tree Editor");
		visiable = false ;
		newTree =new BST();
		currNode = new Node(0);
		p1 = new Point(0,0);
		ActHandler AH =new ActHandler();
		addroot = new JButton("Add New Root");
		addroot.addActionListener(AH);
		addnode= new JButton("Add New Node");
		addnode.addActionListener(AH);
		deletenode =new JButton("Delete Node");
		deletenode.addActionListener(AH);
		south = new JPanel();
		south.add(addroot);
		south.add(addnode);
		south.add(deletenode);
		add(south,BorderLayout.SOUTH);
		MouseHandler h =new MouseHandler();
		board = new Panel();
		board.addMouseListener(h);
		board.addMouseMotionListener(h);
		add(board);
		tree = new Vector<Node>();
		shape = new Vector<Rectangle>();
		string =  new Vector<String>();
		
	} // Constructor
	
	private class ActHandler implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if(event.getSource() == addroot){
				visiable = true;
				tree = new Vector<Node>();
				shape = new Vector<Rectangle>();
				string =  new Vector<String>();
				String input;
				input = JOptionPane.showInputDialog("Enter the value of the root : ");
				newTree = new BST(Integer.parseInt(input));
				currNode =newTree.root;
				tree.addElement(currNode);
				
			}
			else if (event.getSource() == addnode){
				String input;
				if(newTree.root == null){
					JOptionPane.showMessageDialog(Paint.this,"Please add the root first !");
				}else if (newTree.root != null){
					
					visiable = true;
				input = JOptionPane.showInputDialog("Enter the value of the node : ");
				 addNewNode(Integer.parseInt(input));
				}
			
			}else{
				String input;
				input = JOptionPane.showInputDialog("Enter the value of node you want to delete :");
				boolean delete= removeNode(Integer.parseInt(input));
				//repaint();
				if(!delete){
					JOptionPane.showMessageDialog(Paint.this,"the node dose not exsit !");
					}
				}	
			}
		}// ActHandler
	
private class MouseHandler extends MouseAdapter implements MouseMotionListener {
		public void mouseReleased(MouseEvent event){
			if(SwingUtilities.isLeftMouseButton(event)){
			p1 = event.getPoint();
			newRec = new Rectangle(p1.x,p1.y,40,30);
			shape.addElement(newRec);
			number = new String(""+currNode.num);
			string.addElement(number);
			currNode.added=true;
			visiable =false;
			repaint();
			}
			else if(SwingUtilities.isRightMouseButton(event)){
				for(int i =0 ; i<shape.size() ;i++){
					Rectangle rec = shape.get(i);
					if(rec.contains(event.getPoint())){
						String input;
						input = JOptionPane.showInputDialog("Enter the new value of node  :");
						removeNode(tree.get(i).num);
						addNewNode(Integer.parseInt(input));
					}
				}
				repaint();
			}
			
		}
		public void mouseMoved(MouseEvent event){
			p1 = event.getPoint();
			currRec = new Rectangle(p1.x,p1.y,40,30);
			number = new String(""+currNode.num);
			repaint();
		}
		public void mouseDragged(MouseEvent event){
			
		}
	}

public void setFathers(){
	for(int i =0 ;i <tree.size() ;i++){
		Node node = tree.get(i);
		if(node == newTree.root){
			
		}
		for(int j =0 ;j <tree.size() ;j++){
			if(node.isRight(tree.get(j))){
				Rectangle rec = shape.get(j);
				node.father =new Point(rec.x+rec.width,rec.y+rec.height);
			}else if(node.isLeft(tree.get(j))){
				Rectangle rec = shape.get(j);
				node.father =new Point(rec.x,rec.y+rec.height);
			} 
		}
	}
}
	
public void addNewNode(int data){
	for(int i =0 ; i<tree.size() ;i++){
		Node node =tree.get(i);
		if(node.num==data){

		JOptionPane.showMessageDialog(Paint.this,"Cannot duplicate a node !");visiable = false;}
	}if(visiable){
	Node node = new Node(data);
	newTree.add(newTree.root,node);
	currNode =node;
	tree.addElement(currNode);}
	
}
public boolean removeNode(int data){
	boolean delete= newTree.remover(data);
	for(int i =0 ; i<tree.size() ;i++){
		Node node =tree.get(i);
		if(node.num==data){
			tree.removeElement(node);
			shape.removeElement(shape.get(i));
			string.removeElementAt(i);
		}
	}
	return delete;
}
private class Panel extends JPanel{
	
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		setFathers();
		for(int i =0 ; i<shape.size() ;i++){
		Node node = tree.get(i);
		Rectangle rec = shape.get(i);
		String str = string.get(i);
		g.setColor(Color.black);
		g.drawRect(rec.x,rec.y,rec.width,rec.height);
		g.setColor(Color.CYAN);
		g.fillRect(rec.x,rec.y,rec.width,rec.height);
		g.setColor(Color.black);
		g.drawString(str, rec.x+10,rec.y+20);
		if(!node.father.equals(new Point(0,0))){
		g.drawLine(node.father.x, node.father.y,(int)rec.getCenterX(),rec.y);
		}
	
		}
		if (visiable ){
		g.drawRect(currRec.x,currRec.y,currRec.width,currRec.height);
		g.drawString(number, currRec.x+10,currRec.y+20);
		if(!currNode.father.equals(new Point(0,0)) && !currNode.added){
		g.drawLine(currNode.father.x, currNode.father.y,(int)currRec.getCenterX(),currRec.y);
		}
		}
	}
}
}
