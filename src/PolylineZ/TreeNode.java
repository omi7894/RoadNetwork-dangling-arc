package PolylineZ;

public class TreeNode {

		private int id;
		private TreeNode[] child;
		private TreeNode parent;
		private BBox box;
		private Shape[] shape;
		private int numOfShape;
		
		public TreeNode() {
			super();
			// TODO Auto-generated constructor stub
		}

		public TreeNode(int id) {
			this.id=id;
			parent = null;
			box = null;
			numOfShape=0;
			child = new TreeNode[4];
			shape = new Shape[200];
		}
		
		public int getNumOfShape() {
			return numOfShape;
		}

		public void setNumOfShape(int numOfShape) {
			this.numOfShape = numOfShape;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public TreeNode getParent() {
			return parent;
		}

		public void setParent(TreeNode parent) {
			this.parent = parent;
		}

		public TreeNode[] getChild() {
			return child;
		}


		public BBox getBox() {
			return box;
		}

		public void setBox(BBox box) {
			this.box = box;
		}

		public Shape[] getShape() {
			return shape;
		}


		// 첫번째 자식노드와 연결해주는 메소드
		public void makeChild(int n, TreeNode child) {
			if(this.child[n]!=null) {
				this.child[n] = null;
			}
			this.child[n] = child;
		}

		public void addShape(Shape shape) {
			this.shape[numOfShape] = shape;
			numOfShape++;
		}


}
