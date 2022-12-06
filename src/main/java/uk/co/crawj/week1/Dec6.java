package uk.co.crawj.week1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import uk.co.crawj.utility.StarLevel;

public class Dec6
{
    private Dec6()
    {
        throw new IllegalStateException( "Utility Class" );
    }
    
    public static int code( String input, StarLevel starLevel )
    {
        int size = starLevel == StarLevel.STAR_1 ? 4 : 14;
        List<Character> buffer = new ArrayList<>();
        
        char[] charArray = input.toCharArray();
        for ( int i = 0; i < charArray.length; i++ )
        {
            char c = charArray[i];
            if ( buffer.size() < size )
            {
                buffer.add( c );
            }
            else
            {
                if ( !containsADuplicate( buffer ) )
                {
                    return i;
                }
                buffer.remove( 0 );
                buffer.add( c );
            }
        }
        return 0;
    }
    
    private static boolean containsADuplicate( List<Character> buffer )
    {
        List<Character> characters = new ArrayList<>( buffer );
        Collections.sort( characters );
        
        for ( int i = 0; i < characters.size() - 1; i++ )
        {
            if ( Objects.equals( characters.get( i ), characters.get( i + 1 ) ) )
            {
                return true;
            }
        }
        
        return false;
    }
    
}
