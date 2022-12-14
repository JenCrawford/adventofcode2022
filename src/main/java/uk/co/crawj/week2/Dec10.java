package uk.co.crawj.week2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import uk.co.crawj.utility.StarLevel;

public class Dec10
{
    static final Map<Integer, String> pixelRows = new HashMap<>();
    
    static int X = 1;
    
    static String spriteLocation = "###.....................................";
    
    static List<Integer> relevantCycles = Arrays.asList( 20, 60, 100, 140, 180, 220 );
    
    private static int cycleNum = 1;
    
    private static int result = 0;
    
    private Dec10()
    {
        throw new IllegalStateException( "Utility Class" );
    }
    
    public static int code( String input, StarLevel starLevel )
    {
        X = 1;
        cycleNum = 1;
        result = 0;
        String[] instructions = input.split( "\n" );
        
        if ( starLevel == StarLevel.STAR_1 )
        {
            handleStarLevel1( instructions );
        }
        else
        {
            handleStarLevel2( instructions );
        }
        
        return result;
    }
    
    private static void handleStarLevel2( String[] instructions )
    {
        drawPixels( instructions );
        
        for ( String row : pixelRows.values() )
        {
            System.out.println( row );
        }
    }
    
    private static void handleStarLevel1( String[] instructions )
    {
        for ( String instruction : instructions )
        {
            // do something
            if ( instruction.equals( "noop" ) )
            {
                doNoop();
            }
            else if ( instruction.contains( "addx" ) )
            {
                String[] split = instruction.split( " " );
                int change = Integer.parseInt( split[1] );
                
                doAdd( change );
            }
        }
    }
    
    private static void drawPixels( String[] instructions )
    {
        drawPixel( 0 );
        Iterator<String> iterator = Arrays.stream( instructions ).iterator();
        int count = 0;
        while ( iterator.hasNext() )
        {
            for ( int i = 0; i < 40; i++ )
            {
                if ( iterator.hasNext() )
                {
                    String instruction = iterator.next();
                    if ( instruction.equals( "noop" ) )
                    {
                        // do nothing
                        drawPixel( count );
                    }
                    if ( instruction.contains( "addx" ) )
                    {
                        drawPixel( count );
                        i++;
                        updateSpriteLocation( instruction );
                        drawPixel( count );
                    }
                }
            }
            count++;
        }
    }
    
    private static void drawPixel( int count )
    {
        String s = pixelRows.get( count );
        if ( s == null )
        {
            s = "";
        }
        int length = s.length();
        
        if ( length >= 40 )
        {
            drawPixel( count + 1 );
        }
        else
        {
            s += spriteLocation.charAt( length );
            pixelRows.put( count, s );
        }
    }
    
    static void updateSpriteLocation( String instruction )
    {
        if ( instruction.equals( "reset" ) )
        {
            spriteLocation = "###.....................................";
        }
        // do nothing
        if ( instruction.contains( "addx" ) )
        {
            int position = Integer.parseInt( instruction.split( " " )[1] ) + X;
            StringBuilder newSprite = new StringBuilder();
            for ( int i = 0; i < 40; i++ )
            {
                if ( i == position || ( i == position - 1 ) || ( i == position + 1 ) )
                {
                    newSprite.append( "#" );
                }
                else
                {
                    newSprite.append( "." );
                }
            }
            
            spriteLocation = newSprite.toString();
            X = position;
        }
    }
    
    private static void doAdd( int change )
    {
        if ( relevantCycles.contains( cycleNum ) )
        {
            calculateSignalStrength();
        }
        cycleNum++;
        if ( relevantCycles.contains( cycleNum ) )
        {
            calculateSignalStrength();
        }
        cycleNum++;
        X += change;
    }
    
    private static void doNoop()
    {
        if ( relevantCycles.contains( cycleNum ) )
        {
            calculateSignalStrength();
        }
        cycleNum++;
    }
    
    private static void calculateSignalStrength()
    {
        result = result + ( X * cycleNum );
    }
}
