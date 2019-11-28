import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class MainClass {

	static final String INPUT_FILE_PATH = "src\\input.txt";

	public static void main(String[] args) {

		File file = new File(INPUT_FILE_PATH);

		BufferedReader bufferedReader;
		RobotProcessor robotProcessor = RobotProcessor.getInstance();

		try {
			bufferedReader = new BufferedReader(new FileReader(file));
			String line = bufferedReader.readLine();

			String size[] = line.split(" ");

			robotProcessor.init(Integer.parseInt(size[0]), Integer.parseInt(size[1]));

			while ((line = bufferedReader.readLine()) != null) {
				String cordinatesAndDirection[] = line.split(" ");
				robotProcessor.initRobotPostion(Integer.parseInt(cordinatesAndDirection[0]),
						Integer.parseInt(cordinatesAndDirection[1]), cordinatesAndDirection[2].charAt(0));

				line = bufferedReader.readLine();
				robotProcessor.processRobotInstructions(line);
				robotProcessor.printResult();

			}
		} catch (Exception exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}

	}

}

enum Direction {
	N(1), E(2), S(3), W(4);

	private static Map intMap = new HashMap<>();
	private static Map charcterMap = new HashMap<>();
	private int value;

	static {
		for (Direction direction : Direction.values()) {
			intMap.put(direction.value, direction);
			charcterMap.put(direction.toString().charAt(0), direction);
		}
	}

	public static Direction get(int intValue) {
		return (Direction) intMap.get(intValue);
	}

	public static Direction get(Character charValue) {
		return (Direction) charcterMap.get(charValue);
	}

	Direction(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}

class RobotProcessor {

	int mMaxX;
	int mMaxY;
	int mDirection; // (N=1, E=2, S=3, W=4)
	int mX = 0;
	int mY = 0;

	final char P = 'P'; // for 90 degree left
	final char R = 'R'; // for 90 degree right
	final char Q = 'Q'; // to move 1 block

	private static RobotProcessor mRobotProcessor;

	static {
		mRobotProcessor = new RobotProcessor();
	}

	private RobotProcessor() {
	}

	public static RobotProcessor getInstance() {
		return mRobotProcessor;
	}

	public void init(int maxX, int maxY) {
		this.mMaxX = maxX;
		this.mMaxY = maxY;
	}

	public void initRobotPostion(int x, int y, Character direction) {
		mX = x;
		mY = y;
		mDirection = Direction.get(direction).getValue();
	}

	public void processRobotInstructions(String commands) {

		for (int index = 0; index < commands.length(); index++) {
			char command = commands.charAt(index);

			switch (command) {
			case P:
				mDirection--;

				if (mDirection == 0) {
					mDirection = 4;
				}
				break;

			case R:
				mDirection++;
				if (mDirection == 5) {
					mDirection = 1;
				}
				break;

			case Q:
				move();
				break;
			}
		}
	}

	private void move() {

		Direction direction = Direction.get(mDirection);

		switch (direction) {
		case N:
			if (mY < mMaxY) {
				mY++;
			} else {
				// Robot is already at the max of Y
			}
			break;

		case E:
			if (mX < mMaxX) {
				mX++;
			} else {
				// Robot is already at the max of X
			}
			break;

		case S:
			if (mY > 0) {
				mY--;
			} else {
				// Robot is already at the min of Y
			}
			break;

		case W:
			if (mX > 0) {
				mX--;
			} else {
				// Robot is already at the min of X
			}
			break;
		}
	}

	public void printResult() {
		System.out.println(mX + " " + mY + " " + Direction.get(mDirection).toString());
	}
}
