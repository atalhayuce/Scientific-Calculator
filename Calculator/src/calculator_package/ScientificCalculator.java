package calculator_package;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.Properties;

import javax.script.ScriptException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

public class ScientificCalculator extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DecimalFormat decFormat = new DecimalFormat("#,###.00");

	private String[] btnSymbols = { "M0", "M1", "M2", "M3", "AC", "sin", "cos", "tan", "%", "<--", "π", "(", ")", "n!",
			"÷", "x^2", "7", "8", "9", "×", "x^n", "4", "5", "6", "-", "log", "1", "2", "3", "+", "Sqrt", "Cube root",
			"0", ".", "=" };

	private int operator = 0;
	private String M0Value = "0";
	private String M1Value = "0";
	private String M2Value = "0";
	private String M3Value = "0";
	private double myFirstNum = 0, mySecondNum = 0;

	private boolean evalMode = false;

	private JToggleButton btnEvalOnOff = new JToggleButton("Turn Eval Expression On");
	private JPanel displayPanel = new JPanel(new BorderLayout(8, 5));
	private JPanel btnPanel = new JPanel(new GridLayout(7, 5, 2, 2));
	private JPanel bottomPanel = new JPanel(new GridLayout(2, 1, 2, 2));
	private JButton[] buttons = new JButton[35];
	private JTextArea screen = new JTextArea(2, 40);
	private JTextField bottomScreen = new JTextField(40);



	public ScientificCalculator() throws URISyntaxException, IOException {

		// to read memory button values from config.properties file
		readConfiguration();

		initialise();

	}

	private void initialise() {

		setTitle("Scientific Calculator");
		screen.setFont(new Font(getName(), Font.BOLD, 18));
		screen.setBackground(Color.BLUE);
		displayPanel.setBackground(Color.BLUE);
		btnPanel.setBackground(Color.BLUE);
		bottomPanel.setBackground(Color.BLUE);
		screen.setForeground(Color.WHITE);
		screen.setEditable(false);

		// buttons created here
		for (int Symbol_n = 0; Symbol_n < btnSymbols.length; Symbol_n++) {
			buttons[Symbol_n] = new JButton(btnSymbols[Symbol_n]);
			buttons[Symbol_n].setFont(new Font(getName(), Font.BOLD, 14));
			buttons[Symbol_n].setOpaque(false);
			buttons[Symbol_n].setBorderPainted(false);
			buttons[Symbol_n].setBackground(Color.BLUE);
			buttons[Symbol_n].setForeground(Color.WHITE);
			buttons[Symbol_n].addActionListener(this);
			btnPanel.add(buttons[Symbol_n]);
		}

		// these are "(" and ")" so they are only enabled for eval mode.
		buttons[11].setEnabled(false);
		buttons[12].setEnabled(false);

		bottomPanel.add(btnEvalOnOff); // toggle button, could have used radio button too
		bottomPanel.add(bottomScreen);

		bottomScreen.setForeground(Color.WHITE);
		bottomScreen.setBackground(Color.BLUE);

		btnEvalOnOff.setSelected(false); // initial state
		btnEvalOnOff.setBackground(Color.BLUE);
		btnEvalOnOff.setForeground(Color.WHITE);

		btnEvalOnOff.addActionListener(this);

		displayPanel.add(screen, BorderLayout.NORTH);
		displayPanel.add(bottomPanel, BorderLayout.SOUTH);
		displayPanel.add(btnPanel, BorderLayout.CENTER);

		add(displayPanel); // display panel is added to the window
		setSize(550, 650); // width and height of the window is set here
		setLocationRelativeTo(null); // places the form at the centre of the screen
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // closing window is managed at window listener part
		setVisible(true);

		// Window event listener
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					writeConfiguration(); // before closing the window all memory values are saved to config.properties
											// file
					System.exit(0); // closes the program
				} catch (URISyntaxException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});


	}



	public static void main(String[] args) throws URISyntaxException, IOException, ScriptException {

		new ScientificCalculator();

	}

	// Action event listener (listens the button events)
	@Override
	public void actionPerformed(ActionEvent buttonClick) {


		double myNumber;
		// when a button is clicked, it's symbol is being caught here
		String myCommand = buttonClick.getActionCommand().toString();

		switch (myCommand) {

		case ".": // if . button clicked
			if (evalMode == false) {
				// for eval mode OFF, if screen contains "." program prevents to use it again
				if (screen.getText().contains(".") == false) {
				screen.setText(screen.getText() + ".");
				bottomScreen.setText("");
			}
		}
		else if (evalMode == true) {
			// for eval mode ON, "." can be used multiple times
			screen.setText(screen.getText() + ".");
			bottomScreen.setText("");
		}
			break;

		case "0":
			screen.setText(screen.getText() + "0");
			bottomScreen.setText("");
			break;

		case "1":
			screen.setText(screen.getText() + "1");
			bottomScreen.setText("");
			break;

		case "2":
			screen.setText(screen.getText() + "2");
			bottomScreen.setText("");
			break;

		case "3":
			screen.setText(screen.getText() + "3");
			bottomScreen.setText("");
			break;

		case "4":
			screen.setText(screen.getText() + "4");
			bottomScreen.setText("");
			break;

		case "5":
			screen.setText(screen.getText() + "5");
			bottomScreen.setText("");
			break;

		case "6":
			screen.setText(screen.getText() + "6");
			bottomScreen.setText("");
			break;

		case "7":
			screen.setText(screen.getText() + "7");
			bottomScreen.setText("");
			break;

		case "8":
			screen.setText(screen.getText() + "8");
			bottomScreen.setText("");
			break;

		case "9":
			screen.setText(screen.getText() + "9");
			bottomScreen.setText("");
			break;

		case "(": // ( is only used at evaluation mode
			if (evalMode == true) {
			screen.setText(screen.getText() + "(");
			bottomScreen.setText("");
		}
			break;

		case ")": // ) is only used at evaluation mode
			if (evalMode == true) {
			if (screen.getText().contains("(") == true) {
				screen.setText(screen.getText() + ")");
				bottomScreen.setText("");
			}
		}
			break;

		case "M0":
			// if eval mode is false; when screen is empty and memory button (M0,M1..)
			// clicked, the value of it is written to the screen
			// otherwise, the value of the screen is stored to the memory button
			// in eval mode only the value of the memory button can be get not set
			if (screen.getText().isEmpty() == true && evalMode == false) {
			screen.setText(M0Value);
		} else if (screen.getText().isEmpty() == false && evalMode == false) {
				M0Value = screen.getText();

		} else if (evalMode == true) {
			screen.setText(screen.getText() + M0Value);
			}

			bottomScreen.setText("");
			break;

		case "M1":
			// screen empty eval mode OFF
			if (screen.getText().isEmpty() == true && evalMode == false) {
				screen.setText(M1Value);
				// screen full, eval mode OFF
			} else if (screen.getText().isEmpty() == false && evalMode == false) {
				M1Value = screen.getText();
				// eval mode ON
			} else if (evalMode == true) {
				screen.setText(screen.getText() + M1Value);
			}
			bottomScreen.setText("");
			break;

		case "M2":
			if (screen.getText().isEmpty() == true && evalMode == false) {
				screen.setText(M2Value);
			} else if (screen.getText().isEmpty() == false && evalMode == false) {
				M2Value = screen.getText();

			} else if (evalMode == true) {
				screen.setText(screen.getText() + M2Value);
			}
			bottomScreen.setText("");
			break;

		case "M3":
			if (screen.getText().isEmpty() == true && evalMode == false) {
				screen.setText(M3Value);
			} else if (screen.getText().isEmpty() == false && evalMode == false) {
				M3Value = screen.getText();

			} else if (evalMode == true) {
				screen.setText(screen.getText() + M3Value);
			}
			bottomScreen.setText("");

			break;

		case "π":
			if (evalMode == false) {
				screen.setText(String.valueOf(decFormat.format(Math.PI)));
			bottomScreen.setText("");
		} else if (evalMode == true) {
			screen.setText(screen.getText() + String.valueOf(decFormat.format(Math.PI)));
		}
			break;

		case "+":
			// screen FULL and evalmode OFF
			if (screen.getText().isEmpty() == false && evalMode == false) {

				myFirstNum = Double.parseDouble(screen.getText().toString());
				operator = 1;
				screen.setText("");
				bottomScreen.setText("");
				// screen FULL and evalmode ON
			} else if (screen.getText().isEmpty() == false && evalMode == true) {
				screen.setText(screen.getText() + "+");
			}

			break;

		case "-":
			// screen FULL and evalmode OFF
			if (screen.getText().isEmpty() == false && evalMode == false) {

				myFirstNum = Double.parseDouble(screen.getText().toString());
				operator = 2;
				screen.setText("");
				bottomScreen.setText("");
				// screen EMPTY and evalmode OFF
			} else if (screen.getText().isEmpty() == true && evalMode == false) {
				screen.setText("-");
				bottomScreen.setText("");
				// evalmode ON
			} else if (evalMode == true) {
				screen.setText(screen.getText() + "-");
			}

			break;

		case "×":
			// screen full, eval mode OFF
			if (screen.getText().isEmpty() == false && evalMode == false) {

				myFirstNum = Double.parseDouble(screen.getText().toString());
				operator = 3;
				screen.setText("");
				bottomScreen.setText("");
				// screen full, eval mode ON
			} else if (screen.getText().isEmpty() == false && evalMode == true) {
				screen.setText(screen.getText() + "*");
			}

			break;

		case "÷":
			if (screen.getText().isEmpty() == false && evalMode == false) {

				myFirstNum = Double.parseDouble(screen.getText().toString());
				operator = 4;
				screen.setText("");
				bottomScreen.setText("");
			} else if (screen.getText().isEmpty() == false && evalMode == true) {
				screen.setText(screen.getText() + "/");
			}

			break;
//
		case "x^n":
			// screen full, eval mode OFF
			if (screen.getText().isEmpty() == false && evalMode == false) {

				myFirstNum = Double.parseDouble(screen.getText().toString());
				operator = 5;
				screen.setText("");
				// screen full, eval mode ON
			} else if (screen.getText().isEmpty() == false && evalMode == true) {
				screen.setText(screen.getText() + "^");
				bottomScreen.setText("Enter the exponentiation number");
			}

			break;

		case "Sqrt":
			if (screen.getText().isEmpty() == false && evalMode == false) {
				myNumber = Double.parseDouble(screen.getText().toString());
				bottomScreen.setText(
						String.valueOf("Sqrt(" + screen.getText() + ")=" + decFormat.format(Math.sqrt(myNumber))));
				screen.setText(String.valueOf(Math.sqrt(myNumber)));
			} else if (screen.getText().isEmpty() == true && evalMode == false) {
			bottomScreen.setText("Please enter a number first!");
		}
		if (evalMode == true) {
			screen.setText(screen.getText() + " sqrt(");
		}

			break;

		case "Cube root":
			if (screen.getText().isEmpty() == false && evalMode == false) {
				myNumber = Double.parseDouble(screen.getText().toString());
			bottomScreen
						.setText(String.valueOf(
								"Cube root(" + screen.getText() + ")=" + decFormat.format(Math.cbrt(myNumber))));
				screen.setText(String.valueOf(Math.cbrt(myNumber)));
			} else if (screen.getText().isEmpty() == true && evalMode == false) {
			bottomScreen.setText("Please enter a number first!");
		}
		if (evalMode == true) {
			screen.setText(screen.getText() + " cbrt(");
		}
			break;

		case "x^2":
			if (screen.getText().isEmpty() == false && evalMode == false) {
				myNumber = Double.parseDouble(screen.getText().toString());
				bottomScreen.setText(String.valueOf("x² =" + decFormat.format(Math.pow(myNumber, 2))));
				screen.setText(String.valueOf(Math.pow(myNumber, 2)));
				// screen empty, eval mode OFF
			} else if (screen.getText().isEmpty() == true && evalMode == false) {
			bottomScreen.setText("Please enter a number first!");
		}
		if (screen.getText().isEmpty() == false && evalMode == true) {
			screen.setText(screen.getText() + "^2");
		}
			break;

		case "%":
			if (screen.getText().isEmpty() == false && evalMode == false) {
				myNumber = Double.parseDouble(screen.getText().toString());
				screen.setText(String.valueOf(myNumber / 100.0));
			} else if (screen.getText().isEmpty() == true && evalMode == false) {
				bottomScreen.setText("Please enter a number first!");
			}
			if (screen.getText().isEmpty() == false && evalMode == true) {
				screen.setText(screen.getText() + "%");
			}
			break;

		case "AC":
			screen.setText("");
			bottomScreen.setText("");
			break;

		case "n!":
			if (screen.getText().isEmpty() == false && evalMode == false) {
				myNumber = Double.parseDouble(screen.getText().toString());
				// factorialCalc class is called here
				screen.setText(String.valueOf(factorialCalc(myNumber)));
				bottomScreen
						.setText(String.valueOf(
								"Factorial of " + myNumber + " =" + decFormat.format(factorialCalc(myNumber))));
			} else if (screen.getText().isEmpty() == true && evalMode == false) {
				bottomScreen.setText("Please enter a number first!");
			}

			if (evalMode == true) {
				screen.setText(screen.getText() + "!");
			}

			break;

		case "sin":
			// screen FULL evalMode OFF
			if (screen.getText().isEmpty() == false && evalMode == false) {
				myNumber = Double.parseDouble(screen.getText().toString());
				bottomScreen.setText(String
						.valueOf("Sin(" + myNumber + ") =" + decFormat.format(Math.sin(Math.toRadians(myNumber)))));
				screen.setText(String.valueOf(Math.sin(Math.toRadians(myNumber))));
		}
		// screen EMPTY evalMode OFF
		else if (screen.getText().isEmpty() == true && evalMode == false) {
			bottomScreen.setText("Please enter a number first!");
		}
		// evalMode ON
		if (evalMode == true) {
			screen.setText(screen.getText() + " sin(");
		}
			break;

		case "cos":
			if (screen.getText().isEmpty() == false && evalMode == false) {
				myNumber = Double.parseDouble(screen.getText().toString());
				bottomScreen.setText(
						String.valueOf(
								"Cos(" + myNumber + ") =" + decFormat.format(Math.cos(Math.toRadians(myNumber)))));
				screen.setText(String.valueOf(Math.cos(Math.toRadians(myNumber))));
			} else if (screen.getText().isEmpty() == true && evalMode == false) {
				bottomScreen.setText("Please enter a number first!");
			}
			if (evalMode == true) {
				screen.setText(screen.getText() + " cos(");
			}
			break;

		case "tan":
			// screen FULL evalMode OFF
			if (screen.getText().isEmpty() == false && evalMode == false) {
				myNumber = Double.parseDouble(screen.getText().toString());
				bottomScreen.setText(
						String.valueOf(
								"Tan(" + myNumber + ") =" + decFormat.format(Math.tan(Math.toRadians(myNumber)))));
				screen.setText(String.valueOf(Math.tan(Math.toRadians(myNumber))));
				// screen EMPTY evalMode OFF
			} else if (screen.getText().isEmpty() == true && evalMode == false) {
				bottomScreen.setText("Please enter a number first!");
			}
			// evalMode ON
			if (evalMode == true) {
				screen.setText(screen.getText() + " tan(");
			}
			break;

		case "log":
			if (screen.getText().isEmpty() == false && evalMode == false) {
				myNumber = Double.parseDouble(screen.getText().toString());
				bottomScreen.setText(String.valueOf("log(" + myNumber + ")=" + decFormat.format(Math.log10(myNumber))));
				screen.setText(String.valueOf(Math.log10(myNumber)));
			} else if (screen.getText().isEmpty() == true && evalMode == false) {
				bottomScreen.setText("Please enter a number first!");
			}
			if (evalMode == true) {
				screen.setText(screen.getText() + " log(");
			}
			break;


		case "<--":
			if (screen.getText().isEmpty() == false) {
				// erases the last character
				screen.setText(screen.getText().substring(0, screen.getText().length() - 1));
			}



		default:
		}

		// clicked = and evalmode OFF
		if (myCommand.equalsIgnoreCase("=") && evalMode == false) {

			if (screen.getText().isEmpty() == false) {

				mySecondNum = Double.parseDouble(screen.getText().toString());

				switch (operator) {
				case 1: // addition
					screen.setText(String.valueOf(myFirstNum + mySecondNum));
					bottomScreen
							.setText(String
									.valueOf(myFirstNum + " + " + mySecondNum + " = "
											+ decFormat.format(myFirstNum + mySecondNum)));
					break;

				case 2: // subtraction
					screen.setText(String.valueOf(myFirstNum - mySecondNum));

					bottomScreen.setText(String.valueOf(
							myFirstNum + " - " + mySecondNum + " = " + decFormat.format(myFirstNum - mySecondNum)));
					break;

				case 3: // multiplication
					screen.setText(String.valueOf(myFirstNum * mySecondNum));
					bottomScreen
							.setText(String
									.valueOf(myFirstNum + " × " + mySecondNum + " = "
											+ decFormat.format(myFirstNum * mySecondNum)));
					break;

				case 4: // division
					screen.setText(String.valueOf(myFirstNum / mySecondNum));
					bottomScreen
							.setText(String
									.valueOf(myFirstNum + " ÷ " + mySecondNum + " = "
											+ decFormat.format(myFirstNum / mySecondNum)));
					break;

				case 5: // n power of x
					screen.setText(String.valueOf(Math.pow(myFirstNum, mySecondNum)));
					bottomScreen.setText(String.valueOf(mySecondNum + " power of " + myFirstNum + " = "
							+ decFormat.format(Math.pow(myFirstNum, mySecondNum))));
					break;


				default:
				}
			}
			// clicked = and evalmode ON
		} else if (myCommand.equalsIgnoreCase("=") && evalMode == true) {
			bottomScreen.setText(screen.getText());
			screen.setText(String.valueOf(evaluate(screen.getText())));
			bottomScreen.setText(bottomScreen.getText() + "=" + screen.getText());

		}

		// toggle button on/off
		if (myCommand.equalsIgnoreCase("Turn Eval Expression On")) {
			screen.setEditable(true);
			evalMode = true;
			buttons[11].setEnabled(true);
			buttons[12].setEnabled(true);
			bottomScreen.setText("Enter your mathematical expression to calculate");
			btnEvalOnOff.setText("Turn Eval Expression Off");

		} else if (myCommand.equalsIgnoreCase("Turn Eval Expression Off")) {
			evalMode = false;
			screen.setEditable(false);
			buttons[11].setEnabled(false);
			buttons[12].setEnabled(false);
			btnEvalOnOff.setText("Turn Eval Expression On");
		}
	}


	public static double factorialCalc(double n) {
		// it calculates factorial recursively
		if (n <= 1.0) {
			return (1.0);
		}

		return (n * factorialCalc(n - 1));

	}

	public void readConfiguration() throws URISyntaxException, IOException {
		java.net.URL resource = this.getClass().getClassLoader().getResource("config.properties");
		File configFile = new File(resource.toURI());

		try {
			FileReader reader = new FileReader(configFile);
			Properties props = new Properties();
			props.load(reader);

			M0Value = props.getProperty("M0");
			M1Value = props.getProperty("M1");
			M2Value = props.getProperty("M2");
			M3Value = props.getProperty("M3");


			reader.close();
		} catch (FileNotFoundException ex) {
			// file does not exist
		} catch (IOException ex) {
			// I/O error
		}
	}

	public void writeConfiguration() throws URISyntaxException, IOException {
		java.net.URL resource = this.getClass().getClassLoader().getResource("config.properties");
		File configFile = new File(resource.toURI());

		try {
			Properties props = new Properties();
			props.setProperty("M0", M0Value);
			props.setProperty("M1", M1Value);
			props.setProperty("M2", M2Value);
			props.setProperty("M3", M3Value);
			FileWriter writer = new FileWriter(configFile);
			props.store(writer, "memory values");
			writer.close();
		} catch (FileNotFoundException ex) {
			// file does not exist
		} catch (IOException ex) {
			// I/O error
		}
	}

	public double evaluate(final String evalStr) {
		// In java there is no eval() method like in Javascript.
		// I used a method which parses(character by character cozumlemek) the input and
		// calculates the result.
		// This algorithm follows the rules of mathematical operational order to find
		// the correct result.
		// 1. operator sign, variable evaluation, call of function, parenthesis or
		// sub-expressions
		// 2. exponentiation, multiplication or division
		// 3. addition or subtraction
		// Algorithm methods are divided for each level of operations order.

		return new Object() {
			int position = -1, myCharacter;

			void getNextCharacter() {
				++position;
				if (position < evalStr.length()) {
					myCharacter = evalStr.charAt(position);
				} else {
					myCharacter = -1;
				}
				System.out.println("get next char:" + Character.toString((char) myCharacter));
			}

			boolean inputChar(int acceptedChar) {
				// This method is used to check whether the current cursor char is equal with
				// the acceptedChar char value
				// If they are equal, this method returns true and it moves cursor to next char
				while (myCharacter == ' ')
					getNextCharacter();
				if (myCharacter == acceptedChar) {
					System.out.println("input1 char:" + Character.toString((char) myCharacter));
					getNextCharacter();
					System.out.println("input2 char:" + Character.toString((char) myCharacter));
					return true;
			}
				System.out.println("input3 char:" + Character.toString((char) myCharacter));
				return false;
			}

			double myParser() {
				// This method calls the myExpressionParser() method and the algorithm is set to
				// call all the other methods in reverse order
				// So after this method is called, it calls myExpressionParser() and then it
				// calls
				// myTermParser(), and then myFactorParser() and
				// it calls myExpressionParser() for sub-expressions again.
				// Each method has a first line that calls the next method so the mathematical
				// operation order is followed.

				getNextCharacter();
				System.out.println("input4 char:" + Character.toString((char) myCharacter));
				double x = myExpressionParser();

				if (position < evalStr.length()) {

					bottomScreen.setText("Unexpected: " + (char) myCharacter);

					throw new RuntimeException("Unexpected: " + (char) myCharacter);
			}
				System.out.println("my parser x:" + x);
				return x;
			}

			double myExpressionParser() {
				// This method parses the input for the addition and subtraction, exponentiation
				double x = myTermParser();
				// The line above calls the next method so the mathematical operation order is
				// followed.
				for (;;) {
					if (inputChar('+'))
						x += myTermParser(); // addition
					else if (inputChar('-'))
						x -= myTermParser(); // subtraction
					else {
						System.out.println("my expression x:" + x);
						return x;
					}
			}
			}

			double myTermParser() {
				// This method parses the input for the multiplication, division
				double x = myFactorParser();
				// The line above calls the next method so the mathematical operation order is
				// followed.
				System.out.println("my term parser1 x:" + x);
				for (;;) {
					if (inputChar('*')) {
						x *= myFactorParser(); // multiplication
						System.out.println("my term parser2 x:" + x);
					} else if (inputChar('/')) {
						x /= myFactorParser(); // division
						System.out.println("my term parser3 x:" + x);
					} else if (inputChar('^')) {
						x = Math.pow(x, myFactorParser()); // exponentiation
						System.out.println("my term parser4 x:" + x);
					} else {
						System.out.println("my term parser5 x" + x);
						return x;
					}
			}
			}

			double myFactorParser() {
				// This method parses for the input for operator sign, variable evaluation, call
				// of function, parenthesis or sub-expressions

				// This myFactorParser() method does not have its next level as described in the
				// algorithm above
				// So it calls the required methods to evaluate or if there is a parenthesis to
				// evaluate sub-expression.
				if (inputChar('+'))
					return +myFactorParser(); // unary plus
				if (inputChar('-'))
					return -myFactorParser(); // unary minus

				double x;
				int startPos = this.position;
				if (inputChar('(')) { // parentheses
					x = myExpressionParser();
					System.out.println("my factor parser1 x:" + x);
					if (!inputChar(')')) {
						bottomScreen.setText("Missing ')'");
						throw new RuntimeException("Missing ')'");
					}
				} else if ((myCharacter >= '0' && myCharacter <= '9') || myCharacter == '.') { // numbers
					while ((myCharacter >= '0' && myCharacter <= '9') || myCharacter == '.')
						getNextCharacter();
					x = Double.parseDouble(evalStr.substring(startPos, this.position));
					System.out.println("my factor parser2 x:" + x);
				} else if (myCharacter >= 'a' && myCharacter <= 'z') { // functions
					while (myCharacter >= 'a' && myCharacter <= 'z')
						getNextCharacter();
					String func = evalStr.substring(startPos, this.position);
					System.out.println("my factor parser2 func:" + func);
					if (inputChar('(')) {
						x = myExpressionParser();
						System.out.println("my factor parser3 x:" + x);
						// The line above calls the next method so the mathematical operation order is
						// followed.
						if (!inputChar(')')) {

							bottomScreen.setText("Missing ')' after argument to " + func);
							throw new RuntimeException("Missing ')' after argument to " + func);
						}
					} else {
						x = myFactorParser();
						System.out.println("my factor parser4 x:" + x);
						// The line above calls the next method so the mathematical operation order is
						// followed.
					}
					if (func.equals("sqrt"))
						x = Math.sqrt(x);
					else if (func.equals("sin"))
						x = Math.sin(Math.toRadians(x));
					else if (func.equals("cos"))
						x = Math.cos(Math.toRadians(x));
					else if (func.equals("tan"))
						x = Math.tan(Math.toRadians(x));
					else if (func.equals("log"))
						x = Math.log10(x);
					else if (func.equals("cbrt"))
						x = Math.cbrt(x);
					else {

						bottomScreen.setText("Unknown function: " + func);
						throw new RuntimeException("Unknown function: " + func);
					}
				} else {
					bottomScreen.setText("Unexpected: " + (char) myCharacter);
					throw new RuntimeException("Unexpected: " + (char) myCharacter);
				}

				if (inputChar('!'))
					x = factorialCalc(x); // factorial

				if (inputChar('%'))
					x = (x / 100.0); // percentage
				System.out.println("my factor parser5 x:" + x);
				return x;
			}
		}.myParser();
	}


}

