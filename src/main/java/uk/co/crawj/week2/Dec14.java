package uk.co.crawj.week2;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import uk.co.crawj.utility.LogHelper;
import uk.co.crawj.utility.StarLevel;

public class Dec14
{
    private static Map<Integer, List<LocalPoint>> map;
    
    private Dec14()
    {
        throw new IllegalStateException( "Utility Class" );
    }
    
    public static int code( String testInput, StarLevel starLevel )
    {
        createGrid( testInput, starLevel );
        
        try
        {
            for ( int i = 0; i < 100000; i++ )
            {
                addSand();
                LogHelper.log( map );
            }
        }
        catch ( IllegalStateException e )
        {
            return countOs();
        }
        
        return countOs();
    }
    
    private static int countOs()
    {
        return (int) map.values().stream().flatMap( Collection::stream )
                .filter( localPoint -> localPoint.equalsMaterial( "o" ) )
                .count();
    }
    
    private static void addSand()
    {
        boolean stopped = false;
        int count = 0;
        Point finalPoint = new Point( 500, 0 );
        while ( !stopped )
        {
            boolean currentPointIsFinal = currentPointIsFinal( 500, finalPoint.y );
            
            if ( count == 10 || currentPointIsFinal )
            {
                stopped = true;
                finalPoint = calculateXPosition( finalPoint );
            }
            else
            {
                finalPoint.translate( 0, 1 );
            }
            count++;
        }
        
        markGivenPoint( finalPoint.x, finalPoint.y, "o" );
        if ( finalPoint.x == 500 && finalPoint.y == 0 )
        {
            throw new IllegalStateException( "Reached the top" );
        }
    }
    
    private static Point calculateXPosition( Point finalPoint )
    {
        List<LocalPoint> nextRow = map.get( finalPoint.y );
        String finalXM1 = "";
        String finalXV = "";
        String finalXP1 = "";
        for ( LocalPoint localPoint : nextRow )
        {
            if ( localPoint.x == finalPoint.x )
            {
                finalXV = localPoint.getMaterial();
            }
            if ( localPoint.x == finalPoint.x - 1 )
            {
                finalXM1 = localPoint.getMaterial();
            }
            if ( localPoint.x == finalPoint.x + 1 )
            {
                finalXP1 = localPoint.getMaterial();
            }
        }
        
        int result = 0;
        
        if ( finalXV.equals( "." ) )
        {
            result = finalPoint.x;
        }
        else if ( finalXM1.equals( "." ) )
        {
            result = finalPoint.x - 1;
        }
        else if ( finalXP1.equals( "." ) )
        {
            result = finalPoint.x + 1;
        }
        
        if ( currentPointIsFinal( result, finalPoint.y ) )
        {
            return new Point( result, finalPoint.y );
        }
        else
        {
            return calculateXPosition( new Point( result, finalPoint.y + 1 ) );
        }
    }
    
    private static boolean currentPointIsFinal( int finalX, int finalY )
    {
        List<LocalPoint> nextRow = map.get( finalY + 1 );
        return !validateNextRowSteps( finalX, nextRow );
    }
    
    private static boolean validateNextRowSteps( int x, List<LocalPoint> rowToCheck )
    {
        if ( rowToCheck == null )
        {
            throw new IllegalStateException( "End of programme" );
        }
        for ( LocalPoint localPoint : rowToCheck )
        {
            if ( ( localPoint.x == x || localPoint.x == x - 1 || localPoint.x == x + 1 ) && localPoint.equalsMaterial( "." ) )
            {
                return true;
            }
        }
        return false;
    }
    
    static void createGrid( String testInput, StarLevel starLevel )
    {
        String[] patterns = testInput.split( "\n" );
        
        int smallestX = Integer.MAX_VALUE;
        int biggestX = Integer.MIN_VALUE;
        
        int smallestY = Integer.MAX_VALUE;
        int biggestY = Integer.MIN_VALUE;
        
        for ( String pattern : patterns )
        {
            String[] coords = pattern.split( " -> " );
            for ( String coord : coords )
            {
                int x = Integer.parseInt( coord.split( "," )[0] );
                int y = Integer.parseInt( coord.split( "," )[1] );
                
                smallestX = Math.min( smallestX, x );
                biggestX = Math.max( biggestX, x );
                smallestY = Math.min( smallestY, y );
                biggestY = Math.max( biggestY, y );
            }
        }
        
        if ( starLevel == StarLevel.STAR_2 )
        {
            biggestY += 2;
        }
        map = new HashMap<>();
        for ( int i = 0; i <= biggestY; i++ )
        {
            map.put( i, createPoints( smallestX, biggestX, i ) );
        }
        
        for ( String pattern : patterns )
        {
            String[] coords = pattern.split( " -> " );
            for ( int i = 0; i < coords.length - 1; i++ )
            {
                int x = Integer.parseInt( coords[i].split( "," )[0] );
                int y = Integer.parseInt( coords[i].split( "," )[1] );
                
                int nextX = Integer.parseInt( coords[i + 1].split( "," )[0] );
                int nextY = Integer.parseInt( coords[i + 1].split( "," )[1] );
                
                if ( x == nextX )
                {
                    IntStream.range( Math.min( y, nextY ), Math.max( y, nextY ) + 1 ).forEach( p -> markGivenPoint( x, p, "#" ) );
                }
                else
                {
                    IntStream.range( Math.min( x, nextX ), Math.max( x, nextX ) + 1 ).forEach( p -> markGivenPoint( p, y, "#" ) );
                }
            }
        }
        
        if ( starLevel == StarLevel.STAR_2 ) // draw floor
        {
            List<LocalPoint> localPoints = map.get( biggestY );
            for ( LocalPoint localPoint : localPoints )
            {
                localPoint.setMaterial( "#" );
            }
        }
        
        LogHelper.log( map );
    }
    
    private static void markGivenPoint( int x, int y, String type )
    {
        List<LocalPoint> localPoints = map.get( y );
        for ( LocalPoint point : localPoints )
        {
            if ( point.x == x )
            {
                point.setMaterial( type );
                break;
            }
        }
    }
    
    private static ArrayList<LocalPoint> createPoints( int smallestX, int biggestX, int y )
    {
        ArrayList<LocalPoint> points = new ArrayList<>();
        
        IntStream.range( smallestX - 1000, biggestX + 1000 ).forEach( i -> points.add( new LocalPoint( i, y, "." ) ) );
        
        return points;
    }
    
    public static class LocalPoint extends Point
    {
        String material;
        
        public LocalPoint( int x, int y, String material )
        {
            super( x, y );
            this.material = material;
        }
        
        public String getMaterial()
        {
            return material;
        }
        
        public void setMaterial( String material )
        {
            this.material = material;
        }
        
        @Override
        public String toString()
        {
            return getMaterial();
        }
        
        @Override
        public boolean equals( Object obj )
        {
            return super.equals( obj );
        }
        
        @Override
        public int hashCode()
        {
            return super.hashCode();
        }
        
        public boolean equalsMaterial( String check )
        {
            return check.equals( this.getMaterial() );
        }
    }
}
