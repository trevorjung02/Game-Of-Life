import java.util.*;
import java.io.*;

class GameOfLife {
	public static void main(String[] args) throws IOException, InterruptedException {
		int[][] board = randomState(20, 20);
		//String path = "GGG.txt";
		//int[][] board = importState(path);
		while(true) {
			render(board);
			board = nextState(board);
			//Thread.sleep(10);
		}
	}
	public static int[][] importState(String path) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(path));
		String curLine;
		StringTokenizer st = new StringTokenizer(br.readLine());
		int height = Integer.parseInt(st.nextToken());
		int width = Integer.parseInt(st.nextToken());
		//System.out.println(height + " " + width);
		int[][] board = new int[height][width];
		for(int y = 0; y < height; y++) {
			String s = br.readLine();
			for(int x = 0; x < width; x++) {
				board[y][x] = Character.getNumericValue(s.charAt(x));
				//System.out.print(board[y][x]);
			}
			//System.out.println();
		}
		br.close();
		return board;
	}
	public static int[][] randomState(int height, int width) {
		int[][] board = new int[height][width];
		Random rng = new Random();
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				if(rng.nextInt(100) >= 80) {
					board[i][j] = 1;
				}
				else {
					board[i][j] = 0;
				}
			}
		}
		return board;
	}
	public static void render(int[][] board) {
		int width = board[0].length;
		int height = board.length;
		/*System.out.print(' ');
		for(int i = 0; i < width; i++) {
			System.out.print('=');
		}
		System.out.println();*/
		for(int i = 0; i < height; i++) {
			System.out.print('|');
			for(int j = 0; j < width; j++) {
				if(board[i][j] == 1) {
					if(j < width-1) {System.out.print("# ");}
					else {System.out.print("#");}
				}
				else {
					if(j < width-1) {System.out.print("  ");}
					else {System.out.print(" ");}
				}
			}
			System.out.println('|');
		}
		/*System.out.print(' ');
		for(int i = 0; i < width; i++) {
			System.out.print('=');
		}*/
		System.out.println();
	}
	public static int[][] nextState(int[][] board) {
		int width = board[0].length;
		int height = board.length;
		int[][] nextBoard = new int[height][width];
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				int numNeighbors = countNeighbors(board, j, i);
				if(numNeighbors <= 1 || numNeighbors > 3) {
					nextBoard[i][j] = 0;
				}
				else if(numNeighbors == 3 || (numNeighbors == 2 && board[i][j] == 1)) {
					nextBoard[i][j] = 1;
				}
			}
		}
		return nextBoard;
	}
	public static int countNeighbors(int[][] board, int x, int y) {
		int width = board[0].length;
		int height = board.length;
		if(x < 0 || y < 0 || x >= width || y >= height) {
			return 0;
		}
		int numNeighbors = 0;
		for(int i = -1; i<= 1; i++) {
			for(int j = -1; j<=1; j++) {
				numNeighbors += getState(board, x + i, y + j);
			} 
		}
		return numNeighbors-getState(board, x, y);
	}
	public static int getState(int[][] board, int x, int y) {
		int width = board[0].length;
		int height = board.length;
		if(x < 0 || y < 0 || x >= width || y >= height) {
			return 0;
		}
		return board[y][x];
	}
}