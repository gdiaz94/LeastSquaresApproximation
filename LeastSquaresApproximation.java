// Programmer: 	Gabe Diaz
// Date:		05/02/21
// Course:		Numerical Analysis CS384
// Purpose:		Implement an algorithm for calculating the least squares approximation (Linear and Polynomial)
import java.io.*;
import java.util.Scanner;
import java.lang.Math;

public class LeastSquaresApproximation 
{
	static float A[][] = null; // Will be used to hold the coefficients
	static float B[][] = null; // Will be used to hold the answer portion of the equations

	public static void main(String[] args) 
	{
		int n = 0; // Degree
		int m = 0; // Number of Points
		float X[] = null; // Holds the x values from input.txt
		float Y[] = null; // Holds the y values from input.txt
		
		try
		{
			Scanner input = new Scanner(new File("input.txt"));
			n = input.nextInt(); // Degree of the polynomial
			m = input.nextInt();
			
			X = new float[m];
			Y = new float[m];
			
			for (int i = 0; i < m; i++)
			{
				X[i] = input.nextFloat();
			}
			
			for (int i = 0; i < m; i++)
			{
				Y[i] = input.nextFloat();
			}
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
//		for (int i = 0; i < m; i++)
//		{
//			System.out.print("x[" + i + "]: " + X[i] + "\tY[" + i + "]: " + Y[i] + "\n");
//		}
		
		getEquations(X, Y, n, m);
//		printEquations(n, m);
		
		float solutions[] = elimination(A[0].length);
		
//		printEquations(n,m);
		
		for (int i = 0; i < solutions.length; i++)
		{
			System.out.print("a" + i + " = " + solutions[i] + " ");
		}
		System.out.println();
		
		System.out.println("Error = " + calculateError(solutions, X, Y));
	}
	
	private static void getEquations(float X[], float Y[], int n, int m)
	{
		int factors = n + 1; // Number of factors in each equation
		A = new float[factors][factors];
		B = new float[factors][1];
		int exponent = 0;
		
		// Initialize A and B to all zeros
		for (int i = 0; i < factors; i++)
		{
			B[i][0] = 0;
			
			for (int j = 0; j < factors; j++)
			{
				A[i][j] = 0;
			}
		}
		
		for (int i = 0; i < factors; i++)
		{
			exponent = i;
			
			// Calculate values of the B matrix
			for (int j = 0; j < m; j++)
			{
				B[i][0] += (float)(Y[j] * Math.pow(X[j], exponent));
			}
			
			// Calculate values of the A matrix
			for (int j = 0; j < factors; j++)
			{
				for (int k = 0; k < m; k++)
				{
					A[i][j] += (float)Math.pow(X[k], exponent);
				}
				
				exponent++;
//				if(++exponent == n + i)
//					break;
			}
		}
	}
	
	private static void printEquations(int n, int m)
	{
		int factors = n + 1; // Number of factors in each equation
		for (int i = 0; i < factors; i++)
		{
			for (int j = 0; j < factors; j++)
			{
				if (A[i][j] >= 0)
				{
					System.out.print("+" + A[i][j] + "a" + j + " ");
				}
				else
				{
					System.out.print("-" + A[i][j] + "a" + j + " ");
				}
				
			}
			System.out.print("=" + B[i][0] + "\n");
		}
	}
	
	// Performs Gaussian elimination on a square matrix and column matrix that represent
	// a system of equations, solves the system, and returns an array with the variable values
	private static float[] elimination(int rowColNum)
	{
		for (int i = 0; i < rowColNum - 1; i++)
		{
			for (int j = i + 1; j < rowColNum; j++)
			{
				float multiplier = A[j][i] / A[i][i];
				
				for (int k = i; k < rowColNum; k++)
				{
					A[j][k] = A[j][k] - A[i][k] * multiplier;
					
					if ( k == i)
					{
						B[j][0] = B[j][0] - B[i][0] * multiplier;
					}
				}
			}
		}
		
		// Solve the equation
		float solutions[] = new float[rowColNum];
		for (int i = 0; i < rowColNum; i++)
		{
			// Initialize each element to 0
			solutions[i] = 0;
		}
		
		for (int i = rowColNum - 1; i >= 0; i--)
		{
			solutions[i] = B[i][0];
			
			for (int j = i + 1; j < rowColNum; j++)
			{
				solutions[i] -= A[i][j] * solutions[j];
			}
			
			solutions[i] = solutions[i]/A[i][i];
		}
		
		return solutions;
	}
	
	private static float calculateError(float[] solutions, float[] X, float[] Y)
	{
		float error = 0;
		float approximation;
		
		for (int i = 0; i < X.length; i++)
		{
			approximation = 0;
			// Calculate approximation P(Xi)
			for (int j = 0; j < solutions.length; j++)
			{
				approximation += solutions[j] * Math.pow(X[i], j);  
			}
			
			error = (float)Math.pow(Y[i] - approximation, 2);
		}
		
		return error;
	}
}
