package uk.co.crawj.week1;

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
            doMove( move, board, starLevel );
        }
        
        return getResult( board );
    }
    
    private static String getResult( char[][] board )
    {
        StringBuilder result = new StringBuilder();
        
        for ( int i = 0; i < board[0].length; i++ )
        {
            char mover = ' ';
            int moverCount = 0;
            while ( charIsEmpty( mover ) && moverCount < board.length )
            {
                mover = board[moverCount][i];
                moverCount++;
            }
            result.append( mover );
        }
        return result.toString();
    }
    
    private static boolean charIsEmpty( char c )
    {
        return c == ' ' || c == '\u0000' || c == '0';
    }
    
    private static void doMove( String move, char[][] board, StarLevel starLevel )
    {
        String[] instructions = move.split( " " );
        int numberToMove = Integer.parseInt( instructions[1] );
        int start = Integer.parseInt( instructions[3] );
        int end = Integer.parseInt( instructions[5] );
        
        if ( starLevel == StarLevel.STAR_1 )
        {
            moveForStar1( board, numberToMove, start, end );
        }
        else
        {
            moveForStar2( board, numberToMove, start, end );
        }
        
    }
    
    private static void moveForStar2( char[][] board, int numberToMove, int start, int end )
    {
        char[] valsToMove = new char[numberToMove];
        
        for ( int i = 0; i < numberToMove; i++ )
        {
            // get the highest
            valsToMove[i] = getValToMove( board, start );
        }
        
        for ( int i = valsToMove.length - 1; i >= 0; i-- )
        {
            char mover = 'X';
            int moverCount = board.length - 1;
            while ( !charIsEmpty( mover ) && moverCount >= 0 )
            {
                mover = board[moverCount][end - 1];
                moverCount--;
            }
            board[moverCount + 1][end - 1] = valsToMove[i];
        }
        
    }
    
    private static char getValToMove( char[][] board, int start )
    {
        int count = 0;
        char val = ' ';
        while ( charIsEmpty( val ) && count < board.length )
        {
            val = board[count][start - 1];
            count++;
        }
        board[count - 1][start - 1] = ' '; // wipe moved value
        return val;
    }
    
    private static void moveForStar1( char[][] board, int numberToMove, int start, int end )
    {
        for ( int i = 0; i < numberToMove; i++ )
        {
            char mover = 'X';
            int moverCount = board.length - 1;
            while ( !charIsEmpty( mover ) && moverCount >= 0 )
            {
                mover = board[moverCount][end - 1];
                moverCount--;
            }
            
            board[moverCount + 1][end - 1] = getValToMove( board, start );
        }
    }
    
    static char[][] generateBoard( String gameBoard )
    {
        String[] splitBoard = gameBoard.split( "\n" );
    
        int lengthOfRows = 0;
        int[] rows = new int[10];
        for ( int i = 1; i <= 10; i++ )
        {
            int str = splitBoard[splitBoard.length - 1].indexOf( String.valueOf( i ) );
            rows[i - 1] = str;
            if ( str != -1 )
            {
                lengthOfRows++;
            }
        }
    
        String[] splitBoardCopy = gameBoard.substring( 0, gameBoard.lastIndexOf( "\n" ) ).split( "\n" );
        char[][] resultBoard = new char[100][lengthOfRows]; // todo figure actual max size of board needed
        int count = 99 - splitBoardCopy.length + 1;
        for ( String row : splitBoardCopy )
        {
            for ( int i = 0; i < lengthOfRows; i++ )
            {
                char c = row.charAt( rows[i] );
                resultBoard[count][i] = c;
            }
            count++;
        }
        
        return resultBoard;
    }
    
}
