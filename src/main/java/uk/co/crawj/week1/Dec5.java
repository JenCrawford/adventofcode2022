package uk.co.crawj.week1;

import java.util.Arrays;

import uk.co.crawj.utility.StarLevel;

public class Dec5
{
    private Dec5()
    {
        throw new IllegalStateException( "Utility Class" );
    }
    
    public static String code( String input, StarLevel starLevel )
    {
        String[] splitInput = input.split( "\n\n" );
        
        String gameBoard = splitInput[0];
        
        char[][] board = generateBoard( gameBoard );
        
        String[] moves = splitInput[1].split( "\n" );
        
        for ( String move : moves )
        {
            doMove( move, board );
        }
        
        return getResult( board );
    }
    
    private static String getResult( char[][] board )
    {
        StringBuilder result = new StringBuilder();
        
        for ( int i = 0; i < board[0].length; i++ )
        {
            System.out.println( "checking " + i + " for val" );
            char mover = ' ';
            int moverCount = 0;
            while ( charIsEmpty( mover ) && moverCount < board.length )
            {
                mover = board[moverCount][i];
                moverCount++;
            }
            result.append( mover );
        }
        System.out.println( result );
        return result.toString();
    }
    
    private static boolean charIsEmpty( char c )
    {
        return c == ' ' || c == '\u0000' || c == '0';
    }
    
    private static void doMove( String move, char[][] board )
    {
        String[] instructions = move.split( " " );
        int numberToMove = Integer.parseInt( instructions[1] );
        int start = Integer.parseInt( instructions[3] );
        int end = Integer.parseInt( instructions[5] );
        
        for ( int i = 0; i < numberToMove; i++ )
        {
            // get the highest
            int count = 0;
            char val = ' ';
            while ( charIsEmpty( val ) && count < board.length )
            {
                val = board[count][start - 1];
                count++;
                System.out.println( val + " found at " + ( count - 1 ) + " " + ( start - 1 ) );
            }
            
            System.out.println( "Going to move " + val + ". Clearing " + ( count - 1 ) + " " + ( start - 1 ) );
            
            board[count - 1][start - 1] = ' '; // wipe moved value
            
            char mover = 'X';
            int moverCount = board.length - 1;
            while ( !charIsEmpty( mover ) && moverCount >= 0 )
            {
                mover = board[moverCount][end - 1];
                moverCount--;
                System.out.println( "replacement: " + mover + " found at " + ( moverCount + 1 ) + " " + ( end - 1 ) );
            }
            
            System.out.println( "Moving " + val + " to " + ( moverCount + 1 ) + " " + ( end - 1 ) );
            board[moverCount + 1][end - 1] = val;
            System.out.println( Arrays.deepToString( board ) );
        }
    
        
       /*    [D]
         [N] [C]
         [Z] [M] [P]
         
         00  01  02
         10  11  12
         20  21  22
         */
        
        /*
        *   [H]                 [Z]         [J]
            [L]     [W] [B]     [G]         [R]
            [R]     [G] [S]     [J] [H]     [Q]
            [F]     [N] [T] [J] [P] [R]     [F]
            [B]     [C] [M] [R] [Q] [F] [G] [P]
            [C] [D] [F] [D] [D] [D] [T] [M] [G]
            [J] [C] [J] [J] [C] [L] [Z] [V] [B]
            [M] [Z] [H] [P] [N] [W] [P] [L] [C]
             1   2   3   4   5   6   7   8   9
             
             00  01  02  03  04  05  06  07  08
             10  11  12  13  14  15  16  17  18
             20  21  22  23  24  25  26  27  28
             30  31  32  33  34  35  36  37  38
             40  41  42  43  44  45  46  47  48
             *
             *
             70  71  72  73  74  75  76  77  78
             *  * */
    }
    
    static char[][] generateBoard( String gameBoard )
    {
        String[] splitBoard = gameBoard.split( "\n" );
        
        System.out.println( splitBoard.length );
        System.out.println( splitBoard[0] );
        int[] rows = new int[10];
        for ( int i = 1; i <= 10; i++ )
        {
            rows[i - 1] = splitBoard[splitBoard.length - 1].indexOf( String.valueOf( i ) );
        }
        
        String[] splitBoardCopy = gameBoard.substring( 0, gameBoard.lastIndexOf( "\n" ) ).split( "\n" );
        char[][] resultBoard = new char[100][splitBoardCopy.length];
        int count = 99 - splitBoardCopy.length + 1;
        for ( String row : splitBoardCopy )
        {
            for ( int i = 0; i < splitBoardCopy.length; i++ )
            {
                System.out.println( i );
                char c = row.charAt( rows[i] );
                resultBoard[count][i] = c;
            }
            count++;
        }
        
        System.out.println( Arrays.deepToString( resultBoard ) );
        
        return resultBoard;
    }
    
}
